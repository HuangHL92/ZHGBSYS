<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%-- <odin:toolBar property="btnToolBar">
	
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" style=""> --%>
</div>
<odin:hidden property="imprecordid"/>
<odin:hidden property="searchDeptid"/>
<odin:hidden property="bzbDeptid"/>
	<table cellspacing="2" width="98%" align="center">
				<tr>
					<td colspan="2">
						<label id="bz1" style="font-size: 12;color: red">ע��<br>
						1����ѡ���׼���������ѡ���׼��ϵͳ�л�����Ϣ������ȡѡ��������¼���������Ϣ����Ӧ���С�<br>
						2������ѡ�ϼ���������ѡ��Ӧ���л�����Ϣ������ȡ���ݻ����ڱ�Ӧ����û��ƥ�䵽��Ӧ���������ڱ�ѡ�ϼ������½�����Ӧ������<br>
						3������Ƭ��ַ��·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP��</label><br>
					</td>
				</tr>
				<tr>
					<odin:textIconEdit size="60" property="fabsolutepath" label="ѡ���׼�����" onchange="javascript:docheckt();" readonly="true" required="true"  ></odin:textIconEdit>
				</tr>
				<tr>
					<tags:PublicTextIconEdit4 onchange="setParentValue" width="365" property="searchDeptBtn" label="��ѡ�ϼ�����" readonly="true" required="true" codetype="orgTreeJsonData" />
				</tr>
				<tr>
					<odin:textEdit property="adress" size="60" label="��Ƭ��ַ:" value="D:/ȫ������Ա������Ϣϵͳ����׼�棩������/Client/Photos"></odin:textEdit>
				</tr>
				<tr>
					<td height="10px"></td>
				</tr>
			</table>
			<div style="width: 85%">
   				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   				 <input type="checkbox" checked="checked" id="fxz"/><span style="font-size: 12">���ա�����ְ��Ա��</span>
			</div>
			<div style="width:85%" align="right">
				<odin:button text="��������" property="impbtn"  ></odin:button>
			</div>
		
			
		
<%--
<odin:panel contentEl="tree-div" property="d">
<jsp:include page=""></jsp:include>
</odin:panel>
<div id="tree-div"></div>
<odin:groupBox title="�������">
<table>
	<tr align="center">
		<odin:textEdit property="psncount" readonly="true" label="��������"></odin:textEdit>
		<odin:textEdit property="orgcount" readonly="true" label="���������"></odin:textEdit>
	</tr>
</table>
<odin:hidden property="id"/>
<odin:editgrid property="Fgrid" autoFill="true" isFirstLoadData="false" height="200" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="info" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="name" width="100" header="�ļ�����"  align="left" />
		<odin:gridColumn dataIndex="status" width="50" renderer="rendererthis" header="״̬"  align="center" />
		<odin:gridColumn dataIndex="info" width="150" header="�ļ�·��" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
</odin:groupBox>
 --%>
<odin:window src="/blank.htm" id="winFile" width="550" height="400"
	title="ѡ���׼�����" />
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
	parent.odin.ext.getCmp('win1').hide();
	var id = document.getElementById('imprecordid').value;
	var file = document.getElementById('fabsolutepath').value;
	var dept = document.getElementById('searchDeptid').value;
	var param = '&initParams='+id+'||'+file+'||'+dept+'';
///	parent.doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.repandrec.local.KingbsWinfresh'+param,'���������ļ�����',600, 400, null);
	realParent.$h.openWin('simpleExpWin112','pages.repandrec.local.KingbsWinfresh'+param,'���������ļ�����',600,400,'11','<%=request.getContextPath()%>');
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