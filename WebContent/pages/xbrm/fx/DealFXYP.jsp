<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>

<style>
	.trh{
		display:none;
	}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<script type="text/javascript">
function save22(){
	//拟任职务ID
	var nrzwid = document.getElementById("a5315").value;
	//拟任职务
    var a5315 = document.getElementById("a5315_combo").value.replace(/\s+/g, "");
    //拟任单位ID
    var a0195 = document.getElementById("a0195id").value;
    //拟任单位
    var a0195value=document.getElementById("a0195id_combo").value;
    if (a5315.length == 0) {
        $h.alert('系统提示', '拟任职务不能为空！', null, '220');
        return;
    }
    if (a0195 == "") {
        $h.alert('系统提示', '拟任单位不能为空！', null, '220');
        return;
    }
    document.getElementById('fy2201').value=a0195value;
    document.getElementById('fy2202').value=a0195;
    document.getElementById('fy2203').value=a5315;
    document.getElementById('fy2204').value=nrzwid;
    radow.doEvent('save');
}
function changea0195(){
	document.getElementById('a0201b').value=document.getElementById('a0195id').value;
	document.getElementById('a0195').value=document.getElementById('a5315_combo').value;
}
function zzwrenderer(value, params, record, rowIndex, colIndex, ds){
	if(value == '1'){
		return '是';
	} else {
		return '否';
	}
}
function deleterow(value, params, record, rowIndex, colIndex, ds){
	var fy2200=record.data.fy2200;
	return "<a href=\"javascript:deleteRow2(&quot;" + fy2200 + "&quot;)\">删除</a>";
}
function deleteRow2(fy2200){
	Ext.Msg.confirm('系统提示','你确定要删除选中数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent("delete22row",fy2200);
		}
	})
	
}

function doAddPerson(){//增加人员
	var fyId = document.getElementById('fyId').value;
	var cur_hj = '';
	var cur_hj_4 = '';
	var dc005 = '';
	if(fyId==''){
		$h.alert('系统提示','无批次信息！');
		return;
	}
	$h.openWin('findById321','pages.xbrm.fx.SelectPersonByNameFy','按姓名/身份证查询 ',820,520,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'任免人员',
		fyId : fyId,cur_hj:cur_hj,cur_hj_4:cur_hj_4,dc005:dc005});
}
function infoDelete(){
	var fyId = document.getElementById('fyId').value;
	var a0101s='';
	var fy0100s='';
	var grid=Ext.getCmp('gridcq');
	var store=grid.getStore();
	var rowcount=store.getCount();
	var num=0;
	for(var i=0;i<rowcount;i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var a0101=record.data.a0101;
		var fy0100=record.data.fy0100;
		if(true == pcheck){
			num=num+1;
			if(num==1){
				a0101s=a0101;	
				fy0100s=fy0100;
			}else{
				a0101s=a0101s+','+a0101;
				fy0100s=fy0100s+','+fy0100;
			}
		}
	}
	if(fy0100s.length<1){
		$h.alert('系统提示','请勾选人员！');
		return;
	}
	Ext.Msg.confirm('系统提示','你确定要删除选中人员数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent("delete01row", fy0100s);
		}else{
			return false;
		}
	});
}
//按姓名查询
function queryByNameAndIDS(list){
	radow.doEvent('queryByNameAndIDS',list);
}

function setC(){
	var store=Ext.getCmp('NiRenGrid').getStore();
	var nrId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var checkid=record.data.checkid;
		var fy2200=record.data.fy2200;
		if(true==checkid){
			nrId = fy2200;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有选择拟任职务信息!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一条拟任职务信息!');
		return;
	}
	radow.doEvent('setC',nrId);
}
function cancelC(){
	var grid  = Ext.getCmp('gridComp');
	var store = grid.getStore();
	var ids = '';
	var count = 0;
	for(var i=0; i<store.getCount();i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var fy2500=record.data.fy2500;
		if(true==pcheck){
			ids = ids + fy2500 +",";
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有选择信息!');
		return;
	}
	Ext.Msg.confirm('系统提示','你确定要取消已勾选的数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent('cancelC', ids);
		}else{
			return false;
		}
	});
}

function down(){
	radow.doEvent('down');
}

</script>
<odin:hidden property="fyId"/>
<odin:hidden property="fyUserid"/>
<odin:hidden property="a0201b"/>
<odin:hidden property="a0195"/>
<odin:hidden property="fy2201"/>
<odin:hidden property="fy2202"/>
<odin:hidden property="fy2203"/>
<odin:hidden property="fy2204"/>
<odin:hidden property="tplb"/>
<odin:hidden property="f"/>

<odin:toolBar property="rytopbar">
	<odin:fill/>
	<odin:buttonForToolBar text="选择人员" id="doAddPerson" handler="doAddPerson" icon="image/icon021a2.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator isLast="true"/>
</odin:toolBar>
<odin:toolBar property="comptopbar">
	<odin:fill/>
	
	<odin:buttonForToolBar text="岗位人员设置" id="setC" handler="setC" icon="image/icon021a2.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="解除设置" icon="image/icon021a3.gif" handler="cancelC" id="cancelC" />
	<odin:separator/>
	<odin:buttonForToolBar text="导出" id="down" handler="down" icon="images/icon/exp.png" />
	
	<odin:separator isLast="true"/>
</odin:toolBar>
<table style="width: 100%;">
	<tr>
		<td width="50%">
			<odin:editgrid property="NiRenGrid" sm="row" isFirstLoadData="false" url="/"
				height="280" title="" pageSize="50" enableDragDrop="true" >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="checkid"/>
				<odin:gridDataCol name="a0000"/>
				<odin:gridDataCol name="fy0100"/>
				<odin:gridDataCol name="fy2200"/>
				<odin:gridDataCol name="fy2201"/>
				<odin:gridDataCol name="fy2202"/>
				<odin:gridDataCol name="fy2203"/>
				<odin:gridDataCol name="fy2207"/>
				<odin:gridDataCol name="fy2201b"/>
				<odin:gridDataCol name="delete" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn/>
				<odin:gridEditColumn2 header="选中" width="100" editor="checkbox" dataIndex="checkid"
                                         edited="true"/>
				<odin:gridEditColumn2 header="id" edited="false" dataIndex="fy2200" editor="text"
                                         width="200" hidden="true"/>
				<odin:gridEditColumn2 header="拟任单位(简称)" edited="false" dataIndex="fy2201b" 
                                         editor="text" width="300"/>
				<odin:gridEditColumn2 header="拟任单位ID" edited="false" dataIndex="fy2202"
                   					  editor="text" width="300" hidden="true"/>
				<odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="fy2203" editor="text"
                                         width="300"/>
				<odin:gridEditColumn2 header="主职务" edited="false" dataIndex="fy2207" editor="text"
                                         renderer="zzwrenderer" width="150"/>
				<odin:gridEditColumn  header="操作" width="150" dataIndex="delete"
                                        editor="text" edited="false" renderer="deleterow" isLast="true"/>
               </odin:gridColumnModel>
           </odin:editgrid>
		</td>
		<td width="50%">
			<odin:editgrid property="gridcq" height="300" topBarId="rytopbar"
				load="selectRow"  isFirstLoadData="false"  pageSize="9999" 
				clicksToEdit="false" autoFill="true" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="pcheck" /> 
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="fy0118" />
					<odin:gridDataCol name="fy0119" />
					<odin:gridDataCol name="fy0100" />
					<odin:gridDataCol name="fy0108" />
					<odin:gridDataCol name="fy01001" />
					
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="dc001" />
					<odin:gridDataCol name="dc004" />
					<odin:gridDataCol name="a0104" />
					<odin:gridDataCol name="dc001_2" />
					<odin:gridDataCol name="a0192a"/>
					<odin:gridDataCol name="havefine" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 locked="true" header="selectall" width="40"
								editor="checkbox" dataIndex="pcheck" edited="true"
								hideable="false" gridName="gridcq" menuDisabled="true" /> 
					<odin:gridEditColumn2 dataIndex="a0101" header="姓名" width="65" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 dataIndex="fy0108" header="现任职务" width="200" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 dataIndex="fy0100" header="id" hidden="true" width="45" align="center" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" hidden="true" isLast="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<odin:editgrid property="gridComp" height="400" topBarId="comptopbar"
				load="selectRow"  isFirstLoadData="false"  pageSize="9999" 
				clicksToEdit="false" autoFill="true" >
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="pcheck" /> 
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="fy0100" />
					<odin:gridDataCol name="fy0108" />
					<odin:gridDataCol name="fy2201b"/>
					<odin:gridDataCol name="fy2203"/>
					<odin:gridDataCol name="fy2500"/>
					<odin:gridDataCol name="fy0102"/>
					
					<odin:gridDataCol name="havefine" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 locked="true" header="selectall" width="40"
								editor="checkbox" dataIndex="pcheck" edited="true"
								hideable="false" gridName="gridComp" menuDisabled="true" />
					<odin:gridEditColumn2 header="拟任单位(简称)" edited="false" dataIndex="fy2201b" editor="text" width="250"/>
                    <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="fy2203" editor="text" width="250"/>
					<odin:gridEditColumn2 dataIndex="fy0102" header="姓名" width="150" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 dataIndex="fy0108" header="现任职务" width="250" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 dataIndex="fy2500" header="id" hidden="true" width="45" align="center" editor="text" edited="false" isLast="true"/>
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
<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function downFile(){
	var downfile = document.getElementById('f').value;
	document.getElementById('frameid').src="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	setTimeout(cc,3000);
}
function cc(){
	
}

function selectRow(){
	
}

function approvechange(){
	var val=document.getElementById('fyapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		document.getElementById('fyreportreson').value='';
	}else if(val=='2'){
		$('#reportTr').css('display','none');
		document.getElementById('fyreportreson_combo').value='';
		$('#reportvalueTr').css('display','none');
		document.getElementById('fyreportvalue').value='';
	}
}

function resonchange(){
	var val=document.getElementById('fyreportreson').value;
	if(val=='503'){
		$('#reportvalueTr').css('display','block');
	}else{
		$('#reportvalueTr').css('display','none');
		document.getElementById('fyreportvalue').value='';
	}
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('fyId').value = parentParam.fy_id;
	
	
});

function initselect(){
	var val=document.getElementById('fyapprove').value;
	if(val=='1'){
		$('#reportTr').css('display','block');
		var val1=document.getElementById('fyreportreson').value;
		if(val1=='503'){
			$('#reportvalueTr').css('display','block');
		}
	}
}

function saveCallBack(t){
	//$h.alert('系统提示', t, function(){
		parent.$('#fy_id').val($('#rbId').val());
		parent.$('#fy_name').val($('#rbName').val());
		parent.Ext.getCmp('memberGrid').getStore().reload();
		window.close();
		var fyId = document.getElementById('fyId').value;
		parent.$h.openPageModeWin('qcjs','pages.xbrm.fx.DealFXYP','分析研判',1150,800,{fy_id: fyId},g_contextpath);
		
	//});
}
</script>


