<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.safe.SysfunctionManager" %>
<%@ page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysfunction" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String functionid=request.getParameter("functionid");
	Sysfunction sysfunction=SysfunctionManager.getModuleSysfunction(functionid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>操作日志-<%=sysfunction.getTitle()%></title>
<odin:head/>
</head>
<script LANGUAGE="JavaScript">
	function showOriOpLog(){
		alert(window.dialogArguments.parent.SysParam.sysdate);
		Ext.getCmp('opLogList').show(Ext.getCmp('opLogList'));
	}
	
	function doClose(){
		close();   
	}
	
	function doRollback(){
		alert("回退成功");     
	}
</script>
<body onload="javascript:queryLog();">
<odin:base>
<odin:gridWithPagingTool property="opLogList" title="操作日志" url="/pageQueryAction.do?method=query" isFirstLoadData="false" rowDbClick="showOriOpLog" autoFill="false" forceNoScroll="false" width="920" height="500">
	<odin:gridJsonDataModel id="opseno" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="opseno" />
	  	<odin:gridDataCol name="functionid"/>
	  	<odin:gridDataCol name="aae002"/>
	  	<odin:gridDataCol name="aac001"/>
	  	<odin:gridDataCol name="aab001"/>
	  	<odin:gridDataCol name="prcol1"/>
	  	<odin:gridDataCol name="prcol2"/>
	  	<odin:gridDataCol name="prcol3"/>
	  	<odin:gridDataCol name="prcol4"/>
	  	<odin:gridDataCol name="prcol5"/>
	  	<odin:gridDataCol name="prcol6"/>
	  	<odin:gridDataCol name="prcol7"/>
	  	<odin:gridDataCol name="prcol8"/>
	  	<odin:gridDataCol name="aae011"/>
	  	<odin:gridDataCol name="aae036"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridColumn id="yes" header="日志流水号" dataIndex="opseno" width="75"/>
	  	<odin:gridColumn  header="摘要1" dataIndex="prcol1" width="80"/>
	  	<odin:gridColumn  header="摘要2" dataIndex="prcol2" width="80"/>
	  	<odin:gridColumn  header="摘要3" dataIndex="prcol3" width="80"/>
	  	<odin:gridColumn  header="摘要4" dataIndex="prcol4" width="80"/>
	  	<odin:gridColumn  header="摘要5" dataIndex="prcol5" width="80"/>
	  	<odin:gridColumn  header="摘要6" dataIndex="prcol6" width="80"/>
	  	<odin:gridColumn  header="摘要7" dataIndex="prcol7" width="80"/>
	  	<odin:gridColumn  header="摘要8" dataIndex="prcol8" width="80"/>
	  	<odin:gridColumn  header="经办人" dataIndex="aae011" width="75"/>
	  	<odin:gridColumn  header="经办日期" dataIndex="aae036" isLast="true"width="110"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
			'totalCount':2,
	 		'data':[
	 				{opseno:"1234",functionid:"adfad",aae002:"200712",aac001:"21433",aab001:"",prcol1:"asdf",prcol2:"assdfad",prcol3:"asdf",prcol4:"asdf",prcol5:"asdf",prcol6:"asdf",prcol7:"asdf",prcol8:"asdf",aae011:"系统管理员",aae036:"2007-12-21 13:23:45"},
	 				{opseno:"1235",functionid:"adfad",aae002:"200712",aac001:"21433",aab001:"",prcol1:"asdf",prcol2:"assdfad",prcol3:"asdf",prcol4:"asdf",prcol5:"asdf",prcol6:"asdf",prcol7:"asdf",prcol8:"asdf",aae011:"系统管理员",aae036:"2007-12-21 13:23:45"}
	 			   ]	
		}
	</odin:gridJsonData>
</odin:gridWithPagingTool>
<table border="0" align="center" width="100%">
   <tr>
    <td width="100%" align="center">
    	<table border="0">
    		<tr>
    			<td><odin:button text="&nbsp;回退&nbsp;" handler ="doRollback"/></td>
    			<td>&nbsp;&nbsp;</td>
   				<td><odin:button text="&nbsp;关闭&nbsp;" handler ="doClose"/></td>
    		</tr>
    	</table>
    </td>
   </tr>
</table> 
</odin:base>
<script LANGUAGE="JavaScript">
	function queryLog(){
		var querySQL="select a.opseno,a.functionid,a.aae002,a.aac001,a.aab001,a.prcol1,a.prcol2,a.prcol3,a.prcol4,a.prcol5,a.prcol6,a.prcol7,a.prcol8,a.aae011,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') aae036 from sbds_userlog a where a.functionid='<%=functionid%>' and a.eae024='0' order by a.opseno desc";
		//alert(querySQL);
		odin.reloadPageGridWithQueryParams("opLogList",{querysql:querySQL,sqltype:"SQL"});
	}
</script>
</body>
</html>