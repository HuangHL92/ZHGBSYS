<%@page import="com.insigma.siis.local.pagemodel.xbrm.ldbj.PreviewQueryPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%
	try{
		
	
	//String sql=request.getParameter("sql");
	String qvid=request.getParameter("qvid");
	PreviewQueryPageModel previewquerypagemodel=new PreviewQueryPageModel();
	List<HashMap<String, Object>> list=previewquerypagemodel.returnlistPreview(qvid);
	String num="0";
	if(list!=null){
		num=list.size()+"";
	}
%>
<div id="divUp" style="text-align: center;vertical-align: middle;height: 600px;line-height: 500px; color: #15428b;display: block;">
	’设置条件‘中，勾选的指标项名作为显示项!
</div>
<script>
Ext.onReady(function (){
	var qvid=document.getElementById('qvid').value;
	if(qvid!=null&&qvid.trim()!=''&&qvid.trim()!='null'&&qvid!=undefined){
		document.getElementById('divUp').style.display='none';
		document.getElementById('divDown').style.display='block';
	}
});
</script>
<div>
<odin:hidden property="qvid" value="<%=qvid %>"/>
<odin:hidden property="columncount" value="<%=num %>"/>
<div id="divDown" style="display: none;">
	<odin:editgrid property="previewListGrid" bbarId="pageToolBar"  
	pageSize="20" width="390" height="570" url="/" >
		<odin:gridJsonDataModel>
			<%
				for(int i=0;list!=null&&i<list.size();i++){
					String fldname=(String)list.get(i).get("fldname").toString().toLowerCase();
					%>
					<odin:gridDataCol name="<%=fldname %>" />
					<%
				}
			%>
			<odin:gridDataCol name="hidden111" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<%
			for(int i=0;list!=null&&i<list.size();i++){
				String fldnamenote=(String)list.get(i).get("fldnamenote").toString().toLowerCase();
				String fldname=(String)list.get(i).get("fldname").toString().toLowerCase();
				String code_type=(String)list.get(i).get("code_type");
				if(code_type==null||code_type.trim().equals("")||code_type.trim().equals("null")){
					%>
					<odin:gridEditColumn header="<%=fldnamenote %>" width="100"  dataIndex="<%=fldname %>"  edited="false" editor="text" align="left" />
					<%
				}else{
					%>
					<odin:gridEditColumn2 header="<%=fldnamenote %>" width="100"  dataIndex="<%=fldname %>"  edited="false" editor="select" codeType="<%=code_type.toUpperCase() %>" align="left" />
					<%
				}
				
			}
			%>	
			<odin:gridEditColumn header="100" width="100" hidden="true" dataIndex="hidden111"  edited="false" editor="text"  isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid>
</div>
</div>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<script type="text/javascript">
Ext.onReady(function() {
	setColumnWidth();
	var viewSize = Ext.getBody().getViewSize();
	Ext.getCmp('previewListGrid').setHeight(viewSize.height)
}); 
function setColumnWidth(){
	var columncount=document.getElementById("columncount").value;
	var grid=Ext.getCmp('previewListGrid');
	var num=parseInt(columncount);
	for(i=0;i<num;i++){
		if(i==0){
			grid.colModel.setColumnWidth(i,25,'');
		}else{
			grid.colModel.setColumnWidth(i,100,'');
		}
	}
	
}
Ext.onReady(function(){
	var pgrid = Ext.getCmp('previewListGrid');
	var bbar = pgrid.getBottomToolbar();
	bbar.insertButton(11,[
	                      new Ext.menu.Separator({cls:'xtb-sep'}),
							new Ext.Button({
								icon : 'images/icon/table.gif',
								id:'saveSortBtn',
							    text:'导出Excel',
							    handler:function(){
							    	expExcelFromGrid();
							    }
							})
							]); 
});
function expExcelFromGrid(){
	
	var excelName = null;
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('previewListGrid');
	var dstore = pgrid.getStore();
	excelName = "人员信息" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	
	odin.grid.menu.expExcelFromGrid('previewListGrid', excelName, null,null, false);
	
} 
</script>