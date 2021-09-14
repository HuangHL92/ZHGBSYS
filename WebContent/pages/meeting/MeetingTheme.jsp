<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
function outFile(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+record.get("meetingid")+"');\">导出上会材料</a></font>";
}

</script>
<div id="groupTreePanel"></div>
<odin:hidden property="meeting_id"/>
<odin:hidden property="meeting_name"/>
<odin:hidden property="userid"/>
<odin:editgrid2 property="meetingGrid" hasRightMenu="false" title="会议信息" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="mcheck" />
		<odin:gridDataCol name="meetingid" />
		<odin:gridDataCol name="meetingname"/>
		<odin:gridDataCol name="meetingtype"/>
		<odin:gridDataCol name="time"/>
		<odin:gridDataCol name="userid"/>
		<odin:gridDataCol name="username"/>
		<odin:gridDataCol name="material"/>
		<%-- <odin:gridDataCol name="meetingThemefile"/> --%>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 locked="true" header="selectall" width="30" menuDisabled="true" 
							editor="checkbox" dataIndex="mcheck" edited="true"
							hideable="false" gridName="meetingGrid"/>
		<odin:gridEditColumn2 dataIndex="meetingid" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="meetingname" width="190" header="会议名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="meetingtype" width="140" header="会议类型" editor="select" codeType="meettype" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="time" width="140" header="会议日期" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="username" width="100" header="创建人" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="material" width="30" header="考察材料" editor="text" edited="false" align="center" isLast="true" renderer="updatematerial"/>
		<%-- <odin:gridEditColumn2 dataIndex="meetingThemefile" width="140" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="outFile"/> --%>
	</odin:gridColumnModel>
</odin:editgrid2>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>会议议题管理</h3>" />
	<odin:fill />
	
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="新增会议" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改会议" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除会议" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="增加议题" icon="image/zjbt.png" id="publish"  handler="publish"/>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="批量任免表导入" icon="image/icon021a2.gif" handler="impLrms" id="impLrms" />
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="导出上会PAD数据" icon="images/icon/table.gif" id="expSHPad"  handler="expSHPad" isLast="true"/>
	<%--<odin:separator></odin:separator>
	<odin:buttonForToolBar text="会议授权" icon="images/search.gif" id="meetingPower" handler="meetingPower" isLast="true"/> --%>
</odin:toolBar>
<odin:window src="/blank.htm" id="importLrmWinssh" width="450" height="210"
	title="批量任免表导入" modal="true" />
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var meetingGrid = Ext.getCmp('meetingGrid');
	meetingGrid.setHeight(viewSize.height-42);
	meetingGrid.setWidth(viewSize.width);
	var btnToolBar = Ext.getCmp('btnToolBar');
	btnToolBar.setWidth(viewSize.width);
	
	meetingGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('meeting_id').value = rc.data.meetingid;
		document.getElementById('meeting_name').value = rc.data.meetingname;
		document.getElementById('userid').value = rc.data.userid;
	});
	meetingGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('meeting_id').value = rc.data.meetingid;
		publish();
	});
	 /*
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		
		$h.openPageModeWin('qcjs','pages.xbrm.QCJS','全程纪实',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
	}); */
	
});

/*function meetingPower(){
	var meeting_id = document.getElementById('meeting_id').value;
	if(meeting_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$h.openPageModeWin('meetingPower','pages.meeting.MeetingPower','会议授权',530,550,{meeting_id:meeting_id},g_contextpath);}*/
	function updatematerial(value, params, record, rowIndex, colIndex, ds) {
		var meetingid=record.get("meetingid");
		return "<a href=\"javascript:sq(&quot;"+meetingid+"&quot;)\">考察材料</a>";
	}

	function sq(meetingid){ 
		//radow.doEvent('sq',meeting_id);	
		$h.openWin('publishWin','pages.meeting.CadresPlanning','议题材料信息',760, 560,meetingid,g_contextpath);
		
	}
function openTPRmb(){
	$h.openPageModeWin('openTPRmb','pages.mntpsj.PZZDGWTJ','人员信息',1300,800,null,g_contextpath);
}
	
function impLrms(){
	var contextPath = '<%=request.getContextPath()%>';
	odin.showWindowWithSrc("importLrmWinssh",contextPath+"/pages/publicServantManage/ImportLrmssh.jsp?businessClass=com.picCut.servlet.SaveLrmFile");
}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.meeting.MeetingAdd','新增会议',250,315,{meeting_id:'',userid:''},g_contextpath);
}
function infoUpdate(){
	var meeting_id = document.getElementById('meeting_id').value;
	var userid = document.getElementById('userid').value;
	if(meeting_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	
	$h.openPageModeWin('infoUpdate','pages.meeting.MeetingAdd','修改会议',250,315,{meeting_id:meeting_id,userid:userid},g_contextpath);
}
function publish(){
	var meeting_id = document.getElementById('meeting_id').value;
	if(meeting_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$h.openWin('publishWin','pages.meeting.Publish','议题信息',1400,1200,meeting_id,g_contextpath,null,{maximizable:false,resizable:false},true);
}

function expSHPad(){
	var meeting_id = document.getElementById('meeting_id').value;
	if(meeting_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	radow.doEvent("importSHPadBtn", "zip");
}

function infoDelete(){
	var meeting_id = document.getElementById('meeting_id').value;
	var meeting_name = document.getElementById('meeting_name').value;
	if(meeting_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$h.confirm("系统提示：",'删除会议记录将会删除该会议下所有的发布记录以及附件，确定删除会议议题：'+meeting_name+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',meeting_id);
		}else{
			return false;
		}		
	});
}

function outfile(meetingid){
	radow.doEvent('out',meetingid);
}
function outFileZip(){
	var url = g_contextpath+'/PublishFileServlet?method=meetingtheme';
	window.location.href=url;
}
</script>


