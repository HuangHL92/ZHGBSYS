<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>  
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
function doTabChange(tabPanel,activeTab){
    //alert(activeTab.getId());
	//odin.ext.getCmp("zj1l3x1r_combo").syncSize();  //�˷�ʽֻ�ܽ��IE��TABҳ���ڲ�������combo������ȹ�С������
	//var w = odin.ext.getCmp("zj1l3x1r_combo").getWidth();
	//alert(w);
	//var lw = odin.ext.getCmp("zj1l3x1r_combo").listWidth;
	//alert(lw);
    //odin.ext.getCmp("zj1l3x1r_combo").minListWidth = w;
    if(activeTab.getId()=="tab3"){
       //document.all("table3").style.display="";
       /*var cmp = odin.ext.getCmp("zj1l3x1r_combo");
       var w = cmp.width;
       cmp.setWidth(0);
       cmp.setWidth(w+16);
       */
       //alert(odin.ext.getCmp("zj1l3x1r_combo").getXType());
       //odin.ext.getCmp("zj1l3x1r_combo").view.refresh();
       
    }
}
</script>
<div style="padding-left:10px;">
<odin:tab id="tab" tabchange="doTabChange">
   <odin:tabModel>
       <odin:tabItem title="������Ϣ" id="tab1"></odin:tabItem>
       <odin:tabItem title="�����Ϣ" id="tab2"></odin:tabItem>
       <odin:tabItem title="������Ϣ" id="tab3" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
       <table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
       <odin:tabLayOut />
       <tr>
     <odin:textEdit property="worker0r" label="����"/>   
	 <odin:textEdit property="worker1r" label="����"/>
	 <odin:textEdit property="worker2r" label="����"/>
   </tr>
   <tr>
     <odin:textEdit property="dwbm1r" label="��λ����" required="true"/>
	 <odin:textEdit property="name1r" label="��λ����"/>
	 <odin:textIconEdit property="dwxx" label="��λ��Ϣ" iconId="btn"></odin:textIconEdit>
   </tr>
   <tr>
      <odin:textEdit property="xming1r" label="����" />   
	  <odin:select disabled="true" property="zjlx1r" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������"></odin:select>
      <odin:textEdit property="name21r" label="��λ����"/>
   </tr>
   <tr>
     <odin:textEdit property="dw1bm1r" label="��λ����" required="true"/>
	 <odin:textEdit property="na1me1r" label="��λ����"/>
	 <odin:textIconEdit property="dw1xx" label="��λ��Ϣ" iconId="btn"></odin:textIconEdit>
   </tr>
   <tr>
      <odin:textEdit property="xm1ing1r" label="����" />   
	  <odin:select  property="zj1lx1r" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������"></odin:select>
      <odin:textEdit property="na1me21r" label="��λ����"/>
   </tr>
   </table>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
      <table id="table1"><tr><td width="94%"><div id="gridDiv_grid1"></div></td></tr></table>
   </odin:tabCont>  
   <odin:tabCont itemIndex="tab3">
       <table border="0" id="table3" align="center" width="100%"  cellpadding="0" cellspacing="0">
       <odin:tabLayOut />
       <tr>
     <odin:textEdit property="wor3ker0r" label="����"/>   
	 <odin:textEdit property="wor3ker1r" label="����"/>
	 <odin:textEdit property="wor3ker2r" label="����"/>
   </tr>
   <tr>
     <odin:textEdit property="dwb3m1r" label="��λ����" required="true"/>
	 <odin:textEdit property="nam3e1r" label="��λ����"/>
	 <odin:textIconEdit width="144" property="dw3xx" label="��λ��Ϣ" iconId="btn"></odin:textIconEdit>
   </tr>
   <tr>
      <odin:textEdit property="xmin3g1r" label="����" />   
	  <odin:select disabled="true" width="144" property="zjl3x1r" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������"></odin:select>
      <odin:textEdit property="name321r" label="��λ����"/>
   </tr>
   <tr>
     <odin:textEdit property="dw13bm1r" label="��λ����" required="true"/>
	 <odin:textEdit property="na1m3e1r" label="��λ����"/>
	 <odin:textIconEdit width="144" property="dw31xx" label="��λ��Ϣ" iconId="btn"></odin:textIconEdit>
   </tr>
   <tr>
      <odin:textEdit property="xm1i3ng1r" label="����" />   
	  <odin:select  property="zj1l3x1r" width="144" listWidth="160" editor="true" data="['1', '���֤'],['2', '��ҵ֤'],['3', '������'],['4', '����֤��']" label="֤������"></odin:select>
      <odin:textEdit property="na13me21r" label="��λ����"/>
   </tr>
   </table>
   </odin:tabCont>
</odin:tab>
</div>

<odin:grid property="grid1" applyTo="gridDiv_grid1" autoFill="false" forceNoScroll="false"  title="�ҵı��" width="770" height="100">
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
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  dataIndex="lastChange" isLast="true" width="160"/>
</odin:gridColumnModel>                                  
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am']
</odin:griddata>		
</odin:grid>


</odin:base>
</body>
</html>