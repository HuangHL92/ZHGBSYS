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
.step-body-head{font:13px ����;color:#FFFFFF;margin-left:5px;}
.rm-panel>tbody>tr>td{height:141px;margin-left:0px;}/*background:url(bg.png);*/
/*.rmcontent li {border-left:1px solid #ccc;border-top:1px solid #ccc;float:left;width:102px;height:33px;text-align:center;line-height:33px}*/


body{
margin: 1px;overflow: auto;
font-family:'����',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*���ڿ�  */
/* .GBx-fieldset {display:none;} */
.GBx-fieldset .x-form-trigger{right: 0px;}/*ͼ�����  */
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
//�����ϴ�����ӳټ���
var fnSet={};
$h.ready = function(f,id){
	fnSet[id] = f;
}
var gridJsonStore = {}

function doAddPerson(){//������Ա
	var rbId = document.getElementById('rbId').value;
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	var dc005 = document.getElementById('dc005').value;
	if(rbId==''){
		$h.alert('ϵͳ��ʾ','��������Ϣ��');
		return;
	}
	$h.openWin('findById321','pages.xbrm.SelectPersonByName','������/���֤��ѯ ',820,520,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'������Ա',
		rbId:rbId,cur_hj:cur_hj,cur_hj_4:cur_hj_4,dc005:dc005});
			
	
}

function yjsc(){
	var rbId = document.getElementById('rbId').value;
	
	if(rbId==''){
		$h.alert('ϵͳ��ʾ','��������Ϣ��');
		return;
	}
	$h.openWin('yijianshengcheng','pages.xbrm.Yjsc','���䷽�� ',560,560,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'������Ա',
		rbId:rbId,cur_hj:'${RMHJ.DONG_YI}'});
}

function infoDelete(){//�Ƴ���Ա
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
		$h.alert('ϵͳ��ʾ','�빴ѡ��Ա��');
		return;
	}
	$h.openPageModeWin('delWin','pages.xbrm.DelPChoose','�Ƴ���Ա',400,220,{rb_id: rbId,js0100s: js0100s},contextPath);
	
	//var js0100 = document.getElementById('js0100').value;
	//var a0101 = document.getElementById('a0101').value;
	/* if(js0100s.length<3){
		$h.alert('ϵͳ��ʾ','�빴ѡ��Ա��');
		return;
	}
	Ext.Msg.buttonText.yes = '��ֹ';
	Ext.Msg.buttonText.no = '�ݻ�';
	Ext.Msg.buttonText.cancel='ȡ��';
	$h.confirm3btn("ϵͳ��ʾ��",'��������'+a0101s+'��<br/>�������ֹ������ɾ������Ա�����м�ʵ��¼�Լ�������<br/>'+
			'������ݻ��������Ӹ������Ƴ���Ա�����Ǳ�����Ա������¼��',450,function(id) { 
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


function updateNRM(){//��������������Ϣ��
	Ext.Msg.buttonText.ok = 'ȷ������';
	//Ext.Msg.buttonText.no = '�Ƴ���Ա';
	Ext.Msg.buttonText.cancel='ȡ��';
	$h.confirm("ϵͳ��ʾ��",'����������������Ա����������Ϣ������������Ϣ������֮�������ж���Ա����������Ϣ�е����ģ�ϵͳ���Զ����»�����Ϣ��',400,function(id) { 
		if("ok"==id){
			radow.doEvent('updateNRM');
		}if("cancel"==id){
			return false;
		}else{
			return false;
		}	
	});
}
//��������ѯ
function queryByNameAndIDS(list){
	radow.doEvent('queryByNameAndIDS',list);
}
function doDC(){//�������ά��
	//radow.doEvent('doDC');
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','���ά��ҳ��',520,650,{rb_id:rbid},g_contextpath);
	
}
function getCheckList2(index){
	
}
function getCheckList(gridId,fieldName,obj){
	
}

function exportSheets(){
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('ExportSheets','pages.xbrm.ExportSheets','�������',1020,620,{rb_id:rbid},g_contextpath);
	
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
				if(!document.getElementById(rdid)){//�����Ԥ��div������
					if(!!Ext.getCmp(rdid+"_tip")){//��Ӧ��tip���ڣ�������
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
			//Ԥ����Ϣ
			for(var rdid in cb){
				new Ext.ToolTip( { 
					target: rdid, 
					id : rdid+"_tip",
					html: cb[rdid],
					title: 'Ԥ��ԭ��</br>',
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
			document.getElementById('isshownrm').value='1';//��ʾΪ1
		}else{
			document.getElementById('isshownrm').value='2';//����Ϊ2
		}
	});
	
}


</script>
<%-- <%@include file="/comOpenWinInit2.jsp" %> --%>
<odin:hidden property="rbId" title="����id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="a0101" title="����"/>
<odin:hidden property="js0100" title="������Աid"/>
<odin:hidden property="docpath" title="�ĵ���ַ" />
<odin:hidden property="cur_hj" value="0" title="��ǰ����"/>
<odin:hidden property="cur_hj_4" value="4_1" title="���۾���֧����"/>
<odin:hidden property="tplb" value="" title="�������"/>
<odin:hidden property="dc005" value="1" title="����ʶ"/>
<odin:hidden property="block" value="" title="ְ��Ԥ����ʾ����"/>
<odin:hidden property="downfile" />
<odin:hidden property="tabobj" title="tabҳ��ǩ"/>
<odin:hidden property="checkboxtqyj" title="��ȡ���"/>
<odin:hidden property="isshownrm" title="��ʾ������������" value='1'/>  
<odin:hidden property="meettype" title="��������"/>
<odin:hidden property="qcjsjs0100" title="ȫ�̼�ʵ��Աid"/>
<odin:hidden property="qcjsid" title="ȫ�̼�ʵ��Ա����"/>
<odin:hidden property="rb_no" title="���α��"/>
<odin:hidden property="rb_status" title="����������"/>
<odin:hidden property="msg" value="3" title="������ʾ����"/>
<odin:hidden property="js2204s" value="" title=""/>
<odin:hidden property="js0123" title="����ְ��A0200s"/>
<odin:hidden property="js0111a" />
<odin:hidden property="js0117a" />
<odin:hidden property="jsonstr" />
<odin:hidden property="js0100s_finish" title="��Ҫ������js0100"/>
<odin:hidden property="js33row" value="1" title="�����Ƽ���ʾ����"/>
<odin:toolBar property="topbar">
	<%-- <odin:buttonForToolBar id="doDC" text="���ά��" handler="doDC" icon="image/icon021a6.gif" />
		<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="ѡ����Ա" id="doAddPerson" handler="doAddPerson" icon="image/icon021a2.gif" />
	<odin:buttonForToolBar text="�Ƴ�" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<%-- <odin:fill/>
	<odin:buttonForToolBar text="һ������" icon="image/icon021a2.gif" isLast="true" handler="yjsc" id="yjsc" /> --%>
	
	<odin:fill/>

<%-- 	<odin:buttonForToolBar text="ģ��ɲ�����" icon="image/icon021a2.gif" handler="imitate" id="imitate" isLast="true"/>--%>	
		<odin:buttonForToolBar text="һ�����ϵ���" icon="image/icon021a2.gif" handler="filedown" id="filedown" isLast="true"></odin:buttonForToolBar>

</odin:toolBar>
<odin:toolBar property="bbarid">
</odin:toolBar>

<odin:toolBar property="bbar" applyTo="bbardiv">
	<odin:buttonForToolBar text="�ɲ����佨�鷽��" cls="bh" id="btn0" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="����ɲ�����"   cls="bh" id="btn1" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="���������Lrmx"  cls="bh" id="btn6" handler="expLrmxGrid"/>
	<!-- ְ��Ԥ�� -->
	<odin:buttonForToolBar text="ְ��Ԥ���" cls="bh" id="btn4" handler="exportExcel"/>
	<!-- ���Ԥ�� -->
	<odin:buttonForToolBar text="Ԥ�󵥣����ز��ţ�" cls="bh" id="btn5" handler="exportExcel"/>
	<odin:buttonForToolBar text="Ԥ�󵥣����ݣ�" cls="bh" id="btn5_1" handler="exportExcel"/>
	<!-- �����Ƽ� -->
	<odin:buttonForToolBar text="�ֹ����쵼��ͨ������" cls="bh" id="btn2" handler="exportExcel"/>
	<odin:buttonForToolBar text="���Ƽ����������ѡ����ʡ��ί�������" cls="bh" id="btn3" handler="exportExcel"/>
				
	<!-- ��֯���� -->
	<odin:buttonForToolBar text="��֯�����" cls="bh" id="btnKc" handler="exportExcel"/>
	<!-- ���۾��� -->
	<!-- ��������ɲ����� -->
	<odin:buttonForToolBar text="��������ɲ�����" cls="bh" id="btnRmmd" handler="exportExcel"/>

	<!-- ����� -->
	<odin:buttonForToolBar text="�����" cls="bh" id="btnBjd" handler="exportExcel"/>

	<!-- ���浥 -->
	<odin:buttonForToolBar text="���浥" cls="bh" id="btnBgd" handler="exportExcel"/>
	
	<%-- <odin:buttonForToolBar text="�Ƽ����������ܱ�" cls="bh"  id="btn4" /> 
	<odin:buttonForToolBar text="�������񽻴���" cls="bh"  id="btn5" />
	<odin:buttonForToolBar text="�Ƽ�������ܱ�" cls="bh"  id="btn6" />--%>
	<!-- ���� -->
	<odin:buttonForToolBar text="�����������������ܱ�" cls="bh" id="btn7" handler="exportExcel"/>
	<!-- ���۾��� -->
	<odin:buttonForToolBar text="�ɲ����佨�鷽��" cls="bh" id="btn8" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="���ר���ɲ����佨�鷽��" cls="bh" icon="images/keyedit.gif" id="btn9" handler="exportExcel"/>
	<odin:buttonForToolBar text="��ί��ɲ����佨�鷽��" cls="bh" icon="images/keyedit.gif" id="btn10" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="��ί����Ʊ" cls="bh" id="btn11" handler="exportExcel"/>
	<odin:buttonForToolBar text="��ί����Ʊ���" cls="bh" id="btn12" handler="exportExcel"/>
	
	<!-- ��������ȡ���  -->
	<odin:buttonForToolBar text="��ǩ��" cls="bh" id="btn21" handler="importSheet"/>
	<odin:buttonForToolBar text="ί�к�" cls="bh" id="btn22" handler="authorLetter"/>
	<odin:buttonForToolBar text="���͸��ɲ��ල��" cls="bh" id="btnts" handler="tsGBJD"></odin:buttonForToolBar>
	
	
	<!-- �����Ƽ� -->
	<odin:buttonForToolBar text="�Ƽ���"  id="tjb" cls="bh"/>
<%-- 	<odin:buttonForToolBar text="�����Ƽ�"  id="savetj"  cls="bh"/> --%>
	<odin:buttonForToolBar text="�Ƽ����"  id="tjjg" handler="tjjg"  cls="bh"/>
	<odin:buttonForToolBar text="�Ƽ�����"  id="tjgzfa" handler="tjgzfa"  cls="bh"/>
		
	<!-- ����ְ -->
	<%-- <odin:buttonForToolBar text="�ֹ���(��λ)�쵼��ͨ������" cls="bh" icon="images/keyedit.gif" id="btn17" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="�ɲ�̸�����ŷ���" cls="bh"  id="btn14" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="������������" cls="bh"  id="btn13" /> --%>
	<odin:buttonForToolBar text="��ʾ" cls="bh" id="btn16" handler="exportExcel"/>
	<odin:buttonForToolBar text="��˳ɼ�" cls="bh"  id="btnss" handler="searchScore"/>
	<odin:fill></odin:fill>
	
	<%-- <odin:buttonForToolBar id="updateNRM" text="��������������Ϣ��" handler="updateNRM" icon="image/icon021a6.gif" /> --%>
	<odin:buttonForToolBar text="����ְ��Ԥ���" id="saveZS" cls="bh"/>
	<odin:buttonForToolBar text="�������Ԥ���" id="saveQG" cls="bh"/>
	
	<odin:buttonForToolBar  text="���ɹ鵵�����" id="fwexp" cls="bh" handler="fwexphandler"/>
	<!-- <div style="width:80px;height:20px;font-size:20px;background-color:#EBEBE4;"> -->
	<odin:buttonForToolBar  text="ȫ�̼�ʵ" id="qcjs" cls="bh" handler="qcjs"/>
	<odin:buttonForToolBar  text="����" isLast="true" id="save" handler="save" cls="bh"/>
</odin:toolBar>

<div id="tabs" style="display: none;">
  <ul id="ulTitle" style="_width:100%;display: none;" >
  	
  	<li onclick="setColor(1)"><img id="tip1"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-0" hj="0" fn=""  btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
  		�������</a></li>
    <li onclick="setColor(2)"><img id="tip2"  class="tip2word" src="image/u28.png" style="display: none"><a href="#tabs-1" hj="1" fn="file02" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',1],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',1],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	����</a></li>
    <li onclick="setColor(3)"><img id="tip3"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-11" hj="1" fn="file12" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',1],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',1],['saveQG',0],['save',0],['qcjs',0]]">
    	ְ��Ԥ��</a></li>
    <li onclick="setColor(4)"><img id="tip4"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-12" hj="1" fn="file13" btid="[['btnss',0],['fwexp',0],['btnts',0],['',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',1],['btn5_1',1],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',1],['save',0],['qcjs',0]]">
    	���Ԥ��</a></li>
    <li onclick="setColor(5)"><img id="tip5"  class="tip7word" src="image/u28.png" style="display: none"><a href="#tabs-13" id='khid' hj="3" fn="file99" btid="[['btnss',0],['fwexp',0],['btnts',1],['save',1],['saveQG',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn5',0],['btn5_1',0],['btn2',0],['btn3',0],['btn7',0],['btn4',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',1],['btn22',1]]">
    	�������ȡ���</a></li>
    <li onclick="setColor(6)"><img id="tip6"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-2" hj="2" fn="file19" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',1],['tjb',1],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0],['tjgzfa',1]]">
    	�����Ƽ�</a></li>
    <!-- <li ><a href="#tabs-3" hj="3" fn="file04" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	������ѡȷ��</a></li> -->
    <li onclick="setColor(7)"><img id="tip7"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-4" hj="6" fn="file14" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',1],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	��֯����</a></li>
    <!-- <li ><a href="#tabs-5" hj="3" fn="file06" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	����</a></li> -->
    <!-- <li ><a href="#tabs-6" hj="4" fn="file07" btid="[['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',1],['btn9',1],['btn10',1],['btn11',1],['btn12',1],['btn14',0],['btn16',0],['btn17',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	���۾���</a></li> -->
    <li onclick="setColor(8)"><img id="tip8"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-6" hj="4" fn="file07" btid="[['btnss',0],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btnRmmd',1],['btnBjd',1],['btnBgd',1],['btn21',0],['btn22',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',0]]">
    	���۾���</a></li>
    <li onclick="setColor(9)"><img id="tip9"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-7" hj="5" fn="file08" btid="[['btnss',1],['fwexp',0],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	��ǰ��ʾ</a></li>
    <li onclick="setColor(10)"><img id="tip10"  class="tip" src="image/u28.png" style="display: none"><a href="#tabs-16" hj="5" btid="[['btnss',0],['fwexp',1],['btnts',0],['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn5_1',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',0],['btn16',0],['btn17',0],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	���ĵǼ�</a></li>
    <!-- <li ><a href="#tabs-8" hj="5" fn="file09" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['saveQG',0],['save',1],['qcjs',1]]">
    	�������</a></li>
    <li ><a href="#tabs-9" hj="5" fn="file10" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	������</a></li>
    <li ><a href="#tabs-10" hj="5" fn="file11" btid="[['tjgzfa',0],['tjjg',0],['tjb',0],['btn0',0],['btn2',0],['btnKc',0],['btn3',0],['btn4',0],['btn5',0],['btn6',0],['btn7',0],['btn8',0],['btn9',0],['btn10',0],['btn11',0],['btn12',0],['btn14',1],['btn16',1],['btn17',1],['btnRmmd',0],['btnBjd',0],['btnBgd',0],['btn21',0],['btn22',0],['saveZS',0],['saveQG',0],['save',1],['qcjs',1]]">
    	�������</a></li> -->
    <%-- <li ><odin:button text="�������" property="btn3" handler="exportSheets" /></li> --%>
  </ul>
	<div style="width: 100%; margin-top: 3px;">
	  <div style="width: 350px;float: left;" id="gridDiv">
		<odin:editgrid2 property="gridcq"  topBarId="topbar" grouping="true" groupCol="dc001" 
		bbarId="bbarid"  load="selectRow"  isFirstLoadData="false"  width="350"  pageSize="9999" 
		afteredit="ffed" clicksToEdit="false" 
		groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"��\" : \"��\"]})&nbsp;&nbsp;&nbsp;&nbsp;(ע����������ק)"
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
				<%-- <odin:gridColumn dataIndex="a019998" header="Ԥ��" width="30" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="a0101" header="����" width="60" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="�������" sortable="false" width="100" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="�������" sortable="false" width="85" edited="false"   editor="text" editorId="dc001" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001_2" menuDisabled="true" header="̸���������" hidden="true" sortable="false" width="180" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001_2" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="Ԥ��" renderer="yjrenderer.rd" sortable="false" width="45" edited="false" editor="text" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="Ԥ��" renderer="showLink" sortable="false" width="50" edited="false" editor="text" align="center" />
				
				<odin:gridEditColumn2 dataIndex="js01001" header="id" hidden="true" width="45" align="center" editor="text" edited="false" />
				
				<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" hidden="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
				<odin:gridEditColumn2 dataIndex="havefine" menuDisabled="true" header="������Ϣ" renderer="showLinkFine" sortable="false" width="60" edited="false" editor="text" align="center"/>
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
	      <odin:groupBoxNew contentEl="all" property="gp1" title="��������Ϣ" collapsible="true">
	      	<div id="all">
	      		<div id="pi">
	        	<img alt="������Ƭ" id="personImg" style="cursor:default; float: left;"  width="100" height="124" src="" onclick="rmbView()">
	        	<div id="pdata" style=" height: 100%; float: left;padding-left: 10px;"></div>
	        	<%-- <div style="position: absolute;right: 30px;top: 95px">
	        		<odin:button text="����" property="save" ></odin:button>
	        	</div> --%>
	        </div>
	        <div style="height: 5px;float: left;width: 100%;padding: 0px;margin: 0px;font-size: 0px;"></div>
	        <div  class="marginbottom0px GBx-fieldset" >
	        	<odin:hidden property="a5369"/>
	        	<table  style="width: 100%">
	        			<%-- <tr>
							<td class="titleTd" rowspan="1" width="10%" colspan="1">����ְ��</td>
							<odin:textarea property="js0108" maxlength="100" title="����ְ��" rows="5" colspan="1" />
							<td class="titleTd" colspan="1" width="10%" rowspan="1">����ְ��</td>
							<odin:textarea property="js0111" ondblclick='nrzwtop()' maxlength="50" title="����ְ��" rows="5" colspan="1"  readonly='true' />
							<td class="titleTd" colspan="1" width="10%" rowspan="1">����ְ��</td>
							<odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="����ְ��" rows="5" colspan="1" readonly='true'/>
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
							<td class="titleTd" rowspan="1" width="10%" colspan="1">����ְ��</td>
							<%-- <odin:textarea property="js0108" maxlength="100" title="����ְ��" rows="2" colspan="5" /> --%>
							<odin:textEdit property="js0108" maxlength="1000" title="����ְ��" colspan="5" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">����ְ��</td>
							<%-- <odin:textarea property="js0111" ondblclick='nrzwtop()' maxlength="50" title="����ְ��" rows="2" colspan="5"  readonly='true' /> --%>
							<odin:textEdit property="js0111" ondblclick='nrzwtop()' maxlength="1000" title="����ְ��" colspan="5"  readonly='true' />
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">����ְ��</td>
							<%-- <odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="����ְ��" rows="2" colspan="5" readonly='true'/> --%>
							<odin:textEdit property="js0117" ondblclick="nmzw('top')" maxlength="1000" title="����ְ��" colspan="5" readonly='true'/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" width="10%" rowspan="1">��ע</td>
							<%-- <odin:textarea property="js0117" ondblclick="nmzw('top')" maxlength="50" title="����ְ��" rows="2" colspan="5" readonly='true'/> --%>
							<odin:textEdit property="js0124" maxlength="1000" title="��ע" colspan="5" />
						</tr>
						<tr>
							<td class="titleTd"  colspan="1">����ְʱ��</td>
							<odin:textEdit property="js0109"  title="����ְʱ��"   colspan="2" />
							<td class="titleTd" width="20%" colspan="1">��ְ����ʱ��</td>
							<odin:textEdit property="js0110" title="��ͬ��ʱ��"   colspan="2" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">���ε�λ</td>
							<%-- <odin:select2 queryDelay="false" property="js0115" title="���ε�λ" colspan="2" /> --%>
							<odin:textEdit property="js0115" title="���ε�λ" colspan="2" readonly='true'></odin:textEdit>
								
							<td class="titleTd" colspan="1">���ε�λ</td>
							<%-- <odin:select2 queryDelay="false" property="js0116" title="���ε�λ" colspan="2" /> --%>
							<odin:textEdit property="js0116value" title="���ε�λ" readonly='true'></odin:textEdit>
							<odin:hidden property="js0116"/>
						</tr>
	        	</table>
	        	</div>
	      	</div>
	      </odin:groupBoxNew>
		  	      
	      <%-- <odin:groupBox property="gp1" title="��������Ϣ">
	       
	      </odin:groupBox> --%>
	      </div>
	      
	     <%-- <div id='togglebtn' align='center' style="margin-top:5px;margin-buttom:2px;height:20px;width:100%;">
	        	<!-- <img id="upimg" src="images/nrmup.png"> -->
	        	<odin:button  property="showOrhide" handler="showOrhide" text="������غ���ʾ��������Ϣ"></odin:button>
	      		
	      </div> --%>
	      
	      <div id="tabs-0" style="float: left;" class="GBx-fieldset GBxDis">
	      	<odin:groupBoxNew contentEl="jibenqingkuang" collapsible="true" property="jbqkGB"  title="�������">
	      		<div id="jibenqingkuang" class="marginbottom0px">
					<table style="width: 100%;">
						<tr>
							<td width="13%" class="titleTd" colspan="1">����</td>
							<odin:textEdit property="js0102" width="'100%'" title="����" colspan="1" readonly="true" />
							<td width="13%" class="titleTd" colspan="1">�Ա�</td>
							<odin:select2 queryDelay="false" property="js0114"  title="�Ա�" codeType="GB2261" readonly="true" colspan="1" />
							<td width="13%" class="titleTd" colspan="1">��������</td>
							<odin:textEdit property="js0103" width="'100%'" title="��������" colspan="1" readonly="true" />
						</tr>
						<%-- <tr>
							
							<td noWrap="nowrap" align=right><span id="a0194ASpanId" style="FONT-SIZE: 12px">���㹤������</span>&nbsp;</td>
							<td >
								<table  ><tr>
									<odin:numberEdit property="a0194A"  maxlength="3" width="72" />
									<td><span style="font: 12px">��</span></td>
									<odin:numberEdit property="monthB" maxlength="3" width="72" />
									<td><span style="font: 12px">��</span></td>
								</tr></table>
							</td>
							
						</tr> --%>
						<tr>
							<td class="titleTd" colspan="1">����ʱ��</td>
							<odin:textEdit property="js0104" width="'100%'" title="����ʱ��" readonly="true" colspan="1" />
							<td class="titleTd" colspan="1">�뵳ʱ��</td>
							<odin:textEdit property="js0105" width="'100%'" title="�뵳ʱ��" readonly="true" colspan="1" />
							<td class="titleTd" colspan="1">ѧ��ѧλ</td>
							<odin:textEdit property="js0106" width="'100%'" title="ѧ��ѧλ" readonly="true" colspan="1" />
						</tr>
						
						
					</table>
				</div>
	      	</odin:groupBoxNew>
	      	<%-- <odin:groupBox property="jbqkGB" title="�������"  >
	      		
	      	</odin:groupBox> --%>
	      	<%-- <odin:tab id="extTab1" width="700">
				<odin:tabModel >
					<odin:tabItem title="ְ��Ǽ�" id="extTab1_1"></odin:tabItem>
					<odin:tabItem title="��ְ�ʸ�" isLast="true" id="extTab1_2" ></odin:tabItem>
				</odin:tabModel>
			</odin:tab> --%>
	      </div>
		  <div id="tabs-1" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="dongyi" property="dyGB" title="��Ա��ʼ���" collapsible="true">
		  		<div id="dongyi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="22%" class="titleTd" colspan="2">��ȱְλ</td>
							<td class="titleTd" colspan="2">��λҪ��</td>
							<td class="titleTd" colspan="1">�䱸����</td>
						</tr>
						<tr>
							<odin:textarea property="js0202" title="��ȱְλ" maxlength="200" colspan="2" rows="5"/>
							<odin:textarea property="js0203" title="��λҪ��" maxlength="500" colspan="2" rows="5"/>
							<odin:textarea property="js0204" title="�䱸����" maxlength="500" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td width="70%" class="titleTd" colspan="4">�䱸����</td>
							<td width="30%" class="titleTd" colspan="1">��ע</td>
							
						</tr>
						<tr>
							<odin:textarea property="js0205"  maxlength="500" title="�䱸����" colspan="4" rows="5"/>
							<odin:textarea property="js0206"  maxlength="500" title="��ע" colspan="1" rows="5"  />
						</tr>
						
						<tr id="needReportTr">
							<td width="30%" class="titleTd" colspan="1">�Ƿ���Ҫ��ǰ����</td>
							<odin:select2 onchange="approvechange()" colspan="4" property="js0208" title="�Ƿ���Ҫ��ǰ����" value='2' data="['1','��'],['2','��']"></odin:select2>
						</tr>
						<tr  style="display:none;" id="psreportTr">
							<td width="30%" class="titleTd" colspan="1">��������</td>
							<odin:select2 onchange="resonchange()" colspan="4" size="79" property="js0209" codeType="PSYESREASON" value='' title="��������" queryDelay="false"></odin:select2>
						</tr>
						<tr style="display:none;" id="psreportvalueTr">
							<td width="30%" class="titleTd" colspan="1">��������</td>
							<odin:textarea colspan="4" rows="3" title="��������" property="js0210" maxlength="1000"></odin:textarea>
						</tr>
						<tr style="display:none;" id="aproveTr">
							<td width="30%" class="titleTd" colspan="1">�쵼����</td>
							<odin:select2 colspan="4" property="js0211" title="�쵼����" value='2' data="['1','ͨ��'],['2','δͨ��']"></odin:select2>
						</tr>
						
						
						<tr>
							<tags:JUpload3 property="file02" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="5"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		    <%-- <odin:groupBox property="dyGB" title="��Ա��ʼ���"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  
		  <div id="tabs-11" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="yushen" property="zsysGB" title="ְ��Ԥ��" collapsible="true">
		  		<div id="yushen" class="marginbottom0px">
					<table style="width: 100%">
						
						<tr>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">���䱸�ɲ��ĵ�����λ�������������</td>
							<td width="10%" class="titleTd" colspan="2">ְ���㼶</td>
							<td width="10%" class="titleTd" colspan="2">�˶�ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="12%" class="titleTd" colspan="2">�Ƿ�ְ��</td>
							<td width="15%" class="titleTd" colspan="1" rowspan="2">��ע</td>
							<td width="4%" class="titleTd" colspan="1" rowspan="2">��<br>��</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">����</td>
							<td class="titleTd" colspan="1">����</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼<br>ְ��</td>
							<td class="titleTd" colspan="1">����<br>��ְ<br>��</td>
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
							    <tags:rmbPopWinInput property="<%=js1201_i %>" cls="width-80 height-40 no-y-scroll" label="������" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="" hiddenValue=""/>
							        <tags:rmbPopWinInput2 property="<%=js1201_i %>"  label="���䵥λ" 
							    title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData" 
							    defaultValue="" hiddenValue=""/> 
							    </td>--%>
							    
							    <%-- <tags:PublicTextIconEdit isLoadData="false"  property="<%=js1201_i %>" label2="���䵥λ"  codetype="orgTreeJsonData" colspan="1" readonly="true" /> --%> 
							    <%-- <tags:PublicTextIconEdit3  property="a0195" readonly="true" label="ͳ�ƹ�ϵ���ڵ�λ" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3> --%>
							    <%-- <odin:textEdit  title="��λ����" colspan="1" ></odin:textEdit> --%>
														  	
							   	<tags:PublicTextIconEdit3 onfocus="<%=changeValue %>"  property="<%=js1201_i %>" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3>
								
								<%--  --%>
															  	
							    <td colspan="1" id="<%=td_js1202_1_i %>">
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_1 %>" value="1" checked="checked" />
							    </td>
							    <td colspan="1" id="<%=td_js1202_2_i %>" >
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_2 %>" value="0" />
							    </td>
							    <odin:numberEdit property="<%=js1203_i %>" maxlength="3" width="'100%'" title="�˶�ְ���쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1204_i %>" maxlength="3" width="'100%'" title="�˶�ְ�����쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1205_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1206_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1207_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1208_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1209_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" />
							    <odin:numberEdit property="<%=js1210_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" />
						       <%--  <odin:select2 property="<%=js1211_i %>"  width="'100%'" title="��ֵ���쵼ְ��" colspan="1" codeType="XZ09" value="0"/>
 							    <odin:select2 property="<%=js1212_i %>"  width="'100%'" title="��ֵ�����쵼ְ��" colspan="1" codeType="XZ09" value="0"/> --%>
							  <%--  	<odin:select queryDelay="false" property="<%=js1211_i %>" data="['0','��'],['1','��']" width="'100%'" title="��ֵ���쵼ְ��" colspan="1" value="0"></odin:select>
							    <odin:select queryDelay="false" property="<%=js1212_i %>" data="['0','��'],['1','��']" width="'100%'" title="��ֵ�����쵼ְ��" colspan="1" value="0"></odin:select> --%>
							   	<odin:hidden property="<%=js1211_i %>" value="0" title="��ֵ���쵼ְ��"/>
							   	<td>
								   	<select onchange="<%=czsldzschange %>" id="<%=selectjs1211_i %>">
								   		<option value="0" selected>��</option>
							            <option value="1">��</option>
	        						</select>
							   	</td>
							   	<odin:hidden property="<%=js1212_i %>" value="0" title="��ֵ�����쵼ְ��"/>
							   	<td>
								   	<select onchange="<%=czsfldzschange %>" id="<%=selectjs1212_i %>">
								   		<option value="0" selected>��</option>
							            <option value="1">��</option>
	        						</select>
							   	</td>
							    <odin:textarea property="<%=js1213_i %>"  maxlength="500" title="��ע" colspan="1" rows="3"/>
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
						    <td colspan="1" class="titleTd">�ɲ��ල�����</td>
						   <odin:textarea property="js1214"  maxlength="500" title="�ɲ��ල�����" colspan="14" rows="2" readonly="true"/>
						</tr>
						
						<tr>
						    <td colspan="15" class="titleTd">
						        <div id="addrowBtn" style="position:relative;margin-left:3px;width:120px;border:1px solid #7b9ebd;background:url(images/add.png) transparent no-repeat 1px center;border-radius:5px;-moz-border-radius:5px;cursor:pointer;" onclick="addArow()">
										<span style="line-height:20px;padding-left:18px;font-size:13px;">����Ԥ��ְ��</span>
								</div>
						    </td>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		   <%--  <odin:groupBox property="zsysGB" title="ְ��Ԥ��"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  
		  <div id="tabs-13" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="kaoheyijian" property="khyjGB" title="��������ȡ�������ѯί�к�" collapsible="true">
		  		<div id="kaoheyijian" class="marginbottom0px">
					<table style="width: 100%">
						<tr rowspan="2">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">��ǩ���</td>
							<td width="14%" class="titleTd" colspan="1">��ȡ���</td>
							<td style="border:0px;style="width:500px;"" colspan="2">
								<odin:checkBoxGroup  property="tqyjcheck" data="['name1', '�ͼ������'],['name2', '��������'],['name3', '��Ժ'],['name4', '���Ժ'],['name5', '������������'],['name6', '����']"/>
							</td>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1">����������</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="1" label="��"></odin:radio>
							</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="2" label="��"></odin:radio>
							</td>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">��������</td>
							<odin:textarea property="gzsy" title="��������" colspan="2" rows="5" maxlength="700"></odin:textarea>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">��Ϣ��ѯί�к���������</td>
							<odin:textarea property="qtsy" title="��Ϣ��ѯί�к���������" colspan="2" rows="5" maxlength="700"></odin:textarea>
						</tr>
						<tr>
							<td class="titleTd" colspan="2">�ɲ��ල���������</td>
							<odin:textEdit property="crp010" readonly='true'></odin:textEdit>
						</tr>
						<tr>
							<tags:JUpload3 property="file99" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
						
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		  	<%-- <odin:groupBox property="khyjGB" title="��������ȡ�������ѯί�к�">
	      		
	      	</odin:groupBox> --%>
		  </div>

		  <div id="tabs-12" style="float: left;" class="GBx-fieldset GBxDis" >
		  		<odin:groupBoxNew contentEl="qinggan" property="qgysGB" title="��ʡֱ��������ҵ��λ��������ɲ�ѡ��ר��Ԥ��" collapsible="true">
		  			<div id="qinggan" class="marginbottom0px">
                        <table style="width: 100%">
                            <tr>
                                <td class="titleTd" colspan="2" rowspan="2">��Ŀ</td>
                                <td class="titleTd" colspan="7">ְ��</td>

                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1" width="7%">������</td>
                                <td class="titleTd" colspan="1" width="7%">������</td>
                                <td class="titleTd" colspan="1" width="7%">Ѳ��Ա</td>
                                <td class="titleTd" colspan="1" width="7%">��Ѳ<br/>��Ա</td>
                                <td class="titleTd" colspan="1" width="7%">�ϼ�</td>
                                <td class="titleTd" colspan="1" width="7%">����ɲ�ռ��</td>
                                <td class="titleTd" colspan="1" width="58%">��ע</td>

                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3" width="10%">���䱸<br/>���</td>
                                <td class="titleTd" colspan="1" width="10%">����</td>
                                <odin:numberEdit property="r1c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r1c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r1c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                                <td rowspan="6">
                                    <textarea id="r1c7" name="r1c7" rows="12" cols="46"></textarea>
                                </td>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45����������</td>
                                <odin:numberEdit property="r2c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r2c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r2c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">80������</td>
                                <odin:numberEdit property="r3c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r3c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r3c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3">�����<br/>���</td>
                                <td class="titleTd" colspan="1">����</td>
                                <odin:numberEdit property="r4c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r4c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r4c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45����������</td>
                                <odin:numberEdit property="r5c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r5c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r5c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1">80������</td>
                                <odin:numberEdit property="r6c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue()"/>
                                <odin:numberEdit property="r6c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="r6c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">ʡί��֯���ɲ��滮��������
                                </td>
                                <odin:textarea property="js1396" maxlength="500"
                                               title="ʡί��֯���ɲ��滮��������" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">ʡί��֯�����쵼�������
                                </td>
                                <odin:textarea property="js1397" maxlength="500"
                                               title="ʡί��֯�����쵼�������" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">������<br/>��ʵ���
                                </td>
                                <odin:textarea property="js1398" maxlength="500"
                                               title="��������ʵ���" colspan="8" rows="4"/>
                            </tr>


                        </table>
                    </div>
		  		</odin:groupBoxNew>
               <%--  <odin:groupBox property="qgysGB" title="�������м���������ҵ��λ�ش�������ɲ�ѡ��ר��Ԥ��">
                    
                </odin:groupBox> --%>
				
				<odin:groupBoxNew contentEl="qinggan1" property="qgysGB2" title="�У��ݣ���������ɲ�ѡ��ר��Ԥ��" collapsible="true">
					<div id="qinggan1" class="marginbottom0px">
                        <table style="width: 100%">
                            <tr>
                                <td class="titleTd" colspan="2" rowspan="2">��Ŀ</td>
                                <td class="titleTd" colspan="7">ְ��</td>

                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1" width="7%">������</td>
                                <td class="titleTd" colspan="1" width="7%">������</td>
                                <td class="titleTd" colspan="1" width="7%">Ѳ��Ա</td>
                                <td class="titleTd" colspan="1" width="7%">��Ѳ<br/>��Ա</td>
                                <td class="titleTd" colspan="1" width="7%">�ϼ�</td>
                                <td class="titleTd" colspan="1" width="7%">����ɲ�ռ��</td>
                                <td class="titleTd" colspan="1" width="58%">��ע</td>

                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3" width="10%">���䱸<br/>���</td>
                                <td class="titleTd" colspan="1" width="10%">����</td>
                                <odin:numberEdit property="t2r1c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r1c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r1c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                                <td rowspan="6">
                                    <textarea id="t2r1c7" name="t2r1c7" rows="12" cols="46"></textarea>
                                </td>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45����������</td>
                                <odin:numberEdit property="t2r2c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r2c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r2c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">80������</td>
                                <odin:numberEdit property="t2r3c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r3c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r3c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1" rowspan="3">�����<br/>���</td>
                                <td class="titleTd" colspan="1">����</td>
                                <odin:numberEdit property="t2r4c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r4c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r4c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td class="titleTd" colspan="1">45����������</td>
                                <odin:numberEdit property="t2r5c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r5c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r5c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>
                            <tr>
                                <td class="titleTd" colspan="1">80������</td>
                                <odin:numberEdit property="t2r6c1" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c2" maxlength="5" width="'100%'"
                                                 title="������" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c3" maxlength="5" width="'100%'"
                                                 title="Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c4" maxlength="5" width="'100%'"
                                                 title="��Ѳ��Ա" colspan="1" onchange="changeTableValue2()"/>
                                <odin:numberEdit property="t2r6c5" maxlength="5" width="'100%'"
                                                 title="�ϼ�" colspan="1" readonly="true"/>
                                <odin:numberEdit property="t2r6c6" maxlength="5" width="'100%'"
                                                 title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">ʡί��֯���ɲ��滮��������
                                </td>
                                <odin:textarea property="js9996" maxlength="500"
                                               title="ʡί��֯���ɲ��滮��������" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">ʡί��֯�����쵼�������
                                </td>
                                <odin:textarea property="js9997" maxlength="500"
                                               title="ʡί��֯�����쵼�������" colspan="8" rows="4" readonly="true" />
                            </tr>

                            <tr>
                                <td colspan="1" class="titleTd">������<br/>��ʵ���
                                </td>
                                <odin:textarea property="js9998" maxlength="500"
                                               title="��������ʵ���" colspan="8" rows="4"/>
                            </tr>


                        </table>
                    </div>
				</odin:groupBoxNew>
                <%-- <odin:groupBox property="qgysGB2" title="�أ��С������ش�������ɲ�ѡ��ר��Ԥ��">
                    
                </odin:groupBox> --%>
		  </div>

		  <div id="tabs-2" class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="minzhutuijian" property="mztjGB" title="�����Ƽ�" collapsible="true">
		  		<div id="minzhutuijian" class="marginbottom0px">
	      			<table style="width: 100%">
						<tr>
						    <td width="14.28%" class="titleTd" colspan="1">�Ƽ�ְλ</td>
							<odin:textEdit property="js2002" width="'100%'" title="�Ƽ�ְλ" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�Ƽ���ѡ��Χ</td>
							<odin:textEdit property="js2003" width="'100%'" title="�Ƽ���ѡ��Χ" colspan="1" />					
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�Ƽ���ѡ����Ҫ��</td>
							<odin:textarea property="js2004"  title="�Ƽ���ѡ����Ҫ��" colspan="1" rows="5"/>				
						</tr>
					
						<tr>
							<td class="titleTd" colspan="1" >�μ��Ƽ�����</td>
							<odin:textarea property="js2005"  title="�μ��Ƽ�����" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >�Ƽ�����</td>
							<odin:textarea property="js2006"  title="�Ƽ�����" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >��ע</td>
							<odin:textarea property="js2007" title="��ע" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file19" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="6"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
					<br/>
	      			<table width="100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">��������</td>
							<td width="12%" class="titleTd" colspan="1">�Ƽ�ʱ��</td>
							<td width="10%" class="titleTd" colspan="1">�μ�����</td>
							<td  class="titleTd" colspan="1">����</td>
						</tr>
						<tr>
							<odin:textEdit  property="tjrs" colspan="1" />
							<odin:dateEdit format="Ymd" property="tjsj" title="�Ƽ�ʱ��" colspan="1" />
							<odin:numberEdit property="tjcjrs" maxlength="5" width="'100%'" title="�μ�����" colspan="1" />
							<odin:textEdit  property="tjother" colspan="1" />
						</tr>
	      			</table>
	      			<br>
					<table style="width: 100%">
							<tr id="deletetd">
							<td width="10%" class="titleTd" colspan="1" rowspan="2">����</td>
							<td width="13%" class="titleTd" colspan="1" rowspan="2">������λ��ְ��</td>
							<td width="28%" class="titleTd" colspan="3"≯���Ƽ�</td>
							<td width="28%" class="titleTd" colspan="3">�����Ƽ�</td>
							<td width="10%" class="titleTd" colspan="1" rowspan="2">������ѡ���ۺ����</td>
							<td width="18%" class="titleTd" colspan="1" rowspan="2">�����Ƽ�����֯�Ƽ����</td>
						    <td width="3%" class="titleTd" colspan="1" rowspan="2">���<td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">��ЧƱ��</td>
							<td class="titleTd" colspan="1">��Ʊ��</td>
							<td class="titleTd" colspan="1"≯���Ƽ���Χ</td>
							<td class="titleTd" colspan="1">��ЧƱ��</td>
							<td class="titleTd" colspan="1">��Ʊ��</td>
							<td class="titleTd" colspan="1">�����Ƽ���Χ</td>
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
							<odin:textEdit property="<%=js3302_i %>" colspan="1" title="����"/>
							<odin:textEdit property="<%=js3312_i %>" colspan="1" title="������λ��ְ��"/>
							<odin:numberEdit property="<%=thyxp_i %>" maxlength="5" width="'100%'" title="��ЧƱ��" colspan="1" />
							<odin:numberEdit property="<%=thtjp_i %>" maxlength="5" width="'100%'" title="��Ʊ��" colspan="1" />
							<odin:textarea property="<%=thtjfw_i %>" maxlength="200" title="̸���Ƽ���Χ" colspan="1"></odin:textarea>
							<odin:numberEdit property="<%=hyyxp_i %>" maxlength="5" width="'100%'" title="��ЧƱ��" colspan="1" />
							<odin:numberEdit property="<%=hytjp_i %>" maxlength="5" width="'100%'" title="��Ʊ��" colspan="1" />
							<odin:textarea property="<%=hytjfw_i %>" maxlength="200" title="�����Ƽ���Χ" colspan="1"></odin:textarea>
							<odin:textarea property="<%=xbzh %>"  title="������ѡ���ۺ����" colspan="1" rows="2"/>
							<odin:textarea property="<%=zztjqk_i %>"  title="�����Ƽ�����֯�Ƽ����" colspan="1" rows="2"/>
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
										<span style="line-height:20px;padding-left:18px;font-size:13px;">����</span>
								</div>
						    </td>
						</tr>
					</table>
										
					<br>
					<table id="tablehide" style="width: 100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">�Ƽ���ʽ</td>
							<td width="18%" class="titleTd" colspan="1">�Ƽ�ʱ��</td>
							<td width="18%" class="titleTd" colspan="1">�μ�����</td>
							<td width="18%" class="titleTd" colspan="1">��Ʊ��</td>
							<td width="18%" class="titleTd" colspan="1">����</td>
							<td width="18%" class="titleTd" colspan="1">��ע</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�����Ƽ�</td>
							<odin:dateEdit format="Ymd" property="js0302" title="�Ƽ�ʱ��" colspan="1" />
							<odin:numberEdit property="js0303" maxlength="5" width="'100%'" title="�μ�����" colspan="1" />
							<odin:numberEdit property="js0304" maxlength="5" width="'100%'" title="��Ʊ��" colspan="1" />
							<odin:numberEdit property="js0305" maxlength="5" width="'100%'" title="����" colspan="1" />
							<odin:textEdit property="js0306" maxlength="250"  width="'100%'" title="��ע" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1"≯���Ƽ�</td>
							<odin:dateEdit format="Ymd" property="js0307" title="�Ƽ�ʱ��" colspan="1" />
							<odin:numberEdit property="js0308" maxlength="5" width="'100%'" title="�μ�����" colspan="1" />
							<odin:numberEdit property="js0309" maxlength="5" width="'100%'" title="��Ʊ��" colspan="1" />
							<odin:numberEdit property="js0310" maxlength="5" width="'100%'" title="����" colspan="1" />
							<odin:textEdit property="js0311" maxlength="250" width="'100%'" title="��ע" colspan="1" />
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  	
		  	<%-- <odin:groupBox property="mztjGB" title="�����Ƽ�"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-3" class="GBx-fieldset">
		  	<odin:groupBox property="kcrxqdGB" title="������ѡȷ��"  >
		  		<div id="kaocharenxuanqued" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="14%"class="titleTd" colspan="1">�¼���ί�����飩���</td>
							<odin:textarea property="js0402" title="�¼���ί�����飩���" colspan="1" rows="5"/>
							<td width="11%" class="titleTd" colspan="1">����������</td>
							<odin:textarea property="js0403" title="����������" colspan="1" rows="5"/>
						</tr>
						<%-- <tr>
							<tags:JUpload3 property="file03" label="�ϴ�����03" fileTypeDesc="�����ļ�"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr> --%>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		<%--   <div id="tabs-4" class="GBx-fieldset">
		 	<odin:groupBox property="zzkcGB" title="��֯����"  >
		 		<div id="zuzhikaocha" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td class="titleTd" colspan="1" rowspan="2">����ʱ��</td>
							<odin:dateEdit format="Ymd" property="js0502" width="'100%'"  title="����ʱ��" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">����̸������</td>
							<odin:textEdit property="js0503" width="'100%'" title="����̸������" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">�Ƿ񿼲�Ԥ��</td>
							<odin:select2 codeType="XZ09"  property="js0504" title="�Ƿ񿼲�Ԥ��" colspan="1" />
							<td class="titleTd" colspan="1" rowspan="2">�����������
								<input type="text" name="js0505" id="js0505" style="width: 100%;" class=" x-form-text x-form-field">
							</td>
							<td class="titleTd" colspan="1">����</td>
							<td class="titleTd" colspan="1">�Ƚ�����</td>
							<td class="titleTd" colspan="1">������</td>
							<td class="titleTd" colspan="1">���˽�</td>
						</tr>
						<tr>
							<odin:textEdit property="js0506" width="'100%'" title="����" colspan="1"/>
							<odin:textEdit property="js0507" width="'100%'" title="�Ƚ�����" colspan="1"/>
							<odin:textEdit property="js0508" width="'100%'" title="������" colspan="1"/>
							<odin:textEdit property="js0509" width="'100%'" title="���˽�" colspan="1"/>
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="1" rowspan="2">�����������</td>
							<td width="13%" class="titleTd" colspan="1">�μӲ�������</td>
							<td width="7%" class="titleTd" colspan="1">����</td>
							<td width="8%" class="titleTd" colspan="1">��ְ</td>
							<td width="7%" class="titleTd" colspan="1">������ְ</td>
							<td width="8%" class="titleTd" colspan="1">����ְ</td>
							<td width="9%" class="titleTd" colspan="1" rowspan="2">��������������</td>
							<td width="9%" class="titleTd" colspan="1">�μ�����</td>
							<td width="9%" class="titleTd" colspan="1">ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��Ȩ</td>
						</tr>
						<tr>
							<odin:textEdit property="js0510" width="'100%'" title="�μӲ�������" colspan="1"/>
							<odin:textEdit property="js0511" width="'100%'" title="����" colspan="1"/>
							<odin:textEdit property="js0512" width="'100%'" title="��ְ" colspan="1"/>
							<odin:textEdit property="js0513" width="'100%'" title="������ְ" colspan="1"/>
							<odin:textEdit property="js0514" width="'100%'" title="����ְ" colspan="1"/>
							<odin:textEdit property="js0515" width="'100%'" title="�μ�����" colspan="1"/>
							<odin:textEdit property="js0516" width="'100%'" title="ͬ��" colspan="1"/>
							<odin:textEdit property="js0517" width="'100%'" title="��ͬ��" colspan="1"/>
							<odin:textEdit property="js0518" width="'100%'" title="��Ȩ" colspan="1"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >�����з�ӳ�����</td>
							<odin:textarea property="js0519"  title="�����з�ӳ�����" colspan="5" rows="5"/>
							<td class="titleTd" colspan="1" >�˲����</td>
							<odin:textarea property="js0520"  title="�˲����" colspan="4" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >���������</td>
							<odin:textarea property="js0521" title="���������" colspan="10" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file05" label="�ϴ�����05" fileTypeDesc="�����ļ�"  colspan="11"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div> --%>
		    <div id="tabs-4" class="GBx-fieldset GBxDis">
		    <odin:groupBoxNew contentEl="zuzhikaocha" collapsible="true" title="��֯����" property="zzkcGB">
		    	<div id="zuzhikaocha" class="marginbottom0px">
		 			<table style="width: 100%">
		 				<odin:tabLayOut />
		 				<tr>
		 					<td style="width:25%;" class="titleTd" colspan="1">�Ƿ����о�ȷ��������ѡ</td>
		 					<td style="width:25%;">
		 						<odin:radio  property="sfjtyj" value="1" label="��"></odin:radio>
		 						<odin:radio  property="sfjtyj" value="0" label="��"></odin:radio>
		 					</td>
		 					<td style="width:25%;" class="titleTd" colspan="1">���������ų���%</td>
		 					<odin:numberEdit style="width:80px;" maxlength="5" property="cpycl" title="���������ų���%"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">����������</td>
		 					<odin:textarea property="zqyjqk"  title="����������" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">�ͼ죨��죩�������</td>
		 					<odin:textarea property="jjjdyj"  title="�ͼ죨��죩�������" rows="5" colspan="1"/>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">�����ڼ����޾ٱ�</td>
		 					<odin:textarea property="kcqjjb"  title="�����ڼ����޾ٱ�" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">������</td>
		 					<odin:textarea property="hcqk"  title="������" rows="5" colspan="1"/>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">��������ȿ������</td>
		 					<odin:textarea readonly='true'  property="jsnkhqk"  title="��������ȿ������" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">�ɲ�����������������һ��ݡ�������</td>
		 					<td>
		 						<table style="width: 100%;">
									<tr style="height: 15px;">
										<td  style="width: 20%; height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="1" checked="checked" onclick="Check1()">
										</td>
										<td  style="width: 80%; height: 15px;">
											<label style="font-size: 12" >ͨ��</label>
										</td>
									</tr>
									<tr>
										<td style="height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="2" onclick="Check2()">
										</td>
										<td style="height: 15px;">
											<label style="font-size: 12" >��ͨ��</label>
										</td>
									</tr>
									<tr>
										<td style="height: 15px;">
											<input type="radio" id ="slllsh" name="slllsh" value="3" onclick="Check3()">
										</td>
										<td style="height: 15px;">
											<label style="font-size: 12" >�ݻ�</label>
										</td>
									</tr>
								</table>
		 					</td>
		 					<%-- <odin:textarea property="slllshqk"  title="�ɲ�����������������һ��ݡ�������" rows="3" colspan="1"/> --%>
		 				</tr>
		 				<tr>
		 					<td style="width:10%;" class="titleTd" colspan="1">ִ����ǰ�������</td>
		 					<odin:textarea property="sqbgqk"  title="ִ����ǰ�������" rows="5" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1">�����й�����������</td>
		 					<odin:textarea property="grsxchqk"  title="�����й�����������" rows="5" colspan="1"/>
		 				</tr>
		 				<tr style="display: none;" id="slllsh_div">
		 					<td style="width:10%;" class="titleTd" colspan="1">�ɲ�����������������һ��ݡ���������ע</td>
		 					<%-- <td colspan="2">
		 						<table style="border: 0px solid #ffffff;width: 100%;" ><tr style="border: 0px solid #ffffff;">
		 							<odin:textarea property="slllshqk"  title="�ɲ�����������������һ��ݡ�������" rows="3" colspan="1"/>
		 						</tr></table>
		 					</td> --%>
		 					<odin:textarea property="slllshqk"  title="�ɲ�����������������һ��ݡ�������" rows="3" colspan="1"/>
		 					<td style="width:10%;" class="titleTd" colspan="1"></td>
		 					<td colspan="1"></td>
		 					<odin:hidden property="slllshqklx" value="1"/>
		 				</tr>
		 			</table>
					
		 			<br>
					<table style="width: 100%">
						<tr>
						    <td width="14.28%" class="titleTd" colspan="1">���쵥λ</td>
							<odin:textEdit property="js1401" width="'100%'" title="���쵥λ" colspan="1" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">����ʱ��</td>
							<%-- <odin:dateEdit format="Ymd" property="js1402" width="'100%'"  title="����ʱ��" colspan="1" />	 --%>				
							<%-- <odin:textEdit property="js1402" width="'100%'" title="����ʱ��" colspan="1" maxlength="8"></odin:textEdit> --%>
							<odin:numberEdit property="js1402" width="'100%'" title="����ʱ��"  colspan="1" maxlength="8"></odin:numberEdit>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�������������鷶Χ��Ҫ��</td>
							<odin:textarea property="js1403"  title="�������������鷶Χ��Ҫ��" colspan="1" rows="5"/>				
						</tr>
						
						<tr>
							<td class="titleTd" colspan="1" >����̸������Χ</td>
							<odin:textarea property="js1404"  title="����̸������Χ" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >�������Ա</td>
							<odin:textarea property="js1405"  title="�������Ա" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1" >��ע</td>
							<odin:textarea property="js1406" title="��ע" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file14" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="11"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
					
				</div>
		    </odin:groupBoxNew>
		 	<%-- <odin:groupBox property="zzkcGB" title="��֯����"  >
		 		
	      	</odin:groupBox> --%>
	      	<odin:groupBoxNew contentEl="kctjreportdiv" collapsible="true" property="kctijianreport" title="�������������챨��">
	      		<div id="kctjreportdiv" class="marginbottom0px">
	      			<table style="width: 100%">
		 				<odin:tabLayOut />
		 				<tr>
		 					<td class="titleTd" rowspan="2" style="width:10%;" colspan="1">���������������</td>
		 					<td class="titleTd" colspan="1" style="width:10%;">��Ҫ����</td>
		 					<odin:textarea maxlength="500" property="js1417" rows="5" colspan="4"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" colspan="1" style="width:10%;">���ڲ���</td>
		 					<odin:textarea maxlength="500" property="js1418" rows="5" colspan="4"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="7" colspan="1">�����������</td>
		 					<td class="titleTd" style="width:10%;" rowspan="3" colspan="1">�������ۻ��ܽ��</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"��"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"�Ϻ�"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"һ��"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"��"ռ��(%)</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1419" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1420" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1422" maxlength="3"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1423" maxlength="3"></odin:numberEdit>	 					
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:20%;" colspan="1">������ռ��</td>
		 					<odin:numberEdit colspan="1" property="js1421" maxlength="3"></odin:numberEdit>
		 					<td class="titleTd" style="width:20%;" colspan="1">������ռ��</td>
		 					<odin:numberEdit colspan="1" property="js1424" maxlength="3"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="3" colspan="1">�������ۻ��ܽ��</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"������"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"ż������"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"һ���̶ȴ���"ռ��(%)</td>
		 					<td class="titleTd" colspan="1" style="width:20%;">"����"ռ��(%)</td>
		 				</tr>
		 				<tr>
		 					<odin:textarea property="js1425" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1426" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1428" colspan="1"></odin:textarea>
		 					<odin:textarea property="js1429" colspan="1"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:20%;" colspan="1">������ռ��</td>
		 					<odin:numberEdit colspan="1" property="js1427" maxlength="3"></odin:numberEdit>
		 					<td class="titleTd" style="width:20%;" colspan="1">������ռ��</td>
		 					<odin:numberEdit colspan="1" property="js1430" maxlength="3"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" colspan="1">�����Ƚϼ��е�����</td>
		 					<odin:textarea property="js1431" colspan="4" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" colspan="2">֧���������</td>
		 					<odin:select2 colspan="4" property="js1432" title="֧���������"  data="['1','����'],['2','�ǽ���'],['3','������']"></odin:select2>
		 				</tr>
		 			</table>
		 			<table style="width: 100%;">
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="5" colspan="1">����쵼�������</td>
		 					<td class="titleTd" style="width:10%;" rowspan="2" colspan="1">�������ۻ��ܽ��</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">��������</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"��"������</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"�Ϻ�"������</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"һ��"������</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"��"������</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1433" title="�����������" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1434" title="����ò�������" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1435" title="����Ϻò�������" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1436" title="����һ���������" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1437" title="������������" maxlength="5"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="2" colspan="1">�������ۻ��ܽ��</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">��������</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"������"����</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"ż������"����</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"һ���̶ȴ���"����</td>
		 					<td class="titleTd" style="width:16%;" colspan="1">"����"����</td>
		 				</tr>
		 				<tr>
		 					<odin:numberEdit colspan="1" property="js1438" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1439" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1440" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1441" maxlength="5"></odin:numberEdit>
		 					<odin:numberEdit colspan="1" property="js1442" maxlength="5"></odin:numberEdit>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">��Ӧ����Ҫ����</td>
		 					<odin:textarea property="js1443" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="2">���������</td>
		 					<odin:select2 colspan="5" property="js1444" title="���������"  data="['1','����'],['2','�ǽ���'],['3','������']"></odin:select2>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="5" colspan="1">��֯�����</td>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">�����й����������</td>
		 					<odin:textarea property="js1445" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">������ز���������</td>
		 					<odin:textarea property="js1446" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">��Ӧ����������</td>
		 					<odin:textarea property="js1447" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">����������α������</td>
		 					<odin:textarea property="js1448" colspan="5" rows="4" maxlength="500"></odin:textarea>
		 				</tr>
		 				<tr>
		 					<td class="titleTd" style="width:10%;" rowspan="1" colspan="1">������</td>
		 					<odin:select2 colspan="5" property="js1449" title="���������"  data="['1','����'],['2','�ǽ���'],['3','������']"></odin:select2>
		 				</tr>
		 			</table>	
	      		</div>
	      	</odin:groupBoxNew>
		  </div>
		  
		  <div id="tabs-5"  class="GBx-fieldset">
		  	<odin:groupBox property="ynGB" title="����"  >
	      		<div id="yunniang" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="20%"class="titleTd" colspan="1">��λ��ί�����飩���</td>
							<odin:textarea property="js0602" title="��λ��ί�����飩���" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�ֹ����쵼���</td>
							<odin:textarea property="js0603" title="�ֹ����쵼���" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">˫�ع��������</td>
							<odin:textarea property="js0604" title="˫�ع��������" colspan="1" rows="5"/>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�ͼ��첿�����</td>
							<odin:textarea property="js0605" title="�ͼ��첿�����" colspan="1" rows="5"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file06" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="2"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <%-- <div id="tabs-6"  class="GBx-fieldset">
		  	<odin:groupBox property="tljdGB" title="���۾���"  >
	      		<div id="taolunjueding" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td class="titleTd" colspan="1">��������о�ʱ��</td>
							<odin:dateEdit format="Ymd" property="js0702" title="��������о�ʱ��" colspan="2" />
							<td class="titleTd" colspan="2">��ί���ר����о����</td>
							<odin:textarea property="js0703" title="��ί���ר����о����" colspan="2" rows="5"/>
							<td class="titleTd" colspan="1">��ί��ί���о����</td>
							<odin:textarea property="js0704" title="��ί��ί���о����" colspan="2" rows="5"/>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">ȫί���������</td>
							<td width="9%" class="titleTd" colspan="1">�μ�����</td>
							<td width="9%" class="titleTd" colspan="1">ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��Ȩ</td>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">��ίȫί�������</td>
							<td width="9%" class="titleTd" colspan="1">�μ�����</td>
							<td width="9%" class="titleTd" colspan="1">ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��Ȩ</td>
							
						</tr>
						<tr>
							<odin:textEdit property="js0705" width="'100%'" title="�μ�����" colspan="1" />
							<odin:textEdit property="js0706" width="'100%'" title="ͬ��" colspan="1" />
							<odin:textEdit property="js0707" width="'100%'" title="��ͬ��" colspan="1" />
							<odin:textEdit property="js0708" width="'100%'" title="��Ȩ" colspan="1" />
							<odin:textEdit property="js0709" width="'100%'" title="�μ�����" colspan="1" />
							<odin:textEdit property="js0710" width="'100%'" title="ͬ��" colspan="1" />
							<odin:textEdit property="js0711" width="'100%'" title="��ͬ��" colspan="1" />
							<odin:textEdit property="js0712" width="'100%'" title="��Ȩ" colspan="1" />
						</tr>
						<tr>
							<tags:JUpload3 property="file07" label="�ϴ�����07" fileTypeDesc="�����ļ�"  colspan="10"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div> --%>
		  <div id="tabs-6"  class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="taolunjueding" property="tljdGB" title="���۾���" collapsible="true">
		  		<div id="taolunjueding" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="14%" class="titleTd" colspan="1" rowspan="2">��������۽��</td>
							<td width="12%" class="titleTd" colspan="1">ʱ��</td>
							<td width="5%" class="titleTd" colspan="1">�μ�����</td>
							<td width="9%" class="titleTd" colspan="1">ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">����</td>
							<td width="25%" class="titleTd" colspan="1">������</td>
							<td width="30%" class="titleTd" colspan="2">  ��ע      </td>
						</tr>
						<tr>
							<odin:dateEdit property="bhtljg" width="'100%'" colspan="1"></odin:dateEdit>
							<odin:numberEdit property="js1501" width="'100%'" title="�μ����������ᣩ" colspan="1" maxlength="5"></odin:numberEdit>
							<odin:textEdit property="js1502" width="'100%'" title="ͬ�⣨���ᣩ" colspan="1" />
							<odin:textEdit property="js1503" width="'100%'" title="��ͬ�⣨���ᣩ" colspan="1" />
							<odin:textEdit property="js1504" width="'100%'" title="���飨���ᣩ" colspan="1" />
							<odin:textarea property="bhbjqk"  title="������" colspan="1" rows="5"/>
							<odin:textarea property="js1509"  title="��ע�����ᣩ" colspan="1" rows="5"/>
						</tr>
						<tr id="cwhtitle">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">��ί�����۽��</td>
							<td width="12%" class="titleTd" colspan="1">ʱ��</td>
							<td width="5%" class="titleTd" colspan="1">�μ�����</td>
							<td width="9%" class="titleTd" colspan="1">ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">��ͬ��</td>
							<td width="9%" class="titleTd" colspan="1">����</td>
							<td width="25%" class="titleTd" colspan="1">������</td>
							<td width="30%" class="titleTd" colspan="2">  ��ע       </td>							
						</tr>
						<tr id="cwhtr">
							<odin:dateEdit property="cwtljg" width="'100%'" colspan="1"></odin:dateEdit>
							<odin:numberEdit property="js1505" width="'100%'" title="�μ���������ί�ᣩ" colspan="1" maxlength="5"></odin:numberEdit>
							<odin:textEdit property="js1506" width="'100%'" title="ͬ�⣨��ί�ᣩ" colspan="1" />
							<odin:textEdit property="js1507" width="'100%'" title="��ͬ�⣨��ί�ᣩ" colspan="1" />
							<odin:textEdit property="js1508" width="'100%'" title="���飨��ί�ᣩ" colspan="1" />
							<odin:textarea property="cwbjqk"  title="������" colspan="1" rows="5"/>
							<odin:textarea property="js1510"  title="��ע����ί�ᣩ" colspan="1" rows="5"/>
						</tr>
						
						<%-- <tr>
							<odin:textEdit property="js1501" width="'100%'" title="�μ����������ᣩ" colspan="1" />
							<odin:textEdit property="js1502" width="'100%'" title="ͬ�⣨���ᣩ" colspan="1" />
							<odin:textEdit property="js1503" width="'100%'" title="��ͬ�⣨���ᣩ" colspan="1" />
							<odin:textEdit property="js1504" width="'100%'" title="���飨���ᣩ" colspan="1" />
							<odin:textEdit property="js1505" width="'100%'" title="�μ���������ί�ᣩ" colspan="1" />
							<odin:textEdit property="js1506" width="'100%'" title="ͬ�⣨��ί�ᣩ" colspan="1" />
							<odin:textEdit property="js1507" width="'100%'" title="��ͬ�⣨��ί�ᣩ" colspan="1" />
							<odin:textEdit property="js1508" width="'100%'" title="���飨��ί�ᣩ" colspan="1" />
						</tr>
						<tr>
						    <td width="50%" class="titleTd" colspan="1">��ע</td>
						    <odin:textarea property="js1509"  title="��ע�����ᣩ" colspan="1" rows="5"/>
						    <odin:textEdit property="js1509" width="'100%'" title="��ע�����ᣩ" colspan="1" />
						    <td width="50%" class="titleTd" colspan="1" >��ע</td>
						    <odin:textarea property="js1510"  title="��ע����ί�ᣩ" colspan="1" rows="5"/>
						    <odin:textEdit property="js1510" width="'100%'" title="��ע����ί�ᣩ" colspan="1" />
						</tr>
						 --%>
						<tr>
							<tags:JUpload3 property="file07" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="10"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
		  
		  	<%-- <odin:groupBox property="tljdGB" title="���۾���"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-7"  class="GBx-fieldset GBxDis">
		  	<odin:groupBoxNew contentEl="renqiangongshi" property="rqgsGB"  title="��ǰ��ʾ" collapsible="true">
		  		<div id="renqiangongshi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="12%" class="titleTd" colspan="1">��ʾ��ʽ</td>
							<td width="12%" class="titleTd" colspan="1">��ʾʱ��</td>
							<td width="12%" class="titleTd" colspan="1">����ʱ��</td>
							<td width="20%" class="titleTd" colspan="1">��ʾ���</td>
							<td width="20%" class="titleTd" colspan="1">��ʾ���</td>
							<!-- <td class="titleTd" colspan="1">�÷�</td> -->
							<td width="12%" class="titleTd" colspan="1">��ʾ�ڼ����޾ٱ�</td>
							<td width="12%" class="titleTd" colspan="1">������</td>
						</tr>
						<tr>
							<odin:textarea property="gsxs"  title="��ʾ��ʽ" maxlength="200" colspan="1" rows="7" />
							<odin:dateEdit format="Ymd" property="js0802"  title="��ʾʱ��" colspan="1"  />
							<odin:dateEdit format="Ymd" property="js0809"  title="����ʱ��" colspan="1"  />
							<odin:textarea property="js0803"  title="��ʾ���" colspan="1" rows="7" />
							<odin:textarea property="js0804" title="��ʾ���" colspan="1" rows="7" />
							<%-- <odin:numberEdit property="js0805" maxlength="5" title="�÷�" colspan="1" /> --%>
							<odin:hidden property="js0805" title="�÷�"/>
							<odin:textarea property="gsjywjb" title="��ʾ�ڼ����޾ٱ�" colspan="1" rows="7" />
							<odin:textarea property="chqk" title="������" colspan="1" rows="7" />
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="3">����֪ʶ���Գɼ�</td>
							<odin:numberEdit readonly='true' property="js0810" title="����֪ʶ���Գɼ�" colspan="4"/>
						</tr>
						<tr>
							<td width="12%" class="titleTd" colspan="3">���µ��Ϳ��Գɼ�</td>
							<odin:numberEdit readonly='true' property="js0811" title="���µ��Ϳ��Գɼ�" colspan="4"/>
						</tr>
						<tr>
							<tags:JUpload3 property="file08" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="3"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
		  	</odin:groupBoxNew>
					  
		  	<%-- <odin:groupBox property="rqgsGB" title="��ǰ��ʾ"  >
	      		
	      	</odin:groupBox> --%>
		  </div>
		  <div id="tabs-8"  class="GBx-fieldset">
		  	<odin:groupBox property="rmblGB" title="�������" >
	      		<div id="renmianbanli" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="20%" class="titleTd" colspan="1">Ӧ������</td>
							<td width="20%" class="titleTd" colspan="1">ʵ������</td>
							<td width="20%" class="titleTd" colspan="1">�޳�Ʊ</td>
							<td width="20%" class="titleTd" colspan="1">����Ʊ</td>
							<td width="20%" class="titleTd" colspan="1">��ȨƱ</td>
						</tr>
						<tr>
							<odin:textEdit property="js0902" width="'100%'" title="Ӧ������" colspan="1"  />
							<odin:textEdit property="js0903" width="'100%'" title="ʵ������" colspan="1"  />
							<odin:textEdit property="js0904" width="'100%'" title="�޳�Ʊ" colspan="1"  />
							<odin:textEdit property="js0905" width="'100%'" title="����Ʊ" colspan="1"  />
							<odin:textEdit property="js0906" width="'100%'" title="��ȨƱ" colspan="1"  />
						</tr>
						<tr>
							<tags:JUpload3 property="file09" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="5"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <div id="tabs-9"  class="GBx-fieldset">
		  	<odin:groupBox property="syqGB" title="������"  >
	      		<div id="shiyongqi" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="10%" class="titleTd" colspan="1">������</td>
							<odin:textEdit property="js1002" width="'100%'" title="������" colspan="1"  />
							<td width="5%" class="titleTd" colspan="1">ת�����</td>
							<odin:textarea property="js1003" title="ת�����" colspan="1" rows="5" />
						</tr>
						<tr>
							<td class="titleTd" colspan="1">�������</td>
							<odin:textarea property="js1004" title="�������" colspan="3" rows="5" />
						</tr>
						<tr>
							<tags:JUpload3 property="file10" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="4"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  <div id="tabs-10"  class="GBx-fieldset">
		  	<odin:groupBox property="qtqkGB" title="�������" >
	      		<div id="qitaqingkuang" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td align="left" colspan="2">
								1���Ƿ�ѡ�ɲ���
								<input type="radio"  name="js1102" style="width: auto !important;" id="dqwryjh" value="1" checked="checked" /><label for="dqwryjh">��</label>
								<input type="radio" name="js1102" style="width: auto !important;" id="dfdsfsd" value="0" /><label for="dfdsfsd">��</label>
								��&nbsp; 
								2���Ǻβ�κ󱸸ɲ���
								<input type="radio" name="js1103" style="width: auto !important;" id="jgkyhtf" value="1"  /><label for="jgkyhtf">ʡ��</label>
								<input type="radio" name="js1103" style="width: auto !important;" id="sdgdhytgd" value="0" checked="checked"/><label for="sdgdhytgd">�й�</label>
								��&nbsp;<br/>
								3���з���ǰ��ѵ��
								<input type="radio" name="js1104" style="width: auto !important;" id="jgkyewhtf" value="1"  checked="checked"/><label for="jgkyewhtf">��</label>
								<input type="radio" name="js1104" style="width: auto !important;" id="sdgdhrytgd" value="0" /><label for="sdgdhrytgd">��</label>
								��&nbsp;
								4���з��л��㹤��������
								<input type="radio" name="js1105" style="width: auto !important;" id="jgkyewhtf1" value="1"  checked="checked"/><label for="jgkyewhtf1">��</label>
								<input type="radio" name="js1105" style="width: auto !important;" id="sdgdhrytgd1" value="0" /><label for="sdgdhrytgd1">��</label>
								��
							</td>
						</tr>
						<tr>
							<td width="10%" class="titleTd" colspan="1">������Ҫ����˵�������</td>
							<odin:textarea property="js1106" title="�������" colspan="1" rows="5" />
						</tr>
						<tr>
							<tags:JUpload3 property="file11" label="�ϴ�����" fileTypeDesc="�����ļ�"  colspan="2"
							uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
						</tr>
					</table>
				</div>
	      	</odin:groupBox>
		  </div>
		  
		  <div id="tabs-16" style="float: left;">
			 
			<odin:groupBoxNew contentEl="test" collapsible="true" width="760"  property="mygroupBox1" 
			     title="����">
			     <div id="test" style="overflow-y: scroll;">   
				<table border="0" id="myform2" align="center" width="750" cellpadding="0" cellspacing="0">
					<odin:tabLayOut />
					<tr id="msgTr_1">
						<odin:textarea property="fwh1" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20'  />
						<odin:textarea property="fwsj1" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw1" ondblclick="nrzw('nrzw1')" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw1js2200"/>
						<odin:textarea property="nmzw1" ondblclick="nmzw('nmzw1')" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw1a"/>
						<odin:hidden property="nrzw1b"/>
						<odin:hidden property="nmzw1a"/>
						<odin:hidden property="nmzw1b"/>
					</tr>
					<tr id="msgTr_2">
						<odin:textarea property="fwh2" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj2" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw2" ondblclick="nrzw('nrzw2')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw2js2200"/>
						<odin:textarea property="nmzw2" ondblclick="nmzw('nmzw2')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw2a"/>
						<odin:hidden property="nrzw2b"/>
						<odin:hidden property="nmzw2a"/>
						<odin:hidden property="nmzw2b"/>
					</tr>
					<tr id="msgTr_3">
						<odin:textarea property="fwh3" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj3" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw3" ondblclick="nrzw('nrzw3')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw3js2200"/>
						<odin:textarea property="nmzw3" ondblclick="nmzw('nmzw3')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw3a"/>
						<odin:hidden property="nrzw3b"/>
						<odin:hidden property="nmzw3a"/>
						<odin:hidden property="nmzw3b"/>
					</tr>
					<tr id="msgTr_4">
						<odin:textarea property="fwh4" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj4" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw4" ondblclick="nrzw('nrzw4')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw4js2200"/>
						<odin:textarea property="nmzw4" ondblclick="nmzw('nmzw4')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
					
						<odin:hidden property="nrzw4a"/>
						<odin:hidden property="nrzw4b"/>
						<odin:hidden property="nmzw4a"/>
						<odin:hidden property="nmzw4b"/>
					</tr>
					<tr id="msgTr_5">
						<odin:textarea property="fwh5" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj5" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw5" ondblclick="nrzw('nrzw5')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw5js2200"/>
						<odin:textarea property="nmzw5" ondblclick="nmzw('nmzw5')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
					
						<odin:hidden property="nrzw5a"/>
						<odin:hidden property="nrzw5b"/>
						<odin:hidden property="nmzw5a"/>
						<odin:hidden property="nmzw5b"/>
					</tr>
					<tr id="msgTr_6">
						<odin:textarea property="fwh6" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj6" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw6" ondblclick="nrzw('nrzw6')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw6js2200"/>
						<odin:textarea property="nmzw6" ondblclick="nmzw('nmzw6')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						
						<odin:hidden property="nrzw6a"/>
						<odin:hidden property="nrzw6b"/>
						<odin:hidden property="nmzw6a"/>
						<odin:hidden property="nmzw6b"/>
					</tr>
					<tr id="msgTr_7">
						<odin:textarea property="fwh7" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj7" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw7" ondblclick="nrzw('nrzw7')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw7js2200"/>
						<odin:textarea property="nmzw7" ondblclick="nmzw('nmzw7')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw7a"/>
						<odin:hidden property="nrzw7b"/>
						<odin:hidden property="nmzw7a"/>
						<odin:hidden property="nmzw7b"/>
					</tr>
					<tr id="msgTr_8">
						<odin:textarea property="fwh8" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj8" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw8" ondblclick="nrzw('nrzw8')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw8js2200"/>
						<odin:textarea property="nmzw8" ondblclick="nmzw('nmzw8')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw8a"/>
						<odin:hidden property="nrzw8b"/>
						<odin:hidden property="nmzw8a"/>
						<odin:hidden property="nmzw8b"/>
					</tr>
					<tr id="msgTr_9">
						<odin:textarea property="fwh9" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj9" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw9" ondblclick="nrzw('nrzw9')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw9js2200"/>
						<odin:textarea property="nmzw9" ondblclick="nmzw('nmzw9')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw9a"/>
						<odin:hidden property="nrzw9b"/>
						<odin:hidden property="nmzw9a"/>
						<odin:hidden property="nmzw9b"/>
					</tr>
					<tr id="msgTr_10">
						<odin:textarea property="fwh10" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj10" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw10" ondblclick="nrzw('nrzw10')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw10js2200"/>
						<odin:textarea property="nmzw10" ondblclick="nmzw('nmzw10')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw10a"/>
						<odin:hidden property="nrzw10b"/>
						<odin:hidden property="nmzw10a"/>
						<odin:hidden property="nmzw10b"/>
					</tr>
					<tr id="msgTr_11">
						<odin:textarea property="fwh11" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj11" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw11" ondblclick="nrzw('nrzw11')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw11js2200"/>
						<odin:textarea property="nmzw11" ondblclick="nmzw('nmzw11')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						
						<odin:hidden property="nrzw11a"/>
						<odin:hidden property="nrzw11b"/>
						<odin:hidden property="nmzw11a"/>
						<odin:hidden property="nmzw11b"/>
					</tr>
					<tr id="msgTr_12">
						<odin:textarea property="fwh12" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj12" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw12" ondblclick="nrzw('nrzw12')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw12js2200"/>
						<odin:textarea property="nmzw12" ondblclick="nmzw('nmzw12')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw12a"/>
						<odin:hidden property="nrzw12b"/>
						<odin:hidden property="nmzw12a"/>
						<odin:hidden property="nmzw12b"/>
					</tr>
					<tr id="msgTr_13">
						<odin:textarea property="fwh13" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj13" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw13" ondblclick="nrzw('nrzw13')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw13js2200"/>
						<odin:textarea property="nmzw13" ondblclick="nmzw('nmzw13')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw13a"/>
						<odin:hidden property="nrzw13b"/>
						<odin:hidden property="nmzw13a"/>
						<odin:hidden property="nmzw13b"/>
					</tr>
					<tr id="msgTr_14">
						<odin:textarea property="fwh14" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj14" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw14" ondblclick="nrzw('nrzw14')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw14js2200"/>
						<odin:textarea property="nmzw14" ondblclick="nmzw('nmzw14')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw14a"/>
						<odin:hidden property="nrzw14b"/>
						<odin:hidden property="nmzw14a"/>
						<odin:hidden property="nmzw14b"/>
					</tr>
					<tr id="msgTr_15">
						<odin:textarea property="fwh15" maxlength="100" label="���ĺ�" title="���ĺ�" rows="3" cols='20' />
						<odin:textarea property="fwsj15" maxlength="8" label="����ʱ��" title="����ʱ��" rows="3" cols='20'/>
						<odin:textarea property="nrzw15" ondblclick="nrzw('nrzw15')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20'  readonly='true' />
						<odin:hidden property="nrzw15js2200"/>
						<odin:textarea property="nmzw15" ondblclick="nmzw('nmzw15')" maxlength="50" label="����ְ��" title="����ְ��" rows="3" cols='20' readonly='true'/>
						<odin:hidden property="nrzw15a"/>
						<odin:hidden property="nrzw15b"/>
						<odin:hidden property="nmzw15a"/>
						<odin:hidden property="nmzw15b"/>
					</tr>
					
											
					<tr>
					    <td align='left'>
							<odin:button property="addMsg" text="���ӷ���" handler="addMessage"></odin:button>
					    </td>
					    <td align='left'>
							<odin:button property="cutMsg" text="���ٷ���" handler="cutMessage"></odin:button>
					    </td>
					    <td align='left'>
							<odin:button property="clearnrzw" text="�������ְ��" handler="clearnrzw"></odin:button>
					    </td>
					    <td align='right'>
							<odin:button property="updateA02" text="������ְ��Ϣ" handler="updateA02handler"></odin:button>
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
									<div class="col-name">������</div>
									<div class="col-50">�Ա�:��</div>
									<div class="col-50">����:62��</div>
									<div class="col-100">ְλ:��ί�������о������¿Ƹ��Ƴ�</div>
								</td>
							</tr>
						</table>
					</li>
					<li><div class="step-body step-green-head"><div class="step-body-head">����</div></div></li>
					<li><div class="step-body step-red"><div class="step-body-head">ְ��Ԥ��</div></div></li>
					<li><div class="step-body step-green"><div class="step-body-head">���Ԥ��</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">�������ȡ���</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">��֯����</div></div></li>
					<li><div class="step-body step-black"><div class="step-body-head">���۾���</div></div></li>
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
									<div class="col-name">������</div>
									<div class="col-50">�Ա�:��</div>
									<div class="col-50">����:62��</div>
									<div class="col-100">ְλ:��ί�������о������¿Ƹ��Ƴ�</div>
								</td>
							</tr>
						</table>
					</li>
					<li><div class="step-body step-green-head"><div class="step-body-head">����</div></div></li>
					<li><div class="step-body step-red"><div class="step-body-head">ְ��Ԥ��</div></div></li>
					<li><div class="step-body step-green"><div class="step-body-head">���Ԥ��</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">�������ȡ���</div></div></li>
					<li><div class="step-body step-blue"><div class="step-body-head">��֯����</div></div></li>
					<li><div class="step-body step-black"><div class="step-body-head">���۾���</div></div></li>
				</ul>
			</div>
		</td>
	</tr> -->
</table>
</div>	  

<%-- <odin:tabCont itemIndex="extTab1_1">
<table style="width: 100%">
	<tr>
		<odin:textEdit property="zhiwu" label="ְ��"></odin:textEdit>
		<odin:textEdit property="bumen" label="����" ></odin:textEdit>
	</tr>
	<tr>
		<odin:NewDateEditTag property="renzhishijian" label="��ְʱ��"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="mianzhishijain" label="��ְʱ��"></odin:NewDateEditTag>
	</tr>
	<tr>
		<odin:textEdit property="pizhunwenhao" label="��׼�ĺ�" ></odin:textEdit>
	</tr>
</table>
</odin:tabCont>
<odin:tabCont itemIndex="extTab1_2" >

</odin:tabCont> --%>
<odin:hidden property="tljlbxzlx" value="4_1" />

<odin:hidden property="js0100s" title="��Աѡ�񼯺�"/>
<!-- ������ʱʱ���� -->
<odin:hidden property="nrmid" title="�ֶ�"/>
<odin:hidden property="nrmdesc" title="�ֶ�����"/>
<odin:hidden property="nrmvalue" title="ֵ"/>

<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>
<%-- <odin:hidden property="js0100s" title="��Աѡ�񼯺�"/> --%>
<script type="text/javascript">
var ctxPath='<%=ctxPath%>';

function fcclick(id){
	//alert(id);
	var rbId=document.getElementById('rbId').value;
	//$h.openPageModeWin('QgysfcWin', 'pages.xbrm.Qgysfc','������Ϣ', 700, 500, {rb_id: rbId, id: id} , ctxPath);
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

//���İ�ť
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

//���Ӽ��ٷ���
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
		odin.alert("�빴ѡ��Ա");
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
		odin.alert("�빴ѡ��Ա");
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
	$h.openPageModeWin('infotodoWin','pages.comm.InfoToDo','���벿��',310,110, p , ctxPath);
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
			wb = '��ͨ��';
		} else {
			wb = '�ݻ�';
		}
		/* Ext.MessageBox.confirm('ϵͳ��ʾ','���ˡ���������һ��ݡ����Ϊ '+wb+',���潫����Ա�б����Ƴ�����Ա��ȷ�ϱ�����',function(id){
			if("yes"==id){
				radow.doEvent("save");
			}else{
				return;
			}	
		}) */
		$h.confirm('ϵͳ��ʾ','���ˡ���������һ��ݡ����Ϊ '+wb+',���潫����Ա�б����Ƴ�����Ա��ȷ�ϱ�����', 300, function(id){
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
		Ext.Msg.alert('ϵͳ��ʾ','����ѡ����Ա!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	$h.openPageModeWin('RankAddPageNiRenQcjs','pages.xbrm.RankAddPageNiRenQcjs','����ְ��',450,230,a0000+','+id+','+js0100,ctxPath);
}


function nrzw(id){
	var a0000=document.getElementById('a0000').value;
	if(''==a0000){
		Ext.Msg.alert('ϵͳ��ʾ','����ѡ����Ա!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	$h.openPageModeWin('RankAddPageNiRenQcjs','pages.xbrm.NiRenQcjs','����ְ��',450,230,a0000+','+id+','+js0100,ctxPath);
}

function nmzw(id){
	var a0000=document.getElementById('a0000').value;
	var rbId=document.getElementById('rbId').value;
	if(''==a0000){
		Ext.Msg.alert('ϵͳ��ʾ','����ѡ����Ա!');
		return;
	}
	var js0100=document.getElementById('js0100').value;
	//$h.openPageModeWin('WorkUnitsNiRenAddPageQcjs','pages.xbrm.WorkUnitsNiRenAddPageQcjs','����ְ��',420,300,a0000+','+id+','+js0100,ctxPath);
	$h.openPageModeWin('WorkUnitsNiRenAddPageQcjs','pages.xbrm.WorkUnitsNiRenAddPageQcjs','����ְ��',420,300,a0000+','+id+','+rbId+','+js0100,ctxPath);
}


function changeSelect(id,num){
	var value=$('#select'+id+' option:selected').val();
	$('#'+id).val(value);
}

//���Ԥ��ע��˫��
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
	//��ʱ���Ч��
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
	//ҳ�����ʱ�Ƴ����水ť����Ч��
	Ext.getCmp('save').removeClass('bh');
	//Ext.getCmp('qcjs').removeClass('bh');
	
	$('#tablehide').hide();
	
	//��ʾְ��Ԥ������
	var block = parseInt(document.getElementById('block').value);
	for(var i = block+1; i <=20; i++){ //tr_len��Ҫ���Ƶ�tr����  
	     $("#tr_"+i).hide();  
	} 
	//��ʾ�����Ƽ�����
	var js33row=parseInt(document.getElementById('js33row').value);
	for(var i = js33row+1; i <=20; i++){ //tr_len��Ҫ���Ƶ�tr����  
	     $("#js33tr_"+i).hide();  
	} 
	
	//var combo = getSelect();
	Ext.getCmp('gridcq').getBottomToolbar().insertButton(0,[
		//new Ext.menu.Separator({cls:'xtb-sep'}),
		new Ext.Spacer({width:1,height:35}), 
		/* combo */
		{boxLabel: 'ȫ��',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4', listeners:{check:radiochecked}},
		{boxLabel: '�����', name: 'rb-col',xtype: 'radio', inputValue: '4_1',checked: true ,listeners:{check:radiochecked}},
        {boxLabel: '��ǻ�',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4_2',listeners:{check:radiochecked}},
        {boxLabel: '��ί��', name: 'rb-col',xtype: 'radio', inputValue: '4_3',listeners:{check:radiochecked}}
	]);
	
	new Ext.Toolbar({
		renderTo:Ext.getCmp('gridcq').bbar,
		items:[
			new Ext.Spacer({width:1,height:35}), 
			{boxLabel: '�������', name: 'rb-col2',xtype: 'radio', inputValue: '1', checked: true ,listeners:{check:radiochecked2}},
			{boxLabel: '̸���������', name: 'rb-col2',xtype: 'radio', inputValue: '2',listeners:{check:radiochecked2}}
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
	var gp1 = Ext.get("gp1");//������Ϣ
	$( "#pdata" ).css('width',gp1.getWidth()-200);
	//gp1.setHeight(240);
	var g_hight = gp1.getHeight()//������Ϣ+������ĸ�
	//ext��ǩ�߿�  �Ǽ����
	/* var extTab1 = Ext.getCmp("extTab1");
	extTab1.setWidth(gp1.getWidth());
	extTab1.setHeight(peopleInfoGrid.getHeight()-gp1.getHeight()-10); */
	//alert(peopleInfoGrid.getHeight())
		
	var isshownrm=document.getElementById('isshownrm').value;
	if('5'==isshownrm){
		//�������
		var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
		//jbqkGB.css('width',gp1.getWidth()-25);
		jbqkGB.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		var offest = 0;
		 //����
		 var dy=$("#dyGB .x-fieldset-bwrap");
		 dy.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//adjustGB($("#dyGB .x-fieldset-bwrap"),jbqkGB,offest);
		//ְ��Ԥ��
		//adjustGB2($("#zsysGB .x-fieldset-bwrap"),jbqkGB,offest);
		//��������ȡ���
		//adjustGB($("#khyjGB .x-fieldset-bwrap"),jbqkGB,offest);
		var khytqyj=$("#khyjGB .x-fieldset-bwrap");
		khytqyj.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//���Ԥ��
		//var qgysqk = $("#tabs-12");
		 //alert(qgysqk);
		//qgysqk.css('width',jbqkGB.width()-10-20);
		//qgysqk.css('height',10);
		//qgysqk.css('height',peopleInfoGrid.getHeight()-39);
		/*adjustGB2($("#qgysGB .x-fieldset-bwrap"),jbqkGB,offest); */
		//�����Ƽ�
		//adjustGB($("#mztjGB .x-fieldset-bwrap"),jbqkGB,offest);
		var mztj=$("#mztjGB .x-fieldset-bwrap");
		mztj.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//������Աȷ��
		//adjustGB($("#kcrxqdGB .x-fieldset-bwrap"),jbqkGB,offest);
		//��֯����
		//adjustGB($("#zzkcGB .x-fieldset-bwrap"),jbqkGB,offest);
		var zzkc=$("#zzkcGB .x-fieldset-bwrap");
		zzkc.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//����
		//adjustGB($("#ynGB .x-fieldset-bwrap"),jbqkGB,offest);
		//���۾���
		//adjustGB($("#tljdGB .x-fieldset-bwrap"),jbqkGB,offest);
		var tljd=$("#tljdGB .x-fieldset-bwrap");
		tljd.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//��ǰ��ʾ
		//adjustGB($("#rqgsGB .x-fieldset-bwrap"),jbqkGB,offest);
		var rqgs=$("#rqgsGB .x-fieldset-bwrap");
		rqgs.css('height',peopleInfoGrid.getHeight()-g_hight-39-40);
		//�������
		//adjustGB($("#rmblGB .x-fieldset-bwrap"),jbqkGB,offest);
		//������
		//adjustGB($("#syqGB .x-fieldset-bwrap"),jbqkGB,offest);
		//�������
		//adjustGB($("#qtqkGB .x-fieldset-bwrap"),jbqkGB,offest); 
		//ext��ǩ������ʽ����������ҳ��֧�����¹���
		/* var tabContentHeight = jbqkGB.getHeight()-30;
		$( "#extTab1_1_CEL" ).css('height',tabContentHeight).css('overflow-y','auto');
		$( "#extTab1_2_CEL" ).css('height',tabContentHeight).css('overflow-y','auto'); */
	}else if('6'==isshownrm){
		//�������
		var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
		jbqkGB.css('height',viewSize.height);
		var offest = 0;
		 //����
		 var dy=$("#dyGB .x-fieldset-bwrap");
		 dy.css('height',viewSize.height);
		//��������ȡ���
		var khytqyj=$("#khyjGB .x-fieldset-bwrap");
		khytqyj.css('height',500);
		//�����Ƽ�
		var mztj=$("#mztjGB .x-fieldset-bwrap");
		mztj.css('height',viewSize.height);
		//��֯����
		var zzkc=$("#zzkcGB .x-fieldset-bwrap");
		zzkc.css('height',viewSize.height);
		//���۾���
		var tljd=$("#tljdGB .x-fieldset-bwrap");
		tljd.css('height',viewSize.height);
		//��ǰ��ʾ
		var rqgs=$("#rqgsGB .x-fieldset-bwrap");
		rqgs.css('height',viewSize.height);
	}
	
	peopleInfoGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		//if(rc.data.js0100!=$('#js0100').val()){//�����б���ѡ���У�ͬһ�е������򲻴������²�ѯ��ϸ��Ϣ
			$('#js0100').val(rc.data.js0100);
			$('#a0101').val(rc.data.a0101);
			
			document.getElementById('a0000').value=rc.data.a0000;
			radow.doEvent('peopleInfo',rc.data.a0000);
		//}
		
		//alert(document.getElementById('a0000').value);
	});
		
	$('#js0103').parent().parent().parent().attr('width','20%');
	$('#js0102').parent().parent().parent().attr('width','20%');
	$('#js0502').parent().parent().parent().parent().attr('rowspan',2);//����ʱ��
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
	$h.initGridSort('gridcq',function(g){//һ��remove һ��insert һ��sort һ���ᴥ������renderer
		
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
	
	/*��ie�н����������(��ֹֻ��һ����ʾ����Ҫ���ie�������⣬ie8�е�����Ϊ100%ʱ���ı��������ɻ���ʱ*/
	$('#js0108,#js0111,#js0117').each(function(){$(this).css('width',$(this).innerWidth())})
	Ext.getCmp('js0108').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	/* Ext.getCmp('js0109').on('change',function(t,v){saveNRMValue(t,v,t.id);}); */
	Ext.getCmp('js0117').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	Ext.getCmp('js0111').on('change',function(t,v){saveNRMValue(t,v,t.id);});
	//Ext.getCmp('js0115_combo').on('select',function(t,record,index){saveNRMValue(index,record.data.key,'js0115');});
	//Ext.getCmp('js0116_combo').on('select',function(t,record,index){saveNRMValue(index,record.data.key,'js0116');});
});

function hideTsgbjd(str){
	//��ť��ʾ����
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
	//ajax��ѯ���ݿ⣬������ݿ����о�չʾ���ݿ�����Ϣ��û�оͶ�չʾ0
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
			data.push("��");
		}else{
			data.push("��");
		}
	});
	$("input[name='grsxch']").each(function(){
		var ischeck=$(this).is(":checked");
		//console.log(ischeck);
		if(ischeck==false){
			data.push("��");
		}else{
			data.push("��");
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
	for(var i = 1; i <=block; i++){ //��ʾtr����
	     $("#tr_"+i).show();
	}
}

function showArow(){
	var block = parseInt(document.getElementById('block').value);
	for(var t = block+1; t <=30; t++){ //tr_len��Ҫ���Ƶ�tr����  
	     $("#tr_"+t).hide();  
	}
	for(var i = 1; i <=block; i++){ //��ʾtr����
	     $("#tr_"+i).show();  
	} 
}

function getSelect(){
	//��������Դ[��������Դ]
    var combostore = new Ext.data.SimpleStore({
        fields: ['id', 'name'],
        data: [["4", 'ȫ��'], ["4_1", '����'], ["4_2", '���ר���'], ["4_3", '��ί��ɲ����佨�鷽��']]
    });
    //����Combobox
    var combobox = new Ext.form.ComboBox({
        store: combostore,
        width: 150,
        displayField: 'name',
        valueField: 'id',
        triggerAction: 'all',
        emptyText: '��ѡ��...',
        allowBlank: true,
        blankText: '��ѡ����Ա���',
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
    //Combobox��ȡֵ
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
		
		//Ĭ�϶���ѡ
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
    		fnSet[$o]();//��ʼ���ļ����
	    	delete fnSet[$o];
	    	
    	}
    	if(!!fnSet[$o+"_info"]){
    		fnSet[$o+"_info"]();//��ʼ���ļ����
	    	delete fnSet[$o+"_info"];
    	}
		    	
    	var $fn = $(obj).attr('fn');
    	document.getElementById('tabobj').value=$fn;
    	
    	//�����⣬һ�����ض����أ�һ����ʾ����ʾ
		var isshownrm=document.getElementById('isshownrm').value;
		var isshow=$('#personInfo').is(":visible");
		if('1'==isshownrm){
			//$('#personInfo').show();
			//Ext.getCmp('personInfo').show();
			$( "#personInfo" ).css("display","block");
			if($fn=='file12'||$fn=='file13'){
	    		//ְ��Ԥ��������Ա������Ϣ
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
    		//ְ��Ԥ��������Ա������Ϣ
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
    	
    	//��ʾ���ذ�ť
    	if($fn=='file99'){
    		radow.doEvent("tabclickshowts");
    	}
		    	
    	//��ť��ʾ����
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
    	
    	//���� ��ȡ��ǰ���ڵ���Ա
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
		Ext.Msg.alert('ϵͳ��ʾ:','�빴ѡһ����Ա���ٵ���!');
	}
	var rb_no = parent.document.getElementById("rb_no").value;
	document.getElementById("rb_no").value = rb_no;
	/* if(js0100==null || js0100.length==0){
		Ext.Msg.alert('ϵͳ��ʾ:',"��ѡ����Ա!");
	} */
	//file02(����)  ��������ȡ���(file99)   �����Ƽ�(file19)   ��֯����(file14)  ���۾���file07   ��ǰ��ʾfile08
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
 					Ext.Msg.buttonText.ok = '����';
 					Ext.Msg.buttonText.cancel='ȡ��';
 					$h.confirm("ϵͳ��ʾ��",resultjson.msg+"δ�ϴ����ϣ��Ƿ������",220,function(id) { 
 						if("ok"==id){
 							ShowCellCover("start","��ܰ��ʾ��","��������ȫ�̼�ʵ��");
 							radow.doEvent('qcjs');
 							Ext.Msg.hide();
 						}if("cancel"==id){
 							return false;
 						}else{
 							return false;
 						}
 					});
 				  }else{
 					 ShowCellCover("start","��ܰ��ʾ��","��������ȫ�̼�ʵ��");
 					 radow.doEvent('qcjs');
 					 Ext.Msg.hide();
 				  }
 				}
      	   }
        }
   });
}



function saveNRMValue(t,v,id){//������ʵʱ���档
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
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
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
			//ѡ���һ��
			//peopleInfoGrid.getSelectionModel().selectRow(0,true);
			//peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0);
		}
		
	}else{
		/* if(parseInt($('#cur_hj').val())>1)
			peopleInfoGrid.getGridEl().select('.x-grid3-body',true).elements[0]
			.dom.innerHTML='<a class="aclass" href="javascript:void(0) onclick="radow.doEvent(\'addprevHJ\');">��������ϸ����ڵĸɲ���Ϣ</a>';
		 */
	}
	saveStore(peopleInfoGrid.store);
	yjrenderer.initTip();
	
	//����tabҳ��
	radow.doEvent("setShowImg");
	
	//������Ա�Ƿ������д��
	//��ȡ��ǰ����tabҳ
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

//�洢����store
function saveStore(store,record){
	
	var r = store.reader;
	var data = r.jsonData.data;
	
	if(typeof record!='undefined'){
		//���ĺ�������еĻ���
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
//������������ʱ��ն������ݻ��棬 
function clearGridJsonStore(cur_hj){
	delete gridJsonStore[cur_hj];
}
//
//�������ݻ���
function updateGridJsonStore(record){
	var data;
	//̸������ֻ���µ�ǰ�Լ������
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
//����reader����
function updateGridReadStore(store,cur_hj){
	var data;
	data = gridJsonStore[cur_hj]["data"];
	for(var i=0;i<data.length;i++){
		data[i]=store.getAt(i).data;
	}
	
}
//�������ݻ���
function deleteGridJsonStoreRecord(js0100){
	var data;
	for(var cur_hj in gridJsonStore){
		data = gridJsonStore[cur_hj]["data"];
		
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data.splice(i,1);//ɾ����������
				break;
			}
		}
	}
}
//����ְ��Ԥ������
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

//�������ݣ������л��棬����ػ��棬���������̨
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
//����groupbox�����
function adjustGB(oriObj,refObj,offset){
	oriObj.css('width',refObj.width());
	oriObj.css('height',refObj.height()-offset);
}

function adjustGB2(oriObj,refObj,offset){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	oriObj.css('width',refObj.width());
	oriObj.css('height',peopleInfoGrid.getHeight()-39);
}
//����������Ӧtd��
function adjustSelectWidth(id){
	Ext.getCmp(id+'_combo').setWidth($('#tdid_'+id).width()+3);
}

var flength = 0;curfindex=0;
function onUploadSuccess(file, jsondata, response){
	curfindex++;
	updateProgress(curfindex,flength,jsondata.file_name);
	if(curfindex==flength){
		Ext.Msg.hide();
		Ext.example.msg('','����ɹ�!');
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
		if(!fnSet["file"+hz]){//�Ƿ��Ѿ���ʼ��
			flength = flength + eval("$('#file"+hz+"').data('uploadify').queueData.queueLength;");
		}
	}
	
	if(flength>0){
		$h.progress('��ȴ�', '�����ϴ��ļ�...',null,300);
	}else{
		Ext.Msg.hide();
		Ext.example.msg('','����ɹ�!');
	}
}
function updateProgress(cur,total,fname){
	if (fname.length > 15) {
		fname = fname.substr(0,15) + '...';
	}
	Ext.MessageBox.updateProgress(cur / total, '�����ϴ��ļ�:'+fname+'����ʣ'+(total-cur)+'��');
}
//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
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
	Ext.MessageBox.buttonText.ok = "�ر�";
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
	ShowCellCover("start","��ܰ��ʾ��","�������������...");
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
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
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
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
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
	
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function cc(){
	
}

/*��� ��*/
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
	
	ShowCellCover('start','ϵͳ��ʾ','��������ɲ����佨�鷽�� ,�����Ե�...');
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
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
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
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

//��ʾ��ϸ
function showLink(value, params, record, rowIndex, colIndex, ds) {
	
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0119 = record.get('js0119');
	var js0100 = record.get('js0100');
	
	var src = "";
	var alt = "";
	if("1"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-off.png";
		alt = "���Ԥ��";
	} else if("2"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-y.png";
		alt = "�Ƶ�Ԥ��";
	} else if("3"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-g.png";
		alt = "�̵�Ԥ��";
	} else if("4"==js0119){
		<%-- src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "����"; --%>
			
		src = "";
		alt = "";
	} 
																
	if(""==src){
		return "";
	}
	
	
	
	//Ҫ��ʾ����ͼ��ſ����
	return "<img onclick='showDetail(\""+js0100+"\",\"-1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
	
	//ֻ��ʾ�鿴��ϸ
	//return "<a href='javascript:showDetail(\""+js0100+"\")'>�鿴��ϸ</a>";
}

//��ʾ��ϸ
function showLinkFine(value, params, record, rowIndex, colIndex, ds) {
	
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0100 = record.get('js0100');
	
	var src = "";
	var alt = "";
	if("1"==value){
		src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "����";
	} else {
		src += "";
		alt = "";
	} 
	
	if(""==src){
		return "";
	}
	
	//Ҫ��ʾ����ͼ��ſ����
	return "<img onclick='showDetail(\""+js0100+"\",\"1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
}

function showDetail(js0100,type){
	$h.openWin('yjmx','pages.xbrm.YJMX','Ԥ����ϸ ',820,520,null,contextPath,window,{js0100:js0100,type:type});
}

//ģ��ɲ�����
function imitate(){
	var grid = Ext.getCmp('gridcq');
	
	var total = grid.getStore().getCount();//��������
	var record; //������
	var a0000s = ""; //���θɲ�����
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		
		if(true==record.get('pcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}
	
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		$h.openWin('mnrm','pages.xbrm.MNRM','ģ������ ',820,520,null,contextPath,window,{a0000s:a0000s});
	}
}
//һ�������ɲ�����
function filedown(){
var grid = Ext.getCmp('gridcq');
	var total = grid.getStore().getCount();//��������
	var record; //������
	var js0100s = ""; //���θɲ�����
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('pcheck')){
			js0100s += record.get('js01001')+"@#@";
		}
	}
	if(""==js0100s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		$h.openWin('mnrm','pages.xbrm.AllFileDown','��������ѡ�� ',320,220,null,contextPath,window,{js0100s : js0100s});
	}
}

//һ�������ɲ�����
/* function filedown(){
var grid = Ext.getCmp('gridcq');
	
	var total = grid.getStore().getCount();//��������
	var record; //������
	var a0000s = ""; //���θɲ�����
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		
		if(true==record.get('pcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}
	
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		$h.openWin('mnrm','pages.xbrm.AllFileDown','��������ѡ�� ',320,220,null,contextPath,window,{a0000s:a0000s});
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
	//alert(jsonObj.length);//���root���Ӷ������� 
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
					"							<div class=\"col-50\">�Ա�:��</div>"+
					"							<div class=\"col-50\">����:62��</div>"+
					"							<div class=\"col-100\">ְλ:��ί�������о������¿Ƹ��Ƴ�</div>"+
					"						</td>  "+
					"					</tr>"+
					"				</table>"+
					"			</li>"+
					"			<li><div class=\"step-body step-green-head\"><div class=\"step-body-head\">����</div></div></li>"+
					"			<li><div class=\"step-body step-red\"><div class=\"step-body-head\">ְ��Ԥ��</div></div></li>"+
					"			<li><div class=\"step-body step-green\"><div class=\"step-body-head\">���Ԥ��</div></div></li>"+
					"			<li><div class=\"step-body step-blue\"><div class=\"step-body-head\">�������ȡ���</div></div></li>"+
					"			<li><div class=\"step-body step-blue\"><div class=\"step-body-head\">��֯����</div></div></li>"+
					"			<li><div class=\"step-body step-black\"><div class=\"step-body-head\">���۾���</div></div></li>"+
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
	//�����Ƽ��ı��,��Ե�޹ʻ��һ��td,���غ����ɾ��
	setTimeout(function(){
		var td=$('#deletetd').find("td:last-child");
			td.remove();
	},5000);
	
}


//��ʾ�����Ƽ�����
function addJs33row(){
	document.getElementById('js33row').value=parseInt(document.getElementById('js33row').value)+1;
	var js33row = document.getElementById('js33row').value;
	for(var i = 1; i <=js33row; i++){ //��ʾtr����
	     $("#js33tr_"+i).show();
	}
}

//���������һ��
function clearjs33row(i){
	var js33row=$("#js33row").val();
	//���ɾ���������һ�� 
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
	//���ɾ���Ĳ������һ��,������������
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
		//ɾ�����һ�����ݲ�����
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
