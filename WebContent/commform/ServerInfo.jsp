<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.insigma.odin.framework.persistence.*" %>
<%@ page import="java.lang.management.*" %>
<%@ page import="javax.management.*" %>
<%@ page import="javax.management.openmbean.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>INSIIS Server Information</title>
<style type="text/css">
<!--
.tabtitle {
	font-family: "Times New Roman", Times, serif;
	font-size: 24px;
	color: #003366;
	font-weight: bold;
}
.proptab {
	table-layout:fixed;
}
.propkey {
	font-family: Arial, Helvetica, sans-serif;
	color: #333366;
	font-size: 14px;
	font-weight: bold;
}
.propvalue {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	color: #000000;
	text-decoration: none;
}
-->
</style>
</head>
<body>
 <table width="890" border="0" cellpadding="0" cellspacing="0" align="center">
 <tr><td>
 <h1>INSIIS Server Information Page</h1>
 </td></tr>
 <tr><td>
 <p><font color="green">If you can see this page, then server is running normally, and status looks ok.</font></p>
 </td></tr>
 <tr><td>
 	Current Time: <%= new SimpleDateFormat().format(new Date()) %>
 </td></tr>
 </table> 
 
 <br>
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center" class="proptab">
  <caption  align="left" class="tabtitle">
   System Environment
  </caption>
 <%
 	class MBeanGetAttrResult{
		private String message;
		private int    count;
		private HashMap attrMap;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public HashMap getAttrMap() {
			return attrMap;
		}
		public void setAttrMap(HashMap attrMap) {
			this.attrMap = attrMap;
		}
 	};
 
 	class MBeanAttrRetriever{
	 	
	 	private String MBeanName;
	 	private String[] keys = {"init","max","used","committed"};
	 	private String[] ignores = {"SystemProperties"};
	 	
		public MBeanAttrRetriever(String beanname){
			this.MBeanName = beanname;
		}
		
		public MBeanGetAttrResult getAll(){
			MBeanGetAttrResult res = new MBeanGetAttrResult();
			HashMap attrs = new HashMap();	
			
			try{
				MBeanServer mbserver = ManagementFactory.getPlatformMBeanServer();
				ObjectName on = new ObjectName(this.MBeanName);
				MBeanInfo mbi = mbserver.getMBeanInfo(on);
				MBeanAttributeInfo[] attrinfos = mbi.getAttributes();
				for (int i=0;i<attrinfos.length;i++){
					String attrname = attrinfos[i].getName();
					String attrtype = attrinfos[i].getType();
					try{
						Object attrvalue = mbserver.getAttribute(on,attrname);
						boolean ignored = false;
						//attrs.put(attrname,attrvalue.toString());
						for (int k =0;k<ignores.length;k++){
							if (ignores[k].equals(attrname)){
								ignored = true;
								break;
							}
						}
						if (ignored){
							attrs.put(attrname,"<font color=#6600CC>IGNORED</font>");
							continue;
						}
						if (attrtype.equals("javax.management.openmbean.CompositeData")){
							CompositeData comp = (CompositeData)attrvalue;
							StringBuffer compcontent = new StringBuffer();
							for (int j=0;j<keys.length;j++){
								if (comp.containsKey(keys[j])){
									compcontent.append(keys[j]);
									compcontent.append("=");
									compcontent.append(comp.get(keys[j]).toString());
									compcontent.append(" ");
								}
							}
							attrs.put(attrname,compcontent.toString());
						}
						else
							attrs.put(attrname,attrvalue.toString());
					}
					catch (Exception e){
						attrs.put(attrname,"Error while retrieving this attribute");
					}
				}
				res.setMessage("");
				res.setAttrMap(attrs);
				res.setCount(attrs.size());
			}
			catch (InstanceNotFoundException e){
				res.setMessage("Information Not Available");
				res.setCount(-1);
				res.setAttrMap(attrs);
			}
			catch (Exception e){
				res.setMessage("Error while getting information");
				res.setCount(-1);	
				res.setAttrMap(attrs);
			}
			return res;
		}
 	};
 
	class PropTabContentBuilder{
		public String build(MBeanGetAttrResult mars){
			StringBuffer sb = new StringBuffer();
			if (mars.getCount()>0){
				HashMap hm = mars.getAttrMap();
				Set keyset = hm.keySet();
				Iterator it = keyset.iterator();
				while (it.hasNext()){
					String key = (String)it.next();
					String value = (String)hm.get(key);
					sb.append("<tr><td class=\"propkey\" width=250>\n");
					sb.append(key);
					sb.append("\n</td>\n");
					sb.append("<td class=\"propvalue\">\n");
					sb.append(value);
					sb.append("</tr>\n");
				}
				return sb.toString();
			}
			else if (mars.getCount()==0){
				return "<tr><td class=\"propkey\">Empty</td></tr>\n";
			}
			else{
				return "<tr><td class=\"propkey\"><font color=red>"
					   +mars.getMessage()
				       +"</font></td></tr>\n";
			}		
		}
	}
 	
	class SysHbTableChecker{
		public String doCheck(String tabname){
			try{
		 		HBSession hbsess = HBUtil.getHBSession();
		 		Long count = (Long)hbsess.createQuery("select count(*) from "+tabname).uniqueResult();
		 		return "<font color=#009900>OK</font>&nbsp; total count:"+count.toString();
			}
			catch (Exception e){
				
				return "<font color=red>ERROR</font>";
			}
		}
	}
	
	class SysNormalTableChecker{
		public String doCheck(String tabname){
			try{
		 		HBSession hbsess = HBUtil.getHBSession();
		 		java.lang.Number count = (java.lang.Number)hbsess.createSQLQuery("select count(*) from "+tabname).uniqueResult();
		 		return "<font color=#009900>OK</font>&nbsp; total count:"+count.toString();
			}
			catch (Exception e){
				e.printStackTrace();
				return "<font color=red>ERROR</font>";
			}
		}
	}
 	Map envmap = System.getenv();
 	Set envkeyset = envmap.keySet();
 	Iterator it = envkeyset.iterator();
 	while (it.hasNext()){
 		Object key = it.next();
 		String strvalue = envmap.get(key).toString();
 		String strkey = key.toString();
 %>
 <tr><td class="propkey" width=250>
 <%=strkey %>
 </td>
 <td class="propvalue">
 <%= strvalue %>
 </td>
 </tr>
 <%
 	}
 %>
 </table>
 <br>
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  System Properties
 </caption>
 <%
 	Set keyset = System.getProperties().keySet();
 	it = keyset.iterator();
 	while (it.hasNext()){
 		String key = it.next().toString();
 		String value = System.getProperties().getProperty(key.toString());
 %>
 <tr><td class="propkey" width=250>
 <%=key %>
 </td>
 <td class="propvalue">
 <%=value %>
 </td>
 </tr>
 <%
 	}
 %>
 </table>
 <br>
 
 <!-- Operation System Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Operation System Info
 </caption> 
 <%String tabcontent=new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME).getAll());
 %>
 <%=tabcontent %>
 </table>
 <br>
 
 <!-- Runtime Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Runtime Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.RUNTIME_MXBEAN_NAME).getAll());%>
 <%=tabcontent %>
 </table>
 <br>

 <!-- Treads Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Treads Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.THREAD_MXBEAN_NAME).getAll());%>
 <%=tabcontent %>
 </table>
 
 <br>
 <!-- Memory Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Memory Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.MEMORY_MXBEAN_NAME).getAll());%>
 <%=tabcontent %>
 </table> 
 <br>
 
  <!-- Compilation Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Compilation Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.COMPILATION_MXBEAN_NAME).getAll());%>
 <%=tabcontent %>
 </table> 
 <br>

 <!-- Class Loading Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Class Loading Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever(ManagementFactory.CLASS_LOADING_MXBEAN_NAME).getAll());%>
 <%=tabcontent %>
 </table> 
 <br> 
 
 <!-- Logging Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  JVM Loggin Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever("java.util.logging:type=Logging").getAll());%>
 <%=tabcontent %>
 </table> 
 <br> 
 
 <!-- WebLogic Runtime Info -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  WebLogic Runtime Info
 </caption> 
 <%tabcontent = new PropTabContentBuilder().build(new MBeanAttrRetriever("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean").getAll());%>
 <%=tabcontent %>
 </table> 
 <br> 
 
 <!-- Odin Framework Arch Check -->
 <table width="890" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bordercolordark="#CCCCCC" bordercolorlight="#CCCCCC" align="center"  class="proptab">
 <caption  align="left" class="tabtitle">
  Odin Framework Syscheck
 </caption>
  <%
 	try{
 		HBSession hbsess = HBUtil.getHBSession();
 		%>
 		 <tr><td class="propkey" width=250>
		 Sysfunction
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysfunction")%>
		 </td></tr>
		 
 		 <tr><td class="propkey" width=250>
		 Sysuser
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysuser")%>
		 </td></tr>		
		 
		 <tr><td class="propkey" width=250>
		 Sysrole
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysrole")%>
		 </td></tr>
		 
		 <tr><td class="propkey" width=250>
		 Sysacl
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysacl")%>
		 </td></tr>	 
		 
		 <tr><td class="propkey" width=250>
		 Sysact
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysact")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 Sbds_Userlog
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("SbdsUserlog")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 Sbds_log
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("SBDS_LOG")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 Sbds_Syslog
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("SBDS_SYSLOG")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 Sbds_SyslogDt
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("SBDS_SYSLOGDT")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 Sbds_UscrLog
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("SBDS_USCRLOG")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 PrintLog
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Printlog")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 Repreport
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Repreport")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 OperationAudit
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Operationaudit")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 Sysopright
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysoprightacl")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 Sysoprightacl
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("Sysoprightacl")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 ODA_STRUTS_ACTION
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("ODA_STRUTS_ACTION")%>
		 </td></tr>			 

		 <tr><td class="propkey" width=250>
		 ODA_STRUTS_FORM
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("ODA_STRUTS_FORM")%>
		 </td></tr>	
		 
		 <tr><td class="propkey" width=250>
		 ODA_STRUTS_MODULE
		 </td><td class="propvalue">
		 <%= new SysNormalTableChecker().doCheck("ODA_STRUTS_MODULE")%>
		 </td></tr>	

		 <tr><td class="propkey" width=250>
		 VAa10
		 </td><td class="propvalue">
		 <%= new SysHbTableChecker().doCheck("VAa10")%>
		 </td></tr>	
		 
 		<%
 	}
 	catch(Exception e){
 		%>
 		Error while performing Hibernate test.
 		<%= e.getMessage() %>
 		<%
 	}
 %>
 </table>
  <br>

</body>
</html>