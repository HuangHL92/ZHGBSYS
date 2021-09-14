<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script src="<%=request.getContextPath()%>/pages/yntp/js/jquery-1.4.4.min.js" type="text/javascript"></script>

<script type="text/javascript" src="basejs/helperUtil.js"></script>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
#grid1 {
	width: 100% !important;
}
.cellyellow{
background: yellow !important;
background-color: yellow !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">

Ext.onReady(function(){
	$('#tp0100').val(parentParam?parentParam.tp0100:"");
	$('#yn_id').val(parentParam?parentParam.yn_id:"");
	$('#tp0116').val(parentParam?parentParam.tp0116:"");
});

</script>



<div id="btnToolBarDiv" style="width:100%;"></div>		
<odin:hidden property="tp0100" title="动议人员id"/>
<odin:hidden property="yn_id"  />
<odin:hidden property="tp0116"  />
<!-- tpzw00 tpzw01 fxyp07 b01id  gwcode zjcode zwcode a0201e iscount -->  
<table>
  <tr>
  	 <odin:select2 property="fxyp07" label="拟任状态" data="['1','拟任'],['-1','拟免']"/>
     <tags:PublicTextIconEdit3 codetype="orgTreeJsonData" label="任职机构" property="b0111" defaultValue="" readonly="true"/>
     <odin:textEdit property="tpzw01" label="职务描述" width="440"></odin:textEdit>
  </tr>
</table>

<div id="div_data" style="width:100%;">								
		<odin:editgrid property="grid1" autoFill="true" forceNoScroll="true" pageSize="100"
			topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
			        <odin:gridDataCol name="tpzw00" /> 
					<odin:gridDataCol name="tpzw01" />					
					<odin:gridDataCol name="fxyp07" />
					<odin:gridDataCol name="gwcode" />
					<odin:gridDataCol name="zjcode" />
					<odin:gridDataCol name="zwcode" />
					<odin:gridDataCol name="a0201e" />
					<odin:gridDataCol name="a0201d" />
					<odin:gridDataCol name="b01id" />
					<odin:gridDataCol name="b0101" />
					<odin:gridDataCol name="iscount" isLast="true" />

			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="拟任状态" align="center" edited="false" width="100" dataIndex="fxyp07" selectData="['1','拟任'],['-1','拟免']" editor="select"  />
					<odin:gridEditColumn header="单位" align="center" edited="false" width="100" dataIndex="b0101" editor="text"  />
					<odin:gridEditColumn header="职务描述" align="center" edited="false" width="100" dataIndex="tpzw01" editor="text"  />
					<odin:gridEditColumn2 header="职位名称" align="center" edited="false" width="150" dataIndex="gwcode" editor="select" codeType="GWGLLB" hidden="true"/>
					<odin:gridEditColumn header="是否班子成员" align="center" edited="false" width="150" dataIndex="a0201d" editor="select" codeType="XZ09" hidden="true"/>
					<odin:gridEditColumn header="成员类别" align="center" edited="false" width="150" dataIndex="a0201e" editor="select" codeType="ZB129" hidden="true"/>
					<odin:gridEditColumn2 header="对应职务层次" align="center" edited="false" width="150" dataIndex="zwcode" editor="select" isLast="true" hidden="true" codeType="ZB09"/>
			 </odin:gridColumnModel>
		</odin:editgrid>																
</div>
	
<%-- <odin:toolBar property="ToolBar" applyTo="btnToolBarDiv">
    <odin:fill />
	<odin:buttonForToolBar text="新增" id="insert" icon="image/icon021a2.gif" handler="insert"/>
	<odin:separator />
	<odin:buttonForToolBar text="修改" id="update" icon="image/icon021a6.gif" handler="update"/>
	<odin:separator />
	<odin:buttonForToolBar text="删除" id="deleteBtn" icon="image/icon021a3.gif" handler="deleteBtn" />
	<odin:separator />
	<odin:buttonForToolBar text="保存" id="save1" handler="save" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar> --%>

<odin:hidden property="ids" title="类别id"/>
<odin:window src="/blank.htm" id="Check" width="600" height="250"
	title="考核页面" modal="true"></odin:window>
<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("grid1").setHeight(height-height_top);
	Ext.getCmp("grid1").setWidth(width);
}
Ext.onReady(function() {	
	window.onresize=setWidthHeight;
	setWidthHeight();
	
});

function insert(){//新增
	
	radow.doEvent("insert");
}

function update(){  //修改
	
	
	radow.doEvent("update");
}

function deleteBtn(){  //删除
   			
	radow.doEvent('deleteBtn');
	
	
}
function save(){
	
	
	
	radow.doEvent('save.onclick');
}

function countrenderer(value, params, rs, rowIndex, colIndex, ds){
	value = value==null|| value==''?'0' :value;
	if(parseInt(value)>parseInt(rs.get('gwnum'))){
		params.css='cellyellow';
		return "<span style='color:red;font-weight:bold;'>"+value+"</span>";
	} else {
		return value;
	}
}

</script>

