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
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="TrainAddBtn" isLast="true" icon="images/add.gif"></odin:buttonForToolBar>
</odin:toolBar> --%>
<!-- <table class="dasda" style="width:100%;margin-top: 50px;">
	<tr> -->
		<%-- <odin:select2 property="a1101" label="��ѵ���" codeType="ZB29"></odin:select2> --%>
<%-- 		<odin:select2 property="g11021" label="��ѵ���" data="['1','���ް�'],['2','��ѵ��'],['3A','ר���'],['4','����'],['5','���۰�'],['9','����']"></odin:select2>
		<odin:textEdit property="g11020" label="���" ></odin:textEdit> --%>
<%-- 		<odin:textEdit property="a1131" label="��ѵ������"></odin:textEdit>
		<odin:select2 property="a1104" label="��ѵ���״̬" codeType="ZB30"></odin:select2>
		
		
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11022" label="��ѵ�ص�" ></odin:textEdit>
		<odin:textEdit property="a1114" label="��ѵ���쵥λ" ></odin:textEdit>
		<odin:textEdit property="g11006" label="���ڼ�¼"></odin:textEdit>
	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11003" label="��ѵѧ��"></odin:textEdit>
		<odin:NewDateEditTag property="a1107" label="��ѵ��ʼ����" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" label="��ѵ��������" maxlength="8"></odin:NewDateEditTag> --%>
		<%-- <odin:numberEdit property="a1107c" label="��ѵʱ��(��)"></odin:numberEdit>
		<odin:select2 property="g02003" label="��ѵ�������" codeType="AL01"></odin:select2>
		<odin:select2 property="a1151" label="��������������ѵ��ʶ " codeType="XZ09"></odin:select2> --%>
<%-- 	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:select2 property="g11023" label="�Ƿ�����ѧԱ" codeType="XZ09"></odin:select2>
		<odin:textEdit property="g11024" label="��ѵС��"></odin:textEdit>
		<odin:numberEdit property="a1108" label="ѧʱ"></odin:numberEdit> --%>
		<%-- <odin:textEdit property="a1121a" label="��ѵ��������"></odin:textEdit>
		<odin:select2 property="a1127" label="��ѵ�������" codeType="ZB27"></odin:select2> --%>
<%-- 	</tr>
	<tr>
		<td colspan="6"><div style="height: 5px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="g11025" label="���Գɼ�"></odin:textEdit> --%>
		<%-- <odin:textEdit property="a1108a" label="��ѵ�а쵥λ"></odin:textEdit>
		<odin:select2 property="a1108b" label="�а쵥λ���" codeType="ZB144"></odin:select2>
		<odin:textEdit property="g11010" label="����ѧϰ�γ�"></odin:textEdit> --%>
<!-- 	</tr> -->
	<%-- <tr>
		
		<odin:numberEdit property="g11004" label="�ϼ���ѵ����"></odin:numberEdit>
		
	</tr>
	<tr>
		<odin:select2 property="g11007" label="��ѵ�������" codeType="AL01"></odin:select2>
		<odin:numberEdit property="g11011" label="����ѧϰ��ѧʱ"></odin:numberEdit>
		<odin:textEdit property="g11008" label="��ѵС�ᡢ���б���"></odin:textEdit>
	</tr>
	<tr>
		
		<odin:select2 property="g11009" label="������޿�������" codeType="AL02"></odin:select2>
		<odin:textEdit property="g11005" label="����ְ��"></odin:textEdit>
		<odin:numberEdit property="g11015" label="ѧ���÷����Գɼ�"></odin:numberEdit>
	</tr>
	<tr>
		<odin:numberEdit property="g11013" label="�����й��쵼�ɲ�����֪ʶ���Գɼ�" ></odin:numberEdit>
		<odin:NewDateEditTag property="g11012" label="�����й��쵼�ɲ�����֪ʶ����ʱ��" maxlength="8" ></odin:NewDateEditTag>
		<odin:numberEdit property="g11014" label="���ڷ��濼�Գɼ�"></odin:numberEdit>
	</tr>
	<tr>
		<!-- <td><button onclick=" location=location ">ˢ��</button></td> -->
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
<%-- 					  <odin:gridEditColumn2 width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" />		 --%>			  
					  <odin:gridEditColumn2 header="����" dataIndex="a1100" editor="text" edited="false" hidden="true"/>
<!-- 					  selectData="['1','���ް�'],['2','��ѵ��'],['3','ר���'],['4','����']" -->
<%-- 					  <odin:gridEditColumn2 header="��ѵ���" align="center" dataIndex="a1101" editor="select" edited="false" codeType="ZB29" width="120"/> --%>
 					  <odin:gridEditColumn2 header="��ѵ���" align="center"  dataIndex="year" editor="text" edited="false" width="60"/>	
 					  <odin:gridEditColumn2 header="��ѵ������" align="center"  dataIndex="a1131" editor="text" edited="false" width="240"/>	
					  <odin:gridEditColumn2 header="��ѵ���쵥λ" align="center" dataIndex="a1114" editor="text" edited="false" width="120"/>
					 <odin:gridEditColumn2 header="��ѵ�ص�" align="center" dataIndex="address" editor="text" edited="false" width="150"/>
					  <odin:gridEditColumn2 header="��ѵ��ʼ����" align="center" dataIndex="a1107" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="ѧʱ" align="center" dataIndex="a1108" editor="text" edited="false" width="40" isLast="true"/>
					
					 
					</odin:gridColumnModel>
			</odin:grid>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a1100" title="����id" ></odin:hidden>	
</body>
<script type="text/javascript">


var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A11",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A11")%>;
Ext.onReady(function(){
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(fieldsDisabled); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
});
function inita1151(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<span>��</span>";
	}else if(value==1){
		return "<span>��</span>";
	}else if(value==undefined||value==null||value==''){
		return "<span></span>";
	}else{
		return "<span>�쳣</span>";
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
		return "ɾ��";
	} 
	return "<a href=\"javascript:deleteRow2(&quot;"+a1100+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a1100){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
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
