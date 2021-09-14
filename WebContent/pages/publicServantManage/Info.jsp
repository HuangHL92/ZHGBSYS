<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.InfoPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%> 
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>




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

#b0180 {
	width:670px !important;
}
</style>
<script type="text/javascript">
	var ctxPath = '<%=ctxPath%>';
	
	Ext.onReady(function() {
			var man = document.getElementById('manager').value;
			var Tree = Ext.tree;
			var tree = new Tree.TreePanel(
					{
						id : 'group',
						el : 'tree-div',//目标div容器
						split : false,
						monitorResize :true,
						width : 198,
						minSize : 164,
						maxSize : 164,
						rootVisible : false,//是否显示最上级节点
						autoScroll : false,//当内容超过预设的高度时自动出现滚动条。
						collapseMode : 'mini',
						animate : true,
						border : false,
						enableDD : false,//设置树的节点是否可以拖动。
						containerScroll : true,//是否将树形面板注册到滚动管理器ScrollManager中
						loader : new Tree.TreeLoader(//树节点的加载器
								{
						        	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
						        	dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info&eventNames=getTreeJsonData'
								 
								}),
						listeners : {
							'click': function(node){                                       //左键单击事件
								treeChange(node.id);
							}
						}

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
			tree.setRootNode(root);
			tree.render();
			root.expand(true,true);
		});
	
	function saveb01(){
		radow.doEvent('saveBtn.onclick');
	}
	//人员新增修改窗口窗口
	var personTabsId=[];
	
	function reloadTree() {
		var tree = Ext.getCmp("group");
		   
		//获取选中的节点  
		var node = tree.getSelectionModel().getSelectedNode();  
		
	}
	
	function cancelBtnClick(){
		radow.doEvent('querybyid', document.getElementById('b0111').value);
	}
	
	//根据点击的树菜单的信息集，显示相应的信息集录入页面 
	function treeChange(nodeId){
		//获得信息集的id
		var id = nodeId.substring(5);
		
		if(id != null && id != ''){
			
			document.getElementById("A02").style.display="none";
			document.getElementById("A06").style.display="none";
			document.getElementById("A08").style.display="none";
			document.getElementById("A11").style.display="none";
			document.getElementById("A14").style.display="none";
			document.getElementById("A15").style.display="none";
			document.getElementById("A29").style.display="none";
			document.getElementById("A30").style.display="none";
			//document.getElementById("A36").style.display="none";
			document.getElementById("A37").style.display="none";
			//document.getElementById("A41").style.display="none";
			document.getElementById("A53").style.display="none";
			document.getElementById("A60").style.display="none";
			document.getElementById("A61").style.display="none";
			document.getElementById("A62").style.display="none";
			document.getElementById("A63").style.display="none";
			document.getElementById("A64").style.display="none";
			//document.getElementById("A68").style.display="none";
			//document.getElementById("A69").style.display="none";
			document.getElementById("A71").style.display="none"; 
			
			//点击的显示 
			if(id == "All"){
				document.getElementById("A02").style.display="block";
				document.getElementById("A06").style.display="block";
				document.getElementById("A08").style.display="block";
				document.getElementById("A14").style.display="block";
				document.getElementById("A15").style.display="block";
			}else{
				document.getElementById(id).style.display="block"; 
			}
		}
		
	}
	
</script>

<!-- 弹出窗的公共方法 -->
<script type="text/javascript"> 
	
</script>

<%
	String picType = (String) (new SysOrgPageModel().areaInfo
			.get("picType"));
	String ereaname = (String) (new SysOrgPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new SysOrgPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new SysOrgPageModel().areaInfo
			.get("manager"));
%>
<div id="main">
<odin:floatDiv property="bar_div" position="up"></odin:floatDiv>
<table>
	<tr>
		<td height="26"></td>
	</tr>
</table>
<table style="width: 100%;height: 95%;">
	<tr>
		<td valign="top" width="1px">
			<div id="tree-div" style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;">
					
				</div>
		</td>
		<td><div id="divresize" style="height: 100%;width: 10px;cursor: e-resize;"></div></td>
		<td valign="top">
		<div id="girdDiv" style="width: 100%;height: 100%;border: 2px solid #c3daf9;padding-left:40px">
				<table>
					<tr>
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
						<odin:hidden property="isstauts" value="0"/>
						<odin:hidden property="status" title="删除状态" ></odin:hidden>
						<odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
					</tr>
				</table> 
				
				<!---------------------------------------------- 综合信息集（常用信息集） -------------------------------->
				
				<!-----------------------------人员基本信息------------------------------------------------------->
				<odin:groupBox property="A01" title="人员基本信息">
				<table cellspacing="2" width="440" align="left">
					<tr>
						<odin:textEdit property="a0101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名"></odin:textEdit>
						<odin:select2 property="a0104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别" codeType="GB2261"></odin:select2>
						<odin:NewDateEditTag property="a0107" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生年月" isCheck="true" maxlength="8"></odin:NewDateEditTag>
					</tr>
					<tr>
						<odin:select2 property="a0117" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;民族" codeType="GB3304"></odin:select2>		
						<tags:PublicTextIconEdit property="a0111" label="籍贯" codetype="ZB01" readonly="true"></tags:PublicTextIconEdit>	
						<tags:PublicTextIconEdit property="a0114" label="出生地" codetype="ZB01" readonly="true"></tags:PublicTextIconEdit>	
					</tr>
					<tr>
						<%-- <tags:TextAreainput2 property="a0140" cls="width24-80 height1234-40 no-y-scroll cellbgclor" ondblclick="a0140Click()" onkeypress="a0140Click2()" readonly="true" label="入党时间"/> --%>
						<odin:textEdit property="a0140" label="入党时间" ondblclick="a0140Click()" onkeypress="a0140Click2()" readonly="true" onclick="a0140Click()"></odin:textEdit>
						<odin:NewDateEditTag property="a0134" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参加工作时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
						<odin:select2 property="a0128" label="健康状况" codeType="GB2261D"></odin:select2>
					</tr>
					<tr>
						<odin:textEdit property="a0184" label="公民身份号码"></odin:textEdit>
						<odin:textEdit property="a0187a" label="专 长"></odin:textEdit>
					</tr>
					<tr>
						<odin:textarea property="a1701" label="简历" colspan='10' rows="8"></odin:textarea>
					</tr>
				</table>
				</odin:groupBox>
				
				
				
				
				<!---------------------------------- 职务信息集 -------------------------->
				
				<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
				<script type="text/javascript" src="basejs/helperUtil.js"></script>
				<script type="text/javascript" src="js/lengthValidator.js"></script>
				<%@include file="/comOpenWinInit.jsp" %>
				<script type="text/javascript">
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0200 = record.data.a0200;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">删除</a>";
				}
				function deleteRow2(a0200){ 
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("系统提示","最后一条数据无法删除！");
						return;
					}
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',a0200);
						}else{
							return;
						}		
					});	
				}
				
				function deleteRow(){ 
					var sm = Ext.getCmp("WorkUnitsGrid").getSelectionModel();
					if(!sm.hasSelection()){
						Ext.Msg.alert("系统提示","请选择一行数据！");
						return;
					}
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("系统提示","最后一条数据无法删除！");
						return;
					}
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				Ext.onReady(function(){});
				//工作单位职务输出设置
				function a02checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0800);
					radow.doEvent('workUnitsgridchecked',sr.data.a0200);
				}
				
				
				
				function changeSelectData(item){
					var a0255f = Ext.getCmp("a0255_combo");
					var newStore = a0255f.getStore();
					newStore.removeAll();
					newStore.add(new Ext.data.Record(item.one));
					newStore.add(new Ext.data.Record(item.two));
					var keya0255 = document.getElementById("a0255").value;//alert(item.one.key+','+keya0255);
					if(item.one.key==keya0255){
						a0255f.setValue(item.one.value);
					}else if(keya0255==''){
						a0255f.setValue(item.one.value);
						document.getElementById("a0255").value=item.one.key;
					}else{
						a0255f.setValue(item.two.value);
						document.getElementById("a0255").value=item.two.key;
					}
				}
				
				var labelText={'a0255SpanId':['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>任职状态','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>工作状态'],
							   'a0201bSpanId':['<font color="red">*</font>任职机构名称','<font color="red">*</font>工作机构名称'],
							   'a0216aSpanId':['<font color="red">*</font>职务名称','<font color="red">*</font>岗位名称'],
							   //'a0215aSpanId':['职务名称代码','岗位名称代码'],
							   'a0221SpanId':['职务层次','岗位层次'],
							   'a0229SpanId':['分管（从事）工作','岗位工作'],
							   'a0243SpanId':['任职时间','工作开始']};
							   
				function changeLabel(type){
					for(var key in labelText){
						document.getElementById(key).innerHTML=labelText[key][type];
					}
				}		   
				function a0222SelChangePage(record,index){//岗位类别onchange时，职务层次赋值为空
					document.getElementById("a0221").value='';
					document.getElementById("a0221_combo").value='';
					a0221achange();
					a0222SelChange(record,index)
				}	
				function a0222SelChange(record,index){
				
					//岗位类别
					//var a0222 = record.data.key;
					var a0222 = document.getElementById("a0222").value;
					var a0201b = document.getElementById("a0201b").value;
					
				
					document.getElementById("codevalueparameter").value=a0222;
					
					if("01"==a0222){//班子成员
						//$h.selectShow('a0201d');
						selecteEnable('a0201d','0');
					}else{
						//$h.selectHide('a0201d');
						selecteDisable('a0201d');
					}
					
					if("01"==a0222||"99"==a0222){//公务员、参照管理人员岗位or其他
						//$h.selectShow('a0219');//职务类别
						//$h.selectShow('a0251');//职动类型
						///$h.selectShow('a0247');//选拔任用方式
						selecteEnable('a0219');//职务类别
						selecteEnable('a0251');//职动类型
						selecteWinEnable('a0247');//选拔任用方式
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
						//$h.textShow('a0288');//现任职务层次时间
						//$h.dateEnable('a0288');//现任职务层次时间
						
						
						
						changeSelectData({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}});
						changeLabel(0);
					}else if("02"==a0222||"03"==a0222){//事业单位管理岗位or事业单位专业技术岗位
						//$h.selectHide('a0219');//职务类别disabled
						selecteDisable('a0219');//职务类别disabled
						//$h.selectShow('a0251');//职动类型
						selecteEnable('a0251');//职动类型
						//$h.selectShow('a0247');//选拔任用方式
						selecteWinEnable('a0247');//选拔任用方式
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
						changeSelectData({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}});
						changeLabel(0);
						//$h.dateEnable('a0288');//现任职务层次时间
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						//$h.selectHide('a0219');//职务类别
						//$h.selectHide('a0251');//职动类型
						//$h.selectHide('a0247');//选拔任用方式
						selecteDisable('a0219');//职务类别
						selecteDisable('a0251');//职动类型
						selecteWinDisable('a0247');//选拔任用方式
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
						//$h.textHide('a0288');//现任职务层次时间	
						//$h.dateDisable('a0288');//现任职务层次时间	
						changeSelectData({one:{key:'1',value:'在职'},two:{key:'0',value:'不在职'}});
						changeLabel(1);
					}else{
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
					}
					a0255SelChange();
					//a0251change();	
				}
				function a0255SelChange(){
					var a0222 = document.getElementById("a0222").value;
					if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						return;
					}
					//该职务任职状态
					var a0255Value = document.getElementById("a0255").value;
					if("1"==a0255Value){//在任
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
					}else if("0"==a0255Value){//以免
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
					}
				}
				
				function setA0216aValue(record,index){//职务简称
					Ext.getCmp('a0216a').setValue(record.data.value);
				}
				function setA0255Value(record,index){
					Ext.getCmp('a0255').setValue(record.data.key)
				}
				//a01统计关系所在单位
				function setParentValue(record,index){
					document.getElementById('a0195key').value = record.data.key;
					document.getElementById('a0195value').value = record.data.value;
					
					var a0195 = document.getElementById("a0195").value;
					var B0194 = radow.doEvent('a0195Change',a0195);
					
				}
				function witherTwoYear(){
				
					parent.document.getElementById('a0197').value=record.data.key;
				}
				//a01级别
				function setParentA0120Value(record,index){
					parent.document.getElementById('a0120').value=record.data.key;
				}
				//a01 基层工作年限
				function setParentA0194Value(record,index){
					
				}
				
				function a0201bChange(record){
					//任职结构类别 和 职务名称代码对应关系
					radow.doEvent('setZB08Code',record.data.key);
				}
				function a0251change(){//职动类型  破格晋升
					var a0251 = document.getElementById('a0251').value;
					var a0251bOBJ = document.getElementById('a0251b');
					var a0251bTD = document.getElementById('a0251bTD');
					if('26'==a0251){
						
					}else if('27'==a0251){
						a0251bOBJ.checked=true;
						
					}else{
						
					}
				}
				function setA0201eDisabled(){
					var a0201d = document.getElementById("a0201d").value;
					if("0"==a0201d){
						document.getElementById("a0201e_combo").disabled=true;
						document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
						document.getElementById("a0201e_combo").style.backgroundImage="none";
						Ext.query("#a0201e_combo+img")[0].style.display="none";
						document.getElementById("a0201e").value="";
						document.getElementById("a0201e_combo").value="";
					}else if("1"==a0201d || ""==a0201d){
						document.getElementById("a0201e_combo").readOnly=false;
						document.getElementById("a0201e_combo").disabled=false;
						document.getElementById("a0201e_combo").style.backgroundColor="#fff";
						document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
						Ext.query("#a0201e_combo+img")[0].style.display="block";
					}
				}
				</script>
				
				<odin:hidden property="a0200" title="主键id" ></odin:hidden>
				<odin:hidden property="a0225" title="成员内排序" value="0"></odin:hidden>
				<odin:hidden property="a0223" title="多职务排序" ></odin:hidden>
				<odin:hidden property="a0201c" title="机构简称" ></odin:hidden>
				<odin:hidden property="codevalueparameter" title="职务层次和岗位类别的联动"/>
				<odin:hidden property="ChangeValue" title="职务名称代码和单位类别的联动"/>
				<odin:hidden property="a0271" value="0"/>
				<odin:hidden property="a0222" value='0'/>
				<odin:hidden property="a0195key" value=''/>
				<odin:hidden property="a0195value" value=''/>
				
				<odin:groupBox title="职务信息集" property="A02">
				
				<div id="btnToolBarDiv" style="width: 1000px;"></div>
				<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="saveA02" ></odin:buttonForToolBar>
				</odin:toolBar>
				
				<table style="width: 100%">
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="统计关系所在单位" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit3>
									<td rowspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label for="a0197" style="font-size: 12px;" id="a0197SpanId">是否具有两年以上基层工作经历 </label>
										<input align="middle" type="checkbox" name="a0197"  id="a0197" onclick="witherTwoYear()" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<odin:textEdit property="a0192a" width="550" label="全称"  maxlength="1000"><span>&nbsp;&nbsp;(用于任免表)</span></odin:textEdit>
									<td rowspan="2"><odin:button text="更新名称" property="UpdateTitleBtn" ></odin:button></td>
									<td rowspan="2"><odin:button text="集体内排序" property="personGRIDSORT" handler="openSortWin" ></odin:button></td>
								</tr>
								<tr>
							       <odin:textEdit property="a0192" width="550" label="简称"  maxlength="1000"><span>&nbsp;&nbsp;(用于名册)</span></odin:textEdit>
							    </tr>	
							</table>
						</td>
					</tr>
				    <tr>
					    <td width="330">
					    	<table width="330" height="300"><tr><td>
						    	<odin:editgrid property="WorkUnitsGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" 
									 height="330" title="" pageSize="50"  >
										<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								     		<odin:gridDataCol name="a0281"/>
								     		<odin:gridDataCol name="a0200" />
									  		<odin:gridDataCol name="a0201b" />
									  		<odin:gridDataCol name="a0201a" />
									  		<odin:gridDataCol name="a0215a" />
									  		<odin:gridDataCol name="a0216a" />
									  		<odin:gridDataCol name="a0222" />
									   		<odin:gridDataCol name="a0255" isLast="true"/>
									   		
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
										  <odin:gridRowNumColumn />
										  <odin:gridEditColumn2 header="输出" width="100" editor="checkbox" dataIndex="a0281" checkBoxClick="a02checkBoxColClick" edited="true"/>
										  <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" width="200" hidden="true"/>
										  <odin:gridEditColumn2 header="任职机构代码" edited="false"  dataIndex="a0201b"  editor="text" width="200" hidden="true"/>
										  <odin:gridEditColumn2 header="任职机构" edited="false" dataIndex="a0201a" renderer="changea0201a" editor="text" width="300"/>
										  <odin:gridEditColumn2 header="职务名称代码" edited="false"  dataIndex="a0215a" editor="select" codeType="ZB08" hidden="true" width="100"/>
										  <odin:gridEditColumn2 header="职务名称" edited="false"  dataIndex="a0216a" editor="text" width="200"/>
										  <odin:gridEditColumn2 header="岗位类别" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="任职状态" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select" width="200"/>
										  <odin:gridEditColumn header="操作" width="200" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				
									</odin:gridColumnModel>
								</odin:editgrid>
								<label><input type="checkbox" checked="checked" id="xsymzw" onclick="checkChange(this)"/>显示已免职务</label>
								<div id="btngroup"> </div>
								<div style="margin-top: 8px;" id="btngroup2"> </div>
								</td>
							</tr>
						</table>
				
				
				    	</td>
				    	<td >
				    		<table>
				    			<tr  align="left">
				    				<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" required="true" onchange="a0201bChange" label="任职机构" property="a0201b" defaultValue=""/>
				    				<odin:select2 property="a0255" label="任职状态" required="true" onchange="a0255SelChange" codeType="ZB14"></odin:select2>
								</tr>
								<tr>
									<odin:textEdit property="a0216a" label="职务名称" required="true" maxlength="50"></odin:textEdit>
									<odin:select2 property="a0201d" label="是否班子成员" data="['1','是'],['0','否']" onchange="setA0201eDisabled"></odin:select2>
								</tr>
								<tr><td><br><td></tr>
								<tr align="left">
								    <odin:select2 property="a0201e" label="成员类别" codeType="ZB129"></odin:select2>
								    <td></td>
								    <td align="left" id="a0251bTD">
										<input align="middle" type="checkbox" name="a0251b" id="a0251b" />
										<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">破格提拔</label>
									</td>
								</tr>
								<tr>
									
									<tags:PublicTextIconEdit property="a0247" label="选拔任用方式" codetype="ZB122" readonly="true"></tags:PublicTextIconEdit>
									<td></td>
									<td align="left" id="a0219TD">
										<input align="middle" type="checkbox" name="a0219" id="a0219" />
										<label id="a0219SpanId" for="a0219" style="font-size: 12px;">领导职务</label>
									</td>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0243" labelSpanId="a0243SpanId" maxlength="8" label="任职时间" ></odin:NewDateEditTag>
									<odin:textEdit property="a0245" label="任职文号" validator="a0245Length"></odin:textEdit>
								</tr>
								<tr align="left">
								    <odin:hidden property="a0221a" value="0"/> 
								</tr>
								<tr align="left" >
								    <odin:hidden property="a0229" value="0"/>
								    <odin:hidden property="a0251" value="0"/>
								</tr>
								<tr>
									
								</tr>
								<tr id='yimian'>
									<odin:NewDateEditTag property="a0265" label="免职时间" labelSpanId="a0265SpanId"  maxlength="8"></odin:NewDateEditTag>
									<odin:textEdit property="a0267" label="免职文号" validator="a0267Length"></odin:textEdit>
								</tr>
								<tr>
									<!-- 新库添加职务变动综述 tongzj 2017/5/29 -->
								</tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
				    		</table>
				    	</td>
				    </tr>
				    <tr>
				    	<td align="right" colspan="4"><div id="btngroup3" ></div></td>
				    </tr>
				</table>
				</odin:groupBox>
				
				<odin:hidden property="a0281" title="输出设置"/>
				<script type="text/javascript">
				Ext.onReady(function(){
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'上移',
						    cls :'inline',
						    renderTo:"btngroup",
						    handler:UpBtn
						});
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'下移',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:DownBtn
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'保存排序',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('worksort');
						    }
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'sortUseTimeS',
						    text:'按任职时间排序',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('sortUseTime');
						    }
						});
					});
					//统计关系所在单位和任职机构名称提醒
					function saveA02(){
						var a0201b = document.getElementById('a0201b').value;
						var a0195 = document.getElementById('a0195').value;
						if(a0195 != null && a0195!=a0201b){
							Ext.MessageBox.confirm(
								'提示',
								'统计关系所在单位和职务名称不相同，是否继续保持？',
								function (btn){
									if(btn=='yes'){
										radow.doEvent('saveWorkUnits.onclick');
									}
								}
							);
						}else{
							radow.doEvent('saveWorkUnits.onclick');
						}
					}
				</script>
				<script>
				
				function changea0201a(value, params, record,rowIndex,colIndex,ds){
					if(record.data.a0201b=='-1'){
						return '<a title="'+value+'(机构外)">'+value+'(机构外)</a>';
					}else{
						return '<a title="'+value+'">'+value+'</a>';
					}	
				}
				function seta0255Value(value, params, record,rowIndex,colIndex,ds){
					var a0222 = record.data.a0222;
					var textValue = '';
					if("01"==a0222||"99"==a0222||"02"==a0222||"03"==a0222){
					   	textValue = getTextValue({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}},value);
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						textValue = getTextValue({one:{key:'1',value:'在职'},two:{key:'0',value:'不在职'}},value);
					}	
					return '<a title="'+textValue+'">'+textValue+'</a>';
				}
				function getTextValue(item,v){
					if(item.one.key==v){
						return item.one.value;
					}else{
						return item.two.value;
					}
				}
				function checkChange(){
					var checkbox = document.getElementById("xsymzw");
					var grid = Ext.getCmp("WorkUnitsGrid");
					var store = grid.getStore();
					var vibility;
					if(checkbox.checked){
						vibility = "block";
					}else{
						vibility = "none";
					}
					
					var len = store.data.length;
					for(var i=0;i<len;i++){
						var data = store.getAt(i).data;
						var a0255 = data.a0255;//任职状态
						if(a0255=='0'){
							grid.getView().getRow(i).style.display=vibility;
						}
					}
				}
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					//alert(store.getCount());
					
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('该职务已经排在最顶上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index-1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('该职务已经排在最底上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index+1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
					grid.view.refresh();
				}
				
				Ext.onReady(function(){
					
					var pgrid = Ext.getCmp("WorkUnitsGrid");
					
					
					var bbar = pgrid.getBottomToolbar();
					
					
					var dstore = pgrid.getStore();
					var firstload = true;
					dstore.on({  
				       load:{  
				           fn:function(){  
				             checkChange();
				             if(firstload){
				       		    $h.selectGridRow('WorkUnitsGrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				   
				   var ddrow = new Ext.dd.DropTarget(pgrid.container,{
						ddGroup : 'GridDD',
						copy : false,
						notifyDrop : function(dd,e,data){
							//选中了多少行
							var rows = data.selections;
							//拖动到第几行
							var index = dd.getDragData(e).rowIndex;
							if (typeof(index) == "undefined"){
								return;
							}
							//修改store
							for ( i=0; i<rows.length; i++){
								var rowData = rows[i];
								if (!this.copy) dstore.remove(rowData);
								dstore.insert(index, rowData);
							}
							pgrid.view.refresh();
							radow.doEvent('worksort');
						}
					});
				
				
				});
				
				function openSortWin(){
					var a0201b = document.getElementById("a0201b").value;
					if(a0201b==''){
						$h.alert('系统提示：','请先选择机构!');
						return;
					}
					parent.window.a0201b = a0201b;
					$h.openWin('A01SortGrid','pages.publicServantManage.PersonSort','集体内排序',500,480,document.getElementById('subWinIdBussessId').value,'<%=ctxPath%>',window);
				}
				
				
				Ext.onReady(function(){
					
					Ext.getCmp('WorkUnitsGrid').setWidth(400);
					Ext.getCmp('WorkUnitsGrid').setHeight(250)
					
				});
				</script>
				
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				<div id="cover_wrap3"></div>
				
				
				
				
				<!---------------------------------- 专业技术任职资格信息集 -------------------------->
				
				<style>
				<%=FontConfigPageModel.getFontConfig()%>
				#table{position:relative;top: -12px;left:5px;}
				#table2{position:relative;top: -20px; padding: 0px;margin: 0px;height:300}
				.inline{
				display: inline;
				}
				.pl{
				margin-left: 8px;
				}
				</style>
				<script type="text/javascript">
				function setA0602Value(record,index){
					Ext.getCmp('a0602').setValue(record.data.value);
					Ext.getCmp('a0196').setValue(record.data.value);
				}
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0600 = record.data.a0600;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0600+"&quot;)\">删除</a>";
				}
				function deleteRow2(a0600){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA06',a0600);
						}else{
							return;
						}		
					});	
				}
				
				</script>
				
				<odin:groupBox title="专业技术任职资格信息集" property="A06">
				<body>
				<odin:toolBar property="toolBar1" applyTo="tol1">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="professSkillAddBtn" icon="images/add.gif" ></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA06" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				</odin:toolBar>
				<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
				<div id="tol1" style="width: 1000px;"></div>
				<odin:hidden property="sortid" title="排序号"/>
				<table id="table">
					<tr>
						<td>
							<div id="div1" style="width:330;">
							 <odin:editgrid property="professSkillgrid"    isFirstLoadData="false" forceNoScroll="true" url="/" applyTo="div1">
								<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								    <odin:gridDataCol name="a0699" />
									<odin:gridDataCol name="a0600" />
									<odin:gridDataCol name="a0601" />
									<odin:gridDataCol name="a0602" />
									<odin:gridDataCol name="a0604" />
									<odin:gridDataCol name="a0607" />
									<odin:gridDataCol name="a0611" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="输出" width="15" editor="checkbox" checkBoxClick="a06checkBoxColClick" dataIndex="a0699" edited="true"/>
									<odin:gridColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
									<odin:gridEditColumn2 header="专业技术资格代码" dataIndex="a0601" codeType="GB8561" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="专业技术资格" dataIndex="a0602" editor="text" />
									<odin:gridColumn header="获得资格日期" dataIndex="a0604" editor="text" />
									<odin:gridEditColumn2 header="获取资格途径" dataIndex="a0607" codeType="ZB24" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="评委会或考试名称" dataIndex="a0611" editor="text"  hidden="true"/>		
									 <odin:gridEditColumn header="操作" width="15" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>		
								</odin:gridColumnModel>
							 </odin:editgrid>
							</div>
							<div id="btngroupA06"> </div>
						</td>
						<td>
						  <div>
							<table id="table2">
								<tr>
									<odin:textEdit property="a0196" label="专业技术职务" readonly="true"></odin:textEdit>
								</tr>
								<tr>
									<tags:PublicTextIconEdit property="a0601" label="专业技术资格" onchange="setA0602Value" required="true" readonly="true" codetype="GB8561"></tags:PublicTextIconEdit>	
								</tr>
								<tr>
									<odin:textEdit property="a0602" label="专业技术资格名称" validator="a0602Length"></odin:textEdit>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0604" label="获得资格日期" maxlength="8" ></odin:NewDateEditTag>	
								</tr>
								<tr>
									<odin:select2 property="a0607" label="获取资格途径" codeType="ZB24"></odin:select2>		
								</tr>
								<tr>
									<odin:textEdit property="a0611" label="评委会或考试名称" validator="a0611Length"></odin:textEdit>
									<odin:hidden property="a0600" title="主键id" ></odin:hidden>		
								</tr>
							</table>
						  </div>
						</td>
					</tr>
					
					</table>
					
				
				<odin:hidden property="a0699" title="输出"/>
				 </div>
				</body>
				</odin:groupBox>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("professSkillgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				       		 if(firstload){
				       		    $h.selectGridRow('professSkillgrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				
				});
				
				</script>
				<script type="text/javascript">
				Ext.onReady(function(){
					 $h.applyFontConfig($h.spFeildAll.a06);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a06);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('professSkillgrid');
						var gridobj = document.getElementById('forView_professSkillgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled); 
				
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
				Ext.onReady(function(){
					new Ext.Button({
						icon : 'images/icon/arrowup.gif',
						id:'UpBtnA06',
					    text:'上移',
					    cls :'inline',
					    renderTo:"btngroupA06",
					    handler:UpBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/arrowdown.gif',
						id:'DownBtnA06',
					    text:'下移',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:DownBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/save.gif',
						id:'saveSortBtnA06',
					    text:'保存排序',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:function(){
							radow.doEvent('worksortA06');
					    }
					});
					
					Ext.getCmp('professSkillgrid').setHeight(375);
				});
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					//alert(store.getCount());
					
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('该职务已经排在最顶上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index-1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('该职务已经排在最底上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index+1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
					grid.view.refresh();
				}
				
				function a06checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0600);
					radow.doEvent('updateA06',sr.data.a0600);
				}
				
				</script>	
				
				
				
				
				<!---------------------------------------------- 学历学位信息集-------------------------------->
				
				<script type="text/javascript">
				
				function setA0801aValue(record,index){//学位
					Ext.getCmp('a0801a').setValue(record.data.value);
				}
				function setA0901aValue(record,index){//学历
					Ext.getCmp('a0901a').setValue(record.data.value);
				}
				function setA0824Value(record,index){//专业
					Ext.getCmp('a0824').setValue(record.data.value);
				}
				function onkeydownfn(id){
					if(id=='a0801b')
						Ext.getCmp('a0801a').setValue('');
					if(id=='a0901b')
						Ext.getCmp('a0901a').setValue('');
					if(id=='a0827')
						Ext.getCmp('a0824').setValue('');
				}
				odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				};
				//学历学位输出设置
				function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					var msg='';
					if(sr.data.a0899==='true'||sr.data.a0899===true){
						msg = '取消该记录后,该学历学位将不能输出<br/>确定要取消输出该记录吗?';
					}else{
						msg = '选择该记录后，该学历学位将输出<br/>确定要选择输出该记录吗?';
					}
					$h.confirm('系统提示',msg,220,function(id){
						if("ok"==id){
							radow.doEvent('degreesgridchecked',sr.data.a0800);
						}else{
							
							return false;
						}
					});
				}
				
				
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0800 = record.data.a0800;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">删除</a>";
				}
				function deleteRow2(a0800){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA08',a0800);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<odin:toolBar property="toolBar2" applyTo="tol2" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA08" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="学历学位信息集" property="A08">
					<div>
					<odin:hidden property="a0800" title="主键id" ></odin:hidden>
					<odin:hidden property="a0834" title="最高学历标志" />
					<odin:hidden property="a0835" title="最高学位标志" />
					<input type="reset" name="reset" id="resetbtn" style="display: none;" />
					<div id="tol2"></div>
					<table>
						<tr>
							<td>
								<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" topBarId="toolBar2" url="/"   
								 height="210" >
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0899"/>
							     		<odin:gridDataCol name="a0800" />
								  		<odin:gridDataCol name="a0837" />
								  		<odin:gridDataCol name="a0801b" />
								   		<odin:gridDataCol name="a0901b" />
								   		<odin:gridDataCol name="a0814" />
								   		<odin:gridDataCol name="a0827" />			   		
								   		<odin:gridDataCol name="a0811" />
								   		<odin:gridDataCol name="a0804" />
								   		<odin:gridDataCol name="a0807" />
								   		<odin:gridDataCol name="a0904" />
								   		<odin:gridDataCol name="a0801a" />
								   		<odin:gridDataCol name="a0901a" />
								   		<odin:gridDataCol name="a0824" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="输出" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
									  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="类别" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
									  <odin:gridEditColumn header="学历" dataIndex="a0801a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="学位" dataIndex="a0901a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="学校及院系" dataIndex="a0814" edited="false" editor="text"/>
									  <odin:gridEditColumn header="专业" dataIndex="a0824" edited="false" editor="text" />
									  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0837" label="教育类别" required="true" codeType="ZB123"></odin:select2></tr>
									<tr><tags:PublicTextIconEdit property="a0801b" label="学历代码" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0801a" label="学历名称" validator="a0801aLength"></odin:textEdit></tr>
									<tr><odin:numberEdit property="a0811" label="学制年限(年)" maxlength="3"></odin:numberEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0901b" label="学位代码" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0901a" label="学位名称" validator="a0901aLength"></odin:textEdit></tr>
									<tr> <odin:textEdit property="a0814" label="学校（单位）名称" validator="a0814Length"></odin:textEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0827" label="所学专业类别" onchange="setA0824Value" codetype="GB16835" readonly="true" /></tr>
									<tr><odin:textEdit property="a0824" label="所学专业名称" validator="a0824Length"></odin:textEdit></tr>
									<tr><odin:NewDateEditTag property="a0804" label="入学时间"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><odin:NewDateEditTag property="a0807" label="毕（肄）业时间" maxlength="8"></odin:NewDateEditTag></tr>
									<tr><odin:NewDateEditTag property="a0904" label="学位授予时间" maxlength="8"></odin:NewDateEditTag></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
				</odin:groupBox>
				<odin:hidden property="a0899" title="输出"/>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("degreesgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('degreesgrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('degreesgrid');
						var gridobj = document.getElementById('forView_degreesgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('degreesgrid').setHeight(400);
						 Ext.getCmp('degreesgrid').setWidth(570); 
						 document.getElementById('toolBar2').style.width = 863;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>
				<div id="cover_wrap1"></div>
				
				
				<!---------------------------------------------- 奖惩信息集 -------------------------------->
				
				<script type="text/javascript">
				
				//奖惩信息追加
				function appendRewardPunish(){ 
					var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
					if(!sm.hasSelection()){
						alert("请选择一行数据！");
						return;
					}
					radow.doEvent('appendonclick',sm.lastActive+'');
				}
				function setA1404aValue(record,index){//奖惩名称
					Ext.getCmp('a1404a').setValue(record.data.value);
				}
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a1400 = record.data.a1400;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a1400+"&quot;)\">删除</a>";
				}
				function deleteRow2(a1400){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA14',a1400);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				
				<odin:toolBar property="toolBar5" applyTo="tol3">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="RewardPunishAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA14" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="奖惩信息集" property="A14">
					<div>
					<div id="tol3" style="width: 1000px;"></div>
					<div id="wzms">
						<table>
							<tr>
								<odin:textarea property="a14z101" cols="80" rows="4" colspan="5" label="文字描述" validator="a14z101Length"></odin:textarea>
							</tr>
						</table>
					</div>
					<div id="table1">
						
						 <table>
						 	<tr>
						 		<td>
						 					<odin:grid property="RewardPunishGrid" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
								 height="200">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1400" />
								  		<odin:gridDataCol name="a1404b" />
								  		<odin:gridDataCol name="a1404a" />
								   		<odin:gridDataCol name="a1415" />
								   		<odin:gridDataCol name="a1414" />
								   		<odin:gridDataCol name="a1428" />			   		
								   		<odin:gridDataCol name="a1411a" />
								   		<odin:gridDataCol name="a1407" />
								   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text"/>
									  <odin:gridEditColumn2 header="奖惩名称代码" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
									  <odin:gridEditColumn  header="奖惩名称"  dataIndex="a1404a" edited="false" editor="text" />
									  <odin:gridEditColumn2 header="受奖惩时职务层次" dataIndex="a1415" edited="false" codeType="ZB09" editor="select"/>
									  <odin:gridEditColumn2 header="批准机关级别" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
									  <odin:gridEditColumn2 header="批准机关性质" dataIndex="a1428" edited="false" codeType="ZB128" editor="select" hidden="true"/>
									  <odin:gridEditColumn header="批准机关" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
									  <odin:gridEditColumn header="批准日期" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
									  <odin:gridEditColumn header="奖惩撤销日期" dataIndex="a1424" edited="false" editor="text" maxLength="8" hidden="true"/>
									   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
									</td>
									<td>
										<table id="table2">
											<tr id="btn" height="35px;">
												<td><odin:button text="追加当前条" handler="appendRewardPunish" property="append"></odin:button> </td>
												<td id="btn2"><odin:button text="全部替换" property="addAll"></odin:button> </td>
											</tr>
											<tr height="35px;">
											<tags:PublicTextIconEdit property="a1404b" label="奖惩名称代码" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
											</tr>
											<tr height="35px;"><odin:textEdit property="a1404a" label="奖惩名称" ></odin:textEdit></tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:select2 property="a1414" label="批准机关级别"  codeType="ZB03"></odin:select2>	</tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1428" label="批准机关性质" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:textEdit property="a1411a" label="批准机关" ></odin:textEdit></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1407" label="批准日期" maxlength="8" isCheck="true"></odin:NewDateEditTag></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1424" label="奖惩撤销日期" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
											<odin:hidden property="a1400" title="主键id" ></odin:hidden>
										</table>
									</td>
									</tr>
								</table>
					</div>
					</div>
				</odin:groupBox>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("RewardPunishGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           	 	$h.selectGridRow('RewardPunishGrid',0);
				           	 	firstload = false;
				           	 }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a14);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a14);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('RewardPunishGrid');
						var gridobj = document.getElementById('forView_RewardPunishGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className="divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
					     Ext.getCmp('RewardPunishGrid').setHeight(350);
						 Ext.getCmp('RewardPunishGrid').setWidth(507); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				
				
				
				<!---------------------------------------------- 考核信息集 -------------------------------->
				
				<script type="text/javascript">
				
				
				function changedispaly(obj){
					var choose = Ext.getCmp('a0191').getValue();	
					if(choose){
						document.getElementById('choose').style.visibility='visible';
					}else{
						document.getElementById('choose').style.visibility='hidden';
					}
				}
				
				function yearChange(){
				    var now = new Date();
				    var year = now.getFullYear();
				    var yearList = document.getElementById("a1521");
				    for(var i=0;i<=50;i++){
				        year = year-1;
				        yearList.options[i] = new Option(year,year);
				    }
				}
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a1500 = record.data.a1500;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a1500+"&quot;)\">删除</a>";
				}
				function deleteRow2(a1500){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA15',a1500);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				
				<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv2">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="AssessmentInfoAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA15" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="考核信息集" property="A15">
				<div>
				<div id="btnToolBarDiv2" align="center" style="width: 1000px;"></div>
				<odin:hidden property="a1500" title="主键id" ></odin:hidden>
				<div id="wzms">
					<table>
						<tr>
							<td><odin:textarea property="a15z101" cols="70" rows="4" colspan="4" label="文字描述" validator="a15z101Length"></odin:textarea></td>
							<td><div id="choose" style="visibility: hidden;">
						<table><odin:numberEdit property="a1527" label="选择年度个数" size="6"></odin:numberEdit></table>
						</div></td>
						
						<td id="td"><odin:checkbox property="a0191" label="与列表关联" onclick="changedispaly(this)"></odin:checkbox></td>
						
						</tr>
					</table>
				</div>
				<div id="grid">
					<table>
						<tr>
							<td>
								<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
							 height="200">
								<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="a1500" />
							  		<odin:gridDataCol name="a1521" />
							   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								  <odin:gridRowNumColumn />
								  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
								  <odin:gridEditColumn header="年度" dataIndex="a1521" edited="false" editor="text"/>
								  <odin:gridEditColumn2  header="考核结论类别"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"/>
								   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
								</odin:gridColumnModel>
							</odin:grid>	
							</td>
							<td>
								<table>
									<tr height="50">
										<odin:select2 property="a1521" label="考核年度" required="true" maxlength="4" multiSelect="true" ></odin:select2>
									</tr>
									<tr height="50">
										<tags:PublicTextIconEdit property="a1517" label="考核结论类别" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				</div>
				</odin:groupBox>
				<script type="text/javascript">
				Ext.onReady(function(){
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a15);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('AssessmentInfoGrid');
						var gridobj = document.getElementById('forView_AssessmentInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className= "divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth;	
						 Ext.getCmp('AssessmentInfoGrid').setWidth(535); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				
				
				
				
				
				<!---------------------------------------------- 业务信息集 -------------------------------->
				
				<script type="text/javascript">
				function formcheck(){
					return odin.checkValue(document.forms.commForm);
				}
				function setA6004Value(record,index){//学历
					Ext.getCmp('a6004').setValue(record.data.value);
				}
				function setA6006Value(record,index){//学位
					Ext.getCmp('a6006').setValue(record.data.value);
				}
				function setA6108Value(record,index){//学历
					Ext.getCmp('a6108').setValue(record.data.value);
				}
				function setA6110Value(record,index){//学位
					Ext.getCmp('a6110').setValue(record.data.value);
				}
				function saveTrain(){
					var a1107 = document.getElementById('a1107').value;//培训起始时间
					var a1111 = document.getElementById('a1111').value;//培训结束时间
					var text1 = dateValidate(a1107);
					var text2 = dateValidate(a1111);
					if(text1!==true){
						parent.$h.alert('系统提示','培训起始时间' + text1, null,400);
						return false;
					}
					if(text2!==true){
						parent.$h.alert('系统提示','培训结束时间' + text2, null,400);
						return false;
					}
					radow.doEvent('saveA11.onclick');
				}
				//弹出窗口不是父级， 无法显示在当前位置。
				function deleteRowA60(){ 
					var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
					if(!sm.hasSelection()){
						parent.$h.alert("系统提示","请选择一行数据！");
						return;
					}
					parent.Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA60',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				</script>
				<odin:groupBox property="A60" title="考试录用人员信息">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a6001" label="考试录用人员" codeType="XZ09"/>
								<odin:NewDateEditTag property="a6002" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录用时间" maxlength="80"/>
								<odin:select2 property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;录用时政治面貌" labelSpanId="a6003SpanId" validator="a6003Length"  codeType="GB4762" ></odin:select2>
								
								<tags:PublicTextIconEdit property="a6009" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人员来源情况" codetype="ZB146" readonly="true" />
							</tr>
							<tr>
							
								<tags:PublicTextIconEdit property="a6005" label="录用时学历代码" onchange="setA6004Value" codetype="ZB64" readonly="true" />
								<odin:textEdit property="a6004" label="录用时学历名称" validator="a6004Length" />
								
								<tags:PublicTextIconEdit property="a6007" label="录用时学位代码" onchange="setA6006Value" codetype="GB6864" readonly="true" />
								<odin:textEdit property="a6006" label="录用时学位名称" validator="a6006Length" />
								
							</tr>
							<tr>
								<odin:numberEdit property="a6008" label="&nbsp;&nbsp;&nbsp;&nbsp;录用时基层工作时间" maxlength="2" />
								<odin:select2 property="a6010" label="是否服务基层项目人员" codeType="XZ09" />
								<odin:select2 property="a6011" label="是否退役士兵" codeType="XZ09" />
								<odin:select2 property="a6012" label="是否退役大学生士兵" codeType="XZ09" />
							</tr>
							<tr>
								<odin:select2 property="a6013" label="是否残疾人" codeType="XZ09"/>
								<odin:select2 property="a6014" label="是否有海外留学经历" codeType="XZ09"/>
								<odin:numberEdit property="a6015" label="留学年限" maxlength="2"/>
								<odin:select2 property="a6016" label="是否有海外工作经历" codeType="XZ09"/>
							</tr>
							<tr>
								<odin:textEdit property="a6017" label="海外工作年限" validator="a6_101716Length"/>
							</tr>
							<tr>
								<odin:textEdit property="a6401" label="报考准考证号" validator="a6401Length" maxlength="50"/>
								<odin:numberEdit property="a6402" label="行政职业能力分数" maxlength="3"/>
								<odin:numberEdit property="a6403" label="申论分数" maxlength="3"/>
								<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3" />
							</tr>
							<tr>
								<odin:numberEdit property="a6405" label="专业能力测试分数" maxlength="3"/>
								<odin:numberEdit property="a6406" label="公共科目笔试成绩总分" maxlength="3"/>
								<odin:numberEdit property="a6407" label="专业考试成绩" maxlength="3"/>
								<odin:numberEdit property="a6408" label="面试成绩" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A61" title="选调生信息">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2970" label="选调生" codeType="ZB137" />
								<odin:select2 property="a2970a" label="选调生来源" codeType="ZB138" />
								<odin:textEdit property="a2970b" label="选调生初始工作单位" validator="a2970bLength"></odin:textEdit>
								<odin:NewDateEditTag property="a6104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入选调生时间" maxlength="8" />
							</tr>
							<tr>
								<tags:PublicTextIconEdit property="a6109" label="选调时学历代码" onchange="setA6108Value" codetype="ZB64" readonly="true" />
								<odin:textEdit property="a6108" label="选调时学历名称" validator="a6108Length"></odin:textEdit>
								<tags:PublicTextIconEdit property="a6111" label="录用时学位代码" onchange="setA6110Value" codetype="GB6864" readonly="true" />
								<odin:textEdit property="a6110" label="录用时学位名称" validator="a6110Length"/>
							</tr>
							<tr>
								<odin:numberEdit property="a2970c" label="在基层乡镇机关工作时间" maxlength="4">年</odin:numberEdit>
								<odin:select2 property="a6107" label="选调时政治面貌" labelSpanId="a6107SpanId" validator="a6107Length"   codeType="GB4762" ></odin:select2>
								
								<odin:select2 property="a6112" label="是否退役大学生士兵" codeType="XZ09"/>
								<odin:select2 property="a6113" label="是否有海外留学经历" codeType="XZ09"/>
							</tr>
							<tr>
								<odin:numberEdit property="a6114" label="留学年限" maxlength="2"/>
								<odin:select2 property="a6115" label="是否有海外工作经历" codeType="XZ09"/>
								<odin:textEdit property="a6116" label="海外工作年限" validator="a6_101716Length"/>
							</tr>
							<tr>
								<odin:textEdit property="a6401_1" label="报考准考证号" validator="a6401_1Length" maxlength="25"/>
								<odin:numberEdit property="a6402_1" label="行政职业能力分数" maxlength="3"/>
								<odin:numberEdit property="a6403_1" label="申论分数" maxlength="3"/>
								<odin:numberEdit property="a6404_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3"/>
							</tr>
							<tr>
								<odin:numberEdit property="a6405_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专业能力测试分数" maxlength="3"/>
								<odin:numberEdit property="a6406_1" label="公共科目笔试成绩总分" maxlength="3"/>
								<odin:numberEdit property="a6407_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专业考试成绩" maxlength="3"/>
								<odin:numberEdit property="a6408_1" label="面试成绩" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A62" title="公开遴选信息">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2950" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公开遴选" codeType="XZ09"></odin:select2>
								<odin:select2 property="a6202" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;遴选类别" codeType="ZB142" />
								<odin:select2 property="a6203" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;遴选方式" codeType="ZB143" />
								<odin:select2 property="a6204" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原单位层级" codeType="ZB141" />
							</tr>
							<tr>
								<odin:NewDateEditTag property="a6205" label="遴选时间" maxlength="8"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A63" title="公开选调信息">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2951" label="公开选调" codeType="XZ09"></odin:select2>
								<odin:select2 property="a6302" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公开选调类别" codeType="ZB142" />
								<odin:select2 property="a6303" label="原单位类别" codeType="ZB144" />
								<odin:select2 property="a6304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原单位层级" codeType="ZB141" />
							</tr>
							<tr>
								<odin:select2 property="a6305" label="原单位职称或职务" codeType="ZB145" />
								<odin:NewDateEditTag property="a6306" label="公开选调时间" maxlength="8"/>
								<odin:select2 property="a6307" label="是否有海外留学经历" codeType="XZ09"/>
								<odin:numberEdit property="a6308" label="留学年限" maxlength="2"/>
							</tr>
							<tr>
								<odin:select2 property="a6309" label="&nbsp;&nbsp;&nbsp;&nbsp;是否有海外工作经历" codeType="XZ09"/>
								<odin:textEdit property="a6310" label="海外工作年限" validator="a6_101716Length"/>
							</tr>
						</table>
					</odin:groupBox>
					
					<odin:groupBox property="A64" title="考试信息">
						<table cellspacing="2" width="100%" align="center">
							<tr>
								<odin:numberEdit property="a6401" label="报考准考证号" maxlength="25"/>
								<odin:numberEdit property="a6402" label="行政职业能力分数" maxlength="3"/>
								<odin:numberEdit property="a6403" label="申论分数" maxlength="3"/>
								<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					
					<odin:groupBox property="A11" title="培训信息">
					<odin:toolBar property="toolBar8" applyTo="tol4">
									<odin:fill></odin:fill>
									<odin:buttonForToolBar text="保存" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
									<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
									<odin:buttonForToolBar text="&nbsp;&nbsp;删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRowA60"></odin:buttonForToolBar>
					</odin:toolBar>
					
					<!--<div style="border: 1px solid #99bbe8;">-->
					<div id="border">
					<div id="tol4" align="left"></div>
					<odin:hidden property="a1100" title="主键id"></odin:hidden>
					
					<table cellspacing="2" width="460" align="left">
						<tr>
							<odin:textEdit property="a1131" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训班名称" validator="a1131Length"></odin:textEdit>
							<odin:select2 property="a1101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训类别" codeType="ZB29"></odin:select2>
							<odin:textEdit property="a1114" label="培训主办单位" validator="a1114Length"></odin:textEdit>
							<odin:textEdit property="a1121a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训机构名称" validator="a1121aLength"></odin:textEdit>
						</tr>
						<tr>
							<odin:NewDateEditTag property="a1107" isCheck="true" label="培训起始时间" maxlength="8"></odin:NewDateEditTag>
							<odin:NewDateEditTag property="a1111" isCheck="true" label="培训结束时间" maxlength="8"></odin:NewDateEditTag>
							<odin:numberEdit property="a1107c" label="培训时长" decimalPrecision="1" maxlength="4"></odin:numberEdit>
						    <odin:numberEdit property="a1108" label="学时" decimalPrecision="1" maxlength="4"></odin:numberEdit>
						
						</tr>
						<tr style="display: none;">
						    <odin:textEdit property="a1107a" label="" readonly="true">月</odin:textEdit>
							<odin:textEdit property="a1107b" label="零" readonly="true">天</odin:textEdit>
						</tr>
						<tr>
						    <odin:select2 property="a1127" label="培训机构类别" codeType="ZB27"></odin:select2>
						    <odin:select2 property="a1104" label="培训离岗状态" codeType="ZB30"></odin:select2>
						     <odin:select2 property="a1151" label="出国（出境）培训标识" data="['1','是'],['0','否']"></odin:select2>
						</tr>
						<tr>
						   
						</tr>
						<tr>
							<td colspan="8">
								
								<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
								 height="230">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1100" />
								  		<odin:gridDataCol name="a1101" />
								  		<odin:gridDataCol name="a1131" />
								  		<odin:gridDataCol name="a1107" />
								  		<odin:gridDataCol name="a1111" />
								  		<odin:gridDataCol name="a1107a" />
								  		<odin:gridDataCol name="a1107b" />
								  		<odin:gridDataCol name="a1107c" type="float"/>
								  		<odin:gridDataCol name="a1108" type="float"/>
								  		<odin:gridDataCol name="a1114" />
								  		<odin:gridDataCol name="a1121a" />
								  		<odin:gridDataCol name="a1127" />
								  		<odin:gridDataCol name="a1104" />			
								   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text"  width="100" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="培训类别" dataIndex="a1101" editor="select" codeType="ZB29" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训班名称" dataIndex="a1131" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训起始时间" dataIndex="a1107" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训结束时间" dataIndex="a1111" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训时长（月）" dataIndex="a1107a" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="天" dataIndex="a1107b" editor="text" width="100" edited="false" hidden="true"/>
									  <odin:gridEditColumn header="培训时长" dataIndex="a1107c" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="学时" dataIndex="a1108" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训主办单位" dataIndex="a1114" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="培训机构名称" dataIndex="a1121a" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn2 header="培训机构类别" dataIndex="a1127" editor="select" edited="false" codeType="ZB27" width="100"/>
									  <odin:gridEditColumn2 header="培训离岗状态" dataIndex="a1104" editor="select" edited="false" codeType="ZB30" width="100"/>
									  <odin:gridEditColumn2 header="出国（出境）培训标识" dataIndex="a1151" editor="select" edited="false" selectData="['1','是'],['0','否']" width="100" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
							</td>
						</tr>
					</table>
					</div>
					</odin:groupBox>
					
					
						<!-- <script type="text/javascript">
						 Ext.onReady(function(){
						 	var newnode = document.createElement('span');
						 	newnode.style.setAttribute("visibility","hidden");   
						 	newnode.appendChild(document.createTextNode("      a"));
						 	var objj = document.getElementById('a0192d_combo').parentNode;
						 	objj.appendChild(newnode);
						 });
						</script> -->
						<!-----------------------------进入管理------------------------------------------------------->
						<odin:groupBox property="A29" title="进入管理">
						<table cellspacing="2" width="440" align="left">
						
							<tr>
							 	<odin:NewDateEditTag property="a2907"  label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入本单位日期" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
								<tags:PublicTextIconEdit property="a2911" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入本单位变动类别"  codetype="ZB77" readonly="true"></tags:PublicTextIconEdit>		
								<odin:textEdit property="a2941" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在原单位职务"  validator="a2941Length"></odin:textEdit>
								
							</tr>
							<tr>
								<odin:NewDateEditTag property="a2947" label="进入公务员队伍时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a2944" label="在原单位职务层次" validator="a2944Length"></odin:textEdit>
								<odin:textEdit property="a2921a" label="进入本单位前工作单位名称" validator="a2921aLength"></odin:textEdit>
							</tr>
							<tr>
								<odin:NewDateEditTag property="a2949" label="公务员登记时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
								
								<td align="right" style="padding-right: 8px;"><span id="a2947aSpanId" style="font-size: 12" >进入本单位时基层工作经历时间</span> </td>
								<td colspan="1" align="left">
									<table cellpadding="0" cellspacing="0">
									  <tr style="padding-left: 0px;margin-left: 0px;">
									    <odin:numberEdit property="a2947a"   width="70" decimalPrecision="0" maxlength="4" minValue="0">年</odin:numberEdit>
										<odin:numberEdit property="a2947b" width="70" maxlength="2" maxValue="12" decimalPrecision="0" minValue="0">月</odin:numberEdit>
									  </tr>
									</table>
								</td>
							</tr>
						</table>
						</odin:groupBox>
						
						<script type="text/javascript">
						
						</script>
						<script type="text/javascript">
						//弹出窗口不是父级， 无法显示在当前位置。
						function a3101change(){ 
							var a3101 = document.getElementById('a3101').value;
							if(a3101!=null&&a3101!=''){
								odin.setSelectValue('a3001','31');
							}
							var codeType = "orgTreeJsonData";
							var codename = "code_name";
						    var winId = "winId"+Math.round(Math.random()*10000);
						    var label = "选择调往单位";
						  	var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
							
							var url = "pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
							
						    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
						}
						function a3140change(){
							var a3107 = document.getElementById('a3107').value;
							var a3109 = document.getElementById('a3109').value;
							if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
								selecteWinEnable('a3109');
								selecteWinEnable('a3107');
								return;
							}
							if(a3107==null||a3107==''){
								selecteWinEnable('a3109');
							}else{
								selecteWinDisable('a3109');
							}
						}
						function a3141change(){
							var a3109 = document.getElementById('a3109').value;
							var a3107 = document.getElementById('a3107').value;
							if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
								selecteWinEnable('a3109');
								selecteWinEnable('a3107');
								return;
							}
							if(a3109==null||a3109==''){
								selecteWinEnable('a3107');
							}else{
								selecteWinDisable('a3107');
							}
						}
						function onkeydownfn(id){
							if(id=='a3107'){
								a3140change();
							}else if(id=='a3109'){
								a3141change();
							}else if(id=='a2911'){
							}else if(id=='a0120'){
								var record = {data:{value:'',key:''}};
								setParentA0120Value(record);
							}else if(id=='a0122'){
								var record = {data:{value:'',key:''}};
								setParenta0122Value(record);
							}
						}
						</script>
						<!-----------------------------离退------------------------------------------------------->
						<odin:groupBox property="A30" title="离退和退出管理">
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:select2 property="a3101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离退类别" onchange="a3101change" codeType="ZB132"></odin:select2>
								<odin:NewDateEditTag property="a3104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离退批准日期" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a3137" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离退批准文号" validator="a3137Length"></odin:textEdit>		
							</tr>
							<tr>		
								<tags:PublicTextIconEdit property="a3110" label="离退前级别" codetype="ZB134" readonly="true"></tags:PublicTextIconEdit>	
								<odin:textEdit property="a3117a" label="离退后管理单位" validator="a3117aLength"></odin:textEdit>	
								<tags:PublicTextIconEdit property="a3001" label="退出管理方式" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>	
							</tr>
							<tr>
								<odin:textEdit property="a3034" label="备注" validator="a3034Length"></odin:textEdit>		
							
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------拟任免------------------------------------------------------->
						<odin:groupBox property="A53" title="拟任免">
						<odin:hidden property="a5399" title="填报人id" />
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟任职务" validator="a5304Length"></odin:textEdit>
								<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟免职务" validator="a5315Length"></odin:textEdit>
								<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任免理由" validator="a5317Length"></odin:textEdit>
							</tr>
							<tr>
								<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
								<odin:NewDateEditTag property="a5321" label="计算年龄时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:NewDateEditTag property="a5323" label="填表时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a5327" label="填表人" validator="a5327Length"></odin:textEdit>
							</tr>
							<tr>
								<odin:textEdit property="a5319" label="呈报单位" validator="a5319Length"></odin:textEdit>
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------住址通讯A37------------------------------------------------------->
						<odin:groupBox property="A37" title="住址通讯">
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textEdit property="a3701" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;办公地址" colspan="4" width="406" validator="a3701Length"></odin:textEdit>
								<odin:textEdit property="a3707a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;办公电话" validator="a3707fLength"></odin:textEdit>
								<odin:textEdit property="a3707c" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;移动电话" validator="a3707cLength"></odin:textEdit>
										
							</tr>
							<tr>
								<odin:textEdit property="a3707b" label="住宅电话" validator="a3707aLength"></odin:textEdit>
								<odin:textEdit property="a3707e" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秘书电话"  validator="a3707cLength"></odin:textEdit>
								<odin:textEdit property="a3708" label="电子邮箱" colspan="4" validator="$h.email" width="407" maxlength="60"></odin:textEdit>	
							</tr>
							<tr>
								<odin:textEdit property="a3711" label="家庭地址" colspan="4" width="406" validator="a3711Length"></odin:textEdit>
								<odin:textEdit property="a3714" label="住址邮编" validator="postcode" colspan="4" width="407" maxlength="6"></odin:textEdit>	
								
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------备注------------------------------------------------------->
						<odin:groupBox property="A71" title="备注" >
							<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textarea property="a7101" label="备注" colspan='100' rows="4" validator='a7101Length'></odin:textarea>
							</tr>
						</table>
							
						</odin:groupBox>
						<odin:hidden property="a7100"/>
						
			</div>
			
		</td>
	</tr>
</table>
</div>





<script type="text/javascript">
$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	$("#b0104SpanId").prepend("<font color='red'>&nbsp;&nbsp;&nbsp;*</font>");
});


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
    	document.getElementById('girdDiv').style.height=document.body.clientHeight-objTop(document.getElementById('girdDiv'))[0]-4;
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
	Ext.onReady(function() {
		//页面调整
		document.getElementById("main").style.width = document.body.clientWidth + "px";
		document.getElementById("main").style.height = document.body.clientHeight + "px";
		document.getElementById("bar_div").style.width = document.getElementById("main").style.height-1 + "px";
	});
	
	
	
	Ext.onReady(function(){
		
		document.getElementById("A02").style.display="none";		//职务信息集
		document.getElementById("A06").style.display="none";		//专业技术任职资格信息集
		document.getElementById("A08").style.display="none";		//学历学位信息集
		document.getElementById("A14").style.display="none";		//奖惩信息集
		document.getElementById("A15").style.display="none";		//考核信息集
		//document.getElementById("A36").style.display="none";
		//document.getElementById("A41").style.display="none";
		//document.getElementById("A68").style.display="none";
		//document.getElementById("A69").style.display="none";
		
		document.getElementById("A11").style.display="none";		//培训信息
		document.getElementById("A29").style.display="none";		//进入管理
		document.getElementById("A30").style.display="none";		//离退和退出管理
		document.getElementById("A37").style.display="none";		//住址通讯
		document.getElementById("A53").style.display="none";		//拟任免
		document.getElementById("A60").style.display="none";		//考试录用人员信息
		document.getElementById("A61").style.display="none";		//选调生信息
		document.getElementById("A62").style.display="none";		//公开遴选信息
		document.getElementById("A63").style.display="none";		//公开选调信息
		document.getElementById("A64").style.display="none";		//考试信息
		document.getElementById("A71").style.display="none"; 		//备注
		
	});	
	
	
	//入党时间 
	function a0140Click(){
		var name = document.getElementById("a0101").value;
		var Id = document.getElementById("a0000").value;
	 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath);
	}
	function a0140Click2(){
		var name = document.getElementById("a0101").value;
		var Id = document.getElementById("a0000").value;
	 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath);
	}
	
	function a3001change(rs){
		var codeType = "orgTreeJsonData";
		var codename = "code_name";
	    var winId = "winId"+Math.round(Math.random()*10000);
	    var label = "选择调往单位";
//	    var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
		//alert(url);
		var url = "pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
//	    odin.openWindow(winId,label,url,270,415,window,false,true);	
	    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
	}
	
	
	
	
	
</script>