<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function opFunction(value, params, record, rowIndex, colIndex, ds) {
	var id = record.get('checkregid');
	var status = record.get('regstatus');
	if(status=='0'){
		return "<a href=\"javascript:void()\" onclick=\"sqsj('"+id+"')\">收取数据</a>";
	} else if(status=='1'){
		return "<a href=\"javascript:void()\" onclick=\"cxsq('"+id+"')\">撤销收取</a>";
		//return '';	
	} else {
		return '';
	}
}
function sqsj(id){
	document.getElementById('checkregid').value=id;
	radow.doEvent('chaneStatus','1');
}
function cxsq(id){
	document.getElementById('checkregid').value=id;
	radow.doEvent('chaneStatus','0');
}


function downFunction(value, params, record, rowIndex, colIndex, ds){
	var grid = Ext.getCmp('memberGrid');
	var id = record.get('checkregid');
	var cm = grid.getColumnModel();
	var col = cm.config[colIndex].dataIndex;
	if(value=='1'){
		return "<a href=\"javascript:void()\" onclick=\"downF('"+id+"','"+col+"')\">已上传</a>";
	} else {
		return '未上传';
	}
}

function downF(checkregid, col){
	document.getElementById('frameid').src="RegCheckServlet?method=downloadFKFile&checkregid="+encodeURI(encodeURI(checkregid))+"&type="+col;
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
function impCrjxx(){
	//var record = Ext.getCmp('memberGrid').getSelectionModel().getSelected();
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impCrjxx','pages.xbrm.jcgl.CheckRegCRJ','出入境反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crcrj1');
}

function impFcxx(){
	//var record = Ext.getCmp('memberGrid').getSelectionModel().getSelected();
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impFcxx','pages.xbrm.jcgl.CheckRegFC','房产反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crfc1');
}

function impCbxx(){
	//var record = Ext.getCmp('memberGrid').getSelectionModel().getSelected();
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impCbxx','pages.xbrm.jcgl.CheckRegBX','保险反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crbx1');
}

function impGscbxx(){
	//var record = Ext.getCmp('memberGrid').getSelectionModel().getSelected();
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impGscbxx','pages.xbrm.jcgl.CheckRegGSCG','工商参股反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crgscg1');
}
function impGszwxx(){
	//var record = Ext.getCmp('memberGrid').getSelectionModel().getSelected();
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impGszwxx','pages.xbrm.jcgl.CheckRegGSZW','工商职务反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crgszw1');
}
function impZqxx(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	//$h.openPageModeWin('impZqxx','pages.xbrm.jcgl.CheckRegZQ','证券反馈信息',1150,700,{checkregid :record.data.checkregid},g_contextpath);
	impTest(record.data.checkregid, 'crzq1');
}

function downLoadP(){
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var ids = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			ids = ids + record.data.checkregid+",";
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("请勾选一行数据！");
		return null;
	}
	document.getElementById('frameid').src="RegCheckServlet?method=downloadPFile&checkregid="+encodeURI(encodeURI(ids));
}
function downLoadP2(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	document.getElementById('frameid').src="RegCheckServlet?method=downloadPFile_bs&checkregid="+encodeURI(encodeURI(record.data.checkregid));
}

<odin:menu property="fMenu_m4">
<odin:menuItem text="参股信息导入" property="impGscbBtn"  handler="impGscbxx" ></odin:menuItem>
<odin:menuItem text="担任高级职务信息导入" property="impGszwBtn"  handler="impGszwxx"  isLast="true"></odin:menuItem>
</odin:menu>
</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="checkregid"/>
<odin:hidden property="regname"/>
<odin:groupBox title="查询条件">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="regname1" label="名称" ></odin:textEdit>
		<odin:textEdit property="regno1" label="批次编号" maxlength="8" ></odin:textEdit>
		<odin:textEdit property="xm" label="人员姓名" ></odin:textEdit>
		<odin:textEdit property="sfz" label="身份证号码" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="批次信息"
	autoFill="true" pageSize="50" bbarId="pageToolBar" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="checkregid" />
		<odin:gridDataCol name="ckrow"/>
		<odin:gridDataCol name="regno"/>
		<odin:gridDataCol name="regname"/>
		<odin:gridDataCol name="checkdate"/>
		<odin:gridDataCol name="regstatus"/>
		<odin:gridDataCol name="groupid"/>
		<odin:gridDataCol name="groupname"/>
		<odin:gridDataCol name="reguser"/>
		
		<odin:gridDataCol name="crjxx"/>
		<odin:gridDataCol name="fcxx"/>
		<odin:gridDataCol name="sybxxx"/>
		<odin:gridDataCol name="gpjjxx"/>
		<odin:gridDataCol name="gsxx1"/>
		<odin:gridDataCol name="gsxx2"/>
		
		<odin:gridDataCol name="userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="checkregid" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="regstatus" width="110" hidden="true" editor="text" header="状态" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="ckrow" checkBoxClick="checkClicktable" width="30" header="" editor="checkbox"  edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="regname" width="190" header="名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="checkdate" width="140" header="检查日期" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="regno" width="140" header="编号" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="gsxx1" width="100" header="参股反馈" editor="text" edited="false" renderer="downFunction" align="center"/>
		<odin:gridEditColumn2 dataIndex="gsxx2" width="100" header="担任高级职务反馈" editor="text" edited="false" renderer="downFunction" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="groupname" width="140" header="申请机构" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="reguser" width="140" header="申请人" editor="text" edited="false"  isLast="true" align="center"/>
		<%-- <odin:gridEditColumn2 dataIndex="op" width="140" header="操作" editor="text" renderer="opFunction" isLast="true" edited="false"  align="center" isLast="true"  /> --%>
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>检查管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="人员列表" icon="image/icon021a6.gif" handler="personlist"  id="personlist"/>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="对象信息表" icon="images/tb_2.jpg" handler="downLoadP" id="personlistExcel"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="对象信息表(省)" icon="images/tb_2.jpg" handler="downLoadP2" id="personlistExcel2"/>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="出入境反馈" icon="images/icon/table.gif" handler="impCrjxx" id="crjfk"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="房产反馈" icon="images/icon/table.gif" handler="impFcxx" id="fcfk"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="投资性保险反馈" icon="images/icon/table.gif" handler="impCbxx" id="cbfk"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="股票基金反馈" icon="images/icon/table.gif" handler="impZqxx" id="zqfk"/>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="参股信息导入" icon="images/icon/table.gif" handler="impGscbxx" id="impGscbxx"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="担任高级职务信息导入" icon="images/icon/table.gif" handler="impGszwxx" id="impGszwxx"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" handler="infoSearch" />
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>
<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>

<script type="text/javascript">

function impTest(id, type) {
	var p = "&checkregid="+id + "&filetype="+type;
	$h.showWindowWithSrc('simpleExpWin2', g_contextpath
		+ "/pages/xbrm/jcgl/DataVerify2.jsp?i=1"+p,'导入窗口',530,190);	
}
function getRecord(gridid){
	var grid=Ext.getCmp(gridid);
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var record = null;
	for(var i=0;i<rowCount;i++) {
		var record2=store.getAt(i);
		if(record2.data.ckrow == true){
			record = record2;
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("请一行勾选数据！");
		return null;
	}
	if(count>1){
		odin.alert("请一行勾选数据！");
		return null;
	}
	return record;
}

function checkClicktable(row,col,dataIndex,gridid){
	/* var grid=Ext.getCmp(gridid);
	var store = grid.getStore();
	var rowCount = store .getCount();
	for(var i=0;i<rowCount;i++) {
		if(row == i) {
			var record=store .getAt(i);
		   // record.set('ckrow',true);//改变checkbox属性,设置选中
		} else {
			var record=store .getAt(i);
		    record.set('ckrow',false);//改变checkbox属性,设置选中
		}
	} */
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('checkregid').value = rc.data.checkregid;
		document.getElementById('regname').value = rc.data.regname;
	});
	
	/* memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('checkregid').value = rc.data.checkregid;
		
		$h.openPageModeWin('loadadd','pages.xbrm.jcgl.Personlist','人员信息列表',1150,700,{checkregid :rc.data.checkregid},g_contextpath);

	}); */
	
});
function personlist(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	$h.openPageModeWin('loadadd','pages.xbrm.jcgl.PersonGrid','人员信息列表',700,500,{checkregid :record.data.checkregid},g_contextpath);

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


