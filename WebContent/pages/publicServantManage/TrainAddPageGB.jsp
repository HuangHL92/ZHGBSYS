<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}
.dasda td{width: 120px;padding-bottom: 5px;}
</style>
<%-- <odin:toolBar property="toolBar8">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增" id="TrainAddBtn" isLast="true" icon="images/add.gif"></odin:buttonForToolBar>
</odin:toolBar> --%>
<!-- <table class="dasda" style="width:100%;margin-top: 50px;">
	<tr> -->
		<%-- <odin:select2 property="a1101" label="培训类别" codeType="ZB29"></odin:select2> --%>
<%-- 		<odin:select2 property="g11021" label="培训类别" data="['1','研修班'],['2','培训班'],['3A','专题班'],['4','讲座'],['5','理论班'],['9','其他']"></odin:select2>
		<odin:textEdit property="g11020" label="年度" ></odin:textEdit> --%>
<%-- 		<odin:textEdit property="a1131" label="培训班名称"></odin:textEdit>
		<odin:select2 property="a1104" label="培训离岗状态" codeType="ZB30"></odin:select2>
		
		
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11022" label="培训地点" ></odin:textEdit>
		<odin:textEdit property="a1114" label="培训主办单位" ></odin:textEdit>
		<odin:textEdit property="g11006" label="考勤记录"></odin:textEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11003" label="培训学制"></odin:textEdit>
		<odin:NewDateEditTag property="a1107" label="培训起始日期" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" label="培训结束日期" maxlength="8"></odin:NewDateEditTag> --%>
		<%-- <odin:numberEdit property="a1107c" label="培训时长(天)"></odin:numberEdit>
		<odin:select2 property="g02003" label="培训结果评价" codeType="AL01"></odin:select2>
		<odin:select2 property="a1151" label="出国（出境）培训标识 " codeType="XZ09"></odin:select2> --%>
<%-- 	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="g11023" label="是否优秀学员" codeType="XZ09"></odin:select2>
		<odin:textEdit property="g11024" label="培训小结"></odin:textEdit>
		<odin:numberEdit property="a1108" label="学时"></odin:numberEdit> --%>
		<%-- <odin:textEdit property="a1121a" label="培训机构名称"></odin:textEdit>
		<odin:select2 property="a1127" label="培训机构类别" codeType="ZB27"></odin:select2> --%>
<%-- 	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11025" label="考试成绩"></odin:textEdit> --%>
		<%-- <odin:textEdit property="a1108a" label="培训承办单位"></odin:textEdit>
		<odin:select2 property="a1108b" label="承办单位类别" codeType="ZB144"></odin:select2>
		<odin:textEdit property="g11010" label="网络学习课程"></odin:textEdit> --%>
<!-- 	</tr> -->
	<%-- <tr>
		
		<odin:numberEdit property="g11004" label="合计培训天数"></odin:numberEdit>
		
	</tr>
	<tr>
		<odin:select2 property="g11007" label="培训奖惩情况" codeType="AL01"></odin:select2>
		<odin:numberEdit property="g11011" label="网络学习总学时"></odin:numberEdit>
		<odin:textEdit property="g11008" label="培训小结、调研报告"></odin:textEdit>
	</tr>
	<tr>
		
		<odin:select2 property="g11009" label="网络必修课完成情况" codeType="AL02"></odin:select2>
		<odin:textEdit property="g11005" label="班内职务"></odin:textEdit>
		<odin:numberEdit property="g11015" label="学法用法考试成绩"></odin:numberEdit>
	</tr>
	<tr>
		<odin:numberEdit property="g11013" label="新任市管领导干部法律知识考试成绩" ></odin:numberEdit>
		<odin:NewDateEditTag property="g11012" label="新任市管领导干部法律知识考试时间" maxlength="8" ></odin:NewDateEditTag>
		<odin:numberEdit property="g11014" label="党内法规考试成绩"></odin:numberEdit>
	</tr>
	<tr>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr> --%>
<!-- </table> -->

	<odin:grid property="TrainInfoGrid" sm="row" isFirstLoadData="false" url="/" topBarId="toolBar8"
				height="620" autoFill="false"  >
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<%-- 						<odin:gridDataCol name="delete" /> --%>
						<odin:gridDataCol name="a1100" />
<%-- 						<odin:gridDataCol name="a1101" /> --%>
						<odin:gridDataCol name="year" />
						<odin:gridDataCol name="a1131" />
						<odin:gridDataCol name="a1114" />
						<odin:gridDataCol name="address" />
						<odin:gridDataCol name="a1107" />
						<odin:gridDataCol name="a1108" isLast="true"/>

								  		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
<%-- 					  <odin:gridEditColumn2 width="45" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" />		 --%>			  
					  <odin:gridEditColumn2 header="主键" dataIndex="a1100" editor="text" edited="false" hidden="true"/>
<!-- 					  selectData="['1','研修班'],['2','培训班'],['3','专题班'],['4','讲座']" -->
<%-- 					  <odin:gridEditColumn2 header="培训类别" align="center" dataIndex="a1101" editor="select" edited="false" codeType="ZB29" width="120"/> --%>
 					  <odin:gridEditColumn2 header="培训年度" align="center"  dataIndex="year" editor="text" edited="false" width="60"/>	
 					  <odin:gridEditColumn2 header="培训班名称" align="center"  dataIndex="a1131" editor="text" edited="false" width="240"/>	
					  <odin:gridEditColumn2 header="培训主办单位" align="center" dataIndex="a1114" editor="text" edited="false" width="120"/>
					 <odin:gridEditColumn2 header="培训地点" align="center" dataIndex="address" editor="text" edited="false" width="150"/>
					  <odin:gridEditColumn2 header="培训起始日期" align="center" dataIndex="a1107" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="学时" align="center" dataIndex="a1108" editor="text" edited="false" width="40" isLast="true"/>
					
					 
					</odin:gridColumnModel>
			</odin:grid>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="a1100" title="主键id" ></odin:hidden>	
</body>
<script type="text/javascript">


var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A11",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A11")%>;
Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
});
function inita1151(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<span>否</span>";
	}else if(value==1){
		return "<span>是</span>";
	}else if(value==undefined||value==null||value==''){
		return "<span></span>";
	}else{
		return "<span>异常</span>";
	}
}
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function AddBtn(){
	radow.doEvent('TrainAddBtn.onclick');
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a1100 = record.data.a1100;
	 if(parent.buttonDisabled){
		return "删除";
	} 
	return "<a href=\"javascript:deleteRow2(&quot;"+a1100+"&quot;)\">删除</a>";
}
function deleteRow2(a1100){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1100);
		}else{
			return;
		}		
	});	
}
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function lockINFO(){
	Ext.getCmp("save").disable(); 
	Ext.getCmp("TrainAddBtn").disable(); 
	Ext.getCmp("TrainInfoGrid").getColumnModel().setHidden(1,true); 
}

</script>
