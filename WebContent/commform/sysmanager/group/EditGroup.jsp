<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%
	String ssid = (String) request.getAttribute("sid");
	//�ɹ���־ 1Ϊ�ɹ�
	String sucFlag = (String) request.getAttribute("sucFlag");
	String actiontype = (String) request.getAttribute("actiontype");
%>
<html>
<head>
<title>�������Ϣ</title>
<link href="<sicp3:rewrite forward="css"/>" rel="stylesheet"
	type="text/css">

<script src="<sicp3:rewrite forward="globals"/>"></script>
<script language="javascript">
//����ҳ��Ԫ�ص�CSS
//eleName ҳ��Ԫ������
//className Ҫ�л���CSS����
function setClass(eleName,clsName) {
	document.all(eleName).className = clsName;
}
function view() {
      document.forms[0].action="groupAction.do?method=findByKey";
      document.forms[0].submit();
}
function showupdate() {
      document.forms[0].action="groupAction.do?method=findByKey&actiontype=update&groupid="+document.forms[0].groupid.value;
      document.forms[0].submit();
      }
function showadd() {
      document.forms[0].action="groupAction.do?method=findByKey&actiontype=add";
      document.forms[0].submit();
      }
function saveupdate1() {
      if(checkValue(document.forms[0])){
      document.forms[0].action="groupAction.do?method=update&actiontype=update";
      document.forms[0].submit();
      }
      }
function saveadd1() {
      if(checkValue(document.forms[0])){
           document.forms[0].action="groupAction.do?method=add&actiontype=add";
           document.forms[0].submit();
      }
      }
function delrole() {
      if(checkValue(document.forms[0])){
      if (confirm("ȷ��ɾ����?")){
      document.forms[0].action="groupAction.do?method=delete&actiontype=delete";
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
	<!--���ⲿ��-->

	<sicp3:title title="�޸�Ȩ���б�" />
	<sicp3:tabletitle title="Ȩ���б���Ϣ" />
	<%
		//view
		if (null == actiontype || "".equals(actiontype)) {
	%>

	<sicp3:form action="/groupAction.do?method=update" method="POST"
		onsubmit="return checkValue(this)">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap>���������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="parentid"
					required="true" disable="true" label="���������" />
				<td width="8%" height="0" align="right" nowrap>�������<br>
				</td>
				<td width="39%" height="0"><sicp3:text mask="________"
					property="groupid" required="true" disable="true" label="�������" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="name"
					required="true" disable="true" label="��������" />
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="description"
					disable="true" required="true" label="��������" maxlength="50" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>����������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="principal"
					required="false" disable="true" maxlength="30" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="update" class="buttonGray" onclick="showupdate()">��&nbsp;��</button>
				&nbsp;&nbsp;
				<button name="add" class="buttonGray" onclick="showadd()">��&nbsp;��</button>
				&nbsp;&nbsp;
				<button name="update" class="buttonGray" onclick="delrole()">ɾ&nbsp;��</button>
				</td>
			</tr>
		</table>
	</sicp3:form>
	<%
	} else if ("update".equals(actiontype)) {
	%>

	<sicp3:form action="/groupAction.do?method=update" method="POST"
		onsubmit="return checkValue(this)">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap>���������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="parentid"
					required="true" disable="true" label="���������" />
				<td width="8%" height="0" align="right" nowrap>�������<br>
				</td>
				<td width="39%" height="0"><sicp3:text mask="________"
					property="groupid" required="true" disable="true" label="�������" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="name"
					required="true" disable="false" label="��������" />
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="description"
					disable="false" required="true" label="��������" maxlength="50" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>����������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="principal"
					required="false" disable="false" maxlength="30" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="saveupdate" class="buttonGray" onclick="saveupdate1()">��&nbsp;��</button>
				&nbsp;&nbsp;
				<button name="goback" class="buttonGray" onclick="view()">��&nbsp;��</button>
				</td>
			</tr>
		</table>
	</sicp3:form>
	<%
	} else if ("add".equals(actiontype)) {
	%>

	<sicp3:form action="/groupAction.do?method=update" method="POST"
		onsubmit="return checkValue(this)">
		<table width="95%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tableInput">
			<tr>
				<td width="8%" height="0" align="right" nowrap>���������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="parentid"
					required="true" disable="true" label="���������" />
				<td width="8%" height="0" align="right" nowrap>�������<br>
				</td>
				<td width="39%" height="0"><sicp3:text mask="________"
					property="groupid" required="true" disable="true" label="�������"
					value="�Զ�����..." /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="39%" height="0"><sicp3:text property="name"
					required="true" disable="false" label="��������" />
				<td width="8%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>��������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="description"
					disable="false" required="true" label="��������" maxlength="50" /></td>
			</tr>
			<tr>
				<td width="8%" height="0" align="right" nowrap>����������<br>
				</td>
				<td width="40%" height="0"><sicp3:text property="principal"
					required="false" disable="false" maxlength="30" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<table width="95%" height="35" border="0" align="center"
			cellspacing="0" class="tableInput">
			<tr>
				<td class="action">
				<button name="saveadd" class="buttonGray" onclick="saveadd1()">��&nbsp;��</button>
				&nbsp;&nbsp; <!--  button name="goback" class="buttonGray" onclick="view()">��&nbsp;��</button-->
				</td>
			</tr>
		</table>
	</sicp3:form>

	<%
	}
	%>
	<sicp3:bottom />
	<sicp3:errors />
</sicp3:body>
</html>
<script>
  var suc='<%=sucFlag%>';
  var action='<%=actiontype%>';
  if(suc==1){
    var title;
    var sid;
    if(action=='add'){
       sid='<%=ssid%>';
       title=document.forms[0].name.value;
    }
    if(action=='delete'){
       sid='<%=ssid%>';
       title=null;
    }
    if(action=='update'){
       title=document.forms[0].name.value;
       sid=document.forms[0].groupid.value;
    }
    parent.left.resetTree(title,sid,sid,action);
  }
</script>
