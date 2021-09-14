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
	<param name="port" value="串口1"/>
	<param name="PhotoPath" value=""/>
	<param name="ActivityLFrom" value=""/>
	<param name="ActivityLTo" value="" />
</object>

<script type="text/javascript">
	Ext.onReady(
		function(){
			var cardReader = document.getElementById("CardReader1");
			cardReader.setPortNum(0); //自动选择端口
			var ret = cardReader.ReadCard(); //读卡
			if(ret == 0x90){ //读卡正常
				var jsonStr = {};
				jsonStr.aae135 = cardReader.CardNo(); //身份证
				jsonStr.psquery = jsonStr.aae135; //psquery
				jsonStr.aac003 = cardReader.NameL(); //姓名
				jsonStr.aac004 = cardReader.Aac004()=="1"?"1":"2"; //性别，1男0女 
				jsonStr.mz = cardReader.Nation(); //民族
				jsonStr.bdate = Date.parseDate(cardReader.Born(), 'Ymd'); //出生日期
				jsonStr.address = cardReader.Address(); //地址
				jsonStr.police = cardReader.Police(); //签发机关
				
				
				parent.doQuerySelect(jsonStr);//对父页面进行填入
				parent.Ext.get(document.body).unmask(); //去除loading
				parent.triggerOnChange(); //触发onChange事件
			}else{//读卡出错
				cardReader.GetState(); //提示出错信息
				parent.Ext.get(document.body).unmask(); //去除loading
			}
			/*测试
			var jsonStr = {};
			jsonStr.aae135 = '330219197801010102'; //身份证
			jsonStr.psquery = jsonStr.aae135; //psquery
			jsonStr.aac003 = '好大大'; //姓名
			jsonStr.aac004 = "1"=="1"?"1":"2"; //性别，1男0女 
			jsonStr.mz = "01"; //民族
			jsonStr.bdate = Date.parseDate("19780101", 'Ymd'); //出生日期
			jsonStr.address = "测试地址"; //地址
			jsonStr.police = "测试签发机关"; //签发机关
			jsonStr.validDateFrom = Date.parseDate("20000101", 'Ymd'); //起始有效期
			jsonStr.validDateTo = Date.parseDate("20100101", 'Ymd'); //终止有效期
			
			parent.doQuerySelect(jsonStr);//对父页面进行填入
			parent.Ext.get(document.body).unmask(); //去除loading
			parent.triggerOnChange(); //触发onChange事件
			*/
		}
	);
</script>
</body>

</html>