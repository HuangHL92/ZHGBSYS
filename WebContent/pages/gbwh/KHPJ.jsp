<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<div id="gbkhPanel"></div>
<%@include file="/comOpenWinInit.jsp" %>
<style>
.uploadify{
position: absolute;
left: 0px;
top: 0px;
}
div{
	overflow-x: hidden;
	overflow-y:auto;
}
#div_data1{
	height:260px;
	
}
.x-grid3-row-table td
{  
    vertical-align: middle;
    text-align: center;  
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<div id="div_data1">
<odin:editgrid2 property="gbkhGrid" hasRightMenu="false" title="" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="gbkhid" />
		<odin:gridDataCol name="year"/>
		<odin:gridDataCol name="grade"/>
		<odin:gridDataCol name="pat00s"/>
		<odin:gridDataCol name="pat04s"/>
		<odin:gridDataCol name="filename"/>
		<odin:gridDataCol name="filedata"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="gbkhid" width="110" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="year" width="90" header="���" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="grade" width="120" header="��Ȳ���������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="pat00s" width="140" header="����id" hidden="true" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="pat04s" width="140" header="��������" hidden="true" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="filedata" width="280" header="����ܽ�" editor="text" edited="false" align="center"  renderer="HandleFile" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</div>
<odin:toolBar property="btnToolBar" applyTo="gbkhPanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�������" icon="image/icon021a2.gif" id="loadadd" handler="loadadd"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif"  id="infoUpdate" handler="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����ܽ��ϴ�" icon="images/search.gif" id="fileload" isLast="true"/>
</odin:toolBar>
<tags:JUploadBtn property="fileload2" label="" fileTypeDesc="�����ļ�"  colspan="5" applyTo="fileload"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.xls;*.xlsx;*.docx;*.doc" labelTdcls="titleTd" onSelect="uploadfile"/>
<odin:hidden property="a0000"/>
<odin:hidden property="gbkh_id"/>
<div id="btnToolBarDiv1"></div>	
<div id="div_data1">								
		<odin:grid property="grid1" autoFill="false" topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
			        <odin:gridDataCol name="personcheck" /> 
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="a0000" />					
					<odin:gridDataCol name="checktime" />
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="fileurl" isLast="true" />

			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="50" editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid"/>
					<odin:gridEditColumn header="����ID" align="center" edited="false" width="100" dataIndex="id" editor="text" hidden="true"/>
					<odin:gridEditColumn header="��ԱID" align="center" edited="false" width="100" dataIndex="a0000" editor="text" hidden="true"/>
					<odin:gridEditColumn header="�������ʱ��" align="center" edited="false" width="350" dataIndex="checktime" editor="text" />
					<odin:gridEditColumn header="�ļ�����" align="center" edited="false" width="150" dataIndex="filename" editor="text" hidden="true"/>
					<odin:gridEditColumn header="�������" align="center" edited="false" width="630" dataIndex="fil" editor="text" isLast="true" renderer="file"/>
			 </odin:gridColumnModel>
		</odin:grid>																
</div>
<odin:toolBar property="ToolBar" applyTo="btnToolBarDiv1">
    <odin:fill />
<%--     <odin:buttonForToolBar text="ˢ��" id="refresh" icon="images/icon/table.gif" handler="refresh1"/>
	<odin:separator /> --%>
	<odin:buttonForToolBar text="�����������" id="insert" icon="image/icon021a2.gif" handler="insert1"/>
	<odin:separator />
	<odin:buttonForToolBar text="�޸Ŀ������" id="update" icon="image/icon021a6.gif" handler="update1"/>
	<odin:separator />
	<odin:buttonForToolBar text="ɾ���������" id="deleteBtn" icon="image/icon021a3.gif" handler="deleteBtn1" isLast="true"/>
</odin:toolBar>
<odin:hidden property="ids" title="���id"/>

<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function loadadd(){
	$h.openPageModeWin('loadadd','pages.gbwh.GBKHAdd','����',250,200,{gbkh_id:''},g_contextpath);
}
function infoUpdate(){
	var gbkh_id = document.getElementById('gbkh_id').value;

	if(gbkh_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����ݣ�');
		return;
	}
/* 	alert(gbkh_id); */
	$h.openPageModeWin('loadadd','pages.gbwh.GBKHAdd','�޸�',250,200,{gbkh_id:gbkh_id},g_contextpath);
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var gbkhGrid = Ext.getCmp('gbkhGrid');
	gbkhGrid.setHeight(viewSize.height-28);
	gbkhGrid.setWidth(viewSize.width);
	var btnToolBar = Ext.getCmp('btnToolBar');
	btnToolBar.setWidth(viewSize.width);
	document.getElementById('a0000').value = document.getElementById('subWinIdBussessId2').value;
	gbkhGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('gbkh_id').value = rc.data.gbkhid;
	});
	
	$h.initGridSort('gbkhGrid',function(g){
		radow.doEvent('gbkhsort');
	});
});

function uploadfile(){
	var gbkh_id = document.getElementById('gbkh_id').value;
	if(gbkh_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����ݣ�');
		return;
	}
	$('#fileload2').uploadify('upload','*');
/* 	document.getElementById('gbkh_id').value='' */
	//reload();
	//alert();
}
function reload(){
	radow.doEvent('gbkhGrid.dogridquery');
}
function HandleFile(value, params, record, rowIndex, colIndex, ds){
	if(record.get("pat00s")==null||record.get("pat00s")==""){
		return;
	}
	//ֻ��һ����Ϣʱ
	if(record.get("pat00s").indexOf(",")==-1){
		result = "<table width='100%'><tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+record.get("pat00s")+"');\">"+record.get("pat04s")+"</a></font></td><td align='left'><u style=\"color:#FFFFFF\">����</u>&nbsp;&nbsp;&nbsp;<u style=\"color:#FFFFFF\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+record.get("pat00s")+"')\">ɾ��</a></td></tr></table>";
		return result;
	}
	var pat00s = record.get("pat00s").split(",");
	var pat04s = record.get("pat04s").split(",");
	var result = "<table width='100%'>";
	for(var i=0;i<pat00s.length;i++){
		/* result = result+"<div align='center' width='100%' ><font color=blue>"
		+ "<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a>"
		+ "</font></div><br>"; */
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		if(i==0){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><u style=\"color:#D3D3D3\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></td></tr>";
			continue;
		}
		if(i==pat00s.length-1){
			result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<u style=\"color:#D3D3D3\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></td></tr>";
			continue;
		}
		
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		result = result+"<tr><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a></td></tr>";
	}
	/* result = result.substring(0,result.length-4); */
	result = result+"</table>"
	return result;
}
function outfile(pat00){
	var url = g_contextpath+'/PublishFileServlet?method=gbkh_att&pat00='+pat00;
	window.location.href=url;
}

function topordown(pat00,type){
	radow.doEvent('sort',pat00+"@"+type);
}

function deleteAtt(pat00){
	radow.doEvent('deleteAtt',pat00);
}

function onQueueComplete(){
	radow.doEvent('gbkhGrid.dogridquery');
}
</script>


<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv1").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv1").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv1").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv1").style.width=width+'px';
	Ext.getCmp("grid1").setHeight(height-height_top);
	Ext.getCmp("grid1").setWidth(width);
}
Ext.onReady(function() {	
	window.onresize=setWidthHeight;
	setWidthHeight();
	
});
function refresh1(){//ˢ��
	
	radow.doEvent("refresh");
}

function insert1(){//����
	
	radow.doEvent("insert");
}

function update1(){  //�޸�
	
	
	radow.doEvent("update");
}

function deleteBtn1(){  //ɾ��
   			
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
