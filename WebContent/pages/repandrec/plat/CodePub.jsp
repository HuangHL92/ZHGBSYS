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
	<odin:buttonForToolBar text="�·�" id="reppackagebtn"/>
	<odin:buttonForToolBar  text="�ر�"  id="reset" isLast="true"/>
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
		<table >
			<tr>
				<td align="center" colspan="6">
					<table >
						<tr>
						<odin:textIconEdit property="searchDeptBtn" required="true" size="90" colspan="4" label="ѡ�����"/>
						</tr>
						<tr>
						<odin:textIconEdit property="searchModelBtn" required="true" size="90" colspan="4" label="ѡ��ģ��"/>
						</tr>
						<tr>
						<odin:textEdit property="linkpsn" size="38" label="��ϵ��"/>
						<odin:textEdit property="linktel" size="38" label="��ϵ�绰"/>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="6">
					<table >
					
						<tr align="right">
							<odin:textarea property="remark" colspan="255" rows="4" label="�� ע"/>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="6">
				</td>
			</tr>
		</table>
		</div>
		
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="����"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350" maximizable="false" title="����">
</odin:window>
<script>
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
          el : 'tree-div',//Ŀ��div����
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: true,//�Ƿ���ʾ���ϼ��ڵ�
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
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
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