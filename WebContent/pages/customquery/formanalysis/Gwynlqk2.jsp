<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>

<%--用户接受路径参数的值，并保存 --%>
<%@include file="/comOpenWinInit.jsp" %>
<div align="right">
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<odin:dateEdit property="tjtime" label="统计截止时间"></odin:dateEdit>
						<odin:hidden property="timetj"/>
					</tr>
				</table>
			</td>
			<td width="24px"></td>
			<td>
				<odin:button text="统计" handler="tjfunc"></odin:button>
			</td>
			<td width="24px"></td>
			<td>
				<odin:button text="导出" property="imp_excel" handler="exportData"></odin:button>
				<odin:hidden property="jsonString_str1"/>
				<odin:hidden property="jsonString_str2"/>
				<odin:hidden property="jsonString_str3"/>
				<odin:hidden property="jsonString_str4"/>
				<odin:hidden property="jsonString_str5"/>
				<odin:hidden property="jsonString_str6"/>
				<odin:hidden property="jsonString_str7"/>
				<odin:hidden property="groupid"/>
			</td>
		</tr>
	</table>
</div>
<div>
	<object id="DCellWeb1" style="left: 0px; width: 1170px; top: 0px; height: 480px" 
    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
    codebase="softs/cellweb5.cab#version=5,3,8,0429">
    <!-- #version=5.3.9.16 -->
    <param name="_Version" value="65536" />
    <param name="_ExtentX" value="10266" />
    <param name="_ExtentY" value="7011" />
    <param name="_StockProps" value="0" />
    </object>
</div>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
function exportData(){
	DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
	DCellWeb_tj.ExportExcelDlg();
}
</script>
<script type="text/javascript">
var ctpath="<%=request.getContextPath()%>";
function tjfunc(){
	DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
	for(var i=6;i<=25;i++){//行
		for(var j=3;j<=27;j++){//列
			DCellWeb_tj.SetCellString(
	   				j,i,0,"");
		}
	}
	radow.doEvent("initx");
}
////表格双击事件 
function myMouseDClick(col1, row1) {
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3||col1>27){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<6||row1>31){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	var groupid=document.getElementById("groupid").value;
	var year=document.getElementById("timetj").value;
	var cxtj="gwynl2$"+row1+"$"+col1+"$"+groupid+"$"+year;
	$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\gwynlqk2.cll","");
		//document.getElementById("DCellWeb1").InsertRow(28,1,0);
		document.getElementById("DCellWeb1").InsertCol(28,1,0);
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
 /*    DCellWeb_tj.SetRowhidden(29, 29);
    DCellWeb_tj.SetRowhidden(30, 30);
    DCellWeb_tj.SetRowhidden(5, 5);*/
    DCellWeb_tj.SetRowhidden(2, 2);
    DCellWeb_tj.SetColhidden(28, 28); 
    DCellWeb_tj.SetRowhidden(28, 28); 
    var obj ="";
    try{
	    obj = eval('(' + jsonstr + ')');
    }catch(err){
    }
   
    for(var i=0;i<obj.length;i++){////插入数据 行数 循环
    	var a0221=obj[i].a0221;//代码
   	    if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   	    	if(param=='6'||param=='4'||param=='5'||param=='7'){
   	    		
	    		}else{
				continue;//类别代码为空
	    		}	
   	    }
   	 
    	if(param=='3'){
	   		a0221=map2[a0221];//行数	
	   	}else if(param=='4'){
	   		a0221=28;
	   	}else if(param=='5'){
    		a0221='29';//行数
    	} else if(param=='6'){
    		a0221='30';//行数
    	}else if (param=='7'){
    		a0221='31';
    	}else{
	   		a0221=map1[a0221];//行数
	   	}
    	
    	var i_col=3;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy44==0||obj[i].dy44=='null')?"":obj[i].dy44);//
	   	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy45==0||obj[i].dy45=='null')?"":obj[i].dy45);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy46==0||obj[i].dy46=='null')?"":obj[i].dy46);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy47==0||obj[i].dy47=='null')?"":obj[i].dy47);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy48==0||obj[i].dy48=='null')?"":obj[i].dy48);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy49==0||obj[i].dy49=='null')?"":obj[i].dy49);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy50==0||obj[i].dy50=='null')?"":obj[i].dy50);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy51==0||obj[i].dy51=='null')?"":obj[i].dy51);//
			i_col=i_col+1;	
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy52==0||obj[i].dy52=='null')?"":obj[i].dy52);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy53==0||obj[i].dy53=='null')?"":obj[i].dy53);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy54==0||obj[i].dy54=='null')?"":obj[i].dy54);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy55==0||obj[i].dy55=='null')?"":obj[i].dy55);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy56==0||obj[i].dy56=='null')?"":obj[i].dy56);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy57==0||obj[i].dy57=='null')?"":obj[i].dy57);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy58==0||obj[i].dy58=='null')?"":obj[i].dy58);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy59==0||obj[i].dy59=='null')?"":obj[i].dy59);//
			i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy60==0||obj[i].dy60=='null')?"":obj[i].dy60);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy61==0||obj[i].dy61=='null')?"":obj[i].dy61);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy62==0||obj[i].dy62=='null')?"":obj[i].dy62);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy63==0||obj[i].dy63=='null')?"":obj[i].dy63);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy64==0||obj[i].dy64=='null')?"":obj[i].dy64);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy65==0||obj[i].dy65=='null')?"":obj[i].dy65);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dydy66==0||obj[i].dydy66=='null')?"":obj[i].dydy66);//
		  	i_col=i_col+1;
		  DCellWeb_tj.SetCellString( i_col,a0221,0,(obj[i].nlzs==0||obj[i].nlzs=='null')?"":obj[i].nlzs);//
		  	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
			   		i_col,a0221,0,(obj[i].pjnl==0||obj[i].pjnl=='null')?"":obj[i].pjnl);//
   		i_col=i_col+1;
		DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].heji_zs==0||obj[i].heji_zs=='null')?"":obj[i].heji_zs);//
    }
     if(param=='3'){
   		sumHj();
   	} 
    if(param='7'){
    	sumZJ();
    	averageAge();
    }
}


function sumZJ(){
	for(var i=3;i<=28;i++){
		if(i==27){
			continue;
		}
		var temp1=DCellWeb_tj.GetCellString(i,7,0);
		var temp2=DCellWeb_tj.GetCellString(i,28,0);
		var temp3=DCellWeb_tj.GetCellString(i,29,0);
    	var temp4=DCellWeb_tj.GetCellString(i,30,0);
    	var temp5=DCellWeb_tj.GetCellString(i,31,0);
		if(temp1==""||temp1==" "||temp1==null||temp1=='undefined'){
			temp1=0;
		}
		if(temp2==""||temp2==" "||temp2==null||temp2=='undefined'){
			temp2=0;
		}
		if(temp3==""||temp3==" "||temp3==null||temp3=='undefined'){
    		temp3=0;
    	}
    	if(temp4==""||temp4==" "||temp4==null||temp4=='undefined'){
    		temp4=0;
    	}
    	if(temp5==""||temp5==" "||temp5==null||temp5=='undefined'){
    		temp5=0;
    	}
		var sum=parseInt(temp1)+parseInt(temp2)+parseInt(temp3)+parseInt(temp4)+parseInt(temp5);
		if(sum==0){
			sum='';
		}
		DCellWeb_tj.SetCellString( i,6,0,sum);
	}
}
function averageAge(){
	var HJRS=DCellWeb_tj.GetCellString(28,7,0);
	var HJNLZS=DCellWeb_tj.GetCellString(26,7,0);
	if(HJRS==""||HJRS==" "||HJRS==null||HJRS=='undefined'||HJNLZS==""||HJNLZS==" "||HJNLZS==null||HJNLZS=='undefined'){
		DCellWeb_tj.SetCellString(
		   		27,7,0,"");//
	}else{
		DCellWeb_tj.SetCellString(
		   		27,7,0,returnFloat(Math.round(HJNLZS/HJRS)));//
	}
	
	
	var ZJRS=DCellWeb_tj.GetCellString(28,6,0);
	var ZJNLZS=DCellWeb_tj.GetCellString(26,6,0);
	if(ZJRS==""||ZJRS==" "||ZJRS==null||ZJRS=='undefined'||ZJNLZS==""||ZJNLZS==" "||ZJNLZS==null||ZJNLZS=='undefined'){
		DCellWeb_tj.SetCellString(
		   		27,6,0,"");//
	}else{
		DCellWeb_tj.SetCellString(
		   		27,6,0,returnFloat(Math.round(ZJNLZS/ZJRS)));//
	}
}

function returnFloat(value){
	 var xsd=value.toString().split(".");
	 if(xsd.length==1){
	 value=value.toString()+".00";
	 return value;
	 }
	 if(xsd.length>1){
	 if(xsd[1].length<2){
	 value=value.toString()+"0";
	 }
	 return value;
	 }
	}
function sumHj(){
	for(var i=3;i<=28;i++){
		if(i==27){
			continue;
		}
		var sum=colSum(i);
		DCellWeb_tj.SetCellString( i,7,0,sum);
	}
}
function colSum(col){
	var sum=0;
	for(var i=8;i<=25;i++){
		var temp=DCellWeb_tj.GetCellString(col,i,0);
		if(temp!=""&&temp!=" "&&temp!=null&&temp!='undefined'){
			sum=sum+parseInt(temp);
		}
	}
	if(sum==0){
		return '';
	}else{
		return sum;
	}
}
var map1={	
		'1A11':'8',//省部级正职
		'1A12':'9',//省部级副职
		'1A21':'10',//厅局级正职领导职务
		'1A22':'12',//厅局级副职领导职务
		'1A31':'14',//县处级正职领导职务
		'1A32':'16',  // 县处级副职领导职务
		'1A41':'18',  // 乡科级正职领导职务
		'1A42':'20',  // 乡科级副职领导职务
		'1A50':'22',  // 科员
		'1A60':'23', //办事员
		'1A98':'24',  // 试用期人员
		'1A99':'25'   //其他
			};	
var map2={
		'1A21':'11',//厅局级正职非领导职务
		'1A22':'13',	//厅局级副职非领导职务
		'1A31':'15',	//县处级正职非领导职务
		'1A32':'17',	//县处级副职非领导职务
		'1A41':'19',	//乡科级正职非领导职务
		'1A42':'21'	//乡科级副职非领导职务
};    
</script>          
                 