/******
 *  ���ڣ�2019-09-15
 * 	�޶�ʱ�䣺2019-09-29
 *  ��Ҫ���ܣ�
 *  	ʵ�ֶԶ���ɲ����һ����ʡί��ί��ɲ����һ����ʡί���ר�����ɲ����һ�����һ����
 *  �Ĵ�ӡ���ܣ���Ҫ����A3ֽ�ŵĺ����ӡ�������Զ���ҳ��
 */     
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
//�޸�ע����������ұ߾�,���ҳü,���ҳ��
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

 
        function printpreview() {
            // ��ӡҳ��Ԥ��
            $(".noprint").css("display", "none");
             if($("#yntype").val()=="TPHJ2"||$("#yntype").val()=="TPHJ3"){
               PageSetup_Null(10,10,18,18);//����
            } else{
                PageSetup_Null(10,10,30,30);//����
            }
            wb.execwb(7, 1); 
           
            $(".noprint").css("display", "");
        }

        function printit() {
            if (confirm('ȷ����ӡ��')) {
                wb.ExecWB(6, 1);
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

        function onloadPage() { 
			try{
        		$("body").removeClass("line-height");
			}catch(e){
				alert(e);
			}
            document.getElementById("typecore").innerHTML = (opener.document.getElementById("yntype").outerHTML);
            document.getElementById("coordTable_div0").innerHTML = (opener.document.getElementById("coordTable").outerHTML);
            $(".BiaoTouP").html(opener.$("#BiaoTouTitle").html().replace("�������ڣ�", ""));
            $("#TiaoPeiShiJianPrint").html("(" + opener.$("#TiaoPeiShiJianPrint_year").html() + "��" + opener.$("#TiaoPeiShiJianPrint_month").html() + "��" + opener.$("#TiaoPeiShiJianPrint_date").html() + "��)");

			//var A3_HIGHT = (opener.$("#coordTable").width() * 297)/ (420.0);
			var A3_HIGHT = 1000;
			var A3_WIDTH = $("#yntype").val()=="TPHJ1"?1191: 1458;
			// alert(A3_HIGHT)
			 //��ͷ�ĸ߶� BiaoTouP
			 //body �߶�
			 //tr�߶�
			var tableHeader_height = $(".JiMi").outerHeight()+($(".BiaoTouP").outerHeight()+40)+$(".TiaoPeiShiJian").outerHeight();
			var tCoordTable_height = $("#coordTable").outerHeight();
			var tPageHead_height = opener.$("#coordTable table tr").outerHeight();

			var tPage_Height = 0;  //ԭ����һ����ͷ
			var objPointer = null;	//��������ָ��

			var tableIndex = 0;	//���ɱ������

			var objPageTRHead = null; //ҳͷ
			//try{
				$("#coordTable_div0 table tbody").find('tr').each(function (index, item) {
				//opener.$("#coordTable_div tbody").find('tr').each(function (index, item) {
					var trHeight = $(this).outerHeight();	//�и�
					if (tableIndex <=0 ||  (tPage_Height + trHeight) >= A3_HIGHT || tPage_Height <=0 ){
						//���ɱ�DIV�ͱ��
						  tPage_Height=0; //���ñ�ͷ�ĸ�
			    		  if (tableIndex<=0){
			    		  //��һҳ
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
				    			  objPageHead = $(this)[0].outerHTML;              //ȡ�ñ�ͷ
				    			  tPageHead_height = trHeight;
				    			  tPage_Height = tableHeader_height;		//ȡ�ñ�ͷ�߶�
			    			  }
			    			  objPointer = $("#TiaoPeiShiJianPrint");   //��һ���������ڵ���ʱ��Ķ������
			    		  }else{
			    		  //����һҳ
				    		  objPointer = $("#coordTable_div"+tableIndex); //����ָ��div
			    		  }
			    		  tableIndex++;
			    		  
			    		  //==========����<table></table>
			    		  objPointer.after("<div class='A3' id='coordTable_div"+tableIndex+"'></div>"); //����һ��DIV
                       	  var scodtype=$("#yntype").val()=="TPHJ2"?1458:($("#yntype").val()=="TPHJ3"?1458:1191);
			    		  var emptyTableScript = "<table id='coordTable"+tableIndex+"' class='coordTable' id='coordTable' cellspacing='0' style='width:"+scodtype+"px' ><tbody></tbody></table>";
			    		  //==========����<table><tr><th></tr></tr></table> ���ӱ�ͷ
			    		  $("#coordTable_div"+tableIndex).append(emptyTableScript);  //���ӿձ�
			    		  $("#coordTable"+tableIndex+" tbody").html(objPageHead); //��ӱ�ͷ
			    		  tPage_Height+= tPageHead_height;
			   
			    		  $("#coordTable_div"+tableIndex).append("<div id='br_"+tableIndex+"' class='noprint' style='width:"+A3_WIDTH+";' ><br style='width:"+A3_WIDTH+";'><hr style='width:"+A3_WIDTH+";border:1px dotted #036' /><br style='width:"+A3_WIDTH+";'></div>");
			    		   
			    		  if (tableIndex>1){ 
			    			  //����һҳ����£����ӵ�ǰ��
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

            //����ĵģ���ʱ�ָ�
            //$(td).css({"height":"3.5cm","width":"100%","background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","-moz-background-size":" 100% 100%","-o-background-size":" 100% 100%","-webkit-background-size":" 100% 100%","background-size":" 100% 100%","-moz-border-image":" url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+") 0","background-repeat":"no-repeat","background-image":"none","filter":"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"', sizingMethod='scale')"});

        } 