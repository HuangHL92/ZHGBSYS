<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>



<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/odin.css" />
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text="" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="查询" icon="images/search.gif" id="query" />
	<odin:separator></odin:separator> --%>
	
	<odin:buttonForToolBar text="按机构导入数据(HZB1.0)" handler="impTestOld"
		icon="images/icon/exp.png" id="uploadOldbtn"/>
	<%-- <odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="按机构导入数据(套改版)" handler="impTestMake"
		icon="images/icon/exp.png" id="uploadMakebtn"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="按机构导入数据" handler="impTest2111"
		icon="images/icon/exp.png" id="uploadbtn2" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="按人员导入数据" handler="impTest3"
		icon="images/icon/exp.png" id="uploadbtn3" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="通用格式导入数据" handler="TYGSimp"
		icon="images/icon/exp.png" id="tygsUploadbtn" />
<%-- 	<odin:separator></odin:separator> --%>
<%-- 		<odin:buttonForToolBar text="工资通用格式导入数据" handler="WagesSimp" --%>
<%-- 		icon="images/icon/exp.png" id="tygsUploadbtn1" /> --%>
<%-- 	<odin:separator></odin:separator> --%>
<%-- 	<odin:buttonForToolBar text="MYSQL数据库接收" handler="Mysqlimp" --%>
<%-- 		icon="images/icon/exp.png" id="mysqlbtn" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Excel导入数据" handler="Excelimp"
		icon="images/icon/exp.png" id="excelUploadbtn" isLast="true" />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除记录" handler="delimp" isLast="true" 
		icon="images/search.gif" id="delbtn" /> --%>
</odin:toolBar>

<table style="width: 100%;border-collapse:collapse;" id="table_qo_id">
	<tr>
		<td width="15%">
			<div style="overflow: auto; height: 100%;position: relative;">
				<odin:tab id="tab" width="240">
					<odin:tabModel>
						<odin:tabItem title="查询" id="tab1" isLast="true"></odin:tabItem>
					</odin:tabModel>
					<odin:tabCont itemIndex="tab1" className="tab">
						<table id="tb1">
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:select2 property="impstutas1" label="导入状态" value="1" data="['0','全部'],['1','未接收'],['2','已接收'],['3','已拒绝'],['4','接收中']"></odin:select2>
						</tr>
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:dateEdit property="createtimesta" format="Ymd" label="&nbsp;起始时间"></odin:dateEdit>
						</tr>
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:dateEdit property="createtimeend" format="Ymd" label="&nbsp;截止时间"></odin:dateEdit>
						</tr>
						<tr height="12px"><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td></td>
							<td>
								<div style="margin-left: 30px">
								<odin:button property="b1" text="查&nbsp;&nbsp;询" handler="clickquery"></odin:button>
								</div>
							</td>
						</tr>
						<tr>
							<td height="240px" colspan="2"></td>
						</tr>
					</table>
					</odin:tabCont>
			</odin:tab>
			</div>
		</td>
		<td width="85%" valign="top">
		<div id="panel_content">
			<div id="toolDiv"></div>
		</div>
		<odin:editgrid property="MGrid" title="" autoFill="true"
				bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel id="id" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="impinfocheck" />
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="imptime" />
					<odin:gridDataCol name="empgroupname" />
					<%-- <odin:gridDataCol name="vsc001" /> --%>
					<odin:gridDataCol name="isvirety" />
					<odin:gridDataCol name="wrongnumber" />
					<odin:gridDataCol name="totalnumber" />
					<odin:gridDataCol name="impstutas" />
					<odin:gridDataCol name="imprecordid" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="40"
						editor="checkbox" dataIndex="impinfocheck" edited="true"
						hideable="false" gridName="MGrid" checkBoxClick="getCheckList()"
						checkBoxSelectAllClick="getCheckList()" hidden="true"/>
					<odin:gridEditColumn header="文件名称" width="400" dataIndex="filename"
						align="center"  edited="false" editor="text" />
					<odin:gridEditColumn header="导入时间" align="center" width="150"
						dataIndex="imptime" editor="text" edited="false" />
					<odin:gridEditColumn header="上报机构" dataIndex="empgroupname"
						align="center" edited="false" editor="text" width="150" />
					<%-- <odin:gridEditColumn header="校验方案" hidden="true"
						dataIndex="vsc001" align="center" edited="false" editor="select"
						codeType="VSC001" width="100" /> --%>
					<odin:gridEditColumn2 header="是否校验" hidden="false"
						dataIndex="isvirety" align="center"
						selectData="['0','否'],['1','是']" edited="false" editor="select"
						width="100" />
					<odin:gridEditColumn header="人员数量" dataIndex="totalnumber"
						align="center" edited="false" editor="text" width="100" />
					<odin:gridEditColumn2 header="导入状态" dataIndex="impstutas"
						selectData="['1','未接收'],['2','已接收'],['3','已拒绝'],['4','接收中']"
						align="center" edited="false" editor="select" width="100" />
					<odin:gridEditColumn align="center" width="300" header="操作"
						dataIndex="imprecordid" editor="text" edited="false"
						renderer="dataVerify" />

				<%-- 
					<odin:gridEditColumn align="center" width="100" header="校验"
						dataIndex="imprecordid" editor="text" edited="false"
						renderer="dataVerify" />
					<odin:gridEditColumn align="center" width="100" header="导入"
						dataIndex="imprecordid" editor="text" edited="false"
						hidden="false" renderer="compRender" />
					<odin:gridEditColumn align="center" width="100" header="导入"
			dataIndex="imprecordid" editor="text" edited="false" hidden="false"
			renderer="impcompRender" /> 
					<odin:gridEditColumn align="center" width="100" header="接收查看"
						dataIndex="imprecordid" editor="text" edited="false"
						hidden="false" renderer="detailRender" />  --%>

					<odin:gridEditColumn header="id" dataIndex="imprecordid"
						align="center" hidden="true" edited="false" editor="text"
						isLast="true" /> 

				</odin:gridColumnModel>
			</odin:editgrid></td>
	</tr>
</table>


<odin:hidden property="checkList" />
<odin:hidden property="imprecordid" />

<odin:window src="/blank.htm" id="dataVerifyWin" width="960"
	height="500" title="信息校验" modal="true"></odin:window>
<odin:window src="/blank.htm" id="win1" width="560" height="392"
	title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="deptWin" width="560" height="350"
	maximizable="false" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350"
	maximizable="false" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="impExcelWin" width="560" height="350"
	maximizable="false" title="窗口">
</odin:window>
<%-- 
<odin:window src="/blank.htm" id="simpleExpWin2" width="560"
	height="350" maximizable="false" title="窗口" />
--%>
<odin:window src="/blank.htm" id="photoInfoWin" width="560" height="150"
	maximizable="false" title="窗口" />
<odin:window src="/blank.htm" id="impNewHzbORGWin" width="560"
	height="150" maximizable="false" title="窗口" />
<script type="text/javascript">
	function impTestOld() {//导入老版本数据
		$h.openWin('impNewHzbORGOLDWin', 'pages.dataverify.DataVerifyNewOldHZB', '按机构导入数据窗口', 850, 555, '', '<%=request.getContextPath()%>');
	}
	
	function impTestMake() {//导入套改版本数据
		$h.openWin('impNewHzbORGMakeWin', 'pages.dataverify.DataVerifyNewMakeHZB', '按机构导入数据窗口', 850, 555, '', '<%=request.getContextPath()%>');
	}
	
	function impTest2111() {
//			$h.showWindowWithSrc('simpleExpWin11111', contextPath
//					+ "/pages/dataverify/DataVerify2.jsp?i=1",'导入窗口',600,300);
////			$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','导入窗口',850,550,'1111@1111', '');
		$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew', '按机构导入数据窗口', 850, 555, '', '<%=request.getContextPath()%>');
					
	}
	function TYGSimp() {
		$h.openWin('impNewHzbORGWin', 'pages.tygsImp.DataVerifyNew2', '通用格式导入数据窗口', 850, 555, '', '<%=request.getContextPath()%>');
	}
	function WagesSimp(){
		$h.showWindowWithSrc('simpleExpWin2', contextPath
				+ "/pages/tygsImp/DataVerifyWages.jsp?i=1",'导入窗口',530,210);
	}
	function Excelimp() {
		$h.openWin('ExcelImp', 'pages.customquery.ExcelImp', 'Excel导入数据窗口', 394, 420, '', '<%=request.getContextPath()%>');
	}
	function Mysqlimp() {
		$h.openWin('impNewMysqlWin', 'pages.customquery.MysqlImp', 'Mysql库接收', 850, 555, '', '<%=request.getContextPath()%>');
	}
	function impTest2() {
///		var win = odin.ext.getCmp('simpleExpWin');
///		win.setTitle('导入窗口');
		/// win.setSize(500,350); //宽度  高度
///		odin.showWindowWithSrc('simpleExpWin', contextPath
///				+ "/pages/dataverify/DataVerify2.jsp");
		$h.showWindowWithSrc('simpleExpWin11111', contextPath
				+ "/pages/dataverify/DataVerify2.jsp?i=1",'导入窗口',600,300);
				

	}
	function impTest3() {
///	var win = odin.ext.getCmp('simpleExpWin2');
///	win.setTitle('导入窗口');
	//win.setSize(500,350); //宽度  高度
	$h.showWindowWithSrc('simpleExpWin2', contextPath
				+ "/pages/dataverify/DataVerify3.jsp?i=1",'导入窗口',530,250);	
	}
	
	//meiyong
	function impTest() {
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		/// win.setSize(500,350); //宽度  高度
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/dataverify/DataVerify.jsp");
	}
	
	
	function dataVerify(value, params, rs, rowIndex, colIndex, ds) {
		var impstutas = Ext.getCmp('MGrid').getStore().getAt(rowIndex).get('impstutas');//MGrid
		if(impstutas == 1){
			return "<a href=\"javascript:openDataVerifyWin('" + value + "')\">校验</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataCompare('" + value + "')\">对比</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataCompWin('" + value + "')\">接收</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataBack('" + value + "')\">拒绝</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript:openLogWin('" + value + "')\">进度</a>";
		}else if(impstutas == 3){
			return "";
		}else{
			return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript:openLogWin('" + value + "')\">进度</a>";
		}
	}
	/* function dataVerify(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript:openDataVerifyWin('" + value + "')\">校验</a>";
	} */
	
	function openDataVerifyWin(imprecordid){
		radow.doEvent('openDataVerifyWin',imprecordid);
	}
	
	function compRender(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript: dataCompWin('" + value + "')\">导入</a>";
	}
	function dataCompWin(imprecordid){
		document.getElementById('imprecordid').value = imprecordid;
		radow.doEvent('dataComp',imprecordid);
	}
	
	function impcompRender(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript: impdataCompWin('" + value + "')\">导入</a>";
	}
	function impdataCompWin(imprecordid){
		radow.doEvent('impdataCompWin',imprecordid);
	}
	/**
	 * 数据授权的render函数
	 */
	function detailRender(value, params, rs, rowIndex, colIndex, ds) {
		//var impstutas= rs.get("impstutas");
		return "<a href=\"javascript:openLogWin('" + value + "')\">详情</a>";
	}
	function openLogWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid+","+"1";
///		doOpenPupWin(
///				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.RefreshOrgRecRej&imprecordid="
///						+ imprecordid +"&type=1", "导入详情", 600, 470, null);
		$h.openWin('simpleExpWin46','pages.dataverify.RefreshOrgRecRej&imprecordid='+ imprecordid +",1",'导入详情',600,445,imprecordid+",1",'<%=request.getContextPath()%>');
	}
	//meiyong
	function openDetailWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid;
		doOpenPupWin(
				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.ImpDetail&imprecordid="
						+ imprecordid, "导入详情", 600, 470, null);
	}
	//????
	function grantTabChange(tabObj, item) {
		if (item.getId() == 'tab2') {
			odin.ext.getCmp('groupgrid').view.refresh(true);
		}
	}
	function clickquery() {
		///document.getElementById("query").click();
		radow.doEvent('MGrid.dogridquery');
	}
////????
function impScheme(){
	 odin.showWindowWithSrc("impExcelWin",contextPath+"/pages/dataimpexcel/Dataimpexcel.jsp"); 
}

///???
	function getShowTow(){
		odin.ext.getCmp('tab').activate('tab2');
	}
	Ext.onReady(function() {
		setWidthHeight();
		window.onresize=setWidthHeight;
		//页面调整
		//Ext.getCmp('MGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_MGrid'))[0]-4);
		//Ext.getCmp('MGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_MGrid'))[1]-2); 
		//document.getElementById("panel_content").style.width = Ext.getCmp('MGrid').getWidth()+17 + "px";
		//document.getElementById("toolDiv").style.width = Ext.getCmp('MGrid').getWidth()+17 + "px";
		//document.getElementById("tb1").style.height = Ext.getCmp('MGrid').getHeight()-20 + "px";//tb1
		
		
		
	});
	function setWidthHeight(){
		document.getElementById("table_qo_id").parentNode.parentNode.style.overflow='hidden';
		document.getElementById("table_qo_id").parentNode.style.width=document.body.clientWidth;
		var heightClient=window.parent.document.body.clientHeight;//窗口高度
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//系统，上方菜单高度
		//30
		var height_toolbar=document.getElementById("panel_content").offsetHeight;//grid工具栏高度
		Ext.getCmp('MGrid').setHeight(heightClient-gwyxt_height-30-height_toolbar);
		Ext.getCmp('tab').setHeight(heightClient-gwyxt_height-30);
		
		Ext.getCmp('MGrid').setWidth(document.getElementById("panel_content").offsetWidth);
	}
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
	
	//删除记录-lzy
	function delimp(){
		radow.doEvent('delimpinfo');
	}
	
	function addCloseL(subWinId,imprecordid){
		var openerWin = $h.getTopParent().Ext.getCmp(subWinId);
		//console.log(openerWin);
		openerWin.on('beforeclose', function() {
			var boo = kkkk(subWinId,imprecordid);
			return boo;
		});
	}
	function kkkk(subWinId,imprecordid){
		var k = $h.getTopParent().frames["iframe_"+subWinId].document.getElementById('impclose').value;
		if(k==1){
			$h.getTopParent().frames["iframe_"+subWinId].Ext.MessageBox.confirm('提示', '是否进行导入？', function(retp){
				if(retp=='yes'){
					$h.getTopParent().Ext.getCmp(subWinId).close(); 
					$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','校验窗口',1050,598, '2@'+imprecordid+'','<%=request.getContextPath()%>');

								} else {
									$h.getTopParent().Ext.getCmp(subWinId).close();
								}
							});
			$h.getTopParent().frames["iframe_" + subWinId].document
					.getElementById('impclose').value = "0";
			return false;
		} else {
			return true;
		}

	}
	
	//拒绝
	function dataBack(imprecordid){
		
		$h2.confirm2('系统提示','确认是否拒绝该数据包？',200,function(id){
        	if(id=='ok'){
        		Ext.Msg.buttonText.ok='确定';
        		radow.doEvent("rejectBtn",imprecordid);
       		}else if(id=='cancel'){
        	}
        });
	}
	
	//对比
	function dataCompare(imprecordid){
		$h.openWin('dataComparisonWin','pages.dataverify.DataComparison','校验窗口',900,523, ''+imprecordid+'','<%=request.getContextPath()%>');
	}
	
		var $h2 = {
				'confirm' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='确认';
					Ext.Msg.buttonText.cancel='取消';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm2' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='确认拒绝';
					Ext.Msg.buttonText.cancel='取消';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm3' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='确定';
					Ext.Msg.buttonText.cancel='取消接收';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm4' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='是';
					Ext.Msg.buttonText.cancel='否';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				}
			};
function repeatFun(context,str,imprecordid){
	Ext.MessageBox.buttonText.yes="确定";
	Ext.MessageBox.buttonText.no="取消";
	Ext.MessageBox.buttonText.cancel="查看重复详情";
	Ext.MessageBox.show({
		title:'系统提示',
		msg: context,
		buttons: Ext.MessageBox.YESNOCANCEL,
		fn: function(btn){
				if(btn=='yes'){
					radow.doEvent('impmodel',str);
				};
				if(btn=='no'){
				};
				if(btn=='cancel'){
					radow.doEvent('repeat',str+"&&"+imprecordid);
				}
			}
	});
}
function repeatFun1(context,str,imprecordid,count){
	Ext.MessageBox.buttonText.yes="确定";
	Ext.MessageBox.buttonText.no="取消";
	Ext.MessageBox.buttonText.cancel="查看重复详情";
	Ext.MessageBox.show({
		title:'系统提示',
		msg: context,
		buttons: Ext.MessageBox.YESNOCANCEL,
		fn: function(btn){
				if(btn=='yes'){
					if(count=='0'){
						radowImpmodel(str);
					}else{
						repeatFun2(str,imprecordid,count);
					}
				};
				if(btn=='no'){
				};
				if(btn=='cancel'){
					radow.doEvent('repeat',str+"&&"+imprecordid);
				}
			}
	});
}
function repeatFun2(str,imprecordid,count){
	Ext.MessageBox.buttonText.yes="手动匹配";
	Ext.MessageBox.buttonText.no="直接接受";
	Ext.MessageBox.buttonText.cancel="取消";
	Ext.MessageBox.show({
		title:'系统提示',
		msg: '是否手动匹配无职务人员',
		buttons: Ext.MessageBox.YESNOCANCEL,
		fn: function(btn){
				if(btn=='yes'){
					$h.openWin('DataVerifyCheckPsn','pages.dataverify.DataVerifyCheckPsn','人员手动匹配',1200,750,imprecordid,'<%=request.getContextPath()%>');
				};
				if(btn=='no'){
					radowImpmodel(str);
				};
			}
	});
}
function radowImpmodel(str){
	radow.doEvent('impmodel',str);
}
</script>