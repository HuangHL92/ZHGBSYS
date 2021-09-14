<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%
String areaname = (String)new GroupManagePageModel().areaInfo.get("areaname");
%>
<style>
table{
style:cellPadding="0"  !important;
}
div{
margin:0;padding:0;
}


</style>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	    <odin:buttonForToolBar text="导出备案表" id="getSheet"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon"  isLast="true"/>
</odin:toolBar>

	

<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {
  	  id:'group',
        el : 'tree-div',//目标div容器
        split:false,
        width: 239,
        height:450,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//是否显示最上级节点
        autoScroll : true,
        animate : true,
        border:false,
        tbar:new Ext.Toolbar({height:23,items: [{  xtype: "checkbox",
            boxLabel : "包含下级",
            id:'isContain'
         }],
//  height:45,
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


function doQuery() {
	//alert(changeNode);
	radow.doEvent('dogrant',changeNode);
}

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
     document.getElementById("plV").style.display='block';  
	}
}

function gridcq(){
		radow.doEvent("gridcq");
}

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
%>
<!-- <table>
  <tr>
    <td height="25"></td>
  </tr>
</table> -->

<div id="groupTreeContent" style="height: 100% ;">
<div id="btnToolBarDiv"></div>
<table width="100%">
	<tr>
		<td width="175" style="background-color: #cedff5">
		<table width="175">
			<tr>
				<td valign="top">
<odin:tab id="tab" width="240"  tabchange="grantTabChange"  >
	<odin:tabModel>
		<odin:tabItem title="机构树" id="tab1"></odin:tabItem>
		<odin:tabItem title="人员列表" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="tab1" className="tab" >
		<div id="tree-div" style="overflow: auto; border: 0px solid red;"></div>
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
	</odin:tabCont>
	<odin:tabCont itemIndex="tab2" className="tab" >
		<div id="plV" style="display:none;overflow: auto; border: 0px solid red;">
			<odin:editgrid property="plgrid" pageSize="15" bbarId="pageToolBar"  autoFill="false" >
				<odin:gridJsonDataModel >
					<odin:gridDataCol name="personcheck"/>
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0104"/>
					<odin:gridDataCol name="a0107" />
					<odin:gridDataCol name="a0192" />
					<odin:gridDataCol name="a0120" />
					<odin:gridDataCol name="a0180" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn ></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" width="40" editor="checkbox" dataIndex="personcheck" edited="true" gridName="persongrid"/>			    
					<odin:gridColumn dataIndex="a0000" width="110" header="人员id" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="a0101" width="110" header="姓名" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0104" width="60" header="性别" align="center" editor="select" edited="false"  codeType="GB2261" hidden="true"/>
					<odin:gridColumn dataIndex="a0107" width="100" header="出生日期" align="center" editor="text" edited="false" hidden="true"/>
					<odin:gridColumn dataIndex="a0192" width="130" header="职务" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="a0120" width="130" header="政治面貌" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="a0180" width="130" header="备注" align="center" isLast="true" hidden="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
						data:[]
					}
				</odin:gridJsonData>
			</odin:editgrid>
		</div>	
	</odin:tabCont>
</odin:tab>				    	
				</td>	
			</tr>
		</table>
		</td>
		<td>
			<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/FiledownServlet"  target="simpleExpFrame">
			<div id="mp">
				<table width="100%">
					<tr>
						<td align="center">
						<odin:groupBox>
						<table>						
						<tr>
				        <td>
				           <table>
				           <tr>
						   <td><odin:textEdit property="gbs" label="国家行政编制数"></odin:textEdit></td>
						   </tr>
						   <tr>
						   <td><odin:textEdit property="djs" label="登记人员数"></odin:textEdit></td>  
				           </tr>
				           </table>
				        </td>
				        <td>
				           <table>
				           <tr>
						   <td><odin:textEdit property="sys" label="实有人员数"></odin:textEdit></td>	
						   </tr>
						   <tr>
						   <td><odin:textEdit property="zhs" label="暂缓登记人员数"></odin:textEdit></td>
				           </tr>
				           </table>	
				           <odin:hidden property="djgridString"/>
				           <odin:hidden property="zhgridString"/>				         
				        </td>
						</tr>
						</table>
						</odin:groupBox>
						</td>
					</tr>
					<tr>
						<td>
		<odin:hidden property="plcueRowIndex"/>	
		<odin:hidden property="djcueRowIndex"/>		
		<odin:hidden property="zhcueRowIndex"/>							
		<odin:toolBar property="toolBar1">
		
		<odin:buttonForToolBar text="添加" id="addDj" icon="images/icon/right.gif"></odin:buttonForToolBar>		
		<odin:buttonForToolBar text="删除"  id="delDj" icon="images/icon/error.gif" handler="delEmpRowDjgrid" isLast="true"></odin:buttonForToolBar>
		
	   </odin:toolBar>			
            <odin:editgrid property="djgrid" topBarId="toolBar1"   title="登记人员名单"
			width="400" height="210"  autoFill="false" >
			<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0104"/>
					<odin:gridDataCol name="a0107" />
					<odin:gridDataCol name="a0192" />
					<odin:gridDataCol name="a0120" />
					<odin:gridDataCol name="a0180" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
			    <odin:gridRowNumColumn ></odin:gridRowNumColumn>
				    <odin:gridColumn dataIndex="a0000" width="110" header="人员id" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="a0101" width="110" header="姓名" align="center"/>
					<odin:gridColumn dataIndex="a0104" width="60" header="性别" align="center"/>
					<odin:gridColumn dataIndex="a0107" width="100" header="出生日期" align="center" editor="text" edited="false" />
					<odin:gridColumn dataIndex="a0192" width="130" header="职务" align="center" />
					<odin:gridColumn dataIndex="a0120" width="130" header="级别" align="center" />
					<odin:gridColumn dataIndex="a0180" width="130" header="备注" align="center" isLast="true" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	        </odin:editgrid>					
						</td>
					</tr>
					<tr>
						<td>
		<odin:toolBar property="toolBar2">
		
		<odin:buttonForToolBar text="添加" id="addZh"  icon="images/icon/right.gif"></odin:buttonForToolBar>		
		<odin:buttonForToolBar text="删除"  id="delZh" icon="images/icon/error.gif" handler="delEmpRowZhgrid" isLast="true"></odin:buttonForToolBar>
		
	   </odin:toolBar>								
            <odin:editgrid property="zhgrid" topBarId="toolBar2"  title="暂缓登记人员名单"
			width="400" height="210"  autoFill="false" >
			<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0104"/>
					<odin:gridDataCol name="a0107" />
					<odin:gridDataCol name="a0192" />
					<odin:gridDataCol name="a0120" />
					<odin:gridDataCol name="a0180" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
			    <odin:gridRowNumColumn ></odin:gridRowNumColumn>
				    <odin:gridColumn dataIndex="a0000" width="110" header="人员id" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="a0101" width="110" header="姓名" align="center"/>
					<odin:gridColumn dataIndex="a0104" width="60" header="性别" align="center"/>
					<odin:gridColumn dataIndex="a0107" width="100" header="出生日期" align="center" editor="text" edited="false" />
					<odin:gridColumn dataIndex="a0192" width="130" header="职务" align="center" />
					<odin:gridColumn dataIndex="a0120" width="130" header="级别" align="center" />
					<odin:gridColumn dataIndex="a0180" width="130" header="备注" align="center" isLast="true" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	        </odin:editgrid>							
						</td>
					</tr>
				</table>
				</div>
			</form>	
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
function getShowFour(){
	odin.ext.getCmp('tab').activate('tab2'); 
} 
function delEmpRowPlgrid(a,b,c){
	var grid = odin.ext.getCmp('plgrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);
				
	}
	grid.view.refresh();	
}

function delEmpRowDjgrid(a,b,c){
	var grid = odin.ext.getCmp('djgrid');
	var gridpl = odin.ext.getCmp('plgrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var selected;
	for(var i=0;i<selections.length;i++){
		selected = selections[i];
		store.remove(selected);
		gridpl.store.add(selected);	
	}
	if(selected==null||typeof(selected)=='undefined'){
	    alert("请选择要删除的项！");
	    return;
	}
	
	grid.view.refresh();
	gridpl.view.refresh();
	if(document.getElementById("djs").value>=1)
	document.getElementById("djs").value=document.getElementById("djs").value-1;			
}
function delEmpRowZhgrid(a,b,c){
	var grid = odin.ext.getCmp('zhgrid');
	var gridpl = odin.ext.getCmp('plgrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var selected;
	for(var i=0;i<selections.length;i++){
        selected = selections[i];
		store.remove(selected);
		gridpl.store.add(selected);					
	}
	if(selected==null||typeof(selected)=='undefined'){
	    alert("请选择要删除的项！");
	    return;
	}

	grid.view.refresh();
	gridpl.view.refresh();
	if(document.getElementById("zhs").value>=1)
	document.getElementById("zhs").value=document.getElementById("zhs").value-1;			
}

function getDjgridString(){
	var djgridString=document.getElementById("djgridString").value;
	return djgridString;
}

function getZhgridString(){
	var zhgridString=document.getElementById("zhgridString").value;
	return zhgridString;
}

function downLoadTmp(){
	var dw=document.getElementById("ereaname").value;
	var gbs=document.getElementById("gbs").value;
	var sys=document.getElementById("sys").value;
	var djs=document.getElementById("djs").value;
	var zhs=document.getElementById("zhs").value;
	var djgridString=document.getElementById("djgridString").value;
	var zhgridString=document.getElementById("zhgridString").value;
	var checkList = 3;
	if(typeof(djgridString)!='undefined'&&djgridString!=''){
		if(zhs!=''&&zhs!=0){
			doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&babdj=1"+"&checkList="+checkList+"&tmpType=['3', '公务员登记备案表'],['4', '参照公务员法管理机关（单位）公务员登记备案表']")),
		 			"下载文件", 500, 160);
		}else{
			doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&babdj=1"+"&checkList="+checkList+"&tmpType=['13', '公务员登记备案表'],['14', '参照公务员法管理机关（单位）公务员登记备案表']")),
		 			"下载文件", 500, 160);
		}
	}else{
		alert("没有登记任何人员不能导出！");
	}
 	  
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('djgrid').setHeight((Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_djgrid'))[0])*0.5-4);
	 Ext.getCmp('djgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_djgrid'))[1]-4); 
	 Ext.getCmp('zhgrid').setHeight(Ext.getCmp('djgrid').getHeight());
	 Ext.getCmp('zhgrid').setWidth(Ext.getCmp('djgrid').getWidth()); 
	 document.getElementById('btnToolBarDiv').style.width = document.body.clientWidth-4;
	 //	 document.getElementById('plgrid').style.height = document.getElementById('tree-div').style.height;
	 
//	 Ext.getCmp('tree').setHeight(document.getElementById('tree-div').style.height);
	 
	 //document.getElementById('tree-div').style.height = Ext.getCmp('djgrid').getHeight()*2+50;
	 //document.getElementById('plv').style.height = document.getElementById('tree-div').style.height;
	 document.getElementById('tree-div').style.height = 460;
	 document.getElementById('plv').style.width = 239;
	 document.getElementById('plv').style.height = 460;
	 odin.ext.getCmp('plgrid').setHeight(430);
	 
	 //document.getElementById('ext-gen20').style.height = document.getElementById('tree-div').style.height;
	 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 
});
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
</script>