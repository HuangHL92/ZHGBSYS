<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<%
	String xjqy=request.getParameter("xjqy");
%>
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection" enctype="multipart/form-data" >	
<odin:hidden  property="xjqy" />
<table id="tablef"  >
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:select property="hylx" label="会议类型" data="['01','党代会'],['02','人代会'],['03','政协会']" width="100" />
		
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit property="hymc" label="会议名称" width="400" maxlength="100"/>
		
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit property="sj" label="时间" width="400" maxlength="100"/>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<%-- <tr>
		<odin:select property="wcbz" data="['01','未完成'],['02','推进中'],['03','已完成']" label="结果" width="100" />
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr> --%>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFile" label="选举材料文件" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFilej" label="选举结果文件" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFileg" label="会议材料文件" ></odin:textEdit> 
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">
			<table>
				<tr>
					<td width="120px"></td>
					<td>
						<odin:button text="新增" property="impBtn" handler="formSubmit" />
					</td>
					<td width="60px"></td>
					<td>
						<odin:button text="取消" property="cancelBtn" handler="doCloseWin"></odin:button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
	
</form>

<script>
Ext.onReady(function (){
})



function doCloseWin(){
	parent.odin.ext.getCmp('addWin').close();
}

function formSubmit(){
	
	var file = document.getElementById('excelFile').value;
	var file1 = document.getElementById('excelFileg').value;
	var file2 = document.getElementById('excelFilej').value;
	
	if(file !=""){
		var hylx = encodeURI(document.getElementById('hylx').value);
		var hymc = encodeURI(document.getElementById('hymc').value);
		var xjqy = '<%=xjqy %>';
		var sj = encodeURI(document.getElementById('sj').value);
		//var wcbz = encodeURI(document.getElementById('wcbz').value);
		//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addMeetingElection&hylx='+encodeURI(hylx)+'&hymc='+encodeURI(hymc)+'&xjqy='+encodeURI(xjqy)+'&sj='+encodeURI(sj),
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'excelForm',
			success:function(){
				parent.odin.info("新增大会选举成功!");
				//parent.radow.doEvent("memberGrid.dogridquery");
				realParent.odin.ext.getCmp('memberGrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		odin.info('文件必须上传！');
	}
	
}




</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>