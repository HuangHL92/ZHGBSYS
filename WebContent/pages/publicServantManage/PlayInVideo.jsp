<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page import="com.insigma.siis.local.business.entity.MediaEntity"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="GBK">
<title>Document</title>
<script
	src="<%=request.getContextPath()%>/basejs/jwplayer/js/jquery-3.2.1.js"></script>
<script
	src="<%=request.getContextPath()%>/playVideo/jwplayer/jwplayer.js"></script>
<script src="<%=request.getContextPath()%>/playVideo/pdfobject.js"></script>
<script>jwplayer.key="hTHv8+BvigYhzJUOpkmEGlJ7ETsKmqyrQb8/PetBTBI=";</script>
<style>
body, ul, li {
	padding: 0;
	margin: 0
}

.box ul, .box li {
	list-style: none
}

.box img {
	border: none
}

.box a {
	color: #6cf
}

a:hover {
	color: #84b263
}

.box {
	width: 980px;
	margin: 0 auto;
	position: relative;
	overflow: hidden;
	_height: 100%
}

.picbox {
	width: 980px;
	height: 115px;
	overflow: hidden;
	position: relative
}

.piclist {
	height: 115px;
	position: absolute;
	left: 0;
	top: 0
}

.piclist li {
	background: #eee;
	margin-right: 20px;
	padding: 5px;
	float: left
	
}
.piclist img {
	height: 300px;
	width: 200px;
	
}
.swaplist {
	position: absolute;
	left: -3000px;
	top: 0
}

.og_prev, .og_next {
	width: 30px;
	height: 50px;
	background: url<%=request.getContextPath()%>/playVideo/images/icon.png)
		no-repeat;
	background: url(<%= request.getContextPath ()%>/playVideo/images/icon_ie6.png)
		no-repeat;
	position: absolute;
	top: 33px;
	z-index: 99;
	cursor: pointer;
	filter: alpha(opacity = 70);
	opacity: .7
}

.og_prev {
	background-position: 0 -60px;
	left: 4px
}

.og_next {
	background-position: 0 0;
	right: 4px
}

body {
	margin: 0;
	background: #6E6E6E url(<%= request.getContextPath ()%>/playVideo/images/play_backgroup.png)
		no-repeat scroll top;
}

#container, #btnPlayer {
	margin: auto auto;
	margin-top: 13px;
	text-align: center;
	position: relative;
	left: 0px;
	top: 0px;
	z-index: 99998
}

#panel_obj {
	margin: auto auto;
	width: 1024px;
	height: 768px;
}

#control_panel {
	margin-bottom: 0px;
	width: 1024px;
	height: 218px;
	color: #FFFFFF;
}

#photo_item {
	width: 115px;
	height: 139px;
}

#control_table {
	padding: 14px;
	font: normal 14px 黑体;
	color: #FFFFFF;
	text-align: left;
}

#control_table #title {
	font-size: 14px;
}

.namecol {
	padding: 5px;
	width: 47px;
	height: 21px;
	color: #999999;
	text-align: right;
}

.valuecol {
	padding: 5px;
	width: 500px;
	height: 21px;
}

#control_table {
	width: 711px;
}

.playHistory {
	padding: 5px;
	width: 300px;
	height: 130px;
	font-size: 14px;
}

.playHistoryList {
	padding: 0px;
	width: 300px;
	height: 100px;
	overflow-y: scroll;
}

.viewChange {
	cursor: pointer;
	position: relative;
	z-index: 99999;
	float: left;
}
/*pdf*/
.pdfobject-container {
	position: relative;
	left: 117px;
	width: 100%;
	max-width: 790px;
	height: 419px; /*expression(document.body.clientHeight+"px");*/
	margin: 2em 0;
}

.pdfobject {
	border: solid 1px #666;
}

#results {
	padding: 1rem;
}

.hidden {
	display: none;
}

.success {
	color: #4F8A10;
	background-color: #DFF2BF;
}

.fail {
	color: #D8000C;
	background-color: #FFBABA;
}
</style>
<script>
  $(function(e){
	  	//获取当前点击的多媒体路径
	  	var path = "<%= request.getParameter("path")%>"
	  	//获取多媒体文件后缀
	  	var suffix = path.substr(path.lastIndexOf(".")+1,3);
	  	//显示视频信息列表
  		showVideoList();
  		var thePlayer;
  		//显示PDF文件列表
  		showPdfList();
  		//调用判断后缀方法
        jump(suffix);
  		//time=window.setInterval(function(){$('.og_next').click();},5000);
  		linum=$('.mainlist li').length;w=linum*250;
  		$('.piclist').css('width',w+'px');
  		$('.swaplist').html($('.mainlist').html());
  		$('.og_next').click(function(){if($('.swaplist,.mainlist').is(':animated')){$('.swaplist,.mainlist').stop(true,true);}
if($('.mainlist li').length>4){ml=parseInt($('.mainlist').css('left'));sl=parseInt($('.swaplist').css('left'));if(ml<=0&&ml>w*-1){$('.swaplist').css({left:'1000px'});$('.mainlist').animate({left:ml-1000+'px'},'slow');if(ml==(w-1000)*-1){$('.swaplist').animate({left:'0px'},'slow');}}else{$('.mainlist').css({left:'1000px'})
$('.swaplist').animate({left:sl-1000+'px'},'slow');if(sl==(w-1000)*-1){$('.mainlist').animate({left:'0px'},'slow');}}}})
$('.og_prev').click(function(){if($('.swaplist,.mainlist').is(':animated')){$('.swaplist,.mainlist').stop(true,true);}
if($('.mainlist li').length>4){ml=parseInt($('.mainlist').css('left'));sl=parseInt($('.swaplist').css('left'));if(ml<=0&&ml>w*-1){$('.swaplist').css({left:w*-1+'px'});$('.mainlist').animate({left:ml+1000+'px'},'slow');if(ml==0){$('.swaplist').animate({left:(w-1000)*-1+'px'},'slow');}}else{$('.mainlist').css({left:(w-1000)*-1+'px'});$('.swaplist').animate({left:sl+1000+'px'},'slow');if(sl==0){$('.mainlist').animate({left:'0px'},'slow');}}}})});
  	$(document).ready(function(){$('.og_prev,.og_next').hover(function(){$(this).fadeTo('fast',1);},function(){$(this).fadeTo('fast',0.7);})
		
  	})

  </script>
</head>
<body>
	<div id="panel_obj">
		<div id="panel_obj_item">
			<div id="container">loading the player...</div>
			<div></div>
			<div id="btnPlayer" style="display: none">
				<input type="button" class="player-play" value="播放" /> <input
					type="button" class="player-stop" value="停止" /> <input
					type="button" class="player-status" value="取得状态" /> <input
					type="button" class="player-current" value="当前播放秒数" /> <input
					type="button" class="player-goto" value="转到第30秒播放" /> <input
					type="button" class="player-length" value="视频时长(秒)" />
			</div>
			<div id="control_panel">
				<table id="control_table">
					<tr>
						<td id="title">测试标题测试标题测试标题</td>
					</tr>
					<tr>
						<td>
							<table align="left" width="711px">
								<tr>
									<td rowspan="5"><img id="photoitem"
										src="<%=request.getContextPath()%>/playVideo/images/photo.png" />
									</td>
								</tr>
								<tr>
									<td class="namecol">姓名:</td>
									<td class="valuecol" id="username">${name}</td>
								</tr>
								<tr>
									<td class="namecol">民族:</td>
									<td class="valuecol" id="nation">满族</td>
								</tr>
								<tr>
									<td class="namecol">年龄:</td>
									<td class="valuecol" id="age">45</td>
								</tr>
								<tr>
									<td class="namecol">职务:</td>
									<td class="valuecol" id="post">贵州省收代理费见识到了开发简历时代峻峰</td>
								</tr>
							</table>
						</td>

						<td>

							<table align="right" class="playHistory">
								<tr>
									<td height="30">播放列表</td>
								</tr>
								<tr>
									<td class="playHistoryList" height="100px">
										<div style="width: 100%; height: 100px;" id="playlist" >
											
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="panel_view_item" style="display: none">
			<div id="example1" style="position: relative; z-index: 99998"></div>
			<img
				src="<%=request.getContextPath()%>/playVideo/images/left_button.png"
				class="viewChange" style="left: 20px; top: -290px;"
				onclick="leftRead()" /> <img
				src="<%=request.getContextPath()%>/playVideo/images/right_button.png"
				class="viewChange" style="left: 895px; top: -290px;"
				onclick="rightRead()" />
			<div class="box">
				<div class="picbox">
					<ul class="piclist mainlist" id="pdflist">
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/zymcbg.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/1.jpg"
								width="220" height="105" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/ReleaseNotes.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/2.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/printTemplate.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/3.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/oc4jusersguide.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/4.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/ReleaseNotes.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/5.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/pdf/dnzzlife.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/2.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/dnzzlife.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/3.jpg" /></a></li>
						<li><a href="javascript:void(0)" filepath="<%=request.getContextPath()%>/playVideo/pdf/zymcbg.pdf"
							target="_blank"><img
								src="<%=request.getContextPath()%>/playVideo/preview/4.jpg" /></a></li>
					</ul>
					<ul class="piclist swaplist"></ul>
				</div>
				<div class="og_prev"></div>
				<div class="og_next"></div>
			</div>
		</div>
		<img src="<%=request.getContextPath()%>/playVideo/images/vd_sp.png"
			class="viewChange" style="left: 900px; top: -740px;" id="jumpButton"
			onclick="viewChangeClick()"/>
	</div>
	<script type="text/javascript">
	var mediaList;
	var shwoMediaList;
	var pdfList;
	//获取视频播放列表
	function showVideoList(){
		<%
			String a0000=request.getParameter("a0000");
			String jsa00=request.getParameter("jsa00");
			
		%>
		$.ajax({
			"url":"<%=request.getContextPath()%>/getMediaInfo?a0000=<%=a0000%>&jsa00=<%=jsa00%>",
			"type":"GET",
			"async":false,
			"data":{},
			"dataType":"json",
			success:function(data){
			//替换掉个人信息
				$("#photoitem").attr("src","/hzb/servlet/DownloadUserHeadImage?a0000=<%=a0000%>")
				$("#username").html(data.name);
				$("#nation").html(data.nation);
				$("#age").html(data.age);
				$("#post").html(data.post);
				//替换标题
				$("#title").html(data.videoTitle)
				shwoMediaList=data.showVideoList;
				mediaList=data.videoList;
				pdfList=data.showPdfList;
			 	var siteMap=eval(shwoMediaList)
			 	var videoHtml="";
			 	//遍历有序的视频列表,展示出来
			 	for(var j=0;j<siteMap.length;j++){
				　　$.each(siteMap[j],function(key,value){
					videoHtml +="<a href='"+value+"' style='text-decoration: none; color: white;'>"+key+"</a></br>"
				});
				
				}
			 	
				$("#playlist").html(videoHtml);
			},
			error:function(){
				alert("出现未知错误,查询失败,请重试一下");
			}
			
		})
	}
	//显示PDF展示列表
	var sc;
	function showPdfList(){
		
		if(pdfList==null){
		}else{
			$("#pdflist").html("")
			var siteMap=eval(pdfList)
			var pdfHtml="";
		 	for(var j=0;j<siteMap.length;j++){
			　　$.each(siteMap[j],function(key,value){
				pdfHtml+="<li><a href='javascript:void(0)' filepath='"+value+"'target='_blank'><img src='"+key+"'width='220' height='105'/></a></li>"
			});
			
			}
		 	$("#pdflist").html(pdfHtml)
		 	sc = $('.piclist a').map(function(){return $(this).attr("filepath")}).get();
		}
		
	}
	function setpdf1(filePath){
		   var optionst = {  
                pdfOpenParams: {view: 'fitH', page: '1',pagemode:'thumbs',scrollbar:'1'},     
            };  
		PDFObject.embed(filePath, "#example1",optionst);
	}
	
	var inx = 0;
	//点击切换pdf
	function leftRead(){
		if(inx==0){
			alert('已经是第一个文件。');
		}else{
			setpdf1(sc[inx--]);
		}
	}
	function rightRead(){
		if(inx==sc.length-1){
			alert('已经是最后一个文件。');
		}else{
			setpdf1(sc[inx++]);
		}
	}
            var thePlayer;  //保存当前播放器以便操作  
           $(document).ready(function() {
        	   	
            	$('.piclist a').click(function(){
            		debugger;
            		setpdf1($(this).attr("filepath"));
            		inx = $(this).index();
            		return false;
            	});
            	setpdf1(sc[0]);
                thePlayer = jwplayer('container').setup({ 
				    autostart: false,
                    flashplayer: 'jwplayer.flash.swf',  
                   <%--  file: '<%=request.getContextPath()%>/playVideo/video.mp4', --%>  
                   file: '<%= request.getParameter("path")%>', 
                   width: 1003,  
                    height: 534,  
					volume: 0,
					
                });  
              <%--   //var playlists= [{ title:"11111111111111",file: "<%=request.getContextPath()%>/playVideo/video.mp4"},
                // { title:"22222222222222",file: "<%=request.getContextPath()%>/playVideo/video.mp4"},
                 //{ title:"33333333333333",file: "<%=request.getContextPath()%>/playVideo/video.mp4"}]; --%>
               //获取视频播放列表(把当前播放视频排到第一位)
               var playlists=new Array();
                var siteMap=eval(mediaList);
                for(var j=0;j<siteMap.length;j++){
  				　　$.each(siteMap[j],function(key,value){
  					var playPath=value.substr(value.indexOf("=")+1,value.length);
  				  	playlists.push({title:key,file:playPath})
  				});
  				
  				}
                thePlayer.load(playlists);
                //播放 暂停  
                $('.player-play').click(function() {  
                	alert("点击了")
                    if (thePlayer.getState() != 'PLAYING') {  
                        thePlayer.play(true);  
                        this.value = '暂停';  
                    } else {  
                        thePlayer.play(false);  
                        this.value = '播放';  
                    }  
                });  
                  
               //停止
                $('.player-stop').click(function() {
                	thePlayer.stop(); 
                	}); 
                  
                //获取状态  
                $('.player-status').click(function() {  
                    var state = thePlayer.getState();  
                    var msg;  
                    switch (state) {  
                        case 'BUFFERING':  
                            msg = '加载中';  
                            break;  
                        case 'PLAYING':  
                            msg = '正在播放';  
                            break;  
                        case 'PAUSED':  
                            msg = '暂停';  
                            break;  
                        case 'IDLE':  
                            msg = '停止';  
                            break;  
                    }  
                    alert(msg);  
                });  

                //获取播放进度  
                $('.player-current').click(function() { alert(thePlayer.getPosition()); });  
          
                //跳转到指定位置播放  
                $('.player-goto').click(function() {  
                    if (thePlayer.getState() != 'PLAYING') {    //若当前未播放，先启动播放器  
                        thePlayer.play();  
                    }  
                    thePlayer.seek(30); //从指定位置开始播放(单位：秒)  
                });    
                //获取视频长度  
                $('.player-length').click(function() { alert(thePlayer.getDuration()); });  
            });
          
           //判断点击播放的是否是pdf
           function jump(suffix){
        	   if(suffix=="pdf"){
        		   $("#jumpButton").click();
        		   
        		 
        	   }
           } 
           //点击按钮更换资料与视频页面
           function viewChangeClick(){
   			if(panel_obj_item.style.display=='none'){
   					$(".viewChange").css("top","-740px")
   					$(".viewChange").css("left","900px")
   					panel_obj_item.style.display='block';
   					panel_view_item.style.display='none';

   			}else{
   					panel_obj_item.style.display='none';
   					panel_view_item.style.display='block';
   					$(".viewChange").css("top","-625px")
   					$(".viewChange").css("left","900px")
   					$('.player-stop').click();
   			}

           }
        </script>
       
</body>
</html>