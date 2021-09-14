<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/cadremgn/sysmanager/ViewCreate/vc.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/search_condition.js"></script>
<script type="text/javascript">
function checkedFunc(){
	var qvid=document.getElementById("qvid").value;
	radow.doEvent("initPage");
}
var nvl = '<%=DBUtil.getDBType()==DBUtil.DBType.ORACLE?"nvl":"IFNULL"%>';
//alert(nvl)
function initPageInfo(jsonstr){
	
	if(jsonstr==null||jsonstr.trim()==''||jsonstr.trim()=='null'){
		return;
	}
	jsonInfo = eval('(' + jsonstr + ')');
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
		
		var tar='';
		var code_type=obj['code_type'];
		var conditionName5=obj['sign'];
		if(code_type=='S'){
			if(conditionName5.indexOf('between')!=-1){
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuename1']+' '+obj['valuename2'];
			}else if(conditionName5.indexOf('null')!=-1){
				tar=obj['fldname']+' '+obj['signname'];
			}else{
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuename1'];
			}
		}else if(code_type=='N'){
			if(conditionName5.indexOf('between')!=-1){
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1']+' '+obj['valuecode2'];
			}else if(conditionName5.indexOf('null')!=-1){
				tar=obj['fldname']+' '+obj['signname'];
			}else{
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
			}
		}else if(code_type=='T'){
			if(conditionName5.indexOf('between')!=-1){
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1']+' '+obj['valuecode2'];
			}else if(conditionName5.indexOf('null')!=-1){
				tar=obj['fldname']+' '+obj['signname'];
			}else{
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
			}
		}else if(code_type=='C'){
			if(conditionName5.indexOf('null')!=-1){
				tar=obj['fldname']+' '+obj['signname'];
			}else{
				tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
			}
		}
		txtareaarr.push(tar)
		
	}
	
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
			var conditionStr='';
			var code_type=obj['code_type'];
			var conditionName5=obj['sign'];
			var table_code=obj['tblname'];
			var col_code=obj['fldcode'];
			var valuecode1=obj['valuecode1'];
			var valuecode2=obj['valuecode2'];
			if(code_type=='S'){
				if(conditionName5.indexOf('between')!=-1){
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+valuecode1+"' and '"+valuecode2+"'";
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+valuecode2+"' and '"+valuecode1+"' )";
				}else if(conditionName5.indexOf('like')!=-1){
					if(conditionName5.indexOf('%v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+valuecode1+"%'"+" ";
					}else if(conditionName5.indexOf('%v')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+valuecode1+"'"+" ";
					}else if(conditionName5.indexOf('v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+valuecode1+"%'"+" ";
					}
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					conditionStr=" "+nvl+"("+table_code+"."+col_code+",'') "+conditionName5+" "+"'"+valuecode1+"'"+" ";
				}
			}else if(code_type=='N'){
				if(conditionName5.indexOf('between'!=-1)){//��between
					tempright=obj['valuecode1'];
					templeft=obj['valuecode2'];
					conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+")";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					tempright=obj['valuecode1'];
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+tempright+" ";
				}
			}else if(code_type=='T'){
				if(conditionName5.indexOf('between')!=-1){//��between
					tempright="to_date('"+obj['valuecode1']+"','yyyy-mm-dd')";
					templeft="to_date('"+obj['valuecode2']+"','yyyy-mm-dd')";
					conditionStr=" ( to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+templeft+" and "+tempright+")";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					tempright="to_date('"+obj['valuecode1']+"','yyyy-mm-dd')";
					conditionStr=" to_date("+table_code+"."+col_code+",'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
				}
			}else if(code_type=='C'){
				if(conditionName5.indexOf('like')!=-1){
					if(conditionName5.indexOf('%v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+obj['valuecode1']+"%'";
					}else if(conditionName5.indexOf('%v')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+obj['valuecode']+"'";
					}else if(conditionName5.indexOf('v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+obj['valuecode1']+"%'";
					}
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+obj['valuecode1']+"'"+" ";
				}
			}
			txtareaarrCode.push(conditionStr);
		
	}
	document.getElementById("conditionStr").value=createWhereCon();// ����ƴ�ӵ�where���� 
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
			var str=obj['fldname']+","+obj['fldcode']+","+obj['tblname']
			+","+obj['valuename1']+","+obj['valuecode1']+","+obj['code_type']
			+","+obj['valuecode2']+","+obj['valuename2']+","+obj['sign'];
			arrall.push(str);
			arrselect.push(str);
		
	}
	//alert(conditionStr)
	radow.doEvent("initListAndButton");
}
function createWhereCon(){
	var ss='';
	var zhtj=document.getElementById("zhtj").value;
	zhtj=zhtj.replace(/\./g,'');
	zhtj=zhtj.replace(/����/g,' or ');
	zhtj=zhtj.replace(/����/g,' and ');
	zhtj=zhtj.replace(/��/g,' ! ');
	var arr=zhtj.split(/[0-9]{1,}/);
	for(var i=0;txtareaarrCode!=null&&i<txtareaarrCode.length;i++){
		ss=ss+arr[i]+txtareaarrCode[i]
		.replace(/{v}/g,' ')
		.replace(/{%v}/g,' ')
		.replace(/{v%}/g,' ')
		.replace(/{%v%}/g,' ');	
	}
	if(arr!=null&&txtareaarrCode!=null){
		if(arr.length>txtareaarrCode.length){
			ss=ss+arr[txtareaarrCode.length];
			return ss;
		}else{
			return ss;
		}
	}
	return ss;
}
function setAddCon(txtareaarr){
	var arrstr="";
	for(i=0;txtareaarr!=null&&i<txtareaarr.length;i++){
		var temp='';
		//if(txtareaarr[i].indexOf('$')!=-1){
		//	 temp=txtareaarr[i].replace(/\$/g,"\'");
		//}else{
			 temp=txtareaarr[i];
		//}
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;float:left; ' onclick='backgroundFunc(this);'>"+(i+1)+"."+temp+"</span><br/>";
	}
	return arrstr;
}
function initGridChecked(tableInfos,flds){
	if(tableInfos==null||tableInfos.trim()==''||tableInfos.trim()=='null'){
		return;
	}
	document.getElementById('flds').value=flds;
	var gridid='tableList2Grid';
	var grid=Ext.getCmp(gridid);
	var orderStore = grid.getStore();
    var rowCount = orderStore.getCount();
    var arr=tableInfos.split(',');
    //alert(arr);//A01
	for(var j=0;arr!=null&&j<arr.length;j++){
		for(var i=0;i<rowCount;i++) {
	        if(orderStore.getAt(i).get("tblcod").toLowerCase() == arr[j].toLowerCase()) {
	        	var record=orderStore.getAt(i);
				//record.set('checked',false);
				record.set('checked',true);
				//orderStore.removeAt(i);  
				//orderStore.insert(i, record); 
	        }
	    } 
	}
    radow.doEvent('tabletofld');
}
function fldListCheck(){
	var flds=document.getElementById('flds').value;
	if(flds==null||flds.trim()==''||flds.trim()=='null'){
		return;
	}
	var gridid='codeList2Grid';
	var grid=Ext.getCmp(gridid);
	var orderStore = grid.getStore();
    var rowCount = orderStore.getCount();
    var arr=flds.split('@');
	for(var j=0;arr!=null&&j<arr.length;j++){
		var fldr=arr[j].split(',');
		var tmp=fldr[0]+fldr[1];
		tmp=tmp.toLowerCase();
		for(var i=0;i<rowCount;i++) {
			var table=orderStore.getAt(i).get("table_code").toLowerCase();
			var fld=orderStore.getAt(i).get("col_code").toLowerCase();
	        if( (table+fld)== tmp) {
	        	var record=orderStore.getAt(i);
				record.set('checked',false);
				//orderStore.removeAt(i);
				//orderStore.insert(i, record);
	        }
	    }
	}
}
</script>
<div>
<odin:hidden property="flds"/><!-- �����ѡ����Ϣ�� -->
<div id="infojihe" class="area4">
	<table id="tabcon1" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="��Ϣ��" >
				<odin:editgrid property="tableList2Grid" forceNoScroll="true" width="320" height="495" url="/" load="checkedFunc">
					<odin:gridJsonDataModel   root="data" >
						<odin:gridDataCol name="checked"  />
						<odin:gridDataCol name="tblcod"  />
						<odin:gridDataCol name="tblcpt"  isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn><!-- checkBoxClick="checkClicktable" -->
						<odin:gridColumn header="selectall" gridName="tableList2Grid" checkBoxClick="checkClicktable" checkBoxSelectAllClick="getCheckAll" align="center"  editor="checkbox" edited="true" dataIndex="checked" />
						<odin:gridEditColumn header="��Ϣ����" width="105" dataIndex="tblcod" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="��Ϣ����" width="215" dataIndex="tblcpt" edited="false" sortable="false" menuDisabled="true"  editor="text" align="left" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>
<!-- �����Աȱ���460/->205����485/->218��������Ҫ����Ҫ��codeList2Grid1���ص� �� -->
<div  id="fldzbx" class="area4">
	<table id="tabcon2" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="ָ����(��ʾ:�빴ѡָ�����ΪԤ����)" >
				<odin:editgrid property="codeList2Grid" forceNoScroll="true" width="320" height="235" url="/" rowDbClick="rowFldeDbClick" load="fldListCheck">
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
						<odin:gridColumn header="selectall" width="70" gridName="codeList2Grid" checkBoxClick="checkClickCode" align="center"  editor="checkbox" edited="true" dataIndex="checked"  isLast="true" checkBoxSelectAllClick="checkAll"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
		<tr>
			<td style="width:320;">
				<odin:groupBox title="ָ��������(��ʾ:������ʵ���Ƴ�������)" >
				<odin:editgrid property="codeList2Grid1" forceNoScroll="true" width="320" height="235" url="/" rowDbClick="rowFldeDbClick" load="fldListCheck">
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
<div id="conditiondiv" class="area4">
	<table id="tabcon3" style="width:100%;height:100%;">
		<tr>
			<td >
				<odin:groupBox property="confirmconditionproperty" title="ȷ������(��ʾ:��˫��ָ������ȷ������)">
				<table>
					<tr>
						<odin:textEdit property="conditionName4" label="����ָ����"  width="160" readonly="true"></odin:textEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="&nbsp&nbsp�� &nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp"  property="btnm1" handler="conditionclear" ></odin:button></td>
					</tr>
					<tr>
						<odin:select property="conditionName5" label="��������"  width="144" onchange="valuefivefunc"></odin:select>
					</tr>
					<tr id="value1" style="display: none;">
						<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵһ</td>
						<td id='codediv'>
							<input type="hidden" id="conditionName6" name="conditionName6" />
							<input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style='cursor:default;' required="false"  label="false" />
						</td>
						<%-- <tags:ComBoxWithTree property="conditionName6"  label="ֵһ" width="145" codetype="ZB01"/> --%> 
						<%-- <odin:select property="conditionName6" label="ֵһ"  width="144" ></odin:select> --%>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="������б�" property="btnm2" handler="addtolistfunc" ></odin:button></td>
					</tr>
					<%--  <tr>
						<tags:ComBoxWithTree property="valuetree"  label="ֵһ" width="145" codetype="ZB01"/>
						<td  rowspan="2"></td>
					</tr>  --%>
					<tr id="value11" style="display: none;">
						<odin:dateEdit property="conditionName61" label="ֵһ"  width="145" ></odin:dateEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="������б�" property="btnm21" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value111" style="display: block;">
					<odin:textEdit property="conditionName611" label="ֵһ"  width="160" ></odin:textEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="������б�" property="btnm211" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value1111" style="display: none;">
						<odin:numberEdit property="conditionName6111" label="ֵһ"  width="160" ></odin:numberEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="������б�" property="btnm211" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value2" style="display: none;">
						<odin:dateEdit property="conditionName7" label="ֵ��"  width="145" ></odin:dateEdit>
					</tr>
					<tr id="value21" style="display: none;">
						<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">ֵ��</td>
						<td id='codediv2' >
							<input type="hidden" id="conditionName71" name="conditionName71" />
							<input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style='cursor:default;' required="false"  label="false" />
						</td>
						<%-- <odin:select property="conditionName71" label="ֵ��"  width="144" ></odin:select> --%>
					</tr>
					<tr id="value211" style="display: block;">
						<odin:numberEdit property="conditionName711" label="ֵ��"  width="160" ></odin:numberEdit>
					</tr>
					
				</table>
				<table style="width:320">
					<tr>
						<td>
							<div style="width:240;height:160;" id="conditionName8" class="div_area">
							
							</div>
						</td>
						<%-- <odin:textarea property="conditionName8" colspan="5" rows="11" ></odin:textarea> --%>
						<td style="width:50;">
							<table>
							<tr>
								<td id="editcondition" style="text-align:right;width:48;height:30">
								<odin:button text="&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��&nbsp&nbsp"  property="editbtnm" handler="editbtnFunc">
								</odin:button></td>
							</tr>
							<tr>
								<td id="iddeletecondition" style="text-align:right;width:50;height:30"><odin:button text="&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��&nbsp"  property="btnm3" handler="btnm3Func" ></odin:button></td>
							</tr>
							<tr>
								<td id="iddeleteallcondition" style="text-align:right;width:50;height:30"><odin:button text="ȫ��ɾ��"  property="btnm4" handler="btnm4Func" ></odin:button></td>
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
				<odin:groupBox property="mergeconditionproperty" title="�������"  >
				<table style="width:320">
				<tr>
					<td style="width:80">
						<odin:button text="ѡ������" property="btnn1" handler="btnnFunc"></odin:button>
					</td>
					<td><odin:button text="��" property="btnn2" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="��" property="btnn3" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="��" property="btnn4" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="����" property="btnn5" handler="btnnFunc"></odin:button></td>
					<td ><odin:button text="����" property="btnn6" handler="btnnFunc" ></odin:button></td>
					<td style="text-align:right;">
						<div style="margin-right: 10"><odin:button text="��������" property="btnn7" handler="btnn7Funcc" ></odin:button></div>
					</td>
				</tr>
				<tr style="width:540;">
						<odin:textarea property="conditionName9" colspan="8" rows="4" readonly="true" ></odin:textarea>
				</tr>
				<tr>
					<td colspan="7" style="font-size:14;color:red;" >�뽫����ѡ�롰������������У�������Ч</td>
				</tr>
				</table>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>
<div id="divsave4" style="width:1000px;text-align:right;display:block;" >
	<table id="tabcon4" border="0">
		<tr>
			<td style="text-align:right;width:855px;" >
				<odin:button text="����" property="btn4" handler="saveFunc"></odin:button>
			</td>
			<td width="100px">
			</td>
			<td style="width:30;text-align:left;width:145px;">
				<odin:button text="Ԥ��" property="btn5" handler="previewFunc"></odin:button>
			</td>
		</tr>
		<tr height="9px">
			<td colspan="3"></td>
		</tr>
	</table>
</div>
<odin:hidden property="col_data_type"/><!-- ���������� -->
<odin:hidden property="conditoionlist"/><!-- ���������� ���ɵ�ǰ��ƴ�ӵ�����  -->
<odin:hidden property="table_code"/><!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="col_code"/><!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="col_name"/><!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="col_name1"/><!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="code_type"/><!-- ��ǰѡ�е���Ϣ�� -->
<odin:hidden property="col_data_type_should"/><!-- ��ǰѡ�е���Ϣ�� --><!-- �ֶ����ͱ�ʾ -->
<odin:hidden property="qvid"/><!-- ��ͼ���� -->
<odin:hidden property="conditionStr"/><!-- �������ת�� �ɵ� where���� -->
<odin:hidden property="querysql"/><!-- �洢�Ѿ����ɵ�sql -->
<odin:hidden property="parenttablename"/><!-- �洢��ҳ����Ϣ��(��Ա��Ϣ�����޸�) -->
<odin:hidden property="paramurl" value='<%=request.getParameter("paramurl") %>'/><!-- ��Ա��Ϣ���Զ����ѯ -->
<odin:hidden property="allqryusestr"/>
<odin:hidden property="qryusestr"/>
<odin:hidden property="zhtj"/>
</div>
<script type="text/javascript">
function runorstop(value, params, record,rowIndex,colIndex,ds){
	var contextPath = '<%=request.getContextPath()%>';
	return "<img  src='"+contextPath+"/images/wrong.gif' title='ɾ��' style='cursor:pointer' onclick=delThisOne(this,'"+value+"','"+rowIndex+"')><a>&nbsp;&nbsp;</a><img  src='"+contextPath+"/image/up.png' title='����' style='cursor:pointer' onclick=upThisOne(this,'"+value+"','"+rowIndex+"')><img src='"+contextPath+"/image/down.png' title='����' style='cursor:pointer' onclick=downThisOne(this,'"+value+"','"+rowIndex+"')>";
}
function getQuerysql(){
	return document.getElementById("querysql").value;
}
function valuefivefunc(){
	var conditionName5=document.getElementById("conditionName5").value;
	var col_data_type=document.getElementById("col_data_type").value;
	var code_type=document.getElementById("code_type").value;
	var col_data_type_should=document.getElementById("col_data_type_should").value;
	setDisOrShow(code_type,col_data_type_should,col_data_type);
	if(conditionName5.indexOf('between')!=-1){//ѡ����between and����
		if(code_type!=null&&code_type.trim()!=''){
			//����select�ؼ��ɱ༭
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='block';//select
			document.getElementById("value211").style.display='none';//number
			radow.doEvent("setValue21Disable",code_type);
		}else if(col_data_type=='T'||col_data_type=='t'){//����������date��
			//����date�ؼ��ɱ༭
			radow.doEvent("setValue2Disable");
		}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
				||col_data_type_should=="null"||col_data_type_should==null
				||col_data_type_should.length==""||col_data_type_should.length==" "){
			radow.doEvent("setValue111Disable");
		}else{
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
function loadFldList(tablename){
	document.getElementById("parenttablename").value=tablename;
	radow.doEvent("tabletofld");
}
function setQvId(id){
	document.getElementById("qvid").value=id;
}
function setDisplayTalbe(str){//������Ϣ������
	document.getElementById("infojihe").style.display=str;
	document.getElementById("infojihe").style.width="0";
}
function setDisplayCode(str){//����ָ��������
	document.getElementById("fldzbx").style.display=str;
	document.getElementById("fldzbx").style.width="0";
}
function initList(){
	radow.doEvent("loadtable");
}
function setDisalbe(){
	radow.doEvent("setDisalbe");
}
/* zxw�޸���ʽ */
function checkClicktable(num,un,dataIndex,gridid){//ѡ����Ϣ������ѯ��ʾ����Ϣ��
	//var grid = Ext.getCmp(gridid);
	cleanInfo();
	radow.doEvent("modifyTable");
}
//ʵ��ˢ���б�������λ�ò��䣬��������
Ext.override(Ext.grid.GridView, {  
	    scrollTop : function() {  
	        this.scroller.dom.scrollTop = 0;  
	        this.scroller.dom.scrollLeft = 0;  
	    },  
	    scrollToTop : Ext.emptyFn  
	}); 

function checkClickCode(rowIndex,un,dataIndex,gridid){//ѡ����Ϣ�ʵ�ָ���Ϣ������Ԥ����Ϣ��
	radow.doEvent("checkClickCode",rowIndex);
}
//ȫѡ
function checkAll(grid_id,col_id){
	radow.doEvent("checkClickCodeAll");
}
function delThisOne(obj,value,rowIndex){
	radow.doEvent("delThisOne",rowIndex);
}
function upThisOne(obj,value,rowIndex){
	var grid_id4='codeList2Grid1';
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
	//radow.doEvent("upThisOne",rowIndex);
}
function downThisOne(obj,value,rowIndex){
	var grid_id4='codeList2Grid1';
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
	//radow.doEvent("downThisOne",rowIndex);
}
var codecombox='';
function setTree(code_type){
		if(codecombox){
			codecombox.selectStore={};
			codecombox.codetype=code_type;
			codecombox.initComponent();//
			if(document.getElementById("conditionName6_treePanel")){
				if(document.getElementById("conditionName6_treePanel").children){
					try{
						document.getElementById("conditionName6_treePanel").removeChild(document.getElementById("conditionName6_treePanel").children(0));
					}catch(err){
						
					}
				}
			}
		}else{
			var codeStr = '<input type="hidden" id="conditionName6" name="conditionName6" /><input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style="cursor:default;" required="false"  label="false" />';
			document.getElementById("codediv").innerHTML=codeStr; 
			codecombox = new Ext.ux.form.ComboBoxWidthTree({
			 	 selectStore:"",
				 property: 'conditionName6',
				 id:"conditionName6_combotree",
				 label : 'false',
				 applyTo:"conditionName6_combotree",
				 tpl: '<div style="height:200px;"><div id="conditionName6_treePanel"></div></div>',
				 width:160,
				 codetype:code_type,
				 codename:'code_name'
				 
			 });
		}
	
}
var codecombox2='';
function setTree2(code_type){
	if(codecombox2){
		codecombox2.selectStore={};
		codecombox2.codetype=code_type;
		codecombox2.initComponent();//
		if(document.getElementById("conditionName6_treePanel")){
			if(document.getElementById("conditionName6_treePanel").children){
				try{
					document.getElementById("conditionName71_treePanel").removeChild(document.getElementById("conditionName71_treePanel").children(0));
				}catch(err){
					
				}
			}
		}
	}else{
		var codeStr = '<input type="hidden" id="conditionName71" name="conditionName71" /><input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style="cursor:default;" required="false"  label="false" />';
		document.getElementById("codediv2").innerHTML=codeStr; 
		codecombox2 = new Ext.ux.form.ComboBoxWidthTree({
		 	 selectStore:"",
			 property: 'conditionName71',
			 id:"conditionName71_combotree",
			 label : 'false',
			 applyTo:"conditionName71_combotree",
			 tpl: '<div style="height:200px;"><div id="conditionName71_treePanel"></div></div>',
			 width:160,
			 codetype:code_type,
			 codename:'code_name'
			 
		 });
	}
}
function rowFldeDbClick(grid,rowIndex,colIndex,event){//˫����Ϣ��
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	var col_name1=record.get("col_name1");//ָ������
	var col_name=record.get("col_name");//ָ������
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//����������
	
	document.getElementById("col_data_type").value=col_data_type;
	document.getElementById("table_code").value=table_code;
	document.getElementById("col_code").value=col_code;
	document.getElementById("col_name").value=col_name;
	document.getElementById("col_name1").value=col_name1;
	var code_type=record.get("code_type");//ָ���������
	document.getElementById("code_type").value=code_type;
	var col_data_type_should=record.get("col_data_type_should");//ָ���������2
	document.getElementById("col_data_type_should").value=col_data_type_should.toLowerCase();
	
	setDisOrShow(code_type,col_data_type_should,col_data_type);
	setValueToLable(code_type,col_data_type_should,col_data_type);
}
function setDisOrShow(code_type,col_data_type_should,col_data_type){
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	if(code_type!=null
			&&code_type!=' '
			&&code_type!=''&&code_type!='null'){//����ѡ ��between and  ��like  
		
		//��ѯ����
		document.getElementById("value1111").style.display='none';//number
		document.getElementById("value111").style.display='none';//text
		document.getElementById("value11").style.display='none';//date
		document.getElementById("value1").style.display='block';//select
		document.getElementById("value2").style.display='none';//date
		document.getElementById("value21").style.display='none';//text
		document.getElementById("value211").style.display='block';//number
		//setTree(code_type);
		//radow.doEvent("code_type_value1",code_type);
		//raodw.doEvent("selectValue1List",code_type);
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//�������ֶ�   ��between and  ��like 
		//��ѯ����
		document.getElementById("value1").style.display='none';
		document.getElementById("value111").style.display='none';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value11").style.display='block';
		document.getElementById("value2").style.display='block';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='none';
		//radow.doEvent("selectValueList");
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//�ı� ��like ��between and
		//��ѯ����
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='block';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
		//radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//���� ��like  ��between and
		//��ѯ����
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='none';
		document.getElementById("value1111").style.display='block';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
		//radow.doEvent("selectValueListLikeBt");
	}
}
function setValueToLable(code_type,col_data_type_should,col_data_type){
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	if(code_type!=null
			&&code_type!=' '
			&&code_type!=''&&code_type!='null'){//����ѡ ��between and  ��like  
		//��ѯ����
		radow.doEvent("code_type_value1",code_type);
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//�������ֶ�   ��between and  ��like 
		//��ѯ����
		radow.doEvent("selectValueList");
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//�ı� ��like ��between and
		//��ѯ����
		radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//���� ��like  ��between and
		//��ѯ����
		radow.doEvent("selectValueListLikeBt");
	}
}
function setconditionName4(){
	////������ָ���ֵ
	document.getElementById("conditionName4").value=document.getElementById("col_name1").value;
}
function conditionclear(){//�������
	radow.doEvent("conditionclear");
}
function addtolistfunc(){//��ӵ��б�
	var col_data_type_should=document.getElementById("col_data_type_should").value;
	var col_data_type=document.getElementById("col_data_type").value;
	var code_type=document.getElementById("code_type").value;
	var condition=document.getElementById("conditionName5").value;
	if(condition==null||condition==''){
		alert('��ѡ����������!');
		return;
	}
	if(condition.indexOf('null')==-1){
		if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
			var conditionName6=document.getElementById("conditionName6").value;
			var conditionName71=document.getElementById("conditionName71").value;
			if(conditionName6==null||conditionName6==''){
				alert('��ѡ��ֵһ!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName71==null||conditionName71==''){
					alert('��ѡ��ֵ��!');
					return;
				}
			}
		} else if("date"==col_data_type_should.toLowerCase()||col_data_type=='t'||col_data_type=='T'){
			var conditionName61=document.getElementById("conditionName61").value;
			var conditionName7=document.getElementById("conditionName7").value;
			if(conditionName61==null||conditionName61==''){
				alert('��ѡ��ֵһ!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName7==null||conditionName7==''){
					alert('��ѡ��ֵ��!');
					return;
				}
			}
		} else if("number"==col_data_type_should||"NUMBER"==col_data_type_should){
			var conditionName6111=document.getElementById("conditionName6111").value;
			var conditionName711=document.getElementById("conditionName711").value;
			if(conditionName6111==null||conditionName6111==''){
				alert('������ֵһ!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName711==null||conditionName711==''){
					alert('������ֵ��!');
					return;
				}
			}
		}else{
			var conditionName611=document.getElementById("conditionName611").value;
			if(conditionName611==null||conditionName611==''){
				alert('������ֵһ!');
				return;
			}
		}
	}
	radow.doEvent("addtolistfunc");
}
var txtareaarr=[];//�洢����������� ��ʾ����
var txtareaarrCode=[];//�洢����������� ƴ��sql����
var arrall=[];//�洢�������� ���ڻ�ȡѡ����
var arrselect=[];//�洢ѡ�е����� ���ڱ��浽��������
function textareadd(){//��ӵ��б�
	var conditionName5=document.getElementById("conditionName5").value;//��ȡ��������
	if(conditionName5==null||conditionName5=="null"||conditionName5.trim()==''){
		alert("��ѡ������!");
		return;
	}
	var conditoionlist=document.getElementById("conditoionlist").value;//��ȡƴ�ӵ��������
	var qryusestr=document.getElementById("qryusestr").value;
	txtareaarr.push(conditoionlist);//���ƴ��������������
	arrall.push(qryusestr);
	var arrstr="";
	for(i=0;i<txtareaarr.length;i++){//ѭ���������
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;float:left;z-index:999999999;' onclick='backgroundFunc(this);'>"+(i+1)+"."+txtareaarr[i]+"</span><br/>";
	}
	document.getElementById("conditionName8").innerHTML=arrstr;//��ʾ�������
	
	var conditionStr="";
	var table_code=document.getElementById("table_code").value;//��ȡ��Ϣ��
	var col_code=document.getElementById("col_code").value;//��ȡ��Ϣ��
	
	var col_data_type_should=document.getElementById("col_data_type_should").value;//��ȡ�洢���ֶ�����
	var code_type=document.getElementById("code_type").value;//��ȡ��������
	if(code_type!=null){
		code_type=code_type.toLowerCase().trim();
	}
	var col_data_type=document.getElementById("col_data_type").value;//��ȡ��ʾ�Ŀؼ�����
	if(col_data_type!=null){
		col_data_type.toLowerCase().trim();
	}
	var tempright="";
	var templeft="";
	var temprightlike="";
	//conditionName6 ֵһ
	//conditionName7 ֵ��
	//col_data_type_should ���� �ֶδ洢������ ʵ������
	//col_data_type ǰ̨�ؼ�����
/* 	if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//����ѡ ��between and  ��like  
		if(conditionName5.indexOf('between')!=-1){
			conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName6").value+"' and '"+document.getElementById("conditionName71").value+"'";
			conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName71").value+"' and '"+document.getElementById("conditionName6").value+"' )";
		}else if(conditionName5.indexOf('like')!=-1){
			if(conditionName5.indexOf('%v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName6").value+"%'"+" ";
			}else if(conditionName5.indexOf('%v')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName6").value+"'"+" ";
			}else if(conditionName5.indexOf('v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+document.getElementById("conditionName6").value+"%'"+" ";
			}
		}else{
			conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+""+document.getElementById("conditionName6").value+""+" ";
		}
	}else if("date"==col_data_type_should||col_data_type=='t'){//�������ֶ�   ��between and  ��like 
			if(col_data_type_should=='date'){
				if(conditionName5.indexOf('between'!=-1)){//��between
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+") ";
				}else{
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					conditionStr=" "+table_code+"."+col_code+" "+" "+conditionName5+" "+tempright+" ";
				}
				
			}else if(col_data_type_should!='date'&&col_data_type=='t'){
				if(conditionName5.indexOf('between')!=-1){//��between
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
					conditionStr=" ( to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+templeft+" and "+tempright+")";
				}else{
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					conditionStr=" to_date("+table_code+"."+col_code+",'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
				}
			}
	}else if("varchar2"==col_data_type_should
			||col_data_type_should=='clob'
			||col_data_type_should==""
			||col_data_type_should==" "
			||col_data_type_should=="null"){//�ı� ��like ��between and
		if(conditionName5.indexOf('like')==-1){
			conditionStr=" nvl("+table_code+"."+col_code+" ,'')"+conditionName5+" "+"'"+document.getElementById("conditionName611").value+"'"+" ";
		}else{
			if(conditionName5.indexOf('%v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName611").value+"%'";
			}else if(conditionName5.indexOf('%v')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName611").value+"'";
			}else if(conditionName5.indexOf('v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+document.getElementById("conditionName611").value+"%'";
			}
		}
		
	}else if("number"==col_data_type_should){//���� ��like  ��between and
		if(conditionName5.indexOf('between'!=-1)){//��between
			tempright=document.getElementById("conditionName6111").value;
			templeft=document.getElementById("conditionName711").value;
			conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
			conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+")";
		}else{
			tempright=document.getElementById("conditionName611").value;
			conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+tempright+" ";
		}
	} */
		
		//var code_type = document.getElementById("code_type").value;
		//var col_data_type = document.getElementById("col_data_type").value;
		//var col_data_type_should = document.getElementById("col_data_type_should").value;
		//var conditionName5 = document.getElementById("conditionName5").value;
		var conditionName6 = document.getElementById("conditionName6").value;
		var conditionName61 = document.getElementById("conditionName61").value;
		var conditionName611 = document.getElementById("conditionName611").value;
		var conditionName6111 = document.getElementById("conditionName6111").value;
		var conditionName7 = document.getElementById("conditionName7").value;
		var conditionName71 = document.getElementById("conditionName71").value;
		var conditionName711 = document.getElementById("conditionName711").value;
		conditionStr = conditionToWhereSql(table_code,col_code,code_type,col_data_type,col_data_type_should,conditionName5,conditionName6,conditionName61,conditionName611,conditionName6111,conditionName7,conditionName71,conditionName711);
		//alert(conditionStr);
		txtareaarrCode.push(conditionStr);
}
var areastr="";//��ǰѡ�е�����id
function backgroundFunc(obj){//���������¼�
	var spanarr=document.getElementById("conditionName8").children;//��ȡ���е��������
	for(i=0;i<spanarr.length;i++){//ѭ���������
		spanarr[i].style.background="";//������е������������
	}
	areastr=obj.id;//��ȡѡ�е���������
	obj.style.background="pink";//����ѡ�е���������
}
function btnm3Func(){//�Ƴ�
	if(areastr==""||areastr.length==0){
		alert("��ѡ��Ҫɾ��������!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//��ȡ������λ
	txtareaarr.splice(num, 1);//�Ƴ�ѡ�е��������
	txtareaarrCode.splice(num, 1);//�Ƴ�ѡ�е��������������
	arrall.splice(num, 1);
	var arrstr="";
	//���������������
	for(i=0;i<txtareaarr.length;i++){
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;z-index:999999999;float:left; ' onclick='backgroundFunc(this);'>"+(i+1)+"."+txtareaarr[i]+"</span><br/>";
	}
	document.getElementById("conditionName8").innerHTML=arrstr;//��ʾ�������
	areastr="";//����������λΪ��
}
function btnm4Func(){//ȫ��ɾ��
	txtareaarr=[];//��մ洢��������ʾ���������
	txtareaarrCode=[];//�������ƴ��sql ��where����
	arrall=[];//������ڱ��浽���е��������
	document.getElementById("conditionName8").innerHTML="";//����������
}
//ȫ��ɾ���Ƿ�ɱ༭��־
var allDeleteFlag="1";//1 �� 0 ��
var leftBrakets="0";//����������ʼ��Ϊ0
var rightBrakets="0";//����������ʼ��Ϊ0
var mapBtn={
		'btnn2':'(',
		'btnn3':')',
		'btnn4':'.��.',
		'btnn5':'.����.',
		'btnn6':'.����.'
}
var mapBtnCode={
		'btnn2':'(',
		'btnn3':')',
		'btnn4':' not ',
		'btnn5':' and ',
		'btnn6':' or '
}
function btnnFunc(obj){//ѡ������
	if(areastr==""&&obj.id=='btnn1'){//
		alert("��ѡ������!");
		return;
	}
	var str=document.getElementById("conditionName9").value;//��ȡ������� 
	if("btnn1"==obj.id){
		var num=parseInt(areastr.substr(areastr.length-1,areastr.length))+1;//��λѡ�������
		document.getElementById("conditionName9").value=str+" "+num;//ƴ��������� 
		
		var tempCondition="";
		for(i=0;i<txtareaarrCode.length;i++){
			if(i==(num-1)){
				tempCondition=txtareaarrCode[i];//����where ���� 
			}
		}
		//����where ����
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+tempCondition;
		//����ѡ�е������������� arrselect
		var tempQryUse="";
		for(i=0;i<arrall.length;i++){
			if(i==(num-1)){
				tempQryUse=arrall[i];
			}
		}
		arrselect.push(tempQryUse);
		//�����������
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+' '+arrselect.length;
	}else{
		//map[a0221]
		document.getElementById("conditionName9").value=str+mapBtn[obj.id];//ƴ���������
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+mapBtn[obj.id];
		//����where ����
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+mapBtnCode[obj.id];
	}
	
	if(allDeleteFlag=='1'){//��������ȫ��ɾ�� ��־ 0
		allDeleteFlag='0';
	}
	if("btnn2"==obj.id){//��������
		leftBrakets=parseInt(leftBrakets)+1;
	}
	if("btnn3"==obj.id){//��������
		rightBrakets=parseInt(rightBrakets)+1;
	}
	//alert(111)
	radow.doEvent("setDisSelect",obj.id+","+leftBrakets+","+rightBrakets);//����sql 
}
function btnn7Funcc(){//��������
	document.getElementById("conditionName9").value="";//����������
	if(allDeleteFlag=='0'){
		allDeleteFlag='1';//����ȫ��ɾ�� ��־1 ��ɾ��
	}
	leftBrakets=0;//����������ʼ��Ϊ0
	rightBrakets=0;//����������ʼ��Ϊ0
	document.getElementById("conditionStr").value="";//���where����
	document.getElementById("querysql").value="";//��մ洢�Ѿ����ɵ�sql
	document.getElementById("zhtj").value="";
	arrselect=[];
	radow.doEvent("refreshDis");//��ʼ�����������ť
}

function saveFunc(p){//����
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"@";
	}
	document.getElementById("allqryusestr").value=allqryusestr;
	radow.doEvent("saveFunc",p);
}
function previewFunc(){//Ԥ��
	parent.refreshPreviewTab();
}
function getSqlParent(parenthiddenid,functionname){//parenthiddenid��ҳ�����ص�id functionname��ҳ��ķ�������
	radow.doEvent("getSqlParent",parenthiddenid+","+functionname);
}

//������ͼ�����������������������tabҳ���ȫ��js����(��ʼ��ȫ�ֱ�����ҳ��Ԫ��)
function clearConditionPar(){
	cleanInfo();
	radow.doEvent("clearValue");//��� ��ʾ�Ŀؼ�
}
function cleanInfo(){
	leftBrakets=0;//����������ʼ��Ϊ0
	rightBrakets=0;//����������ʼ��Ϊ0
	//ȫ��ɾ���Ƿ�ɱ༭��־
	allDeleteFlag="1";//1 �� 0 ��
	areastr="";//��ǰѡ�е�����id
	txtareaarr=[];//�洢�������� ��ʾ����
	txtareaarrCode=[];//�洢�������� ƴ��sql����
	arrselect=[];
	arrall=[];
	document.getElementById("conditoionlist").value="";//������ص� ��ǰƴ�ӵ����� 
	document.getElementById("table_code").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_code").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_name").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_name1").value="";//������ص�������ص�  ��ǰѡ�е���Ϣ�� -
	document.getElementById("code_type").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_data_type_should").value="";//������ص� ��ǰѡ�е���Ϣ��  �ֶ����ͱ�ʾ
	document.getElementById("conditionStr").value="";//������ص� �������ת�� �ɵ� where����
	document.getElementById("querysql").value="";//������ص� �洢�Ѿ����ɵ�sql
	document.getElementById("conditionName8").innerHTML="";//����������
	document.getElementById("zhtj").value="";//���ص�����ֶ�
	document.getElementById("flds").value="";//��ʼ����ѡ�� -- ָ����
}
function getCheckAll(){
	cleanInfo();
	radow.doEvent("modifyTable");
}

function hiddenSelectBox(){
	 
//	 document.getElementById('ftpUpManagePanel').style.width = docum
	
}

function saveschemeall(qvid){//�Զ����ѯ ҳ�����
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"@";
	}
	document.getElementById("allqryusestr").value=allqryusestr;
	document.getElementById("qvid").value=qvid;
	radow.doEvent("saveFunc");
}
function editbtnFunc(obj){//�༭
	if(areastr==""||areastr.length==0){
		alert("��ѡ��Ҫ�༭������!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//��ȡ������λ
	var typestr=txtareaarr[parseInt(num)];
	var str=arrall[parseInt(num)];
	var arr=str.split(',');
	var table_code=arr[2];
	var col_code=arr[1];
	var grid=Ext.getCmp('codeList2Grid');
	var rowIndex=getRowNum(table_code,col_code);
	if(rowIndex==null){
		grid=Ext.getCmp('codeList2Grid1');
		rowIndex=getRowNum2(table_code,col_code);
	}
	dbRowFLdCon(grid,rowIndex,'','');//����ѡ�е���Ϣ�������Ϣ��ҳ��
	deleteAddCondition();//�Ƴ�
	radow.doEvent('arrToContab',str);	//��ֵ��ҳ��
}
function getRowNum(table,col){
	var grid=Ext.getCmp('codeList2Grid');
	var store = grid.getStore();
	var total=store.getCount();
	for(var i=0;i<total;i++){
		var record=store.getAt(i);
		var table_code=record.get("table_code");
		var col_code=record.get("col_code");
		if((table+col).trim().toLowerCase()==(table_code+col_code).trim().toLowerCase()){
			return i;
		}
	}
}
function getRowNum2(table,col){
	var grid=Ext.getCmp('codeList2Grid1');
	var store = grid.getStore();
	var total=store.getCount();
	for(var i=0;i<total;i++){
		var record=store.getAt(i);
		var table_code=record.get("table_code");
		var col_code=record.get("col_code");
		if((table+col).trim().toLowerCase()==(table_code+col_code).trim().toLowerCase()){
			return i;
		}
	}
}
function dbRowFLdCon(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	var col_name1=record.get("col_name1");//ָ������
	var col_name=record.get("col_name");//ָ������
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//����������
	var col_data_type_should=record.get("col_data_type_should");//ָ���������2
	var code_type=record.get("code_type");//ָ���������
	document.getElementById("col_data_type").value=col_data_type;
	document.getElementById("table_code").value=table_code;
	document.getElementById("col_code").value=col_code;
	document.getElementById("col_name").value=col_name;
	document.getElementById("col_name1").value=col_name1;
	document.getElementById("col_data_type_should").value=col_data_type_should;
	document.getElementById("code_type").value=code_type;
	setDisOrShow(code_type,col_data_type_should,col_data_type);
}
function deleteAddCondition(){
	var num=areastr.substr(areastr.length-1,areastr.length);//��ȡ������λ
	txtareaarr.splice(num, 1);//�Ƴ�ѡ�е��������
	txtareaarrCode.splice(num, 1);//�Ƴ�ѡ�е��������������
	arrall.splice(num, 1);
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
	areastr="";//����������λΪ��
}
</script>
