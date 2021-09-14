//计算在页面的位置
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}

//    
/**
 * @author zhaoyd
 * 计算在页面的位置 加强版 
 * left 左边距离  top 右边距离 width 宽度 height 高度
 * @param obj 
 * @returns
 */
	function GetObjWHLT(obj) {
		var objWHLT = {
			left : 0,
			top : 0,
			width : 0,
			heigth : 0
		};
		var nLt = 0, nTp = 0, offsetParent = obj;
		while (offsetParent != null && offsetParent != document.body) {
			nLt += offsetParent.offsetLeft;
			nTp += offsetParent.offsetTop;
			if ((navigator.userAgent.indexOf('MSIE') >= 0) 
				    && (navigator.userAgent.indexOf('Opera') < 0)) {
				parseInt(offsetParent.currentStyle.borderLeftWidth) > 0 ? nLt += parseInt(offsetParent.currentStyle.borderLeftWidth)
						: "";
				parseInt(offsetParent.currentStyle.borderTopWidth) > 0 ? nTp += parseInt(offsetParent.currentStyle.borderTopWidth)
						: "";
			}
			offsetParent = offsetParent.offsetParent;
		}
		objWHLT.left = nLt;
		objWHLT.top = nTp;
		objWHLT.width = obj.offsetWidth;
		objWHLT.heigth = obj.offsetHeight;
		return objWHLT;
	}