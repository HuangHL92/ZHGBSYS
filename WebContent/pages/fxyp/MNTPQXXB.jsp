<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.pagemodel.fxyp.MNTPQXXBPageModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<style>
#commForm{
	background-color: rgb(87,100,242);
}
#coordTable {
border-right: 1px solid #000;
border-bottom: 1px solid #000;
border-collapse:collapse;
background-color: white;
}
#b0101SpanId{
color: white;
}
.tabletd {
border-left: 1px solid #000;
border-top: 1px solid #000;
}

.tabletd_2 {
border-left: 1px solid #000;
border-top: 1px solid #000;
width:14% !important;
height:50 !important;
}

.yellowbutton {
	font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
	font-size:16px;
	/* font-weight:bolder; */
	color:white;
	width:90px;
	height:33px;
	/* line-height:20px; */
	border:0;
	cursor:pointer;
	background-color:#F08000;
}

.class_a0215a{
font-size: 14px;
}

.class_a0101{
font-weight: bold;
cursor: pointer;
}
#b0101SpanId{
font-size: 14px !important;
}
#b0101_combo{
font-size: 14px;
}


</style>

<%

	MNTPQXXBPageModel mpp=new MNTPQXXBPageModel();
	try{
		String type=request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			type = "1";
		}
		String sfys=request.getSession().getAttribute("sfys")==null?"0":request.getSession().getAttribute("sfys").toString();
		String condition="";
		String name="";
		if("1".equals(type)){//区县市
			condition="select nvl(b0104,b0101) b0104,b0111,b0183,b0185,'核定 '||nvl(b0183,'0')||'正 '||nvl(b0185,'0')||'副 '||nvl(b0246,'0')||'总师' b0188,"
					+"(select count(1) from a02,a01 where a02.a0000=a01.a0000 and a0163='1' and a01.status='1' and a01.a0221 is not null and a02.a0201e in ('1','3') and a0201b=b01.b0111 and a0281='true' and a02.a0277='1') b0235,"
					+"b0236,b0140 from b01 where substr(b0111,1,11)='001.001.004' and length(b0111)=19 and b0131 in('1001','1003','1004','1005')";
			//condition="select b0104,b0111,b0183,b0185,decode(b0183,null,decode(b0185,null,'',b0185||'副'),b0183||'正'||decode(b0185,null,'',b0185||'副')) b0188,b0236 from b01 where b0111='001.001.002.01U'";
			name="区县市调配前机构人员信息表";
		}else if("2".equals(type)){//区县市平台
			condition="select nvl(b0104,b0101) b0104,b0111,b0183,b0185,'核定 '||nvl(b0183,'0')||'正 '||nvl(b0185,'0')||'副 '||nvl(b0246,'0')||'总师' b0188,"
					+"(select count(1) from a02,a01 where a02.a0000=a01.a0000 and a0163='1' and a01.status='1' and a01.a0221 is not null and a02.a0201e in ('1','3') and a0201b=b01.b0111 and a0281='true' and a02.a0277='1') b0235,"
					+"b0236,b0140 from b01 where b0121 like '001.001.004.%' and length(b0111)=19 and b0131='3480'";	
			name="区县市平台调配前机构人员信息表";
		}else if("3".equals(type)){//市直
			condition="select nvl(b0104,b0101) b0104,b0111,b0183,b0185,case when b0111 in ('001.001.002.01N','001.001.002.01P','001.001.002.01O.001','001.001.002.01Q.001') then '核定职数'||(nvl(b0183,'0')+nvl(b0185,'0'))||'职' else '核定 '||nvl(b0183,'0')||'正 '||nvl(b0185,'0')||'副 '||nvl(b0246,'0')||'总师' end b0188,"
					+"(select count(1) from a02,a01 where a02.a0000=a01.a0000 and a0163='1' and a01.status='1' and a01.a0221 is not null and a02.a0201e in ('1','3') and a0201b=b01.b0111 and a0281='true' and a02.a0277='1') b0235,"
					+"b0236,b0140 from b01 where ((b0121 = '001.001.002'"
					+"  and b01id not in ('6C759252379B4E01BFA614D2B06D31FA','B9E46D6110134E77B7273E01EACF21A3','6B48873119494B34A80F0E314549813D')  )"
					+"    or b01id in (  'F0E0EA716C0442328DE41B549BC73C9C','0B3931450F264D36895440B7EAB46B81','24A2EF2597174AE8BF1E65D7EE34DD24',"
					+"   '70BF51BDAD28458DA98B7092B049AECF','BC923D3D6F034B8584C4DDF60071C895','96D6674BC36C428B84166728D0A21455',"
					+"   '72C8D307601E4D15B4DED1B82B41E8DE','A6F4AE51884D4231B7AF3C8623A56884'))";
			name="市直单位调配前机构人员信息表";
		}else if("4".equals(type)){//国企高校
			condition="select nvl(b0104,b0101) b0104,b0111,b0183,b0185,'核定 '||nvl(b0183,'0')||'正 '||nvl(b0185,'0')||'副 '||nvl(b0246,'0')||'总师' b0188,"
					+"(select count(1) from a02,a01 where a02.a0000=a01.a0000 and a0163='1' and a01.status='1' and a01.a0221 is not null and a02.a0201e in ('1','3') and a0201b=b01.b0111 and a0281='true' and a02.a0277='1') b0235,"
					+"b0236,b0140 from b01 where b0111 like '001.001.003.%'"
					 +"  and b01id not in ( '36E7A2F90629493AA4FCAB4345AF6F77','6C5E5C831D8443D48EBF42E89CDE7055','DBAD9B3F2C2E4BAE935FC8F4C245555D','3601E75C9C6F4EF4AD7274C9D6CA09B5','B9E46D6110134E77B7273E01EACF21A3','6C759252379B4E01BFA614D2B06D31FA','D563DF546904420B8D042038B927549A','7D174AA620804C608774111467F3F111')"
					 +"  and b0111 not like '001.001.002.02N%'  and b0114 not like '%X09'";	
			name="国企高校调配前机构人员信息表";
		}
		String mntp_tpqxx_org = request.getSession().getAttribute("mntp_tpqxx_org")==null?"":" and b0111 in ('" + request.getSession().getAttribute("mntp_tpqxx_org").toString().replaceAll(",", "','")+"')";
		List<HashMap<String, Object>> list_b01=mpp.queryB01(condition+mntp_tpqxx_org+"  order by b0269");
		int sys_month= mpp.getMonth();
		if(list_b01!=null&&list_b01.size()>0){
			
%>
<div style="margin-left: 60;">
<odin:hidden property="queryType" value="<%=type %>"/>
<table border="0">
	<tr style="border: 0;">
		<td style="border: 0;width:120;height: 50;"><input  type="button" class="yellowbutton" value="区县市" id="btn1" onclick="updatebtn('1')"/></td>
		<td style="border: 0;width:120;"><input  type="button" class="yellowbutton" value="区县市平台" id="btn2" onclick="updatebtn('2')"/></td>
		<td style="border: 0;width:120;"><input  type="button" class="yellowbutton" value="市直单位" id="btn3" onclick="updatebtn('3')"/></td>
		<td style="border: 0;width:120;"><input  type="button" class="yellowbutton" value="国企高校" id="btn4" onclick="updatebtn('4')"/></td>
	</tr>
</table>

</div>
<table style="margin-left: 60px;">
  <tr>
    <odin:select2 property="b0101" label="&nbsp;&nbsp;选择查询机构："  width="250" multiSelect="true"></odin:select2>
    <td align="center" valign="middle" ><button type='button' onclick="queryOrg()" style='margin-left: 30px;font-size: 14px;'>查看</button></td>
    <td><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><label style="FONT-SIZE: 14px;color: white;">显示颜色</label><span>&nbsp;</span><input name="sfys" id="sfys" type="checkbox" value="<%=sfys %>"  onchange="updateYs()"/> </td>
  </tr>
</table>
<div style="height: 15px;"></div>
<div id="div2" style="overflow: auto;margin-left: 2px;height: 690;display: table; text-align: center;margin:auto">
<table id="coordTable">
	<tr  align="center">
		<td colspan="5" class="tabletd" style="font-size: 24px;height: 60;font-weight:bold;"><%=name %></td>
	</tr>
	<tr  align="center">
		<td  class="tabletd" colspan="3" style="font-size: 16px;height: 40;font-weight:bold;">单位情况</td>
		<td  class="tabletd" rowspan="3" style="font-size: 16px;font-weight:bold;">配备情况</td>
		<td  class="tabletd" rowspan="3" style="font-size: 16px;font-weight:bold;">备注</td>
	</tr>
	<tr align="center">
		<td  class="tabletd" rowspan="2" style="font-size: 16px;font-weight:bold;">单位名称（简称）</td>
		<td  class="tabletd" style="font-size: 16px;height: 40;font-weight:bold;">核定职数</td>
		<td  class="tabletd" rowspan="2" style="font-size: 16px;font-weight:bold;">超/缺配情况</td>
	</tr>
	<tr align="center">
		<td  class="tabletd" style="font-size: 16px;height: 40;font-weight:bold;">实配情况</td>
	</tr>
<%
			for(HashMap<String, Object> map_b01:list_b01){
				String b0236="";
				if("0".equals(map_b01.get("b0235"))){
					if(map_b01.get("b0236")==null||"".equals(map_b01.get("b0236"))){
						
					}else{
						b0236=mpp.isnull(map_b01.get("b0236"));
					}
				}else{
					b0236="超配"+map_b01.get("b0235")+"名";
					if(map_b01.get("b0236")==null||"".equals(map_b01.get("b0236"))){
						
					}else{
						b0236=b0236+"<br>"+map_b01.get("b0236");
					}
				}
				String b0188=mpp.isnull(map_b01.get("b0188")).replace(" 0正", " ").replace(" 0副", " ").replace(" 0总师", " ").replace(" ", "");
%>
	<tr align="center">
		<td rowspan="2" width="200" class="tabletd">
			<%=map_b01.get("b0104") %>
		</td>
		<td width="120" class="tabletd" onclick="openBGWQP('<%=map_b01.get("b0111").toString()%>','<%=mpp.isnull(map_b01.get("b0236"))%>','<%=mpp.isnull(map_b01.get("b0104"))%>')">
			<%=b0188 %>
		</td>
		<td rowspan="2" width="100" class="tabletd">
			<%=b0236%>
		</td>
		<%
				List<HashMap<String, Object>> list_per=mpp.queryBZRY(map_b01.get("b0111").toString());
				if(list_per!=null&&list_per.size()>0){
					int per_num=list_per.size();
					int col_num=per_num/7;
					int ys_num=per_num%7;
					if(ys_num>0){
						col_num=col_num+1;
					}
		%>
		<td rowspan="2" width="780" style="border-left: 0;border-top: 0;" class="tabletd">
			<table  style="width: 100%;border-collapse:collapse;" border="0">
				<%
					for(int i=0;i<col_num;i++){
						String h1="<tr  align='center'>";
						String h2="<tr  align='center'>";
						for(int j=0;j<7;j++){
							if(i*7+j<per_num){
								HashMap<String, Object> map_per=list_per.get(i*7+j);
								if(map_per.get("a0000")!=null&&!"".equals(map_per.get("a0000"))){
									String tdcolor="";
									if("1".equals(map_per.get("a0277"))){
										tdcolor="bgcolor='#e06d6a'";
									}else if("1".equals(map_per.get("a0248"))){
										tdcolor="bgcolor='#5b9bd5'";
									}else if("1".equals(map_per.get("a0201b"))){
										if( Integer.valueOf(map_per.get("a0107").toString())+59*12<sys_month){
											tdcolor="bgcolor='#ffc000'";
										}else{
											tdcolor="bgcolor='#efefef'";
										}
									}else if("10".equals(map_per.get("a0165"))){
										if( Integer.valueOf(map_per.get("a0107").toString())+58*12<sys_month){
											tdcolor="bgcolor='#ffc000'";
										}else{
											tdcolor="bgcolor='#efefef'";
										}
									}else if("11".equals(map_per.get("a0165"))){
										if( Integer.valueOf(map_per.get("a0107").toString())+57*12<sys_month){
											tdcolor="bgcolor='#ffc000'";
										}else{
											tdcolor="bgcolor='#efefef'";
										}
									}else if(Integer.valueOf(map_per.get("a0192f").toString())>54){
										tdcolor="bgcolor='#ffc000'";
									}else{
										tdcolor="bgcolor='#efefef'";
									}
									if("1".equals(sfys)){
										if("bgcolor='#efefef'".equals(tdcolor)){
											h1=h1+"<td class='tabletd_2' "+tdcolor+ " ><a ><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>"+mpp.isnull(map_per.get("a0215a"))+"</span></a></td>";
											h2=h2+"<td class='tabletd_2'><a ><span class='class_a0101'  onclick=\"openRmb('"+map_per.get("a0000")+"')\"  >"+mpp.isnull(map_per.get("a0101"))+"</span></a></td>";
										}else{
											h1=h1+"<td class='tabletd_2' "+tdcolor+ " ><a ><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>"+mpp.isnull(map_per.get("a0215a"))+"</span></a></td>";
											h2=h2+"<td class='tabletd_2' "+tdcolor+ " ><a ><span class='class_a0101'  onclick=\"openRmb('"+map_per.get("a0000")+"')\"  >"+mpp.isnull(map_per.get("a0101"))+"</span></a></td>";
										}
									}else{
										h1=h1+"<td  class='tabletd_2' bgcolor='#efefef'><a ><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>"+mpp.isnull(map_per.get("a0215a"))+"</span></a></td>";
										h2=h2+"<td  class='tabletd_2' ><a ><span class='class_a0101'  onclick=\"openRmb('"+map_per.get("a0000")+"')\"  >"+mpp.isnull(map_per.get("a0101"))+"</span></a></td>";
									}
								}else{
									if("1".equals(sfys)){
										h1=h1+"<td class='tabletd_2' bgcolor='#92d050'><a><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>"+mpp.isnull(map_per.get("a0215a"))+"</span></a></td>";
										h2=h2+"<td class='tabletd_2' bgcolor='#92d050'><a><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>";
									}else{
										h1=h1+"<td  class='tabletd_2' bgcolor='#efefef' ><a><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>"+mpp.isnull(map_per.get("a0215a"))+"</span></a></td>";
										h2=h2+"<td  class='tabletd_2'  ><a><span onclick=\"openGW('"+mpp.isnull(map_per.get("a0215a"))+"','"+map_b01.get("b0104")+"','"+mpp.isnull(map_per.get("gwmc"))+"')\"  class='class_a0215a'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>";
									}
								}
							
							}else{
								h1=h1+"<td  class='tabletd_2'></td>";
								h2=h2+"<td  class='tabletd_2'></td>";
							}
							
						}
						h1=h1+"</tr>";
						h2=h2+"</tr>";
						%>
				<%=h1+h2 %>
						<% 
					}
				%>
			</table>
		</td>
		<%
				}else{
		%>
		<td rowspan="2" width="780" class="tabletd">
		</td>
		<%	
				}
		%>
		<td rowspan="2" width="180" class="tabletd">
			<%=mpp.isnull(map_b01.get("b0140"))%>
		</td>
	</tr>
	<tr  align="center">
		<td class="tabletd" onclick="openBGWQP('<%=map_b01.get("b0111").toString()%>','<%=mpp.isnull(map_b01.get("b0236"))%>','<%=mpp.isnull(map_b01.get("b0104"))%>')">
			<%="实配"+mpp.querySPZS(map_b01.get("b0111").toString()) %>
		</td>
	</tr>
<% 		
		
			}
%>
</table>
</div>
<div style="height: 10;">
</div>
<%
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<odin:hidden property="rmbs"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<script type="text/javascript">



function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function replaceParamVal(paramName,replaceWith) {
    var oUrl = this.location.href.toString();
    var re=eval('/('+ paramName+'=)([^&]*)/gi');
    var nUrl = oUrl.replace(re,paramName+'='+replaceWith);
    this.location = nUrl;
    window.location.href=nUrl;
}
function updatebtn(str){
	replaceParamVal("type",str);
}

function openRmb(a0000){
	radow.doEvent("openRmb",a0000);
}
function openGW(gw,b0101,gwmc){
	<%-- var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 var url = "http://"+ip+":"+port+"/ngbdp/nmntp/tp/xjpc?hasback=false&"+Ext.urlEncode({"gw":gw,"dw":b0101}); 
	 
	 openNewWindow(url,"岗位"); --%>
	 
	 //radow.doEvent('setGWSQL',fxyp00);
	 if(gwmc==''){
		 //$h.alert('','未设置重点岗位！');
		 var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
		 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
		 var url = "http://"+ip+":"+port+"/ngbdp/nmntp/tp/xjpc?hasback=false&"+Ext.urlEncode({"gw":gw,"dw":b0101}); 
		 
		 openNewWindow(url,"岗位");
		 return;
	 }
	openMate(gwmc)
}
function openMate(p){
	$h.openPageModeWin('GWYL','pages.fxyp.gbtp.GWYL','人员列表',1350,680,{query_id:p},'<%=request.getContextPath()%>');
}
 
function openNewWindow(url,name){
	var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数
	if (window.screen) {
	   var ah = screen.availHeight - 40;
	   var aw = screen.availWidth - 10;
	   fulls += ",height=" + ah;
	   fulls += ",innerHeight=" + ah;
	   fulls += ",width=" + aw;
	   fulls += ",innerWidth=" + aw;
	   fulls += ",resizable"
	} else {
	   fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually
	}
	window.open(url,name,fulls);
}
function queryOrg(){
	radow.doEvent("setB0111");
}

function updateYs(){
	radow.doEvent("updateYs");
}

function openBGWQP(str1,str2,str3){
	$h.openPageModeWin('GWQP','pages.sysorg.org.BGWQP','缺配信息',1270,620,{b0111:str1,b0236:str2,b0104:str3},'<%= request.getContextPath() %>');
}

</script>
