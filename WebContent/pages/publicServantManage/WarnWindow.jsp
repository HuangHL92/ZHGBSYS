<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript">

//ҳ���Ҽ�����
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
	 ShowCellCover('start','ϵͳ��ʾ','���ֲ�����Ա��ʱ��¼���ʽ����,���ڰ�������,���Ե�...');
	 Ext.Ajax.request({
		 	method: 'POST',
		 	async: false,
		 	timeout: 300000,
		 	params: {},
		 	url: '<%=request.getContextPath()%>/MsgResponse.do?method=start',
		 	success: function(response, options){
		 		var result = Ext.util.JSON.decode(response.responseText);
				if(result){
					ShowCellCover("success","ϵͳ��ʾ","����(�Ѱ�����)��Ա�ĳ������ڻ�μӹ���ʱ���ʽ������,���޸ĺ��ٽ���'��������'������");
					realParent.openErrorPerson(result.data);
					//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson2',false,false)");
				}
		 	},
		 	failure : function(response, options){
		 		realParent.ShowCellCover('failure','ϵͳ��ʾ','�����쳣��');
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
	odin.alert("��Ա������ת�ɹ���",function(){
		var gridId = document.getElementById('gridNum').value;
		Ext.getCmp(gridId).store.reload();
		realParent.getAffairJson();
	});
}
 
 function ShowCellCover(elementId, titles, msgs)
 {	
 	Ext.MessageBox.buttonText.ok = "�ر�";
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
 			Ext.MessageBox.alert("ϵͳ��ʾ", msgs, function(but) {  
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
 <odin:menuItem text="��Ա������ת" handler="revoke" isLast="true"></odin:menuItem>
 </odin:menu>
 
 <odin:menu property="revokeMenu2">
 <odin:menuItem text="��Ա������ת" handler="revoke2" isLast="true"></odin:menuItem>
 </odin:menu>
 
 <odin:menu property="receiveMenu">
 <odin:menuItem text="��Ա����" handler="receive" isLast="true"></odin:menuItem>
 </odin:menu>
</script>

<odin:window src="/blank.htm" id="peopleReceive" width="450" height="280" title="��Ա����" modal="true"></odin:window>

<odin:hidden property="tabName" value=""/>
<odin:hidden property="retireTime" value=""/>
<odin:hidden property="gridNum" value=""/>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="�����б�" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="��Ҫ��ʾ" id="tab1"></odin:tabItem>
<odin:tabItem title="�����ڵ�������" id="tab2"></odin:tabItem>
<odin:tabItem title="������Ա����" id="tab3"></odin:tabItem>
<odin:tabItem title="��������" id="tab4"></odin:tabItem>
<odin:tabItem title="��ת����Ա����" id="tab5"></odin:tabItem>
<odin:tabItem title="��ת����Ա����" id="tab6"></odin:tabItem>
<odin:tabItem title="�˻���Ա����" id="tab7" isLast="true"></odin:tabItem>
</odin:tabModel>



<%-----------------------------------------��Ҫ��ʾ----------------------------------------------------------%>
<odin:tabCont itemIndex="tab1">
<%-- <br>
<label style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������:</label>
<br><br>
<table>
<tr>
<td width="100px"></td>
<td>
<odin:textEdit property="probation" label="�����ڵ�����Ա" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
</table>

<br>
<table>
<tr>
<td width="77px"></td>
<td>
<odin:textEdit property="retire" label="�ѳ�������ʱ����Ա" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
</table>

<br>
<table>
<tr>
<td width="100px"></td>
<td>
<odin:textEdit property="birthday" label="������������Ա" size="6" disabled="true"></odin:textEdit>
</td>
</tr>
<tr>
	<td style="height: 165px;"></td>
</tr>
</table> --%>
</odin:tabCont>
<%----------------------------------------------�����ڵ�������------------------------------------------------------------%>
<odin:tabCont itemIndex="tab2">
<br>
<% int dayCount = WarnWindowPageModel.getSyDayCount(); %>
<label id="pro" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;������Ա����<%=dayCount%>���ڵ��ڻ��ѵ��� </label>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

</odin:tabCont>
<%----------------------------------------------������Ա����------------------------------------------------------------%>
<odin:tabCont itemIndex="tab3">
<br>
<table>
<tr>
<td width="10"></td>
<td><odin:select property="type1" data="['3','��������'],['2','��������'],['1','һ��������'],['0','�ѳ�������ʱ��']" value="0" onchange="changeType()"></odin:select></td>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------��������------------------------------------------------------------%>
<odin:tabCont itemIndex="tab4">
<br>
<% int birthdayCount = WarnWindowPageModel.getBirthDaycount(); %>
<label id="birth" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;������Ա����<%=birthdayCount%>���ڹ�����</label>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------��ת����Ա------------------------------------------------------------%>
<odin:tabCont itemIndex="tab5">
<br>
<label id="getIn" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;�뾡�촦�����д�ת�����Ա��ѡ��ĳһ��Ա�Ҽ����ɽ���"��Ա����"��</label>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------��ת����Ա------------------------------------------------------------%>
<odin:tabCont itemIndex="tab6">
<br>
<label id="getOut" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;�������������Ĵ�ת����Ա��ѡ��ĳһ��Ա�Ҽ����ɽ���"��Ա��ת����"��</label>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
<%----------------------------------------------�˻���Ա------------------------------------------------------------%>
<odin:tabCont itemIndex="tab7">
<br>
<label id="back" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;���ڶԷ��û�δ��ʱ������Ա�������˻ء���ѡ��ĳһ��Ա�Ҽ����ɽ���"��Ա��ת����"��</label>
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
					
					<odin:gridEditColumn header="����" edited="false" width="110" dataIndex="a0101" editor="text" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="110" dataIndex="a0104" editor="select" codeType="GB2261" />
					
					<odin:gridEditColumn header="��������" edited="false" width="110" dataIndex="a0107" editor="text" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="320" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>
<%-- <table>
<tr>
<td width="370px"></td>
<td>
<odin:button text="��������" property="setBtn"></odin:button>
</td>
<td><div id="choose" style="visibility: hidden">
<odin:button text="�鿴" property="viewBtn"></odin:button></div>
</td>
<td>
<odin:button text="�ر�" property="closeBtn"></odin:button>
</td>
</tr>
</table> --%>
<odin:window src="/blank.htm"  id="setWarnWin" width="450" height="350" title="��������" modal="true"/>
