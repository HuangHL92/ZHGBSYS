<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style>
.pointer{
	cursor: pointer;
}
.colorLight{
	color: red;
}
th{
	background: #CAE8EA ; 
	text-align: center;
	height:50px;
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: SimHei;
	font-weight: normal;
	height:50px;
}
td{
	text-align: center;
	height:50px;
}
.textleft{
	text-align: center;
}
.borderRight{
	border-right: 1px solid #C1DAD7;
}
.borderTop{
	border-top: 1px solid #C1DAD7;
}
.top_btn_style{
	float:left;
	display:block;
	border-radius:5px;
	cursor:pointer;
	margin-left:6px;
	height:25px;
	line-height:14px;
	vertical-align:middle;
	font-size:12px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
	padding: 3px 5px!important;
}
#selectable{
	height:800px;
}
#coordTable{
	min-width: 700px;
	margin: 15px;
}

#jgDiv td{
	border:none
}

#coordTable td,th{
	border:1px solid;
	border-color: #74A6CC;
}

.name{
	cursor:pointer;
} 
.dbwy{
	cursor:pointer;
}
.bz{
	cursor:pointer;
}
.unit{
	cursor:pointer;
} 
.hovercolor{
	background-color:#C0C0C0 !important;
} 
.th10{width: 10%;}.th6{width: 6%;}
.th7{width: 7%;}.th9{width: 9%;}.th11{width: 11%;}
}
.hovercolor{
	background-color:#C0C0C0 !important;
} 

.nrzwwidth{
	width: 100px!important;
}
</style>
<odin:hidden property="b0111"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="b01id"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="data"/>
<odin:hidden property="parentfabd00"/>
<div  style="align:left top;overflow:auto;"  id="selectable">
<div style="width: 12%;height:8%;float: left;">
<odin:select2 property="fabd00" label="&nbsp;&nbsp;选择比对方案："  ></odin:select2>
</div>
<!-- <div style="width: 10%;height:8%;float: left;vertical-align:middle;">
<br/>
	<input type="checkbox" id="showAll"  checked='true'/>
    <span style="font-family: SimHei;">显示调配人员</span>
</div> -->

<div style="width: 10%;height:8%;float: left;">
	<button type='button' onclick="query()" style='margin-top:20px'>查看</button>
</div>
<div style="width: 10%;height:8%;float: left;">
	<button type='button' onclick="openBDFAPage()" style='margin-top:20px'>修改比对方案</button>
</div>
<div style="width: 10%;height:8%;float: left;">
	<button type='button' onclick="openBDFAADDPage()" style='margin-top:20px'>新增比对方案</button>
</div>
<div style="clear: both; text-align: center; width: 100%" ></div>
<div style="display: table; text-align: center;margin:auto " >

	<table id="coordTable" cellspacing="0"   >

		
	</table>
	
</div>
<div align="right">
					<div class="top_btn_style" 
					 style="background-color:#F08000;  
					  line-height:25px;float:right;" onclick="getTabArray()">导出excel
					</div>
					</div>

</div>
<script type="text/javascript">




var B0131DECODE = {"1":"正职","3":"副职",'1001':'党委','1004':'政府','1003':'人大','1005':'政协','1006':'院长','1007':'检查长'};
Ext.onReady(function() {
	
	
	/* $('#b0111').val(parent.Ext.getCmp(subWinId).initialConfig.b0111);
	$('#b01id').val(parent.Ext.getCmp(subWinId).initialConfig.b01id);
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$('#mntp05').val(parent.Ext.getCmp(subWinId).initialConfig.mntp05); */
	if(parent.Ext.getCmp(subWinId).initialConfig.fabd00){
		$('#parentfabd00').val(parent.Ext.getCmp(subWinId).initialConfig.fabd00);
		
	}
	var viewSize = Ext.getBody().getViewSize();
	var height=viewSize.height;
	$("#selectable").css('height',height-40);
	$("#selectable").css('width',viewSize.width);
	
});
function openBDFAPage(){
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择比对方案');
		return;
	}
    $h.openWin('MNTPlist','pages.gwdz.MNTPlist','对比方案列表',900,570,null,'<%= request.getContextPath() %>',null,
      {fabd00:fabd00},true);
}
function openBDFAADDPage(){
	var fabd00 = $('#fabd00').val();
    $h.openWin('MNTPlist','pages.gwdz.MNTPlist','对比方案列表',900,570,null,'<%= request.getContextPath() %>',null,
      {fabd00:''},true);
}

function query(){
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择比对方案');
		return;
	}
	radow.doEvent('showData');
}

function showData(tableData){
	var html = '';
	$.each(tableData, function (i,rowsData) {
		html = html + '<tr>';
		$.each(rowsData, function (j,cellMap) {
			html = html + '<td '+(cellMap['rowspan']?'rowspan="'+cellMap['rowspan']+'"':' ')
			+(cellMap['colspan']?'colspan="'+cellMap['colspan']+'"':' ')
			+(cellMap['sclass']?'class="'+cellMap['sclass']+'"':' ')
			+(cellMap['style']?'style="'+cellMap['style']+'"':' ')
			+(cellMap['a0000']?'a0000="'+cellMap['a0000']+'"':' ')
			+(cellMap['personStatus']?'personStatus="'+cellMap['personStatus']+'"':' ')
			+(cellMap['qx']?'qx="'+cellMap['qx']+'"':' ')
			+'>'+cellMap['text']+'</td>';
		})
		html = html + '</tr>\n';
	});
	$('#coordTable').html(html);
	
	//
}

function setTableWidth(w){
	$('#coordTable').width(w);
	$('.titleColor').css('background-color','rgb(202,232,234)');
	
	
	$('.name').bind('click',function(){
		var a0000=$(this).attr("a0000")
		if(a0000==null || a0000==''){
			return;
		}
	  $h.openPageModeWin('openTPRmb','pages.fxyp.TPRYXXZS','人员信息',1000,800,{a0000:a0000,location:'1'},'<%=request.getContextPath()%>'); 
	});
	
	$('.name,.unit').bind('mouseover',function(){
		  var a0000=$(this).attr("a0000");
		  var b0101=$(this).attr("qx");
		  var html=$(this).innerHTML;
		  if(b0101==null  && html==null){
			  if(a0000==null || a0000==''){
				  $(this).css('cursor','default');
				  return;
			  }	 
		  }
		  $(this).addClass("hovercolor"); 
	  });
	  $('.name,.unit').bind('mouseout',function(){
		  $(this).removeClass("hovercolor"); 
	  });
	  
	  
	  $(".name").each(function(){
		  var personStatus=$(this).attr("personStatus");
		  if(personStatus==-1){
			  $(this).css('background-color','#CAFFFF');
		  }else if(personStatus==1){
			  $(this).css('background-color','#FFC1E0');
		  }
	  });
	  
	  $('.unit').bind('click',function(){
			 var b0111=$(this).attr("qx");
			 var b0101=$(this).text();
			 if(b0111==null || b0111==''){
				 return;
			 }
			 var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
			 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
			 var url = "http://"+ip+":"+port+"/ngbdp/team/?code="+b0111+"&name="+b0101+"&hasback=false"; 
			$h.showWindowWithSrc("BZFX",url,"班子分析", 1500, 1200,null,{closeAction:'close'},true,true);
			}); 
	
}

function getTabArray() {
	var tableObj = document.getElementById('coordTable');
	var maxrow=tableObj.rows.length;
	var maxcell=0;
	for (var i = 0; i <maxrow; i++) { 
		var maxcellx=tableObj.rows[i].cells.length;
		for(var j=0;j<tableObj.rows[i].cells.length;j++){
			var displays=tableObj.rows[i].cells[j].style.display;//多少列
			   if(displays=="none"){
				   maxcellx=maxcellx-1;
			   }	
		}
		if(maxcellx>maxcell){
			maxcell=maxcellx;
		}
	} 
    var data = [];
    if ($("#coordTable tr").length == 0) {
        return data;
    }
    for(var n=0;n<maxrow;n++){
    	var arr=[];
		for(var m=0;m<maxcell;m++){
    		arr.push({'text':' '});
    	}
    	data.push(arr);
    }
    //填充数组
    var ii=0;
    $("#coordTable tr").each(function () {
        var jj=0;
        $(this).children('th,td').each(function () {
        	if($(this).css('display')!="none"){
        	var db=[];
        	db.push({'text':'x'});
        	while(data[ii][jj].text==db[0].text){
        		jj=jj+1;
        	}
            data[ii][jj]=({'text':$(this).text().trim(),'rowspan':$(this).attr("rowspan"),'colspan':$(this).attr("colspan"),'color':$(this).css("background-color")});
            if($(this).attr('rowspan')){
            	for(var rows=0;rows<$(this).attr('rowspan');rows++){
                    if ($(this).attr('colspan')) {
                    	for (var cells = 0;cells< parseInt($(this).attr('colspan'));cells++) {
                    		if(rows!=0||cells!=0){
                    		data[ii+rows][jj+cells]=({'text':'x'});
                    		}
                         }
                	}else{
                		if(rows!=0){
                    	data[ii+rows][jj]=({'text':'x'});
                    	}
                	}  
            	}

            }else{
                if ($(this).attr('colspan')) {
                     for (var cells = 1;cells< parseInt($(this).attr('colspan'));cells++) {
                    	 data[ii][jj+cells]=({'text':'x'});
                      }
             		
               }  
            	
            }
            if ($(this).attr('colspan')) {
            jj=jj+parseInt($(this).attr('colspan'))-1;
            }  
            //alert(jj);
			jj=jj+1;
			}
        });
        ii=ii+1;
    });
	 if(data.length>0){
		  ajaxSubmit('expExceldata',{"data":Ext.encode(data),"excelname":"方案比对"});
	}
}

function ajaxSubmit(radowEvent,parm,callback){
	  if(parm){
	  }else{
	    parm = {};
	  }
	  Ext.Ajax.request({
	    method: 'POST',
	    //form:'rmbform',
	        async: true,
	        params : parm,
	        timeout :300000,//按毫秒计算
	    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.hzb.ExpExcel&eventNames="+radowEvent,
	    success: function(resData){
	      var cfg = Ext.util.JSON.decode(resData.responseText);
	      //alert(cfg.messageCode)
	      if(0==cfg.messageCode){
	                Ext.Msg.hide();

	                if(cfg.elementsScript.indexOf("\n")>0){
	          cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
	          cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
	        }

	        //console.log(cfg.elementsScript);

	        eval(cfg.elementsScript);
	        //var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
	        //parent.document.location.reload();
	        //alert(cfg.elementsScript);
	        //realParent.resetCM(cfg.elementsScript);
	        //parent.Ext.getCmp("setFields").close();
	        //console.log(cfg.mainMessage);

	        if("操作成功！"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('系统提示:',cfg.mainMessage);

	        }else{
//	           Ext.Msg.hide();
	        }
	      }else{
	        //Ext.Msg.hide();

	        /* if(cfg.mainMessage.indexOf("<br/>")>0){

	          $h.alert('系统提示',cfg.mainMessage,null,380);
	          return;
	        } */

	        if("操作成功！"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('系统提示:',cfg.mainMessage);
	        }else{
	          Ext.Msg.hide();
	        }
	      }
	      if(!!callback){
	        callback();
	      }
	    },
	    failure : function(res, options){
	      Ext.Msg.hide();
	      alert("网络异常！");
	    }
	  });
	}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
</script>
