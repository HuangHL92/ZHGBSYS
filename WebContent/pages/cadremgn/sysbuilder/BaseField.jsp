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
    			<odin:groupBox property="group1" title="信息集" >
    				<table>
    					<tr>
    						<td colspan="2" style="margin-top: 2px;"><font style="font-size: 13px;width: 300px;">选择信息群</font>
										</td>
    					</tr>
    					<tr>
    						<td style="vertical-align:top">
    							<table>
    								<tr>
    										<odin:select property="select1" width="314" onchange="changeValue();"></odin:select>
    								</tr>
    								<tr>
										<td colspan="2"><font style="font-size: 13px;width: 300px;">选择信息集</font>
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
														<odin:gridEditColumn header="等级" align="left"
															edited="false" width="262" dataIndex="secretlevel"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="单双标识" align="left"
															edited="false" width="262" dataIndex="numflag"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="组合字段标识" align="left"
															edited="false" width="262" dataIndex="compflag"
															hidden="true"  editor="text"  />
														<odin:gridEditColumn header="信息集名" align="left"
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
								<odin:groupBox title="信息集属性">
								<table style="height: 120px;width: 300px;">
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<odin:textEdit property="t1" label="集代码" width="200" maxlength="4"  required="true" onchange="changeTablecode();"></odin:textEdit>	
													</td>
												</tr>
												<tr>
													<td>
														<odin:textEdit property="t2" label="集名称"  required="true" width="200" ></odin:textEdit>
													</td>
												</tr>
												<tr>
													<td>
														<odin:select property="t0" label="等级"  width="200"></odin:select>
													</td>
												</tr>
												<tr>
													<td  colspan="3">
														<odin:checkbox property="isGroupCode" label="为组合字段信息集" ></odin:checkbox>
													</td>
												</tr>
											</table>
										</td>
										<td colspan="4">
											<div style="width: 255px;">
											<odin:groupBox title="多记录标记">
												<table id="table1">
												<tr>
													<td style="display:none"><odin:radio property="r1" value="2"   label="记录信息集"></odin:radio></td>
												</tr>
												<tr>
													<td><odin:radio property="r1" value="1"  label="单记录信息集"></odin:radio><td>
												</tr>
												<tr>
													<td><odin:radio property="r1" value="0"   label="多记录信息集"></odin:radio>
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
										<td style="width:180;" ><odin:button text="新增" property="btn1" handler="tableadd"></odin:button></td>
										<td style="width:180;"><odin:button text="删除"  property="btn2" handler="tabledelete"></odin:button></td>
										<td style="width:180;"><odin:button text="保存"  property="btn3" handler="tablechange"></odin:button></td>
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
    			<odin:groupBox property="group2" title="信息项" >
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
									    <odin:gridEditColumn header="代码" align="left" hidden="true"
															edited="false" width="200" dataIndex="col_code"
															editor="text" /> 
										<odin:gridEditColumn header="信息项名" align="left"
															edited="false" width="265" dataIndex="rowsname"
															editor="text" isLast="true" />
									</odin:gridColumnModel>
								</odin:grid>
								 <table>
								<tr align="center">
									<td style="width:180;" ><odin:button text="新增" property="btn4" handler="codeAdd"></odin:button></td>
								</tr>
							</table>  
    						</td>
    						<!-- <td>&nbsp;</td> -->
    						<td>
    						     <table>
								<tr>
									<td style="width:180;"><odin:button text="上移" property="btnup" handler="up"></odin:button></td>									
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td></tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
								<td style="width:180;"><odin:button text="下移"  property="btndown" handler="down"></odin:button></td>
							    </tr>
							   <tr><td>&nbsp;</td></tr>
							   <tr><td>&nbsp;</td></tr>
							   <tr><td>&nbsp;</td></tr>
							    <tr>
							    <td style="width:180;"><odin:button text="确定"  property="btnsave" > </odin:button></td>
							    </tr>
							</table> 
    						</td>
							<td>
							<odin:groupBox title="指标项属性">
								<table style="height: 100px; margin-left: 15px;width: 300px;">
									<tr>
										<td>
											<odin:textEdit property="t3" label="指标项代码" width="160"  required="true" onchange="changeCodeName();" ></odin:textEdit>	
										</td>
										<td>
											<odin:textEdit property="t4" label="指标项名称"  required="true" width="160" onchange="changeCodeName2();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:select property="s2" label="指标项类型"  required="true" onchange="changeCodeType();"></odin:select>	
										</td>
										<td>
											<odin:select property="s3" label="代码大类"></odin:select>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="t5" label="指标项长度"  required="true" width="160"></odin:textEdit>	
										</td>
										<!-- <td>
											<odin:textEdit property="t6" label="显示宽度&nbsp;&nbsp;" width="160"></odin:textEdit>	
										</td> -->
										<td>
											<odin:select property="t7" label="等级" width="160"></odin:select>	
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<odin:checkbox property="check1" label="必填项"></odin:checkbox>
										</td>
										<td colspan="2">
											<%-- <odin:checkbox  property="check2" label="传递项"></odin:checkbox> --%>
											<odin:hidden property="check2"/>
										</td>
									</tr>
								</table>
							</odin:groupBox>
							<odin:groupBox title="单项校核条件">
								<table style="height: 80px;width: 300px;">
									<tr>
										<td>
											<odin:select property="s4" label="校验类型" onchange="changeVerifyType();" ></odin:select>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="e1" label="校验条件值1" width="160"></odin:textEdit>	
										</td>
										<td>
											<odin:textEdit property="e2" label="校验条件值2" width="160"></odin:textEdit>		
										</td>
									</tr>
									<tr>
										<td>
											<odin:textarea property="area1" label="校验出错信息" colspan="5"></odin:textarea>
										</td>
									</tr>
								</table>
							</odin:groupBox>
							<table>
								<tr align="center">
									<%-- <td style="width:180;" ><odin:button text="新增" property="btn4" handler="codeAdd"></odin:button></td> --%>
									<td style="width:180;"><odin:button text="删除"  property="btn5" handler="codeDelete"></odin:button></td>
									<td style="width:180;"><odin:button text="保存"  property="btn6" handler="codeSave"></odin:button></td>
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

function tableadd(){//添加到信息集
	radow.doEvent("tableadd");
}

function tablechange(){//信息集修改
	radow.doEvent("tablechange");
}

function tabledelete(){//信息集删除
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
		alert('请选中需要排序的人员!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的人员!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该人员已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}

function down(){
	//radow.doEvent("down");
var grid = odin.ext.getCmp('grid2');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的机构!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的人员!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该人员已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}

/* function savenum(){
	alert("进入排序");
	radow.doEvent("savenum");
	alert("完成排序");
} */

</script>