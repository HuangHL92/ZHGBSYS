<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 1172px !important;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" charset="gbk" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>
<script type="text/javascript">
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
function setA6004Value(record,index){//学历
	Ext.getCmp('a6004').setValue(record.data.value);
}
function setA6006Value(record,index){//学位
	Ext.getCmp('a6006').setValue(record.data.value);
}
function setA6108Value(record,index){//学历
	Ext.getCmp('a6108').setValue(record.data.value);
}
function setA6110Value(record,index){//学位
	Ext.getCmp('a6110').setValue(record.data.value);
}
function saveTrain(){
	var a1107 = document.getElementById('a1107').value;//培训起始时间
	var a1111 = document.getElementById('a1111').value;//培训结束时间
	var text1 = dateValidate(a1107);
	var text2 = dateValidate(a1111);
	if(text1!==true){
		parent.$h.alert('系统提示','培训起始时间' + text1, null,400);
		return false;
	}
	if(text2!==true){
		parent.$h.alert('系统提示','培训结束时间' + text2, null,400);
		return false;
	}
	radow.doEvent('saveA11.onclick');
}
//弹出窗口不是父级， 无法显示在当前位置。
function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		parent.$h.alert("系统提示","请选择一行数据！");
		return;
	}
	parent.Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}
</script>
<br/><br/>

<odin:groupBox property="s1" title="考试录用人员信息">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a6001" label="考试录用人员" codeType="XZ09"/>
			<odin:NewDateEditTag property="a6002" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录用时间" maxlength="80"/>
<%-- 			<odin:textEdit property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;录用时政治面貌" validator="a6003Length" />
 --%>			<odin:select2 property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;录用时政治面貌" labelSpanId="a6003SpanId" validator="a6003Length"  codeType="GB4762" ></odin:select2>
			
			<tags:PublicTextIconEdit property="a6009" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人员来源情况" codetype="ZB146" readonly="true" />
		</tr>
		<tr>
		
			<tags:PublicTextIconEdit property="a6005" label="录用时学历代码" onchange="setA6004Value" codetype="ZB64" readonly="true" />
			<odin:textEdit property="a6004" label="录用时学历名称" validator="a6004Length" />
			
			<tags:PublicTextIconEdit property="a6007" label="录用时学位代码" onchange="setA6006Value" codetype="GB6864" readonly="true" />
			<odin:textEdit property="a6006" label="录用时学位名称" validator="a6006Length" />
			
		</tr>
		<tr>
			<odin:numberEdit property="a6008" label="&nbsp;&nbsp;&nbsp;&nbsp;录用时基层工作时间" maxlength="2" />
			<odin:select2 property="a6010" label="是否服务基层项目人员" codeType="XZ09" />
			<odin:select2 property="a6011" label="是否退役士兵" codeType="XZ09" />
			<odin:select2 property="a6012" label="是否退役大学生士兵" codeType="XZ09" />
		</tr>
		<tr>
			<odin:select2 property="a6013" label="是否残疾人" codeType="XZ09"/>
			<odin:select2 property="a6014" label="是否有海外留学经历" codeType="XZ09"/>
			<odin:numberEdit property="a6015" label="留学年限" maxlength="2"/>
			<odin:select2 property="a6016" label="是否有海外工作经历" codeType="XZ09"/>
		</tr>
		<tr>
			<odin:textEdit property="a6017" label="海外工作年限" validator="a6_101716Length"/>
		</tr>
		<tr>
			<odin:textEdit property="a6401" label="报考准考证号" validator="a6401Length" maxlength="50"/>
			<odin:numberEdit property="a6402" label="行政职业能力分数" maxlength="3"/>
			<odin:numberEdit property="a6403" label="申论分数" maxlength="3"/>
			<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3" />
		</tr>
		<tr>
			<odin:numberEdit property="a6405" label="专业能力测试分数" maxlength="3"/>
			<odin:numberEdit property="a6406" label="公共科目笔试成绩总分" maxlength="3"/>
			<odin:numberEdit property="a6407" label="专业考试成绩" maxlength="3"/>
			<odin:numberEdit property="a6408" label="面试成绩" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="s2" title="选调生信息">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2970" label="选调生" codeType="ZB137" />
			<odin:select2 property="a2970a" label="选调生来源" codeType="ZB138" />
			<odin:textEdit property="a2970b" label="选调生初始工作单位" validator="a2970bLength"></odin:textEdit>
			<odin:NewDateEditTag property="a6104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入选调生时间" maxlength="8" />
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a6109" label="选调时学历代码" onchange="setA6108Value" codetype="ZB64" readonly="true" />
			<odin:textEdit property="a6108" label="选调时学历名称" validator="a6108Length"></odin:textEdit>
			<tags:PublicTextIconEdit property="a6111" label="录用时学位代码" onchange="setA6110Value" codetype="GB6864" readonly="true" />
			<odin:textEdit property="a6110" label="录用时学位名称" validator="a6110Length"/>
		</tr>
		<tr>
			<odin:numberEdit property="a2970c" label="在基层乡镇机关工作时间" maxlength="4">年</odin:numberEdit>
<%-- 			<odin:textEdit property="a6107" label="选调时政治面貌" validator="a6107Length"></odin:textEdit>
 --%>			<odin:select2 property="a6107" label="选调时政治面貌" labelSpanId="a6107SpanId" validator="a6107Length"   codeType="GB4762" ></odin:select2>
			
			<odin:select2 property="a6112" label="是否退役大学生士兵" codeType="XZ09"/>
			<odin:select2 property="a6113" label="是否有海外留学经历" codeType="XZ09"/>
		</tr>
		<tr>
			<odin:numberEdit property="a6114" label="留学年限" maxlength="2"/>
			<odin:select2 property="a6115" label="是否有海外工作经历" codeType="XZ09"/>
			<odin:textEdit property="a6116" label="海外工作年限" validator="a6_101716Length"/>
		</tr>
		<tr>
			<odin:textEdit property="a6401_1" label="报考准考证号" validator="a6401_1Length" maxlength="25"/>
			<odin:numberEdit property="a6402_1" label="行政职业能力分数" maxlength="3"/>
			<odin:numberEdit property="a6403_1" label="申论分数" maxlength="3"/>
			<odin:numberEdit property="a6404_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3"/>
		</tr>
		<tr>
			<odin:numberEdit property="a6405_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专业能力测试分数" maxlength="3"/>
			<odin:numberEdit property="a6406_1" label="公共科目笔试成绩总分" maxlength="3"/>
			<odin:numberEdit property="a6407_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专业考试成绩" maxlength="3"/>
			<odin:numberEdit property="a6408_1" label="面试成绩" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="s3" title="公开遴选信息">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2950" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公开遴选" codeType="XZ09"></odin:select2>
			<odin:select2 property="a6202" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;遴选类别" codeType="ZB142" />
			<odin:select2 property="a6203" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;遴选方式" codeType="ZB143" />
			<odin:select2 property="a6204" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原单位层级" codeType="ZB141" />
		</tr>
		<tr>
			<odin:NewDateEditTag property="a6205" label="遴选时间" maxlength="8"/>
		</tr>
	</table>

</odin:groupBox>
<odin:groupBox property="s4" title="公开选调信息">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2951" label="公开选调" codeType="XZ09"></odin:select2>
			<odin:select2 property="a6302" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公开选调类别" codeType="ZB142" />
			<odin:select2 property="a6303" label="原单位类别" codeType="ZB144" />
			<odin:select2 property="a6304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原单位层级" codeType="ZB141" />
		</tr>
		<tr>
			<odin:select2 property="a6305" label="原单位职称或职务" codeType="ZB145" />
			<odin:NewDateEditTag property="a6306" label="公开选调时间" maxlength="8"/>
			<odin:select2 property="a6307" label="是否有海外留学经历" codeType="XZ09"/>
			<odin:numberEdit property="a6308" label="留学年限" maxlength="2"/>
		</tr>
		<tr>
			<odin:select2 property="a6309" label="&nbsp;&nbsp;&nbsp;&nbsp;是否有海外工作经历" codeType="XZ09"/>
			<odin:textEdit property="a6310" label="海外工作年限" validator="a6_101716Length"/>
		</tr>
	</table>
</odin:groupBox>
<!-- 
<odin:groupBox property="s5" title="考试信息">
	<table cellspacing="2" width="100%" align="center">
		<tr>
			<odin:numberEdit property="a6401" label="报考准考证号" maxlength="25"/>
			<odin:numberEdit property="a6402" label="行政职业能力分数" maxlength="3"/>
			<odin:numberEdit property="a6403" label="申论分数" maxlength="3"/>
			<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他科目分数" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
 -->
<odin:groupBox property="s6" title="培训信息">
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="&nbsp;&nbsp;删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar>

<!--<div style="border: 1px solid #99bbe8;">-->
<div id="border">
<div id="tol2" align="left"></div>
<%--<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>--%>
<odin:hidden property="a1100" title="主键id"></odin:hidden>

<table cellspacing="2" width="460" align="left">
	<tr>
		<odin:textEdit property="a1131" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训班名称" validator="a1131Length"></odin:textEdit>
		<odin:select2 property="a1101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训类别" codeType="ZB29"></odin:select2>
		<odin:textEdit property="a1114" label="培训主办单位" validator="a1114Length"></odin:textEdit>
		<odin:textEdit property="a1121a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训机构名称" validator="a1121aLength"></odin:textEdit>
	</tr>
	<tr>
		<odin:NewDateEditTag property="a1107" isCheck="true" label="培训起始时间" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" isCheck="true" label="培训结束时间" maxlength="8"></odin:NewDateEditTag>
		<odin:numberEdit property="a1107c" label="培训时长" decimalPrecision="1" maxlength="4"></odin:numberEdit>
	    <odin:numberEdit property="a1108" label="学时" decimalPrecision="1" maxlength="4"></odin:numberEdit>
	<%--
		<td align="right" style="padding-right: 8px;"><span id="a1107SpanId" style="font-size: 12" >培训时间</span> </td>
		<td colspan="3" align="left">
			<table cellpadding="0" cellspacing="0">
			  <tr style="padding-left: 0px;margin-left: 0px;">
			    <odin:numberEdit property="a1107" validator="dateValidate" maxlength="8" >至</odin:numberEdit>
				<odin:numberEdit property="a1111" validator="dateValidate" maxlength="8">&nbsp;&nbsp;&nbsp;</odin:numberEdit>
				<td><odin:button text="更新学时" property="count"></odin:button> </td>
			  </tr>
			</table>
		</td>
	--%>
	</tr>
	<tr style="display: none;">
	    <odin:textEdit property="a1107a" label="" readonly="true">月</odin:textEdit>
		<odin:textEdit property="a1107b" label="零" readonly="true">天</odin:textEdit>
	</tr>
	<tr>
	    <odin:select2 property="a1127" label="培训机构类别" codeType="ZB27"></odin:select2>
	    <odin:select2 property="a1104" label="培训离岗状态" codeType="ZB30"></odin:select2>
	     <odin:select2 property="a1151" label="出国（出境）培训标识" data="['1','是'],['0','否']"></odin:select2>
	</tr>
	<tr>
	   
	</tr>
	<tr>
		<td colspan="8">
			
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
			 height="230">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1100" />
			  		<odin:gridDataCol name="a1101" />
			  		<odin:gridDataCol name="a1131" />
			  		<odin:gridDataCol name="a1107" />
			  		<odin:gridDataCol name="a1111" />
			  		<odin:gridDataCol name="a1107a" />
			  		<odin:gridDataCol name="a1107b" />
			  		<odin:gridDataCol name="a1107c" type="float"/>
			  		<odin:gridDataCol name="a1108" type="float"/>
			  		<odin:gridDataCol name="a1114" />
			  		<odin:gridDataCol name="a1121a" />
			  		<odin:gridDataCol name="a1127" />
			  		<odin:gridDataCol name="a1104" />			
			   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text"  width="100" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="培训类别" dataIndex="a1101" editor="select" codeType="ZB29" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训班名称" dataIndex="a1131" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训起始时间" dataIndex="a1107" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训结束时间" dataIndex="a1111" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训时长（月）" dataIndex="a1107a" editor="text" edited="false" width="100" hidden="true"/>
				  <odin:gridEditColumn header="天" dataIndex="a1107b" editor="text" width="100" edited="false" hidden="true"/>
				  <odin:gridEditColumn header="培训时长" dataIndex="a1107c" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="学时" dataIndex="a1108" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训主办单位" dataIndex="a1114" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="培训机构名称" dataIndex="a1121a" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn2 header="培训机构类别" dataIndex="a1127" editor="select" edited="false" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn2 header="培训离岗状态" dataIndex="a1104" editor="select" edited="false" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn2 header="出国（出境）培训标识" dataIndex="a1151" editor="select" edited="false" selectData="['1','是'],['0','否']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</odin:groupBox>
<script type="text/javascript">
 Ext.onReady(function(){
 	
 	applyFontConfig();
 	if(parent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.orthers);
	}
 	$h.fieldsDisabled(parent.fieldsDisabled);
 });
 function applyFontConfig(){
	var cls = 'fontConfig';
	if(parent.isveryfy){
		cls = 'vfontConfig';
	}
	//var $ = parent.$;
	var spFeild = parent.spFeild;
	for(var i_=0;i_<spFeild.length;i_++){
		if(document.getElementById(spFeild[i_]+'SpanId_s')){
			$('#'+spFeild[i_]+'SpanId_s').addClass(cls);
		}else if(document.getElementById(spFeild[i_]+'SpanId')){
			$('#'+spFeild[i_]+'SpanId').addClass(cls);
		}
    	
    }
}
</script>

