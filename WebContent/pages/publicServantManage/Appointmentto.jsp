<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:hidden property="tpid"/>
<odin:hidden property="personids"/>
<odin:hidden property="pdfPath"/>
<odin:hidden property="downfile" />
<odin:window src="/blank.htm" id="pdfViewWin" width="700" height="500"
	title="任免表预览界面" modal="true" />
<div id="groupTreeContent" style="height: 100%; padding-top: 0px;">
<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
	<tr>
		<td>
			<odin:tab id="tab" width="465" height="498" tabchange="grantTabChange">
				<odin:tabModel>
					<odin:tabItem title="标准表册" id="tab1" isLast="true"></odin:tabItem>
					<%-- <odin:tabItem title="&nbsp&nbsp自定义表册&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem> --%>
				</odin:tabModel>
				<odin:tabCont itemIndex="tab1" className="tab">
					<table width="100%" id="btab1">
						<tr>
							<td width="100%">
								<odin:editgrid property="templateInfoGrid1" width="400" height="460" bbarId="pageToolBar" pageSize="20"  >
									<odin:gridJsonDataModel  >  
										<odin:gridDataCol name="tpid" />
										<odin:gridDataCol name="tptype" />
										<odin:gridDataCol name="tpname" isLast="true" />
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridColumn dataIndex="tpid" width="130" header="模板id"
												align="left" hidden="true" />
											<odin:gridColumn dataIndex="tptype" width="130" header="模板类型"
												align="left" hidden="true" />
											<odin:gridColumn dataIndex="tpname" width="50" header="模板名"
												align="left" isLast="true"/> 
										</odin:gridColumnModel>
									<odin:gridJsonData> 
											{
										        data:[]
										    } 
									</odin:gridJsonData>
								</odin:editgrid>
							</td> 
						</tr>
					</table>
				</odin:tabCont>
				<odin:tabCont itemIndex="tab2" className="tab">
				<div id="divtab2" style="display: none">
					<table width="100%" id="btab2">
						<tr>
							<td width="100%">
								<odin:editgrid property="templateInfoGrid2"   width="590" height="460" bbarId="pageToolBar" pageSize="20"  >
									<odin:gridJsonDataModel  >  
										<odin:gridDataCol name="tpid" />
										<odin:gridDataCol name="tptype" />
										<odin:gridDataCol name="tpname" isLast="true" />
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridColumn dataIndex="tpid" width="130" header="模板id"
												align="center" hidden="true" />
											<odin:gridColumn dataIndex="tptype" width="130" header="模板类型"
												align="center" hidden="true" />
											<odin:gridColumn dataIndex="tpname" width="300" header="模板名"
												align="center" isLast="true"/> 
										</odin:gridColumnModel>
									<odin:gridJsonData> 
											{
										        data:[]
										    } 
									</odin:gridJsonData>
								</odin:editgrid>
							</td> 
						</tr>
					</table>
					</div>
				</odin:tabCont>
			</odin:tab>
		</td>
	</tr>	
</table>	
</div>
<script type="text/javascript">

function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	setTimeout(cc,3000);
}
function cc(){
/* w.close(); */
}

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
     document.getElementById("divtab2").style.display='block';  
	}
}

function getPdfPath(){
	var pdfPath = document.getElementById('pdfPath').value;
	return pdfPath;
}

Ext.onReady(function() {
	setWidthHeight1();
	window.onresize=setWidthHeight;
});
function setWidthHeight(){
	setTimeout(setWidthHeight1,300);
}
function setWidthHeight1(){
	document.getElementById("groupTreeContent").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("groupTreeContent").parentNode.style.width=width+'px';
	Ext.getCmp("tab").setHeight(height);
	Ext.getCmp("tab").setWidth(width);
	
	Ext.getCmp("templateInfoGrid1").setHeight(height);
	Ext.getCmp("templateInfoGrid1").setWidth(width);
	
	Ext.getCmp("templateInfoGrid2").setHeight(height);
	Ext.getCmp("templateInfoGrid2").setWidth(width);
	
	Ext.getCmp("templateInfoGrid1").colModel.setColumnWidth(3,width-50,'');//
}

function getTpid(){
	var gridId = "templateInfoGrid1";
	var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	var tpid = selections[0].data.tpid;
	return tpid;
}

function expWord(){
	var tpid = getTpid();
	if(tpid){
		radow.doEvent("expWord",tpid);
	}
}

function expPdf(){
	var tpid = getTpid();
	if(tpid){
		radow.doEvent("expPdf",tpid);
	}
}

<odin:menu property="updateM">
<odin:menuItem text="表册预览" handler="expPdf"></odin:menuItem>
<odin:menuItem text="表册导出" handler="expWord" isLast="true"></odin:menuItem>
</odin:menu>
</script>


