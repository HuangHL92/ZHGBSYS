<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:toolBar property="btnToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="ȷ��" id="save" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="backInfo">
	<table>
		<tr>
			<odin:textarea property="cbd_text" label="������" rows="5" cols="50"></odin:textarea>
		</tr>
		<tr>
			<odin:textEdit property="linkpsn" size="38" label="��ϵ��"/>
		</tr>
		<tr>
			<odin:textEdit property="linktel" size="38" label="��ϵ�绰"/>
		</tr>
		<tr>
			<odin:textarea property="remark" colspan="200" label="�� ע"/>
		</tr>
	</table>
</div>
<odin:hidden property="cbd_id"/>
<odin:hidden property="cbd_name"/>
<odin:hidden property="filePath"/>
<odin:hidden property="operateFlag"/>
<odin:panel contentEl="backInfo" property="backPanel" topBarId="btnToolBar"/>
<script>
function createCBDZip(cbd_text,cbd_name,cbd_id,filePath,linkpsn,linktel,remark,flag){
	url="<%=request.getContextPath()%>/CBDFiledownServlet?method=backByGP&cbd_id="+cbd_id+"&filePath="+filePath+"&cbd_name="+cbd_name+"&cbd_text="+cbd_text+"&linkpsn="+linkpsn+"&linktel="+linktel+"&remark="+remark;
    var iframe = document.createElement("iframe");
    iframe.src = url;
    iframe.style.display = "none";
    document.body.appendChild(iframe);
}

</script>