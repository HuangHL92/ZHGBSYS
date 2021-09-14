<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script type="text/javascript">

</script>



<div id="addResourceContent">
<odin:hidden property="fptconfigid"/>
<table height="100%">
	<tr>
		<td>
			<table height="100%">
				<tr>
					<odin:textEdit property="starttime" colspan="2" label="处理时间"  readonly="true"></odin:textEdit>
					<odin:textEdit property="processstatus" colspan="2" label="状态" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="endtime" colspan="2" label="结束时间" readonly="true"></odin:textEdit>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>图片迁移情况</h3>" />
	<odin:fill />
	
	<odin:buttonForToolBar id="closeBtn" isLast="true" text="关闭"
		icon="images/back.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


