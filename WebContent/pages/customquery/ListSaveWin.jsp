<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<table style="width: 480px;">
	<tr>
		<td>
			<odin:hidden property="cueRowIndex"/>
			<odin:hidden property="cqli"/>
			<odin:hidden property="sql"/>
		<td>
	</tr>
	<tr>
	
		<!-- ���νṹ 
		<td>
		<div id="tree-div" style="overflow: auto; height: 400px; width:536px; border: 2px solid #c3daf9;"></div>
	    <td> -->
	   
</tr>
<tr>
<td>
<table style="margin-top: 54px">
<tr>
<td>
<odin:textEdit  property="perName" width="100" value="��" maxlength="200" readonly="true" label="�ϼ��б�����"></odin:textEdit>
<odin:hidden property="perID" value="-1"/>
<odin:textEdit  property="listName" width="100"   maxlength="200" label="����"></odin:textEdit>
</td>
<td style="width:20px"></td>

<td align="left">
<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" property="addList"></odin:button>
<td style="width:20px"></td>
<%-- <td align="left">
<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" property="overWriteList"></odin:button>
<td style="width:20px"></td>
<td align="left">
<odin:button text="&nbsp;&nbsp;ɾ&nbsp;&nbsp;��&nbsp;&nbsp;"  handler="doDelRow"></odin:button> --%>
<td align="left">
<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;λ&nbsp;&nbsp;"  handler="doReset"></odin:button>
</td>
</tr>
</table>
<td>
</tr>
</table>
<script type="text/javascript">
//��ʼ����
Ext.onReady(function() {
	//initTree();
	
});
function initTree(){
	document.getElementById("tree-div").innerHTML="";
	var Tree = Ext.tree;
	 var tree1 = new Tree.TreePanel( {
  	  id:'group',
        el : 'tree-div',//Ŀ��div����
        split:false,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
        autoScroll : true,
        animate : true,
        border:false,
        enableDD : false,
        containerScroll : true,
        loader : new Tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.customquery.ListSaveWin&eventNames=PersionTreeJsonData'
        })
    });
	 
	 var root1 = new Tree.AsyncTreeNode( {
        text :  '���ϼ�',
        draggable : false,
        id :'-1',
       href:""
  });
  tree1.setRootNode(root1);
  tree1.render();
}
document.onkeydown=function() {
	if(event.keyCode == 27){	//����ESC
        return false;   
	}
};

function removePerson(value, params, rs, rowIndex, colIndex, ds) {
	return "<a href='#' onclick='doDelRow("+rowIndex+")'>ɾ��</a>";
}

function doDelRow(){
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�ϸ���Ϣ��������Ϣɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteList');
		}else{
			return;
		}		
	});	
}
function doReset(){
	radow.doEvent('resetPreName');
}
//��Ա�б����¼� 
function rowClickPeople(a,index){
	var gridcq = Ext.getCmp("listGrid");
	var gStore = gridcq.getStore();
	var listname = gStore.getAt(index).data.listname;
	document.getElementById("listName").value = listname;
}

/* function setCqli(){
	var gridcq = Ext.getCmp("listGrid");
	var gStore = gridcq.getStore();
	var index = document.getElementById("cueRowIndex").value;
	var cqli = gStore.getAt(index).data.cqli;
	document.getElementById("cqli").value = cqli;
} */

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}
</script>

