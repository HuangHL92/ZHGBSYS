<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<script  type="text/javascript">

function InitGrid(){
	var grid_codevalue = odin.ext.getCmp('gridCodevalue');
	//�����ģʽ
	var count = grid_codevalue.colModel;
	//���˳���Ӧ����
	var col = count.getColumnById(2);
	col.sortable=false;
	col.menuDisabled=true;  //��С��ͷȥ��
	
	radow.doEvent('gridCodevalue.dogridquery',"0","200");
} 


/*btnUP*/
function UpBtn(){	
	var grid = odin.ext.getCmp('gridCodevalue');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('��ѡ����Ҫ����Ĵ���!')
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('�ô����Ѿ��������϶�!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}


function DownBtn(){	
	var grid = odin.ext.getCmp('gridCodevalue');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ����Ĵ���!')
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('�ô����Ѿ��������¶�!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
}

</script>

<div  style="width:98%">
<odin:hidden property="subCodeValue"/>
<odin:hidden property="codetype"/>
<table style="width:90%;">
	<tr>
		<td style="width:80%">
			
			<odin:editgrid2 property="gridCodevalue"   height="440" 
						bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="200">
						<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="codevalue" />
							<odin:gridDataCol name="codename" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn sortable="false" header="����" edited="false" width="200" dataIndex="codevalue"  hidden="true" align="center" editor="text"   />
							<odin:gridEditColumn  header="��������" edited="false" width="250" dataIndex="codename" align="center" editor="text" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid2>	
		</td>
		<td style="width:0%">
			<odin:button property="UpBtn"  handler="UpBtn" text="����"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="����"></odin:button><br>
			<odin:button property="closeBtn" text="�ر�"></odin:button><br>
			
			<odin:button property="saveBtn" text="����"></odin:button>
			
		</td>
	
	</tr>
</table>


</div>




