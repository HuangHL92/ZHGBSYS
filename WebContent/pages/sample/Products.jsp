<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="tb1">
	<odin:fill></odin:fill>
	<odin:opLogButtonForToolBar></odin:opLogButtonForToolBar>
	<odin:buttonForToolBar text="�����Զ�����Ӧ����" id="testBtn"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="�����Զ�����Ӧ����2" id="testBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����" id="saveBtn" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel contentEl="productDiv" property="productPanel" topBarId="tb1"></odin:panel>
<div id="productDiv">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
    <tr>
    	<td colspan="6" height="6"></td>
    </tr>
    <tr>
    	<odin:textEdit property="name" label="��Ʒ����" required="true"></odin:textEdit>
    	<odin:select property="ptype" label="��Ʒ���" codeType="PTYPE"></odin:select>
    	<odin:numberEdit property="price" label="��Ʒ�۸�" decimalPrecision="2"></odin:numberEdit>
    </tr>
    <tr>
    	<odin:select property="status" label="״̬" codeType="USEFUL" value="1"></odin:select>
    	<odin:dateEdit property="makedate" label="��������" format="Y-m-d"></odin:dateEdit>
    	<odin:numberEdit property="effectmonths" label="��Ч����" maxlength="2"></odin:numberEdit>
    </tr>
    <tr>
    	<odin:textEdit property="description" label="����" colspan="6" width="'80%'"></odin:textEdit>
    </tr>
    <tr>
		<td colspan="6" height="6"></td>
	</tr>	 
 </table>
</div>
<odin:toolBar property="pToolbar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="ɾ��" id="isDelProducts" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:gridSelectColJs name="ptype" codeType="PTYPE"></odin:gridSelectColJs>
<odin:gridSelectColJs name="status" codeType="USEFUL"></odin:gridSelectColJs>
<odin:editgrid property="pgrid" bbarId="pageToolBar" sm="cell" topBarId="pToolbar" isFirstLoadData="false" url="/" title="��Ʒ�б�" width="780" height="394">
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
  <odin:gridEditColumn header="��Ʒ����" width="80" dataIndex="name" editor="text" edited="false"/>
  <odin:gridEditColumn header="��Ʒ���" width="80" dataIndex="ptype" editor="select" codeType="PTYPE" edited="false"/>
  <odin:gridEditColumn header="״̬" width="80" editor="text" dataIndex="status" editor="select" codeType="USEFUL" edited="false"/>
  <odin:gridEditColumn header="��������" width="80" dataIndex="makedate" editor="text" renderer="odin.Ajax.formatDate" edited="false"/>
  <odin:gridEditColumn  header="��Ʒ����" width="160" dataIndex="description" editor="text" edited="false"/>
  <odin:gridEditColumn header="����" width="80" dataIndex="id" editor="text" renderer="productOp" edited="false" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
<odin:window src="blank.htm" id="productWin" title="��Ʒ��Ϣ����" width="400" height="260" modal="true"></odin:window>
<script>
function productOp(value){
	return "<a href='javascript:isDelete(\""+value+"\")'>ɾ��</a> | <a href='javascript:radow.doEvent(\"isDelete\",\""+value+"\")'>ɾ��2</a> | <a href='javascript:radow.doEvent(\"openUpdateWin\",\""+value+"\")'>�޸�</a>"
}
function isDelete(id){
	odin.confirm("��ȷ��Ҫɾ���ñ���Ϣ��",function(btn){
		if(btn == "ok"){
			radow.doEvent("deleteProduct",id);
		}
	});
}
function selfResFunc(res){
	alert(odin.encode(res));
}
</script>