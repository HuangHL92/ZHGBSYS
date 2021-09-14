<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<!-- 统计分析共同方法 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk.js" ></script>
<!-- 统计分析基本情况分析模块专属 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/JsClassify/Gbjbqknv_c.js" ></script>

<%@include file="/comOpenWinInit.jsp" %>
<%--页面最上方工具条 导出-表格-文字-饼状图 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_ToolBar.jsp" %>

<%--统计列表 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>
<%--统计表格 --%>
<%@include file="/pages/sysorg/org/gbjbqk/GbjbqkSub_form.jsp" %>
<%--统计饼状图 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_Bzt.jsp" %>
<%--文字与隐藏字段 --%>
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
////表格双击事件 
function myMouseDClick(col1, row1) {
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	//平均值
	if(col_xho_end==col1){
		return;
	}
	//占比
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
	for(var i=0;i<array_row.length;i++){//小计
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4){//总计
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	
	var dwid=document.getElementById("subWinIdBussessId2").value;
	if(dcbz_flag==2){//文字显示标志
		dwid=dwid+"$"+''+"$"+col1+"$"+row1+"$"+title;
	}else{
		dwid=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	}
	//dwid=dwid+"$"+col1+"$"+row1+"$"+title;
	
	//alert(dwid);
	var id_weiyi="GbjbqknvSub";//不可重复
	//用户id
	var useridh=document.getElementById("userid_h").value;
	id_weiyi=id_weiyi+useridh;
	//系统时间
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	id_weiyi=id_weiyi+time;
	$h.openWin(id_weiyi,'pages.sysorg.org.gbjbqk.GbjbqknvSub',title+'-二维统计',1300,550,dwid,'<%=request.getContextPath()%>');
}
var title_zj="(女)";
</script>
<!--  点击统计分析    -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_pageinit.js" ></script>
<!-- 表格中的条件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_tjfxtj.js" ></script>
<!-- 工具条按钮 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk_tjfxtoolbar.js" ></script>