<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<body>

<div id="border">
<table cellspacing="2" width="960" align="center" style="width: 100%">
	<tr><div style="height: 20px"></div></tr>
	<tr>
		<td>
			<table>
				<tr height="35px">
					<td>
						<odin:textEdit property="a3601" label="��Ա����" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr height="35px">
					<td>
						<odin:select2 property="a3604a" label="��Ա��ν" codeType="GB4761"></odin:select2>
					</td>
				</tr>
				<tr height="35px">
					<td>
						<odin:NewDateEditTag property="a3607" isCheck="true" label="��������" maxlength="8" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr height="35px">
					<td>
						<odin:select2 property="a3627" label="������ò" codeType="GB4762"></odin:select2>
					</td>
				</tr>
				<tr height="35px">
					<td>
						<odin:textEdit property="a3611" label="������λ��ְ��" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr height="35px">
					<td>
						<tags:PublicTextIconEdit property="a3645" codetype="ZB09" width="160" label="��Աְ����" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left">
						<input type="checkbox" id="a3617" onclick="Seta3617()"/>
						<label id="A3617SpanId" style="font-size: 12px;">�Ƿ��ȡ����������̿���/��Ա���� </label>
					</td>
				</tr>
				<!-- <tr>
					<td align="left">
						<input type="checkbox" id="a3617" onclick="Seta3617()"/>
						<label id="A3617SpanId" style="font-size: 12px;">�Ƿ��ȡ����������̿���/��Ա���� </label>
					</td>
				</tr> -->
			</table>
		</td>
		<td>
			<div style="width:70px"></div>
		</td>
		<td>
		<div style="width:600px">
			<odin:grid property="FamilyInfoGrid" width="600" sm="row"  isFirstLoadData="false" url="/"
				 height="260" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a3600" />
			  		<odin:gridDataCol name="a3601" />
			  		<odin:gridDataCol name="a3604a"/>
			  		<odin:gridDataCol name="a3607" />
			  		<odin:gridDataCol name="a3627" />
			   		<odin:gridDataCol name="a3611"/>
			   		<odin:gridDataCol name="a3645" isLast="true"/>
			   		<%-- <odin:gridDataCol name="A3617" isLast="true"/>	 --%>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn2 header="��Ա����" dataIndex="a3601" editor="text" edited="false" width="70"/>
				  <odin:gridColumn header="id" dataIndex="a3600" editor="text" hidden="true"/>
				  <odin:gridEditColumn2 header="��Ա��ν" align="center" dataIndex="a3604a" editor="select" edited="false" codeType="GB4761" width="70"/>
				  <odin:gridEditColumn2 header="��������" align="center" dataIndex="a3607" editor="text" edited="false" width="70"/>
				  <odin:gridEditColumn2 header="������ò" align="center" dataIndex="a3627" editor="select" width="70" edited="false" codeType="GB4762"/>
				  <odin:gridEditColumn2 header="������λ��ְ��" align="center" dataIndex="a3611" editor="text" edited="false" width="100"/>
			  	  <odin:gridEditColumn2 header="��Աְ����" align="center" dataIndex="a3645" editor="select" edited="false" width="100" codeType="ZB09"/>
			  	 <%-- <odin:gridEditColumn2 header="�Ƿ��ȡ����������̿���/��Ա����" align="center" dataIndex="A3617" editor="select" edited="false" width="150" codeType="XZ09"/> --%>
				  <odin:gridEditColumn2 width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>

</table>
</div>
<div id='btnAdd'>
<odin:button text="��&nbsp;&nbsp;��" handler="AddBtn"></odin:button>
</div>
<div id='btnSave'>
<odin:button text="��&nbsp;&nbsp;��" handler="save"></odin:button>
</div>
<div id='btnCancel'>
<odin:button text="ȡ&nbsp;&nbsp;��" handler="Cancel"></odin:button>
</div>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a3617check" title="�Ƿ��ȡ����������̿���/��Ա����" ></odin:hidden>
<odin:hidden property="a3600" title="����id" ></odin:hidden>
</body>
<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
Ext.onReady(function(){
	document.getElementById('a3617check').value=0;
});
function Seta3617(){
	if(document.getElementById('a3617').checked){
		document.getElementById('a3617check').value = 1;
	}else{
		document.getElementById('a3617check').value = 0;
	}
}
function AddBtn(){
	radow.doEvent('FamilyAddBtn.onclick');
}
function save(){
	document.getElementById("a0000").value = window.parent.frames["BaseAddPage_GB"].document.getElementById("a0000").value;
	radow.doEvent('save.onclick');
}
function changea3617check(value){
	if(value==0){
		document.getElementById("a3617").checked = false;
	}else{
		document.getElementById("a3617").checked = true;
	}

}
function Cancel(){
	alert("demo");
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a3600 = record.data.a3600;
	/* if(realParent.buttonDisabled){
		return "ɾ��";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+a3600+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a3600){
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) {
		if("yes"==id){
			radow.doEvent('deleteRow',a3600);
		}else{
			return;
		}
	});
}




function deleteRow(){
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		$h.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) {
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}
	});
}

</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
}

#toolBar8 {
	width: 750px !important;
}
#btnAdd{position: absolute;top:550px;left:400px;}
#btnSave{position: absolute;top:550px;left:450px;}
#btnCancel{position: absolute;top:550px;left:500px;}
</style>
