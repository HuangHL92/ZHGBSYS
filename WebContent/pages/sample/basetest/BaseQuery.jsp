<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
var isRun=true;
var loadtime=0;
function queryStart(){
	loadtime = new Date().getTime();
	radow.doEvent('grid1.dogridquery');		
}
</script>
<odin:toolBar property="floatToolBar" applyTo="floatToolDiv">
	<odin:fill />
	<odin:buttonForToolBar text="查询(<U>Q</U>)" id="doQuery" isLast="true"
		cls="x-btn-text-icon" icon="images/search.gif" handler="queryStart" />

</odin:toolBar>
<odin:floatDiv property="floatToolDiv"></odin:floatDiv>
<br>
<br>

<div id="querycondition">
<table border="0" id="myform" align="center" width="100%"
	cellpadding="0" cellspacing="0">
	<odin:tabLayOut />
	<tr>
		<odin:textEdit property="ake001" label="药品编码" maxlength="20" />
		<odin:textEdit property="ake002" label="医保通用名" maxlength="50" />
		<odin:select property="saka065" label="收费等级" codeType="AKA065" />
	</tr>
	<tr>
		<odin:textEdit property="saka020" label="拼音简码" maxlength="25" />
		<odin:textEdit property="saka021" label="五笔简码" maxlength="25" />
		<odin:select property="saae100" label="有效标志" codeType="AAE100"
			value="1" />
	</tr>
	<tr>
		<odin:textEdit property="cbka009" label="厂商" maxlength="25" />
		<odin:textEdit property="caka094" label="招标码" colspan="6" width="410" />
	</tr>
	<odin:hidden property="aaz231" />
	<odin:hidden property="digest" />
	<odin:hidden property="aaa" value="0" />
	<!-- 能否导出 -->
</table>
</div>


<table width="100%">
	<tr>
		<td align="center"><odin:groupBoxNew title="查询条件"
			contentEl="querycondition" property="mygroupBox"></odin:groupBoxNew>
		</td>
	</tr>
</table>

<odin:toolBar property="myToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="导出EXCEL" isLast="true"
		handler="expPersonInfo" id="expMyExcel" cls="x-btn-text-icon"
		icon="images/out.gif"></odin:buttonForToolBar>
</odin:toolBar>
<odin:gridSelectColJs name="ake003" codeType="AKE003"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aka065" codeType="AKA065"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aka063" codeType="AKA063"></odin:gridSelectColJs>
<odin:gridSelectColJs name="ake004" codeType="AKE004"></odin:gridSelectColJs>
<odin:gridSelectColJs name="sor" codeType="SOR001"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aka036"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aka064" codeType="AKA064"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eae047" codeType="EAE047"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aka032"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aae100" codeType="AAE100"></odin:gridSelectColJs>
<odin:grid property="grid1" pageSize="100" isFirstLoadData="false" title="查询结果" autoFill="false" width="775" height="410"
	bbarId="pageToolBar" bufferView="true" cacheSize="50" url="/">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">

		<odin:gridDataCol name="sor" />
		<odin:gridDataCol name="aaz231" />
		<odin:gridDataCol name="ake001" />
		<odin:gridDataCol name="ake003" />
		<odin:gridDataCol name="ake002" />
		<odin:gridDataCol name="aka062" />
		<odin:gridDataCol name="aae030" />
		<odin:gridDataCol name="aae031" />
		<odin:gridDataCol name="aka061" />
		<odin:gridDataCol name="aka020" />
		<odin:gridDataCol name="aka021" />
		<odin:gridDataCol name="aka065" />
		<odin:gridDataCol name="aka063" />
		<odin:gridDataCol name="aka070" />
		<odin:gridDataCol name="aka052" />
		<odin:gridDataCol name="aka036" />
		<odin:gridDataCol name="bka009" />
		<odin:gridDataCol name="aka094" />
		<odin:gridDataCol name="aka095" />
		<odin:gridDataCol name="aka096" />
		<odin:gridDataCol name="aka074" />
		<odin:gridDataCol name="aka097" />
		<odin:gridDataCol name="aka098" />
		<odin:gridDataCol name="aka099" type="float" />
		<odin:gridDataCol name="aka100" type="float" />
		<odin:gridDataCol name="aka101" type="float" />
		<odin:gridDataCol name="aka069" type="float" />
		<odin:gridDataCol name="aae013" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn width="30"/>
		<odin:gridColumn hidden="true" header="社保目录ID" dataIndex="aaz231"
			width="60" />
		<odin:gridColumn header="状态" dataIndex="sor" codeType="SOR001"
			editor="select" hidden="true" width="40" />
		<odin:gridColumn header="开始时间" dataIndex="aae030" width="70" />
		<odin:gridColumn header="结束时间" dataIndex="aae031" width="70" />
		<odin:gridColumn header="医保编码" dataIndex="ake001" width="80" />
		<odin:gridColumn header="目录类别" dataIndex="ake003" codeType="AKE003"
			editor="select" width="80" />
		<odin:gridColumn header="医保通用名" dataIndex="ake002" width="80" />
		<odin:gridColumn header="英文名" dataIndex="aka062" width="100" />
		<odin:gridColumn header="拼音简码" dataIndex="aka020" width="60" />
		<%-- 		<odin:gridColumn header="五笔简码" dataIndex="aka021" width="60" />
		<odin:gridColumn header="医保剂型" dataIndex="aka070" width="60" />
		<odin:gridColumn header="收费等级" dataIndex="aka065" codeType="AKA065"
			editor="select" width="60" />
		<odin:gridColumn header="医疗发票类别" dataIndex="aka063" codeType="AKA063"
			editor="select" width="80" />
		<odin:gridColumn header="限制范围" dataIndex="aka036" width="120"
			codeType="YESNO" editor="select" />
		<odin:gridColumn header="招标码" dataIndex="aka094" width="60" />
		<odin:gridColumn header="厂商" dataIndex="bka009" width="60" />
		<odin:gridColumn header="招标通用名" dataIndex="aka095" width="100" />
		<odin:gridColumn header="招标商品名" dataIndex="aka061" width="80" />
		<odin:gridColumn header="招标剂型" dataIndex="aka096" width="80" /> --%>
		<%-- 		<odin:gridColumn header="规格" dataIndex="aka074" width="80" />
		<odin:gridColumn header="转化比" dataIndex="aka097" width="60" />
		<odin:gridColumn header="单位" dataIndex="aka052" width="60" />
		<odin:gridColumn header="包装材质" dataIndex="aka098" width="80" />
		<odin:gridColumn header="政府定价" dataIndex="aka099" edited="false"
			editor="number" width="80" />
		<odin:gridColumn header="招标定价" dataIndex="aka100" edited="false"
			editor="number" width="100" />
		<odin:gridColumn header="医院零售定价" dataIndex="aka101" edited="false"
			editor="number" width="100" /> --%>
		<odin:gridColumn header="自理比例" dataIndex="aka069" edited="false"
			isLast="true" editor="number" width="60" />
	</odin:gridColumnModel>
</odin:grid>

<odin:keyMap property="pageKey">
	<odin:keyMapItem key="Q" isLast="true" fn="queryStart"></odin:keyMapItem>
</odin:keyMap>
<odin:window src="/sys/excel/simpleExpExcelWindow.jsp" title="导出数据"
	id="simpleExpWin" height="160"></odin:window>
<script>
    var uuid="<%=java.util.UUID.randomUUID().toString()%>";
	var fileNameSim = "药品目录"+uuid;
	var sheetNameSim = "药品目录";
	var querySQLSim = "";
	
	function expPersonInfo(){
		
		var aaa = document.all.aaa.value;
		
		if(aaa == "0"){
			odin.alert("请先查询，再导出数据！");
			return;
		}
	
		var ake001 =document.all.ake001.value;
		var ake002 =document.all.ake002.value;
		var aka065 =document.all.saka065.value;
		var aka020 =document.all.saka020.value;
		var aka021 =document.all.saka021.value;
		var aae100 =document.all.saae100.value; 
		var bka009 =document.all.cbka009.value;
		var aka094 =document.all.caka094.value;
	
		querySQLSim = "select a.ake001 药品编码,a.ake002 药品名称,a.aka020 拼音简码,a.aka062 英文名,a.aae030 开始时间,a.aae031 结束时间,a.aka065  收费项目等级,'' 医院等级,'' 使用范围,0 门诊限额,0 住院限额,0 门诊限量,0 住院限量,a.ake004 产地属性,a.aka070 药品剂型,a.aka074 药品规格,a.aka052 单位,a.eke001 药品产地,a.bka009 厂商,a.bka008 限制使用内容,a.aka063 收费类别,a.aka094 招标码,a.aka095 招标通用名,a.aka061 招标商品名,a.aka097 转化比,a.aka098 包装材质,a.aka099 政府定价,a.aka100 招标定价,a.aka101 医院零售定价,a.aka069 参考医保自理比例" 
					+" from ka02 a where 1=1";
		if(ake001.length>0) {
			querySQLSim += " and ake001 like '%"+ake001+"%'";
		}
		if(ake002.length>0) {
			querySQLSim += " and ake002 like '%"+ake002+"%'";
		}
		if(aka065.length>0) {
			querySQLSim += " and aka065='"+aka065+"'";
		}
		if(aka020.length>0) {
			querySQLSim += " and Upper(aka020) like '%"+aka020.toUpperCase()+"%'";
		}
		if(aka021.length>0) {
			querySQLSim += " and Upper(aka021) like '%"+aka021.toUpperCase()+"%'";
		}
		if(bka009.length>0) {
			querySQLSim += " and Upper(bka009) like '%"+bka009.toUpperCase()+"%'";
		}
		if(aka094.length>0) {
			querySQLSim += " and Upper(aka094) like '%"+aka094.toUpperCase()+"%'";
		}
		
		if(aae100.length>0) {
			querySQLSim += " and aae100='"+aae100+"'";
		}
		odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/simpleExpExcelWindow.jsp");
	}
odin.ext.onReady(function(){
	var g1 = odin.ext.getCmp('grid1');
	g1.store.on('load',function(){
		if(isRun){
			var endTime=new Date().getTime();
			alert("表格查询耗时："+(endTime-loadtime)+'ms');
			loadtime=0;
		}
	});
	g1.getBottomToolbar().addListener('beforechange',function(){
		loadtime = new Date().getTime();
	});
});
function showPageTime(){
	alert("页面加载耗时："+document.getElementById("loadedDiv").innerHTML+"\n\
		页面渲染耗时：" + document.getElementById("finishedDiv").innerHTML);
}	
</script>
