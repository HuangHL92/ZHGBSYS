<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ҵ�����Ȩ��ά��</title>
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
 			<odin:buttonForToolBar text="����" handler="doAdd" isLast="true"/>
		</odin:toolBar>
	
		<odin:editgrid property="list" topBarId="myToolBar" title="Ȩ���б�" autoFill="false" width="780" height="350" beforeedit="doReady" sm="row">
			<odin:gridDataModel>	
				<odin:gridDataCol name="oprightcode" />
	  			<odin:gridDataCol name="oprightdesc"/>
	  			<odin:gridDataCol name="aaaa" isLast="true"/>
			</odin:gridDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridColumn  header="����Ȩ�ޱ��" dataIndex="oprightcode" width="160"/>
				<odin:gridColumn  header="����Ȩ������" dataIndex="oprightdesc" width="513"/>
			  	<odin:gridColumn  header="�� ��" dataIndex="aaaa" width="80" editor="text" isLast="true"/>
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
		<odin:groupBox title="����/�޸�Ȩ��" >
		   	<table width="98%"  cellpadding="0" cellspacing="0" >
		   	<odin:tabLayOut/>	
		   		<tr>
		   			<td>
		   		 		<table id="aaa" cellpadding="0" cellspacing="0" width="98%">
		   	 	 		<odin:tabLayOut/>
		   	 	 			<tr>
		   						<odin:textEdit property="oprightcode" label="����Ȩ�ޱ��" required="true" />
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
		   						<odin:textEdit property="oprightdesc" label="����Ȩ������" required="true" colspan="4" size="78" />
		   						<td>
									<odin:button text="�� ��" handler="doSave"/>
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