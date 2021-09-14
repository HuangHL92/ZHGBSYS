<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<script  type="text/javascript">
//alert(parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById("a0201b").value);

function InitGrid(){
	var grid_A01 = odin.ext.getCmp('gridA01');
	//�����ģʽ
	var count = grid_A01.colModel;
	//���˳���Ӧ����
	var col = count.getColumnById(2);
	col.sortable=false;
	col.menuDisabled=true;  //��С��ͷȥ��
	
	radow.doEvent('gridA01.dogridquery',"0","200");
} 


//����
function UpBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	
	if (sm.length<=0){
		Ext.Msg.alert('ϵͳ��ʾ','��ѡ����Ҫ�������Ա��');
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		Ext.Msg.alert('ϵͳ��ʾ','����Ա�Ѿ�������ϣ�');
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������
	grid.getView().refresh();
}

//����
function DownBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		Ext.Msg.alert('ϵͳ��ʾ','��ѡ����Ҫ�������Ա��');
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		Ext.Msg.alert('ϵͳ��ʾ','����Ա�Ѿ���������£�');
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}

</script>

<div style="width:98%">
<odin:hidden property="a0201b" title="����id"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a0101" title="����"/>
<odin:hidden property="a0225" title="����������"/>
<script type="text/javascript">

var a0000 = document.getElementById('subWinIdBussessId').value;
Ext.onReady(function(){
	
	document.getElementById("a0201b").value=realParent.document.getElementById("a0201b").value;
	
	var a0201b_combo = realParent.document.getElementById("a0201b_combo").value;
	
	document.getElementById("a0201b_combo").innerText = "��ǰ���������"+ a0201b_combo
	
	document.getElementById("a0000").value=a0000;
	document.getElementById("a0101").value=realParent.window.document.getElementById('a0101').value;
	document.getElementById("a0225").value=realParent.document.getElementById("a0225").value;
});
var isload = true;
function rowRenderer(value, params, record,rowIndex,colIndex,ds){
	if(value==a0000&&isload){
		Ext.getCmp('gridA01').getSelectionModel().selectRow(rowIndex,true);
		isload = false;
	}
	return value;
}
</script>
<table style="width:93%;">
	<tr>
		<td style="width:93%">
			<font id="a0201b_combo" style="font-size: 12px;"></font>
			<odin:editgrid property="gridA01"   height="550" 
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="1000">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0192a" />
					<odin:gridDataCol name="a0101" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"   header="����"  edited="false"  dataIndex="a0101" align="center" editor="text"  />
					<odin:gridEditColumn sortable="false"   header="������λ��ְ��"  edited="false"  dataIndex="a0192a" align="center" editor="text" width="110"/>
					<odin:gridEditColumn sortable="false"   header="ѡ��"  edited="false" hidden="true" renderer="rowRenderer" dataIndex="a0000" editor="text"  />
					<odin:gridEditColumn sortable="false" header="��Ա���" edited="false"  dataIndex="a0000" align="center" editor="text" hidden="true" isLast="true"   />
				</odin:gridColumnModel>
			</odin:editgrid>	
		</td>
		<td style="width:7%">
			<odin:button property="UpBtn"  handler="UpBtn" text="����"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="����"></odin:button><br>
			<odin:button property="saveBtn" text="����"></odin:button>
		</td>
	</tr>
</table>
</div>