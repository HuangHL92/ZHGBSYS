<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>

<style>
	.trh{
		display:none;
	}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">
</script>

<odin:hidden property="id"/>
<odin:hidden property="rbId"/>
<odin:editgrid property="peopleInfoGrid" title="��Ա��Ϣ��"
	autoFill="true" width="550" height="500" bbarId="pageToolBar"
	pageSize="20">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="personcheck" />
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="a0101" />
		<odin:gridDataCol name="a0104" />
		<odin:gridDataCol name="a0107" />
		<odin:gridDataCol name="a0117" />
		<odin:gridDataCol name="a0141" />
		<odin:gridDataCol name="a0192a" />
		<odin:gridDataCol name="a0184" />
		<odin:gridDataCol name="a0148" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="a0000" width="110" header="id"
			align="center" hidden="true" />
		<odin:gridColumn dataIndex="a0101" width="110" header="����"
			align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="100" header="�Ա�"
			align="center" editor="select" edited="false" codeType="GB2261" />
		<odin:gridColumn dataIndex="a0107" width="130" header="��������"
			align="center" editor="text" edited="false" />
		<odin:gridEditColumn2 dataIndex="a0117" width="130" header="����"
			align="center" editor="select" edited="false" codeType="GB3304" />
		<odin:gridEditColumn2 dataIndex="a0141" width="130" header="������ò"
			align="center" editor="select" edited="false" codeType="GB4762" />
		<odin:gridColumn dataIndex="a0192a" width="130" header="������λ��ְ��"
			align="center" />
		<odin:gridEditColumn2 dataIndex="a0148" width="130" header="ְ����"
			align="center" isLast="true" editor="select" edited="false"
			codeType="ZB09" />
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{data:[]}
	</odin:gridJsonData>
</odin:editgrid>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var viewSize = Ext.getBody().getViewSize();
	var peopleInfoGrid =Ext.getCmp('peopleInfoGrid');
	peopleInfoGrid.setHeight(viewSize.height);
	
	document.getElementById('rbId').value = parentParam.rb_id;
	document.getElementById('id').value = parentParam.id;
	
});

</script>


