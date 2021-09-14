<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>

<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="btnSaveContinue"  text="���沢����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btnSave"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnReload" text="ˢ��"  icon="images/icon/click.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="vsc001"/><!-- verify_scheme У�鷽������ ,У�鷽������ʶ��һ��У�飻��verify_scheme��verify_ruleһ�Զࣩ-->
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<!-- У�������Ϣ -->
<odin:hidden property="vru001"/>	<!--У�������������-->
<odin:hidden property="vru006"/>	<!--У������-->
<odin:hidden property="vru007" value="0"/>	<!--��Ч���Ĭ�ϣ�0���ǿգ�     0-��Ч(δ����)��1-��Ч�������ã�-->
<odin:hidden property="vru008"/>	<!--������Ϣ��У����𣩣�(����)_(�ֶ���)δͨ�� _��У�������ƣ�-->
<odin:hidden property="vru009"/>	<!--�����ֶ�-->

<!-- У��sql��Ϣ -->
<odin:hidden property="vsl001"/><!-- У������б����(У��������+�б�˳����)������ -->
<odin:hidden property="vsl005"  /> <!--�ֶ�1��������" -->
<odin:hidden property="vsl010"  /> <!--�ֶ�2��������" -->
<odin:hidden property="vsl013"  /> <!--�����ֶ�" -->

<!-- �����ǰGrid�к� -->
<odin:hidden property="cueRowIndex"/>

<odin:groupBox title="У�������Ϣ">
	<table style="width:100%" >
		<tr>
			<odin:textEdit property="vru002" label="У��������" colspan="4"  maxlength="45" size="75"/>	<!--У��������Ĭ�ϣ�(����)_(�ֶ���)��У�����ͣ������޸�-->
			<odin:select2 property="vru003" label="У�����" codeType="VRU003" value="1" required="true" size="10" />	<!--Ĭ��Ϊ1�����  1-����2-���棬3-��ʾ-->
		</tr>
		
	</table>
</odin:groupBox>
	<odin:groupBox title="ѡ������<label style='color:red'>��ע������Ӽ�������������̶��ȶ�ֵ��ѡ��ȶ�ֵ���ȶ���Ϣ��ֻ����ѡ��һ��</label>">
		<table width="100%">
		    <tr>
				<odin:select2 property="vru004" label="У����Ϣ��"    required="true" />	<!--�����-->
				<odin:select2 property="vru005" label="У����Ϣ��"  required="true"/>	<!--�ֶδ���-->
				<%-- <odin:select property="vsl003" label="��Ϣ��1" codeType="VSL003" disabled="true" required="true" />
				<odin:select property="vsl004" label="��Ϣ��1" codeType="VSL004" disabled="true"  required="true" /> --%>
				<odin:hidden property="vsl003"/>
				<odin:hidden property="vsl004"/>
				
				<odin:select2 property="vsl006" label="�����" codeType="1" size="25" required="true" colspan="4"/>
			</tr>
			<tr>
				<odin:textEdit property="vsl007" label="�̶��ȶ�ֵ" maxlength="10"/>
				<odin:select2 property="vsl013" label="ѡ��ȶ�ֵ" colspan="6"/>
			</tr>
			<tr>
				<odin:select2 property="vsl008" label="�ȶ���Ϣ��"  onchange="true"  />
				<odin:select2 property="vsl009" label="�ȶ���Ϣ��"   />
			
				<td>
					<odin:radio property="vsl012" value="1" label="����"  />
				</td>
				<td>
					<odin:radio property="vsl012" value="2" label="����&nbsp;&nbsp;&nbsp;" />
				</td>
				<td>
					<odin:button text="����" property="savecond"></odin:button>
				</td>
				<td>
					<odin:button text="�޸�" property="modefyCond"></odin:button>
				</td>
			</tr>
			
		</table>
	</odin:groupBox>
	<odin:toolBar property="toolBar2">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="&nbsp;(+&nbsp;" id="addLeft"  ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="&nbsp;+)&nbsp;" id="addRight"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="&nbsp;(-&nbsp;" id="delLeft"  ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="&nbsp;-)&nbsp;" id="delRight"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="����" id="upRow" icon="images/icon/arrowup.gif" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="����" id="downRow" icon="images/icon/arrowdown.gif"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="ɾ��" handler="delRow"  icon="images/icon/delete.gif" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="ȫɾ" id="delAll"  icon="images/icon/delete.gif"  isLast="true"></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:gridSelectColJs name="vsl012" codeType="vsl012"  />
	<odin:gridSelectColJs name="vsl002" selectData="['0', ''],['1', '('],['2', '(('],['3', '((('],['4', '(((('],['5', '(((((']"></odin:gridSelectColJs>
	<odin:gridSelectColJs name="vsl011" selectData="['0', ''],['1', ')'],['2', '))'],['3', ')))'],['4', '))))'],['5', ')))))']"></odin:gridSelectColJs>
<odin:editgrid property="grid" autoFill="true" topBarId="toolBar2" height="270" sm="row" isFirstLoadData="false" bbarId="pageToolBar" url="/">
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="vsl001" />
							<odin:gridDataCol name="vru001" />
							<odin:gridDataCol name="vsl002" />
							<odin:gridDataCol name="vsl003_name" />
							<odin:gridDataCol name="vsl004_name" />
							<odin:gridDataCol name="vsl003" />
							<odin:gridDataCol name="vsl004" />
							<odin:gridDataCol name="vsl005" />
							<odin:gridDataCol name="vsl006" />
							<odin:gridDataCol name="vsl007" />
							<odin:gridDataCol name="vsl013_name" />
							<odin:gridDataCol name="vsl013" />
							<odin:gridDataCol name="vsl008" />
							<odin:gridDataCol name="vsl009" />
							<odin:gridDataCol name="vsl008_name" />
							<odin:gridDataCol name="vsl009_name" />
							<odin:gridDataCol name="vsl010" />
							<odin:gridDataCol name="vsl011" />
							<odin:gridDataCol name="vsl012" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="vsl001" header="У������б����(У��������+�б�˳����)������" hidden="true" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vru001" header="У�����������"  hidden="true"  align="center"/>
							<odin:gridEditColumn2 dataIndex="vsl002" header="������" edited="false" align="center" width="35" editor="select"  sortable="false" selectData="['0', ''],['1', '('],['2', '(('],['3', '((('],['4', '(((('],['5', '(((((']"/>
							<odin:gridColumn dataIndex="vsl003_name" header="У����Ϣ��"  width="60" editor="text"  align="center"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl004_name" header="У����Ϣ��"  width="60"  editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl003" header="У����Ϣ������"  width="60"  hidden="true" editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl004" header="У����Ϣ�����"  width="60"  hidden="true"  editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl005" header="У���ֶ���������"  hidden="true"  align="center" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="vsl006" header="������"  edited="false"  codeType="VSL006" editor="select" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl007" header="�ȶ��趨ֵ" align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl013_name" header="�ȶ�ѡ��ֵ"  align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl013" header="�ȶ�ѡ�����" hidden="true"  align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl008_name" header="�ȶ���Ϣ��"  width="60"   editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl009_name" header="�ȶ���Ϣ��"  width="60"   editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl008" header="�ȶ���Ϣ������"  width="60" hidden="true"  editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl009" header="�ȶ���Ϣ�����"  width="60" hidden="true"  editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl010" header="�ȶ��ֶ���������" hidden="true"   align="center" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="vsl011" header="������"  edited="false"  align="center"  width="35" editor="select"  sortable="false" selectData="['0', ''],['1', ')'],['2', '))'],['3', ')))'],['4', '))))'],['5', ')))))']"/>
							<odin:gridEditColumn2 dataIndex="vsl012" header="���ӷ�" codeType="VSL012" edited="false" editor="select" align="center"  sortable="false" isLast="true"/>

						</odin:gridColumnModel>
					</odin:editgrid>


<odin:window src="/blank.htm" id="win3" width="560" height="392" title="sql�ű��鿴">
</odin:window>

<script>
Ext.onReady(function() {
	document.getElementById('vru002SpanId').innerHTML='<font color="red">*</font>У��������';
});


function addEmpRow(){
	radow.addGridEmptyRow("grid",0);
}
function dataRender4del(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=href='javascript:void(0)' onclick = del('"+rowIndex+"')>ɾ��</a>";
}
function del(rowIndex){
	var grid = odin.ext.getCmp('grid');
	var store = grid.store;
	store.removeAt(rowIndex);
}

function delEmpRow1(){
	var grid = odin.ext.getCmp('grid');
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount()-1;
	if(store.getCount() > 0){
		for( var i = store.getCount()-1 ;i>=0; i-- ){
			var ck = grid.getStore().getAt(i).get("logchecked");
	        if(ck == true){
	        	store.remove(grid.getStore().getAt(i));
			}
		}
	}
	grid.view.refresh();
}


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
	radow.doEvent('delCount');
}
</script>