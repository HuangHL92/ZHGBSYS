<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="�����б�Excel" id="exportExcel" handler="exportExcel" icon="images/icon/exp.png" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" style="width:960px;"></div>
<div style="width:100%;">
		<div id="clear_search" style="width:60%">
			<table style="width:100%;" >
				<tr align="left">
					<odin:textEdit property="a0101" label="����"/> 
					<odin:textEdit property="a0184" label="���֤����"/> 
					<td align="right">
						<odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;ѯ&nbsp;&nbsp;" property="query"
							></odin:button>
					</td>
				</tr>
			</table>
		</div>
	<odin:editgrid  property="repeatInfogrid" height="425" width="700" title="" autoFill="true" bbarId="pageToolBar" pageSize="20"  url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="a0101" />
		<odin:gridDataCol name="a0184" />
		<odin:gridDataCol name="a0104" />
		<odin:gridDataCol name="a0117" />
		<odin:gridDataCol name="a0195" />
		<odin:gridDataCol name="remove" />
		<odin:gridDataCol name="a0192a" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn2 dataIndex="a0101" width="60" header="��  ��"
				align="center" editor="text" edited="false"/>
			<odin:gridEditColumn2 header="���֤�� " edited="false" width="90"
				align="center" dataIndex="a0184" editor="text" />
			<odin:gridEditColumn2 dataIndex="a0104" width="60" header="�Ա�"
				align="center" editor="select" edited="false" codeType="GB2261" />
			<odin:gridEditColumn2 dataIndex="a0117" width="60" header="����"
				align="center" editor="select" edited="false" codeType="GB3304" />
			<odin:gridEditColumn2 dataIndex="a0195" width="110" header="ͳ�ƹ�ϵ���ڵ�λ"
				align="center" editor="text" edited="false"/>	
			<odin:gridEditColumn2 dataIndex="a0192a" width="110" header="������λ��ְ��"
				align="center" editor="text" edited="false"/>	
			<odin:gridEditColumn align="center" width="50" header="����"
				dataIndex="remove" editor="text" edited="false" renderer="removePerson" />
			<odin:gridEditColumn2 dataIndex="a0000" width="100" header="��Աid"
				hideable="false" editor="text" align="center" isLast="true"
				hidden="true" />
	</odin:gridColumnModel>
	</odin:editgrid>
</div>
<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("repeatInfogrid").setHeight(height-height_top-clear_search_height);
	Ext.getCmp("repeatInfogrid").setWidth(width);
}
Ext.onReady(function() {
	setWidthHeight();
	window.onresize=setWidthHeight;
});
/* //��ʼ����ȣ����ú��򲻹���
Ext.onReady(function() {
	$(document.body).css({
	    "overflow-x":"hidden",//��ֹbody�������
	    "overflow-y":"hidden"
	  });
	
}); */

document.onkeydown=function() {
	if(event.keyCode == 27){	//����ESC
        return false;   
	}
};

function doDelRow(rowIndex){
	var store = Ext.getCmp("repeatInfogrid").store;
	var a0000 = store.getAt(rowIndex).data.a0000;
	radow.doEvent("deleteEvent",a0000);
}

function removePerson(value, params, rs, rowIndex, colIndex, ds) {
	return "<a href='#' onclick='doDelRow("+rowIndex+")'>ɾ��</a>";
}

//��ɾ����ɺ�����ˢ�±��
function reloadGird(){
	Ext.getCmp("repeatInfogrid").store.reload();
}

function exportExcel(){
	
	var excelName = null;
	
	//excel�������Ƶ�ƴ�� 
	var pgrid = Ext.getCmp('repeatInfogrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	
	if(num != 0){
		//����б��һ����
		excelName = dstore.getAt(0).get('a0101');
		
		if(num > 1){
			excelName = excelName + "��" + num +"��";
		}
	}
	
	excelName = "��Ա��Ϣ" + "_" + excelName
	+ "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('repeatInfogrid', excelName, null,null, false);
	
}  

</script>