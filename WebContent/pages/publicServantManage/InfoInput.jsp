<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@include file="/comOpenWinInit.jsp" %>
<%@page
	import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/picCut/js/jquery-1.4.2.js"></script>

<style>
.x-form-item {
	width: 100%;
	height: 100%;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">

</script>
<div style="float:left;width:280px;height:290px" >
				<odin:editgrid property="peopleInfoGrid" title="��Ա��Ϣ�б�" bbarId="pageToolBar" pageSize="20"
					autoFill="false" width="100" height="290"  hasRightMenu="true" rightMenuId="updateM" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="a0101" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="a0101" width="80" header="����"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="������λ��ְ��"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0000" width="110" header="��Աid"
							hideable="false" editor="text" align="center" isLast="true"
							hidden="true" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
</div>
<div style="float:right;width:970px">
<odin:tab id="tab">
	<odin:tabModel>
		<odin:tabItem title="��Ա������Ϣ" id="tab1"></odin:tabItem>
		<odin:tabItem title="������λ��ְ��" id="tab2"></odin:tabItem>
		<odin:tabItem title="��ְ����" id="tab3"></odin:tabItem>
		<odin:tabItem title="��ְ��" id="tab4"></odin:tabItem>
		<odin:tabItem title="רҵ����ְ��" id="tab5"></odin:tabItem>
		<odin:tabItem title="ѧ��ѧλ" id="tab6"></odin:tabItem>
		<odin:tabItem title="��������" id="tab7"></odin:tabItem>
		<odin:tabItem title="������Ϣ" id="tab8"></odin:tabItem>
		<odin:tabItem title="��ͥ��Ա" id="tab9" ></odin:tabItem>
		<odin:tabItem title="������Ϣ��" id="tab10" isLast="true"></odin:tabItem>
	</odin:tabModel>
	
	
	<%-----------------------------��Ա������Ϣ-------------------<odin:hidden property="a0000"/>------------------------------------%>
	<odin:tabCont itemIndex="tab1">
		<div id="floatToolDiv1"></div>
		<odin:toolBar property="floatToolBar1" applyTo="floatToolDiv1">
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save" isLast="true" handler="saveConfirm"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		
		<table style="width:100%;" >
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<td>&nbsp; </td>
		</tr>
		<tr>
			<odin:textEdit property="a0101" required="true" label="����"></odin:textEdit>
			<odin:textEdit property="a0184" label="���֤��" required="true" validator="$h.IDCard"></odin:textEdit>
			<odin:select2 property="a0104" required="true" label="�Ա�" codeType="GB2261"></odin:select2>
			
		</tr>
		<tr></tr>
		
		<tr>
			<odin:NewDateEditTag property="a0107" required="true" label="��������" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:select2 property="a0117" required="true" label="����" codeType="GB3304"></odin:select2>		
			<tags:PublicTextIconEdit property="a0111" label="����" codetype="ZB01" readonly="true" required="true" onchange="a0111Change" codename="code_name3"></tags:PublicTextIconEdit>
		</tr>
		
		<tr></tr>
		<tr>
			<tags:PublicTextIconEdit property="a0114" label="������" codetype="ZB01" readonly="true" required="true" onchange="a0114Change" codename="code_name3"></tags:PublicTextIconEdit>	
			<odin:textEdit property="a0140" label="�뵳ʱ��" ondblclick="a0140Click()" onkeypress="a0140Click2()"  onclick="a0140Click()"></odin:textEdit>
			<odin:NewDateEditTag property="a0134" label="�μӹ���ʱ��" isCheck="true" maxlength="8" required="true"></odin:NewDateEditTag>
		</tr>
		<tr></tr>
		<tr>
			<odin:select2 property="a0128" required="true" label="����״��" codeType="GB2261D"></odin:select2>
			<odin:textEdit property="a0187a" label="ר��"></odin:textEdit>
			<odin:select2 property="a0165" codeType="ZB130" label="�������" required="true"></odin:select2>
		</tr>
		<tr></tr>
		<tr>
			<tags:PublicTextIconEdit property="a0160" codetype="ZB125" width="160" label="��Ա���" readonly="true" required="true"/>
			<odin:select2 property="a0121" codeType="ZB135" label="��������"></odin:select2>
		</tr>
		<tr>
			<td>&nbsp; </td>
		</tr>
		<tr>
			<odin:textarea property="a1701" label="����" colspan='4' rows="16"></odin:textarea>
			
			<td class="top-last label-clor height10-359">
				<div style="width: 100%;height: 100%;position: relative;">
					<div onclick="showwin()"  title="��������" style="width:22px;height:20px;background-image:url(images/icon_column.gif);background-size:100%;  margin-bottom: 0px;position: absolute;top: 2px;left: 0px;cursor:pointer;"></div>
				</div>
			</td>
			<td class="left-last label-clor width7-120" rowspan="4">
				<div style="width:120px; height:170px;cursor: pointer;" onclick="showdialog()">
					<img alt="��Ƭ" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="136" height="170" src=""  /> 
				</div>
			</td>
		</tr>
	</table>
		
		
		
	</odin:tabCont>
	<%-----------------------------������λ��ְ��-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab2">
		<div id="floatToolDiv2"></div>
		<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save2" isLast="true" handler="saveConfirm2"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			
	</odin:tabCont>
	
	
	<%-----------------------------��ְ����-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab3">
		<odin:toolBar property="floatToolBar3" applyTo="floatToolDiv3">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save3" isLast="true" handler="saveConfirm3"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv3"></Div>
	</odin:tabCont>
	
	<%-----------------------------��ְ��-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab4">
		<odin:toolBar property="floatToolBar4" applyTo="floatToolDiv4">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save4" isLast="true" handler="saveConfirm4"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv4"></Div>
	</odin:tabCont>
	
	
	<%-----------------------------רҵ����ְ��-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab5">
		<odin:toolBar property="floatToolBar5" applyTo="floatToolDiv5">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save5" isLast="true" handler="saveConfirm5"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv5"></Div>
	</odin:tabCont>
	
	<%-----------------------------ѧ��ѧλ-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab6">
		<odin:toolBar property="floatToolBar6" applyTo="floatToolDiv6">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save6" isLast="true" handler="saveConfirm6"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv6"></Div>
	</odin:tabCont>
	
	
	<%-----------------------------��������-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab7">
		<odin:toolBar property="floatToolBar7" applyTo="floatToolDiv7">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save7" isLast="true" handler="saveConfirm7"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv7"></Div>
	</odin:tabCont>
	
	
	<%-----------------------------������Ϣ-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab8">
		<odin:toolBar property="floatToolBar8" applyTo="floatToolDiv8">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save8" isLast="true" handler="saveConfirm8"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv8"></Div>
	</odin:tabCont>
	
	
	<%-----------------------------��ͥ��Ա-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab9">
		<odin:toolBar property="floatToolBar9" applyTo="floatToolDiv9">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save9" isLast="true" handler="saveConfirm9"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv9"></Div>
	</odin:tabCont>
	
	
	<%-----------------------------������Ϣ��-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab10">
		<odin:toolBar property="floatToolBar10" applyTo="floatToolDiv10">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="����" id="save10" isLast="true" handler="saveConfirm10"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv10"></Div>
	</odin:tabCont>
	
</odin:tab>
</div>
<odin:hidden property="type" value=""/>


<script>
Ext.onReady(function(){
	
	
});


function saveConfirm(){
	
}

function saveConfirm2(){
	
}

function saveConfirm3(){
	
}

function saveConfirm4(){
	
}

function saveConfirm5(){
	
}

function saveConfirm6(){
	
}

function saveConfirm7(){
	
}

function saveConfirm8(){
	
}

function saveConfirm9(){
	
}

function saveConfirm10(){
	
}
	
	
</script>
</div>
<odin:hidden property="type" value=""/>
<odin:hidden property="ids" />
<odin:hidden property="checkIds" />

<script>
Ext.onReady(function(){
	var width=document.body.clientWidth;
	var height=document.body.clientHeight;
	Ext.getCmp("peopleInfoGrid").setHeight(height);
	Ext.getCmp("tab").setHeight(height);
	//Ext.getCmp('a0215a_combo').hide();
});
	function getTabletype(){
		var type = realParent.document.getElementById("tableType").value;
		document.getElementById("type").value = type;
	}
	
	
</script>