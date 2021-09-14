<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<ss:editgrid property="div_2"  title="审核明细信息" isFirstLoadData="false" url="/" height="400" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="当前审核级" name="aulevel" editor="select" width="100" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="当前审核标志" name="auflag" editor="select" width="120" codeType="SHBZ"></ss:gridCol>
			<ss:gridCol header="审核人信息" name="usersinfo" width="200" editor="text"/>
			<ss:gridCol header="审核人" name="aae011" editor="text" width="100"></ss:gridCol>
			<ss:gridCol header="审核意见" name="aae013" editor="text" width="200"></ss:gridCol>
			<ss:gridCol header="审核时间" editor="date" name="aae036" width="180"></ss:gridCol>
			<ss:gridCol header="级别id" name="levelid" width="300" editor="number" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
 