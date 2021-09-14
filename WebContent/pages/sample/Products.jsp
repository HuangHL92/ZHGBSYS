<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="tb1">
	<odin:fill></odin:fill>
	<odin:opLogButtonForToolBar></odin:opLogButtonForToolBar>
	<odin:buttonForToolBar text="测试自定义响应函数" id="testBtn"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="测试自定义响应函数2" id="testBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="保存" id="saveBtn" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel contentEl="productDiv" property="productPanel" topBarId="tb1"></odin:panel>
<div id="productDiv">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
    <tr>
    	<td colspan="6" height="6"></td>
    </tr>
    <tr>
    	<odin:textEdit property="name" label="产品名称" required="true"></odin:textEdit>
    	<odin:select property="ptype" label="产品类别" codeType="PTYPE"></odin:select>
    	<odin:numberEdit property="price" label="产品价格" decimalPrecision="2"></odin:numberEdit>
    </tr>
    <tr>
    	<odin:select property="status" label="状态" codeType="USEFUL" value="1"></odin:select>
    	<odin:dateEdit property="makedate" label="生产日期" format="Y-m-d"></odin:dateEdit>
    	<odin:numberEdit property="effectmonths" label="有效月数" maxlength="2"></odin:numberEdit>
    </tr>
    <tr>
    	<odin:textEdit property="description" label="描述" colspan="6" width="'80%'"></odin:textEdit>
    </tr>
    <tr>
		<td colspan="6" height="6"></td>
	</tr>	 
 </table>
</div>
<odin:toolBar property="pToolbar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="删除" id="isDelProducts" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:gridSelectColJs name="ptype" codeType="PTYPE"></odin:gridSelectColJs>
<odin:gridSelectColJs name="status" codeType="USEFUL"></odin:gridSelectColJs>
<odin:editgrid property="pgrid" bbarId="pageToolBar" sm="cell" topBarId="pToolbar" isFirstLoadData="false" url="/" title="产品列表" width="780" height="394">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="ptype"/>
  <odin:gridDataCol name="price"/>
  <odin:gridDataCol name="makedate"/>
  <odin:gridDataCol name="effectmonths"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="description"/>
  <odin:gridDataCol name="check" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="selectall"   gridName="pgrid" dataIndex="check" width="40" editor="checkbox"/>
  <odin:gridEditColumn header="产品名称" width="80" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn header="产品类别" width="80" dataIndex="ptype" editor="select" codeType="PTYPE" edited="false"/>
  <odin:gridEditColumn header="状态" width="80" editor="text" dataIndex="status" editor="select" codeType="USEFUL" edited="false"/>
  <odin:gridEditColumn header="生产日期" width="80" dataIndex="makedate" editor="text" renderer="odin.Ajax.formatDate" edited="false"/>
  <odin:gridEditColumn  header="产品描述" width="160" dataIndex="description" editor="text" edited="false"/>
  <odin:gridEditColumn header="操作" width="80" dataIndex="id" editor="text" renderer="productOp" edited="false" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
<odin:window src="blank.htm" id="productWin" title="产品信息窗口" width="400" height="260" modal="true"></odin:window>
<script>
function productOp(value){
	return "<a href='javascript:isDelete(\""+value+"\")'>删除</a> | <a href='javascript:radow.doEvent(\"isDelete\",\""+value+"\")'>删除2</a> | <a href='javascript:radow.doEvent(\"openUpdateWin\",\""+value+"\")'>修改</a>"
}
function isDelete(id){
	odin.confirm("您确定要删除该笔信息吗？",function(btn){
		if(btn == "ok"){
			radow.doEvent("deleteProduct",id);
		}
	});
}
function selfResFunc(res){
	alert(odin.encode(res));
}
</script>