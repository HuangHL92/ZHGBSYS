//�������Ϣ
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
    var userAgent = navigator.userAgent; //ȡ���������userAgent�ַ���
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
    //	alert("Opera�����");
        return "Opera"
    }; //�ж��Ƿ�Opera�����
    if (userAgent.indexOf("Firefox") > -1) {
    	//alert("Firefox�����");
       return "FF";
    } //�ж��Ƿ�Firefox�����
    if (userAgent.indexOf("Chrome") > -1){
    	//alert("Chrome�ȸ������");//alert(userAgent);
    	//alert(navigator.appName);
    	//navigator.vendor
    	//alert(navigator.vendor);
	  return "Chrome";
	}
    if (userAgent.indexOf("Safari") > -1) {
    	//alert("Safari�����");
        return "Safari";
    } //�ж��Ƿ�Safari�����
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
    	//alert("IE�����");//alert(userAgent);
        return "IE";
    }; //�ж��Ƿ�IE�����
}