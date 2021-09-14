<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="top" width="200px">
		    <div id="tree"></div>
		</td>
		<td align="left" valign="top" width="200px">
		    <odin:toolBar property="btnToolBar1">
	            <odin:textForToolBar text="代码集内可导出代码"/>
	        <odin:fill/>
	            <odin:buttonForToolBar text="新增" id="add" handler="addAction" cls="x-btn-text-icon"/>
            </odin:toolBar>
            <odin:editgrid property="list1"  width="200" height="400" topBarId="btnToolBar1" rowDbClick="add1" isFirstLoadData="false" url="/" sm="checkbox">
	            <odin:gridJsonDataModel>
		            <odin:gridDataCol name="selected"/>
		            <odin:gridDataCol name="codeType"/>
		            <odin:gridDataCol name="codeValue"/>
		            <odin:gridDataCol name="codeName" isLast="true"/>
	                </odin:gridJsonDataModel>
	            <odin:gridColumnModel>
		            <odin:gridRowNumColumn/>
		            <odin:gridColumn header="selectall" gridName="list1" dataIndex="selected" edited="true" editor="checkbox" width="1"/>
		            <odin:gridEditColumn dataIndex="codeType" edited="false" editor="text" hidden="true"/>
		            <odin:gridEditColumn dataIndex="codeValue" edited="false" editor="text" hidden="true"/>
		            <odin:gridEditColumn header="可导出代码" dataIndex="codeName" edited="false" editor="text" width="2" isLast="true"/>
	           </odin:gridColumnModel>
            </odin:editgrid>
		</td>
		<td align="left" valign="top" width="200px">
		<odin:toolBar property="btnToolBar2">
	        <odin:textForToolBar text="导出代码"/>
	        <odin:fill/>
	            <odin:buttonForToolBar text="移出" id="remove" handler="removeAction" cls="x-btn-text-icon"/>
            </odin:toolBar>
            <odin:editgrid property="list2"  width="200" height="400" topBarId="btnToolBar2" rowDbClick="remove2" isFirstLoadData="false" url="/" sm="checkbox">
	            <odin:gridJsonDataModel>
		            <odin:gridDataCol name="selected"/>
		            <odin:gridDataCol name="codeType"/>
		            <odin:gridDataCol name="codeValue"/>
		            <odin:gridDataCol name="codeName" isLast="true"/>
	            </odin:gridJsonDataModel>
	            <odin:gridColumnModel>
		            <odin:gridRowNumColumn/>
		            <odin:gridColumn header="selectall" gridName="list1" dataIndex="selected" edited="true" editor="checkbox" width="1"/>
		            <odin:gridEditColumn dataIndex="codeType" edited="false" editor="text" hidden="true"/>
		            <odin:gridEditColumn dataIndex="codeValue" edited="false" editor="text" hidden="true"/>
		            <odin:gridEditColumn header="导出代码" dataIndex="codeName" edited="false" editor="text" width="2" isLast="true"/>
	           </odin:gridColumnModel>
            </odin:editgrid>
		</td>
	</tr>
	<tr>
	    <td colspan="3">
	   		<table style="margin-top:15px">
	   		    <tr>
	   		        <td width="50px">
				       <odin:textEdit property="fileName" disabled="true" label="文&nbsp;件&nbsp;名" width="350" maxlength="100"/>
				    </td>
				    <td>
				       <a href="javascript:void(0)" onClick="download()">导出</a>
				    </td>
	   		    </tr>
	   		</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
 function addAction(){
	radow.doEvent('add');
 }

 function removeAction() {
	 radow.doEvent('remove');
 }
 function add1() {
	 var grid1 = odin.ext.getCmp('list1');
	 var rowData = grid1.getSelectionModel().getSelected();
	 grid1.store.remove(rowData);
	 var grid2 = odin.ext.getCmp('list2');
	 grid2.store.add(rowData);
 }
 
 function remove2() {
	 var grid2 = odin.ext.getCmp('list2');
	 var rowData = grid2.getSelectionModel().getSelected();
	 grid2.store.remove(rowData);
	 var grid1 = odin.ext.getCmp('list1');
	 grid1.store.add(rowData);
 }
 
 function download() {
	 var grid2 = odin.ext.getCmp('list2');
	 var data = grid2.store.data;
	 var param = '';
	 for(var i=0,len=data.length;i<len;i++){
		 param += data.itemAt(i).get('codeType')+':'+data.itemAt(i).get('codeValue');
		 if(i<len-1){
			 param += ',';
		 }
	 }
	 if(param==''){
	 	alert("请选择要导出的数据");
	 	return;
	 }
	 var url = 'AddTypeDownServlet?method=downCodeValueFile&param='+param;
	 window.location.href=url;
 }
 
 Ext.onReady(function(){
	 var url = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValueDeliverCue&eventNames=getTreeJsonData';
	 var tree = new Ext.tree.TreePanel({
         title : '选择代码集', 
		 id : 'CodeValueTree', 
		 el : 'tree',				//将树形添加到一个指定的div中,非常重要！
		 region : 'west', 
		 width : 200, 
		 split : true, 
		 height : 404,
		 frame : false,				//美化界面
		 enableDD : false,			//是否支持拖拽效果
		 containerScroll : true,		//是否支持滚动条
		 autoScroll : true, 
		 rootVisible : false,		//是否隐藏根节点,很多情况下，我们选择隐藏根节点增加美观性
		 border : true,				//边框
		 animate : true,				//动画效果
		 loader : new Ext.tree.TreeLoader({
			dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValueDeliverCue&eventNames=getTreeJsonData'
		}),	
		listeners : {
			'click': function(node){                                       //左键单击事件
				radow.doEvent("exportList",node.id);
			}
		}
	    });
	    var root = new Ext.tree.AsyncTreeNode({
		    id : '-1', 
		    text : '代码集', 
		    draggable : false,
		    uiProvider: Ext.tree.TreeCheckNodeUI,
		    expanded : true
	    });
	    tree.setRootNode(root);
	    root.expand(true);
	    tree.render(); 
 });
</script>

