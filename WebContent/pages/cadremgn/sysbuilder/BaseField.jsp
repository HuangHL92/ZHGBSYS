<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
#grid2 {
	width: 316px !important;
}
#area1 {
	width: 425px !important;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">


</script>
<odin:hidden property="tt"/>    
<odin:hidden property="ttt"/>
<div>
	<table>
		<tr>
			<td>
				<div style="width: 640px;height: 200px;">
    			<odin:groupBox property="group1" title="��Ϣ��" >
    				<table>
    					<tr>
    						<td colspan="2" style="margin-top: 2px;"><font style="font-size: 13px;width: 300px;">ѡ����ϢȺ</font>
										</td>
    					</tr>
    					<tr>
    						<td style="vertical-align:top">
    							<table>
    								<tr>
    										<odin:select property="select1" width="314" onchange="changeValue();"></odin:select>
    								</tr>
    								<tr>
										<td colspan="2"><font style="font-size: 13px;width: 300px;">ѡ����Ϣ��</font>
										</td>
									</tr>
    								<tr>
    									<td colspan="2">
    										<odin:grid property="grid1" autoFill="false" height="140" width=""
													url="/">
													<odin:gridJsonDataModel id="id" root="data">
														<odin:gridDataCol name="secretlevel"  />
														<odin:gridDataCol name="numflag"  />
														<odin:gridDataCol name="compflag"  />
														<odin:gridDataCol name="rowsname" isLast="true" />
													</odin:gridJsonDataModel>
													<odin:gridColumnModel>
														<odin:gridRowNumColumn></odin:gridRowNumColumn>
														<odin:gridEditColumn header="�ȼ�" align="left"
															edited="false" width="262" dataIndex="secretlevel"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="��˫��ʶ" align="left"
															edited="false" width="262" dataIndex="numflag"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="����ֶα�ʶ" align="left"
															edited="false" width="262" dataIndex="compflag"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="��Ϣ����" align="left"
															edited="false" width="262" dataIndex="rowsname"
															editor="text" isLast="true" />
													</odin:gridColumnModel>
											</odin:grid>
    									</td>
    								</tr>
    							</table>
    							
    						</td>
    						<td>&nbsp;</td>
							<td>
								<odin:groupBox title="��Ϣ������">
								<table style="height: 120px;width: 300px;">
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<odin:textEdit property="t1" label="������" width="200" maxlength="4"  required="true" onchange="changeTablecode();"></odin:textEdit>	
													</td>
												</tr>
												<tr>
													<td>
														<odin:textEdit property="t2" label="������"  required="true" width="200" ></odin:textEdit>
													</td>
												</tr>
												<tr>
													<td>
														<odin:select property="t0" label="�ȼ�"  width="200"></odin:select>
													</td>
												</tr>
												<tr>
													<td  colspan="3">
														<odin:checkbox property="isGroupCode" label="Ϊ����ֶ���Ϣ��" ></odin:checkbox>
													</td>
												</tr>
											</table>
										</td>
										<td colspan="4">
											<div style="width: 255px;">
											<odin:groupBox title="���¼���">
												<table id="table1">
												<tr>
													<td style="display:none"><odin:radio property="r1" value="2"   label="��¼��Ϣ��"></odin:radio></td>
												</tr>
												<tr>
													<td><odin:radio property="r1" value="1"  label="����¼��Ϣ��"></odin:radio><td>
												</tr>
												<tr>
													<td><odin:radio property="r1" value="0"   label="���¼��Ϣ��"></odin:radio>
												</tr>
												</table>
											</odin:groupBox>
											</div>
										</td>
									</tr>				
								</table>
								</odin:groupBox>
								<table>
									<tr align="center">
										<td style="width:180;" ><odin:button text="����" property="btn1" handler="tableadd"></odin:button></td>
										<td style="width:180;"><odin:button text="ɾ��"  property="btn2" handler="tabledelete"></odin:button></td>
										<td style="width:180;"><odin:button text="����"  property="btn3" handler="tablechange"></odin:button></td>
									</tr>
								</table>
							</td>
						</tr>
    				</table>
    			</odin:groupBox>
    			</div>
			</td>
		</tr>
		
		<tr>
			<td ></td>
		</tr>
		
		<tr>
			<td>
				<div style="width: 640px;height: 250px;">
    			<odin:groupBox property="group2" title="��Ϣ��" >
    				<table>
    					<tr>
    						<td style="vertical-align:top" >
    							<odin:grid property="grid2" autoFill="false" height="291"  url="/">
									<odin:gridJsonDataModel id="id2" root="data">
									    <odin:gridDataCol name="col_code"  />  
										<odin:gridDataCol name="rowsname" isLast="true" />
										</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
									    <odin:gridEditColumn header="����" align="left" hidden="true"
															edited="false" width="200" dataIndex="col_code"
															editor="text" /> 
										<odin:gridEditColumn header="��Ϣ����" align="left"
															edited="false" width="265" dataIndex="rowsname"
															editor="text" isLast="true" />
									</odin:gridColumnModel>
								</odin:grid>
								 <table>
								<tr align="center">
									<td style="width:180;" ><odin:button text="����" property="btn4" handler="codeAdd"></odin:button></td>
								</tr>
							</table>  
    						</td>
    						<!-- <td>&nbsp;</td> -->
    						<td>
    						     <table>
								<tr>
									<td style="width:180;"><odin:button text="����" property="btnup" handler="up"></odin:button></td>									
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
								<td style="width:180;"><odin:button text="����"  property="btndown" handler="down"></odin:button></td>
							    </tr>
							   <tr><td>&nbsp;</td></tr>
							   <tr><td>&nbsp;</td></tr>
							   <tr><td>&nbsp;</td></tr>
							    <tr>
							    <td style="width:180;"><odin:button text="ȷ��"  property="btnsave" > </odin:button></td>
							    </tr>
							</table> 
    						</td>
							<td>
							<odin:groupBox title="ָ��������">
								<table style="height: 100px; margin-left: 15px;width: 300px;">
									<tr>
										<td>
											<odin:textEdit property="t3" label="ָ�������" width="160"  required="true" onchange="changeCodeName();" ></odin:textEdit>	
										</td>
										<td>
											<odin:textEdit property="t4" label="ָ��������"  required="true" width="160" onchange="changeCodeName2();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:select property="s2" label="ָ��������"  required="true" onchange="changeCodeType();"></odin:select>	
										</td>
										<td>
											<odin:select property="s3" label="�������"></odin:select>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="t5" label="ָ�����"  required="true" width="160"></odin:textEdit>	
										</td>
										<!-- <td>
											<odin:textEdit property="t6" label="��ʾ���&nbsp;&nbsp;" width="160"></odin:textEdit>	
										</td> -->
										<td>
											<odin:select property="t7" label="�ȼ�" width="160"></odin:select>	
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<odin:checkbox property="check1" label="������"></odin:checkbox>
										</td>
										<td colspan="2">
											<%-- <odin:checkbox  property="check2" label="������"></odin:checkbox> --%>
											<odin:hidden property="check2"/>
										</td>
									</tr>
								</table>
							</odin:groupBox>
							<odin:groupBox title="����У������">
								<table style="height: 80px;width: 300px;">
									<tr>
										<td>
											<odin:select property="s4" label="У������" onchange="changeVerifyType();" ></odin:select>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="e1" label="У������ֵ1" width="160"></odin:textEdit>	
										</td>
										<td>
											<odin:textEdit property="e2" label="У������ֵ2" width="160"></odin:textEdit>		
										</td>
									</tr>
									<tr>
										<td>
											<odin:textarea property="area1" label="У�������Ϣ" colspan="5"></odin:textarea>
										</td>
									</tr>
								</table>
							</odin:groupBox>
							<table>
								<tr align="center">
									<%-- <td style="width:180;" ><odin:button text="����" property="btn4" handler="codeAdd"></odin:button></td> --%>
									<td style="width:180;"><odin:button text="ɾ��"  property="btn5" handler="codeDelete"></odin:button></td>
									<td style="width:180;"><odin:button text="����"  property="btn6" handler="codeSave"></odin:button></td>
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
function changeValue(){
	radow.doEvent("changeValue");
}

function tableadd(){//��ӵ���Ϣ��
	radow.doEvent("tableadd");
}

function tablechange(){//��Ϣ���޸�
	radow.doEvent("tablechange");
}

function tabledelete(){//��Ϣ��ɾ��
	radow.doEvent("tabledelete");
}

function changeTablecode(){
	radow.doEvent("changeTablecode");
}

function changeDisable(){
	document.getElementById('table1').disabled = true;
}

function changeable(){
	document.getElementById('table1').disabled = false;
}

function changeCodeType(){
	radow.doEvent("changeCodeType");
}

function changeVerifyType(){
	radow.doEvent("changeVerifyType");
}

function changeCodeName(){
	radow.doEvent("changeCodeName");
}

function changeCodeName2(){
	radow.doEvent("changeCodeName2");
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
function up(){
	//radow.doEvent("up");
var grid = odin.ext.getCmp('grid2');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('����Ա�Ѿ��������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}

function down(){
	//radow.doEvent("down");
var grid = odin.ext.getCmp('grid2');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ����Ļ���!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('����Ա�Ѿ����������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}

/* function savenum(){
	alert("��������");
	radow.doEvent("savenum");
	alert("�������");
} */

</script>