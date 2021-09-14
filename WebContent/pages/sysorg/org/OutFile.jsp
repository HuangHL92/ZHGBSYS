<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<script type="text/javascript">
Ext.onReady(function() {
	
	  var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {
  	  id : 'group',
        el : 'tree-div',//目标div容器
        split:false,
        width: 164,
        minSize: 164,  
        maxSize: 164,
        height:250,
        rootVisible: false,//是否显示最上级节点
        autoScroll : true,
        animate : true,
        border:true,
        enableDD : false,
        containerScroll : true,
        loader : new Tree.TreeLoader( {
              dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTreeOut'
        })
    });
    var root = new Tree.AsyncTreeNode( {
          text :  document.getElementById('ereaname').value,
          iconCls : document.getElementById('picType').value,
          draggable : false,
          id : document.getElementById('ereaid').value,//默认的node值：?node=-100
          href:"javascript:radow.doEvent('querybyid','-100.')"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand();
    
    var man1 = document.getElementById('manager').value;
    var Tree1 = Ext.tree;
    var tree1 = new Tree.TreePanel( {
  	  id : 'groupright',
        el : 'tree-div1',//目标div容器
        split:false,
        width: 164,
        minSize: 164,
        maxSize: 164,
        height:250,
        rootVisible: false,
        autoScroll : true,
        animate : true,
        border:true,
        enableDD : false,
        containerScroll : true,
        loader : new Tree.TreeLoader( {
               dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.Zjzzy&eventNames=orgTreeJsonDataright'
        })
    });
    var root1 = new Tree.AsyncTreeNode( {
          text :  document.getElementById('ereaname').value,
          iconCls : document.getElementById('picType').value,
          draggable : false,
          id : document.getElementById('ereaid').value,//默认的node值：?node=-100
          href:"javascript:radow.doEvent('querybyidright','"+document.getElementById('ereaid').value+"')"
    });
    tree1.setRootNode(root1);
    tree1.render();
    root1.expand();
    
    var side  = document.getElementById("tree-div");
    var side  = document.getElementById("tree-div1");
    var side_resize=function()  
    {  
            height = document.body.clientHeight;  
            document.getElementById("tree-div").style.height=height*0.8+"px";  
            document.getElementById("tree-div1").style.height=height*0.8+"px";
            width =  document.body.clientWidth;              
            document.getElementById("tree-div").style.width=width*0.35+"px";
            document.getElementById("tree-div1").style.width=width*0.35+"px";
            document.getElementById("turnOutId").style.width=width*0.35+"px";
            document.getElementById("changeIntoId").style.width=width*0.35+"px";
            Ext.getCmp("group").setWidth(width*0.35+"px");
            Ext.getCmp("groupright").setWidth(width*0.35+"px");
//            document.getElementById('main').style.marginLeft = "2px";  
       	  document.getElementById("main").style.width=width+"px";  
    }  
    side_resize();  
    window.onresize=side_resize; 
}); 
function reloadTree() {
    var tree = Ext.getCmp("groupright");
    tree.root.reload();
    tree.collapseAll();
    var tree1 = Ext.getCmp("group");
    tree1.root.reload();
    tree1.collapseAll();
}

</script>
<%
	String picType = (String) (new SysOrgPageModel().areaInfo
			.get("picType"));
	String ereaname = (String) (new SysOrgPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new SysOrgPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new SysOrgPageModel().areaInfo
			.get("manager"));
%>
<div align="center">
	<table>
		<tr align="left">
			<odin:textEdit property="turnOut" label="当前机构" width="100" disabled="true" />
			<td>&nbsp;</td>
			<odin:textEdit property="changeInto" label="恢复到" width="100" disabled="true" />
		</tr>
		<tr>
			<td colspan="2"><div id="tree-div"></div></td>
			<td>
				<img src="<%=request.getContextPath()%>/pages/sysorg/org/images/arrow-right.png">
			</td>
			<td colspan="2"><div id="tree-div1"></div></td>
		</tr>
		<tr align="center">
			<td colspan="2"><odin:button  text="机构删除" property="Btndelete"  > </odin:button></td>
			<td>&nbsp;</td>
			<td colspan="2"><odin:button text="机构恢复" property="transferSysOrgBtn" > </odin:button></td>
		</tr>
	</table>
</div>
<odin:hidden property="turnOutId"/>
<odin:hidden property="changeIntoId"/>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>"/>
<odin:hidden property="checkedgroupid1" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>"/>


