<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=gbk"/>
    <title>UMEDITOR 完整demo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk"/>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<link href="themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="third-party/jquery.min.js"></script>
<script type="text/javascript" src="third-party/template.min.js"></script>
<script type="text/javascript" charset="gbk" src="umeditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="umeditor.min.js"></script>
<script type="text/javascript" src="lang/zh-cn/zh-cn.js"></script>

<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<body style="overflow-x:hidden">
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=addNotice" enctype="multipart/form-data" >	
<odin:hidden  property="id" />
<odin:hidden  property="id" />
<table id="tablef"  >
	<tr>
		<td>&nbsp;</td>
		
	</tr>
	<tr>
		<odin:textEdit property="title" label="标题" width="400" maxlength="40"/>
		<td align="right"><span style="font-size: 12;width: 50px;">&nbsp;&nbsp;&nbsp;等级&nbsp;</span></td>
	   <td>
	   <select id="secret">
	     <option value="1"></option>   
         <option value="2"><span style="font-size: 12;">一级</span></option>   
         <option value="3"><span style="font-size: 12;">二级</span></option>   
         <option value="4"><span style="font-size: 12;">三级</span></option>
         </select>
	   </td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<odin:textEdit width="400" inputType="file" property="excelFile" label="选择文件"></odin:textEdit> 		
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
    <tr>
		<odin:textarea property="a1701" label="" colspan="4" rows="22"></odin:textarea>				
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


var from = document.getElementsByName("commForm")
//alert(from.name);
for(var i=0;i<from.length;i++)
{
	from[i].id="commForm";
}


var um = UM.getEditor('myEditor');



Ext.onReady(function (){
	
	//设置富文本编辑器的高度
	var firstChild = $("#text>:first");
	firstChild.css('height','400px');
	
	 var oBox=document.getElementById('myEditor');
     //给myEditor的div加onkeydown事件
     oBox.onkeydown=function(){
    	var keynum;
   	    keynum = window.event ? window.event.keyCode : window.event.which;
   	    if(keynum == 8){  //BackSpace（退格）
   	     
   	    }
   	 	if(keynum == 13){  //Enter（回车）
   	 		var text = UM.getEditor('myEditor').getContent();			//带格式的纯文本内容
   	 		UM.getEditor('myEditor').setContent('<p><br/></p>', true);
   	    }
     };
	
})



function doCloseWin(){
	parent.odin.ext.getCmp('addWin').close();
}

function formSubmit(){
	
	var file = encodeURI(encodeURI(document.getElementById('excelFile').value));		//文件
	var title = encodeURI(encodeURI(document.getElementById('title').value));			//标题
	var secret = encodeURI(encodeURI(document.getElementById("secret").value));		//等级
	//var text = encodeURI(encodeURI(UM.getEditor('myEditor').getContent()));			//带格式的纯文本内容
	
	var text = encodeURI(encodeURI(document.getElementById("a1701").value));		//带格式的纯文本内容
	
	
	
	text = text.replace(/&nbsp;/g, "uuiiooopphh");
	text = text.replace(/&/g, "hhhjjjkkklll");
	
	
	if(title !=""){
		
		
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=addNotice&title='+title+'&file='+file+'&text='+text+'&secret='+secret,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'excelForm',
			success:function(){
				parent.odin.alert("新增通知公告成功!");
				realParent.odin.ext.getCmp('noticeSetgrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		parent.odin.alert("标题必须填写!");
	}
	
}

</script>


</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>