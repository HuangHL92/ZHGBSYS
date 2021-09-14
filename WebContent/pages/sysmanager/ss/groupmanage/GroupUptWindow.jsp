<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="基本信息更改" id="tab1"></odin:tabItem>
       <odin:tabItem title="持有者与父类更改" id="tab2" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
		<table id="basicTable">
			<tr>
				<td height="5"></td>
			</tr>
			<tr>
				<odin:textEdit property="name1" label="用户组名称" required="true" />
				<odin:textEdit property="shortname1" label="简称" />
			</tr>
			<tr>
				<odin:select property="chargedept1" label="主管部门" codeType="AAB301"/>
				<odin:textEdit property="districtcode1" label="地区代码" required="true"/>
			</tr>
			<tr>
				<odin:textEdit property="principal1" label="机构负责人" />
				<odin:textEdit property="address1" label="地址" />
			</tr>
			<tr>
				<odin:textEdit property="tel1" label="电话" />
				<odin:textEdit property="linkman1" label="联系人" />
			</tr>
			<tr>
				<odin:textEdit property="license1" label="保留" />
				<odin:textEdit property="org1" label="系统机构编码" />
			</tr>
			<tr>
				<odin:textEdit property="desc1" label="用户组描述" width="246" colspan="4"/>
			</tr>
			<tr>
				<odin:textEdit property="otherinfo1" label="其他信息"/>
				<odin:select property="rate" label="级别"></odin:select>
			</tr>
			<tr>
				<td width="45" height="50"></td>
				<td><odin:button text="保存更新" property="uptBtn"></odin:button></td>
			</tr>
		</table>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
	<table id="speTable">
		<tr>
			<td height="30"></td>
		</tr>
		<tr>
			<odin:textEdit property="ownerName" label="持有者" />
			<odin:textEdit property="parentName" label="父类" />
		</tr>
		<tr>
			<td width="50" height="50"></td>
			<td><odin:button text="保存更新" property="uptSpeBtn"></odin:button></td>
		</tr>
      </table>
   </odin:tabCont>  
</odin:tab>



