<%@page contentType="text/javascript; charset=GBK"language="java"%>var ie=document.all!=null;var moz=!ie&&document.getElementById!=null&&document.layers==null;function bs_isNull(theVar){if(typeof(theVar)=='undefined')return true;if(theVar==null)return true;return false;}
function bs_isObject(theVar){ret=false;if(typeof(theVar)=='object'){ret=!bs_isNull(theVar);}
return ret;}
function bs_isEmpty(theVar){if(bs_isNull(theVar))return true;if(theVar=='')return true;return false;}