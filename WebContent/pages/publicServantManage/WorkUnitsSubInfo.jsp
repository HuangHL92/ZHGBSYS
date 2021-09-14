<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.OrthersAddPagePageModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<style>
body{
height: 100%

}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript">

</script>
<div>
<div id="btnToolBarDiv" ></div>
</div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">


				<odin:fill></odin:fill>
				
				<odin:buttonForToolBar text="保存" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"  ></odin:buttonForToolBar>
</odin:toolBar>

<%
Map<String, List<Object[]>> os_list = CommSQL.getInfoExt("'A02'");
if(os_list!=null&&os_list.size()>0){
	for(String key : os_list.keySet()){
		List<Object[]> entitys = os_list.get(key);
		String[] kv = key.split("___");
		%>
		<odin:groupBox property="<%=kv[0] %>" title="<%=kv[1] %>">
			<table cellspacing="2" width="98%" align="center">
				<tr>
		<%
		
		Integer size = entitys.size(),index=0;;
		for(Object[] entity : entitys){
			
			
			String data_type = entity[5].toString();
			Object code_type = entity[3];
			if(index%2==0){
				%>
				</tr>
				<tr>
				<%
			}
			if("VARCHAR2".equals(data_type)){//文本
				if(code_type!=null&&!"".equals(code_type)){
					%>
					<tags:PublicTextIconEdit maxlength="100" property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" codetype="<%=code_type.toString() %>" readonly="true"></tags:PublicTextIconEdit>
					<%
				}else{
					%>
					<odin:textEdit maxlength="100" property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:textEdit>
					<%
				}
			}else if("NUMBER".equals(data_type)){//数字
				%>
				<odin:numberEdit maxlength="50" property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:numberEdit>
				<%
			}else if("DATE".equals(data_type)){//日期
				%>
				<odin:NewDateEditTag property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" isCheck="true" maxlength="8" ></odin:NewDateEditTag>
				<%
			}
			index++;
			
			
			
		}
		
		%>
				</tr>
			</table>
		</odin:groupBox>
		<%
		
	}
	
}

%>