<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	String ctxPath = request.getContextPath();
%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/js/templete/default-byname.css" />
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<style>
.x-btn {
    background-color: #3e55f9;
}
</style>
<div id="groupTreeContent">
	<table id="gridcq-tbl">
		<tr>
			<td colspan="3">
				<table id="gridcq-btn-tbl">
					<tr>
						<td>
							<div id="queryName-warn">�������/���֤��</br>ѯ���밴���Ÿ�����</div>
						</td>
						<td>
							<odin:select property="tpye" canOutSelectList="true" value = '1' data="['1','����'],['2','���֤����']" width="120"></odin:select>
						</td>
						<td>
							<textarea id="queryName" name="queryName" class="x-form-text x-form-field"></textarea> 
						</td>
						<td>
							<odin:button property="search" text="����" handler="toQuery" />
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="width: 420px; padding-right: 5px;" align="center">
				<odin:editgrid property="gridcq" title="��ѡ�б�"  width="420" height="300" autoFill="false" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck"/>	
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0163" />
						<odin:gridDataCol name="a0192a" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn width="24"></odin:gridRowNumColumn>
						<odin:gridEditColumn2 width="30" header="selectall" editor="checkbox" dataIndex="personcheck" edited="true" hideable="false" gridName="persongrid" />
						<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
						<odin:gridColumn width="60" dataIndex="a0101" header="����" align="center" />
						<odin:gridEditColumn2 width="40" dataIndex="a0104" header="�Ա�" align="center" editor="select" edited="false" codeType="GB2261" />
						<odin:gridColumn width="180" dataIndex="a0192a" edited="false" header="��λְ��" align="center" />
						<odin:gridEditColumn2 width="60" dataIndex="a0163" header="��Ա״̬" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
			</td>
			<td style="width: 20px;" align="center">
				<div id='rigthBtn'></div>
				<br>
				<div id='rigthAllBtn'></div>
				<br>
				<div id='liftBtn'></div>
				<br>
				<div id='liftAllBtn'></div>
			</td>
			<td style="width: 420px; padding-left: 5px;" align="center">
				<odin:editgrid property="selectName" title="����б�" width="420" height="300" autoFill="false" >
					<odin:gridJsonDataModel>
							<odin:gridDataCol name="personcheck2" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="a0104" />
							<odin:gridDataCol name="a0163" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn width="24"></odin:gridRowNumColumn>
							<odin:gridEditColumn2 width="30" header="selectall" editor="checkbox" dataIndex="personcheck2" edited="true" hideable="false" gridName="persongrid2" />
							<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
							<odin:gridColumn width="60" dataIndex="a0101" header="����" align="center" />
							<odin:gridEditColumn2 width="40" dataIndex="a0104" header="�Ա�" align="center" editor="select" edited="false" codeType="GB2261" />
							<odin:gridColumn width="180" dataIndex="a0192a" edited="false" header="��λְ��" align="center" />
						<odin:gridEditColumn2 width="60" dataIndex="a0163" header="��Ա״̬" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
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
<div id="btn-div" align="center">
	<table>
		<tr>
			<td><odin:button property="clearPreList" text="�����ѡ�б�" handler="clearRst" /></td>
			<td><odin:button property="sure" text="ȷ��" handler="saveSelect" /></td>
		</tr>
	</table>
</div>

<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!-- �Ƿ�����ˢ��grid�ı�� -->


<script type="text/javascript">

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.type == "textarea") {
			toDOQuery();
			return false;
		}
	}else if(event.keyCode == 27){	//����ESC
	        return false;   
	}
}



Ext.onReady(function() {
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
	gridcq.on("rowdblclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = sStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
		}
		gStore.remove(rowData);
		gridcq.view.refresh();
	});
	
	selectName.on("rowdblclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = gStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}
		
		sStore.remove(rowData);
		selectName.view.refresh();
	});
	//�����ѯ���ֻ��һ�����Զ����Ƶ�����б�
	gStore.on({  
       load:{  
           fn:function(){
        	   var mark = document.getElementById("mark").value;
        	   if(gStore.getCount() == 1 && mark == 1){
        		   radow.doEvent('rigthAllBtn.onclick');  
        	   }
        	   document.getElementById("mark").value = 0;
           }      
       },  
       scope:this      
	  });  
});






	function saveSelect(){
		radow.doEvent('saveSelect');
	}
	function clearSelect(){
		radow.doEvent('clearSelect');
	}
	function clearRst(){
		radow.doEvent('clearRst');
	}
	
	var continueCount = 0;//����ѡ�����
	var changeNode = {};//ÿ�β����ļ�¼
	var childNodes = "";
	var continueOne;//����ѡ�����һ������
	var top = "";//
	var tag = 0;
	var nocheck = 1;

	var nodeSelectedSet = {};

	function existsChoose() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		var continueCheckbox = document.getElementById('continueCheckbox');
		if (existsCheckbox.checked == false) {
			existsCheckbox.checked = false;
		} else {
			existsCheckbox.checked = true;
			continueCheckbox.checked = false;
		}
	}
	function continueChoose() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		var continueCheckbox = document.getElementById('continueCheckbox');
		if (continueCheckbox.checked == false) {
			continueCheckbox.checked = false;
		} else {
			continueCount = 0;
			tag = 0;
			continueCheckbox.checked = true;
			existsCheckbox.checked = false;
		}
	}
	function rigthBtnFun(){
		radow.doEvent('rigthBtn.onclick');
	}
	function rigthAllBtnFun(){
		radow.doEvent('rigthAllBtn.onclick');
	}
	function liftBtnFun(){
		radow.doEvent('liftBtn.onclick');
	}
	function liftAllBtnFun(){
		radow.doEvent('liftAllBtn.onclick');
	}
	Ext.onReady(function() {
		new Ext.Button({
			icon : 'images/icon/rightOne.png',
			id:'btn1',
		    cls :'inline pl',
		    renderTo:"rigthBtn",
		    handler:function(){
		    	rigthBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/rightAll.png',
			id:'btn2',
		    cls :'inline pl',
		    renderTo:"rigthAllBtn",
		    handler:function(){
		    	rigthAllBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/leftOne.png',
			id:'btn3',
		    cls :'inline pl',
		    renderTo:"liftBtn",
		    handler:function(){
		    	liftBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/leftAll.png',
			id:'btn4',
		    cls :'inline pl',
		    renderTo:"liftAllBtn",
		    handler:function(){
		    	liftAllBtnFun();
		    }
		});		
});


	var oldSelectIdArrayCount = 0;
	var oldSelectIdArray = new Array();
	var count = 0;//������
	function doQueryNext() {
		var nextProperty = document.getElementById('nextProperty').value;
		if (nextProperty == "") {
			return;
		}
		var tree = Ext.getCmp("group");
		var node = tree.getRootNode();
		oldSelectIdArray.length = 0;//���
		loopNext(node, nextProperty);
		oldSelectIdArray[count % oldSelectIdArray.length].select();
		count += 1;
	}

	function loopNext(node, nextProperty) {
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (node.childNodes[i].text.indexOf(nextProperty) >= 0) {
					oldSelectIdArray.push(node.childNodes[i]);
					loopNext(node.childNodes[i], nextProperty);
				} else {
					loopNext(node.childNodes[i], nextProperty);
				}
			}
		}
	}
	//�����ѯ
	function toQuery(){
		radow.doEvent('queryFromData');
	}
	
</script>



<script type="text/javascript">

</script>