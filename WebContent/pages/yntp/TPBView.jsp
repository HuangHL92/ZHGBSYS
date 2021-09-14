<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/yntp/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/yntp/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/yntp/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/yntp/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/yntp/js/tableEditer.js" type="text/javascript"></script>
 
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/yntp/gbtp.css">

<style>
body{
margin: 1px;overflow-y: scroll;overflow-x: hidden;
font-family:'宋体',Simsun;
word-break:break-all;
margin-bottom: 250px;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
}
.btncls2{
	position: absolute;
	left: 10px;
	top: 117px;
	z-index: 2000;
	width: 300px;
}
.kcclClass{
background-color: rgb(102,204,255) !important;
}
.drag_color{
	background-color: rgb(232,232,232) !important;
}
.drag_pre_color{
	background-color: rgb(233,250,238)!important;
}
.default_color{
	background-color: #FFFFFF !important;
}
</style>
<odin:hidden property="ynId"/>
<odin:hidden property="yn_gd_id"/>


<script type="text/javascript">



var g_contextpath = '<%= request.getContextPath() %>';
	Ext.onReady(function(){
		$('#ynId').val(parentParam?parentParam.yn_id:"d4535b4b-7374-4279-b6ea-cbd3df29aed4");
		$('#yn_type').val(parentParam?parentParam.yn_type:"TPHJ1");
		$("#yntype").val(parentParam?parentParam.yn_type:"TPHJ1");
	});
	Array.prototype.removeIndex = function (index) {
	  if (index > - 1) {
	    this.splice(index, 1);
	  }
	};
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null")?" ":v.replace(/\n/g,"<br/>"));
	}
	//根据行号获取rowid
	function GetRowid(rowIndex){
		return GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset];
	}
	
</script>

<script type="text/javascript">
var GLOBLE={};
GLOBLE.colOffset=1;//第三列开始可编辑区域。
GLOBLE.rowOffset=1;//第二行开始可编辑区域。
//根据行号存储人员id 行号=index+1
GLOBLE['ROWID']=[];
//根据类别和列号获取列名
GLOBLE['COL_CONFIG_1']={"2":"tp0101"};
GLOBLE['COL_CONFIG_2']={"2":"tp0101"};
GLOBLE['COL_CONFIG_3']={"2":"tp0101","3":"tp0102","4":"tp0103","5":"tp0104","6":"tp0105","7":"tp0106","8":["tp0107","tp0108","tp0109","tp0110","tp0111","tp0112","tp0113","tp0114","tp0115"]};
//剪切后的数据 存放对象
GLOBLE['CUTEDDATA']={};
GLOBLE['CUTEDROWID']=[];

//根据id存储行信息
GLOBLE['ID_ROWINFO']={};

//更新行后更新序号和行号。
var complate = function () {
    $('#coordTable tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    var k = 1;
    $('#coordTable tr:gt(0) td:nth-child(2)').each(function (i, item) {
    	var id = GetRowid($(this).parent().index());
    	var type = GLOBLE['ID_ROWINFO'][id]["type"];
    	if(type=="3"){
    		$(this).text(k++);
    	}
        
    });
}

//创建行//{"8":["tp0107","tp0108","tp0109","tp0110", "tp0111","tp0112","tp0113","tp0114","tp0115"]}
var createRow = function (type,rowIndex,guid,rowData) {//splice(index, 0, val);

	if(!type){
		type="3";
	}
	if(!guid){
		guid = GUID();
	}
	var tr;
	var rowd;
	if(type=="1"){
		tr = $('<tr class="data YiJiBiaoTiTR"><td class="rownum default_color"></td><td class="TNR default_color"></td><td colspan="8" class=" YiJiBiaoTiTD">&nbsp;</td></tr>');
		if(rowData){
        	rowd=rowData;
        	var tds = $("td:nth-child(n+3)", tr);
        	SetTDtext(tds[0],rowData["tp0101"]);
		}else{
        	rowd={"tp0100":guid,"type":"1","tp0101":""};
		}
		
	}else if(type=="2"){
		tr = $('<tr class="data ErJiBiaoTiTR"><td class="rownum default_color"></td><td class="TNR default_color"></td><td colspan="8" class=" ErJiBiaoTiTD">&nbsp;</td></tr>');
		if(rowData){
        	rowd=rowData;
        	var tds = $("td:nth-child(n+3)", tr);
        	SetTDtext(tds[0],rowData["tp0101"]);
		}else{
        	rowd={"tp0100":guid,"type":"2","tp0101":""};
		}
	}else{
		var kcclClass = "";//考察材料背景颜色
		if(rowData){
			kcclClass = rowData['kcclclass'];
		}
		tr = $('<tr class="data"><td class="rownum default_color"></td><td class="TNR '+kcclClass+' "></td><td>&nbsp;</td><td class="TNR">&nbsp;</td><td class="TNR">&nbsp;</td><td class="TNR">&nbsp;</td><td>&nbsp;</td><td class="align-left">&nbsp;</td><td class="align-left">&nbsp;</td></tr>');
		if(rowData){
			var tds = $("td:nth-child(n+3)", tr);
			var yntype = $("#yntype").val();
			var tp0103="";var tp0104="";
			if("TPHJ1"==yntype||"TPHJ2"==yntype){
				tp0103 = rowData["tp0103"];
				tp0104 = rowData["tp0104"];
			}
        	SetTDtext(tds[0],rowData["tp0101"]);SetTDtext(tds[1],rowData["tp0102"]);SetTDtext(tds[2],tp0103);SetTDtext(tds[3],tp0104);SetTDtext(tds[4],rowData["tp0105"]);SetTDtext(tds[5],rowData["tp0106"]);SetTDtext(tds[6],rowData["tp0107"]);
        	rowd=rowData;
		}else{
        	rowd={"tp0100":guid,"type":"3","tp0101":"","tp0102":"","tp0103":"","tp0104":"","tp0105":"","tp0106":"","tp0107":"","tp0108":"","tp0109":"","tp0110":"","tp0111":"","tp0112":"","tp0113":"","tp0114":"","tp0115":""};
		}
	}
	//更新数据对象
	if(rowIndex>0){
		GLOBLE['ROWID'].splice(rowIndex-GLOBLE.rowOffset, 0, guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}else if(rowIndex==0){//在表头上表示追加
		GLOBLE['ROWID'].push(guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}
    return tr;
}

//剪贴后触发的方法
var cuted = function () {
    complate();
}


var uploadKCCL = (function(){//考察材料上传
	var obj = null;
	return {
		hasSave:function(o){
			obj = o;
			var rowIndex = $(o).index();
        	if(rowIndex>0){
        		var rowId = GetRowid(rowIndex);
        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
        		var tp0100 = GLOBLE['ID_ROWINFO'][rowId]["tp0100"];
        		var ynId = document.getElementById('ynId').value;
        		if(a0000==""||a0000==null){
            		return;
            	}
        		eventajax("hasSaved",{tp0100:tp0100},function(code){
        			if(code==0){
        				$h.openPageModeWin('uploadKccl','pages.yntp.UploadKccl','考察材料上传',300,160,
                				{a0000:a0000,ynId:ynId,$tr:$(o)},g_contextpath);
        			}else{
        				$h.confirm("系统提示：",'调配表信息尚未保存，<br/>点击【确定】保存调配表信息，点击【否】取消。',200,function(id) { 
        					if("ok"==id){
        						//Ext.getCmp('a0184').clearInvalid();
        						save("savefirst");
        					}	
        				});
        			}
        		})
        	}
		},
		openUploadKcclWin:function(){//
			if(obj){
				var rowIndex = $(obj).index();
	        	if(rowIndex>0){
	        		var rowId = GetRowid(rowIndex);
	        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
	        		var ynId = document.getElementById('ynId').value;
	        		if(a0000==""||a0000==null){
	            		return;
	            	}
	        		$h.openPageModeWin('uploadKccl','pages.yntp.UploadKccl','考察材料上传',300,130,
	        				{a0000:a0000,ynId:ynId,$tr:$(obj)},g_contextpath);
	        	}
			}
		}
	}
})();

$(document).ready(function () {


   
  //点击选中行
    $("#coordTable tr.data td:nth-child(1)").live('dblclick', function (e) {
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	}
    	$h.showModalDialog('personInfoOP',g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000,'人员信息修改',1009,630,null,
		{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
        return false;
    });
    
 });
    </script>

<div class="btncls2">
<odin:toolBar property="topbar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="删除" id="deleteGD" handler="deleteGD" icon="images/icon/table.gif" />
	<odin:buttonForToolBar text="打印任免表" id="Print.printWord" handler="Print.printWordNoRM" icon="images/icon/table.gif" />
	<odin:buttonForToolBar text="导出调配表" icon="images/icon/table.gif" handler="exportTPB" id="exportTPB" />
	<odin:buttonForToolBar text="读取" icon="images/icon/table.gif" isLast="true" handler="readTPB" id="readTPB" />
</odin:toolBar>
<odin:editgrid2 property="memberGrid" height="560" pageSize="9999" topBarId="topbar" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="yn_id" />
		<odin:gridDataCol name="yn_type"/>
		<odin:gridDataCol name="yndate"/>
		<odin:gridDataCol name="yn_gd_desc"/>
		<odin:gridDataCol name="yn_gd_id" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="yn_type" width="110" header="调配环节" editor="select" selectData="['TPHJ1','酝酿'],['TPHJ2','市委书记专题会议成员酝酿'],['TPHJ3','部务会议'],['TPHJ4','市委书记专题会议'],['TPHJ5','市委常委会']" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="yn_gd_desc" width="190" header="备注" editor="text" edited="false" align="left"/>
		
		<odin:gridEditColumn2 dataIndex="yndate" width="110" header="存档时间" editor="text" edited="false" align="center"  isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</div>
<!-- <div class="btncls">
	<div class="top_btn_style" style="background-color:#3680C9; " onclick="exportTPB('TPB')">导出调配表</div>
	<div class="top_btn_style" style="background-color:#3680C9; " onclick="openProcessWin('SHSJB')">导出上会数据包</div>
	<div class="top_btn_style" style="background-color:#3680C9; " onclick="openProcessWin('full')">导出上会数据包</div>
	<div class="top_btn_style" style="background-color:#3680C9; " onclick="Print.printWord()">打印任免表</div>
</div> -->
<div id="selectable" style="height: 700px;overflow-y: scroll;">
						
						
<div style="text-align:left; margin-left:320px;margin-top:20px;margin-bottom:20px; width:804px;">
<p class="BiaoTouP">干&nbsp;部&nbsp;调&nbsp;配&nbsp;建&nbsp;议&nbsp;方&nbsp;案</p>
<p class="TiaoPeiLeiXing">（<span class="TiaoPeiLeiXingSPAN">酝酿</span>）</p>

<p class="TiaoPeiShiJian"><span class="TiaoPeiShiJianYSPAN  TNR">2019</span>年<span class="TiaoPeiShiJianMSPAN TNR">12</span>月<span class="TiaoPeiShiJianDSPAN TNR">2</span>日</p>
<table id="coordTable" cellspacing="0" width="100%" >
	<tr>
	    <th style="width:4%">查<br/>看</th>
	    <th style="width:4%">序<br/>号</th>
	    <th style="width:8%">姓名</th>
	    <th style="width:8%">出生<br/>年月</th>
	    <th id="RenXianZhiShiJian" style="width:8%">任现职<br/>时间</th>
	    <th id="RenTongJiShiJian" style="width:8%">任同级<br/>时间</th>
	    <th style="width:10%">学历<br/>职称</th>
	    <th id="XianRenZhiWu" style="width:25%">现任职务</th>
	    <th id="NiRenMianZhiWu" style="width:25%">拟任免职务</th>
	</tr>
	
</table>


	<div class="NiRenMianBianJi" rowIndex="" colIndex="" id="NiRenMianBianJi" style="position: absolute;display:none; top: 0px;left: 0px; border: 1px solid silver; background-color:rgb(224,234,245); padding: 6px;">
		<div class="Ren" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
		<div class="Mian" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
		<div class="QiTa" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">
			<textarea style="height: 100%;width: 100%"></textarea>
		</div>
	</div>
	
	
</div>
</div>

<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<script type="text/javascript">

function reShowMsg(gridobj,index,e){
	var rc = gridobj.getStore().getAt(index);
	$('#ynId').val(rc.data.yn_id);
	$("#yntype").val(rc.data.yn_type);
	$("#yn_gd_id").val(rc.data.yn_gd_id);
	TIME_INIT.clearData();
	radow.doEvent('initX');
}
function readTPB(){
	radow.doEvent('readTPB');
}
function deleteGD(){
	$h.confirm("系统提示：",'确认删除？',100,function(id) { 
		if("ok"==id){
			radow.doEvent('deleteGD');
		}else{
			return false;
		}		
	});
	
}

var doAddPerson = (function(){//增加人员
	var obj = null;
	return {
		sel:function(o){
			obj = o;
			var ynId = document.getElementById('ynId').value;
			var yntype = $("#yntype").val();
			if(ynId==''){
				//$h.alert('系统提示','无批次信息！');
				//return;
			}
			$h.openWin('seltpry','pages.yntp.SelectTPRY','按姓名/身份证查询 ',820,520,null,g_contextpath,window,
					{maximizable:false,resizable:false,RMRY:'任免人员',
				ynId:ynId,yntype:yntype});
		},
		queryByNameAndIDS:function(list){//按姓名查询
			if(obj){
				radow.doEvent('queryByNameAndIDS',list);
			}
		},
		addPerson:function(plist){//按姓名查询 插入人员
			var rowIndex = 0;
			if(obj){
				rowIndex = $(obj).index();
			} 	
       		if(rowIndex>0){
	   			$.each(plist,function (i, item){
	        		var $tr = createRow(item["type"],rowIndex+i,item["tp0100"],item);
	        		$tr.insertBefore($(obj));
	   			});
      		}else if(rowIndex==0){//追加
				$.each(plist,function (i, item){
					var $tr = createRow(item["type"],rowIndex,item["tp0100"],item);
          			$("#coordTable").append($tr);
          		});
      		}
			
			complate();
			obj = null;
		}
	}
})();


Ext.onReady(function () {
	/* TIME_INIT.initShiJian();
	TIME_INIT.setTPHJLB();
	TIME_INIT.setTPHJSJ_Y();
	TIME_INIT.setTPHJSJ_M();
	TIME_INIT.setTPHJSJ_D(); */
	
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.on('rowclick',reShowMsg);
	
});
var TIME_INIT = (function(){
	return {
		setTPHJLB : function(){
			//调配环节类别控件
			var $div = $(".TiaoPeiLeiXingDIV");
			var $sel = $("#yn_type");
			$div.hide();
			$sel.focus();
			$(".TiaoPeiLeiXingSPAN").bind('click', function(e) {
				//var left = 200 + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				//$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				//调配环节类别id
				var tphjid = $(this).children('option:selected').val();  
			　　　var tphjtext = $(this).children('option:selected').text();
				$(".TiaoPeiLeiXingSPAN").text(tphjtext);
				$("#yntype").val(tphjid);
				//清空数据
				TIME_INIT.clearData();
				radow.doEvent('initX');
		　　	});
		},
		changeTableType : function(tphjid){
			if(tphjid=='TPHJ1'||tphjid=='TPHJ2'){
				$('#RenXianZhiShiJian').css('width','8%');
			    $('#RenTongJiShiJian').css('width','8%');
			    $('#XianRenZhiWu').css('width','25%');
			    $('#NiRenMianZhiWu').css('width','25%');
			}else{
				$('#RenXianZhiShiJian').css('width','0%');
			    $('#RenTongJiShiJian').css('width','0%');
			    $('#XianRenZhiWu').css('width','33%');
			    $('#NiRenMianZhiWu').css('width','33%');
			}
		},
		clearData : function(){
			$(".data").remove();
			GLOBLE['ROWID']=[];
			GLOBLE['ID_ROWINFO']={};
			//情空缓存
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
		},

		setTPHJSJ_Y : function(){
			//调配时间控件
			var $div = $(".TiaoPeiShiJianY");
			var $sel = $("#tp_y");
			$div.hide();
			$sel.focus();
			$(".TiaoPeiShiJianYSPAN").bind('click', function(e) {
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
			　　　var tphjid = $(this).children('option:selected').val();  
			　　　var tphjtext = $(this).children('option:selected').text();
				$(".TiaoPeiShiJianYSPAN").text(tphjtext);
				$("#tpy").val(tphjid);
		　　	});
		},
		setTPHJSJ_M : function(){
			//调配时间控件
			var $div = $(".TiaoPeiShiJianM");
			var $sel = $("#tp_m");
			$div.hide();
			$sel.focus();
			$(".TiaoPeiShiJianMSPAN").bind('click', function(e) {
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
			　　　var tphjid = $(this).children('option:selected').val();  
			　　　var tphjtext = $(this).children('option:selected').text();
				$(".TiaoPeiShiJianMSPAN").text(tphjtext);
				$("#tpm").val(tphjid);
		　　	});
		},
		setTPHJSJ_D : function(){
			//调配时间控件
			var $div = $(".TiaoPeiShiJianD");
			var $sel = $("#tp_d");
			$div.hide();
			$sel.focus();
			$(".TiaoPeiShiJianDSPAN").bind('click', function(e) {
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				//调配环节类别id
			　　　var tphjid = $(this).children('option:selected').val();  
			　　　var tphjtext = $(this).children('option:selected').text();
				$(".TiaoPeiShiJianDSPAN").text(tphjtext);
				$("#tpd").val(tphjid);
		　　	});
		},
		initShiJian : function(){
			var myDate = new Date();
			var y = myDate.getFullYear();
			var $sel = $("#tp_y");
			for(var i=0;i<3;i++){
				var $o = $('<option value="'+(y-i+1)+'">'+(y-i+1)+'</option>');
				$sel.append($o);
			}
			$sel = $("#tp_m");
			for(var i=1;i<13;i++){
				var $o = $('<option value="'+(i)+'">'+(i)+'</option>');
				$sel.append($o);
			}
			$sel = $("#tp_d");
			for(var i=1;i<32;i++){
				var $o = $('<option value="'+(i)+'">'+(i)+'</option>');
				$sel.append($o);
			}
		},
		//设置调配时间信息
		setTime : function(y,m,d,tphjlb){
			$('#tpy').val(y);
			$('#tpm').val(m);
			$('#tpd').val(d);
			$('#tp_y').val(y);
			$('#tp_m').val(m);
			$('#tp_d').val(d);
			$("#yntype").val(tphjlb);
			$("#yn_type").val(tphjlb);
			
		　　　var tphjtext = $('#tp_y').children('option:selected').text();
			$(".TiaoPeiShiJianYSPAN").text(y);
		　　　tphjtext = $('#tp_m').children('option:selected').text();
			$(".TiaoPeiShiJianMSPAN").text(m);
		　　　tphjtext = $('#tp_d').children('option:selected').text();
			$(".TiaoPeiShiJianDSPAN").text(d);
		　　　tphjtext = $('#yn_type').children('option:selected').text();
			$(".TiaoPeiLeiXingSPAN").text(tphjtext);
			
		}
	}
})();



function exportTPB(expType){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var yn_gd_id = $("#yn_gd_id").val();
	
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.yntp.TPBView&eventNames=ExpTPB';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出干部调配建议方案 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType,yn_gd_id:yn_gd_id},
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



function eventajax(eventType,params,cbfun){
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.yntp.TPB&eventNames='+eventType;
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : params,
        callback: function (options, success, response) {
      	   if (success) {
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				  cbfun(cfg.messageCode)
 				}
      	   }
        }
   });
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
function openProcessWin(v){//导出上会材料
	var ynId = $("#ynId").val();
	var src = g_contextpath+'/PublishFileServlet?method=expSHCL&ynId='+ynId+'&exptype='+v;
	var selecthtml = '<select id="expParm"  name="expParm" style="margin:10px;"> '+
		  '<option value ="&cur_hj=TPHJ3">部会</option>'+
		  '<option value="&cur_hj=TPHJ4,TPHJ5">书记会常委会</option>'+
		'</select>';
		
	var win = new Ext.Window({
		html : '<button style="margin:10px;"  onclick="$(\'#iframe_expFile\').attr(\'src\',\''+src+'\');this.disabled=true;">点击开始执行导出</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
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








 

//打印
var Print = (function(){
	var wdapp = $h.getTopParent().WORD_PRINT;
	
	return {
		printWord : function(){
			if(!wdapp){
				try{ 
					wdapp = new ActiveXObject("Word.Application");
			  	}catch(e){ 
				    alert("无法调用Office对象，请确保您的机器已安装了Office并已将本系统的站点名加入到IE的信任站点列表中，并将ActiveX控件选项√上启用！"); 
				    return; 
			  	}
			}
			Ext.MessageBox.wait('正在打印中，请稍后。。。');
			radow.doEvent("expWord");
		},
		printWordNoRM : function(){
			if(!wdapp){
				try{ 
					wdapp = new ActiveXObject("Word.Application");
			  	}catch(e){ 
				    alert("无法调用Office对象，请确保您的机器已安装了Office并已将本系统的站点名加入到IE的信任站点列表中，并将ActiveX控件选项√上启用！"); 
				    return; 
			  	}
			}
			Ext.MessageBox.wait('正在打印中，请稍后。。。');
			radow.doEvent("expWord","NORM");
		},
		startPrint : function(downloadUUID,sid){
			var url=window.location.protocol+"//"+window.location.host+
			g_contextpath+'/PublishFileServlet;jsessionid='+sid+'?method=downloadFile&uuid='+downloadUUID;
			//Ext.MessageBox.hide();
			wdapp.Documents.Open(url);//打开word模板url
			wdapp.Application.Printout();
		}
	}
  	
  	
})();





function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}




</script>
<div style="display: none;">
<div class="TiaoPeiLeiXingDIV">
<select id="yn_type" name="yn_type">
  <option value="TPHJ1">酝酿</option>
  <option value="TPHJ2">市委书记专题会议成员酝酿</option>
  <option value="TPHJ3">部务会议</option>
  <option value="TPHJ4">市委书记专题会议</option>
  <option value="TPHJ5">市委常委会</option>
</select>
</div>
<div class="TiaoPeiShiJianY">
	<select id="tp_y" name="tp_y">
	</select>
</div>
<div class="TiaoPeiShiJianM">
	<select id="tp_m" name="tp_m">
	</select>
</div>
<div class="TiaoPeiShiJianD">
	<select id="tp_d" name="tp_d">
	</select>
</div>
</div>



<odin:hidden property="yntype" />
<odin:hidden property="tpy" />
<odin:hidden property="tpm" />
<odin:hidden property="tpd" />
<odin:hidden property="docpath" />
<odin:hidden property="ID_ROWINFO" title="保存对象"/>
<odin:hidden property="ROWID" title="保存对象id"/>
