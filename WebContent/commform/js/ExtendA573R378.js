
if(!window.XartWebui_Grid_Callback_Loaded){window.XartWebui_Grid.prototype.LoadTemplates=function(qzAie){for(var qzba=0;qzba<qzAie.length;qzba++){var item=qzAie.item(qzba),sEncodedContent=item.firstChild.nodeValue,sContent=sEncodedContent.replace(/\$\$\$CART_CDATA_CLOSE\$\$\$/g,"]]>"),oTemplatesDiv=document.getElementById(this.Id+"_ServerTemplates"),oNewTemplate=document.createElement("div");oNewTemplate.id=item.nodeName;oNewTemplate.innerHTML=sContent;oTemplatesDiv.appendChild(oNewTemplate);}};window.XartWebui_Grid.prototype.LoadData=function(qzAie){return eval(qzAie[0].nodeValue);};window.XartWebui_Grid.prototype.SetParams=function(qzAie){for(var paramNode=0;paramNode<qzAie.length;paramNode++){var xmlParamNode=qzAie.item(paramNode),qqGz=xmlParamNode.nodeName,qqGy=xmlParamNode.firstChild?xmlParamNode.firstChild.nodeValue:null;this[qqGz]=eval(qqGy);}};window.XartWebui_Grid.prototype.AjaxCallback=function(pars){this.Callback(0,null,null,null,true,pars);};window.XartWebui_Grid.prototype.Callback=function(level,parentRow,script,actionURI,isAjax,pars){if(this.ClientSideOnBeforeCallback){this.ClientSideOnBeforeCallback();}
if(this.CallbackInProgress){return;}else{this.CallbackInProgress=true;}
if(!script){script=this.GetEventList();}
if(!level){level=0;}
var url=this.CallbackPrefix,postData="";if(parentRow){postData+="&Cart_"+this.Id+"_Callback_Path="+parentRow.qg10;}
postData+="&Cart_"+this.Id+"_Callback_Level="+level;postData+="&Cart_"+this.Id+"_Callback_Script="+encodeURIComponent(base64encode(utf16to8(script)));if(parentRow){}else{if(this.LoadingPanelEnabled){this.Data.length=0;this.Render();}}
postData+="&"+pars;this.DoCallback(level,parentRow,url,postData,90210,null,isAjax);};window.XartWebui_Grid.prototype.DoCallback=function(level,parentRow,url,postData,pageSize,batchParam,isAjax){var grid=this,xmlHttpRequest=false,qzzc=null;var xxquery=this.Levels[level].PageQueryData;postData+="&pageQueryData="+xxquery;if(isAjax){}else{postData+="&"+xxquery+"="+encodeURIComponent(document.getElementById(xxquery).value);if(batchParam!=null){var bp=batchParam.go2Page;if("current"==bp){postData+="&toPage="+(grid.CurrentPageIndex+1);}else{if("next"==bp){if((grid.CurrentPageIndex+2)>=grid.PageCount){postData+="&toPage="+(grid.PageCount);}else{postData+="&toPage="+(grid.CurrentPageIndex+2);}}else{if(bp>grid.RecordCount){bp=grid.RecordCount;}
postData+="&toPage="+bp;}}}else{postData+="&toPage="+(grid.CurrentPageIndex+1);}}
postData+="&isCallBack=true";postData+="&pageSize="+grid.PageSize;postData+="&columnsFromCallBack=";for(var qzba=0;qzba<this.Table.Columns.length;qzba++){postData+=this.Table.Columns[qzba].DataField;if(qzba<this.Table.Columns.length-1){postData+=",";}}
postData=postData+"&columnsTextFromCallBack=";for(var qzba=0;qzba<this.Table.Columns.length;qzba++){if(this.Table.Columns[qzba].HeadingText==""){postData+=this.Table.Columns[qzba].DataField;}else{postData+=this.Table.Columns[qzba].HeadingText;}
if(qzba<this.Table.Columns.length-1){postData+=",";}}
postData+="&level="+level;postData+="&dataMember="+this.Levels[level].DataMember;function qzAjj(){if(!parentRow&&grid.TemplateCache){grid.TemplateCache=new Object();}
grid.LoadGroups(grid.Groupings);if(grid.AddingRow){grid.Table.Data.push([]);grid.EditingId=grid.Table.GetRow(grid.Table.Data.length-1).ClientId;}
if(!grid.qg9){grid.qg9=document.getElementById(grid.Id+"_EventList");}
if(grid.qg9){var arOneTimeEvents=["INSERT","ADDROW","UPDATE","DELETE","CHECK","UNCHECK"];for(var qzba=0;qzba<arOneTimeEvents.length;qzba++){grid.qg9.value=grid.RemoveEvents(grid.qg9.value,arOneTimeEvents[qzba]);}}
grid.CallbackInProgress=false;if(grid.SelfReferencing&&level<=grid.Levels.length+1){grid.Levels[grid.Levels.length]=grid.Levels[0];}
grid.Render();if(grid.ClientSideOnAfterCallback){setTimeout(grid.Id+".ClientSideOnAfterCallback()",10);}}
function qzzs(){if(qzzc.readyState&&qzzc.readyState!=4&&qzzc.readyState!="complete"){return;}
var responseText=qzzc.responseText;if(false){if(responseText){alert("Received content:\n"+responseText);}}
if(xmlHttpRequest){qzzc=qzzc.responseXML;}
if(qzzc&&qzzc.documentElement){if(qzzc.documentElement.childNodes.length==6){var qzAif=qzzc.documentElement.childNodes[0].childNodes,arTemplates=qzzc.documentElement.childNodes[1].childNodes,arData=qzzc.documentElement.childNodes[2].childNodes,response_XMLData=qzzc.documentElement.childNodes[3].firstChild.nodeValue,system_XMLData=qzzc.documentElement.childNodes[4].firstChild.nodeValue,queryInfo_Data=qzzc.documentElement.childNodes[5].firstChild.nodeValue;if(response_XMLData){grid.Response_Data=eval("("+response_XMLData+")");}
grid.SetParams(qzAif);if(arTemplates.length>0){grid.LoadTemplates(arTemplates);}
if(parentRow){parentRow.Data[grid.Levels[parentRow.Level].Columns.length]=grid.LoadData(arData);}else{grid.Data=grid.LoadData(arData);grid.Table=grid.LoadTable(grid.Data,0,[]);}
qzAjj();if(system_XMLData){alert(system_XMLData);}
if(isAjax){document.all(xxquery).value=queryInfo_Data;}}else{qzAjj();var sError=qzzc.documentElement.childNodes[0].firstChild.nodeValue;if(grid.ClientSideOnCallbackError){grid.ClientSideOnCallbackError(sError);}else{alert("Callback error:\n"+sError);}}}else{qzAjj();}}
if(this.Debug){alert("Sending callback data:\n"+unescape(postData).replace(/\&/g,"\n").replace(/\;/g,"\n"));}
if(window.XMLHttpRequest){xmlHttpRequest=true;var qzzc=new XMLHttpRequest();qzzc.onreadystatechange=qzzs;qzzc.open("POST",url,true);qzzc.setRequestHeader("Content-Type","application/x-www-form-urlencoded");qzzc.send(postData);}else{if(document.implementation&&document.implementation.createDocument){qzzc=document.implementation.createDocument("","",null);qzzc.onload=qzzs;}else{if(cart_browser_ie){if(window.ActiveXObject){try{qzzc=new ActiveXObject("Microsoft.XMLHTTP");qzzc.onreadystatechange=qzzs;qzzc.open("POST",url,true);qzzc.setRequestHeader("Content-Type","application/x-www-form-urlencoded");qzzc.send(postData);xmlHttpRequest=true;}
catch(ex){}}
if(qzzc==null){var qzac=this.Id+"_island",qzv=document.getElementById(qzac);if(!qzv){qzv=document.createElement("xml");qzv.id=qzac;document.body.appendChild(qzv);}
if(qzv.XMLDocument){qzzc=qzv.XMLDocument;qzzc.onreadystatechange=qzzs;}else{return false;}}}else{return false;}}}
if(!xmlHttpRequest){qzzc.async=true;try{qzzc.load(url+postData);}
catch(ex){qzAjj();alert("Data not loaded: "+(ex.message?ex.message:ex));}}
return true;};window.XartWebui_Grid_Callback_Loaded=true;}