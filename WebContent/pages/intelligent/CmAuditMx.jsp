<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<ss:editgrid property="div_2"  title="�����ϸ��Ϣ" isFirstLoadData="false" url="/" height="400" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="��ǰ��˼�" name="aulevel" editor="select" width="100" codeType="SHJS"></ss:gridCol>
			<ss:gridCol header="��ǰ��˱�־" name="auflag" editor="select" width="120" codeType="SHBZ"></ss:gridCol>
			<ss:gridCol header="�������Ϣ" name="usersinfo" width="200" editor="text"/>
			<ss:gridCol header="�����" name="aae011" editor="text" width="100"></ss:gridCol>
			<ss:gridCol header="������" name="aae013" editor="text" width="200"></ss:gridCol>
			<ss:gridCol header="���ʱ��" editor="date" name="aae036" width="180"></ss:gridCol>
			<ss:gridCol header="����id" name="levelid" width="300" editor="number" p="H"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
 