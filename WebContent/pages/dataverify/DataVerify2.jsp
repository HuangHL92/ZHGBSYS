<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>

<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
 #daoru{position:relative;left:20px;width:790} 
</style>
<title>导入文件</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile" enctype="multipart/form-data" >	
<odin:hidden  property="orginfo" />
<odin:hidden  property="valueimp" />
<odin:hidden  property="uuid" />
<odin:hidden  property="filename" />
<odin:hidden  property="count" />
<odin:hidden  property="type11" />
<odin:groupBox title="数据导入" property="daoru">
<table>
<tr>
	<td>
	<odin:textEdit width="700" inputType="file" colspan="3"  property="excelFile" label="选择文件:" onchange="return FileUpload_onselect()" ></odin:textEdit> 
	</td>
	<%-- <td align="center" valign="middle">
	<odin:button text="上传" property="impwBtn" handler="formupload" /> 
	<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn">
	</td> --%>
</tr>
	
</table>	
</odin:groupBox>
	
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<script>
Ext.onReady(function (){
//	Ext.getCmp('impBtn').setDisabled(true); 
})
	//ajax 上传
	function formupload(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='3'){
				alert("不是ZB3或HZB格式文件，不能导入");
				parent.location.reload();
				return;
			}
			//odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			parent.Ext.Msg.wait('正在上传，请稍候...','系统提示：');
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFile',
				isUpload:true,
				form:'excelForm',
				success:function(){
					parent.Ext.Msg.hide();
				},
				failure : function(){
					parent.Ext.Msg.hide();
				}
			});
			//document.excelForm.submit();
			///odin.ext.get(document.body).unmask(); 
		}else{
			odin.info('请选择文件之后再做导入处理！');
		}
	}

/** 首次载入列表数据开始 */
Ext.onReady(function(){
	var impBtnText = "<%=(request.getParameter("impBtnText")==null?"":request.getParameter("impBtnText"))%>";
	if(impBtnText!=""){
		odin.ext.getCmp('impBtn').setText(impBtnText);
	}
});
function openChooseFileWin(){
	document.getElementById('excelFile').click();
}
function formSubmit2(){
 	var filename = document.getElementById('filename').value;
 	var uuid = document.getElementById('uuid').value;
 	var v = odin.ext.get('valueimp').getValue();
 	if(uuid==''||filename==''){
 		alert('请先进行“上传检查”。');
 		return;
 	}
 	if(v=='2'){
		alert("机构验证不通过，不能导入。");
		return;
	} else if(v=='3'){
		alert("不是ZB3或HZB格式文件，不能导入。");
		return;
	} else if(v==''){
		alert("上传检查未通过。");
		return;
	}
	var count = document.getElementById('psncount').value;
	var count1 = document.getElementById('count').value;
	var type11 = document.getElementById('type11').value;
	var count2 =  (Number(count)+Number(count1));
	if(type11 == "MYSQL" && count2>100000){
		alert("软件内已经存在"+count1+"人的数据，当前数据包内包含"+count+"人的数据，单机版软件若储存超过10万人数据会导致运行缓慢");
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		////odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param,succfun1,failfun1,false,false);
		///odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param);
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImp&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
///					realParent.odin.ext.getCmp('simpleExpWin').store.reload();
					realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
					///FileUpload_onselect();
					///Ext.Msg.alert('upfile','文件上传成功！');
				}
			});
///		realParent.odin.ext.getCmp('simpleExpWin').hide();
///		parent.doOpenPupWin(contextPath + "/pages/repandrec/local/KingbsWinfresh1.jsp&id=" + uuid, "导入详情", 550, 300, null);
		realParent.$h.showWindowWithSrc('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'导入详情',500,150);
			
	}else{
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		////odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param,succfun1,failfun1,false,false);
		///odin.Ajax.request(contextPath+'/UploadFileServlet?method=zzbFileImp', param);
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImp&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
///					realParent.odin.ext.getCmp('simpleExpWin').store.reload();
					realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
					///FileUpload_onselect();
					///Ext.Msg.alert('upfile','文件上传成功！');
				}
			});
///		realParent.odin.ext.getCmp('simpleExpWin').hide();
///		parent.doOpenPupWin(contextPath + "/pages/repandrec/local/KingbsWinfresh1.jsp&id=" + uuid, "导入详情", 550, 300, null);
		realParent.$h.showWindowWithSrc('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'导入详情',500,150);

	}


}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
	/*
	if(type==1){
		odin.info('数据已成功上传并处理完毕！',doCloseWin);
	}else if(type==2){
		odin.info('失败！',doCloseWin);
	}else if(type==3){
		odin.info('业务处理发生异常！',doCloseWin);
	}else if(type==4){
		odin.info('调用业务类异常！',doCloseWin);
	}
	*/
	doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	parent.odin.ext.getCmp('simpleExpWin').hide();
	if(businessData!=""&&type==1){
		if(businessData.indexOf("\n")>=0){
			parent.odin.alert("解析数据失败，数据格式非法！");
			return;
		}
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
function doCloseWin1(type){
	//odin.ext.get(document.body).mask('正在刷新页面......', odin.msgCls);
	//parent.clickquery();
	parent.odin.ext.getCmp('simpleExpWin11111').close();
	//parent.resFuncImpExcel(odin.ext.decode(businessData));
}

function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
    var param = {};
	param.path = path;
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck', param,succfun,failfun,false,false);
}

	function succfun(response){
 		//构建一个数组
 		//Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		document.getElementById('orginfo').value=response.data.orginfo;
 		document.getElementById('valueimp').value=response.data.valueimp;
 		
 		//直接出发提交
 		formupload();
 	}
 	function failfun(response){
 		alert("操作失败");
 	}
 	
 	function succfun1(){
 		//构建一个数组
 	}
 	function failfun1(){
 		
 	}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>