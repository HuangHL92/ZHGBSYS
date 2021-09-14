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


<odin:hidden property="b0111"/>
<odin:hidden property="b0101"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="query_type"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>
<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid property="noticeSetgrid" hasRightMenu="false" autoFill="false"  bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="a0192a"/>
						 	<odin:gridDataCol name="b0101" isLast="true" /> 
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="120" header="姓名" align="center"/>
							<odin:gridColumn dataIndex="a0192a" width="550" header="工作单位及职务"  />
						 	<odin:gridColumn dataIndex="b0101" hidden="true" header="班子名称" align="center" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid>
		</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	document.getElementById('grid').style.width = document.body.clientWidth;
	Ext.getCmp('noticeSetgrid').setWidth(document.body.clientWidth-5);
	Ext.getCmp('noticeSetgrid').setHeight(document.body.clientHeight-5);
	
	
	$('#b0111').val(parent.Ext.getCmp(subWinId).initialConfig.b0111);
	$('#b0101').val(parent.Ext.getCmp(subWinId).initialConfig.b0101);
	$('#colIndex').val(parent.Ext.getCmp(subWinId).initialConfig.colIndex);
	$('#query_type').val(parent.Ext.getCmp(subWinId).initialConfig.query_type);
	
	
	
	var pgrid = Ext.getCmp('noticeSetgrid');



	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[new Ext.Button({
			icon : 'images/icon/table.gif',
			id:'getAll',
		    text:'导出Excel',
		    handler:expExcelFromGrid
		})
		]);
	
});
function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function expExcelFromGrid(){

    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('noticeSetgrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
    odin.grid.menu.expExcelFromGrid('noticeSetgrid', excelName, null,null, false);
}
</script>
