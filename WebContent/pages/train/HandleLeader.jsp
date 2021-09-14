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
		<odin:textEdit property="a0101" label="�쵼����" width="160"></odin:textEdit>
		<odin:select2 property="a0104" label="�쵼�Ա�" width="160" codeType="GB2261"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0184" label="���֤����" width="160"></odin:textEdit>
		<odin:select2 property="g11027" label="����ְ����" width="160" codeType="TrainZB09"></odin:select2>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a0177" label="���ε�λ����" width="160"></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:NewDateEditTag property="g11037" isCheck="true" label="�Ͻ�̨ʱ��" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="g11038" label="������Ŀ" width="160" ></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:numberEdit property="a1107c" label="��ѵʱ��" width="160" ></odin:numberEdit>
		<odin:numberEdit property="a1108" label="���ѧʱ��" width="160" ></odin:numberEdit>
	</tr>
</table>
<table style="width:91.3%;">
	<tr>
		<odin:textarea property="a0192a" label="�ֹ�����λ��ְ�� " rows="2" colspan="205"></odin:textarea>
	</tr>
</table>
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
<odin:hidden property="leacerid"/>
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
	parent.Ext.getCmp('leaderGrid').getStore().reload();
	parent.Ext.example.msg('','����ɹ�',1);
	window.close();
}
</script>



