<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar2">
	<odin:fill></odin:fill>
	<ss:resetBtn></ss:resetBtn>
	<ss:doSaveBtn/>
</ss:toolBar>
<ss:editgrid property="div_2" pageSize="150" topBarId="bar2"   isFirstLoadData="false" url="/" title="����������Ϣ"  height="400" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="30" name="checked" editor="checkbox"  p="E" />
			<ss:gridCol header="����id" name="ruleid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="�����ϵid" name="id" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="��������" editor="select" name="auditname" width="230" p="E"></ss:gridCol>
			<ss:gridCol header="�������" editor="text" name="auditid" width="180" p="E" maxLength="200"></ss:gridCol>			
		</ss:gridColModel>
</ss:editgrid>


<script type="text/javascript">
function preflush(Aeras,currow){
	window.parent.setAeras(Aeras,currow);
	var win = parent.Ext.getCmp('win_pup');
    if(win){
    	win.hide();
    } 
} 
</script>

