<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page
	import="com.insigma.siis.local.pagemodel.publicServantManage.QueryPersonListPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%String ctxPath = request.getContextPath(); 

%>	
<style>
.x-panel-body{
height: 90%
}
.x-panel-bwrap{
height: 100%
}
.picOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}
.picInnerOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}
.picGroupOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png) !important;
}
#tree-div{
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">

function selectAllPeople(){
	var gridId = "persongrid";
	var fieldName = "personcheck";
	var checkAll = document.getElementById('checkAll');
	var value = checkAll.checked;
	var store = odin.ext.getCmp(gridId).store;
	var length = store.getCount();
	for(index=0;index<length;index++){
		store.getAt(index).set(fieldName,value);
	}
}
 
function deleteRow(){ 
	var sm = Ext.getCmp("persongrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteconfirm',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}
function impLrm(){
	odin.showWindowWithSrc("importLrmWin",contextPath+"/pages/publicServantManage/ImportLrm.jsp?businessClass=com.picCut.servlet.SaveLrmFile"); 
}
function impLrmx(){
	odin.showWindowWithSrc("importLrmxWin",contextPath+"/pages/publicServantManage/ImportLrmx.jsp?businessClass=com.picCut.servlet.SaveLrmFile"); 
}

function reloadtree(){return;
		var tree = Ext.getCmp("group");
		var node = tree.getSelectionModel().getSelectedNode();  
		if(node == null) { //没有选中 重载树  
			tree.root.reload();
			tree.expandPath(tree.getRootNode().getPath(),null,function(){addnode();});	
		} else {        //重载树 并默认选中上次选择的节点    
		    var path = node.getPath('id');  
		    tree.getLoader().load(tree.getRootNode(),  
		                function(treeNode) {  
		                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
		                                tree.getSelectionModel().select(oLastNode);
		                                /*if(oLastNode.attributes.href){
		                                	radow.doEvent('querybyid',oLastNode.id);
		                                }  */
		                                
		                            });  
		                    tree.expandPath(tree.getRootNode().getPath(),null,function(){addnode();});	
		                }, this);    
		}  
}

var tree;var personListTabId;
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
      	  id : 'group',	
          el : 'tree-div',//目标div容器
          split:false,
          monitorResize :true,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,//是否显示最上级节点
          autoScroll : true,
          animate : true,
          border:false,
          tbar:new Ext.Toolbar({items: [{  xtype: "checkbox",
						                   boxLabel : "包含下级",
						                   id:'isContain'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "现职人员",
						                   checked : true,
						                   id:'xzry'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "历史人员",
						                   id:'lsry'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "离退人员",
						                   id:'ltry'
						                }],
						         height:45,
						         layout :'column'}),
          enableDD : true,
          containerScroll : false,
          loader : new Tree.TreeLoader( {
              dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
          })
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      //root.expand();
      tree.expandPath(root.getPath(),null,function(){addnode();});
      
      personListTabId = parent.tabs.getActiveTab().id;
}); 

function exportAll(){
	odin.grid.menu.expExcelFromGrid('persongrid', null, null,null, false);
}


function setPageSize1(){
	//odin.grid.menu.setPageSize('memberGrid');
	var gridId = 'persongrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("请先查询出数据后再进行本操作！");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 200);
	} else {
		odin.error("非分页grid不能使用此功能！");
		return;
	}
}

<odin:menu property="exchangeMenu">
//<odin:menuItem text="导出LRM" property="exportLrmBtn" ></odin:menuItem>
<odin:menuItem text="导出" property="exportLrmxBtn" ></odin:menuItem>
<odin:menuItem text="导入" property="importLrmBtn"  handler="impLrm" ></odin:menuItem>
//<odin:menuItem text="导出个人数据" property="importHzbBtn"  ></odin:menuItem>
<odin:menuItem text="批量打印" property="batchPrint" isLast="true" ></odin:menuItem>
//<odin:menuItem text="导入LRMX" property="importLrmxBtn"  handler="impLrmx" ></odin:menuItem>
//<odin:menuItem text="导出任免审批表" property="exportPdfBtn" isLast="true" ></odin:menuItem>
</odin:menu>


</script>

<% 
	String picType = (String)(new QueryPersonListPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new QueryPersonListPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new QueryPersonListPageModel().areaInfo.get("areaid"));
	String manager = (String)(new QueryPersonListPageModel().areaInfo.get("manager"));
%>
<odin:hidden property="viewValue" value=""/>
<odin:hidden property="isContainHidden" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" /> 
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:toolBar property="floatToolBar" applyTo="toolDiv"  >
	<odin:textForToolBar text="<h3>人员信息维护</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="重名检测" id="verifyBtn" icon="image/u28.png" handler="openVerifyWin"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="人员增加" id="loadadd" icon="images/add.gif" cls="x-btn-text-icon" />	
	<odin:buttonForToolBar text="人员修改" id="modifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="人员删除" id="deletePersonBtn" icon="images/back.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="任免表" id="exchangeBtn" id="exchangeId" menu ="exchangeMenu" icon="images/icon/exp.png" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<!--<odin:buttonForToolBar text="人员修改" id="modifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="导出Lrmx" id="exportLrmxBtn" icon="images/icon/exp.png" cls="x-btn-text-icon"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="查询" id="btn1" icon="images/search.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="批量打印" id="batchPrint" icon="image/u117.png" cls="x-btn-text-icon" />	-->
	<odin:buttonForToolBar text="批量修改" id="betchModifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="事务提醒" id="warnWinBtn"  icon="images/warning.gif"/>
	<odin:buttonForToolBar text="导出个人数据" id="importHzbBtn"  icon="images/icon/exp.png"/>
	<odin:buttonForToolBar text="导出页面信息" id="exportAll" handler="exportAll" icon="images/icon/table.gif"  cls="x-btn-text-icon" />	
	<odin:buttonForToolBar text="设置每页条数" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" isLast="true" tooltip="用于设置每页记录条数" />
	<!--<odin:buttonForToolBar text="保存" id="save" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />-->
</odin:toolBar>


<div style="height: 100%">
	<div id="toolDiv"></div>
	<table height="100%" width="100%" cellspacing="0">
		<tr>
			<td valign="top" width="50px" id="tdtree">
				<div id="tree-div" style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;">
					
				</div>
			</td>
			<td><div id="divresize" style="height: 100%;width: 3px;cursor: e-resize;"></div></td>
			<td valign="top" id="tdgrid" >
			<div id="girdDiv" style="width: 100%">
				<odin:groupBox property="ssk" title="搜索框">
					<table width="100%">
						<tr>
							<odin:hidden property="downfile"/>
							<odin:textEdit property="a0101A" label="姓名" maxlength="18"></odin:textEdit>
							<odin:textEdit property="a0184A" label="身份证号" maxlength="18"></odin:textEdit>
							<td><odin:button text="查询" property="btn1"></odin:button></td>
							<td><input type="checkbox" id="checkAll" onclick="selectAllPeople()">
							<font style="font-size: 13">全选</font></td>
								
						</tr>
					</table>
				</odin:groupBox>
			
				<odin:editgrid property="persongrid" 
					bbarId="pageToolBar" isFirstLoadData="false" url="/" autoFill="false" topBarId="" pageSize="20">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="personcheck"/>
						<odin:gridDataCol name="a0148" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="age" />
						<odin:gridDataCol name="a0117" />
						<odin:gridDataCol name="a0141" />
						<odin:gridDataCol name="a0192a"/>
						
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0140"/>
						<odin:gridDataCol name="a0134" />
						<odin:gridDataCol name="a0165"/>
						<odin:gridDataCol name="a0160" />
						<odin:gridDataCol name="a0120"/>
						<odin:gridDataCol name="a0192d" />
						<odin:gridDataCol name="a0121"/>
						<odin:gridDataCol name="a0184" />
						
						<odin:gridDataCol name="qrzxl"/>
						<odin:gridDataCol name="zzxl" />
						
						<odin:gridDataCol name="a0000" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" align="center" hideable="false" dataIndex="personcheck" edited="true" gridName="persongrid"/>
						<odin:gridEditColumn2 header="姓名" edited="false" width="80" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="性别" edited="false" width="60" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="年龄" edited="false" width="60" align="center"  dataIndex="age" editor="text"/>
						<odin:gridEditColumn2 header="民族" edited="false" width="80" align="center"  dataIndex="a0117" editor="select" codeType="GB3304"/>
						<odin:gridEditColumn2 header="政治面貌" edited="false" width="80" align="center"  dataIndex="a0141" editor="select" codeType="GB4762"/>
						<odin:gridEditColumn2 header="工作单位及职务" edited="false" width="350" align="center"  dataIndex="a0192a" editor="text"/>
						<odin:gridEditColumn2 header="职务层次" edited="false" width="120" align="center"  dataIndex="a0148" editor="select" codeType="ZB09" />
						<%--<odin:gridColumn header="预览打印" edited="false" width="60" dataIndex="a0000" align="center" hidden="true" renderer="openEditer" />--%>
						
						<odin:gridEditColumn2 header="出生年月" edited="false" width="120" align="center"  dataIndex="a0107" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="入党时间" edited="false" width="120" align="center"  dataIndex="a0140" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="参加工作时间" edited="false" width="120" align="center"  dataIndex="a0134" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="管理类别" edited="false" width="120" align="center"  dataIndex="a0165" editor="select" hidden="true" codeType="ZB130"/>
						<odin:gridEditColumn2 header="人员类别" edited="false" width="120" align="center"  dataIndex="a0160" editor="select" hidden="true" codeType="ZB125"/>
						<odin:gridEditColumn2 header="级别" edited="false" width="120" align="center"  dataIndex="a0120" editor="select" hidden="true" codeType="ZB134"/>
						<odin:gridEditColumn2 header="职级" edited="false" width="120" align="center"  dataIndex="a0192d" editor="select" hidden="true" codeType="ZB133"/>
						<odin:gridEditColumn2 header="编制类型" edited="false" width="120" align="center"  dataIndex="a0121" editor="select" hidden="true" codeType="ZB135"/>
						<odin:gridEditColumn header="身份证号 " edited="false" width="160" align="center"  dataIndex="a0184" editor="text" hidden="true"/>
						<odin:gridColumn header="最高全日制学历" dataIndex="qrzxl" width="110" hidden="true" align="center"/>
						<odin:gridColumn header="最高在职学历" dataIndex="zzxl" width="110" hidden="true"  align="center"/>
						<odin:gridEditColumn2 header="id" edited="false" width="200" dataIndex="a0000" hideable="false" isLast="true" editor="text" hidden="true" />
					</odin:gridColumnModel>
				</odin:editgrid>
				</div>
			</td>
		</tr>
	</table>
</div>

<odin:window src="/blank.htm"  id="pdfViewWin" width="700" height="500" title="任免表预览界面" modal="true"/>	
<odin:window src="/blank.htm"  id="UpdateWin" width="320" height="215" title="参数修改窗口" />
<odin:window src="/blank.htm"  id="betchModifyWin" width="900" height="500" title="批量修改" modal="true"/>
<odin:window src="/blank.htm"  id="deletePersonWin" width="520" height="400" title="人员删除" modal="true"/>
<odin:window src="/blank.htm"  id="warnWin" width="520" height="450" title="事务提醒" modal="true"/>
<odin:window src="/blank.htm"  id="importLrmWin" width="520" height="490" title="任免表导入" modal="true"/>
<odin:window src="/blank.htm"  id="importLrmxWin" width="520" height="170" title="LRMX导入" modal="true"/>
<odin:window src="/blank.htm"  id="expTimeWin" width="450" height="130" title="系统提醒" modal="true"/>
<odin:window src="/blank.htm"  id="batchPrintTimeWin" width="520" height="170" title="系统提醒" modal="true"/>
<odin:window src="/blank.htm"  id="refreshWin" width="520" height="170" title="导出进度" modal="true"/>

<script type="text/javascript">
function canSaveSort(){
	var checkedgroupid = document.getElementById("checkedgroupid").value;
	if(""==checkedgroupid||checkedgroupid==null||"undefined"==checkedgroupid){
		Ext.Msg.alert('系统提示','请点击左侧的机构树！');
		return false;
	}
	if(checkedgroupid=='X001'||checkedgroupid=='X0010'||checkedgroupid=='X002'||checkedgroupid=='X003'){
		Ext.Msg.alert('系统提示','机构树中 "其他现职人员"、"离退人员" 及 "历史人员" 不能排序！');
		return false;
	}
	var isContain = document.getElementById("isContainHidden").value;
	if("1"==isContain){
		Ext.Msg.alert('系统提示','请不要选中包含下级、历史人员或离退人员！');
		return false;
	}
}

function UpBtn(){	
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('persongrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要排序的人员!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该人员已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}


function DownBtn(){	
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('persongrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的人员!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该人员已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}
//计算在页面的位置
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}
var win_addwin;
var win_addwinnew;
Ext.onReady(function() {
	var pgrid = Ext.getCmp('persongrid');

	var bbar = pgrid.getBottomToolbar();
	bbar.insertButton(11,[
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:100}),
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'上移',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'下移',
						    handler:DownBtn
						}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'保存排序',
						    handler:function(){
						    	if(canSaveSort()===false){
									return;
								}
								var d = bbar.getPageData(); 
								var pageNum = bbar.readPage(d); 
								radow.doEvent('personsort',bbar.initialConfig.pageSize+','+pageNum);
						    }
						})]);
	//页面调整
	Ext.getCmp('persongrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_persongrid'))[0]-4);
	Ext.getCmp('persongrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_persongrid'))[1]-2); 
	//alert();
	//document.getElementById('tree-div').style.height=document.getElementById('tdgrid').offsetHeight;
	//document.body.style.height=1;
	//alert(Ext.getBody().getViewSize().height);
	
	
	
	var dstore = pgrid.getStore();
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
					ddGroup : 'GridDD',
					copy : false,
					notifyDrop : function(dd,e,data){
						if(canSaveSort()===false){
							return false;
						}
						//选中了多少行
						var rows = data.selections;
						//拖动到第几行
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						Ext.Msg.confirm("系统提示","是否确认排序？",function(id) { 
							if("yes"==id){
								//修改store
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
						});
					}
				});



	win_addwin = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwin" name="iframe_addwin" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '人员新增窗口',
		layout : 'fit',
		width : 620,
		height : 415,
		closeAction : 'hide',
		closable : true,
		minimizable : false,
		maximizable : true,
		modal : false,
		maximized:true,
		id : 'addwin',
		bodyStyle : 'background-color:#FFFFFF',
		plain : true
	});
	
	win_addwinnew = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwinnew" name="iframe_addwinnew" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '人员新增窗口',
		layout : 'fit',
		width : 620,
		height : 415,
		closeAction : 'hide',
		closable : true,
		minimizable : false,
		maximizable : true,
		modal : false,
		maximized:true,
		id : 'addwinnew',
		bodyStyle : 'background-color:#FFFFFF',
		plain : true
	});
	
	
	
	//4028b8815382758d0153828d591b0002 人员信息维护
	//var tab=parent.tabs.getItem('4028b8815382758d0153828d591b0002');
	//tab.on('beforeclose',function(){
	//	for(var i=0;i<personTabsId.length;i++){
	//		parent.tabs.remove(parent.tabs.getItem(personTabsId[i]));
	//	}
	//});
	
	
	pgrid.store.on('load',function(){//翻页全选（伪全选，仅用作页面展示）
		var fieldName = "personcheck";
		var checkAll = document.getElementById('checkAll');
		var value = checkAll.checked;
		var store = pgrid.store;
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
		}
	});
});


function openEditer(value, params, record,rowIndex,colIndex,ds){
	
	if(value){
		return "<img src='"+contextPath+"/image/u117.png' title='' style='cursor:pointer' onclick=\"printView('"+value+"');\">";
	}else{
		return null;
	}
}

function printView(value){
	var flag = false; 
	if (confirm("任免表打印中是否包含拟任免信息？"))  {  
		flag = true;
	}  else  { 
		flag = false;
	}
	
	radow.doEvent('printView',value+','+flag);
}

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '其他现职人员', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001',
          leaf: false ,
          dblclick:"javascript:radow.doEvent('querybyid','X001')"
      });
      newnode.appendChild(new Ext.tree.TreeNode({ 
		  text: '职务为空的其他现职人员', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X0010',
          leaf: true ,
          dblclick:"javascript:radow.doEvent('querybyid','X0010')"
      }));
      nodeadd.appendChild(newnode);
      /*
      nodeadd.appendChild(new Ext.tree.TreeNode({ 
		  text: '离退人员', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X002',
  	      disabled:<=!GroupManagePageModel.personviewp%>,
          leaf: true ,
          dblclick:"javascript:<=GroupManagePageModel.personviewp%>?radow.doEvent('querybyid','X002'):''"
      }));
      nodeadd.appendChild(new Ext.tree.TreeNode({ 
		  text: '历史人员', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X003',
  	      disabled:<=!GroupManagePageModel.personviewp%>,
          leaf: true ,
          dblclick:"javascript:<=GroupManagePageModel.personviewp%>?radow.doEvent('querybyid','X003'):''"
      }));
      */
}
//人员新增修改窗口窗口
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh,param){
      var tab=parent.tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        personListTabId:personListTabId,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'&a0000='+aid+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
    }
	function reloadTree(){
		setTimeout(xx,1000);
	///window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
		setTimeout(cc,3000);
	}
	function cc(){
	w.close();
	}
	function reload(){
		odin.reset();
	}
</script>
<script type="text/javascript">

$(function() {
    //绑定需要拖拽改变大小的元素对象 
    bindResize(document.getElementById('divresize'),document.getElementById('tree-div'),document.getElementById('girdDiv'));
});

function bindResize(el,treeDiv,girdDiv) {
    //初始化参数 
    var els = treeDiv.style,
    girdEls = girdDiv.style,
    //鼠标的 X 和 Y 轴坐标 
    x = y = x2 = y2 = 0;
    //鼠标按下后事件
    $(el).mousedown(function(e) {
        //按下元素后，计算当前鼠标与对象计算后的坐标 
        x = e.clientX - treeDiv.offsetWidth;
   		y = e.clientY - treeDiv.offsetHeight;
   		//x2 = girdDiv.offsetWidth;
        //在支持 setCapture 做些东东 
        el.setCapture ? (
        //捕捉焦点 
        el.setCapture(),
        //设置事件 
        el.onmousemove = function(ev) {
            mouseMove(ev || event)
        },
        el.onmouseup = mouseUp
    ) : (
        //绑定事件 
        $(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp)
    );
        //防止默认事件发生 
        e.preventDefault();
    });
    //移动事件 
    function mouseMove(e) {
        //宇宙超级无敌运算中... 
        els.width = e.clientX - x + 'px';
        tree.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	Ext.getCmp('persongrid').setHeight(document.body.clientHeight-objTop(document.getElementById('forView_persongrid'))[0]-4);
    }
    //停止事件 
    function mouseUp() {
        //在支持 releaseCapture 做些东东 
        el.releaseCapture ? (
        //释放焦点 
        el.releaseCapture(),
        //移除事件 
        el.onmousemove = el.onmouseup = null
	    ) : (
	        //卸载事件 
	        $(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
	    );
    }
} 


function openPdfPage( winId, title, ParamPdfPath){
	var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.PdfView&pdfFilePath="+ParamPdfPath;
   
   	odin.openWindow(winId,title,url,700,500);
	
	
}
function openVerifyWin(){
	addTab('重名检测','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.RepeatQuery',false,false);
}

</script>