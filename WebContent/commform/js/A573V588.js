
var qzic=false,qzmg,qzlx,qzbr,qzkm=new Array(),qzhe=new Array(),qzpu=new Array(),qzno=new Array();function art_InitDockable(qzro,qzAcf,qzAce,qzAdl,qzAka,qzAhd,qzAfh,qzAdk){var qzg=art_GetInstance(qzro);qzg.qzAbc=true;qzg.qznp=qzAka;qzg.qzpf=qzg.Frame.offsetWidth;qzg.qznq=qzg.Frame.offsetHeight;qzg.qzAcg=qzAhd;qzg.qzAaa=qzAfh;qzg.UndockedCssClass=qzAdk;var qzAhe=qzAcf.split(",");qzg.qzdv=qzAhe;if(qzAce!=""){qxa(qzg.Id,qzAce,qzAdl,true);qzg.Frame.style.visibility="visible";}
if(qzgq<qzg.Frame.style.zIndex){qzgq=qzg.Frame.style.zIndex;}}
function qzAfg(qzg,qzAmc,qzAmb){var frameObj=qzg.Frame,qzbs=frameObj.parentNode;frameObj.style.display="none";frameObj.style.position="absolute";frameObj.style.left=qzAmc;frameObj.style.top=qzAmb;qzbs.removeChild(frameObj);frameObj.style.width=qzbs.offsetWidth;document.body.appendChild(frameObj);qzbs.NumSnaps--;frameObj.style.display="block";qzg.qzms=false;qzg.qzup=null;qzg.qzvq=qzg.qzcf;qzg.qzcf=-1;qzyv(qzbs);if(qzg.UndockedCssClass!=""){frameObj.className=qzg.UndockedCssClass;}}
function qxa(qzaz,qzlw,qzcf,qzma){var qzg=art_GetInstance(qzaz),frameObj=qzg.Frame;frameObj.style.display="none";var qzbs=document.getElementById(qzlw);if(!qzbs){alert("Docking container '"+qzlw+"' not found!");}
if(!qzbs.NumSnaps){qzbs.NumSnaps=0;}
if(qzma){if(qzcf>qzbs.NumSnaps){setTimeout(qzaz+".Dock('"+qzlw+"',"+qzcf+","+qzma+")",10);return;}else{if(qzcf<qzbs.NumSnaps){qzcf=qzbs.NumSnaps;}}}
frameObj.parentNode.removeChild(frameObj);frameObj.style.position="relative";frameObj.style.width="100%";frameObj.style.height="";frameObj.style.top="";frameObj.style.left="0px";frameObj.style.zIndex=qzbs.style.zIndex;var qzkl=-1;if(qzcf>=qzbs.NumSnaps||qzcf<0){qzkl=qzbs.NumSnaps;}else{qzkl=qzcf;}
if(qzkl>=qzbs.childNodes.length){qzbs.appendChild(frameObj);}else{qzbs.insertBefore(frameObj,qzbs.childNodes[qzkl]);}
qzcf=qzbs.NumSnaps;qzbs.NumSnaps++;if(!qzg.IsMinimized){frameObj.style.display="";}
qzyt(qzg);qzg.qzms=true;qzg.qzup=qzlw;qzg.qzpv=qzlw;qzg.qzcf=qzcf;qzg.qzvq=qzcf;qzyv(qzbs);if(qzg.UndockedCssClass!=""){frameObj.className=qzg.qzAaa;}
if(!qzma&&qzg.ClientSideOnDock){qzg.ClientSideOnDock(qzg,qzlw,qzkl);}
if(!qzma&&qzg.AutoPostBackOnDock){__doPostBack(qzg.ControlId,"DOCK "+qzlw+" "+qzcf);return;}
if(!qzma&&qzg.AutoCallBackOnDock){qzg.Callback();}}
function qzyv(qzbs){qzzx(qzbs);var qzyw=qzkb(qzbs);if(qzyw){var qzmj=qzhs(qzyw);if(qzmj.qzlb){art_RepositionFloater(qzmj);}}}
function qzzx(qzbs){for(var qzba=0;qzba<qzbs.NumSnaps;qzba++){var frameObj=qzbs.childNodes[qzba],qzg=qzhs(frameObj);if(qzg){qzg.qzcf=qzba;qzjg(qzg);}}}
function qzAej(qzdv){qzbr=qzdv;for(var qzba=0;qzba<qzbr.length;qzba++){var qzAgr=document.getElementById(qzbr[qzba]);if(qzAgr){qzAdj(qzAgr,qzba);}else{alert("DockingContainer "+(qzba+1)+" ("+qzbr[qzba]+") not found!");}}}
function qzAdj(qzbc,index){qzkm[index]=qzhf(qzbc);qzhe[index]=qzmh(qzbc);qzno[index]=qzbc.offsetWidth;qzpu[index]=qzbc.offsetHeight;}
function qzAhc(qzet,qznp){switch(qznp){case"DashedOutline":qzet.style.bgcolor="transparent";qzet.style.borderWidth=1;qzet.style.borderColor="#ffffff";qzet.style.borderStyle="dashed";var qzeu=document.createElement("div");qzeu.style.borderWidth=1;qzeu.style.borderColor="#000000";qzeu.style.borderStyle="dashed";qzeu.style.width="100%";qzeu.style.height="100%";qzet.appendChild(qzeu);break;case"None":break;case"Shadow":qzet.style.zIndex=qzgq-1;qzAab(qzet,"#202020");break;case"TransparentRectangle":qzet.style.zIndex=qzgq-1;qzAch(qzet,"#000000","#505050");break;default:qzet.style.borderWidth="2px";qzet.style.borderStyle="solid";break;}}
function qziv(qzvb,index){var qzbs=document.getElementById(qzvb);for(var qzba=index-1;qzba>=0;qzba--){if(qzuq(qzbs.childNodes[qzba])&&qzbs.childNodes[qzba].style.position=="relative"){return qzba;}}
return-1;}
function qzAbr(qzAjk,qzvb,x,y){var qzzy=false,qzbs=document.getElementById(qzvb),qzAlh=qzhf(qzAjk),qzuf=qzAjk.offsetHeight/2,qzAdt=qzAlh+qzuf,qzAdb=null,qzkf=null,qzxq=0,qzAkq=-1;for(var qzba=0;qzba<qzbs.childNodes.length;qzba++){if(qzuq(qzbs.childNodes[qzba])&&qzbs.childNodes[qzba].style.position=="relative"){qzAdb=qzkf;qzkf=qzbs.childNodes[qzba];qzzy=true;qzxq=qzhf(qzkf);if((qzAdb&&qzAdb.offsetHeight>qzuf&&qzAdt>qzxq)||y>=qzxq||!qzAdb){qzAkq=qzba;}}}
if(qzzy&&((qzkf.offsetHeight>qzuf&&qzxq+qzkf.offsetHeight<qzAdt)||y>=qzxq+qzkf.offsetHeight)){return qzbs.childNodes.length;}else{return qzAkq;}}
var XartWebui_Snap_Dock_Loaded=true;