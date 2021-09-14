<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<odin:toolBar property="toolBar8" applyTo="tol2">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" handler="addnew" icon="images/add.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="tol2" align="left"></div>
<table  style="width: 100%">
	<tr>
		<odin:NewDateEditTag property="a2947" isCheck="true" label="���빫��Ա����ʱ��" required="true" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a2949" isCheck="true" label="����Ա�Ǽ�ʱ��" required="true" maxlength="8"></odin:NewDateEditTag>
	</tr>
	
</table>
	<odin:grid property="RegisterInfoGrid" sm="row"  isFirstLoadData="false" url="/"
		 height="260"  >
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="n2900" />
			<odin:gridDataCol name="a2947" />
	   		<odin:gridDataCol name="a2949"/>
	   		<odin:gridDataCol name="delete" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
		  <odin:gridRowNumColumn />
		  <odin:gridColumn header="id" dataIndex="n2900" editor="text" hidden="true"/>
		  <odin:gridEditColumn2 header="���빫��Ա����ʱ��" align="center" dataIndex="a2947" editor="text" edited="false" width="250"/>
		  <odin:gridEditColumn2 header="����Ա�Ǽ�ʱ��" align="center" dataIndex="a2949" editor="text" edited="false" width="250"/>
		  <odin:gridEditColumn2 width="100" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" align="center"/>
		</odin:gridColumnModel>
	</odin:grid>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="n2900" title="�Ǽ�����"/>
<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("a0000").value = realParent.document.getElementById("a0000").value;
	
	
	var firstload = true;
	var pgrid = Ext.getCmp("RegisterInfoGrid");
	var dstore = pgrid.getStore();
	dstore.on({  
       load:{  
           fn:function(){  
           	 if(firstload){
           		  $h.selectGridRow('RegisterInfoGrid',0);
           		  firstload = false;
             }
           }      
       },  
       scope:this      
   });  
	
	
});
function saveTrain(){
	radow.doEvent('save.onclick');
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var n2900 = record.data.n2900;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+n2900+"&quot;)\">ɾ��</a>";
}
function deleteRow2(n2900){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',n2900);
		}else{
			return;
		}		
	});	
}

function addnew(){
	$('#n2900').val('');
	$('#a2947').val('');
	$('#a2947_1').val('');
	$('#a2949').val('');
	$('#a2949_1').val('');
}

function lockINFO(){
	Ext.getCmp("TrainingInfoAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("RegisterInfoGrid").getColumnModel().setHidden(4,true);
}

</script>



