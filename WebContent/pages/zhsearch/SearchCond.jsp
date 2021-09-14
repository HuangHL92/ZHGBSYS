<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
String qtype = request.getParameter("qt");
String qid = request.getParameter("qid");
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
 

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
  
<%@page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.ShowControl"%>
<%@page import="javax.servlet.http.HttpSession"%>


<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="修改" id="updateBtn" icon="images/keyedit.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> 
	<odin:buttonForToolBar text="删除" id="deleteBtn" icon="images/qinkong.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;" >
<div id="btnToolBarDiv" style="width: 100%;" ></div>
<table id="pictable" style="width: 100%;display: none" cellspacing="0px" cellspacing="1">
	<tr>
		<td align="center" style="height: 50%;width: 20%;"><span id="i0" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c0" type="checkbox" onclick="checkThis()"/><span id="p0" onclick="clickThis(id)"><font id="f0" style="cursor: pointer;"></font><br><font id="g0" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i1" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c1" type="checkbox" onclick="checkThis()"/><span id="p1" onclick="clickThis(id)"><font id="f1" style="cursor: pointer;"></font><br><font id="g1" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i2" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c2" type="checkbox" onclick="checkThis()"/><span id="p2" onclick="clickThis(id)"><font id="f2" style="cursor: pointer;"></font><br><font id="g2" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i3" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c3" type="checkbox" onclick="checkThis()"/><span id="p3" onclick="clickThis(id)"><font id="f3" style="cursor: pointer;"></font><br><font id="g3" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i4" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c4" type="checkbox" onclick="checkThis()"/><span id="p4" onclick="clickThis(id)"><font id="f4" style="cursor: pointer;"></font><br><font id="g4" style="cursor: pointer;"></font></span></span></td>
	</tr>                                                                                                                                                                                                                                                                                                                                 
	<tr>                                                                                                                                                                                                                                                                                                                                  
		<td align="center" style="height: 50%;width: 20%;"><span id="i5" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c5" type="checkbox" onclick="checkThis()"/><span id="p5" onclick="clickThis(id)"><font id="f5" style="cursor: pointer;"></font><br><font id="g5" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i6" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c6" type="checkbox" onclick="checkThis()"/><span id="p6" onclick="clickThis(id)"><font id="f6" style="cursor: pointer;"></font><br><font id="g6" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i7" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c7" type="checkbox" onclick="checkThis()"/><span id="p7" onclick="clickThis(id)"><font id="f7" style="cursor: pointer;"></font><br><font id="g7" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i8" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c8" type="checkbox" onclick="checkThis()"/><span id="p8" onclick="clickThis(id)"><font id="f8" style="cursor: pointer;"></font><br><font id="g8" style="cursor: pointer;"></font></span></span></td>
		<td align="center" style="height: 50%;width: 20%;"><span id="i9" style="background-color: #6495ED;" ondblclick="querypep(name)"><br><br><input id="c9" type="checkbox" onclick="checkThis()"/><span id="p9" onclick="clickThis(id)"><font id="f9" style="cursor: pointer;"></font><br><font id="g9" style="cursor: pointer;"></font></span></span></td>
	</tr>
	
	<tr style="background-color: #cedff5" height="20px">
		<td colspan="5">
			<table>
				<tr>
					<td><odin:button text="首页" property="fristpage" handler="fristpage"/></td>
					<td><odin:button text="上一页" property="lastpage" handler="lastpage"/></td>
					<td><odin:textEdit property="page" label="当前页:" width="50" readonly="true" value="1"/></td>
					<td align="left" width="60px"><font style="font-size: 12">共</font><font id="page2" style="font-size: 12"></font><font style="font-size: 12">页</font></td>
					<td><odin:button text="下一页" property="nextpage" handler="nextpage"/><td>
					<td><odin:button text="尾页" property="endpage" handler="endpage"/><td>
					<!-- <td align="left" width="120px"><input type="checkbox" id="checkAll3" onclick="selectAllPeople()"><font style="font-size: 13">全部人员</font></td> -->
					<td align="right" width="600px"><font style="font-size: 12">共</font><font id="l" style="font-size: 12"></font><font style="font-size: 12">条记录</font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>		
</div>
<odin:hidden property="picA0000s"/>
<odin:hidden property="tableType" value="1"/>
<odin:hidden property="personq" value=""/>
<odin:hidden property="sql" value=""/>
<script type="text/javascript">
function groupQuery(qid){
	radow.doEvent("initListAddGroupFlag");
	$h.openPageModeWin('group','pages.customquery.Group&qid='+qid,'组合查询',1200,720,qid,contextPath,null,
		{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}
/* 自定义查询  青 岛 */
function userDefined2(qid,a,b,c,cid){
	var subWinIdBussessId2 = "";
	if(cid==""){
		subWinIdBussessId2 = qid;
	}
	$h.openPageModeWin('win1','pages.cadremgn.infmtionquery.UserDefinedQuery&qid='+qid,'自定义查询',1250,604,subWinIdBussessId2,'<%=request.getContextPath() %>');
}
function nextpage(){
	var page=document.getElementById("page").value;
	var length=document.getElementById("l").innerHTML;
	var maxpage=Math.ceil(length/10);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		//alert("已经是最后一页");
		Ext.Msg.alert("系统提示","已经是最后一页！");
		return;
	}
	for(var i=0;i<10;i++){
		/* document.getElementById("i"+i).src="";
		document.getElementById("i"+i).alt=""; */
		document.getElementById("i"+i).name="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("g"+i).innerHTML="";
		document.getElementById("c"+i).checked=false;
		//document.getElementById("i"+i).style.display="none";
		document.getElementById("c"+i).style.display="none";
	}
	document.getElementById("picA0000s").value = "";
	document.getElementById("page").value=newpage;
	radow.doEvent("picshow");
	//checkAllPeople();
}
function lastpage(){
	var page=document.getElementById("page").value;
	if(page=="1"){
		//alert("已经是第一页");
		Ext.Msg.alert("系统提示","已经是第一页！");
		return;
	}
	for(var i=0;i<10;i++){
		/* document.getElementById("i"+i).src="";
		document.getElementById("i"+i).alt=""; */
		document.getElementById("i"+i).name="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("g"+i).innerHTML="";
		document.getElementById("c"+i).checked=false;
		//document.getElementById("i"+i).style.display="none";
		document.getElementById("c"+i).style.display="none";
	}
	document.getElementById("picA0000s").value = "";
	var newpage=parseInt(page)-1;
	document.getElementById("page").value=newpage;
	radow.doEvent("picshow");
	//checkAllPeople();
}
 function fristpage(){
	 for(var i=0;i<10;i++){
			/* document.getElementById("i"+i).src="";
			document.getElementById("i"+i).alt=""; */
			document.getElementById("i"+i).name="";
			document.getElementById("f"+i).innerHTML="";
			document.getElementById("g"+i).innerHTML="";
			document.getElementById("c"+i).checked=false;
			//document.getElementById("i"+i).style.display="none";
			document.getElementById("c"+i).style.display="none";
		}
	 document.getElementById("picA0000s").value = "";
	 document.getElementById("page").value=1;
	 radow.doEvent("picshow");
	 //checkAllPeople();
 }
 function endpage(){
	 var page=document.getElementById("page").value;
		var length=document.getElementById("l").innerHTML;
		var maxpage=Math.ceil(length/10);
		for(var i=0;i<10;i++){
			/* document.getElementById("i"+i).src="";
			document.getElementById("i"+i).alt="";*/
			document.getElementById("i"+i).name=""; 
			document.getElementById("f"+i).innerHTML="";
			document.getElementById("g"+i).innerHTML="";
			document.getElementById("c"+i).checked=false;
			//document.getElementById("i"+i).style.display="none";
			document.getElementById("c"+i).style.display="none";
		}
		document.getElementById("picA0000s").value = "";
		document.getElementById("page").value=maxpage;
		radow.doEvent("picshow");
		//checkAllPeople();
 }

function picshow(){
	//如果此时已经打开显示子集，则将其隐藏
	document.getElementById("pictable").style.display='block';
	for(var i=0;i<10;i++){
		//document.getElementById("i"+i).innerHTML="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("i"+i).name="";
		//document.getElementById("i"+i).style.display="none";
		document.getElementById("c"+i).style.display="none";
	}
	document.getElementById("page").value="1";
	radow.doEvent("Show");
}
/* 照片的方法 */
function setlength(size){
	document.getElementById("l").innerHTML=size;
	var p = size/10;
	document.getElementById("page2").innerHTML = Math.ceil(p);
}

function checkThis(){
	var picA0000s = "";
	for(var i=0;i<10;i++){
		var tof = document.getElementById("c"+i).checked;
		if(tof){
			var node = document.getElementById("i"+i);
			var a0000 = node.name;
			
			picA0000s ="'" + a0000 + "'," + picA0000s;
		}
	}
	document.getElementById("picA0000s").value = picA0000s;
}
function clickThis(id){
	var tableType = document.getElementById("tableType").value;
	if(tableType == "2"){
		var newid = id.replace("sp","datac");
	}
	if(tableType == "3"){
		var newid = id.replace("p","c");
	}
	var tof = document.getElementById(newid).checked;
	if(tof==false){
		document.getElementById(newid).checked = true;
	}
	if(tof==true){
		document.getElementById(newid).checked = false;
	}
	checkThis();
}
function querypep(name){
	 if(name==""){
		 return;
	 }
	 //alert(name);
	 //alert(typeof name);
	 var arr = name.split('@');
	 var p = '';
	 if(arr[1]=='1'){
		 p = '&qt=1&qid='+arr[0];
		 setTimeout("parent.addTab1('402882f265c693770165c6c7d65b0007','综合查询','/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch"+p+"')",10 );
	 }else if(arr[1]=='2'){
		 p = '&qt=2&qid='+arr[0];
		 setTimeout("parent.addTab1('402882f265c693770165c6c7d65b0007','综合查询','/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch"+p+"')",10 );
	 }
	 
	 //radow.doEvent("updateBtn.onclick");
}

function showpic(i,qid,qname,lx,qtext){
	//document.getElementById("i"+i).src=path;
	/* document.getElementById("i"+i).style.display="";
	document.getElementById("i"+i).alt=a0101;*/
	document.getElementById("i"+i).name=qid+'@'+lx; 
	document.getElementById("f"+i).innerHTML=qname;
	document.getElementById("g"+i).innerHTML=qtext;
	document.getElementById("c"+i).style.display="";
	
}
Ext.onReady(function(){
	document.getElementById("girdDiv").parentNode.style.width=document.body.clientWidth+'px';
	var viewSize = Ext.getBody().getViewSize();
	var pictable = document.getElementById("pictable");
	pictable.style.height = viewSize.height-42 + "px";
	picshow();
	
})
</script>
