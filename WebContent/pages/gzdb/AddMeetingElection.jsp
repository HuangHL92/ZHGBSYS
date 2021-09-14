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
<%
	String xjqy=request.getParameter("xjqy");
%>
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection" enctype="multipart/form-data" >	
<odin:hidden  property="xjqy" />
<table id="tablef"  >
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:select property="hylx" label="��������" data="['01','������'],['02','�˴���'],['03','��Э��']" width="100" />
		
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit property="hymc" label="��������" width="400" maxlength="100"/>
		
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit property="sj" label="ʱ��" width="400" maxlength="100"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<%-- <tr>
		<odin:select property="wcbz" data="['01','δ���'],['02','�ƽ���'],['03','�����']" label="���" width="100" />
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr> --%>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFile" label="ѡ�ٲ����ļ�" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFilej" label="ѡ�ٽ���ļ�" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFileg" label="��������ļ�" ></odin:textEdit> 
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
	var file1 = document.getElementById('excelFileg').value;
	var file2 = document.getElementById('excelFilej').value;
	
	if(file !=""){
		var hylx = encodeURI(document.getElementById('hylx').value);
		var hymc = encodeURI(document.getElementById('hymc').value);
		var xjqy = '<%=xjqy %>';
		var sj = encodeURI(document.getElementById('sj').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&hylx='+encodeURI(hylx)+'&hymc='+encodeURI(hymc)+'&xjqy='+encodeURI(xjqy)+'&sj='+encodeURI(sj),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'excelForm',
			success:function(){
				parent.odin.info("�������ѡ�ٳɹ�!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
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