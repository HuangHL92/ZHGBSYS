$(document).ready(function(){
	var bg = new Array(9); 
	bg[0] = '../style/default/images/page/meeting/swap_bg1.jpg'  
	bg[1] = '../style/default/images/page/meeting/swap_bg3.jpg'
	bg[2] = '../style/default/images/page/meeting/swap_bg5.jpg'
	bg[3] = '../style/default/images/page/meeting/swap_bg7.jpg' 
	bg[4] = '../style/default/images/page/meeting/swap_bg8.jpg'
	bg[5] = '../style/default/images/page/meeting/swap_bg9.jpg'
	bg[6] = '../style/default/images/page/meeting/bg1024_07.jpg'
	bg[7] = '../style/default/images/page/meeting/bg1024_02.jpg'
	bg[8] = '../style/default/images/page/meeting/swap_bg4.jpg'
	var index = Math.floor(Math.random() * bg.length);
	
	$("#background").attr('src', bg[index]);
	
	// 按ESC键退出
	document.onkeydown = function() {
		if (event.keyCode == 27) {
			event.returnValue = null;
			window.returnValue = null;
			window.close();
		}
	};	
});

