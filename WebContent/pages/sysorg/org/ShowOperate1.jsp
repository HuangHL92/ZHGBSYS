<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import ="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.utils.CommonQueryBS"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.radow.RadowException"%>
<%@page import="com.insigma.odin.framework.radow.event.EventRtnType"%>
<%@page import ="javax.script.ScriptEngine"%>
<%@page import ="javax.script.ScriptEngineManager"%>
<%@page import ="javax.script.ScriptException"%>
<%
   String ctxPath = request.getContextPath();
   String subWinIdBussessId = request.getParameter("subWinIdBussessId");
   
   //��ȡ�û�����������unitid
   Map<String,Object> lists = CommonQueryBS.getMapBySQL("select sign_code,unitid from b01 where b0111 = '"+subWinIdBussessId+"'"); 
 //�ж�����ҵ��λ����������λ
   Map<String,Object> sums = null;
   if(lists.get("sign_code")!=null){
	   if(lists.get("sign_code").equals("1")){
		   //�������ҵ��λ
		    sums = CommonQueryBS.getMapBySQL("select b28 + b29 + b30 AS BZS,"
			       +"s28 + s29 + s30 AS SYS,"
			       +" b11 + b12 as BMLDZS,"
			       +"s17 + s18 + s20 + s21 as BMLDSYS,"
			       +" s66+s74 AS NSJGS,"
			       +"b50 + b23 AS NSJGLDZS"
			 +" from gzdba.hdsy_table where unitid='"+lists.get("unitid")+"'");
		    
	   }else if(lists.get("sign_code").equals("2")){
		   //�����������λ
		   sums = CommonQueryBS.getMapBySQL("select b2 + b3 + b4 + b7 + b9 AS BZS,"
			       +"s2 + s3 + s4 AS SYS,"
			       +" b11 + b12 as BMLDZS,"
			       +"s17 + s18 + s20 + s21 as BMLDSYS,"
			       +"s66 + s74 AS NSJGS,"
			       +"b50 + b23 AS NSJGLDZS"
			 +" from gzdba.hdsy_table where unitid='"+lists.get("unitid")+"'");
		   
	   }
   }else{

      return;
   }
   //����ͳ����
   //��ȡ������Ӧ����
   Map<String,Object> list = CommonQueryBS.getMapBySQL("select * from gzdba.jgsy_common where unitid='"+lists.get("unitid")+"'");
   
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<script src="rmb/jquery-1.7.2.min.js"> </script>
	<link href="css/main.css" rel="stylesheet">
    <style type="text/css">
    
    div{
    	height:450px;
        overflow:auto;
    }
    table{
      font-size: 18px;
      border:0px;
      border-collapse: collapse;
      
     }
    td{
      padding-left: 5px;
     }
    tr{
      
      border: 1px solid black;
     }
	.row1{
		width:200px;
		height:36px;
		background: #fafafa;
	}
	.row2{
		width:400px
	}
	.row2 p{
		width:100%
	}
	.row3 p{
		width:1012px
	}
	.span1{
		margin-left: 30px;
	}
	.span2{
		margin-left: 50px;
	}
	.public , .government{
		display:none;
	}
</style>
<script type="text/javascript">
    window.onload=function(){
    	//��ȡ�û���������
		 String sign = lists.get("sign_code");
    	//�ж�����ҵ��λ���ǻ��ص�λ
    	if(sign==1){
    		//�������ҵ��λ ����ʾ��ҵ��λ���е�tr��Ϣ
    		$(".public").css("display","block");
    	}else if(sign==2){
    		//����ǻ��ص�λ ����ʾ��ҵ��λ���е�tr��Ϣ
    		$(".government").css("display","block");
    	}
    	//ֱ����zs_tag ���⣺flag
    	var zsTag = <%=list.get("zs_tag")%>;
    	var flag = <%=list.get("flag")%>;
    	if(zsTag==1){
    		$("#zsTag").attr("checked",true);
    	}else if(flag==1){
    		$('#flag').attr("checked",true);
    	}
    };
</script>
</head>

<body id="Operate" >
<div>
	<table border="1">
		<tr>
			<td  class="row1">����ע����룺</td>
			<td  class="row2" >
				<p  label="����ע�����" readonly="readonly"><%=list.get("jgsy_code")%></p>
			</td>
			<td class="row1">��ϵ�����ơ��ɣ���</td>
			<td  class="row2" >
				<p label="��ϵ�����ơ��ɣ�" readonly="readonly"><%=list.get("cs_no")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">�������ƣ�</td>
			<td  class="row3" colspan="3"> 
				<p label="��������" readonly="readonly"><%=list.get("jgsy_name")%></p>
			</td>

		</tr>
		<tr>
			<td  class="row1">����ȫ�ƣ�</td>
			<td  class="row2" colspan="3">
				<p  label="����ȫ��" readonly="readonly"><%=list.get("se_name")%></p>
			</td>
		   
			<!-- <td class="row1">����֤�汾��</td>
			<td  class="row2">
				<span>�ɰ�</span> <input type="radio" name="version" onclick="javascript: return false;" checked="checked">
                <span class="span1">�°�</span> <input type="radio" onclick="javascript: return false;" name="version">
			</td> -->
		</tr>
		<tr>
			<td  class="row1">ϰ�ߣ��淶����ƣ�</td>
			<td  class="row2" >
				<p  label="ϰ�ߣ��淶�����" readonly="readonly"><%=list.get("st_name")%></p>
			</td>
			<td class="row1">����ʱ�䣺</td>
			<td  class="row2">
				<p label="����ʱ��" readonly="readonly"><%=list.get("update_time")%></p>
			</td>
		</tr>
		<!--<tr>
			<td  class="row1">�������ƣ�</td>
			<td  class="row3" colspan="3"> 
				<p label="��������" readonly="readonly">�����޶�Ӧ����</p>
			</td>

		</tr>
		 <tr>
			<td  class="row1">�������������ļ���</td>
			<td  class="row3" colspan="3"> 
				<p label="����ע�����" readonly="readonly">δ�ҵ�����ֶ�</p>
			</td>

		</tr> -->
		<tr>
			<td  class="row1">���ܲ��ţ�</td>
			<td  class="row3" colspan="3"> 
				<p label="����ע�����" readonly="readonly"><%=list.get("main_dept")%></p>
			</td>

		</tr>
		<tr>
			<td  class="row1">ͳһ������ô��룺</td>
			<td  class="row2" >
				<p  label="ͳһ������ô���" readonly="readonly"><%=list.get("unify_code")%></p>
			</td>
			<td class="row1">��֯�������룺</td>
			<td  class="row2">
				<p label="��֯��������" readonly="readonly"><%=list.get("orgcode")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">����֤��ţ�</td>
			<td  class="row2" >
				<p  label="����֤���" readonly="readonly"><%=list.get("register")%></p>
			</td>
			<td class="row1">���������ĺţ�</td>
			<td  class="row2">
				<p label="���������ĺ�" readonly="readonly"><%=list.get("spw_num")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">�������ƹ���֤���ͣ�</td>
			<td  class="row2" >
				<p  label="�������ƹ���֤����" readonly="readonly">δ�ҵ�����ֶ�</p>
			</td>
			<td class="row1">�������</td>
			<td  class="row2">
				<p label="�������" readonly="readonly"><%=list.get("unit_jb") %></p>
			</td>
		</tr>
		<tr class="government">
			<td  class="row1">�������ƹ���֤���ͣ�</td>
			<td  class="row2" >
				<p  label="�������ƹ���֤����" readonly="readonly">δ�ҵ�����ֶ�</p>
			</td>
			<td class="row1">���������ĺţ�</td>
			<td  class="row2">
				<p label="���������ĺ�" readonly="readonly"><%=list.get("spw_num")%></p>
			</td>
		</tr>
		<tr class="government">
			<td  class="row1">�������</td>
			<td  class="row2" >
				<p  label="�������" readonly="readonly">δ�ҵ�����ֶ�</p>
			</td>
			<td class="row1">�������룺</td>
			<td  class="row2">
				<p label="��������" readonly="readonly"><%=list.get("jgsy_code")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">������ϵ��</td>
			<td  class="row2" >
				<p  label="������ϵ" readonly="readonly"><%=list.get("dep_code")%></p>
			</td>
			<td class="row1">������ʽ��</td>
			<td  class="row2">
				<p label="������ʽ" readonly="readonly"><%=list.get("outlayid")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">�������ԣ�</td>
			<td  class="row3" colspan="3"> 
				<p label="��������" readonly="readonly"><%=list.get("jgsy_type")%></p>
			</td>

		</tr>
		<tr class="government">
			<td  class="row1">�������ʣ�</td>
			<td  class="row2" >
				<p  label="��������" readonly="readonly"><%=list.get("typeid")%></p>
			</td>
			<td class="row1">�����������</td>
			<td  class="row2">
				<p label="�����������" readonly="readonly"><%=list.get("intypeid")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">��ҵ��λ���</td>
			<td  class="row2" >
				<p  label="��ҵ��λ���" readonly="readonly"><%=list.get("dwlb")%></p>
			</td>
			<td class="row1">��ҵ���ࣺ</td>
			<td  class="row2">
				<p label="��ҵ����" readonly="readonly">δ�ҵ�����ֶ�</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">��������</td>
			<td  class="row2" >
				<p  label="������" readonly="readonly"><%=sums.get("bzs")%>&nbsp;��</p>
			</td>
			<td class="row1">ʵ��������</td>
			<td  class="row2">
				<p label="ʵ������" readonly="readonly"><%=sums.get("sys") %>&nbsp;��</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">�����쵼ְ����</td>
			<td  class="row2" >
				<p  label="�����쵼ְ��" readonly="readonly"><%=sums.get("bmldzs") %>&nbsp;��</p>
			</td>
			<td class="row1">�����쵼ʵ������</td>
			<td  class="row2">
				<p label="�����쵼ʵ����" readonly="readonly"><%=sums.get("bmldsys") %>&nbsp;��</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">�����������</td>
			<td  class="row2" >
				<p  label="���������" readonly="readonly"><%=sums.get("nsjgs") %>&nbsp;��</p>
			</td>
			<td class="row1">��������쵼ְ����</td>
			<td  class="row2">
				<p label="��������쵼ְ��" readonly="readonly"><%=sums.get("nsjgldzs") %>&nbsp;��</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">�䶯���ʹ��룺</td>
			<td  class="row2" >
				<p  label="�䶯���ʹ���" readonly="readonly"><%=list.get("bdlx")%></p>
			</td>
			<td class="row1">�䶯ʱ�䣺</td>
			<td  class="row2">
				<p label="�䶯ʱ��" readonly="readonly"><%=list.get("bd_time")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">��λ�����룺</td>
			<td  class="row2" >
				<p  label="��λ������" readonly="readonly"><%=list.get("dwlb")%></p>
			</td>
			<td class="row1">���������ˣ�</td>
			<td  class="row2">
				<p label="����������" readonly="readonly"><%=list.get("fdr")%></p>
			</td>
		</tr>
			<tr>
			<td  class="row1">����ѡ�</td>
			<td  class="row3" colspan="3"> 
				<span>ֱ����־:</span> <input id="zsTag" type="checkbox" name="options" onclick="javascript: return false;" >
                <span class="span2">������(԰��):</span> <input   type="checkbox" name="options" onclick="javascript: return false;">
			    <span class="span2">�������:</span> <input id="flag" type="checkbox" name="options" onclick="javascript: return false;">
			</td>

		</tr>
		<tr>
			<td  class="row1">�ʱࣺ</td>
			<td  class="row2" >
				<p  label="�ʱ�" readonly="readonly"><%=list.get("postalcode")%></p>
			</td>
			<td class="row1">��ַ��</td>
			<td  class="row2">
				<p label="��ַ" readonly="readonly"><%=list.get("address")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">��ϵ�ˣ�</td>
			<td  class="row2" >
				<p  label="��ϵ��" readonly="readonly"><%=list.get("linkman")%></p>
			</td>
			<td class="row1">�绰��</td>
			<td  class="row2">
				<p label="�绰" readonly="readonly"><%=list.get("phone")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">��ע��</td>
			<td  class="row2" >
				<p  label="��ע" readonly="readonly"><%=list.get("remark")%></p>
			</td>
			<td class="row1">����ţ�</td>
			<td  class="row2">
				<p label="�����" readonly="readonly"><%=list.get("px_no")%></p>
			</td>
		</tr>
		
	</table>
	</div>
</body>

</html>