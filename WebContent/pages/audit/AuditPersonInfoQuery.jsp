<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>联审平台</title>
<link type="text/css" rel="stylesheet" href="<%=ctxPath%>/pages/huiyi/static/layui/css/layui.css"/>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/templete/templete-default.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/basejs/ext/ux/css/LockingGridView.css" />
<script src="<%=request.getContextPath()%>/pages/huiyi/static/layui/lay/modules/layer.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/ux/LockingGridView.js"></script>
<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
</head>
<odin:hidden property="auditType" />
<body>
	<div id="container">
		<table id="container-table" border="0" cellspacing='0'>
			<tr>
				<td valign="top" style="position: relative;" id="left-td">
					<div id="search-div">
						<%--<blockquote class="layui-elem-quote">联审</blockquote>--%>

						<div class="layui-btn-group layui-btn-fluid">
							<button type="button" id="sbtn" class="layui-btn layui-btn-fluid"  onclick="changeType('');">全部</button>
							<button type="button" id="sbtn01"  class="layui-btn layui-btn-fluid" style="margin-top:10px;" onclick="changeType('01');">担当作为好干部</button>
						</div>
					</div>
				</td>
				<td style="position: relative;" id="middle-td">
					<div id="divresize" onclick="foldLeftContainer(this)" style="background: url(image/right.png) #D6E3F2 no-repeat center center;"></div>
				</td>
				<td valign="top" id="right-td">
					<div id="container-div" class="scrollbar">
						<!-- 工具条中的按钮 -->
						<div style="width: 100%;height: 30px;background-color: #cedff5;" id="toolDiv">
							<table>
								<tr>
									<td><span><font style="font-size: 12px;">姓名/身份证</font></span></td>
									<td><input NAME="queryname" id="queryname" maxlength="36" /></td>
									<td><div id="btnToolBarDiv2"></div></td>
								</tr>
							</table>
						</div>
						<!-- 主列表 -->
						<div id="girdDiv" style="width: 100%;margin:0px 0px 0px 0px;" >	     
							<odin:hidden property="lsid" title="联审平台主键"/>
							<odin:hidden property="batchcheck"/>
							<odin:editgrid2 property="unitreGrid" title="" autoFill="false" width="100%" bbarId="pageToolBar" locked="true" pageSize="20" isFirstLoadData="false" url="/" >
								<odin:gridJsonDataModel id="idmodel" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="oid" />
									<odin:gridDataCol name="a0101" />
									<odin:gridDataCol name="a0184" />
									<odin:gridDataCol name="a0192a" />
									<odin:gridDataCol name="audit_type" />
									<odin:gridDataCol name="audit_result" />
									<odin:gridDataCol name="audit_details" />
									<odin:gridDataCol name="audit_remark" />
									<odin:gridDataCol name="p_sortid" />
									<odin:gridDataCol name="jw_result" />
									<odin:gridDataCol name="zzb_gb_result" />
									<odin:gridDataCol name="zzb_jd_result" />
									<odin:gridDataCol name="xf_result" />
									<odin:gridDataCol name="fy_result" />
									<odin:gridDataCol name="jcy_result" />
									<odin:gridDataCol name="fgw_result" />
									<odin:gridDataCol name="ga_result" />
									<odin:gridDataCol name="rlsb_result" />
									<odin:gridDataCol name="zrzy_result" />
									<odin:gridDataCol name="sthj_result" />
									<odin:gridDataCol name="wjw_result" />
									<odin:gridDataCol name="yjgl_result" />
									<odin:gridDataCol name="sj_result" />
									<odin:gridDataCol name="scjg_result" />
									<odin:gridDataCol name="tj_result" />
									<odin:gridDataCol name="gh_result" />
									<odin:gridDataCol name="sw_result" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel2>
								    <odin:gridRowNumColumn2/>
								    <odin:gridEditColumn2 header="id"  dataIndex="oid" align="center" editor="text" width="100" hidden="true" edited="false" locked="true"/>
									<odin:gridEditColumn2 header="姓名" dataIndex="a0101" align="center" edited="false" editor="text" width="100" locked="true"/>
									<odin:gridEditColumn2 header="身份证" dataIndex="a0184" align="center" edited="false" editor="text" width="150" locked="true"/>
									<odin:gridEditColumn2 header="工作单位及职务" dataIndex="a0192a" align="center" edited="false" editor="text" width="200" locked="true"/>
									<odin:gridEditColumn2 header="联审类别" dataIndex="audit_type" align="center" edited="false" editor="select" codeType="AUDIT_TYPE" width="200" />
									<odin:gridEditColumn2 header="联审意见" dataIndex="audit_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="联审结论" dataIndex="audit_details" align="center" edited="false" editor="text" width="200" />
									<odin:gridEditColumn2 header="备注" dataIndex="audit_remark" align="center" edited="false" editor="text" width="200" />
									<odin:gridEditColumn2 header="省纪委省监委" dataIndex="jw_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省委组织部干部综合处" dataIndex="zzb_gb_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省委组织部干部监督室" dataIndex="zzb_jd_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省信访局" dataIndex="xf_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省法院" dataIndex="fy_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省检察院" dataIndex="jcy_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省发改委" dataIndex="fgw_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省公安厅" dataIndex="ga_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省人力社保厅" dataIndex="rlsb_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省自然资源厅" dataIndex="zrzy_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省生态环境厅" dataIndex="sthj_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省卫健委" dataIndex="wjw_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省应急管理厅" dataIndex="yjgl_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省审计厅" dataIndex="sj_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省市场监管局" dataIndex="scjg_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省统计局" dataIndex="tj_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="省总工会" dataIndex="gh_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200" />
									<odin:gridEditColumn2 header="国家税务总局浙江省税务局" dataIndex="sw_result" align="center" edited="false" editor="select" codeType="AUDIT_IDEA" width="200"  isLast="true" />
								</odin:gridColumnModel2>
						</odin:editgrid2>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<odin:toolBar property="btnToolBar2" applyTo="btnToolBarDiv2">
		<odin:buttonForToolBar text="搜索" id="searchBtn" icon="image/icon_search.png" handler="search" isLast="true" />
	</odin:toolBar>

	<%--<odin:toolBar property="btnToolBar" applyTo="toolDiv">
		<odin:textForToolBar text=""/>
		<odin:fill/>
		<odin:buttonForToolBar text="提交联审名单" icon="images/add.gif" handler="impLsUser" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar  text="联审结论导入"  icon="images/i_2.gif" handler="impLsResult" isLast="true" />
	</odin:toolBar>--%>
	<!-- 该JS文件需在页面元素末尾加载 -->
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	<script type="text/javascript">  
    Ext.onReady(function() {
		//自适应大小
		window.onresize=resizeframe;
		resizeframe(); 
		
    	 var pgrid = Ext.getCmp('unitreGrid');
    		var bbar = pgrid.getBottomToolbar();
    		 bbar.insertButton(11,[
    							
    							new Ext.menu.Separator({cls:'xtb-sep'}),
    							new Ext.Button({
    								icon : 'images/keyedit.gif',
    								id:'setPageSize',
    							    text:'设置每页条数',
    							    handler:setPageSize
    							})
   
    							]);
    	 
    });
    
	function setPageSize(){
		var gridId = 'unitreGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
		}
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGrid.jsp";
			doOpenPupWin(url, "设置每页条数", 300, 150);
			
			
		} else {
			odin.error("非分页grid不能使用此功能！");
			return;
		}
	}
	
	//查询
	 function search(){
		radow.doEvent('unitreGrid.dogridquery');
	}
	/*function impLsUser(){

		$h.showWindowWithSrc3('LsUserImp', contextPath+ "/pages/unitedreview/LsUserImp.jsp", '导入窗口', 600, 200, null,null,
				{maximizable:false,resizable:false,draggable:false}, false);
	}*/
	/*function impLsResult(){

	}*/
	function changeType(type){
		document.getElementById('auditType').value=type;
		$("button[id^=sbtn]").removeClass("layui-bg-blue");
		$("#sbtn"+type).addClass("layui-bg-blue");

		radow.doEvent('unitreGrid.dogridquery');
	}


	//清除
	function clear(){
		document.getElementById('queryname').value="";
	}

	function openLsData(){
    	var lsid = document.getElementById("lsid").value;
		$h.openWin('lsData','pages.audit.AuditPersonInfo','联审录入',1200,800,lsid,contextPath,null,
				{maximizable:false,resizable:true,draggable:false}, true);
	}

	//左侧框 收缩与展开
	function foldLeftContainer(node) {
		if (node.style.background.indexOf("right.png") != -1) { // 收缩
			node.style.background = "url(image/left.png) #D6E3F2 no-repeat center center";

			leftTd.style.display = "none";

			rightTd.style.width = clientWidth - middleWidth + 'px';
			containerDiv.style.width = clientWidth - middleWidth + 'px';
			
			var grid=Ext.getCmp('unitreGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var gwidth=clientWidth-middleWidth;//获取当前div宽度
	    	grid.setWidth(gwidth);//重置grid的宽度
		} else { // 展开
			node.style.background = "url(image/right.png) #D6E3F2 no-repeat center center";

			leftTd.style.display = "block";

			rightTd.style.width = clientWidth - searchDiv.offsetWidth - middleWidth
					+ 'px';
			containerDiv.style.width = clientWidth - searchDiv.offsetWidth
					- middleWidth + 'px';
			
			var grid=Ext.getCmp('unitreGrid');
	        grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var gwidth=clientWidth - searchDiv.offsetWidth - middleWidth;//获取当前div宽度
	    	grid.setWidth(gwidth);//重置grid的宽度
		}
	}

	function resizeframe(){
		var grid = Ext.getCmp('unitreGrid');
		grid.setHeight(clientHeight - document.getElementById("toolDiv").offsetHeight);
	}
</script>
</body>
</html>
