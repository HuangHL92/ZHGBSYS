<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
.div-inline {
	float: left;
	margin-left: 15;
}
</style>
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="savebtn" handler="savebtnhand" text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="addbtn"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="deletebtn" text="ɾ��"  icon="images/icon/click.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<!-- �����ǰGrid�к� -->
<odin:hidden property="id"/>
<odin:hidden property="cueRowIndex"/>
<odin:hidden property="is_select_para"/>
<div class="div-inline" style="height: 565;width: 270">
	<odin:groupBox title="У�˺�������" >
					<td >
						<odin:grid property="functionList"  height="565" rowClick="clickTable"  url="/">
							<odin:gridJsonDataModel root="data">
								<odin:gridDataCol name="id" />
								<odin:gridDataCol name="describe" />
								<odin:gridDataCol name="checkname" isLast="true" />
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn header="����" dataIndex="id" edited="false" editor="text" align="left" hidden="true" />
								<odin:gridEditColumn header="��������" dataIndex="describe" edited="false" editor="text" align="left" hidden="true" />
								<odin:gridEditColumn header="У�˺�������" dataIndex="checkname" edited="false" editor="text" align="left" isLast="true" />
							</odin:gridColumnModel>
						</odin:grid>
					</td>
	</odin:groupBox>
</div>
<div class="div-inline" style="height: 600;width: 68%">
	<odin:groupBox title="У�˺�������">
		<table style="width:100%" >
			<tr>
				<odin:textEdit property="checkname" label="У�麯������" colspan="1"  maxlength="98" size="98"/>	 
			</tr>
			<tr>
				<odin:textarea property="describe" label="������������" colspan="4" rows="6" cols="99"/>
			</tr>
		</table>
	</odin:groupBox>
		<odin:groupBox title="���������ֶ�¼��">
			<table style="width:100%" >
				<tr>
					<odin:numberEdit property="num" label="������ţ�K��" colspan="2"  maxlength="34" size="34" minValue="1" onblur="onchangeNum(this)"/>	
					<odin:textEdit property="paradescribe" label="����������L��" colspan="2"  maxlength="34" size="34"/>	 
				</tr>
				<tr>
					<odin:select property="type" label="��������" editor="false" colspan="2" codeType="TC01" maxlength="31" size="31"/>	 
					<td style="padding-left:89" colspan="2"><odin:checkbox property="is_select" label="����Ϊ��ѡ����" /></td>	
				</tr>
			</table>
		</odin:groupBox>
		
		<odin:groupBox title="���������б�">
			<odin:toolBar property="toolBar2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="savecond" handler="saveconhandler" icon="images/icon/save.gif" ></odin:buttonForToolBar>
<%-- 				<odin:buttonForToolBar text="����" id="add" icon="images/icon/save.gif" ></odin:buttonForToolBar>
			<odin:buttonForToolBar text="����" id="upRow" icon="images/icon/arrowup.gif" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="downRow" icon="images/icon/arrowdown.gif"  ></odin:buttonForToolBar> --%>
				<odin:buttonForToolBar text="ɾ��" handler="delRow"  icon="images/icon/delete.gif" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
		
			<td colspan="1" style="height: 600;width: 300;">
					<odin:grid property="grid" autoFill="true" topBarId="toolBar2" rowClick="clickPara" height="270" sm="row" isFirstLoadData="false" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel root="data">
									<odin:gridDataCol name="num" />
									<odin:gridDataCol name="paradescribe" />
									<odin:gridDataCol name="type" />
									<odin:gridDataCol name="is_select" isLast="true"/>
								</odin:gridJsonDataModel>
							<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn dataIndex="num" header="�������" edited="false" align="center" width="35" editor="text"  />
									<odin:gridEditColumn dataIndex="paradescribe" header="��������"  edited="false"  align="center" width="60" editor="text"   sortable="false" />
									<odin:gridEditColumn dataIndex="type" header="��������"  edited="false"  align="center"  width="35" editor="text"  sortable="false"/>
									<odin:gridEditColumn dataIndex="is_select" header="��ѡ��"  selectData="['0', '��'],['1', '��']"  edited="false" editor="select" align="center"  sortable="false" isLast="true"/>
							</odin:gridColumnModel>
						</odin:grid>
					</td>
	</odin:groupBox>
</div>

<script>
var j= jQuery.noConflict(); 
//ɾ��һ�в���
function delRow(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);
	}	
	grid.view.refresh();
	alert("ɾ���ɹ���");
//	radow.doEvent('delCount');
}

//�������������ʾֵ
function clickTable(grid,rowIndex,colIndex,event){
	var id=grid.store.getAt(rowIndex).get("id");
   	var describe=grid.store.getAt(rowIndex).get("describe");
   	var checkname=grid.store.getAt(rowIndex).get("checkname");
	j("#id").val(id);
	j("#describe").val(describe);
	j("#checkname").val(checkname);
	radow.doEvent('showFunction');
}


//��������У��
function savebtnhand(){
	var checkname=j("#checkname").val().trim();
	var describe=j("#describe").val().trim();
	if(checkname==""){
		alert("����дУ�麯�����ƣ�");
		return ;
	}
	if(describe==""){
		alert("����д��������������");
		return ;
	}
	
	radow.doEvent('savebtnhand');
}

//��������У��
function saveconhandler(){
	var num=j("#num").val().trim();
	var type=j("#type").val().trim();
	if(!isNumber(num)){
		alert("������ű���Ϊ���֣�");
		return ;
	}
	if(type==""){
		alert("��ѡ��������ͣ�");
		return ;
	}
	
	radow.doEvent('saveconhandler');
}
//�Ƿ�Ϊ����
function isNumber(val){
    var regPos = /^\d+(\.\d+)?$/; //�Ǹ�������
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //��������
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }
}

//��������б�ĳһ�У���ʾֵ�������ֶ�¼����
function clickPara(grid,rowIndex,colIndex,event){
	var num=grid.store.getAt(rowIndex).get("num");
   	var paradescribe=grid.store.getAt(rowIndex).get("paradescribe");
   	var type=grid.store.getAt(rowIndex).get("type");
   	var is_select=grid.store.getAt(rowIndex).get("is_select");
	j("#num").val(num);
	j("#paradescribe").val(paradescribe);
 	j("#type").val(type);
	j("#is_select_para").val(is_select); 
	radow.doEvent('setPara');
}

function onchangeNum(obj){
	if(!isNumber(parseInt(obj.value))){
		alert("������ű���Ϊ��������");
		return ;
	}
	if(obj.value.indexOf(".") != -1){
		alert("������ű���Ϊ��������");
		return ;
	}else{
		
	}
}
</script>