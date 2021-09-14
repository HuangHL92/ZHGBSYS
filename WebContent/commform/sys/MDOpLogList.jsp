<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String functionid=request.getParameter("functionid");
	if(functionid==null) functionid="";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>操作日志</title>
<odin:head/>
<odin:sysParam/>
<script type="text/javascript">var functionid="<%=functionid%>";</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/sys/MDOpLogList.js"></script>
</head>

<body>
<odin:base>
		<span style="position:absolute;top:26;left:300;z-index:1000">
		<table id="simple">
			<tr>
				<td>
					<table width="160"><tr>
						<odin:dateEdit property="querydate" label="" onchange="javascript:simpleQuery();"/>
					</tr></table>
				</td>
				
				<td>&nbsp;<a id="complex" href="#"><span style='font-size:12px'>高级搜索</span></a></td>
			</tr>
		</table>
		</span>
	
		
<odin:window width="451" height="280" title="高级搜索" buttonId="complex" id="complex_win" src="/sys/OpLogComplexQueryWinAction.do"/>

<odin:gridWithPagingTool property="opLogList" title="操作日志" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false" rowDbClick="doDbClick" autoFill="false" forceNoScroll="false" width="781" height="510" 
	topToolBar="true" counting="true" grouping="true" groupCol="functiontitle">
	<odin:gridJsonDataModel id="opseno" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="opseno" />
	  	<odin:gridDataCol name="functionid"/>
	  	<odin:gridDataCol name="functiontitle"/>
	  	<odin:gridDataCol name="aae002"/>
	  	<odin:gridDataCol name="aac001"/>
	  	<odin:gridDataCol name="aab001"/>
	  	<odin:gridDataCol name="digest"/>
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
	  	<odin:gridDataCol name="eae024"/>
	  	<odin:gridDataCol name="rbflag" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridColumn id="yes" header="日志流水号" dataIndex="opseno" width="67" renderer="opsenoToDetailUrl"/>
		<odin:gridColumn  header="业务名称" dataIndex="functiontitle" width="100"/>
		<odin:gridColumn  header="摘要信息" dataIndex="digest" width="320"/>
	  	<odin:gridColumn  header="经办人" dataIndex="aae011" width="60"/>
	  	<odin:gridColumn  header="经办日期" dataIndex="aae036" width="110"/>
	  	<odin:gridColumn  header="回退" dataIndex="opseno" width="40" renderer="opsenoToRollbackUrl"/>
	  	<odin:gridColumn  header="单据打印" dataIndex="opseno" isLast="true" width="60" renderer="opsenoToReprint"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
			'totalCount':0,
	 		'data':[]	
		}
	</odin:gridJsonData>
</odin:gridWithPagingTool>
<script>
	Ext.onReady(function(){
		document.all.querydate.value=SysParam.sysdate;
		simpleQuery();
		//queryLog();		
	});
</script>
</odin:base>
</body>
</html>