<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowSYQPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:hidden property="retireTime" value=""/>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="保存列表" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="退休人员提醒" id="tab3" isLast="true"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex="tab3">
<br>
<table>
<tr>
<td width="10"></td>
<td><odin:select property="type1" data="['3','本月退休'],['2','下月退休'],['1','一年内退休'],['0','已超过退休时间']" value="0" onchange="changeType()"></odin:select></td>
</tr>
</table>
<odin:editgrid property="persongrid1"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="355">
				<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true" />
					
					<odin:gridEditColumn header="姓名" edited="false" width="80" dataIndex="a0101" editor="text" align="center" />
					
					<odin:gridEditColumn header="性别" edited="false" width="80" dataIndex="a0104" editor="text" renderer="GB2261" align="center" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="80" dataIndex="a0107" editor="text" align="center"/>
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="410" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>


<script type="text/javascript">

function changeType(){
	 radow.doEvent("type1");
}

//页面右键禁用
document.oncontextmenu=rightMouse;
function rightMouse() {
	return false;
}
function GB2261(value,params,record,rowIndex,colIndex,ds){
	if(value == '1'){
		value = '男';
	}
	if(value == '2'){
		value = '女';
	}
	return value;
}
</script>
