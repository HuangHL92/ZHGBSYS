<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />

<%@include file="/comOpenWinInit.jsp"%>

<style>
	#attachPhoto {
		width: 300px;
		height: 20px;
	}

	#savePic {
		width: 70px;
		height: 35px;
	}
</style>
	<table >
	<tr>
		<td height="20px" colspan="2"></td>
	</tr>
	<tr>
		<td>
		<span style="font-size:12px;margin-left:5px;" >�ļ�ѡ��</span>
		</td>
		<td>
			<iframe width="390px" height="35px" src="<%=request.getContextPath() %>/pages/publicServantManage/UpLoadPic.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" id="if_pic"></iframe>
		</td>
	</tr>
	<tr align="left">
		<div>
		<odin:NewDateEditTag property="updateTime" label="��ƬӦ��ʱ��" maxlength="8" width="390"  required="true"/>
		</div>
	</tr>
	</table>
	<table>
	<tr>
	<td width="170px"></td>
	<td align="center">
		<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" handler="saveUserPic" property="ti" />
	</td>
	<td width="20px"></td>
	<td align="center">
		<odin:button text="�滻ʧ����Ա�鿴" handler="searcherror" property="photoerror" />
	</td>
 	</tr>
 </table>
	<%-- <div>
		<div>
			<div><iframe width="380px" height="35px" src="<%=request.getContextPath() %>/pages/publicServantManage/UpLoadPic.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" id="if_pic"></iframe></div>
			<odin:NewDateEditTag property="updateTime" label="�滻ʱ��"  maxlength="8" required="true"></odin:NewDateEditTag>
			<div><input type="button" id="savePic" class="yellowbutton" name="savePic" title="������Ƭ" value="������Ƭ" onclick="javascript:saveUserPic();" /></div>
		</div>
	</div> --%>
<odin:hidden property="save_realpath"/>
<script type="text/javascript">

	function doHandleDate() {
		var myDate = new Date();
		var tYear = myDate.getFullYear();
		var tMonth = myDate.getMonth();
		var tDay = myDate.getDate();

		var m = tMonth + 1;
		if (m.toString().length == 1) {
			m = "0" + m;
		}
		return tYear +""+ m;
	}
	$("#updateTime_1").val(doHandleDate());
	$("#updateTime").val(doHandleDate());
function saveUserPic() {
	var savePath = document.frames["if_pic"].document.getElementById('attachPhoto').value;
	//�ж��ϴ��ĸ����Ƿ�ΪͼƬ
	if ("" == savePath) {
		alert("��ѡ���ϴ��ļ���");
		return;
	}
	var fileType = getFileType(savePath);
	//�ж��ϴ��ĸ����Ƿ�ΪͼƬ
	if("zip"!=fileType){
		document.frames["if_pic"].document.getElementById('attachPhoto').value = "";
		alert("���ϴ�zip��ʽ�ļ�");
		return;
	}
	document.getElementById('save_realpath').value = savePath;
	$h.confirm('ϵͳ��ʾ����Ҫ��','���β��������滻��ǰԭ����Ƭ������ʾ���Ƿ����ִ���滻��<br/>'+
			'ѡ���ǡ����滻������Ƭ������ʾ��<br/>ѡ�񡰷񡱣��������κβ���',200,function(id){
		//alert(id);
		if(id=='ok'){
			//Ext.Msg.wait("���ڵ��룬���Ժ�...","ϵͳ��ʾ");
			document.getElementById('if_pic').contentWindow.formSubmit();
		}else if(id=='cancel'){

		}
	});
}

/**
 * �ж��ϴ����ļ��Ƿ�Ϊ��Ƭ��ʽ
 */
function formatPhoto() {
	var filePath = $("#attachPhoto").val();
	if("" != filePath){
		var fileType = getFileType(filePath);
		//�ж��ϴ��ĸ����Ƿ�Ϊzip��ʽ
		if("zip"!=fileType){
			$("#attachPhoto").val("");
			alert("���ϴ�zip��ʽ�ļ�");
		}
	}
}

/**
 * ��ȡ�ļ�����׺
 */
function getFileType(filePath){
	var startIndex = filePath.lastIndexOf(".");
	if(startIndex != -1) {
		return filePath.substring(startIndex+1, filePath.length).toLowerCase();
	} else {
		return "";
	}
}
function searcherror(){
	$h.openPageModeWin('importimpPhotosWins','pages.publicServantManage.PhotoError','δ�滻��Ƭ��Ϣ',565,407,'',contextPath);
}

</script>
