<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>

<ss:hlistDiv id="div_1" cols="6" >
	<ss:textEdit property="aae135_a" label="���֤"/>
	<ss:textEdit property="aac003_a" label="����"/>
	<ss:textEdit property="eac001_a" label="���˱���"/>
	<ss:textEdit property="aaz501_a" label="����"/>
	<ss:textEdit property="eac213_a" label="����ƴ��"/>
	<ss:textEdit property="eab014_a" label="��λ����"/>
	<ss:numberEdit property="aaz001_a" label="��λ����"  p="H"/>
	<ss:numberEdit property="aac001_a" label="��Ա����" p="H" />
	<ss:textEdit property="flag" label="ˢ�±�־" p="H"/>
	<ss:textEdit property="iscpflag" label="�Ƿ����λ" p="H"/>
	
</ss:hlistDiv>
<odin:editgrid property="div_2"  pageSize="10"  bbarId="pageToolBar"  isFirstLoadData="false" autoFill="false" url="/" title=""  height="190" load="loadStore" sm="row" rowDbClick="rDbClick">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="aac001" />
		<odin:gridDataCol name="aaz001" />
		<odin:gridDataCol name="cpquery" />
		<odin:gridDataCol name="aae135" />
		<odin:gridDataCol name="aac003" />
		<odin:gridDataCol name="aac004" />
		<odin:gridDataCol name="eac001" />
		<odin:gridDataCol name="eac070" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aaf015" />
		<odin:gridDataCol name="aab023" />
		<odin:gridDataCol name="aac009" />
		<odin:gridDataCol name="eab014" />
		<odin:gridDataCol name="aac001" />
		<odin:gridDataCol name="eac157" />
		<odin:gridDataCol name="eac158" />
		<odin:gridDataCol name="aic020" />
		<odin:gridDataCol name="aac006" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="cpcombo" />
		<odin:gridDataCol name="rowstocol" />
		<ss:gridDataBatchCol keyCol="aae140" batchFilter="aaa102 in('11','31','21','41','51','61')" valueCol="aac008"></ss:gridDataBatchCol>
		<odin:gridDataCol name="eab219" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<ss:gridEditColumn header="��Ա����" width="50" dataIndex="aac001" editor="number" p="H"/>
		<ss:gridEditColumn header="��λ����" width="50" dataIndex="aaz001" editor="number" p="H"/>
		<ss:gridEditColumn header="��λ��ѯ" width="50" dataIndex="cpquery" editor="number" p="H"/>
		<ss:gridEditColumn header="���֤����"  dataIndex="aae135" editor="text" width="137"/>
		<ss:gridEditColumn header="����"  dataIndex="aac003" editor="text" width="90" p="D"/>
		<ss:gridEditColumn header="�Ա�" dataIndex="aac004" editor="select" codeType="AAC004" width="60" p="D"/>
		<ss:gridEditColumn header="���˱���" dataIndex="eac001" editor="text" width="80" p="D"/>
		<ss:gridBatchCol editor="select" keyCol="aae140" batchFilter="aaa102 in('11','31','21','41','51','61')" valueCol="aac008" width="100" p="D"/>
		<ss:gridEditColumn header="����" dataIndex="eac070" editor="select" width="70" codeType="EAC070" p="D"/>
		<%if(ObjectUtil.equals(request.getParameter("isCpflag"),"true")){ %>
			<ss:gridEditColumn header="Ͻ��" dataIndex="aab301" editor="select" codeType="AAB301" width="80" p="D"/>
			<ss:gridEditColumn header="��λ����" dataIndex="aab004" editor="text" width="135" p="D"/>
			<ss:gridEditColumn header="��λ����" dataIndex="aab001" editor="text" width="80" p="D"/>
			<ss:gridEditColumn header="�ֵ�" width="50" dataIndex="aaf015" editor="select" width="100" codeType="AAF015" p="D"/>
			<ss:gridEditColumn header="���ܲ���" width="70" dataIndex="aab023"
				editor="select" codeType="AAB023" width="90" p="D"/>
			<ss:gridEditColumn header="��������" width="60" dataIndex="aac009"
				editor="text" p="H"/>
			<ss:gridEditColumn header="��λ����" width="75" dataIndex="eab014"
				editor="text" p="H"/>
			<ss:gridEditColumn header="��Ա����" width="75" dataIndex="aac001"
				editor="number" p="H"/>
			<ss:gridEditColumn header="��Ա�����ϸ" width="75" dataIndex="eac157"
				editor="text" p="H"/>
			<ss:gridEditColumn header="��Ա������" width="75" dataIndex="eac158"
				editor="text" p="H"/>
			<ss:gridEditColumn header="�ϱ�����" width="75" dataIndex="aic020"
				editor="number" p="H"/>
		<%}else{%>
			<ss:gridEditColumn header="��λ����" width="160" dataIndex="aab001" editor="text" p="H"/>
			<ss:gridEditColumn header="��λ����" width="160" dataIndex="aab004" editor="text" p="H"/>
			<ss:gridEditColumn header="�α���λ" width="160" dataIndex="rowstocol" editor="text" p="D"/>
		<%}%>
		
		<ss:gridEditColumn header="��������" width="75" dataIndex="aac006" editor="text" p="H"/>	
		<ss:gridEditColumn header="��֯��������" width="75" dataIndex="eab219" editor="text" isLast="true" p="H"/>
	</odin:gridColumnModel>		
</odin:editgrid>


<script>
isQueryDiv='div_2';
function loadStore(){
	var g=odin.ext.getCmp('div_2');
	var s=g.store;
	var c=s.getCount();
	var errmg="��ᱣ��ϵͳ��û��Ҫ���ҵ���Ա��";
	switch(c){
	case 0: 
			parent.odin.error("��ᱣ��ϵͳ��û��Ҫ���ҵ���Ա��");
			parent.radow.cm.setValid(parent.commParams.currentColumn,false,errmg);
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
	if(document.getElementById("iscpflag").value== false || document.getElementById("iscpflag").value =='false' || document.getElementById("iscpflag").value==''){
		var params = {};
		params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+"" ;
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, successFunc, null, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		r.set('aab001',data[0].aaa102);
		r.set('cpquery',data[0].aaa102);
		r.set('aab004',data[0].aaa103);
		r.set('aaz001',data[0].aaz001);
		r.set('cpcombo',odin.ext.encode(data));
		
	}
	fillParentData(r.data,parent.commParams.currentColumn);
}
  function successFunc(responseTxt) { // �յ�ajax���óɹ��Ĵ�����
		//
	}


</script>