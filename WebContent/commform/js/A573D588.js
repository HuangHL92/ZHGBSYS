
function qzkn()
{var qzm=qqPa.qzad;if(qzm.ParentStorageIndex>=0)
{qzm=qqPa.qzo(qzm.ParentStorageIndex);};qqPa.qzad=qzm;};function qzhm()
{var navBar=qqPa,qzm=navBar.qzad;qqPa.qzad=navBar.qzo(qzm.ChildIndexes[qzm.ChildIndexes.length-1]);while(qqPa.qzad.GetProperty('Expanded')&&qqPa.qzad.ChildIndexes.length>0)
{qzhm();};};function qzqy()
{var qzm=qqPa.qzad,qzaq=null,siblingIndexes;if(qzm.ParentStorageIndex>=0)
{siblingIndexes=qqPa.qzo(qzm.ParentStorageIndex).ChildIndexes;}else
{siblingIndexes=qqPa.GetRootItemIndexes();};for(var qzba=0;qzba<siblingIndexes.length;qzba++)
{if(siblingIndexes[qzba]==qzm.StorageIndex)
{if(qzba>0)
{qzaq=qqPa.qzo(siblingIndexes[qzba-1]);qqPa.qzad=qzaq;};break;};};if(qzaq)
{while(qzaq.GetProperty('Expanded')&&qzaq.ChildIndexes.length>0)
{qzhm();qzaq=qqPa.qzad;};}else if(qzm.ParentStorageIndex>=0)
{qzkn();};};function qzls(qzya,qzAbh)
{var qzm=qqPa.qzad;if(!qzya&&qzm.ChildIndexes.length>0&&qzm.GetProperty('Expanded'))
{qqPa.qzad=qqPa.qzo(qzm.ChildIndexes[0]);return;}else
{var siblingIndexes;if(qzm.ParentStorageIndex>=0)
{siblingIndexes=qqPa.qzo(qzm.ParentStorageIndex).ChildIndexes;}else
{siblingIndexes=qqPa.GetRootItemIndexes();};for(var qzba=0;qzba<siblingIndexes.length;qzba++)
{if(siblingIndexes[qzba]==qzm.StorageIndex)
{if(qzba<siblingIndexes.length-1)
{qqPa.qzad=qqPa.qzo(siblingIndexes[qzba+1]);return;};};};if(!qzAbh&&qzm.ParentStorageIndex>=0)
{for(var qzbc=qzm;qzbc!=null;qzbc=qqPa.qzo(qzbc.ParentStorageIndex))
{if(!qzbc.qzvn())
{qzkn();qzls(true);};};};};};function qzsb()
{qqPa.qzad=qqPa.Items()[0];};function XartWebui_NavBar_KeyMoveHome()
{var qzar=qqPa.qzad;qzsb();qzed(qzar);};function XartWebui_NavBar_KeyMoveEnd()
{var qzar=qqPa.qzad,qzAgg=qqPa.GetLastRootIndex();qqPa.qzad=qqPa.qzo(qzAgg);if(qqPa.qzad.GetProperty('Expanded')&&qqPa.qzad.ChildIndexes.length>0)
{qzhm();};qzed(qzar);};function XartWebui_NavBar_KeyMoveUp_Temp(){var qzar=qqPa.qzad;var currentitem=document.getElementById(qqPa.NavBarID+'_item_'+qzar.StorageIndex);if(currentitem.onmouseout)
{currentitem.onmouseout();};XartWebui_NavBar_LastItemInLevel();var qzaq=qqPa.qzad;if(qzaq)
{var qzwi=document.getElementById(qqPa.NavBarID+'_item_'+qzaq.StorageIndex);if(qzwi.onmouseover)
{qzwi.onmouseover();};qqPa.qzkj=1;};}
function XartWebui_NavBar_keyMoveDown_Temp(){var qzar=qqPa.qzad;var currentitem=document.getElementById(qqPa.NavBarID+'_item_'+qzar.StorageIndex);if(currentitem.onmouseout)
{currentitem.onmouseout();};if(qzar.ChildIndexes.length>0&&qzar.GetProperty('Expanded'))
{qqPa.qzad=qqPa.qzo(qzar.ChildIndexes[0]);}else if(!qzar.GetProperty('Expanded')){XartWebui_NavBar_NextItemInLevel();}
var qzaq=qqPa.qzad;if(qzaq)
{var qzwi=document.getElementById(qqPa.NavBarID+'_item_'+qzaq.StorageIndex);if(qzwi.onmouseover)
{qzwi.onmouseover();};qqPa.qzkj=1;};}
function XartWebui_NavBar_LastItemInLevel(){var qzar=qqPa.qzad;var parentitem=qzar.ParentItem;if(!parentitem){var rootindexs=qqPa.GetRootItemIndexes();for(var rootindex=0;rootindex<rootindexs.length;rootindex++){if(rootindexs[rootindex]==qzar.StorageIndex){if(rootindex!=0){qqPa.qzad=qqPa.qzo(rootindexs[rootindex-1]);XartWebui_NavBar_LastItem();return;}
else{qqPa.qzad=qqPa.qzo(rootindexs[rootindexs.length-1]);XartWebui_NavBar_LastItem();return;}}}}
var childindexes=parentitem.ChildIndexes;for(var index=0;index<childindexes.length;index++){if(childindexes[index]==qzar.StorageIndex){if(index!=0){qqPa.qzad=qqPa.qzo(childindexes[index-1]);XartWebui_NavBar_LastItem();return;}
else{qqPa.qzad=parentitem;return;}}}}
function XartWebui_NavBar_LastItem(){var qzar=qqPa.qzad;if(!qzar.GetProperty('Expanded')){qqPa.qzad=qzar;return;}else{qqPa.qzad=qzar.ChildItemArray[qzar.ChildItemArray.length-1];XartWebui_NavBar_LastItem();}}
function XartWebui_NavBar_NextItemInLevel(){var qzar=qqPa.qzad;var parentitem=qzar.ParentItem;if(!parentitem){var rootindexs=qqPa.GetRootItemIndexes();for(var rootindex=0;rootindex<rootindexs.length;rootindex++){if(rootindexs[rootindex]==qzar.StorageIndex){if(rootindex!=rootindexs.length-1){qqPa.qzad=qqPa.qzo(rootindexs[rootindex+1]);return;}
else{qqPa.qzad=qqPa.qzo(rootindexs[0]);return;}}}}
var childindexes=parentitem.ChildIndexes;for(var index=0;index<childindexes.length;index++){if(childindexes[index]==qzar.StorageIndex){if(index!=childindexes.length-1){qqPa.qzad=qqPa.qzo(childindexes[index+1]);return;}
else{qqPa.qzad=parentitem;XartWebui_NavBar_NextItemInLevel();}}}}
function XartWebui_NavBar_KeyMoveDown()
{var qzar=qqPa.qzad;qzls();qzed(qzar);};function XartWebui_NavBar_KeyMoveUp()
{var qzar=qqPa.qzad;qzqy();qzed(qzar);};function qzed(qzar)
{if(qzar)
{var qzwj=document.getElementById(qqPa.NavBarID+'_item_'+qzar.StorageIndex);if(qzwj.onmouseout)
{qzwj.onmouseout();};};var qzaq=qqPa.qzad;if(qzaq)
{var qzwi=document.getElementById(qqPa.NavBarID+'_item_'+qzaq.StorageIndex);if(qzwi.onmouseover)
{qzwi.onmouseover();};};qqPa.qzkj=1;};function XartWebui_NavBar_KeyboardSetToItem(navBar,qzm)
{navBar.qzad=qzm;qqPa=navBar;};function XartWebui_NavBar_SetKeyboardFocusedNavBar(qzyz)
{if(qqPa&&qqPa==qzyz)return;if(qqPa)
{var qzrr=document.getElementById(qqPa.NavBarID+"_div");if(qzrr)qzrr.className=qqPa.CssClass;};qqPa=qzyz;if(qzyz.FocusedCssClass!='')
{var qzAip=document.getElementById(qqPa.NavBarID+"_div");qzAip.className=qzyz.FocusedCssClass;};};function XartWebui_NavBar_KeySelectItem()
{var navBar=qqPa,qzm=navBar.qzad,qzcd=document.getElementById(navBar.NavBarID+'_item_'+qzm.StorageIndex);qzhx(navBar,qzm,qzcd,false);};function XartWebui_NavBar_InitKeyboard(navBar)
{XartWebui_NavBar_SetKeyboardFocusedNavBar(navBar);navBar.KeyboardEnabled=true;navBar.qzad=navBar.Items()[0];XartWebui_RegisterKeyHandler(navBar,'Enter','XartWebui_NavBar_KeySelectItem()');XartWebui_RegisterKeyHandler(navBar,'(','XartWebui_NavBar_keyMoveDown_Temp()');XartWebui_RegisterKeyHandler(navBar,'&','XartWebui_NavBar_KeyMoveUp_Temp()');XartWebui_RegisterKeyHandler(navBar,'$','XartWebui_NavBar_KeyMoveHome()');XartWebui_RegisterKeyHandler(navBar,'#','XartWebui_NavBar_KeyMoveEnd()');Event.observe(document,'keydown',XartWebui_HandleKeyPress,false);};var XartWebui_NavBar_Keyboard_Loaded=true;