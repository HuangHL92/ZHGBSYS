<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/farbtastic.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<style type="text/css" media="screen">
.colorwell{border:2px solid #fff;width:6em;text-align:center;cursor:default;}
body.colorwell-selected{
	border:2px solid #000;
	font-weight:bold;
}
.farbtastic {
  position: relative;
}
.farbtastic * {
  position: absolute;
  cursor: crosshair;
}
.farbtastic, .farbtastic .wheel {
  width: 195px;
  height: 195px;
}
.farbtastic .color, .farbtastic .overlay {
  top: 47px;
  left: 47px;
  width: 101px;
  height: 101px;
}
.farbtastic .wheel {
  background: url(image/wheel.png) no-repeat;
  width: 195px;
  height: 195px;
}
.farbtastic .overlay {
  background: url(image/mask.png) no-repeat;
}
.farbtastic .marker {
  width: 17px;
  height: 17px;
  margin: -8px 0 0 -8px;
  overflow: hidden; 
  background: url(image/marker.png) no-repeat;
}
</style>

<script type="text/javascript" charset="utf-8">
$(document).ready(function(){
	var f = $.farbtastic('#picker');
	var selected;
	$('.colorwell').each(function(){
		f.linkTo(this);
		$(this).css('opacity',0.75);
	}).focus(function(){
		if(selected){
			$(selected).css('opacity', 0.75).removeClass('colorwell-selected');
		}
		f.linkTo(this);
		$(selected = this).css('opacity', 1).addClass('colorwell-selected');
	});
});
</script>

<div id="picker" style="float:right;"></div>

<div class="form-item">
	<label for="color">颜色</label>
	<input type="text" id="color" name="color" class="colorwell colorwell-selected" readonly="readonly" value="#123456" /><br/>
	<label for="bold" >加粗</label>
	<input align="middle" type="checkbox" name="bold" id="bold" />
	<odin:button text="应用" property="save"></odin:button>
</div>



