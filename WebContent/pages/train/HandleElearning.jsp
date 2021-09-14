<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: #F6F6F6;
	overflow:auto;
}
.vueBtn {
    display: inline-block;
    padding: .3em .5em;
    background-color: #6495ED;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: .3em;
    box-shadow: 0 1px white inset;
    text-align: center;
    text-shadow: 0 1px 1px black;
    color:white;
    font-weight: bold;
	cursor:pointer;
}
</style>

<div id="div_data" style="height: 300;overflow-y: scroll;">
<table style="width:100%;margin-top: 40px;">
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0101" label="姓名" width="160"></odin:textEdit>
		<odin:select2 property="a0104" label="性别" width="160" codeType="GB2261"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0184" label="身份证号码" width="160"></odin:textEdit>
		<odin:numberEdit property="g11020" label="培训年度" width="160" ></odin:numberEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:select2 property="g11027" label="现任职务层次" width="160" codeType="TrainZB09"></odin:select2>
		<odin:textEdit property="a0177" label="现任单位编码" width="160" ></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:NewDateEditTag property="a1107" isCheck="true" label="学习开始日期" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" isCheck="true" label="学习结束日期" maxlength="8"></odin:NewDateEditTag>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="g11042" label="课程名称" width="160" ></odin:textEdit>
		<odin:numberEdit property="a1108" label="获得学时数" width="160" ></odin:numberEdit>
	</tr>
</table>
<table style="width:91.3%;">
	<tr>
		<odin:textarea property="a0192a" label="现工作单位及职务全称" rows="2" colspan="237"></odin:textarea>
	</tr>
</table>
</div>
<!-- <div id="bottomDiv" style="width: 100%;">
					<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<td><div onclick="save()" class="vueBtn">保 存</div>
							</td>
						<tr>
					</table>
				</td>
			</tr>
		</table>
	</div> -->
<odin:hidden property="elearningid"/>
<script type="text/javascript">
Ext.onReady(function(){
	var div_data = document.getElementById("div_data");
	/* var bottomDiv = document.getElementById("bottomDiv"); */
	var viewSize = Ext.getBody().getViewSize();
	div_data.style.height = viewSize.height-42;
	div_data.style.width =  viewSize.width;
	/* bottomDiv.style.width =  viewSize.width; */
});
function save(){
	radow.doEvent('save');
}
function saveCallBack(){
	parent.Ext.getCmp('elearningGrid').getStore().reload();
	parent.Ext.example.msg('','保存成功',1);
	window.close();
}
</script>



