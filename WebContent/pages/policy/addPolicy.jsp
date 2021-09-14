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

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=addPolicy" enctype="multipart/form-data" >	
<odin:hidden  property="id" />
<odin:hidden  property="secret" />

<table id="tablef"  >
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit property="title" label="����" width="400" maxlength="100"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:select property="secret" label="����" data="['01','�����쵼�������ν���'],['02','�����쵼���ӹ滮��Ҫ'],['03','��������ɲ�����'],['04','���ڵ���ɲ���Ů�ɲ�����'],['05','���ڼ����ɲ�������Ϊ'],['06','���ڸɲ����˹���'],['07','��������ɲ����齨��'],['08','��������']" width="100" maxlength="40"/>
		
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFile" label="ѡ���ļ�" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">
			<table>
				<tr>
					<td width="120px"></td>
					<td>
						<odin:button text="����" property="impBtn" handler="formSubmit" />
					</td>
					<td width="60px"></td>
					<td>
						<odin:button text="ȡ��" property="cancelBtn" handler="doCloseWin"></odin:button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
	
</form>

<script>
Ext.onReady(function (){
	
})



function doCloseWin(){
	parent.odin.ext.getCmp('addWin').close();
}

function formSubmit(){
	
	var file = document.getElementById('excelFile').value;
	
	if(file !=""){
		var title = encodeURI(document.getElementById('title').value);
		var secret = encodeURI(document.getElementById('secret').value);
		//odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addPolicy&title='+encodeURI(title)+'&secret='+encodeURI(secret),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'excelForm',
			success:function(){
				parent.odin.alert("�������߷���ɹ�!");
				//Ext.getBody().unmask();//ȥ��MASK  
				realParent.odin.ext.getCmp('policySetgrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('�ļ������ϴ���');
	}
	
}




</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>