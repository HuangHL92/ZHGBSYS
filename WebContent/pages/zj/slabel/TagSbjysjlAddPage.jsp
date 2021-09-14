<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#tag_container {
	width: 380px;
	height: 320px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
}

#left_div {
	width: 380px;
	height: 320px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
}

#left_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	line-height: 26px;
	padding-left: 2px;
}

#right_div {
	width: 380px;
	height: 320px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
}

#right_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	line-height: 26px;
	padding-left: 2px;
}

#bottom_div {
	width: 100%;
	height: 40px;
	padding-top: 5px;
}

#bottom_div table {
	width: 100%;
}
</style>

<div id="tag_container">
	<div id="left_div">
		<div>
			<input type="checkbox" name="tags1" id="tags1">
			<label>全国优秀共产党员</label>
		</div>
		<div>
			<input type="checkbox" name="tags2" id="tags2">
			<label>全国优秀党务工作者</label>
		</div>
		<div>
			<input type="checkbox" name="tags3" id="tags3">
			<label>全国优秀县委书记</label>
		</div>
		<div>
			<input type="checkbox" name="tags4" id="tags4">
			<label>全国劳动模范</label>
		</div>
		<div>
			<input type="checkbox" name="tags6" id="tags6">
			<label>全省优秀共产党员</label>
		</div>
		<div>
			<input type="checkbox" name="tags7" id="tags7">
			<label>全省优秀党务工作者</label>
		</div>
		<div>
			<input type="checkbox" name="tags8" id="tags8">
			<label>全省优秀县委书记</label>
		</div>
		<div>
			<input type="checkbox" name="tags9" id="tags9">
			<label>全省劳动模范</label>
		</div>
	<!-- </div>
	<div id="right_div">
		<div>
			<input type="checkbox" name="tags10" id="tags10">
			<label>国家级科技进步奖</label>
		</div>
		<div>
			<input type="checkbox" name="tags11" id="tags11">
			<label>国家级教学成果奖</label>
		</div>
		<div>
			<input type="checkbox" name="tags12" id="tags12">
			<label>省级科技进步奖</label>
		</div>
		<div>
			<input type="checkbox" name="tags13" id="tags13">
			<label>省级教学成果奖</label>
		</div>-->
		<div> 
			<input type="checkbox" name="tags5" id="tags5" onclick="changeNoteCss(this)">
			<label>其他奖励&nbsp;&nbsp;</label>
			<input type="text" name="tags5n" id="tags5n" disabled="disabled" style="width:200px;">
		</div>
	</div>
</div>
<div id="bottom_div">
	<table>
		<tr>
			<td align="center" >
				<odin:button text="&nbsp;保&nbsp;&nbsp;存&nbsp;" property="save" />
			</td>		
		</tr>		
	</table>
</div> 
<script type="text/javascript">
//输入框中显示选中标签
function changeNoteCss(node){
	if($(node).is(':checked')) {
		document.getElementById(node.id + "n").disabled = false;
	}else{
		document.getElementById(node.id + "n").disabled = true;
	}
}
</script>