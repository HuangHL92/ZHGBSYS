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
    font-family: ����;
    /* position: absolute; */
    left:0; right:0;  bottom:0;
    margin:auto;
    top:20px;
    width: 100%;
    height: 635px;
    overflow-y: scroll; 
}
td{
	font-family: ����_GB2312;
}

.tdlabel{
   text-align: center; 
   font-family: ����������� !important;    
}
.title-font{
	font-family: ����С���μ���;
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
	font-family:�������μ���!important;
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
		<div class="top_btn_style" style="background-color:#F08000;margin-left:0px;width:70px;" onclick="expJISHIForm();" >������ʵ��</div> 
	</div>
	<div style="width:50%;height:70px;float:right; ">
		<div class="top_btn_style" style="background-color:#F08000;margin-left:0px;width:70px;" onclick="save();" >��&nbsp;&nbsp;��</div> 
	</div>
</div>
<%-- <odin:toolBar property="bbar" applyTo="bbardiv">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" icon="images/icon/save.gif" handler="save" isLast="true" id="save"/>
</odin:toolBar> --%>

<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
	<!-- <tr>
		<td colspan="2"><div id="bbardiv"></div></td>
	</tr> -->
	<tr>
		<td style="width: 300px;">
			<table style="width: 100%;">
				<tr>
					<odin:select2 property="yn_type1" label="ѡ������" value="TPHJ1" width="240" onchange="typechange"
						data="['TPHJ1','����'],['TPHJ2','��ί���ר������Ա����'],['TPHJ3','�������'],['TPHJ4','��ί���ר�����'],['TPHJ5','��ί��ί��']"></odin:select2>
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
								<odin:gridEditColumn2 dataIndex="tp0100" width="50" hidden="true" editor="text" header="����" edited="false" align="center"/>
								<odin:gridEditColumn2 dataIndex="tp0101" width="100" header="����" editor="text" edited="false" align="left"/>
								<odin:gridEditColumn2 dataIndex="tp0106" width="150" header="����ְ��" editor="text" edited="false" align="left"/>
								<odin:gridEditColumn2 dataIndex="a0000" width="50" hidden="true" editor="text" header="��Ա����" edited="false" isLast="true" align="center"/>
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
				<span class="title-font">�ɲ�ѡ�����ù�����ʵ��</span>
				<table cellpadding="0" cellspacing="0" 
			     	style="font-size: 18px; border: 0px solid black;width: 100%;margin-bottom: 10px;margin-top: 10px;">
					<tr>
						<td style="text-align: center;font-family:�������μ���!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="rc"/>
							<label for="hao">�ճ�ѡ��</label>
						</td>
						<td style="text-align: center;font-family:�������μ���!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="hj"/>
							<label for="jiaohao">����ѡ��</label>
						</td>
						<td style="text-align: center;font-family:�������μ���!important;">
							<input type="checkbox" onclick="GRYP.setGRYPckValue(this)" name="gzjs018" class="GRYPckclass gzjs018" id="jzx"/>
							<label for="zhongdeng">������ѡ��</label>
						</td>
			       </tr>
				</table>
			    <table  cellpadding="0" cellspacing="0" class="tableJBXX" 
			     	style="border-bottom: 0px; margin-bottom: 0px;" >
			       <tr>
			         <td class="tdlabel width1 height1 center">��&nbsp;��</td>
			         <td class="width2 center" id="tp0101">&nbsp;</td>
			         <td class="tdlabel width2">����ְ��</td>
			         <td class="width3 left" colspan="3" id="tp0106">&nbsp;</td>
			       </tr>
			       <tr>
			        <td class="tdlabel ">��&nbsp;��<br>ְ&nbsp;��</td>
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
			         <td class="tdlabel height1" colspan="5">����ѡ�γ������</td>
			       </tr>
			       <tr>
			         <td class="width1 height1 tdlabel center">����</td>
			         <td class="width4 tdlabel" colspan="2">�����γɵĹ�������</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj01" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width1 height2 tdlabel">��&nbsp;��<br>��&nbsp;��</td>
			         <td class="width4 tdlabel" colspan="2">�����Ƽ��͸���̸���Ƽ����ܱ�</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
						<tags:JUploadGZJS property="sj02" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
						</td>
			       </tr>
			       <tr>
			         <td class="width1 tdlabel" rowspan="7">��<br>��</td>
			         <td class="width6 tdlabel">ȷ����������<br>ʱ��ͷ�ʽ</td>
			         <td class="input-editor widthInput" id="gzjs001" colspan="3">&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">��������ѡ����ͼ������������</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj03" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">����Ԥ��</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj04" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">�����������ܱ�����������ܱ�</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj05" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">�����й���������ʵ���</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj06" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">Ⱥ�ڷ�ӳ����Ҫ���⼰�����ʵ���</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj07" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width4 tdlabel height1" colspan="2">�ɲ������������ɲ��������</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj08" label="�ϴ�" fileTypeDesc="�����ļ�" 
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
			         <td class="tdlabel " rowspan="7" >��<br>��<br>��<br>��</td>
			         <td class="tdlabel height2" colspan="3">����ֹ��쵼�����<br>ʱ��Ͷ���</td>
			         <td class="input-editor " id="gzjs002" colspan="7">&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2" align="left" style="text-align: center; " colspan="6">��ί�����飩�Ա���λ��������ѡ���������<br>�������������</td>
			         <td class="tdlabel " colspan="2">���޲���</td>
			         <td class="width5 left " colspan="2">
			         	<tags:JUploadGZJS property="sj09" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			       	 <td class="tdlabel " rowspan="4">��<br>��<br>��<br>��</td>
			         <td class="tdlabel " align="left" rowspan="2">&nbsp;&nbsp;�������<br><br>��������</td>
			         <td class="tdlabel " colspan="2" rowspan="2">����<br>ʱ��</td>
			         <td class="tdlabel " rowspan="2">������</td>
			         <td class="tdlabel " rowspan="2">Ӧ��<br>����</td>
			         <td class="tdlabel " rowspan="2">ʵ��<br>����</td>
			         <td class="tdlabel height1" colspan="3">������</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2" >ͬ��</td>
			         <td class="tdlabel height2" >��ͬ��</td>
			         <td class="tdlabel height2" >��Ȩ</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height1">�������</td>
			         <td class="input-editor center" id="gzjs003" colspan="2" style="word-wrap: break-word;">&nbsp;</td>
			         <td class="input-editor center" id="gzjs004" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs005" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs006" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs007" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs008" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs009" >&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height2">��ί�����飩<br>����</td>
			         <td class="input-editor center" id="gzjs010" colspan="2">&nbsp;</td>
			         <td class="input-editor center" id="gzjs011" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs012" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs013" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs014" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs015" >&nbsp;</td>
			         <td class="input-editor center" id="gzjs016" >&nbsp;</td>
			       </tr>
			       <tr>
			         <td class="tdlabel height1" colspan="6">˫�ع���ɲ��������Э�ܷ��ظ����</td>
			         <td class="tdlabel " colspan="2">���޲���</td>
			         <td class="width5 left " colspan="2">
			         	<tags:JUploadGZJS property="sj10" label="�ϴ�" fileTypeDesc="�����ļ�" 
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
			         <td class="width1 tdlabel center" rowspan="3">��ְ</td>
			         <td class="width4 height1 tdlabel" colspan="2">��ְǰ��ʾ����</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj11" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="height1 width4 tdlabel" colspan="2">��ʾ�ڼ�Ⱥ�ڷ�ӳ�����⼰��������ʵ���</td>
			         <td class="tdlabel width5" >���޲���</td>
			         <td class="width5 left">
			         	<tags:JUploadGZJS property="sj12" label="�ϴ�" fileTypeDesc="�����ļ�" 
								uploadLimit="99" fileSizeLimit="20MB" fileTypeExts="*.*" />
					 </td>
			       </tr>
			       <tr>
			         <td class="width6 tdlabel " style="height: 70px;">��̸ְ��<br>��ʱ���<br≯����</td>
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
		//����padding����2
		var width = td.width() +6*2;
        var height = td.height()+8;
        
        if(tagName=="SPAN"){
        	width = td.width();
            height = td.height();
        }
      //�����ı���Ҳ����input�Ľڵ�   
        var div = $('<div style="position:absolute;top:0px;left:0px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //���ı����ݼ���td   
        td.append(div);
        
        //�����ı���ֵ����������ı�����   
        input.attr('value', text);
        input.css('border', "0px");
        input.css('text-align', "left");
        //��Ϊjqueryѡ��ı�־
        input.addClass('txt_editer');
        if(tagName=="SPAN"){
        	input.css('overflow', "hidden");
        	input.css('border', "solid 1px #000000");
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei �������λ��������Լ����ͱ�������򡣷�ֹ�㵽�����հ�λ��Ҳ�ᴥ�����¼�
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//�������ݶ���
            GRYP.setValue(td, $(this).val(),id);
            
        });
        input.trigger("focus").focusEnd();
	});
	
});


function save(){
	var a0000 = document.getElementById('a0000').value;
	var tp0100 = document.getElementById('tp0100').value;
	if(a0000 == null || a0000=='' || tp0100 == null || tp0100==''){
		odin.alert("����ѡ����Ա��");
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
		odin.alert("����ѡ����Ա��");
		return;
	} 
	if($('#flag').val()!='1'){
		odin.alert("���ȱ��棡");
		$('#flag').val(0);
	}
	
	var table=$("#rmbword table");
	var dataArray=[];
	
	$.each(table,function(index,tableeach){
		var tableDataArray=[];
		
		if($(this).hasClass("tableJBXX")){
			var td=$("td",$(this));
			$.each(td,function(i,tdeach){
				if($(this).prev().text()!="���޲���" & !$(this).hasClass("tdlabel")){
				
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
		//����
		var k;
		for(k in jsonData){
			if(k!="a0000"&&k!="gzjsid"){
				var td = $("#"+k);
				//��ͨ�����
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
		//����
		var k;
		for(k in jsonData){
			if(k!="a0000"&&k!="gzjsid"){
				var td = $("#"+k);
				//��ͨ�����
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

//�ļ�����
function download(id){
	//���ظ��� downloadframe
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	document.getElementById('downloadframe').src="PublishFileServlet?method=downloadFile&jsf00="+encodeURI(encodeURI(id));
	
}
</script>
