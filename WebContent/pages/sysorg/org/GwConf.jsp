<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#grid1 {
	width: 100% !important;
}
.cellyellow{
background: yellow !important;
background-color: yellow !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">
</script>
<%@include file="/comOpenWinInit.jsp" %>	

<div id="btnToolBarDiv" style="width:100%;"></div>		
<div id="div_data" style="width:100%;">								
		<odin:editgrid property="grid1" autoFill="true" forceNoScroll="true" pageSize="100"
			topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
			        <odin:gridDataCol name="colcheck" /> 
					<odin:gridDataCol name="b0111" />					
					<odin:gridDataCol name="gwcode" />
					<odin:gridDataCol name="gwname" />
					<odin:gridDataCol name="zjcode" />
					<odin:gridDataCol name="zwcode" />
					<odin:gridDataCol name="gwnum" />
					<odin:gridDataCol name="countnum" />
					<odin:gridDataCol name="jggwconfid" isLast="true" />

			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="selectall" width="10" editor="checkbox" 
						dataIndex="colcheck" edited="true" gridName="grid1"/>
					<odin:gridEditColumn header="����ID" align="center" edited="false" width="100" dataIndex="b0111" editor="text" hidden="true"/>
					<odin:gridEditColumn2 header="ְλ����" align="center" edited="false" width="150" dataIndex="gwcode" editor="select" codeType="GWGLLB"/>
					<odin:gridEditColumn header="ְλ����" align="center" edited="false" width="150" dataIndex="gwnum" editor="text" />
					<odin:gridEditColumn header="ʵ������" align="center" renderer="countrenderer" edited="false" width="150" dataIndex="countnum" editor="text" />
					<odin:gridEditColumn2 header="��Ӧְ����" align="center" edited="false" width="150" dataIndex="zwcode" editor="select" isLast="true" codeType="ZB09"/>
					<%-- <odin:gridEditColumn header="����" align="center" edited="false" width="370" dataIndex="jggwconfid" editor="text" isLast="true" renderer="file"/> --%>
			 </odin:gridColumnModel>
		</odin:editgrid>																
</div>
	
<odin:toolBar property="ToolBar" applyTo="btnToolBarDiv">
    <odin:fill />
    <odin:buttonForToolBar text="ˢ��" id="refresh" icon="images/icon/table.gif" handler="refresh"/>
	<odin:separator />
	<odin:buttonForToolBar text="����" id="insert" icon="image/icon021a2.gif" handler="insert"/>
	<odin:separator />
	<odin:buttonForToolBar text="�޸�" id="update" icon="image/icon021a6.gif" handler="update"/>
	<odin:separator />
	<odin:buttonForToolBar text="ɾ��" id="deleteBtn" icon="image/icon021a3.gif" handler="deleteBtn" isLast="true"/>
</odin:toolBar>

<odin:hidden property="ids" title="���id"/>
<odin:window src="/blank.htm" id="Check" width="600" height="250"
	title="����ҳ��" modal="true"></odin:window>
<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("grid1").setHeight(height-height_top);
	Ext.getCmp("grid1").setWidth(width);
}
Ext.onReady(function() {	
	window.onresize=setWidthHeight;
	setWidthHeight();
	
});
function refresh(){//ˢ��
	
	radow.doEvent("refresh");
}

function insert(){//����
	
	radow.doEvent("insert");
}

function update(){  //�޸�
	
	
	radow.doEvent("update");
}

function deleteBtn(){  //ɾ��
   			
	radow.doEvent('deleteBtn');
	
	
}


function countrenderer(value, params, rs, rowIndex, colIndex, ds){
	value = value==null|| value==''?'0' :value;
	if(parseInt(value)>parseInt(rs.get('gwnum'))){
		params.css='cellyellow';
		return "<span style='color:red;font-weight:bold;'>"+value+"</span>";
	} else {
		return value;
	}
}
//����ԭ��
function file(value, params, rs, rowIndex, colIndex, ds){
	var url = rs.get('fileurl');
	var name = rs.get('filename');
	
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloads('" + url + "')\">"+name+"</a>";
	} 
	
	
	 if(name != null && name != ''){
	
		return "<a href=\"javascript:downloads('" + url + "')\">"+name+"</a>";
	} 	
} 
/*����*/
function downloads(url){
	window.location="YearCheckServlet?method=YearCheckFile&filePath="+encodeURI(encodeURI(url));
}
</script>

