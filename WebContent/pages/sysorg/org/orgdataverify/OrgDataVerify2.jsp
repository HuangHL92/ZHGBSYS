<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerify2PageModel" %>
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
	.x-grid3-row TD{
		vertical-align: middle !important;
		font-size:12px;
		text-align: center;
	}

	</style>
<odin:hidden property="grid9_totalcount"></odin:hidden> <!-- ���֤�ظ�У��ȫ�������� -->
<odin:hidden property="bsType"/>		<!-- У��ҵ������ -->
<odin:hidden property="b0111OrimpID"/>	<!-- ��ҳ�洫�����ҪУ��ĵ�λ���� -->
<odin:hidden property="dbClickvel002"/>	<!-- ˫��ѡ�еĴ�����Ϣ -->
<odin:hidden property="dbClickvel003"/>	<!-- ˫��ѡ�еĴ�����Ϣ -->
<odin:hidden property="peopleid"/><!-- ��ԱУ��ʱ����ԱID -->
<div id="addSceneContent">
	<div id="tooldiv"></div>
	<div  style="text-align:center">
	<table style="margin: auto; ">
			<tr>
			<%-- <label style="color:red;">
				<td>
					<odin:checkbox property="ignoreCheckbox" label="���Դ�����ʾ" onclick="ignoreChoose(this)"  style="zoom:90%;vertical-align:-3px;"></odin:checkbox>
				</td>
			</label> --%>
			<odin:select2 property="a0163" label="�Ƿ���ְ"  data="[0,'ȫ��'],['1','��ְ']" value="1" width="60"/>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<odin:select2 property="vsc001" label="У�鷽��"  width="100"/>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<tags:PublicTextIconEdit4  width="520" property="searchDeptBtn" label="ѡ�����" readonly="true" codetype="orgTreeJsonData"/>
			<div id="progressbar" style = "z-index : 1;display: none"><div class="progress-label"></div></div>
			</tr>
	</table>
	</div>
 	<div id="tab0" class="x-hide-display">
<table style="border:solid 0px !important">
	<tr>
		<td colspan="6">
			<odin:editgrid property="ruleGrid"  width="100%" url="/" forceNoScroll="true">
				<odin:gridJsonDataModel root="data" >
					<odin:gridDataCol name="checked"  />
					<odin:gridDataCol name="vru001"/>
					<odin:gridDataCol name="vru002"/>
					<odin:gridDataCol name="vru003"/>
					<odin:gridDataCol name="vru003_name"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="ruleGrid" align="center" editor="checkbox" edited="true" dataIndex="checked" />
					<odin:gridColumn dataIndex="vru001" 			header="�������" hidden="true"></odin:gridColumn>
					<odin:gridEditColumn dataIndex="vru002"  		header="У�����" width="400" edited="false" editor="select" align="left" />
					<odin:gridEditColumn dataIndex="vru003"  		header="У�����ʹ���"  edited="false" editor="text" align="center" hidden="true"/>
					<odin:gridEditColumn dataIndex="vru003_name"	header="У������" width="100"   edited="false" editor="text" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>

</div>

		
		<div id="tab1" class="x-hide-display">	
					<!--  grid �Ұ벿�� -->
						<odin:toolBar property="errorDetailGridToolBar">
							<odin:fill/>
							<odin:buttonForToolBar text="����ȫ��" id="expErrorDetail" handler="expExcelFromErrorDetailGrid" cls="x-btn-text-icon"   icon="image/btn_upload.png"  isLast="true"/>
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar" property="errorDetailGrid"  width="100%" bbarId="pageToolBar" isFirstLoadData="false" forceNoScroll="true">
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
								<odin:gridRowNumColumn ></odin:gridRowNumColumn>
								<odin:gridColumn dataIndex="vel002_b" 		header="��������" align="center" renderer="alignLeft" width="120" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_name"  	header="����" align="center" renderer="alignLeft" width="95" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel006"  		header="������Ϣ" align="center" renderer="alignLeft" width="390" editor="text"  edited="false" isLast="true"/>
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
							<odin:fill/>
							<odin:buttonForToolBar text="�����б�" id="savelist" icon="images/icon/save.gif"></odin:buttonForToolBar>
							<odin:buttonForToolBar text="����ȫ��" id="expErrorDetail2" handler="expExcelFromErrorDetailGrid2" cls="x-btn-text-icon"   icon="image/btn_upload.png"  isLast="true" />
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar2" property="errorDetailGrid2"  autoFill="true"  bbarId="pageToolBar" remoteSort="true" forceNoScroll="true">
							<odin:gridJsonDataModel id="vel001" root="data" totalProperty="totalCount" > 
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel002_name" />
								<odin:gridDataCol name="vel002_sfz" />
								<odin:gridDataCol name="vel002_zw" />
								<odin:gridDataCol name="vel002" />
								<odin:gridDataCol name="vel003" />
								<odin:gridDataCol name="vel004" />
								<odin:gridDataCol name="vel005" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="vel011" />
								<odin:gridDataCol name="vel010"  isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn dataIndex="vel002_name" 	header="����" align="center" width="50" editor="text"  edited="false"  />
								<odin:gridColumn dataIndex="vel002_sfz" 	header="���֤����" align="center" width="130" editor="text"  edited="false"/>
								<odin:gridColumn dataIndex="vel002_zw" 		header="ְ��" align="center" renderer="alignLeft" width="125" editor="text"  edited="false" />
								<odin:gridEditColumn2 dataIndex="vel006" 	header="������Ϣ" align="center" renderer="alignLeft" width="300" editor="text"  edited="false"/>
								<odin:gridEditColumn  dataIndex="vel011"  	header="����" align="center"  width="50"  editor="text"  edited="false"  renderer="ignoreForGrid" sortable="false" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
							 	{
							        data:[]
							    }				
							</odin:gridJsonData>
						</odin:editgrid>
		</div>
		
		
<!-- ========================lxl��ʼ================================= -->
		<div id="tab4" class="x-hide-display">	
						<odin:toolBar property="errorDetailGridToolBar4">
							<odin:fill/>
							<odin:buttonForToolBar text="ȡ������" id="cancelIgnore" icon="image/icon040a3.gif"></odin:buttonForToolBar>
							<odin:buttonForToolBar text="����ȫ��" id="expErrorDetail2" handler="expExcelFromErrorDetailGrid4" cls="x-btn-text-icon"   icon="images/icon/exp.png" isLast="true" />
						</odin:toolBar>
						<odin:editgrid topBarId="errorDetailGridToolBar4" property="errorDetailGrid4"  groupCol="a0101" grouping="true" remoteSort="true" forceNoScroll="true">
							<odin:gridJsonDataModel  > 
								<odin:gridDataCol name="checked"  />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="vel001" />
								<odin:gridDataCol name="vel009" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0184" />
								<odin:gridDataCol name="vel006" />
								<odin:gridDataCol name="detail"   isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridColumn header="selectall" gridName="errorDetailGrid4" align="center"  editor="checkbox" edited="true" dataIndex="checked" />
								<odin:gridColumn dataIndex="a0101" 		header="����" align="center" width="50" editor="text"  edited="false"/>
								<odin:gridColumn dataIndex="a0184" 		header="���֤����" align="center" width="140" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="vel006" 	header="����" align="center" width="300" editor="text"  edited="false" />
								<odin:gridColumn dataIndex="detail" 	header="����" align="center" renderer="alignLeft" width="130" editor="text"  edited="false" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
							 	{
							        data:[]
							    }				
							</odin:gridJsonData>
						</odin:editgrid>
		</div>
<!-- ========================lxl����================================= -->
		<div id="tab2" class="x-hide-display">
			 <odin:editgrid property="errorRuleGrid"  autoFill="false"   bbarId="pageToolBar" forceNoScroll="true">
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
					<odin:gridColumn dataIndex="sv001" header="����" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv002" header="�����������" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv003" header="�����������" align="center" width="600" editor="text"  edited="false"  />
					<odin:gridColumn dataIndex="sv004" header="����У������" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="sv005" header="������" align="center" width="90" editor="text"  edited="false"  hidden="true" />
					<odin:gridColumn dataIndex="sv006" header="�����ֶ�" align="center" width="150" editor="text"   edited="false"  hidden="true"   isLast="true"/>
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
	<odin:buttonForToolBar text="����У�鱨��" id="DownloadVerificationReport" handler="DownloadVerificationReport" cls="x-btn-text-icon"   icon="images/icon/imp.gif" />
	<odin:fill/>
	<%-- <odin:buttonForToolBar text="��Ƭ���" icon="image/u45.png" id="imgVerify" tooltip="��Ȩ���ڻ����е�������Ա��������Ƭ���"/> --%>
	<odin:buttonForToolBar id="ArrangeBtn" handler='Arrange' text="��������" icon="image/icon040a15.gif"/>
	<odin:buttonForToolBar id="btnSave"  text="ִ��У��" icon="images/icon/right.gif" isLast="true"/>
	<%-- <odin:buttonForToolBar id="btnSaveUpdated"  text="����У����" icon="images/save.gif"  isLast="true" /> --%>
</odin:toolBar>

<odin:hidden property="canDownloadVerificationReport" value="0"/>
<odin:window src="/blank.htm" id="refreshWin" width="560" height="450"
	maximizable="false" title="����" />
<odin:window src="/blank.htm" id="selectVerifyWin" width="580" height="500" title="У��ѡ�񴰿�"/>

<script type="text/javascript">


 function ignoreForGrid(value, params, record, rowIndex, colIndex, ds){
	return "<a  href=\"javascript:radow.doEvent('ignore','"+record.get('vel002')+"');\">����</a>";
}
//=========================lxl����=========================
function showOrgDataVerifyNew(){
	$h.openWin('findById','pages.sysorg.org.orgdataverify.OrgDataVerifyNew','���Դ���',800,600,null,contextPath,null,{maximizable:false,resizable:false});
}
function  refreshGrid(){
	radow.doEvent('errorDetailGrid2.dogridquery');
	radow.doEvent('errorDetailGrid4.dogridquery');
	//ˢ������������Ϣ��ҳ��
}

// zxw У�˱���
function DownloadVerificationReport(){
	var cdvr = document.getElementById("canDownloadVerificationReport").value;
	if(cdvr != '0'){
		radow.doEvent('canDownloadVerificationReport');
	}else{
		$h.alert('ϵͳ��ʾ��', '���Ƚ���У�ˣ�',null,180);
	}
}

function Arrange(){
	 $h.openWin('OrgDataVerifyArrange','pages.sysorg.org.orgdataverify.OrgDataVerifyArrange','��������',500,260,null,contextPath,null,{maximizable:false,resizable:false});
}
function expExcelFromErrorDetailGrid(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid', '����У�˽����Ϣ', null,null, false);
	
}
function expExcelFromErrorDetailGrid2(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid2', '��ԱУ�˽����Ϣ', null,null, false);
}
function expExcelFromErrorDetailGrid4(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid4', '��������Ա��Ϣ', null,null, false);
}


function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=parent.tabs.getItem(aid);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  //alert(Ext.urlEncode({'asd':'����'}));
    	src = src+'&'+Ext.urlEncode({'a0000':aid});
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
	    },
	    closable:true
        }).show();  
		
      }
      parent.Ext.getCmp('DataVerifyWin').close();
    }

 

		var progressbar = $( "#progressbar" ),
			progressLabel = $( ".progress-label" );

		progressbar.progressbar({
			value: false,
			change: function() {
				progressLabel.text( progressbar.progressbar( "value" ) + "%" );
			},
			complete: function() {
				progressLabel.text( "�������" );
			}
		});
		
		 function progress(val,processId) {
				if(val<0){
					val = 0;
				}

				progressbar.progressbar( "value", val  );
				setTimeout(function(){radow.doEvent('queryBar',processId+'@'+val);}, 1000); 	
		} 
		 
/**2017.04.26yinl������ʾ**/
var sw = 1;
function setwaiting(){
	var texts = progressLabel.text();
	if(texts == null || texts == "" || texts == "��ɣ�"){
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
	progressLabel.text("��ɣ�");
}

function checkButton(){
	var canDownloadVerificationReport = document.getElementById("canDownloadVerificationReport").value;
	if(canDownloadVerificationReport=='0'){
		Ext.getCmp("DownloadVerificationReport").hide();
	}else{
		Ext.getCmp("DownloadVerificationReport").show();
	}
}

 Ext.onReady(function() {
		  var side_resize=function(){
			//ҳ�����
			document.getElementById("addSceneContent").style.width = document.body.clientWidth;
			document.getElementById("addSceneContent").style.height = document.body.clientHeight + "px";
		      Ext.getCmp('ruleGrid').setWidth(document.body.clientWidth-6);
		      Ext.getCmp('ruleGrid').setHeight(document.body.clientHeight-90);
		      var viewSize = Ext.getBody().getViewSize();
		      Ext.getCmp('errorDetailGrid2').setHeight(viewSize.height-85);
		      Ext.getCmp('errorDetailGrid4').setHeight(viewSize.height-85);
			  var gridobj = document.getElementById('forView_errorDetailGrid2');
			  var gridobj = document.getElementById('forView_errorDetailGrid4');
		      var grid_pos = $h.pos(gridobj);
		      Ext.getCmp('errorDetailGrid2').setWidth(viewSize.width-grid_pos.left);
		      Ext.getCmp('errorDetailGrid4').setWidth(viewSize.width-grid_pos.left);
		      
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
              activeTab: 0,//�����ҳ��
              frame: false, 
              border : false, 
              items: [
             {title:"׼��У��",contentEl:"tab0",id:"tab0"} ,
            /*  {title:"��λУ�˽��" ,contentEl:'tab1',id:"tab1"}, */
             {title:"��ԱУ�˽��" ,contentEl:'tab3',id:"tab3"},
             {title:"����У�˽����Ϣ" ,contentEl:'tab4',id:"tab4"},
             {title:"У����������" ,contentEl:'tab2',id:"tab2" }
             ],
             listeners:{
            	 'tabchange':function(tab){
            		 if(tab.getActiveTab().id=='tab3'){
             				radow.doEvent('errorDetailGrid2.dogridquery');
             			 
             		 };
             		
             		if(tab.getActiveTab().id=='tab1'){
             				radow.doEvent('errorDetailGrid.dogridquery');
             		};
             		if(tab.getActiveTab().id=='tab4'){
             				radow.doEvent('errorDetailGrid4.dogridquery');
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
					 if (validatorNull(cell)) continue;
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
					 if (validatorNull(cell)) continue;
					 cell.innerHTML = cell.innerHTML.replace("x-grid3-cell-inner","");
				 }
			 }
		 }
	 }); 
 });
 
 
 function verify(vpid,b0111,ruleids,bsType,a0163){
		var param={'vpid':vpid,'b0111':b0111,'ruleids':ruleids,'bsType':bsType,'a0163':a0163,'method':'verify'};
		//console.log(param);
		ShowCellCover('start','ϵͳ��ʾ','����У��...');
		  $.ajax({
		        type: "POST",
		        url:"<%=request.getContextPath()%>/CustomExcelServlet",
		        data: param ,//$('#excelForm').serialize(),// ���formid
		        error: function(request) {},
		        success: function(data) {
		        
		        	  radow.doEvent("showgrid",vpid);
		          	
		        }
		    }); 
	 }
	 
	 
	 function ShowCellCover(elementId, titles, msgs){	
	 	Ext.MessageBox.buttonText.ok = "�ر�";
	 	if(elementId.indexOf("start") != -1){
	 	
	 		Ext.MessageBox.show({
	 			title:titles,
	 			msg:msgs,
	 			width:300,
	 	        height:300,
	 			closable:false,
	 			modal:true,
	 			progress:true,
	 			wait:true,
	 			animEl: 'elId',
	 			increment:5, 
	 			waitConfig: {interval:150}
	 		});
	 	}else if(elementId.indexOf("success") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 		        height:300,
	 		        modal:true,
	 				closable:true,
	 				buttons: Ext.MessageBox.OK
	 			});
	 	}else if(elementId.indexOf("failure") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 	}else {
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 		}
	 }
 
	 /* �����������ʱ���ú��� */
	 function queryGroupByUpdate(obj){}
	 function personInfoWin(personId){
			var maxHeight = window.parent.document.body.offsetHeight || 640;
			var personDataVerifyWin=parent.Ext.getCmp('PersonDataVerifyWin');
			if(personDataVerifyWin){
				personDataVerifyWin.close();
			}
			var win=parent.Ext.getCmp('PersonInfoWin');
			if(win){
				win.close();
			}
			/* $h.openWin('PersonInfoWin', 'pages.customquery.person.PersonInfo', '��Ա��Ϣ', 1060, maxHeight - 20, null, ctxPath, null, {a0000: personId, gridName:'peopleInfoGrid', maximizable:false, resizable:true, draggable:false}, false);*/
			$h.openWin('PersonInfoWin', 'pages.customquery.person.PersonInfo', '��Ա��Ϣ', 1060, maxHeight - 20, personId, '<%=request.getContextPath()%>', null, {gridName:'peopleInfoGrid',maximizable:false, resizable:true, draggable:false,collapsible: true}, false);

		}
	</script>
