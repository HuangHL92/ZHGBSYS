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
		for(var j=3;j<=24;j++){//列
			if(j==10){
				continue;
			}
			DCellWeb_tj.SetCellString(
	   				j,i,0,"");
		}
	}
	for(var j=3;j<=24;j++){//列
		if(j==10){
			continue;
		}
		DCellWeb_tj.SetCellString(
   				j,28,0,"");
	}
	radow.doEvent("init");
}
////表格双击事件 
function myMouseDClick(col1, row1) {
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=='10'||col1=="undefined"||col1<3||col1>24){
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
	var cxtj="ssy$"+row1+"$"+col1+"$"+groupid+"$"+year;
	$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\hztj\\J1703105.cll","");
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
	DCellWeb_tj.SetRowhidden(2, 2);
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
    	if(param=='1'){
    		a0221=map1[a0221];//行数
    	}else if(param=='2'){
    		a0221=map2[a0221];//行数
    	}else if(param=='3'){
    		a0221=map3[a0221];//行数
    	}else if(param=='4'){
    		a0221='29';//行数
    	}else if(param=='5'){
    		a0221='30';//行数
    	} else if(param=='6'){
    		a0221='28';//行数
    	}else if (param=='7'){
    		a0221='31'
    	}
    	var i_col=3;
    	DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].heji==0||obj[i].heji=='null')?"":obj[i].heji);//合计
    	i_col++;
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].nv==0||obj[i].nv=='null')?"":obj[i].nv);//女
   		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].sm==0||obj[i].sm=='null')?"":obj[i].sm);//少数民族
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].zd==0||obj[i].zd=='null')?"":obj[i].zd);//中共党员
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].bs==0||obj[i].bs=='null')?"":obj[i].bs);//博士
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].ss==0||obj[i].ss=='null')?"":obj[i].ss);//硕士
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].xs==0||obj[i].xs=='null')?"":obj[i].xs);//学士
		i_col++;
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].yjs==0||obj[i].yjs=='null')?"":obj[i].yjs);//研究生
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].dxbk==0||obj[i].dxbk=='null')?"":obj[i].dxbk);//大学本科
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].dxzz==0||obj[i].dxzz=='null')?"":obj[i].dxzz);//大学专科
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].zz==0||obj[i].zz=='null')?"":obj[i].zz);//中专及以下
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy2==0||obj[i].zhccxy2=='null')?"":obj[i].zhccxy2);//不满2年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?"":obj[i].zhccxy3);//2至不满3年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy4==0||obj[i].zhccxy4=='null')?"":obj[i].zhccxy4);//3至不满4年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?"":obj[i].zhccxy5);//4至不满5年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy6==0||obj[i].zhccxy6=='null')?"":obj[i].zhccxy6);//5至不满6年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy7==0||obj[i].zhccxy7=='null')?"":obj[i].zhccxy7);//6至不满7年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy8==0||obj[i].zhccxy8=='null')?"":obj[i].zhccxy8);//7至不满8年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy12==0||obj[i].zhccxy12=='null')?"":obj[i].zhccxy12);//8至不满12年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy15==0||obj[i].zhccxy15=='null')?"":obj[i].zhccxy15);//12至不满15年
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy16==0||obj[i].zhccxy16=='null')?"":obj[i].zhccxy16);//15年及以上
		
		//showCell();
    }
    if(param=='6'){
   		sumHj();
   		sumZj();
   	} 
}
/* function showCell(){
	for(var i=6;i<=25;i++){
		for(var j=3;j<=24;j++){
			DCellWeb_tj.SetCellShowHide(j,i,0,true);
		}
	}
} */
function sumZj(){
    for(var i=3;i<=24;i++){
    	if(i==10){
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
    	if((temp2+temp1+temp3+temp4+temp5)==0){
    		DCellWeb_tj.SetCellString( i,6,0,'');
    	}else{
	    	DCellWeb_tj.SetCellString( i,6,0,parseInt(temp1)+parseInt(temp2)+parseInt(temp3)+parseInt(temp4)+parseInt(temp5));
    	}
    }
}
function sumHj(){
	for(var i=3;i<=24;i++){
		if(i==10){
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
		'1A50':'22',//科员 
		'1A60':'23',//办事员 
		'1A98':'24',//试用期人员
		'1A99':'25'//其他 
			};
//领导职务
var map2={	
		'1A21':'10',//厅局级正职
		'1A22':'12',//厅局级副职
		'1A31':'14',//县处级正职 
		'1A32':'16',//县处级副职 
		'1A41':'18',//乡科级正职 
		'1A42':'20'//其乡科级副职
			};
//非领导职务
var map3={	
		'1A21':'11',//厅局级正职
		'1A22':'13',//厅局级副职
		'1A31':'15',//县处级正职 
		'1A32':'17',//县处级副职 
		'1A41':'19',//乡科级正职 
		'1A42':'21'//其乡科级副职
			};
</script>