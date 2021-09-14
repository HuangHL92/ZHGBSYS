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
String ctxPath=request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
*{margin:0;padding:0;}
.rmcontent {position:relative;margin-left:3px;width:100%;}
.rmcontent li{list-style-type:none;margin:0px;font:12px arial,sans-serif;float:left;position:relative;}
.rmcontent ul{list-style-type:none;margin:0px;}
.rmcontent div{line-height:23px;}
.people-card{width:240px;height:100px;border:1px solid #71AEFF; 
	border-radius:10px;
}
.people-card img{width:46px;height:46px;float:left;position:relative;padding:3px;border:1px;border-radius:50px}
.people-card .col-name{height:20px;width:191px;font:17px arial,sans-serif;color:#0070F8;float:left;position:relative;text-align:left;margin-top:4px}
.col-50{width:30%;float:left;position:relative;height:22px;}
.col-100{width:100%;float:left;position:relative;height:47px;}
.rmcontent>ul>li{float:left;position:relative;}
.step-red{background:url(pages/xbrm/images/red.png); }
.step-green{background:url(pages/xbrm/images/green.png); }
.step-green-head{background:url(pages/xbrm/images/green-head.png); margin-left:5px;}
.step-blue{background:url(pages/xbrm/images/green.png); }
.step-black{background:url(pages/xbrm/images/dark.png); }
.step-body{width:110px;height:99px; background-size: 110px 99px; background-repeat:no-repeat;}
.step-body-head{font:13px 黑体;color:#FFFFFF;margin-left:5px;}
.rm-panel>tbody>tr>td{height:141px;margin-left:0px;}/*background:url(bg.png);*/
/*.rmcontent li {border-left:1px solid #ccc;border-top:1px solid #ccc;float:left;width:102px;height:33px;text-align:center;line-height:33px}*/


body{
margin: 1px;overflow: auto;
font-family:'宋体',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*日期宽  */
/* .GBx-fieldset {display:none;} */
.GBx-fieldset .x-form-trigger{right: 0px;}/*图标对其  */
.GBx-fieldset input{width: 100%!important;}
.GBx-fieldset .x-fieldset{padding-bottom: 0px;margin-bottom: 12px;margin-top: 12px}

.GBx-fieldset .x-fieldset-bwrap{overflow-y: auto;}

.GBxDis{ display:none; }

.marginbottom0px .x-form-item{margin-bottom: 0px;}

.marginbottom0px table,.marginbottom0px table tr th,.marginbottom0px table tr td
{ border:1px solid #74A6CC; padding: 3px; border-right-width: 0px;  }

.marginbottom0px table
{line-height: 25px; text-align: center; border-collapse: collapse;border-right-width: 1px; }   

.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
}
.tip{
	position:absolute;
	z-index: 10;
	left:60px;
	top:-4px;
}
.tip2word{
	position:absolute;
	z-index: 10;
	left:36px;
	top:-4px;
}
.tip7word{
	position:absolute;
	z-index: 10;
	left:96px;
	top:-4px;
}
.bh{ display: none; }
.tbh{ display: none; }
.comboh{cursor: pointer!important;background:none!important;background-color:white!important;}
.aclass{font-size: 12px; padding-left: 3px!important;line-height: 30px;}

TEXTAREA.x-form-field{overflow-y:auto;}

.x-grid3-row TD{height: 28px;line-height: 28px;vertical-align: middle;}
.x-grid3-cell-inner{padding-top: 0px;}
.x-tip-header .x-tool{background-image: none;}

	#saveZS  .x-btn-text{
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #2ae; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px; DISPLAY: block; PADDING-RIGHT: 5px
	}
	#saveZS{
	margin-right:12px;
	}
	
	#saveQG .x-btn-text{
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #2ae; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px; DISPLAY: block; PADDING-RIGHT: 5px
	}
	#saveQG{
	margin-right:12px;
	}

	#qcjs .x-btn-text{
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #2ae; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px; DISPLAY: block; PADDING-RIGHT: 5px
	}
	
	#fwexp .x-btn-text{
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #2ae; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px; DISPLAY: block; PADDING-RIGHT: 5px
	}
	
	#save .x-btn-text{
	width:50px;;CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #00589E; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px; DISPLAY: block;
	}
	#save{
		margin-right:12px;
	} 
	 
	#btn0 .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	#btn0{
	margin-left:12px;
	}
	
	/*  #btn1 .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} */
	#btn6 .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	 #btn4 .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	#btn4{
	margin-left:12px;
	}
	
	
	 #btn5 .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	#btn5{
	margin-left:12px;
	}
	#btn5_1 .x-btn-text{
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	#btn5_1{
	margin-left:12px;
	}
	 #btn21 .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	 
	 #btn22 .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	 
	 #btnts .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	}
	 
	 #tjb .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	 
	 #tjjg .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	 
	 #tjgzfa  .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	 
	 #btnKc .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	#btnKc{
	margin-left:12px;
	} 
	 
	 
	 #btnRmmd .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	
	#btnRmmd{
	margin-left:5px;
	}
	 
	 #btnBjd  .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	 
	 #btnBgd .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	 
	 #btn14 .x-btn-text{
	
	CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	
	#btn14{
	margin-left:12px;
	}
	
	 #btn16 .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	
	#btnss .x-btn-text{
	
		CURSOR: pointer; TEXT-DECORATION: none; BACKGROUND: #FFA500; POSITION: relative; FLOAT: left; COLOR: #fff; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-LEFT: 5px;  PADDING-RIGHT: 5px
	} 
	
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
	var rbId = document.getElementById('rbId').value;
	
	var a0101s='';
	var js0100s='';
	
	var grid=Ext.getCmp('gridcq');
	var store=grid.getStore();
	var rowcount=store.getCount();
	var num=0;
	for(var i=0;i<rowcount;i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var a0101=record.data.a0101;
		var js0100=record.data.js0100;
		if(true == pcheck){
			num=num+1;
			if(num==1){
				a0101s=a0101;	
				js0100s=js0100;
			}else{
				a0101s=a0101s+','+a0101;
				js0100s=js0100s+','+js0100;
			}
		}
	}
	if(js0100s.length<1){
		$h.alert('系统提示','请勾选人员！');
		return;
	}
	$h.openPageModeWin('delWin','pages.xbrm.DelPChoose','移除人员',400,220,{rb_id: rbId,js0100s: js0100s},contextPath);
	
	//var js0100 = document.getElementById('js0100').value;
	//var a0101 = document.getElementById('a0101').value;
	/* if(js0100s.length<3){
		$h.alert('系统提示','请勾选人员！');
		return;
	}
	Ext.Msg.buttonText.yes = '终止';
	Ext.Msg.buttonText.no = '暂缓';
	Ext.Msg.buttonText.cancel='取消';
	$h.confirm3btn("系统提示：",'操作对象：'+a0101s+'！<br/>点击【终止】，将删除该人员的所有纪实记录以及附件；<br/>'+
			'点击【暂缓】，将从该批次移除人员，但是保留人员操作记录。',450,function(id) { 
				if("yes"==id){
					radow.doEvent('allDelete',js0100s);
				}if("no"==id){
					//radow.doEvent('hjDelete',js0100);
					radow.doEvent('setDelete',js0100s);
				}else{
					return false;
				}		
	}); */
}


function allDelete(js0100){
	radow.doEvent('allDelete',js0100);
}


function updateNRM(){//更新至拟任免信息集
	Ext.Msg.buttonText.ok = '确定更新';
	//Ext.Msg.buttonText.no = '移除人员';
	Ext.Msg.buttonText.cancel='取消';
	$h.confirm("系统提示：",'将该批次下所有人员的拟任免信息更新至基础信息；更新之后，若还有对人员的拟任免信息有调整的，系统将自动更新基础信息。',400,function(id) { 
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
	
	$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','类别维护页面',520,650,{rb_id:rbid},g_contextpath);
	
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

function showOrhide(){

	$('#personInfo').slideToggle(1500,function(){
		var isshow=$('#personInfo').is(":visible");
		if(isshow){
			document.getElementById('isshownrm').value='1';//显示为1
		}else{
			document.getElementById('isshownrm').value='2';//隐藏为2
		}
	});
	
}


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
<odin:hidden property="block" value="" title="职数预审显示行数"/>
<odin:hidden property="downfile" />
<odin:hidden property="tabobj" title="tab页标签"/>
<odin:hidden property="checkboxtqyj" title="听取意见"/>
<odin:hidden property="isshownrm" title="显示隐藏拟任免标记" value='1'/>  
<odin:hidden property="meettype" title="会议类型"/>
<odin:hidden property="qcjsjs0100" title="全程纪实人员id"/>
<odin:hidden property="qcjsid" title="全程纪实人员编码"/>
<odin:hidden property="rb_no" title="批次编号"/>
<odin:hidden property="rb_status" title="批次完成情况"/>
<odin:hidden property="msg" value="3" title="发文显示行数"/>
<odin:hidden property="js2204s" value="" title=""/>
<odin:hidden property="js0123" title="拟免职务A0200s"/>
<odin:hidden property="js0111a" />
<odin:hidden property="js0117a" />
<odin:hidden property="jsonstr" />
<odin:hidden property="js0100s_finish" title="需要高亮的js0100"/>
<odin:hidden property="js33row" value="1" title="民主推荐显示行数"/>
<odin:toolBar property="topbar">
	<%-- <odin:buttonForToolBar id="doDC" text="类别维护" handler="doDC" icon="image/icon021a6.gif" />
		<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="选择人员" id="doAddPerson" handler="doAddPerson" icon="image/icon021a2.gif" />
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<%-- <odin:fill/>
	<odin:buttonForToolBar text="一键生成" icon="image/icon021a2.gif" isLast="true" handler="yjsc" id="yjsc" /> --%>
	
	<odin:fill/>

<%-- 	<odin:buttonForToolBar text="模拟干部任免" icon="image/icon021a2.gif" handler="imitate" id="imitate" isLast="true"/>--%>	
		<odin:buttonForToolBar text="一键资料导出" icon="image/icon021a2.gif" handler="filedown" id="filedown" isLast="true"></odin:buttonForToolBar>

</odin:toolBar>
<odin:toolBar property="bbarid">
</odin:toolBar>

<odin:toolBar property="bbar" applyTo="bbardiv">
	<odin:buttonForToolBar text="干部调配建议方案" cls="bh" id="btn0" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="动议干部名单"   cls="bh" id="btn1" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="导出任免表Lrmx"  cls="bh" id="btn6" handler="expLrmxGrid"/>
	<!-- 职数预审 -->
	<odin:buttonForToolBar text="职数预审表" cls="bh" id="btn4" handler="exportExcel"/>
	<!-- 青干预审 -->
	<odin:buttonForToolBar text="预审单（机关部门）" cls="bh" id="btn5" handler="exportExcel"/>
	<odin:buttonForToolBar text="预审单（市州）" cls="bh" id="btn5_1" handler="exportExcel"/>
	<!-- 民主推荐 -->
	<odin:buttonForToolBar text="分管市领导的通气材料" cls="bh" id="btn2" handler="exportExcel"/>
	<odin:buttonForToolBar text="拟推荐考察对象人选征求省纪委意见名单" cls="bh" id="btn3" handler="exportExcel"/>
				
	<!-- 组织考察 -->
	<odin:buttonForToolBar text="组织考察表" cls="bh" id="btnKc" handler="exportExcel"/>
	<!-- 讨论决定 -->
	<!-- 讨论任免干部名单 -->
	<odin:buttonForToolBar text="讨论任免干部名单" cls="bh" id="btnRmmd" handler="exportExcel"/>

	<!-- 表决单 -->
	<odin:buttonForToolBar text="表决单" cls="bh" id="btnBjd" handler="exportExcel"/>

	<!-- 报告单 -->
	<odin:buttonForToolBar text="报告单" cls="bh" id="btnBgd" handler="exportExcel"/>
	
	<%-- <odin:buttonForToolBar text="推荐考察编组汇总表" cls="bh"  id="btn4" /> 
	<odin:buttonForToolBar text="考察任务交代表" cls="bh"  id="btn5" />
	<odin:buttonForToolBar text="推荐情况汇总表" cls="bh"  id="btn6" />--%>
	<!-- 考察 -->
	<odin:buttonForToolBar text="考察对象民义情况汇总表" cls="bh" id="btn7" handler="exportExcel"/>
	<!-- 讨论决定 -->
	<odin:buttonForToolBar text="干部调配建议方案" cls="bh" id="btn8" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="书记专题会干部调配建议方案" cls="bh" icon="images/keyedit.gif" id="btn9" handler="exportExcel"/>
	<odin:buttonForToolBar text="常委会干部调配建议方案" cls="bh" icon="images/keyedit.gif" id="btn10" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="常委会表决票" cls="bh" id="btn11" handler="exportExcel"/>
	<odin:buttonForToolBar text="常委会表决票情况" cls="bh" id="btn12" handler="exportExcel"/>
	
	<!-- 考核与听取意见  -->
	<odin:buttonForToolBar text="审签单" cls="bh" id="btn21" handler="importSheet"/>
	<odin:buttonForToolBar text="委托函" cls="bh" id="btn22" handler="authorLetter"/>
	<odin:buttonForToolBar text="推送给干部监督处" cls="bh" id="btnts" handler="tsGBJD"></odin:buttonForToolBar>
	
	
	<!-- 民主推荐 -->
	<odin:buttonForToolBar text="推荐表"  id="tjb" cls="bh"/>
<%-- 	<odin:buttonForToolBar text="保存推荐"  id="savetj"  cls="bh"/> --%>
	<odin:buttonForToolBar text="推荐结果"  id="tjjg" handler="tjjg"  cls="bh"/>
	<odin:buttonForToolBar text="推荐方案"  id="tjgzfa" handler="tjgzfa"  cls="bh"/>
		
	<!-- 任免职 -->
	<%-- <odin:buttonForToolBar text="分管市(单位)领导的通气材料" cls="bh" icon="images/keyedit.gif" id="btn17" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="干部谈话安排方案" cls="bh"  id="btn14" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="报到工作方案" cls="bh"  id="btn13" /> --%>
	<odin:buttonForToolBar text="公示" cls="bh" id="btn16" handler="exportExcel"/>
	<odin:buttonForToolBar text="查核成绩" cls="bh"  id="btnss" handler="searchScore"/>
	<odin:fill></odin:fill>
	
	<%-- <odin:buttonForToolBar id="updateNRM" text="更新至拟任免信息集" handler="updateNRM" icon="image/icon021a6.gif" /> --%>
	<odin:buttonForToolBar text="保存职数预审表" id="saveZS" cls="bh"/>
	<odin:buttonForToolBar text="保存青干预审表" id="saveQG" cls="bh"/>
	
	<odin:buttonForToolBar  text="生成归档任免表" id="fwexp" cls="bh" handler="fwexphandler"/>
	<!-- <div style="width:80px;height:20px;font-size:20px;background-color:#EBEBE4;"> -->
	<odin:buttonForToolBar  text="全程纪实" id="qcjs" cls="bh" handler="qcjs"/>
	<odin:buttonForToolBar  text="保存" isLast="true" id="save" handler="save" cls="bh"/>
</odin:toolBar>

<div id="tabs" style="display: none;">
  <ul id="ulTitle" style="_width:100%;display: none;" >
  	
  	<li onclick="setColor(1)"><img id="tip1"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-0" hj="0" fn=""  btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
  		基本情况</a></li>
    <li onclick="setColor(2)"><img id="tip2"  class="tip2word" src="image/u28.png" style="display: none"><a href="#tabs-1" hj="1" fn="file02" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',1],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',1],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	动议</a></li>
    <li onclick="setColor(3)"><img id="tip3"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-11" hj="1" fn="file12" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',1],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',1],['saveQG',0],['save',0],['qcjs',0]]">
    	职数预审</a></li>
    <li onclick="setColor(4)"><img id="tip4"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-12" hj="1" fn="file13" btid="[['btnss',0],['fwexp',0],['btnts',0],['',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',1],['btn5_1',1],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',1],['save',0],['qcjs',0]]">
    	青干预审</a></li>
    <li onclick="setColor(5)"><img id="tip5"  class="tip7word" src="image/u28.png" style="display: none"><a href="#tabs-13" id='khid' hj="3" fn="file99" btid="[['btnss',0],['fwexp',0],['btnts',1],['save',1],['saveQG',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn5',0],['btn5_1',0],['btn2',0],['btn3',0],['btn7',0],['btn4',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',1],['btn22',1]]">
    	查核与听取意见</a></li>
    <li onclick="setColor(6)"><img id="tip6"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-2" hj="2" fn="file19" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',1],['tjb',1],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0],['tjgzfa',1]]">
    	民主推荐</a></li>
    <!-- <li ><a href="#tabs-3" hj="3" fn="file04" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	考察人选确定</a></li> -->
    <li onclick="setColor(7)"><img id="tip7"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-4" hj="6" fn="file14" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',1],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	组织考察</a></li>
    <!-- <li ><a href="#tabs-5" hj="3" fn="file06" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	酝酿</a></li> -->
    <!-- <li ><a href="#tabs-6" hj="4" fn="file07" btid="[['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',1],['btn9',1],['btn10',1],['btn11',1],['btn12',1],['btn14',0],['btn16',0],['btn17',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	讨论决定</a></li> -->
    <li onclick="setColor(8)"><img id="tip8"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-6" hj="4" fn="file07" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btnRmmd',1],['btnBjd',1],['btnBgd',1],['btn21',0],['btn22',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	讨论决定</a></li>
    <li onclick="setColor(9)"><img id="tip9"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-7" hj="5" fn="file08" btid="[['btnss',1],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	任前公示</a></li>
    <li onclick="setColor(10)"><img id="tip10"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-16" hj="5" btid="[['btnss',0],['fwexp',1],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	发文登记</a></li>
    <!-- <li ><a href="#tabs-8" hj="5" fn="file09" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['saveQG',0],['save',1],['qcjs',1]]">
    	任免办理</a></li>
    <li ><a href="#tabs-9" hj="5" fn="file10" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	试用期</a></li>
    <li ><a href="#tabs-10" hj="5" fn="file11" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	其他情况</a></li> -->
    <%-- <li ><odin:button text="表样输出" property="btn3" handler="exportSheets" /></li> --%>
  </ul>
	<div style="width: 100%; margin-top: 3px;">
	  <div style="width: 350px;float: left;" id="gridDiv">
		<odin:editgrid2 property="gridcq"  topBarId="topbar" grouping="true" groupCol="dc001" 
		bbarId="bbarid"  load="selectRow"  isFirstLoadData="false"  width="350"  pageSize="9999" 
		afteredit="ffed" clicksToEdit="false" 
		groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"人\" : \"人\"]})&nbsp;&nbsp;&nbsp;&nbsp;(注：排序请拖拽)"
			autoFill="false" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="pcheck" /> 
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="js0118" />
				<odin:gridDataCol name="js0119" />
				<odin:gridDataCol name="js0100" />
				<odin:gridDataCol name="js01001" />
				
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="dc001" />
				<odin:gridDataCol name="dc004" />
				<odin:gridDataCol name="a0104" />
				<odin:gridDataCol name="dc001_2" />
				<odin:gridDataCol name="a0192a"/>
				<odin:gridDataCol name="finish"></odin:gridDataCol>
				<odin:gridDataCol name="havefine" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 locked="true" header="selectall" width="40"
							editor="checkbox" dataIndex="pcheck" edited="true"
							hideable="false" gridName="gridcq" 
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" menuDisabled="true" /> 
				<%-- <odin:gridColumn dataIndex="a019998" header="预警" width="30" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="a0101" header="姓名" width="60" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="调配类别" sortable="false" width="100" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="调配类别" sortable="false" width="85" edited="false"   editor="text" editorId="dc001" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001_2" menuDisabled="true" header="谈话安排类别" hidden="true" sortable="false" width="180" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001_2" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="预警" renderer="yjrenderer.rd" sortable="false" width="45" edited="false" editor="text" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="预警" renderer="showLink" sortable="false" width="50" edited="false" editor="text" align="center" />
				
				<odin:gridEditColumn2 dataIndex="js01001" header="id" hidden="true" width="45" align="center" editor="text" edited="false" />
				
				<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" hidden="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
				<odin:gridEditColumn2 dataIndex="havefine" menuDisabled="true" header="正面信息" renderer="showLinkFine" sortable="false" width="60" edited="false" editor="text" align="center"/>
				<odin:gridEditColumn2 dataIndex="finish" header="" width="25" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" isLast="true"/>
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
	  </div>
	  <div id="peopleInfo" style="float: left; overflow-y: auto !important;">
	      <div style="margin-left: 3px;" id="personInfo">
	      <odin:groupBoxNew contentEl="all" property="gp1" title="拟任免信息" collapsible="true">
	      	<div id="all">
	      		<div id="pi">
	        	<img alt="个人照片" id="personImg" style="cursor:default; float: left;"  width="100" height="124" src="" onclick="rmbView()">
	        	<div id="pdata" style=" height: 100%; float: left;padding-left: 10px;"></div>
	        	<%-- <div style="position: absolute;right: 30px;top: 95px">
	        		<odin:button text="保存" property="save" ></odin:button>
	        	</div> --%>
	        </div>
	        <div style="height: 5px;float: left;width: 100%;padding: 0px;margin: 0px;font-size: 0px;"></div>
	        <div  class="marginbottom0px GBx-fieldset" >
	        	<odin:hidden property="a5369"/>
	        	<table  style="width: 100%">
	        			<%-- <tr>
							<td class="titleTd" rowspan="1" width="10%" colspan="1">现任职务</td>
							<odin:textarea property="js0108" maxlength="100" title="现任职务" rows="5" colspan="1" />
							<td class="titleTd" colspan="1" width="10%" rowspan="1">拟任职务</td>
							<odin:textarea property="js0111" ondblclick='nrzwtop()' maxlength="50" title="拟任职务" rows="5" colspan="1"  readonly='true' />
							<td class="titleTd" colspan="1" width="10%" rowspan="1">拟免职务</td>
							<odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="拟免职务" rows="5" colspan="1" readonly='true'/>
							<odin:hidden property="a5369"/>
						</tr> --%>
	        			<tr style="display: block;">
							<td  rowspan="1" width="13%" colspan="1"> </td>
							<td  rowspan="1" width="20%" colspan="1"> </td>
							<td  colspan="1" width="13%" rowspan="1"> </td>
							<td  rowspan="1" width="20%" colspan="1"> </td>
							<td  colspan="1" width="13%" rowspan="1"> </td>
							<td  rowspan="1" width="20%" colspan="1"> </td>
						</tr>
						
						<tr>
							<td class="titleTd" rowspan="1" width="10%" colspan="1">现任职务</td>
							<%-- <odin:textarea property="js0108" maxlength="100" title="现任职务" rows="2" colspan="5" /> --%>
							<odin:textEdit property="js0108" maxlength="1000" title="现任职务" colspan="5" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">拟任职务</td>
							<%-- <odin:textarea property="js0111" ondblclick='nrzwtop()' maxlength="50" title="拟任职务" rows="2" colspan="5"  readonly='true' /> --%>
							<odin:textEdit property="js0111" ondblclick='nrzwtop()' maxlength="1000" title="拟任职务" colspan="5"  readonly='true' />
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">拟免职务</td>
							<%-- <odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="拟免职务" rows="2" colspan="5" readonly='true'/> --%>
							<odin:textEdit property="js0117" ondblclick="nmzw('top')" maxlength="1000" title="拟免职务" colspan="5" readonly='true'/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">备注</td>
							<%-- <odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="拟免职务" rows="2" colspan="5" readonly='true'/> --%>
							<odin:textEdit property="js0124" maxlength="1000" title="备注" colspan="5" />
						</tr>
						<tr>
							<td class="titleTd"  colspan="1">任现职时间</td>
							<odin:textEdit property="js0109"  title="任现职时间"   colspan="2" />
							<td class="titleTd" width="20%" colspan="1">现职务层次时间</td>
							<odin:textEdit property="js0110" title="任同级时间"   colspan="2" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">现任单位</td>
							<%-- <odin:select2 queryDelay="false" property="js0115" title="现任单位" colspan="2" /> --%>
							<odin:textEdit property="js0115" title="现任单位" colspan="2" readonly='true'></odin:textEdit>
								
							<td class="titleTd" colspan="1">拟任单位</td>
							<%-- <odin:select2 queryDelay="false" property="js0116" title="拟任单位" colspan="2" /> --%>
							<odin:textEdit property="js0116value" title="拟任单位" readonly='true'></odin:textEdit>
							<odin:hidden property="js0116"/>
						</tr>
	        	</table>
	        	</div>
	      	</div>
	      </odin:groupBoxNew>
		  	      
	      <%-- <odin:groupBox property="gp1" title="拟任免信息">
	       
	      </odin:groupBox> --%>
	      </div>
	      
	     <%-- <div id='togglebtn' align='center' style="margin-top:5px;margin-buttom:2px;height:20px;width:100%;">
	        	<!-- <img id="upimg" src="images/nrmup.png"> -->
	        	<odin:button  property="showOrhide" handler="showOrhide" text="点击隐藏和显示拟任免信息"></odin:button>
	      		
	      </div> --%>
	      
	      <div id="tabs-0" style="float: left;" class="GBx-fieldset GBxDis">
	      	<odin:groupBoxNew contentEl="jibenqingkuang" collapsible="true" property="jbqkGB"  title="基本情况">
	      		<div id="jibenqingkuang" class="marginbottom0px">
					<table style="width: 100%;">
						<tr>
							<td width="13%" class="titleTd" colspan="1">姓名</td>
							<odin:textEdit property="js0102" width="'100%'" title="姓名" colspan="1" readonly="true" />
							<td width="13%" class="titleTd" colspan="1">性别</td>
							<odin:select2 queryDelay="false" property="js0114"  title="性别" codeType="GB2261" readonly="true" colspan="1" />
							<td width="13%" class="titleTd" colspan="1">出生年月</td>
							<odin:textEdit property="js0103" width="'100%'" title="出生年月" colspan="1" readonly="true" />
						</tr>
						<%-- <tr>
							
							<td noWrap="nowrap" align=right><span id="a0194ASpanId" style="FONT-SIZE: 12px">基层工作经历</span>&nbsp;</td>
							<td >
								<table  ><tr>
									<odin:numberEdit property="a0194A"  maxlength="3" width="72" />
									<td><span style="font: 12px">年</span></td>
									<odin:numberEdit property="monthB" maxlength="3" width="72" />
									<td><span style="font: 12px">月</span></td>
								</tr></table>
							</td>
							
						</tr> --%>
						<tr>
							<td class="titleTd" colspan="1">工作时间</td>
							<odin:textEdit property="js0104" width="'100%'" title="工作时间" readonly="true" colspan="1" />
							<td class="titleTd" colspan="1">入党时间</td>
							<odin:textEdit property="js0105" width="'100%'" title="入党时间" readonly="true" colspan="1" />
							<td class="titleTd" colspan="1">学历学位</td>
							<odin:textEdit property="js0106" width="'100%'" title="学历学位" readonly="true" colspan="1" />
						</tr>
						
						
					</table>
				</div>
	      	</odin:groupBoxNew>
	      	<%-- <odin:groupBox property="jbqkGB" title="基本情况"  >
	      		
	      	</odin:groupBox> --%>
	      	<%-- <odin:tab id="extTab1" width="700">
				<odin:tabModel >
					<odin:tabItem title="职务登记" id="extTab1_1"></odin:tabItem>
					<odin:tabItem title="任职资格" isLast="true" id="extTab1_2" ></odin:tabItem>
				</odin:tabModel>
			</odin:tab> --%>
	      </div>
		  <div id="tabs-1" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="dongyi" property="dyGB" title="人员初始提出" collapsible="true">
		  		<div id="dongyi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="22%" class="titleTd" colspan="2">空缺职位</td>
							<td class="titleTd" colspan="2">岗位要求</td>
							<td class="titleTd" colspan="1">配备方向</td>
						</tr>
						<tr>
							<odin:textarea property="js0202" title="空缺职位" maxlength="200" colspan="2" rows="5"/>
							<odin:textarea property="js0203" title="岗位要求" maxlength="500" colspan="2" rows="5"/>
							<odin:textarea property="js0204" title="配备方向" maxlength="500" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td width="70%" class="titleTd" colspan="4">配备建议</td>
							<td width="30%" class="titleTd" colspan="1">备注</td>
							
						</tr>
						<tr>
							<odin:textarea property="js0205"  maxlength="500" title="配备建议" colspan="4" rows="5"/>
							<odin:textarea property="js0206"  maxlength="500" title="备注" colspan="1" rows="5"  />
						</tr>
						
						<tr id="needReportTr">
							<td width="30%" class="titleTd" colspan="1">是否需要事前报告</td>
							<odin:select2 onchange="approvechange()" colspan="4" property="js0208" title="是否需要事前报告" value='2' data="['1','是'],['2','否']"></odin:select2>
						</tr>
						<tr  style="display:none;" id="psreportTr">
							<td width="30%" class="titleTd" colspan="1">报告事项</td>
							<odin:select2 onchange="resonchange()" colspan="4" size="79" property="js0209" codeType="PSYESREASON" value='' title="报告事项" queryDelay="false"></odin:select2>
						</tr>
						<tr style="display:none;" id="psreportvalueTr">
							<td width="30%" class="titleTd" colspan="1">其他事项</td>
							<odin:textarea colspan="4" rows="3" title="其他事项" property="js0210" maxlength="1000"></odin:textarea>
						</tr>
						<tr style="display:none;" id="aproveTr">
							<td width="30%" class="titleTd" colspan="1">领导审批</td>
							<odin:select2 colspan="4" property="js0211" title="领导审批" value='2' data="['1','通过'],['2','未通过']"></odin:select2>
						</tr>
						
						
						<tr>
							<tags:JUpload3 property="file02" label="上传资料" fileTypeDesc="所有文件"  colspan="5"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		    <%-- <odin:groupBox property="dyGB" title="人员初始提出"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  
		  <div id="tabs-11" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="yushen" property="zsysGB" title="职数预审" collapsible="true">
		  		<div id="yushen" class="marginbottom0px">
					<table style="width: 100%">
						
						<tr>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">拟配备干部的地区或单位（含内设机构）</td>
							<td width="10%" class="titleTd" colspan="2">职数层级</td>
							<td width="10%" class="titleTd" colspan="2">核定职数</td>
							<td width="10%" class="titleTd" colspan="2">现配职数</td>
							<td width="10%" class="titleTd" colspan="2">拟免职数</td>
							<td width="10%" class="titleTd" colspan="2">拟配职数</td>
							<td width="12%" class="titleTd" colspan="2">是否超职数</td>
							<td width="15%" class="titleTd" colspan="1" rowspan="2">备注</td>
							<td width="4%" class="titleTd" colspan="1" rowspan="2">清<br>空</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">厅级</td>
							<td class="titleTd" colspan="1">处级</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导<br>职数</td>
							<td class="titleTd" colspan="1">非领<br>导职<br>数</td>
						</tr>
						 <%
						 int rownum = 1;
						 for(int i=1;i<=20;i++){
							    String tr_i = "tr_"+rownum;
							    String td_js1201 = "td_js1201_"+rownum;
							    String js1201_i = "js1201_"+rownum;
							    String changeValue = "changeValue('js1201_"+rownum+"','"+rownum+"')";
							    String td_js1202_1_i = "td_js1202_1_"+rownum;
							    String td_js1202_2_i = "td_js1202_2_"+rownum;
							    String js1202_i = "js1202_"+rownum;
							    String js1202_1 = "js1202_1_"+rownum;
							    String js1202_2 = "js1202_2_"+rownum;
							    String js1203_i = "js1203_"+rownum;
							    String js1204_i = "js1204_"+rownum;
							    String js1205_i = "js1205_"+rownum;
							    String js1206_i = "js1206_"+rownum;
							    String js1207_i = "js1207_"+rownum;
							    String js1208_i = "js1208_"+rownum;
							    String js1209_i = "js1209_"+rownum;
							    String js1210_i = "js1210_"+rownum;
							    String js1211_i = "js1211_"+rownum;
							    String js1212_i = "js1212_"+rownum;
							    String js1213_i = "js1213_"+rownum;
							    String a0195_i  = "a0195_"+rownum;
							    String czsldzschange = "changeSelect('js1211_"+rownum+"','"+rownum+"')";
							    String selectjs1211_i = "selectjs1211_"+rownum;
							    String czsfldzschange = "changeSelect('js1212_"+rownum+"','"+rownum+"')";
							    String selectjs1212_i = "selectjs1212_"+rownum;
							    %>
							<tr id="<%=tr_i %>">
							    <%-- <td colspan="1" id="<%=td_js1201 %>">
							    <tags:rmbPopWinInput property="<%=js1201_i %>" cls="width-80 height-40 no-y-scroll" label="出生地" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="" hiddenValue=""/>
							        <tags:rmbPopWinInput2 property="<%=js1201_i %>"  label="拟配单位" 
							    title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData" 
							    defaultValue="" hiddenValue=""/> 
							    </td>--%>
							    
							    <%-- <tags:PublicTextIconEdit isLoadData="false"  property="<%=js1201_i %>" label2="拟配单位"  codetype="orgTreeJsonData" colspan="1" readonly="true" /> --%> 
							    <%-- <tags:PublicTextIconEdit3  property="a0195" readonly="true" label="统计关系所在单位" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3> --%>
							    <%-- <odin:textEdit  title="单位名称" colspan="1" ></odin:textEdit> --%>
														  	
							   	<tags:PublicTextIconEdit3 onfocus="<%=changeValue %>"  property="<%=js1201_i %>" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3>
								
								<%--  --%>
															  	
							    <td colspan="1" id="<%=td_js1202_1_i %>">
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_1 %>" value="1" checked="checked" />
							    </td>
							    <td colspan="1" id="<%=td_js1202_2_i %>" >
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_2 %>" value="0" />
							    </td>
							    <odin:numberEdit property="<%=js1203_i %>" maxlength="3" width="'100%'" title="核定职数领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1204_i %>" maxlength="3" width="'100%'" title="核定职数非领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1205_i %>" maxlength="3" width="'100%'" title="现配职数领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1206_i %>" maxlength="3" width="'100%'" title="现配职数非领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1207_i %>" maxlength="3" width="'100%'" title="拟免职数领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1208_i %>" maxlength="3" width="'100%'" title="拟免职数非领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1209_i %>" maxlength="3" width="'100%'" title="拟配职数领导职数" colspan="1" />
							    <odin:numberEdit property="<%=js1210_i %>" maxlength="3" width="'100%'" title="拟配职数非领导职数" colspan="1" />
						       <%--  <odin:select2 property="<%=js1211_i %>"  width="'100%'" title="超值数领导职数" colspan="1" codeType="XZ09" value="0"/>
 							    <odin:select2 property="<%=js1212_i %>"  width="'100%'" title="超值数非领导职数" colspan="1" codeType="XZ09" value="0"/> --%>
							  <%--  	<odin:select queryDelay="false" property="<%=js1211_i %>" data="['0','否'],['1','是']" width="'100%'" title="超值数领导职数" colspan="1" value="0"></odin:select>
							    <odin:select queryDelay="false" property="<%=js1212_i %>" data="['0','否'],['1','是']" width="'100%'" title="超值数非领导职数" colspan="1" value="0"></odin:select> --%>
							   	<odin:hidden property="<%=js1211_i %>" value="0" title="超值数领导职数"/>
							   	<td>
								   	<select onchange="<%=czsldzschange %>" id="<%=selectjs1211_i %>">
								   		<option value="0" selected>否</option>
							            <option value="1">是</option>
	        						</select>
							   	</td>
							   	<odin:hidden property="<%=js1212_i %>" value="0" title="超值数非领导职数"/>
							   	<td>
								   	<select onchange="<%=czsfldzschange %>" id="<%=selectjs1212_i %>">
								   		<option value="0" selected>否</option>
							            <option value="1">是</option>
	        						</select>
							   	</td>
							    <odin:textarea property="<%=js1213_i %>"  maxlength="500" title="备注" colspan="1" rows="3"/>
								<td>
								   	<div id="addrowBtn_<%=rownum %>" style="position:relative;margin-left:1px;width:20px;height: 20px;border:0px solid #7b9ebd;background:url(images/back.gif) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="clearArow('<%=rownum %>')">
									</div>
							   	</td>
							</tr>
						 <%
						     rownum++;
						    }
						 %>
						 
						 <tr>
						    <td colspan="1" class="titleTd">干部监督处意见</td>
						   <odin:textarea property="js1214"  maxlength="500" title="干部监督处意见" colspan="14" rows="2" readonly="true"/>
						</tr>
						
						<tr>
						    <td colspan="15" class="titleTd">
						        <div id="addrowBtn" style="position:relative;margin-left:3px;width:120px;border:1px solid #7b9ebd;background:url(images/add.png) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="addArow()">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">增加预审职数</span>
								</div>
						    </td>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		   <%--  <odin:groupBox property="zsysGB" title="职数预审"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  
		  <div id="tabs-13" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="kaoheyijian" property="khyjGB" title="考核与听取意见及查询委托函" collapsible="true">
		  		<div id="kaoheyijian" class="marginbottom0px">
					<table style="width: 100%">
						<tr rowspan="2">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">审签类别</td>
							<td width="14%" class="titleTd" colspan="1">听取意见</td>
							<td style="border:0px;style="width:500px;"" colspan="2">
								<odin:checkBoxGroup  property="tqyjcheck" data="['name1', '纪检监察机关'],['name2', '公安机关'],['name3', '法院'],['name4', '检察院'],['name5', '卫生计生部门'],['name6', '其他']"/>
							</td>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1">个人事项查核</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="1" label="是"></odin:radio>
							</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="2" label="否"></odin:radio>
							</td>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">工作事由</td>
							<odin:textarea property="gzsy" title="工作事由" colspan="2" rows="5" maxlength="700"></odin:textarea>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">信息查询委托函其他事由</td>
							<odin:textarea property="qtsy" title="信息查询委托函其他事由" colspan="2" rows="5" maxlength="700"></odin:textarea>
						</tr>
						<tr>
							<td class="titleTd" colspan="2">干部监督处反馈结果</td>
							<odin:textEdit property="crp010" readonly='true'></odin:textEdit>
						</tr>
						<tr>
							<tags:JUpload3 property="file99" label="上传资料" fileTypeDesc="所有文件"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
						
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		  	<%-- <odin:groupBox property="khyjGB" title="考核与听取意见及查询委托函">
	      		
	      	</odin:groupBox> --%>
		  </div>

		  <div id="tabs-12" style="float: left;" class="GBx-fieldset GBxDis" >
		  		<odin:groupBoxNew contentEl="qinggan" property="qgysGB" title="设省直机关企事业单位厅级青年干部选配专项预审" collapsible="true">
		  			<div id="qinggan" class="marginbottom0px">
                        <table style="width: 100%">
                            <tr>
                                <td class="titleTd" colspan="2" rowspan="2">项目</td>
                                <td class="titleTd" colspan="7">职级</td>

                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1" width="7%">正厅级</td>
                                <td class="titleTd" colspan="1" width="7%">副厅级</td>
                                <td class="titleTd" colspan="1" width="7%">巡视员</td>
                                <td class="titleTd" colspan="1" width="7%">副巡<br/>视员</td>
                                <td class="titleTd" colspan="1" width="7%">合计</td>
                                <td class="titleTd" colspan="1" width="7%">青年干部占比</td>
                                <td class="titleTd" colspan="1" width="58%">备注</td>

                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3" width="10%">现配备<br/>情况</td>
                                <td class="titleTd" colspan="1" width="10%">人数</td>
                                <odin:numberEdit property="r1c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r1c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                                <td rowspan="6">
                                    <textarea id="r1c7" name="r1c7" rows="12" cols="46"></textarea>
                                </td>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45岁以下人数</td>
                                <odin:numberEdit property="r2c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r2c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">80后人数</td>
                                <odin:numberEdit property="r3c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r3c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3">拟提拔<br/>情况</td>
                                <td class="titleTd" colspan="1">人数</td>
                                <odin:numberEdit property="r4c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r4c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45岁以下人数</td>
                                <odin:numberEdit property="r5c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r5c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1">80后人数</td>
                                <odin:numberEdit property="r6c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r6c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">省委组织部干部规划办审核意见
                                </td>
                                <odin:textarea property="js1396" maxlength="500"
                                               title="省委组织部干部规划办审核意见" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">省委组织部部领导审批意见
                                </td>
                                <odin:textarea property="js1397" maxlength="500"
                                               title="省委组织部部领导审批意见" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">审核意见<br/>落实情况
                                </td>
                                <odin:textarea property="js1398" maxlength="500"
                                               title="审核意见落实情况" colspan="8" rows="4"/>
                            </tr>


                        </table>
                    </div>
		  		</odin:groupBoxNew>
               <%--  <odin:groupBox property="qgysGB" title="设区市市级机关企事业单位县处级青年干部选配专项预审">
                    
                </odin:groupBox> --%>
				
				<odin:groupBoxNew contentEl="qinggan1" property="qgysGB2" title="市（州）厅级青年干部选配专项预审" collapsible="true">
					<div id="qinggan1" class="marginbottom0px">
                        <table style="width: 100%">
                            <tr>
                                <td class="titleTd" colspan="2" rowspan="2">项目</td>
                                <td class="titleTd" colspan="7">职级</td>

                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1" width="7%">正厅级</td>
                                <td class="titleTd" colspan="1" width="7%">副厅级</td>
                                <td class="titleTd" colspan="1" width="7%">巡视员</td>
                                <td class="titleTd" colspan="1" width="7%">副巡<br/>视员</td>
                                <td class="titleTd" colspan="1" width="7%">合计</td>
                                <td class="titleTd" colspan="1" width="7%">青年干部占比</td>
                                <td class="titleTd" colspan="1" width="58%">备注</td>

                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3" width="10%">现配备<br/>情况</td>
                                <td class="titleTd" colspan="1" width="10%">人数</td>
                                <odin:numberEdit property="t2r1c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r1c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                                <td rowspan="6">
                                    <textarea id="t2r1c7" name="t2r1c7" rows="12" cols="46"></textarea>
                                </td>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45岁以下人数</td>
                                <odin:numberEdit property="t2r2c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r2c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">80后人数</td>
                                <odin:numberEdit property="t2r3c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r3c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3">拟提拔<br/>情况</td>
                                <td class="titleTd" colspan="1">人数</td>
                                <odin:numberEdit property="t2r4c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r4c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45岁以下人数</td>
                                <odin:numberEdit property="t2r5c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r5c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1">80后人数</td>
                                <odin:numberEdit property="t2r6c1" maxlength="5" width="'100%'"
                                                 title="正厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c2" maxlength="5" width="'100%'"
                                                 title="副厅级" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c3" maxlength="5" width="'100%'"
                                                 title="巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c4" maxlength="5" width="'100%'"
                                                 title="副巡视员" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c5" maxlength="5" width="'100%'"
                                                 title="合计" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r6c6" maxlength="5" width="'100%'"
                                                 title="青年干部占比" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">省委组织部干部规划办审核意见
                                </td>
                                <odin:textarea property="js9996" maxlength="500"
                                               title="省委组织部干部规划办审核意见" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">省委组织部部领导审批意见
                                </td>
                                <odin:textarea property="js9997" maxlength="500"
                                               title="省委组织部部领导审批意见" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">审核意见<br/>落实情况
                                </td>
                                <odin:textarea property="js9998" maxlength="500"
                                               title="审核意见落实情况" colspan="8" rows="4"/>
                            </tr>


                        </table>
                    </div>
				</odin:groupBoxNew>
                <%-- <odin:groupBox property="qgysGB2" title="县（市、区）县处级青年干部选配专项预审">
                    
                </odin:groupBox> --%>
		  </div>

		  <div id="tabs-2" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="minzhutuijian" property="mztjGB" title="民主推荐" collapsible="true">
		  		<div id="minzhutuijian" class="marginbottom0px">
	      			<table style="width: 100%">
						<tr>
						    <td width="14.28%" class="titleTd" colspan="1">推荐职位</td>
							<odin:textEdit property="js2002" width="'100%'" title="推荐职位" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">推荐人选范围</td>
							<odin:textEdit property="js2003" width="'100%'" title="推荐人选范围" colspan="1" />					
						</tr>
						<tr>
							<td class="titleTd" colspan="1">推荐人选具体要求</td>
							<odin:textarea property="js2004"  title="推荐人选具体要求" colspan="1" rows="5"/>				
						</tr>
					
						<tr>
							<td class="titleTd" colspan="1" >参加推荐对象</td>
							<odin:textarea property="js2005"  title="参加推荐对象" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >推荐程序</td>
							<odin:textarea property="js2006"  title="推荐程序" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >备注</td>
							<odin:textarea property="js2007" title="备注" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file19" label="上传资料" fileTypeDesc="所有文件"  colspan="6"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
					<br/>
	      			<table width="100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">限推人数</td>
							<td width="12%" class="titleTd" colspan="1">推荐时间</td>
							<td width="10%" class="titleTd" colspan="1">参加人数</td>
							<td  class="titleTd" colspan="1">其他</td>
						</tr>
						<tr>
							<odin:textEdit  property="tjrs" colspan="1" />
							<odin:dateEdit format="Ymd" property="tjsj" title="推荐时间" colspan="1" />
							<odin:numberEdit property="tjcjrs" maxlength="5" width="'100%'" title="参加人数" colspan="1" />
							<odin:textEdit  property="tjother" colspan="1" />
						</tr>
	      			</table>
	      			<br>
					<table style="width: 100%">
							<tr id="deletetd">
							<td width="10%" class="titleTd" colspan="1" rowspan="2">姓名</td>
							<td width="13%" class="titleTd" colspan="1" rowspan="2">工作单位及职务</td>
							<td width="28%" class="titleTd" colspan="3">谈话推荐</td>
							<td width="28%" class="titleTd" colspan="3">会议推荐</td>
							<td width="10%" class="titleTd" colspan="1" rowspan="2">竞争性选拔综合情况</td>
							<td width="18%" class="titleTd" colspan="1" rowspan="2">个人推荐或组织推荐情况</td>
						    <td width="3%" class="titleTd" colspan="1" rowspan="2">清空<td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">有效票数</td>
							<td class="titleTd" colspan="1">得票数</td>
							<td class="titleTd" colspan="1">谈话推荐范围</td>
							<td class="titleTd" colspan="1">有效票数</td>
							<td class="titleTd" colspan="1">得票数</td>
							<td class="titleTd" colspan="1">会议推荐范围</td>
						</tr>
						<%
							for(int i=1;i<=20;i++){
								String js3302_i="js3302_"+i;
								String thyxp_i="thyxp_"+i;
								String thtjp_i="thtjp_"+i;
								String thtjfw_i="thtjfw_"+i;
								String hyyxp_i="hyyxp_"+i;
								String hytjp_i="hytjp_"+i;
								String hytjfw_i="hytjfw_"+i;
								String xbzh="xbzh_"+i;
								String zztjqk_i="zztjqk_"+i;
								String js33tr_i="js33tr_"+i;
								String js3312_i="js3312_"+i;
						%>		
							<tr id="<%=js33tr_i%>">
							<odin:textEdit property="<%=js3302_i %>" colspan="1" title="姓名"/>
							<odin:textEdit property="<%=js3312_i %>" colspan="1" title="工作单位及职务"/>
							<odin:numberEdit property="<%=thyxp_i %>" maxlength="5" width="'100%'" title="有效票数" colspan="1" />
							<odin:numberEdit property="<%=thtjp_i %>" maxlength="5" width="'100%'" title="得票数" colspan="1" />
							<odin:textarea property="<%=thtjfw_i %>" maxlength="200" title="谈话推荐范围" colspan="1"></odin:textarea>
							<odin:numberEdit property="<%=hyyxp_i %>" maxlength="5" width="'100%'" title="有效票数" colspan="1" />
							<odin:numberEdit property="<%=hytjp_i %>" maxlength="5" width="'100%'" title="得票数" colspan="1" />
							<odin:textarea property="<%=hytjfw_i %>" maxlength="200" title="会议推荐范围" colspan="1"></odin:textarea>
							<odin:textarea property="<%=xbzh %>"  title="竞争性选拔综合情况" colspan="1" rows="2"/>
							<odin:textarea property="<%=zztjqk_i %>"  title="个人推荐或组织推荐情况" colspan="1" rows="2"/>
							<td colspan="1">
								   	<div id="deleteJs33rowBtn_<%=i %>" style="position:relative;margin-left:1px;width:20px;height: 20px;border:0px solid #7b9ebd;background:url(images/back.gif) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="clearjs33row('<%=i%>')">
									</div>
							 </td>
						
							</tr> 	
								
								
						<%		
							}
						
						%>
						
						<tr>
						    <td colspan="11" class="titleTd">
						        <div id="addJs33" style="position:relative;margin-left:3px;width:120px;border:1px solid #7b9ebd;background:url(images/add.png) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="addJs33row()">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">增加</span>
								</div>
						    </td>
						</tr>
					</table>
										
					<br>
					<table id="tablehide" style="width: 100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">推荐方式</td>
							<td width="18%" class="titleTd" colspan="1">推荐时间</td>
							<td width="18%" class="titleTd" colspan="1">参加人数</td>
							<td width="18%" class="titleTd" colspan="1">得票数</td>
							<td width="18%" class="titleTd" colspan="1">名次</td>
							<td width="18%" class="titleTd" colspan="1">备注</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">会议推荐</td>
							<odin:dateEdit format="Ymd" property="js0302" title="推荐时间" colspan="1" />
							<odin:numberEdit property="js0303" maxlength="5" width="'100%'" title="参加人数" colspan="1" />
							<odin:numberEdit property="js0304" maxlength="5" width="'100%'" title="得票数" colspan="1" />
							<odin:numberEdit property="js0305" maxlength="5" width="'100%'" title="名次" colspan="1" />
							<odin:textEdit property="js0306" maxlength="250"  width="'100%'" title="备注" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">谈话推荐</td>
							<odin:dateEdit format="Ymd" property="js0307" title="推荐时间" colspan="1" />
							<odin:numberEdit property="js0308" maxlength="5" width="'100%'" title="参加人数" colspan="1" />
							<odin:numberEdit property="js0309" maxlength="5" width="'100%'" title="得票数" colspan="1" />
							<odin:numberEdit property="js0310" maxlength="5" width="'100%'" title="名次" colspan="1" />
							<odin:textEdit property="js0311" maxlength="250" width="'100%'" title="备注" colspan="1" />
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		  	<%-- <odin:groupBox property="mztjGB" title="民主推荐"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-3" class="GBx-fieldset">
		  	<odin:groupBox property="kcrxqdGB" title="考察人选确定"  >
		  		<div id="kaocharenxuanqued" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="14%"class="titleTd" colspan="1">下级党委（党组）意见</td>
							<odin:textarea property="js0402" title="下级党委（党组）意见" colspan="1" rows="5"/>
							<td width="11%" class="titleTd" colspan="1">部务会议意见</td>
							<odin:textarea property="js0403" title="部务会议意见" colspan="1" rows="5"/>
						</tr>
						<%-- <tr>
							<tags:JUpload3 property="file03" label="上传资料03" fileTypeDesc="所有文件"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr> --%>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		<%--   <div id="tabs-4" class="GBx-fieldset">
		 	<odin:groupBox property="zzkcGB" title="组织考察"  >
		 		<div id="zuzhikaocha" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td class="titleTd" colspan="1" rowspan="2">考察时间</td>
							<odin:dateEdit format="Ymd" property="js0502" width="'100%'"  title="考察时间" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">个别谈话人数</td>
							<odin:textEdit property="js0503" width="'100%'" title="个别谈话人数" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">是否考察预告</td>
							<odin:select2 codeType="XZ09"  property="js0504" title="是否考察预告" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">民意调查人数
								<input type="text" name="js0505" id="js0505" style="width: 100%;" class=" x-form-text x-form-field">
							</td>
							<td class="titleTd" colspan="1">满意</td>
							<td class="titleTd" colspan="1">比较满意</td>
							<td class="titleTd" colspan="1">不满意</td>
							<td class="titleTd" colspan="1">不了解</td>
						</tr>
						<tr>
							<odin:textEdit property="js0506" width="'100%'" title="满意" colspan="1"/>
							<odin:textEdit property="js0507" width="'100%'" title="比较满意" colspan="1"/>
							<odin:textEdit property="js0508" width="'100%'" title="不满意" colspan="1"/>
							<odin:textEdit property="js0509" width="'100%'" title="不了解" colspan="1"/>
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="1" rowspan="2">民主测评情况</td>
							<td width="13%" class="titleTd" colspan="1">参加测评人数</td>
							<td width="7%" class="titleTd" colspan="1">优秀</td>
							<td width="8%" class="titleTd" colspan="1">称职</td>
							<td width="7%" class="titleTd" colspan="1">基本称职</td>
							<td width="8%" class="titleTd" colspan="1">不称职</td>
							<td width="9%" class="titleTd" colspan="1" rowspan="2">书面征求意见情况</td>
							<td width="9%" class="titleTd" colspan="1">参加人数</td>
							<td width="9%" class="titleTd" colspan="1">同意</td>
							<td width="9%" class="titleTd" colspan="1">不同意</td>
							<td width="9%" class="titleTd" colspan="1">弃权</td>
						</tr>
						<tr>
							<odin:textEdit property="js0510" width="'100%'" title="参加测评人数" colspan="1"/>
							<odin:textEdit property="js0511" width="'100%'" title="优秀" colspan="1"/>
							<odin:textEdit property="js0512" width="'100%'" title="称职" colspan="1"/>
							<odin:textEdit property="js0513" width="'100%'" title="基本称职" colspan="1"/>
							<odin:textEdit property="js0514" width="'100%'" title="不称职" colspan="1"/>
							<odin:textEdit property="js0515" width="'100%'" title="参加人数" colspan="1"/>
							<odin:textEdit property="js0516" width="'100%'" title="同意" colspan="1"/>
							<odin:textEdit property="js0517" width="'100%'" title="不同意" colspan="1"/>
							<odin:textEdit property="js0518" width="'100%'" title="弃权" colspan="1"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >考察中反映的情况</td>
							<odin:textarea property="js0519"  title="考察中反映的情况" colspan="5" rows="5"/>
							<td class="titleTd" colspan="1" >核查情况</td>
							<odin:textarea property="js0520"  title="核查情况" colspan="4" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >考察组意见</td>
							<odin:textarea property="js0521" title="考察组意见" colspan="10" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file05" label="上传资料05" fileTypeDesc="所有文件"  colspan="11"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div> --%>
		    <div id="tabs-4" class="GBx-fieldset GBxDis">
		    <odin:groupBoxNew contentEl="zuzhikaocha" collapsible="true" title="组织考察" property="zzkcGB">
		    	<div id="zuzhikaocha" class="marginbottom0px">
		 			<table style="width: 100%">
		 				<odin:tabLayOut />
		 				<tr>
		 					<td style="width:25%;" class="titleTd" colspan="1">是否集体研究确定考察人选</td>
		 					<td style="width:25%;">
		 						<odin:radio  property="sfjtyj" value="1" label="是"></odin:radio>
		 						<odin:radio  property="sfjtyj" value="0" label="否"></odin:radio>
		 					</td>
		 					<td style="width:25%;" class="titleTd" colspan="1">民主测评优称率%</td>
		 					<odin:numberEdit style="width:80px;" maxlength="5" property="cpycl" title="民主测评优称率%"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">征求意见情况</td>
		 					<odin:textarea property="zqyjqk"  title="征求意见情况" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">纪检（监察）鉴定意见</td>
		 					<odin:textarea property="jjjdyj"  title="纪检（监察）鉴定意见" rows="5" colspan="1"/>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">考察期间有无举报</td>
		 					<odin:textarea property="kcqjjb"  title="考察期间有无举报" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">查核情况</td>
		 					<odin:textarea property="hcqk"  title="查核情况" rows="5" colspan="1"/>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">近三年年度考核情况</td>
		 					<odin:textarea readonly='true'  property="jsnkhqk"  title="近三年年度考核情况" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">干部档案及“三龄两历一身份”审核情况</td>
		 					<td>
		 						<table style="width: 100%;">
									<tr style="height: 15px;">
										<td  style="width: 20%; height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="1" checked="checked" onclick="Check1()">
										</td>
										<td  style="width: 80%; height: 15px;">
											<label style="font-size: 12" >通过</label>
										</td>
									</tr>
									<tr>
										<td style="height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="2" onclick="Check2()">
										</td>
										<td style="height: 15px;">
											<label style="font-size: 12" >不通过</label>
										</td>
									</tr>
									<tr>
										<td style="height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="3" onclick="Check3()">
										</td>
										<td style="height: 15px;">
											<label style="font-size: 12" >暂缓</label>
										</td>
									</tr>
								</table>
		 					</td>
		 					<%-- <odin:textarea property="slllshqk"  title="干部档案及“三龄两历一身份”审核情况" rows="3" colspan="1"/> --%>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">执行事前报告情况</td>
		 					<odin:textarea property="sqbgqk"  title="执行事前报告情况" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">个人有关事项报告查核情况</td>
		 					<odin:textarea property="grsxchqk"  title="个人有关事项报告查核情况" rows="5" colspan="1"/>
		 				</tr>
		 				<tr style="display: none;" id="slllsh_div">
		 					<td style="width:10%;" class="titleTd" colspan="1">干部档案及“三龄两历一身份”审核情况备注</td>
		 					<%-- <td colspan="2">
		 						<table style="border: 0px solid #ffffff;width: 100%;" ><tr style="border: 0px solid #ffffff;">
		 							<odin:textarea property="slllshqk"  title="干部档案及“三龄两历一身份”审核情况" rows="3" colspan="1"/>
		 						</tr></table>
		 					</td> --%>
		 					<odin:textarea property="slllshqk"  title="干部档案及“三龄两历一身份”审核情况" rows="3" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1"></td>
		 					<td colspan="1"></td>
		 					<odin:hidden property="slllshqklx" value="1"/>
		 				</tr>
		 			</table>
					
		 			<br>
					<table style="width: 100%">
						<tr>
						    <td width="14.28%" class="titleTd" colspan="1">考察单位</td>
							<odin:textEdit property="js1401" width="'100%'" title="考察单位" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">考察时间</td>
							<%-- <odin:dateEdit format="Ymd" property="js1402" width="'100%'"  title="考察时间" colspan="1" />	 --%>				
							<%-- <odin:textEdit property="js1402" width="'100%'" title="考察时间" colspan="1" maxlength="8"></odin:textEdit> --%>
							<odin:numberEdit property="js1402" width="'100%'" title="考察时间"  colspan="1" maxlength="8"></odin:numberEdit>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">民主测评、评议范围和要求</td>
							<odin:textarea property="js1403"  title="民主测评、评议范围和要求" colspan="1" rows="5"/>				
						</tr>
						
						<tr>
							<td class="titleTd" colspan="1" >个别谈话对象范围</td>
							<odin:textarea property="js1404"  title="个别谈话对象范围" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >考察组成员</td>
							<odin:textarea property="js1405"  title="考察组成员" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >备注</td>
							<odin:textarea property="js1406" title="备注" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file14" label="上传资料" fileTypeDesc="所有文件"  colspan="11"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
					
				</div>
		    </odin:groupBoxNew>
		 	<%-- <odin:groupBox property="zzkcGB" title="组织考察"  >
		 		
	      	</odin:groupBox> --%>
	      	<odin:groupBoxNew contentEl="kctjreportdiv" collapsible="true" property="kctijianreport" title="考察对象政治体检报告">
	      		<div id="kctjreportdiv" class="marginbottom0px">
	      			<table style="width: 100%">
		 				<odin:tabLayOut />
		 				<tr>
		 					<td class="titleTd" rowspan="2" style="width:10%;" colspan="1">个人自我评价意见</td>
		 					<td class="titleTd" colspan="1" style="width:10%;">主要表现</td>
		 					<odin:textarea maxlength="500" property="js1417" rows="5" colspan="4"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" colspan="1" style="width:10%;">存在不足</td>
		 					<odin:textarea maxlength="500" property="js1418" rows="5" colspan="4"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="7" colspan="1">民主测评结果</td>
		 					<td class="titleTd" style="width:10%;" rowspan="3" colspan="1">正向评价汇总结果</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"好"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"较好"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"一般"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"差"占比(%)</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1419" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1420" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1422" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1423" maxlength="3"></odin:numberEdit>	 					
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:20%;" colspan="1">两项总占比</td>
		 					<odin:numberEdit colspan="1" property="js1421" maxlength="3"></odin:numberEdit>
		 					<td class="titleTd" style="width:20%;" colspan="1">两项总占比</td>
		 					<odin:numberEdit colspan="1" property="js1424" maxlength="3"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="3" colspan="1">反向评价汇总结果</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"不存在"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"偶尔存在"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"一定程度存在"占比(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"存在"占比(%)</td>
		 				</tr>
		 				<tr>
		 					<odin:textarea property="js1425" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1426" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1428" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1429" colspan="1"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:20%;" colspan="1">两项总占比</td>
		 					<odin:numberEdit colspan="1" property="js1427" maxlength="3"></odin:numberEdit>
		 					<td class="titleTd" style="width:20%;" colspan="1">两项总占比</td>
		 					<odin:numberEdit colspan="1" property="js1430" maxlength="3"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" colspan="1">反馈比较集中的问题</td>
		 					<odin:textarea property="js1431" colspan="4" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" colspan="2">支部评价情况</td>
		 					<odin:select2 colspan="4" property="js1432" title="支部评价情况"  data="['1','健康'],['2','亚健康'],['3','不健康']"></odin:select2>
		 				</tr>
		 			</table>
		 			<table style="width: 100%;">
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="5" colspan="1">相关领导评价意见</td>
		 					<td class="titleTd" style="width:10%;" rowspan="2" colspan="1">正向评价汇总结果</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">参评人数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"好"评价数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"较好"评价数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"一般"评价数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"差"评价数</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1433" title="正向参评人数" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1434" title="正向好参评人数" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1435" title="正向较好参评人数" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1436" title="正向一般参评人数" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1437" title="正向差参评人数" maxlength="5"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="2" colspan="1">反向评价汇总结果</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">参评人数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"不存在"人数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"偶尔存在"人数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"一定程度存在"人数</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"存在"人数</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1438" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1439" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1440" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1441" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1442" maxlength="5"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">反应的主要问题</td>
		 					<odin:textarea property="js1443" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="2">考察议情况</td>
		 					<odin:select2 colspan="5" property="js1444" title="考察议情况"  data="['1','健康'],['2','亚健康'],['3','不健康']"></odin:select2>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="5" colspan="1">组织诊情况</td>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">个人有关事项查核情况</td>
		 					<odin:textarea property="js1445" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">征求相关部门意见情况</td>
		 					<odin:textarea property="js1446" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">反应问题合适情况</td>
		 					<odin:textarea property="js1447" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">考察对象政治表现意见</td>
		 					<odin:textarea property="js1448" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">体检结论</td>
		 					<odin:select2 colspan="5" property="js1449" title="考察议情况"  data="['1','健康'],['2','亚健康'],['3','不健康']"></odin:select2>
		 				</tr>
		 			</table>	
	      		</div>
	      	</odin:groupBoxNew>
		  </div>
		  
		  <div id="tabs-5"  class="GBx-fieldset">
		  	<odin:groupBox property="ynGB" title="酝酿"  >
	      		<div id="yunniang" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="20%"class="titleTd" colspan="1">单位党委（党组）意见</td>
							<odin:textarea property="js0602" title="单位党委（党组）意见" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">分管市领导意见</td>
							<odin:textarea property="js0603" title="分管市领导意见" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">双重管理部门意见</td>
							<odin:textarea property="js0604" title="双重管理部门意见" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">纪检监察部门意见</td>
							<odin:textarea property="js0605" title="纪检监察部门意见" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file06" label="上传资料" fileTypeDesc="所有文件"  colspan="2"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <%-- <div id="tabs-6"  class="GBx-fieldset">
		  	<odin:groupBox property="tljdGB" title="讨论决定"  >
	      		<div id="taolunjueding" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td class="titleTd" colspan="1">部务会议研究时间</td>
							<odin:dateEdit format="Ymd" property="js0702" title="部务会议研究时间" colspan="2" />
							<td class="titleTd" colspan="2">市委书记专题会研究情况</td>
							<odin:textarea property="js0703" title="市委书记专题会研究情况" colspan="2" rows="5"/>
							<td class="titleTd" colspan="1">市委常委会研究情况</td>
							<odin:textarea property="js0704" title="市委常委会研究情况" colspan="2" rows="5"/>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">全委会征求意见</td>
							<td width="9%" class="titleTd" colspan="1">参加人数</td>
							<td width="9%" class="titleTd" colspan="1">同意</td>
							<td width="9%" class="titleTd" colspan="1">不同意</td>
							<td width="9%" class="titleTd" colspan="1">弃权</td>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">市委全委会表决情况</td>
							<td width="9%" class="titleTd" colspan="1">参加人数</td>
							<td width="9%" class="titleTd" colspan="1">同意</td>
							<td width="9%" class="titleTd" colspan="1">不同意</td>
							<td width="9%" class="titleTd" colspan="1">弃权</td>
							
						</tr>
						<tr>
							<odin:textEdit property="js0705" width="'100%'" title="参加人数" colspan="1" />
							<odin:textEdit property="js0706" width="'100%'" title="同意" colspan="1" />
							<odin:textEdit property="js0707" width="'100%'" title="不同意" colspan="1" />
							<odin:textEdit property="js0708" width="'100%'" title="弃权" colspan="1" />
							<odin:textEdit property="js0709" width="'100%'" title="参加人数" colspan="1" />
							<odin:textEdit property="js0710" width="'100%'" title="同意" colspan="1" />
							<odin:textEdit property="js0711" width="'100%'" title="不同意" colspan="1" />
							<odin:textEdit property="js0712" width="'100%'" title="弃权" colspan="1" />
						</tr>
						<tr>
							<tags:JUpload3 property="file07" label="上传资料07" fileTypeDesc="所有文件"  colspan="10"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div> --%>
		  <div id="tabs-6"  class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="taolunjueding" property="tljdGB" title="讨论决定" collapsible="true">
		  		<div id="taolunjueding" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">部务会讨论结果</td>
							<td width="12%" class="titleTd" colspan="1">时间</td>
							<td width="5%" class="titleTd" colspan="1">参加人数</td>
							<td width="9%" class="titleTd" colspan="1">同意</td>
							<td width="9%" class="titleTd" colspan="1">不同意</td>
							<td width="9%" class="titleTd" colspan="1">缓议</td>
							<td width="25%" class="titleTd" colspan="1">表决情况</td>
							<td width="30%" class="titleTd" colspan="2">  备注      </td>
						</tr>
						<tr>
							<odin:dateEdit property="bhtljg" width="'100%'" colspan="1"></odin:dateEdit>
							<odin:numberEdit property="js1501" width="'100%'" title="参加人数（部会）" colspan="1" maxlength="5"></odin:numberEdit>
							<odin:textEdit property="js1502" width="'100%'" title="同意（部会）" colspan="1" />
							<odin:textEdit property="js1503" width="'100%'" title="不同意（部会）" colspan="1" />
							<odin:textEdit property="js1504" width="'100%'" title="缓议（部会）" colspan="1" />
							<odin:textarea property="bhbjqk"  title="表决情况" colspan="1" rows="5"/>
							<odin:textarea property="js1509"  title="备注（部会）" colspan="1" rows="5"/>
						</tr>
						<tr id="cwhtitle">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">常委会讨论结果</td>
							<td width="12%" class="titleTd" colspan="1">时间</td>
							<td width="5%" class="titleTd" colspan="1">参加人数</td>
							<td width="9%" class="titleTd" colspan="1">同意</td>
							<td width="9%" class="titleTd" colspan="1">不同意</td>
							<td width="9%" class="titleTd" colspan="1">缓议</td>
							<td width="25%" class="titleTd" colspan="1">表决情况</td>
							<td width="30%" class="titleTd" colspan="2">  备注       </td>							
						</tr>
						<tr id="cwhtr">
							<odin:dateEdit property="cwtljg" width="'100%'" colspan="1"></odin:dateEdit>
							<odin:numberEdit property="js1505" width="'100%'" title="参加人数（常委会）" colspan="1" maxlength="5"></odin:numberEdit>
							<odin:textEdit property="js1506" width="'100%'" title="同意（常委会）" colspan="1" />
							<odin:textEdit property="js1507" width="'100%'" title="不同意（常委会）" colspan="1" />
							<odin:textEdit property="js1508" width="'100%'" title="缓议（常委会）" colspan="1" />
							<odin:textarea property="cwbjqk"  title="表决情况" colspan="1" rows="5"/>
							<odin:textarea property="js1510"  title="备注（常委会）" colspan="1" rows="5"/>
						</tr>
						
						<%-- <tr>
							<odin:textEdit property="js1501" width="'100%'" title="参加人数（部会）" colspan="1" />
							<odin:textEdit property="js1502" width="'100%'" title="同意（部会）" colspan="1" />
							<odin:textEdit property="js1503" width="'100%'" title="不同意（部会）" colspan="1" />
							<odin:textEdit property="js1504" width="'100%'" title="缓议（部会）" colspan="1" />
							<odin:textEdit property="js1505" width="'100%'" title="参加人数（常委会）" colspan="1" />
							<odin:textEdit property="js1506" width="'100%'" title="同意（常委会）" colspan="1" />
							<odin:textEdit property="js1507" width="'100%'" title="不同意（常委会）" colspan="1" />
							<odin:textEdit property="js1508" width="'100%'" title="缓议（常委会）" colspan="1" />
						</tr>
						<tr>
						    <td width="50%" class="titleTd" colspan="1">备注</td>
						    <odin:textarea property="js1509"  title="备注（部会）" colspan="1" rows="5"/>
						    <odin:textEdit property="js1509" width="'100%'" title="备注（部会）" colspan="1" />
						    <td width="50%" class="titleTd" colspan="1" >备注</td>
						    <odin:textarea property="js1510"  title="备注（常委会）" colspan="1" rows="5"/>
						    <odin:textEdit property="js1510" width="'100%'" title="备注（常委会）" colspan="1" />
						</tr>
						 --%>
						<tr>
							<tags:JUpload3 property="file07" label="上传资料" fileTypeDesc="所有文件"  colspan="10"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  
		  	<%-- <odin:groupBox property="tljdGB" title="讨论决定"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-7"  class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="renqiangongshi" property="rqgsGB"  title="任前公示" collapsible="true">
		  		<div id="renqiangongshi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="12%" class="titleTd" colspan="1">公示形式</td>
							<td width="12%" class="titleTd" colspan="1">公示时间</td>
							<td width="12%" class="titleTd" colspan="1">结束时间</td>
							<td width="20%" class="titleTd" colspan="1">公示情况</td>
							<td width="20%" class="titleTd" colspan="1">公示结果</td>
							<!-- <td class="titleTd" colspan="1">得分</td> -->
							<td width="12%" class="titleTd" colspan="1">公示期间有无举报</td>
							<td width="12%" class="titleTd" colspan="1">查核情况</td>
						</tr>
						<tr>
							<odin:textarea property="gsxs"  title="公示形式" maxlength="200" colspan="1" rows="7" />
							<odin:dateEdit format="Ymd" property="js0802"  title="公示时间" colspan="1"  />
							<odin:dateEdit format="Ymd" property="js0809"  title="结束时间" colspan="1"  />
							<odin:textarea property="js0803"  title="公示情况" colspan="1" rows="7" />
							<odin:textarea property="js0804" title="公示结果" colspan="1" rows="7" />
							<%-- <odin:numberEdit property="js0805" maxlength="5" title="得分" colspan="1" /> --%>
							<odin:hidden property="js0805" title="得分"/>
							<odin:textarea property="gsjywjb" title="公示期间有无举报" colspan="1" rows="7" />
							<odin:textarea property="chqk" title="查核情况" colspan="1" rows="7" />
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="3">法律知识考试成绩</td>
							<odin:numberEdit readonly='true' property="js0810" title="法律知识考试成绩" colspan="4"/>
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="3">党章党纪考试成绩</td>
							<odin:numberEdit readonly='true' property="js0811" title="党章党纪考试成绩" colspan="4"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file08" label="上传资料" fileTypeDesc="所有文件"  colspan="3"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
					  
		  	<%-- <odin:groupBox property="rqgsGB" title="任前公示"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-8"  class="GBx-fieldset">
		  	<odin:groupBox property="rmblGB" title="任免办理" >
	      		<div id="renmianbanli" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="20%" class="titleTd" colspan="1">应到人数</td>
							<td width="20%" class="titleTd" colspan="1">实到人数</td>
							<td width="20%" class="titleTd" colspan="1">赞成票</td>
							<td width="20%" class="titleTd" colspan="1">反对票</td>
							<td width="20%" class="titleTd" colspan="1">弃权票</td>
						</tr>
						<tr>
							<odin:textEdit property="js0902" width="'100%'" title="应到人数" colspan="1"  />
							<odin:textEdit property="js0903" width="'100%'" title="实到人数" colspan="1"  />
							<odin:textEdit property="js0904" width="'100%'" title="赞成票" colspan="1"  />
							<odin:textEdit property="js0905" width="'100%'" title="反对票" colspan="1"  />
							<odin:textEdit property="js0906" width="'100%'" title="弃权票" colspan="1"  />
						</tr>
						<tr>
							<tags:JUpload3 property="file09" label="上传资料" fileTypeDesc="所有文件"  colspan="5"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <div id="tabs-9"  class="GBx-fieldset">
		  	<odin:groupBox property="syqGB" title="试用期"  >
	      		<div id="shiyongqi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">试用期</td>
							<odin:textEdit property="js1002" width="'100%'" title="试用期" colspan="1"  />
							<td width="5%" class="titleTd" colspan="1">转正意见</td>
							<odin:textarea property="js1003" title="转正意见" colspan="1" rows="5" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">考核情况</td>
							<odin:textarea property="js1004" title="考核情况" colspan="3" rows="5" />
						</tr>
						<tr>
							<tags:JUpload3 property="file10" label="上传资料" fileTypeDesc="所有文件"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <div id="tabs-10"  class="GBx-fieldset">
		  	<odin:groupBox property="qtqkGB" title="其他情况" >
	      		<div id="qitaqingkuang" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td align="left" colspan="2">
								1、是否公选干部（
								<input type="radio"  name="js1102" style="width: auto !important;" id="dqwryjh" value="1" checked="checked" /><label for="dqwryjh">是</label>
								<input type="radio" name="js1102" style="width: auto !important;" id="dfdsfsd" value="0" /><label for="dfdsfsd">否</label>
								）&nbsp; 
								2、是何层次后备干部（
								<input type="radio" name="js1103" style="width: auto !important;" id="jgkyhtf" value="1"  /><label for="jgkyhtf">省管</label>
								<input type="radio" name="js1103" style="width: auto !important;" id="sdgdhytgd" value="0" checked="checked"/><label for="sdgdhytgd">市管</label>
								）&nbsp;<br/>
								3、有否任前培训（
								<input type="radio" name="js1104" style="width: auto !important;" id="jgkyewhtf" value="1"  checked="checked"/><label for="jgkyewhtf">有</label>
								<input type="radio" name="js1104" style="width: auto !important;" id="sdgdhrytgd" value="0" /><label for="sdgdhrytgd">无</label>
								）&nbsp;
								4、有否有基层工作经历（
								<input type="radio" name="js1105" style="width: auto !important;" id="jgkyewhtf1" value="1"  checked="checked"/><label for="jgkyewhtf1">有</label>
								<input type="radio" name="js1105" style="width: auto !important;" id="sdgdhrytgd1" value="0" /><label for="sdgdhrytgd1">无</label>
								）
							</td>
						</tr>
						<tr>
							<td width="10%" class="titleTd" colspan="1">其他需要补充说明的情况</td>
							<odin:textarea property="js1106" title="考核情况" colspan="1" rows="5" />
						</tr>
						<tr>
							<tags:JUpload3 property="file11" label="上传资料" fileTypeDesc="所有文件"  colspan="2"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  
		  <div id="tabs-16" style="float: left;">
			 
			<odin:groupBoxNew contentEl="test" collapsible="true" width="760"  property="mygroupBox1" 
			     title="发文">
			     <div id="test" style="overflow-y: scroll;">   
				<table border="0" id="myform2" align="center" width="750" cellpadding="0" cellspacing="0">
					<odin:tabLayOut />
					<tr id="msgTr_1">
						<odin:textarea property="fwh1" maxlength="100" label="发文号" title="发文号" rows="3" cols='20'  />
						<odin:textarea property="fwsj1" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw1" ondblclick="nrzw('nrzw1')" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw1js2200"/>
						<odin:textarea property="nmzw1" ondblclick="nmzw('nmzw1')" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw1a"/>
						<odin:hidden property="nrzw1b"/>
						<odin:hidden property="nmzw1a"/>
						<odin:hidden property="nmzw1b"/>
					</tr>
					<tr id="msgTr_2">
						<odin:textarea property="fwh2" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj2" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw2" ondblclick="nrzw('nrzw2')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw2js2200"/>
						<odin:textarea property="nmzw2" ondblclick="nmzw('nmzw2')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw2a"/>
						<odin:hidden property="nrzw2b"/>
						<odin:hidden property="nmzw2a"/>
						<odin:hidden property="nmzw2b"/>
					</tr>
					<tr id="msgTr_3">
						<odin:textarea property="fwh3" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj3" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw3" ondblclick="nrzw('nrzw3')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw3js2200"/>
						<odin:textarea property="nmzw3" ondblclick="nmzw('nmzw3')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw3a"/>
						<odin:hidden property="nrzw3b"/>
						<odin:hidden property="nmzw3a"/>
						<odin:hidden property="nmzw3b"/>
					</tr>
					<tr id="msgTr_4">
						<odin:textarea property="fwh4" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj4" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw4" ondblclick="nrzw('nrzw4')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw4js2200"/>
						<odin:textarea property="nmzw4" ondblclick="nmzw('nmzw4')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
					
						<odin:hidden property="nrzw4a"/>
						<odin:hidden property="nrzw4b"/>
						<odin:hidden property="nmzw4a"/>
						<odin:hidden property="nmzw4b"/>
					</tr>
					<tr id="msgTr_5">
						<odin:textarea property="fwh5" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj5" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw5" ondblclick="nrzw('nrzw5')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw5js2200"/>
						<odin:textarea property="nmzw5" ondblclick="nmzw('nmzw5')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
					
						<odin:hidden property="nrzw5a"/>
						<odin:hidden property="nrzw5b"/>
						<odin:hidden property="nmzw5a"/>
						<odin:hidden property="nmzw5b"/>
					</tr>
					<tr id="msgTr_6">
						<odin:textarea property="fwh6" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj6" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw6" ondblclick="nrzw('nrzw6')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw6js2200"/>
						<odin:textarea property="nmzw6" ondblclick="nmzw('nmzw6')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						
						<odin:hidden property="nrzw6a"/>
						<odin:hidden property="nrzw6b"/>
						<odin:hidden property="nmzw6a"/>
						<odin:hidden property="nmzw6b"/>
					</tr>
					<tr id="msgTr_7">
						<odin:textarea property="fwh7" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj7" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw7" ondblclick="nrzw('nrzw7')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw7js2200"/>
						<odin:textarea property="nmzw7" ondblclick="nmzw('nmzw7')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw7a"/>
						<odin:hidden property="nrzw7b"/>
						<odin:hidden property="nmzw7a"/>
						<odin:hidden property="nmzw7b"/>
					</tr>
					<tr id="msgTr_8">
						<odin:textarea property="fwh8" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj8" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw8" ondblclick="nrzw('nrzw8')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw8js2200"/>
						<odin:textarea property="nmzw8" ondblclick="nmzw('nmzw8')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw8a"/>
						<odin:hidden property="nrzw8b"/>
						<odin:hidden property="nmzw8a"/>
						<odin:hidden property="nmzw8b"/>
					</tr>
					<tr id="msgTr_9">
						<odin:textarea property="fwh9" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj9" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw9" ondblclick="nrzw('nrzw9')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw9js2200"/>
						<odin:textarea property="nmzw9" ondblclick="nmzw('nmzw9')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw9a"/>
						<odin:hidden property="nrzw9b"/>
						<odin:hidden property="nmzw9a"/>
						<odin:hidden property="nmzw9b"/>
					</tr>
					<tr id="msgTr_10">
						<odin:textarea property="fwh10" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj10" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw10" ondblclick="nrzw('nrzw10')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw10js2200"/>
						<odin:textarea property="nmzw10" ondblclick="nmzw('nmzw10')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw10a"/>
						<odin:hidden property="nrzw10b"/>
						<odin:hidden property="nmzw10a"/>
						<odin:hidden property="nmzw10b"/>
					</tr>
					<tr id="msgTr_11">
						<odin:textarea property="fwh11" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj11" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw11" ondblclick="nrzw('nrzw11')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw11js2200"/>
						<odin:textarea property="nmzw11" ondblclick="nmzw('nmzw11')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						
						<odin:hidden property="nrzw11a"/>
						<odin:hidden property="nrzw11b"/>
						<odin:hidden property="nmzw11a"/>
						<odin:hidden property="nmzw11b"/>
					</tr>
					<tr id="msgTr_12">
						<odin:textarea property="fwh12" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj12" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw12" ondblclick="nrzw('nrzw12')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw12js2200"/>
						<odin:textarea property="nmzw12" ondblclick="nmzw('nmzw12')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw12a"/>
						<odin:hidden property="nrzw12b"/>
						<odin:hidden property="nmzw12a"/>
						<odin:hidden property="nmzw12b"/>
					</tr>
					<tr id="msgTr_13">
						<odin:textarea property="fwh13" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj13" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw13" ondblclick="nrzw('nrzw13')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw13js2200"/>
						<odin:textarea property="nmzw13" ondblclick="nmzw('nmzw13')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw13a"/>
						<odin:hidden property="nrzw13b"/>
						<odin:hidden property="nmzw13a"/>
						<odin:hidden property="nmzw13b"/>
					</tr>
					<tr id="msgTr_14">
						<odin:textarea property="fwh14" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj14" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw14" ondblclick="nrzw('nrzw14')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw14js2200"/>
						<odin:textarea property="nmzw14" ondblclick="nmzw('nmzw14')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw14a"/>
						<odin:hidden property="nrzw14b"/>
						<odin:hidden property="nmzw14a"/>
						<odin:hidden property="nmzw14b"/>
					</tr>
					<tr id="msgTr_15">
						<odin:textarea property="fwh15" maxlength="100" label="发文号" title="发文号" rows="3" cols='20' />
						<odin:textarea property="fwsj15" maxlength="8" label="发文时间" title="发文时间" rows="3" cols='20'/>
						<odin:textarea property="nrzw15" ondblclick="nrzw('nrzw15')" maxlength="50" label="拟任职务" title="拟任职务" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw15js2200"/>
						<odin:textarea property="nmzw15" ondblclick="nmzw('nmzw15')" maxlength="50" label="拟免职务" title="拟免职务" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw15a"/>
						<odin:hidden property="nrzw15b"/>
						<odin:hidden property="nmzw15a"/>
						<odin:hidden property="nmzw15b"/>
					</tr>
					
											
					<tr>
					    <td align='left'>
							<odin:button property="addMsg" text="增加发文" handler="addMessage"></odin:button>
					    </td>
					    <td align='left'>
							<odin:button property="cutMsg" text="减少发文" handler="cutMessage"></odin:button>
					    </td>
					    <td align='left'>
							<odin:button property="clearnrzw" text="清除拟任职务" handler="clearnrzw"></odin:button>
					    </td>
					    <td align='right'>
							<odin:button property="updateA02" text="更新任职信息" handler="updateA02handler"></odin:button>
					    </td>
					</tr>
				</table>
			</div>
			     
			</odin:groupBoxNew>
			
		  </div>
		  	 			  
	  </div>  
	  <div id="bbardiv" ></div>  	  
	</div>  
</div>

<div id="newdiv" style=" overflow:scroll;">
	<table class="rm-panel" id="newtable" cellspacing="0" cellpadding="0">
	<tr></tr>
	 <!-- <tr>
		 <td>
			<div class="rmcontent">
				<ul>
					<li>
						<table class="people-card">
							<tr>
								<td valign="top">
									<img src="pages/xbrm/images/photo.png"/>
								</td>
								<td valign="top">
									<div class="col-name">刘菊香</div>
									<div class="col-50">性别:男</div>
									<div class="col-50">年龄:62岁</div>
									<div class="col-100">职位:区委区政府研究室人事科副科长</div>
								</td>
							</tr>
						</table>
					</li>
					<li><div class="step-body step-green-head"><div class="step-body-head">动议</div></div></li>
					<li><div class="step-body step-red"><div class="step-body-head">职数预审</div></div></li>
					<li><div class="step-body step-green"><div class="step-body-head">青干预审</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">查核与听取意见</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">组织考察</div></div></li>
					<li><div class="step-body step-black"><div class="step-body-head">讨论决定</div></div></li>
				</ul>
			</div>
		</td>
	</tr>
	 <tr>
		 <td>
			<div class="rmcontent">
				<ul>
					<li>
						<table class="people-card">
							<tr>
								<td valign="top">
									<img src="pages/xbrm/images/photo.png"/>
								</td>
								<td valign="top">
									<div class="col-name">刘菊香</div>
									<div class="col-50">性别:男</div>
									<div class="col-50">年龄:62岁</div>
									<div class="col-100">职位:区委区政府研究室人事科副科长</div>
								</td>
							</tr>
						</table>
					</li>
					<li><div class="step-body step-green-head"><div class="step-body-head">动议</div></div></li>
					<li><div class="step-body step-red"><div class="step-body-head">职数预审</div></div></li>
					<li><div class="step-body step-green"><div class="step-body-head">青干预审</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">查核与听取意见</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">组织考察</div></div></li>
					<li><div class="step-body step-black"><div class="step-body-head">讨论决定</div></div></li>
				</ul>
			</div>
		</td>
	</tr> -->
</table>
</div>	  

<%-- <odin:tabCont itemIndex="extTab1_1">
<table style="width: 100%">
	<tr>
		<odin:textEdit property="zhiwu" label="职务"></odin:textEdit>
		<odin:textEdit property="bumen" label="部门" ></odin:textEdit>
	</tr>
	<tr>
		<odin:NewDateEditTag property="renzhishijian" label="任职时间"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="mianzhishijain" label="免职时间"></odin:NewDateEditTag>
	</tr>
	<tr>
		<odin:textEdit property="pizhunwenhao" label="批准文号" ></odin:textEdit>
	</tr>
</table>
</odin:tabCont>
<odin:tabCont itemIndex="extTab1_2" >

</odin:tabCont> --%>
<odin:hidden property="tljlbxzlx" value="4_1" />

<odin:hidden property="js0100s" title="人员选择集合"/>
<!-- 拟任免时时保存 -->
<odin:hidden property="nrmid" title="字段"/>
<odin:hidden property="nrmdesc" title="字段中文"/>
<odin:hidden property="nrmvalue" title="值"/>

<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>
<%-- <odin:hidden property="js0100s" title="人员选择集合"/> --%>
<script type="text/javascript">
var ctxPath='<%=ctxPath%>';

function fcclick(id){
	//alert(id);
	var rbId=document.getElementById('rbId').value;
	//$h.openPageModeWin('QgysfcWin', 'pages.xbrm.Qgysfc','反查信息', 700, 500, {rb_id: rbId, id: id} , ctxPath);
}

function removeJsPerson(js0100){
	radow.doEvent('removeJsPerson', js0100);
}

function Check3(){
	document.getElementById('slllsh_div').style.display='block';
	document.getElementById('slllshqklx').value='3';
}
function Check2(){
	document.getElementById('slllsh_div').style.display='block';
	document.getElementById('slllshqklx').value='2';
}
function Check1(){
	document.getElementById('slllsh_div').style.display='none';
	document.getElementById('slllshqklx').value='1';
}
function updateA02handler(){
	radow.doEvent("updateA02");
}

function clearnrzw(){
	for(var i=1;i<16;i++){
		$('#nrzw'+i).val('');
	}
	radow.doEvent('clearnrzw');
}

//发文按钮
function fwexphandler(){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var store = peopleInfoGrid.store;
	var js0100s = "";
	var count = 0;
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			js0100s = js0100s + rowData.data.js0100 + ",";
			count = count +1;
		}
	}
	if(count==store.getCount()){
		doucment.getElementById("rb_status").value = 'true';
	}
	document.getElementById("js0100s").value=js0100s;
	radow.doEvent('fwexpdo');
}

function showMsg(num){
	var msg=parseInt(num);
	for(var i=1;i<msg+1;i++){
		$('#msgTr_'+i).show();
	}
	for(var i=msg+1;i<16;i++){
		$('#msgTr_'+i).hide();
	}
	document.getElementById('msg').value=num;
}

//增加减少发文
function cutMessage(){
	var msg=parseInt(document.getElementById('msg').value)
	$('#nrzw'+msg).val('');
	$('#nmzw'+msg).val('');
	$('#fwh'+msg).val('');
	$('#msgTr_'+msg).hide();
	document.getElementById('msg').value=msg-1;
}


function addMessage(){
	var msg=parseInt(document.getElementById('msg').value)+1;
	for(var i=1;i<msg+1;i++){
		$('#msgTr_'+i).show();
	}
	for(var i=msg+1;i<16;i++){
		$('#msgTr_'+i).hide();
	}
	document.getElementById('msg').value=msg;
}

function tjgzfa(){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var store = peopleInfoGrid.store;
	var js0100s = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			js0100s = js0100s + rowData.data.js0100 + ",";
		}
	} 
	if(js0100s==""){
		odin.alert("请勾选人员");
	}else{
		document.getElementById("js0100s").value=js0100s;
		radow.doEvent('tjgzfa');
	}
	
}

function tjjg(){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var store = peopleInfoGrid.store;
	var js0100s = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			js0100s = js0100s + rowData.data.js0100 + ",";
		}
	} 
	if(js0100s==""){
		odin.alert("请勾选人员");
	}else{
		document.getElementById("js0100s").value=js0100s;
		radow.doEvent('tjjg');
	}
	
}

function hidesp(){
	$('#needReportTr').css('display','none');
	$('#psreportTr').css('display','none');
	$('#psreportvalueTr').css('display','none');
	$('#aproveTr').css('display','none');
}

function hidecwh(){
	$('#cwhtitle').css('display','none');
	$('#cwhtr').css('display','none');
}

function showcwh(){
	$('#cwhtitle').css('display','block');
	$('#cwhtr').css('display','block');
}

function hideaproveTr(){
	//document.getElementById('js0211_combo').value='';
	$('#aproveTr').css('display','none');
}

function showaproveTr(){
	//document.getElementById('js0211_combo').value='';
	$('#aproveTr').css('display','block');
}

function showreportTr(){
	$('#psreportTr').css('display','block');
}

function showreportvalueTr(){
	$('#psreportvalueTr').css('display','block');
}

function hidereportTr(){
	document.getElementById('js0209_combo').value='';
	document.getElementById('js0210').value='';
	$('#psreportTr').css('display','none');
	$('#psreportvalueTr').css('display','none');
	$('#aproveTr').css('display','none');
}


function hidereportvalueTr(){
	document.getElementById('js0210').value='';
	$('#psreportvalueTr').css('display','none');
}

function approvechange(){
	var val=document.getElementById('js0208').value;
	if(val=='1'){
		$('#psreportTr').css('display','block');
		$('#aproveTr').css('display','block');
	}else if(val=='2'){
		document.getElementById('js0209_combo').value='';
		$('#psreportTr').css('display','none');
		document.getElementById('js0210').value='';
		$('#psreportvalueTr').css('display','none');
		$('#aproveTr').css('display','none');
		
	}
}
function resonchange(){
	var val=document.getElementById('js0209').value;
	if(val=='610'){
		$('#psreportvalueTr').css('display','block');
	}else{
		document.getElementById('js0210').value='';
		$('#psreportvalueTr').css('display','none');
	}
}

function tsGBJD(){
	//alert(22)
	radow.doEvent("tsGBJD");
}

function todoInfo(type,resbusiid,resbusiname,targetid,targetname){
	var p = {'type':type, 'rid':resbusiid,'rname':resbusiname,'tid': targetid, 'tname': targetname};
	$h.openPageModeWin('infotodoWin','pages.comm.InfoToDo','提请部门',310,110, p , ctxPath);
}

function tqyjche(name,che){
	$("input[name='tqyjcheck']").each(function(){
		var v=$(this).val();
		if(name==v){
			if(che==1){
				$(this).prop('checked',true);	
			}else if(che==2){
				$(this).prop('checked',false);
			}
		}
	})
	
}


function slllshcheck(value){
	$("input[name='slllsh']").each(function(){
		var v=$(this).val();
		if(value==v){
			$(this).attr('checked',true);
		}else{
			$(this).attr('checked',false);
		}
	});
	if(value == 1){
		document.getElementById('slllsh_div').style.display='none';
	} else {
		document.getElementById('slllsh_div').style.display='block';
	}
}

function grsxche(value){
	$("input[name='grsxch']").each(function(){
		var v=$(this).val();
		if(value==v){
			$(this).attr('checked',true);
		}else{
			$(this).attr('checked',false);
		}
	})
}

function save(){

	var data=[];
	$("input[name='tqyjcheck']").each(function(){
		var ischeck=$(this).is(":checked");
		var value=$(this).val();
		if(ischeck==true){
			data.push(value);
				
		}
	});
	document.getElementById('checkboxtqyj').value=data+""
	//alert(document.getElementById('checkboxtqyj').value);
	var slllysfsh = document.getElementById('slllshqklx').value;
	if(slllysfsh == '1'){
		radow.doEvent("save",i);
	} else {
		var wb = '';
		if(slllysfsh == '2'){
			wb = '不通过';
		} else {
			wb = '暂缓';
		}
		/* Ext.MessageBox.confirm('系统提示','该人“三龄两历一身份”审核为 '+wb+',保存将从人员列表中移除该人员，确认保存吗？',function(id){
			if("yes"==id){
				radow.doEvent("save");
			}else{
				return;
			}	
		}) */
		$h.confirm('系统提示','该人“三龄两历一身份”审核为 '+wb+',保存将从人员列表中移除该人员，确认保存吗？', 300, function(id){
			if("ok"==id){
				radow.doEvent("save");
			}else{
				return;
			}	
		});
		
	}
}

function nrzwtop(){
	var a0000=document.getElementById('a0000').value;
	if(''==a0000){
		Ext.Msg.alert('系统提示','请先选择人员!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	$h.openPageModeWin('RankAddPageNiRenQcjs','pages.xbrm.RankAddPageNiRenQcjs','拟任职务',450,230,a0000+','+id+','+js0100,ctxPath);
}


function nrzw(id){
	var a0000=document.getElementById('a0000').value;
	if(''==a0000){
		Ext.Msg.alert('系统提示','请先选择人员!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	$h.openPageModeWin('RankAddPageNiRenQcjs','pages.xbrm.NiRenQcjs','拟任职务',450,230,a0000+','+id+','+js0100,ctxPath);
}

function nmzw(id){
	var a0000=document.getElementById('a0000').value;
	var rbId=document.getElementById('rbId').value;
	if(''==a0000){
		Ext.Msg.alert('系统提示','请先选择人员!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	//$h.openPageModeWin('WorkUnitsNiRenAddPageQcjs','pages.xbrm.WorkUnitsNiRenAddPageQcjs','拟免职务',420,300,a0000+','+id+','+js0100,ctxPath);
	$h.openPageModeWin('WorkUnitsNiRenAddPageQcjs','pages.xbrm.WorkUnitsNiRenAddPageQcjs','拟免职务',420,300,a0000+','+id+','+rbId+','+js0100,ctxPath);
}


function changeSelect(id,num){
	var value=$('#select'+id+' option:selected').val();
	$('#'+id).val(value);
}

//青干预审注册双击
Ext.onReady(function(){
	/* var cell1 = Ext.getCmp('r1c1');
	cell1.getEl().on('dblclick',function(p){
		fcclick('r1c1');
	});
	var cell2 = Ext.getCmp('r1c2');
	cell2.getEl().on('dblclick',function(p){
		fcclick('r1c2');
	}); */
	for(var i=1; i<= 6; i++){
		for(var j=1; j<= 5; j++){
			var ddid = 'r'+i+'c'+j;
			var cell = Ext.getCmp(ddid);
			cell.getEl().on('dblclick',function(p,d){
				fcclick(this.id);
			});
			var ddid2 = 't2r'+i+'c'+j;
			var cell2 = Ext.getCmp(ddid2);
			cell2.getEl().on('dblclick',function(p){
				fcclick(this.id);
			});
		}
	}
	
});

Ext.onReady(function(){
	
	Ext.BLANK_IMAGE_URL = "${pageContext.request.contextPath}/ext/resources/images/default/s.gif";
	//暂时添加效果
	$("#tabs-3").hide();
	$("#tabs-5").hide();
	$("#tabs-8").hide();  
	$("#tabs-9").hide();  
	$("#tabs-10").hide();
	var meettype=document.getElementById('meetType').value;
	if('1'==meettype){
		hidecwh();
	}else if('2'==meettype){
		showcwh();
	}
	//页面加载时移除保存按钮隐藏效果
	Ext.getCmp('save').removeClass('bh');
	//Ext.getCmp('qcjs').removeClass('bh');
	
	$('#tablehide').hide();
	
	//显示职数预审行数
	var block = parseInt(document.getElementById('block').value);
	for(var i = block+1; i <=20; i++){ //tr_len是要控制的tr个数  
	     $("#tr_"+i).hide();  
	} 
	//显示民主推荐行数
	var js33row=parseInt(document.getElementById('js33row').value);
	for(var i = js33row+1; i <=20; i++){ //tr_len是要控制的tr个数  
	     $("#js33tr_"+i).hide();  
	} 
	
	//var combo = getSelect();
	Ext.getCmp('gridcq').getBottomToolbar().insertButton(0,[
		//new Ext.menu.Separator({cls:'xtb-sep'}),
		new Ext.Spacer({width:1,height:35}), 
		/* combo */
		{boxLabel: '全部',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4', listeners:{check:radiochecked}},
		{boxLabel: '部务会', name: 'rb-col',xtype: 'radio', inputValue: '4_1',checked: true ,listeners:{check:radiochecked}},
        {boxLabel: '书记会',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4_2',listeners:{check:radiochecked}},
        {boxLabel: '常委会', name: 'rb-col',xtype: 'radio', inputValue: '4_3',listeners:{check:radiochecked}}
	]);
	
	new Ext.Toolbar({
		renderTo:Ext.getCmp('gridcq').bbar,
		items:[
			new Ext.Spacer({width:1,height:35}), 
			{boxLabel: '调配类别', name: 'rb-col2',xtype: 'radio', inputValue: '1', checked: true ,listeners:{check:radiochecked2}},
			{boxLabel: '谈话安排类别', name: 'rb-col2',xtype: 'radio', inputValue: '2',listeners:{check:radiochecked2}}
		],
		id:"bbarid2"
	}).hide();

	
	//combo.show();
	Ext.getCmp('bbarid').hide();
	$(function() {
	    $( "#tabs" ).tabs({ 'select': function(event, ui) { tabclick(ui.newTab.closest("li a").context) }});
	    $( "#ulTitle" ).css("display","block");
	});
	
	
	//document.getElementById('rbId').value='a33415cd-c8a4-4b94-a287-207872fc774b'
	if(typeof parentParam!= 'undefined'){
		document.getElementById('rbId').value=parentParam.rb_id;
	}else{
		document.getElementById('rbId').value='c42981e1-d876-4d5c-9e85-13eb5bad13eb';
	}
	
		
	for(var i=3;i<16;i++){
		$('#msgTr_'+i).hide();
	}
	
	
	//$("#togglebtn").css('height','20px');
	
	_ulTitleObj = $( "#ulTitle" );
	var viewSize = Ext.getBody().getViewSize();
	//alert('viewSize.height-'+viewSize.height)
	var peopleInfoGrid =Ext.getCmp('gridcq');
	peopleInfoGrid.setHeight(viewSize.height-parseInt(_ulTitleObj.css('height'))-50);
	//alert('peopleInfoGrid.getHeight-'+peopleInfoGrid.getHeight())
	var gridobj = document.getElementById('forView_gridcq');
	var grid_pos = $h.pos(gridobj);
	$( "#peopleInfo" ).css('width',viewSize.width-grid_pos.left-peopleInfoGrid.getWidth()-6);
	$( "#peopleInfo" ).css('height',peopleInfoGrid.getHeight());
	
	//$( "#gp1" ).css('height','10px;');
	var gp1 = Ext.get("gp1");//个人信息
	$( "#pdata" ).css('width',gp1.getWidth()-200);
	//gp1.setHeight(240);
	var g_hight = gp1.getHeight()//个人信息+拟任免的高
	//ext标签高宽  登记审查
	/* var extTab1 = Ext.getCmp("extTab1");
	extTab1.setWidth(gp1.getWidth());
	extTab1.setHeight(peopleInfoGrid.getHeight()-gp1.getHeight()-10); */
	//alert(peopleInfoGrid.getHeight())
		
	var isshownrm=document.getElementById('isshownrm').value;
	if('5'==isshownrm){
		//基本情况
		var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
		//jbqkGB.css('width',gp1.getWidth()-25);
		jbqkGB.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		var offest = 0;
		 //动议
		 var dy=$("#dyGB .x-fieldset-bwrap");
		 dy.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//adjustGB($("#dyGB .x-fieldset-bwrap"),jbqkGB,offest);
		//职数预审
		//adjustGB2($("#zsysGB .x-fieldset-bwrap"),jbqkGB,offest);
		//考核与听取意见
		//adjustGB($("#khyjGB .x-fieldset-bwrap"),jbqkGB,offest);
		var khytqyj=$("#khyjGB .x-fieldset-bwrap");
		khytqyj.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//青干预审
		//var qgysqk = $("#tabs-12");
		 //alert(qgysqk);
		//qgysqk.css('width',jbqkGB.width()-10-20);
		//qgysqk.css('height',10);
		//qgysqk.css('height',peopleInfoGrid.getHeight()-39);
		/*adjustGB2($("#qgysGB .x-fieldset-bwrap"),jbqkGB,offest); */
		//民主推荐
		//adjustGB($("#mztjGB .x-fieldset-bwrap"),jbqkGB,offest);
		var mztj=$("#mztjGB .x-fieldset-bwrap");
		mztj.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//考察人员确定
		//adjustGB($("#kcrxqdGB .x-fieldset-bwrap"),jbqkGB,offest);
		//组织考察
		//adjustGB($("#zzkcGB .x-fieldset-bwrap"),jbqkGB,offest);
		var zzkc=$("#zzkcGB .x-fieldset-bwrap");
		zzkc.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//酝酿
		//adjustGB($("#ynGB .x-fieldset-bwrap"),jbqkGB,offest);
		//讨论决定
		//adjustGB($("#tljdGB .x-fieldset-bwrap"),jbqkGB,offest);
		var tljd=$("#tljdGB .x-fieldset-bwrap");
		tljd.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//任前公示
		//adjustGB($("#rqgsGB .x-fieldset-bwrap"),jbqkGB,offest);
		var rqgs=$("#rqgsGB .x-fieldset-bwrap");
		rqgs.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//任免办理
		//adjustGB($("#rmblGB .x-fieldset-bwrap"),jbqkGB,offest);
		//试用期
		//adjustGB($("#syqGB .x-fieldset-bwrap"),jbqkGB,offest);
		//其他情况
		//adjustGB($("#qtqkGB .x-fieldset-bwrap"),jbqkGB,offest); 
		//ext标签内容样式调整，超出页面支持上下滚动
		/* var tabContentHeight = jbqkGB.getHeight()-30;
		$( "#extTab1_1_CEL" ).css('height',tabContentHeight).css('overflow-y','auto');
		$( "#extTab1_2_CEL" ).css('height',tabContentHeight).css('overflow-y','auto'); */
	}else if('6'==isshownrm){
		//基本情况
		var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
		jbqkGB.css('height',viewSize.height);
		var offest = 0;
		 //动议
		 var dy=$("#dyGB .x-fieldset-bwrap");
		 dy.css('height',viewSize.height);
		//考核与听取意见
		var khytqyj=$("#khyjGB .x-fieldset-bwrap");
		khytqyj.css('height',500);
		//民主推荐
		var mztj=$("#mztjGB .x-fieldset-bwrap");
		mztj.css('height',viewSize.height);
		//组织考察
		var zzkc=$("#zzkcGB .x-fieldset-bwrap");
		zzkc.css('height',viewSize.height);
		//讨论决定
		var tljd=$("#tljdGB .x-fieldset-bwrap");
		tljd.css('height',viewSize.height);
		//任前公示
		var rqgs=$("#rqgsGB .x-fieldset-bwrap");
		rqgs.css('height',viewSize.height);
	}
	
	peopleInfoGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		//if(rc.data.js0100!=$('#js0100').val()){//环节列表上选择行，同一行的数据则不触发重新查询详细信息
			$('#js0100').val(rc.data.js0100);
			$('#a0101').val(rc.data.a0101);
			
			document.getElementById('a0000').value=rc.data.a0000;
			radow.doEvent('peopleInfo',rc.data.a0000);
		//}
		
		//alert(document.getElementById('a0000').value);
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
	
	//alert($('#tdid_js0114').width())
	//adjustSelectWidth('js0114');
	//alert($('#tdid_js0114').width())
	//fnSet['file02']();
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
	
	/*在ie中解决断行问题(防止只有一行显示，主要解决ie兼容问题，ie8中当设宽度为100%时，文本域类容由换行时*/
	$('#js0108,#js0111,#js0117').each(function(){$(this).css('width',$(this).innerWidth())})
	Ext.getCmp('js0108').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	/* Ext.getCmp('js0109').on('change',function(t,v){saveNRMValue(t,v,t.id);}); */
	Ext.getCmp('js0117').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	Ext.getCmp('js0111').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	//Ext.getCmp('js0115_combo').on('select',function(t,record,index){saveNRMValue(index,record.data.key,'js0115');});
	//Ext.getCmp('js0116_combo').on('select',function(t,record,index){saveNRMValue(index,record.data.key,'js0116');});
});

function hideTsgbjd(str){
	//按钮显示隐藏
	var $fn=document.getElementById('tabobj').value;
	if($fn=='file99'){
		Ext.getCmp("btnts").addClass('bh');
		document.getElementById('crp010').value=str;
	}
}


function showTsgbjd(){
	//Ext.getCmp("btnts").removeClass('bh');
	var $fn=document.getElementById('tabobj').value;
	if($fn=='file99'){
		Ext.getCmp("btnts").removeClass('bh');
		document.getElementById('crp010').value='';
	}
}

function clearArow(rownum){
	$('#js1201_'+rownum).val('');
	$('#js1201_'+rownum+'_combo').val('');
	$('#td_js1202_1_'+rownum+' input').prop("checked",true);
	
	$('#js1202_'+rownum).val('');
	$('#js1203_'+rownum).val('');
	$('#js1204_'+rownum).val('');
	$('#js1205_'+rownum).val('');
	$('#js1206_'+rownum).val('');
	$('#js1207_'+rownum).val('');
	$('#js1208_'+rownum).val('');
	$('#js1209_'+rownum).val('');
	$('#js1210_'+rownum).val('');
	$('#js1211_'+rownum).val('');
	$('#selectjs1211_'+rownum).find("option[value='0']").attr("selected",true);
	$('#js1212_'+rownum).val('');
	$('#selectjs1212_'+rownum).find("option[value='0']").attr("selected",true);
	$('#js1213_'+rownum).val('');
	$('#js1214').val('');
}

function changeValue(js1201,rownum){
	js1201=$('#'+js1201).val()
	
	var rbid=$('#rbId').val()
	//ajax查询数据库，如果数据库中有就展示数据库中信息，没有就都展示0
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.QCJS&eventNames=selectJs12ByDW';
	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {js1201:js1201,rbid:rbid},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
      		   var cfg = Ext.util.JSON.decode(result);
      		   if(undefined ==cfg.messageCode){
      			   var json = eval('('+response.responseText+')');
      			   //alert(json.js1214);
        		   if(json.js1202=='1'){
        			  $('#td_js1202_1_'+rownum+' input').prop("checked",true);
        		   }else{
        			  $('#td_js1202_2_'+rownum+' input').prop("checked",true);
        		   }
        		   //$('#js1202_'+rownum).val(Number(json.js1202));
	  			   $('#js1203_'+rownum).val(Number(json.js1203));
	  			   $('#js1204_'+rownum).val(Number(json.js1204));
	  			   $('#js1205_'+rownum).val(Number(json.js1205));
	  			   $('#js1206_'+rownum).val(Number(json.js1206));
	  			   $('#js1207_'+rownum).val(Number(json.js1207));
	  			   $('#js1208_'+rownum).val(Number(json.js1208));
	  			   $('#js1209_'+rownum).val(Number(json.js1209));
	  			   $('#js1210_'+rownum).val(Number(json.js1210));
	  			   $('#js1211_'+rownum).val(json.js1211);
	  			   $('#selectjs1211_'+rownum).find("option[value='"+json.js1211+"']").attr("selected",true);
	  			   $('#js1212_'+rownum).val(json.js1212);
	  			   $('#selectjs1212_'+rownum).find("option[value='"+json.js1212+"']").attr("selected",true);
	  			   $('#js1213_'+rownum).val(json.js1213);
	  			   $('#js1214').val(json.js1214);
			   }else{
				   $('#td_js1202_1_'+rownum+' input').prop("checked",true);
        		   $('#js1202_'+rownum).val('0');
	  			   $('#js1203_'+rownum).val('0');
	  			   $('#js1204_'+rownum).val('0');
	  			   $('#js1205_'+rownum).val('0');
	  			   $('#js1206_'+rownum).val('0');
	  			   $('#js1207_'+rownum).val('0');
	  			   $('#js1208_'+rownum).val('0');
	  			   $('#js1209_'+rownum).val('0');
	  			   $('#js1210_'+rownum).val('0');
	  			   $('#js1211_'+rownum).val('0');
	  			   $('#selectjs1211_'+rownum).find("option[value='0']").attr("selected",true);
	  			   $('#js1212_'+rownum).val('0');
	  			   $('#selectjs1212_'+rownum).find("option[value='0']").attr("selected",true);
	  			   $('#js1213_'+rownum).val('');
	  			   $('#js1214').val('');
			   }
				$('#js1203_'+rownum).focus();
				//$('#js1203_'+rownum).focus().select();
      	   }
        }
   });
	

}


function authorLetter(){
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
		
	var data=$("#qtsy").text();
	radow.doEvent('authorLetter',data);
}


function importSheet(){
	
	var data=[];
	$("input[name='tqyjcheck']").each(function(){
		var ischeck=$(this).is(":checked");
		//console.log(ischeck);
		if(ischeck==false){
			data.push("□");
		}else{
			data.push("√");
		}
	});
	$("input[name='grsxch']").each(function(){
		var ischeck=$(this).is(":checked");
		//console.log(ischeck);
		if(ischeck==false){
			data.push("□");
		}else{
			data.push("√");
		}
	});
	
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
	
	
	data.push($("#gzsy").text());
	radow.doEvent('importSheet',data+"");
}

function addArow(){
	document.getElementById('block').value=parseInt(document.getElementById('block').value)+1;
	var block = document.getElementById('block').value;
	for(var i = 1; i <=block; i++){ //显示tr个数
	     $("#tr_"+i).show();
	}
}

function showArow(){
	var block = parseInt(document.getElementById('block').value);
	for(var t = block+1; t <=30; t++){ //tr_len是要控制的tr个数  
	     $("#tr_"+t).hide();  
	}
	for(var i = 1; i <=block; i++){ //显示tr个数
	     $("#tr_"+i).show();  
	} 
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
	if(r.checked){
		$('#tljlbxzlx').val(r.inputValue);
	}
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

function khcheck(){
	$("input[name='tqyjcheck']").each(function(){
		$(this).prop('checked',true);
	})
	$("input[name='grsxch']").each(function(){
		var v=$(this).val();
		if('1'==v){
			$(this).attr('checked',true);
		}
	})
}

function tabclick(obj){
	
		var $href=$(obj).attr('href');
		/* if($href=='#tabs-11' || $href=='#tabs-12'){
			$('#togglebtn').css('display','none');
		}else{
			$('#togglebtn').css('display','block');
		} */
		
		//默认都勾选
		if($href=='#tabs-13'){
			$("input[name='tqyjcheck']").each(function(){
				$(this).prop('checked',true);
			})
			$("input[name='grsxch']").each(function(){
				var v=$(this).val();
				if('1'==v){
					$(this).attr('checked',true);
				}
			})
		}
				
		if($href=='#tabs-6'){
			var meettype=document.getElementById('meetType').value;
			if('1'==meettype){
				hidecwh();
			}else if('2'==meettype){
				showcwh();
			}
		}
		
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
    	document.getElementById('tabobj').value=$fn;
    	
    	//拟任免，一个隐藏都隐藏，一个显示都显示
		var isshownrm=document.getElementById('isshownrm').value;
		var isshow=$('#personInfo').is(":visible");
		if('1'==isshownrm){
			//$('#personInfo').show();
			//Ext.getCmp('personInfo').show();
			$( "#personInfo" ).css("display","block");
			if($fn=='file12'||$fn=='file13'){
	    		//职数预审隐藏人员基本信息
	    		$( "#personInfo" ).css("display","none");
	    		loadTableData($fn);
	    	}
			document.getElementById('isshownrm').value='1';
		}else if('2'==isshownrm){
			//$('#personInfo').hide();
			//Ext.getCmp('personInfo').hide();
			$( "#personInfo" ).css("display","none");
			document.getElementById('isshownrm').value='2';
		}
    	
		/* if($fn=='file12'||$fn=='file13'){
    		//职数预审隐藏人员基本信息
    		$( "#personInfo" ).css("display","none");
    		loadTableData($fn);
    	}else{
    		$( "#personInfo" ).css("display","block");
    	} */
    	
    	
    	if($fn=='file13'){
    		$('#tabs-12').css("display","block");
    	}else{
    		$('#tabs-12').css("display","none");
    	}
    	
    	//显示隐藏按钮
    	if($fn=='file99'){
    		radow.doEvent("tabclickshowts");
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
    	//alert($hj)
    	//alert($('#cur_hj').val());
    	/* if($hj!=$('#cur_hj').val()){
    		$('#cur_hj').val($hj);
    		if(($hj=="${RMHJ.DONG_YI}"&&$('#cur_hj').val()=="${RMHJ.JI_BEN_QING_KUANG}")||($hj=="${RMHJ.JI_BEN_QING_KUANG}"&&$('#cur_hj').val()=="${RMHJ.DONG_YI}")){
    			
    		}else{
    			
    			loadgriddata();
    			
    		}
    	}else{
    		
    	} */
    	
    	
    	if($hj=='4'){
    		Ext.getCmp('bbarid').show();
    		var meettype=document.getElementById('meetType').value;
    		if('1'==meettype){
    			Ext.getCmp('bbarid').setDisabled(true);
    		} else {
    			Ext.getCmp('bbarid').setDisabled(false);
    		}
    	}else{
    		Ext.getCmp('bbarid').hide();
    	}
    	if($hj=='6'){
    		//Ext.getCmp('bbarid2').show();
    		Ext.getCmp('bbarid2').hide();
    	}else{
    		Ext.getCmp('bbarid2').hide();
    	}
    	
	//});
}


function qcjs(){
	var js0100='';
	
	var grid=Ext.getCmp('gridcq');
	var store=grid.getStore();
	var num=0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var rejs0100=record.data.js0100;
		var a0101=record.data.a0101;
		var a0000=record.data.a0000;
		if(pcheck == true){
			num=num+1;
			if(num == 1){
				js0100=rejs0100;
				document.getElementById('qcjsjs0100').value=js0100;
				$('#js0100').val(js0100);
				$('#a0101').val(a0101);
				document.getElementById('a0000').value=a0000;
				document.getElementById('qcjsid').value=i+1;
				//radow.doEvent('peopleInfo',rc.data.a0000);
			}
		}
	}
	if(num!=1){
		Ext.Msg.alert('系统提示:','请勾选一个人员后，再导出!');
	}
	var rb_no = parent.document.getElementById("rb_no").value;
	document.getElementById("rb_no").value = rb_no;
	/* if(js0100==null || js0100.length==0){
		Ext.Msg.alert('系统提示:',"请选择人员!");
	} */
	//file02(动议)  考核与听取意见(file99)   民主推荐(file19)   组织考察(file14)  讨论决定file07   任前公示file08
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.QCJS&eventNames=hasUploadAll';
	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {js0100:js0100},
        callback: function (options, success, response) {
      	   if (success) {
      		   
      		   var result = response.responseText;
 			   if(result){
 				  var resultjson = Ext.decode(result);
 				  var cfg = Ext.util.JSON.decode(result);
 				  
 				  if(undefined ==cfg.messageCode){
 					Ext.Msg.buttonText.ok = '继续';
 					Ext.Msg.buttonText.cancel='取消';
 					$h.confirm("系统提示：",resultjson.msg+"未上传资料，是否继续？",220,function(id) { 
 						if("ok"==id){
 							ShowCellCover("start","温馨提示：","正在生成全程纪实表");
 							radow.doEvent('qcjs');
 							Ext.Msg.hide();
 						}if("cancel"==id){
 							return false;
 						}else{
 							return false;
 						}
 					});
 				  }else{
 					 ShowCellCover("start","温馨提示：","正在生成全程纪实表");
 					 radow.doEvent('qcjs');
 					 Ext.Msg.hide();
 				  }
 				}
      	   }
        }
   });
}



function saveNRMValue(t,v,id){//拟任免实时保存。
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
	
	//设置tab页打勾
	radow.doEvent("setShowImg");
	
	//设置人员是否完成填写打勾
	//获取当前激活tab页
	var $tabs = $('#ulTitle').children( 'li' );
    var i=1;
    $tabs.each( function() {
        var $tab = $( this );
        if($tab.hasClass('ui-tabs-active')){
           	return false;
        }else{
            i++;
        }
    } );
	radow.doEvent("setColor",i);
	
	
	
	
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
//加载职数预审数据
function loadTableData(filename){
	radow.doEvent('TableData',filename);
}

function controlColor(i){
	var img = Ext.query("#js1201_"+i+"_combo+img")[0];
	img.onclick=null;
	Ext.getCmp('js1201_'+i+'_combo').disable();
	
	$('#js1202_1_'+i).attr("disabled",true);
	$('#js1202_2_'+i).attr("disabled",true);

	Ext.getCmp("js1203_"+i).setDisabled(true);
	Ext.getCmp("js1204_"+i).setDisabled(true);
	Ext.getCmp("js1205_"+i).setDisabled(true);
	Ext.getCmp("js1206_"+i).setDisabled(true);
	Ext.getCmp("js1207_"+i).setDisabled(true);
	Ext.getCmp("js1208_"+i).setDisabled(true);
	Ext.getCmp("js1209_"+i).setDisabled(true);
	Ext.getCmp("js1210_"+i).setDisabled(true);
	
	$("#selectjs1211_"+i).attr('disabled',true);
	$('#selectjs1212_'+i).attr("disabled",true);
	
	Ext.getCmp("js1213_"+i).setDisabled(true);
	
	Ext.getCmp("saveZS").addClass('bh');
	document.getElementById('addrowBtn').style.display = 'none';
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
	//window.location="PublishFileServlet?method=downloadFile&id="+encodeURI(encodeURI(id));
	document.getElementById('frameid').src="PublishFileServlet?method=downloadFile&id="+encodeURI(encodeURI(id));
	
}

function ffed(e,b){
	
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
	///window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	document.getElementById('frameid').src="PublishFileServlet?method=downloadFile&uuid="+encodeURI(encodeURI(downloadUUID));
	
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

function expLrmxGrid(){
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
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.QCJS&eventNames=exportLrmxBtn';
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {rbId:rbId},
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

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	//window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	document.getElementById('frameid').src="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function cc(){
	
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
	
	var js0100=document.getElementById("js0100").value;
	var js0802=document.getElementById("js0802").value;
	var js0803=document.getElementById("js0803").value;
	var js0804=document.getElementById("js0804").value;
	var js0805=document.getElementById("js0805").value;
	var tljlbxzlx=document.getElementById("tljlbxzlx").value;
	
	ShowCellCover('start','系统提示','正在输出干部调配建议方案 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {rbId:rbId,buttonid:buttonid,buttontext:buttontext,js0100:js0100,js0802:js0802,js0803:js0803,js0804:js0804,js0805:js0805,tljlbxzlx:tljlbxzlx},
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
//一键导出干部资料
function filedown(){
var grid = Ext.getCmp('gridcq');
	var total = grid.getStore().getCount();//数据行数
	var record; //行数据
	var js0100s = ""; //拟任干部名单
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('pcheck')){
			js0100s += record.get('js01001')+"@#@";
		}
	}
	if(""==js0100s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		$h.openWin('mnrm','pages.xbrm.AllFileDown','下载资料选择 ',320,220,null,contextPath,window,{js0100s : js0100s});
	}
}

//一键导出干部资料
/* function filedown(){
var grid = Ext.getCmp('gridcq');
	
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
		$h.openWin('mnrm','pages.xbrm.AllFileDown','下载资料选择 ',320,220,null,contextPath,window,{a0000s:a0000s});
	}
} */



function changeTableValue() {
    $(".x-form-num-field").each(function () {
        if (this.value == "") {
            this.value = '0';
        }
    })
    radow.doEvent("changeTableValue");
}

function changeTableValue2() {
	$(".x-form-num-field").each(function () {
		if (this.value == "") {
			this.value = '0';
		}
	})
	radow.doEvent("changeTableValue2");
}

function rmbView() {
	var a0000 = document.getElementById('a0000').value;
	if(a0000==""||a0000==null){
		return;
	}
	radow.doEvent("printViewNew",a0000);

}
function downloadfile(downfile){
	//var downfile = '<%=request.getSession().getAttribute("QCJSPath") %>';
	//downfile = eval('('+downfile+')');
	window.open("<%=request.getContextPath()%>/SorlQueryServlet?path="+encodeURI(encodeURI(downfile)));

}

function searchScore(){
	radow.doEvent("searchScore");
}

function setColor(num){
	if(num==3||num==4){
		var grid=Ext.getCmp("gridcq");
	    var store = grid.getStore();
	    for(var i=0;i<store.getCount();i++){
	    	store.getAt(i).set("finish","");
	    }
	}else{
		radow.doEvent("setColor",num);
	}
	
}
function showColor(){
	var grid=Ext.getCmp("gridcq");
    var store = grid.getStore();
    for(var i=0;i<store.getCount();i++){
    	store.getAt(i).set("finish","");
    }
    var data=store.getRange(0,store.getCount());
    var a=document.getElementById("js0100s_finish").value;
    var js0100s=a.split(",");
    for(var i=0;i<js0100s.length;i++){
    	for(var j=0;j<data.length;j++){
    		if(data[j].data.js0100==js0100s[i]){
    			store.getAt(j).set("finish","<img  src='image/u28.png' >");
        		break;
        	}
        }
    	
    }
}

function addTr(){	
	var jsonstr = document.getElementById('jsonstr').value;
	//alert(jsonstr);
	var jsonObj = eval('('+jsonstr+')');
	//debugger;
	//alert(jsonObj.length);//输出root的子对象数量 
	var trstr = "";
	for(var i=0;i<jsonObj.length;i++){
		alert("a0101:"+jsonObj[i].a0101); 
		trstr += " <tr> "+
					" <td> "+
					"	<div class=\"rmcontent\">  "+
					"		<ul>    "+
					"			<li>"+
					"				<table class=\"people-card\">"+
					"					<tr>"+
					"						<td valign=\"top\">"+
					"							<img src=\"pages/xbrm/images/photo.png\"/>"+
					"						</td>"+
					"						<td valign=\"top\">"+
					"							<div class=\"col-name\">"+jsonObj[i].a0101+"</div>"+
					"							<div class=\"col-50\">性别:男</div>"+
					"							<div class=\"col-50\">年龄:62岁</div>"+
					"							<div class=\"col-100\">职位:区委区政府研究室人事科副科长</div>"+
					"						</td>  "+
					"					</tr>"+
					"				</table>"+
					"			</li>"+
					"			<li><div class=\"step-body step-green-head\"><div class=\"step-body-head\">动议</div></div></li>"+
					"			<li><div class=\"step-body step-red\"><div class=\"step-body-head\">职数预审</div></div></li>"+
					"			<li><div class=\"step-body step-green\"><div class=\"step-body-head\">青干预审</div></div></li>"+
					"			<li><div class=\"step-body step-blue\"><div class=\"step-body-head\">查核与听取意见</div></div></li>"+
					"			<li><div class=\"step-body step-blue\"><div class=\"step-body-head\">组织考察</div></div></li>"+
					"			<li><div class=\"step-body step-black\"><div class=\"step-body-head\">讨论决定</div></div></li>"+
					"		</ul>"+
					"	</div>"+
					" </td>"+
				    "</tr>";		
	} 
	$("#newtable tr").append(trstr);	
	
	/* var grid=Ext.getCmp("gridcq");
    var store = grid.getStore();
    for(var i=0;i<store.getCount();i++){
    	store.getAt(i).set("finish","");
    }
    var data=store.getRange(0,store.getCount());
    var a=document.getElementById("js0100s_finish").value;
    var js0100s=a.split(",");
    for(var i=0;i<js0100s.length;i++){
    	for(var j=0;j<data.length;j++){
    		if(data[j].data.js0100==js0100s[i]){
    			store.getAt(j).set("finish","<img  src='image/u28.png' >");
        		break;
        	}
        }
    	
    } */
}



function setwhite(){
	var grid=Ext.getCmp("gridcq");
    var store = grid.getStore();
    for(var i=0;i<store.getCount();i++){
    	store.getAt(i).set("finish","");
    }
}
window.onload=function(){
	//民主推荐的表格,无缘无故会多一个td,加载后把它删掉
	setTimeout(function(){
		var td=$('#deletetd').find("td:last-child");
			td.remove();
	},5000);
	
}


//显示民主推荐行数
function addJs33row(){
	document.getElementById('js33row').value=parseInt(document.getElementById('js33row').value)+1;
	var js33row = document.getElementById('js33row').value;
	for(var i = 1; i <=js33row; i++){ //显示tr个数
	     $("#js33tr_"+i).show();
	}
}

//清空了其中一行
function clearjs33row(i){
	var js33row=$("#js33row").val();
	//如果删除的是最后一行 
	if(js33row==i){
		$("#js33row").val(parseInt(js33row)-1);
		$("#js3302_"+i).val("");
		$("#js3312_"+i).val("");
		$("#thyxp_"+i).val("");
		$("#thtjp_"+i).val("");
		$("#thtjfw_"+i).val("");
		$("#hyyxp_"+i).val("");
		$("#hytjp_"+i).val("");
		$("#hytjfw_"+i).val("");
		$("#xbzh_"+i).val("");
		$("#zztjqk_"+i).val("");
		$("#js33tr_"+js33row).hide();
	}
	//如果删除的不是最后一行,把数据往上移
	if(js33row>i){
		for(var j=parseInt(i)+1;j<=js33row;j++){
			var b=j-1;
			$("#js3302_"+b).val($("#js3302_"+j).val());
			$("#js3312_"+b).val($("#js3312_"+j).val());
			$("#thyxp_"+b).val($("#thyxp_"+j).val());
			$("#thtjp_"+b).val($("#thtjp_"+j).val());
			$("#thtjfw_"+b).val($("#thtjfw_"+j).val());
			$("#hyyxp_"+b).val($("#hyyxp_"+j).val());
			$("#hytjp_"+b).val($("#hytjp_"+j).val());
			$("#hytjfw_"+b).val($("#hytjfw_"+j).val());
			$("#xbzh_"+b).val($("#xbzh_"+j).val());
			$("#zztjqk_"+b).val($("#zztjqk_"+j).val());
		}
		//删除最后一行数据并隐藏
		$("#js3302_"+js33row).val("");
		$("#js3312_"+js33row).val("");
		$("#thyxp_"+js33row).val("");
		$("#thtjp_"+js33row).val("");
		$("#thtjfw_"+js33row).val("");
		$("#hyyxp_"+js33row).val("");
		$("#hytjp_"+js33row).val("");
		$("#hytjfw_"+js33row).val("");
		$("#xbzh_"+js33row).val("");
		$("#zztjqk_"+js33row).val("");
		$("#js33tr_"+js33row).hide();
		$("#js33row").val(parseInt(js33row)-1);
	}
	
}

function isHiddenQGYS(){
	//setTimeout(function(){
		var js1396 = document.getElementById("js1396").value;
		var js1397 = document.getElementById("js1397").value;
		var js9996 = document.getElementById("js9996").value;
		var js9997 = document.getElementById("js9997").value;
		//alert(js1396);alert(js1397);alert(js9996);alert(js9997);
		if(js1396&&js1397&&js9996&&js9997){
			Ext.getCmp("saveQG").addClass('bh');
		}
		//},8000);
}
</script>
