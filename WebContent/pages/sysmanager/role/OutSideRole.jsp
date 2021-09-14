<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>角色查询</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="clean"  text="清屏"  icon="images/sx.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btn_query" isLast="true" text="查询"  icon="images/search.gif" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="roleQueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="roleQName" label="角色名称"></odin:textEdit>
	    	<odin:select2 property="roleOwner" label="角色所有者" ></odin:select2>
	    	<odin:textEdit property="roleQDesc" label="角色描述"></odin:textEdit>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="roleQueryContent" property="roleQueryPanel" topBarId="btnToolBar"></odin:panel>
<odin:gridSelectColJs2 name="status" selectData="['1','有效'],['0','无效']"></odin:gridSelectColJs2>
<odin:toolBar property="pageTopBar">
	<odin:fill/>
	<odin:buttonForToolBar text="新增" id="addRole" icon="images/add.gif" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="注销" id="delRole" isLast="true" icon="images/back.gif"/>
</odin:toolBar>
<odin:editgrid property="grid6" hasRightMenu="false" topBarId="pageTopBar" pageSize="20" bbarId="pageToolBar" url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid6.dogridquery" title="角色信息表" width="778" height="430">
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
 <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" />
  <odin:gridEditColumn header="角色id" hidden="true" dataIndex="roleid" editor="text" />
  <odin:gridEditColumn header="角色名称" align="center" width="100" dataIndex="rolename" selectOnFocus="true" editor="text" edited="false"  />
  <odin:gridEditColumn header="角色描述" width="130" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="true" />
  <odin:gridEditColumn header="角色所有者"  width="80" dataIndex="ownername" edited="false" editor="text" align="center" hidden="true"/>
  <odin:gridEditColumn header="角色编码"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" />
  <odin:gridEditColumn2 header="角色所属系统" dataIndex="hostsys" align="center" hidden="false" edited="false" editor="select" width="80" codeType="ROLESYS"/>
  <odin:gridEditColumn2 header="状态" sortable="false" align="center" width="80" dataIndex="status" editor="select"  renderer="radow.commUserfulForGrid" selectData="['1','有效'],['0','无效']" />
  <odin:gridEditColumn sortable="false" align="center" width="60" header="授权" dataIndex="op1"  editor="text" edited="false" renderer="commGrantForGrid" hidden="true" isLast="true"/>
  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="注销" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
 
</odin:gridColumnModel>			
</odin:editgrid>	


<odin:window src="/" modal="true" id="roleWindow" width="500" height="140"></odin:window>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="520"></odin:window>
<script type="text/javascript">
   
    function commGrantForGrid(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('dogridgrant','"+record.get('roleid')+"');\">";
	}
	
	function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='注销！' onclick=\"radow.doEvent('dogriddelete','"+record.get('roleid')+"@"+record.get('hostsys')+"');\">";
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
							    text:'上移',
							    handler:UpBtn
							}),
							new Ext.Button({
								icon : 'images/icon/arrowdown.gif',
								id:'DownBtn',
							    text:'下移',
							    handler:DownBtn
							}),
							new Ext.Button({
								icon : 'images/icon/save.gif',
								id:'saveSortBtn',
							    text:'保存排序',
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
							
							//选中了多少行
							var rows = data.selections;
							//拖动到第几行
							var index = dd.getDragData(e).rowIndex;
							if (typeof(index) == "undefined"){
								return;
							}
							Ext.Msg.confirm("系统提示","是否确认排序？",function(id) { 
								if("yes"==id){
									//修改store
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
		
	});
	
	function UpBtn(){
		var grid = odin.ext.getCmp('grid6');
		var sm = grid.getSelectionModel().getSelections();
		var store = grid.store;
		//alert(store.getCount());
		
		if (sm.length<=0){
			alert('请选中需要排序的人员!')
			return;	
		}
		
		var selectdata = sm[0];  //选中行中的第一行
		var index = store.indexOf(selectdata);
		if (index==0){
			alert('该人员已经排在最顶上!')
			return;
		}
		
		store.remove(selectdata);  //移除
		store.insert(index-1, selectdata);  //插入到上一行前面
		
		grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
		
		grid.getView().refresh();
	}
	
	function DownBtn(){	
		
		var grid = odin.ext.getCmp('grid6');
		
		var sm = grid.getSelectionModel().getSelections();
		var store = grid.store;
		if (sm.length<=0){
			alert('请选中需要排序的人员!')
			return;	
		}
		
		var selectdata = sm[0];  //选中行中的第一行
		var index = store.indexOf(selectdata);
		var total = store.getCount();
		if (index==(total-1) ){
			alert('该人员已经排在最底上!')
			return;
		}
		
		store.remove(selectdata);  //移除
		store.insert(index+1, selectdata);  //插入到上一行前面
		
		grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
		grid.view.refresh();
	}
</script>
