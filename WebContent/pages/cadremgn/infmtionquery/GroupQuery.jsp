<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel"%>
<%@include file="/comOpenWinInit2.jsp"%>
<%
	
%>
<style>
	.div_area {
		border: 1px solid #b5b8c8;
		overflow-y: scroll;
	}
	
	#area4 {
		border: 0px rgb(206, 221, 239) solid;
		height: 100%;
		float: left;
	}
	
	#btnm1,
	#btnm2,
	#btnm3,
	#btnm4 {
		width: 60;
		height: 30;
	}
	
	#btn5,
	#btn6,
	#btn7,
	#btn8,
	#btn9 {
		width: 60;
		height: 30;
	}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<odin:hidden property="qrysqlcc" value="" />
<odin:hidden property="allqryusestr" />
<odin:hidden property="qryusestr" />
<div>
	<div id="tooldiv" style="boder: 0;"></div>

	<odin:tab id="tab">
		<odin:tabModel>
			<odin:tabItem title="��������" id="tab1" isLast="true"></odin:tabItem>
			<%-- <odin:tabItem title="���" id="tab2" isLast="true"></odin:tabItem> --%>
		</odin:tabModel>
		<odin:tabCont itemIndex="tab1">
			<div id="area4">
				<table>
					<tr>
						<td style="width: 250;">
							<odin:groupBox title="������ͼ">
								<odin:editgrid property="viewListGrid" width="230" height="495" rowDbClick="rowDbClicktable" url="/">
									<odin:gridJsonDataModel root="data">
										<odin:gridDataCol name="checked" />
										<odin:gridDataCol name="condition" />
										<odin:gridDataCol name="qvid" />
										<odin:gridDataCol name="chinesename" isLast="true" />
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridColumn header="selectall" checkBoxSelectAllClick="getCheckAll" gridName="viewListGrid" align="center" editor="checkbox" edited="true" width="0" dataIndex="checked" />
										<odin:gridEditColumn header="��ͼ���ʽ" width="0" dataIndex="condition" hidden="true" edited="false" editor="text" align="left" />
										<odin:gridEditColumn header="��ͼID" width="0" dataIndex="qvid" hidden="true" edited="false" editor="text" align="left" />
										<odin:gridEditColumn header="��ͼ��" width="180" dataIndex="chinesename" edited="false" editor="text" align="left" isLast="true" />
									</odin:gridColumnModel>
								</odin:editgrid>
							</odin:groupBox>
						</td>
					</tr>
				</table>
			</div>
			<div id="area4" style="width: 250;">
				<table>
					<tr>
						<td style="width:320;">
							<odin:groupBox title="��Ԥ��ָ����(��ʾ:��ŷ�Ԥ����Ϣ��)" >
							<odin:editgrid property="codeList2Grid" forceNoScroll="true" width="320" height="224" url="/" rowDbClick="rowFldeDbClick" >
								<odin:gridJsonDataModel   root="data" >
									<odin:gridDataCol name="col_name" />
									<odin:gridDataCol name="col_name1" />
									<odin:gridDataCol name="code_type" />
									<odin:gridDataCol name="col_code" />
									<odin:gridDataCol name="col_data_type" />
									<odin:gridDataCol name="table_code" />
									<odin:gridDataCol name="col_data_type_should" />
									<odin:gridDataCol name="checked"  isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="ָ������" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
									<odin:gridEditColumn2 header="ָ������" width="215"  dataIndex="col_name1"  edited="false" sortable="false" menuDisabled="true" editor="text" align="left"/>
									<odin:gridEditColumn header="ָ���������" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
									<odin:gridEditColumn header="����������" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
									<odin:gridEditColumn header="ָ����" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
									<odin:gridEditColumn header="��Ϣ��" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
									<odin:gridEditColumn header="ָ���������2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
									<odin:gridColumn header="selectall" width="70" gridName="codeList2Grid" checkBoxClick="checkClickCode" align="center"  editor="checkbox" edited="true" dataIndex="checked"  isLast="true" />
								</odin:gridColumnModel>
							</odin:editgrid>
							</odin:groupBox>
						</td>
					</tr>
					<tr>
					<td style="width:320;">
						<odin:groupBox title="ָ��������(��ʾ:������ʵ���Ƴ�������)" >
						<odin:editgrid property="codeList2Grid1" forceNoScroll="true" width="320" height="224" url="/" rowDbClick="rowFldeDbClick" >
							<odin:gridJsonDataModel   root="data" >
								<odin:gridDataCol name="col_name" />
								<odin:gridDataCol name="col_name1" />
								<odin:gridDataCol name="code_type" />
								<odin:gridDataCol name="col_code" />
								<odin:gridDataCol name="col_data_type" />
								<odin:gridDataCol name="table_code" />
								<odin:gridDataCol name="col_data_type_should" />
								<odin:gridDataCol name="caozuo"  isLast="true" />
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn header="ָ������" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
								<odin:gridEditColumn2 header="ָ������" width="215"  dataIndex="col_name1" sortable="false" menuDisabled="true"  edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="ָ���������" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="����������" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="ָ����" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="��Ϣ��" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="ָ���������2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
								<odin:gridEditColumn header="����" width="70" align="center" edited="false" editor="text" dataIndex="caozuo" renderer="runorstop"  isLast="true" />
							</odin:gridColumnModel>
						</odin:editgrid>
						</odin:groupBox>
					</td>
				</tr>
				</table>
			</div>
			<div id="area4" style="width: 200;">
				<div id="area44">
					<table>
						<tr>
							<td>
								<odin:groupBox title="ȷ������">
									<table>
										<tr>
											<odin:textEdit property="conditionName4" label="����ָ����" width="160" readonly="true"></odin:textEdit>
											<td rowspan="2" style="text-align: right; width: 90">
												<odin:button text="&nbsp&nbsp&nbsp�� &nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp" property="btnm1" handler="cleancondition"></odin:button>
											</td>
										</tr>
										<tr>
											<odin:select property="conditionName5" label="��������" onchange="valuefivefunc" width="160"></odin:select>
										</tr>
										<tr id="value1" style="display: none;">
											<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵһ</td>
											<td id='codediv'>
												<input type="hidden" id="conditionName6" name="conditionName6" />
												<input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style='cursor:default;' required="false" label="false" />
											</td>
											<td rowspan="2" style="text-align:right;width:90">
												<odin:button text="������б�" property="btnm2" handler="addtolistfunc"></odin:button>
											</td>
										</tr>
										<tr id="value11" style="display: none;">
											<odin:dateEdit property="conditionName61" label="ֵһ" width="160"></odin:dateEdit>
											<td rowspan="2" style="text-align:right;width:90">
												<odin:button text="������б�" property="btnm21" handler="addtolistfunc"></odin:button>
											</td>
										</tr>
										<tr id="value111" style="display: block;">
											<odin:textEdit property="conditionName611" label="ֵһ" width="160"></odin:textEdit>
											<td rowspan="2" style="text-align:right;width:90">
												<odin:button text="������б�" property="btnm211" handler="addtolistfunc"></odin:button>
											</td>
										</tr>
										<tr id="value1111" style="display: none;">
											<odin:numberEdit property="conditionName6111" label="ֵһ" width="160"></odin:numberEdit>
											<td rowspan="2" style="text-align:right;width:90">
												<odin:button text="������б�" property="btnm211" handler="addtolistfunc"></odin:button>
											</td>
										</tr>
										<tr id="value2" style="display: none;">
											<odin:dateEdit property="conditionName7" label="ֵ��" width="160"></odin:dateEdit>
										</tr>
										<tr id="value21" style="display: none;">
											<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵ��</td>
											<td id='codediv2'>
												<input type="hidden" id="conditionName71" name="conditionName71" />
												<input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style='cursor:default;' required="false" label="false" />
											</td>
										</tr>
										<tr id="value211" style="display: block;">
											<odin:numberEdit property="conditionName711" label="ֵ��" width="160"></odin:numberEdit>
										</tr>

									</table>
									<table style="width: 320">
										<tr>
											<td>
												<div style="width: 240; height: 160;" id="conditionName8" class="div_area"></div>
											</td>
											<td style="width: 50;">
												<table>
													<tr>
														<td style="text-align: right; width: 50; height: 80">
															<odin:button text="&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp" property="btnm3" handler="btnm3Func"></odin:button>
														</td>
													</tr>
													<tr>
														<td style="text-align: right; width: 50; height: 80">
															<odin:button text="ȫ��ɾ��" property="btnm4" handler="btnm4Func"></odin:button>
														</td>
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
								<odin:groupBox title="�������">
									<table style="width: 320; ">
										<tr>
											<td style="width: 80">
												<odin:button text="ѡ������" property="btnn1" handler="btnnFunc"></odin:button>
											</td>
											<td>
												<odin:button text="��" property="btnn2" handler="btnnFunc"></odin:button>
											</td>
											<td>
												<odin:button text="��" property="btnn3" handler="btnnFunc"></odin:button>
											</td>
											<td>
												<odin:button text="��" property="btnn4" handler="btnnFunc"></odin:button>
											</td>
											<td>
												<odin:button text="����" property="btnn5" handler="btnnFunc"></odin:button>
											</td>
											<td>
												<odin:button text="����" property="btnn6" handler="btnnFunc"></odin:button>
											</td>
											<td style="text-align: right;">
												<div style="margin-right: 10">
													<odin:button text="��������" property="btnn7" handler="btnn7Funcc"></odin:button>
												</div>
											</td>
										</tr>
										<tr style="width: 540px;">
											<odin:textarea property="conditionName9" colspan="8" rows="6" readonly="true"></odin:textarea>
										</tr>
										<tr style="height:22px;">
											<td colspan="7" style="font-size: 15; color: red;">�뽫����ѡ�롰������������У�������Ч</td>
										</tr>
									</table>
								</odin:groupBox>
						</tr>
					</table>
				</div>
			</div>
			<div style="width: 270; text-align: right;">
				<table>
					<tr>
						<td style="text-align: right;">
							<input id="btn5" class="yellowbutton" type="button" value="��&nbsp;&nbsp;ѯ" onclick="previewFunc()" style="margin-right: 10px;float: right;"/>
						</td>
					</tr>
				</table>
			</div>
		</odin:tabCont>
	</odin:tab>
	<odin:hidden property="conditoionlist" />
	<!-- ���������� ���ɵ�ǰ��ƴ�ӵ�����  -->
	<odin:hidden property="table_code" />
	<!-- ��ǰѡ�е���Ϣ�� -->
	<odin:hidden property="col_code" />
	<!-- ��ǰѡ�е���Ϣ�� -->
	<odin:hidden property="col_name" />
	<!-- ��ǰѡ�е���Ϣ������ -->
	<odin:hidden property="col_name_quan" />
	<!-- ��ǰѡ�е���Ϣȫ���� -->
	<odin:hidden property="code_type" />
	<!-- ��ǰѡ�е���Ϣ�� -->
	<odin:hidden property="col_data_type" />
	<!-- ��ǰѡ�е���Ϣ�� -->
	<odin:hidden property="col_data_type_should" />
	<!-- ��ǰѡ�е���Ϣ�� -->
	<!-- �ֶ����ͱ�ʾ -->
	<odin:hidden property="qvid" />
	<!-- ��ͼ���� -->
	<odin:hidden property="conditionStr" />
	<!-- �������ת�� �ɵ� where���� -->
	<odin:hidden property="querysql" />
	<!-- �洢�Ѿ����ɵ�sql -->
	<odin:hidden property="parenttablename" />
	<!-- �洢��ҳ����Ϣ�� -->
	<odin:hidden property="for_table_qryuse" />
	<!-- ���qryuse����Ϣ���� -->
	<odin:hidden property="for_table_qryuses" />
	<!-- ���qryuse����Ϣ���� -->
</div>
<script type="text/javascript">
function checkClickCode(num,un,dataIndex,gridid){//ѡ����Ϣ�ʵ�ָ���Ϣ������Ԥ����Ϣ��
	//alert(num);//5->alert(4);
	radow.doEvent("checkClickCode",num);
}
function delThisOne(obj,value,rowIndex){
	radow.doEvent("delThisOne",rowIndex);
}
function upThisOne(obj,value,rowIndex){
	radow.doEvent("upThisOne",rowIndex);
}
function downThisOne(obj,value,rowIndex){
	radow.doEvent("downThisOne",rowIndex);
}
	
	function runorstop(value, params, record,rowIndex,colIndex,ds){
		var contextPath = '<%=request.getContextPath()%>';
		return "<img  src='"+contextPath+"/images/wrong.gif' title='ɾ��' style='cursor:pointer' onclick=delThisOne(this,'"+value+"','"+rowIndex+"')><a>&nbsp;&nbsp;</a><img  src='"+contextPath+"/image/up.png' title='����' style='cursor:pointer' onclick=upThisOne(this,'"+value+"','"+rowIndex+"')><img src='"+contextPath+"/image/down.png' title='����' style='cursor:pointer' onclick=downThisOne(this,'"+value+"','"+rowIndex+"')>";
	}
	function refreshList() { //�����ѽ�����ͼ
		radow.doEvent("viewListGrid.dogridquery");
	};

	function rowDbClicktable(grid, rowIndex, colIndex, event) { //˫����ͼ����ѯ��ʾ��Ϣ��
		var record = grid.store.getAt(rowIndex); //���˫���ĵ�ǰ��ͼ�еļ�¼
		document.getElementById("qrysqlcc").value = grid.store.getAt(rowIndex).get("qvid");
		radow.doEvent("selqvid", record.get("qvid"));
	};

	function cleancondition() { //�������������Ϣ
		radow.doEvent("clearqvfid");
	};

	function getCheckAll() {
		radow.doEvent("selqvid");
	};
	var codecombox;

	function rowFldeDbClick(grid, rowIndex, colIndex, event) { //˫����Ϣ��
		var record = grid.store.getAt(rowIndex); //���˫���ĵ�ǰ�еļ�¼
		var col_name_quan = record.get("col_name1"); //ָ��ȫ���� -- A01.A0101 ����
		var table_code = record.get("table_code");//ָ����Ϣ�� -- A01
		var col_code = record.get("col_code");//ָ����Ϣ�� -- A0101
		var col_name = record.get("col_name"); //ָ������ -- ����
		var code_type = record.get("code_type"); //ָ��������� -- ZB01||null
		var col_data_type = record.get("col_data_type");//������������ -- C
		var col_data_type_should = record.get("col_data_type_should"); //ָ��������ݿ����� -- varchar2
		document.getElementById("col_name_quan").value = col_name_quan;
		document.getElementById("table_code").value = table_code;
		document.getElementById("col_code").value = col_code;
		document.getElementById("col_name").value = col_name;
		document.getElementById("code_type").value = code_type;
		col_data_type=document.getElementById("col_data_type").value = col_data_type==null||col_data_type=="null"?"":col_data_type.toUpperCase();
		col_data_type_should=document.getElementById("col_data_type_should").value = col_data_type_should==null?'':col_data_type_should.toLowerCase();
		//alert(code_type+"||"+col_data_type+"||"+col_data_type_should);
		if(code_type!=null &&code_type!=' ' &&code_type!=''&&code_type!='null'){//����ѡ ��between and  ��like  
			document.getElementById("value1111").style.display='none';//number
			document.getElementById("value111").style.display='none';//text
			document.getElementById("value11").style.display='none';//date
			document.getElementById("value1").style.display='block';//select
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='none';//text
			document.getElementById("value211").style.display='block';//number
			radow.doEvent("code_type_value1",code_type);
		}else if(col_data_type_should=='date'||col_data_type=='T'){//�������ֶ�   ��between and  ��like 
			document.getElementById("value1").style.display='none';
			document.getElementById("value111").style.display='none';
			document.getElementById("value1111").style.display='none';
			document.getElementById("value11").style.display='block';
			document.getElementById("value2").style.display='block';
			document.getElementById("value21").style.display='none';
			document.getElementById("value211").style.display='none';
			radow.doEvent("selectValueList");
		}else if(col_data_type_should=="" ||col_data_type_should=="clob"||col_data_type_should=="varchar2" ||col_data_type_should=="null"||col_data_type_should==null ||col_data_type_should.length==""||col_data_type_should.length==" "){//�ı� ��like ��between and
			document.getElementById("value1").style.display='none';
			document.getElementById("value11").style.display='none';
			document.getElementById("value111").style.display='block';
			document.getElementById("value1111").style.display='none';
			document.getElementById("value2").style.display='none';
			document.getElementById("value21").style.display='none';
			document.getElementById("value211").style.display='block';
			radow.doEvent("selectValueListNobt");
		}else if(col_data_type_should=="number"){//���� ��like  ��between and
			document.getElementById("value1").style.display='none';
			document.getElementById("value11").style.display='none';
			document.getElementById("value111").style.display='none';
			document.getElementById("value1111").style.display='block';
			document.getElementById("value2").style.display='none';
			document.getElementById("value21").style.display='none';
			document.getElementById("value211").style.display='block';
			radow.doEvent("selectValueListLikeBt");
		}else{
			document.getElementById("value1").style.display='none';
			document.getElementById("value11").style.display='none';
			document.getElementById("value111").style.display='block';
			document.getElementById("value1111").style.display='none';
			document.getElementById("value2").style.display='none';
			document.getElementById("value21").style.display='block';
			document.getElementById("value211").style.display='none';
			radow.doEvent("selectValueListNobt");
		}
	}

	function conditionclear() { //�������
		radow.doEvent("conditionclear");
	}

	function addtolistfunc() { //��ӵ��б�
		radow.doEvent("addtolistfunc");
	}
	var txtareaarr = []; //�洢�������� ��ʾ����
	var txtareaarrCode = []; //�洢�������� ƴ��sql����
	function textareadd() {
		var conditoionlist = document.getElementById("conditoionlist").value;
		txtareaarr.push(conditoionlist);
		var arrstr = "";
		for(i = 0; i < txtareaarr.length; i++) {
			arrstr = arrstr + "<span id='spanid" + i + "' style='cursor: pointer;font-size:13px;float:left;z-index:999999999;' onclick='backgroundFunc(this);'>" + (i + 1) + "." + txtareaarr[i] + "</span><br/>";
		}
		document.getElementById("conditionName8").innerHTML = arrstr;
		document.getElementById("for_table_qryuses").value = document.getElementById("for_table_qryuses").value + document.getElementById("for_table_qryuse").value;
		var conditionStr = "";
		var table_code = document.getElementById("table_code").value;
		var col_code = document.getElementById("col_code").value;
		var conditionName5 = document.getElementById("conditionName5").value;
		var col_data_type_should = document.getElementById("col_data_type_should").value;
		var code_type = document.getElementById("code_type").value;
		var tempright = "";
		var temprightlike = "";
		if(code_type != null && code_type != "" && code_type != "null" && code_type != " ") {
			if(conditionName5.indexOf('between')!=-1){
				conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName6").value+"' and '"+document.getElementById("conditionName71").value+"'";
				conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName71").value+"' and '"+document.getElementById("conditionName6").value+"' )";
			} else if(conditionName5.indexOf('null') != -1) {
				//conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " ";
				conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
			} else if(conditionName5.indexOf('like') == -1) {
				conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'" + document.getElementById("conditionName6").value + "' ";
			}  else {
				if(conditionName5.indexOf('%v%') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'%" + document.getElementById("conditionName6").value + "%' ";
				} else if(conditionName5.indexOf('%v') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'%" + document.getElementById("conditionName6").value + "' ";
				} else if(conditionName5.indexOf('v%') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'" + document.getElementById("conditionName6").value + "%' ";
				}
			}
		} else if("date" == col_data_type_should) {
			if(col_data_type_should=='date'){
				if(conditionName5.indexOf('between'!=-1)){//��between
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+") ";
				} else if(conditionName5.indexOf('null') != -1) {
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				} else {
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					conditionStr=" "+table_code+"."+col_code+" "+" "+conditionName5+" "+tempright+" ";
				}
				
			}else if(col_data_type_should!='date'&&col_data_type=='t'){
				if(conditionName5.indexOf('between')!=-1){//��between
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
					conditionStr=" ( to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+templeft+" and "+tempright+")";
				} else if(conditionName5.indexOf('null') != -1) {
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				} else {
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					conditionStr=" to_date("+table_code+"."+col_code+",'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
				}
			}
		} else if("varchar2" == col_data_type_should || col_data_type_should == 'clob' || col_data_type_should == "" || col_data_type_should == " " || col_data_type_should == "null") {
			if(conditionName5.indexOf('null') != -1) {
				conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
			} else if(conditionName5.indexOf('like') == -1) {
				conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'" + document.getElementById("conditionName611").value + "' ";
			} else {
				if(conditionName5.indexOf('%v%') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'%" + document.getElementById("conditionName611").value + "%'";
				} else if(conditionName5.indexOf('%v') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'%" + document.getElementById("conditionName611").value + "'";
				} else if(conditionName5.indexOf('v%') != -1) {
					conditionStr = " " + table_code + "." + col_code + " " + conditionName5 + " " + "'" + document.getElementById("conditionName611").value + "%'";
				}
			}

		} else if("number" == col_data_type_should) {
			if(conditionName5.indexOf('between'!=-1)){//��between
				tempright=document.getElementById("conditionName6111").value;
				templeft=document.getElementById("conditionName711").value;
				conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
				conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+")";
			} else if(conditionName5.indexOf('null') != -1) {
				conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
			} else {
				tempright=document.getElementById("conditionName611").value;
				conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+tempright+" ";
			}
		}
		txtareaarrCode.push(conditionStr);
	}
	var areastr = ""; //��ǰѡ�е�����id
	function backgroundFunc(obj) { //���������¼�
		var spanarr = document.getElementById("conditionName8").children;
		for(i = 0; i < spanarr.length; i++) {
			spanarr[i].style.background = "";
		}
		areastr = obj.id;
		obj.style.background = "pink";
	}
	 
	function btnm3Func() { //�Ƴ�
		if(areastr == "" || areastr.length == 0) {
			alert("��ѡ��Ҫɾ��������!");
			return;
		}
		var longStr = document.getElementById("for_table_qryuses").value;
		document.getElementById("for_table_qryuses").value = "";
		var forLongStr = longStr.split("@");
		forLongStr.splice(num, 1);
		for(i = 0; forLongStr.length > 0 && i < forLongStr.length; i++) {
			document.getElementById("for_table_qryuses").value = document.getElementById("for_table_qryuses").value + forLongStr[i] + "@";
		}
		var num = areastr.substr(areastr.length - 1, areastr.length);
		txtareaarr.splice(num, 1);
		txtareaarrCode.splice(num, 1);
		var arrstr = "";
		for(i = 0; i < txtareaarr.length; i++) {
			arrstr = arrstr + "<span id='spanid" + i + "' style='cursor: pointer;font-size:13px;z-index:999999999;float:left; ' onclick='backgroundFunc(this);'>" + (i + 1) + "." + txtareaarr[i] + "</span><br/>";
		}

		document.getElementById("conditionName8").innerHTML = arrstr;
		areastr = ""; //����������λΪ��
	}

	function btnm4Func() { //ȫ��ɾ��
		txtareaarr = [];
		txtareaarrCode = [];
		document.getElementById("for_table_qryuses").value = "";
		document.getElementById("conditionName8").innerHTML = "";
	}
	//ȫ��ɾ���Ƿ�ɱ༭��־
	var allDeleteFlag = "1"; //1 �� 0 ��
	var leftBrakets = "0"; //����������ʼ��Ϊ0
	var rightBrakets = "0"; //����������ʼ��Ϊ0
	var mapBtn = {
		'btnn2': '(',
		'btnn3': ')',
		'btnn4': '.��.',
		'btnn5': '.����.',
		'btnn6': '.����.'
	}
	var mapBtnCode = {
		'btnn2': '(',
		'btnn3': ')',
		'btnn4': ' not ',
		'btnn5': ' and ',
		'btnn6': ' or '
	}

	function btnnFunc(obj) { //ѡ������
		if(areastr == "") { //
			alert("��ѡ������!");
			return;
		}
		var str = document.getElementById("conditionName9").value;
		if("btnn1" == obj.id) {
			var num = parseInt(areastr.substr(areastr.length - 1, areastr.length)) + 1;
			document.getElementById("conditionName9").value = str + " " + num;
			var tempCondition = "";
			for(i = 0; i < txtareaarrCode.length; i++) {
				if(i == (num - 1)) {
					tempCondition = txtareaarrCode[i];
				}
			}
			arrselect.push(tempCondition);
			document.getElementById("conditionStr").value = document.getElementById("conditionStr").value + tempCondition;
		} else {
			document.getElementById("conditionName9").value = str + mapBtn[obj.id];
			document.getElementById("conditionStr").value = document.getElementById("conditionStr").value + mapBtnCode[obj.id];
		}

		if(allDeleteFlag == '1') {
			allDeleteFlag = '0';
		}
		if("btnn2" == obj.id) {
			leftBrakets = parseInt(leftBrakets) + 1;
		}
		if("btnn3" == obj.id) {
			rightBrakets = parseInt(rightBrakets) + 1;
		}
		radow.doEvent("setDisSelect", obj.id + "," + leftBrakets + "," + rightBrakets);
	}

	function btnn7Funcc() { //��������
		document.getElementById("conditionName9").value = "";
		if(allDeleteFlag == '0') {
			allDeleteFlag = '1';
		}
		leftBrakets = 0; //����������ʼ��Ϊ0
		rightBrakets = 0; //����������ʼ��Ϊ0
		document.getElementById("conditionStr").value = "";
		document.getElementById("querysql").value = ""; //�洢�Ѿ����ɵ�sql
		arrselect = [];
		radow.doEvent("refreshDis");
	}
	var arrall = []; //�洢�������� ���ڻ�ȡѡ����
	var arrselect = []; //�洢ѡ�е����� ���ڱ��浽��������

	function previewFunc() { //��ѯ
		var allqryusestr = "";
		for(i = 0; i < arrselect.length; i++) {
			allqryusestr = allqryusestr + arrselect[i] + "@";
		}
		var longStr = document.getElementById("for_table_qryuses").value;
		document.getElementById("for_table_qryuses").value = "";
		var forLongStr = longStr.split("@");
		var k = 0;
		for(i = 0; i < forLongStr.length - 1; i++) {
			if(forLongStr[i].length > 5) {
				k = i + 1;
				document.getElementById("for_table_qryuses").value = document.getElementById("for_table_qryuses").value + k + "," + forLongStr[i] + "@";
			}
		}
		document.getElementById("allqryusestr").value = allqryusestr;
		var st = document.getElementById("qrysqlcc").value;
		var tj = document.getElementById("conditionName9").value;
		if(st == "" || st == " " || st == null || st.length == 0) {
			alert("����ѡ���Ѵ���ͼ��");
			return;
		}
		if(tj == "" || tj == " " || tj == null || tj.length == 0) {
			alert("��༭������");
			return;
		}
		radow.doEvent("previewFunc");
	}

	function udfbt5func() {
		var qvid = document.getElementById('qrysqlcc').value;
		var pWindow = window.dialogArguments['window'];
		pWindow.document.getElementById('qvid').value = qvid;
		Ext.Ajax.request({
			method: 'POST',
			async: true,
			params: {
				'qvid': qvid
			},
			timeout: 300000, //���������
			url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&eventNames=query_config",
			success: function(resData) {
				var cfg = Ext.util.JSON.decode(resData.responseText);
				if(0 == cfg.messageCode) {
					window.close();
					pWindow.document.getElementById('sql').value = '';
					pWindow.changeType(1);
					pWindow.Ext.getCmp('peopleInfoGrid').show();
					pWindow.document.getElementById("pictable").style.display = 'none';
					pWindow.document.getElementById("picdata").style.display = 'none';
					pWindow.document.getElementById("btd").style.display = 'block';
					pWindow.resetCM(cfg.elementsScript);
				} else {
					Ext.Msg.alert('ϵͳ��ʾ', cfg.mainMessage)
				}
			},
			failure: function(res, options) {
				Ext.Msg.hide();
				alert("�����쳣��");
			}
		});
	}
	var codecombox = '';

	function setTree(code_type) {
		if(codecombox) {
			codecombox.selectStore = {};
			codecombox.codetype = code_type;
			codecombox.initComponent(); 
			try{
				document.getElementById("conditionName6_treePanel").removeChild(document.getElementById("conditionName6_treePanel").children(0));
			}catch (e) {}
		} else {
			var codeStr = '<input type="hidden" id="conditionName6" name="conditionName6" /><input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style="cursor:default;" required="false"  label="false" />';
			document.getElementById("codediv").innerHTML = codeStr;
			codecombox = new Ext.ux.form.ComboBoxWidthTree({
				selectStore: "",
				property: 'conditionName6',
				id: "conditionName6_combotree",
				label: 'false',
				applyTo: "conditionName6_combotree",
				tpl: '<div style="height:200px;"><div id="conditionName6_treePanel"></div></div>',
				width: 160,
				codetype: code_type,
				codename: 'code_name'

			});
		}
	}
	var codecombox2 = '';

	function setTree2(code_type) {
		if(codecombox2) {
			codecombox2.selectStore = {};
			codecombox2.codetype = code_type;
			codecombox2.initComponent(); 
			try{
				document.getElementById("conditionName71_treePanel").removeChild(document.getElementById("conditionName71_treePanel").children(0));
			}catch (e) {}
		} else {
			var codeStr = '<input type="hidden" id="conditionName71" name="conditionName71" /><input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style="cursor:default;" required="false"  label="false" />';
			document.getElementById("codediv2").innerHTML = codeStr;
			codecombox2 = new Ext.ux.form.ComboBoxWidthTree({
				selectStore: "",
				property: 'conditionName71',
				id: "conditionName71_combotree",
				label: 'false',
				applyTo: "conditionName71_combotree",
				tpl: '<div style="height:200px;"><div id="conditionName71_treePanel"></div></div>',
				width: 160,
				codetype: code_type,
				codename: 'code_name'

			});
		}
	}

	function valuefivefunc() {
		var conditionName5 = document.getElementById("conditionName5").value;
		//alert(conditionName5);
		if(conditionName5.indexOf('between') != -1) { //ѡ����between and����
			var col_data_type = document.getElementById("col_data_type").value;
			var code_type = document.getElementById("code_type").value;
			if(code_type != null && code_type.trim() != '') {
				//����select�ؼ��ɱ༭
				document.getElementById("value2").style.display = 'none'; //date
				document.getElementById("value21").style.display = 'block'; //select
				document.getElementById("value211").style.display = 'none'; //number
				radow.doEvent("setValue21Disable", code_type);
			} else if(col_data_type == 'T' || col_data_type == 't') { //����������date��
				//����date�ؼ��ɱ༭
				radow.doEvent("setValue2Disable");
			} else {
				//����number�ؼ��ɱ༭
				radow.doEvent("setValue211Disable");
			}
		}else if(conditionName5.indexOf('null')!=-1){
			//���ò��ɱ༭
			document.getElementById("value1").style.display='none';//select
			document.getElementById("value11").style.display='none';//date
			document.getElementById("value111").style.display='block';//text
			document.getElementById("value1111").style.display='none';//number
			
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='none';//select
			document.getElementById("value211").style.display='block';//number
			radow.doEvent("setValue1And2Disable");
		}

	}
</script>