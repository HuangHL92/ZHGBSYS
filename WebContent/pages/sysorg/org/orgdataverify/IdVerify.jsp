<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div>
	<odin:toolBar property="errorDetailGridToolBar">
						<%-- <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;������ϸ��Ϣ</h1>"></odin:textForToolBar>
							<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ></odin:textForToolBar>
							<odin:separator /> --%>
							<odin:fill/>
							<odin:buttonForToolBar text="�����б�" id="savelist" icon="images/icon/save.gif"></odin:buttonForToolBar>
							<odin:buttonForToolBar text="�鿴ȫ��" id="selectall" cls="x-btn-text-icon"  icon="images/search.gif"  />
							<odin:separator />
							<odin:buttonForToolBar text="����ȫ��" id="expErrorDetail" handler="expExcelFromIDErrorDetailGrid" cls="x-btn-text-icon"   icon="images/icon/exp.png"/>
							<odin:separator />
							<odin:buttonForToolBar text="���֤�Ϸ�У��" id="validity" />
							<odin:separator />
							<odin:buttonForToolBar text="���֤�ظ�У��" id="echo"/>
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar" property="IDerrorDetailGrid"  bbarId="pageToolBar" isFirstLoadData="false" forceNoScroll="true" remoteSort="true">
							<odin:gridJsonDataModel  > 
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel002_name" />
								<odin:gridDataCol name="vel002_sfz" />
								<odin:gridDataCol name="vel002_zw" />
								<odin:gridDataCol name="vel002" />
								<odin:gridDataCol name="vel003" />
								<odin:gridDataCol name="vel004" />
								<odin:gridDataCol name="vel005" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="vel010"  isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn dataIndex="vel001" header="������Ϣid::���� " align="center" width="85" editor="text"  edited="false"   hidden="true"/>
								<odin:gridColumn dataIndex="vel002_name" header="����" align="left" width="50" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_sfz" header="���֤����" align="left" width="130" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="vel002_zw" header="ְ��" align="left" width="120" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002" header="��������ID" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel003" header="�������" align="center" width="50" editor="text" hidden="true" edited="false" hidden="true" />
								<odin:gridColumn dataIndex="vel004" header="У������id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel005" header="ҵ������" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel006" header="������Ϣ" align="left" width="330" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel010" header="������" align="center" width="100" editor="text"  edited="false"   hidden="true" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
							 	{
							        data:[]
							    }				
							</odin:gridJsonData>
						</odin:editgrid>
	
	
</div>
<odin:hidden property="bsType"/>

<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	Ext.getCmp('IDerrorDetailGrid').setWidth(document.body.clientWidth);
    Ext.getCmp('IDerrorDetailGrid').setHeight(viewSize.height+469);
    var egrid = Ext.getCmp("IDerrorDetailGrid");
	 var estore = egrid.getStore();
	 estore.on({
		 load:function(s){
			 var view =egrid.getView();
			 var rows =egrid.getView().getRows();
			 for(var i=0; i<rows.length; i++){
				 for(var j=0;j<10;j++){
					 var cell = view.getCell(i,j);
					 cell.innerHTML = cell.innerHTML.replace("x-grid3-cell-inner","");
				 }
			 }
			
		 }
	 });

});



function expExcelFromIDErrorDetailGrid(){
	odin.grid.menu.expExcelFromGrid('IDerrorDetailGrid', null, null,null, false);
}


/* function echoclick(){
	odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
	radow.doEvent("echo");
} */


</script>