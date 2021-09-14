<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 450px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>

<odin:hidden property="ynId" />
<odin:hidden property="PersonType" value="<%=type%>"/>
<odin:hidden property="yntype" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!--  �Ƿ�����ˢ��grid�ı�� -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />

<odin:hidden property="bz00" />
<odin:hidden property="sql2" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />

<div id="groupTreeContent" style="height: 92%;width: 100%;">
	<table style="height: 95%;">
		<tr valign="top">
			<td width="20%" height="100%" rowspan="0" colspan="0">
				<div style="height: 5%;background-color: #cedff5;" align="center">
					<input type="checkbox"  id="continueCheckbox" style="display:none" onclick="continueChoose()"><!-- <font style="font-size: 13">����ѡ��</font> -->
					<input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font style="font-size: 13">�����¼�</font>
				</div>
				<div class="busy">
					<div id="tree-div" style="border: 2px solid #c3daf9;height: 100%; width: 98%;"></div>
				</div>
			</td>  
			<td id="gridcqtd" style="width: 67%;height: 100%;">
				<table style="width: 100%;height: 100%;border: 2px solid #c3daf9;">
					<tr>
						
					</tr>					
					<tr   style="width: 100%;">
						<td colspan="3">
						<table style="height:50px;width:100%">
							<tr>
								<td style="width: 130px;" >
									<div style="font-size: 12px;margin-top: 10px;margin-left: 10px;color: red;">�������/���֤��</br>ѯ���밴���Ÿ�����</div>
								</td>
								<td>
									<odin:select property="tpye" label="" canOutSelectList="true" value = '1' data="['1','����'],['2','���֤����']" width="120"></odin:select>
								</td>
								<td>
									<textarea  class="x-form-text x-form-field" style="width: 390px;height:25px;  margin-top:1px;" name="queryName" id="queryName"  ></textarea> 
									
								</td>
								<td> <input id="buttonSearch" type="button" style="position: relative;top: 2px;" onclick="toDOQuery()" value="����"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr style="width: 100%;" align="center">
						<td width="46%" height="100%">
							
							<div id="selectByPersonIdDiv" >
								<odin:editgrid property="gridcq" title="��ѡ�б�"  width="300" height="350" pageSize="9999"
									autoFill="false" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck"  />
										<odin:gridDataCol name="a0000" />
										<odin:gridDataCol name="a0101" />
										<odin:gridDataCol name="a0104" />
										<odin:gridDataCol name="a0163" />
										<odin:gridDataCol name="a0192a" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 header="selectall" width="0"
											editor="checkbox" dataIndex="personcheck" edited="true" hidden="true"
											hideable="false" gridName="persongrid" />
										<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
										<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
										<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="100"  align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
									</odin:gridColumnModel>
									<odin:gridJsonData>
										{
									        data:[]
									    }
									</odin:gridJsonData>
								</odin:editgrid>
							</div>

						</td>
						<td style="width: 8%;height: 100%;" align="center">
							<div id='rigthBtn'  style="display: none"></div>
							<br>
							<div id='rigthAllBtn' title="ȫѡ"></div>
							<br>
							<div id='liftBtn' style="display: none"></div>
							<br>
							<div id='liftAllBtn' title="ȫѡ"></div>
						</td>
						<td width="46%;" height="100%;" align="center">
							<div id="selectByPersonIdDiv2">
								<odin:editgrid property="selectName" title="����б�" width="300" height="350" autoFill="false" >
									<odin:gridJsonDataModel>
											<odin:gridDataCol name="personcheck2"  />
											<odin:gridDataCol name="a0000" />
											<odin:gridDataCol name="a0101" />
											<odin:gridDataCol name="a0104" />
											<odin:gridDataCol name="a0163" />
											<odin:gridDataCol name="a0192a" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 header="selectall" width="0"
																  hidden="true"	editor="checkbox" dataIndex="personcheck2" edited="true"
												hideable="false" gridName="persongrid" />
											<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
											<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" align="center" width="45" editor="select" edited="false" codeType="GB2261" />
											<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="100" align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
										</odin:gridColumnModel>
										<odin:gridJsonData>
											{
										        data:[]
										    }
										</odin:gridJsonData>
								</odin:editgrid>
							</div>

						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</div>
<div align="right" >
	<table>
		<tr>
			<td>
				<input type="button" height="20" width="40" value="�����ѡ�б�" onclick="clearRst()">&nbsp;&nbsp;
			</td>
			<div style="display:none">
	 			<!-- odin:select2  property="tplb"  label="��������������ȡ��ѡ�����ȷ�����ɣ�"  odin:select2  -->
	 		</div>
			<td><input type="button" height="20" width="40" value="&nbsp;ȷ&nbsp;&nbsp;��&nbsp;" onclick="saveSelect()">&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>

<script type="text/javascript">

function resetPiCiSelect(jsonArray){ 
	//selectByInputYnId
	var objSelect = $("#selectByInputYnId");
	$("option", $("#selectByInputYnId")).remove();
	objSelect.append("<option value=''>&nbsp;&nbsp;</option>");
	$(jsonArray).each(function() { 
		objSelect.append("<option value='" + this.code + "'>" + this.name + "</option>");
	});
	
}

function selectByInputYnIdClick(){
	$("#selectByYnIdDiv").css("display","none");
	$("#selectByYnIdDiv2").css("display","none");
	
	$("#selectByPersonIdDiv").css("display","block");  
	$("#selectByPersonIdDiv2").css("display","block"); 
	
	var val = $("#selectByInputYnId").val(); 
	val = val.replace(/\s*/g,""); 
	if (val == ""){
		$("input[name='rdoSwitch']").removeAttr("disabled");
		$("#buttonSearch").removeAttr("disabled"); 
	} else{
		$("#selectByInputYnIdHidden").val(val);
		radow.doEvent('queryFromData'); 
		$("input[name='rdoSwitch']").attr("disabled","true");
		$("#buttonSearch").attr("disabled","true");
	}
}

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
	
	/*if(!!parent.Ext.getCmp(subWinId).initialConfig.ynId){
		document.getElementById("ynId").value=parent.Ext.getCmp(subWinId).initialConfig.ynId;
	}
	if(!!parent.Ext.getCmp(subWinId).initialConfig.yntype){
		document.getElementById("yntype").value=parent.Ext.getCmp(subWinId).initialConfig.yntype;
	} */
	document.getElementById("checkedgroupid").value=parent.document.getElementById('checkedgroupid').value;
	/*$("#appointment").val(parent.$("#appointment").val());
	if (parent.$("#appointment").val()=="1"){
		$("#selectByPersonIdBtn").attr("disabled","true");
		$("#selectByYnIdBtn").attr("disabled","true");
	}*/
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
	gridcq.on("rowclick",function(o, index, o2){
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

	selectName.on("rowclick",function(o, index, o2){
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

	//
    // var sm = gridcq.CheckboxSelectionModel();
	//
    // sm.handleMouseDown = Ext.emptyFn;//����ӦMouseDown�¼�
    // sm.on('rowselect',function(sm_,rowIndex,record){//��ѡ�е�ʱ��
    //     alert(index);
    //     console.log(index)
    //     rigthBtnFun();
    // }, this);

    // gridcq.on("rowselect",function(o, index, o2){
    //     alert(index);
    //     console.log(index)
    //     rigthBtnFun();
    // });
    // selectName.on("rowselect",function(o, index, o2){
    //     liftBtnFun();
	//
    // });
    // var sm = gridcq.RowSelectionModel({
    //     listeners:{
    //         'rowselect':function(sm,rowIndex,r){
    //             alert(1);
	//
    //             Ext.log("select",rowIndex,r.get('name'))
    //             rigthBtnFun();
    //         }
    //     }
    // });
    // sm.handleMouseDown = Ext.emptyFn;//����ӦMouseDown�¼�
    // sm.on('rowselect', function (sm_, rowIndex, record) {//��ѡ�е�ʱ��
    //     alert(1);
    // }, this);
    // sm.on('rowdeselect', function (sm_, rowIndex, record) {//��δѡ�е�ʱ��
    //     alert(2);
    // }, this);
    // gridcq.CheckboxSelectionModel({
    //     listeners: {
    //         'rowselect': function (sm, rowIndex, record) {
    //             console.log('rowselect', rowIndex)
    //         },
    //         'rowdeselect': function (sm, rowIndex, record) {
    //             console.log('rowdeselect', rowIndex)
    //         },
    //         'selectionchange': function (sm) {
    //             console.log('selectionchange', sm.getSelections().length);
    //         }
    //     }
	//
	//
    // });
});



	/***
	  * ������Աѡ��
	  *
	  */
	function selectByPersonId(){ 
		$("#selectByYnIdDiv").css("display","none");
		$("#selectByYnIdDiv2").css("display","none");
		
		$("#selectByPersonIdDiv").css("display","block");  
		$("#selectByPersonIdDiv2").css("display","block"); 
		
		$("#selectType").val("0");
		$("#selectByInputYnIdHidden").val(""); //ȡ������������ѡ��
		$("#buttonSearch").removeAttr("disabled");
	}
	/***
	  * ��������ѡ��
	  *
	  */	
	function selectByYnId(){ 
		$("#selectByYnIdDiv").css("display","block"); 
		$("#selectByYnIdDiv2").css("display","block"); 
		$("#selectByPersonIdDiv").css("display","none");
		$("#selectByPersonIdDiv2").css("display","none"); 
		$("#selectType").val("1");
		$("#selectByInputYnIdHidden").val(""); //ȡ������������ѡ��
		$("#buttonSearch").attr("disabled","true");
		radow.doEvent('queryFromYnId'); 	
	}
	
	/***
	   *   ������Ա��Ϣ
	 *
	 */
	function saveSelect(){  
		var appointment = $("#appointment").val(); 
		if (appointment == "1") {
			$h.confirm("ϵͳ��ʾ��",'ȷ����ʽ��������Ա��Ϣ����м��ת�뵽��ʽ�⣬�Ƿ������',200,function(id) {
				if (id == 'ok'){
					radow.doEvent('doAppointment');
				}
			});
		} else { 
		
		var o = doQuery();
		var a = JSON.stringify(o);
			
			radow.doEvent('saveSelect',a);
		}
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
        toDOQuery();
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
        toDOQuery();

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
		
		//���ݸ�ҳ��Ľṹ�����ػ�����  2019.12.05 yzk
		//var LWflag=parent.Ext.getCmp(subWinId).initialConfig.LWflag;
		var sign="write";
		/*if(LWflag=="1"){
			sign="look"
		}else{
			sign="write";
		}*/
		
		var Tree = Ext.tree;
				var tree = new Tree.TreePanel(
						{
							id : 'group',
							el : 'tree-div',//Ŀ��div����
							split : false,
							width : 240,
							rootVisible : false,
							autoScroll : true,
							animate : true,
							border : false,
							//height:350,
							enableDD : false,
							containerScroll : true,
							checkModel : 'multiple',
							loader : new Tree.TreeLoader(
									{
										baseAttrs : {
											uiProvider : Ext.tree.TreeCheckNodeUI
										},
										//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&eventNames=orgTreeJsonData&tag=1',
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1',
										baseParams : {sign: sign}
									//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData&tag=1'
									}),
							listeners : {
								'expandnode' : function(node) {
									//��ȡչ���ڵ����Ϣ
									if (node.attributes.tag == "1") {
										node.attributes.tag = "2";
										for (var i = 0; i < node.childNodes.length; i++) {
											node.childNodes[i].ui.checkbox.checked = true;
											node.childNodes[i].attributes.tag = "1";
										}
									} else if (node.attributes.tag == "0") {
										node.attributes.tag = "2";
										for (var i = 0; i < node.childNodes.length; i++) {
											node.childNodes[i].ui.checkbox.checked = false;
											node.childNodes[i].attributes.tag = "0";
										}
									}
								}
							}
						});
				var root = new Tree.AsyncTreeNode({
					checked : false,
					text : '',
					iconCls : '',
					draggable : false,
					expanded : true,
					id : '-1'//,//Ĭ�ϵ�nodeֵ��?node=-100
				//href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
				});
				tree.setRootNode(root);
				tree.on(
								"check",
								function(node, checked) {
									//������ѡ��ťΪѡ�У������node.idΪ��ѡ�в����� �����node.id Ϊѡ������Ϊ��һnode.id ���� ����ڶ���node.id Ϊѡ��  ѡ�����пɼ�node.id
									//node.attributes.tag    1��������ѡ�¼� 0������ȡ���¼� 2��ʲô������
									var existsCheckbox = document
											.getElementById('existsCheckbox');
									var continueCheckbox = document
											.getElementById('continueCheckbox');
									if (checked && existsCheckbox.checked
											&& !continueCheckbox.checked) {
										node.attributes.tag = "1";
										loop(node)
									} else if (!checked
											&& existsCheckbox.checked) {
										node.attributes.tag = "0";
									}
									if (continueCheckbox.checked) {
										if (checked) {
											if (continueCount == 1) {
												continueCheckbox.checked = false;
												continueCheck(continueOne, node);
											}
											if (continueCount == 0) {

												continueOne = node;
												continueCount = 1;
											}
										}
									}
									//��������¼���ѡ��,�˴��¼���ѡ��
									if (existsCheckbox.checked) {
										loop(node);
									}
									if (existsCheckbox.checked) {
										if (checked) {
											//changeNode += node.id+":"+checked+":1,";
											changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
													+ checked + ":1,")
													: (checked + ":1,");
										} else {
											//changeNode += node.id+":"+checked+":2,";
											changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
													+ checked + ":2,")
													: (checked + ":2,");
										}
									} else {
										//changeNode += node.id+":"+checked+":0,";
										changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
												+ checked + ":0,")
												: (checked + ":0,");
									}
									toDOQuery();
									//alert(changeNode);
								}); //ע��"check"�¼�
				tree.render();
				root.expand();
        var viewSize = Ext.getBody().getViewSize();
        var tableTab1 = document.getElementById("tree-div");
        tableTab1.style.height = viewSize.height-49+"px";//87 82
        LEFT_HEIGHT=viewSize.height-20;
        $("#tree-div >div").height("440px");
        $("#tree-div ").height("440px");
        //alert($("#tree-div >div").height());
			});
	function findNode(node, one, two) {
		if (tag == 1) {
			return one;
		}
		if (tag == 2) {
			return two;
		}
		if (node.id == one.id) {
			tag = 1;
			return one;
		}
		if (node.id == two.id) {
			tag = 2;
			return two;
		}
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (tag != 0) {
					return;
				}
				findNode(node.childNodes[i], one, two);
			}
		}
	}
	function upOrDown(one, two) {
		var tree = Ext.getCmp("group");
		if (tree.getRootNode().id == two.id) {
			return two;
		}
		if (tree.getRootNode().id == one.id) {
			return one;
		}
		var node = tree.getRootNode();
		//һֱ������
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (tag != 0) {
					return one;
				}
				findNode(node.childNodes[i], one, two);
			}
		}
	}
	function continueCheck(one, two) {
		//�ж�ѡ��ڶ��εķ���������ϣ�ִ�г��Ϸ�����������ִ��
		//�ж��Ƿ����ϼ��ڵ㣬�ϼ��ڵ������ң����û���ϼ��ڵ㷵��false
		upOrDown(one, two);
		if (tag == 1) {
			if (continueCheckDownLoop(one, two.id) == 1) {
				two.attributes.tag = "2";
				return 1;
			}
		} else {
			if (continueCheckDownLoop(two, one.id) == 1) {
				one.attributes.tag = "2";
				return 1;
			}
		}
	}

	function continueCheckUpLoop(one, two) {
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
			}
		}
		try {
			if (one.parentNode.parentNode.childNodes.length > 0) {
				for (var i = 0; i < one.parentNode.parentNode.childNodes.length; i++) {
					if (one.parentNode.parentNode.childNodes[i].id == one.parentNode.id) {
						if (i + 1 < one.parentNode.parentNode.childNodes.length) {
							//���¼�����Ҳ�����ƽ��
							if (continueCheckDownLoop(
									one.parentNode.parentNode.childNodes[i + 1],
									two) == 1) {
								return 1;
							} else {
								if (continueCheckSameLoop(
										one.parentNode.childNodes[i + 1], two) == 1) {
									return 1;
								}
							}
						} else {
							if (continueCheckUpLoop(
									one.parentNode.parentNode.childNodes[0],
									two) == 1) {
								return 1;
							}
						}
					}
				}
			}
		} catch (e) {

		}
	}
	function continueCheckDownLoop(one, two) {
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
			}
		}
		var node = one;
		//һֱ������
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (continueCheckDownLoop(node.childNodes[i], two) == 1) {
					return 1;
				}
			}
		} else {
			//ƽ������
			nocheck = 0;
			if (continueCheckSameLoop(one, two) == 1) {
				return 1;
			}
		}
	}

	function continueCheckSameLoop(one, two) {
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
			}
		}
		var node = one;
		if (node.parentNode.childNodes.length > 0) {
			for (var i = 0; i < node.parentNode.childNodes.length; i++) {
				if (node.parentNode.childNodes[i].id == one.id) {
					nocheck = 1;
					//���û��ƽ������һ��node����ô�������ϼ�
					if (i + 1 < node.parentNode.childNodes.length) {
						//���¼�����Ҳ�����ƽ��
						if (continueCheckDownLoop(
								node.parentNode.childNodes[i + 1], two) == 1) {
							return 1;
						} else {
							if (continueCheckSameLoop(
									node.parentNode.childNodes[i + 1], two) == 1) {
								return 1;
							}
						}
					} else {
						//���ϼ�
						nocheck = 0;
						if (continueCheckUpLoop(one, two)) {
							return 1;
						}
					}
				}
			}
		}
	}

	function loop(node) {
		if (node.ui.checkbox.checked == true) {
			//node.expand();
			if (node.childNodes.length > 0) {
				for (var i = 0; i < node.childNodes.length; i++) {
					node.childNodes[i].attributes.tag = "1";
					node.childNodes[i].ui.checkbox.checked = true;
					//changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
					changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
							+ node.childNodes[i].ui.checkbox.checked + ":1,")
							: (node.childNodes[i].ui.checkbox.checked + ":1,");
					loop(node.childNodes[i]);
				}
			}
		} else {
			node.ui.checkbox.checked = false;
			for (var i = 0; i < node.childNodes.length; i++) {
				node.childNodes[i].attributes.tag = "0";
				node.childNodes[i].ui.checkbox.checked = false;
				changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
						+ node.childNodes[i].ui.checkbox.checked + ":0,")
						: (node.childNodes[i].ui.checkbox.checked + ":0,");
				loop(node.childNodes[i]);
			}
		}
	}
	function getValue() {
		var URLParams = new Array();
		var aParams = document.location.search.substr(1).split('&');
		for (i = 0; i < aParams.length; i++) {
			var aParam = aParams[i].split('=');
			URLParams[aParam[0]] = aParam[1];
		}
		return URLParams["roleid"];
	}
	Ext.tree.TreeCheckNodeUI = function() {
		//'multiple':��ѡ; 'single':��ѡ; 'cascade':������ѡ 
		this.checkModel = 'multiple';

		//only leaf can checked 
		this.onlyLeafCheckable = false;

		Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
	};

	Ext.extend(
					Ext.tree.TreeCheckNodeUI,
					Ext.tree.TreeNodeUI,
					{

						renderElements : function(n, a, targetNode, bulkRender) {
							var tree = n.getOwnerTree();
							this.checkModel = tree.checkModel
									|| this.checkModel;
							this.onlyLeafCheckable = tree.onlyLeafCheckable || false;

							// add some indent caching, this helps performance when rendering a large tree 
							this.indentMarkup = n.parentNode ? n.parentNode.ui
									.getChildIndent() : '';

							var cb = (!this.onlyLeafCheckable || a.leaf);
							var href = a.href ? a.href : Ext.isGecko ? "" : "#";
							var buf = [
									'<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">',
									'<span class="x-tree-node-indent">',
									this.indentMarkup,
									"</span>",
									'<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />',
									'<img src="',
									a.icon || this.emptyIcon,
									'" class="x-tree-node-icon',
									(a.icon ? " x-tree-node-inline-icon" : ""),
									(a.iconCls ? " " + a.iconCls : ""),
									'" unselectable="on" />',
									cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />'
											: '/>'))
											: '',
									'<a hidefocus="on" class="x-tree-node-anchor" href="',
									href,
									'" tabIndex="1" ',
									a.hrefTarget ? ' target="' + a.hrefTarget
											+ '"' : "",
									'><span unselectable="on">',
									n.text,
									"</span></a></div>",
									'<ul class="x-tree-node-ct" style="display:none;"></ul>',
									"</li>" ].join('');

							var nel;
							if (bulkRender !== true && n.nextSibling
									&& (nel = n.nextSibling.ui.getEl())) {
								this.wrap = Ext.DomHelper.insertHtml(
										"beforeBegin", nel, buf);
							} else {
								this.wrap = Ext.DomHelper.insertHtml(
										"beforeEnd", targetNode, buf);
							}

							this.elNode = this.wrap.childNodes[0];
							this.ctNode = this.wrap.childNodes[1];
							var cs = this.elNode.childNodes;
							this.indentNode = cs[0];
							this.ecNode = cs[1];
							this.iconNode = cs[2];
							var index = 3;
							if (cb) {
								this.checkbox = cs[3];
								Ext.fly(this.checkbox).on(
										'click',
										this.check.createDelegate(this,
												[ null ]));
								index++;
							}
							this.anchor = cs[index];
							this.textNode = cs[index].firstChild;
						},

						// private 
						check : function(checked) {
							var n = this.node;
							var tree = n.getOwnerTree();
							this.checkModel = tree.checkModel
									|| this.checkModel;

							if (checked === null) {
								checked = this.checkbox.checked;
							} else {
								this.checkbox.checked = checked;
							}

							n.attributes.checked = checked;
							tree.fireEvent('check', n, checked);

							if (!this.onlyLeafCheckable
									&& this.checkModel == 'cascade') {
								var parentNode = n.parentNode;
								if (parentNode !== null) {
									this.parentCheck(parentNode, checked);
								}
								if (!n.expanded && !n.childrenRendered) {
									n.expand(false, false, this.childCheck);
								} else {
									this.childCheck(n);
								}
							} else if (this.checkModel == 'single') {
								var checkedNodes = tree.getChecked();
								for (var i = 0; i < checkedNodes.length; i++) {
									var node = checkedNodes[i];
									if (node.id != n.id) {
										node.getUI().checkbox.checked = false;
										node.attributes.checked = false;
										tree.fireEvent('check', node, false);
									}
								}
							}

						},

						// private 
						childCheck : function(node) {
							var a = node.attributes;
							if (!a.leaf) {
								var cs = node.childNodes;
								var csui;
								for (var i = 0; i < cs.length; i++) {
									csui = cs[i].getUI();
									if (csui.checkbox.checked ^ a.checked)
										csui.check(a.checked);
								}
							}
						},

						// private 
						parentCheck : function(node, checked) {
							var checkbox = node.getUI().checkbox;
							if (typeof checkbox == 'undefined')
								return;
							if (!(checked ^ checkbox.checked))
								return;
							if (!checked && this.childHasChecked(node))
								return;
							checkbox.checked = checked;
							node.attributes.checked = checked;
							node.getOwnerTree().fireEvent('check', node,
									checked);

							var parentNode = node.parentNode;
							if (parentNode !== null) {
								this.parentCheck(parentNode, checked);
							}
						},

						// private 
						childHasChecked : function(node) {
							var childNodes = node.childNodes;
							if (childNodes || childNodes.length > 0) {
								for (var i = 0; i < childNodes.length; i++) {
									if (childNodes[i].getUI().checkbox.checked)
										return true;
								}
							}
							return false;
						},

						toggleCheck : function(value) {
							var cb = this.checkbox;
							if (cb) {
								var checked = (value === undefined ? !cb.checked
										: value);
								this.check(checked);
							}
						}
					});

	function loopRoot(rootnode) {//[�ڵ�����Ƿ�����¼����Ƿ�ѡ�б���]
		for (var i = 0; i < rootnode.childNodes.length; i++) {
			var cNode = rootnode.childNodes[i];
			if (cNode.ui.checkbox.checked) {
				nodeSelectedSet[cNode.id] = [ cNode, true, true ];
			} else {
				loopParent(cNode);//��������һ��δ��ѡ�У��ϼ����ĳɲ������¼�
			}
			if (cNode.childNodes.length > 0) {
				loopRoot(cNode);
			} else {
				if (cNode.attributes.tag == 1 && !cNode.ui.checkbox.checked) {//û���ֶ�չ���¼������� �������¼�     ����û��ѡ��
					if (cNode.isLeaf()) {//��Ҷ�ӽڵ�
						//nodeSelectedSet[cNode.id]=[cNode,false,false];//[�ڵ���󣬲������¼�������û��ѡ��]
					} else {//����Ҷ�ӽڵ�
						nodeSelectedSet[cNode.id] = [ cNode, true, false ];//[�ڵ���󣬰����¼�������û��ѡ��]
					}
				} else if (cNode.attributes.tag != 1
						&& cNode.ui.checkbox.checked) {//�������¼���û��չ���¼�������������ѡ��
					if (cNode.isLeaf()) {//��Ҷ�ӽڵ�

					} else {//����Ҷ�ӽڵ�
						loopParent(cNode);
					}
					nodeSelectedSet[cNode.id] = [ cNode, false, true ];

				} else if (cNode.attributes.tag == 1
						&& cNode.ui.checkbox.checked && cNode.isLeaf()) {//Ҷ�ӽڵ�  �����¼��������������¼���
					nodeSelectedSet[cNode.id] = [ cNode, false, true ];
				}
			}
		}

	}
	//�����ڵ����ò������¼���
	function loopParent(cNode) {
		if (cNode.parentNode) {
			if (nodeSelectedSet[cNode.parentNode.id]) {
				nodeSelectedSet[cNode.parentNode.id][1] = false;
			}
			loopParent(cNode.parentNode);
		}
	}

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
	
	var allCheckedString = "";
	function findchildnode(node){
        var childnodes = node.childNodes;
        
        var length = 0;
        if (typeof(childnodes.length) != undefined && childnodes.length) length = childnodes.length;
         
        for(var i=0;i<length;i++){  //�ӽڵ���ȡ���ӽڵ����α���
            var tNode = childnodes[i]; 
            allCheckedString+=","+tNode.text;
            if (typeof(tNode.length) == undefined || !tNode.length || tNode.length<=0){
            	
            }else if(tNode.childNodes.length>0){  //�ж��ӽڵ����Ƿ�����ӽڵ㣬���˾����ж��Ƿ�leaf��̫������Ϊ��ʱ����leaf�Ľڵ�Ҳ����û���ӽڵ�
                findchildnode(tNode);    //��������ӽڵ�  �ݹ�
            }    
        } 
    }
	
	//�����ѯ
	function toDOQuery(){ 
	//alert("�����ѯ");
		//���ҵ�ѡ��ĵ�λ  
		var o = doQuery();
		if (isObjectEmpty(o)){
		Ext.Msg.alert('ϵͳ��ʾ:','������߻�����ѡ��λ��');
			return;
		}
	
		document.getElementById("selectUnitId").value = JSON.stringify(o);

	
		radow.doEvent('queryFromData');
	}
	
	function isObjectEmpty(obj){
		for(var key in obj){
		    if(key){
		      return false
		    }
	  	}
	  	return true
	}
	
	function doQuery() {
		//alert( JSON.stringify(changeNode));
		//alert("�����ѯ");
		var treenode = Ext.getCmp('group');
		var rootNode = treenode.getRootNode();
		loopRoot(rootNode);
		
		var nodeRealSelectedSet = {};//��Ҫ����Ľڵ㡣
		for(var nodeid in nodeSelectedSet){  
		  var selectedNode = nodeSelectedSet[nodeid][0];//��ѡ�еĽڵ�
		  //����¼�û��չ���ڵ㣬�ұ���û�б�ѡ�У����а����¼�ѡ�б�־��
		  if(!nodeSelectedSet[nodeid][2]){
			  nodeRealSelectedSet[nodeid] = selectedNode.text+"(�¼�����):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  
		  }
		  if(nodeSelectedSet[nodeid][2]&&!nodeSelectedSet[nodeid][1]){
			  var pNode = selectedNode.parentNode;
			  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//���ڵ㲻����  �� ���ڵ㲻�����¼�
				  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//�ڵ�����+ �������¼�+ ������ѡ��
				  
			  }
			 
		  }
		  //�����¼��ڵ�δ��չ��
		  //��������ظ��İ����¼�
	      if(nodeSelectedSet[nodeid][1]){//�����¼�
			  if(selectedNode.parentNode){//���ϼ��ڵ�
			  	  var pNode = selectedNode.parentNode;
			  	  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//�ϼ�û�б�ѡ�� �� �ϼ��ڵ㲻�����¼�
			  		nodeRealSelectedSet[nodeid] = selectedNode.text+"(ȫ������):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  	  }
			  }
	      }else{
	    	  nodeRealSelectedSet[nodeid] = selectedNode.text+":true:true";//�ڵ�����+ �������¼�+ �����Ƿ�ѡ��
	      }
	    } 
		
		//alert( JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};

		return nodeRealSelectedSet;
		//radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));
		
	}	
	
	
	function selectofSelection() {
		//alert( JSON.stringify(changeNode));
		var treenode = Ext.getCmp('group');
		var rootNode = treenode.getRootNode();
		loopRoot(rootNode);
		
		var nodeRealSelectedSet = {};//��Ҫ����Ľڵ㡣
		for(var nodeid in nodeSelectedSet){  
		  var selectedNode = nodeSelectedSet[nodeid][0];//��ѡ�еĽڵ�
		  //����¼�û��չ���ڵ㣬�ұ���û�б�ѡ�У����а����¼�ѡ�б�־��
		  if(!nodeSelectedSet[nodeid][2]){
			  nodeRealSelectedSet[nodeid] = selectedNode.text+"(�¼�����):true:false";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  continue;
		  }
		  if(nodeSelectedSet[nodeid][2]&&!nodeSelectedSet[nodeid][1]){
			  var pNode = selectedNode.parentNode;
			  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//���ڵ㲻����  �� ���ڵ㲻�����¼�
				  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//�ڵ�����+ �������¼�+ ������ѡ��
				  
			  }
			  continue;
		  }
		  //�����¼��ڵ�δ��չ��
		  //��������ظ��İ����¼�
	      if(nodeSelectedSet[nodeid][1]){//�����¼�
			  if(selectedNode.parentNode){//���ϼ��ڵ�
			  	  var pNode = selectedNode.parentNode;
			  	  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//�ϼ�û�б�ѡ�� �� �ϼ��ڵ㲻�����¼�
			  		nodeRealSelectedSet[nodeid] = selectedNode.text+"(ȫ������):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  	  }
			  }
	      }else{
	    	  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//�ڵ�����+ �������¼�+ �����Ƿ�ѡ��
	      }
	    } 
		
		//alert( JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};

		return nodeRealSelectedSet;
		//radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));
		
	}	
</script>



<script type="text/javascript">

</script>
</html>