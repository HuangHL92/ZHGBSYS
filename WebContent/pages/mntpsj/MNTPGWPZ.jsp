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
function init(){//初始化参数
	document.getElementById("fabd00").value=parentParams.fabd00;
	
	updateGrid();
}

function updateGrid(){
	radow.doEvent('updateGrid');
}

function delGW(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"delGW2('"+record.get("fxyp00")+"');\">删除岗位</a>&nbsp;&nbsp;<a style='cursor:pointer;' onclick=\"delperson('"+record.get("fxyp00")+"','"+record.get("a0000")+"');\">删除人员</a></font>";
}

function delGW2(fxyp00){
	radow.doEvent('delGW2',fxyp00);
}

function delperson(fxyp00,a0000){
	if(a0000==""){
		alert("该岗位无人员！");
	}else{
		radow.doEvent('delperson',fxyp00+"@@"+a0000);
	}
}

</script>
<div id="TitleContent" style="width: 100%;text-align: right;">
<button type='button' class="btn btn-primary " onclick="addGW()" style="margin: 5px 10px 0px 10px;">增加岗位</button>
<button type='button' class="btn btn-primary " onclick="saveGW()" style="margin: 5px 10px 0px 10px;">保存岗位</button>
</div>
<table>
	<tr height="30">
		<td rowspan="3" width="70"></td>
		<odin:textEdit property="mntpname" label="模拟调配名" colspan="5" width="577" readonly="true"></odin:textEdit>
		<td rowspan="3" width="70"></td>
	</tr>
	<tr height="30">
		<odin:select2 property="mntp_b01"  label="调配单位" colspan="5" width="577" onchange="changeB01();" />
	</tr>
	<tr height="30">
		<odin:textEdit property="gwname" label="岗位名称" width="230"></odin:textEdit>
		<td width="10px"></td>
		<odin:select2 property="gwtype" label="成员类别" codeType="ZB129" width="230"></odin:select2>
	</tr>
	<tr>
		<td colspan="7" width="950">
				<odin:editgrid2 property="allGrid" hasRightMenu="false"  bbarId="pageToolBar" height="390" title="岗位和人员维护" forceNoScroll="true" autoFill="true" pageSize="200"  url="/">
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
						<odin:gridEditColumn2 dataIndex="gwname" width="90" menuDisabled="true" sortable="false" header="岗位名称" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="gwtype" width="40" menuDisabled="true" sortable="false" header="岗位类别" editor="select" codeType="ZB129" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0101" width="50" menuDisabled="true" sortable="false" header="姓名" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0192a" width="100" menuDisabled="true" sortable="false" header="职务" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="sortid" width="25" menuDisabled="true" sortable="false" header="岗位<br/>排序" editor="text" edited="false"  align="center" />
						<odin:gridEditColumn2 dataIndex="sortnum" width="25" menuDisabled="true" sortable="false" header="人员<br/>排序" editor="text" edited="false"  align="center" />
						<odin:gridEditColumn2 dataIndex="delGW" width="85" menuDisabled="true" sortable="false" header="操作" editor="text" edited="false" align="center" renderer="delGW"/>
						<odin:gridEditColumn2 dataIndex="gwmc" width="70" menuDisabled="true" sortable="false" header="重点岗位" editor="select" edited="true"  editorId="asd17"   align="center"  />
						<odin:gridEditColumn2 dataIndex="bzgw" width="60" header="班子岗位" editor="selectTree" edited="true" editorId="aaasda" codeType="KZ01"   isLast="true" align="center" />
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
		Ext.Msg.alert("系统提示","岗位名称不能为空！");
		return;
	}
	
	if(gwtype==""){
		Ext.Msg.alert("系统提示","成员类别不能为空！");
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