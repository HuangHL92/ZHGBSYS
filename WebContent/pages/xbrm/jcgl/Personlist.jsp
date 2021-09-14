<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<%
RMHJ r = new RMHJ();
request.setAttribute("RMHJ", r.rmhj);
request.setAttribute("RYFL", r.ryfl);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
body{
margin: 1px;overflow: auto;
font-family:'宋体',Simsun;
word-break:break-all;
}

</style>
<script type="text/javascript">
function doAddPerson(){
	var checkregid = document.getElementById('checkregid').value;
	if(checkregid==''){
		$h.alert('系统提示','无批次信息！');
		return;
	}
	$h.openWin('findById321','pages.xbrm.jcgl.SelectPByName','按姓名/身份证查询 ',820,520,checkregid,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'任免人员', checkregid :checkregid});
}

function doAddPerson2(){
	var checkregid = document.getElementById('checkregid').value;
	if(checkregid==''){
		$h.alert('系统提示','无批次信息！');
		return;
	}
	$h.openWin('findById321','pages.xbrm.jcgl.SelectPByName','按姓名/身份证查询 ',820,520,checkregid,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'任免人员', checkregid :checkregid, cyk : 'cyk'});
}
function infoDelete(){
	radow.doEvent('infoDelete');
}
function getCheckList2(){
	
}
function getCheckList(){
	
}
function editAddPerson(){
	document.getElementById('crp008').value="";
	document.getElementById('crp001').value="";
	document.getElementById('crp006').value="";
	document.getElementById('crp003').value="";
	document.getElementById('crp005').value="";
	document.getElementById('crp004').value="";
	document.getElementById('crp001').value="";
	document.getElementById('crp000').value="";
	document.getElementById('a3600').value="";
	document.getElementById('a0000').value="";
	document.getElementById('sortid1').value="";
	document.getElementById('sortid2').value="";
	var store = Ext.getCmp("familyGrid").store;
	store.removeAll();
}
function editSavePerson(){
	radow.doEvent('saveInfo');
}
function editAddfamily(){
	var store = Ext.getCmp("familyGrid").store;
	var jsonrecord = {a0000:'',crp000:'',a3600:'',crp001:'',crp002:'',crp003:'',crp004:'',crp005:'',crp006:'',crp007:'2',crp008:'',checkregid:''};
	store.add(new Ext.data.Record(jsonrecord));
}
function deletefamily(){
	var store = Ext.getCmp("familyGrid").store;
	var record = Ext.getCmp("familyGrid").getSelectionModel().getSelected();
	store.remove(record);
	/* for(var i=0;i<store.getCount();i++){
		store.getAt(i).set("del",i);
	} */
}

function searchOnePerson(){
	radow.doEvent('gridcq.dogridquery');
}

</script>

<odin:toolBar property="topbar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="下载" id="downf" handler="downfile" icon="images/icon_photodesk.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="选择人员" id="doAddPerson" handler="doAddPerson2" icon="image/icon021a2.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator isLast="true"/>
</odin:toolBar>
<odin:toolBar property="tbar1" applyTo="div_tbar">
	<odin:fill></odin:fill>
	<odin:separator/>
	<odin:buttonForToolBar text="新增人员" id="editAddPerson" handler="editAddPerson" icon="images/add.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="保存" handler="editSavePerson" id="editSavePerson" icon="images/save.gif" />
	<odin:separator isLast="true"/>
</odin:toolBar>

<odin:toolBar property="familyTBar" >
	<odin:buttonForToolBar text="<h3>家庭成员信息</h3>"></odin:buttonForToolBar>
	<odin:fill></odin:fill>
	<odin:separator/>
	<odin:buttonForToolBar text="新增" id="editAddfamily" handler="editAddfamily" icon="images/add.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="删除" handler="deletefamily" id="deletefamily" icon="images/icon/delete.gif" />
	<odin:separator isLast="true"/>
</odin:toolBar>

<div id="tabs" >
	<div style="width: 100%; ">
		<div style="width: 400px;float: left;" id="gridDiv">
			<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-right: 1px solid rgb(153,187,232);">
				<tr><td colspan="6" height="3px"></td></tr>
				<tr>
					<odin:textEdit property="crp001_" width="120" label="姓名" ></odin:textEdit>
					<td>&nbsp;</td>
					<odin:textEdit property="crp006_" width="130" label="身份证" ></odin:textEdit>
					<td width="100px" align="center" valign="top"> <odin:button text="&nbsp;搜索&nbsp;" handler="searchOnePerson" property="searchOnePerson" /> </td>
				</tr>
			</table>
			<odin:editgrid2 property="gridcq"  topBarId="topbar" load="selectRow" bbarId="pageToolBar"
				isFirstLoadData="false" width="400" pageSize="50" clicksToEdit="false" autoFill="false" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="pcheck" /> 
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="crp000" />
					<odin:gridDataCol name="a3600" />
					<odin:gridDataCol name="crp001" />
					<odin:gridDataCol name="crp002" />
					<odin:gridDataCol name="crp006" />
					<odin:gridDataCol name="crp008" />
					<odin:gridDataCol name="havefine" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 locked="true" header="selectall" width="40"
								editor="checkbox" dataIndex="pcheck" edited="true"
								hideable="false" gridName="gridcq" 
								checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" menuDisabled="true" /> 
					<%-- 
					<odin:gridEditColumn2 dataIndex="crp002" header="称谓" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					 --%>
					<odin:gridEditColumn2 dataIndex="crp001" header="姓名" width="90" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 dataIndex="crp006" header="身份证" width="220" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					
					<odin:gridEditColumn2 dataIndex="crp008" header="人员id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="a0000" header="人员id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="crp000" header="id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="a3600" header="姓名"  menuDisabled="true" edited="false" editor="text" hidden="true" isLast="true" />
					
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid2>
	  	</div>
		<div id="peopleInfo" style="float: left; overflow-y: auto !important;">
			<div id="div_tbar"></div>
			<odin:groupBox title="本人信息">
	  			<table style="width: 100%;">
	  				<tr>
	  					<odin:textEdit property="crp001" label="姓名" required="true" maxlength="36"></odin:textEdit>
	  					<odin:textEdit property="crp006" label="身份证号码" required="true" maxlength="18"></odin:textEdit>
	  					
	  				</tr>
	  				<tr>
	  					<odin:select2 property="crp009" label="性别" maxlength="8" data="['男','男'],['女','女']"></odin:select2>
	  					<%-- <odin:textEdit property="crp005" label="政治面貌" ></odin:textEdit> --%>
	  					<odin:select2 property="crp005" label="政治面貌" codeType="GB4762" required="false" ></odin:select2>
	  				</tr>
	  				<tr>
	  					<odin:textEdit property="crp012" label="人员类别" maxlength="25"></odin:textEdit>
	  					<odin:textEdit property="crp011" label="职级" maxlength="25"></odin:textEdit>
	  				</tr>
	  				<tr>
	  					<odin:dateEdit property="crp018" label="查核日期"  format="Ymd" maxlength="8"></odin:dateEdit>
	  					<td colspan="2"></td>
	  				</tr>
	  				
	  				<tr>
	  					<odin:textarea property="crp004" label="职务" cols="100" colspan="4" maxlength="36"></odin:textarea>
	  				</tr>
	  			</table>
	  	  	</odin:groupBox>
	  	   	<%-- <odin:groupBox title="家庭成员信息"> --%>
	  	   	<odin:gridSelectColJs2 name="crp002"  selectData="['配偶','配偶'],['子女','子女'],['子女的配偶','子女的配偶']"></odin:gridSelectColJs2>
	  	  	<odin:gridSelectColJs2 name="crp009"  selectData="['男','男'],['女','女']"></odin:gridSelectColJs2>
	  	  		<odin:editgrid2 property="familyGrid" topBarId="familyTBar" clicksToEdit="true" >
		  	  		<odin:gridJsonDataModel>
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="crp000" />
						<odin:gridDataCol name="a3600" />
						<odin:gridDataCol name="crp001" />
						<odin:gridDataCol name="crp002" />
						<odin:gridDataCol name="crp003" />
						<odin:gridDataCol name="crp004" />
						<odin:gridDataCol name="crp005" />
						<odin:gridDataCol name="crp006" />
						<odin:gridDataCol name="crp007" />
						<odin:gridDataCol name="crp008" />
						<odin:gridDataCol name="crp009" />
						<odin:gridDataCol name="crp010" />
						<odin:gridDataCol name="checkregid" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="crp002" header="称谓" width="70" sortable="false" menuDisabled="true" edited="true" editor="select" selectData="['配偶','配偶'],['子女','子女'],['子女的配偶','子女的配偶']" align="center" />
						<odin:gridEditColumn2 dataIndex="crp001" header="姓名" width="70" sortable="false" menuDisabled="true" edited="true" editor="text" align="center" />
						<odin:gridEditColumn2 dataIndex="crp009" header="性别" width="70" sortable="false" menuDisabled="true" edited="true" editor="select" selectData="['男','男'],['女','女']" align="center" />
						<odin:gridEditColumn2 dataIndex="crp006" header="身份证" width="170" sortable="false" menuDisabled="true" edited="true" editor="text" align="center" />
						<odin:gridEditColumn2 dataIndex="crp005" header="政治面貌" width="70" sortable="false" menuDisabled="true" edited="true" editor="select" codeType="GB4762" align="center" />
						<odin:gridEditColumn2 dataIndex="crp004" header="职务" width="180" sortable="false" menuDisabled="true" edited="true" editor="text" align="center" />
						
						<odin:gridEditColumn2 dataIndex="crp007" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="crp008" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
						
						
						<odin:gridEditColumn2 dataIndex="crp003" header="出生日期" width="70" sortable="false" menuDisabled="true" edited="true" editor="text" hidden="true" align="center" />
						<odin:gridEditColumn2 dataIndex="crp010" header="结果" menuDisabled="true" edited="false" editor="text" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="a0000" header="人员id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="crp000" header="id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="a3600" header="家庭成员id"  menuDisabled="true" edited="false" editor="text" hidden="true" isLast="true" />
						
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
	  	  		</odin:editgrid2>
	  	  	<%-- </odin:groupBox> --%>
		</div>  
	</div>
</div>

<odin:hidden property="checkregid"/>
<odin:hidden property="crp000"/>
<odin:hidden property="crp008"/>
<odin:hidden property="a0000"/>
<odin:hidden property="crp003"/>
<odin:hidden property="crp010"/>

<%-- <odin:hidden property="crp011"/>
<odin:hidden property="crp012"/> --%>
<odin:hidden property="crp013"/>
<odin:hidden property="crp014"/>
<odin:hidden property="crp015"/>
<odin:hidden property="crp016"/>
<odin:hidden property="crp017"/>

<odin:hidden property="a3600"/>
<odin:hidden property="sortid1"/>
<odin:hidden property="sortid2"/>
<odin:hidden property="crp002" value="报告人"/>
<odin:hidden property="crp007" value="1"/>

<odin:hidden property="ckfileid"/>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script type="text/javascript">
function downfile(){
	var id = document.getElementById('ckfileid').value;
	document.getElementById('downloadframe').src="<%=request.getContextPath()%>/PublishFileServlet?method=downloadFile&checkregfileid="+encodeURI(encodeURI(id));
}

function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.crp000==$('#crp000').val()){
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,i,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	} else {
		radow.doEvent('rowPclick','');
	}
}

function setWidthHeight(){
	//document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	//document.getElementById("gridDiv").parentNode.style.width=width+'px';
	//var height_top=document.getElementById("topbar").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	//document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("gridcq").setHeight(height-37);
	//Ext.getCmp("grid1").setWidth(width);
}
Ext.onReady(function() {
	
	document.getElementById('checkregid').value = parentParam.checkregid;
	window.onresize=setWidthHeight;
	setWidthHeight();
	var gridcq = Ext.getCmp('gridcq');
	gridcq.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		radow.doEvent('rowPclick',rc.data.crp000);
	});
	
});
</script>
