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
	<odin:buttonForToolBar text="��ѯ(<U>Q</U>)" id="doQuery" isLast="true"
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
		<odin:textEdit property="ake001" label="ҩƷ����" maxlength="20" />
		<odin:textEdit property="ake002" label="ҽ��ͨ����" maxlength="50" />
		<odin:select property="saka065" label="�շѵȼ�" codeType="AKA065" />
	</tr>
	<tr>
		<odin:textEdit property="saka020" label="ƴ������" maxlength="25" />
		<odin:textEdit property="saka021" label="��ʼ���" maxlength="25" />
		<odin:select property="saae100" label="��Ч��־" codeType="AAE100"
			value="1" />
	</tr>
	<tr>
		<odin:textEdit property="cbka009" label="����" maxlength="25" />
		<odin:textEdit property="caka094" label="�б���" colspan="6" width="410" />
	</tr>
	<odin:hidden property="aaz231" />
	<odin:hidden property="digest" />
	<odin:hidden property="aaa" value="0" />
	<!-- �ܷ񵼳� -->
</table>
</div>


<table width="100%">
	<tr>
		<td align="center"><odin:groupBoxNew title="��ѯ����"
			contentEl="querycondition" property="mygroupBox"></odin:groupBoxNew>
		</td>
	</tr>
</table>

<odin:toolBar property="myToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����EXCEL" isLast="true"
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
<odin:grid property="grid1" pageSize="100" isFirstLoadData="false" title="��ѯ���" autoFill="false" width="775" height="410"
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
		<odin:gridColumn hidden="true" header="�籣Ŀ¼ID" dataIndex="aaz231"
			width="60" />
		<odin:gridColumn header="״̬" dataIndex="sor" codeType="SOR001"
			editor="select" hidden="true" width="40" />
		<odin:gridColumn header="��ʼʱ��" dataIndex="aae030" width="70" />
		<odin:gridColumn header="����ʱ��" dataIndex="aae031" width="70" />
		<odin:gridColumn header="ҽ������" dataIndex="ake001" width="80" />
		<odin:gridColumn header="Ŀ¼���" dataIndex="ake003" codeType="AKE003"
			editor="select" width="80" />
		<odin:gridColumn header="ҽ��ͨ����" dataIndex="ake002" width="80" />
		<odin:gridColumn header="Ӣ����" dataIndex="aka062" width="100" />
		<odin:gridColumn header="ƴ������" dataIndex="aka020" width="60" />
		<%-- 		<odin:gridColumn header="��ʼ���" dataIndex="aka021" width="60" />
		<odin:gridColumn header="ҽ������" dataIndex="aka070" width="60" />
		<odin:gridColumn header="�շѵȼ�" dataIndex="aka065" codeType="AKA065"
			editor="select" width="60" />
		<odin:gridColumn header="ҽ�Ʒ�Ʊ���" dataIndex="aka063" codeType="AKA063"
			editor="select" width="80" />
		<odin:gridColumn header="���Ʒ�Χ" dataIndex="aka036" width="120"
			codeType="YESNO" editor="select" />
		<odin:gridColumn header="�б���" dataIndex="aka094" width="60" />
		<odin:gridColumn header="����" dataIndex="bka009" width="60" />
		<odin:gridColumn header="�б�ͨ����" dataIndex="aka095" width="100" />
		<odin:gridColumn header="�б���Ʒ��" dataIndex="aka061" width="80" />
		<odin:gridColumn header="�б����" dataIndex="aka096" width="80" /> --%>
		<%-- 		<odin:gridColumn header="���" dataIndex="aka074" width="80" />
		<odin:gridColumn header="ת����" dataIndex="aka097" width="60" />
		<odin:gridColumn header="��λ" dataIndex="aka052" width="60" />
		<odin:gridColumn header="��װ����" dataIndex="aka098" width="80" />
		<odin:gridColumn header="��������" dataIndex="aka099" edited="false"
			editor="number" width="80" />
		<odin:gridColumn header="�б궨��" dataIndex="aka100" edited="false"
			editor="number" width="100" />
		<odin:gridColumn header="ҽԺ���۶���" dataIndex="aka101" edited="false"
			editor="number" width="100" /> --%>
		<odin:gridColumn header="�������" dataIndex="aka069" edited="false"
			isLast="true" editor="number" width="60" />
	</odin:gridColumnModel>
</odin:grid>

<odin:keyMap property="pageKey">
	<odin:keyMapItem key="Q" isLast="true" fn="queryStart"></odin:keyMapItem>
</odin:keyMap>
<odin:window src="/sys/excel/simpleExpExcelWindow.jsp" title="��������"
	id="simpleExpWin" height="160"></odin:window>
<script>
    var uuid="<%=java.util.UUID.randomUUID().toString()%>";
	var fileNameSim = "ҩƷĿ¼"+uuid;
	var sheetNameSim = "ҩƷĿ¼";
	var querySQLSim = "";
	
	function expPersonInfo(){
		
		var aaa = document.all.aaa.value;
		
		if(aaa == "0"){
			odin.alert("���Ȳ�ѯ���ٵ������ݣ�");
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
	
		querySQLSim = "select a.ake001 ҩƷ����,a.ake002 ҩƷ����,a.aka020 ƴ������,a.aka062 Ӣ����,a.aae030 ��ʼʱ��,a.aae031 ����ʱ��,a.aka065  �շ���Ŀ�ȼ�,'' ҽԺ�ȼ�,'' ʹ�÷�Χ,0 �����޶�,0 סԺ�޶�,0 ��������,0 סԺ����,a.ake004 ��������,a.aka070 ҩƷ����,a.aka074 ҩƷ���,a.aka052 ��λ,a.eke001 ҩƷ����,a.bka009 ����,a.bka008 ����ʹ������,a.aka063 �շ����,a.aka094 �б���,a.aka095 �б�ͨ����,a.aka061 �б���Ʒ��,a.aka097 ת����,a.aka098 ��װ����,a.aka099 ��������,a.aka100 �б궨��,a.aka101 ҽԺ���۶���,a.aka069 �ο�ҽ���������" 
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
			alert("����ѯ��ʱ��"+(endTime-loadtime)+'ms');
			loadtime=0;
		}
	});
	g1.getBottomToolbar().addListener('beforechange',function(){
		loadtime = new Date().getTime();
	});
});
function showPageTime(){
	alert("ҳ����غ�ʱ��"+document.getElementById("loadedDiv").innerHTML+"\n\
		ҳ����Ⱦ��ʱ��" + document.getElementById("finishedDiv").innerHTML);
}	
</script>
