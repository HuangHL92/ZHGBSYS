<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
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
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.business.entity.CodeValue"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="上报" id="reppackagebtn"
		icon="images/icon/exp.png" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="重置" icon="images/sx.gif" id="reset"
		isLast="true" />
</odin:toolBar>
<odin:panel contentEl="panel_content" property="mypanel"
	topBarId="btnToolBar"></odin:panel>
<div id="panel_content"><odin:hidden property="ereaname"
	value="<%=ereaname%>" /> <odin:hidden property="ereaid"
	value="<%=ereaid%>" /> <odin:hidden property="manager"
	value="<%=manager%>" /> <odin:hidden property="picType"
	value="<%=picType%>" /> <odin:hidden property="ftpid" /> <odin:hidden
	property="downfile" />

<div>
<table width="550px" height="100%">

	<tr>
		<td width="100%">
		<table width="100%">
			<tr>
				<odin:textIconEdit property="searchDeptBtn" required="true" readonly="true"
					width="438" colspan="4" label="上报机构" />
			</tr>
			<tr>
				<odin:textEdit property="linkpsn" required="true" colspan="2"
					label="联系人" />
				<odin:textEdit property="linktel" required="true" colspan="2"
					label="联系电话" />
			</tr>
			<tr>
				<odin:textarea property="remark" colspan="4" label="备 注" />
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table>
			<tr>
				<td width="20"></td>
				<td><odin:checkbox property="gzlbry" onclick="ongzlb();"
					label="所有工作人员类别"></odin:checkbox></td>
				<td width="260"></td>
				<td><odin:checkbox property="gllbry" onclick="ongllb();"
					label="所有管理人员类别"></odin:checkbox></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<table width="100%">
			<tr>
				<td colspan="5" width="66%">
				<div id="gzlb" style="float: left;"><odin:groupBox
					title="工作人员类别">
					<table>
						<tr>
							<td valign="top">
							<%
								List<CodeValue> list = HBUtil.getHBSession().createQuery(
											" from CodeValue t where "
													+ " codeType='ZB125' order by codeValue")
											.list();
									if (list != null && list.size() > 0) {
										int gzs = list.size();
										int lefts = (gzs + 1) / 2;
										for (int i = 0; i < lefts; i++) {
											CodeValue code = list.get(i);
											int k = i + 1;
											String codename = code.getCodeName();
											String ddd = "gz_" + k;
							%> <odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
							<%
								}
							%>
							</td>
							<td valign="top">
							<%
								for (int j = lefts; j < gzs; j++) {
											CodeValue code = list.get(j);
											int k = j + 1;
											String codename = code.getCodeName();
											String ddd = "gz_" + k;
							%> <odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
							<%
								}
									}
							%>
							</td>
						</tr>
					</table>
				</odin:groupBox></div>
				</td>
				<td width="2%"></td>
				<td width="32%" valign="top">
				<div id="gllb" style="float: left;"><odin:groupBox
					title="管理人员类别">
					<%
						List<CodeValue> list2 = HBUtil.getHBSession().createQuery(
									" from CodeValue t where "
											+ " codeType='ZB130' order by codeValue")
									.list();
							if (list2 != null && list2.size() > 0) {
								int gzs = list2.size();
								for (int i = 0; i < gzs; i++) {
									CodeValue code = list2.get(i);
									int k = i + 1;
									String codename = code.getCodeName();
									String ddd = "gl_" + k;
					%>
					<odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
					<%
						}
							}
					%>
				</odin:groupBox></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<%--- 
			<tr>
				<td colspan="6">
				<table width="100%">
					<tr>
						<td colspan="4">
							<div id="gzlb" style="{float:left;}">
							<odin:groupBox title="导出所有工作类别人员">
								<table><tr><td valign="top">
								<odin:checkbox property="gz_1"  label="综合管理类管理员"></odin:checkbox>
								<odin:checkbox property="gz_2" label="专业技术类管理员"></odin:checkbox>
								<odin:checkbox property="gz_3" label="行政执法类公务员"></odin:checkbox>
								<odin:checkbox property="gz_4" label="参照管理的机关工作人员"></odin:checkbox>
								<odin:checkbox property="gz_5" label="参照管理的事业工作人员"></odin:checkbox>
								<odin:checkbox property="gz_6" label="事业单位管理人员"></odin:checkbox>
								<odin:checkbox property="gz_7" label="事业单位技术人员"></odin:checkbox>
								</td><td valign="top">
								<odin:checkbox property="gz_8" label="企业管理人员"></odin:checkbox>
								<odin:checkbox property="gz_9" label="企业技术人员"></odin:checkbox>
								<odin:checkbox property="gz_10" label="企业工勤人员"></odin:checkbox>
								<odin:checkbox property="gz_11" label="机关工勤人员"></odin:checkbox>
								<odin:checkbox property="gz_12" label="事业单位工勤人员"></odin:checkbox>
								<odin:checkbox property="gz_13" label="临时人员"></odin:checkbox>
								</td></tr></table>
							</odin:groupBox>
							</div>
						</td>
						<td width="20px"></td>
						<td colspan="2" valign="top">
							<div id="gllb" style="{float:left;}">
							<odin:groupBox title="导出所有管理类别人员">
								<odin:checkbox property="gl_1" label="中央管理干部"></odin:checkbox>
								<odin:checkbox property="gl_2" label="省级党委管理干部"></odin:checkbox>
								<odin:checkbox property="gl_3" label="市级党委管理干部"></odin:checkbox>
								<odin:checkbox property="gl_4" label="县级党委管理干部"></odin:checkbox>
								<odin:checkbox property="gl_5" label="其他"></odin:checkbox>
							</odin:groupBox>
							</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			--%>
</table>
</div>
<div>
<table>
	<tr>
		<td width="20px"></td>
		<td><odin:checkbox property="ltry" label="上报离退人员"></odin:checkbox>
		</td>
		<td><odin:checkbox property="lsry" label="上报历史人员"></odin:checkbox>
		</td>
		<td width="100px"></td>
		<%--
					<odin:select property="gjgs" label="导出格式" required="true" readonly="true" value="1" data="['1','hzb'],['2','zb3']"></odin:select>
					--%>
		<odin:hidden property="gjgs" value="1" />
	</tr>
</table>
</div>
</div>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350"
	maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="355" height="350"
	maximizable="false" title="窗口">
</odin:window>
<script>
/**function ongzlb(){
	var gzlb = odin.ext.getCmp('gzlbry').getValue();
	if(gzlb==1){
		odin.ext.getCmp('gz_1').setValue(1);	
		odin.ext.getCmp('gz_2').setValue(1);	
		odin.ext.getCmp('gz_3').setValue(1);	
		odin.ext.getCmp('gz_4').setValue(1);	
		odin.ext.getCmp('gz_5').setValue(1);	
		odin.ext.getCmp('gz_6').setValue(1);	
		odin.ext.getCmp('gz_7').setValue(1);	
		odin.ext.getCmp('gz_8').setValue(1);	
		odin.ext.getCmp('gz_9').setValue(1);	
		odin.ext.getCmp('gz_10').setValue(1);	
		odin.ext.getCmp('gz_11').setValue(1);	
		odin.ext.getCmp('gz_12').setValue(1);	
		odin.ext.getCmp('gz_13').setValue(1);	
		document.getElementById("gzlb").disabled=true;
	} else {
		odin.ext.getCmp('gz_1').setValue(0);	
		odin.ext.getCmp('gz_2').setValue(0);	
		odin.ext.getCmp('gz_3').setValue(0);	
		odin.ext.getCmp('gz_4').setValue(0);	
		odin.ext.getCmp('gz_5').setValue(0);	
		odin.ext.getCmp('gz_6').setValue(0);	
		odin.ext.getCmp('gz_7').setValue(0);	
		odin.ext.getCmp('gz_8').setValue(0);	
		odin.ext.getCmp('gz_9').setValue(0);	
		odin.ext.getCmp('gz_10').setValue(0);	
		odin.ext.getCmp('gz_11').setValue(0);	
		odin.ext.getCmp('gz_12').setValue(0);	
		odin.ext.getCmp('gz_13').setValue(0);
		document.getElementById("gzlb").disabled=false;	
	}
}
function ongllb(){
	var gllb = odin.ext.getCmp('gllbry').getValue();
	if(gllb==1){
		odin.ext.getCmp('gl_1').setValue(1);	
		odin.ext.getCmp('gl_2').setValue(1);	
		odin.ext.getCmp('gl_3').setValue(1);	
		odin.ext.getCmp('gl_4').setValue(1);	
		odin.ext.getCmp('gl_5').setValue(1);
		document.getElementById("gllb").disabled=true;	
		
	} else {
		odin.ext.getCmp('gl_1').setValue(0);	
		odin.ext.getCmp('gl_2').setValue(0);	
		odin.ext.getCmp('gl_3').setValue(0);	
		odin.ext.getCmp('gl_4').setValue(0);	
		odin.ext.getCmp('gl_5').setValue(0);
		document.getElementById("gllb").disabled=false;	
	}
	
}*/
function ongzlb(){
	var gzlb = odin.ext.getCmp('gzlbry').getValue();
	
	if(gzlb==1){
	<% 
	List<CodeValue> list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
		" codeType='ZB125' order by codeValue").list();
	if(list!=null && list.size()>0){
		int gzs = list.size();
		for(int i=0;i<gzs;i++){
			int k = i + 1;
			String ddd="gz_"+k;	
	%>
		odin.ext.getCmp('<%=ddd %>').setValue(1);	
	<% 
		}
	}
	%>	
		document.getElementById("gzlb").disabled=true;
	} else {
		<% 
	
	if(list!=null && list.size()>0){
		int gzs = list.size();
		for(int i=0;i<gzs;i++){
			int k = i + 1;
			String ddd="gz_"+k;	
	%>
		odin.ext.getCmp('<%=ddd %>').setValue(0);	
	<% 
		}
	}
	%>
		document.getElementById("gzlb").disabled=false;	
	}
}
function ongllb(){
	var gllb = odin.ext.getCmp('gllbry').getValue();
	if(gllb==1){
	<%
								List<CodeValue> list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
									" codeType='ZB130' order by codeValue").list();
								if(list2!=null && list2.size()>0){
									int gzs = list2.size();
									for(int i=0;i<gzs;i++){
										int k = i + 1;
										String ddd="gl_"+k;
								%>
		odin.ext.getCmp('<%=ddd %>').setValue(1);	
		
		<% 
		}
	}
	%>
		document.getElementById("gllb").disabled=true;	
		
	} else {
	<%
								if(list2!=null && list2.size()>0){
									int gzs = list2.size();
									for(int i=0;i<gzs;i++){
										int k = i + 1;
										String ddd="gl_"+k;
								%>
		odin.ext.getCmp('<%=ddd %>').setValue(0);	
		<% 
		}
	}
	%>
		document.getElementById("gllb").disabled=false;	
	}
	
}
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
function reloadTree(downf){
	alert(downf);
	setTimeout(xx(file),1000);
	///window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
}
function xx(file){
	var downfile = document.getElementById('downfile').value;
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(file)));
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