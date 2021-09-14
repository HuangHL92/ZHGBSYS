<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<style>

</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>

<odin:hidden property="sp0102" />
<!-- <table style="height:50px;width:100%">
	<tr>
		<td>
			<textarea  class="x-form-text x-form-field" style="width: 300px;height:25px;  margin-top:1px;" name="queryName" id="queryName"  ></textarea> 
			
		</td>
		<td> <input type="button" style="position: relative;top: 2px;" onclick="toDOQuery()" value="搜索"></td>
	</tr>
</table> -->
<odin:editgrid property="gridcq" title="待选列表"  width="300" height="450" pageSize="9999"
								autoFill="false" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="personcheck" />	
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="a0101" />
		<odin:gridDataCol name="a0104" />
		<odin:gridDataCol name="a0163" />
		<odin:gridDataCol name="a0192a" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn2 header="selectall" width="40"
			editor="checkbox" dataIndex="personcheck" edited="true"
			hideable="false" gridName="persongrid" />
		<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
		<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
		<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="100"  align="center" />
		<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	        data:[]
	    }
	</odin:gridJsonData>
</odin:editgrid>


<script type="text/javascript">

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.type == "textarea") {
			toDOQuery();
			return false;
		}
	}else if(event.keyCode == 27){	//禁用ESC
	        return false;   
	}
}



Ext.onReady(function() {
	
	if(!!parent.Ext.getCmp(subWinId).initialConfig.sp0102){
		document.getElementById("sp0102").value=parent.Ext.getCmp(subWinId).initialConfig.sp0102;
	}
	
	 
	var gridcq = Ext.getCmp("gridcq");
	var gStore = gridcq.getStore();
	gridcq.on("rowdblclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		parent.setInfo(rowData.data.a0000);
		parent.Ext.getCmp(subWinId).close();
	});
	
	
});


	//点击查询
	function toDOQuery(){
		radow.doEvent('queryFromData');
	}
	
</script>


