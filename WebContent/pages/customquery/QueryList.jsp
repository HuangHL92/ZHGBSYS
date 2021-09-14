<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<table style="width: 480px;">
	<tr>
		<td>
			<odin:hidden property="cueRowIndex"/>
			<odin:hidden property="sql"/>
		<td>
	</tr>
	<tr>
		<td style="width:480px">
				<div id="tree-div" style="overflow: auto; height: 400px; width:536px; border: 2px solid #c3daf9;"></div>
		</td>
	</tr>
	<tr>
		<td>
			<table align="center">
				<tr>
					<td>
						<odin:hidden property="perID" value="-1" />
						<odin:button text="����ѡ���б�" property="loadListNew"></odin:button>
					</td>
					<td style="width:40px"></td>
					<td>
						<odin:button text="&nbsp;&nbsp;ɾ&nbsp;&nbsp;��&nbsp;&nbsp;"  handler="doDelRow"></odin:button>
					</td>
				</tr>
			</table>
		<td>
	</tr>
</table>
<script type="text/javascript">

Ext.onReady(function() {
	initTree();
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

function removePerson(value, params, rs, rowIndex, colIndex, ds) {
	return "<a href='#' onclick='doDelRow("+rowIndex+")'>ɾ��</a>";
}

function doDelRow(rowIndex){
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�ϸ���Ϣ��������Ϣɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteList');
		}else{
			return;
		}		
	});	
}

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}
</script>