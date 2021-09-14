<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
#girdDiv{
	width:946px;
}
.x-panel-body{
height: 100%
}
.x-panel-bwrap{
height: 95%
}
.picOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}

.picInnerOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}

.picGroupOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png)
		!important;
}
#tree-div{
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
 function  styleInit(){ 
	//alert("1");
	$("#memberGrid").attr("style","height:490px");
	$(".x-panel-body").attr("style","height:440px");
	$(".x-panel-body").children().attr("style","height:440px");
	$(".x-grid3-scroller").attr("style","height:415px");
	$(".x-panel-noborder").attr("style","border: 2px solid rgb(195, 218, 249); border-image: none; width: 160px; height: 520px; overflow: auto; background-color: rgb(215, 228, 243);")
	$(".x-panel-body-noborder").attr("style","height:490px");
	//$(".x-panel-noborder").attr.style().
} 


function xianyin(){

	var v=document.getElementById("xianyin");
	var v2=document.getElementById("memberGrid");
	if(v.style.display==""){
		v.style.display='none';
		
		v2.style.height='490px';
		$(".x-panel-body").attr("style","height:440px");
		$(".x-panel-body").children().attr("style","height:440px");
		$(".x-grid3-scroller").attr("style","height:415px");
		$(".x-panel-noborder").attr("style","border: 2px solid rgb(195, 218, 249); border-image: none; width: 160px; height: 520px; overflow: auto; background-color: rgb(215, 228, 243);")
		$(".x-panel-body-noborder").attr("style","height:490px");
		$("#ext-gen46").attr("style","background-image: url('images/icon/icon_adown.gif');");
		$("#ext-gen46").text("展开");
	}else if(v.style.display=='none'){
		v.style.display=""
		
		v2.style.height='353px';
		$(".x-panel-body").attr("style","height:310px");
		$(".x-panel-body").children().attr("style","height:310px");
		$(".x-grid3-scroller").attr("style","height:284px");
		$(".x-panel-noborder").attr("style","border: 2px solid rgb(195, 218, 249); border-image: none; width: 160px; height: 520px; overflow: auto; background-color: rgb(215, 228, 243);")
		$(".x-panel-body-noborder").attr("style","height:490px");
		$("#ext-gen46").attr("style","background-image: url('images/icon/icon_upwards.gif');");
		$("#ext-gen46").text("关闭");
	}
}
	Ext
			.onReady(function() {
				var man = document.getElementById('manager').value;
				var Tree = Ext.tree;
				var tree = new Tree.TreePanel(
						{
							id : 'group',
							el : 'tree-div',//目标div容器
							split : false,
							width : 164,
							height : 500,
							minSize : 164,
							maxSize : 164,
							rootVisible : false,//是否显示最上级节点
							autoScroll : true,
							animate : true,
							border : false,
							enableDD : false,
							containerScroll : true,
							loader : new Tree.TreeLoader(
									{
							        	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
									})
						});
				//tree.on('dblclick', function(node, event) {
					//Ext.Msg.alert(node.id);
					//radow.doEvent('treeDblclick', node.id);
				//});
				tree.on('click', function(node, event) {
					//Ext.Msg.alert(node.text);
					$("#optionGroup").html(node.text);
					//radow.doEvent('treeDblclick', node.id);
				});
				var root = new Tree.AsyncTreeNode({
					checked : false,
					text : document.getElementById('ereaname').value,
					iconCls : document.getElementById('picType').value,
					draggable : false,
					id : document.getElementById('ereaid').value,//默认的node值：?node=-100
					href : "javascript:radow.doEvent('querybyid','"
							+ document.getElementById('ereaid').value + "')"
				});
				tree.setRootNode(root);
				tree.render();
				root.expand();
			});

	function reloadTree() {
		var tree = Ext.getCmp("group");
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
	
	function exportAll(){
		odin.grid.menu.expExcelFromGrid('memberGrid', null, null,null, false);
	}
	
	
	var seeNode = "";
	function selectSee(){
		var tree = Ext.getCmp("group");
		var root =tree.getRootNode();
		if(root.childNodes.length>0){
			seeNode="'"+root.childNodes[0].id+"',";
			loop(root.childNodes[0]);
		}
		radow.doEvent('selectSee', seeNode);
		seeNode="";
	}
	function selectSeeDown(){
		var checkedgroupid =document.getElementById('checkedgroupid').value;
		if(checkedgroupid==''){
			alert("请选择机构");
			return;
		}
		var tree = Ext.getCmp("group");
		var node = tree.getSelectionModel().getSelectedNode();
		//if(!node.isExpanded()){
		//	alert("选中单位下没有可见机构");
		//	return;
		//}
		var root =tree.getRootNode();
		if(root.childNodes.length>0){
			seeNode="'"+root.childNodes[0].id+"',";
			loop(root.childNodes[0]);
		}
		//alert(seeNode);
		var arr=seeNode.split(",");
		var nodeid = "'"+node.id+"'";
		seeNode="";
		var tag=0;
		for(var i=0;i<arr.length-1;i++){
			if(arr[i]==nodeid){
				tag=1;
			}
			if(tag==1){
				seeNode+=arr[i]+",";
			}
		}
		//alert(seeNode);
		radow.doEvent('selectSee', seeNode);
		seeNode="";
		tag =0;
	}
	function loop(node){
	  if(node.isExpanded()){
		  if(node.childNodes.length>0){
			  for(var i =0;i<node.childNodes.length ;i++){
				  seeNode=seeNode+"'"+node.childNodes[i].id+"',";
				  if(node.childNodes[i].isExpanded()){
				  		loop(node.childNodes[i]);
				  }
			  }
		  }
	  }
	}
<odin:menu property="exchangeMenu">
<odin:menuItem text="查询所有机构" property="selectAllOrgBtn" ></odin:menuItem>
<odin:menuItem text="查询选中单位下所有机构" property="selectAllDownBtn" ></odin:menuItem>
<odin:menuItem text="查询所有可见机构" property="selectSeeBtn1"  handler="selectSee" ></odin:menuItem>
<odin:menuItem text="查询选中单位下所有可见机构" property="selectSeeDownBtn1" handler="selectSeeDown"  isLast="true" ></odin:menuItem>
</odin:menu>
</script>

<%
	String picType = (String) (new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String) (new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String) (new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String) (new SysOrgPageModel().areaInfo.get("manager"));
	String ctxPath = request.getContextPath();
%>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="checkList" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:toolBar property="floatToolBar" applyTo="toolDiv"  >
	<%-- <odin:textForToolBar text="<h3>机构信息</h3>" /> --%>
	<odin:buttonForToolBar text="<h3>编辑模式</h3>" id="openUpdateOrgWinBtn" icon=""  />
	<odin:fill />
	
	<odin:buttonForToolBar text="批量修改" icon="image/u53.png" id="betchModifyBtn" tooltip="对选中机构进行批量修改" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="校验" icon="image/u53.png" id="dataVerify" tooltip="对选中机构进行数据校验" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" id="selectAllBtn" icon="images/search.gif" tooltip="对所在机构及其下级机构进行查询" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="重置" id="reset" icon="images/sx.gif" tooltip="清空输入框" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询列表" id="exchangeBtn" id="exchangeId" menu ="exchangeMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="导出" id="exportAll" handler="exportAll" isLast="true" icon="images/icon/exp.png" tooltip="导出全部数据" cls="x-btn-text-icon"/>
</odin:toolBar>

<div style="height: 100%" id="main">
	<div id="toolDiv"></div>
	<table >
		<tr>
			<td valign="top" width="50px" id="tdtree">
				<div id="tree-div" style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;background-color: #d7e4f3;">
				</div>
			</td>
			<td><div id="divresize" style="height: 100%;width: 3px;cursor: e-resize;"></div></td>
			<td valign="top" id="tdgrid" >
			<div id="girdDiv" style="width: 946px;" >
			<%-- <odin:panel title="高级查询" contentEl="condition"
					property="conditionPanel" collapsed="true" collapsible="true"></odin:panel> 
				  <div id="condition">  --%> 
				  
				<odin:toolBar property="floatToolBar2" applyTo="toolDiv2"  >
					<odin:textForToolBar text="高级查询" /> 
					<odin:fill />
					
					
					<odin:buttonForToolBar text="展开" id="exportAll2" isLast="true" icon="images/icon/icon_adown.gif"  cls="x-btn-text-icon"/>
				</odin:toolBar>
				<div id="toolDiv2"></div>
				<div id="xianyin" style="display: none">
				<odin:groupBox title="搜索框" >
					<div>
					<div style="float: left; width: 250;" >
						<table style="width: 100%">
							<tr>
								<tags:PublicOrgCheck label="选择机构" property="SysOrgTree" />
							</tr>
	
						</table>
					</div>
					<div style="float: left; width: 480;" >
						<table style="width: 100%">
							<tr>
							    <odin:textEdit property="b0101name" maxlength="50" label="机构名称" ></odin:textEdit>
								<tags:PublicTextIconEdit property="b0127" label="机构级别"
									codetype="ZB03" maxlength="8" readonly="true" />
							</tr>
							<tr>
								<tags:PublicTextIconEdit property="b0131" label="机构类别"
									codetype="ZB04" maxlength="8" readonly="true" />
								<tags:PublicTextIconEdit property="b0124" label="隶属关系"
									codetype="ZB87" maxlength="8" readonly="true" />
							</tr>
							<tr>
								<tags:PublicTextIconEdit property="b0117" label="机构所在政区"
									codetype="ZB01" maxlength="8" readonly="true" />
								<odin:select2 property="b0194" label="单位类型" codeType="B0194"
									multiSelect="true"></odin:select2>
							</tr>
						</table>
					</div>
					</div>
				</odin:groupBox> 
				</div>
				 <!-- </div>  -->
				 <div id=gridDiv2 ></div>
				<odin:editgrid property="memberGrid" applyTo="gridDiv2" title="下级机构列表" autoFill="false" hasRightMenu="false" 
					 bbarId="pageToolBar" pageSize="20"  topBarId="" isFirstLoadData="false" url="/">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="orgcheck" />
						<odin:gridDataCol name="b0111x" />
						<odin:gridDataCol name="b0111" />
						<odin:gridDataCol name="b0101" />
						<odin:gridDataCol name="b0114" />
						<odin:gridDataCol name="b0104" />
						<odin:gridDataCol name="b0127" />
						<odin:gridDataCol name="b0131" />
						<odin:gridDataCol name="b0124" />
						<odin:gridDataCol name="b0117" />
						<odin:gridDataCol name="b0194" />
						<odin:gridDataCol name="b0238" />
						<odin:gridDataCol name="b0239" />
						<odin:gridDataCol name="b0227" />
						<odin:gridDataCol name="b0232" />
						<odin:gridDataCol name="b0233" />
						<%-- <odin:gridDataCol name="b0188" />
						<odin:gridDataCol name="b0189" /> --%>
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0183" />
						<odin:gridDataCol name="b0185" />
						<odin:gridDataCol name="b0150" />
						<odin:gridDataCol name="b0190" />
						<odin:gridDataCol name="b0191a" />
						<odin:gridDataCol name="b0192" />
						<odin:gridDataCol name="b0193" />
						<odin:gridDataCol name="b0180" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="orgcheck" edited="true"
							hideable="false" gridName="memberGrid"
							checkBoxClick="getCheckList()"
							checkBoxSelectAllClick="getCheckList()" />
						<odin:gridColumn dataIndex="b0111x" width="110" header="机构编码" align="center" hidden="true"/> 
						<odin:gridColumn dataIndex="b0101" width="110" header="机构名称" align="center" />
						<odin:gridColumn dataIndex="b0114" width="110" header="机构编码" align="center" />	
						<odin:gridColumn dataIndex="b0104" width="110" header="简称" align="center" />	
						<odin:gridEditColumn2 header="机构级别" align="center" dataIndex="b0127" width="120" editor="select" codeType="ZB03" edited="false" />
						<odin:gridEditColumn2 header="机构类别" align="center" dataIndex="b0131" width="120" editor="select" codeType="ZB04" edited="false" />
						<odin:gridEditColumn2 header="隶属关系" align="center" dataIndex="b0124" width="120" editor="select" codeType="ZB87" edited="false" />
						<odin:gridEditColumn2 header="机构所在政区" align="center" dataIndex="b0117" width="120" editor="select" codeType="ZB01" edited="false" />
						<odin:gridEditColumn2 header="单位类型" align="center" dataIndex="b0194" width="120" editor="select" codeType="B0194" edited="false" />
						<odin:gridColumn dataIndex="b0238" width="130" header="参照公务员法管理审批时间" align="center" />
						<odin:gridColumn dataIndex="b0239" width="130" header="参照公务员法管理审批文号" align="center" />
						<odin:gridColumn dataIndex="b0183" width="130" header="正职领导" align="center" />
						<odin:gridColumn dataIndex="b0185" width="130" header="副职领导" align="center" />	
						<%-- <odin:gridColumn dataIndex="b0188" width="130" header="正职非领导" align="center" />
						<odin:gridColumn dataIndex="b0189" width="130" header="副职非领导" align="center" /> --%>
						<odin:gridColumn dataIndex="b0227" width="130" header="行政编制" align="center" />												
						<odin:gridColumn dataIndex="b0232" width="130" header="参照公务员法管理事业编制数" align="center" />
						<odin:gridColumn dataIndex="b0233" width="130" header="事业编制数" align="center" />
						<odin:gridColumn dataIndex="b0150" width="130" header="内设领导职数" align="center" />
						<odin:gridColumn dataIndex="b0190" width="130" header="内设正职领导" align="center" />
						<odin:gridColumn dataIndex="b0191a" width="130" header="内设副职领导" align="center" />
						<odin:gridColumn dataIndex="b0192" width="130" header="内设正职非领导" align="center" />
						<odin:gridColumn dataIndex="b0193" width="130" header="内设副职非领导" align="center" />
						<odin:gridColumn dataIndex="b0180" width="130" header="备注" align="center" isLast="true" />				
					</odin:gridColumnModel>
				</odin:editgrid>
				</div>
			</td>
		</tr>
	</table>
</div>
<odin:window src="/blank.htm" id="addOrgWin" width="800" height="500" title="新建下级机构页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="updateOrgWin" width="800" height="475" title="修改机构页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="transferSysOrgWin" width="520" height="400" title="整建制转移窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="batchTransferPersonnelWin" width="520" height="400" title="批量转移人员（机构合并）" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgSortWin" width="280" height="430" title="机构排序" modal="true"></odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="800" height="520" title="信息校验" modal="true"></odin:window>
<odin:window src="/blank.htm" id="exportOrgColumn" width="225" height="420" title="机构导出列选择" modal="true"></odin:window>
<odin:window src="/blank.htm" id="betchModifyWin" width="900"
	height="500" title="批量修改" modal="true" />
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
Ext.onReady(function() {
	Ext.getCmp('memberGrid').setHeight(Ext.getBody().getViewSize().height-170);  
	Ext.getCmp('memberGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_memberGrid'))[1]-2); 
	document.getElementById("toolDiv").style.width = document.body.clientWidth + "px";
	document.getElementById("main").style.width = document.body.clientWidth; + "px";
	//document.body.style.height=1;
});
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
        var tree = Ext.getCmp('group')
        tree.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	Ext.getCmp('memberGrid').getHeight=document.body.clientHeight-objTop(document.getElementById('memberGrid'))[0]+100;
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
	
	function getCheckList(){
		radow.doEvent('getCheckList');
	}
</script>
