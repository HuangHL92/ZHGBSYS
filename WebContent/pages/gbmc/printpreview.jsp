  <meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>
��ӡ����
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
		font-family:'����',Simsun;
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
	����		.noprint{
	  ����		display:none
	����		}
		}  
	</style>
	<script language="javascript">   
        function printsetup() {
            // ��ӡҳ������
            wb.execwb(8, 1);
        }

        function printdata() {
            // ��ӡҳ��Ԥ��
            $(".noprint").css("display", "none");
            wb.execwb(6, 1);
            $(".noprint").css("display", "");
        }
        //����ֽ�ŷ���
      //�޸�עcontext����������ұ߾�,���ҳü,���ҳ��
function PageSetup_Null(mtop,mbottom,mleft,mright)
{
   //alert("mtop="+mtop,+"mbottom="+mbottom,"mleft="+mleft,"mright="+mright);

    //ע��ע�����ĵ�λ��Ӣ�磬��ӡ�������Ǻ��ף�1Ӣ��=25.4����
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
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mleft);  //��߽߱�

        HKEY_Key="margin_right" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mright); //�ұ߽߱�

        HKEY_Key="margin_top" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mtop);//�ϱ߽߱�

        HKEY_Key="margin_bottom" ;
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,mbottom); //�±߽߱�

        HKEY_Key="header";
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); //���ҳü

        HKEY_Key="footer";
        RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");//���ҳ��

        RegWsh.sendKeys("%fu");
        RegWsh.sendKeys("%a");
        RegWsh.sendKeys("{ENTER}");
    }
    catch(e){}
}
		function printdata(){
		  // ��ӡҳ��Ԥ��
		  $(".noprint").css("display","none");
		  wb.execwb(6,1);
		  $(".noprint").css("display","");
		}		
		function printpreview(){
		  // ��ӡҳ��Ԥ��
		  $(".noprint").css("display","none");
		  wb.execwb(7,1);
		  $(".noprint").css("display","");
		}
		function printit(){
		 if (confirm('ȷ����ӡ��')){
		  	wb.ExecWB(6,1);
		 }
		  //wb.execwb(1,1)//��
		  //wb.ExecWB(2,1);//�ر��������е�IE���ڣ�����һ���´���
		  //wb.ExecWB(4,1)//;������ҳ
		  //wb.ExecWB(6,1)//��ӡ
		  //wb.ExecWB(7,1)//��ӡԤ��
		  //wb.ExecWB(8,1)//��ӡҳ������
		  //wb.ExecWB(10,1)//�鿴ҳ������
		  //wb.ExecWB(15,1)//�����ǳ������д�ȷ��
		  //wb.ExecWB(17,1)//ȫѡ
		  //wb.ExecWB(22,1)//ˢ��
		  //wb.ExecWB(45,1)//�رմ�������ʾ
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
                    // alert("ģʽ����=",document.compatMode);
                    // Ext.log("ģʽ����=",document.compatMode);
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
	      
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_setup style="width:100px;height:30px" value="" onclick="javascript:printsetup();">&nbsp;��ӡҳ������&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_show  style="width:100px;height:30px" value="" onclick="javascript:printpreview();">&nbsp;��ӡԤ��&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" value="��ӡ" type="button" style="width:60px;height:30px" value="" onclick="javascript:printdata(); ">&nbsp;��ӡ&nbsp;</button>
	    <button class="btn btn-primary" style="margin:5px;padding:5px" name=button_fh style="width:60px;height:30px" value="" onclick="javascript:window.close();"> &nbsp;�ر� &nbsp;</button> &nbsp;
        <button style=";display: none;margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"
                onclick="changeclass()">&nbsp;����&nbsp;
        </button>
		<BR>
 
	</div> 
	
	 <div id="coordTable_div">
	 </div>
</div> 
</body>
</html>