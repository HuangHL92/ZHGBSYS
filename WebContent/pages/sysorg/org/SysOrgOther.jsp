<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%
	String ctxPath = request.getContextPath(); 
	String orgInfoGridPageSize=(String)request.getSession().getAttribute("SOOPageSize");//设置每页显示的条数

%>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
 <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/third-party/zeroclipboard/ZeroClipboard.min.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.all.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败--> 
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文--> 
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
.x-tab-panel-header{padding-bottom:0px;}
.x-panel-bwrap,.x-panel-body {
	height: 100%
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

</style>
<script type="text/javascript">
var tree;
//机构树初始化
	Ext.onReady(function() {
				var man = document.getElementById('manager').value;
				var Tree = Ext.tree;
				tree = new Tree.TreePanel(
						{
							id : 'group',
							el : 'tree-div',//目标div容器
							split : false,
							monitorResize :true,
							width : 250,
							//title :'机构树',
							rootVisible : false,//是否显示最上级节点
							autoScroll : true,//当内容超过预设的高度时自动出现滚动条。
							collapseMode : 'mini',
							animate : true,
							border : false,
							enableDD : false,//设置树的节点是否可以拖动。
							containerScroll : true,//是否将树形面板注册到滚动管理器ScrollManager中
							loader : new Tree.TreeLoader(//树节点的加载器
									{
							        	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
							        	//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataTree'
									 /* 树节点展开时，当前节点的id会作为请求参数被发送到服务器，在服务器可以通过node参数名进行获取。
										主要配置项：
										dataUrl：获取子节点的URL地址。
										baseAttrs：子节点的基本属性对象，该对象中的属性将被添加到树加载器创建的所有子节点上。优先服务器返回的同名属性值。 */
									})
						});
				
				var root = new Tree.AsyncTreeNode({
					checked : false,//当前节点的选择状态
					text : document.getElementById('ereaname').value,//节点上的文本信息
					iconCls : document.getElementById('picType').value,//应用到节点图标上的样式
					draggable : false,//是否允许拖曳
					id : document.getElementById('ereaid').value,//默认的node值：?node=-100
					href : "javascript:radow.doEvent('querybyid','"//节点的连接属性
							+ document.getElementById('ereaid').value + "')"
				});
				tree.setRootNode(root);//设置根结点
				//tree.addListener('click', BiaoZhunClick);
				tree.render();//显示
				root.expand(false,true, callback);//默认展开
				//root.expand();
			});
	/* var dbNode="";
    function BiaoZhunClick(node, e) {
    	e.stopEvent();
    	e.stopPropagation();
    	e.preventDefault();
		setTimeout(function(){ unselect(node);},100);
    }
    function unselect(node){
    	node.unselect();
    	if(dbNode!=""){
    		Ext.getCmp('group').getNodeById(dbNode).select();
    	}
    }
    function selectNode(id){
    	dbNode=id;
    	setTimeout(function(){ selectNode1(id);},100);
    }
    function selectNode1(id){
    	Ext.getCmp('group').getNodeById(id).select();
    } */
	var callback = function (node){//仅展看下级
		if(node.hasChildNodes()) {
			node.eachChild(function(child){
				child.expand();
			})
		}
	}
	function saveb01(){
		radow.doEvent('saveBtn.onclick');
	}
	//人员新增修改窗口窗口
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh){
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
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
    }
	function reloadTree() {
		var tree = Ext.getCmp("group");
		   
		//获取选中的节点  
		var node = tree.getSelectionModel().getSelectedNode();  
		if(node == null) { //没有选中 重载树  
			tree.root.reload();
			tree.root.expand(false,true, callback);//默认展开
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
	
	function cancelBtnClick(){
		radow.doEvent('querybyid', document.getElementById('b0111').value);
	}
		
<odin:menu property="exchangeMenu">
<odin:menuItem text="下级机构领导职数配置" property="lowOrgLeaderBtn" ></odin:menuItem>
<odin:menuItem text="下级机构编制人员对比" property="lowOrgPeopleBtn" ></odin:menuItem>
<odin:menuItem text="多机构领导职数配置" property="manyOrgLeaderBtn"  ></odin:menuItem>
<odin:menuItem text="多机构编制人员对比" property="manyOrgPeopleBtn" isLast="true" ></odin:menuItem>
</odin:menu>

</script>

<!-- 弹出窗的公共方法 -->
<script type="text/javascript"> 
	function tongji(param){
		$h.openWin('statisticsWin','pages.sysorg.org.StatisticsWin','机构数据统计页面（包含下级）',1500,600,param,'<%=ctxPath%>');
	}
	function lowOrgLeaderYuLan(param){
		$h.openWin('SysPreview','pages.sysorg.org.SysPreview','职数编制统计',1000,550,param,'<%=ctxPath%>');
	}
	function openQueryWin(){
		var newWin_ = $h.getTopParent().Ext.getCmp('queryGroupWin');
		if(!newWin_){
			var win = $h.openWin('queryGroupWin','pages.sysorg.org.QueryGroup','机构查询',1150,550,'','<%=ctxPath%>',null,
					{modal:false,collapsed:false,collapsible:true,titleCollapse:true,maximized:false,top:40});
			win.collapse(false);
			win.expand(true);
		}else{
			newWin_.expand(true); 
		}
	}
	function JudgeRepeatWin(){
		$h.openWin('JudgeRepeatWin','pages.sysorg.org.JudgeRepeat','机构查重',1150,550,'','<%=ctxPath%>');
		<%-- $h.openWin('SysPreview','pages.sysorg.org.SysPreview','机构查重',1000,550,'','<%=ctxPath%>'); --%>
	}
	Ext.onReady(function() {
		try{
			$('#sb0227').css('background', '#C4D5FF');
			$('#sb0183').css('background', '#C4D5FF');
			$('#sb0185').css('background', '#C4D5FF');
			$('#sb0235').css('background', '#C4D5FF');
			$('#sb0233').css('background', '#C4D5FF');
			$('#sb0236').css('background', '#C4D5FF');
			$('#sb0234').css('background', '#C4D5FF');
			$('#sb0150').css('background', '#C4D5FF');
			$('#sb0190').css('background', '#C4D5FF');
			$('#sb0191a').css('background', '#C4D5FF');
			$('#sb0232').css('background', '#C4D5FF');
			$('#hideDiv').hide();
			$('#gird').hide();
		}catch(err){
			
		}
	});
	<odin:menu property="updateM">
	<odin:menuItem text="新建下级机构" handler="addOrgWinBtnright"></odin:menuItem>
	<odin:menuItem text="修改" handler="updateWinBtnright" isLast="true"></odin:menuItem>
/* 	<odin:menuItem text="机构删除" handler="deleteOrgBtnright" ></odin:menuItem> */
	</odin:menu>
	<odin:menu property="BzzsMenu">
	/* <odin:menuItem text="事业单位编制核定表" property="Sydw"></odin:menuItem>
	<odin:menuItem text="行政机构编制核定表" property="Xzjg"></odin:menuItem>
	<odin:menuItem text="参公事业单位编制核定表" property="Cgsy" ></odin:menuItem> */
	<odin:menuItem text="市直单位职数情况汇总表" property="SZDW" handler="SZDWHZB" ></odin:menuItem>
	<odin:menuItem text="区、县（市）领导班子职数统计表" property="QXSLDBZ" handler="QXSLDBZHZB" ></odin:menuItem>
	<odin:menuItem text="区、县（市）平台职数统计表" property="QXSPT" handler="QXSPTHZB" ></odin:menuItem>
	<odin:menuItem text="国企高校职数情况汇总表" property="GQGX" handler="GQGXHZB" ></odin:menuItem>
	</odin:menu>
	function orgKind(){alert('调研中...');}
	
	
	function SZDWHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('SZDWHZB',contextPath + "/pages/sysorg/hzb/SZDWHZB.jsp?a=1",'市直单位职数情况汇总表',1200,600,null,{maximizable:true,resizable:true});
	}
	function GQGXHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('SZDWHZB',contextPath + "/pages/sysorg/hzb/SZDWHZB.jsp?gqgx=gqgx",'国企高校职数情况汇总表',1200,600,null,{maximizable:true,resizable:true,gqgx:'gqgx'});
	}
	function QXSLDBZHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('QXSLDBZHZB',contextPath + "/pages/sysorg/hzb/QXSLDBZHZB.jsp?a=1",'区、县（市）领导班子职数统计表',1200,600,null,{maximizable:true,resizable:true});
	}
	function QXSPTHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('QXSPTHZB',contextPath + "/pages/sysorg/hzb/QXSPTHZB.jsp?a=1",'区、县（市）平台职数统计表',1200,600,null,{maximizable:true,resizable:true});
	}
	
	
	
	//机构画像
	function  orgPortrait(){
	
		radow.doEvent("orgPortrait");
		
	}
	
	function insertInfo(groupid){
		//alert(groupid);	
		/* var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数  
		 if (window.screen) {  
		    var ah = screen.availHeight - 30;  
		    var aw = screen.availWidth - 90;  
		    fulls += ",height=" + ah;  
		    fulls += ",innerHeight=" + ah;  
		    fulls += ",width=" + aw;  
		    fulls += ",innerWidth=" + aw;  
		    fulls += ",resizable"  
		 } else {  
		    fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually  
		 }
		window.open(ctxPath+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.OrgPortrait&b0111="+groupid,"画像",fulls);
	 */
		var contextPath = '<%=request.getContextPath()%>';

			$h.openPageModeWin('duty','pages.sysorg.org.OrgPortrait','机构画像',1151,880,groupid,contextPath,null,
					{modal:true,collapsed:false,collapsible:true,titleCollapse:false,maximized:false});
		
	
	}
	
	//右键新建下级机构
	
	
	function addOrgWinBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("addOrgWinBtnFunc",id);
	}
	//列表右键修改
	function updateWinBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("updateWinBtnFunc",id+","+selections[0].data.type);
	}
	//列表右键机构删除
	function deleteOrgBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("deleteOrgBtnFunc",id+","+selections[0].data.type);
	}
	function show(obj){
		document.getElementById(obj).style.display='block';
	}
	function hide(obj){
		document.getElementById(obj).style.display='block';
	}
	function funcAfterLoad(){
		document.getElementById("scanwrongfulunid").value='false';//设置显示机构不合法列表标志为 false
		document.getElementById("scanlegalperson").value='false';//设置法人单位编码为空标志为 false
	}
	function impTest() {
		$h.showWindowWithSrc('simpleExpWin2', '<%=ctxPath %>'
			+ "/pages/sysorg/org/DataVerify2.jsp?i=1",'导入窗口',530,190);	
	}
</script>


<%
	SysOrgPageModel sysorgpagemodel=new SysOrgPageModel();
	String picType = (String) (sysorgpagemodel.areaInfo
			.get("picType"));
	String ereaname = (String) (sysorgpagemodel.areaInfo
			.get("areaname"));
	String ereaid = (String) (sysorgpagemodel.areaInfo
			.get("areaid"));
	String manager = (String) (sysorgpagemodel.areaInfo
			.get("manager"));
%>
<div id="main" style="width:100%;height:100%;">
<odin:hidden property="downfile"/>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="b0111"/> 
<odin:hidden property="b0121"/>
<odin:hidden property="b0101old"/>
<odin:hidden property="b0104old"/>
<odin:hidden property="sql"/><!-- 列表查询sql临时隐藏，已经不在使用 -->
<odin:hidden property="isList" value="0"/>
<odin:hidden property="isstauts" value="0"/>
<odin:hidden property="delete_groupid"/><!-- 要删除的机构id，临时隐藏 -->
<odin:hidden property="scanwrongfulunid" value="false"/><!-- 显示不合法机构列表标志 true -->
<odin:hidden property="scanlegalperson" value="false"/><!-- 显示法人机构编码为空列表标志 true -->


<%-- <odin:floatDiv property="bar_div" position="up"></odin:floatDiv> --%>
<table style="width: 100%;height: 100%;" border="0" cellspacing='0' id="table_id">
	<tr>
		<%-- <td style="margin:0px 0px 0px 0px;border: 0px solid #a9bfd3;background-color:#d0def0;">
			<div class="div_bhxj" style="margin:0px 0px 0px 0px;border: 0px solid #a9bfd3;background-color:#d0def0;">
				<odin:checkbox property="bhxj"  style="margin:0px 0px 0px 0px;" label="包含下级"></odin:checkbox>
			</div>
		</td> --%>
		<td colspan="3">
			<div id="bar_div" style="width: 100%;height:100%;position: relative;"></div>
		</td>
	</tr>
	<tr>
		<td valign="top" style="position: relative;" >
		<odin:tab id="tab" width="250"  tabchange="grantTabChange">
				<odin:tabModel>
					<odin:tabItem title="机构树" id="tab1"></odin:tabItem>
					<odin:tabItem title="&nbsp&nbsp查询&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>
				</odin:tabModel>
				<odin:tabCont itemIndex="tab1">
				<!-- transparent -->
					<div id="tree-div" style="overflow: auto;height: 100%;border: 2px solid transparent;margin:0px 0px 0px 0px;">
					</div>
					<div style="margin:0px 0px 0px 0px;border: 1px solid #c3daf9;background-color:#cedff5;height: 100%;" valign="middle">
						<table id="jg_tj" width="250" style="background-color:#cedff5;border: 1px solid #c3daf9;height:100%" border="0" valign="middle">
							 <tr>
						        <td colspan="4">
						        	<table border="0">
						        		<tr>
						        			<td width="190px">
							        			<odin:checkbox property="bhxa_check" label="包含下级"></odin:checkbox>
						        			</td>
						        			<td>
						        				<table>
						        					<tr>
						        						<td>
						        							<odin:button text="刷新 " property="flashOrgInfo" handler="reloadTree"></odin:button>
						        						</td>
						        					</tr>
						        				</table>
						        			</td>
						        		</tr>
						        	</table>
						        </td>
						   </tr>
						<tr>
						        <td><label style="font-size: 12">机构定位</label></td>
						        <td><input type="text" value="请输入名称或编码" onfocus="javascript:if(this.value=='请输入名称或编码')this.value='';" id="jgdw" size="17" title="请输入名称或编码"
							            class=" x-form-text x-form-field"></td>
							    <td style ="width:10px"></td>
						        <td ><odin:button property="selectOrgsBtn" 
								        text="下一个" handler="doQueryNext"/></td>
						</tr>
						</table>
					</div>
					
				</odin:tabCont>
				<odin:tabCont itemIndex="tab2">
					<div id="div_tab2" style="margin:0px 0px 0px 0px;border: 2px solid #c3daf9;height:300;margin-top: 0px;">
						<table border="0" style="width:100%;">
							<col width="35%">
							<col width="30%">
							<col width="35%">
							<tr>
								<td>
								</td>
								<td>
									<table style="width:100%;" border="0">
										<col width="10%">
										<col width="90%">
										<tr>
											<td colspan="2"  heigth="3">&nbsp;</td>
										</tr>
										<tr>
											<odin:select2 property="b0194" label="单位类型" data="['1','法人单位'],['2','内设机构'],['3','机构分组']"/>
											<odin:hidden property="b0194_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"> </td>
										</tr>
										<tr>
											<odin:textEdit property="b0114" label="机构编码" />
											<odin:hidden property="b0114_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<odin:textEdit property="b0101" label="机构名称"/>
											<odin:hidden property="b0101_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<odin:textEdit property="b0104" label="简&nbsp;&nbsp;&nbsp;&nbsp;称"/>
											<odin:hidden property="b0104_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0117" label="所在政区" codetype="ZB01" codename="code_name3" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0124" label="隶属关系" codetype="ZB87" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0131" label="机构类别" codetype="ZB04" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0127" label="机构级别" codetype="ZB03" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3">&nbsp;</td>
										</tr>
									</table>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
								<table style="width:100%;" border="0">
									<tr >
									<td align="center">
										<odin:button text="清空" handler="clearValue" />
									</td>
									<td align="center" >
										<odin:button text="查询"  handler="queryDO" />
									</td>
									<td></td>
									</tr>
								</table>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
				</odin:tabCont>
		</odin:tab>
		</td>
		<td style="position: relative;">
			<div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:url(image/right.png) #D6E3F2 no-repeat center center;"></div>
		</td>
		<td valign="top" class='bigDiv' id="bigDiv" width="100%" >
		<div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;" >
		<odin:editgrid property="orgInfoGrid" cellDbClick="DbClick_grid_func"
					autoFill="false" width="0"  bbarId="pageToolBar" remoteSort="true" 
					hasRightMenu="true" rightMenuId="updateM" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="b0111" />
						<odin:gridDataCol name="b0101" />
						<odin:gridDataCol name="b0101parent" />
						<odin:gridDataCol name="b0114" />
						<odin:gridDataCol name="b0194" /> 
						<odin:gridDataCol name="signcode" />
						<odin:gridDataCol name="unifycode" />
						
						<odin:gridDataCol name="b0117" />
						<odin:gridDataCol name="b0124" />
						<odin:gridDataCol name="b0131" />
						<odin:gridDataCol name="b0127" />
						<odin:gridDataCol name="grouptype" />
						<odin:gridDataCol name="operate" />
						<odin:gridDataCol name="type" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<%-- <odin:gridRowNumColumn></odin:gridRowNumColumn> --%>
						<odin:gridEditColumn2 header="selectall" width="20"  editor="checkbox" dataIndex="personcheck" edited="true" hideable="false" checkBoxClick="personcheck_func" checkBoxSelectAllClick="getCheckAll"/>
						<odin:gridEditColumn dataIndex="b0111" width="100" header="机构主键" edited="false" editor="text" hidden="true" />
						<odin:gridEditColumn2 dataIndex="b0101parent" width="100" header="上级机构名称" editor="text" edited="false" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="b0101" width="100" header="机构名称" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0114" width="50" header="机构编码" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0194" width="35" header="机构类型" editor="text" edited="false" />
						
						<odin:gridEditColumn2 dataIndex="b0117" width="35" header="所在政区" editor="select" codeType="ZB01" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0124" width="35" header="隶属关系" editor="select" codeType="ZB87" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0131" width="35" header="机构类别" editor="select" codeType="ZB04" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0127" width="35" header="机构级别" editor="select" codeType="ZB03" edited="false" />
						<odin:gridEditColumn2 dataIndex="grouptype" width="35" header="关联部门" editor="select" hidden="true" codeType="SSKSBM" edited="false" />
						<odin:gridEditColumn2 dataIndex="operate" width="100" header="操作" editor="select" codeType="ddd" renderer="dodetail" hidden="true" edited="false" />
				          
						<odin:gridEditColumn2 dataIndex="type" width="40" header="权限类型" editor="text" hidden="true" edited="false" isLast="true"/>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
			</div>
		</td>
	</tr>
</table>
</div>
<odin:hidden property="openSelectOrgWinBtn"/>
<odin:hidden property="outFile"/>
<odin:hidden property="tab_flag"/>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<%-- <odin:textForToolBar text="<h3>机构信息</h3>" /> --%>
	<%-- <odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="<h3>查询模式</h3>" id="openSelectOrgWinBtn" icon=""  /> --%>
	<odin:fill />
	<%-- <odin:buttonForToolBar id="cytjfx" text="常用统计分析" icon="image/u179.png" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>  --%>
	<%-- <odin:buttonForToolBar id="hgfx" text="宏观分析" icon="image/u45.png" tooltip="对选中机构及包含下级进行宏观分析"/>
	<odin:separator></odin:separator> --%>
	
	<%-- <odin:buttonForToolBar text="&nbsp;展示切换" id="showList" handler="showListChnge"  icon="image/icon040a10.gif"></odin:buttonForToolBar> --%>

	<%-- <odin:buttonForToolBar text="&nbsp;机构画像"  id="orgPortrait" handler="orgPortrait"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;单位类维护" id="orgKind" handler="orgKind"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;同步OA用户" id="synchroOAUser"  handler="synchroOAUser"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="&nbsp;同步OA机构" id="synchroOA"  handler="synchroOA"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;组织史" id="zzsBtn"  icon="image/icon040a10.gif" handler="openZZSWin"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="bzzsid" text="编制职数"  menu="BzzsMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="&nbsp;新建下级机构" id="addOrgWinBtn"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon040a14.gif" id="updateWinBtn" tooltip="修改"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;删除" id="deleteOrgBtn" handler="deleteOrg"  icon="image/icon040a3.gif"></odin:buttonForToolBar>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;回收站" id="recoverBtn"  icon="images/qinkong.gif"></odin:buttonForToolBar> --%>
	<odin:separator />
	<%-- <odin:buttonForToolBar text="机构校核" icon="image/u53.png" id="dataVerify" tooltip="对选中机构进行校验"  />
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;机构查询" id="queryGroupBtn1" handler="openQueryWin" icon="image/icon040a15.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;机构查重" id="JudgeRepeatBtn1" handler="JudgeRepeatWin" icon="image/icon040a15.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;年度考核" id="YearCheckBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;机构排序" id="sortSysOrgBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="隶属关系变更" id="transferSysOrg"
		icon="images/icon/reset.gif" tooltip="将机构转移到其他机构" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="人员批量转移" icon="images/icon/exp.png"
		id="batchTransferPersonnel" />
	<odin:separator></odin:separator> 
	<odin:buttonForToolBar text="&nbsp;职位管理配置" id="gwConfBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="&nbsp;职位导入" id="gwConfBtn2" handler="impTest" icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;补全机构编码" id="aotumaticCode" handler="aotumaticCode" icon="image/icon040a14.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;机构信息导出" id="impOrgInfo" icon="image/icon040a14.gif"></odin:buttonForToolBar>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;部门关联配置" id="refSetBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar> --%>
	<odin:separator isLast="true"></odin:separator>
	<%-- <odin:separator></odin:separator> --%>
  
	<%-- <odin:buttonForToolBar text="机构维护" id="orgBtn"   icon="images/icon/folderClosed.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
<%--  	<odin:buttonForToolBar text="历史机构 " id="outFile"
		icon="images/icon/reset.gif" tooltip="存放删除的机构" /> 
		
	<odin:separator></odin:separator>--%>
	
<%-- <odin:buttonForToolBar text="职数编制统计" id="exchangeBtn" id="exchangeId" isLast="true" menu ="exchangeMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> --%>
	<%-- <odin:buttonForToolBar text="职数编制统计" id="exchangeBtn"  isLast="true" menu ="exchangeMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> --%>
	<%-- <odin:separator></odin:separator> --%>
<%-- 	<odin:buttonForToolBar text="保存" icon="images/save.gif"
		id="saveBtn" tooltip="保存"/> 
	<odin:separator></odin:separator>--%>
	<%-- <odin:buttonForToolBar text="照片检测" icon="image/u45.png"
		id="imgVerify" tooltip="对权限内机构中的所有人员进行无照片检测"/>  --%>
	<%-- <odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="取消" icon="images/sx.gif"
		id="cancelBtn" tooltip="取消"  isLast="true" handler="cancelBtnClick"/>	 --%>	
</odin:toolBar>
<odin:hidden property="cancelBtn"/>
<odin:window src="/blank.htm" id="tjfxWin" width="400" height="530"
	title="常用统计分析" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin" width="1250" height="550"
	title="新建下级机构页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="YearCheck" width="760" height="460"
	title="年度考核页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="updateNameWin" width="400" height="290"
	title="人员工作单位及职务信息更新提示" modal="true"></odin:window>
<odin:window src="/blank.htm" id="updateOrgWin" width="1200" height="550"
	title="修改机构页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="scanOrgWin" width="900" height="500"
	title="查看机构页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="transferSysOrgWin" width="420"
	height="400" title="隶属关系变更" modal="true"></odin:window>
<odin:window src="/blank.htm" id="outFilegWin" width="520"
	height="400" title="隶属关系变更" modal="true"></odin:window>
<odin:window src="/blank.htm" id="batchTransferPersonnelWin" width="520"
	height="400" title="人员批量转移" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgSortWin" width="510" height="600"
	title="机构排序" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgimpWin" width="300" height="600"
	title="机构信息导出 " modal="true"></odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500"
	title="信息校验" modal="true"></odin:window>
<odin:window src="/blank.htm" id="imgVerifyWin" width="960" height="500"
	title="照片检测" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgStatisticsWin" width="395" height="490"
	title="选择机构范围" modal="true"></odin:window>

<odin:window src="/blank.htm" id="queryGroupWin" width="800" height="480"
	title="查询机构" modal="true"></odin:window>
<odin:window src="/blank.htm" id="JudgeRepeatWin" width="800" height="480"
	title="查询机构" modal="true"></odin:window>
<odin:window src="/blank.htm" id="showOperateWin" width="800" height="480"
	title="机构信息修改" modal="true" ></odin:window>
<odin:window src="/blank.htm" id="orgConnectWin" width="800" height="480"
	title="编办机构关联" modal="true" ></odin:window>
<script type="text/javascript">
function expExcelFromGrid(){
	
	var excelName = null;
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('orgInfoGrid');
	var dstore = pgrid.getStore();
	excelName = "机构信息" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	
	odin.grid.menu.expExcelFromGrid('orgInfoGrid', excelName, null,null, false);
	
} 
//获取宽度
var mainWidth = document.body.clientWidth;//可用区域宽度



//机构查询调用
function queryDO(groupID,path){
  	tree.getLoader().load(
 		tree.getRootNode(),
 		function(){
 			tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
                tree.getSelectionModel().select(oLastNode);  
            });
 		},
 		this
 	);
  	funcAfterLoad();//设置查询列表标志,不合法编码，法人单位为空编码  标志为false(tab tab1 不合法编码，法人单位为空编码 4中显示列表)
	radow.doEvent('querybyid',groupID);
}
function dodetail(){
	return "<a href=\"javascript:showOperate()\">浏览</a>&nbsp&nbsp<a href=\"javascript:orgConnect()\">关联编办</a>;"
}

function showOperate(){
	var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
	var id = selections[0].data.b0111; 
	var sign_code = selections[0].data.signcode;
	if(!feildIsNull(sign_code )){
		$h.openWin('showOperateWin','pages.sysorg.org.ShowOperate1','机构信息维护',1280,500,id,'<%=ctxPath%>');
	}else{
		alert("没有获取到单位类型");
	}
	
}

function orgConnect(){
	var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
	var id = selections[0].data.b0111; 
	console.log("id "+id);
	$h.openWin('orgConnectWin','pages.sysorg.org.OrgConnect','编办机构关联',1300,550,id,'<%=ctxPath%>');
}
//机构修改页面调用查询机构事件
function queryGroupByUpdate(groupID){
	var flag=document.getElementById("scanwrongfulunid").value;
	var flag1=document.getElementById("scanlegalperson").value;
	if(flag=='true'){//查询不合法机构信息
		radow.doEvent('scanWrongfulUnid');
	}else if(flag1=='true'){
		radow.doEvent('scanLegalPerson');
	}else{//
		reloadTree();
		//radow.doEvent('querybyid',groupID);
		radow.doEvent('orgInfoGrid.dogridquery');
	}
}
/* 
$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	$("#b0104SpanId").prepend("<font color='red'>&nbsp;&nbsp;&nbsp;*</font>");
}); */
function myfunction(opType,radioType){
	var opType = opType;
	var radioType = radioType;
	if(opType==1){
		document.getElementById("b0194a").checked = 'checked';
		document.getElementById("b0194a").disabled=false; 
		document.getElementById("b0194b").disabled=false; 
		document.getElementById("b0194c").disabled=false; 
		Check1();
//显示
	}else{
		if(radioType==1){
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}else if(radioType==2){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").checked = 'checked';
			document.getElementById("b0194c").disabled=false; 
			Check2();
		}else if(radioType==3){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").checked = 'checked';
			Check3();
		}else{
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}
	}
}
function Check1(){
	var treeWidth = document.getElementById("tree-div").clientWidth;//机构树宽度
	var bigWidth = mainWidth-treeWidth-10;//右方div宽度
	//设置宽度
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	
	$(".tr1").show();
	$(".tr2").show();
	$(".tr4").show();
	$(".tr5").show();
	$('#div11').css('height','260px'); 
	$('#div22').css('height','260px'); 
	$('#div33').css('height','260px');
	$('#temp_div').show();
	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
	document.getElementById("b01a").value='法人单位';
	
	$("#tr33").width(bigWidth/100*41);
	$("#tr4").width(bigWidth/100*33);
	$("#tr5").width(bigWidth/100*26);
}
function Check2(){
	var treeWidth = document.getElementById("tree-div").clientWidth;//机构树宽度
	var bigWidth = mainWidth-treeWidth-10;//右方div宽度
	
	//设置宽度
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	$("#tr4").width(0);
	$("#tr33").width(bigWidth/100*56);
	$("#tr5").width(bigWidth/100*44);
	
	$(".tr1").hide();
	$(".tr2").show();
	$(".tr4").hide();
	$(".tr5").show();
	$('#div11').css('height','150px'); 
	$('#div22').css('height','150px'); 
	$('#div33').css('height','150px'); 
	$('#temp_div').show();
	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
		document.getElementById("b01a").value='内设机构';
}
function Check3(){ 
	var treeWidth = document.getElementById("tree-div").clientWidth;//机构树宽度
	var bigWidth = mainWidth-treeWidth-10;//右方div宽度
	//设置宽度
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	$("#tr33").width(bigWidth);
	$("#tr4").width(0);
	$("#tr5").width(0);
	
	$(".tr1").hide();
	$(".tr2").hide();
	$(".tr4").hide();
	$(".tr5").hide();
	$('#div11').css('height','120px'); 
	$('#div22').css('height','120px'); 
	$('#div33').css('height','120px'); 
	$('#temp_div').hide();
	$(".tr3").hide();
	document.getElementById("b01a").value='机构分组';
}
//显示机构类型按钮
function alertTypeFun(){
	$('.type11').show();
	$('.type22').show();
	$('.type33').show();
}
//删除机构时查案机构下人员
function queryPersonByGroupId(groupIDs,unitname){
	Ext.MessageBox.minWidth = 300;//设置 MessageBox 弹窗最小宽度
	Ext.MessageBox.confirm('提示',''+unitname+'下有现职人员，无法删除！是否查看？',function(btn){  
        if(btn=='yes'){
            var aid=groupIDs+"zz";
        	var tab=parent.tabs.getItem(aid);
           if (tab){ 
       			parent.tabs.activate(aid);
            }else{
	        	var src = '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery';
	        	//groupIDs = groupIDs+'__'+Ext.getCmp('group').getNodeById(groupIDs).getPath('id');
	            parent.parent.tabs.add({
	                id: aid,
	                title: '人员信息',
	                html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'jgcx_delete@@'+groupIDs+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	          	    listeners:{//判断页面是否更改，
	          	    	
	          	    },
	          	    closable:true
	                }).show();
            }
        } 
    }); 
}

//删除机构时查案机构下人员
function queryPersonByGroupId1(groupIDs,unitname){
	Ext.MessageBox.minWidth = 300;//设置 MessageBox 弹窗最小宽度
	Ext.MessageBox.confirm('提示',''+unitname+'下有非现职人员，无法删除！是否查看？',function(btn){  
        if(btn=='yes'){
            var aid=groupIDs+"ltorls";
        	var tab=parent.tabs.getItem(aid);
           if (tab){ 
       			parent.tabs.activate(aid);
            }else{
	        	var src = '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery';
	        	//groupIDs = groupIDs+'__'+Ext.getCmp('group').getNodeById(groupIDs).getPath('id');
	            parent.parent.tabs.add({
	                id: aid,
	                title: '人员信息',
	                html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'jgcxOrlt_delete@@'+groupIDs+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	          	    listeners:{//判断页面是否更改，
	          	    	
	          	    },
	          	    closable:true
	                }).show();
            }
        } 
    }); 
}

	function download(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
		setTimeout(cc,3000);
	}
	function cc(){
	w.close();
	}
	function imgVerifyWin(){
	addTab('照片检测','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false);
}

</script>
<script type="text/javascript">
/* $(function() {
    //绑定需要拖拽改变大小的元素对象 
    bindResize();
}); */
/* function bindResize() {
	var el=document.getElementById('divresize');
	
	var girdDiv=document.getElementById('bigDiv');
    //鼠标的 X 和 Y 轴坐标 
    x = y = x2 = y2 = 0;
    //鼠标按下后事件
    $(el).mousedown(function(e) {
		if(e.which==1){//==1鼠标左键按下
        	
        }else{
        	return;
        }
        //按下元素后，计算当前鼠标与对象计算后的坐标 
    	var treeDiv="";
    	if(tab_flag=='tab1'){
    		treeDiv=document.getElementById('tree-div');
    	}else{
    		treeDiv=document.getElementById('div_tab2').firstChild;//table 对象
    	}
        if(tab_flag=='tab1'){
        	x = e.clientX - treeDiv.offsetWidth;
        	//y = e.clientY - treeDiv.offsetHeight;
        }else{
        	x = e.clientX -treeDiv.offsetWidth;
        	//y = e.clientY - treeDiv.style.height;
        }
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
        document.getElementById("div_tab2").firstChild.style.width=e.clientX-x;
       	//宇宙超级无敌运算中... 
        var tree = Ext.getCmp('group')
        //tree的宽度设置
        tree.setWidth(e.clientX - x);
        //tab的宽度设置
        var resizeobj =Ext.getCmp('tab');
		resizeobj.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	//右边的td宽度设置（列表所在td） 不需要
    	//document.getElementById('bigDiv').style.height=document.body.clientHeight-objTop(document.getElementById('bigDiv'))[0]-4;
    	
    	//
    	var grid=Ext.getCmp('orgInfoGrid');
    	grid.setWidth(0);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
    	grid.setWidth(document.body.clientWidth-resizeobj.getWidth());
    	var width=document.getElementById("girdDiv").firstChild.firstChild.offsetWidth;//获取当前div宽度
    	grid.setWidth(width);//重置grid的宽度
    	
    	var grid_width=grid.getWidth();
    	//50 150 300 400 200
    	//动态设置列宽
    	grid.colModel.setColumnWidth(0,grid_column_0*width,'');//第0列
    	//grid.colModel.setColumnWidth(1,grid_column_1*width,'');//第1列
    	grid.colModel.setColumnWidth(2,grid_column_2*width,'');//第2列
    	grid.colModel.setColumnWidth(3,grid_column_3*width,'');//第3列
    	grid.colModel.setColumnWidth(4,grid_column_4*width,'');//第4列
    	grid.colModel.setColumnWidth(5,grid_column_5*width,'');//第5列
    	grid.colModel.setColumnWidth(6,grid_column_6*width,'');//第6列
    	grid.colModel.setColumnWidth(7,grid_column_7*width,'');//第7列
    	grid.colModel.setColumnWidth(8,grid_column_8*width,'');//第8列
    	//grid.colModel.setColumnWidth(9,grid_column_9*width,'');//第9列
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
} */ 
	
	//计算在页面的位置
	/* function objTop(obj){
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
	} */
	var grid_column_0=0.04;
	//var grid_column_1=0.3;
	var grid_column_2=0.20;
	var grid_column_3=0.11;
	var grid_column_4=0.11;
	var grid_column_5=0.11;
	var grid_column_6=0.11;
	var grid_column_7=0.10;
	var grid_column_8=0.10;
	var grid_column_9=0.10;
	//var grid_column_9="";
	function jainceHeight(){
		setTimeout(jainceHeight1,500); 
	}
	
	function jainceHeight1(){
		var heightClient=window.parent.document.body.clientHeight;
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;
		var tabHeight=28;
		var tr1Height=30;
		var tr2ButtomHeight=30;
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
		
	
		var gird_dom=Ext.getCmp('orgInfoGrid');//获取grid对象
		var resizeobj =Ext.getCmp('tab');
		
		gird_dom.setHeight(t2Height);
		resizeobj.setHeight(t2Height);
		var jg_tj=document.getElementById("jg_tj").offsetHeight;
		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
		document.getElementById("div_tab2").style.height=t2Height-30;
	}

	function initHeightWidth(){
		var heightClient=window.parent.document.body.clientHeight;//窗口高度
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//系统，上方菜单高度
		var tabHeight=28;//tab页导航工具栏高度
		var tr1Height=30;//table的第一行高度（）
		var tr2ButtomHeight=30;//grid底部工具栏高度
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
		//63 
		//28
		//30
		//30
		//页面调整
		document.getElementById("main").style.width = window.parent.document.body.clientWidth + "px";
		document.getElementById("main").parentNode.style.height='100%';
		document.getElementById("main").parentNode.style.width='100%';
		
		document.getElementById("main").parentNode.parentNode.style.width='100%';
		document.getElementById("main").parentNode.parentNode.style.height='100%';
		
		document.getElementById("main").parentNode.parentNode.style.overflow='hidden';
		
		var gird_dom=Ext.getCmp('orgInfoGrid');//获取grid对象
		gird_dom.setWidth(window.parent.document.body.clientWidth-250-7);
		gird_dom.setHeight(t2Height);
		
		//初始化tab高度
		var resizeobj =Ext.getCmp('tab');
		resizeobj.setHeight(t2Height);
		
		var grid_width=gird_dom.getWidth();
		//初始化设置列宽
    	gird_dom.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
    	//gird_dom.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
    	gird_dom.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//第2列
    	gird_dom.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//第3列
    	gird_dom.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//第4列
    	gird_dom.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//第5列
    	gird_dom.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//第6列
    	gird_dom.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//第7列
    	gird_dom.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//第8列
    	//gird_dom.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
		gird_dom.colModel.setColumnWidth(10,grid_column_9*grid_width,'');//第8列
    	
		//设置机构树的宽度与tab的宽度保持一致
		try{
			var resizeobj =Ext.getCmp('tab');
			resizeobj.setHeight(t2Height)
			var jg_tj=document.getElementById("jg_tj").offsetHeight;
    		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
    		document.getElementById("tree-div").style.width=resizeobj.getWidth();
    		//查询tab保持一致
    		document.getElementById("div_tab2").style.width=resizeobj.getWidth();
    		document.getElementById("div_tab2").style.height=t2Height-30;
    		
    		//初始化tab显示标志
    		document.getElementById("tab_flag").value='tab1';
    		window.onresize=jainceHeight;
    		checkboxvl("bhxa_check");
		}catch(err){
			
		}
	}
	function checkboxvl(id){
		try{
			var node_66;
			var node_55;
			var node_5_height;
			var node_6_height;
			node_66=$("#"+id).parent();
			node_6_height=node_66[0].offsetHeight;
			node_55=node_66.children();
			node_5_height=node_55[0].offsetHeight;
			var mt=(node_6_height-node_5_height)/2;
			$("#"+id).parent().parent().parent().css({"top": mt+"px"});
		}catch(err){
		}
	}
	Ext.onReady(function() {
		initHeightWidth();
	}); 
	Ext.onReady(function(){
		var pgrid = Ext.getCmp('orgInfoGrid');
		var dstore = pgrid.getStore();
		dstore.on({  
	       load:{  
	           fn:function(){  
	        	   pGridLoad(dstore.getTotalCount());
	        	   if($('#selectall_orgInfoGrid_personcheck').attr('class')=='x-grid3-check-col-on'){
	        		 //重新加载后选项复选框去掉勾。
	            	   document.getElementById('selectall_orgInfoGrid_personcheck').click();
	        	   }
	           }
	       },  
	       scope:this      
	   });  

	});
	function pGridLoad(tatalcount){
		<%-- if(tatalcount><%=CommSQL.MAXROW%>){
			document.getElementById('radioC1').checked=true;
			document.getElementById('radioC2').disabled=true;
			document.getElementById('radioC3').disabled=true;
			document.getElementById('radioC4').disabled=true;
			
		}else{
			document.getElementById('radioC2').disabled=false;
			document.getElementById('radioC3').disabled=false;
			document.getElementById('radioC4').disabled=false;
		} --%>
	}
	Ext.onReady(function() {
		document.getElementById('jgdw').style.width=115+'px';
	});
	var tab_flag="tab1";//tab页显示标志
	function grantTabChange(tabObj,item){
		if(item.getId()=='tab1'){
			tab_flag="tab1";
			//隐藏标志到页面
			document.getElementById("tab_flag").value='tab1';
		}else{
			tab_flag="tab2";
			document.getElementById("tab_flag").value='tab2';
		}
		//重置页面高度样式
		jainceHeight2();
	}
	function jainceHeight2(){
		var heightClient=window.parent.document.body.clientHeight;
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;
		var tabHeight=28;
		var tr1Height=30;
		var tr2ButtomHeight=30;
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
	
		var gird_dom=Ext.getCmp('orgInfoGrid');//获取grid对象
		var jg_tj=document.getElementById("jg_tj").offsetHeight;
		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
		document.getElementById("div_tab2").style.height=t2Height-30;
	}
	//查询//机构名称    机构编码    单位类型    简称 ，后台不能直接取值，设置隐藏值（原因未知）     
	function queryDO(){
		//$('#b0101_h_combo').val($('#b0101_combo').val());
		$('#b0101_h').val($('#b0101').val());
		
		//$('#b0114_h_combo').val($('#b0114_combo').val());
		$('#b0114_h').val($('#b0114').val());
		
		//$('#b0194_h_combo').val($('#b0194_combo').val());
		$('#b0194_h').val($('#b0194').val());
		
		//$('#b0104_h_combo').val($('#b0104_combo').val());
		$('#b0104_h').val($('#b0104').val());
		radow.doEvent("orgInfoGrid.dogridquery");
	}
	
	//机构政区 隶属关系 机构类别 机构级别 按下 backspace 则直接清空，输入的值是无效的，选择有效
	window.document.onkeydown=func_press;
	function func_press(ev){
		ev=(ev)?ev:window.event;
		if(ev.keyCode==8||ev.keyCode=='8'){//backspace
			if('b0117_combo' == document.activeElement.id){//机构政区
				$('#b0117_combo').val("");
				$('#b0117').val("");
			}
			if('b0124_combo' == document.activeElement.id){//隶属关系
				$('#b0124_combo').val("");
				$('#b0124').val("");
			}
			if('b0131_combo' == document.activeElement.id){//机构类别
				$('#b0131_combo').val("");
				$('#b0131').val("");
			}
			if('b0127_combo' == document.activeElement.id){//机构级别
				$('#b0127_combo').val("");
				$('#b0127').val("");
			}
		}else if(ev.keyCode==13||ev.keyCode=='13'){//enter
			if('jgdw' == document.activeElement.id||document.activeElement.id==''){
				doQueryNext();//具体处理函数
			}
		}else if(event.keyCode == 27){	//禁用ESC
	        return false;   
		}
	}
	var querynum = 0;//查询计数
	var lastquery = ""; //上一次查询值
	var cxjg_length=0;
	function doQueryNext() {
		var nextProperty = document.getElementById('jgdw').value;//获取搜索框的值
		if (nextProperty == "") {
			return;
		}
		var tree = Ext.getCmp("group");	//获取树
		 if(nextProperty != lastquery){	//如果查询变更,计数清空
	   	  querynum = 0; 
	     }
		if(querynum>parseInt(cxjg_length)){//查询次数大于查询结果集长度，重置为零，重新循环
			querynum=0;
		}
		 if (nextProperty !== ""&&(querynum<=cxjg_length)) {//有查询条件，且查询次数小于等于查询结果集长度
		  Ext.Ajax.request({
		     url : '<%=request.getContextPath()%>/JGQueryServlet?method=JGQuery_jgxx',
		     params : {
		    	'queryName' : nextProperty,
		    	'queryNum'	: querynum
		     },
		     async:true,
		     method : "post",
		     success : function(a, b) {
		      var r = Ext.util.JSON.decode(a.responseText);
		      var data = eval(r);
		      if(data[0] == "1"){//有查询结果
		    	  cxjg_length=data[1];//查询结果集长度
		    	  tree.expandPath(data[2], 'id', this.onExpandPathComplete);
		    	  lastquery = nextProperty;
		    	  querynum++;
		      }else if(data[0] == '0'){// 没有查询结果
		    	  cxjg_length=data[1];//查询结果集长度
		    	  Ext.Msg.alert("信息", "没有满足查询条件的机构");
		      }else if(data[0] == '3'){//  请求速度太快，参数异常
		    	  cxjg_length=data[1];//查询结果集长度
		    	  querynum = 0;			//查询到底,提示后计数清空
		    	  cxjg_length=data[1];//查询结果集长度
		    	  doQueryNext();
		      } else if(data[0] == '2'){ //最后一个查询结果
		    	  cxjg_length=data[1];//查询结果集长度
		    	 // Ext.Msg.alert("信息", "已经查询到最后一个机构,再次查询将从第一个开始");
		    	  querynum = 0;			//查询到底,提示后计数清空
		    	  doQueryNext();
		      }
		     }
		    });
		 } 
	}
	onExpandPathComplete = function(bSuccess, oLastNode) {
		 if (!bSuccess) {
		  return;
		 } 
		 // focus 节点，并选中节点！，以下代码不可少
		 oLastNode.ensureVisible();
		 oLastNode.select();
		 oLastNode.fireEvent('click', oLastNode);
		};
    //清空tab页面 ，查询条件
	function clearValue(){
		$('#b0194').val("");
		$('#b0114').val("");
		$('#b0101').val("");
		$('#b0104').val("");
		$('#b0117').val("");
		$('#b0124').val("");
		$('#b0131').val("");
		$('#b0127').val("");
		$('#b0194_combo').val("");
		$('#b0114_combo').val("");
		$('#b0101_combo').val("");
		$('#b0104_combo').val("");
		$('#b0117_combo').val("");
		$('#b0124_combo').val("");
		$('#b0131_combo').val("");
		$('#b0127_combo').val("");
	}
    //列表双击事件
    function DbClick_grid_func(grid,rowIndex,colIndex,event){
    	
    	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
    	//弹出窗口
    	radow.doEvent("DbClick_grid",record.get("type")+","+record.get("b0111"));
    }
    //列表复选框单选
    //a-row b-col c-col_id d-grid_id
    function personcheck_func(row,col,col_id,grid_id){
    	/* var grid=Ext.getCmp(grid_id);
    	record=grid.store.getAt(row);
    	if(record.get("personcheck")==true){
    		document.getElementById("checkedgroupid").value=record.get("b0111");
    	}else{
    		document.getElementById("checkedgroupid").value="";
    	} */
    }
    //全选
   function getCheckAll(grid_id,col_id){
	  /* var grid=Ext.getCmp(grid_id);
	   var store=grid.store;
	   if(store.totalLength>0){//列表有数据
		   if(store.getAt(0).get("personcheck")==true){//第一行被选中，则是全被选中
			   document.getElementById("checkedgroupid").value=store.getAt(0).get("b0111");
		   }else{
			   document.getElementById("checkedgroupid").value=record.get("b0111");
		   }
	   } */ 
   }
    //收缩 展开
    var flag_ss=false;
   function abcde(obj){
	   if(flag_ss==false){
		 	//收缩
	   		document.getElementById("div_tab2").firstChild.style.width=1;//查询列表宽度设置
	        var tree = Ext.getCmp('group');
	        //tree的宽度设置
	        tree.setWidth(1);
	        //tab的宽度设置
	        var resizeobj =Ext.getCmp('tab');
			resizeobj.setWidth(1);
			
			flag_ss=true;//隐藏标志
			// document.getElementById(obj.id).innerHTML='>';
			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('orgInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//第8列
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }else{ //伸展开
			 //收缩
			 document.getElementById("div_tab2").firstChild.style.width=250;//查询列表宽度设置
		     var tree = Ext.getCmp('group')
		     //tree的宽度设置
		     tree.setWidth(250);
		     //tab的宽度设置
		     var resizeobj =Ext.getCmp('tab');
			 resizeobj.setWidth(250);
			 flag_ss=false;//伸展开标志
			 //document.getElementById(obj.id).innerHTML='<span><</span>';
			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
			 var grid=Ext.getCmp('orgInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//第8列
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }
   }
   Ext.onReady(function(){
	   var pagesize = "20";
		var orgInfoGridPageSize='<%= orgInfoGridPageSize%>';
		if(orgInfoGridPageSize!=null&&orgInfoGridPageSize!='null'&&orgInfoGridPageSize.length>0){
			pagesize=orgInfoGridPageSize;
		}
		pageingToolbar = (Ext.getCmp('orgInfoGrid').getBottomToolbar());
		pageingToolbar.pageSize = Number(pagesize);
		var s = Ext.getCmp('orgInfoGrid').store;
		s.baseParams.limit = pagesize;
		if(s.lastOptions && s.lastOptions.params){
			s.lastOptions.params.limit = pagesize;
		}
});
   Ext.onReady(function(){
		var pgrid = Ext.getCmp('orgInfoGrid');
		var bbar = pgrid.getBottomToolbar();
		bbar.insertButton(11,[
		                      new Ext.menu.Separator({cls:'xtb-sep'}),
								new Ext.Button({
									icon : 'images/icon/table.gif',
									id:'saveSortBtn',
								    text:'导出Excel',
								    handler:function(){
								    	expExcelFromGrid();
								    }
								}),
								new Ext.Button({
									icon : 'images/keyedit.gif',
									id:'setPageSize',
								    text:'设置每页条数',
								    handler:setPageSize1
								})
								]); 
   });
 
   function aotumaticCode(){
	   startImport();
   }
   function startImport(){
	   var checkedgroupid=document.getElementById("checkedgroupid").value;
	   var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther&eventNames=aotumaticCode_onclick';
   		ShowCellCover('start','系统提示','正在补充编码中 ,请您稍等...');
	   	Ext.Ajax.request({
	   		timeout: 900000,
	   		url: path,
	   		async: true,
	   		method :"post",
	   		params : {
				'checkedgroupid':checkedgroupid
	   		},
	       
	           callback: function (options, success, response) {
	        	   if (success) {
	        		   var result = response.responseText;
	   					if(result){
	   						Ext.Msg.hide();
	   						var json = eval('(' + result + ')');
	   						var data_str=json.data;
	   						var arr=data_str.split('@');
	   						if(arr[0]==2){//成功
	   							alert(arr[1]);
	   							radow.doEvent("orgInfoGrid.dogridquery");
	   						}else if(arr[0]==1){//失败
	   							alert(arr[1]);
	   						}else if(arr[0]==3){//存在不合法的机构编码
	   							judgeReult(arr[1],'确认','取消');
	   							//this.getExecuteSG().addExecuteCode("judgeReult('"+title+"','确认','取消'');");
	   						}else if(arr[0]==4){//存在编码为空的法人单位
	   							judgeReult1(arr[1],'确认','取消');
	   						}
	   						Ext.Msg.hide();
	   					}
	        	   }
	           }
	      });
   }
   function judgeReult1(title,leftButtom,rightButtom){
	   	var win11 = new Ext.Window({
	   		border:false,//去掉文本的边框
	   		title: '系统提示',   
	   		msg: title, 
	   		width:280,          //设置窗口大小;
	   		height:110,
	   		closeAction:'hide', //点击右上角关闭按钮后会执行的操作;
	   		closable:true,     //隐藏关闭按钮;
	   		draggable:true,     //窗口可拖动;
	   		plain:true,              //使窗体主体更融于框架颜色;
	   		buttonAlign:'center',
	   		html: '<div style="width:100%;text-align:center;">'+title+'</div>',
	   		buttons:[{
	       		  text:leftButtom,
	       		  handler:function(){ //点击后隐藏窗口;
	       			 //查看不为空且不合法的机构信息
	       			 document.getElementById("scanwrongfulunid").value='false';//设置列表显示不合法机构信息，标志为false
	       			document.getElementById("scanlegalperson").value='true';//设置法人单位编码为空标志为true
	       			
	       			 radow.doEvent("scanLegalPerson");
	       			  win11.hide();
	       		  }
	       		},
	       		{
	         		  text:rightButtom,
	         		  handler:function(){ //点击后隐藏窗口;
	         			win11.hide();
	         		  }
	         		}
	   		]
	   		});
	   		win11.show();
	   }
   function judgeReult(title,leftButtom,rightButtom){
   	var win11 = new Ext.Window({
   		border:false,//去掉文本的边框
   		title: '系统提示',   
   		msg: title, 
   		width:280,          //设置窗口大小;
   		height:110,
   		closeAction:'hide', //点击右上角关闭按钮后会执行的操作;
   		closable:true,     //隐藏关闭按钮;
   		draggable:true,     //窗口可拖动;
   		plain:true,              //使窗体主体更融于框架颜色;
   		buttonAlign:'center',
   		html: '<div style="width:100%;text-align:center;">'+title+'</div>',
   		buttons:[{
       		  text:leftButtom,
       		  handler:function(){ //点击后隐藏窗口;
       			 //查看不为空且不合法的机构信息
       			  document.getElementById("scanwrongfulunid").value='true';//设置列表显示不合法机构信息，标志为true
	       			document.getElementById("scanlegalperson").value='false';//设置法人单位编码为空标志为false
       			 radow.doEvent("scanWrongfulUnid");
       			  win11.hide();
       		  }
       		},
       		{
         		  text:rightButtom,
         		  handler:function(){ //点击后隐藏窗口;
         			win11.hide();
         		  }
         		}
   		]
   		});
   		win11.show();
   }
   //虚假进度条
   function ShowCellCover(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "关闭";
	   	if(elementId.indexOf("start") != -1){
	   	
	   		Ext.MessageBox.show({
	   			title:titles,
	   			msg:msgs,
	   			width:300,
	   	        height:300,
	   			closable:false,
	   		//	buttons: Ext.MessageBox.OK,		
	   			modal:true,
	   			progress:true,
	   			wait:true,
	   			animEl: 'elId',
	   			increment:5, 
	   			waitConfig: {interval:150}
	   			//,icon:Ext.MessageBox.INFO        
	   		});
	   	}
   }
   //重新复选框，选中方法中 的set 方法
   Ext.apply(Ext.data.Record.prototype,{set : function(name, value){
	    if(String(this.data[name]) == String(value)){
	        return;
	    }
	    //this.dirty = true;
	    if(!this.modified){
	        this.modified = {};
	    }
	    if(typeof this.modified[name] == 'undefined'){
	        this.modified[name] = this.data[name];
	    }
	    this.data[name] = value;
	    if(!this.editing){
	        this.store.afterEdit(this);
	    }       
	}})
	
	//设置每页条数
	function setPageSize1(){
		
		var gridId = 'orgInfoGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("要设置的列表不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGridSOO.jsp";
			doOpenPupWin(url, "设置每页条数", 300, 150);
		} else {
			odin.error("非分页grid不能使用此功能！");
			return;
		}
	}
	
	
	
	/* 监控回车事件,绑定机构定位  */ //readyonly 可删除
	document.onkeydown=function() {
		if (event.keyCode == 8) { 
			if ((document.activeElement.type == "text"||document.activeElement.type == "textarea")&&document.activeElement.readOnly == true) {
				
				var id = document.activeElement.id;
				var index = id.indexOf('_combo');
				var index2 = id.indexOf('comboxArea_');
				if(index!=-1){
					var realid =  id.substring(0,index);
					document.getElementById(realid).value='';
					document.getElementById(id).value='';
					onkeydownfn(realid);
					return false;
				}else if(index2!=-1){
					var realid =  id.substring(11,id.length);
					document.getElementById(realid).value='';
					document.getElementById(id).value='';
					onkeydownfn(realid);
					return false;
				}
				return false;
			}
			if(document.activeElement.type != "password" && document.activeElement.type != "text" 
				&& document.activeElement.type != "textarea"){
				return false;
			}   
	 
		}else if(event.keyCode == 27){	//禁用ESC
	        return false;   
		}
	};
	
	
	function deleteOrg(){
		radow.doEvent('deleteOrgBtn');
	}
	
	function synchroOA(){
		
	radow.doEvent('synchroOA');
	}
	
	function synchroOAUser(){
		radow.doEvent('synchroOAUser');
	}
	
	function againReq(unitname){
		$h.confirm("系统提示：",'确定删除机构'+unitname+',是否继续？',330,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('数据删除中...','系统提示');
				radow.doEvent('deleteExec');
			}else{
				return false;
			}		
		});
	}
	
	
	function openZZSWin(){
		
	}
</script>