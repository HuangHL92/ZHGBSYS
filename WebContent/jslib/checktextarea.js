jQuery.fn.checkTextArea = function(max) {  
	if(arguments.length == 0) max = 100;  
	this.keyup(function() {  
		var area = $(this);          
		if (max > 0) {  
			if (area.val().length > max) {  
				area.val(area.val().substr(0, max)); 
				System.showInfoMsg('已经达到' + max + '个字，不能再录入了！');
			}  
		}  
	});  
	
	this.blur(function() {  
		var area = $(this);  
		if (max > 0) {  
			if (area.val().length > max) {  
				area.val(area.val().substr(0, max)); 
				System.showInfoMsg('已经达到' + max + '个字，不能再录入了！');						
			}  
		}   
	});  
	
	this.on("paste", function() { 
		var area = $(this);  
		setTimeout(function() { 
			if (max > 0) {  
				if (area.val().length > max) {  
					area.val(area.val().substr(0, max)); 
					System.showInfoMsg('已经达到' + max + '个字，不能再录入了！');
				}  
			}  
		}, 
			100
		); 
	}); 
}  