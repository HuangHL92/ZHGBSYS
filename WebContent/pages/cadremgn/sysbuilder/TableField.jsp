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
Ext.onReady(function(){
	document.getElementById('gbpptzbsx').firstChild.style.height='227px';//�ֶ����ɹ�������
});
function createDefine(){
	var ctci=document.getElementById("ctci").value;
	var tableinfo=document.getElementById("select1").value;
	//var tableinfo=document.getElementById("select1").value;
	//alert(tableinfo);
	var param=ctci+","+tableinfo;
	$h.openPageModeWin('createDefine','pages.cadremgn.sysbuilder.CreateDefine','����ֶ����ɶ��� ',800,550,param,'<%=request.getContextPath()%>');
<%-- 	$h.openWin('createDefine','pages.cadremgn.sysbuilder.CreateDefine','�ֶ����ɶ���',700,550, '','<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
 --%>}

</script>

<div>
	<table>
		<tr>
			<td style="vertical-align: middle;">
				<div style="width: 640px;height: 250px;">
    			<odin:groupBox property="group2" title="����ֶ��б�" >
    				<table>
    					<tr>
    						<td>
    							<odin:grid property="grid2" autoFill="false" height="220" url="/">
									<odin:gridJsonDataModel id="id2" root="data">
										<odin:gridDataCol name="rowsname" isLast="true" />
										</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn header="&nbsp;" align="left"
															edited="false" width="280" dataIndex="rowsname"
															editor="text" isLast="true" />
									</odin:gridColumnModel>
								</odin:grid>
    						</td>
							<td>
							<odin:groupBox title="ָ��������"  property="gbpptzbsx" >
								<table style="height: 180px; margin-left: 15px;width: 300px;">
									<tr>
											<odin:textEdit property="t3" label="ָ�������" width="209"></odin:textEdit>	
									</tr>
									<tr>
											<odin:textEdit property="t4" label="ָ��������" width="209"></odin:textEdit>	
									</tr>
									<tr>
											<odin:select property="s2" label="ָ��������" width="209"></odin:select>	
									</tr>
									<tr>
											<odin:textEdit property="t5" label="ָ�����" width="209"></odin:textEdit>	
									</tr>
									<tr>
										<td colspan="2">
											<table>
												<tr>
													<td>
														<odin:checkbox property="check1" label="�Ƿ�Ϊ�洢�����ֶ�"></odin:checkbox>
													</td>
													<td >
														<table>
															<tr>
														<odin:select property="checknum" label="" width="137" ></odin:select>	
															</tr>
														</table>
													</td>
												</tr>
											</table>	
										</td>
									</tr>
								</table>
							</odin:groupBox>
							</td>
						</tr>
						</table>
    			</odin:groupBox>
    			</div>
			</td>
			<td>
				<table
					style="height: 200px; margin-left: 10px; width: 100px;">
					<tr>
						<td><odin:button text="����" handler="codeAdd" property="btn1"></odin:button></td>
					</tr>
					<tr>
						<td><odin:button text="ɾ��" property="btn2" handler="codeDelete"></odin:button></td>
					</tr>
					<tr>
						<td><odin:button text="�޸�" property="btn3" handler="codeSave"></odin:button></td>
					</tr>
					<tr>
						<td><odin:button text="����" property="btn4" handler="createDefine"></odin:button></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<odin:hidden property="select1"/>
<odin:hidden property="ctci"/>
<script>
function codeAdd(){
	radow.doEvent("codeAdd");
}
function codeDelete(){
	radow.doEvent("codeDelete");
}
function codeSave(){
	radow.doEvent("codeSave");
}
</script>