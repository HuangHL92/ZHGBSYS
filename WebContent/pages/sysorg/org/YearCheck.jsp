<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#grid1 {
	width: 316px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">
</script>
<%@include file="/comOpenWinInit.jsp" %>	

<div id="btnToolBarDiv" style="width:955px;"></div>		
<div id="div_data">								
		<odin:grid property="grid1" autoFill="false" topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
			        <odin:gridDataCol name="personcheck" /> 
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="b0111" />					
					<odin:gridDataCol name="checktime" />
					<odin:gridDataCol name="checkyear" />
					<odin:gridDataCol name="checkfile" />
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="fileurl" isLast="true" />

			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid"/>
					<odin:gridEditColumn header="����ID" align="center" edited="false" width="100" dataIndex="id" editor="text" hidden="true"/>
					<odin:gridEditColumn header="����ID" align="center" edited="false" width="100" dataIndex="b0111" editor="text" hidden="true"/>
					<odin:gridEditColumn header="����ʱ��" align="center" edited="false" width="150" dataIndex="checktime" editor="text" />
					<odin:gridEditColumn header="�������" align="center" edited="false" width="150" dataIndex="checkyear" editor="text" />
					<odin:gridEditColumn header="�ļ�id" align="center" edited="false" width="150" dataIndex="checkfile" editor="text" hidden="true"/>
					<odin:gridEditColumn header="�ļ�����" align="center" edited="false" width="150" dataIndex="filename" editor="text" hidden="true"/>
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="370" dataIndex="fil" editor="text" isLast="true" renderer="file"/>
			 </odin:gridColumnModel>
		</odin:grid>																
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

