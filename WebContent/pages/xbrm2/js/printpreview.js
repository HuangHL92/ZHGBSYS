/******
 *  日期：2019-09-15
 * 	修订时间：2019-09-29
 *  主要功能：
 *  	实现对动议干部情况一览表、省委常委会干部情况一览表、省委书记专题会议干部情况一览表等一览表
 *  的打印功能，需要按照A3纸张的横向打印，并且自动分页。
 */     
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
//修改注册表上下左右边距,清空页眉,清空页脚
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

 
        function printpreview() {
            // 打印页面预览
            $(".noprint").css("display", "none");
             if($("#yntype").val()=="TPHJ2"||$("#yntype").val()=="TPHJ3"){
               PageSetup_Null(10,10,18,18);//毫米
            } else{
                PageSetup_Null(10,10,30,30);//毫米
            }
            wb.execwb(7, 1); 
           
            $(".noprint").css("display", "");
        }

        function printit() {
            if (confirm('确定打印吗？')) {
                wb.ExecWB(6, 1);
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

        function onloadPage() { 
			try{
        		$("body").removeClass("line-height");
			}catch(e){
				alert(e);
			}
            document.getElementById("typecore").innerHTML = (opener.document.getElementById("yntype").outerHTML);
            document.getElementById("coordTable_div0").innerHTML = (opener.document.getElementById("coordTable").outerHTML);
            $(".BiaoTouP").html(opener.$("#BiaoTouTitle").html().replace("报告日期：", ""));
            $("#TiaoPeiShiJianPrint").html("(" + opener.$("#TiaoPeiShiJianPrint_year").html() + "年" + opener.$("#TiaoPeiShiJianPrint_month").html() + "月" + opener.$("#TiaoPeiShiJianPrint_date").html() + "日)");

			//var A3_HIGHT = (opener.$("#coordTable").width() * 297)/ (420.0);
			var A3_HIGHT = 1000;
			var A3_WIDTH = $("#yntype").val()=="TPHJ1"?1191: 1458;
			// alert(A3_HIGHT)
			 //表头的高度 BiaoTouP
			 //body 高度
			 //tr高度
			var tableHeader_height = $(".JiMi").outerHeight()+($(".BiaoTouP").outerHeight()+40)+$(".TiaoPeiShiJian").outerHeight();
			var tCoordTable_height = $("#coordTable").outerHeight();
			var tPageHead_height = opener.$("#coordTable table tr").outerHeight();

			var tPage_Height = 0;  //原来有一个表头
			var objPointer = null;	//插入对象的指针

			var tableIndex = 0;	//生成表格索引

			var objPageTRHead = null; //页头
			//try{
				$("#coordTable_div0 table tbody").find('tr').each(function (index, item) {
				//opener.$("#coordTable_div tbody").find('tr').each(function (index, item) {
					var trHeight = $(this).outerHeight();	//行高
					if (tableIndex <=0 ||  (tPage_Height + trHeight) >= A3_HIGHT || tPage_Height <=0 ){
						//生成表DIV和表格
						  tPage_Height=0; //重置表头的高
			    		  if (tableIndex<=0){
			    		  //第一页
								var isNextTH = false;
								 var th = $(this).next().find('th');
								 if ($(th) && $(th).length>0){
								 	var count = $(th).length;
								 	if (count>0) isNextTH = true;  // is TH
								 }

			    			  if (isNextTH){
			    				  var domTH1 = $(this)[0];
			    				  var domTH2 = $(this).next()[0];
			    				  objPageHead = domTH1.outerHTML+domTH2.outerHTML;
			    				  tPageHead_height = $(this).outerHeight() + $(this).next().outerHeight();
			    				  tPage_Height = tableHeader_height;
			    			  } else{
				    			  objPageHead = $(this)[0].outerHTML;              //取得表头
				    			  tPageHead_height = trHeight;
				    			  tPage_Height = tableHeader_height;		//取得表头高度
			    			  }
			    			  objPointer = $("#TiaoPeiShiJianPrint");   //第一个对象是在调配时间的对象后面
			    		  }else{
			    		  //另起一页
				    		  objPointer = $("#coordTable_div"+tableIndex); //重新指向div
			    		  }
			    		  tableIndex++;
			    		  
			    		  //==========增加<table></table>
			    		  objPointer.after("<div class='A3' id='coordTable_div"+tableIndex+"'></div>"); //增加一个DIV
                       	  var scodtype=$("#yntype").val()=="TPHJ2"?1458:($("#yntype").val()=="TPHJ3"?1458:1191);
			    		  var emptyTableScript = "<table id='coordTable"+tableIndex+"' class='coordTable' id='coordTable' cellspacing='0' style='width:"+scodtype+"px' ><tbody></tbody></table>";
			    		  //==========增加<table><tr><th></tr></tr></table> 增加表头
			    		  $("#coordTable_div"+tableIndex).append(emptyTableScript);  //增加空表
			    		  $("#coordTable"+tableIndex+" tbody").html(objPageHead); //添加表头
			    		  tPage_Height+= tPageHead_height;
			   
			    		  $("#coordTable_div"+tableIndex).append("<div id='br_"+tableIndex+"' class='noprint' style='width:"+A3_WIDTH+";' ><br style='width:"+A3_WIDTH+";'><hr style='width:"+A3_WIDTH+";border:1px dotted #036' /><br style='width:"+A3_WIDTH+";'></div>");
			    		   
			    		  if (tableIndex>1){ 
			    			  //另启一页情况下，增加当前行
							  $("#coordTable"+tableIndex+" tbody").append("<tr>"+$(this)[0].outerHTML+"</tr>");
			    			  tPage_Height+=trHeight;			    			  
			    		  }
					} else {
						//alert($(this).html());
						var isTD = true;
						 var th = $(this).find('th');
						 if ($(th) && $(th).length>0){
						 	var count = $(th).length;
						 	if (count>0) isTD = false;  // is TH
						 }

						if (isTD){
							$("#coordTable"+tableIndex+" tbody").append("<tr>"+$(this)[0].outerHTML+"</tr>");
			    			tPage_Height+=trHeight;
						}
					}
				});

			//}catch(e){
			//	alert(e);
			//}

			$("#coordTable_div0").html("");

			$("#br_" + tableIndex).remove();
            $("#coordTable_div" + tableIndex).removeClass("A3");

            //$(".coordTable tr :first-child").css("width","0%");

            //alert("#coordTable_div"+tableIndex);
            //alert($("#coordTable"+tableIndex));
            //$("#coordTable_div0").css("display","none");
            //
            //
            //
            /*
            $("tbody tr th:eq(0)").each(function () {
                $(this).css("display", "none")
            });
            $("tbody tr td").each(function () {
                if ($(this).index() <= 0) {
                    $(this).css("display", "none");
                }

            });**/
            $("tbody tr td").each(function () {

                if ($(this).html().indexOf("img") < 0) {
                    var td = $(this);
                    SetTDtext(td, $(this).html());


                }
            });
            $("tbody .data td span  span").css("font-size",'')


        }

        function printHeight() {
            // alert($("#coordTable").outerHeight()+$(".BiaoTouP").outerHeight()+$(".TiaoPeiShiJian").outerHeight());

            $(".coordTable tr :first-child").css("width", "0%");

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

        function SetTDtext(td, v) {
    		if ($(td).html().toLowerCase().indexOf("img")>=0){
    			return; 
    		}
            try {
                $(td).html((v == "" || v == null || v == "null") ? "&nbsp;" : v.replace(/\n/g, "<br/>"));
                var scrollHeight = $(td)[0].scrollHeight;
                if (scrollHeight > 160) {
                    var fontSize = 15;
                    var index=0;
                    while (scrollHeight > 160){
                    	var s = "<span style='font-size:" + fontSize + "pt'>" + v.replace(/\n/g, "<br/>") + "</span>";
                    	$(td).html((v == "" || v == null || v == "null") ? "&nbsp;" : s);
                    	scrollHeight = $(td)[0].scrollHeight;
                    	fontSize--;
                    	
                    	index++;
                    	if (index>5) { 
                    		break;
                    	}
                    }
                    $(td).attr("height", 160);
                   // $(td).attr("title", v);
                } else {
                    $(td).removeAttr("height");
                    $(td).attr("title", "");
                }
            } catch (e) {
                alert(e);
            }
        }

        function SetTDPhoto(td, v) {
            var imgId = "personImg" + v.replace(/[-]/g, '');
            $(td).css("font-size:0;cellpading:0px;cellspacing:0px;");
            $(td).attr("height", 160);
            $(td).html("<img id='" + imgId + "' style=\"cellpading:0px;cellspacing:0px;border:0px;margin 0px;padding:0px;vertical-align:bottom;display:block;width:100%;height:160\" src='/hzb/servlet/DownloadUserHeadImage?a0000=" + v + "'/>");
            //{"background-color":"yellow","font-size":"200%"}
            //$(td).css({"background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","background-size":"40px 40px","background-repeat":"no-repeat","background-attachment":"fixed"});

            //昃鹏改的，暂时恢复
            //$(td).css({"height":"3.5cm","width":"100%","background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","-moz-background-size":" 100% 100%","-o-background-size":" 100% 100%","-webkit-background-size":" 100% 100%","background-size":" 100% 100%","-moz-border-image":" url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+") 0","background-repeat":"no-repeat","background-image":"none","filter":"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"', sizingMethod='scale')"});

        } 