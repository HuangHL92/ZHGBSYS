/******
 *  日期：2019-08-20
 * 	修订时间：2019-10-17
 *  主要功能：
 *  	实现对动议干部情况一览表、省委常委会干部情况一览表、省委书记专题会议干部情况一览表等一览表
 *  的显示、编辑、导入、复制、粘贴、调整格式、归档、考察材料管理、照片处理等的功能
 */
var GLOBLE={};
GLOBLE.colOffset=0;//第三列开始可编辑区域。
GLOBLE.rowOffset=1;//第二行开始可编辑区域。
//根据行号存储人员id 行号=index+1
GLOBLE['ROWID']=[];
//根据类别和列号获取列名
GLOBLE['COL_CONFIG_1']={"2":"tp0101"};
GLOBLE['COL_CONFIG_2']={"2":"tp0101"};
 
//剪切后的数据 存放对象
GLOBLE['CUTEDDATA']={};
GLOBLE['CUTEDROWID']=[];

//根据id存储行信息
GLOBLE['ID_ROWINFO']={};
 /*GLOBLE['ID_ROWINFO']["11"]={"tp0100":"11","type":"1","tp0101":"一、双重管理单位领导人员征求意见："};
GLOBLE['ID_ROWINFO']["22"]={"tp0100":"22","type":"2","tp0101":"中国联合网络通信有限公司宁波市分公司："};
GLOBLE['ID_ROWINFO']["33"]={"tp0100":"33","type":"3","tp0101":"张三1","tp0102":"69.02","tp0103":"90.11","tp0104":"88.12","tp0105":"大学本科\n在职硕士","tp0106":"中国联通系统集成有限公司浙江省分公司总经理","tp0107":"任中国联通宁波市分公司党委书记、总经理，免现职"};
 */

//更新行后更新序号和行号。
var complate = function () {
    $('#coordTable tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    
    var index = 1;
    $('#coordTable tr:gt(0) td:nth-child(2)').each(function (i, item) {
        if ($(this).parent().children().length<4){
        	index = 1;
        }else{
        	$(this).text(index);
        	index++;
        }
    });
     
     
}

 
 //上传照片
function picupload1 (o) {
	var rowIndex = $(o).index();
	if(rowIndex>0){
		/* if ($("#coordTable tr:eq(1) th").length>0){
			rowIndex--;
		} */
		
		var rowId = GetRowid(rowIndex);
		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"]; 
		var imgId = a0000.replace(/[-]/g,'');
		document.getElementById("a0000").value = a0000;
		$h.showModalDialog('picupload',g_contextpath+'/picCut/picwin.jsp?ImgIdPostfix='+imgId+'&a0000="+a0000+"','头像上传',900,490,null,{a0000:'"+a0000+"'},true);
	}
} 

//剪贴后触发的方法
var cuted = function () {
    complate();
}
 
function inputTPBExcel(){
	var ynId = document.getElementById('ynId').value;
	var tp0116 = document.getElementById('yntype').value;
	$("#coordTableHtmlContent").val("");
	$h.confirm("系统提示：",'模板数据导入，<br/>导入Excel文件数据会清除当前批次数据，是否继续？',200,function(id) { 
		if("ok"==id){ 
			$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','导入Excel',300,160,
					{"ynId":ynId,"tp0116":tp0116,isKccl:0},g_contextpath);
			location.reload(true);
		}	
	}); 
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
        				$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','考察材料上传',300,400,
                				{a0000:a0000,ynId:ynId,isKccl:1,$tr:$(o),scroll:"scroll:yes;"},g_contextpath);
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
	        				{a0000:a0000,ynId:ynId,isKccl:1,$tr:$(obj)},g_contextpath);
	        	}
			}
		}
	}
})();

$(document).ready(function () {


//主调函数
//setTimeout(function () {
    $('#coordTable').coordTable({
        selecte_col_len: 4, //坐标每行的列数
        selector_row: 'tr.data td:nth-child(2)', //一个jquery选择器，表示可以点击选择行的区域
        selector_td: 'tr.data td:nth-child(n+2)', //一个jquery选择器，表示可以用鼠标拖拽的区域
        appended: complate,
        inserted: complate,
        selectTPRY:doAddPerson.sel,
        updateNRM: function(o){
        	var rowIndex = $(o).index();
        	if(rowIndex>0){
        		var rowId = GetRowid(rowIndex);
        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
        		if(a0000==""||a0000==null){
            		return;
            	}
        		radow.doEvent('updateNRM',a0000);
        	}
        	
        },
        uploadKCCL: uploadKCCL.hasSave,
        deleted: cuted,
        cleared: cuted,
        pasted: complate,
        cuted: cuted,
        create_row: createRow,
        enableCopy: true,
        enablePaste: true,
        enableCut: true,
        enableAppend: true,
        enableInsert: true,
        enableDelete: true,
        enableEdit: true,
        picupload: function (o) { 
        	picupload1(o);
        },        
        saveGDCL: function (o) { 
        	saveGD();
        },
        readGDCL: function (o) { 
        	readGD();
        },        
    	updateData:function(rows){//剪切方法
    		var curCache = [];
    		$.each(rows, function () {
                var rowIndex = $(this).index();
                var rowId = GetRowid(rowIndex);
                GLOBLE['CUTEDROWID'].push(rowId);
                curCache.push(rowId);
              //剪切后的数据 存放对象
                GLOBLE['CUTEDDATA'][rowId]=GLOBLE['ID_ROWINFO'][rowId];
            });
    		$.each(curCache, function (i,item) {
    			//删除id
    			GLOBLE['ROWID'].splice($.inArray(item,GLOBLE['ROWID']),1);
    			delete GLOBLE['ID_ROWINFO'][item];
    		});
    	},
    	rowsPasted:function(o){//粘贴方法
    		var rowIndex = $(o).index();
    	
    		//剪切对象中没有数据直接返回
    		if(GLOBLE['CUTEDROWID'].length==0){
    			return;
    		}
    		if(rowIndex>0){
    			$.each(GLOBLE['CUTEDROWID'],function (i, item){
        			var $tr = createRow(GLOBLE['CUTEDDATA'][item]["type"],rowIndex+i,item,GLOBLE['CUTEDDATA'][item]);
        			$tr.insertBefore($(o));
        		});
    		}else if(rowIndex==0){//追加
				$.each(GLOBLE['CUTEDROWID'],function (i, item){
        			var $tr = createRow(GLOBLE['CUTEDDATA'][item]["type"],rowIndex,item,GLOBLE['CUTEDDATA'][item]);
        			$(o).parent().append($tr);
        		});
    		}
    		
    		//情空缓存
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
    	},
    	rowsDelete : function(rows){//删除行
    		$(rows).each(function(){
    			var rowIndex = $(this).index(); 
    			if(rowIndex==0){//表头
    				return;
    			}
    			//删除id
    			//console.log(GLOBLE); 
    			delete GLOBLE['ID_ROWINFO'][GetRowid(rowIndex)];
    			//GLOBLE['ROWID'].removeIndex(rowIndex-GLOBLE.rowOffset);
    			//alert(GetRowid(rowIndex)+"   "+findDataIndex(GetRowid(rowIndex))+"  "+(rowIndex-GLOBLE.rowOffset));
    			var dataIndex = findDataIndex(GetRowid(rowIndex));
    			GLOBLE['ROWID'].removeIndex(dataIndex);
    			$(this).remove();
        	});
    	}
    });

    $('#coordTable').tableEditer({
        selector: 'tr.data td:nth-child(n+1)',
    	getValue:function(rowIndex,colIndex){
//{"8":["tp0107","tp0108","tp0109","tp0110 tp0111","tp0112","tp0113","tp0114","tp0115]}
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex); 
    		if(colName instanceof Array){//colName是数组
    			var dataJSON = GLOBLE['ID_ROWINFO'][rowId];
    			var text = [dataJSON[colName[0]]||"",dataJSON[colName[4]]||"",dataJSON[colName[5]]||"",dataJSON[colName[8]]||""];
        		return text;
    		}else{
    			//alert(colName + "\n" + JSON.stringify(GLOBLE['ID_ROWINFO'][rowId]));
        		var text = GLOBLE['ID_ROWINFO'][rowId][colName];
        		return text==null?"":text;
    		}
    		
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		//更新数据对象 
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex);  
    		if(colName instanceof Array){//colName是数组  拟任免职务
    			var dataJSON = GLOBLE['ID_ROWINFO'][rowId];
    			/* dataJSON[colName[0]] = value[0];
    			dataJSON[colName[4]] = value[1];
    			dataJSON[colName[5]] = value[2];
    			dataJSON[colName[8]] = value[3]; */ 
    			SetTDtext(obj,value[0]);
    		}else{  
    			SetTDtext(obj,value); 
        		GLOBLE['ID_ROWINFO'][rowId][colName]=value; 
        		if ($(obj).parent().children("td").length<4){ 
        			GLOBLE['ID_ROWINFO'][rowId]["tp0101"]=value; 
        		}
    		}
    		
    	}
    }); 

//}, 50);
  //点击选中行
    $("#coordTable tr.data td:nth-child(2)").live('dblclick', function (e) {
    	if ($(this).parent().children('td').length<4){//标题行
    		return;
    	}
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	} 
    	 
    	if (a0000.indexOf('ZZZZ')>=0){
    		a0000 = a0000.substring(4);
    	} 
    	$("#rmburl").val("");//清空任免表的URL
    	 
    	/**
		eventajax("hasPerson",{"a0000":a0000},function(code){
			if(code==0){
		    	$h.showModalDialog('personInfoOP','http://192.168.181.1:8080'+g_contextpath+'/rmb/ZHGBrmb.jsp?FromModules=1&a0000='+a0000+
		    			'&vid=076C02424269C122AB1D0635D217DB81A37F22A481F485BB55C9F513FE8FA6D5501B9174FFCC2B0D1DDE44D8687B957C5FB44758604ACC9592022A4B2BE8835D7BEA86A63CE866417787F2CCDD0D88B992A889E0BCAAE786146180B12AA07B003363FD5EDA2C969B86C8B1891F757034A0130356E5C334BD3EB2C30616DC1788','人员信息浏览',1009,630,null,
		    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
		    	        return false;
			}else{

		    	$h.alert('系统提示','干部数据库没有该人信息！');
			}
		})   ***/
		 
		eventajax("getRMBLoginURL",{"a0000":a0000},function(code){
			var url = $("#rmburl").val(); 
			if(url !=""){ 
				if (url.toLowerCase().indexOf("http")>=0){ 
					 top.loginTemplateDB(); 
					 setTimeout("toTempDBRMB('"+a0000+"')", 1000);  
				} else {  
		    		$h.showModalDialog('personInfoOP',url,'人员信息浏览',1009,630,null,
		    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
		    	        return false;
				}
			}else{
		    	$h.alert('系统提示','干部数据库没有该人信息！');
			}
		}) 	 	

    });
    
 });
 
 
 function toTempDBRMB(a0000){
	 var url = $("#rmburl").val(); 
 
		$h.showModalDialog('personInfoOP',url,'人员信息浏览',1009,630,null,
    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
    	        return false;	 
 }
function impLrmFilesToZJK(){
	
	top.loginTemplateDB(); 
	setTimeout("ImportLrms2()", 1000);   
	return;
	 
	var isBatch = 1;
	
	eventajax("getPublicServantManage",{"IsBatch":isBatch},function(code){
		var url = $("#rmburl").val();  
		/**
		//var newWin= window.open(url,'newwindow','height=400,width=600,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');//新打开一个空窗口
		***/
		
	    $h.showModalDialog('publicServantManage',url,'信息导入',600,400,null,
	    			{maximizable:false,resizable:false,draggable:false},true);
	     return true;
	}) 	 		 
 }
 
 

 function ImportLrms2(){
	 var url = remoteServer+"/pages/publicServantManage/ImportLrms2.jsp?businessClass=com.picCut.servlet.SaveLrmFile";
	 //var newWin= window.open(url,'newwindow','height='+300+',width='+600+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//新打开一个空窗口
	    $h.showModalDialog('publicServantManage',url,'信息导入',600,400,null,
    			{maximizable:false,resizable:false,draggable:false},true);
	 
 }
 function ImportLrm2(){
	 var url = remoteServer+"/pages/publicServantManage/ImportLrm2.jsp?businessClass=com.picCut.servlet.SaveLrmFile";
	 //var newWin= window.open(url,'newwindow','height='+300+',width='+600+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//新打开一个空窗口
	 $h.showModalDialog('publicServantManage',url,'信息导入',600,400,null,
    			{maximizable:false,resizable:false,draggable:false},true);
 }
 
 function impLrmFileToZJK(){  
	 top.loginTemplateDB(); 
	 setTimeout("ImportLrm2()", 1000);   
	 return;
	 
	var isBatch = 0;
	
	eventajax("getPublicServantManage",{"IsBatch":isBatch},function(code){
		var url = $("#rmburl").val();  
		/**
		//var newWin= window.open(url,'newwindow','height=400,width=600,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');//新打开一个空窗口
		***/
		
	    $h.showModalDialog('publicServantManage',url,'信息导入',600,400,null,
	    			{maximizable:false,resizable:false,draggable:false},true);
	     return true;
	}) 	 		 
 }
 
//删除索引
	Array.prototype.removeIndex = function (index) {
	  if (index > - 1) {
	    this.splice(index, 1);
	  }
	};
	
	//生成UUID
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}
	
	//设置文本信息
	function SetTDtext(td,v) {
		if ($(td).html().toLowerCase().indexOf("img")>=0){
			return; 
		}
		try{
			var colIndex = $(td).index();
			if (v==null) v="";
			//控制籍贯的显示样式
			if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0120"){
				if (v.length == 4){
					//两个字换行
					v = v.substring(0,2) + "<BR/>" + v.substring(2);
				}else if (v.length == 5 || v.length == 6){
					//三个字换行
					v = v.substring(0,3) + "<BR/>" + v.substring(3);
				}
			}else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0119"){
				if (v.indexOf("<")<0){
					var tText = "";
					for (var ii=0;ii<=v.length;ii++){
						tText+=v.substring(ii,ii+1)+"<BR/>";
					}
					v = tText;
				}else {
					v=v.toUpperCase(); 
					while (v.endWith("<BR/>")){
						v = v.substring(0,v.length-5);
					}
					while (v.endWith("<BR>")){
						v = v.substring(0,v.length-4);
					}					
				}
			} else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0102" ||
					GLOBLE['COL_CONFIG_3'][colIndex] == "tp0103" ||
					GLOBLE['COL_CONFIG_3'][colIndex] == "tp0104"){
				if (v.length>7){
					v = v.substring(0,7);
				}
			} else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0101"){
				//姓名为两个字的中间加空格，其它不变
				v = v.replace(' ','');
				if (v.length==2){
					v = v.substring(0,1) +" "+ v.substring(1,2);
				}
			}
			
			
			  $(td).html((v==""||v==null||v=="null")?"":v.replace(/\n/g,"<br/>"));
			  var scrollHeight = $(td)[0].scrollHeight; 
			  if (scrollHeight>=160){ 
				  var fontSize = 15; 
				  while (scrollHeight>=160){
					  var s = "<span style='font-size:"+fontSize+"pt'>"+v.replace(/\n/g,"<br/>") + "</span>";
					  $(td).html((v==""||v==null||v=="null")?"":s);
					  scrollHeight = $(td)[0].scrollHeight;
					  fontSize=fontSize-1; 
				  }
				  $(td).attr("height",160);
				  /**
			  	  var fontSize = 1+parseInt(Math.ceil(15*150.0/scrollHeight));
				  var s = "<span style='font-size:"+fontSize+"pt'>"+v.replace(/\n/g,"<br/>") + "</span>";
				  $(td).html((v==""||v==null||v=="null")?"&nbsp;":s);
				  
				  $(td).attr("height",150);
				  
				  if ($(td).html().toLowerCase().indexOf("img")<0){
				  	$(td).attr("title",v);
				  } */
				  
			  }else{
				  $(td).removeAttr("height");
				  $(td).attr("title","");
			  }
			   
		}catch(e){ 
			alert(e);
		}
	}
	
	//设置照片
	function SetTDPhoto(td,v) {
		var imgId = "personImg"+v.replace(/[-]/g,'');
		$(td).css("font-size:0;cellpading:0px;cellspacing:0px;");
		$(td).attr("height",160);
		$(td).attr("title","");
		$(td).html("<img id='"+imgId+"' style=\"cellpading:0px;cellspacing:0px;border:0px;margin 0px;padding:0px;vertical-align:bottom;display:block;width:100%;height:160\" src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"&centerPhoto=1'/>");
		//{"background-color":"yellow","font-size":"200%"}
		//$(td).css({"background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","background-size":"40px 40px","background-repeat":"no-repeat","background-attachment":"fixed"});
		
		//昃鹏改的，暂时恢复
		//$(td).css({"height":"3.5cm","width":"100%","background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","-moz-background-size":" 100% 100%","-o-background-size":" 100% 100%","-webkit-background-size":" 100% 100%","background-size":" 100% 100%","-moz-border-image":" url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+") 0","background-repeat":"no-repeat","background-image":"none","filter":"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"', sizingMethod='scale')"});
 
	}
	//根据行号获取rowid
	function GetRowid(rowIndex){
		var rowId = $("#coordTable tbody tr:eq("+rowIndex+")").attr("uuid");
		return rowId;
		//alert(rowId+"\n"+GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset]);
		//return GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset];
	}
	
	function findDataIndex(uuid){
		var index=0;
   		$("#coordTable tbody tr").each(function(){
   			if ($(this).attr("uuid")){ 
   				if ($(this).attr("uuid")==uuid){
   					//alert($(this).attr("uuid") + "\n"+uuid+"\n"+index);
   					return index;
   				}
   				index++;
   			}
   		});	
   		return index;
	}
	

	
	
	$(document).ready(function () { 
		$(function(){ 
			$(document).bind('click',function(e){ 
				 var $NiRenMianBianJi = $('.NiRenMianBianJi');
				if($NiRenMianBianJi.css('display')=='none'){
					return;
				}
				var e = e || window.event; //浏览器兼容性 
				var elem = e.target || e.srcElement; 
				
				while (elem) { //循环判断至跟节点，防止点击的是div的id=‘test’的子元素 
					if (elem.id && elem.id=='NiRenMianBianJi') { 
						return; 
					} 
					elem = elem.parentNode; 
				} 
				
				var rowIndex = $NiRenMianBianJi.attr("rowIndex");
				var colIndex = $NiRenMianBianJi.attr("colIndex");
				//分两步  ，一步就很卡
				var $trr = $("#coordTable tr:nth-child("+(rowIndex+1)+")");
				var $td = $("td:nth-child("+(colIndex+1)+")",$trr);
				
				//拟任免对象
				var niRenMianDesc;
				var Ren = $('.Ren textarea').val();
				var Mian = $('.Mian textarea').val();
				var QiTa = $('.QiTa textarea').val();
				if(Ren!=''&&Mian!=''&&QiTa!=''){
					niRenMianDesc = "任" + Ren + "，" + "免" + Mian + "，" + QiTa;
				}else if(Ren!=''&&Mian!=''){
					niRenMianDesc = "任" + Ren + "，" + "免" + Mian 
				}else if(Ren!=''&&QiTa!=''){
					niRenMianDesc = "任" + Ren + "，" + QiTa;
				}else if(Mian!=''&&QiTa!=''){
					niRenMianDesc = "免" + Mian + "，" + QiTa;
				}else if(Ren!=''){
					niRenMianDesc = "任" + Ren;
				}else if(Mian!=''){
					niRenMianDesc = "免" + Mian;
				}else if(QiTa!=''){
					niRenMianDesc = QiTa;
				}else{
					niRenMianDesc = "";
				}
				
	        	var NiRenMian = [niRenMianDesc,Ren,Mian,QiTa];
	        	
	        	//更新数据对象
	            jQuery.tableEditer.setValue($td, NiRenMian,rowIndex,colIndex);
	        	
				$('#NiRenMianBianJi').css('display','none'); //点击的不是div或其子元素 
			});	  

		});
	})
	
 var selectedRow = null;
var doAddPerson = (function(){//增加人员
	var obj = null;
	var coordTable_html;
	return {
		//选择人员
		sel:function(o){ 
			obj = o;
			var ynId = document.getElementById('ynId').value;
			var yntype = $("#yntype").val();
			$("#appointment").val("0");
			if(ynId==''){
				//$h.alert('系统提示','无批次信息！');
				//return;
			}
			$h.openWin('seltpry','pages.xbrm2.SelectTPRY','按姓名/身份证查询 ',1040,520,null,g_contextpath,window,
					{maximizable:false,resizable:false,RMRY:'任免人员',
				ynId:ynId,yntype:yntype});
		},
		
		//正式任命
		appointment : function(){   
			var ynId = document.getElementById('ynId').value;
			var yntype = $("#yntype").val();
			$("#appointment").val("1");
			if(ynId==''){
				//$h.alert('系统提示','无批次信息！');
				//return;
			}
			$h.openWin('seltpry','pages.xbrm2.SelectTPRY','按姓名/身份证查询 ',1040,520,null,g_contextpath,window,
					{maximizable:false,resizable:false,RMRY:'任免人员',
				ynId:ynId,yntype:yntype});
		},
		
		//查询
		queryByNameAndIDS:function(list){//按姓名查询 
			//if(obj){
				radow.doEvent('queryByNameAndIDS',list);
			//}
		},
		
		//增加人员
		addPerson:function(plist){//按姓名查询 插入人员
			var rowIndex = 0;
			if(obj){
				rowIndex = $(obj).index();
			} else {
				rowIndex = 0;
			}
			try{    
				var coordTable_html =  Base64.decode ($("#coordTableHtmlContent").val());
				var isFromSaveHtml = false;
				if (typeof(coordTable_html)==undefined){
					isFromSaveHtml = false;
				} else {
					isFromSaveHtml = (coordTable_html !=null && coordTable_html !="");
				}
				//isFromSaveHtml = false;
				if (isFromSaveHtml){
					//console.log(coordTable_html);   // zouzhilin 2019-09-02
					//console.log(decodeURIComponent(coordTable_html));
					coordTable_html = decodeURIComponent(coordTable_html); 
					$("#coordTable_div").html(coordTable_html); 
					return; 
				}    
	       		if(rowIndex>0){
		   			$.each(plist,function (i, item){
		        		var $tr = createRow(item["type"],rowIndex+i,item["tp0100"],item);
		        		if (!isFromSaveHtml) $tr.insertBefore($(obj));
		   			});
	      		}else if(rowIndex==0){//追加
					$.each(plist,function (i, item){ 
						var $tr = createRow(item["type"],rowIndex,item["tp0100"],item);
						if (!isFromSaveHtml) { 
							$("#coordTable").append($tr);
						}
	          		});
	      		}  
	       		complate(); 
	       		if (!isFromSaveHtml){ 
					jQuery.coordTable.appendDiv();
	       		}
	       		$("#coordTable tbody tr td").each(function(){
	       			if ($(this).html().indexOf("img")<0){
	       				var td = $(this);
	       				SetTDtext(td,$(this).html()); 
	       			} else { 
						console.log($(this).html());
					}
	       		});
	       		$("#coordTable").colResizable();
	       		
				$('tr:eq(0)').find('th:eq(0)').css("width","0"); 
				$('tr').find('td:eq(0)').css("width","0");
				
				obj = null;
			}catch(e){
				alert(e);
			} 
			
		}
		
	}
})();


//计算年龄
function fillCalAgeYear(cal_age_year){
	if (cal_age_year!="" && 
			cal_age_year.indexOf('-')>0){
		var array = cal_age_year.split("-");
		if (array.length == 3){
			$("#NL_year").html(array[0]);
			$("#NL_month").html(array[1]);
			$("#NL_date").html(array[2]);
		}
	}
}

//保存，将页面也同时保存
function save(p){ 
	/***
	$("#coordTable tbody tr").each(function(){
		if ($(this).attr("uuid")){
			alert($(this).index()+"  "+ $(this).attr("uuid"))
		}
	});
	return;***/ 
	var coordTable_html =  Base64.encode(encodeURIComponent($("#coordTable_div").html()));
	$("#coordTableHtmlContent").val(coordTable_html);
 	$("#tb_unit_id").val($("#tb_unit").val()); 
 	$("#cal_age_year").val($("#NL_year").html() + "-" + $("#NL_month").html() +"-"+$("#NL_date").html());
	var ID_ROWINFO = Ext.encode(GLOBLE['ID_ROWINFO']);
	var ROWID = Ext.encode(GLOBLE['ROWID']);
	$('#ID_ROWINFO').val(ID_ROWINFO);
	/**
	$('#ROWID').val(ROWID); 
	alert(ROWID);**/
	
	var ROWID="";
	$("#coordTable tbody tr").each(function(){
		if ($(this).attr("uuid")){
			ROWID+= ",'" + $(this).attr("uuid") + "'";
		}
	});
	if (ROWID != "") ROWID = ROWID.substring(1);
	ROWID = "["+ROWID+"]"; 
	$('#ROWID').val(ROWID);  
	if(p){
		radow.doEvent('save',p);
	}else{
		radow.doEvent('save');
	}
	
}

//归档，上会议通过后进行归档材料
function saveGD(){//归档
	var msgTemplate =  "请输入备注：";  
	Ext.MessageBox.prompt("输入框",msgTemplate,function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 
			 var coordTable_html =  Base64.encode(encodeURIComponent($("#coordTable_div").html()));
			 $("#coordTableHtmlContent").val(coordTable_html);
			 			 
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("saveGD",txt);
		 }
	        
	},this,true); 
}

//读取归档信息
function readGD(){//读档 
	$h.openPageModeWin('gdck','pages.xbrm2.TPBView','干部调配建议方案存档信息',1150,screen.height *0.95,
			{yn_id:$('#ynId').val(),scroll:"scroll:no;"},g_contextpath);
}
function updateNRM(){
	save("updateNRM");
}

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
			
			var $TiaoPeiShiJianY;
			$(".TiaoPeiShiJianYSPAN").bind('click', function(e) {
				$TiaoPeiShiJianY = this;
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
				//alert($($div)[0].outerHTML);
				$(".TiaoPeiShiJianYSPAN").text(tphjtext); 
				if ($($TiaoPeiShiJianY).attr("id") =="TiaoPeiShiJianPrint_year"){
					$($TiaoPeiShiJianY).text(tphjtext);
					$("#tpy").val(tphjid);
				}
			});
		},
		setTPHJSJ_M : function(){
			//调配时间控件
			var $div = $(".TiaoPeiShiJianM");
			var $sel = $("#tp_m");
			$div.hide();
			$sel.focus();
			
			var $TiaoPeiShiJianM;
			$(".TiaoPeiShiJianMSPAN").bind('click', function(e) {
				$TiaoPeiShiJianM = this;
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
				//$(".TiaoPeiShiJianMSPAN").text(tphjtext);
				$($TiaoPeiShiJianM).text(tphjtext);
				$("#tpm").val(tphjid);
			});
		},
		setTPHJSJ_D : function(){
			//调配时间控件
			var $div = $(".TiaoPeiShiJianD");
			var $sel = $("#tp_d");
			$div.hide();
			$sel.focus();
			var $TiaoPeiShiJianD;
			$(".TiaoPeiShiJianDSPAN").bind('click', function(e) {
				$TiaoPeiShiJianD = this;
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
				//$(".TiaoPeiShiJianDSPAN").text(tphjtext);
				$($TiaoPeiShiJianD).text(tphjtext);
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
			
			$("#TiaoPeiShiJianPrint_year").text(y);
			$("#TiaoPeiShiJianPrint_month").text(m);
			$("#TiaoPeiShiJianPrint_date").text(d);
				/*var tphjtext = $('#tp_y').children('option:selected').text();
				$("#TiaoPeiShiJianPrint_year").text(tphjtext);
				alert(y+"-"+m+"-"+d+"  "+tphjlb);
				tphjtext = $('#tp_m').children('option:selected').text();
				$("TiaoPeiShiJianPrint_month").text(tphjtext);
				tphjtext = $('#tp_d').children('option:selected').text();
				alert(y+"-"+m+"-"+d+"  "+tphjlb);
				$("#TiaoPeiShiJianPrint_date").text(tphjtext);
				alert(y+"-"+m+"-"+d+"  "+tphjlb);*/
			tphjtext = $('#yn_type').children('option:selected').text();
			$(".TiaoPeiLeiXingSPAN").text(tphjtext);
			
		}
	}
})();

function clearScreenData(){
	TIME_INIT.clearData();
	setTimeout(function(){save();},100);
}
//字体设置
function fontSet(){ 
	var win = new Ext.Window({
		html : '<button style="margin:10px;"  onclick="$(\'#iframe_fontSet\').attr(\'src\',\''+src+'\');this.disabled=true;">点击开始执行导出</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
		title : '字体设置',
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

//计算年龄
function calNL(){
	//NLJZRQ
	save("savefirst");
	$("#cal_age_year").val($("#NL_year").html() + "-" + $("#NL_month").html() +"-"+$("#NL_date").html());
	radow.doEvent('calNL'); 
}


//导出调配表Excel
function exportTPB(expType){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames=ExpTPB';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出干部调配建议方案 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType},
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

//导出调配表Excel
function exportTPBExcel(){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var expType = "";
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames=ExpTPBExcel';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出干部人员信息一览表 ,请您稍等...');
   	Ext.Ajax.request({
   		timeout: 6000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType},
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
 	 						//alert("文件已经生成！");
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

//通用ajax调用
function eventajax(eventType,params,cbfun){
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames='+eventType;
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
 	      		   try{ 
	      		   		var remoteServerUrl = cfg.selfResponseFunc;
	      		   		$("#rmburl").val(remoteServerUrl);
 	      		   }catch(e){
 	      			   alert(e);
 	      		   }
 	      		   
 				  cbfun(cfg.messageCode)
 				}
      	   }
        }
   });
}


//显示进度条
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
 
function expTable(){
	 
}
 
function CloseAfterPrint(){ 
	try{
		var tata = document.execCommand("print");
		if(tata=tata){ 
			$("#coordTable").css("width","1486px");
			$("#coordTable_div").css("overflow","scroll");
			$("#coordTable_div").css("width","1486px");
			$("#coordTable_div").css("height","500");			    
		} else {
			setTimeout("CloseAfterPrint();",1000);
		}
	}catch(e){
		alert(e);
	}
} 

function printTable11(){
		$("#coordTable").css("width","1486px");
		$("#coordTable_div").css("overflow","hidden");
		$("#coordTable_div").css("width","1486px");
		$("#coordTable_div").css("height","");	 
		$("#coordTable tr td").css("position","");
		  
		CloseAfterPrint();
}
	
function printTable()
{  
	
    var tableToPrint = document.getElementById('coordTable');//将要被打印的表格
    var newWin= window.open(g_contextpath + "/pages/xbrm2/printpreview.jsp",'newwindow','height='+screen.height+',width='+screen.width+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//新打开一个空窗口
    return;
    
    newWin.document.write('<link rel="stylesheet" href= "'+g_contextpath + '/pages/xbrm2/gbtp2print.css">');
    
    newWin.document.write('<p class="BiaoTouP" style="text-align:center">干&nbsp;部&nbsp;信&nbsp;息&nbsp;一&nbsp;览&nbsp;表 </font></p>');
    newWin.document.write('<p class="TiaoPeiShiJian" style="text-align:right">'+$("#TiaoPeiShiJianPrint").html()+'</p>');
    newWin.document.write(' <style media="print"> '+    		
    		'	@page { '+   
    		'   size:landscape;'+ 
    		'   } '+ 
    		' *{margin:0px!important;padding:0px!important;} '+
    		' body{  '+
    		' margin: 1px;overflow-y: scroll;overflow-x: hidden; ' +
    		' font-family:"宋体",Simsun; ' +
    		' word-break:break-all; ' +
    		' margin-bottom: 250px; ' +
    		' overflow-y:hidden; ' +
    		' } ' +
			'    .kcclClass{ ' +
			'    background-color: rgb(102,204,255) !important; ' +
			'    } ' +
			'    .drag_color{ '+
			'    	background-color: rgb(232,232,232) !important; ' +
			'    } '+
			'    .drag_pre_color{ '+
			'    	background-color: rgb(233,250,238)!important; ' +
			'    } '+
			'    .default_color{ '+
			'    	background-color: #FFFFFF !important; ' +
			'    } '+
			'    </style> ');
    newWin.document.write('<body style="overflow-x:auto:overflow-y:auto">');
    newWin.document.write(tableToPrint.outerHTML);//将表格添加进新的窗口
    newWin.document.write('</body>');
    newWin.document.close();//在IE浏览器中使用必须添加这一句
    newWin.focus();//在IE浏览器中使用必须添加这一句 
    remove_ie_header_and_footer(); 
   	
}  
function remove_ie_header_and_footer() {
  	    var hkey_root, hkey_path, hkey_key;
  	    hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
  	    try {
  	        var RegWsh = new ActiveXObject("WScript.Shell");
  	        RegWsh.RegWrite(hkey_path + "header", "");
  	        RegWsh.RegWrite(hkey_path + "footer", "");
  	    } catch (e) {}
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
			//var url=window.location.protocol+"//"+window.location.host+
			//g_contextpath+'/PublishFileServlet;jsessionid='+sid+'?method=downloadFile&uuid='+downloadUUID;
			//wdapp.Documents.Open(url);//打开word模板url
			wdapp.Application.Printout();
		}
	}
  	
  	
})();




//下载
function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src',g_contextpath + '/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
window.onbeforeunload=function(){
	//realParent.infoSearch();
}
  
//Ctrl+S保存
document.onkeydown=function()   {
    if (event.ctrlKey == true && event.keyCode == 83) {//Ctrl+S
        event.returnvalue = false;
        $("tr td textarea").focusout()
        $("#saveid").click();
    	setTimeout(function(){save();},100);
        
    }
 
}

function CopyTable()
{
//整个表格拷贝到EXCEL中
var printArea = document.getElementById("selectable");
try{
	var word =new ActiveXObject("Word.Application");
	var doc =word.Documents.Add("",0,1).Range(0,1);
	var sel=document.body.createTextRange();
	sel.moveToElementText(printArea);
	sel.execCommand("Copy");
	doc.Paste();
	word.Application.Visible = true;
	word=null;
	
} catch (e) {
	alert("导出数据失败，需要在客户机器安装Microsoft Office Word(建议安装2010以后版本)，将当前站点加入信任站点，允许在IE中运行ActiveX控件。");
}
}


var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->'+
    ' <style type="text/css">'+
    '.excelTable  {'+
    'border-collapse:collapse;'+
    ' border:thin solid #999; '+
    '}'+
    '   .excelTable  th {'+
    '   border: thin solid #999;'+
    '  padding:20px;'+
    '  text-align: center;'+
    '  border-top: thin solid #999;'+
    ' '+
    '  }'+
    ' .excelTable  td{'+
    ' border:thin solid #999;'+
    '  padding:2px 5px;'+
    '  text-align: center;'+
    ' }</style>'+'</head><body><table border="1">{table}</table></body></html>'
        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
        var downloadLink = document.createElement("a");
        downloadLink.href = uri + base64(format(template, ctx));
        downloadLink.download = name+".xls";
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    }
})()


	function onAppointment(){  
		try{ 
			doAddPerson.appointment(selectedRow);
		}catch(e){
			alert(1);
		}
	}
 	
 	function onappendouter(){
 		try{
 			$('#coordTable').append();
 		}catch(e){
 			alert(e);
 		}
 	}
 	
 	var objTD = null;
 	function closeChangFontWindow(strHTML){ 
 		try{
 	 		var rowIndex = $(objTD).parent().index();
 	 		var colIndex = $(objTD).index();
 	 		var strtext = $(strHTML).text();
 	 		try{
	    		var colName = "";
	    		if ($(this).parent().children().length<4){
	    			colName = "tp0101";
	    		}else{
	    			colName = GLOBLE['COL_CONFIG_3'][colIndex];
	    		}
	    		var rowId = GetRowid(rowIndex); 
	    		GLOBLE['ID_ROWINFO'][rowId][colName] = strtext;
	    		
 	 		}catch(e){
 	 			alert(e);
 	 		}
	 		var jlfl = Ext.getCmp("changFont");
	 		jlfl.hide();
 		}catch(e){
 			alert(e);
 		}
 	}
 	
 	/**
 	 * 样式调整
 	 * 
 	 */
 	function adjStyle(){
 		var src = g_contextpath + "/pages/xbrm2/ueditor-1.4.3.3/_examples/adjStyle.jsp?text="; 		
 		window.open(src,'fullscreen','fullscreen,toolbar=no,  menubar=no,location=no,   directories=no,   copyhistory=no,  scrollbars=no,   resizable=no,status=no')   ;  
 	}
 	function changFont(obj){ 
 		if (!obj){
 			return;
 		}
 		if ($("#fontCheckBox").val()!="3"){
 			Ext.Msg.alert('系统提示:',"请弹窗模式下调整字体！");
 			return;
 		}
 		
 		objTD = obj;
 		var text = $(obj).html(); 
 		if ($("#fontSetFrame").attr("id")){
 			//$("#fontSetFrame").attr("src",src);
 			var jlfl = Ext.getCmp("changFont");
 			jlfl.show(); 
 			fontSetFrame.editor.html(text);
 			return;
 		} 
 		if (text.indexOf('<')>=0){
 			text = $(text).html();
 		}
 		if (text=="&nbsp;"){
 			text = "";
 		} 		
 		text = encodeURI(text);
 		
 		var src = g_contextpath + "/pages/xbrm2/changefont.jsp?text="+text;
 		var win = new Ext.Window({
 			html : '<iframe id="fontSetFrame" name="fontSetFrame" style="width:100%;height:220" src='+src+'></iframe>',
 			title : '字体设置',
 			layout : 'fit',
 			left:0,
 			top:0,
 			width : 480,
 			height : 220,
 			closeAction : 'close',
 			closable : true,
 			modal : false,
 			id : 'changFont',
 			collapsed:false,
 			collapsible:false,
 			bodyStyle : 'background-color:#FFFFFF',
 			plain : true,
 			titleCollapse:false,
 			listeners:{}     
 		});
 		win.show();	 
 	} 
 	
	function loginTemplateDB(){  
		var u = g_loginTemplate; 
		var url = remoteServer + '/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'ganbu'}"; 
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","myframe");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		} 				
	}  	