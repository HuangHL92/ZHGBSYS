<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel" %>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
  <style>
 	.ui-progressbar {
		position: relative;
	}
	.progress-label {
		position: absolute;
		left: 50%;
		top: 4px;
		font-weight: bold;
		text-shadow: 1px 1px 0 #fff;
	} 

	</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>	
<odin:hidden property="grid9_totalcount"></odin:hidden> <!-- 身份证重复校验全部数据量 -->
<odin:hidden property="bsType"/>		<!-- 校验业务类型 -->
<odin:hidden property="b0111OrimpID"/>	<!-- 父页面传入的需要校验的单位编码 -->
<odin:hidden property="dbClickvel002"/>	<!-- 双击选中的错误信息 -->
<odin:hidden property="dbClickvel003"/>	<!-- 双击选中的错误信息 -->
<odin:hidden property="peopleid"/><!-- 人员校验时的人员ID -->
<odin:hidden property="groupid"/><!-- 机构校验时的机构ID -->
<div id="addSceneContent">
 <div id="tab0" class="x-hide-display">
 <div id="tooldiv"></div>
<div id="progressbar" style = "z-index : 1;display: none"><div class="progress-label"></div></div>
<table style="border:solid 0px !important">
	<tr align="justify">
		<%-- <odin:select2 property="a0163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否在职"  data="[0,'全部'],['1','在职']" value="1" />	 --%>	
			<odin:select2 property="vsc001" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校验方案" ></odin:select2>
	</tr>
	<tr>
		<td colspan="6">
			<odin:editgrid property="ruleGrid"  width="100%" url="/" >
			<odin:gridJsonDataModel   root="data" >
					<odin:gridDataCol name="checked"  />
					<odin:gridDataCol name="vru001"/>
					<odin:gridDataCol name="vru002"/>
					<odin:gridDataCol name="vru003"/>
					<odin:gridDataCol name="vru003_name"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="ruleGrid" align="center" 
						editor="checkbox" edited="true" dataIndex="checked" />
					<odin:gridColumn dataIndex="vru001" header="规则编码" hidden="true"></odin:gridColumn>
					<odin:gridEditColumn header="校验规则" width="400" dataIndex="vru002"  edited="false" editor="select" align="left" />
					<odin:gridEditColumn header="校验类型代码"  dataIndex="vru003"  edited="false" editor="text" align="center" hidden="true"/>
					<odin:gridEditColumn header="校验类型" width="100"  dataIndex="vru003_name"  edited="false" editor="text" align="left" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>

</div>

		
		<div id="tab1" class="x-hide-display">	
					<!--  grid 右半部分 -->
						<odin:toolBar property="errorDetailGridToolBar">
							<%-- <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;错误详细信息</h1>"></odin:textForToolBar>
							<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ></odin:textForToolBar>
							<odin:buttonForToolBar text="查看全部" id="allErrorDetail" cls="x-btn-text-icon"  icon="images/search.gif"  /> 
							<odin:separator /> --%>
							<odin:fill/>
							<odin:buttonForToolBar text="导出全部" id="expErrorDetail" handler="expExcelFromErrorDetailGrid" cls="x-btn-text-icon"   icon="images/icon/exp.png"  isLast="true"/>
							<%-- <odin:separator /> --%>
							<%-- <odin:buttonForToolBar isLast="true" text="导出校验结果综述" id="expSummarize" cls="x-btn-text-icon"   icon="images/icon/exp.png" /> --%>
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar" property="errorDetailGrid"   bbarId="pageToolBar" isFirstLoadData="false" forceNoScroll="true">
							<odin:gridJsonDataModel  > 
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel002_name" />
								<odin:gridDataCol name="vel002_b" />
								<odin:gridDataCol name="vel002" />
								<odin:gridDataCol name="vel003" />
								<odin:gridDataCol name="vel004" />
								<odin:gridDataCol name="vel005" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="vel010"  isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn dataIndex="vel001" header="错误信息id::主键 " align="left" width="85" editor="text"  edited="false"   hidden="true"/>
								<odin:gridColumn dataIndex="vel002_b" header="所属机构" align="left" width="120" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_name" header="机构" align="left" width="95" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002" header="错误主体ID" align="left" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel003" header="主体类别" align="center" width="50" editor="text" hidden="true" edited="false"  />
								<odin:gridColumn dataIndex="vel004" header="校验批次id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel005" header="业务类型" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel006"  header="错误信息" align="left"  width="390" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="vel010" header="操作人" align="center" width="100" editor="text"  edited="false"   hidden="true" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
							 	{
							        data:[]
							    }				
							</odin:gridJsonData>
						</odin:editgrid>
		</div>
		<div id="tab3" class="x-hide-display">	
						<odin:toolBar property="errorDetailGridToolBar2">
							<%-- <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;错误详细信息</h1>"></odin:textForToolBar>
							<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ></odin:textForToolBar>
							<odin:buttonForToolBar text="查看全部" id="allErrorDetail2" cls="x-btn-text-icon"  icon="images/search.gif"  /> 
							<odin:separator /> --%>
							<odin:fill/>
							<odin:buttonForToolBar text="保存列表" id="savelist" icon="images/icon/save.gif"></odin:buttonForToolBar>
							<odin:buttonForToolBar text="导出全部" id="expErrorDetail2" handler="expExcelFromErrorDetailGrid2" cls="x-btn-text-icon"   icon="images/icon/exp.png" isLast="true"/>
						<%-- 	<odin:buttonForToolBar isLast="true" text="" /> --%>
							<%-- <odin:buttonForToolBar isLast="true" text="导出校验结果综述" id="expSummarize" cls="x-btn-text-icon"   icon="images/icon/exp.png" /> --%>
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar2" property="errorDetailGrid2"   bbarId="pageToolBar" remoteSort="true" >
							<odin:gridJsonDataModel  > 
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel002_name" />
								<odin:gridDataCol name="vel002_sfz" />
								<odin:gridDataCol name="vel002_zw" />
								<odin:gridDataCol name="vel002" />
								<odin:gridDataCol name="vel003" />
								<odin:gridDataCol name="vel004" />
								<odin:gridDataCol name="vel005" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="vel010"  isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn dataIndex="vel001" header="错误信息id::主键 " align="center" width="85" editor="text"  edited="false"   hidden="true"/>
								<odin:gridColumn dataIndex="vel002_name" header="姓名" align="left" width="50" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_sfz" header="身份证号码" align="left" width="125" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="vel002_zw" header="职务" align="left" width="120" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002" header="错误主体ID" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel003" header="主体类别" align="center" width="50" editor="text" hidden="true" edited="false" hidden="true" />
								<odin:gridColumn dataIndex="vel004" header="校验批次id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel005" header="业务类型" align="center" width="100" editor="text"  edited="false"  hidden="true" />
								<odin:gridColumn dataIndex="vel006" header="错误信息" align="left" width="335" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel010" header="操作人" align="center" width="100" editor="text"  edited="false"   hidden="true" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
							 	{
							        data:[]
							    }				
							</odin:gridJsonData>
						</odin:editgrid>
		</div>
	 	<div id="tab2" class="x-hide-display">
			 <odin:editgrid property="errorRuleGrid"  autoFill="false"   bbarId="pageToolBar" >
				<odin:gridJsonDataModel  > 
					<odin:gridDataCol name="sv001" />
					<odin:gridDataCol name="sv002" />
					<odin:gridDataCol name="sv003" />
					<odin:gridDataCol name="sv004" />
					<odin:gridDataCol name="sv005" />
					<odin:gridDataCol name="sv006" isLast="true"/>	
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn/>
					<odin:gridColumn dataIndex="sv001" header="主键" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv002" header="出错规则主体" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv003" header="出错规则名称" align="center" width="600" editor="text"  edited="false"  />
					<odin:gridColumn dataIndex="sv004" header="出错校验类型" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv005" header="操作人" align="center" width="90" editor="text"  edited="false"  hidden="true" />
					<odin:gridColumn dataIndex="sv006" header="备用字段" align="center" width="150" editor="text"   edited="false"  hidden="true"   isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
				 	{
				        data:[]
				    }				
				</odin:gridJsonData>
			</odin:editgrid> 
		</div> 
</div>
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:textForToolBar text=""></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="impconfirmBtn"  text="接收"  icon="images/icon_photodesk.gif"/>
	<odin:buttonForToolBar id="btnSave"  text="执行校验" icon="images/icon/right.gif"  />
	<odin:buttonForToolBar id="rejectBtn"  text="拒绝"  icon="images/qinkong.gif" isLast="true"/>
	<%-- <odin:buttonForToolBar text="照片检测" icon="image/u45.png" id="imgVerify" tooltip="对权限内机构中的所有人员进行无照片检测" handler="imgVerify"/> --%>
	<%-- <odin:buttonForToolBar id="btnSaveUpdated"  text="保存校验标记" icon="images/save.gif"  isLast="true" /> --%>
</odin:toolBar>

<odin:window src="/blank.htm" id="refreshWin" width="560" height="450"
	maximizable="false" title="窗口" />
<odin:window src="/blank.htm" id="selectVerifyWin" width="580" height="500" title="校验选择窗口"/>
<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("subWinIdBussessId2").value=parent.Ext.getCmp(subWinId).initialConfig.ids; 
})	;
function expExcelFromErrorDetailGrid(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid', null, null,null, false);
	
}
function expExcelFromErrorDetailGrid2(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid2', null, null,null, false);
}
/* function exporterr(str){
	alert(str);
	w = window.open("ProblemDownServlet?method=downFilejy&prid="+str);
} */

function expExcelFromErrorGrid(){
	odin.grid.menu.expExcelFromGrid('errorGrid', null, null,null, false);
	
}
function expExcelFromErrorGrid9(){
	odin.grid.menu.expExcelFromGrid('errorGrid9', null, null,null, false);
	
}

//人员新增修改窗口窗口
var personTabsId=[];
function addTab(index,atitle,aid,src,forced,autoRefresh,errorInfo){
      var tab=parent.parent.tabs.getItem(aid);
      
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
    	  parent.parent.tabs.activate(tab);
    	  parent.parent.document.getElementById('I'+aid).contentWindow.chengTabActive(index);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
      	personTabsId.push(aid);
        parent.parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        tabType:index,
        errorInfo:errorInfo,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'&a0000='+aid+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',//tongzj  添加URL传参
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
      parent.Ext.getCmp('dataVerifyWin').close();
 }
 
 function addOrgTab(oid,atitle,aid,src,forced,autoRefresh,errorInfo){
	var tab=parent.parent.tabs.getItem(aid);
	var tabid = parent.parent.tabs.getActiveTab().id;
	
	if (forced)
		aid = 'R'+(Math.random()*Math.random()*100000000);
	if (tab && !forced){ 
		if(tabid == aid){
			radow.doEvent("closeWindow");
		}
		parent.parent.tabs.activate(tab);
		parent.parent.document.getElementById('I'+aid).contentWindow.radow.doEvent('querybyid',oid);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
	}else{
    	personTabsId.push(aid);
        parent.parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        orgid:oid,
        errorInfo:errorInfo,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'&a0000='+aid+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
	 parent.Ext.getCmp('dataVerifyWin').close();
 }

		var progressbar = $( "#progressbar" ),
			progressLabel = $( ".progress-label" );

		progressbar.progressbar({
			value: false,
			change: function() {
				progressLabel.text( progressbar.progressbar( "value" ) + "%" );
			},
			complete: function() {
				progressLabel.text( "结果加载" );
			}
		});
		
		 function progress(val,processId) {
				if(val<0){
					val = 0;
				}

				progressbar.progressbar( "value", val  );
				setTimeout(function(){radow.doEvent('queryBar',processId+'@'+val);}, 1000); 	
		} 
		 
/**2017.04.26yinl加载提示**/
var sw = 1;
function setwaiting(){
	var texts = progressLabel.text();
	if(texts == null || texts == "" || texts == "完成！"){
		return ;
	}
	if(sw == 7){
		sw = 1;
		texts = texts.replace(/\./g,"");
	}
	sw++;
	texts = texts+".";
	progressLabel.text(texts);
}
window.setInterval("setwaiting()",400);

function setprogressLabelnull(){
	progressLabel.text("完成！");
}


 Ext.onReady(function() {
		
		  var side_resize=function(){
			//页面调整
			document.getElementById("addSceneContent").style.width = document.body.clientWidth + "px";
			document.getElementById("addSceneContent").style.height = document.body.clientHeight + "px";
			//document.getElementById("tooldiv").style.width = document.body.clientWidth-1 + "px";
			/* Ext.getCmp('errorDetailGrid').setWidth(document.body.clientWidth);
		      Ext.getCmp('errorDetailGrid').setHeight(document.body.clientHeight-82);
		      Ext.getCmp('errorDetailGrid2').setWidth(document.body.clientWidth-2);
		      Ext.getCmp('errorDetailGrid2').setHeight(document.body.clientHeight-83); */
		      Ext.getCmp('ruleGrid').setWidth(document.body.clientWidth-7);
		      Ext.getCmp('ruleGrid').setHeight(document.body.clientHeight-90);
		      //Ext.getCmp('errorGrid').setWidth(283);
		     // Ext.getCmp('errorGrid').setHeight(464);
		     	 
		      
		      
		      var viewSize = Ext.getBody().getViewSize();
		      Ext.getCmp('errorDetailGrid2').setHeight(viewSize.height-85);
			  var gridobj = document.getElementById('forView_errorDetailGrid2');
		      var grid_pos = $h.pos(gridobj);
		      Ext.getCmp('errorDetailGrid2').setWidth(viewSize.width-grid_pos.left);
		      
		      Ext.getCmp('errorDetailGrid').setHeight(viewSize.height-85);
		      Ext.getCmp('errorDetailGrid').setWidth(viewSize.width-grid_pos.left);
		      Ext.getCmp('errorRuleGrid').setWidth(viewSize.width-grid_pos.left);
		      Ext.getCmp('errorRuleGrid').setHeight(viewSize.height-55);
		}
			side_resize();  
			
	      window.onresize=side_resize;   
	      
	      
	      
	      
	      var mytab = new Ext.TabPanel({
              renderTo: 'addSceneContent',
              id:'tab',
              autoWidth:true,
              activeTab: 0,//激活的页数
              frame: false, 
              border : false, 
              items: [
             {title:"准备校核",contentEl:"tab0",id:"tab0"} ,
             {title:"单位校核结果" ,contentEl:'tab1',id:"tab1"},
             {title:"人员校核结果" ,contentEl:'tab3',id:"tab3"},
             {title:"校验规则出错结果" ,contentEl:'tab2',id:"tab2"} 
             ],
             listeners:{
            	 'tabchange':function(tab){
            		 if(tab.getActiveTab().id=='tab3'){
            			if(Ext.getCmp('errorDetailGrid2').store.totalLength<1){
            				radow.doEvent('errorDetailGrid2.dogridquery');
            			}
            			 
            		 };
            		if(tab.getActiveTab().id=='tab1'){
            			if(Ext.getCmp('errorDetailGrid').store.totalLength<1){
            				radow.doEvent('errorDetailGrid.dogridquery');
            			}
            		};
            	 }
             }

          });

	});	

 function setMsg(value, params, record,rowIndex,colIndex,ds){
	 var size=value.length;
	 var row=size/33;
	   row=Math.ceil(row);
	 return "<div onload='this.parentNode.className=this.parentNode.className.replace(\"x-grid3-cell-inner\",\"\")' >"+value+"</div>";
 }
 
  Ext.onReady(function(){
	 var egrid = Ext.getCmp("errorDetailGrid");
	 var estore = egrid.getStore();
	 estore.on({
		 load:function(s){
			 var view =egrid.getView();
			 var rows =egrid.getView().getRows();
			 for(var i=0; i<rows.length; i++){
				 for(var j=0;j<10;j++){
					 var cell = view.getCell(i,j);
					 cell.innerHTML = cell.innerHTML.replace("x-grid3-cell-inner","");
				 }
			 }
			
		 }
	 });
	 
	 
	  var egrid2 = Ext.getCmp("errorDetailGrid2");
	 var estore2 = egrid2.getStore();
	 estore2.on({
		 load:function(s){
			 var view =egrid2.getView();
			 var rows =egrid2.getView().getRows();
			 for(var i=0; i<rows.length; i++){
				 for(var j=0;j<10;j++){
					 var cell = view.getCell(i,j);
					 cell.innerHTML = cell.innerHTML.replace("x-grid3-cell-inner","");
				 }
			 }
		 }
	 }); 
	
 }); 
 
 function verify(vpid,b0111,ruleids,bsType,peopleid,groupid){
	var param={'vpid':vpid,'b0111':b0111,'ruleids':ruleids,'bsType':bsType,'peopleid':peopleid,'groupid':groupid,'method':'verify'};
	//console.log(param);
	ShowCellCover('start','系统提示','正在校验...');
	  $.ajax({
	        type: "POST",
	        url:"<%=request.getContextPath()%>/CustomExcelServlet",
	        data: param ,//$('#excelForm').serialize(),// 你的formid
	        error: function(request) {
	            //$("#orginfo").val(data);
	            //ShowCellCover('failure','系统提示','汇总失败');
	        },
	        success: function(data) {
	        
	        	  radow.doEvent("showgrid",vpid);
	          
	        }
	    }); 
 }
 
 
 function ShowCellCover(elementId, titles, msgs){	
 	Ext.MessageBox.buttonText.ok = "关闭";
 	if(elementId.indexOf("start") != -1){
 	
 		Ext.MessageBox.show({
 			title:titles,
 			msg:msgs,
 			width:300,
 	        height:300,
 			closable:false,
 		//	buttons: Ext.MessageBox.OK,		
 			modal:true,
 			progress:true,
 			wait:true,
 			animEl: 'elId',
 			increment:5, 
 			waitConfig: {interval:150}
 			//,icon:Ext.MessageBox.INFO        
 		});
 	}else if(elementId.indexOf("success") != -1){
 			Ext.MessageBox.show({
 				title:titles,
 				msg:msgs,
 				width:300,
 		        height:300,
 		        modal:true,
 				closable:true,
 				//icon:Ext.MessageBox.INFO,  
 				buttons: Ext.MessageBox.OK
 			});
 			/*
 			setTimeout(function(){
 					Ext.MessageBox.hide();
 			, 2000);
 			*/
 					
 	}else if(elementId.indexOf("failure") != -1){
 			Ext.MessageBox.show({
 				title:titles,
 				msg:msgs,
 				width:300,
 				modal:true,
 		        height:300,
 				closable:true,
 				//icon:Ext.MessageBox.INFO,
 				buttons: Ext.MessageBox.OK		
 			});
 			/*
 			setTimeout(function(){
 					Ext.MessageBox.hide();
 			}, 2000);
 			*/
 	}else {
 			Ext.MessageBox.show({
 				title:titles,
 				msg:msgs,
 				width:300,
 				modal:true,
 		        height:300,
 				closable:true,
 				//icon:Ext.MessageBox.INFO,
 				buttons: Ext.MessageBox.OK		
 			});
 		}

 	
 }
	</script>
