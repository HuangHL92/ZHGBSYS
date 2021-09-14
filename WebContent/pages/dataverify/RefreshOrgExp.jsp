<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>
<script type="text/javascript" src="/hzb/basejs/ext/ext-lang-zh_CN-GBK.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<odin:toolBar property="btnToolBar">
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" >
<table>
	<tr>
		<odin:hidden property="psncount"  />
		<odin:hidden property="orgcount" />
	</tr>
</table>
<odin:hidden property="id"/>
<odin:editgrid property="Fgrid"  height="248"  autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="adf1" />
		<odin:gridDataCol name="info" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="adf1" hidden="true" sortable="false" header="文件"  align="left" />
		<odin:gridColumn dataIndex="name" width="100" sortable="false" header="阶段"  align="left" />
		<odin:gridColumn dataIndex="status" width="50" sortable="false" renderer="rendererthis" header="状态"  align="center" />
		<odin:gridColumn dataIndex="info" width="150" sortable="false" header="详情" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">
function myrefresh() 
{
     ///radow.doEvent('btnsx');
     Ext.getCmp("Fgrid").getStore().reload();
} 
var timer1= window.setInterval("myrefresh()",2000); 

function rendererthis(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return "";
	} else if(value=='1'){
		return "<img src='<%=request.getContextPath()%>/basejs/ext/resources/images/default/grid/wait.gif'>";
	} else if(value=='2'){
		if(rowIndex==2){
			clearInterval(timer1);
			if(rs.data['adf1']!=null && rs.data['adf1']=='orgdataverify'){
				var id = document.getElementById("id").value;
				realParent.radow.doEvent('orgdataverify',id);
				parent.Ext.getCmp('refreshWin').close();
			}else if(rs.data['adf1']!=null && rs.data['adf1']=='datarep'){
				realParent.radow.doEvent('MGrid.dogridquery');
				alert("上报完成！");
				parent.Ext.getCmp('refreshWin').close();
			}else if(rs.data['adf1']!=null && rs.data['adf1']!=''){
				downFile(rs.data['adf1']);
			}
		}
		return "<img src='<%=request.getContextPath()%>/images/right1.gif'>";
	} else if(value=='4'){
		clearInterval(timer1);
		return "<img src='<%=request.getContextPath()%>/images/wrong.gif'>";
	}
}

function downFile(downfile){
	window.location="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	//var w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	//setTimeout(cc,3000);
}

function cc()
{
	w.close();
}
</script>