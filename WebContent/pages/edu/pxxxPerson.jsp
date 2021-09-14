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

<div id="div_data" style="height: 500;">
<table style="width:100%;margin-top: 40px;">
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0101" label="姓名" width="160" readonly="true"></odin:textEdit>
		<odin:select2 property="a0104" label="性别" width="160" codeType="GB2261" readonly="true"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0184" label="身份证号码" width="160" readonly="true"></odin:textEdit>
		<td colspan="2"></td>

	</tr>
</table>
<table style="width:86%;">
	<tr>
		<odin:textarea property="a0192a" label="现工作单位及职务全称 " rows="2" colspan="239" readonly="true"></odin:textarea>
	</tr>
</table>
<odin:editgrid2 width="100%" property="traingrid" hasRightMenu="false"  autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="xrdx00" />
		<odin:gridDataCol name="xrdx01" />
		<odin:gridDataCol name="year"/>
		<odin:gridDataCol name="xrdx02"/>
		<odin:gridDataCol name="xrdx03" />
		<odin:gridDataCol name="xrdx04"/>
		<odin:gridDataCol name="xrdx05"/>
		<odin:gridDataCol name="xrdx06"/>
		<odin:gridDataCol name="xrdx08"/>
		<odin:gridDataCol name="xrdx09"/>
		<odin:gridDataCol name="xrdx10"/>
		<odin:gridDataCol name="xrdx11"/>
		<odin:gridDataCol name="xrdx12" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="xrdx00" width="10" editor="text" header="主键" hidden="true" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="year" width="80" header="培训<br/>年度" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx01" width="350" header="班次名称" editor="text"  edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx02" width="120" header="班次类型" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx05" width="120" header="开班时间" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx06" width="120" header="结业时间" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx09" width="60" header="学时" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="xrdx10" width="120" header="培训地点" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
  </odin:editgrid2>
<%-- 
<table style="width:86%;">
	<tr>
		<odin:textarea property="g02003" label="学员培训小结信息 " rows="8" colspan="197"></odin:textarea>
	</tr>
</table>--%>
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
	</div>
</div> -->
<odin:hidden property="a0000"/>

<script type="text/javascript">
Ext.onReady(function(){
	var div_data = document.getElementById("div_data");
	/* var bottomDiv = document.getElementById("bottomDiv"); */
	var viewSize = Ext.getBody().getViewSize();
	div_data.style.height = viewSize.height-42;
	div_data.style.width =  viewSize.width;
	/* bottomDiv.style.width =  viewSize.width; */
});


</script>