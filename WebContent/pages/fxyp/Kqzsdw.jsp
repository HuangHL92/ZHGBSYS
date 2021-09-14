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

<style type="text/css">
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.top_btn_style{
	display:inline;
	border-radius:5px;
	cursor:pointer;
	margin-left:7px;
	height:25px;
	line-height:25px;
	font-size:14px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
	margin-left: 420px;
}
</style>
<script type="text/javascript">
function searchn(){
	radow.doEvent("noticeSetgrid.dogridquery");
}
function selectORG(){
	var url = g_contextpath + "/pages/fxyp/OrgConfig.jsp?";
	$h.showWindowWithSrc("setOrgConfig",url,"设置显示单位", 400, 730);
}
</script>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp05"/>
<table style="width: 100%;">
  <tr>
    <td style="width: 70%;">
    	<table>
		  	<tr>
				<odin:textEdit property="dwmc" label="单位名称"/>
				<td><odin:button text="查询" property="searchn" handler="searchn" /></td>
				<td><odin:button text="选择单位" property="selectORG" handler="selectORG" /></td>
				<td class="quxianshi" style="display: none;">
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="2" checked>领导班子</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="3">平台</label>
				</td>
				<td>
					<div class="top_btn_style" 
					 style="background-color:#F08000; font-size:18px;
					 width: 120px;" onclick="openViewGW($('#mntp00').val())">岗位人选表
					</div>
				</td>
			</tr>
		</table>

		<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid2 property="noticeSetgrid" hasRightMenu="false" autoFill="false"   pageSize="300" isFirstLoadData="false" url="/">
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
							<odin:gridDataCol name="b0235" />
							<odin:gridDataCol name="b0235_ygrx" />
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
							
							<odin:gridDataCol name="b0236" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 dataIndex="jgmc" width="210" header="单位名称" align="left" editor="text" edited="false" menuDisabled="true" hidden="true"/>
							<odin:gridEditColumn2 editor="text" width="90" edited="false" dataIndex="b0234" header="空缺正职</br>拟配岗位/人选" renderer="selectGW" menuDisabled="true" align="center" hidden="true"/>
							<odin:gridEditColumn2 dataIndex="b0235" width="90" header="空缺副职</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bzqpdw" width="90" header="党委空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzf" width="90" header="政府空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqprd" width="90" header="人大空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzx" width="90" header="政协空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="fyqp" width="90" header="院长空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="jcyqp" width="90" header="检查长空缺</br>拟配岗位/人选"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							
							<odin:gridEditColumn2 dataIndex="b0236" width="120" header="空缺岗位" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bz" width="110" header="超配情况" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="yxgw" width="120" header="拟配岗位" renderer="displayYXGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="nmgw" width="120" header="拟免岗位" renderer="displayYXGW" isLast="true" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
						</odin:gridColumnModel>
					</odin:editgrid2>
		</div>
	</td>
    <td style="width: 30%;">
    	<div  style="align:left top;height:100%;overflow:auto;">
			<odin:editgrid2 property="pgrid" hasRightMenu="false" grouping="true" groupCol="yxgw" autoFill="false" 
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"名\" : \"名\"]})"
			pageSize="200" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="yxgw" />
							<odin:gridDataCol name="tp0100" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="120" header="姓名" align="center"/>
							<odin:gridColumn dataIndex="yxgw" width="120" header="拟配岗位" hidden="true" align="center"/>
							<odin:gridEditColumn2 dataIndex="a0192a" width="230" header="现任工作单位及职务" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="tp0100" width="50" header="操作" editor="text" edited="false" renderer="moveP" isLast="true" align="center"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid2>
		</div>
    </td>
  </tr>
</table>



<script type="text/javascript">
var FIELD_RXLB = {"b0234":"1","b0235":"3","bzqpdw":"1001","bzqpzf":"1004","bzqprd":"1003","bzqpzx":"1005","jcyqp":"1007","fyqp":"1006"};
var RXLB_FIELD = {"1":"b0234","3":"b0235","1001":"bzqpdw","1004":"bzqpzf","1003":"bzqprd","1005":"bzqpzx","1007":"jcyqp","1006":"fyqp"};
var B0131DECODE = {"1":"正职","3":"副职",'1001':'党委','1004':'政府','1003':'人大','1005':'政协','1006':'院长','1007':'检查长'};

var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	noticeSetgrid.setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgrid').setHeight(document.body.clientHeight);
	
	
	document.getElementById("mntp00").value=parent.Ext.getCmp(subWinId).initialConfig.mntp00;
	document.getElementById("mntp05").value=parent.Ext.getCmp(subWinId).initialConfig.mntp05;
	closeWin();
	
	//设置显示列
	var ColumnModel = noticeSetgrid.getColumnModel();
	if($('#mntp05').val()=='2'){
		$(".quxianshi").show();
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 50 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), false );
	}else if($('#mntp05').val()=='1'||$('#mntp05').val()=='4'){
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );

	}
	
	ColumnModel.setHidden( ColumnModel.findColumnIndex('jgmc'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('b0236'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('bz'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('yxgw'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('nmgw'), false );
	
	
	
	
	
	noticeSetgrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#b01id').val(rc.data.b01id);
		radow.doEvent('pgrid.dogridquery');
	});
	
	//$h.initGridSort('noticeSetgrid',function(g){
		//radow.doEvent('publishsort');
	//});
});


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
function moveP(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('DeleteP','"+value+"');\">移除</a>"
	+ "</font></div>";
	
}
function selectGW(value, params, record, rowIndex, colIndex, ds) {
	var ygrx='',FXYP06='';
	if(value=="0"||value==null){
		value="";
	}
	var DataIndex = Ext.getCmp("noticeSetgrid").getColumnModel().getDataIndex(colIndex);
	ygrx = record.data[DataIndex+"_ygrx"];
	FXYP06 = FIELD_RXLB[DataIndex];
	
	var b0236 = (record.data.b0236||"").replace(/[0-9]名/g,"");
	b0236 = b0236.replace(/\r/gi,"").replace(/(\n)|(<\/br>)/gi,function(t){
		return t+(record.data.jgmc||"");
	});
	
	var dwmckqgw = (record.data.jgmc||"")+b0236;
	dwmckqgw = dwmckqgw.replace(/\r/gi,"").replace(/\n/gi,"\\n").replace(/<\/br>/gi,"\\n");
	return "<div align='center' width='100%' >"+(value||"")+(ygrx=="0"?"":("/"+ygrx))+"</br><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"openGW('"+FXYP06+"','"+record.data.b01id+"','"+dwmckqgw+"');\">选择岗位</a>"
	+ "</font></div>";
	
}

function openGW(type,b01id,dwmckqgw){
	Ext.MessageBox.buttonText.ok='确定（设置人选条件）';
	Ext.MessageBox.prompt("请输入岗位名称：",'',function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("addInfo",txt+"@@"+type+"@@"+b01id+"@@"+$('#mntp00').val());
			 //openRenXuanTiaoJian('');
		 }
		 Ext.MessageBox.buttonText.ok='确定';   
	},this,true,dwmckqgw); 
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
	radow.doEvent('noticeSetgrid.dogridquery');
	radow.doEvent('pgrid.dogridquery');
	openViewGW($("#mntp00").val());
}
function openViewGW(mntp00){
	$h.openPageModeWin('RXFXYP','pages.fxyp.RXFXYP','干部模拟调配人选分析研判表',1250,900,
			{mntp00:mntp00,scroll:"scroll:yes;"},g_contextpath);
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
		queryByNameAndIDS:function(list,fxyp00){//按姓名查询
			$("#a0000s").val(list);
			radow.doEvent('queryByNameAndIDS',fxyp00);
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

function changeZSB(){
	var mntp05 = $("input[name='mntp05_r']:checked").val();
	$("#mntp05").val(mntp05);
	
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	//设置显示列
	var ColumnModel = noticeSetgrid.getColumnModel();
	if($('#mntp05').val()=='2'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 50 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), false );
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
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );
	}
	
	
	radow.doEvent('noticeSetgrid.dogridquery');
	radow.doEvent('pgrid.dogridquery');
	
}
</script>
<odin:hidden property="a0000s"/>
<odin:hidden property="b01id"/>
