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
//��ʼ��ҳ����Ϣ
var pWindow='';
var pparam='';
function init(){//��ʼ������
	pWindow=window.dialogArguments['window'];
	pparam=window.dialogArguments['param'];
	var arr=pparam.split(',');
	var ctci=arr[0];
	var tablejhinfo=arr[1];
	document.getElementById("ctci").value=ctci;
	document.getElementById("tablejhinfo")	.value=tablejhinfo;
	var pageparam=arr[1];
	document.getElementById("pageparam").value=pageparam;
	document.getElementById('g1').firstChild.style.width='380px';//�Ѷ��������ֶ�
	document.getElementById('recordsetboxproperty').firstChild.style.width='380px';//��¼��ϸ�ʽ����
	document.getElementById('fieldruleboxproperty').firstChild.style.width='380px';//�ֶ����ɹ�������
	document.getElementById('fieldruleboxproperty').firstChild.style.height='226px';//�ֶ����ɹ�������
	
	document.getElementById('selectTarget').firstChild.style.height='486px';//ѡ��ָ����
	document.getElementById('searchResultSymbol').firstChild.style.width='380px';//��ѯ���ָ����
	document.getElementById('searchResultSymbol').firstChild.style.height='486px';//��ѯ���ָ����
	document.getElementById('fldAttributeSet').firstChild.style.height='245px';//�ֶ���������
	document.getElementById('fldAttributeSet').firstChild.style.width='380px';//�ֶ���������
	document.getElementById('sureCondition').firstChild.style.height='325px';//ȷ������
	document.getElementById('sureCondition').firstChild.style.width='420px';//ȷ������
	document.getElementById('orderzbx').firstChild.style.width='390px';//����ָ����
	document.getElementById('selectzbx').firstChild.style.width='350px';//ѡ��ָ����
	
	radow.doEvent("initpage");
}
function confirmAct(str){//��ʾ
	if(confirm(str)){
		window.close();
	}else{
		window.close();
	}
}


function setGridListChecked(row,col,col_id,grid_id){//�����б�ѡ�� ��ѡ
	var grid=Ext.getCmp(grid_id);
	var orderStore = grid.getStore();
	if(orderStore.getAt(row).get(col_id)==true){//��ǰ�й�ѡ�У�������ѡ����ȡ��
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

/* function mergefieldnextfunc(){//����ֶ� ����һ��
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
	document.getElementById("searchGrpFld").value=searchGrpFld;//���е������������
	 
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"��@��";
	}
	document.getElementById("allqryusestr").value=allqryusestr;//���е����ص��������
	radow.doEvent('saveFunc');
}
function swithTabForSave(tabNum){
	odin.ext.getCmp('jltab').activate('tab'+tabNum);
}
function insertGrpFld(rsm001,ctci){															 
	 	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysbuilder.CreateDefine&eventNames=insertGrpData';
		ShowCellCover('start','ϵͳ��ʾ','����ֶ�����������,�����Ե�...');
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
   						if(arr[0]==2){//�ɹ�
   							alert("����ɹ�!");
   						}else if(arr[0]==1){//ʧ��
   							alert("����ʧ��!"+arr[1]);
   						}else if(arr[0]==3){
   							alert("����ɹ�!(���ڹ��������ݣ��ѽ�ȡ��)");
   						}
   						Ext.Msg.hide();
   					}
        	   }
           }
	    });
}
//��ٽ�����
function ShowCellCover(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "�ر�";
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
		alert("��sql���ʽ����ѽ��ָ��������ȫ�����");
		var ctci=document.getElementById("ctci").value;
		var param=ctci+",25";
		$h.openPageModeWin('GroupSqlRes','pages.cadremgn.sysbuilder.GroupSqlRes','Sql���ʽƴд�� ',630,460,param,'<%=request.getContextPath()%>');
	}else{
		alert("��ѡ����ָ����");
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
	<odin:hidden property="ctci"/><!-- ��Ϣ��id -->
	<odin:hidden property="pageparam"/><!-- ҳ����������ã�ʶ������or�޸Ĺ��� -->
	<odin:hidden property="searchGrpFld"/><!-- ���е������������ -->
	<odin:hidden property="allqryusestr"/><!-- ���е����ص�������� -->
	<odin:hidden property="tablesInfo"/><!-- ��ʼ����Ϣ��ѡ����Ϣ -->
	<odin:hidden property="setfld"/>
	<odin:hidden property="tablejhinfo"/><!-- ��ҳ����Ϣ�� -->
</div>  
<div id="tooldiv"></div>
	<odin:tab id="jltab" height="550">
		    <odin:tabModel>
		    	<odin:tabItem title="&nbsp;&nbsp;����ֶ�&nbsp;&nbsp;" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="ѡ����Ϣ��" id="tab2"></odin:tabItem>
		    	<odin:tabItem title="���ָ����" id="tab3"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;" id="tab4"></odin:tabItem>
		    	<odin:tabItem title="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;" id="tab5" isLast="true"></odin:tabItem>
		    </odin:tabModel> 
		    <odin:tabCont itemIndex="tab1">
				<div style="width: 100%;height: 580px;margin-left: 10px;">
					<table>
						<tr>
							<odin:textEdit property="showname" label="��ǰ�����ֶ�" size="53" readonly="true"></odin:textEdit>
							<!-- <font style="width: 100px;font-size: 13;font-weight: bolder;">��ǰ�����ֶ�</font><input type="text" id="t1" style="width: 200px;"> -->
						</tr>
						<tr>  
							<td colspan="2" style="">
				 				<odin:groupBox property="g1" title="�Ѷ��������ֶ�"  >
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
								<odin:groupBox title="��¼��ϸ�ʽ����" property="recordsetboxproperty">
								<table >
									<tr>
										<odin:select2 property="startrow" label="ѡ���ѯ�����¼:�ӵ�" codeType="GRPSLSC"></odin:select2>
										<td>
											<font style="font-size: 12;margin-left: 3px;">��</font>
										</td>
									</tr>
									<tr>  
										<odin:select2 property="endrow" label="����"  codeType="GRPSLSC"></odin:select2>
										<td>
											 <font style="font-size: 12;margin-left: 3px;">��</font>
										</td>
									</tr>
									<tr>
										<odin:select2 property="connector" label="ѡ���¼�����ӷ�"  codeType="GRPCNYB"></odin:select2>
										<td>
										</td>
									</tr>
									<tr>
										<odin:textEdit property="indentrow" label="��¼��������:ÿ��"></odin:textEdit>
										<td>
											<font style="font-size: 12;">���ַ�</font>
										</td>
									</tr>
									<tr>  
										<odin:textEdit property="indextnum" label="ÿ��"></odin:textEdit>
										<td>
											<font style="font-size: 12;">���ַ�λ��</font>
										</td>	
									</tr>
									<tr>  
										
										<td>
											<odin:checkbox property="firstindex" name="firstindex" label="�����п�ʼ����"></odin:checkbox>
										</td>
										<td >
											<odin:checkbox property="centerbranch" name="centerbranch" label="��¼����о�������"></odin:checkbox>
										</td>
										<td >
										</td>
									</tr>
								</table>
								</odin:groupBox>
								<odin:groupBox title="�ֶ����ɹ�������" property="fieldruleboxproperty">
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
								<odin:button text="��һ��" ></odin:button>
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
		    							<odin:groupBox title="ѡ����Ϣ��" >
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
		    							<odin:groupBox title="ָ����" >
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
    							<odin:groupBox title="ѡ��ָ����" property="selectTarget">
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
																<odin:gridEditColumn header="����������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
																<odin:gridEditColumn header="������������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
																<odin:gridEditColumn header="ָ��������" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
																<odin:gridEditColumn header="�е���������" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
																<odin:gridEditColumn header="ָ�����Ӧ����" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
																<odin:gridEditColumn header="��Ϣ�����" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
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
				    										<odin:button text="���" handler="addIndexTermFld"></odin:button>
							    						</td>
							    						<td width="80px">
							    						</td>
							    						<td align="center">
						    								<odin:button text="ȫ�����" handler="searchAddAll"></odin:button>
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
								<odin:groupBox title="��ѯ���ָ����" width="600px" property="searchResultSymbol">
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
																<odin:gridEditColumn header="����������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
																<odin:gridEditColumn header="������������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
																<odin:gridEditColumn header="ָ��������" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
																<odin:gridEditColumn header="�е���������" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
																<odin:gridEditColumn header="ָ�����Ӧ����" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
																<odin:gridEditColumn header="��Ϣ�����" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
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
															<tr> <td><odin:button text="����" handler="indexTermUpMove"></odin:button></td> </tr>
															<tr height="5px"> </tr>
															<tr> <td><odin:button text="����" handler="indexTermDownMove"></odin:button></td> </tr>
															<tr height="5px"> </tr>
															<tr> <td><odin:button text="ɾ��" handler="indexTermRemove"></odin:button></td> </tr>
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
														<odin:checkbox property="check2" label="��ѯ���Ϊsql�������ʽ" onclick="check2Func(this)"></odin:checkbox>
													</td>
													<td width="6px">
													</td>
													<td>
														<odin:button property="button1" text="�༭���ʽ"  handler="editExpressionFunc"></odin:button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<odin:groupBox title="�ֶ���������" property="fldAttributeSet">
												<table style="height:100%;">
													<tr>
														<td>
															<odin:checkbox property="check4" label="�ϲ�ͬ�����ֶ�" onclick="check4Func(this)"></odin:checkbox>
														</td>
														<td>
															<odin:checkbox property="check3" label="�����ֶ�ת��Ϊ����" onclick="check3Func(this)"></odin:checkbox>
														</td>
													</tr>
													<tr>
														<odin:select2 property="connsymbol" label="�ֶκ�������ַ�" onblur="onmouseoutFunc(this,0)" codeType="GRPCNYB"  onchange="grpFldAttributeFunc1"></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select12" label="ֵΪ��ʱ���ӷ�����ʽ" codeType="GRPYBDL" onblur="onmouseoutFunc(this,1)" onchange="grpFldAttributeFunc2"></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select13" label="�ֶ�Ϊ�յĴ����" codeType="GRPNLRP" onblur="onmouseoutFunc(this,2)" onchange="grpFldAttributeFunc3" ></odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select property="select14" label="�ֶκϼƺ���" onblur="onmouseoutFunc(this,3)" onchange="grpFldAttributeFunc4"></odin:select>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="selectsame" label="ͬ����������ӷ�" codeType="GRPSMRP" onblur="onmouseoutFunc(this,4)" onchange="grpFldAttributeFunc5"> </odin:select2>
													</tr>
													<tr height="20px">
													</tr>
													<tr>
															<odin:select2 property="select15" label="���ڸ�ʽ��" codeType="GRPDTFM" onblur="onmouseoutFunc(this,5)" onchange="grpFldAttributeFunc6"></odin:select2>
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
		    							<odin:groupBox title="ѡ��ָ����" >
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
													<odin:gridEditColumn header="����������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type" editor="text" />
													<odin:gridEditColumn header="������������" hidden="true" align="left" edited="false" width="290" dataIndex="col_data_type_should" editor="text" />
													<odin:gridEditColumn header="ָ��������" hidden="true" align="left" edited="false" width="290" dataIndex="code_type" editor="text" />
													<odin:gridEditColumn header="�е���������" hidden="true" align="left" edited="false" width="290" dataIndex="col_name" editor="text" />
													<odin:gridEditColumn header="ָ�����Ӧ����" hidden="true" align="left" edited="false" width="290" dataIndex="col_code" editor="text" />
													<odin:gridEditColumn header="��Ϣ�����" hidden="true" align="left" edited="false" width="290" dataIndex="table_code" editor="text" />
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
		    							<odin:groupBox title="ȷ������" property="sureCondition">
		    								<div>
		    									<odin:tab id="tabcondition" width="400" tabchange="tab4onchange">
		    										 <odin:tabModel>
		    										 	<odin:tabItem title="������ѯ" id="tabcondition1"></odin:tabItem>
		    											<odin:tabItem title="�߼���ѯ" id="tabcondition2" isLast="true"></odin:tabItem>
		    										 </odin:tabModel>
			    									<odin:tabCont itemIndex="tabcondition1">
			    										<table>
			    											<tr height="3px">
			    											</tr>
															<tr>
																<odin:textEdit property="conditionName4" label="����ָ����"  readonly="true"></odin:textEdit>
																<td rowspan="2" style="text-align:center;width:200">
																<odin:button text="&nbsp&nbsp&nbsp�� &nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp"  property="btnm1" handler="conditionclear" ></odin:button>
																</td>
															</tr>
															<tr>
																<odin:select property="conditionName5" label="��������" width="158" onchange="valuefivefunc"></odin:select>
																<td>
																
																</td>
															</tr>
															<tr id="value1" valign="middle" style="display: none;">
																<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵһ</td>
																<td id='codediv'>
																	<input type="hidden" id="conditionName6" name="conditionName6" />
																	<input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style='cursor:default;' required="false"  label="false" />
																</td>
																<%-- <odin:select property="conditionName6" label="ֵһ" width="158" ></odin:select> --%>
																<td style="text-align:center;width:200">
																<odin:button text="������б�" property="btnm2" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value11" style="display: none;" valign="middle">
																<odin:dateEdit property="conditionName61" label="ֵһ" width="158"></odin:dateEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="������б�" property="btnm21" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value111" style="display: block;" valign="middle">
																<odin:textEdit property="conditionName611" label="ֵһ"  ></odin:textEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="������б�" property="btnm211" handler="addtolistfunc"></odin:button>
																</td>
															</tr>
															<tr id="value1111" style="display: none;" valign="middle">
																<odin:numberEdit property="conditionName6111" label="ֵһ" ></odin:numberEdit>
																<td  style="text-align:center;width:200">
																<odin:button text="������б�" property="btnm211" handler="addtolistfunc" ></odin:button>
																</td>
															</tr>
															<tr id="value2" style="display: none;" valign="middle">
																<odin:dateEdit property="conditionName7" label="ֵ��"  width="140" ></odin:dateEdit>
																<td style="text-align:center;width:200">&nbsp;
																</td>
															</tr>
															<tr id="value21" style="display: none;" valign="middle">
																<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵ��</td>
																<td id='codediv2' >
																	<input type="hidden" id="conditionName71" name="conditionName71" />
																	<input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style='cursor:default;' required="false"  label="false" />
																</td>
																<%-- <odin:select property="conditionName71" label="ֵ��" width="140" ></odin:select> --%>
																<td style="text-align:center;width:200">
																</td>
															</tr>
															<tr id="value211" style="display: block;" valign="middle">
																<odin:numberEdit property="conditionName711" label="ֵ��"  ></odin:numberEdit>
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
						    												<odin:select property="stringfuncid" defaultValue="�ַ�������" onchange="stringOnChange"></odin:select>
						    												<odin:select property="datefuncid" defaultValue="���ں���" onchange="dateOnChange"></odin:select>
						    											</tr>
						    											<tr>
						    												<td style="width:15px;"></td>
						    												<odin:select property="numberfuncid" defaultValue="��ֵ����" onchange="numberOnChange"></odin:select>
						    												<odin:select property="opratesymfuncid" defaultValue="�����" onchange="opratesymOnChange"></odin:select>
						    											</tr>
						    											<tr>
						    												<td style="width:15px;"></td>
						    												<odin:select property="infosetfuncid" defaultValue="��Ϣ���б�" onchange="tableOnChange"></odin:select>
						    												<odin:select property="databasefuncid" defaultValue="���ݿ��ֶ��б�" onchange="databaseOnChange"></odin:select>
						    											</tr>
			    													</table>
			    												</td>
			    											</tr>
			    											<tr>
			    												<td>
			    													<table>
				    													<tr>
						    												<td style="font-size:12px;text-align: right;">���ʽƴд��</td>
						    												<td colspan="2">
						    													<table>
						    														<tr>
									    												<odin:textEdit width="210" property="expressionid" onmouseout="expressOut(this)"></odin:textEdit>
						    														</tr>
						    													</table>
						    												</td>
						    												<td >
						    													<odin:button text="&nbsp&nbsp&nbsp�� &nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp" property="tab4clear" handler="cleanTab4High"></odin:button>
						    												</td>
						    											</tr>
						    											<tr>
						    												<td style="font-size:12px;text-align: right;">���ʽ����˵��</td>
						    												<td colspan="2">
						    													<table>
						    														<tr>
									    												<odin:textEdit width="210" property="expressionexplainid" ></odin:textEdit>
						    														</tr>
						    													</table>
						    												</td>
						    												<td >
						    													<odin:button text="������б�" property="tab4AddToList" handler="addListHigh"></odin:button>
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
															<odin:button text="&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp"  property="editbtnm" handler="editbtnFunc">
															</odin:button></td>
														</tr>
														<tr>
															<td id="iddeletecondition" style="text-align:right;width:48;height:30">
															<odin:button text="&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp"  property="btnm3" handler="btnm3Func">
															</odin:button></td>
														</tr>
														<tr>
															<td id="iddeleteallcondition" style="text-align:right;width:50;height:30">
															<odin:button text="ȫ��ɾ��"  property="btnm4" handler="btnm4Func"></odin:button></td>
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
		    							<odin:groupBox title="�������" width="350">
		    								<table >
												<tr>
													<td width="70px">
														<odin:button text="ѡ������" property="btnn1" handler="btnnFunc"></odin:button>
													</td>
													<td><odin:button text="��" property="btnn2"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="��" property="btnn3"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="��" property="btnn4"  handler="btnnFunc"></odin:button></td>
													<td><odin:button text="����" property="btnn5"  handler="btnnFunc"></odin:button></td>
													<td ><odin:button text="����" property="btnn6"  handler="btnnFunc"></odin:button></td>
													<td align="right">
														<odin:button text="��������" property="btnn7" handler="btnn7Funcc" ></odin:button>
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
													<td colspan="8" style="font-size:14;color:red;" >�뽫����ѡ�롰������������У�������Ч</td>
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
		    							<odin:groupBox title="ѡ��ָ����" property="selectzbx" width="300">
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
		    							<odin:button text="���" handler="addOrderFld"></odin:button>
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
		    							<odin:groupBox title="����ָ����" property="orderzbx" width="380">
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
						    								<tr><td align="center"><odin:button text="����" property="upmove" handler="upmovefunc"></odin:button></td> </tr>
						    								<tr height="24px"><td>&nbsp;</td></tr>
						    								<tr><td align="center"><odin:button text="����" property="downmove" handler="dowmmoveofunc"></odin:button></td> </tr>
						    								<tr height="24px"><td>&nbsp;</td></tr>
						    								<tr><td align="center"><odin:button text="�Ƴ�" property="remove" handler="removeRow"></odin:button></td> </tr>
						    							</table>
						    						</td>
		    									</tr>
		    								</table>
		    							</odin:groupBox>
		    						</td>
		    					</tr>
		    					<%-- <tr>
		    						<td align="center">
		    							<odin:button text="ȫ�����" handler="clearAllOrder"></odin:button>
		    						</td>
		    					</tr> --%>
		    				</table>
		    			</td>
		    		</tr>
		    	</table>
		    </odin:tabCont>
	 </odin:tab>
	<div>
		<odin:hidden property="showcolname"/><!-- ָ������ -->
		<odin:hidden property="col_name"/><!-- ָ������ -->
		<odin:hidden property="table_code"/><!-- ���� -->
		<odin:hidden property="col_code"/><!-- �ֶ��� -->
		<odin:hidden property="col_data_type"/><!-- ���������� -->
		<odin:hidden property="col_data_type_should"/><!-- ָ���������2 -->
		<odin:hidden property="code_type"/><!--ָ��������� -->
		<odin:hidden property="qryusestr"/><!--//ƴ�ӵ�ǰ����������ڱ��浽�������� -->
		<odin:hidden property="conditoionlist"/><!--//ƴ���������������ʾ -->
		<odin:hidden property="conditionStr"/><!-- ����ƴ�ӵ�where���� -->
		<odin:hidden property="zhtj"/><!-- �������ڱ����������� -->
		<odin:hidden property="querysql"/><!-- �������ɵ�sql��ҳ�� -->
		<odin:hidden property="tabType" value="1"/>
	</div>
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<%-- <odin:buttonForToolBar id="change"  text="�޸�"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="delete"  text="ɾ��"/> --%>
	<odin:fill/>
	<odin:buttonForToolBar id="Save"  text="����" handler="saveFunc"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="reset"  text="����" isLast="true" handler="cleanAllData"/>
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
function cleanAllData(){//����
	//ȡ������ֶ��Ѿ�ѡ�е���Ϣ�� contentList
	cancelSelect('contentList','change')
	//�������
	Ext.getCmp('contentList4').getStore().removeAll();
	Ext.getCmp('contentList3').getStore().removeAll();
	//��� ����
	cleanInfo();
	Ext.getCmp('contentList6').getStore().removeAll();
	//���ָ����
	searchObj=new Object();
	Ext.getCmp('contentList5').getStore().removeAll();
	Ext.getCmp('searchList').getStore().removeAll();
	//���ѡ����Ϣ��
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
