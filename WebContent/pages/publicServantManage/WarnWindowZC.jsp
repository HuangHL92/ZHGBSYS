<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">

//页面右键禁用
document.oncontextmenu=rightMouse;
function rightMouse() {
	return false;
}
 
<odin:menu property="revokeMenu2">
<odin:menuItem text="人员撤销调转" handler="revoke2" isLast="true"></odin:menuItem>
</odin:menu>

function revoke2(){
	 var gridId = "persongrid1";
	 var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	 var a0000 = selections[0].data.a0000;
	 if(a0000){
		radow.doEvent("revokeWarn",a0000);
	 }
}

function revokeSuccess(){
	odin.alert("人员撤销调转成功！",function(){
		var gridId = "persongrid1";
		Ext.getCmp(gridId).store.reload();
		realParent.getAffairJson();
	});
}
function GB2261(value,params,record,rowIndex,colIndex,ds){
	if(value == '1'){
		value = '男';
	}
	if(value == '2'){
		value = '女';
	}
	return value;
}
</script>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="保存列表" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="待转出人员" isLast="true" id="tab6"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex="tab6">
<br>
<label id="getOut" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;下列是您操作的待转出人员（选择某一人员右键，可进行"人员调转撤销"）</label>
<br><br>
<odin:editgrid property="persongrid1" hasRightMenu="true" rightMenuId="revokeMenu2"
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
					
					<odin:gridEditColumn header="姓名" edited="false" width="80" dataIndex="a0101" editor="text" align="center"/>
					
					<odin:gridEditColumn header="性别" edited="false" width="80" dataIndex="a0104" editor="text" renderer="GB2261" align="center" />
					
					<odin:gridEditColumn header="出生日期" edited="false" width="80" dataIndex="a0107" editor="text" align="center"/>
					
					<odin:gridEditColumn header="工作单位及职务" edited="false" width="410" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>


