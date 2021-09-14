<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>����ƽ̨</title>
<odin:head />
<link type="text/css" rel="stylesheet" href="<%=ctxPath%>/pages/huiyi/static/layui/css/layui.css"/>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/templete/templete-default.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/basejs/ext/ux/css/LockingGridView.css" />
<script src="<%=request.getContextPath()%>/pages/huiyi/static/layui/lay/modules/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/ux/LockingGridView.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/odin.grid.menu.js"></script>

<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
</head>
<odin:hidden property="path" />
<odin:hidden property="impType" />
<odin:hidden property="impBatch" />

<body>
	<div id="container">
		<table id="container-table" border="0" cellspacing='0'>
			<tr>
				<td valign="top" id="right-td">
					<div id="container-div" class="scrollbar">
						<!-- �������еİ�ť -->
						<div style="width: 100%;height: 30px;background-color: #cedff5;" id="toolDiv">
							<table>
								<tr>
									
									<td><div id="btnToolBarDiv2"></div></td>
								</tr>
							</table>
						</div>
						<!-- ���б� -->
						<div id="girdDiv" style="width: 100%;margin:0px 0px 0px 0px;" >	     
							<odin:editgrid2 property="unitreGrid" title="" autoFill="false" width="100%" bbarId="pageToolBar" locked="true" pageSize="20" isFirstLoadData="false" url="/" >
								<odin:gridJsonDataModel id="idmodel" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="check" />
								<odin:gridDataCol name="oid" />
								<odin:gridDataCol name="audit_batch_no" />
								<odin:gridDataCol name="dept_sub_time"/>
								<odin:gridDataCol name="audit_sub_time"/>
								<odin:gridDataCol name="batch_status" />
								<odin:gridDataCol name="createbyname" />
								<odin:gridDataCol name="batch_type" isLast="true"/>
								</odin:gridJsonDataModel>
								<odin:gridColumnModel2>
								    <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
								<odin:gridEditColumn2 dataIndex="check" width="30" header="selectall" align="center" editor="checkbox" edited="true" locked="true" gridName="unitreGrid" checkBoxSelectAllClick="getCheckList" />
								<odin:gridEditColumn2 dataIndex="oid" width="90" header="oid" align="center" editor="text"  edited="false" hidden="true" locked="true"  />
								<odin:gridEditColumn2 dataIndex="audit_batch_no" width="90" header="���κ�" align="center"  editor="text"  edited="false" locked="true" />
								<odin:gridEditColumn2 dataIndex="dept_sub_time" width="180" header="�����ύʱ��" align="center"  editor="text"  edited="false"  />
								<odin:gridEditColumn2 dataIndex="audit_sub_time" width="200" header="�����ύʱ��" align="center" editor="text"  edited="false"/>
								<odin:gridEditColumn2 dataIndex="batch_status" header="״̬" width="90"  align="center" editor="select"  edited="false" codeType="AUDIT_STATUS"/>
								<odin:gridEditColumn2 dataIndex="createbyname" header="������" width="90"  align="center" editor="text"  edited="false"/>
								<odin:gridEditColumn2 dataIndex="batch_type" header="����"  width="180"  align="center" editor="select" codeType="AUDIT_TYPE"  edited="false" />
								<odin:gridEditColumn2 header="����"  dataIndex="oid" align="center" edited="false" editor="text" width="200"  isLast="true" renderer="renderOption"/>
								</odin:gridColumnModel2>
						</odin:editgrid2>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<odin:toolBar property="btnToolBar2" applyTo="btnToolBarDiv2">
		
		<odin:separator/>
		<odin:buttonForToolBar text="����Ա����" id="impBtn" icon="images/icon/exp.png" handler="impExcel" isLast="true" />
	</odin:toolBar>

	
	<!-- ��JS�ļ�����ҳ��Ԫ��ĩβ���� -->
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	<script type="text/javascript">  
    Ext.onReady(function() {
		//����Ӧ��С
		Ext.getCmp('unitreGrid').stripeRows=true;
		window.onresize=resizeframe;
		resizeframe(); 
		
    	 var pgrid = Ext.getCmp('unitreGrid');
    		var bbar = pgrid.getBottomToolbar();
    		 bbar.insertButton(11,[
    							
    							new Ext.menu.Separator({cls:'xtb-sep'}),
    							new Ext.Button({
    								icon : 'images/keyedit.gif',
    								id:'setPageSize',
    							    text:'����ÿҳ����',
    							    handler:setPageSize
    							})
   
    							]);
    		
    		 
    	 
    });
    
	function setPageSize(){
		var gridId = 'unitreGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
		}
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGrid.jsp";
			doOpenPupWin(url, "����ÿҳ����", 300, 150);
			
			
		} else {
			odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
			return;
		}
	}
	
	
	function setGridHidden(name){
		 var pgrid = Ext.getCmp('unitreGrid');
		var column=pgrid.getColumnModel();
		var cols=column.getColumnCount(true);
		for(var i=6;i<cols;i++){
			var header=column.getColumnHeader(i);
			if(header.indexOf(name)==-1){
				pgrid.getColumnModel().setHidden(i, true);
			}
		}
		
	}
	
	
	//��ѯ
	 function search(){
		radow.doEvent('unitreGrid.dogridquery');
	}
	
	 function renderOption(value, params, rs, rowIndex, colIndex, ds){
		    
		    var btn=" <div class=\"layui-btn-group\">";
		    

			
			btn=btn+"<a class=\"layui-btn layui-btn-normal layui-btn-xs\" href=\"javascript:expExcel('" + value + "')\">����</a>";
			btn=btn+"<a class=\"layui-btn layui-btn-normal layui-btn-xs\" href=\"javascript:impBatchExcel('" + value + "')\">����</a>";
			

		    btn=btn+"</div>";
		    return btn;
		}



	function openInfo(id){
    	
		$h.openWin('lsData','pages.audit.AuditPersonInfo','����¼��',1200,800,id,contextPath,null,
				{maximizable:false,resizable:true,draggable:false}, true);
	}

	

	function resizeframe(){
		var grid = Ext.getCmp('unitreGrid');
		grid.setHeight(clientHeight - document.getElementById("toolDiv").offsetHeight);
	}
	
	
	
	function impExcel(){
		document.getElementById("impType").value="AuditFeedback";
		$h.showWindowWithSrc2('AuditExcelImp', contextPath+ "/pages/audit/AuditFeedbackImp.jsp", '���봰��', 600, 230, window,
				{maximizable:false,resizable:false,draggable:false}, true);
	}
	
	function impBatchExcel(oid){
		document.getElementById("impType").value="AuditFeedback_Batch";
		document.getElementById("impBatch").value=oid;
		$h.showWindowWithSrc2('AuditExcelImp', contextPath+ "/pages/audit/AuditFeedbackImp.jsp", '���봰��', 600, 230, window,
				{maximizable:false,resizable:false,draggable:false}, true);
	}
	
	
	function expExcel(oid){
		radow.doEvent("expExcel",oid);
	}
	
	function exp() {
        var path = document.getElementById('path').value;
        
        window.location.href = '<%=request.getContextPath()%>' + '/ProblemDownServlet?method=downFileSys&prid=' + encodeURI(encodeURI(path));
    }
	
</script>
</body>
</html>
