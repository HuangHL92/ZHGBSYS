<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
<!--
function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
	return "<a href='#' onclick=\"runPJob('"+record.get('name')+"')\" title=��ʼִ�и�����>��ʼִ��</a> / \
	 <a href='#' onclick=\"monitorPJob('"+record.get('name')+"')\" title='�鿴�����������һ�����н��ȣ�������ʵʱ���'>���м��</a> / \
	 <img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' onclick=\"confirmDelete('dogriddelete','"+record.get('name')+"');\">";
}
function confirmDelete(eventName,id){
	odin.confirm("��ȷ��Ҫɾ���ö�ʱ������",function(v){
		if(v=="ok"){
			radow.doEvent(eventName,id);
		}
	});
}
//��ͣ������������ĳ��JOB
function runPJob(name){
	radow.doEvent("runPJob",name);
}
function monitorPJob(name){
	var tname = encodeURIComponent(name);
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobMonitor&initParams="+tname,"����������",700,388);
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
<odin:groupBox property="s2" title="�������������Ϣ">
<table align="center">
	<tr>
		<odin:textEdit property="name" label="��������" title="ΪȫӢ�ģ����ұ���Ψһ���������ظ���" required="true"></odin:textEdit>
		<odin:textEdit colspan="4" property="title" size="70" label="�������" required="true"></odin:textEdit>
	</tr>
	<tr>
		<odin:select property="pjobtype" label="�������" value="1"></odin:select>
		<odin:textEdit colspan="4" property="jobcontent" size="115" label="����ִ������" title="�����Ϊ�洢����ʱ����Ϊ������������ΪJava��ʱ��������" required="true"></odin:textEdit>
		<odin:hidden property="id"/>
	</tr>
	<tr>
		<odin:numberEdit property="pcount" label="��������" value="5"></odin:numberEdit>
		<odin:textEdit colspan="4" property="param" size="115" label="ִ�в���" title="java��ʱ����ִ��ʱ�Ĳ���"></odin:textEdit>
	</tr>
</table>
<input type="hidden" name="id" id="id" />
</odin:groupBox>
<odin:groupBox property="s3" title="��ʱ����">
<table width="80%" align="center">
	<tr>
		<odin:textEdit property="everydaytime" label="ÿ�켸�㼸�ּ���" value=""></odin:textEdit>
		<td colspan="4" style="font-size: 12px;color: red;">���磬��ʱ���ʽΪ14:30:00</td>
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
  <odin:gridEditColumn 	header="�������" width="80" dataIndex="title" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="��������" align="center" width="40" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="��ǰ���" align="center" edited="false" dataIndex="jobtype" editor="select" codeType="PJOBTYPE"/>
  <odin:gridEditColumn 	header="ִ������" align="center" dataIndex="jobcontent" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="��������"  dataIndex="pcount" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="ִ�в���"  dataIndex="param" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="����ʱ��"  dataIndex="createdate" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="����" align="center" dataIndex="id" editor="text" edited="false" width="80" isLast="true" renderer="commGridColDelete"/>
</odin:gridColumnModel>		
</odin:editgrid>