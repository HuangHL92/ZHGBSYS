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
			      alert('��ť�����¼�������');     
			  }
</script>
<table width="100%" >     
<tr>
	<td>
		<odin:groupBox title="��������">
			<table border="0" id="myform1" align="center" width="100%"  cellpadding="0" cellspacing="0">
			<odin:tabLayOut />
			   <tr>
			     <odin:textEdit property="EAE073" label="������������" required="true"  /> 
			     <odin:textEdit property="AAE147" colspan="2" disabled="true"/>
			     <odin:select property="AIA005" label="����������ʽ" data="['01', 'ĳ��������Ӷ���'],['02', 'ĳ��������Ӱٷֱ�'],['03', '���ݽɷ����޵���'],['04', '�ܶ����Ӱٷֱ�']" required="true"></odin:select>
			   </tr>
			   <tr>
			     <odin:select property="AAD124" label="����֧����Ŀ" data="['1', '��������'],['2', 'Ӫ����'],['3', 'ҽ�Ʋ���']"></odin:select> 
			     <odin:textEdit property="EAE068" colspan="2" disabled="true"/>
			     <odin:textEdit property="AIA004" label="��������"  required="true"/>
			   </tr>		   
			</table>
		</odin:groupBox> 				
	</td>	
</tr>
</table>
<odin:gridWithCheckBox property="grid1" title="ҵ����Ϣ" width="764" height="360">
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
  <odin:gridColumn  header="�籣����" dataIndex="second" />
  <odin:gridColumn  header="���֤����"  dataIndex="third" />
  <odin:gridColumn  header="����" dataIndex="fourth" />
  <odin:gridColumn  header="�Ա�" dataIndex="fifth" />
  <odin:gridColumn  header="��������" dataIndex="sixth"/>
  <odin:gridColumn  header="��ϵ��ַ" dataIndex="seventh" />
  <odin:gridColumn  header="��������" dataIndex="eighth" />
  <odin:gridColumn  header="��֤״̬" dataIndex="ninth"/>
  <odin:gridColumn  header="��Ч��־" dataIndex="tenth"/>
  <odin:gridColumn  header="��ע" dataIndex="eleventh" isLast="true" />
</odin:gridColumnModel>
<odin:griddata>
        ['007','331023198410286637','�ɵ�','��','1984-10-28','����','310015','��','��','��']
</odin:griddata>		
</odin:gridWithCheckBox>

<table border="0" id="myform3" align="center" width="750"  cellpadding="0" cellspacing="0">
   <tr>
    <td width="330"></td>
    <td width="60" ><odin:button text="������־" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="δ��֤��ѯ" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="����֤��ѯ" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp����&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp����&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp����&nbsp" handler ="doSave" /></td>
    <td width="60" align="center"><odin:button text="&nbsp�ر�&nbsp" handler ="doSave" /></td>
   </tr>
</table> 
 
</odin:base>
</body>
</html>