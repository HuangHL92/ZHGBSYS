<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<odin:hidden property="sublibrariesmodelid"/>

	<style>
#ext-gen242 {
	width: 550px !important;
	herght: 475px !important;
}
/* #aa{
		width:415px !important;
	}
	#conditionName{
		width:360px !important;
	} */
</style>

<odin:toolBar property="toolBar2">
	<odin:buttonForToolBar text="(+" id="addLeftBracket" handler="addleftBracket"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="(-" id="delLeftBracket" handler="delleftBracket"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" id="upRow" icon="images/icon/arrowup.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����" id="downRow" icon="images/icon/arrowdown.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="-)" id="delRightBracket" handler="delRightBracket"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="+)" id="addRightBracket" handler="addRightBracket" isLast="true" ></odin:buttonForToolBar>
	
	

</odin:toolBar>
<odin:gridSelectColJs2 name="logicSymbolsd" codeType="LOGICSYMBOLS"></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="existsFlag" selectData="['exists','����'],['not exists','������'],['','']"></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="tableNamed" selectData=""></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="colNamesInfo" selectData=""></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="colValuesView" selectData=""></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="opeartorsd" selectData=""></odin:gridSelectColJs2>

<odin:gridSelectColJs2 name="leftBracket" selectData="['(','(']"></odin:gridSelectColJs2>
<odin:gridSelectColJs2 name="rightBracket" selectData="[')',')']"></odin:gridSelectColJs2>

<odin:hidden property="cueRowIndex" />		
		
		

<odin:groupBox title="ѡ������">
<table width="96%"><tr><td>
	<odin:editgrid2 property="grid" topBarId="toolBar2" afteredit="cellEdit" autoFill="true" forceNoScroll="true" height="200" sm="row" remoteSort="false">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="logchecked" />
			<odin:gridDataCol name="tableNamed" />
			<odin:gridDataCol name="tableName" />
			<odin:gridDataCol name="leftBracket" />
			<odin:gridDataCol name="existsFlag" />
			<odin:gridDataCol name="colNamesInfo" />
			<odin:gridDataCol name="colNamesValue" />
			<odin:gridDataCol name="colNames" />
			<odin:gridDataCol name="opeartorsd" />
			<odin:gridDataCol name="opeartors" />
			<odin:gridDataCol name="colValues" />
			<odin:gridDataCol name="colValuesView" />
			<odin:gridDataCol name="rightBracket" />
			<odin:gridDataCol name="logicSymbolsd" />
			<odin:gridDataCol name="logicSymbols" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridColumn header=""  editor="checkbox" dataIndex="logchecked" hidden="true" sortable="false" />
			<odin:gridRowNumColumn header="���" width="35"></odin:gridRowNumColumn>
			<odin:gridEditColumn2 dataIndex="tableNamed" header="��Ϣ��" align="center" width="40" edited="true" editor="select" required="false" sortable="false" />
			<odin:gridColumn dataIndex="tableName" header="����" align="center"  hidden="true"  sortable="false" />
			<odin:gridColumn dataIndex="leftBracket" header="������" align="center" width="10" edited="true" editor="select" selectData="['(','(']" sortable="false" />
			<odin:gridEditColumn2 dataIndex="existsFlag" header="�Ƿ����" align="center"  edited="true" sortable="false" editor="select" hidden="true" selectData="['exists','����'],['not exists','������'],['','']" />
			<odin:gridEditColumn2 dataIndex="colNamesInfo" header="��Ϣ��" align="center" width="40" edited="true" editor="select" required="true" sortable="false" />
			<odin:gridColumn dataIndex="colNamesValue" header="��Ϣ������" align="center" hidden="true" sortable="false" />
			<odin:gridColumn dataIndex="colNames" header="��Ϣ������" align="center" hidden="true" sortable="false" />
			<odin:gridEditColumn2 dataIndex="opeartorsd" header="�����" align="center" width="15" required="true" edited="true" editor="select" sortable="false" />
			<odin:gridColumn dataIndex="opeartors" header="�����" align="center" hidden="true" sortable="false" />
			<odin:gridEditColumn2 dataIndex="colValuesView" header="ֵ" align="center" width="40" editor="select" required="true" edited="true" sortable="false" />
			<odin:gridColumn dataIndex="colValues" header="����" align="center" editor="text" hidden="true" sortable="false" />
			
			<odin:gridColumn dataIndex="rightBracket" header="������" align="center" edited="true" width="10" editor="select" selectData="[')',')']" sortable="false" />
			<odin:gridEditColumn2 header="��ϵ" editor="select" width="10" dataIndex="logicSymbolsd" codeType="LOGICSYMBOLS" edited="true" sortable="false"  />
			<odin:gridColumn header="��ϵ"  width="10" dataIndex="logicSymbols" hidden="true" sortable="false" isLast="true" />
		</odin:gridColumnModel>
						<odin:gridJsonData> 
						{data:[]}
					</odin:gridJsonData>
	</odin:editgrid2>
</td></tr></table>
	<table>
		<tr>
			<odin:textarea property="aa" label="��ѯ����һ��" cols="100" rows="5" style="width:500px;" colspan="1"></odin:textarea>
		</tr>
		<tr>
			<odin:textEdit property="conditionName" label="��ѯ��������" width="500" ></odin:textEdit>
		</tr>	
	</table>
</odin:groupBox>
<table style="width: 90%" id="bp">
	<tr>
		<td align="right" width="70%"><odin:button text="��������" property="savecond"  handler="savecond"></odin:button></td>
		<td align="right"><odin:button text="ɾ������" property="delEmpRow" handler="delEmpRow"></odin:button></td>
		<td align="right"><odin:button text="���淽��" property="btnSave"></odin:button></td>
		<td align="right" ><odin:button text="��ʼ��ѯ" property="doQuery"></odin:button></td>
		<td align="right"><odin:button text="�ر�" property="closeWin"></odin:button></td>
	</tr>
</table>

<script>
function createCellEdit(e){
	// var sm = e.grid.getColumnModel()
	//radow.doEvent('createCellEdit');//��Ϣ��
	
	//var w = sm.getColumnWidth(e.column);//17
	//var headername = sm.getDataIndex(e.column);
	//if(headername=="tableNamed"){
		//document.getElementById('comboEdit_'+headername).value= 100;
		//alert(100);
	//}
}
function cellEdit(e){
	// e.row;;//�޸Ĺ����д�0��ʼ  e.column;//�޸��� e.originalValue;//ԭʼֵ e.value;//�޸ĺ��ֵ e.grid;//��ǰ�޸ĵ�grid e.field;//���ڱ��༭���ֶ��� e.record;//���ڱ��༭����
	var grid = odin.ext.getCmp('grid');
	var headername = grid.getColumnModel().getDataIndex(e.column);
	//alert(tableNamed_select);
	//alert( eval('e.record.data.'+e.field));
	//alert( e.value);
	var exists = false;
	if(headername=="tableNamed"){
		var dataarray = tableNamed_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				//alert(dataarray[i][0]);
				e.record.data.tableName=dataarray[i][0];
				
				//��Ϣ���л��������������Ϣ��
				e.record.data.colNamesInfo="";
				e.record.data.colNamesValue="";
				e.record.data.colNames="";
				e.record.data.opeartorsd="";
				e.record.data.opeartors="";
				e.record.data.colValuesView="";
				e.record.data.colValues="";
				
				e.record.commit();
				radow.doEvent('setColNames',dataarray[i][0]);//��Ϣ��
				break;
			}
		}
		if(!exists){
			e.record.data.tableNamed="";
			e.record.data.tableName="";
			e.record.data.colNamesInfo="";
			e.record.data.colNamesValue="";
			e.record.data.colNames="";
			e.record.data.opeartorsd="";
			e.record.data.opeartors="";
			e.record.data.colValuesView="";
			e.record.data.colValues="";
		}
	}else if(headername=="colNamesInfo"){
		var dataarray = colNamesInfo_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				//alert(dataarray[i][0]);
				e.record.data.colNamesValue=dataarray[i][0];
				e.record.data.colNames=dataarray[i][1];
				
				
				//��Ϣ���л��������������Ϣ��
				e.record.data.opeartorsd="";
				e.record.data.opeartors="";
				e.record.data.colValuesView="";
				e.record.data.colValues="";
				
				e.record.commit();
				if(e.record.data.tableName!=null&&e.record.data.tableName!=''){
					radow.doEvent('setCodeValue',dataarray[i][0]+"@"+e.record.data.tableName);//����codevalue
				}
				
				break;
			}
			
		}
		if(!exists){
			e.record.data.colNamesInfo="";
			e.record.data.colNamesValue="";
			e.record.data.colNames="";
			e.record.data.opeartorsd="";
			e.record.data.opeartors="";
			e.record.data.colValuesView="";
			e.record.data.colValues="";
		}
	}else if(headername=="colValuesView"){//�������������ֵ
		var dataarray = colValuesView_select;
		if(dataarray.length==0){
			e.record.data.colValues=e.value;
			e.record.commit();
		}else{
			for(var i=0;i<dataarray.length;i++){
				if(e.value==dataarray[i][1]){
					exists = true;
					//alert(dataarray[i][0]);
					e.record.data.colValues=dataarray[i][0];
					e.record.commit();
					break;
				}
				
			}
			if(!exists){
				e.record.data.colValuesView="";
				e.record.data.colValues="";
			}
		}
		if("({c} is null or {c}={v})"==e.record.data.opeartors){
			e.record.data.colValuesView="";
			e.record.data.colValues="";
		}
		
	}else if(headername=='opeartorsd'){
		var dataarray = opeartorsd_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				//alert(dataarray[i][0]);
				e.record.data.opeartors=dataarray[i][0];
				if("({c} is null or {c}={v})"==e.record.data.opeartors){
					e.record.data.colValuesView="";
					e.record.data.colValues="";
				}
				e.record.commit();
				break;
			}
			
		}
		if(!exists){
			e.record.data.opeartors="";
			e.record.data.opeartorsd="";
		}
	}else if(headername=='logicSymbolsd'){
		var dataarray = logicSymbolsd_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				//alert(dataarray[i][0]);
				e.record.data.logicSymbols=dataarray[i][0];
				e.record.commit();
				break;
			}
			
		}
		if(!exists){
			e.record.data.logicSymbols="";
			e.record.data.logicSymbolsd="";
		}
	}else if(headername=='leftBracket'){
		var dataarray = leftBracket_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				break;
			}
			
		}
		if(!exists){
			e.record.data.leftBracket="";
		}
	}else if(headername=='rightBracket'){
		var dataarray = rightBracket_select;
		for(var i=0;i<dataarray.length;i++){
			if(e.value==dataarray[i][1]){
				exists = true;
				break;
			}
			
		}
		if(!exists){
			e.record.data.rightBracket="";
		}
	}
	
	e.record.commit();
	radow.doEvent('PrintSQL');
}



	function savecond() {
		document.getElementById("conditionName").focus();
		
		var grid = odin.ext.getCmp('grid');
		var store = grid.getStore();
		var p = new Ext.data.Record({  
			logchecked: '',  
			tableNamed: '',  
			tableName: '',  
			leftBracket: '',  
			existsFlag: '', 
			colNamesInfo: '',
			colNamesValue: '',  
			colNames: '',
			opeartorsd: '',
			opeartors: '',
			colValues: '',
			colValuesView: '',
			rightBracket: '',
			logicSymbolsd: '��',
			logicSymbols: ' and 1=1 '
        });
		store.insert(store.getCount(), p);
		//radow.doEvent('setColNames','A01');
		//var neweditor = newEditor([ [ 'exists', '����' ], [ 'not exists', '������' ], [ '', '' ] ]);
		//grid.getColumnModel().setEditor( 2, neweditor );
		
		//radow.doEvent('savecond');
	}

	
	
	
	
	function addEmpRow() {
		radow.addGridEmptyRow("grid", 0);
		printSql();
	}
	function dataRender4del(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=href='javascript:void(0)' onclick = del('" + rowIndex
				+ "')>ɾ��</a>";
	}
	function del(rowIndex) {
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		store.removeAt(rowIndex);
		printSql();
	}

	function delEmpRow1() {
		var grid = odin.ext.getCmp('grid');
		var arrayObj = new Array();
		;
		var store = grid.store;
		var i = store.getCount() - 1;
		if (store.getCount() > 0) {
			for (var i = store.getCount() - 1; i >= 0; i--) {
				var ck = grid.getStore().getAt(i).get("logchecked");
				if (ck == true) {
					store.remove(grid.getStore().getAt(i));
				}
			}
		}
		grid.view.refresh();
		/**	for (i = 0; i < arrayObj.length; i++)
		 {
		 store.remove(grid.getStore().getAt(arrayObj[i]));
		 }
		 */
		printSql();

	}
	function delEmpRow(a, b, c) {
		var grid = odin.ext.getCmp('grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			store.remove(selected);

		}
		grid.view.refresh();
		printSql();
	}

	function addleftBracket(a, b, c) {
		var grid = odin.ext.getCmp('grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			selected.data['leftBracket'] = selected.data['leftBracket'] + '(';

		}
		grid.view.refresh();
		printSql();
	}

	function addRightBracket(a, b, c) {
		var grid = odin.ext.getCmp('grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			selected.data['rightBracket'] = selected.data['rightBracket'] + ')';

		}
		grid.view.refresh();
		printSql();
	}

	function delleftBracket(a, b, c) {
		var grid = odin.ext.getCmp('grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			selected.data['leftBracket'] = selected.data['leftBracket'].substr(
					selected.data['leftBracket'].indexOf('(') + 1,
					selected.data['leftBracket'].length);

		}
		grid.view.refresh();
		printSql();
	}

	function delRightBracket(a, b, c) {
		var grid = odin.ext.getCmp('grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			selected.data['rightBracket'] = selected.data['rightBracket']
					.substr(0, selected.data['rightBracket'].lastIndexOf(')'))
					+ selected.data['rightBracket'].substr(
							selected.data['rightBracket'].lastIndexOf(')') + 1,
							selected.data['leftBracket'].length);

		}
		grid.view.refresh();
		printSql();
	}
	//��װ��������sqlƴ������ 
	function printSql() {
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		var sqltoChar = "";
		var eiDTO = {};
		if (store.getCount() > 0) {
			for (var i = 0; i < store.getCount(); i++) {
				var ck = grid.getStore().getAt(i).get("logchecked");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("leftBracket");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("colNames");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("opeartors");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("colValuesView");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("rightBracket");
				sqltoChar = sqltoChar
						+ grid.getStore().getAt(i).get("logicSymbols");
			}
		}
		document.all.aa.value = sqltoChar;
		radow.doEvent("PrintSQL", odin.encode(eiDTO));
	}
	
	Ext.onReady(function(){
		var pgrid = Ext.getCmp('grid');
		var bbar = pgrid.getTopToolbar();
		bbar.insertButton(5,[
			//new Ext.menu.Separator({cls:'xtb-sep'}),
			new Ext.Spacer({width:100})
			]);
		//alert(bbar);
		
		/* var grid = odin.ext.getCmp('grid');
		grid.on('rowclick',function(gridobj,index,c,d){
			radow.doEvent('gridrowclick');
		}); */
		
		
		
		window.onresize=resizeframe;
		resizeframe();
		
		
		var pgrid = Ext.getCmp('grid');
		var dstore = pgrid.getStore();
		var ddrow = new Ext.dd.DropTarget(pgrid.container,{
						ddGroup : 'GridDD',
						copy : false,
						notifyDrop : function(dd,e,data){
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
							radow.doEvent('PrintSQL');
							/* Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ������",function(id) { 
								if("yes"==id){
									//�޸�store
									for ( i=0; i<rows.length; i++){
										var rowData = rows[i];
										if (!this.copy) dstore.remove(rowData);
										dstore.insert(index, rowData);
									}
									pgrid.view.refresh();
									radow.doEvent('personsort');
								}else{
									return;
								}		
							}); */
						}
					});
		
		dstore.on('load',function(){
			insertEmptyRow(this);
		});
		
		
		
		
	});
	function insertEmptyRow(ds){
		var dstorecount = ds.getCount();
		//alert(dstorecount);
		for(var gi=0;gi<8-dstorecount;gi++){
			savecond();
		}
	}
	function resizeframe(){
		var ta = document.getElementById("aa");
		var tx = document.getElementById("conditionName");
		
		var viewSize = Ext.getBody().getViewSize();
		ta.style.width = viewSize.width-250;
		tx.style.width = viewSize.width-250;
		
		var grid =Ext.getCmp('grid');
		grid.setHeight(viewSize.height-220);
		
		document.getElementById("savecond").style.height='40';
		document.getElementById("delEmpRow").style.height='40';
		document.getElementById("btnSave").style.height='40';
		document.getElementById("doQuery").style.height='40';
		document.getElementById("closeWin").style.height='40';
	}
	
	function changestore(){
		var pgrid = Ext.getCmp('grid');
		var a = pgrid.initialConfig.cm.config[2].editor.getStore();
		var newRecord=new Ext.data.Record({key:'1',value:'����'});   
		a.add(newRecord);   
	}
	
</script>