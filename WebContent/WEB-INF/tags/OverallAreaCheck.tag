<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="ͳ�������" description="����ͳ������⣬���û�Ļ����������" %>
<%@ attribute name="way" required="false" description="�ջ�Ϊ0ʱΪֻ��ס������ʾ��ʾ��Ϣ�����򵯳��Ի���ȷ����رո�ҵ�񴰿�"%>
<%
	com.insigma.siis.local.comm.planarea.OverallAreaBean area = (com.insigma.siis.local.comm.planarea.OverallAreaBean)com.insigma.odin.framework.util.SysUtil.getCurrentUser(request).getUserOtherInfo().get("userOverallArea");
	way = ((way==null||way.equals(""))?"0":way);
	if(area==null || area.getAreaCode()==null || area.getAreaCode().equals("")){		
%>
<script>
 Ext.onReady(function(){
 	 var way = "<%=way%>";
 	 if(way!="0"){
		 odin.info("��ҵ�����Ҫ��ĳһͳ�����²ſɰ���ȷ����ϵͳ���Զ��رյ�ǰ���ڡ�",function(){
		 	var itemId = MDParam.functionid;
		 	var item = top.frames["main"].tabs.getItem(itemId);
		 	if(item.closable){
		        top.frames["main"].tabs.remove(item);
		    } 
		 });
	 }else{
	 	mask();
	 }
 });
 function mask(){
    window.setTimeout(function(){
 		odin.ext.get(document.body).mask("<h3><font color=red>��ҵ�����Ҫ��ĳһͳ�����²ſɰ���</font></h3>",odin.msgCls);
 	},1000);	
 } 
 </script> 
<% 
	}
%>