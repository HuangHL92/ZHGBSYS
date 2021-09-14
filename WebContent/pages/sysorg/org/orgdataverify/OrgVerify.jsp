<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<div>
		<odin:toolBar property="errorDetailGridToolBar">
							<%-- <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;错误详细信息</h1>"></odin:textForToolBar>
							<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ></odin:textForToolBar>
							<odin:separator /> --%>
							<odin:fill/>
							<odin:buttonForToolBar text="查看全部" id="selectall" cls="x-btn-text-icon"  icon="images/search.gif"  />
							<odin:separator />
							<odin:buttonForToolBar text="导出全部" id="expErrorDetail" handler="expExcelFromErrorDetailGrid" cls="x-btn-text-icon"   icon="images/icon/exp.png"/>
							 <odin:separator />
							<odin:buttonForToolBar text="编码合法校核" id="validity" /> 
							<odin:separator />
							<odin:buttonForToolBar text="编码重复校核" id="echo"/>
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar" property="errorDetailGrid"   bbarId="pageToolBar" isFirstLoadData="false" forceNoScroll="true">
							<odin:gridJsonDataModel  > 
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel002_name" />
								<odin:gridDataCol name="vel002_b" />
								<odin:gridDataCol name="vel002" />
								<odin:gridDataCol name="vel003" />
								<odin:gridDataCol name="vel004" />
								<odin:gridDataCol name="vel005" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="vel010"  isLast="true"/>				
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn dataIndex="vel001" header="错误信息id::主键 " align="left" width="85" editor="text"  edited="false"   hidden="true"/>
								<odin:gridColumn dataIndex="vel002_b" header="所属机构" align="left" width="120" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_name" header="机构" align="left" width="95" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002" header="错误主体ID" align="left" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel003" header="主体类别" align="center" width="50" editor="text" hidden="true" edited="false"  />
								<odin:gridColumn dataIndex="vel004" header="校验批次id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel005" header="业务类型" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel006"  header="错误信息" align="left"  width="390" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="vel010" header="操作人" align="center" width="100" editor="text"  edited="false"   hidden="true" isLast="true"/>
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
	Ext.getCmp('errorDetailGrid').setWidth(document.body.clientWidth);
    Ext.getCmp('errorDetailGrid').setHeight(viewSize.height+469);
		

});



function expExcelFromErrorDetailGrid(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid', null, null,null, false);
	
}


/* function echoclick(){
	odin.ext.get(document.body).mask('正在上传数据并处理中......', odin.msgCls);
	radow.doEvent("echo");
} */
</script>