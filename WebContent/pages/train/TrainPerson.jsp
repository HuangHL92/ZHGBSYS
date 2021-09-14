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
		<odin:textEdit property="a0101" label="����" width="160"></odin:textEdit>
		<odin:select2 property="a0104" label="�Ա�" width="160" codeType="GB2261"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0184" label="���֤����" width="160"></odin:textEdit>
		<td colspan="2"></td>
		<%--<odin:select2 property="g11027" label="����ְ����" width="160" codeType="TrainZB09"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0177" label="���ε�λ����" width="160" ></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:select2 property="g11028" label="�Ƿ���鳤" width="160" codeType="XZ09"></odin:select2>
		<odin:select2 property="g11029" label="�Ƿ�����ѧԱ" width="160" codeType="XZ09"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="g11032" label="ѧԱ����" width="160" ></odin:textEdit> 
		<odin:numberEdit property="a1108" label="���ѧʱ��" width="160" ></odin:numberEdit>--%>
	</tr>
</table>
<table style="width:86%;">
	<tr>
		<odin:textarea property="a0192a" label="�ֹ�����λ��ְ��ȫ�� " rows="2" colspan="239"></odin:textarea>
	</tr>
</table>
<odin:editgrid2 width="100%" property="traingrid" hasRightMenu="false"  autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/">
	<odin:gridJsonDataModel>
		 <odin:gridDataCol name="pcheck" />
		<odin:gridDataCol name="trainid" />
		<odin:gridDataCol name="g11020"/>
		<odin:gridDataCol name="a1131" />
		<odin:gridDataCol name="a1101"/>
		<odin:gridDataCol name="g11024"/>
		<odin:gridDataCol name="g11021"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="trainid" width="10" editor="text" header="����" hidden="true" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11020" width="120" header="��ѵ���" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a1131" width="350" header="�������" editor="text"  edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a1101" width="140" header="���" editor="select" codeType="ZB29" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11024" width="120" header="ѧ�ƣ��죩" editor="text"  edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11021" width="120" header="���ѧʱ��" editor="text" edited="false" align="center" isLast="true"/>		
	</odin:gridColumnModel>
  </odin:editgrid2>
<%-- 
<table style="width:86%;">
	<tr>
		<odin:textarea property="g02003" label="ѧԱ��ѵС����Ϣ " rows="8" colspan="197"></odin:textarea>
	</tr>
</table>--%>
</div>
<!-- <div id="bottomDiv" style="width: 100%;">
					<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<td><div onclick="save()" class="vueBtn">�� ��</div>
							</td>
						<tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div> -->
<odin:hidden property="personnelid"/>
<odin:hidden property="trainid"/>
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
	//alert("����ɹ�");
	parent.Ext.getCmp('grid2').getStore().reload();
	parent.Ext.example.msg('','����ɹ�',1);
	window.close();
}
</script>