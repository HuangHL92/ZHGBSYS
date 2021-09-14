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
	
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
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
				if(conditionName5.indexOf('between'!=-1)){//有between
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
				if(conditionName5.indexOf('between')!=-1){//有between
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
	document.getElementById("conditionStr").value=createWhereCon();// 隐藏拼接的where条件 
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
	zhtj=zhtj.replace(/或者/g,' or ');
	zhtj=zhtj.replace(/并且/g,' and ');
	zhtj=zhtj.replace(/非/g,' ! ');
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
<odin:hidden property="flds"/><!-- 存放已选择信息项 -->
<div id="infojihe" class="area4">
	<table id="tabcon1" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="信息集" >
				<odin:editgrid property="tableList2Grid" forceNoScroll="true" width="320" height="495" url="/" load="checkedFunc">
					<odin:gridJsonDataModel   root="data" >
						<odin:gridDataCol name="checked"  />
						<odin:gridDataCol name="tblcod"  />
						<odin:gridDataCol name="tblcpt"  isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn><!-- checkBoxClick="checkClicktable" -->
						<odin:gridColumn header="selectall" gridName="tableList2Grid" checkBoxClick="checkClicktable" checkBoxSelectAllClick="getCheckAll" align="center"  editor="checkbox" edited="true" dataIndex="checked" />
						<odin:gridEditColumn header="信息集名" width="105" dataIndex="tblcod" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="信息集名" width="215" dataIndex="tblcpt" edited="false" sortable="false" menuDisabled="true"  editor="text" align="left" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>
<!-- 调整对比表【左460/->205】【485/->218】【不需要的需要将codeList2Grid1隐藏掉 】 -->
<div  id="fldzbx" class="area4">
	<table id="tabcon2" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="指标项(提示:请勾选指标项，作为预览项)" >
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
						<odin:gridEditColumn header="指标项名" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="指标项名" width="215"  dataIndex="col_name1"  edited="false" sortable="false" menuDisabled="true" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="列数据类型" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标项" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="信息表" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
						<odin:gridColumn header="selectall" width="70" gridName="codeList2Grid" checkBoxClick="checkClickCode" align="center"  editor="checkbox" edited="true" dataIndex="checked"  isLast="true" checkBoxSelectAllClick="checkAll"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
		<tr>
			<td style="width:320;">
				<odin:groupBox title="指标项排序(提示:操作可实现移除与排序)" >
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
						<odin:gridEditColumn header="指标项名" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="指标项名" width="215"  dataIndex="col_name1" sortable="false" menuDisabled="true"  edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="列数据类型" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标项" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="信息表" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="操作" width="70" align="center" edited="false" editor="text" dataIndex="caozuo" renderer="runorstop"  isLast="true" />
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
				<odin:groupBox property="confirmconditionproperty" title="确定条件(提示:请双击指标项，添加确认条件)">
				<table>
					<tr>
						<odin:textEdit property="conditionName4" label="条件指标项"  width="160" readonly="true"></odin:textEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="&nbsp&nbsp清 &nbsp&nbsp&nbsp&nbsp除&nbsp&nbsp&nbsp"  property="btnm1" handler="conditionclear" ></odin:button></td>
					</tr>
					<tr>
						<odin:select property="conditionName5" label="条件符号"  width="144" onchange="valuefivefunc"></odin:select>
					</tr>
					<tr id="value1" style="display: none;">
						<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">值一</td>
						<td id='codediv'>
							<input type="hidden" id="conditionName6" name="conditionName6" />
							<input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style='cursor:default;' required="false"  label="false" />
						</td>
						<%-- <tags:ComBoxWithTree property="conditionName6"  label="值一" width="145" codetype="ZB01"/> --%> 
						<%-- <odin:select property="conditionName6" label="值一"  width="144" ></odin:select> --%>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="添加至列表" property="btnm2" handler="addtolistfunc" ></odin:button></td>
					</tr>
					<%--  <tr>
						<tags:ComBoxWithTree property="valuetree"  label="值一" width="145" codetype="ZB01"/>
						<td  rowspan="2"></td>
					</tr>  --%>
					<tr id="value11" style="display: none;">
						<odin:dateEdit property="conditionName61" label="值一"  width="145" ></odin:dateEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="添加至列表" property="btnm21" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value111" style="display: block;">
					<odin:textEdit property="conditionName611" label="值一"  width="160" ></odin:textEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="添加至列表" property="btnm211" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value1111" style="display: none;">
						<odin:numberEdit property="conditionName6111" label="值一"  width="160" ></odin:numberEdit>
						<td rowspan="2" style="text-align:right;width:90"><odin:button text="添加至列表" property="btnm211" handler="addtolistfunc"></odin:button></td>
					</tr>
					<tr id="value2" style="display: none;">
						<odin:dateEdit property="conditionName7" label="值二"  width="145" ></odin:dateEdit>
					</tr>
					<tr id="value21" style="display: none;">
						<td style="font-size:12px;padding-right:6px;margin-right:6px;text-align: right;">值二</td>
						<td id='codediv2' >
							<input type="hidden" id="conditionName71" name="conditionName71" />
							<input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style='cursor:default;' required="false"  label="false" />
						</td>
						<%-- <odin:select property="conditionName71" label="值二"  width="144" ></odin:select> --%>
					</tr>
					<tr id="value211" style="display: block;">
						<odin:numberEdit property="conditionName711" label="值二"  width="160" ></odin:numberEdit>
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
								<odin:button text="&nbsp&nbsp编&nbsp&nbsp&nbsp&nbsp辑&nbsp&nbsp"  property="editbtnm" handler="editbtnFunc">
								</odin:button></td>
							</tr>
							<tr>
								<td id="iddeletecondition" style="text-align:right;width:50;height:30"><odin:button text="&nbsp&nbsp移&nbsp&nbsp&nbsp&nbsp除&nbsp"  property="btnm3" handler="btnm3Func" ></odin:button></td>
							</tr>
							<tr>
								<td id="iddeleteallcondition" style="text-align:right;width:50;height:30"><odin:button text="全部删除"  property="btnm4" handler="btnm4Func" ></odin:button></td>
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
				<odin:groupBox property="mergeconditionproperty" title="组合条件"  >
				<table style="width:320">
				<tr>
					<td style="width:80">
						<odin:button text="选择条件" property="btnn1" handler="btnnFunc"></odin:button>
					</td>
					<td><odin:button text="（" property="btnn2" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="）" property="btnn3" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="非" property="btnn4" handler="btnnFunc"></odin:button></td>
					<td><odin:button text="并且" property="btnn5" handler="btnnFunc"></odin:button></td>
					<td ><odin:button text="或者" property="btnn6" handler="btnnFunc" ></odin:button></td>
					<td style="text-align:right;">
						<div style="margin-right: 10"><odin:button text="重新设置" property="btnn7" handler="btnn7Funcc" ></odin:button></div>
					</td>
				</tr>
				<tr style="width:540;">
						<odin:textarea property="conditionName9" colspan="8" rows="4" readonly="true" ></odin:textarea>
				</tr>
				<tr>
					<td colspan="7" style="font-size:14;color:red;" >请将条件选入“组合条件”框中，否则无效</td>
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
				<odin:button text="保存" property="btn4" handler="saveFunc"></odin:button>
			</td>
			<td width="100px">
			</td>
			<td style="width:30;text-align:left;width:145px;">
				<odin:button text="预览" property="btn5" handler="previewFunc"></odin:button>
			</td>
		</tr>
		<tr height="9px">
			<td colspan="3"></td>
		</tr>
	</table>
</div>
<odin:hidden property="col_data_type"/><!-- 列数据类型 -->
<odin:hidden property="conditoionlist"/><!-- 点击添加条件 生成当前行拼接的条件  -->
<odin:hidden property="table_code"/><!-- 当前选中的信息项 -->
<odin:hidden property="col_code"/><!-- 当前选中的信息项 -->
<odin:hidden property="col_name"/><!-- 当前选中的信息项 -->
<odin:hidden property="col_name1"/><!-- 当前选中的信息项 -->
<odin:hidden property="code_type"/><!-- 当前选中的信息项 -->
<odin:hidden property="col_data_type_should"/><!-- 当前选中的信息项 --><!-- 字段类型标示 -->
<odin:hidden property="qvid"/><!-- 视图主键 -->
<odin:hidden property="conditionStr"/><!-- 组合条件转换 成的 where条件 -->
<odin:hidden property="querysql"/><!-- 存储已经生成的sql -->
<odin:hidden property="parenttablename"/><!-- 存储父页面信息集(人员信息批量修改) -->
<odin:hidden property="paramurl" value='<%=request.getParameter("paramurl") %>'/><!-- 人员信息，自定义查询 -->
<odin:hidden property="allqryusestr"/>
<odin:hidden property="qryusestr"/>
<odin:hidden property="zhtj"/>
</div>
<script type="text/javascript">
function runorstop(value, params, record,rowIndex,colIndex,ds){
	var contextPath = '<%=request.getContextPath()%>';
	return "<img  src='"+contextPath+"/images/wrong.gif' title='删除' style='cursor:pointer' onclick=delThisOne(this,'"+value+"','"+rowIndex+"')><a>&nbsp;&nbsp;</a><img  src='"+contextPath+"/image/up.png' title='上移' style='cursor:pointer' onclick=upThisOne(this,'"+value+"','"+rowIndex+"')><img src='"+contextPath+"/image/down.png' title='下移' style='cursor:pointer' onclick=downThisOne(this,'"+value+"','"+rowIndex+"')>";
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
	if(conditionName5.indexOf('between')!=-1){//选择了between and条件
		if(code_type!=null&&code_type.trim()!=''){
			//设置select控件可编辑
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='block';//select
			document.getElementById("value211").style.display='none';//number
			radow.doEvent("setValue21Disable",code_type);
		}else if(col_data_type=='T'||col_data_type=='t'){//数据类型是date型
			//设置date控件可编辑
			radow.doEvent("setValue2Disable");
		}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
				||col_data_type_should=="null"||col_data_type_should==null
				||col_data_type_should.length==""||col_data_type_should.length==" "){
			radow.doEvent("setValue111Disable");
		}else{
			//设置number控件可编辑
			radow.doEvent("setValue211Disable");
		}
	}else if(conditionName5.indexOf('null')!=-1){
		//设置不可编辑
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
function setDisplayTalbe(str){//设置信息集显隐
	document.getElementById("infojihe").style.display=str;
	document.getElementById("infojihe").style.width="0";
}
function setDisplayCode(str){//设置指标项显隐
	document.getElementById("fldzbx").style.display=str;
	document.getElementById("fldzbx").style.width="0";
}
function initList(){
	radow.doEvent("loadtable");
}
function setDisalbe(){
	radow.doEvent("setDisalbe");
}
/* zxw修改样式 */
function checkClicktable(num,un,dataIndex,gridid){//选中信息集，查询显示，信息项
	//var grid = Ext.getCmp(gridid);
	cleanInfo();
	radow.doEvent("modifyTable");
}
//实现刷新列表，滚动条位置不变，参数配置
Ext.override(Ext.grid.GridView, {  
	    scrollTop : function() {  
	        this.scroller.dom.scrollTop = 0;  
	        this.scroller.dom.scrollLeft = 0;  
	    },  
	    scrollToTop : Ext.emptyFn  
	}); 

function checkClickCode(rowIndex,un,dataIndex,gridid){//选中信息项，实现该信息项移至预览信息项
	radow.doEvent("checkClickCode",rowIndex);
}
//全选
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
function rowFldeDbClick(grid,rowIndex,colIndex,event){//双击信息项
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	var col_name1=record.get("col_name1");//指标名称
	var col_name=record.get("col_name");//指标名称
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//列数据类型
	
	document.getElementById("col_data_type").value=col_data_type;
	document.getElementById("table_code").value=table_code;
	document.getElementById("col_code").value=col_code;
	document.getElementById("col_name").value=col_name;
	document.getElementById("col_name1").value=col_name1;
	var code_type=record.get("code_type");//指标代码类型
	document.getElementById("code_type").value=code_type;
	var col_data_type_should=record.get("col_data_type_should");//指标代码类型2
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
			&&code_type!=''&&code_type!='null'){//下拉选 有between and  有like  
		
		//查询符号
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
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//日期型字段   有between and  无like 
		//查询符号
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
			||col_data_type_should.length==""||col_data_type_should.length==" "){//文本 有like 无between and
		//查询符号
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='block';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
		//radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//数字 有like  有between and
		//查询符号
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
			&&code_type!=''&&code_type!='null'){//下拉选 有between and  有like  
		//查询符号
		radow.doEvent("code_type_value1",code_type);
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//日期型字段   有between and  无like 
		//查询符号
		radow.doEvent("selectValueList");
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//文本 有like 无between and
		//查询符号
		radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//数字 有like  有between and
		//查询符号
		radow.doEvent("selectValueListLikeBt");
	}
}
function setconditionName4(){
	////给条件指标项赋值
	document.getElementById("conditionName4").value=document.getElementById("col_name1").value;
}
function conditionclear(){//清除条件
	radow.doEvent("conditionclear");
}
function addtolistfunc(){//添加到列表
	var col_data_type_should=document.getElementById("col_data_type_should").value;
	var col_data_type=document.getElementById("col_data_type").value;
	var code_type=document.getElementById("code_type").value;
	var condition=document.getElementById("conditionName5").value;
	if(condition==null||condition==''){
		alert('请选择条件符号!');
		return;
	}
	if(condition.indexOf('null')==-1){
		if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
			var conditionName6=document.getElementById("conditionName6").value;
			var conditionName71=document.getElementById("conditionName71").value;
			if(conditionName6==null||conditionName6==''){
				alert('请选择值一!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName71==null||conditionName71==''){
					alert('请选择值二!');
					return;
				}
			}
		} else if("date"==col_data_type_should.toLowerCase()||col_data_type=='t'||col_data_type=='T'){
			var conditionName61=document.getElementById("conditionName61").value;
			var conditionName7=document.getElementById("conditionName7").value;
			if(conditionName61==null||conditionName61==''){
				alert('请选择值一!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName7==null||conditionName7==''){
					alert('请选择值二!');
					return;
				}
			}
		} else if("number"==col_data_type_should||"NUMBER"==col_data_type_should){
			var conditionName6111=document.getElementById("conditionName6111").value;
			var conditionName711=document.getElementById("conditionName711").value;
			if(conditionName6111==null||conditionName6111==''){
				alert('请输入值一!');
				return;
			}
			if(condition.indexOf('between')!=-1){
				if(conditionName711==null||conditionName711==''){
					alert('请输入值二!');
					return;
				}
			}
		}else{
			var conditionName611=document.getElementById("conditionName611").value;
			if(conditionName611==null||conditionName611==''){
				alert('请输入值一!');
				return;
			}
		}
	}
	radow.doEvent("addtolistfunc");
}
var txtareaarr=[];//存储所有添加条件 显示条件
var txtareaarrCode=[];//存储所有添加条件 拼接sql条件
var arrall=[];//存储所有条件 用于获取选择项
var arrselect=[];//存储选中的条件 用于保存到条件表中
function textareadd(){//添加到列表
	var conditionName5=document.getElementById("conditionName5").value;//获取条件符号
	if(conditionName5==null||conditionName5=="null"||conditionName5.trim()==''){
		alert("请选择条件!");
		return;
	}
	var conditoionlist=document.getElementById("conditoionlist").value;//获取拼接的添加条件
	var qryusestr=document.getElementById("qryusestr").value;
	txtareaarr.push(conditoionlist);//添加拼接条件到数组中
	arrall.push(qryusestr);
	var arrstr="";
	for(i=0;i<txtareaarr.length;i++){//循环添加条件
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;float:left;z-index:999999999;' onclick='backgroundFunc(this);'>"+(i+1)+"."+txtareaarr[i]+"</span><br/>";
	}
	document.getElementById("conditionName8").innerHTML=arrstr;//显示添加条件
	
	var conditionStr="";
	var table_code=document.getElementById("table_code").value;//获取信息集
	var col_code=document.getElementById("col_code").value;//获取信息项
	
	var col_data_type_should=document.getElementById("col_data_type_should").value;//获取存储的字段类型
	var code_type=document.getElementById("code_type").value;//获取下拉参数
	if(code_type!=null){
		code_type=code_type.toLowerCase().trim();
	}
	var col_data_type=document.getElementById("col_data_type").value;//获取显示的控件类型
	if(col_data_type!=null){
		col_data_type.toLowerCase().trim();
	}
	var tempright="";
	var templeft="";
	var temprightlike="";
	//conditionName6 值一
	//conditionName7 值二
	//col_data_type_should 表中 字段存储的数据 实际类型
	//col_data_type 前台控件类型
/* 	if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//下拉选 无between and  有like  
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
	}else if("date"==col_data_type_should||col_data_type=='t'){//日期型字段   有between and  无like 
			if(col_data_type_should=='date'){
				if(conditionName5.indexOf('between'!=-1)){//有between
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+") ";
				}else{
					tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
					conditionStr=" "+table_code+"."+col_code+" "+" "+conditionName5+" "+tempright+" ";
				}
				
			}else if(col_data_type_should!='date'&&col_data_type=='t'){
				if(conditionName5.indexOf('between')!=-1){//有between
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
			||col_data_type_should=="null"){//文本 有like 无between and
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
		
	}else if("number"==col_data_type_should){//数字 有like  有between and
		if(conditionName5.indexOf('between'!=-1)){//有between
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
var areastr="";//当前选中的条件id
function backgroundFunc(obj){//条件单机事件
	var spanarr=document.getElementById("conditionName8").children;//获取所有的添加条件
	for(i=0;i<spanarr.length;i++){//循环添加条件
		spanarr[i].style.background="";//清空所有的添加条件背景
	}
	areastr=obj.id;//获取选中的条件条件
	obj.style.background="pink";//设置选中的条件背景
}
function btnm3Func(){//移除
	if(areastr==""||areastr.length==0){
		alert("请选择要删除的条件!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//获取条件定位
	txtareaarr.splice(num, 1);//移除选中的添加条件
	txtareaarrCode.splice(num, 1);//移除选中的添加条件带代码
	arrall.splice(num, 1);
	var arrstr="";
	//重新设置添加条件
	for(i=0;i<txtareaarr.length;i++){
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;z-index:999999999;float:left; ' onclick='backgroundFunc(this);'>"+(i+1)+"."+txtareaarr[i]+"</span><br/>";
	}
	document.getElementById("conditionName8").innerHTML=arrstr;//显示添加条件
	areastr="";//重置条件定位为空
}
function btnm4Func(){//全部删除
	txtareaarr=[];//清空存储的用于显示的添加条件
	txtareaarrCode=[];//清空用于拼接sql 的where条件
	arrall=[];//清空用于保存到表中的添加条件
	document.getElementById("conditionName8").innerHTML="";//清空添加条件
}
//全部删除是否可编辑标志
var allDeleteFlag="1";//1 是 0 否
var leftBrakets="0";//左括号数初始化为0
var rightBrakets="0";//右括号数初始化为0
var mapBtn={
		'btnn2':'(',
		'btnn3':')',
		'btnn4':'.非.',
		'btnn5':'.并且.',
		'btnn6':'.或者.'
}
var mapBtnCode={
		'btnn2':'(',
		'btnn3':')',
		'btnn4':' not ',
		'btnn5':' and ',
		'btnn6':' or '
}
function btnnFunc(obj){//选择条件
	if(areastr==""&&obj.id=='btnn1'){//
		alert("请选择条件!");
		return;
	}
	var str=document.getElementById("conditionName9").value;//获取组合条件 
	if("btnn1"==obj.id){
		var num=parseInt(areastr.substr(areastr.length-1,areastr.length))+1;//定位选择的条件
		document.getElementById("conditionName9").value=str+" "+num;//拼接组合条件 
		
		var tempCondition="";
		for(i=0;i<txtareaarrCode.length;i++){
			if(i==(num-1)){
				tempCondition=txtareaarrCode[i];//生成where 条件 
			}
		}
		//隐藏where 条件
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+tempCondition;
		//增加选中的条件到数组中 arrselect
		var tempQryUse="";
		for(i=0;i<arrall.length;i++){
			if(i==(num-1)){
				tempQryUse=arrall[i];
			}
		}
		arrselect.push(tempQryUse);
		//隐藏组合条件
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+' '+arrselect.length;
	}else{
		//map[a0221]
		document.getElementById("conditionName9").value=str+mapBtn[obj.id];//拼接组合条件
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+mapBtn[obj.id];
		//隐藏where 条件
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+mapBtnCode[obj.id];
	}
	
	if(allDeleteFlag=='1'){//重新设置全部删除 标志 0
		allDeleteFlag='0';
	}
	if("btnn2"==obj.id){//左括号数
		leftBrakets=parseInt(leftBrakets)+1;
	}
	if("btnn3"==obj.id){//有括号数
		rightBrakets=parseInt(rightBrakets)+1;
	}
	//alert(111)
	radow.doEvent("setDisSelect",obj.id+","+leftBrakets+","+rightBrakets);//生成sql 
}
function btnn7Funcc(){//重新设置
	document.getElementById("conditionName9").value="";//清空组合条件
	if(allDeleteFlag=='0'){
		allDeleteFlag='1';//重置全部删除 标志1 已删除
	}
	leftBrakets=0;//左括号数初始化为0
	rightBrakets=0;//右括号数初始化为0
	document.getElementById("conditionStr").value="";//清空where条件
	document.getElementById("querysql").value="";//清空存储已经生成的sql
	document.getElementById("zhtj").value="";
	arrselect=[];
	radow.doEvent("refreshDis");//初始化组合条件按钮
}

function saveFunc(p){//保存
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"@";
	}
	document.getElementById("allqryusestr").value=allqryusestr;
	radow.doEvent("saveFunc",p);
}
function previewFunc(){//预览
	parent.refreshPreviewTab();
}
function getSqlParent(parenthiddenid,functionname){//parenthiddenid父页面隐藏的id functionname父页面的方法名称
	radow.doEvent("getSqlParent",parenthiddenid+","+functionname);
}

//新增视图，则清空设置条件设置条件tab页面的全局js变量(初始化全局变量与页面元素)
function clearConditionPar(){
	cleanInfo();
	radow.doEvent("clearValue");//清空 显示的控件
}
function cleanInfo(){
	leftBrakets=0;//左括号数初始化为0
	rightBrakets=0;//右括号数初始化为0
	//全部删除是否可编辑标志
	allDeleteFlag="1";//1 是 0 否
	areastr="";//当前选中的条件id
	txtareaarr=[];//存储所有条件 显示条件
	txtareaarrCode=[];//存储所有条件 拼接sql条件
	arrselect=[];
	arrall=[];
	document.getElementById("conditoionlist").value="";//清空隐藏的 当前拼接的条件 
	document.getElementById("table_code").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_code").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_name").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_name1").value="";//清空隐藏的清空隐藏的  当前选中的信息项 -
	document.getElementById("code_type").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_data_type_should").value="";//清空隐藏的 当前选中的信息项  字段类型标示
	document.getElementById("conditionStr").value="";//清空隐藏的 组合条件转换 成的 where条件
	document.getElementById("querysql").value="";//清空隐藏的 存储已经生成的sql
	document.getElementById("conditionName8").innerHTML="";//清空添加条件
	document.getElementById("zhtj").value="";//隐藏的组合字段
	document.getElementById("flds").value="";//初始化勾选的 -- 指标项
}
function getCheckAll(){
	cleanInfo();
	radow.doEvent("modifyTable");
}

function hiddenSelectBox(){
	 
//	 document.getElementById('ftpUpManagePanel').style.width = docum
	
}

function saveschemeall(qvid){//自定义查询 页面调用
	var allqryusestr="";
	for(i=0;i<arrselect.length;i++){
		allqryusestr=allqryusestr+arrselect[i]+"@";
	}
	document.getElementById("allqryusestr").value=allqryusestr;
	document.getElementById("qvid").value=qvid;
	radow.doEvent("saveFunc");
}
function editbtnFunc(obj){//编辑
	if(areastr==""||areastr.length==0){
		alert("请选中要编辑的条件!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//获取条件定位
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
	dbRowFLdCon(grid,rowIndex,'','');//设置选中的信息项，隐藏信息到页面
	deleteAddCondition();//移除
	radow.doEvent('arrToContab',str);	//赋值到页面
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
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	var col_name1=record.get("col_name1");//指标名称
	var col_name=record.get("col_name");//指标名称
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//列数据类型
	var col_data_type_should=record.get("col_data_type_should");//指标代码类型2
	var code_type=record.get("code_type");//指标代码类型
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
	var num=areastr.substr(areastr.length-1,areastr.length);//获取条件定位
	txtareaarr.splice(num, 1);//移除选中的添加条件
	txtareaarrCode.splice(num, 1);//移除选中的添加条件带代码
	arrall.splice(num, 1);
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
	areastr="";//重置条件定位为空
}
</script>
