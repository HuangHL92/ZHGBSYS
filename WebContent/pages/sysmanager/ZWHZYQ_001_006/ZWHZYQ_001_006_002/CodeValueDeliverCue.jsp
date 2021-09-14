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
	            <odin:textForToolBar text="���뼯�ڿɵ�������"/>
	        <odin:fill/>
	            <odin:buttonForToolBar text="����" id="add" handler="addAction" cls="x-btn-text-icon"/>
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
		            <odin:gridEditColumn header="�ɵ�������" dataIndex="codeName" edited="false" editor="text" width="2" isLast="true"/>
	           </odin:gridColumnModel>
            </odin:editgrid>
		</td>
		<td align="left" valign="top" width="200px">
		<odin:toolBar property="btnToolBar2">
	        <odin:textForToolBar text="��������"/>
	        <odin:fill/>
	            <odin:buttonForToolBar text="�Ƴ�" id="remove" handler="removeAction" cls="x-btn-text-icon"/>
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
		            <odin:gridEditColumn header="��������" dataIndex="codeName" edited="false" editor="text" width="2" isLast="true"/>
	           </odin:gridColumnModel>
            </odin:editgrid>
		</td>
	</tr>
	<tr>
	    <td colspan="3">
	   		<table style="margin-top:15px">
	   		    <tr>
	   		        <td width="50px">
				       <odin:textEdit property="fileName" disabled="true" label="��&nbsp;��&nbsp;��" width="350" maxlength="100"/>
				    </td>
				    <td>
				       <a href="javascript:void(0)" onClick="download()">����</a>
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
	 	alert("��ѡ��Ҫ����������");
	 	return;
	 }
	 var url = 'AddTypeDownServlet?method=downCodeValueFile&param='+param;
	 window.location.href=url;
 }
 
 Ext.onReady(function(){
	 var url = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValueDeliverCue&eventNames=getTreeJsonData';
	 var tree = new Ext.tree.TreePanel({
         title : 'ѡ����뼯', 
		 id : 'CodeValueTree', 
		 el : 'tree',				//��������ӵ�һ��ָ����div��,�ǳ���Ҫ��
		 region : 'west', 
		 width : 200, 
		 split : true, 
		 height : 404,
		 frame : false,				//��������
		 enableDD : false,			//�Ƿ�֧����קЧ��
		 containerScroll : true,		//�Ƿ�֧�ֹ�����
		 autoScroll : true, 
		 rootVisible : false,		//�Ƿ����ظ��ڵ�,�ܶ�����£�����ѡ�����ظ��ڵ�����������
		 border : true,				//�߿�
		 animate : true,				//����Ч��
		 loader : new Ext.tree.TreeLoader({
			dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValueDeliverCue&eventNames=getTreeJsonData'
		}),	
		listeners : {
			'click': function(node){                                       //��������¼�
				radow.doEvent("exportList",node.id);
			}
		}
	    });
	    var root = new Ext.tree.AsyncTreeNode({
		    id : '-1', 
		    text : '���뼯', 
		    draggable : false,
		    uiProvider: Ext.tree.TreeCheckNodeUI,
		    expanded : true
	    });
	    tree.setRootNode(root);
	    root.expand(true);
	    tree.render(); 
 });
</script>

