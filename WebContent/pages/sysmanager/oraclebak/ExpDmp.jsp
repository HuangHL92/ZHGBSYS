<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="mainPage/js/jquery.js"></script>
<script type="text/javascript">

</script>

<style>
div{
border:solid 0px !important;
}
</style>

<div id="addResourceContent">
<odin:hidden property="fptconfigid"/>

	<table style="width: 100%;height: 100%;">
		
		<tr style="height:30px;">
			<odin:textEdit property="username" label="数据库用户名" value="ZHGBSYS" required="true"></odin:textEdit>
			<odin:textEdit property="password" label="数据库密码" value="ZHGBSYS123" required="true"></odin:textEdit>
		</tr>
		<tr style="height:30px;">
		    <odin:textEdit property="sid"  label="数据库服务名" value="localhost:5236" required="true"></odin:textEdit>
		    <odin:textEdit property="savepath" value="/root/expdm" label="备份路径" required="true"></odin:textEdit>
		</tr>
		<tr style="height:30px;">
			<td colspan="4">
			    <label id="bz1" style="font-size: 12;color: red">注：路径以反斜杠“/”作为分隔符（如：C:/HZB）。</label><br>
				<label id="bz2" style="font-size: 12;color: red">注：数据库服务名为本机配置的连接数据库的网络服务名</label><br>
				<label id="bz3" style="font-size: 12;color: red">注：若配置信息输入错误将无法备份数据库文件</label><br>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<iframe width="100%" style="border: 1px solid rgb(179,201,229);background-color: black;" id="iframe_expFile" name="iframe_expFile"  height="80%" src=""></iframe>
			</td>
		</tr>
	</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>数据库配置信息</h3>" />
	<odin:fill />
	
	<odin:buttonForToolBar id="btnExp" text="导出" handler="expSRC" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
	<%-- <odin:buttonForToolBar id="stopExp" isLast="true" text="终止导出"  
		icon="images/save.gif"  cls="x-btn-text-icon" /> --%>
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//页面调整
	document.getElementById("addResourceContent").style.width = document.body.clientWidth-4 + "px";
	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
});


function expSRC(){
	var username = $('#username').val();
	var password = $('#password').val();
	var sid = $('#sid').val();
	var savepath = $('#savepath').val();
	if(username==''){
		alert('请输入数据库用户名');
		return;
	}
	if(password==''){
		alert('请输入数据库密码');
		return;
	}
	if(sid==''){
		alert('请输入数据库服务名');
		return;
	}
	if(savepath==''){
		alert('请输入备份路径');
		return;
	}
	var s = "&username="+username;
	s+= "&password="+password;
	s+= "&sid="+sid;
	s+= "&savepath="+savepath;
	var src = contextPath+'/PublishFileServlet?method=expPBCX'+s;
	$('#iframe_expFile').attr('src',src);
}


</script>