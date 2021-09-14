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
		<div class="pgHead">�ŷþٱ�</div>
		<table id="xfjbInfoTbl">
			<tr>
				<td class="width-140 bold">��ʵ����</td>
				<td class="width-120 bold">ʱ&nbsp;��</td>
				<td class="width-620 bold">��&nbsp;��</td>
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
					//String cccount=objs[2]==null?"":"("+objs[2].toString()+"��)";
					if(x==0){%>
			<tr>
				<td rowspan="<%=xfjb.size() %>"><%=xf_resulte %><%-- (<%=xfjb.size() %>)�� --%></td>
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
		<div class="pgHead">��Ա��λ�ල��Ϣ</div>
		
		
		
		<div class="pgSubhead left">1.�м�ί</div>
		
		<table id="cydwjdInfoTbl1">
	
		<%if(null==cydw||0==cydw.size()){%>
			<tr>
				<td>��</td>
			</tr>
		<%} else { %>
		<tr>
				<td class="width-100 bold">����ʱ��</td>
				<td class="width-100 bold">�ܴ������</td>
				<td class="width-370 bold">����ժҪ</td>
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
					sclqk+=djcfname+"��";
				}
				if(zwcfname!=""){
					sclqk+=zwcfname+"��";
				}
				if(zzclname!=""){
					sclqk+=zzclname+"��";
				}
				if(txhxjmname!=""){
					sclqk+=txhxjmname+"��";
				}
				if(wfsclqkname!=""){
					sclqk+=wfsclqkname+"��";
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
		<div class="pgSubhead left">2.����ƾ�</div>
		<table id="cydwjdInfoTbl4">
		<%if(null==jjzr||0==jjzr.size()){%>
			<tr>
				<td>��</td>
			</tr>
		<%} else { %>
			<tr>
				<td class="width-100 bold">���ʱ��</td>
				<td class="width-140 bold">������</td>
				<td class="width-320 bold">��ƽ���</td>
				<td class="width-320 bold">����ժҪ</td>
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
		<div class="pgHead">���Ѻ�ѯ����</div>
		<table id="sclInfoTbl">
			<tr>
				<td class="width-140 bold">����ʱ��</td>
				<td class="width-140 bold">�ܴ������</td>
				<td class="width-370 bold">����ժҪ</td>
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
		<div class="pgHead">�й������</div>
		<table id="ygsxInfoTbl">
			<tr>
				<td class="width-100 bold">�������</td>
				<td class="width-160 bold">������</td>
				<td class="width-160 bold">������</td>
				<td class="width-160 bold">��˽��</td>
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
		<div class="pgHead">�ش������</div>
		<table id="zdsxInfoTbl">
			<tr>
				<td class="width-100 bold">����ʱ��</td>
				<td class="width-160 bold">�������</td>
				<td class="width-620 bold">��������</td>
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
		<div class="pgHead">������Ϣ</div>
		<div class="pgSubhead left">1.�򹫳���</div>
		<table id="ygcgInfoTbl">
			<tr>
				<td class="width-140 bold">ǰ�����ң�������</td>
				<td class="width-120 bold">����ʱ��</td>
				<td class="width-120 bold">����ͣ��ʱ�䣨�죩</td>
				<td class="width-500 bold">����</td>
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
		<div class="pgSubhead left">2.��˽����</div>
		<table id="yscgInfoTbl">
			<tr>
				<td class="width-140 bold">ǰ�����ң�������</td>
				<td class="width-120 bold">����ʱ��</td>
				<td class="width-120 bold">����ͣ��ʱ�䣨�죩</td>
				<td class="width-500 bold">����</td>
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
		<div class="pgHead">ѡ�����˼����Ϣ</div>
		<table id="xryrInfoTbl">
			<tr>
				<td class="bold">����鵥λ</td>
				<td class="bold">���ʱ��</td>
				<td class="bold">��鱨�棨������</td>
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
		<div class="pgHead">����������Ϊ</div>
		<table id="ddzwInfoTbl">
			<tr>
				<td class="width-140 bold">����ʱ��</td>
				<td class="width-370 bold">�ܴ������</td>
				<td class="width-370 bold">����ժҪ</td>
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
		<div class="pgHead">�����������</div>
		<div class="pgSubhead left">1.�����������</div>
		<table id="ndjjzrInfoTbl">
			<tr>
				<td class="width-140 bold">���</td>
				<td class="width-220 bold">ʱ��ְ��</td>
				<td class="width-220 bold">����Ƶ�λ</td>
				<td class="width-300 bold">��ƽ���</td>
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
				String sjjl=objs[4]==null?"":objs[4].toString();	//��ƽ���
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
		<div class="pgSubhead left">2.���ξ��������</div>
		<table id="lrjjsxInfoTbl">
			<tr>
				<td class="width-220 bold">��λ����</td>
				<td class="width-220 bold">�����쵼ְ��</td>
				<td class="width-140 bold">�����쵼����</td>
				<td class="width-300 bold">���Ӹ���</td>
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
		<div class="pgHead">��ȡ��ί���</div>
		<div class="pgSubhead left">1.������</div>
		<table id="yjshInfoTbl">
			<tr>
				<td class="width-220 bold">�ύ����</td>
				<td class="width-220 bold">��ί�������</td>
				<td class="width-220 bold">��ί����ʱ��</td>
			</tr>
			<%if(null==yjsh||0==yjsh.size()){%>
				<tr>
				<td colspan="3" class="height-60"></td>
				</tr>
			<%}else{
				for(Object[] objs:yjsh){
					String bm=objs[0]==null?"":objs[0].toString();		//�ύ����
					String jwfkyj=objs[1]==null?"":objs[1].toString();	//��ί�������	
					String sj=objs[2]==null?"":objs[2].toString();	//��ί����ʱ��
					
					String[] ztsz=new String[]{"ȫ��","δ�ύ","�ύ�ල��","�ύ��ί","��ί�ѷ���","��ίδ����"};
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



<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="id" title="����id" ></odin:hidden>	
</body>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("SUPERVISION")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("SUPERVISION")%>;

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});

function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	//�༭Ȩ��
	//$h.setEditable(window.parent.infoEditable['JdInformationAddPage_GB']=='1',{width:853,height:355,left:0,top:35},"rmbInfo",$h.disabledButtons.JdInfo);
	radow.doEvent('initX');
}


Ext.onReady(function(){
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	//$h.fieldsDisabled(fieldsDisabled); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	//var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	//$h.selectDisabled(selectDisabled,imgdata); 
	
	var viewSize = Ext.getBody().getViewSize();
	$("#content").width(viewSize.width);
	$("#content").height(viewSize.height-10);
});
</script>
