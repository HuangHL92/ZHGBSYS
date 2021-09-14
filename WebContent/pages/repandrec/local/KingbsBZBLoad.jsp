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
						<label id="bz1" style="font-size: 12;color: red">注：<br>
						1、“选择标准版机构”，选择标准版系统中机构信息，将抽取选择机构及下级机构的信息到本应用中。<br>
						2、“备选上级机构”，选择本应用中机构信息，若抽取数据机构在本应用中没有匹配到对应机构，则在备选上级机构下建立对应机构。<br>
						3、“照片地址”路径以反斜杠“/”作为分隔符（如：D:/TFTP）</label><br>
					</td>
				</tr>
				<tr>
					<odin:textIconEdit size="60" property="fabsolutepath" label="选择标准版机构" onchange="javascript:docheckt();" readonly="true" required="true"  ></odin:textIconEdit>
				</tr>
				<tr>
					<tags:PublicTextIconEdit4 onchange="setParentValue" width="365" property="searchDeptBtn" label="备选上级机构" readonly="true" required="true" codetype="orgTreeJsonData" />
				</tr>
				<tr>
					<odin:textEdit property="adress" size="60" label="照片地址:" value="D:/全国公务员管理信息系统（标准版）单机版/Client/Photos"></odin:textEdit>
				</tr>
				<tr>
					<td height="10px"></td>
				</tr>
			</table>
			<div style="width: 85%">
   				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   				 <input type="checkbox" checked="checked" id="fxz"/><span style="font-size: 12">接收“非现职人员”</span>
			</div>
			<div style="width:85%" align="right">
				<odin:button text="导入数据" property="impbtn"  ></odin:button>
			</div>
		
			
		
<%--
<odin:panel contentEl="tree-div" property="d">
<jsp:include page=""></jsp:include>
</odin:panel>
<div id="tree-div"></div>
<odin:groupBox title="导入情况">
<table>
	<tr align="center">
		<odin:textEdit property="psncount" readonly="true" label="导入人数"></odin:textEdit>
		<odin:textEdit property="orgcount" readonly="true" label="导入机构数"></odin:textEdit>
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
		<odin:gridColumn dataIndex="name" width="100" header="文件名称"  align="left" />
		<odin:gridColumn dataIndex="status" width="50" renderer="rendererthis" header="状态"  align="center" />
		<odin:gridColumn dataIndex="info" width="150" header="文件路径" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
</odin:groupBox>
 --%>
<odin:window src="/blank.htm" id="winFile" width="550" height="400"
	title="选择标准版机构" />
<odin:window src="/blank.htm" id="winfresh" width="550" height="400"
	title="导入数据文件进度" />
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
///	parent.doOpenPupWin('/radowAction.do?method=doEvent&pageModel=pages.repandrec.local.KingbsWinfresh'+param,'导入数据文件进度',600, 400, null);
	realParent.$h.openWin('simpleExpWin112','pages.repandrec.local.KingbsWinfresh'+param,'导入数据文件进度',600,400,'11','<%=request.getContextPath()%>');
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