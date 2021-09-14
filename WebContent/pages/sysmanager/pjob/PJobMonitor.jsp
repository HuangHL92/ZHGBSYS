<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function commGridCol(value, params, record, rowIndex, colIndex, ds){
	var status = record.get("status");
	if(status=='1'){
		return "<a href='#' onclick='isCancelPJob(\""+value+"\")'>ȡ��ִ��</a>";
	}else{
		return "ִ�н���";
	}
}
function isCancelPJob(id){
	odin.confirm("��ȷ��Ҫȡ���ò��������ִ����",function(v){
		if(v=="ok"){
			radow.doEvent("cancelPJob",id);
		}
	});
}
var pbar = null; //����������
var cuePjob = null;
function startProgress(pjob,pjobRun){
	cuePjob = pjob;
	var s = pjobRun.status;
	if("1"==s){
		var html = "������Ϊ��"+pjob.name+"������ʼ����ʱ��Ϊ��"+odin.Ajax.formatDateTime(pjobRun.startdate)+"��������ִ���С�";
		document.getElementById("basicInfo").innerHTML = html;
		pbar = new Ext.ProgressBar({
			width:600,
			renderTo :'PBar'
		});
		window.setTimeout(function(){
			radow.doEvent("updateProgress");
		},2000);
	}
}
function updateProgress(progress,timeMsg){
	var update_progress = odin.round(progress,2);
	console.log(progress+"||"+update_progress);
	pbar.updateProgress(update_progress/100,update_progress+'%,��ʱ��'+timeMsg,true);
	if(progress<100){
		window.setTimeout(function(){
			radow.doEvent("updateProgress");
		},2000);
	}else{
		pbar.updateProgress(100,"�����,�ܺ�ʱ��"+timeMsg,true);
		document.getElementById("basicInfo").innerHTML = "������Ϊ��"+cuePjob.name+"�����Ѿ�ִ����ɣ�";
		odin.ext.getCmp("pjobRunGrid").store.reload();
	}
}
</script>
<input id="pjobName" name="pjobName" value="<%=request.getParameter("initParams")%>" type="hidden"/>
<odin:groupBox title="���½��ȼ��">
<div id="basicInfo" style="color: red;font-size: 12px;font-weight: bold"></div>
<div id="PBar" style="margin-top: 6px;"></div>
</odin:groupBox>
<odin:groupBox title="ִ����ʷ">
<odin:editgrid property="pjobRunGrid" sm="row" bbarId="pageToolBar" hasRightMenu="false" isFirstLoadData="false" url="/" autoFill="false" forceNoScroll="false" title="" height="200" >
<odin:gridJsonDataModel  id="id">
  <odin:gridDataCol name="id"/>
  <odin:gridDataCol name="pjobname"/>
  <odin:gridDataCol name="startdate"/>
  <odin:gridDataCol name="enddate"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="loginname"/>
  <odin:gridDataCol name="username"/>
  <odin:gridDataCol name="changereason" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn></odin:gridRowNumColumn>
  <odin:gridEditColumn 	header="��ʼʱ��" align="center" width="90" dataIndex="startdate" edited="false" editor="text"/>
  <odin:gridEditColumn 	header="����ʱ��" align="center" width="90" dataIndex="enddate" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="ִ��״̬" align="center" width="80" edited="false" dataIndex="status" editor="select" codeType="PJOBSTATUS"/>
  <odin:gridEditColumn 	header="������" align="center" dataIndex="username" width="80" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="����ԭ��" width="150" width="150" dataIndex="changereason" editor="text" edited="false"/>
  <odin:gridEditColumn 	header="����" align="center" dataIndex="id" editor="text" edited="false" width="120" isLast="true" renderer="commGridCol"/>
</odin:gridColumnModel>		
</odin:editgrid>
</odin:groupBox>
