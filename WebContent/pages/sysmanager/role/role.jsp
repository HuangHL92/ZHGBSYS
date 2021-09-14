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
	<odin:textForToolBar text="<h3>角色查询</h3>"/>
	<odin:fill/>
<%if(loginname.equals("system")){ %>
	<odin:buttonForToolBar text="新增" id="addRole"  icon="images/add.gif"  />
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
			<odin:gridSelectColJs2 name="status" selectData="['1','有效'],['0','无效']"></odin:gridSelectColJs2>
			<odin:editgrid property="grid6" hasRightMenu="false" topBarId="pageTopBar" pageSize="20" bbarId="pageToolBar" title="角色列表" url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid6.dogridquery&limited=1" autoFill="true" forceNoScroll="true">
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
		  	<odin:gridEditColumn header="角色id" hidden="true" dataIndex="roleid" editor="text" edited="false"/>
		  	<odin:gridEditColumn header="角色名称" align="center" width="150" dataIndex="rolename" selectOnFocus="true" editor="text" edited="false"  />
		  	<odin:gridEditColumn header="角色描述" width="400" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="<%=bool%>" align="center"/>
		  	<odin:gridEditColumn header="创建者"  width="100" dataIndex="ownername" edited="false" editor="text" align="center" hidden="false"/>
		  	<odin:gridEditColumn header="角色编码"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" hidden="true"/>
		  	<odin:gridEditColumn2 header="角色类型" dataIndex="hostsys" align="center"  edited="false" editor="select" width="80" renderer="roleTypeForGrid"/>
		  	
		  	<%if(loginname.equals("system")){ %>
		    <odin:gridEditColumn sortable="false" align="center" width="60" header="删除" dataIndex="op2"  editor="text" edited="false" renderer="commGridColDelete"  />
		  	<odin:gridEditColumn sortable="false" align="center" width="60" header="操作" dataIndex="op1"  editor="text" edited="false" renderer="commGrantForGrid" />
		  	<%} %>
		  	<odin:gridEditColumn2 header="状态" sortable="false" align="center" width="80" dataIndex="status" editor="select" edited="false" renderer="radow.commUserfulForGrid" selectData="['1','有效'],['0','无效']" isLast="true" />
		  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="删除" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
		</odin:gridColumnModel>			
		</odin:editgrid>	
		</td>
		<td>
			<odin:editgrid property="grid7" hasRightMenu="false" topBarId="pageTopBar" pageSize="20" bbarId="pageToolBar"  url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid7.dogridquery" title="用户列表">
			<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="checked" />
		 		<odin:gridDataCol name="userid" />
		 		<odin:gridDataCol name="loginname" />
		  		<odin:gridDataCol name="username" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
			 		<odin:gridRowNumColumn ></odin:gridRowNumColumn>
			<%--  <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" width="50"/> --%>
				<odin:gridEditColumn header="状态"  dataIndex="checked" editor="checkbox" edited="true" gridName="grid6" checkBoxClick="getCheckList"/>
			  	<odin:gridEditColumn header="用户id" hidden="true" dataIndex="userid" editor="text" edited="false"/>
			  	
			  	<odin:gridEditColumn header="用户名" align="center" width="150" dataIndex="username" selectOnFocus="true" editor="text" edited="false" />
			  	<odin:gridEditColumn header="登录名" dataIndex="loginname" editor="text" edited="false" width="150"  isLast="true"/>
			  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="删除" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
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
		return "<a  href=\"javascript:radow.doEvent('dogridgrant','"+record.get('roleid')+"');\">授权</a>";
	}
	
	function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='删除！' style='cursor: pointer;' onclick=\"radow.doEvent('dogriddelete','"+record.get('roleid')+"');\">";
	}
	
	function roleTypeForGrid(value, params, record, rowIndex, colIndex, ds){
    	if(value=='2'){
    		return '普通用户';
    	}else {
    		return '管理员';
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
		
			//页面调整
//			Ext.getCmp('grid6').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid6'))[0]);
//			Ext.getCmp('grid7').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid7'))[0]-4);
			Ext.getCmp('grid6').setWidth(((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid6'))[1]-2)*0.6-3); 
			Ext.getCmp('grid7').setWidth(((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid6'))[1]-2)*0.4-3); 
			Ext.getCmp('grid6').setHeight(Ext.getBody().getViewSize().height-document.getElementById('gridTab').offsetTop-5);
			Ext.getCmp('grid7').setHeight(Ext.getBody().getViewSize().height-document.getElementById('gridTab').offsetTop-5);
			document.getElementById('roleQueryPanel').style.width = document.body.clientWidth;
			
			//授权用户变动后的保存按钮
			Ext.getCmp('grid7').getBottomToolbar().insertButton(11,[
								new Ext.menu.Separator({cls:'xtb-sep'}),
								new Ext.Spacer({width:100}),
								new Ext.Button({
									icon : 'images/icon/save.gif',
									id:'UserSave',
								    text:'保存',
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
			alert('该人员已经排在最底上!');
			return;
		}
		
		store.remove(selectdata);  //移除
		store.insert(index+1, selectdata);  //插入到上一行前面
		
		grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
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
			odin.alert("请选择角色！");
			return;
		}
		if(userArr == "" && userArr1==""){
			odin.alert("没有发生改变，无法保存！");
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
		//false 说明将要解除授权  true 说明要授权
		if(check){
			//在解除授权数组里查找 
			if(!removeByValue(removeArray, userid)){
				grantArray.push(userid);
			}
		}
		if(!check){
			//在授权数组里查找 
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
