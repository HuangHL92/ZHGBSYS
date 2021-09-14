/*!
 * nav-default JavaScript Library
 *
 *  使用前需先加载依赖JS:
 * 	jQuery v1.12.4 jquery.com | jquery.org/license
 * 
 */

var leftWidth = 240;
var middleWidth = 7;
var clientWidth = $(window).width();
var clientHeight = $(window).height();

var leftTd = document.getElementById("left-td");
var middleTd = document.getElementById("middle-td");
var rightTd = document.getElementById("right-td");
var containerDiv = document.getElementById("container-div");

var searchDiv = document.getElementById("search-div");
var searchTbl = document.getElementById("search-tbl");
var searchBtn = document.getElementById("search-btn");

function autoResize() {
	clientWidth = $(window).width();
	clientHeight = $(window).height();
	leftTd.style.height = clientHeight + 'px';
	leftTd.style.width = leftWidth + 'px';

	// 搜索区域高度超出最大高度, 添加滚动条
	//if (searchDiv.scrollHeight > clientHeight - searchBtn.offsetHeight) {
	if(searchBtn){
		searchDiv.style.height = clientHeight - $(searchBtn).height() + 'px';
	}else{
		searchDiv.style.height = clientHeight + 'px';
	}

	/* searchDiv.style.overflowY = "scroll"; */
	//} else {
		/* searchDiv.style.overflowY = "hidden"; */
		/* searchTbl.style.margin = '10px 0 10px 8px'; */
	//}

	middleTd.style.height = clientHeight + 'px';
	middleTd.style.width = middleWidth + 'px';

	rightTd.style.height = clientHeight + 'px';
	rightTd.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';

	containerDiv.style.height = clientHeight + 'px';
	containerDiv.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';

	// 窗口大小改变时，初始化页面布局
	window.onresize = autoResize;
	/*
	 * $(window).resize(function() { // 窗口大小改变时，初始化页面布局 autoResize(); });
	 */
}

// 初始化页面布局
Ext.onReady(function(){
	autoResize();
})
autoResize();

// 左侧框 收缩与展开
function foldLeftContainer(node) {
	if (node.style.background.indexOf("right.png") != -1) { // 收缩
		node.style.background = "url(image/left.png) #D6E3F2 no-repeat center center";

		rightTd.style.width = clientWidth - middleWidth + 'px';
		containerDiv.style.width = clientWidth - middleWidth + 'px';

		leftTd.style.display = "none";
	} else { // 展开
		node.style.background = "url(image/right.png) #D6E3F2 no-repeat center center";

		leftTd.style.display = "block";

		rightTd.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';
		containerDiv.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';
	}
}
