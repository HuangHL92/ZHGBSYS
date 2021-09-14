<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
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


/*btnUP*/
function UpBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('����Ա�Ѿ��������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}


function DownBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ����Ļ���!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('����Ա�Ѿ����������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}

//��ְ�������� 
function sortPost(){
	radow.doEvent("sortPost");
}

//����������
function sortOrderCode(){
	
	//ƴ�������ֵ
	var grid = odin.ext.getCmp('gridA01');
	var store = grid.store;
	var a0225String = "";
	
	
	if (store.getCount() > 0) {
		for (var i = 0; i < store.getCount(); i++) {
			var a0225 = grid.getStore().getAt(i).get("a0225");
			
			//�ж�a0255�����Ƿ�Ϸ���ֻ��Ϊ������
			if (!(/(^[0-9]\d*$)/.test(a0225))) { 
��������			
				Ext.Msg.alert("��ʾ��Ϣ", "��������Ĳ�����Ȼ�� ��");
		��������return false; 
		����}
			
			a0225String = a0225String + a0225 + ",";
			
		}
	}
	
	radow.doEvent("sortOrderCode");
	
}

</script>

<div  style="width:98%">
<odin:hidden property="a0201b" title="����id"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a0101" title="����"/>
<odin:hidden property="a0225" title="����������"/>
<script type="text/javascript">
var a0000 = feildIsNull(realParent.document.getElementById("a0000"))?realParent.document.getElementById("a0000s").value:realParent.document.getElementById("a0000").value; 
Ext.onReady(function(){
	
	new Ext.Button({
		icon : 'images/icon/icon_upwards.gif',
		id:'UpBtn',
	    text:'��&nbsp;��',
	    cls :'inline pl',
	    renderTo:"UpBtn1",
	    handler:function(){
	    	UpBtn();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/icon_adown.gif',
		id:'DownBtn',
	    text:'��&nbsp;��',
	    cls :'inline pl',
	    renderTo:"DownBtn1",
	    handler:function(){
	    	DownBtn();
	    }
	});
	new Ext.Button({
		icon : '',
		id:'sortPost',
	    text:'��ְ����',
	    cls :'inline pl',
	    renderTo:"sortPost1",
	    handler:function(){
	    	sortPost();
	    }
	});
	new Ext.Button({
		icon : '',
		id:'sortOrderCode',
	    text:'����������',
	    cls :'inline pl',
	    renderTo:"sortOrderCode1",
	    handler:function(){
	    	sortOrderCode();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/save.gif',
		id:'saveBtn',
	    text:'ȷ&nbsp;��',
	    cls :'inline pl',
	    renderTo:"saveBtn1",
	    handler:function(){
	    	//saveBtn();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/close.gif',
		id:'closeBtn',
	    text:'��&nbsp;��',
	    renderTo:"closeBtn1",
	    cls :'inline pl'
	});
	
	document.getElementById("a0201b").value=realParent.document.getElementById("a0201b").value;
	var a0201b_combo = realParent.document.getElementById("a0201b_combo").value;
	document.getElementById("a0201b_combo").innerText = "��ǰ���������"+ a0201b_combo ;
	
	document.getElementById("a0000").value=a0000;
	//document.getElementById("a0101").value=realParent.document.getElementById('a0101').value;
	if(realParent.document.getElementById("a0225")){
		document.getElementById("a0225").value=realParent.document.getElementById("a0225").value;
	}
	
	
	
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
<table style="width:100%;">
	<tr>
		<td style="width:80%">
			<font id="a0201b_combo" style="font-size: 12px;"></font>
			<odin:editgrid2 property="gridA01"   height="420" 
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="1000">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0225" />
					<odin:gridDataCol name="a0192a" />
					<odin:gridDataCol name="a0101" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn sortable="false"   header="����"  edited="false"  dataIndex="a0225" align="center" editor="text"  width="8" edited="true"/>
					<odin:gridEditColumn sortable="false"   header="����"  edited="false"  dataIndex="a0101" align="center" editor="text"  width="15"/>
					<odin:gridEditColumn sortable="false"   header="������λ��ְ��"  edited="false"  dataIndex="a0192a" align="center" editor="text"  />
					<odin:gridEditColumn sortable="false"   header="ѡ��"  edited="false" hidden="true" renderer="rowRenderer" dataIndex="a0000" editor="text"  />
					<odin:gridEditColumn sortable="false" header="��Ա���" edited="false"  dataIndex="a0000" align="center" editor="text" hidden="true" isLast="true"   />
				</odin:gridColumnModel>
			</odin:editgrid2>	
		</td>
		<td style="width:6%"></td>
		<td style="width:14%;" align="center">
			<%-- <odin:button property="UpBtn"  handler="UpBtn" text="����"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="����"></odin:button><br>
			<odin:button property="saveBtn" text="����"></odin:button> --%>
			
			<div id='UpBtn1'></div>
			<br>
			<div id='DownBtn1'></div>
			<br>
			<div id='sortPost1'></div>
			<br>
			<div id='sortOrderCode1'></div>
			<br>
			<div id='saveBtn1'></div>
			<br>
			<div id='closeBtn1'></div>
		</td>
		<!-- <td style="width:5%"></td> -->
	</tr>
</table>


</div>

<script type="text/javascript">

Ext.onReady(function(){
//�����ƶ�
	var pgrid = Ext.getCmp('gridA01');
	
	
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
		ddGroup : 'GridDD',
		copy : false,
		notifyDrop : function(dd,e,data){
			
			var dstore = pgrid.getStore();
			
			
			//ѡ���˶�����
			var rows = data.selections;
			//�϶����ڼ���
			
			
			var index = dd.getDragData(e).rowIndex;
			if (typeof(index) == "undefined"){
				return;
			}
			//�޸�store
			for ( i=0; i<rows.length; i++){
				var rowData = rows[i];
				if (!this.copy) dstore.remove(rowData);
				dstore.insert(index, rowData);
			}
			pgrid.view.refresh();
			
			
		}
	});
	
});

</script>


