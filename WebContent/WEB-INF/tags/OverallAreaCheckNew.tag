<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="统筹区检测" description="进行统筹区检测，如果没的话则不允许操作"%>
<%@ attribute name="way" required="false"
	description="空或为0时为只遮住，并显示提示信息，否则弹出对话框，确定后关闭该业务窗口"%>
<%
	com.insigma.siis.local.comm.planarea.OverallAreaBean area = (com.insigma.siis.local.comm.planarea.OverallAreaBean) com.insigma.odin.framework.util.SysUtil
			.getCurrentUser(request).getUserOtherInfo().get(
					"userOverallArea");
	way = ((way == null || way.equals("")) ? "0" : way);
	if (area == null || area.getAreaCode() == null
			|| area.getAreaCode().equals("")) {
%>
<odin:window src="/blank.htm" id="win3" width="830" height="575"></odin:window>
<script>
 Ext.onReady(function(){
 	 var way = "<%=way%>";
 	 odin.Ajax.request(contextPath+'/com/insigma/siis/local/comm/planarea/OverallAreaAction.do?method=selectOverallArea',null,tcq_succfun,tcq_failfun,false,false);
 });
 function mask(){
    window.setTimeout(function(){
 		odin.ext.get(document.body).mask("<h3><font color=red>本业务必须要在某一统筹区下才可办理！</font></h3>",odin.msgCls);
 	},1000);	
 }
 functin closeWin(){
 	odin.info("本业务必须要在某一统筹区下才可办理！确定后系统将自动关闭当前窗口。",function(){
		 var itemId = MDParam.functionid;
		 var item = top.frames["main"].tabs.getItem(itemId);
		 if(item.closable){
		     top.frames["main"].tabs.remove(item);
		 } 
	});
 }
 	var rs;
 	function tcq_succfun(response){
 		//构建一个数组
 		var rs=new Array(response.data.length);
  		for(i=0;i<response.data.length;i++){
    		rs[i]= new Ext.data.Record(response.data[i]);
    	}
 		var num=response.data.length;
 		if(num>1){
 			openWindow(response);
 		}
 	}
 	function tcq_failfun(rm){
 		alert("设置统筹区信息失败！");
 		closeWin();
 	}
 	function openWindow(response){
 		odin.showWindowWithSrc('win3','<%=request.getContextPath()%>/pages/comm/planarea/Control.jsp');
 	} 
 </script>
<%
	}
%>