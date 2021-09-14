<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%
String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%> 
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.InfoComWindowPageModel"%>
<odin:head/>
<link href="<%=ctxPath %>/picCut/css/rmb.css" type="text/css" rel="Stylesheet" /> 
<style>

	.x-form-item{ 
		width: 100%;
		height: 100%;
		margin: 0px 0px 0px 0px;
		padding: 0px 0px 0px 0px;
	}
	
	
	.fontdisplay{
		padding-top: 10px !important;
	}
	
	div.TAwrap {   
	  display:table;   
	  _position:relative;   
	  overflow:hidden;   
	}   
	div.TAsubwrap {   
	
	  vertical-align:middle;   
	  display:table-cell;   
	  _position:absolute;   
	  z-index:-100;
	  _top:47%;  
	  
	}   
	div.TAcontent {   
	word-wrap:break-word;
		font-family: '宋体';
		font-size:19px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		line-height:100%;
	  _position:relative;   
	  _top:-47%;  
	  _left:  -50%; 
	}
.v_standard_left tr td{
		border:0px;
		text-align: left;
		}
		
.cellbgclor{
	background-color: rgb(242,247,253)!important;
	background-image: none!important;
}
</style>
<script type="text/javascript" src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';

  

function namevalidator(value){
	if(value.length>18){
		return '姓名字数不能超过18';
	}
	var a0101obj = document.getElementById('a0101');
	//去除前面的点
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='。'||firstStr==='·'){
			a0101obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//将。.转为·
	if(value.match(/\.|。|\r\n/g)){
		a0101obj.value = value.replace(/\.|。/g,"·").replace(/\r\n/g,"");
	}
	return true;
}
function namevalidator2(obj){
	var value = obj.value;
	if(value.length>18){
		return '姓名字数不能超过18';
	}
	var a0101obj = obj;
	//去除前面的点
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='。'||firstStr==='·'){
			a0101obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//将。.转为·
	if(value.match(/\.|。|\r\n/g)){
		a0101obj.value = value.replace(/\.|。/g,"·").replace(/\r\n/g,"");
	}
	return true;
}



</script>

<div id="floatToolDiv" style="margin-top: 27px; position: absolute;top: 0px;width:98%;z-index:1"></div>


<div id="divrmb1" style="display: none;">

	<br/><br/>
	<%-----------------------------人员基本信息-------------------------------------------------------%>
	<odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
	<table cellpadding="0px;" align="center">
		<tr>
			<td>
				<table id="v_standard" class="v_standard" cellspacing="0px" cellpadding="0px;" align="left">
					<%--第1行- onblur="nameCheck(this);"--%>
					<tr>
						<td class="label-clor widthcol1-66 heightrow1-46" id="a0101SpanId_s">姓&nbsp;&nbsp;名</td>
						<td class="width01-86"><tags:RMBTextView property="a0101" validator="namevalidator"  cls="widthcol2-86 heightrow1-46 no-y-scroll cellbgclor" label="姓名"/></td>
						<td class="label-clor widthcol3-70" id="a0104SpanId_s">性&nbsp;&nbsp;别</td>
						<td class="width24-80"><tags:RMBTextView property="a0104" cls=" widthcol4-86 heightrow1-46 no-y-scroll cellbgclor"/></td>
						<td class="label-clor widthcol5-86" id="a0107SpanId_s">出生年月<br/><font style="line-height: 22px;">(岁)</font></td>
						<td class="width56-75"><tags:RMBTextView property="a0107" label="出生年月" cls="widthcol6-88 height05-46 no-y-scroll cellbgclor" /> <tags:RMBTextView property="a0107_s" label="出生年月" cls="widthcol6-88 height05-46 no-y-scroll cellbgclor" /></td>
						<td class="left-last label-clor width06-136" rowspan="4">
							<div style="width:136px; height:184px;cursor: pointer;" >
								<img alt="照片" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="136" height="184" src=""  /> 
							</div>
						</td>
					</tr>
					<%--第2行----%>
					<tr>
						<td class="label-clor heightrow2-46" id="a0117SpanId_s">民&nbsp;&nbsp;族</td>
						<td style="position: relative;"><tags:RMBTextView property="a0117" label="民族" cls="heightrow2-46 widthcol2-86 no-y-scroll cellbgclor"  />
						</td>
						<td class="label-clor" id="a0111SpanId_s">籍&nbsp;&nbsp;贯</td>
						<td style="position: relative;"><tags:RMBTextView property="a0111" label="籍贯" cls="heightrow2-46 widthcol4-86 no-y-scroll cellbgclor"  /> </td>
						<td class="label-clor"  id="a0114SpanId_s">出&nbsp;生&nbsp;地</td>
						<td style="position: relative;"><tags:RMBTextView property="a0114" cls="heightrow2-46 widthcol6-88 no-y-scroll cellbgclor" label="出生地" /></td>
						
					</tr>
					<%--第3行--%>
					<tr>
						<td class="label-clor heightrow3-46" id="a0140SpanId_s">入&nbsp;&nbsp;党<br/>时&nbsp;&nbsp;间</td>				
						<td><tags:RMBTextView property="a0140" cls="widthcol2-86 heightrow3-46 no-y-scroll cellbgclor" /></td>
						
						<td class="label-clor" id="a0134SpanId_s">参加工<br/>作时间</td>
						<td><tags:RMBTextView property="a0134" label="参加工作时间" cls="widthcol4-86 heightrow3-46 no-y-scroll cellbgclor" /></td>
							
						<td class="label-clor" id="a0128SpanId_s">健康状况</td>
						<td><tags:RMBTextView property="a0128" cls="widthcol6-88 heightrow3-46 no-y-scroll cellbgclor" label="健康状况" /></td>
					</tr>
					<%--第4行--%>
					<tr>
						<td class="label-clor heightrow4-46" id="a0196SpanId_s">专业技<br/>术职务</td>                                               
						<td colspan="2"><tags:RMBTextView property="a0196" label="专业技术职务" cls="width31-157 heightrow4-46 no-y-scroll cellbgclor" /> </td>
						<td class="label-clor" id="a0187aSpanId_s">熟悉专业<br/>有何专长</td>
						<td colspan="2"><tags:RMBTextView  property="a0187a"  cls="width33-175 heightrow4-46 no-y-scroll cellbgclor" label="专长"/></td>
					</tr>
					<%--第5行--%>
					<tr>
						<td class="label-clor height5678-80" rowspan="4" id="xlxwSpanId_s">学&nbsp;历<br/>学&nbsp;位</td>
						<td class="label-clor" rowspan="2">全日制<br/>教&nbsp;&nbsp;育</td>															
						<td colspan="2"><tags:RMBTextView property="qrzxl" label="全日制教育：学历" cls="width42-157 heightrow5-23 no-y-scroll cellbgclor" /> </td>
						<td class="label-clor" rowspan="2">毕业院校<br/>系及专业</td>
						<td class="left-last" colspan="2"><tags:RMBTextView property="qrzxlxx" label="院校系及专业(学历)" cls="width44-224 heightrow5-23 no-y-scroll cellbgclor" /> </td>
					</tr>
					<%--第6行--%>
					<tr>
						
						
						<td colspan="2"><tags:RMBTextView property="qrzxw" label="全日制教育：学位" cls="width52-157 heightrow6-23 no-y-scroll cellbgclor" /></td>
						
						<td class="left-last" colspan="2"><tags:RMBTextView property="qrzxwxx" label="院校系及专业(学位)" cls="width54-224 heightrow6-23 no-y-scroll cellbgclor" /></td>
					</tr>
					<%--第7行--%>
					<tr>
						
						<td class="label-clor" rowspan="2">在&nbsp;&nbsp;职<br/>教&nbsp;&nbsp;育</td>
						<td colspan="2"><tags:RMBTextView property="zzxl" label="在职教育：学历" cls="width62-157 heightrow7-23 no-y-scroll cellbgclor" /> </td>
						<td class="label-clor" rowspan="2">毕业院校<br/>系及专业</td>
						<td class="left-last" colspan="2"><tags:RMBTextView property="zzxlxx" label="院校系及专业(学历)" cls="width64-224 heightrow7-23 no-y-scroll cellbgclor" /></td>
					</tr>
					<%--第8行--%>
					<tr>
						
						
						<td colspan="2"><tags:RMBTextView property="zzxw" label="在职教育：学位" cls="width72-157 heightrow8-23 no-y-scroll cellbgclor" /></td>
						
						<td class="left-last" colspan="2"><tags:RMBTextView property="zzxwxx" label="院校系及专业(学位)" cls="width74-224 heightrow8-23 no-y-scroll cellbgclor" /></td>
					</tr>
					
					<%--第9行--%>
					<tr>
						<td class="label-clor heightrow9-46" colspan="2" id="a0192aSpanId_s">现  任  职  务</td>															<%--link2tab('tab_workUnits')--%>
						<td class="left-last" colspan="5"><tags:RMBTextView property="a0192a" cls="heightrow9-46 width81-470 no-y-scroll cellbgclor" label="工作单位及职务全称" /></td>
					</tr>
					
					<%--第10行--%>
					<tr>
						<td class="label-clor heightrow10-46" colspan="2" id="a0192aSpanId_s">拟  任  职  务</td>															<%--link2tab('tab_workUnits')--%>
						<td class="left-last" colspan="5"><tags:RMBTextView property="nrzw" cls="heightrow10-46 width91-470 no-y-scroll cellbgclor" label="工作单位及职务全称" /></td>
					</tr>
					
					<%--第11行--%>
					<tr>
						<td class="label-clor heightrow11-46" colspan="2" id="a0192aSpanId_s">拟  免  职  务</td>															<%--link2tab('tab_workUnits')--%>
						<td class="left-last" colspan="5"><tags:RMBTextView property="nmzw" cls="heightrow11-46 width101-470 no-y-scroll cellbgclor" label="工作单位及职务全称" /></td>
					</tr>
					
					<%--第12行--%>
					<tr>
						<td class="top-last label-clor heightrow12-475">简<br/>历</td>
						<td class="left-last top-last" colspan="6"><tags:RMBTextView property="a1701" cls="heightrow12-475 width111-557 font-left no-y-scroll cellbgclor"/>  </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="divrmb2" style="display: none;">
	<br/><br/>
	<table cellpadding="0px;" align="center">
		<tr>
			<td>
				<table id="v_standard2" class="v_standard" cellspacing="0px" >
					
					<%--第1行--%>
					<tr>
						<td class="label-clor width13-60 height5678-80" colspan="1" id="a14z101SpanId_s">奖<br/>惩<br/>综<br/>述</td>
						<td class="left-last  width-478" colspan="5"><tags:TextAreainput property="a14z101" label="奖惩综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage')" onkeypress="openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage')" readonly="true"/></td>
					</tr>
					<%--第2行--%>
					<tr>
						<td class="label-clor height5678-80" colspan="1" id="a15z101SpanId_s">年度<br/>考核<br/>结果<br/>综述</td>
						<td class="left-last" colspan="5"><tags:TextAreainput property="a15z101" label="年度考核结果综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage')" onkeypress="openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage')" readonly="true"/></td>
					</tr>
					<%--第3行--%>
					<tr>
						<td class="label-clor " rowspan="11" id="tdrowspan" style="position: relative;">
						    <span style="_position: absolute;_left: 40%;_top: 15%; vertical-align: middle;"  id="a36SpanId_s">家<br/>庭<br/>主<br/>要<br/>成<br/>员<br/>及<br/>社<br/>会<br/>重<br/>要<br/>关<br/>系</span>
							<div class="width13-60" style="_position:absolute;_left: 0px;;_top: 90%; ">
								<input class="btn3" style="width: 50px;" type="button" value="新增" id="addrowBtn" onclick="addA36row()"/>
							</div>
						</td>
						<td class="label-clor width13-60 height1234-40" id="a3604aSpanId_s">称&nbsp;&nbsp;谓</td>
						<td class="label-clor width56-75" id="a3601SpanId_s">姓&nbsp;&nbsp;名</td>
						<td class="label-clor width56-75" id="a3607SpanId_s">出生年月</td>
						<td class="label-clor width13-60 height1234-40" id="a3627SpanId_s">政&nbsp;治<br/>面&nbsp;貌</td>
						<td class="label-clor width-200 left-last" id="a3611SpanId_s">工作单位及职务</td>
						
					</tr>
					<%--第4行--%>
					<% for(Integer i=1;i<=10;i++){ 
						String a3604a_i = "a3604a_"+i;
						String a3601_i = "a3601_"+i;
						String a3607_i = "a3607_"+i;
						String a3627_i = "a3627_"+i;
						String a3611_i = "a3611_"+i;
						String a3600_i = "a3600_"+i;
						%>
					<tr>
						<td class="width13-60 height1234-40"><tags:SelectInput property="<%=a3604a_i %>" codetypeJS="GB4761" cls="width13-60 height1234-40 input-text cellbgclor"/></td>
						<td ><tags:TextAreainput2 property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="width56-75 height1234-40 no-y-scroll cellbgclor" /></td>
						<td ><tags:TextAreainput3 property="<%=a3607_i %>" cls="width56-75 height1234-40 no-y-scroll cellbgclor" vtype="dateBTY" isY="true" label="出生年月"/></td>
						<td ><tags:SelectInput property="<%=a3627_i %>" codetypeJS="GB4762" cls="height1234-40 width13-60 input-text cellbgclor"/></td>
						<td class="left-last ">
							<tags:TextAreainput2 property="<%=a3611_i %>" cls="height1234-40 width-200 no-y-scroll cellbgclor" />
							<odin:hidden property="<%=a3600_i %>"/>
						</td>
					</tr>
					<%} %>
					<%--第14行--%>
					<tr id="targetNodeTR">
						<td class="top-last label-clor height5678-80" >备<br/><br/>注</td>
						<td class="left-last top-last" colspan="5"><tags:TextAreainput property="a0180" label="备注" cls="width-478 height5678-80 font-left" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
	
<div id="rmbtabsdiv"></div>
	
<div id="moreRow" style="display: none;">
	<odin:hidden property="rowLength" value="10" />
	<table id="tableMoreRow">
		<% for(Integer i=11;i<=30;i++){ 
			String a3604a_i = "a3604a_"+i;
			String a3601_i = "a3601_"+i;
			String a3607_i = "a3607_"+i;
			String a3627_i = "a3627_"+i;
			String a3611_i = "a3611_"+i;
			String a3600_i = "a3600_"+i;
			%>
			<tr>
				<td class="width13-60 height1234-40"><tags:SelectInput property="<%=a3604a_i %>" codetypeJS="GB4761" cls="width13-60 height1234-40 input-text"/></td>
				<td ><tags:TextAreainput2 property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="width56-75 height1234-40 no-y-scroll" /></td>
				<td ><tags:TextAreainput3 property="<%=a3607_i %>" cls="width56-75 height1234-40 no-y-scroll" vtype="dateBTY" isY="true" label="出生年月"/></td>
				<td ><tags:SelectInput property="<%=a3627_i %>" codetypeJS="GB4762" cls="height1234-40 width13-60 input-text"/></td>
				<td class="left-last">
					<tags:TextAreainput2 property="<%=a3611_i %>" cls="height1234-40 width-200 no-y-scroll" />
					<odin:hidden property="<%=a3600_i %>"/>
				</td>
			</tr>
		<%} %>
	</table>
</div>


<script type="text/javascript">
var tableMoreRow = document.getElementById('tableMoreRow');
var trs = tableMoreRow.lastChild.childNodes;
var Tindex = 0;
function addA36row(){//table tbody tr input
	if(Tindex==20)return;
	var table = document.getElementById('v_standard2');
	
	//第3行第一个td 标题
	var titleNode = document.getElementById("tdrowspan");//table.lastChild.firstChild.nextSibling.nextSibling.firstChild;
	
	var targetNode = document.getElementById("targetNodeTR");//table.lastChild.lastChild;
	
	var newNode = trs[0];
	if(!newNode.innerHTML){
		newNode = trs[Tindex];
	}
	titleNode.setAttribute("rowSpan",parseInt(titleNode.getAttribute("rowspan"))+1);
	table.lastChild.insertBefore(newNode, targetNode);
	
	
	var rowLength = document.getElementById('rowLength');
	rowLength.value=parseInt(rowLength.value)+1+'';
	Tindex++;
}



Ext.onReady(function(){
	document.getElementById("divrmb1").style.display="block";
	document.getElementById("divrmb2").style.display="block";
	var tabs = new Ext.TabPanel({
		id:'rmbTabs',
		title:'helloal',
		region: 'center',
		margins: '0 3 0 0',
		activeTab: 0,
		applyTo:'rmbtabsdiv',
		enableTabScroll: true,
		autoScroll:false,
		frame : true,
		//plugins: new Ext.ux.TabCloseMenu(),
listeners:{
'tabchange':function(obj1,obj2){
	
	if(obj2.id=='rmb1'){
	}else if(obj2.id=='rmb2'){
		for(var i=1;i<=30;i++){ 
			if(document.getElementById('a3607_'+i).value!=''){
				eval('a3607_'+i+'onblur();');
			}
			if(document.getElementById('a3601_'+i).value!=''){
				eval('a3601_'+i+'onblur();');
			}
			if(document.getElementById('a3611_'+i).value!=''){
				eval('a3611_'+i+'onblur();');
			}
		}
		
	}else{}
}
},
		items: [{
			autoScroll:true,
			id:"rmb1",
			title : '任免表信息一',
			contentEl:'divrmb1'
		},{
			autoScroll:true,
			id:"rmb2",
			title : '任免表信息二',
			contentEl:'divrmb2'
		}]
	});
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	tabs.activate(tabs.getItem('rmb2'));
	tabs.activate(tabs.getItem('rmb1'));	
});




function onblurFAjust(field){
	document.getElementById("div_"+field).style.fontSize = 19;
	eval(field+'onblur("","","",false,false);');
	var objh = document.getElementById("div_"+field).offsetHeight;
	var relh = document.getElementById("wrapdiv_"+field).offsetHeight;
	var fsize = 19;
	while(objh - relh > 0){
		document.getElementById("div_"+field).style.fontSize = fsize-1;
		fsize = fsize-1;
		
		eval(field+'onblur("","","",false,false);');
		objh = document.getElementById("div_"+field).offsetHeight;
		relh = document.getElementById("wrapdiv_"+field).offsetHeight;
	}
	if(objh>fsize){
		document.getElementById("div_"+field).style.textAlign='left';
	}
	
}


</script>



