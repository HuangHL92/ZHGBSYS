<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
.x-panel-bwrap,.x-panel-body {
	height: 100%
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

</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<script type="text/javascript">


	var continueCount = 0;//����ѡ�����
	var changeNode = "";
	var childNodes = "";
	var continueOne;//����ѡ�����һ������
	var top = "";//
	var tag = 0;
	var nocheck = 1;
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
	function continueCheck(one, two) {
		//�ж�ѡ��ڶ��εķ���������ϣ�ִ�г��Ϸ�����������ִ��
		//�ж��Ƿ����ϼ��ڵ㣬�ϼ��ڵ������ң����û���ϼ��ڵ㷵��false
		upOrDown(one, two);
		if (tag == 1) {
			if (continueCheckDownLoop(one, two.id) == 1) {
				two.attributes.tag="2";
				return 1;
			}
		} else {
			if (continueCheckDownLoop(two, one.id) == 1) {
				one.attributes.tag="2";
				return 1;
			}
		}
	}

	function continueCheckDownLoop(one, two) {
		one.attributes.tag="1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked) {
				one.ui.checkbox.checked = true;
				changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
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
		one.attributes.tag="1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
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

	var nodeSelectedSet = {};

	function loopRoot(rootnode){//[�ڵ�����Ƿ�����¼����Ƿ�ѡ�б���]
		  for(var i =0;i<rootnode.childNodes.length ;i++){
			  var cNode = rootnode.childNodes[i];
			  if(cNode.ui.checkbox.checked){
				  nodeSelectedSet[cNode.id]=[cNode,true,true];
			  }else{
				  loopParent(cNode);//��������һ��δ��ѡ�У��ϼ����ĳɲ������¼�
			  }
			  if(cNode.childNodes.length>0){
				  loopRoot(cNode);
			  }else{
				  if(cNode.attributes.tag==1&&!cNode.ui.checkbox.checked){//û���ֶ�չ���¼������� �������¼�     ����û��ѡ��
					  if(cNode.isLeaf()){//��Ҷ�ӽڵ�
						  //nodeSelectedSet[cNode.id]=[cNode,false,false];//[�ڵ���󣬲������¼�������û��ѡ��]
					  }else{//����Ҷ�ӽڵ�
						  nodeSelectedSet[cNode.id]=[cNode,true,false];//[�ڵ���󣬰����¼�������û��ѡ��]
					  }
				  }else if(cNode.attributes.tag!=1&&cNode.ui.checkbox.checked){//�������¼���û��չ���¼�������������ѡ��
					  if(cNode.isLeaf()){//��Ҷ�ӽڵ�
						  
					  }else{//����Ҷ�ӽڵ�
						  loopParent(cNode);
					  }
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
					  
				  }else if(cNode.attributes.tag==1&&cNode.ui.checkbox.checked&&cNode.isLeaf()){//Ҷ�ӽڵ�  �����¼��������������¼���
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
				  }
			  }
		  }
		  
	}
	
	//�����ڵ����ò������¼���
	function loopParent(cNode){
		if(cNode.parentNode){
			if(nodeSelectedSet[cNode.parentNode.id]){
				nodeSelectedSet[cNode.parentNode.id][1]=false;
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

	function grantTabChange(tabObj, item) {
		if (item.getId() == 'tab2') {
			odin.ext.getCmp('resourcegrid').view.refresh(true);
		}
	}

	function returnWin(returnValue) {
		if (window.opener) { //���Chrome������ļ�������
			window.opener.Sure(returnValue);
		} else {
			window.returnValue = returnValue; //����ֵ
		}
		window.close();
	}

	function OnInput(e) {
		oldSelectIdArray.length = 0;
		loopNextTag = 0;
		oldSelectCount = 0;
		oldSelectId = "1";
		count = 0;
	}
	
	
	function tishi() {
		radow.doEvent('Zhishow');
		parent.exeOtherTab('402888045c960861015c9661add50003','freathh');

		
	}
</script>
<%-- <div id="groupTreeContent1" style="height: 10%"><odin:button text="ȷ��" property="Zhishow" handler="tishi"></odin:button> </div> --%>
<div id="groupTreeContent" style="height: 100%">
	<table width="100%">
		<tr valign="top">
			<td> <odin:editgrid property="peopleInfoGrid" title="�û��б�"
					autoFill="false" width="550" height="500" bbarId="pageToolBar"
					pageSize="20">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="loginname" />
						<odin:gridDataCol name="b0101" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridColumn header="selectall" width="50" editor="checkbox"
							dataIndex="personcheck" edited="true" gridName="persongrid"
							checkBoxClick="getCheckList()"
							checkBoxSelectAllClick="getCheckList()" />
						<odin:gridEditColumn2 dataIndex="a0000" width="110" header="id" hideable="false" editor="text"
							align="center" hidden="true" />
						<odin:gridColumn dataIndex="loginname" width="110" header="�û���"
							align="center" />
						<odin:gridColumn dataIndex="b0101" width="215" header="�û����ڻ���"
							align="center"edited="false" isLast="true" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
	{
        data:[]
    }
</odin:gridJsonData>
				</odin:editgrid></td>
		</tr>
	</table>
</div>





<script type="text/javascript">

Ext.onReady(function(){
	//alert(122222222222222222);
	$("#ext-gen8").children().after("<input type='button' onclick='tishi();' id='abc' value='ȷ��' style='position:absolute;right:1px;top:1px;background-color:#cddef3; '/>");
});
// var extdiv = document.getElementById("ext-gen8");
 //var divhtml = extdiv.innerHTML;
 
// extdiv.innerHTML = divhtml+"<input type='button' value='��ť'>";
//
/* Ext.onReady(function(){
	var pgrid = Ext.getCmp('peopleInfoGrid');
	
	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:100}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'Zhishow',
						    text:'ȷ��',
						    handler:function(){
								radow.doEvent('tishi');
						    }
						}),
						
						]); 

}); */

	function defChecked(ids){
	//odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
}

	function lict(){
	document.getElementById('checkList').value = '';
	}
    function getPersonId(){
    	var personId = document.getElementById('checkList').value;
    	return personId;
    } 
     
	function getCheckList() {
		radow.doEvent('getCheckList');
	}
	
	function getPersonIdForDj(){
    	var personId = document.getElementById('a0000').value;
    	return personId;
	}
	
	function getPersonNameForDj(){
    	var personName = document.getElementById('a0101').value;
    	return personName;
	}
	
	
	//��׼����
	function downLoadTmp() {
		document.getElementById('tabname').value = '��׼����';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 1;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '��׼����']")), "��׼�ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '��׼����']")), "��׼�ļ�", 450,
					210);
				
			}
			

	}
	
	
	//��׼�Զ���
	function downLoadZdyTmp() {
		document.getElementById('tabname').value='�Զ����׼����';
		radow.doEvent("chooseout");
	}
	function zdy(){
		var zdys = document.getElementById('ddd').value;
		////alert(cfa);
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 3;
		
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
				
			}
	}
	
	function downLoadTmp2() {
		document.getElementById('tabname').value='��Ƭ����';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 2;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '��Ƭ����(ÿ��4��)'],['8','��Ƭ����(ÿ��1��)']")), "�����ļ�", 500,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '��Ƭ����(ÿ��4��)'],['8','��Ƭ����(ÿ��1��)']")), "�����ļ�", 500,
					210);
				
			}

	}
	
	function downLoadTmp4() {
		document.getElementById('tabname').value='�Զ�����Ƭ����';
		radow.doEvent('zpzdy');
	}
	function zpzdy(){
		var zdys = document.getElementById('ddd').value;
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
     	var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 4;
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
				
			}			
		
	}
	
	/**
	 * �����ǼǱ��
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function expExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
				j++;
			}
		}
		if (a0000 == '') {
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
		if(j>3){
			for(var i = 0; i < 3; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1)+'��';
		}else{
			for(var i = 0; i < j; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1);
		}
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = a0101;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?download=true&filename="+filename)), "�����ļ�", 600, 200);
	}
	 
	function ml(a0000,allName){
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = allName;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp")), "�����ļ�", 600, 520);
	}
	
	//�ǼǱ�༭����
	function editFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ��Ҫ�ϴ���������Ա��¼��");
			return;
		}
		if(values[3] > 1){
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}
		var name = values[1]+"@"+values[2];
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid="+values[0]+"&uname="+name);
		
	}
	
	//�鿴/ɾ������
	function modifyFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ����Ա��¼��");
			return;
		}
		if(values[3] > 1){
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@1");
		
	}
	
	//��ȡҳ������
	function getValue(){
		
		var gridId = "peopleInfoGrid";
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var count = 0;
		var a0000 ='';//��Ա���
		var a0101 = '';//��Ա����
		var a0184 = '';//��Ա���֤��
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = record.a0000;
				a0101 = record.a0101;
				a0184 = record.a0184;
				count=count+1;
			}
		}
		return a0000+"@"+a0101+"@"+a0184+"@"+count;
	}
	
	/**
	 * �����ǼǱ��
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function createExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
		
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('checkPer',a0000+"@"+a0101);
		
	}	
	//��Ա�����޸Ĵ��ڴ���
	var personTabsId = [];
	function addTab(atitle, aid, src, forced, autoRefresh, param) {
		var tab = parent.tabs.getItem(aid);
		if (forced)
			aid = 'R' + (Math.random() * Math.random() * 100000000);
		if (tab && !forced) {
			parent.tabs.activate(tab);
			if (typeof autoRefresh != 'undefined' && autoRefresh) {
				document.getElementById('I' + aid).src = src;
			}
		} else {
			personTabsId.push(aid);
			parent.tabs
					.add(
							{
								title : (atitle),
								id : aid,
								tabid : aid,
								personid : aid,
								html : '<Iframe width="100%" height="100%" scrolling="auto" id="I'
										+ aid
										+ '" frameborder="0" src="'
										+ src
										+ '&a0000='+aid+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
								listeners : {//�ж�ҳ���Ƿ���ģ�

								},
								closable : true
							}).show();

		}
	}

function Ztrue(){
	alert(111);
}
function UpBtn(){
	alert(111);
}
function DownBtn(){
	alert(111);
}
function setPageSize1(){
	alert(111);
}


	Ext.onReady(function() {
		//ҳ�����
		 Ext.getCmp('peopleInfoGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_peopleInfoGrid'))[0]-4);
		 Ext.getCmp('peopleInfoGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_peopleInfoGrid'))[1]-2); 
		 document.getElementById('groupTreePanel').style.width = document.body.clientWidth;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
		 document.getElementById("tree-div").style.height = Ext.getCmp('peopleInfoGrid').getHeight()-63;
		
					
		 
	});
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
	
</script>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="�ϴ���������"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="�鿴/ɾ����������"></odin:window>
<odin:window src="/blank.htm" id="othertem" width="300" height="200" maximizable="false" title="ѡ����ģ��"></odin:window>
<odin:hidden property="ddd"/>


