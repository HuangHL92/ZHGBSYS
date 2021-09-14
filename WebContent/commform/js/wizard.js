
function GoTo(index,obj)
{if(!(index>=0&&index<obj.NumPages()))
{alert('Invalid index: '+index);return;}
obj.SetPageIndex(index);}
function topButton(obj,wizard){var currentIndex=obj.GetPageIndex();var multipageid=obj.ControlId;if(wizard=="next"){for(i=0;i<=currentIndex;i++){var num=document.getElementById(multipageid+"_"+i);if(num!=null){if(i==currentIndex){num.disabled=null;}else{num.disabled="true";}}}}else if(wizard=="previous"){for(i=currentIndex+1;i>=0;i--){var num=document.getElementById(multipageid+"_"+i);if(num!=null){if(i==currentIndex){num.disabled=null;}else{num.disabled="true";}}}}else if(wizard=="first"){for(i=0;i<=obj.NumPages()-1;i++){var num=document.getElementById(multipageid+"_"+i);if(num!=null){if(i==0){num.disabled=null;}else{num.disabled="true";}}}}else if(wizard=="last"){for(i=currentIndex;i>=0;i--){var num=document.getElementById(multipageid+"_"+i);if(num!=null){if(i==currentIndex){num.disabled=null;}else{num.disabled="true";}}}}}
function PageButton(obj,divid){var div=document.getElementById(divid);var multipageid=obj.ControlId;var num=obj.NumPages()-1;var str="";var input="";var j;if(div!=null){for(var i=0;i<=num;i++){j=i+1;if(i==0){input="<input type=\"button\" value=\"  "+j+"  \" id=\""+multipageid+"_"+i+"\" class='buttonGray'/> &nbsp;&nbsp;";}else{input="<input type=\"button\" value=\"  "+j+"  \" disabled=\"disabled\" id=\""+multipageid+"_"+i+"\" class='buttonGray'/> &nbsp;&nbsp;";}
str=str+input;}
div.innerHTML=str;return true;}}