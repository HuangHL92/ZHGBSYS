<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="ͳ�������" description="����ͳ������⣬���û�Ļ����������"%>
<%@ attribute name="way" required="false"
	description="�ջ�Ϊ0ʱΪֻ��ס������ʾ��ʾ��Ϣ�����򵯳��Ի���ȷ����رո�ҵ�񴰿�"%>
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
 		odin.ext.get(document.body).mask("<h3><font color=red>��ҵ�����Ҫ��ĳһͳ�����²ſɰ���</font></h3>",odin.msgCls);
 	},1000);	
 }
 functin closeWin(){
 	odin.info("��ҵ�����Ҫ��ĳһͳ�����²ſɰ���ȷ����ϵͳ���Զ��رյ�ǰ���ڡ�",function(){
		 var itemId = MDParam.functionid;
		 var item = top.frames["main"].tabs.getItem(itemId);
		 if(item.closable){
		     top.frames["main"].tabs.remove(item);
		 } 
	});
 }
 	var rs;
 	function tcq_succfun(response){
 		//����һ������
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
 		alert("����ͳ������Ϣʧ�ܣ�");
 		closeWin();
 	}
 	function openWindow(response){
 		odin.showWindowWithSrc('win3','<%=request.getContextPath()%>/pages/comm/planarea/Control.jsp');
 	} 
 </script>
<%
	}
%>