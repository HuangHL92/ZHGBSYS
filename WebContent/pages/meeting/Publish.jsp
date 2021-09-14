<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<div id="publishPanel"></div>

<%@include file="/comOpenWinInit.jsp" %>
<style>
.uploadify{
position: absolute;
left: 0px;
top: 0px;
}
.table-tdx { 
          border: 1px solid #000;
             }        
.table-td { background:#FFF;} 
#forView_peopleGrid{
	height:100%;
}

.x-fieldset{
	height:100%;
}
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 406px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<table>
	<tr>
		<odin:select2 property="meetingname" onchange="mNameChange"  label="��������" width="200"></odin:select2>
		<td width="20"></td>
		<odin:textEdit property="publishname" label="��������" readonly="true"  width="200"></odin:textEdit>
		<td width="20"></td>
		<odin:select2 property="publishtype" label="��������" codeType="AgendaType" readonly="true" width="160"></odin:select2>
		<td width="20"></td>
		<odin:textEdit property="titlename" label="��������" readonly="true" width="200"></odin:textEdit>
	</tr>
</table>

<odin:toolBar property="btnToolBar" applyTo="publishPanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��������" icon="image/zjyt.png" id="loadadd" handler="loadadd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�����" icon="image/icon021a6.gif"  id="infoUpdate" handler="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ������" icon="image/delete.png" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<%-- --<odin:buttonForToolBar text="��������" icon="image/btyy.png" id="publishCite" handler="publishCite" />
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="���ӱ���" icon="image/zjbt.png" id="titleadd" handler="titleadd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸ı���" icon="image/xgbt.png"  id="titleUpdate" handler="titleUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ������" icon="image/delete2.png"  id="titleDelete" handler="titleDelete"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������Ա����ʽ�⣩" icon="image/icon021a2.gif" id="addperson" handler="addperson" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Lrmx����" icon="image/icon021a2.gif" id="addLrmx" handler="addLrmx" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�ֹ�¼��" icon="image/icon021a2.gif" id="handadd" handler="handadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Lrmx����" icon="images/icon/table.gif" id="expLrmxGrid" handler="expLrmxGrid" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������Ȩ" icon="image/ytsq.png" id="publishPower" handler="publishPower" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="���⸴��" icon="image/ytfz.png" id="publishCopy" handler="publishCopy" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ա����" icon="image/ryfz.png" id="personCopy" handler="personCopy"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ա����" icon="image/ryyy.png" id="personCite" handler="personCite" />
	<odin:separator></odin:separator>
	<%--<odin:buttonForToolBar text="��ʾ" icon="image/ryyy.png" id="translation" handler="translation" />
	<odin:separator></odin:separator>
	 <odin:buttonForToolBar text="Ԥ��" icon="images/search.gif" id="meetingPreview" handler="btn_init" />
	<odin:buttonForToolBar text="����" icon="images/search.gif" id="meetingCreateRmb" handler="createRmbs" />
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="���չʾ" icon="images/search.gif" id="exportPdf" handler="exportPdf" isLast="true"/>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="�ĵ��ϴ�" icon="images/search.gif" id="fileload" isLast="true"/>--%>
</odin:toolBar>
<table style="width: 100%">
	<tr>
		<td  width="15%" >
		  <odin:groupBox title="������" >
				<div id="tree-div1" style="height: 270px;"></div>
			</odin:groupBox> 
		</td>
		 <td width="13%">
			<odin:editgrid2 property="peopleGrid" hasRightMenu="false" title="" forceNoScroll="true" autoFill="true"  pageSize="200" url="/">
				<odin:gridJsonDataModel>
					<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
					<odin:gridDataCol name="publishid" />
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0192a"/>
					<odin:gridDataCol name="sh001"/>
					<odin:gridDataCol name="delperson"/>
					<odin:gridDataCol name="a0000"/>
					<odin:gridDataCol name="yy_flag"/>
					<odin:gridDataCol name="sh000"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<%-- <odin:gridEditColumn2 header="selectall" width="30" gridName="monthlyWorkGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/> --%>
					<odin:gridEditColumn2 dataIndex="a0101" width="40" header="����" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="a0192a" width="100" header="������λ��ְ��" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="sh001" width="20" header="�����" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="delperson" width="30" header="����" editor="text" edited="false" align="center" isLast="true" renderer="updateperson"/>
				</odin:gridColumnModel>
			</odin:editgrid2>  
		</td>
		<td width="72%" align="center" rowspan="2">
			<odin:groupBox title="Ԥ��">
				<iframe id="mainIframe" name="mainIframe" 
					src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=" 
					frameborder="4" scrolling="auto" width="720" height="600"></iframe>
			</odin:groupBox>
		</td>
	</tr>
	<tr>
	<td colspan="2" width="28%">
<div style="height:345px;overflow: auto;">
<odin:groupBox property="group1" title="��Ա��Ϣ">
	<table >
		<tr>
			<odin:textEdit property="a0101" label="����" width="100" ></odin:textEdit>
			<odin:select2 property="a0104" label="�Ա�" codeType="GB2261" width="145" ></odin:select2>
			<%-- <odin:dateEdit property="a0107" label="��������" format="Ymd" width="120"/> --%>
			<odin:NewDateEditTag property="a0107" labelSpanId="a0107SpanId" maxlength="8" label="��������" width="145"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0141" label="������ò" codeType="GB4762" width="100"></odin:select2>
			<odin:textEdit property="zgxl" label="���ѧ��"  width="145"></odin:textEdit>
			<odin:textEdit property="zgxw" label="���ѧλ"  width="145"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a0192a" label="��ְ" colspan="4" width="315"></odin:textEdit>
			<odin:textEdit property="tp0125" label="��ְ��ע"  width="145" ></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="tp0111" label="����" colspan="6" width="415" readonly="true" ondblclick="openNR();"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0112" label="����" colspan="6" width="415"  readonly="true" ondblclick="openNM();"></odin:textEdit>
		</tr>--%>
		<tr>
			<odin:select2 property="tp0121" label="��������" width="100" onchange="changeNR()" codeType="NIRENTYPE"></odin:select2>
			<odin:textEdit property="tp0111" label="����ְ��"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0123" label="���α�ע"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="tp0122" label="��������" width="100" onchange="changeNM()" codeType="NIMIANTYPE"></odin:select2>
			<odin:textEdit property="tp0112" label="����ְ��"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0124" label="���ⱸע"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0113" label="��������" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0114" label="��ע˵��" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2  property="tp0116" label="��ʾ��ְ" data="['0', '��'],['1', '��']"  width="100" value=""></odin:select2>
			<odin:select2  property="tp0117" label="��ʾ�����" data="['0', '��'],['1', '��']"   width="145" value=""></odin:select2>
			<odin:textEdit property="sh001" label="�����" width="145"></odin:textEdit>
		</tr>
		<%-- <tr>
			<td colspan="5" align="left">
	       <iframe id="frame"  name="frame" height="33px" width="280px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder=��no�� border=��0�� marginwidth=��0�� marginheight=��0�� scrolling=��no�� allowtransparency=��yes��></iframe>
            </td>
			<td colspan="5" align="left">
				<tags:JUpload property="personfile" uploadLimit="1" label="ѡ���ļ�" fileTypeDesc="�����ļ�"   fileSizeLimit="20MB" fileTypeExts="*.*" />
			</td>
			<td colspan="5">
				<odin:button text="�����ϴ�" property="fujSave" handler="fujSave" ></odin:button>
			</td>
			<td  colspan="5">
				<odin:button text="����" property="personSave" handler="personSave" ></odin:button>
			</td>
		</tr> --%>
		<!-- <tr>
			<td bgcolor="#C0DCF1"   style="text-align: center ; line-height:80px ;">
         <div style="width: 120 px; height: 80px;">
            <font >����</font>
         </div>   
     	 </td>
			<td bgcolor="#C0DCF1"  colspan="5"  style="text-align: center ; line-height:80px ;">
			<div style="width: 220 px; height: 80px;">
            <span id = "div_fujian"></span>
            </div>
     	 	</td>
		</tr> -->
	</table>
	<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="50"></td>
			<td>
		       <iframe id="frame" name="frame" height="33px" width="330px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td>
				<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()"> --%>
				<odin:button text="�ύ" property="sc" handler="sc" ></odin:button>
			</td>
			<td  colspan="5">
				<odin:button text="����" property="personSave" handler="personSave" ></odin:button>
			</td>
			<td width="50"></td>
		</tr>
	</table>
	<odin:hidden property="pid"/>
</form>
	<table>
	<!-- <tr>
	<td  height="15" align="center">
	  <span style="text-align: center;display:block;" id = "div_fujian"></span>
	  </td>
	</tr> -->
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="67" width="555"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="add00"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="filename" width="450" editor="text" edited="false" header="��������" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="110" header="����" editor="text" edited="false" 
						menuDisabled="true" sortable="false" align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
	
</table>
</odin:groupBox>
</div>
</div>
	</td>
	</tr>
</table>
<odin:window src="/blank.htm" id="importLrmWinssh" width="400" height="150"
	title="�����������" modal="true" />

<odin:hidden property="meetingid"/>
<odin:hidden property="publish_id"/>
<odin:hidden property="title_id"/>
<odin:hidden property="new_userid"/>
<odin:hidden property="p_userid"/>
<odin:hidden property="ischange"/>
<odin:hidden property="downfile"/>
<odin:hidden property="sh000"/>
<odin:hidden property="personPath"/>
<script type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function(){
	document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
	$h.initGridSort('peopleGrid',function(g){
	    radow.doEvent('rolesort');
	  });
	var peopleGrid = Ext.getCmp('peopleGrid');
	peopleGrid.on('rowdblclick',function(gridobj,index,e){
	
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('sh000').value=rc.data.sh000;
		var sh000=document.getElementById('sh000').value;
		
		var yy_flag=rc.data.yy_flag;
		var ischange=document.getElementById('ischange').value;
		var new_userid=document.getElementById('new_userid').value;
		var p_userid=document.getElementById('p_userid').value;
		if(new_userid!=p_userid&&(ischange==null||ischange=="")){
			$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');
			radow.doEvent('personClear');
			yulan_rmb(sh000);
		}else if(yy_flag==1){
			$h.alert('ϵͳ��ʾ','����ԱΪ������Ա�����Դ���ݽ����޸�');
			radow.doEvent('personClear');
			yulan_rmb(sh000);
		}else{
			radow.doEvent('queryPersonx',sh000);
			//radow.doEvent('expShrmb',sh000);
			yulan_rmb(sh000);
		}
	});
}); 


function queryPersonx(sh000,titleid,titlename){
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');
	}else{
		document.getElementById('sh000').value=sh000;
		document.getElementById('title_id').value=titleid;
		document.getElementById('titlename').value=titlename;
		radow.doEvent('queryPersonx',sh000);
	}
}
//����ύ
function sc(){
	var pid = document.getElementById('sh000').value;
	if(pid != null && pid != ''){
		window.frames['frame'].impd(pid);
	}else{
		$h.alert('ϵͳ��ʾ','��ѡ��,���ύ����');
		window.frames['frame'].imd();
	}
	
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
    radow.doEvent("fileGrid.dogridquery");
}
/* function fg() {
	//window.close();
	//var parentWin = window.opener;
    radow.doEvent("fileGrid.dogridquery");
   // realParent.radow.doEvent("fileGrid.dogridquery");
} */
//grid��������ʾ�Ĳ������
function operateUod(value, params, record,rowIndex,colIndex,ds){
	return "<a href=\"javascript:deleteRow('"+rowIndex+"')\">ɾ��</a>&nbsp;";
}
//������ذ�ť
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

// ����
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// ɾ��
function deleteRow(rowIndex) {
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFi", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}

//�ϴ��ļ�
function appendixUpload() {
	var sh000 = document.getElementById('sh000').value;
	// ��������
	window.frames["frame"].uploadAppendixFile(sh000);
}
//����ر�,ǰ��ҳ�� �������ݸ���
function colseWin( html ){
	//����ǰһҳ��
	debugger;
	if(document.getElementById("div_fujian")!=null){
		alert(html);
		document.getElementById("div_fujian").innerHTML =html;
		
	}
	//window.close();
}
/* function filele(url){
	var tp0115 = rs.get('tp0115');
	var tp0118 = rs.get('tp0118');
	var url=tp0115.replace(/\\/g,"/");
	var meetingname = rs.get('meetingname');
	fz(meetingname);
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+tp0118+"</a>";
	} 
	

}  */
function cin(fujianInfo){
	debugger;
	alert(fujianInfo);
	/* return "<a href=\"javascript:downloads('" +url +"')\">"+fujianInfo+"</a>"; */
	if(document.getElementById("div_fujian")!=null){
		document.getElementById("div_fujian").innerHTML =fujianInfo;
		
	}
}
 function file(value, params, rs, rowIndex, colIndex, ds){
	var wj05 = rs.get('fileurl');
	var name = rs.get('filename');
	var url=wj05.replace(/\\/g,"/");
	debugger;
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+name+"</a>";
	} 
	

}  
//����
 function downloadFile(url) {
 	var downfile = url;
 	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
 	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
 }
function queryName(){
	var name = document.getElementById('queryName').value;
	if(!name){
		return;
	}
	//ȥ���ַ�������ͷ�Ŀո�
	name = name.replace(/^\s*|\s*$/g,"");
	if(name==''||name=='��������')
		{
		$h.alert('ϵͳ��ʾ','�������������ٲ�ѯ');
		return;
		}
	radow.doEvent('peopleGrid2.dogridquery');
}
//�鿴�������
function fujSave(){
	var sh000 = document.getElementById("sh000").value;//��ȡp0800��ֵ
	if (sh000 == null || sh000 =="" ) {
		odin.alert("����ѡ��,���ύ������");
		return;
	}
	$h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "�����б�", 760, 560, sh000,"<%=request.getContextPath()%>");
}

function translation(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','����ѡ�����⣡');
		return;
	}
	radow.doEvent('translation');
}

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
}

function publishPower(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','����ѡ�����⣡');
		return;
	}
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid==p_userid){
		$h.openPageModeWin('meetingPower','pages.meeting.MeetingPower','������Ȩ',600,550,{publish_id:publish_id},g_contextpath);
	}else{
		$h.alert('ϵͳ��ʾ','�Ǵ������޷���Ȩ');	
	}
}

function createRmbs(){
	var sh000=document.getElementById('sh000').value;
	var str1='';
	var str2='';
	if(sh000==''){
		var title_id=document.getElementById('title_id').value;
		if(title_id==''){
			var publish_id=document.getElementById('publish_id').value;
			if(publish_id==''){
				$h.alert('ϵͳ��ʾ','����ѡ�����ɶ���');
				return;
			}else{
				str1='publish_id';
				str2=publish_id;
			}
		}else{
			str1='title_id';
			str2=title_id;
		}
	}else{
		str1='sh000';
		str2=sh000;
	}
	ShowCellCover("start","��ܰ��ʾ��","�������������...");
	radow.doEvent('createRmbs',str1+"@@"+str2);
}

function btn_init(){
	var publishid=document.getElementById('publish_id').value;
	if(publishid==''){
		$h.alert('ϵͳ��ʾ','����ѡ��������Ԥ��');
	}else{
		document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=' +publishid;
	}
	//document.getElementById('mainIframe').src=document.frames["mainIframe"].location;
	
}

function yulan_rmb(sh000){
	document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=sh000@@'+sh000;
} 


function exportPdf(){
	var publishid=document.getElementById('publish_id').value;
	if(publishid==''){
		$h.alert('ϵͳ��ʾ','����ѡ��������Ԥ��');
	}else{
		$h.openPageModeWin('MeetingPreview','pages.meeting.MeetingPreview2&publishid='+publishid,'���չʾ',800,800,{publishid:publishid},g_contextpath);
	}
	//document.getElementById('mainIframe').src=document.frames["mainIframe"].location;
	
}

function updateperson(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">ά��</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">ɾ��</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">ɾ��</a></font>";
}
/*
function addpeople(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"addpel('"+record.get("a0000")+"');\">����</a></font>";
}*/

function publishCopy(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ�����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('publishCopy','pages.meeting.PublishCopy','���⸴��',360,400,{publish_id:publish_id},g_contextpath);
	}
}

function publishCite(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ�����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('publishCite','pages.meeting.PublishCite','��������',360,400,{publish_id:publish_id},g_contextpath);
	}
}

function personCopy(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('personCopy','pages.meeting.PersonCopy','��Ա����',800,600,'',g_contextpath);
	}
}

function personCite(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ�����ñ��⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('publishCite','pages.meeting.PersonCite','��Ա����',800,600,{title_id:title_id},g_contextpath);
	}
}

function handadd(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ����ѡ����Ա���ڱ��⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('handadd','pages.meeting.MeetingUpdatePel','������Ա',630,400,'',g_contextpath);
	}
}
function updatepel(sh000,yy_flag){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else if(yy_flag==1){
		$h.alert('ϵͳ��ʾ','����ԱΪ������Ա�����Դ���ݽ����޸�');
	}else{
		$h.openPageModeWin('updateperson','pages.meeting.MeetingUpdatePel','�޸���Ա',580,550,sh000,g_contextpath);
	}
}

function deletepel(sh000,a0000){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		radow.doEvent('delperson',sh000+','+a0000);
	}
}

function nameChange(record,index){
	document.getElementById('publish_id').value = record.data.key;
	updateTree();
}

function mNameChange(record,index){
	document.getElementById('meetingid').value = record.data.key;
	document.getElementById('publish_id').value="";
	document.getElementById('title_id').value="";
	document.getElementById('publishname').value ="";
	document.getElementById('publishtype').value ="";
	document.getElementById('publishtype_combo').value ="";
	document.getElementById('titlename').value ="";
	document.getElementById('p_userid').value ="";
	document.getElementById('ischange').value ="";
	updateTree();
}
function reloadSelData(){
	document.getElementById('publish_id').value ="";
	document.getElementById('title_id').value ="";
	document.getElementById('publishname').value ="";
	document.getElementById('publishtype').value ="";
	document.getElementById('publishtype_combo').value ="";
	document.getElementById('titlename').value ="";
	document.getElementById('p_userid').value ="";
	document.getElementById('ischange').value ="";
	//radow.doEvent('initX');
	updateTree();
}

function reloadPerGrid(){
	radow.doEvent('peopleGrid.dogridquery');
}


function titleadd(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','����ѡ���������⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('TitleAdd','pages.meeting.TitleAdd','����',650,530,{type:'add'},g_contextpath);
	}
}
function titleUpdate(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','����ѡ����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('TitleAdd','pages.meeting.TitleAdd','�޸ı���',650,530,{type:'update'},g_contextpath);
	}

}

function titleDelete(){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	var title_id=document.getElementById('title_id').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else if(title_id==null||title_id==""){
		$h.alert('ϵͳ��ʾ','����ѡ�����');
	}else{
		var titlename=document.getElementById('titlename').value;
		if(confirm('ȷ��ɾ�����⣺'+titlename+ ' ?')){
			radow.doEvent('titleDelete');
		}
	}
}

function infoDelete(){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	var publish_id=document.getElementById('publish_id').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else if(publish_id==null||publish_id==""){
		$h.alert('ϵͳ��ʾ','����ѡ������');
	}else{
		var publishname=document.getElementById('publishname').value;
		if(confirm('ȷ��ɾ�����⣺'+publishname+ ' ?')){
			radow.doEvent('confirmDelete');
		}
	}
}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.meeting.PublishAdd','����',250,200,{publish_id:''},g_contextpath);
}
function infoUpdate(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','����ѡ�����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('loadadd','pages.meeting.PublishAdd','�޸�����',250,315,{publish_id:publish_id},g_contextpath);
	}
}
Ext.onReady(function() {
	document.getElementById('meetingid').value = document.getElementById('subWinIdBussessId2').value;
	var viewSize = Ext.getBody().getViewSize();
	var publishGrid = Ext.getCmp('peopleGrid');
	publishGrid.setHeight(317);
	//var publishGrid2 = Ext.getCmp('peopleGrid2');
	//publishGrid2.setHeight(viewSize.height-28);
	//publishGrid.setWidth(350);
	var btnToolBar = Ext.getCmp('btnToolBar');
	btnToolBar.setWidth(viewSize.width);
	/* publishGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('publish_id').value = rc.data.publishid;
	}); */
	
	/*$h.initGridSort('publishGrid',function(g){
		radow.doEvent('publishsort');
	}); */
});

Ext.onReady(function() {
	 var tree = new Ext.tree.TreePanel({    
          region: 'center',
          id: 'titleTree',
          el: 'tree-div1',
          //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
          collapsible: true,    
          /* title: '����',//�����ı�  */   
          width: 265,  
          height:260,
          border : false,//���    
          autoScroll: true,//�Զ�������    
          animate : true,//����Ч��    
          rootVisible: true,//���ڵ��Ƿ�ɼ�    
          split: true,    
          loader : new Ext.tree.TreeLoader({
       	   dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.meeting.Publish&eventNames=updateTree&meetingid='
          }),  
          root : new Ext.tree.AsyncTreeNode({    
              text: "",
              id:'-1'
          }),  
          listeners: { 
       	   click: function(node){
       		   //alert(node.attributes.id);
       		   //document.getElementById('titlename').value=node.attributes.text;
       		   //var title_id=node.attributes.id;
       		   var title_id=node.attributes.id;
       		   radow.doEvent("updateTitle",title_id); 
       	   },
              afterrender: function(node) {        
            	  node.expandAll();//չ���� 
              }        
          }     
      });    
	 tree.render();
});

function updateTree() {
	var tree = Ext.getCmp("titleTree");
	var url = 'radowAction.do?method=doEvent&pageModel=pages.meeting.Publish&eventNames=updateTree&meetingid=';
	tree.loader.dataUrl = url + document.getElementById('meetingname').value;
	tree.root.setText(Ext.getCmp('meetingname_combo').getValue());
	tree.root.reload();
	tree.expandAll();
}

function uploadfile(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����ݣ�');
		return;
	}
	$('#fileload2').uploadify('upload','*');
	//reload();
	//alert();
}
function reload(){
	radow.doEvent('publishGrid.dogridquery');
}
function HandleFile(value, params, record, rowIndex, colIndex, ds){
	if(record.get("pat00s")==null||record.get("pat00s")==""){
		return;
	}
	//ֻ��һ����Ϣʱ
	if(record.get("pat00s").indexOf(",")==-1){
		result = "<table width=''><tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+record.get("pat00s")+"');\">"+record.get("pat04s")+"</a></font></td><td align='left'><u style=\"color:#FFFFFF\">����</u>&nbsp;&nbsp;&nbsp;<u style=\"color:#FFFFFF\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+record.get("pat00s")+"')\">ɾ��</a></tr>";
		return result;
	}
	var pat00s = record.get("pat00s").split(",");
	var pat04s = record.get("pat04s").split(",");
	var result = "<table width='100%'>";
	for(var i=0;i<pat00s.length;i++){
		/* result = result+"<div align='center' width='100%' ><font color=blue>"
		+ "<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a>"
		+ "</font></div><br>"; */
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		if(i==0){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><u style=\"color:#D3D3D3\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></tr>";
			continue;
		}
		if(i==pat00s.length-1){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<u style=\"color:#D3D3D3\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></tr>";
			continue;
		}
		
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></tr>";
	}
	/* result = result.substring(0,result.length-4); */
	result = result+"</table>"
	return result;
}
function outfile(pat00){
	var url = g_contextpath+'/PublishFileServlet?method=publish_att&pat00='+pat00;
	window.location.href=url;
}

function topordown(pat00,type){
	radow.doEvent('sort',pat00+"@"+type);
}

function deleteAtt(pat00){
	radow.doEvent('deleteAtt',pat00);
}

function onQueueComplete(){
	radow.doEvent('publishGrid.dogridquery');
}
function queryPerson(){
	radow.doEvent('peopleGrid.dogridquery');
}

function addperson(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		$h.openPageModeWin('addperson','pages.meeting.MeetingByName','������Ա',1020,520,{meeting_id:''},g_contextpath);
	}
}


function expLrmxGrid(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ�����⣡');
		return;
	}
	//radow.doEvent('exportLrmxBtn');
	radow.doEvent("exportLrmxBtn", "zip");
}

function addLrmx(){
	var contextPath = '<%=request.getContextPath()%>';
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ����⣡');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('ϵͳ��ʾ','���û�û���޸�Ȩ��');	
	}else{
		var publish_id = document.getElementById('publish_id').value;
		odin.showWindowWithSrc("importLrmWinssh",contextPath+"/pages/publicServantManage/ImportLrmssh.jsp?businessClass=com.picCut.servlet.SaveLrmFile&title_id="+title_id+"&publish_id="+publish_id);
	}
}

/*var addper =  new Ext.menu.Menu({id:'addper',items:[
new Ext.menu.Item({id:'printRmb',disabled:false,text:'��ӡ'}),
new Ext.menu.Item({id:'printSet',disabled:false,text:'��ӡ������'})
]});*/

function changeNR(){
	var tp0113=document.getElementById('tp0113').value;
	if(tp0113==''){
		var tp0121=document.getElementById('tp0121').value;
		if(tp0121=='2'){
			document.getElementById('tp0113').value='�ɲ���ְ';
		}else if(tp0121=='4'){
			document.getElementById('tp0113').value='��������ת��';
		}
	}
}

function changeNM(){
	var tp0113=document.getElementById('tp0113').value;
	if(tp0113==''){
		var tp0122=document.getElementById('tp0122').value;
		if(tp0122=='3'||tp0122=='4'){
			document.getElementById('tp0113').value='�ѵ�������������';
		}
	}
}

function personSave(){
	var sh000=document.getElementById('sh000').value;
	if(sh000==null||sh000==''){
		alert('����˫��ѡ����Ա��');
	}else{
		var tp0113=document.getElementById('tp0113').value;
		if(tp0113==''){
			if(confirm("��������Ϊ�գ��ԡ�������Ҫ���Զ���䣿")){
				document.getElementById('tp0113').value='������Ҫ';
			}
		}
		radow.doEvent("personSave");
		yulan_rmb(sh000);
	}
}
/* function downloadword(){
	setTimeout(xx,1000);
} */
function downloadword(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
	//window.location = "DownloadServlet?method=PersionFile&filePath=" + encodeURI(encodeURI(url));
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function download(id){
	var personPath = document.getElementById('personPath').value;
	//alert(personPath);
	window.location="ProblemDownServlet?method=downFile&prid="+personPath;
}

var g_contextpath = '<%= request.getContextPath() %>';

function openNR(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=1','������Ϣ',580,350,'',g_contextpath);
}

function openNM(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=2','������Ϣ',580,350,'',g_contextpath);
}
function clearValue(){

}

</script>