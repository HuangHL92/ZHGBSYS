<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="self"%>
<odin:MDParam></odin:MDParam>
<odin:floatDiv property="btnToolBarDiv"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="清空" id="clean" cls="x-btn-text-icon"
		isLast="true" />
</odin:toolBar>
<br><br>
<table>
	<tr>
		<odin:htmlTextEdit property="a1ab011" label="描述信息"  value="123" colspan="6" width="600" height="200">
		</odin:htmlTextEdit>
	</tr>
	<tr>
		<td colspan="6" height="4"></td>
	</tr>
	<tr>
		<self:BasicAreaQuery property="area001"/>
	</tr>
	<tr>
		<odin:textEdit property="aab001" required="true" label="单位名称"></odin:textEdit>
		<odin:numberEdit property="aab002" label="单位号"></odin:numberEdit>
	</tr>
	<tr>
		<odin:dateEdit property="aab003" label="时间" format="Ym"></odin:dateEdit>
		<odin:textEdit property="aab107" label="单位地址"></odin:textEdit>
		<odin:select property="aab004"  label="单位号" readonly="true" data="['11', '身份证'],['21', '毕业证']"></odin:select>
	</tr>
	<tr>
		<td><input type="radio" name="r1" value="1" checked="checked">1 <input type="radio"  name="r1" value="2">2</td>
	</tr>
	<tr>
		<td><input type="checkbox" id="c1" value="1" checked="checked">C1 <input type="checkbox" id="c2" value="2">C2</td>
	</tr>
	<tr>
		<odin:textEdit property="aab007" label="单位地址"></odin:textEdit>
		<td>
			<odin:radio property="aab006" value="1" label="单选"></odin:radio>
		</td>
		<td>
			<odin:radio property="aab006" value="2" label="单选2"></odin:radio>
		</td>
	</tr>
	<tr>
		<odin:textEdit property="aab008" label="单位地址2" value="哈哈哈哈"></odin:textEdit>
		<odin:checkbox property="aab005" label="复选" value="1"></odin:checkbox>
	</tr>
	<tr>
		<odin:textEdit property="data" label="data"></odin:textEdit>
		
	</tr>
	<tr> 
		<td>
			<img id="aab009" src="<%=request.getContextPath()%>/images/xiugai.gif">
		</td>
		<td>
			<odin:button text="保存" property="btn1"></odin:button> 
		</td>
	</tr>
</table>

<div id="div_1">
	<table>
		<tr>
			<odin:textEdit property="aab018" label="单位地址18"></odin:textEdit>
			<odin:textEdit property="aab028" label="单位地址28"></odin:textEdit>
		</tr>
		<tr>
			<odin:textIconEdit property="aab030" label="带图标的输入框"></odin:textIconEdit>
			<odin:textIconEdit property="type" label="类型"></odin:textIconEdit>
		</tr>
		<tr>
			<odin:hidden property="hidden1" value="hidden"/>
		</tr>
	</table>
</div>


<div id="panel_content">
<odin:groupBox title="基本信息" property="group1">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="工作" required="true"/> 
	 <odin:textEdit property="worker1" label="工作"/>
	 <odin:textEdit property="worker2" label="工作"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="单位编码"/>
	 <odin:textEdit property="name" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="性命"/>
	  <odin:select property="zjlx" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>

   </tr>
   <tr>
   	  <odin:textarea colspan="4" label="测试区域" property="tarea" rows="3" cols="50"></odin:textarea>
   </tr>	 
 </table>
 </odin:groupBox>

<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
   	   <td><odin:button property="btn2" text="提交基本信息1的数据"/></td>	
   </tr>	 
</table>

</div>

<odin:hidden property="hidden2" value="hidden"/>
<odin:hidden property="hidden3" value="hidden"/>

<odin:toolBar property="toolBar1">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="导出" handler="doExp"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="保存" id="toolBarBtn1" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel  property="mypanel" width="650" height="300" topBarId="toolBar1" title="简单Panel" collapsible="true" contentEl="panel_content"/> 


<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="基本信息" id="tab1"></odin:tabItem>
       <odin:tabItem title="相关信息" id="tab2"></odin:tabItem>
       <odin:tabItem title="基本信息" id="tab3" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
       <table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
	       <tr>
	     <odin:textEdit property="worker0r" label="工作"/>   
		 <odin:textEdit property="worker1r" label="工作"/>
		 <odin:textEdit property="worker2r" label="工作"/>
	   </tr>
	   <tr>
	     <odin:textEdit property="dwbm1r" label="单位编码" required="true"/>
		 <odin:textEdit property="name1r" label="单位名称"/>
		 <odin:textIconEdit property="dwxx" label="单位信息" iconId="btn"></odin:textIconEdit>
	   </tr>
	   </table>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
      <table id="table1">
      	<tr>
	      <odin:textEdit property="xming1r" label="姓名" />   
		  <odin:select disabled="true" property="zjlx1r" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型"></odin:select>
	      <odin:textEdit property="name21r" label="单位名称"/>
	   </tr>
	   <tr>
	     <odin:textEdit property="dw1bm1r" label="单位编码" required="true"/>
		 <odin:textEdit property="na1me1r" label="单位名称"/>
		 <odin:textIconEdit property="dw1xx" label="单位信息" iconId="btn"></odin:textIconEdit>
	   </tr>
	   <tr>
	      <odin:textEdit property="xm1ing1r" label="姓名" />   
		  <odin:select  property="zj1lx1r" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型"></odin:select>
	      <odin:textEdit property="na1me21r" label="单位名称"/>
	   </tr>
      </table>
   </odin:tabCont>  
</odin:tab>
<odin:window src="/blank.htm" title="导出" width="550" height="150" id="simpleTextExpWin"></odin:window>

<script>
function resFunc(res){
	alert(res.mainMessage);
}
function aab004Change(){
	radow.doOnComboChange('aab004');
}
var fileNameSim  = "";
var separator = "@";
var querySQLSim = "";
function doExp(){
	querySQLSim = "select * from smt_user ";
	odin.showWindowWithSrc('simpleTextExpWin',contextPath+'/sys/text/simpleExpTextWindow.jsp');
}
//window.location.href="samples/tag/htmlEditor.jsp";
</script>

