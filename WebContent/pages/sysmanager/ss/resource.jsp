<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%
String count = GlobalNames.sysConfig.get("YYHEADCOUNT");
%>

<script type="text/javascript">
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
            el : 'tree-div',//目标div容器
            autoScroll : true,
            rootVisible: false,
            split:false,
            width: 224,
            minSize: 224,
            maxSize: 224,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.resource&eventNames=orgTreeJsonData'
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : '业务菜单树',
            draggable : false,
            id : 'null',//默认的node值：?node=-100
            href:"javascript:void(0)"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();

});
function doSave(btn){
	var msg = "";
	if(radow.$("parentid").value == "" && (radow.$("parentid_combo").value == "" || radow.$("parentid_combo").value == "请您选择...") && radow.$("functionid").value == ""){
		msg = "<font color=red>您即将创建一个顶级菜单节点，</font>";
	}
	odin.confirm(msg+"您确定要执行"+btn.getText()+"操作吗？",function(rtn){
		if(rtn=='ok'){
			radow.doEvent("saveMenu");
		}
	});
}
function createRootNode(){
	radow.$("parentid").value = "";
	radow.$("parentid_combo").value = "";
	radow.$("functionid").value = "";
	radow.doEvent("btnNew.onclick");
}
var count = 24;
function showYyHeadDiv(){
	var html = "";
	for(var i=0;i<count;i++){
		var iid = i+1;
		html += "<lable><img src='images/func/"+iid+".png' id='"+iid+"' onclick='checkHead(this);' style='cursor:hand'/></lable>";
	}
	document.getElementById("showYyHeadDiv").innerHTML=html;
	document.getElementById('showYyHeadDiv').style.display="block";
	//$('#showYyHeadDiv').html(html);
	//获取选择框的位置
	//var p = $("#yyhead");
	//var yyhead = p.offset();
	//$('#showYyHeadDiv').css({top:yyhead.top+15,left:yyhead.left}).fadeIn('slow');
}


//点击图标后 赋值给选择框
function checkHead(is){
	var id = is.id;
	var funcpic = id;
	document.getElementById("funcpic").value=funcpic;
	document.getElementById('showYyHeadDiv').style.display="none";
}
</script>



<div id="addResourceContent">
<table height="480">
	<tr>
		<td valign="top">
		<table height="100%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		
		<td valign="top">
			<table width="550px">
				<tr>
					<odin:select property="parentid" isPageSelect="true" label="父菜单" pageSize="20" tpl="keyvalue" minChars="2"  canOutSelectList="true" codeType="FUNCTIONID"></odin:select>
					<odin:textEdit property="functionid" label="菜单ID" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="functioncode" label="菜单编号" required="true" maxlength="9" title="前几位必须与父菜单的菜单编号一致（除弹出页面等子功能，设置为“00000000”）"></odin:textEdit>
					<odin:numberEdit property="orderno" label="菜单序号" required="true" maxlength="4"></odin:numberEdit>
				</tr>
				<tr>
					<odin:textEdit property="title" label="菜单名称" required="true"></odin:textEdit>
					<odin:select property="type" label="菜单类型" codeType="CDTYPE" tpl="keyvalue" value="1" required="true"></odin:select>
				</tr>
				<tr>
					<odin:textEdit property="description" label="功能描述" required="true" colspan="4" size="81"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="location" label="菜单URL" size="81" colspan="4"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="ywlx" label="业务类型" required="true" maxlength="6" title="如为查询等无业务类型的功能，请设置为“000000”"></odin:textEdit>
					<odin:textEdit property="bdyy" label="变动原因" required="true" maxlength="8" title="如没有或有多个变动原因，请设置为“00000000”"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="zyxx" label="摘要信息" size="81" colspan="4" title="不需要保存的功能，请输入为“{}”，否则必须填写！"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="param1" label="自定义模块参数1"></odin:textEdit>
					<odin:textEdit property="param2" label="自定义模块参数2"></odin:textEdit>
				</tr>
				<tr>
					<td>
						<div style="display: none;z-index: 3;position: absolute; background: #F8F8FF;width: 320px;height: 250px;overflow: auto;"  id="showYyHeadDiv"></div>
					</td>
				</tr>
				<tr>
					<odin:textEdit property="param3" label="自定义模块参数3"></odin:textEdit>
					<odin:textEdit property="param4" label="自定义模块参数4"></odin:textEdit>
				</tr>
				<tr>					
					<odin:textEdit property="proc" maxlength="4" label="处理编号" value="P0" required="true"></odin:textEdit>					
					<odin:select property="auflag" label="自动审核" required="true" tpl="keyvalue" codeType="CDAUFLAG" value="1"></odin:select>				
				</tr>				
				<tr>
					<odin:select property="opctrl" label="操作期限制" tpl="keyvalue" required="true"></odin:select>
					<odin:textEdit property="owner" label="开发人员"></odin:textEdit>
				</tr>
				<tr>
					<odin:select property="draftflag" label="草稿箱修改" tpl="keyvalue" required="true" value="1"></odin:select>
					<odin:select property="sysid" label="所属系统" tpl="keyvalue" required="true" ></odin:select>
				</tr>								
				<tr>
					<odin:select property="cprate" label="控制等级" codeType="cprate" tpl="keyvalue"></odin:select>
					<odin:select property="active" label="状态" codeType="USEFUL" value="1" required="true" tpl="keyvalue"></odin:select>
					
				</tr>
				<tr>
					<odin:select property="isproxy" label="是否使用代理" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
					<odin:select property="appid" label="产品应用" codeType="PROXY" tpl="keyvalue"></odin:select>
				</tr>
				<tr>
					<odin:select property="rpflag" label="报表参数" codeType="RPTYPE" value="1" tpl="keyvalue"></odin:select>
					<odin:select property="publicflag" label="是否公共函数" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
				</tr>
				<tr>
					<odin:select property="log" label="是否记录日志" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
					<odin:select property="rbflag" label="操作日志回退标志" codeType="YESNO" value="1" tpl="keyvalue"></odin:select>
					
				</tr>
				<tr>
					<odin:select property="reauflag" label="是否稽核" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>					
					<odin:textEdit property="funcpic" label="系统图标" onclick="showYyHeadDiv();" readonly="true"></odin:textEdit>							
					
				</tr>		
		
			</table>
			
		</td>
		
	</tr>
</table>

</div>

<odin:hidden property="optype" value="1"/>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>资源管理</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="新建根节点" icon="images/i_2.gif" handler="createRootNode"
		cls="x-btn-text-icon" />
	<odin:buttonForToolBar id="btnNew" text="新建" icon="images/i_2.gif"
		cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnDelete" text="删除"
		icon="images/qinkong.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSave" isLast="true" text="保存" handler="doSave"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent"  property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


