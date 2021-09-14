
function XartWebui_TabStrip_SetKeyboardFocusedTabStrip(tabStrip){qqPa=tabStrip;}
function XartWebui_TabStrip_InitKeyboard(tabStrip){XartWebui_TabStrip_SetKeyboardFocusedTabStrip(tabStrip);tabStrip.KeyboardEnabled=true;tabStrip.qzad=tabStrip.Tabs()[0];Event.observe(document,"keydown",XartWebui_HandleKeyPress,false);}
var XartWebui_TabStrip_Keyboard_Loaded=true;