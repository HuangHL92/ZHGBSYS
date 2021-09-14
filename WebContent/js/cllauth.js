/**
 * 设置基础信息设置
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
	 		alert("信息异常，请联系管理员！"); 
		}
  }
  catch(e) {
	  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
	    var curWwwPath_=window.document.location.href;   
	    //获取主机地址之后的目录，如： /myproj/view/my.jsp 
	    var pathName_=window.document.location.pathname;  
	    var pos_=curWwwPath_.indexOf(pathName_);  
	    //获取主机地址，如： http://localhost:8083  
	    var localhostPaht_=curWwwPath_.substring(0,pos_);
	    //获取带"/"的项目名，如：/myproj
	    var projectName_=pathName_.substring(0,pathName_.substr(1).indexOf('/')+1);
	    //得到了 http://localhost:8083/myproj  
	    var realPath_=localhostPaht_+projectName_;
        //alert("请点击页面提示信息允许插件自动安装或者点击确定下载华表插件手动安装（请使用ie兼容模式）!"+"<span>222</span>");  
	    //window.open (realPath_+"/pages/sysorg/CellDownload.jsp")
	    if (confirm("检测不到华表插件，请点击页面提示信息允许华表插件自动安装(跟浏览器有关)或者点击确定下载华表插件手动安装（请使用ie浏览器兼容模式）!")) {  
	    	window.location.href=realPath_+"/softTools/cellregs0714.zip";
        }else {  
        } 
        //alert(e.message);
	}
	
}