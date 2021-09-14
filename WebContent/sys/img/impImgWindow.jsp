<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.cm.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" method="post"  action="<%=request.getContextPath()%>/file/fileUploadServlet?method=impFileAndProcess" enctype="multipart/form-data" target="impFrame">	
<table align="center" width="96%">
	<tr>
		<td height="4" colspan="2"></td>
	</tr>
	<tr>
		<td><div class="x-form-item">文件选择<input type="file" id="normalFile" name="normalFile" onchange="imgPreView(this)" size="47"/></div></td>
		<td align="right">
			<fieldset id="previewArea" style="display: none;text-align: center;height: 172px;width: 132px;">
			<legend style="font-size: 12px">图片预览</legend>
			<img id="myImg" width="120" height="150" src="<%=request.getContextPath()%>/images/icon/emptyPerson.jpg" style="border:solid 1px #19B904;" onmouseover="radow.cm.mouseOverImg(this)"/>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td height="4" colspan="2"></td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<odin:button text="导入" property="impBtn" handler="formSubmit"></odin:button>
		</td>
	</tr>
	<tr>
		<td height="6"></td>
	</tr>
</table>
	
	<input type="hidden" name="businessClass" value="com.insigma.odin.framework.sys.img.ImgUploadAccess"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<iframe id="impFrame" name="impFrame" width="0" height="0"></iframe>

<script>
/** 首次载入列表数据开始 */
var page_url_params = [];
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function openChooseFileWin(){
	document.getElementById('normalFile').click();
}
function formSubmit(){
	var fileName = document.getElementById('normalFile').value;
	if(fileName!=""){
		var fileType = fileName.substr(fileName.length-4,4).toLowerCase();
		if(".gif,.jpg,.png,.bmp".indexOf(fileType)<0){
			odin.error("您选择的文件不是图片类文件，不能进行上传！");
			return;
		}
		//alert(document.getElementById('excelFile').value);
		odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		document.excelForm.submit();
	}else{
		odin.info('请选择文件之后再做导入处理！');
	}
}
function info(type,data){
	document.getElementById('normalFile').value='';
	odin.ext.get(document.body).unmask();
	businessData = data;
	var resData = odin.ext.decode(data);
	parent.document.getElementById('<%=request.getParameter("id")%>').value = resData.id;
	parent.document.getElementById('<%=request.getParameter("id")%>_img').src = contextPath+"/imgAction.do?method=loadPhotoById&id="+resData.id;
	doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('win_pup').hide();
	if(businessData!=""&&type==1){
		if(businessData.indexOf("\n")>=0){
			parent.odin.alert("解析数据失败，数据格式非法！");
			return;
		}
		if(typeof parent.resFuncImpFile != 'undefined'){
			parent.resFuncImpFile(odin.ext.decode(businessData));
		}
	}
}
function imgPreView(input){
	if(input.files && input.files[0]){
		var reader = new FileReader();
		var img = odin.ext.get("myImg");
		reader.onloadend = function(e){
			var win = parent.odin.ext.getCmp("win_pup");
			win.setHeight(254);
			if(Ext.isIE){
				win.setWidth(601);
			}
			img.dom.setAttribute("src",e.target.result);
			odin.ext.get("previewArea").setDisplayed("");
		}
		reader.readAsDataURL(input.files[0]);
	}
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>