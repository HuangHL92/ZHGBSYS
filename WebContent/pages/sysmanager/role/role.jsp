<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%	UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
	String loginname = user.getLoginname();
	String bool = "";
	if(loginname.equals("system")){
		bool = "true";
	}else{
		bool = "false";
	}
%>

<odin:toolBar property="btnToolBar" applyTo="roleQueryPanel">
	<odin:textForToolBar text="<h3>��ɫ��ѯ</h3>"/>
	<odin:fill/>
<%if(loginname.equals("system")){ %>
	<odin:buttonForToolBar text="����" id="addRole"  icon="images/add.gif"  />
<%} %>
<odin:menuSeparator isLast="true"></odin:menuSeparator>
</odin:toolBar>

<odin:hidden property="roleId"></odin:hidden>
<odin:hidden property="grantArray"></odin:hidden>
<odin:hidden property="removeArray"></odin:hidden>
<odin:hidden property="roletype"></odin:hidden>

<div id="roleQueryPanel"></div>
<table id="gridTab">
	<tr>
		<td style="padding-right: 2px">
			<odin:gridSelectColJs2 name="status" selectData="['1','��Ч'],['0','��Ч']"></odin:gridSelectColJs2>
			<odin:editgrid property="grid6" hasRightMenu="false" topBarId="pageTopBar" pageSize="20" bbarId="pageToolBar" title="��ɫ�б�" url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid6.dogridquery&limited=1" autoFill="true" forceNoScroll="true">
			<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="checked" />
		 		<odin:gridDataCol name="roleid" />
		 		<odin:gridDataCol name="roledesc" />
		  		<odin:gridDataCol name="owner" />
		  		<odin:gridDataCol name="ownername" />
		  		<odin:gridDataCol name="hostsys" />
		  		<odin:gridDataCol name="rolecode" />
		  		<odin:gridDataCol name="status" />
		  		<odin:gridDataCol name="rolename" isLast="true"/>
			</odin:gridJsonDataModel>
		<odin:gridColumnModel>
		 <odin:gridRowNumColumn ></odin:gridRowNumColumn>
		<%--  <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" width="50"/> --%>
		  	<odin:gridEditColumn header="��ɫid" hidden="true" dataIndex="roleid" editor="text" edited="false"/>
		  	<odin:gridEditColumn header="��ɫ����" align="center" width="150" dataIndex="rolename" selectOnFocus="true" editor="text" edited="false"  />
		  	<odin:gridEditColumn header="��ɫ����" width="400" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="<%=bool%>" align="center"/>
		  	<odin:gridEditColumn header="������"  width="100" dataIndex="ownername" edited="false" editor="text" align="center" hidden="false"/>
		  	<odin:gridEditColumn header="��ɫ����"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" hidden="true"/>
		  	<odin:gridEditColumn2 header="��ɫ����" dataIndex="hostsys" align="center"  edited="false" editor="select" width="80" renderer="roleTypeForGrid"/>
		  	
		  	<%if(loginname.equals("system")){ %>
		    <odin:gridEditColumn sortable="false" align="center" width="60" header="ɾ��" dataIndex="op2"  editor="text" edited="false" renderer="commGridColDelete"  />
		  	<odin:gridEditColumn sortable="false" align="center" width="60" header="����" dataIndex="op1"  editor="text" edited="false" renderer="commGrantForGrid" />
		  	<%} %>
		  	<odin:gridEditColumn2 header="״̬" sortable="false" align="center" width="80" dataIndex="status" editor="select" edited="false" renderer="radow.commUserfulForGrid" selectData="['1','��Ч'],['0','��Ч']" isLast="true" />
		  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="ɾ��" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
		</odin:gridColumnModel>			
		</odin:editgrid>	
		</td>
		<td>
			<odin:editgrid property="grid7" hasRightMenu="false" topBarId="pageTopBar" pageSize="20" bbarId="pageToolBar"  url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid7.dogridquery" title="�û��б�">
			<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="checked" />
		 		<odin:gridDataCol name="userid" />
		 		<odin:gridDataCol name="loginname" />
		  		<odin:gridDataCol name="username" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
			 		<odin:gridRowNumColumn ></odin:gridRowNumColumn>
			<%--  <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" width="50"/> --%>
				<odin:gridEditColumn header="״̬"  dataIndex="checked" editor="checkbox" edited="true" gridName="grid6" checkBoxClick="getCheckList"/>
			  	<odin:gridEditColumn header="�û�id" hidden="true" dataIndex="userid" editor="text" edited="false"/>
			  	
			  	<odin:gridEditColumn header="�û���" align="center" width="150" dataIndex="username" selectOnFocus="true" editor="text" edited="false" />
			  	<odin:gridEditColumn header="��¼��" dataIndex="loginname" editor="text" edited="false" width="150"  isLast="true"/>
			  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="ɾ��" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
			</odin:gridColumnModel>			
		</odin:editgrid>
		</td>
	</tr>	
</table>



<odin:window src="/" modal="true" id="roleWindow" width="500" height="240"></odin:window>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="520"></odin:window>
<script type="text/javascript">
	var grantArray = new Array();
	var removeArray = new Array();
    function commGrantForGrid(value, params, record, rowIndex, colIndex, ds){
		return "<a  href=\"javascript:radow.doEvent('dogridgrant','"+record.get('roleid')+"');\">��Ȩ</a>";
	}
	
	function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' style='cursor: pointer;' onclick=\"radow.doEvent('dogriddelete','"+record.get('roleid')+"');\">";
	}
	
	function roleTypeForGrid(value, params, record, rowIndex, colIndex, ds){
    	if(value=='2'){
    		return '��ͨ�û�';
    	}else {
    		return '����Ա';
    	}
	}
	Ext.onReady(function() {
		var pgrid = Ext.getCmp('grid6');
		var bbar = pgrid.getBottomToolbar();
		bbar.insertButton(11,[
							new Ext.menu.Separator({cls:'xtb-sep'}),
							new Ext.Spacer({width:100}),
							new Ext.Button({
								icon : 'images/icon/arrowup.gif',
								id:'UpBtn',
							    text:'����',
							    handler:UpBtn
							}),
							new Ext.Button({
								icon : 'images/icon/arrowdown.gif',
								id:'DownBtn',
							    text:'����',
							    handler:DownBtn
							}),
							new Ext.Button({
								icon : 'images/icon/save.gif',
								id:'saveSortBtn',
							    text:'��������',
							    handler:function(){
									var d = bbar.getPageData(); 
									var pageNum = bbar.readPage(d); 
									radow.doEvent('usersort',bbar.initialConfig.pageSize+','+pageNum);
							    }
							})]);
		
		var dstore = pgrid.getStore();
		var ddrow = new Ext.dd.DropTarget(pgrid.container,{
						ddGroup : 'GridDD',
						copy : false,
						notifyDrop : function(dd,e,data){
							
							//ѡ���˶�����
							var rows = data.selections;
							//�϶����ڼ���
							var index = dd.getDragData(e).rowIndex;
							if (typeof(index) == "undefined"){
								return;
							}
							Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ������",function(id) { 
								if("yes"==id){
									//�޸�store
									for ( i=0; i<rows.length; i++){
										var rowData = rows[i];
										if (!this.copy) dstore.remove(rowData);
										dstore.insert(index, rowData);
									}
									pgrid.view.refresh();
									radow.doEvent('usersort');
								}else{
									return;
								}		
							});
						}
					});
		
			//ҳ�����
//			Ext.getCmp('grid6').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid6'))[0]);
//			Ext.getCmp('grid7').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid7'))[0]-4);
			Ext.getCmp('grid6').setWidth(((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid6'))[1]-2)*0.6-3); 
			Ext.getCmp('grid7').setWidth(((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid6'))[1]-2)*0.4-3); 
			Ext.getCmp('grid6').setHeight(Ext.getBody().getViewSize().height-document.getElementById('gridTab').offsetTop-5);
			Ext.getCmp('grid7').setHeight(Ext.getBody().getViewSize().height-document.getElementById('gridTab').offsetTop-5);
			document.getElementById('roleQueryPanel').style.width = document.body.clientWidth;
			
			//��Ȩ�û��䶯��ı��水ť
			Ext.getCmp('grid7').getBottomToolbar().insertButton(11,[
								new Ext.menu.Separator({cls:'xtb-sep'}),
								new Ext.Spacer({width:100}),
								new Ext.Button({
									icon : 'images/icon/save.gif',
									id:'UserSave',
								    text:'����',
								    handler:SaveUser
								})

								]);
	});
	
	function UpBtn(){
		var grid = odin.ext.getCmp('grid6');
		var sm = grid.getSelectionModel().getSelections();
		var store = grid.store;
		//alert(store.getCount());
		
		if (sm.length<=0){
			alert('��ѡ����Ҫ�������Ա!')
			return;	
		}
		
		var selectdata = sm[0];  //ѡ�����еĵ�һ��
		var index = store.indexOf(selectdata);
		if (index==0){
			alert('����Ա�Ѿ��������!')
			return;
		}
		
		store.remove(selectdata);  //�Ƴ�
		store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
		
		grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
		
		grid.getView().refresh();
	}
	
	function DownBtn(){	
		
		var grid = odin.ext.getCmp('grid6');
		
		var sm = grid.getSelectionModel().getSelections();
		var store = grid.store;
		if (sm.length<=0){
			alert('��ѡ����Ҫ�������Ա!')
			return;	
		}
		
		var selectdata = sm[0];  //ѡ�����еĵ�һ��
		var index = store.indexOf(selectdata);
		var total = store.getCount();
		if (index==(total-1) ){
			alert('����Ա�Ѿ����������!');
			return;
		}
		
		store.remove(selectdata);  //�Ƴ�
		store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
		
		grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
		grid.view.refresh();
	}
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
	
	function SaveUser(){
		var userArr = document.getElementById("grantArray").value;
		var userArr1 = document.getElementById("removeArray").value;
		var roleid = document.getElementById("roleid").value;
		if(roleid == ''){
			odin.alert("��ѡ���ɫ��");
			return;
		}
		if(userArr == "" && userArr1==""){
			odin.alert("û�з����ı䣬�޷����棡");
			return;
		}
		grantArray = new Array();
		removeArray = new Array();
		radow.doEvent("delUserOfRole");
	}
	
	function getCheckList(num){
		var grid = Ext.getCmp('grid7');
		
		var check = grid.store.getAt(num).get('checked');
		var userid = grid.store.getAt(num).get('userid');
		//false ˵����Ҫ�����Ȩ  true ˵��Ҫ��Ȩ
		if(check){
			//�ڽ����Ȩ��������� 
			if(!removeByValue(removeArray, userid)){
				grantArray.push(userid);
			}
		}
		if(!check){
			//����Ȩ��������� 
			if(!removeByValue(grantArray, userid)){
				removeArray.push(userid);
			}
		}
		document.getElementById("grantArray").value = grantArray.join(",");
		document.getElementById("removeArray").value = removeArray.join("','");

	}
	
	function removeByValue(arr, val) {
		  for(var i=0; i<arr.length; i++) {
		    if(arr[i] == val) {
		      arr.splice(i, 1);
		      return true;
		    }
		  }
		  return false;
		}
</script>
