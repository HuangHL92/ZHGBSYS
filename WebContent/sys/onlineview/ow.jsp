<%@ page contentType="text/html; charset=gbk" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%
	String routePath="E:/primarymathFile";
	String mid="aaa";
	String bid="bbb";
	String max_size="3";
	String userName="zxb";
	String url="/commform/onlineview/OnlineViewServlet?method=getAttachments1&routePath="+routePath+"&mid="+mid+"&bid="+bid+"&max_size="+max_size+"&userName="+userName;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<odin:head/>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/onlineview/publ/syst/basis.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/onlineview/ow.js"></script>
<script type="text/javascript">

//�����Ԥ��
function yulan(obj) {
	var fid = obj;
	//var contextPath = syst.getEleObj("contextPath").value;

	//Ԥ��
	var url = contextPath
			+ "/commform/onlineview/OnlineViewServlet?method=yulan&fid=" + fid;		

	//syst.sendReqAsync("post", url, null, lookInFrame);
	window.open(url,"fileContentArea","location=no;memubar=no;titlebar=no;toolbar=no;",false);

}


	function ylFromServer(value, params, rs, rowIndex, colIndex, ds){
		var fid = rs.get('fid');
		
		return "<a id='" + fid + "' name='" + fid
		+ "' href='javascript:yulan("+fid+");'>Ԥ��</a>";

		
	}
	
	function downFromServer(value, params, rs, rowIndex, colIndex, ds){
		var fid = rs.get('fid');
		
		var url = contextPath
		+ "/commform/onlineview/OnlineViewServlet?method=downloadAtta&fid="
		+ fid;
		
		return "<a id='" + fid + "' name='" + fid
		+ "' href='"+url+"'>����</a>";
	}
</script>
<title>�ޱ����ĵ�</title>
</head>

<body>

<form id="upform" name="upform" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/commform/onlineview/OnlineViewServlet?method=toCompu" target="fileContentArea">
  
  <input type="file" name="upfile" id="upfile" />
  <input type="submit" value="�ϴ�" /><br>
  <div id="attachArea" name="attachArea"></div>
  <input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
  
  <!-- ���漸���������ͨ�����Դ��� -->
  <input type="hidden" id="routePath" name="routePath" value="E:/primarymathFile"/><!-- �ļ���Ÿ�·�� -->
  <input type="hidden" id="mid" name="mid" value="aaa"/><!-- ģ��ID -->
  <input type="hidden" id="bid" name="bid" value="bbb"/><!-- ҵ��ID -->
  <input type="hidden" id="max_size" name="max_size" value="3"/><!-- �ļ�����ϴ���С����λM -->
  <input type="hidden" id="userName" name="userName" value="zxb"/><!-- �ϴ��û��� -->
</form>


<odin:editgrid property="attachGrid" title="�����б�" autoFill="false" width="590" height="160" bbarId="pageToolBar" pageSize="20" isFirstLoadData="true" url="<%=url %>">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="fid"/>
					<odin:gridDataCol name="username"/>
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="fsize" />
					<odin:gridDataCol name="uptime" />
					<odin:gridDataCol name="yulan" />
					<odin:gridDataCol name="down" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="fid" width="5" header="" align="center" hidden="true"/>
					<odin:gridColumn dataIndex="username" width="110" header="�ϴ���" align="center"/>
					<odin:gridColumn dataIndex="filename" width="100" header="�ļ���" align="center"/>
					<odin:gridColumn dataIndex="fsize" width="130" header="�ļ���С" align="center"/>
					<odin:gridColumn dataIndex="uptime" width="100" header="�ϴ�ʱ��" align="center"/>
					<odin:gridColumn dataIndex="yulan" header="Ԥ��" width="80" renderer="ylFromServer" align="center"/>
					<odin:gridColumn dataIndex="down" header="����" width="50" renderer="downFromServer" align="center" isLast="true"/>
				
					
				</odin:gridColumnModel>
			</odin:editgrid>
<!-- frameBorder="false" width="20%" height="10%" -->
<div style="position:relative;">
<iframe id="fileContentArea" name="fileContentArea" src="<%=request.getContextPath()%>/commform/onlineview/fileLookArea.jsp" frameBorder="false"></iframe>
</div>

</body>
</html>