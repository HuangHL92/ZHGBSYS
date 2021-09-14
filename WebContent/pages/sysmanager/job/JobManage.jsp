<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = f.format(new Date());
	String time = now.substring(11);
%>
<script type="text/javascript">
<!--
function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
	return "<a href='#' onclick=\"pauseJob(1,'"+value+"')\" title=暂时停止该JOB运行>暂停</a> / <a href='#' onclick=\"pauseJob(2,'"+value+"')\" title=重新运行改JOB>重启</a> / <img src='"+contextPath+"/images/qinkong.gif' title='删除！' onclick=\"confirmDelete('dogriddelete','"+value+"');\">";
}
function confirmDelete(eventName,id){
	odin.confirm("您确定要删除该定时任务吗？请确保其已经正常结束再删。",function(v){
		if(v=="ok"){
			radow.doEvent(eventName,id);
		}
	});
}
//暂停或者重新运行某个JOB
function pauseJob(type,id){
	document.getElementById("id").value = id;
	radow.doEvent("pauseOrRestartJob",type);
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
<odin:groupBox property="s2" title="定时任务基本信息">
<table align="center">
	<tr>
		<odin:textEdit property="name" label="任务名称" title="为全英文，而且必须唯一，不能有重复。" required="true"></odin:textEdit>
		<odin:textEdit colspan="4" property="title" size="70" label="任务标题" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="startdate" label="开始执行时间" value="<%=now%>"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="classname" size="115" label="任务具体实现类" required="true"></odin:textEdit>
		<odin:hidden property="id"/>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="param1" size="115" label="执行参数"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="description" size="115" label="备注"></odin:textEdit>
	</tr>
</table>
<input type="hidden" name="id" id="id" />
</odin:groupBox>
<odin:groupBox property="s3" title="定时策略">
<table width="80%" align="center">
	<tr>
		<odin:select property="intervalType" label="间隔策略" codeType="INTERVALTYPE"></odin:select>
		<odin:numberEdit property="intervalCount" label="间隔长度" value="0" width="60" title="除立刻执行（此时为0）外，其它都不允许为0"></odin:numberEdit>
		<odin:numberEdit property="execount" label="执行次数" value="0" width="60" title="大于0时才会起作用，否则为永远执行下去"></odin:numberEdit>
	</tr>
	<tr>
		<odin:textEdit property="everydaytime" label="每天几点几分几秒" value=""></odin:textEdit>
		<td colspan="4" style="font-size: 12px;color: red;">例如，该时间格式为<%=time%></td>
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

<odin:gridSelectColJs name="status" codeType="JOBSTATUS"></odin:gridSelectColJs>
<odin:editgrid property="jobgrid" sm="row" bbarId="pageToolBar" isFirstLoadData="false" url="/" width="770" title="" height="200" >
<odin:gridJsonDataModel  id="id">
  <odin:gridDataCol name="id"/>
  <odin:gridDataCol name="name"/>
  <odin:gridDataCol name="title"/>
  <odin:gridDataCol name="classname"/>
  <odin:gridDataCol name="startdate"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="description" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridEditColumn 	header="任务标题" width="80" dataIndex="title" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="任务名称" align="center" width="40" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="当前状态" align="center" edited="false" dataIndex="status" editor="select" codeType="JOBSTATUS"/>
  <odin:gridEditColumn 	header="任务具体实现类" align="center" dataIndex="classname" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="备注"  dataIndex="description" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="操作" align="center" dataIndex="id" editor="text" edited="false" width="80" isLast="true" renderer="commGridColDelete"/>
</odin:gridColumnModel>		
</odin:editgrid>