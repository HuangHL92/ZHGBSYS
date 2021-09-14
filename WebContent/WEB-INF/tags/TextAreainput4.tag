<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表文本输入框(垂直居中)" description="任免表文本输入框(垂直居中) css样式写在页面中" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="ondblclick" required="false" %> 
<%
String ctxpathemp = request.getContextPath();
%>
<div class="<%=cls%>" id="out_<%=property%>" ondblclick="<%=ondblclick==null?"":ondblclick%>" style="overflow: hidden;display:block;">
 <div class="TAwrap <%=cls%>"  id="wrapdiv_<%=property%>">   
   <div class="TAsubwrap ">   
     <div class="TAcontent " id="div_<%=property%>" >
    	<%=defaultValue%>
     </div>   
   </div>   
</div>
</div>
	
<script type="text/javascript">
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
		wrapdiv_h = wrapdiv.offsetHeight;
		//alert(wrapdiv_h);alert(document.getElementById("out_<%=property%>").offsetHeight);
		if(document.getElementById("out_<%=property%>").offsetHeight<wrapdiv_h){
			wrapdiv.style.marginTop=4;
		}else{
			wrapdiv.style.marginTop=0;
		}
		
	}
}




</script>