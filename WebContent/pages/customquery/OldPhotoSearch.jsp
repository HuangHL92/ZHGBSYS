<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@ page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
  

<style>
.x-panel-header{
border: 0px;

}
.x-toolbar span{
	font: bold;
}

#area1 {
	width: 100%;
	height: 100%;
	
}
#area1-2 {
	width: 460px;
}
#photos{
        width: 100%;
        height: 450px;
      }
#photos ul li{
	display: inline;
}

.buttonzdy{
    background-color: #2193EA;
  }
 .buttonzdyhover{
  background-color: #fff;
  
  }
</style>

<div id="area1">
<%-- <div id="tooldiv" style="width:100%;"></div>
	<odin:toolBar property="btnToolBar" applyTo="tooldiv">
		<odin:textForToolBar text=""></odin:textForToolBar>
		<odin:fill/>
		<odin:buttonForToolBar id="expBtn"  text="&nbsp&nbsp&nbsp&nbsp选&nbsp&nbsp&nbsp&nbsp中&nbsp&nbsp&nbsp&nbsp" icon="images/icon/exp.png" handler="getChoose" isLast="true" />
	</odin:toolBar> --%>
<div style="width:98%;margin-left: 1%">
<odin:groupBox property="allOldPhoto" title="旧照片列表">
	<div id="photos">
		<!-- <ul>
			<li onclick="genUnRoot('1')" id="sjjg_1" style="width:25%" class="buttonzdyhover">
                  <a>
                    <div style="text-align:center;">
                     <img src="./picCut/image/photo.jpg" />
                     <p>2009年11月</p>
                    </div>
                  </a>
              </li>
              <li onclick="genUnRoot('2')" id="sjjg_2" style="width:25%" class="buttonzdyhover">
                  <a>
                    <div style="text-align:center;">
                     <img src="./picCut/image/photo.jpg" />
                     <p>2009年12月</p>
                    </div>
                  </a>
              </li>
              <li onclick="genUnRoot('3')" id="sjjg_3" style="width:25%" class="buttonzdyhover">
                  <a>
                    <div style="text-align:center;">
                     <img src="./picCut/image/photo.jpg" />
                     <p>2015年11月</p>
                    </div>
                  </a>
              </li>
              <li onclick="genUnRoot('4')" id="sjjg_4" style="width:25%" class="buttonzdyhover">
                  <a>
                    <div style="text-align:center;">
                     <img src="./picCut/image/photo.jpg" />
                     <p>2019年01月</p>
                    </div>
                  </a>
              </li>
              <li onclick="genUnRoot('5')" id="sjjg_5" style="width:25%" class="buttonzdyhover">
                  <a>
                    <div style="text-align:center;">
                     <img src="./picCut/image/photo.jpg" />
                     <p>2019年03月</p>
                    </div>
                  </a>
              </li>
		</ul> -->
	</div>
</odin:groupBox>
</div>
</div>
<odin:hidden property="Num"/>
<odin:hidden property="a0000"/>
<script>
/* Ext.onReady(function(){
	var a0000 = document.getElementById('subWinIdBussessId').value
}) */
function closeWin(){
	window.close();
}


function genUnRoot(num){
	//$('#sjjg_'+num).addClass('buttonzdy');
	var photoNum = document.getElementById('Num').value();
	alert(photoNum);
	//var photoNum=5;
	for(var i=0;i<=photoNum;i++){
		if(i==num){
			$('#sjjg_'+i).removeClass('buttonzdyhover');
			$('#sjjg_'+i).addClass('buttonzdy');
			//alert(i);
		}else{
			$('#sjjg_'+i).removeClass('buttonzdy');
			$('#sjjg_'+i).addClass('buttonzdyhover');
			//alert(i);
		}
	}
}



function getChoose(){
	//var rmbNum = realParent.document.getElementById('rmbNum').value;
	//document.getElementById('Num').value = rmbNum;
	var chooseId = window.dialogArguments['param'];
	//alert(chooseId);
	radow.doEvent("expOutApp");
}

</script>
