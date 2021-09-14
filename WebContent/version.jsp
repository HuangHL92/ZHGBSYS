<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<odin:head/>
<style type="text/css">
.cont{
	margin: 40 auto;
}
.displaycode{ 
	width:780px;
	margin:0 auto;
	border: 1px solid #cccccc;
	font-family: "Andale Mono", "Lucida Console", Monaco, Liberation, fixed, monospace; 
	font-size: 11px; 
	overflow:auto;    
	background:#F7F7F7 !important; 
	color:#000000;
	height: 80px;
	padding-top: 10px;
	padding-left: 10px;
}
#version{
	font-weight: bolder;
	color: red; 
}
</style>
<script type="text/javascript">
	odin.v = {
		build : [{ //������ʷ����һ��Ϊ����һ����Ϣ�����а�����ʷ�����ź͹���ʱ��  
			num : 1,
			date : '2013.12.11',
			version:'V6.2.0' //������Ӧ�汾��
		} ],
		version : 'V6.2.0'  //��ǰ�汾��
	}
	odin.ext.onReady(function() {
		Ext.get("version").insertHtml("afterBegin",odin.v.version);
	});
</script>
</head>
<body>
<table align="center"><tr><td>
<div class="cont">
	<div class="displaycode">
	ϵͳ���к�����ܰ汾�ţ�<span id="version"></span>
	</div>
</div>
</td></tr></table>
</body>
</html>