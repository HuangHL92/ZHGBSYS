<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.AddRmbPageModel"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.insigma.siis.local.business.helperUtil.IdCardManageUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.business.entity.LogMain"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.siis.local.business.entity.A01"%>
<%@page import="com.insigma.siis.local.business.entity.A99Z1"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%!private String SV(String v){
	return v==null?"":StringEscapeUtils.escapeHtml(v);
}
%>
<%
	String ctxPath = request.getContextPath(); 
	String username = "";
	try{
		username = SysManagerUtils.getUserName(); 	//�����û����˴�Ϊ�û���¼������
	}catch(Exception e){
		out.println("<script>alert('��¼��ʱ��');parent.window.location='"+ctxPath+"/LogonDialog.jsp'</script>");
	}
	
	
	String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
	//RrmbCodeType = CodeType2js.getRrmbCodeType();
	if(RrmbCodeType==null){
		RrmbCodeType = CodeType2js.getRrmbCodeType();
		session.setAttribute("RrmbCodeType",RrmbCodeType);
	}
	
	HBSession sess = HBUtil.getHBSession();
	String setTitle = "";
	
	String a0000 = request.getParameter("a0000");
	a0000 = URLDecoder.decode(a0000,"UTF8");
	String sql = "from A01 where a0000='"+a0000+"'";
	List list = sess.createQuery(sql).list();
	
	
	
	
	A01 a01 = null;
	if(list==null||list.size()==0){
		a01 = new A01();
		a01.setA0000(a0000);
		a01.setA0163("1");//Ĭ����ְ��Ա
		a01.setA14z101("��");//��������
		a01.setStatus("4");
		a01.setA0197("0");//���㹤������ʱ����������
		a01.setTbr(SysManagerUtils.getUserId());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("����");
		sess.save(a01);
		sess.flush();
	}else{
		
		a01 = (A01) list.get(0);
		//������־
		/* LogMain logmain = new LogMain();
		logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//����id
		logmain.setUserlog(username); 	//�����û����˴�Ϊ�û���¼������
		
		logmain.setSystemoperatedate(new Date()); 			//ϵͳ����ʱ��
		logmain.setEventtype("������Ա��Ϣ¼��ҳ��"); 		//����
		logmain.setEventobject("���������Ϣ"); 				//����������
		logmain.setObjectid(a0000); 						//�����漰�������
		logmain.setObjectname(a01.getA0101());   			//��ǰ�������漰���������
		sess.save(logmain);
		sess.flush(); */
		
		setTitle = AddRmbPageModel.setTitle(a01);
		
	}
	
	String a0195Text = null;
	if(a01.getA0195()!=null){
		a0195Text = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
		
		if(a0195Text == null || a0195Text.equals("")){
			a0195Text = a01.getA0195();
		}
	}
	
	//��ͥ��Ա
	String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
	List lista36 = sess.createQuery(sqla36).list();
	int lista36Length = lista36.size();
	
	//������Ϣ�� 
	String sqlaA99Z1 = "from A99Z1 where a0000='"+a0000+"'";
	List listA99Z1 = sess.createQuery(sqlaA99Z1).list();
	
	A99Z1 a99Z1 = null;
	if(listA99Z1==null||listA99Z1.size()==0){
		//a0000 = UUID.randomUUID().toString();
		a99Z1 = new A99Z1();
	}else{
		a99Z1 = (A99Z1) listA99Z1.get(0);
	}
	request.setAttribute("a01", a01);
%>
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
<%=RrmbCodeType%>


var realParent = window.dialogArguments.window;
var parent = window.dialogArguments.window;
//var gridName = parent.Ext.getCmp("personInfoOP").initialConfig.gridName;
var gridName = window.dialogArguments.wincfg.gridName;

</script>
