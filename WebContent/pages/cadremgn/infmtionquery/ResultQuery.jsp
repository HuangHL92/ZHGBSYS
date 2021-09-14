<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.infmtionquery.SqlSearchPageModel"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%@ include file="/comOpenWinInit2.jsp" %>
<odin:hidden property="vru000" value="<%=request.getParameter(\"vru000\") %>"/>
<odin:groupBox title="查询结果">
<table style="width: 100%">
	<tr> 
		<td width="100%">
		<odin:editgrid property="resultListGrid" height="335" bbarId="pageToolBar"  
			pageSize="20" url="/" autoFill="false" >
		<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="dualname"  />
			 <% 
				if(request.getParameter("vru000")!=null&&!"".equals(request.getParameter("vru000"))
					&&!"null".equals(request.getParameter("vru000").toString())){
					String qvid=request.getParameter("vru000").toString();
					SqlSearchPageModel ssp=new SqlSearchPageModel();
					List<String> list=ssp.querySqlCode(qvid);
					int i = 0;
					String[] split = null;
					if(list==null||list.size()==0){
						%><odin:gridDataCol name="dualname" isLast="true" /><%
					}else{
						for(String name : list){
							split = name.split("@");
							i++;
							if(i==list.size() ){
								%>
								<odin:gridDataCol name="<%=split[0].toLowerCase() %>"  isLast="true" />
								<%
							}else{
								%>
								<odin:gridDataCol name="<%=split[0].toLowerCase() %>"  />
								<%
							}
							
						}
					}
				}else{
					%>
					<odin:gridDataCol name="dualname" isLast="true" />
					<%
				}
			%> 
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			 <%
				if(request.getParameter("vru000")!=null&&!"".equals(request.getParameter("vru000"))
					&&!"null".equals(request.getParameter("vru000").toString())){
					String qvid=request.getParameter("vru000").toString();
					SqlSearchPageModel ssp=new SqlSearchPageModel();
					List<String> list=ssp.querySqlCode(qvid);
					int i = 0;
					String[] split = null;
					if(list==null||list.size()==0){
						%><odin:gridDataCol name="dualname" isLast="true" /><%
					}else{
						String header = "";
						for(String name : list){
							split = name.split("@");
							i++;
							header = split[1].equals("null")?split[0].toLowerCase():split[1];
							if(i==list.size() ){
								%>
								<odin:gridEditColumn2 dataIndex="<%=split[0].toLowerCase() %>"  width="100" header="<%=header %>" codeType="<%=split[2] %>"
									 align="center" editor="<%=split[3] %>" edited="false"  isLast="true"/>
								<%
							}else{
								%>
								<odin:gridEditColumn2 dataIndex="<%=split[0].toLowerCase() %>"  width="100" header="<%=header %>" codeType="<%=split[2] %>"
									align="center" editor="<%=split[3] %>" edited="false"/>
								<%
							}
						}
					}
				}else{
					%>
							<odin:gridEditColumn2 dataIndex="dualname" width="100" hidden="true"
							 align="center" editor="text" edited="false"  isLast="true"/>
					<%
				}
			 	
			%> 
		</odin:gridColumnModel>
		</odin:editgrid>
		</td>
	</tr>
	<tr >
	<td align="right" style="padding-right:20px" >
		<%-- <odin:button  text="导出" property="btn2"  handler="expExcelFromGrid" /> --%>
		<input id="btn2" class="yellowbutton" type="button" value="导&nbsp;&nbsp;出" onclick="expExcelFromGrid()"/>
	</td>
</tr>
</table>
</odin:groupBox>

<script type="text/javascript">
function expExcelFromGrid(){
	var excelName = null;
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('resultListGrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	
	var length = dstore.getCount();
	if(length==0){
		$h.alert('系统提示：','没有要导出的数据！',null,180);
		return;
	}
	excelName = "sql查询" + "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('resultListGrid', excelName, null,null, false);
	
} 

Ext.onReady(function() {
	radow.doEvent("tempFunc");
});
</script>