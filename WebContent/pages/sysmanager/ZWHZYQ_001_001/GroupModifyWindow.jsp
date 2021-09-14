<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="基本信息更改" id="tab1"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
		<table id="basicTable">
			<tr>
				<td height="5"></td>
			</tr>
			<tr>
				<odin:textEdit property="name1" label="用户组名称" required="true" />
			</tr>
			<tr>
				<td width="45" height="50"></td>
				<td><odin:button text="保存更新" property="uptBtn"></odin:button></td>
			</tr>
		</table>
   </odin:tabCont>
</odin:tab>





