<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}

.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<script type="text/javascript">
function downLoad1(){
	var store=Ext.getCmp('gridright').getStore();
	var rbId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var rbcheck=record.data.rbmcheck;
		var rbm_id=record.data.rbm_id;
		if(true==rbcheck){
			rbId = rbm_id;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有勾选合并批次!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一个合并批次!');
		return;
	}
	document.getElementById("cur_hj_4").value='4_1';
	radow.doEvent('ExpGird',rbId);
}
function downLoad2(){
	var store=Ext.getCmp('gridright').getStore();
	var rbId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var rbcheck=record.data.rbmcheck;
		var rbm_id=record.data.rbm_id;
		if(true==rbcheck){
			rbId = rbm_id;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有勾选合并批次!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一个合并批次!');
		return;
	}
	document.getElementById("cur_hj_4").value='4_3';
	radow.doEvent('ExpGird',rbId);
}

<odin:menu property="fMenu_m1">
<odin:menuItem text="部务会" property="exp1Btn"  handler="downLoad1" ></odin:menuItem>
<odin:menuItem text="常委会" property="exp2Btn"  handler="downLoad2"  isLast="true"></odin:menuItem>
</odin:menu>
</script>

<odin:hidden property="cur_hj" value="0" title="当前环节"/>
<odin:hidden property="cur_hj_4" value="4" title="讨论决定支环节"/>
<odin:hidden property="dc005" />
<odin:hidden property="RMRY" />
<odin:hidden property="rbId" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!--  是否搜索刷新grid的标记 -->
<odin:hidden property="rbmIds" />
<odin:hidden property="docpath"/>

<odin:hidden property="rbmDept" value="01"/>

<odin:toolBar property="rightbar" >
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="合并"  icon="image/merge.png"  id="mergebar" handler="mergehandler" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:toolBar property="leftbar" >
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="干部名单"  icon="images/icon/exp.png" id="downMc" menu="fMenu_m1"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="发文"  icon="image/icon021a6.gif" id="sentDoc" handler="sentDoc" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="拆分"  icon="image/split.png"  id="splitbar" handler="splithandler" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>


<table style="width: 100%;height: 100%;"  cellspacing="0" cellpadding="0">
	<tr style="width: 100%;" align="center">
		<td height="100" style="width:50%;" valign="top">
			<odin:editgrid property="gridleft" bbarId="PageToolBar" topBarId="rightbar" title="待选列表" pageSize="9999"
				autoFill="true" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="rbcheck" />	
					<odin:gridDataCol name="rb_id" />
					<odin:gridDataCol name="rb_name" />
					<odin:gridDataCol name="rb_date" />
					<odin:gridDataCol name="rb_no" />
					<odin:gridDataCol name="rb_org"/>
					<odin:gridDataCol name="rb_applicant"/>
					<odin:gridDataCol name="rb_userid" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="rbcheck" edited="true"
							hideable="false" gridName="gridright" />
					<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="rb_id" />
					<odin:gridColumn dataIndex="rb_name" header="批次名称" width="80" align="center" />
					<odin:gridColumn dataIndex="rb_date" header="申请日期" width="80" align="center" />
					<odin:gridColumn dataIndex="rb_no" header="批次编号" width="80" align="center" />
					<odin:gridColumn dataIndex="rb_org" header="申请机构" width="80" align="center" />
					<odin:gridColumn dataIndex="rb_applicant" header="申请人" width="80" align="center" isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid>
			
		</td>
		<td width="50%;" height="100%;" valign="top">
			<odin:editgrid property="gridright" bbarId="PageToolBar" topBarId="leftbar" title="合并列表" pageSize="9999"
				autoFill="true" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="rbmcheck" />	
					<odin:gridDataCol name="rbm_id" />
					<odin:gridDataCol name="rbm_name" />
					<odin:gridDataCol name="rbm_date" />
					<odin:gridDataCol name="rbm_no" />
					<odin:gridDataCol name="rbm_org"/>
					<odin:gridDataCol name="rbm_applicant"/>
					<odin:gridDataCol name="rbm_userid" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="rbmcheck" edited="true"
							hideable="false" gridName="gridright" />
					<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="rbm_id" />
					<odin:gridColumn dataIndex="rbm_name" header="批次名称" width="80" align="center" />
					<odin:gridColumn dataIndex="rbm_date" header="申请日期" width="80" align="center" />
					<odin:gridColumn dataIndex="rbm_no" header="批次编号" width="80" align="center" />
					<odin:gridColumn dataIndex="rbm_org" header="申请机构" width="80" align="center" />
					<odin:gridColumn dataIndex="rbm_applicant" header="申请人" width="80" align="center" isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid>
		</td>
	</tr>
</table>




<script type="text/javascript">
function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	return false
}

function sentDoc(){
	var store=Ext.getCmp('gridright').getStore();
	var rbId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var rbcheck=record.data.rbmcheck;
		var rbm_id=record.data.rbm_id;
		if(true==rbcheck){
			rbId = rbm_id;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有勾选合并批次!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一个合并批次!');
		return;
	}
	//var rbId='1c866794-6770-4010-a4d9-ef54cef19440';
	$h.openPageModeWin('loadadd','pages.xbrm.MergeSendDoc','合并批次',1000,700,rbId,g_contextpath);
}

var g_contextpath = '<%= request.getContextPath() %>';

function splithandler(){
	var rbmIds='';
	var store=Ext.getCmp('gridright').getStore();
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var rbmcheck=record.data.rbmcheck;
		var rbm_id=record.data.rbm_id;
		if(true==rbmcheck){
			rbmIds=rbmIds+rbm_id+",";
		}
	}
	if(''==rbmIds){
		$h.alert('系统提示','没有勾选合并批次!',null,200);
		return;
	}else{
		Ext.Msg.confirm("系统体验","你确定要拆分此合并批次吗？",function(id){
			if("yes"==id){
				document.getElementById("rbmIds").value=rbmIds;
				radow.doEvent("splitrbm");
			}else{
				return;
			}	
		})
	}
}


function f5rightgird(){
	radow.doEvent('gridleft.dogridquery');
	radow.doEvent('gridright.dogridquery');
}

function mergehandler(){
	var store=Ext.getCmp('gridleft').getStore();
	var rbIds='';
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var rbcheck=record.data.rbcheck;
		var rb_id=record.data.rb_id;
		if(true==rbcheck){
			rbIds=rbIds+rb_id+",";
		}
	}
	if(''==rbIds){
		$h.alert('系统提示','没有勾选批次!');
		return;
	}else{
		$h.openPageModeWin('loadadd','pages.xbrm.AddMergeJSGL','合并批次',580,300,rbIds,g_contextpath);
	}
}

Ext.onReady(function(){
	
	var viewSize = Ext.getBody().getViewSize();
	var gridleft = Ext.getCmp('gridleft');
	gridleft.setHeight(viewSize.height-12);
	var gridright = Ext.getCmp('gridright');
	gridright.setHeight(viewSize.height-12);
	Ext.get('commForm').setWidth(viewSize.width);
	gridleft.setWidth(viewSize.width/2);
	gridright.setWidth(viewSize.width/2);
	/* memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('checkregid').value = rc.data.checkregid;
		document.getElementById('regname').value = rc.data.regname;
	}); */
	
	gridright.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var rbm_id = rc.data.rbm_id;
		$h.openPageModeWin('loadadd','pages.xbrm.MergeSendDoc','合并批次',1000,700,rbm_id,g_contextpath);

	});
	
});
	
</script>
