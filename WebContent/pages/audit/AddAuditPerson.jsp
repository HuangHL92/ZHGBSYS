<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
	String ctxPath = request.getContextPath();
%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<%=ctxPath%>/layui/css/layui.css"/>
<script src="<%=request.getContextPath()%>/js/msgtip.js"></script>
<script src="<%=request.getContextPath()%>/js/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/console.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript"src="<%=ctxPath%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=ctxPath%>/basejs/ext/ext-all.js"> </script>
<script src="<%=ctxPath%>/basejs/odin.js"> </script>

<script src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
<title>添加人员</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}

#popDiv {
   display: none;
   width: 765px;
   height:150px;
   overflow-y:scroll;
   border: 1px #74c0f9 solid;
   background: #FFF;
   position: absolute;
   left: 97px;
   margin-top:76px;
   color: #323232;
   z-index:321;
}   

div#bottomDiv .x-btn {
    background-color: #2196f3!important;
    border-radius: 30px;
    width: 156px!important;
}
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<body style="overflow-x:hidden">
<odin:base>
 <odin:groupBox property="wjxx" title="人员信息" >
<table border="0" id="myform2" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
    
    <tr>
	 <odin:textEdit property="a0101"  label="姓名"  required="true" />
	 <odin:textEdit property="a0184"  label="身份证号"   />
    </tr>
    <tr>
	 <odin:textEdit property="a0192a"  label="工作单位及职务"  />
	 
	 
    </tr>
</table>
</odin:groupBox> 
 <div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td>
								<odin:button text="清除" property="clear"  handler="clearValue"></odin:button>
							</td>
							<td>
								<odin:button text="保存" property="save" handler="savePerson" ></odin:button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
<script>



function clearValue(){
	document.getElementById("a0101").value="";
	document.getElementById("a0184").value="";
	document.getElementById("a0192a").value="";
	
	
}


function savePerson(){
	var a0101=document.getElementById("a0101").value;
	var a0184=document.getElementById("a0184").value;
	var a0192a=document.getElementById("a0192a").value;
	if(a0101==''||a0184==''){
		alert("请填写姓名和身份证");
		return
	}
	var uuid=guid();
	var data={oid:uuid,a0000:'',a0101:a0101,a0184:a0184,a0192a:a0192a};
	realParent.doAddColumnData(data);
	parent.odin.ext.getCmp('AddAuditPerson').close();
}

function  guid() {
	function  S4() {
	    return  (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	 }
    return  (S4()+S4()+ "-" +S4()+ "-" +S4()+ "-" +S4()+ "-" +S4()+S4()+S4());
}


</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>