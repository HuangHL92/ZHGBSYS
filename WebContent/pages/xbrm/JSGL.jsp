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
<odin:hidden property="rb_no"/>
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
		<odin:gridDataCol name="rb_leadview"/>
		<odin:gridDataCol name="rb_status"/>
		<odin:gridDataCol name="rbm_status"/>
		<odin:gridDataCol name="rb_userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="rb_id" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_name" width="190" header="批次名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_date" width="140" header="申请日期" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_no" width="140" header="批次编号" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_org" width="140" header="申请机构" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_applicant" width="140" header="申请人" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_leadview"  header="审批结果" editor="text" edited="false" renderer="leadviewshow"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_status"  header="状态" editor="text" edited="false" renderer="statusshow"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rbm_status"  header="合并状态" editor="text" edited="false" isLast="true" renderer="rbmRenderer"  align="center"/>
	</odin:gridColumnModel>
</odin:editgrid2>


<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>选拨任用管理</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="领导审批" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" isLast="true" handler="infoSearch"/>
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
		document.getElementById('rb_no').value = rc.data.rb_no;
		$h.openPageModeWin('qcjs','pages.xbrm.QCJS','干部选拨任用',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
		radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //关闭  
	});
	
});

function leadviewshow(value, params, record, rowIndex, colIndex, ds){
	if(value=='1'){
		return '同意';
	}else if(value=='2'){
		return '不同意';
	}
}

function rbmRenderer(value,params,record,rowIndex,colIndex,ds){
	if(value=='2'){
		return '';
	} else if(value=='1'){
		return "<a href=\"javascript:void();\" onclick=\"batchMergeCancel('"+record.data.rb_id+"')\">撤回合并</a>";
	} else {
		return "<a href=\"javascript:void();\" onclick=\"batchMergeSent('"+record.data.rb_id+"')\">发起合并</a>";
	}
}
function batchMergeCancel(rb_id){
	radow.doEvent('rbmCancel',rb_id);
}
function batchMergeSent(rb_id){
	//radow.doEvent('rbmSent',rb_id);
	$h.showModalDialog('picupload',g_contextpath+'/pages/xbrm/MGDeptWin.jsp?rb_id='+rb_id+'','信息发送', 300,100,null,{rb_id : rb_id},true);

}
function statusshow(value,params,record,rowIndex,colIndex,ds){
	if(value=='1'){
		return '完成';
	}else{
		return '进行中';
	}
}

function leadsugg(){
	radow.doEvent('leadsuggWin');
	/* Ext.Msg.prompt("领导审批结果","请输入'1'或'2'：1表示同意，2表示不同意。",function(btn,text){
		if(btn=='ok'){
			if(text=='1' || text=='2'){
				radow.doEvent('leadsuggest',text);
			}else{
				$h.alert('系统提示','输入不正确!');
			}
		}else if(btn=='cancel'){
			return false;
		}
	}); */
}

function spwin() {
	var shareTypeCmbstores = new Ext.data.SimpleStore({
		fields : ['id', 'value'],
		data : [['1', '同意'], ['2', '不同意']]
	});
	var shareTypeCmb = new Ext.form.ComboBox({
		editable : false,
		triggerAction : 'all',
		fieldLabel : '<font color="red">*</font>审批结果',
		labelStyle : "text-align:right;",  
		store : shareTypeCmbstores,
		displayField : 'value',
		valueField : 'id',
		name : 'type',
		mode : 'local',
		anchor : '90%',
		value : '1',
		allowBlank : false
	})
	var okbtn = new Ext.Button({
		text : '确定',
		handler : function() {
			radow.doEvent('leadsuggest', shareTypeCmb.getValue());
			insert_Win.close();
			//alert("值是 :"+shareTypeCmb.getValue()+" 显示值是:"+shareTypeCmb.getRawValue())
		}
	});
	var nobtn = new Ext.Button({
		text : '关闭',
		handler : function() {
			insert_Win.close();
		}
	});
	var insert_Win = new Ext.Window({
		plain : true,
		layout : 'form',
		resizable : false, // 改变大小
		draggable : true, // 不允许拖动
		closeAction : 'close',// 可被关闭 close or hide
		modal : true, // 模态窗口
		width : 300,
		height : 110,
		title : '领导审批结果',
		items : [shareTypeCmb],
		bodyStyle:"background-color: white;padding:10px 5px 0;",
		autoScroll : true,
		buttonAlign : 'center',
		loadMask : true,
		bbar : ['->','-',okbtn,'-', nobtn]
	});
	insert_Win.show();
}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','增加批次',580,300,{rb_id:''},g_contextpath);
}
function infoUpdate(){
	var rb_id = document.getElementById('rb_id').value;

	if(rb_id==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','修改批次',580,300,{rb_id:rb_id},g_contextpath);
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


