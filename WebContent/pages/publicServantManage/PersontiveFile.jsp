<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
.td_width {
	width: 20px;
}
</style>
<odin:hidden property="a0000" title="人员id" />
<odin:hidden property="checkedgroupid" title="单位" />
<odin:toolBar property="btnToolBarET">
	<odin:fill />
	<odin:textForToolBar text="" />
	<odin:buttonForToolBar text="上传材料" icon="images/add.gif" id="addBtnET"  handler="upload"  isLast="true"/>
</odin:toolBar>
<div>
	<br />
</div>

<div id="div">
	<odin:editgrid2 property="clGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET"
				width="590" height="300" pageSize="10" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="wj00" />
						<odin:gridDataCol name="year" />
						<odin:gridDataCol name="wj03" />
						<odin:gridDataCol name="wj02" />
						<odin:gridDataCol name="wj05" />
						<odin:gridDataCol name="delete" isLast="true"/>		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="wj00"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="year" width="40" header="年份" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj02" width="80" header="文件类型" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj03" width="180" header="文件名" align="center" edited="false"  editor="text" renderer="file" />
						<odin:gridEditColumn2 width="30" header="操作" dataIndex="delete" editor="text" align="center"  edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
</div>
<script type="text/javascript">
    Ext.onReady(function () {
    	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
    	document.getElementById("checkedgroupid").value = window.parent.document.getElementById("checkedgroupid").value;
    	//radow.doEvent('clGrid.dogridquery');
        //var viewSize = Ext.getBody().getViewSize();
    });


  //删除行
    function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
    	var wj00 = record.data.wj00;
    	return "<a href=\"javascript:deleteRow2(&quot;"+wj00+"&quot;)\">删除</a>";
    }
    function deleteRow2(wj00){ 
    	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRow',wj00);
    		}else{
    			return;
    		}		
    	});	
    }

  //来文原件
    function file(value, params, rs, rowIndex, colIndex, ds){
    	var wj05 = rs.get('wj05');
    	var name = rs.get('wj03');
    	var url=wj05.replace(/\\/g,"/");
    	 if(name != null && name != ''){
    		return "<a href=\"javascript:downloads('" +url +"')\">"+name+"</a>";
    	} 

    } 
    function downloads(url){
    	window.location="YearCheckServlet?method=YearCheckFile&filePath="+encodeURI(encodeURI(url));
    }

    //上传文件
    function upload() {
    	radow.doEvent("insert");
    }

</script>
