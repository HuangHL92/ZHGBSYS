<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:head/>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/print.css"/>

</head>
<body>
<div id="header" class="noprint">
	<table width="100%" id="headTab">
		<tr>
			<td width="80%"></td>
			<td align="center"><odin:button text="&nbsp;&nbsp;打印&nbsp;&nbsp;',iconCls:'printBtnIcon',scale: 'large"  handler="doPrint"/></td>
		</tr>
	</table>
</div>
<div id="title" ></div>
<div id="content"></div>
</body>
<script type="text/javascript">
<!--
var gridId='<%=request.getParameter("gridId")%>';
var isPrintAll = <%=request.getParameter("isPrintAll")%>;
var functionid = '<%=request.getParameter("functionid")%>';
var html = "<table id='grid' cellspace=0 margin=0 border=0 width='96%'>";
html += "<tr id='gridHead' class=odd>";
var emptyColInfo = {};
var emptyColTitleInfo = {};
Ext.onReady(
	function(){
		document.title = "通用表格打印";
		var g = parent.odin.ext.getCmp(gridId);
		var s = g.store;
		var v = g.getView();
		var cm = g.getColumnModel();
		var headNames = {};
		var title = null, dataIndex=null;
			
		for(var i=0;i<cm.getColumnCount();i++){
			title = cm.getColumnHeader(i);
			dataIndex = cm.getDataIndex(i);
			
			if(cm.isHidden(i)){
				emptyColInfo[dataIndex] = true;
				emptyColTitleInfo[title] = true;
				continue;
			}
			if(title.indexOf("x-grid3-check-col-td")>0){
				emptyColInfo[dataIndex] = true;
				emptyColTitleInfo[title] = true;
				title = "";
				continue;
			}
			html += "<td align=center nowrap>"+title+"</td>";
			
			headNames[dataIndex]= title;
		}
		html += "</tr>";
		if(!isPrintAll){ //打印当前页
			for(var i=0;i<s.getCount();i++){
				html += "<tr>";
				
				for(var j=0;j<cm.getColumnCount();j++){
					title = cm.getColumnHeader(j);
					dataIndex = cm.getDataIndex(j);
					
					if(emptyColTitleInfo[title]===true){
						continue;
					}
					html += "<td align=center nowrap>"+v.getCell(i,j).innerText+"</td>";
				}
				html += "</tr>";
			}
		
		    if(s.getCount()> 0 && v.statiKey !== undefined){
		    	html += "<tr>";
		    	var sv = null;
		    	
		    	for(var k=0;k<cm.getColumnCount();k++){
		    		title = cm.getColumnHeader(k);
					dataIndex = cm.getDataIndex(k);
					
					if(emptyColTitleInfo[title]===true){
						continue;
					}
					
					sv = v.sRecord.data[dataIndex];
					sv = sv===undefined||sv===null?"":sv;
					
					html += "<td align=right nowrap>"+sv+"</td>";
		    	}
		    	
		    	html += "</tr>";
		    }
		    
			html += "</table>";
			document.getElementById("content").innerHTML = html;
			fillTitle();
		}else{
			var url = contextPath+"/sys/PrintAction.do?method=getPrintAllData";
			var param = {};
			param.gridId = gridId;
			param.functionid = functionid;
			param.headNames = Ext.encode(headNames);
			odin.Ajax.request(url,param,doFillPrintData,doFail,true,true);
			
		}
	}
);
function doPrint(){
	window.print();
}
function doFail(res){
	odin.error(res.mainMessage,function(){
		parent.odin.ext.getCmp("win_pup").hide();
	});
}
function doFillPrintData(res){
	var data = res.data;
	var g = parent.odin.ext.getCmp(gridId);
	var s = g.store;
	var v = g.getView();
	var cm = g.getColumnModel();
	var title = null;
	var dataIndex = null;
	var cueDataIndex, cell;
	var sumobj = null;
	
	if(data.length>0 && v.statiKey !== undefined){
		sumobj = data.pop();
	}
	
	for(var i=0;i<data.length;i++){
		html += "<tr>";
		for(var j=0;j<cm.getColumnCount();j++){
			title = cm.getColumnHeader(j);
			dataIndex = cm.getDataIndex(j);
			
			if(emptyColTitleInfo[title]==true){
				continue;
			}
			
			cueDataIndex = dataIndex;
			cell = "";
			
			if(cueDataIndex==""){
				cell = (i+1);
			}else if(cueDataIndex!=""){
				cell = data[i][cueDataIndex];
			}
			
			if(!cell || cell==null){
				cell = "";
			}
			
			html += "<td align=center nowrap>"+cell+"</td>";
		}
		html += "</tr>";
	}
	
	if(sumobj != null){
		html += "<tr>";
    	var sv = null;
    	
    	for(var k=0;k<cm.getColumnCount();k++){
    		title = cm.getColumnHeader(k);
			dataIndex = cm.getDataIndex(k);
			
			if(emptyColTitleInfo[title]===true){
				continue;
			}
			
			sv = sumobj[dataIndex];
			sv = sv===undefined||sv===null?"":sv;
			
			html += "<td align=right nowrap>"+sv+"</td>";
    	}
    	
    	html += "</tr>";
	}
	
	html += "</table>";
	document.getElementById("content").innerHTML = html;
	fillTitle();
}

function fillTitle(){
	var g = parent.odin.ext.getCmp(gridId);
	var cm = g.getColumnModel();	
	var allmsg="";
	var title="";
	var left="";
	var right="";
	try{
		title=parent.document.getElementById('com_print_title').value;
	}catch(exception){}
	try{
		left=parent.document.getElementById('com_print_left').value;
	}catch(exception){}
	try{
		right=parent.document.getElementById('com_print_right').value;
	}catch(exception){}
	var count=cm.getColumnCount();
	var width=document.getElementById("grid").clientWidth;
	if(title!=""){
		allmsg="<table width='"+width+"' ><tr height='40' ><td width='100%'  colspan='"+count+"' align='center'><P id='real_title'><H2 >"+title+"<H2></P></td></tr></table>";
	}
	var colspan=count- (count/2).toFixed(0);
	
	allmsg=allmsg+"<table width='"+width+"' ><tr><td  colspan='"+(count/2).toFixed(0)+"' align='left'>&nbsp;&nbsp;&nbsp;"+left+"</td><td   align='right' colspan='"+colspan+"'>"+right+"</td></tr></table>";
	
	document.getElementById("title").innerHTML = allmsg;
}
-->
</script>
</html>