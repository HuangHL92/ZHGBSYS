<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style>
#bzyp{
	height:670px;
	overflow-y:auto;
	font-family:SimHei;
}
table td{
	border: 1px solid #DDD;
	padding-left:10px;
	
}
#table1 tr>td:first-child{
	background-color:#ADD8E6;	
}
#table2 tr>td:first-child{
	background-color:#ADD8E6;	
}

table{
	border-collapse:collapse
}

td input{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:20px; 
	width:100%;
	border:none;
}

#bzfx,#bdgb,#bjylxtr{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:120px; 
	width:100%;
	border:none;
}
#jbgk,#fzdw{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:100px; 
	width:100%;
	border:none;
}
#tpjy{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:120px; 
	width:100%;
	border:none;
}
#ztpj,#bzbz,#yhjy{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:100px; 
	width:100%;
	border:none;
	
}
#btnToolBarDiv td{
	border:1px;
}
#table3 td{
	border-collapse:collapse
}
td a{
	cursor:pointer;
}

</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<odin:hidden property="bz00" title="����id"></odin:hidden>
<odin:hidden property="tp00"/>
<odin:hidden property="checkedgroupid"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="a0101"/>
<%-- <odin:hidden property="a0000"/> --%>

<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="query_type"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>

<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
<%-- 				<odin:buttonForToolBar text="ɾ��" icon="images/back.gif" cls="x-btn-text-icon" id="delete" handler="deleteRow"></odin:buttonForToolBar>
				
				<odin:buttonForToolBar text="��������" id="saveAll" cls="x-btn-text-icon" icon="images/save.gif" ></odin:buttonForToolBar> --%>
				
				<odin:buttonForToolBar text="����" id="BZTPAddBtn" icon="images/add.gif" cls="x-btn-text-icon"  isLast="true"></odin:buttonForToolBar>
				
			<%-- 	<odin:buttonForToolBar text="����" id="save22" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar> --%>
</odin:toolBar> 

<div id="bzyp">
	<table width="960" id="table1" align="center" style="border: 1px solid #DDD;">
		<tr>
			<td width="120" height="60" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">����(��)</td>
			<td colspan=7 id="qxs" ></td>
		</tr>
		<tr>
			<td colspan=8 style="padding-left: 0">
				<table style="border: 1px solid #DDD;background-color:#FFFFFF"">
				<tr>
					<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">
					��������
					</td>
					<odin:textarea  property="ztpj" ></odin:textarea>
					<td width="120"  style="text-align:center;font-family:SimHei;background-color:#ADD8E6">
					���Ӳ���
					</td>
					<odin:textarea  property="bzbz" ></odin:textarea>
					<td width="120"  style="text-align:center;font-family:SimHei;background-color:#ADD8E6">
					�Ż�����
					</td>
					<odin:textarea  property="yhjy" ></odin:textarea>
					<td align="left" rowspan="3">
					<button onclick="saveFX()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:80px;height:30px;
    	cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">��&nbsp;&nbsp;��</button>
					</td>
					
				</tr>
		<tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;padding-left:0px;background-color:#ADD8E6">�����ſ�</td>
			<odin:textarea  property="jbgk" colspan="7" ></odin:textarea>

		</tr>
		<tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;padding-left:0px;background-color:#ADD8E6">��չ��λ</td>
			<odin:textarea  property="fzdw" colspan="7" ></odin:textarea>

		</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">ְ���䱸���</td>
			<td colspan=3 id="zspb" width="360"></td>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6;">�ṹ�͸ɲ��䱸���</td>
			<td colspan=3 id="jgxgbpb" width="360"></td>
		</tr>
		<tr>
			<td width="120" rowspan=3 height="150" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">��������<br/>����ͷ���ɲ�</td>
			<td height="50" colspan=2>��Գ�����йܸɲ�</td>
			<td colspan=5 id="xdcs" width=""></td>
		</tr>
		<tr>
			<td height="50" colspan=2 style="background-color:#FFFFFF">�����Ƚ�������йܸɲ�</td>
			<td colspan=5 id="bjyx" width=""></td>
		</tr>
		<tr>
			<td height="50" colspan=2 style="background-color:#FFFFFF">�������һ����йܸɲ�</td>
			<td colspan=5 id="fxyb" width=""></td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�����������</td>
			<odin:textarea  property="bzfx" colspan="7" ></odin:textarea>
			<!-- <td colspan=7 id="bzypjyb">
				<textarea id="bzypjy" name="bzypjy" ></textarea>
			</td> -->
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�����䱸����</td>
			<odin:textarea  property="tpjy" colspan="7" ></odin:textarea>
			<!-- <td colspan=7 id="bzypjyb">
				<textarea id="bzypjy" name="bzypjy" ></textarea>
			</td> -->
		</tr>
		<%-- <tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;padding-left:0px;background-color:#ADD8E6">���ظɲ�</td>
			<odin:textarea  property="bdgb" colspan="3" ondblclick="BDGBClick()" readonly="true"></odin:textarea>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">
					��������������
			</td>
			<odin:textarea  property="bjylxtr" colspan="4" ondblclick="BJYLXTRClick()" readonly="true"></odin:textarea>
		</tr> --%>
	</table>
		<div id="ButtonDiv" style="border:none;text-align:right;padding-right:5px">
		<button onclick="saveFX()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:120px;height:30px;
    	cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">������ӷ���</button>
		</div>
		
	<table width="960" align="center" id="table2">
		

		<tr>
			<td rowspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���˲������</td>
			<td colspan=2 height="40" style="text-align:center;font-family:SimHei;">�������</td>
			<odin:select2 property="year"  onchange="yearQuery()"  value="0" style="width:100%;border-right:none"></odin:select2>
			<td colspan=3 style="border-left:none"></td>
		</tr>
		<tr>
			<td rowspan=2 colspan=2  height=50 style="text-align:center;font-family:SimHei;background-color:#FFFFFF">���������������ۡ����㡱��</td>
			<td colspan=2 style="text-align:center;font-family:SimHei;">�ۺϿ������</td>
			<td colspan=2 style="text-align:center;font-family:SimHei;">�󵳽�����</td>
			<td rowspan=2  style="text-align:center;font-family:SimHei;">��ȿ������</td>
		</tr>
		<tr>
			<td height=25 style="text-align:center;font-family:SimHei;background-color:#FFFFFF">���յ÷�</td>
			<td style="text-align:center;font-family:SimHei;">�����ȴ�</td>
			<td style="text-align:center;font-family:SimHei;">�����ܷ�</td>
			<td style="text-align:center;font-family:SimHei;">����</td>
		</tr>
		<tr>
			<td style="font-family:SimHei;background-color:#FFFFFF;text-align:center;">��ί</td>
			<td><input id="dwyxl" /></td>
			<td rowspan=4><input id="zzdf" /></td>
			<td rowspan=4><input id="kpdc" /></td>
			<td rowspan=4><input id="khzf" /></td>
			<td rowspan=4><input id="pm" /></td>
			<td rowspan=4><input id="ndkhqk" /></td>
		</tr>
		<tr>
			<td style="text-align:center;font-family:SimHei;background-color:#FFFFFF">�˴�</td>
			<td><input id="rdyxl" /></td>
		</tr>
		<tr>
			<td style="text-align:center;font-family:SimHei;background-color:#FFFFFF">����</td>
			<td><input id="zfyxl" /></td>
		</tr>
		<tr>
			<td style="text-align:center;font-family:SimHei;background-color:#FFFFFF">��Э</td>
			<td width=50><input id="zxyxl" /></td>
		</tr>
	</table>
	<div id="ButtonDiv" style="border:none;text-align:right;padding-right:5px">
		<button onclick="saveNDKH()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:120px;height:30px;
    	cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">���濼�˽��</button>
	</div>
	<table align="center" width=960 id="table3">
	<tr>
			<td  height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���佨��</td>
	</tr>
			<tr>
			<td style="text-align:left;padding-left:0px;">
				<odin:grid property="BZTPGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="250" width="960">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="tp00" />
					<odin:gridDataCol name="tpgw" />
			   		<odin:gridDataCol name="tpry"/>
			   		<odin:gridDataCol name="tptj" />			   		
 			   		<odin:gridDataCol name="delete" isLast="true"/>		   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="tp00" editor="text" hidden="true" />
				  <odin:gridEditColumn header="�����λ" dataIndex="tpgw" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="��Ա"  dataIndex="tpry" edited="false" editor="text"   />
				  <odin:gridEditColumn header="����" dataIndex="tptj" edited="false" editor="text"/>
				  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/> 
				</odin:gridColumnModel>
			</odin:grid>	
			</td>
			</tr>
	</table>
	<div id="btnToolBarDiv"></div>
</div>


<%-- <odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" handler="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar> --%>

<script type="text/javascript">
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	/* document.getElementById("checkedgroupid").value=parent.Ext.getCmp(subWinId).initialConfig.checkedgroupid; */
	document.getElementById("checkedgroupid").value=parent.document.getElementById('checkedgroupid').value;
/* 	$('#lyjg').bind('click',function(){
		alert("aaa");
	})  */
/* 	document.getElementById('test').onclick=function(){
	alert('aaaa');
	} */
/* 	document.getElementById("checkedgroupid").value="testAAA" */

	
});

function saveFX(){

	
	radow.doEvent('saveFX');
}

function saveNDKH(){
	
	radow.doEvent('saveNDKH');
}

function yearQuery(){
	radow.doEvent('yearQuery');
}

function openBZTP(){
	var tp00=document.getElementById('tp00').value;
	$h.openPageModeWin('BZTP','pages.fxyp.BZTPAddPage','�����иɲ�����',900,430,document.getElementById('tp00').value,ctxPath,null,{maximizable:false,resizable:false});
}

/* function noticeSetgrid(){
	document.elementFromPoint(event.clientX,event.clientY);
	document.elementFromPoint(event.clientX,event.clientY);
	document.getElementById("a0101").value= document.elementFromPoint(event.clientX,event.clientY).text; 
	document.getElementById("a0101").value= document.elementFromPoint(event.clientX,event.clientY).text; 
    //���е� obj.tagName=="A" ��ʾ��ȡ��ǰ�ı�ǩ��a��ǩ
	alert(document.getElementById("a0101").value);
    radow.doEvent('noticeSetgrid');
}
 */
function openrmb(){
	$('a').bind('click',function(){
		document.getElementById("a0101").value=this.innerHTML;
		radow.doEvent('openrmb');
	}) 
	
	

  /*   $('#zzcx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 4;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       });
	
	$('#fzcx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 6;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       });
	
	$('#zscx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 8;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       }); */
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var tp00 = record.data.tp00;
	/* if(realParent.buttonDisabled){
		return "ɾ��";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+tp00+"&quot;)\">ɾ��</a>";
}
/* function BDGBClick(){
	 var checkedgroupid=document.getElementById("checkedgroupid").value;
	 $h.openPageModeWin('addperson1','pages.fxyp.BmChoose','������Ա',560,435,checkedgroupid,ctxPath);
	 //$h.openPageModeWin('Candidate','pages.fxyp.QxypCandidate&initParams='+id,'�����Ƽ���Ա',1200,600,'',ctxPath,window);	 
} */
/* function BJYLXTRClick(){
	 var checkedgroupid=document.getElementById("checkedgroupid").value;
	 $h.openPageModeWin('addperson','pages.fxyp.BZYPChoose','������Ա',1020,520,checkedgroupid,ctxPath);
	 //$h.openPageModeWin('Candidate','pages.fxyp.QxypCandidate&initParams='+id,'�����Ƽ���Ա',1200,600,'',ctxPath,window);	 
} */

function queryPerson(){
	radow.doEvent('init2');
}
function deleteRow2(tp00){ 
	/* Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',tp00);
		}else{
			return;
		}		
	});	 */
	radow.doEvent('deleteRow',tp00);
}



</script>

	