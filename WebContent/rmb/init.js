

//alert(ue);
$(function(){
	var ue = parent.UE.getEditor('editor');
	//获得简历
	ue.ready(function () {
		var cce;
		ue.addListener('selectionchange',function(a,b,c){
			clearTimeout(cce);
			cce = setTimeout(function() {
				changeCss();
			}, 300);
		  //  console.log("选区已经变化！");
		});
	        	
	});
}); 

function changeCss(){
	var ue = parent.UE.getEditor('editor');
	//var iwin = document.getElementById("ueditor_0").contentWindow;
	var pnodes = $("#bodyContent p");
	
	
	///^[0-9 ]{4}[\. ．][0-9| ]{2}[\-─-]{1,2}[[0-9 ]{4}[\. ．][0-9 ]{2}[ ]]{0,1}/
	//console.log(iwin.$)
	 //console.log(pnodes.length)
	// console.log(c);
	pnodes.each(function(){
		var text = $(this).text();
		if(text.match(/^([0-9 \u2002\x20\xA0]{4}[\. \u2002\uff0e\x20\xA0][0-9 \u2002\x20\xA0]{2}[\-\u2500\u2014]{1,2}[[0-9 \u2002\x20\xA0]{4}[\. \u2002\uff0e\x20\xA0][0-9 \u2002\x20\xA0]{2}[\u2002\x20\xA0]{2}]{0,1})|([\u2002\x20\xA0]{18})/)){
			$(this).attr('style','text-indent: -9em!important');
		}else{
			$(this).css('text-indent','');
		}
	});
	
	//parent.$("#editor").css('visibility','visible');
	setTimeout(function() {
		ue.fireEvent("contentchange");
	}, 30);
	
}