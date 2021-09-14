<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFilePsn" enctype="multipart/form-data" >	
<odin:hidden  property="valueimp" />
<odin:hidden  property="uuid" />
<odin:hidden  property="filename" />
<odin:hidden  property="count1" />
<odin:hidden  property="type1" />
<odin:hidden property="gjgss" />

<table id="tablef"  >
	<tr>
		
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFile" label="ѡ���ļ�" onchange="return FileUpload_onselect()" ></odin:textEdit> 
	</tr>
	<tr>
		<odin:textEdit property="packagetype" readonly="true" label="���ݰ�����" ></odin:textEdit>
		<odin:textEdit property="packagetime" readonly="true" label="���ݰ�����ʱ��"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="psncount" readonly="true" label="��������" />
		<odin:textEdit property="gjgs"  readonly="true" label="�����ʽ" ></odin:textEdit>
	</tr>
	 
	<tr>
		<odin:textarea property="orginfo" rows="3" colspan="4" cols="4" style="width:416;" readonly="true" readonly="true"  label="���ݰ����" />
	</tr>
	
</table>
<table>
				<tr>
					<odin:select2 property="a0165" codeType='ZB130' label="������Ա���:" ></odin:select2>
					<td><font style="font-size: 12">�����Ͻ����ᱻ����</font></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" checked="checked" id="fxz" /><span style="font-size: 12">����ְ��Ա</span></td>
				</tr>
				<tr>
					<td></td><td></td><td></td>
					<td>
						<odin:button text="������ʱ��" property="impBtn" handler="formSubmit2" />
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
			}
		});
	});
	odin.ext.get('btn').on('click',function(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='2'){
				alert("��֤��ͨ�������ܵ���!");
				return;
			} else if(v=='3'){
				alert("ֻ֧��HZB��ʽ�ļ������ܵ���");
				return;
			} else if(v==''){
				alert("��ѡ���ļ������");
				return;
			}
			odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
			document.excelForm.submit();
		}else{
			odin.info('��ѡ���ļ�֮���������봦��');
		}
	});*/
	function formupload(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='3'){
				alert("ֻ֧��HZB��7z��ʽ�ļ������ܵ���");
				return;
			}
			odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFilePsn',
				isUpload:true,
				form:'excelForm',
				success:function(){
				}
			});
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
 	var filename=document.getElementById('filename').value;
 	var uuid= document.getElementById('uuid').value;
 	var v = odin.ext.get('valueimp').getValue();
 	if(uuid==''||filename==''){
 		alert('���Ƚ��С��ϴ���顱��');
 		return;
 	}
 	if(v=='2'){
		alert("��֤��ͨ�������ܵ��롣");
		return;
	} else if(v=='3'){
		alert("ֻ֧��HZB��7z��zip��ʽ�ļ������ܵ��롣");
		return;
	} else if(v==''){
		alert("�ϴ����δͨ����");
		return;
	}
	
	var count2 = document.getElementById("count1").value;
	var type2 = document.getElementById("type1").value;
	var psncount1 = document.getElementById("psncount").value;
	var a0165 = document.getElementById('a0165').value;
	var fxz = document.getElementById('fxz').checked;
	if(type2 == "MYSQL" && Number(count2)+Number(psncount1)>100000){
		alert("������Ѿ�����"+count2+"�˵����ݣ���ǰ���ݰ��ڰ���"+psncount1+"�˵����ݣ���������������泬��10�������ݻᵼ�����л���");
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/ZjUploadFileServlet?method=zzbFileImpPsn&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename,'a0165':a0165,'fxz':fxz},
				success:function(){
					realParent.odin.ext.getCmp('MGrid').store.reload();
					parent.Ext.getCmp(subWinId).close(); 
				}
			});
		realParent.$h.openWinMin('simpleExpWin4','pages.repandrec.local.KingbsWinfresh&initParams='+ uuid,'���鴰��',500,240,uuid,'<%=request.getContextPath()%>');
	
	}else{
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/ZjUploadFileServlet?method=zzbFileImpPsn&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename,'a0165':a0165,'fxz':fxz},
				success:function(){
					realParent.odin.ext.getCmp('MGrid').store.reload();
					parent.Ext.getCmp(subWinId).close();
				}
			});
		realParent.$h.openWinMin('simpleExpWin4','pages.repandrec.local.KingbsWinfresh&initParams='+ uuid,'���鴰��',500,240,uuid,'<%=request.getContextPath()%>');
		}
	}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
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
	parent.odin.ext.getCmp('simpleExpWin2').close();
}

function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
    var param = {};
	param.path = path;
	param.isPerson = '1';
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck3', param,succfun,failfun,false,false);
}

	function succfun(response){
 		//����һ������
 		Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		document.getElementById('valueimp').value=response.data.valueimp;
 		
 		//ֱ�Ӵ����ύ
 		formupload();
 	}
 	function failfun(rm){
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