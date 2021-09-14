<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

 <div id="girdDiv" style="width: 100%;margin:0;">
 	<table>
        <tr>
        	<odin:select2 property="year"  label="年份"  />
 			<td align="right">&nbsp;</td>
 			<odin:textEdit property="xrdx01"  label="班次名称"  />
 			<td align="right">&nbsp;</td>
    		<odin:select2  property="type" label="选调类型"  data="['1', '上级选调'],['2', '本级选调'],['3', '其他培训']" ></odin:select2>
    		<td align="right">&nbsp;</td>
    		<tags:ComBoxWithTree property="bjtype" codetype="PXLX"  width="150"  label="班级类型"  />
    		<td align="right">&nbsp;</td>
        </tr>
    </table>
    <table class="x-form-item2" style="background-color: rgb(209,223,245); border-left: 1px solid rgb(153,187,232);
	border-top: 1px solid rgb(153,187,232); border-right: 1px solid rgb(153,187,232);top: 0px;width: 100%;">
         <tr>
          	<td align="right" style="width:5%;">
                 <odin:button text="&nbsp;清&nbsp;&nbsp;空&nbsp;" property="clean" handler="clean"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;查&nbsp;&nbsp;询&nbsp;" property="BZQuery" handler="BZQuery"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;新&nbsp;&nbsp;增&nbsp;" property="BZAdd" handler="BZAdd"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;导&nbsp;&nbsp;出&nbsp;" property="export" handler="expExcelFromGrid"></odin:button>
             </td>
<%--              <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;修&nbsp;&nbsp;改&nbsp;" property="BZUpdate" handler="BZUpdate"></odin:button>
             </td> --%>
<%--              <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;删&nbsp;&nbsp;除&nbsp;" property="BZDel"  handler="BZDel"></odin:button>
             </td> --%>

             <td align="right">&nbsp;</td>
         </tr>
     </table>
     <table style="width: 100%;">
          <tr>
              <td>
                  <odin:editgrid2 property="editgrid" hasRightMenu="false" topBarId="btnToolBar" width="100%" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
                      <odin:gridJsonDataModel >
                          <odin:gridDataCol name="xrdx00"/>
                          <odin:gridDataCol name="year"/>
                          <odin:gridDataCol name="type"/>
                          <odin:gridDataCol name="xrdx01"/>
                          <odin:gridDataCol name="xrdx02"/>
                          <odin:gridDataCol name="xrdx03"/>
<%--                           <odin:gridDataCol name="xrdx04"/> --%>
                          <odin:gridDataCol name="xrdx05"/>
                          <odin:gridDataCol name="xrdx06"/>
                          <odin:gridDataCol name="xrdx07"/>
                          <odin:gridDataCol name="xrdx08"/>
                          <odin:gridDataCol name="xrdx09"/>
                          <odin:gridDataCol name="xrdx10"/>
                          <odin:gridDataCol name="xrdx11"/>
                          <odin:gridDataCol name="xrdx12"/>
                          <odin:gridDataCol name="status"/>
                          <odin:gridDataCol name="caozuo" isLast="true"/>
                      </odin:gridJsonDataModel>
                      <odin:gridColumnModel>
                          <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                          <odin:gridEditColumn2 dataIndex="xrdx00" width="50" header="id" editor="text" edited="false" align="center" hidden="true"/>
                          <odin:gridEditColumn2 dataIndex="year" width="50" header="年份" editor="text" edited="false" align="center"/>
                          <odin:gridEditColumn2 dataIndex="type" width="50" header="选调类型" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="xrdx01" width="180" header="班级名称" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="xrdx02" width="80" header="班级类型" editor="text" edited="false" align="center" />
						  <odin:gridEditColumn2 dataIndex="xrdx03" width="100" header="主办单位" editor="text" edited="false" align="center" />
<%-- 						  <odin:gridEditColumn2 dataIndex="xrdx04" width="50" header="是否<br/>出国培训" editor="select" edited="false" align="center" codeType="TRANCRJ"/> --%>
						  <odin:gridEditColumn2 dataIndex="xrdx05" width="80" header="开班时间" editor="text" edited="false" align="center" renderer="dateRenderer"/>
						  <odin:gridEditColumn2 dataIndex="xrdx06" width="80" header="结业时间" editor="text" edited="false" align="center" renderer="dateRenderer"/>
						  <odin:gridEditColumn2 dataIndex="xrdx07" width="80" header="杭州参训<br/>人数" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx08" width="40" header="学制" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx09" width="40" header="学时" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx10" width="100" header="培训地点" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx11" width="80" header="培训组织" editor="text" edited="false" align="center" />
						  <odin:gridEditColumn2 dataIndex="xrdx12" width="60" header="办班级别" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="status" width="80" header="班级状态" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="caozuo" width="100" header="操作" editor="text" edited="false" isLast="true" align="center" renderer="updateDel"/>
                      </odin:gridColumnModel>
                  </odin:editgrid2>
              </td>
          </tr>
      </table>
 
 </div>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('editgrid');
	grid2.setHeight(viewSize.height/1.167);
})
function dateRenderer(val) {
	if(val!=null && val!=''){
		var length=val.length;
	    if ( val.length === 8) {
	        return val.substr(0, 4) + "." + val.substr(4, 2)+ "." +val.substr(6, 2);
	    } else {
	        return val;
	    }
	}else{
		return val;
	}
	
	
}
function updateDel(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"updateBZ('"+record.get("xrdx00")+"');\">更新</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style='cursor:pointer;' onclick=\"delBZ('"+record.get("xrdx00")+"');\">删除</a></font>";
}
function updateBZ(xrdx00){
	if(xrdx00==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	// $h.openPageModeWin('updateTrain', 'pages.train.HandleTrain', '查看(修改)班级信息', 651, 350, trainid, g_contextpath);
	 $h.openPageModeWin('addXRDX','pages.edu.xrdxAdd','新增班次',651,400,xrdx00,g_contextpath);
	//$h.openPageModeWin('impFuJian','pages.train.ImpFuJian','上传附件',500,300,{trainid:trainid},g_contextpath);
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
    excelName ="班次名单";

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}
function delBZ(xrdx00){
	if(xrdx00==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$h.confirm("系统提示：","是否确认删除？",200,function(id) { 
		if("ok"==id){
			radow.doEvent("delBZ",xrdx00);
		}else{
			return false;
		}		
	});
}
var g_contextpath = '<%= request.getContextPath() %>';
function clean(){
	document.getElementById("year").value ='';
	document.getElementById("year_combo").value ='';
	document.getElementById("xrdx01").value ='';
	document.getElementById("type").value ='';
	document.getElementById("type_combo").value ='';
	document.getElementById("bjtype").value ='';
	document.getElementById("bjtype_combotree").value ='';

}
function BZQuery(){
    radow.doEvent("editgrid.dogridquery");
}

function BZAdd(){
	$h.openPageModeWin('addXRDX','pages.edu.xrdxAdd','新增班次',651,500,'',g_contextpath);

}
function BZDel(){

}

 </script>
 