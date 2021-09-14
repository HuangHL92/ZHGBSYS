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
   
   //获取该机构的性质与unitid
   Map<String,Object> lists = CommonQueryBS.getMapBySQL("select sign_code,unitid from b01 where b0111 = '"+subWinIdBussessId+"'"); 
 //判断是事业单位还是行政单位
   Map<String,Object> sums = null;
   if(lists.get("sign_code")!=null){
	   if(lists.get("sign_code").equals("1")){
		   //如果是事业单位
		    sums = CommonQueryBS.getMapBySQL("select b28 + b29 + b30 AS BZS,"
			       +"s28 + s29 + s30 AS SYS,"
			       +" b11 + b12 as BMLDZS,"
			       +"s17 + s18 + s20 + s21 as BMLDSYS,"
			       +" s66+s74 AS NSJGS,"
			       +"b50 + b23 AS NSJGLDZS"
			 +" from gzdba.hdsy_table where unitid='"+lists.get("unitid")+"'");
		    
	   }else if(lists.get("sign_code").equals("2")){
		   //如果是行政单位
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
   //计算统计数
   //获取机构对应数据
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
    	//获取该机构的性质
		 String sign = lists.get("sign_code");
    	//判断是事业单位还是机关单位
    	if(sign==1){
    		//如果是事业单位 则显示事业单位特有的tr信息
    		$(".public").css("display","block");
    	}else if(sign==2){
    		//如果是机关单位 则显示事业单位特有的tr信息
    		$(".government").css("display","block");
    	}
    	//直属：zs_tag 特殊：flag
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
			<td  class="row1">机构注册代码：</td>
			<td  class="row2" >
				<p  label="机构注册代码" readonly="readonly"><%=list.get("jgsy_code")%></p>
			</td>
			<td class="row1">联系处（科、股）：</td>
			<td  class="row2" >
				<p label="联系处（科、股）" readonly="readonly"><%=list.get("cs_no")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">机构名称：</td>
			<td  class="row3" colspan="3"> 
				<p label="机构名称" readonly="readonly"><%=list.get("jgsy_name")%></p>
			</td>

		</tr>
		<tr>
			<td  class="row1">其他全称：</td>
			<td  class="row2" colspan="3">
				<p  label="其他全称" readonly="readonly"><%=list.get("se_name")%></p>
			</td>
		   
			<!-- <td class="row1">编制证版本：</td>
			<td  class="row2">
				<span>旧版</span> <input type="radio" name="version" onclick="javascript: return false;" checked="checked">
                <span class="span1">新版</span> <input type="radio" onclick="javascript: return false;" name="version">
			</td> -->
		</tr>
		<tr>
			<td  class="row1">习惯（规范）简称：</td>
			<td  class="row2" >
				<p  label="习惯（规范）简称" readonly="readonly"><%=list.get("st_name")%></p>
			</td>
			<td class="row1">成立时间：</td>
			<td  class="row2">
				<p label="成立时间" readonly="readonly"><%=list.get("update_time")%></p>
			</td>
		</tr>
		<!--<tr>
			<td  class="row1">网上名称：</td>
			<td  class="row3" colspan="3"> 
				<p label="网上名称" readonly="readonly">表中无对应数据</p>
			</td>

		</tr>
		 <tr>
			<td  class="row1">机构编制事项文件：</td>
			<td  class="row3" colspan="3"> 
				<p label="机构注册代码" readonly="readonly">未找到相关字段</p>
			</td>

		</tr> -->
		<tr>
			<td  class="row1">主管部门：</td>
			<td  class="row3" colspan="3"> 
				<p label="机构注册代码" readonly="readonly"><%=list.get("main_dept")%></p>
			</td>

		</tr>
		<tr>
			<td  class="row1">统一社会信用代码：</td>
			<td  class="row2" >
				<p  label="统一社会信用代码" readonly="readonly"><%=list.get("unify_code")%></p>
			</td>
			<td class="row1">组织机构代码：</td>
			<td  class="row2">
				<p label="组织机构代码" readonly="readonly"><%=list.get("orgcode")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">法人证书号：</td>
			<td  class="row2" >
				<p  label="法人证书号" readonly="readonly"><%=list.get("register")%></p>
			</td>
			<td class="row1">机构设立文号：</td>
			<td  class="row2">
				<p label="机构设立文号" readonly="readonly"><%=list.get("spw_num")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">机构编制管理证类型：</td>
			<td  class="row2" >
				<p  label="机构编制管理证类型" readonly="readonly">未找到相关字段</p>
			</td>
			<td class="row1">机构规格：</td>
			<td  class="row2">
				<p label="机构规格" readonly="readonly"><%=list.get("unit_jb") %></p>
			</td>
		</tr>
		<tr class="government">
			<td  class="row1">机构编制管理证类型：</td>
			<td  class="row2" >
				<p  label="机构编制管理证类型" readonly="readonly">未找到相关字段</p>
			</td>
			<td class="row1">机构设立文号：</td>
			<td  class="row2">
				<p label="机构设立文号" readonly="readonly"><%=list.get("spw_num")%></p>
			</td>
		</tr>
		<tr class="government">
			<td  class="row1">机构规格：</td>
			<td  class="row2" >
				<p  label="机构规格" readonly="readonly">未找到相关字段</p>
			</td>
			<td class="row1">机构编码：</td>
			<td  class="row2">
				<p label="机构编码" readonly="readonly"><%=list.get("jgsy_code")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">隶属关系：</td>
			<td  class="row2" >
				<p  label="隶属关系" readonly="readonly"><%=list.get("dep_code")%></p>
			</td>
			<td class="row1">经费形式：</td>
			<td  class="row2">
				<p label="经费形式" readonly="readonly"><%=list.get("outlayid")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">机构属性：</td>
			<td  class="row3" colspan="3"> 
				<p label="机构属性" readonly="readonly"><%=list.get("jgsy_type")%></p>
			</td>

		</tr>
		<tr class="government">
			<td  class="row1">机构性质：</td>
			<td  class="row2" >
				<p  label="机构性质" readonly="readonly"><%=list.get("typeid")%></p>
			</td>
			<td class="row1">机构管理类别：</td>
			<td  class="row2">
				<p label="机构管理类别" readonly="readonly"><%=list.get("intypeid")%></p>
			</td>
		</tr>
		<tr class="public">
			<td  class="row1">事业单位类别：</td>
			<td  class="row2" >
				<p  label="事业单位类别" readonly="readonly"><%=list.get("dwlb")%></p>
			</td>
			<td class="row1">行业分类：</td>
			<td  class="row2">
				<p label="行业分类" readonly="readonly">未找到相关字段</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">编制数：</td>
			<td  class="row2" >
				<p  label="编制数" readonly="readonly"><%=sums.get("bzs")%>&nbsp;名</p>
			</td>
			<td class="row1">实有人数：</td>
			<td  class="row2">
				<p label="实有人数" readonly="readonly"><%=sums.get("sys") %>&nbsp;人</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">部门领导职数：</td>
			<td  class="row2" >
				<p  label="部门领导职数" readonly="readonly"><%=sums.get("bmldzs") %>&nbsp;名</p>
			</td>
			<td class="row1">部门领导实有数：</td>
			<td  class="row2">
				<p label="部门领导实有数" readonly="readonly"><%=sums.get("bmldsys") %>&nbsp;人</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">内设机构数：</td>
			<td  class="row2" >
				<p  label="内设机构数" readonly="readonly"><%=sums.get("nsjgs") %>&nbsp;个</p>
			</td>
			<td class="row1">内设机构领导职数：</td>
			<td  class="row2">
				<p label="内设机构领导职数" readonly="readonly"><%=sums.get("nsjgldzs") %>&nbsp;名</p>
			</td>
		</tr>
		<tr>
			<td  class="row1">变动类型代码：</td>
			<td  class="row2" >
				<p  label="变动类型代码" readonly="readonly"><%=list.get("bdlx")%></p>
			</td>
			<td class="row1">变动时间：</td>
			<td  class="row2">
				<p label="变动时间" readonly="readonly"><%=list.get("bd_time")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">单位类别代码：</td>
			<td  class="row2" >
				<p  label="单位类别代码" readonly="readonly"><%=list.get("dwlb")%></p>
			</td>
			<td class="row1">法定代表人：</td>
			<td  class="row2">
				<p label="法定代表人" readonly="readonly"><%=list.get("fdr")%></p>
			</td>
		</tr>
			<tr>
			<td  class="row1">其他选项：</td>
			<td  class="row3" colspan="3"> 
				<span>直属标志:</span> <input id="zsTag" type="checkbox" name="options" onclick="javascript: return false;" >
                <span class="span2">开发区(园区):</span> <input   type="checkbox" name="options" onclick="javascript: return false;">
			    <span class="span2">特殊机构:</span> <input id="flag" type="checkbox" name="options" onclick="javascript: return false;">
			</td>

		</tr>
		<tr>
			<td  class="row1">邮编：</td>
			<td  class="row2" >
				<p  label="邮编" readonly="readonly"><%=list.get("postalcode")%></p>
			</td>
			<td class="row1">地址：</td>
			<td  class="row2">
				<p label="地址" readonly="readonly"><%=list.get("address")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">联系人：</td>
			<td  class="row2" >
				<p  label="联系人" readonly="readonly"><%=list.get("linkman")%></p>
			</td>
			<td class="row1">电话：</td>
			<td  class="row2">
				<p label="电话" readonly="readonly"><%=list.get("phone")%></p>
			</td>
		</tr>
		<tr>
			<td  class="row1">备注：</td>
			<td  class="row2" >
				<p  label="备注" readonly="readonly"><%=list.get("remark")%></p>
			</td>
			<td class="row1">排序号：</td>
			<td  class="row2">
				<p label="排序号" readonly="readonly"><%=list.get("px_no")%></p>
			</td>
		</tr>
		
	</table>
	</div>
</body>

</html>