<%@ page contentType="text/html; charset=GBK" language="java"%>

<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%
	String ssid = (String) request.getAttribute("sid");
	//成功标志 1为成功
	String sucFlag = (String) request.getAttribute("sucFlag");
	String actiontype = (String) request.getAttribute("actiontype");
	String type = (String) request.getAttribute("type");
	String log = (String) request.getAttribute("log");
	String ctxpath = request.getContextPath();
	if(type=="0"){
		type="叶子";
	}
%>
<html>
<head>
<title>代码表信息</title>
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet"
	type="text/css">
<LINK href="<%=ctxpath%>/css/combobox.css" type=text/css rel=stylesheet>
<script src="<sicp3:rewrite forward="globals"/>"></script>
<script language="javascript">
//设置页面元素的CSS
//eleName 页面元素名称
//className 要切换的CSS名称
function setClass(eleName,clsName) {
	document.all(eleName).className = clsName;
}
function view() {
      document.forms[0].action="functionAction.do?method=findByKey";
      document.forms[0].submit();
}
function showupdate() {
      document.forms[0].action="functionAction.do?method=findByKey&actiontype=update";
      document.forms[0].submit();
      }
function showadd() {
      document.forms[0].action="functionAction.do?method=findByKey&actiontype=add";
      document.forms[0].submit();
      }
function saveupdate1() {
      if(checkValue(document.forms[0])){
      document.forms[0].action="functionAction.do?method=update&actiontype=update";
      document.forms[0].submit();
      }
      }
function saveadd1() {
      if(checkValue(document.forms[0])){
        var flag=1;
        if(document.forms[0].type.value==2){
           if(null==document.forms[0].location.value||""==document.forms[0].location.value){
             flag=0;
           }
        }
        if(flag==1){
           document.forms[0].action="functionAction.do?method=add&actiontype=add";
           document.forms[0].submit();
        }else{
           alert("当节点类型是按钮时，连接不能为空！");
        }
      }
      }
function delrole() {
      if(checkValue(document.forms[0])){
      	if (confirm("确定删除吗?")){
      		document.forms[0].action="functionAction.do?method=delete&actiontype=delete";
      		document.forms[0].submit();
      	}else{
      		return false;
      	}
      }
}

function page_init()
{
}
//-->
</script>
</head>

<sicp3:body>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
	</table>
	<!--标题部分-->

	<sicp3:title title="修改权限列表" />
	<sicp3:tabletitle title="权限列表信息" />
	<sicp3:form action="/functionAction.do?method=update" method="POST"
		onsubmit="return checkValue(this)">
		<%
			//view
			if (null == actiontype || "".equals(actiontype)) {
		%>
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="17%" height="0" align="right" nowrap><sicp3:colortxt
					value="*" />权限ID</td>
				<td width="16%" height="0" align="center"><sicp3:text
					mask="_nnnnnn" property="functionid" required="true" disable="true" /></td>
				<td width="17%" height="0" align="right" nowrap>连接<br>
				</td>
				<td width="50%" height="0" align="center"><sicp3:text
					property="location" disable="true"></sicp3:text></td>

			</tr>
			<tr>
				<td height="0" align="right" nowrap>父ID<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="_nnnnnn"
					property="parent" required="false" disable="true" /></td>
				<td width="17%" height="0" align="right" nowrap><sicp3:colortxt
					value="*" />标题<br>
				</td>
				<td width="17%" height="0" align="center"><sicp3:text
					property="title" disable="true" label="标题"></sicp3:text></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />序号<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="nnnnn"
					property="orderno" required="true" disable="true" /></td>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />类型<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="type"
					id="type" selectedValue="<%=type %>" disable="true" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>是否记日志<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="log"
					id="log" selectedValue="<%=log %>" disable="true" /></td>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />描述<br>
				</td>
				<td height="0" align="center"><sicp3:text
					property="description" disable="true" required="true"></sicp3:text></td>
			</tr>
			<td height="0" align="right" nowrap>开发人员<br>
			</td>
			<td height="0" align="center"><sicp3:text property="owner"
				disable="true"></sicp3:text></td>
			<td height="0" align="right" nowrap><br>
			</td>
			<td height="0" align="center"></td>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="update" class="buttonGray" onclick="showupdate()">修&nbsp;改</button>
				<%
				if ("1".equals(type)) {
				%> &nbsp;&nbsp;
				<button name="add" class="buttonGray" onclick="showadd()">增&nbsp;加</button>
				<%
				}
				%> &nbsp;&nbsp;
				<button name="update" class="buttonGray" onclick="delrole()">删&nbsp;除</button>
				</td>
			</tr>
		</table>
		<%
		} else if ("update".equals(actiontype)) {
		%>
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="17%" height="0" align="right" nowrap><sicp3:colortxt
					value="*" />权限ID</td>
				<td width="16%" height="0" align="center"><sicp3:text
					mask="_nnnnnn" property="functionid" required="true" disable="true" /></td>
				<td width="17%" height="0" align="right" nowrap>连接<br>
				</td>
				<td width="50%" height="0" align="center"><sicp3:text
					property="location" disable="false" maxlength="256"></sicp3:text></td>

			</tr>
			<tr>
				<td height="0" align="right" nowrap>父ID<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="_nnnnnn"
					property="parent" required="false" disable="true" /></td>
				<td width="17%" height="0" align="right" nowrap><sicp3:colortxt
					value="*" />标题<br>
				</td>
				<td width="17%" height="0" align="center"><sicp3:text
					property="title" disable="false" label="标题" maxlength="50"></sicp3:text></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />序号<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="nnnnn"
					property="orderno" required="true" disable="false" /></td>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />类型<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="type"
					selectedValue="<%=type %>" id="type1" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>是否记日志<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="log"
					id="log1" selectedValue="<%=log %>" /></td>

				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />描述<br>
				</td>
				<td height="0" align="center"><sicp3:text
					property="description" disable="false" required="true"></sicp3:text></td>
			</tr>
			<td height="0" align="right" nowrap>开发人员<br>
			</td>
			<td height="0" align="center"><sicp3:text property="owner"
				disable="false"></sicp3:text></td>
			<td height="0" align="right" nowrap><br>
			</td>
			<td height="0" align="center"></td>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="saveupdate" class="buttonGray" onclick="saveupdate1()">保&nbsp;存</button>
				&nbsp;&nbsp;
				<button name="goback" class="buttonGray" onclick="view()">返&nbsp;回</button>
				</td>
			</tr>
		</table>
		<%
		} else if ("add".equals(actiontype)) {
		%>
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td height="0" align="right" nowrap value="*">父ID<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="_nnnnnn"
					property="parent" required="false" disable="true" /></td>
				<td width="17%" height="0" align="right" nowrap><sicp3:colortxt
					value="*" />标题<br>
				</td>
				<td width="17%" height="0" align="center"><sicp3:text
					property="title" label="标题" required="true" disable="false"
					maxlength="50"></sicp3:text></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />序号<br>
				</td>
				<td height="0" align="center"><sicp3:text mask="nnnnn"
					property="orderno" label="序号" required="false" disable="false" /></td>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />类型<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="type"
					id="testttt" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>是否记日志<br>
				</td>
				<td height="0" align="center"><sicp3:codelist property="log"
					id="log2" /></td>
				<td height="0" align="right" nowrap><sicp3:colortxt value="*" />描述<br>
				</td>
				<td height="0" align="center"><sicp3:text
					property="description" disable="false" required="true" /></td>
			</tr>
			<tr>
				<td width="17%" height="0" align="right" nowrap>连接<br>
				</td>
				<td width="50%" height="0" align="center"><sicp3:text
					property="location" disable="false" maxlength="256"></sicp3:text></td>
				<td height="0" align="right" nowrap>开发人员<br>
				</td>
				<td height="0" align="center"><sicp3:text property="owner"
					disable="false"></sicp3:text></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="saveadd" class="buttonGray" onclick="saveadd1()">保&nbsp;存</button>
				&nbsp;&nbsp; <!--  button name="goback" class="buttonGray" onclick="view()">返&nbsp;回</button-->
				</td>
			</tr>
		</table>

		<%
		}
		%>
	</sicp3:form>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
<script>
  var suc='<%=sucFlag%>';
  var action='<%=actiontype%>';
  if(suc==1){
    var title;
    var orderno;
    var sid;
    if(action=='add'){
       sid="<%=ssid%>";
       title=document.forms[0].title.value;
       orderno=document.forms[0].orderno.value;
    }
    if(action=='update'){
       sid=document.forms[0].functionid.value;
       title=document.forms[0].title.value;
       orderno=document.forms[0].orderno.value;
    }
    if(action=='delete'){
       sid="<%=ssid%>";
       title=null;
    }
    if(action=='paste'){
       sid="<%=ssid%>";
       title=null;
    }
    parent.left.resetTree(title,sid,orderno,action);
  }
</script>
