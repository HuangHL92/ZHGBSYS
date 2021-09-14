<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>功能测试</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<link href="basejs/ext/resources/css/ext-all.css" rel="stylesheet" type="text/css" />
</head>
<SCRIPT ID="clientEventHandlersVBS" LANGUAGE="vbscript">
<!--
Sub expexcel_onclick
	///msgbox "daochu33333"
	///DCellWeb1.ExportExcelDlg
	///DCellWeb1.DoExportExcelFile
End Sub

sub addimgex
	col = DCellWeb1.GetCurrentCol
	row = DCellWeb1.GetCurrentRow
	a1 = 0
	a2 = 0
	tp = 2 '图片拉伸
	select case tp
		case "1"
			tp=0
			a1 = 2 '左右对齐
			a2 = 2 '上下对齐
		case "2"
		if true = true then
			tp=3
		else
			tp=1
		end if
		case else
			tp=4
	end select
	imgurl = "http://localhost:8080/hzbtj/img/sbjs.jpg"
	if imgurl="" then
		DCellWeb1.SetCellImage col,row,0,-1,0,0,0
	else
		url = DCellWeb1.AddImage(imgurl)
		if url>=0 then
			DCellWeb1.SetCellImage col,row,0,url,tp,a1,a2
			DCellWeb1.redraw()
			'msgbox "图片插入成功！"
		else
			msgbox "图片载入失败！"
		end if 
	end if
end sub
-->
</SCRIPT>
<script type="text/javascript">
var ctpath = "<%=request.getContextPath()%>";
function ofileonclick(){
	DCellWeb1.openfile(ctpath+"/template/010101.cll","");
	//DCellWeb1.ImportExcelDlg();
}
function savetable(){
	DCellWeb1.savefile();
}
function exportexcels(){
	///alert(11111);
	DCellWeb1.ExportExcelDlg();	
}
function saveXml(){
	var xmlcontent = DCellWeb1.SaveToXML(""); 
	document.getElementById("xmldiv").innerText=xmlcontent;
}
function readXml(){
	setAuth(DCellWeb1);
/**	var xmlcon = "";
	var xmlcon = xmlcon+"<Workbook><DocumentProperties><Created>2017-03-13T11:30:38Z</Created><Version>03.1015</Version></DocumentProperties><Worksheet Name=\"第1页\"><Table><Row Index=\"2\"><Cell Index=\"2\"><Data Type=\"String\">职务</Data></Cell><Cell Index=\"4\"><Data Type=\"String\">姓名</Data></Cell><Cell Index=\"6\"><Data Type=\"String\">籍贯</Data></Cell><Cell Index=\"8\"><Data Type=\"String\">出生</Data></Cell><Cell Index=\"10\"><Data Type=\"String\">全日制</Data></Cell><Cell Index=\"12\"><Data Type=\"String\">在职</Data></Cell><Cell Index=\"14\"><Data Type=\"String\">入党</Data></Cell><Cell Index=\"16\"><Data Type=\"String\">参加工</Data></Cell><Cell Index=\"18\"><Data Type=\"String\">任职</Data></Cell><Cell Index=\"20\"><Data Type=\"String\">备注</Data></Cell></Row><Row Index=\"3\"><Cell Index=\"8\"><Data Type=\"String\">年月</Data></Cell><Cell Index=\"10\"><Data Type=\"String\">学历</Data></Cell><Cell Index=\"12\"><Data Type=\"String\">学历</Data></Cell><Cell Index=\"14\"><Data Type=\"String\">时间</Data></Cell><Cell Index=\"16\"><Data Type=\"String\">作时间</Data></Cell><Cell Index=\"18\"><Data Type=\"String\">时间</Data></Cell></Row><Row Index=\"5\"><Cell Index=\"2\"><Data Type=\"String\">监\n察员\n";
	var xmlcon = xmlcon+"ADSads";
	var xmlcon = xmlcon+"ererwefr\nwads";
	var xmlcon = xmlcon+"阿斯顿发斯蒂芬";
	var xmlcon = xmlcon+"SADas";
	var xmlcon = xmlcon+"大师法士大夫</Data></Cell><Cell Index=\"4\"><Data Type=\"String\">王家卫</Data></Cell><Cell Index=\"6\"><Data Type=\"String\">浙江磐安</Data></Cell><Cell Index=\"8\"><Data Type=\"String\">1980.01</Data></Cell><Cell Index=\"10\"><Data Type=\"String\">大学</Data></Cell><Cell Index=\"12\"><Data Type=\"String\">研究生</Data></Cell><Cell Index=\"14\"><Data Type=\"String\">1998.01</Data></Cell><Cell Index=\"16\"><Data Type=\"String\">2000.01</Data></Cell><Cell Index=\"18\"><Data Type=\"String\">2003.01</Data></Cell><Cell Index=\"20\"><Data Type=\"String\">监察厅监察员</Data></Cell></Row></Table></Worksheet></Workbook>";
	//alert(1111);
	DCellWeb1.ReadFromXML(xmlcon);	
	*/
	/**var cellxml = "";
	cellxml = cellxml+"<Workbook>";
	cellxml = cellxml+"<DocumentProperties><Created>2017-03-13T12:24:55Z</Created><Version>03.1015</Version></DocumentProperties>";
	cellxml = cellxml+"<Worksheet Name=\"第1页\">";
	cellxml = cellxml+"<Table>";
	cellxml = cellxml+"<Row Index=\"1\">";
	cellxml = cellxml+"<Cell Index=\"1\">";
	cellxml = cellxml+"<Data Type=\"String\">asdfasd\nfas\ndfschar(10)a</Data>";
	cellxml = cellxml+"</Cell>";
	cellxml = cellxml+"</Row>";
	cellxml = cellxml+"</Table>";
	cellxml = cellxml+"</Worksheet>";
	cellxml = cellxml+"</Workbook>";**/
	
	cellxml = "";
	cellxml = cellxml+"<Workbook>";
	/**cellxml = cellxml+"<DocumentProperties>";
	cellxml = cellxml+"<Created>2017-05-23T10:48:39Z</Created>";
	cellxml = cellxml+"<Version>03.1015</Version>";
	cellxml = cellxml+"</DocumentProperties>";**/
	cellxml = cellxml+"<Worksheet Name=\"第1页\">";
	cellxml = cellxml+"<Table>";
	/**cellxml = cellxml+"<Row Index=\"2\">";
	cellxml = cellxml+"<Cell Index=\"2\"><Data Type=\"String\">职务</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"4\"><Data Type=\"String\">姓名</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"6\"><Data Type=\"String\">籍贯</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"8\"><Data Type=\"String\">出生</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"10\"><Data Type=\"String\">全日制</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"12\"><Data Type=\"String\">在职</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"14\"><Data Type=\"String\">入党</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"16\"><Data Type=\"String\">参加工</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"18\"><Data Type=\"String\">任职</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"20\"><Data Type=\"String\">备注</Data></Cell>";
	cellxml = cellxml+"</Row>";
	cellxml = cellxml+"<Row Index=\"3\">";
	cellxml = cellxml+"<Cell Index=\"8\"><Data Type=\"String\">年月</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"10\"><Data Type=\"String\">学历</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"12\"><Data Type=\"String\">学历</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"14\"><Data Type=\"String\">时间</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"16\"><Data Type=\"String\">作时间</Data></Cell>";
	cellxml = cellxml+"<Cell Index=\"18\"><Data Type=\"String\">时间</Data></Cell>";
	cellxml = cellxml+"</Row>";**/
	DCellWeb1.InsertRow(50, 6050, 0);
	for(var i = 5;i<100;i++){
		/**cellxml = cellxml+"<Row Index=\""+i+"\">";
		cellxml = cellxml+"<Cell Index=\"2\"><Data Type=\"String\">监察员</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"4\"><Data Type=\"String\">李世石</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"6\"><Data Type=\"String\">浙江杭州</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"8\"><Data Type=\"String\">1980.01</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"10\"><Data Type=\"String\">大学</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"12\"><Data Type=\"String\">研究生</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"14\"><Data Type=\"String\">2000.01</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"16\"><Data Type=\"String\">2000.01</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"18\"><Data Type=\"String\">2000.10</Data></Cell>";
		cellxml = cellxml+"<Cell Index=\"20\"><Data Type=\"String\">监察厅监察员</Data></Cell>";
		cellxml = cellxml+"</Row>";**/
		DCellWeb1.SetCellString(2,i,0,"监察员");
		DCellWeb1.SetCellString(4,i,0,"监察员");
		DCellWeb1.SetCellString(6,i,0,"监察员");
		DCellWeb1.SetCellString(8,i,0,"监察员");
		DCellWeb1.SetCellString(10,i,0,"监察员");
		DCellWeb1.SetCellString(12,i,0,"监察员");
		DCellWeb1.SetCellString(14,i,0,"监察员"); 
		DCellWeb1.SetCellString(16,i,0,"监察员");
		DCellWeb1.SetCellString(18,i,0,"监察员");
		DCellWeb1.SetCellString(20,i,0,"监察员");
	}
	DCellWeb1.SetCellFontSize(2,6,0,12);
	DCellWeb1.SetCellAlign(2,6,0, 64 + 8);
	DCellWeb1.SetCellHideZero(2,6,0, 0);
	DCellWeb1.SetRowHeight(1,64,6, 0);
	DCellWeb1.SetCellTextStyle(2,6,0,2); 
	
	/**
DCellWeb1.SetCellString(2,i,0,"监察员");
		DCellWeb1.SetCellFontSize(2,i,0,12);
		DCellWeb1.SetCellAlign(2,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(2,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(2,i,0,2); 
		
		DCellWeb1.SetCellString(4,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(4,i,0,12);
		DCellWeb1.SetCellAlign(4,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(4,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(4,i,0,2); 
		
		DCellWeb1.SetCellString(6,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(6,i,0,12);
		DCellWeb1.SetCellAlign(6,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(6,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(6,i,0,2); 
		
		DCellWeb1.SetCellString(8,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(8,i,0,12);
		DCellWeb1.SetCellAlign(8,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(8,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(8,i,0,2); 
		
		DCellWeb1.SetCellString(10,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(10,i,0,12);
		DCellWeb1.SetCellAlign(10,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(10,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(10,i,0,2); 
		
		DCellWeb1.SetCellString(12,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(12,i,0,12);
		DCellWeb1.SetCellAlign(12,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(12,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(12,i,0,2); 
		
		DCellWeb1.SetCellString(14,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(14,i,0,12);
		DCellWeb1.SetCellAlign(14,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(14,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(14,i,0,2); 
		
		DCellWeb1.SetCellString(16,i,0,"监察员");
		DCellWeb1.SetCellFontSize(16,i,0,12);
		DCellWeb1.SetCellAlign(16,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(16,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(16,i,0,2); 
		
		DCellWeb1.SetCellString(18,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(18,i,0,12);
		DCellWeb1.SetCellAlign(18,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(18,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(18,i,0,2); 
		
		DCellWeb1.SetCellString(20,i,0,"监察员");
 		DCellWeb1.SetCellFontSize(20,i,0,12);
		DCellWeb1.SetCellAlign(20,i,0, 64 + 8);
		DCellWeb1.SetCellHideZero(20,i,0, 0);
		DCellWeb1.SetRowHeight(1, 64, i, 0);
		DCellWeb1.SetCellTextStyle(20,i,0,2); 
	
	**/
	
	
	cellxml = cellxml+"</Table>";
	cellxml = cellxml+"</Worksheet>";
	cellxml = cellxml+"</Workbook>";
	
	//alert(cellxml);
	//DCellWeb1.InsertRow(50, 6050, 0);
	//DCellWeb1.ReadFromXML(cellxml);	
	
	//DCellWeb1.SetCellString(1,1,0,"a\rsdfasd\nfas\ndfschar(10)\ra");
}

function setCellImg(){
	//DCellWeb1.	
}
</script>
<body>

<object id="DCellWeb1" style="left: 0px; width: 99%; top: 0px; height: 500px" 
    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
    codebase="softTools/cellweb5.cab#version=5,3,8,0429">
    <param name="_Version" value="65536" />
    <param name="_ExtentX" value="10266" />
    <param name="_ExtentY" value="7011" />
    <param name="_StockProps" value="0" />
</object>

<table width="95%" align="left">
	<tr>
		<td>
			<input name="ofile" type="button" value = "远程模板打开" onclick="javascript:ofileonclick();"/>
			<input name="sfile" type="button" value = "保存表样" onclick="javascript:savetable();"/>
			<input name="expexcel" type="button" value = "Excel文件" onclick="javascript:exportexcels();"/>
			<input name="sxmlcell" type="button" value = "WiteXml文件" onclick="javascript:saveXml();"/>
			<input name="rxmlcell" type="button" value = "ReadXml文件" onclick="javascript:readXml();"/>
			<input name="openwin" type="button" value = "EXT窗口" onclick="javascript:openWind();"/>
			<input name="lookva" type="button" value = "查看返回值" onclick="javascript:lookValue();"/>
			<input type="button" name=addimg value="添加图片 " onclick="addimgex()"/>
			<input type="button" name=testbut value="测试按钮 " onclick="javascript:testss();"/>
			
		</td>
	</tr>
</table>

<div id="xmldiv"></div>
</body>
<script type="text/javascript">

setAuth(document.getElementById("DCellWeb1"));

ofileonclick();

</script>
<script type="text/javascript">

var param = "";
function openWind(){
	var xmwin = new Ext.Window({
		title : "测试窗口",
		renderTo : Ext.getBody(),
		width : 880,
		height : 385,
		resizable:false,
		layout : 'column',	     						
		modal : true,
		html : "<iframe src='http://localhost:8080/hzb/testSerP.jsp' height='400' width='900'></iframe>",
		buttonAlign : "left",
		closeAction : "hide",
		buttons : [{
			xtype : "button",
			text : "关&nbsp;&nbsp;&nbsp;&nbsp;闭",
			handler : function() {   											
				xmwin.close();
			}
		}]
	});
	xmwin.show();
}
function getWinValue(vals){
	param = vals;
}
function lookValue(){
	alert(param);
}

function testss(){
	document.getElementById("xmldiv").innerText = "\n"+encodeURI("1460-1706-0167-6005");
}
</script>
</html>