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


 <odin:groupBox property="wjxx" title="家庭成员" >
<table border="0" id="myform2" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
    <tr>
	 <odin:textEdit property="a0101"  label="姓名"  required="true" />
	 <odin:textEdit property="a0184"  label="身份证号"   />
	 <odin:hidden property="oid"/>
	 
    </tr>
    <tr>
	 <odin:textEdit property="a0192a"  label="工作单位及职务"  />
	 <odin:select2 property="familyType"  label="家庭成员关系"  data="['子女','子女'],['配偶','配偶']" />
	 
    </tr>
</table>
</odin:groupBox> 
<div id="toolDiv"></div>
<odin:groupBox property="fbinfo" title="人员列表" >
 <div id="gridDiv" style="width: 100%;height:200px;overflow: auto;">
	<odin:editgrid2 property="familyGrid"   height="200"
			hasRightMenu="false" autoFill="true"  url="/">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="oid"/>
					<odin:gridDataCol name="p_oid"/>
					<odin:gridDataCol name="family_type"/>
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0184"/>
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
					<odin:gridEditColumn2 dataIndex="oid" width="10" editor="text" header="主键" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="p_oid" width="10" editor="text" header="p_oid" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="a0101" width="30" header="姓名" editor="text" edited="false"  sortable="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0184" width="50" header="身份证" editor="text" edited="false"   sortable="false" align="center" />
					<odin:gridEditColumn2 dataIndex="a0192a" width="80" header="单位职务" editor="text" edited="false"  sortable="false" align="center"  />
					<odin:gridEditColumn2 dataIndex="family_type" width="80" header="家庭成员关系" editor="text" edited="false"  sortable="false" align="center"  isLast="true"   />
					
				</odin:gridColumnModel>
			</odin:editgrid2>
</div>
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
								<odin:button text="保存" property="save" ></odin:button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:fill />
	
	<odin:buttonForToolBar text="删除" icon="images/delete.png"  handler="deleteColumn" id="deleteColumn" isLast="true"/>
	
	
</odin:toolBar>
<script>



function clearValue(){
	document.getElementById("a0101").value="";
	document.getElementById("a0184").value="";
	document.getElementById("a0192a").value="";
	document.getElementById("familyType").value="";
	document.getElementById("oid").value="";
	
}


function deleteColumn(){
	var gzGrid = odin.ext.getCmp('familyGrid');
	var store = gzGrid.getStore();
	var sm = gzGrid.getSelectionModel();
	var Selections = sm.getSelections();//获取当前行
	if(Selections.length==0){
		alert("请选择行");
		return;
	}
	console.log(Selections);
	var oids="";
	$.each(Selections,function (i, Selection){
		oids+="'"+Selection.data.oid+"',";
	});
	oids=oids.substr(0,oids.length-1);
	radow.doEvent("deleteFamily",oids);
}





</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>