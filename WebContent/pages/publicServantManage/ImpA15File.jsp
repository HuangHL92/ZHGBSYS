<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%

	String ctxPath = request.getContextPath();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title></title>
<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
<odin:head />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/imp/excelImp.css" />
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/odin.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/radow/corejs/radow.util.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
</head>
<body>
	<div style="width: 100%; height: 100px;">
		<table style="width: 100%; height: 100%;margin-top:0px;">
			<tr>
				<td colspan="1">
					<form id="data" name="data" method="post" enctype="multipart/form-data">
						<div>
							<font style="font-size: 13px;">&nbsp&nbsp选择要导入的文件：</font>
							<input id="import" type="file" class="file" name="ww" style="width: 300px"/> 
						</div>
					</form>
				</td>
				<td colspan="1">
					<label for="sex" style="font-size: 13px;">导入年份:</label>
						 <select id="drnf" > 
							<%  Calendar  c = new  GregorianCalendar();
								int year = c.get(Calendar.YEAR)-1;
							    for(int i=0;i<15;i++){
							        %>
							        <option value=<%=year-i%>><%=year-i%></option>
							    <%}%>
						 </select>
				</td>
				<td colspan="1" align="center" >
					<input type="button" value="&nbsp;开始导入&nbsp;" id="start" onclick="startImport()" />
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<div class="imgDiv" style="font-size: 13px;">
					  &nbsp;&nbsp;点击下载模板:
					<a href="javascript:downLoadExcel()">年度考核导入模板</a><img src="<%=ctxPath%>/image/icon_excel_sm2.png" class="download_icon" onclick="javascript:downLoadExcel();">
					</div>
				</td>
			</tr>
		</table>
	</div>
	<script  type="text/javascript">
	/* 开始导入 */
	function startImport(){
		var file=document.getElementById('import').value;
		var drnf=document.getElementById('drnf').value;
		if(file==""){
			parent.ShowCellCover('failure','错误提示',"未选择文件");
			return;
		}
		if(drnf==""){
			parent.ShowCellCover('failure','错误提示',"未选择考核年度");
			return;
		}
		var index = file.lastIndexOf("."); 
		var str  = file.substring(index + 1, file.length);
		if(str.indexOf("xls") == -1&&str.indexOf("xlsx")==-1&&str.indexOf("zip")==-1){
			parent.ShowCellCover('failure','错误提示',"文件格式错误或无文件");
			return;
		}
		var path = contextPath + '/ProblemDownServlet?method=impA15Check&drnf='+drnf;
		
		Ext.Ajax.request({
			url : path,
			form : 'data',
			callback : function(options, success, response) {
					var result = response.responseText;
						result=result.replace('<pre>','').replace('</pre>','');
// 						var results=result.split("--");
							if ('success' == result) {
								startImport1(str);
							}
							if ('error' == result) {
								parent.Ext.Msg.confirm("系统提示","该年度已导入同名文件，是否继续导入？",function(id) { 
									if("yes"==id){
										startImport1(str);
									}else{
										return;
									}		
								});	
								
							}
				
			}
		});
		
	}
	/* 开始导入 */
	function startImport1(suffix) {
		var drnf=document.getElementById('drnf').value;
		var batchid=parent.document.getElementById("batchid").value;
		var path = contextPath + '/ProblemDownServlet?method=impA15&batchid='+batchid+'&drnf='+drnf;
		if(suffix=='zip'){
			path=contextPath + '/ProblemDownServlet?method=impA15By7zOrZip&batchid='+batchid+'&drnf='+drnf+'&suffix='+suffix;
		}
		
		parent.ShowCellCover('start', '系统提示', '正在处理EXCEL文件,需要一定时间,请您稍等...');
		Ext.Ajax.request({
			url : path,
			form : 'data',
			callback : function(options, success, response) {
					var result = response.responseText;
						result=result.replace('<pre>','').replace('</pre>','');
						var results=result.split("--");
							if ('success' == results[0]) {
								//parent.radow.doEvent('MGrid.dogridquery');
// 								parent.impCallback();
								parent.ShowCellCover('failure', '系统提示', "导入成功"+results[1]+",导入失败"+results[2]);
								parent.document.getElementById('isFlag').innerHTML='成功<span style=\'color: green;\'>'+results[1]+'</span>条,失败<span style=\'color: red;\'>'+results[2]+'</span>条！<button onclick="confImp()">确认导入</button><button onclick="callImp()">取消导入</button>'
								parent.radow.doEvent('memberGrid.dogridquery');
							}
							if ('error' == results[0]) {
								parent.ShowCellCover('failure', '错误提示', "导入错误");
							}
			}
		});
	}


	/**
	 * 下载模板
	 */
	function downLoadExcel() {
		var fileName = '年度考核导入模板.xls';
		window.location = contextPath + "/ProblemDownServlet?method=downloadExcel&&fileName=" + encodeURI(encodeURI(fileName))
	}
	</script>
</body>
</html>

