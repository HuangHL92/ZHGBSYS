<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel" %>
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
	


<odin:hidden property="bsType"/>		<!-- У��ҵ������ -->
<odin:hidden property="b0111OrimpID"/>	<!-- ��ҳ�洫�����ҪУ��ĵ�λ���� -->
<odin:hidden property="dbClickvel002"/>	<!-- ˫��ѡ�еĴ�����Ϣ -->
<odin:hidden property="dbClickvel003"/>	<!-- ˫��ѡ�еĴ�����Ϣ -->
<div id="addSceneContent">
	<table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<odin:select property="vsc001" label="У�鷽��" codeType="vsc001" required="true"></odin:select>
			</td>
			<td>
				<table id="updatedDiv">
					<tr>
						<odin:select2 property="updated" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;У����"  data="[0,'δУ��'],['1','У��ͨ��']" />
					</tr>
				</table>
			</td>
			<td colspan="2" align="right" noWrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp; <div id="progressbar"><div class="progress-label"></div></div></td>
		</tr>
		<!-- <tr><td  align="right" noWrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp; <div id="progressbar"><div class="progress-label"></div></div></td></tr> -->
	</table>
	<table width="100%" >
		<tr><td></td></tr>
		<tr>
		 	<td width="15%">
			<odin:toolBar property="errorGridToolBar">
				<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;������Ϣ</h1>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="����ȫ��" id="expError" handler="expExcelFromErrorGrid" cls="x-btn-text-icon"   icon="images/icon/exp.png"  /> 
				<odin:buttonForToolBar isLast="true" text="" />
			</odin:toolBar>
			
			<odin:editgrid property="errorGrid"  autoFill="false"    topBarId="errorGridToolBar" bbarId="pageToolBar" >
				<odin:gridJsonDataModel  > 
					<odin:gridDataCol name="vel002" />
					<odin:gridDataCol name="vel003" />
					<odin:gridDataCol name="vel002_name" />
					<odin:gridDataCol name="count_nums" isLast="true"/>	
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn/>
					<odin:gridColumn dataIndex="vel002" header="��������ID" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="vel003" header="������������" align="center" width="80" editor="text" hidden="true" edited="false"  />
					<odin:gridColumn dataIndex="vel002_name" header="��������" align="center" width="80" editor="text"  edited="false"  />
					<odin:gridColumn dataIndex="count_nums" header="����" align="center" width="45" editor="text"   edited="false"   isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
				 	{
				        data:[]
				    }				
				</odin:gridJsonData>
			</odin:editgrid>
			</td>
			<!--  grid �Ұ벿�� -->
			<td width="63%">
				<odin:toolBar property="errorDetailGridToolBar">
					<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;������ϸ��Ϣ</h1>"></odin:textForToolBar>
					<odin:fill />
					<odin:buttonForToolBar text="�鿴ȫ��" id="allErrorDetail" cls="x-btn-text-icon"  icon="images/search.gif"  /> 
					<odin:separator />
					<odin:buttonForToolBar text="����ȫ��" id="expErrorDetail" handler="expExcelFromErrorDetailGrid" cls="x-btn-text-icon"   icon="images/icon/exp.png"  />
					<odin:separator />
					<odin:buttonForToolBar isLast="true" text="" />
				</odin:toolBar>
				<odin:editgrid topBarId="errorDetailGridToolBar" property="errorDetailGrid"   bbarId="pageToolBar" >
					<odin:gridJsonDataModel  > 
						<odin:gridDataCol name="vel001" />
						<odin:gridDataCol name="vel002_name" />
						<odin:gridDataCol name="vel002" />
						<odin:gridDataCol name="vru003" />
						<odin:gridDataCol name="vel003_name" />
						<odin:gridDataCol name="vel003" />
						<odin:gridDataCol name="vel004" />
						<odin:gridDataCol name="vel005" />
						<odin:gridDataCol name="vel006" />
						<odin:gridDataCol name="vel007" />
						<odin:gridDataCol name="vel008" />
						<odin:gridDataCol name="vel009" />
						<odin:gridDataCol name="vel010"  isLast="true"/>		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn />
						<odin:gridColumn dataIndex="vel001" header="������Ϣid::���� " align="center" width="85" editor="text"  edited="false"   hidden="true"/>
						<odin:gridColumn dataIndex="vel002_name" header="��������" align="center" width="75" editor="text"  edited="false"  />
						<odin:gridColumn dataIndex="vel002" header="��������ID" align="center" width="100" editor="text"  edited="false"  hidden="true" />
						<odin:gridEditColumn2 dataIndex="vru003" header="��������" align="center" width="50" editor="select"  edited="false" hidden="true" />
						<odin:gridColumn dataIndex="vel003_name" header="�������" align="center" width="50" editor="text"  edited="false"  hidden="true"/>
						<odin:gridColumn dataIndex="vel003" header="�������" align="center" width="50" editor="text" hidden="true" edited="false"  />
						<odin:gridColumn dataIndex="vel004" header="У������id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
						<odin:gridColumn dataIndex="vel005" header="ҵ������" align="center" width="100" editor="text"  edited="false"  hidden="true" />
						<odin:gridColumn dataIndex="vel007" header="��У����Ϣ��" align="center" width="90" editor="text"  edited="false"  />
						<odin:gridColumn dataIndex="vel008" header="��У����Ϣ��" align="center" width="90" editor="text"  edited="false"  />
						<odin:gridColumn dataIndex="vel006" header="������Ϣ" align="left"  width="370" editor="text"  edited="false"   />
						<odin:gridColumn dataIndex="vel009" header="У�����id" align="center" width="100" editor="text"  edited="false"  hidden="true" />
						<odin:gridColumn dataIndex="vel010" header="�����ֶ�" align="center" width="100" editor="text"  edited="false"   hidden="true" isLast="true"/>
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
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text=""></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="��ʼУ��" icon="images/icon/right.gif" />
	<odin:buttonForToolBar id="impBtn"  text="����"  icon="images/icon_photodesk.gif"/>
	<odin:buttonForToolBar id="rejectBtn"  text="���"  icon="images/qinkong.gif" />
	<odin:buttonForToolBar id="btnSaveUpdated"  text="����У����" icon="images/save.gif"  isLast="true" />
</odin:toolBar>
<odin:panel contentEl="addSceneContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
function expExcelFromErrorDetailGrid(){
	odin.grid.menu.expExcelFromGrid('errorDetailGrid', null, null,null, false);
	
}
function expExcelFromErrorGrid(){
	odin.grid.menu.expExcelFromGrid('errorGrid', null, null,null, false);
	
}

//��Ա�����޸Ĵ��ڴ���
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh,errorInfo){
      var tab=parent.parent.tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
    	  parent.parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        errorInfo:errorInfo,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
	    },
	    closable:true
        }).show();  
		
      }
 }

		var progressbar = $( "#progressbar" ),
			progressLabel = $( ".progress-label" );

		progressbar.progressbar({
			value: false,
			change: function() {
				progressLabel.text( progressbar.progressbar( "value" ) + "%" );
			},
			complete: function() {
				progressLabel.text( "���!" );
			}
		});
		
		 function progress(val,processId) {
				if(val<0){
					val = 0;
				}

				progressbar.progressbar( "value", val  );
				
				radow.doEvent('queryBar',processId+'@'+val);
		} 
		
		/*  function progress() {
			var val = progressbar.progressbar( "value" ) || 0;

			progressbar.progressbar( "value", val + 1 );

			if ( val < 99 ) {
				setTimeout( progress, 100 );
			}
		}  */
	

		/* setTimeout( progress, 3000 ); */
	</script>


 
