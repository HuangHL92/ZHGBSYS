<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
textarea{
width: 400px;overflow: auto;
}
.tb , .tb tr th, .tb tr td
{ border:1px solid #74A6CC;  border-right-width: 0px;  }
.tb
{ text-align: center; border-collapse: collapse;border-width: 2px; }
.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
	line-height:20px;  

	letter-spacing:3px;  
}
.tb .x-form-trigger{right: 0px;}/*图标对其  */
.tb input{width: 100%!important;border: none;}
.tb textarea{border: none;overflow: auto;word-break:break-all;};
.tb .x-form-item{margin-bottom: 0px;}


.ext-ie .x-grid3-cell-enable{
height: auto;
}
.x-grid3-cell-inner{
white-space: normal;
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
<table style="width: 100%;" class="tb">
	<tr>
		<td class="titleTd" width="15%" colspan="1">标题</td>
		<odin:textEdit property="spp13" readonly="true" width="'100%'" title="标题" colspan="1"/>
		<td width="15%" class="titleTd" colspan="1">兼职类型</td>
		<odin:select2 hideTrigger="true" readonly="true" property="spp02" width="'100%'" title="兼职类型" data="['1','领导干部社团兼职审批']" colspan="1"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file03" label="材料上传" fileTypeDesc="所有文件"  colspan="4"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
</table>
<odin:editgrid2 property="memberGrid"  
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
		<odin:gridEditColumn2 dataIndex="sp0106" width="140" header="现任单位及职务" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0130" width="140" header="目前其他兼职情况" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0107" width="140" header="名称" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0107b" width="140" header="职务" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0131" width="140" header="姓名" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0132" width="140" header="兼职时职务" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0108" width="50" header="任期" editor="text" edited="false" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0133" width="70" header="备注" editor="text" edited="false" menuDisabled="true" sortable="false" align="center" />
		<odin:gridEditColumn2 dataIndex="sp0134" width="70" header="已辞标志" editor="select" menuDisabled="true" sortable="false" codeType="XZ09" edited="false" align="center" isLast="true"  />
		
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
		<tags:ComBoxWithTree property="spb04" label="送审单位" disabled="true" codetype="USER" />
	
	</tr>
</table>

<script type="text/javascript">

function setDisabled1(){//登记节点
	$('#spp05,#spp04').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)').css('background-image','none')});
	$('#file03').text('材料附件');
}

function setspb04hidden(){//登记节点
	//机构隐藏
	$('#spb04SpanId').css('display','none');
	$('#spb04_combotree').css('display','none');
}
Ext.onReady(function() {
	$('#spp02').parent().parent().parent().attr('width','33%'); 

	/*在ie中解决断行问题(防止只有一行显示，主要解决ie兼容问题，ie8中当设宽度为100%时，文本域类容由换行时*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	var gridobj = document.getElementById('forView_memberGrid');
	var grid_pos = $h.pos(gridobj);
	memberGrid.setHeight(viewSize.height-130-grid_pos.top);

	if(typeof parentParam!='undefined'&&typeof parentParam.spp00!='undefined')
		document.getElementById('spp00').value = parentParam.spp00;
	
});

var g_contextpath = '<%= request.getContextPath() %>';
//文件下载
function download(id){
	
	//下载附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>
<odin:hidden property="field"/>
<odin:hidden property="value"/>
<odin:hidden property="sp01id"/>
<odin:hidden property="fieldTitle"/>
<odin:hidden property="name"/>

