<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<div id="publishPanel"></div>

<%@include file="/comOpenWinInit.jsp" %>
<style>
.uploadify{
position: absolute;
left: 0px;
top: 0px;
}
.table-tdx { 
          border: 1px solid #000;
             }        
.table-td { background:#FFF;} 
#forView_peopleGrid{
	height:100%;
}

.x-fieldset{
	height:100%;
}
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 406px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<table>
	<tr>
		<odin:select2 property="meetingname" onchange="mNameChange"  label="会议名称" width="200"></odin:select2>
		<td width="20"></td>
		<odin:textEdit property="publishname" label="议题名称" readonly="true"  width="200"></odin:textEdit>
		<td width="20"></td>
		<odin:select2 property="publishtype" label="议题类型" codeType="AgendaType" readonly="true" width="160"></odin:select2>
		<td width="20"></td>
		<odin:textEdit property="titlename" label="标题名称" readonly="true" width="200"></odin:textEdit>
	</tr>
</table>

<odin:toolBar property="btnToolBar" applyTo="publishPanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加议题" icon="image/zjyt.png" id="loadadd" handler="loadadd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改议题" icon="image/icon021a6.gif"  id="infoUpdate" handler="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除议题" icon="image/delete.png" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<%-- --<odin:buttonForToolBar text="议题引用" icon="image/btyy.png" id="publishCite" handler="publishCite" />
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="增加标题" icon="image/zjbt.png" id="titleadd" handler="titleadd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改标题" icon="image/xgbt.png"  id="titleUpdate" handler="titleUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除标题" icon="image/delete2.png"  id="titleDelete" handler="titleDelete"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="新增人员（正式库）" icon="image/icon021a2.gif" id="addperson" handler="addperson" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Lrmx导入" icon="image/icon021a2.gif" id="addLrmx" handler="addLrmx" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="手工录入" icon="image/icon021a2.gif" id="handadd" handler="handadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="Lrmx导出" icon="images/icon/table.gif" id="expLrmxGrid" handler="expLrmxGrid" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="议题授权" icon="image/ytsq.png" id="publishPower" handler="publishPower" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="议题复制" icon="image/ytfz.png" id="publishCopy" handler="publishCopy" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="人员复制" icon="image/ryfz.png" id="personCopy" handler="personCopy"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="人员引用" icon="image/ryyy.png" id="personCite" handler="personCite" />
	<odin:separator></odin:separator>
	<%--<odin:buttonForToolBar text="公示" icon="image/ryyy.png" id="translation" handler="translation" />
	<odin:separator></odin:separator>
	 <odin:buttonForToolBar text="预览" icon="images/search.gif" id="meetingPreview" handler="btn_init" />
	<odin:buttonForToolBar text="生成" icon="images/search.gif" id="meetingCreateRmb" handler="createRmbs" />
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="表格展示" icon="images/search.gif" id="exportPdf" handler="exportPdf" isLast="true"/>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="文档上传" icon="images/search.gif" id="fileload" isLast="true"/>--%>
</odin:toolBar>
<table style="width: 100%">
	<tr>
		<td  width="15%" >
		  <odin:groupBox title="会议树" >
				<div id="tree-div1" style="height: 270px;"></div>
			</odin:groupBox> 
		</td>
		 <td width="13%">
			<odin:editgrid2 property="peopleGrid" hasRightMenu="false" title="" forceNoScroll="true" autoFill="true"  pageSize="200" url="/">
				<odin:gridJsonDataModel>
					<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
					<odin:gridDataCol name="publishid" />
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0192a"/>
					<odin:gridDataCol name="sh001"/>
					<odin:gridDataCol name="delperson"/>
					<odin:gridDataCol name="a0000"/>
					<odin:gridDataCol name="yy_flag"/>
					<odin:gridDataCol name="sh000"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<%-- <odin:gridEditColumn2 header="selectall" width="30" gridName="monthlyWorkGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/> --%>
					<odin:gridEditColumn2 dataIndex="a0101" width="40" header="姓名" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="a0192a" width="100" header="工作单位及职务" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="sh001" width="20" header="排序号" editor="text" edited="false" align="center" />
					<odin:gridEditColumn2 dataIndex="delperson" width="30" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="updateperson"/>
				</odin:gridColumnModel>
			</odin:editgrid2>  
		</td>
		<td width="72%" align="center" rowspan="2">
			<odin:groupBox title="预览">
				<iframe id="mainIframe" name="mainIframe" 
					src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=" 
					frameborder="4" scrolling="auto" width="720" height="600"></iframe>
			</odin:groupBox>
		</td>
	</tr>
	<tr>
	<td colspan="2" width="28%">
<div style="height:345px;overflow: auto;">
<odin:groupBox property="group1" title="人员信息">
	<table >
		<tr>
			<odin:textEdit property="a0101" label="姓名" width="100" ></odin:textEdit>
			<odin:select2 property="a0104" label="性别" codeType="GB2261" width="145" ></odin:select2>
			<%-- <odin:dateEdit property="a0107" label="出生日期" format="Ymd" width="120"/> --%>
			<odin:NewDateEditTag property="a0107" labelSpanId="a0107SpanId" maxlength="8" label="出生日期" width="145"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0141" label="政治面貌" codeType="GB4762" width="100"></odin:select2>
			<odin:textEdit property="zgxl" label="最高学历"  width="145"></odin:textEdit>
			<odin:textEdit property="zgxw" label="最高学位"  width="145"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a0192a" label="现职" colspan="4" width="315"></odin:textEdit>
			<odin:textEdit property="tp0125" label="现职备注"  width="145" ></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="tp0111" label="拟任" colspan="6" width="415" readonly="true" ondblclick="openNR();"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0112" label="拟免" colspan="6" width="415"  readonly="true" ondblclick="openNM();"></odin:textEdit>
		</tr>--%>
		<tr>
			<odin:select2 property="tp0121" label="拟任类型" width="100" onchange="changeNR()" codeType="NIRENTYPE"></odin:select2>
			<odin:textEdit property="tp0111" label="拟任职务"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0123" label="拟任备注"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="tp0122" label="拟免类型" width="100" onchange="changeNM()" codeType="NIMIANTYPE"></odin:select2>
			<odin:textEdit property="tp0112" label="拟免职务"  width="145" ></odin:textEdit>
			<odin:textEdit property="tp0124" label="拟免备注"  width="145" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0113" label="任免理由" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="tp0114" label="备注说明" colspan="6" width="515"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2  property="tp0116" label="显示现职" data="['0', '否'],['1', '是']"  width="100" value=""></odin:select2>
			<odin:select2  property="tp0117" label="显示任免表" data="['0', '否'],['1', '是']"   width="145" value=""></odin:select2>
			<odin:textEdit property="sh001" label="排序号" width="145"></odin:textEdit>
		</tr>
		<%-- <tr>
			<td colspan="5" align="left">
	       <iframe id="frame"  name="frame" height="33px" width="280px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
            </td>
			<td colspan="5" align="left">
				<tags:JUpload property="personfile" uploadLimit="1" label="选择文件" fileTypeDesc="所有文件"   fileSizeLimit="20MB" fileTypeExts="*.*" />
			</td>
			<td colspan="5">
				<odin:button text="附件上传" property="fujSave" handler="fujSave" ></odin:button>
			</td>
			<td  colspan="5">
				<odin:button text="保存" property="personSave" handler="personSave" ></odin:button>
			</td>
		</tr> --%>
		<!-- <tr>
			<td bgcolor="#C0DCF1"   style="text-align: center ; line-height:80px ;">
         <div style="width: 120 px; height: 80px;">
            <font >附件</font>
         </div>   
     	 </td>
			<td bgcolor="#C0DCF1"  colspan="5"  style="text-align: center ; line-height:80px ;">
			<div style="width: 220 px; height: 80px;">
            <span id = "div_fujian"></span>
            </div>
     	 	</td>
		</tr> -->
	</table>
	<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="50"></td>
			<td>
		       <iframe id="frame" name="frame" height="33px" width="330px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td>
				<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()"> --%>
				<odin:button text="提交" property="sc" handler="sc" ></odin:button>
			</td>
			<td  colspan="5">
				<odin:button text="保存" property="personSave" handler="personSave" ></odin:button>
			</td>
			<td width="50"></td>
		</tr>
	</table>
	<odin:hidden property="pid"/>
</form>
	<table>
	<!-- <tr>
	<td  height="15" align="center">
	  <span style="text-align: center;display:block;" id = "div_fujian"></span>
	  </td>
	</tr> -->
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="67" width="555"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="add00"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="filename" width="450" editor="text" edited="false" header="附件名称" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="110" header="操作" editor="text" edited="false" 
						menuDisabled="true" sortable="false" align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
	
</table>
</odin:groupBox>
</div>
</div>
	</td>
	</tr>
</table>
<odin:window src="/blank.htm" id="importLrmWinssh" width="400" height="150"
	title="批量任免表导入" modal="true" />

<odin:hidden property="meetingid"/>
<odin:hidden property="publish_id"/>
<odin:hidden property="title_id"/>
<odin:hidden property="new_userid"/>
<odin:hidden property="p_userid"/>
<odin:hidden property="ischange"/>
<odin:hidden property="downfile"/>
<odin:hidden property="sh000"/>
<odin:hidden property="personPath"/>
<script type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function(){
	document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
	$h.initGridSort('peopleGrid',function(g){
	    radow.doEvent('rolesort');
	  });
	var peopleGrid = Ext.getCmp('peopleGrid');
	peopleGrid.on('rowdblclick',function(gridobj,index,e){
	
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('sh000').value=rc.data.sh000;
		var sh000=document.getElementById('sh000').value;
		
		var yy_flag=rc.data.yy_flag;
		var ischange=document.getElementById('ischange').value;
		var new_userid=document.getElementById('new_userid').value;
		var p_userid=document.getElementById('p_userid').value;
		if(new_userid!=p_userid&&(ischange==null||ischange=="")){
			$h.alert('系统提示','该用户没有修改权限');
			radow.doEvent('personClear');
			yulan_rmb(sh000);
		}else if(yy_flag==1){
			$h.alert('系统提示','该人员为引用人员，请对源数据进行修改');
			radow.doEvent('personClear');
			yulan_rmb(sh000);
		}else{
			radow.doEvent('queryPersonx',sh000);
			//radow.doEvent('expShrmb',sh000);
			yulan_rmb(sh000);
		}
	});
}); 


function queryPersonx(sh000,titleid,titlename){
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');
	}else{
		document.getElementById('sh000').value=sh000;
		document.getElementById('title_id').value=titleid;
		document.getElementById('titlename').value=titlename;
		radow.doEvent('queryPersonx',sh000);
	}
}
//点击提交
function sc(){
	var pid = document.getElementById('sh000').value;
	if(pid != null && pid != ''){
		window.frames['frame'].impd(pid);
	}else{
		$h.alert('系统提示','请选人,再提交附件');
		window.frames['frame'].imd();
	}
	
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
    radow.doEvent("fileGrid.dogridquery");
}
/* function fg() {
	//window.close();
	//var parentWin = window.opener;
    radow.doEvent("fileGrid.dogridquery");
   // realParent.radow.doEvent("fileGrid.dogridquery");
} */
//grid操作列显示的操作情况
function operateUod(value, params, record,rowIndex,colIndex,ds){
	return "<a href=\"javascript:deleteRow('"+rowIndex+"')\">删除</a>&nbsp;";
}
//点击下载按钮
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

// 下载
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// 删除
function deleteRow(rowIndex) {
	// 确认删除
	$h.confirm("系统提示：",'是否确认删除？',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFi", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}

//上传文件
function appendixUpload() {
	var sh000 = document.getElementById('sh000').value;
	// 批次主键
	window.frames["frame"].uploadAppendixFile(sh000);
}
//点击关闭,前面页面 附件内容更新
function colseWin( html ){
	//更新前一页面
	debugger;
	if(document.getElementById("div_fujian")!=null){
		alert(html);
		document.getElementById("div_fujian").innerHTML =html;
		
	}
	//window.close();
}
/* function filele(url){
	var tp0115 = rs.get('tp0115');
	var tp0118 = rs.get('tp0118');
	var url=tp0115.replace(/\\/g,"/");
	var meetingname = rs.get('meetingname');
	fz(meetingname);
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+tp0118+"</a>";
	} 
	

}  */
function cin(fujianInfo){
	debugger;
	alert(fujianInfo);
	/* return "<a href=\"javascript:downloads('" +url +"')\">"+fujianInfo+"</a>"; */
	if(document.getElementById("div_fujian")!=null){
		document.getElementById("div_fujian").innerHTML =fujianInfo;
		
	}
}
 function file(value, params, rs, rowIndex, colIndex, ds){
	var wj05 = rs.get('fileurl');
	var name = rs.get('filename');
	var url=wj05.replace(/\\/g,"/");
	debugger;
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+name+"</a>";
	} 
	

}  
//下载
 function downloadFile(url) {
 	var downfile = url;
 	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
 	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
 }
function queryName(){
	var name = document.getElementById('queryName').value;
	if(!name){
		return;
	}
	//去除字符串内两头的空格
	name = name.replace(/^\s*|\s*$/g,"");
	if(name==''||name=='输入姓名')
		{
		$h.alert('系统提示','请先输入姓名再查询');
		return;
		}
	radow.doEvent('peopleGrid2.dogridquery');
}
//查看附件情况
function fujSave(){
	var sh000 = document.getElementById("sh000").value;//获取p0800的值
	if (sh000 == null || sh000 =="" ) {
		odin.alert("请先选人,再提交附件！");
		return;
	}
	$h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "附件列表", 760, 560, sh000,"<%=request.getContextPath()%>");
}

function translation(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请先选择议题！');
		return;
	}
	radow.doEvent('translation');
}

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
}

function publishPower(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请先选择议题！');
		return;
	}
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid==p_userid){
		$h.openPageModeWin('meetingPower','pages.meeting.MeetingPower','议题授权',600,550,{publish_id:publish_id},g_contextpath);
	}else{
		$h.alert('系统提示','非创建人无法授权');	
	}
}

function createRmbs(){
	var sh000=document.getElementById('sh000').value;
	var str1='';
	var str2='';
	if(sh000==''){
		var title_id=document.getElementById('title_id').value;
		if(title_id==''){
			var publish_id=document.getElementById('publish_id').value;
			if(publish_id==''){
				$h.alert('系统提示','请先选择生成对象');
				return;
			}else{
				str1='publish_id';
				str2=publish_id;
			}
		}else{
			str1='title_id';
			str2=title_id;
		}
	}else{
		str1='sh000';
		str2=sh000;
	}
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('createRmbs',str1+"@@"+str2);
}

function btn_init(){
	var publishid=document.getElementById('publish_id').value;
	if(publishid==''){
		$h.alert('系统提示','请先选择议题再预览');
	}else{
		document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=' +publishid;
	}
	//document.getElementById('mainIframe').src=document.frames["mainIframe"].location;
	
}

function yulan_rmb(sh000){
	document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=sh000@@'+sh000;
} 


function exportPdf(){
	var publishid=document.getElementById('publish_id').value;
	if(publishid==''){
		$h.alert('系统提示','请先选择议题再预览');
	}else{
		$h.openPageModeWin('MeetingPreview','pages.meeting.MeetingPreview2&publishid='+publishid,'表格展示',800,800,{publishid:publishid},g_contextpath);
	}
	//document.getElementById('mainIframe').src=document.frames["mainIframe"].location;
	
}

function updateperson(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
}
/*
function addpeople(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"addpel('"+record.get("a0000")+"');\">新增</a></font>";
}*/

function publishCopy(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请选择议题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('publishCopy','pages.meeting.PublishCopy','议题复制',360,400,{publish_id:publish_id},g_contextpath);
	}
}

function publishCite(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请选择议题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('publishCite','pages.meeting.PublishCite','议题引用',360,400,{publish_id:publish_id},g_contextpath);
	}
}

function personCopy(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请选择标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('personCopy','pages.meeting.PersonCopy','人员复制',800,600,'',g_contextpath);
	}
}

function personCite(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请选择被引用标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('publishCite','pages.meeting.PersonCite','人员引用',800,600,{title_id:title_id},g_contextpath);
	}
}

function handadd(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请选择先选择人员所在标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('handadd','pages.meeting.MeetingUpdatePel','新增人员',630,400,'',g_contextpath);
	}
}
function updatepel(sh000,yy_flag){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else if(yy_flag==1){
		$h.alert('系统提示','该人员为引用人员，请对源数据进行修改');
	}else{
		$h.openPageModeWin('updateperson','pages.meeting.MeetingUpdatePel','修改人员',580,550,sh000,g_contextpath);
	}
}

function deletepel(sh000,a0000){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		radow.doEvent('delperson',sh000+','+a0000);
	}
}

function nameChange(record,index){
	document.getElementById('publish_id').value = record.data.key;
	updateTree();
}

function mNameChange(record,index){
	document.getElementById('meetingid').value = record.data.key;
	document.getElementById('publish_id').value="";
	document.getElementById('title_id').value="";
	document.getElementById('publishname').value ="";
	document.getElementById('publishtype').value ="";
	document.getElementById('publishtype_combo').value ="";
	document.getElementById('titlename').value ="";
	document.getElementById('p_userid').value ="";
	document.getElementById('ischange').value ="";
	updateTree();
}
function reloadSelData(){
	document.getElementById('publish_id').value ="";
	document.getElementById('title_id').value ="";
	document.getElementById('publishname').value ="";
	document.getElementById('publishtype').value ="";
	document.getElementById('publishtype_combo').value ="";
	document.getElementById('titlename').value ="";
	document.getElementById('p_userid').value ="";
	document.getElementById('ischange').value ="";
	//radow.doEvent('initX');
	updateTree();
}

function reloadPerGrid(){
	radow.doEvent('peopleGrid.dogridquery');
}


function titleadd(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请先选择议题或标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('TitleAdd','pages.meeting.TitleAdd','新增',650,530,{type:'add'},g_contextpath);
	}
}
function titleUpdate(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请先选择标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('TitleAdd','pages.meeting.TitleAdd','修改标题',650,530,{type:'update'},g_contextpath);
	}

}

function titleDelete(){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	var title_id=document.getElementById('title_id').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else if(title_id==null||title_id==""){
		$h.alert('系统提示','请先选择标题');
	}else{
		var titlename=document.getElementById('titlename').value;
		if(confirm('确认删除标题：'+titlename+ ' ?')){
			radow.doEvent('titleDelete');
		}
	}
}

function infoDelete(){
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	var publish_id=document.getElementById('publish_id').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else if(publish_id==null||publish_id==""){
		$h.alert('系统提示','请先选择议题');
	}else{
		var publishname=document.getElementById('publishname').value;
		if(confirm('确认删除议题：'+publishname+ ' ?')){
			radow.doEvent('confirmDelete');
		}
	}
}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.meeting.PublishAdd','新增',250,200,{publish_id:''},g_contextpath);
}
function infoUpdate(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请先选择议题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('loadadd','pages.meeting.PublishAdd','修改议题',250,315,{publish_id:publish_id},g_contextpath);
	}
}
Ext.onReady(function() {
	document.getElementById('meetingid').value = document.getElementById('subWinIdBussessId2').value;
	var viewSize = Ext.getBody().getViewSize();
	var publishGrid = Ext.getCmp('peopleGrid');
	publishGrid.setHeight(317);
	//var publishGrid2 = Ext.getCmp('peopleGrid2');
	//publishGrid2.setHeight(viewSize.height-28);
	//publishGrid.setWidth(350);
	var btnToolBar = Ext.getCmp('btnToolBar');
	btnToolBar.setWidth(viewSize.width);
	/* publishGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('publish_id').value = rc.data.publishid;
	}); */
	
	/*$h.initGridSort('publishGrid',function(g){
		radow.doEvent('publishsort');
	}); */
});

Ext.onReady(function() {
	 var tree = new Ext.tree.TreePanel({    
          region: 'center',
          id: 'titleTree',
          el: 'tree-div1',
          //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
          collapsible: true,    
          /* title: '标题',//标题文本  */   
          width: 265,  
          height:260,
          border : false,//表框    
          autoScroll: true,//自动滚动条    
          animate : true,//动画效果    
          rootVisible: true,//根节点是否可见    
          split: true,    
          loader : new Ext.tree.TreeLoader({
       	   dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.meeting.Publish&eventNames=updateTree&meetingid='
          }),  
          root : new Ext.tree.AsyncTreeNode({    
              text: "",
              id:'-1'
          }),  
          listeners: { 
       	   click: function(node){
       		   //alert(node.attributes.id);
       		   //document.getElementById('titlename').value=node.attributes.text;
       		   //var title_id=node.attributes.id;
       		   var title_id=node.attributes.id;
       		   radow.doEvent("updateTitle",title_id); 
       	   },
              afterrender: function(node) {        
            	  node.expandAll();//展开树 
              }        
          }     
      });    
	 tree.render();
});

function updateTree() {
	var tree = Ext.getCmp("titleTree");
	var url = 'radowAction.do?method=doEvent&pageModel=pages.meeting.Publish&eventNames=updateTree&meetingid=';
	tree.loader.dataUrl = url + document.getElementById('meetingname').value;
	tree.root.setText(Ext.getCmp('meetingname_combo').getValue());
	tree.root.reload();
	tree.expandAll();
}

function uploadfile(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请选择一行数据！');
		return;
	}
	$('#fileload2').uploadify('upload','*');
	//reload();
	//alert();
}
function reload(){
	radow.doEvent('publishGrid.dogridquery');
}
function HandleFile(value, params, record, rowIndex, colIndex, ds){
	if(record.get("pat00s")==null||record.get("pat00s")==""){
		return;
	}
	//只有一条信息时
	if(record.get("pat00s").indexOf(",")==-1){
		result = "<table width=''><tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+record.get("pat00s")+"');\">"+record.get("pat04s")+"</a></font></td><td align='left'><u style=\"color:#FFFFFF\">上移</u>&nbsp;&nbsp;&nbsp;<u style=\"color:#FFFFFF\">下移</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+record.get("pat00s")+"')\">删除</a></tr>";
		return result;
	}
	var pat00s = record.get("pat00s").split(",");
	var pat04s = record.get("pat04s").split(",");
	var result = "<table width='100%'>";
	for(var i=0;i<pat00s.length;i++){
		/* result = result+"<div align='center' width='100%' ><font color=blue>"
		+ "<a href=\"javascript:deleteRow2()\">上移</a>&nbsp; &nbsp; &nbsp;<a href=\"javascript:deleteRow2()\">下移</a>&nbsp; &nbsp; &nbsp;<a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a>"
		+ "</font></div><br>"; */
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">上移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">下移</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		if(i==0){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><u style=\"color:#D3D3D3\">上移</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">下移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">删除</a></tr>";
			continue;
		}
		if(i==pat00s.length-1){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">上移</a>&nbsp;&nbsp;&nbsp;<u style=\"color:#D3D3D3\">下移</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">删除</a></tr>";
			continue;
		}
		
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">上移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">下移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">删除</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">上移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">下移</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">删除</a></tr>";
	}
	/* result = result.substring(0,result.length-4); */
	result = result+"</table>"
	return result;
}
function outfile(pat00){
	var url = g_contextpath+'/PublishFileServlet?method=publish_att&pat00='+pat00;
	window.location.href=url;
}

function topordown(pat00,type){
	radow.doEvent('sort',pat00+"@"+type);
}

function deleteAtt(pat00){
	radow.doEvent('deleteAtt',pat00);
}

function onQueueComplete(){
	radow.doEvent('publishGrid.dogridquery');
}
function queryPerson(){
	radow.doEvent('peopleGrid.dogridquery');
}

function addperson(){
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请选择标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		$h.openPageModeWin('addperson','pages.meeting.MeetingByName','新增人员',1020,520,{meeting_id:''},g_contextpath);
	}
}


function expLrmxGrid(){
	var publish_id = document.getElementById('publish_id').value;
	if(publish_id==''){
		$h.alert('系统提示','请选择议题！');
		return;
	}
	//radow.doEvent('exportLrmxBtn');
	radow.doEvent("exportLrmxBtn", "zip");
}

function addLrmx(){
	var contextPath = '<%=request.getContextPath()%>';
	var title_id = document.getElementById('title_id').value;
	if(title_id==''){
		$h.alert('系统提示','请选择标题！');
		return;
	}
	var ischange=document.getElementById('ischange').value;
	var new_userid=document.getElementById('new_userid').value;
	var p_userid=document.getElementById('p_userid').value;
	if(new_userid!=p_userid&&(ischange==null||ischange=="")){
		$h.alert('系统提示','该用户没有修改权限');	
	}else{
		var publish_id = document.getElementById('publish_id').value;
		odin.showWindowWithSrc("importLrmWinssh",contextPath+"/pages/publicServantManage/ImportLrmssh.jsp?businessClass=com.picCut.servlet.SaveLrmFile&title_id="+title_id+"&publish_id="+publish_id);
	}
}

/*var addper =  new Ext.menu.Menu({id:'addper',items:[
new Ext.menu.Item({id:'printRmb',disabled:false,text:'打印'}),
new Ext.menu.Item({id:'printSet',disabled:false,text:'打印机设置'})
]});*/

function changeNR(){
	var tp0113=document.getElementById('tp0113').value;
	if(tp0113==''){
		var tp0121=document.getElementById('tp0121').value;
		if(tp0121=='2'){
			document.getElementById('tp0113').value='干部挂职';
		}else if(tp0121=='4'){
			document.getElementById('tp0113').value='试用期满转正';
		}
	}
}

function changeNM(){
	var tp0113=document.getElementById('tp0113').value;
	if(tp0113==''){
		var tp0122=document.getElementById('tp0122').value;
		if(tp0122=='3'||tp0122=='4'){
			document.getElementById('tp0113').value='已到法定退休年龄';
		}
	}
}

function personSave(){
	var sh000=document.getElementById('sh000').value;
	if(sh000==null||sh000==''){
		alert('请先双击选择人员！');
	}else{
		var tp0113=document.getElementById('tp0113').value;
		if(tp0113==''){
			if(confirm("任免理由为空，以“工作需要”自动填充？")){
				document.getElementById('tp0113').value='工作需要';
			}
		}
		radow.doEvent("personSave");
		yulan_rmb(sh000);
	}
}
/* function downloadword(){
	setTimeout(xx,1000);
} */
function downloadword(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
	//window.location = "DownloadServlet?method=PersionFile&filePath=" + encodeURI(encodeURI(url));
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function download(id){
	var personPath = document.getElementById('personPath').value;
	//alert(personPath);
	window.location="ProblemDownServlet?method=downFile&prid="+personPath;
}

var g_contextpath = '<%= request.getContextPath() %>';

function openNR(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=1','拟任信息',580,350,'',g_contextpath);
}

function openNM(){
	$h.openPageModeWin('meetingMove','pages.meeting.MeetingMove&type=2','拟免信息',580,350,'',g_contextpath);
}
function clearValue(){

}

</script>