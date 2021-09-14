/*!
 * nav-default JavaScript Library
 *
 *  ʹ��ǰ���ȼ�������JS:
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

	// ��������߶ȳ������߶�, ��ӹ�����
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

	// ���ڴ�С�ı�ʱ����ʼ��ҳ�沼��
	window.onresize = autoResize;
	/*
	 * $(window).resize(function() { // ���ڴ�С�ı�ʱ����ʼ��ҳ�沼�� autoResize(); });
	 */
}

// ��ʼ��ҳ�沼��
Ext.onReady(function(){
	autoResize();
})
autoResize();

// ���� ������չ��
function foldLeftContainer(node) {
	if (node.style.background.indexOf("right.png") != -1) { // ����
		node.style.background = "url(image/left.png) #D6E3F2 no-repeat center center";

		rightTd.style.width = clientWidth - middleWidth + 'px';
		containerDiv.style.width = clientWidth - middleWidth + 'px';

		leftTd.style.display = "none";
	} else { // չ��
		node.style.background = "url(image/right.png) #D6E3F2 no-repeat center center";

		leftTd.style.display = "block";

		rightTd.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';
		containerDiv.style.width = clientWidth - searchDiv.offsetWidth - middleWidth + 'px';
	}
}
