<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<style>
body{
overflow-x: hidden ! important;
}
</style>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<odin:hidden property="path"></odin:hidden>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:textForToolBar text="<h4></h4>"/>
	<odin:fill/>
	<odin:buttonForToolBar  text="新增"  id="add" icon="images/add.gif" isLast="true" handler="addWin"/>
</odin:toolBar>

<odin:hidden  property="sql"></odin:hidden>
<div>
	<div id="toolDiv" ></div>
	<odin:editgrid property="policySetgrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="fileurl" />
		<odin:gridDataCol name="title" />
		<odin:gridDataCol name="username" />
		<odin:gridDataCol name="filename" />
		<odin:gridDataCol name="secret" />
		<odin:gridDataCol name="updatetime" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn dataIndex="id" width="90" hidden="true" header="id" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="文件相对路径" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn header="标题" dataIndex="title" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="创建人" dataIndex="username" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="文件名称" dataIndex="filename" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="等级" dataIndex="secret" align="center" edited="false" editor="select" width="50" selectData="['01','关于领导班子政治建设'],['02','关于领导班子规划纲要'],['03','关于年轻干部工作'],['04','关于党外干部、女干部工作'],['05','关于激励干部担当作为'],['06','关于干部考核工作'],['07','关于乡镇干部队伍建设'],['08','其他方面']"/>
		<odin:gridEditColumn header="更新时间" dataIndex="updatetime" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="操作" dataIndex="downtimes" align="center" renderer="GrantRender" edited="false" editor="text" width="50"  isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
 <script type="text/javascript">  


    Ext.onReady(function() {
    	//页面调整
    	 Ext.getCmp('policySetgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_policySetgrid'))[0]-4);
    	 Ext.getCmp('policySetgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_policySetgrid'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = Ext.getCmp('policySetgrid').getWidth() +'px';
    });
    
    
    function verify(){
    	var count = 0;
    	var grid = Ext.getCmp("policySetgrid");
    	var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
    	var filePath = "";
    	var status="";
    	var grid = odin.ext.getCmp('policySetgrid');
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.check) {
				filePath = record.zipfile;
				status = record.status;
				count += 1;
			}
		}
		if(count == 0){
			odin.alert("请选择要下载的数据！");
			return;
		}
		if(count > 1){
			odin.alert("仅支持单条记录下载！");
			return;
		}
		if(status != "导出完成"){
			odin.alert("等待导出完成后下载！");
			return;
		} 
		if(filePath != ""){
			radow.doEvent("v",encodeURI(filePath));
 		}else{
			odin.error("文件路径异常，无法下载！");
		} 
		
    }
    
   	
	function download(){
		var filePath="";
		var id="";
    	var grid = odin.ext.getCmp('policySetgrid');
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.check) {
				filePath = record.zipfile;
				id = record.id;
			}
		}
		window.location="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(filePath));
		radow.doEvent("count",id);
		refresh();
	}
	
	
	function addWin() {
		$h.showWindowWithSrc('addWin', contextPath
				+ "/pages/policy/addPolicy.jsp?i=1",'新增政策法规窗口',530,250);	
	}
	
	function GrantRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:downloadPolicyFile('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">下载</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		 "<a href=\"javascript:lookFileOnline('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">预览</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:deletePolicyFile('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">删除</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	
	
	//删除政策法规
	//encodeURI，用来做url转码，解决中文传输乱码问题 
	function deletePolicyFile(id, fileurl){
		
		//通过ajax请求，删除政策法规 
		$.ajax({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=deletePolicyFile',
			type:"GET",
			data:{
				"id":id,
				"filePath":encodeURI(fileurl)
			},
			success:function(){
				radow.doEvent('policySetgrid.dogridquery');
				odin.alert("政策法规删除成功!");
				parent.gzt.window.location.reload();
			},
			error:function(){
				odin.alert("政策法规删除失败!");		
			}
		});
		
	}


	//下载政策法规文件 
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	function downloadPolicyFile(id, fileurl){
		//window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	
		$('#iframe_expBZYP').attr('src',"<%= request.getContextPath() %>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl)));
		return false
	
	}
	
	function lookFileOnline(id,fileurl){
		window.open("<%=request.getContextPath()%>/SorlQueryServlet?path="+encodeURI(encodeURI(fileurl)));
	}
</script>