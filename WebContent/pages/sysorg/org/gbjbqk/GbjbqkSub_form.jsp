<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div style="height:3px"></div>
<div id="createtable" style="overflow-y:hidden;overflow-x:hi;overflow: auto;margin-left:15px;display:none;">
	<table style="width:100%;" border="0">
		<col width="5%">
		<col width="20%">
		<col width="10%">
		<col width="65%">
		<tr >
		</tr>
		<tr>
			<odin:select property="zwlb" label="职务类别" codeType="zwcelb" multiSelectWithAll="true" onchange="zwlb_func()"></odin:select>
			<td>
				<odin:checkbox property="xianyin" label="隐藏全零行" value="1"></odin:checkbox>
			</td>
			<td>
				<odin:checkbox property="yczb" label="隐藏占比" value="1" ></odin:checkbox>
			</td>
		</tr>
	</table>
	<table style="width:100%;" border="0">
	
		<tr>
			<td colspan="4">
				<div>
					<object id="DCellWeb1" style="left: 0px; width: 1250px; top: 0px; height: 400px" 
				    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
				    codebase="softs/cellweb5.cab#version=5,3,8,0429">
				    <!-- #version=5.3.9.16 -->
				    <param name="_Version" value="65536" />
				    <param name="_ExtentX" value="10266" />
				    <param name="_ExtentY" value="7011" />
				    <param name="_StockProps" value="0" />
				    </object>
				</div>
			</td>
		</tr>
	</table>
</div>