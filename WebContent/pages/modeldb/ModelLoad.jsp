<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar id="save"  text="保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
</odin:toolBar>
<%-- <odin:floatDiv property="bar_div" position="up" ></odin:floatDiv> --%>
<odin:panel contentEl="bar_div" property="mypanel1" topBarId="btnToolBar" ></odin:panel>
<table>
	<tr>
		<td height="26"></td>
	</tr>
</table>
<div id="bar_div">
<%-- <odin:groupBox property="gb_model_base" title="主题信息录入"> --%>
	<table>
	<tr>
		<odin:hidden property="sub_libraries_model_type_old"/><!-- 原分库类型 -->
	
	    <odin:hidden property="sub_libraries_model_id"/>
		<odin:hidden property="run_state"/>
		<odin:hidden property="is_effective"/>
		<odin:hidden property="self_create_mark"/>
		<odin:hidden property="create_user_id"/>
		<odin:hidden property="create_username"/>
		<odin:hidden property="create_groupid"/>
		<odin:hidden property="create_group_name"/>
		<odin:hidden property="create_time"/>
		<odin:hidden property="change_user_id"/>
		<odin:hidden property="change_user_name"/>
		<odin:hidden property="change_time"/>
		<odin:hidden property="sub_libraries_model_key"/>
		<odin:textEdit property="sub_libraries_model_name" label="主题名称" required="true" colspan="3" size="63" maxlength="100"/> 
	</tr>
	<tr>
		<odin:select property="sub_libraries_model_type" label="分库类型" data="['1','逻辑分库'],['2','物理分库']" required="true" size="60" />
	</tr>
	<tr>
		<odin:textarea property="sub_libraries_model_info" label="主题说明"  colspan="4" rows="4" cols="63" maxlength="200" />
	</tr>
  </table>
<%-- </odin:groupBox> --%>
</div>
