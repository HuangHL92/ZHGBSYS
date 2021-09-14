<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>


<style>
#zwzcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
#mxInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
  white-space:normal !important;
}
#zwqc textarea{
	width:200px;
	height:60px
}
#zwmx textarea{
	width:200px;
	height:40px
}
</style>
<script  type="text/javascript">

</script>

<odin:toolBar property="btnToolBar" >
	<odin:fill />
<%-- 	<odin:buttonForToolBar text="增加指标方案" icon="images/add.gif" id="addScheme" handler="addScheme"/>
	<odin:buttonForToolBar text="修改指标方案" icon="image/d02.png" id="editScheme" handler="addScheme"/> --%>
	<odin:buttonForToolBar text="全单位搜索" icon="image/d02.png" id="searchBtn" handler="flash" />
<%-- 	<odin:buttonForToolBar text="职务关联机构" icon="image/zjyt.png" id="orgBtn" handler="gljg" /> --%>
	<odin:buttonForToolBar text="增加职务全称" icon="images/add.gif" id="addBtn" handler="addzwzc" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="btnToolBarET" >
	<odin:fill />
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:buttonForToolBar text="删除职务" icon="image/delete2.png" id="delBtnET" handler="delmx" />
	<odin:buttonForToolBar text="增加职务" icon="images/add.gif" id="addBtnET" handler="addmx" isLast="true"/>
</odin:toolBar>
<odin:hidden property="zwzc00id"/>
<odin:hidden property="a0192aid"/>
<table width='100%' style="overflow:hidden">
	<tr>
		<td>
			<table width='100%' style="overflow-y: auto; overflow-x:hidden;"><tr>
			 	<td  valign="top">
                        <odin:tab id="tab" width="270" height="670">
                            <odin:tabModel>
                                <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                            </odin:tabModel>
                            <odin:tabCont itemIndex="tab1" className="tab">
                                <table id="tableTab1" style="height: 465px;border-collapse:collapse;">
                                    <tr>
                                        <td colspan="2">
                                            <div id="tree-div" style="overflow-y: auto; overflow-x:hidden;height: 600; width: 100%; border: 2px solid #c3daf9;"></div>
                                            <odin:hidden property="checkedgroupid"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                            <input type="checkbox" id="isContain"  />
                                            <span style="font-size: 13px">包含下级</span>
                                        </td>
                                    </tr>
                                </table>
                            </odin:tabCont>
                        </odin:tab>
                    </td>
			<td width="45%" height="670" >
				<odin:editgrid2 property="zwzcGrid" hasRightMenu="false" topBarId="btnToolBar" bbarId="pageToolBar" width="590" height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="zwzc00" />
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="a0165"/>
						<odin:gridDataCol name="a0221"/>
						<odin:gridDataCol name="jzaz"/>
						<odin:gridDataCol name="a0192e" />
						<odin:gridDataCol name="sortid" />
						<odin:gridDataCol name="gfhjc" />
						<odin:gridDataCol name="xzzw" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="zwzc00"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="80" header="职务全称" align="left" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="a0165" width="60"  header="管理类别" editor="text" align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0221"  width="20" header="职务<br/>层次" codeType="ZB09" editor="select"  align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="jzaz" width="20" header="是否军转安置" selectData="['1','是'],['0','否']"  align="center"  editor="select"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="sortid" width="10" header="序号" align="center" editor="text"  edited="false"   />
						<odin:gridEditColumn2 dataIndex="gfhjc" width="40" header="规范化简称" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="xzzw" width="40" header="行政职务" align="center" editor="text"  edited="false" isLast="true" />
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			<td width="45%" >
				<odin:editgrid2 property="zwzcMxGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET"
				width="590" height="670" pageSize="200" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="zwmx00" />
						<odin:gridDataCol name="a0215a" />
						<odin:gridDataCol name="a0201a" />
						<odin:gridDataCol name="b01id" />
						<odin:gridDataCol name="a0201d" />
						<odin:gridDataCol name="a0201e" />
						<odin:gridDataCol name="a0279" />
						<odin:gridDataCol name="a0248" />
						<odin:gridDataCol name="zwcode" />
						<odin:gridDataCol name="zjcode" />
						<odin:gridDataCol name="zwzc00"/>
						<odin:gridDataCol name="sortid" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="zwmx00"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0215a" width="140" header="职务名称" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0201a" width="90" header="任职机构名称" align="center"  edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="b01id"  header="机构id" hidden="true"  edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0201d"  header="是否班子成员" selectData="['1','是'],['0','否']"  align="center"  editor="select"  edited="false"  editor="select"/>
						<odin:gridEditColumn2 dataIndex="a0201e"  header="班子成员类别" codeType="ZB129" editor="select" edited="false"  align="center"/>
						<odin:gridEditColumn2 dataIndex="a0279"  header="主职务标识" hidden="true" edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="a0248"  header="是否占职数" selectData="['1','不占职数'],['0','占职数'],['','占职数']"  align="center"  edited="false"  editor="select"/>
						<odin:gridEditColumn2 dataIndex="zwcode"  header="职务层次代码" hidden="true" edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="zjcode"  header="职级代码" hidden="true" edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="zwzc00"  header="职务总称id" hidden="true"  edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="sortid" width="30" header="排序号" align="center" editor="text"  edited="false"  isLast="true"  />
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			</tr></table>
		
		</td>
	</tr>
</table>


<div id="zwzcInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		  <tr id="zwqc">
			<odin:textarea property="a0192a"   label="职务全称"></odin:textarea>
		  </tr>
		  <tr>
			
			<odin:select2 property="a0165" codeType="ZB130"  width="130" value="" label="管理类别" required="true" filter="code_value not in('06','16','17')"  multiSelect="true" />
							<%-- <tags:rmbSelect property="a0165" cls=" right_option" label="管理类别"
textareaStyle="height:23px;" codetype="ZB130" defaultValue="<%=SV(a01.getA0165()) %>" selectTDStyle="width:130px;" /> --%>
			
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0221" codetype="ZB09" width="160" label="职务层次"  readonly="true"/>
		</tr>
		<tr></tr>
		 <%-- <tr>
			<tags:PublicTextIconEdit property="a0192e" codetype="ZB148" width="160" label="职级" readonly="true" />
		</tr>  --%>
		<odin:hidden property="a0192e"/>
		<tr>
<%-- 			<odin:select2 property="jzaz" value="0" data="['1','优秀'],['2','良好'],['3','一般'],['4','较差']" ></odin:select2> --%>
			<odin:select2 property="jzaz"  value="0" label="是否军转安置"  data="['1','是'],['0','否']" ></odin:select2> 
		</tr>
		<tr>
			<odin:textEdit property="gfhjc" label="规范化简称" ></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="xzzw" label="行政职务" ></odin:textEdit>
		  </tr>
		</table>
		<odin:hidden property="zwzc00"/>
		<div style="margin-left: 100px;margin-top: 15px;">
			<odin:button text="确定" property="saveZWZCInfo" handler="saveZWZCInfo" />
		</div>
	</div>
</div>
<div id="mxInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr id="zwmx">
			<odin:textarea property="a0215a" label="职务名称"  />
		  </tr>
		  <tr>
			<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="选择任职机构" property="a0201b" defaultValue="" readonly="true" />
		  </tr>
		  <tr>
			<odin:textEdit property="a0201a" label="任职机构名称"   ></odin:textEdit>
		  </tr>
		  <tr>
			<odin:select2 property="a0201d"  value="0" label="是否班子成员"  data="['1','是'],['0','否']" ></odin:select2> 
		  </tr>
		  <tr>
		  	<odin:select2 property="a0201e" label="成员类别"  codeType="ZB129"></odin:select2>
		  </tr>
		  <tr>
		  	<odin:select2 property="a0279"  value="0" label="是否主职务"  data="['1','是'],['0','否']" ></odin:select2> 
		  </tr>
		  <tr>
		    <odin:select2 property="a0248"  value="0" label="是否占职数"  data="['1','不占职数'],['0','占职数']" ></odin:select2> 
		  </tr>
		</table>
		<odin:hidden property="zwmx00"/>
		<odin:hidden property="b01id"/>
		<odin:hidden property="zwcode"/>
		<odin:hidden property="zjcode"/>
		<div style="margin-left: 120px;margin-top: 15px;">
			<odin:button text="确定" property="saveMXInfo" handler="saveMXInfo" />
		</div>
		<input id="ets01_combo" name="ets01_combo"  type="hidden"/>
		<input id="ets01" name="ets01" type="hidden"/>
	</div>
</div>

<script type="text/javascript">
<%-- <%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
} --%>

Ext.onReady(function() {
	$h.initGridSort('zwzcGrid',function(g){
		radow.doEvent('rolesort');
	});	
	$h.initGridSort('zwzcMxGrid',function(g){
		radow.doEvent('rolesort1');
	});
	var pgrid = Ext.getCmp('zwzcGrid')
	var bbar = pgrid.getTopToolbar();
 	 bbar.insertButton(1,[
							createSel2()
						]);
	
	
	
	openZwzcWin();
	openMxWin();
	hideWin();
	
	//页面调整
	 Ext.getCmp('zwzcMxGrid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_ZwzcMxGrid')).top-4)*0.99);
	 Ext.getCmp('zwzcGrid').setHeight(Ext.getCmp('zwzcMxGrid').getHeight());
	 
	 
	 Ext.getCmp('zwzcGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
/* 		$('#a0192a').val(rc.data.a0192a);
		$('#zwzc00').val(rc.data.zwzc00);
		$('#a0165').val(rc.data.a0165); */
		odin.setSelectValue('a0192a',rc.data.a0192a);
		odin.setSelectValue('zwzc00',rc.data.zwzc00);
		odin.setSelectValue('a0165',rc.data.a0165);
		odin.setSelectValue('a0221',rc.data.a0221);
		odin.setSelectValue('a0192e',rc.data.a0192e);
		odin.setSelectValue('jzaz',rc.data.jzaz);
		odin.setSelectValue('gfhjc',rc.data.gfhjc);
		odin.setSelectValue('xzzw',rc.data.xzzw);
/* 		$('#a0221').val(rc.data.a0221);
		$('#a0192e').val(rc.data.a0192e); */
		//$('#zwzc00id').val(rc.data.zwzc00);
		openZwzcWin();
	});
	 Ext.getCmp('zwzcMxGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		if($('#zwzc00id').val==''){
			$h.alert('','请选择职务全称！')
			return;
		}
		odin.setSelectValue('a0201b','');
		odin.setSelectValue('a0215a',rc.data.a0215a);
		odin.setSelectValue('zwmx00',rc.data.zwmx00);
		odin.setSelectValue('a0201a',rc.data.a0201a);
		odin.setSelectValue('b01id',rc.data.b01id);
		odin.setSelectValue('a0201d',rc.data.a0201d);
		odin.setSelectValue('a0201e',rc.data.a0201e);
		odin.setSelectValue('a0279',rc.data.a0279);
		odin.setSelectValue('a0248',rc.data.a0248);
		odin.setSelectValue('zwcode',rc.data.zwcode);
		odin.setSelectValue('zjcode',rc.data.zjcode);
		odin.setSelectValue('a0201b_combo',rc.data.a0201a);
		/* $('#a0215a').val(rc.data.a0215a);
		$('#zwmx00').val(rc.data.zwmx00);
		$('#a0201a').val(rc.data.a0201a);
		$('#b01id').val(rc.data.b01id);
		$('#a0201d').val(rc.data.a0201d);
		$('#a0201e').val(rc.data.a0201e);
		$('#a0279').val(rc.data.a0279);
		$('#a0248').val(rc.data.a0248);
		$('#zwcode').val(rc.data.zwcode);
		$('#zjcode').val(rc.data.zjcode); */
		openMxWin();
	});
	 
});




function flash() {
	document.getElementById("checkedgroupid").value = '';
	radow.doEvent('zwzcGrid.dogridquery');
	
}

function addzwzc(){
	var b0111=document.getElementById("checkedgroupid").value;
	if(b0111==null || b0111==''){
		$h.alert('','请选择职务所属单位！')
		return;
	}
	odin.setSelectValue('a0192a','');
	odin.setSelectValue('zwzc00','');
	odin.setSelectValue('a0165','');
	odin.setSelectValue('a0221','');
	odin.setSelectValue('a0192e','');
	odin.setSelectValue('jzaz','');
	odin.setSelectValue('gfhjc','');
	odin.setSelectValue('xzzw','');
	/* $('#a0192a').val('');
	$('#zwzc00').val('');
	$('#a0165').val('');
	$('#a0221').val('');
	$('#a0192e').val(''); */
	openZwzcWin();
	
}

function delmx(){
	var a=document.getElementById("zwmx00").value;
	if(a==null || a==''){
		$h.alert('','请选择职务明细！')
		return;
	}
	$h.confirm("系统提示：","是否确认删除？",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delMxInfo");
		}else{
			return false;
		}		
	});
	
	
}
function addmx(){
	if(document.getElementById("zwzc00id").value =='' || document.getElementById("zwzc00id").value ==null){
		$h.alert('','请选择职务全称！')
		return;
	}
	/* $('#a0215a').val('');
	$('#zwmx00').val('');
	$('#a0201a').val('');
	$('#b01id').val('');
	$('#a0201d').val('');
	$('#a0201e').val('');
	$('#a0279').val('');
	$('#a0248').val('');
	$('#zwcode').val('');
	$('#zjcode').val(''); */
	odin.setSelectValue('a0215a','');
	odin.setSelectValue('zwmx00','');
	odin.setSelectValue('a0201a','');
	odin.setSelectValue('b01id','');
	odin.setSelectValue('a0201d','');
	odin.setSelectValue('a0201e','');
	odin.setSelectValue('a0279','');
	odin.setSelectValue('a0248','');
	odin.setSelectValue('a0201b','');
	odin.setSelectValue('a0201b_combo','');
	openMxWin();
	
}
function openZwzcWin(){
	var win = Ext.getCmp("addzwzc");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '职务总称维护',
		layout : 'fit',
		width : 350,
		height : 281,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addzwzc',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"zwzcInfo",
		listeners:{}
		           
	});
	win.show();
}
function openMxWin(){
	var win = Ext.getCmp("addmx");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '职务明细维护',
		layout : 'fit',
		width : 380,
		height : 321,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addmx',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"mxInfo",
		listeners:{}
		           
	});
	win.show();
}

function saveZWZCInfo(){
	radow.doEvent("addZWZCInfo");
	Ext.getCmp("addzwzc").hide();
}
function saveMXInfo(){
	radow.doEvent("addMxInfo");
	Ext.getCmp("addmx").hide();
}



function showPerct(value, params, record, rowIndex, colIndex, ds) {
	
	return value+"%";
	
	
}

function hideWin(){
	var win = Ext.getCmp("addmx");	
	if(win){
		win.hide();	
	}
	var win = Ext.getCmp("addzwzc");	
	if(win){
		win.hide();	
	}
}


<%-- function addScheme(a){
	var p;
	if(a.id=='addScheme'){
		p={};
	}else{
		p={ets00:$('#ets01').val(),ets01:$('#ets01_combo').val()};
	}
	$h.openWin('addScheme','pages.bzpj.PJZBScheme','指标方案维护',430,635,null,'<%=request.getContextPath()%>',null,
			p,true);
} --%>

function createSel2(){
	var ets01_combo = new Ext.form.TextField({
		emptyText :'输入职务全称',
		id:'search'
	});
	return ets01_combo;
}

function gljg(){
	var g;
	var p=document.getElementById('zwzc00id').value;
	if(p==null || p==''){
		$h.alert('','请选择职务！')
		return;
	}
	g={zwzc00:$('#zwzc00id').val(),a0192a:$('#a0192aid').val()}
	$h.openWin('ZWZCScheme','pages.zwzc.ZWZCScheme','关联机构管理',430,635,null,'<%=request.getContextPath()%>',null,
			g,true);
}
/* function createSel(){

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
							radow.doEvent('zwzcGrid.dogridquery');
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
} */
var tree;
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function () {
    var Tree = Ext.tree;
    tree = new Tree.TreePanel({
        id: 'group',
        el: 'tree-div',//目标div容器
        split: false,
        width: 270,
        height: 600,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//是否显示最上级节点
        autoScroll: true,
        animate: true,
        border: false,
        enableDD: false,
        containerScroll: true,
        loader: new Tree.TreeLoader({
            dataUrl: 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1'
        })
    });
    tree.on('click', treeClick);

    var root = new Tree.AsyncTreeNode({
        id: "-1"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(false, true, callback);

    var viewSize = Ext.getBody().getViewSize();
    var editgrid = Ext.getCmp('editgrid');

    var resizeobj = Ext.getCmp('tab');
    resizeobj.setHeight(viewSize.height - 19);//34 - 29
    var tableTab1 = document.getElementById("tableTab1");
    tableTab1.style.height = viewSize.height - 49 + "px";//87 82
    editgrid.setHeight(viewSize.height - 50);
    editgrid.setWidth(viewSize.width - 270);
});

function treeClick(node, e) {
    document.getElementById("checkedgroupid").value = node.id;
    radow.doEvent('zwzcGrid.dogridquery');
}

function a0201bChange(record){
	
	//任职结构类别 和 职务名称代码对应关系
	radow.doEvent('setZB08Code',record.data.key);

}



</script>


