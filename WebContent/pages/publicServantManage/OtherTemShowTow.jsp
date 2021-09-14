<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.OtherTemShowTowPageModel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>


<script type="text/javascript">
	var ctpath = "<%=request.getContextPath() %>";
   Ext.onReady(function openfile(){
		document.getElementById("DCellWeb1").ShowSheetLabel(0,document.getElementById("DCellWeb1").GetCurSheet());
  		document.getElementById("DCellWeb1").ShowPageBreak(0);
        //设置不支持允许拖动
        document.getElementById("DCellWeb1").AllowDragdrop = false;
   });

</script>
<script type="text/javascript" language="javascript">
var ctpath = "<%=request.getContextPath() %>";
        //打印预览
        function btn_PrePrint() 
        {	var aa = document.getElementById("DCellWeb1");
        	var countperson = 0; //统计人数
        	var a = 0;//循环变量
        	var b = 0;//行高
        	var c = 0;//循环变量
        	var d = 0;//截取行目录人的人数
        	function start(){
        		for(;a<(结束行的参数);a++){
        			//设A4高为210
        			b += get();//行高
        			countperson++;
        			if(b>210){
        				countperson--;
        				acc (countperson);
        			}
        		}
        	} 
      
       
        
        	
        	setAuth(document.getElementById("DCellWeb1"));
            document.getElementById("DCellWeb1").PrintPreview(1, document.getElementById("DCellWeb1").GetCurSheet);
        }
     	//打印
        function btn_Print() 
        {   
        	setAuth(document.getElementById("DCellWeb1"));
            document.getElementById("DCellWeb1").PrintSheet(true, document.getElementById("DCellWeb1").GetCurSheet);
        }
        // 导出Excel
        function do_eportExcelDlg(){
        	var cell = document.getElementById("DCellWeb1");
        	setAuth(cell);
        	cell.ExportExcelDlg();
       		
        }
        //导出路径页面打开
        function openWin(){
        	radow.doEvent("openExpPathWin2");
        }
    	// 导出Excel
    	function do_eportPDF(path,name){
      	    document.getElementById("DCellWeb1").ExportPdfFile( path+name+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets());
      	    if(document.getElementById("DCellWeb1").ExportPdfFile( path+name+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets())==true){
      	     			 alert("导出成功！");
      	     		 }
	  
    		
    	}
    	
</script>

<%
	OtherTemShowTowPageModel p = new OtherTemShowTowPageModel();
	String pre = p.aa(request);
	out.write(pre);
%>
<script type="text/javascript">
var ctpath = "<%=request.getContextPath() %>";
	function showdata(){
		setAuth(document.getElementById("DCellWeb1"));
		a();
	}
	
	
	b = 0;//合并单元格用
	c = 1;//机构编用
	var cell ;
 	var data 
	function show(value){
		
		cell = document.getElementById("DCellWeb1");
		cell.setCurSheet(0);//获取第一页的方法
		cell.PrintSetMargin(100,100,100,100);//设置页边距
		cell.PrintSetOrient(1);//进纸方向
		cell.setCurSheet(1);//获取第二页的方法
		cell.PrintSetMargin(100,100,100,100);//设置页边距
		cell.PrintSetOrient(1);//进纸方向
		cell.PrintSetFoot('','第&P页','');
		cell.SetSheetLabel(0,"封面");//设置指定表页的页签名称。
		cell.SetSheetLabel(1,"目录");//设置指定表页的页签名称。
		cell.SetSheetLabel(2,"数据");//设置指定表页的页签名称。
		cell.MergeCells(3,9,9,9);
		var a = 0;
		try{
			data  = eval(value);
		}catch(err){
	
		}
		///合并目录的虚线单元格
		for(a = 0; a < data.length;a++){
			cell.MergeCells(2,4+a,10,4+a);
		}
		var item ;
		var mess = '';//存放拼接的信息
		var rownum = 5;
		for (var i = 0; i < data.length; i++) {
		    item = data[i];
///		        console.log(item.B0101);
		        mess = getrowline(item.B0101,item.num); 
		        cell.SetCellString(2,4+b,'1',mess);
		        cell.SetCellString(11,4+b,'1','选择' +' '+item.num+' '+'人');
		        cell.SetCellDouble(13,4+b,'1',rownum);
		        rownum = rownum + parseInt(item.num)+1;//加一是数据页每个机构都加了一个空行
		        b++;
		}
		getrowheight();
		cell.setCurSheet(1);//获取第二页的方法
		cell.SetColHidden(13,13);
		cell.DeleteRow(4+a,46-a,1);
		cell.setCurSheet(2);//获取第三页的方法
		cell.PrintSetMargin(100,100,100,100);//设置页边距
		cell.PrintSetOrient(1);//进纸方向
	}
	function getrowheight(){
		var countperson = 0;
		var clll = document.getElementById("DCellWeb1");
		var countwww = 0;
		var pagenum = 1;
		var mulu1 = 4; //mulu 行
		var mulu2 = 5; //mulu 值
//		var jjjj = 1000;//假设 行 数目
		clll.SetCurSheet(2);
		var rowscount = clll.GetRows(2);
		var Height = clll.PrintGetPaperHeight(2) - (100*2); 
		for(var a = 1; a <= rowscount; a++){
			var rowheight = clll.GetRowHeight(0,a,2);
			countwww = countwww + rowheight;
			if(countwww > Height){
				pagenum = pagenum + 1;
				countwww = rowheight;
			}
			if(mulu2 == a){
				cell.SetCellString(12, mulu1,'1',pagenum + '页');
				mulu1 ++;
				mulu2 = clll.getCellDouble(13, mulu1,1);
				
			}
		}
	}
	
	//a 是机构名称，b人数
	function getrowline(a,b){
		var anum = a.length;
		var bnum = b.length;
		var cnum = anum+bnum
		var line = '';
		for(var r = 0; r < 100-cnum;r++){
			line += '-';
		}
		return a +' ' + line ;
	}
	
</script>
</head>
<body>
	<div style="background:#F0F0F0">
	<table>
		<tr>
			<TD NOWRAP><A class=tbButton id=cmdFilePrintPreview title=打印预览 onclick="btn_PrePrint()" ><IMG style="align:absMiddle;" src="images/general/printpreview.gif" width="20" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFilePrint title=打印 onclick="btn_Print()" ><IMG style="align:absMiddle;" src="images/general/print.gif" width="20" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=导出Excel onclick="do_eportExcelDlg()" ><IMG style="align:absMiddle;" src="main/images/f05.png" width="16" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=导出PDF onclick="openWin()" ><IMG style="align:absMiddle;" src="main/images/zwzpdf5.gif" width="20" height="20"></A></TD>
	</table>
	<object id="DCellWeb1" style="left: 0px; width: 100%; top: 0px; height: 95%" 
    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
    codebase="softTools/cellweb5.cab#version=5.3.9.16">
    <!-- #version=5.3.9.16 --><!-- #version=5,3,8,0429 -->
    <param name="_Version" value="65536" />
    <param name="_ExtentX" value="10266" />
    <param name="_ExtentY" value="7011" />
    <param name="_StockProps" value="0" />
    </object>
	</div>
</body>
<odin:hidden property="tpid"/>
<odin:hidden property="rownum"/>
<odin:window src="/blank.htm" id="ExpPathWin2" width="420" height="159"
	title="设置PDF导出路径" modal="true"></odin:window> 
<script type="text/javascript" language="javascript">
setAuth(document.getElementById("DCellWeb1"));
</script>
</html>







