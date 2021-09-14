<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.pagemodel.meeting.MeetingPreviewPageModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%

	MeetingPreviewPageModel mpp=new MeetingPreviewPageModel();
	try{
		String publishid=request.getParameter("publishid");
		if(publishid==null||"".equals(publishid)){
		}else{
		List<HashMap<String, Object>> list_publish=mpp.queryPublish(publishid);
		String publishname="";
		String type="";
		String qx_type="";
		if(list_publish!=null&&list_publish.size()>0) {
			publishname=list_publish.get(0).get("agendaname").toString();
			type=list_publish.get(0).get("agendatype").toString();
			qx_type=list_publish.get(0).get("qx_type").toString();
		}
		String meetingtime=mpp.queryMeetingTime(publishid);
	
%>
<style>
table{
	font-family: 'FangSong_GB2312';
	font-size:21px;
	line-height:1.8em;
}
.class1{
	font-size:29px;
	font-family:'方正小标宋简体';
}
.class2{
	font-family: 'KaiTi_GB2312';
	font-size:21px;
}
.class3{
	font-family: 黑体;
	font-size:21px;
}
.class4{
	font-family: 'FangSong_GB2312';
	font-size:21px;
}
.class5{
	font-family: 'KaiTi_GB2312';
	font-size:21px;
	font-weight:bold;
}
.class6{
	font-family: 'FangSong_GB2312';
	font-size:21px;
	font-weight:bold;
}
.class7{
	font-family: 'KaiTi_GB2312';
	font-size:12pt;
	line-height:1.2em;
	text-align:center;
	text-align:justify;
	text-justify:distribute-all-lines;
	text-align-last:justify
}
.class8{
	font-family: 'KaiTi_GB2312';
	font-size:14pt;
	line-height:1.5em;
}
.rmfont{
	font-family: 'Times New Roman';
}
.table_p td{
border: 1px solid black;
text-align: center;
}
.table_p{
border-collapse:collapse;
border: 2px solid black;
}
</style>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script type="text/javascript">


</script>
<table style="width: 100%">
<tr>
<td  width="2%" rowspan="2">
</td>
<td  width="96%">

<table style="width: 100%">
	<tr height="20px" >
		<td width="100%"></td>
	</tr>
	<tr>
		<td>
			<table style="width: 100%;">
				<tr>
					<td style="border: 2px solid black;" width="18%"  align="center">
						<table>
								<%
								List<HashMap<String, Object>> list_meet=mpp.queryMeeting(publishid);
								String meetingtype=list_meet.get(0).get("meetingtype").toString();
								if("1".equals(meetingtype)){
									%>
							<tr>
								<td class="class7">
									市委组织部
								</td>
							</tr>
							<tr>
								<td class="class7">
									部务会议材料
								</td>
							</tr>
									<% 
								}else if("2".equals(meetingtype)){
									%>
							<tr>
								<td class="class7">
									市委五人小组会
								</td>
							</tr>
							<tr>
								<td class="class7">
									会议材料
								</td>
							</tr>
									<%
								}else if("3".equals(meetingtype)){
									String meetingjc=list_meet.get(0).get("meetingjc").toString();
									String meetingpc=list_meet.get(0).get("meetingpc").toString();
									meetingjc=mpp.repNum(meetingjc);
									meetingpc=mpp.repNum(meetingpc);
									%>
							<tr>
								<td class="class7">
									<%=meetingjc %>届市委常委会
								</td>
							</tr>
							<tr>
								<td class="class7">
									第<%=meetingpc %>次会议材料
								</td>
							</tr>
									<%
								}
								%>
									
								
						</table>
					</td>
					<td width="67%">
					</td>
					<td  width="15%"  align="center">
						<table>
							<tr>
								<td class="class8">
									会议材料
								</td>
							</tr>
							<tr>
								<td class="class8">
									注意保管
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
	<tr>
		<td align="center" class="class1">
			<%=mpp.repNum(publishname) %>
		</td>
	</tr>
	<tr>
		<td align="center" class="class2">
			<%=mpp.repNum(meetingtime) %>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
</table>
</td>
	<td width="2%" rowspan="2">
	</td>
	<%
	List<HashMap<String, Object>> list1=mpp.queryTitle(publishid, "-1");
	if(list1!=null&&list1.size()>0){
		if("1".equals(type)){
	%>
	<tr><td>
	<table class="table_p" style="width: 100%">	
	<tr>
		<td width="4%">序号</td>
		<td width="8%">姓名</td>
		<td width="8%">出生<br>年月</td>
		<td width="12%">学历<br>学位</td>
		<td width="18%">现任</td>
		<td width="18%">拟任</td>
		<td width="18%">拟免</td>
		<td width="14%">备注</td>
	</tr>
	<%
		}else if("3".equals(type)){
	%>
			<tr><td>
			<table class="table_p" style="width: 100%">	
			<tr>
				<td width="5%">序号</td>
				<td width="12%">姓名</td>
				<td width="12%">出生<br>年月</td>
				<td width="16%">学历<br>学位</td>
				<td width="25%">现任</td>
				<td width="20%">备注</td>
			</tr>
	<%
		}
		for(int i=1;i<=list1.size();i++){
			HashMap<String, Object> map1=list1.get(i-1);
	%>
	<tr>
	<%
			if("2".equals(type)){
				
			}else{
				if("1".equals(type)){
	%>
		<td class="class3" colspan="8" style="text-align: left;"> 
	<%
				}else if("3".equals(type)){
	%>
		<td class="class3" colspan="6" style="text-align: left;"> 
	<%	
				}
			if("1".equals(map1.get("title02"))){
	%>
			<%= mpp.queryNum(i)%>、<%=mpp.repNum(map1.get("title01"))%>
	<%
			}else if("2".equals(map1.get("title02"))){
	%>
			（<%= mpp.queryNum(i)%>）<%=mpp.repNum(map1.get("title01"))%>
	<%
			}else if("3".equals(map1.get("title02"))){
	%>
			<%=mpp.repNum(i+"")+"."+mpp.repNum(map1.get("title01"))%>
	<%
			}
			if(map1.get("title03")!=null&&!"".equals(map1.get("title03"))){
	%>
				<span  class="class4" ><%="<br>"+mpp.repNum(map1.get("title03"))%></span>
	<%
			}
	%>
		</td>
	<% 
			}
	%>
	</tr>
	<%
			
			if("1".equals(type)||"3".equals(type)){
			List<HashMap<String, Object>> list2=mpp.queryPenson(publishid, map1.get("titleid").toString(),qx_type);
			if(list2!=null&&list2.size()>0){
				int map2_i=1;
				for(HashMap<String, Object> map2:list2){
	%>
			<tr>
				<td><%=mpp.repNum(map2_i) %></td>
				<td>
					<%=map2.get("a0101")%>
				</td>
				<td>
					<%=map2.get("a0107")%>
				</td>
				<td >
					<%=mpp.repNum(map2.get("xlxw"))%>
				</td>
				<% if("1".equals(type)){ %>
				<td >
					<%=mpp.repNum(map2.get("a0192a"))%>
				</td>
				<%
				}else if("3".equals(type)){
					%>
					<td >
						<%=mpp.repNum(map2.get("a0192a_dy"))%>
					</td>
					<%
				}
				if("1".equals(type)){
				%>
				<td>
					<%=mpp.repNum(map2.get("tp0121")==null?"":map2.get("tp0121").toString())+mpp.repNum(map2.get("tp0111")==null?"":map2.get("tp0111").toString())%>
				</td>
				<td>
					<%=mpp.repNum(mpp.isnull(map2.get("tp0122"))+(map2.get("tp0112")==null?"":map2.get("tp0112").toString()))%>
				</td>
				<%
				}
				%>
				<td>
					<%=mpp.repNum(map2.get("tp0114"))%>
				</td>
			</tr>	
	<%
					map2_i++;
				}
			}
			}
			List<HashMap<String, Object>> list3=mpp.queryTitle(publishid, map1.get("titleid").toString());
			if(list3!=null&&list3.size()>0){
				int x=0;
				for(int j=1;j<=list3.size();j++){
					HashMap<String, Object> map3=list3.get(j-1);
				%>
				<tr>
				<%
					if("1".equals(type)){
					%>
						<td class="class3" colspan="8" style="text-align: left;"> 
					<%
					}else if("3".equals(type)){
					%>
						<td class="class3" colspan="6" style="text-align: left;"> 
					<%	
					}
						if("1".equals(map3.get("title02"))){
				%>
						<%= mpp.queryNum(j)%>、<%=mpp.repNum(map3.get("title01"))%>
				<%
						}else if("2".equals(map3.get("title02"))){
				%>
						（<%= mpp.queryNum(j-x)%>）<%=mpp.repNum(map3.get("title01"))%>
				<%
						}else if("3".equals(map3.get("title02"))){
							x=x+1;
				%>
						<%=mpp.repNum(j+"")+"."+mpp.repNum(map3.get("title01"))%>
				<%
						}
						if(map3.get("title03")!=null&&!"".equals(map3.get("title03"))){
	%>
						<span  class="class4" ><%="<br>"+mpp.repNum(map3.get("title03"))%></span>
				<%
						}
				%>
					</td></tr>
				<%
						if("1".equals(type)||"3".equals(type)){
						List<HashMap<String, Object>> list4=mpp.queryPenson(publishid, map3.get("titleid").toString(),qx_type);
						if(list4!=null&&list4.size()>0){
							int map4_i=1;
							for(HashMap<String, Object> map4:list4){
				%>
						<tr>
							<td><%=mpp.repNum(map4_i) %></td>
							<td>
								<%=map4.get("a0101")%>
							</td>
							<td>
								<%=map4.get("a0107")%>
							</td>
							<td >
								<%=mpp.repNum(map4.get("xlxw"))%>
							</td>
							<% if("1".equals(type)){ %>
							<td >
								<%=mpp.repNum(map4.get("a0192a"))%>
							</td>
							<%
							}else if("3".equals(type)){
								%>
								<td >
									<%=mpp.repNum(map4.get("a0192a_dy"))%>
								</td>
								<%
							}
							if("1".equals(type)){
							%>
							<td>
								<%=mpp.repNum(map4.get("tp0121")==null?"":map4.get("tp0121").toString())+mpp.repNum(map4.get("tp0111")==null?"":map4.get("tp0111").toString())%>
							</td>
							<td>
								<%=mpp.repNum(mpp.isnull(map4.get("tp0122"))+(map4.get("tp0112")==null?"":map4.get("tp0112").toString()))%>
							</td>
							<%} %>
							<td>
								<%=mpp.repNum(map4.get("tp0114"))%>
							</td>
						</tr>
						<%
								map4_i++;
							}
						}
						}
						List<HashMap<String, Object>> list5=mpp.queryTitle(publishid, map3.get("titleid").toString());
						if(list5!=null&&list5.size()>0){
								for(int k=1;k<=list5.size();k++){
									HashMap<String, Object> map5=list5.get(k-1);
									%>
									<tr>
									<%
									if("1".equals(type)){
									%>
										<td class="class3" colspan="8" style="text-align: left;"> 
									<%
									}else if("3".equals(type)){
									%>
										<td class="class3" colspan="6" style="text-align: left;"> 
									<%	
									}
									if("1".equals(map5.get("title02"))){
									%>
											<%= mpp.queryNum(k)%>、<%=mpp.repNum(map5.get("title01"))%>
									<%
									}else if("2".equals(map5.get("title02"))){
									%>
											（<%= mpp.queryNum(k)%>）<%=mpp.repNum(map5.get("title01"))%>
									<%
									}else if("3".equals(map5.get("title02"))){
									%>
											<%=mpp.repNum(k+"")+"."+mpp.repNum(map5.get("title01"))%>
									<%
									}
									if(map5.get("title03")!=null&&!"".equals(map5.get("title03"))){
					%>
										<span  class="class4" ><%="<br>"+mpp.repNum(map5.get("title03"))%></span>
								<%
									}
									%>
									</td></tr>
									<%
									if("1".equals(type)||"3".equals(type)){
									List<HashMap<String, Object>> list6=mpp.queryPenson(publishid, map5.get("titleid").toString(),qx_type);
									if(list6!=null&&list6.size()>0){
										int map6_i=1;
										for(HashMap<String, Object> map6:list6){
								%>
										<tr>
											<td><%=mpp.repNum(map6_i) %></td>
											<td>
												<%=map6.get("a0101")%>
											</td>
											<td>
												<%=map6.get("a0107")%>
											</td>
											<td >
												<%=mpp.repNum(map6.get("xlxw"))%>
											</td>
											<% if("1".equals(type)){ %>
											<td >
												<%=mpp.repNum(map6.get("a0192a"))%>
											</td>
											<%
											}else if("3".equals(type)){
												%>
												<td >
													<%=mpp.repNum(map6.get("a0192a_dy"))%>
												</td>
												<%
											}
											if("1".equals(type)){
											%>
											<td>
												<%=mpp.repNum(map6.get("tp0121")==null?"":map6.get("tp0121").toString())+mpp.repNum(map6.get("tp0111")==null?"":map6.get("tp0111").toString())%>
											</td>
											<td>
												<%=mpp.repNum(mpp.isnull(map6.get("tp0122"))+(map6.get("tp0112")==null?"":map6.get("tp0112").toString()))%>
											</td>
											<%} %>
											<td>
												<%=mpp.repNum(map6.get("tp0114"))%>
											</td>
										</tr>
	<%
											map6_i++;
										}
									}
									}
							}
						}
				}
			}
		}
	}
	%>
	<tr height="40px">
		<td colspan="8"></td>
	</tr>
</table>
</td>
</tr>
</table>
<% 		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<odin:hidden property="publishid"/>
<script type="text/javascript">


</script>
