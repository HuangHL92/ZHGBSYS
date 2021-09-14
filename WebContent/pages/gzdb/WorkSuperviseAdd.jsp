<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>

<style>
 #perInfo input{
	border: 1px solid #c0d1e3 !important;
}
 .vueBtn {
        display: inline-block;
        padding: .3em .5em;
        background-color: #6495ED;
        border: 1px solid rgba(0, 0, 0, .2);
        border-radius: .3em;
        box-shadow: 0 1px white inset;
        text-align: center;
        text-shadow: 0 1px 1px black;
        color: white;
        font-weight: bold;
        cursor: pointer;
    }
</style>

<div id="perInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		   <tr>
		   <odin:textarea property="wcqk"   label="完成情况"  cols="70" rows="8" maxlength="550"></odin:textarea>
		  </tr>
		  <tr>
		  	<odin:textarea property="bz"   label="备注" cols="70" rows="1" maxlength="550"></odin:textarea>
		  </tr>
		  <tr>
			<odin:select2 property="wcqkbj"  value=""  label="完成标记"  data="['1','完成'],['2','长期坚持'],['3','正在整改']"></odin:select2> 
		  </tr>
		</table>
		<odin:hidden property="rwwcqkid"/>
		<odin:hidden property="gzdbid"/>
		<%-- <div style="margin-left: 100px;margin-top: 15px;">
			<odin:button text="保存" property="savePerInfo" handler="savePerInfo" />
		</div> --%>
	</div>
</div>
<div id="bottomDiv" style=" width: 100%; position: absolute;top: 300px;">
<table style="width: 100%;">
<tr align="center">
<td>
<table>
<td><div onclick="save()" class="vueBtn">保 存</div>
</td>
<tr>
</table>
</td>
</tr>
</table>
</div>
<odin:hidden property="gzdbid"/>
<odin:hidden property="rwwcqkid"/>
<odin:hidden property="wcqk"/>
<odin:hidden property="wcqkbj"/>
<odin:hidden property="bz"/>
<script>
Ext.onReady(function () {
	/* $("#operation").val(GetQueryString("operation"));
    var operation=$("#operation").val();
    alert(operation);
    alert(rwwcqkid);
    $("#rwwcqkid").val(GetQueryString("rwwcqkid"));
	 radow.doEvent("initPage"); */
})
function save(){
     radow.doEvent("save");
 } 
function success(){
	/* if (typeof(parent.GridReload)!="undefined"){
		parent.GridReload();
	} */
	realParent.radow.doEvent('grid2.dogridquery');
	// window.close();
}
function closeSelfWin() {
	//var aa= Ext.getCmp("personBorrow1");
	//window.close();
   	 parent.Ext.getCmp("WorkSuperviseAdd").close();
}

</script>