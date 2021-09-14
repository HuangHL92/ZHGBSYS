<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function expFile(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+value+"');\">导出上会材料</a>"
	+ "</font></div>"
}
function openProcessWin(v){
	var src = g_contextpath+'/PublishFileServlet?method=downloadshanghuicailiao&rb_id='+v;
	var selecthtml = '<select id="expParm"  name="expParm" style="margin:10px;"> '+
		  '<option value ="&cur_hj_4=4_1">部会</option>'+
		  '<option value="&cur_hj_4=4_2">书记会</option>'+
		  '<option value="&cur_hj_4=4_3">常委会</option>'+
		'</select>';
		
	var win = new Ext.Window({
		html : selecthtml+'<button style="margin:10px;"  onclick="$(\'#iframe_expFile\').attr(\'src\',\''+src+'\'+$(\'#expParm\').val());this.disabled=true;">点击开始执行导出</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
		title : '导出上会材料',
		layout : 'fit',
		width : 400,
		height : 350,
		closeAction : 'close',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{}            
	});
	win.show();
}

</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="rb_id"/>
<odin:hidden property="rb_name"/>
<odin:groupBox title="查询条件">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="rb_name1" label="批次名称" ></odin:textEdit>
		<odin:textEdit property="rb_date1" label="申请日期" ></odin:textEdit>
		<%-- <odin:select2 property="rb_type" label="类型" data="['1','班子换届'],['2','个人提拔']"></odin:select2> --%>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="批次信息" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="rb_id" />
		<odin:gridDataCol name="rb_name"/>
		<odin:gridDataCol name="rb_date"/>
		<odin:gridDataCol name="rb_no"/>
		<odin:gridDataCol name="rb_org"/>
		<odin:gridDataCol name="rb_applicant"/>
		<odin:gridDataCol name="rb_userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="rb_id" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_name" width="190" header="批次名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_date" width="140" header="申请日期" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_no" width="140" header="批次编号" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_org" width="140" header="申请机构" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_applicant" width="140" header="申请人" editor="text" edited="false" isLast="true"  align="center"/>
		
		<%-- <odin:gridEditColumn2 dataIndex="rb_id" width="140" header="操作" editor="text" renderer="expFile" edited="false"  align="center" isLast="true"  />
		 --%>
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>纪实批次管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" handler="infoSearch" isLast="true"/>
</odin:toolBar>


<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		document.getElementById('rb_name').value = rc.data.rb_name;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		$h.openPageModeWin('qcjs','pages.xbrm.JBXXT','基本信息',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
	});
	
});
function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','增加批次',510,300,{rb_id:''},g_contextpath);
}
function infoUpdate(){
	var rb_id = document.getElementById('rb_id').value;

	if(rb_id==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','修改批次',510,300,{rb_id:rb_id},g_contextpath);
}
function infoDelete(){//移除人员
	var rb_id = document.getElementById('rb_id').value;
	var rb_name = document.getElementById('rb_name').value;
	if(rb_id==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	$h.confirm("系统提示：",'删除批次记录将会删除该批次下所有的干部纪实记录以及附件，确定删除批次：'+rb_name+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',rb_id);
		}else{
			return false;
		}		
	});
}


function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.rb_id==$('#rb_id').val()){
				
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	}
}
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


