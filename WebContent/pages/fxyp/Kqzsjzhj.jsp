<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/field-to-grid-dd.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/field-to-grid-dd2.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style type="text/css">
label,td{
	font-size: 12px;
}
.x-grid3-col{
	vertical-align: middle!important;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.x-grid-record-nm{
	background: #ccccff!important;
}
.x-grid-record-zr{
	background: #ff5555!important;
}
.x-grid-record-ytp{
	background: #84bf96;
}
.x-grid-record-zr span,.x-grid-record-zr div{
	color: white;
}
.x-grid-record-ytp span,.x-grid-record-ytp div{
	color: white;
}
.top_btn_style{
	float:left;
	display:block;
	border-radius:5px;
	cursor:pointer;
	margin-left:6px;
	height:25px;
	line-height:14px;
	vertical-align:middle;
	font-size:12px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
	padding: 3px 5px!important;
}

.x-drop-target-active {
	background-color: #D88!important;
}
.x-drop-target-active2 {
	background-color: rgb(50,148,246)!important;
}
.mdf {
	background-color: #D18;
}
.OrgInfo{
	border: 1px solid #c0d1e3 ;
	font-size: 12px;
	overflow: auto;
}
.OrgInfo .p1{
	text-indent: -7em;
	padding-left: 6em;
	margin: 10px 0px;
}
.OrgInfo .p2{
	text-indent: -6em;
	padding-left: 6em;
	margin: 10px 0px;
}
#gwInfo input,#mntpbzInfo input{
	border: 1px solid #c0d1e3 !important;
}
/*反查样式  */
.reverse-search{
	cursor: pointer;
}
.rcbgcolor{
	background-color: #98FB98;
}
</style>
<script type="text/javascript">
function searchn(){
	NoticeSetgridScroll = 0;
	radow.doEvent("noticeSetgrid.dogridquery");
}
function selectORG(){
	var url = g_contextpath + "/pages/fxyp/OrgConfig.jsp?";
	$h.showWindowWithSrc("setOrgConfig",url,"设置显示单位", 400, 730);
}
function showECharts(b01id,b0111){
	$('#b01id').val(b01id);
	$('#b0111').val(b0111);
	/* var newWin_ = $h.getTopParent().Ext.getCmp('structuralAnalysis');
	if(newWin_){
		newWin_.show();
		$h.getTopParent().document.getElementById('iframe_structuralAnalysis').contentWindow.submitForm();
		return;
	} */
	var url = g_contextpath + "/pages/fxyp/StructuralAnalysis.jsp?mntp00="+$('#mntp00').val()+"&b01id="+b01id;
	$h.showWindowWithSrc("structuralAnalysis",url,"图标分析", 1400, 700,null,{closeAction:'close'},true);
}
function showET(value, params, record, rowIndex, colIndex, ds) {
	
	//if($('#mntp05').val()=='2'){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"showECharts('"+record.data.b01id+"','"+record.data.b0111+"');\">"+value+"</a>"
		+ "</font></div>";
	//}else{
	//	return value;
	//}
	
	
}
</script>
<odin:hidden property="mntp01"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="b0111"/>
<odin:hidden property="a0000s"/>
<odin:hidden property="a0000sBD"/>
<odin:hidden property="a0200s"/>
<odin:hidden property="b01id"/>
<div id="grid-example"></div>




<odin:toolBar property="addMNOrgToolBar" >
	<odin:fill />
	<odin:buttonForToolBar text="新建岗位" icon="images/add.gif" id="addMNGW" handler="openXJGW" />
	<odin:buttonForToolBar text="新建单位" icon="images/add.gif" id="addMNOrg" handler="addMNOrg" isLast="true"/>
</odin:toolBar>
<table style="width: 100%;">
  <tr>
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<odin:textEdit property="dwmc" width="40" label="单位名称"/>
				<td><odin:button text="查询" property="searchn" handler="searchn" /></td>
				<td><odin:button text="选择单位" property="selectORG" handler="selectORG" /></td>
				<td class="quxianshi" style="display: none;">
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="2" checked>区县市</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="3">区县市平台</label>
					<br/>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="1">市直单位</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="4">国企高校</label>
				</td>
				
			</tr>
		</table>

		<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid2 property="noticeSetgrid" hasRightMenu="false"  load="refreshNoticeSetgrid" topBarId="addMNOrgToolBar"
			   pageSize="300" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="jgmc" />
							<odin:gridDataCol name="zzhd" />
							<odin:gridDataCol name="zzsp" />
							<odin:gridDataCol name="fzhd" />
							<odin:gridDataCol name="fzsp" />
							<odin:gridDataCol name="zshd" />
							<odin:gridDataCol name="zssp" />
							<odin:gridDataCol name="bzzs" />
							<odin:gridDataCol name="b0111" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="bz" />
							<odin:gridDataCol name="b0234" />
							<odin:gridDataCol name="b0234_ygrx" />
							<odin:gridDataCol name="b0234_yggw" />
							<odin:gridDataCol name="b0235" />
							<odin:gridDataCol name="b0235_ygrx" />
							<odin:gridDataCol name="b0235_yggw" />
							<odin:gridDataCol name="yxgw" />
							
							
							
							<%-- 区县 --%>
							<odin:gridDataCol name="fyqp" />
							<odin:gridDataCol name="jcyqp" />
							<odin:gridDataCol name="bzqpdw" />
							<odin:gridDataCol name="bzqpzf" />
							<odin:gridDataCol name="bzqprd" />
							<odin:gridDataCol name="bzqpzx" />
							<odin:gridDataCol name="fyqp_ygrx" />
							<odin:gridDataCol name="jcyqp_ygrx" />
							<odin:gridDataCol name="bzqpdw_ygrx" />
							<odin:gridDataCol name="bzqpzf_ygrx" />
							<odin:gridDataCol name="bzqprd_ygrx" />
							<odin:gridDataCol name="bzqpzx_ygrx" />
							
							<odin:gridDataCol name="zjqp_ygrx" />
							
							
							<odin:gridDataCol name="fyqp_yggw" />
							<odin:gridDataCol name="jcyqp_yggw" />
							<odin:gridDataCol name="bzqpdw_yggw" />
							<odin:gridDataCol name="bzqpzf_yggw" />
							<odin:gridDataCol name="bzqprd_yggw" />
							<odin:gridDataCol name="bzqpzx_yggw" />
							
							<odin:gridDataCol name="bzdw" />
							<odin:gridDataCol name="bzzf" />
							<odin:gridDataCol name="bzrd" />
							<odin:gridDataCol name="bzzx" />
							<odin:gridDataCol name="bzspdw" />
							<odin:gridDataCol name="bzspzf" />
							<odin:gridDataCol name="bzsprd" />
							<odin:gridDataCol name="bzspzx" />
							<odin:gridDataCol name="fj" />
							<odin:gridDataCol name="fjsp" />
							
							<odin:gridDataCol name="fy" />
							<odin:gridDataCol name="jcy" />
							<odin:gridDataCol name="fysp" />
							<odin:gridDataCol name="jcysp" />
							
							<odin:gridDataCol name="b0236" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 dataIndex="jgmc" width="210" header="单位名称" align="left" editor="text" edited="false" renderer="showET" menuDisabled="true" hidden="true"/>
							<odin:gridEditColumn2 editor="text" width="40" edited="false" dataIndex="b0234" header="正职" renderer="selectGW" menuDisabled="true" align="center" hidden="true"/>
							<odin:gridEditColumn2 dataIndex="b0235" width="40" header="副职"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
					<%-- 
							<odin:gridEditColumn2 dataIndex="bzdw" width="30" header="党委核定" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzzf" width="30" header="政府核定" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzrd" width="30" header="人大核定" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzzx" width="30" header="政协核定" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bzspdw" width="30" header="党委实配" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzspzf" width="30" header="政府实配" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzsprd" width="30" header="人大实配" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzspzx" width="30" header="政协实配" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="fj" width="30" header="法检核定" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="fjsp" width="30" header="法检实配" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
					 --%>				
							
							
							<odin:gridEditColumn2 dataIndex="bzqpdw" width="40" header="党委"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzf" width="40" header="政府"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqprd" width="40" header="人大"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzx" width="40" header="政协"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="fyqp" width="40" header="法院"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="jcyqp" width="40" header="检察院"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							
							<odin:gridEditColumn2 dataIndex="b0236" width="90" header="空缺岗位" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bz" width="90" header="超配情况" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="yxgw" width="120" header="拟配岗位" renderer="displayYXGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="nmgw" width="120" header="拟免岗位" renderer="displayYXGW" isLast="true" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
						</odin:gridColumnModel>
					</odin:editgrid2>
		</div>
	</td>
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<td style="height:25px;">
					调配前：
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
			<odin:editgrid2 height="50" ddGroup="pgridBufferDD" property="pgrid" hasRightMenu="false" grouping="true" groupCol="zrrx"
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"名\" : \"名\"]})"
			pageSize="200" isFirstLoadData="false" url="/" 
			autoFill="true" forceNoScroll="true">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="personstatus" />
							<odin:gridDataCol name="a01bzdesc" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0200" />
							<odin:gridDataCol name="a0201b" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="zrrx" />
							<odin:gridDataCol name="fxyp07" />
							<odin:gridDataCol name="fxyp00" />
							<odin:gridDataCol name="tp0100" />
							<odin:gridDataCol name="a0215a" />
							<odin:gridDataCol name="b01idbz" />
							<odin:gridDataCol name="b0111bz" />
							<odin:gridDataCol name="b0101bz" />
							<odin:gridDataCol name="zwqc00" />
							<odin:gridDataCol name="rybz" />
							<odin:gridDataCol name="bmd" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="60" header="姓名" align="center"/>
							<odin:gridColumn dataIndex="zrrx" width="120" header="在任人员" hidden="true" renderer="decodeZRRY" align="center"/>
							<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="任现职务" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="任现工作单位及职务" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="去向" editor="text" edited="false" renderer="decodeBZ" align="left"/>
							<odin:gridEditColumn2 dataIndex="a0200" width="40" header="操作" editor="text" edited="false" renderer="moveP" isLast="true" align="center"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid2>
		</div>
    </td>
    
    
    <td width="25%">
    	<table>
		  	<tr>
				<td style="height:25px;">
					调配后：
				</td>
				<td>
					<div class="top_btn_style" 
					 style="background-color:#F08000; line-height:25px;  margin-left: 130px; "
					  onclick="openGWSortWin($('#mntp00').val())">岗位排序
					</div>
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
    	<odin:editgrid2 height="50" property="pgrid2" hasRightMenu="false" grouping="true" groupCol="zrrx" load="pgridLoad"
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"名\" : \"名\"]})"
			pageSize="200" isFirstLoadData="false" url="/" 
			autoFill="true" forceNoScroll="false">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="personstatus" />
				<odin:gridDataCol name="a01bzdesc" />
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="a0200" />
				<odin:gridDataCol name="a0201b" />
				<odin:gridDataCol name="b01id" />
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="zrrx" />
				<odin:gridDataCol name="fxyp07" />
				<odin:gridDataCol name="fxyp00" />
				<odin:gridDataCol name="tp0100" />
				<odin:gridDataCol name="a0215a" />
				<odin:gridDataCol name="b01idbz" />
				<odin:gridDataCol name="b0111bz" />
				<odin:gridDataCol name="b0101bz" />
				<odin:gridDataCol name="zwqc00" />
				<odin:gridDataCol name="zwqc01" />
				<odin:gridDataCol name="rybz" />
				<odin:gridDataCol name="bmd" />
				<odin:gridDataCol name="a0192a" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="a0101" width="60" header="姓名" align="center"/>
				<odin:gridColumn dataIndex="zrrx" width="120" header="在任人员" hidden="true" renderer="decodeZRRY" align="center"/>
				<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="任现职务" editor="text" edited="false" renderer="decodeGW" align="left"/>
				<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="任现工作单位及职务" editor="text" edited="false"  align="left"/>
				<odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="来源" editor="text" edited="false" renderer="decodeBZ"  align="left"/>
				<%-- <odin:gridEditColumn2 dataIndex="personstatus" width="40" header="123" editor="text" edited="false"   align="left"/> --%>
				<odin:gridEditColumn2 dataIndex="a0200" width="40" header="操作" editor="text" edited="false" renderer="moveP" isLast="true" align="center"/>
			</odin:gridColumnModel>
				 
		</odin:editgrid2>
		</div>
    </td>
    
    
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<td style="height:25px;">
					待任免区：
				</td>
				<td>
					 <div class="top_btn_style" 
				      style="background-color:#F08000;  margin-left: 50px;
				       " onclick="openRenXuanTiaoJian($('#mntp00').val())">查询<br/>搜索
				     </div>
				     <div class="top_btn_style" 
				      style="background-color:#F08000; 
				       " onclick="openLRMXWIN($('#mntp00').val())">导入<br/>lrmx(zip)
				     </div>
				     <div class="top_btn_style" 
				      style="background-color:#F08000;   " onclick="rybd()">人员<br/>比对
				     </div>
				     <div class="top_btn_style" 
				      style="background-color:#F08000;   " onclick="radow.doEvent('clearList')">清空<br/>列表
				     </div>
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
			<odin:editgrid2 height="50" ddGroup="noticeSetgridDD" property="pgridBuffer" hasRightMenu="false" 
			pageSize="200" isFirstLoadData="false" url="/"
			autoFill="true" forceNoScroll="true">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="personstatus" />
							<odin:gridDataCol name="a01bzdesc" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0200" />
							<odin:gridDataCol name="a0201b" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="zrrx" />
							<odin:gridDataCol name="fxyp07" />
							<odin:gridDataCol name="fxyp00" />
							<odin:gridDataCol name="tp0100" />
							<odin:gridDataCol name="a0215a" />
							
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="60" header="姓名" align="center"/>
							<odin:gridColumn dataIndex="zrrx" width="120" header="在任人员" hidden="true" align="center"/>
							<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="任现职务" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="任现工作单位及职务" editor="text" edited="false"  align="left"/>
							<%-- <odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="来源/去向" editor="text" edited="false"  align="left"/> --%>
							<%-- <odin:gridEditColumn2 dataIndex="personstatus" width="40" header="123" editor="text" edited="false"   align="left"/> --%>
							<odin:gridEditColumn2 dataIndex="a0200" width="40" header="操作" editor="text" edited="false" renderer="movePB" isLast="true" align="center"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid2>
		</div>
		<div class="OrgInfo">
			<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
		</div>
		<table style="width: 100%">
		  	<tr>
				<td style="height:25px;">
					模拟情况：
				</td>
				<td>
					<div class="top_btn_style" 
					 style="background-color:#F08000; " onclick="openViewGW($('#mntp00').val())">岗位<br/>人选表
					</div>
					<div class=" top_btn_style" 
					 style="background-color:#F08000; " onclick="openViewRYBD($('#mntp00').val())">调配情况<br/>(单位)
					</div>
					<div class=" top_btn_style" 
					 style="background-color:#F08000; " onclick="openViewRYBD_GW($('#mntp00').val())">调配情况<br/>(岗位)
					</div>
					<div class=" top_btn_style" 
					 style="background-color:#F08000; " onclick="openCBDY($('#mntp00').val())">初步<br/>动议
					</div>
				</td>
			</tr>
		</table>
    </td>
  </tr>
</table>



<script type="text/javascript">
function openCBDY(mntp00){
  var publishid=mntp00;
  $h.openPageModeWin('DongYiPreview','pages.fxyp.DongYiPreview&publishid='+publishid,'模拟调配预览',1000,700,{publishid:publishid},g_contextpath);
}
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	noticeSetgrid.setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgrid2').setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgrid').setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgridBuffer').setHeight(document.body.clientHeight-300);
	$('.OrgInfo').css('height',document.body.clientHeight-Ext.getCmp('pgridBuffer').getHeight()-75);
	
	
	noticeSetgrid.store.on('load',setNoticeSetgridScroll);
	
	
	Ext.getCmp('pgrid2').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//拟免
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//在任
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	};
	Ext.getCmp('pgrid').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//拟免
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//在任
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	};
	
	
	
	
	
	/* noticeSetgridView.getRowClass=function(record,rowIndex,rowParams,store){
		console.log(store)
		var fyqp_ygrx = record.data.fyqp_ygrx||0;
		var jcyqp_ygrx = record.data.jcyqp_ygrx||0;
		var bzqpdw_ygrx = record.data.bzqpdw_ygrx||0;
		var bzqpzf_ygrx = record.data.bzqpzf_ygrx||0;
		var bzqprd_ygrx = record.data.bzqprd_ygrx||0;
		var bzqpzx_ygrx = record.data.bzqpzx_ygrx||0;
		var b0234_ygrx = record.data.b0234_ygrx||0;
		var b0235_ygrx = record.data.b0235_ygrx||0;
		
		var all = parseInt(fyqp_ygrx)+parseInt(jcyqp_ygrx)+parseInt(bzqpdw_ygrx)+parseInt(bzqpzf_ygrx)+
				  parseInt(bzqprd_ygrx)+parseInt(bzqpzx_ygrx)+parseInt(b0234_ygrx)+parseInt(b0235_ygrx);
		if(all>0){//拟免
			return 'x-grid-record-ytp';
        }else{
        	return '';
        } 
		
		
	}; */
	
	$("#mntp00").val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	//$("#mntp01").val(parent.Ext.getCmp(subWinId).initialConfig.mntp01);
	$("#mntp01").val('2');
	var mntp01 = $("#mntp01").val();
	var mntp05 = parent.Ext.getCmp(subWinId).initialConfig.mntp05;
	if(mntp01=='1'||mntp01=='3'){
		mntp05='1';
	}else if(mntp01=='2'){
		mntp05='2';
	}
	$("#mntp05").val(mntp05);
	$("input[name='mntp05_r'][value='"+$("#mntp05").val()+"']").attr('checked',true);
	closeWin();
	
	//设置显示列
	changeZSB(true);
	
	
	
	
	noticeSetgrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#b01id').val(rc.data.b01id);
		$('#b0111').val(rc.data.b0111);
		radow.doEvent('pgrid.dogridquery');
		radow.doEvent('pgrid2.dogridquery');
		radow.doEvent('showOrgInfo');
	});
	
	initGridSort('noticeSetgrid',function(g){
		//radow.doEvent('publishsort');
	});
	initGridSortG2G('pgridBuffer',function(g){
		//radow.doEvent('publishsort');
	});
	//initpgrid2Sort();
});
//待任免区到班子
function initGridSort(gridId,callback){
    var pgrid = Ext.getCmp(gridId);
    var dstore = pgrid.getStore();
    var firstGridDropTargetEl =  pgrid.getView().el.dom.childNodes[0].childNodes[1];
    GridDrop.init(pgrid);
    var ddrow = new Ext.dd.DropTarget(firstGridDropTargetEl,GridDrop);
}
//待任免区到调配后
function initpgrid2Sort(gridId,callback){
    var pgrid = Ext.getCmp('pgrid2');
    var dstore = pgrid.getStore();
    var firstGridDropTargetEl =  pgrid.getView().el.dom.childNodes[0].childNodes[1];
    GridDrop2.init(pgrid);
    var ddrow = new Ext.dd.DropTarget(firstGridDropTargetEl,GridDrop2);
}
//调配前到待任免区
function initGridSortG2G(gridId,callback){
    var pgrid = Ext.getCmp(gridId);
    var dstore = pgrid.getStore();
    GridDrop2G.init(pgrid,Ext.getCmp('noticeSetgrid'));
    var ddrow = new Ext.dd.DropTarget(pgrid.container,GridDrop2G);
}

function displayYXGW(value, params, record, rowIndex, colIndex, ds) {
	var vArrayGw,vArray;
	var retStr = "";
	if(value){
		vArrayGw = value.split("{RN}");
		for(i=0;i<vArrayGw.length;i++){
			vArray = vArrayGw[i].split("@@");
			retStr += "<div align='left' width='100%' ><font color=rgb(55,100,160)>"
			+ "<a style='cursor:pointer;font-weight:bold' onclick=\"openYouGuanRenXuann('"+vArray[2]+"');\">"+vArray[0]+"</a>"
			+ "</font></div></br>"
		}
		return retStr;
		
	}
	
	return "";
}
function displayKQGW(value, params, record, rowIndex, colIndex, ds) {
	value = (value||"").replace(/\r/gi,"").replace(/\n/gi,"</br>");
	return value;
}
function movePB(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	//+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('DeleteP','"+value+"');\">移除</a>"
	+ (record.data.personstatus.indexOf("3")>=0?"<a style='cursor:pointer;' onclick=\"movePB_by_nm('"+record.id+"');\">免现职</a></br></br>":"")
	+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_movePB('"+record.id+"');\">移除</a>"
	+ "</font></div>";
	
}
function moveP(value, params, record, rowIndex, colIndex, ds) {
	var fxyp07 = record.data.fxyp07;
	if(fxyp07=='-1'){//拟免
		//record.set('personStatus', '2');
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_moveP('"+record.id+"',-1,'"+ds.baseParams.cueGridId+"');\">取消</a>"
		+ "</font></div>";
    }else if(fxyp07=='1'){//在任
    	return "<div align='center' width='100%' ><font color=blue>"
    	+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_moveP('"+record.id+"',1,'"+ds.baseParams.cueGridId+"');\">删除</a>"
    	+ "</font></div>";
    }else{
    	return '';
    }
	
	
}
function selectGW(value, params, record, rowIndex, colIndex, ds) {
	var DataIndex = Ext.getCmp("noticeSetgrid").getColumnModel().getDataIndex(colIndex);
	var ygrx='',FXYP06='';
	if(value==""||value==null){
		value=0;
	}
	
	if(record.data[DataIndex+"_yggw"]){
		value = parseInt(value) - parseInt(record.data[DataIndex+"_yggw"]);
	}
	
	if(value==0){
		value="";
	}
	
	ygrx = record.data[DataIndex+"_ygrx"];
	FXYP06 = FIELD_RXLB[DataIndex];
	
	var b0236 = (record.data.b0236||"").replace(/[0-9]名/g,"");
	b0236 = b0236.replace(/\r/gi,"").replace(/(\n)|(<\/br>)/gi,function(t){
		return t+(record.data.jgmc||"");
	});
	
	
	
	//核定 实配 缺配核定合并
	var HD = record.data[QP_HD_SP[DataIndex][0]];
	var SP = record.data[QP_HD_SP[DataIndex][1]];
	
	if(HD==null){
		HD='';
	}
	if(SP==null){
		SP='';
	}
	
	var dwmckqgw = (record.data.jgmc||"")+b0236;
	dwmckqgw = dwmckqgw.replace(/\r/gi,"").replace(/\n/gi,"\\n").replace(/<\/br>/gi,"\\n");
	return "<div align='center' width='100%' >"+(SP+"/"+HD+"<br/>(") +(value||"0")/*+(ygrx=="0"?"/":("/"+ygrx)) */+")<font color=blue>"
	//+ "<a style='cursor:pointer;' onclick=\"openGW('"+FXYP06+"','"+record.data.b01id+"','"+dwmckqgw+"');\">选择岗位</a>"
	+ "</font></div>";
	
}




function openLRMXWIN(mntp00){
  var contextPath = '<%=request.getContextPath()%>';
  $h.showWindowWithSrc("importLrmWinmntp",
		  contextPath+"/pages/publicServantManage/ImportLrmssh.jsp?businessClass=com.picCut.servlet.SaveLrmFile"
		  ,"导入lrmx", 420, 230,null,{closeAction:'close'});
}

function openRenXuanTiaoJian(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(!newWin_){
		newWin_ = $h.openWin('rxtj','pages.fxyp.RenXuanTiaoJian','干部调配人选条件 ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_rxtj").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide(); 
	}
}
function openYouGuanRenXuann(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(!newWin_){
		newWin_ = $h.openWin('ygrx','pages.fxyp.YouGuanRenXuan','有关人选名单 ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_ygrx").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.reload();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide(); 
	}
}
function hideWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide();
	}
	//刷新表格
	//radow.doEvent('noticeSetgrid.dogridquery');
	//radow.doEvent('pgrid.dogridquery');
	//openViewGW($("#mntp00").val());
}
function openViewGW(mntp00){
	$h.openPageModeWin('RXFXYP','pages.fxyp.RXFXYP','干部模拟调配人选分析研判表',1250,900,
			{mntp00:mntp00,scroll:"scroll:yes;"},g_contextpath);
}
function openGWSortWin(mntp00){
	$h.openWin('GWSort','pages.fxyp.GWSort','岗位维护',1000,720,'','<%=request.getContextPath()%>',null,
			{mntp00:mntp00,b01id:$('#b01id').val()},true);
}
function openViewRYBD(mntp00){//人员比对
	var b01id = $('#b01id').val();
	var b0111 = $('#b0111').val();
	var mntp05 = $('#mntp05').val();
	/* if(b0111==''){
		$h.alert('','请选择单位！');
		return;
	} */
	$h.openWin('ViewRYBDWin','pages.fxyp.BZRYBD','模拟情况',1410,900,'','<%=request.getContextPath()%>',null,{mntp00:mntp00,b01id:b01id,b0111:b0111,mntp05:mntp05},true);
}
function openViewRYBD_GW(mntp00){//人员比对
	var b01id = $('#b01id').val();
	var b0111 = $('#b0111').val();
	var mntp05 = $('#mntp05').val();
	/* if(b0111==''){
		$h.alert('','请选择单位！');
		return;
	} */
	$h.openWin('ViewRYBDGWWin','pages.fxyp.BZRYBDGW','模拟情况',1410,900,'','<%=request.getContextPath()%>',null,{mntp00:mntp00,b01id:b01id,b0111:b0111,mntp05:mntp05},true);
}
function closeWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.destroy();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.destroy();
	}
	//刷新表格
}



var doAddPerson = (function(){//增加人员
	return {
		queryByNameAndIDS_JZHJ:function(fxyp00){//拟任
			radow.doEvent('queryByNameAndIDS',fxyp00);
		},
		queryByNameAndIDS:function(a0000s){//放入暂存区
			radow.doEvent('queryByNameAndIDS_ZCQ',a0000s);
		},
		addPgridBuffer:function(data){//放入暂存区
			var pgridBuffer = Ext.getCmp('pgridBuffer');
			var store = pgridBuffer.getStore();
			var ortherRow = []
			
			hideWin();
			
			var flag = true;
			for(var i=0;i<data.length;i++){
				flag = true;
				for(var si=0;si<store.getCount();si++){
					if(data[i]['a0200']==store.getAt(si).data.a0200){
						flag = false;
						break;
					}
				}
				if(flag){
					var rc = new Ext.data.Record(data[i]);
					ortherRow.push(rc);
					store.add(rc);
				}
			}
			updateZS(ortherRow);
		}
	}
})();


function openZSGW(){//打开职数岗位配置  已经是职数岗位页面了
	
}


document.onkeydown=function() {
	if (event.keyCode == 13) {
		var id = document.activeElement.id;
		if(id=='dwmc'){
			searchn();
			return false;
		}
	}
	
};

function changeZSB(fload){
	if($(".quxianshi").is(':visible')){
		var mntp05 = $("input[name='mntp05_r']:checked").val();
		$("#mntp05").val(mntp05);
	}
	
	
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	//设置隐藏显示要先清除数据，否则行渲染需要时间
	noticeSetgrid.getStore().removeAll();
	//设置显示列
	var ColumnModel = noticeSetgrid.getColumnModel();
	$(".quxianshi").show();
	if($('#mntp05').val()=='2'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 50 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), false );
		
		/* ColumnModel.setHidden( ColumnModel.findColumnIndex('bzdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzrd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzsprd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fj'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fjsp'), false ); */
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), true );
		
	}else if($('#mntp05').val()=='3'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 150 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), true );
		
		/* ColumnModel.setHidden( ColumnModel.findColumnIndex('bzdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzrd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzsprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fj'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fjsp'), true ); */
		
		
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );
	}else if($('#mntp05').val()=='1'||$('#mntp05').val()=='4'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 200 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );
		
		/* ColumnModel.setHidden( ColumnModel.findColumnIndex('bzdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzrd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzsprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzspzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fj'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fjsp'), true ); */
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), true );

	}
	
	ColumnModel.setHidden( ColumnModel.findColumnIndex('jgmc'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('b0236'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('bz'), false );
	
	if(!fload){
		searchn();
	}
	
	//radow.doEvent('pgrid.dogridquery');
	
}


Ext.onReady(function () {
    Ext.getCmp('pgrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var a0000 = rc.data.a0000;
		if(a0000==''||a0000==null){
			$('.rxxx').hide();
		}else{
			$('.rxxx').show();
		}
		var a01bzdesc = rc.data.a01bzdesc;
		var fxyp07 = rc.data.fxyp07;
		var tp0100 = rc.data.tp0100;
		var a0200 = rc.data.a0200;
		var a0000 = rc.data.a0000;
		var rybz = rc.data.rybz;
		
		
		
		//无拟任免职务  职务全称设置空
		//岗位维护信息
		var fxyp00 = rc.data.fxyp00;
		if(fxyp00&&fxyp00!=''&&fxyp00!='null'){
			radow.doEvent('setGWWHInfo',fxyp00);
			$('.gwwh').show();
		}else{
			//无拟任免职务  职务全称设置空
			$('#zwqc00WH').val('');
			$('.gwwh').hide();
		}
		$('.XingMing2').html("姓名："+rc.data.a0101);
		
		var b0111bz = rc.data.b0111bz;
		var b0101bz = rc.data.b0101bz;
		var bmd = rc.data.bmd;
		odin.setSelectValue('bmd',bmd);
		odin.setSelectValue('a01bzdesc',a01bzdesc);
		$('#a01bzdescSpanId').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;去向');
		$('#a0201bSeclectSpanId').html('选择去向单位');
		
		
		$('#rybz').val(rybz);
		$('#a0201bSeclect').val(b0111bz);
		$('#a0201bSeclect_combo').val(b0101bz);
		//alert(b0111bz)
		if(b0111bz==''||b0111bz==null){
			radow.doEvent('setBZOrgInfo',a0000+"@1");
		}
		
		hideB01();
		
		if(fxyp07=="1"){
			$('#a01bztype').val("2");
			$('#a01bzid').val(tp0100);
		}else{
			$('#a01bztype').val("1");
			$('#a01bzid').val(a0200);
		}
		if(fxyp07=='-1'){
			$('.bmdsty').show();
		}else{
			$('.bmdsty').hide();
		}
		openETCWin();
	});
    Ext.getCmp('pgrid2').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var a0000 = rc.data.a0000;
		
		if(a0000==''||a0000==null){
			$('.rxxx').hide();
		}else{
			$('.rxxx').show();
		}
		var a01bzdesc = rc.data.a01bzdesc;
		var fxyp07 = rc.data.fxyp07;
		var tp0100 = rc.data.tp0100;
		var a0200 = rc.data.a0200;
		
		var b0111bz = rc.data.b0111bz;
		var b0101bz = rc.data.b0101bz;
		var rybz = rc.data.rybz;
		var bmd = rc.data.bmd;
		odin.setSelectValue('bmd',bmd);
		//岗位维护信息
		var fxyp00 = rc.data.fxyp00;
		if(fxyp00&&fxyp00!=''&&fxyp00!='null'){
			radow.doEvent('setGWWHInfo',fxyp00);
			$('.gwwh').show();
		}else{
			//无拟任免职务  职务全称设置空
			$('#zwqc00WH').val('');
			$('.gwwh').hide();
		}
		$('.XingMing2').html("姓名："+rc.data.a0101);
		
		odin.setSelectValue('a01bzdesc',a01bzdesc);
		
		$('#rybz').val(rybz);
		$('#a0201bSeclect').val(b0111bz);
		$('#a0201bSeclect_combo').val(b0101bz);
		//alert(b0111bz)
		if(b0111bz==''||b0111bz==null){
			//来源单位 取拟免
			radow.doEvent('setBZOrgInfo',a0000+"@-1");
		}
		
		hideB01();
		
		$('#a01bzdescSpanId').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;来源');
		$('#a0201bSeclectSpanId').html('选择来源单位');
		if(fxyp07=="1"){
			$('#a01bztype').val("2");
			$('#a01bzid').val(tp0100);
		}else{
			$('#a01bztype').val("1");
			$('#a01bzid').val(a0200);
		}
		$('.bmdsty').hide();//白名单只有拟免的时候才维护
		openETCWin();
	});

    
    
    
    
})

function hideB01(record, index){
	var a01bzdesc  = $('#a01bzdesc').val();
	
	if(a01bzdesc!='本市交流'){
		Ext.getCmp('a0201bSeclect_combo').hide();
		$('#a0201bSeclectSpanId').hide();
	}else{
		Ext.getCmp('a0201bSeclect_combo').show();
		$('#a0201bSeclectSpanId').show();
	}
}
</script>

<div id="gwInfo" style="height: 520px;overflow-y: auto;">
<odin:hidden property="dqnrzwqcid" title="当前拟任 职务全称id"/>
<odin:hidden property="dqnmzwqcid" title="当前拟免 职务全称id"/>
	<div style="margin-left: 20px;margin-right: 20px;margin-top: 5px;">
		<div class="XingMing">123</div>
		<label  class="dqnrzwLB">该人员已设拟任岗位：<span class="dqnrzw" ></span></label>
		
		<table>
		  <%-- <tr>
			<odin:select2 property="yxgwSel" width="391"    label="该单位已设拟任岗位"/>
		  </tr> --%>
		  <tr height="10"><td>&nbsp;</td></tr>
		  <tr class="ygwa02">
			<odin:select2 property="gwa02" width="430"    label="原岗位"/>
		  </tr>
		  <tr class="nrdwb01">
			<odin:select2 property="dwb01" width="430"    label="拟任单位"/>
		  </tr>
		  <tr >
		  	<td class="NRinfo" colspan="2"></td>
		  </tr>
		  <tr >
			<odin:textarea property="dwmckqgw" cols="65" rows="3" label="新增拟任岗位"></odin:textarea>
		  </tr>
		  
		  <tr class="cylb">
			<odin:select2 property="a0201e" label="成员类别" codeType="ZB129" />
		  </tr>
		  
		  <tr height="10"><td>&nbsp;</td></tr>
		  <tr>
			<odin:textarea property="a0192aMN" cols="65" rows="3" label="职务全称描述<br/>(默认与岗位名称一致)"></odin:textarea>
		  </tr>
		  <tr class="zwzj">
		  	<tags:PublicTextIconEdit property="a0501b" codetype="ZB148" onchange="a0501bChange" label="职级" readonly="true"/>
		  </tr>
		  <tr>
			<odin:numberEdit property="zwqc01" label="岗位数目" value="1" decimalPrecision="0" />
		  </tr>
		  
		  
		  
		  <tr class="nmxx">
		  	<td style="padding-top: 20px;"><label >当前工作单位及职务：</label></td>
		  	<td class="dqgzdwjzw" style="padding-top: 20px;word-break : break-all;"></td>
		  </tr>
		  <tr class="nmxx">
		  	<td ><label >设置拟免职务：</label></td>
			<td>
				<odin:hidden property="fxyp06" title="该字段作为标记区分正副职区县4个班子类型，职级公务员"/>
				<odin:hidden property="b01idkq" title="目标单位"/>
				<input type="checkbox" name="mxz" id="mxz" style="border: none!important;display: none;" checked="checked" />
				<!-- <label for="mxz">是否免现职</label> 	 -->
			</td>
		  </tr>
		  <tr class="nmxx">
			<odin:textarea property="nmzwqc" cols="70" rows="3" title="拟免职务描述"></odin:textarea>
		  </tr>
		  <tr  class="nmxx">
		  	<td class="NMinfo" colspan="2"></td>
		  </tr>
		</table>
		<div style="margin-left: 315px;margin-top: 15px;margin-bottom: 30px;">
			<odin:button text="确定" property="saveGWInfo" handler="saveGWInfo" />
			<%-- <odin:button text="保存并并添加其他兼职" property="saveGWInfo2" handler="saveGWInfoNoClose" /> --%>
		</div>
	</div>
</div>
<div id="mntpbzInfo" style="overflow-y:scroll;height: 290px;">
	<div style="margin-left: 20px;margin-top: 10px;width: 485px;">
		<div id="mntpbzInfoToolbarDiv"></div>
		<div class="XingMing2 rxxx">123</div>
		<table>
		  <tr class="rxxx"><!--人选信息  -->
			<odin:select2 property="a01bzdesc" onchange="hideB01" data="['留任','留任'],['内部交流','内部交流'],['本市交流','本市交流'],['退出或转出','退出或转出']" width="400" label="来源/去向" />
		  </tr>
		 <tr class="rxxx"> 
			<odin:textEdit property="rybz"  label="备注"/>
		  </tr>
		  <tr class="rxxx bmdsty">
			<odin:select2 property="bmd"  codeType="XZ09" width="400" label="指定显示" />
		  </tr>
		  <tr class="rxxx">
		  	<tags:PublicTextIconEdit3 label="选择单位" property="a0201bSeclect" defaultValue="" width="400" codetype="orgTreeJsonData"  readonly="true"/>
		  </tr>
		  
		  <tr class="gwwh">
		  	<td colspan="2">
		  		<odin:groupBox title="岗位维护">
		  			<table>
					  <tr>
						<odin:textarea property="a0192aWH" cols="52" labelSpanId="a0192aWHSpanId" rows="3" label="拟任职务全称"/>
					  </tr>
					 <%--  <tr class="zwqcwh">
						<odin:select2 property="a0201eWH" label="成员类别" codeType="ZB129" />
					  </tr> --%>
					  <tr class="zwqcwh">
					  	<tags:PublicTextIconEdit property="a0501bWH" codetype="ZB148" label="职级" readonly="true"/>
					  	
					  </tr>
					  <tr>
					  	<td class="zwwh" colspan="2"></td>
					  </tr>
					  <tr>
					  	<td>
					  		<odin:hidden property="zwqc00WH" title="职务全称id"/>
					  		<odin:hidden property="gwmxInfo" title="岗位明细"/>
					  	</td>
					  </tr>
					 
					</table>
		  		</odin:groupBox>
			</td>
		  </tr>
		</table>
		<odin:hidden property="a01bzid"/>
		<odin:hidden property="a01bztype"/>
		<%-- <div style="margin-left: 245px;margin-top: 5px;margin-bottom: 15px;">
			<odin:button text="确定" property="savemntpbzInfo" handler="saveMntpbzInfo" />
		</div> --%>
	</div>
</div>
<odin:toolBar property="mntpbzInfoToolbar" applyTo="mntpbzInfoToolbarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="确定" icon="images/save.gif" id="mntpbzInfoBtn" handler="saveMntpbzInfo" isLast="true"/>
</odin:toolBar>

<script type="text/javascript">
Ext.onReady(function(){
	//Ext.getCmp('yxgwSel_combo').on('select',setGWdis);
	Ext.getCmp('gwa02_combo').on('select',setGWA02);
	Ext.getCmp('dwb01_combo').on('select',setNRDWb01);
});
</script>
