<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgPersonImgVerifyPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<%String ctxPath = request.getContextPath(); 
%>	
<style>
.x-panel-body{
height: 95%
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
td{
border: 0px solid red;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
  var Tree = Ext.tree;
  var tree = new Tree.TreePanel( {
	  id:'group',
      el : 'tree-div',//目标div容器
      split:false,
      monitorResize :true,
      width: 270,
      height:460,
      minSize: 164,
      maxSize: 164,
      rootVisible: false,//是否显示最上级节点
      autoScroll : true,
      animate : true,
      border:false,
      tbar:new Ext.Toolbar({items: [{  xtype: "checkbox",
          boxLabel : "包含下级",
          id:'isContain'
       }],
height:25,
layout :'column'}),
      enableDD : false,
      containerScroll : true,
      loader : new Tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
      })
  });
	var root = new Tree.AsyncTreeNode({
		checked : false,
		text : document.getElementById('ereaname').value,
		iconCls : document.getElementById('picType').value,
		draggable : false,
		id : document.getElementById('ereaid').value,//默认的node值：?node=-100
		href : "javascript:radow.doEvent('querybyid','"
				+ document.getElementById('ereaid').value +"'')"
	});
	tree.setRootNode(root);
	tree.render();
	root.expand();
}); 

function reloadTree() {
	var tree = Ext.getCmp("group");
	   //tree.root.reload();
	   //var path =tree.getPath(groupid);
	   //tree.collapseAll();
	//tree对象  
	//获取选中的节点  
	var node = tree.getSelectionModel().getSelectedNode();  
	if(node == null) { //没有选中 重载树  
		tree.root.reload();
	} else {        //重载树 并默认选中上次选择的节点    
	    var path = node.getPath('id');  
	    tree.getLoader().load(tree.getRootNode(),  
	                function(treeNode) {  
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
	                                tree.getSelectionModel().select(oLastNode);  
	                            });  
	                }, this);    
	}  
}
function expExcelFromGrid(){
	odin.grid.menu.expExcelFromGrid('persongrid2', null, null,null, false);
	
}  
<odin:menu property="expMenu">
<odin:menuItem text="导出全部数据" property="getAll" handler="expExcelFromGrid" isLast="true"></odin:menuItem>
</odin:menu>
</script>

<%
SysOrgPageModel sys = new SysOrgPageModel();
String picType = (String) (sys.areaInfo
		.get("picType"));
String ereaname = (String) (sys.areaInfo
		.get("areaname"));
String ereaid = (String) (sys.areaInfo
		.get("areaid"));
String manager = (String) (sys.areaInfo
		.get("manager"));
String areaname = (String)new GroupManagePageModel().areaInfo.get("areaname");
String checktime = (String)request.getAttribute("checktime");
%>

<odin:hidden property="sql"/>
<odin:hidden property="checkList"/>

<odin:toolBar property="float" applyTo="toolDiv"  >
	<odin:textForToolBar text="<h3>照片检测 </h3>"/>
	<odin:fill />
	<odin:buttonForToolBar text="开始检测"  id="btn1" icon="images/icon/query.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="导出数据" id="expBtn" id="expId"
		menu="expMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="刷新" isLast="true" id="reload" icon="images/icon/refresh.png"></odin:buttonForToolBar>
</odin:toolBar>
<odin:hidden property="b0111OrimpID"/>	<!-- 父页面传入的需要校验的单位编码 -->

<div style="height: 100%;width: 100%">
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
			<odin:groupBox property="ssk" title="信息提示" >
					<table width="100%">
						<tr>
							<h3 style="color:red;font-size: 12;line-height:15px" >注：下列人员没有上传照片！双击可上传人员照片、编辑人员信息！ </h3>
						</tr>
						<tr>
							<td>
							<odin:textEdit property="checkedname"   style="overflow:auto; background-attachment:fixed;background-repeat:no-repeat;border-style:solid;border-color:#FFFFFF;background-color:white" label="当前选择机构："  value="" disabled="true" />
							</td>
							<td  width="70px">
							<odin:textEdit property="checktime" style="overflow:auto; background-attachment:fixed;background-repeat:no-repeat;border-style:solid;border-color:#FFFFFF;background-color:white" label="上次检测时间："  value="<%=OrgPersonImgVerifyPageModel.checktime %>" disabled="true" />
							</td>
							<td width="270px"></td>
							<odin:textEdit property="a0101A" label="姓名"  maxlength="36"></odin:textEdit>	
							<td>
							<odin:button text="检测结果中查询" property="doquery"></odin:button>
							</td>						
							<td width="150px">
							</td>
							<td width="400px">
							</td>
						</tr>					
					</table>
				</odin:groupBox>
				<odin:editgrid property="persongrid2" 
					bbarId="pageToolBar" isFirstLoadData="false" url="/" autoFill="false" topBarId="" pageSize="20">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
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
						<odin:gridEditColumn2 header="姓名" edited="false" width="80" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="性别" edited="false" width="60" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="年龄" edited="false" width="60" align="center"  dataIndex="age" editor="text"/>
						<odin:gridEditColumn2 header="民族" edited="false" width="80" align="center"  dataIndex="a0117" editor="select" codeType="GB3304"/>
						<odin:gridEditColumn2 header="政治面貌" edited="false" width="80" align="center"  dataIndex="a0141" editor="select" codeType="GB4762"/>
						<odin:gridEditColumn2 header="工作单位及职务" edited="false" width="350" align="center"  dataIndex="a0192a" editor="text"/>
						<odin:gridEditColumn2 header="职务层次" edited="false" width="120" align="center"  dataIndex="a0148" editor="select" codeType="ZB09" />
						<%--<odin:gridColumn header="预览打印" edited="false" width="60" dataIndex="a0000" align="center" hidden="true" renderer="openEditer" />--%>
						
						<odin:gridEditColumn2 header="出生年月" edited="false" width="120" align="center"  dataIndex="a0107" editor="text" />
						<odin:gridEditColumn2 header="入党时间" edited="false" width="120" align="center"  dataIndex="a0140" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="参加工作时间" edited="false" width="120" align="center"  dataIndex="a0134" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="管理类别" edited="false" width="120" align="center"  dataIndex="a0165" editor="select"  codeType="ZB130"/>
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
				<!-- <div id="tree-div"
					style='overflow: auto;height:100%; width:100%; border: 0px solid #c3daf9;'></div> -->
				    <odin:hidden property="area" value="<%=areaname%>" />
                    <odin:hidden property="id" />
                    <odin:hidden property="fid" />
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />					
					<odin:hidden property="sql"/>
					<odin:hidden property="additionalSql"/>
					<odin:hidden property="cueRowIndex"/>
					<odin:hidden property="saveName"/>
				    <odin:hidden property="codevalueparameter"/>
                    <odin:hidden property="checkList"/>
					<odin:hidden property="a0201b"/>      
				    <odin:hidden property="djgridString"/>
				    <odin:hidden property="zhgridString"/>
				</div>
			</td>
		</tr>
	</table>
</div>

<odin:window src="/blank.htm"  id="deletePersonWin" width="520" height="400" title="人员删除" modal="true"/>

<script type="text/javascript">




var win_addwin;
var win_addwinnew;
Ext.onReady(function() {

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
	
});






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
    	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        //personListTabId:personListTabId,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
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
	/*
	function change(obj){
		var choose = Ext.getCmp(obj).getValue();
		//alert(choose);
	}
	*/

	function query(){
		radow.doEvent('persongrid2.dogridquery');
	}
	
    Ext.onReady(function() {
    	//页面调整
    	 Ext.getCmp('persongrid2').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_persongrid2'))[0]-4);
    	 Ext.getCmp('persongrid2').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_persongrid2'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = document.body.clientWidth +'px';


    });
                                                                                                                                                       
   function checktime(){
	   document.all.checktime.value = "123";
	   
   } 
	
</script>