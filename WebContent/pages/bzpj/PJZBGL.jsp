<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>


<style>
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
#etInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
</style>
<script  type="text/javascript">

</script>

<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="方案："></odin:textForToolBar>
	<odin:fill />
	<odin:buttonForToolBar text="增加指标方案" icon="images/add.gif" id="addScheme" handler="addScheme"/>
	<odin:buttonForToolBar text="修改指标方案" icon="image/d02.png" id="editScheme" handler="addScheme"/>
	<odin:buttonForToolBar text="增加指标类" icon="images/add.gif" id="addBtn" handler="addetc" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="btnToolBarET" >
	<odin:fill />
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:buttonForToolBar text="增加" icon="images/add.gif" id="addBtnET" handler="addet" isLast="true"/>
</odin:toolBar>
<odin:hidden property="etc00id"/>
<table width="100%">
	<tr>
		<td>
			
			<table width="100%"><tr>
			<td width="30%">
			<odin:editgrid property="memberGrid" hasRightMenu="true" topBarId="btnToolBar" autoFill="true" width="590" height="380" pageSize="200" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="etc00" />
					<odin:gridDataCol name="etc01"/>
					<odin:gridDataCol name="ets00"/>
					<odin:gridDataCol name="etc03" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="etc01"  header="指标类" align="left" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
			</td>
			<td width="70%">
			<odin:editgrid property="logGrid" hasRightMenu="false" autoFill="true" topBarId="btnToolBarET"
			width="590" height="380" pageSize="200" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="et00" />
					<odin:gridDataCol name="et01" />
					<odin:gridDataCol name="et02" />
					<odin:gridDataCol name="et03" />
					<odin:gridDataCol name="et04" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="et01" width="150" header="信息项" align="center"/>
					<odin:gridColumn dataIndex="et02" width="30" header="分值下限" align="center"/>
					<odin:gridColumn dataIndex="et03" width="30" header="分值上线" align="center"/>
					<odin:gridColumn dataIndex="et04" width="30" header="占比" align="center" renderer="showPerct" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
			</td>
			</tr></table>
		
		</td>
	</tr>
</table>


<div id="etcInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textarea property="etc01" cols="70" rows="6" label="指标类"></odin:textarea>
		  </tr>
		</table>
		<odin:hidden property="etc00"/>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="确定" property="saveETCInfo" handler="saveETCInfo" />
		</div>
	</div>
</div>
<div id="etInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textarea property="et01" cols="70" rows="6" label="指标类" colspan="4" />
		  </tr>
		  <tr>
		  	<odin:numberEdit property="et02"  label="分值下限"/>
		  	<odin:numberEdit property="et03"  label="分值上限"/>
		  </tr>
		  <tr>
		  	<odin:numberEdit property="et04"   maxValue="100" minValue="0" label="占比" colspan="4">%</odin:numberEdit>
		  <tr>
		</table>
		<odin:hidden property="et00"/>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="确定" property="saveETInfo" handler="saveETInfo" />
		</div>
		<input id="ets01_combo" name="ets01_combo" type="text"/>
<input id="ets01" name="ets01" type="hidden"/>
	</div>
</div>

<script type="text/javascript">



Ext.onReady(function() {
	var pgrid = Ext.getCmp('memberGrid')
	var bbar = pgrid.getTopToolbar();
	 bbar.insertButton(1,[
							createSel()
						]);
	
	
	
	openETCWin();
	openETWin();
	hideWin();
	
	//页面调整
	 Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_logGrid')).top-4)*0.99);
	 Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
	 
	 
	 Ext.getCmp('memberGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		$('#etc01').val(rc.data.etc01);
		$('#etc00').val(rc.data.etc00);
		//$('#etc00id').val(rc.data.etc00);
		openETCWin();
	});
	 Ext.getCmp('logGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		if($('#etc00id').val==''){
			$h.alert('','请选择指标类！')
			return;
		}
		$('#et01').val(rc.data.et01);
		$('#et00').val(rc.data.et00);
		$('#et02').val(rc.data.et02);
		$('#et03').val(rc.data.et03);
		$('#et04').val(rc.data.et04);
		openETWin();
	});
	 
});




function flash() {
	radow.doEvent('memberGrid.dogridquery');
	
}

function addetc(){
	$('#etc01').val('');
	$('#etc00').val('');
	openETCWin();
	
}
function delet(){
	
	
}
function addet(){
	if($('#etc00id').val==''){
		$h.alert('','请选择指标类！')
		return;
	}
	$('#et01').val('');
	$('#et00').val('');
	$('#et02').val('0');
	$('#et03').val('100');
	$('#et04').val('20');
	openETWin();
	
}
function openETCWin(){
	var win = Ext.getCmp("addetc");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '指标类维护',
		layout : 'fit',
		width : 500,
		height : 201,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addetc',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"etcInfo",
		listeners:{}
		           
	});
	win.show();
}
function openETWin(){
	var win = Ext.getCmp("addet");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '指标维护',
		layout : 'fit',
		width : 500,
		height : 301,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addet',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"etInfo",
		listeners:{}
		           
	});
	win.show();
}

function saveETCInfo(){
	radow.doEvent("addETCInfo");
	Ext.getCmp("addetc").hide();
}
function saveETInfo(){
	radow.doEvent("addETInfo");
	Ext.getCmp("addet").hide();
}



function showPerct(value, params, record, rowIndex, colIndex, ds) {
	
	return value+"%";
	
	
}

function hideWin(){
	var win = Ext.getCmp("addet");	
	if(win){
		win.hide();	
	}
	var win = Ext.getCmp("addetc");	
	if(win){
		win.hide();	
	}
}


function addScheme(a){
	var p;
	if(a.id=='addScheme'){
		p={};
	}else{
		p={ets00:$('#ets01').val(),ets01:$('#ets01_combo').val()};
	}
	$h.openWin('addScheme','pages.bzpj.PJZBScheme','指标方案维护',430,635,null,'<%=request.getContextPath()%>',null,
			p,true);
}




function createSel(){

	var ets01_comboData = [ [] ];
		var ets01_store = new Ext.data.SimpleStore({
			data : ets01_comboData,
			fields : [ 'key', 'value' ],
			createFilterFn : odin.createFilterFn
		});
		var ets01_combo = new Ext.form.ComboBox(
				{
					store : ets01_store,
					displayField : 'value',
					canOutSelectList : 'false',
					typeAhead : false,
					id : 'ets01_combo',
					mode : 'local',
					emptyText : '',
					editable : true,
					triggerAction : 'all',
					width : 120,
					hideTrigger : false,
					onSelect : function(record, index) {
						
						if (this.fireEvent('beforeselect', this, record,
								index) !== false) {
							this.setValue(record.data[this.valueField
									|| this.displayField]);
							this.collapse();
							odin.doAccForSelect(this);
							radow.doEvent('memberGrid.dogridquery');
							this.fireEvent('select', this, record, index);
						}
					},
					applyTo : 'ets01_combo'
				});
		Ext.getCmp('ets01_combo')
				.addListener('blur', odin.doAccForSelect);
		Ext.getCmp('ets01_combo').addListener('select',
				odin.setHiddenTextValue);
		Ext.getCmp('ets01_combo').addListener('focus', odin.comboFocus);
		Ext.getCmp('ets01_combo')
				.addListener('expand', odin.setListWidth);
		Ext.getCmp('ets01_combo').addListener('invalid',
				odin.trrigerCommInvalid);
		
		return ets01_combo;
}
</script>


