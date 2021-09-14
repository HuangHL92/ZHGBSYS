<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="commform/basejs/json2.js"></script>


<style>
#TitleContent textarea{
	width:280px;
}
</style>
<odin:hidden property="b0111"/>
<odin:hidden property="b01id"/>
<odin:hidden property="qpid"/>
<odin:hidden property="flag" value="1"/>

<script type="text/javascript">
function init(){//��ʼ������
	var b0111=parentParam.b0111;
	document.getElementById("b0111").value=b0111;
	document.getElementById("zongshu").value=parentParam.b0236;
	document.getElementById("b0104").value=parentParam.b0104;
	updateb01id(b0111);
}

function updateb01id(b0111){
	radow.doEvent('updateb01id',b0111);
}

function deleteGW(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deleteGW2('"+record.get("qpid")+"');\">ɾ��</a></font>";
}

function deleteGW2(qpid){
	radow.doEvent('deleteGW',qpid);
}

</script>
<div  align="center" id="TitleContent">
<table>
	<tr>
		<odin:textEdit property="b0104" label="��������" width="320" readonly="true"></odin:textEdit>
		<td rowspan="3" colspan="4" width="800">
			<odin:groupBox property="group2" title="��λ����" >
				<odin:editgrid2 property="allGrid" hasRightMenu="false" bbarId="pageToolBar" height="310" title="" forceNoScroll="true" autoFill="true" pageSize="100"  url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="gwname"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="bzgw"/>
						<odin:gridDataCol name="sortid"/>
						<odin:gridDataCol name="type"/>
						<odin:gridDataCol name="id"/>
						<odin:gridDataCol name="gwmc" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="gwname" width="150" header="��λ����" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0101" width="60" header="����" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="gwmc" width="70" menuDisabled="true" sortable="false" header="�ص��λ" editor="select" edited="true"  editorId="asd17"   align="center"  />
						<odin:gridEditColumn2 dataIndex="bzgw" width="150" header="���Ӹ�λ" editor="selectTree" edited="true" editorId="aaa" codeType="KZ01"  align="center" />
						<odin:gridEditColumn2 dataIndex="sortid" width="40" header="����" editor="text" edited="false"  align="center" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
			</odin:groupBox>
		</td>
	</tr>
	<tr>
		<odin:textEdit property="zongshu" label="ȱ������" width="320" ></odin:textEdit>
	</tr>
	<tr>
		<td colspan='2'>
		<odin:groupBox property="group1" title="ȱ���λ��Ϣ" >
			<table style="width: 360;height: 210">
				<tr>
					<odin:textEdit property="gwname" label="ȱ���λ����" width="240"></odin:textEdit>
				</tr>
				<tr>
					<odin:select2 property="gwzf" label="��Ա���" data="['1', '��ְ'],['3', '��ְ']" width="240"></odin:select2>
				</tr>
				<tr>
					<td colspan="2">
						<odin:editgrid2 property="GWGrid" hasRightMenu="false" bbarId="pageToolBar" height="210" title="" forceNoScroll="true" autoFill="true" pageSize="20"  url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="gwname"/>
								<odin:gridDataCol name="gwzf"/>
								<odin:gridDataCol name="sortid"/>
								<odin:gridDataCol name="qpid"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn2 dataIndex="gwname" width="150" header="ȱ���λ����" editor="text" edited="false" align="center" />
								<odin:gridEditColumn2 dataIndex="gwzf" width="80" header="��Ա���" editor="text"  edited="false" align="center"/>
								<odin:gridEditColumn2 dataIndex="sortid" width="50" header="����" editor="text" edited="false" hidden="true" align="center"/>
								<odin:gridEditColumn2 dataIndex="delcount" width="50" header="����" editor="text" edited="false" align="center" isLast="true" renderer="deleteGW"/>
							</odin:gridColumnModel>
						</odin:editgrid2>
					</td>
				</tr>
			</table>
			</odin:groupBox>
		</td>
	</tr>
	<tr>
		<odin:textarea label="�˶�ְ��˵��" property="b0270"  rows="4"></odin:textarea>
		<odin:textarea label="ʵ���䱸���" property="b0271"  rows="4"></odin:textarea>
		<odin:textarea label="����ʡֱ��λ���" property="b0272"  rows="4"></odin:textarea>
	</tr>
	<tr>
		<odin:textarea label="��ע" property="b0140"   rows="4"></odin:textarea>
		<odin:textarea label="��ȱ��λ" property="b0273"  rows="4"></odin:textarea>
		<odin:textarea label="�������ϣ���˳��쵼��λ" property="b0274"  rows="4"></odin:textarea>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar text="����ȱ������" id="updateZS" icon="images/icon/xxwh.png" handler="updateZS"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����ȱ���λ" id="addGWBtn" icon="images/add.gif" handler="addGW"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����ȱ���λ" id="save"  icon="images/save.gif" handler="saveGW" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="���汸ע" id="savebz"  icon="images/save.gif" handler="saveBZ"  isLast="true"></odin:buttonForToolBar>
	<%-- <odin:buttonForToolBar text="������������" id="updateZS" isLast="true" icon="images/save.gif" ></odin:buttonForToolBar>--%>
</odin:toolBar>
<odin:panel contentEl="TitleContent" property="TitlePanel" topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
function updateZS(){
	radow.doEvent('updateZS');
}

function addGW(){
	document.getElementById("gwname").value="";
	document.getElementById("gwzf").value="";
	document.getElementById("gwzf_combo").value="";
	document.getElementById("flag").value="1";
	document.getElementById('qpid').value='';
}

function saveGW(){
	var gwname = document.getElementById('gwname').value;	
	var gwzf = document.getElementById('gwzf').value;
	if(gwname==""){
		Ext.Msg.alert("ϵͳ��ʾ","ȱ���λ���Ʋ���Ϊ�գ�");
		return;
	}
	
	if(gwzf==""){
		Ext.Msg.alert("ϵͳ��ʾ","��Ա�����Ϊ�գ�");
		return;
	}
	//radow.doEvent('save','1');
	if (confirm("ȷ������ȱ��������")) {
		radow.doEvent('save','1');
	} else {
		radow.doEvent('save','2');
	}
}

function saveBZ(){
	radow.doEvent('saveBZ');
}

Ext.onReady(function(){
	$h.initGridSort('GWGrid',function(g){
	    radow.doEvent('rolesort');
	  });
	$h.initGridSort('allGrid',function(g){
	    radow.doEvent('allgwsort');
	  });
}); 

</script>
</html>