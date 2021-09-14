<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="PersonInfoServer.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/pages/publicServantManage/personInfo/widthConfig.css"/>								  
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/pages/publicServantManage/personInfo/heightConfig.css"/>								  
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/pages/publicServantManage/personInfo/PersonInfo.css"/>	

<style>
body {
	background-color: rgb(214,227,243);
}
/* .dasda td{width: 120px;padding-bottom: 5px;} */
</style>

<div id="content">
	<div id="page1">
		<div class="pgHead">信访举报</div>
		<table id="xfjbInfoTbl">
			<tr>
				<td class="width-140 bold">查实结论</td>
				<td class="width-120 bold">时&nbsp;间</td>
				<td class="width-620 bold">内&nbsp;容</td>
			</tr>
		<%if(null==xfjbs||0==xfjbs.size()){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%}
		boolean pgflag = true;
		for(int p=0, len=xfjbs.size(); p<len; p++){
			List<Object[]> xfjb=xfjbs.get(p);
			if(xfjb!=null&&xfjb.size()>0){
				pgflag = false;
				int x=0;
				for(Object[] objs:xfjb){
					String cctime=objs[0]==null?"":objs[0].toString();
					String cccontent=objs[1]==null?"":objs[1].toString();
					String xf_resulte=objs[2]==null?"":objs[2].toString();
					//String cccount=objs[2]==null?"":"("+objs[2].toString()+"次)";
					if(x==0){%>
			<tr>
				<td rowspan="<%=xfjb.size() %>"><%=xf_resulte %><%-- (<%=xfjb.size() %>)次 --%></td>
				<td><%=cctime %></td>
				<td class="left"><%=cccontent %></td>
			</tr> 
					<%} else {%>
			<tr>
				<td><%=cctime %></td>
				<td class="left"><%=cccontent %></td>
			</tr>
					<%}
					x++;
				}
			}
		}
		if(pgflag){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%}%>
		</table>
	</div>
	<div id="page2" >
		<div class="pgHead">成员单位监督信息</div>
		
		
		
		<div class="pgSubhead left">1.市纪委</div>
		
		<table id="cydwjdInfoTbl1">
	
		<%if(null==cydw||0==cydw.size()){%>
			<tr>
				<td>无</td>
			</tr>
		<%} else { %>
		<tr>
				<td class="width-100 bold">处理时间</td>
				<td class="width-100 bold">受处理情况</td>
				<td class="width-370 bold">案情摘要</td>
			</tr>
		<% 
			for(Object[] objs:cydw){
				String rolename=objs[0]==null?"":objs[0].toString();
				String inputtime=objs[1]==null?"":objs[1].toString();
				String briefcase=objs[2]==null?"":objs[2].toString();
				String djcfname=objs[3]==null?"":objs[3].toString();
				String zwcfname=objs[4]==null?"":objs[4].toString();
				String zzclname=objs[5]==null?"":objs[5].toString();
				String txhxjmname=objs[6]==null?"":objs[6].toString();
				String wfsclqkname=objs[7]==null?"":objs[7].toString();
				String sclqk="";
				if(djcfname!=""){
					sclqk+=djcfname+"；";
				}
				if(zwcfname!=""){
					sclqk+=zwcfname+"；";
				}
				if(zzclname!=""){
					sclqk+=zzclname+"；";
				}
				if(txhxjmname!=""){
					sclqk+=txhxjmname+"；";
				}
				if(wfsclqkname!=""){
					sclqk+=wfsclqkname+"；";
				}
				%>
			<tr>
				<td><%=inputtime %></td>
				<td><%=sclqk %></td>
				<td class="left"><%=briefcase %></td>
			</tr> 
				<%} 
		}%>
		</table>
		<div class="pgSubhead left">2.市审计局</div>
		<table id="cydwjdInfoTbl4">
		<%if(null==jjzr||0==jjzr.size()){%>
			<tr>
				<td>无</td>
			</tr>
		<%} else { %>
			<tr>
				<td class="width-100 bold">审计时间</td>
				<td class="width-140 bold">审计类别</td>
				<td class="width-320 bold">审计结论</td>
				<td class="width-320 bold">问题摘要</td>
			</tr>
		<% 	for(Object[] objs:jjzr){
				String sjsj=objs[0]==null?"":objs[0].toString();
				String sjlb=objs[1]==null?"":objs[1].toString();
				String sjjl=objs[2]==null?"":objs[2].toString();
				String zywtzy=objs[3]==null?"":objs[3].toString();%>
			<tr>
				<td><%=sjsj %></td>		
				<td><%=sjlb %></td>
				<td class="left"><%=sjjl %></td>
				<td class="left"><%=zywtzy %></td>
			</tr> 
			<%}
		}%>
		</table>

		
	</div>
	<div id="page3" >
		<div class="pgHead">提醒函询诫勉</div>
		<table id="sclInfoTbl">
			<tr>
				<td class="width-140 bold">处理时间</td>
				<td class="width-140 bold">受处理情况</td>
				<td class="width-370 bold">案情摘要</td>
			</tr>
		<%if(null==cfqk||0==cfqk.size()){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:cfqk){
				String clsj=objs[0]==null?"":objs[0].toString();
				String sxnr=objs[1]==null?"":objs[1].toString();
				String sclqk=objs[2]==null?"":objs[2].toString();%>
			<tr>
				<td><%=clsj %></td>
				<td class="left"><%=sclqk %></td>
				<td class="left"><%=sxnr %></td>
			</tr> 
			<%}
		}%>
		</table>
	</div>
	<div id="page8" >
		<div class="pgHead">有关事项报告</div>
		<table id="ygsxInfoTbl">
			<tr>
				<td class="width-100 bold">查核日期</td>
				<td class="width-160 bold">查核年度</td>
				<td class="width-160 bold">查核类别</td>
				<td class="width-160 bold">查核结果</td>
			</tr>
		<%if(null==grygsx||0==grygsx.size()){%>
			<tr>
				<td colspan="4" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:grygsx){
				String wthcrq=objs[0]==null?"":objs[0].toString();
				String hcnd=objs[1]==null?"":objs[1].toString();
				String hclb=objs[2]==null?"":objs[2].toString();
				String hcjg=objs[3]==null?"":objs[3].toString();%>
			<tr>
				<td><%=wthcrq %></td>
				<td><%=hcnd %></td>
				<td><%=hclb %></td>
				<td class="left"><%=hcjg %></td>
			</tr> 
			<%}
		}%>
		</table>
	</div>
	<%-- <div id="page4" >
		<div class="pgHead">重大事项报告</div>
		<table id="zdsxInfoTbl">
			<tr>
				<td class="width-100 bold">报告时间</td>
				<td class="width-160 bold">事项类别</td>
				<td class="width-620 bold">事项内容</td>
			</tr>
		<%if(null==grzdsx||0==grzdsx.size()){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:grzdsx){
				String pssj=objs[0]==null?"":objs[0].toString();
				String bgsxlb=objs[1]==null?"":objs[1].toString();
				String bgnr=objs[2]==null?"":objs[2].toString();%>
			<tr>
				<td><%=pssj %></td>
				<td><%=bgsxlb %></td>
				<td class="left"><%=bgnr %></td>
			</tr> 
			<%}
		}%>
		</table>
	</div>--%>
	<div id="page5" >
		<div class="pgHead">出国信息</div>
		<div class="pgSubhead left">1.因公出国</div>
		<table id="ygcgInfoTbl">
			<tr>
				<td class="width-140 bold">前往国家（地区）</td>
				<td class="width-120 bold">出国时间</td>
				<td class="width-120 bold">在外停留时间（天）</td>
				<td class="width-500 bold">事由</td>
			</tr>
		<%if(null==ygcg||0==ygcg.size()){%>
			<tr>
				<td colspan="4" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:ygcg){
				String country1=objs[0]==null?"":objs[0].toString();
				String abroaddate1=objs[1]==null?"":objs[1].toString();
				String residencetime1=objs[2]==null?"":objs[2].toString();
				String incident1=objs[3]==null?"":objs[3].toString();
				%>
			<tr>
				<td><%=country1 %></td>
				<td><%=abroaddate1 %></td>
				<td><%=residencetime1 %></td>
				<td class="left"><%=incident1 %></td>
			</tr>
				<%}
		}%>
		</table>
		<div class="pgSubhead left">2.因私出国</div>
		<table id="yscgInfoTbl">
			<tr>
				<td class="width-140 bold">前往国家（地区）</td>
				<td class="width-120 bold">出国时间</td>
				<td class="width-120 bold">在外停留时间（天）</td>
				<td class="width-500 bold">事由</td>
			</tr>
		<%if(null==yscg||0==yscg.size()){%>
			<tr>
				<td colspan="4" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:yscg){
				String country2=objs[0]==null?"":objs[0].toString();
				String abroaddate2=objs[1]==null?"":objs[1].toString();
				String residencetime2=objs[2]==null?"":objs[2].toString();
				String incident2=objs[3]==null?"":objs[3].toString();
				%>
			<tr>
				<td><%=country2 %></td>
				<td><%=abroaddate2 %></td>
				<td><%=residencetime2 %></td>
				<td class="left"><%=incident2 %></td>
			</tr>
				<%}
		}%>
		</table>		
	</div>
	<%-- <div id="page6" >
		<div class="pgHead">选人用人检查信息</div>
		<table id="xryrInfoTbl">
			<tr>
				<td class="bold">被检查单位</td>
				<td class="bold">检查时间</td>
				<td class="bold">检查报告（附件）</td>
			</tr>
		<%if(null==xryr||0==xryr.size()){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:xryr){
				String jcdate=objs[0]==null?"":objs[0].toString();
				String jcdept=objs[1]==null?"":objs[1].toString();
				String jcfilename=objs[2]==null?"":objs[2].toString();
				String jcfileurl=objs[3]==null?"":objs[3].toString();
				%>
			<tr>
				<td><%=jcdate %></td>
				<td><%=jcdept %></td>
				<td class="left"> <a href="javascript:downloads('<%=jcfileurl %>')"><%=jcfilename %></a></td>
			</tr>
				<%}
		}%>
		</table>		
	</div> --%>
	<%-- 
	<div id="page9" >
		<div class="pgHead">不担当不作为</div>
		<table id="ddzwInfoTbl">
			<tr>
				<td class="width-140 bold">处理时间</td>
				<td class="width-370 bold">受处理情况</td>
				<td class="width-370 bold">案情摘要</td>
			</tr>
		<%if(null==ddzw||0==ddzw.size()){%>
			<tr>
				<td colspan="3" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:ddzw){
				String fxsj=objs[0]==null?"":objs[0].toString();
				String sy=objs[1]==null?"":objs[1].toString();
				String clcs=objs[2]==null?"":objs[2].toString();%>
			<tr>
				<td><%=fxsj %></td>
				<td class="left"><%=clcs %></td>
				<td class="left"><%=sy %></td>
			</tr> 
			<%}
		}%>
		</table>
	</div>
	--%>
	<div id="page10" >
		<div class="pgHead">经济责任审计</div>
		<div class="pgSubhead left">1.经济责任审计</div>
		<table id="ndjjzrInfoTbl">
			<tr>
				<td class="width-140 bold">年度</td>
				<td class="width-220 bold">时任职务</td>
				<td class="width-220 bold">被审计单位</td>
				<td class="width-300 bold">审计结论</td>
			</tr>
		<%if(null==ndjjzr||0==ndjjzr.size()){%>
			<tr>
				<td colspan="4" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:ndjjzr){
				String nd=objs[0]==null?"":objs[0].toString();
				String srzw=objs[1]==null?"":objs[1].toString();
				String bdcdw=objs[2]==null?"":objs[2].toString();
				String sjbgname=objs[3]==null?"":objs[3].toString();
				/* String sjbgurl=objs[4]==null?"":objs[4].toString(); */
				String sjjl=objs[4]==null?"":objs[4].toString();	//审计结论
				%>
			<tr>
				<td><%=nd %></td>
				<td><%=srzw %></td>
				<td><%=bdcdw %></td>
				<td><%=sjjl %></td>
				<%-- <td class="left"> <a href="javascript:downloads('<%=sjbgurl %>')"><%=sjbgname %></a></td> --%>
			</tr>
				<%}
		}%>
		</table>
		<div class="pgSubhead left">2.离任经济事项交接</div>
		<table id="lrjjsxInfoTbl">
			<tr>
				<td class="width-220 bold">单位名称</td>
				<td class="width-220 bold">离任领导职务</td>
				<td class="width-140 bold">接任领导姓名</td>
				<td class="width-300 bold">交接附件</td>
			</tr>
		<%if(null==lrjjsx||0==lrjjsx.size()){%>
			<tr>
				<td colspan="4" class="height-60"></td>
			</tr>
		<%} else {
			for(Object[] objs:lrjjsx){
				String dwmc=objs[0]==null?"":objs[0].toString();
				String lrldzw=objs[1]==null?"":objs[1].toString();
				String jrldxm=objs[2]==null?"":objs[2].toString();
				String jjfjname=objs[3]==null?"":objs[3].toString();
				String jjfjurl=objs[4]==null?"":objs[4].toString();
				%>
			<tr>
				<td><%=dwmc %></td>
				<td><%=lrldzw %></td>
				<td><%=jrldxm %></td>
				<td class="left"> <a href="javascript:downloads('<%=jjfjurl %>')"><%=jjfjname %></a></td>
			</tr>
				<%}
		}%>
		</table>		
	</div>
	<div id="page11" >
		<div class="pgHead">听取纪委意见</div>
		<div class="pgSubhead left">1.意见审核</div>
		<table id="yjshInfoTbl">
			<tr>
				<td class="width-220 bold">提交部门</td>
				<td class="width-220 bold">纪委反馈意见</td>
				<td class="width-220 bold">纪委反馈时间</td>
			</tr>
			<%if(null==yjsh||0==yjsh.size()){%>
				<tr>
				<td colspan="3" class="height-60"></td>
				</tr>
			<%}else{
				for(Object[] objs:yjsh){
					String bm=objs[0]==null?"":objs[0].toString();		//提交部门
					String jwfkyj=objs[1]==null?"":objs[1].toString();	//纪委反馈意见	
					String sj=objs[2]==null?"":objs[2].toString();	//纪委反馈时间
					
					String[] ztsz=new String[]{"全部","未提交","提交监督室","提交纪委","纪委已反馈","纪委未反馈"};
					%>
					<tr>
						<td><%=bm %></td>
						<td><%=jwfkyj %></td>
						<td><%=sj %></td>
					</tr>
					<%
				}
			}%>
			</table>
	</div>
</div>



<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="id" title="主键id" ></odin:hidden>	
</body>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("SUPERVISION")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("SUPERVISION")%>;

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});

function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	//编辑权限
	//$h.setEditable(window.parent.infoEditable['JdInformationAddPage_GB']=='1',{width:853,height:355,left:0,top:35},"rmbInfo",$h.disabledButtons.JdInfo);
	radow.doEvent('initX');
}


Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	//$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	//$h.selectDisabled(selectDisabled,imgdata); 
	
	var viewSize = Ext.getBody().getViewSize();
	$("#content").width(viewSize.width);
	$("#content").height(viewSize.height-10);
});
</script>
