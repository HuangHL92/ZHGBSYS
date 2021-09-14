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
<body style="overflow-y:hidden;overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=zzbFilePsn" enctype="multipart/form-data" >	
<odin:hidden  property="valueimp" />
<odin:hidden  property="uuid" />
<odin:hidden  property="filename" />
<odin:hidden  property="count1" />
<odin:hidden  property="type1" />
<odin:hidden property="gjgss" />

<table id="tablef"  >
	<tr>
		
	</tr>
	<tr>
		<odin:textEdit width="416" inputType="file" colspan="4"  property="excelFile" label="选择文件" onchange="return FileUpload_onselect()" ></odin:textEdit> 
	</tr>
	<tr>
		<odin:textEdit property="packagetype" readonly="true" label="数据包类型" ></odin:textEdit>
		<odin:textEdit property="packagetime" readonly="true" label="数据包生成时间"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="psncount" readonly="true" label="包含人数" />
		<odin:textEdit property="gjgs"  readonly="true" label="导入格式" ></odin:textEdit>
	</tr>
	 
	<tr>
		<odin:textarea property="orginfo" rows="3" colspan="4" cols="4" style="width:416;" readonly="true" readonly="true"  label="数据包情况" />
	</tr>
	
</table>
<table>
				<tr>
					<odin:select2 property="a0165" codeType='ZB130' label="管理人员类别:" ></odin:select2>
					<td><font style="font-size: 12">及以上将不会被更新</font></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" checked="checked" id="fxz" /><span style="font-size: 12">非现职人员</span></td>
				</tr>
				<tr>
					<td></td><td></td><td></td>
					<td>
						<odin:button text="导入临时库" property="impBtn" handler="formSubmit2" />
					</td>
					
				</tr>
			</table>
	<input type="hidden" name="businessClass" value="<%=(request.getParameter("businessClass")==null?"":request.getParameter("businessClass"))%>"/>
	<input type="hidden" name="businessParam" value="<%=(request.getParameter("businessParam")==null?"":request.getParameter("businessParam"))%>"/>
	
</form>

<script>
Ext.onReady(function (){
	Ext.getCmp('impBtn').setDisabled(true); 
})
	//ajax 上传
	/*odin.ext.get('btn').on('click',function(){
		alert();
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/UploadHelpFileServlet?method=zzbFile',
			isUpload:true,
			form:'excelForm',
			success:function(){
				FileUpload_onselect();
			}
		});
	});
	odin.ext.get('btn').on('click',function(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='2'){
				alert("验证不通过，不能导入!");
				return;
			} else if(v=='3'){
				alert("只支持HZB格式文件，不能导入");
				return;
			} else if(v==''){
				alert("请选择文件并检查");
				return;
			}
			odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			document.excelForm.submit();
		}else{
			odin.info('请选择文件之后再做导入处理！');
		}
	});*/
	function formupload(){
		if(document.getElementById('excelFile').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='3'){
				alert("只支持HZB、7z格式文件，不能导入");
				return;
			}
			odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFilePsn',
				isUpload:true,
				form:'excelForm',
				success:function(){
				}
			});
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
 	var filename=document.getElementById('filename').value;
 	var uuid= document.getElementById('uuid').value;
 	var v = odin.ext.get('valueimp').getValue();
 	if(uuid==''||filename==''){
 		alert('请先进行“上传检查”。');
 		return;
 	}
 	if(v=='2'){
		alert("验证不通过，不能导入。");
		return;
	} else if(v=='3'){
		alert("只支持HZB、7z、zip格式文件，不能导入。");
		return;
	} else if(v==''){
		alert("上传检查未通过。");
		return;
	}
	
	var count2 = document.getElementById("count1").value;
	var type2 = document.getElementById("type1").value;
	var psncount1 = document.getElementById("psncount").value;
	var a0165 = document.getElementById('a0165').value;
	var fxz = document.getElementById('fxz').checked;
	if(type2 == "MYSQL" && Number(count2)+Number(psncount1)>100000){
		alert("软件内已经存在"+count2+"人的数据，当前数据包内包含"+psncount1+"人的数据，单机版软件若储存超过10万人数据会导致运行缓慢");
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/ZjUploadFileServlet?method=zzbFileImpPsn&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename,'a0165':a0165,'fxz':fxz},
				success:function(){
					realParent.odin.ext.getCmp('MGrid').store.reload();
					parent.Ext.getCmp(subWinId).close(); 
				}
			});
		realParent.$h.openWinMin('simpleExpWin4','pages.repandrec.local.KingbsWinfresh&initParams='+ uuid,'详情窗口',500,240,uuid,'<%=request.getContextPath()%>');
	
	}else{
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/ZjUploadFileServlet?method=zzbFileImpPsn&uuid'+uuid+'&filename'+filename,
				params : {'uuid':uuid,'filename':filename,'a0165':a0165,'fxz':fxz},
				success:function(){
					realParent.odin.ext.getCmp('MGrid').store.reload();
					parent.Ext.getCmp(subWinId).close();
				}
			});
		realParent.$h.openWinMin('simpleExpWin4','pages.repandrec.local.KingbsWinfresh&initParams='+ uuid,'详情窗口',500,240,uuid,'<%=request.getContextPath()%>');
		}
	}
function info(type){
	document.getElementById('excelFile').value='';
	odin.ext.get(document.body).unmask();
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
	parent.odin.ext.getCmp('simpleExpWin2').close();
}

function FileUpload_onselect(){   
    var path=document.getElementById('excelFile').value;
    var param = {};
	param.path = path;
	param.isPerson = '1';
    odin.Ajax.request(contextPath+'/dataverify/DataVerifyAction.do?method=doCheck3', param,succfun,failfun,false,false);
}

	function succfun(response){
 		//构建一个数组
 		Ext.getCmp('orginfo').setValue(response.data.orginfo);
 		document.getElementById('valueimp').value=response.data.valueimp;
 		
 		//直接触发提交
 		formupload();
 	}
 	function failfun(rm){
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