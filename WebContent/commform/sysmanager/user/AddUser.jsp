<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page
	import="java.util.Iterator,com.lbs.cp.sysmanager.entity.SysGroup,com.lbs.cp.sysmanager.entity.SysRole,com.lbs.cp.sysmanager.entity.Sysdatapopedom"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script src="<sicp3:rewrite forward="leaftree"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectBase"/>"></script>
<script src="<sicp3:rewrite forward="quickSelectMgr"/>"></script>
<script src="<sicp3:rewrite forward="select"/>"></script>
<script src="<sicp3:rewrite forward="quickSelect"/>"></script>

<html>
<head>
<title>�û���Ϣ</title>
<sicp3:base />
<script language="javascript">
<!--
//����ҳ��Ԫ�ص�CSS
//eleName ҳ��Ԫ������
//className Ҫ�л���CSS����
function setClass(eleName,clsName) {
	document.all(eleName).className = clsName;
}


function confirmPassword(){
  var pd = document.userForm.passwd.value;
  var confirm = document.userForm.confirm.value;
  if(pd != confirm){
  	alert("�����ȷ�����벻һ�£�");
    return false;
  }
  return true;
}

//У���û�id
  function IsDigit(cCheck) { return (('0'<=cCheck) && (cCheck<='9')); }
	function IsAlpha(cCheck) { return ((('a'<=cCheck) && (cCheck<='z')) || (('A'<=cCheck) && (cCheck<='Z'))) }
	function VerifyInput(strUserID){
	    if (strUserID == ""){
	        alert("�����������û���");
	        document.frmUserInfo.username.focus();
	        return false;
	    }
	    for (nIndex=0; nIndex<strUserID.length; nIndex++){
	        cCheck = strUserID.charAt(nIndex);
	        if ( nIndex==0 && ( cCheck =='-' || cCheck =='_') ){
	            alert("�û������ַ�����Ϊ��ĸ������");
	            document.userForm.username.focus();
	            return false;
	        }

	        if (!(IsDigit(cCheck) || IsAlpha(cCheck) || cCheck=='-' || cCheck=='_' )){
	            alert("�û���ֻ��ʹ��Ӣ����ĸ�������Լ�-��_���������ַ�����Ϊ��ĸ������");
	            document.userForm.username.focus();
	            return false;
	        }
	    }
	    return true;
	}

function check(){

  var username = document.userForm.username.value;
  if((null == username) ||( "" == username)){
  	alert("�û�ID����Ϊ�գ�");
  	document.userForm.username.focus();
    return false;
  }

	if(!VerifyInput(username))
		return false;

   var operatorname = document.userForm.operatorname.value;
  if((null == operatorname) ||( "" == operatorname)){
  	alert("�û����Ʋ���Ϊ�գ�");
  	document.userForm.operatorname.focus();
    return false;
  }

  document.userForm.roleList.value = getSelectedData('userForm','roleListRight');
  document.userForm.roleDPPDList.value = getSelectedData('userForm','roleDPPDListRight');
  document.userForm.groupList.value = getSelectedData('userForm','groupListRight');
	document.userForm.trainOrg.value = getSelectedData('userForm','trainOrgRight');

	 if(!confirmPassword()) return false;
  var groupList = document.userForm.groupList.value;
  if((null == groupList) ||( "" == groupList)){
  	alert("������������Ϊ�գ�");
    document.userForm.groupListLeft.focus();
    return false;
  }
  return true;


}

function goBack() {
 window.location.href="<sicp3:rewrite page='/userAction.do?method=goBack'/>&stringData=" +
      	document.all("stringData").value ;
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
	<!--¼�벿��3-->

	<sicp3:title title="����û�" />
	<sicp3:tabletitle title="�û���Ϣ" />
	<%
		String stringData = request.getParameter("stringData");
		Iterator rolesIter = (Iterator) pageContext
				.findAttribute("rolesIter");
		Iterator DPPDIter = (Iterator) pageContext
				.findAttribute("DPPDIter");
		Iterator freeRolesIter = (Iterator) pageContext
				.findAttribute("freeRolesIter");
		Iterator freeDPPDRolesIter = (Iterator) pageContext
				.findAttribute("freeDPPDRolesIter");
		Iterator groupsIter = (Iterator) pageContext
				.findAttribute("groupsIter");
		Iterator freeGroupsIter = (Iterator) pageContext
				.findAttribute("freeGroupsIter");
	%>
	<table width="95%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tableInput">
		<sicp3:form action="/userAction.do?method=add">
			<tr>
				<td width="9%" height="0" align="right" nowrap><font
					color="#FF0000">*</font>�û�ID<br>
				</td>
				<td width="86%" height="0"><sicp3:text property="username"
					required="true" maxlength="16" disable="false" /> <sicp3:hidden
					property="stringData" value="<%=stringData%>" /> <sicp3:hidden
					property="roleList" /> <sicp3:hidden property="roleDPPDList" /> <sicp3:hidden
					property="groupList" /> <sicp3:hidden property="trainOrg" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>����</td>
				<td height="0" align="center"><input name="passwd"
					type="password" class="text" size="20"></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>ȷ������</td>
				<td height="0" align="center"><input name="confirm"
					type="password" class="text" size="20"></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap><font color="#FF0000">*</font>�û�����</td>
				<td height="0" align="center"><sicp3:text
					property="operatorname" disable="false" maxlength="32"></sicp3:text></td>
			</tr>
			<tr>
				<td height="25" align="right" nowrap>����</td>
				<td height="25" align="center"><sicp3:text
					property="description" disable="false" maxlength="50"></sicp3:text></td>
			</tr>
						<tr>
				<td width="9%" height="0" align="right">��������<br>
				</td>
				<td height="0"><sicp3:text property="name" style="cursor:hand"
					styleClass="text"
					onclick="setRegionTree(this,document.all.name,document.all.groupid)"
					readonly="true" /> <sicp3:hidden property="groupid" /></td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>������ɫ</td>
				<td>
				<table cellspacing="0" cellpadding="0"
					style="width:400px;height:100px" align="left">
					<tr>
						<td align="center">��ѡ��ɫ</td>
						<td align="center" style="width:10px"></td>
						<td align="center">��ѡ��ɫ</td>
					</tr>
					<tr>
						<td align="center"><select name="roleListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">
							<%
									while ((null != freeRolesIter) && (freeRolesIter.hasNext())) {
									SysRole sr = (SysRole) freeRolesIter.next();
							%>
							<option value="<%=sr.getRoleid()%>"><%=sr.getRoleName()%></option>
							<%
							}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleListLeft','roleListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleListLeft','roleListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="roleListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','roleListLeft','roleListRight',false)">
							<%
									while ((null != rolesIter) && (rolesIter.hasNext())) {
									SysRole sr = (SysRole) rolesIter.next();
							%>
							<option value="<%=sr.getRoleid()%>"><%=sr.getRoleName()%></option>
							<%
							}
							%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="0" align="right" nowrap>����Ȩ�޽�ɫ</td>
				<td>
				<table cellspacing="0" cellpadding="0"
					style="width:400px;height:100px" align="left">
					<tr>
						<td align="center">��ѡ��ɫ</td>
						<td align="center" style="width:10px"></td>
						<td align="center">��ѡ��ɫ</td>
					</tr>
					<tr>
						<td align="center"><select name="roleDPPDListLeft" multiple
							size="18"
							ondblclick="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',false)">
							<%
									while ((null != freeDPPDRolesIter) && (freeDPPDRolesIter.hasNext())) {
									Sysdatapopedom sr = (Sysdatapopedom) freeDPPDRolesIter.next();
							%>
							<option value="<%=sr.getPopedomid()%>"><%=sr.getPopedomname()%></option>
							<%
							}
							%>
						</select></td>
						<td style="width:10px" align="center">
						<table>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',false)">&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveRight('userForm','roleDPPDListLeft','roleDPPDListRight',true)">&#62;&#62;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',false)">&#60;</a></td>
							</tr>
							<tr>
								<td><a class="ALink"
									href="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',true)">&#60;&#60;</a></td>
							</tr>
						</table>
						</td>
						<td align="left"><select name="roleDPPDListRight" multiple
							size="18"
							ondblclick="javascript:moveLeft('userForm','roleDPPDListLeft','roleDPPDListRight',false)">
							<%
									while ((null != DPPDIter) && (DPPDIter.hasNext())) {
									Sysdatapopedom sr = (Sysdatapopedom) DPPDIter.next();
							%>
							<option value="<%=sr.getPopedomid()%>"><%=sr.getPopedomname()%></option>
							<%
							}
							%>
						</select></td>
					</tr>
				</table>
				</td>
			</tr>
	</table>
	<table width="95%" height="35" border="0" align="center"
		cellspacing="0" class="tableInput">
		<tr>
			<td class="action"><sicp3:submit onclick="return check()"
				styleClass="buttonGray">��&nbsp;��</sicp3:submit> <input type="button"
				name="close" value="�� ��" onclick="goBack()" class="buttonGray">
			</td>
		</tr>
		</sicp3:form>
	</table>
	<sicp3:bottom />
	<sicp3:errors />
	</sicp3:body>
</html>
