<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit.jsp"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<!-- JS ��ʼ -->
<script type="text/javascript" charset="gbk" src="rmb/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.all.js"> </script>
<!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��--> 
<!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������--> 
<script type="text/javascript" charset="gbk" src="ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>

</style>
<body>
<odin:toolBar property="saveBtn" applyTo="save">
	<odin:fill/>
	<odin:buttonForToolBar id="composeBtn" handler="composeFunc" text="���沢��������������" icon="image/btn_adopt.png" isLast="true"/>
</odin:toolBar>
<odin:hidden property="a1700"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a1799"/>
	<odin:groupBox title="��������ǰ����������Ԥ��" width="748">
		<div id="editorid" class="editorclass">
			<odin:hidden property="a1701z" name="����" />
			<script id="editor" type="text/plain" style="width: 100%;   height:75;"></script>
		</div>
	</odin:groupBox>
	<odin:groupBox title="��������ά��" width="748">
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
				<odin:NewDateEditTag property="a1701" label="��ʼʱ��" isCheck="false" labelSpanId="startSpanId" maxlength="6" required="true" width="170"></odin:NewDateEditTag>
				<td style="width:60px"></td>
				<td style="width:60px"></td>
				<odin:NewDateEditTag property="a1702" label="����ʱ��" isCheck="false" labelSpanId="endSpanId" maxlength="6" width="170"></odin:NewDateEditTag>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1703" label="��������" required="true" width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr>
				<tags:ComBoxWithTree property="a1705" codetype="JL02"  width="150"  label="�ص��λ" nodeDblclick="a1705change" />
				<odin:select2 property="a1704" codeType="JL02"  width="100"  label="��λ����" readonly="true"/>	
				<odin:select2 property="a1706" data="['������','������'],['�ۺϹ�����','�ۺϹ�����'],['����ҵ�͹�ҵ������','����ҵ�͹�ҵ������']
				,['�����ݺ���Ϣ������','�����ݺ���Ϣ������'],['�ǽ��ǹ���','�ǽ��ǹ���'],['����������','����������'],['������ó��','������ó��']
				,['ũҵũ����','ũҵũ����'],['�Ļ���չ��������','�Ļ���չ��������'],['���취������','���취������'],['��ҵ��Ӫ������','��ҵ��Ӫ������']
				,['���ڲ�����','���ڲ�����']" 
				width="130"  label="�ֹܹ�������" multiSelect="true" />
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1707" label="�ֹܹ�����Ч"  width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1708" label="��ע" width="520" colspan="6"></odin:textEdit>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<div id="zzjlgrid">
				<odin:toolBar property="zzjl" applyTo="gridTopBar">
					<odin:fill />
					<odin:buttonForToolBar id="addBtn" handler="addFunc" isLast="true" text="������" icon="image/icon040a2.gif"/>
				</odin:toolBar>
				<div id ="gridTopBar"></div>
   				<odin:editgrid2 property="grid" title="��ܰ��ʾ�����ڴ��б�����ӵ�ǰ����������䡯�򡮿�ʱ��Ρ����ּ�����Ϣ,˫������ά��" afteredit="cellEdit" width="630" height="180" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- ��ʱչʾ�Ƿ�Ϊ�ڼ��������� 1 �� 0 ���� -->
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
						<odin:gridColumn 		dataIndex="a1700"  	header="����"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="8"	header="��ʼ"  width="60" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="8"	header="����"  width="60" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1703" 	header="����"  width="280" edited="false" editor="text" align="left" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="�ص��λ"  width="60"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="��λ����"  width="60"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="�ֹܹ�������"  width="120"  edited="false" editor="text" align="center" selectData="['������','������'],['�ۺϹ�����','�ۺϹ�����'],['����ҵ�͹�ҵ������','����ҵ�͹�ҵ������']
				,['�����ݺ���Ϣ������','�����ݺ���Ϣ������'],['�ǽ��ǹ���','�ǽ��ǹ���'],['����������','����������'],['������ó��','������ó��']
				,['ũҵũ����','ũҵũ����'],['�Ļ���չ��������','�Ļ���չ��������'],['���취������','���취������'],['��ҵ��Ӫ������','��ҵ��Ӫ������']
				,['���ڲ�����','���ڲ�����']"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="�ֹܹ�����Ч"  width="120"  edited="false" editor="text" align="center"  hidden="true" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="��ע"  width="120"  edited="false" editor="text" align="center" hidden="true"  ></odin:gridEditColumn2>
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

<!-- ������Ϣ ���� -->


<!-- ���� ��ʼ -->
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
						//ѡ���˶�����
						var rows = data.selections;
						//�϶����ڼ���
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						//�޸�store
						for ( i=0; i<rows.length; i++){
							var rowData = rows[i];
							if (!this.copy) dstore.remove(rowData);
							dstore.insert(index, rowData);
						}
						pgrid.view.refresh();
					}
				});
});
//����gridΪ��ק��������Ӧ 
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
//�����ʾ
function indexFormatter(value, params, record, rowIndex, colIndex, ds) {
  return rowIndex+1;
}
var rowIndex = 0;
//���ڼ�¼��ǰ��������
function isHaveRow(obj){
	rowIndex = rowIndex + parseInt(obj);
}

//ȥ����С����ӵĿո�
function changeTrim(value,params,record,rowIndex,colIndex,ds){
	if(validatorNull(value)){
		return "";
	}else{
		return value.trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
	}
}

//����
function caozuo(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='ɾ������' onclick=\"delrowFunc('"+a1700+"');\">";
}
function isQiJian(a1701str) {
    if(a1701str.indexOf("(��") == 0||a1701str.indexOf("����") == 0||
     a1701str.indexOf("(��") == 0||a1701str.indexOf("����") == 0||
     a1701str.indexOf("(1") == 0 ||a1701str.indexOf("��1") == 0||
     a1701str.indexOf("(2") == 0 ||a1701str.indexOf("��2") == 0){
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
//��ȡ�ַ����������۵�����Ϊ��ʱ�����ؿ��ַ���
function getObjStrOutNull(value) {
	return validatorNull(value) ? "" : value;
}
function doQuery() {
	radow.doEvent('grid.dogridquery');
/* 	radow.doEvent('compose'); */
}
//�ж�Ԫ���Ƿ�Ϊ ��,"��",""
function validatorNull(value) {
	if(typeof value == "undefined" || value == null || value == ""){
		return true;
	}else{
		return false;
	}
}
//ƴ�Ӽ���
function composeFunc(){
	var za1701=document.getElementById('a1701').value;
	var za1703=document.getElementById('a1703').value;
	if(za1701==''||za1703==''){
		odin.info("��������ʼʱ�������Ϊ�գ�����ȷά������")
		return false;
		}
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid').store;
	
	//��ȡ������Ϣ������
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		radow.doEvent('compose');
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//��ȡ�жϼ��������ַ���
		if(isQiJian(a1703)){//��������ڼ�����û��������еģ��򲻽��д���
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//�����Ƴ��հ���
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//������δ��ֹʱ��,У���Ƿ�����
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "ϵͳ��ʾ");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	radow.doEvent('compose');
}
//������������� + �������������
function checkVarFunc(a1701Arr, a1702Arr, a1703Arr){
	var za1702=document.getElementById('a1702').value;
	var za1701=document.getElementById('a1701').value;
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//�������������
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
			//return "���ڼ�����ʼʱ��Ϊ�գ�";
		}else{
			if(a1703Arr[j]==""){
					return "���ڼ�������Ϊ�գ�";
			}
		}
		if(za1702){
			if((a1701Arr[j]!=''&&a1701Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]<za1701)){
				return "����䡯������ʱ��Ρ���������ʱ��Ӧ����ְ������ʼ����ʱ���ڣ���ѡ��ά������ȷ�������У���";
			}
		}
	}
	//�������������
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '��' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "������Ϣ"+a1701Arr[i]+"��"+a1702Arr[i]+"��ʱ�䲻������";
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
		//if(value.length!=6) odin.alert("��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���","","ϵͳ��ʾ");
		if(!reg.test(value)) {
			return "��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���";
		}
		if(!/^[0-9]{6}$/.test(value)){
			return "��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���";
		}
	}
	
	return "";
}
function checkJL(evalue){
	if(!evalue){
		return "��⵽���ڿհ׼����У�����������ٱ��棡";
	}
	return "";
}
//�б�༭�¼�
function cellEdit(e){
	//�޸Ĺ����д�0��ʼ  e.row;
	//�޸��� e.column;
	//ԭʼֵ e.originalValue;
	//�޸ĺ��ֵ e.value;
	//��ǰ�޸ĵ�grid e.grid;
	//���ڱ��༭���ֶ��� e.field;
	//���ڱ��༭���� e.record
	var grid = odin.ext.getCmp('grid');
	var ecolumn = grid.getColumnModel().getDataIndex(e.column);
	var evalue = e.value;
	var reg = new RegExp("^[0-9]*$");
	if("a1701" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
			if(!reg.test(value)) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
			if(!/^[0-9]*$/.test(value)) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
		}
	}
	if("a1702" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
			if(!reg.test(value)) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
			if(!/^[0-9]*$/.test(value)) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
		}
	}
	if("a1703" == ecolumn){
		if(evalue == '') odin.alert("������������ݣ�","","ϵͳ��ʾ");
	}
}
//������
function addFunc(){
	var a1701=document.getElementById("a1701").value;
	var a1703=document.getElementById("a1703").value;
	if(a1701==null || a1701=='' || a1703==null || a1703==''){
		$h.alert('','������������Ŀ����Ϣ��')
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
		console.log("�����쳣��");
	}
	rowIndex = rowIndex + 1;
}
//ɾ����ְ������
function delrowFunc(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("��ʾ","�Ƿ�ȷ��ɾ������������",200,function(e){
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
//������ʽ��
function a1701Format(obj){
	var a1701 = obj.replace(/�ڼ�/g,'���');
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
<!-- ���� ���� -->