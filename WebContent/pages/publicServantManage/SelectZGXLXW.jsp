<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
#zzzgxl,#zzzgxw{position: relative;top:-7px;}
#qrzzgxl,#qrzzgxw{position: relative;top:3px;}
body{background:#DFE8F6}
#tool{width:578px}
</style>
<div id="tool">
	<odin:toolBar property="toolbar" applyTo="tool">
		<odin:fill/>
		<odin:buttonForToolBar id="save"  text="ȷ��"  isLast="true" ></odin:buttonForToolBar>
	</odin:toolBar>
</div>
<div >
	<table>
		<tr style="width: 10px"></tr>
		<tr>
							<td rowspan="4"><input type="checkbox" id="qrzzgxl" onclick="xuelichange()"/><br/><input type="checkbox" id="qrzzgxw" onclick="xueweichange()"/><br/><br/><input type="checkbox" id="zzzgxl" onclick="xuelichange2()"/><br/><input type="checkbox" id="zzzgxw" onclick="xueweichange2()"/></td>
							<td rowspan="2" >ȫ����<br/>��&nbsp;&nbsp;��</td>
							<td  colspan="2">
								<input id="qrzxl" name="qrzxl"  label="ȫ���ƽ�����ѧ��"  readonly="readonly"    style="width:190px;line-height: 18px">
							
							</td>
							<td  rowspan="2">��ҵԺУ<br/>ϵ��רҵ</td>
							<td  colspan="2">
								<input id="qrzxlxx" name="qrzxlxx"  label="ԺУϵ��רҵ(ѧ��)" required="false" readonly="readonly"   style="width:200px;text-align:left;line-height: 18px"">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input id="qrzxw" name="qrzxw"   label="ȫ���ƽ�����ѧλ" required="false" readonly="readonly"   style="width:190px;line-height: 18px"">
							</td>
							<td  colspan="2">
								<input id="qrzxwxx" name="qrzxwxx"  label="ԺУϵ��רҵ(ѧλ)" required="false" readonly="readonly"   style="width:200px;text-align:left;line-height: 18px"">
							</td>
						</tr>
						<tr>
							<td  rowspan="2">��&nbsp;&nbsp;ְ<br/>��&nbsp;&nbsp;��</td>
							<td  colspan="2">
								<input id="zzxl" name="zzxl"   label="��ְ������ѧ��" required="false" readonly="readonly"   style="width:190px;line-height: 18px"">
							</td>
							<td  rowspan="2">��ҵԺУ<br/>ϵ��רҵ</td>
							<td  colspan="2">
								<input id="zzxlxx" name="zzxlxx"  label="ԺУϵ��רҵ(ѧ��)" required="false" readonly="readonly"   style="width:200px;text-align:left;line-height: 18px"">
							</td>
						</tr>
						<tr>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxw" name="zzxw"   label="��ְ������ѧλ" required="false" label="��ְ������ѧ��" readonly="readonly"  style="width:190px;line-height: 18px"">
							</td>
							<td class="label-clor height-30" colspan="2">
								<input id="zzxwxx" name="zzxwxx"  label="ԺУϵ��רҵ(ѧλ)" required="false" readonly="readonly"  style="width:200px;text-align:left;line-height: 18px"">
							</td>
						</tr>
	</table>
</div>

<script type="text/javascript">
	function xuelichange(){
		var qrzzgxl=document.getElementById('qrzzgxl');
		var zzzgxl=document.getElementById('zzzgxl');	
		if(qrzzgxl.checked==true){
			zzzgxl.checked=false;
		}
		
	}
	function xuelichange2(){
		var qrzzgxl=document.getElementById('qrzzgxl');
		var zzzgxl=document.getElementById('zzzgxl');
		if(zzzgxl.checked==true){
			qrzzgxl.checked=false;
		}
	}
	function xueweichange(){
		var qrzzgxw=document.getElementById('qrzzgxw');
		var zzzgxw=document.getElementById('zzzgxw');
		if(qrzzgxw.checked==true){
			zzzgxw.checked=false;
		}
		
	}
	function xueweichange2(){
		var qrzzgxw=document.getElementById('qrzzgxw');
		var zzzgxw=document.getElementById('zzzgxw');
		if(zzzgxw.checked==true){
			qrzzgxw.checked=false;
		}
	}
	/* Ext.onReady(function(){
		var win=parent.Ext.getCmp("selectzgxlxw");
		win.closable=false;
	}); */
	window.onbeforeunload=function(){
		radow.doEvent("close");
	}


	
</script>
