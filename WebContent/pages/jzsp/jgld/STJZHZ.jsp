<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
textarea{
width: 400px;overflow: auto;
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="sp0100s"/>
<odin:hidden property="spp00"/>
<odin:hidden property="usertype"/>
<odin:editgrid2 property="memberGrid"  afteredit="afedt" 
hasRightMenu="false" title="社团兼职审批列表汇总" autoFill="true"  url="/"
 colHGroupRows="[{header:'',align:'center',colspan:1,rowcss:false},{header:'兼职领导干部',align:'center',colspan:3},{header:'拟兼职社团',align:'center',colspan:2},{header:'原兼职领导干部',align:'center',colspan:2},{header:'',align:'center',colspan:1,rowcss:false},{header:'',align:'center',colspan:1,rowcss:false},{header:'',align:'center',colspan:1,rowcss:false}]">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="sp0100" />
		<odin:gridDataCol name="a0000"/>
		<odin:gridDataCol name="sp0102"/>
		<odin:gridDataCol name="sp0130"/>
		<odin:gridDataCol name="sp0131"/>
		<odin:gridDataCol name="sp0132"/>
		<odin:gridDataCol name="sp0133"/>
		<odin:gridDataCol name="sp0134"/>
		<odin:gridDataCol name="sp0103"/>
		<odin:gridDataCol name="sp0104"/>
		<odin:gridDataCol name="sp0106"/>
		<odin:gridDataCol name="sp0107" />
		<odin:gridDataCol name="sp0107b" />
		<odin:gridDataCol name="spb02" />
		<odin:gridDataCol name="spb03" />
		<odin:gridDataCol name="spb04" />
		<odin:gridDataCol name="sp0114" />
		<odin:gridDataCol name="sp0108" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="sp0102" width="110" editor="text" header="姓名" edited="false" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0106" width="140" header="现任单位及职务" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0130" width="140" header="目前其他兼职情况" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0107" width="140" header="名称" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0107b" width="140" header="职务" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0131" width="140" header="姓名" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0132" width="140" header="兼职时职务" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0108" width="50" header="任期" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0133" width="70" header="备注" editor="text" edited="true" menuDisabled="true" sortable="false" align="center" />
		<odin:gridEditColumn2 dataIndex="sp0134" width="70" header="已辞标志" editor="select" menuDisabled="true" sortable="false" codeType="XZ09" edited="true" align="center" isLast="true"  />
		
	</odin:gridColumnModel>
</odin:editgrid2>
<table style="width: 100%;">
  <tr>
    <odin:textarea property="spp04"  label="处室意见" rows="6" cols="20" />
    <odin:textarea property="spp05" label="部领导意见" rows="6" />
  </tr>
</table>
<table style="width: 100%;">
	<tr>
		<tags:ComBoxWithTree property="spb04" label="送审单位" codetype="USER" nodeDblclick="nodeclick" />
		<td align="left" style="">
			<odin:button text="保存"  property="save"></odin:button>
		</td>
		<td align="left" id="passtd" style="">
			<odin:button text="保存并送审"  property="saveS" handler="saveS"></odin:button>
		</td>
		<td align="left" id="dentd" style="">
			<odin:button text="未通过"  property="saveB" handler="saveB"></odin:button>
		</td>
		<td><button onclick=" location.replace(location.href); ">刷新</button></td>
	</tr>
</table>

<script type="text/javascript">

function setDisabled1(){//登记节点
	$('#saveB').css('display','none');
	$('#spp05').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)').css('background-image','none')});
}
function setDisabled2(){//登记节点
	$('#save').css('display','none');
	$('#saveS button').text('通过');
	$('#spb04SpanId').css('display','none');
	Ext.getCmp('spb04_combotree').hide();
	$('#spp04').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)').css('background-image','none')});
	$('#passtd').attr('align','right').css('width','700px');
	$('#dentd').css('padding-left','50px');
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-170);
	
	if(typeof parentParam!='undefined'&&typeof parentParam.sp0100s!='undefined')
		document.getElementById('sp0100s').value = parentParam.sp0100s;
	
	if(typeof parentParam!='undefined'&&typeof parentParam.spp00!='undefined')
		document.getElementById('spp00').value = parentParam.spp00;
	
});

function saveCallBack(t,fg){
	//var sp0100 = $('#sp0100').val();
	$h.alert('系统提示', t, function(){
	/* 	parent.$('#sp0100').val($('#sp0100').val());
		parent.$('#a0000').val($('#a0000').val());*/
		parent.infoSearch(); 
		window.close();
		
		//window.close();
	});
}
var g_contextpath = '<%= request.getContextPath() %>';

function saveS(){
	radow.doEvent('save.onclick','ss2');
}
function saveB(){
	radow.doEvent('save.onclick','ss3');
}
function nodeclick(node,e){
	$('#usertype').val(node.attributes.ntype);
}

function afedt(e,b){
	
	$('#field').val(e.field);
	$('#name').val(e.record.data.sp0102);
	$('#value').val(e.value);
	$('#sp01id').val(e.record.data.sp0100);
	$('#fieldTitle').val(e.grid.colModel.config[e.column].header);
	//console.log(b)
	//alert(e.grid.colModel.config[e.column].header)
	var grid =Ext.getCmp('memberGrid');
	var store = grid.store;
	store.commitChanges() 
	radow.doEvent('saveChange');
}
</script>
<odin:hidden property="field"/>
<odin:hidden property="value"/>
<odin:hidden property="sp01id"/>
<odin:hidden property="fieldTitle"/>
<odin:hidden property="name"/>

