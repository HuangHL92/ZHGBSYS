<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">
//alert(parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById("a0201b").value);

/*btnUP*/
function UpBtn(){	
	var grid = odin.ext.getCmp('grid');
	
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
	var grid = odin.ext.getCmp('grid');
	
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

</script>

<div  style="width:98%">
<odin:hidden property="sql"/>
<script type="text/javascript">
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
	/* new Ext.Button({
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
	}); */
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
});
</script>

<table>
	<tr>
		<odin:select2 colspan="2" property="gbquery" onchange="query()" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ɲ�������ࣺ" data="['0', '�ɲ�������'],['1', '�ص��λ'],['3', '����ɲ�'],['4', 'רҵ�ɲ�'],['5', '�ɲ��ݶ�'],['6', '�ṹ�Ըɲ�'],['7', '����θɲ�'],['2', 'ְ��������������������']" title="�ɲ��������" value="0"></odin:select2>
	</tr>
</table>
<table style="width:100%;">
	<tr>
		<td style="width:80%">
			<!-- <font id="a0201b_combo" style="font-size: 12px;"></font> -->
			<odin:editgrid2 property="grid"   height="420" 
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="1000">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="uuid" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="sortid" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"  header="����" hidden="true" edited="false"  dataIndex="sortid" align="center" editor="text"  width="8" edited="true"/>
					<odin:gridEditColumn sortable="false"  header="�ɲ���������"  edited="false"  dataIndex="name" align="center" editor="text"  />
					<odin:gridEditColumn sortable="false"  header="����"  edited="false" hidden="true" dataIndex="uuid" editor="text" isLast="true"   />
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
	var pgrid = Ext.getCmp('grid');
	
	
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

function query(){
	radow.doEvent("grid.dogridquery");
}
</script>


