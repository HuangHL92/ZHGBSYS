<%@page contentType="text/javascript; charset=GBK"language="java"%>function LEMIS(){this.WEB_APP_NAME="<%=request.getContextPath()%>";}
var leaf=new LEMIS();function getFormValue(frm){var row="";if(frm.elements.length){for(var i=0;i<frm.length;i++){var obj=frm[i];if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){var name=obj.name;var value=obj.value;row=row+name+"="+value+"&";}}}
return row;}
function getAlldata(obj){var row="";var flag=0;if(obj.length){for(var i=0;i<obj.length;i++){if(obj(i).type!="submit"&&obj(i).type!="reset"&&obj(i).type!="button"){if(obj(i).type=="radio"||obj(i).type=="checkbox"){if(obj(i).checked){flag=1;}else{flag=0;}}else{if(flag==1){var name=obj(i).name;var value=obj(i).value;row=row+name+"="+value+"&";}}}}}else{if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(obj.type=="radio"||obj.type=="checkbox"){if(obj.checked){flag=1;}else{flag=0;}}else{if(flag==1){var name=obj.name;var value=obj.value;row=row+name+"="+value+"&";}}}}
return row;}
function clearForm(obj,aryDef){if(obj.length){for(var i=0;i<obj.length;i++){if(obj(i).type!="submit"&&obj(i).type!="reset"&&obj(i).type!="button"){if(obj(i).type=="hidden"){if(obj(i).name=="aaa020"||obj(i).name=="aca111"){obj(i).value='';}}else{if(!isInAry(obj(i).name,aryDef))
obj(i).value='';}}}}else{if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(obj.type=="hidden"){if(obj.name=="aaa020"||obj.name=="aca111"){obj.value='';}}else{if(!isInAry(obj(i).name,aryDef))
obj.value='';}}}}
function isInAry(name,ary){for(var i=0;i<ary.length;i++){if(name==ary[i]){return true;break;}}
return false;}
function resetForm(obj){clearForm(obj,new Array());}
function replaceStr(str)
{str=str.replace(/%/g,"%25");str=str.replace(/&/g,"%26");str=str.replace(/\\/g,"&#92;");str=str.replace(/</g,"&#60;");str=str.replace(/>/g,"&#62;");str=str.replace(/\"/g,"&#34;");str=str.replace(/ /g,"&nbsp;");return str;}
function checkValue(formObj){var obj;var form=formObj;if(undefined!=form.elements){for(i=0;i<form.elements.length;i++){obj=form[i];if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(!validate(obj)){obj.focus();return false;}}}}else{obj=form;if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(!validate(obj)){obj.focus();return false;}}}
return true;}
function checkMask(formObj){var obj;var form=formObj;if(undefined!=form.elements){for(i=0;i<form.elements.length;i++){obj=form[i];if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(!validateM(obj)){obj.focus();return false;}}}}else{obj=form;if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(!validateM(obj)){obj.focus();return false;}}}
return true;}
function validate(obj){if(undefined!=obj.required&&"true"==obj.required){return checkEmpty(obj);}
return true;}
function validateM(obj){if(undefined!=obj.mask){return validateMask(obj);}
return true;}
function validateMask(obj){str=trimValue(obj.value);obj.value=str;if(undefined==str||""==str||null==str)
return true;if("date"==obj.mask)
return validateDate(obj);else if("shortdate"==obj.mask)
return validateShortDate(obj);else if("mediumdate"==obj.mask)
return validateMediumDate(obj);else if("money"==obj.mask)
return validateMoney(obj);else if("bigmoney"==obj.mask)
return validateBigMoney(obj);else if("card"==obj.mask)
return validateCard(obj);else if("period"==obj.mask)
return validatePeriod(obj);else if("######"==obj.mask){return validatePostalcode(obj);}else if("yearmonth"==obj.mask){return timeinput(obj);}else if("url"==obj.mask){return verifyURL(obj);}else if("email"==obj.mask){return isValidEMail(obj);}else if("tomonth"==obj.mask){return toMonth(obj);}else{return validateNumber(obj);}
return true;}
function checkEmpty(element){if(element.value==null||element.value==""||element.value==undefined){if(element.label==undefined){alert("不能为空!");element.select();}else{alert(element.label+"不能为空!");element.focus();}
return false;}else{return true;}}
function validateDate(element){str=element.value;if(str.length==8){var m=str.substr(4,1);var n=str.substr(6,1);if(m=="/"&&n=="/"){str=str.substr(0,4)+"-0"+str.substr(5,1)+"-0"+str.substr(7,1);element.value=str;}else if(m=="-"&&n=="-"){str=str.substr(0,4)+"-0"+str.substr(5,1)+"-0"+str.substr(7,1);element.value=str;}else if(m!="-"&&n!="-"&&m!="/"&&n!="/"){str=str.substr(0,4)+"-"+str.substr(4,2)+"-"+str.substr(6,2);element.value=str;}}else if(str.length==10){var m=str.substr(4,1);var n=str.substr(7,1);if(m=="/"&&n=="/"){str=str.substr(0,4)+"-"+str.substr(5,2)+"-"+str.substr(8,2);element.value=str;}}else if(str.length==9){var m=str.substr(4,1);var o=str.substr(6,1);var n=str.substr(7,1);if(m=="/"&&o=="/"){str=str.substr(0,4)+"-0"+str.substr(5,1)+"-"+str.substr(7,2);element.value=str;}else if(m=="-"&&o=="-"){str=str.substr(0,4)+"-0"+str.substr(5,1)+"-"+str.substr(7,2);element.value=str;}else if(m=="-"&&n=="-"){str=str.substr(0,4)+"-"+str.substr(5,2)+"-0"+str.substr(8,1);element.value=str;}else if(m=="/"&&n=="/"){str=str.substr(0,4)+"-"+str.substr(5,2)+"-0"+str.substr(8,1);element.value=str;}}
var r=str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);if(r==null){alert("日期输入不合法，格式应为\"2004-05-01\"!");element.value="";element.select();return false;}
var d=new Date(r[1],r[3]-1,r[4]);if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]){return true;}else{alert("日期输入不合法，格式应为\"2004-05-01\"!");element.value="";element.select();return false;}}
function validateShortDate(element){str=element.value;var r=str.match(/\d{4}/);if(r==null||"0"==str.charAt(0)){alert("日期输入不合法，格式应为\"2004\"!");element.select();return false;}else{return true;}}
function validateMediumDate(element){str=element.value;var r=str.match(/^(\d{4})(\/)(\d{1,2})$/);if(r==null){alert("日期输入不合法，格式应为\"2004/05\"!");element.select();return false;}
var d=new Date(r[1],r[3]-1,"11");if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()=="11"){return true;}else{alert("日期输入不合法，格式应为\"2004/05\"!");element.select();return false;}}
function validateMoney(element){str=element.value;if(str.indexOf(".")==0){str="0"+str;element.value=str;}
if(str.lastIndexOf(".")==(str.length-1)){str=str+"00";element.value=str;}
var r=str.match(/^\d{1,6}(\.\d{1,2})?$/);if(r==null||("0"==r[1].charAt(0)&&r[1].length>1)){alert("数字输入不合法，格式应为\"123456.77\"!");element.select();return false;}else{return true;}}
function validateBigMoney(element){str=element.value;if(str.indexOf(".")==0){str="0"+str;element.value=str;}
if(str.lastIndexOf(".")==(str.length-1)){str=str+"00";element.value=str;}
var r=str.match(/^\d{1,10}(\.\d{1,2})?$/);if(r==null||("0"==r[1].charAt(0)&&r[1].length>1)){alert("数字输入不合法，格式应为\"1234512345.77\"!");element.select();return false;}else{return true;}}
function validateNumber(element){str=element.value;var mask=element.mask;var indexDot=mask.indexOf('.');var intLen=mask.length;var decimalLen=0;if(-1!=indexDot){intLen=mask.substr(0,indexDot).length;decimalLen=mask.length-intLen-1;}
if(str.indexOf('.')==0){str="0"+str;element.value=str;}
if(str.lastIndexOf(".")==(str.length-1)){if(-1!=indexDot){for(var i=0;i<decimalLen;i++){str=str+"0";}
element.value=str;}}
var indexDot1=str.indexOf('.');var mask=element.mask;var indexDot=mask.indexOf('.');var indexDot1=str.indexOf('.');var intLen=mask.length;var decimalLen=0;if(-1!=indexDot){intLen=mask.substr(0,indexDot).length;decimalLen=mask.length-intLen-1;}
var intStrLen=str.length;var decimalStrLen=0;if(-1!=str.indexOf('.')){intStrLen=str.substr(0,indexDot1).length;decimalStrLen=str.length-intStrLen-1;}
var msg="数字输入不合法，格式应为最大不超过"+intLen+"位整数(不能以0开头)";if(decimalLen>0)
msg=msg+"和"+decimalLen+"位小数!";var r=str.match(/^\d+(\.\d+)?$/);if(r==null||str.length>mask.length||intStrLen>intLen||decimalStrLen>decimalLen){alert(msg);element.select();return false;}
if(-1!=indexDot1){if("0"==str.charAt(0)&&indexDot1!=1){alert(msg);element.select();return false;}}else{if("0"==str.charAt(0)&&str.length>1){alert(msg);element.select();return false;}}
return true;}
function validatePostalcode(element){str=element.value;var r=str.match(/^(\d{6})$/);if(r==null||"0"==str.charAt(0)){alert("邮政编码输入不合法，格式应为\"100013\"的6位整数（不以0开头）!");element.select();return false;}
return true;}
function validateCard(element){var inputStr=element.value;var inputPro="身份证号码";if(inputStr==null)inputStr="";var format=inputStr.length;if(inputStr=="")return true;if(format==15||format==18){if(!checkID(inputStr,inputPro,format,element))
return false;}else{alert("身份证的位数输入不正确，请重新输入！");element.select();return false;}
if((format==18)&&(!checkCheckStr(inputStr,element)))return false;if(!is0AndPosInteger(inputStr,element)){alert(inputPro+"输入不合法，请重新输入！");return false;}
return true;}
function checkCheckStr(inputStr,element){var aCity="11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";var iSum=0;var info="";inputStr=inputStr.replace(/x$/i,"a");var curCity=inputStr.substr(0,2);if(!(aCity.indexOf(curCity)>0))
{alert("身份证输入不合法！头两位错误，请重新输入！");element.select();return false;}
for(var i=17;i>=0;i--)
iSum+=(Math.pow(2,i)%11)*parseInt(inputStr.charAt(17-i),11);if(iSum%11!=1)
{alert("身份证输入不合法！校验位错误，请重新输入！");element.select();return false;}
return true;}
function validate_date(objName,_date,element){var temp;var year,month,day;temp=_date.substring(0,4);year=parseInt(temp,10);temp=_date.substring(4,6);month=parseInt(temp,10);temp=_date.substring(6,8);day=parseInt(temp,10);if(year<1900||year>2200){alert(objName+"年份应介于1900与2200之间，请重新输入！");element.select();return false;}
if(month<1||month>12){alert(objName+"月份必须介于1与12之间，请重新输入！");element.select();return false;}
if((day==0)||(day>31)){alert(objName+"日必须介于0与31之间，请重新输入！");element.select();return false;}else if(day>28&&day<31){if(month==2){if(day!=29){alert(objName+year+"年"+month+"月无"+day+"日，请重新输入！");element.select();return false;}else{if((year%4)!=0){alert(objName+year+"年"+month+"月无"+day+"日，请重新输入！");element.select();return false;}else{if((year%100==0)&&(year%400!=0)){alert(objName+year+"年"+month+"月无"+day+"日，请重新输入！");element.select();return false;}}}}}else if(day==31){if((month==2)||(month==4)||(month==6)||(month==9)||(month==11)){alert(objName+month+"月无"+day+"日，请重新输入！");element.select();return false;}}
return true;}
function checkID(inputStr,objName,format,element){var _date;if(inputStr.length!=format){alert(objName+"格式不对,应为“"+format+"”位，请重新输入！");element.select();return false;}else{if(format==18){_date=inputStr.substring(6,14);}else if(format==15){_date="19"+inputStr.substring(6,12);}
return validate_date(objName,_date,element);}
return true;}
function is0AndPosInteger(inputVal,element){var format=inputVal.length;if(format==18){var lastChar=inputVal.charAt(inputVal.length-1)
if(lastChar=="X")
inputVal=inputVal.substring(0,inputVal.length-1);}
for(var i=0;i<inputVal.length;i++){var oneChar=inputVal.charAt(i)
if(oneChar<"0"||oneChar>"9"){return false;}}
return true;}
function validatePeriod(element){inputStr=element.value;if(undefined!=inputStr&&""!=inputStr&&null!=inputStr){var r=inputStr.match(/^\d{6}$/);if(r==null){alert("年月中的月份不合法，请重新输入！");element.select();return false;}
if(inputStr.substr(4,2)>12||inputStr.substr(4,2)<1){alert("年月中的月份不合法，请重新输入！");element.select();element.select();return false;}
if(inputStr.substr(0,4)<1900){alert("年月中的年份不能小于1900，请重新输入！");element.select();return false;}}
return true;}
function trimValue(val){if(undefined==val)
return val;var value=val.replace(/_/g,"");value=value.replace(/ /ig,"");return value;}
function preCheckForBatch(){var obj=document.all("tableform");if(obj.length){for(i=0;i<obj.length;i++){if(obj(i).type!="submit"&&obj(i).type!="reset"&&obj(i).type!="button"){if(obj(i).type=="radio"||obj(i).type=="checkbox"){if(obj(i).checked){flag=1;}else{flag=0;}}else{if(1==flag&&"true"==obj(i).isEAF){if(!validate(obj(i))){return false;}}}}}}else{if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(obj.type=="radio"||obj.type=="checkbox"){if(obj.checked){flag=1;}else{flag=0;}}else{if(1==flag&&"true"==obj.isEAF){if(!validate(obj)){return false;}}}}}
return true;}
function newBatch(){var obj=document.all("tableform");if(obj.length){for(i=0;i<obj.length;i++){if(obj(i).type!="submit"&&obj(i).type!="reset"&&obj(i).type!="button"){if(obj(i).type=="radio"||obj(i).type=="checkbox"){if(obj(i).checked){obj(i).checked=false;}}else{if("hidden"!=obj(i).type){obj(i).value='';}}}}}else{if(obj.type!="submit"&&obj.type!="reset"&&obj.type!="button"){if(obj.type=="radio"||obj.type=="checkbox"){if(obj.checked){obj.checked=false;}}else{if("hidden"!=obj.type){obj.value='';}}}}}
function page_init()
{try{setFocus();}catch(e){}}
function setFocus(){var form=document.forms[0];if(!form){return;}
var controls=form.elements;for(var i=0;i<controls.length;i++){var firstControl=controls[i];if(!(firstControl.type=="hidden"||firstControl.readOnly=="true"||firstControl.disabled=="true")){firstControl.focus();break;}}}
function selectall(obj)
{if(null==obj)return false;var num=obj.length;if(document.all("checkall").checked){if(num==null){obj.checked=true;}
else{for(var i=0;i<num;i++){if(!obj[i].disabled){obj[i].checked=true;}}}}
else
{if(num==null){obj.checked=false;}
else{for(var i=0;i<num;i++)
obj[i].checked=false;}}}
function checkItem(checkListName,controlCheck)
{var aryChecked=document.all(checkListName);if(aryChecked!=null)
{if(aryChecked.length!=null&&aryChecked.length>0)
{var nCount=0;for(var i=0;i<aryChecked.length;i++)
{if(aryChecked[i].checked)
nCount++;}
if(nCount==aryChecked.length){controlCheck.checked=true;}else{controlCheck.checked=false;}}
else
{controlCheck.checked=aryChecked.checked;}}}
function editObj(name){var checkObj=document.all(name);if(checkObj){if(checkObj.length){for(var i=0,j=0;i<checkObj.length&&j<=2;i++){if(checkObj[i].checked){j++;}}
if(j>1){alert("只能选择一条业务数据！");return false;}else if(j==0){alert("请选择业务数据！");return false;}}else{if(!checkObj.checked){alert("请选择业务数据");return false;}}}else{alert("当前没有可操作业务数据！");return false;}
return true;}
function delObj(name){var checkObj=document.all(name);if(checkObj){if(checkObj.length){for(var i=0,j=0;i<checkObj.length;i++){if(checkObj[i].checked){j++;break;}}
if(j==0){alert("请选择业务数据！");return false;}}else{if(!checkObj.checked){alert("请选择业务数据！");return false;}}}else{alert("当前没有可操作的业务数据！");return false;}
return true;}
function getRowData(obj){var obj_col=obj.parentElement;if(obj_col==null)
{return;}
var obj_row=obj_col.parentElement;if(obj_row==null)
{return;}
var rowData=new Array();var iLength=obj_row.childNodes.length;var iNum=0;for(var i=0;i<iLength;i++)
{rowData[iNum]=obj_row.childNodes(i).innerText;iNum++;}
return rowData;}
function getEditData(name){var obj=document.all.tags("input");var rowdata=null;var str="";for(var i=0;i<obj.length;i++)
{if(obj[i].type=="checkbox"){if(obj[i].name==name&&obj[i].checked){rowdata=getRowData(obj[i]);break;}}}
return rowdata;}
function checkRadio(objRadio){if(objRadio==undefined)
return null;if(objRadio.length==undefined){return objRadio.checked;}else{for(var i=0;i<objRadio.length;i++){if(objRadio[i].checked)
return true;}
return false;}}
function checkRadio(objRadio){if(objRadio==undefined)
return true;if(objRadio.length==undefined){return objRadio.checked;}else{for(var i=0;i<objRadio.length;i++){if(objRadio[i].checked)
return true;}
return false;}}
var bMoveable=true;var regionTree=false;var workTypeTree=false;var sysfTree=false;var calendar=false;var _VersionInfo="Version:2.0&#13;2.0作者:walkingpoison&#13;1.0作者: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com"
var strFrame;document.writeln('<iframe id=meizzDateLayer Author=wayx frameborder=0 style="position: absolute; width: 144; height: 211; z-index: 9998; display: none"></iframe>');strFrame='<style>';strFrame+='INPUT.button{BORDER-RIGHT: #8FB1F3 1px solid;BORDER-TOP: #8FB1F3 1px solid;BORDER-LEFT: #8FB1F3 1px solid;';strFrame+='BORDER-BOTTOM: #8FB1F3 1px solid;BACKGROUND-COLOR: #fff8ec;font-family:宋体;}';strFrame+='TD{FONT-SIZE: 9pt;font-family:宋体;}';strFrame+='</style>';strFrame+='<scr'+'ipt>';strFrame+='var datelayerx,datelayery;   /*存放日历控件的鼠标位置*/';strFrame+='var bDrag;   /*标记是否开始拖动*/';strFrame+='function document.onmousemove()  /*在鼠标移动事件中，如果开始拖动日历，则移动日历*/';strFrame+='{if(bDrag && window.event.button==1)';strFrame+=' {var DateLayer=parent.document.all.meizzDateLayer.style;';strFrame+='     DateLayer.posLeft += window.event.clientX-datelayerx;/*由于每次移动以后鼠标位置都恢复为初始的位置，因此写法与div中不同*/';strFrame+='     DateLayer.posTop += window.event.clientY-datelayery;}}';strFrame+='function DragStart()     /*开始日历拖动*/';strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';strFrame+=' datelayerx=window.event.clientX;';strFrame+=' datelayery=window.event.clientY;';strFrame+=' bDrag=true;}';strFrame+='function DragEnd(){      /*结束日历拖动*/';strFrame+=' bDrag=false;}';strFrame+='</scr'+'ipt>';strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false"><span id=tmpSelectYearLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 19;display: none"></span>';strFrame+='<span id=tmpSelectMonthLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 78;display: none"></span>';strFrame+='<table border=1 cellspacing=0 cellpadding=0 width=142 height=160 bordercolor=#8FB1F3 bgcolor=#8FB1F3 Author="wayx">';strFrame+='  <tr Author="wayx"><td width=142 height=23 Author="wayx" bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=140 Author="wayx" height=23>';strFrame+='      <tr align=center Author="wayx"><td width=16 align=center bgcolor=#8FB1F3 style="font-size:12px;cursor: hand;color: #ffffff" ';strFrame+='        onclick="parent.meizzPrevM()" title="向前翻 1 月" Author=meizz><b Author=meizz>&lt;</b>';strFrame+='        </td><td width=60 align=center style="font-size:12px;cursor:default" Author=meizz ';strFrame+='onmouseover="style.backgroundColor=\'#FFD700\'" onmouseout="style.backgroundColor=\'white\'" ';strFrame+='onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="点击这里选择年份"><span Author=meizz id=meizzYearHead></span></td>';strFrame+='<td width=48 align=center style="font-size:12px;cursor:default" Author=meizz onmouseover="style.backgroundColor=\'#FFD700\'" ';strFrame+=' onmouseout="style.backgroundColor=\'white\'" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))"';strFrame+='        title="点击这里选择月份"><span id=meizzMonthHead Author=meizz></span></td>';strFrame+='        <td width=16 bgcolor=#8FB1F3 align=center style="font-size:12px;cursor: hand;color: #ffffff" ';strFrame+='         onclick="parent.meizzNextM()" title="向后翻 1 月" Author=meizz><b Author=meizz>&gt;</b></td></tr>';strFrame+='    </table></td></tr>';strFrame+='  <tr Author="wayx"><td width=142 height=18 Author="wayx">';strFrame+='<table border=1 cellspacing=0 cellpadding=0 bgcolor=#8FB1F3 '+(bMoveable?'onmousedown="DragStart()" onmouseup="DragEnd()"':'');strFrame+=' BORDERCOLORLIGHT=#8FB1F3 BORDERCOLORDARK=#FFFFFF width=140 height=20 Author="wayx" style="cursor:'+(bMoveable?'move':'default')+'">';strFrame+='<tr Author="wayx" align=center valign=bottom><td style="font-size:12px;color:#FFFFFF" Author=meizz>日</td>';strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>一</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>二</td>';strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>三</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>四</td>';strFrame+='<td style="font-size:12px;color:#FFFFFF" Author=meizz>五</td><td style="font-size:12px;color:#FFFFFF" Author=meizz>六</td></tr>';strFrame+='</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->';strFrame+='  <tr Author="wayx"><td width=142 height=120 Author="wayx">';strFrame+='    <table border=1 cellspacing=2 cellpadding=0 BORDERCOLORLIGHT=#8FB1F3 BORDERCOLORDARK=#FFFFFF bgcolor=#fff8ec width=140 height=120 Author="wayx">';var n=0;for(j=0;j<5;j++){strFrame+=' <tr align=center Author="wayx">';for(i=0;i<7;i++){strFrame+='<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px" Author=meizz onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+='      <tr align=center Author="wayx">';for(i=35;i<39;i++)strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px" Author=wayx onclick="parent.meizzDayClick(this.innerText,0)"></td>';strFrame+='        <td colspan=3 align=right Author=meizz><span onclick=parent.closeLayer() style="font-size:12px;cursor: hand"';strFrame+='         Author=meizz title="'+_VersionInfo+'"><u>关闭</u></span>&nbsp;</td></tr>';strFrame+='    </table></td></tr><tr Author="wayx"><td Author="wayx">';strFrame+='        <table border=0 cellspacing=1 cellpadding=0 width=100% Author="wayx" bgcolor=#FFFFFF>';strFrame+='          <tr Author="wayx"><td Author=meizz align=left><input Author=meizz type=button class=button value="<<" title="向前翻 1 年" onclick="parent.meizzPrevY()" ';strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px"><input Author=meizz class=button title="向前翻 1 月" type=button ';strFrame+='             value="< " onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';strFrame+='             Author=meizz align=center><input Author=meizz type=button class=button value=今日 onclick="parent.meizzToday()" ';strFrame+='             onfocus="this.blur()" title="当前日期" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';strFrame+='             Author=meizz align=right><input Author=meizz type=button class=button value=" >" onclick="parent.meizzNextM()" ';strFrame+='             onfocus="this.blur()" title="向后翻 1 月" class=button style="font-size: 12px; height: 20px"><input ';strFrame+='             Author=meizz type=button class=button value=">>" title="向后翻 1 年" onclick="parent.meizzNextY()"';strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px"></td>';strFrame+='</tr></table></td></tr></table></div>';window.frames.meizzDateLayer.document.writeln(strFrame);window.frames.meizzDateLayer.document.close();var outObject;var outButton;var outDate="";var odatelayer=window.frames.meizzDateLayer.document.all;function setday(tt)
{if(arguments.length>2){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
calendar=true;leafTree=false;var dads=document.all.meizzDateLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;outObject=th;outButton=(arguments.length==1)?null:th;var reg=/^(\d+)-(\d{1,2})-(\d{1,2})$/;var r=outObject.value.match(reg);if(r!=null){r[2]=r[2]-1;var d=new Date(r[1],r[2],r[3]);if(d.getFullYear()==r[1]&&d.getMonth()==r[2]&&d.getDate()==r[3]){outDate=d;}
else outDate="";meizzSetDay(r[1],r[2]+1);}
else{outDate="";meizzSetDay(new Date().getFullYear(),new Date().getMonth()+1);}
dads.display='';event.returnValue=false;}
var MonHead=new Array(12);MonHead[0]=31;MonHead[1]=28;MonHead[2]=31;MonHead[3]=30;MonHead[4]=31;MonHead[5]=30;MonHead[6]=31;MonHead[7]=31;MonHead[8]=30;MonHead[9]=31;MonHead[10]=30;MonHead[11]=31;var meizzTheYear=new Date().getFullYear();var meizzTheMonth=new Date().getMonth()+1;var meizzWDay=new Array(39);function document.onclick(){with(window.event)
{if(workTypeTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=workType_outObject1){regionTree=false;workTypeTree=false;sysfTree=false;calendar=false;workTypeTree_close();}}else if(regionTree){}else if(sysfTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=region_outObject1){regionTree=false;workTypeTree=false;sysfTree=false;calendar=false;sysfTree_close();}}else if(calendar){if(srcElement.getAttribute("Author")!="wayx"&&srcElement!=outObject&&srcElement!=outButton)
{regionTree=false;workTypeTree=false;sysfTree=false;calendar=false;closeLayer();}}}}
function document.onkeyup()
{if(window.event.keyCode==27){if(outObject)outObject.blur();closeLayer();}
else if(document.activeElement)
if(document.activeElement.getAttribute("Author")==null&&document.activeElement!=outObject&&document.activeElement!=outButton)
{closeLayer();}}
function meizzWriteHead(yy,mm)
{odatelayer.meizzYearHead.innerText=yy+" 年";odatelayer.meizzMonthHead.innerText=mm+" 月";}
function tmpSelectYearInnerHTML(strYear)
{if(strYear.match(/\D/)!=null){alert("年份输入参数不是数字！");return;}
var m=(strYear)?strYear:new Date().getFullYear();if(m<1000||m>9999){alert("年份值不在 1000 到 9999 之间！");return;}
var n=m-100;if(n<1000)n=1000;if(n+26>9999)n=9974;var s="<select Author=meizz name=tmpSelectYear style='font-size: 12px' "
s+="onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
s+="onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
s+="parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";var selectInnerHTML=s;for(var i=n;i<n+150;i++)
{if(i==m)
{selectInnerHTML+="<option Author=wayx value='"+i+"' selected>"+i+"年"+"</option>\r\n";}
else{selectInnerHTML+="<option Author=wayx value='"+i+"'>"+i+"年"+"</option>\r\n";}}
selectInnerHTML+="</select>";odatelayer.tmpSelectYearLayer.style.display="";odatelayer.tmpSelectYearLayer.innerHTML=selectInnerHTML;odatelayer.tmpSelectYear.focus();}
function tmpSelectMonthInnerHTML(strMonth)
{if(strMonth.match(/\D/)!=null){alert("月份输入参数不是数字！");return;}
var m=(strMonth)?strMonth:new Date().getMonth()+1;var s="<select Author=meizz name=tmpSelectMonth style='font-size: 12px' "
s+="onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
s+="onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
s+="parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";var selectInnerHTML=s;for(var i=1;i<13;i++)
{if(i==m)
{selectInnerHTML+="<option Author=wayx value='"+i+"' selected>"+i+"月"+"</option>\r\n";}
else{selectInnerHTML+="<option Author=wayx value='"+i+"'>"+i+"月"+"</option>\r\n";}}
selectInnerHTML+="</select>";odatelayer.tmpSelectMonthLayer.style.display="";odatelayer.tmpSelectMonthLayer.innerHTML=selectInnerHTML;odatelayer.tmpSelectMonth.focus();}
function closeLayer()
{regionTree=false;workTypeTree=false;sysfTree=false;calendar=false;document.all.meizzDateLayer.style.display="none";}
function IsPinYear(year)
{if(0==year%4&&((year%100!=0)||(year%400==0)))return true;else return false;}
function GetMonthCount(year,month)
{var c=MonHead[month-1];if((month==2)&&IsPinYear(year))c++;return c;}
function GetDOW(day,month,year)
{var dt=new Date(year,month-1,day).getDay()/7;return dt;}
function meizzPrevY()
{if(meizzTheYear>999&&meizzTheYear<10000){meizzTheYear--;}
else{alert("年份超出范围（1000-9999）！");}
meizzSetDay(meizzTheYear,meizzTheMonth);}
function meizzNextY()
{if(meizzTheYear>999&&meizzTheYear<10000){meizzTheYear++;}
else{alert("年份超出范围（1000-9999）！");}
meizzSetDay(meizzTheYear,meizzTheMonth);}
function meizzToday()
{var today;meizzTheYear=new Date().getFullYear();meizzTheMonth=new Date().getMonth()+1;today=new Date().getDate();meizzSetDay(meizzTheYear,meizzTheMonth);if(outObject){if(meizzTheMonth<10)
meizzTheMonth="0"+meizzTheMonth;if(today<10)
today="0"+today;outObject.value=meizzTheYear+"-"+meizzTheMonth+"-"+today;}
closeLayer();}
function meizzPrevM()
{if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
meizzSetDay(meizzTheYear,meizzTheMonth);}
function meizzNextM()
{if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
meizzSetDay(meizzTheYear,meizzTheMonth);}
function meizzSetDay(yy,mm)
{meizzWriteHead(yy,mm);meizzTheYear=yy;meizzTheMonth=mm;for(var i=0;i<39;i++){meizzWDay[i]=""};var day1=1,day2=1,firstday=new Date(yy,mm-1,1).getDay();for(i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1
for(i=firstday;day1<GetMonthCount(yy,mm)+1;i++){meizzWDay[i]=day1;day1++;}
for(i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++}
for(i=0;i<39;i++)
{var da=eval("odatelayer.meizzDay"+i)
if(meizzWDay[i]!="")
{da.borderColorLight="#8FB1F3";da.borderColorDark="#FFFFFF";if(i<firstday)
{da.innerHTML="<b><font color=gray>"+meizzWDay[i]+"</font></b>";da.title=(mm==1?12:mm-1)+"月"+meizzWDay[i]+"日";da.onclick=Function("meizzDayClick(this.innerText,-1)");if(!outDate)
da.style.backgroundColor=((mm==1?yy-1:yy)==new Date().getFullYear()&&(mm==1?12:mm-1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0";else
{da.style.backgroundColor=((mm==1?yy-1:yy)==outDate.getFullYear()&&(mm==1?12:mm-1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())?"#00ffff":(((mm==1?yy-1:yy)==new Date().getFullYear()&&(mm==1?12:mm-1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0");if((mm==1?yy-1:yy)==outDate.getFullYear()&&(mm==1?12:mm-1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
{da.borderColorLight="#FFFFFF";da.borderColorDark="#8FB1F3";}}}
else if(i>=firstday+GetMonthCount(yy,mm))
{da.innerHTML="<b><font color=gray>"+meizzWDay[i]+"</font></b>";da.title=(mm==12?1:mm+1)+"月"+meizzWDay[i]+"日";da.onclick=Function("meizzDayClick(this.innerText,1)");if(!outDate)
da.style.backgroundColor=((mm==12?yy+1:yy)==new Date().getFullYear()&&(mm==12?1:mm+1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0";else
{da.style.backgroundColor=((mm==12?yy+1:yy)==outDate.getFullYear()&&(mm==12?1:mm+1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())?"#00ffff":(((mm==12?yy+1:yy)==new Date().getFullYear()&&(mm==12?1:mm+1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0");if((mm==12?yy+1:yy)==outDate.getFullYear()&&(mm==12?1:mm+1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
{da.borderColorLight="#FFFFFF";da.borderColorDark="#8FB1F3";}}}
else
{da.innerHTML="<b>"+meizzWDay[i]+"</b>";da.title=mm+"月"+meizzWDay[i]+"日";da.onclick=Function("meizzDayClick(this.innerText,0)");if(!outDate)
da.style.backgroundColor=(yy==new Date().getFullYear()&&mm==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0";else
{da.style.backgroundColor=(yy==outDate.getFullYear()&&mm==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())?"#00ffff":((yy==new Date().getFullYear()&&mm==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#FFD700":"#e0e0e0");if(yy==outDate.getFullYear()&&mm==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
{da.borderColorLight="#FFFFFF";da.borderColorDark="#8FB1F3";}}}
da.style.cursor="hand"}
else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}}}
function meizzDayClick(n,ex)
{var yy=meizzTheYear;var mm=parseInt(meizzTheMonth)+ex;if(mm<1){yy--;mm=12+mm;}
else if(mm>12){yy++;mm=mm-12;}
if(mm<10){mm="0"+mm;}
if(outObject)
{if(!n){return;}
if(n<10){n="0"+n;}
outObject.value=yy+"-"+mm+"-"+n;closeLayer();}
else{closeLayer();alert("您所要输出的控件对象并不存在！");}}
function replaceBlankAll(str){str=str.toString();if(str=="")
{return;}
var reg=/ /gi;return str.replace(reg,"");}
function replaceBlank(str){str=str.toString();if(str=="")
{return;}
var reg=/(^\s*|\s*$)/g;return str.replace(reg,"");}
function compareDate(max,min){var newMax=strToDate(max);var newMin=strToDate(min);if(newMax>=newMin){return true;}else{return false;}}
function strToDate(strDate){var tempDate=strDate;var index1=tempDate.indexOf(".");if(-1==index1)
index1=tempDate.indexOf("-");var index2=tempDate.lastIndexOf(".");if(-1==index2)
index2=tempDate.lastIndexOf("-");if((-1!=index1)||(-1!=index2)){var year=tempDate.substring(0,index1);var m=parseInt(tempDate.substring(index1+1,index2),10);var month=""+m;if(m<10)
month="0"+m;var d=parseInt(tempDate.substring(index2+1,tempDate.length),10);var day=""+d;if(d<10)
day="0"+d;tempDate=year+month+day;}
return tempDate;}
function getStrLength(str){var num=str.length;var arr=str.match(/[^\x00-\x80]/ig)
if(arr!=null)num+=arr.length;return num}
function getDateForCard(str){var inputStr=str.toString();var year;var month;var day;if(inputStr.length==18)
{year=parseInt(inputStr.substring(6,10),10).toString();month=parseInt(inputStr.substring(10,12),10).toString();day=parseInt(inputStr.substring(12,14),10).toString();}else{year=parseInt(inputStr.substring(6,8),10).toString();year="19"+year;month=parseInt(inputStr.substring(8,10),10).toString();day=parseInt(inputStr.substring(10,12),10).toString();}
if(month.length==1)
{month="0"+month;}
if(day.length==1)
{day="0"+day;}
return year+month+day;}
function getSexForCard(str){var inputStr=str.toString();var sex;if(inputStr.length==18)
{sex=inputStr.charAt(16);if(sex%2==0)
{return 2;}else{return 1;}}else{sex=inputStr.charAt(14);if(sex%2==0)
{return 2;}else{return 1;}}}
function compareMoney(maxMoney,minMoney){var max=0;var min=0;maxMoney=replaceBlank(maxMoney);minMoney=replaceBlank(minMoney);if(null!=maxMoney&&""!=maxMoney){max=parseInt(maxMoney.replace("￥","").replace(".",""));}
if(null!=minMoney&&""!=minMoney){min=parseInt(minMoney.replace("￥","").replace(".",""));}
if(max>=min){return true;}else{return false;}}
function moveRight(form,leftOption,rightOption,isAll){var leftSelect=document.all(form).all(leftOption);var rightSelect=document.all(form).all(rightOption);move(leftSelect,rightSelect,isAll);}
function moveLeft(form,leftOption,rightOption,isAll){var leftSelect=document.all(form).all(leftOption);var rightSelect=document.all(form).all(rightOption);move(rightSelect,leftSelect,isAll);}
function move(fromSelect,toSelect,isAll){fromOptions=fromSelect.options;var toSelectLength=0;if(toSelect.options){toSelectLength=toSelect.options.length;}
if(fromOptions.length){if(isAll){for(i=0;i<fromOptions.length;){var newOption=new Option(fromOptions[i].text,fromOptions[i].value,toSelectLength++);toSelect.add(newOption);fromSelect.remove(fromOptions[i].index);}}else{for(i=0;i<fromOptions.length;i++){if(fromOptions[i].selected){var newOption=new Option(fromOptions[i].text,fromOptions[i].value,++toSelectLength);toSelect.add(newOption);fromSelect.remove(fromOptions[i].index);i=0;}}}}}
function getSelectedData(form,selectedOption){var selected=document.all(form).all(selectedOption);if(selected==null)
{return null;}
var resultList="";if(selected.length){for(i=0;i<selected.length;i++){resultList+=selected[i].value+";";}}
return resultList;}
function computeForBatchInput(objID){}
function getInfoByCard(objID){}
function computeYearMonth(objID){}
function selectChange(objID){}
function closeWindow(formName){if(confirm("确信要关闭此窗口吗？")){var XMLHTTP=new ActiveXObject("Microsoft.XMLHTTP");XMLHTTP.open("POST","<%=request.getContextPath()%>/cleanSessionAction.do?name="+formName,false);XMLHTTP.send("");location.href="<%=request.getContextPath()%>/commform/Main.jsp";}}
var keyStr="ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"abcdefghijklmnopqrstuvwxyz"+"0123456789+/=";function encode64(inp)
{var out="";var chr1,chr2,chr3="";var enc1,enc2,enc3,enc4="";var i=0;do{chr1=inp.charCodeAt(i++);chr2=inp.charCodeAt(i++);chr3=inp.charCodeAt(i++);enc1=chr1>>2;enc2=((chr1&3)<<4)|(chr2>>4);enc3=((chr2&15)<<2)|(chr3>>6);enc4=chr3&63;if(isNaN(chr2)){enc3=enc4=64;}else if(isNaN(chr3)){enc4=64;}
out=out+keyStr.charAt(enc1)+keyStr.charAt(enc2)+keyStr.charAt(enc3)+
keyStr.charAt(enc4);chr1=chr2=chr3="";enc1=enc2=enc3=enc4="";}while(i<inp.length);return out;}
function decode64(inp)
{var out="";var chr1,chr2,chr3="";var enc1,enc2,enc3,enc4="";var i=0;var base64test=/[^A-Za-z0-9+/=]/g;if(base64test.exec(inp)){alert("There were invalid base64 characters in the input text.n"+"Valid base64 characters are A-Z, a-z, 0-9, ?+?, ?/?, and ?=?n"+"Expect errors in decoding.");}
inp=inp.replace(/[^A-Za-z0-9+/=]/g,"");do{enc1=keyStr.indexOf(inp.charAt(i++));enc2=keyStr.indexOf(inp.charAt(i++));enc3=keyStr.indexOf(inp.charAt(i++));enc4=keyStr.indexOf(inp.charAt(i++));chr1=(enc1<<2)|(enc2>>4);chr2=((enc2&15)<<4)|(enc3>>2);chr3=((enc3&3)<<6)|enc4;out=out+String.fromCharCode(chr1);if(enc3!=64){out=out+String.fromCharCode(chr2);}
if(enc4!=64){out=out+String.fromCharCode(chr3);}
chr1=chr2=chr3="";enc1=enc2=enc3=enc4="";}while(i<inp.length);return out;}
function TagaddUser(obj){var webapp=leaf.WEB_APP_NAME;src='/'+webapp+'/sysmanager/MainUser.do';style="help:no;status:no;dialogWidth:30;dialogHeight:30";reval=window.showModalDialog(src,obj,style);}
function TagclearUser(obj){obj.value="";obj.ids="";obj.groupids="";}
function GetElCoordinate(e)
{var t=e.offsetTop;var l=e.offsetLeft;var w=e.offsetWidth;var h=e.offsetHeight;while(e=e.offsetParent)
{t+=e.offsetTop;l+=e.offsetLeft;}
return{top:t,left:l,width:w,height:h,bottom:t+h,right:l+w}}
function GetAbsPoint(e)
{var oRect=e.getBoundingClientRect();return{top:oRect.top,left:oRect.left,width:e.offsetWidth,height:e.offsetHeight,bottom:oRect.bottom,right:oRect.right}}
function AjaxRequest(url,bizFunction,blockmode)
{var fe=document.activeElement;var myAjax=new Ajax.Request(url,{method:'get',onSuccess:doResponse,onFailure:doFail});if(blockmode){document.body.disabled=true;}
var divname="_AjaxProcessDiv";var div=document.getElementById(divname);if(!div){div=document.createElement("DIV");div.style.visibility="hidden";div.id=divname;document.body.appendChild(div);div.innerHTML="<img src='<%=request.getContextPath()%>/images/loading/m2006621247.gif'>正在加载...";div.style.position="absolute";div.style.zIndex=999;div.style.backgroundColor="#B8860B";}
div=document.getElementById(divname);var pos=GetAbsPoint(fe);div.style.left=pos.right-5;div.style.top=pos.bottom-5;div.style.visibility="visible";function doResponse(originalRequest)
{div.style.visibility="hidden";var responseText=originalRequest.responseText;var json=null;try
{json=eval('('+responseText+')');}
catch(err)
{alert("Ajax请求返回的数据非法！");throw err;}
var msg=json["com.lbs.leaf.Exception"];if(msg){alert(msg);}
if(blockmode){document.body.disabled=false;}
if(bizFunction){bizFunction(json);}}
function doFail(originalRequest)
{div.style.visibility="hidden";alert("Ajax请求失败！");if(blockmode){document.body.disabled=false;}}}
function EnterToTab(){var e=window.event.srcElement;var type=e.type;if(type!='button'&&type!='textarea'&&event.keyCode==13){event.keyCode=9;}}
function onlyInputNum(){if(event.keyCode<45||event.keyCode>57){if(event.keyCode==88){event.returnValue=true;}else{event.returnValue=false;}}}
function isValidEMail(Email){var EmailStr=Email.value;if(EmailStr=="")return true;var myReg=/[_a-zA-Z0-9]+@([_a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,3}$/;if(myReg.test(EmailStr))return true;else{alert("请输入有效的E-MAIL!");return false;}}
function checkMaskCard(obj,birthday,sex){if(validateMCard(obj)){if(setBirthday(obj,birthday,sex)){return true;}else{return false;}}else{return false;}}
function validateMCard(obj){if(undefined!=obj.mask){var mask=validateMask(obj);if(!mask){obj.focus();}
return mask;}
return true;}
function setBirthday(obj,birthday,sex){var input=obj.value;var strOldID=trimValue(input);if(strOldID.length==15){var year="19"+strOldID.substr(6,2)+"-"+strOldID.substr(8,2)+"-"+strOldID.substr(10,2);var gender=getSexForIDCard(strOldID);var name=document.getElementById(birthday);var sexname=document.getElementById(sex);if(name==null&&sexname==null){alert("出生日期和性别id不存在！");return false;}else{if(name!=null){name.value=year;}
if(sexname!=null){sexname.value=gender;}}
return true;}else if(strOldID.length==18){var year=strOldID.substr(6,4)+"-"+strOldID.substr(10,2)+"-"+strOldID.substr(12,2);var gender=getSexForIDCard(strOldID);var name=document.getElementById(birthday);var sexname=document.getElementById(sex);if(name==null&&sexname==null){alert("出生日期和性别id不存在！");return false;}else{if(name!=null){name.value=year;}
if(sexname!=null){sexname.value=gender;}}
return true;}else{return false;}}
function getSexForIDCard(str){var inputStr=str.toString();var sex;if(inputStr.length==18)
{sex=inputStr.charAt(16);if(sex%2==0)
{return 0;}else{return 1;}}else{sex=inputStr.charAt(14);if(sex%2==0)
{return 0;}else{return 1;}}}
function compareBirthSex(obj,birth,sex){if(validateMCard(obj)){var strOldID=trimValue(obj.value);if(strOldID.length==15){var year="19"+strOldID.substr(6,2)+"-"+strOldID.substr(8,2)+"-"+strOldID.substr(10,2);var gender=getSexForIDCard(strOldID);if(year==birth&&gender==sex){return true;}else{alert("身份证号码与出生日期、性别不一致！");return false;}}else if(strOldID.length==18){var year=strOldID.substr(6,4)+"-"+strOldID.substr(10,2)+"-"+strOldID.substr(12,2);var gender=getSexForIDCard(strOldID);if(year==birth&&gender==sex){return true;}else{alert("身份证号码与出生日期、性别不一致！");return false;}}}else{return false;}}
function inputNumAndEnterPress(js){if(event.keyCode<45||event.keyCode>57){if(event.keyCode==88){event.returnValue=true;}else if(event.keyCode==13){js();event.returnValue=true;}else{event.returnValue=false;}}else{event.returnValue=true;}}
function changeFontColor(obj,oldvalue){var newvalue=obj.value;if(newvalue!=null&&newvalue!=""){if(newvalue!=oldvalue){obj.style.color="red";}else{obj.style.color="black";}}}
function changeColorFortextarea(obj){var newvalue=obj.value;var oldvalue=obj.oldvalue;if(newvalue!=null&&newvalue!=""){if(newvalue!=oldvalue){obj.style.color="red";}else{obj.style.color="black";}}}
function verifyURL(theurl){var endvalue,allowstrlist,email;endvalue=true;email=theurl.value;email="http:\/\/"+email.replace("http:\/\/","");if(email.lastIndexOf(":")==-1||email.lastIndexOf(".")==-1||email.lastIndexOf("//")==-1){endvalue=false;}else if(email.indexOf(".",email.indexOf(":"))==-1){endvalue=false;}else if(email.substr(0,1)==":"||email.substr(0,1)=="."||email.substr(email.length-1,1)==":"||email.substr(email.length-1,1)=="."){endvalue=false;}else if(email.substr(0,1)=="_"||email.substr(0,1)=="-"||email.substr(email.length-1,1)=="_"||email.substr(email.length-1,1)=="-"){endvalue=false;}else if(email.substr(0,1)=="/"){endvalue=false;}
email=email.replace("http:\/\/","");allowstrlist="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-.";for(k=0;k<email.length;k++){if(allowstrlist.indexOf(email.substr(k,1))==-1){endvalue=false;break;}}
if(!endvalue){alert("请输入正确的url！");}
return(endvalue);}
function restrictTextAreaLength(obj,leftobj,entedobj){var lenE=obj.value.length;var lenC=0;var CJK=obj.value.match(/[\u4E00-\u9FA5\uF900-\uFA2D]/g);if(CJK!=null)lenC+=CJK.length;var ented=lenC+lenE;if(entedobj){entedobj.innerText=ented;}
var left=obj.maxlength-ented;if(leftobj)
{leftobj.innerText=left;}
if(left<0){var tmp=0
var cut=obj.value.substring(0,obj.maxlength);for(var i=0;i<cut.length;i++){tmp+=/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(cut.charAt(i))?2:1;if(tmp>obj.maxlength)break;}
obj.value=cut.substring(0,i);}}
function fucIntchk(str)
{var strSource='0123456789';var ch;var i;var temp;for(i=0;i<=(str.length-1);i++)
{ch=str.charAt(i);temp=strSource.indexOf(ch);if(temp==-1)
{return 0;}}
if(strSource.indexOf(ch)==-1)
{return 0;}
else
{return 1;}}
function timeinput(element){var str=element.value;if(str.length<6){alert("请输入6位年月，如200401！");element.select();return false;}
if(fucIntchk(str)==0){alert("年月中只应包含数字，请重新输入！");element.select();return false;}
var r=str.match(/^(\d{4})(\/)(\d{1,2})$/);var s="";var isdate=new Date();var year=isdate.getFullYear();var month=isdate.getMonth();s+=year+"-";s+=month+1;if(str.substr(0,4)<2003||str.substr(0,4)>year){alert("年月中的年份不能小于2003年大于当前年份，请重新输入！");element.select();return false;}
if(str.substr(4,2)>12||str.substr(4,2)<1){alert("年月中的月份输入不合法，请重新输入！");element.select();return false;}
if(month+1>9){s=s.replace("-","");}
else{s=s.replace("-","0");}
if(str>s){alert("年月不能大于当前年月，请重新输入！");element.select();return false;}
return true;}
function updatewindow(gridid,src,width,height,str){if(!gridid.Table.CheckSelectedRow()){if(""!=str&&undefined!=str){alert("请选择数据");}else{alert(str);}}
var url=src;if(""!=width&&undefined!=width){width="50";}
if(""!=height&&undefined!=height){height="35";}
style="help:no;status:no;dialogWidth:"+width+";dialogHeight:"+height;reval=window.showModalDialog(url,window,style);if(reval=="success"){gridid.Callback();}else{gridid.Callback();}}
function addwindow(gridid,src,width,height,form){if(""!=width&&undefined!=width){width="50";}
if(""!=height&&undefined!=height){height="35";}
style="help:no;status:no;center:yes;dialogWidth:"+width+";dialogHeight:"+height;reval=window.showModalDialog(src,window,style);if(form==undefined){form=document.forms[0];}
if(reval=="success"){gridid.Callback();}else{gridid.Callback();}}
function popwindow(src,width,height,str){var url=src;if(""!=width&&undefined!=width){width="50";}
if(""!=height&&undefined!=height){height="35";}
style="help:no;status:no;dialogWidth:"+width+";dialogHeight:"+height;window.showModalDialog(url,window,style);}
function toMonth(element){var str=element.value;if(str.length<6){alert("请输入6位年月，如200401！");element.select();return false;}
if(fucIntchk(str)==0){alert("年月中只应包含数字，请重新输入！");element.select();return false;}
if(str.substr(0,4)<1900){alert("年月中的年份不能小于1900年，请重新输入！");element.select();return false;}
if(str.substr(4,2)>12||str.substr(4,2)<1){alert("年月中的月份输入不合法，请重新输入！");element.select();return false;}
return true;}