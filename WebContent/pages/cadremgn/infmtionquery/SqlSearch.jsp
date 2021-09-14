<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.infmtionquery.SqlSearchPageModel"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<%@ include file="/comOpenWinInit2.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<style>
.div-inline {
	float: left;
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
	text-align: center; /* 文字等内容居中 */
}
table{
 border:0;
 cellpadding:0;
 cellspacing:0;
 margin:0;
}
iframe{
 border:0;
 cellpadding:0;
 cellspacing:0;
 margin:0;
 height:100%;
 width:100%;
}
#tab__tab1{
	margin-left:390;
}
</style>
<script>
//初始化树
Ext.onReady(function() {
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {//定义一棵树
  	    id:'group',
        el : 'tree-div',//目标div容器
        split:false,
        height:265,
        width:150,
        minSize: 164,
        maxSize: 164,
        rootVisible: true,//是否显示最上级节点，默认为true
        autoScroll : true,//超过范围自动出现滚动条
        animate : true,//展开和收缩时的动画效果
        border : false,
        enableDD : false,////不仅可以拖动,还可以通过Drag改变节点的层次结构(drap和drop)
        containerScroll : true,//是否将树形面板注册到滚动管理器ScrollManager中
        loader : new Tree.TreeLoader( {
      	     dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.CheckExpression&eventNames=orgTreeJsonData&codetype='
        }),
        listeners: { 
      	   click: function(node){
      		   var codevalue=node.attributes.id;
      		   radow.doEvent("clickCodeValue",codevalue);
      	   },
           afterrender: function(node) {        
               tree.expandAll();//展开树     
           }        
         } 
    });
	     var root = new Tree.AsyncTreeNode( { //创建AsyncTreeNode 
	          text : document.getElementById('ereaname').value,
	          draggable : false,//拖动
	          id : document.getElementById('nodeid').value //默认的node值：?node=-100
	          //href:"javascript:radow.doEvent('clickCodeValue','"+document.getElementById('codevalue').value+"')"
	     });
    tree.setRootNode(root);//创建根节点 
    tree.render();
    
}); 

<%
	String ereaname = "";
	String nodeid = "-1";
%>
//单击获取codetype值
function orgTreeJsonData(){
	var tree = Ext.getCmp("group");
    var url='radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.CheckExpression&eventNames=orgTreeJsonData&codetype=';
    tree.loader.dataUrl=url+document.getElementById('codetype').value;
    var selections = odin.ext.getCmp("codeListGrid").getSelectionModel().getSelections();
	var col_name = selections[0].data.col_name;
	var colname = col_name.substring(col_name.indexOf(".")+1,col_name.length);
    tree.root.setText(colname);
    tree.root.reload();
}


</script>
<div id="panel_content">
</div >
<odin:tab id="tab">
	<odin:tabModel >
		<odin:tabItem title="修改" id="tab1"></odin:tabItem>
		<odin:tabItem title="设置修改条件" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1"  >
<table width="100%" height="100%">
<tr><td>
<!-- 点击当前Grid行号 -->
<odin:hidden property="updateFlag" value=""/>
	<odin:groupBox  title="查询语句">
		<table>
			<tr>
				<odin:textarea property="vru000" cols="150" rows="8" />
			</tr>
			<tr >
			<td align="right" style="padding-right:20px" >
				<%-- <odin:button  text="执行"  property="btn1" handler="queryResult" /> --%>
				<input id="btn1" class="yellowbutton" type="button" value="执&nbsp;&nbsp;行" onclick="queryResult()"/>
			</td>
		</tr>
		</table>
	</odin:groupBox>
</td>
</tr>
<tr><td>
	<!--<odin:groupBox title="错误信息提示" >
		<table>
			<tr>
				<odin:textarea property="vru005" cols="120" disabled="true" rows="3" />
			</tr>
		</table>
	</odin:groupBox>-->
	<!-- <table>
		<tr >
			<td style="padding-left:550px" >
				<odin:button  text="执行"  property="btn1" handler="queryResult" />
			</td>
			<td style="padding-left:20px" >
				<odin:button  text="导出" property="btn2"  handler="expExcelFromGrid" />
			</td>
		</tr>
	</table> -->
</td>
</tr>
<tr>
<td>
<odin:groupBox title="数据库字段">
	<table>
		<tr>
			<td>
				<div class="div-inline" style="margin-left: 10;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 170;"><odin:editgrid
						property="tableListGrid" width="100" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="table_code" />
							<odin:gridDataCol name="table_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="表数" width="0" dataIndex="table_code" edited="false" editor="text" align="left" hidden="true"/>
							<odin:gridEditColumn header="表名" width="123" dataIndex="table_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 170">
					<odin:editgrid property="codeListGrid" width="170" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_type" />
							<odin:gridDataCol name="col_code" />
							<odin:gridDataCol name="col_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="字段属性" width="0" hidden="true" dataIndex="code_type" edited="false" editor="text" align="left" />
							<odin:gridEditColumn header="字段数" width="0" dataIndex="col_code" edited="false" editor="text" align="left"  hidden="true"/>
							<odin:gridEditColumn header="字段名" width="123" dataIndex="col_name" edited="false" editor="text" align="left" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 140">
						<odin:editgrid property="personListGrid3123123"  width="140" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="运算符" width="0" dataIndex="code_value" edited="false" editor="text" align="left" hidden="true" />
							<odin:gridEditColumn header="运算符名称" width="95" dataIndex="code_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			<tr>
		</table>
	</div>
	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 140">
					<odin:editgrid property="personListGrid121" width="140" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="符号" width="0" dataIndex="code_value" edited="false" editor="text" align="left" hidden="true" />
							<odin:gridEditColumn header="校核函数列表" width="95" dataIndex="code_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
	</div >
	
	<div class="div-inline" style="width: 130px;height: 20px;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 140px; margin-top:55px; height: 20px;">
				    <odin:groupBox title="选择代码" >
						<div id="tree-div" style="width: 130px;height: 20px;"></div>
					</odin:groupBox>
				</td>
			</tr>
			<tr>
				<odin:hidden property="codevalue"/> 
			    <odin:hidden property="ereaname" value="<%=ereaname%>" />
				<odin:hidden property="nodeid" value="<%=nodeid%>" />
			</tr>
		</table>
	</div>
	</td>
	</tr>
	</table>
</odin:groupBox>
</td>

</tr>
</table>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
<table>
	<tr>
		<td width="860" height="440">
			<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.infmtionquery.ResultQuery"  id="resultUpdate" name="resultUpdate"></iframe>
		</td>
	</tr>
	<tr>
		<td height="120">
			<odin:groupBox title="错误信息提示" >
				<table>
					<tr>
						<odin:textarea property="vru005" cols="150" disabled="true" rows="5" />
					</tr>
				</table>
			</odin:groupBox>
		</td>
	</tr>
</table>
</odin:tabCont>
</odin:tab>
<!-- 当前插入内容 -->
<odin:hidden property="qvid" />
<!-- 当前插入内容 -->
<odin:hidden property="nowString" />
<!-- 当前选中的信息集 -->
<odin:hidden property="table_code" />
<!-- 当前选中的信息项 类型:A01.A0000 --> 
<odin:hidden property="col_code" />
<!-- 当前选中的信息项的类型 --> 
<odin:hidden property="codetype" />
<script>
function setValue(){
	var str=document.getElementById('updateFlag').value;
	/* var resultListGrid=window.frames["resultUpdate"].document.getElementById("resultListGrid");
	.getStore().removeAll(); */
	/* if(str=='yes'){
		 */
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.infmtionquery.ResultQuery" ;
	//alert(document.getElementById('vru000').value);
	//alert(url);
	document.getElementById('resultUpdate').src=url+"&vru000="+document.getElementById('qvid').value;
	odin.ext.getCmp('tab').activate('tab2');
	/* } */
}
function queryResult(){
	radow.doEvent('setBtnQuery');
}

function rowDbClick1(){
	var getPosi = " "+document.getElementById('nowString').value+" ";
	var textarea = document.getElementById('vru000');
	var vru000 = document.getElementById('vru000').value;
	var userSelection = getCaret(textarea);
	document.getElementById('vru000').value = vru000.substring(0,userSelection) + getPosi + vru000.substring(userSelection,vru000.length);
}
//实现文本指定位置拼接
function getCaret(el) { 
  if (el.selectionStart) { 
    return el.selectionStart; 
  } else if (document.selection) { 
    el.focus(); 
    var r = document.selection.createRange(); 
    if (r == null) { 
      return 0; 
    } 
    var re = el.createTextRange(), 
        rc = re.duplicate(); 
    re.moveToBookmark(r.getBookmark()); 
    rc.setEndPoint('EndToStart', re); 
    return rc.text.length; 
  }  
  return 0; 
}

</script>