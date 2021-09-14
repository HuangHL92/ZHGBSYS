<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>

<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
 #daoru{position:relative;left:20px;width:790} 
</style>
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=uploadHzbMakeFile" enctype="multipart/form-data" >	
<odin:hidden  property="orginfo" />
<odin:hidden  property="valueimp" />
<odin:hidden  property="uuid" />
<odin:hidden  property="filename" />
<odin:hidden  property="count" />
<odin:hidden  property="type11" />
<odin:groupBox title="数据导入" property="daoru">
<table>
<tr>
	<td>
	<odin:textEdit width="700" inputType="file" colspan="3"  property="excelFile" label="选择文件:" onchange="return FileUpload_onselect()" ></odin:textEdit> 
	</td>
</tr>
	
</table>	
</odin:groupBox>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<script>
Ext.onReady(function (){
//	Ext.getCmp('impBtn').setDisabled(true); 
})
	function formupload(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='3'){
				alert("不是HZB格式文件，不能导入");
				return;
			}
			//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			parent.Ext.Msg.wait('正在上传，请稍候...','系统提示：');
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=uploadHzbOldFile',
				isUpload:true,
				form:'excelForm',
				success:function(){
					parent.Ext.Msg.hide();
				},
				failure : function(){
					parent.Ext.Msg.hide();
				}
			});
			//document.excelForm.submit();
			///odin.ext.get(document.body).unmask(); 
		}else{
			odin.info('请选择文件之后再做导入处理！');
		}
	}

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function openChooseFileWin(){
	document.getElementById('excelFile').click();
}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
	doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('simpleExpWin').hide();
	if(businessData!=""&&type==1){
		if(businessData.indexOf("\n")>=0){
			parent.odin.alert("解析数据失败，数据格式非法！");
			return;
		}
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
function doCloseWin1(type){
	//odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	//parent.clickquery();
	parent.odin.ext.getCmp('simpleExpWin11111').close();
	//parent.resFuncImpExcel(odin.ext.decode(businessData));
}

function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
    var param = {'isPerson':'1'}; //之上传汇总版文件，跟人员上传。
	param.path = path;
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck', param,succfun,failfun,false,false);
}

	function succfun(response){
 		//构建一个数组
 		//Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		document.getElementById('orginfo').value=response.data.orginfo;
 		document.getElementById('valueimp').value=response.data.valueimp;
 		
 		//直接出发提交
 		formupload();
 	}
 	function failfun(response){
 		alert("操作失败");
 	}
 	
 	function succfun1(){
 		//构建一个数组
 	}
 	function failfun1(){
 		
 	}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>