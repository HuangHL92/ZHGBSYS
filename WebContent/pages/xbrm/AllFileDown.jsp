<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp"%>

<head>
	<style>

	</style>

	<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
</head>
	
<body>
	<odin:hidden property="downfile"/>
	<odin:hidden property="js0100s" /> <!-- 拟任免人选基本情况ID -->
	<div style="width: 25px;float: left;"></div>
	<div style="float: left;">
	<div style="height:50px;"></div>
		<table style="">
			<tr>
				<td>
					<label for="DY" style="font-size:18px;"><input type="checkbox" id="DY" name="fileLX" value="JS02">
					动议</label>
				</td>
				<td>
					<label for="KT" style="font-size:18px;"><input type="checkbox" id="KT" name="fileLX" value="JS99">
					考核和听取意见</label>
				</td>
			</tr>
			<tr>
				<td>
					<label for="MJ" style="font-size:18px;"><input type="checkbox" id="MJ" name="fileLX" value="JS19">
					民主推荐</label>
				</td>
				<td>
					<label for="KC" style="font-size:18px;"><input type="checkbox" id="KC" name="fileLX" value="JS14">
					组织考察</label>
				</td>
			</tr>
			<tr>
				<td>
					<label for="TL" style="font-size:18px;"><input type="checkbox" id="TL" name="fileLX" value="JS07">
					讨论决定</label>
				</td>
				<td>
					<label for="GS" style="font-size:18px;"><input type="checkbox" id="GS" name="fileLX" value="JS08">
					任前公示</label>
				</td>
			</tr>
			<tr><td></td><td></td></tr>
			<tr style="align:center;">
				<td></td>
				<td><odin:button text="确定" property="UpdateTitleBtn" handler="getNames"></odin:button></td>
			</tr>
		</table>
	</div>
</body>	

<script>
	var basePath = "<%=request.getContextPath()%>";
	
	$(function(){
		$("#js0100s").val(parent.Ext.getCmp("mnrm").initialConfig.js0100s);
		//alert(document.getElementById("a0000s").value);
	});
	
	Ext.onReady(function(){   
		//document.getElementById("panel_content").style.width = Ext.getCmp('gridMnrm').getWidth() + "px";
    });
	
	function getNames(){
		var fileLX = document.getElementsByName("fileLX");
		var fileLXNames = "";
		//console.log(fileLX);
		for(var i=0;i<fileLX.length;i++){
			if(fileLX[i].checked){
				fileLXNames = fileLXNames + fileLX[i].value + ",";
			}
		}
		if(fileLXNames.length!=0){
			fileLXNames = fileLXNames.substr(0,fileLXNames.length-1);
		}else{
			Ext.MessageBox.alert("提示","请选择导出模块!");
			return;
		}
		radow.doEvent("download",fileLXNames);
		//alert(fileLXNames);
	}
	function reloadTree(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
		window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
		ShowCellCover("","温馨提示","导出成功！");
		setTimeout(cc,3000);
	}
	function cc(){
		
	}
	function ShowCellCover(elementId, titles, msgs)
	{	
		Ext.MessageBox.buttonText.ok = "关闭";
		if(elementId.indexOf("start") != -1){
		
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
		        height:300,
				closable:false,
			//	buttons: Ext.MessageBox.OK,		
				modal:true,
				progress:true,
				wait:true,
				animEl: 'elId',
				increment:5, 
				waitConfig: {interval:150}
				//,icon:Ext.MessageBox.INFO        
			});
		}else if(elementId.indexOf("success") != -1){
				Ext.MessageBox.confirm("系统提示", msgs, function(but) {  
					
				}); 
		}else if(elementId.indexOf("failure") != -1){
				Ext.MessageBox.show({
					title:titles,
					msg:msgs,
					width:300,
					modal:true,
			        height:300,
					closable:true,
					//icon:Ext.MessageBox.INFO,
					buttons: Ext.MessageBox.OK		
				});
				/*
				setTimeout(function(){
						Ext.MessageBox.hide();
				}, 2000);
				*/
		}else {
				Ext.MessageBox.show({
					title:titles,
					msg:msgs,
					width:300,
					modal:true,
			        height:300,
					closable:true,
					//icon:Ext.MessageBox.INFO,
					buttons: Ext.MessageBox.OK		
				});
			}
	}
</script>

