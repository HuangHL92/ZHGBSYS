<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%
	String ctxPath = request.getContextPath(); 
	String orgInfoGridPageSize=(String)request.getSession().getAttribute("SOOPageSize");//����ÿҳ��ʾ������

%>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
 <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/third-party/zeroclipboard/ZeroClipboard.min.js"></script>
    <script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/ueditor.all.js"> </script>
<!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��--> 
<!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������--> 
<script type="text/javascript" charset="gbk" src="<%=ctxPath%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
.x-tab-panel-header{padding-bottom:0px;}
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

</style>
<script type="text/javascript">
var tree;
//��������ʼ��
	Ext.onReady(function() {
				var man = document.getElementById('manager').value;
				var Tree = Ext.tree;
				tree = new Tree.TreePanel(
						{
							id : 'group',
							el : 'tree-div',//Ŀ��div����
							split : false,
							monitorResize :true,
							width : 250,
							//title :'������',
							rootVisible : false,//�Ƿ���ʾ���ϼ��ڵ�
							autoScroll : true,//�����ݳ���Ԥ��ĸ߶�ʱ�Զ����ֹ�������
							collapseMode : 'mini',
							animate : true,
							border : false,
							enableDD : false,//�������Ľڵ��Ƿ�����϶���
							containerScroll : true,//�Ƿ��������ע�ᵽ����������ScrollManager��
							loader : new Tree.TreeLoader(//���ڵ�ļ�����
									{
							        	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
							        	//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataTree'
									 /* ���ڵ�չ��ʱ����ǰ�ڵ��id����Ϊ������������͵����������ڷ���������ͨ��node���������л�ȡ��
										��Ҫ�����
										dataUrl����ȡ�ӽڵ��URL��ַ��
										baseAttrs���ӽڵ�Ļ������Զ��󣬸ö����е����Խ�����ӵ��������������������ӽڵ��ϡ����ȷ��������ص�ͬ������ֵ�� */
									})
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
				tree.setRootNode(root);//���ø����
				//tree.addListener('click', BiaoZhunClick);
				tree.render();//��ʾ
				root.expand(false,true, callback);//Ĭ��չ��
				//root.expand();
			});
	/* var dbNode="";
    function BiaoZhunClick(node, e) {
    	e.stopEvent();
    	e.stopPropagation();
    	e.preventDefault();
		setTimeout(function(){ unselect(node);},100);
    }
    function unselect(node){
    	node.unselect();
    	if(dbNode!=""){
    		Ext.getCmp('group').getNodeById(dbNode).select();
    	}
    }
    function selectNode(id){
    	dbNode=id;
    	setTimeout(function(){ selectNode1(id);},100);
    }
    function selectNode1(id){
    	Ext.getCmp('group').getNodeById(id).select();
    } */
	var callback = function (node){//��չ���¼�
		if(node.hasChildNodes()) {
			node.eachChild(function(child){
				child.expand();
			})
		}
	}
	function saveb01(){
		radow.doEvent('saveBtn.onclick');
	}
	//��Ա�����޸Ĵ��ڴ���
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=parent.tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
	    },
	    closable:true
        }).show();  
		
      }
    }
	function reloadTree() {
		var tree = Ext.getCmp("group");
		   
		//��ȡѡ�еĽڵ�  
		var node = tree.getSelectionModel().getSelectedNode();  
		if(node == null) { //û��ѡ�� ������  
			tree.root.reload();
			tree.root.expand(false,true, callback);//Ĭ��չ��
		} else {        //������ ��Ĭ��ѡ���ϴ�ѡ��Ľڵ�    
		    var path = node.getPath('id');  
		    tree.getLoader().load(tree.getRootNode(),  
		                function(treeNode) {  
		                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
		                                tree.getSelectionModel().select(oLastNode);  
		                            });  
		                }, this);    
		}  
	}
	
	function cancelBtnClick(){
		radow.doEvent('querybyid', document.getElementById('b0111').value);
	}
		
<odin:menu property="exchangeMenu">
<odin:menuItem text="�¼������쵼ְ������" property="lowOrgLeaderBtn" ></odin:menuItem>
<odin:menuItem text="�¼�����������Ա�Ա�" property="lowOrgPeopleBtn" ></odin:menuItem>
<odin:menuItem text="������쵼ְ������" property="manyOrgLeaderBtn"  ></odin:menuItem>
<odin:menuItem text="�����������Ա�Ա�" property="manyOrgPeopleBtn" isLast="true" ></odin:menuItem>
</odin:menu>

</script>

<!-- �������Ĺ������� -->
<script type="text/javascript"> 
	function tongji(param){
		$h.openWin('statisticsWin','pages.sysorg.org.StatisticsWin','��������ͳ��ҳ�棨�����¼���',1500,600,param,'<%=ctxPath%>');
	}
	function lowOrgLeaderYuLan(param){
		$h.openWin('SysPreview','pages.sysorg.org.SysPreview','ְ������ͳ��',1000,550,param,'<%=ctxPath%>');
	}
	function openQueryWin(){
		var newWin_ = $h.getTopParent().Ext.getCmp('queryGroupWin');
		if(!newWin_){
			var win = $h.openWin('queryGroupWin','pages.sysorg.org.QueryGroup','������ѯ',1150,550,'','<%=ctxPath%>',null,
					{modal:false,collapsed:false,collapsible:true,titleCollapse:true,maximized:false,top:40});
			win.collapse(false);
			win.expand(true);
		}else{
			newWin_.expand(true); 
		}
	}
	function JudgeRepeatWin(){
		$h.openWin('JudgeRepeatWin','pages.sysorg.org.JudgeRepeat','��������',1150,550,'','<%=ctxPath%>');
		<%-- $h.openWin('SysPreview','pages.sysorg.org.SysPreview','��������',1000,550,'','<%=ctxPath%>'); --%>
	}
	Ext.onReady(function() {
		try{
			$('#sb0227').css('background', '#C4D5FF');
			$('#sb0183').css('background', '#C4D5FF');
			$('#sb0185').css('background', '#C4D5FF');
			$('#sb0235').css('background', '#C4D5FF');
			$('#sb0233').css('background', '#C4D5FF');
			$('#sb0236').css('background', '#C4D5FF');
			$('#sb0234').css('background', '#C4D5FF');
			$('#sb0150').css('background', '#C4D5FF');
			$('#sb0190').css('background', '#C4D5FF');
			$('#sb0191a').css('background', '#C4D5FF');
			$('#sb0232').css('background', '#C4D5FF');
			$('#hideDiv').hide();
			$('#gird').hide();
		}catch(err){
			
		}
	});
	<odin:menu property="updateM">
	<odin:menuItem text="�½��¼�����" handler="addOrgWinBtnright"></odin:menuItem>
	<odin:menuItem text="�޸�" handler="updateWinBtnright" isLast="true"></odin:menuItem>
/* 	<odin:menuItem text="����ɾ��" handler="deleteOrgBtnright" ></odin:menuItem> */
	</odin:menu>
	<odin:menu property="BzzsMenu">
	/* <odin:menuItem text="��ҵ��λ���ƺ˶���" property="Sydw"></odin:menuItem>
	<odin:menuItem text="�����������ƺ˶���" property="Xzjg"></odin:menuItem>
	<odin:menuItem text="�ι���ҵ��λ���ƺ˶���" property="Cgsy" ></odin:menuItem> */
	<odin:menuItem text="��ֱ��λְ��������ܱ�" property="SZDW" handler="SZDWHZB" ></odin:menuItem>
	<odin:menuItem text="�����أ��У��쵼����ְ��ͳ�Ʊ�" property="QXSLDBZ" handler="QXSLDBZHZB" ></odin:menuItem>
	<odin:menuItem text="�����أ��У�ƽְ̨��ͳ�Ʊ�" property="QXSPT" handler="QXSPTHZB" ></odin:menuItem>
	<odin:menuItem text="�����Уְ��������ܱ�" property="GQGX" handler="GQGXHZB" ></odin:menuItem>
	</odin:menu>
	function orgKind(){alert('������...');}
	
	
	function SZDWHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('SZDWHZB',contextPath + "/pages/sysorg/hzb/SZDWHZB.jsp?a=1",'��ֱ��λְ��������ܱ�',1200,600,null,{maximizable:true,resizable:true});
	}
	function GQGXHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('SZDWHZB',contextPath + "/pages/sysorg/hzb/SZDWHZB.jsp?gqgx=gqgx",'�����Уְ��������ܱ�',1200,600,null,{maximizable:true,resizable:true,gqgx:'gqgx'});
	}
	function QXSLDBZHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('QXSLDBZHZB',contextPath + "/pages/sysorg/hzb/QXSLDBZHZB.jsp?a=1",'�����أ��У��쵼����ְ��ͳ�Ʊ�',1200,600,null,{maximizable:true,resizable:true});
	}
	function QXSPTHZB(){
		var contextPath = '<%=request.getContextPath()%>';
		$h.showWindowWithSrc('QXSPTHZB',contextPath + "/pages/sysorg/hzb/QXSPTHZB.jsp?a=1",'�����أ��У�ƽְ̨��ͳ�Ʊ�',1200,600,null,{maximizable:true,resizable:true});
	}
	
	
	
	//��������
	function  orgPortrait(){
	
		radow.doEvent("orgPortrait");
		
	}
	
	function insertInfo(groupid){
		//alert(groupid);	
		/* var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //���嵯�����ڵĲ���  
		 if (window.screen) {  
		    var ah = screen.availHeight - 30;  
		    var aw = screen.availWidth - 90;  
		    fulls += ",height=" + ah;  
		    fulls += ",innerHeight=" + ah;  
		    fulls += ",width=" + aw;  
		    fulls += ",innerWidth=" + aw;  
		    fulls += ",resizable"  
		 } else {  
		    fulls += ",resizable"; // ���ڲ�֧��screen���Ե�������������ֹ�������󻯡� manually  
		 }
		window.open(ctxPath+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.OrgPortrait&b0111="+groupid,"����",fulls);
	 */
		var contextPath = '<%=request.getContextPath()%>';

			$h.openPageModeWin('duty','pages.sysorg.org.OrgPortrait','��������',1151,880,groupid,contextPath,null,
					{modal:true,collapsed:false,collapsible:true,titleCollapse:false,maximized:false});
		
	
	}
	
	//�Ҽ��½��¼�����
	
	
	function addOrgWinBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("addOrgWinBtnFunc",id);
	}
	//�б��Ҽ��޸�
	function updateWinBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("updateWinBtnFunc",id+","+selections[0].data.type);
	}
	//�б��Ҽ�����ɾ��
	function deleteOrgBtnright(){
		var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
		var id = selections[0].data.b0111;
		radow.doEvent("deleteOrgBtnFunc",id+","+selections[0].data.type);
	}
	function show(obj){
		document.getElementById(obj).style.display='block';
	}
	function hide(obj){
		document.getElementById(obj).style.display='block';
	}
	function funcAfterLoad(){
		document.getElementById("scanwrongfulunid").value='false';//������ʾ�������Ϸ��б��־Ϊ false
		document.getElementById("scanlegalperson").value='false';//���÷��˵�λ����Ϊ�ձ�־Ϊ false
	}
	function impTest() {
		$h.showWindowWithSrc('simpleExpWin2', '<%=ctxPath %>'
			+ "/pages/sysorg/org/DataVerify2.jsp?i=1",'���봰��',530,190);	
	}
</script>


<%
	SysOrgPageModel sysorgpagemodel=new SysOrgPageModel();
	String picType = (String) (sysorgpagemodel.areaInfo
			.get("picType"));
	String ereaname = (String) (sysorgpagemodel.areaInfo
			.get("areaname"));
	String ereaid = (String) (sysorgpagemodel.areaInfo
			.get("areaid"));
	String manager = (String) (sysorgpagemodel.areaInfo
			.get("manager"));
%>
<div id="main" style="width:100%;height:100%;">
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
<odin:hidden property="sql"/><!-- �б��ѯsql��ʱ���أ��Ѿ�����ʹ�� -->
<odin:hidden property="isList" value="0"/>
<odin:hidden property="isstauts" value="0"/>
<odin:hidden property="delete_groupid"/><!-- Ҫɾ���Ļ���id����ʱ���� -->
<odin:hidden property="scanwrongfulunid" value="false"/><!-- ��ʾ���Ϸ������б��־ true -->
<odin:hidden property="scanlegalperson" value="false"/><!-- ��ʾ���˻�������Ϊ���б��־ true -->


<%-- <odin:floatDiv property="bar_div" position="up"></odin:floatDiv> --%>
<table style="width: 100%;height: 100%;" border="0" cellspacing='0' id="table_id">
	<tr>
		<%-- <td style="margin:0px 0px 0px 0px;border: 0px solid #a9bfd3;background-color:#d0def0;">
			<div class="div_bhxj" style="margin:0px 0px 0px 0px;border: 0px solid #a9bfd3;background-color:#d0def0;">
				<odin:checkbox property="bhxj"  style="margin:0px 0px 0px 0px;" label="�����¼�"></odin:checkbox>
			</div>
		</td> --%>
		<td colspan="3">
			<div id="bar_div" style="width: 100%;height:100%;position: relative;"></div>
		</td>
	</tr>
	<tr>
		<td valign="top" style="position: relative;" >
		<odin:tab id="tab" width="250"  tabchange="grantTabChange">
				<odin:tabModel>
					<odin:tabItem title="������" id="tab1"></odin:tabItem>
					<odin:tabItem title="&nbsp&nbsp��ѯ&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>
				</odin:tabModel>
				<odin:tabCont itemIndex="tab1">
				<!-- transparent -->
					<div id="tree-div" style="overflow: auto;height: 100%;border: 2px solid transparent;margin:0px 0px 0px 0px;">
					</div>
					<div style="margin:0px 0px 0px 0px;border: 1px solid #c3daf9;background-color:#cedff5;height: 100%;" valign="middle">
						<table id="jg_tj" width="250" style="background-color:#cedff5;border: 1px solid #c3daf9;height:100%" border="0" valign="middle">
							 <tr>
						        <td colspan="4">
						        	<table border="0">
						        		<tr>
						        			<td width="190px">
							        			<odin:checkbox property="bhxa_check" label="�����¼�"></odin:checkbox>
						        			</td>
						        			<td>
						        				<table>
						        					<tr>
						        						<td>
						        							<odin:button text="ˢ�� " property="flashOrgInfo" handler="reloadTree"></odin:button>
						        						</td>
						        					</tr>
						        				</table>
						        			</td>
						        		</tr>
						        	</table>
						        </td>
						   </tr>
						<tr>
						        <td><label style="font-size: 12">������λ</label></td>
						        <td><input type="text" value="���������ƻ����" onfocus="javascript:if(this.value=='���������ƻ����')this.value='';" id="jgdw" size="17" title="���������ƻ����"
							            class=" x-form-text x-form-field"></td>
							    <td style ="width:10px"></td>
						        <td ><odin:button property="selectOrgsBtn" 
								        text="��һ��" handler="doQueryNext"/></td>
						</tr>
						</table>
					</div>
					
				</odin:tabCont>
				<odin:tabCont itemIndex="tab2">
					<div id="div_tab2" style="margin:0px 0px 0px 0px;border: 2px solid #c3daf9;height:300;margin-top: 0px;">
						<table border="0" style="width:100%;">
							<col width="35%">
							<col width="30%">
							<col width="35%">
							<tr>
								<td>
								</td>
								<td>
									<table style="width:100%;" border="0">
										<col width="10%">
										<col width="90%">
										<tr>
											<td colspan="2"  heigth="3">&nbsp;</td>
										</tr>
										<tr>
											<odin:select2 property="b0194" label="��λ����" data="['1','���˵�λ'],['2','�������'],['3','��������']"/>
											<odin:hidden property="b0194_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"> </td>
										</tr>
										<tr>
											<odin:textEdit property="b0114" label="��������" />
											<odin:hidden property="b0114_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<odin:textEdit property="b0101" label="��������"/>
											<odin:hidden property="b0101_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<odin:textEdit property="b0104" label="��&nbsp;&nbsp;&nbsp;&nbsp;��"/>
											<odin:hidden property="b0104_h"/>
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0117" label="��������" codetype="ZB01" codename="code_name3" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0124" label="������ϵ" codetype="ZB87" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0131" label="�������" codetype="ZB04" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3"></td>
										</tr>
										<tr>
											<tags:PublicTextIconEdit property="b0127" label="��������" codetype="ZB03" maxlength="8" />
										</tr>
										<tr>
											<td colspan="2"  heigth="3">&nbsp;</td>
										</tr>
									</table>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
								<table style="width:100%;" border="0">
									<tr >
									<td align="center">
										<odin:button text="���" handler="clearValue" />
									</td>
									<td align="center" >
										<odin:button text="��ѯ"  handler="queryDO" />
									</td>
									<td></td>
									</tr>
								</table>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
				</odin:tabCont>
		</odin:tab>
		</td>
		<td style="position: relative;">
			<div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:url(image/right.png) #D6E3F2 no-repeat center center;"></div>
		</td>
		<td valign="top" class='bigDiv' id="bigDiv" width="100%" >
		<div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;" >
		<odin:editgrid property="orgInfoGrid" cellDbClick="DbClick_grid_func"
					autoFill="false" width="0"  bbarId="pageToolBar" remoteSort="true" 
					hasRightMenu="true" rightMenuId="updateM" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="b0111" />
						<odin:gridDataCol name="b0101" />
						<odin:gridDataCol name="b0101parent" />
						<odin:gridDataCol name="b0114" />
						<odin:gridDataCol name="b0194" /> 
						<odin:gridDataCol name="signcode" />
						<odin:gridDataCol name="unifycode" />
						
						<odin:gridDataCol name="b0117" />
						<odin:gridDataCol name="b0124" />
						<odin:gridDataCol name="b0131" />
						<odin:gridDataCol name="b0127" />
						<odin:gridDataCol name="grouptype" />
						<odin:gridDataCol name="operate" />
						<odin:gridDataCol name="type" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<%-- <odin:gridRowNumColumn></odin:gridRowNumColumn> --%>
						<odin:gridEditColumn2 header="selectall" width="20"  editor="checkbox" dataIndex="personcheck" edited="true" hideable="false" checkBoxClick="personcheck_func" checkBoxSelectAllClick="getCheckAll"/>
						<odin:gridEditColumn dataIndex="b0111" width="100" header="��������" edited="false" editor="text" hidden="true" />
						<odin:gridEditColumn2 dataIndex="b0101parent" width="100" header="�ϼ���������" editor="text" edited="false" hidden="true"/>
						<odin:gridEditColumn2 dataIndex="b0101" width="100" header="��������" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0114" width="50" header="��������" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0194" width="35" header="��������" editor="text" edited="false" />
						
						<odin:gridEditColumn2 dataIndex="b0117" width="35" header="��������" editor="select" codeType="ZB01" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0124" width="35" header="������ϵ" editor="select" codeType="ZB87" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0131" width="35" header="�������" editor="select" codeType="ZB04" edited="false" />
						<odin:gridEditColumn2 dataIndex="b0127" width="35" header="��������" editor="select" codeType="ZB03" edited="false" />
						<odin:gridEditColumn2 dataIndex="grouptype" width="35" header="��������" editor="select" hidden="true" codeType="SSKSBM" edited="false" />
						<odin:gridEditColumn2 dataIndex="operate" width="100" header="����" editor="select" codeType="ddd" renderer="dodetail" hidden="true" edited="false" />
				          
						<odin:gridEditColumn2 dataIndex="type" width="40" header="Ȩ������" editor="text" hidden="true" edited="false" isLast="true"/>
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
</div>
<odin:hidden property="openSelectOrgWinBtn"/>
<odin:hidden property="outFile"/>
<odin:hidden property="tab_flag"/>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<%-- <odin:textForToolBar text="<h3>������Ϣ</h3>" /> --%>
	<%-- <odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="<h3>��ѯģʽ</h3>" id="openSelectOrgWinBtn" icon=""  /> --%>
	<odin:fill />
	<%-- <odin:buttonForToolBar id="cytjfx" text="����ͳ�Ʒ���" icon="image/u179.png" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>  --%>
	<%-- <odin:buttonForToolBar id="hgfx" text="��۷���" icon="image/u45.png" tooltip="��ѡ�л����������¼����к�۷���"/>
	<odin:separator></odin:separator> --%>
	
	<%-- <odin:buttonForToolBar text="&nbsp;չʾ�л�" id="showList" handler="showListChnge"  icon="image/icon040a10.gif"></odin:buttonForToolBar> --%>

	<%-- <odin:buttonForToolBar text="&nbsp;��������"  id="orgPortrait" handler="orgPortrait"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;��λ��ά��" id="orgKind" handler="orgKind"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;ͬ��OA�û�" id="synchroOAUser"  handler="synchroOAUser"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="&nbsp;ͬ��OA����" id="synchroOA"  handler="synchroOA"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;��֯ʷ" id="zzsBtn"  icon="image/icon040a10.gif" handler="openZZSWin"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="bzzsid" text="����ְ��"  menu="BzzsMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="&nbsp;�½��¼�����" id="addOrgWinBtn"  icon="image/icon040a2.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon040a14.gif" id="updateWinBtn" tooltip="�޸�"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;ɾ��" id="deleteOrgBtn" handler="deleteOrg"  icon="image/icon040a3.gif"></odin:buttonForToolBar>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;����վ" id="recoverBtn"  icon="images/qinkong.gif"></odin:buttonForToolBar> --%>
	<odin:separator />
	<%-- <odin:buttonForToolBar text="����У��" icon="image/u53.png" id="dataVerify" tooltip="��ѡ�л�������У��"  />
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;������ѯ" id="queryGroupBtn1" handler="openQueryWin" icon="image/icon040a15.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;��������" id="JudgeRepeatBtn1" handler="JudgeRepeatWin" icon="image/icon040a15.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;��ȿ���" id="YearCheckBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;��������" id="sortSysOrgBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������ϵ���" id="transferSysOrg"
		icon="images/icon/reset.gif" tooltip="������ת�Ƶ���������" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ա����ת��" icon="images/icon/exp.png"
		id="batchTransferPersonnel" />
	<odin:separator></odin:separator> 
	<odin:buttonForToolBar text="&nbsp;ְλ��������" id="gwConfBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="&nbsp;ְλ����" id="gwConfBtn2" handler="impTest" icon="image/icon040a10.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="&nbsp;��ȫ��������" id="aotumaticCode" handler="aotumaticCode" icon="image/icon040a14.gif"></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="&nbsp;������Ϣ����" id="impOrgInfo" icon="image/icon040a14.gif"></odin:buttonForToolBar>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="&nbsp;���Ź�������" id="refSetBtn"  icon="image/icon040a10.gif"></odin:buttonForToolBar> --%>
	<odin:separator isLast="true"></odin:separator>
	<%-- <odin:separator></odin:separator> --%>
  
	<%-- <odin:buttonForToolBar text="����ά��" id="orgBtn"   icon="images/icon/folderClosed.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
	<odin:separator></odin:separator> --%>
<%--  	<odin:buttonForToolBar text="��ʷ���� " id="outFile"
		icon="images/icon/reset.gif" tooltip="���ɾ���Ļ���" /> 
		
	<odin:separator></odin:separator>--%>
	
<%-- <odin:buttonForToolBar text="ְ������ͳ��" id="exchangeBtn" id="exchangeId" isLast="true" menu ="exchangeMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> --%>
	<%-- <odin:buttonForToolBar text="ְ������ͳ��" id="exchangeBtn"  isLast="true" menu ="exchangeMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> --%>
	<%-- <odin:separator></odin:separator> --%>
<%-- 	<odin:buttonForToolBar text="����" icon="images/save.gif"
		id="saveBtn" tooltip="����"/> 
	<odin:separator></odin:separator>--%>
	<%-- <odin:buttonForToolBar text="��Ƭ���" icon="image/u45.png"
		id="imgVerify" tooltip="��Ȩ���ڻ����е�������Ա��������Ƭ���"/>  --%>
	<%-- <odin:separator></odin:separator> --%>
	<%-- <odin:buttonForToolBar text="ȡ��" icon="images/sx.gif"
		id="cancelBtn" tooltip="ȡ��"  isLast="true" handler="cancelBtnClick"/>	 --%>	
</odin:toolBar>
<odin:hidden property="cancelBtn"/>
<odin:window src="/blank.htm" id="tjfxWin" width="400" height="530"
	title="����ͳ�Ʒ���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin" width="1250" height="550"
	title="�½��¼�����ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="YearCheck" width="760" height="460"
	title="��ȿ���ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="updateNameWin" width="400" height="290"
	title="��Ա������λ��ְ����Ϣ������ʾ" modal="true"></odin:window>
<odin:window src="/blank.htm" id="updateOrgWin" width="1200" height="550"
	title="�޸Ļ���ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="scanOrgWin" width="900" height="500"
	title="�鿴����ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="transferSysOrgWin" width="420"
	height="400" title="������ϵ���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="outFilegWin" width="520"
	height="400" title="������ϵ���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="batchTransferPersonnelWin" width="520"
	height="400" title="��Ա����ת��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgSortWin" width="510" height="600"
	title="��������" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgimpWin" width="300" height="600"
	title="������Ϣ���� " modal="true"></odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500"
	title="��ϢУ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="imgVerifyWin" width="960" height="500"
	title="��Ƭ���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="orgStatisticsWin" width="395" height="490"
	title="ѡ�������Χ" modal="true"></odin:window>

<odin:window src="/blank.htm" id="queryGroupWin" width="800" height="480"
	title="��ѯ����" modal="true"></odin:window>
<odin:window src="/blank.htm" id="JudgeRepeatWin" width="800" height="480"
	title="��ѯ����" modal="true"></odin:window>
<odin:window src="/blank.htm" id="showOperateWin" width="800" height="480"
	title="������Ϣ�޸�" modal="true" ></odin:window>
<odin:window src="/blank.htm" id="orgConnectWin" width="800" height="480"
	title="����������" modal="true" ></odin:window>
<script type="text/javascript">
function expExcelFromGrid(){
	
	var excelName = null;
	
	//excel�������Ƶ�ƴ�� 
	var pgrid = Ext.getCmp('orgInfoGrid');
	var dstore = pgrid.getStore();
	excelName = "������Ϣ" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	
	odin.grid.menu.expExcelFromGrid('orgInfoGrid', excelName, null,null, false);
	
} 
//��ȡ���
var mainWidth = document.body.clientWidth;//����������



//������ѯ����
function queryDO(groupID,path){
  	tree.getLoader().load(
 		tree.getRootNode(),
 		function(){
 			tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
                tree.getSelectionModel().select(oLastNode);  
            });
 		},
 		this
 	);
  	funcAfterLoad();//���ò�ѯ�б��־,���Ϸ����룬���˵�λΪ�ձ���  ��־Ϊfalse(tab tab1 ���Ϸ����룬���˵�λΪ�ձ��� 4����ʾ�б�)
	radow.doEvent('querybyid',groupID);
}
function dodetail(){
	return "<a href=\"javascript:showOperate()\">���</a>&nbsp&nbsp<a href=\"javascript:orgConnect()\">�������</a>;"
}

function showOperate(){
	var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
	var id = selections[0].data.b0111; 
	var sign_code = selections[0].data.signcode;
	if(!feildIsNull(sign_code )){
		$h.openWin('showOperateWin','pages.sysorg.org.ShowOperate1','������Ϣά��',1280,500,id,'<%=ctxPath%>');
	}else{
		alert("û�л�ȡ����λ����");
	}
	
}

function orgConnect(){
	var selections = odin.ext.getCmp('orgInfoGrid').getSelectionModel().getSelections();
	var id = selections[0].data.b0111; 
	console.log("id "+id);
	$h.openWin('orgConnectWin','pages.sysorg.org.OrgConnect','����������',1300,550,id,'<%=ctxPath%>');
}
//�����޸�ҳ����ò�ѯ�����¼�
function queryGroupByUpdate(groupID){
	var flag=document.getElementById("scanwrongfulunid").value;
	var flag1=document.getElementById("scanlegalperson").value;
	if(flag=='true'){//��ѯ���Ϸ�������Ϣ
		radow.doEvent('scanWrongfulUnid');
	}else if(flag1=='true'){
		radow.doEvent('scanLegalPerson');
	}else{//
		reloadTree();
		//radow.doEvent('querybyid',groupID);
		radow.doEvent('orgInfoGrid.dogridquery');
	}
}
/* 
$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	$("#b0104SpanId").prepend("<font color='red'>&nbsp;&nbsp;&nbsp;*</font>");
}); */
function myfunction(opType,radioType){
	var opType = opType;
	var radioType = radioType;
	if(opType==1){
		document.getElementById("b0194a").checked = 'checked';
		document.getElementById("b0194a").disabled=false; 
		document.getElementById("b0194b").disabled=false; 
		document.getElementById("b0194c").disabled=false; 
		Check1();
//��ʾ
	}else{
		if(radioType==1){
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}else if(radioType==2){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").checked = 'checked';
			document.getElementById("b0194c").disabled=false; 
			Check2();
		}else if(radioType==3){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").checked = 'checked';
			Check3();
		}else{
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}
	}
}
function Check1(){
	var treeWidth = document.getElementById("tree-div").clientWidth;//���������
	var bigWidth = mainWidth-treeWidth-10;//�ҷ�div���
	//���ÿ��
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	
	$(".tr1").show();
	$(".tr2").show();
	$(".tr4").show();
	$(".tr5").show();
	$('#div11').css('height','260px'); 
	$('#div22').css('height','260px'); 
	$('#div33').css('height','260px');
	$('#temp_div').show();
	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
	document.getElementById("b01a").value='���˵�λ';
	
	$("#tr33").width(bigWidth/100*41);
	$("#tr4").width(bigWidth/100*33);
	$("#tr5").width(bigWidth/100*26);
}
function Check2(){
	var treeWidth = document.getElementById("tree-div").clientWidth;//���������
	var bigWidth = mainWidth-treeWidth-10;//�ҷ�div���
	
	//���ÿ��
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	$("#tr4").width(0);
	$("#tr33").width(bigWidth/100*56);
	$("#tr5").width(bigWidth/100*44);
	
	$(".tr1").hide();
	$(".tr2").show();
	$(".tr4").hide();
	$(".tr5").show();
	$('#div11').css('height','150px'); 
	$('#div22').css('height','150px'); 
	$('#div33').css('height','150px'); 
	$('#temp_div').show();
	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
		document.getElementById("b01a").value='�������';
}
function Check3(){ 
	var treeWidth = document.getElementById("tree-div").clientWidth;//���������
	var bigWidth = mainWidth-treeWidth-10;//�ҷ�div���
	//���ÿ��
	$("#lab").width(60);
	$("#b0180").width(bigWidth-80);
	$("#tr33").width(bigWidth);
	$("#tr4").width(0);
	$("#tr5").width(0);
	
	$(".tr1").hide();
	$(".tr2").hide();
	$(".tr4").hide();
	$(".tr5").hide();
	$('#div11').css('height','120px'); 
	$('#div22').css('height','120px'); 
	$('#div33').css('height','120px'); 
	$('#temp_div').hide();
	$(".tr3").hide();
	document.getElementById("b01a").value='��������';
}
//��ʾ�������Ͱ�ť
function alertTypeFun(){
	$('.type11').show();
	$('.type22').show();
	$('.type33').show();
}
//ɾ������ʱ�鰸��������Ա
function queryPersonByGroupId(groupIDs,unitname){
	Ext.MessageBox.minWidth = 300;//���� MessageBox ������С���
	Ext.MessageBox.confirm('��ʾ',''+unitname+'������ְ��Ա���޷�ɾ�����Ƿ�鿴��',function(btn){  
        if(btn=='yes'){
            var aid=groupIDs+"zz";
        	var tab=parent.tabs.getItem(aid);
           if (tab){ 
       			parent.tabs.activate(aid);
            }else{
	        	var src = '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery';
	        	//groupIDs = groupIDs+'__'+Ext.getCmp('group').getNodeById(groupIDs).getPath('id');
	            parent.parent.tabs.add({
	                id: aid,
	                title: '��Ա��Ϣ',
	                html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'jgcx_delete@@'+groupIDs+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	          	    listeners:{//�ж�ҳ���Ƿ���ģ�
	          	    	
	          	    },
	          	    closable:true
	                }).show();
            }
        } 
    }); 
}

//ɾ������ʱ�鰸��������Ա
function queryPersonByGroupId1(groupIDs,unitname){
	Ext.MessageBox.minWidth = 300;//���� MessageBox ������С���
	Ext.MessageBox.confirm('��ʾ',''+unitname+'���з���ְ��Ա���޷�ɾ�����Ƿ�鿴��',function(btn){  
        if(btn=='yes'){
            var aid=groupIDs+"ltorls";
        	var tab=parent.tabs.getItem(aid);
           if (tab){ 
       			parent.tabs.activate(aid);
            }else{
	        	var src = '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery';
	        	//groupIDs = groupIDs+'__'+Ext.getCmp('group').getNodeById(groupIDs).getPath('id');
	            parent.parent.tabs.add({
	                id: aid,
	                title: '��Ա��Ϣ',
	                html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'jgcxOrlt_delete@@'+groupIDs+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	          	    listeners:{//�ж�ҳ���Ƿ���ģ�
	          	    	
	          	    },
	          	    closable:true
	                }).show();
            }
        } 
    }); 
}

	function download(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
		setTimeout(cc,3000);
	}
	function cc(){
	w.close();
	}
	function imgVerifyWin(){
	addTab('��Ƭ���','','<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false);
}

</script>
<script type="text/javascript">
/* $(function() {
    //����Ҫ��ק�ı��С��Ԫ�ض��� 
    bindResize();
}); */
/* function bindResize() {
	var el=document.getElementById('divresize');
	
	var girdDiv=document.getElementById('bigDiv');
    //���� X �� Y ������ 
    x = y = x2 = y2 = 0;
    //��갴�º��¼�
    $(el).mousedown(function(e) {
		if(e.which==1){//==1����������
        	
        }else{
        	return;
        }
        //����Ԫ�غ󣬼��㵱ǰ����������������� 
    	var treeDiv="";
    	if(tab_flag=='tab1'){
    		treeDiv=document.getElementById('tree-div');
    	}else{
    		treeDiv=document.getElementById('div_tab2').firstChild;//table ����
    	}
        if(tab_flag=='tab1'){
        	x = e.clientX - treeDiv.offsetWidth;
        	//y = e.clientY - treeDiv.offsetHeight;
        }else{
        	x = e.clientX -treeDiv.offsetWidth;
        	//y = e.clientY - treeDiv.style.height;
        }
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
        document.getElementById("div_tab2").firstChild.style.width=e.clientX-x;
       	//���泬���޵�������... 
        var tree = Ext.getCmp('group')
        //tree�Ŀ������
        tree.setWidth(e.clientX - x);
        //tab�Ŀ������
        var resizeobj =Ext.getCmp('tab');
		resizeobj.setWidth(e.clientX - x);
    	//els.height = e.clientY - y + 'px';
    	//�ұߵ�td������ã��б�����td�� ����Ҫ
    	//document.getElementById('bigDiv').style.height=document.body.clientHeight-objTop(document.getElementById('bigDiv'))[0]-4;
    	
    	//
    	var grid=Ext.getCmp('orgInfoGrid');
    	grid.setWidth(0);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
    	grid.setWidth(document.body.clientWidth-resizeobj.getWidth());
    	var width=document.getElementById("girdDiv").firstChild.firstChild.offsetWidth;//��ȡ��ǰdiv���
    	grid.setWidth(width);//����grid�Ŀ��
    	
    	var grid_width=grid.getWidth();
    	//50 150 300 400 200
    	//��̬�����п�
    	grid.colModel.setColumnWidth(0,grid_column_0*width,'');//��0��
    	//grid.colModel.setColumnWidth(1,grid_column_1*width,'');//��1��
    	grid.colModel.setColumnWidth(2,grid_column_2*width,'');//��2��
    	grid.colModel.setColumnWidth(3,grid_column_3*width,'');//��3��
    	grid.colModel.setColumnWidth(4,grid_column_4*width,'');//��4��
    	grid.colModel.setColumnWidth(5,grid_column_5*width,'');//��5��
    	grid.colModel.setColumnWidth(6,grid_column_6*width,'');//��6��
    	grid.colModel.setColumnWidth(7,grid_column_7*width,'');//��7��
    	grid.colModel.setColumnWidth(8,grid_column_8*width,'');//��8��
    	//grid.colModel.setColumnWidth(9,grid_column_9*width,'');//��9��
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
} */ 
	
	//������ҳ���λ��
	/* function objTop(obj){
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
	} */
	var grid_column_0=0.04;
	//var grid_column_1=0.3;
	var grid_column_2=0.20;
	var grid_column_3=0.11;
	var grid_column_4=0.11;
	var grid_column_5=0.11;
	var grid_column_6=0.11;
	var grid_column_7=0.10;
	var grid_column_8=0.10;
	var grid_column_9=0.10;
	//var grid_column_9="";
	function jainceHeight(){
		setTimeout(jainceHeight1,500); 
	}
	
	function jainceHeight1(){
		var heightClient=window.parent.document.body.clientHeight;
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;
		var tabHeight=28;
		var tr1Height=30;
		var tr2ButtomHeight=30;
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
		
	
		var gird_dom=Ext.getCmp('orgInfoGrid');//��ȡgrid����
		var resizeobj =Ext.getCmp('tab');
		
		gird_dom.setHeight(t2Height);
		resizeobj.setHeight(t2Height);
		var jg_tj=document.getElementById("jg_tj").offsetHeight;
		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
		document.getElementById("div_tab2").style.height=t2Height-30;
	}

	function initHeightWidth(){
		var heightClient=window.parent.document.body.clientHeight;//���ڸ߶�
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;//ϵͳ���Ϸ��˵��߶�
		var tabHeight=28;//tabҳ�����������߶�
		var tr1Height=30;//table�ĵ�һ�и߶ȣ���
		var tr2ButtomHeight=30;//grid�ײ��������߶�
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
		//63 
		//28
		//30
		//30
		//ҳ�����
		document.getElementById("main").style.width = window.parent.document.body.clientWidth + "px";
		document.getElementById("main").parentNode.style.height='100%';
		document.getElementById("main").parentNode.style.width='100%';
		
		document.getElementById("main").parentNode.parentNode.style.width='100%';
		document.getElementById("main").parentNode.parentNode.style.height='100%';
		
		document.getElementById("main").parentNode.parentNode.style.overflow='hidden';
		
		var gird_dom=Ext.getCmp('orgInfoGrid');//��ȡgrid����
		gird_dom.setWidth(window.parent.document.body.clientWidth-250-7);
		gird_dom.setHeight(t2Height);
		
		//��ʼ��tab�߶�
		var resizeobj =Ext.getCmp('tab');
		resizeobj.setHeight(t2Height);
		
		var grid_width=gird_dom.getWidth();
		//��ʼ�������п�
    	gird_dom.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
    	//gird_dom.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
    	gird_dom.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//��2��
    	gird_dom.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//��3��
    	gird_dom.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//��4��
    	gird_dom.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//��5��
    	gird_dom.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//��6��
    	gird_dom.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//��7��
    	gird_dom.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//��8��
    	//gird_dom.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
		gird_dom.colModel.setColumnWidth(10,grid_column_9*grid_width,'');//��8��
    	
		//���û������Ŀ����tab�Ŀ�ȱ���һ��
		try{
			var resizeobj =Ext.getCmp('tab');
			resizeobj.setHeight(t2Height)
			var jg_tj=document.getElementById("jg_tj").offsetHeight;
    		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
    		document.getElementById("tree-div").style.width=resizeobj.getWidth();
    		//��ѯtab����һ��
    		document.getElementById("div_tab2").style.width=resizeobj.getWidth();
    		document.getElementById("div_tab2").style.height=t2Height-30;
    		
    		//��ʼ��tab��ʾ��־
    		document.getElementById("tab_flag").value='tab1';
    		window.onresize=jainceHeight;
    		checkboxvl("bhxa_check");
		}catch(err){
			
		}
	}
	function checkboxvl(id){
		try{
			var node_66;
			var node_55;
			var node_5_height;
			var node_6_height;
			node_66=$("#"+id).parent();
			node_6_height=node_66[0].offsetHeight;
			node_55=node_66.children();
			node_5_height=node_55[0].offsetHeight;
			var mt=(node_6_height-node_5_height)/2;
			$("#"+id).parent().parent().parent().css({"top": mt+"px"});
		}catch(err){
		}
	}
	Ext.onReady(function() {
		initHeightWidth();
	}); 
	Ext.onReady(function(){
		var pgrid = Ext.getCmp('orgInfoGrid');
		var dstore = pgrid.getStore();
		dstore.on({  
	       load:{  
	           fn:function(){  
	        	   pGridLoad(dstore.getTotalCount());
	        	   if($('#selectall_orgInfoGrid_personcheck').attr('class')=='x-grid3-check-col-on'){
	        		 //���¼��غ�ѡ�ѡ��ȥ������
	            	   document.getElementById('selectall_orgInfoGrid_personcheck').click();
	        	   }
	           }
	       },  
	       scope:this      
	   });  

	});
	function pGridLoad(tatalcount){
		<%-- if(tatalcount><%=CommSQL.MAXROW%>){
			document.getElementById('radioC1').checked=true;
			document.getElementById('radioC2').disabled=true;
			document.getElementById('radioC3').disabled=true;
			document.getElementById('radioC4').disabled=true;
			
		}else{
			document.getElementById('radioC2').disabled=false;
			document.getElementById('radioC3').disabled=false;
			document.getElementById('radioC4').disabled=false;
		} --%>
	}
	Ext.onReady(function() {
		document.getElementById('jgdw').style.width=115+'px';
	});
	var tab_flag="tab1";//tabҳ��ʾ��־
	function grantTabChange(tabObj,item){
		if(item.getId()=='tab1'){
			tab_flag="tab1";
			//���ر�־��ҳ��
			document.getElementById("tab_flag").value='tab1';
		}else{
			tab_flag="tab2";
			document.getElementById("tab_flag").value='tab2';
		}
		//����ҳ��߶���ʽ
		jainceHeight2();
	}
	function jainceHeight2(){
		var heightClient=window.parent.document.body.clientHeight;
		var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;
		var tabHeight=28;
		var tr1Height=30;
		var tr2ButtomHeight=30;
		var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;
	
		var gird_dom=Ext.getCmp('orgInfoGrid');//��ȡgrid����
		var jg_tj=document.getElementById("jg_tj").offsetHeight;
		document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
		document.getElementById("div_tab2").style.height=t2Height-30;
	}
	//��ѯ//��������    ��������    ��λ����    ��� ����̨����ֱ��ȡֵ����������ֵ��ԭ��δ֪��     
	function queryDO(){
		//$('#b0101_h_combo').val($('#b0101_combo').val());
		$('#b0101_h').val($('#b0101').val());
		
		//$('#b0114_h_combo').val($('#b0114_combo').val());
		$('#b0114_h').val($('#b0114').val());
		
		//$('#b0194_h_combo').val($('#b0194_combo').val());
		$('#b0194_h').val($('#b0194').val());
		
		//$('#b0104_h_combo').val($('#b0104_combo').val());
		$('#b0104_h').val($('#b0104').val());
		radow.doEvent("orgInfoGrid.dogridquery");
	}
	
	//�������� ������ϵ ������� �������� ���� backspace ��ֱ����գ������ֵ����Ч�ģ�ѡ����Ч
	window.document.onkeydown=func_press;
	function func_press(ev){
		ev=(ev)?ev:window.event;
		if(ev.keyCode==8||ev.keyCode=='8'){//backspace
			if('b0117_combo' == document.activeElement.id){//��������
				$('#b0117_combo').val("");
				$('#b0117').val("");
			}
			if('b0124_combo' == document.activeElement.id){//������ϵ
				$('#b0124_combo').val("");
				$('#b0124').val("");
			}
			if('b0131_combo' == document.activeElement.id){//�������
				$('#b0131_combo').val("");
				$('#b0131').val("");
			}
			if('b0127_combo' == document.activeElement.id){//��������
				$('#b0127_combo').val("");
				$('#b0127').val("");
			}
		}else if(ev.keyCode==13||ev.keyCode=='13'){//enter
			if('jgdw' == document.activeElement.id||document.activeElement.id==''){
				doQueryNext();//���崦����
			}
		}else if(event.keyCode == 27){	//����ESC
	        return false;   
		}
	}
	var querynum = 0;//��ѯ����
	var lastquery = ""; //��һ�β�ѯֵ
	var cxjg_length=0;
	function doQueryNext() {
		var nextProperty = document.getElementById('jgdw').value;//��ȡ�������ֵ
		if (nextProperty == "") {
			return;
		}
		var tree = Ext.getCmp("group");	//��ȡ��
		 if(nextProperty != lastquery){	//�����ѯ���,�������
	   	  querynum = 0; 
	     }
		if(querynum>parseInt(cxjg_length)){//��ѯ�������ڲ�ѯ��������ȣ�����Ϊ�㣬����ѭ��
			querynum=0;
		}
		 if (nextProperty !== ""&&(querynum<=cxjg_length)) {//�в�ѯ�������Ҳ�ѯ����С�ڵ��ڲ�ѯ���������
		  Ext.Ajax.request({
		     url : '<%=request.getContextPath()%>/JGQueryServlet?method=JGQuery_jgxx',
		     params : {
		    	'queryName' : nextProperty,
		    	'queryNum'	: querynum
		     },
		     async:true,
		     method : "post",
		     success : function(a, b) {
		      var r = Ext.util.JSON.decode(a.responseText);
		      var data = eval(r);
		      if(data[0] == "1"){//�в�ѯ���
		    	  cxjg_length=data[1];//��ѯ���������
		    	  tree.expandPath(data[2], 'id', this.onExpandPathComplete);
		    	  lastquery = nextProperty;
		    	  querynum++;
		      }else if(data[0] == '0'){// û�в�ѯ���
		    	  cxjg_length=data[1];//��ѯ���������
		    	  Ext.Msg.alert("��Ϣ", "û�������ѯ�����Ļ���");
		      }else if(data[0] == '3'){//  �����ٶ�̫�죬�����쳣
		    	  cxjg_length=data[1];//��ѯ���������
		    	  querynum = 0;			//��ѯ����,��ʾ��������
		    	  cxjg_length=data[1];//��ѯ���������
		    	  doQueryNext();
		      } else if(data[0] == '2'){ //���һ����ѯ���
		    	  cxjg_length=data[1];//��ѯ���������
		    	 // Ext.Msg.alert("��Ϣ", "�Ѿ���ѯ�����һ������,�ٴβ�ѯ���ӵ�һ����ʼ");
		    	  querynum = 0;			//��ѯ����,��ʾ��������
		    	  doQueryNext();
		      }
		     }
		    });
		 } 
	}
	onExpandPathComplete = function(bSuccess, oLastNode) {
		 if (!bSuccess) {
		  return;
		 } 
		 // focus �ڵ㣬��ѡ�нڵ㣡�����´��벻����
		 oLastNode.ensureVisible();
		 oLastNode.select();
		 oLastNode.fireEvent('click', oLastNode);
		};
    //���tabҳ�� ����ѯ����
	function clearValue(){
		$('#b0194').val("");
		$('#b0114').val("");
		$('#b0101').val("");
		$('#b0104').val("");
		$('#b0117').val("");
		$('#b0124').val("");
		$('#b0131').val("");
		$('#b0127').val("");
		$('#b0194_combo').val("");
		$('#b0114_combo').val("");
		$('#b0101_combo').val("");
		$('#b0104_combo').val("");
		$('#b0117_combo').val("");
		$('#b0124_combo').val("");
		$('#b0131_combo').val("");
		$('#b0127_combo').val("");
	}
    //�б�˫���¼�
    function DbClick_grid_func(grid,rowIndex,colIndex,event){
    	
    	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
    	//��������
    	radow.doEvent("DbClick_grid",record.get("type")+","+record.get("b0111"));
    }
    //�б�ѡ��ѡ
    //a-row b-col c-col_id d-grid_id
    function personcheck_func(row,col,col_id,grid_id){
    	/* var grid=Ext.getCmp(grid_id);
    	record=grid.store.getAt(row);
    	if(record.get("personcheck")==true){
    		document.getElementById("checkedgroupid").value=record.get("b0111");
    	}else{
    		document.getElementById("checkedgroupid").value="";
    	} */
    }
    //ȫѡ
   function getCheckAll(grid_id,col_id){
	  /* var grid=Ext.getCmp(grid_id);
	   var store=grid.store;
	   if(store.totalLength>0){//�б�������
		   if(store.getAt(0).get("personcheck")==true){//��һ�б�ѡ�У�����ȫ��ѡ��
			   document.getElementById("checkedgroupid").value=store.getAt(0).get("b0111");
		   }else{
			   document.getElementById("checkedgroupid").value=record.get("b0111");
		   }
	   } */ 
   }
    //���� չ��
    var flag_ss=false;
   function abcde(obj){
	   if(flag_ss==false){
		 	//����
	   		document.getElementById("div_tab2").firstChild.style.width=1;//��ѯ�б�������
	        var tree = Ext.getCmp('group');
	        //tree�Ŀ������
	        tree.setWidth(1);
	        //tab�Ŀ������
	        var resizeobj =Ext.getCmp('tab');
			resizeobj.setWidth(1);
			
			flag_ss=true;//���ر�־
			// document.getElementById(obj.id).innerHTML='>';
			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('orgInfoGrid');
	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
	    	grid.setWidth(width);//����grid�Ŀ��
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//��̬�����п�
	    	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	    	grid.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//��2��
	    	grid.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//��3��
	    	grid.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//��4��
	    	grid.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//��5��
	    	grid.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//��6��
	    	grid.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//��7��
	    	grid.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//��8��
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
	   }else{ //��չ��
			 //����
			 document.getElementById("div_tab2").firstChild.style.width=250;//��ѯ�б�������
		     var tree = Ext.getCmp('group')
		     //tree�Ŀ������
		     tree.setWidth(250);
		     //tab�Ŀ������
		     var resizeobj =Ext.getCmp('tab');
			 resizeobj.setWidth(250);
			 flag_ss=false;//��չ����־
			 //document.getElementById(obj.id).innerHTML='<span><</span>';
			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
			 var grid=Ext.getCmp('orgInfoGrid');
	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
	    	grid.setWidth(width);//����grid�Ŀ��
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//��̬�����п�
	    	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	    	grid.colModel.setColumnWidth(3,grid_column_2*grid_width,'');//��2��
	    	grid.colModel.setColumnWidth(4,grid_column_3*grid_width,'');//��3��
	    	grid.colModel.setColumnWidth(5,grid_column_4*grid_width,'');//��4��
	    	grid.colModel.setColumnWidth(6,grid_column_5*grid_width,'');//��5��
	    	grid.colModel.setColumnWidth(7,grid_column_6*grid_width,'');//��6��
	    	grid.colModel.setColumnWidth(8,grid_column_7*grid_width,'');//��7��
	    	grid.colModel.setColumnWidth(9,grid_column_8*grid_width,'');//��8��
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
	   }
   }
   Ext.onReady(function(){
	   var pagesize = "20";
		var orgInfoGridPageSize='<%= orgInfoGridPageSize%>';
		if(orgInfoGridPageSize!=null&&orgInfoGridPageSize!='null'&&orgInfoGridPageSize.length>0){
			pagesize=orgInfoGridPageSize;
		}
		pageingToolbar = (Ext.getCmp('orgInfoGrid').getBottomToolbar());
		pageingToolbar.pageSize = Number(pagesize);
		var s = Ext.getCmp('orgInfoGrid').store;
		s.baseParams.limit = pagesize;
		if(s.lastOptions && s.lastOptions.params){
			s.lastOptions.params.limit = pagesize;
		}
});
   Ext.onReady(function(){
		var pgrid = Ext.getCmp('orgInfoGrid');
		var bbar = pgrid.getBottomToolbar();
		bbar.insertButton(11,[
		                      new Ext.menu.Separator({cls:'xtb-sep'}),
								new Ext.Button({
									icon : 'images/icon/table.gif',
									id:'saveSortBtn',
								    text:'����Excel',
								    handler:function(){
								    	expExcelFromGrid();
								    }
								}),
								new Ext.Button({
									icon : 'images/keyedit.gif',
									id:'setPageSize',
								    text:'����ÿҳ����',
								    handler:setPageSize1
								})
								]); 
   });
 
   function aotumaticCode(){
	   startImport();
   }
   function startImport(){
	   var checkedgroupid=document.getElementById("checkedgroupid").value;
	   var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther&eventNames=aotumaticCode_onclick';
   		ShowCellCover('start','ϵͳ��ʾ','���ڲ�������� ,�����Ե�...');
	   	Ext.Ajax.request({
	   		timeout: 900000,
	   		url: path,
	   		async: true,
	   		method :"post",
	   		params : {
				'checkedgroupid':checkedgroupid
	   		},
	       
	           callback: function (options, success, response) {
	        	   if (success) {
	        		   var result = response.responseText;
	   					if(result){
	   						Ext.Msg.hide();
	   						var json = eval('(' + result + ')');
	   						var data_str=json.data;
	   						var arr=data_str.split('@');
	   						if(arr[0]==2){//�ɹ�
	   							alert(arr[1]);
	   							radow.doEvent("orgInfoGrid.dogridquery");
	   						}else if(arr[0]==1){//ʧ��
	   							alert(arr[1]);
	   						}else if(arr[0]==3){//���ڲ��Ϸ��Ļ�������
	   							judgeReult(arr[1],'ȷ��','ȡ��');
	   							//this.getExecuteSG().addExecuteCode("judgeReult('"+title+"','ȷ��','ȡ��'');");
	   						}else if(arr[0]==4){//���ڱ���Ϊ�յķ��˵�λ
	   							judgeReult1(arr[1],'ȷ��','ȡ��');
	   						}
	   						Ext.Msg.hide();
	   					}
	        	   }
	           }
	      });
   }
   function judgeReult1(title,leftButtom,rightButtom){
	   	var win11 = new Ext.Window({
	   		border:false,//ȥ���ı��ı߿�
	   		title: 'ϵͳ��ʾ',   
	   		msg: title, 
	   		width:280,          //���ô��ڴ�С;
	   		height:110,
	   		closeAction:'hide', //������Ͻǹرհ�ť���ִ�еĲ���;
	   		closable:true,     //���عرհ�ť;
	   		draggable:true,     //���ڿ��϶�;
	   		plain:true,              //ʹ������������ڿ����ɫ;
	   		buttonAlign:'center',
	   		html: '<div style="width:100%;text-align:center;">'+title+'</div>',
	   		buttons:[{
	       		  text:leftButtom,
	       		  handler:function(){ //��������ش���;
	       			 //�鿴��Ϊ���Ҳ��Ϸ��Ļ�����Ϣ
	       			 document.getElementById("scanwrongfulunid").value='false';//�����б���ʾ���Ϸ�������Ϣ����־Ϊfalse
	       			document.getElementById("scanlegalperson").value='true';//���÷��˵�λ����Ϊ�ձ�־Ϊtrue
	       			
	       			 radow.doEvent("scanLegalPerson");
	       			  win11.hide();
	       		  }
	       		},
	       		{
	         		  text:rightButtom,
	         		  handler:function(){ //��������ش���;
	         			win11.hide();
	         		  }
	         		}
	   		]
	   		});
	   		win11.show();
	   }
   function judgeReult(title,leftButtom,rightButtom){
   	var win11 = new Ext.Window({
   		border:false,//ȥ���ı��ı߿�
   		title: 'ϵͳ��ʾ',   
   		msg: title, 
   		width:280,          //���ô��ڴ�С;
   		height:110,
   		closeAction:'hide', //������Ͻǹرհ�ť���ִ�еĲ���;
   		closable:true,     //���عرհ�ť;
   		draggable:true,     //���ڿ��϶�;
   		plain:true,              //ʹ������������ڿ����ɫ;
   		buttonAlign:'center',
   		html: '<div style="width:100%;text-align:center;">'+title+'</div>',
   		buttons:[{
       		  text:leftButtom,
       		  handler:function(){ //��������ش���;
       			 //�鿴��Ϊ���Ҳ��Ϸ��Ļ�����Ϣ
       			  document.getElementById("scanwrongfulunid").value='true';//�����б���ʾ���Ϸ�������Ϣ����־Ϊtrue
	       			document.getElementById("scanlegalperson").value='false';//���÷��˵�λ����Ϊ�ձ�־Ϊfalse
       			 radow.doEvent("scanWrongfulUnid");
       			  win11.hide();
       		  }
       		},
       		{
         		  text:rightButtom,
         		  handler:function(){ //��������ش���;
         			win11.hide();
         		  }
         		}
   		]
   		});
   		win11.show();
   }
   //��ٽ�����
   function ShowCellCover(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "�ر�";
	   	if(elementId.indexOf("start") != -1){
	   	
	   		Ext.MessageBox.show({
	   			title:titles,
	   			msg:msgs,
	   			width:300,
	   	        height:300,
	   			closable:false,
	   		//	buttons: Ext.MessageBox.OK,		
	   			modal:true,
	   			progress:true,
	   			wait:true,
	   			animEl: 'elId',
	   			increment:5, 
	   			waitConfig: {interval:150}
	   			//,icon:Ext.MessageBox.INFO        
	   		});
	   	}
   }
   //���¸�ѡ��ѡ�з����� ��set ����
   Ext.apply(Ext.data.Record.prototype,{set : function(name, value){
	    if(String(this.data[name]) == String(value)){
	        return;
	    }
	    //this.dirty = true;
	    if(!this.modified){
	        this.modified = {};
	    }
	    if(typeof this.modified[name] == 'undefined'){
	        this.modified[name] = this.data[name];
	    }
	    this.data[name] = value;
	    if(!this.editing){
	        this.store.afterEdit(this);
	    }       
	}})
	
	//����ÿҳ����
	function setPageSize1(){
		
		var gridId = 'orgInfoGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ���õ��б����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGridSOO.jsp";
			doOpenPupWin(url, "����ÿҳ����", 300, 150);
		} else {
			odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
			return;
		}
	}
	
	
	
	/* ��ػس��¼�,�󶨻�����λ  */ //readyonly ��ɾ��
	document.onkeydown=function() {
		if (event.keyCode == 8) { 
			if ((document.activeElement.type == "text"||document.activeElement.type == "textarea")&&document.activeElement.readOnly == true) {
				
				var id = document.activeElement.id;
				var index = id.indexOf('_combo');
				var index2 = id.indexOf('comboxArea_');
				if(index!=-1){
					var realid =  id.substring(0,index);
					document.getElementById(realid).value='';
					document.getElementById(id).value='';
					onkeydownfn(realid);
					return false;
				}else if(index2!=-1){
					var realid =  id.substring(11,id.length);
					document.getElementById(realid).value='';
					document.getElementById(id).value='';
					onkeydownfn(realid);
					return false;
				}
				return false;
			}
			if(document.activeElement.type != "password" && document.activeElement.type != "text" 
				&& document.activeElement.type != "textarea"){
				return false;
			}   
	 
		}else if(event.keyCode == 27){	//����ESC
	        return false;   
		}
	};
	
	
	function deleteOrg(){
		radow.doEvent('deleteOrgBtn');
	}
	
	function synchroOA(){
		
	radow.doEvent('synchroOA');
	}
	
	function synchroOAUser(){
		radow.doEvent('synchroOAUser');
	}
	
	function againReq(unitname){
		$h.confirm("ϵͳ��ʾ��",'ȷ��ɾ������'+unitname+',�Ƿ������',330,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('����ɾ����...','ϵͳ��ʾ');
				radow.doEvent('deleteExec');
			}else{
				return false;
			}		
		});
	}
	
	
	function openZZSWin(){
		
	}
</script>