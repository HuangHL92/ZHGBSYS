<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>


<odin:toolBar property="toolBar1" applyTo="tol1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>

	<table cellspacing="2" width="440" align="left" style="margin-top: 40px;margin-left: 40px;">
		
		<!-- A01��Ϣ -->
		<tr>
			
			<odin:hidden property="a0000" title="id(a0000" ></odin:hidden>
			<tags:PublicTextIconEdit property="a0115a" label="�ɳ���" codetype="ZB01" readonly="true" onchange="a0115aChange" codename="code_name3"></tags:PublicTextIconEdit>	
			
			<td>
		 		<tags:PublicTextIconEdit property="a0122" codetype="ZB139" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;רҵ�����๫��Ա��ְ�ʸ�" readonly="true" />
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0120" codetype="ZB134" label="����" readonly="true"/>
			<td>
		 		<odin:NewDateEditTag property="a2949"  label="����Ա�Ǽ�ʱ��" isCheck="true" maxlength="8" required="true"></odin:NewDateEditTag>	
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		
					
		<!-- A99Z1��Ϣ -->				
		<%-- <tr>
			<td>
				&nbsp;
			</td>
			<td>
				<odin:hidden property="a99Z100" title="id(a99Z100" ></odin:hidden>
				<input type="checkbox" name="a99z101" id="a99z101"/>
				<label id="a99z103SpanId" for="a99z101" style="font-size: 12px;">�Ƿ�¼</label>
			</td>
			<td>
		 		<odin:NewDateEditTag property="a99z102"  label="¼��ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				<input type="checkbox" name="a99z103" id="a99z103"/>
				<label id="a99z103SpanId" for="a99z103" style="font-size: 12px;">�Ƿ�ѡ����</label>
			</td>
			<td>
				<odin:NewDateEditTag property="a99z104" label="����ѡ����ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			</td>
		</tr> --%>
	</table>

</div>


<script type="text/javascript">
Ext.onReady(function(){
	
	$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap1').className = "divcover_wrap";
	}
	
	$h.applyFontConfig($h.spFeildAll.a29);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a29);
	}
	
	document.getElementById('tol1').style.width = document.body.clientWidth - 2 ;	
});



</script>

<div id="cover_wrap1"></div>