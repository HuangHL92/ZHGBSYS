<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>

<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile" enctype="multipart/form-data" >	
<odin:hidden  property="valueimp" />
<odin:hidden  property="uuid" />
<odin:hidden  property="filename" />
<odin:hidden  property="count" />
<odin:hidden  property="type11" />


<table width="96%">
	<tr>
		
	</tr>
	<tr>
	<td style="{font :12px}" align="right">�ļ�ѡ��  </td>
		<td colspan="3"><div class="x-form-item"><input type="file" id="excelFile" name="excelFile" size="47" onchange="return FileUpload_onselect()" /></div></td>
	</tr>
	<tr>
		<odin:textEdit property="packagetype" readonly="true" label="���ݰ�����" ></odin:textEdit>
		<odin:textEdit property="packagetime" readonly="true" label="���ݰ�����ʱ��"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="b0101" label="��������" readonly="true" size="78" colspan="6" />
	</tr>
	<tr>
		<odin:textEdit property="b0111" readonly="true" label="��������" />
		<odin:textEdit property="psncount" readonly="true" label="��������" />
	</tr>
	<tr>
		<odin:textEdit property="b0131_combo" readonly="true" label="��������" />
		<odin:hidden property="b0131" />
	</tr>
	<tr>
		<odin:textEdit property="linkpsn" label="��ϵ��" readonly="true" />
		<odin:textEdit property="linktel" label="��ϵ�绰" readonly="true" />
		<%-- <odin:select2 property="b0131" label="��������" codeType="ZB04"></odin:select2>--%>
	</tr>
	<tr>
		<odin:textarea property="orginfo" rows="3" readonly="true" colspan="6" label="ƥ��������" />
	</tr>
	<tr>
		<td colspan="6">
			<table>
				<tr>
					
					<td width="20px"></td>
					<td>
					<odin:textEdit property="gjgs_combo" readonly="true" label="�����ʽ" />
					<odin:hidden property="gjgs" />
					</td>
					<td width="40px"></td>
					<td>
						<odin:button text="����У��" property="impwBtn" handler="formupload" />
					</td>
					<td width="20px"></td>
					<td>
						<%-- <input type="button" id="btn" value="�ϴ�"/>--%>
						<odin:button text="����" property="impBtn" handler="formSubmit2" />
					</td>
					<td width="20px"></td>
					<td><odin:button text="ȡ��" property="cancelBtn" handler="doCloseWin1"></odin:button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
</table>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<script>
Ext.onReady(function (){
	Ext.getCmp('impBtn').setDisabled(true); 
})
	//ajax �ϴ�
	/*odin.ext.get('btn').on('click',function(){
		alert();
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/UploadHelpFileServlet?method=zzbFile',
			isUpload:true,
			form:'excelForm',
			success:function(){
				FileUpload_onselect();
				///Ext.Msg.alert('upfile','�ļ��ϴ��ɹ���');
			}
		});
	});
	odin.ext.get('btn').on('click',function(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='2'){
				alert("������֤��ͨ�������ܵ���!");
				return;
			} else if(v=='3'){
				alert("����ZB3��HZB��ʽ�ļ������ܵ���");
				return;
			} else if(v==''){
				alert("��ѡ���ļ������");
				return;
			}
			odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
			document.excelForm.submit();
			///odin.ext.get(document.body).unmask(); 
		}else{
			odin.info('��ѡ���ļ�֮���������봦��');
		}
	});*/
	function formupload(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='3'){
				alert("����ZB3��HZB��ʽ�ļ������ܵ���");
				return;
			}
			odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile',
				isUpload:true,
				form:'excelForm',
				success:function(){
					///FileUpload_onselect();
					///Ext.Msg.alert('upfile','�ļ��ϴ��ɹ���');
				}
			});
			//document.excelForm.submit();
			///odin.ext.get(document.body).unmask(); 
		}else{
			odin.info('��ѡ���ļ�֮���������봦��');
		}
	}

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function openChooseFileWin(){
	document.getElementById('excelFile').click();
}
function formSubmit2(){
 	var filename = document.getElementById('filename').value;
 	var uuid = document.getElementById('uuid').value;
 	var v = odin.ext.get('valueimp').getValue();
 	if(uuid==''||filename==''){
 		alert('���Ƚ��С��ϴ���顱��');
 		return;
 	}
 	if(v=='2'){
		alert("������֤��ͨ�������ܵ��롣");
		return;
	} else if(v=='3'){
		alert("����ZB3��HZB��ʽ�ļ������ܵ��롣");
		return;
	} else if(v==''){
		alert("�ϴ����δͨ����");
		return;
	}
	var count = document.getElementById('psncount').value;
	var count1 = document.getElementById('count').value;
	var type11 = document.getElementById('type11').value;
	var count2 =  (Number(count)+Number(count1));
	if(type11 == "MYSQL" && count2>100000){
		alert("������Ѿ�����"+count1+"�˵����ݣ���ǰ���ݰ��ڰ���"+count+"�˵����ݣ���������������泬��10�������ݻᵼ�����л���");
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		////odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param,succfun1,failfun1,false,false);
		///odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param);
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImp&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
///					realParent.odin.ext.getCmp('simpleExpWin').store.reload();
					realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
					///FileUpload_onselect();
					///Ext.Msg.alert('upfile','�ļ��ϴ��ɹ���');
				}
			});
///		realParent.odin.ext.getCmp('simpleExpWin').hide();
///		parent.doOpenPupWin(contextPath + "/pages/repandrec/local/KingbsWinfresh1.jsp&id=" + uuid, "��������", 550, 300, null);
		realParent.$h.showWindowWithSrc('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'��������',500,150);
			
	}else{
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		////odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param,succfun1,failfun1,false,false);
		///odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param);
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImp&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
///					realParent.odin.ext.getCmp('simpleExpWin').store.reload();
					realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
					///FileUpload_onselect();
					///Ext.Msg.alert('upfile','�ļ��ϴ��ɹ���');
				}
			});
///		realParent.odin.ext.getCmp('simpleExpWin').hide();
///		parent.doOpenPupWin(contextPath + "/pages/repandrec/local/KingbsWinfresh1.jsp&id=" + uuid, "��������", 550, 300, null);
		realParent.$h.showWindowWithSrc('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'��������',500,150);

	}


}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
	/*
	if(type==1){
		odin.info('�����ѳɹ��ϴ���������ϣ�',doCloseWin);
	}else if(type==2){
		odin.info('ʧ�ܣ�',doCloseWin);
	}else if(type==3){
		odin.info('ҵ�������쳣��',doCloseWin);
	}else if(type==4){
		odin.info('����ҵ�����쳣��',doCloseWin);
	}
	*/
	doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.odin.ext.getCmp('simpleExpWin').hide();
	if(businessData!=""&&type==1){
		if(businessData.indexOf("\n")>=0){
			parent.odin.alert("��������ʧ�ܣ����ݸ�ʽ�Ƿ���");
			return;
		}
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
function doCloseWin1(type){
	//odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	//parent.clickquery();
	parent.odin.ext.getCmp('simpleExpWin11111').close();
	//parent.resFuncImpExcel(odin.ext.decode(businessData));
}

function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
    var param = {};
	param.path = path;
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck', param,succfun,failfun,false,false);
}

	function succfun(response){
 		//����һ������
 		Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		document.getElementById('valueimp').value=response.data.valueimp;
 	}
 	function failfun(response){
 		alert("����ʧ��");
 	}
 	
 	function succfun1(){
 		//����һ������
 	}
 	function failfun1(){
 		
 	}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>