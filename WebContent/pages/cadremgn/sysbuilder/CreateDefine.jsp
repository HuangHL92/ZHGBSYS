<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/CDTabFourTJ.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/CDTabThreeJGZBX.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/CDTabTwoXXJ.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/CDTabOneZHZD.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/CDTabFiveOrder.js"></script>


<style>
	.div_area{
		border:1px solid #b5b8c8;
		overflow-y :scroll;
	}
</style>
<script type="text/javascript">
//初始化页面信息
var pWindow='';
var pparam='';
function init(){//初始化参数
	pWindow=window.dialogArguments['window'];
	pparam=window.dialogArguments['param'];
	var arr=pparam.split(',');
	var ctci=arr[0];
	var tablejhinfo=arr[1];
	document.getElementById("ctci").value=ctci;
	document.getElementById("tablejhinfo")	.value=tablejhinfo;
	var pageparam=arr[1];
	document.getElementById("pageparam").value=pageparam;
	document.getElementById('g1').firstChild.style.width='380px';//已定义的组合字段
	document.getElementById('recordsetboxproperty').firstChild.style.width='380px';//记录组合格式设置
	document.getElementById('fieldruleboxproperty').firstChild.style.width='380px';//字段生成规则描述
	document.getElementById('fieldruleboxproperty').firstChild.style.height='226px';//字段生成规则描述
	
	document.getElementById('selectTarget').firstChild.style.height='486px';//选择指标项
	document.getElementById('searchResultSymbol').firstChild.style.width='380px';//查询结果指标项
	document.getElementById('searchResultSymbol').firstChild.style.height='486px';//查询结果指标项
	document.getElementById('fldAttributeSet').firstChild.style.height='245px';//字段属性设置
	document.getElementById('fldAttributeSet').firstChild.style.width='380px';//字段属性设置
	document.getElementById('sureCondition').firstChild.style.height='325px';//确定条件
	document.getElementById('sureCondition').firstChild.style.width='420px';//确定条件
	document.getElementById('orderzbx').firstChild.style.width='390px';//排序指标项
	document.getElementById('selectzbx').firstChild.style.width='350px';//选择指标项
	
	radow.doEvent("initpage");
}
function confirmAct(str){//提示
	if(confirm(str)){
		window.close();
	}else{
		window.close();
	}
}


function setGridListChecked(row,col,col_id,grid_id){//设置列表复选框 单选
	var grid=Ext.getCmp(grid_id);
	var orderStore = grid.getStore();
	if(orderStore.getAt(row).get(col_id)==true){//当前行规选中，则其他选中行取消
		//var selectionmodel=grid.getSelectionModel();
		var rowCount = orderStore.getCount();
		for(var i=0;i<rowCount;i++){
			if(i!=row&&orderStore.getAt(i).get(col_id)==true){
				var record=orderStore.getAt(i);
				record.set(col_id,false);
				orderStore.removeAt(i);  
				orderStore.insert(i, record); 
				//grid.getSelectionModel().select(record);
				//selectionmodel.selectRow(i,false);
			}
		}
	}
}

/* function mergefieldnextfunc(){//组合字段 ，下一步
	odin.ext.getCmp('jltab').activate('tab2');
} */
/* function clearAll(){
	radow.doEvent("clearAll");
} */
function saveFunc(){
	var searchGrpFld='';
	for(var item in searchObj){
		var id=item;
		var arr=searchObj[item];
		if(arr){
			for(var i=0;i<arr.length;i++){
				id=id+"@"+arr[i];
			}
			searchGrpFld=searchGrpFld+id+"$";
		}
	}
	if(searchGrpFld!=''){
		searchGrpFld=searchGrpFld.substr(0,searchGrpFld.length-1); 
	}
	document.getElementById("searchGrpFld").value=searchGrpFld;//所有的组合设置属性
	 
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"。@。";
	}
	document.getElementById("allqryusestr").value=allqryusestr;//所有的隐藏的组合条件
	radow.doEvent('saveFunc');
}
function swithTabForSave(tabNum){
	odin.ext.getCmp('jltab').activate('tab'+tabNum);
}
function insertGrpFld(rsm001,ctci){															 
	 	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysbuilder.CreateDefine&eventNames=insertGrpData';
		ShowCellCover('start','系统提示','组合字段数据生成中,请您稍等...');
	   	Ext.Ajax.request({
	   		timeout: 900000,
	   		url: path,
	   		async: true,
	   		method :"post",
	   		params : {
				'rsm001':rsm001,
				'ctci':ctci
	   		},
            callback: function (options, success, response) {
        	   if (success) {
        		    var result = response.responseText;
   					if(result){
   						Ext.Msg.hide();
   						var json = eval('(' + result + ')');
   						var data_str=json.data;
   						var arr=data_str.split('@');
   						if(arr[0]==2){//成功
   							alert("保存成功!");
   						}else if(arr[0]==1){//失败
   							alert("保存失败!"+arr[1]);
   						}else if(arr[0]==3){
   							alert("保存成功!(存在过长的数据，已截取。)");
   						}
   						Ext.Msg.hide();
   					}
        	   }
           }
	    });
}
//虚假进度条
function ShowCellCover(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "关闭";
	   	if(elementId.indexOf("start") != -1){
	   	
	   		Ext.MessageBox.show({
	   			title:titles,
	   			msg:msgs,
	   			width:300,
	   	        height:300,
	   			closable:false,
	   		//	buttons: Ext.MessageBox.OK,		
	   			modal:true,
	   			progress:true,
	   			wait:true,
	   			animEl: 'elId',
	   			increment:5, 
	   			waitConfig: {interval:150}
	   			//,icon:Ext.MessageBox.INFO        
	   		});
	   	}
}
function editExpressionFunc(obj){
	var grid=Ext.getCmp("searchList");
	var orderStore = grid.getStore();
	var rowCount=orderStore.getCount();
	if(rowCount>0){
		alert("用sql表达式将会把结果指标项属性全部清空");
		var ctci=document.getElementById("ctci").value;
		var param=ctci+",25";
		$h.openPageModeWin('GroupSqlRes','pages.cadremgn.sysbuilder.GroupSqlRes','Sql表达式拼写器 ',630,460,param,'<%=request.getContextPath()%>');
	}else{
		alert("先选择结果指标项");
	}
}
</script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
#jltab__tab1{
		margin-left:200;
	}
</style>       
<div>
	<odin:hidden property="ctci"/><!-- 信息项id -->
	<odin:hidden property="pageparam"/><!-- 页面参数，作用：识别新增or修改功能 -->
	<odin:hidden property="searchGrpFld"/><!-- 所有的组合设置属性 -->
	<odin:hidden property="allqryusestr"/><!-- 所有的隐藏的组合条件 -->
	<odin:hidden property="tablesInfo"/><!-- 初始化信息集选中信息 -->
	<odin:hidden property="setfld"/>
	<odin:hidden property="tablejhinfo"/><!-- 父页面信息集 -->
</div>  
<div id="tooldiv"></div>
	<odin:tab id="jltab" height="550">
		    <odin:tabModel>
		    	<odin:tabItem title="&nbsp;&nbsp;组合字段&nbsp;&nbsp;" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="选择信息集" id="tab2"></odin:tabItem>
		    	<odin:tabItem title="结果指标项" id="tab3"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;&nbsp;" id="tab4"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;排&nbsp;&nbsp;&nbsp;&nbsp;序&nbsp;&nbsp;" id="tab5" isLast="true"></odin:tabItem>
		    </odin:tabModel> 
		    <odin:tabCont itemIndex="tab1">
				<div style="width: 100%;height: 580px;margin-left: 10px;">
					<table>
						<tr>
							<odin:textEdit property="showname" label="当前操作字段" size="53" readonly="true"></odin:textEdit>
							<!-- <font style="width: 100px;font-size: 13;font-weight: bolder;">当前操作字段</font><input type="text" id="t1" style="width: 200px;"> -->
						</tr>
						<tr>  
							<td colspan="2" style="">
				 				<odin:groupBox property="g1" title="已定义的组合字段"  >
				 					<odin:editgrid property="contentList" url="/" height="420" width="375" load="setMergeChecked">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="showname" />
											<odin:gridDataCol name="table_code" />
											<odin:gridDataCol name="col_code" />
											<odin:gridDataCol name="ctci" />
											<odin:gridDataCol name="col_name" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridColumn header=""  dataIndex="change"  editor="checkbox" edited="true" checkBoxClick="defineFieldcheck"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="270" dataIndex="showname" editor="text" />
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="table_code" editor="text" hidden="true"/>
					     						<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="col_code" editor="text" hidden="true"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="ctci" editor="text" hidden="true"/>
											<odin:gridEditColumn header="" align="left" edited="false" width="55" dataIndex="col_name" editor="text" hidden="true" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
								</odin:groupBox>
							</td>     
							<td colspan="2" valign="top">
								<odin:groupBox title="记录组合格式设置" property="recordsetboxproperty">
								<table >
									<tr>
										<odin:select2 property="startrow" label="选择查询结果记录:从第" codeType="GRPSLSC"></odin:select2>
										<td>
											<font style="font-size: 12;margin-left: 3px;">行</font>
										</td>
									</tr>
									<tr>  
										<odin:select2 property="endrow" label="到第"  codeType="GRPSLSC"></odin:select2>
										<td>
											 <font style="font-size: 12;margin-left: 3px;">行</font>
										</td>
									</tr>
									<tr>
										<odin:select2 property="connector" label="选择记录间连接符"  codeType="GRPCNYB"></odin:select2>
										<td>
										</td>
									</tr>
									<tr>
										<odin:textEdit property="indentrow" label="记录缩进排列:每行"></odin:textEdit>
										<td>
											<font style="font-size: 12;">个字符</font>
										</td>
									</tr>
									<tr>  
										<odin:textEdit property="indextnum" label="每行"></odin:textEdit>
										<td>
											<font style="font-size: 12;">个字符位置</font>
										</td>	
									</tr>
									<tr>  
										
										<td>
											<odin:checkbox property="firstindex" name="firstindex" label="从首行开始缩进"></odin:checkbox>
										</td>
										<td >
											<odin:checkbox property="centerbranch" name="centerbranch" label="记录间分行居中排列"></odin:checkbox>
										</td>
										<td >
										</td>
									</tr>
								</table>
								</odin:groupBox>
								<odin:groupBox title="字段生成规则描述" property="fieldruleboxproperty">
									<table style="height: 155px;">
										<tr>
											<td width="1px">
											</td>
											<td> 
												<odin:textarea property="area4" cols="63" rows="13"></odin:textarea>
											</td>
										</tr>
									</table>
								</odin:groupBox>
							</td>
						</tr>
						<%-- <tr style="width:100%">
							<td colspan="4" align="center">
								<odin:button text="下一步" ></odin:button>
							</td>
						</tr> --%>
					</table>    
				</div>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<table>
		    		<tr>
		    			<td>
		    				<table>
		    					<tr>
		    						<td>
		    							<odin:groupBox title="选择信息集" >
		    								<div style="width:370px;">
			    								<odin:editgrid property="content2" url="/" height="448" width="340px" load="setTableInfoSelected">
													<odin:gridJsonDataModel id="id2" root="data">
														<odin:gridDataCol name="showtablename"/>
														<odin:gridDataCol name="table_code" isLast="true"/>
													</odin:gridJsonDataModel>
													<odin:gridColumnModel>  
														<odin:gridRowNumColumn></odin:gridRowNumColumn>
														<odin:gridColumn header="" dataIndex="change" editor="checkbox" edited="true" width="50"  checkBoxClick="infoTableCheck"/>
														<odin:gridEditColumn header="" align="left" edited="false" width="270" dataIndex="showtablename" editor="text" />
														<odin:gridEditColumn header="" align="left" hidden="true" edited="false" width="270" dataIndex="table_code" editor="text" isLast="true"/>
													</odin:gridColumnModel>
												</odin:editgrid>
											</div>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    				</table>
		    			</td>
		    			<td> 
		    				<table>
		    					<tr>
		    						<td>
		    							<odin:groupBox title="指标项" >
		    								<div style="width:370px;">
		    								<odin:editgrid property="contentList2" url="/" height="448" width="380px">
												<odin:gridJsonDataModel id="id" root="data">
													<odin:gridDataCol name="showcolname" isLast="true"/>
												</odin:gridJsonDataModel>
												<odin:gridColumnModel>
													<odin:gridRowNumColumn></odin:gridRowNumColumn>
													<odin:gridEditColumn header="" align="left" edited="false" width="300" dataIndex="showcolname" editor="text" isLast="true"/>
												</odin:gridColumnModel>
											</odin:editgrid>
											</div>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    				</table>
		    			</td>
		    		</tr>
		    	</table>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab3">
		    	<div style="width: 100%;height: 580px;margin-left: 10px;">
					<table>
						<tr>
							<td>
    							<odin:groupBox title="选择指标项" property="selectTarget">
    								<table>
    									<tr>
    										<td>
    											<div style="width: 323px;height: 452px;">
				    								<odin:editgrid property="contentList5" url="/" height="452" width="353" >
														<odin:gridJsonDataModel id="id" root="data">
																<odin:gridDataCol name="showcolname" />
																<odin:gridDataCol name="table_code" />
																<odin:gridDataCol name="col_code" />
																<odin:gridDataCol name="col_name" />
																<odin:gridDataCol name="code_type" />
																<odin:gridDataCol name="col_data_type_should" />
																<odin:gridDataCol name="col_data_type" />
																<odin:gridDataCol name="ordernum" isLast="true"/>
															</odin:gridJsonDataModel>
															<odin:gridColumnModel>
																<odin:gridRowNumColumn></odin:gridRowNumColumn>
																<odin:gridEditColumn header="" align="left" edited="false" width="270" dataIndex="showcolname" editor="text" />
																<odin:gridEditColumn header="列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
																<odin:gridEditColumn header="老列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
																<odin:gridEditColumn header="指标项类型" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
																<odin:gridEditColumn header="列的中文名称" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
																<odin:gridEditColumn header="指标项对应的列" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
																<odin:gridEditColumn header="信息表编码" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
																<odin:gridEditColumn header="" hidden="true" align="left" edited="false" width="290" dataIndex="ordernum" editor="text" isLast="true"/>
															</odin:gridColumnModel>
														<%-- <odin:gridJsonDataModel id="id" root="data">
															<odin:gridDataCol name="ordernum" />
															<odin:gridDataCol name="showcolname" isLast="true"/>
														</odin:gridJsonDataModel>
														<odin:gridColumnModel>
															<odin:gridRowNumColumn></odin:gridRowNumColumn>
															<odin:gridEditColumn header="" align="left" edited="false" width="300" dataIndex="showcolname" editor="text" />
															<odin:gridEditColumn header="" align="left" hidden="true" edited="false" width="290" dataIndex="ordernum" editor="text" isLast="true"/>
														</odin:gridColumnModel> --%>
													</odin:editgrid>
												</div>
    										</td>
    									</tr>
    									<%-- <tr height="5px">
    									</tr>
    									<tr>
    										<td>
	    										<table>
	    											<tr>
	    												<td width="105px">
	    												</td>
	    												<td align="center">
				    										<odin:button text="添加" handler="addIndexTermFld"></odin:button>
							    						</td>
							    						<td width="80px">
							    						</td>
							    						<td align="center">
						    								<odin:button text="全部添加" handler="searchAddAll"></odin:button>
							    						</td>
							    						<td>
	    												</td>
	    											</tr>
	    										</table>
    										</td>
				    					</tr> --%>
    								</table>
    							</odin:groupBox>
							</td>
							<td style="width:41px;">
								<table>
									<tr>
										<td>
										<odin:button text="&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;" handler="addIndexTermFld"></odin:button>
										</td>
									</tr>
									<tr style="height:50px;">
										<td></td>
									</tr>
									<tr>
										<td>
										<odin:button text="&nbsp;&gt;&nbsp;&gt;&nbsp;" handler="searchAddAll"></odin:button>
										</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<odin:groupBox title="查询结果指标项" width="600px" property="searchResultSymbol">
								<table style="width:100%;">
									<tr>
										<td>
											<table style="height: 155px;">
												<tr>
													<td width="300">
														<odin:grid property="searchList" url="/" height="165" width="600" rowClick="searchclickfunc" >
															<odin:gridJsonDataModel id="id" root="data">
																<odin:gridDataCol name="showcolname" />
																<odin:gridDataCol name="table_code" />
																<odin:gridDataCol name="col_code" />
																<odin:gridDataCol name="col_name" />
																<odin:gridDataCol name="code_type" />
																<odin:gridDataCol name="col_data_type_should" />
																<odin:gridDataCol name="col_data_type" />
																<odin:gridDataCol name="ordernum" isLast="true"/>
															</odin:gridJsonDataModel>
															<odin:gridColumnModel>
																<odin:gridRowNumColumn></odin:gridRowNumColumn>
																<odin:gridEditColumn header="" align="left" edited="false" width="250" dataIndex="showcolname" editor="text" />
																<odin:gridEditColumn header="列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
																<odin:gridEditColumn header="老列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
																<odin:gridEditColumn header="指标项类型" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
																<odin:gridEditColumn header="列的中文名称" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
																<odin:gridEditColumn header="指标项对应的列" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
																<odin:gridEditColumn header="信息表编码" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
																<odin:gridEditColumn header="" hidden="true" align="left" edited="false" width="290" dataIndex="ordernum" editor="text" isLast="true"/>
															</odin:gridColumnModel>
															<%-- <odin:gridJsonDataModel id="id" root="data">
																<odin:gridDataCol name="ordernum" />
																<odin:gridDataCol name="showcolname" isLast="true"/>
															</odin:gridJsonDataModel>
															<odin:gridColumnModel>
																<odin:gridRowNumColumn></odin:gridRowNumColumn>
																<odin:gridEditColumn header="" align="left" edited="false" width="250" dataIndex="showcolname" editor="text" />
																<odin:gridEditColumn header="" align="left" hidden="true" edited="false" width="250" dataIndex="ordernum" editor="text" isLast="true"/>
															</odin:gridColumnModel> --%>
														</odin:grid>
														<%-- <odin:textarea property="area5" cols="50" rows="9"></odin:textarea> --%>
													</td>
													<td align="center" width="80px">
														<table >
															<tr height="1px"> </tr>
															<tr> <td><odin:button text="上移" handler="indexTermUpMove"></odin:button></td> </tr>
															<tr height="5px"> </tr>
															<tr> <td><odin:button text="下移" handler="indexTermDownMove"></odin:button></td> </tr>
															<tr height="5px"> </tr>
															<tr> <td><odin:button text="删除" handler="indexTermRemove"></odin:button></td> </tr>
															<tr height="1px"> </tr>
														</table>
													</td>
													<td width="3px">
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<odin:checkbox property="check2" label="查询结果为sql函数表达式" onclick="check2Func(this)"></odin:checkbox>
													</td>
													<td width="6px">
													</td>
													<td>
														<odin:button property="button1" text="编辑表达式"  handler="editExpressionFunc"></odin:button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<odin:groupBox title="字段属性设置" property="fldAttributeSet">
												<table style="height:100%;">
													<tr>
														<td>
															<odin:checkbox property="check4" label="合并同类项字段" onclick="check4Func(this)"></odin:checkbox>
														</td>
														<td>
															<odin:checkbox property="check3" label="代码字段转换为描述" onclick="check3Func(this)"></odin:checkbox>
														</td>
													</tr>
													<tr>
														<odin:select2 property="connsymbol" label="字段后的连接字符" onblur="onmouseoutFunc(this,0)" codeType="GRPCNYB"  onchange="grpFldAttributeFunc1"></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select12" label="值为空时连接符处理方式" codeType="GRPYBDL" onblur="onmouseoutFunc(this,1)" onchange="grpFldAttributeFunc2"></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select13" label="字段为空的代替符" codeType="GRPNLRP" onblur="onmouseoutFunc(this,2)" onchange="grpFldAttributeFunc3" ></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select property="select14" label="字段合计函数" onblur="onmouseoutFunc(this,3)" onchange="grpFldAttributeFunc4"></odin:select>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="selectsame" label="同类项替代连接符" codeType="GRPSMRP" onblur="onmouseoutFunc(this,4)" onchange="grpFldAttributeFunc5"> </odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select15" label="日期格式化" codeType="GRPDTFM" onblur="onmouseoutFunc(this,5)" onchange="grpFldAttributeFunc6"></odin:select2>
													</tr>
												</table>
											</odin:groupBox>
										</td>
									</tr>
								</table>
							</odin:groupBox>
							</td>
						</tr>
					</table>
				</div>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab4">
		    	<table>
		    		<tr>
		    			<td>
		    				<table>
		    					<tr>
		    						<td>
		    							<odin:groupBox title="选择指标项" >
		    								<div style="width: 340px;height: 400px;">
		    								<odin:editgrid property="contentList6" url="/" height="448" rowDbClick="rowFldeDbClick">
												<odin:gridJsonDataModel id="id" root="data">
													<odin:gridDataCol name="showcolname" />
													<odin:gridDataCol name="table_code" />
													<odin:gridDataCol name="col_code" />
													<odin:gridDataCol name="col_name" />
													<odin:gridDataCol name="code_type" />
													<odin:gridDataCol name="col_data_type_should" />
													<odin:gridDataCol name="col_data_type" />
													<odin:gridDataCol name="ordernum" isLast="true"/>
												</odin:gridJsonDataModel>
												<odin:gridColumnModel>
													<odin:gridRowNumColumn></odin:gridRowNumColumn>
													<odin:gridEditColumn header="" align="left" edited="false" width="290" dataIndex="showcolname" editor="text" />
													<odin:gridEditColumn header="列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
													<odin:gridEditColumn header="老列数据类型" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
													<odin:gridEditColumn header="指标项类型" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
													<odin:gridEditColumn header="列的中文名称" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
													<odin:gridEditColumn header="指标项对应的列" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
													<odin:gridEditColumn header="信息表编码" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
													<odin:gridEditColumn header="" hidden="true" align="left" edited="false" width="290" dataIndex="ordernum" editor="text" isLast="true"/>
												</odin:gridColumnModel>
											</odin:editgrid>
											</div>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    				</table>
		    			</td>
		    			<td valign="top">
		    				<table>
		    					<tr>
		    						<td valign="top">
		    							<odin:groupBox title="确定条件" property="sureCondition">
		    								<div>
		    									<odin:tab id="tabcondition" width="400" tabchange="tab4onchange">
		    										 <odin:tabModel>
		    										 	<odin:tabItem title="条件查询" id="tabcondition1"></odin:tabItem>
		    											<odin:tabItem title="高级查询" id="tabcondition2" isLast="true"></odin:tabItem>
		    										 </odin:tabModel>
			    									<odin:tabCont itemIndex="tabcondition1">
			    										<table>
			    											<tr height="3px">
			    											</tr>
															<tr>
																<odin:textEdit property="conditionName4" label="条件指标项"  readonly="true"></odin:textEdit>
																<td rowspan="2" style="text-align:center;width:200">
																<odin:button text="&nbsp&nbsp&nbsp清 &nbsp&nbsp&nbsp&nbsp除&nbsp&nbsp&nbsp"  property="btnm1" handler="conditionclear" ></odin:button>
																</td>
															</tr>
															<tr>
																<odin:select property="conditionName5" label="条件符号" width="158" onchange="valuefivefunc"></odin:select>
																<td>
																
																</td>
															</tr>
															<tr id="value1" valign="middle" style="display: none;">
																<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">值一</td>
																<td id='codediv'>
																	<input type="hidden" id="conditionName6" name="conditionName6" />
																	<input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style='cursor:default;' required="false"  label="false" />
																</td>
																<%-- <odin:select property="conditionName6" label="值一" width="158" ></odin:select> --%>
																<td style="text-align:center;width:200">
																<odin:button text="添加至列表" property="btnm2" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value11" style="display: none;" valign="middle">
																<odin:dateEdit property="conditionName61" label="值一" width="158"></odin:dateEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="添加至列表" property="btnm21" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value111" style="display: block;" valign="middle">
																<odin:textEdit property="conditionName611" label="值一"  ></odin:textEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="添加至列表" property="btnm211" handler="addtolistfunc"></odin:button>
																</td>
															</tr>
															<tr id="value1111" style="display: none;" valign="middle">
																<odin:numberEdit property="conditionName6111" label="值一" ></odin:numberEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="添加至列表" property="btnm211" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value2" style="display: none;" valign="middle">
																<odin:dateEdit property="conditionName7" label="值二"  width="140" ></odin:dateEdit>
																<td style="text-align:center;width:200">&nbsp;
																</td>
															</tr>
															<tr id="value21" style="display: none;" valign="middle">
																<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">值二</td>
																<td id='codediv2' >
																	<input type="hidden" id="conditionName71" name="conditionName71" />
																	<input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style='cursor:default;' required="false"  label="false" />
																</td>
																<%-- <odin:select property="conditionName71" label="值二" width="140" ></odin:select> --%>
																<td style="text-align:center;width:200">
																</td>
															</tr>
															<tr id="value211" style="display: block;" valign="middle">
																<odin:numberEdit property="conditionName711" label="值二"  ></odin:numberEdit>
																<td style="text-align:center;width:200">
																</td>
															</tr>
															
														</table>
			    									</odin:tabCont>
			    									<odin:tabCont itemIndex="tabcondition2">
			    										<table style="width:100%;margin: 0px;margin: 0px;padding: 0px;" >
			    											<tr>
			    												<td>
			    													<table style="width:100%;">
			    														<tr>
			    															<td style="width:15px;"></td>
						    												<odin:select property="stringfuncid" defaultValue="字符串函数" onchange="stringOnChange"></odin:select>
						    												<odin:select property="datefuncid" defaultValue="日期函数" onchange="dateOnChange"></odin:select>
						    											</tr>
						    											<tr>
						    												<td style="width:15px;"></td>
						    												<odin:select property="numberfuncid" defaultValue="数值函数" onchange="numberOnChange"></odin:select>
						    												<odin:select property="opratesymfuncid" defaultValue="运算符" onchange="opratesymOnChange"></odin:select>
						    											</tr>
						    											<tr>
						    												<td style="width:15px;"></td>
						    												<odin:select property="infosetfuncid" defaultValue="信息集列表" onchange="tableOnChange"></odin:select>
						    												<odin:select property="databasefuncid" defaultValue="数据库字段列表" onchange="databaseOnChange"></odin:select>
						    											</tr>
			    													</table>
			    												</td>
			    											</tr>
			    											<tr>
			    												<td>
			    													<table>
				    													<tr>
						    												<td style="font-size:12px;text-align: right;">表达式拼写区</td>
						    												<td colspan="2">
						    													<table>
						    														<tr>
									    												<odin:textEdit width="210" property="expressionid" onmouseout="expressOut(this)"></odin:textEdit>
						    														</tr>
						    													</table>
						    												</td>
						    												<td >
						    													<odin:button text="&nbsp&nbsp&nbsp清 &nbsp&nbsp&nbsp&nbsp除&nbsp&nbsp&nbsp" property="tab4clear" handler="cleanTab4High"></odin:button>
						    												</td>
						    											</tr>
						    											<tr>
						    												<td style="font-size:12px;text-align: right;">表达式语义说明</td>
						    												<td colspan="2">
						    													<table>
						    														<tr>
									    												<odin:textEdit width="210" property="expressionexplainid" ></odin:textEdit>
						    														</tr>
						    													</table>
						    												</td>
						    												<td >
						    													<odin:button text="添加至列表" property="tab4AddToList" handler="addListHigh"></odin:button>
						    												</td>
						    											</tr>
			    													</table>
			    												</td>
			    											</tr>
			    										</table>
			    									</odin:tabCont>
		    								</odin:tab>
		    								</div>
		    								<table style="width:320">
												<tr>
													<td>
														<div style="width:320;height:100;" id="conditionName8" class="div_area">
														
														</div>
													</td>
													<td style="width:50;" align="center">
														<table>
														<tr>
															<td id="editcondition" style="text-align:right;width:48;height:30">
															<odin:button text="&nbsp&nbsp编&nbsp&nbsp&nbsp&nbsp辑&nbsp&nbsp"  property="editbtnm" handler="editbtnFunc">
															</odin:button></td>
														</tr>
														<tr>
															<td id="iddeletecondition" style="text-align:right;width:48;height:30">
															<odin:button text="&nbsp&nbsp移&nbsp&nbsp&nbsp&nbsp除&nbsp&nbsp"  property="btnm3" handler="btnm3Func">
															</odin:button></td>
														</tr>
														<tr>
															<td id="iddeleteallcondition" style="text-align:right;width:50;height:30">
															<odin:button text="全部删除"  property="btnm4" handler="btnm4Func"></odin:button></td>
														</tr>
														</table>
													</td>
												</tr>
											</table>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    					<tr>
		    						<td>
		    							<span ></span>
		    							<odin:groupBox title="组合条件" width="350">
		    								<table >
												<tr>
													<td width="70px">
														<odin:button text="选择条件" property="btnn1" handler="btnnFunc"></odin:button>
													</td>
													<td><odin:button text="（" property="btnn2"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="）" property="btnn3"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="非" property="btnn4"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="并且" property="btnn5"  handler="btnnFunc"></odin:button></td>
													<td ><odin:button text="或者" property="btnn6"  handler="btnnFunc"></odin:button></td>
													<td align="right">
														<odin:button text="重新设置" property="btnn7" handler="btnn7Funcc" ></odin:button>
													</td>
													<td  width="1px">&nbsp;</td>
												</tr>
												<tr>
													<td colspan="8">
														<table>
															<tr>
																<odin:textarea property="conditionName9" cols="60" rows="4" readonly="true" ></odin:textarea>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td colspan="8" style="font-size:14;color:red;" >请将条件选入“组合条件”框中，否则无效</td>
												</tr>
											</table>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    				</table>
		    			</td>
		    		</tr>
		    	</table>
		    </odin:tabCont>
		    <odin:tabCont itemIndex="tab5">
		    		<table>
		    		<tr>
		    			<td>
		    				<table>
		    					<tr>
		    						<td>
		    							<odin:groupBox title="选择指标项" property="selectzbx" width="300">
		    								<div >
		    								<odin:editgrid property="contentList3" url="/" height="449" width="300" rowDbClick="addOrderFld">
												<odin:gridJsonDataModel id="id" root="data">
													<odin:gridDataCol name="ordernum" />
													<odin:gridDataCol name="col_code" />
													<odin:gridDataCol name="table_code" />
													<odin:gridDataCol name="showcolname" isLast="true"/>
												</odin:gridJsonDataModel>
												<odin:gridColumnModel>
													<odin:gridRowNumColumn></odin:gridRowNumColumn>
													<odin:gridEditColumn header="" align="left" edited="false" width="280" dataIndex="showcolname" editor="text" />
													<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="290" dataIndex="col_code" editor="text" />
													<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="290" dataIndex="table_code" editor="text" />
													<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="290" dataIndex="ordernum" editor="text" isLast="true"/>
												</odin:gridColumnModel>
											</odin:editgrid>
											</div>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    					<%-- <tr>
		    						<td align="center">
		    							<odin:button text="添加" handler="addOrderFld"></odin:button>
		    						</td>
		    					</tr> --%>
		    				</table>
		    			</td>
		    			<td  style="width:50px;vertical-align: middle;">
		    				<table border="0">
		    					<tr>
		    						<td>
		    							<odin:button text="&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;" handler="addOrderFld"></odin:button>
		    						</td>
		    					</tr>
		    					<tr style="height:40px;">
		    						<td>&nbsp;</td>
		    					</tr>
		    					<tr>
		    						<td>
		    							<odin:button text="&nbsp;&lt;&nbsp;&lt;&nbsp;" handler="clearAllOrder"></odin:button>
		    						</td>
		    					</tr>
		    				</table>
		    			</td>
		    			<td>
		    				<table>
		    					<tr>
		    						<td>
		    							<odin:groupBox title="排序指标项" property="orderzbx" width="380">
		    								<table style="width:100%;" >
		    									<tr>
		    										<td width="380px">
		    											<div style="width:100%;">
					    								<odin:editgrid property="contentList4" url="/" height="445" width="380" cellDbClick="dbClickOrderFunc">
															<odin:gridJsonDataModel id="id" root="data">
																<odin:gridDataCol name="col_code" />
																<odin:gridDataCol name="table_code" />
																<odin:gridDataCol name="ordernum" />
																<odin:gridDataCol name="showcolname" isLast="true"/>
															</odin:gridJsonDataModel>
															<odin:gridColumnModel>
																<odin:gridRowNumColumn></odin:gridRowNumColumn>
																<odin:gridEditColumn header="" align="left" edited="false" width="240" dataIndex="showcolname" editor="text" />
																<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="290" dataIndex="col_code" editor="text" />
																<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="290" dataIndex="table_code" editor="text" />
																<odin:gridEditColumn header="" align="left" edited="false" hidden="true" width="240" dataIndex="ordernum" editor="text" isLast="true"/>
															</odin:gridColumnModel>
														</odin:editgrid>
														</div>
		    										</td>
		    										<td width="80px" valign="middle">
						    							<table style="width:100%;height:100%;">
						    								<tr><td align="center"><odin:button text="上移" property="upmove" handler="upmovefunc"></odin:button></td> </tr>
						    								<tr height="24px"><td>&nbsp;</td></tr>
						    								<tr><td align="center"><odin:button text="下移" property="downmove" handler="dowmmoveofunc"></odin:button></td> </tr>
						    								<tr height="24px"><td>&nbsp;</td></tr>
						    								<tr><td align="center"><odin:button text="移除" property="remove" handler="removeRow"></odin:button></td> </tr>
						    							</table>
						    						</td>
		    									</tr>
		    								</table>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    					<%-- <tr>
		    						<td align="center">
		    							<odin:button text="全部清除" handler="clearAllOrder"></odin:button>
		    						</td>
		    					</tr> --%>
		    				</table>
		    			</td>
		    		</tr>
		    	</table>
		    </odin:tabCont>
	 </odin:tab>
	<div>
		<odin:hidden property="showcolname"/><!-- 指标名称 -->
		<odin:hidden property="col_name"/><!-- 指标名称 -->
		<odin:hidden property="table_code"/><!-- 表名 -->
		<odin:hidden property="col_code"/><!-- 字段名 -->
		<odin:hidden property="col_data_type"/><!-- 列数据类型 -->
		<odin:hidden property="col_data_type_should"/><!-- 指标代码类型2 -->
		<odin:hidden property="code_type"/><!--指标代码类型 -->
		<odin:hidden property="qryusestr"/><!--//拼接当前添加条件用于保存到条件表中 -->
		<odin:hidden property="conditoionlist"/><!--//拼接添加条件用于显示 -->
		<odin:hidden property="conditionStr"/><!-- 隐藏拼接的where条件 -->
		<odin:hidden property="zhtj"/><!-- 隐藏用于保存的组合条件 -->
		<odin:hidden property="querysql"/><!-- 保存生成的sql到页面 -->
		<odin:hidden property="tabType" value="1"/>
	</div>
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<%-- <odin:buttonForToolBar id="change"  text="修改"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="delete"  text="删除"/> --%>
	<odin:fill/>
	<odin:buttonForToolBar id="Save"  text="保存" handler="saveFunc"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="reset"  text="重置" isLast="true" handler="cleanAllData"/>
</odin:toolBar>

<script type="text/javascript">

function cancelSelect(grid_id,checked){
	var grid=Ext.getCmp(grid_id);
	var orderStore = grid.getStore();
	var rowCount=orderStore.getCount();
	for(var i=0;i<rowCount;i++){
		if(orderStore.getAt(i).get(checked)==true){
			var record=orderStore.getAt(i);
			record.set(checked,false);
			orderStore.removeAt(i);  
			orderStore.insert(i, record); 
		}
	}
}
function cleanAllData(){//重置
	//取消组合字段已经选中的信息项 contentList
	cancelSelect('contentList','change')
	//清空排序
	Ext.getCmp('contentList4').getStore().removeAll();
	Ext.getCmp('contentList3').getStore().removeAll();
	//清空 条件
	cleanInfo();
	Ext.getCmp('contentList6').getStore().removeAll();
	//结果指标项
	searchObj=new Object();
	Ext.getCmp('contentList5').getStore().removeAll();
	Ext.getCmp('searchList').getStore().removeAll();
	//清空选择信息集
	cancelSelect('content2','change');
//	Ext.getCmp('content2').getStore().removeAll();
	Ext.getCmp('contentList2').getStore().removeAll();
	radow.doEvent("clearRowRecordSet");
}




function upmove(grid_id4){
	
	var grid4=Ext.getCmp(grid_id4);
	var store = grid4.getStore();
	var selections = grid4.getSelectionModel().getSelections();
	   var record = selections[0]; 
	   var index = store.indexOf(record);
	    if (index > 0) {  
	        store.removeAt(index);  
	        store.insert(index - 1, record);  
	        grid4.getView().refresh();
	        grid4.getSelectionModel().selectRange(index - 1, index - 1);  
	    }  
}


function downmove(grid_id4){
	
	var grid=Ext.getCmp(grid_id4);
	var store = grid.getStore();
	var records = grid.getSelectionModel().getSelections();
	   var record = records[0]; 
	    var index = store.indexOf(record);
	   
	    if (index < store.getCount() - 1) {  
	        store.removeAt(index);  
	        store.insert(index + 1, record);  
	        grid.getView().refresh(); 
	        grid.getSelectionModel().selectRange(index + 1, index + 1);  
	    }  
}


</script>
