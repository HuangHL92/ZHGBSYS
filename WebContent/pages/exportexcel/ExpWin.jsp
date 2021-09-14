<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head/>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/FiledownServlet"  target="simpleExpFrame">	
<table align="center" width="96%">
	<tr>

		<odin:select property="excelType" label="表册类型"  editor="false"   data='<%=(request.getParameter("tmpType")==null?"":java.net.URLDecoder.decode(request.getParameter("tmpType"),"utf-8"))%>'   tpl="<%=ItemValue.tpl_Value%>" required="true"></odin:select>
	</tr>
	<%if(request.getParameter("checkList").equals("1")){%>
	<tr>
        <odin:select property="viewType" label="导出方式" editor="false" value="1"  data="['1', '不分组无机构名称列'],['2', '不分组有机构名称列'],['3', '按机构分组'],['4', '按职务层次分组'],['5', '按职务层次分组，按机构排序']" ></odin:select>
	</tr>
	<%} %>
	 <%if(request.getParameter("checkList").equals("2")){%>
	<tr>
        <odin:select property="viewType2" label="导出方式" editor="false" value="1"  data="['1', '不分组无机构名称'],['3', '按机构分组'],['4', '按职务层次分组']" ></odin:select>
	</tr>
	<%} %>
	<tr>
		<odin:textEdit property="fileName" required="true" label="导出文件名" width="390" title="要导出的excel文件的名称"></odin:textEdit>
	</tr>
	<tr>
		<td height="10" colspan="2"></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			
		
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="下载文件">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value='<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>'/>
	<input type="hidden" name="dw" value='<%=(request.getParameter("dw")==null?"":java.net.URLDecoder.decode(request.getParameter("dw"),"utf-8"))%>' />
	<input type="hidden" name="dwbm" value='<%=(request.getParameter("dwbm")==null?"":java.net.URLDecoder.decode(request.getParameter("dwbm"),"utf-8"))%>' />
	<input type="hidden" name="gbs" value='<%=(request.getParameter("gbs")==null?"":request.getParameter("gbs"))%>' />
	<input type="hidden" name="sys" value='<%=(request.getParameter("sys")==null?"":request.getParameter("sys"))%>' />
	<input type="hidden" name="djs" value='<%=(request.getParameter("djs")==null?"":request.getParameter("djs"))%>' />
	<input type="hidden" name="zhs" value='<%=(request.getParameter("zhs")==null?"":request.getParameter("zhs"))%>' />
	<input type="hidden" name="babdj" value='<%=(request.getParameter("babdj")==null?"":request.getParameter("babdj"))%>' />
	<input type="hidden" name="djgridString" value=""/>
	<input type="hidden" name="zhgridString" value=""/>
	<input type="hidden" name="List" value=""/>
	<%-- <input type="hidden" name="checkList" value='<%=(request.getParameter("checkList")==null?"":java.net.URLDecoder.decode(request.getParameter("checkList"),"utf-8"))%>' /> --%>
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>
/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var babdj=document.getElementById("babdj").value;
	if(babdj!=""){
		var djgridString = parent.getDjgridString();
		document.getElementById("djgridString").value = djgridString;
		var zhgridString = parent.getZhgridString();
		document.getElementById("zhgridString").value = zhgridString;
	}else{
		var checkList = parent.getPersonId();
		document.getElementById("List").value = checkList;
	}
	var a0101=document.getElementById("dw").value;
	if(typeof(a0101)!='undefined'){
	    var excelType=document.getElementById("excelType").value;
	    var filename="";
	    if(excelType=='3'){
	    	filename="公务员登记备案表"
	    }else if(excelType=='4'){
	        filename="参照公务员法管理机关（单位）公务员登记备案表"
	    }else if(excelType=='13'){
	    	filename="公务员登记备案表"
	    }else if(excelType=='14'){
	        filename="参照公务员法管理机关（单位）公务员登记备案表"
	    }
		document.simpleExcelForm.fileName.value = a0101+'_'+filename+'.doc';
		//parent.fileNameSim = undefined;
	}
	//formSubmit();
});
function excelType(){
    checkSelect('excelType');
	var a0101=document.getElementById("dw").value;
	var excelType=document.getElementById("excelType").value;
	var op="_";
	var filename="";
    if(excelType=='3'){
    	filename="公务员登记备案表"
    }else if(excelType=='4'){
        filename="参照公务员法管理机关（单位）公务员登记备案表"
    }else if(excelType=='5'){
        filename="标准名册"
    }else if(excelType=='6'){
        filename="照片名册(每行4人)"
    }else if(excelType=='8'){
        filename="照片名册(每行1人)"
    }else if(excelType=='13'){
    	filename="公务员登记备案表"
    }else if(excelType=='14'){
        filename="参照公务员法管理机关（单位）公务员登记备案表"
    }
    if(a0101==''||a0101=='null')
    	op='';
    if(excelType=='5'||excelType=='6'||excelType=='8'){
		document.simpleExcelForm.fileName.value =  a0101+op+filename+'.xls';
    }else{
    	document.simpleExcelForm.fileName.value =  a0101+op+filename+'.doc';
    }
}
function openChooseFileWin(){
	document.getElementById("excelFile").click();
}

function formSubmit(){
	if(document.getElementById('excelType').value==""){
	     alert("请选择表类型！");
	     return;
	}

	if(document.getElementById('fileName').value!=""){
		parent.fileNameSim = undefined;
		odin.ext.get(document.body).mask('导出数据中...', odin.msgCls);
		document.simpleExcelForm.submit();
		doCloseWin();
		//doWaitingForEnd();
	}else{
		parent.odin.info('请输入文件名！');
	}
}


function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}

function doCloseWin(){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.doHiddenPupWin();
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>