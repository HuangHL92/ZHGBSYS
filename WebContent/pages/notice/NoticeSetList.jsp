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
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:textForToolBar text="<h4></h4>"/>
	<odin:fill/>
	<odin:buttonForToolBar  text="新增"  id="add" icon="images/add.gif" isLast="true" handler="addWin"/>
</odin:toolBar>
<odin:hidden property="b0111" title="主键id" ></odin:hidden>
<div>
	<div id="toolDiv" ></div>
	<odin:editgrid property="noticeSetgrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="title" />
		<odin:gridDataCol name="text" />
		<odin:gridDataCol name="filename" />
		<odin:gridDataCol name="fileurl" />
		<odin:gridDataCol name="a0000name" />
		<odin:gridDataCol name="secret" />
		<odin:gridDataCol name="updatetime" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn dataIndex="id" width="90" hidden="true" header="id" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="文件相对路径" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn header="标题" dataIndex="title" align="center" edited="false" editor="text" width="80" />
		<odin:gridEditColumn header="创建人" dataIndex="a0000name" align="center" edited="false" editor="text" width="40" />
		<odin:gridEditColumn header="等级" dataIndex="secret" align="center" edited="false" editor="select" width="40" renderer="mj"/>
		<odin:gridEditColumn header="更新时间" dataIndex="updatetime" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="操作" dataIndex="downtimes" align="center" renderer="GrantRender" edited="false" editor="text" width="50"  isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>

 <script type="text/javascript">  


    Ext.onReady(function() {
    	//页面调整
    	 Ext.getCmp('noticeSetgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_noticeSetgrid'))[0]-4);
    	 Ext.getCmp('noticeSetgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_noticeSetgrid'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = Ext.getCmp('noticeSetgrid').getWidth() +'px';
    });
    
    function mj(value, params, rs, rowIndex, colIndex, ds){
    	if(value==1){
    		return ;
    	}else if(value==2){
    		return "一级";
    	}else if(value==3){
    		return "二级";
    	}else if(value==4){
    		return "三级";
    	}
    	
    	
    }
	
	function addWin() {
		
		var b0111 = document.getElementById('b0111').value;			//当前用户所属机构
		
		if(b0111 == null || b0111 == ''){
			odin.alert("当前用户所属机构不存在，无法新增通知公告!");		
			return;
		}
		
		$h.showWindowWithSrc('addWin', contextPath
				+ "/pages/notice/addNotice.jsp?i=1",'新增通知公告窗口',650,700);	
	}
	
	function GrantRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:updateNotice('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">修改</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:deleteNotice('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">删除</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		"<a href=\"javascript:noticeRecipentList('"+rs.get('id')+"')\">查看情况</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	
	
	//删除通知公告
	//encodeURI，用来做url转码，解决中文传输乱码问题 
	function deleteNotice(id, fileurl){
		
		//通过ajax请求，删除政策法规 
		$.ajax({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=deleteNotice',
			type:"GET",
			data:{
				"id":id,
				"filePath":encodeURI(fileurl)
			},
			success:function(){
				radow.doEvent('noticeSetgrid.dogridquery');
				odin.alert("通知公告删除成功!");	
				parent.gzt.window.location.reload();
			},
			error:function(){
				odin.alert("通知公告删除失败!");		
			}
		});
		
	}


	//下载通知公告文件 
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	function downloadNoticeFile(id, fileurl){
		window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	}
	
	//修改通知公告 
	function updateNotice(id, fileurl) {
		$h.showWindowWithSrc('updateWin', contextPath
				+ "/pages/notice/updateNotice.jsp?i=1&id="+id,'修改通知公告窗口',750,900);	
	}
	
	//通知公告查看列表，弹窗 
	function noticeRecipentList(noticeId){
		$h.openWin('noticeRecipentList','pages.notice.NoticeRecipentWindow','通知公告查看列表',600,600,noticeId,contextPath);
	}
</script>