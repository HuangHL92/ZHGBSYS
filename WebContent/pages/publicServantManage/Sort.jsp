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

</script>

<div  style="width:98%">
<odin:hidden property="a0000"/>

<script type="text/javascript">



Ext.onReady(function(){
	
	
});
var isload = true;
/* function rowRenderer(value, params, record,rowIndex,colIndex,ds){
	if(value==a0000&&isload){
		Ext.getCmp('gridA01').getSelectionModel().selectRow(rowIndex,true);
		isload = false;
	}
	return value;
} */

//��Ա�б����¼� 
function rowClickPeople(a,index){
	var gridcq = Ext.getCmp("gridA01");
	var gStore = gridcq.getStore();
	
	//����
	var a0101 = gStore.getAt(index).data.a0101;
	document.getElementById("a0101").innerText = a0101;
	
	//��ǰ���
	var sort = gStore.getAt(index).data.sort;
	document.getElementById("sortSelect").innerText = sort;
	document.getElementById("sortNow").value = sort;
	
	//id
	var a0000 = gStore.getAt(index).data.a0000;
	document.getElementById("a0000").value = a0000;
}


function save(){
	
	
	var a0000  = document.getElementById('a0000').value;
	if(a0000 == null || a0000 == ""){
		Ext.Msg.alert("��ʾ��Ϣ", "��ѡ���¼��");
	����return false; 
	}
	
	//�жϵ�������Ƿ�Ϸ�
	var sort  = document.getElementById('sort').value;				//�������
	var sortMax  = document.getElementById('sortMax').value;		//������
	var sortNow  = document.getElementById('sortNow').value;		//��ǰ���
	
	
	if (!(/(^[1-9]\d*$)/.test(sort))) { 
��������
		Ext.Msg.alert("��ʾ��Ϣ", "����Ĳ�����������");
��������return false; 
����}else { 
	
		if(sort == sortNow){
			Ext.Msg.alert("��ʾ��Ϣ", "��ǰ��Ų�����������һ�£�");
			return false; 
		}
��������
		if(parseInt(sort) > parseInt(sortMax)){
			Ext.Msg.alert("��ʾ��Ϣ", "������Ŵ��������ţ�");
			return false; 
		}
		
		radow.doEvent("save");
		
����} 
	
}


function backSort(){
	
	//��ǰ���
	var sort = document.getElementById('sort').value;
	document.getElementById("sortSelect").innerText = sort;
	document.getElementById("sortNow").value = sort;
	
}

</script>
<table style="width:96%;">
	<tr>
		<td style="width:96%">
			
			<div id="clear_search" style="width:60%">
			<font style="font-size: 12px;">&nbsp;&nbsp;������</font>
						<font id="a0101" style="font-size: 12px;"></font>
						<font style="font-size: 12px;">&nbsp;&nbsp;&nbsp;&nbsp;��ǰ��ţ�</font>
						<font id="sortSelect" style="font-size: 12px;"></font>
			
			<table style="width:100%;" >
				<tr align="left">
					<odin:textEdit property="sort" label="�������" required="true" ></odin:textEdit>
					<td align="right">
						<odin:button text="&nbsp;&nbsp;ȷ&nbsp;&nbsp;��&nbsp;&nbsp;" property="save" handler="save"
							></odin:button>
					</td>
					<td align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="right">
						
						<odin:button text="&nbsp;&nbsp;�����б�&nbsp;&nbsp;" property="load"></odin:button>
					</td>
				</tr>
			</table>
		  </div>
		  
			
			<odin:editgrid property="gridA01"   height="567" cellmousedown="rowClickPeople"
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="800">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="sessionid" />
					<odin:gridDataCol name="sort" />
					<odin:gridDataCol name="a0192a" />
					<odin:gridDataCol name="a0101" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"   header="����"  edited="false"  dataIndex="a0101" align="center" editor="text"  width="80"/>
					<odin:gridEditColumn sortable="false"   header="������λ��ְ��"  edited="false"  dataIndex="a0192a" align="left" editor="text"  width="150"/>
					<odin:gridEditColumn sortable="false"   header="���"  edited="false"  dataIndex="sort" align="center" editor="text"  width="80"/>
					<odin:gridEditColumn sortable="false"   header="ѡ��"  edited="false" hidden="true" dataIndex="a0000" editor="text" />
					<odin:gridEditColumn sortable="false" header="��Ա���" edited="false"  dataIndex="a0000" align="center" editor="text" hidden="true" isLast="true"   />
					
				</odin:gridColumnModel>
			</odin:editgrid>	
		</td>
		<!-- <td style="width:6%"></td>
		<td style="width:14%;" align="center">
			<div id='UpBtn1'></div>
			<br>
			<div id='DownBtn1'></div>
			<br>
			<div id='saveBtn1'></div>
			<br>
			<div id='closeBtn1'></div>
		</td> -->
		
	</tr>
</table>
<odin:hidden property="sortMax"/>
<odin:hidden property="sortNow"/>
<odin:hidden property="a0000"/>

</div>




