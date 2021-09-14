<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lengthValidator.js"></script>
<style>
#wzms{width: 100%;position: relative;top: 10px;left: 15px;}
#grid{width: 100%;position: relative;top: 10px;left: 15px;}
</style>
<script type="text/javascript">
function save(){
	//文字描述不可超过1000字 
	var tagcjlxzs = document.getElementById('tagcjlxzs').value;	
	
	if(tagcjlxzs.length > 1000) {
		Ext.Msg.alert("提示信息", "文字描述长度超过限制：1000字以内！");
	　　return false; 
　　}else { 
		radow.doEvent("save.onclick");
　　} 
	
}

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var tagid = record.data.tagid;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+tagid+"&quot;)\">删除</a>";
}
function deleteRow2(tagid){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',tagid);
		}else{
			return;
		}		
	});	
}
</script>


<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" id="TagCjlxAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="保存" id="saveBtn" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar>
</odin:toolBar>
<div>
<div id="btnToolBarDiv" align="center"></div>
<odin:hidden property="tagid" title="主键id" ></odin:hidden>
<div id="wzms">
	<table>
		<tr>
			<td><odin:textarea property="tagcjlxzs" cols="85" rows="4" label="文字描述" validator="tagcjlxzsLength" readonly="true"></odin:textarea></td>
		</tr>
	</table>
</div>
<div id="grid">
	<table>
		<tr>
			<td>
				<odin:grid property="TagCjlxGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" height="200">
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="tagid" />
				   		<odin:gridDataCol name="tagcjlx" isLast="true"/>			   		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
					  <odin:gridEditColumn header="id" dataIndex="tagid" editor="text" hidden="true"/>
					  <odin:gridEditColumn2  header="惩戒类型"  dataIndex="tagcjlx" width="300" edited="false" editor="select" codeType="TAGCJLX"/>
					   <odin:gridEditColumn header="操作" dataIndex="delete" width="120" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
				</odin:grid>	
			</td>
			<td>
				<table>
					<tr height="50">
						<tags:PublicTextIconEdit property="tagcjlx" label="惩戒类型" width="180" required="true" codetype="TAGCJLX" readonly="true"></tags:PublicTextIconEdit>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

</div>
<script type="text/javascript">
Ext.onReady(function(){
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.tagCjlx);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var ext_gridobj = Ext.getCmp('TagCjlxGrid');
		var gridobj = document.getElementById('forView_TagCjlxGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className= "divcover_wrap";
		cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className= "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
		
	}
	
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(realParent.fieldsDisabled);
});	

function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}
Ext.onReady(function(){
	var side_resize=function(){
		 document.getElementById('btnToolBarDiv').style.width = document.body.clientWidth;	
		 Ext.getCmp('TagCjlxGrid').setWidth(500); 
	}
	side_resize();  
	window.onresize=side_resize; 
});
</script>


<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>