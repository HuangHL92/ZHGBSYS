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
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
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
		if(node == null) { //û��ѡ�� ������  
			tree.root.reload();
			tree.expandPath(tree.getRootNode().getPath(),null,function(){addnode();});	
		} else {        //������ ��Ĭ��ѡ���ϴ�ѡ��Ľڵ�    
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
          el : 'tree-div',//Ŀ��div����
          split:false,
          monitorResize :true,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
          autoScroll : true,
          animate : true,
          border:false,
          tbar:new Ext.Toolbar({items: [{  xtype: "checkbox",
						                   boxLabel : "�����¼�",
						                   id:'isContain'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "��ְ��Ա",
						                   checked : true,
						                   id:'xzry'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "��ʷ��Ա",
						                   id:'lsry'
						                },
						                {  xtype: "checkbox",
						                   boxLabel : "������Ա",
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
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
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
		odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("���Ȳ�ѯ�����ݺ��ٽ��б�������");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "����ÿҳ����", 300, 200);
	} else {
		odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
		return;
	}
}

<odin:menu property="exchangeMenu">
//<odin:menuItem text="����LRM" property="exportLrmBtn" ></odin:menuItem>
<odin:menuItem text="����" property="exportLrmxBtn" ></odin:menuItem>
<odin:menuItem text="����" property="importLrmBtn"  handler="impLrm" ></odin:menuItem>
//<odin:menuItem text="������������" property="importHzbBtn"  ></odin:menuItem>
<odin:menuItem text="������ӡ" property="batchPrint" isLast="true" ></odin:menuItem>
//<odin:menuItem text="����LRMX" property="importLrmxBtn"  handler="impLrmx" ></odin:menuItem>
//<odin:menuItem text="��������������" property="exportPdfBtn" isLast="true" ></odin:menuItem>
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
	<odin:textForToolBar text="<h3>��Ա��Ϣά��</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="�������" id="verifyBtn" icon="image/u28.png" handler="openVerifyWin"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��Ա����" id="loadadd" icon="images/add.gif" cls="x-btn-text-icon" />	
	<odin:buttonForToolBar text="��Ա�޸�" id="modifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��Աɾ��" id="deletePersonBtn" icon="images/back.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="�����" id="exchangeBtn" id="exchangeId" menu ="exchangeMenu" icon="images/icon/exp.png" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<!--<odin:buttonForToolBar text="��Ա�޸�" id="modifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="����Lrmx" id="exportLrmxBtn" icon="images/icon/exp.png" cls="x-btn-text-icon"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="��ѯ" id="btn1" icon="images/search.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>-->
	<!--<odin:buttonForToolBar text="������ӡ" id="batchPrint" icon="image/u117.png" cls="x-btn-text-icon" />	-->
	<odin:buttonForToolBar text="�����޸�" id="betchModifyBtn" icon="images/keyedit.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��������" id="warnWinBtn"  icon="images/warning.gif"/>
	<odin:buttonForToolBar text="������������" id="importHzbBtn"  icon="images/icon/exp.png"/>
	<odin:buttonForToolBar text="����ҳ����Ϣ" id="exportAll" handler="exportAll" icon="images/icon/table.gif"  cls="x-btn-text-icon" />	
	<odin:buttonForToolBar text="����ÿҳ����" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" isLast="true" tooltip="��������ÿҳ��¼����" />
	<!--<odin:buttonForToolBar text="����" id="save" isLast="true"
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
				<odin:groupBox property="ssk" title="������">
					<table width="100%">
						<tr>
							<odin:hidden property="downfile"/>
							<odin:textEdit property="a0101A" label="����" maxlength="18"></odin:textEdit>
							<odin:textEdit property="a0184A" label="���֤��" maxlength="18"></odin:textEdit>
							<td><odin:button text="��ѯ" property="btn1"></odin:button></td>
							<td><input type="checkbox" id="checkAll" onclick="selectAllPeople()">
							<font style="font-size: 13">ȫѡ</font></td>
								
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
						<odin:gridEditColumn2 header="����" edited="false" width="80" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="�Ա�" edited="false" width="60" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="����" edited="false" width="60" align="center"  dataIndex="age" editor="text"/>
						<odin:gridEditColumn2 header="����" edited="false" width="80" align="center"  dataIndex="a0117" editor="select" codeType="GB3304"/>
						<odin:gridEditColumn2 header="������ò" edited="false" width="80" align="center"  dataIndex="a0141" editor="select" codeType="GB4762"/>
						<odin:gridEditColumn2 header="������λ��ְ��" edited="false" width="350" align="center"  dataIndex="a0192a" editor="text"/>
						<odin:gridEditColumn2 header="ְ����" edited="false" width="120" align="center"  dataIndex="a0148" editor="select" codeType="ZB09" />
						<%--<odin:gridColumn header="Ԥ����ӡ" edited="false" width="60" dataIndex="a0000" align="center" hidden="true" renderer="openEditer" />--%>
						
						<odin:gridEditColumn2 header="��������" edited="false" width="120" align="center"  dataIndex="a0107" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="�뵳ʱ��" edited="false" width="120" align="center"  dataIndex="a0140" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="�μӹ���ʱ��" edited="false" width="120" align="center"  dataIndex="a0134" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="�������" edited="false" width="120" align="center"  dataIndex="a0165" editor="select" hidden="true" codeType="ZB130"/>
						<odin:gridEditColumn2 header="��Ա���" edited="false" width="120" align="center"  dataIndex="a0160" editor="select" hidden="true" codeType="ZB125"/>
						<odin:gridEditColumn2 header="����" edited="false" width="120" align="center"  dataIndex="a0120" editor="select" hidden="true" codeType="ZB134"/>
						<odin:gridEditColumn2 header="ְ��" edited="false" width="120" align="center"  dataIndex="a0192d" editor="select" hidden="true" codeType="ZB133"/>
						<odin:gridEditColumn2 header="��������" edited="false" width="120" align="center"  dataIndex="a0121" editor="select" hidden="true" codeType="ZB135"/>
						<odin:gridEditColumn header="���֤�� " edited="false" width="160" align="center"  dataIndex="a0184" editor="text" hidden="true"/>
						<odin:gridColumn header="���ȫ����ѧ��" dataIndex="qrzxl" width="110" hidden="true" align="center"/>
						<odin:gridColumn header="�����ְѧ��" dataIndex="zzxl" width="110" hidden="true"  align="center"/>
						<odin:gridEditColumn2 header="id" edited="false" width="200" dataIndex="a0000" hideable="false" isLast="true" editor="text" hidden="true" />
					</odin:gridColumnModel>
				</odin:editgrid>
				</div>
			</td>
		</tr>
	</table>
</div>

<odin:window src="/blank.htm"  id="pdfViewWin" width="700" height="500" title="�����Ԥ������" modal="true"/>	
<odin:window src="/blank.htm"  id="UpdateWin" width="320" height="215" title="�����޸Ĵ���" />
<odin:window src="/blank.htm"  id="betchModifyWin" width="900" height="500" title="�����޸�" modal="true"/>
<odin:window src="/blank.htm"  id="deletePersonWin" width="520" height="400" title="��Աɾ��" modal="true"/>
<odin:window src="/blank.htm"  id="warnWin" width="520" height="450" title="��������" modal="true"/>
<odin:window src="/blank.htm"  id="importLrmWin" width="520" height="490" title="�������" modal="true"/>
<odin:window src="/blank.htm"  id="importLrmxWin" width="520" height="170" title="LRMX����" modal="true"/>
<odin:window src="/blank.htm"  id="expTimeWin" width="450" height="130" title="ϵͳ����" modal="true"/>
<odin:window src="/blank.htm"  id="batchPrintTimeWin" width="520" height="170" title="ϵͳ����" modal="true"/>
<odin:window src="/blank.htm"  id="refreshWin" width="520" height="170" title="��������" modal="true"/>

<script type="text/javascript">
function canSaveSort(){
	var checkedgroupid = document.getElementById("checkedgroupid").value;
	if(""==checkedgroupid||checkedgroupid==null||"undefined"==checkedgroupid){
		Ext.Msg.alert('ϵͳ��ʾ','�������Ļ�������');
		return false;
	}
	if(checkedgroupid=='X001'||checkedgroupid=='X0010'||checkedgroupid=='X002'||checkedgroupid=='X003'){
		Ext.Msg.alert('ϵͳ��ʾ','�������� "������ְ��Ա"��"������Ա" �� "��ʷ��Ա" ��������');
		return false;
	}
	var isContain = document.getElementById("isContainHidden").value;
	if("1"==isContain){
		Ext.Msg.alert('ϵͳ��ʾ','�벻Ҫѡ�а����¼�����ʷ��Ա��������Ա��');
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
		alert('��ѡ����Ҫ�������Ա!')
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
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('persongrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ�������Ա!')
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
//������ҳ���λ��
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
						    text:'����',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'����',
						    handler:DownBtn
						}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'��������',
						    handler:function(){
						    	if(canSaveSort()===false){
									return;
								}
								var d = bbar.getPageData(); 
								var pageNum = bbar.readPage(d); 
								radow.doEvent('personsort',bbar.initialConfig.pageSize+','+pageNum);
						    }
						})]);
	//ҳ�����
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
						//ѡ���˶�����
						var rows = data.selections;
						//�϶����ڼ���
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ������",function(id) { 
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
						});
					}
				});



	win_addwin = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwin" name="iframe_addwin" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '��Ա��������',
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
		title : '��Ա��������',
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
	
	
	
	//4028b8815382758d0153828d591b0002 ��Ա��Ϣά��
	//var tab=parent.tabs.getItem('4028b8815382758d0153828d591b0002');
	//tab.on('beforeclose',function(){
	//	for(var i=0;i<personTabsId.length;i++){
	//		parent.tabs.remove(parent.tabs.getItem(personTabsId[i]));
	//	}
	//});
	
	
	pgrid.store.on('load',function(){//��ҳȫѡ��αȫѡ��������ҳ��չʾ��
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
	if (confirm("������ӡ���Ƿ������������Ϣ��"))  {  
		flag = true;
	}  else  { 
		flag = false;
	}
	
	radow.doEvent('printView',value+','+flag);
}

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '������ְ��Ա', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001',
          leaf: false ,
          dblclick:"javascript:radow.doEvent('querybyid','X001')"
      });
      newnode.appendChild(new Ext.tree.TreeNode({ 
		  text: 'ְ��Ϊ�յ�������ְ��Ա', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X0010',
          leaf: true ,
          dblclick:"javascript:radow.doEvent('querybyid','X0010')"
      }));
      nodeadd.appendChild(newnode);
      /*
      nodeadd.appendChild(new Ext.tree.TreeNode({ 
		  text: '������Ա', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X002',
  	      disabled:<=!GroupManagePageModel.personviewp%>,
          leaf: true ,
          dblclick:"javascript:<=GroupManagePageModel.personviewp%>?radow.doEvent('querybyid','X002'):''"
      }));
      nodeadd.appendChild(new Ext.tree.TreeNode({ 
		  text: '��ʷ��Ա', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X003',
  	      disabled:<=!GroupManagePageModel.personviewp%>,
          leaf: true ,
          dblclick:"javascript:<=GroupManagePageModel.personviewp%>?radow.doEvent('querybyid','X003'):''"
      }));
      */
}
//��Ա�����޸Ĵ��ڴ���
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
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
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
    //����Ҫ��ק�ı��С��Ԫ�ض��� 
    bindResize(document.getElementById('divresize'),document.getElementById('tree-div'),document.getElementById('girdDiv'));
});

function bindResize(el,treeDiv,girdDiv) {
    //��ʼ������ 
    var els = treeDiv.style,
    girdEls = girdDiv.style,
    //���� X �� Y ������ 
    x = y = x2 = y2 = 0;
    //��갴�º��¼�
    $(el).mousedown(function(e) {
        //����Ԫ�غ󣬼��㵱ǰ����������������� 
        x = e.clientX - treeDiv.offsetWidth;
   		y = e.clientY - treeDiv.offsetHeight;
   		//x2 = girdDiv.offsetWidth;
        //��֧�� setCapture ��Щ���� 
        el.setCapture ? (
        //��׽���� 
        el.setCapture(),
        //�����¼� 
        el.onmousemove = function(ev) {
            mouseMove(ev || event)
        },
        el.onmouseup = mouseUp
    ) : (
        //���¼� 
        $(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp)
    );
        //��ֹĬ���¼����� 
        e.preventDefault();
    });
    //�ƶ��¼� 
    function mouseMove(e) {
        //���泬���޵�������... 
        els.width = e.clientX - x + 'px';
        tree.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	Ext.getCmp('persongrid').setHeight(document.body.clientHeight-objTop(document.getElementById('forView_persongrid'))[0]-4);
    }
    //ֹͣ�¼� 
    function mouseUp() {
        //��֧�� releaseCapture ��Щ���� 
        el.releaseCapture ? (
        //�ͷŽ��� 
        el.releaseCapture(),
        //�Ƴ��¼� 
        el.onmousemove = el.onmouseup = null
	    ) : (
	        //ж���¼� 
	        $(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
	    );
    }
} 


function openPdfPage( winId, title, ParamPdfPath){
	var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.PdfView&pdfFilePath="+ParamPdfPath;
   
   	odin.openWindow(winId,title,url,700,500);
	
	
}
function openVerifyWin(){
	addTab('�������','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.RepeatQuery',false,false);
}

</script>