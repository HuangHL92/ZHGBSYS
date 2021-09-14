<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadHelpFileServlet" enctype="multipart/form-data" target="impFrame">	
<odin:hidden  property="valueimp" />
<table width="96%">
	<tr>
		
	</tr>
	<%-- 
	<tr>
		
		<odin:select property="a0165" label="�������Ϊ" codeType="ZB130"></odin:select> <td width="150" style="{font :12px}">����Ա�����ᱻ�滻</td>
	</tr>
	--%>
	<tr>
	<td style="{font :12px}" align="right">�ļ�ѡ��  </td>
		<td colspan="3"><div class="x-form-item"><input type="file" id="excelFile" name="excelFile" size="47" onchange="return FileUpload_onselect()" /></div></td>
	</tr>
	<tr>
		<odin:textEdit property="packagetype" label="���ݰ�����" ></odin:textEdit>
		<odin:textEdit property="packagetime" label="���ݰ�����ʱ��"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="b0101" label="��������" size="78" colspan="6" />
	</tr>
	<tr>
		<odin:textEdit property="b0111" label="��������" />
		<odin:select2 property="b0131" label="��������" codeType="ZB04"></odin:select2>
	</tr>
	<%-- 
	<tr>
		<odin:textEdit property="psnnumber" label="��������" />
		<odin:textEdit property="datetype" label="���ݰ��汾" />
	</tr>
	--%>
	<tr>
		<odin:textarea property="orginfo" rows="3" readonly="true" colspan="6" label="ƥ��������" />
	</tr>
	<tr>
		<td colspan="6">
			<table>
				<tr>
					
					<td width="20px"></td>
					<td>
					<odin:select2 property="gjgs" readonly="true" label="�����ʽ" data="['1','ep'],['2','zb3'],['3','dmp'],['4','lrm'],['5','lrmx'],['6','xls'],['7','zzb3'],['8','cjub3']"></odin:select2>
					</td>
					<td width="40px"></td>
					<td>
						<%--<odin:button text="����" property="impBtn" handler="formSubmit"></odin:button>--%>
						<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formSubmit()">
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

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>
<script>
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
function formSubmit(){
	if(document.getElementById('excelFile').value!=""){
		//alert(document.all('excelFile').value);
		var v = odin.ext.get('valueimp').getValue();
		if(v=='2'){
			alert("������֤��ͨ�������ܵ���!");
			return;
		} else if(v=='3'){
			alert("����ZB3��HZB��ʽ�ļ������ܵ���");
			return;
		}
		odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		document.excelForm.submit();
	//	parent.clickquery();
	//	parent.odin.ext.getCmp('simpleExpWin').hide();
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
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
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.clickquery();
	parent.odin.ext.getCmp('simpleExpWin').hide();
	//parent.resFuncImpExcel(odin.ext.decode(businessData));
}

function FileUpload_onselect(){
    var path=document.getElementById('excelFile').value;
    var param = {};
	param.path = path;
	
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck', param,succfun,failfun,false,false);
	   ///changeView();
    /**
    aa=path.split('.');
    document.all('TbxName').value=aa[aa.length-1];  //jpg ���
    var name;
    name=path.split('\\'); 
    var bb=name[name.length-1];             
    document.all('Tbx_FileName').value=bb.substr(0,bb.indexOf('.'));  //AddFile ���
    */
}

	function succfun(response){
 		//����һ������
 		Ext.getCmp('b0101').setValue(response.data.b0101);
 		
 		Ext.getCmp('packagetype').setValue(response.data.packagetype);
 		Ext.getCmp('packagetime').setValue(response.data.packagetime);
 		Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		Ext.getCmp('b0111').setValue(response.data.b0111);
 		document.getElementById('gjgs_combo').value=response.data.gjgs_combo;
 		document.getElementById('gjgs').value=response.data.gjgs;
 		document.getElementById('valueimp').value=response.data.valueimp;
 		document.getElementById('b0131').value=response.data.b0131;
 		Ext.getCmp('b0131_combo').setValue(response.data.b0131_combo);
 		//Ext.getCmp('b0131').setValue(response.data.b0131);
 		//Ext.getCmp('valueimp').setValue(response.data.valueimp);
 		//Ext.getCmp('gjgs').setValue(response.data.gjgs);
 		
 		
 		/**rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i]= new Ext.data.Record(response.data[i]);
    	}
 		var num=response.data.length;
 		if(num>1){
 			openWindow(response);
 		}else{
 			//g_areaName=rs[0].data.name;
 			//document.getElementById('areaName').innerText = g_areaName;
 		}*/
 	}
 	function failfun(rm){
 		alert("����ʧ��");
 	}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>