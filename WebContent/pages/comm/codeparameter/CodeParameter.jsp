<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����ά��</title>
<odin:head/>

<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/CodeParameter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/addParameterStub.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/deleteParameterStub.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/loadBasicMessageStub.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/loadParameterMessageStub.js"></script>


<odin:MDParam></odin:MDParam>
</head>
<body>
<odin:base>

<odin:keyMap property="testAddKey">
    <odin:keyMapItem key="W" alt="true"  fn="odin.openOpLogList"></odin:keyMapItem>
	<odin:keyMapItem key="S" alt="true"  fn="save" isLast="true"></odin:keyMapItem>
</odin:keyMap>

<odin:form action="/com/insigma/siis/local/module/common/codeparameter/SaveAction.do?method=save" method="post">
<table>
	<tr>
		<td height="26" >&nbsp;</td>
	</tr>
</table>

<odin:floatDiv property="floatToolDiv"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="floatToolDiv">
	<odin:textForToolBar text="ҵ��������"/>
	<odin:fill/>
	<odin:opLogButtonForToolBar/>
	<odin:buttonForToolBar isLast="true" text="����(<U>S</U>)"  handler="save" iconCls="iconToolBarBtnSave"/>
</odin:toolBar>

<table width="100%">
	<tr>
		<td width="300" align="left" valign="top">
			<div id="grid1"></div>
		</td>			
		<td align="left" valign="top">
			<div id="grid2"></div>
		</td>
	</tr>
	
<odin:hidden property="deleteList"/>
	
</table>
<!-- 
<odin:toolBar property="myToolBar1">
			<odin:fill></odin:fill>	
			<odin:buttonForToolBar text="ɾ��" handler="doDelete1" iconCls="iconToolBarBtnRemove"/>
			<odin:separator />
			<odin:buttonForToolBar text="����" handler="doAdd1" iconCls="iconToolBarBtnAdd" isLast="true"/>
</odin:toolBar>
 -->
<odin:editgrid property="list1" topBarId="myToolBar1" applyTo="grid1" title="�������Ϣ" width="300" height="410" beforeedit="doReady" sm="row">
	<odin:gridDataModel>
				<odin:gridDataCol name="aaa100" />
				<odin:gridDataCol name="aaa101" />
				<odin:gridDataCol name="aaa104" isLast="true" />
	</odin:gridDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn/>	
				<odin:gridColumn  header="�������" dataIndex="aaa100" width="30" editor="text" />  
				<odin:gridColumn  header="�������" dataIndex="aaa101" width="70" editor="text" />
				<odin:gridColumn  hidden="true" header="ά����־" dataIndex="aaa104" width="30" editor="text" isLast="true" />
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
      		data:[]
  		}
	</odin:gridJsonData>		
</odin:editgrid>


<odin:toolBar property="myToolBar2">
			<odin:fill></odin:fill>	
			<odin:buttonForToolBar text="ɾ��" handler="doDelete2" iconCls="iconToolBarBtnRemove"/>
			<odin:separator />
			<odin:buttonForToolBar text="����" handler="doAdd2" iconCls="iconToolBarBtnAdd" isLast="true"/>
</odin:toolBar>
<odin:gridSelectColJs name="aae100" codeType="ACTIVE"></odin:gridSelectColJs>
<odin:editgrid property="list2" topBarId="myToolBar2" applyTo="grid2" title="��������Ϣ" width="480"  height="410" beforeedit="doReady2" afteredit="doEdit2" sm="row">
	<odin:gridDataModel>
				<odin:gridDataCol name="aaa100" />
				<odin:gridDataCol name="aaa102" />
				<odin:gridDataCol name="aaa103" />
				<odin:gridDataCol name="aaa105" />
				<odin:gridDataCol name="aae100" />
				<odin:gridDataCol name="aaa104" />
				<odin:gridDataCol name="aaz093" type="int"/>
				<odin:gridDataCol name="flag" type="int" isLast="true"/>
	</odin:gridDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn/>	
				<odin:gridColumn  header="�������" dataIndex="aaa100" width="35" editor="text" />  
				<odin:gridColumn  header="����ֵ" dataIndex="aaa102" width="30" editor="text" />
				<odin:gridColumn  header="��������" dataIndex="aaa103" width="70" editor="text" />
				<odin:gridColumn  header="��������" dataIndex="aaa105" width="35" editor="text" />
				<odin:gridColumn  header="��Ч��־λ" dataIndex="aae100" editor="select" codeType="ACTIVE" width="40"/>
				<odin:gridColumn  hidden="true" header="ά����־" dataIndex="aaa104" width="35" editor="text" />
				<odin:gridColumn  hidden="true" header="����ID" dataIndex="aaz093" width="35" />
				<odin:gridColumn  hidden="true" header="��־λ" dataIndex="flag" width="35" isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
			{
      			data:[]
  			}
	</odin:gridJsonData>		
</odin:editgrid>
	
</odin:form>
</odin:base>
</body>
</html>