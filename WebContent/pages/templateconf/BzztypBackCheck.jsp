<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.BackCheckPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<%
 String sql1 = request.getParameter("initParams");
System.out.println(sql1);
%>

<script type="text/javascript">
   

</script>
 <odin:hidden property="sqls" /> 
  <input type="hidden" id="gid"/>
<!-- <input type="text" id="sqlz"/> -->
<odin:head></odin:head>
<odin:base></odin:base>
<div id="border">
<div id="tol2" align="left"></div>
<div id='tale' style="font-weight: bold;word-break: keep-all">����״̬����-����</div>
			<odin:editgrid2  property="TrainingInfoGrid" height="300" title="" autoFill="true" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="20"  url="/" >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id"  />
			  		<odin:gridDataCol name="b0111"/> 
			  		<odin:gridDataCol name="b0101" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="����" dataIndex="id" editor="text" edited="false" width="20" hidden="true"/>
				  	<odin:gridEditColumn2 header="����id" dataIndex="b0111" editor="text" edited="false" width="20" hidden="true"/>
				  	<odin:gridEditColumn2 width="100" header="��λ����" dataIndex="b0101" editor="text" edited="false"   isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid2 >
</div>
<script type="text/javascript">

Ext.onReady(function(){
  var vwin = window.dialogArguments; //�õ�window����
  //var a=window.returnValue;
  var doc = vwin.document.getElementById("sqlhong").value; //��ø�ҳ���ֵ
  var doc1 = vwin.document.getElementById("sqlhuang").value; //��ø�ҳ���ֵ
  var doc2 = vwin.document.getElementById("sqllv").value; //��ø�ҳ���ֵ
  var flag = vwin.document.getElementById("flag").value; //��ø�ҳ���ֵ
  

  
  if(flag=='hong'){
	 document.getElementById("sqls").value=doc;
	 // radow.doEvent('TrainingInfoGrid.dogridquery');
  }else if(flag=='huang'){
	  document.getElementById("sqls").value=doc1;
	 // radow.doEvent('TrainingInfoGrid.dogridquery');
  }else if(flag=='lv'){
	  document.getElementById("sqls").value=doc2;
	  //radow.doEvent('TrainingInfoGrid.dogridquery');
  }  
  
	
});
function rowDbClick(grid,rowIndex,event){
	var gridId = "TrainingInfoGrid";
	var grid = Ext.getCmp(gridId);
	var record = grid.getStore().getAt(rowIndex);   //��ȡ��ǰ�е�����
	var gid1 = record.data.b0111;
	document.getElementById("gid").value=gid1;
	var hh=$(window).height();
	var ww=$(window).width()-20;
<%-- 	news=window.open("<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BzypTj");  --%>
	window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BzypTj',window,'dialogTop:0;dialogWidth:'+(window.screen.availWidth)+'px;DialogHeight='+(window.screen.availHeight)+'px;help=0;center=1;status:no;directories:yes;scrollbars:no;resizable:no;help:no');
}

/* $(window).bind('beforeunload',function(){
	alert(112233);
    if(news != null && news.closed == false){
    	//news.close();
    	cos();
    }else if(news==null){
    	cos();
    	alert("XXX");
    	//news.close();
    }
}) */

</script>
<style>

</style>
