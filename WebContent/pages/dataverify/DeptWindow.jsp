<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<style>
.picOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}
.picInnerOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}
.picGroupOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png) !important;
}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
          el : 'tree-div',//目标div容器
          split:false,
          width: 200,
          minSize: 164,
          maxSize: 164,
          rootVisible: true,//是否显示最上级节点
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData'
          })
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();
}); 

</script>

<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
%>

<div id="groupTreeContent" style="height: 100%">
<table width="100%">
	<tr>
		<td width="100%">
		<table width="100%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow:auto; height: 460px; width: 250px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		<td>
			<table>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="optionGroup"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>选择</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="确定" id="submitvalue" isLast="true" />
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>



