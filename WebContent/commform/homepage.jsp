<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<link href="commform/css/index_wssb.css" rel="stylesheet" type="text/css" />
<link href="commform/css/homepage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="commform/basejs/homepage.js"></script>

<style type="text/css">
#svc-toolbar .bgp-fr1{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr2{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -37px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr3{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -74px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr4{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -111px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr5{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -148px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr6{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -185px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
#svc-toolbar .bgp-fr7{width:52px;height:37px;margin-bottom:.5em;background: transparent url(commform/img/function_bady.gif) 0 -222px no-repeat}
.ttv{background: transparent url(commform/img/function_bady.gif) 0 0 no-repeat}
</style>
<script type="text/javascript">
	var aaa=r;
	aaa.svcToolbarYSpritePosition={a1:"0",a2:"-37px",a3:"-74px",a4:"-111px",a5:"-148px"};
	window.onload=aaa.init;
	
	function addTab(atitle, aid, src){
		top.showDiv("content");
		top.addTab(atitle, aid, src);
	};
	
</script>
</head>
<body style="text-align:center;" > 
<div id="wrapper">
<table id="svc-toolbar" class="bgp" cellpadding="0" cellspacing="" border="0" width="380">
<tr>
	<td nowrap><a id="a1-i" href="javascript:addTab('人员新增','402880e51b0155ca011b016bb9f20005',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin');" title="人员新增"><span class="bgp-fr1"></span><span>人员新增</span></a></td>
	<td nowrap><a id="a2-i" href="javascript:addTab('人员续保','2881a0071c1b5c42011c1b70dbb30004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin-1');" title="人员续保"><span class="bgp-fr2"></span><span>人员续保</span></a></td>
	<td nowrap><a id="a3-i" href="javascript:addTab('人员中断','2881a0071c02094e011c02c3801c0004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.PauseJoin');" title="人员中断"><span class="bgp-fr3"></span><span>人员中断</span></a></td>
	<td nowrap><a id="a4-i" href="javascript:addTab('基数申报录入','2881a0071f841284011f841894500026',top.contextPath+'/pages/commAction.do?method=wssb.psrewage.JsInput');" title="基数申报录入"><span class="bgp-fr4"></span><span>基数申报录入</span></a></td>
</tr>
</table>
</div>
<div id="tt" class="ttm" style="display:none">
<div class="ttl"></div>
<div class="ttc">
	<div class="ttdc">
		<div class="ttdl"></div>
		<div class="ttdr"></div>
	</div>
	<div class="tt-text"></div>
	<div class="ttdc">
		<div class="ttdl"></div>
		<div class="ttdr"></div>
	</div>
</div>
<div class="ttl"></div>
<div class="ttvc">
	<div class="ttv"></div>
</div>
</div>
</body>
</html>