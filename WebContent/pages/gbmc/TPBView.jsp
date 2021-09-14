<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/tableEditer.js" type="text/javascript"></script>
 
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm2/gbtp.css">

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
        	var tds = $("td:nth-child(n+2)", tr);
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
        if(rowData) {
            var tds = $("td:nth-child(n+2)", tr);
            var yntype = $("#yntype").val();
            var tp0103 = "";
            var tp0104 = "";

            SetTDtext(tds[0], rowData["a0101"]);  //   姓名
            SetTDtext(tds[1], rowData["a0192a"]);  //   现工作单位及职务全称
            SetTDtext(tds[2], rowData["a0104"]);  //   性别
            SetTDtext(tds[3], rowData["a0117"]);  //   民族
            SetTDtext(tds[4], rowData["a0111a"]);  //   籍贯
            SetTDtext(tds[5], rowData["qrzxl"]);  //   最高全日制学历
            SetTDtext(tds[6], rowData["qrzxlxx"]);  //   院校系专业（最高全日制学历）
            SetTDtext(tds[7], rowData["zzxl"]);  //   最高在职学历
            SetTDtext(tds[8], rowData["zzxlxx"]);  //   院校系专业（最高在职学历）
            SetTDtext(tds[9], rowData["a0107"]);  //   出生日期
            SetTDtext(tds[10], rowData["a0144"]);  //   入党时间
            SetTDtext(tds[11], rowData["a0134"]);  //   参加工作时间
            SetTDtext(tds[12], rowData["a0288"]);  //   任现职务层次时间
            SetTDtext(tds[13], rowData["a0192c"]);  //   任该职级时间
            // SetTDtext(tds[15], rowData["a0195"]);  //   统计关系所在code
            // SetTDtext(tds[16], rowData["a0195A"]);  //   统计关系所在单位
            SetTDtext(tds[14], rowData["comments"]);  //   最高学位，专业技术
            rowd = rowData;

            rowIndex = 0;
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
        				$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','考察材料上传',300,160,
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
	        		$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','考察材料上传',300,130,
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
    	/* $h.showModalDialog('personInfoOP',g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000,'人员信息修改',1009,630,null,{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true); */
		window.open(g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000, '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no');
        return false;
    });
    
 });
    </script>

<table style="width:100%">
<tr>
<td style="width:300px">
	<div style="width:300px">
	<odin:toolBar property="topbar">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="删除" id="deleteGD" handler="deleteGD" icon="images/icon/table.gif" />
		<!--<odin:buttonForToolBar text="打印任免表" id="Print.printWord" handler="Print.printWordNoRM" icon="images/icon/table.gif" />-->
		<odin:buttonForToolBar text="导出" icon="images/icon/table.gif" handler="exportTPB" id="exportTPB" />
		<odin:buttonForToolBar text="读取" icon="images/icon/table.gif" isLast="true" handler="readTPB" id="readTPB" />
	</odin:toolBar>
	<odin:editgrid2 property="memberGrid" height="640" pageSize="9999" topBarId="topbar" url="/">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="yn_id" />
			<odin:gridDataCol name="yn_type"/>
			<odin:gridDataCol name="yndate"/>
			<odin:gridDataCol name="yn_gd_desc"/>
			<odin:gridDataCol name="yn_gd_id" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
			<odin:gridEditColumn2 dataIndex="yn_type" width="110" header="调配环节" editor="select" selectData="['TPHJ1','干部信息一览表']" edited="false" align="center"/> <!-- ,['TPHJ2','市委书记专题会议成员酝酿'],['TPHJ3','部务会议'],['TPHJ4','市委书记专题会议'],['TPHJ5','市委常委会'] -->
			<odin:gridEditColumn2 dataIndex="yn_gd_desc" width="190" header="备注" editor="text" edited="false" align="left"/>
			
			<odin:gridEditColumn2 dataIndex="yndate" width="110" header="存档时间" editor="text" edited="false" align="center"  isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid2>
	</div> 
</td>
<td>
	<div id="selectable" style="margin-top:30px;text-align:center">
		<p class="BiaoTouP" style="text-align:center"><font>干&nbsp;部&nbsp;信&nbsp;息&nbsp;一&nbsp;览&nbsp;表 </font></p>
		<p class="TiaoPeiShiJian" id="TiaoPeiShiJianPrint" style="text-align:right"><span class="TiaoPeiShiJianYSPAN  TNR">2019</span>年<span class="TiaoPeiShiJianMSPAN TNR">12</span>月<span class="TiaoPeiShiJianDSPAN TNR">2</span>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
		<div id="coordTable_div" style="margin-left:20px;margin-bottom:20px; overflow:scroll;width:820px; height:560px">
			<table id="coordTable" cellspacing="0" style="width:2080px" >
				<tr>
					<%--<th rowspan="2" style="height:50px !important; width:5%">查看</th>--%>
					<th rowspan="2" style="height:50px !important; width:3%">序<BR>号</th>
					<th rowspan="2" style="height:50px !important; width:5%">姓名</th>
					<%--<th rowspan="2" style="height:50px !important; width:7%">照片</th>  --%>
					<th rowspan="2" style="height:50px !important; width:9%">现任职务</th>
					<%--<th rowspan="2" style="height:50px !important; width:10%">拟任免职务</th>--%>
					<th rowspan="2" style="height:50px !important; width:2%">性<BR>别</th>
					<th rowspan="2" style="height:50px !important; width:2%">民<BR>族</th>
					<th rowspan="2" style="height:50px !important; width:4%">籍贯</th>

					<th colspan="2" style=" width:20%">全日制教育</th>
					<th colspan="2" style=" width:20%">在职教育</th>

					<th rowspan="2" style="height:50px !important; width:5%">出生<BR>年月</th>
					<th rowspan="2" style="height:50px !important; width:5%">入党<BR>时间</th>
					<th rowspan="2" style="height:50px !important; width:5%">参与工作时间</th>
					<th rowspan="2" style=" width:5%">任现职<BR>时&nbsp;间</th>
					<th rowspan="2" style=" width:5%">任现职<BR>级时间</th>
					<th rowspan="2" style=" width:7%">备注</th>
				</tr>
				<tr>
					<th style="    font-family: 黑体; font-size: 13pt; letter-spacing: 2px">学历</th>
					<th style="   font-family: 黑体; font-size: 13pt; letter-spacing: 2px">毕业院校及专业</th>

					<th style="    font-family: 黑体; font-size: 13pt; letter-spacing: 2px">学历</th>
					<th style="   font-family: 黑体; font-size: 13pt; letter-spacing: 2px">毕业院校及专业</th>

				</tr>
			</table>
		</div>
	
	
		<%--<div class="NiRenMianBianJi" rowIndex="" colIndex="" id="NiRenMianBianJi" style="position: absolute;display:none; top: 0px;left: 0px; border: 1px solid silver; background-color:rgb(224,234,245); padding: 6px;">--%>
			<%--<div class="Ren" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
			<%--<div class="Mian" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
			<%--<div class="QiTa" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
		<%--</div>--%>
		
		
	</div>
</td>
</tr>
</table>

 

<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<script type="text/javascript">

// function reShowMsg(gridobj,index,e){
// 	var rc = gridobj.getStore().getAt(index);
// 	$('#ynId').val(rc.data.yn_id);
// 	$("#yntype").val(rc.data.yn_type);
// 	$("#yn_gd_id").val(rc.data.yn_gd_id);
// 	TIME_INIT.clearData();
// 	radow.doEvent('initX');
// }
// function readTPB(){
// 	radow.doEvent('readTPB');
// }
// function deleteGD(){
// 	$h.confirm("系统提示：",'确认删除？',100,function(id) {
// 		if("ok"==id){
// 			radow.doEvent('deleteGD');
// 		}else{
// 			return false;
// 		}
// 	});
//
// }
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
            $h.openWin('seltpry','pages.xbrm2.SelectTPRY','按姓名/身份证查询 ',1040,520,null,g_contextpath,window,
                {maximizable:false,resizable:false,RMRY:'任免人员',
                    ynId:ynId,yntype:yntype});
        },
        queryByNameAndIDS:function(list){//按姓名查询
            //if(obj){
            radow.doEvent('queryByNameAndIDS',list);
            //}
        },
        addPerson:function(plist){//按姓名查询 插入人员
            var rowIndex = 0;
            if(obj){
                rowIndex = $(obj).index();
            } else {
                rowIndex = 0;
            }
            try{
                if(rowIndex>0){
                    $.each(plist,function (i, item){
                        var $tr = createRow(item["type"],rowIndex+i,item["a0000"],item);
                        $tr.insertBefore($(obj));
                    });
                }else if(rowIndex==0){//追加
                    $.each(plist,function (i, item){
                        var $tr = createRow(item["type"],rowIndex,item["a0000"],item);
                        $("#coordTable").append($tr);
                    });
                }

                complate();
                $("#coordTable_div").css("height",$(document.body).height()-130+"px");
                // jQuery.coordTable.appendDiv();
                obj = null;
            }catch(e){
                alert(e);
            }

        }

    }
})();





function exportTPB(expType){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var yn_gd_id = $("#yn_gd_id").val();
	
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPBView&eventNames=ExpTPB';
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
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames='+eventType;
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
			  		$helper.alertActiveX();
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
			  		$helper.alertActiveX();
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
