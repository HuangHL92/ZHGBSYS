<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="ϵͳҵ���ڿ���" description="ϵͳҵ���ڿ���" %>
<!-- 
<script type="text/javascript">
<%	
 	com.insigma.siis.local.util.SysCal syscal=new com.insigma.siis.local.util.SysCal();
com.insigma.odin.framework.privilege.vo.FunctionVO sysfun=com.insigma.odin.framework.sys.SysfunctionManager.getModuleSysfunction();
	if(sysfun.getUptype()!=null){
		if(sysfun.getUptype().equals("0")){
			if(syscal.getOpFlag()==0){	
%>
			Ext.onReady(function(){	
				alert("ϵͳ�������ڲ������������ҵ������ϵͳ����Ա��ϵ��");
				var tabs=top.main.tabs;
				var tab=tabs.getItem(MDParam.functionid);
				if (tab){tabs.remove(tab);}
			});
<%				
			}
		}else if(sysfun.getUptype().equals("1")){
			if(syscal.getPyFlag()==0){
%>
			Ext.onReady(function(){	
				alert("ϵͳ�������ڲ��������֧��ҵ������ϵͳ����Ա��ϵ��");
				var tabs=top.main.tabs;
				var tab=tabs.getItem(MDParam.functionid);
				if (tab){tabs.remove(tab);}
			});
<%
			}
		}
	}
%>
</script>-->