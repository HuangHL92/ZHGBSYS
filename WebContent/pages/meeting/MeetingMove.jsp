<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.meeting.MeetingMovePageModel"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>

<script type="text/javascript" src="commform/basejs/json2.js"></script>
<odin:hidden property="sh000"/>
<odin:hidden property="flag" value='1'/>
<odin:hidden property="sh002" />
<odin:hidden property="type" value='<%=request.getParameter("type") %>'/>

<script type="text/javascript">
var type='<%=request.getParameter("type") %>';
function init(){//��ʼ������
	var sh000=parent.document.getElementById('sh000').value;
	document.getElementById("sh000").value=parent.document.getElementById('sh000').value;
	if(type==1){
		document.getElementById("zongshu").value=parent.document.getElementById('tp0111').value;
	}else if(type==2){
		document.getElementById("zongshu").value=parent.document.getElementById('tp0112').value;
	}
	radow.doEvent('initX',sh000);
}

function deleteZW(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deleteZW2('"+record.get("sh002")+"');\">ɾ��</a></font>";
}

function deleteZW2(sh002){
	radow.doEvent('deleteZW',sh002);
}

</script>
<div  align="center" id="TitleContent">
<table>
	<tr>
		<% 
			String type=request.getParameter("type"); 
			if("1".equals(type)){
		%>
				<odin:textEdit property="zongshu" label="��������" width="490" ></odin:textEdit>
		<% 
			}else if("2".equals(type)){
		%>
				<odin:textEdit property="zongshu" label="��������" width="490"  ></odin:textEdit>
		<% 
			}
		%>
		
	</tr>
	<tr>
		<td colspan='2'>
		<odin:groupBox property="group1" title="ְ����Ϣ" >
			<table style="width: 300;height: 280">
				<tr>
					<td rowspan="3">
						<odin:editgrid2 property="ZWGrid" hasRightMenu="false" bbarId="pageToolBar" height="250" title="" forceNoScroll="true" autoFill="true" pageSize="20"  url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="a0201a"/>
								<odin:gridDataCol name="a0201b"/>
								<odin:gridDataCol name="a0215a"/>
								<odin:gridDataCol name="sortid"/>
								<odin:gridDataCol name="sh002"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn2 dataIndex="a0201a" width="80" header="��ְ��������" editor="text" edited="false" align="center" />
								<odin:gridEditColumn2 dataIndex="a0201b" width="80" header="��ְ��������" editor="text" edited="false" hidden="true" align="center"/>
								<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="ְ������" editor="select" edited="false" codeType="GB3304" align="center" />
								<odin:gridEditColumn2 dataIndex="sortid" width="30" header="����" editor="text" edited="false" align="center"/>
								<odin:gridEditColumn2 dataIndex="delperson" width="30" header="����" editor="text" edited="false" align="center" isLast="true" renderer="deleteZW"/>
							</odin:gridColumnModel>
						</odin:editgrid2>
					</td>
					<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="��ְ����"  property="a0201b" defaultValue="" readonly="true"/>
				</tr>
				<tr>
					<odin:textEdit property="a0201a" label="��ְ��������" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="a0215a" label="ְ������" ></odin:textEdit>
				</tr>
			</table>
			</odin:groupBox>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar text="����" id="addZWBtn" icon="images/add.gif" handler="addZW"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" id="save"  icon="images/save.gif" handler="saveZW"  isLast="true"></odin:buttonForToolBar>
	<%-- <odin:buttonForToolBar text="������������" id="updateZS" isLast="true" icon="images/save.gif" ></odin:buttonForToolBar>--%>
</odin:toolBar>
<odin:panel contentEl="TitleContent" property="TitlePanel" topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
function addZW(){
	document.getElementById("a0201b").value="";
	document.getElementById("a0201b_combo").value="";
	
	document.getElementById("a0201a").value="";
	document.getElementById("a0215a").value="";
	document.getElementById("flag").value="1";
	document.getElementById("sh002").value="";
}

function saveZW(){
	var a0201a = document.getElementById('a0201a').value;		//��ְ��������
	var a0201b = document.getElementById('a0201b').value;
	var a0215a = document.getElementById('a0215a').value;//ְ������
	if(a0201b==""){
		Ext.Msg.alert("ϵͳ��ʾ","������ְ���б�ѡ��ְ��");
		return;
	}
	
	if(a0201a==""){
		Ext.Msg.alert("ϵͳ��ʾ","��ְ�������Ʋ���Ϊ�գ�");
		return;
	}
	
	if(a0215a==""){
		Ext.Msg.alert("ϵͳ��ʾ","ְ�����Ʋ���Ϊ�գ�");
		return;
	}
	
	radow.doEvent('save');
}

function a0201bChange(record){
	radow.doEvent("a0201bChange");
}

Ext.onReady(function(){
	$h.initGridSort('ZWGrid',function(g){
	    radow.doEvent('rolesort');
	  });
}); 

</script>
</html>