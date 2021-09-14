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
	for(var i=5;i<=23;i++){//行
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
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<5||row1>23){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	var groupid=document.getElementById("groupid").value;
	var year=document.getElementById("timetj").value;
	var cxtj="sycznl1$"+row1+"$"+col1+"$"+groupid+"$"+year;
	$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\cgsynlqk1.cll","");
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
    //DCellWeb_tj.SetRowhidden(29, 29);
    //DCellWeb_tj.SetRowhidden(30, 30);
    //DCellWeb_tj.SetRowhidden(5, 5);
    DCellWeb_tj.SetRowhidden(2, 2);
    var obj ="";
    try{
	    obj = eval('(' + jsonstr + ')');
    }catch(err){
    }
    for(var i=0;i<obj.length;i++){////插入数据 行数 循环
    	var a0221=obj[i].a0221;//代码
   	    if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   			continue;//类别代码为空
   	    }
    	
    	
   	 	if(param=='3'){
	   		a0221=map2[a0221];//行数	
	   	}else{
	   		a0221=map1[a0221];//行数
	   	}
	   //a0221=map1[a0221];//行数
    	var i_col=3;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].heji==0||obj[i].heji=='null')?"":obj[i].heji);//合计
	   i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].xydy20==0||obj[i].xydy20=='null')?"":obj[i].xydy20);//20及以下
	   	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy21==0||obj[i].dy21=='null')?"":obj[i].dy21);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy22==0||obj[i].dy22=='null')?"":obj[i].dy22);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy23==0||obj[i].dy23=='null')?"":obj[i].dy23);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy24==0||obj[i].dy24=='null')?"":obj[i].dy24);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy25==0||obj[i].dy25=='null')?"":obj[i].dy25);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy26==0||obj[i].dy26=='null')?"":obj[i].dy26);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy27==0||obj[i].dy27=='null')?"":obj[i].dy27);//
			i_col=i_col+1;	
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy28==0||obj[i].dy28=='null')?"":obj[i].dy28);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy29==0||obj[i].dy29=='null')?"":obj[i].dy29);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy30==0||obj[i].dy30=='null')?"":obj[i].dy30);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy31==0||obj[i].dy31=='null')?"":obj[i].dy31);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy32==0||obj[i].dy32=='null')?"":obj[i].dy32);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy33==0||obj[i].dy33=='null')?"":obj[i].dy33);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy34==0||obj[i].dy34=='null')?"":obj[i].dy34);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy35==0||obj[i].dy35=='null')?"":obj[i].dy35);//
			i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy36==0||obj[i].dy36=='null')?"":obj[i].dy36);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy37==0||obj[i].dy37=='null')?"":obj[i].dy37);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy38==0||obj[i].dy38=='null')?"":obj[i].dy38);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy39==0||obj[i].dy39=='null')?"":obj[i].dy39);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,(obj[i].dy40==0||obj[i].dy40=='null')?"":obj[i].dy40);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy41==0||obj[i].dy41=='null')?"":obj[i].dy41);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,(obj[i].dy42==0||obj[i].dy42=='null')?"":obj[i].dy42);//
		  	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,(obj[i].dy43==0||obj[i].dy43=='null')?"":obj[i].dy43);//
    }
    if(param=='3'){
   		sumHj();
   	}
}
function sumHj(){
	for(var i=3;i<=27;i++){
		var sum=colSum(i);
		DCellWeb_tj.SetCellString( i,5,0,sum);
	}
}
function colSum(col){
	var sum=0;
	for(var i=6;i<=23;i++){
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
		'1A11':'6',//省部级正职
		'1A12':'7',//省部级副职
		'1A21':'8',//厅局级正职领导职务
		'1A22':'10',//厅局级副职领导职务
		'1A31':'12',//县处级正职领导职务
		'1A32':'14',  // 县处级副职领导职务
		'1A41':'16',  // 乡科级正职领导职务
		'1A42':'18',  // 乡科级副职领导职务
		'1A50':'20',  // 科员
		'1A60':'21', //办事员
		'1A98':'22',  // 试用期人员
		'1A99':'23'   //其他
			};	
var map2={
		'1A21':'9',//厅局级正职非领导职务
		'1A22':'11',	//厅局级副职非领导职务
		'1A31':'13',	//县处级正职非领导职务
		'1A32':'15',	//县处级副职非领导职务
		'1A41':'17',	//乡科级正职非领导职务
		'1A42':'19'	//乡科级副职非领导职务
};   
</script>          
                 