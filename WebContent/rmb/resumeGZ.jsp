<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.AddZHGBRmbPageModel"%>
<%@page import="java.io.Reader"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<script src="jquery-1.7.2.min.js"> </script>

<%
String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");

HBSession sess = HBUtil.getHBSession();
Object obj = sess.createSQLQuery("select a1701 from a01 where a0000 = '"+a0000+"'").uniqueResult(); 
java.sql.Clob clob = (java.sql.Clob)obj;
String a1701 = "";
if(clob!=null){
	Reader inStream = clob.getCharacterStream();
	char[] c = new char[(int) clob.length()];
	inStream.read(c);
	//data�Ƕ�������Ҫ���ص����ݣ�������String
	a1701 = new String(c);
	inStream.close();
}
/* 
String a1701Show = AddZHGBRmbPageModel.formatJL(a1701,new StringBuffer());
System.out.println(a1701Show); */
%>
<script type="text/javascript">

</script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.all.js"> </script>
<!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��-->
<!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������-->
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/lang/zh-cn/zh-cn.js"></script>

<style type="text/css">
.indentclass {

    padding-left: 9em!important;
	text-indent: -9em!important;

}
</style>
</head>
<input id="a0000" type="hidden" value="<%=a0000 %>">
<input id="jl" type="hidden" value="<%=a1701 %>">
<body>

	<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
	<button onclick="savejl()" style="margin-left: 45%">��&nbsp;&nbsp;��</button>
	<!-- <button onclick="appendContent()">׷������</button> -->
<script type="text/javascript">
//����ʹ�ù�������getEditor���������ñ༭��ʵ���������ĳ���հ������øñ༭����ֱ�ӵ���UE.getEditor('editor')�����õ���ص�ʵ��
var ue = UE.getEditor('editor');
//alert(ue);
$(function(){
	//��ü���
	setTimeout('setContent()',500);
	
	
}); 

function savejl(){
	var text = ue.getPlainTxt();
	var a0000 = document.getElementById('a0000').value;
	//alert(text);alert(a0000);
	var param={"text":text,"a0000":a0000};
	//console.log(param);
	$.ajax({
	      type: "POST",
	      url:"<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=saveJL",
	      data: param ,//$('#excelForm').serialize(),// ���formid
	      error: function(request) {
	    	  alert("��������ʧ�ܣ�");
	      },
	      success: function(data) {
	          alert("��������ɹ���");
	      }
	}); 
}

function setContent(){
	var a1701 = document.getElementById("jl").value;
	var pas = a1701.split(/\r|\n|\r\n/g);
	//console.log(pas)
	//ue.setContent(pas, true);
	ue.setContent(pas[0], false);
	for(var i=1;i<pas.length;i++){
		//console.log(pas[i])
		ue.setContent(pas[i], true);
	}
}
function save(){return;
	//��������
	var resume = document.getElementById("resume").value;
	//���ø�ҳ�淽�� 
	realParent.setResume(resume);
	//�رձ�ҳ�� 
	parent.odin.ext.getCmp('resumeWin').close();
}
function appendContent() {
	ue.setContent('<p>11</p>', true);
}


var cce;
ue.addListener('selectionchange',function(a,b,c){
	clearTimeout(cce);
	cce = setTimeout(function() {
		changeCss();
	}, 300);
  //  console.log("ѡ���Ѿ��仯��");
});


function changeCss(){
	var iwin = document.getElementById("ueditor_0").contentWindow;
	var pnodes = iwin.$("#bodyContent p");
	///^[0-9 ]{4}[\. ��][0-9| ]{2}[\-��-]{1,2}[[0-9 ]{4}[\. ��][0-9 ]{2}[ ]]{0,1}/
	//console.log(iwin.$)
	 //console.log(pnodes.length)
	// console.log(c);
	pnodes.each(function(){
		var text = $(this).text();
		
		if(text.match(/^[0-9 ]{4}[\. ��][0-9| ]{2}[\-��-]{1,2}[[0-9 ]{4}[\. ��][0-9 ]{2}[ ]{2}]{0,1}/)){
			$(this).attr('style','text-indent: -9em!important');
			
		}else{
			$(this).css('text-indent','');
		}
	});
}

//

</script>
</body>
</html>
