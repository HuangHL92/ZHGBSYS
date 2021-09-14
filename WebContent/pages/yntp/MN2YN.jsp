<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:buttonForToolBar text="查看人选方案" icon="image/icon021a3.gif" handler="infoView" isLast="true" id="infoView" />
</odin:toolBar>
<div id="groupTreePanel"></div>
<odin:hidden property="mntp00s"/>
<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid property="noticeSetgrid" hasRightMenu="false" autoFill="false"   pageSize="200" isFirstLoadData="false" url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="mntp00" />
						<odin:gridDataCol name="mntp01"/>
						<odin:gridDataCol name="mntp02"/>
						<odin:gridDataCol name="mntp03"/>
						<odin:gridDataCol name="mntp05"/>
						<odin:gridDataCol name="mnur04"/>
						<odin:gridDataCol name="mntp04" isLast="true"/>
					</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 header="selectall" width="80" menuDisabled="true"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="noticeSetgrid" 
							/>
							<odin:gridColumn dataIndex="mntp04" width="440" header="名称" editor="text" edited="false" align="center"/>
							<odin:gridColumn dataIndex="mnur04" width="130" header="来源用户" isLast="true" align="left"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid>
		</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	document.getElementById('grid').style.width = document.body.clientWidth;
	Ext.getCmp('noticeSetgrid').setWidth(document.body.clientWidth);
	Ext.getCmp('noticeSetgrid').setHeight(document.body.clientHeight);
	
	
});
var g_contextpath = '<%= request.getContextPath() %>';
function infoView(){
	var grid = Ext.getCmp('noticeSetgrid');
	var store = grid.getStore();
	var length = store.getCount();
	var mntp00s='';
	for (var i = 0; i < length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		if (record.personcheck) {
			mntp00s = mntp00s + record.mntp00 + ',';
		}
	}
	if (mntp00s == '') {
		odin.alert("请选择方案！");
		return;
	}
	mntp00s = mntp00s.substring(0, mntp00s.length - 1);
	$("#mntp00s").val(mntp00s);
	$h.openPageModeWin('infoView','pages.yntp.mn2yn.RXFXYP','干部模拟调配人选分析研判表',1250,900,
			{mntp00s:mntp00s,scroll:"scroll:yes;"},g_contextpath);
}
</script>
