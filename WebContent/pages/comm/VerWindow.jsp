<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<style>
	#id1{
		padding-top:10px;
	}
</style>
<odin:groupBox title="系统版本信息">
	<odin:hidden property="v_name"  />
	<odin:hidden property="v_time" />
	<table id="id1">
		<tr>
			<div id="name" style="font-size: 18px;color: rgb(0,0,128);width: 300px; text-align: center;"></div>
		</tr>
		</br>
		<tr>
			<div id="time" style="font-size: 18px;color: rgb(0,0,128);width: 300px; text-align: center;"></div>
		</tr>
	</table>
</odin:groupBox>
