<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<style>
	#bg {
		width: 100%;
		height: 100%;
		background-color: #000;
		position: absolute;
		top: 0;
		left: 0;
		z-index: 2;
		opacity: 0;
		/*兼容IE8及以下版本浏览器*/
		filter: alpha(opacity=0);
		display: none;
	}
</style>

<script>
	function lockHTML(){
		var s = document.getElementById("bg");
	    s.style.display = "block";
	}
</script>

<div id="bg"></div>