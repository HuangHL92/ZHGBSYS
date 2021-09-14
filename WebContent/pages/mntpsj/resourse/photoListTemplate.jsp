<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<link rel="stylesheet" type="text/css" href="pages/mntpsj/resourse/photolist.css"> 
<script src="pages/mntpsj/resourse/photolist.js"></script>
<style>
form {
    margin: 0px;
}
</style>
<div class="gbtj-content">
</div>

<div class="gbtj-content1" style="display: none;">
	<div class="gbtj-list" onclick="Photo_List.changckbox(this)">
		<div class="a01-content">
			<p class="pa0101"><input name="a01a0000" type="checkbox"   class="ckbox" style="display: none;" value="" /><span class="a0101" ></span><span class="a0104" ></span></p>
			<p><span class="a0107" ></span></p>
			<p><span class="a0141" ></span></p>
			<p><span class="a0134" ></span></p>
			<p><span class="xlxw" ></span></p>
			<div  class="diva0192a"  style="width: 100%;height: 40px;display: table-cell;vertical-align: middle;overflow: hidden;">
				<p class="pa0192a" ><span class="a0192a" title="" ></span></p>
			</div>
			<div class="gb-title" >
				<img class="gb-title-img" alt="" src="">
			</div>
		</div>
	</div>
</div>


<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>

<script type="text/javascript">
var myMask;
Ext.onReady(function() {
	myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
	//Photo_List.doResize()
});
</script>