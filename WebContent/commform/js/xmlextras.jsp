<%@page contentType="text/javascript; charset=GBK"language="java"%>function getDomDocumentPrefix(){if(getDomDocumentPrefix.prefix)
return getDomDocumentPrefix.prefix;var prefixes=["MSXML2","Microsoft","MSXML","MSXML3"];var o;for(var i=0;i<prefixes.length;i++){try{o=new ActiveXObject(prefixes[i]+".DomDocument");return getDomDocumentPrefix.prefix=prefixes[i];}
catch(ex){};}
throw new Error("Could not find an installed XML parser");}
function getXmlHttpPrefix(){if(getXmlHttpPrefix.prefix)
return getXmlHttpPrefix.prefix;var prefixes=["MSXML2","Microsoft","MSXML","MSXML3"];var o;for(var i=0;i<prefixes.length;i++){try{o=new ActiveXObject(prefixes[i]+".XmlHttp");return getXmlHttpPrefix.prefix=prefixes[i];}
catch(ex){};}
throw new Error("Could not find an installed XML parser");}
function XmlHttp(){}
XmlHttp.create=function(){try{if(window.XMLHttpRequest){var req=new XMLHttpRequest();if(req.readyState==null){req.readyState=1;req.addEventListener("load",function(){req.readyState=4;if(typeof req.onreadystatechange=="function")
req.onreadystatechange();},false);}
return req;}
if(window.ActiveXObject){return new ActiveXObject(getXmlHttpPrefix()+".XmlHttp");}}
catch(ex){}
throw new Error("Your browser does not support XmlHttp objects");};function XmlDocument(){}
XmlDocument.create=function(){try{if(document.implementation&&document.implementation.createDocument){var doc=document.implementation.createDocument("","",null);if(doc.readyState==null){doc.readyState=1;doc.addEventListener("load",function(){doc.readyState=4;if(typeof doc.onreadystatechange=="function")
doc.onreadystatechange();},false);}
return doc;}
if(window.ActiveXObject)
return new ActiveXObject(getDomDocumentPrefix()+".DomDocument");}
catch(ex){}
throw new Error("Your browser does not support XmlDocument objects");};if(window.DOMParser&&window.XMLSerializer&&window.Node&&Node.prototype&&Node.prototype.__defineGetter__){XMLDocument.prototype.loadXML=Document.prototype.loadXML=function(s){var doc2=(new DOMParser()).parseFromString(s,"text/xml");while(this.hasChildNodes())
this.removeChild(this.lastChild);for(var i=0;i<doc2.childNodes.length;i++){this.appendChild(this.importNode(doc2.childNodes[i],true));}};XMLDocument.prototype.__defineGetter__("xml",function(){return(new XMLSerializer()).serializeToString(this);});Document.prototype.__defineGetter__("xml",function(){return(new XMLSerializer()).serializeToString(this);});}