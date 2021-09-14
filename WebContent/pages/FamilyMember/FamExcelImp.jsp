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
							<font style="font-size: 13px;">&nbsp&nbspѡ��Ҫ������ļ���</font>
							<input id="import" type="file" class="file" name="ww" /> 
						</div>
					</form>
				</td>
			</tr>
			<%-- <tr>
				<td colspan="2">
					<div class="imgDiv">
					�������ģ��:
					<a href="javascript:downLoadExcel()">&nbsp�ټ�ͥ��Ա��Ϣģ��</a><img src="<%=ctxPath%>/image/icon_excel_sm2.png" class="download_icon" onclick="downLoadExcel();return false;">
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
					<font style="font-size: 13px; ">&nbsp* �����Excel�ļ��ĸ�ʽ��������дģ��һ��,�����ܵ���!</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="&nbsp;��ʼ����&nbsp;" id="start" onclick="startImport()" />
					<input type="button" value="&nbsp;&nbsp;�ر�&nbsp;&nbsp;" id="closeWin" onclick="gbWin()" />
				</td>
			</tr>
		</table>
	</div>
	<odin:hidden property="pathfile" title="�ϴ�·��"/>
	<script  type="text/javascript">
	/* �ر� */
	function gbWin(){
		parent.odin.ext.getCmp('FamExcelImp').hide();
	}

	/* ��ʼ���� */
	function startImport() {
		var file=$("#import").val();
		var index1=file.lastIndexOf(".");
		var index2=file.length;
		var suffix=file.substring(index1+1,index2);
		if(file==""){
			alert("�����ļ�����Ϊ�գ������µ��룡");
		}else if(suffix=="xls"||suffix=="xlsx"||suffix=="zip"){
			var path = contextPath + '/FormImportAction.do?method=startImp';
			//ShowCellCover('start', 'ϵͳ��ʾ', '���ڴ���EXCEL�ļ�,��Ҫһ��ʱ��,�����Ե�...');
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
									alert("����ɹ�");
									gbWin();// �ر�ҳ�� 
								}
								if ('error' == result) {
									alert("����ʧ��");
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
				alert("�ļ���ʽ����ȷ,�����µ���!");
			}
	}
	/**
	 * ����ģ��
	 */
	function downLoadExcel(url){
		var fileName='error.xls';
		window.location=contextPath +"/FormImportAction.do?method=downloadExcel&url="+url+"&fileName="+fileName;
	}
	
	</script>
</body>
</html>
