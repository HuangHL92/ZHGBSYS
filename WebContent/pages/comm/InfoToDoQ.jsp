<%@page import="com.insigma.siis.local.epsoft.config.AppConfig"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit3.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';

function opFunction(value, params, record, rowIndex, colIndex, ds) {
	var itdc003 = record.get('itdc003');
	var itdc004 = record.get('itdc004');
	var itdc005 = record.get('itdc005');
	var itdc008 = record.get('itdc008');
	var itdr013 = record.get('itdr013');
	var link = "<a href=\"javascript:void()\" onclick=\"bl('"+itdc003
			+"','"+itdc004+"','"+itdc005+"','"+itdc008+"','"+itdr013+"')\">办理</a>";
	return link;
}

function bl(itdc003, itdc004, itdc005, itdc008, itdr013){
	var p = "&mainXdbid="+itdr013;
	if(itdc003=='tab'){
		var tab=parent.parent.tabs.getItem(itdc005);
		if (tab){
			parent.document.getElementById('I'+itdc005).src = g_contextpath + itdc004 +p;
			parent.parent.tabs.activate(tab);
	    }else{
	    	setTimeout("parent.addTab1('"+itdc005+"','"+itdc008+"','"+itdc004+p+"')",10 );   //综合查询
	    }
	} else {
		
	}
}

</script>
<div id="groupTreePanel"></div>
<odin:groupBox title="查询条件">
<table style="width: 100%;">
	<tr>
		<odin:select2 property="qtype" canOutSelectList="false" label="代办类型" ></odin:select2>
		<odin:dateEdit property="itdr003" label="提交时间" format="Ymd" maxlength="8" ></odin:dateEdit>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid"  hasRightMenu="false" title="批次信息" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="itdc001" />
		<odin:gridDataCol name="itdc002"/>
		<odin:gridDataCol name="itdc003"/>
		<odin:gridDataCol name="itdc004"/>
		<odin:gridDataCol name="itdc005"/>
		<odin:gridDataCol name="itdc008"/>
		
		<odin:gridDataCol name="itdr001"/>
		<odin:gridDataCol name="itdr002"/>
		<odin:gridDataCol name="itdr003"/>
		<odin:gridDataCol name="itdr013"/>
		<odin:gridDataCol name="itdr014"/>
		
		<odin:gridDataCol name="itdr000" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="itdc003" width="50" hidden="true" header="打开方式" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="itdc004" width="50" hidden="true" header="打开链接" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="itdc005" width="50" hidden="true" header="打开编码" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="itdc008" width="50" hidden="true" header="打开标题" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="itdr013" width="50" hidden="true" header="办理事项" editor="text" edited="false" align="center"/>
		
		
		<odin:gridEditColumn2 dataIndex="itdc002" width="200" header="代办类型" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="itdr002" width="100" header="发起人" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="itdr003" width="100" header="发起时间" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="itdr014" width="100" header="办理事项" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="op" width="140" header="办理" editor="text" renderer="opFunction" isLast="true" edited="false"  align="center" isLast="true"  />
	</odin:gridColumnModel>
</odin:editgrid2>


<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>代办信息</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" handler="infoSearch" />
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<script type="text/javascript">
var infoSearch = function(){
	radow.doEvent("memberGrid.dogridquery");
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var record = gridobj.getStore().getAt(index);
		var itdc003 = record.get('itdc003');
		var itdc004 = record.get('itdc004');
		var itdc005 = record.get('itdc005');
		var itdc008 = record.get('itdc008');
		var itdr013 = record.get('itdr013');
		bl(itdc003,itdc004,itdc005,itdc008,itdr013);
	});
	
});

</script>


