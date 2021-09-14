<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar2">
	<odin:fill></odin:fill>
	<ss:resetBtn></ss:resetBtn>
	<ss:doSaveBtn/>
</ss:toolBar>
<ss:editgrid property="div_2" pageSize="150" topBarId="bar2"   isFirstLoadData="false" url="/" title="规则区域信息"  height="400" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="selectall" width="30" name="checked" editor="checkbox"  p="E" />
			<ss:gridCol header="规则id" name="ruleid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="区域关系id" name="id" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="区域名称" editor="select" name="auditname" width="230" p="E"></ss:gridCol>
			<ss:gridCol header="区域编码" editor="text" name="auditid" width="180" p="E" maxLength="200"></ss:gridCol>			
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

