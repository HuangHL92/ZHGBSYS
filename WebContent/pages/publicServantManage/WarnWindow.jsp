<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript">

//页面右键禁用
document.oncontextmenu=rightMouse;
function rightMouse() {
	return false;
}


 function check(){
    document.getElementById('choose').style.visibility='hidden';
 }
 
 function check1(){
    document.getElementById('choose').style.visibility='hidden';
 }
 function msgWait(){
	 //realParent.closeWarnWin();
	 ShowCellCover('start','系统提示','发现部分人员的时间录入格式有误,正在帮您查找,请稍等...');
	 Ext.Ajax.request({
		 	method: 'POST',
		 	async: false,
		 	timeout: 300000,
		 	params: {},
		 	url: '<%=request.getContextPath()%>/MsgResponse.do?method=start',
		 	success: function(response, options){
		 		var result = Ext.util.JSON.decode(response.responseText);
				if(result){
					ShowCellCover("success","系统提示","上述(已帮您打开)人员的出生日期或参加工作时间格式有问题,请修改后再进行'事务提醒'操作！");
					realParent.openErrorPerson(result.data);
					//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson2',false,false)");
				}
		 	},
		 	failure : function(response, options){
		 		realParent.ShowCellCover('failure','系统提示','网络异常！');
			}
		});
 }
 
 function tabHidden(type){
	 for(var i = 1;i<=7;i++){
		 var id = 'tab__tab' + i;
		 document.getElementById(id).style.display = 'none';
	 }
	 document.getElementById("tab__"+type).style.display = '';
	 Ext.getCmp('tab').setActiveTab(type);
 }
 
 function revoke(){
	 var gridId = "persongrid6";
	 var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	 var a0000 = selections[0].data.a0000;
	 if(a0000){
		document.getElementById('gridNum').value = gridId;
		radow.doEvent("revokeWarn",a0000);
	 }
 }
 
 function revoke2(){
	 var gridId = "persongrid5";
	 var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	 var a0000 = selections[0].data.a0000;
	 if(a0000){
		document.getElementById('gridNum').value = gridId;
		radow.doEvent("revokeWarn",a0000);
	 }
 }
 
 function receive(){
	 var gridId = "persongrid4";
	 var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	 var a0000 = selections[0].data.a0000;
	 var a0101 = selections[0].data.a0101;
	 var param = a0000 + "," + a0101;
	 radow.util.openWindow('peopleReceive','pages.customquery.PeopleReceive&initParams='+param);
 }
 
 function revokeSuccess(){
	odin.alert("人员撤销调转成功！",function(){
		var gridId = document.getElementById('gridNum').value;
		Ext.getCmp(gridId).store.reload();
		realParent.getAffairJson();
	});
}
 
 function ShowCellCover(elementId, titles, msgs)
 {	
 	Ext.MessageBox.buttonText.ok = "关闭";
 	if(elementId.indexOf("start") != -1){
 	
 		Ext.MessageBox.show({
 			title:titles,
 			msg:msgs,
 			width:300,
 	        height:300,
 			closable:false,
 		//	buttons: Ext.MessageBox.OK,		
 			modal:true,
 			progress:true,
 			wait:true,
 			animEl: 'elId',
 			increment:5, 
 			waitConfig: {interval:150}
 			//,icon:Ext.MessageBox.INFO        
 		});
 	}else if(elementId.indexOf("success") != -1){
 			Ext.MessageBox.alert("系统提示", msgs, function(but) {  
 				realParent.closeWarnWin();
 			}); 
 	}else if(elementId.indexOf("failure") != -1){
 			Ext.MessageBox.show({
 				title:titles,
 				msg:msgs,
 				width:300,
 				modal:true,
 		        height:300,
 				closable:true,
 				//icon:Ext.MessageBox.INFO,
 				buttons: Ext.MessageBox.OK		
 			});
 			/*
 			setTimeout(function(){
 					Ext.MessageBox.hide();
 			}, 2000);
 			*/
 	}else {
 			Ext.MessageBox.show({
 				title:titles,
 				msg:msgs,
 				width:300,
 				modal:true,
 		        height:300,
 				closable:true,
 				//icon:Ext.MessageBox.INFO,
 				buttons: Ext.MessageBox.OK		
 			});
 		}
 }
 
 function changeType(){
	 radow.doEvent("type1");
 }
 
 <odin:menu property="revokeMenu">
 <odin:menuItem text="人员撤销调转" handler="revoke" isLast="true"></odin:menuItem>
 </odin:menu>
 
 <odin:menu property="revokeMenu2">
 <odin:menuItem text="人员撤销调转" handler="revoke2" isLast="true"></odin:menuItem>
 </odin:menu>
 
 <odin:menu property="receiveMenu">
 <odin:menuItem text="人员接收" handler="receive" isLast="true"></odin:menuItem>
 </odin:menu>
</script>

<odin:window src="/blank.htm" id="peopleReceive" width="450" height="280" title="人员接收" modal="true"></odin:window>

<odin:hidden property="tabName" value=""/>
<odin:hidden property="retireTime" value=""/>
<odin:hidden property="gridNum" value=""/>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="保存列表" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="简要提示" id="tab1"></odin:tabItem>
<odin:tabItem title="试用期到期提醒" id="tab2"></odin:tabItem>
<odin:tabItem title="退休人员提醒" id="tab3"></odin:tabItem>
<odin:tabItem title="生日提醒" id="tab4"></odin:tabItem>
<odin:tabItem title="待转入人员提醒" id="tab5"></odin:tabItem>
<odin:tabItem title="待转出人员提醒" id="tab6"></odin:tabItem>
<odin:tabItem title="退回人员提醒" id="tab7" isLast="true"></odin:tabItem>
</odin:tabModel>



<%-----------------------------------------简要提示----------------------------------------------------------%>
<odin:tabCont itemIndex="tab1">
<%-- <br>
<label style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;今日提醒:</label>
<br><br>
<table>
<tr>
<td width="100px"></td>
<td>
<odin:textEdit property="probation" label="试用期到期人员" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
</table>

<br>
<table>
<tr>
<td width="77px"></td>
<td>
<odin:textEdit property="retire" label="已超过退休时间人员" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
</table>

<br>
<table>
<tr>
<td width="100px"></td>
<td>
<odin:textEdit property="birthday" label="即将过生日人员" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
<tr>
	<td style="height: 165px;"></td>
</tr>
</table> --%>
</odin:tabCont>
<%----------------------------------------------试用期到期提醒------------------------------------------------------------%>
<odin:tabCont itemIndex="tab2">
<br>
<% int dayCount = WarnWindowPageModel.getSyDayCount(); %>
<label id="pro" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;下列人员将在<%=dayCount%>天内到期或已到期 </label>
<br><br>
<odin:editgrid property="persongrid1"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="355">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

</odin:tabCont>
<%----------------------------------------------退休人员提醒------------------------------------------------------------%>
<odin:tabCont itemIndex="tab3">
<br>
<table>
<tr>
<td width="10"></td>
<td><odin:select property="type1" data="['3','本月退休'],['2','下月退休'],['1','一年内退休'],['0','已超过退休时间']" value="0" onchange="changeType()"></odin:select></td>
</tr>
</table>
<odin:editgrid property="persongrid2"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="355">
				<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------生日提醒------------------------------------------------------------%>
<odin:tabCont itemIndex="tab4">
<br>
<% int birthdayCount = WarnWindowPageModel.getBirthDaycount(); %>
<label id="birth" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;下列人员将在<%=birthdayCount%>天内过生日</label>
<br><br>
<odin:editgrid property="persongrid3"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="350">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------待转入人员------------------------------------------------------------%>
<odin:tabCont itemIndex="tab5">
<br>
<label id="getIn" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;请尽快处理下列待转入的人员（选择某一人员右键，可进行"人员接收"）</label>
<br><br>
<odin:editgrid property="persongrid4" hasRightMenu="true" rightMenuId="receiveMenu"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="350">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------待转出人员------------------------------------------------------------%>
<odin:tabCont itemIndex="tab6">
<br>
<label id="getOut" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;下列是您操作的待转出人员（选择某一人员右键，可进行"人员调转撤销"）</label>
<br><br>
<odin:editgrid property="persongrid5" hasRightMenu="true" rightMenuId="revokeMenu2"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="350">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------退回人员------------------------------------------------------------%>
<odin:tabCont itemIndex="tab7">
<br>
<label id="back" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;由于对方用户未及时接收人员，导致退回。（选择某一人员右键，可进行"人员调转撤销"）</label>
<br><br>
<odin:editgrid property="persongrid6" hasRightMenu="true" rightMenuId="revokeMenu"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="350">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="姓名" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="性别" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>
<%-- <table>
<tr>
<td width="370px"></td>
<td>
<odin:button text="提醒设置" property="setBtn"></odin:button>
</td>
<td><div id="choose" style="visibility: hidden">
<odin:button text="查看" property="viewBtn"></odin:button></div>
</td>
<td>
<odin:button text="关闭" property="closeBtn"></odin:button>
</td>
</tr>
</table> --%>
<odin:window src="/blank.htm"  id="setWarnWin" width="450" height="350" title="提醒设置" modal="true"/>
