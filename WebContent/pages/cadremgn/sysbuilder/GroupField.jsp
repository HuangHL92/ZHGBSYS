<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
#grid1,#grid2 {
	width: 316px !important;
}
#area1 {
	width: 425px !important;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
function createDefine(){
	var ctci=document.getElementById("ctci").value;
	var tableinfo=document.getElementById("select1").value;
	//alert(tableinfo);
	var param=ctci+","+tableinfo;
	$h.openPageModeWin('createDefine','pages.cadremgn.sysbuilder.CreateDefine','����ֶ����ɶ��� ',800,550,param,'<%=request.getContextPath()%>');
	        <%-- $h.openWin('createDefine','pages.cadremgn.sysbuilder.CreateDefine','�ֶ����ɶ���',980,550, param,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false}); --%>
}

</script>
<div>
	<odin:hidden property="ctci"/>
</div>
<div>
<table>
<tr>
<td>
<div style="width: 640px;height: 280px;">
	<odin:groupBox property="group1" title="����ֶ�" >

	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td colspan="2" style="margin-top: 2px;"><font style="font-size: 13px;width: 314;">ѡ������ֶ���Ϣ��</font>
						</td>
					</tr>
					<tr>
						<odin:select property="select1" width="314" onchange="changeValue();"></odin:select>
					</tr>
					<tr>
						<td colspan="2"><font style="font-size: 13px;width: 314;">ѡ������ֶ�</font>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<odin:grid property="grid1" autoFill="false" height="260" width=""
							url="/">
							<odin:gridJsonDataModel id="id2" root="data">
								<odin:gridDataCol name="rowsname" isLast="true" />
								</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn header="ָ������" align="left"
									edited="false" width="265" dataIndex="rowsname"
									editor="text" isLast="true" />
							</odin:gridColumnModel>
						</odin:grid>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<odin:groupBox title="ָ��������">
					<table style="height: 245px;width: 300px;">
						<tr>
							<td>
								<odin:textEdit property="t3" label="ָ�������" width="200" onchange="changeCodeName();"></odin:textEdit>	
							</td>
						</tr>
						<tr>
							<td>
								<odin:textEdit property="t4" label="ָ��������" width="200"></odin:textEdit>	
							</td>
						</tr>
						<tr>
							<td>
								<odin:select property="s2" label="ָ��������" width="200" codeType="TC01" onchange="changeCodeType();"></odin:select>	
							</td>
						</tr>
						<tr>
							<td>
								<odin:select property="s3" label="�������" width="200" onchange="changeColDataTypeShould();"></odin:select>	
							</td>
						</tr>
						<tr>
							<td>
								<odin:textEdit property="t5" label="ָ�����" width="200"></odin:textEdit>	
							</td>
						</tr>
						<tr>
							<td>
								<odin:textEdit property="t6" label="��ʾ���" width="200"></odin:textEdit>	
							</td>
						</tr>
						<tr>     
							<td colspan="3">
								<table style="aligin:center">
								  	<tr>
								  	<td>
										<odin:checkbox property="check1" label="�Ƿ�Ϊ�洢�����ֶ�" onclick="changecheck();"></odin:checkbox>
									</td>
									<td>
										<odin:select property="checknum" label="" width="137" ></odin:select>	
									</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</odin:groupBox>
				<table style="height: 25px;width: 320px;">
					<tr  align="center">
						<td style="width:100;"><odin:button text="����" property="btn1" handler="codeAdd"></odin:button></td>
						<td style="width:100;"><odin:button text="ɾ��"  property="btn2" handler="codeDelete"></odin:button></td>
						<td style="width:100;"><odin:button text="����"  property="btn3" handler="codeSave"></odin:button></td>
						<td style="width:100;"><odin:button text="����"  property="btn4" handler="createDefine"></odin:button></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</odin:groupBox>
</div>
</td>
</tr>
</table>
</div>

<script type="text/javascript">
function changecheck(){
	radow.doEvent("changecheck");
}

function changeValue(){
	radow.doEvent("changeValue");
}

function codeSave(){
	radow.doEvent("codeSave");
}

function codeAdd(){
	radow.doEvent("codeAdd");
}

function codeDelete(){
	radow.doEvent("codeDelete");
}

function changeCodeType(){
	radow.doEvent("changeCodeType");
}
function changeColDataTypeShould(){
	radow.doEvent("changeColDataTypeShould");
}
function changeCodeName(){
	radow.doEvent("changeCodeName");
}

</script>
