
if(!window.XartWebui_Grid_Callback_Loaded){window.XartWebui_Grid.prototype.LoadTemplates=function(qzAie){for(var qzba=0;qzba<qzAie.length;qzba++){var item=qzAie.item(qzba),sEncodedContent=item.firstChild.nodeValue,sContent=sEncodedContent.replace(/\$\$\$CART_CDATA_CLOSE\$\$\$/g,"]]>"),oTemplatesDiv=document.getElementById(this.Id+"_ServerTemplates"),oNewTemplate=document.createElement("div");oNewTemplate.id=item.nodeName;oNewTemplate.innerHTML=sContent;oTemplatesDiv.appendChild(oNewTemplate);}};window.XartWebui_Grid.prototype.LoadData=function(qzAie){return eval(qzAie[0].nodeValue);};window.XartWebui_Grid.prototype.SetParams=function(qzAie){for(var paramNode=0;paramNode<qzAie.length;paramNode++){var xmlParamNode=qzAie.item(paramNode),qqGz=xmlParamNode.nodeName,qqGy=xmlParamNode.firstChild?xmlParamNode.firstChild.nodeValue:null;this[qqGz]=eval(qqGy);}};window.XartWebui_Grid.prototype.Callback=function(level,parentRow,script){if(this.ClientSideOnBeforeCallback){this.ClientSideOnBeforeCallback();}
if(this.CallbackInProgress){return;}else{this.CallbackInProgress=true;}
if(!script){script=this.GetEventList();}
if(!level){level=0;}
var url=this.CallbackPrefix,postData="";if(parentRow){postData+="&Cart_"+this.Id+"_Callback_Path="+parentRow.qg10;}
postData+="&Cart_"+this.Id+"_Callback_Level="+level;postData+="&Cart_"+this.Id+"_Callback_Script="+encodeURIComponent(script);if(parentRow){}else{if(this.LoadingPanelEnabled){this.Data.length=0;this.Render();}}
this.DoCallback(level,parentRow,url,postData,90210);};window.XartWebui_Grid.prototype.DoCallback=function(level,parentRow,url,postData,pageSize){var grid=this,xmlHttpRequest=false,qzzc=null;function qzAjj(){if(!parentRow&&grid.TemplateCache){grid.TemplateCache=new Object();}
grid.LoadGroups(grid.Groupings);if(grid.AddingRow){grid.Table.Data.push([]);grid.EditingId=grid.Table.GetRow(grid.Table.Data.length-1).ClientId;}
if(!grid.qg9){grid.qg9=document.getElementById(grid.Id+"_EventList");}
if(grid.qg9){var arOneTimeEvents=["INSERT","ADDROW","UPDATE","DELETE","CHECK","UNCHECK"];for(var qzba=0;qzba<arOneTimeEvents.length;qzba++){grid.qg9.value=grid.RemoveEvents(grid.qg9.value,arOneTimeEvents[qzba]);}}
grid.CallbackInProgress=false;if(grid.SelfReferencing&&level<=grid.Levels.length+1){grid.Levels[grid.Levels.length]=grid.Levels[0];}
grid.Render();if(grid.ClientSideOnAfterCallback){setTimeout(grid.Id+".ClientSideOnAfterCallback()",10);}}
function qzzs(){if(qzzc.readyState&&qzzc.readyState!=4&&qzzc.readyState!="complete"){return;}
var responseText=qzzc.responseText;if(grid.Debug){if(responseText){alert("Received content:\n"+responseText);}}
if(xmlHttpRequest){qzzc=qzzc.responseXML;}
if(qzzc&&qzzc.documentElement){if(qzzc.documentElement.childNodes.length==3){var qzAif=qzzc.documentElement.childNodes[0].childNodes,arTemplates=qzzc.documentElement.childNodes[1].childNodes,arData=qzzc.documentElement.childNodes[2].childNodes;grid.SetParams(qzAif);if(arTemplates.length>0){grid.LoadTemplates(arTemplates);}
if(parentRow){parentRow.Data[grid.Levels[parentRow.Level].Columns.length]=grid.LoadData(arData);}else{grid.Data=grid.LoadData(arData);grid.Table=grid.LoadTable(grid.Data,0,[]);}
qzAjj();}else{qzAjj();var sError=qzzc.documentElement.childNodes[0].firstChild.nodeValue;if(grid.ClientSideOnCallbackError){grid.ClientSideOnCallbackError(sError);}else{alert("Callback error:\n"+sError);}}}else{alert("The data could not be loaded.");qzAjj();}}
if(this.Debug){alert("Sending callback data:\n"+unescape(postData).replace(/\&/g,"\n").replace(/\;/g,"\n"));}
if(window.XMLHttpRequest){xmlHttpRequest=true;var qzzc=new XMLHttpRequest();qzzc.onreadystatechange=qzzs;qzzc.open("POST",url,true);qzzc.setRequestHeader("Content-Type","application/x-www-form-urlencoded");qzzc.send(postData);}else{if(document.implementation&&document.implementation.createDocument){qzzc=document.implementation.createDocument("","",null);qzzc.onload=qzzs;}else{if(cart_browser_ie){if(window.ActiveXObject){try{qzzc=new ActiveXObject("Microsoft.XMLHTTP");qzzc.onreadystatechange=qzzs;qzzc.open("POST",url,true);qzzc.setRequestHeader("Content-Type","application/x-www-form-urlencoded");qzzc.send(postData);xmlHttpRequest=true;}
catch(ex){}}
if(qzzc==null){var qzac=this.Id+"_island",qzv=document.getElementById(qzac);if(!qzv){qzv=document.createElement("xml");qzv.id=qzac;document.body.appendChild(qzv);}
if(qzv.XMLDocument){qzzc=qzv.XMLDocument;qzzc.onreadystatechange=qzzs;}else{return false;}}}else{return false;}}}
if(!xmlHttpRequest){qzzc.async=true;try{qzzc.load(url+postData);}
catch(ex){qzAjj();alert("Data not loaded: "+(ex.message?ex.message:ex));}}
return true;};window.XartWebui_Grid_Callback_Loaded=true;}