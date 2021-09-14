<%@page contentType="text/html; charset=GBK" %>
<style>
#progressbar{
		position:absolute;
		left:45%;
		top:40%;
		border:3px solid #B2D0F7;
		/*background:white url(<%=request.getContextPath()%>/images/block-bg.gif) repeat-x;*/
		padding:10px;
		font:bold 14px verdana,tahoma,helvetica; 
		color:#003366;
		width:300px;
		text-align:center;
		z-index:9999;
	}
	#progressbar .border{
		border: 1px solid #777;
		width: 276px;
		height: 13px;
		padding: 1px;
	}
	#progressbar .bar{
		float:left;
		background-color: #73c944;
		width: 50%;
		height: 13px;
		overflow: hidden;
	}
	#progressbar .desc{
		text-align: center;
		font-size: 12px;
		line-height: 24px;
	}
</style>
<div id="progressbar">
	<div class="border">
		<div class="bar">&#160;</div>
	</div>
	<div class="desc">
		正在加载...
	</div>
</div>

<script type="text/javascript">
<!--
	var loading = function(){
		var ct = document.getElementById("progressbar"),
			desc = ct.getElementsByTagName("div"),
			idx = 0, time = 500, bar = desc[1], desc = desc[2];
		bar.setValue = function(n){
			this.style.width = n + "%"; };
		void function(){
			bar.setValue(idx += (100 - idx) * .2);
			timer = setTimeout(arguments.callee, time += 100);
		}();
		return {
			remove: function(){
				clearTimeout(timer);
				desc.innerHTML = "加载完成";
				bar.setValue(100);
				setTimeout(function(){
					ct.parentNode.removeChild(ct);
				}, 500);
			}
		};
	}();
	//setTimeout(loading.remove, 10000);
//-->
</script>