<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.infmtionquery.SqlSearchPageModel"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<%@ include file="/comOpenWinInit2.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<style>
.div-inline {
	float: left;
	margin: 0 auto; /* ���� ����Ǳ���ģ������������ԷǱ��� */
	text-align: center; /* ���ֵ����ݾ��� */
}
table{
 border:0;
 cellpadding:0;
 cellspacing:0;
 margin:0;
}
iframe{
 border:0;
 cellpadding:0;
 cellspacing:0;
 margin:0;
 height:100%;
 width:100%;
}
#tab__tab1{
	margin-left:390;
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
        height:265,
        width:150,
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
<div id="panel_content">
</div >
<odin:tab id="tab">
	<odin:tabModel >
		<odin:tabItem title="�޸�" id="tab1"></odin:tabItem>
		<odin:tabItem title="�����޸�����" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1"  >
<table width="100%" height="100%">
<tr><td>
<!-- �����ǰGrid�к� -->
<odin:hidden property="updateFlag" value=""/>
	<odin:groupBox  title="��ѯ���">
		<table>
			<tr>
				<odin:textarea property="vru000" cols="150" rows="8" />
			</tr>
			<tr >
			<td align="right" style="padding-right:20px" >
				<%-- <odin:button  text="ִ��"  property="btn1" handler="queryResult" /> --%>
				<input id="btn1" class="yellowbutton" type="button" value="ִ&nbsp;&nbsp;��" onclick="queryResult()"/>
			</td>
		</tr>
		</table>
	</odin:groupBox>
</td>
</tr>
<tr><td>
	<!--<odin:groupBox title="������Ϣ��ʾ" >
		<table>
			<tr>
				<odin:textarea property="vru005" cols="120" disabled="true" rows="3" />
			</tr>
		</table>
	</odin:groupBox>-->
	<!-- <table>
		<tr >
			<td style="padding-left:550px" >
				<odin:button  text="ִ��"  property="btn1" handler="queryResult" />
			</td>
			<td style="padding-left:20px" >
				<odin:button  text="����" property="btn2"  handler="expExcelFromGrid" />
			</td>
		</tr>
	</table> -->
</td>
</tr>
<tr>
<td>
<odin:groupBox title="���ݿ��ֶ�">
	<table>
		<tr>
			<td>
				<div class="div-inline" style="margin-left: 10;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 170;"><odin:editgrid
						property="tableListGrid" width="100" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="table_code" />
							<odin:gridDataCol name="table_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="����" width="0" dataIndex="table_code" edited="false" editor="text" align="left" hidden="true"/>
							<odin:gridEditColumn header="����" width="123" dataIndex="table_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 170">
					<odin:editgrid property="codeListGrid" width="170" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_type" />
							<odin:gridDataCol name="col_code" />
							<odin:gridDataCol name="col_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="�ֶ�����" width="0" hidden="true" dataIndex="code_type" edited="false" editor="text" align="left" />
							<odin:gridEditColumn header="�ֶ���" width="0" dataIndex="col_code" edited="false" editor="text" align="left"  hidden="true"/>
							<odin:gridEditColumn header="�ֶ���" width="123" dataIndex="col_name" edited="false" editor="text" align="left" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
	</div>

	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 140">
						<odin:editgrid property="personListGrid3123123"  width="140" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="�����" width="0" dataIndex="code_value" edited="false" editor="text" align="left" hidden="true" />
							<odin:gridEditColumn header="���������" width="95" dataIndex="code_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid></td>
			<tr>
		</table>
	</div>
	<div class="div-inline">
		<table style="border: solid 0px !important">
			<tr>
				<td colspan="1" style="width: 140">
					<odin:editgrid property="personListGrid121" width="140" height="300" url="/">
						<odin:gridJsonDataModel root="data">
							<odin:gridDataCol name="code_value" />
							<odin:gridDataCol name="code_name" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="����" width="0" dataIndex="code_value" edited="false" editor="text" align="left" hidden="true" />
							<odin:gridEditColumn header="У�˺����б�" width="95" dataIndex="code_name" edited="false" editor="text" align="left" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
	</div >
	
	<div class="div-inline" style="width: 130px;height: 20px;">
		<table style="border: solid 0px !important;">
			<tr>
				<td colspan="1" style="width: 140px; margin-top:55px; height: 20px;">
				    <odin:groupBox title="ѡ�����" >
						<div id="tree-div" style="width: 130px;height: 20px;"></div>
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
	</td>
	</tr>
	</table>
</odin:groupBox>
</td>

</tr>
</table>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
<table>
	<tr>
		<td width="860" height="440">
			<iframe src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.infmtionquery.ResultQuery"  id="resultUpdate" name="resultUpdate"></iframe>
		</td>
	</tr>
	<tr>
		<td height="120">
			<odin:groupBox title="������Ϣ��ʾ" >
				<table>
					<tr>
						<odin:textarea property="vru005" cols="150" disabled="true" rows="5" />
					</tr>
				</table>
			</odin:groupBox>
		</td>
	</tr>
</table>
</odin:tabCont>
</odin:tab>
<!-- ��ǰ�������� -->
<odin:hidden property="qvid" />
<!-- ��ǰ�������� -->
<odin:hidden property="nowString" />
<!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="table_code" />
<!-- ��ǰѡ�е���Ϣ�� ����:A01.A0000 --> 
<odin:hidden property="col_code" />
<!-- ��ǰѡ�е���Ϣ������� --> 
<odin:hidden property="codetype" />
<script>
function setValue(){
	var str=document.getElementById('updateFlag').value;
	/* var resultListGrid=window.frames["resultUpdate"].document.getElementById("resultListGrid");
	.getStore().removeAll(); */
	/* if(str=='yes'){
		 */
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.infmtionquery.ResultQuery" ;
	//alert(document.getElementById('vru000').value);
	//alert(url);
	document.getElementById('resultUpdate').src=url+"&vru000="+document.getElementById('qvid').value;
	odin.ext.getCmp('tab').activate('tab2');
	/* } */
}
function queryResult(){
	radow.doEvent('setBtnQuery');
}

function rowDbClick1(){
	var getPosi = " "+document.getElementById('nowString').value+" ";
	var textarea = document.getElementById('vru000');
	var vru000 = document.getElementById('vru000').value;
	var userSelection = getCaret(textarea);
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