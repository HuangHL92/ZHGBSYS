<%@page import="com.insigma.siis.local.pagemodel.dataverify.SDataOrgImpPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
	String a00 = "['1','综合管理类管理员'],['2','专业技术类管理员']['3','行政管理类管理员']['4','事业单位管理人员']['5','事业单位管理人员5']";
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.business.entity.CodeValue"%>
<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>
<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/ux/css/Ext.ux.form.LovCombo.css"/>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
 #tableid1{position:relative;left:10px;top: 10px;} 
</style>


<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="导出" id="expbtn" icon="images/icon/exp.png" />
	
	<odin:separator></odin:separator>
	<odin:buttonForToolBar  text="重置"  id="reset" icon="images/sx.gif" isLast="true"/>
</odin:toolBar>
<div id="panel_content" align="center">
		<div id="toolDiv"></div>
		<odin:hidden property="ereaname" value="<%=ereaname%>" />
		<odin:hidden property="ereaid" value="<%=ereaid%>" />
		<odin:hidden property="manager" value="<%=manager%>" />
		<odin:hidden property="picType" value="<%=picType%>" />
		<odin:hidden property="tabimp"/>
		<odin:hidden property="searchDeptid"/>
		<table id="tableid1" style="width:600px;"  >
			
			<%-- <tr>
				<td colspan="6" width="100%" >
					<table width="100%"> --%>
						<tr>
							<%-- <odin:select2 property="inx_type" label="选择交换格式" data="<%=SDataOrgImpPageModel.getInxType() %>"></odin:select2>
							 --%>
							<odin:hidden property="inx_type" value="f2e5de60-4eae-4e7e-990e-e46aef4d6fce"/>
						</tr>
						<tr>
							<tags:PublicTextIconEdit4 onchange="setParentValue" colspan="4" width="478" property="searchDeptBtn" label="选择机构" readonly="true" codetype="orgTreeJsonData" />
						</tr>
						<tr >
							<odin:textEdit property="linkpsn" colspan="1" width="180" label="联系人"/>
							<odin:textEdit property="linktel" width="180" colspan="1" label="联系电话"/>
						</tr>
						<tr >
							<odin:textarea property="remark" colspan="4" cols="4" style="width:475px;" label="备 注"/>
						</tr>
					<%-- </table>
				</td>
			</tr> --%>
			
			<tr>
				<td colspan="4">
					<table >
						<tr>
							<td width="20"></td>
							<td><odin:checkbox property="zdcjg" onclick="onzdcjg();" label="只导出机构信息"></odin:checkbox></td>
							<td width="220"></td>
							<td></td>
						</tr>
						<tr id="allchoose">
							<td width="20"></td>
							<td><odin:checkbox property="gzlbry" onclick="ongzlb();" label="所有工作人员类别"></odin:checkbox></td>
							<td ></td>
							<td><odin:checkbox property="gllbry" onclick="ongllb();"  label="所有管理人员类别"></odin:checkbox></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<table style="width:550px;">
					<tr>
						<td width="340px" >
							<div id="gzlb" style="{float:left;}">
							<odin:groupBox title="工作人员类别">
								<table><tr><td valign="top">
								<%
								List<CodeValue> list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
									" codeType='ZB125' order by codeValue").list();
								if(list!=null && list.size()>0){
									int gzs = list.size();
									int lefts = (gzs+1)/2;
									for(int i=0;i<lefts;i++){
										CodeValue code = list.get(i);
										int k = i + 1;
										String codename= code.getCodeName();
										String ddd="gz_"+k;
								%>
								<odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
								<%
									}
								%>
								</td><td valign="top"> 
								<%
									for(int j=lefts;j<gzs;j++){
										CodeValue code = list.get(j);
										int k = j + 1;
										String codename= code.getCodeName();
										String ddd="gz_"+k;
								%>
								<odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
								<%
									}
								}
								%>
								</td></tr>
								</table>
							</odin:groupBox>
							</div>
						</td>
						<td width="10px"></td>
						<td width="190px" valign="top">
							<div id="gllb" style="{float:left;}">
							<odin:groupBox title="管理人员类别">
							<%
								List<CodeValue> list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
									" codeType='ZB130' order by codeValue").list();
								if(list2!=null && list2.size()>0){
									int gzs = list2.size();
									for(int i=0;i<gzs;i++){
										CodeValue code = list2.get(i);
										int k = i + 1;
										String codename= code.getCodeName();
										String ddd="gl_"+k;
								%>
								<odin:checkbox property="<%=ddd %>" label="<%=codename %>"></odin:checkbox>
								<%
									}
								}
								%>
							</odin:groupBox>
							</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table id="lslt">
						<tr>
							<td width="20px"></td>
							<td>
								<odin:checkbox property="fxzry" label="非现职人员"></odin:checkbox>
								<%-- <odin:checkbox property="ltry" label="导出离退人员"></odin:checkbox>
							</td>
							<td>
								<odin:checkbox property="lsry" label="导出历史人员"></odin:checkbox> --%>
							</td>
							<td width="170px"></td>
							<odin:hidden property="gjgs"  value="1" />
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350" maximizable="false" title="窗口"> </odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500" title="信息校验" modal="true" />
<odin:window src="/blank.htm" id="refreshWin" width="550" height="400" maximizable="false" title="窗口" />
<script>
function onzdcjg(){
	var gzlb = odin.ext.getCmp('zdcjg').getValue();
	
	if(gzlb==1){
		document.getElementById("gzlb").disabled=true;
		document.getElementById("gllb").disabled=true;
		document.getElementById("lslt").disabled=true;
		document.getElementById("allchoose").disabled=true;
	} else {
		document.getElementById("lslt").disabled=false;
		document.getElementById("allchoose").disabled=false;
		var gzlb = odin.ext.getCmp('gzlbry').getValue();
		var gllb = odin.ext.getCmp('gllbry').getValue();
		if(gzlb==0){
			document.getElementById("gzlb").disabled=false;
		}
		if(gllb==0){
			document.getElementById("gllb").disabled=false;
		}
	}
}

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
//a01统计关系所在单位
function setParentValue(record,index){
	document.getElementById('searchDeptid').value=record.data.key;
}

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
Ext.onReady(function() {
	//页面调整
	document.getElementById("panel_content").style.width = document.body.clientWidth + "px";
//	document.getElementById("panel_content").style.height = document.body.clientHeight + "px";
	document.getElementById("toolDiv").style.width = document.body.clientWidth + "px";
	
	document.getElementById("gzlbry").click();
	document.getElementById("gllbry").click();
	document.getElementById("fxzry").click();
});

var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=parent.tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  //alert(Ext.urlEncode({'asd':'三大'}));
    	src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
    }
</script>