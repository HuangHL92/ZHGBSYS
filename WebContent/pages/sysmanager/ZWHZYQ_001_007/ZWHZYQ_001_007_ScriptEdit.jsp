<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<title>脚本编辑</title>
<%
	String sqlText = request.getSession().getAttribute("sqlText").toString();
	sqlText = java.net.URLDecoder.decode(java.net.URLDecoder.decode(sqlText, "UTF-8"), "UTF-8");
%>
<script type="text/javascript">
	var sqlText='<%=sqlText%>';
</script>
<script
	src="<%=request.getContextPath()%>/editarea/edit_area/edit_area_full.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	editAreaLoader.init({
		id : "extractScriptSql", 
		start_highlight : true, 
		cursor_position : "begin", 
		allow_toggle : false, 
		language : "zh", 
		syntax : "sql", 
		font_size : "10", 
		begin_toolbar : "DYear,|,DMonth,|,DDay,|,SYear,|,SMonth,|", 
		toolbar : "search,|,go_to_line, |, undo,|, redo", 
		end_toolbar : "|, copy", 
		show_line_colors : true, 
		replace_tab_by_spaces : 4, 
		change_callback : "propertyValueOnchange"
	});
</script>
<odin:hidden property="schemeId"/>
<odin:hidden property="scriptId"/>
<odin:hidden property="opmode"/>
<odin:hidden property="sqlText"/>
<odin:hidden property="changeflag" value="false"/>
<span style="position: absolute; top: 7; left: 8; z-index: 1000; font-size: 12px; font-weight: normal; font-family: Arial" id="gridT"></span>
<odin:toolBar property="btnToolBar" applyTo="toolBarDIV">
	<odin:fill/>
	<odin:buttonForToolBar text="应用" id="save" handler="save" icon="images/icon/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="关闭" id="closeWin" handler="close" icon="images/icon/close.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<div id="toolBarDIV"></div>
<table width="100%" cellpadding="0" cellspacing="0" border="0"  >
	<tr>
		<td colspan="5">
			<textarea id="extractScriptSql" style="width: 790px; height: 388px" name="extractScriptSql"><%=sqlText%></textarea>
		</td>
	</tr>
	<tr id="t">
		<td>
			<table cellpadding="0" cellspacing="0" border="0" style="margin-top: 1px" >
				<tr></tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	Ext.onReady(function(){
		
	}, this, {
		delay : 500
	});

	function propertyValueOnchange(){
		document.all.changeflag.value='true';
	}

	//脚本编辑器取值  
	function getSqlValue(){
		var temp = editAreaLoader.getValue('extractScriptSql');
		temp = temp.replace(/\'/g, "&quot;");
		document.all.sqlText.value = temp;
	}
	
	function save(){
		getSqlValue();
		radow.doEvent('save');
	}
	
	function close(){ 
	      radow.doEvent('close');
	}
</script>
