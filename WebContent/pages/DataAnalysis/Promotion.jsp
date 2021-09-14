<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<style>
table{table-layout:fixed}
.imgtop {float:right;position:relative;bottom:-62px;right:80px;}
.imgbottom {float:right;position:relative;top:-46px;right:80px;}
.dateitem {height:100px!important;width:180px!important;border:1px solid #000000;z-index:-1;font:14px/1.5 tahoma, arial, ΢���ź�;}
.dateitemstep {width:90px!important;}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="jslib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="jslib/echarts/themes/macarons.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/pages/DataAnalysis/js/jquery.min.js"></script>

<script  type="text/javascript">
function xzgw(){
	radow.doEvent('xzgw');
}
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function() {
	//var ereaid = document.getElementById('ereaid').value;
	//alert(ereaid);
	//radow.doEvent("querybyid",ereaid);
	//initAllChart();
});

function initAllChart(divstr){
	//alert(divstr);
	$("#timelinediv").empty();
	$("#timelinediv").append(divstr);   
}

</script>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>����ɲ����ݷ���</h3>" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="�쵼����" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ˢ��" id="refresh" icon="images/icon/table.gif" isLast="true" handler="refresh"/>
</odin:toolBar>


<div id="groupTreeContent" style="display:none;height: 100%; padding-top: 0px;">
	<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
		<tr>
			<td width="330" height="100%" id="td1" >
				<table width="330"  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;">
					<tr>
						<td valign="top">
							<odin:tab id="tab" width="330"  tabchange="grantTabChange">
								<odin:tabModel>
									<odin:tabItem title="������" id="tab1" isLast="true"></odin:tabItem>
							   <%-- <odin:tabItem title="&nbsp&nbsp��ѯ&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>   --%>
								</odin:tabModel>
								<odin:tabCont itemIndex="tab1" className="tab">
									<table id="tableTab1" style="border-collapse:collapse;">
										<tr>
											<td>
											<div id="tree-div" style="overflow: auto; height: 100%; width: 100%; border: 2px solid #c3daf9;">
											</div>
											</td>
										</tr>
									</table>
								</odin:tabCont>							
							</odin:tab>
						</td>						
					</tr>
				</table>
			</td>
			<td style="height:100%;width:7px;">
				<!-- <div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2 url(image/right.png) no-repeat center center;"></div> -->
				<div id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2  no-repeat center center;"></div>
			</td>
			<td>
				<div id="echartsdiv" style="width:100%; height: 100%;float: left;border:0px;margin: 0px;padding: 0px; overflow:auto;">
					<!-- <div id="groupTreePanel" style="width:100%;"></div>  -->
					<div id="Retire" style="display:none;width: 100%; height: 100px; margin: 0 auto;">
						<odin:groupBox title="��ѯ����">
							<table style="width: 100%;">
								<tr>
									<odin:dateEdit property="warndate" label="����ʱ��" ></odin:dateEdit>
								</tr>
							</table>
						</odin:groupBox>
					</div>
					<div id="timelinediv" style="height:100%;font-size:4px">
					<!-- <div style="margin-top: 15px;">
						<table style="margin-bottom: 15px;">
						 <tr style="border:1px solid #000;">
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop"  src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ������������յ��ĵĵĵĵĵĵĵĵĵĵ�<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop"  src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop"  src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem"><img class="imgtop"  src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem"><img class="imgtop"  src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						 </tr>
						</table>
						<hr color="red"/>
						<table style="margin-top: 15px;">
						 <tr style="border:1px solid #000;">
						  <td style="width:135px;"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom"  src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom"  src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom"  src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						 </tr>
						</table>
					</div>
					
					<div style="margin-top: 15px;">
						<table style="margin-bottom: 15px;">
						 <tr style="border:1px solid #000;">
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop" src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ������������յ��ĵĵĵĵĵĵĵĵĵĵ�<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop" src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem"><img class="imgtop" src="./pages/DataAnalysis/imgs/topItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						 </tr>
						</table>
						<hr color="red"/>
						<table style="margin-top: 15px;">
						 <tr style="border:1px solid #000;">
						  <td width="135"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom" src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom" src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem"><img class="imgbottom" src="./pages/DataAnalysis/imgs/bottomItem1.png"/>�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						 </tr>
						</table>
					</div>
					
					<div style="margin-top: 80px;">
						<table>
						 <tr style="border:1px solid #000;">
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ������������յ��ĵĵĵĵĵĵĵĵĵĵ�<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						 </tr>
						</table>
						<hr color="red"/>
						<table>
						 <tr style="border:1px solid #000;">
						  <td width="135"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F
						  " class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						 </tr>
						</table>
					</div>
					
					<div style="margin-top: 80px;margin-bottom: 80px;">
						<table>
						 <tr style="border:1px solid #000;">
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ������������յ��ĵĵĵĵĵĵĵĵĵĵ�<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#A3C1FF" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FFA995" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						 </tr>
						</table>
						<hr color="red"/>
						<table>
						 <tr style="border:1px solid #000;">
						  <td width="135"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">�Ժ��� 2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						  <td class="dateitemstep"></td>
						  <td  bgcolor="#FED59F" class="dateitem">2003<br>ˮ����<br>ˮ������Ա</td>
						 </tr>
						</table>
					</div> -->
					</div>
					<!-- <div style="position:absolute;margin-left:10px;margin-top:10px">
						<div style="float:left;width:150px;heigth:180px;border:1px solid #000">
						 <p>200209<br>����ʡˮ����<br>ˮ��������</p>
						</div>
						<div style="float:left;width:60px;heigth:2px;;border-bottom:1px solid #000">
						</div>
						<div style="float:left;width:150px;heigth:180px;border:1px solid #000">
						 <p>200209<br>����ʡˮ����<br>ˮ��������</p>
						</div>
					</div> -->
					<!-- <div class="container">
					  <div class="fishBoneDiv" />
					</div> -->
					<!-- <div id="timelinediv"  style=" width:2000px; height:100%;text-align:center;padding-top:0px;padding-bottom:40px;">
						<div id="container"></div>
						<div id="container" style="width: 800px; height: 400px; margin: 20 auto;"></div>
					</div> -->		
				</div>
						
			</td>
		</tr>
	</table>
</div>


<%
	String ereaname = (String) (new OrgSortPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new OrgSortPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new OrgSortPageModel().areaInfo
			.get("manager"));
	String picType = (String)(new OrgSortPageModel().areaInfo.get("picType"));
%>
	<odin:hidden property="checkedgroupid" />
	<odin:hidden property="forsearchgroupid" />
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<odin:hidden property="picType" value="<%=picType%>" />
	


<script type="text/javascript">
Ext.onReady(function() {
	$("#groupTreeContent").show();
	var viewSize = Ext.getBody().getViewSize();
	//Ext.get('commForm').setWidth(viewSize.width);
	//alert(viewSize.height);
	var tableTab1 = document.getElementById("tableTab1"); 
	tableTab1.style.height = viewSize.height+100+"px";//87 82
	
	//divresize
	var divresize = document.getElementById("divresize"); 
	divresize.style.height = viewSize.height+"px";//87 82
	//tab
	var tab = document.getElementById("tab"); 
	tab.style.height = viewSize.height+"px";//87 82
	
	/* var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);*/
	
});
var ht=document.body.clientHeight-30;
var tree;
 Ext.onReady(function() {
	var newWin_1 = $h.getTopParent().Ext.getCmp('conditionwin');
	if(newWin_1){
		newWin_1.close();
	}
	newWin_1 = $h.getTopParent().Ext.getCmp('group');
	if(newWin_1){
		newWin_1.close();
	}
	  var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
       tree = new Tree.TreePanel( {
  	  id:'group',
        el : 'tree-div',//Ŀ��div����
        split:false,
        width: 340,
        height:ht,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
        autoScroll : true,
        animate : true,
        border:false,
        enableDD : false,
        containerScroll : true,
        loader : new Tree.TreeLoader( {
        	  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataTree'
            //dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1'
        })
    });
    //listeners:{'itemclick':tree_event},
	var root = new Tree.AsyncTreeNode({
		checked : false,
		text : document.getElementById('ereaname').value,
		iconCls : document.getElementById('picType').value,
		draggable : false,
		id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
		href : "javascript:radow.doEvent('querybyid','" + document.getElementById('ereaid').value + "')"		
	});
	//alert(document.getElementById('ereaid').value);
	//document.getElementById('ereaid').value = 
	tree.setRootNode(root);
	tree.render();
	tree.expandPath(root.getPath(),null,function(){addnode();});
	tree.expandPath(root.getPath(),null,function(){addnodebm();});   //����ְ����
	root.expand(false,true, callback);
	
}); 

function tree_event(node,event){
	alert("1");
	/* var id=event.data.id;
	Ext.Msg.show({
		title:'��ʾ',
		msg:'�㵥����'+id,
	}); */
};
	
var callback = function (node){
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}
var flag_ss=false;
function abcde(obj){
	return '';
	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
	   if(flag_ss==false){
		 	//����
		 	document.getElementById("td1").style.display="none";
	   		/* document.getElementById("divtab2").firstChild.style.width=1;//��ѯ�б�������
	        var tree = Ext.getCmp('group');
	        //tree�Ŀ������
	        tree.setWidth(1);
	        //tab�Ŀ������
	        var resizeobj =Ext.getCmp('tab');
			resizeobj.setWidth(1); */
			
			flag_ss=true;//���ر�־
			//document.getElementById(obj.id).innerHTML='>';
			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
	    	grid.setWidth(width);//����grid�Ŀ��
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//��̬�����п�
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//��7��
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//��8�� */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
	   }else{ //��չ��
			
			/*  document.getElementById("divtab2").firstChild.style.width=250;//��ѯ�б�������
		     var tree = Ext.getCmp('group')
		     //tree�Ŀ������
		     tree.setWidth(250);
		     //tab�Ŀ������
		     var resizeobj =Ext.getCmp('tab');
			 resizeobj.setWidth(250); */
			 document.getElementById("td1").style.display="block";
			 
			 flag_ss=false;//��չ����־
			 //document.getElementById(obj.id).innerHTML='<span><</span>';
			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
	    	grid.setWidth(width);//����grid�Ŀ��
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//��̬�����п�
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//��7��
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//��8�� */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
	   }
}	

function refresh(){//ˢ��
	var ereaid = document.getElementById('ereaid').value;
	//alert(ereaid);
	radow.doEvent("querybyid",ereaid);
}










function leadviewshow(value, params, record, rowIndex, colIndex, ds){
	if(value=='1'){
		return 'ͬ��';
	}else if(value=='2'){
		return '��ͬ��';
	}
}

function rbmRenderer(value,params,record,rowIndex,colIndex,ds){
	if(value=='2'){
		return '';
	} else if(value=='1'){
		return "<a href=\"javascript:void();\" onclick=\"batchMergeCancel('"+record.data.rb_id+"')\">���غϲ�</a>";
	} else {
		return "<a href=\"javascript:void();\" onclick=\"batchMergeSent('"+record.data.rb_id+"')\">����ϲ�</a>";
	}
}
function batchMergeCancel(rb_id){
	radow.doEvent('rbmCancel',rb_id);
}
function batchMergeSent(rb_id){
	//radow.doEvent('rbmSent',rb_id);
	$h.showModalDialog('picupload',g_contextpath+'/pages/xbrm/MGDeptWin.jsp?rb_id='+rb_id+'','��Ϣ����', 300,100,null,{rb_id : rb_id},true);

}
function statusshow(value,params,record,rowIndex,colIndex,ds){
	if(value=='1'){
		return '���';
	}else{
		return '������';
	}
}


</script>


