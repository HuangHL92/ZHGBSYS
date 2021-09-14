<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.business.helperUtil.IdCardManageUtil"%>
<%@page import="com.insigma.siis.local.business.entity.A36"%>
<%@page import="com.insigma.siis.local.business.entity.B01"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.AddPerson2PageModel"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.A01"%>
<%@page import="com.insigma.siis.local.business.entity.LogMain"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>

<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page isELIgnored="false"%> 
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%
SysManagerUtils.getUserId();
String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
HBSession sess = HBUtil.getHBSession();
if(request.getSession().getAttribute("tema0000")!=null){
	String a0001 = request.getSession().getAttribute("tema0000").toString();
}

String sql = "from A01 where a0000=?";
List list = sess.createQuery(sql).setString(0, a0000).list();
A01 a01 = (A01) list.get(0);
A01 a01E = new A01();
sql = " from B01 where b0111=?";
List list2 = sess.createQuery(sql).setString(0, a01.getA0195()).list();
B01 b01 = new B01();
if(list2!=null && list2.size()>0){
	b01 = (B01) list2.get(0);
}
//处理简历格式
String a1701 = a01.getA1701();//简历

a01.setA1701(AddPerson2PageModel.formatJL(a1701,new StringBuffer("")));
String a0107S = a01.getA0107();//出生日期
a01.setA0104(HBUtil.getCodeName("GB2261", a01.getA0104()));
a01.setA0117(HBUtil.getCodeName("GB3304", a01.getA0117()));
a01.setA0111(a01.getComboxArea_a0111());//   HBUtil.getCodeName("ZB01", a01.getA0111(),"code_name3")
a01.setA0114(a01.getComboxArea_a0114());//HBUtil.getCodeName("ZB01", a01.getA0114(),"code_name3")
a01.setA0107(AddPerson2PageModel.DateFmt(a01.getA0107()));
a01.setA0134(AddPerson2PageModel.DateFmt(a01.getA0134()));
a01.setA0165(HBUtil.getCodeName("ZB130", a01.getA0165()));
a01E.setA0160(HBUtil.getCodeName("ZB125", a01.getA0160()));
a01.setA0121(HBUtil.getCodeName("ZB135", a01.getA0121()));
request.setAttribute("a01", a01);
request.setAttribute("a01E", a01E);
if(b01!=null){
	request.setAttribute("b01", b01);
}
//家庭成员
String sqla36 = "from A36 where a0000='"+a0000+"' order by sortId,a3600";
List<A36> lista36 = sess.createQuery(sqla36).list();



%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.InfoComWindowPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{ 
color: red;
}
	.width-200{
		width: 218px !important;
	}
	.x-panel-body{
		
	}
	.btn3 {
	    BORDER-RIGHT: #7b9ebd 1px solid; 
	    PADDING-RIGHT: 2px; 
	    BORDER-TOP: #7b9ebd 1px solid; 
	    PADDING-LEFT: 2px; 
	    FONT-SIZE: 12px; 
	    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); 
	    BORDER-LEFT: #7b9ebd 1px solid; 
	    CURSOR: hand; COLOR: black; 
	    PADDING-TOP: 2px; 
	    BORDER-BOTTOM: #7b9ebd 1px solid;
	}
	
	.x-form-item{
		width: 100%;
		height: 100%;
		margin: 0px 0px 0px 0px;
		padding: 0px 0px 0px 0px;
	}
	.v_standard tr td{
		border-left: 2px solid #477aaf;
		border-top: 2px solid #477aaf;
		font-size: 16px;
		margin: 0px;
		padding:0px;
		font-family: '宋体';
		text-align: center;
		overflow: hidden;
		
		}
	.v_standard tr td textarea{
		font-family: '宋体', Simsun;
		font-size:15px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		text-align: center;
		border: 0px;
		margin: 0px;
		padding: 0px;
	}
	.v_standard tr td input{
		font-family: '宋体';
		size:14px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		border: 0px;
		margin: 0px!important;
		padding: 0px!important;
	}
	.top-last{
	border-bottom: 2px solid #477aaf;
	}
	.left-last{
	border-right: 2px solid #477aaf;
	}
	.v_standard{
	
	}
	.label-clor{
	background-color: #d4e9fc;
	}
	.width13-60{
	width: 60px !important;
	}
	.width24-80{
	width: 80px !important;
	}
	
	.width56-75{
	width: 75px !important;
	}
	.width5T6-150{
	width: 150px !important;
	}
	.width2T3-140{
	width: 157px !important;
	}
	.width2T3T4-220{
	width: 220px !important;
	}
	.width3T4-140{
	width: 157px !important;
	}
	.width6T7-195{
	width: 211px !important;
	}
	.width7-120{
	width: 136px !important;
	}
	.width3_7-410{
	width: 454px !important;
	
	}
	.width2_7-410{
	width: 536px !important;
	
	}
	.font-left{
	text-align: left !important;
	}
	.height1234-40{
	height: 40px !important;
	}
	.heightNew-40{
	height: 40px !important;
	}
	.height5678-80{
	height: 96px !important;
	}
	.height5i6i7i8-20{
	height: 24px !important;
	}
	.height9-35{
	height: 35px !important;
	}
	.height10-359{
	height: 365px !important;
	}
	.width-478{
	width: 536px !important;
	}
	.height10-445{
	height: 465px !important;
	}
	.input-text{
		text-align: center;
		line-height: 36px !important;
		border: 0px;
		
	}
	.input-text_left{
		text-align: left;
		line-height: 36px !important;
		border: 0px;
		
	}
	.input-text2{
		text-align: center;
		line-height: 24px !important;
		border: 0px;
		
	}
	.no-y-scroll{
		overflow-y:hidden !important;
	}
	.fontdisplay{
		padding-top: 10px !important;
	}
	
	div.TAwrap {   
	  display:table;   
	  _position:relative;   
	  overflow:hidden;   
	}   
	div.TAsubwrap {   
		
	  vertical-align:middle;   
	  display:table-cell;   
	  _position:absolute;   
	  z-index:-100;
	  _top:47%;  
	  
	}   
	div.TAcontent {   
		display: inline-block;
		_display: block !important;
		font-family: '宋体';
		font-size:14px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		line-height:142%;
	  _position:relative;   
	  _top:-47%;  
	  _left:  -50%; 
	  text-align: left;
	}
.v_standard_left tr td{
		border:0px;
		text-align: left;
		}
		
.cellbgclor{
	background-color: rgb(242,247,253)!important;
	background-image: none!important;
}
</style>
<odin:head></odin:head>
<script type="text/javascript" src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<%
//A01 a01 = (A01)request.getSession().getAttribute("a01");
//System.out.println(a01);
%>
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
function getPdfPath(){
	var pdfPath = document.getElementById('pdfPath').value;
	return pdfPath;
}

//查看权限变量
var buttonDisabled = false;
var spFeild = $h.spFeildAll.a01.concat($h.spFeildAll.a29).concat($h.spFeildAll.a11).concat($h.spFeildAll.a31).concat($h.spFeildAll.a30).concat($h.spFeildAll.a53).concat($h.spFeildAll.a37);
function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };





function exportLrmBtnNrm(){
	var a0184 = document.getElementById('a0184').value;//身份证号
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续打印？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				radow.doEvent('exportLrmBtnNrm.onclick');
			}else{
				return false;
			}		
		});
	}else{
		radow.doEvent('exportLrmBtnNrm.onclick');
	}
}

function exportLrmBtn(){
	var a0184 = document.getElementById('a0184').value;//身份证号
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续打印？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				radow.doEvent('exportLrmBtn.onclick');
			}else{
				return false;
			}		
		});
	}else{
		radow.doEvent('exportLrmBtn.onclick');
	}
}



//人员类别 专业技术类公务员任职资格联动
function selectchange(record,index){
	if(document.getElementById('Iorthers')){
		var orthersWindow = document.getElementById('Iorthers').contentWindow;
		if(orthersWindow){
			orthersWindow.onA0160Change(record,index);
		}
	}
}

function showdialog(){
	
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src;
}




</script>

<div id="floatToolDiv" style="margin-top: 27px; position: absolute;top: 0px;width:98%;z-index:1"></div>

<odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
<odin:hidden property="radow_parent_data" title="主键a0000" ></odin:hidden>
<div id="divrmb1">

	<br/><br/>
	<%-----------------------------人员基本信息-------------------------------------------------------%>
	<odin:hidden property="orgid" title="历史离退人员所在的机构id" ></odin:hidden>
	<odin:hidden property="pdfPath" title="pdf路径"/>
	<table cellpadding="0px;" align="center">
		<tr>
			<td>
				<table id="v_standard" class="v_standard" cellspacing="0px" cellpadding="0px;" align="left">
					<%--第1行- --%>
					<tr>
						<td class="label-clor width13-60 height1234-40" id="a0101SpanId_s">姓&nbsp;名</td>
						<td class="width24-80"><tags:TextAreainput4 property="a0101" defaultValue="${a01.a0101}"  cls="width24-80 height1234-40 no-y-scroll cellbgclor" label="姓名" /></td>
						<td class="label-clor width13-60" id="a0104SpanId_s">性&nbsp;&nbsp;别</td>
						<td class="width24-80"><tags:TextAreainput4 property="a0104" defaultValue="${a01.a0104}" cls=" height1234-40 width24-80 input-text cellbgclor"/></td>
						<td class="label-clor width56-75" id="a0107SpanId_s">出生年月</td>
						<td class="width56-75"><tags:TextAreainput4 property="a0107" label="出生年月" defaultValue="${a01.a0107}" cls="width56-75 height1234-40 no-y-scroll cellbgclor" /> </td>
						<td class="left-last label-clor width7-120" rowspan="4">
							<div style="width:120px; height:170px;cursor: pointer;" onclick="showdialog()">
								<img alt="照片" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="136" height="170" src="" /> 
							</div>
						
						</td>
						<td class="left-last top-last " valign="top" style="width: 185px;background-color: rgb(166,202,240);text-align: left;"  rowspan="10">
							<div style="width: 100%;height: 100%;">
								<table>
									<tr>
										<td style="height: 5px"></td>
									</tr>
								</table>
								<div id="a0163Divid" style="font-size: 18px;color: rgb(0,0,128);width: 100%; text-align: center;"></div>
								<table class="v_standard_left"  border="0" style="margin-left: 10px;">
									<tr><td><br/> </td></tr>
									<tr>
										<td align="left" style="text-align: left;"><span id="a0195SpanId_s" style="font-size: 14px;">统计关系所在单位</span></td>
									</tr>
									<tr style="width: 100px;">
										<odin:textEdit property="a0195" value="${b01.b0101}" readonly="true" ></odin:textEdit>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0184SpanId_s" style="font-size: 14px;">公民身份号码</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0184" value="${a01.a0184}" required="true" readonly="true"></odin:textEdit>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0165SpanId_s" style="font-size: 14px;">管理类别</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0165"  value="${a01.a0165}" readonly="true" ></odin:textEdit>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0160SpanId_s" style="font-size: 14px;">人员类别</span></td>
									</tr>
									<tr>
 									 <odin:textEdit property="a0160E" value="${a01E.a0160}" readonly="true"  ></odin:textEdit> 
 									 <odin:hidden property="a0160" value="${a01.a0160}"   ></odin:hidden> 
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0121SpanId_s" style="font-size: 14px;">编制类型</span></td>
									</tr>
									<tr>
									  <odin:textEdit property="a0121" value="${a01.a0121}" readonly="true" ></odin:textEdit>
									</tr>
								<!-- 现职务层次 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0221SpanId_s" style="font-size: 14px;">现职务层次</span></td>
									</tr>
									<tr>
										<%-- <odin:select2 property="a0221" codeType="ZB09" width="144"></odin:select2> --%>
										<odin:select2 hideTrigger="true" property="a0221"value="${a01.a0221}" codeType="ZB09" width="160" readonly="true"></odin:select2>
									</tr>
									<!-- 现职务层次时间 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0288SpanId_s" class="fontConfig" style="font-size: 14px;">现职务层次时间</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0288" width="158" readonly="true" value="${a01.a0288}"></odin:textEdit>
									</tr>
									<!-- 职级 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0192eSpanId_s" style="font-size: 14px;">现职级 </span></td>
									</tr>
									<tr>
										<%-- <odin:textEdit property="a0192e" width="144" readonly="true"></odin:textEdit> --%>
										<odin:select2 hideTrigger="true" property="a0192e" value="${a01.a0192e}" codeType="ZB148" width="160"  readonly="true"></odin:select2>
									</tr>
									<!-- 现职级时间 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0192cSpanId_s" class="fontConfig" style="font-size: 14px;">现职级时间</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0192c" width="158" readonly="true" value="${a01.a0192c}"></odin:textEdit>
									</tr>
									<!-- 其他信息 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;">
											<odin:groupBox title="其他信息" >
												<span>&nbsp;</span>
												<button onclick="$h.openWin('entry','pages.publicServantManage.EntryAddPage','进入管理',800,300,document.getElementById('a0000').value,ctxPath)" style="width:135px;margin-top: 20px;">进入管理</button>
												<span>&nbsp;</span>
												<button onclick="$h.openWin('logout','pages.publicServantManage.LogoutAddPage','退出管理',600,300,document.getElementById('a0000').value,ctxPath)" style="width:135px;margin-top: 10px;">退出管理</button>
												<span>&nbsp;</span>
												<button onclick="$h.openWin('appointRemove','pages.publicServantManage.AppointRemoveAddPage','拟任免',1150,300,document.getElementById('a0000').value,ctxPath)" style="width:135px;margin-top: 10px;">拟任免</button>
												<span>&nbsp;</span>
											</odin:groupBox>
										</td>
									</tr>
									
								</table>
							</div>
						<td/>
					</tr>
					<%--第2行----%>
					<tr>
						<td class="label-clor height1234-40" id="a0117SpanId_s">民&nbsp;&nbsp;族</td>
						<td style="position: relative;"><tags:TextAreainput4 property="a0117" defaultValue="${a01.a0117}" label="民族" cls="height1234-40 width24-80 input-text cellbgclor"  />
						</td>
						<td class="label-clor" id="a0111SpanId_s">籍&nbsp;&nbsp;贯</td>
						<td style="position: relative;"><tags:TextAreainput4 property="a0111" defaultValue="${a01.a0111}" label="籍贯" cls="height1234-40 width24-80 no-y-scroll cellbgclor"  /> 
							
						</td>
						<td class="label-clor"  id="a0114SpanId_s">出&nbsp;生&nbsp;地</td>
						<td style="position: relative;"><tags:TextAreainput4 property="a0114" defaultValue="${a01.a0114}" cls="height1234-40 width56-75 no-y-scroll cellbgclor"  label="出生地" /></td>
						
					</tr>
					<%--第3行--%>
					<tr>
						<td class="label-clor height1234-40" id="a0140SpanId_s">入&nbsp;&nbsp;党<br/>时&nbsp;&nbsp;间</td>				
						<td><tags:TextAreainput4 property="a0140" defaultValue="${a01.a0140}" cls="width24-80 height1234-40 no-y-scroll cellbgclor" ondblclick="$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath)" /></td>
						
						<td class="label-clor" id="a0134SpanId_s">参加工<br/>作时间</td>
						<td><tags:TextAreainput4 property="a0134" defaultValue="${a01.a0134}" label="参加工作时间" cls="width24-80 height1234-40 no-y-scroll cellbgclor" /></td>
							
						<td class="label-clor" id="a0128SpanId_s">健康状况</td>
						<td><tags:TextAreainput4 property="a0128" defaultValue="${a01.a0128}" cls="width56-75 height1234-40 no-y-scroll cellbgclor" label="健康状况" defaultValue="健康"/></td>
					</tr>
					<%--第4行--%>
					<tr>
						<td class="label-clor height1234-40" id="a0196SpanId_s">专业技<br/>术职务</td>                                               
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:TextAreainput4 property="a0196" defaultValue="${a01.a0196}" label="专业技术职务" cls="width2T3-140 height1234-40 no-y-scroll cellbgclor" ondblclick="$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',700,485,document.getElementById('a0000').value,ctxPath)" /> </td>
						<td class="label-clor" id="a0187aSpanId_s">熟悉专业<br/>有何特长</td>
						<td colspan="2" ><tags:TextAreainput4  property="a0187a" defaultValue="${a01.a0187a}"  cls="width5T6-150 height1234-40 no-y-scroll cellbgclor"  label="专长"/></td>
					</tr>
					
					<%--第5行--%>
					<tr>
						<td class="label-clor height5678-80" rowspan="4" id="xlxwSpanId_s">学&nbsp;历<br/>学&nbsp;位</td>
						<td class="label-clor" rowspan="2">全日制<br/>教&nbsp;&nbsp;育</td>															
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxl" defaultValue="${a01.qrzxl}" label="全日制教育：学历" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/> </td>
						<td class="label-clor" rowspan="2">毕业院校<br/>系及专业</td>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxlxx" defaultValue="${a01.qrzxlxx}" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/> </td>
					</tr>
					<%--第6行--%>
					<tr>
						
						
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxw" defaultValue="${a01.qrzxw}" label="全日制教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
						
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxwxx" defaultValue="${a01.qrzxwxx}" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
					</tr>
					<%--第7行--%>
					<tr>
						
						<td class="label-clor" rowspan="2">在&nbsp;&nbsp;职<br/>教&nbsp;&nbsp;育</td>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxl" defaultValue="${a01.zzxl}" label="在职教育：学历"  cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/> </td>
						<td class="label-clor" rowspan="2">毕业院校<br/>系及专业</td>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxlxx" defaultValue="${a01.zzxlxx}" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
					</tr>
					<%--第8行--%>
					<tr>
						
						
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxw" defaultValue="${a01.zzxw}" label="在职教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
						
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxwxx" defaultValue="${a01.zzxwxx}" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
					</tr>
					
					<%--第9行--%>
					<tr>
						<td class="label-clor height9-35" colspan="2" id="a0192aSpanId_s">工作单位及职务</td>															
						<td class="left-last" colspan="5" style="background-color: rgb(242,247,253)"><tags:Textinput property="a0192a" defaultValue="${a01.a0192a}" cls="height9-35 input-text_left width3_7-410 cellbgclor" label="工作单位及职务全称" ondblclick="$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',950,563,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
					</tr>
					<%--第10行--%>
					<tr>
						<td class="top-last label-clor height10-359">
							<div style="width: 100%;height: 100%;position: relative;">
								<span style="font-family: STHeiti; font-size: 16px;position: absolute;top:150px;left: 22px" id="a1701SpanId_s">简<br/>历</span>
							</div>
						</td>
						<td style="background-color: rgb(242,247,253)" class="left-last top-last" colspan="6"><tags:TextAreainput property="a1701" defaultValue="${a01.a1701}" cls="height10-359 width2_7-410 font-left cellbgclor" readonly="true"/>  </td>
					</tr>
					<tr>
						<td style="height: 20px">
						</td>
					</tr>
					<%--第1行--%>
					<tr>
						<td class="label-clor width13-60 height5678-80" colspan="1" id="a14z101SpanId_s">奖<br/>惩<br/>综<br/>述</td>
						<td style="background-color: rgb(242,247,253)" class="left-last  width-478" colspan="6"><tags:TextAreainput property="a14z101" defaultValue="${a01.a14z101}" label="奖惩综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="$h.openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','奖惩情况',830,500,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
						<td class="left-last top-last " valign="top" id="jc" style="width: 200px;background-color: rgb(166,202,240);text-align: left;"  rowspan="14">
							<div style="width: 100%;height: 100%;">
								<div id="a0163Divid2 style="font-size: 18px;color: rgb(0,0,128);width: 100%; text-align: center;"></div>
								<table class="v_standard_left"  border="0" style="margin-left: 10px;"></table>
							</div>
						<td/>
					</tr>
					<%--第2行--%>
					<tr>
						<td  class="label-clor height5678-80" colspan="1" id="a15z101SpanId_s">年度<br/>考核<br/>结果<br/>综述</td>
						<td style="background-color: rgb(242,247,253)" class="left-last" colspan="6"><tags:TextAreainput property="a15z101" defaultValue="${a01.a15z101}" label="年度考核结果综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="$h.openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','年度考核情况',830,360,document.getElementById('a0000').value,ctxPath)"  readonly="true"/></td>
					</tr>
					<%--第3行--%>
					<tr>
						<td class="label-clor " rowspan="11" id="tdrowspan" style="position: relative;">
						    <span style="_position: absolute;_left: 40%;_top: 15%; vertical-align: middle;"  id="a36SpanId_s" class="fontConfig">家<br/>庭<br/>主<br/>要<br/>成<br/>员<br/>及<br/>社<br/>会<br/>重<br/>要<br/>关<br/>系</span>
						</td>
						<td class="label-clor width13-60 height1234-40" id="a3604aSpanId_s">称&nbsp;&nbsp;谓</td>
						<td class="label-clor width56-75" id="a3601SpanId_s">姓&nbsp;&nbsp;名</td>
						<td class="label-clor width56-75" id="a3607SpanId_s">出生年月</td>
						<td class="label-clor width13-60 height1234-40" id="a3627SpanId_s">政&nbsp;治<br/>面&nbsp;貌</td>
						<td class="label-clor width-200 left-last" id="a3611SpanId_s" colspan="2">工作单位及职务</td>
					</tr>
					<%--第4行--%>
					<% for(Integer i=1;i<=10;i++){ 
						String a3604a_i = "a3604a_"+i;
						String a3601_i = "a3601_"+i;
						String a3607_i = "a3607_"+i;
						String a3627_i = "a3627_"+i;
						String a3611_i = "a3611_"+i;
						String a3600_i = "a3600_"+i;
						int length = lista36.size();
						A36 a36 = new A36();
						if(length>=i){
							a36 = lista36.get(i-1);
							a36.setA3604a(HBUtil.getCodeName("GB4761", a36.getA3604a()));
							a36.setA3627(HBUtil.getCodeName("GB4762", a36.getA3627()));
							a36.setA3607(AddPerson2PageModel.DateFmt(a36.getA3607()));
						}
						request.setAttribute("a36", a36);
						%>
					<tr>
						<td class="width13-60 height1234-40" style="background-color: rgb(242,247,253)"><tags:TextAreainput4 property="<%=a3604a_i %>" defaultValue="${a36.a3604a}" cls="height1234-40 width24-80 input-text cellbgclor"/></td>
						<td ><tags:TextAreainput4 property="<%=a3601_i %>" defaultValue="${a36.a3601}" cls="width56-75 height1234-40 no-y-scroll cellbgclor" /></td>
						<td ><tags:TextAreainput4 property="<%=a3607_i %>" defaultValue="${a36.a3607}" cls="width24-80 height1234-40 no-y-scroll cellbgclor"  label="出生年月"/></td>
						<td style="background-color: rgb(242,247,253)"><tags:TextAreainput4 property="<%=a3627_i %>" defaultValue="${a36.a3627}" cls="height1234-40 width56-75 input-text cellbgclor"/></td>
						<td class="left-last " colspan="2" style="background-color: rgb(242,247,253)">
							<tags:TextAreainput4 property="<%=a3611_i %>" defaultValue="${a36.a3611}" cls="height1234-40 width-200 no-y-scroll cellbgclor" />
							<odin:hidden property="<%=a3600_i %>" value="${a36.a3600}"/>
						</td>
					</tr>
					<%} %>
					<%--第14行--%>
					<tr id="targetNodeTR">
						<td class="top-last label-clor height5678-80 fontConfig">备<br/><br/>注</td>
						<td class="left-last top-last" colspan="6"><tags:TextAreainput property="a0180" defaultValue="${a01.a0180}" label="备注" cls="width-478 height5678-80 font-left" readonly="true" /></td>
					</tr>
					<tr>
						<td style="height: 20px">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</div>

<div id="divrmb2"">
	<br/><br/>
	
</div>
	<odin:hidden property="a0141" value="${a01.a0141}"></odin:hidden>
	<odin:hidden property="a0144" value="${a01.a0144}"></odin:hidden>
	<odin:hidden property="a3921" value="${a01.a3921}"></odin:hidden>
	<odin:hidden property="a3927" value="${a01.a3927}"></odin:hidden>	
	<odin:hidden property="a0194" value="${a01.a0194}"></odin:hidden>	
	<odin:hidden property="a0197" value="${a01.a0197}"></odin:hidden>
	<odin:hidden property="a0107" value="${a01.a0107}"></odin:hidden>	
	
<div id="rmbtabsdiv"></div>




<odin:window src="/blank.htm"  id="pdfViewWin" width="700" height="500" title="任免表打印预览界面" modal="true"/>	




<script type="text/javascript">

Ext.onReady(function(){
	window.exchangeMenu =  new Ext.menu.Menu({
	id:'exchangeMenu',
	items:[  
		new Ext.menu.Item({
			//id:'exportLrmBtnNrm',
			disabled:false,
			text:'打印任免表（包含拟任免信息）',
			handler:exportLrmBtnNrm
			}), 
		new Ext.menu.Item({
			//id:'exportLrmBtn',
			disabled:false,
			text:'打印任免表（不包含拟任免信息）',
			handler:exportLrmBtn
			}) 
	]});
});	

/**
*新增窗口
*/
function openWin(id,url){
	var newwin = Ext.getCmp(id);
	if(!newwin.rendered){  
		newwin.show();
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
		
	}else{  
		newwin.show();//alert("show")  
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
	} 
}
	
	newWin({id:'fontConfig',title:'字体设置',modal:true,width:380,height:230,maximizable:true});

	<%-- newWin({id:'picupload',title:'头像上传',modal:true,width:900,height:490,src:'<%=ctxPath%>/picCut/picwin.jsp'}); --%>


var thisTab = "",isveryfy=false;
Ext.onReady(function(){
	//初始化id
	var a0000id = '<%=a0000==null?"":a0000%>';
	document.getElementById("a0000").value=a0000id;
	document.getElementById("radow_parent_data").value=a0000id;
	if(a0000id==''){
		thisTab = parent.tabs.getActiveTab();
	}else{
		thisTab = parent.tabs.getItem(a0000id);
	}
	
	if(thisTab.initialConfig.errorInfo){
		isveryfy=true;
		var errorInfo = thisTab.initialConfig.errorInfo; 
		spFeild = new Array();
		//专业技术职务 学历学位 工作单位及职务 入党时间 奖惩情况 年度考核结果 家庭主要成员及社会重要关系
		var specialFeild = {"a0196":false,//专业技术职务
							"xlxw":false,//学历学位
							"a0192a":false,//工作单位及职务
							"a0140":false,//入党时间
							"a14z101":false,//奖惩情况
							"a15z101":false,//年度考核结果
							"a36":false};//家庭主要成员及社会重要关系
		var specialFeildids = ['a0196','xlxw','a0192a','a0140','a14z101','a15z101','a36'];
		for(var it=0;it<errorInfo.length;it++){
			if('A06'==errorInfo[it].tableCode){
				specialFeild.a0196 = true;
			}
			if('A08'==errorInfo[it].tableCode){
				specialFeild.xlxw = true;
			}
			if('A02'==errorInfo[it].tableCode){
				specialFeild.a0192a = true;
			}
			if('A14'==errorInfo[it].tableCode){
				specialFeild.a14z101 = true;
			}
			if('A15'==errorInfo[it].tableCode){
				specialFeild.a15z101 = true;
			}
			if('A36'==errorInfo[it].tableCode){
				specialFeild.a36 = true;
			}
			if('a0141'==errorInfo[it].columnCode.toLowerCase()||'a0144'==errorInfo[it].columnCode.toLowerCase()||'a3921'==errorInfo[it].columnCode.toLowerCase()||'a3927'==errorInfo[it].columnCode.toLowerCase()){
				specialFeild.a0140 = true;
			}
			spFeild.push(errorInfo[it].columnCode.toLowerCase());
		}
		for(var i2=0;i2<specialFeildids.length;i2++){
			if(specialFeild[specialFeildids[i2]]){
				spFeild.push(specialFeildids[i2]);
			}
		}
		
	}

	document.getElementById("divrmb1").style.display="block";
	document.getElementById("divrmb2").style.display="block";
	var tabs = new Ext.TabPanel({
		id:'rmbTabs',
		title:'helloal',
		region: 'center',
		margins: '0 3 0 0',
		activeTab: 0,
		applyTo:'rmbtabsdiv',
		enableTabScroll: true,
		autoScroll:false,
		frame : true,
		//plugins: new Ext.ux.TabCloseMenu(),
	listeners:{
		'tabchange':function(obj1,obj2){
		
		if(obj2.id=='rmb1'){
			ajusta0101();
			ajusta0140();
			ajusta0111();
			ajusta0114();
			ajusta0107();
			ajusta0134();
			ajusta0187a();
			ajusta0128();
			ajusta0196();
		}else if(obj2.id=='rmb2'){
			for(var i=1;i<=10;i++){ 
				if(document.getElementById('div_a3607_'+i).innerText!=''){
					eval('ajusta3607_'+i+'();');
				}
				if(document.getElementById('div_a3601_'+i).innerText!=''){
					eval('ajusta3601_'+i+'();');
				}
				if(document.getElementById('div_a3611_'+i).innerText!=''){
					eval('ajusta3611_'+i+'();');
				}
				if(document.getElementById('div_a3604a_'+i).innerText!=''){
					eval('ajusta3604a_'+i+'();');
				}
				if(document.getElementById('div_a3627_'+i).innerText!=''){
					eval('ajusta3627_'+i+'();');
				}
			}
			
		}else{}
	}
},
		items: [{
			autoScroll:true,
			id:"rmb1",
			title : '任免表录入',
			contentEl:'divrmb1'
		},{
			autoScroll:true,
			id:"rmb2",
			title : '信息集录入',
			contentEl:'divrmb2'
		}<%-- ,{
			autoScroll:true,
			id:"BusinessInfo",
			title : '业务信息',
			html: '<Iframe width="100%" height="100%" scrolling="auto" id="IBusinessInfo" frameborder="0" src="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.BusinessInfo" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
			
		},{
			autoScroll:true,
			id:"orthers",
			title : '其他信息',
			html: '<Iframe width="100%" height="100%" scrolling="auto" id="Iorthers" frameborder="0" src="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OrthersAddPage" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
			
		} --%>]
	});
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	
	
	tabs.activate(tabs.getItem('rmb2'));
	tabs.activate(tabs.getItem('rmb1'));	
    applyFontConfig(spFeild);
    applyFontConfig($h.spFeildAll.a36);
    
});

function applyFontConfig(spFeildx){
	var cls = 'fontConfig';
	if(isveryfy){
		cls = 'vfontConfig';
	}
	for(var i_=0;i_<spFeildx.length;i_++){
		if(document.getElementById(spFeildx[i_]+'SpanId_s')){
			$('#'+spFeildx[i_]+'SpanId_s').addClass(cls);
		}else if(document.getElementById(spFeildx[i_]+'SpanId')){
			$('#'+spFeildx[i_]+'SpanId').addClass(cls);
		}
    	
    }
}

</script>

<script type="text/javascript">
Ext.onReady(function(){
		
document.getElementById('personImg').src='<%=request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+a0000%>';
document.getElementById('a0163Divid').innerHTML="<%=HBUtil.getCodeName("ZB126", (a01.getA0163()==null?"":a01.getA0163()))%>";
		
});	
</script>
<odin:toolBar property="floatToolBar" applyTo="floatToolDiv">
	<odin:fill />
	<odin:buttonForToolBar text="打印任免表" id="printView" menu ="exchangeMenu" icon="image/u117.png"/>
	<odin:buttonForToolBar text="编辑模式" id="chmode" handler="chmode" isLast="true" 
		icon="images/keyedit.gif"  />
</odin:toolBar>
<script type="text/javascript">

function chmode(){
	window.location.href='<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson';
}


var buttonDisabled=true;
var fieldsDisabled="";
<%
String a0101 = a01.getA0101()==null?"":a01.getA0101();//姓名
String sex = a01.getA0104();
String age = "";
int agei = 0;
if((agei = IdCardManageUtil.getAgefrombirth(a0107S))!=-1){
	age = agei + "";
}
String title = a0101 + "，" + sex + "，" + age+"岁";
	
%>
Ext.onReady(function(){
window.parent.tabs.getItem(thisTab.initialConfig.tabid).setTitle('<%=title%>');

document.getElementById('a0192e_combo').focus();
document.getElementById('a0192e_combo').blur();
});


</script>

