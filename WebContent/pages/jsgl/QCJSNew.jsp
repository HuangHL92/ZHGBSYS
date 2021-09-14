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
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>


<style>
.rmbword{
    font-family: 宋体;
    /* position: absolute; */
    left:0; right:0;  bottom:0;
    margin:auto;
    top:20px;
    width: 100%;
    height: 635px;
    overflow-y: scroll; 
}
td{
	font-family: 仿宋_GB2312;
}

.tdlabel{
   text-align: center; 
   font-family: 方正黑体简体 !important;    
}
.title-font{
	font-family: 方正小标宋简体;
	font-size: 30px;
}

.tableJBXX{ 
	font-size: 18px;  
	border-left: 1px solid black;
	border-top: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 1px solid black;
	width: 830px;
	margin-bottom: 80px;
	/* table-layout: fixed; */
}
.tableJBXX tr td{ 
	border-left: 1px solid black;
	border-top: 1px solid black;
}
.width1{ width: 60px; }.width2{ width: 100px; }.width3{ width: 570px; }
.width4{ width: 470px; }.width5{ width: 150px; }
.width6{ width: 200px; }.width7{ width: 580px; }


.height1{height: 30px;}.height2{height: 55px;}.height3{height: 55px;}.height4{height: 55px;}
.height5{height: 105px;}.height6{height: 55px;}.height7{height: 320px;}.height8{height: 50px;}
.height9{height: 270px;}

.width21{ width: 110px; }.width22{ width: 380px; }.width23{ width: 360px; }
.width24{width: 10%;}.width25{width: 8%;}.width26{width: 10%;}.width27{width:8%;}
.width29{width: 10%;}.width210{width: 10%;}.width211{width: 10%;}.width212{width: 10%;}

.height21{height: 210px;}.height22{height: 80px;}.height23{height: 65px;}.height24{height: 65px;}
.height25{height: 85px;}

.trfont td{
	font-family:方正仿宋简体!important;
	text-align: center; 
}

.input-editor{
	position: relative;
	padding: 6px 8px;
}
.input-editor2{
	position: relative;
}
.widthInput{
	width: 580px;
}
.GRYP15,.GRYP17{
	zoom:180%;
}
.center{
	text-align: center; 
}
.left{
	text-align: left; 
}

.uploadify-queue-item{
width: 145px;
height: 30px; 
margin-top: 1px;
margin-right: 5px;
margin-bottom: 2px;
font: 11px Verdana, Geneva, sans-serif;
}
.uploadify-progress{
top: -2px;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.top_btn_style{
border-radius:5px;cursor:pointer;margin-left:7px;height:30px;line-height:30px;font-size:14px;margin-top:10px;color:#fff;background-color:#3680C9;text-align: center;
}
</style>
<script type="text/javascript">
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}
</script>

<div id="rmbButton" style="width: 278px;position: absolute;z-index: 8000; top: 580px;left: 846px;">
	<div style="width:50%;height:70px;float:right; ">
		<div class="top_btn_style" style="background-color:#F08000;margin-left:0px;width:70px;" onclick="expJISHIForm();" >导出纪实表</div> 
	</div>
	<div style="width:50%;height:70px;float:right; ">
		<div class="top_btn_style" style="background-color:#F08000;margin-left:0px;width:70px;" onclick="save();" >保&nbsp;&nbsp;存</div> 
	</div>
</div>
<%-- <odin:toolBar property="bbar" applyTo="bbardiv">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="保存" icon="images/icon/save.gif" handler="save" isLast="true" id="save"/>
</odin:toolBar> --%>

<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
	<!-- <tr>
		<td colspan="2"><div id="bbardiv"></div></td>
	</tr> -->
	<tr>
		<td style="width: 300px;">
			<table style="width: 100%;">
				<tr>
					<odin:select2 property="yn_type1" label="选择类型" value="TPHJ1" width="240" onchange="typechange"
						data="['TPHJ1','酝酿'],['TPHJ2','市委书记专题会议成员酝酿'],['TPHJ3','部务会议'],['TPHJ4','市委书记专题会议'],['TPHJ5','市委常委会']"></odin:select2>
				</tr>
				<tr>
					<td colspan="2">
						<odin:editgrid2 property="gridcq" isFirstLoadData="false" rowDbClick="reShowMsg" width="350"  pageSize="9999" 
							clicksToEdit="false" autoFill="false" >
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="tp0100" />
								<odin:gridDataCol name="tp0101" />
								<odin:gridDataCol name="tp0102" />
								<odin:gridDataCol name="tp0106" />
								<odin:gridDataCol name="tp0116" />
								<odin:gridDataCol name="tp0100" />
								<odin:gridDataCol name="a0000" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn2 dataIndex="tp0100" width="50" hidden="true" editor="text" header="主键" edited="false" align="center"/>
								<odin:gridEditColumn2 dataIndex="tp0101" width="100" header="姓名" editor="text" edited="false" align="left"/>
								<odin:gridEditColumn2 dataIndex="tp0106" width="150" header="现任职务" editor="text" edited="false" align="left"/>
								<odin:gridEditColumn2 dataIndex="a0000" width="50" hidden="true" editor="text" header="人员主键" edited="false" isLast="true" align="center"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
								{
							        data:[]
							    }
							</odin:gridJsonData>
						</odin:editgrid2>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<div class="rmbword" id="rmbword">
				<div style='text-align:center;'>
				<div style="height: 20px;"></div>
				<span class="title-font">干部选拔任用工作纪实表</span>
				<table cellpadding="0" cellspacing="0" 
			     	style="font-size: 18px; border: 0px solid black;width: 100%;margin-bottom: 10px;margin-top: 10px;">
					<tr>
						<td style="text-align: center;font-family:方正仿宋简体!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="rc"/>
							<label for="hao">日常选拔</label>
						</td>
						<td style="text-align: center;font-family:方正仿宋简体!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="hj"/>
							<label for="jiaohao">换届选拔</label>
						</td>
						<td style="text-align: center;font-family:方正仿宋简体!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="jzx"/>
							<label for="zhongdeng">竞争性选拔</label>
						</td>
			       </tr>
				</table>
			    <table  cellpadding="0" cellspacing="0" class="tableJBXX" 
			     	style="border-bottom: 0px; margin-bottom: 0px;" >
			       <tr>
			         <td class="tdlabel width1 height1 center">姓&nbsp;名</td>
			         <td class="width2 center" id="tp0101">&nbsp;</td>
			         <td class="tdlabel width2">现任职务</td>
			         <td class="width3 left" colspan="3" id="tp0106">&nbsp;</td>
			       </tr>
			       <tr>
			        <td class="tdlabel ">拟&nbsp;任<br>职&nbsp;务</td>
			         <td class="left" id="tp0107" colspan="5">&nbsp;</td>
			       </tr>
			     </table>
			     
			     <table  cellpadding="0" cellspacing="0" 
			     	style="border-bottom: 0px;border-top: 0px;margin-bottom: 0px;"
			      		class="tableJBXX" >
			      	<tr>
			         <td class="tdlabel" width="60px"></td>
			         <td class="tdlabel" width="200px"></td>
			         <td class="tdlabel" width="270px"></td>
			         <td class="tdlabel" width="150px"></td>
			         <td class="tdlabel" width="150px"></td>
			       	</tr>
			     	<tr>
			         <td class="tdlabel height1" colspan="5">履行选任程序情况</td>
			       </tr>
			       <tr>
			         <td class="width1 height1 tdlabel center">动议</td>
			         <td class="width4 tdlabel" colspan="2">酝酿形成的工作方案</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj01" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width1 height2 tdlabel">民&nbsp;主<br>推&nbsp;荐</td>
			         <td class="width4 tdlabel" colspan="2">会议推荐和个别谈话推荐汇总表</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
						<tags:JUploadGZJS property="sj02" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
						</td>
			       </tr>
			       <tr>
			         <td class="width1 tdlabel" rowspan="7">考<br>察</td>
			         <td class="width6 tdlabel">确定考察对象的<br>时间和方式</td>
			         <td class="input-editor widthInput" id="gzjs001" colspan="3">&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">拟提任人选征求纪检监察机关意见情况</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj03" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">考察预告</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj04" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">民主测评汇总表、征求意见汇总表</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj05" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">个人有关事项报告抽查核实情况</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj06" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">群众反映的主要问题及调查核实情况</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj07" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">干部任免审批表、干部考察情况</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj08" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr><!--  -->
			     </table>
			     <table  cellpadding="0" cellspacing="0" 
			     	style="border-bottom: 0px;border-top: 0px;margin-bottom: 0px;"
			      		class="tableJBXX" >
			     	<tr>
			         <td class="tdlabel " style="width: 60px;"></td>
			         <td style="width: 50px;"></td>
			         <td style="width: 150px;"></td>
			         <td style="width: 60px;"></td>
			         <td style="width: 75px;"></td>
			         <td style="width: 75px;"></td>
			         <td style="width: 60px;"></td>
			         <td style="width: 75px;"></td><td style="width: 75px;"></td><td style="width: 75px;"></td>
			         <td style="width: 75px;"></td>
			       	</tr>
			     	<tr>
			         <td class="tdlabel " rowspan="7" >讨<br>论<br>决<br>定</td>
			         <td class="tdlabel height2" colspan="3">征求分管领导意见的<br>时间和对象</td>
			         <td class="input-editor " id="gzjs002" colspan="7">&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2" align="left" style="text-align: center; " colspan="6">党委（党组）对本单位拟提任人选的意见建议<br>及廉政鉴定情况</td>
			         <td class="tdlabel " colspan="2">有无材料</td>
			         <td class="width5 left " colspan="2">
			         	<tags:JUploadGZJS property="sj09" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			       	 <td class="tdlabel " rowspan="4">会<br>议<br>决<br>定</td>
			         <td class="tdlabel " align="left" rowspan="2">&nbsp;&nbsp;会议情况<br><br>会议名称</td>
			         <td class="tdlabel " colspan="2" rowspan="2">会议<br>时间</td>
			         <td class="tdlabel " rowspan="2">主持人</td>
			         <td class="tdlabel " rowspan="2">应到<br>人数</td>
			         <td class="tdlabel " rowspan="2">实到<br>人数</td>
			         <td class="tdlabel height1" colspan="3">表决结果</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2" >同意</td>
			         <td class="tdlabel height2" >不同意</td>
			         <td class="tdlabel height2" >弃权</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height1">部务会议</td>
			         <td class="input-editor center" id="gzjs003" colspan="2" style="word-wrap: break-word;">&nbsp;</td>
			         <td class="input-editor center" id="gzjs004" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs005" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs006" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs007" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs008" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs009" >&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2">党委（党组）<br>会议</td>
			         <td class="input-editor center" id="gzjs010" colspan="2">&nbsp;</td>
			         <td class="input-editor center" id="gzjs011" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs012" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs013" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs014" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs015" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs016" >&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height1" colspan="6">双重管理干部提拔任用协管方回复意见</td>
			         <td class="tdlabel " colspan="2">有无材料</td>
			         <td class="width5 left " colspan="2">
			         	<tags:JUploadGZJS property="sj10" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			      </table>
			      <table  cellpadding="0" cellspacing="0" 
			     	style="border-bottom: 2px solid black;border-top: 0px;"
			      		class="tableJBXX" >
			      	<tr>
			         <td class="tdlabel" width="60px"></td>
			         <td class="tdlabel" width="200px"></td>
			         <td class="tdlabel" width="270px"></td>
			         <td class="tdlabel" width="150px"></td>
			         <td class="tdlabel" width="150px"></td>
			       	</tr>
			       <tr>
			         <td class="width1 tdlabel center" rowspan="3">任职</td>
			         <td class="width4 height1 tdlabel" colspan="2">任职前公示公告</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj11" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="height1 width4 tdlabel" colspan="2">公示期间群众反映的问题及意见调查核实情况</td>
			         <td class="tdlabel width5" >有无材料</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj12" label="上传" fileTypeDesc="所有文件" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width6 tdlabel " style="height: 70px;">任职谈话<br>的时间和<br>谈话人</td>
			         <td class="input-editor widthInput" id="gzjs017" colspan="3">&nbsp;</td>
			       </tr>
			      </table>
			    </div>
			  </div>
			
	  </td>
	</tr>
	
</table>
<odin:hidden property="ynid"/>
<odin:hidden property="a0000"/>
<odin:hidden property="tp0100"/>
<odin:hidden property="tp0116"/>
<odin:hidden property="gzjsid"/>
<odin:hidden property="UPDATE_FIELD"/>
<odin:hidden property="DATA"/>
<odin:hidden property="expdataarray"/>
<odin:hidden property="docpath"/>
<odin:hidden property="flag"/>
<div style="display: none;">
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<iframe src="" id='downloadframe' ></iframe>
</div>
<script type="text/javascript">
var GRYP = (function(){
	return {
		getValue:function(id){
			var text = GRYP["DATA"][id];
			return text==null?"":text;
		},
		setValue:function(td,text,id){
			this.SetTDtext(td,text);
			GRYP["DATA"][id] = text;
			GRYP["UPDATE_FIELD"][id]="1";
		},
		SetTDtext:function(td,v){
			$(td).html((v==""||v==null||v=="null")?"&nbsp;":v.replace(/\n/g,"<br/>"));
		},
		setGRYPckValue:function(ckbx){
			var text = ckbx.id;
			var col = ckbx.name;
			$("."+col+"[id!='"+text+"']").attr("checked", false);
			if(ckbx.checked){
				GRYP["DATA"][col]=text;
			}else{
				GRYP["DATA"][col]="";
			}
			GRYP["UPDATE_FIELD"][col]="2";
		}
	}
})();
GRYP["DATA"]={};
GRYP["UPDATE_FIELD"]={};
$(function(){
	$(".input-editor,.input-editor2").bind('click', function (event) {
		var td = $(this);
		var tagName = td.get(0).tagName;;
		var id = td.attr("id");
		if (td.children("textarea").length > 0) {
            return false;
        }
		var text = GRYP.getValue(id);
		//加上padding乘以2
		var width = td.width() +6*2;
        var height = td.height()+8;
        
        if(tagName=="SPAN"){
        	width = td.width();
            height = td.height();
        }
      //建立文本框，也就是input的节点   
        var div = $('<div style="position:absolute;top:0px;left:0px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //将文本内容加入td   
        td.append(div);
        
        //设置文本框值，即保存的文本内容   
        input.attr('value', text);
        input.css('border', "0px");
        input.css('text-align', "left");
        //作为jquery选择的标志
        input.addClass('txt_editer');
        if(tagName=="SPAN"){
        	input.css('overflow', "hidden");
        	input.css('border', "solid 1px #000000");
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei 输入框点击位置如果是自己，就保留输入框。防止点到输入框空白位置也会触发该事件
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//更新数据对象
            GRYP.setValue(td, $(this).val(),id);
            
        });
        input.trigger("focus").focusEnd();
	});
	
});


function save(){
	var a0000 = document.getElementById('a0000').value;
	var tp0100 = document.getElementById('tp0100').value;
	if(a0000 == null || a0000=='' || tp0100 == null || tp0100==''){
		odin.alert("请先选择人员！");
		return;
	}
	var DATA = Ext.encode(GRYP["DATA"]);
	var UPDATE_FIELD = Ext.encode(GRYP["UPDATE_FIELD"]);
	$('#DATA').val(DATA);
	$('#UPDATE_FIELD').val(UPDATE_FIELD);
	radow.doEvent('save');
	$('#flag').val('1');
}

function expJISHIForm(){
	var a0000 = document.getElementById('a0000').value;
	var tp0100 = document.getElementById('tp0100').value;
	if(a0000 == null || a0000=='' || tp0100 == null || tp0100==''){
		odin.alert("请先选择人员！");
		return;
	} 
	if($('#flag').val()!='1'){
		odin.alert("请先保存！");
		$('#flag').val(0);
	}
	
	var table=$("#rmbword table");
	var dataArray=[];
	
	$.each(table,function(index,tableeach){
		var tableDataArray=[];
		
		if($(this).hasClass("tableJBXX")){
			var td=$("td",$(this));
			$.each(td,function(i,tdeach){
				if($(this).prev().text()!="有无材料" & !$(this).hasClass("tdlabel")){
				
					if($("td",$(this).parent("tr")).length==11){
						
					}else{
						if($(this).text()!="")
							tableDataArray.push($(this).text());
						else
							tableDataArray.push("");
					}
				}
			}); 
		}else {
			var input=$("input",$(this));
			$.each(input,function(inputi,inputeach){
				if($(this).attr("checked"))
					tableDataArray.push("R");
				else
					tableDataArray.push("O");
			});
		}
		dataArray.push(tableDataArray);
	});
	
	$("#expdataarray").val(Ext.encode(dataArray));
	radow.doEvent('expJISHIWord');
}


function reShowMsg(gridobj,index,e){
	var rc = gridobj.getStore().getAt(index);
	$('#tp0100').val(rc.data.tp0100);
	$('#tp0116').val(rc.data.tp0116);
	document.getElementById('a0000').value=rc.data.a0000;
	radow.doEvent('initX2');
}
function formcheck(){
	//return odin.checkValue(document.forms.commForm);
}

function clearData(){
	GRYP["UPDATE_FIELD"]={};
}

function initData(jsonArray){
	if(jsonArray.length>0){
		var jsonData = jsonArray[0];
		GRYP["DATA"]=jsonData;
		clearData();
		//回显
		var k;
		for(k in jsonData){
			if(k!="a0000"&&k!="gzjsid"){
				var td = $("#"+k);
				//普通输入框
				if(td.length==1){
					GRYP.SetTDtext(td,jsonData[k]);
				}else{//checkbox
					var v =  jsonData[k];
					if(v!=""&&v!=null&&v!="null"){
						$("."+k).attr("checked", false);
						$("#"+v).attr("checked", true);
					}else{
						$("."+k).attr("checked", false);
					}
				}
			}
		}
		setGryp00(jsonData["gzjsid"]);
	}else{
		var jsonData = {"gzjs007": "&nbsp;","gzjs018":"&nbsp;","gzjs006":"&nbsp;","gzjs017":"&nbsp;",
				"gzjs005":"&nbsp;","gzjs016":"&nbsp;","gzjs004":"&nbsp;","gzjs015":"&nbsp;",
				"gzjs009":"&nbsp;","gzjs008":"&nbsp;","gzjs010":"&nbsp;","gzjs003":"&nbsp;",
				"gzjs014":"&nbsp;","gzjs002":"&nbsp;","gzjs013":"&nbsp;","gzjs001":"&nbsp;",
				"gzjs012":"&nbsp;","gzjs011":"&nbsp;","gzjsid":"&nbsp;"};
		GRYP["DATA"]={};
		clearData();
		//回显
		var k;
		for(k in jsonData){
			if(k!="a0000"&&k!="gzjsid"){
				var td = $("#"+k);
				//普通输入框
				if(td.length==1){
					GRYP.SetTDtext(td,null);
				}else{//checkbox
					$("."+k).attr("checked", false);
				}
			}
		}
		setGryp00("");
	}
}

function setGryp00(id){
	$("#gzjsid").val(id);
}

function setJCXX(jsonArray){
	if(jsonArray.length>0){
		var jsonData = jsonArray[0];
		var k;
		for(k in jsonData){
			GRYP.SetTDtext($("#"+k),jsonData[k]);
		}
	}
}
function typechange(){
	$('#tp0100').val("");
	$('#tp0116').val("");
	document.getElementById('a0000').value="";
	radow.doEvent("gridcq.dogridquery");
	initData({});
	setJCXX({tp0101:"", tp0106:"", tp0107:""});
	
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}    

</script>

<script type="text/javascript">
Ext.onReady(function(){
	
	var viewSize = Ext.getBody().getViewSize();
	var peopleInfoGrid =Ext.getCmp('gridcq');
	peopleInfoGrid.setHeight(viewSize.height-32);
	var gridobj = document.getElementById('forView_gridcq');
	var grid_pos = $h.pos(gridobj);
	
	//document.getElementById('rmbword').style.height= viewSize +"px";
	//$("#rmbword").css('height', peopleInfoGrid.getHeight());
	$('#rmbword').height(viewSize.height);
	if(typeof parentParam!= 'undefined'){
		document.getElementById('ynid').value=parentParam.yn_id;
	}
});

//文件下载
function download(id){
	//下载附件 downloadframe
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	document.getElementById('downloadframe').src="PublishFileServlet?method=downloadFile&jsf00="+encodeURI(encodeURI(id));
	
}
</script>
