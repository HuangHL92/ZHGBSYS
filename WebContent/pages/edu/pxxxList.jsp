<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<odin:hidden property="listtype"/>
<odin:hidden property="xrdx01"/>
<odin:hidden property="sortid"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>
<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
<odin:toolBar property="btnToolBar" >
	<odin:fill />
	<odin:buttonForToolBar text="人员列表导出" icon="images/icon/table.gif"  id="export" handler="expExcelFromGrid" isLast="true"/> 
<%-- 	 <odin:buttonForToolBar text="人员条件查询" icon="image/icon021a2.gif" id="searchBtn" handler="rycx" /> 
    <odin:buttonForToolBar text="新增人员" icon="images/add.gif" id="combineBtn" handler="addRY" />  --%>
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
<%-- 	<odin:buttonForToolBar text="删除人员" icon="image/delete2.png" id="delBtn" handler="delRY" /> --%>
	
</odin:toolBar>
<table    style="width:100%">
	<tr>
		<td>
			<odin:editgrid2 property="editgrid" hasRightMenu="false"   topBarId="btnToolBar" title="" autoFill="true" pageSize="50" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel>
                         <odin:gridDataCol name="a0000"/>
                         <odin:gridDataCol name="a0101"/>
                         <odin:gridDataCol name="a0184"/>
                         <odin:gridDataCol name="a0165"/>
                         <odin:gridDataCol name="a0288"/>
                         <odin:gridDataCol name="a3707c"/>
                         <odin:gridDataCol name="b0101"/>
                         <odin:gridDataCol name="a0104"/>
                         <odin:gridDataCol name="a0107"/>
                         <odin:gridDataCol name="a0192a"/>
                         <odin:gridDataCol name="xrdx00"/>
                         <odin:gridDataCol name="xrdx01"/>
                         <odin:gridDataCol name="year"/>
                         <odin:gridDataCol name="xrdx02"/>
                         <odin:gridDataCol name="xrdx03"/>
                         <odin:gridDataCol name="xrdx04"/>
                         <odin:gridDataCol name="xrdx05"/>
                         <odin:gridDataCol name="xrdx06"/>
                         <odin:gridDataCol name="xrdx07"/>
                         <odin:gridDataCol name="xrdx08"/>
                         <odin:gridDataCol name="xrdx09"/>
                         <odin:gridDataCol name="xrdx10"/>
                         <odin:gridDataCol name="xrdx11"/>
                         <odin:gridDataCol name="xrdx12" isLast="true"/>

				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
                  <odin:gridEditColumn2 dataIndex="a0000" width="50" header="id" editor="text" edited="false" align="center" hidden="true"/>
                  <odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="a0104" width="30" header="性别" editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="a0107" width="50" header="出生年月" editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="a0165" width="50" header="管理类别"  editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="a3707c" width="50" header="手机号码" editor="text" edited="false" align="center"/> 
				  <odin:gridEditColumn2 dataIndex="a0184" width="70" header="身份证号" editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="b0101" width="50" header="任职机构" editor="text" edited="false" align="center"/>
				  <odin:gridEditColumn2 dataIndex="a0192a" width="120" header="任现职务" editor="text" edited="false" align="center"/>
		     	<odin:gridEditColumn2 dataIndex="a0288" width="50" header="任现职务<br/>层次时间" editor="text" edited="false" align="center"/>
                 <odin:gridEditColumn2 dataIndex="xrdx01" width="120" header="班次姓名" editor="text" edited="false" align="center" />
                 <odin:gridEditColumn2 dataIndex="year" width="50" header="年份" editor="text" edited="false" align="center" /> 
                  <odin:gridEditColumn2 dataIndex="xrdx02" width="50" header="班次类型" editor="text" edited="false" align="center" />     
                  <odin:gridEditColumn2 dataIndex="xrdx05" width="80" header="开班时间" editor="text" edited="false" align="center" />  
                  <odin:gridEditColumn2 dataIndex="xrdx06" width="80" header="结业时间" editor="text" edited="false" align="center" /> 
                  <odin:gridEditColumn2 dataIndex="xrdx08" width="30" header="学制" editor="text" edited="false" align="center" />  
                  <odin:gridEditColumn2 dataIndex="xrdx09" width="50" header="学时" editor="text" edited="false" align="center" />   
                  <odin:gridEditColumn2 dataIndex="xrdx10" width="100" header="培训地点" editor="text" edited="false" align="center" isLast="true"/>  
			  </odin:gridColumnModel>
	 </odin:editgrid2>
		</td>
	</tr>
</table>

</div>

<script>
Ext.onReady(function () {
//var data = parent.Ext.getCmp(subWinId).initialConfig.data
	if(parentParams.listtype){
	    document.getElementById("listtype").value=parentParams.listtype;
	}
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('editgrid');
	grid2.setHeight(viewSize.height/1.06);
	grid2.setWidth(viewSize.width);


})


var g_contextpath = '<%= request.getContextPath() %>';

function dateRenderer(val) {
	if(val!=null && val!=''){
		var length=val.length;
		if( val.length === 6 || val.length === 8){
	    	return val.substr(0, 4) + "." + val.substr(4, 2);
	    }else{
	    	return val;
	    }
	}else{
		return val;
	}
	
	
}
function expExcelFromGrid(){
	


    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('editgrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
/*     odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false); */

/* 
        excelName = "人员信息" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd");  */
    excelName ='培训信息人员名单';

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}

</script>
