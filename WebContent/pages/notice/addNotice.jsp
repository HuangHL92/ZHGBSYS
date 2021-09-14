<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=gbk"/>
    <title>UMEDITOR ����demo</title>
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
		<odin:textEdit property="title" label="����" width="400" maxlength="40"/>
		<td align="right"><span style="font-size: 12;width: 50px;">&nbsp;&nbsp;&nbsp;�ȼ�&nbsp;</span></td>
	   <td>
	   <select id="secret">
	     <option value="1"></option>   
         <option value="2"><span style="font-size: 12;">һ��</span></option>   
         <option value="3"><span style="font-size: 12;">����</span></option>   
         <option value="4"><span style="font-size: 12;">����</span></option>
         </select>
	   </td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<odin:textEdit width="400" inputType="file" property="excelFile" label="ѡ���ļ�"></odin:textEdit> 		
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
						<odin:button text="����" property="impBtn" handler="formSubmit" />
					</td>
					<td width="60px"></td>
					<td>
						<odin:button text="ȡ��" property="cancelBtn" handler="doCloseWin"></odin:button>
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
	
	//���ø��ı��༭���ĸ߶�
	var firstChild = $("#text>:first");
	firstChild.css('height','400px');
	
	 var oBox=document.getElementById('myEditor');
     //��myEditor��div��onkeydown�¼�
     oBox.onkeydown=function(){
    	var keynum;
   	    keynum = window.event ? window.event.keyCode : window.event.which;
   	    if(keynum == 8){  //BackSpace���˸�
   	     
   	    }
   	 	if(keynum == 13){  //Enter���س���
   	 		var text = UM.getEditor('myEditor').getContent();			//����ʽ�Ĵ��ı�����
   	 		UM.getEditor('myEditor').setContent('<p><br/></p>', true);
   	    }
     };
	
})



function doCloseWin(){
	parent.odin.ext.getCmp('addWin').close();
}

function formSubmit(){
	
	var file = encodeURI(encodeURI(document.getElementById('excelFile').value));		//�ļ�
	var title = encodeURI(encodeURI(document.getElementById('title').value));			//����
	var secret = encodeURI(encodeURI(document.getElementById("secret").value));		//�ȼ�
	//var text = encodeURI(encodeURI(UM.getEditor('myEditor').getContent()));			//����ʽ�Ĵ��ı�����
	
	var text = encodeURI(encodeURI(document.getElementById("a1701").value));		//����ʽ�Ĵ��ı�����
	
	
	
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
				parent.odin.alert("����֪ͨ����ɹ�!");
				realParent.odin.ext.getCmp('noticeSetgrid').store.reload();
				parent.odin.ext.getCmp('addWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		parent.odin.alert("���������д!");
	}
	
}

</script>


</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>