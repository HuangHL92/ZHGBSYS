<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<style>#all .menu   { background-color: menu; cursor: hand; display: none; font-family: Arial;
               font-size: 9pt; color: 800000; left: 0px; overflow: hidden;
               position: absolute; top: 0px; border-left: 1px solid #efefef;
               border-right: 1px solid #505050; border-top: 1px solid #efefef;
               border-bottom: 1px solid #505050; margin: 0pt; padding: 3pt }
#all .menu span { cursor: default; width: 100%; padding-left: 10pt }
#all .menu span.selected { background-color: red; background-repeat: repeat; background-attachment:
               scroll; color: white; background-position: 0% }
body         { font-family: Verdana; color: #000080; font-size: 9pt }
</style>
	<head>
        <link href=<%=request.getContextPath()%>/css/tree.jsp rel="stylesheet" type="text/css">
        <link href=<%=request.getContextPath()%>/css/style.jsp rel="stylesheet" type="text/css">
        <script src=<%=request.getContextPath()%>/js/xtree.jsp></script>
        <script src=<%=request.getContextPath()%>/js/xmlextras.jsp></script>
        <script src=<%=request.getContextPath()%>/js/xloadtree.jsp></script>
        <script src=<%=request.getContextPath()%>/js/Globals.jsp></script>
	</head>
<SCRIPT>
// Define global script variables
var bContextKey=false;
var groupid="";
// The fnDetermine function performs most of the work

function fnGetContextID(el) {
	while (el!=null) {
		if (el.contextmenu) return el.contextmenu
		el = el.parentElement
	}
	return ""
}

function fnDetermine(){
	oWorkItem=event.srcElement;
    //判断是否是所点击连接弹出菜单上按钮
    var srceArray=oWorkItem.id.split("-");
    //如果是左键则
    if(event.button==1&&srceArray[0]=="webfx"&&srceArray[1]=="tree"&&srceArray[2]=="object"&&srceArray[4]=="anchor"){
		    var ids=event.srcElement.href.split("(");
		    ids=ids[1].split(")");
		    ids=ids[0].split(",");
		    name=ids[0];
		    value=ids[1];
		    names=name.split("'");
		    name=names[1];
		    values=value.split("'");
		    value=values[1];
		    editgroup(name,value);
    }
	// Proceed if the desired keyboard key is pressed.
	if(bContextKey==true){
		// If the menu STATUS is false, continue.
		if(oContextMenu.getAttribute("status")=="false"&&srceArray[0]=="webfx"&&srceArray[1]=="tree"&&srceArray[2]=="object"&&srceArray[4]=="anchor"){
		//获取参数
		    var groupids=event.srcElement.href.split("(");
		    groupids=groupids[1].split(")");
		    groupids=groupids[0].split(",");
		    groupid=groupids[1];
		    if(undefined==groupids[1]){
    		    groupid=undefined;
		    }else{
		    groupid=groupid.split("'");
		    groupid=groupid[1];
		    }
			// Give the menu mouse capture so it can interact better with the page.
			oContextMenu.setCapture();
			// Relocate the menu to an offset from the mouse position.
			oContextMenu.style.top=event.clientY + document.body.scrollTop + 1;
			oContextMenu.style.left=event.clientX + document.body.scrollLeft + 1;
			oContextMenu.innerHTML="";
			// Set its STATUS to true.
			var sContext ="demo"
			if (sContext!="") {
				fnPopulate(sContext)
				oContextMenu.setAttribute("status","true");
				event.returnValue=false;
			}
			else
				event.returnValue=true
		}
	}
	else{
		// If the keyboard key was not pressed and the menu status is true, continue.
		if(oContextMenu.getAttribute("status")=="true"){
			// If the object that was clicked has the menu for a parent and it has a COMPONENT attribute equal to "menuitem", continue.
			if((oWorkItem.parentElement.id=="oContextMenu")&&(oWorkItem.getAttribute("component")=="menuitem")){
				fnFireContext(oWorkItem)
			}
			// Reset the context menu, release mouse capture, and hide it.
			oContextMenu.style.display="none";
			oContextMenu.setAttribute("status","false");
			oContextMenu.releaseCapture();
			oContextMenu.innerHTML="";
			event.returnValue=false;
		}
	}
}
function fnPopulate(sID) {
    	var str=""
		str='<span component="menuitem" menuid=view id=oMenuItem0>查看</span><BR>';
		str+='<span component="menuitem" menuid=add id=oMenuItem1>增加</span><BR>';
		str+='<span component="menuitem" menuid=update id=oMenuItem2>修改</span><BR>';
		str+='<span component="menuitem" menuid=delete id=oMenuItem3>删除</span><BR>';
		oContextMenu.innerHTML=str;
		oContextMenu.style.display="block";
		oContextMenu.style.pixelHeight = oContextMenu.scrollHeight;
}

function fnFireContext(oItem) {
    var right = parent.document.all("right");
	switch (oItem.menuid) {
		case "view":
            right.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=findByKey&groupid="+groupid;
			break;
		case "add":
            right.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=findByKey&actiontype=add&groupid="+groupid;
			break;
		case "update":
            right.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=findByKey&actiontype=update&groupid="+groupid;
			break;
		case "delete":
            right.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=delete&actiontype=delete&groupid="+groupid;
            var left = parent.document.all("left");
            left.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=loadGroupTree";
			break;
		default:
			alert("You Selected:\nID: " + oItem.menuid + "\nText: " + oItem.innerText)
	}
}

/* The chirp functions provide visual appeal.  Notice that they are specific about only changing styles while the mouse is within the client window.  This is because setCapture allows the mouse to pick up coordinate values on the window (not the user's screen) and this can throw an error if you try to get a property from an object that isn't recognized. */
function fnChirpOn(){
	if((event.clientX>0)&&(event.clientY>0)&&(event.clientX<document.body.offsetWidth)&&(event.clientY<document.body.offsetHeight)){
		oWorkItem=event.srcElement;
		if(oWorkItem.getAttribute("component")=="menuitem"){
			oWorkItem.className = "selected"
		}
	}
}
function fnChirpOff(){
	if((event.clientX>0)&&(event.clientY>0)&&(event.clientX<document.body.offsetWidth)&&(event.clientY<document.body.offsetHeight)){
		oWorkItem=event.srcElement;
		if(oWorkItem.getAttribute("component")=="menuitem"){
			oWorkItem.className = ""
		}
	}
}

function fnInit(){
	if (oContextMenu) {
		oContextMenu.style.width=60;
		oContextMenu.style.height=document.body.offsetHeight/2;
		oContextMenu.style.zIndex=2;
		// Setup the basic styles of the context menu.
		document.oncontextmenu=fnSuppress;
	}
}

function fnInContext(el) {
	while (el!=null) {
		if (el.id=="oContextMenu") return true
		el = el.offsetParent
	}
	return false
}

function fnSuppress(){
	if (!(fnInContext(event.srcElement))) {
		oContextMenu.style.display="none";
		oContextMenu.setAttribute("status","false");
		oContextMenu.releaseCapture();
		bContextKey=true;
	}

	fnDetermine();
	bContextKey=false;
}
window.onload = fnInit;
document.onmousedown = fnDetermine;
</SCRIPT>
<div class="menu" id="oContextMenu" onmouseout="fnChirpOff()" onmouseover="fnChirpOn()" status="false" style="width: 10; height: 20">
</div>
 <body id="all" contextmenu="demo">
<table border="1" cellPadding="1" cellSpacing="1"  bordercolor="#FFFFFF" width="100%">
<tr>
<td>
<table width="100%">
  <tr>
    <td>
        <table class="divTable" border="0" cellPadding="0" cellSpacing="0" width="100%">
          <tr>
              <td width="46%" nowrap><font  face=Verdana color="#ffffff"><strong>机构列表</strong></FONT></td>
              <td width="27%" class="tableHead"></td>
              <td width="27%" class="tableHead"></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td>
        <table width="100%">
        <%
        	String condition = request.getParameter("condition");
        	if(null == condition)
        		condition = "";
        %>
            <tr><td valign="top" height="360"><sicp3:tree property='group' condition="<%=condition%>"/></td></tr>
        </table>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
<script>
function addNode(display_name,display_value) {
}
function editgroup(dispaly_value,code_value){//
    var right = parent.document.all("right");
    right.src="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=findByKey&groupid="+code_value;
}
function resetTree(title,sid,orderno,actionFlag){
    //添加
  if(actionFlag=='add'){
    if(regionTree.getSelected().text=='机构列表'){
                   regionTree.getSelected().add(new WebFXTreeItem(title,"javascript:editgroup('"+title+"','"+sid+"')"));
    }else{
    //删除焦点所在节点所有子节点
    var hasNodes=new Array();
	var sum=regionTree.getSelected().childNodes.length
	for(i=0;i<sum;i++){
	     hasNodes[i]=regionTree.getSelected().childNodes[0];
         regionTree.getSelected().childNodes[0].remove();
	}
    //重新排序
    var flag=0;
	for(i=0;i<hasNodes.length;i++){
     	var tmpnode=hasNodes[i].action.split("(");
		    tmpnode=tmpnode[1].split(")");
		    tmpnode=tmpnode[0].split(",");
		    no=tmpnode[2];
		    var no1=parseInt(no,10);
		    var orderno1=parseInt(orderno,10);
		    if(no1>orderno1){
		        if(flag==0){
                   regionTree.getSelected().add(new WebFXTreeItem(title,"javascript:editgroup('"+title+"','"+sid+")'"));
                   flag=1;
    		    }
		    }
            regionTree.getSelected().add(hasNodes[i]);
	}
		    if(flag==0){
                 regionTree.getSelected().add(new WebFXTreeItem(title,"javascript:editgroup('"+title+"','"+sid+"')"));
                 flag=1;
		    }
	regionTree.getSelected().expand();
  }
  }
  //修改
  if(actionFlag=='update'){
     var href="javascript:editgroup('"+title+"','"+sid+"')";
	 url="<a href=\"" +href+ "\" id=\"" +regionTree.getSelected().id+ "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" +">" +title+ "</a>";
     document.all(regionTree.getSelected().id+"-anchor").outerHTML=url;
     //设置焦点
     regionTree.getSelected().focus();
  }
  //修改
  if(actionFlag=='delete'){
      if(regionTree.getSelected()){
          regionTree.getSelected().remove();
      }
  }
}
</script>