<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<style>
#tjInfo tr td{
 font-size: 22px;
}
.bjtj{
/* border-radius:5px;
cursor:pointer;
font-size:20px;
color:#fff;
background-color:#3680C9;
text-align: center;
background-color:#F08000; */
margin-left:7px;
margin-top:2px;
}


</style>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<table id="tjInfo" cellspacing="0" style="width: 101.3%;background-color: rgb(209,223,245);">
	<tr style="border-bottom: 1px solid black;">
		<td align="right" style="width: 10%;border-bottom: 1px solid black;">岗位名称：</td>
		<td id="gwmc" style="width: 90%;border-bottom: 1px solid black;"></td>
	</tr>
	<tr>
		<td align="right" style="width: 10%;">人选条件：</td>
		<td id="rxtj" style="width: 90%;"></td>
	</tr>
</table> 
<odin:editgrid2 property="elearningGrid" height="200" hasRightMenu="false" topBarId="btnToolBar" title="符合条件人选名单" autoFill="true"  pageSize="20" bbarId=""  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="personcheck" />  
		<odin:gridDataCol name="a0000" />  
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="a0107"/>
		<odin:gridDataCol name="a0288"/>
		<odin:gridDataCol name="a0192c"/>
		<odin:gridDataCol name="zgxl"/>
		<odin:gridDataCol name="a0140"/>
		<odin:gridDataCol name="a0192f"/>
		<odin:gridDataCol name="zgxw"/>
		<odin:gridDataCol name="a0192a" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="elearningGrid" 
							/>
		<odin:gridEditColumn2 dataIndex="a0101" width="60" header="姓名" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0104" width="30" header="性别" codeType="GB2261" editor="select" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0107" width="60" header="出生年月" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0140" width="60" header="入党时间" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0288" width="60" header="现职务层次时间" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192c" width="60" header="现职级时间" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192f" width="60" header="任现职时间" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="zgxl" width="60" header="学历" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="zgxw" width="60" header="学位" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="160" header="现任工作单位及职务" editor="text" edited="false" align="left" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>

<%@include file="resourse/photoListTemplate.jsp" %>
<div style="display: none;" class="moreBtn">
	<div style="width: 100%;text-align: center;">
		<a class="btn  btn-mini" href="#"><img alt="" src="<%=request.getContextPath()%>/main/gbmainimg/hyp.png">
		<span>换一批</span></a>
	</div>
</div>


<odin:hidden property="fxyp00"/>
<odin:hidden property="gwmcH"/>
<odin:hidden property="rxtjH"/>
<odin:hidden property="a0000s"/>
<odin:hidden property="queryType" value="1"/>



<script type="text/javascript">





var g_contextpath = '<%= request.getContextPath() %>';


function doResize(){
	var elearningGrid = Ext.getCmp('elearningGrid');
	elearningGrid.setHeight(200);
	var viewSize = Ext.getBody().getViewSize();
	
	elearningGrid.setHeight(viewSize.height-$('#tjInfo').height()-1);
	elearningGrid.setWidth(viewSize.width);
	/* $('.gbtj-content').width(viewSize.width);
	$('.gbtj-content').height(viewSize.height-$('#tjInfo').height()-1); */
	elearningGrid.hide()
}

//设置岗位信息
function setGWXX(){
	$("#gwmc").html($("#gwmcH").val());
	$("#rxtj").html($("#rxtjH").val()+
			"<br/><a class='btn btn-primary bjtj' onclick='saveP(&quot;"+document.getElementById("fxyp00").value+"&quot;)'>确认选择人员</a>"+
			"<a class='btn btn-primary bjtj' onclick='rybd()'>人员比对</a>"+
			"<a class='btn btn-primary bjtj' onclick='realParent.openRenXuanTiaoJian(&quot;"+document.getElementById("fxyp00").value+"&quot;,1)'>全库查询</a>"+
			"<a class='btn btn-primary bjtj' onclick='realParent.openRenXuanTiaoJian(&quot;"+document.getElementById("fxyp00").value+"&quot;,2)'>二次查询</a>"+
			"<a class='btn btn-primary bjtj' onclick='realParent.openRenXuanTiaoJian(&quot;"+document.getElementById("fxyp00").value+"&quot;,3)'>追加查询</a>"+
			"<a class='btn btn-primary bjtj' onclick='realParent.openRenXuanTiaoJian(&quot;"+document.getElementById("fxyp00").value+"&quot;,4)'>剔除查询</a>"+
			'<a style="margin-left:541px;" onclick="nextPage()" class="btn  btn-mini" href="javascript:void(0)"><img alt="" src="main/gbmainimg/hyp.png"> <span>换一批</span></a>'
	);
	
	doResize()
	
}

function nextPage(){
	Photo_List._p.nextPage();
	Photo_List.ajaxSubmit('ShowData','pages.mntpsj.YouGuanRenXuan');
}

function reload(){
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	document.getElementById("queryType").value=parent.Ext.getCmp(subWinId).initialConfig.queryType;
	radow.doEvent('initX');
}


Ext.onReady(function(){
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	//window.onresize=resizeframe;
	//resizeframe();
	var elearningGrid = Ext.getCmp('elearningGrid');
	/* var bbar = elearningGrid.getBottomToolbar();
	 bbar.insertButton(11,[
						
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:700}),
						new Ext.Button({
							icon : 'images/keyedit.gif',
							id:'photoMode',
						    text:'确认选择人员',
						    handler:saveP
						})
						]);  */
						
});

function saveP(){
	var grid = Ext.getCmp('elearningGrid');
	var store = grid.getStore();
	var length = store.getCount();
	var a0000s='';
	$('input:checkbox[name=a01a0000]:checked').each(function(i){
		a0000s = a0000s + $(this).attr('a0000') + ',';
     });
	/* for (var i = 0; i < length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		if (record.personcheck) {
			a0000s = a0000s + record.a0000 + ',';
		}
	} */
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000s").val(a0000s);
	radow.doEvent('saveP');
}

function savePFromTpbj(a0000s){
	$("#a0000s").val(a0000s);
	radow.doEvent('saveP');
}

function rybd(){
	var grid = Ext.getCmp('elearningGrid');
	var store = grid.getStore();
	var length = store.getCount();
	var a0000s='';
	$('input:checkbox[name=a01a0000]:checked').each(function(i){
		a0000s = a0000s + $(this).attr('a0000') + ',';
     });
	/* for (var i = 0; i < length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		if (record.personcheck) {
			a0000s = a0000s + record.a0000 + ',';
		}
	} */
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000s").val(a0000s);
	radow.doEvent('tpbj.onclick');
}


function clearSelected() {
	 //列表
	/* var gridId = "elearningGrid";
	var fieldName = "personcheck";
	var store = odin.ext.getCmp(gridId).store;
	var length = store.getCount();
	for (var i = 0; i < length; i++) {
		store.getAt(i).set(fieldName, false);
	}*/
	$('input:checkbox[name=a01a0000]:checked').prop('checked', false);
	$('.gbtj-list').css('background-color','rgb(255,255,255)');
    $('#a0000s').val(''); 
}
</script>
