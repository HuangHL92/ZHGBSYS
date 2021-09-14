<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.Hashtable"%>

<%--web 增人计划申报 wzp --%>
<link href="<%=request.getContextPath()%>/pages/css/control.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/browerinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/stepBar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/newpage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.Info2PageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%>

<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';

$(function(){
	stepBar.init("stepBar", {
		step : 1,
		change : false,
		animation : true
	});
});
<%--初始化--%>
function init(){
	step_func();
	daying11(1);
	var grid = odin.ext.getCmp('fjcl_div');
	var clientheight=document.body.clientHeight;
	var clientwidth=document.body.clientWidth;
}
function daying11(value){
	if(value<=stepvar){
		hidden_all();
		$("#"+arr[value-1]).css('display','');
		if(value==1){
		}else if(value==2){
			<%--新录用公务员计划详情--%>
			radow.doEvent("list_xlygw.dogridquery");
		}else if(value==3){
			<%--附件材料--%>
			radow.doEvent("list_yscfj.dogridquery");
		}else if(value==4){
			odin.alert("完成送审!");
		}
	}
}

function hidden_all(){
	document.getElementById("fjcl_div").style.display='none';
	document.getElementById("xlygwyjhxq_div").style.display='none';
	document.getElementById("zrjhxx_div").style.display='none';
	document.getElementById("wcss_div").style.display='none';
}

</script>


<div id="stepBar" class="ui-stepBar-wrap">
	<div class="ui-stepBar">
		<div class="ui-stepProcess"></div>
	</div>
	<div class="ui-stepInfo-wrap">
		<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0" style="height: 120px;">
			<tr>
				<td class="ui-stepInfo">
					
					<a class="ui-stepSequence" onclick="djb(1)">1</a>
					<p class="ui-stepName" >人员基本信息</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(2)">2</a>
					<p class="ui-stepName" >工作单位及职务</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(3)">3</a>
					<p class="ui-stepName" >现职务层次</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(4)">4</a>
					<p class="ui-stepName" >现职级</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(5)">5</a>
					<p class="ui-stepName" >专业技术职务</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(6)">6</a>
					<p class="ui-stepName">学历学位</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(7)">7</a>
					<p class="ui-stepName">奖惩综述</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(8)">8</a>
					<p class="ui-stepName">考核信息</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(9)">9</a>
					<p class="ui-stepName">家庭成员</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(10)">10</a>
					<p class="ui-stepName">进入管理</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(11)">11</a>
					<p class="ui-stepName">退出管理</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(12)">12</a>
					<p class="ui-stepName">拟任免</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(13)">13</a>
					<p class="ui-stepName">自定义指标项</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(14)">14</a>
					<p class="ui-stepName">电子档案</p>
				</td>
			</tr>
		</table>
	</div>
</div>
<%--完成送审 start --%>
<div id="wcss_div" style="display:'';">
	<hr style="height:1px;border:none;border-top:1px solid #99BBE8;" width="100%">
</div>
<%--完成送审 end --%>

<script type="text/javascript">

</script>
<odin:hidden property="status" title="删除状态" ></odin:hidden>
<odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
<odin:hidden property="a0163" title="人员状态" ></odin:hidden>
<odin:hidden property="comboxArea_a0111" title="籍贯" ></odin:hidden>
<odin:hidden property="comboxArea_a0114" title="出生地" ></odin:hidden>
<!-- 1：有修改权限，2：无修改权限 -->
<odin:hidden property="isUpdate" title="是否有修改权限" ></odin:hidden>

<!-- 入党时间使用相关值 -->
<odin:hidden property="a0141"></odin:hidden>
<odin:hidden property="a0144"></odin:hidden>
<odin:hidden property="a3921"></odin:hidden>
<odin:hidden property="a3927"></odin:hidden>

<!-- A01任免表录入有，信息集录入没有的信息，隐藏域，这是为了防止信息丢失 -->
<odin:hidden property="a0180" title="备注"></odin:hidden>
<odin:hidden property="a0221" title="现职务层次"></odin:hidden>
<odin:hidden property="a0288" title="现职务层次时间"></odin:hidden>
<odin:hidden property="a0192e" title="现职级"></odin:hidden>
<odin:hidden property="a0192c" title="现职级时间"></odin:hidden>

<odin:hidden property="qrzxl" title="全日制学历"></odin:hidden>
<odin:hidden property="qrzxlxx" title="全日制学历学校"></odin:hidden>
<odin:hidden property="qrzxw" title="全日制学位"></odin:hidden>
<odin:hidden property="qrzxwxx" title="全日制学位学校"></odin:hidden>
<odin:hidden property="zzxl" title="在职学历"></odin:hidden>
<odin:hidden property="zzxlxx" title="在职学历学校"></odin:hidden>
<odin:hidden property="zzxw" title="在职学位"></odin:hidden>
<odin:hidden property="zzxwxx" title="在职学位学校"></odin:hidden>

<odin:hidden property="b0194Type" value=''/>
<odin:hidden property="a0192f" title="工作单位及职务全称对应的，任职时间" ></odin:hidden>

<div id="div1" style="margin-left:5%;display:'';width: 95%;border:1px solid #00F">
<!-- <odin:groupBox title="人员基本信息"> -->
	<div id="btnToolBarDivA01" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA01" applyTo="btnToolBarDivA01">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="上一人员" id="lastp" icon="images/icon/left2.gif"/>
					<odin:buttonForToolBar text="下一人员" id="nextp" icon="images/icon//right2.gif"/>
					<odin:buttonForToolBar text="保存" id="InfoSaveA01" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="savePerson"></odin:buttonForToolBar>
	</odin:toolBar>
	<table style="width:100%;" >
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<odin:textEdit property="a0101" required="true" label="姓名"></odin:textEdit>
			<odin:textEdit property="a0184" label="公民身份证号码" required="true" validator="$h.IDCard"></odin:textEdit>
			<odin:select2 property="a0104" required="true" label="性别" codeType="GB2261"></odin:select2>
			
		</tr>
		<tr>
			<odin:NewDateEditTag property="a0107" required="true" label="出生年月" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:select2 property="a0117" required="true" label="民族" codeType="GB3304"></odin:select2>		
			<tags:PublicTextIconEdit property="a0111" label="籍贯" codetype="ZB01" readonly="true" required="true" onchange="a0111Change" codename="code_name3"></tags:PublicTextIconEdit>
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0114" label="出生地" codetype="ZB01" readonly="true" required="true" onchange="a0114Change" codename="code_name3"></tags:PublicTextIconEdit>	
			<odin:textEdit property="a0140" label="入党时间" ondblclick="a0140Click()" onkeypress="a0140Click2()"  onclick="a0140Click()"></odin:textEdit>
			<odin:NewDateEditTag property="a0134" label="参加工作时间" isCheck="true" maxlength="8" required="true"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0128" required="true" label="健康状况" codeType="GB2261D"></odin:select2>
			<odin:textEdit property="a0187a" label="专长"></odin:textEdit>
			<odin:select2 property="a0165" codeType="ZB130" label="管理类别" required="true"></odin:select2>
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0160" codetype="ZB125" width="160" label="人员类别" readonly="true" required="true"/>
			<odin:select2 property="a0121" codeType="ZB135" label="编制类型" required="true"></odin:select2>
			<!-- <odin:select2 hideTrigger="true" property="a0221" codeType="ZB09" width="177" ondblclick="a0221Click()" onkeypress="a0221Click2()" readonly="true" label="现职务层次"></odin:select2> -->
		</tr>
		<!-- <tr>
			<odin:textEdit property="a0288" width="177" readonly="true" label="现职务层次时间"></odin:textEdit>
			<odin:select2 hideTrigger="true" property="a0192e" codeType="ZB148" width="177" ondblclick="a0192eClick()" onkeypress="a0192eClick2()" readonly="true" label="现职级"></odin:select2>
			<odin:textEdit property="a0192c" width="177" readonly="true" label="现职级时间"></odin:textEdit>
		</tr> -->
		<tr>
			<odin:textarea property="a1701" label="简历" colspan='4' rows="16"></odin:textarea>
			
			<td class="top-last label-clor height10-359">
				<div style="width: 100%;height: 100%;position: relative;">
					<div onclick="showwin()"  title="简历生成" style="width:22px;height:20px;background-image:url(images/icon_column.gif);background-size:100%;  margin-bottom: 0px;position: absolute;top: 2px;left: 0px;cursor:pointer;"></div>
				</div>
			</td>
			<td class="left-last label-clor width7-120" rowspan="4">
				<div style="width:120px; height:170px;cursor: pointer;" onclick="showdialog()">
					<img alt="照片" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="136" height="170" src=""  /> 
				</div>
			</td>
		</tr>
	</table>
	
<!-- </odin:groupBox> -->
<%--简历生成--%>
	<div id="jlcontent" style="display: none;">
	    <odin:tab id="jltab" >
		    <odin:tabModel>
		    	<odin:tabItem title="简历范例" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="按职务自动生成简历" id="tab2" isLast="true"></odin:tabItem>
		    </odin:tabModel>
		    <odin:tabCont itemIndex="tab1">
				<span id="contenttext" style="font-family: '宋体', Simsun;">
					<%--日期16个字符 2个空格 描述--%> 
				<br>   
				1973.07--1977.09&nbsp;&nbsp;某某省某某县某某镇小学教师<br/>
				1977.09--1979.09&nbsp;&nbsp;某某省某某县某某镇初中教师<br/>
				1979.09--1988.11&nbsp;&nbsp;某某省某某市委党校教师（其间：1985.10--<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1988.07在某某省委党校电教大专班学习）<br/>
				1988.11--1993.07&nbsp;&nbsp;某某省某某市委宣传部干事、副科长、科长<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1987.09--1992.07在某某大学某某系某某专业学习）<br/>
				1993.07--1995.11&nbsp;&nbsp;某某省某某市某某局副局长<br/>
				1995.11--1998.05&nbsp;&nbsp;某某省某某市某某局局长<br/>
				1998.05--2005.09&nbsp;&nbsp;某某省某某市副市长（其间：2001.08--2004.05<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在中央党校某某专业研究生班学习）<br/>
				2005.09--2005.10&nbsp;&nbsp;某某省某某市委常委、副市长<br/>
				2005.10--2007.02&nbsp;&nbsp;某某省某某市委副书记、代市长<br/>
				2007.02--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;某某省某某市委副书记、市长<br/>
																<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第××届中央候补委员	
				</span>
				<%-- <odin:button text="全选" property="qx1" handler="selectall"></odin:button> --%>	
				<div style="height: 70px"></div>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<br>
		    	<span id="contenttext2" style="font-family: '宋体', Simsun;height: 240px"></span>
		    	<odin:button property="qx2" text="&nbsp;&nbsp;全&nbsp;&nbsp;选&nbsp;&nbsp;" handler="selectall2"></odin:button>	
		    </odin:tabCont>
	    </odin:tab>
	</div>
<!-- <table style="width:100%;" >
	<col width="50%">
	<col width="50%">
	<tr>
		<td align="right">
			<odin:button property="InfoSaveA01" handler="savePerson" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
		</td>
		<td align="left">
			<odin:button text="保存并下一步" property="bcn1"></odin:button>
		</td>
	</tr>
</table> -->
</div>

<script type="text/javascript">
function savePerson(a,b,confirm){
	var a0101 = document.getElementById('a0101').value;//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	
	var orthersWindow = null;
	
	
	if(a0101==''){
		$h.alert('系统提示：','姓名不能为空！',null,220);
		return false;
	}
	if (a0101.indexOf(" ") >=0){
		$h.alert('系统提示',"姓名不能包含空格！",null,220);
		return false;
	}
	if(a0101.length>18){
		$h.alert('系统提示：','姓名字数不能超过18！',null,220); 
		return false;
	}
	
	//校验身份证是否合法
	if(a0184==''){
		$h.alert('系统提示：','身份证号不能为空!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('系统提示：','身份证号不能超过18位!',null,220);
		return false;
	}
	
	
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续保存？',400,function(id) { 
			if("ok"==id){
				Ext.getCmp('a0184').clearInvalid();
				savePersonSub(a,b,confirm,false);
			}else{
				return false;
			}		
		});
	}else{
		savePersonSub(a,b,confirm,true);
	}
}


function savePersonSub(a,b,confirm,isIdcard){
	var a0101 = document.getElementById('a0101').value;//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var a0104 = document.getElementById('a0104').value;//性别 
	
	if(a0104==''){
		$h.alert('系统提示：','性别不能为空！',null,220);
		return false;
	}
	
	//出生日期格式
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('系统提示：','出生年月：'+datetext,null,320);
		return false;
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = a0107;//出身年月
	var msg = '出生日期与身份证不一致,合法日期格式为:'+birthdaya0184+'或'+birthdaya0184.substring(0, 6)+'，<br/>是否继续保存？';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("系统提示：",msg,400,function(id) { 
			if("ok"==id){
				radow.doEvent('InfoSave.onclick',confirm);
			}else{
				return false;
			}		
		});	
		return false;
	}else{
		radow.doEvent('InfoSave.onclick',confirm);
		//其他信息
		
	}
}
</script>

<!--------------------------------------- 职务信息集 --------------------------------------------->
				<%@include file="/comOpenWinInit.jsp" %>
				<script type="text/javascript">
				function deleteRowRendererA02(value, params, record,rowIndex,colIndex,ds){
					var a0200 = record.data.a0200;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">删除</a>";
				}
				function deleteRow2(a0200){ 
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("系统提示","最后一条数据无法删除！");
						return;
					}
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',a0200);
						}else{
							return;
						}		
					});	
				}
				
				function deleteRow(){ 
					var sm = Ext.getCmp("WorkUnitsGrid").getSelectionModel();
					if(!sm.hasSelection()){
						Ext.Msg.alert("系统提示","请选择一行数据！");
						return;
					}
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("系统提示","最后一条数据无法删除！");
						return;
					}
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				Ext.onReady(function(){});
				//工作单位职务输出设
				function a02checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0800);
					radow.doEvent('workUnitsgridchecked',sr.data.a0200);
				}
				
				
				
				function changeSelectData(item){
					var a0255f = Ext.getCmp("a0255_combo");
					var newStore = a0255f.getStore();
					newStore.removeAll();
					newStore.add(new Ext.data.Record(item.one));
					newStore.add(new Ext.data.Record(item.two));
					var keya0255 = document.getElementById("a0255").value;//alert(item.one.key+','+keya0255);
					if(item.one.key==keya0255){
						a0255f.setValue(item.one.value);
					}else if(keya0255==''){
						a0255f.setValue(item.one.value);
						document.getElementById("a0255").value=item.one.key;
					}else{
						a0255f.setValue(item.two.value);
						document.getElementById("a0255").value=item.two.key;
					}
				}
				
				var labelText={'a0255SpanId':['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>任职状态','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>工作状态'],
							   'a0201bSpanId':['<font color="red">*</font>任职机构名称','<font color="red">*</font>工作机构名称'],
							   'a0216aSpanId':['<font color="red">*</font>职务名称','<font color="red">*</font>岗位名称'],
							   //'a0215aSpanId':['职务名称代码','岗位名称代码'],
							   'a0221SpanId':['职务层次','岗位层次'],
							   'a0229SpanId':['分管（从事）工作','岗位工作'],
							   'a0243SpanId':['任职时间','工作开始']};
							   
				function changeLabel(type){
					for(var key in labelText){
						document.getElementById(key).innerHTML=labelText[key][type];
					}
				}		   
				function a0222SelChangePage(record,index){//岗位类别onchange时，职务层次赋值为空
					document.getElementById("a0221").value='';
					document.getElementById("a0221_combo").value='';
					a0221achange();
					a0222SelChange(record,index)
				}	
				function a0222SelChange(record,index){
				
					var a0222 = document.getElementById("a0222").value;
					var a0201b = document.getElementById("a0201b").value;
					
				
					document.getElementById("codevalueparameter").value=a0222;
					
					if("01"==a0222){//班子成员
						
						selecteEnable('a0201d','0');
					}else{
						
						selecteDisable('a0201d');
					}
					
					if("01"==a0222||"99"==a0222){//公务员、参照管理人员岗位or其他
						
						selecteEnable('a0219');//职务类别
						selecteEnable('a0251');//职动类型
						selecteWinEnable('a0247');//选拔任用方式
						document.getElementById('yimian').style.visibility="visible";
						
						changeSelectData({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}});
						changeLabel(0);
					}else if("02"==a0222||"03"==a0222){//事业单位管理岗位or事业单位专业技术岗位
						selecteDisable('a0219');//职务类别disabled
						selecteEnable('a0251');//职动类型
						selecteWinEnable('a0247');//选拔任用方式
						document.getElementById('yimian').style.visibility="visible";
						changeSelectData({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}});
						changeLabel(0);
						
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						
						selecteDisable('a0219');//职务类别
						selecteDisable('a0251');//职动类型
						selecteWinDisable('a0247');//选拔任用方式
						document.getElementById('yimian').style.visibility="hidden";
						changeSelectData({one:{key:'1',value:'在职'},two:{key:'0',value:'不在职'}});
						changeLabel(1);
					}else{
						
						document.getElementById('yimian').style.visibility="hidden";
					}
					a0255SelChange();
					
				}
				function a0255SelChange(){
					var a0222 = document.getElementById("a0222").value;
					if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						return;
					}
					
					var a0255 = $("input[name='a0255']:checked").val();
					if("1"==a0255){//在任
						document.getElementById('yimian').style.visibility="hidden";
					}else if("0"==a0255){//以免
						document.getElementById('yimian').style.visibility="visible";
					}
					document.getElementById('a0255').value = a0255;
				}
				
				function setA0216aValue(record,index){//职务简称
					Ext.getCmp('a0216a').setValue(record.data.value);
				}
				function setA0255Value(record,index){
					Ext.getCmp('a0255').setValue(record.data.key)
				}
				//a01统计关系所在单位
				function setParentValue(record,index){
					document.getElementById('a0195key').value = record.data.key;
					document.getElementById('a0195value').value = record.data.value;
					
					var a0195 = document.getElementById("a0195").value;
					var B0194 = radow.doEvent('a0195Change',a0195);
					
				}
				function witherTwoYear(){
				
					parent.document.getElementById('a0197').value=record.data.key;
				}
				//a01级别
				function setParentA0120Value(record,index){
					parent.document.getElementById('a0120').value=record.data.key;
				}
				//a01 基层工作年限
				function setParentA0194Value(record,index){
					
				}
				
				function a0201bChange(record){
					//任职结构类别 和 职务名称代码对应关系
					radow.doEvent('setZB08Code',record.data.key);
					
					//如果当前人员还没有“统计关系所在单位”，并且还是当前没有职务,则给“统计关系所在单位”赋值为任职机构 
					radow.doEvent("a0201bChange",record.data.key);
					
				}
				function a0251change(){//职动类型  破格晋升
					var a0251 = document.getElementById('a0251').value;
					var a0251bOBJ = document.getElementById('a0251b');
					var a0251bTD = document.getElementById('a0251bTD');
					if('26'==a0251){
						
					}else if('27'==a0251){
						a0251bOBJ.checked=true;
						
					}else{
						
					}
				}
				
				function setA0201eDisabled(){
					var a0201d = document.getElementById("a0201d").checked;
					document.getElementById('a0201eSpanId').innerHTML='成员类别';
					if(!a0201d){
						document.getElementById("a0201e_combo").disabled=true;
						document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
						document.getElementById("a0201e_combo").style.backgroundImage="none";
						Ext.query("#a0201e_combo+img")[0].style.display="none";
						document.getElementById("a0201e").value="";
						document.getElementById("a0201e_combo").value="";
					}else{
						document.getElementById("a0201e_combo").readOnly=false;
						document.getElementById("a0201e_combo").disabled=false;
						document.getElementById("a0201e_combo").style.backgroundColor="#fff";
						document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
						Ext.query("#a0201e_combo+img")[0].style.display="block";
						if(a0201d){
							document.getElementById('a0201eSpanId').innerHTML='<font color="red">*</font>成员类别';
						}
					}
				}

				$(function(){
				    $("#a0201d").change(function() {
				    	setA0201eDisabled();
				    });
				}); 
				
				
				
				</script>
				<div id="div2" style="display:'';">
				<odin:hidden property="a0200" title="主键id" ></odin:hidden>
				<odin:hidden property="a0255" title="任职状态" ></odin:hidden>
				<odin:hidden property="a0225" title="成员内排序" value="0"></odin:hidden>
				<odin:hidden property="a0223" title="多职务排序" ></odin:hidden>
				<odin:hidden property="a0201c" title="机构简称" ></odin:hidden>
				<odin:hidden property="codevalueparameter" title="职务层次和岗位类别的联动"/>
				<odin:hidden property="ChangeValue" title="职务名称代码和单位类别的联动"/>
				<odin:hidden property="a0271" value="0"/>
				<odin:hidden property="a0222" value='0'/>
				<odin:hidden property="a0195key" value=''/>
				<odin:hidden property="a0195value" value=''/>
				
				<!-- <odin:groupBox title="职务信息集"> -->
				
				<div id="A02" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<div id="btnToolBarDiv"></div>
				<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="saveA02" ></odin:buttonForToolBar>
				</odin:toolBar>
				
				<table style="width: 100%">
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="统计关系所在单位" readonly="true" codetype="orgTreeJsonData" width="250"></tags:PublicTextIconEdit3>
									<td rowspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label for="a0197" style="font-size: 12px;" id="a0197SpanId">是否具有两年以上基层工作经历 </label>
										<input align="middle" type="checkbox" name="a0197"  id="a0197" onclick="witherTwoYear()" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<odin:textEdit property="a0192a" width="550" label="全称"  maxlength="1000"><span>&nbsp;&nbsp;(用于任免表)</span></odin:textEdit>
									<td rowspan="2"><odin:button text="更新名称" property="UpdateTitleBtn" ></odin:button></td>
									<td rowspan="2"><odin:button text="集体内排序" property="personGRIDSORT" handler="openSortWin" ></odin:button></td>
								</tr>
								<tr>
							       <odin:textEdit property="a0192" width="550" label="简称"  maxlength="1000"><span>&nbsp;&nbsp;(用于名册)</span></odin:textEdit>
							    </tr>	
							</table>
						</td>
					</tr>
				    <tr>
					    <td>
					    	<table width="330"><tr><td>
						    	<odin:editgrid property="WorkUnitsGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" 
									 height="330" title="" pageSize="50">
										<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								     		<odin:gridDataCol name="a0281"/>
								     		<odin:gridDataCol name="a0200" />
									  		<odin:gridDataCol name="a0201b" />
									  		<odin:gridDataCol name="a0201a" />
									  		<odin:gridDataCol name="a0215a" />
									  		<odin:gridDataCol name="a0216a" />
									  		<odin:gridDataCol name="a0222" />
									   		<odin:gridDataCol name="a0255" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
										  <odin:gridRowNumColumn />
										  <odin:gridEditColumn2 header="输出" editor="checkbox" dataIndex="a0281" checkBoxClick="a02checkBoxColClick" edited="true"/>
										  <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="任职机构代码" edited="false"  dataIndex="a0201b"  editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="任职机构" edited="false" dataIndex="a0201a" renderer="changea0201a" editor="text"/>
										  <odin:gridEditColumn2 header="职务名称代码" edited="false"  dataIndex="a0215a" editor="select" codeType="ZB08" hidden="true"/>
										  <odin:gridEditColumn2 header="职务名称" edited="false"  dataIndex="a0216a" editor="text"/>
										  <odin:gridEditColumn2 header="岗位类别" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="任职状态" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select"/>
										  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA02" isLast="true"/>
									</odin:gridColumnModel>
								</odin:editgrid>
								<label style="font-size: 13px;"><input type="checkbox" checked="checked" id="xsymzw" onclick="checkChange(this)"/>显示已免职务</label>
								<div id="btngroup"> </div>
								<div style="margin-top: 8px;" id="btngroup2"> </div>
								</td>
							</tr>
						</table>
				
				
				    	</td>
				    	<td >
				    		<table>
				    			<tr  align="left">
				    				<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="任职机构" property="a0201b" defaultValue=""/>
				    				<!-- <odin:select2 property="a0255" label="任职状态" required="true" onchange="a0255SelChange" value="1" codeType="ZB14"></odin:select2> -->
				    				
				    				<td align="right"><!-- <span id="a0195SpanId_s" style="font-size: 12px;">任职状态&nbsp;</span> --></td>
								    <td align="left">
										<input align="middle" type="radio" name="a0255" id="a02551" checked="checked" value="1" class="radioItem"/>
										<label for="a0255" style="font-size: 12px;">在任</label>
										<span>&nbsp;</span>
										<input align="middle" type="radio" name="a0255" id="a02550" value="0" class="radioItem"/>
										<label for="a0255" style="font-size: 12px;">已免</label>
									</td>
								</tr>
								<tr>
									<odin:textEdit property="a0216a" label="职务名称" required="true" maxlength="50"></odin:textEdit>
									<!-- <odin:select2 property="a0201d" label="是否班子成员" data="['1','是'],['0','否']" onchange="setA0201eDisabled"></odin:select2> -->
									<td></td>
									<td align="left" id="a0219TD">
										<input align="middle" type="checkbox" name="a0219" id="a0219" />
										<label id="a0219SpanId" for="a0219" style="font-size: 12px;">领导职务</label>
									</td>	
								</tr>
								
								<tr align="left">
								    <odin:select2 property="a0201e" label="成员类别" codeType="ZB129"></odin:select2>
								    <td></td>
								    <td align="left" id="a0201dTD">
										<input align="middle" type="checkbox" name="a0201d" id="a0201d"/>
										<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">是否班子成员</label>
									</td>
								    
								</tr>
								<tr>
									
									<tags:PublicTextIconEdit property="a0247" label="选拔任用方式" codetype="ZB122" readonly="true" required="true"></tags:PublicTextIconEdit>
									<td></td>
									<td align="left" id="a0251bTD">
										<input align="middle" type="checkbox" name="a0251b" id="a0251b" />
										<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">破格提拔</label>
									</td>
									
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0243" isCheck="true" labelSpanId="a0243SpanId" maxlength="8" label="任职时间" required="true"></odin:NewDateEditTag>
									<odin:textEdit property="a0245" label="任职文号" validator="a0245Length"></odin:textEdit>
								</tr>
								<tr align="left">
								    <odin:hidden property="a0221a" value="0"/> 
								</tr>
								<tr align="left" >
								    <odin:hidden property="a0229" value="0"/>
								    <odin:hidden property="a0251" value="0"/>
								</tr>
								<tr>
									
								</tr>
								<tr id='yimian'>
									<odin:NewDateEditTag property="a0265" isCheck="true" label="免职时间" labelSpanId="a0265SpanId"  maxlength="8"></odin:NewDateEditTag>
									<odin:textEdit property="a0267" label="免职文号" validator="a0267Length"></odin:textEdit>
								</tr>
								<tr>
									<!-- 新库添加职务变动综述 tongzj 2017/5/29 -->
								</tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
				    		</table>
				    	</td>
				    </tr>
				    <tr>
				    	<td align="right" colspan="4"><div id="btngroup3" ></div></td>
				    </tr>
				</table>
				</div>
				<!-- </odin:groupBox> -->
				<odin:hidden property="a0281" title="输出设置"/>
				
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'上移',
						    cls :'inline',
						    renderTo:"btngroup",
						    handler:UpBtn
						});
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'下移',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:DownBtn
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'保存排序',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('worksort');
						    }
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'sortUseTimeS',
						    text:'按任职时间排序',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('sortUseTime');
						    }
						});
					});
					//统计关系所在单位和任职机构名称提醒
					function saveA02(){
						
						var a0247 = document.getElementById('a0247').value;
						if(!a0247){
							$h.alert('系统提示','选拔任用方式不能为空！', null,200);
							return false;
						}
						
						//任职时间验证 
						var a0243 = document.getElementById("a0243").value;//任职时间
						var a0265 = document.getElementById('a0265').value;//免职时间
						var a0243_1 = document.getElementById("a0243_1").value;		//任职时间页面显示值
						var a0265_1 = document.getElementById("a0265_1").value;		//免职时间页面显示值 
						
						
						var now = new Date();
						var nowTime = now.toLocaleDateString();
						var year = nowTime.substring(0 , 4);//年
						var MonthIndex = nowTime.indexOf("月");
						var mon = nowTime.substring(5 , MonthIndex);//月
						var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
						if(mon.length == 1){
							mon = "0" + mon;
						}
						if(day.length == 1){
							day = "0" + day;
						}
						
						nowTime = year + mon + day;//获取八位数的时间字符串
						
						var time = a0243;
						if(time.length == 6){
							time = time + "01";
						}
						
						if(parseInt(time) > parseInt(nowTime)){
							odin.alert("任职时间不能晚于系统当前时间");
							return false;
						}
						
						var time2 = a0265;
						if(time2.length == 6){
							time2 = time2 + "01";
						}
						if(parseInt(time2) > parseInt(nowTime)){
							odin.alert("免职时间不能晚于系统当前时间");
							return false;
						}
						
						if(!a0243_1){
							$h.alert('系统提示','任职时间不能为空！', null,200);
							return false;
						}
						
						var a0255 = $("input[name='a0255']:checked").val();
						/* if("0"==a0255 && !a0265_1){
							$h.alert('系统提示','免职时间不能为空！', null,200);
							return false;
						} */
						
						
						var text1 = dateValidate(a0243_1);
						var text2 = dateValidate(a0265_1);
						
						if(a0243_1.indexOf(".") > 0){
							text1 = dateValidate(a0243);
						}
						if(a0265_1.indexOf(".") > 0){
							text2 = dateValidate(a0265);
						}
						
						
						if(text1!==true){
							$h.alert('系统提示','任职时间' + text1, null,400);
							return false;
						}
						if(text2!==true){
							$h.alert('系统提示','免职时间' + text2, null,400);
							return false;
						}
						
						
						/* if(!a0243){
							$h.alert('系统提示','任职时间不能为空！', null,200);
							return false;
						} */
						
						//职务名称：必须填写且不可以有空格或其它非汉字字符
						var a0216a = document.getElementById("a0216a").value;
						if(!a0216a){
							odin.alert("职务名称不可为空！");
							return;
						}
						if (a0216a.indexOf(" ") >=0){
							odin.alert("职务名称不能包含空格！");
							return;
						}
					    if(!(/^[\u3220-\uFA29]+$/.test(a0216a))){
					    	odin.alert("职务名称不能包含非汉字字符！");
							return;
					    }
						
						var a0201b = document.getElementById('a0201b').value;
						var a0195 = document.getElementById('a0195').value;
						if(a0201b==""){
							Ext.Msg.alert("系统提示","请先点击图标进行任职机构的选择！");
							return;
						}
						if(a0195 != null && a0195!=a0201b){
							Ext.MessageBox.confirm(
								'提示',
								'统计关系所在单位和任职机构不相同，是否继续保持？',
								function (btn){
									if(btn=='yes'){
										radow.doEvent('saveWorkUnits.onclick');
									}
								}
							);
						}else{
							radow.doEvent('saveWorkUnits.onclick');
						}
					}
				</script>
				<script>
				
				function changea0201a(value, params, record,rowIndex,colIndex,ds){
					if(record.data.a0201b=='-1'){
						return '<a title="'+value+'(机构外)">'+value+'(机构外)</a>';
					}else{
						return '<a title="'+value+'">'+value+'</a>';
					}	
				}
				function seta0255Value(value, params, record,rowIndex,colIndex,ds){
					var a0222 = record.data.a0222;
					var textValue = '';
					if("01"==a0222||"99"==a0222||"02"==a0222||"03"==a0222){
					   	textValue = getTextValue({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}},value);
					}else if("04"==a0222||"05"==a0222||"06"== a0222 || "07"==a0222){//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
						textValue = getTextValue({one:{key:'1',value:'在职'},two:{key:'0',value:'不在职'}},value);
					}	
					return '<a title="'+textValue+'">'+textValue+'</a>';
				}
				function getTextValue(item,v){
					if(item.one.key==v){
						return item.one.value;
					}else{
						return item.two.value;
					}
				}
				function checkChange(){
					var checkbox = document.getElementById("xsymzw");
					var grid = Ext.getCmp("WorkUnitsGrid");
					var store = grid.getStore();
					var vibility;
					if(checkbox.checked){
						vibility = "block";
					}else{
						vibility = "none";
					}
					
					var len = store.data.length;
					for(var i=0;i<len;i++){
						var data = store.getAt(i).data;
						var a0255 = data.a0255;//任职状态
						if(a0255=='0'){
							grid.getView().getRow(i).style.display=vibility;
						}
					}
				}
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					
					
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('该职务已经排在最顶上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index-1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('请选中需要排序的职务!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('该职务已经排在最底上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index+1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
					grid.view.refresh();
				}
				
				Ext.onReady(function(){
					
					var pgrid = Ext.getCmp("WorkUnitsGrid");
					
					
					var bbar = pgrid.getBottomToolbar();
					
					
					var dstore = pgrid.getStore();
					var firstload = true;
					dstore.on({  
				       load:{  
				           fn:function(){  
				             checkChange();
				             if(firstload){
				       		    $h.selectGridRow('WorkUnitsGrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				   
				   var ddrow = new Ext.dd.DropTarget(pgrid.container,{
						ddGroup : 'GridDD',
						copy : false,
						notifyDrop : function(dd,e,data){
							//选中了多少行
							var rows = data.selections;
							//拖动到第几行
							var index = dd.getDragData(e).rowIndex;
							if (typeof(index) == "undefined"){
								return;
							}
							//修改store
							for ( i=0; i<rows.length; i++){
								var rowData = rows[i];
								if (!this.copy) dstore.remove(rowData);
								dstore.insert(index, rowData);
							}
							pgrid.view.refresh();
							radow.doEvent('worksort');
						}
					});
				
				
				});
				
				function openSortWin(){
					var a0201b = document.getElementById("a0201b").value;
					if(a0201b==''){
						$h.alert('系统提示：','请先选择机构!');
						return;
					}
					parent.window.a0201b = a0201b;
					$h.openWin('A01SortGrid','pages.publicServantManage.PersonSort','集体内排序',500,480,document.getElementById('subWinIdBussessId').value,'<%=ctxPath%>',window);
				}
				
				Ext.onReady(function(){
					
					//Ext.getCmp('WorkUnitsGrid').setWidth(581);
					Ext.getCmp('WorkUnitsGrid').setHeight(250)
					Ext.getCmp('WorkUnitsGrid').setWidth(document.body.clientWidth*0.48); 
					Ext.getCmp('a0192a').setWidth(document.body.clientWidth*0.48-32); 
					Ext.getCmp('a0192').setWidth(document.body.clientWidth*0.48-32); 
					
					document.getElementById('a0201bSpanId').innerHTML='<font color="red">*</font>任职机构';		
					
					
					//是否在任的联动 
					a0255SelChange();
					$(".radioItem").change(function() {
						var a0255 = $("input[name='a0255']:checked").val();
						if("1"==a0255){//在任
							document.getElementById('yimian').style.visibility="hidden";
						}else if("0"==a0255){//以免
							document.getElementById('yimian').style.visibility="visible";
						}
						document.getElementById('a0255').value = a0255;
					})
					
					//设置任职机构选择框长度
					document.getElementById('a0201b_combo').style.width="190px";
				});
				</script>
				
				
				
<!--------------------------------------- 现职级 A051--------------------------------------------->

				<script type="text/javascript">
				function deleteRowRendererA051(value, params, record,rowIndex,colIndex,ds){
					var a0500 = record.data.a0500;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA051(&quot;"+a0500+"&quot;)\">删除</a>";
				}
				function deleteRowA051(a0500){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA051',a0500);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div4" style="display:'';">
				<div id="A051" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA051" applyTo="tolA051" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加"  id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA051" handler="saveTrain" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0500" title="主键id" ></odin:hidden>
					<div>
					<div id="tolA051"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="TrainingInfoGrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0500" />
								  		<odin:gridDataCol name="a0501b" />
								  		<odin:gridDataCol name="a0504"/>
								  		<odin:gridDataCol name="a0517" />
								   		<odin:gridDataCol name="a0524" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn2 header="主键" dataIndex="a0500" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="职级" dataIndex="a0501b" editor="select" edited="false" codeType="ZB148" width="100"/>
									  <odin:gridEditColumn header="批准日期" dataIndex="a0504" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="终止日期" dataIndex="a0517" editor="text" width="100" edited="false"/>
									  <odin:gridEditColumn2 header="状态" dataIndex="a0524" editor="select" edited="false" selectData="['1','在任'],['0','已免']" width="100"/>
									  <odin:gridEditColumn width="45" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA051" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0501b" label="职级" codeType="ZB148" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a0524" label="状态" codeType="ZB14" onchange="setA0517Disabled" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0504" isCheck="true" label="批准日期" maxlength="8" required="true"></odin:NewDateEditTag></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0517" isCheck="true" label="终止日期" maxlength="8"></odin:NewDateEditTag>	</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("TrainingInfoGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('TrainingInfoGrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('familyid');
						var gridobj = document.getElementById('forView_TrainingInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('TrainingInfoGrid').setHeight(400);
						 Ext.getCmp('TrainingInfoGrid').setWidth(document.body.clientWidth*0.64); 
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				
				
		function saveTrain(){
			var a0524 = document.getElementById("a0524").value;//状态
			var a0504 = document.getElementById("a0504").value;//批准日期
			var a0517 = document.getElementById('a0517').value;//结束日期
			var a0504_1 = document.getElementById("a0504_1").value;		//批准日期页面显示值
			var a0517_1 = document.getElementById("a0517_1").value;		//结束日期页面显示值 
			
			//批准日期、状态必填 （职务层次后台有校验）
			if(!a0524){
				$h.alert('系统提示','状态不能为空！', null,200);
				return false;
			}
			
			var now = new Date();
			var nowTime = now.toLocaleDateString();
			var year = nowTime.substring(0 , 4);//年
			var MonthIndex = nowTime.indexOf("月");
			var mon = nowTime.substring(5 , MonthIndex);//月
			var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
			if(mon.length == 1){
				mon = "0" + mon;
			}
			if(day.length == 1){
				day = "0" + day;
			}
			
			nowTime = year + mon + day;//获取八位数的时间字符串
			
			var time = a0504;
			if(time.length == 6){
				time = time + "01";
			}
			
			if(parseInt(time) > parseInt(nowTime)){
				odin.alert("批准日期不能晚于系统当前时间");
				return false;
			}
			
			var time2 = a0517;
			if(time2.length == 6){
				time2 = time2 + "01";
			}
			if(parseInt(time2) > parseInt(nowTime)){
				odin.alert("终止日期不能晚于系统当前时间");
				return false;
			}
			
			if(!a0504_1){
				$h.alert('系统提示','批准日期不能为空！', null,200);
				return false;
			}
			
			
			if("0"==a0524 && !a0517_1){
				$h.alert('系统提示','终止日期不能为空！', null,200);
				return false;
			}
			
			
			var text1 = dateValidate(a0504_1);
			var text2 = dateValidate(a0517_1);
			
			if(a0504_1.indexOf(".") > 0){
				text1 = dateValidate(a0504);
			}
			if(a0517_1.indexOf(".") > 0){
				text2 = dateValidate(a0517);
			}
			
			
			if(text1!==true){
				$h.alert('系统提示','批准日期' + text1, null,400);
				return false;
			}
			if(text2!==true){
				$h.alert('系统提示','终止日期' + text2, null,400);
				return false;
			}
			
			radow.doEvent('saveA11.onclick');
		}
			
		
		function setA0517Disabled(){
			var value = document.getElementById("a0524").value;
			if("0"==value || value==''){
				document.getElementById("a0517_1").readOnly=false;
				document.getElementById("a0517_1").disabled=false;
				document.getElementById("a0517_1").style.backgroundColor="#fff";
				document.getElementById("a0517_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
				
			}else if("1"==value){
				document.getElementById("a0517_1").readOnly=true;
				document.getElementById("a0517_1").disabled=true;
				document.getElementById("a0517_1").style.backgroundColor="#EBEBE4";
				document.getElementById("a0517_1").style.backgroundImage="none";
				document.getElementById("a0517").value="";
				document.getElementById("a0517_1").value="";
				
			}
		}
				

		</script>		
		
		
<!--------------------------------------- 现职务 A050--------------------------------------------->

				<script type="text/javascript">
				function deleteRowRendererA050(value, params, record,rowIndex,colIndex,ds){
					var a0500 = record.data.a0500;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA050(&quot;"+a0500+"&quot;)\">删除</a>";
				}
				function deleteRowA050(a0500){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA050',a0500);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div3" style="display:'';">
				
				
				<div id="A050" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA050" applyTo="tolA050" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加"  id="rankAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA050" handler="saveTrainA050"  icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0500Post" title="主键id" ></odin:hidden>
					<div>
					<div id="tolA050"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="rankGrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0500" />
								  		<odin:gridDataCol name="a0501b" />
								  		<odin:gridDataCol name="a0504"/>
								  		<odin:gridDataCol name="a0517" />
								   		<odin:gridDataCol name="a0524" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn2 header="主键" dataIndex="a0500" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="职务层次" align="center" dataIndex="a0501b" editor="select" edited="false" codeType="ZB09" width="100"/>
									  <odin:gridEditColumn header="批准日期" align="center" dataIndex="a0504" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="终止日期" align="center" dataIndex="a0517" editor="text" width="100" edited="false"/>
									  <odin:gridEditColumn2 header="状态" align="center" dataIndex="a0524" editor="select" edited="false" selectData="['1','在任'],['0','已免']" width="100"/>
									  <odin:gridEditColumn width="45" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA050" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><tags:PublicTextIconEdit property="a0501bPost" codetype="ZB09" width="160" label="职务层次" required="true"/></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a0524Post" label="状态" codeType="ZB14" onchange="setA0517DisabledA050" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0504Post" isCheck="true" label="批准日期" maxlength="8" required="true"></odin:NewDateEditTag></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0517Post" isCheck="true" label="终止日期" maxlength="8"></odin:NewDateEditTag>	</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("rankGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('rankGrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('rankGrid');
						var gridobj = document.getElementById('forView_rankGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('rankGrid').setHeight(400);
						 Ext.getCmp('rankGrid').setWidth(document.body.clientWidth*0.64); 
					}
					side_resize();  
					window.onresize=side_resize; 
				})
	
				
	function setA0517DisabledA050(){
		var value = document.getElementById("a0524Post").value;
		if("0"==value || value==''){
			document.getElementById("a0517Post_1").readOnly=false;
			document.getElementById("a0517Post_1").disabled=false;
			document.getElementById("a0517Post_1").style.backgroundColor="#fff";
			document.getElementById("a0517Post_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
			
		}else if("1"==value){
			document.getElementById("a0517Post_1").readOnly=true;
			document.getElementById("a0517Post_1").disabled=true;
			document.getElementById("a0517Post_1").style.backgroundColor="#EBEBE4";
			document.getElementById("a0517Post_1").style.backgroundImage="none";
			document.getElementById("a0517Post").value="";
			document.getElementById("a0517Post_1").value="";
		}
	}
	
	function saveTrainA050(){
		
		var a0524 = document.getElementById("a0524Post").value;//状态
		var a0504 = document.getElementById("a0504Post").value;//批准日期
		var a0517 = document.getElementById('a0517Post').value;//结束日期
		var a0504_1 = document.getElementById("a0504Post_1").value;		//批准日期页面显示值
		var a0517_1 = document.getElementById("a0517Post_1").value;		//结束日期页面显示值 
		
		
		//批准日期、状态必填 （职务层次后台有校验）
		if(!a0524){
			$h.alert('系统提示','状态不能为空！', null,200);
			return false;
		}
		
		var now = new Date();
		var nowTime = now.toLocaleDateString();
		var year = nowTime.substring(0 , 4);//年
		var MonthIndex = nowTime.indexOf("月");
		var mon = nowTime.substring(5 , MonthIndex);//月
		var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
		if(mon.length == 1){
			mon = "0" + mon;
		}
		if(day.length == 1){
			day = "0" + day;
		}
		
		nowTime = year + mon + day;//获取八位数的时间字符串
		
		var time = a0504;
		if(time.length == 6){
			time = time + "01";
		}
		if(parseInt(time) > parseInt(nowTime)){
			odin.alert("批准日期不能晚于系统当前时间");
			return false;
		}
		
		var time2 = a0517;
		if(time2.length == 6){
			time2 = time2 + "01";
		}
		if(parseInt(time2) > parseInt(nowTime)){
			odin.alert("终止日期不能晚于系统当前时间");
			return false;
		}
		
		if(!a0504_1){
			$h.alert('系统提示','批准日期不能为空！', null,200);
			return false;
		}
		
		
		if("0"==a0524 && !a0517_1){
			$h.alert('系统提示','终止日期不能为空！', null,200);
			return false;
		}
		
		
		var text1 = dateValidate(a0504_1);
		var text2 = dateValidate(a0517_1);
		
		if(a0504_1.indexOf(".") > 0){
			text1 = dateValidate(a0504);
		}
		if(a0517_1.indexOf(".") > 0){
			text2 = dateValidate(a0517);
		}
		
		
		if(text1!==true){
			$h.alert('系统提示','批准日期' + text1, null,400);
			return false;
		}
		if(text2!==true){
			$h.alert('系统提示','终止日期' + text2, null,400);
			return false;
		}
		
		radow.doEvent('saveA12.onclick');
	}
		
	</script>		
						

<!--------------------------------------- 专业技术职务信息集 --------------------------------------------->
				
				<style>
				<%=FontConfigPageModel.getFontConfig()%>
				#table{position:relative;top: -12px;left:5px;}
				#table2{position:relative;top: -20px; padding: 0px;margin: 0px;height:300}
				#tableA14{position:relative;top: -50px; padding: 0px;margin: 0px;height:300}
				.inline{
				display: inline;
				}
				.pl{
				margin-left: 8px;
				}
				</style>
				<script type="text/javascript">
				function setA0602Value(record,index){
					Ext.getCmp('a0602').setValue(record.data.value);
					Ext.getCmp('a0196').setValue(record.data.value);
				}
				function deleteRowRendererA06(value, params, record,rowIndex,colIndex,ds){
					var a0600 = record.data.a0600;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA06(&quot;"+a0600+"&quot;)\">删除</a>";
				}
				function deleteRowA06(a0600){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA06',a0600);
						}else{
							return;
						}		
					});	
				}
				
				</script>
				<div id="div5" style="display:'';">
				
				<div id="A06" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<odin:toolBar property="toolBar1" applyTo="tol1">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="professSkillAddBtn" icon="images/add.gif" ></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA06" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				</odin:toolBar>
				<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
				<div id="tol1"></div>
				<odin:hidden property="sortid" title="排序号"/>
				<table id="table" style="margin-top: 18px;">
					<tr>
						<td>
							<!-- <div id="divA06" style="width:330;margin-top: 20px;"> -->
							 <odin:editgrid property="professSkillgrid"  isFirstLoadData="false" forceNoScroll="true" url="/">
								<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								    <odin:gridDataCol name="a0699" />
									<odin:gridDataCol name="a0600" />
									<odin:gridDataCol name="a0601" />
									<odin:gridDataCol name="a0602" />
									<odin:gridDataCol name="a0604" />
									<odin:gridDataCol name="a0607" />
									<odin:gridDataCol name="a0611" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="输出" width="15" editor="checkbox" checkBoxClick="a06checkBoxColClick" dataIndex="a0699" edited="true"/>
									<odin:gridColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
									<odin:gridEditColumn2 header="专业技术资格代码" dataIndex="a0601" codeType="GB8561" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="专业技术资格" dataIndex="a0602" editor="text" />
									<odin:gridColumn header="获得资格日期" dataIndex="a0604" editor="text" />
									<odin:gridEditColumn2 header="获取资格途径" dataIndex="a0607" codeType="ZB24" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="评委会或考试名称" dataIndex="a0611" editor="text"  hidden="true"/>		
									 <odin:gridEditColumn header="操作" width="15" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA06" isLast="true"/>		
								</odin:gridColumnModel>
							 </odin:editgrid>
							<!-- </div> -->
							<div id="btngroupA06"> </div>
							<div style="margin-top: 8px;" id="btngroup6"> </div>
						</td>
						<td>
						  <div>
							<table id="table2">
								<tr>
									<odin:textEdit property="a0196" label="专业技术职务" readonly="true"></odin:textEdit>
								</tr>
								<tr>
									<td height="50px"></td>
								</tr>
								<tr>
									<tags:PublicTextIconEdit property="a0601" label="专业技术资格" onchange="setA0602Value" required="true" readonly="true" codetype="GB8561"></tags:PublicTextIconEdit>	
								</tr>
								<tr>
									<odin:textEdit property="a0602" label="专业技术资格名称" validator="a0602Length"></odin:textEdit>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0604" label="获得资格日期" maxlength="8" ></odin:NewDateEditTag>	
								</tr>
								<tr>
									<odin:select2 property="a0607" label="获取资格途径" codeType="ZB24"></odin:select2>		
								</tr>
								<tr>
									<odin:textEdit property="a0611" label="评委会或考试名称" validator="a0611Length"></odin:textEdit>
									<odin:hidden property="a0600" title="主键id" ></odin:hidden>		
								</tr>
							</table>
						  </div>
						</td>
					</tr>
					</table>
				<odin:hidden property="a0699" title="输出"/>
				</div>
				</div>
				
				</div>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("professSkillgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				       		 if(firstload){
				       		    $h.selectGridRow('professSkillgrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				
				});
				
				</script>
				<script type="text/javascript">
				Ext.onReady(function(){
					 $h.applyFontConfig($h.spFeildAll.a06);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a06);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('professSkillgrid');
						var gridobj = document.getElementById('forView_professSkillgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled); 
				
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					new Ext.Button({
						icon : 'images/icon/arrowup.gif',
						id:'UpBtnA06',
					    text:'上移',
					    cls :'inline',
					    renderTo:"btngroupA06",
					    handler:UpBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/arrowdown.gif',
						id:'DownBtnA06',
					    text:'下移',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:DownBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/save.gif',
						id:'saveSortBtnA06',
					    text:'保存排序',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:function(){
							radow.doEvent('worksortA06');
					    }
					});
					
					Ext.getCmp('professSkillgrid').setHeight(375);
				});
				
				function UpBtnA06(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					
					if (sm.length<=0){
						alert('请选中需要排序的专业技术资格!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('该专业技术资格已经排在最顶上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index-1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
					
					grid.getView().refresh();
				}
				
				
				function DownBtnA06(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('请选中需要排序的专业技术资格!')
						return;	
					}
					
					var selectdata = sm[0];  //选中行中的第一行
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('该专业技术资格已经排在最底上!')
						return;
					}
					
					store.remove(selectdata);  //移除
					store.insert(index+1, selectdata);  //插入到上一行前面
					
					grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
					grid.view.refresh();
				}
				
				function a06checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					
					radow.doEvent('updateA06',sr.data.a0600);
				}
				Ext.onReady(function(){
					
					Ext.getCmp('professSkillgrid').setWidth(500);
					Ext.getCmp('professSkillgrid').setWidth(document.body.clientWidth*0.64); 
					//Ext.getCmp('professSkillgrid').setHeight(250)
					
				});
				</script>	

<!--------------------------------------- 学历学位信息集 --------------------------------------------->

				
				<script type="text/javascript">
				
				function setA0801aValue(record,index){//学位
					Ext.getCmp('a0801a').setValue(record.data.value);
				}
				function setA0901aValue(record,index){//学历
					Ext.getCmp('a0901a').setValue(record.data.value);
				}
				function setA0824Value(record,index){//专业
					Ext.getCmp('a0824').setValue(record.data.value);
				}
				function onkeydownfn(id){
					if(id=='a0801b')
						Ext.getCmp('a0801a').setValue('');
					if(id=='a0901b')
						Ext.getCmp('a0901a').setValue('');
					if(id=='a0827')
						Ext.getCmp('a0824').setValue('');
				}
				odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				};
				//学历学位输出设置
				function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					
					
					if(gridName == "degreesgrid"){
						var sr = getGridSelected(gridName);
						
						if(!sr){
							return;
						}
						
						var msg='';
						if(sr.data.a0899==='true'||sr.data.a0899===true){
							msg = '取消该记录后,该学历学位将不能输出<br/>确定要取消输出该记录吗?';
						}else{
							msg = '选择该记录后，该学历学位将输出<br/>确定要选择输出该记录吗?';
						}
						$h.confirm('系统提示',msg,220,function(id){
							if("ok"==id){
								radow.doEvent('degreesgridchecked',sr.data.a0800);
							}else{
								
								return false;
							}
						});
					}
					
					
				}
				
				function deleteRowRendererA08(value, params, record,rowIndex,colIndex,ds){
					var a0800 = record.data.a0800;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA08(&quot;"+a0800+"&quot;)\">删除</a>";
				}
				function deleteRowA08(a0800){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA08',a0800);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div6" style="display:'';">
				
				<!-- <odin:groupBox title="学历学位信息集"> -->
				<div id="A08" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBar2" applyTo="tol2" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA08" icon="images/save.gif" handler="saveDegree" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0800" title="主键id" ></odin:hidden>
					<odin:hidden property="a0834" title="最高学历标志" />
					<odin:hidden property="a0835" title="最高学位标志" />
					<input type="reset" name="reset" id="resetbtn" style="display: none;" />
					<div>
					<div id="tol2"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0899"/>
							     		<odin:gridDataCol name="a0800" />
								  		<odin:gridDataCol name="a0837" />
								  		<odin:gridDataCol name="a0801b" />
								   		<odin:gridDataCol name="a0901b" />
								   		<odin:gridDataCol name="a0814" />
								   		<odin:gridDataCol name="a0827" />			   		
								   		<odin:gridDataCol name="a0811" />
								   		<odin:gridDataCol name="a0804" />
								   		<odin:gridDataCol name="a0807" />
								   		<odin:gridDataCol name="a0904" />
								   		<odin:gridDataCol name="a0801a" />
								   		<odin:gridDataCol name="a0901a" />
								   		<odin:gridDataCol name="a0824" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="输出" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
									  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="类别" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
									  <odin:gridEditColumn header="学历" dataIndex="a0801a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="学位" dataIndex="a0901a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="学校及院系" dataIndex="a0814" edited="false" editor="text"/>
									  <odin:gridEditColumn header="专业" dataIndex="a0824" edited="false" editor="text" />
									  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA08" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0837" label="教育类别" required="true" codeType="ZB123"></odin:select2></tr>
									<tr><tags:PublicTextIconEdit property="a0801b" label="学历代码" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0801a" label="学历名称" validator="a0801aLength"></odin:textEdit></tr>
									<tr><odin:numberEdit property="a0811" label="学制年限(年)" maxlength="3"></odin:numberEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0901b" label="学位代码" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0901a" label="学位名称" validator="a0901aLength"></odin:textEdit></tr>
									<tr> <odin:textEdit property="a0814" label="学校（单位）名称" validator="a0814Length"></odin:textEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0827" label="所学专业类别" onchange="setA0824Value" codetype="GB16835" readonly="true" /></tr>
									<tr><odin:textEdit property="a0824" label="所学专业名称" validator="a0824Length"></odin:textEdit></tr>
									<tr><odin:NewDateEditTag property="a0804" label="入学时间"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><odin:NewDateEditTag property="a0807" label="毕（肄）业时间" maxlength="8"></odin:NewDateEditTag></tr>
									<tr><odin:NewDateEditTag property="a0904" label="学位授予时间" maxlength="8"></odin:NewDateEditTag></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				<odin:hidden property="a0899" title="输出"/>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("degreesgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('degreesgrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('degreesgrid');
						var gridobj = document.getElementById('forView_degreesgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('degreesgrid').setHeight(400);
						 //Ext.getCmp('degreesgrid').setWidth(800); 
						 Ext.getCmp('degreesgrid').setWidth(document.body.clientWidth*0.64); 
						 //document.getElementById('toolBar2').style.width = 1096;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>

<!--------------------------------------- 奖惩信息集 --------------------------------------------->
				
				<script type="text/javascript">
				
				//奖惩信息追加
				function appendRewardPunish(){ 
					var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
					if(!sm.hasSelection()){
						alert("请选择一行数据！");
						return;
					}
					radow.doEvent('appendonclick',sm.lastActive+'');
				}
				function setA1404aValue(record,index){//奖惩名称
					Ext.getCmp('a1404a').setValue(record.data.value);
				}
				
				function deleteRowRendererA14(value, params, record,rowIndex,colIndex,ds){
					var a1400 = record.data.a1400;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA14(&quot;"+a1400+"&quot;)\">删除</a>";
				}
				function deleteRowA14(a1400){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA14',a1400);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<div id="div7" style="display:'';">
				
				<!-- <odin:groupBox title="奖惩信息集"> -->
				<div id="A14" style="margin-left:5%;width: 95%;border:1px solid #00F">
				
					<odin:toolBar property="toolBar5" applyTo="tol3">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="RewardPunishAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA14" isLast="true" handler="saveReward" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<div id="tol3"></div>
					<div id="wzms">
						<table>
							<tr>
								<odin:textarea property="a14z101" cols="80" rows="4" colspan="5" label="文字描述" validator="a14z101Length"></odin:textarea>
							</tr>
						</table>
					</div>
					<div id="table1">
						
						 <table>
						 	<tr>
						 		<td>
						 					<odin:grid property="RewardPunishGrid" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
								 height="200">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1400" />
								  		<odin:gridDataCol name="a1404b" />
								  		<odin:gridDataCol name="a1404a" />
								   		<odin:gridDataCol name="a1415" />
								   		<odin:gridDataCol name="a1414" />
								   		<odin:gridDataCol name="a1428" />			   		
								   		<odin:gridDataCol name="a1411a" />
								   		<odin:gridDataCol name="a1407" />
								   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text"/>
									  <odin:gridEditColumn2 header="奖惩名称代码" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
									  <odin:gridEditColumn  header="奖惩名称"  dataIndex="a1404a" edited="false" editor="text" />
									  <odin:gridEditColumn2 header="受奖惩时职务层次" dataIndex="a1415" edited="false" codeType="ZB09" editor="select" width="50"/>
									  <odin:gridEditColumn2 header="批准机关级别" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
									  <odin:gridEditColumn2 header="批准机关性质" dataIndex="a1428" edited="false" codeType="ZB128" editor="select" hidden="true"/>
									  <odin:gridEditColumn header="批准机关" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
									  <odin:gridEditColumn header="批准日期" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
									  <odin:gridEditColumn header="奖惩撤销日期" dataIndex="a1424" edited="false" editor="text" maxLength="8" hidden="true"/>
									   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA14" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
									</td>
									<td>
										<table id="tableA14">
											<tr id="btn" height="35px;">
												<td align="right"><odin:button text="追加当前条" handler="appendRewardPunish" property="append"></odin:button> </td>
												<td id="btn2" align="center"><odin:button text="全部替换" property="addAll"></odin:button> </td>
											</tr>
											<tr height="35px;">
											<tags:PublicTextIconEdit property="a1404b" label="奖惩名称代码" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
											</tr>
											<tr height="35px;"><odin:textEdit property="a1404a" label="奖惩名称" required="true"></odin:textEdit></tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:select2 property="a1414" label="批准机关级别"  codeType="ZB03"></odin:select2>	</tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1428" label="批准机关性质" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:textEdit property="a1411a" label="批准机关" required="true"></odin:textEdit></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1407" label="批准日期" maxlength="8" isCheck="true" required="true"></odin:NewDateEditTag></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1424" label="奖惩撤销日期" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
											<odin:hidden property="a1400" title="主键id" ></odin:hidden>
										</table>
									</td>
									</tr>
								</table>
					</div>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				</div>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("RewardPunishGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           	 	$h.selectGridRow('RewardPunishGrid',0);
				           	 	firstload = false;
				           	 }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a14);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a14);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('RewardPunishGrid');
						var gridobj = document.getElementById('forView_RewardPunishGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className="divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
					     Ext.getCmp('RewardPunishGrid').setHeight(350);
						 //Ext.getCmp('RewardPunishGrid').setWidth(750); 
						 Ext.getCmp('a14z101').setWidth(document.body.clientWidth*0.64-55); 
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth*0.9 + 10;	
						 Ext.getCmp('RewardPunishGrid').setWidth(document.body.clientWidth*0.64); 
						 
						 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>


<!--------------------------------------- 考核信息集 --------------------------------------------->

				
				<script type="text/javascript">
				
				
				function changedispaly(obj){
					var choose = Ext.getCmp('a0191').getValue();	
					if(choose){
						document.getElementById('choose').style.visibility='visible';
					}else{
						document.getElementById('choose').style.visibility='hidden';
					}
				}
				
				function yearChange(){
				    var now = new Date();
				    var year = now.getFullYear();
				    var yearList = document.getElementById("a1521");
				    for(var i=0;i<=50;i++){
				        year = year-1;
				        yearList.options[i] = new Option(year,year);
				    }
				}
				
				function deleteRowRendererA15(value, params, record,rowIndex,colIndex,ds){
					var a1500 = record.data.a1500;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA15(&quot;"+a1500+"&quot;)\">删除</a>";
				}
				function deleteRowA15(a1500){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA15',a1500);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<div id="div8" style="display:'';">
				
				<!-- <odin:groupBox title="考核信息集"> -->
				<div id="A15" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv2">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加" id="AssessmentInfoAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA15" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<div>
				<div id="btnToolBarDiv2" align="center" style=""></div>
				<odin:hidden property="a1500" title="主键id" ></odin:hidden>
				<div id="wzms">
					<table>
						<tr>
							<td><odin:textarea property="a15z101" cols="117" rows="4" colspan="8" label="文字描述" validator="a15z101Length"></odin:textarea></td>
							<td>
								<div id="choose" style="visibility: hidden;">
									<table><odin:numberEdit property="a1527" label="&nbsp;&nbsp;&nbsp;选择年度个数" size="4"></odin:numberEdit></table>
								</div>
							</td>
							<td>
								<span>&nbsp;&nbsp;&nbsp;</span>
							</td>
							<td id="td" style="margin-left: 10px;"><odin:checkbox property="a0191" label="与列表关联" onclick="changedispaly(this)"></odin:checkbox></td>
						</tr>
					</table>
				</div>
				<div id="grid">
					<table>
						<tr>
							<td>
								<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
							 height="200">
								<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="a1500" />
							  		<odin:gridDataCol name="a1521" />
							   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								  <odin:gridRowNumColumn />
								  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
								  <odin:gridEditColumn header="年度" dataIndex="a1521" edited="false" editor="text"/>
								  <odin:gridEditColumn2  header="考核结论类别"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"/>
								   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA15" isLast="true"/>
								</odin:gridColumnModel>
							</odin:grid>	
							</td>
							<td>
								<table>
									<tr height="50">
										<odin:select2 property="a1521" label="考核年度" required="true" maxlength="4" multiSelect="true" ></odin:select2>
									</tr>
									<tr height="50">
										<tags:PublicTextIconEdit property="a1517" label="考核结论类别" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				</div>
				</div>
				<!-- </odin:groupBox> -->
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a15);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('AssessmentInfoGrid');
						var gridobj = document.getElementById('forView_AssessmentInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className= "divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});	
				
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth*0.9 + 10;	
						 Ext.getCmp('AssessmentInfoGrid').setWidth(document.body.clientWidth*0.64); 
						 Ext.getCmp('a15z101').setWidth(document.body.clientWidth*0.64-57); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>


<!--------------------------------------- 家庭成员及社会关系 --------------------------------------------->

				<script type="text/javascript">
				
				
				
				/* odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				}; */
				
				function deleteRowRendererA36(value, params, record,rowIndex,colIndex,ds){
					var a3600 = record.data.a3600;
					if(parent.buttonDisabled){
						return "删除";
					}
					return "<a href=\"javascript:deleteRowA36(&quot;"+a3600+"&quot;)\">删除</a>";
				}
				function deleteRowA36(a3600){ 
					Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA36',a3600);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div9" style="display:'';">
				
				<!-- <odin:groupBox title="家庭成员及社会关系"> -->
				<div id="A36" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA36" applyTo="tolA36" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="增加"  id="familyAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="保存" id="saveA36" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a3600" title="主键id" ></odin:hidden>
					<div>
					<div id="tolA36"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="familyid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a3600" />
								  		<odin:gridDataCol name="a3604a" />
								  		<odin:gridDataCol name="a3601" />
								   		<odin:gridDataCol name="a3607" />
								   		<odin:gridDataCol name="a3627" />
								   		<odin:gridDataCol name="a3611" />
								   		<odin:gridDataCol name="a3684" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="id" dataIndex="a3600" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="称谓" dataIndex="a3604a" codeType="GB4761" edited="false" editor="select"/>
									  <odin:gridEditColumn header="姓名" dataIndex="a3601" edited="false" editor="text"/>
									  <odin:gridEditColumn header="出生年月" dataIndex="a3607" edited="false" editor="text"/>
									  <odin:gridEditColumn2 header="政治面貌" dataIndex="a3627" codeType="GB4762" edited="false" editor="select"/>
									  <odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" edited="false" editor="text" width="80"/>
									  <odin:gridEditColumn header="身份号码" dataIndex="a3684" edited="false" editor="text" width="70"/>
									  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA36" isLast="true" width="30"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a3604a" label="称谓" required="true" codeType="GB4761"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3601" label="姓名" required="true"></odin:textEdit></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3684" label="身份证号码" validator="$h.IDCard"></odin:textEdit></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a3607" label="出生年月"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a3627" label="政治面貌" codeType="GB4762" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3611" label="工作单位及职务" required="true"></odin:textEdit></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("familyid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('familyid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('familyid');
						var gridobj = document.getElementById('forView_familyid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
				});
				function objTop(obj){
				    var tt = obj.offsetTop;
				    var ll = obj.offsetLeft;
				    while(true){
				    	if(obj.offsetParent){
				    		obj = obj.offsetParent;
				    		tt+=obj.offsetTop;
				    		ll+=obj.offsetLeft;
				    	}else{
				    		return [tt,ll];
				    	}
					}
				    return tt;  
				}
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('familyid').setHeight(400);
						 Ext.getCmp('familyid').setWidth(document.body.clientWidth*0.64); 
						 //Ext.getCmp('familyid').setWidth(800); 
						 //document.getElementById('toolBarA36').style.width = 1096;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>


<!--------------------------------------- 备注信息集 --------------------------------------------->
<!-- <div id="div11" style="display:'';margin-top: 30px;"> -->
<div id="div13" style="display:'';margin-left:5%;width: 95%;border:1px solid #00F">

<%-----------------------------自定义人员信息项-------------------------------------------------------%>


	<div id="btnToolBarDivA71" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA71" applyTo="btnToolBarDivA71">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="保存" id="bc13" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>
	
	<%
Map<String, List<Object[]>> os_list = Info2PageModel.getInfoExt();
if(os_list!=null&&os_list.size()>0){
	for(String key : os_list.keySet()){
		List<Object[]> entitys = os_list.get(key);
		String[] kv = key.split("___");
		%>
		<odin:groupBox property="<%=kv[0] %>" title="<%=kv[1] %>">
			<table cellspacing="2" width="98%" align="center">
				<tr>
		<%
		
		Integer size = entitys.size(),index=0;;
		for(Object[] entity : entitys){
			String data_type = entity[5].toString();
			Object code_type = entity[3];
			if(index%3==0){
				%>
				</tr>
				<tr>
				<%
			}
			if("VARCHAR2".equals(data_type)){//文本
				if(code_type!=null&&!"".equals(code_type)){
					%>
					<tags:PublicTextIconEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" codetype="<%=code_type.toString() %>" readonly="true"></tags:PublicTextIconEdit>
					<%
				}else{
					%>
					<odin:textEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:textEdit>
					<%
				}
			}else if("NUMBER".equals(data_type)){//数字
				%>
				<odin:numberEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:numberEdit>
				<%
			}else if("DATE".equals(data_type)){//日期
				%>
				<odin:NewDateEditTag property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" isCheck="true" maxlength="8" ></odin:NewDateEditTag>
				<%
			}
			index++;
		}
		
		%>
				</tr>
			</table>
		</odin:groupBox>
		<%
		
	}
	
}

%>
	
	<table style="width:100%;margin-top: 20px;">
		<col width="4%">
		<col  width="46%">
		<col  width="50%">
		<tr>
			<odin:textarea property="a7101" label="备注" colspan='100' rows="8" validator='a7101Length'></odin:textarea>
			<!-- <td>
			<textarea name="a7101" id='a7101' style="width:650px;height:200px"></textarea>
			</td> -->
			<odin:hidden property="a7100" title="id(a7100" ></odin:hidden>
		</tr>
	</table>

</div>


<!--------------------------------------- 进入管理信息集 --------------------------------------------->
<div id="div10" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA29" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA29" applyTo="btnToolBarDivA29">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="保存" id="bc10" handler="saveEntry2" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>
	<table style="width:100%;height:175px;margin-top: 20px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
		 	<odin:NewDateEditTag property="a2907"  label="进入本单位日期" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			<tags:PublicTextIconEdit property="a2911" label="进入本单位变动类别" required="true" codetype="ZB77" readonly="true"></tags:PublicTextIconEdit>		
			<%-- <odin:textEdit property="a2941" label="在原单位职务"  validator="a2941Length"></odin:textEdit> --%>
			<tags:PublicTextIconEdit property="a2944s" label="在原单位职务层次" codetype="ZB09"></tags:PublicTextIconEdit>
		</tr>
		<tr>
			<odin:textEdit property="a2944" label="在原单位职务层次" validator="a2944Length"></odin:textEdit>
			<odin:textEdit property="a2921a" label="进入本单位前工作单位名称" validator="a2921aLength"></odin:textEdit>
			<odin:NewDateEditTag property="a2949" label="公务员登记时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
		</tr>
		<tr></tr>
	</table>
</div>

<script type="text/javascript">
//进入管理信息集
function saveEntry2(){
	//1、进入本单位日期：与出生日期进行比较，一般应大于18周岁。
	
	//不能晚于当前时间
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//年
	var MonthIndex = nowTime.indexOf("月");
	var mon = nowTime.substring(5 , MonthIndex);//月
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//获取八位数的时间字符串
	
	var time = document.getElementById("a2907").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("进入本单位日期不能晚于系统当前时间");
		return;
	}
	
	var time2 = document.getElementById("a2949").value;;
	if(time2.length == 6){
		time2 = time2 + "01";
	}
	if(parseInt(time2) > parseInt(nowTime)){
		odin.alert("公务员登记时间不能晚于系统当前时间");
		return;
	}
	
	//2、进入本单位变动类别：必须填写。
	var a2911_combo = document.getElementById("a2911_combo").value;
	if(!a2911_combo){
		odin.alert("进入本单位变动类别不可为空！");
		return;
	}
	radow.doEvent("bc10");
}
</script>

<!--------------------------------------- 退出管理信息集 --------------------------------------------->
<div id="div11" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA30" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA30" applyTo="btnToolBarDivA30">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="保存" id="bc11" handler="saveLogout2" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>

	
	<table style="width:100%;height:175px;margin-top: 20px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<tags:PublicTextIconEdit property="a3001" label="退出管理方式" required="true" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>	
			<tags:PublicTextIconEdit  property="a3007a" label="调往单位" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit>
			<odin:select2 property="orgid" label="退出单位"></odin:select2>
		</tr>
		<tr>
			<odin:NewDateEditTag property="a3004" label="退出管理时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a3034" label="备注" validator="a3034Length"></odin:textEdit>
		</tr>
		<tr></tr>
	</table>
	
<!-- <table style="width:100%;margin-top: 20px;" >
	<col width="84%">
	<col width="16%">
	<tr>
		<td align="right">
			<odin:button property="bc9" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
		</td>
		<td align="left">
			<odin:button text="保存并下一步" property="bcn9"></odin:button>
		</td>
	</tr>
</table> -->
</div>

<script type="text/javascript">
function a3001change(rs){
	if(rs.data.key.substring(0,1) != "1" && rs.data.key.substring(0,1) != "2"){
		odin.ext.getCmp('a3007a_combo').disable();
		Ext.query("#a3007a_combo+img")[0].onclick=null;
		document.getElementById('a3007a_combo').value='';
		odin.ext.getCmp('orgid_combo').enable();
		//Ext.query('#orgid_combo+img')[0].onclick=openDiseaseInfoCommonQueryorgid;
		return;
	}

	odin.ext.getCmp('a3007a_combo').enable();
	Ext.query("#a3007a_combo+img")[0].onclick=openDiseaseInfoCommonQuerya3007a;
	odin.ext.getCmp('orgid_combo').disable();
	//Ext.query('#orgid_combo+img')[0].onclick=null;
	document.getElementById('orgid').value='';
	document.getElementById('orgid_combo').value='';

	
}

function returnwinabc(rs){
	if(rs!=null){
		var rss = rs.split(",");
		document.getElementById('orgid').value=rss[0];
	}
}

//退出信息集
function saveLogout2(){
	//1、退出管理方式：必须填写。
	var a3001_combo = document.getElementById("a3001_combo").value;
	if(!a3001_combo){
		odin.alert("退出管理方式不可为空！");
		return;
	}
	//2、退出本单位日期：应晚于参加工作时间。
	
	//不能晚于当前时间
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//年
	var MonthIndex = nowTime.indexOf("月");
	var mon = nowTime.substring(5 , MonthIndex);//月
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//获取八位数的时间字符串
	
	var time = document.getElementById("a3004").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("退出管理时间不能晚于系统当前时间");
		return;
	}
	
	radow.doEvent("bc11");

}
</script>

<!--------------------------------------- 拟任免信息集 --------------------------------------------->
<div id="div12" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA53" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA53" applyTo="btnToolBarDivA53">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="保存" id="bc12" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>

	<table style="width:100%;height:175px;margin-top: 16px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟任职务" validator="a5304Length"></odin:textEdit>
			<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟免职务" validator="a5315Length"></odin:textEdit>
			<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任免理由" validator="a5317Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
			<odin:hidden property="a5399" title="id(a5399" ></odin:hidden>
			<odin:NewDateEditTag property="a5321" label="计算年龄时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:NewDateEditTag property="a5323" label="填表时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a5327" label="填表人" validator="a5327Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a5319" label="呈报单位" validator="a5319Length"></odin:textEdit>
		</tr>
		<tr></tr>
	</table>
</div>


<script type="text/javascript">
function djb(b){
	
	radow.doEvent("dj.onclick",b);
	dj(b);
}	
function dj(b){
	$(function(){
		stepBar.init("stepBar", {
			step : b,
			change : false,
			animation : true
		});
	});
}

function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };
//验证身份证号并获取出生日期
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	
	iIdNo = trim(iIdNo);
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		
		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		return tmpStr;
	}
}

//入党时间设置弹窗 
function a0140Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeInfoAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath);
}

//籍贯设置，赋值comboxArea_a0111
function a0111Change(){
	var a0111_combo = document.getElementById("a0111_combo").value;
	document.getElementById("comboxArea_a0111").value = document.getElementById("a0111_combo").value;
}

//出生地设置，赋值comboxArea_a0114
function a0114Change(){
	document.getElementById("comboxArea_a0114").value = document.getElementById("a0114_combo").value;
}




//照片点击触发事件 
function showdialog(){
	var isUpdate = document.getElementById('isUpdate').value;
	if(isUpdate == 2){
		return;
	}
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src;
}

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

newWin({id:'nameCheck',title:'重名检测',modal:true,width:500,height:420,maximizable:true});
newWin({id:'fontConfig',title:'字体设置',modal:true,width:380,height:230,maximizable:true});
newWin({id:'picupload',title:'头像上传',modal:true,width:900,height:490,src:'<%=ctxPath%>/picCut/picwin.jsp'});



var newwin = new Ext.Window({
	contentEl: "jlcontent",
	title : '简历范例',
	layout : 'fit',
	width : 525,
	overflow : 'hidden',
	height : 343,
	closeAction : 'hide',
	closable : true,
	minimizable : false,
	maximizable : false,
	modal : false,
	maximized:false,
	id : 'jlfl',
	bodyStyle : 'background-color:#FFFFFF; overflow-x:hidden; overflow-y:scroll',
	plain : true,
	listeners:{}
});

//简历生成模式
function selectall(){
	var contenttext = document.getElementById("contenttext").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}
//简历生成模式
function selectall2(){
	var contenttext = document.getElementById("contenttext2").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}


function showwin(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		document.getElementById("jlcontent").style.display="block";
 		var jlfl = Ext.getCmp("jlfl");
 		Ext.getCmp("jltab").activate("tab2");
 		if(!jlfl.rendered){  
 			jlfl.show();//alert("no reader")  
 		}else if(jlfl.hidden){  
 			jlfl.show();//alert("hidden")  
 		}else{  
 			jlfl.hide();//alert("show")  
 		}
}

function a7101Length(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}


//查看权限变量,没有修改权限，信息集录入所有的保存按钮不可以 
function buttonDisabled(){
	Ext.getCmp("InfoSaveA01").setDisabled(true);
	
}

function a0221Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0221Click2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',711,361,document.getElementById('a0000').value,ctxPath);
}

function a0192eClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0192eClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',711,361,document.getElementById('a0000').value,ctxPath);
}

function changea0201d(type){
	if(type == '1'){
		document.getElementById('a0201dSpanId').innerHTML='<font color="red">*</font>是否班子成员';
	}
	if(type == '2'){
		document.getElementById('a0201dSpanId').innerHTML='是否班子成员';
		document.getElementById('a0201eSpanId').innerHTML='成员类别';
	}
}

//简历上面提示信息
Ext.onReady(function(){
	var jltab__tab1 = document.getElementById('jltab__tab1');
	var mubiao = jltab__tab1.parentNode;
	
	$("#"+mubiao.id).after("<div style='POSITION: absolute; LEFT: 320px; TOP: 5px;font-size: 12px;color:red;'>依据工作单位及职务自动生成</div>");  
	 
});	

//填写身份证后，性别和出生日期联动显示，身份证分15位和18位处理 
function card(){
	var IDCard = document.getElementById("a0184").value;
	
	if(IDCard.length == 15 || IDCard.length == 18){
		var a0104 = 2;			//性别：1男，2女 
		var a0104_combo = "女";
		var a0107 = 200001;		//出生年月
		
		if(IDCard.length == 18){			//18位身份证联动处理 
			//性别
			a0104 -= IDCard.substring(16,17)%2;
			//出生年月 
			a0107 = IDCard.substring(6,14);
		}
		
		if(IDCard.length == 15){			//15位身份证联动处理 
			//性别
			a0104 -= IDCard.substring(14,15)%2;
			//出生年月 
			a0107 = "19" + IDCard.substring(6,12);
		}
		
		
		if(a0104 == 1){
			a0104_combo = "男";
		}
		//赋值 
		document.getElementById("a0107").value = a0107;
		document.getElementById("a0107_1").value = a0107;
		document.getElementById("a0104").value = a0104;
		document.getElementById("a0104_combo").value = a0104_combo;
		
		
	}
	
	
}



</script>


<!------------------------------------------------------- 电子档案  ----------------------------------------------------->
<script type="text/javascript">

var tree;
var root;




function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.getRootNode().reload();
    tree.expandAll();
}
function reloadThisGroup() {
	document.getElementById('groupname').innerHTML=document.getElementById('optionGroup').value;
}



function XXXGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:downloadFolderFile('"+rs.get('id')+"','"+rs.get('catalog')+"')\">下载</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:deleteFolderFile('"+rs.get('id')+"','"+rs.get('catalog')+"')\">删除</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	
}



</script>

<% 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>

			<odin:hidden property="treeId"/>
			<odin:hidden property="ereaname" value="<%=ereaname%>" />
			<odin:hidden property="ereaid" value="<%=ereaid%>" />
<div id="div14" style="display:'';margin-left:5%;width: 95%;border:1px solid #00F">

	<table>
		<tr>
			<td style="width: 15%"><div id="tree-div" style="overflow:auto; height:100%; width: 175px;float: left;border: 2px solid #c3daf9;display: inline;">

				</div>
			</td>
			<td style="width: 85%">
							<div style="float: left;display: inline;padding-left: 15px" id="left;">
						
							<odin:groupBox title="档案文件导入" property="daoru">
								<table>
								<tr>
									<td>
									<odin:textEdit width="600" inputType="file" colspan="3"  property="excelFile" label="选择文件:"></odin:textEdit> 
									</td>
									<td align="center" valign="middle">
									<%-- <odin:button text="上传" property="impwBtn" handler="formupload" />  --%>
									<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn">
									</td>
								</tr>
								</table>	
							</odin:groupBox>
							
					
						<odin:editgrid property="folderGrid" autoFill="false" pageSize="20" isFirstLoadData="false" url="/">
							<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="id"/>
								<odin:gridDataCol name="catalog"/>
								<odin:gridDataCol name="name"/>
								<odin:gridDataCol name="filesize"/>
								<odin:gridDataCol name="uploads" />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="time" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridEditColumn2 dataIndex="id" width="90" hidden="true" header="文件id" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="catalog" width="220" header="目录名称" align="center" editor="text" edited="false" hidden="true"/>
								<odin:gridEditColumn2 dataIndex="name" width="240" header="文件名称" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="filesize" width="140" header="文件大小" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="uploads" width="140" header="上传人" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="time" width="160" header="更新时间" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn dataIndex="op4" header="操作" width="200" renderer="XXXGrantRender" align="center" isLast="true" editor="text" edited="false"/> 
							</odin:gridColumnModel>
						</odin:editgrid> 
	</div>
			</td>
		</tr>
	</table>													
	
</div>
<odin:window src="" modal="true" id="AddFolderTree" width="380" height="260" title="新建文件夹" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="UpdateFolderTree" width="380" height="260" title="修改文件夹" closable="true" maximizable="false"></odin:window>
<script type="text/javascript">
var win_addwin;
var win_addwinnew;

Ext.onReady(function(){
	/* var side_resize=function(){
		 Ext.getCmp('folderGrid').setHeight(380);
	}
	side_resize();  
	window.onresize=side_resize;  */
	Ext.getCmp('folderGrid').setHeight(380);
})


//这段初始化js需要放在页面最后面 
Ext.onReady(function(){
	
	//初始化id
	var a0000id = '<%=a0000==null?"":a0000%>';
	
	document.getElementById("a0000").value = a0000id;
	
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
	
	
	var a0000 = thisTab.initialConfig.personid;
	var tabType = thisTab.initialConfig.tabType;
	
	
 	if(a0000.indexOf("addTab")!=-1){
		radow.doEvent('tabClick',a0000);
	}else{
		radow.doEvent('tabClick',a0000);
	} 
	
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	
    applyFontConfig(spFeild);
    applyFontConfig($h.spFeildAll.a36);
    
});



Ext.onReady(function() {
	var a0000 = document.getElementById('a0000').value;
	var a0000New = a0000.replace(/update/, "")
	
	Ext.QuickTips.init();
	
     //var id = document.getElementById('ereaid').value;
	  
     var Tree = Ext.tree;
     tree = new Tree.TreePanel( {
	  id:'group',
      el : 'tree-div',//目标div容器
      split:false,
      collapseMode : 'mini',
      monitorResize :true,   
      height:460,
      width: 270,
      minSize: 164,
      maxSize: 164,
      rootVisible: true,//是否显示最上级节点
      autoScroll : true,
      animate : true,
      border:false,
      enableDD : false,
      containerScroll : true,
      
      tbar:[
		      '->', 
		        {
		        	text : '新增', 
		        	icon : 'images/add.gif', 
		        	handler : function(){
		        		radow.doEvent('openNewWindow');
		        	}
		        }, '-', 
		        {
		        	text : '修改', 
		        	icon : 'images/i_2.gif', 
		        	handler : function(){
		        		radow.doEvent("update");
		        	}
		        }, '-', 
		        {
		        	text : '删除', 
		        	icon : 'images/icon/delete.gif', 
		        	handler : function(){
		        		
		        		radow.doEvent("delete");
		        	}
		        }
		      ],
      loader : new Tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info2&eventNames=folderTree&a0000New='+a0000New
      })
  });
     
    
	root = new Tree.AsyncTreeNode({
		text : "档案文件夹",
		id : "001.001",					//默认的node值：?node=001.001
		href : "javascript:radow.doEvent('folderGriddb','001.001')"
	});
	tree.setRootNode(root);
	tree.render();
	root.expand(true);
	
}); 


function formupload(){
	var treeId = document.getElementById('treeId').value;
	
	if(treeId == null || treeId == ""){
		odin.info('请选择目标文件夹 ！');
		return;
	}
	
	if(treeId == '001.001'){
		odin.info('档案文件夹，不可上传文件 ！');
		return;
	}
	
	
	var from = document.getElementsByName("commForm")
	//alert(from.name);
	
	for(var i=0;i<from.length;i++)
	{
		from[i].id="commForm";
	}
	
	if(document.getElementById('excelFile').value!=""){
		
		var a0000 = document.getElementById('subWinIdBussessId').value;
		
		odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/UploadFileServlet?method=uploadFolder&a0000='+a0000+'&treeId='+treeId,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'commForm',
			success:function(){
				radow.doEvent('folderGrid.dogridquery');
				odin.alert("文件上传成功!");
				Ext.getBody().unmask();//去除MASK   
			}
		});
	}else{
		odin.info('请选择文件之后再做导入处理！');
	}
}

//删除电子档案文件
//encodeURI，用来做url转码，解决中文传输乱码问题 
function deleteFolderFile(id, catalog){
	
	$.ajax({
		url:'<%=request.getContextPath()%>/UploadFileServlet?method=deleteFolderFile',
		type:"GET",
		data:{
			"id":id,
			"filePath":encodeURI(catalog)
		},
		success:function(){
			radow.doEvent('folderGrid.dogridquery');
			odin.alert("文件删除成功!");		
		},
		error:function(){
			odin.alert("文件删除失败!");		
		}
	});
	
}


//下载电子档案文件
//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
function downloadFolderFile(id, catalog){
	
	window.location="UploadFileServlet?method=downloadFolderFile&filePath="+encodeURI(encodeURI(catalog));
	
}


</script>




