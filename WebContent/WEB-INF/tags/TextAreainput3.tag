<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表文本输入框(垂直居中)" description="任免表文本输入框(垂直居中) 日期控件  css样式写在页面中" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="ondblclick" required="false" %> 
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="onkeypress" required="false" %>  
<%@ attribute name="onfocus" required="false" %>
<%@ attribute name="validator" required="false" %>
<%@ attribute name="isY" required="false" %>
<%@ attribute name="onpropertychange" required="false" %>  
<%@ attribute name="vtype" required="false" %>  
<%
String ctxpathemp = request.getContextPath();
%>
<div class="<%=cls%>" id="out_<%=property%>" style="overflow: hidden;display:block;">
 <div class="TAwrap <%=cls%>"  id="wrapdiv_<%=property%>">   
   <div class="TAsubwrap ">   
     <div class="TAcontent " id="div_<%=property%>" >
    	
     </div>   
   </div>   
</div>
</div>
<textArea name="<%=property%>" style="display:none; font-size:16px;" 
	class="<%=cls%>" id="<%=property%>" required="<%=required==null?"false":required%>" value="<%=defaultValue==null?"":defaultValue%>" label="<%=label%>"
	<%=readonly==null?"":"readonly='readonly'"%> ondblclick="<%=ondblclick==null?"":ondblclick%>" onkeypress="<%=onkeypress==null?"":onkeypress%>" onchange="<%=onchange==null?"":onchange%>" 
	onpropertychange="<%=onpropertychange==null?"":onpropertychange%>" 
	onfocus="<%=onfocus==null?"":onfocus%>" ></textArea>
	
<script type="text/javascript">
Ext.onReady(function(){
	var <%=property%> = new Ext.form.TextArea(
		{ 
		  id:'<%=property%>',
		  <%=validator==null?"":"validator:"+validator+","%>
		  <%=vtype==null?"":"vtype:"+"'"+vtype+"',"%>
		  applyTo:'<%=property%>'
		  
		});
		
//垂直居中事件   div 和 输入框切换
if(window.attachEvent){
     document.getElementById("<%=property%>").attachEvent('onblur',<%=property%>onblur );  
     
     document.getElementById("wrapdiv_<%=property%>").attachEvent('onclick',wrapdiv_<%=property%>onclick );            
 }
if(window.addEventListener){
 	document.getElementById("<%=property%>").addEventListener('blur', <%=property%>onblur,false);
 	 
 	document.getElementById("wrapdiv_<%=property%>").addEventListener('click',wrapdiv_<%=property%>onclick,false ); 	
 }		
 	 	
});



//显示div 隐藏输入控件
function <%=property%>onblur(){
	var wrapdiv = document.getElementById("wrapdiv_<%=property%>");
	document.getElementById("<%=property%>").style.display="none";
	//wrapdiv.style.display="table";
	document.getElementById("out_<%=property%>").style.display="block";
	
	var <%=property%>value = document.getElementById("<%=property%>").value;
	var datetext<%=property%> = $h.date(<%=property%>value,<%=isY%>);
	if(datetext<%=property%>!==true){
		$h.alert('系统提示：','<%=label%>:'+datetext<%=property%>,function(){wrapdiv_<%=property%>onclick();},250);
		return false;
	}
	if(<%=property%>value!=''&&<%=property%>value.length>4){
		<%=property%>value = <%=property%>value.substring(0,4)+'.'+<%=property%>value.substring(4,6);
	}
	
	document.getElementById("div_<%=property%>").innerHTML=<%=property%>value;
	ajust<%=property%>();
}
//隐藏div 显示输入控件
function wrapdiv_<%=property%>onclick(){
//必须先div隐藏 后输入框显示，否则表格会被撑开
	//document.getElementById("wrapdiv_<%=property%>").style.display="none";
	document.getElementById("out_<%=property%>").style.display="none";
	document.getElementById("<%=property%>").style.display="block";
	
	document.getElementById("<%=property%>").focus();
}


function ajust<%=property%>(){
	//调整top
	var wrapdiv = document.getElementById("wrapdiv_<%=property%>");
	var wrapdiv_h = wrapdiv.offsetHeight/2;
	document.getElementById('div_<%=property%>').style.top='-50%';
	var div_t = document.getElementById('div_<%=property%>').offsetTop-4;
	if(Ext.isIE){//alert(wrapdiv_h+','+div_t);
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