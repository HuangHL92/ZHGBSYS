<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html style="background-color: rgb(223,232,246);">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />

<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/RowExpander.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ExtJS/local/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/odin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<odin:head/>

<style>
font {
	font-family: "΢���ź�";
}
#start {
	position: relative;
	left: 90px;
	font-family: ΢���ź�;
}
#closeWin {
	position: relative;
	left: 180px;
	font-family: ΢���ź�;
}
</style>
</head>
<script type="text/javascript">
/* �ر� */
function gbWin(){
	var win = parent.parent.Ext.getCmp('ExcelImp'); 
	if(win){
		win.close();
	}
}

/* ��ʼ���� */
function startImport(){
	var unid = parent.document.getElementById("unid").value;//��ȡ���յ�unidֵ
	if(!unid){
		alert("����ѡ��Ҫ����ĵ�λ��");
		return;
	}
	
	var path = '<%=request.getContextPath()%>/HZBImportZzbAction.do?method=importByUnid&unid='+unid;
	
	parent.ShowCellCover('start','ϵͳ��ʾ','���ڵ���EXCEL�ļ�,��Ҫһ��ʱ��,�����Ե�...');
	Ext.Ajax.request({
		url: path,
        form:'data',
        callback: function (options, success, response) {
			if (success) {
				var result = response.responseText;
				if(result){
					result = result.substring(5,result.length-6);
					var json = eval('(' + result + ')');
					var data = json.data;
					for (var key in data) {
						if('success'==key){
							parent.Ext.MessageBox.hide();
							parent.Ext.Msg.alert('ϵͳ��ʾ',data.success,function(){
								gbWin();//�ر�ҳ��
								//ˢ��grid
								parent.realParent.Ext.getCmp('MGrid').store.reload();
							});
						}
						if('error'==key){
							parent.ShowCellCover('failure','������ʾ',data.error);
						}
					}
				}
            } 
        }
   });
}

</script>
<div style="width: 360px;height: 150px">
	<table style="width: 100%;">
		<tr>
			<td>
				<font style="font-size: 13px;">&nbspѡ���ϴ�Ҫ������ļ�</font>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<font style="font-size: 13px;">&nbsp1: �����Excel�ļ��ĸ�ʽ��������дģ��һ��,�����ܵ���!</font>
			</td>
		</tr>
	</table>	
	<br>
	<form id="data" name="data" method="post" enctype="multipart/form-data">
		<div>
			<font style="font-size: 13px;">&nbsp&nbspѡ��Ҫ������ļ���</font>
			<input id="import" type="file" style="width: 380px;" value="���.." name="ww"/>
			<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="��ʼ����" id="start" onclick="startImport()"/>
		</div>
	</form>
</div>

</html>

