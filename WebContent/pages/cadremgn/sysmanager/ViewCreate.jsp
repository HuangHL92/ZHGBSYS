<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<%-- <%@include file="/comOpenWinInit2.jsp" %> --%>
  <style>
	#area1 {
		border: 0px rgb(206, 221, 239) solid;
		height:100%;
		width: 505;
		float: left;
	}
	#area3 {
		border: 0px rgb(206, 221, 239) solid;
		height:100%;
		width: 493;
		float: left;
	}
	#tab__tab1{
		margin-left:420;
	}
	</style>
 <odin:tab id="tab" height="600" >
	<odin:tabModel >
		<odin:tabItem title="&nbsp;&nbsp;&nbsp;视&nbsp;&nbsp;&nbsp;图&nbsp;&nbsp;&nbsp;" id="tab1"></odin:tabItem>
		<odin:tabItem title="设置条件" id="tab2" ></odin:tabItem>
		<odin:tabItem title="&nbsp;&nbsp;&nbsp;预&nbsp;&nbsp;览&nbsp;&nbsp;&nbsp;&nbsp;" id="tab3" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1">
 <div id="area1">
<table style="border:solid 0px !important;width:100%;">
	<tr>
		<td>
			<odin:groupBox property="havecreateviewproperty" title="已建立视图" >
			<odin:editgrid property="viewListGrid" bbarId="pageToolBar"  
			pageSize="20" width="400" height="460" url="/" rowDbClick="rowqvDbClick" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="qvid" />
					<odin:gridDataCol name="viewname" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="主键" width="0"  dataIndex="qvid"  edited="false" editor="text" align="left" />
					<odin:gridEditColumn header="视图名" width="440"  dataIndex="viewname"  edited="false" editor="text" align="left" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
			</odin:groupBox>
		</td>
	</tr>
</table>
</div>
<div id="area3">
<table style="border:solid 0px !important;width:100%;">
	<tr>
		<td >
		<odin:groupBox property="viewinfoproperty" title="视图信息" >
			<table style="border:solid 0px !important">
			<tr style="height:40;">
				<odin:textEdit property="viewname" label="视图名" colspan="3" maxlength="100"  width="400" ></odin:textEdit>
				<odin:hidden property="qvid"/>
			</tr>
			<tr style="height:40;">
				<odin:textEdit property="chinesename" label="中文名" colspan="3" maxlength="50" width="400" ></odin:textEdit>	
			</tr>
			<tr style="height:40;">
				<odin:textEdit property="use" label="用&nbsp&nbsp途" colspan="3"  width="400" ></odin:textEdit>	
			</tr>
			</table>
			<table style="width:400">
				<tr style="height:40;">
					<td style="font-size:12;">功能描述</td>
				</tr>
				<tr>
					<odin:textarea property="funcdesc" cols="79" colspan="2" rows="21" ></odin:textarea>
					
				</tr>
			</table>
		</odin:groupBox>
		</td>
	</tr>
</table>
</div>
<div style="width:1000px;">
	<table style="width:100%;" border="0">
		<tr>
			<td style="width:680;text-align:right;vertical-align: middle;" >
				<input class="yellowbutton" type="button" value="&nbsp;&nbsp;&nbsp;删&nbsp;&nbsp;&nbsp;&nbsp;除&nbsp;&nbsp;&nbsp;" onclick="deleteqv()" id="btn1"  style="width:80;" />
				<%-- <odin:button text="&nbsp;&nbsp;&nbsp;删&nbsp;&nbsp;&nbsp;&nbsp;除&nbsp;&nbsp;&nbsp;" property="btn1" handler="deleteqv"></odin:button> --%>
			</td>
			<td style="width:160;text-align:center;">
				<input class="yellowbutton" type="button" value="保存并设置" onclick="saveandset()" id="btn2"  style="width:80;" />
				<%-- <odin:button text="保存并设置" property="btn2" handler="saveandset"></odin:button> --%>
			</td>
			<td style="width:160;text-align:left;">
				<input class="yellowbutton" type="button" value="&nbsp;&nbsp;&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;&nbsp;&nbsp;" onclick="clearqv()" id="btn3"  style="width:80;" />
				<%-- <odin:button text="&nbsp;&nbsp;&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;&nbsp;&nbsp;" property="btn3"  handler="clearqv"></odin:button> --%>
			</td>
		</tr>
		<tr height="9px">
		</tr>
	</table>
</div>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
	<iframe id="iframeCondition" width="100%" height="575px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.ViewCondition" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
</odin:tabCont>
<odin:tabCont itemIndex="tab3">
	<iframe id="iframePreview" width="100%" height="575px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.PreviewQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
</odin:tabCont>
</odin:tab>
<script type="text/javascript">
function refreshList(){//加载已建立视图
	radow.doEvent("viewListGrid.dogridquery");
};
function saveandset(){//保存并设置
	
	//"功能描述"不可超过50字
	var funcdesc = document.getElementById('funcdesc').value;	
	
	if(funcdesc.length > 500){
		Ext.Msg.alert("系统提示","功能描述不可超过500字！");
		return;
	}
	 
	radow.doEvent("saveandset");
}
function rowqvDbClick(grid,rowIndex,colIndex,event){//已建视图行双击事件
   	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
   	//弹出窗口
   	radow.doEvent("savetoqv",record.get("qvid"));
}
function clearqv(){//重置
	document.frames['iframeCondition'].Ext.getCmp('tableList2Grid').getStore().removeAll();
	document.frames['iframeCondition'].Ext.getCmp('codeList2Grid').getStore().removeAll();
	document.frames['iframeCondition'].Ext.getCmp('codeList2Grid1').getStore().removeAll();
	document.frames['iframePreview'].Ext.getCmp('previewListGrid').getStore().removeAll();
	radow.doEvent("clearqv");
}
function deleteqv(){//删除
	radow.doEvent("deleteCheck",document.getElementById('viewname').value);
}
function setTab2(){
	document.getElementById('iframeCondition').contentWindow.initList();
}
function setDisalbe(){
	document.getElementById('iframeCondition').contentWindow.setDisalbe();
}
function setQvidToIframe(iframeid,qvid){
	document.getElementById(iframeid).contentWindow.setQvId(qvid);
}
/* function rowtableDbClick(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	radow.doEvent("loadtofld",record.get("tblcod"));
} */
function refreshPreviewTab(){
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.PreviewQuery" ;
	var sqlPre=document.frames["iframeCondition"].document.getElementById('querysql').value;
	var qvid=document.frames["iframeCondition"].document.getElementById('qvid').value;
	
	//console.log(sqlPre);
	if(sqlPre==""||sqlPre==" "||sqlPre==null||sqlPre.length==0){
		
		
		//先保存 add by zoulei 2018年5月18日
		document.frames["iframeCondition"].saveFunc("refreshPreviewTab");
		return;
	}
	odin.ext.getCmp('tab').activate('tab3');
	document.getElementById('iframePreview').src=url+"&sql="+""+"&qvid="+qvid;
}

function refreshPreviewTab2(){
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.PreviewQuery" ;
	var sqlPre=document.frames["iframeCondition"].document.getElementById('querysql').value;
	var qvid=document.frames["iframeCondition"].document.getElementById('qvid').value;
	
	if(sqlPre==""||sqlPre==" "||sqlPre==null||sqlPre.length==0){
		alert("请先点击保存！");
		return;
	}
	odin.ext.getCmp('tab').activate('tab3');
	document.getElementById('iframePreview').src=url+"&sql="+""+"&qvid="+qvid;
} 
function loadGridPre(qvid){
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.PreviewQuery" ;
	document.getElementById('iframePreview').src=url+"&sql="+""+"&qvid="+qvid;
}
//新增视图，则清空设置条件tab页面的全局js变量
function clearParameter(){
	document.getElementById('iframeCondition').contentWindow.clearConditionPar();
}
//修改视图
//function setConditionPar(){
	
//}
Ext.onReady(function(){
	//havecreateviewproperty
	//viewListGrid
	//viewinfoproperty
	var viewdomListGrid=Ext.getCmp('viewListGrid');
	viewdomListGrid.setHeight(485);
	document.getElementById('havecreateviewproperty').firstChild.style.height='520px';
	document.getElementById('viewinfoproperty').firstChild.style.height='520px';
	
	 document.frames["iframeCondition"].Ext.getCmp('tableList2Grid').setHeight(485);
	 document.frames["iframeCondition"].Ext.getCmp('codeList2Grid').setHeight(218);
	 document.frames["iframeCondition"].Ext.getCmp('codeList2Grid1').setHeight(218);
	 document.frames["iframeCondition"].document.getElementById('conditionName8').style.height='150px';
	 document.frames["iframeCondition"].document.getElementById('confirmconditionproperty').firstChild.style.height='320px';
	 document.frames["iframeCondition"].document.getElementById('mergeconditionproperty').firstChild.style.height='190px';
	 
	
	 
});
</script>
