<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script src="<%=request.getContextPath()%>/layui/layer/layer.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/layui/layui.css" />


<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script src="<%=request.getContextPath()%>/layui/layer/layer.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/layui/layui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pages/css/warningPerson.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
<%@include file="/comOpenWinInit.jsp" %>

<div>
	<table style="width: 100%">
		<tr>
			<td>
			<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid property="marryGrid" hasRightMenu="false" autoFill="false"  bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/"  width="100%" height="300" topBarId="toolbar">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="a0192a" />
							<odin:gridDataCol name="xsd"  isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0000" width="120" header="人员主键" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="a0101" width="60" header="姓名" align="center" />
							<odin:gridColumn dataIndex="a0192a" width="300" header="工作单位及职务" align="center" />
							<odin:gridColumn dataIndex="xsd" width="60" header="相似度" align="center" />
							<odin:gridColumn dataIndex="" width="70" header="操作" align="center" isLast="true" renderer="caozuo"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</div>
			</td>
		</tr>
	</table>
	<odin:hidden property="a0215aWord"/>
	<odin:hidden property="a0814Word"/>
	<odin:hidden property="a1701Word"/>
</div>
<%
	String a1521=request.getParameter("a1521");
	String a1517=request.getParameter("a1517");
	String id=request.getParameter("value");
%>
<odin:hidden property="a0101" title="姓名" />
<odin:hidden property="a0192" title="工作单位及职务" />
<odin:hidden property="a1521" title="考核年度"  value='<%=a1521%>'/>
<odin:hidden property="a1517" title="考核等次"  value='<%=a1517%>'/>
<odin:hidden property="rmbs" title="已经打开的任免表页面的人员id"/>
<script >
$(document).ready(function(){
	var name=getQueryString('name');
	var a0192=getQueryString('a0192');
	document.getElementById('a0101').value=name;
	document.getElementById('a0192').value=a0192;
});
//获取url参数
function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
	return unescape(r[2]);
	}
	return null;
}



//打开任免表
function openZHGBRMB(a0000){
	 var othis = $(this);
     active.setTop(a0000);
 }


function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function caozuo(param,obj,record){
	var a0000=record.data.a0000;//人员主键
	var a0101=document.getElementById('a0101').value//姓名
	var a1521=document.getElementById('a1521').value;//年份
	var a1517=document.getElementById('a1517').value;//考核等次
	return "<a href=\"javascript:marryInfo('"+a0000+"','"+<%=a1517%>+"','"+<%=a1521%>+"','"+a0101+"')\">匹配</a>"; 
	
}
function marryInfo(a0000,a1517,a1521,a0101){
	var temp='<%=id%>'+'#@#'+a0000+'#@#'+a1517+'#@#'+a1521+'#@#'+a0101;
	radow.doEvent('marryInfo',temp);
}
//关闭$.opinWin打开的窗口，用Dialog打开的窗口
function collapseGroupWin(){
	parent.odin.ext.getCmp('marryInfo').close();
}
function realParentDoEvent(event){
	realParent.radow.doEvent(event);
}
function onpenRmb(a0000){
	var context = '<%=request.getContextPath()%>';
	window.open(context+'/rmb/ZHGBrmb.jsp?a0000='+a0000, '_blank', 'height='+(screen.height-150)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	
}
</script>

