<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<head>

<odin:head />
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<div id="groupTreeContent" >
<div id="groupTreePanel"></div>
<odin:groupBox title="�ɲ���Ϣ����" property="ggBox">
<form name="excelForm" action="<%=request.getContextPath()%>/ZhgbFileServlet" id="excelForm" method="post"  enctype="multipart/form-data" >
	<table >
		<tr>
			<odin:select2 property="importType" editor="true" size="30"  label="��������"/>
			<odin:textEdit width="720" inputType="file" colspan="4"  property="importExcle" label="ѡ���ļ�" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="startNumber" size="33" label="�������ʼ�У��������Ҫ�������ݵ�excel������"/>
			<odin:textEdit property="rowNumber" size="33" label="�����������"/>
		</tr>
<%--		<tr>--%>
<%--			<td colspan="4" align="center">--%>
<%--				<odin:button text="&nbsp;&nbsp;��&nbsp;��&nbsp;&nbsp;" property="impBtn" handler="formSubmit" />--%>
<%--			</td>--%>
<%--		</tr>--%>
	</table>
	</odin:groupBox>
</form>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>�ɲ����ݵ�����Ϣ��</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="excel��������" icon="images/icon/exp.png" id="impExcel" handler="impExcel"/>
	<odin:separator></odin:separator>
</odin:toolBar>
</body>
<script type="text/javascript">
	function impExcel(){
		formSubmit();
	}
	function formSubmit(){
		var file = document.getElementById('importExcle').value;
		var startNumber = document.getElementById('startNumber').value;
		let importType = document.getElementById('importType').value;
		let rowNumber = document.getElementById('rowNumber').value;

		if(file !=""){
			odin.ext.Ajax.request({
				url: '<%=request.getContextPath()%>/ZhgbFileServlet?startNumber='+startNumber+'&importType='+importType+'&rowNumber='+rowNumber,
				isUpload: true,
				method: 'post',
				fileUpload: true,
				form: 'excelForm',
				success: function () {
					/* parent.odin.alert("���������ɹ�!"); */
					// window.parent.realParent.location.replace(window.parent.realParent.location);
					odin.alert("�ɹ�����")
				}
			});
		} else {
			odin.info('�ļ������ϴ���');
		}
	}

</script>