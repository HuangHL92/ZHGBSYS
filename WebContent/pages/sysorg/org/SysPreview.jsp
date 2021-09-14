<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@page import="com.insigma.siis.local.business.entity.B01"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysPreviewPageModel"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.SysOrgBS"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@include file="/comOpenWinInit.jsp" %>
<%@ page isELIgnored="false" %>
 
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>

<%
SysPreviewPageModel pm =null;
String subWinIdBussessIds=null;
String[] str =null;
String num=null;
String groupid =null;
String sql = "";
int counts_bz = 0;
int counts_zs=0;
try{
	pm = new SysPreviewPageModel();
	subWinIdBussessIds = subWinIdBussessId; 
	str = subWinIdBussessIds.split("\\|");
    num = str[0];
    groupid = str[1];
	if(str.length>2){
    	sql = str[2];
    }
    //System.out.println(sql);
    if("1".equals(num)){//下级领导职数
    	List<Map<String, Object>> res = pm.one(groupid);
    	request.getSession().removeAttribute("resultList");
    	request.getSession().setAttribute("resultList", res);
    }
    if("2".equals(num)){//下级编制
		List<Map<String, Object>> resultList2 = pm.two(groupid);
		counts_bz = Integer.valueOf(SysOrgBS.selectCountBySubIdBz(groupid));//下级机构数 仅仅法人单位
		counts_zs = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));//下级机构数 法人单位与内设机构
		request.getSession().setAttribute("resultList2", resultList2);
	}
	if("3".equals(num)){//领导职数多选
		//groupid 就是 value
		String b01String = groupid;
		List<B01> list = pm.yuLanMany(b01String,sql,"3");
		List<Map<String, Object>> resultList = pm.three(list);
		request.getSession().setAttribute("resultList", resultList);
	}
	if("4".equals(num)){//编制多选
		//groupid 就是 value
		String b01String = groupid;
		List<B01> list = pm.yuLanMany(b01String,sql,"4");
		List<Map<String, Object>> resultList2 = pm.four(list);
		counts_bz = Integer.valueOf(SysOrgBS.selectCountBySubIdBz_Counts(sql));//下级机构数 仅仅法人单位
		counts_zs = Integer.valueOf(SysOrgBS.selectCountBySubId_Counts(sql));//下级机构数 法人单位与内设机构
		request.getSession().setAttribute("resultList2", resultList2);
	}
	//List<Map<String, Object>> res = (List<Map<String, Object>>)request.getSession().getAttribute("resultList");
}catch(Exception e){
	e.printStackTrace();
}
	

	
%>

<style type="text/css">
span {
	font-size: 13px;
}
#bar_div {
	width: 100% !important;
}
</style>

<script type="text/javascript">
function download(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}
function cc(){
	w.close();
}
function closeWin(){
	var win = parent.Ext.getCmp('SysPreview');
    if (win) {win.close();}
}           
function change1(){
	document.getElementById("mytable1").style.display="";
}
function change2(){
	document.getElementById("mytable2").style.display="";
}

function openDiv(){
	var el = Ext.get(document.body);
	el.mask(odin.msg, odin.msgCls);
	var tId = setInterval(function closeWin(){
		if("1"==$("#biaozhi").val()){
			el.unmask();
			clearInterval(tId);
		}
	},100);
	$("#biaozhi").val("0");
}

</script>

<body style="background-color: #E0EEEE;">

<div id="bar_div"></div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:fill/>
	<odin:buttonForToolBar text="导出表格" icon="images/icon/exp.png" id="exp"></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="关闭" icon="images/wrong.gif" id="wrong" handler="closeWin" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:hidden property="checkedgroupid"/>
<odin:hidden property="downfile"/>
<odin:hidden property="num"/>
<odin:hidden property="execlName"/>
<odin:hidden property="biaozhi"/>

<div align="center">

<!-- 预览表一   ================================================================================================================================================== -->

	<div id="mytable1" style="display: none;">
		<table border="0" cellspacing="1px" style="border: 1px;text-align: center; margin-top: 15px; margin-left: 40px; border-collapse: collapse;">
			<thead>
				<tr>
					<td style="font-size: 20px" colspan="18" height="50" bgcolor="#F8F8FF" align="center">各单位领导职数配备情况表</td>
				</tr>
				<tr align="center">
					<td width="120" bgcolor="#F8F8FF" rowspan="2" height="90"><span>机构名称</span></td>
					<td width="100" bgcolor="#F8F8FF" rowspan="2" height="90"><span>机构级别</span></td>
					<td width="100" bgcolor="#F8F8FF" rowspan="2" height="90"><span>机构类型</span></td>
					<td width="180" bgcolor="#F8F8FF" colspan="4" height="35"><span>领导职数</span></td>
					<td width="180" bgcolor="#F8F8FF" colspan="4" height="35"><span>正职领导职数</span></td>
					<td width="180" bgcolor="#F8F8FF" colspan="4" height="35"><span>副职领导职数</span></td>
					<!-- <td width="90" bgcolor="#F8F8FF" colspan="2"><span>同级正职非领导职数</span></td>
					<td width="90" bgcolor="#F8F8FF" colspan="2"><span>同级副职非领导职数</span></td> -->
				</tr>
				<tr height="65" align="center">
					<td width="45" bgcolor="#F8F8FF"><span>应配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>实配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>超配</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>缺配</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>应配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>实配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>超配</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>缺配</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>应配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>实配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>超配</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>缺配</span></td>
					<!-- <td width="45" bgcolor="#F8F8FF"><span>应配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>实配人数</span></td> -->
					<!-- <td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
					<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td> -->
					<!-- <td width="45" bgcolor="#F8F8FF"><span>应配人数</span></td>
					<td width="45" bgcolor="#F8F8FF"><span>实配人数</span></td> -->
					<!-- <td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
					<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td> -->
				</tr>
			</thead>
			<tbody id="goods1">
			<c:forEach items="${resultList }" var="map">
				<tr  height="38" align="center">
            		<td width="120" bgcolor="#F8F8FF"><span>${map["B0101"]}</span></td>
            		<td width="100" bgcolor="#F8F8FF"><span>${map["B0127"]}</span></td>
            		<td width="100" bgcolor="#F8F8FF"><span>${map["B0194"]}</span></td>
            		
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zzB0183"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zzB0111"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span style="color:red">${map["zzover"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zzlow"]}</span></td>
            		
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zlB0183"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zlB0111"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span style="color:red">${map["zlover"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zllow"]}</span></td>
            		
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["flB0185"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["flB0111"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span style="color:red">${map["flover"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["fllow"]}</span></td>
            		
            		<%-- <td width="45" bgcolor="#FFFFFF" ><span>${map["zflB0188"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["zflB0111"]}</span></td> --%>
            		<%-- <td width="35" bgcolor="#FFFFFF" ><span style="color:red">${map["zflover"]}</span></td>
            		<td width="35" bgcolor="#FFFFFF" ><span>${map["zfllow"]}</span></td> --%>
            		<%-- <td width="45" bgcolor="#FFFFFF" ><span>${map["fflB0189"]}</span></td>
            		<td width="45" bgcolor="#FFFFFF" ><span>${map["fflB0111"]}</span></td> --%>
            		<%-- <td width="35" bgcolor="#FFFFFF" ><span style="color:red">${map["fflover"]}</span></td>
            		<td width="35" bgcolor="#FFFFFF" ><span>${map["ffllow"]}</span></td> --%>
            	</tr>
            </c:forEach>
            </tbody>
            <tfoot>
				<tr height="38" align="center">
					<td width="120" bgcolor="#F8F8FF"><span>总计</span></td>
					<td width="100" bgcolor="#F8F8FF"><span></span></td>
					<td width="100" bgcolor="#F8F8FF"><span></span></td>
					
					<td width="45" bgcolor="#FFFFFF"><span id="zj1"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj2"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj3"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj4"
						style="font-weight: bold;"></span></td>
						
					<td width="45" bgcolor="#FFFFFF"><span id="zj5"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj6"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj7"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj8"
						style="font-weight: bold;"></span></td>
						
					<td width="45" bgcolor="#FFFFFF"><span id="zj9"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj10"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj11"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj12"
						style="font-weight: bold;"></span></td>
					<!-- <td width="45" bgcolor="#FFFFFF"><span id="zj9"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj10"
						style="font-weight: bold;"></span></td> -->
				<!-- 	<td width="35" bgcolor="#FFFFFF"><span id="zj11"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="zj12"
						style="font-weight: bold;"></span></td> -->
					<!-- <td width="45" bgcolor="#FFFFFF"><span id="zj13"
						style="font-weight: bold;"></span></td>
					<td width="45" bgcolor="#FFFFFF"><span id="zj14"
						style="font-weight: bold;"></span></td> -->
					<!-- <td width="35" bgcolor="#FFFFFF"><span id="zj15"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="zj16"
						style="font-weight: bold;"></span></td> -->
				</tr>
			</tfoot>
		</table>
	</div>

<!-- 预览表二  ================================================================================================================================================== -->

		<div id="mytable2" style="display: none;">
			<table cellspacing="1px" style="border: 1px;text-align: center; margin-top: 15px; margin-left: 10px;margin-right:10px; border-collapse: collapse;">
				<thead>
					<tr align="center">
						<td align="center" colspan="22" height="50" bgcolor="#F8F8FF"><span
							style="font-size: 20px;">各单位编制与人员配备表</span></td>
					</tr>
					<tr align="center">
						<td width="120" bgcolor="#F8F8FF" rowspan="2" height="90"><span>机构名称</span></td>
						<td width="130" bgcolor="#F8F8FF" rowspan="2" height="90"><span>机构级别</span></td>
						<td width="140" bgcolor="#F8F8FF" colspan="4" height="38"><span>行政编制数</span></td>
						<td width="140" bgcolor="#F8F8FF" colspan="4"><span>事业编制数(参公)</span></td>
						<td width="140" bgcolor="#F8F8FF" colspan="4"><span>事业编制数(非参公)</span></td>
						<!-- <td width="140" bgcolor="#F8F8FF" colspan="4"><span>政法专项编制数</span></td> -->
						<td width="140" bgcolor="#F8F8FF" colspan="4"><span>工勤编制数</span></td>
						<td width="140" bgcolor="#F8F8FF" colspan="4"><span>其他编制数</span></td>
						
					</tr>
					<tr height="52" align="center">
						<td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td>
						<!-- <td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td> -->
						<td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>应配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>实配人数</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>超配</span></td>
						<td width="35" bgcolor="#F8F8FF"><span>缺配</span></td>
					</tr>
				</thead>
				<tbody id="goods2">
				<c:forEach items="${resultList2 }" var="map">
					<tr height="38" align="center">
						<td width="120" bgcolor="#F8F8FF"><span>${map["B0101"]}</span></td>
						<td width="130" bgcolor="#F8F8FF"><span>${map["B0127"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["xzB0227"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["xzB0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["xzover"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["xzlow"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["syB0232"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["syB0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["syover"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["sylow"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["qtB0233"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["qtB0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["qtover"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["qtlow"]}</span></td>
						
						<%-- <td width="35" bgcolor="#FFFFFF"><span>${map["zfzxB0235"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["zfzxB0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["zfzxover"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["zfzxlow"]}</span></td> --%>
						
						<td width="35" bgcolor="#FFFFFF"><span>${map["gqB0236"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["gqB0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["gqover"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["gqlow"]}</span></td>
						
						<td width="35" bgcolor="#FFFFFF"><span>${map["qt2B0234"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["qt2B0111"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span style="color: red">${map["qt2over"]}</span></td>
						<td width="35" bgcolor="#FFFFFF"><span>${map["qt2low"]}</span></td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr height="38" align="center">
					<td width="120" bgcolor="#F8F8FF"><span>总计</span></td>
					<td width="130" bgcolor="#F8F8FF"><span></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z1"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z2"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z3"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z4"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z5"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z6"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z7"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z8"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z9"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z10"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z11"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z12"
						style="font-weight: bold;"></span></td>
						
					<!-- <td width="35" bgcolor="#FFFFFF"><span id="z13"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z14"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z15"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z16"
						style="font-weight: bold;"></span></td> -->
					
					<td width="35" bgcolor="#FFFFFF"><span id="z17"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z18"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z19"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z20"
						style="font-weight: bold;"></span></td>		
					
					<td width="35" bgcolor="#FFFFFF"><span id="z21"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z22"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z23"
						style="font-weight: bold;"></span></td>
					<td width="35" bgcolor="#FFFFFF"><span id="z24"
						style="font-weight: bold;"></span></td>		
				</tr>
				</tfoot>
			</table>
		</div>

<!-- ================================================================================================================================================== -->


</div>


</body>


<script type="text/javascript">
//合计
function sum1() {
	var s1 = 0;var s2 = 0;var s3 = 0;var s4 = 0;var s5 = 0;var s6 = 0;var s7 = 0;var s8 = 0;
	var s9 = 0;var s10 = 0;/* var s11 = 0;var s12 = 0; */var s13 = 0;var s14 = 0;/* var s15 = 0;var s16 = 0; */
	var $trs = $("#goods1").children();
	for(var i=0;i<$trs.length;i++){
		var $tds = $($trs[i]).children();
		var t1 = $($tds[3]).children().html();s1 += parseInt(t1==""?0:t1);
		var t2 = $($tds[4]).children().html();s2 += parseInt(t2==""?0:t2);
		var t3 = $($tds[5]).children().html();s3 += parseInt(t3==""?0:t3);
		var t4 = $($tds[6]).children().html();s4 += parseInt(t4==""?0:t4);
		var t5 = $($tds[7]).children().html();s5 += parseInt(t5==""?0:t5);
		var t6 = $($tds[8]).children().html();s6 += parseInt(t6==""?0:t6);
		var t7 = $($tds[9]).children().html();s7 += parseInt(t7==""?0:t7);
		var t8 = $($tds[10]).children().html();s8 += parseInt(t8==""?0:t8);
		var t9 = $($tds[11]).children().html();s9 += parseInt(t9==""?0:t9);
		var t10 = $($tds[12]).children().html();s10 += parseInt(t10==""?0:t10);
	
		var t13 = $($tds[13]).children().html();s13 += parseInt(t13==""?0:t13);
		var t14 = $($tds[14]).children().html();s14 += parseInt(t14==""?0:t14);
		
	}
	
	$("#zj1").html(s1); $("#zj2").html(s2);$("#zj3").html(s3);$("#zj4").html(s4);$("#zj5").html(s5);$("#zj6").html(s6);$("#zj7").html(s7);
	$("#zj8").html(s8); $("#zj9").html(s9);$("#zj10").html(s10);$("#zj11").html(s13);$("#zj12").html(s14);
	
}

function sum2() {
	var s1 = 0;var s2 = 0;var s3 = 0;var s4 = 0;var s5 = 0;var s6 = 0;var s7 = 0;var s8 = 0;
	var s9 = 0;var s10 = 0;var s11 = 0;var s12 = 0;var s13 = 0;var s14 = 0;var s15 = 0;var s16 = 0;
	var s17 = 0;var s18 = 0;var s19 = 0;var s20 = 0;var s21 = 0;var s22 = 0;var s23 = 0;var s24 = 0;
	
	var $trs = $("#goods2").children();
	for(var i=0;i<$trs.length;i++){
		var $tds = $($trs[i]).children();
		var t1 = $($tds[2]).children().html();s1 += parseInt(t1==""?0:t1);
		var t2 = $($tds[3]).children().html();s2 += parseInt(t2==""?0:t2);
		var t3 = $($tds[4]).children().html();s3 += parseInt(t3==""?0:t3);
		var t4 = $($tds[5]).children().html();s4 += parseInt(t4==""?0:t4);
		var t5 = $($tds[6]).children().html();s5 += parseInt(t5==""?0:t5);
		var t6 = $($tds[7]).children().html();s6 += parseInt(t6==""?0:t6);
		var t7 = $($tds[8]).children().html();s7 += parseInt(t7==""?0:t7);
		var t8 = $($tds[9]).children().html();s8 += parseInt(t8==""?0:t8);
		var t9 = $($tds[10]).children().html();s9 += parseInt(t9==""?0:t9);
		var t10 = $($tds[11]).children().html();s10 += parseInt(t10==""?0:t10);
		var t11 = $($tds[12]).children().html();s11 += parseInt(t11==""?0:t11);
		var t12 = $($tds[13]).children().html();s12 += parseInt(t12==""?0:t12);
		var t13 = $($tds[14]).children().html();s13 += parseInt(t13==""?0:t13);
		var t14 = $($tds[15]).children().html();s14 += parseInt(t14==""?0:t14);
		var t15 = $($tds[16]).children().html();s15 += parseInt(t15==""?0:t15);
		var t16 = $($tds[17]).children().html();s16 += parseInt(t16==""?0:t16);
		var t17 = $($tds[18]).children().html();s17 += parseInt(t17==""?0:t17);
		var t18 = $($tds[19]).children().html();s18 += parseInt(t18==""?0:t18);
		var t19 = $($tds[20]).children().html();s19 += parseInt(t19==""?0:t19);
		var t20 = $($tds[21]).children().html();s20 += parseInt(t20==""?0:t20);
		/* var t21 = $($tds[22]).children().html();s21 += parseInt(t21==""?0:t21);
		var t22 = $($tds[23]).children().html();s22 += parseInt(t22==""?0:t22);
		var t23 = $($tds[24]).children().html();s23 += parseInt(t23==""?0:t23);
		var t24 = $($tds[25]).children().html();s24 += parseInt(t24==""?0:t24); */
	}
	
	$("#z1").html(s1); $("#z2").html(s2);$("#z3").html(s3);$("#z4").html(s4);$("#z5").html(s5);$("#z6").html(s6);$("#z7").html(s7);
	$("#z8").html(s8); 
	$("#z9").html(s9);
	$("#z10").html(s10);
	$("#z11").html(s11);
	$("#z12").html(s12);
	/* $("#z13").html(s13);
	$("#z14").html(s14);
	$("#z15").html(s15);
	$("#z16").html(s16); */
	$("#z17").html(s17);
	$("#z18").html(s18);
	$("#z19").html(s19);
	$("#z20").html(s20);
	$("#z21").html(s21);
	$("#z22").html(s22);
	$("#z23").html(s23);
	$("#z24").html(s24);
}
var counts_bz=<%=counts_bz%>
var counts_zs=<%=counts_zs%>
var num_bz=<%=num%>
Ext.onReady(function() {
	if(num_bz=='2'||num_bz=='4'){
		if(counts_bz=='0'&&counts_zs!='0'){
			alert("编制人员对比仅统计法人单位下的人员!");
		}
	}
});
</script>




