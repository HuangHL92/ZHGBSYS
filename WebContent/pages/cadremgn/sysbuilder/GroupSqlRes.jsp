<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>


</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
//��ʼ��ҳ����Ϣ
var pWindow='';
var pparam='';
var res="";
function init(){//��ʼ������
	pWindow=window.dialogArguments['window'];
	pparam=window.dialogArguments['param'];
	var arr=pparam.split(',');
	var ctci=arr[0];
	document.getElementById("ctci").value=ctci;
	var grid=parent.Ext.getCmp("searchList");
	var orderStore = grid.getStore();
	var rowCount=orderStore.getCount();
	for(var i=0;i<rowCount;i++){
		res=res+";"+orderStore.getAt(i).get("showcolname");
	}
	radow.doEvent("initpage",res);
}


</script>
<div>
	<odin:hidden property="ctci"/>
</div>
<div>
<table>
<tr>
<td>
<odin:groupBox property="group1" title="sql���ʽƴд" >
<table  style="width: 600;height:400;">
	<tr >
		<odin:select property="strfunc" label="�ַ���������" width="200" onchange="funcOnChange('strfunc');"></odin:select>
		<odin:select property="datefunc" label="����ʱ�䴦����" width="200"  onchange="funcOnChange('datefunc');"></odin:select>
	</tr>
	<tr>
		<odin:select property="numberfunc" label="��ֵ������" width="200"  onchange="funcOnChange('numberfunc');"></odin:select>
		<odin:select property="operatfunc" label="���������" width="200"  onchange="funcOnChange('operatfunc');"></odin:select>
	</tr>
	<tr>
		<odin:select property="databaseco" label="���ݿ��ֶ�" width="200"  onchange="funcOnChange('databaseco');"></odin:select>
		<td colspan="2">
		</td>
	</tr>
	<tr height="100">
		<td colspan="4">
			<odin:groupBox title="ƴд���ʽ����">
			<table style="width: 100%;height:100">
				<tr>
					<odin:textarea property="spellexpress" colspan="5" rows="6" onmouseout="expressOut();"></odin:textarea>
				</tr>
			</table>
			</odin:groupBox>
		</td>
	</tr>
	<tr height="100">
		<td colspan="4">
			<odin:groupBox title="���ʽ����˵��">
			<table style="width: 100%;height:100">
				<tr>
					<odin:textarea property="expexplain" colspan="5" rows="6"></odin:textarea>
				</tr>
			</table>
			</odin:groupBox>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<table>
				<tr>
					<td style="width:100;"></td>
					<td align="center" style="width:200;"><odin:button text="���ʽУ��" property="btn1" handler="spellexp"></odin:button></td>
					<td align="center" style="width:200;"><odin:button text="ȷ��" property="btn1" handler="save"></odin:button></td>
					<td style="width:100;"></td>
					<!-- <td align="center" style="width:200;"><odin:button text="��������" property="btn1" handler="funchelp"></odin:button></td> -->
				</tr>
			</table>
		</td>
	</tr>
	
</table>
</odin:groupBox>
</td>
</tr>
</table>
</div>

<script type="text/javascript">
function spellexp(){
	radow.doEvent("spellexp",res);
}
function save(){
	radow.doEvent("saveSqlExpress",res);
}
function funchelp(){
	
}
function closewin(){
	window.close();
}

function funcOnChange(idstr){
	var value=document.getElementById('spellexpress').value;
	var str=document.getElementById(idstr).value;
	if(idstr=="databaseco"){
		var arr=str.split("\/.");
		alert(arr[0]);
		str=arr[0];
	}
	value=value.substr(0,positionnum)+str+value.substr(positionnum,value.length);
	document.getElementById('spellexpress').value=value;
}
var positionnum=0;
function expressOut(){
	 var el = document.getElementById('spellexpress');
  if (el.selectionStart) { 
    	positionnum=el.selectionStart; 
    	return;
  } else if (document.selection) { 
	    var r = document.selection.createRange(); 
	    if (r == null) { 
	      positionnum= 0; 
	      return;
	    } 
	    var re = el.createTextRange(), 
	    rc = re.duplicate(); 
	    re.moveToBookmark(r.getBookmark()); 
	    rc.setEndPoint('EndToStart', re); 
	    positionnum= rc.text.length; 
	    return;
  }  
  positionnum= 0; 
  return;
}




</script>
