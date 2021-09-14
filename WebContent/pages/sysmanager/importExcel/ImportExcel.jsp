<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<style>

.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
word-break:break-all;
}

</style>
<script  type="text/javascript">

function opwin() {
	var win = odin.ext.getCmp('fileImpWin');
	win.setTitle('���봰��');
	/// win.setSize(500,350); //���  �߶�
	odin.showWindowWithSrc('fileImpWin', contextPath
			+ "/pages/sysmanager/ZWHZYQ_001_004/FileImpWindow.jsp");
}
</script>

<div id="groupTreeContent" >
<div id="groupTreePanel"></div>


<odin:groupBox title="������Ϣ��ѡ��" property="ggBox">
	<form name="excelForm" id="excelForm" method="post" action="<%=request.getContextPath()%>/TagFileServlet?method=addZhgbExcelFile" enctype="multipart/form-data" >
	<table >
		<tr>
			<odin:select2 property="importType" editor="true" size="30"  label="��������"/>
			<odin:textEdit width="720" inputType="file" colspan="4"  property="excelFile" label="ѡ���ļ�" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="startNumber" size="33" label="�������ʼ�У��������Ҫ�������ݵ�excel������"/>
			<odin:textEdit property="rowNumber" size="33" label="�����������"/>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<odin:button text="&nbsp;&nbsp;��&nbsp;��&nbsp;&nbsp;" property="impBtn" handler="formSubmit" />
			</td>
		</tr>
	</table>
	</form>
</odin:groupBox>
<table >
	<tr>
		<td width="50%">
			<odin:editgrid property="memberGrid" hasRightMenu="false" title="��־����Ϣ" autoFill="true"  forceNoScroll="false"  height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="system_log_id" />
					<odin:gridDataCol name="userlog"/>
					<odin:gridDataCol name="eventtype"/>
					<odin:gridDataCol name="eventobject" />
					<odin:gridDataCol name="objectname" />
					<odin:gridDataCol name="system_operate_date" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="system_log_id" width="110" hidden="true" header="��־����" align="center"/>
					<odin:gridColumn dataIndex="userlog" editor="select" width="110" header="�����û�" codeType="USER"  align="center"/>
					<odin:gridColumn dataIndex="eventtype" width="190" header="��������" align="center" editor="text"/>
					<odin:gridColumn dataIndex="eventobject" width="140" header="��Ϣ��" align="center" editor="text"/>
					<odin:gridColumn dataIndex="objectname" header="��������" width="80" align="center" editor="text"/>
					<odin:gridColumn dataIndex="system_operate_date" width="130" header="����ʱ��" align="center" editor="text" isLast="true" renderer="time"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
		<td width="50%">
			<odin:editgrid property="logGrid" title="��־��ϸ��Ϣ"  hasRightMenu="false"   autoFill="true" forceNoScroll="false"    height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="systemlogdetailid" />
					<odin:gridDataCol name="dataname"/>
					<odin:gridDataCol name="oldvalue"/>
					<odin:gridDataCol name="newvalue" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="dataname" width="150" header="��Ϣ��" align="center"/>
					<odin:gridColumn dataIndex="oldvalue" width="300" header="ԭֵ" align="center"/>
					<odin:gridColumn dataIndex="newvalue" width="300" header="��ֵ" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
		
</div>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>��־����</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="��־�鵵" id="logSaveBtn" tooltip="������־Ǩ��"/> --%>
	<odin:buttonForToolBar text="����excel" icon="images/icon/exp.png" id="expExcel" handler="expExcel"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="��־Ǩ��" icon="images/icon/exp.png" id="logMoveBtn" tooltip="������־Ǩ��"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������־" icon="images/mylog.gif" id="logExpBtn" handler="logExp" tooltip="������־����"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������־" icon="images/icon/imp.gif" id="logImpBtn22" handler="opwin" tooltip="������־����"/>
	<odin:separator></odin:separator>  --%>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="findLogBtn" tooltip="��ѯ��־" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel> -->

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="ģ��Ȩ�޷���ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="fileImpWin" width="400" height="170" title="��־����ҳ��" modal="true"></odin:window>		
<odin:window src="/blank.htm" id="logMoveWin" width="430" height="330" title="��־Ǩ��ҳ��" modal="true"></odin:window>

<script type="text/javascript">
function expExcel(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,null, false);
}
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height-140));
	 Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
//	 Ext.getCmp('groupTreePanel').setWidth(document.body.clientWidth);
	 //document.getElementById("groupTreePanel").style.width = document.body.clientWidth;
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}


function flash() {
	radow.doEvent('memberGrid.dogridquery');
	
}
</script>


