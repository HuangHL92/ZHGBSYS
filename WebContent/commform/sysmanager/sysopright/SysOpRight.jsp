<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>业务操作权限维护</title>
<odin:head/>

<script type="text/javascript" src="<%=request.getContextPath()%>/commform/sysmanager/sysopright/SysOpRight.js"></script>


<odin:MDParam></odin:MDParam>
</head>
<body>
<odin:base>

<table width="98%" align="center" >  
  
<tr>
	<td>
		
		<odin:toolBar property="myToolBar">
 			<odin:fill></odin:fill>	
 			<odin:buttonForToolBar text="增加" handler="doAdd" isLast="true"/>
		</odin:toolBar>
	
		<odin:editgrid property="list" topBarId="myToolBar" title="权限列表" autoFill="false" width="780" height="350" beforeedit="doReady" sm="row">
			<odin:gridDataModel>	
				<odin:gridDataCol name="oprightcode" />
	  			<odin:gridDataCol name="oprightdesc"/>
	  			<odin:gridDataCol name="aaaa" isLast="true"/>
			</odin:gridDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn  header="操作权限编号" dataIndex="oprightcode" width="160"/>
				<odin:gridColumn  header="操作权限描述" dataIndex="oprightdesc" width="513"/>
			  	<odin:gridColumn  header="操 作" dataIndex="aaaa" width="80" editor="text" isLast="true"/>
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			 		data:[]	
				}
			</odin:gridJsonData>
		</odin:editgrid>
		<br>
	</td>
</tr>  
  
<tr>
	<td>
		<odin:groupBox title="增加/修改权限" >
		   	<table width="98%"  cellpadding="0" cellspacing="0" >
		   	<odin:tabLayOut/>	
		   		<tr>
		   			<td>
		   		 		<table id="aaa" cellpadding="0" cellspacing="0" width="98%">
		   	 	 		<odin:tabLayOut/>
		   	 	 			<tr>
		   						<odin:textEdit property="oprightcode" label="操作权限编号" required="true" />
		   						<td></td><td></td>
		   						<td></td><td></td>
		   					</tr>
		   				</table>
		   			</td>	
		   		</tr>
		   	
		   		<tr>
		   			<td>
		   		 		<table id="bbb" cellpadding="0" cellspacing="0" width="98%">
		   	 	 		<odin:tabLayOut/>
		   	 	 			<tr>
		   						<odin:textEdit property="oprightdesc" label="操作权限描述" required="true" colspan="4" size="78" />
		   						<td>
									<odin:button text="保 存" handler="doSave"/>
								<td>
		   					</tr>
		   				</table>
		   			</td>	
		   		</tr>
		   	   		
		   </table>
		</odin:groupBox>
	</td>
</tr>	

</table>

</odin:base>
</body>
</html>