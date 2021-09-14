<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<%
RMHJ r = new RMHJ();
request.setAttribute("RMHJ", r.rmhj);
request.setAttribute("RYFL", r.ryfl);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
body{
margin: 1px;overflow: auto;
font-family:'宋体',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*日期宽  */
.GBx-fieldset .x-form-trigger{right: 0px;}/*图标对其  */
.GBx-fieldset input{width: 100%!important;}
.GBx-fieldset .x-fieldset{padding-bottom: 0px;margin-bottom: -12px;margin-top: 12px}

.GBx-fieldset .x-fieldset-bwrap{overflow-y: auto;}

.marginbottom0px .x-form-item{margin-bottom: 0px;}

.marginbottom0px table,.marginbottom0px table tr th,.marginbottom0px table tr td
{ border:1px solid #74A6CC; padding: 3px; border-right-width: 0px;  }

.marginbottom0px table
{line-height: 25px; text-align: center; border-collapse: collapse;border-right-width: 1px;margin-bottom:20px;}   

.marginbottom0px .x-form-item tr td{
	border:0px;
}
.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
}

.bh{ display: none; }
.tbh{ display: none; }
.comboh{cursor: pointer!important;background:none!important;background-color:white!important;}
.aclass{font-size: 12px; padding-left: 3px!important;line-height: 30px;}

TEXTAREA.x-form-field{overflow-y:auto;}

.x-grid3-row TD{height: 28px;line-height: 28px;vertical-align: middle;}
.x-grid3-cell-inner{padding-top: 0px;}
.x-tip-header .x-tool{background-image: none;}
</style>
<script type="text/javascript">
//附件上传组件延迟加载
var fnSet={};
$h.ready = function(f,id){
	fnSet[id] = f;
}
var gridJsonStore = {}

function doAddPerson(){//增加人员
	var rbId = document.getElementById('rbId').value;
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	var dc005 = document.getElementById('dc005').value;
	if(rbId==''){
		$h.alert('系统提示','无批次信息！');
		return;
	}
	$h.openWin('findById321','pages.xbrm.SelectPersonByName','按姓名/身份证查询 ',820,520,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'任免人员',
		rbId:rbId,cur_hj:cur_hj,cur_hj_4:cur_hj_4,dc005:dc005});
			
	
}

function yjsc(){
	var rbId = document.getElementById('rbId').value;
	
	if(rbId==''){
		$h.alert('系统提示','无批次信息！');
		return;
	}
	$h.openWin('yijianshengcheng','pages.xbrm.Yjsc','调配方案 ',560,560,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'任免人员',
		rbId:rbId,cur_hj:'${RMHJ.DONG_YI}'});
}

function infoDelete(){//移除人员
	var js0100 = document.getElementById('js0100').value;
	var a0101 = document.getElementById('a0101').value;
	if(js0100==''){
		$h.alert('系统提示','请选择人员！');
		return;
	}
	Ext.Msg.buttonText.yes = '删除人员';
	Ext.Msg.buttonText.no = '移除人员';
	Ext.Msg.buttonText.cancel='取消';
	$h.confirm3btn("系统提示：",'操作对象：'+a0101+'！<br/>点击【删除人员】，将删除该人员的所有纪实记录以及附件；<br/>'+
	'点击【移除人员】，将从该环节移除人员，纪实记录和附件不会被删除。',450,function(id) { 
		if("yes"==id){
			radow.doEvent('allDelete',js0100);
		}if("no"==id){
			radow.doEvent('hjDelete',js0100);
		}else{
			return false;
		}		
	});
}
function updateNRM(){//更新至拟任免信息集
	Ext.Msg.buttonText.ok = '确定更新';
	//Ext.Msg.buttonText.no = '移除人员';
	Ext.Msg.buttonText.cancel='取消';
	$h.confirm("系统提示：",'将该批次下所有人员的拟任免信息更新至基础信息；更新之后，若还有对人员的拟任免信息有调整的，系统将自动更新基础信息。',450,function(id) { 
		if("ok"==id){
			radow.doEvent('updateNRM');
		}if("cancel"==id){
			return false;
		}else{
			return false;
		}		
	});
}
//按姓名查询
function queryByNameAndIDS(list){
	radow.doEvent('queryByNameAndIDS',list);
}
function doDC(){//调配类别维护
	//radow.doEvent('doDC');
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','类别维护页面',820,650,{rb_id:rbid},g_contextpath);
	
}
function getCheckList2(index){
	
}
function getCheckList(gridId,fieldName,obj){
	
}

function exportSheets(){
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('ExportSheets','pages.xbrm.ExportSheets','表样输出',1020,620,{rb_id:rbid},g_contextpath);
	
}
function dc001Select(record,index){
	Ext.getCmp('gridcq').stopEditing(false)
}

var yjrenderer = function(){
	var i = 0;
	var cb = {};
	var ret = {
			
		rd : function(value, params, record, rowIndex, colIndex, ds, colorExp){
	
			//return "<div style=' background-position: -19px -8px;width:31px;height:30px; background-image: url(icos/yujing2.jpg)'></div>";
			var imgsrc = '';
			if(record.data.js0119=='1'){
				imgsrc = "icos/emergency-off.png";
			}else if(record.data.js0119=='2'){
				imgsrc = "icos/emergency-y.png";
			}else{
				imgsrc = "icos/emergency-g.png";
			}
			
			if(value!=''&&value!=null){
				i++;
				//alert(i)
				cb['divrend_'+i] = value;
				//new Ext.ToolTip( { target: 'divrend_'+i, html: value });
				return "<div id='divrend_"+i+"' style='width:30px;height:30px;'><image style='width:24px;height:24px;' src='"+imgsrc+"'/></div>";
			}
			else
				return '';
				
		},
		destroy : function(){
			
			for(var rdid in cb){
				if(!document.getElementById(rdid)){//如果行预警div不存在
					if(!!Ext.getCmp(rdid+"_tip")){//对应的tip存在，则销毁
						//alert(rdid+"_tip")
						Ext.getCmp(rdid+"_tip").destroy();
						delete cb[rdid];
					}
					
				}
				
			} 
			//i = 0; cb = {};
			//alert(i);
			return this; 
		},
		initTip : function(){
			this.destroy();
			//预警信息
			for(var rdid in cb){
				new Ext.ToolTip( { 
					target: rdid, 
					id : rdid+"_tip",
					html: cb[rdid],
					title: '预警原因：</br>',
					trackMouse:true,
					anchor: 'left',
					dismissDelay: 150000,
			        //autoHide: false,
			        closable: true,
			       // draggable:true,
					anchorOffset: 0
				});
			}
			return this;
		}
	}
	return ret;
}();



</script>
<%-- <%@include file="/comOpenWinInit2.jsp" %> --%>
<odin:hidden property="rbId" title="批次id"/>
<odin:hidden property="a0000" title="人员id"/>
<odin:hidden property="a0101" title="姓名"/>
<odin:hidden property="js0100" title="任免人员id"/>
<odin:hidden property="docpath" title="文档地址" />
<odin:hidden property="cur_hj" value="0" title="当前环节"/>
<odin:hidden property="cur_hj_4" value="4_1" title="讨论决定支环节"/>
<odin:hidden property="tplb" value="" title="调配类别"/>
<odin:hidden property="dc005" value="1" title="类别标识"/>
<odin:hidden property="downfile" />
<odin:toolBar property="topbar">
	<odin:buttonForToolBar id="doDC" text="类别维护" handler="doDC" icon="image/icon021a6.gif" />
		<odin:separator></odin:separator>
	<odin:buttonForToolBar text="选择人员" id="doAddPerson" handler="doAddPerson" icon="image/icon021a2.gif" />
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<%-- <odin:fill/>
	<odin:buttonForToolBar text="一键生成" icon="image/icon021a2.gif" isLast="true" handler="yjsc" id="yjsc" /> --%>
	
	<odin:fill/>
	<odin:buttonForToolBar text="模拟干部任免" icon="image/icon021a2.gif" handler="imitate" id="imitate" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="bbarid">
</odin:toolBar>



<odin:toolBar property="bbar" applyTo="bbardiv">
	<!-- 民主推荐 -->
	<odin:buttonForToolBar text="分管市领导的通气材料" cls="bh" icon="images/keyedit.gif" id="btn2" handler="exportExcel"/>
	<odin:buttonForToolBar text="拟推荐考察对象人选征求省纪委意见名单" cls="bh" icon="images/keyedit.gif" id="btn3" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="推荐考察编组汇总表" cls="bh"  id="btn4" /> 
	<odin:buttonForToolBar text="考察任务交代表" cls="bh"  id="btn5" />
	<odin:buttonForToolBar text="推荐情况汇总表" cls="bh"  id="btn6" />--%>
	<!-- 考察 -->
	<odin:buttonForToolBar text="考察对象民义情况汇总表" cls="bh" icon="images/keyedit.gif" id="btn7" handler="exportExcel"/>
	<!-- 讨论决定 -->
	<%-- <odin:buttonForToolBar text="书记专题会干部调配建议方案" cls="bh" icon="images/keyedit.gif" id="btn9" handler="exportExcel"/>
	<odin:buttonForToolBar text="常委会干部调配建议方案" cls="bh" icon="images/keyedit.gif" id="btn10" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="常委会表决票" cls="bh" icon="images/keyedit.gif" id="btn11" handler="exportExcel"/>
	<odin:buttonForToolBar text="常委会表决票情况" cls="bh" icon="images/keyedit.gif" id="btn12" handler="exportExcel"/>
	
	<!-- 任免职 -->
	<odin:buttonForToolBar text="分管市(单位)领导的通气材料" cls="bh" icon="images/keyedit.gif" id="btn17" handler="exportExcel"/>
	<odin:buttonForToolBar text="干部谈话安排方案" icon="images/keyedit.gif" cls="bh"  id="btn14" handler="exportExcel"/>
	
	<odin:buttonForToolBar text="公示" icon="images/keyedit.gif" cls="bh"  id="btn16" handler="exportExcel"/>
	<odin:fill></odin:fill>
	
	<odin:buttonForToolBar text="增加" icon="images/icon/save.gif" id="addRow" handler="addRow"/>
	<odin:buttonForToolBar text="导出对象信息" icon="images/icon/save.gif" id="exportExcel" handler="exportJBXXBExcel"/>
	<odin:buttonForToolBar text="保存" icon="images/icon/save.gif" handler="saveobj" isLast="true"/>
</odin:toolBar>

<div id="tabs" >
	<div style="width: 100%; margin-top: 3px;padding-bottom:20px;">
	  <div style="width: 350px;float: left;" id="gridDiv">
		<odin:editgrid2 property="gridcq"  topBarId="topbar" grouping="true" groupCol="dc001" 
		bbarId="bbarid"  load="selectRow"  isFirstLoadData="false"  width="350"  pageSize="9999" 
		afteredit="ffed" clicksToEdit="false" 
		groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"人\" : \"人\"]})"
			autoFill="false" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="pcheck" /> 
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="js0118" />
				<odin:gridDataCol name="js0119" />
				<odin:gridDataCol name="js0100" />
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
							hideable="false" gridName="gridcq" 
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" menuDisabled="true" /> 
				<%-- <odin:gridColumn dataIndex="a019998" header="预警" width="30" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="a0101" header="姓名" width="65" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="调配类别" sortable="false" width="100" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001_2" menuDisabled="true" header="谈话安排类别" hidden="true" sortable="false" width="180" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001_2" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="预警" renderer="yjrenderer.rd" sortable="false" width="45" edited="false" editor="text" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="预警" renderer="showLink" sortable="false" width="55" edited="false" editor="text" align="center" />
				
				<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" hidden="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
				<odin:gridEditColumn2 dataIndex="havefine" menuDisabled="true" header="正面信息" renderer="showLinkFine" sortable="false" width="60" edited="false" editor="text" align="center" isLast="true"/>
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
	  </div>
			  
	  <div id="peopleInfo" style="float: left;">
	  	<div style="margin-bottom:20px;">
	      <odin:groupBox  property="gp1" title="考核与听取意见及查询委托函">
	        <div style="height: 5px;float: left;width: 100%;padding: 0px;margin: 0px;font-size: 0px;"></div>
	        <div  class="marginbottom0px GBx-fieldset" >
	        	<table style="width: 100%">
						<tr rowspan="2">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">审签类别</td>
							<td width="14%" class="titleTd" colspan="1">听取意见</td>
							<td style="border:0px;style="width:500px;"" colspan="2">
								<odin:checkBoxGroup property="tqyjcheck" data="['name1', '纪检监察机关'],['name2', '公安机关'],['name3', '法院'],['name4', '检察院'],['name5', '卫生计生部门'],['name6', '其他']"/>
							</td>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1">个人事项查核</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="1" label="是" styleClass="x-form-item"></odin:radio>
							</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="0" label="否"></odin:radio>
							</td>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">工作事由</td>
							<odin:textarea property="gzsy" title="工作事由" colspan="2" rows="5"></odin:textarea>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">信息查询委托函其他事由</td>
							<odin:textarea property="qtsy" title="信息查询委托函其他事由" colspan="2" rows="5"></odin:textarea>
						</tr>
					</table>
	        </div>
	      </odin:groupBox>
	    </div>
	     
		<div id="tab-1" class="GBx-fieldset" style="">
			<odin:groupBox property="jbxxb" title="领导干部个人有关事项报告抽查核实对象基本信息表">
		      		<div id="jibenxinxi" class="marginbottom0px" >
						<table style="width:100%;">
						  	<tr>
						  		<td width="8%" class="titleTd">对象</td>
						  		<td width="8%" class="titleTd">姓名</td>
						  		<td width="5%" class="titleTd">性别</td>
						  		<td width="20%" class="titleTd">现任职务</td>
						  		<td width="14%" class="titleTd">身份证号</td>
						  		<td width="14%" class="titleTd">房产查询地</td>
						  		<td width="14%" class="titleTd">填报日期</td>
						  	</tr>
						  	<tbody id="tableID">
						  		<!-- <tr>
							  		<td width="14%">
							  			<input name='tableIDinput' type="text" />
							  		</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
							  	</tr>
							  	<tr>
							  		<td width="14%">
							  			<input name='tableIDinput' type="text" />
							  		</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
						  		</tr> -->
						  	</tbody>
						  				  	
		 			 </table>
					</div>
		     </odin:groupBox>
		</div> 
	  </div>
	</div>
  	<div id="bbardiv"></div>
</div>


<odin:hidden property="js0100s" title="人员选择集合"/>
<!-- 拟任免时时保存 -->
<odin:hidden property="nrmid" title="字段"/>
<odin:hidden property="nrmdesc" title="字段中文"/>
<odin:hidden property="nrmvalue" title="值"/>
<odin:hidden property="objson" title="objson"  />
<script type="text/javascript">
	
	
	function saveobj(){
		var tbody=document.getElementById("tableID");
		var jsonT="[";
		for(var i=0;i<tbody.rows.length;i++){
			jsonT+='{"target":"'+tbody.rows[i].cells[0].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"name":"'+tbody.rows[i].cells[1].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"sex":"'+tbody.rows[i].cells[2].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"zw":"'+tbody.rows[i].cells[3].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"idcard":"'+tbody.rows[i].cells[4].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"house":"'+tbody.rows[i].cells[5].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"date":"'+tbody.rows[i].cells[6].getElementsByTagName("INPUT")[0].value+'"},';
		}
		jsonT=jsonT.substr(0,jsonT.length-1);
		jsonT+="]";
		document.getElementById('objson').value=jsonT;
		radow.doEvent('save');
		
	}
	
	
	function exportJBXXBExcel(){
		radow.doEvent('exportJBXXTExcel');
	}
	
	function addRow(){
		var tr="<tr style=\"height:30px;\">";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "</tr>";
		$("#tableID").append(tr);
	}
	
	function reloadTree(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
		window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
		ShowCellCover("","温馨提示","导出成功！");
		setTimeout(function(){},3000);
	}
	
	function getSelect(){
	//创建数据源[数组数据源]
    var combostore = new Ext.data.SimpleStore({
        fields: ['id', 'name'],
        data: [["4", '全部'], ["4_1", '部会'], ["4_2", '书记专题会'], ["4_3", '常委会干部调配建议方案']]
    });
    //创建Combobox
    var combobox = new Ext.form.ComboBox({
        store: combostore,
        width: 150,
        displayField: 'name',
        valueField: 'id',
        triggerAction: 'all',
        emptyText: '请选择...',
        allowBlank: true,
        blankText: '请选择人员类别',
        editable :false,
        cls:'comboh',
        id:'tljdcombo',
        mode: 'local' ,
        forceSelection :true,
        value: "4" ,
        listeners :{
        	show:function(){
        		$('#tljdcombo').removeClass('x-trigger-noedit').parent().removeClass('x-item-disabled');
        	},
        	render:function(){Ext.getCmp('tljdcombo').hide();}
        }
    });
    //Combobox获取值
    combobox.on('select', function () {
    	if($('#cur_hj').val()=='${RMHJ.TAO_LUN_JUE_DING}'){
    		$('#cur_hj_4').val(combobox.getValue());
    		loadgriddata();
    		//Ext.getCmp('gridcq').store.groupField='company';
    	}
    });
    return combobox;
}
function radiochecked(r){
	if($('#cur_hj').val()=='${RMHJ.TAO_LUN_JUE_DING}'){
		if(r.checked){
			$('#cur_hj_4').val(r.inputValue);
			loadgriddata();
		};
	}
}
function radiochecked2(r){
	if($('#cur_hj').val()=='${RMHJ.REN_MIAN_ZHI}'){
		if(r.checked){
			$('#dc005').val(r.inputValue);
			loadgriddata();
		};
	}
}
function tabclick(obj){
	//$('#tabs ul li a').bind('click', function(event) {
		
    	var $o = $(obj).attr('fn');
    	if(!!fnSet[$o]){
    		fnSet[$o]();//初始化文件组件
	    	delete fnSet[$o];
	    	
    	}
    	if(!!fnSet[$o+"_info"]){
    		fnSet[$o+"_info"]();//初始化文件组件
	    	delete fnSet[$o+"_info"];
    	}
    	
    	var $fn = $(obj).attr('fn');
    	if($fn=='file12'){
    		//职数预审隐藏人员基本信息
    		$( "#personInfo" ).css("display","none");
    	}else{
    		$( "#personInfo" ).css("display","block");
    	}
    	
    	//按钮显示隐藏
    	var $btid = $(obj).attr('btid');
    	if($btid!=null){
    		var arrayid = eval($btid);
    		for(var i=0;i<arrayid.length;i++){
    			if(arrayid[i][1]==1&&!!Ext.getCmp(arrayid[i][0])){
    				Ext.getCmp(arrayid[i][0]).removeClass('bh');
    			}else if(arrayid[i][1]==0&&!!Ext.getCmp(arrayid[i][0])){
    				Ext.getCmp(arrayid[i][0]).addClass('bh');
    			}
    		}
    	}
    	
    	//环节 读取当前环节的人员
    	var $hj = $(obj).attr('hj');
    	if($hj!=$('#cur_hj').val()){
    		$('#cur_hj').val($hj);
    		if(($hj=="${RMHJ.DONG_YI}"&&$('#cur_hj').val()=="${RMHJ.JI_BEN_QING_KUANG}")||($hj=="${RMHJ.JI_BEN_QING_KUANG}"&&$('#cur_hj').val()=="${RMHJ.DONG_YI}")){
    			
    		}else{
    			
    			loadgriddata();
    			
    		}
    		
    		
    	}else{
    		
    	}
    	if($hj=='4'){
    		Ext.getCmp('bbarid').show();
    	}else{
    		Ext.getCmp('bbarid').hide();
    	}
    	if($hj=='5'){
    		Ext.getCmp('bbarid2').show();
    	}else{
    		Ext.getCmp('bbarid2').hide();
    	}
    	
	//});
}


/* Ext.onReady(function(){
	var jbqkGB = $("#jbxxb .x-fieldset-bwrap");
	jbqkGB.css('height',212);
}) */


Ext.onReady(function(){
	
	
	Ext.getCmp('gridcq').getBottomToolbar().insertButton(0,[
		new Ext.Spacer({width:1,height:25}), 
		{boxLabel: '全部',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4', listeners:{check:radiochecked}},
		{boxLabel: '部会', name: 'rb-col',xtype: 'radio', inputValue: '4_1',checked: true ,listeners:{check:radiochecked}},
        {boxLabel: '书记会', name: 'rb-col',xtype: 'radio', inputValue: '4_2',listeners:{check:radiochecked}},
        {boxLabel: '常委会', name: 'rb-col',xtype: 'radio', inputValue: '4_3',listeners:{check:radiochecked}}
	]);
		
	
	new Ext.Toolbar({
		renderTo:Ext.getCmp('gridcq').bbar,
		items:[
			new Ext.Spacer({width:1,height:25}), 
			{boxLabel: '调配类别', name: 'rb-col2',xtype: 'radio', inputValue: '1', checked: true ,listeners:{check:radiochecked2}},
			{boxLabel: '谈话安排类别', name: 'rb-col2',xtype: 'radio', inputValue: '2',listeners:{check:radiochecked2}}
		],
		id:"bbarid2"
	}).hide(); 
	
	Ext.getCmp('bbarid').hide();
	$(function() {
	    $( "#tabs" ).tabs({ 'select': function(event, ui) { tabclick(ui.newTab.closest("li a").context) }});
	    $( "#ulTitle" ).css("display","block");
	    
	});
	
	if(typeof parentParam!= 'undefined'){
		document.getElementById('rbId').value=parentParam.rb_id;
	}else{
		document.getElementById('rbId').value='c42981e1-d876-4d5c-9e85-13eb5bad13eb';
	}
	
	_ulTitleObj = $( "#ulTitle" );
	var viewSize = Ext.getBody().getViewSize();
	var peopleInfoGrid =Ext.getCmp('gridcq');
	
	peopleInfoGrid.setHeight(viewSize.height-60);
		
	var gridobj = document.getElementById('forView_gridcq');
	var grid_pos = $h.pos(gridobj);
	
	$( "#peopleInfo" ).css('width',viewSize.width-grid_pos.left-peopleInfoGrid.getWidth()-6);
	//$( "#peopleInfo" ).css('height',peopleInfoGrid.getHeight());
	
	var gp1 = Ext.get("gp1");//个人信息
	
	var g_hight = gp1.getHeight()//个人信息+拟任免的高
	
	//基本情况
	//var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
	//jbqkGB.css('width',gp1.getWidth()-25);
	//jbqkGB.css('height',peopleInfoGrid.getHeight()-g_hight-39);
	//var offest = 0;
	
	var jbqkGB1 = $("#jbxxb .x-fieldset-bwrap");
	jbqkGB1.css('height',250);
	
	$( "#pdata" ).css('width',gp1.getWidth()-140);
	
	peopleInfoGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		if(rc.data.js0100!=$('#js0100').val()){//环节列表上选择行，同一行的数据则不触发重新查询详细信息
			$('#js0100').val(rc.data.js0100);
			$('#a0101').val(rc.data.a0101);	
			document.getElementById('a0000').value=rc.data.a0000;
			radow.doEvent('peopleInfo',rc.data.a0000);
		}
	});
	
	$('#js0103').parent().parent().parent().attr('width','20%');
	$('#js0102').parent().parent().parent().attr('width','20%');
	$('#js0502').parent().parent().parent().parent().attr('rowspan',2);//考察时间
	$('#js0503').parent().parent().parent().attr('rowspan',2);
	$('#js0504').parent().parent().parent().attr('rowspan',2);
	$('#js0108').parent().parent().parent().attr('width','25%'); 
	$('#js0117').parent().parent().parent().attr('width','25%');
	$('#js0402').parent().parent().parent().attr('width','37%');
	$('#js1002').parent().parent().parent().attr('width','30%');
		
	$h.initGridSort('gridcq',function(g){//一次remove 一次insert 一次sort 一共会触发三次renderer
		fieldsort();
		yjrenderer.initTip();
		var cur_hj = document.getElementById('cur_hj').value;
		var cur_hj_4 = document.getElementById('cur_hj_4').value;
		if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
			cur_hj=cur_hj_4;
		}
		if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
			cur_hj='${RMHJ.DONG_YI}';
		}
		if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
			cur_hj='5_2';
		}
		updateGridReadStore(g.store,cur_hj);
		radow.doEvent('personsort');
	});
	$('#js0108,#js0111,#js0117').each(function(){$(this).css('width',$(this).innerWidth())})
});


function saveNRMValue(t,v,id){
	if($('#js0100').val()!=''){
		$('#nrmid').val(id);
		$('#nrmvalue').val(v);
		$('#nrmdesc').val($('#'+id).attr('titleLabel'));
		radow.doEvent('saveNRMValue');
	} 
	
	
}


function fieldsort(){
	var g =Ext.getCmp('gridcq');
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		g.store.sort('dc001_2','ASC');
	}else{
		g.store.sort('dc001','ASC');
	}
}


function selectRow(a,store){

	var peopleInfoGrid =Ext.getCmp('gridcq');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.a0000==$('#a0000').val()){
				
				$('#js0100').val(rc.data.js0100);
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-10)*27;},100);
				break;
			}
		}
		if(flag){
			//选择第一行
			//peopleInfoGrid.getSelectionModel().selectRow(0,true);
			//peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0);
		}
		
	}else{
		/* if(parseInt($('#cur_hj').val())>1)
			peopleInfoGrid.getGridEl().select('.x-grid3-body',true).elements[0]
			.dom.innerHTML='<a class="aclass" href="javascript:void(0) onclick="radow.doEvent(\'addprevHJ\');">点击加载上个环节的干部信息</a>';
		 */
	}
	saveStore(peopleInfoGrid.store);
	yjrenderer.initTip();
}

//存储表格的store
function saveStore(store,record){
	
	var r = store.reader;
	var data = r.jsonData.data;
	
	if(typeof record!='undefined'){
		//更改后更新所有的缓存
		updateGridJsonStore(record);


	}
	
	var readRecords = {};
	var hj = $('#cur_hj')
    readRecords[store.reader.meta.root] = data;  //"data"      
    readRecords[store.reader.meta.totalProperty] = data.length;//"totalCount"
    var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
		cur_hj=cur_hj_4;
	}
	if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
		cur_hj='${RMHJ.DONG_YI}';
	}
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		cur_hj='5_2';
	}
	gridJsonStore[cur_hj]=readRecords;
}
//其他环节新增时清空动议数据缓存， 
function clearGridJsonStore(cur_hj){
	delete gridJsonStore[cur_hj];
}
//
//更新数据缓存
function updateGridJsonStore(record){
	var data;
	//谈话安排只更新当前自己的类别
	var cur_hj = document.getElementById('cur_hj').value;
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		data = gridJsonStore[cur_hj+"_2"]["data"];
		var js0100 = record.data.js0100;
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data[i]=record.data;
				return;
			}
		}
		return;
	}
	
	for(cur_hj in gridJsonStore){
		data = gridJsonStore[cur_hj]["data"];
		var js0100 = record.data.js0100;
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data[i]=record.data;
				break;
			}
		}
	}
}
//更新reader数据
function updateGridReadStore(store,cur_hj){
	var data;
	data = gridJsonStore[cur_hj]["data"];
	for(var i=0;i<data.length;i++){
		data[i]=store.getAt(i).data;
	}
	
}
//更新数据缓存
function deleteGridJsonStoreRecord(js0100){
	var data;
	for(var cur_hj in gridJsonStore){
		data = gridJsonStore[cur_hj]["data"];
		
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data.splice(i,1);//删除这条数据
				break;
			}
		}
	}
}
//加载数据，若已有缓存，则加载缓存，否则请求后台
function loadgriddata(){
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
		cur_hj=cur_hj_4;
	}
	if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
		cur_hj='${RMHJ.DONG_YI}';
	}
	
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var  columnModel = peopleInfoGrid.getColumnModel(); 
	
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		cur_hj='5_2';
		columnModel.setHidden(4,false);
		columnModel.setHidden(3,true);
		peopleInfoGrid.store.groupField='dc001_2';
	}else{
		columnModel.setHidden(4,true);
		columnModel.setHidden(3,false);
		peopleInfoGrid.store.groupField='dc001';
	}
	
	fieldsort();
	
	if(!!gridJsonStore[cur_hj]){
		peopleInfoGrid.store.loadData(gridJsonStore[cur_hj], false);
	}else{
		radow.doEvent('gridcq.dogridquery');
	}
}
//右下groupbox框调整
function adjustGB(oriObj,refObj,offset){
	oriObj.css('width',refObj.width());
	oriObj.css('height',refObj.height()-offset);
}

function adjustGB2(oriObj,refObj,offset){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	oriObj.css('width',refObj.width());
	oriObj.css('height',peopleInfoGrid.getHeight()-39);
}
//下拉框宽度适应td宽
function adjustSelectWidth(id){
	Ext.getCmp(id+'_combo').setWidth($('#tdid_'+id).width()+3);
}

var flength = 0;curfindex=0;
function onUploadSuccess(file, jsondata, response){
	curfindex++;
	updateProgress(curfindex,flength,jsondata.file_name);
	if(curfindex==flength){
		Ext.Msg.hide();
		Ext.example.msg('','保存成功!');
	}
}
function setFileLength(){
	curfindex=0;
	flength = 0;var hz = '';
	for(i=2;i<12;i++){
		if(i<10){
			hz = '0'+i;
		}else{
			hz = i;
		}
		if(!fnSet["file"+hz]){//是否已经初始化
			flength = flength + eval("$('#file"+hz+"').data('uploadify').queueData.queueLength;");
		}
	}
	if(flength>0){
		$h.progress('请等待', '正在上传文件...',null,300);
	}else{
		Ext.Msg.hide();
		Ext.example.msg('','保存成功!');
	}
}
function updateProgress(cur,total,fname){
	if (fname.length > 15) {
		fname = fname.substr(0,15) + '...';
	}
	Ext.MessageBox.updateProgress(cur / total, '正在上传文件:'+fname+'，还剩'+(total-cur)+'个');
}
//文件下载
function download(id){
	
	//下载附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	window.location="PublishFileServlet?method=downloadFile&id="+encodeURI(encodeURI(id));
	
}

function ffed(e,b){
	alert(666)
	var dc003 = e.value;
	var js0100 = e.record.data.js0100;
	var grid =Ext.getCmp('gridcq');
	
	var store = grid.store;
	
	store.commitChanges() 
	//var r = store.reader;
	//var data = r.jsonData.data;
	//var readRecords = {};
   // readRecords[store.reader.meta.root] = data;        
   // readRecords[store.reader.meta.totalProperty] = data.length;                     	                   
    //store.loadData(readRecords, true);
	//grid.view.refresh();
	var dc003array = dc003.split("@@");
	if(dc003array.length==2){
		dc003 = dc003array[1];
		if(dc003=='999'){
			dc003='';
		}
		radow.doEvent('savedata',js0100+"@@@"+dc003+"");
		fieldsort();
		saveStore(store,e.record);
	}else{
		return;
	}
	
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	return false
}

function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}
/*输出 表*/
function exportExcel(obj){
	
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var store = peopleInfoGrid.store;
	var js0100s = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			js0100s = js0100s + rowData.data.js0100 + ",";
		}
	}
	
	document.getElementById("js0100s").value=js0100s;
	
	var rbId=document.getElementById("rbId").value;
	var buttonid = obj.id;
	var buttontext = obj.text;
	//alert(param);
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.QCJS&eventNames=ExpGird';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出干部调配建议方案 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {rbId:rbId,buttonid:buttonid,buttontext:buttontext},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				 //alert(cfg.messageCode)
 					if(0==cfg.messageCode){
 						if("操作成功！"!=cfg.mainMessage){
 							Ext.Msg.alert('系统提示:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}
 	 						
 	 						//console.log(cfg.elementsScript);
 	 						eval(cfg.elementsScript);
 						}else{
							 		 					
 						}
 					}else{
 						Ext.Msg.alert('系统提示:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

//显示明细
function showLink(value, params, record, rowIndex, colIndex, ds) {
	//alert(222)
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0119 = record.get('js0119');
	var js0100 = record.get('js0100');
	
	var src = "";
	var alt = "";
	if("1"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-off.png";
		alt = "红灯预警";
	} else if("2"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-y.png";
		alt = "黄灯预警";
	} else if("3"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-g.png";
		alt = "绿灯预警";
	} else if("4"==js0119){
		<%-- src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "优秀"; --%>
		
		src = "";
		alt = "";
	} 
	
	if(""==src){
		return "";
	}
	
	//要显示最坏情况图标放开这个
	return "<img onclick='showDetail(\""+js0100+"\",\"-1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
	
	//只显示查看明细
	//return "<a href='javascript:showDetail(\""+js0100+"\")'>查看明细</a>";
}

//显示明细
function showLinkFine(value, params, record, rowIndex, colIndex, ds) {
	//alert(111)
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0100 = record.get('js0100');
	
	
	var src = "";
	var alt = "";
	if("1"==value){
		src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "优秀";
	} else {
		src += "";
		alt = "";
	} 
	
	if(""==src){
		return "";
	}
	
	//要显示最坏情况图标放开这个
	return "<img onclick='showDetail(\""+js0100+"\",\"1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
}

function showDetail(js0100,type){
	$h.openWin('yjmx','pages.xbrm.YJMX','预警明细 ',820,520,null,contextPath,window,{js0100:js0100,type:type});
}

//模拟干部任免
function imitate(){
	var grid = Ext.getCmp('gridcq');
	//alert(222)
	var total = grid.getStore().getCount();//数据行数
	var record; //行数据
	var a0000s = ""; //拟任干部名单
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('pcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}
	
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		$h.openWin('mnrm','pages.xbrm.MNRM','模拟任免 ',820,520,null,contextPath,window,{a0000s:a0000s});
	}
}
</script>
