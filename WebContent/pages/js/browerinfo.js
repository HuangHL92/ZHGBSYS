//ä¯ÀÀÆ÷ĞÅÏ¢
function getBrower(){
	var ua ;
	if(navigator){
		ua = navigator.userAgent;
	}
    var s = "MSIE";
    var i ;
    if(ua){
    	i = ua.indexOf(s)
    }
    if (i >= 0) { 
       return 'IE';
    }
    return "";
}

function myBrowser(){
    var userAgent = navigator.userAgent; //È¡µÃä¯ÀÀÆ÷µÄuserAgent×Ö·û´®
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
    //	alert("Operaä¯ÀÀÆ÷");
        return "Opera"
    }; //ÅĞ¶ÏÊÇ·ñOperaä¯ÀÀÆ÷
    if (userAgent.indexOf("Firefox") > -1) {
    	//alert("Firefoxä¯ÀÀÆ÷");
       return "FF";
    } //ÅĞ¶ÏÊÇ·ñFirefoxä¯ÀÀÆ÷
    if (userAgent.indexOf("Chrome") > -1){
    	//alert("Chrome¹È¸èä¯ÀÀÆ÷");//alert(userAgent);
    	//alert(navigator.appName);
    	//navigator.vendor
    	//alert(navigator.vendor);
	  return "Chrome";
	}
    if (userAgent.indexOf("Safari") > -1) {
    	//alert("Safariä¯ÀÀÆ÷");
        return "Safari";
    } //ÅĞ¶ÏÊÇ·ñSafariä¯ÀÀÆ÷
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
    	//alert("IEä¯ÀÀÆ÷");//alert(userAgent);
        return "IE";
    }; //ÅĞ¶ÏÊÇ·ñIEä¯ÀÀÆ÷
}