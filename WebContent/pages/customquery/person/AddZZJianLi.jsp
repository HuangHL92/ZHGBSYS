<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit.jsp"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<!-- JS 开始 -->
<script type="text/javascript" charset="gbk" src="rmb/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.all.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败--> 
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文--> 
<script type="text/javascript" charset="gbk" src="ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>

</style>
<body>
<odin:toolBar property="saveBtn" applyTo="save">
	<odin:fill/>
	<odin:buttonForToolBar id="composeBtn" handler="composeFunc" text="保存并更新主简历描述" icon="image/btn_adopt.png" isLast="true"/>
</odin:toolBar>
<odin:hidden property="a1700"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a1799"/>
	<odin:groupBox title="简历（当前主简历区域）预览" width="748">
		<div id="editorid" class="editorclass">
			<odin:hidden property="a1701z" name="简历" />
			<script id="editor" type="text/plain" style="width: 100%;   height:75;"></script>
		</div>
	</odin:groupBox>
	<odin:groupBox title="简历详情维护" width="748">
	<table style="width:748;">
		<tr>
			<td>
				<div id="save"></div>
			</td>
		</tr>
		<tr>
			<td>
			<table>
			<tr style="height:30;">
				<odin:NewDateEditTag property="a1701" label="开始时间" isCheck="false" labelSpanId="startSpanId" maxlength="6" required="true" width="170"></odin:NewDateEditTag>
				<td style="width:60px"></td>
				<td style="width:60px"></td>
				<odin:NewDateEditTag property="a1702" label="结束时间" isCheck="false" labelSpanId="endSpanId" maxlength="6" width="170"></odin:NewDateEditTag>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1703" label="简历描述" required="true" width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr>
				<tags:ComBoxWithTree property="a1705" codetype="JL02"  width="150"  label="重点岗位" nodeDblclick="a1705change" />
				<odin:select2 property="a1704" codeType="JL02"  width="100"  label="单位类型" readonly="true"/>	
				<odin:select2 property="a1706" data="['党务类','党务类'],['综合管理类','综合管理类'],['制造业和工业经济类','制造业和工业经济类']
				,['大数据和信息技术类','大数据和信息技术类'],['城建城管类','城建城管类'],['教育卫生类','教育卫生类'],['服务商贸类','服务商贸类']
				,['农业农村类','农业农村类'],['文化发展和旅游类','文化发展和旅游类'],['公检法政法类','公检法政法类'],['企业经营管理类','企业经营管理类']
				,['金融财务类','金融财务类']" 
				width="130"  label="分管工作类型" multiSelect="true" />
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1707" label="分管工作成效"  width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1708" label="备注" width="520" colspan="6"></odin:textEdit>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<div id="zzjlgrid">
				<odin:toolBar property="zzjl" applyTo="gridTopBar">
					<odin:fill />
					<odin:buttonForToolBar id="addBtn" handler="addFunc" isLast="true" text="新增行" icon="image/icon040a2.gif"/>
				</odin:toolBar>
				<div id ="gridTopBar"></div>
   				<odin:editgrid2 property="grid" title="温馨提示：请在此列表中添加当前主简历‘其间’或‘跨时间段’部分简历信息,双击进行维护" afteredit="cellEdit" width="630" height="180" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- 临时展示是否为期间等情况数据 1 是 0 不是 -->
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a1701" />
						<odin:gridDataCol name="a1702" />
						<odin:gridDataCol name="a1703" />
						<odin:gridDataCol name="a0221" />
						<odin:gridDataCol name="complete"/>
						<odin:gridDataCol name="a1704" />
						<odin:gridDataCol name="a1705" />
						<odin:gridDataCol name="a1706" />
						<odin:gridDataCol name="a1707" />
						<odin:gridDataCol name="a1708" />
						<odin:gridDataCol name="a0192e" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 	dataIndex="a1799"  	header=""  	   width="35" editor="text" edited="false" align="center" renderer="indexFormatter"></odin:gridEditColumn2>
						<odin:gridColumn 		dataIndex="a1700"  	header="主键"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="8"	header="开始"  width="60" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="8"	header="结束"  width="60" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1703" 	header="描述"  width="280" edited="false" editor="text" align="left" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="重点岗位"  width="60"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="单位类型"  width="60"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="分管工作类型"  width="120"  edited="false" editor="text" align="center" selectData="['党务类','党务类'],['综合管理类','综合管理类'],['制造业和工业经济类','制造业和工业经济类']
				,['大数据和信息技术类','大数据和信息技术类'],['城建城管类','城建城管类'],['教育卫生类','教育卫生类'],['服务商贸类','服务商贸类']
				,['农业农村类','农业农村类'],['文化发展和旅游类','文化发展和旅游类'],['公检法政法类','公检法政法类'],['企业经营管理类','企业经营管理类']
				,['金融财务类','金融财务类']"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="分管工作成效"  width="120"  edited="false" editor="text" align="center"  hidden="true" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="备注"  width="120"  edited="false" editor="text" align="center" hidden="true"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="caozuo" 	header=""  width="35" edited="false" editor="text" align="center" renderer="caozuo"  menuDisabled="true" sortable="false" isLast="true"></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
							data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid2>
			</div>
			</td>
		</tr>
	</table>
	</odin:groupBox>
</body>
<!-- </div>	 -->

<!-- 界面信息 结束 -->


<!-- 函数 开始 -->
<script type="text/javascript">
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function(){
	var pgrid = Ext.getCmp('grid');
	var dstore = pgrid.getStore();
	windowOnresize();
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
					ddGroup : 'GridDD',
					copy : false,
					notifyDrop : function(dd,e,data){
						//选中了多少行
						var rows = data.selections;
						//拖动到第几行
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						//修改store
						for ( i=0; i<rows.length; i++){
							var rowData = rows[i];
							if (!this.copy) dstore.remove(rowData);
							dstore.insert(index, rowData);
						}
						pgrid.view.refresh();
					}
				});
});
//调整grid为拖拽窗口自适应 
window.onresize = function() {
	windowOnresize();
}; 
<%
/* String RrmbCodeType = (String)session.getAttribute("RrmbCodeType"); */
String RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.EXTRA_TAGS[v[i]]){
				returnV += CodeTypeJson.EXTRA_TAGS[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
} 
 function windowOnresize() {
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
} 
//序号显示
function indexFormatter(value, params, record, rowIndex, colIndex, ds) {
  return rowIndex+1;
}
var rowIndex = 0;
//用于记录当前存在行数
function isHaveRow(obj){
	rowIndex = rowIndex + parseInt(obj);
}

//去除不小心添加的空格
function changeTrim(value,params,record,rowIndex,colIndex,ds){
	if(validatorNull(value)){
		return "";
	}else{
		return value.trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
	}
}

//操作
function caozuo(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='删除本行' onclick=\"delrowFunc('"+a1700+"');\">";
}
function isQiJian(a1701str) {
    if(a1701str.indexOf("(其") == 0||a1701str.indexOf("（其") == 0||
     a1701str.indexOf("(期") == 0||a1701str.indexOf("（期") == 0||
     a1701str.indexOf("(1") == 0 ||a1701str.indexOf("（1") == 0||
     a1701str.indexOf("(2") == 0 ||a1701str.indexOf("（2") == 0){
      return true;
    }else{
      return false;
    }
}

function a1705change(){
	var a1705=document.getElementById('a1705').value;
	var a1704=a1705.substring(0,2);
	document.getElementById('a1704').value=a1704;
	radow.doEvent('changeA1704name')
}
//获取字符串对象，无论当对象为空时，返回空字符串
function getObjStrOutNull(value) {
	return validatorNull(value) ? "" : value;
}
function doQuery() {
	radow.doEvent('grid.dogridquery');
/* 	radow.doEvent('compose'); */
}
//判断元素是否为 空,"空",""
function validatorNull(value) {
	if(typeof value == "undefined" || value == null || value == ""){
		return true;
	}else{
		return false;
	}
}
//拼接简历
function composeFunc(){
	var za1701=document.getElementById('a1701').value;
	var za1703=document.getElementById('a1703').value;
	if(za1701==''||za1703==''){
		odin.info("主简历开始时间或描述为空，请正确维护简历")
		return false;
		}
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid').store;
	
	//获取三个信息项数组
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		radow.doEvent('compose');
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//获取判断简历描述字符串
		if(isQiJian(a1703)){//如果存在期间或者用户主动换行的，则不进行处理
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//用于移除空白行
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//检验多个未截止时间,校验是否连续
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "系统提示");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	radow.doEvent('compose');
}
//检验简历完整性 + 检验简历连续性
function checkVarFunc(a1701Arr, a1702Arr, a1703Arr){
	var za1702=document.getElementById('a1702').value;
	var za1701=document.getElementById('a1701').value;
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//检验简历完整性
	for(var j = 0, n = a1702Arr.length; j < n; j++){
		msg = checkDate(a1701Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(a1702Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkJL(a1703Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(za1702);
		if(msg!=""){
				return msg;
		}	
		if(a1701Arr[j]==""){
			//return "存在简历开始时间为空！";
		}else{
			if(a1703Arr[j]==""){
					return "存在简历内容为空！";
			}
		}
		if(za1702){
			if((a1701Arr[j]!=''&&a1701Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]<za1701)){
				return "‘其间’及‘跨时间段’简历结束时间应介于职简历开始结束时间内（或选择并维护至正确主简历行）！";
			}
		}
	}
	//检验简历连续性
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '（' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "简历信息"+a1701Arr[i]+"到"+a1702Arr[i]+"的时间不连续！";
							}
						}
					}
				}
			}
		}
	} */
	
	return msg;
}

function checkDate(evalue){
	var reg = new RegExp("^[0-9]{6}$");
	
	if(evalue){
		var value = evalue.trim();
		//if(value.length!=6) odin.alert("检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存","","系统提示");
		if(!reg.test(value)) {
			return "检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存";
		}
		if(!/^[0-9]{6}$/.test(value)){
			return "检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存";
		}
	}
	
	return "";
}
function checkJL(evalue){
	if(!evalue){
		return "检测到存在空白简历行，请检查无误后再保存！";
	}
	return "";
}
//列表编辑事件
function cellEdit(e){
	//修改过的行从0开始  e.row;
	//修改列 e.column;
	//原始值 e.originalValue;
	//修改后的值 e.value;
	//当前修改的grid e.grid;
	//正在被编辑的字段名 e.field;
	//正在被编辑的行 e.record
	var grid = odin.ext.getCmp('grid');
	var ecolumn = grid.getColumnModel().getDataIndex(e.column);
	var evalue = e.value;
	var reg = new RegExp("^[0-9]*$");
	if("a1701" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
			if(!reg.test(value)) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
			if(!/^[0-9]*$/.test(value)) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
		}
	}
	if("a1702" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("请输入6位截止时间！","","系统提示");
			if(!reg.test(value)) odin.alert("请输入6位截止时间！","","系统提示");
			if(!/^[0-9]*$/.test(value)) odin.alert("请输入6位截止时间！","","系统提示");
		}
	}
	if("a1703" == ecolumn){
		if(evalue == '') odin.alert("请输入简历内容！","","系统提示");
	}
}
//新增行
function addFunc(){
	var a1701=document.getElementById("a1701").value;
	var a1703=document.getElementById("a1703").value;
	if(a1701==null || a1701=='' || a1703==null || a1703==''){
		$h.alert('','请先输入主条目的信息！')
		return;
		
	}
	
	var hanghao = Ext.getCmp('grid').getSelectionModel().lastActive;
	//alert(hanghao);
	if(!hanghao){
		hanghao=-1;
		}
	radow.addGridEmptyRow('grid',(hanghao+1));
	try {
		Ext.getCmp('grid').getSelectionModel().selectRow(hanghao+1);
		Ext.getCmp('grid').getView().focusRow(hanghao+1);
	} catch (e) {
		console.log("发生异常！");
	}
	rowIndex = rowIndex + 1;
}
//删除在职简历行
function delrowFunc(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("提示","是否确认删除本条简历？",200,function(e){
		if("ok" == e){
			radow.doEvent("deleteRow",a1700);
		}else{
			return;
		}
		});
	}
}
var ue = UE.getEditor('editor');

ue.addListener('blur',function(a,b,c){
	document.getElementById("a1701z").value = ue.getPlainTxt().trim();
});
function toA1701(obj){
	ue.ready(function () {
		var a1701 = obj;
		a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
		ue.setContent("<p>"+a1701+"</p>", false);
		ue.fireEvent("selectionchange");
	});
	document.getElementById("a1701z").value = a1701Format(obj);
}
//简历格式化
function a1701Format(obj){
	var a1701 = obj.replace(/期间/g,'其间');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	return newA1701;
}
</script>
<!-- 函数 结束 -->