<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>

<odin:head/>
<odin:MDParam></odin:MDParam>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadLrmsServlet" enctype="multipart/form-data" target="impFrame">	
<table >
	<tr>
		<td height="20"></td>
	</tr>
	<tr >
		<!-- <td ><label style="font-size: 12" >文件选择</label></td> -->
		<odin:textEdit inputType="file" property="normalFile"  name="normalFile" label="&nbsp&nbsp&nbsp文件选择" size="47" ></odin:textEdit>
	</tr>
<%-- 	<tr >
		<!-- <td ><label style="font-size: 12" >照片选择</label></td> -->
		<odin:textEdit inputType="file" property="pic" name="pic" size="47"  label="照片选择"></odin:textEdit>
	</tr>	 --%>
	
	<tr></tr>
	<tr id = "tr1" align="left">
	       <!-- <td><label style="font-size: 12" >导入机构</label></td> -->
	       <tags:PublicTextIconEdit3 property="a0201b" codetype="orgTreeJsonData" readonly="true" required="true" label="导入机构" width="330"/>
	</tr>
	<tr>
		<td></td>
		<td align="center" colspan="2" style="position: relative;margin-left: 30px;">
			<odin:button text="&nbsp;&nbsp;导&nbsp;&nbsp;入&nbsp;&nbsp;" property="impBtn" handler="formSubmit"></odin:button>
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});

document.onkeydown=function() {
	if(event.keyCode == 27){	//禁用ESC
        return false;   
	}
};

function formSubmit(){
	var lrmOrlrmx =document.getElementById('normalFile').value;
//	var pic =document.getElementById('pic').value;
	var a0201b =document.getElementById('a0201b').value;
	if(lrmOrlrmx.length<1){
		odin.alert("请选择导入数据");
		return;
	}
	if(lrmOrlrmx.length>1){
		lrmOrlrmx=lrmOrlrmx.substr(lrmOrlrmx.length-4,lrmOrlrmx.length);
		if(lrmOrlrmx=='.zip'){
		}else {
			odin.alert("仅支持zip文件格式导入！");
			return;
		}
	}
	if(!(a0201b.length>1)){
		odin.alert("请选择导入机构！");
		return;
	}
/* 	if(pic.length>1){
		pic=pic.substr(pic.length-4,pic.length);
		if(pic!='.pic'){
			alert("仅支持pic文件格式导入！");
			return;
		}
	} */
	
		if(document.getElementById('normalFile').value!=""){

			//alert(document.getElementById('excelFile').value);
			//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			//document.excelForm.submit();
<%-- 			Ext.Ajax.request({  
	            url : "<%=request.getContextPath()%>/UpLoadLrmsServlet",  
	            isUpload : true,  
	            form : "excelForm",  
	            success : function(response) {  
	                eval(response.responseText);  
	            }  
	        }); --%>
	        updateUpLoad();
		}
	
}

function updateUpLoad(value){
	var a0201b = document.getElementById('a0201b').value;
	$h.confirm3btn('系统提示（重要）','如果系统发现任免表人员与本库人员信息重复，是否还执行追加？<br/>'+
			'选择“是”，重复人员将继续重复追加；<br/>选择“否”，重复人员被跳过，不进行追加；<br/>选择“取消”，不进行任何操作',200,function(id){
		if(id=='yes'){
			Ext.Msg.wait("正在导入，请稍后...","系统提示");
			Ext.Ajax.request({  
		        url : "<%=request.getContextPath()%>/UpLoadLrmsServlet?method=Again&a0201b="+a0201b,  
		        isUpload : true,  
		        form : "excelForm",  
		        success : function(response) {  
		            //eval(response.responseText);  
		        	Ext.Msg.hide();
		        	parent.odin.alert('导入成功');
		        	parent.odin.ext.getCmp('importLrmWins').hide();
		        	parent.reloadGrid();
		        }  
		    });
		}else if(id=='no'){
				Ext.Msg.wait("正在导入，请稍后...","系统提示");
				Ext.Ajax.request({  
			        url : "<%=request.getContextPath()%>/UpLoadLrmsServlet?a0201b="+a0201b,  
			        isUpload : true,  
			        form : "excelForm",  
			        success : function(response) {  
			            //eval(response.responseText);  
			        	Ext.Msg.hide();
			        	parent.odin.alert('导入成功');
			        	parent.odin.ext.getCmp('importLrmWins').hide();
			        	parent.reloadGrid();
			        }  
			    });
			}else if(id=='cancel'){
			
			}
		});
	<%-- if(confirm("已存在【"+value+"】的任免表，是否覆盖？")){
		Ext.Ajax.request({  
	        url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet?method=Update",  
	        isUpload : true,  
	        form : "excelForm",  
	        success : function(response) {  
	            eval(response.responseText);  
	        }  
	    });
	}else{
		Ext.Ajax.request({  
	        url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet?method=Again",  
	        isUpload : true,  
	        form : "excelForm",  
	        success : function(response) {  
	            eval(response.responseText);  
	        }  
	    });
	} --%>

}


</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>