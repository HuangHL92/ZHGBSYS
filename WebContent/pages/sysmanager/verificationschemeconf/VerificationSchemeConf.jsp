<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div id="main">
<odin:hidden property="username"/>
<odin:hidden property="dbClickVsc001"/><!-- ˫��ѡ�е�У�鷽������ -->
<odin:hidden property="isdefault"/><!--˫��ѡ�еķ����Ƿ�Ĭ�Ϸ���  -->
<!--<div id="panel_content" >
		<odin:hidden property="nothing"/>
		<odin:toolBar property="btnToolBar" applyTo="panel_content">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="��ѯ" id="query" icon="images/search.gif"  tooltip="��ѯ��ϢУ�鷽��" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="У���������" id="paraSet" icon="image/u53.png" tooltip="������ϢУ�������ʹ�õ����������" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="У�˺�������" id="checkFunction" icon="image/u53.gif"  tooltip="�Զ���У�˺���" handler="checkF"/>
		<odin:buttonForToolBar text="У�˱��ʽ����" id="checkExpression" icon="images/search.gif"  tooltip="��ѯ��ϢУ�鷽��" handler="checkE" />
		<odin:buttonForToolBar text="ˢ��ҳ��" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
		</odin:toolBar>
</div>-->
<div id="groupTreeContent" style="height: 100%;padding-top: 0px;">
	<table >
		<tr>
			 <td  id="table_id1">
			<odin:toolBar property="toolBar1">
			<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;У�鷽����Ϣ</h1>"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="����" id="addScheme" icon="images/add.gif" tooltip="������ϢУ�鷽��" />
			<odin:buttonForToolBar text="����" id="expScheme" icon="images/icon/exp.png" tooltip="������ϢУ�鷽��" handler="expS"/>
			<odin:buttonForToolBar text="����" id="impScheme" handler="impScheme" icon="images/icon/exp.png"  tooltip="������ϢУ�鷽��" isLast="true"></odin:buttonForToolBar>
		</odin:toolBar>	
		<odin:editgrid property="VeriySchemeGrid" title=""   bbarId="pageToolBar" topBarId="toolBar1"  pageSize="10" isFirstLoadData="false" url="/"><!-- bbarId="pageToolBar" -->
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vsc003" />
			<odin:gridDataCol name="vsc004" />
			<odin:gridDataCol name="vsc005" />
			<odin:gridDataCol name="vsc006" />
			<odin:gridDataCol name="vsc007" />
			<odin:gridDataCol name="vsc008" />
			<odin:gridDataCol name="vsc009" />
			<odin:gridDataCol name="vsc010" />
			<odin:gridDataCol name="vsc011"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="У�鷽������" width="100" dataIndex="vsc001" edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="У�鷽������" width="120" dataIndex="vsc002" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="������Ա" width="130" dataIndex="vsc005" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="�����������û���" width="130" dataIndex="vsc004" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="����ʱ��" width="130" dataIndex="vsc006" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn2 header="Ĭ�ϻ�������" width="80" dataIndex="vsc007" edited="false" editor="select" codeType="VSC007" align="center" hidden="true"/>
			<odin:gridEditColumn2 header="������" width="60" dataIndex="vsc008" edited="false" editor="select" align="center" selectData="['0', '��'],['1', '��']" hidden="true"/>
			<odin:gridEditColumn2 header="״̬" width="60" dataIndex="vsc003" edited="false"  editor="select" align="center"  selectData="['0', '������'],['1', '������']" renderer="runorstop"/>
			<odin:gridEditColumn header="��ע" width="100" dataIndex="vsc009" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="������ԱId" width="50" dataIndex="vsc010" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="������Ա���ڵ�λ����" width="50" dataIndex="vsc011" edited="false" editor="text" align="center" hidden="true"  />
			<odin:gridEditColumn edited="false" dataIndex="updatedelete" header="����" editor="text" isLast="true" renderer="uod" width="100" align="center"/>
		</odin:gridColumnModel>
	</odin:editgrid>
			</td>
			<td  id="table_id2">
		<odin:toolBar property="toolBar2">
		<odin:textForToolBar text="<h1 id=&quot;abc&quot; style=&quot;color:rgb(21,66,139);size:11px&quot; >У�鷽����Ϣ</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="����" id="addrule" handler="addRule" icon="images/add.gif" tooltip="������ǰ�����Ĺ���"/>
		<odin:buttonForToolBar text="ȫ������" id="runAllRule" handler="runAllRule" icon="images/icon/right.gif" tooltip="���õ�ǰ���й���"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="���Ƶ�...����" id="copyRow" handler="copyRow" icon="images/icon/exp.png" tooltip="�����б���ѡ�й�����������"></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="У�˺�������" id="checkFunction" icon="image/u53.png" tooltip="�Զ���У�˺���"  handler="checkF"  isLast="true"/>
		</odin:toolBar>
	<odin:editgrid property="VerifyRuleGrid"  topBarId="toolBar2" bbarId="pageToolBar"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vru001" />
			<odin:gridDataCol name="vru002" />
			<odin:gridDataCol name="vru003" />
			<odin:gridDataCol name="vru004_name" />
			<odin:gridDataCol name="vru005_name" />
			<odin:gridDataCol name="vru006" />
			<odin:gridDataCol name="vru007" />
			<odin:gridDataCol name="vru008" />
			<odin:gridDataCol name="vru009"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="У�鷽������" width="35" dataIndex="vsc001"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="У�鷽��" width="150" dataIndex="vsc002"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="У��������" width="50" dataIndex="vru001"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="У���������" width="180" dataIndex="vru002"	edited="false" editor="text" align="left"   />
			<odin:gridEditColumn2 header="���" width="100" dataIndex="vru003"	edited="false" editor="select" align="center" codeType="VRU003"  />
			<odin:gridEditColumn header="��У����Ϣ��" width="150" dataIndex="vru004_name"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="��У����Ϣ��" width="150" dataIndex="vru005_name"	edited="false" editor="text" align="center"    />
			<odin:gridEditColumn header="У������" width="100" dataIndex="vru006"	edited="false" editor="text" align="center"   hidden="true"  />
			<odin:gridEditColumn2 header="״̬" width="60" dataIndex="vru007"	edited="false" editor="select" align="center"  selectData="['0', '������'],['1', '������']" renderer="rosRule" />
			<odin:gridEditColumn2 header="����" width="100" dataIndex="updatedelete" edited="false" editor="text" renderer="uodRule" align="center"/>
			<odin:gridEditColumn header="������Ϣ" width="180" dataIndex="vru008"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="ƴ�Ӻõ�SQL���" width="50" dataIndex="vru009"	edited="false" editor="text" align="center"  hidden="true"   isLast="true" />
		</odin:gridColumnModel>
		</odin:editgrid>
			</td>
		</tr>
	</table>
</div>
<%-- <odin:tab id="tab" width="100%"> --%>
<%-- <odin:tabModel>
<odin:tabItem title="��ϢУ�鷽��" id="tab1"></odin:tabItem>
<odin:tabItem title="��ϢУ�����" id="tab2" isLast="true"></odin:tabItem>
</odin:tabModel>
---------------------------------------��ϢУ�鷽�� tab1--------------------------------------------------------
<odin:tabCont itemIndex="tab1">	
	
	<odin:toolBar property="btnToolBar">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="��ѯ" id="query" icon="images/search.gif"  tooltip="��ѯ��ϢУ�鷽��" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="У���������" id="paraSet" icon="image/u53.png" tooltip="������ϢУ�������ʹ�õ����������" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="ˢ��ҳ��" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
	</odin:toolBar>
	

	
	
	<div id="panel_content" >
		<odin:hidden property="nothing"/>
	</div>
	<odin:toolBar property="toolBar1">
		<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;У�鷽����Ϣ</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="��ѯ" id="query" icon="images/search.gif"  tooltip="��ѯ��ϢУ�鷽��" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="У���������" id="paraSet" icon="image/u53.png" tooltip="������ϢУ�������ʹ�õ����������" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="����" id="addScheme" icon="images/add.gif" tooltip="������ϢУ�鷽��" />
		<odin:buttonForToolBar text="�޸�" id="updateScheme" icon="image/u53.png" tooltip="�޸���ϢУ�鷽��"/>
		<odin:buttonForToolBar text="ɾ��" id="delScheme" icon="images/icon/delete.gif" tooltip="ѡ�е���ϢУ�鷽��ɾ��" />
		<odin:buttonForToolBar text="����" id="saveUpdateScheme" icon="images/save.gif" tooltip="ѡ�е���ϢУ�鷽���޸����ơ���ע������Ĭ�Ϸ������"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="����" id="run"  tooltip="ѡ�е���ϢУ�鷽�����ã����ú��򷽰���ѡ��" icon="images/icon/right.gif"/>
		<odin:buttonForToolBar text="ͣ��" id="stop" tooltip="ѡ�е���ϢУ�鷽��ͣ�ã�ͣ�ú��򷽰�����ѡ��" icon="images/back.gif"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="����" id="expScheme" icon="images/icon/exp.png" tooltip="������ϢУ�鷽��"/>
		<odin:buttonForToolBar text="����" id="impScheme" handler="impScheme" icon="images/icon/exp.png"  tooltip="������ϢУ�鷽��" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="ˢ��ҳ��" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
	</odin:toolBar>		
	<odin:gridSelectColJs2 name="vsc007" codeType="VSC007"></odin:gridSelectColJs2>
	<odin:panel contentEl="panel_content" property="mypanel1" topBarId="btnToolBar" ></odin:panel>
	<odin:editgrid property="VeriySchemeGrid" title=""   bbarId="pageToolBar" topBarId="toolBar1"  pageSize="10" isFirstLoadData="false" url="/"><!-- bbarId="pageToolBar" -->
	<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="mcheck" />
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vsc003" />
			<odin:gridDataCol name="vsc004" />
			<odin:gridDataCol name="vsc005" />
			<odin:gridDataCol name="vsc006" />
			<odin:gridDataCol name="vsc007" />
			<odin:gridDataCol name="vsc008" />
			<odin:gridDataCol name="vsc009" />
			<odin:gridDataCol name="vsc010" />
			<odin:gridDataCol name="vsc011"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn header="selectall" gridName="VeriySchemeGrid" align="center" width="50"
				editor="checkbox" edited="true" dataIndex="mcheck" />
			<odin:gridColumn dataIndex="vsc001" header="��������" width="60" align="center" renderer="setter" sortable="false" hidden="true"/>
			<odin:gridColumn dataIndex="vsc001" header="�޸�" width="60" align="center" hidden="true" renderer="openEditer"/>
			<odin:gridEditColumn header="У�鷽������" width="100" dataIndex="vsc001" edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="У�鷽������" width="150" dataIndex="vsc002" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="������Ա" width="130" dataIndex="vsc005" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="�����������û���" width="130" dataIndex="vsc004" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="����ʱ��" width="130" dataIndex="vsc006" edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 header="Ĭ�ϻ�������" width="80" dataIndex="vsc007" edited="false" editor="select" codeType="VSC007" align="center" hidden="false"/>
			<odin:gridEditColumn2 header="������" width="60" dataIndex="vsc008" edited="false" editor="select" align="center" selectData="['0', '��'],['1', '��']" hidden="true"/>
			<odin:gridEditColumn2 header="״̬" width="60" dataIndex="vsc003" edited="false"  editor="select" align="center"  selectData="['0', '������'],['1', '������']" renderer="runorstop"/>
			<odin:gridEditColumn header="��ע" width="220" dataIndex="vsc009" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="������ԱId" width="50" dataIndex="vsc010" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="������Ա���ڵ�λ����" width="50" dataIndex="vsc011" edited="false" editor="text" align="center" hidden="true"  isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</odin:tabCont>	

<!-- -------------------------------------------��ϢУ����� tab2 --------------------------------------------------- -->
<odin:tabCont itemIndex="tab2">
	<odin:toolBar property="toolBar2">
		<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="����" id="copyRow" handler="copyRow" icon="images/icon/exp.png" tooltip="������ǰ�����Ĺ���"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="����" id="addrule" handler="addRule" icon="images/add.gif" tooltip="�����б���ѡ�й�����������"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="����" id="runRule"  tooltip="����ѡ�е�У�����" icon="images/icon/right.gif" handler="runRow"/>
		<odin:buttonForToolBar text="ͣ��" id="stopRule" tooltip="ͣ��ѡ�е�У�����" icon="images/back.gif" handler="stopRow"/>
		<odin:buttonForToolBar text="ɾ��" id="delRow" handler="delRow" icon="images/icon/delete.gif" tooltip="ɾ���б���ѡ�е�У�����" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="ȫɾ" id="delAll" icon="images/icon/delete.gif"  isLast="true"  tooltip="ɾ���б���ȫ����У�����"></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:editgrid property="VerifyRuleGrid" width="100%"   topBarId="toolBar2" bbarId="pageToolBar"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vru001" />
			<odin:gridDataCol name="vru002" />
			<odin:gridDataCol name="vru003" />
			<odin:gridDataCol name="vru004" />
			<odin:gridDataCol name="vru005" />
			<odin:gridDataCol name="vru006" />
			<odin:gridDataCol name="vru007" />
			<odin:gridDataCol name="vru008" />
			<odin:gridDataCol name="vru009"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
	
			<odin:gridEditColumn header="У�鷽������" width="35" dataIndex="vsc001"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="У�鷽��" width="150" dataIndex="vsc002"	edited="false" editor="text" align="center"  hidden="false"  />
			<odin:gridEditColumn header="У��������" width="50" dataIndex="vru001"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="У���������" width="380" dataIndex="vru002"	edited="false" editor="text" align="left"   />
			<odin:gridEditColumn2 header="���" width="100" dataIndex="vru003"	edited="false" editor="select" align="center" codeType="VRU003"  />
			<odin:gridEditColumn header="��У����Ϣ��" width="150" dataIndex="vru004"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="��У����Ϣ��" width="200" dataIndex="vru005"	edited="false" editor="text" align="center"    />
			<odin:gridEditColumn header="У������" width="100" dataIndex="vru006"	edited="false" editor="text" align="center"   hidden="true"  />
			<odin:gridEditColumn2 header="���ñ��" width="80" dataIndex="vru007"	edited="false" editor="select" align="center"  selectData="['0', '������'],['1', '������']"  />
			<odin:gridEditColumn header="������Ϣ" width="180" dataIndex="vru008"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="ƴ�Ӻõ�SQL���" width="50" dataIndex="vru009"	edited="false" editor="text" align="center"  hidden="true"   isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</odin:tabCont>		
</odin:tab>	 --%>	
</div>
<odin:window src="/blank.htm" id="paraSetWin" width="580" height="500" maximizable="false" title="��ϢУ������������ý���" closable="true" modal="true" />
<odin:window src="/blank.htm" id="addSchemeWin" width="605" height="245" maximizable="false" title="�����޸�У�鷽����Ϣ" closable="true" modal="true" />
<odin:window src="/blank.htm" id="addwin" width="780" height="500" maximizable="false" title="�����޸�У�������Ϣ"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="impExcelWin" width="450" height="150" title="�������봰��"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="expExcelWin" width="560" height="150" title="������������"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="copyWin" width="350" height="200" title="����У����򴰿�" closable="true" modal="true"/>

<odin:window src="/blank.htm" id="checkFunctionWin" width="780" height="500" maximizable="false" title="У�˺���"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="checkExpressionWin" width="605" height="245" maximizable="false" title="У�˱��ʽ" closable="true" modal="true" />
<script>
/* function openEditer(value, params, record,rowIndex,colIndex,ds){
	if(value){
		return "<img src='"+contextPath+"/images/update.gif' title='' style='cursor:pointer' onclick=\"radow.doEvent('updateScheme','"+value+"');\">";
	}else{
		return null;
	}
} */
/* function setter(value, params, record,rowIndex,colIndex,ds){
	if(value){
		return "<img src='"+contextPath+"/images/article.gif' title=''  style='cursor:pointer' onclick=\"radow.doEvent('addMyscript','"+value+"');\">";
	}else{
		return null;
	}
} */
function changeh1(text){
	document.getElementById("abc").innerHTML="��ǰУ�鷽��:"+text;
	
	
}
function changetext(){
	var objs=document.getElementById("abc").innerHTML="У�鷽����Ϣ";
}
function addRule(){
	radow.doEvent("addMyscript")
}
function runorstop(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<img  src='"+contextPath+"/images/wrong.gif' title=''  style='cursor:pointer' onclick=runScheme(this,'"+value+"','"+rowIndex+"')>";
	}else{
		return "<img  src='"+contextPath+"/images/right1.gif' title=''  style='cursor:pointer' onclick=runScheme(this,'"+value+"','"+rowIndex+"')>";
	}
}
var img;
var img2;
function runScheme(obj,v,rowIndex){
	img=obj;
	radow.doEvent("runOrStopScheme",v+','+rowIndex);

}
function changePic(v){
	if(v==0){
		img.src=contextPath+"/images/right1.gif";
	}else{
		img.src=contextPath+"/images/wrong.gif";
	}
}

function uod(value, params, record,rowIndex,colIndex,ds){
	var username=document.getElementById("username").value;
	if(record.data.vsc007==0||username=='admin'){
		return "<a href=\"javascript:updateSchemeRow('"+rowIndex+"')\">�޸�</a>&nbsp&nbsp<a href=\"javascript:deleteSchemeRow('"+rowIndex+"')\">ɾ��</a>";
	}else{
		return "<a >����</a>&nbsp&nbsp<a>����</a>";
	}
}

function rosRule(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<img  src='"+contextPath+"/images/wrong.gif' title=''  style='cursor:pointer' onclick=runRule(this,'"+value+"','"+rowIndex+"')>";
	}else{
		return "<img  src='"+contextPath+"/images/right1.gif' title=''  style='cursor:pointer' onclick=runRule(this,'"+value+"','"+rowIndex+"')>";
	}
}

function runRule(obj,v,rowIndex){
	img2=obj;
	radow.doEvent("runOrStopRule",v+','+rowIndex);
}

function uodRule(value, params, record,rowIndex,colIndex,ds){
	var username=document.getElementById("username").value;
	var isdef=document.getElementById("isdefault").value;
	if(isdef=='1'&&username=='admin'){
		return "<a href=\"javascript:updateRuleRow('"+rowIndex+"')\">�޸�</a>&nbsp&nbsp<a href=\"javascript:deleteRuleRow('"+rowIndex+"')\">ɾ��</a>";
	}else if(isdef=='0'){
		return "<a href=\"javascript:updateRuleRow('"+rowIndex+"')\">�޸�</a>&nbsp&nbsp<a href=\"javascript:deleteRuleRow('"+rowIndex+"')\">ɾ��</a>";
	}else{
		return "<a >����</a>&nbsp&nbsp<a>����</a>";
	}
	
}
function updateRuleRow(index){
	radow.doEvent("updateRule",index);
}
function deleteRuleRow(index){
	radow.doEvent("delRowBefore",index);
}
function updateSchemeRow(index){
	radow.doEvent("updateScheme.onclick",index);
}
function deleteSchemeRow(index){
	radow.doEvent("delScheme.onclick",index);
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('groupgrid').view.refresh(true);
	}
}

function delRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('delRowBefore',vru001s);
}

function copyRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('openCopyWin',vru001s);
}
function runRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('runRule',vru001s);
}

function stopRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('stopRule',vru001s);
}

function expS(){
	var grid = odin.ext.getCmp('VeriySchemeGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vsc001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vsc001s = vsc001s +selections[i].data.vsc001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('expScheme',vsc001s);
}

var vsc001Exp = '';
var fileName = '';
function expScheme(vsc001,fName){
	vsc001Exp = vsc001;
	fileName = fName;
	odin.showWindowWithSrc('expExcelWin',contextPath+"/pages/sysmanager/verificationschemeconf/ExcelExpWindow.jsp");
}


function impScheme(){
	 odin.showWindowWithSrc("impExcelWin",contextPath+"/pages/sysmanager/verificationschemeconf/ExcelImpWindow.jsp"); 
	 
}

/* function getShowTow(){
	odin.ext.getCmp('tab').activate('tab2');
} */
Ext.onReady(function() {
	//ҳ�����
	 //Ext.getCmp('VeriySchemeGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_VeriySchemeGrid'))[0]-4)
	//alert(Ext.getBody().getViewSize().height);
	//alert(Ext.getBody().getViewSize().width);
	Ext.getCmp('VeriySchemeGrid').setHeight(505);
	Ext.getCmp('VeriySchemeGrid').setWidth(425);
	//Ext.getCmp('VeriySchemeGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_VeriySchemeGrid'))[1]-2); 
	 //Ext.getCmp('mypanel1').setWidth(document.body.clientWidth);
	 //Ext.getCmp('toolBar1').setWidth(document.body.clientWidth);
	 document.getElementById("main").style.width = document.body.clientWidth;
		//ҳ�����
	Ext.getCmp('VerifyRuleGrid').setHeight(505);
	Ext.getCmp('VerifyRuleGrid').setWidth(927);	
	 //Ext.getCmp('VerifyRuleGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_VerifyRuleGrid'))[0]-4);
	 //Ext.getCmp('VerifyRuleGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_VerifyRuleGrid'))[1]-2); 
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}

function runAllRule(){
	var vsc001=document.getElementById('dbClickVsc001').value;
	var mk = new Ext.LoadMask(document.body, {  
		msg: '�������ã����Ժ�',  
		removeMask: true //��ɺ��Ƴ�  
		});  
		mk.show(); //��ʾ  
	 Ext.Ajax.request({
			url : "radowAction.do?method=doEvent&pageModel=pages.sysmanager.verificationschemeconf.VerificationSchemeConf&eventNames=runAllRule&vsc001="+vsc001,
			success : function(data) {
				mk.hide(); //�ر�  
				//console.log(data);
				//Ext.msg.alert(data.responseText);
				Ext.Msg.alert("ϵͳ��ʾ",data.responseText);
				radow.doEvent("VerifyRuleGrid.dogridquery");
			}
		});
}
function checkF(){
	$h.openPageModeWin('checkFunctionWin','pages.cadremgn.sysmanager.CheckFunction','У�˺�������',1000,650,'','<%=request.getContextPath()%>');
} 
function checkE(){
	$h.openPageModeWin('checkExpressionWin','pages.cadremgn.sysmanager.CheckExpression','У�˱��ʽ����',795,620,'','<%=request.getContextPath()%>');
} 
function checkExpressionWinQD(vscvru){
	$h.openPageModeWin('QueryCallWin2','pages.cadremgn.sysmanager.CheckExpression','�߼�У������ά��',1010,645,vscvru,'<%=request.getContextPath()%>'); 
}

</script>

<script type="text/javascript">
Ext.onReady(function(){
	document.getElementById("groupTreeContent").parentNode.parentNode.style.overflow='hidden';
	window.onresize=resizeframe;
	resizeframe();
});

function resizeframe(){
	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById("checkFunction").style.marginRight="160px";
	var businessInfoGrid =Ext.getCmp('VeriySchemeGrid');
	businessInfoGrid.setHeight(viewSize.height);//56 52

	var modelInfoGrid =Ext.getCmp('VerifyRuleGrid');
	modelInfoGrid.setHeight(viewSize.height);
	modelInfoGrid.setWidth(viewSize.width-270);
}
</script>