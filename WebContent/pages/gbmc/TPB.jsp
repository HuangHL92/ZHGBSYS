<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ" %>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit4.jsp" %>

<meta http-equiv="X-UA-Compatible" content="IE=8"/>


<script src="<%=request.getContextPath()%>/pages/gbmc/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/gbmc/js/underscore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js"
        type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/jquery.contextmenu.r2.js"
        type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/gbmc/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/gbmc/js/tableEditer.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/pages/gbmc/js/colResizable-1.6.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome.css">
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/font-awesome-ie7.min.css">
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/mainPage/css/bootstrap.min.css">

<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/gbmc/gbtp.css">

<style>
    * {
        margin: 0px !important;
        padding: 0px !important;
    }

    body { 
        overflow-y: hidden;
        overflow-x: hidden;
        font-family: '宋体', Simsun;
        word-break: break-all;
        margin-bottom: 50px; 
    }

    #jqContextMenu {
        border-style: groove;
        background-color: gary;
        border-width: 1px;
    }

    .txt_editer {
        border-style: none;
    }

    .kcclClass {
        background-color: rgb(102, 204, 255) !important;
    }

    .drag_color {
        background-color: rgb(232, 232, 232) !important;
    }

    .drag_pre_color {
        background-color: rgb(233, 250, 238) !important;
    }

    .default_color {
        background-color: #FFFFFF !important;
    }
</style>

<style>
    @media print {
        　　.noprint {
            　　display: none 　　
        }
    }
</style>

<odin:hidden property="ynId"/>
<odin:hidden property="downfile" />
<script type="text/javascript">
function reloadTree(){
		setTimeout(xx,1000);
	}
function xx(){
		var downfile = document.getElementById('downfile').value;
		/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
		window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
		ShowCellCover("","温馨提示","导出成功！");
		setTimeout(cc,3000);
	}

function printPdf(){
	var downfile = document.getElementById('downfile').value;
	$("#printIframe")[0].src="printPdfServlet.pdf?method=printPdfFile&prid="+encodeURI(encodeURI(downfile));
	//ShowCellCover("","温馨提示","打印成功！");
	printAct();
}
function printAct(){
  if($("#printIframe")[0].readyState=="complete"){
    $("#printIframe")[0].contentWindow.focus();
    $("#printIframe")[0].contentWindow.print();
  }else{alert($("#printIframe")[0].readyState);
    setTimeout(printAct,1000); 
  }
}
</script>
<script type="text/javascript">
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}
	
    function SetTDtext(td, fieldName,v) {  
       	v = ((feildIsNull(v) ? "&nbsp;" : v)); 
   		try{
   			var colIndex = $(td).index();
   			if (v==null) v="";
   			//控制籍贯的显示样式
   			if (fieldName == "a0111a"){
   				if (v.length == 4){
   					$(td).attr("nowrap","nowrap");
   					//两个字换行
   					v = v.substring(0,2) + "<BR/>" + v.substring(2);
   				}else if (v.length == 5 || v.length == 6){
   					$(td).attr("nowrap","nowrap");
   					//三个字换行
   					v = v.substring(0,3) + "<BR/>" + v.substring(3);
   				}
   			}else if (fieldName == "a0117"){
   				$(td).attr("nowrap","nowrap");
   				if (v.indexOf("<")<0){
   					var tText = "";
   					for (var ii=0;ii<=v.length;ii++){
   						tText+=v.substring(ii,ii+1)+"<BR/>";
   					}
   					v = tText;
   				}
					v=v.toUpperCase(); 
					while (v.endWith("<BR />")){
						v = v.substring(0,v.length-6);
					} 		   					
					while (v.endWith("<BR/>")){
						v = v.substring(0,v.length-5);
					} 					
					while (v.endWith("<BR>")){
						v = v.substring(0,v.length-4);
					}	 			 
   			} else if (fieldName== "a0117" ||
   					fieldName == "a0144" ||
   					fieldName == "a0192c"){
   				$(td).attr("nowrap","nowrap");
   				if (v.length>7){
   					v = v.substring(0,7);
   				}
   			} else if (fieldName == "a0101"){
   				//姓名为两个字的中间加空格，其它不变
   				v = v.replace(' ','');
   				if (v.length==2){
   					v = v.substring(0,1) +"  "+ v.substring(1,2);
   				}
   			}
   			
   			var TD_HEIGHT = 160;
   			
   			  $(td).html((v==""||v==null||v=="null")?"&nbsp;":v.replace(/\n/g,"<br/>"));
   			  var scrollHeight = $(td)[0].scrollHeight; 
   			  if (scrollHeight>=TD_HEIGHT){ 
   				  var fontSize = 11; 
   				  while (scrollHeight>=TD_HEIGHT){
   					  var s = "<span style='font-size:"+fontSize+"pt'>"+v.replace(/\n/g,"<br/>") + "</span>";
   					  $(td).html((v==""||v==null||v=="null")?"&nbsp;":s);
   					  scrollHeight = $(td)[0].scrollHeight;
   					  fontSize=fontSize-1; 
   				  }
   				  $(td).attr("height",TD_HEIGHT); 
   			  }else{
   				  $(td).removeAttr("height");
   				  $(td).attr("title","");
   			  }
   		}catch(e){ 
   			alert(e);
   		}        	
    }
 

    function getWidth(obj) {
        alert("模式开启=", document.compatMode);
        if (document.compatMode == "BackCompat") {
            cWidth = document.body.clientWidth;
            cHeight = document.body.clientHeight;
            sWidth = document.body.scrollWidth;
            sHeight = document.body.scrollHeight;
            sLeft = document.body.scrollLeft;
            sTop = document.body.scrollTop;
        }
        else { //document.compatMode == \"CSS1Compat\"
            cWidth = document.documentElement.clientWidth;
            cHeight = document.documentElement.clientHeight;
            sWidth = document.documentElement.scrollWidth;
            sHeight = document.documentElement.scrollHeight;
            sLeft = document.documentElement.scrollLeft == 0 ? document.body.scrollLeft : document.documentElement.scrollLeft;
            sTop = document.documentElement.scrollTop == 0 ? document.body.scrollTop : document.documentElement.scrollTop;
        }
    }


   

    $(document).ready(function () { 
        var viewSize = Ext.getBody().getViewSize(); 
        $("#selectable").height(viewSize.height - 60);
    });
     
</script>
 
<body>
	
	<div id="selectable" style="margin-left:20px!important;overflow-y:scroll; overflow-x:scroll;height:480px;width:100%;align:center;">
		<p id="BiaoTouTitle" class="BiaoTouP" style="width:1380;text-align:center"> 干部名册 </p>		
		<div id="coordTable_div" style="margin-left:10px;" width=1380>
		<% if (subWinIdBussessId.endsWith("gbmc1")) {%> 
			<%-- <jsp:include page="gbmc1_youhua.jsp"></jsp:include> --%>
		<% }else if (subWinIdBussessId.endsWith("gbmc2")) {%> 
		<%-- 	<jsp:include page="gbmc2.jsp"></jsp:include> --%>
		<% }else if (subWinIdBussessId.endsWith("gbmc3")) {%> 
			<jsp:include page="gbmc3.jsp"></jsp:include>
		<% }else if (subWinIdBussessId.endsWith("gbmc4")) {%> 
			<jsp:include page="gbmc4.jsp"></jsp:include>
		<% }else if (subWinIdBussessId.endsWith("gbmc5")) {%> 
			<jsp:include page="gbmc5.jsp"></jsp:include>
		<% } %>
		</div>
	</div> 
	
	<iframe id="iframe_expTPB" style="display: none;" src=""></iframe>

	<div class="testIDC">
    	<p style="margin:10px !important" class="noprint">
	        <label class="pull-left" class="label-primary">
	            <%--<input type="checkbox" id="fontCheckBox" checked style="margin:5px;padding:5px"  > <span class="text-primary">弹窗模式&nbsp;</span>--%>
	        </label>
	        <label class="pull-right"> 
	            <%--&nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="save();">&nbsp;保存&nbsp;</button>--%>
	
	            <!-- &nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary"
	                          onclick="remove_ie_header_and_footer();printTable()">&nbsp;打印窗体&nbsp;
	        </button> -->
	            <!-- &nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="exportTPBword()">&nbsp;导出word&nbsp;
	        </button> -->
	            &nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="exportTPBExcel()">&nbsp;导出Excel&nbsp;
	        </button>
	            <!-- &nbsp;<button style="margin:5px;padding:5px" type="button" class="btn btn-sn btn-primary" onclick="exportTPBPdf()">&nbsp;打&nbsp;印&nbsp;
	        </button> -->
	            &nbsp;
	            <button style="margin:5px;padding:5px;display: none" type="button" class="btn btn-sn btn-primary"
	                    onclick="changeclass()">&nbsp;测试&nbsp;
	            </button>
	        </label>
	    </p>
	
	    <odin:hidden property="yntype"/>
	    <odin:hidden property="tpy"/>
	    <odin:hidden property="tpm"/>
	    <odin:hidden property="tpd"/>
	    <odin:hidden property="docpath"/>
	    <odin:hidden property="ID_ROWINFO" title="保存对象"/>
	    <odin:hidden property="ROWID" title="保存对象id"/>
	    <odin:hidden property="sql"/>
	    <odin:hidden property="a0000" title="人员编码id"/>
	
	    <odin:hidden property="NL_JZRQ" title="年龄截止日期"/>
	
	</div>
	

	    <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0">
	    </OBJECT>	
</body>

<script> 
    function StandardTaxRate() {
        $.ajax({
            url: "/Resource/salaryTaxRate.xml",
            dataType: 'xml',
            type: 'GET',
            timeout: 2000,
            error: function (xml) {
                alert("加载XML 文件出错！");
            },
            success: function (xml) {
                $(xml).find("taxrate").each(function (i) {
                    var oid = $(this).attr("id");
                    var lower = $(this).children("lower").text();
                    var upper = $(this).children("upper").text();
                    var rate = $(this).children("rate").text();
                    var buckle = $(this).children("buckle").text();
                    ///后续操作。。。
                });
            }
        });
    } 

    function printsetup() {
        // 打印页面设置 
        document.all.wb.execwb(8, 1);
    }    
	function printpreview(){
		// 打印页面预览
		try{
			$(".noprint").css("display","none");  
			document.all.wb.execwb(7,1);
			document.all.wb.outerHTML = "";
			$(".noprint").css("display","");
		}catch(e){
			alert(e);
		}
	}   
	
	function remove_ie_header_and_footer() {
  	    var hkey_root, hkey_path, hkey_key;
  	    hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
  	    try {
  	        var RegWsh = new ActiveXObject("WScript.Shell");
  	        RegWsh.RegWrite(hkey_path + "header", "");
  	        RegWsh.RegWrite(hkey_path + "footer", "");
  	    } catch (e) {}
  	}
  	//导出word
  	function exportTPBword(){
  	radow.doEvent("exportTPBword");
  	} 	

	  	//导出excel
	function exportTPBExcel(){
	radow.doEvent("exportTPBExcel");
	} 
  	  	//导出word
  	function exportTPBPdf(){
  	radow.doEvent("exportTPBPdf");
  	} 	
</script>
<iframe style="display:none" height="1000"  width="1000" id="printIframe" src="about:blank"></iframe>
