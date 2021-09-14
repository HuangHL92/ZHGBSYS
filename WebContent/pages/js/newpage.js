function opennewpage(src,title){
	 parent.tabs.add({	
	        title: (title),

	        html: '<Iframe width="100%" height="100%" scrolling="auto"  frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
		    closable:true
	 }).show();
	}