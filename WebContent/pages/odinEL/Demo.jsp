<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript">
<odin:menu property="menu_athers">
<odin:menuItem text="&nbsp;增加" icon="image/icon021a2.gif" property="loadadd11" />
<odin:menuItem text="&nbsp;修改" icon="image/icon021a6.gif"  property="rmbUpdate11"/>
<odin:menuItem text="&nbsp;删除" icon="image/icon021a3.gif" property="deletePersonBtn11" />
<odin:menuItem text="&nbsp;批量维护" icon="image/icon021a6.gif"  property="betchModifyBtn11"  />
<odin:menuItem text="&nbsp;简历提取" icon="image/icon021a6.gif"  property="getA1711" />
<odin:menuItem text="&nbsp;干部材料" icon="image/icon021a6.gif"  property="PersionFileBtn11"  />
<odin:menuItem text="&nbsp;关心关爱" icon="image/icon021a6.gif"  property="GxgaFileBtn11"/>
<odin:menuItem text="&nbsp;考核评价" icon="image/icon021a6.gif" isLast="true"  property="KHPJFileBtn11" />
</odin:menu>

</script>
<div id="publishPanel"></div>
<odin:toolBar property="btnToolBar" applyTo="publishPanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加议题" menu="menu_athers" icon="image/zjyt.png" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除标题" icon="image/delete2.png"  id="titleDelete" />
	<odin:separator></odin:separator>
	
	<odin:buttonForToolBar text="导出PDF" icon="images/search.gif" id="exportPdf" isLast="true"/>
</odin:toolBar>


<table>
  <tr>
	<odin:select2 property="select1" data="['1','选项1'],['2','选项2']"  label="下拉选1" />
  </tr>
  <tr>
	<odin:select2 property="select2" codeType="XZ09"  label="下拉选2" />
  </tr>
  <tr>
	<tags:ComBoxWithTree property="a0192e" label="现职级" readonly="true" ischecked="true" width="160" codetype="ZB148" />

  </tr>
  
</table>






