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
	<odin:textForToolBar text="����ά��"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="��ʼ�������б�"  id="initdata" icon="image/u53.png"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����"  id="addBtn" icon="images/add.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����" id="save" handler="saveData" icon="images/save.gif"></odin:buttonForToolBar>
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<div>

<odin:tab id="jltab">
	<odin:tabModel>
	  	<odin:tabItem title="�����༭" id="tab0"></odin:tabItem>
	  	<odin:tabItem title="�����ı��༭" id="tab1"></odin:tabItem>
	  	<odin:tabItem title="��ְ���Զ����ɼ���" id="tab2" isLast="true"></odin:tabItem>
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
			  <odin:gridEditColumn header="��Աid" dataIndex="a0000" editor="text" edited="false" hidden="true"/>
			  <odin:gridEditColumn header="��ʼʱ��" width="50" dataIndex="startDate" codeType="ZB123" edited="false" editor="select"/>
			  <odin:gridEditColumn header="����ʱ��" width="50" dataIndex="endDate" edited="false" editor="text"/>
			  <odin:gridEditColumn header="����" width="150" dataIndex="entryContent" edited="false" editor="text"/>
			  <odin:gridEditColumn header="����" dataIndex="a1701EntryId" width="100" align="center" editor="text" edited="false" renderer="op1Renderer" isLast="true"/>
			</odin:gridColumnModel>
		</odin:grid>
		</td>
		<td valign="top">
			<table id="table2">
				<tr height="30px;"><odin:NewDateEditTag property="startDate" label="��ʼ����" maxlength="8" isCheck="true" required="true"></odin:NewDateEditTag></tr>
				<tr height="30px;"><odin:NewDateEditTag property="endDate" label="��������" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
				<tr ><odin:textarea property="entryContent" cols="25" rows="10" label="����" required="true"></odin:textarea></tr>
				<tr height="30px;"><odin:select2 property="refBs" label="����ҵ������" data="['A02','ְ����Ϣ'],['A08','ѧ��ѧλ��Ϣ']"></odin:select2>	</tr>
				<%-- <tr height="35px;"><tags:PublicTextIconEdit property="" label="" readonly="true" codetype=""></tags:PublicTextIconEdit></tr> --%>
				<odin:hidden property="a1701EntryId" title="����id" ></odin:hidden>
				<odin:hidden property="refBsId" title="����id" ></odin:hidden>
				<odin:hidden property="isMatch" title="����id" ></odin:hidden>
				<odin:hidden property="a0000" title="����id" ></odin:hidden>
			</table>
		</td>
		</tr>
		</table>
	</odin:tabCont>
   		<odin:tabCont itemIndex="tab1" >
		<textarea style="height:376px;width: 870px;font-size:20px;" id="contenttext" style="font-family: '����', Simsun;"></textarea>
		<table style="width: 100%;">
			<tr>
				<td width="650px"></td>
				<td><odin:button property="qx" text="&nbsp;&nbsp;ȷ&nbsp;&nbsp;��&nbsp;&nbsp;" ></odin:button></td>
				<td width="5px"></td>
				<td><odin:button property="gb1" text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" ></odin:button></td>
			</tr>
		</table>
		
	</odin:tabCont>
   	<odin:tabCont itemIndex="tab2">
   		<textarea style="height:376px;width: 870px;font-size:20px;" id="contenttext2" style="font-family: '����', Simsun;"></textarea>
   		<table style="width: 100%;">
			<tr>
				<td width="650px"></td>
				<td><odin:button property="qx2" text="&nbsp;&nbsp;ȷ&nbsp;&nbsp;��&nbsp;&nbsp;" ></odin:button></td>
				<td width="5px"></td>
				<td><odin:button property="gb2" text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" ></odin:button></td>
			</tr>
		</table>
   	</odin:tabCont>
</odin:tab>
</div>
<script type="text/javascript">

function openRefWin(a1701EntryId){
	$h.openPageModeWin('selectywxx','pages.publicServantManage.ResumeBsSelect','ѡ�����ҵ��',600,360,a1701EntryId,'<%=request.getContextPath()%>',null,null,null);
}

function saveData(){
	
	
	//��ʼ����
	var startDate = document.getElementById('startDate').value;	
	if(startDate == ''){
		odin.alert("��ʼ���ڲ���Ϊ�գ�");
		return false;
	}
	//entryContent
	var entryContent = document.getElementById('entryContent').value;	
	if(entryContent == ''){
		odin.alert("���ݲ���Ϊ�գ�");
		return false;
	}
	var startDate_1 = document.getElementById('startDate_1').value;	
	
	var text = dateValidateBeforeTady(startDate_1);
	if(startDate_1.indexOf(".") > 0){
		text = dateValidateBeforeTady(startDate);
	}
	if(text!==true){
		$h.alert('ϵͳ��ʾ','��ʼ���ڣ�' + text, null,400);
		return false;
	}
	
	//��������
	var endDate = document.getElementById('endDate').value;	
	var endDate_1 = document.getElementById('endDate_1').value;	
	
	var text1 = dateValidateBeforeTady(endDate_1);
	if(endDate_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(endDate);
	}
	if(text1!==true){
		$h.alert('ϵͳ��ʾ','�������ڣ�' + text1, null,400);
		return false;
	}
	radow.doEvent("saveData");
����
}

function op1Renderer(value, params, record, rowIndex, colIndex, ds){
	var op = "";
	var a1701EntryId = record.data.a1701EntryId;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	var isMatch = record.data.isMatch;
	if(isMatch == '1' || isMatch == '2'){
		op = op + "<a href=\"javascript:openRefWin(&quot;"+a1701EntryId+"&quot;)\">ҵ�����</a> | ";
		op = op + "<a href=\"javascript:radow.doEvent(&quot;removeRef&quot;,&quot;"+a1701EntryId+"&quot;)\">�������</a> | ";
	} else {
		op = op + "<a href=\"javascript:openRefWin(&quot;"+a1701EntryId+"&quot;)\">ҵ�����</a> | ";
	}
	return op + "<a href=\"javascript:deleteRow2(&quot;"+a1701EntryId+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a1701EntryId){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
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
	$h.openPageModeWin('selectzgxlxw','pages.publicServantManage.SelectZGXLXW','ѡ�����ѧ��ѧλ',580,180,a0000,'<%=request.getContextPath()%>',null,null,null);
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
	if(!wincfg||(wincfg&&!wincfg.maximized)){//�������
		newWin_.setWidth(width);
		newWin_.setHeight(height);
		newWin_.setPosition(wleft,wtop);
	}
	return newWin_;

}




</script>

<div id="cover_wrap1"></div>
