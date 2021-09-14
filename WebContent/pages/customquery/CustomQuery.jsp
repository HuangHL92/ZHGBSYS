<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pages/css/warningPerson.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>

<%@page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.ShowControl"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>

<%
String pageSize = "fy";
if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
	 pageSize = session.getAttribute("pageSize").toString(); 				 //判断是否设置了自定义每页数量，如果设置了使用自定义
}
ShowControl showControl = new ShowControl();
String pps_isuseful = showControl.getPpsResult();

    String isTemplateDB = request.getParameter("IsTemplateDB");
    HBSession sess = HBUtil.getHBSession();
    List<Object[]> li = (List<Object[]>) sess.createSQLQuery("select aaa001,aaa005 from aa01 where aaa001 = 'QDTJ'").list();
    Object[] o1 = li.get(0);
    String url1 = o1[1].toString();
    String isDisplay = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001='ISDISPLAY'").uniqueResult();
    List<Object[]> dataList = sess.createSQLQuery("select code_value,code_name from code_value where code_type='BZS01' and code_status='1'  order by code_value").list();
    String datalist = "", a = "", b = "";
    for (int i = 0; i < dataList.size(); i++) {
        Object[] obj = dataList.get(i);
        if (i == 0) {
            a = "['" + obj[0].toString();
        } else {
            a = ",['" + obj[0].toString();
        }
        b = a + "','" + obj[1].toString() + "']";
        datalist += b;
    }
%>


<style>
.x-panel-body {
	height: 100%
}

.x-panel-bwrap {
	height: 100%
}

.x-form-item2 tr td .x-form-item {
	margin-bottom: 0px !important;
}

.style1 table {
	color: #000000;
}

.style1 {
	color: #000000;
	cursor: pointer;
}

.style2 table {
	background-color: #D1F7B7;
	color: #000000;
}

.style2 {
	color: #000000;
	cursor: pointer;
}
#tableTab1{
	overflow-y:auto;
	height:470px;
}
</style>

<script type="text/javascript">


function changeRowClass(record, rowIndex, rowParams, store) {
	if("已审"==store.getAt(rowIndex).get("a0189") && "已审"==store.getAt(rowIndex).get("a0190")){
		return 'style2';
	} else {
		return 'style1';
	}
}

function view(){
    <%-- $h.openPageModeWin('WebOffice','pages.weboffice.ViewOffice','历史纪录',870,435,'','<%=request.getContextPath()%>');   --%>
    var iWidth = 200;
    var iHeight = 400;
    var top = (window.screen.height - 30 - iHeight) / 2;       //获得窗口的垂直位置;
    var left = (window.screen.width - 10 - iWidth) / 2;   //获得窗口的水平位置;
<%--     window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice', '导出', 'height=' + iHeight + ',width=' + iWidth + ',left='+left+',top='+top+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes'); --%>
		 window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice',window,'dialogWidth:280px; dialogHeight:400px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
}

//自定义导出
function OnPropChanged (event) {
	var a=document.getElementById("test").value ;
	var arr=a.split(',');
	var type=arr[0];
	var name=arr[1];
	var bs = arr[3];
	var rad = arr[4];
	var namebs = name+','+bs+','+rad;
    if (event.propertyName.toLowerCase () == "value") {
    	var sql=document.getElementById("sql").value;
    	//var qvid=document.getElementById("qvid").value;
    	if(sql==""){
    		$h.alert('系统提示：','请双击机构树查询！',null,180);
    		return;
    	}
    	var gridId = "peopleInfoGrid";
    	var grid = Ext.getCmp(gridId);
    	var store = grid.getStore();
    	var length = store.getCount();
    	if(length==0){
    		$h.alert('系统提示：','请先选择记录！',null,150);
    		return;
    	}
    	 if(type==3){//自定义word导出
    		radow.doEvent("chuidrusesson");
    		if(name=="D99D8CB4B5FC4276BB9A93376CA96A15"){
    			radow.doEvent("expword","12");
    		}else if(name=="E3673674657D47B499099FB97F0EF64A"){
    			radow.doEvent("expword","13");
    		}else if(name=="52C1F7A298A742E3B9744CE4B7CD1A68"){
    			radow.doEvent("expword","14");
    		}else{
	        	radow.doEvent("expwordw",namebs);
    		}
    	}else if (type==2){//基本名册导出
    	 	radow.doEvent("chuidrusesson");
     		radow.doEvent("expworde",namebs);
    	}else if(type==1){//单位名册导出
    		radow.doEvent("chuidrusesson");
     		radow.doEvent("expworddw",namebs);
    }else{
    	radow.doEvent("chuidrusesson");
 		radow.doEvent("expExcel",namebs);
    }
    }

}
 //自定义打印
function OnPropChanged1 (event) {
	var a=document.getElementById("test1").value ;
	var arr=a.split(',');
	var type=arr[0];
	var name=arr[1];
	var bs = arr[3];
	var rad = arr[4];
	var namebs = name+','+bs+','+rad;
    if (event.propertyName.toLowerCase () == "value") {
    	var sql=document.getElementById("sql").value;
    	//var qvid=document.getElementById("qvid").value;
    	if(sql==""){
    		$h.alert('系统提示：','请双击机构树查询！',null,180);
    		return;
        }
        var gridId = "peopleInfoGrid";
        var grid = Ext.getCmp(gridId);
        var store = grid.getStore();
        var length = store.getCount();
        if (length == 0) {
            $h.alert('系统提示：', '请先选择记录！', null, 150);
            return;
        }

        var wdapp = null;
        try {
            wdapp = new ActiveXObject("KWPS.Application");
        } catch (e) {
            try {
                wdapp = new ActiveXObject("Word.Application");
            } catch (e) {
                $helper.alertActiveX();
                $h.alert('系统提示：', '请重新安装WPS2016', null, 180);
                return;
            }
        }

        if (type == 3) {//自定义word导出
            // Ext.MessageBox.wait('打印中，请稍后。。。');
            radow.doEvent("chuidrusesson");
            /* radow.doEvent("printRmb","1");  */
            //ajaxSubmit("expwordw",namebs);
            radow.doEvent("expwordw", namebs);
            /* radow.doEvent("chuidrusesson");
            radow.doEvent("expwordw",name); */
        } else if (type == 2) {//基本名册导出
    	 	radow.doEvent("chuidrusesson");
     		radow.doEvent("expworde",namebs);
    	}else if(type==1){//单位名册导出
    		radow.doEvent("chuidrusesson");
     		radow.doEvent("expworddw",namebs);
    }else{
    	radow.doEvent("chuidrusesson");
 		radow.doEvent("expExcel",namebs);
    }
    }

}

/* 监控回车事件,绑定机构定位  */ //readyonly 可删除
document.onkeydown=function() {
	if (event.keyCode == 8) {
		if ((document.activeElement.type == "text"||document.activeElement.type == "textarea")&&document.activeElement.readOnly == true) {

			var id = document.activeElement.id;
			var index = id.indexOf('_combo');
			var index2 = id.indexOf('comboxArea_');
			if(index!=-1){
				var realid =  id.substring(0,index);
				document.getElementById(realid).value='';
				document.getElementById(id).value='';
				onkeydownfn(realid);
				return false;
			}else if(index2!=-1){
				var realid =  id.substring(11,id.length);
				document.getElementById(realid).value='';
				document.getElementById(id).value='';
				onkeydownfn(realid);
				return false;
			}
			return false;
		}
		if(document.activeElement.type != "password" && document.activeElement.type != "text"
			&& document.activeElement.type != "textarea"){
			return false;
		}

	}else if(event.keyCode == 13){
		doQueryNext();//具体处理函数
        return false;
	}else if(event.keyCode == 27){	//禁用ESC
        return false;
	}
};




function getPersonIdForDj(){
	var personId = document.getElementById('a0000').value;
	return personId;
}

function getPersonNameForDj(){
	var personName = document.getElementById('a0101').value;
	return personName;
}

function selectAllPeople(){
	var tableType = document.getElementById("tableType").value;
	//列表
	if(tableType == '1'){
		var gridId = "peopleInfoGrid";
		var fieldName = "personcheck";
		var checkAll = document.getElementById('checkAll');
		var value = checkAll.checked;
		var store = odin.ext.getCmp(gridId).store;
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
		}
	}
	//小资料、小资料
	if(tableType == '2'){
		var checkAll = document.getElementById('checkAll2');
		var value = checkAll.checked;
		for(var i=0;i<6;i++){
			document.getElementById("datac"+i).checked = value;
		}
	}
	//照片
	if(tableType == '3'){
		var checkAll = document.getElementById('checkAll3');
		var value = checkAll.checked;
		for(var i=0;i<10;i++){
			document.getElementById("c"+i).checked = value;
		}
	}
}
function orderQuery(){
	var orderquery = document.getElementById('orderquery');
	if(orderquery.checked){
		document.getElementById('orderqueryhidden').value = 1;
	}else{
		document.getElementById('orderqueryhidden').value = 0;
	}
}
function deleteRow(){
	var sm = Ext.getCmp("persongrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) {
		if("yes"==id){
			radow.doEvent('deleteconfirm',sm.lastActive+'');
		}else{

		}
	});
}
function impLrm(){
	odin.showWindowWithSrc("importLrmWin",contextPath+"/pages/publicServantManage/ImportLrm.jsp?businessClass=com.picCut.servlet.SaveLrmFile");
}
/* function impLrm(){
	$h.openWin('importLrmWin','pages.publicServantManage.ImportLrm','选择模板',500,200,'传参',ctxPath);
} */

function impLrmx(){
	odin.showWindowWithSrc("importLrmxWin",contextPath+"/pages/publicServantManage/ImportLrmx.jsp?businessClass=com.picCut.servlet.SaveLrmFile");
}
function impLrms(){
	odin.showWindowWithSrc("importLrmWins",contextPath+"/pages/publicServantManage/ImportLrms.jsp?businessClass=com.picCut.servlet.SaveLrmFile");
}
function impPhotos(){
	$h.openWin('importimpPhotosWins','pages.publicServantManage.ImportPhotos','批量替換照片',500,200,'传参',contextPath);
	//odin.showWindowWithSrc("importimpPhotosWins",contextPath+"/pages/publicServantManage/ImportPhotos.jsp?");
}
function printSet(){
	//得到默认打印机
	var oShell = new ActiveXObject("WScript.Shell");
    var sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device';
    var sDefault = oShell.RegRead(sRegVal);
    var printer =sDefault.split(",")[0];
	/* radow.doEvent("printSetInit",printer); */

	//得到所有的打印机传到后端
    var nt = new ActiveXObject("WScript.Network"); //获取插件
    var oPrinters = nt.EnumPrinterConnections(); //需要编写 ActiveX 控件来获得d打印机列表
    if (oPrinters == null || oPrinters.length == 0)
    {
        alert('当前你的机器暂没安装打印机');
        return;
    } else
    {
        //alert('当前打印机台数:'+oPrinters.length);
    }

	var printers = "";
    for (i = 0; i < oPrinters.length; i += 2)
    {

        var name = oPrinters.Item(i + 1);
        if(i==0){
        	printers=name;
        }else{
        	printers=printers+"|@|"+name;
        }
    }
    var param = printer+"&%"+printers;
	$h.openWin('printSetWin','pages.publicServantManage.PrintSet','打印机设置',500,180,param,ctxPath);
}





/* function DownURL(strRemoteURL, strLocalURL){
    try{
        var xmlHTTP = new ActiveXObject("Microsoft.XMLHTTP");
        xmlHTTP.open("Get", strRemoteURL, false);
        xmlHTTP.send();
        var adodbStream = new ActiveXObject("ADODB.Stream");
        adodbStream.Type = 1;//1=adTypeBinary
        adodbStream.Open();
        adodbStream.write(xmlHTTP.responseBody);
        adodbStream.SaveToFile(strLocalURL, 2);
        adodbStream.Close();
        adodbStream = null;
        xmlHTTP = null;
    }
    catch (e){
        window.confirm("下载URL出错!");
    }
    //window.confirm("下载完成.");
} */

function SaveAs5(imgURL)
{
var oPop = window.open(imgURL,"","width=1, height=1, top=5000, left=5000");
for(; oPop.document.readyState != "complete"; )
{
if (oPop.document.readyState == "complete")break;
}
oPop.document.execCommand("Save");
oPop.close();
}




//打印
function print(){
	//("http://127.0.0.1:8080/hzb/ziploud/57bee0d0ec4940119742e007e5015113/expFiles_20180514110455/1_江  海.doc");

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
        return;
    }
    var gridId = "peopleInfoGrid";
    var grid = Ext.getCmp(gridId);
    var store = grid.getStore();
    var length = store.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '请先选择记录！', null, 150);
        return;
    }
    var wdapp = null;
    try {
        wdapp = new ActiveXObject("KWPS.Application");
    } catch (e) {
        try {
            wdapp = new ActiveXObject("Word.Application");
        } catch (e) {
            $helper.alertActiveX();
            $h.alert('系统提示：', '请重新安装WPS2016', null, 180);
            return;
        }
    }
    Ext.MessageBox.wait('正在打印中，请稍后。。。');
    radow.doEvent("chuidrusesson");
    /* radow.doEvent("printRmb","1");   */
    ajaxSubmit("printRmb", "1");

}

function ajaxSubmit(radowEvent, parm) {
    if (parm) {
    } else {
		parm = {};
	}
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&eventNames="+radowEvent,
		success: function(resData){
			//alert(resData.responseText);
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();

				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}

				//console.log(cfg.elementsScript);
				eval(cfg.elementsScript);
				//var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
				//parent.document.location.reload();
				//alert(cfg.elementsScript);
				//realParent.resetCM(cfg.elementsScript);
				//parent.Ext.getCmp("setFields").close();
				//console.log(cfg.mainMessage);
				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}else{
				//Ext.Msg.hide();

				if(cfg.mainMessage.indexOf("<br/>")>0){

					$h.alert('系统提示',cfg.mainMessage,null,380);
					return;
				}

				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}

		},
		failure : function(res, options){
			Ext.Msg.hide();
            alert("网络异常！");
        }
    });
}


function printStart(path) {
    var url = window.location.protocol + "//" + window.location.host + "/hzb/";
    path = url + path;
    //Ext.MessageBox.hide();
    var wdapp = null;
    try {
        wdapp = new ActiveXObject("KWPS.Application");
        wdapp.Documents.Open(path);//打开word模板url
        wdapp.Application.Printout();
    } catch (e) {
        try {
            wdapp = new ActiveXObject("Word.Application");
            wdapp.Documents.Open(path);//打开word模板url
            wdapp.Application.Printout();
        } catch (e) {
            $helper.alertActiveX();
            $h.alert('系统提示：', '请重新安装WPS2016', null, 180);

        }
    }

}

/* function impLrms(){
	//odin.showWindowWithSrc("importLrmWins",contextPath+"/pages/publicServantManage/ImportLrms.jsp?businessClass=com.picCut.servlet.SaveLrmFile");
	$h.openWin('importLrmWins','pages.publicServantManage.ImportLrms','选择模板',500,200,'传参',ctxPath);
} */


function closeWin() {
	odin.ext.getCmp('importLrmWin').hide();
}

function exportAll(){
alert("2");
	odin.grid.menu.expExcelFromGrid('persongrid', null, null,null, false);
}

function getPersonId(){
	var personId = document.getElementById('checkList').value;
	return personId;
}

function downLoadTmp(){

	var dwbm=document.getElementById('a0201b').value;
	var list=document.getElementById("checkList").value;
	if(typeof(list)!='undefined'&&list!='')
		 doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dwbm="+dwbm+"&checkList=1"+"&tmpType=['5', '标准名册'],['6', '照片名册(每行4人)'],['8','照片名册(每行1人)']")),
		 			"下载文件", 500, 210);
	else
		Ext.Msg.alert("系统提示","没有选择任何人员不能导出！");

}

function expExcelFromGrid(){
	var sql=document.getElementById("sql").value;
    if (sql == "") {
        $h.alert('系统提示：', '请双击机构树查询！', null, 180);
        return;
    }

/*     var excelName = null; */
    //excel导出名称的拼接
    /* var pgrid = Ext.getCmp('peopleInfoGrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    } */
    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('peopleInfoGrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
/*     odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false); */
    	if(num != 0){
            //获得列表第一个人
            excelName = dstore.getAt(0).get('a0101');

            if(num > 1){
                excelName = excelName + "等" + num +"人";
            }
        }

        excelName = "人员信息" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd"); 

	odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}


function expExcelFromGrid2(){
	var sql=document.getElementById("sql").value;
    if (sql == "") {
        $h.alert('系统提示：', '请双击机构树查询！', null, 180);
        return;
    }

    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('peopleInfoGrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
    	if(num != 0){
            //获得列表第一个人
            excelName = dstore.getAt(0).get('a0101');

            if(num > 1){
                excelName = excelName + "等" + num +"人";
            }
        }

        excelName = "人员信息" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd"); 

	radow.doEvent("expExcelFromGrid2",excelName);
}

/**
 * 导出登记表册
 * @param {} gridId
 * @param {} fileName
 * @param {} sheetName
 * @param {} headNames
 * @param {} isFromInterface
 */
function createExcelTemp() {
	var a0000 = '';
	var a0101 = '';
	var gridId = "peopleInfoGrid";
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("没有要导出的数据！");
		return;
	}

	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for (var i = 0; i < store.data.length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		if (record.personcheck) {
			a0000 = a0000 + record.a0000 + ',';
			a0101 = a0101 + record.a0101 + ',';
		}
	}
	if (a0000 == '') {
		odin.error("请选中要导出的行！");
		return;
	}

	a0101 = a0101.substring(0, a0101.length - 1);
	a0000 = a0000.substring(0, a0000.length - 1);

	radow.doEvent('checkPer',a0000+"@"+a0101);

}
function createExcelTemp1() {
	radow.doEvent('checkPer1');

}

 /**
  * 导出登记表册
  * @param {} gridId
  * @param {} fileName
  * @param {} sheetName
  * @param {} headNames
  * @param {} isFromInterface
  */
 function expExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
				j++;
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}
		if(j>3){
			for(var i = 0; i < 3; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1)+'等';
		}else{
			for(var i = 0; i < j; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1);
		}
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = a0101;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?download=true&filename="+filename)), "下载文件", 600, 200);
	}

 function ml(a0000,allName){
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = allName;
		doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpTempDjbWindow.jsp")), "下载文件", 600, 520);
		//$h.showWindowWithSrc('exportexcelWin',contextPath + "/pages/exportexcel/ExpTempDjbWindow.jsp",'下载文件',600,400);
	}
//自定义表格展示
function othertem(){
	var a0000 = '';
	var a0101 = '';
	var filename = '';
	var j = 0;
	var person;
	var tableType = document.getElementById('tableType').value;
	//列表
	if(tableType == '1'){
		var objAll = document.getElementById('checkAll').checked;
		if(objAll){
			person = "allperson";
///			radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			$h.openWin('alertWin','pages.customquery.AlertWin&initParams='+person,'自定义模板窗口',800,500,'r', ctxPath);
		}
		else{
			var gridId = "peopleInfoGrid";
			if (!Ext.getCmp(gridId)) {
				odin.error("要展示的grid不存在！gridId=" + gridId);
				return;
			}
			var grid = Ext.getCmp(gridId);
			var store = grid.getStore();
			if (store.getCount() == 0) {
				odin.error("没有要展示的数据！");
				return;
			}
			person = document.getElementById('checkList').value;
			if(person == ''){
				odin.error("请选择要展示的人员！");
				return;
			}
///			radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			$h.openWin('alertWin','pages.customquery.AlertWin&initParams='+person,'自定义模板窗口',800,500,'r', ctxPath);
		}
	}
	//小资料、照片
	if(tableType == '2' || tableType == '3'){
		if(tableType == '2'){
			var objAll = document.getElementById('checkAll2').checked;
			if(objAll){
				document.getElementById('picA0000s').value = "";
				person = "allperson";
				radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			}
			else{
				var person = document.getElementById('picA0000s').value;
				if(!person){
					odin.error("请选择要展示的人员！");
					return;
				}
				person = person.replace(new RegExp("'","gm"),"|");
				person = person.replace(new RegExp(",","gm"),"@");
				person = person.substring(0,(person.length-1));
				radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			}
		}
		if(tableType == '3'){
			var objAll = document.getElementById('checkAll3').checked;
			if(objAll){
				person = "allperson";
				radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			}
			else{
				var person = document.getElementById('picA0000s').value;
				if(!person){
					odin.error("请选择要展示的人员！");
					return;
				}
				person = person.replace(new RegExp("'","gm"),"|");
				person = person.replace(new RegExp(",","gm"),"@");
				person = person.substring(0,(person.length-1));
				radow.util.openWindow('alertWin','pages.customquery.AlertWin&initParams='+person);
			}
		}


	}
	/* var url = contextPath + "/pages/publicServantManage/ZDYtem.jsp";
	doOpenPupWin(url, "选择模板", 300, 200); */


}

//花名册展示
function choses(){
	var a0000 = '';
	var a0101 = '';
	var filename = '';
	var j = 0;
	var gridId = "peopleInfoGrid";
	if (!Ext.getCmp(gridId)) {
		odin.error("要展示的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("没有要展示的数据！");
		return;
	}
	var person = document.getElementById('checkList').value;
	if(person == ''){
		odin.alert("请先选择要展示的人员！");
		return;
	}
	//判断是否点击了列表中的全部人员
	var objAll = document.getElementById('checkAll').checked;
	if(objAll){
//		odin.error("请选择要展示的人员！");
		if(confirm('您确定要展示当前机构及下级机构的所有人员吗？')){
		var url = contextPath + "/pages/publicServantManage/chooseZDYtem.jsp";
		doOpenPupWin(url, "选择模板", 400, 180);
//				radow.doEvent('deltp',zdyid);
			}
		return;
	}
	//radow.doEvent('choosedata');
	var url = contextPath + "/pages/publicServantManage/chooseZDYtem.jsp";
	doOpenPupWin(url, "选择模板", 400, 180);

}

//右键：状态变更
function changeStateRigth(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("changeStateRigth",a0000);
	}
}


function incr(){
	radow.doEvent("loadadd.onclick");
}

function modify(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("modify",a0000);
	}
}

function deletePeople(){
	var a0000 = getA0000();

	var a0101 = getA0101();
	document.getElementById('deleteTip').value= a0101;


	if(a0000){
		radow.doEvent("deletePeople","'"+a0000+"',");
	}
	var a0101 = getA0101();

	document.getElementById('deleteA0000S').value= "'"+a0000+"',";
}

function printTrue(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("printTrue",a0000);
	}
}

function printFalse(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("printFalse",a0000);
	}
}

function printFalse1_1(){
	var a0000 = getA0000();
	if(a0000){
		document.getElementById("printPdf").value = "pdf1.1";
		radow.doEvent("printFalse",a0000);
	}
}

function printPdfView(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("printPdfView",a0000);
	}
}

//右击：导出任免表Word
function exportGBDocRigth(){

	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("exportGBDocRigth",a0000);
	}

}


function expLrmx(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("expLrmx",a0000);
	}
}

function expLrm(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("expLrm",a0000);
	}
}

//右击：导出任免表PDF
function exportPdf(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("exportPdf",a0000);
	}
}

//右击：导出任免表HZB
function importHzb(){
	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("importHzb",a0000);
	}
}

//右击：表册输出
function out(){

	var a0000 = getA0000();

	if(a0000){
		radow.doEvent("out",a0000);
		$h.openWin('addOrgWin6','pages.publicServantManage.Appointmentto','选择模板',500,540,'传参',ctxPath);
	}

}

function outNew(){

	var a0000 = getA0000();
	if(a0000){
		radow.doEvent("out",a0000);
		$h.openWin('addOrgWin7','pages.publicServantManage.Appointmentto_new','选择模板',500,540,'传参',ctxPath);
	}

}

function transfer(){
	var gridId = "peopleInfoGrid";
	var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	var a0000 = selections[0].data.a0000;
	var a0101 = selections[0].data.a0101;
	var param = a0000 + "," + a0101;

	//radow.util.openWindow('peopleTrans','pages.customquery.PeopleTransfer&initParams='+param);

	$h.openWin('peopleTrans','pages.customquery.PeopleTransfer','人员调转',453,320,param,ctxPath)
}

//人员列表单击事件
function rowClickPeople(a,index){
	//getCheckList3(index);
}

function getA0000(){
	var gridId = "peopleInfoGrid";
	var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	var a0000 = selections[0].data.a0000;
	return a0000;
}

function getA0101(){
	var gridId = "peopleInfoGrid";
	var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	var a0101 = selections[0].data.a0101;
	return a0101;
}

function getPdfPath(){
	var pdfPath = document.getElementById('pdfPath').value;
	return pdfPath;
}
///任免表展示方法
function printAppointment(){
    $h.openWin('addOrgWin6', 'pages.publicServantManage.Appointmentto', '任免表展示', 800, 900, getA0000(), ctxPath);
}

//xin显示表格
function zhlistshow() {
    //如果此时已经打开显示子集，则将其隐藏
    var sql = document.getElementById("sql").value;
    if (sql == "") {
        //alert("请先双击机构树进行查询")
        //Ext.Msg.alert("系统提示","请先双击机构树进行查询！");
        //return;
    }
    //判断列表是否有值
    var grid = Ext.getCmp('peopleInfoGrid');
    var store = grid.getStore();
    var length = store.getCount();
    if (length == 0) {
        //列表为空，统计全部
        sql = '0';
    } else {
        //列表不为空
        sql = '1';
	}
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_zh',"综合统计"+'',1200,550,sql,'<%=request.getContextPath()%>');
}

//年龄统计
function Nllistshow() {
    //如果此时已经打开显示子集，则将其隐藏
    var sql = document.getElementById("sql").value;
    if (sql == "") {
        //alert("请先双击机构树进行查询")
        //Ext.Msg.alert("系统提示","请先双击机构树进行查询！");
        //return;
    }
    //判断列表是否有值
    var grid = Ext.getCmp('peopleInfoGrid');
    var store = grid.getStore();
    var length = store.getCount();
    if (length == 0) {
        //列表为空，统计全部
        sql = "0";
    } else {
        //列表不为空
        sql = "1";
	}
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_age',"年龄统计"+'',1200,550,sql,'<%=request.getContextPath()%>');
}
//表册输出打开窗口
function openchose(){

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	$h.openWin('addOrgWin6','pages.publicServantManage.Appointmentto','选择模板',500,540,'传参',ctxPath);
}

function openchose_new(){

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	$h.openWin('addOrgWin7','pages.publicServantManage.Appointmentto_new','选择模板',500,540,'传参',ctxPath);
}


function exportGBDoc(){
	//document.getElementById("DCellWeb1").ExportExcelDlg();
  	var docA0000s = document.getElementById('docA0000s').value;
 	var docA0101s = document.getElementById('docA0101s').value;
 	//var viewType = document.getElementById('viewType').value;
 	var firstName = "";
 	var tempType = "47";
	if(docA0101s.indexOf(',') >= 0){
		firstName = docA0101s.split(',')[0];
		fileName = firstName+'等人_干部审批任免表.doc';
	}else{
		fileName = docA0101s+'_干部审批任免表.doc';
	}
    var form = document.createElement("form");
    form.method = "post";
    form.style.display = "none";
    form.action = "<%=request.getContextPath()%>/FiledownServlet?downLoad=true";

   	var hideInput = document.createElement("input");
   	hideInput.type="hidden";
   	hideInput.name= "excelType";
   	hideInput.value= tempType;
   	form.appendChild(hideInput);

   	var hideInput2 = document.createElement("input");
  	hideInput2.type="hidden";
  	hideInput2.name= "a0000";
  	hideInput2.value= docA0000s;
  	form.appendChild(hideInput2);

   	var hideInput3 = document.createElement("input");
   	hideInput3.type="hidden";
   	hideInput3.name= "fileName";
   	hideInput3.value= fileName;
   	form.appendChild(hideInput3);

   	var hideInput4 = document.createElement("input");
  	hideInput4.type="hidden";
  	hideInput4.name= "a0101";
  	hideInput4.value= docA0101s;
  	form.appendChild(hideInput4);

  	document.body.appendChild(form);
    form.submit();
}


function expWord1(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","1");
}

function expWord1_2(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","1_2");
}

function expWord1_1(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","1_1");
}



function expWord1_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",1);
	}
}
function expWord13_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",13);
	}
}
function expWord2_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",2);
	}
}
function expWord3_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",3);
	}
}
function expWord4_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",4);
	}
}
function expWord5_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",5);
	}
}
function expWord6_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",6);
	}
}
function expWord7_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",7);
	}
}
function expWord8_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",8);
	}
}
function expWord9_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",9);
	}
}
function expWord10_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",10);
	}
}
function expWord11_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",11);
	}
}
function expWord12_right(){
	var a0000 = getA0000();
	document.getElementById("expword_personid").value = a0000;
	if(a0000){
		radow.doEvent("expword1",12);
	}
}
function expWord2(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","2");
}

function expWord3(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","3");
}

function expWord4(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","4");
}

function expWord5(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","5");
}

function expWord6(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","6");
}

function expWord7(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","7");
}

function expWord8(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","8");
}

function expWord16(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","16");
}

function expWord9(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","9");
}

function expWord15(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","15");
}

function expWord14(){
var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var expType = "";
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.customQuery&eventNames=ExpTPBExcel';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出干部人员信息一览表 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 6000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				 //alert(cfg.messageCode)
 					if(0==cfg.messageCode){
 						if("操作成功！"!=cfg.mainMessage){
 							Ext.Msg.alert('系统提示:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}

 	 						//console.log(cfg.elementsScript);
 	 						//alert("文件已经生成！");
 	 						eval(cfg.elementsScript);
 						}else{


 						}

 					}else{
 						Ext.Msg.alert('系统提示:',cfg.mainMessage);

 					}
 				}
      	   }
        }
   });
}
function expWord10(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","10");
}

function expWord11(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","11");
}
function expWord12(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","12");
}
function openGxgaFile(){
	var grid = odin.ext.getCmp('peopleInfoGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var a0000s = "";

	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var flag=0;
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.personcheck == true){
			a0000s=a0000s+rowData.data.a0000+",";
			flag++;
		}
	} 
	if(a0000s == null || a0000s == ""||flag>1){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	$h.openPageModeWin('gxgafile','pages.gbwh.GXGAXXLR','关心关爱',1100,600,{a0000:a0000},g_contextpath);
	<%-- var a0000 = document.getElementById("a0000").value;
	var g_contextpath = '<%=request.getContextPath()%>';
	$h.openPageModeWin('gxgafile','pages.gbwh.GXGAXXLR','关心关爱',1100,600,{a0000:a0000},g_contextpath); --%>

 	<%-- var contextPath = '<%=request.getContextPath()%>';
 	$h.showWindowWithSrc('GXGAXXLR',contextPath + "/pages/gbwh/GXGAXXLR.jsp?a=1",'关心关爱',1200,600,null,{maximizable:true,resizable:true}); --%>
	
}

function openKHPJFile(){
	var grid = odin.ext.getCmp('peopleInfoGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var a0000s = "";

	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var flag=0;
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.personcheck == true){
			a0000s=a0000s+rowData.data.a0000+",";
			flag++;
		}
	} 
	if(a0000s == null || a0000s == ""||flag>1){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	$h.openWin('khpjWin','pages.gbwh.KHPJ','考核评价',1100,600,a0000,g_contextpath,null,{maximizable:false,resizable:false});
/* 	$h.openPageModeWin('khpjfile','pages.gbwh.KHPJ','考核评价',1100,600,{a0000:a0000},g_contextpath); */

 	<%-- var contextPath = '<%=request.getContextPath()%>';
 	$h.showWindowWithSrc('GXGAXXLR',contextPath + "/pages/gbwh/GXGAXXLR.jsp?a=1",'关心关爱',1200,600,null,{maximizable:true,resizable:true}); --%>
	
}
/* 导出任免表时 增加进度条 */
function expLrmGrid(){
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('exportLrmBtn');
}

function expLrmxGrid(){
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('exportLrmxBtn');
}

function hzbBtn(){
	radow.doEvent("importHzbBtn","hzb");
}

function expZip(){
	var excelfile=document.getElementById('expPadZipFile').value;
	$('#excelfile').val(encodeURI(excelfile));
	var excel=$("#excelfile").val();
	var index1=excel.lastIndexOf(".");
	var index2=excel.length;
	var suffix=excel.substring(index1+1,index2);
	if(suffix!=""){
		if(suffix!=("xls")){
			alert("Excel文件必须为2003版的*.xls文件！");
		}else if(suffix==("xls")){
			var padConditions = "";
			var cs = 0;
			$("input[name='padCons']").each(function(){
				var ischeck=$(this).is(":checked");
				var v=$(this).val();
				if(ischeck==true){
                    var ischeck = $(this).is(":checked");
                    var cs = $(this).val();
                    if (ischeck == false) {
                        //data.push("□");
                    } else {
                        //data.push("√");
                        padConditions = padConditions + v + ",";
                        cs++;
                    }
                }
            });

            //var padZipCons;
            $('#padZipCons').val(padConditions);
            radow.doEvent("importHzbBtn", "zip");
        }
    } else {
        var padConditions = "";
        var cs = 0;
        $("input[name='padCons']").each(function () {
			var ischeck=$(this).is(":checked");
			var v=$(this).val();
			if(ischeck==true){
                var ischeck = $(this).is(":checked");
                var cs = $(this).val();
                if (ischeck == false) {
                    //data.push("□");
                } else {
                    //data.push("√");
                    padConditions = padConditions + v + ",";
                    cs++;
                }
            }
        });

        //var padZipCons;
        $('#padZipCons').val(padConditions);
        radow.doEvent("importHzbBtn", "zip");
    }
    var updateDate = new Date();
    var updateTime = updateDate.toLocaleDateString();
    radow.doEvent("sentUpdateTime", updateTime)
}



function zipBtn1(){
	showExtPadData();
	<%-- $h.openPageModeWin('appZip','pages.customquery.Query4Comm','平板数据导出 ',800,1100,'1','<%=request.getContextPath()%>'); --%>
	//radow.doEvent("importHzbBtn","zip");
}

function exportZipBtn(){
	showExtZipData();
}

function expGSZipBtn(){
	/*var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expGSZipBtn");*/
	$h.openWin('PUBLICITY','pages.customquery.Publicity','公示导出',900,600,'',ctxPath);
}
function expSZSJZipBtn(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('PUBLICSZSJCITY','pages.customquery.PublicSZSJcity','省组导出',900,600,'',ctxPath);
}


function expBtnword(){
	radow.doEvent("expBtnwordonclick");
}

function zipBtn(){
	radow.doEvent("importHzbBtn","7z");
}
function zbyfunc(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_ssygwy',"公务员基本情况表（总表一）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function zbefunc(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_ssegwy',"公务员基本情况表（总表二）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function qtfunc(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_swqt',"参公群团总表"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function czfunc(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gbjbqk','pages.customquery.formanalysis.formanalysis_sycz',"参公事业总表"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenGwynlqk1(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gwynlqk1','pages.customquery.formanalysis.Gwynlqk1',"公务员年龄情况表（总表一）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenGwynlqk2(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gwynlqk2','pages.customquery.formanalysis.Gwynlqk2',"公务员年龄情况表（总表二）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenGwynlqk3(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gwynlqk3','pages.customquery.formanalysis.Gwynlqk3',"公务员年龄情况表（总表三）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenGwynlqk4(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Gwynlqk4','pages.customquery.formanalysis.Gwynlqk4',"公务员年龄情况表（总表四）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenCgqtnlqk1(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Cgqtnlqk1','pages.customquery.formanalysis.Cgqtnlqk1',"参公群团年龄情况表（总表一）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenCgqtnlqk2(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Cgqtnlqk2','pages.customquery.formanalysis.Cgqtnlqk2',"参公群团年龄情况表（总表二）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenCgsynlqk1(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Cgsynlqk1','pages.customquery.formanalysis.Cgsynlqk1',"参公事业年龄情况表（总表一）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function OpenCgsynlqk2(){
	var groupid=document.getElementById('checkedgroupid').value;
	$h.openWin('Cgsynlqk2','pages.customquery.formanalysis.Cgsynlqk2',"参公事业年龄情况表（总表二）"+'',1200,550,groupid,'<%=request.getContextPath()%>');
}
function kaocha(){
	alert('调研中')
}


/* 无锡加 */

//隐藏干部处功能
function hiddenTTFAudit(){
	Ext.getCmp("unLockGBC").hide();
	Ext.getCmp("cadresTTFAudit").hide();
	Ext.getCmp("unLockAudit").hide();
}

//隐藏干部一处功能
function hiddenOAudit(){
	Ext.getCmp("cadresOAudit").hide();
	Ext.getCmp("unLockGBYC").hide();
	Ext.getCmp("unLockAudit").hide();
}

//隐藏审核条件
function hiddenAudit(){
	$(".isAuditCls").attr("style","display:none");
	Ext.getCmp("cadresAuditBtn").hide();
}

//隐藏取消干部处审核、取消干部一处审核
function hiddenSubAudit(){
	Ext.getCmp("unLockGBC").hide();
	Ext.fetCmp("unLockGBYC").hide();
}

function cadresTTFAudit(){
	if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('cadresTTFAudit',a0000s);
	}
}
function cadresOAudit(){
	if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('cadresOAudit',a0000s);
	}
}


function unLockGBC(){
	unLock(1)
}
function unLockGBYC(){
	unLock(2)
}
function unLockAll(){
	unLock('all')
}

function unLock(f){
	if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('unLockAudit',f+","+a0000s);
	}
}

//获取选中人员
function getChechA0000S(){
	var grid = Ext.getCmp('peopleInfoGrid');

	var total = grid.getStore().getCount();//数据行数
	var record; //行数据
	var a0000s = ""; //拟任干部名单

	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('personcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}

	return a0000s;
}


function impHistoryExcel(){
	radow.doEvent("impHistoryExcel");
}

function openBzyp(){
	var checkedgroupid = document.getElementById('checkedgroupid').value;
	
	if(checkedgroupid==''){
		$h.alert('系统提示','请选择单位！');
		return;
	}
	var a=checkedgroupid.indexOf("001.001.002");
	var b=checkedgroupid.indexOf("001.001.002.01O");
	var c=checkedgroupid.indexOf("001.001.002.01Q");
	var d=checkedgroupid.indexOf("001.001.002.02O");
	if( a<0 || checkedgroupid.length!=15 || a==4)
	{	
		if(b<0 && c<0 && d<0){
		$h.alert('系统提示','请选择正确单位！');
		return;
		}	
	}
	if(checkedgroupid.length==15 && (b>=0 || c>=0 || d>=0)){
		$h.alert('系统提示','请选择正确单位！');
		return;
	}
	if(checkedgroupid=='001.001.002.01O.005' || checkedgroupid=='001.001.002.01Q.003' || checkedgroupid=='001.001.002.02O.001'){
		$h.alert('系统提示','请选择正确单位！');
		return;
	}
	$h.openWin('BZYP','pages.fxyp.BZYP','单位班子研判',1000,650,null,ctxPath,null,
			{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true);
/* 	$h.openWin('BZYP','pages.fxyp.BZYP','班子研判',1000,650,{checkedgroupid:checkedgroupid},ctxPath); */
}
function openGqbzyp(){
	var checkedgroupid = document.getElementById('checkedgroupid').value;
	
	if(checkedgroupid==''){
		$h.alert('系统提示','请选择国企高校！');
		return;
	}
	var a=checkedgroupid.indexOf("001.001.003");
	if( a<0 || checkedgroupid.length!=15 || a==4)
	{	
		$h.alert('系统提示','请选择正确国企高校！');
		return;
		
	}

	$h.openWin('GQBZYP','pages.fxyp.GQBZYP','国企高校班子研判',1000,650,null,ctxPath,null,
			{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true);
/* 	$h.openWin('BZYP','pages.fxyp.BZYP','班子研判',1000,650,{checkedgroupid:checkedgroupid},ctxPath); */
}

function openQxsbzyp(){
	var checkedgroupid = document.getElementById('checkedgroupid').value;
	
	if(checkedgroupid==''){
		$h.alert('系统提示','请选择区县市！');
		return;
	}
	var a=checkedgroupid.indexOf("001.001.004");
	if(a< 0 || checkedgroupid.length!=15)
	{
		$h.alert('系统提示','请选择正确区县市！');
		return;
	}
	$h.openWin('QXSBZYP','pages.fxyp.QXSBZYP','区县市班子研判',1000,650,null,ctxPath,null,
			{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true);
/* 	$h.openWin('BZYP','pages.fxyp.BZYP','班子研判',1000,650,{checkedgroupid:checkedgroupid},ctxPath); */
}

function tjfxtab1(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXDZLDGBB',contextPath + "/pages/sysorg/hzb/QXDZLDGBB.jsp?a=1",'区、县（市）党政领导干部情况统计表',1200,600,null,{maximizable:true,resizable:true});
}
function tjfxtab2(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXSTLDBZJGB',contextPath + "/pages/sysorg/hzb/QXSTLDBZJGB.jsp?a=1",'区、县（市）四套领导班子结构性干部情况统计表',1200,600,null,{maximizable:true,resizable:true});
}
function tjfxtab3(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB',contextPath + "/pages/sysorg/hzb/SGGBTJB.jsp?a=1",'全市市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab4(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB2',contextPath + "/pages/sysorg/hzb/SGGBTJB2.jsp?a=1",'区县（市）市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab5(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB3',contextPath + "/pages/sysorg/hzb/SGGBTJB3.jsp?a=1",'市直机关部门（不含国企高校）市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab15(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB15',contextPath + "/pages/sysorg/hzb/SGGBTJB15.jsp?a=1",'市直单位市管干部情况统计表',1350,600,null,{maximizable:true,resizable:true});
}
function tjfxtab6(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB4',contextPath + "/pages/sysorg/hzb/SGGBTJB4.jsp?a=1",'市属高校市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab7(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB5',contextPath + "/pages/sysorg/hzb/SGGBTJB5.jsp?a=1",'市属国企市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab8(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB6',contextPath + "/pages/sysorg/hzb/SGGBTJB6.jsp?a=1",'市直单位（含国企高校）市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab9(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB7',contextPath + "/pages/sysorg/hzb/SGGBTJB7.jsp?a=1",'市级党政部门市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab10(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB8',contextPath + "/pages/sysorg/hzb/SGGBTJB8.jsp?a=1",'市级党委部门市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab11(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SGGBTJB9',contextPath + "/pages/sysorg/hzb/SGGBTJB9.jsp?a=1",'市级政府部门市管干部情况统计表',1300,600,null,{maximizable:true,resizable:true});
}
function tjfxtab12(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QSXZDZZZPBB',contextPath + "/pages/sysorg/hzb/QSXZDZZZPBB.jsp?a=1",'全市乡镇（街道）党政正职配备情况统计表',1200,600,null,{maximizable:true,resizable:true});
}
function tjfxtab13(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SZDWZCLDGBPBQK',contextPath + "/pages/sysorg/hzb/SZDWZCLDGBPBQK.jsp?a=1",'市直单位中层（处级）领导干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function tjfxtab14(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SZDWZCNQGB',contextPath + "/pages/sysorg/hzb/SZDWZCNQGB.jsp?a=1",'市直单位中层（处级）年轻干部配备情况',1200,650,null,{maximizable:true,resizable:true});
}

function qttjfxmenu1(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XSQDZLDBZ',contextPath + "/pages/sysorg/hzb/XSQDZLDBZ.jsp?a=1",'县（市、区）党政领导班子和干部队伍有关情况统计',1200,600,null,{maximizable:true,resizable:true});
}

function qttjfxmenu2(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZDZZZTJB',contextPath + "/pages/sysorg/hzb/XZDZZZTJB.jsp?a=1",'乡镇（街道）35岁左右及以下党政正职有关情况统计表',1200,600,null,{maximizable:true,resizable:true});
}

function qttjfxmenu3(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXSCJLDGBYGQKTJ',contextPath + "/pages/sysorg/hzb/QXSCJLDGBYGQKTJ.jsp?a=1",'设区市40岁左右及以下县处级领导干部有关情况统计',1200,600,null,{maximizable:true,resizable:true});
}
function qttjfxmenu4(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('HZSFNFZGHTJ',contextPath + "/pages/sysorg/hzb/HZSFNFZGHTJ.jsp?a=1",'杭州市妇女发展“十三五”规划监测指标',1200,600,null,{maximizable:true,resizable:true});
}
function qttjfxmenu5(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QGGBDWQK',contextPath + "/pages/sysorg/hzb/QGGBDWQK.jsp?a=1",'区管干部队伍情况统计表',1400,800,null,{maximizable:true,resizable:true});
}
function qttjfxmenu6(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZLDBZNQGBPBQK',contextPath + "/pages/sysorg/hzb/XZLDBZNQGBPBQK.jsp?a=1",'乡镇领导班子年轻干部配备情况（2021.06）',1200,700,null,{maximizable:true,resizable:true});
}
function qttjfxmenu7(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZLDBZNQGBPBQKK',contextPath + "/pages/sysorg/hzb/XZLDBZNQGBPBQKK.jsp?a=1",'乡镇领导班子年轻干部配备情况',1200,700,null,{maximizable:true,resizable:true});
}
function qttjfxmenu8(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXZSDWNQGBPBQK',contextPath + "/pages/sysorg/hzb/QXZSDWNQGBPPQK.jsp?a=1",'区县（市）直属单位年轻干部配备情况',1200,800,null,{maximizable:true,resizable:true});
}
function qttjfxmenu9(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZJDLDBZNQGBPZQK',contextPath + "/pages/sysorg/hzb/XZJDLDBZNQGBPZQK.jsp?a=1",'乡镇（街道）领导班子年轻干部配备情况',1200,700,null,{maximizable:true,resizable:true});
}
function cjnqgbtj(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('CJNQGBTJ',contextPath + "/pages/sysorg/hzb/CJNQGBTJ.jsp?a=1",'设区市县处级年轻干部有关情况统计',1400,800,null,{maximizable:true,resizable:true});
}
function xsqldbztj(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XSQLDBZTJ',contextPath + "/pages/sysorg/hzb/XSQLDBZTJ.jsp?a=1",'县（市、区）领导班子有关情况统计',1400,800,null,{maximizable:true,resizable:true});
}
function xzjdldbztj(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZJDLDBZTJ',contextPath + "/pages/sysorg/hzb/XZJDLDBZTJ.jsp?a=1",'乡镇（街道）领导班子有关情况统计',1400,800,null,{maximizable:true,resizable:true});
}
function xsqnqgbtj(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XSQNQGBTJ',contextPath + "/pages/sysorg/hzb/XSQNQGBTJ.jsp?a=1",'县（市、区）年轻干部有关情况统计',1400,800,null,{maximizable:true,resizable:true});
}
function qxcjgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('qxcjgbpb',contextPath + "/pages/sysorg/hzb/QXCJGBPB.jsp?a=1",'区县（市）管干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function qxszsdwgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXSZSDWGBPB',contextPath + "/pages/sysorg/hzb/QXSZSDWGBPB.jsp?a=1",'区县（市）直属单位领导干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function qxsdzbmgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXSDZBMGBPB',contextPath + "/pages/sysorg/hzb/QXSDZBMGBPB.jsp?a=1",'区县（市）党政部门领导干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function szdwcjzcgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SZDWCJZCGBPB',contextPath + "/pages/sysorg/hzb/SZDWCJZCGBPB.jsp?a=1",'市直单位处级（中层）干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function xzldgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZLDGBPB',contextPath + "/pages/sysorg/hzb/XZLDGBPB.jsp?a=1",'乡镇领导干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function xzjdldgbpb(){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('XZJDLDGBPB',contextPath + "/pages/sysorg/hzb/XZJDLDGBPB.jsp?a=1",'乡镇街道领导干部配备情况统计分析表',1220,650,null,{maximizable:true,resizable:true});
}
function tjfxtabdepmenu1(g){
	
	con=g.id;
	if("dwbm"==con)
		con="党委部门分表";
	if("zfbm"==con)
		con="政府部门分表";
	if("fjjg"==con)
		con="法检机关分表";
	if("mzdp"==con)
		con="民主党派分表";
	if("qt"==con)
		con="其他市级机关分表";
	if("ssgx"==con)
		con="市属高校分表";
	if("ssgq"==con)
		con="市属国企分表";
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('SZDWZCLDGBPBQKDEP',contextPath + "/pages/sysorg/hzb/SZDWZCLDGBPBQKDEP.jsp?a=1"+"&b="+g.id,con,1200,600,null,{maximizable:true,resizable:true});
	
}


/* <odin:menu property="piliangMenu">
<odin:menuItem text="批量维护" property="betchModifyBtn"   ></odin:menuItem>
<odin:menuItem text="批量考察" property="kaochaBtns"  isLast="true"  handler="kaocha" ></odin:menuItem>
</odin:menu> */
/* <odin:menu property="regMenu_m1">
<odin:menuItem text="任免表导入" property="importLrmBtn"  handler="impLrm" ></odin:menuItem>
<odin:menuItem text="批量任免表导入" property="importLrmBtns"  handler="impLrms" ></odin:menuItem>
<odin:menuItem text="批量照片替換" property="importPhotoBtns"  isLast="true"  handler="impPhotos" ></odin:menuItem>
</odin:menu> */




<odin:menu property="menu_athers">
<odin:menuItem text="&nbsp;增加" icon="image/icon021a2.gif" property="loadadd" />
<odin:menuItem text="&nbsp;修改" icon="image/icon021a6.gif"  property="rmbUpdate"/>
<odin:menuItem text="&nbsp;删除" icon="image/icon021a3.gif" property="deletePersonBtn" />
<odin:menuItem text="&nbsp;批量维护" icon="image/icon021a6.gif"  property="betchModifyBtn"  />
<%--<odin:menuItem text="&nbsp;简历提取" icon="image/icon021a6.gif"  property="getA17" />--%>
<odin:menuItem text="&nbsp;干部材料" icon="image/icon021a6.gif"  property="PersionFileBtn"  />
<odin:menuItem text="&nbsp;关心关爱" icon="image/icon021a6.gif"  property="GxgaFileBtn" handler="openGxgaFile"/>
<odin:menuItem text="&nbsp;考核评价" icon="image/icon021a6.gif" isLast="true"  property="KHPJFileBtn" handler="openKHPJFile"/>
</odin:menu>

<odin:menu property="menu_bzyp">
<odin:menuItem text="&nbsp;单位班子研判" icon="images/icon/bzyp.png" property="bzyp"  handler="openBzyp"/>
<odin:menuItem text="&nbsp;区县市班子研判" icon="images/icon/qxs.png"  property="qxsbzyp"  handler="openQxsbzyp"/>
<odin:menuItem text="&nbsp;国企高校班子研判" icon="image/icon021a6.gif" isLast="true" property="gqbzyp"  handler="openGqbzyp" />
</odin:menu>


<odin:menu property="printMenu">
<odin:menuItem text="打印" property="printRmb"  handler="print" ></odin:menuItem>
<odin:menuItem text="打印机设置" property="printSet"  isLast="true"  handler="printSet" ></odin:menuItem>
</odin:menu>

<odin:menu property="tjfxdepmenu">
<odin:menuItem text="市直单位中层（处级）领导干部配备情况统计分析表"  property="tjfxtab13"  handler="tjfxtab13"  ></odin:menuItem>
<odin:menuItem text="市直单位中层（处级）干部配备情况统计分析表（二）"  property="szdwcjzcgbpb" handler="szdwcjzcgbpb" ></odin:menuItem>
<odin:menuItem text="党委部门分表" property="dwbm" handler= "tjfxtabdepmenu1" ></odin:menuItem>
<odin:menuItem text="政府部门分表" property="zfbm" handler="tjfxtabdepmenu1"></odin:menuItem>
<odin:menuItem text="法检机关分表" property="fjjg" handler="tjfxtabdepmenu1"></odin:menuItem>
<odin:menuItem text="民主党派分表" property="mzdp" handler="tjfxtabdepmenu1" ></odin:menuItem>
<odin:menuItem text="其他市级机关分表" property="qt" handler="tjfxtabdepmenu1" ></odin:menuItem>
<odin:menuItem text="市属高校分表" property="ssgx" handler="tjfxtabdepmenu1" ></odin:menuItem>
<odin:menuItem text="市属国企分表" property="ssgq" handler="tjfxtabdepmenu1" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="qxldgbpb">
<odin:menuItem text="区县（市）管干部配备情况统计分析表"  property="qxcjgbpb" handler="qxcjgbpb"  ></odin:menuItem>
<odin:menuItem text="区县（市）直属单位领导干部配备情况统计分析表"  property="qxszsdwgbpb" handler="qxszsdwgbpb"  ></odin:menuItem>
<odin:menuItem text="区县（市）党政部门领导干部配备情况统计分析表"  property="qxsdzbmgbpb" handler="qxsdzbmgbpb"></odin:menuItem>
<odin:menuItem text="乡镇街道领导干部配备情况统计分析表" property="xzjdldgbpb" handler="xzjdldgbpb"  ></odin:menuItem>
<odin:menuItem text="乡镇领导干部配备情况统计分析表" property="xzldgbpb" handler="xzldgbpb"  isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="nqgbpbqk">
<odin:menuItem text="市直单位中层（处级）年轻干部配备情况"  property="tjfxtab14"  handler="tjfxtab14" ></odin:menuItem>
<odin:menuItem text="区县（市）直属单位年轻干部配备情况"  property="qttjfxmenu8"  handler="qttjfxmenu8" ></odin:menuItem>  
/* <odin:menuItem text="乡镇领导班子年轻干部配备情况（2021.06）"  property="qttjfxmenu6"  handler="qttjfxmenu6" ></odin:menuItem>  */ 
<odin:menuItem text="乡镇领导班子年轻干部配备情况"  property="qttjfxmenu7"  handler="qttjfxmenu7"></odin:menuItem>  
<odin:menuItem text="乡镇（街道）领导班子年轻干部配备情况"  property="qttjfxmenu9"  handler="qttjfxmenu9" isLast="true"></odin:menuItem> 
</odin:menu>

<odin:menu property="qttjfxmenu">
<odin:menuItem text="县（市、区）党政领导班子和干部队伍有关情况统计"  property="qttjfxmenu1"  handler="qttjfxmenu1"  ></odin:menuItem>
<odin:menuItem text="乡镇（街道）35岁左右及以下党政正职有关情况统计"  property="qttjfxmenu2"  handler="qttjfxmenu2"  ></odin:menuItem>
<odin:menuItem text="设区市40岁左右及以下县处级领导干部有关情况统计"  property="qttjfxmenu3"  handler="qttjfxmenu3" ></odin:menuItem>
<odin:menuItem text="杭州市妇女发展“十三五”规划监测指标"  property="qttjfxmenu4"  handler="qttjfxmenu4" ></odin:menuItem> 
<odin:menuItem text="区管干部队伍情况统计表"  property="qttjfxmenu5"  handler="qttjfxmenu5" isLast="true"></odin:menuItem>  

</odin:menu>

<odin:menu property="tjfxmenu1">
<odin:menuItem text="设区市县处级年轻干部有关情况统计"  property="cjnqgbtj"  handler="cjnqgbtj"  ></odin:menuItem>
<odin:menuItem text="县（市、区）领导班子有关情况统计"  property="xsqldbztj"  handler="xsqldbztj"  isLast="true" ></odin:menuItem>
/* <odin:menuItem text="乡镇（街道）领导班子有关情况统计"  property="xzjdldbztj"  handler="xzjdldbztj"   ></odin:menuItem>
<odin:menuItem text="县（市、区）年轻干部有关情况统计"  property="xsqnqgbtj"  handler="xsqnqgbtj"  isLast="true" ></odin:menuItem> */


</odin:menu>

<odin:menu property="tjfxqssggbmenu">
<odin:menuItem text="全市市管干部情况统计表" property="tjfxtab3"  handler="tjfxtab3"  ></odin:menuItem>
<odin:menuItem text="区县（市）市管干部情况统计表" property="tjfxtab4"  handler="tjfxtab4"  ></odin:menuItem>
<odin:menuItem text="市直机关部门（不含国企高校）市管干部情况统计表" property="tjfxtab5"  handler="tjfxtab5" ></odin:menuItem>
<odin:menuItem text="市直单位市管干部情况统计表" property="tjfxtab15"  handler="tjfxtab15" ></odin:menuItem>
<odin:menuItem text="市属高校市管干部情况统计表" property="tjfxtab6"  handler="tjfxtab6"   ></odin:menuItem>
<odin:menuItem text="市属国企市管干部情况统计表" property="tjfxtab7"  handler="tjfxtab7"  ></odin:menuItem>
<odin:menuItem text="市直单位（含国企高校）市管干部情况统计表" property="tjfxtab8"  handler="tjfxtab8"   ></odin:menuItem>
<odin:menuItem text="市级党政部门市管干部情况统计表" property="tjfxtab9"  handler="tjfxtab9"   ></odin:menuItem>
<odin:menuItem text="市级党委部门市管干部情况统计表" property="tjfxtab10"  handler="tjfxtab10"  ></odin:menuItem>
<odin:menuItem text="市级政府部门市管干部情况统计表" property="tjfxtab11"  handler="tjfxtab11"  isLast="true"  ></odin:menuItem>
</odin:menu>

<odin:menu property="TjfxMenu">
<odin:menuItem text="区、县（市）党政领导干部情况统计表" property="tjfxtab1" handler="tjfxtab1"  ></odin:menuItem>
<odin:menuItem text="区、县（市）四套领导班子结构性干部情况统计表" property="tjfxtab2"  handler="tjfxtab2"  ></odin:menuItem>
<odin:menuItem text="全市市管干部情况统计表"  menu="tjfxqssggbmenu"  ></odin:menuItem>
<odin:menuItem text="全市乡镇（街道）党政正职配备情况统计表" property="tjfxtab12"  handler="tjfxtab12" ></odin:menuItem>
<odin:menuItem text="市直单位中层（处级）领导干部配备情况统计分析表" menu = "tjfxdepmenu"  ></odin:menuItem>
<odin:menuItem text="年轻干部配备情况"  menu = "nqgbpbqk" ></odin:menuItem>
<odin:menuItem text="区县领导干部配备情况"  menu = "qxldgbpb"></odin:menuItem>
<odin:menuItem text="其他情况统计分析表" menu = "qttjfxmenu" isLast="true" ></odin:menuItem>
</odin:menu>



/* 干部审核 */
<odin:menu property="cadresAuditMenu">
<odin:menuItem text="干部处审核" property="cadresTTFAudit" handler="cadresTTFAudit"></odin:menuItem>
<odin:menuItem text="干部一处审核" property="cadresOAudit" handler="cadresOAudit"></odin:menuItem>
<odin:menuItem text="取消干部处审核" property="unLockGBC" handler="unLockGBC"></odin:menuItem>
<odin:menuItem text="取消干部一处审核" property="unLockGBYC" handler="unLockGBYC"></odin:menuItem>
<odin:menuItem text="取消全部审核" property="unLockAudit" handler="unLockAll" isLast="true"></odin:menuItem>
</odin:menu>

/* <odin:menu property="regMenu">
<odin:menuItem text="生成登记表" property="createDjb" handler="createExcelTemp"></odin:menuItem>
<odin:menuItem text="查看登记表" property="getDjb" handler="expExcelTemp"></odin:menuItem>
<odin:menuItem text="导出任免表" property="exportLrmxBtn" ></odin:menuItem>
<odin:menuItem text="导入任免表" property="importLrmBtn"  handler="impLrm" ></odin:menuItem>
<odin:menuItem text="批量导入任免表" property="importLrmBtns"  handler="impLrms" ></odin:menuItem>
<odin:menuItem text="批量打印任免表" property="batchPrint" isLast="true" ></odin:menuItem>
</odin:menu> */

<odin:menu property="expWord">
<odin:menuItem text="干部任免审批表" property="exp1"  handler="expWord1" ></odin:menuItem>
<odin:menuItem text="干部任免审批表(出生年月)" property="exp1_2"  handler="expWord1_2" ></odin:menuItem>
<odin:menuItem text="干部任免审批表(审核用)" property="exp1_1"  handler="expWord1_1" ></odin:menuItem>
<odin:menuItem text="公务员职级套转表" property="exp12"  handler="expWord12" ></odin:menuItem>
<odin:menuItem text="公务员登记表" property="exp2"  handler="expWord2" ></odin:menuItem>
<odin:menuItem text="参照公务员登记表" property="exp3"  handler="expWord3" ></odin:menuItem>
<odin:menuItem text="公务员登记备案表" property="exp4"  handler="expWord4" ></odin:menuItem>
<odin:menuItem text="参照公务员登记备案表" property="exp5"  handler="expWord5" ></odin:menuItem>
<odin:menuItem text="公务员年度考核登记表" property="exp6"  handler="expWord6" ></odin:menuItem>
<odin:menuItem text="奖励审批表" property="exp7"  handler="expWord7" ></odin:menuItem>
<odin:menuItem text="公务员录用表" property="exp10"  handler="expWord10" ></odin:menuItem>
<odin:menuItem text="公务员调任审批表" property="exp11"  handler="expWord11" ></odin:menuItem>
<odin:menuItem text="会议名册（excel）"  property="exp16"  handler="expWord16" ></odin:menuItem>
//<odin:menuItem text="会议名册（Word）"  property="exp8"  handler="expWord8" ></odin:menuItem>
<odin:menuItem text="干部名册（word）"  property="exp9"  handler="expWord9" ></odin:menuItem>
<odin:menuItem text="干部名册（excel）"  property="exp15"  handler="expWord15" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="expWord1">
<odin:menuItem text="干部任免审批表" property="exp1_right"  handler="expWord1_right" ></odin:menuItem>
<odin:menuItem text="干部任免审批表(审核用)" property="exp13_right"  handler="expWord13_right" ></odin:menuItem>
<odin:menuItem text="公务员职级套转表" property="exp12_right"  handler="expWord12_right" ></odin:menuItem>
<odin:menuItem text="公务员登记表" property="exp2_right"  handler="expWord2_right" ></odin:menuItem>
<odin:menuItem text="参照登记表" property="exp3_right"  handler="expWord3_right" ></odin:menuItem>
<odin:menuItem text="公务员登记备案表" property="exp4_right"  handler="expWord4_right" ></odin:menuItem>
<odin:menuItem text="参照登记备案表" property="exp5_right"  handler="expWord5_right" ></odin:menuItem>
<odin:menuItem text="公务员年度考核登记表" property="exp6_right"  handler="expWord6_right" ></odin:menuItem>
<odin:menuItem text="奖励审批表" property="exp7_right"  handler="expWord7_right" ></odin:menuItem>
<odin:menuItem text="公务员录用表" property="exp10_right"  handler="expWord10_right" ></odin:menuItem>
<odin:menuItem text="公务员调任审批表" property="exp11_right"  handler="expWord11_right" ></odin:menuItem>
<odin:menuItem text="干部花名册（一人一行）.xls" property="exp8_right"  handler="expWord8_right" ></odin:menuItem>
<odin:menuItem text="干部花名册（按机构分组）" property="exp9_right"  handler="expWord9_right" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="expMenu">
<odin:menuItem text="任免表导入" property="importLrmBtn"  handler="impLrm" ></odin:menuItem>
<odin:menuItem text="批量任免表导入" property="importLrmBtns"  handler="impLrms" ></odin:menuItem>
<odin:menuItem text="批量照片替換" property="importPhotoBtns"  handler="impPhotos" ></odin:menuItem>
<odin:menuItem text="导出业务表" menu = "expWord" ></odin:menuItem>
/* <odin:menuItem text="导出任免表Word" property="exportGBDocBtn" ></odin:menuItem> */
<odin:menuItem text="导出任免表Lrmx" property="exportLrmxBtn" handler="expLrmxGrid"></odin:menuItem>
/* <odin:menuItem text="导出全部数据" property="getAll" handler="expExcelFromGrid"></odin:menuItem> */
<odin:menuItem text="导出任免表Lrm" property="exportLrmBtn" handler="expLrmGrid"></odin:menuItem>
/* <odin:menuItem text="导出任免表PDF" property="exportPdfBtn" ></odin:menuItem> */
<odin:menuItem text="导出任免表PDF" property="exportPdfForAspose"></odin:menuItem>
<odin:menuItem text="导出个人数据HZB" property="importHzbBtn" handler="hzbBtn"></odin:menuItem>
<odin:menuItem text="导出PAD数据ZIP" property="importZipBtn" handler="zipBtn1"></odin:menuItem>
<odin:menuItem text="导出数据ZIP包" property="exportZipBtn" handler="exportZipBtn"></odin:menuItem>
<odin:menuItem text="导出公示ZIP包" property="expGSZipBtn" handler="expGSZipBtn"></odin:menuItem>
<odin:menuItem text="导出省组数据ZIP包" property="expSZSJZipBtn" handler="expSZSJZipBtn"></odin:menuItem>
/* <odin:menuItem text="导出个人数据7z" property="import7zBtn" handler="zipBtn"></odin:menuItem> */
/* <odin:buttonForToolBar text="表册导出" id="expBtnword" menu="expMenuword"   icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> */

/* <odin:menuItem text="导出无历史照片Excel" property="noHistoryExcel" handler="impHistoryExcel"></odin:menuItem> */
<odin:menuItem text="导出自定义表册" property="importExpButton" handler="expBtnword" isLast="true"></odin:menuItem>

</odin:menu>
<odin:menu property="regMenum">
/* handler="exportGBDoc" */
<odin:menuItem text="自定义表册" property="getDjbz" handler="othertem" ></odin:menuItem>
<odin:menuItem text="干部简要情况表" property="createGanBu" ></odin:menuItem>
<odin:menuItem text="干部任免审批表" property="createDjrmb" ></odin:menuItem>
<odin:menuItem text="公务员登记表" property="createDjbj" ></odin:menuItem>
<odin:menuItem text="公务员奖励审批表" property="createJiangLi" ></odin:menuItem>
<odin:menuItem text="公务员年度考核表" property="createKaoHe" ></odin:menuItem>
<odin:menuItem text="干部任免审批表2" property="createDjrmb2" handler= "printAppointment"></odin:menuItem>
<odin:menuItem text="花名册" property="createHuaMcc" handler="choses" isLast= "true"></odin:menuItem>
</odin:menu>

<odin:menu property="rmb">
<odin:menuItem text="打印任免表（包含拟任免信息）" handler="printTrue"></odin:menuItem>
<odin:menuItem text="打印任免表（不包含拟任免信息）" handler="printFalse" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="expL">
<odin:menuItem text="导出业务表" menu = "expWord1"></odin:menuItem>
/* <odin:menuItem text="导出任免表Word" handler="exportGBDocRigth"></odin:menuItem> */
<odin:menuItem text="导出任免表Lrmx" handler="expLrmx"></odin:menuItem>
<odin:menuItem text="导出任免表Lrm" handler="expLrm"></odin:menuItem>
<odin:menuItem text="导出任免表PDF" handler="exportPdf" ></odin:menuItem>
<odin:menuItem text="导出个人数据HZB" handler="hzbBtn" isLast="true"></odin:menuItem>
</odin:menu>






<odin:menu property="printRight">
<odin:menuItem text="干部任免审批表" property="print1" handler="printFalse"></odin:menuItem>
<odin:menuItem text="干部任免审批表(审核用)" property="print1_1" handler="printFalse1_1" isLast="true"></odin:menuItem>
</odin:menu>
<odin:menu property="updateM">
/* <odin:menuItem text="状态变更" handler="changeStateRigth"></odin:menuItem> */
<odin:menuItem text="人员增加" handler="incr"></odin:menuItem>
<odin:menuItem text="人员编辑" handler="modify"></odin:menuItem>
/* <odin:menuItem text="人员删除" handler="deletePeople"></odin:menuItem> */
/* <odin:menuItem text="人员调转" handler="transfer"></odin:menuItem> */
<odin:menuItem text="打印任免表" menu="printRight" ></odin:menuItem>
<odin:menuItem text="导出" menu="expL" isLast="true"></odin:menuItem>
/* <odin:menuItem text="表册输出" handler="out" isLast="true"></odin:menuItem> */
</odin:menu>



<odin:menu property="hztjBtnNl">
<odin:menuItem text="公务员基本情况表（总表一）" property="zbygwyjbqk"  handler="zbyfunc" ></odin:menuItem>
<odin:menuItem text="公务员基本情况表（总表二）" property="zbegwyjbqk"  handler="zbefunc" ></odin:menuItem>
<odin:menuItem text="参公群团总表" property="qtczglryqk"  handler="qtfunc" ></odin:menuItem>
<odin:menuItem text="参公事业总表" property="cgczglryqk"  handler="czfunc" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="nlqkzt">
<odin:menuItem text="公务员年龄情况表（总表一）" property="gwynlqk1" handler="OpenGwynlqk1" ></odin:menuItem>
<odin:menuItem text="公务员年龄情况表（总表二）" property="gwynlqk2" handler="OpenGwynlqk2"  ></odin:menuItem>
<odin:menuItem text="公务员年龄情况表（总表三）" property="gwynlqk3" handler="OpenGwynlqk3"  ></odin:menuItem>
<odin:menuItem text="公务员年龄情况表（总表四）" property="gwynlqk4" handler="OpenGwynlqk4"  ></odin:menuItem>
<odin:menuItem text="参公群团年龄情况表（总表一）" property="cgqtnlqk1"  handler="OpenCgqtnlqk1" ></odin:menuItem>
<odin:menuItem text="参公群团年龄情况表（总表二）" property="cgqtnlqk2"  handler="OpenCgqtnlqk2" ></odin:menuItem>
<odin:menuItem text="参公事业年龄情况表（总表一）" property="cgsynlqk1"  handler="OpenCgsynlqk1" ></odin:menuItem>
<odin:menuItem text="参公事业年龄情况表（总表二）" property="cgsynlqk2"  handler="OpenCgsynlqk2" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="ry_tjfx">
<odin:menuItem text="综合统计" property="jbqktjBtnZH" handler="zhlistshow"></odin:menuItem>
<odin:menuItem text="年龄统计" property="jbqktjBtnNl" handler="Nllistshow" ></odin:menuItem>
<odin:menuItem text="基本情况直统" property="hztjBtnNlmenu"  menu="hztjBtnNl"></odin:menuItem>
<odin:menuItem text="年龄情况直统" property="nlqkztBtn"  menu="nlqkzt" isLast="true"></odin:menuItem>
</odin:menu>
</script>

<%
	String areaname = (String) new GroupManagePageModel().areaInfo
			.get("areaname");
	String ctxPath = request.getContextPath();
	 //机构查询
	String groupID = request.getParameter("groupID");
	String userid = SysManagerUtils.getUserId();
	CommSQL.initA01_config(userid);
	List<Object[]> gridDataCollist = CommSQL.A01_CONFIG_LIST.get(userid);
	int size = gridDataCollist.size();
%>
<odin:hidden property="deleteA0000S" />
<odin:hidden property="removePersonIDs" />
<odin:hidden property="groupID" value="<%=groupID %>" />
<odin:hidden property="viewValue" value="" />
<odin:hidden property="isContainHidden" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="a02_a0201b_sb" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="" />
<odin:hidden property="a0000s" />
<odin:hidden property="a0000" />
<odin:hidden property="showTabID" />
<odin:hidden property="a0101" />
<odin:hidden property="mllb" />
<odin:hidden property="xzry" value="1" />
<odin:hidden property="lsry" />
<odin:hidden property="ltry" />
<odin:hidden property="padZipCons"/>
<!-- 增加全员整库搜索 -->
<odin:hidden property="tableType" value="1" />
<odin:hidden property="picA0000s" />
<odin:hidden property="docA0000s" />
<odin:hidden property="docA0101s" />
<odin:hidden property="showOrHid" value="hid" />
<odin:hidden property="pdfPath" />
<odin:hidden property="temporarySort" />
<odin:hidden property="printPdf" />
<odin:hidden property="lookOrWrite" />
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:buttonForToolBar text="列表" id="gridPeople" icon="images/icon/table.gif" handler="gridshow"/>
	<odin:separator />
	<odin:buttonForToolBar text="小资料" id="messagePeople" icon="images/icon/zl.png" handler="datashow"/>
	<odin:separator />
	<odin:buttonForToolBar text="照片" id="picshow" icon="images/icon/photo.png" handler="picshow"/>
	<odin:separator />
     <!--<odin:buttonForToolBar text="统计报表" id="tjGridList" icon="images/icon/table.gif" handler="tjGridList"/>-->
     <odin:buttonForToolBar id="tjfx" text="统计分析" menu="TjfxMenu" icon="images/icon/tjfx.png" />
	<odin:separator />	
    <odin:buttonForToolBar text="人员比对" cls="x-btn-text-icon" icon="images/icon/rybd.png" id="tpbj"></odin:buttonForToolBar>	
<%-- 	<odin:separator />
	<odin:buttonForToolBar text="班子研判" id="menu_bzypid"
		menu="menu_bzyp" icon="images/icon/bzyp.png" cls="x-btn-text-icon"></odin:buttonForToolBar> --%>
<%-- 	<odin:buttonForToolBar text="单位班子研判" id="bzyp" icon="images/icon/bzyp.png" handler="openBzyp"/>
	<odin:separator />
	<odin:buttonForToolBar text="区县市班子研判" id="qxsbzyp" icon="images/icon/qxs.png" handler="openQxsbzyp"/> 
	<odin:separator />
	<odin:buttonForToolBar text="国企高校班子研判" id="gqbzyp" icon="images/icon/bzyp.png" handler="openGqbzyp"/>
	<odin:separator /> --%>
<%-- 	<odin:separator />
	<odin:buttonForToolBar text="色块标识设置" id="skbs" icon="images/icon/skbs.png" handler="openSkbs"/> --%> 
	<%-- <odin:buttonForToolBar text="统计" id="listshowid"
		menu="ry_tjfx" icon="images/icon/icon031a7.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> --%>
	<odin:fill />
	<odin:buttonForToolBar text="pps数据抽样" id="ppsData" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<%-- <odin:buttonForToolBar text="状态变更" id="changeState" icon="images/icon/reset.gif" tooltip="变更人员状态" /> --%>
	
	<odin:buttonForToolBar text="信息维护" id="menu_athersid"
		menu="menu_athers" icon="images/icon/xxwh.png" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<%-- <odin:buttonForToolBar text="&nbsp;监督信息" icon="image/icon021a6.gif"  id="jdinformationBtn"  />menu="piliangMenu" id="piliangBtn"
	<odin:separator /> --%>
	<%-- <odin:buttonForToolBar text="人员查重" icon="image/u53.png" id="sameId" tooltip="搜索查看重复人员" />
	<odin:separator />	 --%>
	<%-- <odin:buttonForToolBar text="数据校核" icon="image/u53.png" id="dataVerify" tooltip="对选中人员进行数据校验" />
	<odin:separator />	 --%>
	<%-- <odin:buttonForToolBar text="表册导出" id="expBtnword" menu="expMenuword"   icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> --%>
	<%-- <odin:buttonForToolBar text="导入数据" id="imprmboldBtn"
		menu="regMenu_m1" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar> --%>
	<odin:buttonForToolBar text="导入导出" id="expBtn" menu="expMenu" icon="images/icon/drdc.png" cls="x-btn-text-icon"></odin:buttonForToolBar>

	<%-- <odin:buttonForToolBar text="打印任免表" id="printBtn"
	menu="printMenu" icon="images/icon/printer.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>--%>

	<odin:buttonForToolBar text="干部审核" id="cadresAuditBtn"
	menu="cadresAuditMenu" icon="images/icon/query.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>

	<%-- <odin:buttonForToolBar text="导出数据" id="expBtn" id="expId"
		menu="expMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar> --%>
	<%-- <odin:buttonForToolBar id="shows" text="显示子集" icon="images/search.gif" cls="x-btn-text-icon" handler="showSons"/>
	<odin:buttonForToolBar id="hids" text="隐藏子集" icon="images/search.gif" cls="x-btn-text-icon" handler="showSons" />
	<odin:separator /> --%>
	<!--<odin:buttonForToolBar text="表册输出" id="regBtn1" menu="regMenum" icon="images/icon_photodesk.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>-->
	<%-- <odin:buttonForToolBar text="表册输出_new" id="regBtn1_new" handler="openchose_new" icon="images/icon_photodesk.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<odin:separator /> --%>
	<%-- <odin:buttonForToolBar text="表册输出" id="regBtn1" handler="openchose" icon="images/icon_photodesk.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar> --%>
</odin:toolBar>


<script type="text/javascript">


function openSkbs(){
	var checkedgroupid = document.getElementById('checkedgroupid').value;
	$h.openWin('SKBS','pages.fxyp.SKBS','色块标识设置',1000,650,null,ctxPath,null,
			{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true);
}

/* 列表显示 */
function gridshow(){
	//resizeframe();
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		//alert("请先双击机构树进行查询")
		Ext.Msg.alert("系统提示","请先双击机构树进行查询！");
		return;
	}; */

	//将temporarySort置为1
	radow.doEvent("setTemporarySort");

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}


	changeType(1);
	Ext.getCmp('peopleInfoGrid').show();
	document.getElementById("pictable").style.display='none';
	document.getElementById("picdata").style.display='none';

	var grid = Ext.getCmp('peopleInfoGrid');    //通过grid的id取到grid
    grid.store.reload();      //将数据reload()就可以刷新了
}
/* 小资料显示 */
function datashow(){
	//如果此时已经打开显示子集，则将其隐藏
	/* var dd = document.getElementById("formulaResult_view");
	if(dd.style.display != "none"){
		//odin.alert("切换小资料或者照片前，请先点击——隐藏子集 ");
		dd.style.display = "none";
		odin.ext.getCmp('peopleInfoGrid').setHeight(objHeight);

		//odin.ext.getCmp('shows').show();
		//odin.ext.getCmp('hids').hide();

	} */



	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	changeType(2);
	/* 隐藏列表 */
	Ext.getCmp('peopleInfoGrid').hide();
	/* 隐藏照片 */
	document.getElementById("pictable").style.display='none';

	document.getElementById("picdata").style.display='block';
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		document.getElementById("datai"+i).style.display="none";
		document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("page3").value="1";
	radow.doEvent("ShowData");

}


function cutString(str, len) {
		//alert(str.length);
		  //length属性读出来的汉字长度为1
		  if(str.length*2 <= len) {
			  return str;
		  }
		  var strlen = 0;
		  var s = "";
		  for(var i = 0;i < str.length; i++) {
			  s = s + str.charAt(i);
			  if (str.charCodeAt(i) > 128) {
				  strlen = strlen + 2;
				  if(strlen >= len){
					  return s.substring(0,s.length-1) + "...";
				  }
			  } else {
				  strlen = strlen + 1;
				  if(strlen >= len){
					  return s.substring(0,s.length-2) + "...";
				  }
			  }
		  }
		  return s;
	 }


/* 照片显示 */
function picshow(){
	//如果此时已经打开显示子集，则将其隐藏
	/* var dd = document.getElementById("formulaResult_view");
	if(dd.style.display != "none"){
		//odin.alert("切换小资料或者照片前，请先点击——隐藏子集 ");
		dd.style.display = "none";
		odin.ext.getCmp('peopleInfoGrid').setHeight(objHeight);

		//odin.ext.getCmp('shows').show();
		//odin.ext.getCmp('hids').hide();
	} */

	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		//alert("请先双击机构树进行查询")
		Ext.Msg.alert("系统提示","请先双击机构树进行查询！");
		return;
	}; */

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}


	changeType(3);
	/* 隐藏列表 */
	Ext.getCmp('peopleInfoGrid').hide();
	/* 隐藏小资料 */
	document.getElementById("picdata").style.display='none';

	document.getElementById("pictable").style.display='block';
	for(var i=0;i<10;i++){
		document.getElementById("i"+i).src="";
		document.getElementById("i"+i).alt="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("i"+i).name="";
		document.getElementById("i"+i).style.display="none";
		document.getElementById("g"+i).innerHTML="";
		document.getElementById("c"+i).style.display="none";
	}
	document.getElementById("page").value="1";
	radow.doEvent("Show");

}

function tjGridList(){
	var width = 320;
	var height = 320;
	var pWidth = screen.availWidth;
	var pHeigth = screen.availHeight;

    if (!width || pWidth < width) {
        width = pWidth;
    }
    if (!height || pHeigth < height) {
        height = pHeigth;
    }
    var wtop = (pHeigth - height) / 2;
    var wleft = (pWidth - width) / 2;
    url = 'pages.tbyearconf.DbTjByParam';
    var obj = {};
    obj['window'] = window;
    obj['title'] = "统计报表";
	var url = '<%=url1 %>';
    var url = 'http://'+url+'/radowAction.do?method=doEvent&pageModel=pages.tbyearconf.DbTjByParam&flag=2&vid=321B138965EC826CD59CEB97D095DF86E69C7E7A1E62A225399AE01716F6A2D9A35357087DA8170CC225B73294BAFD762C25EAB37B6D7F6BFB44B1105545C94E31683467FF6FC844FE26C5F6E42A210F4BB95B59CAA916CCB21EE8E64E00D961F83EF6D07A334B63A7CB79C45F46B7A7921880F57B4D2DEDDD97F652B9086CD7';
	var p = "dialogWidth="+width+"px;dialogHeight="+height+"px;help=no;resizable=no;status=no;center=yes;scroll:no;location:no;toolbar:no;menubar:no;titlebar:no;";
	//window.showModalDialog(url,obj,p);
	tjGrid(url,obj,p);
	//$h.openPageModeWin('GenerateByA01','pages.tbyearconf.DbTjByParam','统计生成', 320, 330);
}
function tjGrid(url,obj,p){
	$.ajax({
		  url: url,
		  type: 'GET',
		  complete: function(response) {
		   if(response.status == 200) {
			   window.showModalDialog(url,obj,p);
		   } else {
			   Ext.Msg.alert("系统提示", "对不起，访问地址有误，请联系管理员");
		   }
		  }
		 });
}

function showpic(i,a0000,a0101,a0192a){
	//document.getElementById("i"+i).src=path;
	document.getElementById("i"+i).style.display="";
	document.getElementById("i"+i).alt=a0101;
	document.getElementById("i"+i).name=a0000;
	document.getElementById("f"+i).innerHTML=a0101;
	document.getElementById("g"+i).innerHTML=a0192a;
	document.getElementById("c"+i).style.display="";

}
function showdata(i,a0000,a0101,data){
	//document.getElementById("datai"+i).src=path;
	document.getElementById("datai"+i).style.display="";
	document.getElementById("datai"+i).alt=a0101;
	document.getElementById("datai"+i).name=a0000;
	document.getElementById("dataf"+i).innerHTML=a0101;
	document.getElementById("datac"+i).style.display="";
	//document.getElementById("resume"+i).innerHTML=data;
	var s=cutString(data,200);
	document.getElementById('resume'+i).innerHTML=s;
	document.getElementById("resume"+i).title=data;

}

/* 照片的方法 */
function setlength(size){
	document.getElementById("l").innerHTML=size;
	var p = size/10;
	document.getElementById("page2").innerHTML = Math.ceil(p);
}
/* 小资料的方法 */
function setlength2(size){
	document.getElementById("l2").innerHTML=size;
	var p = size/6;
	document.getElementById("page4").innerHTML = Math.ceil(p);
}
function nextpage(){
	var page=document.getElementById("page").value;
	var length=document.getElementById("l").innerHTML;
	var maxpage=Math.ceil(length/10);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		//alert("已经是最后一页");
		Ext.Msg.alert("系统提示","已经是最后一页！");
		return;
	}
	for(var i=0;i<10;i++){
		document.getElementById("i"+i).src="";
		document.getElementById("i"+i).alt="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("i"+i).name="";
		document.getElementById("c"+i).checked=false;
		document.getElementById("i"+i).style.display="none";
		document.getElementById("c"+i).style.display="none";
		document.getElementById("g"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	document.getElementById("page").value=newpage;
	radow.doEvent("picshow");
	//checkAllPeople();
}
function lastpage(){
	var page=document.getElementById("page").value;
	if(page=="1"){
		//alert("已经是第一页");
		Ext.Msg.alert("系统提示","已经是第一页！");
		return;
	}
	for(var i=0;i<10;i++){
		document.getElementById("i"+i).src="";
		document.getElementById("i"+i).alt="";
		document.getElementById("f"+i).innerHTML="";
		document.getElementById("i"+i).name="";
		document.getElementById("c"+i).checked=false;
		document.getElementById("i"+i).style.display="none";
		document.getElementById("c"+i).style.display="none";
		document.getElementById("g"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	var newpage=parseInt(page)-1;
	document.getElementById("page").value=newpage;
	radow.doEvent("picshow");
	//checkAllPeople();
}
 function fristpage(){
	 for(var i=0;i<10;i++){
			document.getElementById("i"+i).src="";
			document.getElementById("i"+i).alt="";
			document.getElementById("f"+i).innerHTML="";
			document.getElementById("i"+i).name="";
			document.getElementById("c"+i).checked=false;
			document.getElementById("i"+i).style.display="none";
			document.getElementById("c"+i).style.display="none";
			document.getElementById("g"+i).innerHTML="";
		}
	 document.getElementById("picA0000s").value = "";
	 document.getElementById("page").value=1;
	 radow.doEvent("picshow");
	 //checkAllPeople();
 }
 function endpage(){
	 var page=document.getElementById("page").value;
		var length=document.getElementById("l").innerHTML;
		var maxpage=Math.ceil(length/10);
		for(var i=0;i<10;i++){
			document.getElementById("i"+i).src="";
			document.getElementById("i"+i).alt="";
			document.getElementById("f"+i).innerHTML="";
			document.getElementById("i"+i).name="";
			document.getElementById("c"+i).checked=false;
			document.getElementById("i"+i).style.display="none";
			document.getElementById("c"+i).style.display="none";
			document.getElementById("g"+i).innerHTML="";
		}
		document.getElementById("picA0000s").value = "";
		document.getElementById("page").value=maxpage;
		radow.doEvent("picshow");
		//checkAllPeople();
 }
 function showgrid(){
	    Ext.getCmp('peopleInfoGrid').show();
	    document.getElementById("pictable").style.display='none';
		document.getElementById("picdata").style.display='none';
		changeType(1);
 }
 function querypep(name){
	 if(name==""){
		 return;
	 }
	 radow.doEvent("pic.dbclick",name);
 }

function checkThis(){
	var picA0000s = "";
	var tableType = document.getElementById("tableType").value;
	if(tableType == "2"){
		for(var i=0;i<6;i++){
			var tof = document.getElementById("datac"+i).checked;
			if(tof){
				var node = document.getElementById("datai"+i);
				var a0000 = node.name;
				picA0000s ="'" + a0000 + "'," + picA0000s;
			}
		}
	}
	if(tableType == "3"){
		for(var i=0;i<10;i++){
			var tof = document.getElementById("c"+i).checked;
			if(tof){
				var node = document.getElementById("i"+i);
				var a0000 = node.name;

				picA0000s ="'" + a0000 + "'," + picA0000s;
			}
		}
	}
	document.getElementById("picA0000s").value = picA0000s;
}
function clickThis(id){
	var tableType = document.getElementById("tableType").value;
	if(tableType == "2"){
		var newid = id.replace("sp","datac");
	}
	if(tableType == "3"){
		var newid = id.replace("p","c");
	}
	var tof = document.getElementById(newid).checked;
	if(tof==false){
		document.getElementById(newid).checked = true;
	}
	if(tof==true){
		document.getElementById(newid).checked = false;
	}
	checkThis();
}
function changeType(value){
	//清空列表复选框
	var gridId = "peopleInfoGrid";
	var fieldName = "personcheck";
	var store = odin.ext.getCmp(gridId).store;
	var length = store.getCount();
	for(index=0;index<length;index++){
		store.getAt(index).set(fieldName,false);
	}
	document.getElementById("checkList").value = "";
	//清空小资料复选框
	for(var i=0;i<6;i++){
		var tof = document.getElementById("datac"+i).checked;
		if(tof){
			document.getElementById("datac"+i).checked = false;
		}
	}
	//清空照片复选框
	for(var i=0;i<10;i++){
		var tof = document.getElementById("c"+i).checked;
		if(tof){
			document.getElementById("c"+i).checked = false;
		}
	}
	//清空全部人员按钮
	//document.getElementById("checkAll").checked = false;
	//document.getElementById("checkAll2").checked = false;
	//document.getElementById("checkAll3").checked = false;

	document.getElementById("tableType").value = value;
	document.getElementById("picA0000s").value = "";
	showDisabled(value);
}
/* disabled显示子集 */
function showDisabled(value){
	//列表
	if(value == '1'){
		//odin.ext.getCmp('hids').setDisabled(false);
		//odin.ext.getCmp('shows').setDisabled(false);
		odin.ext.getCmp('exportLrmBtn').show();
		odin.ext.getCmp('exportLrmxBtn').show();
		odin.ext.getCmp('exportPdfForAspose').show();
		odin.ext.getCmp('createHuaMcc').show();
	}
	//小资料
	if(value == '2'){
		//odin.ext.getCmp('hids').setDisabled(true);
		//odin.ext.getCmp('shows').setDisabled(true);
		odin.ext.getCmp('exportLrmBtn').show();
		odin.ext.getCmp('exportLrmxBtn').show();
		odin.ext.getCmp('exportPdfForAspose').show();
		odin.ext.getCmp('createHuaMcc').hide();
	}
	//照片
	if(value == '3'){
		odin.ext.getCmp('exportLrmBtn').show();
		odin.ext.getCmp('exportLrmxBtn').show();
		odin.ext.getCmp('exportPdfForAspose').show();
		odin.ext.getCmp('createHuaMcc').hide();
	}
}
/* 小资料翻页方法 */
function nextpage2(){
	var page=document.getElementById("page3").value;
	var length=document.getElementById("l2").innerHTML;
	var maxpage=Math.ceil(length/6);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		//alert("已经是最后一页");
		Ext.Msg.alert("系统提示","已经是最后一页！");
		return;
	}
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		document.getElementById("datac"+i).checked=false;
		document.getElementById("datai"+i).style.display="none";
		document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	document.getElementById("page3").value=newpage;

	radow.doEvent("datashow");
	//checkAllPeople();
}
function lastpage2(){
	var page=document.getElementById("page3").value;
	if(page=="1"){
		//alert("已经是第一页");
		Ext.Msg.alert("系统提示","已经是第一页！");
		return;
	}
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		document.getElementById("datac"+i).checked=false;
		document.getElementById("datai"+i).style.display="none";
		document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	var newpage=parseInt(page)-1;
	document.getElementById("page3").value=newpage;
	radow.doEvent("datashow");
	//checkAllPeople();
}
//判断是否选择全部人员按钮
function checkAllPeople(){
	var checkAll;
	var tableType = document.getElementById("tableType").value;
	if(tableType == '2'){
		checkAll = document.getElementById("checkAll2");
	}
	if(tableType == '3'){
		checkAll = document.getElementById("checkAll3");
	}
	 var tof = checkAll.checked;
	 if(tof){
		 selectAllPeople();
	 }
}
 function fristpage2(){
	 for(var i=0;i<6;i++){
			document.getElementById("datai"+i).src="";
			document.getElementById("datai"+i).alt="";
			document.getElementById("dataf"+i).innerHTML="";
			document.getElementById("datai"+i).name="";
			document.getElementById("datac"+i).checked=false;
			document.getElementById("datai"+i).style.display="none";
			document.getElementById("datac"+i).style.display="none";
			document.getElementById("resume"+i).innerHTML="";
		}
	 document.getElementById("picA0000s").value = "";
	 document.getElementById("page3").value=1;
	 radow.doEvent("datashow");
	 //checkAllPeople();
 }
 function endpage2(){
	 var page=document.getElementById("page3").value;
		var length=document.getElementById("l2").innerHTML;
		var maxpage=Math.ceil(length/6);
		for(var i=0;i<6;i++){
			document.getElementById("datai"+i).src="";
			document.getElementById("datai"+i).alt="";
			document.getElementById("dataf"+i).innerHTML="";
			document.getElementById("datai"+i).name="";
			document.getElementById("datac"+i).checked=false;
			document.getElementById("datai"+i).style.display="none";
			document.getElementById("datac"+i).style.display="none";
			document.getElementById("resume"+i).innerHTML="";
		}
		document.getElementById("picA0000s").value = "";
		document.getElementById("page3").value=maxpage;
		radow.doEvent("datashow");
		//checkAllPeople();
 }

//机构查询调用
/* function queryDO(groupID){
	//var nodeSelect = Ext.getCmp('group').getNodeById('001.001');

	//alert(groupStr);

	//nodeSelect.expand();
	//tree.expandAll();
 	//tree.expandAll(alert(1111)); */
 /* 	var nodeSelect = Ext.getCmp('group').getNodeById('001.001');
 	nodeSelect.select(); */

 	 /*$('#isContain').attr('checked',true);
	radow.doEvent('querybyid',groupID);
  	tree.getLoader().load(
 		tree.getRootNode(),
 		function(){
 			tree.expandPath(path, 'id', function(bSucess, oLastNode) {
                tree.getSelectionModel().select(oLastNode);
            });
 		},
 		this
 	);
} */
var tree;
Ext.onReady(function() {
	<% if (!"1".equals(isTemplateDB)){ %>
		var newWin_1 = $h.getTopParent().Ext.getCmp('conditionwin');
		if(newWin_1){
			newWin_1.close();
		}
		newWin_1 = $h.getTopParent().Ext.getCmp('group');
		if(newWin_1){
			newWin_1.close();
		}
	<%}%>
	  var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
       tree = new Tree.TreePanel( {
  	  id:'group',
        el : 'tree-div',//目标div容器
        split:false,
        width: 270,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//是否显示最上级节点
        autoScroll : true,
        animate : true,
        border:false,
        height:548,
        /* tbar:new Ext.Toolbar({
	    	   		id:'tool1',
	    	   		items: [ {
	         		   xtype: "checkbox",
	                  boxLabel : "包含下级",
	             //     width: 135,
	                  id:'isContain'
	                },'-', */
	                /* {  xtype: "checkbox",
		                   boxLabel : "按职务层次排序",
		                   width: 135,
		                   checked : true,
		                   id:'paixu'
		            },
	                {  xtype: "checkbox",
	                   boxLabel : "现职人员",
	                   checked : true,
	                   id:'xzry'
	                },'-',
	                {  xtype: "checkbox",
	                   boxLabel : "历史人员",
	                   id:'lsry'
	                },'-',
	                {  xtype: "checkbox",
	                   boxLabel : "离退人员",
	                   id:'ltry'
	                }],
	         height:45,
	         layout :'column'}),*/
        enableDD : false,
        containerScroll : true,
        loader : new Tree.TreeLoader( {
              dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1',
              baseParams : {sign: 'look'}
        }),
        listeners: {
            'click':function(node,e){
            	//alert(11111);
            	//每次点击机构树都去除上次的排序标识 并去除输入框的内容
            	cancleSort();
            }
          }
    });
	var root = new Tree.AsyncTreeNode({
		checked : false,
		text : document.getElementById('ereaname').value,
		iconCls : document.getElementById('picType').value,
		draggable : false,
		id : document.getElementById('ereaid').value,//默认的node值：?node=-100
		href : "javascript:radow.doEvent('querybyid','" + document.getElementById('ereaid').value + "')"
	});
	tree.setRootNode(root);
	tree.render();
	tree.expandPath(root.getPath(),null,function(){addnode();});
	//tree.expandPath(root.getPath(),null,function(){addnodebm();});   //无任职部门
	//tree.expandPath(root.getPath(),null,function(){addnodebm();});   //无主职务或主职务已免
	root.expand(false,true, callback);


	//增加tree的右击菜单 ，设置机构的集体内排序
  /*  var contextmenu = new Ext.menu.Menu({
        id : 'Menu',
        items : [{
            text : '集体内排序',
            handler : function(node,e){
            	//排除“无职务人员”
            	if(currentnode.id == "X001"){
            		Ext.Msg.alert('系统提示','无职务人员不支持集体内排序！');
            		return;
            	}

            	var a0201b = document.getElementById("a0201b").value;
            	//alert(a0201b);
            	if(a0201b == ""){
            		Ext.Msg.alert('系统提示','请先选择机构查询出结果！');
            		return;
            	}
                document.getElementById("a0201b_combo").value = currentnode.text;
                //document.getElementById("a0201b").value = currentnode.id;
                //打开集体内排序弹窗页面
                $h.openWin('a0225Win','pages.publicServantManage.A0225Win','集体内排序',500,650,currentnode.id,ctxPath);
            }
        }]
    });

    tree.on("contextmenu",function(node,e){
        e.preventDefault();
        currentnode = node;
        var a = node.id.indexOf('A');
        if(a == -1){
            node.select();
            contextmenu.showAt(e.getXY());
        }
    })  */

});

var callback = function (node){
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
    }
};

function addnode(){
	var nodeadd = tree.getRootNode();
	var newnode = new Ext.tree.TreeNode({
		  text: '无职务人员',
		  //text: '无职历人员',
          expanded: false,
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001',
          leaf: false ,
          /* dblclick:"javascript:radow.doEvent('querybyid','X001')" */
          href:"javascript:radow.doEvent('querybyid','X001')"
      });
	  var isDisplay = <%=isDisplay%>;
	  if(isDisplay===1){
      nodeadd.appendChild(newnode);
	  }
}

function addnodebm(){
	var nodeadd = tree.getRootNode();
	var newnode = new Ext.tree.TreeNode({
		  text: '无主职务或主职务已免人员',
          expanded: false,
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X002',
          leaf: false ,
          /* dblclick:"javascript:radow.doEvent('querybyid','X002')" */
          href:"javascript:radow.doEvent('querybyid','X002')"
      });
	 var isDisplay = <%=isDisplay%>;
	 if(isDisplay===1){
	 nodeadd.appendChild(newnode);
	}
}

function reloadTree() {
	var tree = Ext.getCmp("group");
	//获取选中的节点
	var node = tree.getSelectionModel().getSelectedNode();
	if(node == null) { //没有选中 重载树
		tree.root.reload();
	} else {        //重载树 并默认选中上次选择的节点
	    var path = node.getPath('id');
	    tree.getLoader().load(tree.getRootNode(),
	                function(treeNode) {
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {
	                                tree.getSelectionModel().select(oLastNode);
	                            });
	                }, this);
	}
}

function doQuery() {
	radow.doEvent('dogrant',changeNode);
}


//设置显示字段
function setFields(){
	var url = contextPath + "/pages/customquery/A01FieldsConfig.jsp?";
	//doOpenPupWin(url, "设置显示字段", 400, 550);
	$h.showWindowWithSrc("setFields",url,"设置显示字段", 400, 610);
}


//设置每页条数
function setPageSize1(){

	var gridId = 'peopleInfoGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {

		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 150);
		//$h.openWin('policyListWin',url,'政策法规',1050,550,'',ctxPath);


	} else {
		odin.error("非分页grid不能使用此功能！");

	}
}

function openconditionwin(){
	//var newWin_ = $h.getTopParent().Ext.getCmp('conditionwin');
	//if(!newWin_){
		newWin_ = $h.openWin('conditionwin','pages.customquery.QueryConditionEx','查询方案',1000,550,'',contextPath,null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false,maximizable:false,resizable:false});
		//newWin_.collapse(false);
		//newWin_.expand(true);
	//}else{
		//newWin_.expand(true);
	//}

}

function conditionRowdbclick(sqlstr,orgjson){

	//alert( Ext.util.JSON.encode(orgjson));
	var radioC = document.getElementsByName("radioC");
    for (i=0; i<radioC.length; i++) {
        if (radioC[i].checked) {
        	radioC = radioC[i].value;
        	break;
        }
    }
	//var hasQueried = document.getElementById("sql").value;
	//.alert(radioC);
	/* if("1"!=radioC){
		if(""==hasQueried || hasQueried==null){
			$h.getTopParent().Ext.Msg.alert('系统提示',"未进行过查询请先查询!");
			return;
		}else{

		}
	} */
    document.getElementById("sql").value = sqlstr;
    document.getElementById("orgjson").value = Ext.util.JSON.encode(orgjson);
    document.getElementById("tabn").value = 'tab3';//固定查询
    //判断列表、小资料、照片
    var tableType = document.getElementById("tableType").value;
    if ('1' == tableType) {
        radow.doEvent("peopleInfoGrid.dogridquery");
    }
    if ('2' == tableType) {
        radow.doEvent("peopleInfoGrid.dogridquery");
        datashow();
    }
    if ('3' == tableType) {
        radow.doEvent("peopleInfoGrid.dogridquery");
        picshow();
	}
	var newWin_ = $h.getTopParent().Ext.getCmp('conditionwin');
	if(!newWin_){
	}else{
		//newWin_.collapse(false);
		newWin_.close();
	}
}

function refresh(){
	odin.reset();//刷新当前页面
}


</script>

<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
%>

<%
		String json = "['all','全部'],['01','中央管理干部'],['02','省级党委管理干部'],['03','市级党委管理干部'],['04','县级党委管理干部'],['09','其他']";
		//设置管理干部类别的下拉选项（与权限联系）
		String sql = "SELECT RATE FROM SMT_USER WHERE USERID = '"+SysManagerUtils.getUserId()+"'";
		Object obj = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
		String value = "";
		if(obj != null){
			value = obj.toString().replaceAll("\\'", "");
		}
		if(!"".equals(value)){
			String[] s = value.split(",");
			for(int i=0;i<s.length;i++){
				String v = s[i];
				if("01".equals(v)){
					json = json.replace(",['01','中央管理干部']","");
				}
				if("02".equals(v)){
					json = json.replace(",['02','省级党委管理干部']","");
				}
				if("03".equals(v)){
					json = json.replace(",['03','市级党委管理干部']","");
				}
				if("04".equals(v)){
					json = json.replace(",['04','县级党委管理干部']","");
				}
				if("09".equals(v)){
					json = json.replace(",['09','其他']","");
				}
			}
		}
	%>
<odin:hidden property="qvid" />
<!-- 当前选择的机构树机构名称 -->
<odin:hidden property="a0201b_combo" />
<!-- 是否查询单个机构，决定人员不同排序方式：true-单机构查询，false-多机构查询 -->
<odin:hidden property="orgjson" />
<odin:hidden property="singleOrg" value="true" />
<odin:hidden property="isContainHidden" />
<odin:hidden property="tabn" />
<odin:hidden property="deleteTip"/>  <!-- 删除提示文字 -->
<odin:hidden property="listSql" />
<odin:hidden property="WhetherMatching" />
<div id="groupTreeContent" style="display:none;height: 100%; padding-top: 0px;">
<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
		<tr>
			<td width="270" id="td1">
		 		<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-right: 1px solid rgb(153,187,232);">
					<tr>
						<tags:ComBoxWithTree property="personq"  label="" required="false" disabled="false" codetype="ZB126" width="90"  />
						<td width="110"></td>
						<odin:textEdit property="seachName" emptyText="输入姓名" width="110" />
						<td><odin:button text="搜索" property="searchOnePerson" handler="searchPersonByName"></odin:button></td>
					</tr>
				</table>

				<table width="270"  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%; overflow: auto;">
					<tr>
							<td valign="top">
								<odin:tab id="tab" width="270" height="498" tabchange="grantTabChange">
								<odin:tabModel>
									<odin:tabItem title="机构树" id="tab1"></odin:tabItem>
				       				<odin:tabItem title="&nbsp&nbsp查询&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>
								</odin:tabModel>
								<odin:tabCont itemIndex="tab1" className="tab">
									<table id="tableTab1" style="height: 480;border-collapse:collapse;">
										<tr>
											<td>
												<div id="tree-div"
													style="overflow: auto; height: 100%; width: 100%; border: 2px solid #c3daf9;"></div>
												<odin:hidden property="area" value="<%=areaname%>" /> <odin:hidden
													property="id" /> <odin:hidden property="fid" /> <odin:hidden
													property="checkedgroupid" /> <odin:hidden
													property="forsearchgroupid" /> <odin:hidden
													property="ereaname" value="<%=ereaname%>" /> <odin:hidden
													property="ereaid" value="<%=ereaid%>" /> <odin:hidden
													property="manager" value="<%=manager%>" /> <odin:hidden
													property="picType" value="<%=picType%>" /> <odin:hidden
													property="sql" /> <odin:hidden property="additionalSql" /> <odin:hidden
													property="cueRowIndex" /> <odin:hidden property="saveName" />
													<odin:hidden property="codevalueparameter" /> <odin:hidden
													property="checkList" /> <odin:hidden property="a0201b" />
													<odin:hidden property="cqli" /></td>
										</tr>
										<tr>
											<td style="width: 100%; background-color: #cedff5;height: 30px;">
												<input type="checkbox" id="isContain" checked="checked"> <font style="font-size: 13">包含下级</font>
												<input class="isAuditCls" type="checkbox" id="isAudit" > <font class="isAuditCls" style="font-size: 13">已审</font>
												<!-- <input id="changeLook" type="button" value="切换浏览机构树"  style="width: 100px;font-size: 12px;display: none" onclick="lookPerson()"/>
												<input id="changeWrite" type="button" value="切换编辑机构树" style="width: 100px;font-size: 12px;" onclick="writePerson()"/> -->
												<odin:hidden property="LWflag" value="1"/>
											</td>
										</tr>
									</table>
										<table style="width: 100%; background-color: #cedff5;border-collapse:collapse;">
										<tr>
											<td width="70"><label style="font-size: 12">机构定位</label></td>
										        <td width="115"><input type="text" id="nextProperty" size="17"
											            class=" x-form-text x-form-field"></td>
											<td style="width: 10px"></td>
										        <td ><odin:button property="selectOrgsBtn"
												        text="下一个" handler="doQueryNext" /></td>
										</tr>
									</table>
								</odin:tabCont>
								<odin:tabCont itemIndex="tab2" className="tab">
									<div id="divtab2" style="overflow: hidden; overflow-x: hidden; position: absolute; height: 440px; width: 270; float: none;">
										 <table  id='pBottom' style="width: 100%; background-color: #cedff5;font-size: 12px;">
											<tr>
												<td align="center"><label><input name="radioC" id="radioC1" type="radio" value="1" />全库查询</label> </td>
												<td align="left"><label><input name="radioC" id="radioC2" type="radio" value="2" />二次查询</label></td>
											</tr>
											<tr>
												<td align="center"><label><input name="radioC" id="radioC3" type="radio" value="3" />追加查询</label></td>
												<td align="left"><label><input name="radioC" id="radioC4" type="radio" value="4" />剔除查询</label></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="姓名/身份证查询" onclick="queryByName()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="按机构查询" onclick="queryWithOrgTree()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="常用条件查询" onclick="groupQuery()"></td>
											</tr>
										</table>
										<br>
										<!-- <br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="自定义查询" onclick="userDefined()"></td>
											</tr>
										</table>
										<br> -->
										<br>
										<table id="gbmccx" style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="干部名册查询" onclick="gbmcQuery()"></td>
											</tr>
										</table>
										<%--<br><br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="自定义查询（新）" onclick="userDefined2()"></td>
											</tr>
										</table> 
										 <br>
						<br>
						<table style="width: 100%;">
							<tr>
								<td style="width: 30px"></td>
								<td><input class="bluebutton bigbutton-customquery" type="button" value="查询结果列表" onclick="queryResult()"></td>
								<td><input class="bluebutton bigbutton-customquery" type="button" value="查询方案" onclick="openconditionwin()"></td>
							</tr>
						</table>
						<br>
						<br>
						<table style="width: 100%;">
							<tr>
							<td style="width: 30px"></td>
								<td><input class="bluebutton bigbutton-customquery" type="button" value="列表追加查询" onclick="listAddQuery()"></td>
								<td><input class="bluebutton bigbutton-customquery" type="button" value="视图查询" onclick="groupQueryFunc()"></td>
							</tr>
						</table>
						<br>
						<br>
						
						<br>
						<br>
						<table style="width: 100%;">
							<tr>
							<td style="width: 30px"></td>
								<td ><odin:button text="sql查询（新）"  property="openSql1" handler="openSql1"></odin:button></td>
							</tr>
						</table>
						--%>
						</div>  
								</odin:tabCont>
								<odin:tabCont itemIndex="tab3" className="tab">
									<div id="cuslView" style="width: 100%; display: none">
										<table id="tableTab3" style="height: 450">
											<tr><td>
												<odin:hidden property="orderqueryhidden" />
												<input type="checkbox" id="orderquery"
											onclick="orderQuery()"> <font style="font-size: 13">按职务层次排序</font></td>
											</tr>

											<tr>
												<td>
													<table style="width: 265; background-color: #cedff5">
														<tr align="center">
															<td><odin:button text="增加" property="mscript"></odin:button>
															</td>
															<td><odin:button text="删除" property="mscriptDel"></odin:button>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
								</odin:tabCont>
							</odin:tab></td>



					</tr>
				</table>
			</td>
			<td style="height: 100%; width: 7px;">
			<div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2 url(image/right.png) no-repeat center center;"></div>
			</td>
			<td>
				<div id="btnToolBarDiv"></div>
						<div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;" >
						<table style="width: 100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
						<tr>
								<td><odin:editgrid2 property="peopleInfoGrid" enableColumnHide="false" load="refreshPerson"
									autoFill="false" locked="true" width="100%" height="496" bbarId="pageToolBar" hasRightMenu="true" remoteSort="true" rightMenuId="updateM" cellmousedown="rowClickPeople"
									pageSize="20" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck" />
										<odin:gridDataCol name="a0000" />

										<%

							int i = 0;
							for(Object[] o : gridDataCollist){
								String name = o[0].toString();
								i++;
								if(i==size ){
									%>
										<odin:gridDataCol name="<%=name %>" isLast="true" />
										<%
								}else{
									%>
										<odin:gridDataCol name="<%=name %>" />
										<%
								}

							}
						%>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel2>
										<odin:gridEditColumn2 locked="true" header="selectall" width="40"
											editor="checkbox" dataIndex="personcheck" edited="true"
											hideable="false" gridName="persongrid"
											checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" sortable="false"/>
										<odin:gridRowNumColumn2 header="&nbsp;序<br/>&nbsp;号" width="30"></odin:gridRowNumColumn2>
										<%
							int i = 0;
							for(Object[] o : CommSQL.A01_CONFIG_LIST.get(userid)){
								String name = o[0].toString();
								String editor = o[5].toString().toLowerCase();
								String header = o[2].toString();
								String desc = o[6].toString();
								String width = o[3].toString();
								String codeType = o[4]==null?"":o[4].toString();
								String renderer = o[7]==null?"":o[7].toString();
								String align = o[9].toString();
								String sortable = o[10]==null?"true":o[10].toString();
								boolean locked =false;
								if("a0101".equals(name)){
									locked = true;
								}
								i++;
								if(!"a0000".equals(name)){
									if(i==size ){
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											 align="<%=align %>" editor="<%=editor %>" edited="false" codeType="<%=codeType %>"
											renderer="<%=renderer %>"   isLast="true" sortable="<%=sortable %>"/>

										<%

									}else{
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											align="<%=align %>" editor="<%=editor %>" locked="<%=locked %>" edited="false" codeType="<%=codeType %>"
											renderer="<%=renderer %>" sortable="<%=sortable %>"/>
										<%
									}
								}

							}
						%>
									</odin:gridColumnModel2>
									<odin:gridJsonData>
	{
        data:[]
    }
</odin:gridJsonData>
								</odin:editgrid2>
									<table id="picdata" style=" width: 100%;margin-top:45px;display: none;text-align:center;" cellspacing="0px" cellspacing="1">
									<tr valign='top' style='height: 50%'>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai0" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac0" type="checkbox" onclick="checkThis()"/><span id="sp0" onclick="clickThis(id)"><font id="dataf0" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume0" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai1" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac1" type="checkbox" onclick="checkThis()"/><span id="sp1" onclick="clickThis(id)"><font id="dataf1" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume1" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai2" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac2" type="checkbox" onclick="checkThis()"/><span id="sp2" onclick="clickThis(id)"><font id="dataf2" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume2" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr valign='top' style='height: 50%'>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai3" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac3" type="checkbox" onclick="checkThis()"/><span id="sp3" onclick="clickThis(id)"><font id="dataf3" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume3" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai4" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac4" type="checkbox" onclick="checkThis()"/><span id="sp4" onclick="clickThis(id)"><font id="dataf4" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume4" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr valign='top'>
													<td style="height: 170px;width: 136px;"><img id="datai5" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
													<td style="vertical-align:top;" align='left'>
														<input id="datac5" type="checkbox" onclick="checkThis()"/><span id="sp5" onclick="clickThis(id)"><font id="dataf5" style="cursor: pointer;font-size:14px;"></font></span>
														<br>
														<br>
														<font id="resume5" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr style="background-color: #cedff5" height="20px" align='left'>
										<td colspan="3">
											<table>
												<tr>
													<td><odin:button text="首页" property="fristpage2" handler="fristpage2"/></td>
													<td><odin:button text="上一页" property="lastpage2" handler="lastpage2"/></td>
													<td><odin:textEdit property="page3" label="当前页:" width="50" readonly="true" value="1"/></td>
													<td align="left" width="60px"><font style="font-size: 12">共</font><font id="page4" style="font-size: 12"></font><font style="font-size: 12">页</font></td>
													<td><odin:button text="下一页" property="nextpage2" handler="nextpage2"/><td>
													<td><odin:button text="尾页" property="endpage2" handler="endpage2"/><td>
														<!-- <td align="left" width="120px"><input type="checkbox" id="checkAll2" onclick="selectAllPeople()"><font style="font-size: 13">全部人员</font></td> -->
													<td align="right" width="600px"><font style="font-size: 12">共</font><font id="l2" style="font-size: 12"></font><font style="font-size: 12">条记录</font></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>



								<table id="pictable" style="width: 100%;display: none" cellspacing="0px" cellspacing="1">
									<tr>
										<td align="center" style="height: 50%;width: 20%"><img id="i0" height="170" width="136" alt="" src="" ondblclick="querypep(name)" ><br><br><input id="c0" type="checkbox" onclick="checkThis()"/><span id="p0" onclick="clickThis(id)"><font id="f0" style="cursor: pointer;"></font><br><font id="g0" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i1" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c1" type="checkbox" onclick="checkThis()"/><span id="p1" onclick="clickThis(id)"><font id="f1" style="cursor: pointer;"></font><br><font id="g1" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i2" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c2" type="checkbox" onclick="checkThis()"/><span id="p2" onclick="clickThis(id)"><font id="f2" style="cursor: pointer;"></font><br><font id="g2" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i3" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c3" type="checkbox" onclick="checkThis()"/><span id="p3" onclick="clickThis(id)"><font id="f3" style="cursor: pointer;"></font><br><font id="g3" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i4" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c4" type="checkbox" onclick="checkThis()"/><span id="p4" onclick="clickThis(id)"><font id="f4" style="cursor: pointer;"></font><br><font id="g4" style="cursor: pointer;"></font></span></td>
									</tr>
									<tr>
										<td align="center" style="height: 50%;width: 20%"><img id="i5" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c5" type="checkbox" onclick="checkThis()"/><span id="p5" onclick="clickThis(id)"><font id="f5" style="cursor: pointer;"></font><br><font id="g5" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i6" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c6" type="checkbox" onclick="checkThis()"/><span id="p6" onclick="clickThis(id)"><font id="f6" style="cursor: pointer;"></font><br><font id="g6" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i7" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c7" type="checkbox" onclick="checkThis()"/><span id="p7" onclick="clickThis(id)"><font id="f7" style="cursor: pointer;"></font><br><font id="g7" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i8" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c8" type="checkbox" onclick="checkThis()"/><span id="p8" onclick="clickThis(id)"><font id="f8" style="cursor: pointer;"></font><br><font id="g8" style="cursor: pointer;"></font></span></td>
										<td align="center" style="height: 50%;width: 20%"><img id="i9" height="170" width="136" alt="" src="" ondblclick="querypep(name)"><br><br><input id="c9" type="checkbox" onclick="checkThis()"/><span id="p9" onclick="clickThis(id)"><font id="f9" style="cursor: pointer;"></font><br><font id="g9" style="cursor: pointer;"></font></span></td>
									</tr>
									<tr style="background-color: #cedff5" height="20px">
										<td colspan="5">
											<table>
												<tr>
													<td><odin:button text="首页" property="fristpage" handler="fristpage"/></td>
													<td><odin:button text="上一页" property="lastpage" handler="lastpage"/></td>
													<td><odin:textEdit property="page" label="当前页:" width="50" readonly="true" value="1"/></td>
													<td align="left" width="60px"><font style="font-size: 12">共</font><font id="page2" style="font-size: 12"></font><font style="font-size: 12">页</font></td>
													<td><odin:button text="下一页" property="nextpage" handler="nextpage"/><td>
													<td><odin:button text="尾页" property="endpage" handler="endpage"/><td>
														<!-- <td align="left" width="120px"><input type="checkbox" id="checkAll3" onclick="selectAllPeople()"><font style="font-size: 13">全部人员</font></td> -->
													<td align="right" width="600px"><font style="font-size: 12">共</font><font id="l" style="font-size: 12"></font><font style="font-size: 12">条记录</font></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="20" style="background-color: #cedff5;display: none;" id="btd">
								<table style="width: 100%">
									<tr>
										<odin:hidden property="downfile" />
										<td><span><font style="font-size: 12px;">审核信息</font></span></td>
										<odin:textEdit property="shinfo" width="1025" readonly="true"></odin:textEdit>
										<td><img  src="<%=request.getContextPath()%>/images/guard_icon1_orange.png" onclick="searchWaring()"/><div id="warning_NUM"  class="divright">3</div></td>
									</tr>


									<tr style="display: none;">
										<odin:hidden property="downfile" />
										<td><span><font style="font-size: 12px;">实配信息</font></span></td>
										<!-- <td><input  id="sp" maxlength="36" width="1000"/></td> -->
										<%-- <%
						HBSession hs = HBUtil.getHBSession();
						CommQuery query = new CommQuery();
						List<HashMap<String,Object>> li = query.getListBySQL(sql);
						HashMap<String,Object> map = new HashMap<String,Object>();
						map = li.get(0);
						String sql = "select validity from smt_user where loginname='"+username+"'";

						%> --%>
						<odin:textEdit property="sp"  width="1025" readonly="true" style="display:none;"></odin:textEdit>
									</tr>

								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<odin:window src="/blank.htm" id="alertWin" width="800" height="500" title="自定义模板窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="peopleTrans" width="450" height="280" title="人员调转" modal="true"></odin:window>
<odin:hidden property="downfile" />
<odin:window src="/blank.htm" id="sameIdCheckWin" width="940" height="500"
	title="人员查重" modal="true"></odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500"
	title="信息校验" modal="true"></odin:window>
<odin:window src="/blank.htm" id="pdfViewWin" width="700" height="500"
	title="任免表预览界面" modal="true" />
<odin:window src="/blank.htm" id="UpdateWin" width="320" height="215"
	title="参数修改窗口" />
<odin:window src="/blank.htm" id="betchModifyWin" width="1200"
	height="350" title="批量修改" modal="true" />
<odin:window src="/blank.htm" id="jdinformation" width="1200"
	height="350" title="监督信息" modal="true" />
<odin:window src="/blank.htm" id="persionfile" width="1200"
	height="550" title="干部材料" modal="true" />

<odin:window src="/blank.htm" id="deletePersonWin" width="520"
	height="400" title="人员删除" modal="true" />
<odin:window src="/blank.htm" id="importLrmWin" width="520" height="210"
	title="任免表导入" modal="true" />
<odin:window src="/blank.htm" id="importLrmxWin" width="520"
	height="170" title="LRMX导入" modal="true" />
<odin:window src="/blank.htm" id="expTimeWin" width="450" height="130"
	title="系统提醒" modal="true" />
<odin:window src="/blank.htm" id="batchPrintTimeWin" width="520"
	height="170" title="系统提醒" modal="true" />
<odin:window src="/blank.htm" id="refreshWin" width="520" height="170"
	title="导出进度" modal="true" />
<odin:window src="/blank.htm" id="ExtPadData" title="导出平板压缩包" width="400" height="400"></odin:window>
<odin:toolBar property="btnToolBar" applyTo="extPad">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSaveUpdated"  text="导出" handler="expZip" icon="images/icon/exp.png"  isLast="true" />
</odin:toolBar>
<div id="extPadPanel" style="display: none;width: 100%;height:100%">
	<div id="extPad"></div>
		<table>
			<tr style="width:350">
				<td noWrap="nowrap" style="FONT-SIZE: 12px">选择文件：</td>
  				<tags:JUpload2 property="expPadZipFile" label="选择文件" fileTypeDesc="文件类型"  colspan="2"
 		 		uploadLimit="1" fileSizeLimit="20MB" fileTypeExts="*.xls" labelTdcls="titleTd"/>
			</tr>
			<tr>
				<odin:checkBoxGroup property="padCons" data="['isZGGB', '包含中管干部'],['showGBHX', '包含干部画像']"></odin:checkBoxGroup>
				<hr/>
				<odin:checkBoxGroup property="padCons" data="<%=datalist %>"></odin:checkBoxGroup>
				<hr/>
			</tr>
			<tr>
				<td noWrap="nowrap" style="FONT-SIZE: 12px"></td>
			</tr>
			<tr>
			</tr>
	</table>
	<table>
				<td style="FONT-SIZE:12px">上次选择文件时间:<odin:textEdit property="latestTime" maxlength="14" readonly="true"/>

	</table>
</div>

<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="导出" id="exportZipbtn_Hy" icon="images/icon/exp.png"  isLast="true"/>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar  text="重置"  id="reset" icon="images/sx.gif" isLast="true"/> --%>
</odin:toolBar>
<odin:window src="/blank.htm" id="ExtZipData" title="导出Zip包" width="500" height="180"></odin:window>
<div id="extZipPanel" style="display: none" style="width: 100%;">
	<div id="toolDiv"></div>
	<table style="width: 100%">
		<tr align="right">
			<td>
				<table>
					<tr >
							<odin:textEdit property="linkpsn" colspan="1" width="180" label="联系人"/>
							<odin:textEdit property="linktel" width="180" colspan="1" label="联系电话"/>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<%-- <div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><odin:button text="清除条件" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td><odin:button text="保存条件" property="saveCon1" handler="saveConF"></odin:button>
							</td>
							<td align="center"><odin:button text="开始查询" property="mQuery" handler="dosearch"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div> --%>

<odin:panel property="padpanel" width="500" height="350" title="简单Panel"
	collapsible="true" contentEl="extPadPanel" />
<script>
function showExtPadData(){
	var noticeWindow = odin.ext.getCmp('ExtPadData');
	noticeWindow.show(noticeWindow);
	var obj = document.getElementById('extPadPanel');
	noticeWindow.body.dom.innerHTML='';
	noticeWindow.body.appendChild(obj);
	obj.style.display="block";
}
function showExtZipData(){
	var noticeWindow = odin.ext.getCmp('ExtZipData');
		noticeWindow.show(noticeWindow);
		var obj = document.getElementById('extZipPanel');
		noticeWindow.body.dom.innerHTML='';
		noticeWindow.body.appendChild(obj);
		obj.style.display="block";
	}
</script>
<%-- <odin:window src="/" id="win2" width="560" height="500" title="人员组列表"
	modal="true"></odin:window> --%>

<odin:window src="/" id="win3" width="500" height="290" title="保存列表"
	modal="true"></odin:window>
<odin:window src="/blank.htm" id="ExcelImp" width="400" height="430" title="Excel套表导入" modal="true"></odin:window>

<odin:window src="/blank.htm" id="importLrmWins" width="450" height="210"
	title="批量任免表导入" modal="true" />
<script>
var ctxPath = '<%=ctxPath%>';
function canSaveSort(){
	var checkedgroupid = document.getElementById("checkedgroupid").value;
	if(""==checkedgroupid||checkedgroupid==null||"undefined"==checkedgroupid){
		Ext.Msg.alert('系统提示','请点击左侧的机构树！');
		return false;
	}
	if(checkedgroupid=='X001'||checkedgroupid=='X0010'||checkedgroupid=='X002'||checkedgroupid=='X003'){
		Ext.Msg.alert('系统提示','机构树中 "其他人员"、"职务为空的现职人员" 不能排序！');
		return false;
	}
	var isContain = document.getElementById("isContainHidden").value;
	if("1"==isContain){
		Ext.Msg.alert('系统提示','无法对包含下级所查询出的人员进行排序（请先去掉包含下级，仅查询单一机构人员）');
		return false;
	}
}
//add zepeng 20180418 元素较多，会出现渲染两次的闪动，先隐藏Org ext.onready的时候再显示
Ext.onReady(function() {
	//alert(document.getElementById("personq").value)
	var ua = navigator.userAgent.toLowerCase()
	$("#groupTreeContent").show();
	if(!Ext.isIE){
		if(/chrome/.test(ua)){
			$("#groupTreeContent").css("padding-top","13px")
		}else{
			$("#groupTreeContent").css("padding-top","1px")
		}
		
	}
	document.getElementById('personq').value = '1';
	document.getElementById('personq_combotree').value = '现职人员';
	$('#isContain').val('1');
	/* document.getElementById('changeLook').style.display = "none";
	document.getElementById('changeWrite').style.display = "";
	odin.ext.getCmp('loadadd').hide();
	odin.ext.getCmp('rmbUpdate').hide();
	odin.ext.getCmp('deletePersonBtn').hide();
	odin.ext.getCmp('betchModifyBtn').hide();
	odin.ext.getCmp('getA17').hide();
	odin.ext.getCmp('PersionFileBtn').hide();
	odin.ext.getCmp('imprmboldBtn').hide(); */
	//odin.ext.getCmp('ImpA15').hide();

	//document.getElementById('lookOrWrite').value = "look";
	document.getElementById('lookOrWrite').value = "write";
});

//pps显示的控制
Ext.onReady(function () {
	var pps_isuseful = <%="'"+pps_isuseful+"'" %>;

	if (pps_isuseful != null && pps_isuseful == ("ON")) {
		odin.ext.getCmp('ppsData').show();
	} else {
		odin.ext.getCmp('ppsData').hide();
	}
});
//初始化打印机
Ext.onReady(function () {
	try {
		var oShell = new ActiveXObject("WScript.Shell");
	} catch (e) {
		$helper.alertActiveX();
		return;
	}
	var sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device';
	var sDefault = oShell.RegRead(sRegVal);
	var printer = sDefault.split(",");
	radow.doEvent("printSetInit", printer[0]);
});

Ext.onReady(function(){
	var pgrid = Ext.getCmp('peopleInfoGrid');



	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[

						new Ext.menu.Separator({cls:'xtb-sep'}),
						/* new Ext.Button({
							id:'Btn',
						    text:'每页'
						}),
						new Ext.form.TextField({fieldLabel:'姓名', id:'pageSize', width:35}), */
						//new Ext.Spacer({width:100}),
						/* new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'上移',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'下移',
						    handler:DownBtn
						}), */
						new Ext.Button({
							icon : 'images/keyedit.gif',
							id:'setPageSize',
						    text:'设置每页条数',
						    handler:setPageSize1
						}),

						new Ext.Button({
							icon : 'images/keyedit.gif',
							id:'setFields',
						    text:'设置显示字段',
						    handler:setFields
						}),
						new Ext.Button({
							icon : 'images/icon/table.gif',
							id:'getAll',
						    text:'导出Excel',
						    handler:expExcelFromGrid
						}),
						new Ext.Button({
							icon : 'images/icon/table.gif',
							id:'getAll2',
						    text:'导出Excel新版',
						    handler:expExcelFromGrid2
						}),
						/* new Ext.Button({
							icon : 'image/u53.png',
							id:'sortHand',
						    text:'手动排序',
						    handler:sortHand
						}), */
						/* new Ext.Button({
							icon : 'image/u53.png',
							id:'saveSortBtn',
						    text:'临时排序',
						    handler:sort
						}), */
						new Ext.Button({
							icon : 'image/u53.png',
							id:'setA0281',hidden:true,
						    text:'取消已免职务输出'
						}),
						new Ext.Button({
							icon : 'image/u53.png',
							id:'sortWin',
						    text:'集体内排序',
						    /* hidden:true, */
						    handler:openSortWin
						}),
						new Ext.Button({
							icon : 'image/icon021a2.gif',
							id:'addpublicity',
						    text:'加到公示名单',
						    /* hidden:true, */
						    handler:addpublicity
						}),
						new Ext.Button({
							icon : 'image/icon021a2.gif',
							id:'addHistorylist',
						    text:'加到人员名单',
						    /* hidden:true, */
						    handler:addHistorylist
						})
						/* ,
						new Ext.Button({
							icon : 'images/icon/table.gif',
							id:'tableList',
						    text:'固定选择列表',
						
						    handler:openTableList
						}) */
						]);

	/* pgrid.store.on('load',function(){//翻页全选（伪全选，仅用作页面展示）
		var fieldName = "personcheck";
		var checkAll = document.getElementById('checkAll');
		var value = checkAll.checked;
		var store = pgrid.store;
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
		}
	}); */


});

function UpBtn(){
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('peopleInfoGrid');
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		//alert('请选中需要排序的人员!')
		Ext.Msg.alert("系统提示","请选中需要排序的人员!");
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		//alert('该人员已经排在最顶上!')
		Ext.Msg.alert("系统提示","该人员已经排在最顶上!");
		return;
	}
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行
	grid.getView().refresh();
}
function DownBtn(){
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('peopleInfoGrid');
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		//alert('请选中需要排序的人员!')
		Ext.Msg.alert("系统提示","请选中需要排序的人员!");
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		//alert('该人员已经排在最底上!')
		Ext.Msg.alert("系统提示","该人员已经排在最底上!");
		return;
	}
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行
	grid.view.refresh();
}


var searchTag="";
var tagIdx=-1;
function getShowFour(){
	odin.ext.getCmp('tab').activate('tab2');
	odin.ext.getCmp('tab').activate('tab1');
}
function getShowTab3(){
	//odin.ext.getCmp('tab').activate('tab3');
}
function resetCM(cmConfig,dmConfig){
	var cfg = cmConfig.split("{split}");
	var grid = Ext.getCmp('peopleInfoGrid');
	//var cm = grid.getColumnModel();
	//alert(cmConfig);
	//cm.setConfig(eval(cfg[0]));
	//var lastOptions = grid.getStore().lastOptions;
	var reader =  new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'id'}, eval(cfg[1]));
	var gridData = {data:[]};

	var ds = new Ext.data.Store({reader: reader,baseParams: {cueGridId:'peopleInfoGrid'},data: gridData,proxy:new Ext.data.MemoryProxy(gridData),remoteSort:true});
	var colModel = new Ext.ux.grid.LockingColumnModel(eval(cfg[0]));
    grid.reconfigure(ds, colModel);//重新生成列模型和数据对象。
    //ds.load(lastOptions);

    var bbar = grid.getBottomToolbar();
    bbar.bind(ds);//重新绑定分页栏数据对象

    var sql = document.getElementById("sql").value;
    var qvid = document.getElementById("qvid").value;
    if (sql == "" && qvid == "") {
        return;
    }

    radow.doEvent("peopleInfoGrid.dogridquery");

    var dateshow = document.getElementById("pictable").style.display;
    var picture = document.getElementById("picdata").style.display;
    if ('none' != dateshow) {
        datashow();
    }
	if('none' != picture){
		picshow();
	}


	//alert(bbar.cursor);
	/*bbar.doLoad(bbar.cursor);
	ds.reload(); */


}
function getCheckList(gridId,fieldName,obj){


	//x-grid3-check-col
	if("x-grid3-check-col"==obj.className){
		var objs = Ext.query(".x-grid3-check-col-on");
		for(var i=0;i<objs.length;i++){
			objs[i].className = "x-grid3-check-col";
		}
	}else{
		var objs = Ext.query(".x-grid3-check-col");
		for(var i=0;i<objs.length;i++){
			objs[i].className = "x-grid3-check-col-on";
		}
	}

	//var grid = Ext.getCmp('peopleInfoGrid');
	//return;
	radow.doEvent('getCheckList');
}
function getCheckList2(num){



	var grid = Ext.getCmp('peopleInfoGrid');
	var listString = document.getElementById("checkList").value;
	var sign = 0;
	if("" == listString){
		sign = 1;//说明是首次修改
	}

	var a0000 = "";
	var personcheck = grid.store.getAt(num).get('personcheck');
	//先对checkBox进行操作,如果是"true",改为"false";如果是"false",改为"true"
	//changeCheckBox(personcheck,num);

	var personid = grid.store.getAt(num).get('a0000');
	var peopleName = grid.store.getAt(num).get('a0101');
	a0000 = personid;
	//true 说明将要勾选这个人员
	if(personcheck){
		listString=listString+"@|"+personid+"|";
	}
	if(!personcheck){
		if(listString.length == 38){
			listString = listString.replace("|"+personid+"|","");
		}else{
			/* 确保清除取消勾选的a0000 */
			listString = listString.replace("@|"+personid+"|","");
			listString = listString.replace("|"+personid+"|@","");
		}
	}
	if(sign == 1){
		listString=listString.substring(listString.indexOf("@")+1,listString.length);
	}
	document.getElementById("checkList").value = listString;

	document.getElementById("a0000s").value = a0000;
	//changeTabId(peopleName);
}
function getCheckList3(num){
	var grid = Ext.getCmp('peopleInfoGrid');
	var listString = document.getElementById("checkList").value;
	var sign = 0;
	if("" == listString){
		sign = 1;//说明是首次修改
	}

	var a0000 = "";
	var personcheck = grid.store.getAt(num).get('personcheck');
	//先对checkBox进行操作,如果是"true",改为"false";如果是"false",改为"true"
	changeCheckBox(personcheck,num);

	var personid = grid.store.getAt(num).get('a0000');
	var peopleName = grid.store.getAt(num).get('a0101');
	a0000 = personid;
	//false 说明将要勾选这个人员
	if(!personcheck){
		listString=listString+"@|"+personid+"|";
	}
	if(personcheck){
		if(listString.length == 38){
			listString = listString.replace("|"+personid+"|","");
		}else{
			/* 确保清除取消勾选的a0000 */
			listString = listString.replace("@|"+personid+"|","");
			listString = listString.replace("|"+personid+"|@","");
		}
	}
	if(sign == 1){
		listString=listString.substring(listString.indexOf("@")+1,listString.length);
	}
	document.getElementById("checkList").value = listString;

	document.getElementById("a0000s").value = a0000;
	//changeTabId(peopleName);
}
function changeTabId(peopleName){
	//将  peopleName 填入当前人员的span中
	document.getElementById("peopleName").innerHTML = peopleName;

	var tabID = document.getElementById("showTabID").value;
		if("" == tabID || tabID){
			if(""==tabID || "showTab1"==tabID){
				radow.doEvent("zwxx.dogridquery");
			}
			if("showTab2"==tabID){
				radow.doEvent("zhuanyexx.dogridquery");
			}
			if("showTab3"==tabID){
				radow.doEvent("xuelixx.dogridquery");
			}
			if("showTab4"==tabID){
				radow.doEvent("peixunxx.dogridquery");
			}
			if("showTab5"==tabID){
				radow.doEvent("jiangchengxx.dogridquery");
			}
			if("showTab6"==tabID){
				radow.doEvent("kaohexx.dogridquery");
			}
			if("showTab7"==tabID){
				radow.doEvent("jinruxx.dogridquery");
			}
			if("showTab8"==tabID){
				radow.doEvent("tuichuxx.dogridquery");
			}
			if("showTab9"==tabID){
				radow.doEvent("jiatingxx.dogridquery");
			}
			if("showTab12"==tabID){
				radow.doEvent("nirenxx.dogridquery");
			}
			if("showTab20"==tabID){
				radow.doEvent("beizhuxx.dogridquery");
			}
		}
}
var chishu = 0;
var dosearch = function(store){
	store.un('load',dosearch);
	chishu++;


    sreach(1, 1, 1, true)
};

//定位
function sreach(){

	var name = document.getElementById("seachName").value;

	var grid = odin.ext.getCmp('peopleInfoGrid');
	var store = grid.store;
	store.un('load',dosearch);

	var selection=grid.getSelectionModel();
  //  do{
		for(var i =0;i<store.getCount();i++){
	         var record=store.getAt(i);
	         //var data=record.get('a0184');
	        if(isNaN(name)==false){
	        	 var data=record.get('a0184');
	         }else{
	        	 var data=record.get('a0101');
	         }

	         if(i>tagIdx){
		         if(data.indexOf(name)>-1||ConvertPinyin(data).indexOf(name.toUpperCase())>-1){
		        	 selection.selectRange(i,i);//选择范围间的行，一条数据
		        //	 searchTag=record.get('a0000');
		        	 tagIdx=i;
		        	 chishu = 0;
		        	 grid.getView().scroller.dom.scrollTop = i*27;
		        	 return;
		         }
	         }
	         if(i==(store.getCount()-1)){
	        	 tagIdx=-1;
	         }

		}
//	}while(tagIdx!=-1&&searchTag!='');
//	searchTag = '';
	if(chishu>=5){
		chishu = 0;
		return;
	}
	next();

	//radow.doEvent('peopleInfoGrid.dogridquery');
	//grid.view.refresh();
}

function next(){
	pageingToolbar = (Ext.getCmp('peopleInfoGrid').getBottomToolbar());

	var s = Ext.getCmp("peopleInfoGrid").store;
	var start = 0;
	if(s.lastOptions && s.lastOptions.params){		//不是第一页有值

		start = s.lastOptions.params.start;
	}

	var max = 1;
	if(start != 0){
		var zong = s.totalLength;
		var limit = pageingToolbar.pageSize;
		max = zong - limit;
	}

	//如果是最后一页则回到第一页
	if(start >= max){
		chishu = 0;
		pageingToolbar.store.un('load',dosearch);
		pageingToolbar.moveFirst();
	}else{
		pageingToolbar.store.on('load',dosearch);
		pageingToolbar.moveNext();
	}


	//setTimeout("sreach();",500)
}




function delEmpRow(a,b,c){

	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	}

	var grid = odin.ext.getCmp('peopleInfoGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var a0000s = "";

	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}

	/* for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);
		//alert(selected.data.a0000);
		a0000s=a0000s+selected.data.a0000+",";
	}  */
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.personcheck == true){
			store.remove(rowData);
			i--;
			a0000s=a0000s+rowData.data.a0000+",";
			store.totalLength--
		}
	}


	if(a0000s == null || a0000s == ""){
		$h.alert('系统提示：','请先选择记录！',null,150);
	}

	grid.view.refresh();
	document.getElementById("removePersonIDs").value=a0000s;
	radow.doEvent("removePersons");
	var bbar = grid.getBottomToolbar();
	var count = bbar.store.getCount();
	var msg = count == 0?
		String.format(
        		bbar.displayMsg,
        		bbar.cursor, bbar.cursor+count, bbar.store.getTotalCount()
        ):
        String.format(
        		bbar.displayMsg,
        		bbar.cursor+1, bbar.cursor+count, bbar.store.getTotalCount()
        );
	bbar.displayItem.setText(msg);
	//bbar.updateInfo();
	//alert(bbar.store.getCount());
}

function openPdfPage( winId, title, ParamPdfPath){
	var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.PdfView&pdfFilePath="+ParamPdfPath;

   	odin.openWindow(winId,title,url,700,500);


}
function openEditer(value, params, record,rowIndex,colIndex,ds){

	if(value){
		return "<img src='"+contextPath+"/image/u117.png' title='' style='cursor:pointer' onclick=\"printView('"+value+"');\">";
	}else{
		return null;
	}
}

function printView(value){
	var flag = false;
	if (confirm("任免表打印中是否包含拟任免信息？"))  {
		flag = true;
	}  else  {
		flag = false;
	}

	radow.doEvent('printView',value+','+flag);
}
//人员新增修改窗口窗口
var personTabsId=[];
/*
function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=parent.tabs.getItem(aid);

      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  //alert(Ext.urlEncode({'asd':'三大'}));
    	src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，

	    },
	    closable:true
        }).show();

      }
    }
    */
function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=parent.tabs.getItem(aid);
      if (tab && !forced){
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  //alert(Ext.urlEncode({'asd':'三大'}));
    	src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，

	    },
	    closable:true
        }).show();

      }
    }


	function downloadword(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
		window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
		ShowCellCover("","温馨提示","导出成功！");
		setTimeout(cc,3000);
	}
	function cc(){

	}
	function reload(){
		odin.reset();
	}
var selAll = document.getElementById("checkAll");
	function selectAll()
	{
	  var obj = document.getElementsByName("personcheck");
	  if(document.getElementById("checkAll").checked == false)
	  {
	  for(var i=0; i<obj.length; i++)
	  {
	    obj[i].checked=false;
	  }
	  }else
	  {
	  for(var i=0; i<obj.length; i++)
	  {
	    obj[i].checked=true;
	  }
	  }

	}
function imgVerifyWin(){
	addTab('照片检测','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false);
}
function openVerifyWin(){
	addTab('重名检测','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.RepeatQuery',false,false);
}
function reloadGrid(){
	Ext.getCmp('peopleInfoGrid').store.reload();
}
function doSaveList(){
	radow.doEvent('doSaveList');
}

/* Excel套表导入 */
function openExcelWin(){
	radow.util.openWindow('ExcelImp','pages.customquery.ExcelImp');
}
var objHeight;

/* 显示子集列表 */
function showSons(){
	var dd = document.getElementById("formulaResult_view");

	//alert(dd.style.display)
	if(dd.style.display=="none")
	{
		//loadResult(bz);
		odin.ext.getCmp('peopleInfoGrid').setHeight(((objHeight)/3)*2);
		var wid = odin.ext.getCmp('peopleInfoGrid').getWidth();

		dd.style.display = "block";

		document.getElementById("formulaResult_view").style.height = (objHeight)/3-2;
		document.getElementById("formulaResult_view").style.width = wid + "px";
		odin.ext.getCmp('showTab').setHeight((objHeight)/3-2);

		document.getElementById("div1").style.display = 'block';

		//odin.ext.getCmp('shows').hide();
		//odin.ext.getCmp('hids').show();

	}
	else
	{
		dd.style.display = "none";
		odin.ext.getCmp('peopleInfoGrid').setHeight(objHeight);

		//odin.ext.getCmp('shows').show();
		//odin.ext.getCmp('hids').hide();

	}
}

/* grid双击事件 */
function rowDoubleC(){
	/* var tabID = document.getElementById("showTabID").value;
	var a0000s = document.getElementById('a0000s').value;
	if(tabID=='showTab1' || tabID==""){
		$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',950,660,a0000s,ctxPath)
	}
	if(tabID=='showTab2'){
		$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',950,660,a0000s,ctxPath);
	}
	if(tabID=='showTab3'){
		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,a0000s,ctxPath)
	}
	if(tabID=='showTab5'){
		$h.openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','奖惩情况',1050,450,a0000s,ctxPath)
	}
	if(tabID=='showTab6'){
		$h.openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','年度考核情况',800,490,a0000s,ctxPath)
	}else{
		return;
	} */
}

function showTabChange(tabObj,item){
	var tabID = item.getId();
	document.getElementById("showTabID").value = tabID;
	//alert(document.getElementById("showTabID").value);

	/* for(var i=1;i<=20;i++){
		document.getElementById("div"+i).style.display = 'none';
	} */
	if(tabID=='showTab1'){
		document.getElementById("div1").style.display = 'block';
		radow.doEvent('zwxx.dogridquery');
	}
	if(tabID=='showTab2'){
		document.getElementById("div2").style.display = 'block';
		radow.doEvent('zhuanyexx.dogridquery');
	}
	if(tabID=='showTab3'){
		document.getElementById("div3").style.display = 'block';
		radow.doEvent('xuelixx.dogridquery');
	}
	if(tabID=='showTab4'){
		document.getElementById("div4").style.display = 'block';
		radow.doEvent('peixunxx.dogridquery');
	}
	if(tabID=='showTab5'){
		document.getElementById("div5").style.display = 'block';
		radow.doEvent('jiangchengxx.dogridquery');
	}
	if(tabID=='showTab6'){
		document.getElementById("div6").style.display = 'block';
		radow.doEvent('kaohexx.dogridquery');
	}
	if(tabID=='showTab7'){
		document.getElementById("div7").style.display = 'block';
		radow.doEvent('jinruxx.dogridquery');
	}
	if(tabID=='showTab8'){
		document.getElementById("div8").style.display = 'block';
		radow.doEvent('tuichuxx.dogridquery');
	}
	if(tabID=='showTab9'){
		document.getElementById("div9").style.display = 'block';
		radow.doEvent('jiatingxx.dogridquery');
	}
	if(tabID=='showTab12'){
		document.getElementById("div12").style.display = 'block';
		radow.doEvent('nirenxx.dogridquery');
	}
	if(tabID=='showTab20'){
		document.getElementById("div20").style.display = 'block';
		radow.doEvent('beizhuxx.dogridquery');
	}
}

function resizeframe(){
	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
	var offs = 5;
	var resizeobj =Ext.getCmp('tab');
	var viewSize = Ext.getBody().getViewSize();
	resizeobj.setHeight(viewSize.height-24-offs);//34 - 29

	var tableTab1 = document.getElementById("tableTab1");
	tableTab1.style.height = viewSize.height-76-offs+"px";//87 82

	/* var divtab2 = document.getElementById("divtab2");
	divtab2.style.height = viewSize.height-152-offs+"px";// 157 152 */

	//var tableTab3 = document.getElementById("tableTab3");
	//tableTab3.style.height = viewSize.height-110+"px";

	var peopleInfoGrid =Ext.getCmp('peopleInfoGrid');
	peopleInfoGrid.setHeight(viewSize.height-25-offs);//56 52
	
	
	Ext.getCmp('group').setHeight(viewSize.height-105-offs)

	/* 设置小资料与照片下的高度 */
	var picdata = document.getElementById("picdata");
	picdata.style.height = viewSize.height-115 + "px";

	var pictable = document.getElementById("pictable");
	pictable.style.height = viewSize.height-70 + "px";

	var gridobj = document.getElementById('forView_peopleInfoGrid');
	
	var grid_pos = $h.pos(gridobj);
	peopleInfoGrid.setWidth(viewSize.width-grid_pos.left);
	
	objHeight = peopleInfoGrid.getHeight();
}

function searchPersonByName(){
	var name = document.getElementById('seachName').value;
	//alert(1111)
	if(!name){

		return;
	}
	//去除字符串内两头的空格
	name = name.replace(/^\s*|\s*$/g,"");
	if(name==''||name=='输入姓名')
		{
		$h.alert('系统提示','请先输入姓名再查询');
		return;
		}
	radow.doEvent('queryByName',name);
}

/* 自定义查询 */
function userDefined(a,b,c,cid){
	radow.doEvent("initListAddGroupFlag");
	var subWinIdBussessId2 = "";
	if(cid){
		subWinIdBussessId2 = cid;
	}
	$h.openWin('win1','pages.customquery.QueryConditionEdit','自定义查询',1250,600,subWinIdBussessId2,'<%=ctxPath%>');
}


function openImpA15Win(){
	$h.openWin('impA15','pages.publicServantManage.ImpA15','年度考核导入 ',660,680,"",ctxPath);
}


/* 自定义查询  青 岛 */
function userDefined2(a,b,c,cid){
	var checked=document.getElementById('radioC1').checked;
	if(checked==false){
		alert('请选择全库查询!');
		return;
	}
	var subWinIdBussessId2 = "";
	if(cid){
		subWinIdBussessId2 = cid;
	}
	//新的自定义查询
	//$h.openPageModeWin('win1','pages.cadremgn.infmtionquery.UserDefinedQuery','自定义查询',1000,604,subWinIdBussessId2,'<%=ctxPath%>');
	$h.openPageModeWin('win2','pages.xbrm.ldbj.TPFN','自定义查询',1000,604,subWinIdBussessId2,'<%=ctxPath%>');
}
/* 人员查询方案tab2 */
function queryProject(){
	alert("正在努力开发中....");
}

/* 查询结果列表 */
function queryResult(){
	radow.doEvent("initListAddGroupFlag");
	radow.doEvent("loadList.onclick");
}
/* 按姓名查询 */
function queryByName(){
	radow.doEvent("initListAddGroupFlag");
	//获取现职或非现职类型
	var  a =document.getElementById('personq').value;
	var  LWflag=document.getElementById("LWflag").value;

	//alert(a);
	$h.openWin('findById','pages.customquery.CustomQueryByName&PersonType='+a,'按姓名/身份证查询 ',1020,520,null,contextPath,null,{maximizable:false,resizable:false,LWflag:LWflag});
	//radow.util.openWindow('findById','pages.customquery.CustomQueryByName');
}


/* 组合查询   青 岛 */
function groupQueryFunc(){
		$h.openPageModeWin('GroupQuery','pages.cadremgn.infmtionquery.GroupQuery','组合查询',920,610,'',contextPath,null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}
/* sql查询   青 岛 */
function openSql1(){
	$h.openPageModeWin('SqlSearchWin','pages.cadremgn.infmtionquery.SqlSearch','SQL查询',860,600,'',contextPath,null,{maximizable:false,resizable:false});
}

var groupWin;
/* 组合查询 */
function groupQuery(){
	radow.doEvent("initListAddGroupFlag");
	//var newWin_ = $h.getTopParent().Ext.getCmp('group');
	//if(!newWin_){
		$h.openPageModeWin('group','pages.customquery.Group','组合查询',1200,720,'',contextPath,null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});

		//console.log("------------------------"+groupWin);
	//}else{
    //	newWin_.expand(true);
    //}
    //radow.util.openWindow('group','pages.customquery.Group');
}

/* 匹配查询 */
function matchingQuery() {
    var grid = Ext.getCmp('peopleInfoGrid');
    var store = grid.store;
    var total = store.getCount();//数据行数
    if (total == 0)
        $h.alert("系统提示", "请至少选中一行数据进行匹配");
    var a0000s = '';
    //取复选框选中人员的人员主键
    for (var i = 0; i < total; i++) {
        if (store.getAt(i).data['personcheck'])
            a0000s += store.getAt(i).data['a0000'] + ",";
    }
    //若复选框中没有选中的人员则选取选中的行的人员主键
    if (a0000s == '') {
        var selectRows = grid.getSelectionModel().getSelections();
        var len = selectRows.length;
        if (len == 0)
            $h.alert("系统提示", "请至少选中一行数据进行匹配");
        else
            for (var i = 0; i < len; i++)
                a0000s += selectRows[i].get('a0000') + ",";
    }
    a0000s = a0000s.substr(0, a0000s.length - 1);
    //alert(a0000s);
    $h.openPageModeWin('group', 'pages.customquery.Matching', '匹配查询', 1200, 720, a0000s, contextPath, null, {
        modal: true,
        collapsed: false,
        collapsible: false,
        titleCollapse: false,
        maximized: false
    });
}

function listAddQuery() {
    $h.openPageModeWin('listAddGroup', 'pages.customquery.ListAddGroup', '列表追加查询', 1200, 720, '', contextPath, null,
			{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}
/* 按机构查询  */
function queryWithOrgTree(){
	radow.doEvent("initListAddGroupFlag");
	$h.openWin('queryWithOrgTreeWin','pages.customquery.QueryWithOrgTree','按机构查询',480,700,'',contextPath,null,
			{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}

Ext.onReady(function(){
	document.getElementById("groupTreeContent").parentNode.parentNode.style.overflow='hidden'; //左侧 设置溢出隐藏
	window.onresize=resizeframe;    //浏览器窗口大小改变时候执行resizeframe方法
	

	//odin.ext.getCmp('showTab').enableTabScroll = true;
	//odin.ext.getCmp('hids').hide();

});

Ext.onReady(function(){
		var pagesize = "20";

		pageingToolbar = (Ext.getCmp('peopleInfoGrid').getBottomToolbar());
		pageingToolbar.pageSize = Number(pagesize);
		var s = Ext.getCmp('peopleInfoGrid').store;
		s.baseParams.limit = pagesize;
		//s.baseParams.start = 50;
		if(s.lastOptions && s.lastOptions.params){
			s.lastOptions.params.limit = pagesize;
		}

});

function showtem(value){
	radow.doEvent('showtem',value);
}


//调用花名册的方法
function listofnam(value){
	radow.doEvent('createHuaMc',value);
}
//按姓名查询
function queryByNameAndIDS(list){

	radow.doEvent("initListAddGroupFlag");
	radow.doEvent('queryByNameAndIDS',list);
}
function managerSelect(b){
	var valueMLLB = b.data.key;
	if(valueMLLB=='all' || valueMLLB==""){
		document.getElementById('mllb').value = "";
	}else{
		document.getElementById('mllb').value = valueMLLB;
	}
}

Ext.onReady(function(){
	$('#isContain').attr('checked',true);


	//Ext.getCmp('peopleInfoGrid').colModel.config[0].locked = true;

});

/* 用于修改checkBox */
function changeCheckBox(flag,num){
	var grid = Ext.getCmp('peopleInfoGrid');
	if(flag){
		grid.store.getAt(num).set('personcheck',false);
	}
	if(!flag){
		grid.store.getAt(num).set('personcheck',true);
	}
}
//设置分页条数



//显示子集上  显示人员提示信息
/* Ext.onReady(function(){
	//var showTab__showTab1 = document.getElementById('showTab__showTab1');
	//var mubiao = showTab__showTab1.parentNode;

	//$("#"+mubiao.id).prepend("<div style='font-size: 12px;font-weight: bold;color: rgb(65,106,163);'>&nbsp当前人员：<span id='peopleName' style='display:inline;font-size: 12px;color: rgb(65,106,163);'></span></div>");

});	 */

var querynum = 0;//查询计数
var lastquery = ""; //上一次查询值
function doQueryNext() {
	if(flag_ss){	//如果搜索框已隐藏则不搜索
		return;
	}
	var nextProperty = document.getElementById('nextProperty').value;//获取搜索框的值
	if (nextProperty == "") {
		return;
	}
	var tree = Ext.getCmp("group");	//获取树
	 if(nextProperty != lastquery){	//如果查询变更,计数清空
   	  querynum = 0;
     }
	 if (nextProperty !== "") {
	  Ext.Ajax.request({
	     url : '<%=request.getContextPath()%>/JGQueryServlet?method=JGQuery',
	     params : {
	    	'queryName' : nextProperty,
	    	'queryNum'	: querynum
	     },
	     method : "post",
	     success : function(a, b) {
	      var r = Ext.util.JSON.decode(a.responseText);
	      var data = eval(r);
	      if(data[0] == "1"){
	    	  tree.expandPath(data[1], 'id', this.onExpandPathComplete);
	    	  lastquery = nextProperty;
	    	  querynum++;
	      }else if(querynum == 0){
	    	  Ext.Msg.alert("信息", "没有满足查询条件的机构");
	      }else{
	    	  Ext.Msg.alert("信息", "已经查询到最后一个机构,再次查询将从第一个开始");
	    	  querynum = 0;			//查询到底,提示后计数清空
	      }
	     }
	    });
	 }
}
onExpandPathComplete = function(bSuccess, oLastNode) {
	 if (!bSuccess) {
	  return;
	 }
	 // focus 节点，并选中节点！，以下代码不可少
	 oLastNode.ensureVisible();
	 oLastNode.select();
	 oLastNode.fireEvent('click', oLastNode);
	};

Ext.onReady(function(){
	var pgrid = Ext.getCmp('peopleInfoGrid');
	var dstore = pgrid.getStore();
	dstore.on({
       load:{
           fn:function(){
        	   pGridLoad(dstore.getTotalCount());
        	   if($('#selectall_peopleInfoGrid_personcheck').attr('class')=='x-grid3-check-col-on'){
        		 //重新加载后选项复选框去掉勾。
            	   document.getElementById('selectall_peopleInfoGrid_personcheck').click();
        	   }
           }
       },
       scope:this
   });

});


function pGridLoad(tatalcount){
	if(tatalcount><%=CommSQL.MAXROW%>){
		document.getElementById('radioC1').checked=true;
		document.getElementById('radioC2').disabled=true;
		document.getElementById('radioC3').disabled=true;
		document.getElementById('radioC4').disabled=true;

	}else{
		document.getElementById('radioC2').disabled=false;
		document.getElementById('radioC3').disabled=false;
		document.getElementById('radioC4').disabled=false;
	}
}

function show(obj){
	document.getElementById(obj).style.display='block';
}
function hide(obj){
	document.getElementById(obj).style.display='block';
}



var grid_column_0=0.04;
//var grid_column_1=0.3;
var grid_column_2=0.21;
var grid_column_3=0.14;
var grid_column_4=0.12;
var grid_column_5=0.12;
var grid_column_6=0.12;
var grid_column_7=0.12;
var grid_column_8=0.12;
//var grid_column_9="";
var flag_ss=false;

function abcde(obj){
	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
	   if(flag_ss==false){
		 	//收缩
		 	document.getElementById("td1").style.display="none";
	   		/* document.getElementById("divtab2").firstChild.style.width=1;//查询列表宽度设置
	        var tree = Ext.getCmp('group');
	        //tree的宽度设置
	        tree.setWidth(1);
	        //tab的宽度设置
	        var resizeobj =Ext.getCmp('tab');
			resizeobj.setWidth(1); */

			flag_ss=true;//隐藏标志
			//document.getElementById(obj.id).innerHTML='>';
			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度

	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }else{ //伸展开

			/*  document.getElementById("divtab2").firstChild.style.width=250;//查询列表宽度设置
		     var tree = Ext.getCmp('group')
		     //tree的宽度设置
		     tree.setWidth(250);
		     //tab的宽度设置
		     var resizeobj =Ext.getCmp('tab');
			 resizeobj.setWidth(250); */
			 document.getElementById("td1").style.display="block";

			 flag_ss=false;//伸展开标志
			 //document.getElementById(obj.id).innerHTML='<span><</span>';
			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度

	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }
}


	var tab_flag="tab1";//tab页显示标志
	function grantTabChange(tabObj,item){
		if(item.getId()=='tab3'){
		tab_flag="tab3";
	     document.getElementById("cuslView").style.display='block';
	     document.getElementById("tabn").value = "tab3";
		}else if(item.getId()=='tab2'){
			tab_flag="tab2";
			document.getElementById("tabn").value = "tab2";
		}else{
			tab_flag="tab1";
			document.getElementById("tabn").value = "tab1";
		}
	}

var selectedRow = null;
    var doAddPerson = (function () {//增加人员
        var obj = null;
        return {

            queryByNameAndIDS: function (list,sub) {//按姓名查询
			document.getElementById("checkedgroupid").value = sub;
                //if(obj){
                radow.doEvent('queryByNameAndIDS', list);
                //}
            }

        }
    })();

</script>
<script type="text/javascript">
Ext.onReady(function(){

    //初始化grid的宽度
    try {//
        var grid = odin.ext.getCmp('peopleInfoGrid');
        var width = document.getElementById("peopleInfoGrid").parentNode.offsetWidth;
        grid.setWidth(parseInt(width));
    } catch (err) {

    }

    var fy = "fy";
    var pageSizeNew = "";
    if ('<%=pageSize%>'!= fy){
		pageSizeNew = <%=pageSize%>;
	}


	if(pageSizeNew != null && pageSizeNew != ""){
		var gridId = "peopleInfoGrid";
		pageingToolbar = (Ext.getCmp(gridId).getBottomToolbar() || Ext.getCmp(gridId).getTopToolbar());
		pageingToolbar.pageSize = Number(pageSizeNew);
		var s = Ext.getCmp(gridId).store;
		s.baseParams.limit = pageSizeNew;
		if(s.lastOptions && s.lastOptions.params){
			s.lastOptions.params.limit = pageSizeNew;
		}
	}


});

</script>
<style>
.a {

}
</style>

<script type="text/javascript">



Ext.onReady(function(){

	Ext.MessageBox.minWidth=140;
	/* var grid = Ext.getCmp('peopleInfoGrid');
	var store = grid.store;
	alert(store.getSortState().field);
	alert(store.getSortState().direction);	 */
});

function refreshPerson(){

	var a0201b = document.getElementById("a0201b").value;

	if(a0201b == null || a0201b == ''){
		return;
	}


	var tableType = document.getElementById("tableType").value;


	if(tableType == 1){								//列表

		showgrid()
	}


	if(tableType == 2){								//小资料
		//radow.doEvent("ShowData");
		datashow()
	}

	if(tableType == 3){								//照片
		//radow.doEvent("Show");
		picshow()
	}

}

function ShowCellCover(elementId, titles, msgs)
{
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){

		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5,
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("系统提示", msgs, function(but) {

			});
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK
			});
		}
}

//临时排序
function sort(){
	$h.openWin('Sort','pages.publicServantManage.Sort','临时排序 ',560,680,document.getElementById('tableType').value,ctxPath);
}

function load(){
	radow.doEvent("peopleInfoGrid.dogridquery");
}

//手动排序 ,设置自定义排序字段
function sortHand(){
	var url = contextPath + "/pages/customquery/A01SortConfig.jsp?";
	$h.showWindowWithSrc("sortHand",url,"设置排序", 400, 600);
}

Ext.override(Ext.data.Store, {
	load : function(options) {
        options = options || {};
        this.storeOptions(options);
        if(this.sortInfo && this.remoteSort){
        	//console.log(options)
            var pn = this.paramNames;
            options.params = options.params || {};
            options.params[pn.sort] = this.sortInfo.field;
            options.params[pn.dir] = this.sortInfo.direction;
            //console.log(options)
            if(!options.params['isPageTurning']&&options.params['start']>0){
            	options.params['startCache'] = options.params['start'];
            	options.params['start'] = 0;
            }
        }
        try {
            return this.execute('read', null, options); // <-- null represents rs.  No rs for load actions.
        } catch(e) {
            this.handleException(e);
            return false;
        }
    },
    /**
     * This method should generally not be used directly.  This method is called internally
     * by {@link #load}, or if a Writer is set will be called automatically when {@link #add},
     * {@link #remove}, or {@link #update} events fire.
     * @param {String} action Action name ('read', 'create', 'update', or 'destroy')
     * @param {Record/Record[]} rs
     * @param {Object} options
     * @throws Error
     * @private
     */
    execute : function(action, rs, options) {
        // blow up if action not Ext.data.CREATE, READ, UPDATE, DESTROY
        if (!Ext.data.Api.isAction(action)) {
            throw new Ext.data.Api.Error('execute', action);
        }
        // make sure options has a params key
        options = Ext.applyIf(options||{}, {
            params: {}
        });

        // have to separate before-events since load has a different signature than create,destroy and save events since load does not
        // include the rs (record resultset) parameter.  Capture return values from the beforeaction into doRequest flag.
        var doRequest = true;

        if (action === 'read') {
            doRequest = this.fireEvent('beforeload', this, options);
        }else {
            // if Writer is configured as listful, force single-recoord rs to be [{}} instead of {}
            if (this.writer.listful === true && this.restful !== true) {
                rs = (Ext.isArray(rs)) ? rs : [rs];
            }
            // if rs has just a single record, shift it off so that Writer writes data as '{}' rather than '[{}]'
            else if (Ext.isArray(rs) && rs.length == 1) {
                rs = rs.shift();
            }
            // Write the action to options.params
            if ((doRequest = this.fireEvent('beforewrite', this, action, rs, options)) !== false) {
                this.writer.write(action, options.params, rs);
            }
        }
        if (doRequest !== false) {
            // Send request to proxy.
            var params = Ext.apply({}, options.params, this.baseParams);
            if (this.writer && this.proxy.url && !this.proxy.restful && !Ext.data.Api.hasUniqueUrl(this.proxy, action)) {
                params.xaction = action;
            }
            // Note:  Up until this point we've been dealing with 'action' as a key from Ext.data.Api.actions.  We'll flip it now
            // and send the value into DataProxy#request, since it's the value which maps to the DataProxy#api
            this.proxy.request(Ext.data.Api.actions[action], rs, params, this.reader, this.createCallback(action, rs), this, options);
        }
        if(!options.params['isPageTurning']&&options.params['startCache']>0){
        	options.params['start'] = options.params['startCache'];
        	delete options.params['startCache'];
        }
        return doRequest;
    }
});



var $h2 = {
		'confirm' : function(title, msg, width, fn) {
			Ext.Msg.buttonText.ok='确认';
			Ext.Msg.buttonText.cancel='取消';
			Ext.Msg.show({
						title : title,
						msg : msg,
						width : width,
						buttons : Ext.MessageBox.OKCANCEL,
						fn : fn
					});
		}
	};

function openSortWin(){
	var b0111 = document.getElementById("checkedgroupid").value;
	if(b0111){
		$h.openPageModeWin('A01SortGrid','pages.publicServantManage.PersonSort&initParams='+b0111,'集体内排序',650,450,'','<%=request.getContextPath()%>',window);
	}else{
		odin.alert("请选择一个机构！");
	}
}
//显示固定格式列表
/* function openTableList(){
	radow.doEvent('tableList');
} */


function openPersionFile(){
	var a0000 = document.getElementById("a0000").value;
	var g_contextpath = '<%=request.getContextPath()%>';
	$h.openPageModeWin('persionfile','pages.publicServantManage.PersionFile','干部材料',1100,600,{a0000:a0000},g_contextpath);
}


function peoplePortrait(){
	 var a0000PeoplePortrait = document.getElementById("a0000").value;
	 var ctxPath = "<%=request.getContextPath()%>";
	 var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数
	 if (window.screen) {
	    var ah = screen.availHeight - 30;
	    var aw = screen.availWidth - 90;
	    fulls += ",height=" + ah;
	    fulls += ",innerHeight=" + ah;
	    fulls += ",width=" + aw;
	    fulls += ",innerWidth=" + aw;
	    fulls += ",resizable"
	 } else {
	    fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually
	 }
	 window.open(ctxPath+"/radowAction.do?method=doEvent&pageModel=pages.customquery.PeoplePortrait&a0000="+a0000PeoplePortrait,"画像",fulls);

}
document.onkeydown=function() {

	if (event.keyCode == 13) {
		if (document.activeElement.id == "seachName") {
			searchPersonByName();
			return false;
		}
	}else if(event.keyCode == 27){	//禁用ESC
	        return false;
    }
};



//加载浏览机构树
function lookPerson(){
	document.getElementById('LWflag').value = '1';//切换浏览编辑树权限标识
	document.getElementById('changeLook').style.display="none";
	document.getElementById('changeWrite').style.display="";
	odin.ext.getCmp('loadadd').hide();
	odin.ext.getCmp('rmbUpdate').hide();
	odin.ext.getCmp('deletePersonBtn').hide();
	odin.ext.getCmp('betchModifyBtn').hide();
	odin.ext.getCmp('getA17').hide();
	odin.ext.getCmp('PersionFileBtn').hide();
	odin.ext.getCmp('GxgaFileBtn').hide();
	odin.ext.getCmp('imprmboldBtn').hide();
	//odin.ext.getCmp('ImpA15').hide();
	Ext.getCmp('sortWin').setVisible(false);

	var loader = Ext.getCmp("group").getLoader();
	Ext.apply(loader.baseParams,{sign: 'look'});
	Ext.getCmp("group").root.reload();
	Ext.getCmp('group').getRootNode().expand(false,true, callback);
	Ext.getCmp("group").expandPath(Ext.getCmp('group').getRootNode().getPath(),null,function(){addnode();});
	//Ext.getCmp("group").expandPath(Ext.getCmp('group').getRootNode().getPath(),null,function(){addnodebm();});   //无主职务或主职务已免
	document.getElementById('lookOrWrite').value = "look";

	radow.doEvent("consoleButtons");
}
//加载编辑机构树
function writePerson(){
	document.getElementById('LWflag').value = '2';//切换浏览编辑树权限标识
	document.getElementById('changeLook').style.display="";
	document.getElementById('changeWrite').style.display="none";
	odin.ext.getCmp('loadadd').show();
	odin.ext.getCmp('rmbUpdate').show();
	odin.ext.getCmp('deletePersonBtn').show();
	odin.ext.getCmp('betchModifyBtn').show();
	odin.ext.getCmp('getA17').show();
	odin.ext.getCmp('PersionFileBtn').show();
	odin.ext.getCmp('GxgaFileBtn').show();
	odin.ext.getCmp('imprmboldBtn').show();
	//odin.ext.getCmp('ImpA15').show();
	Ext.getCmp('sortWin').setVisible(true);


	var loader = Ext.getCmp("group").getLoader();
	Ext.apply(loader.baseParams,{sign: 'write'});
	Ext.getCmp("group").root.reload();
	Ext.getCmp('group').getRootNode().expand(false,true, callback);
	Ext.getCmp("group").expandPath(Ext.getCmp('group').getRootNode().getPath(),null,function(){addnode();});
	//Ext.getCmp("group").expandPath(Ext.getCmp('group').getRootNode().getPath(),null,function(){addnodebm();});   //无主职务或主职务已免
	document.getElementById('lookOrWrite').value = "write";

	radow.doEvent("consoleButtons");
}

</script>
<script type="text/javascript">
/* 去除排序 清空输入框 */
function cancleSort(){
	//alert(11111);
	var grid = Ext.getCmp('peopleInfoGrid');
	var store = grid.getStore();
	var sortInfo = store.sortInfo;
	store.sortInfo = null;
	$(".sort-asc").removeClass("sort-asc");
	$(".sort-desc").removeClass("sort-desc");
	$('#seachName').val('');
	$('#seachName').focus();
	$('#seachName').blur();
}
</script>
<!-- 提示人员异常情况 -->
<script type="text/javascript">
     Ext.onReady(function () {
    	 //ajaxsearchWaring('warningPerson');
     });

     function ajaxsearchWaring(radowEvent) {
    	 Ext.Ajax.request({
    			method: 'POST',
    	        async: true,
    	        params : {},
    	        timeout :300000,//按毫秒计算
    			url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&eventNames="+radowEvent,
    			success: function(resData){
    				//alert(resData.responseText);
    				var cfg = Ext.util.JSON.decode(resData.responseText);
    				if(0==cfg.messageCode){
    					Ext.Msg.hide();

    					if(cfg.elementsScript.indexOf("\n")>0){
    						cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
    						cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
    					}

    					eval(cfg.elementsScript);
    					if("操作成功！"!=cfg.mainMessage){
    						Ext.Msg.alert('系统提示:',cfg.mainMessage);
    					}
    				}else{
    					//Ext.Msg.hide();

    					if(cfg.mainMessage.indexOf("<br/>")>0){

    						$h.alert('系统提示',cfg.mainMessage,null,380);
    						return;
    					}

    					if("操作成功！"!=cfg.mainMessage){
    						Ext.Msg.alert('系统提示:',cfg.mainMessage);
    					}
    				}

    			},
    			failure : function(res, options){
    				Ext.Msg.hide();
    	            alert("网络异常！");
    	        }
    	    });
    	}


     function searchWaring(){
    	 radow.doEvent("warningPerson");
     }
     function warningPerson(obj){
    	 obj = eval(obj);
    	 var len = obj.length;
    	 if(len==0)
    		 return;
    	 $("#warning_NUM").html(len);
    	 for(var i = 0 ; i<len; i++){
    		 obj[i].value=obj[i].value.replace(/\"/g,"'");
    		 obj[i].handler = function() {runSql(this.value);}
    		 }
         var eBody = Ext.getBody();

         var msgWinConfig = { width: 300, height: 150 };


         var win = new Ext.Window({
        	 closable: false,
        	 draggable:false,
        	 resizable:false,
        	 closable: true,
        	 closeAction:'hide',
        	 autoHide:3,
        	 shadow: false,
        	 resizable: false,
             x: eBody.getWidth() - msgWinConfig.width,
             y: eBody.getHeight() - msgWinConfig.height,
             width: msgWinConfig.width,
             height: msgWinConfig.height,
             items:obj
         });
         win.show();

     }
function runSql(sql){
	$("#sql").val(sql);
	radow.doEvent("peopleInfoGrid.dogridquery");
}


function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}


<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();
if(RrmbCodeType==null){
	RrmbCodeType = CodeType2js.getRrmbCodeType();
	session.setAttribute("RrmbCodeType",RrmbCodeType);
}
%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
}

function gbmcQuery(){
	$h.openPageModeWin('gbmcQuery','pages.customquery.gbmcQuery','干部查询列表',650,480,'','<%=request.getContextPath()%>',window);
}

function clearSelected() {
    var tableType = document.getElementById("tableType").value;
    if (tableType == '1') {
        //列表
		var gridId = "peopleInfoGrid";
		var fieldName = "personcheck";
		var store = odin.ext.getCmp(gridId).store;
		var length = store.getCount();
		for (var i = 0; i < length; i++) {
			store.getAt(i).set(fieldName, false);
			
		}
		var objs = Ext.query(".x-grid3-check-col-on");
		for(var i=0;i<objs.length;i++){
			objs[i].className = "x-grid3-check-col";
		}
	} else if (tableType == '2') {
		//小资料、小资料
		for (var i = 0; i < 6; i++) {
			document.getElementById("datac" + i).checked = false
		}
	} else if (tableType == '3') {
		//照片
		for (var i = 0; i < 10; i++) {
			document.getElementById("c" + i).checked = false;
		}
	}
    $('#picA0000s').val('');
}

function addpublicity(){
	radow.doEvent("chuidrusesson");
	radow.doEvent("addpublicity");
}

function addHistorylist(){
	var gridId = "peopleInfoGrid";
    var grid = Ext.getCmp(gridId);
    var store = grid.getStore();
    var length = store.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '请先选择记录！', null, 150);
        return;
    }
	$h.openPageModeWin('addHistorylist','pages.customquery.addHistoryMD','历史名单添加',500,350,'',ctxPath);
}
function addHistoryPer(str){
	radow.doEvent("chuidrusesson");
	radow.doEvent("addHistoryPer",str);
}

 </script>

<input type="hidden" id="test" oninput="OnInput (event)" onpropertychange="OnPropChanged(event)"  value="1">
<input type="hidden" id="test1" oninput="OnInput (event)" onpropertychange="OnPropChanged1(event)" >
<odin:hidden property="a0163" />
<odin:hidden property="isContain2"/>  <%--单机构点击和多机构点击区分--%>
<odin:hidden property="a0163_combo" />
<odin:hidden property="expword_personid"/>	<!-- 右键导出word单人id -->
<odin:hidden property="excelfile"/>
<odin:hidden property="a1701Word" title="常用查询传过来的值"/>
<odin:hidden property="zb09Words" title="简历匹配传过来的值"/>
<odin:hidden property="a0215aWord" title="常用查询传过来的值"/>
<odin:hidden property="a0814Word" title="常用查询传过来的值"/>
<odin:hidden property="rmbs" title="已经打开的任免表页面的人员id"/>
<odin:window src="/blank.htm" id="addOrgWin" width="1200" height="900"
	title="干部任免审批表" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin1" width="1200" height="900"
	title="公务员登记表格" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin2" width="1200" height="900"
	title="干部简要表格" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin3" width="1200" height="900"
	title="奖励审批表格" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin4" width="1200" height="900"
	title="年度考核表格" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin5" width="1200" height="900"
	title="花名册" modal="true"></odin:window>

