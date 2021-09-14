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

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/servlet/UpLoadLrmServlet" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="96%">
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<!-- <td ><label style="font-size: 12" >文件选择</label></td>
		<td ><input type="file" id="normalFile" name="normalFile" size="47" accept=".lrm,.lrmx" /></td> -->
		<odin:textEdit inputType="file" property="normalFile"  name="normalFile" label="&nbsp&nbsp&nbsp文件选择" size="47" ></odin:textEdit>
	</tr>
	<tr>
		<!-- <td ><label style="font-size: 12" >照片选择</label></td>
		<td ><input type="file" id="pic" name="pic" size="47" accept=".pic" /></td> -->
		<odin:textEdit inputType="file" property="pic" name="pic" size="47"  label="照片选择"></odin:textEdit>
	</tr>	
	<tr></tr>
	<tr id = "tr1" align="left">
	       <!-- <td><label style="font-size: 12" >导入机构</label></td> -->
	       <tags:PublicTextIconEdit3 property="a0201b" codetype="orgTreeJsonData" readonly="true" required="true" label="导入机构" width="330"/>
	</tr>
	<tr> 
		<td id="td1" align="center" colspan="2" style="position: relative;"> 
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
	var pic =document.getElementById('pic').value;
	var a0201b =document.getElementById('a0201b').value;
	if(lrmOrlrmx.length<1&&pic.length<1){
		odin.alert("请选择导入数据");
		return;
	}
	if(lrmOrlrmx.length>1){
		lrmOrlrmx=lrmOrlrmx.substr(lrmOrlrmx.length-4,lrmOrlrmx.length);
		if(lrmOrlrmx=='.lrm'||lrmOrlrmx=='lrmx'){
		}else {
			odin.alert("文件仅支持lrm或lrmx文件格式导入！");
			return;
		}
	}
	if(pic.length>1){
		pic=pic.substr(pic.length-4,pic.length);
		if(pic!='.pic'&&pic!=".jpg"){
			odin.alert("照片仅支持pic和jpg文件格式导入！");
			return;
		}
	}
	if(!(a0201b.length>1)){
		odin.alert("请选择导入机构！");
		return;
	}
	Ext.Msg.wait("正在导入，请稍后...","系统提示");
	if(lrmOrlrmx=='.lrm'&&pic.length<1){
		/* var r = confirm("是否匹配lrm文件对应图片！");
		if(r){
			return;
		} */
/* 		$h.confirm3btn('系统提示','是否匹配lrm文件对应图片！',200,function(id){
			if(id=='yes'){
				return;
				}else if(id=='no'){ */
					if(document.getElementById('normalFile').value!=""){
						//alert(document.getElementById('excelFile').value);
						//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
						//document.excelForm.submit();
						Ext.Ajax.request({  
				            url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet",  
				            isUpload : true,  
				            form : "excelForm",  
				            success : function(response) {  
				            	var t = response.responseText.replace('<pre>','')
				            	.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','')
				            	.replace('</pre>','');
				            	eval(t);
				            }  
				        });
						//alert(Ext.form.BasicForm.getValues());
					}/* else{
						odin.info('请选择文件之后再做导入处理！');
					}
				}else if(id=='cancel'){
				return;
				}
			}); */
	}else{
		if(document.getElementById('normalFile').value!=""){
			//alert(document.getElementById('excelFile').value);
			//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			//document.excelForm.submit();
			Ext.Ajax.request({  
	            url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet",  
	            isUpload : true,  
	            form : "excelForm",  
	            success : function(response) {  
	            	var t = response.responseText.replace('<pre>','')
	            	.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','')
	            	.replace('</pre>','');
	            	eval(t);
	            }  
	        });
		}
	}
}

function updateUpLoad(value){
	odin.alert("该人员在库中已存在，请删除后再导入");
}

<%-- function updateUpLoad(value){
	$h.confirm('系统提示','已存在【'+value+'】的任免表，是否追加？',200,function(id){
		if(id=='yes'){
			Ext.Ajax.request({  
		        url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet?method=Update",  
		        isUpload : true,  
		        form : "excelForm",  
		        success : function(response) {  
		            eval(response.responseText);  
		        }  
		    });
			}else 
			if(id=='ok'){
				Ext.Ajax.request({  
			        url : "<%=request.getContextPath()%>/servlet/UpLoadLrmServlet?method=Again",  
			        isUpload : true,  
			        form : "excelForm",  
			        success : function(response) {  
			            eval(response.responseText);  
			        }  
			    });
			}else if(id=='cancel'){
			
			}
		});
	if(confirm("已存在【"+value+"】的任免表，是否覆盖？")){
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
	}

} --%>


</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>