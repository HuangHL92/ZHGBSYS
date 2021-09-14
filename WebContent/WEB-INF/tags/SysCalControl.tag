<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="系统业务期控制" description="系统业务期控制" %>
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
				alert("系统结算期内不允许办理征缴业务，请与系统管理员联系！");
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
				alert("系统结算期内不允许办理支付业务，请与系统管理员联系！");
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