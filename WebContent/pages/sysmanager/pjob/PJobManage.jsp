<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
<!--
function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
	return "<a href='#' onclick=\"runPJob('"+record.get('name')+"')\" title=开始执行该任务>开始执行</a> / \
	 <a href='#' onclick=\"monitorPJob('"+record.get('name')+"')\" title='查看该任务的最新一次运行进度，并进行实时监控'>运行监控</a> / \
	 <img src='"+contextPath+"/images/qinkong.gif' title='删除！' onclick=\"confirmDelete('dogriddelete','"+record.get('name')+"');\">";
}
function confirmDelete(eventName,id){
	odin.confirm("您确定要删除该定时任务吗？",function(v){
		if(v=="ok"){
			radow.doEvent(eventName,id);
		}
	});
}
//暂停或者重新运行某个JOB
function runPJob(name){
	radow.doEvent("runPJob",name);
}
function monitorPJob(name){
	var tname = encodeURIComponent(name);
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobMonitor&initParams="+tname,"并发任务监控",700,388);
}
function refresh(){
	window.location.reload();
}
//-->
</script>
<div id="toolDiv"></div>
<odin:toolBar property="floatToolBar" applyTo="toolDiv">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="刷新" id="refresh" icon="images/sx.gif" cls="x-btn-text-icon" handler="refresh"/>
	<odin:buttonForToolBar text="保存" id="save" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:groupBox property="s2" title="并发任务基本信息">
<table align="center">
	<tr>
		<odin:textEdit property="name" label="任务名称" title="为全英文，而且必须唯一，不能有重复。" required="true"></odin:textEdit>
		<odin:textEdit colspan="4" property="title" size="70" label="任务标题" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:select property="pjobtype" label="任务类别" value="1"></odin:select>
		<odin:textEdit colspan="4" property="jobcontent" size="115" label="任务执行内容" title="当类别为存储过程时这里为过程名，否则为Java定时任务类名" required="true"></odin:textEdit>
		<odin:hidden property="id"/>
	</tr>
	<tr>
		<odin:numberEdit property="pcount" label="并发个数" value="5"></odin:numberEdit>
		<odin:textEdit colspan="4" property="param" size="115" label="执行参数" title="java定时任务执行时的参数"></odin:textEdit>
	</tr>
</table>
<input type="hidden" name="id" id="id" />
</odin:groupBox>
<odin:groupBox property="s3" title="定时策略">
<table width="80%" align="center">
	<tr>
		<odin:textEdit property="everydaytime" label="每天几点几分几秒" value=""></odin:textEdit>
		<td colspan="4" style="font-size: 12px;color: red;">例如，该时间格式为14:30:00</td>
	</tr>
	<tr>
		<odin:select property="weekDay" label="每星期" codeType="WEEKDAY" width="80"></odin:select>
		<odin:textEdit property="wdaytime" label="几点几分几秒" value=""></odin:textEdit>
	</tr>
	<tr>
		<odin:select property="lastDay" label="每月" codeType="LASTDAY" width="80"></odin:select>
		<odin:textEdit property="ldaytime" label="几点几分几秒" value=""></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="expression" label="复杂表达式策略" colspan="6" width="482"></odin:textEdit>
	</tr>
</table>
</odin:groupBox>

<odin:editgrid property="jobgrid" sm="row" bbarId="pageToolBar" hasRightMenu="false" isFirstLoadData="false" url="/" width="770" title="" height="200" >
<odin:gridJsonDataModel  id="id">
  <odin:gridDataCol name="id"/>
  <odin:gridDataCol name="name"/>
  <odin:gridDataCol name="title"/>
  <odin:gridDataCol name="jobtype"/>
  <odin:gridDataCol name="jobcontent"/>
  <odin:gridDataCol name="pcount"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="param"/>
  <odin:gridDataCol name="createdate" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridEditColumn 	header="任务标题" width="80" dataIndex="title" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="任务名称" align="center" width="40" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="当前类别" align="center" edited="false" dataIndex="jobtype" editor="select" codeType="PJOBTYPE"/>
  <odin:gridEditColumn 	header="执行内容" align="center" dataIndex="jobcontent" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="并发个数"  dataIndex="pcount" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="执行参数"  dataIndex="param" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="创建时间"  dataIndex="createdate" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="操作" align="center" dataIndex="id" editor="text" edited="false" width="80" isLast="true" renderer="commGridColDelete"/>
</odin:gridColumnModel>		
</odin:editgrid>