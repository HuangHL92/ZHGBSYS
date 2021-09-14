<%@page import="com.insigma.siis.local.pagemodel.pakh.PakhShow"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.SZDWHZB"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
String egl00 = request.getParameter("egl00");
%>
<script type="text/javascript">
Array.prototype.indexOf=function(str){for(var i=0;i<this.length;i++){if(this[i]==str)return i;}}
Array.prototype.indexOfArray=function(str,ti){for(var i=0;i<this.length;i++){if(this[i]){if(this[i][ti]==str)return i;}}}
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var coldata = <%=PakhShow.getColData(egl00)%>; 
var gradedata = <%=PakhShow.getGradeData(egl00)%>;
var b01data = <%=PakhShow.getB01Data(egl00)%>;
var GLOBLE = {};
GLOBLE['colspan_info']={};
//行机构id
GLOBLE['ROWID']=[]
//列 指标id
GLOBLE['COLID']=[]
//根据id存储行信息
GLOBLE['ID_ROWINFO']={};
$(function(){
	//alert(data.length);
	//列合并信息
	GLOBLE['colspan_info']['et00']=coldata.length;
	
	$.each(coldata, function (i,item) {
		if(GLOBLE['colspan_info'][item["etc00"]]){
			GLOBLE['colspan_info'][item["etc00"]]=GLOBLE['colspan_info'][item["etc00"]]+1;
		}else{
			//区县市列加1
			if(i==0){
				GLOBLE['colspan_info'][item["etc00"]]=1+1+1;//多一个总分列
				GLOBLE['colspan_info']['et00'] = GLOBLE['colspan_info']['et00'];
			}else{
				GLOBLE['colspan_info'][item["etc00"]]=1+1;//多一个总分列
				GLOBLE['colspan_info']['et00'] = GLOBLE['colspan_info']['et00']+1;
			}
			
		}
	});
	//设置table宽
	$('#coordTable').css('width',GLOBLE['colspan_info']['et00']*80)
	//标题
	var innerTable = "<tr><th class='borderTop title borderRight' colspan='"+GLOBLE['colspan_info']['et00']+"' >";
	innerTable += coldata[0]['egl04'] + "</th></tr><tr>";
	
	
	var colspanDownCount = 0;
	//大类
	$.each(coldata, function (i,item) {
		
		if(colspanDownCount>0){
			colspanDownCount--;
			return;
		}
		
		//计算colspan
		if(colspanDownCount==0){
			colspanDownCount = GLOBLE['colspan_info'][item["etc00"]];
			
		}
		innerTable += "<th "+((i+1==GLOBLE['colspan_info']['et00'])?"class='borderRight'":"")+" colspan='"+colspanDownCount+"' >";
		innerTable += item['etc01'] +"</th>"
		//多一个总计 要减2     多一个区县列减1
		if(i==0){
			colspanDownCount = colspanDownCount-2-1;
		}else{
			colspanDownCount = colspanDownCount-2;
		}
	});
	
	//结束
	innerTable += "</tr><tr>";
	/* $('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	//$(this).addClass('colorLight');
        }
		
    }); */
    //console.log(innerTable)
    //小类
    colspanDownCount = 0;
    var offest_colspanDownCount=0;
    $.each(coldata, function (i,item) {
    	if(i==0){
    		innerTable += "<th class='thwidthTitle'>单位</th>";
    		offest_colspanDownCount++;
		} 
    	//计算colspan
		if(colspanDownCount==1&&i>0){
			colspanDownCount = GLOBLE['colspan_info'][item["etc00"]];
			//innerTable += "<th class='thwidth'>小计</th>";
			//colspanDownCount--;
			offest_colspanDownCount++
		}else if(colspanDownCount==0){
			colspanDownCount = GLOBLE['colspan_info'][item["etc00"]];
		}
		if(i==0){
			colspanDownCount--;
		} 
    	innerTable += "<th class='thwidth'>";
		innerTable += item['et01'] +"</th>";
		
		colspanDownCount--;
		
		//列对应的指标id
		GLOBLE['COLID'][(i+offest_colspanDownCount)+""] = [item['et00'],item['etc00'],item['et01'],item['et02'],item['et03'],item['et04']];
		
    });
    //innerTable += "<th class='thwidth'>小计</th>";
    innerTable += "</tr>";
    
	//console.log(innerTable)
	
	
	
	
	
	
	
	
	
	
	//区县数据
	$.each(b01data, function (i,item) {
		innerTable += "<tr>";
		var colIdx = 0;
		for(;colIdx<GLOBLE['colspan_info']['et00'];colIdx++){
			if(colIdx==0){
				innerTable += "<td>" + item['b0101'] +"</td>";
			}else if(colIdx==GLOBLE['colspan_info']['et00']-1){
				innerTable += "<td  class='borderRight input-editor' >&ensp;</td>";
			}else{
				//是否打分项
				if(GLOBLE['COLID'][colIdx]){
					innerTable += "<td class='input-editor'>&ensp;</td>";
				}else{
					innerTable += "<td>&ensp;</td>";
				}
				
			}
		}
		innerTable += "</tr>";
		//行机构id  加表头的3行
		GLOBLE['ROWID'][i+3]=item["b01id"];
		GLOBLE['ID_ROWINFO'][item["b01id"]]={};
	});
	
	$('#coordTable').append(innerTable);
	$('#coordTable tr').each(function(i,td){
		$(td).addClass('rebgcolor'+i%2)
	})
	
	//分值  
	//根据id存储行信息
	//GLOBLE['ID_ROWINFO']={};
	$.each(gradedata, function (i,item) {
		
		//获取行
		var b01id = item["b01id"];
		var rowIndex = GLOBLE['ROWID'].indexOf(b01id);
		var et00 = item["et00"];
		var colIndex = GLOBLE['COLID'].indexOfArray(et00,0);
		var td = $('td:eq('+colIndex+')','#coordTable tr:eq('+rowIndex+')');
		SetTDtext(td,item["grade"]);
		
		GLOBLE['ID_ROWINFO'][item["b01id"]][item["et00"]]={'et00':item["et00"],'et01':item["et01"],'et02':item["et02"],'et03':item["et03"],'et04':item["et04"]
			,'eg00':item["eg00"],'b01id':item["b01id"],'grade':item["grade"],'etc00':item["etc00"] }
		updateGrade(td)
	
	});
	
});

function updateGrade(td){
	//获取行
	var rowIndex = $(td).parent().index();
	var colIndex = $(td).index();
	//总分
	var sumGrade = 0;
	var rowId = GetRowid(rowIndex);
	
	//colInfo  [item['et00'],item['etc00'],item['et01'],item['et02'],item['et03'],item['et04']]
	//列配置 大类在小计列时为空 左边为空时是小计 右边为空时是下一个大类
	var colInfo = [],colName='';
	var GradeIndex = colIndex;
	while(typeof(GLOBLE['COLID'][GradeIndex])!= 'undefined'){
		GradeIndex--
	}
	var endIndex = GradeIndex + 1;
	while(typeof(GLOBLE['COLID'][endIndex])!= 'undefined'){
		endIndex++
	}
	
	var countIndex = GradeIndex +1;
	var grade = 0;
	for(;countIndex < endIndex;countIndex++){
		colInfo = GLOBLE['COLID'][countIndex];
		colName = colInfo[0];
		if(GLOBLE['ID_ROWINFO'][rowId][colName]){
			grade = GLOBLE['ID_ROWINFO'][rowId][colName]["grade"];
			if(grade!=''&&grade!=null){
				sumGrade = sumGrade + parseFloat(grade)*parseFloat(colInfo[5])/100;
			}
		}
		
	}
	
	sumGrade = sumGrade.toFixed(2);
	
	
	var td = $('td:eq('+endIndex+')','#coordTable tr:eq('+rowIndex+')');
	SetTDtext(td,sumGrade);
	
}



function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0")?" ":v.replace(/\n/g,"<br/>"));
}

</script>
<style type="text/css">
.rebgcolor1{
	background-color:#F0F0F0;
}
.rebgcolor2{
	background-color:#FFFFFF;
}
#selectable{
	text-align: center;
	width: 100%;
	height: 100%;
	overflow: auto;
}
.pointer{
	cursor: pointer;
}

.colorLight{
	color: red;
}
.input-editor{
	
	padding: 6px 8px;
}
th{
	background: #CAE8EA ; 
	text-align: center;
	padding: 5px;
	
}
.thwidth{
	width: 50px;
	
}
.thwidthTitle{
	width: 200px;
	
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: 宋体;
	font-weight: normal;
	position: relative;
	padding: 8px;
}
td{
	text-align: center;
}
.textleft{
	text-align: left;
}
.title{
	font-family: 方正小标宋简体;
	font-size: 28px;
	font-weight: normal;
}
.borderRight{
	border-right: 1px solid #C1DAD7;
}
.borderTop{
	border-top: 1px solid #C1DAD7;
}

table{
	border-collapse:collapse;
	font-size:12px;
}

</style>
</head>
<body>
<div id="selectable">
	<table id="coordTable" cellspacing="0" cellpadding="0" >
		
	</table>
</div>


<script type="text/javascript">
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}
function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
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




$(function(){
	$(".input-editor").on('click', function (event) {
		var td = $(this);
		var tagName = td.get(0).tagName;;
		if (td.children("textarea").length > 0) {
            return false;
        }
		//行号 zoulei
        var rowIndex = td.parent().index();
        //列号zoulei
        var colIndex = td.index();
      
		var text = GRYP.getValue(rowIndex,colIndex,td);
		//加上padding乘以2
		var width = td.width() +6*2;
        var height = td.height()+8;
        if(tagName=="DIV"){
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
            GRYP.setValue(td, $(this).val(),rowIndex,colIndex);
            
        });
        input.trigger("focus").focusEnd();
	});
});



//根据行号获取rowid
function GetRowid(rowIndex){
	return GLOBLE['ROWID'][rowIndex];
}
var GRYP = (function(){
	return {
		getValue:function(rowIndex,colIndex,$td){
    		var colName = GLOBLE['COLID'][colIndex][0];
    		var rowId = GetRowid(rowIndex);
    		if(GLOBLE['ID_ROWINFO'][rowId][colName]){
    			var text = GLOBLE['ID_ROWINFO'][rowId][colName]["grade"];
        		return text==null?"":text;
    		}else{
    			return "";
    		}
    		
    		
    	},
    	isNumber : function(val) {
    	    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    	    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    	    if(regPos.test(val) || regNeg.test(val)) {
    	        return true;
    	   	} else {
    	        return false;
    		}
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		//判断是不是数字
    		if(value!=''&&!value!=null&&!GRYP.isNumber(value)){
    			alert("非法数字："+value);
    			$('textarea',obj).trigger("focus").focusEnd();
    			return;
    		}
    		
    		
    		//更新数据对象
    		var colInfo = GLOBLE['COLID'][colIndex];
    		//colInfo  [item['et00'],item['etc00'],item['et01'],item['et02'],item['et03'],item['et04']]
    		if(parseFloat(colInfo[3])>parseFloat(value)||parseFloat(colInfo[4])<parseFloat(value)){
				alert("非法数字：值必须在“"+colInfo[3]+"”和“"+colInfo[4]+"”之间!");
				$('textarea',obj).trigger("focus").focusEnd();
    			return;
    		}
    		
    		
    		var colName = colInfo[0];
    		var rowId = GetRowid(rowIndex);
    		
    		SetTDtext(obj,value);
    		if(GLOBLE['ID_ROWINFO'][rowId][colName]){
    			GLOBLE['ID_ROWINFO'][rowId][colName]["grade"]=value;
    		}else{
    			GLOBLE['ID_ROWINFO'][rowId][colName]={};
    			GLOBLE['ID_ROWINFO'][rowId][colName]["grade"]=value;
    			GLOBLE['ID_ROWINFO'][rowId][colName]["b01id"]=rowId;
    			GLOBLE['ID_ROWINFO'][rowId][colName]["et00"]=colInfo[0];
    			GLOBLE['ID_ROWINFO'][rowId][colName]["etc00"]=colInfo[1];
    			GLOBLE['ID_ROWINFO'][rowId][colName]["eg00"]=GUID();
    		}
    		updateGrade(obj);
    		//更新后台
    		ajaxSubmit("saveGrade",GLOBLE['ID_ROWINFO'][rowId][colName]);
    	}
		
	}
})();

function ajaxSubmit(radowEvent,parm,callback){
	  if(parm){
	  }else{
	    parm = {};
	  }
	  parm['egl00']='<%=egl00%>';
	  Ext.Ajax.request({
	    method: 'POST',
	    //form:'rmbform',
	        async: true,
	        params : parm,
	        timeout :300000,//按毫秒计算
	    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.pakh.PakhMaintain&eventNames="+radowEvent,
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



</script>
</body>
</html>
