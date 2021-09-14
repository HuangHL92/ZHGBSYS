<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%
  String picUrl = request.getParameter("Picurl"); 
String a0000 = request.getParameter("a0000"); 
  String  step = request.getParameter("step"); 
  String ctxPath = request.getContextPath(); 
  //String fomat="";String rohto="";
  String src = "";
  
  String ImgIdPostfix = request.getParameter("ImgIdPostfix");
  if (a0000!=null&&!"".equals(a0000)&&(ImgIdPostfix==null || "".equals(ImgIdPostfix))){
  	ImgIdPostfix = a0000.replaceAll("\\-", "");
  }  
  
if("3".equals(step)){
	a0000 = URLEncoder.encode(a0000, "utf-8");
	src = request.getContextPath()+"/servlet/DownloadUserHeadImage?ImgIdPostfix="+ImgIdPostfix+"&a0000="+a0000;
}
  
 


System.out.println("ImgIdPostfix==============>"+ImgIdPostfix);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>照片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
<base target = "_self" /> 
<link href="<%=ctxPath %>/picCut/css/main.css" type="text/css" rel="Stylesheet" />
    <script type="text/javascript" src="<%=ctxPath %>/picCut/js/jquery1.2.6.pack.js"></script>
    <script  type="text/javascript" src="<%=ctxPath %>/picCut/js/ui.core.packed.js" ></script>
    <script type="text/javascript" src="<%=ctxPath %>/picCut/js/ui.draggable.packed.js" ></script>
    <script type="text/javascript" src="<%=ctxPath %>/picCut/js/CutPic.js"></script>
    <script type="text/javascript">
    var rparent = window.dialogArguments.window;
    var parent = window.dialogArguments.window;
    window.name="win_test";
        function Step1() {
            $("#Step2Container").hide();           
        }

        function Step2() {
            $("#CurruntPicContainer").hide();
        }
        function Step3() {
        	try{ 
        		rparent.document.getElementById('personImg').src=rparent.document.getElementById('personImg').src;
        		window.close();
        	}catch(e){
        		alert("可能没有替换成功，请刷新！");
        	}
            
       }
       
  function setA0000(){
  	var a0000 = rparent.document.getElementById('a0000').value;
  	document.getElementById('a0000').value = a0000;
  }     
       
 function submitform(){
	var fileobj = document.getElementById("fuPhoto");
	var pathvalue = fileobj.value.toLowerCase();
	var endStr = '.jpg';
	if(pathvalue==''){
		return;
	}
	var d=pathvalue.length-4;
  	if (d>=0&&pathvalue.lastIndexOf(endStr)==d){
  		//document.form1.submit();
  		uploadPhoto()
  	}else{
  		alert('请上传JPG格式的照片!');
  	}
}      
 
 
 
 function uploadPhoto(){
		
		//照片删除需要确认
			var file = $("#fuPhoto")[0].files[0];
	        var form = new FormData();
	        form.append("file", file);
	        form.append("ImgIdPostfix", $('#ImgIdPostfix').val());
			var a0000 = rparent.document.getElementById("a0000").value;
			$.ajax({
				url:"<%=ctxPath %>/servlet/UpLoadUserHeadImage?ImgIdPostfix=<%=ImgIdPostfix%>",
				type:"POST",
				contentType: "multipart/form-data",
	            data: form,
	            async: false, //异步
	            processData: false, //很重要，告诉jquery不要对form进行处理
	            contentType: false, //很重要，指定为false才能形成正确的Content-Type
				success:function(data){
					eval(data);
				},
				error:function(data){
					alert(data);
				}
			});
			
		
		
		
	}
 
 
 
 
 function uploadPhoto2(){
		
		//照片删除需要确认
			var data = $("#form12").serialize(); 
			$.ajax({
				url:"<%=ctxPath %>/servlet/ZoomImage",
				type:"POST",
			    data:data,  //重点必须为一个变量如：data
				success:function(data){
					eval(data);
				},
				error:function(data){
					alert("status:"+data.status+",statusText:"+data.statusText
							+",response:"+data.response
							+",timeout:"+data.timeout
							+",responseURL:"+data.responseURL);
				}
			});
			
		
		
		
	}
    </script>
</head>
<body onload="setA0000()">
   

    <div>
     <div class="left">
         <!--当前照片
         <div id="CurruntPicContainer">
            <div class="title"><b>当前照片</b></div>
            <div class="photocontainer">
                <img id="imgphoto" src="picCut/UploadPhoto/20090117105013734.jpg" style="border-width:0px;" />
            </div>
         </div>
         -->
         <!--Step 2-->
         <div id="Step2Container">
                   <div id="Canvas" class="uploaddiv">
                   
                            <div id="ImageDragContainer">                               
                               <img id="ImageDrag" class="imagePhoto" src="<%=ctxPath %>/picCut/UploadPhoto/<%=picUrl+"?r="+Math.random() %>" style="border-width:0px;" />                                                        
                            </div>
                            <div id="IconContainer">                               
                               <img id="ImageIcon" class="imagePhoto" src="<%=ctxPath %>/picCut/UploadPhoto/<%=picUrl+"?r="+Math.random()%>" style="border-width:0px;" />                                                        
                            </div>                          
                    </div>
         </div>
     
     </div>
<div class="right">    
     <div>
     	<form name="form1" method="post" action="<%=ctxPath %>/servlet/UpLoadUserHeadImage?ImgIdPostfix=<%=ImgIdPostfix%>&r=<%=Math.random() %>" target="win_test" id="form1" enctype="multipart/form-data">
         <!--Step 1-->
         <div id="Step1Container">
            <div class="title"><b>更换照片</b></div>
            <div id="uploadcontainer">
                <div class="uploadtooltip">请选择新的照片文件，上传照片需小于2M，否则无法进行上传压缩。</div>
                <div class="uploaddiv"><input type="file" name="fuPhoto" accept="*.jpg,*.gif,*.png,*.jpeg,*.bmp" id="fuPhoto" title="选择照片" onchange="submitform();"/></div>
               <!--  <div class="uploaddiv"><input type="button" name="btnUpload" value="确 定" onclick="submitform();" id="btnUpload" /></div> -->
            </div>
         </div>
         <input type="hidden" id="ImgIdPostfix" value="<%=ImgIdPostfix%>"/>
         </form>
     </div>
     
     <div style="margin-top: 100px;">
     	<div class="uploaddiv">
            <table>
                 <tr>
                     <td id="Min">
                             <img alt="缩小" src="<%=ctxPath %>/picCut/image/_c.gif" onmouseover="this.src='<%=ctxPath %>/picCut/image/_c.gif';" onmouseout="this.src='<%=ctxPath %>/picCut/image/_h.gif';" id="moresmall" class="smallbig" />
                     </td>
                     <td>
                         <div id="bar">
                             <div class="child">
                             </div>
                         </div>
                     </td>
                     <td id="Max">
                             <img alt="放大" src="<%=ctxPath %>/picCut/image/c.gif" onmouseover="this.src='<%=ctxPath %>/picCut/image/c.gif';" onmouseout="this.src='<%=ctxPath %>/picCut/image/h.gif';" id="morebig" class="smallbig" />
                     </td>
                 </tr>
             </table>
         </div>
     </div>
     <div>
	     <form name="form12" id="form12" action="<%=ctxPath %>/servlet/ZoomImage" method="post">
	     
	                    <input type="hidden" name="picture" value="<%=picUrl%>"/>
	                    <div class="uploaddiv">
	                    	<input type="hidden" name="personImg" id="personImg" value="<%=src%>" />
	                    	<input type="hidden" name="a0000" id="a0000" />
	                        <input type="submit" name="btn_Image" onclick="uploadPhoto2();return false;" value="保存头像" id="btn_Image" />
	                    </div>           
	                 <div style="display: none;">
	                    图片实际宽度： <input name="txt_width" type="text" value="1" id="txt_width" /><br />
	                    图片实际高度： <input name="txt_height" type="text" value="1" id="txt_height" /><br />
	                    距离顶部： <input name="txt_top" type="text" value="82" id="txt_top" /><br />
	                    距离左边： <input name="txt_left" type="text" value="73" id="txt_left" /><br />
	                    截取框的宽： <input name="txt_DropWidth" type="text" value="272" id="txt_DropWidth" /><br />
	                    截取框的高： <input name="txt_DropHeight" type="text" value="340" id="txt_DropHeight" /><br />
	                    放大倍数： <input name="txt_Zoom" type="text" id="txt_Zoom" />
	                 </div>  
	                 
	       </form>
      </div>
</div>        
          
    </div>
    <% 
      if(null==picUrl||"".equals(picUrl))
      {%>
          <script type='text/javascript'>Step1();</script>
      <%}else if(!"".equals(picUrl)&& "2".equals(step)){
      %>
      <script type='text/javascript'>Step2();</script>
      <%}else if(!"".equals(picUrl)&& "3".equals(step)){
      %>
      <script type='text/javascript'>Step3();</script>
      <%}%>

</body>
</html>