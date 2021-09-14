<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit.jsp" %>
<style>
.uploadify{
position: absolute;
left: 0px;
top: 0px;
}


.x-fieldset{
	height:100%;
}

</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<div id="btnPanel"></div>
<div>
<odin:editgrid2 property="peopleGrid" hasRightMenu="false"  height="510" 
				title="" forceNoScroll="true" autoFill="true" pageSize="200"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="publishid" />
		<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck" edited="true" 
							hideable="false" gridName="peopleGrid" 
							/>
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="a0117"/>
		<odin:gridDataCol name="a0107"/>
		<odin:gridDataCol name="a0111a"/>
		<odin:gridDataCol name="a0192a"/>
		<odin:gridDataCol name="delperson"/>
		<odin:gridDataCol name="a0000"/>
		<odin:gridDataCol name="sortid"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn2 dataIndex="a0101" width="60" header="姓名" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="性别" editor="select" edited="false" codeType="GB2261" align="center" />
		<odin:gridEditColumn2 dataIndex="a0117" width="40" header="民族" editor="select" edited="false" codeType="GB3304" align="center" />
		<odin:gridEditColumn2 dataIndex="a0107" width="60" header="出生年月" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0111a" width="60" header="籍贯" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0192a" width="300" header="工作单位及职务" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="delperson" width="40" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="updateperson"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</div>
<odin:toolBar property="btnToolBar" applyTo="btnPanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="全部删除" icon="image/delete2.png" id="deleteAll" handler="deleteAll"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="公示导出ZIP包" icon="images/icon/table.gif" id="expzip" handler="expGSZipBtn" isLast="true"/>
</odin:toolBar>
<odin:hidden property="downfile"/>
<script type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';


function updateperson(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletepel('"+record.get("a0000")+"');\">删除</a></font>";
}

function deletepel(a0000){
	radow.doEvent('delperson',a0000);
}

function deleteAll(){
	if(confirm('确认删除所有公示列表人员？')){
		radow.doEvent('deleteAll');
    }
}

function expGSZipBtn(){
	radow.doEvent('expGSZipBtn');
}

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
}
Ext.onReady(function() {
	$h.initGridSort('peopleGrid',function(g){
		radow.doEvent('rolesort');
	});
});

</script>