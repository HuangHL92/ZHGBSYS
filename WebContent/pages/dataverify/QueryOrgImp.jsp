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
	<%-- <odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="query" />
	<odin:separator></odin:separator> --%>
	
	<odin:buttonForToolBar text="��������������(HZB1.0)" handler="impTestOld"
		icon="images/icon/exp.png" id="uploadOldbtn"/>
	<%-- <odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="��������������(�׸İ�)" handler="impTestMake"
		icon="images/icon/exp.png" id="uploadMakebtn"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��������������" handler="impTest2111"
		icon="images/icon/exp.png" id="uploadbtn2" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����Ա��������" handler="impTest3"
		icon="images/icon/exp.png" id="uploadbtn3" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ͨ�ø�ʽ��������" handler="TYGSimp"
		icon="images/icon/exp.png" id="tygsUploadbtn" />
<%-- 	<odin:separator></odin:separator> --%>
<%-- 		<odin:buttonForToolBar text="����ͨ�ø�ʽ��������" handler="WagesSimp" --%>
<%-- 		icon="images/icon/exp.png" id="tygsUploadbtn1" /> --%>
<%-- 	<odin:separator></odin:separator> --%>
<%-- 	<odin:buttonForToolBar text="MYSQL���ݿ����" handler="Mysqlimp" --%>
<%-- 		icon="images/icon/exp.png" id="mysqlbtn" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Excel��������" handler="Excelimp"
		icon="images/icon/exp.png" id="excelUploadbtn" isLast="true" />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ����¼" handler="delimp" isLast="true" 
		icon="images/search.gif" id="delbtn" /> --%>
</odin:toolBar>

<table style="width: 100%;border-collapse:collapse;" id="table_qo_id">
	<tr>
		<td width="15%">
			<div style="overflow: auto; height: 100%;position: relative;">
				<odin:tab id="tab" width="240">
					<odin:tabModel>
						<odin:tabItem title="��ѯ" id="tab1" isLast="true"></odin:tabItem>
					</odin:tabModel>
					<odin:tabCont itemIndex="tab1" className="tab">
						<table id="tb1">
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:select2 property="impstutas1" label="����״̬" value="1" data="['0','ȫ��'],['1','δ����'],['2','�ѽ���'],['3','�Ѿܾ�'],['4','������']"></odin:select2>
						</tr>
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:dateEdit property="createtimesta" format="Ymd" label="&nbsp;��ʼʱ��"></odin:dateEdit>
						</tr>
						<tr height="12px"><td  colspan="2">&nbsp;</td></tr>
						<tr>
							<odin:dateEdit property="createtimeend" format="Ymd" label="&nbsp;��ֹʱ��"></odin:dateEdit>
						</tr>
						<tr height="12px"><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td></td>
							<td>
								<div style="margin-left: 30px">
								<odin:button property="b1" text="��&nbsp;&nbsp;ѯ" handler="clickquery"></odin:button>
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
					<odin:gridEditColumn header="�ļ�����" width="400" dataIndex="filename"
						align="center"  edited="false" editor="text" />
					<odin:gridEditColumn header="����ʱ��" align="center" width="150"
						dataIndex="imptime" editor="text" edited="false" />
					<odin:gridEditColumn header="�ϱ�����" dataIndex="empgroupname"
						align="center" edited="false" editor="text" width="150" />
					<%-- <odin:gridEditColumn header="У�鷽��" hidden="true"
						dataIndex="vsc001" align="center" edited="false" editor="select"
						codeType="VSC001" width="100" /> --%>
					<odin:gridEditColumn2 header="�Ƿ�У��" hidden="false"
						dataIndex="isvirety" align="center"
						selectData="['0','��'],['1','��']" edited="false" editor="select"
						width="100" />
					<odin:gridEditColumn header="��Ա����" dataIndex="totalnumber"
						align="center" edited="false" editor="text" width="100" />
					<odin:gridEditColumn2 header="����״̬" dataIndex="impstutas"
						selectData="['1','δ����'],['2','�ѽ���'],['3','�Ѿܾ�'],['4','������']"
						align="center" edited="false" editor="select" width="100" />
					<odin:gridEditColumn align="center" width="300" header="����"
						dataIndex="imprecordid" editor="text" edited="false"
						renderer="dataVerify" />

				<%-- 
					<odin:gridEditColumn align="center" width="100" header="У��"
						dataIndex="imprecordid" editor="text" edited="false"
						renderer="dataVerify" />
					<odin:gridEditColumn align="center" width="100" header="����"
						dataIndex="imprecordid" editor="text" edited="false"
						hidden="false" renderer="compRender" />
					<odin:gridEditColumn align="center" width="100" header="����"
			dataIndex="imprecordid" editor="text" edited="false" hidden="false"
			renderer="impcompRender" /> 
					<odin:gridEditColumn align="center" width="100" header="���ղ鿴"
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
	height="500" title="��ϢУ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="win1" width="560" height="392"
	title="����">
</odin:window>
<odin:window src="/blank.htm" id="deptWin" width="560" height="350"
	maximizable="false" title="����">
</odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350"
	maximizable="false" title="����">
</odin:window>
<odin:window src="/blank.htm" id="impExcelWin" width="560" height="350"
	maximizable="false" title="����">
</odin:window>
<%-- 
<odin:window src="/blank.htm" id="simpleExpWin2" width="560"
	height="350" maximizable="false" title="����" />
--%>
<odin:window src="/blank.htm" id="photoInfoWin" width="560" height="150"
	maximizable="false" title="����" />
<odin:window src="/blank.htm" id="impNewHzbORGWin" width="560"
	height="150" maximizable="false" title="����" />
<script type="text/javascript">
	function impTestOld() {//�����ϰ汾����
		$h.openWin('impNewHzbORGOLDWin', 'pages.dataverify.DataVerifyNewOldHZB', '�������������ݴ���', 850, 555, '', '<%=request.getContextPath()%>');
	}
	
	function impTestMake() {//�����׸İ汾����
		$h.openWin('impNewHzbORGMakeWin', 'pages.dataverify.DataVerifyNewMakeHZB', '�������������ݴ���', 850, 555, '', '<%=request.getContextPath()%>');
	}
	
	function impTest2111() {
//			$h.showWindowWithSrc('simpleExpWin11111', contextPath
//					+ "/pages/dataverify/DataVerify2.jsp?i=1",'���봰��',600,300);
////			$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew','���봰��',850,550,'1111@1111', '');
		$h.openWin('impNewHzbORGWin', 'pages.dataverify.DataVerifyNew', '�������������ݴ���', 850, 555, '', '<%=request.getContextPath()%>');
					
	}
	function TYGSimp() {
		$h.openWin('impNewHzbORGWin', 'pages.tygsImp.DataVerifyNew2', 'ͨ�ø�ʽ�������ݴ���', 850, 555, '', '<%=request.getContextPath()%>');
	}
	function WagesSimp(){
		$h.showWindowWithSrc('simpleExpWin2', contextPath
				+ "/pages/tygsImp/DataVerifyWages.jsp?i=1",'���봰��',530,210);
	}
	function Excelimp() {
		$h.openWin('ExcelImp', 'pages.customquery.ExcelImp', 'Excel�������ݴ���', 394, 420, '', '<%=request.getContextPath()%>');
	}
	function Mysqlimp() {
		$h.openWin('impNewMysqlWin', 'pages.customquery.MysqlImp', 'Mysql�����', 850, 555, '', '<%=request.getContextPath()%>');
	}
	function impTest2() {
///		var win = odin.ext.getCmp('simpleExpWin');
///		win.setTitle('���봰��');
		/// win.setSize(500,350); //���  �߶�
///		odin.showWindowWithSrc('simpleExpWin', contextPath
///				+ "/pages/dataverify/DataVerify2.jsp");
		$h.showWindowWithSrc('simpleExpWin11111', contextPath
				+ "/pages/dataverify/DataVerify2.jsp?i=1",'���봰��',600,300);
				

	}
	function impTest3() {
///	var win = odin.ext.getCmp('simpleExpWin2');
///	win.setTitle('���봰��');
	//win.setSize(500,350); //���  �߶�
	$h.showWindowWithSrc('simpleExpWin2', contextPath
				+ "/pages/dataverify/DataVerify3.jsp?i=1",'���봰��',530,250);	
	}
	
	//meiyong
	function impTest() {
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		/// win.setSize(500,350); //���  �߶�
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/dataverify/DataVerify.jsp");
	}
	
	
	function dataVerify(value, params, rs, rowIndex, colIndex, ds) {
		var impstutas = Ext.getCmp('MGrid').getStore().getAt(rowIndex).get('impstutas');//MGrid
		if(impstutas == 1){
			return "<a href=\"javascript:openDataVerifyWin('" + value + "')\">У��</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataCompare('" + value + "')\">�Ա�</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataCompWin('" + value + "')\">����</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript: dataBack('" + value + "')\">�ܾ�</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript:openLogWin('" + value + "')\">����</a>";
		}else if(impstutas == 3){
			return "";
		}else{
			return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+"&nbsp;&nbsp;&nbsp;&nbsp;"
			+"<a href=\"javascript:openLogWin('" + value + "')\">����</a>";
		}
	}
	/* function dataVerify(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript:openDataVerifyWin('" + value + "')\">У��</a>";
	} */
	
	function openDataVerifyWin(imprecordid){
		radow.doEvent('openDataVerifyWin',imprecordid);
	}
	
	function compRender(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript: dataCompWin('" + value + "')\">����</a>";
	}
	function dataCompWin(imprecordid){
		document.getElementById('imprecordid').value = imprecordid;
		radow.doEvent('dataComp',imprecordid);
	}
	
	function impcompRender(value, params, rs, rowIndex, colIndex, ds) {
		return "<a href=\"javascript: impdataCompWin('" + value + "')\">����</a>";
	}
	function impdataCompWin(imprecordid){
		radow.doEvent('impdataCompWin',imprecordid);
	}
	/**
	 * ������Ȩ��render����
	 */
	function detailRender(value, params, rs, rowIndex, colIndex, ds) {
		//var impstutas= rs.get("impstutas");
		return "<a href=\"javascript:openLogWin('" + value + "')\">����</a>";
	}
	function openLogWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid+","+"1";
///		doOpenPupWin(
///				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.RefreshOrgRecRej&imprecordid="
///						+ imprecordid +"&type=1", "��������", 600, 470, null);
		$h.openWin('simpleExpWin46','pages.dataverify.RefreshOrgRecRej&imprecordid='+ imprecordid +",1",'��������',600,445,imprecordid+",1",'<%=request.getContextPath()%>');
	}
	//meiyong
	function openDetailWin(imprecordid) {
		document.getElementById("radow_parent_data").value = imprecordid;
		doOpenPupWin(
				"/radowAction.do?method=doEvent&pageModel=pages.dataverify.ImpDetail&imprecordid="
						+ imprecordid, "��������", 600, 470, null);
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
		//ҳ�����
		//Ext.getCmp('MGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_MGrid'))[0]-4);
		//Ext.getCmp('MGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_MGrid'))[1]-2); 
		//document.getElementById("panel_content").style.width = Ext.getCmp('MGrid').getWidth()+17 + "px";
		//document.getElementById("toolDiv").style.width = Ext.getCmp('MGrid').getWidth()+17 + "px";
		//document.getElementById("tb1").style.height = Ext.getCmp('MGrid').getHeight()-20 + "px";//tb1
		
		
		
	});
	function setWidthHeight(){
		document.getElementById("table_qo_id").parentNode.parentNode.style.overflow='hidden';
		document.getElementById("table_qo_id").parentNode.style.width=document.body.clientWidth;
		var heightClient=window.parent.document.body.clientHeight;//���ڸ߶�
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//ϵͳ���Ϸ��˵��߶�
		//30
		var height_toolbar=document.getElementById("panel_content").offsetHeight;//grid�������߶�
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
	
	//ɾ����¼-lzy
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
			$h.getTopParent().frames["iframe_"+subWinId].Ext.MessageBox.confirm('��ʾ', '�Ƿ���е��룿', function(retp){
				if(retp=='yes'){
					$h.getTopParent().Ext.getCmp(subWinId).close(); 
					$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','У�鴰��',1050,598, '2@'+imprecordid+'','<%=request.getContextPath()%>');

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
	
	//�ܾ�
	function dataBack(imprecordid){
		
		$h2.confirm2('ϵͳ��ʾ','ȷ���Ƿ�ܾ������ݰ���',200,function(id){
        	if(id=='ok'){
        		Ext.Msg.buttonText.ok='ȷ��';
        		radow.doEvent("rejectBtn",imprecordid);
       		}else if(id=='cancel'){
        	}
        });
	}
	
	//�Ա�
	function dataCompare(imprecordid){
		$h.openWin('dataComparisonWin','pages.dataverify.DataComparison','У�鴰��',900,523, ''+imprecordid+'','<%=request.getContextPath()%>');
	}
	
		var $h2 = {
				'confirm' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='ȷ��';
					Ext.Msg.buttonText.cancel='ȡ��';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm2' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='ȷ�Ͼܾ�';
					Ext.Msg.buttonText.cancel='ȡ��';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm3' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='ȷ��';
					Ext.Msg.buttonText.cancel='ȡ������';
					Ext.Msg.show({
								title : title,
								msg : msg,
								width : width,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : fn
							});
				},
				'confirm4' : function(title, msg, width, fn) {
					Ext.Msg.buttonText.ok='��';
					Ext.Msg.buttonText.cancel='��';
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
	Ext.MessageBox.buttonText.yes="ȷ��";
	Ext.MessageBox.buttonText.no="ȡ��";
	Ext.MessageBox.buttonText.cancel="�鿴�ظ�����";
	Ext.MessageBox.show({
		title:'ϵͳ��ʾ',
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
	Ext.MessageBox.buttonText.yes="ȷ��";
	Ext.MessageBox.buttonText.no="ȡ��";
	Ext.MessageBox.buttonText.cancel="�鿴�ظ�����";
	Ext.MessageBox.show({
		title:'ϵͳ��ʾ',
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
	Ext.MessageBox.buttonText.yes="�ֶ�ƥ��";
	Ext.MessageBox.buttonText.no="ֱ�ӽ���";
	Ext.MessageBox.buttonText.cancel="ȡ��";
	Ext.MessageBox.show({
		title:'ϵͳ��ʾ',
		msg: '�Ƿ��ֶ�ƥ����ְ����Ա',
		buttons: Ext.MessageBox.YESNOCANCEL,
		fn: function(btn){
				if(btn=='yes'){
					$h.openWin('DataVerifyCheckPsn','pages.dataverify.DataVerifyCheckPsn','��Ա�ֶ�ƥ��',1200,750,imprecordid,'<%=request.getContextPath()%>');
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