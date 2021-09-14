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
	font: normal 14px ����;
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
	  	//��ȡ��ǰ����Ķ�ý��·��
	  	var path = "<%= request.getParameter("path")%>"
	  	//��ȡ��ý���ļ���׺
	  	var suffix = path.substr(path.lastIndexOf(".")+1,3);
	  	//��ʾ��Ƶ��Ϣ�б�
  		showVideoList();
  		var thePlayer;
  		//��ʾPDF�ļ��б�
  		showPdfList();
  		//�����жϺ�׺����
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
				<input type="button" class="player-play" value="����" /> <input
					type="button" class="player-stop" value="ֹͣ" /> <input
					type="button" class="player-status" value="ȡ��״̬" /> <input
					type="button" class="player-current" value="��ǰ��������" /> <input
					type="button" class="player-goto" value="ת����30�벥��" /> <input
					type="button" class="player-length" value="��Ƶʱ��(��)" />
			</div>
			<div id="control_panel">
				<table id="control_table">
					<tr>
						<td id="title">���Ա�����Ա�����Ա���</td>
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
									<td class="namecol">����:</td>
									<td class="valuecol" id="username">${name}</td>
								</tr>
								<tr>
									<td class="namecol">����:</td>
									<td class="valuecol" id="nation">����</td>
								</tr>
								<tr>
									<td class="namecol">����:</td>
									<td class="valuecol" id="age">45</td>
								</tr>
								<tr>
									<td class="namecol">ְ��:</td>
									<td class="valuecol" id="post">����ʡ�մ���Ѽ�ʶ���˿�������ʱ������</td>
								</tr>
							</table>
						</td>

						<td>

							<table align="right" class="playHistory">
								<tr>
									<td height="30">�����б�</td>
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
	//��ȡ��Ƶ�����б�
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
			//�滻��������Ϣ
				$("#photoitem").attr("src","/hzb/servlet/DownloadUserHeadImage?a0000=<%=a0000%>")
				$("#username").html(data.name);
				$("#nation").html(data.nation);
				$("#age").html(data.age);
				$("#post").html(data.post);
				//�滻����
				$("#title").html(data.videoTitle)
				shwoMediaList=data.showVideoList;
				mediaList=data.videoList;
				pdfList=data.showPdfList;
			 	var siteMap=eval(shwoMediaList)
			 	var videoHtml="";
			 	//�����������Ƶ�б�,չʾ����
			 	for(var j=0;j<siteMap.length;j++){
				����$.each(siteMap[j],function(key,value){
					videoHtml +="<a href='"+value+"' style='text-decoration: none; color: white;'>"+key+"</a></br>"
				});
				
				}
			 	
				$("#playlist").html(videoHtml);
			},
			error:function(){
				alert("����δ֪����,��ѯʧ��,������һ��");
			}
			
		})
	}
	//��ʾPDFչʾ�б�
	var sc;
	function showPdfList(){
		
		if(pdfList==null){
		}else{
			$("#pdflist").html("")
			var siteMap=eval(pdfList)
			var pdfHtml="";
		 	for(var j=0;j<siteMap.length;j++){
			����$.each(siteMap[j],function(key,value){
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
	//����л�pdf
	function leftRead(){
		if(inx==0){
			alert('�Ѿ��ǵ�һ���ļ���');
		}else{
			setpdf1(sc[inx--]);
		}
	}
	function rightRead(){
		if(inx==sc.length-1){
			alert('�Ѿ������һ���ļ���');
		}else{
			setpdf1(sc[inx++]);
		}
	}
            var thePlayer;  //���浱ǰ�������Ա����  
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
               //��ȡ��Ƶ�����б�(�ѵ�ǰ������Ƶ�ŵ���һλ)
               var playlists=new Array();
                var siteMap=eval(mediaList);
                for(var j=0;j<siteMap.length;j++){
  				����$.each(siteMap[j],function(key,value){
  					var playPath=value.substr(value.indexOf("=")+1,value.length);
  				  	playlists.push({title:key,file:playPath})
  				});
  				
  				}
                thePlayer.load(playlists);
                //���� ��ͣ  
                $('.player-play').click(function() {  
                	alert("�����")
                    if (thePlayer.getState() != 'PLAYING') {  
                        thePlayer.play(true);  
                        this.value = '��ͣ';  
                    } else {  
                        thePlayer.play(false);  
                        this.value = '����';  
                    }  
                });  
                  
               //ֹͣ
                $('.player-stop').click(function() {
                	thePlayer.stop(); 
                	}); 
                  
                //��ȡ״̬  
                $('.player-status').click(function() {  
                    var state = thePlayer.getState();  
                    var msg;  
                    switch (state) {  
                        case 'BUFFERING':  
                            msg = '������';  
                            break;  
                        case 'PLAYING':  
                            msg = '���ڲ���';  
                            break;  
                        case 'PAUSED':  
                            msg = '��ͣ';  
                            break;  
                        case 'IDLE':  
                            msg = 'ֹͣ';  
                            break;  
                    }  
                    alert(msg);  
                });  

                //��ȡ���Ž���  
                $('.player-current').click(function() { alert(thePlayer.getPosition()); });  
          
                //��ת��ָ��λ�ò���  
                $('.player-goto').click(function() {  
                    if (thePlayer.getState() != 'PLAYING') {    //����ǰδ���ţ�������������  
                        thePlayer.play();  
                    }  
                    thePlayer.seek(30); //��ָ��λ�ÿ�ʼ����(��λ����)  
                });    
                //��ȡ��Ƶ����  
                $('.player-length').click(function() { alert(thePlayer.getDuration()); });  
            });
          
           //�жϵ�����ŵ��Ƿ���pdf
           function jump(suffix){
        	   if(suffix=="pdf"){
        		   $("#jumpButton").click();
        		   
        		 
        	   }
           } 
           //�����ť������������Ƶҳ��
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