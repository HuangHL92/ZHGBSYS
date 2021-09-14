<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>浙大网新网上申报系统</title>
<odin:head/>
<link href="css/index_wssb.css" rel="stylesheet" type="text/css" />

</head>
<body>
<script language=javascript>
function right_review_on(index)
{
	var childDiv = document.all('content_left');
    for(i=0;i<childDiv.length;i++){
        if(index==i){
        	childDiv[i].style.display = "";
        }else{
            childDiv[i].style.display = "none";
        }
    }
}
</script>
<!-- 
<div id="content_left_bady">
<div id="content_toppic"></div>
<div id="content_centerpic">
<div id="nav_left">
<ul>
<li ><a href="#">单位申报</a></li>
<li class="hot"><a href="#">系统管理</a></li>
<li ><a href="#">系统公共</a></li>
<li ><a href="#">应用示例</a></li>
</ul>
</div>
<div id="nav_content">
<ul>
<li><a href="#"><img src="img/jiaose.gif"  border="0"><span>角色</span></a></li>
<li><a href="#"><img src="img/house.gif" border="0"><span>机构</span></a></li>
<li><a href="#"><img src="img/yongfu.gif" width="14" height="16"><span>用户</span></a></li>
<li><a href="#"><img src="img/quan.gif" border="0"><span>授权</span></a></li>
<li><a href="#"><img src="img/shouquan.gif" border="0"><span>权限列表</span></a></li>
<li><a href="#"><img src="img/password.gif"  border="0"><span>更改用户密码</span></a></li>
<li><a href="#"><img src="img/rizi.gif"  border="0"><span>日志管理</span></a></li>
<li><a href="#"><img src="img/shuju.gif"  border="0"><span>数据权限</span></a></li>
<li><a href="#"><img src="img/back.gif"  border="0"><span>通用回退</span></a></li>
</ul>
</div>
</div>
<div id="content_bottompic"></div>
</div>
 -->

<logic:notEmpty  name="EPMenuDataTree" scope="session">
<logic:iterate id="treenode" indexId="index" name="EPMenuDataTree" property="children" scope="session">
  <logic:equal name="index" value="0">
      <div id="content_left" style='display:'>
  </logic:equal>
  <logic:notEqual name="index" value="0">
      <div id="content_left" style='display:none;'>
  </logic:notEqual>
  <div id="content_left_pic">
  <div id="content_top"></div>
  <div id="nav_left">
  <ul>
  <logic:iterate id="node" indexId="indexNode" name="EPMenuDataTree" property="children" scope="session">
              <bean:define id="descText" name="node" property="text" type="String"></bean:define>
              <%
              		if(descText.length()>4){
              			descText = descText.substring(0,4);
              		}
                    int index_t = (int)index;
                    int indexNode_t = (int)indexNode;
                    if(index_t==indexNode_t){
              %>
              <li class="hot"><a href="#"><%=descText%></a></li>
              <%
                    }else{
              %>
			  <li ><a href="#" onclick="right_review_on('<bean:write name="indexNode" />');"><%=descText%></a></li>
			  <%
                    }
			  %>
              </logic:iterate>
           </ul>
			</div>
			<div id="nav_content">
			<ul>
            <logic:iterate id="secondNode" name="treenode" property="children" scope="page">
            <li><a href="#" onClick="parent.openUrl('<bean:write name="secondNode" property="text"/>','<bean:write name="secondNode" property="id"/>','<bean:write name="secondNode" property="link" />')"><img src="img/shuju.gif"  border="0"><span><bean:write name="secondNode" property="text"/></span></a></li>
            </logic:iterate>
           
            <logic:iterate id="commMenu" indexId="commIndex" name="commList" scope="session">
                <li><a href="#" onClick="parent.openUrl('<bean:write name="commMenu" property="text"/>','commMenu<bean:write name="commIndex"/>','<bean:write name="commMenu" property="url"/>')" ><img src="<bean:write name="commMenu" property="icon"/>"  border="0"><span><bean:write name="commMenu" property="text"/></span></a></li>
            </logic:iterate>        
	</ul>
	</div>
	</div>

</div>

</logic:iterate>
</logic:notEmpty>

</body>
</html>
