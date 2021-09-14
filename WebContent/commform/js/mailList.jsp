<%@page contentType="text/javascript; charset=GBK"language="java"%>document.writeln('<iframe id=mailListLayer src="" close=true frameborder=0 style="position: absolute; width: 280; height: 400; z-index: 9998; display: none"></iframe>');var outObj1;var outObj2;var mailList=false;function setMailList(tt,disObjName,valObjName)
{if(arguments.length>4){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
var srcFrame=document.all.mailListLayer.src;if(""==srcFrame){document.all.mailListLayer.src="<%=request.getContextPath()%>/common/MailList.jsp";}
var dads=document.all.mailListLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;dads.display='';outObj1=arguments[1];outObj2=arguments[2];mailList=true;}
function setValue(select){var displayValue=undefined;var codeValue=undefined;var options=select.options;for(var i=0;i<options.length;i++){if(options[i].selected){displayValue=options[i].text;codeValue=options[i].value;}}
if(undefined==codeValue)
return;if(undefined!=parent.outObj2.value){if(-1!=parent.outObj2.value.indexOf(codeValue)){arrNames=parent.outObj1.value.split(/,/g);arrCodes=parent.outObj2.value.split(/,/g);var arrNewNames=new Array();var arrNewCodes=new Array();for(var i=0;i<arrCodes.length;i++){if(codeValue==arrCodes[i]){continue;}
arrNewNames[arrNewNames.length]=arrNames[i];arrNewCodes[arrNewCodes.length]=arrCodes[i];}
parent.outObj1.value="";parent.outObj2.value="";for(var i=0;i<arrNewCodes.length;i++){if(undefined==parent.outObj2.value||""==parent.outObj2.value){parent.outObj1.value=arrNewNames[i];parent.outObj2.value=arrNewCodes[i];}else{parent.outObj1.value=parent.outObj1.value+","+arrNewNames[i];parent.outObj2.value=parent.outObj2.value+","+arrNewCodes[i];}}
return;}}
if(undefined==parent.outObj2.value||""==parent.outObj2.value){parent.outObj1.value=displayValue;parent.outObj2.value=codeValue;}else{parent.outObj1.value=parent.outObj1.value+","+displayValue;parent.outObj2.value=parent.outObj2.value+","+codeValue;}}
function clearMailList(){parent.outObj1.value="";parent.outObj2.value="";parent.closeMailList()}
function selectAll(){parent.outObj1.value="";parent.outObj2.value="";var options=document.all.mailList.options;for(var i=1;i<options.length;i++){if(undefined==parent.outObj2.value||""==parent.outObj2.value){parent.outObj1.value=options[i].text;parent.outObj2.value=options[i].value;}else{parent.outObj1.value=parent.outObj1.value+","+options[i].text;parent.outObj2.value=parent.outObj2.value+","+options[i].value;}}}
function close_MailList_Frame(){parent.closeMailList()}
function closeMailList(){mailList=false;document.all.mailListLayer.style.display="none";}
function clearValue(obj1,obj2){obj1.value="";obj2.value="";}
function document.onclick(){with(window.event)
{if(mailList&&srcElement.getAttribute("close")!="true"&&srcElement!=outObj1){mailList=false;closeMailList();}}}