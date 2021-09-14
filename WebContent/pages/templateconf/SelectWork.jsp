<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>

<script type="text/javascript">
   

</script>

<div id="btnToolBarDiv" ></div>
			<odin:editgrid2  property="TrainingInfoGrid" height="500" title="" autoFill="true" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="20"  url="/" >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="personcheck"  />
					<odin:gridDataCol name="gwcode"  />
					<odin:gridDataCol name="gwname" isLast="true"/>
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				    <odin:gridEditColumn2 locked="true" header="selectall" width="20"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false"/>
				  	<odin:gridEditColumn2 header="职位代码" dataIndex="gwcode" editor="text" edited="false" width="20" hidden="true"/>
				  	<odin:gridEditColumn2 header="职位名称" align="center" dataIndex="gwname" editor="text" edited="false" width="120" isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid2 >
</div>


<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="保存" id="save" icon="images/icon/table.gif" isLast="true"/>
</odin:toolBar>

<odin:hidden property="a0000" value=""/>
<odin:hidden property="b0111" value=""/>

<script type="text/javascript">



</script>

