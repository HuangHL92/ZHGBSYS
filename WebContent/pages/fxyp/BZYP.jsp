<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style>
#bzyp{
	font-family:SimHei;
	height:670px;
	overflow-y:auto;
}
table td{
	border: 1px solid #DDD;
	padding-left:10px;
}
table{
	border-collapse:collapse
}
/* tr>td:first-child{
	background-color:#ADD8E6;	
} */
#btnToolBarDiv td{
	border:1px;
}
tr textarea{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:100px; 
	width:100%;
	border:none;
}
td input{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:60px; 
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

#trqk,#jbgk,#fzdw,#bdgb,#bjylxtr{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:100px; 
	width:100%;
	border:none;
}
#bzfx{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:180px; 
	width:100%;
	border:none;
}
#bzypjy{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:100px; 
	width:100%;
	border:none;
}
#zxkh{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:120px; 
	width:100%;
	border:none;
}
td a{
	cursor:pointer;
}
td u{
	cursor:pointer;
}

</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<odin:hidden property="bz00" title="����id"></odin:hidden>
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
<odin:hidden property="tp00"/>
<odin:hidden property="nljgyj"/>
<odin:hidden property="lyjgyj"/>
<odin:hidden property="zyjgyj"/>

<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
<%-- 				<odin:buttonForToolBar text="ɾ��" icon="images/back.gif" cls="x-btn-text-icon" id="delete" handler="deleteRow"></odin:buttonForToolBar>
				
				<odin:buttonForToolBar text="��������" id="saveAll" cls="x-btn-text-icon" icon="images/save.gif" ></odin:buttonForToolBar> --%>
				
				<odin:buttonForToolBar text="����" id="BZTPAddBtn" icon="images/add.gif" cls="x-btn-text-icon"  isLast="true"></odin:buttonForToolBar>
				
			<%-- 	<odin:buttonForToolBar text="����" id="save22" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar> --%>
</odin:toolBar> 

<div id="bzyp">
	<table width="960" align="center" style="border: 1px solid #DDD;">
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">��λ</td>
			<td colspan=7 id="dwm" ></td>
		</tr>
		<tr>
			<td colspan=8 style="padding-left: 0">
				<table >
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
					<odin:textarea  property="yhjy"></odin:textarea>
					<td align="left"  rowspan="3">
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
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�˶�ְ��</td>
			<td colspan=7  id="hdzs"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">ʵ��ְ��</td>
			<td colspan=7 id="spzs"></td>
		</tr>
		<tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;padding-left:0px;background-color:#ADD8E6">2018�����������йܸɲ����</td>
			<odin:textarea  property="trqk" colspan="7" ></odin:textarea>
			<!-- <td colspan=7 id="bzypjyb">
				<textarea id="bzypjy" name="bzypjy" ></textarea>
			</td> -->
		</tr>
		<%-- <tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;padding-left:0px;background-color:#ADD8E6">���ظɲ�</td>
			<odin:textarea  property="bdgb" colspan="2.9" ondblclick="BDGBClick()" readonly="true"></odin:textarea>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">
					��������������
			</td>
			<odin:textarea  property="bjylxtr" colspan="4.1" ondblclick="BJYLXTRClick()" readonly="true"></odin:textarea>
		</tr> --%>

		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#FFFFFF">��ȿ��˽��</td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�������</td>
			<odin:select2 property="year"  onchange="yearQuery()"   style="width:100%"></odin:select2>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;">���˽��</td>
			<odin:select2 property="ndkhjg" value="0" data="['1','����'],['2','����'],['3','һ��'],['4','�ϲ�']" ></odin:select2>
			<td style="text-align:center"><button type="button" onclick="saveNDKH()" style="border-radius:5px;background-color: #00CACA;border: none;
			cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;">���˽������</button></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6"> ��ȿ���<br/>�������</td>
			<td colspan=7 >
				<input id="ndyxgr" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#FFFFFF">ר��˽��</td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">ר���<br/>�������</td>
			<td colspan="7">
			<textarea id="zxkh" name="zxkh"  cols="7" readonly="readonly" 
			ondblclick="ZXKHClick()" ></textarea>
			</td>
		</tr>
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#FFFFFF">���ӽṹ����</td>
		</tr>
		<tr>
			<td width="120" height="60" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">����ṹ</td>
			<td colspan=7 id="nljg"></td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">��Դ�ṹ</td>
			<td colspan=7 id="lyjg"></td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">רҵ�ṹ</td>
			<td colspan=7 id="zyjg"></td>
		</tr>
		<tr>
			<td width="120" height="60" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�ṹ�Ըɲ�</td>
			<td colspan=7 id="jgxgb"></td>
		</tr>
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#FFFFFF">�ص��ע�ɲ�</td>
		</tr>
		<!-- <tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���������<br/>�й���ְ</td>
			<td colspan=7 id="bxyxsgzz"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ڿ�����<br/>�й���ְ</td>
			<td colspan=7 id="jqtrsgzz"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���������<br/>�йܸ�ְ</td>
			<td colspan=7 id="bxyxsgfz"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ڿ�����<br/>�йܸ�ְ</td>
			<td colspan=7 id="jqtrsgfz"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">������������<br/>�ɲ���������</td>
			<td colspan=7 id="yxnqgb"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ע��ְ</td>
			<td colspan=7 id="xgzzz"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ע��ְ</td>
			<td colspan=7 id="xgzfz"></td>
		</tr> -->
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ڿɽ�һ��<br/>ʹ����ѡ</td>
			<td colspan=7 id="jqkjybsyrx"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���������<br/>����ɲ�</td>
			<td colspan=7 id="bxyxddwgb"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���������<br/>Ů�ɲ�</td>
			<td colspan=7 id="bxyxdvgb"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���������<br/>����ɲ�</td>
			<td colspan=7 id="bxyxdnqgb"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">ʡ��������ɲ�</td>
			<td colspan=7 id="syxnqgb"></td>
		</tr>
		<tr>
			<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">����������ɲ�</td>
			<td colspan=7 id="snqgb"></td>
		</tr>
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#FFFFFF">���ӷ���</td>
		</tr>
		<tr>
		<td width="120" height="40" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">����רҵ��</td>
		<odin:select2 property="zyxbm"  onchange="zyxQuery()" value="1" data="['1','רҵ�Բ���'],['0','��רҵ�Բ���']" ></odin:select2>
		</tr>
		<tr>
			<td width="120" height="180" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">���ӷ���</td>
			<odin:textarea property="bzfx" colspan="7" readonly="true"></odin:textarea>
			<!-- <td colspan=7 id="bzfxb" >
				<textarea id="bzfx" name="bzfx" ></textarea>
			</td> -->
		</tr>
		<tr>
			<td width="120" height="100" style="text-align:center;font-family:SimHei;background-color:#ADD8E6">�����䱸����</td>
			<odin:textarea property="bzypjy" colspan="7" ></odin:textarea>
			<!-- <td colspan=7 id="bzypjyb">
				<textarea id="bzypjy" name="bzypjy" ></textarea>
			</td> -->
		</tr>	
	</table>
	<div id="ButtonDiv" style="border:none;text-align:right;padding-right:10px">
		<button onclick="saveFX()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:80px;height:30px;
    	cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">��&nbsp;&nbsp;��</button>
	</div>
	<table align="center" width=960 id="table3">
	<tr>
			<td  height="40" style="text-align:center;font-family:SimHei;font-size:18px;background-color:#ADD8E6">���佨��</td>
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


	closeWin();
});

function saveFX(){
	var bzfx=document.getElementById("bzfx").value;
	var bzypjy=document.getElementById("bzypjy").value;
	
	radow.doEvent('saveFX');
}

function saveNDKH(){
	
	radow.doEvent('saveNDKH');
}

function yearQuery(){
	radow.doEvent('yearQuery');
}

function zyxQuery(){
	radow.doEvent('init7');
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
	
	

    $('#zzcx').bind('click',function(){
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
       });
}
 
function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','��Ա�б�',700,500,'','<%=request.getContextPath()%>',null,p,true);
}


function ZXKHClick(){
	var checkedgroupid = document.getElementById('checkedgroupid').value;
	$h.openPageModeWin('BZZXKH','pages.publicServantManage.BZZXKHAddPage','����ר������',900,430,document.getElementById('checkedgroupid').value,ctxPath,null,{maximizable:false,resizable:false}); 
/* 	$h.openWin('BZZXKH','pages.publicServantManage.BZZXKH','ר������',800,330,null,ctxPath,null,
			{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
}


function openBZTP(){
	var tp00=document.getElementById('tp00').value;
	$h.openPageModeWin('BZTP','pages.fxyp.BZTPAddPage','�ɲ�����',900,430,document.getElementById('tp00').value,ctxPath,null,{maximizable:false,resizable:false});
}

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var tp00 = record.data.tp00;
	
	return "<a href=\"javascript:deleteRow2(&quot;"+tp00+"&quot;)\">ɾ��</a>";
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

	