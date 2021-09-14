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
		<span style="font-size:12px;margin-left:5px;" >文件选择</span>
		</td>
		<td>
			<iframe width="390px" height="35px" src="<%=request.getContextPath() %>/pages/publicServantManage/UpLoadPic.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" id="if_pic"></iframe>
		</td>
	</tr>
	<tr align="left">
		<div>
		<odin:NewDateEditTag property="updateTime" label="照片应用时间" maxlength="8" width="390"  required="true"/>
		</div>
	</tr>
	</table>
	<table>
	<tr>
	<td width="170px"></td>
	<td align="center">
		<odin:button text="&nbsp;&nbsp;替&nbsp;&nbsp;换&nbsp;&nbsp;" handler="saveUserPic" property="ti" />
	</td>
	<td width="20px"></td>
	<td align="center">
		<odin:button text="替换失败人员查看" handler="searcherror" property="photoerror" />
	</td>
 	</tr>
 </table>
	<%-- <div>
		<div>
			<div><iframe width="380px" height="35px" src="<%=request.getContextPath() %>/pages/publicServantManage/UpLoadPic.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" id="if_pic"></iframe></div>
			<odin:NewDateEditTag property="updateTime" label="替换时间"  maxlength="8" required="true"></odin:NewDateEditTag>
			<div><input type="button" id="savePic" class="yellowbutton" name="savePic" title="保存照片" value="保存照片" onclick="javascript:saveUserPic();" /></div>
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
	//判断上传的附件是否为图片
	if ("" == savePath) {
		alert("请选择上传文件！");
		return;
	}
	var fileType = getFileType(savePath);
	//判断上传的附件是否为图片
	if("zip"!=fileType){
		document.frames["if_pic"].document.getElementById('attachPhoto').value = "";
		alert("请上传zip格式文件");
		return;
	}
	document.getElementById('save_realpath').value = savePath;
	$h.confirm('系统提示（重要）','本次操作将会替换当前原有照片进行显示，是否继续执行替换？<br/>'+
			'选择“是”，替换已有照片进行显示；<br/>选择“否”，不进行任何操作',200,function(id){
		//alert(id);
		if(id=='ok'){
			//Ext.Msg.wait("正在导入，请稍后...","系统提示");
			document.getElementById('if_pic').contentWindow.formSubmit();
		}else if(id=='cancel'){

		}
	});
}

/**
 * 判断上传的文件是否为照片格式
 */
function formatPhoto() {
	var filePath = $("#attachPhoto").val();
	if("" != filePath){
		var fileType = getFileType(filePath);
		//判断上传的附件是否为zip格式
		if("zip"!=fileType){
			$("#attachPhoto").val("");
			alert("请上传zip格式文件");
		}
	}
}

/**
 * 获取文件名后缀
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
	$h.openPageModeWin('importimpPhotosWins','pages.publicServantManage.PhotoError','未替换照片信息',565,407,'',contextPath);
}

</script>
