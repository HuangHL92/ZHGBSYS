<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script type="text/javascript">
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
            el : 'tree-div',//目标div容器
            autoScroll : true,
            rootVisible: false,
            split:false,
            id:"orgTree",
            width: 224,
            minSize: 224,
            maxSize: 224,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.resource.resource&eventNames=orgTreeJsonData'
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
  	//页面调整
  		document.getElementById("addResourceContent").style.width = document.body.clientWidth + "px";
  		//document.getElementById("addResourceContent").style.height = document.body.clientHeight*0.8 + "px";
  		document.getElementById("addResourcePanel").style.width = document.body.clientWidth-1 + "px";
}); 
</script>



<div id="addResourceContent">
<div id="addResourcePanel"></div>
<table height="100%">
	<tr>
		<td valign="top">
		<table height="100%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto;height: 600px; width: 250px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		<td valign="top">
			<table width="550px" style="height: 300px;">
				<tr>
					<odin:hidden property="parent"></odin:hidden>
					<odin:hidden property="functionid"></odin:hidden>
				</tr>
				<tr>
					<odin:textEdit property="parentname" label="父资源名称" disabled="true"></odin:textEdit>
					<odin:textEdit property="title" label="资源名称" required="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="orderno" label="同级菜单序号" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="location" label="资源URL" size="82"
						colspan="4"></odin:textEdit>
				</tr>
				<tr>
					<odin:select property="type" label="类型" codeType="NTYPE" value="1"></odin:select>
					<odin:textEdit property="description" label="资源描述"></odin:textEdit>
				</tr>
				<tr style="display: none;">
					<odin:select property="isproxy" label="是否使用代理" codeType="YESNO"
						value="0"></odin:select>
					<odin:select property="appid" label="产品应用" codeType="PROXY"
						></odin:select>
				</tr>
				<tr>
					<odin:textEdit property="owner" label="资源持有者"></odin:textEdit>
					<odin:select property="active" label="状态" codeType="USEFUL"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:select property="rpflag" label="报表参数" codeType="RPTYPE"
						value="1"></odin:select>
					<odin:select property="uptype" label="模块功能分类" codeType="UPTYPE"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="param1" label="自定义模块参数1"></odin:textEdit>
					<odin:textEdit property="param2" label="自定义模块参数2"></odin:textEdit>
				</tr>
				<tr>
					<odin:select2 property="param3" data="['011','非密'],['01','内部'],['0','秘密']" label="密级标识" />
					
				</tr>
				<tr style="display: none;">
					<odin:select property="log" label="是否记录日志" codeType="YESNO"
						value="0"></odin:select>
						<odin:select property="rbflag" label="是否允许回退" codeType="YESNO"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:select property="publicflag" label="是否公共函数" codeType="YESNO"
							value="0"></odin:select>
					<odin:textEdit property="ywlx" maxlength="6" label="业务类型"></odin:textEdit>		
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="cprate" maxlength="1" label="控制等级"></odin:textEdit>
					<odin:select property="opctrl" label="操作期限制" codeType="opctrl"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:numberEdit property="auflag" label="审核级别"></odin:numberEdit>
					<odin:select property="reauflag" label="是否稽核" codeType="YESNO"
						value="0"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="functioncode" maxlength="8" label="功能编号"></odin:textEdit>
					<odin:textEdit property="proc" maxlength="4" label="处理编号"></odin:textEdit>
				</tr>
				<tr style="display: none;">
					<odin:textarea property="prsource" label="日志信息主要字段" rows="4"
						colspan="4" ></odin:textarea>
					<!--<odin:textEdit property="prsource" label="日志信息主要字段" size="82"
						colspan="4" ></odin:textEdit>-->
				</tr>
				<tr style="display: none;">
					<odin:textarea property="zyxx" label="摘要信息" rows="4"  ></odin:textarea>
					<odin:textEdit property="param4" label="自定义模块参数4" ></odin:textEdit>
				</tr>	
				<tr>
					<td height="20"></td>
				</tr>					
			</table>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar" applyTo="addResourcePanel">
	<odin:textForToolBar text="<h3>资源管理</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnNew" text="新建" icon="images/i_2.gif"
		cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnDelete" text="删除"
		icon="images/qinkong.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSave" isLast="true" text="保存"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<!-- 
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>
 -->

