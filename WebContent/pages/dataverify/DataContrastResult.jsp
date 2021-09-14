<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%-- 网上信访预处理 --%>   
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script type="text/javascript" src="basejs/helperUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
	<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
	<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>

</head>



<%
String subWinId = request.getParameter("subWinId");
String subWinIdBussessId = request.getParameter("subWinIdBussessId");
if(subWinIdBussessId!=null){
	subWinIdBussessId = new String(request.getParameter("subWinIdBussessId").getBytes("iso8859-1"),"utf8");
}
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var params = parent.Ext.getCmp(subWinId).initialConfig.param;
</script>
<odin:hidden property="subWinIdBussessId" value="<%=subWinIdBussessId %>"/>
<odin:hidden property="subWinIdBussessId2" value=""/>

<style type = "text/css">
#listDiv,.xx-fieldset{
/* border: 1px solid;
border-color: red; */


}
</style>

<script type="text/javascript">
<%
	String ctxPath = request.getContextPath();
%>

function setGridHeight(){
	var grid = odin.ext.getCmp('list1');
	//grid.setHeight(document.body.clientHeight-document.getElementById("QueryPanel_panel").offsetHeight);
	//grid.setHeight(document.body.clientHeight-document.getElementById("fieldsetCXTJ").offsetHeight-30);
}
 //打开（新开tab页面）       
function openView(){
	var obj=odin.ext.getCmp('list1').getSelectionModel().getSelections()[0];
	var storeid=obj.get('storeid');
	var iWidth=1150; //弹出窗口的宽度;
	var iHeight=550; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	$h.openWin('DataContrastResultWin','pages.dataverify.DataContrastResultWin&initParams='+ storeid,'数据比对结果窗口',680,380,storeid,'<%=request.getContextPath()%>');

 }

</script>
<odin:toolBar property="btnToolBar" applyTo="topbtn">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="查询" id="search"  />
	<odin:buttonForToolBar text="清空" id="clear"   isLast="true"/>
</odin:toolBar>


<div id="QueryContent" style="width:1022px;">
<div id="topbtn" ></div>
<fieldset class="xx-fieldset" id="fieldsetCXTJ">
	<odin:groupBox property="ggBox" title="搜索框">
		<odin:hidden property="imp_record_id"/><!-- [0102]导入记录信息表imp_record主键 -->
		<div id="searchTab" >
			<table width="98%">
				<tr>
					<odin:textEdit property="jgmc" label="机构名称" maxlength="32"></odin:textEdit>
					<odin:select property="dataResultType"  data="['4', '全部'],['0', '已删除'],['1', '一致'],['2', '新增'],['3', '已修改']" label="数据匹配结果"  ></odin:select>
				</tr>
				<tr>
				</tr>
			</table>
		</div>
	</odin:groupBox>
</fieldset>



<odin:toolBar property="btnToolBar1" >
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="打开" id="open"  cls="x-btn-text-icon2" icon="images/dk.png" handler="openView"/>
	<odin:buttonForToolBar text="刷新" id="refresh"  cls="x-btn-text-icon2" icon="images/sx.png" isLast="true"/>
</odin:toolBar>
<odin:editgrid  property="list1" sm="checkbox"  topBarId="btnToolBar1"  bbarId="pageToolBar"  rowDbClick="openView"  width="600" height="398"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="storeid"/>
		<odin:gridDataCol name="imp_record_id"/>
		<odin:gridDataCol name="b0111_n"/>
		<odin:gridDataCol name="b0111_w" />
		<odin:gridDataCol name="b0111_parent_n" />
		<odin:gridDataCol name="b0111_parent_w" />
		<odin:gridDataCol name="b0101_n" />
		<odin:gridDataCol name="b0101_w" />
		<odin:gridDataCol name="b0114_n" />
		<odin:gridDataCol name="b0114_w" />
		<odin:gridDataCol name="opptimetype" />
		<odin:gridDataCol name="comments" />
		<odin:gridDataCol name="opptime" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn width="30"/>
		<odin:gridColumn dataIndex="xfjcheck" header="selectall" gridName="list1" align="center" width="10" editor="checkbox" edited="true" />
		<odin:gridEditColumn dataIndex="storeid" header="数据比对结果表id" width="40"  edited="false"  editor="text" hidden="true"/>
		<odin:gridEditColumn dataIndex="imp_record_id" header="imp_record主键" width="70"  edited="false"  editor="text" hidden="true"/>
		<odin:gridEditColumn dataIndex="b0101_n" header="机构名称（内部）" width="60"  edited="false"  editor="text" hidden="false"/>
		<odin:gridEditColumn dataIndex="b0114_n" header="机构编码（内部）" width="40"  edited="false"  editor="text" hidden="false"/>
		<odin:gridEditColumn dataIndex="b0101_w" header="机构名称（外部）" width="60"  edited="false"  editor="text" hidden="false"/>
		<odin:gridEditColumn dataIndex="b0114_w" header="机构编码（外部）" width="40"  edited="false"  editor="text" hidden="false"/>
		<odin:gridEditColumn dataIndex="comments" header="比对结果描述" width="60"  edited="false"  editor="text" hidden="false"/>
		<odin:gridEditColumn dataIndex="opptimetype" header="比对结果" width="25"  edited="false" editor="select" selectData="['0','已删除'],['1','一致'],['2','新增'],['3','已修改']" />
		<odin:gridEditColumn dataIndex="opptime" header="操作时间" width="45"  edited="false"  editor="text" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>

<odin:window src="/blank.htm" id="dataContrastResultWin" width="800" height="593" title="比对结果详情"/>
