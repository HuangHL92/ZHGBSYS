<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>

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
		<!-- <td ><label style="font-size: 12" >�ļ�ѡ��</label></td> -->
		<odin:textEdit inputType="file" property="normalFile"  name="normalFile" label="&nbsp&nbsp&nbsp�ļ�ѡ��" size="47" ></odin:textEdit>
	</tr>
	<tr>
		<td></td>
		<td align="center" colspan="2" style="position: relative;margin-left: 30px;">
			<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" property="impBtn" handler="formSubmit"></odin:button>
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	<odin:hidden property="a0201b" value="001.002"/>
	<odin:hidden property="title_id" value=""/>
	<odin:hidden property="publish_id" value=""/>
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	var title_id="<%=request.getParameter("title_id")%>";
	var publish_id="<%=request.getParameter("publish_id")%>";
	document.getElementById('title_id').value=title_id;
	document.getElementById('publish_id').value=publish_id;
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});

document.onkeydown=function() {
	if(event.keyCode == 27){	//����ESC
        return false;   
	}
};

function formSubmit(){
	var lrmOrlrmx =document.getElementById('normalFile').value;
//	var pic =document.getElementById('pic').value;
	var a0201b =document.getElementById('a0201b').value;
	if(lrmOrlrmx.length<1){
		odin.alert("��ѡ��������");
		return;
	}
	if(lrmOrlrmx.length>1){
		lrmOrlrmx=lrmOrlrmx.substr(lrmOrlrmx.length-4,lrmOrlrmx.length);
		if(lrmOrlrmx=='.zip' || lrmOrlrmx=='lrmx'){
		}else {
			odin.alert("��֧��zip��lrmx�ļ���ʽ���룡");
			return;
		}
	}
	if(!(a0201b.length>1)){
		odin.alert("��ѡ���������");
		return;
	}
/* 	if(pic.length>1){
		pic=pic.substr(pic.length-4,pic.length);
		if(pic!='.pic'){
			alert("��֧��pic�ļ���ʽ���룡");
			return;
		}
	} */
	
		if(document.getElementById('normalFile').value!=""){

			//alert(document.getElementById('excelFile').value);
			//odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
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
	var title_id = document.getElementById('title_id').value;
	var publish_id = document.getElementById('publish_id').value;
	$h.confirm3btn('ϵͳ��ʾ����Ҫ��','���ϵͳ�����������Ա�뱾����Ա��Ϣ�ظ����Ƿ�ִ��׷�ӣ�<br/>'+
			'ѡ���ǡ����ظ���Ա�������ظ�׷�ӣ�<br/>ѡ�񡰷񡱣��ظ���Ա��������������׷�ӣ�<br/>ѡ��ȡ�������������κβ���',200,function(id){
		if(id=='yes'){
			Ext.Msg.wait("���ڵ��룬���Ժ�...","ϵͳ��ʾ");
			Ext.Ajax.request({  
		        url : "<%=request.getContextPath()%>/UpLoadLrmsshServlet?method=Again&a0201b="+a0201b+"&title_id="+title_id+"&publish_id="+publish_id,  
		        isUpload : true,  
		        form : "excelForm",  
		        success : function(response) {  
		            //eval(response.responseText);
		            if(publish_id!='null'){
		            	parent.odin.ext.getCmp('importLrmWinssh').hide();
			        	Ext.Msg.hide();
			        	alert('����ɹ�');
			        	parent.reloadSelData();
			        	parent.queryPerson();
		            }else{//ģ�����
		            	var t = response.responseText.replace('<pre>','')
		            	.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','')
		            	.replace('</pre>','');
		            	eval(t);
		            	parent.odin.ext.getCmp('importLrmWinmntp').close();
		            }
		        	
		        }  
		    });
		}else if(id=='no'){
				Ext.Msg.wait("���ڵ��룬���Ժ�...","ϵͳ��ʾ");
				Ext.Ajax.request({  
			        url : "<%=request.getContextPath()%>/UpLoadLrmsshServlet?a0201b="+a0201b+"&title_id="+title_id+"&publish_id="+publish_id,  
			        isUpload : true,  
			        form : "excelForm",  
			        success : function(response) {  
			            //eval(response.responseText);  
			        	if(publish_id!='null'){
			            	parent.odin.ext.getCmp('importLrmWinssh').hide();
				        	Ext.Msg.hide();
				        	alert('����ɹ�');
				        	parent.reloadSelData();
				        	parent.queryPerson();
			            }else{
			            	var t = response.responseText.replace('<pre>','').replace('</pre>','');
			            	
			            	eval(t);
			            	parent.odin.ext.getCmp('importLrmWinmntp').close();
			            }
			        }  
			    });
			}else if(id=='cancel'){
			
			}
		});
	<%-- if(confirm("�Ѵ��ڡ�"+value+"����������Ƿ񸲸ǣ�")){
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