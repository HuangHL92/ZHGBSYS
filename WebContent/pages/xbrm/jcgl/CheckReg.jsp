<%@page import="com.insigma.siis.local.epsoft.config.AppConfig"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit3.jsp" %>
<%
String wl = AppConfig.GBJDWLQH;
%>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var wl = '<%= wl %>';

var g_contextpath = '<%= request.getContextPath() %>';

function opFunction(value, params, record, rowIndex, colIndex, ds) {
	var id = record.get('checkregid');
	var status = record.get('regstatus');
	var regtype = record.get('regtype');
	if(status=='0'){
		var link = "<a href=\"javascript:void()\" onclick=\"sqsj('"+id+"')\">��Ϣ����</a>";
		link = link + " | <a href=\"javascript:void()\" onclick=\"xbfk('"+id+"')\">�������</a>";
		return link;
	} else if(status=='1'){
		var link = "<a href=\"javascript:void()\" onclick=\"cxsq('"+id+"')\">ȡ������</a>";
		link = link + " | <a href=\"javascript:void()\" onclick=\"xbfk('"+id+"')\">�������</a>";
		return link;
	} else {
		return '';
	}
}
function downFunction(value, params, record, rowIndex, colIndex, ds){
	var grid = Ext.getCmp('memberGrid');
	var id = record.get('checkregid');
	var cm = grid.getColumnModel();
	var col = cm.config[colIndex].dataIndex;
	if(value=='1'){
		<% if(wl.equals("1")){ %>
		return "<a href=\"javascript:void()\" onclick=\"viewF('"+id+"','"+col+"')\">Ԥ��</a>";
		<%} else {%>
		return "<a href=\"javascript:void()\" onclick=\"downF('"+id+"','"+col+"')\">���ϴ�</a>";
		<%} %>
	} else {
		return '';
	}
}

function viewF(checkregid, col){
	if(col=="crjxx") {
		$h.openPageModeWin('impCrjxx','pages.xbrm.jcgl.CheckRegCRJ','���뾳������Ϣ',1150,700,{checkregid :checkregid},g_contextpath);
	} else if(col == "fcxx"){
		$h.openPageModeWin('impFcxx','pages.xbrm.jcgl.CheckRegFC','����������Ϣ',1150,700,{checkregid : checkregid},g_contextpath);
	} else if(col=="sybxxx"){
		$h.openPageModeWin('impCbxx','pages.xbrm.jcgl.CheckRegBX','���շ�����Ϣ',1150,700,{checkregid :checkregid},g_contextpath);
	} else if(col=="gpjjxx"){
		$h.openPageModeWin('impZqxx','pages.xbrm.jcgl.CheckRegZQ','֤ȯ������Ϣ',1150,700,{checkregid :checkregid},g_contextpath);
	} else if(col=="gsxx1"){
		$h.openPageModeWin('impGscbxx','pages.xbrm.jcgl.CheckRegGSCG','���̲ιɷ�����Ϣ',1150,700,{checkregid :checkregid},g_contextpath);
	} else if(col=="gsxx2"){
		$h.openPageModeWin('impGszwxx','pages.xbrm.jcgl.CheckRegGSZW','���θ߼�ְ������Ϣ',1150,700,{checkregid :checkregid},g_contextpath);
	}
}
function downF(checkregid, col){
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFKFile&checkregid="+encodeURI(encodeURI(checkregid))+"&type="+col;
}

function sqsj(id){
	document.getElementById('checkregid').value=id;
	//radow.doEvent('chaneStatus','1');
	$h.showModalDialog('picupload',g_contextpath+'/pages/xbrm/jcgl/recDeptWin.jsp?checkregid='+id+'','��Ϣ����', 300,100,null,{checkregid : id},true);
}
function cxsq(id){
	document.getElementById('checkregid').value=id;
	radow.doEvent('chaneStatus','0');
}

function xbfk(id){
	document.getElementById('checkregid').value=id;
	$h.openPageModeWin('loadadd','pages.xbrm.jcgl.CheckRegFKJG','�������',700,500,{checkregid :id},g_contextpath);
}

function downLoadP(){
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var ids = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			ids = ids + record.data.checkregid+",";
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	document.getElementById('frameid').src="RegCheckServlet?method=downloadPFile&checkregid="+encodeURI(encodeURI(ids));
}
function downLoadP2(){
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var ids = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			ids = ids + record.data.checkregid+",";
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	document.getElementById('frameid').src="RegCheckServlet?method=downloadPFile_bs&checkregid="+encodeURI(encodeURI(ids));
}


function expSxd(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	radow.doEvent('importSheet', checkregid);
}

function downLoadP3(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_chdbsj&checkregid="+encodeURI(encodeURI(checkregid));
}
function downLoadP4(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_chdbzd&checkregid="+encodeURI(encodeURI(checkregid));
}
function downLoadP5(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_jghz&checkregid="+encodeURI(encodeURI(checkregid));
}
function cxwth(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_xxwth&p=1&checkregid="+encodeURI(encodeURI(checkregid));
}

function cxwth2(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_xxwth&p=2&checkregid="+encodeURI(encodeURI(checkregid));
}

function jtcyk(){
	var p = "&filetype=jtcyk";
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
		+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1"+p,'���봰��',530,180);	
}
function drpc(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
		+ "/pages/xbrm/jcgl/DataVerify4.jsp?i=1",'���봰��',530,180);	
}

function checkClicktable(){
	
}

function pthz(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_pthz",'���봰��',530,180);	
}
function yscg(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_yscg",'���봰��',530,180);	
}
function gattxz(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_gattxz",'���봰��',530,180);	
}
function lwgat(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_lwgat",'���봰��',530,180);	
}
function yjgw(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_yjgw",'���봰��',530,180);	
}
function wyjgw(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_wyjgw",'���봰��',530,180);	
}
function fcqd(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_fcqk",'���봰��',530,180);	
}
function gpqk(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_gpqk",'���봰��',530,180);	
}
function jjqk(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_jjqk",'���봰��',530,180);	
}
function tsxbx(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_tsxbx",'���봰��',530,180);	
}
function jsqk(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify3.jsp?i=1&filetype=ndk_jsqk",'���봰��',530,180);	
}

function impCrjxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impCrjxx','pages.xbrm.jcgl.CheckRegCRJ','���뾳������Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}
function impFcxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impFcxx','pages.xbrm.jcgl.CheckRegFC','����������Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}

function impCbxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impCbxx','pages.xbrm.jcgl.CheckRegBX','���շ�����Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}

function impGscbxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impGscbxx','pages.xbrm.jcgl.CheckRegGSCG','���̲ιɷ�����Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}
function impGszwxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impGszwxx','pages.xbrm.jcgl.CheckRegGSZW','����ְ������Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}
function impZqxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('impZqxx','pages.xbrm.jcgl.CheckRegZQ','֤ȯ������Ϣ',1150,700,{checkregid :record.data.checkregid},g_contextpath);
}

function expPc(){
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var ids = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			ids = ids + record.data.checkregid+",";
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFile_PC&checkregid="+encodeURI(encodeURI(ids));
}

function impPc(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify5.jsp?i=1",'���봰��',530,180);	
}
function impPcZip(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/xbrm/jcgl/DataVerify6.jsp?i=1",'���봰��',530,180);	
}

<odin:menu property="fMenu_m1">
<odin:menuItem text="������Ϣ��" property="exp1Btn"  handler="downLoadP" ></odin:menuItem>
<odin:menuItem text="������Ϣ��(ʡ)" property="exp2Btn"  handler="downLoadP2" ></odin:menuItem>
<odin:menuItem text="������Ϣ��(ʡ)-����" property="drpc"  handler="drpc"  isLast="true"></odin:menuItem>

</odin:menu>

<odin:menu property="fMenu_m2">
<odin:menuItem text="������" property="exp3Btn"  handler="downLoadP3" ></odin:menuItem>
<odin:menuItem text="�ص���" property="exp4Btn"  handler="downLoadP4" ></odin:menuItem>
<odin:menuItem text="��ʵ����" property="exp5Btn"  handler="downLoadP5"  isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="fMenu_m3">
<% if(wl.equals("1")){ %>
<odin:menuItem text="��ͥ��Ա��" property="jtcyk"  handler="jtcyk" ></odin:menuItem>

<odin:menuItem text="��ͨ�������" property="pthz"  handler="pthz" ></odin:menuItem>
<odin:menuItem text="��˽�������" property="yscg"  handler="yscg" ></odin:menuItem>
<odin:menuItem text="�۰�̨ͨ��֤���" property="gattxz"  handler="gattxz" ></odin:menuItem>
<odin:menuItem text="�����۰�̨���" property="lwgat"  handler="lwgat" ></odin:menuItem>
<odin:menuItem text="�ƾӹ������" property="yjgw"  handler="yjgw" ></odin:menuItem>
<odin:menuItem text="δ�ƾӹ���(���ӹ���)���" property="wyjgw"  handler="wyjgw" ></odin:menuItem>
<odin:menuItem text="�������" property="fcqd"  handler="fcqd" ></odin:menuItem>
<odin:menuItem text="��Ʊ���" property="gpqk"  handler="gpqk" ></odin:menuItem>
<odin:menuItem text="�������" property="jjqk"  handler="jjqk" ></odin:menuItem>
<odin:menuItem text="Ͷ���ͱ���" property="tsxbx"  handler="tsxbx" ></odin:menuItem>
<odin:menuItem text="�������" property="jsqk"  handler="jsqk"  isLast="true"></odin:menuItem>
<%} else {%>
<odin:menuItem text="��ͥ��Ա��" property="jtcyk"  handler="jtcyk" isLast="true" ></odin:menuItem>
<%} %>
</odin:menu>


<odin:menu property="fMenu_m4">
<odin:menuItem text="���ε���Zip" property="impPcZip"  handler="impPcZip" ></odin:menuItem>
<odin:menuItem text="���ε���Excel" property="impPc"  handler="impPc" ></odin:menuItem>
<odin:menuItem text="���뾳������Ϣ" property="impCrjxx"  handler="impCrjxx" ></odin:menuItem>
<odin:menuItem text="��������" property="impFcxx"  handler="impFcxx" ></odin:menuItem>
<odin:menuItem text="Ͷ���ͱ��շ���" property="impCbxx"  handler="impCbxx" ></odin:menuItem>
<odin:menuItem text="��Ʊ������" property="impZqxx"  handler="impZqxx" ></odin:menuItem>
<odin:menuItem text="�ι���Ϣ����" property="impGscbBtn"  handler="impGscbxx" ></odin:menuItem>
<odin:menuItem text="���θ߼�ְ����Ϣ����" property="impGszwBtn"  handler="impGszwxx"  isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="fMenu_m5">
<odin:menuItem text="ί�к�(ʡ)" property="wths1"  handler="cxwth" ></odin:menuItem>
<odin:menuItem text="ί�к�(��)" property="wths2"  handler="cxwth2"  isLast="true"></odin:menuItem>
</odin:menu>
</script>
			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="checkregid"/>
<odin:hidden property="regname"/>
<odin:hidden property="downfile"/>

<odin:groupBox title="��ѯ����">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="regname1" label="��������" ></odin:textEdit>
		<odin:textEdit property="regno1" label="���α��" maxlength="8" ></odin:textEdit>
		<odin:textEdit property="xm" label="��Ա����" ></odin:textEdit>
		<odin:textEdit property="sfz" label="���֤����" ></odin:textEdit>
		
		<%-- <odin:select2 property="rb_type" label="����" data="['1','���ӻ���'],['2','�������']"></odin:select2> --%>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="������Ϣ" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="checkregid" />
		<odin:gridDataCol name="ckrow"/>
		<odin:gridDataCol name="regno"/>
		<odin:gridDataCol name="regname"/>
		<odin:gridDataCol name="checkdate"/>
		<odin:gridDataCol name="regstatus"/>
		<odin:gridDataCol name="groupid"/>
		<odin:gridDataCol name="groupname"/>
		<odin:gridDataCol name="reguser"/>
		<odin:gridDataCol name="regtype"/>
		<odin:gridDataCol name="regotherid"/>
		
		<odin:gridDataCol name="crjxx"/>
		<odin:gridDataCol name="fcxx"/>
		<odin:gridDataCol name="sybxxx"/>
		<odin:gridDataCol name="gpjjxx"/>
		<odin:gridDataCol name="gsxx1"/>
		<odin:gridDataCol name="gsxx2"/>
		
		<odin:gridDataCol name="userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="checkregid" width="110" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="regstatus" width="110" hidden="true" editor="text" header="״̬" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="ckrow" checkBoxClick="checkClicktable" width="30" header="" editor="checkbox"  edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="regname" width="100" header="��������" editor="text" edited="false" align="center"/>
		<%-- <odin:gridEditColumn2 dataIndex="checkdate" width="140" header="�˲�����" editor="text" edited="false" align="center"/> --%>
		<odin:gridEditColumn2 dataIndex="regno" width="100" header="���α��" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="groupname" width="100" header="�������" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="crjxx" width="50" header="���뾳����" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="fcxx" width="50" header="��������" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="sybxxx" width="50" header="Ͷ���ͱ��շ���" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="gpjjxx" width="50" header="��Ʊ������" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="gsxx1" width="50" header="�ιɷ���" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="gsxx2" width="50" header="���θ߼�ְ����" editor="text" edited="false" renderer="downFunction" align="center"/>
		
		<%-- <odin:gridEditColumn2 dataIndex="reguser" width="140" header="������" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="regtype" width="140" header="����"  hidden="true" editor="text" edited="false"  align="center"/> --%>
		<odin:gridEditColumn2 dataIndex="regotherid" width="140" header="����id" hidden="true" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="op" width="140" header="����" editor="text" renderer="opFunction" isLast="true" edited="false"  align="center" isLast="true"  />
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>�˲����</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ա�б�" icon="image/icon021a6.gif" handler="personlist"  id="personlist"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif"  handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="infoSearch" handler="infoSearch" />
	<odin:separator></odin:separator>
	
	<odin:buttonForToolBar text="������Ϣ��" icon="images/icon/exp.png" menu="fMenu_m1" id="infoSearch2" />
	<odin:separator></odin:separator>
	
	<% if(wl.equals("1")){ %>
	<odin:buttonForToolBar text="���ݽ���" icon="images/icon/exp.png" menu="fMenu_m4" id="fkxxdr"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��˶ԱȻ���" icon="images/icon/exp.png" menu="fMenu_m2" id="infoSearch3" />
	<odin:separator></odin:separator>
	<%} else { %>
	<odin:buttonForToolBar text="��ѯί�к�" icon="image/u115.png" menu="fMenu_m5" id="cxwth"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������ǩ��" icon="images/icon/exp.png" handler="expSxd" id="dcsqd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��������" icon="images/icon/exp.png" handler="expPc" id="fkxxdr"/>
	<odin:separator></odin:separator>
	<%} %>
	<odin:buttonForToolBar text="��������" icon="image/icon021a6.gif" menu="fMenu_m3" id="infoSearch4" />
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	/* memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('checkregid').value = rc.data.checkregid;
		document.getElementById('regname').value = rc.data.regname;
	}); */
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('checkregid').value = rc.data.checkregid;
		
		$h.openPageModeWin('loadadd','pages.xbrm.jcgl.Personlist','��Ա��Ϣά��',1150,700,{checkregid :rc.data.checkregid},g_contextpath);

	});
	
});
function personlist(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('loadadd','pages.xbrm.jcgl.Personlist','��Ա��Ϣά��',1150,700,{checkregid :checkregid},g_contextpath);

}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm.jcgl.CheckRegInfo','���Ӻ˲�',500,170,{checkregid:''},g_contextpath);
}
function infoUpdate(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var checkregid = record.data.checkregid;
	$h.openPageModeWin('loadadd','pages.xbrm.jcgl.CheckRegInfo','�޸ĺ˲�',500,170,{checkregid :checkregid},g_contextpath);
}
function infoDelete(){//�Ƴ���Ա
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var checkregids = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			if(record.data.regstatus=='0'){
				checkregids = checkregids + record.data.checkregid+",";
			}
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	if(count>0 && checkregids==''){
		odin.alert("��ѡ���ݲ���ɾ����ȷ���Ƿ����ڽ������ݣ�");
		return null;
	}
	
	/* var checkregid = document.getElementById('checkregid').value;
	var regname = document.getElementById('regname').value;
	if(checkregid==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	var record = Ext.getCmp("memberGrid").getSelectionModel().getSelected();
	if(record.data.regstatus!='0'){
		$h.alert('ϵͳ��ʾ','������������ȡ�����У�����ɾ����');
		return;
	} */
	$h.confirm("ϵͳ��ʾ��",'ɾ����¼����ɾ���ü�¼��Ӧ��������Ա��Ϣ�Լ�������ȷ��ɾ����?',400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',checkregids);
		}else{
			return false;
		}		
	});
}


function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.rb_id==$('#rb_id').val()){
				
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	}
}
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}

function getRecord(gridid){
	var grid=Ext.getCmp(gridid);
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var record = null;
	for(var i=0;i<rowCount;i++) {
		var record2=store.getAt(i);
		if(record2.data.ckrow == true){
			record = record2;
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	if(count>1){
		odin.alert("��ֻ��ѡһ�����ݣ�");
		return null;
	}
	return record;
}

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	//window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	document.getElementById('frameid').src="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	//ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function cc(){
	
}
</script>


