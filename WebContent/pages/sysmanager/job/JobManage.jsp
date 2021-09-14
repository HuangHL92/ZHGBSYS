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
	return "<a href='#' onclick=\"pauseJob(1,'"+value+"')\" title=��ʱֹͣ��JOB����>��ͣ</a> / <a href='#' onclick=\"pauseJob(2,'"+value+"')\" title=�������и�JOB>����</a> / <img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' onclick=\"confirmDelete('dogriddelete','"+value+"');\">";
}
function confirmDelete(eventName,id){
	odin.confirm("��ȷ��Ҫɾ���ö�ʱ��������ȷ�����Ѿ�����������ɾ��",function(v){
		if(v=="ok"){
			radow.doEvent(eventName,id);
		}
	});
}
//��ͣ������������ĳ��JOB
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
	<odin:buttonForToolBar text="ˢ��" id="refresh" icon="images/sx.gif" cls="x-btn-text-icon" handler="refresh"/>
	<odin:buttonForToolBar text="����" id="save" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:groupBox property="s2" title="��ʱ���������Ϣ">
<table align="center">
	<tr>
		<odin:textEdit property="name" label="��������" title="ΪȫӢ�ģ����ұ���Ψһ���������ظ���" required="true"></odin:textEdit>
		<odin:textEdit colspan="4" property="title" size="70" label="�������" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="startdate" label="��ʼִ��ʱ��" value="<%=now%>"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="classname" size="115" label="�������ʵ����" required="true"></odin:textEdit>
		<odin:hidden property="id"/>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="param1" size="115" label="ִ�в���"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit colspan="6" property="description" size="115" label="��ע"></odin:textEdit>
	</tr>
</table>
<input type="hidden" name="id" id="id" />
</odin:groupBox>
<odin:groupBox property="s3" title="��ʱ����">
<table width="80%" align="center">
	<tr>
		<odin:select property="intervalType" label="�������" codeType="INTERVALTYPE"></odin:select>
		<odin:numberEdit property="intervalCount" label="�������" value="0" width="60" title="������ִ�У���ʱΪ0���⣬������������Ϊ0"></odin:numberEdit>
		<odin:numberEdit property="execount" label="ִ�д���" value="0" width="60" title="����0ʱ�Ż������ã�����Ϊ��Զִ����ȥ"></odin:numberEdit>
	</tr>
	<tr>
		<odin:textEdit property="everydaytime" label="ÿ�켸�㼸�ּ���" value=""></odin:textEdit>
		<td colspan="4" style="font-size: 12px;color: red;">���磬��ʱ���ʽΪ<%=time%></td>
	</tr>
	<tr>
		<odin:select property="weekDay" label="ÿ����" codeType="WEEKDAY" width="80"></odin:select>
		<odin:textEdit property="wdaytime" label="���㼸�ּ���" value=""></odin:textEdit>
	</tr>
	<tr>
		<odin:select property="lastDay" label="ÿ��" codeType="LASTDAY" width="80"></odin:select>
		<odin:textEdit property="ldaytime" label="���㼸�ּ���" value=""></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="expression" label="���ӱ��ʽ����" colspan="6" width="482"></odin:textEdit>
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
  <odin:gridEditColumn 	header="�������" width="80" dataIndex="title" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="��������" align="center" width="40" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="��ǰ״̬" align="center" edited="false" dataIndex="status" editor="select" codeType="JOBSTATUS"/>
  <odin:gridEditColumn 	header="�������ʵ����" align="center" dataIndex="classname" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="��ע"  dataIndex="description" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="����" align="center" dataIndex="id" editor="text" edited="false" width="80" isLast="true" renderer="commGridColDelete"/>
</odin:gridColumnModel>		
</odin:editgrid>