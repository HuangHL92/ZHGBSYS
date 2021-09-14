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
	for(var i=6;i<=29;i++){//行
		for(var j=3;j<=24;j++){//列
			if(j==10){
				continue;
			}
			DCellWeb_tj.SetCellString(
	   				j,i,0,"");
		}
	}
	radow.doEvent("init");
}
////表格双击事件 
function myMouseDClick(col1, row1) {
//参数错误 
if(col1==0||col1==""||col1==" "||col1==null||col1=='10'||col1=="undefined"||col1<3||col1>24){
	return;
}
if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<6||row1>29){
	return;
}
//数值为0 或 空
var value=DCellWeb_tj.GetCellString(col1,row1,0);
if(value==0||value==""||value==" "||value==null||value=="undefined"){
	return;
}
var groupid=document.getElementById("groupid").value;
var year=document.getElementById("timetj").value;
var cxtj="sse$"+row1+"$"+col1+"$"+groupid+"$"+year;
$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\hztj\\J1703106.cll","");
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
	DCellWeb_tj.SetRowhidden(2, 2);
    var obj ="";
    try{
	    obj = eval('(' + jsonstr + ')');
    }catch(err){
    }
    for(var i=0;i<obj.length;i++){////插入数据 行数 循环
    	var a0221=obj[i].a0221;//代码
   	    if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   	    	if(param=='5'){
   	    		
   	    	}else{
   				continue;//类别代码为空
   	    	}
   	    }
    	if(param=='1'){
    		a0221='6';//行数
    	}else if(param=='2'){
    		a0221=map2[a0221];//行数
    	}else if(param=='3'){
    		a0221='16';//行数
    	}else if(param=='4'){
    		a0221=map4[a0221];//行数
    		json1_func(a0221,obj,i);
    		continue;
    	}else if(param=='5'){
    		a0221='29';//行数
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
		
    }
    if(param=='5'){
   		sumHj1();
   		sumHj2();
   	}
}
function sum(temp1,temp2){
	if(temp1==""||temp1=='undefined' ||temp1==null||temp1==" "){
		temp1=0;
	}
	if(temp2==""||temp2=='undefined' ||temp2==null||temp2==" "){
		temp2=0;
	}
	var num=(parseInt(temp1)+parseInt(temp2));
	if(num==0){
		return '';
	}
	return num;
}
function json1_func(a0221,obj,i){
	var i_col=3;
	var temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	
	var temp2=(obj[i].heji==0||obj[i].heji=='null')?"":obj[i].heji;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//合计
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].nv==0||obj[i].nv=='null')?"":obj[i].nv;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//女
		i_col++;
		temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
		temp2=(obj[i].sm==0||obj[i].sm=='null')?"":obj[i].sm;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//少数民族
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zd==0||obj[i].zd=='null')?"":obj[i].zd;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//中共党员
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].bs==0||obj[i].bs=='null')?"":obj[i].bs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//博士
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].ss==0||obj[i].ss=='null')?"":obj[i].ss;		
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//硕士
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].xs==0||obj[i].xs=='null')?"":obj[i].xs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//学士
	i_col++;
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].yjs==0||obj[i].yjs=='null')?"":obj[i].yjs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//研究生
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].dxbk==0||obj[i].dxbk=='null')?"":obj[i].dxbk;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//大学本科
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].dxzz==0||obj[i].dxzz=='null')?"":obj[i].dxzz;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//大学专科
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zz==0||obj[i].zz=='null')?"":obj[i].zz;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//中专及以下
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy2==0||obj[i].zhccxy2=='null')?"":obj[i].zhccxy2;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//不满2年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?"":obj[i].zhccxy3;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//2至不满3年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy4==0||obj[i].zhccxy4=='null')?"":obj[i].zhccxy4;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//3至不满4年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?"":obj[i].zhccxy5;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//4至不满5年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy6==0||obj[i].zhccxy6=='null')?"":obj[i].zhccxy6;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//5至不满6年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy7==0||obj[i].zhccxy7=='null')?"":obj[i].zhccxy7;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//6至不满7年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy8==0||obj[i].zhccxy8=='null')?"":obj[i].zhccxy8;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//7至不满8年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy12==0||obj[i].zhccxy12=='null')?"":obj[i].zhccxy12;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//8至不满12年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy15==0||obj[i].zhccxy15=='null')?"":obj[i].zhccxy15;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//12至不满15年
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy16==0||obj[i].zhccxy16=='null')?"":obj[i].zhccxy16;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//15年及以上
}
function sumHj2(){
	for(var i=3;i<=24;i++){
		if(i==10){
			continue;
		}
		var sum=colSum2(i);
		DCellWeb_tj.SetCellString( i,16,0,sum);
	}
}
function colSum2(col){
	var sum=0;
	for(var i=17;i<=28;i++){
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
function sumHj1(){
	for(var i=3;i<=24;i++){
		if(i==10){
			continue;
		}
		var sum=colSum1(i);
		DCellWeb_tj.SetCellString( i,6,0,sum);
	}
}
function colSum1(col){
	var sum=0;
	for(var i=7;i<=15;i++){
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
//人民警察警员职务序列 
var map2={	
		'20':'7',//一级警长  
		'21':'8',//二级警长  
		'22':'9',//三级警长  
		'23':'10',//四级警长  
		'24':'11',//一级警员  
		'25':'12',//二级警员  
		'26':'13',//三级警员  
		'27':'14',//试用期人员 
		'28':'15'//其他 	  
			};
//非领导职务
var map4={	
		'301':'17',//首席大法官
		'302':'18',//一级大法官
		'303':'19',//二级大法官  
		'304':'20',//一级高级法官
		'305':'21',//二级高级法官
		'306':'22',//三级高级法官
		'307':'23',//四级高级法官
		'308':'24',//一级法官
		'309':'25',//二级法官
		'310':'26',//三级法官
		'311':'27',//四级法官
		'312':'28',//五级法官
		'401':'17',//首席大检察官
		'402':'18',//一级大检察官
		'403':'19',//二级大检察官
		'404':'20',//一级高级检察官
		'405':'21',//二级高级检察官
		'406':'22',//三级高级检察官
		'407':'23',//四级高级检察官
		'408':'24',//一级检察官
		'409':'25',//二级检察官
		'410':'26',//三级检察官
		'411':'27',//四级检察官
		'412':'28'//五级检察官
			};
</script>