<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>
<%
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript">
	var g_contextpath = '<%= request.getContextPath() %>';
</script>
<style>
.x-form-item2 tr td .x-form-item{
margin-bottom: 0px !important;
}
/* #gp2{
display:none;
} */
</style>
<script type="text/javascript">
</script>
<%--<odin:toolBar property="btnToolBar1">
	<odin:fill/>
	<odin:buttonForToolBar text="上传附件" handler="updateTrain" icon="images/icon/exp.png" />
	 <odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除班次" handler="deleteTrain" icon="image/icon021a2.gif" /> 
</odin:toolBar>--%>
<odin:toolBar property="btnToolBar1">
	<odin:fill />
	<odin:buttonForToolBar text="新增班次" handler="addTrain"
		icon="image/icon021a2.gif" id="p029" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改班次" handler="updateTrain"
		icon="image/icon021a2.gif" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除班次" handler="deleteTrain"
		icon="image/delete2.png" />
</odin:toolBar>
<odin:toolBar property="btnToolBar2">
	<odin:fill />
	<odin:buttonForToolBar text="删除学员" handler="deletePerson"
		icon="image/delete2.png" id="infoDelete" isLast="true" />
</odin:toolBar>
<odin:toolBar property="bbarid">
</odin:toolBar>
<div id="" >
	<div style="width: 101.12%;height:100%;margin-top: 3px;">
	  <div id="div1" style="width: 44%;float: left;">
	  	<odin:groupBox property="gp" title="培训班次名单">
	  	<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
			<tr>
				<odin:select2 property="nd" label="年度：" maxlength="4" onchange="changeData()" multiSelect="false" width="70"></odin:select2>
				<!-- <td>&nbsp;</td> -->
				<odin:textEdit property="trainname" label="班次名称："  width="150" />
				
<%-- 				<odin:select2 property="trainname" label="班次名称：" multiSelect="false" width="150"></odin:select2>
 --%>				<odin:select2 property="trainclass" label="培训班类别：" multiSelect="false" codeType="ZB29" width="80" value=""></odin:select2>
				<td align="right" style="width: 10%;"><odin:button text="查询" property="trainQuery" handler="doTrainQuery"></odin:button></td>
			</tr>
		</table> 
		<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
			<tr>
				<odin:select2 property="mainunit" label="培训主办单位："  width="130" codeType="TRANORG" ></odin:select2>
				<odin:select2 property="joinnum" label="参训人数：" canOutSelectList="false" data="['0,20','0至20人'],['20,50','20至50人'],['50,100','50至100人'],['$','100人以上']" width="90" ></odin:select2>
				<odin:select2 property="trainunitclass" label="培训机构级别：" multiSelect="false" codeType="ZB27" width="80"></odin:select2>
			</tr>
		</table> 
	  	 <odin:editgrid2 width="100%" property="grid1" hasRightMenu="false"  autoFill="true" topBarId="btnToolBar1" bbarId="pageToolBar" pageSize="20" url="/">
			<odin:gridJsonDataModel>
				 <odin:gridDataCol name="pcheck" />
				<odin:gridDataCol name="trainid" />
				<odin:gridDataCol name="g11020"/>
				<odin:gridDataCol name="a1131" />
				<odin:gridDataCol name="a1101"/>
				<odin:gridDataCol name="g11024"/>
				<odin:gridDataCol name="g11023"/>
				<odin:gridDataCol name="cz"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="trainid" width="10" editor="text" header="主键" hidden="true" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="g11020" width="120" header="培训年度" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1131" width="400" header="班次名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1101" width="150" header="类别" editor="select" codeType="ZB29" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="g11024" width="100" header="学制（天）" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="cz" width="100" header="操作" editor="text" edited="false" align="center" renderer="detailedcz" isLast="true"/>			
			</odin:gridColumnModel>
		  </odin:editgrid2>
		</odin:groupBox>
		 <%-- <odin:groupBox property="gp2" title="领导干部上讲台名单">
			<odin:editgrid2 property="grid3" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true" pageSize="9999" bbarId="pageToolBar"  url="/">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="pcheck" />
					<odin:gridDataCol name="leacerid" />
					<odin:gridDataCol name="trainid"/>
					<odin:gridDataCol name="a0184"/>
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0104"/>
					<odin:gridDataCol name="a0192a"/>
					<odin:gridDataCol name="g11027"/>
					<odin:gridDataCol name="a1108"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
					<odin:gridEditColumn2 dataIndex="leacerid" width="110" hidden="true" editor="text" header="leader主键" edited="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="trainid" width="110" hidden="true" editor="text" header="班次主键" edited="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0184" width="10" hidden="true" editor="text" header="身份证号" edited="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center" renderer="trainData"/>
					<odin:gridEditColumn2 dataIndex="a0104" width="40" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0192a" width="250" header="现工作单位" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="g11027" width="100" header="现任职务层次" editor="select" codeType="TrainZB09" edited="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a1108" width="60" header="获得学时数" editor="text" edited="false" align="center" renderer="datacz" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
			</odin:groupBox>  --%>
	  </div>
	  <div id="peopleInfo" style="width:54%;float: left; margin-left: 8px;">
		      <odin:groupBox property="gp1" title="培训人员名单">
		      	<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
					<tr>
						<td><div style="width: 5px"></div></td>
						<odin:textEdit property="seachName" label="姓名："  width="100" />
<%-- 						<odin:select2 property="pg11027" label="现任职务层次：" onchange="loadStat()" multiSelect="false" canOutSelectList="false" data="['1','处级及处级以上'],['2','处级以下']" width="150" ></odin:select2>
 --%>						
 						<odin:textEdit property="pga0184" label="身份证号码："   width="150"/>
						<td align="right" style="width: 41%"><odin:button text="查询" property="personQuery" handler="PersonQuery"></odin:button></td>
					</tr>
				</table> 
		          <odin:editgrid2 property="grid2" hasRightMenu="false" topBarId="btnToolBar2" autoFill="true"  bbarId="pageToolBar" pageSize="20"  url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="pcheck" />
						<odin:gridDataCol name="personnelid" />
						<odin:gridDataCol name="trainid"/>
						<odin:gridDataCol name="a0184"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0104"/>
						<%-- <odin:gridDataCol name="g11027"/> --%>
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="g11021"/>
						<%-- <odin:gridDataCol name="g11028"/>
						<odin:gridDataCol name="g11029"/>--%>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
						 <odin:gridEditColumn2 locked="true" header="selectall" width="30" menuDisabled="true" 
							editor="checkbox" dataIndex="pcheck" edited="true"
							hideable="false" gridName="gridcq"/>
						<odin:gridEditColumn2 dataIndex="personnelid" width="10" hidden="true" editor="text" header="人员主键" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="trainid" width="10" hidden="true" editor="text" header="班次主键" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0104" width="30" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0184" width="100" header="身份证号码 " editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="220" header="现工作单位 " editor="text" edited="false" align="center"/>
						<%-- <odin:gridEditColumn2 dataIndex="g11027" width="100" header="现任职务层次" editor="select" codeType="TrainZB09" edited="false" align="center"/> --%>
						<odin:gridEditColumn2 dataIndex="g11021" width="50" header="获得学时数" editor="text" edited="false" align="center" renderer="datacz"  isLast="true"/>
						<%-- <odin:gridEditColumn2 dataIndex="g11028" width="80" header="是否班组长 " editor="select" codeType="XZ09" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="g11029" width="90" header="是否优秀学员" editor="select" codeType="XZ09" edited="false" align="center"  isLast="true"/> --%>
			
					</odin:gridColumnModel>
				  </odin:editgrid2>
				</odin:groupBox>
	  </div>  
	</div>
</div>
<odin:hidden property="train_id"/>
<odin:hidden property="personnelid"/>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('grid2');
	grid2.setHeight(viewSize.height/1.167);
	/* grid2.setWidth(viewSize.width/1.845); */
	var grid1 = Ext.getCmp('grid1');
	grid1.setWidth(viewSize.width/2.3);
	grid1.setHeight(viewSize.height/1.224);
	/* var grid3 = Ext.getCmp('grid3');
	grid3.setHeight(viewSize.height/2.643);
	grid3.setWidth(viewSize.width/2.32); */
	grid1.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('train_id').value = rc.data.trainid;
		/* var a1131 = rc.data.a1131;
		document.getElementById('trainName').innerText = a1131; */
		
	});
	grid1.on('rowdblclick',function(gridobj,index,e){
		/* document.getElementById('seachName').value='';
		document.getElementById('pg11027').value='';
		document.getElementById('pg11027_combo').value='';
		
		//$(".ux-lovcombo-icon-unchecked").("ux-lovcombo-icon-unchecked", " hover_s fl fv lv ");
		try{
			//$(".ux-lovcombo-icon-unchecked").("ux-lovcombo-icon-unchecked", " hover_s fl fv lv ");
			$(".ux-lovcombo-icon-checked").attr("ux-lovcombo-icon-checked", "ux-lovcombo-icon-unchecked");
			//document.getElementsByClassName('ux-lovcombo-icon-checked').setAttribute("ux-lovcombo-icon-checked","ux-lovcombo-icon-unchecked");
		}catch(error){
			
		} */
		/* radow.doEvent("grid3.dogridquery"); */
		radow.doEvent("QueryData");
	});
	grid2.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		$h.openPageModeWin('addPerson','pages.train.TrainPerson','修改',900,580,rc.data.personnelid+"@"+rc.data.trainid,g_contextpath);
	});
	/* grid3.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$h.openPageModeWin('loadadd','pages.train.HandleLeader','修改',700,300,rc.data.leacerid,g_contextpath);
	}); */
})
//新增班次
function addTrain(){
	$h.openPageModeWin('addTrain','pages.train.HandleTrain','新增班次',651,350,'',g_contextpath);
}
//修改班次
function updateTrain(){
	var trainid=document.getElementById('train_id').value;
	if(trainid==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	 $h.openPageModeWin('updateTrain', 'pages.train.HandleTrain', '查看(修改)班级信息', 651, 350, trainid, g_contextpath);
	//$h.openPageModeWin('impFuJian','pages.train.ImpFuJian','上传附件',500,300,{trainid:trainid},g_contextpath);
}
function deleteTrain(){
	var trainid=document.getElementById('train_id').value;
	if(trainid==''||trainid==null){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	 odin.confirm("您确定要删除该班次数据吗？", function (btn) {
         if (btn == "ok") {
        	 radow.doEvent('deleteTrain',trainid);
        		document.getElementById('train_id').value='';
        		document.getElementById('trainName').innerText='';
         } else {
             return;
         }
     });
	
}
function deleteCallBack(){
	Ext.getCmp('grid1').getStore().reload();
	$h.alert('系统提示','刪除成功');
}
function addPerson(){
	var trainid = document.getElementById('train_id').value;
	if(trainid==''||trainid==null){
		$h.alert('系统提示','请选择班次！');
		return;
	}
	var trainid = document.getElementById('train_id').value;
	var param =''+"@"+trainid;
	$h.openPageModeWin('addPerson','pages.train.TrainPerson','新增人员',900,430,param,g_contextpath);
}
function deletePerson(){
	
	 //var p0400 = document.getElementById('p0400').value;
     odin.confirm("您确定要删除该学员数据吗？", function (btn) {
         if (btn == "ok") {
             radow.doEvent("deletePerson");
         } else {
             return;
         }
     });

     //document.getElementById('p0400').value = '';
	//radow.doEvent("deletePerson");
}
function leaderQuery(trainid){
	$h.openPageModeWin('trainLeader','pages.train.TrainLeader','领导干部上讲台情况',900,500,trainid,g_contextpath);
	//$h.openWin('trainLeader','pages.train.TrainLeader','领导干部上讲台情况',900,500,trainid,g_contextpath,null,{maximizable:false,resizable:false});
}

function detailedcz(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"detailed('"+record.get("trainid")+"');\">"+"查询"+"</a></font>";
}

function detailed(trainid){
	radow.doEvent("openTrain",trainid);
	//$h.openPageModeWin('TrainWin','pages.train.HandleTrain','班次信息',800,400,trainid,g_contextpath);
}

function openTrain1(trainid){
	$h.openPageModeWin('TrainWin','pages.train.HandleTrain','班次信息',1300,400,trainid,g_contextpath);
}

function openTrain2(trainid){
	$h.openPageModeWin('TrainWin','pages.train.HandleTrain','班次信息',651,300,trainid,g_contextpath);
}

function deleteCallBack_p(){
	Ext.getCmp('grid2').getStore().reload();
}
function changeData(){
	/* radow.doEvent("changeNd"); */
}
function doTrainQuery(){
	radow.doEvent("grid1.dogridquery");
}
function PersonQuery(){
	radow.doEvent("grid2.dogridquery");
}
function datacz(value, params, record, rowIndex, colIndex, ds){
	if(value!=null){
		if(value.substr(0, 1)=="."){
			return "0"+value;
		}else{
			return value;
		}
	}else{
		return "0";
	}
	
}

function trainData(value, params, record, rowIndex, colIndex, ds){
	record.data.trainid;
	return "<font color=blue><a style='cursor:pointer;' onclick=\"TrainQuery('"+record.get("a0101")+","+record.get("a0184")+"');\">"+value+"</a></font>";
}
function TrainQuery(param){
	param = param+","+document.getElementById("nd").value;
	$h.openPageModeWin('trainWin','pages.train.TrainMessage','培训详情',900,700,param,g_contextpath);
}
</script>
