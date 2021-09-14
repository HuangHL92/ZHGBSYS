<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<link rel="stylesheet" type="text/css" href="mainPage/css/odin-font-size.css"> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%@include file="/comOpenWinInit.jsp" %>

<odin:hidden property="b0111"/>
<odin:hidden property="famx00"/>
<odin:hidden property="fabd00"/>
<odin:hidden property="fxyp00"/>
<odin:hidden property="flag" value="1"/>

<script type="text/javascript">
function init(){//��ʼ������
	document.getElementById("fabd00").value=parentParams.fabd00;
	
	updateGrid();
}

function updateGrid(){
	radow.doEvent('updateGrid');
}

function delGW(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">ά��</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">ɾ��</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"delGW2('"+record.get("fxyp00")+"');\">ɾ����λ</a>&nbsp;&nbsp;<a style='cursor:pointer;' onclick=\"delperson('"+record.get("fxyp00")+"','"+record.get("a0000")+"');\">ɾ����Ա</a></font>";
}

function delGW2(fxyp00){
	radow.doEvent('delGW2',fxyp00);
}

function delperson(fxyp00,a0000){
	if(a0000==""){
		alert("�ø�λ����Ա��");
	}else{
		radow.doEvent('delperson',fxyp00+"@@"+a0000);
	}
}

</script>
<div id="TitleContent" style="width: 100%;text-align: right;">
<button type='button' class="btn btn-primary " onclick="addGW()" style="margin: 5px 10px 0px 10px;">���Ӹ�λ</button>
<button type='button' class="btn btn-primary " onclick="saveGW()" style="margin: 5px 10px 0px 10px;">�����λ</button>
</div>
<table>
	<tr height="30">
		<td rowspan="3" width="70"></td>
		<odin:textEdit property="mntpname" label="ģ�������" colspan="5" width="577" readonly="true"></odin:textEdit>
		<td rowspan="3" width="70"></td>
	</tr>
	<tr height="30">
		<odin:select2 property="mntp_b01"  label="���䵥λ" colspan="5" width="577" onchange="changeB01();" />
	</tr>
	<tr height="30">
		<odin:textEdit property="gwname" label="��λ����" width="230"></odin:textEdit>
		<td width="10px"></td>
		<odin:select2 property="gwtype" label="��Ա���" codeType="ZB129" width="230"></odin:select2>
	</tr>
	<tr>
		<td colspan="7" width="950">
				<odin:editgrid2 property="allGrid" hasRightMenu="false"  bbarId="pageToolBar" height="390" title="��λ����Աά��" forceNoScroll="true" autoFill="true" pageSize="200"  url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="fxyp00"/>
						<odin:gridDataCol name="tp0100"/>
						<odin:gridDataCol name="sortnum"/>
						<odin:gridDataCol name="a0000"/>
						<odin:gridDataCol name="gwname"/>
						<odin:gridDataCol name="gwtype"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="sortid"/>
						<odin:gridDataCol name="bzgw"/>
						<odin:gridDataCol name="gwmc" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="gwname" width="90" menuDisabled="true" sortable="false" header="��λ����" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="gwtype" width="40" menuDisabled="true" sortable="false" header="��λ���" editor="select" codeType="ZB129" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0101" width="50" menuDisabled="true" sortable="false" header="����" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0192a" width="100" menuDisabled="true" sortable="false" header="ְ��" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="sortid" width="25" menuDisabled="true" sortable="false" header="��λ<br/>����" editor="text" edited="false"  align="center" />
						<odin:gridEditColumn2 dataIndex="sortnum" width="25" menuDisabled="true" sortable="false" header="��Ա<br/>����" editor="text" edited="false"  align="center" />
						<odin:gridEditColumn2 dataIndex="delGW" width="85" menuDisabled="true" sortable="false" header="����" editor="text" edited="false" align="center" renderer="delGW"/>
						<odin:gridEditColumn2 dataIndex="gwmc" width="70" menuDisabled="true" sortable="false" header="�ص��λ" editor="select" edited="true"  editorId="asd17"   align="center"  />
						<odin:gridEditColumn2 dataIndex="bzgw" width="60" header="���Ӹ�λ" editor="selectTree" edited="true" editorId="aaasda" codeType="KZ01"   isLast="true" align="center" />
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
	</tr>
	
</table>

<script type="text/javascript">
function changeB01(){
	addGW();
	radow.doEvent('allGrid.dogridquery');
}

function addGW(){
	document.getElementById("gwname").value="";
	document.getElementById("gwtype").value="";
	document.getElementById("gwtype_combo").value="";
	document.getElementById("flag").value="1";
	document.getElementById('fxyp00').value='';
}

function saveGW(){
	var gwname = document.getElementById('gwname').value;	
	var gwtype = document.getElementById('gwtype').value;
	if(gwname==""){
		Ext.Msg.alert("ϵͳ��ʾ","��λ���Ʋ���Ϊ�գ�");
		return;
	}
	
	if(gwtype==""){
		Ext.Msg.alert("ϵͳ��ʾ","��Ա�����Ϊ�գ�");
		return;
	} 
	radow.doEvent('save');
}


Ext.onReady(function(){
	$h.initGridSort('allGrid',function(g){
	    radow.doEvent('allgwsort');
	  });
	Ext.getCmp('allGrid').on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#flag').val("2");
		$('#gwname').val(rc.data.gwname);
		$('#fxyp00').val(rc.data.fxyp00);
		odin.setSelectValue('gwtype',rc.data.gwtype)
	});
	
	parent.Ext.getCmp(subWinId).on('beforeclose',function(){
		realParent.infoSearch();
	})
}); 

</script>
</html>