<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<style>
body{
overflow-x: hidden ! important;
}
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<odin:hidden property="path"></odin:hidden>
<odin:hidden property="checkList"/>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<%-- <odin:textForToolBar text=""/>
	<odin:textForToolBar text="<h4>数据导出详情页</h4>"/> --%>
	<odin:fill/>
	<%-- <odin:buttonForToolBar  text="MYSQL数据库上报" id="mysqlExp" icon="images/icon/exp.png" handler="mysqlDataExp"/>
	<odin:separator/> --%>
	<%-- <odin:buttonForToolBar  text="自定义格式数据上报" id="expZDY" icon="images/icon/exp.png" handler="dataExp2"/>
	<odin:separator/> --%>
	<odin:buttonForToolBar  text="HZB格式数据上报" id="exp" icon="images/icon/exp.png" handler="dataExp"/>
	<odin:separator/>
	 <odin:buttonForToolBar  text="通用格式数据上报" id="uniexp" icon="images/icon/exp.png" handler="unidataExp"/>
	<odin:separator/> 
	<%-- <odin:buttonForToolBar  text="Excel格式数据上报" id="exceliexp" icon="images/icon/exp.png" handler="exceldataExp"/>
	<odin:separator/> --%>
	<odin:buttonForToolBar  text="下载"  id="down" icon="images/icon_photodesk.gif" tooltip="仅支持单条记录下载" handler="verify" />
	<odin:separator/>
	<odin:buttonForToolBar text="删除记录" handler="delimp" icon="images/icon/delete.gif" id="delbtn" isLast="true"/> 
	<%-- <odin:separator/>
	<odin:buttonForToolBar  text="刷新列表"  id="refresh" icon="images/sx.gif" isLast="true"  handler="refresh"/> --%>
</odin:toolBar>

<odin:hidden  property="sql"></odin:hidden>

<table style="width: 100%;border-collapse:collapse;" id="table_qo_id">
	<tr>
		<td width="15%">
		<div style="overflow: auto; height: 100%;position: relative;">
			<odin:tab id="tab" width="240">
				<odin:tabModel>
					<odin:tabItem title="查询" id="tab1" isLast="true"></odin:tabItem>
				</odin:tabModel>
				<odin:tabCont itemIndex="tab1" className="tab">
					<table id="tb1" style="width:100%">
					<tr height="12px"><td colspan="2">&nbsp;</td></tr>
					<tr >
						<odin:textEdit property="userId" label="导出用户" maxlength="32"/>
					</tr>
					<tr height="12px"><td colspan="2">&nbsp;</td></tr>
					<tr >
						<odin:dateEdit property="time" label="起始时间"/>
					</tr>
					<tr height="12px"><td colspan="2">&nbsp;</td></tr>
					<tr >
						<odin:dateEdit property="time2" label="截止时间"/>
					</tr>
					<tr height="12px"><td colspan="2">&nbsp;</td></tr>
					<tr >
						<td></td>
						<td>
							<div style="margin-left: 30px">
							<odin:button property="b1" text="查&nbsp;&nbsp;询" handler="clickquery"></odin:button>
							</div>
						</td>
					</tr>
					<tr>
						<td height="270px" colspan="2"></td>
					</tr>
				</table>
				</odin:tabCont>
			</odin:tab>
		</div>
		</td>
		<td width="85%">
			<div id="toolDiv"></div> 
			<odin:editgrid property="expInfogrid"
				title="" autoFill="true" bbarId="pageToolBar" pageSize="20"
				isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel id="id" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="check" />
					<odin:gridDataCol name="createuser" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="b0101" />
					<odin:gridDataCol name="psncount" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="starttime" />
					<odin:gridDataCol name="endtime" />
					<odin:gridDataCol name="zipfile" />
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="downtimes" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="20"
						editor="checkbox" dataIndex="check" edited="true" hideable="false"
						gridName="expInfogrid" checkBoxClick="getCheckList()" checkBoxSelectAllClick="getCheckList()"/>
					<odin:gridEditColumn header="文件名" dataIndex="name" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="机构名称" dataIndex="b0101" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="人数" dataIndex="psncount" align="center"
						edited="false" editor="text" width="30" />
					<odin:gridEditColumn header="操作人" dataIndex="createuser"
						edited="false" editor="text" width="50" hidden="true"/>
					<odin:gridEditColumn header="开始时间" dataIndex="starttime"
						align="center" edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="结束时间" dataIndex="endtime"
						align="center" edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="状态" dataIndex="status" edited="false"
						editor="text" width="50" align="center"/>
					<odin:gridEditColumn header="路径" dataIndex="zipfile" align="center"
						edited="false" editor="text" width="50" hidden="true" />
					<odin:gridEditColumn header="id" dataIndex="id" align="center"
						edited="false" editor="text" width="50" hidden="true" />
					<odin:gridEditColumn header="下载次数" dataIndex="downtimes"
						align="center" edited="false" editor="text" width="50" hidden="true"
						isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>


<div>
	
</div>

 <script type="text/javascript">  


    Ext.onReady(function() {
    	setWidthHeight();
		window.onresize=setWidthHeight;
    	//页面调整
    	/*  Ext.getCmp('expInfogrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_expInfogrid'))[0]-4);
    	 //Ext.getCmp('expInfogrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_expInfogrid'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = Ext.getCmp('expInfogrid').getWidth()+17 +'px';
    	 document.getElementById("tb1").style.height = Ext.getCmp('expInfogrid').getHeight()-20 + "px";//tb1 */

    });
    function setWidthHeight(){
		document.getElementById("table_qo_id").parentNode.parentNode.style.overflow='hidden';
		document.getElementById("table_qo_id").parentNode.style.width=document.body.clientWidth;
		var heightClient=window.parent.document.body.clientHeight;//窗口高度
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//系统，上方菜单高度
		//30
		var height_toolbar=document.getElementById("toolDiv").offsetHeight;//grid工具栏高度
		Ext.getCmp('expInfogrid').setHeight(heightClient-gwyxt_height-30-height_toolbar);
		Ext.getCmp('tab').setHeight(heightClient-gwyxt_height-30);
		
		Ext.getCmp('expInfogrid').setWidth(document.getElementById("toolDiv").offsetWidth);
	}
    function verify(){
    	var count = 0;
    	var grid = Ext.getCmp("expInfogrid");
    	var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
    	var filePath = "";
    	var status="";
    	var grid = odin.ext.getCmp('expInfogrid');
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
    
    function refresh(){
    	radow.doEvent("btn1.onclick");
    }
	function download(){
		var filePath="";
		var id="";
    	var grid = odin.ext.getCmp('expInfogrid');
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
	function clickquery(){
		radow.doEvent("btn1.onclick");
	}
	function dataExp(){
		$h.openWin('dataOrgImp','pages.dataverify.DataOrgImp','HZB格式数据上报窗口',750,550, '','<%=request.getContextPath()%>');
	}
	function dataExp2(){
		$h.openWin('dataOrgImp2','pages.dataverify.SDataOrgImp','自定义格式数据上报窗口',750,550, '','<%=request.getContextPath()%>');
	}
	function unidataExp(){
		$h.openWin('dataOrgImpUni','pages.dataverify.DataOrgImpUni','通用格式数据上报窗口',750,550, '','<%=request.getContextPath()%>');
	}
	function exceldataExp(){
		$h.openWin('dataOrgExcelExp','pages.dataverify.DataExcelExport','Excel格式数据上报窗口',750,200,'','<%=request.getContextPath() %>');
	}
	function mysqlDataExp(){
		$h.openWin('dataOrgMysql','pages.dataverify.DataOrgMysql','MYSQL数据库上报窗口',750,338, '','<%=request.getContextPath()%>');
	}
	
	//删除记录
	function delimp(){
		radow.doEvent('delimpinfo');
	}
	
	function getCheckList(){
		radow.doEvent('getCheckList');
	}
	function getListInfo(){
		radow.doEvent("expInfogrid.dogridquery");
	}
</script>