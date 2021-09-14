<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>统筹区选择</title>
<odin:head/>
<script type="text/javascript">
	var MDParam={"":"","":""};
</script>
</head>
<body>
<odin:base>
	<odin:form action="/com/insigma/siis/local/comm/planarea/OverallAreaAction.do?method=selectOverallArea" method="post">
		<odin:groupBox title="请选择统筹区">
		<table width="24" height="53">
			<tr>
					<td colspan="6" height="15"></td>
			</tr>
			<tr>
					<odin:select property="name" required="true" label="统筹区" data="['name1','默认'],['name2','默认2']"/>
			</tr>
			<tr>
					<odin:hidden property="areaCode"/>
			</tr>
			<tr>
					<odin:hidden property="parentCode"/>
			</tr>
			<tr>
					<odin:hidden property="isOverallArea"/>
			</tr>
			<tr>
				<td colspan="6" height="5"></td>
			</tr>
			<tr>
				<td align="right" colspan="5">
					<odin:button  text="确定" handler="doSave"></odin:button>
				</td>
			</tr>
		</table>
		
		</odin:groupBox>
	</odin:form>
</odin:base>
</body>
<script type="text/javascript">
	var rrs = parent.rs;
	var selectData = new Array(rrs.length);
	for(i=0;i<selectData.length;i++){
		selectData[i]={};
		selectData[i].key = rrs[i].data.name;
		selectData[i].value = rrs[i].data.name;
	}
	Ext.onReady(function(){
	   odin.reSetSelectData("name",selectData);
	});
	
	var g_planarea=null;
	function doSave(response){
		for(i=0;i<rrs.length;i++){
			if(rrs[i].data.name==document.all.name.value){
				document.all.areaCode.value=rrs[i].data.areaCode;
				document.all.parentCode.value=rrs[i].data.parentCode;
				document.all.isOverallArea.value=rrs[i].data.isOverallArea;
				break;
			}
		}
		odin.submit(document.overallAreaBean,sucFun,failFun);	
	}
	function sucFun(rm){
		parent.odin.ext.getCmp('win3').hide();
		parent.info(rm.mainMessage);
	}
	function failFun(rm){
		alert("设置统筹区信息失败！");
		parent.closeWin();
	}
</script>
</html>
