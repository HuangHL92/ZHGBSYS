<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	
	String ctxPath = request.getContextPath();
%>
	<style>


</style>

<div style="height: 34;">
	<div style="float: left;"><img class="gb-title-img" alt="" width="7" height="30" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png"></div>
	<div style="float: left;"><span style="font-size: 22px;">干部调配人选条件</span></div>
</div>
<div style="float: left;position: relative;">
	<table width="240" cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5;height: 26;">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 14px">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 14px">包含下级</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
</div>

<div  style="float: left;">
	<div id="wayDiv">
		<table >
			<tr >
				<odin:select2 property="waytype" label="&nbsp;&nbsp;&nbsp;&nbsp;重点岗位类别" codeType="ZDGWTYPE" width="120" onchange="changeType"></odin:select2>
				<odin:select2 property="zdgwid" label="&nbsp;&nbsp;重点岗位" width="150" onchange="changeZdgw"></odin:select2>
				<odin:select2 property="wayid" label="&nbsp;&nbsp;方案" width="100" onchange="changeWay"></odin:select2>
				<td width="40px;"></td>
				<odin:textEdit property="a0101" label="&nbsp;&nbsp;&nbsp;&nbsp;姓名" width="150" ></odin:textEdit>
				<td width="5px;"></td>
				<td><odin:button text="姓名查询"  handler="queryA0101"></odin:button></td>
				<td width="15px;"></td>
				<td><odin:button text="快速查询"  handler="openRenXuanTianJian2"></odin:button></td>
			</tr>
		</table>
	</div>
<div id="conditionArea" style="height: 240; overflow-y: scroll;">
		<div id="jbDiv">
			<table style="width: 100%;height: 94%;">
				<tr>
					<td>
						<table style="width: 99%;height: 100%;">
							
							<tr>
								<td rowspan='2' width="250">
									<odin:groupBox title="待选条件列表"  >
										<table style="width: 100%;height: 100%;">
											<tr>
												<odin:select2 property="query_type"  label="待选条件类别" data="['9', '常用条件'],['1', '资格条件'],['2', '约束性条件'],['3', '岗位必要条件'],['4', '专业要求'],['5', '优先性条件'],['6', '班子需要'],['7', '来源']" width="300" onchange="changeDXMX"></odin:select2>
											</tr>
											<tr>
												<td colspan="2">
													<odin:editgrid2 property="DXcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="480" autoFill="true"  pageSize="50" ddGroup="dxtomx" url="/">
														<odin:gridJsonDataModel>
															<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
															<odin:gridDataCol name="dcondname" />
															<odin:gridDataCol name="dconddesc"/>
															<odin:gridDataCol name="mxtype"/>
															<odin:gridDataCol name="dxmxid"/>
														</odin:gridJsonDataModel>
														<odin:gridColumnModel>
															<odin:gridRowNumColumn></odin:gridRowNumColumn>
															<odin:gridEditColumn2 dataIndex="dcondname" width="120" header="条件名" editor="text" edited="false" align="center" />
															<odin:gridEditColumn2 dataIndex="dconddesc" width="170" header="条件描述" editor="text" edited="false" align="center"  hidden="true" />
															<odin:gridEditColumn2 dataIndex="dxmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" isLast="true" />
															<%-- <odin:gridEditColumn2 dataIndex="addcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="addconditon" />
														 --%></odin:gridColumnModel>
													</odin:editgrid2>  
												</td>
											</tr>
										</table>
									</odin:groupBox>
								</td>
								<td  width="600">
									<odin:groupBox title="资格条件列表" >
										<odin:editgrid2 property="ZGcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="220" autoFill="true"  pageSize="50" url="/">
											<odin:gridJsonDataModel>
												<odin:gridDataCol name="zgcondname" />
												<odin:gridDataCol name="zgconddesc"/>
												<odin:gridDataCol name="zgcondcs1"/>
												<odin:gridDataCol name="zgcondcs2"/>
												<odin:gridDataCol name="zgtjtype"/>
												<odin:gridDataCol name="zgmxid"/>
											</odin:gridJsonDataModel>
											<odin:gridColumnModel>
												<odin:gridRowNumColumn></odin:gridRowNumColumn>
												<odin:gridEditColumn2 dataIndex="zgcondname" width="100" header="资格条件名" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="zgconddesc" width="130" header="资格条件描述" editor="text" edited="false" hidden="true" align="center" />
												<odin:gridEditColumn2 dataIndex="zgcondcs1" width="50" header="关系" editor="select" codeType="TC05" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="zgcondcs2" width="120" header="数值" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="zgmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" />
												<odin:gridEditColumn2 dataIndex="delzgcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="delzgcond" />
											</odin:gridColumnModel>
										</odin:editgrid2>  
									</odin:groupBox>
								</td>
							</tr>
							<tr>
								<td  width="600">
									<odin:groupBox title="权重条件列表" >
										<odin:editgrid2 property="QZcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="220" autoFill="true"  pageSize="50" url="/">
											<odin:gridJsonDataModel>
												<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
												<odin:gridDataCol name="qzcondname" />
												<odin:gridDataCol name="qzconddesc"/>
												<odin:gridDataCol name="qzcondcs1"/>
												<odin:gridDataCol name="qzcondcs2"/>
												<odin:gridDataCol name="qztjtype"/>
												<odin:gridDataCol name="qzgrade"/>
												<odin:gridDataCol name="qzmxid"/>
											</odin:gridJsonDataModel>
											<odin:gridColumnModel>
												<odin:gridRowNumColumn></odin:gridRowNumColumn>
												<odin:gridEditColumn2 dataIndex="qzcondname" width="100" header="权重条件名" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="qzconddesc" width="130" header="权重条件描述" editor="text" hidden="true" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="qzcondcs1" width="50" header="关系" editor="select" codeType="TC05" edited="false"  align="center" />
												<odin:gridEditColumn2 dataIndex="qzcondcs2" width="120" header="数值" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="qzgrade" width="40" header="分数" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="qzmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true"  />
												<odin:gridEditColumn2 dataIndex="delqzcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="delqzcond" />
											</odin:gridColumnModel>
										</odin:editgrid2>  
									</odin:groupBox>
									</td>
								</tr>
								
							</table>
						</td>
					</tr>
				</table>
		</div>

	
</div>	
	<div id="bottomDiv" style="width: 100%;" align="center">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td width="20%"></td>
				<td>
					<odin:button text="清除条件" property="clearCon2" handler="clearConbtn2"></odin:button>
				</td>
				<td>
					<odin:button text="开始查询" property="mQuery" handler="dosearch"/>
				</td>
				<td width="20%"></td>
			</tr>
		</table>
	</div>

</div>

<odin:hidden property="fxyp00"/>
<odin:hidden property="queryType"/>
<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="sql"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function(){
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	if(parent.Ext.getCmp(subWinId).initialConfig.queryType){
		document.getElementById("queryType").value=parent.Ext.getCmp(subWinId).initialConfig.queryType;
	}else{
		$('#queryType').val('');
	}
	
	window.onresize=resizeframe;
	resizeframe();
	Ext.getCmp('DXcondGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var dxmxid=rc.data.dxmxid;
		var mxtype=rc.data.mxtype;
		if(mxtype=='41'||mxtype=='931'){
			dxmxid=dxmxid+"##1";
			radow.doEvent('addQZMX',dxmxid);
		}else if(mxtype=='71'||mxtype=='932'){
			dxmxid=dxmxid+"##1";
			radow.doEvent('addZGMX',dxmxid);
		}else{
			radow.doEvent('addZGMX',dxmxid);
		}
	 });
	Ext.getCmp('ZGcondGrid').on('rowdblclick',function(gridobj,index,e){
 		var rc = gridobj.getStore().getAt(index);
 		if(rc.data.zgtjtype=='3'){
			return;
 		}else{
 			$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'zg',id:rc.data.zgmxid,cs:rc.data.zgcondcs1,query:'_query'},g_contextpath);
 		}
	 });
	 Ext.getCmp('QZcondGrid').on('rowdblclick',function(gridobj,index,e){
			var rc = gridobj.getStore().getAt(index);
			$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'qz',id:rc.data.qzmxid,cs:rc.data.qzcondcs1,query:'_query'},g_contextpath);
	
	 });
	 GridDrop2G.init("ZGcondGrid");
	 
	 new Ext.dd.DropTarget(Ext.getCmp('ZGcondGrid').container,GridDrop2G);
	 GridDrop2G.init("QZcondGrid");
	 new Ext.dd.DropTarget(Ext.getCmp('QZcondGrid').container,GridDrop2G);
	 
})


function tfckbox(checkboxName,hiddenName){
	var checkboxes = document.getElementsByName(checkboxName);
	var hiddenValue = "";
	for (i=0; i<checkboxes.length; i++) {  
        if (checkboxes[i].checked) {  
        	hiddenValue = hiddenValue + checkboxes[i].value+',';
        }  
    }
	if(hiddenValue.length>0){
		hiddenValue = hiddenValue.substring(0,hiddenValue.length-1);
	}
	document.getElementById(hiddenName).value = hiddenValue;
}

function dosearch(){
	document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	/* tfckbox('xla0801b','xla0801bv');
	tfckbox('xwa0901b','xwa0901bv');
	tfckbox('a0601','a0601v'); */
	//var param;
	//radow.doEvent('mQueryonclick',param);
	radow.doEvent('startQuery');
}



function collapseGroupWin(fxyp00){
	 var newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
	if(!newWin_){
	}else{
		//newWin_.collapse(false); 
		newWin_.hide();
		//realParent.infoSearch(fxyp00,true);
		//打开 查找有关人选页面
		realParent.openYouGuanRenXuann(fxyp00,document.getElementById("queryType").value);
	} 
	//window.close();
}




function resizeframe(){
	var conditionArea = document.getElementById("conditionArea");
	var treediv = document.getElementById("tree-div");
	var viewSize = Ext.getBody().getViewSize();
	conditionArea.style.width = viewSize.width-240;
	conditionArea.style.height = viewSize.height-100;
	//var pos = $h.pos(document.getElementById("ltb"));
	//alert(viewSize.height-pos.top);
	//document.getElementById("bottomDiv").style.marginTop = viewSize.height - pos.top-62;
	treediv.style.height = viewSize.height-62;
	
	//alert(conditionArea.parentNode.parentNode.style.width);
	conditionArea.parentNode.parentNode.style.width=viewSize.width;
}


function reloadtree(){
	var treep = Ext.getCmp('group');
	var rootNode = treep.getRootNode();
	rootNode.reload();
	rootNode.expand();
}

function gbmcQueryBtn(){
	$h.openPageModeWin('gbmcQueryMntp','pages.customquery.gbmcQuery_mntp','干部查询列表',650,480,'','<%=request.getContextPath()%>',window);
}


function clearConbtn(condi){
	var queryType = parent.Ext.getCmp(subWinId).initialConfig.queryType+"";
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	document.getElementById("queryType").value=queryType;
	//radow.doEvent('clearReset');
	radow.doEvent('initX','');
}

function clearConbtn2(condi){
	radow.doEvent('clearReset');
}

function addconditon(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"addconditon2('"+record.get("dxmxid")+"','1');\">资格</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"addconditon2('"+record.get("dxmxid")+"','2');\">权重</a></font>";
}

function addconditon2(dxmxid,type){
	if(type==1){
		radow.doEvent('addZGMX',dxmxid);
	}else if(type==2){
		radow.doEvent('addQZMX',dxmxid);
	}
}

function delzgcond(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletecond('"+record.get("zgmxid")+"','1');\">删除</a></font>";
}

function delqzcond(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletecond('"+record.get("qzmxid")+"','2');\">删除</a></font>";
}

function deletecond(mxid,type){
	if(type==1){
		radow.doEvent('deleteZGMX',mxid);
	}else if(type==2){
		radow.doEvent('deleteQZMX',mxid);
	}
}


function changeType(){
	radow.doEvent('changeType');
}

function changeZdgw(){
	radow.doEvent('changeZdgw');
}

function changeWay(){
	radow.doEvent('changeWay');
}

function changeDXMX(){
	radow.doEvent('DXcondGrid.dogridquery');
}

function updateZDGWZG(){
	radow.doEvent('ZGcondGrid.dogridquery');
}

function updateZDGWQZ(){
	radow.doEvent('QZcondGrid.dogridquery');
}

var GridDrop2G = {
	    ddGroup : 'dxtomx',
	    copy : false,
	    init: function(gridid) {
	    	this.gridid = gridid;
	    },
	    notifyEnter : function(dd, e, data){
	      delete this.dropOK;
	      var rows = data.selections;
	         
	        //遍历store
	        for ( i=0; i<rows.length; i++){
	            var rowData = rows[i];
	            
	            
	        }
	      
	      this.dropOK=true;
	        return this.dropAllowed;
	    },
	    notifyOver : function(dd, e, data){
	      return this.dropOK ? this.dropAllowed : this.dropNotAllowed;
	    },
	    notifyDrop : function(dd,e,data){
	      if(this.dropOK){
	        //选中了多少行
	            var rows = data.selections;
	            //拖动一个人的时候，如果该人有其他职务，也加进去。
	            if(this.gridid=='ZGcondGrid'){
		            for ( ind=0; ind<rows.length; ind++){
		              var rowData = rows[ind];
		              var dxmxid=rowData.data.dxmxid;
		              radow.doEvent('addZGMX',dxmxid);
		            }
	            }
	            if(this.gridid=='QZcondGrid'){
		            for ( ind=0; ind<rows.length; ind++){
		              var rowData = rows[ind];
		              var dxmxid=rowData.data.dxmxid;
		              radow.doEvent('addQZMX',dxmxid);
		            }
	            }
	            if(typeof callback=='function'){
	                callback(pgrid);
	            }
	      }
	        

	    }
	}

function queryA0101(){
	radow.doEvent('queryA0101');
}

function openRenXuanTianJian2(){
	var fxyp00=document.getElementById("fxyp00").value;
	newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
  	if(newWin_){
   		newWin_.hide(); 
   		realParent.openRenXuanTiaoJian2(fxyp00);
  	}
}

</script>


<%@include file="/pages/customquery/otjs.jsp" %>

