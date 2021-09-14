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
table td{
	border: 1px solid #DDD;
	padding-left:10px;
	
}
td button{
border-radius:5px;
background-color: #F08000;
border: none;
width:120px;
height:30px;
cursor:pointer;
color: white;
text-align: center;
text-decoration: none;
display: inline-block;
font-size: 16px;
}
 table{
	border-collapse:collapse
}
#table1 tr>td:first-child{
	background-color:#ADD8E6;	
}
textarea{
	font-family:SimHei;
	font-size:40px;	
	height:120px; 
	width:100%;
	border:none;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>


<odin:hidden property="SKBSid"/>


<div id="bzyp">
	<table width="960" align="center" style="border: 1px solid #DDD;">
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;background-color:#FFFFFF;padding-left:0px;">һ����ɫ��ؽ�����İ���</td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">1����ֱ����ȱ��Ҫ�쵼����Ҫ�쵼�����ڵ��䡾��ɫ��</td>
			<td  style="text-align:center"><button onclick="Click1()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">2������3����ȿ���ĩλ��������2λ����ֱ10λ������ɫ��</td>
			<td  style="text-align:center"><button onclick="Click2()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">3�����ӳ�Ա������ر���ְ��������ɫ��</td>
			<td  style="text-align:center"><button onclick="Click3()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">4������Ӳ��Ž�����Ӱ����ְ����ɫ��</td>
			<td  style="text-align:center"><button onclick="Click4()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td colspan=8 height="40" style="text-align:center;font-family:SimHei;background-color:#FFFFFF;padding-left:0px;">������ɫ�����ע����İ���</td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">5������ɲ�����ƫ�ͣ�����15%������ɫ��</td>
			<td  style="text-align:center"><button onclick="Click5()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">6�������ڰ��ӳ�Ա����1/3���ϻ�3�����ϡ���ɫ��</td>
			<td  style="text-align:center"><button onclick="Click6()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">7��������Ů�ɲ�������ɲ��䱸��������꡾��ɫ��</td>
			<td  style="text-align:center"><button onclick="Click7()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">8��������ȿ���ĩλ��������2λ����ֱ10λ������ɫ��</td>
			<td  style="text-align:center"><button onclick="Click8()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">9��ר������������ص��ע���ص����ĵȸ�����������ɫ��</td>
			<td id="dwm" style="text-align:center"><button onclick="Click9()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">10����������������ӳ�Ա�ܵ������񴦷֡���ɫ��</td>
			<td id="dwm" style="text-align:center"><button onclick="Click10()" type="button" >ѡ����</button></td>
		</tr>
		<tr>
			<td width="600" height="40" colspan=7 style="text-align:center;font-family:SimHei;background-color:#ADD8E6">11�����ӳ�Ա�����轻����ְ��������ɫ��</td>
			<td id="dwm" style="text-align:center"><button onclick="Click11()" type="button" >ѡ����</button></td>
		</tr>
		
		
	</table>

</div>


<%-- <odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" handler="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar> --%>

<script type="text/javascript">
var ctxPath = '<%= request.getContextPath() %>';
function Click1(){
	var SKBSid = '1';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click2(){
	var SKBSid = '2';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click3(){
	var SKBSid = '3';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click4(){
	var SKBSid = '4';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click5(){
	var SKBSid = '5';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click6(){
	var SKBSid = '6';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click7(){
	var SKBSid = '7';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click8(){
	var SKBSid = '8';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click9(){
	var SKBSid = '9';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click10(){
	var SKBSid = '10';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
function Click11(){
	var SKBSid = '11';
	if(SKBSid==''){
		$h.alert('ϵͳ��ʾ','��ѡ���ʶ����');
		return;
	}
	$h.openPageModeWin('SKBSBM','pages.fxyp.SKBSBM','����ѡ��',530,550,{SKBSid:SKBSid},ctxPath);}
</script>

	