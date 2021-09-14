<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.InfoPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%> 
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>




.x-panel-bwrap,.x-panel-body {
	
	height: 100%
}

.picOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}



.picInnerOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}

.picGroupOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png)
		!important;
}

#b0180 {
	width:670px !important;
}
</style>
<script type="text/javascript">
	var ctxPath = '<%=ctxPath%>';
	
	Ext.onReady(function() {
			var man = document.getElementById('manager').value;
			var Tree = Ext.tree;
			var tree = new Tree.TreePanel(
					{
						id : 'group',
						el : 'tree-div',//Ŀ��div����
						split : false,
						monitorResize :true,
						width : 198,
						minSize : 164,
						maxSize : 164,
						rootVisible : false,//�Ƿ���ʾ���ϼ��ڵ�
						autoScroll : false,//�����ݳ���Ԥ��ĸ߶�ʱ�Զ����ֹ�������
						collapseMode : 'mini',
						animate : true,
						border : false,
						enableDD : false,//�������Ľڵ��Ƿ�����϶���
						containerScroll : true,//�Ƿ��������ע�ᵽ����������ScrollManager��
						loader : new Tree.TreeLoader(//���ڵ�ļ�����
								{
						        	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
						        	dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info&eventNames=getTreeJsonData'
								 
								}),
						listeners : {
							'click': function(node){                                       //��������¼�
								treeChange(node.id);
							}
						}

					});
			
			var root = new Tree.AsyncTreeNode({
				checked : false,//��ǰ�ڵ��ѡ��״̬
				text : document.getElementById('ereaname').value,//�ڵ��ϵ��ı���Ϣ
				iconCls : document.getElementById('picType').value,//Ӧ�õ��ڵ�ͼ���ϵ���ʽ
				draggable : false,//�Ƿ�������ҷ
				id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
				href : "javascript:radow.doEvent('querybyid','"//�ڵ����������
						+ document.getElementById('ereaid').value + "')"
			});
			tree.setRootNode(root);
			tree.render();
			root.expand(true,true);
		});
	
	function saveb01(){
		radow.doEvent('saveBtn.onclick');
	}
	//��Ա�����޸Ĵ��ڴ���
	var personTabsId=[];
	
	function reloadTree() {
		var tree = Ext.getCmp("group");
		   
		//��ȡѡ�еĽڵ�  
		var node = tree.getSelectionModel().getSelectedNode();  
		
	}
	
	function cancelBtnClick(){
		radow.doEvent('querybyid', document.getElementById('b0111').value);
	}
	
	//���ݵ�������˵�����Ϣ������ʾ��Ӧ����Ϣ��¼��ҳ�� 
	function treeChange(nodeId){
		//�����Ϣ����id
		var id = nodeId.substring(5);
		
		if(id != null && id != ''){
			
			document.getElementById("A02").style.display="none";
			document.getElementById("A06").style.display="none";
			document.getElementById("A08").style.display="none";
			document.getElementById("A11").style.display="none";
			document.getElementById("A14").style.display="none";
			document.getElementById("A15").style.display="none";
			document.getElementById("A29").style.display="none";
			document.getElementById("A30").style.display="none";
			//document.getElementById("A36").style.display="none";
			document.getElementById("A37").style.display="none";
			//document.getElementById("A41").style.display="none";
			document.getElementById("A53").style.display="none";
			document.getElementById("A60").style.display="none";
			document.getElementById("A61").style.display="none";
			document.getElementById("A62").style.display="none";
			document.getElementById("A63").style.display="none";
			document.getElementById("A64").style.display="none";
			//document.getElementById("A68").style.display="none";
			//document.getElementById("A69").style.display="none";
			document.getElementById("A71").style.display="none"; 
			
			//�������ʾ 
			if(id == "All"){
				document.getElementById("A02").style.display="block";
				document.getElementById("A06").style.display="block";
				document.getElementById("A08").style.display="block";
				document.getElementById("A14").style.display="block";
				document.getElementById("A15").style.display="block";
			}else{
				document.getElementById(id).style.display="block"; 
			}
		}
		
	}
	
</script>

<!-- �������Ĺ������� -->
<script type="text/javascript"> 
	
</script>

<%
	String picType = (String) (new SysOrgPageModel().areaInfo
			.get("picType"));
	String ereaname = (String) (new SysOrgPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new SysOrgPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new SysOrgPageModel().areaInfo
			.get("manager"));
%>
<div id="main">
<odin:floatDiv property="bar_div" position="up"></odin:floatDiv>
<table>
	<tr>
		<td height="26"></td>
	</tr>
</table>
<table style="width: 100%;height: 95%;">
	<tr>
		<td valign="top" width="1px">
			<div id="tree-div" style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;">
					
				</div>
		</td>
		<td><div id="divresize" style="height: 100%;width: 10px;cursor: e-resize;"></div></td>
		<td valign="top">
		<div id="girdDiv" style="width: 100%;height: 100%;border: 2px solid #c3daf9;padding-left:40px">
				<table>
					<tr>
						<odin:hidden property="downfile"/>
						<odin:hidden property="checkedgroupid" />
						<odin:hidden property="forsearchgroupid" />
						<odin:hidden property="ereaname" value="<%=ereaname%>" />
						<odin:hidden property="ereaid" value="<%=ereaid%>" />
						<odin:hidden property="manager" value="<%=manager%>" />
						<odin:hidden property="picType" value="<%=picType%>" />
						<odin:hidden property="b0111"/>
						<odin:hidden property="b0121"/>
						<odin:hidden property="b0101old"/>
						<odin:hidden property="b0104old"/>
						<odin:hidden property="isstauts" value="0"/>
						<odin:hidden property="status" title="ɾ��״̬" ></odin:hidden>
						<odin:hidden property="a0000" title="����a0000" ></odin:hidden>
					</tr>
				</table> 
				
				<!---------------------------------------------- �ۺ���Ϣ����������Ϣ���� -------------------------------->
				
				<!-----------------------------��Ա������Ϣ------------------------------------------------------->
				<odin:groupBox property="A01" title="��Ա������Ϣ">
				<table cellspacing="2" width="440" align="left">
					<tr>
						<odin:textEdit property="a0101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����"></odin:textEdit>
						<odin:select2 property="a0104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ա�" codeType="GB2261"></odin:select2>
						<odin:NewDateEditTag property="a0107" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������" isCheck="true" maxlength="8"></odin:NewDateEditTag>
					</tr>
					<tr>
						<odin:select2 property="a0117" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����" codeType="GB3304"></odin:select2>		
						<tags:PublicTextIconEdit property="a0111" label="����" codetype="ZB01" readonly="true"></tags:PublicTextIconEdit>	
						<tags:PublicTextIconEdit property="a0114" label="������" codetype="ZB01" readonly="true"></tags:PublicTextIconEdit>	
					</tr>
					<tr>
						<%-- <tags:TextAreainput2 property="a0140" cls="width24-80 height1234-40 no-y-scroll cellbgclor" ondblclick="a0140Click()" onkeypress="a0140Click2()" readonly="true" label="�뵳ʱ��"/> --%>
						<odin:textEdit property="a0140" label="�뵳ʱ��" ondblclick="a0140Click()" onkeypress="a0140Click2()" readonly="true" onclick="a0140Click()"></odin:textEdit>
						<odin:NewDateEditTag property="a0134" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�μӹ���ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
						<odin:select2 property="a0128" label="����״��" codeType="GB2261D"></odin:select2>
					</tr>
					<tr>
						<odin:textEdit property="a0184" label="������ݺ���"></odin:textEdit>
						<odin:textEdit property="a0187a" label="ר ��"></odin:textEdit>
					</tr>
					<tr>
						<odin:textarea property="a1701" label="����" colspan='10' rows="8"></odin:textarea>
					</tr>
				</table>
				</odin:groupBox>
				
				
				
				
				<!---------------------------------- ְ����Ϣ�� -------------------------->
				
				<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
				<script type="text/javascript" src="basejs/helperUtil.js"></script>
				<script type="text/javascript" src="js/lengthValidator.js"></script>
				<%@include file="/comOpenWinInit.jsp" %>
				<script type="text/javascript">
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0200 = record.data.a0200;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a0200){ 
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
						return;
					}
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',a0200);
						}else{
							return;
						}		
					});	
				}
				
				function deleteRow(){ 
					var sm = Ext.getCmp("WorkUnitsGrid").getSelectionModel();
					if(!sm.hasSelection()){
						Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
						return;
					}
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
						return;
					}
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				Ext.onReady(function(){});
				//������λְ���������
				function a02checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0800);
					radow.doEvent('workUnitsgridchecked',sr.data.a0200);
				}
				
				
				
				function changeSelectData(item){
					var a0255f = Ext.getCmp("a0255_combo");
					var newStore = a0255f.getStore();
					newStore.removeAll();
					newStore.add(new Ext.data.Record(item.one));
					newStore.add(new Ext.data.Record(item.two));
					var keya0255 = document.getElementById("a0255").value;//alert(item.one.key+','+keya0255);
					if(item.one.key==keya0255){
						a0255f.setValue(item.one.value);
					}else if(keya0255==''){
						a0255f.setValue(item.one.value);
						document.getElementById("a0255").value=item.one.key;
					}else{
						a0255f.setValue(item.two.value);
						document.getElementById("a0255").value=item.two.key;
					}
				}
				
				var labelText={'a0255SpanId':['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>��ְ״̬','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>����״̬'],
							   'a0201bSpanId':['<font color="red">*</font>��ְ��������','<font color="red">*</font>������������'],
							   'a0216aSpanId':['<font color="red">*</font>ְ������','<font color="red">*</font>��λ����'],
							   //'a0215aSpanId':['ְ�����ƴ���','��λ���ƴ���'],
							   'a0221SpanId':['ְ����','��λ���'],
							   'a0229SpanId':['�ֹܣ����£�����','��λ����'],
							   'a0243SpanId':['��ְʱ��','������ʼ']};
							   
				function changeLabel(type){
					for(var key in labelText){
						document.getElementById(key).innerHTML=labelText[key][type];
					}
				}		   
				function a0222SelChangePage(record,index){//��λ���onchangeʱ��ְ���θ�ֵΪ��
					document.getElementById("a0221").value='';
					document.getElementById("a0221_combo").value='';
					a0221achange();
					a0222SelChange(record,index)
				}	
				function a0222SelChange(record,index){
				
					//��λ���
					//var a0222 = record.data.key;
					var a0222 = document.getElementById("a0222").value;
					var a0201b = document.getElementById("a0201b").value;
					
				
					document.getElementById("codevalueparameter").value=a0222;
					
					if("01"==a0222){//���ӳ�Ա
						//$h.selectShow('a0201d');
						selecteEnable('a0201d','0');
					}else{
						//$h.selectHide('a0201d');
						selecteDisable('a0201d');
					}
					
					if("01"==a0222||"99"==a0222){//����Ա�����չ�����Ա��λor����
						//$h.selectShow('a0219');//ְ�����
						//$h.selectShow('a0251');//ְ������
						///$h.selectShow('a0247');//ѡ�����÷�ʽ
						selecteEnable('a0219');//ְ�����
						selecteEnable('a0251');//ְ������
						selecteWinEnable('a0247');//ѡ�����÷�ʽ
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
						//$h.textShow('a0288');//����ְ����ʱ��
						//$h.dateEnable('a0288');//����ְ����ʱ��
						
						
						
						changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
						changeLabel(0);
					}else if("02"==a0222||"03"==a0222){//��ҵ��λ�����λor��ҵ��λרҵ������λ
						//$h.selectHide('a0219');//ְ�����disabled
						selecteDisable('a0219');//ְ�����disabled
						//$h.selectShow('a0251');//ְ������
						selecteEnable('a0251');//ְ������
						//$h.selectShow('a0247');//ѡ�����÷�ʽ
						selecteWinEnable('a0247');//ѡ�����÷�ʽ
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
						changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
						changeLabel(0);
						//$h.dateEnable('a0288');//����ְ����ʱ��
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						//$h.selectHide('a0219');//ְ�����
						//$h.selectHide('a0251');//ְ������
						//$h.selectHide('a0247');//ѡ�����÷�ʽ
						selecteDisable('a0219');//ְ�����
						selecteDisable('a0251');//ְ������
						selecteWinDisable('a0247');//ѡ�����÷�ʽ
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
						//$h.textHide('a0288');//����ְ����ʱ��	
						//$h.dateDisable('a0288');//����ְ����ʱ��	
						changeSelectData({one:{key:'1',value:'��ְ'},two:{key:'0',value:'����ְ'}});
						changeLabel(1);
					}else{
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
					}
					a0255SelChange();
					//a0251change();	
				}
				function a0255SelChange(){
					var a0222 = document.getElementById("a0222").value;
					if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						return;
					}
					//��ְ����ְ״̬
					var a0255Value = document.getElementById("a0255").value;
					if("1"==a0255Value){//����
						//document.getElementById('yimian').style.display="none";
						document.getElementById('yimian').style.visibility="hidden";
					}else if("0"==a0255Value){//����
						//document.getElementById('yimian').style.display="block";
						document.getElementById('yimian').style.visibility="visible";
					}
				}
				
				function setA0216aValue(record,index){//ְ����
					Ext.getCmp('a0216a').setValue(record.data.value);
				}
				function setA0255Value(record,index){
					Ext.getCmp('a0255').setValue(record.data.key)
				}
				//a01ͳ�ƹ�ϵ���ڵ�λ
				function setParentValue(record,index){
					document.getElementById('a0195key').value = record.data.key;
					document.getElementById('a0195value').value = record.data.value;
					
					var a0195 = document.getElementById("a0195").value;
					var B0194 = radow.doEvent('a0195Change',a0195);
					
				}
				function witherTwoYear(){
				
					parent.document.getElementById('a0197').value=record.data.key;
				}
				//a01����
				function setParentA0120Value(record,index){
					parent.document.getElementById('a0120').value=record.data.key;
				}
				//a01 ���㹤������
				function setParentA0194Value(record,index){
					
				}
				
				function a0201bChange(record){
					//��ְ�ṹ��� �� ְ�����ƴ����Ӧ��ϵ
					radow.doEvent('setZB08Code',record.data.key);
				}
				function a0251change(){//ְ������  �Ƹ����
					var a0251 = document.getElementById('a0251').value;
					var a0251bOBJ = document.getElementById('a0251b');
					var a0251bTD = document.getElementById('a0251bTD');
					if('26'==a0251){
						
					}else if('27'==a0251){
						a0251bOBJ.checked=true;
						
					}else{
						
					}
				}
				function setA0201eDisabled(){
					var a0201d = document.getElementById("a0201d").value;
					if("0"==a0201d){
						document.getElementById("a0201e_combo").disabled=true;
						document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
						document.getElementById("a0201e_combo").style.backgroundImage="none";
						Ext.query("#a0201e_combo+img")[0].style.display="none";
						document.getElementById("a0201e").value="";
						document.getElementById("a0201e_combo").value="";
					}else if("1"==a0201d || ""==a0201d){
						document.getElementById("a0201e_combo").readOnly=false;
						document.getElementById("a0201e_combo").disabled=false;
						document.getElementById("a0201e_combo").style.backgroundColor="#fff";
						document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
						Ext.query("#a0201e_combo+img")[0].style.display="block";
					}
				}
				</script>
				
				<odin:hidden property="a0200" title="����id" ></odin:hidden>
				<odin:hidden property="a0225" title="��Ա������" value="0"></odin:hidden>
				<odin:hidden property="a0223" title="��ְ������" ></odin:hidden>
				<odin:hidden property="a0201c" title="�������" ></odin:hidden>
				<odin:hidden property="codevalueparameter" title="ְ���κ͸�λ��������"/>
				<odin:hidden property="ChangeValue" title="ְ�����ƴ���͵�λ��������"/>
				<odin:hidden property="a0271" value="0"/>
				<odin:hidden property="a0222" value='0'/>
				<odin:hidden property="a0195key" value=''/>
				<odin:hidden property="a0195value" value=''/>
				
				<odin:groupBox title="ְ����Ϣ��" property="A02">
				
				<div id="btnToolBarDiv" style="width: 1000px;"></div>
				<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="saveA02" ></odin:buttonForToolBar>
				</odin:toolBar>
				
				<table style="width: 100%">
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="ͳ�ƹ�ϵ���ڵ�λ" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit3>
									<td rowspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label for="a0197" style="font-size: 12px;" id="a0197SpanId">�Ƿ�����������ϻ��㹤������ </label>
										<input align="middle" type="checkbox" name="a0197"  id="a0197" onclick="witherTwoYear()" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<odin:textEdit property="a0192a" width="550" label="ȫ��"  maxlength="1000"><span>&nbsp;&nbsp;(���������)</span></odin:textEdit>
									<td rowspan="2"><odin:button text="��������" property="UpdateTitleBtn" ></odin:button></td>
									<td rowspan="2"><odin:button text="����������" property="personGRIDSORT" handler="openSortWin" ></odin:button></td>
								</tr>
								<tr>
							       <odin:textEdit property="a0192" width="550" label="���"  maxlength="1000"><span>&nbsp;&nbsp;(��������)</span></odin:textEdit>
							    </tr>	
							</table>
						</td>
					</tr>
				    <tr>
					    <td width="330">
					    	<table width="330" height="300"><tr><td>
						    	<odin:editgrid property="WorkUnitsGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" 
									 height="330" title="" pageSize="50"  >
										<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								     		<odin:gridDataCol name="a0281"/>
								     		<odin:gridDataCol name="a0200" />
									  		<odin:gridDataCol name="a0201b" />
									  		<odin:gridDataCol name="a0201a" />
									  		<odin:gridDataCol name="a0215a" />
									  		<odin:gridDataCol name="a0216a" />
									  		<odin:gridDataCol name="a0222" />
									   		<odin:gridDataCol name="a0255" isLast="true"/>
									   		
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
										  <odin:gridRowNumColumn />
										  <odin:gridEditColumn2 header="���" width="100" editor="checkbox" dataIndex="a0281" checkBoxClick="a02checkBoxColClick" edited="true"/>
										  <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" width="200" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ��������" edited="false"  dataIndex="a0201b"  editor="text" width="200" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ����" edited="false" dataIndex="a0201a" renderer="changea0201a" editor="text" width="300"/>
										  <odin:gridEditColumn2 header="ְ�����ƴ���" edited="false"  dataIndex="a0215a" editor="select" codeType="ZB08" hidden="true" width="100"/>
										  <odin:gridEditColumn2 header="ְ������" edited="false"  dataIndex="a0216a" editor="text" width="200"/>
										  <odin:gridEditColumn2 header="��λ���" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ״̬" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select" width="200"/>
										  <odin:gridEditColumn header="����" width="200" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				
									</odin:gridColumnModel>
								</odin:editgrid>
								<label><input type="checkbox" checked="checked" id="xsymzw" onclick="checkChange(this)"/>��ʾ����ְ��</label>
								<div id="btngroup"> </div>
								<div style="margin-top: 8px;" id="btngroup2"> </div>
								</td>
							</tr>
						</table>
				
				
				    	</td>
				    	<td >
				    		<table>
				    			<tr  align="left">
				    				<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" required="true" onchange="a0201bChange" label="��ְ����" property="a0201b" defaultValue=""/>
				    				<odin:select2 property="a0255" label="��ְ״̬" required="true" onchange="a0255SelChange" codeType="ZB14"></odin:select2>
								</tr>
								<tr>
									<odin:textEdit property="a0216a" label="ְ������" required="true" maxlength="50"></odin:textEdit>
									<odin:select2 property="a0201d" label="�Ƿ���ӳ�Ա" data="['1','��'],['0','��']" onchange="setA0201eDisabled"></odin:select2>
								</tr>
								<tr><td><br><td></tr>
								<tr align="left">
								    <odin:select2 property="a0201e" label="��Ա���" codeType="ZB129"></odin:select2>
								    <td></td>
								    <td align="left" id="a0251bTD">
										<input align="middle" type="checkbox" name="a0251b" id="a0251b" />
										<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">�Ƹ����</label>
									</td>
								</tr>
								<tr>
									
									<tags:PublicTextIconEdit property="a0247" label="ѡ�����÷�ʽ" codetype="ZB122" readonly="true"></tags:PublicTextIconEdit>
									<td></td>
									<td align="left" id="a0219TD">
										<input align="middle" type="checkbox" name="a0219" id="a0219" />
										<label id="a0219SpanId" for="a0219" style="font-size: 12px;">�쵼ְ��</label>
									</td>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0243" labelSpanId="a0243SpanId" maxlength="8" label="��ְʱ��" ></odin:NewDateEditTag>
									<odin:textEdit property="a0245" label="��ְ�ĺ�" validator="a0245Length"></odin:textEdit>
								</tr>
								<tr align="left">
								    <odin:hidden property="a0221a" value="0"/> 
								</tr>
								<tr align="left" >
								    <odin:hidden property="a0229" value="0"/>
								    <odin:hidden property="a0251" value="0"/>
								</tr>
								<tr>
									
								</tr>
								<tr id='yimian'>
									<odin:NewDateEditTag property="a0265" label="��ְʱ��" labelSpanId="a0265SpanId"  maxlength="8"></odin:NewDateEditTag>
									<odin:textEdit property="a0267" label="��ְ�ĺ�" validator="a0267Length"></odin:textEdit>
								</tr>
								<tr>
									<!-- �¿����ְ��䶯���� tongzj 2017/5/29 -->
								</tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
				    		</table>
				    	</td>
				    </tr>
				    <tr>
				    	<td align="right" colspan="4"><div id="btngroup3" ></div></td>
				    </tr>
				</table>
				</odin:groupBox>
				
				<odin:hidden property="a0281" title="�������"/>
				<script type="text/javascript">
				Ext.onReady(function(){
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'����',
						    cls :'inline',
						    renderTo:"btngroup",
						    handler:UpBtn
						});
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'����',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:DownBtn
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'��������',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('worksort');
						    }
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'sortUseTimeS',
						    text:'����ְʱ������',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('sortUseTime');
						    }
						});
					});
					//ͳ�ƹ�ϵ���ڵ�λ����ְ������������
					function saveA02(){
						var a0201b = document.getElementById('a0201b').value;
						var a0195 = document.getElementById('a0195').value;
						if(a0195 != null && a0195!=a0201b){
							Ext.MessageBox.confirm(
								'��ʾ',
								'ͳ�ƹ�ϵ���ڵ�λ��ְ�����Ʋ���ͬ���Ƿ�������֣�',
								function (btn){
									if(btn=='yes'){
										radow.doEvent('saveWorkUnits.onclick');
									}
								}
							);
						}else{
							radow.doEvent('saveWorkUnits.onclick');
						}
					}
				</script>
				<script>
				
				function changea0201a(value, params, record,rowIndex,colIndex,ds){
					if(record.data.a0201b=='-1'){
						return '<a title="'+value+'(������)">'+value+'(������)</a>';
					}else{
						return '<a title="'+value+'">'+value+'</a>';
					}	
				}
				function seta0255Value(value, params, record,rowIndex,colIndex,ds){
					var a0222 = record.data.a0222;
					var textValue = '';
					if("01"==a0222||"99"==a0222||"02"==a0222||"03"==a0222){
					   	textValue = getTextValue({one:{key:'1',value:'����'},two:{key:'0',value:'����'}},value);
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						textValue = getTextValue({one:{key:'1',value:'��ְ'},two:{key:'0',value:'����ְ'}},value);
					}	
					return '<a title="'+textValue+'">'+textValue+'</a>';
				}
				function getTextValue(item,v){
					if(item.one.key==v){
						return item.one.value;
					}else{
						return item.two.value;
					}
				}
				function checkChange(){
					var checkbox = document.getElementById("xsymzw");
					var grid = Ext.getCmp("WorkUnitsGrid");
					var store = grid.getStore();
					var vibility;
					if(checkbox.checked){
						vibility = "block";
					}else{
						vibility = "none";
					}
					
					var len = store.data.length;
					for(var i=0;i<len;i++){
						var data = store.getAt(i).data;
						var a0255 = data.a0255;//��ְ״̬
						if(a0255=='0'){
							grid.getView().getRow(i).style.display=vibility;
						}
					}
				}
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					//alert(store.getCount());
					
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('��ְ���Ѿ��������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('��ְ���Ѿ����������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
					grid.view.refresh();
				}
				
				Ext.onReady(function(){
					
					var pgrid = Ext.getCmp("WorkUnitsGrid");
					
					
					var bbar = pgrid.getBottomToolbar();
					
					
					var dstore = pgrid.getStore();
					var firstload = true;
					dstore.on({  
				       load:{  
				           fn:function(){  
				             checkChange();
				             if(firstload){
				       		    $h.selectGridRow('WorkUnitsGrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				   
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
							//�޸�store
							for ( i=0; i<rows.length; i++){
								var rowData = rows[i];
								if (!this.copy) dstore.remove(rowData);
								dstore.insert(index, rowData);
							}
							pgrid.view.refresh();
							radow.doEvent('worksort');
						}
					});
				
				
				});
				
				function openSortWin(){
					var a0201b = document.getElementById("a0201b").value;
					if(a0201b==''){
						$h.alert('ϵͳ��ʾ��','����ѡ�����!');
						return;
					}
					parent.window.a0201b = a0201b;
					$h.openWin('A01SortGrid','pages.publicServantManage.PersonSort','����������',500,480,document.getElementById('subWinIdBussessId').value,'<%=ctxPath%>',window);
				}
				
				
				Ext.onReady(function(){
					
					Ext.getCmp('WorkUnitsGrid').setWidth(400);
					Ext.getCmp('WorkUnitsGrid').setHeight(250)
					
				});
				</script>
				
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				<div id="cover_wrap3"></div>
				
				
				
				
				<!---------------------------------- רҵ������ְ�ʸ���Ϣ�� -------------------------->
				
				<style>
				<%=FontConfigPageModel.getFontConfig()%>
				#table{position:relative;top: -12px;left:5px;}
				#table2{position:relative;top: -20px; padding: 0px;margin: 0px;height:300}
				.inline{
				display: inline;
				}
				.pl{
				margin-left: 8px;
				}
				</style>
				<script type="text/javascript">
				function setA0602Value(record,index){
					Ext.getCmp('a0602').setValue(record.data.value);
					Ext.getCmp('a0196').setValue(record.data.value);
				}
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0600 = record.data.a0600;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0600+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a0600){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA06',a0600);
						}else{
							return;
						}		
					});	
				}
				
				</script>
				
				<odin:groupBox title="רҵ������ְ�ʸ���Ϣ��" property="A06">
				<body>
				<odin:toolBar property="toolBar1" applyTo="tol1">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="professSkillAddBtn" icon="images/add.gif" ></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA06" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				</odin:toolBar>
				<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
				<div id="tol1" style="width: 1000px;"></div>
				<odin:hidden property="sortid" title="�����"/>
				<table id="table">
					<tr>
						<td>
							<div id="div1" style="width:330;">
							 <odin:editgrid property="professSkillgrid"    isFirstLoadData="false" forceNoScroll="true" url="/" applyTo="div1">
								<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								    <odin:gridDataCol name="a0699" />
									<odin:gridDataCol name="a0600" />
									<odin:gridDataCol name="a0601" />
									<odin:gridDataCol name="a0602" />
									<odin:gridDataCol name="a0604" />
									<odin:gridDataCol name="a0607" />
									<odin:gridDataCol name="a0611" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="���" width="15" editor="checkbox" checkBoxClick="a06checkBoxColClick" dataIndex="a0699" edited="true"/>
									<odin:gridColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
									<odin:gridEditColumn2 header="רҵ�����ʸ����" dataIndex="a0601" codeType="GB8561" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="רҵ�����ʸ�" dataIndex="a0602" editor="text" />
									<odin:gridColumn header="����ʸ�����" dataIndex="a0604" editor="text" />
									<odin:gridEditColumn2 header="��ȡ�ʸ�;��" dataIndex="a0607" codeType="ZB24" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="��ί���������" dataIndex="a0611" editor="text"  hidden="true"/>		
									 <odin:gridEditColumn header="����" width="15" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>		
								</odin:gridColumnModel>
							 </odin:editgrid>
							</div>
							<div id="btngroupA06"> </div>
						</td>
						<td>
						  <div>
							<table id="table2">
								<tr>
									<odin:textEdit property="a0196" label="רҵ����ְ��" readonly="true"></odin:textEdit>
								</tr>
								<tr>
									<tags:PublicTextIconEdit property="a0601" label="רҵ�����ʸ�" onchange="setA0602Value" required="true" readonly="true" codetype="GB8561"></tags:PublicTextIconEdit>	
								</tr>
								<tr>
									<odin:textEdit property="a0602" label="רҵ�����ʸ�����" validator="a0602Length"></odin:textEdit>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0604" label="����ʸ�����" maxlength="8" ></odin:NewDateEditTag>	
								</tr>
								<tr>
									<odin:select2 property="a0607" label="��ȡ�ʸ�;��" codeType="ZB24"></odin:select2>		
								</tr>
								<tr>
									<odin:textEdit property="a0611" label="��ί���������" validator="a0611Length"></odin:textEdit>
									<odin:hidden property="a0600" title="����id" ></odin:hidden>		
								</tr>
							</table>
						  </div>
						</td>
					</tr>
					
					</table>
					
				
				<odin:hidden property="a0699" title="���"/>
				 </div>
				</body>
				</odin:groupBox>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("professSkillgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				       		 if(firstload){
				       		    $h.selectGridRow('professSkillgrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				
				});
				
				</script>
				<script type="text/javascript">
				Ext.onReady(function(){
					 $h.applyFontConfig($h.spFeildAll.a06);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a06);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('professSkillgrid');
						var gridobj = document.getElementById('forView_professSkillgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled); 
				
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
				Ext.onReady(function(){
					new Ext.Button({
						icon : 'images/icon/arrowup.gif',
						id:'UpBtnA06',
					    text:'����',
					    cls :'inline',
					    renderTo:"btngroupA06",
					    handler:UpBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/arrowdown.gif',
						id:'DownBtnA06',
					    text:'����',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:DownBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/save.gif',
						id:'saveSortBtnA06',
					    text:'��������',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:function(){
							radow.doEvent('worksortA06');
					    }
					});
					
					Ext.getCmp('professSkillgrid').setHeight(375);
				});
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					//alert(store.getCount());
					
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('��ְ���Ѿ��������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('��ְ���Ѿ����������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
					grid.view.refresh();
				}
				
				function a06checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0600);
					radow.doEvent('updateA06',sr.data.a0600);
				}
				
				</script>	
				
				
				
				
				<!---------------------------------------------- ѧ��ѧλ��Ϣ��-------------------------------->
				
				<script type="text/javascript">
				
				function setA0801aValue(record,index){//ѧλ
					Ext.getCmp('a0801a').setValue(record.data.value);
				}
				function setA0901aValue(record,index){//ѧ��
					Ext.getCmp('a0901a').setValue(record.data.value);
				}
				function setA0824Value(record,index){//רҵ
					Ext.getCmp('a0824').setValue(record.data.value);
				}
				function onkeydownfn(id){
					if(id=='a0801b')
						Ext.getCmp('a0801a').setValue('');
					if(id=='a0901b')
						Ext.getCmp('a0901a').setValue('');
					if(id=='a0827')
						Ext.getCmp('a0824').setValue('');
				}
				odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				};
				//ѧ��ѧλ�������
				function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					var msg='';
					if(sr.data.a0899==='true'||sr.data.a0899===true){
						msg = 'ȡ���ü�¼��,��ѧ��ѧλ���������<br/>ȷ��Ҫȡ������ü�¼��?';
					}else{
						msg = 'ѡ��ü�¼�󣬸�ѧ��ѧλ�����<br/>ȷ��Ҫѡ������ü�¼��?';
					}
					$h.confirm('ϵͳ��ʾ',msg,220,function(id){
						if("ok"==id){
							radow.doEvent('degreesgridchecked',sr.data.a0800);
						}else{
							
							return false;
						}
					});
				}
				
				
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a0800 = record.data.a0800;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a0800){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA08',a0800);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<odin:toolBar property="toolBar2" applyTo="tol2" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA08" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="ѧ��ѧλ��Ϣ��" property="A08">
					<div>
					<odin:hidden property="a0800" title="����id" ></odin:hidden>
					<odin:hidden property="a0834" title="���ѧ����־" />
					<odin:hidden property="a0835" title="���ѧλ��־" />
					<input type="reset" name="reset" id="resetbtn" style="display: none;" />
					<div id="tol2"></div>
					<table>
						<tr>
							<td>
								<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" topBarId="toolBar2" url="/"   
								 height="210" >
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0899"/>
							     		<odin:gridDataCol name="a0800" />
								  		<odin:gridDataCol name="a0837" />
								  		<odin:gridDataCol name="a0801b" />
								   		<odin:gridDataCol name="a0901b" />
								   		<odin:gridDataCol name="a0814" />
								   		<odin:gridDataCol name="a0827" />			   		
								   		<odin:gridDataCol name="a0811" />
								   		<odin:gridDataCol name="a0804" />
								   		<odin:gridDataCol name="a0807" />
								   		<odin:gridDataCol name="a0904" />
								   		<odin:gridDataCol name="a0801a" />
								   		<odin:gridDataCol name="a0901a" />
								   		<odin:gridDataCol name="a0824" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="���" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
									  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="���" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
									  <odin:gridEditColumn header="ѧ��" dataIndex="a0801a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="ѧλ" dataIndex="a0901a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" edited="false" editor="text"/>
									  <odin:gridEditColumn header="רҵ" dataIndex="a0824" edited="false" editor="text" />
									  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0837" label="�������" required="true" codeType="ZB123"></odin:select2></tr>
									<tr><tags:PublicTextIconEdit property="a0801b" label="ѧ������" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0801a" label="ѧ������" validator="a0801aLength"></odin:textEdit></tr>
									<tr><odin:numberEdit property="a0811" label="ѧ������(��)" maxlength="3"></odin:numberEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0901b" label="ѧλ����" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0901a" label="ѧλ����" validator="a0901aLength"></odin:textEdit></tr>
									<tr> <odin:textEdit property="a0814" label="ѧУ����λ������" validator="a0814Length"></odin:textEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0827" label="��ѧרҵ���" onchange="setA0824Value" codetype="GB16835" readonly="true" /></tr>
									<tr><odin:textEdit property="a0824" label="��ѧרҵ����" validator="a0824Length"></odin:textEdit></tr>
									<tr><odin:NewDateEditTag property="a0804" label="��ѧʱ��"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><odin:NewDateEditTag property="a0807" label="�ϣ��ޣ�ҵʱ��" maxlength="8"></odin:NewDateEditTag></tr>
									<tr><odin:NewDateEditTag property="a0904" label="ѧλ����ʱ��" maxlength="8"></odin:NewDateEditTag></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
				</odin:groupBox>
				<odin:hidden property="a0899" title="���"/>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("degreesgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('degreesgrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('degreesgrid');
						var gridobj = document.getElementById('forView_degreesgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('degreesgrid').setHeight(400);
						 Ext.getCmp('degreesgrid').setWidth(570); 
						 document.getElementById('toolBar2').style.width = 863;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>
				<div id="cover_wrap1"></div>
				
				
				<!---------------------------------------------- ������Ϣ�� -------------------------------->
				
				<script type="text/javascript">
				
				//������Ϣ׷��
				function appendRewardPunish(){ 
					var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
					if(!sm.hasSelection()){
						alert("��ѡ��һ�����ݣ�");
						return;
					}
					radow.doEvent('appendonclick',sm.lastActive+'');
				}
				function setA1404aValue(record,index){//��������
					Ext.getCmp('a1404a').setValue(record.data.value);
				}
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a1400 = record.data.a1400;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a1400+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a1400){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA14',a1400);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				
				<odin:toolBar property="toolBar5" applyTo="tol3">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="RewardPunishAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA14" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="������Ϣ��" property="A14">
					<div>
					<div id="tol3" style="width: 1000px;"></div>
					<div id="wzms">
						<table>
							<tr>
								<odin:textarea property="a14z101" cols="80" rows="4" colspan="5" label="��������" validator="a14z101Length"></odin:textarea>
							</tr>
						</table>
					</div>
					<div id="table1">
						
						 <table>
						 	<tr>
						 		<td>
						 					<odin:grid property="RewardPunishGrid" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
								 height="200">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1400" />
								  		<odin:gridDataCol name="a1404b" />
								  		<odin:gridDataCol name="a1404a" />
								   		<odin:gridDataCol name="a1415" />
								   		<odin:gridDataCol name="a1414" />
								   		<odin:gridDataCol name="a1428" />			   		
								   		<odin:gridDataCol name="a1411a" />
								   		<odin:gridDataCol name="a1407" />
								   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text"/>
									  <odin:gridEditColumn2 header="�������ƴ���" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
									  <odin:gridEditColumn  header="��������"  dataIndex="a1404a" edited="false" editor="text" />
									  <odin:gridEditColumn2 header="�ܽ���ʱְ����" dataIndex="a1415" edited="false" codeType="ZB09" editor="select"/>
									  <odin:gridEditColumn2 header="��׼���ؼ���" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
									  <odin:gridEditColumn2 header="��׼��������" dataIndex="a1428" edited="false" codeType="ZB128" editor="select" hidden="true"/>
									  <odin:gridEditColumn header="��׼����" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
									  <odin:gridEditColumn header="��׼����" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
									  <odin:gridEditColumn header="���ͳ�������" dataIndex="a1424" edited="false" editor="text" maxLength="8" hidden="true"/>
									   <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
									</td>
									<td>
										<table id="table2">
											<tr id="btn" height="35px;">
												<td><odin:button text="׷�ӵ�ǰ��" handler="appendRewardPunish" property="append"></odin:button> </td>
												<td id="btn2"><odin:button text="ȫ���滻" property="addAll"></odin:button> </td>
											</tr>
											<tr height="35px;">
											<tags:PublicTextIconEdit property="a1404b" label="�������ƴ���" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
											</tr>
											<tr height="35px;"><odin:textEdit property="a1404a" label="��������" ></odin:textEdit></tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1415" label="�ܽ���ʱְ����" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:select2 property="a1414" label="��׼���ؼ���"  codeType="ZB03"></odin:select2>	</tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1428" label="��׼��������" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:textEdit property="a1411a" label="��׼����" ></odin:textEdit></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1407" label="��׼����" maxlength="8" isCheck="true"></odin:NewDateEditTag></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1424" label="���ͳ�������" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
											<odin:hidden property="a1400" title="����id" ></odin:hidden>
										</table>
									</td>
									</tr>
								</table>
					</div>
					</div>
				</odin:groupBox>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("RewardPunishGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           	 	$h.selectGridRow('RewardPunishGrid',0);
				           	 	firstload = false;
				           	 }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a14);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a14);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('RewardPunishGrid');
						var gridobj = document.getElementById('forView_RewardPunishGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className="divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
					     Ext.getCmp('RewardPunishGrid').setHeight(350);
						 Ext.getCmp('RewardPunishGrid').setWidth(507); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				
				
				
				<!---------------------------------------------- ������Ϣ�� -------------------------------->
				
				<script type="text/javascript">
				
				
				function changedispaly(obj){
					var choose = Ext.getCmp('a0191').getValue();	
					if(choose){
						document.getElementById('choose').style.visibility='visible';
					}else{
						document.getElementById('choose').style.visibility='hidden';
					}
				}
				
				function yearChange(){
				    var now = new Date();
				    var year = now.getFullYear();
				    var yearList = document.getElementById("a1521");
				    for(var i=0;i<=50;i++){
				        year = year-1;
				        yearList.options[i] = new Option(year,year);
				    }
				}
				
				function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
					var a1500 = record.data.a1500;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a1500+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a1500){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA15',a1500);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				
				<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv2">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="AssessmentInfoAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA15" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<odin:groupBox title="������Ϣ��" property="A15">
				<div>
				<div id="btnToolBarDiv2" align="center" style="width: 1000px;"></div>
				<odin:hidden property="a1500" title="����id" ></odin:hidden>
				<div id="wzms">
					<table>
						<tr>
							<td><odin:textarea property="a15z101" cols="70" rows="4" colspan="4" label="��������" validator="a15z101Length"></odin:textarea></td>
							<td><div id="choose" style="visibility: hidden;">
						<table><odin:numberEdit property="a1527" label="ѡ����ȸ���" size="6"></odin:numberEdit></table>
						</div></td>
						
						<td id="td"><odin:checkbox property="a0191" label="���б����" onclick="changedispaly(this)"></odin:checkbox></td>
						
						</tr>
					</table>
				</div>
				<div id="grid">
					<table>
						<tr>
							<td>
								<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
							 height="200">
								<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="a1500" />
							  		<odin:gridDataCol name="a1521" />
							   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								  <odin:gridRowNumColumn />
								  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
								  <odin:gridEditColumn header="���" dataIndex="a1521" edited="false" editor="text"/>
								  <odin:gridEditColumn2  header="���˽������"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"/>
								   <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
								</odin:gridColumnModel>
							</odin:grid>	
							</td>
							<td>
								<table>
									<tr height="50">
										<odin:select2 property="a1521" label="�������" required="true" maxlength="4" multiSelect="true" ></odin:select2>
									</tr>
									<tr height="50">
										<tags:PublicTextIconEdit property="a1517" label="���˽������" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				</div>
				</odin:groupBox>
				<script type="text/javascript">
				Ext.onReady(function(){
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a15);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('AssessmentInfoGrid');
						var gridobj = document.getElementById('forView_AssessmentInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className= "divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth;	
						 Ext.getCmp('AssessmentInfoGrid').setWidth(535); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>
				<div id="cover_wrap1"></div>
				<div id="cover_wrap2"></div>
				
				
				
				
				
				<!---------------------------------------------- ҵ����Ϣ�� -------------------------------->
				
				<script type="text/javascript">
				function formcheck(){
					return odin.checkValue(document.forms.commForm);
				}
				function setA6004Value(record,index){//ѧ��
					Ext.getCmp('a6004').setValue(record.data.value);
				}
				function setA6006Value(record,index){//ѧλ
					Ext.getCmp('a6006').setValue(record.data.value);
				}
				function setA6108Value(record,index){//ѧ��
					Ext.getCmp('a6108').setValue(record.data.value);
				}
				function setA6110Value(record,index){//ѧλ
					Ext.getCmp('a6110').setValue(record.data.value);
				}
				function saveTrain(){
					var a1107 = document.getElementById('a1107').value;//��ѵ��ʼʱ��
					var a1111 = document.getElementById('a1111').value;//��ѵ����ʱ��
					var text1 = dateValidate(a1107);
					var text2 = dateValidate(a1111);
					if(text1!==true){
						parent.$h.alert('ϵͳ��ʾ','��ѵ��ʼʱ��' + text1, null,400);
						return false;
					}
					if(text2!==true){
						parent.$h.alert('ϵͳ��ʾ','��ѵ����ʱ��' + text2, null,400);
						return false;
					}
					radow.doEvent('saveA11.onclick');
				}
				//�������ڲ��Ǹ����� �޷���ʾ�ڵ�ǰλ�á�
				function deleteRowA60(){ 
					var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
					if(!sm.hasSelection()){
						parent.$h.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
						return;
					}
					parent.Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA60',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				</script>
				<odin:groupBox property="A60" title="����¼����Ա��Ϣ">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a6001" label="����¼����Ա" codeType="XZ09"/>
								<odin:NewDateEditTag property="a6002" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ��" maxlength="80"/>
								<odin:select2 property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ������ò" labelSpanId="a6003SpanId" validator="a6003Length"  codeType="GB4762" ></odin:select2>
								
								<tags:PublicTextIconEdit property="a6009" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ա��Դ���" codetype="ZB146" readonly="true" />
							</tr>
							<tr>
							
								<tags:PublicTextIconEdit property="a6005" label="¼��ʱѧ������" onchange="setA6004Value" codetype="ZB64" readonly="true" />
								<odin:textEdit property="a6004" label="¼��ʱѧ������" validator="a6004Length" />
								
								<tags:PublicTextIconEdit property="a6007" label="¼��ʱѧλ����" onchange="setA6006Value" codetype="GB6864" readonly="true" />
								<odin:textEdit property="a6006" label="¼��ʱѧλ����" validator="a6006Length" />
								
							</tr>
							<tr>
								<odin:numberEdit property="a6008" label="&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ���㹤��ʱ��" maxlength="2" />
								<odin:select2 property="a6010" label="�Ƿ���������Ŀ��Ա" codeType="XZ09" />
								<odin:select2 property="a6011" label="�Ƿ�����ʿ��" codeType="XZ09" />
								<odin:select2 property="a6012" label="�Ƿ����۴�ѧ��ʿ��" codeType="XZ09" />
							</tr>
							<tr>
								<odin:select2 property="a6013" label="�Ƿ�м���" codeType="XZ09"/>
								<odin:select2 property="a6014" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
								<odin:numberEdit property="a6015" label="��ѧ����" maxlength="2"/>
								<odin:select2 property="a6016" label="�Ƿ��к��⹤������" codeType="XZ09"/>
							</tr>
							<tr>
								<odin:textEdit property="a6017" label="���⹤������" validator="a6_101716Length"/>
							</tr>
							<tr>
								<odin:textEdit property="a6401" label="����׼��֤��" validator="a6401Length" maxlength="50"/>
								<odin:numberEdit property="a6402" label="����ְҵ��������" maxlength="3"/>
								<odin:numberEdit property="a6403" label="���۷���" maxlength="3"/>
								<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3" />
							</tr>
							<tr>
								<odin:numberEdit property="a6405" label="רҵ�������Է���" maxlength="3"/>
								<odin:numberEdit property="a6406" label="������Ŀ���Գɼ��ܷ�" maxlength="3"/>
								<odin:numberEdit property="a6407" label="רҵ���Գɼ�" maxlength="3"/>
								<odin:numberEdit property="a6408" label="���Գɼ�" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A61" title="ѡ������Ϣ">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2970" label="ѡ����" codeType="ZB137" />
								<odin:select2 property="a2970a" label="ѡ������Դ" codeType="ZB138" />
								<odin:textEdit property="a2970b" label="ѡ������ʼ������λ" validator="a2970bLength"></odin:textEdit>
								<odin:NewDateEditTag property="a6104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ѡ����ʱ��" maxlength="8" />
							</tr>
							<tr>
								<tags:PublicTextIconEdit property="a6109" label="ѡ��ʱѧ������" onchange="setA6108Value" codetype="ZB64" readonly="true" />
								<odin:textEdit property="a6108" label="ѡ��ʱѧ������" validator="a6108Length"></odin:textEdit>
								<tags:PublicTextIconEdit property="a6111" label="¼��ʱѧλ����" onchange="setA6110Value" codetype="GB6864" readonly="true" />
								<odin:textEdit property="a6110" label="¼��ʱѧλ����" validator="a6110Length"/>
							</tr>
							<tr>
								<odin:numberEdit property="a2970c" label="�ڻ���������ع���ʱ��" maxlength="4">��</odin:numberEdit>
								<odin:select2 property="a6107" label="ѡ��ʱ������ò" labelSpanId="a6107SpanId" validator="a6107Length"   codeType="GB4762" ></odin:select2>
								
								<odin:select2 property="a6112" label="�Ƿ����۴�ѧ��ʿ��" codeType="XZ09"/>
								<odin:select2 property="a6113" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
							</tr>
							<tr>
								<odin:numberEdit property="a6114" label="��ѧ����" maxlength="2"/>
								<odin:select2 property="a6115" label="�Ƿ��к��⹤������" codeType="XZ09"/>
								<odin:textEdit property="a6116" label="���⹤������" validator="a6_101716Length"/>
							</tr>
							<tr>
								<odin:textEdit property="a6401_1" label="����׼��֤��" validator="a6401_1Length" maxlength="25"/>
								<odin:numberEdit property="a6402_1" label="����ְҵ��������" maxlength="3"/>
								<odin:numberEdit property="a6403_1" label="���۷���" maxlength="3"/>
								<odin:numberEdit property="a6404_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3"/>
							</tr>
							<tr>
								<odin:numberEdit property="a6405_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;רҵ�������Է���" maxlength="3"/>
								<odin:numberEdit property="a6406_1" label="������Ŀ���Գɼ��ܷ�" maxlength="3"/>
								<odin:numberEdit property="a6407_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;רҵ���Գɼ�" maxlength="3"/>
								<odin:numberEdit property="a6408_1" label="���Գɼ�" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A62" title="������ѡ��Ϣ">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2950" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ѡ" codeType="XZ09"></odin:select2>
								<odin:select2 property="a6202" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѡ���" codeType="ZB142" />
								<odin:select2 property="a6203" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѡ��ʽ" codeType="ZB143" />
								<odin:select2 property="a6204" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ��λ�㼶" codeType="ZB141" />
							</tr>
							<tr>
								<odin:NewDateEditTag property="a6205" label="��ѡʱ��" maxlength="8"/>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox property="A63" title="����ѡ����Ϣ">
						<table cellspacing="2" width="460" align="left">
							<tr>
								<odin:select2 property="a2951" label="����ѡ��" codeType="XZ09"></odin:select2>
								<odin:select2 property="a6302" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ѡ�����" codeType="ZB142" />
								<odin:select2 property="a6303" label="ԭ��λ���" codeType="ZB144" />
								<odin:select2 property="a6304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ��λ�㼶" codeType="ZB141" />
							</tr>
							<tr>
								<odin:select2 property="a6305" label="ԭ��λְ�ƻ�ְ��" codeType="ZB145" />
								<odin:NewDateEditTag property="a6306" label="����ѡ��ʱ��" maxlength="8"/>
								<odin:select2 property="a6307" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
								<odin:numberEdit property="a6308" label="��ѧ����" maxlength="2"/>
							</tr>
							<tr>
								<odin:select2 property="a6309" label="&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ��к��⹤������" codeType="XZ09"/>
								<odin:textEdit property="a6310" label="���⹤������" validator="a6_101716Length"/>
							</tr>
						</table>
					</odin:groupBox>
					
					<odin:groupBox property="A64" title="������Ϣ">
						<table cellspacing="2" width="100%" align="center">
							<tr>
								<odin:numberEdit property="a6401" label="����׼��֤��" maxlength="25"/>
								<odin:numberEdit property="a6402" label="����ְҵ��������" maxlength="3"/>
								<odin:numberEdit property="a6403" label="���۷���" maxlength="3"/>
								<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3"/>
							</tr>
						</table>
					</odin:groupBox>
					
					<odin:groupBox property="A11" title="��ѵ��Ϣ">
					<odin:toolBar property="toolBar8" applyTo="tol4">
									<odin:fill></odin:fill>
									<odin:buttonForToolBar text="����" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
									<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
									<odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRowA60"></odin:buttonForToolBar>
					</odin:toolBar>
					
					<!--<div style="border: 1px solid #99bbe8;">-->
					<div id="border">
					<div id="tol4" align="left"></div>
					<odin:hidden property="a1100" title="����id"></odin:hidden>
					
					<table cellspacing="2" width="460" align="left">
						<tr>
							<odin:textEdit property="a1131" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ������" validator="a1131Length"></odin:textEdit>
							<odin:select2 property="a1101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ���" codeType="ZB29"></odin:select2>
							<odin:textEdit property="a1114" label="��ѵ���쵥λ" validator="a1114Length"></odin:textEdit>
							<odin:textEdit property="a1121a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ��������" validator="a1121aLength"></odin:textEdit>
						</tr>
						<tr>
							<odin:NewDateEditTag property="a1107" isCheck="true" label="��ѵ��ʼʱ��" maxlength="8"></odin:NewDateEditTag>
							<odin:NewDateEditTag property="a1111" isCheck="true" label="��ѵ����ʱ��" maxlength="8"></odin:NewDateEditTag>
							<odin:numberEdit property="a1107c" label="��ѵʱ��" decimalPrecision="1" maxlength="4"></odin:numberEdit>
						    <odin:numberEdit property="a1108" label="ѧʱ" decimalPrecision="1" maxlength="4"></odin:numberEdit>
						
						</tr>
						<tr style="display: none;">
						    <odin:textEdit property="a1107a" label="" readonly="true">��</odin:textEdit>
							<odin:textEdit property="a1107b" label="��" readonly="true">��</odin:textEdit>
						</tr>
						<tr>
						    <odin:select2 property="a1127" label="��ѵ�������" codeType="ZB27"></odin:select2>
						    <odin:select2 property="a1104" label="��ѵ���״̬" codeType="ZB30"></odin:select2>
						     <odin:select2 property="a1151" label="��������������ѵ��ʶ" data="['1','��'],['0','��']"></odin:select2>
						</tr>
						<tr>
						   
						</tr>
						<tr>
							<td colspan="8">
								
								<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
								 height="230">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1100" />
								  		<odin:gridDataCol name="a1101" />
								  		<odin:gridDataCol name="a1131" />
								  		<odin:gridDataCol name="a1107" />
								  		<odin:gridDataCol name="a1111" />
								  		<odin:gridDataCol name="a1107a" />
								  		<odin:gridDataCol name="a1107b" />
								  		<odin:gridDataCol name="a1107c" type="float"/>
								  		<odin:gridDataCol name="a1108" type="float"/>
								  		<odin:gridDataCol name="a1114" />
								  		<odin:gridDataCol name="a1121a" />
								  		<odin:gridDataCol name="a1127" />
								  		<odin:gridDataCol name="a1104" />			
								   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text"  width="100" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="��ѵ���" dataIndex="a1101" editor="select" codeType="ZB29" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵ������" dataIndex="a1131" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵ��ʼʱ��" dataIndex="a1107" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵ����ʱ��" dataIndex="a1111" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵʱ�����£�" dataIndex="a1107a" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="��" dataIndex="a1107b" editor="text" width="100" edited="false" hidden="true"/>
									  <odin:gridEditColumn header="��ѵʱ��" dataIndex="a1107c" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="ѧʱ" dataIndex="a1108" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵ���쵥λ" dataIndex="a1114" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ѵ��������" dataIndex="a1121a" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn2 header="��ѵ�������" dataIndex="a1127" editor="select" edited="false" codeType="ZB27" width="100"/>
									  <odin:gridEditColumn2 header="��ѵ���״̬" dataIndex="a1104" editor="select" edited="false" codeType="ZB30" width="100"/>
									  <odin:gridEditColumn2 header="��������������ѵ��ʶ" dataIndex="a1151" editor="select" edited="false" selectData="['1','��'],['0','��']" width="100" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
							</td>
						</tr>
					</table>
					</div>
					</odin:groupBox>
					
					
						<!-- <script type="text/javascript">
						 Ext.onReady(function(){
						 	var newnode = document.createElement('span');
						 	newnode.style.setAttribute("visibility","hidden");   
						 	newnode.appendChild(document.createTextNode("      a"));
						 	var objj = document.getElementById('a0192d_combo').parentNode;
						 	objj.appendChild(newnode);
						 });
						</script> -->
						<!-----------------------------�������------------------------------------------------------->
						<odin:groupBox property="A29" title="�������">
						<table cellspacing="2" width="440" align="left">
						
							<tr>
							 	<odin:NewDateEditTag property="a2907"  label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���뱾��λ����" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
								<tags:PublicTextIconEdit property="a2911" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���뱾��λ�䶯���"  codetype="ZB77" readonly="true"></tags:PublicTextIconEdit>		
								<odin:textEdit property="a2941" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ԭ��λְ��"  validator="a2941Length"></odin:textEdit>
								
							</tr>
							<tr>
								<odin:NewDateEditTag property="a2947" label="���빫��Ա����ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a2944" label="��ԭ��λְ����" validator="a2944Length"></odin:textEdit>
								<odin:textEdit property="a2921a" label="���뱾��λǰ������λ����" validator="a2921aLength"></odin:textEdit>
							</tr>
							<tr>
								<odin:NewDateEditTag property="a2949" label="����Ա�Ǽ�ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
								
								<td align="right" style="padding-right: 8px;"><span id="a2947aSpanId" style="font-size: 12" >���뱾��λʱ���㹤������ʱ��</span> </td>
								<td colspan="1" align="left">
									<table cellpadding="0" cellspacing="0">
									  <tr style="padding-left: 0px;margin-left: 0px;">
									    <odin:numberEdit property="a2947a"   width="70" decimalPrecision="0" maxlength="4" minValue="0">��</odin:numberEdit>
										<odin:numberEdit property="a2947b" width="70" maxlength="2" maxValue="12" decimalPrecision="0" minValue="0">��</odin:numberEdit>
									  </tr>
									</table>
								</td>
							</tr>
						</table>
						</odin:groupBox>
						
						<script type="text/javascript">
						
						</script>
						<script type="text/javascript">
						//�������ڲ��Ǹ����� �޷���ʾ�ڵ�ǰλ�á�
						function a3101change(){ 
							var a3101 = document.getElementById('a3101').value;
							if(a3101!=null&&a3101!=''){
								odin.setSelectValue('a3001','31');
							}
							var codeType = "orgTreeJsonData";
							var codename = "code_name";
						    var winId = "winId"+Math.round(Math.random()*10000);
						    var label = "ѡ�������λ";
						  	var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
							
							var url = "pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
							
						    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
						}
						function a3140change(){
							var a3107 = document.getElementById('a3107').value;
							var a3109 = document.getElementById('a3109').value;
							if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
								selecteWinEnable('a3109');
								selecteWinEnable('a3107');
								return;
							}
							if(a3107==null||a3107==''){
								selecteWinEnable('a3109');
							}else{
								selecteWinDisable('a3109');
							}
						}
						function a3141change(){
							var a3109 = document.getElementById('a3109').value;
							var a3107 = document.getElementById('a3107').value;
							if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
								selecteWinEnable('a3109');
								selecteWinEnable('a3107');
								return;
							}
							if(a3109==null||a3109==''){
								selecteWinEnable('a3107');
							}else{
								selecteWinDisable('a3107');
							}
						}
						function onkeydownfn(id){
							if(id=='a3107'){
								a3140change();
							}else if(id=='a3109'){
								a3141change();
							}else if(id=='a2911'){
							}else if(id=='a0120'){
								var record = {data:{value:'',key:''}};
								setParentA0120Value(record);
							}else if(id=='a0122'){
								var record = {data:{value:'',key:''}};
								setParenta0122Value(record);
							}
						}
						</script>
						<!-----------------------------����------------------------------------------------------->
						<odin:groupBox property="A30" title="���˺��˳�����">
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:select2 property="a3101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������" onchange="a3101change" codeType="ZB132"></odin:select2>
								<odin:NewDateEditTag property="a3104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������׼����" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a3137" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������׼�ĺ�" validator="a3137Length"></odin:textEdit>		
							</tr>
							<tr>		
								<tags:PublicTextIconEdit property="a3110" label="����ǰ����" codetype="ZB134" readonly="true"></tags:PublicTextIconEdit>	
								<odin:textEdit property="a3117a" label="���˺����λ" validator="a3117aLength"></odin:textEdit>	
								<tags:PublicTextIconEdit property="a3001" label="�˳�����ʽ" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>	
							</tr>
							<tr>
								<odin:textEdit property="a3034" label="��ע" validator="a3034Length"></odin:textEdit>		
							
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------������------------------------------------------------------->
						<odin:groupBox property="A53" title="������">
						<odin:hidden property="a5399" title="���id" />
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5304Length"></odin:textEdit>
								<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5315Length"></odin:textEdit>
								<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������" validator="a5317Length"></odin:textEdit>
							</tr>
							<tr>
								<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
								<odin:NewDateEditTag property="a5321" label="��������ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:NewDateEditTag property="a5323" label="���ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
								<odin:textEdit property="a5327" label="�����" validator="a5327Length"></odin:textEdit>
							</tr>
							<tr>
								<odin:textEdit property="a5319" label="�ʱ���λ" validator="a5319Length"></odin:textEdit>
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------סַͨѶA37------------------------------------------------------->
						<odin:groupBox property="A37" title="סַͨѶ">
						<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textEdit property="a3701" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�칫��ַ" colspan="4" width="406" validator="a3701Length"></odin:textEdit>
								<odin:textEdit property="a3707a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�칫�绰" validator="a3707fLength"></odin:textEdit>
								<odin:textEdit property="a3707c" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ƶ��绰" validator="a3707cLength"></odin:textEdit>
										
							</tr>
							<tr>
								<odin:textEdit property="a3707b" label="סլ�绰" validator="a3707aLength"></odin:textEdit>
								<odin:textEdit property="a3707e" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����绰"  validator="a3707cLength"></odin:textEdit>
								<odin:textEdit property="a3708" label="��������" colspan="4" validator="$h.email" width="407" maxlength="60"></odin:textEdit>	
							</tr>
							<tr>
								<odin:textEdit property="a3711" label="��ͥ��ַ" colspan="4" width="406" validator="a3711Length"></odin:textEdit>
								<odin:textEdit property="a3714" label="סַ�ʱ�" validator="postcode" colspan="4" width="407" maxlength="6"></odin:textEdit>	
								
							</tr>
						</table>
						</odin:groupBox>
						<!-----------------------------��ע------------------------------------------------------->
						<odin:groupBox property="A71" title="��ע" >
							<table cellspacing="2" width="440" align="left">
							<tr>
								<odin:textarea property="a7101" label="��ע" colspan='100' rows="4" validator='a7101Length'></odin:textarea>
							</tr>
						</table>
							
						</odin:groupBox>
						<odin:hidden property="a7100"/>
						
			</div>
			
		</td>
	</tr>
</table>
</div>





<script type="text/javascript">
$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	$("#b0104SpanId").prepend("<font color='red'>&nbsp;&nbsp;&nbsp;*</font>");
});


</script>
<script type="text/javascript">
$(function() {
    //����Ҫ��ק�ı��С��Ԫ�ض��� 
    bindResize(document.getElementById('divresize'),document.getElementById('tree-div'),document.getElementById('girdDiv'));
});

function bindResize(el,treeDiv,girdDiv) {
    //��ʼ������ 
    var els = treeDiv.style,
    girdEls = girdDiv.style,
    //���� X �� Y ������ 
    x = y = x2 = y2 = 0;
    //��갴�º��¼�
    $(el).mousedown(function(e) {
        //����Ԫ�غ󣬼��㵱ǰ����������������� 
        x = e.clientX - treeDiv.offsetWidth;
   		y = e.clientY - treeDiv.offsetHeight;
        //��֧�� setCapture ��Щ���� 
        el.setCapture ? (
        //��׽���� 
        el.setCapture(),
        //�����¼� 
        el.onmousemove = function(ev) {
            mouseMove(ev || event)
        },
        el.onmouseup = mouseUp
    ) : (
        //���¼� 
        $(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp)
    );
        //��ֹĬ���¼����� 
        e.preventDefault();
    });
    //�ƶ��¼� 
    function mouseMove(e) {
        //���泬���޵�������... 
        els.width = e.clientX - x + 'px';
        var tree = Ext.getCmp('group')
        tree.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	document.getElementById('girdDiv').style.height=document.body.clientHeight-objTop(document.getElementById('girdDiv'))[0]-4;
    }
    //ֹͣ�¼� 
    function mouseUp() {
        //��֧�� releaseCapture ��Щ���� 
        el.releaseCapture ? (
        //�ͷŽ��� 
        el.releaseCapture(),
        //�Ƴ��¼� 
        el.onmousemove = el.onmouseup = null
	    ) : (
	        //ж���¼� 
	        $(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
	    );
    }
} 
	
	//������ҳ���λ��
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
	Ext.onReady(function() {
		//ҳ�����
		document.getElementById("main").style.width = document.body.clientWidth + "px";
		document.getElementById("main").style.height = document.body.clientHeight + "px";
		document.getElementById("bar_div").style.width = document.getElementById("main").style.height-1 + "px";
	});
	
	
	
	Ext.onReady(function(){
		
		document.getElementById("A02").style.display="none";		//ְ����Ϣ��
		document.getElementById("A06").style.display="none";		//רҵ������ְ�ʸ���Ϣ��
		document.getElementById("A08").style.display="none";		//ѧ��ѧλ��Ϣ��
		document.getElementById("A14").style.display="none";		//������Ϣ��
		document.getElementById("A15").style.display="none";		//������Ϣ��
		//document.getElementById("A36").style.display="none";
		//document.getElementById("A41").style.display="none";
		//document.getElementById("A68").style.display="none";
		//document.getElementById("A69").style.display="none";
		
		document.getElementById("A11").style.display="none";		//��ѵ��Ϣ
		document.getElementById("A29").style.display="none";		//�������
		document.getElementById("A30").style.display="none";		//���˺��˳�����
		document.getElementById("A37").style.display="none";		//סַͨѶ
		document.getElementById("A53").style.display="none";		//������
		document.getElementById("A60").style.display="none";		//����¼����Ա��Ϣ
		document.getElementById("A61").style.display="none";		//ѡ������Ϣ
		document.getElementById("A62").style.display="none";		//������ѡ��Ϣ
		document.getElementById("A63").style.display="none";		//����ѡ����Ϣ
		document.getElementById("A64").style.display="none";		//������Ϣ
		document.getElementById("A71").style.display="none"; 		//��ע
		
	});	
	
	
	//�뵳ʱ�� 
	function a0140Click(){
		var name = document.getElementById("a0101").value;
		var Id = document.getElementById("a0000").value;
	 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',600,300,document.getElementById('a0000').value,ctxPath);
	}
	function a0140Click2(){
		var name = document.getElementById("a0101").value;
		var Id = document.getElementById("a0000").value;
	 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','�뵳ʱ��',600,300,document.getElementById('a0000').value,ctxPath);
	}
	
	function a3001change(rs){
		var codeType = "orgTreeJsonData";
		var codename = "code_name";
	    var winId = "winId"+Math.round(Math.random()*10000);
	    var label = "ѡ�������λ";
//	    var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
		//alert(url);
		var url = "pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
//	    odin.openWindow(winId,label,url,270,415,window,false,true);	
	    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
	}
	
	
	
	
	
</script>