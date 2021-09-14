<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<style>

input{
	border: 1px solid #c0d1e3 !important;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.ext-ie .x-grid3-cell-enable{
	height: auto !important;
}
</style>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<div id="groupTreePanel"></div>

<table>
<tr>
<td>
<odin:editgrid2 property="blackGrid"  hasRightMenu="false" autoFill="true" title="不显示列表" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0101_b" />
		<odin:gridDataCol name="a0104_b" />
		<odin:gridDataCol name="a0107_b" />
		<odin:gridDataCol name="a0201b_b"/>
		<odin:gridDataCol name="a0215a_b"/>
		<odin:gridDataCol name="a0000_b"/>
		<odin:gridDataCol name="a0200_b" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="a0101_b" width="100" header="姓名" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0104_b" width="50" header="性别" editor="select" edited="false" align="center" codeType="GB2261" />
		<odin:gridEditColumn2 dataIndex="a0107_b" width="80" header="出生年月" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0201b_b" width="230" header="工作单位" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0215a_b" width="200" header="职务名称" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="delperson" width="50" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="deleteBlack"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</td>
<td>
<odin:editgrid2 property="grayGrid"  hasRightMenu="false" autoFill="true" title="选择性显示列表" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0101_g" />
		<odin:gridDataCol name="a0104_g" />
		<odin:gridDataCol name="a0107_g" />
		<odin:gridDataCol name="a0201b_g"/>
		<odin:gridDataCol name="a0215a_g"/>
		<odin:gridDataCol name="a0000_g"/>
		<odin:gridDataCol name="a0200_g" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="a0101_g" width="100" header="姓名" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0104_g" width="50" header="性别" editor="select" edited="false" align="center" codeType="GB2261" />
		<odin:gridEditColumn2 dataIndex="a0107_g" width="80" header="出生年月" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0201b_g" width="210" header="工作单位" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0215a_g" width="200" header="职务名称" editor="text" edited="false" align="center"  />
		<odin:gridEditColumn2 dataIndex="delperson" width="50" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="deleteGray"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</td>
</tr>
</table>




<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:buttonForToolBar text="维护" icon="image/icon021a2.gif" handler="loadadd"  id="loadadd"  isLast="true" />
</odin:toolBar>
	
<script type="text/javascript">

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var blackGrid = Ext.getCmp('blackGrid');
	blackGrid.setHeight(viewSize.height-40);
	blackGrid.setWidth(viewSize.width/2-10);
	
	var grayGrid = Ext.getCmp('grayGrid');
	grayGrid.setHeight(viewSize.height-40);
	grayGrid.setWidth(viewSize.width/2-10);
});

function loadadd(){
	$h.openWin('loadadd','pages.fxyp.MNMDADD','名单维护',1400,1000,null,g_contextpath,null,
			null,true);
	$h.getTopParent().Ext.getCmp('loadadd').on('close',function(){
		radow.doEvent('blackGrid.dogridquery');
		radow.doEvent('grayGrid.dogridquery');
	})
}

function deleteBlack(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletepel('"+record.get("a0200_b")+"','1');\">删除</a></font>";
}

function deleteGray(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletepel('"+record.get("a0200_g")+"','2');\">删除</a></font>";
}

function deletepel(a0200,type){
	radow.doEvent('deletepel',a0200+"##"+type);
}

</script>
