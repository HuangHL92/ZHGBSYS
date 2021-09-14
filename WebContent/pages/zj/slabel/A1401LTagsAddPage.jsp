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
	height: 400px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
}

#left_div {
	width: 240px;
	height: 410px;
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
	height: 410px;
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
</style>
<div id="tag_container">
	<div id="left_div">
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">���ر���λ���ʹ��</div>
		<div class="leftMenu" onclick="changeTag(this, '02');">����������(��),��У,����ʹ��</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">����������ʹ��</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">�ָ�λѹ������,�ָڽ���,��У��ѵ��</div>
	</div>
	<div id="right_div">
	    <!-- ���ر���λ��� -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="tag0101" id="tag0101" >
					<label>���ر���λ</label>
				</td>	
			</tr>
		</table>
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0201" id="tag0201" >
					<label>����(��)</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0202" id="tag0202" >
					<label>��У</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0203" id="tag0203" >
					<label>����</label>
				</td>	
			</tr>
		</table>
		<!-- ����������(��),��У,����ʹ�� -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0301" id="tag0301" >
					<label>�е�ί��������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0302" id="tag0302" >
					<label>Ⱥ������ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0303" id="tag0303" >
					<label>���취 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0304" id="tag0304" >
					<label>�����ۺϳǽ���ͨ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0305" id="tag0305" >
					<label>�����Ļ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0306" id="tag0306" >
					<label>����ִ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0307" id="tag0307" >
					<label>ũ��ˮ</label>
				</td>	
			</tr>
		</table>	
		<!-- �ָ�λѹ������,�ָڽ���,��У��ѵ,���ɶ����� -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0401" id="tag0401" >
					<label>�ָ�λѹ������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0402" id="tag0402" >
					<label>�ָڽ��� </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0403" id="tag0403" >
					<label>��У��ѵ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0404" id="tag0404" >
					<label>���ɶ���</label>
				</td>	
			</tr>
		</table>	

	</div>
</div>
<%-- <div id="bottom_div">
	<table>
		<tr>
			<td align="center" >
				<odin:button text="��&nbsp;&nbsp;��" property="save" />
			</td>		
		</tr>		
	</table>
</div>  --%>
<table align="center" width="96%">	
			<td align="center">
				<img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('save')">
			</td>
</table>
<script type="text/javascript">
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
