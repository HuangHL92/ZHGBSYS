  <meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>
打印窗体
</title>
<head>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit.jsp" %>

<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>
	    
	    
 <META http-equiv=Content-Type content="text/html; charset=gbk"> 
 <meta http-equiv="X-UA-Compatible" content="IE=8" /> 
 
	<script src="<%=request.getContextPath()%>/pages/gbmc/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/gbmc/js/underscore.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/gbmc/js/tableEditer.js" type="text/javascript"></script>
 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome.css">
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome-ie7.min.css">    
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/bootstrap.min.css"> 
 	
	<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/gbmc/gbtp.css">

	<style>	

		<!--  
		.A3 {          
		    page-break-before: auto;          
		    page-break-after: always;  
		}  
		-->  
		
		*{margin:0px!important;padding:0px!important;}
		body{
		margin: 1px;overflow-y: scroll;overflow-x: hidden;
		font-family:'宋体',Simsun;
		word-break:break-all;
		margin-bottom: 250px;
		overflow-y:hidden;
		}
		
		#jqContextMenu{
			border-style:groove;
			background-color: gary;
			border-width: 1px;
		}
		
		.txt_editer{
			border-style: none;
		}
		
		.kcclClass{
		background-color: rgb(102,204,255) !important;
		}
		.drag_color{
			background-color: rgb(232,232,232) !important;
		}
		.drag_pre_color{
			background-color: rgb(233,250,238)!important;
		}
		.default_color{
			background-color: #FFFFFF !important;
		}
	</style>

	<style>
		@media print{
	　　		.noprint{
	  　　		display:none
	　　		}
		}  
	</style>
	<script language="javascript">   
        function printsetup() {
            // 打印页面设置
            wb.execwb(8, 1);
        }

        function printdata() {
            // 打印页面预览
            $(".noprint").css("display", "none");
            wb.execwb(6, 1);
            $(".noprint").css("display", "");
        }
        //设置纸张方向
      //修改注context册表上下左右边距,清空页眉,清空页脚
function PageSetup_Null(mtop,mbottom,mleft,mright)
{
   //alert("mtop="+mtop,+"mbottom="+mbottom,"mleft="+mleft,"mright="+mright);

    //注意注册表里的单位是英寸，打印设置中是毫米，1英寸=25.4毫米
    mtop=""+parseFloat(mtop)/25.4;
    mbottom=""+parseFloat(mbottom)/25.4;
    mleft=""+parseFloat(mleft)/25.4;
    mright=""+parseFloat(mright)/25.4;
    var HKEY_Root,HKEY_Path,HKEY_Key;
    HKEY_Root="HKEY_CURRENT_USER";
    HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
    try
    {
        var RegWsh=new ActiveXObject("WScript.Shell");

        HKEY_Key="margin_left" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mleft);  //左边边界

        HKEY_Key="margin_right" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mright); //右边边界

        HKEY_Key="margin_top" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mtop);//上边边界

        HKEY_Key="margin_bottom" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mbottom); //下边边界

        HKEY_Key="header";
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); //清空页眉

        HKEY_Key="footer";
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");//清空页脚

        RegWsh.sendKeys("%fu");
        RegWsh.sendKeys("%a");
        RegWsh.sendKeys("{ENTER}");
    }
    catch(e){}
}
		function printdata(){
		  // 打印页面预览
		  $(".noprint").css("display","none");
		  wb.execwb(6,1);
		  $(".noprint").css("display","");
		}		
		function printpreview(){
		  // 打印页面预览
		  $(".noprint").css("display","none");
		  wb.execwb(7,1);
		  $(".noprint").css("display","");
		}
		function printit(){
		 if (confirm('确定打印吗？')){
		  	wb.ExecWB(6,1);
		 }
		  //wb.execwb(1,1)//打开
		  //wb.ExecWB(2,1);//关闭现在所有的IE窗口，并打开一个新窗口
		  //wb.ExecWB(4,1)//;保存网页
		  //wb.ExecWB(6,1)//打印
		  //wb.ExecWB(7,1)//打印预览
		  //wb.ExecWB(8,1)//打印页面设置
		  //wb.ExecWB(10,1)//查看页面属性
		  //wb.ExecWB(15,1)//好像是撤销，有待确认
		  //wb.ExecWB(17,1)//全选
		  //wb.ExecWB(22,1)//刷新
		  //wb.ExecWB(45,1)//关闭窗体无提示
		 }
		 function onloadPage(){
			$("#coordTable_div").html(opener.$("#coordTable_div").html()); 
		 }
		 
		 function printHeight(){
			// alert($("#coordTable").outerHeight()+$(".BiaoTouP").outerHeight()+$(".TiaoPeiShiJian").outerHeight());
			
			$(".coordTable tr :first-child").css("width","0%");
			$(".coordTable tr :first-child").css("border","0");
		
			/*
			 var tbodyHeight=0; 
			 $("#coordTable tbody").find('tr').each(function (index, item) {
			      var trHeight = $(this).outerHeight(true);
			      console.log(trHeight);
			      tbodyHeight+=trHeight; 
			 }); 
			 */
			 
			 //alert("curr:"+$("#coordTable tbody").outerHeight() + " " + tbodyHeight);
			 			  
		 }

        function changeclass(obj){
            var func=$("#"+obj+" tr td div");
            _.each(func,function(obj,index){
                if(obj.scrollHeight>obj.clientHeight||obj.offsetHeight>obj.clientHeight) {
                    // alert("模式开启=",document.compatMode);
                    // Ext.log("模式开启=",document.compatMode);
                    // alert("obj.scrollHeight="+obj.scrollHeight,"obj.offsetHeight="+obj.offsetHeight,"obj.clientHeight="+obj.clientHeight);

                    var scrollHeight=obj.scrollHeight>obj.offsetHeight?obj.scrollHeight:obj.offsetHeight;
                    // console.log(obj);
                    var fontSi= 1 + parseInt(Math.ceil(8 * 120.0 / scrollHeight));
                    var fontSize=fontSi>13?13:fontSi;
                    $(obj).find("span").css("font-size", fontSize+"pt");

                }
            })
        }

	 </script>
 </head>
 <body style="overflow-x:auto;overflow-y:auto" onload="onloadPage()">
	<div class="noprint" style="width:100%;height:50px;margin-top:10px;font-size:12px;text-align:right;">
	 	<BR>   
	    
	    <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0">
	    </OBJECT>
	      
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_setup style="width:100px;height:30px" value="" onclick="javascript:printsetup();">&nbsp;打印页面设置&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_show  style="width:100px;height:30px" value="" onclick="javascript:printpreview();">&nbsp;打印预览&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" value="打印" type="button" style="width:60px;height:30px" value="" onclick="javascript:printdata(); ">&nbsp;打印&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_fh style="width:60px;height:30px" value="" onclick="javascript:window.close();"> &nbsp;关闭 &nbsp;</button> &nbsp;
        <button style=";display: none;margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"
                onclick="changeclass()">&nbsp;测试&nbsp;
        </button>
		<BR>
 
	</div> 
	
	 <div id="coordTable_div">
	 </div>
</div> 
</body>
</html>