<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead />
</head>
<body>

<object id="CardReader1" codebase="<%=request.getContextPath()%>/sys/activex/FirstActivex.cab#version=1,0,1,4" classid="CLSID:F225795B-A882-4FBA-934C-805E1B2FBD1B" >
	<param name="_Version" value="65536"/>
	<param name="_ExtentX" value="2646"/>
	<param name="_ExtentY" value="1323"/>
	<param name="_StockProps" value="0"/>
	<param name="port" value="����1"/>
	<param name="PhotoPath" value=""/>
	<param name="ActivityLFrom" value=""/>
	<param name="ActivityLTo" value="" />
</object>

<script type="text/javascript">
	Ext.onReady(
		function(){
			var cardReader = document.getElementById("CardReader1");
			cardReader.setPortNum(0); //�Զ�ѡ��˿�
			var ret = cardReader.ReadCard(); //����
			if(ret == 0x90){ //��������
				var jsonStr = {};
				jsonStr.aae135 = cardReader.CardNo(); //���֤
				jsonStr.psquery = jsonStr.aae135; //psquery
				jsonStr.aac003 = cardReader.NameL(); //����
				jsonStr.aac004 = cardReader.Aac004()=="1"?"1":"2"; //�Ա�1��0Ů 
				jsonStr.mz = cardReader.Nation(); //����
				jsonStr.bdate = Date.parseDate(cardReader.Born(), 'Ymd'); //��������
				jsonStr.address = cardReader.Address(); //��ַ
				jsonStr.police = cardReader.Police(); //ǩ������
				
				
				parent.doQuerySelect(jsonStr);//�Ը�ҳ���������
				parent.Ext.get(document.body).unmask(); //ȥ��loading
				parent.triggerOnChange(); //����onChange�¼�
			}else{//��������
				cardReader.GetState(); //��ʾ������Ϣ
				parent.Ext.get(document.body).unmask(); //ȥ��loading
			}
			/*����
			var jsonStr = {};
			jsonStr.aae135 = '330219197801010102'; //���֤
			jsonStr.psquery = jsonStr.aae135; //psquery
			jsonStr.aac003 = '�ô��'; //����
			jsonStr.aac004 = "1"=="1"?"1":"2"; //�Ա�1��0Ů 
			jsonStr.mz = "01"; //����
			jsonStr.bdate = Date.parseDate("19780101", 'Ymd'); //��������
			jsonStr.address = "���Ե�ַ"; //��ַ
			jsonStr.police = "����ǩ������"; //ǩ������
			jsonStr.validDateFrom = Date.parseDate("20000101", 'Ymd'); //��ʼ��Ч��
			jsonStr.validDateTo = Date.parseDate("20100101", 'Ymd'); //��ֹ��Ч��
			
			parent.doQuerySelect(jsonStr);//�Ը�ҳ���������
			parent.Ext.get(document.body).unmask(); //ȥ��loading
			parent.triggerOnChange(); //����onChange�¼�
			*/
		}
	);
</script>
</body>

</html>