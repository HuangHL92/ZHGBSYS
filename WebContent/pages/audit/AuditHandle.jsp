<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>�������</title>
<link type="text/css" rel="stylesheet" href="<%=ctxPath%>/pages/huiyi/static/layui/css/layui.css"/>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/basejs/ext/ux/css/LockingGridView.css" />
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/huiyi/static/layui/lay/modules/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/ext/ux/LockingGridView.js"></script>
<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
<%String groupID=request.getParameter("groupID"); %>
</head>
<body>
	<odin:hidden property="modelType" value="1" ></odin:hidden>
	<div id="container">
		<table id="container-table" border="0" cellspacing='0'>
			<tr>
				<td valign="top" style="position: relative;" id="left-td">
					<div id="search-div" class="scrollbar">
						<!-- odin��ǩ width �й����� 130�� �޹����� 140 -->
						<table id="search-tbl" align="center">
							
							<tr>
							<odin:textEdit property="pc"  label="����"  width="140" /> 
							</tr>
							<tr>
							<odin:textEdit property="name"  label="����"   width="140" /> 
							</tr>
							
						</table>
					</div>
					<div id="search-btn" align="center">
						<table align="center">
							<tr>
								<td><odin:button text="���" handler="refrush" /></td>
								<td><odin:button text="��ѯ" handler="search" /></td>
							</tr>
						</table>
					</div>
				</td>
				<td style="position: relative;" id="middle-td">
					<div id="divresize" onclick="foldLeftContainer(this)" style="background: url(image/right.png) #D6E3F2 no-repeat center center;"></div>
				</td>
				<td valign="top" id="right-td">
					<div id="container-div" class="scrollbar">     
						<!-- �������еİ�ť -->	     
						<div id="toolDiv" style="width: 100%;height: 30px;" >
						  <div class="layui-btn-group" >
							<a class="layui-btn layui-btn-sm" id="Batch" href="javascript:changeView(1);">�����б�</a>
							<a class="layui-btn layui-btn-sm" id="People" href="javascript:changeView(2);">��Ա�б�</a>
							<a class="layui-btn layui-btn-normal layui-btn-normal layui-btn-sm" id="AddBatch" href="javascript:addBatch();">�������</a>
							<a class="layui-btn layui-btn-normal layui-btn-normal layui-btn-sm" id="submitGJ" href="javascript:submitGJ();">�ύ�ɲ��ල��</a>
						 	<a class="layui-btn layui-btn-normal layui-btn-normal layui-btn-sm" id="submitAudit" href="javascript:submitAudit();">�ύ����</a>
						  </div>
						</div>
						
						<!-- ���б� -->
						<div id="batchGirdDiv" style="width: 100%;margin:0px 0px 0px 0px;" >	
							<odin:editgrid2 property="memberGrid" enableColumnHide="false"  autoFill="false" locked="true" width="100%" height="496" bbarId="pageToolBar" pageSize="20" hasRightMenu="false" >
							<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="check" />
								<odin:gridDataCol name="oid" />
								<odin:gridDataCol name="audit_batch_no" />
								<odin:gridDataCol name="dept_sub_time"/>
								<odin:gridDataCol name="audit_sub_time"/>
								<odin:gridDataCol name="batch_status" />
								<odin:gridDataCol name="createbyname" />
								<odin:gridDataCol name="batch_type" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel2 >
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
								<odin:gridEditColumn2 dataIndex="check" width="30" header="selectall" align="center" editor="checkbox" edited="true" locked="true" gridName="memberGrid" checkBoxSelectAllClick="getCheckList" />
								<odin:gridEditColumn2 dataIndex="oid" width="90" header="oid" align="center" editor="text"  edited="false" hidden="true" locked="true"  />
								<odin:gridEditColumn2 dataIndex="audit_batch_no" width="90" header="���κ�" align="center"  editor="text"  edited="false" locked="true" />
								<odin:gridEditColumn2 dataIndex="dept_sub_time" width="180" header="�����ύʱ��" align="center"  editor="text"  edited="false"  />
								<odin:gridEditColumn2 dataIndex="audit_sub_time" width="200" header="�����ύʱ��" align="center" editor="text"  edited="false"/>
								<odin:gridEditColumn2 dataIndex="batch_status" header="״̬" width="90"  align="center" editor="select"  edited="false" codeType="AUDIT_STATUS"/>
								<odin:gridEditColumn2 dataIndex="createbyname" header="������" width="90"  align="center" editor="text"  edited="false"/>
								<odin:gridEditColumn2 dataIndex="batch_type" header="����"  width="180"  align="center" editor="select" codeType="AUDIT_TYPE"  edited="false"  isLast="true"/>
							</odin:gridColumnModel2>
							<odin:gridJsonData>
								{
							        data:[]
							    }
							</odin:gridJsonData>
						</odin:editgrid2>
						</div>
						
						<div id="peopleGirdDiv" style="width: 101%;margin:0px 0px 0px 0px;" >	
							<odin:editgrid2 property="MGrid"   height="200" bbarId="pageToolBar" pageSize="20"
									hasRightMenu="false" autoFill="true"  url="/">
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="oid"/>
										<odin:gridDataCol name="a0000"/>
										<odin:gridDataCol name="a0101"/>
										<odin:gridDataCol name="a0184"/>
										<odin:gridDataCol name="family"/>
										<odin:gridDataCol name="a0192a" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
										<odin:gridEditColumn2 dataIndex="oid" width="10" editor="text" header="����" hidden="true"/>
										<odin:gridEditColumn2 dataIndex="a0000" width="10" editor="text" header="a0000" hidden="true"/>
										<odin:gridEditColumn2 dataIndex="a0101" width="30" header="����" editor="text" edited="false"  sortable="false" align="center"/>
										<odin:gridEditColumn2 dataIndex="a0184" width="50" header="���֤" editor="text" edited="false"   sortable="false" align="center" />
										<odin:gridEditColumn2 dataIndex="a0192a" width="80" header="��λְ��" editor="text" edited="false"  sortable="false" align="center"  />
										<odin:gridEditColumn2 dataIndex="family" width="80" header="��ͥ��Ա" editor="text" edited="false"  sortable="false" align="center" isLast="true" />
									</odin:gridColumnModel>
								</odin:editgrid2>
						</div>
						
						
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- ��JS�ļ�����ҳ��Ԫ��ĩβ���� -->
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	
	<script type="text/javascript">
	Ext.onReady(function() {
		Ext.getCmp('memberGrid').stripeRows=true;
		Ext.getCmp('MGrid').stripeRows=true;
		//����Ӧ��С
		window.onresize=resizeframe;
		resizeframe(); 

		var grid = Ext.getCmp('MGrid');
		var bbar = grid.getBottomToolbar();
		 bbar.insertButton(11,[
							
			new Ext.menu.Separator({cls:'xtb-sep'}),
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'setPageSize',
			    text:'����ÿҳ����',
			    handler:setPageSize
			}),
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'expAll',
			    text:'����ȫ��',
			    handler:expAll
			})
		]);
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
	
	
	function setPageSize(){
		var gridId = 'MGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ���õ�grid�����ڣ�gridId=" + gridId);
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
	

	
	
	
	
	
	
	
	function expAll(){
		odin.grid.menu.expExcelFromGrid('MGrid', null, null,null, false);
	}
	

	
	function search(){
		
		var modeltype=$("#modelType").val();
        if(modeltype=="1"){
        	radow.doEvent('memberGrid.dogridquery');
        }else{
        	radow.doEvent('MGrid.dogridquery');
		}
	}
	
	//���
	function refrush(){
		document.getElementById('pc').value="";
		document.getElementById('name').value="";
		
	} 
	
	
	//���� ������չ��
	function foldLeftContainer(node) {
		if (node.style.background.indexOf("right.png") != -1) { // ����
			node.style.background = "url(image/left.png) #D6E3F2 no-repeat center center";
	
			leftTd.style.display = "none";
	
			rightTd.style.width = clientWidth - middleWidth + 'px';
			containerDiv.style.width = clientWidth - middleWidth + 'px';
			
			var modeltype=$("#modelType").val();
			if(modeltype=="1"){
				var grid=Ext.getCmp('memberGrid');
		    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
		    	var gwidth=clientWidth-middleWidth;//��ȡ��ǰdiv���
		    	grid.setWidth(gwidth);//����grid�Ŀ��
			}else{
				var mGrid = Ext.getCmp('MGrid');
		    	mGrid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
		    	mGrid.setWidth(gwidth);//����grid�Ŀ��
			}
			
	    	
	    	
	    	
		} else { // չ��
			node.style.background = "url(image/right.png) #D6E3F2 no-repeat center center";
	
			leftTd.style.display = "block";
	
			rightTd.style.width = clientWidth - searchDiv.offsetWidth - middleWidth
					+ 'px';
			containerDiv.style.width = clientWidth - searchDiv.offsetWidth
					- middleWidth + 'px';
			
			var modeltype=$("#modelType").val();
			if(modeltype=="1"){
				var grid=Ext.getCmp('memberGrid');
		    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
		    	var gwidth=clientWidth-middleWidth;//��ȡ��ǰdiv���
		    	grid.setWidth(gwidth);//����grid�Ŀ��
			}else{
				var mGrid = Ext.getCmp('MGrid');
		    	mGrid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
		    	mGrid.setWidth(gwidth);//����grid�Ŀ��
			}
		}
	}
	
	function resizeframe(){
		var modeltype=$("#modelType").val();
        //�����б�
        if(modeltype=="1") {
			$("#batchGirdDiv").show();
			$("#peopleGirdDiv").hide();
            var batchGrid = Ext.getCmp('memberGrid');
			batchGrid.setHeight(clientHeight-document.getElementById("toolDiv").offsetHeight)
			//��Ա�б�
		}else{
            $("#batchGirdDiv").hide();
			$("#peopleGirdDiv").show();
            var mGrid = Ext.getCmp('MGrid');
            mGrid.setHeight(clientHeight-document.getElementById("toolDiv").offsetHeight);
		}
        

	}
	
	function changeView(type) {
        $("#modelType").val(type);
        resizeframe();
    }

	
	
	function addBatch() {
        $h.openWin('AuditBatch','pages.audit.AddAuditBatch','��������',window.parent.document.body.offsetWidth-450,900,null,contextPath,null,
            {modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
    }

	function openBatch(id) {
        $h.openWin('AuditBatch','pages.audit.AddAuditBatch','�޸�����',window.parent.document.body.offsetWidth-450,900,id,contextPath,null,
            {modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
   }
	
	
	
	function submitGJ(){
		var grid = Ext.getCmp("memberGrid");
        var store = grid.getStore();
        var id = '';
        

        for (var i = 0; i < store.data.length; i++) {
            var selected = store.getAt(i);
            var record = selected.data;
            if (record.check) {
                id +="'"+ record.oid+"',";
            }
        }
        if (id == '') {
            odin.error("�빴ѡҪ�ύ�����Σ�");
            return;
        }
       id=id.substring(0,id.length-1);
       radow.doEvent("submitGJ",id);
        
	}
	
	function submitAudit(){
		var grid = Ext.getCmp("memberGrid");
        var store = grid.getStore();
        var id = '';
        

        for (var i = 0; i < store.data.length; i++) {
            var selected = store.getAt(i);
            var record = selected.data;
            if (record.check) {
                id +="'"+ record.oid+"',";
            }
        }
        if (id == '') {
            odin.error("�빴ѡҪ�ύ�����Σ�");
            return;
        }
       id=id.substring(0,id.length-1);
       radow.doEvent("submitAudit",id);
        
	}
	</script>
</body>
</html>
