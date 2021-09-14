<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerify2PageModel" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>	
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" handler="executeCheck" text="执行校验" icon="images/icon/right.gif"/>
	<odin:buttonForToolBar id="btnSaveUpdated"  text="导出结果" handler="expExcelFromGrid" icon="images/icon/exp.png"  isLast="true" />
</odin:toolBar>
<div id="tooldiv" style="width:100%;"></div>
<div id="floatRight">
<odin:hidden property="table" title="表名" ></odin:hidden>
 <table >
		<tr>
<%-- <odin:select2 property="a0163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否在职"  data="[0,'全部'],['1','在职']" value="1" width="40"/>	--%>		
    <td>   <odin:select2 property="TABLE_CODE" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择信息集"  width="200"/></td>
		<!-- <td width="50"></td> -->
<%-- <tags:PublicTextIconEdit4  property="searchDeptBtn" label="选择机构" readonly="true" codetype="orgTreeJsonData"/> --%>		
		</tr>
		</table> 
		
		<odin:editgrid property="editgrids" bbarId="pageToolBar" sm="row" forceNoScroll="true" pageSize="20" remoteSort="true" isFirstLoadData="false" height="535" >
			<odin:gridJsonDataModel  id="id" root="data">
					<odin:gridDataCol name="rownum" />
		     		<odin:gridDataCol name="p_a0000b0111" />
		     		<odin:gridDataCol name="p_tablename" />
		     		<odin:gridDataCol name="p_codename" />
		     		<odin:gridDataCol name="p_hingename" />
		     		<odin:gridDataCol name="p_hingevalue" />
		     		<odin:gridDataCol name="p_codevalue" />
		     		<odin:gridDataCol name="p_texterror" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridEditColumn align="center" width="15" header="序号" dataIndex="rownum" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="0" header="主键" dataIndex="p_a0000b0111" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn align="center" width="50" header="表名" dataIndex="p_tablename" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="45" header="字段名" dataIndex="p_codename" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="40" header="姓名/单位名" dataIndex="p_hingename" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="60" header="身份证/机构编码" dataIndex="p_hingevalue" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="30" header="字段值" dataIndex="p_codevalue" editor="text" edited="false"/>
				  <odin:gridEditColumn align="center" width="70" header="错误信息" editor="text" dataIndex="p_texterror" edited="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
			</odin:gridJsonData>
		</odin:editgrid>
</div>
<script type="text/javascript">
Ext.onReady(function(){
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	Ext.getCmp("editgrids").setHeight(height-60);
	Ext.getCmp("editgrids").setWidth(width);
}); 


var contextPath='<%=request.getContextPath() %>';
function executeCheck(){
	var table = document.getElementById('TABLE_CODE').value;
	radow.doEvent('executeCheck',table);
}


function expExcelFromGrid(){
	var excelName = null;
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('editgrids');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	
	var length = dstore.getCount();
	if(length==0){
		$h.alert('系统提示：','没有要导出的数据！',null,180);
		return;
	}
	excelName = "单项校核" + "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('editgrids', excelName, null,null, false);
	
}  

</script>