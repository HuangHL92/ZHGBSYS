<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="公共组件查询" description="公共组件查询（任免表）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="true" %>
<%@ attribute name="codetype" required="true" %>
<%@ attribute name="codename" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="ondblclick" required="false" %> 
<%@ attribute name="onkeypress" required="false" %> 
<%@ attribute name="onchange" required="false" %> 
<%@ attribute name="maxlength" required="false" %> 
<%
String ctxPath = request.getContextPath(); 
required = required==null?"false":required;
defaultValue = defaultValue==null?"":defaultValue;

boolean readonly2 = readonly==null?false:Boolean.valueOf(readonly);
onkeypress = readonly2?("openDiseaseInfoCommonQuery"+property+"();"):"";
ondblclick = "openDiseaseInfoCommonQuery"+property+"();";
String onclick = "openDiseaseInfoCommonQuery"+property;
%>
<script type="text/javascript">
function openDiseaseInfoCommonQuery<%=property%>(){
	var _e = window.event; 
	if(!_e){
		if(arguments.callee.caller){
			_e = arguments.callee.caller.arguments[0]; 
		}
	}
	if (_e&&_e.keyCode == 8) { return false;}
	var codetype = "<%=codetype%>";
    var codename = "<%=codename==null?"code_name":codename%>";
    var codevalueparameter ="none";
    if(document.getElementById('codevalueparameter')){
    	<%="ZB09".equals(codetype)?"codevalueparameter = document.getElementById('codevalueparameter').value;":""%>
    }
    var winId = "winId"+Math.round(Math.random()*10000);
    var url="pages.sysorg.org.PublicWindow&aa="+Math.random()+"&property=<%=property %>&codetype=<%=codetype %>&closewin="+winId+"&codename="+codename+"&subWinId="+winId;
   	//alert(url);
   	if(codevalueparameter!="none"){
   		url=url+"&codevalueparameter="+codevalueparameter;
   	}
   	var label='<%=label %>';
   	$h.openPageModeWin(winId,url,label,270,385,'','<%=ctxPath%>');
}
function returnwin<%=property%>(rs){
	if(rs!=null){
		var rss = rs.split(",");
		document.getElementById('comboxArea_<%=property%>').value = rss[1];
		document.getElementById('<%=property%>').value=rss[0];
		var record = {data:{value:rss[1],key:rss[0]}};
		<%=onchange==null?"":onchange+"(record);" %>
		<%=property%>onblur();
	}
}

</script>
<div class="<%=cls%>" id="out_<%=property%>" style="overflow: hidden;display:block;">
 <div class="TAwrap <%=cls%>"  id="wrapdiv_<%=property%>">   
   <div class="TAsubwrap ">   
     <div class="TAcontent " id="div_<%=property%>" >
    	
     </div>   
   </div>   
</div>
</div>
<odin:hidden property="codevalueparameter" />
<odin:hidden property='<%=property %>' />
<textArea name="comboxArea_<%=property%>" 
	class="<%=cls%>" id="comboxArea_<%=property%>" required="<%=required==null?"false":required%>" value="<%=defaultValue==null?"":defaultValue%>" label="<%=label%>"
	<%=readonly==null?"":"readonly='readonly'"%> ondblclick="openDiseaseInfoCommonQuery<%=property%>()" onkeypress="<%=onkeypress==null?"":onkeypress%>"></textArea>
<%-- <img class="x-form-trigger x-form-arrow-trigger " id="<%=property %>_img" onclick="openDiseaseInfoCommonQuery<%=property%>()" style="position: absolute;top: 0px;display:block;right:0px;cursor:pointer;width:17px;height:21px;background-image: url('<%=ctxPath%>/basejs/ext/resources/images/default/form/search-trigger.gif');" /> --%>
<img class="x-form-trigger x-form-arrow-trigger " id="<%=property %>_img" onclick="openDiseaseInfoCommonQuery<%=property%>()" style="position: absolute;top: 0px;display:block;right:0px;cursor:pointer;width:17px;height:21px;" />
<script type="text/javascript">
	Ext.onReady(function() {
			var <%=property%> = new Ext.form.TextArea({
						allowBlank : !<%=required==null?"false":required%>,
						width : 160,
						id : '<%=property%>',
						inputType : 'text',
						applyTo : '<%=property%>'
					});
		

	//垂直居中事件   div 和 输入框切换
	if(window.attachEvent){
	     document.getElementById("comboxArea_<%=property%>").attachEvent('onblur',<%=property%>onblur );  
	     
	     document.getElementById("wrapdiv_<%=property%>").attachEvent('onclick',wrapdiv_<%=property%>onclick );            
	}	
	if(window.addEventListener){	
 	 document.getElementById("comboxArea_<%=property%>").addEventListener('blur', <%=property%>onblur,false);
 	 
 	 document.getElementById("wrapdiv_<%=property%>").addEventListener('click',wrapdiv_<%=property%>onclick,false );
 	} 		
});



//显示div 隐藏输入控件
function <%=property%>onblur(){
	var wrapdiv = document.getElementById("wrapdiv_<%=property%>");
	document.getElementById("comboxArea_<%=property%>").style.display="none";
	//wrapdiv.style.display="table";
	document.getElementById("out_<%=property%>").style.display="block";
	var objCodeValue = document.getElementById("<%=property%>").value;
	var objValue = document.getElementById("comboxArea_<%=property%>").value;
	
	document.getElementById("div_<%=property%>").innerHTML=objValue;
	
	var imgHidden = function(){
		document.getElementById("<%=property %>_img").style.display="none";
	};
	var imgShow = function(){
		<%-- document.getElementById("<%=property %>_img").style.display="block"; --%>
	}

	//img.onmouseout=imgHidden;
	//img.onmouseover=imgShow;
	document.getElementById("<%=property %>_img").parentNode.onmouseout=imgHidden;
	document.getElementById("<%=property %>_img").parentNode.onmouseover=imgShow;
	ajust<%=property%>();
}
//隐藏div 显示输入控件
function wrapdiv_<%=property%>onclick(){
//必须先div隐藏 后输入框显示，否则表格会被撑开
	//document.getElementById("wrapdiv_<%=property%>").style.display="none";
	document.getElementById("out_<%=property%>").style.display="none";
	document.getElementById("comboxArea_<%=property%>").style.display="block";
	
	document.getElementById("comboxArea_<%=property%>").focus();
	
}


function ajust<%=property%>(){
	//调整top
	var wrapdiv = document.getElementById("wrapdiv_<%=property%>");
	var wrapdiv_h = wrapdiv.offsetHeight/2;
	document.getElementById('div_<%=property%>').style.top='-50%';
	var div_t = document.getElementById('div_<%=property%>').offsetTop-4;
	if(Ext.isIE){
		if((wrapdiv_h+div_t)<=0){
			document.getElementById('div_<%=property%>').style.top=-wrapdiv_h+4;
		}else{
			document.getElementById('div_<%=property%>').style.top='-50%';
		}
	}else{
		var wrapdiv = document.getElementById("wrapdiv_<%=property%>");
		var wrapdiv_h = wrapdiv.offsetHeight;
		//alert(wrapdiv_h);alert(document.getElementById("out_<%=property%>").offsetHeight);
		if(document.getElementById("out_<%=property%>").offsetHeight<wrapdiv_h){
			wrapdiv.style.marginTop=4;
		}else{
			wrapdiv.style.marginTop=0;
		}
		
	}
}

</script>

