<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<%@page import="com.insigma.siis.local.pagemodel.search.PreSubmitPageModel"%>
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
        {
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
        	document.getElementById("DCellWeb1").ExportExcelDlg();
        }
        
    	// 导出Excel
    	function do_eportPDF(){
    		setAuth(document.getElementById("DCellWeb1"));
      		 Ext.MessageBox.prompt("输入框","请输入文件名：",function(bu,txt){   
      			 if(bu=="ok"){
      				 document.getElementById("DCellWeb1").ExportPdfFile( txt+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets());
      	     		 if(document.getElementById("DCellWeb1").ExportPdfFile( txt+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets())==true){
      	     			 alert("导出成功！");
      	     		 }
      			 }
		});   
    		
    	}
</script>

<%
	PreSubmitPageModel p = new PreSubmitPageModel();
	String pre = p.aa(request);
	out.write(pre);
%>
<script type="text/javascript">
var ctpath = "<%=request.getContextPath() %>";
	function showdata(){
		setAuth(document.getElementById("DCellWeb1"));
		a();
	}
</script>

</head>
<body>
	<div style="background:#F0F0F0">
	<table>
		<tr>
			<TD NOWRAP><A class=tbButton id=cmdFilePrintPreview title=打印预览 onclick="btn_PrePrint()" ><IMG style="align:absMiddle;" src="images/general/printpreview.gif" width="16" height="16"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFilePrint title=打印 onclick="btn_Print()" ><IMG style="align:absMiddle;" src="images/general/print.gif" width="16" height="16"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=导出Excel onclick="do_eportExcelDlg()" ><IMG style="align:absMiddle;" src="main/images/f05.png" width="16" height="16"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=导出PDF onclick="do_eportPDF()" ><IMG style="align:absMiddle;" src="main/images/i06.png" width="16" height="16"></A></TD>
	</table>
	<object id="DCellWeb1" style="left: 0px; width: 100%; top: 0px; height: 100%" 
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
<script type="text/javascript" language="javascript">
setAuth(document.getElementById("DCellWeb1"));
</script>
</html>







