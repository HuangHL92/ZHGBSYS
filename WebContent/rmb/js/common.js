var keys = {ESC:27,TAB:9,RETURN:13,LEFT:37,UP:38,RIGHT:39,DOWN:40,ENTER:13,SHIFT:16};


var oText,oMenu,nowClickId,canValide;
$(document).mousedown(function(ev){
	canValide = false;
	var oEvent=ev||event;
	var target=oEvent.target||oEvent.srcElement;
	if(oMenu) {
		if(target !==oMenu &&target!==oText){
			if ($(target).attr("id") && $(target).attr("id").indexOf(nowClickId) > -1) {

			} else {
				oMenu.style.display = 'none';
				canValide = true;
			}
		}
	}
});


function initSelectData(id, nationList, Keylist, selectTDStyle) {
	var $nationTbl = $("#" + id + "table");
	var index = 1;
	for (var i = 0; i < Keylist.length; i++) {
		var key = Keylist[i];
		var value = nationList[key];
		var $tr = $("<tr index=" + index + " key-value='" + key + "' class='option-item' id='" + id + "" + key + "option'>");
		$tr.appendTo($nationTbl);
		$("<td style='padding-left:2px!important;border-top:0px!important;text-align:left;" + selectTDStyle + "'  id='" + id + index + "' onclick=chooseComoboVal('" + id + "Text','" + id + "','" + id + "Menu','" + id + "Arrow','" + key + "','" + value + "')>").html(value).appendTo($tr);
		index++;
	}


	$("#" + id + "Text").keydown(function (event) {
		keydown(event,id);
	});

	$("#"+id+"Text").keyup(function(event){
		keyup(event,id);
	});
	
	$("#"+id+"Menu table tbody tr").mouseover(function(){
    	$("#"+id+"Menu table").find("tr").removeClass('backcolor');
        $(this).addClass("backcolor");
    });
    
    $("#"+id+"Menu table tbody tr").mouseout(function(){
        $(this).removeClass("backcolor");
    });
}


function keyupdown(id){
	$("#"+id+"Menu table").find("td").removeClass('backcolor');
	$("#"+id+"Menu table").find("tr").removeClass('backcolor');
	$("#"+id+"Menu table tbody tr").hide().filter(":contains('"+$("#"+id+"Text").val()+"')").show();
	$("#"+id+"Menu table tbody tr").each(function () {
		if($(this).css("display") !== 'none') {
			$(this).addClass('backcolor');
			return false;
		}
	});
	var i = 0;
	$("#"+id+" table tbody tr").each(function () {
		if($(this).css("display") !== 'none') {
			i++;
		}
	});
	if(i > 7) {
		$("#"+id+"Menu").css('height','200px');
	} else {
		if(i == 1){
			$("#"+id+"Menu").css('height','40px');
		} else {
			var totalHeight = i*30;
			$("#"+id+"Menu").css('height',totalHeight+'px');
		}
	}
}
function valideNation(id,nationListReverse,label,nationLen) {
	if(canValide){
		var textVal = $("#"+id+"Text").val();
		if(textVal == null || textVal.trim() === ''){
			$("#"+id).val("");
			return;
		}
		var key = nationListReverse[textVal];
		if(key) {
			$("#"+id).val(key);
		} else {
			$h.alert('系统提示', '您输入的【' + (label == null ? "" : label) + '】信息选择范围之外，请重新先择！', function () {
				moveEnd(document.getElementById(id + "Text"));
				showNationComobo(id + "Menu", id, id, id + "Arrow", id, nationLen);
			});
		}
	}
}


function showNationComobo(menuList,hideKey,menuName,arrowId,id,nationLen){
	nowClickId = id;
	oText=document.getElementById(id+"Text");
	oMenu=document.getElementById(id+"Menu");
	//alert(nationLen);
	var totalHeight;
	if(nationLen < 7) {
		totalHeight = nationLen*30;
		$("#"+id+"Menu").css('height',totalHeight+'px');
	} else {
		totalHeight = 200;
		$("#"+id+"Menu").css('height','200px');
	}
	var $obj = $("#"+id+"Text");
	//alert($obj.offset().top+","+totalHeight+","+$(window).height());
	if($obj.offset().top+totalHeight+$obj.height()>$(window).height()){
		$("#"+id+"Menu").css('top',$obj.position().top-totalHeight);
	}else{
		$("#"+id+"Menu").css('top',$obj.position().top+$obj.height());
	}
	
	//$("#"+id+"Menu").css('left',$obj.position().left+parseInt($obj.css('margin-left'))-1);
	
	if(($obj.offset().left+$("#"+id+"Menu").width())>$(window).width()){
		$("#"+id+"Menu").css('left',$obj.position().left+parseInt($obj.css('margin-left'))-1-$("#"+id+"Menu").width()+$obj.width());
	}else{
		$("#"+id+"Menu").css('left',$obj.position().left+parseInt($obj.css('margin-left'))-1);
	}
	
	
	
	
	$("#"+id+"Menu table tbody tr").show();
	$("#"+arrowId).css('display','block');
	$("#"+menuList).show();//slideDown(200);
	$("#"+arrowId).addClass("searchArrowRote");
	var value = $("#"+hideKey).val();
	$("#"+id+value+'option').addClass('backcolor');
	fixScroll(id);
}


function chooseComoboVal(showText,hideKey,menuList,arrowId,value,name){
	clickType = true;
	$("#"+showText).val(name);
    $("#"+hideKey).attr("value",value);
    $("#"+menuList).css('display','none');
	$("#"+arrowId).removeClass("searchArrowRote");
	$("#"+arrowId).css('display','none');
}


function getHovered(id){
	return getVisible(id).filter('.backcolor');
}
function getVisible(id){
	return $("#"+id+"Menu table tbody tr").filter(':visible');
}

function keydown(event,id){
	switch(event.which){
		case keys.UP:
			move('up',id);
			break;
		case keys.DOWN:
			move('down',id);
			break;
		case keys.TAB:
			enter(event,id);
			break;
		case keys.RIGHT:
			break;
		case keys.ENTER:
			enter(event,id);
			break;
		default:							
			break;
	}
}

function keyup(event,id) {
	switch (event.which) {
	case keys.ENTER:
	case keys.UP:
	case keys.DOWN:
	case keys.LEFT:
	case keys.RIGHT:
	case keys.TAB:
	case keys.SHIFT:
		break;
	default:
		filter(id);
		break;
	}
}

function filter(id){
	$("#"+id+"Menu table").find("tr").removeClass('backcolor');
	$("#"+id+"Menu table tbody tr").hide().filter(":contains('"+$("#"+id+"Text").val()+"')").show();
	move('down',id);
	autoHeight(id);
}

function move(dir,id){
	var items = getVisible(id),
	current = getHovered(id),
	index = current.prevAll('.option-item').filter(':visible').length,
	total = items.length;
	if(current.length == 0) {
		items.eq(0).addClass("backcolor");
		return;
	}
	switch(dir){
		case 'up':
			index--;
			(index < 0) && (index = (total - 1));
			break;
		case 'down':
			index++;
			(index >= total) && (index = 0);							
			break;
	}
	items.removeClass("backcolor").eq(index).addClass("backcolor");
	fixScroll(id);
}

function fixScroll(id){
	var item = getHovered(id);
	if(!item.length) return;
	var offsetTop,
		upperBound,
		lowerBound,
		heightDelta = item.outerHeight();
	offsetTop = item[0].offsetTop;
	upperBound = $("#"+id+"Menu").scrollTop();
	lowerBound = upperBound + 200 - heightDelta;
	if (offsetTop < upperBound) {
		$("#"+id+"Menu").scrollTop(offsetTop);
	} else if (offsetTop > lowerBound) {
		$("#"+id+"Menu").scrollTop(offsetTop - 200 + heightDelta);
	}
}

function autoHeight(id) {
	var items = getVisible(id);
	var len = items.length;
	if(len >= 7) {
		$("#"+id+"Menu").css('height','200px');
	} else {
		if(len == 1){
			$("#"+id+"Menu").css('height','40px');
		} else {
			var totalHeight = len*30;
			$("#"+id+"Menu").css('height',totalHeight+'px');
		}
	}
}

function enter(event,id){
	var item = getHovered(id);
	item.length && select(item,id);
	if(event && event.which == keys.ENTER) {
		if(item.length) {
			hideComobo(id+'Menu',id+'Arrow');
			$("#"+id+"Text").blur();
		}
		event.preventDefault();
		$("#"+id+"Text").focus();
		try{
			changeNextItem(id+"Text");
		}catch(e){
			
		}
	}
}

function select(event,id){
	var item = event.currentTarget? $(event.currentTarget) : $(event);
	if(!item.length) return;
	var index = item.attr('index');
	var value = $('#'+id+index).html();
	$("#"+id+"Text").val(value);
	$("#"+id).val(item.attr('key-value'));
}


// 隐藏div 显示输入控件
function onClickEvent(textAreaId,outDivId,divId,type) {
	// 必须先div隐藏 后输入框显示，否则表格会被撑开
	document.getElementById(outDivId).style.display = "none";
	document.getElementById(textAreaId).style.display = "block";
	moveEnd(document.getElementById(textAreaId));
}

function onSelectChooseEvent(textAreaId,outDivId,divId,type){
	document.getElementById(outDivId).style.display = "none";
	document.getElementById(textAreaId).style.display = "block";
	var select = document.getElementById('comobox_a0104');
	fireEvent(select,'onclick');
}

function fireEvent(ele, eventName) {
    if (document.all && !document.addEventListener) {
   ele.fireEvent(eventName);
   } else {
   var e = document.createEvent("HTMLEvents");
   e.initEvent(eventName.replace(/^on/i, ''), false, true);
   ele.dispatchEvent(e);
   }
}

function isIE() {
	return (navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0);
}

// 显示div 隐藏输入控件
function onblurEvent(textAreaId,outDivId,divId,warpId,type,offsetTop) {
	document.getElementById(textAreaId).style.display = "none";
	document.getElementById(outDivId).style.display = "block";
	if(type){
		document.getElementById(divId).innerText = document.getElementById(textAreaId).value;
	} else {
		document.getElementById(divId).innerHTML = document.getElementById(textAreaId).value;
	}
	adjustTop(warpId,divId,outDivId,offsetTop);
}

function onSelectedEvent(textAreaId,outDivId,divId,warpId,comoboId,hideId) {
	document.getElementById(textAreaId).style.display = "none";
	document.getElementById(outDivId).style.display = "block";
	var value = document.getElementById(comoboId).value;
	document.getElementById(divId).innerHTML=maleName[value];
	document.getElementById(hideId).value=value;
	adjustTop(warpId,divId,outDivId);
}

function onSubstrblurEvent(textAreaId,outDivId,divId,warpId,type,offsetTop,label) {
	document.getElementById(textAreaId).style.display = "none";
	document.getElementById(outDivId).style.display = "block";
	var a0107value = document.getElementById(textAreaId).value;
	if(a0107value==''){
		document.getElementById(divId).innerHTML="";
		return true;
	}
	rtn = dateValidateBeforeTady(a0107value);
	if(rtn!==true) {
		//触发校验
		$h.alert('系统提示', '您输入的【' + (label == null ? "" : label) + '】格式有误:' + rtn, function () {
			moveEndDiv(document.getElementById("wrapdiv_" + textAreaId), document.getElementById(textAreaId));
		}, 400);
		return false;
	}
	
	a0107value = a0107value.substring(0,4)+'.'+a0107value.substring(4,6);
	
	document.getElementById(divId).innerHTML=a0107value;
	adjustTop(warpId,divId,outDivId,offsetTop);
}

function isNumber(value) {
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}

function adjustTop(warpId,divId,outDivId,offsetTop) {
	if (offsetTop) {

	} else {
		offsetTop = 1;
	}
	//alert(offsetTop);
	// 调整top
	var wrapdiv = document.getElementById(warpId);
	var wrapdiv_h = wrapdiv.offsetHeight / 2;
	document.getElementById(divId).style.top = '-50%';
	var div_t = document.getElementById(divId).offsetTop - 4;
	if (isIE()) {
		if ((wrapdiv_h + div_t) <= 0) {//
			document.getElementById(divId).style.top = -wrapdiv_h + offsetTop;
		} else {
			document.getElementById(divId).style.top = '-43%';
		}
	} else {
		wrapdiv_h = wrapdiv.offsetHeight;
		if (document.getElementById(outDivId).offsetHeight < wrapdiv_h) {
			wrapdiv.style.marginTop = 4;
		} else {
			wrapdiv.style.marginTop = 0;
		}
	}
}

function moveEnd(obj) {
	obj.focus();
	var len = obj.value.length;
	if (document.selection) {// ie识别
		var sel = obj.createTextRange();
		sel.moveStart('character', len);
		sel.collapse();
		sel.select();
	} else if (typeof obj.selectionStart == 'number'
		&& typeof obj.selectionEnd == 'number') {
		obj.selectionStart = obj.selectionEnd = len;// ff和chrome
	}
}
function moveEndDiv(divObj,obj) {
	divObj.click();
	var len = obj.value.length;
	if (document.selection) {// ie识别
		var sel = obj.createTextRange();
		sel.moveStart('character', len);
		sel.collapse();
		sel.select();
	} else if (typeof obj.selectionStart == 'number'
		&& typeof obj.selectionEnd == 'number') {
		obj.selectionStart = obj.selectionEnd = len;// ff和chrome
	}
}

function openDiseaseInfoCommonQuerya0111() {
	alert('籍贯相关事件');
}

function openDiseaseInfoCommonQuerya0114() {
	alert('出生地相关事件');
}

function showPic() {
	alert('上传图片逻辑');
}



function openDiseaseInfoCommonQuery(id,codetype,codename,ctxPath,label) {
	if (event.keyCode == 8) {
		return false;
	}
	//var codetype;
	if (codename) {

	} else {
		codename = "code_name";
	}
	var winId = "winId" + Math.round(Math.random() * 10000);
	var url = "pages.sysorg.org.PublicWindow&aa=" + Math.random() + "&property=" + id + "&codetype=" + codetype + "&closewin=" + winId + "&codename=" + codename + "&subWinId=" + winId;

	//odin.openWindow(winId,label,url,270,415,window,true,true);
	$h.openPageModeWin(winId, url, label, 270, 385, '', ctxPath);
}
function returnwin(rs,id){
	if(rs!=null){
		var rss = rs.split(",");
		document.getElementById('comboxArea_'+id).value = rss[1];
		document.getElementById(id).value=rss[0];
		//console.log(id)
		if(id=='a0111'||id=='a0114'||id=='tagzybj'||id=='tagzc')
			onblurEvent('comboxArea_'+id,'out_'+id,'div_'+id,'wrapdiv_'+id,false);
		try{
			eval(id+"onchange(rss[0],rss[1])");
		}catch(e){}
	}
}

function hideComobo(menuList,arrowId){
	$("#"+menuList).css('display','none');
	$("#"+arrowId).removeClass("searchArrowRote");
	$("#"+arrowId).css('display','none');	
}

//日期输出格式 onblur 就2个日期输入项  不能更改的 就不校验了。
function rmbblurDate_bj(hideid,ischeck,isblur) 
{
	var id = hideid + "_1";
	var disObj = document.getElementById(id);
	var val = disObj.value+'';
	
	if(val==''){
		if(isblur){
			document.getElementById(hideid).value = val;
		}
		return;
	}
	var rtn;
	if (ischeck) {
		rtn = dateValidate(val);
	} else {
		rtn = dateValidateBeforeTady(val);
	}

	if (rtn !== true) {
		//触发校验
		$("#" + hideid + "_1").addClass('error_style');
		$("#" + hideid + "_err").css('display', 'block');
		$("#" + hideid + "_desc").attr('title', rtn);
		//moveEnd(document.getElementById(hideid+"_1"));

		return;
	} else {
		$("#" + hideid + "_1").removeClass('error_style');
		$("#" + hideid + "_err").css('display', 'none');
	}
	if (isblur) {
		document.getElementById(hideid).value = val;
		disObj.value = val.substr(0, 4) + "." + val.substr(4, 2);
	}
	
}


//聚焦的具体操作
function rmbrestoreDate(hid) {
	var id = hid + "_1";
	if($("#"+hid+"_err").css('display')=='block'){
		return;
	}
	var disObj = document.getElementById(id);
	//if(disObj.isValid()){
		var hval = document.getElementById(hid).value;
		if (hval!=null && hval!="") {
			disObj.value=hval;
			moveEnd(document.getElementById(id));
			/*var obj = document.getElementById(id);
			var len = hval.length;
            if (document.selection) {
                var sel = obj.createTextRange();
                sel.moveStart('character', len);
                sel.collapse();
                sel.select();
            } else if (typeof obj.selectionStart == 'number'
            && typeof obj.selectionEnd == 'number') {
                obj.selectionStart = obj.selectionEnd = len;
            }*/
		}
	//}else{
	
	//}
	

}





function setSelectShowValue(id,codetypeJson,value,outSelect){
	if(outSelect){
		document.getElementById(id+"Text").value=value;
	}else if(codetypeJson[value]){
		document.getElementById(id+"Text").value=codetypeJson[value];
	}else{
		document.getElementById(id+"Text").value='';
	}
	
	
}



function disableInput(check,inputId) {
	
	var obj = $('#'+inputId+'_1');
	if($(check).is(':checked')) {
		obj.attr("readonly",false);
	} else {
		$("#"+inputId+"_1").removeClass('error_style');
		$("#"+inputId+"_err").css('display','none');
		$('#'+inputId).val("");
		obj.val("");
		obj.attr("readonly","true");
	}
}



function setTDHeight(){
	$("#a14z101").css("height",70);
	$("#a14z101").css("height",$("#a14z101")[0].scrollHeight+10);
}