<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/comOpenWinInit2.jsp"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
.div-inline {
	float: left;
	margin: 0 auto; /* ���� ����Ǳ���ģ������������ԷǱ��� */
	text-align: center; /* ���ֵ����ݾ��� */
}
</style>
<script>
//��ʼ����
Ext.onReady(function() {
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {//����һ����
  	    id:'group',
        el : 'tree-div',//Ŀ��div����
        split:false,
        height:340,
        width:160,
        minSize: 164,
        maxSize: 164,
        rootVisible: true,//�Ƿ���ʾ���ϼ��ڵ㣬Ĭ��Ϊtrue
        autoScroll : true,//������Χ�Զ����ֹ�����
        animate : true,//չ��������ʱ�Ķ���Ч��
        border : false,
        enableDD : false,////���������϶�,������ͨ��Drag�ı�ڵ�Ĳ�νṹ(drap��drop)
        containerScroll : true,//�Ƿ��������ע�ᵽ����������ScrollManager��
        loader : new Tree.TreeLoader( {
      	     dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.CheckExpression&eventNames=orgTreeJsonData&codetype='
        }),
        listeners: { 
      	   click: function(node){
      		   var codevalue=node.attributes.id;
      		   radow.doEvent("clickCodeValue",codevalue);
      	   },
           afterrender: function(node) {        
               tree.expandAll();//չ����     
           }        
         } 
    });
	     var root = new Tree.AsyncTreeNode( { //����AsyncTreeNode 
	          text : document.getElementById('ereaname').value,
	          draggable : false,//�϶�
	          id : document.getElementById('nodeid').value //Ĭ�ϵ�nodeֵ��?node=-100
	          //href:"javascript:radow.doEvent('clickCodeValue','"+document.getElementById('codevalue').value+"')"
	     });
    tree.setRootNode(root);//�������ڵ� 
    tree.render();
    
}); 

<%
	String ereaname = "";
	String nodeid = "-1";
%>
//������ȡcodetypeֵ
function orgTreeJsonData(){
	var tree = Ext.getCmp("group");
    var url='radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.CheckExpression&eventNames=orgTreeJsonData&codetype=';
    tree.loader.dataUrl=url+document.getElementById('codetype').value;
    var selections = odin.ext.getCmp("codeListGrid").getSelectionModel().getSelections();
	var col_name = selections[0].data.col_name;
	var colname = col_name.substring(col_name.indexOf(".")+1,col_name.length);
    tree.root.setText(colname);
    tree.root.reload();
}


</script>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="vru003" text="���ʽУ��" handler="verifiClick" tooltip="У��ϸ���뱣��"
		icon="images/forum.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSaveContinue" text="����" handler="keepClick"  tooltip="����󣬸�У�����Ϊ������"
		icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnReload" text="ɾ��" handler="deletcClick"
		icon="images/icon/click.gif" cls="x-btn-text-icon" />
	<odin:buttonForToolBar text="��һ��" id="upRow" handler="upperClick" tooltip="��Ϊ���л�����Ĭ�ϼ�������ƶ�������һ������"
		icon="images/icon/arrowup.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��һ��" id="downRow" handler="downClick" tooltip="��Ϊ���л�����Ĭ�ϼ�������ƶ�������һ������"
		icon="images/icon/arrowdown.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�" tooltip="��������У����򣬷����޷�����"
		handler="btnClose" icon="images/back.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<div id="panel_content">
	<odin:hidden property="vsc001" />
	<!-- verify_scheme У�鷽������ ,У�鷽������ʶ��һ��У�飻��verify_scheme��verify_ruleһ�Զࣩ-->
</div>
<odin:panel contentEl="panel_content" property="mypanel"
	topBarId="btnToolBar"></odin:panel>
<!-- У�������Ϣ -->
<odin:hidden property="vru001" />
<!-- ��У����Ϣ�� -->
<odin:hidden property="vru004" />
<!-- ��У����Ϣ�� -->
<odin:hidden property="vru005" />
<!-- ƴ����ɵ�SQL�е����� -->
<odin:hidden property="vru006" />
<!--�ֶ�����-->
<odin:hidden property="vru007" value="0" />
<!--��Ч���Ĭ�ϣ�0���ǿգ�     0-��Ч(δ����)��1-��Ч�������ã�-->
<odin:hidden property="vru008" />
<!--����SQL-->
<odin:hidden property="vru009" />
<!--�����ֶ�-->

<!-- ����SQL -->
<odin:hidden property="vru010" />
<!-- У������б����(У��������+�б�˳����)������ -->
<odin:hidden property="vsl005" />
<!--�����ֶ�" -->

<!-- ��ǰ��֤��Ϣ�Ƿ�ͨ�� -->
<odin:hidden property="Flag" />

<table style="width: 100%">
	<tr>
		<odin:textarea property="vru000" label="У����ʽ" value="" colspan="4" rows="7" maxlength="200"/>
		<!-- 
			<td>
				<odin:button property="vru003" text="���ʽУ��" handler="verifiClick"/>
				<div style="height: 20"></div>
				<odin:button property="hintcode" text="������ʾ" handler="codeClick"/>
			</td>
		 -->
	</tr>
	<tr>
		<odin:textEdit property="vru002" label="У����ʾ��Ϣ" colspan="4" maxlength="150" size="150" />
	</tr>
	<tr>
		<!--<odin:select property="vru005" label="У������" colspan="2" maxlength="22" onchange="selectOnChange" size="22" />-->
		<odin:textEdit property="vsc002" label="У������" colspan="2" maxlength="22" size="22" readonly="true"/>
		<!--У��������Ĭ�ϣ�(����)_(�ֶ���)��У�����ͣ������޸�-->
		<odin:dateEdit property="A1KC192" label="����ʱ��"  mask="yyyy-mm-dd"/>
	</tr>
	<%-- 	<tr>
		<td colspan="2" style="padding-left:130px"><odin:button property="vru009" text="У������..." /></td>
		<td colspan="2"><odin:checkbox property="vru010" label="�߼�У�������ø�У�˱��ʽ" /></td>
	</tr>--%>
</table>


<odin:groupBox title="���ݿ��ֶ�">
	<table>
		<tr>
			<td>
				<div class="div-inline" style="margin-left: 10;">
		<table style="border: solid 0px !important;">
			<tr colspan="4">
				<td colspan="1" style="width: 200;"><odin:editgrid
						property="tableListGrid" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="table_code" />
							<odin:gridDataCol name="table_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="����" width="150"
								dataIndex="table_code" edited="false" editor="text" align="left" 
								hidden="true"/>
							<odin:gridEditColumn header="����" width="50"
								dataIndex="table_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 200"><odin:editgrid
						property="codeListGrid" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_type" />
							<odin:gridDataCol name="col_code" />
							<odin:gridDataCol name="col_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="�ֶ�����" width="150" hidden="true"
								dataIndex="code_type" edited="false" editor="text" align="left" />
							<odin:gridEditColumn header="�ֶ���" width="50" dataIndex="col_code"
								edited="false" editor="text" align="left"  hidden="true"/>
							<odin:gridEditColumn header="�ֶ���" width="150"
								dataIndex="col_name" edited="false" editor="text" align="left" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid></td>                 
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 160"><odin:editgrid
						property="personListGrid3123123"  width="160" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="�����" width="0"
								dataIndex="code_value" edited="false" editor="text" align="left"
								hidden="true" />
							<odin:gridEditColumn header="���������" width="150"
								dataIndex="code_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			<tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 200"><odin:editgrid
						property="personListGrid121" width="200" height="400" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="����" width="0"
								dataIndex="code_value" edited="false" editor="text" align="left"
								hidden="true" />
							<odin:gridEditColumn header="У�˺����б�" width="150"
								dataIndex="code_name" edited="false" editor="text" align="left"
								isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div >
	<div class="div-inline" style="width: 160px;height: 350px;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 160px; margin-top:55px; height: 340px;">
				    <odin:groupBox title="ѡ�����" >
						<div id="tree-div" style="width: 160px;height: 363px;"></div>
					</odin:groupBox>
				</td>
			</tr>
			<tr>
				<odin:hidden property="codevalue"/> 
			    <odin:hidden property="ereaname" value="<%=ereaname%>" />
				<odin:hidden property="nodeid" value="<%=nodeid%>" />
			</tr>
		</table>
	</div>
</odin:groupBox>

<!-- ��ǰ�������� -->
<odin:hidden property="nowString" />
<!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="table_code" />
<!-- ��ǰѡ�е���Ϣ�� ����:A01.A0000 --> 
<odin:hidden property="col_code" />
<!-- ��ǰѡ�е���Ϣ������� --> 
<odin:hidden property="codetype" />
<!-- �Դ洢���ֶ���:A01.A0000 --> 
<odin:hidden property="vru011" />
<!-- ��������־ --> 
<odin:hidden property="keepCheckBZ" />

<script>

var vsc001="";
function verifiClick(){
	if(document.getElementById("A1KC192").value==""){
		alert("����ȷ������ʱ�䣡");
		return;
	}
	document.getElementById("keepCheckBZ").value="0";
	radow.doEvent("verifiClick");
}
function keepClick(){//�������
	if(document.getElementById("Flag").value==""||document.getElementById("Flag").value=="no"){
		alert("����ȷ��У����ʽ�ϸ�");
		return;
	}
	document.getElementById("keepCheckBZ").value="1";
	radow.doEvent("keepCheck"); 
}
function deletcClick(){//ɾ������ -- ����������ɾ����Ӧ����  -- ������������ˢ��ҳ��
	radow.doEvent("deletcClick"); 
}

function upperClick(){//��һ��
	radow.doEvent("rownumChange","1"); 
}
function downClick(){//��һ��
	radow.doEvent("rownumChange","0"); 
}
function codeClick(){
	$h.openPageModeWin('CodeClickWin','pages.cadremgn.sysmanager.CodeClickWindow','ѡ����ʾ����',270,410,'','<%=request.getContextPath() %>');
}
function selectOnChange(){//�޸�У�鷽������  -- ѡ��У������
	vsc001=document.getElementById("vsc001").value=document.getElementById("vru005").value;
	radow.doEvent("selectOnChange",vsc001); 
}
function btnClose() {//�ر�ҳ��
	window.close();
	forverification();
}

function forverification(){
	var pWindow=window.dialogArguments['window'];
	pWindow.Ext.getCmp('VerifyRuleGrid').store.reload();
}
<!-- �洢����[A01.A0000,A01.A0101] --> 
function rowDbClick1(){
	var getPosi = " "+document.getElementById('nowString').value+" ";
	var textarea = document.getElementById('vru000');
	var vru000 = document.getElementById('vru000').value;
	var userSelection = getCaret(textarea);
	//alert(userSelection);
	document.getElementById('vru000').value = vru000.substring(0,userSelection) + getPosi + vru000.substring(userSelection,vru000.length);
}
//ʵ���ı�ָ��λ��ƴ��
function getCaret(el) { 
	  if (el.selectionStart) { 
	    return el.selectionStart; 
	  } else if (document.selection) { 
	    el.focus(); 
	    var r = document.selection.createRange(); 
	    if (r == null) { 
	      return 0; 
	    } 
	    var re = el.createTextRange(), 
	        rc = re.duplicate(); 
	    re.moveToBookmark(r.getBookmark()); 
	    rc.setEndPoint('EndToStart', re); 
	    return rc.text.length; 
	  }  
	  return 0; 
	}
</script>