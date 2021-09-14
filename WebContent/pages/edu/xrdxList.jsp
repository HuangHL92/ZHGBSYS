<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<odin:hidden property="xrdx00"/>
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
	<odin:buttonForToolBar text="人员列表导出" icon="images/icon/table.gif"  id="export" handler="expExcelFromGrid" /> 
<%--  	 <odin:buttonForToolBar text="人员条件查询" icon="image/icon021a2.gif" id="searchBtn" handler="rycx" />   --%>
    <odin:buttonForToolBar text="新增人员" icon="images/add.gif" id="combineBtn" handler="addRY" isLast="true"/> 
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
<%-- 	<odin:buttonForToolBar text="删除人员" icon="image/delete2.png" id="delBtn" handler="delRY" /> --%>
	
</odin:toolBar>
<table    style="width:100%">
	<tr>
		<td>
			<odin:editgrid2 property="editgrid" hasRightMenu="false"   topBarId="btnToolBar" title="" autoFill="true" pageSize="50" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="a0104" />
				<odin:gridDataCol name="a0107" />
				<odin:gridDataCol name="a0111a" />
				<odin:gridDataCol name="a0141" />
				<odin:gridDataCol name="a0184" />
				<odin:gridDataCol name="a3707c" />
				<odin:gridDataCol name="b0101" />
				<odin:gridDataCol name="a0192a" />
				<odin:gridDataCol name="sortid" />
				<odin:gridDataCol name="a0288" />
				<odin:gridDataCol name="a0192f" />
				<odin:gridDataCol name="xrdx08" />
				<odin:gridDataCol name="xrdx09" />
				<odin:gridDataCol name="xrdx083" />
				<odin:gridDataCol name="xrdx093" />
				<odin:gridDataCol name="xrdx085" />
				<odin:gridDataCol name="xrdx095" />
				<odin:gridDataCol name="del" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
				<odin:gridEditColumn2 header="姓名" align="center" edited="false" width="80" dataIndex="a0101" editor="text"   />
				<odin:gridEditColumn2 header="性别" align="center" edited="false" width="40" dataIndex="a0104" editor="select"  codeType="GB2261"/>
				<odin:gridEditColumn2 header="任职机构" align="center" edited="false" width="160" dataIndex="b0101" editor="text"  />
				<odin:gridEditColumn2 header="任现职务" align="center" edited="false" width="240" dataIndex="a0192a" editor="text"  />
				<odin:gridEditColumn2 header="出生日期" align="center" edited="false"  width="120" dataIndex="a0107" editor="text" renderer="dateRenderer" />
				<odin:gridEditColumn2 header="籍贯" align="center" edited="false" width="80" dataIndex="a0111a" editor="text"   />
				<odin:gridEditColumn2 header="政治面貌" align="center" edited="false" width="100" dataIndex="a0141" editor="select"  codeType="GB4762"  />
				<odin:gridEditColumn2 header="任现职务时间" align="center" edited="false" width="120" dataIndex="a0192f" editor="text"  renderer="dateRenderer" />
				<odin:gridEditColumn2 header="任现职务层次时间" align="center" edited="false" width="120" dataIndex="a0288" editor="text"  renderer="dateRenderer" />
				<odin:gridEditColumn2 header="身份证号" align="center" edited="false" width="120" dataIndex="a0184" editor="text"   />
				<odin:gridEditColumn2 header="手机号" align="center" edited="false" width="120" dataIndex="a3707c" editor="text"   />
				<odin:gridEditColumn2 header="合计天数" align="center" edited="false" width="80" dataIndex="xrdx08" editor="text"   />
				<odin:gridEditColumn2 header="合计学时" align="center" edited="false" width="80" dataIndex="xrdx09" editor="text"   />
				<odin:gridEditColumn2 header="近三年合计天数" align="center" edited="false" width="80" dataIndex="xrdx083" editor="text"  hidden="true"  />
				<odin:gridEditColumn2 header="近三年合计学时" align="center" edited="false" width="80" dataIndex="xrdx093" editor="text"  hidden="true"  />
				<odin:gridEditColumn2 header="近五年合计天数" align="center" edited="false" width="80" dataIndex="xrdx085" editor="text"  hidden="true"  />
				<odin:gridEditColumn2 header="近五年合计学时" align="center" edited="false" width="80" dataIndex="xrdx095" editor="text" hidden="true"   />
			  	<odin:gridEditColumn2 header="操作" align="center" edited="false" width="120" dataIndex="del" editor="text" hidden="true" renderer="delry" isLast="true"/>
			  </odin:gridColumnModel>
	 </odin:editgrid2>
		</td>
	</tr>
</table>

</div>

<script>
Ext.onReady(function () {
//var data = parent.Ext.getCmp(subWinId).initialConfig.data
	if(parentParams.xrdx00){
	    document.getElementById("xrdx00").value=parentParams.xrdx00;
	}
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('editgrid');
	grid2.setHeight(viewSize.height/1.06);
	grid2.setWidth(viewSize.width);


})

function delry(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"dodel('"+record.get("a0000")+"','"+record.get("sortid")+"');\">删除人员</a></font>";
}
var g_contextpath = '<%= request.getContextPath() %>';
function dodel(a0000,sortid){
	document.getElementById('sortid').value=sortid;
	if(a0000==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$h.confirm("系统提示：","是否确认删除？",200,function(id) { 
		if("ok"==id){
			radow.doEvent("dodel",a0000);
		}else{
			return false;
		}		
	});
}
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
    excelName =document.getElementById('xrdx01').value+'人员名单';

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}
function rycx(){
	var xrdx00 = document.getElementById('xrdx00').value;
	$h.openWin('RYCX','pages.gwdz.RYCX','人员查询',1500,731,null,'<%= request.getContextPath() %>',null,
			{xrdx00:xrdx00},true);
    //$(".table tr:nth-of-type(2n)").css("font-family:SimHei!important")
}
function addRY(){
	var xrdx00 = document.getElementById('xrdx00').value;
	if(xrdx00==''){
		$h.alert('系统提示','请选择班次！');
		return;
	}
	$h.openPageModeWin('addperson','pages.edu.xrdxChoose','新增人员',1020,520,{xrdx00:''},g_contextpath);
}
function queryPerson(){
	radow.doEvent("editgrid.dogridquery");
}
function callback(param) {
	$h.getTopParent().Ext.getCmp('RYCX').close()
	radow.doEvent("TJsave",param);
}

</script>
