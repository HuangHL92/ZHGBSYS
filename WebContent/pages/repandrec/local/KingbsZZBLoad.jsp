<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<%-- <odin:toolBar property="btnToolBar">
	
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" style="">
</div> --%>
<odin:hidden property="imprecordid"/>
<odin:hidden property="searchDeptid"/>
<odin:hidden property="fname"/>
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="2"><label id="bz1" style="font-size: 12; color: red">ע������ѡ�ϼ������������������ݸ�����û��ƥ�䵽��Ӧ���������ڱ�ѡ�ϼ������½�����Ӧ������</label><br>
		</td>
	</tr>
	<tr>
		<td height="10px"></td>
	</tr>
	<tr>
		<odin:textIconEdit size="60" property="fabsolutepath" label="ѡ���������ļ�"
			onchange="javascript:docheckt();" readonly="true" required="true"></odin:textIconEdit>
	</tr>
	<tr>
		<tags:PublicTextIconEdit4 onchange="setParentValue" width="365"
			property="searchDeptBtn" label="��ѡ�ϼ�����" readonly="true" required="true"
			codetype="orgTreeJsonData" />
	</tr>
	<tr>
		<td height="10px"></td>
	</tr>
</table>
<div style="width: 85%">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="checkbox" id="fxz"/><span style="font-size: 12">�Ƿ���ա�����ְ��Ա��</span>
</div>
<div style="width: 85%" align="right"><odin:button text="��������"
	property="s111" handler="dothisfunc"></odin:button></div>

<odin:window src="/blank.htm" id="winFile" width="550" height="400"
	title="ѡ���������ļ�" />
<odin:window src="/blank.htm" id="winfresh" width="550" height="400"
	title="���������ļ�����" />
<script type="text/javascript">
function setParentValue(record,index){
	document.getElementById('searchDeptid').value=record.data.key;
	radow.doEvent('docheck2');
}

function docheckt(){
	radow.doEvent('docheck2');
}

function dothisfunc(record,index){
///	parent.odin.ext.getCmp('win1').hide();
	var id = document.getElementById('imprecordid').value;
	var file = document.getElementById('fabsolutepath').value;
	var dept = document.getElementById('searchDeptid').value;
	//����ְ��Ա
	var fxz = document.getElementById('fxz').checked;
	var param = '&initParams='+id+'||'+file+'||'+dept+'||'+fxz+'';
	if(file==null || file==''){
		odin.alert("��ѡ��ѡ���������ļ�����");
		return��
	}
	if(dept==null || dept==''){
		odin.alert("��ѡ�񡮱�ѡ�ϼ���������");
		return��
	}
	//alert(param);
///	parent.doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.repandrec.local.KingbsWinfresh'+param,'���������ļ�����',600, 400, null);
	parent.Ext.getCmp(subWinId).close();
	realParent.$h.openWinMin('winnn1122','pages.repandrec.local.KingbsWinfresh'+param,'�������',650,160,'qq','<%=request.getContextPath()%>');
	//radow.doEvent('imp3btn.onclick');
}
/**	
function myrefresh() 
{
     radow.doEvent('btnsx');
} 
var timer1= window.setInterval("myrefresh()",3000); 

function rendererthis(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return "";
	} else if(value=='1'){
		return "<img src='<%=request.getContextPath()%>/basejs/ext/resources/images/default/grid/wait.gif'>";
	} else if(value=='2'){
		if(false){
			clearInterval(timer1);
		}
		return "<img src='<%=request.getContextPath()%>/images/right1.gif'>";
	}
}*/
</script>