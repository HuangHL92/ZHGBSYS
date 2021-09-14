<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>


<style>
/**
 * 页面样式CSS
 */
#tag_container {
	width: 774px;
	height: 538px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
}

#left_div {
	width: 240px;
	height: 536px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
	overflow-y: auto;
	overflow-x: hidden;
}

#left_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	border-width: 0 1px 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	line-height: 26px;
	padding-left: 2px;
}

#right_div {
	width: 520px;
	height: 520px;
	float: left;
	padding-left: 10px;
}

#right_div table {
	font-size: 14px;
}

#right_div div {
	display: none;
	width: 100%;
	height: 536px;
	overflow-y: auto;
}

#right_div div table {
	font-size: 14px;
}

#right_div table tr td {
	height: 26px;
	line-height: 26px;
}

#right_div table tr td input[type=text] {
	width: 50px;
	height: 21px;
}

#bottom_div {
	width: 100%;
	height: 40px;
	padding-top: 5px;
}

#bottom_div table {
	width: 100%;
}
label{
font-size: 18px;
}
td input {
	margin: 0px 0px;
	zoom:125%;
}
</style>
<div id="tag_container">
	<div id="left_div">
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">机关事业单位经历</div>
		<div class="leftMenu" onclick="changeTag(this, '02');">企业和机构经历</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">高校(科研院所)经历</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">其他经历</div>
		
	</div>
	<div id="right_div">
	    <!-- 机关事业单位经历 -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="attr2101" id="attr2101" class="marginLeft20"/><label for="attr2101">区县市班子成员</label><br>
					<input type="checkbox" name="attr2102" id="attr2102" class="marginLeft20"/><label for="attr2102">乡镇街道班子</label><br>
					<input type="checkbox" name="attr2103" id="attr2103" class="marginLeft20"/><label for="attr2103">乡镇街道党政主要负责人</label><br>
					<input type="checkbox" name="attr2104" id="attr2104" class="marginLeft20"/><label for="attr2104">政法委或政法部门工作经历</label><br>
					<input type="checkbox" name="attr2105" id="attr2105" class="marginLeft20"/><label for="attr2105">法律工作经历五年以上</label><br>
					<input type="checkbox" name="attr2106" id="attr2106" class="marginLeft20"/><label for="attr2106">镇街团委书记或区县市团委班子成员</label><br>
					<input type="checkbox" name="attr2107" id="attr2107" class="marginLeft20"/><label for="attr2107">纪检监察部门</label><br>
					<input type="checkbox" name="attr2108" id="attr2108" class="marginLeft20"/><label for="attr2108">两年及以上基层工作经历</label><br>
				</td>	
			</tr>	
				
			
		</table>
		<!-- 企业和机构经历 -->
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2201" id="attr2201" class="marginLeft20"/><label for="attr2201">民营企业负责人</label><br>
					<input type="checkbox" name="attr2202" id="attr2202" class="marginLeft20"/><label for="attr2202">民营企业中层</label><br>
					<input type="checkbox" name="attr2203" id="attr2203" class="marginLeft20"/><label for="attr2203">外资企业中方负责人</label><br>
					<input type="checkbox" name="attr2204" id="attr2204" class="marginLeft20"/><label for="attr2204">外资企业外方代表</label><br>
					<input type="checkbox" name="attr2205" id="attr2205" class="marginLeft20"/><label for="attr2205">外资企业中层</label><br>
					<input type="checkbox" name="attr2206" id="attr2206" class="marginLeft20"/><label for="attr2206">国有企业负责人</label><br>
					<input type="checkbox" name="attr2207" id="attr2207" class="marginLeft20"/><label for="attr2207">国有企业部门负责人</label><br>
					<input type="checkbox" name="attr2208" id="attr2208" class="marginLeft20"/><label for="attr2208">事务所及其他社会组织</label><br>
					<input type="checkbox" name="attr2209" id="attr2209" class="marginLeft20"/><label for="attr2209">国际组织</label><br>
				</td>	
			</tr>
			
			
		</table>
		<!-- 高校(科研院所)经历 -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2301" id="attr2301" class="marginLeft20"/><label for="attr2301">正职</label><br>
					<input type="checkbox" name="attr2302" id="attr2302" class="marginLeft20"/><label for="attr2302">副职</label><br>
					<input type="checkbox" name="attr2303" id="attr2303" class="marginLeft20"/><label for="attr2303">部门正职</label><br>
					<input type="checkbox" name="attr2304" id="attr2304" class="marginLeft20"/><label for="attr2304">部门副职</label><br>
					<input type="checkbox" name="attr2305" id="attr2305" class="marginLeft20"/><label for="attr2305">二级学院党组织书记</label><br>
					<input type="checkbox" name="attr2306" id="attr2306" class="marginLeft20"/><label for="attr2306">二级学院院长</label><br>
					<input type="checkbox" name="attr2307" id="attr2307" class="marginLeft20"/><label for="attr2307">二级学院副职</label><br>
					<input type="checkbox" name="attr2308" id="attr2308" class="marginLeft20"/><label for="attr2308">校团委</label><br>
				</td>	
			</tr>
		</table>	
		<!-- 其他经历 -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2401" id="attr2401" class="marginLeft20"/><label for="attr2401">海外学习经历</label><br>
					<input type="checkbox" name="attr2402" id="attr2402" class="marginLeft20"/><label for="attr2402">海外工作经历</label><br>
					<input type="checkbox" name="attr2403" id="attr2403" class="marginLeft20"/><label for="attr2403">聘任制公务员</label><br>
					<input type="checkbox" name="attr2404" id="attr2404" class="marginLeft20"/><label for="attr2404">公开选拔</label><br>
					<input type="checkbox" name="attr2405" id="attr2405" class="marginLeft20"/><label for="attr2405">大学生村官</label><br>
					<input type="checkbox" name="attr2406" id="attr2406" class="marginLeft20"/><label for="attr2406">团以下转业退伍</label><br>
					<input type="checkbox" name="attr2407" id="attr2407" class="marginLeft20"/><label for="attr2407">副师职以上军转</label><br>
					<input type="checkbox" name="attr2408" id="attr2408" class="marginLeft20"/><label for="attr2408">任领导联系人</label><br>
					<textarea rows="3" cols="70"  name="attr2409" id="attr2409" ></textarea>
				</td>
			</tr>
		</table>	
	</div>
</div>
<div id="bottom_div">
	<table>
		<tr>
			<td align="center" >
				<odin:button text="保&nbsp;&nbsp;存" property="save" />
			</td>		
		</tr>		
	</table>
</div> 

<script type="text/javascript">
Ext.onReady(function(){
	//document.getElementById("attr2101").value="";


});
$('#attr2408').click(function(){
	var obj=$('#attr2409');
	if(this.checked)
		obj.attr("disabled",false);
	else{
		obj.val("");
		obj.attr("disabled","disabled");
	}
}); 

//根据父节点切换子节点
function changeTag(node, codevalue){
	var codevaluelist = ['01', '02', '03', '04'];
	for(var i = 0,len=codevaluelist.length; i < len; i++) {
		document.getElementById("tag" + codevaluelist[i]).style.display = "none";
	}
	document.getElementById("tag" + codevalue).style.display = "block";
	changeTagMenuHover(node);
}

//标签用
function disableInputTag(check,inputId) {
	var obj = $('#'+inputId);
	if($(check).is(':checked')) {
		obj.attr("disabled",false);
	} else {
		obj.val("");
		obj.attr("disabled","disabled");
	}
}
/**
 * ******************************************此处以下所有方法用于修改左侧菜单鼠标悬停样式【成套使用】*******************************************************
 */
Ext.onReady(function() {
	leftMenuHover();
	unbindOnmouseEvent(document.getElementById("firstTag"));
});

function changeTagMenuHover(node){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		nodes[i].style.backgroundColor = "#FFFFFF";
		bindOnmouseEvent(nodes[i]);
	}
	unbindOnmouseEvent(node);
	node.style.backgroundColor = "#1E90FF";
}

//菜单鼠标悬浮，点击，离开事件
function leftMenuHover(){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		bindOnmouseEvent(nodes[i]);
	}
}

/*
 * 重写getElementsByClassName()方法，IE8及以下没有该方法
 */
function getElementsByClassName(className, tagName) {
    if (document.getElementsByClassName) {
        // 使用现有方法
        return document.getElementsByClassName(className);
    } else {
        // 循环遍历所有标签，返回带有相应类名的元素
        var rets = [], nodes = document.getElementsByTagName(tagName);
        for (var i = 0, len = nodes.length; i < len; i++) {
            if (hasClass(nodes[i],className)) {
            	rets.push(nodes[i]);
            }
        }
        return rets;
    }
}

function hasClass(tagStr,className){  
    var arr=tagStr.className.split(/\s+/ );  //这个正则表达式是因为class可以有多个,判断是否包含  
    for (var i=0;i<arr.length;i++){  
           if (arr[i]==className){  
                 return true ;  
           }  
    }  
    return false ;  
}

function bindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标悬停事件
	node.onmouseout=function(){ node.style.backgroundColor = "#FFFFFF"; };//鼠标离开事件
	node.onmousedown=function(){node.style.backgroundColor = "#1E90FF";};//鼠标点击时触发事件
}

function unbindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标悬停事件
	node.onmouseout=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标离开事件
	node.onmousedown=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标点击时触发事件
}

</script>
