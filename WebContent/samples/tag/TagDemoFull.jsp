<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ǩ����</title>
<odin:head />
</head>

<body>
<odin:base>
<input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;">
<table align="center">
	    <script>
		      function doSave()
			  {
			      alert('��ť�����¼�������'); 
			      alert('123:'+document.getElementById('zjlx11').value);  
			      alert(Ext.get('zjlx11').next().value);  
			      Ext.get('zjlx11').next().value= Ext.get('zjlx11').next().value.substring(5);
			  }
		</script>
	   <tr>
	     <td nowrap="nowrap" colspan="2" align="center"><odin:button text="����" handler ="doSave" /></td><td colspan="2" nowrap="nowrap" align="center"><epsoft:button text="ȡ��" handler ="doSave" /></td>
	   </tr>
	   </table>
<table align="center">
<!--COLGROUP><COL width='16%'><COL width='17%'><COL width='16%'><COL width='17%'><COL width='16%'><COL width='17%'></COLGROUP-->
<!--tr>
   <td nowrap="nowrap">��λ����</td>
    <odin:textEdit property="aab001"></odin:textEdit> 
   <td nowrap="nowrap">��λ����</td>
      <odin:textEdit property="aab002"></odin:textEdit>
   <td nowrap="nowrap">��λ����</td>
      <odin:textEdit property="aab003"></odin:textEdit>
 </tr-->
 <tr>
   <odin:textEdit property="aab004" label="��λ����" readonly="true" size="30"></odin:textEdit>
   <odin:textEdit property="aab005" label="��λ����" disabled="true" size="30"></odin:textEdit>
   <odin:textEdit property="aab006" required="true" label="��λ����" size="30"></odin:textEdit>
 </tr>
  <tr>
   <odin:dateEdit property="aab007" readonly="true" size="20" label="��λ����"></odin:dateEdit>
   <odin:textEdit property="aab008" label="��λ����" mask="email" colspan="4" size="60"></odin:textEdit>
 </tr>
  <tr>
   <odin:select property="aab009" disabled="true" editor="true" label="��λ����" colspan="4" size="80">
['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']
   </odin:select>
<odin:numberEdit property="aab010" required="true" decimalPrecision="2" label="��λ����" size="30"></odin:numberEdit>
 </tr>
 <tr>
   <odin:textarea property="aab011" label="��λ����" value="var data = Ext.getCmp('grid1').store.data;
   alert(data.itemAt(0).get('company'));
   alert(data.itemAt(0).get('price'));
   alert(data.itemAt(0).get('change'));
   alert(data.itemAt(0).get('lastChange'));
   alert(data.length);

alert(Ext.getCmp('grid6').store.data.length);
alert(Ext.getCmp('grid6').store.data.itemAt(0).get('price'));

Ext.getCmp('grid6').store.data.itemAt(0).set('price',40);" colspan="5" cols="80" rows="10">

    </odin:textarea>
   <td>
   <script>
    function doFun()
	{
	     alert(document.getElementById('aab011').value);
	     eval(document.getElementById('aab011').value);
	}
   </script>
   <odin:button text="ִ��" handler ="doFun" /></td>
 </tr>
 <tr>
   <td colspan="6">
      <odin:groupBox title="������Ϣ">
	    <table align="center">  
		 <tr>
   <odin:dateEdit property="zaab007" disabled="true" size="30" label="1��λ����"></odin:dateEdit>
   <odin:textEdit property="aab021" label="��λ����" disabled="true" size="30"></odin:textEdit>
   <odin:textEdit property="aab022" required="true" label="��λ����" size="30"></odin:textEdit>
       </tr>
	   </table>
	  </odin:groupBox> 
   </td>
 </tr> 
  <tr>
   <td colspan="6">
   <odin:simplePanel title="����" width="700" bodyWidth="600">
      <odin:groupBox title="������Ϣ">
	    <table align="center" width="100%">  
		 <tr>
   <odin:textEdit property="aab030" label="��λ����" readonly="true" ></odin:textEdit><odin:textEdit property="aab031" label="��λ����" disabled="true"></odin:textEdit>
       </tr>
	   <tr>

	     <td nowrap="nowrap" colspan="2" align="right"><odin:button text="  ����  " handler ="doSave" /></td><td colspan="2" nowrap="nowrap" align="left"><odin:button text="  ȡ��  " handler ="doSave" /></td>

	   </tr>
	   </table>
	  </odin:groupBox>
	</odin:simplePanel>  
   </td>
 </tr>
</table> 
<br><center>
<odin:simplePanel title="��form" width="807" bodyWidth="777">
<odin:groupBox title="������Ϣ">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="����"/> 
	 <odin:textEdit property="worker1" label="����"/>
	 <odin:textEdit property="worker2" label="����"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="��λ����" />
	 <odin:textEdit property="name" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="����" />
	  <odin:select property="zjlx" readonly="true" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox>
 <br><br>
 <odin:groupBox title="������Ϣ">
<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker10" label="����"/> 
	 <odin:textEdit property="worker11" label="����"/>
	 <odin:textEdit property="worker12" label="����"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm1" label="��λ����" />
	 <odin:textEdit property="name1" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="����" />
	  <odin:select property="zjlx1" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox> 
 <br><br>
 <table border="0" id="myform" width="100%" align="center"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker110" label="����"/> 
	 <odin:textEdit property="worker111" label="����"/>
	 <odin:dateEdit property="aab1007" size="15" label="��λ����"></odin:dateEdit>
   </tr>
  <tr>
     <odin:textEdit property="dwbm11" label="��λ����" />
	 <odin:textEdit property="name11" label="��λ����" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming11" label="����" />
	  <odin:select property="zjlx11" editor="true" label="֤������" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" >
	  </odin:select>
	  <td nowrap="nowrap"><odin:checkbox property="workplace" label="�����ص�1" /></td>
	  <td>
	  <odin:radio property="workplace2" value="1" label="�����ص�2" />
	  </td>

   </tr>
   <tr>
      <td colspan="3"></td>
      <td><odin:radio property="workplace3" value="1" label="����1" /></td>
	  <td><odin:radio property="workplace3" value="2" label="����2" /></td>
	  <td><odin:radio property="workplace3" value="3" label="����3" /></td>
   </tr>	 
 </table>   
</odin:simplePanel> 
</center>
<br>
<table><tr><td>
<div id="gridDiv_grid1"></div></td>
<td valign="top"><div id="grid3">
 <odin:groupBox title="������Ϣ">
<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="wtrker10" label="����"/> 
   </tr>
  <tr>
     <odin:textEdit property="dwtbm1" label="��λ����" />
   </tr>
   <tr>
      <odin:textEdit property="xmting1" label="����" />
   </tr>	 
 </table>
 </odin:groupBox> 
</td></tr></table>
<odin:grid property="grid1" applyTo="gridDiv_grid1" title="�ҵı��" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  header="�۸�" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:grid>

<odin:gridWithCheckBox property="grid2" forceNoScroll="false" autoFill="false" title="�ҵı��(����ѡ��)" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:gridWithCheckBox>

<odin:grid property="grid3" title="�ҵı��(���к�)" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:grid>
<odin:gridWithCellSelect property="grid4" title="�ҵı��(����ѡ�ֵ�Ԫ��)" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:gridWithCellSelect>

<odin:gridWithPagingTool autoFill="false" forceNoScroll="false" url="/samples/tag/pageJsonData.jsp" property="grid5" pageSize="15" title="��ҳ���" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>		
</odin:gridWithPagingTool>

<odin:grid property="grid6" title="�ҵı��(Ĭ������Ϊ���飬�������json�ṹ)" width="600">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:gridJsonData>
 {
     'totalCount':2,
	 'data':[{'id':1,'company':'��˾1','price':20.0,'change':12,'lastChange':'���ĸı�'},
	 {'id':2,'company':'��˾2','price':22.0,'change':54,'lastChange':'���ĸı�1'} ]
 }
</odin:gridJsonData>		
</odin:grid>

<odin:editgrid property="grid7" title="�ɱ༭���" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridEditColumn id="yes" header="��˾" width="160" dataIndex="company" editor="text"/>
  <odin:gridEditColumn  header="�۸�" dataIndex="price" editor="number" />
  <odin:gridEditColumn  dataIndex="change" editor="checkbox" />
  <odin:gridEditColumn  dataIndex="pctChange" editor="select"/>
  <odin:gridEditColumn  dataIndex="lastChange"  isLast="true" editor="date"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am']
</odin:griddata>		
</odin:editgrid>


<odin:grid property="grid8" counting="true"  title="����(ͳ��)���" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" countType="sum" dataIndex="price" />
  <odin:gridColumn  countType="average" dataIndex="change" />
  <odin:gridColumn  countType="average" dataIndex="pctChange" />
  <odin:gridColumn  countType="count" dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:grid>


<br><br><br><br>
</odin:base>  
</body>
</html>
