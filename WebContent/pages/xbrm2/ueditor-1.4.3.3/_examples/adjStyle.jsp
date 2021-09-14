<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>样式排版</title>
  <meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 

　　<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="../ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="editor_api.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="../lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>

<div>  
    <script id="editor" type="text/plain" style="width:100%;height:500px;">
	</script>
</div>

<script type="text/javascript">
	window.onbeforeunload= function(event) { 
		if (confirm("确定需要更新吗？")){
			saveHtml();
			return ; 
		} 
	}
	
	//Ctrl+S保存
	document.onkeydown=function()   {
	    if (event.ctrlKey == true && event.keyCode == 83) {//Ctrl+S 
	    	saveHtml(); 
	    }
	 
	}
	
	$(document).ready(function () { 
		var t=setTimeout("setContent()",1000); 
	});
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');


    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = parent.$("#coordTable")[0].outerHTML;
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    
    function unbind(){
        var edui9_state = document.getElementById("edui9_state");
        edui9_state.onmousedown = '';
        var edui9_body = document.getElementById("edui9_body");
        edui9_body.onmousedown = '';
        try{
        	$("#edui9").unbind(); 
        	document.getElementById("edui9").onclick = null; 
        }catch(e){
        	alert(e);
        }
        $("#edui9").html("");
        $("#edui9").html('<div class="edui-default edui-state-hover" id="edui9_state" ><div class="edui-button-wrap edui-default"><div title="保存" class="edui-button-body edui-default" id="edui9_body" unselectable="on"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div>');
        $("#edui9").bind("click",function(){ 
        	saveHtml();
        	alert("保存成功！");
        });          	
    }
    
    String.prototype.replaceAll = function(s1, s2) {
        return this.replace(new RegExp(s1, "gm"), s2);
    }
    
    function setContent(isAppendTo) { 
    	unbind();      
    	var html = opener.$("#coordTable")[0].outerHTML; 
    	html = html.replaceAll("width: 0px","display:none!important"); 
    	html = html.replaceAll("WIDTH: 0px","display:none!important");   
    	var o = $(html); 
    	/* 
    	if (opener.$("#yntype").val()=="TPHJ1"){
    		$(o).find(".YiJiBiaoTiTD").attr("colspan","16");
    	}*/
    	/* 
		if (opener.$("#yntype").val()=="TPHJ2" || 
				opener.$("#yntype").val()=="TPHJ3"){ 
			$(o).find("tr").each(function () { 
				if ($(this).index()<=0) $(this).children('th:eq(0)').css("display","none");
				if ($(this).index()>=2) $(this).children('td:eq(0)').css("display","none");
			}); 
		}else{ 
			$(o).find("tr").each(function () { 
				if ($(this).index()<=0) {
					$(this).children('th:eq(18)').css("display","none"); 
					$(this).children('th:eq(17)').css("display","none"); 
					$(this).children('th:eq(16)').css("display","none"); 
					$(this).children('th:eq( 0)').css("display","none");					
				}else{ 
					$(this).children('td:eq(18)').css("display","none"); 
					$(this).children('td:eq(17)').css("display","none"); 
					$(this).children('td:eq(16)').css("display","none"); 
					$(this).children('td:eq( 0)').css("display","none");
				}
			});
		} */
		/***
		var o = $(html); 
		$(o).find("tr").each(function () { 
			$(this).children('th:eq(0)').css("display","none");
			$(this).children('td:eq(0)').css("display","none");
		});***/
		
        UE.getEditor('editor').setContent($(o)[0].outerHTML, true); 
         
    }
    
    function saveHtml(){
    	//$("#iframe的ID").contents().find("#iframe中的控件ID") 
    	
    	
    	
    	var html = $("iframe").contents().find("#coordTable").html();     	
    	
    	//console.log(html);
    	html = html.replaceAll("display:none!important","width: 0px")
    	opener.$("#coordTable").html(html) ; 
    	
    	opener.$("#coordTable").find("tr").each(function () { 
			$(this).children('th:eq(20)').remove();
			$(this).children('td:eq(20)').remove();
		}); 
		
    	return;
    	
    	 if (opener.$("#yntype").val()=="TPHJ1"){
    		opener.$("#coordTable").find(".YiJiBiaoTiTD").attr("colspan","19");
    	}   	
    	
    }
    
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }
</script>
</body>
</html>