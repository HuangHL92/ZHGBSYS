<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.ExcelViewOfficePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<odin:head></odin:head>
<odin:base></odin:base>
<odin:hidden property="downfile" />

<div id="border"> 
<div id="tol2" align="left"></div>
<odin:toolBar property="ToolBar">
			<odin:fill />
			<odin:buttonForToolBar text="����" id="btn1" handler="up"/>
			<odin:buttonForToolBar text="����" id="btn2" handler="down"/>
			<odin:buttonForToolBar text="ȷ��" id="btn3" handler="Save" isLast="true"/>			
		</odin:toolBar>
			<odin:grid property="TrainingInfoGrid" height="500" title="" autoFill="true" topBarId="ToolBar" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="200"  url="/" rowDbClick="rowDbClick">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id"  />
					<odin:gridDataCol name="type"  />
					<odin:gridDataCol name="filename" />
			  		<odin:gridDataCol name="creattime"  />
			  		<odin:gridDataCol name="selty"  /> 
			  		<odin:gridDataCol name="updatetime" isLast="true"/>
<%-- 			  		<odin:gridDataCol name="realpath"/> --%>
<%-- 			   		<odin:gridDataCol name="type" isLast="true"/>		   		 --%>
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="����" dataIndex="id" editor="text" edited="false" width="100" hidden="true"/>
				  	<odin:gridEditColumn2 header="��ʶ" dataIndex="type" editor="text" edited="false" width="100" hidden="true"/>
				  	<odin:gridEditColumn2 header="��������" align="center" dataIndex="filename" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="����ʱ��" align="center" dataIndex="creattime" editor="text" edited="false"  width="100" />
				  	<odin:gridEditColumn2 header="�޸�ʱ��" align="center" dataIndex="updatetime" editor="text" edited="false" width="100"/>
				  	<odin:gridEditColumn2 header="�Ƿ�ͨ�ñ�ʶ" align="center" dataIndex="selty" editor="text" edited="true" width="100" hidden="true"/> 
<%-- 				  	<odin:gridEditColumn2  hidden="true" width="200" header="�ļ�·��" dataIndex="realpath" editor="text" edited="false"  /> --%>
<%-- 				  	<odin:gridEditColumn2 hidden="true" width="200" header="��ʶ" dataIndex="type" editor="text" edited="false"  /> --%>
				  	<odin:gridEditColumn2 width="65" header="����" dataIndex="delet" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
										
			</odin:grid>
			
			<%-- <table>
				<tr align="center"><!-- align="center" -->
					<td  ><odin:button text="����" property="btn1" handler="up"></odin:button></td>
					<td ><odin:button text="����"  property="btn2" handler="down"></odin:button></td>
					<td ><odin:button text="����"  property="btn3" handler="Save"></odin:button></td>
				</tr>
            </table> --%>
			
</div> 

<script type="text/javascript">
 Ext.onReady(function(){
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	Ext.getCmp("TrainingInfoGrid").setHeight(height);
	Ext.getCmp("TrainingInfoGrid").setWidth(width);
}); 

function rowDbClick(grid,rowIndex,event){/* ��ȡ�ļ�����,�ش�����ҳ�� */
	var gridId = "TrainingInfoGrid";
	var grid = Ext.getCmp(gridId);
	var record = grid.getStore().getAt(rowIndex);   //��ȡ��ǰ�е�����
	var type = record.data.type;
	var id = record.data.id;
	var selty = record.data.selty;
	var typeid = type+","+id+","+selty;
	//window.opener.document.getElementById("filename").value = record.data.id; 
	window.opener.document.getElementById("filename").value = typeid; 
	window.close();
}



function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var id = record.data.id;
	var filename = record.data.filename;
	var fileid = id+","+filename;
	/* if(realParent.buttonDisabled){
		return "ɾ��";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+id+"&quot;)\">ɾ��</a>&nbsp&nbsp&nbsp&nbsp<a href=\"javascript:expword(&quot;"+fileid+"&quot;)\">����</a>";
}
function deleteRow2(template){ 
	
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(ida) { 
			if("yes"==ida){
				radow.doEvent('deleteRow',template);
			}else{
				return;
			}		
		});	
	} 
 
function expword(fileid){
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ����ظ�ģ��",function(ida) { 
		if("yes"==ida){
			radow.doEvent("expword",fileid);
		}else{
			return;;
		}		
	});	 
}
 
function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	//ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function cc(){
	
}
function reload(){
	odin.reset();
}
function Save(){
	radow.doEvent("Save");
}

function up(){
	//radow.doEvent("up");
    var grid = odin.ext.getCmp('TrainingInfoGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('��ѡ����Ҫģ��!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�����ģ��!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('��ģ���Ѿ��������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}

function down(){
	//radow.doEvent("down");
var grid = odin.ext.getCmp('TrainingInfoGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ�����ģ��!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�����ģ��!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('��ģ���Ѿ����������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}
</script>
<style>

</style>
