<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
SysOrgPageModel sys = new SysOrgPageModel();
String picType = (String) (sys.areaInfo.get("picType"));
String ereaname = (String) (sys.areaInfo.get("areaname"));
String ereaid = (String) (sys.areaInfo.get("areaid"));
String manager = (String) (sys.areaInfo.get("manager"));
String ctxPath = request.getContextPath();
String downloadfile="jtxxplxz";

%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title></title>
<script type="text/javascript">var contextPath = "<%=ctxPath%>", downloadfileName = "<%=downloadfile%>";</script>
<odin:head />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/imp/excelImp.css" />
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/odin.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/radow/corejs/radow.util.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/jquery/jquery-1.7.2.min.js"></script>
</head>
<body>
	<div style="width: 100%; height: 160px;">
		<table style="width: 100%; height: 100%;margin-top:10px;">
			<tr>
				<td colspan="2">
					<form id="data" name="data" method="post" enctype="multipart/form-data">
						<div>
							<font style="font-size: 13px;">&nbsp&nbsp选择要导入的文件：</font>
							<input id="import" type="file" class="file" name="ww" /> 
						</div>
					</form>
				</td>
			</tr>
			<%-- <tr>
				<td colspan="2">
					<div class="imgDiv">
					点击下载模板:
					<a href="javascript:downLoadExcel()">&nbsp①家庭成员信息模板</a><img src="<%=ctxPath%>/image/icon_excel_sm2.png" class="download_icon" onclick="downLoadExcel();return false;">
					</div>
				</td>
			</tr> --%>
			<tr>
				<td colspan="2">
					<hr/>
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<font style="font-size: 13px; ">&nbsp* 导入的Excel文件的格式必须与填写模板一致,否则不能导入!</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="&nbsp;开始导入&nbsp;" id="start" onclick="startImport()" />
					<input type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;" id="closeWin" onclick="gbWin()" />
				</td>
			</tr>
		</table>
	</div>
	<odin:hidden property="pathfile" title="上传路径"/>
	<script  type="text/javascript">
	/* 关闭 */
	function gbWin(){
		parent.odin.ext.getCmp('FamExcelImp').hide();
	}

	/* 开始导入 */
	function startImport() {
		var file=$("#import").val();
		var index1=file.lastIndexOf(".");
		var index2=file.length;
		var suffix=file.substring(index1+1,index2);
		if(file==""){
			alert("导入文件不能为空，请重新导入！");
		}else if(suffix=="xls"||suffix=="xlsx"||suffix=="zip"){
			var path = contextPath + '/FormImportAction.do?method=startImp';
			//ShowCellCover('start', '系统提示', '正在处理EXCEL文件,需要一定时间,请您稍等...');
			Ext.Ajax.request({
				url : path,
				form : 'data',
				callback : function(options, success, response) {
					if (success) {
						var result = response.responseText;
						 if (result) {
							result = result.substring(5, result.length - 6);
							var json = eval('(' + result + ')');
							var data = json.data;
							var result=data.key;
							var type =data.type;
								if ('success' == result) {
									alert("导入成功");
									gbWin();// 关闭页面 
								}
								if ('error' == result) {
									alert("导入失败");
									if('zip' == type){
										downLoadExcel(data.file)
									}
									
								}
						}
						parent.radow.doEvent('noticeSetgrid.dogridquery');
						
					}
				}
			});
		}else{
				alert("文件格式不正确,请重新导入!");
			}
	}
	/**
	 * 下载模板
	 */
	function downLoadExcel(url){
		var fileName='error.xls';
		window.location=contextPath +"/FormImportAction.do?method=downloadExcel&url="+url+"&fileName="+fileName;
	}
	
	</script>
</body>
</html>
