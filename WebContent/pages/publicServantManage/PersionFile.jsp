<%@page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %> 

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />

<style>

/*  #grid1 {
	width: 316px !important;
	
}
#grid2 {
	width: 316px !important;
	
}
#grid3 {
	width: 316px !important;
	
}
#grid4 {
	width: 316px !important;
	
}
#grid5 {
	width: 316px !important;
	
}
#grid6 {
	width: 316px !important;
	
}  */
</style>



<script type="text/javascript">

</script>

<odin:hidden property="id" title="id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="ids" title="��Ա��id"/>
<odin:hidden property="downfile" />
<odin:window src="/blank.htm" id="Information" width="300" height="250"
	title="�ල��Ϣҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="impWin" width="450" height="150" title="�����ϴ�"  closable="true"  modal="true" />



<table style="width:970;height:250">   
	<tr>
		<td width="380px" height="40px">		
<odin:toolBar property="ToolBar1">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;����</h1>" isLast="true"></odin:textForToolBar>
 
<%-- 	<odin:buttonForToolBar text="����" id="checkBtn" icon="image/icon021a2.gif" handler="check" isLast="true"/> --%>
</odin:toolBar>											
		<odin:grid property="grid1" autoFill="false" topBarId="ToolBar1"    url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="realurl" />
					<odin:gridDataCol name="filenumber" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>																
		</td>	
		<td width="380px" height="40px">		
<odin:toolBar property="ToolBar2">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;��������ȡ���</h1>" isLast="true"></odin:textForToolBar>
 <%--    <odin:fill /> --%>
	<%-- <odin:buttonForToolBar text="����" id="inspectBtn" icon="image/icon021a2.gif" handler="inspect" isLast="true"/> --%>
</odin:toolBar>											
		<odin:grid property="grid2" autoFill="false" topBarId="ToolBar2"  url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="realurl" />
					<odin:gridDataCol name="filenumber" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>																
		</td>	
		<td width="380px" height="40px">
<odin:toolBar property="ToolBar3">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;�����Ƽ�</h1>" isLast="true"></odin:textForToolBar>
    <%-- <odin:fill /> --%>
<%-- 	<odin:buttonForToolBar text="����" id="talkBtn" icon="image/icon021a2.gif" handler="talk" isLast="true"/> --%>
</odin:toolBar>
		<odin:grid property="grid3" autoFill="false" topBarId="ToolBar3"  url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="fileurl" />
					<odin:gridDataCol name="realurl" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>																
		</td>																							
	</tr>
	<tr>
	<td width="380px" height="40px" >		
<odin:toolBar property="ToolBar4">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;��֯����</h1>" isLast="true"></odin:textForToolBar>
 <%--    <odin:fill /> --%>
	<%-- <odin:buttonForToolBar text="����" id="reportBtn" icon="image/icon021a2.gif" handler="report" isLast="true"/> --%>
</odin:toolBar>												
		<odin:grid property="grid4" autoFill="false" topBarId="ToolBar4"  url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="fileurl" />
					<odin:gridDataCol name="realurl" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>																
		</td>
<td width="380px" height="40px" >		
<odin:toolBar property="ToolBar5">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;���۾���</h1>" isLast="true"></odin:textForToolBar>
<%--     <odin:fill /> --%>
	<%-- <odin:buttonForToolBar text="����" id="talkdecBtn" icon="image/icon021a2.gif" handler="report" isLast="true"/> --%>
</odin:toolBar>												
		<odin:grid property="grid5" autoFill="false" topBarId="ToolBar5"  url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="fileurl" />
					<odin:gridDataCol name="realurl" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>	 															
		</td>		
<td width="380px" height="40px" >		
<odin:toolBar property="ToolBar6">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;��ǰ��ʾ</h1>" isLast="true"></odin:textForToolBar>
 <%--    <odin:fill /> --%>
	<%-- <odin:buttonForToolBar text="����" id="talkdecBtn" icon="image/icon021a2.gif" handler="report" isLast="true"/> --%>
</odin:toolBar>												
		<odin:grid property="grid6" autoFill="false" topBarId="ToolBar6"  url="/">
			<odin:gridJsonDataModel id="jsa00" root="data">			         
					<odin:gridDataCol name="jsa00" />
					<odin:gridDataCol name="jsa06" />
					<odin:gridDataCol name="jsa04" />
					<odin:gridDataCol name="fileurl" />
					<odin:gridDataCol name="realurl" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="jsa00" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="�ϴ�ʱ��" align="center" edited="false" width="80" dataIndex="jsa06" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="jsa04" editor="text" renderer="file" />
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>	 															
		</td>


<%-- 	<td width="480px" height="40px">
<odin:toolBar property="ToolBar6">
	<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;��ǰ��ʾ</h1>"></odin:textForToolBar>
    <odin:fill />
	<odin:buttonForToolBar text="����" id="talkBtn2" icon="image/icon021a2.gif" handler="talk" isLast="true"/>
</odin:toolBar>												
		<odin:grid property="grid6" autoFill="false" topBarId="ToolBar6"  url="/">
			<odin:gridJsonDataModel id="id" root="data">			         
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="time" />
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="fileurl" />
					<odin:gridDataCol name="filenumber" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>					
					<odin:gridEditColumn header="ID" align="center" edited="false" width="45" dataIndex="id" editor="text"  hidden="true"/>				
					<odin:gridEditColumn header="����ʱ��" align="center" edited="false" width="80" dataIndex="time" editor="text" />
					<odin:gridEditColumn header="�ļ�" align="center" edited="false" width="200" dataIndex="fil" editor="text"  renderer="file"/>
					<odin:gridEditColumn2 width="40" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" />
			 </odin:gridColumnModel>
		</odin:grid>
	</td> --%>
	</tr>
	
	
	<tr>
		<td style="margin-left:30px" colspan="3" align="right">
		 	<odin:button  property="impro" text="��������" handler="impZip"></odin:button>
		</td>
	</tr>
</table>

<script type="text/javascript">


Ext.onReady(function() {
	
	//var viewSize = Ext.getBody().getViewSize();
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	Ext.getCmp("grid1").setHeight(height-320);
	Ext.getCmp("grid2").setHeight(height-320);
	Ext.getCmp("grid3").setHeight(height-320);
	Ext.getCmp("grid4").setHeight(height-320);
	Ext.getCmp("grid5").setHeight(height-320);
	Ext.getCmp("grid6").setHeight(height-320);
	
	Ext.getCmp("grid1").setWidth(width-740);
	Ext.getCmp("grid2").setWidth(width-740);
	Ext.getCmp("grid3").setWidth(width-740);
	Ext.getCmp("grid4").setWidth(width-740);
	Ext.getCmp("grid5").setWidth(width-740);
	Ext.getCmp("grid6").setWidth(width-740);
	
	Ext.getCmp("impro").setHeight(30);
	
	
	document.getElementById('a0000').value = parentParam.a0000;	
	//alert(document.getElementById('a0000').value);
});

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}

function impZip(){
	radow.doEvent("importZip");
}


function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var id = record.data.jsa00;	
	if(parent.buttonDisabled){
		return "ɾ��";
	} 
	return "<a href=\"javascript:deleteRow3(&quot;"+id+"&quot;)\">ɾ��</a>";
}

function deleteRow3(sid){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) {
		if("yes"==id){
			radow.doEvent('deleteRow3',sid);
		}else{
			return;
		}		
	});	
}

function deleteRow2(sid){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) {
		if("yes"==id){
			radow.doEvent('deleteRow',sid);
		}else{
			return;
		}		
	});	
}
//ԭ��
function file(value, params, rs, rowIndex, colIndex, ds){
	var name = rs.get('jsa04');
	var realurl =rs.get('realurl').replace(/\\/g,"/");
	if(name != null && name != ''){
		return "<a href=\"javascript:downloads1('" + realurl + "','"+name+"')\">"+name+"</a>";
	}
}
function downloads1(url,filename){
	window.location="YearCheckServlet?method=PersionFileNew&filename="+encodeURI(encodeURI(filename))+"&filePath="+encodeURI(encodeURI(url));
	
}

/*����*/
function downloads(url){
	window.location="YearCheckServlet?method=PersionFile&filePath="+encodeURI(encodeURI(url));
	
}
function inspect(){//ˢ��
	var a0000 = document.getElementById("a0000").value;	
	var para = a0000+"2";
	var g_contextpath = '<%= request.getContextPath() %>';
	$h.openPageModeWin('impWin','pages.publicServantManage.ImpWindow','�����ϴ�',450,250,{para:para},g_contextpath);
}


function check(){       //��������
	var a0000 = document.getElementById("a0000").value;	
	var para = a0000+"1";
	var g_contextpath = '<%= request.getContextPath() %>';
	$h.openPageModeWin('impWin','pages.publicServantManage.ImpWindow','�����ϴ�',450,250,{para:para},g_contextpath);
	
}

function talk(){  //�޸�
	var a0000 = document.getElementById("a0000").value;	
	var para = a0000+"3";
	var g_contextpath = '<%= request.getContextPath() %>';
	$h.openPageModeWin('impWin','pages.publicServantManage.ImpWindow','�����ϴ�',450,250,{para:para},g_contextpath);
}

function report(){  //ɾ��
	var a0000 = document.getElementById("a0000").value;	
	var para = a0000+"4";
	var g_contextpath = '<%= request.getContextPath() %>';
	$h.openPageModeWin('impWin','pages.publicServantManage.ImpWindow','�����ϴ�',450,250,{para:para},g_contextpath);
	
}

function open(){  
	var g_contextpath = '<%= request.getContextPath() %>';
	var id = document.getElementById('id').value;
	var a0000 = document.getElementById('a0000').value;
	
	var ids = id+"@@@"+a0000;
	
	$h.openPageModeWin('Information','pages.publicServantManage.Information','�ල��Ϣ',550,300,{rb_id:ids},g_contextpath);
}

function openinsert(){  
	var g_contextpath = '<%= request.getContextPath() %>';
	var a0000 = document.getElementById('a0000').value;
	

	$h.openPageModeWin('Information','pages.publicServantManage.Information','�ල��Ϣ',550,300,{rb_id:a0000},g_contextpath);
}



function restore(){
	odin.ext.getCmp('grid1').store.reload();
	odin.ext.getCmp('grid2').store.reload();
	odin.ext.getCmp('grid3').store.reload();
	odin.ext.getCmp('grid4').store.reload();
}
</script>

