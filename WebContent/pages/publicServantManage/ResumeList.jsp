<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript" src="../../basejs/helperUtil.js"></script>
<style>
.x-panel-header{
border: 0px;
}
.x-toolbar span{
	font: bold;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">

</script>



<odin:toolBar property="toolBar0" applyTo="toolBar_div" >
	<odin:textForToolBar text="简历维护"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="初始化简历列表"  id="initdata" icon="image/u53.png"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="增加"  id="addBtn" icon="images/add.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="保存" id="save" handler="saveData" icon="images/save.gif"></odin:buttonForToolBar>
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<div>

<odin:tab id="jltab">
	<odin:tabModel>
	  	<odin:tabItem title="简历编辑" id="tab0"></odin:tabItem>
	  	<odin:tabItem title="简历文本编辑" id="tab1"></odin:tabItem>
	  	<odin:tabItem title="按职务自动生成简历" id="tab2" isLast="true"></odin:tabItem>
  	</odin:tabModel> 
   	<odin:tabCont itemIndex="tab0" >
   		<div id ="toolBar_div" style="width:100%;"></div>
   		<table>
   		<tr><td></td></tr>
	 	<tr>
	 	<td width="600px" > 
		<odin:grid property="a1701grid" isFirstLoadData="false" height="373" forceNoScroll="true" url="/" >
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
	     		<odin:gridDataCol name="isMatch" />
	     		<odin:gridDataCol name="a1701EntryId" />
	     		<odin:gridDataCol name="startDate" />
	     		<odin:gridDataCol name="endDate" />
	     		<odin:gridDataCol name="entryContent" />
	     		<odin:gridDataCol name="refBs" />
	     		<odin:gridDataCol name="refBsId" />
		   		<odin:gridDataCol name="a0000" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
			  <odin:gridRowNumColumn />
			  <odin:gridEditColumn header="isMatch" dataIndex="isMatch" editor="text" edited="false" hidden="true"/>
			  <odin:gridEditColumn header="id" dataIndex="a1701EntryId" editor="text" edited="false" hidden="true"/>
			  <odin:gridEditColumn header="人员id" dataIndex="a0000" editor="text" edited="false" hidden="true"/>
			  <odin:gridEditColumn header="开始时间" width="50" dataIndex="startDate" codeType="ZB123" edited="false" editor="select"/>
			  <odin:gridEditColumn header="结束时间" width="50" dataIndex="endDate" edited="false" editor="text"/>
			  <odin:gridEditColumn header="内容" width="150" dataIndex="entryContent" edited="false" editor="text"/>
			  <odin:gridEditColumn header="操作" dataIndex="a1701EntryId" width="100" align="center" editor="text" edited="false" renderer="op1Renderer" isLast="true"/>
			</odin:gridColumnModel>
		</odin:grid>
		</td>
		<td valign="top">
			<table id="table2">
				<tr height="30px;"><odin:NewDateEditTag property="startDate" label="开始日期" maxlength="8" isCheck="true" required="true"></odin:NewDateEditTag></tr>
				<tr height="30px;"><odin:NewDateEditTag property="endDate" label="结束日期" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
				<tr ><odin:textarea property="entryContent" cols="25" rows="10" label="内容" required="true"></odin:textarea></tr>
				<tr height="30px;"><odin:select2 property="refBs" label="关联业务类型" data="['A02','职务信息'],['A08','学历学位信息']"></odin:select2>	</tr>
				<%-- <tr height="35px;"><tags:PublicTextIconEdit property="" label="" readonly="true" codetype=""></tags:PublicTextIconEdit></tr> --%>
				<odin:hidden property="a1701EntryId" title="主键id" ></odin:hidden>
				<odin:hidden property="refBsId" title="主键id" ></odin:hidden>
				<odin:hidden property="isMatch" title="主键id" ></odin:hidden>
				<odin:hidden property="a0000" title="主键id" ></odin:hidden>
			</table>
		</td>
		</tr>
		</table>
	</odin:tabCont>
   		<odin:tabCont itemIndex="tab1" >
		<textarea style="height:376px;width: 870px;font-size:20px;" id="contenttext" style="font-family: '宋体', Simsun;"></textarea>
		<table style="width: 100%;">
			<tr>
				<td width="650px"></td>
				<td><odin:button property="qx" text="&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;" ></odin:button></td>
				<td width="5px"></td>
				<td><odin:button property="gb1" text="&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;" ></odin:button></td>
			</tr>
		</table>
		
	</odin:tabCont>
   	<odin:tabCont itemIndex="tab2">
   		<textarea style="height:376px;width: 870px;font-size:20px;" id="contenttext2" style="font-family: '宋体', Simsun;"></textarea>
   		<table style="width: 100%;">
			<tr>
				<td width="650px"></td>
				<td><odin:button property="qx2" text="&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;" ></odin:button></td>
				<td width="5px"></td>
				<td><odin:button property="gb2" text="&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;" ></odin:button></td>
			</tr>
		</table>
   	</odin:tabCont>
</odin:tab>
</div>
<script type="text/javascript">

function openRefWin(a1701EntryId){
	$h.openPageModeWin('selectywxx','pages.publicServantManage.ResumeBsSelect','选择关联业务',600,360,a1701EntryId,'<%=request.getContextPath()%>',null,null,null);
}

function saveData(){
	
	
	//开始日期
	var startDate = document.getElementById('startDate').value;	
	if(startDate == ''){
		odin.alert("开始日期不可为空！");
		return false;
	}
	//entryContent
	var entryContent = document.getElementById('entryContent').value;	
	if(entryContent == ''){
		odin.alert("内容不可为空！");
		return false;
	}
	var startDate_1 = document.getElementById('startDate_1').value;	
	
	var text = dateValidateBeforeTady(startDate_1);
	if(startDate_1.indexOf(".") > 0){
		text = dateValidateBeforeTady(startDate);
	}
	if(text!==true){
		$h.alert('系统提示','开始日期：' + text, null,400);
		return false;
	}
	
	//结束日期
	var endDate = document.getElementById('endDate').value;	
	var endDate_1 = document.getElementById('endDate_1').value;	
	
	var text1 = dateValidateBeforeTady(endDate_1);
	if(endDate_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(endDate);
	}
	if(text1!==true){
		$h.alert('系统提示','结束日期：' + text1, null,400);
		return false;
	}
	radow.doEvent("saveData");
　　
}

function op1Renderer(value, params, record, rowIndex, colIndex, ds){
	var op = "";
	var a1701EntryId = record.data.a1701EntryId;
	if(realParent.buttonDisabled){
		return "删除";
	}
	var isMatch = record.data.isMatch;
	if(isMatch == '1' || isMatch == '2'){
		op = op + "<a href=\"javascript:openRefWin(&quot;"+a1701EntryId+"&quot;)\">业务关联</a> | ";
		op = op + "<a href=\"javascript:radow.doEvent(&quot;removeRef&quot;,&quot;"+a1701EntryId+"&quot;)\">解除关联</a> | ";
	} else {
		op = op + "<a href=\"javascript:openRefWin(&quot;"+a1701EntryId+"&quot;)\">业务关联</a> | ";
	}
	return op + "<a href=\"javascript:deleteRow2(&quot;"+a1701EntryId+"&quot;)\">删除</a>";
}
function deleteRow2(a1701EntryId){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1701EntryId);
		}else{
			return;
		}		
	});	
}
Ext.onReady(function(){
	
});

Ext.onReady(function(){
	var side_resize=function(){
		 /* Ext.getCmp('degreesgrid').setHeight(400);
		 Ext.getCmp('degreesgrid').setWidth(570);  */
		 document.getElementById('toolBar0').style.width = document.body.clientWidth ;
	}
	side_resize();  
	window.onresize=side_resize; 
	
})

function open(a0000){
	$h.openPageModeWin('selectzgxlxw','pages.publicServantManage.SelectZGXLXW','选择最高学历学位',580,180,a0000,'<%=request.getContextPath()%>',null,null,null);
}
function openWin(id,url,title,width,height,param,ctx,parentScope,wincfg,isrmb){
	if(!parentScope){
		parentScope = $h.getTopParent();
	}
	
	var pWidth = parentScope.Ext.getBody().getViewSize().width;
	var pHeigth = parentScope.Ext.getBody().getViewSize().height
	
	if(!width||pWidth<width){
		if(isrmb){
			width = pWidth;
		}else{
			width = parseInt(pWidth*0.6);
		}
		
	}
	if(!height||pHeigth<height){
		if(isrmb){
			height = pHeigth;
		}else{
			height = parseInt(pHeigth*0.8);
		}
		
	}
	var wtop = (pHeigth-height)/2;
	var wleft = (pWidth-width)/2;
	if(wincfg&&wincfg.top){
		wtop = wtop>wincfg.top?wincfg.top:wtop;
	}
	var p = Ext.urlEncode({'subWinId':id,'subWinIdBussessId':param});//'&subWinId='+id+'&subWinIdBussessId='+param;
	url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+'&'+p;
	var pjson = {id:id,title:title,maximizable:true, src:url,width:width,parentWinObj:parentScope,
			height:height,closeAction:'close',thisWin:window,param:param,closable:false,maximizable:false}
	Ext.apply(pjson,wincfg);
	var newWin_ = newWin(pjson);
	newWin_.show();
	if(!wincfg||(wincfg&&!wincfg.maximized)){//不是最大化
		newWin_.setWidth(width);
		newWin_.setHeight(height);
		newWin_.setPosition(wleft,wtop);
	}
	return newWin_;

}




</script>

<div id="cover_wrap1"></div>
