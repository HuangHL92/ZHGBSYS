<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
<head>

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
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.Notice"%>
<%@page import="com.insigma.siis.local.business.entity.NoticeFileVo"%>
<%@ page language="java" import="java.util.*" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�޸�֪ͨ����</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}
</style>
<%
HBSession sess = HBUtil.getHBSession();
//֪ͨ����id
String id = request.getParameter("id");
//֪ͨ������� 
Notice notice = (Notice)sess.get(Notice.class, id);
request.setAttribute("notice", notice);

//��ѯ�����еĸ���
String sqlFlie = "select id,noticeId,fileName,fileUrl,fileSize from NoticeFile where noticeId ='"+id+"'";
List<NoticeFileVo> noticeFileList = sess.createSQLQuery(sqlFlie).setResultTransformer(Transformers.aliasToBean(NoticeFileVo.class)).list();
request.setAttribute("noticeFileList", noticeFileList);
%>

</head>
<%@include file="/comOpenWinInit.jsp" %>
<body>
<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/PublishFileServlet?method=updateNotice" enctype="multipart/form-data" >	
<odin:hidden  property="id" value="${notice.id}"/>
<odin:hidden  property="txt" value="${notice.text}"/>
<odin:hidden  property="fileUrl" value="${notice.fileUrl}"/>
<table id="tablef"  >
	<tr>
		<td>&nbsp;</td>		
	</tr>
	<tr>		
		<odin:textEdit property="title" label="����" width="400" value="${notice.title}" maxlength="40"/>
		<td align="right"><span style="font-size: 12;width: 50px;" >&nbsp;&nbsp;&nbsp;�ȼ�&nbsp;</span></td>
	   <td align="left">
	   <select id="secret" value="${notice.secret}" style="width: 80px">
	     <option value="1" <c:if test="${'1' eq notice.secret}">selected</c:if>></option>   
         <option value="2" <c:if test="${'2' eq notice.secret}">selected</c:if>><span style="font-size: 12;">һ��</span></option>   
         <option value="3" <c:if test="${'3' eq notice.secret}">selected</c:if>><span style="font-size: 12;">����</span></option>   
         <option value="4" <c:if test="${'4' eq notice.secret}">selected</c:if>><span style="font-size: 12;">����</span></option>
         </select>
	   </td>
	
	
	</tr>
    <tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td><span style="font-size: 12">��ǰ�ļ���</span></td>
		
		<td>
			<% for(Integer i=1;i<=noticeFileList.size();i++){ 
								
				int length = noticeFileList.size();
				NoticeFileVo noticeFile = new NoticeFileVo();
				if(length>=i){
					noticeFile = noticeFileList.get(i-1);
				}
				request.setAttribute("noticeFile", noticeFile);
			%>
				${noticeFile.fileName}
				<img alt="ɾ��" src="<%=request.getContextPath()%>/images/wrong.gif" onclick="deleteNoticeFile('${noticeFile.fileUrl}','${noticeFile.id}')" style="cursor:pointer;">
			<%} %>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
		</td>
		
	</tr>
	
	<tr>
		<odin:textEdit width="250" inputType="file" colspan="4"  property="excelFile" label="ѡ���ļ���"></odin:textEdit> 
		
	</tr>
    <tr>
		<td height="10"></td>
	</tr>
	<tr>
		<!-- 
		<td id="text">
			<script type="text/plain" id="myEditor" style="height:340px;width:700px;">
			</script> 
		</td>
		 -->
		 
		<odin:textarea property="a1701" label="" colspan='4' rows="18" ></odin:textarea>
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
						<odin:button text="�޸�" property="impBtn" handler="formSubmit" />
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

<script type="text/javascript">
//��ʼ�����ı��༭����� 
var um = UM.getEditor('myEditor');

Ext.onReady(function (){
	
	document.getElementById('a1701').value = document.getElementById('txt').value;
	
	
	//���ø��ı��༭���ĸ߶�
	var firstChild = $("#text>:first");
	firstChild.css('height','400px');
	
	var text = document.getElementById('txt').value;
	//var secret = document.getElementById('txt').value;
	
	//UM.getEditor('myEditor').execCommand('insertHtml', text);
	UM.getEditor('myEditor').setContent(text);
	
	
	
})

function doCloseWin(){
	parent.odin.ext.getCmp('updateWin').close();
}

function formSubmit(){
	
	var id = document.getElementById('id').value;
	var file = encodeURI(encodeURI(document.getElementById('excelFile').value));		//�ļ�
	var title = encodeURI(encodeURI(document.getElementById('title').value));			//����
	//var text = encodeURI(encodeURI(UM.getEditor('myEditor').getContent()));			//����ʽ�Ĵ��ı�����
	var text = encodeURI(encodeURI(document.getElementById("a1701").value));		//����ʽ�Ĵ��ı�����
	var secret = encodeURI(encodeURI(document.getElementById("secret").value));     //�ȼ�
	var fileUrl = encodeURI(encodeURI(document.getElementById('fileUrl').value));		//�ļ�
	
	text = text.replace(/&nbsp;/g, "uuiiooopphh");
	text = text.replace(/&/g, "hhhjjjkkklll");
	
	if(title !=""){
		
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=updateNotice&title='+title+'&file='+file+'&text='+text+'&id='+id+'&fileUrl='+fileUrl+'&secret='+secret,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'excelForm',
			success:function(){
				parent.odin.alert("����֪ͨ����ɹ�!");
				realParent.odin.ext.getCmp('noticeSetgrid').store.reload();
				parent.odin.ext.getCmp('updateWin').close();
				realParent.parent.gzt.window.location.reload();
			}
		});
		
	}else{
		parent.odin.alert("���������д!");
	}
	
}

//ɾ��֪ͨ���渽�� 
//encodeURI��������urlת�룬������Ĵ����������� 
function deleteNoticeFile(fileurl, id){
	
	//ͨ��ajax����ɾ��֪ͨ���渽�� 
	$.ajax({
		url:'<%=request.getContextPath()%>/PublishFileServlet?method=deleteNoticeFile',
		type:"GET",
		data:{
			"id":id,
			"filePath":encodeURI(fileurl)
		},
		success:function(){
			
			odin.alert("ɾ���ɹ�!");	
			location.reload();   //ɾ���ļ�֮��ˢ��ҳ�� 
		},
		error:function(){
			odin.alert("ɾ��ʧ��!");		
		}
	});
	
}

</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>