<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text="" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="图片分发" id="imp10btn" icon="images/icon/exp.png" />
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="抽取标准版数据" id="imp4btn" icon="images/icon/exp.png" />
	<odin:separator></odin:separator><%-- --%>
	<%-- <odin:buttonForToolBar text="导入ZZB3数据" id="imp2btn" icon="images/icon/exp.png" />
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="配置" id="initbtn" isLast="true" icon="image/u53.png" />
</odin:toolBar>

<table style="width: 100%;border-collapse:collapse;" id="table_qo_id">
	<tr>
		<td width="15%">
			<div style="overflow: auto; height: 100%; position: relative;">
				<odin:tab id="tab" width="240">
					<odin:tabModel>
						<odin:tabItem title="查询" id="tab1" isLast="true"></odin:tabItem>
					</odin:tabModel>
					<odin:tabCont itemIndex="tab1" className="tab">
					<table id="myform" >
						<tr height="12px"><td colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:select2 property="impstutas1" label="导入状态" value="1" data="['0','全部'],['1','未接收'],['2','已接收'],['3','已拒绝'],['4','接收中']"></odin:select2>
						</tr>
						<tr height="12px"><td colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:dateEdit property="createtimesta" format="Ymd" label="&nbsp;起始时间"></odin:dateEdit>
						</tr>
						<tr height="12px"><td colspan="2">&nbsp;</td></tr>
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
		<td width="85%">
			<div id="toolDiv"></div> 
			<odin:editgrid property="MGrid" title=""
				autoFill="true" bbarId="pageToolBar" pageSize="20"
				isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel id="id" root="data"
					totalProperty="totalCount">

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

					<odin:gridEditColumn header="文件名称" width="340" dataIndex="filename"
						edited="false" editor="text" />
					<odin:gridEditColumn header="导入时间" align="center" width="170"
						dataIndex="imptime" editor="text" edited="false" />
					<odin:gridEditColumn header="上报机构" dataIndex="empgroupname"
						align="center" edited="false" editor="text" width="220" />
					<%-- <odin:gridEditColumn header="校验方案" hidden="false"
						dataIndex="vsc001" align="center" codeType="VSC001" edited="false"
						editor="select" width="100" /> --%>
					<odin:gridEditColumn2 header="是否校验" hidden="false"
						dataIndex="isvirety" align="center"
						selectData="['0','否'],['1','是']" edited="false" editor="select"
						width="100" />
					<%--
		<odin:gridEditColumn header="未通过数据量" dataIndex="wrongnumber" hidden="true"
			align="center" edited="false" editor="text" width="100" />
			--%>
					<odin:gridEditColumn header="人员数量" dataIndex="totalnumber"
						align="center" edited="false" editor="text" width="100" />
					<odin:gridEditColumn2 header="导入状态" dataIndex="impstutas"
						align="center"
						selectData="['1','未接收'],['2','已接收'],['3','已拒绝'],['4','接受中']"
						edited="false" editor="select" width="100" />
					<%-- <odin:gridEditColumn align="center" width="100" header="校验"
						dataIndex="imprecordid" editor="text" edited="false"
						renderer="dataVerify" /> --%>
					<odin:gridEditColumn align="center" width="300" header="操作"
						dataIndex="imprecordid" editor="text" edited="false"
						hidden="false" renderer="compRender" />
					<%-- <odin:gridEditColumn align="center" width="100" header="接收查看"
						hidden="false" dataIndex="imprecordid" editor="text"
						edited="false" renderer="detailRender" /> --%>
					<odin:gridEditColumn header="id" dataIndex="imprecordid"
						align="center" hidden="true" edited="false" editor="text"
						isLast="true" />

				</odin:gridColumnModel>
			</odin:editgrid>

		</td>
	</tr>
</table>

<odin:hidden property="imprecordid" />

<odin:window src="/blank.htm" id="win10" width="500" height="200"
	title="图片分发" modal="true"></odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500"
	title="信息校验" modal="true"></odin:window>
<odin:window src="/blank.htm" id="win1" width="650" height="460"
	title="导入数据窗口">
</odin:window>
<odin:window src="/blank.htm" id="deptWin" width="560" height="350"
	maximizable="false" title="窗口"/>
<odin:window src="/blank.htm" id="initwin2" width="650" height="392"
	title="配置窗口">
</odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350"
	maximizable="false" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="impExcelWin" width="560" height="350" maximizable="false" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="winfresh" width="550" height="400"
	title="导入数据文件进度" />
<odin:window src="/blank.htm" id="win4" width="650" height="460"
	title="导入数据窗口" />
<script type="text/javascript">

function compRender(value, params, rs, rowIndex, colIndex, ds) {
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
function dataCompWin(imprecordid){
	document.getElementById('imprecordid').value = imprecordid;
	radow.doEvent('dataComp',imprecordid);
}
	
	/**
	 * 数据授权的render函数
	 */
	function dataVerify(value, params, rs, rowIndex, colIndex, ds) {
			return "<a href=\"javascript:openDataVerifyWin('" + value + "')\">校验</a>";
	}
	
	function openDataVerifyWin(imprecordid){
		radow.doEvent('openDataVerifyWin',imprecordid);
		
	}
	
	function detailRender(value, params, rs, rowIndex, colIndex, ds) {
		///var impstutas= rs.get("impstutas");
		return "<a href=\"javascript:openLogWin('" + value + "')\">详情</a>";
	}
	function openLogWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid +",1";
///		doOpenPupWin(
///				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.RefreshOrgRecRej&imprecordid="
///						+ imprecordid +",1", "导入详情", 600, 470, null);
		$h.openWin('simpleExpWin46','pages.dataverify.RefreshOrgRecRej&imprecordid='+ imprecordid +",1",'导入详情',600,445,imprecordid+",1",'<%=request.getContextPath()%>');
	}
	function openDetailWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid;
		doOpenPupWin(
				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.ImpDetail&imprecordid="
						+ imprecordid, "导入详情", 600, 470, null);
		
	}
	function grantTabChange(tabObj, item) {
		if (item.getId() == 'tab2') {
			odin.ext.getCmp('groupgrid').view.refresh(true);
		}
	}
	function clickquery() {
		radow.doEvent('query.onclick');
	}

	function getShowTow(){
		odin.ext.getCmp('tab').activate('tab2');
	}
	Ext.onReady(function() {
		setWidthHeight();
		window.onresize=setWidthHeight;
		//页面调整
		/* Ext.getCmp('MGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_MGrid'))[0]-4);
		//Ext.getCmp('MGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_MGrid'))[1]-2); 
		//document.getElementById("panel_content").style.width = Ext.getCmp('MGrid').getWidth() + "px";
		document.getElementById("toolDiv").style.width = Ext.getCmp('MGrid').getWidth()+17 + "px";
		document.getElementById("myform").style.height = Ext.getCmp('MGrid').getHeight()-20 + "px";//tb1 */
	});
	function setWidthHeight(){
		document.getElementById("table_qo_id").parentNode.parentNode.style.overflow='hidden';
		document.getElementById("table_qo_id").parentNode.style.width=document.body.clientWidth;
		var heightClient=window.parent.document.body.clientHeight;//窗口高度
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//系统，上方菜单高度
		//30
		var height_toolbar=document.getElementById("toolDiv").offsetHeight;//grid工具栏高度
		Ext.getCmp('MGrid').setHeight(heightClient-gwyxt_height-30-height_toolbar);
		Ext.getCmp('tab').setHeight(heightClient-gwyxt_height-30);
		
		Ext.getCmp('MGrid').setWidth(document.getElementById("toolDiv").offsetWidth);
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
			}
		};
</script>