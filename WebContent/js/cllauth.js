/**
 * ���û�����Ϣ����
 * yinl
 * @param o
 */
function setAuth(o){
	try{
		var uname = decodeURI("%E8%BF%90%E8%BE%93%E7%94%9F%E4%BA%A7%E4%BF%A1%E6%81%AF%E5%B9%B3%E5%8F%B0");
		var rgname = "13040352";
		var rgcode = "1460-1706-0167-6005";
		var i = o.login(uname,"",rgname,rgcode);
	 	if (i==1){
	 		o.HideFormulaErrorInfo(1);
		}else{
	 		alert("��Ϣ�쳣������ϵ����Ա��"); 
		}
  }
  catch(e) {
	  //��ȡ��ǰ��ַ���磺 http://localhost:8083/myproj/view/my.jsp
	    var curWwwPath_=window.document.location.href;   
	    //��ȡ������ַ֮���Ŀ¼���磺 /myproj/view/my.jsp 
	    var pathName_=window.document.location.pathname;  
	    var pos_=curWwwPath_.indexOf(pathName_);  
	    //��ȡ������ַ���磺 http://localhost:8083  
	    var localhostPaht_=curWwwPath_.substring(0,pos_);
	    //��ȡ��"/"����Ŀ�����磺/myproj
	    var projectName_=pathName_.substring(0,pathName_.substr(1).indexOf('/')+1);
	    //�õ��� http://localhost:8083/myproj  
	    var realPath_=localhostPaht_+projectName_;
        //alert("����ҳ����ʾ��Ϣ�������Զ���װ���ߵ��ȷ�����ػ������ֶ���װ����ʹ��ie����ģʽ��!"+"<span>222</span>");  
	    //window.open (realPath_+"/pages/sysorg/CellDownload.jsp")
	    if (confirm("��ⲻ��������������ҳ����ʾ��Ϣ���������Զ���װ(��������й�)���ߵ��ȷ�����ػ������ֶ���װ����ʹ��ie���������ģʽ��!")) {  
	    	window.location.href=realPath_+"/softTools/cellregs0714.zip";
        }else {  
        } 
        //alert(e.message);
	}
	
}