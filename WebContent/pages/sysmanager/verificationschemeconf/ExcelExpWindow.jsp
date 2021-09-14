<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导出excel文件</title>
<odin:head />
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y: hidden; overflow-x: hidden">
	<odin:base>
		<form name="simpleExcelForm" method="post"
			action="<%=request.getContextPath()%>/CustomExcelServlet?method=VerificationSchemeExcelExp"
			target="simpleExpFrame">
			<table align="center" width="96%">
				<tr>
					<td height="20" colspan="2"></td>
				</tr>
				<tr>
					<odin:textEdit property="fileName"  label="" colspan="4" 
						width="390" title="要导出的excel文件的名称"></odin:textEdit>
				</tr>
				<tr>
					<td><!-- <span style=font:12>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保护密码</span> --></td>
					<td><!-- <input name="passwd" type="password" class="btn" size="30" > --></td>
					<td align="center" colspan="6"><img
						src="<%=request.getContextPath()%>/images/daochu.gif"
						onclick="formSubmit()"> <!-- &nbsp;&nbsp;<img src="../../images/qingkong.gif" onclick="document.all('excelFile').value='';"> -->
					</td>
				</tr>
				<tr>
					<td height="2" colspan="2"></td>
				</tr>
				<tr>
					
				</tr>
				<tr>
					<td height="6" colspan="2"></td>
				</tr>
			</table>
			<input type="hidden" name="businessClass"
				value="<%=(request.getParameter("businessClass") == null
						? ""
						: request.getParameter("businessClass"))%>" />
			<input type="hidden" name="vsc001Exp" id="vsc001Exp" value="" />
		</form>
		<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0"></iframe>

		<script>
			/** 首次载入列表数据开始 */
			Ext
					.onReady(function() {
						if (typeof (parent.vsc001Exp) != 'undefined') {
							document.getElementById("vsc001Exp").value= parent.vsc001Exp;
							//document.simpleExcelForm.vsc001Exp.value = parent.vsc001Exp;
							//alert(document.getElementById("vsc001Exp").value);
						}
						if (typeof (parent.fileName) != 'undefined') {
							document.simpleExcelForm.fileName.value = parent.fileName;
							if(document.simpleExcelForm.fileName.value == ''){
								odin.ext.getCmp('fileName').hide();
							}
						}
					});

			function openChooseFileWin() {
				document.all.excelFile.click();
			}

			function formSubmit() {
				//if (document.all('fileName').value != "") {
					//alert(document.all('excelFile').value);
					odin.ext.get(document.body).mask('正在导出数据中......',
							odin.msgCls);
					document.simpleExcelForm.submit();
					parent.odin.ext.getCmp('expExcelWin').hide();
					/*}  else {
					odin.info('请输入文件名！');
				} */
			}
		</script>

	</odin:base>
	<odin:response onSuccess="doSuccess" />
</body>
</html>