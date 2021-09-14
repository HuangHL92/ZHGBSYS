<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="确定" id="btnAdd"/>
	<odin:buttonForToolBar  text="关闭"  id="closeBtn" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<odin:hidden property="searchDeptid"/>
		<div>
	<odin:editgrid property="Grid" title="" autoFill="true" bbarId="pageToolBar" pageSize="10" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
			<odin:gridDataCol name="checked"/>
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="hostname" />
		<odin:gridDataCol name="username" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn  width="20" editor="checkbox" header="" dataIndex="checked" edited="true"/>
		<odin:gridColumn dataIndex="id" width="10" header="FTP内部id" hidden="true" align="center" />
		<odin:gridColumn dataIndex="name" width="200" header="FTP名称"  align="center" />
		<odin:gridColumn dataIndex="hostname" width="200" header="FTP主机地址" align="center" />
		<odin:gridColumn dataIndex="username" width="200" isLast="true" header="FTP用户名" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
		</div>
		
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350" maximizable="false" title="窗口">
</odin:window>
<script>
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
          el : 'tree-div',//目标div容器
          split:false,
          width: 164,
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
function reloadTree(){
	setTimeout(xx,1000);
	///window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}
function cc()
{
	w.close();
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab1'){
		odin.ext.getCmp('tabimp').view.refresh(1);
	}
	if(item.getId()=='tab2'){
		odin.ext.getCmp('tabimp').view.refresh(2);
	}
}
</script>