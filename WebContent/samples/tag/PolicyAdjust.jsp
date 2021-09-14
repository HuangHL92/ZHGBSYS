<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>
<odin:base>
<script>
		      function doSave()
			  {
			      alert('按钮单击事件触发！');     
			  }
</script>
<table width="100%" >     
<tr>
	<td>
		<odin:groupBox title="待遇调整">
			<table border="0" id="myform1" align="center" width="100%"  cellpadding="0" cellspacing="0">
			<odin:tabLayOut />
			   <tr>
			     <odin:textEdit property="EAE073" label="待遇调整政策" required="true"  /> 
			     <odin:textEdit property="AAE147" colspan="2" disabled="true"/>
			     <odin:select property="AIA005" label="待遇调整方式" data="['01', '某项待遇增加定额'],['02', '某项待遇增加百分比'],['03', '根据缴费年限调整'],['04', '总额增加百分比']" required="true"></odin:select>
			   </tr>
			   <tr>
			     <odin:select property="AAD124" label="待遇支付项目" data="['1', '生育津贴'],['2', '营养费'],['3', '医疗补偿']"></odin:select> 
			     <odin:textEdit property="EAE068" colspan="2" disabled="true"/>
			     <odin:textEdit property="AIA004" label="待遇调整"  required="true"/>
			   </tr>		   
			</table>
		</odin:groupBox> 				
	</td>	
</tr>
</table>
<odin:gridWithCheckBox property="grid1" title="业务信息" width="764" height="360">
<odin:gridDataModel>
  <odin:gridDataCol name="second" />   
  <odin:gridDataCol name="third"/>
  <odin:gridDataCol name="fourth"/>
  <odin:gridDataCol name="fifth"/>
  <odin:gridDataCol name="sixth"/>
  <odin:gridDataCol name="seventh"/>
  <odin:gridDataCol name="eighth"/>
  <odin:gridDataCol name="ninth"/>
  <odin:gridDataCol name="tenth"/>
  <odin:gridDataCol name="eleventh" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridColumn  header="社保编码" dataIndex="second" />
  <odin:gridColumn  header="身份证号码"  dataIndex="third" />
  <odin:gridColumn  header="姓名" dataIndex="fourth" />
  <odin:gridColumn  header="性别" dataIndex="fifth" />
  <odin:gridColumn  header="出生日期" dataIndex="sixth"/>
  <odin:gridColumn  header="联系地址" dataIndex="seventh" />
  <odin:gridColumn  header="邮政编码" dataIndex="eighth" />
  <odin:gridColumn  header="认证状态" dataIndex="ninth"/>
  <odin:gridColumn  header="有效标志" dataIndex="tenth"/>
  <odin:gridColumn  header="备注" dataIndex="eleventh" isLast="true" />
</odin:gridColumnModel>
<odin:griddata>
        ['007','331023198410286637','仙道','男','1984-10-28','义乌','310015','是','是','无']
</odin:griddata>		
</odin:gridWithCheckBox>

<table border="0" id="myform3" align="center" width="750"  cellpadding="0" cellspacing="0">
   <tr>
    <td width="330"></td>
    <td width="60" ><odin:button text="操作日志" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="未认证查询" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="已认证查询" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp导出&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp导入&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp保存&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp关闭&nbsp" handler ="doSave" /></td>
   </tr>
</table> 
 
</odin:base>
</body>
</html>