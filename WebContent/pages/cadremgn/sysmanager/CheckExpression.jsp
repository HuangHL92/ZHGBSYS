<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/comOpenWinInit2.jsp"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
.div-inline {
	float: left;
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
	text-align: center; /* 文字等内容居中 */
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
        height:340,
        width:160,
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
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="vru003" text="表达式校验" handler="verifiClick" tooltip="校验合格后，请保存"
		icon="images/forum.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSaveContinue" text="保存" handler="keepClick"  tooltip="点击后，该校验规则将为您启用"
		icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnReload" text="删除" handler="deletcClick"
		icon="images/icon/click.gif" cls="x-btn-text-icon" />
	<odin:buttonForToolBar text="上一个" id="upRow" handler="upperClick" tooltip="将为您切换至非默认检验规则复制而来的上一条规则"
		icon="images/icon/arrowup.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="下一个" id="downRow" handler="downClick" tooltip="将为您切换至非默认检验规则复制而来的下一条规则"
		icon="images/icon/arrowdown.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="关闭" tooltip="请您保存校验规则，否在无法启用"
		handler="btnClose" icon="images/back.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<div id="panel_content">
	<odin:hidden property="vsc001" />
	<!-- verify_scheme 校验方案主键 ,校验方案编码识别一组校验；（verify_scheme与verify_rule一对多）-->
</div>
<odin:panel contentEl="panel_content" property="mypanel"
	topBarId="btnToolBar"></odin:panel>
<!-- 校验规则信息 -->
<odin:hidden property="vru001" />
<!-- 主校验信息集 -->
<odin:hidden property="vru004" />
<!-- 主校验信息项 -->
<odin:hidden property="vru005" />
<!-- 拼接完成的SQL中的主表 -->
<odin:hidden property="vru006" />
<!--字段类型-->
<odin:hidden property="vru007" value="0" />
<!--有效标记默认：0；非空；     0-无效(未启用)，1-有效（已启用）-->
<odin:hidden property="vru008" />
<!--完整SQL-->
<odin:hidden property="vru009" />
<!--备用字段-->

<!-- 部分SQL -->
<odin:hidden property="vru010" />
<!-- 校验语句列表编码(校验语句编码+列表顺序码)；主键 -->
<odin:hidden property="vsl005" />
<!--备用字段" -->

<!-- 当前验证信息是否通过 -->
<odin:hidden property="Flag" />

<table style="width: 100%">
	<tr>
		<odin:textarea property="vru000" label="校验表达式" value="" colspan="4" rows="7" maxlength="200"/>
		<!-- 
			<td>
				<odin:button property="vru003" text="表达式校验" handler="verifiClick"/>
				<div style="height: 20"></div>
				<odin:button property="hintcode" text="代码提示" handler="codeClick"/>
			</td>
		 -->
	</tr>
	<tr>
		<odin:textEdit property="vru002" label="校核提示信息" colspan="4" maxlength="150" size="150" />
	</tr>
	<tr>
		<!--<odin:select property="vru005" label="校核类型" colspan="2" maxlength="22" onchange="selectOnChange" size="22" />-->
		<odin:textEdit property="vsc002" label="校核类型" colspan="2" maxlength="22" size="22" readonly="true"/>
		<!--校验项名称默认：(表名)_(字段名)（校验类型）；可修改-->
		<odin:dateEdit property="A1KC192" label="生成时间"  mask="yyyy-mm-dd"/>
	</tr>
	<%-- 	<tr>
		<td colspan="2" style="padding-left:130px"><odin:button property="vru009" text="校核类型..." /></td>
		<td colspan="2"><odin:checkbox property="vru010" label="逻辑校核中启用该校核表达式" /></td>
	</tr>--%>
</table>


<odin:groupBox title="数据库字段">
	<table>
		<tr>
			<td>
				<div class="div-inline" style="margin-left: 10;">
		<table style="border: solid 0px !important;">
			<tr colspan="4">
				<td colspan="1" style="width: 200;"><odin:editgrid
						property="tableListGrid" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="table_code" />
							<odin:gridDataCol name="table_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="表数" width="150"
								dataIndex="table_code" edited="false" editor="text" align="left" 
								hidden="true"/>
							<odin:gridEditColumn header="表名" width="50"
								dataIndex="table_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 200"><odin:editgrid
						property="codeListGrid" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_type" />
							<odin:gridDataCol name="col_code" />
							<odin:gridDataCol name="col_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="字段属性" width="150" hidden="true"
								dataIndex="code_type" edited="false" editor="text" align="left" />
							<odin:gridEditColumn header="字段数" width="50" dataIndex="col_code"
								edited="false" editor="text" align="left"  hidden="true"/>
							<odin:gridEditColumn header="字段名" width="150"
								dataIndex="col_name" edited="false" editor="text" align="left" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid></td>                 
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 160"><odin:editgrid
						property="personListGrid3123123"  width="160" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="运算符" width="0"
								dataIndex="code_value" edited="false" editor="text" align="left"
								hidden="true" />
							<odin:gridEditColumn header="运算符名称" width="150"
								dataIndex="code_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			<tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 200"><odin:editgrid
						property="personListGrid121" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="符号" width="0"
								dataIndex="code_value" edited="false" editor="text" align="left"
								hidden="true" />
							<odin:gridEditColumn header="校核函数列表" width="150"
								dataIndex="code_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div >
	<div class="div-inline" style="width: 160px;height: 350px;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 160px; margin-top:55px; height: 340px;">
				    <odin:groupBox title="选择代码" >
						<div id="tree-div" style="width: 160px;height: 363px;"></div>
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
</odin:groupBox>

<!-- 当前插入内容 -->
<odin:hidden property="nowString" />
<!-- 当前选中的信息集 -->
<odin:hidden property="table_code" />
<!-- 当前选中的信息项 类型:A01.A0000 --> 
<odin:hidden property="col_code" />
<!-- 当前选中的信息项的类型 --> 
<odin:hidden property="codetype" />
<!-- 以存储的字段名:A01.A0000 --> 
<odin:hidden property="vru011" />
<!-- 保存点击标志 --> 
<odin:hidden property="keepCheckBZ" />

<script>

var vsc001="";
function verifiClick(){
	if(document.getElementById("A1KC192").value==""){
		alert("请先确认生成时间！");
		return;
	}
	document.getElementById("keepCheckBZ").value="0";
	radow.doEvent("verifiClick");
}
function keepClick(){//保存操作
	if(document.getElementById("Flag").value==""||document.getElementById("Flag").value=="no"){
		alert("请先确认校验表达式合格！");
		return;
	}
	document.getElementById("keepCheckBZ").value="1";
	radow.doEvent("keepCheck"); 
}
function deletcClick(){//删除操作 -- 存在主键则删除对应规则  -- 不存在主键则刷新页面
	radow.doEvent("deletcClick"); 
}

function upperClick(){//上一个
	radow.doEvent("rownumChange","1"); 
}
function downClick(){//下一个
	radow.doEvent("rownumChange","0"); 
}
function codeClick(){
	$h.openPageModeWin('CodeClickWin','pages.cadremgn.sysmanager.CodeClickWindow','选择提示代码',270,410,'','<%=request.getContextPath() %>');
}
function selectOnChange(){//修改校验方案主键  -- 选择校验类型
	vsc001=document.getElementById("vsc001").value=document.getElementById("vru005").value;
	radow.doEvent("selectOnChange",vsc001); 
}
function btnClose() {//关闭页面
	window.close();
	forverification();
}

function forverification(){
	var pWindow=window.dialogArguments['window'];
	pWindow.Ext.getCmp('VerifyRuleGrid').store.reload();
}
<!-- 存储类型[A01.A0000,A01.A0101] --> 
function rowDbClick1(){
	var getPosi = " "+document.getElementById('nowString').value+" ";
	var textarea = document.getElementById('vru000');
	var vru000 = document.getElementById('vru000').value;
	var userSelection = getCaret(textarea);
	//alert(userSelection);
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