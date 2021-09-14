<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>


<style>
/**
 * ҳ����ʽCSS
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
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">������ҵ��λ����</div>
		<div class="leftMenu" onclick="changeTag(this, '02');">��ҵ�ͻ�������</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">��У(����Ժ��)����</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">��������</div>
		
	</div>
	<div id="right_div">
	    <!-- ������ҵ��λ���� -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="attr2101" id="attr2101" class="marginLeft20"/><label for="attr2101">�����а��ӳ�Ա</label><br>
					<input type="checkbox" name="attr2102" id="attr2102" class="marginLeft20"/><label for="attr2102">����ֵ�����</label><br>
					<input type="checkbox" name="attr2103" id="attr2103" class="marginLeft20"/><label for="attr2103">����ֵ�������Ҫ������</label><br>
					<input type="checkbox" name="attr2104" id="attr2104" class="marginLeft20"/><label for="attr2104">����ί���������Ź�������</label><br>
					<input type="checkbox" name="attr2105" id="attr2105" class="marginLeft20"/><label for="attr2105">���ɹ���������������</label><br>
					<input type="checkbox" name="attr2106" id="attr2106" class="marginLeft20"/><label for="attr2106">�����ί��ǻ���������ί���ӳ�Ա</label><br>
					<input type="checkbox" name="attr2107" id="attr2107" class="marginLeft20"/><label for="attr2107">�ͼ��첿��</label><br>
					<input type="checkbox" name="attr2108" id="attr2108" class="marginLeft20"/><label for="attr2108">���꼰���ϻ��㹤������</label><br>
				</td>	
			</tr>	
				
			
		</table>
		<!-- ��ҵ�ͻ������� -->
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2201" id="attr2201" class="marginLeft20"/><label for="attr2201">��Ӫ��ҵ������</label><br>
					<input type="checkbox" name="attr2202" id="attr2202" class="marginLeft20"/><label for="attr2202">��Ӫ��ҵ�в�</label><br>
					<input type="checkbox" name="attr2203" id="attr2203" class="marginLeft20"/><label for="attr2203">������ҵ�з�������</label><br>
					<input type="checkbox" name="attr2204" id="attr2204" class="marginLeft20"/><label for="attr2204">������ҵ�ⷽ����</label><br>
					<input type="checkbox" name="attr2205" id="attr2205" class="marginLeft20"/><label for="attr2205">������ҵ�в�</label><br>
					<input type="checkbox" name="attr2206" id="attr2206" class="marginLeft20"/><label for="attr2206">������ҵ������</label><br>
					<input type="checkbox" name="attr2207" id="attr2207" class="marginLeft20"/><label for="attr2207">������ҵ���Ÿ�����</label><br>
					<input type="checkbox" name="attr2208" id="attr2208" class="marginLeft20"/><label for="attr2208">�����������������֯</label><br>
					<input type="checkbox" name="attr2209" id="attr2209" class="marginLeft20"/><label for="attr2209">������֯</label><br>
				</td>	
			</tr>
			
			
		</table>
		<!-- ��У(����Ժ��)���� -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2301" id="attr2301" class="marginLeft20"/><label for="attr2301">��ְ</label><br>
					<input type="checkbox" name="attr2302" id="attr2302" class="marginLeft20"/><label for="attr2302">��ְ</label><br>
					<input type="checkbox" name="attr2303" id="attr2303" class="marginLeft20"/><label for="attr2303">������ְ</label><br>
					<input type="checkbox" name="attr2304" id="attr2304" class="marginLeft20"/><label for="attr2304">���Ÿ�ְ</label><br>
					<input type="checkbox" name="attr2305" id="attr2305" class="marginLeft20"/><label for="attr2305">����ѧԺ����֯���</label><br>
					<input type="checkbox" name="attr2306" id="attr2306" class="marginLeft20"/><label for="attr2306">����ѧԺԺ��</label><br>
					<input type="checkbox" name="attr2307" id="attr2307" class="marginLeft20"/><label for="attr2307">����ѧԺ��ְ</label><br>
					<input type="checkbox" name="attr2308" id="attr2308" class="marginLeft20"/><label for="attr2308">У��ί</label><br>
				</td>	
			</tr>
		</table>	
		<!-- �������� -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="attr2401" id="attr2401" class="marginLeft20"/><label for="attr2401">����ѧϰ����</label><br>
					<input type="checkbox" name="attr2402" id="attr2402" class="marginLeft20"/><label for="attr2402">���⹤������</label><br>
					<input type="checkbox" name="attr2403" id="attr2403" class="marginLeft20"/><label for="attr2403">Ƹ���ƹ���Ա</label><br>
					<input type="checkbox" name="attr2404" id="attr2404" class="marginLeft20"/><label for="attr2404">����ѡ��</label><br>
					<input type="checkbox" name="attr2405" id="attr2405" class="marginLeft20"/><label for="attr2405">��ѧ�����</label><br>
					<input type="checkbox" name="attr2406" id="attr2406" class="marginLeft20"/><label for="attr2406">������תҵ����</label><br>
					<input type="checkbox" name="attr2407" id="attr2407" class="marginLeft20"/><label for="attr2407">��ʦְ���Ͼ�ת</label><br>
					<input type="checkbox" name="attr2408" id="attr2408" class="marginLeft20"/><label for="attr2408">���쵼��ϵ��</label><br>
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
				<odin:button text="��&nbsp;&nbsp;��" property="save" />
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

//���ݸ��ڵ��л��ӽڵ�
function changeTag(node, codevalue){
	var codevaluelist = ['01', '02', '03', '04'];
	for(var i = 0,len=codevaluelist.length; i < len; i++) {
		document.getElementById("tag" + codevaluelist[i]).style.display = "none";
	}
	document.getElementById("tag" + codevalue).style.display = "block";
	changeTagMenuHover(node);
}

//��ǩ��
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
 * ******************************************�˴��������з��������޸����˵������ͣ��ʽ������ʹ�á�*******************************************************
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

//�˵����������������뿪�¼�
function leftMenuHover(){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		bindOnmouseEvent(nodes[i]);
	}
}

/*
 * ��дgetElementsByClassName()������IE8������û�и÷���
 */
function getElementsByClassName(className, tagName) {
    if (document.getElementsByClassName) {
        // ʹ�����з���
        return document.getElementsByClassName(className);
    } else {
        // ѭ���������б�ǩ�����ش�����Ӧ������Ԫ��
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
    var arr=tagStr.className.split(/\s+/ );  //���������ʽ����Ϊclass�����ж��,�ж��Ƿ����  
    for (var i=0;i<arr.length;i++){  
           if (arr[i]==className){  
                 return true ;  
           }  
    }  
    return false ;  
}

function bindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#FFFFFF"; };//����뿪�¼�
	node.onmousedown=function(){node.style.backgroundColor = "#1E90FF";};//�����ʱ�����¼�
}

function unbindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#1E90FF"; };//����뿪�¼�
	node.onmousedown=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ʱ�����¼�
}

</script>
