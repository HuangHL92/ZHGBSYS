<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>

<body>

<div id="border">
<table cellspacing="2" width="960" align="center" style="width: 100%">
	<tr>
		<td>
			<table>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:textEdit property="a03011" label="报考准考证号"></odin:textEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03021" label="行政职业能力分数"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03095" label="申论分数"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03027" label="其他科目分数"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03014" label="专业能力测试分数"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03017" label="公共科目笔试成绩总分"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03018" label="专业考试成绩"></odin:numberEdit>
					</td>
				</tr>
				<tr>
					<td><div style="width:300px"></div></td>
					<td>
						<odin:numberEdit property="a03024" label="面试成绩"></odin:numberEdit>
					</td>
				</tr>
			</table>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
</script>
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
}
</style>



