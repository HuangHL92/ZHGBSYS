<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<ss:hlistDiv id="1" cols="6" p="H">
	<ss:textEdit property="aae135_a" label="���֤"/>
	<ss:textEdit property="aac003_a" label="����"/>
	<ss:textEdit property="eac213_a" label="����ƴ��"/>
	<ss:numberEdit property="aac001_a" label="��Ա����" p="H" />
</ss:hlistDiv>

<odin:gridSelectColJs name="eab216" codeType="eab216"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aac004" codeType="AAC004"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aac005" codeType="AAC005"></odin:gridSelectColJs>

<odin:editgrid property="div_2"  pageSize="150"  bbarId="pageToolBar"  isFirstLoadData="false"  url="/" title=""  height="200" load="loadStore" sm="row" rowDbClick="rDbClick" autoFill="false">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		
		<odin:gridDataCol name="aac001" />
		<odin:gridDataCol name="aae135" />
		<odin:gridDataCol name="aac003" />
		<odin:gridDataCol name="aac004" />
		<odin:gridDataCol name="aac006" />
		<odin:gridDataCol name="eab216" />
		<odin:gridDataCol name="aac005" />
		<odin:gridDataCol name="aac009" />
		<odin:gridDataCol name="aac010" />
		<odin:gridDataCol name="aae006" />
		<odin:gridDataCol name="aae005" />
		<ss:gridDataBatchCol keyCol="aae140" batchFilter="aaa102 in('01','02','03','04','05','06')" valueCol="aac008"></ss:gridDataBatchCol>
		<odin:gridDataCol name="eac101" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<ss:gridEditColumn header="��Ա����" width="50" dataIndex="aac001"
			editor="number" p="H"/>
		<ss:gridEditColumn header="���֤����" width="160" dataIndex="aae135"
			editor="text"/>
		<ss:gridEditColumn header="����" width="100" dataIndex="aac003"
			editor="text" />
		<ss:gridEditColumn header="�Ա�" width="45" dataIndex="aac004"
			editor="select" codeType="AAC004"/>
		<ss:gridEditColumn header="��������" width="100" dataIndex="aac006"
			editor="date" />
		<ss:gridEditColumn header="���״̬" width="100" dataIndex="eab216"
			editor="select" codeType="EAB216"/>
		<ss:gridBatchCol editor="select" keyCol="aae140" batchFilter="aaa102 in('01','02','03','04','05','06')" valueCol="aac008" width="100"/>	
		<ss:gridEditColumn header="����" width="50" dataIndex="aac005"
			editor="select" codeType="AAC005"/>
		<ss:gridEditColumn header="��������" width="700" dataIndex="aac009"
			editor="text" />
		<ss:gridEditColumn header="�������ڵ�" width="200" dataIndex="aac010"
			editor="text" />
		<ss:gridEditColumn header="�־�ס��ַ" width="200" dataIndex="aae006"
			editor="text" />
		<ss:gridEditColumn header="��ϵ�绰" width="100" dataIndex="aae005"
			editor="text" />	
		<ss:gridEditColumn header="�ֻ�" width="100" dataIndex="eac101"
			editor="text" isLast="true" />
	</odin:gridColumnModel>		
</odin:editgrid>


<script>
isQueryDiv='div_2';
function loadStore(){
	var g=odin.ext.getCmp('div_2');
	var s=g.store;
	var c=s.getCount();
	switch(c){
	case 0: 
			//parent.radow.cm.setValid(parent.commParams.currentColumn,false);
			//parent.odin.unmask();
			parent.radow.cm.doCheck("psidnew");
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