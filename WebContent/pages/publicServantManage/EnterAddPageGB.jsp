<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<odin:hidden property="a0000" title="人员主键"/>
<style>
body {
	background-color: rgb(214,227,243);
}
/* #one {
	position: absolute;
	left: 200px;
	top: 100px;
	width: 20px;
} */
</style>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<table style="width:100%;margin-top: 50px;" >
	<tr>
		<odin:NewDateEditTag property="a2907" isCheck="true" label="进入本单位日期" maxlength="8"  ></odin:NewDateEditTag>
		<tags:PublicTextIconEdit property="a2911" label="进入本单位变动类别" codetype="ZB77"  readonly="true" />
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a2921a" label="进入本单位前工作单位名称"  ></odin:textEdit>
		<odin:textEdit property="a2941" label="在原单位职务"  ></odin:textEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a2944" codetype="ZB09" label="在原单位职务层次"  readonly="true"/>
		<td><span style="font-size: 12px;float:right;">进入本单位时基层工作经历时间:</span></td>
		<td>
				<table >
					<tr>
						<odin:numberEdit property="a2947a_Y"  maxlength="2" width="60" />
						<td><span style="font-size: 12px">年</span></td>
						<td><div style="width: 8px"></div></td>
						<odin:numberEdit property="a2947a_M" maxlength="2" width="60" />
						<td><span style="font-size: 12px">月</span></td>
					</tr>
				</table>
		</td>	
	</tr>

</table>
<%-- <div id="one"><img src="<%=request.getContextPath()%>/image/quanxian1.png"> </div> --%>

<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A29")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A29")%>;


Ext.onReady(function(){
	
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	for(var i=0; i<fieldsDisabled.length; i++){
		var formfield = fieldsDisabled[i].split("_")[1];
		if(formfield=='a2947a'){
			Ext.getCmp(formfield+'_Y').disable();
			$('#'+formfield+'_Y').addClass('bgclor');
			Ext.getCmp(formfield+'_M').disable();
			$('#'+formfield+'_M').addClass('bgclor');
			/* alert($('#'+formfield+'_M').offset().top);
			alert($('#'+formfield+'_M').offset().left); */
			//alert(document.getElementById('#'+formfield+'_M').offset().top);
			<%-- console.log(document.getElementById("a2941"));
			alert(document.getElementById("a2941").clientWidth);
			alert(document.getElementById("a2941").clientHeight);
		　　　　//添加 div
		　　　　var div = document.createElement("div");
		　　　　//设置 div 属性，如 id
				div.style.cssText="width:80px;height:20px;border:1px solid #000;";
				div.style.position = "absolute";
				div.style.left = $('#'+formfield+'_Y').offset().left+'px';
				div.style.top = $('#'+formfield+'_Y').offset().top+'px';
		　　　　div.innerHTML = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
		　　　　document.body.appendChild(div); --%>
		}
	}
	for(var i=0; i<selectDisabled.length; i++){
		var formfield = selectDisabled[i].split("_")[1];
		if(formfield=='a2947a'){
			var div_y = document.createElement("div");
			div_y.style.border="1px solid rgb(192,192,192)";
			div_y.style.width = document.getElementById(formfield+"_Y").offsetWidth;
			div_y.style.height = document.getElementById(formfield+"_Y").offsetHeight;
			div_y.style.position = "absolute";
			div_y.style.left = $('#'+formfield+"_Y").offset().left+'px';
			div_y.style.top = $('#'+formfield+"_Y").offset().top+'px';
	　　　　div_y.style.backgroundImage = imgdata;
			div_y.style.backgroundRepeat = "no-repeat";
			div_y.style.backgroundColor = "white";
			div_y.style.backgroundPosition = "center";
	　　　　document.body.appendChild(div_y);
			var div_m = document.createElement("div");
			div_m.style.border="1px solid rgb(192,192,192)";
			div_m.style.width = document.getElementById(formfield+"_M").offsetWidth;
			div_m.style.height = document.getElementById(formfield+"_M").offsetHeight;
			div_m.style.position = "absolute";
			div_m.style.left = $('#'+formfield+"_M").offset().left+'px';
			div_m.style.top = $('#'+formfield+"_M").offset().top+'px';
			div_m.style.backgroundImage = imgdata;
			div_m.style.backgroundRepeat = "no-repeat";
			div_m.style.backgroundColor = "white";
			div_m.style.backgroundPosition = "center";
			document.body.appendChild(div_m);
			Ext.getCmp(formfield+'_Y').disable();
			$('#'+formfield+'_Y').addClass('bgclor');
			Ext.getCmp(formfield+'_M').disable();
			$('#'+formfield+'_M').addClass('bgclor');
			/* var div_m = document.createElement("div");
			div_m.style.width = document.getElementById(formfield+"_M").clientWidth;
			div_m.style.height = document.getElementById(formfield+"_M").clientHeight;
			div_m.style.position = "absolute";
			div_m.style.left = $('#'+formfield+"_M").offset().left+'px';
			div_m.style.top = $('#'+formfield+"_M").offset().top+'px';
			div_m.innerHTML = imgdata;
			document.body.appendChild(div_m); */
		}
	}	

	<%-- for(var i=0; i<selectDisabled.length; i++){
		var formfield = selectDisabled[i].split("_")[1]+"_1";
		alert(formfield);
		alert(document.getElementById(formfield).clientWidth);
		var div = document.createElement("div");
　　　　//设置 div 属性，如 id
		//div.style.cssText="width:80px;height:20px;border:1px solid #000;";
		div.style.width = document.getElementById(formfield).clientWidth;
		div.style.height = document.getElementById(formfield).clientHeight;
		div.style.position = "absolute";
		div.style.left = $('#'+formfield).offset().left+'px';
		div.style.top = $('#'+formfield).offset().top+'px';
　　　　div.innerHTML = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
　　　　document.body.appendChild(div);
	} --%>
});
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function save(){
	radow.doEvent('save');
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}


</script>



