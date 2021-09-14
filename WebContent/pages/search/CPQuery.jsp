<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<odin:gridSelectColJs name="aab301" codeType="AAB301"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aaf015" codeType="AAF015"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aab023" codeType="AAB023"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eab014" codeType="EAB014"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eab026" codeType="EAB026"></odin:gridSelectColJs>
<odin:editgrid property="div_2"  pageSize="150"  bbarId="pageToolBar"  isFirstLoadData="false"  url="/" title=""  height="280" load="loadStore" sm="row" rowDbClick="rDbClick">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="aaz001" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="aaf015" />
		<odin:gridDataCol name="aab023" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="aab030" />
		<odin:gridDataCol name="aaf015" />
		<odin:gridDataCol name="eab014" />
		<odin:gridDataCol name="eab026" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<odin:gridEditColumn header="��λ����" width="80" dataIndex="aaz001"
			editor="number" edited="false"/>
		<odin:gridEditColumn header="Ͻ��" width="80" dataIndex="aab301"
			editor="select" codeType="AAB301" edited="false"/>
		<odin:gridEditColumn header="�ֵ�" width="120" dataIndex="aaf015"
			editor="select" codeType="AAF015" edited="false"/>
		<odin:gridEditColumn header="���ܲ���" width="120" dataIndex="aab023"
			editor="select" codeType="AAB023" edited="false"/>
		<odin:gridEditColumn header="��λ����" width="80" dataIndex="aab001"
			editor="text" edited="false"/>
		<odin:gridEditColumn header="��λ����" width="80" dataIndex="aab004"
			editor="text" edited="false"/>
		<odin:gridEditColumn header="˰��" width="120" dataIndex="aab030"
			editor="text" edited="false"/>
		<odin:gridEditColumn header="����" width="120" dataIndex="eab014"
			editor="select" codeType="EAB014" hidden="true" />
		<odin:gridEditColumn header="״̬" width="120" dataIndex="eab026"
			editor="select" codeType="EAB026" isLast="true" edited="false"/>
	</odin:gridColumnModel>		
</odin:editgrid>
<ss:hlistDiv id="1" cols="6" >
	<ss:textEdit property="aab001_a" label="��λ����" p="H" />
	<ss:textEdit property="aab004_a" label="��λ����" p="H" />
	<ss:select property="aab301_a" label="Ͻ��" codeType="AAB301" p="H" />

	<ss:select property="aaf015_a" label="�ֵ�" codeType="AAF015" p="H" />
	<ss:select property="aab023_a" label="���ܲ���" codeType="AAB023" p="H" />
	<ss:select property="eab026_a" label="��λ״̬" codeType="EAB026" p="H" />

	<ss:textEdit property="eab212_a" label="��λ����ƴ��"  p="H"/>
	<ss:numberEdit property="aaz001_a" label="��λ����" p="H" />

</ss:hlistDiv>
<script>

isQueryDiv='div_2';
function loadStore(){
	var g=odin.ext.getCmp('div_2');
	var s=g.store;
	var c=s.getCount();
	switch(c){
	case 0: 
			parent.odin.error("��ᱣ��ϵͳ��û��Ҫ���ҵĵ�λ��");
			parent.radow.cm.setValid(parent.commParams.currentColumn,false);
			parent.odin.unmask();
			break;
	case 1:rDbClick(g,0);
			break;
	default:
		var windowId = "win_pup";
		var pupWindow = parent.Ext.getCmp(windowId);
		pupWindow.show();
		parent.odin.unmask();
		break;
	}
}
function rDbClick(g,i){
	var s=g.store;
	var r=s.getAt(i);
	fillParentData(r.data,parent.commParams.currentColumn);
}


</script>