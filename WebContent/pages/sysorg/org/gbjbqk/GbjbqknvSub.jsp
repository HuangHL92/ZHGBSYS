<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<!-- ͳ�Ʒ�����ͬ���� -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk.js" ></script>
<!-- ͳ�Ʒ��������������ģ��ר�� -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/JsClassify/Gbjbqknv_c.js" ></script>

<%@include file="/comOpenWinInit.jsp" %>
<%--ҳ�����Ϸ������� ����-���-����-��״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_ToolBar.jsp" %>

<%--ͳ���б� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>
<%--ͳ�Ʊ�� --%>
<%@include file="/pages/sysorg/org/gbjbqk/GbjbqkSub_form.jsp" %>
<%--ͳ�Ʊ�״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_Bzt.jsp" %>
<%--�����������ֶ� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_sub_WAndHT.jsp" %>
<odin:window src="/" id="gbjbqktjsubid" width="1300" height="550"></odin:window>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
var ctpath="<%=request.getContextPath()%>";
////���˫���¼� 
function myMouseDClick(col1, row1) {
	//�������� 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4){
		return;
	}
	//��ֵΪ0 �� ��
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	//ƽ��ֵ
	if(col_xho_end==col1){
		return;
	}
	//ռ��
	if(col1>=col_xho_start&&col1<=col_xho_end){
		if(col1%2==1){
			return;
		}
	}
	if(col1>=col_xhj_start&&col1<=col_xhj_end){
		if(col1%2==0){
			return;
		}
	}
	var title1="";
	var title2="";
	title1=DCellWeb_tj.GetCellString(2,row1,0);
	for(var i=0;i<array_row.length;i++){//С��
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4){//�ܼ�
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	
	var dwid=document.getElementById("subWinIdBussessId2").value;
	if(dcbz_flag==2){//������ʾ��־
		dwid=dwid+"$"+''+"$"+col1+"$"+row1+"$"+title;
	}else{
		dwid=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	}
	//dwid=dwid+"$"+col1+"$"+row1+"$"+title;
	
	//alert(dwid);
	var id_weiyi="GbjbqknvSub";//�����ظ�
	//�û�id
	var useridh=document.getElementById("userid_h").value;
	id_weiyi=id_weiyi+useridh;
	//ϵͳʱ��
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	id_weiyi=id_weiyi+time;
	$h.openWin(id_weiyi,'pages.sysorg.org.gbjbqk.GbjbqknvSub',title+'-��άͳ��',1300,550,dwid,'<%=request.getContextPath()%>');
}
var title_zj="(Ů)";
</script>
<!--  ���ͳ�Ʒ���    -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_pageinit.js" ></script>
<!-- ����е����� -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_tjfxtj.js" ></script>
<!-- ��������ť -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_tjfxtoolbar.js" ></script>