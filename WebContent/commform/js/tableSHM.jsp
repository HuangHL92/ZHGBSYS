<%@page contentType="text/javascript; charset=GBK"language="java"%>var count=0;var limit=new Array();var countlimit=1;function expandIt(el){obj=eval("sub"+el);if(obj.style.display=="none")
{obj.style.display="block";if(count<countlimit)
{limit[count]=el;count++;}
else
{eval("sub"+limit[0]).style.display="none";for(i=0;i<limit.length-1;i++)
{limit[i]=limit[i+1];}
limit[limit.length-1]=el;}}
else{obj.style.display="none";var j;for(i=0;i<limit.length;i++)
{if(limit[i]==el)
j=i;}
for(i=j;i<limit.length-1;i++){limit[i]=limit[i+1];}
limit[limit.length-1]=null;count--;}}
function changeEdited(rowId,colId){var fieldObj;var checkboxObj=document.getElementById("subCheckbox"+rowId);for(var i=0;i<colId;i++){fieldObj=document.getElementById(rowId+(i+1));(checkboxObj.checked==true)?fieldObj.disabled=false:fieldObj.disabled=true;}}