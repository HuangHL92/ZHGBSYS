<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<%-- <%@include file="/comOpenWinInit.jsp" %> --%>
<style type="text/css">  
#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"΢���ź�";}
</style>  
        
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/DataAnalysis/map_load.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="jslib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="jslib/echarts/themes/macarons.js"></script>
<script  type="text/javascript">
function xzgw(){
	radow.doEvent('xzgw');
}
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function() {


	var ereaid = document.getElementById('ereaid').value;
	//alert(ereaid);
	//radow.doEvent("querybyid",ereaid);
	//initAllChart(zt8,ft8,zc8,fc8,zt9,ft9,zc9,fc9,zt,ft,zc,fc,zt1,ft1,zc1,fc1,zt2,ft2,zc2,fc2,zt3,ft3,zc3,fc3);
});

function initAllChart() {

}
</script>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>��Ա�б�</h3>" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="�쵼����" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="" id="refresh" isLast="true" handler="refresh"/>
</odin:toolBar>

<odin:toolBar property="btnToolBar2" applyTo="groupTreePanel2">
	<odin:textForToolBar text="<h3>��Ա�켣</h3>" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="�쵼����" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��ѯ" id="refresh" icon="images/search.gif" isLast="true" handler="refresh"/>
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
			<td style="height:100%;width:150px;">
				<div id="groupTreePanel"></div>
				<!-- <div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2 url(image/right.png) no-repeat center center;"></div> -->
				<!-- <div id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2  no-repeat center center;"></div> -->
				<odin:editgrid property="grid1" autoFill="true" forceNoScroll="true" pageSize="100"
						topBarId="ToolBar" url="/">
					<odin:gridJsonDataModel id="id" root="data">
					        <odin:gridDataCol name="a0000" /> 
							<odin:gridDataCol name="a0101" isLast="true"/>	
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="��ԱID" align="center" edited="false" width="100" dataIndex="a0000" editor="text" hidden="true"/>
							<odin:gridEditColumn2 header="����" align="center" edited="false" width="100" dataIndex="a0101" isLast="true" editor="text" />
					 </odin:gridColumnModel>
				</odin:editgrid>	
			</td>
			<td>
				<div id="echartsdiv" style="width:100%;height: 100%;float: left;border:0px;margin: 0px;padding: 0px; ">
					<div id="groupTreePanel2"></div>
					<div id="Retire" style="width: 100%; height: 75px; margin: 0 auto;">
						<odin:groupBox title="��ѯ����">
							<table style="width: 100%;">
								<tr>
									<odin:textEdit property="personname" label="��Ա����" ></odin:textEdit>
								</tr>
							</table>
						</odin:groupBox>
					</div>
					<div style="text-align:center;padding-top:0px;">
						<div id="allmap"></div>
						<!-- <div id="container" style="width: 800px; height: 400px; margin: 20 auto;"></div> -->
					</div>						
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
	<odin:hidden property="a0000" />
	<odin:hidden property="checkedgroupid" />
	<odin:hidden property="forsearchgroupid" />
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<odin:hidden property="picType" value="<%=picType%>" />
	


<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById('personname').onkeydown = function(e) {
		var ev = (typeof event!= 'undefined') ? window.event : e;
		if(ev.keyCode == 13) {
			return false;
		}
	};
	$("#groupTreeContent").show();
	var viewSize = Ext.getBody().getViewSize();
	//Ext.get('commForm').setWidth(viewSize.width);
	//alert(viewSize.height);
	var tableTab1 = document.getElementById("tableTab1"); 
	tableTab1.style.height = viewSize.height+100+"px";//87 82
	
	//divresize
	//var divresize = document.getElementById("divresize"); 
	//divresize.style.height = viewSize.height+"px";//87 82
	//tab
	var tab = document.getElementById("tab"); 
	tab.style.height = viewSize.height+15+"px";//87 82
	
	var grid1 = Ext.getCmp('grid1');
	grid1.setHeight(viewSize.height+50);
	//grid1.setWidth(viewSize.width-387);
	grid1.setWidth(150);
	/* var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);*/
	grid1.on('rowdblclick',function(gridobj,index,e){  
		var person = gridobj.getStore().getAt(index)
		var ereaid = document.getElementById('ereaid').value;
		var a0000 = person.data.a0000;
		document.getElementById('a0000').value = a0000;
		//alert(a0000);
		radow.doEvent("getmap");
		//$h.openPageModeWin('ChangesItem','pages.DataAnalysis.ChangesItem','��Ϣ�䶯����',580,300,{dataname:rc.data.dataname,objectid:rc.data.objectid,ereaid:ereaid},g_contextpath);
		//radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //�ر�  
	});
	
});

var ht=document.body.clientHeight-35;
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
        height:ht,
        width: 330,
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


<script type="text/javascript">
function baidumap(jwd){
	//console.log(jwd);
	//alert(jwd);
	// �ٶȵ�ͼAPI����
	var map = new BMap.Map("allmap");    // ����Mapʵ��
	map.clearOverlays();//���ԭ���ı�ע
	map.centerAndZoom(new BMap.Point(105.358,27.285), 6);  // ��ʼ����ͼ,�������ĵ�����͵�ͼ����
	map.enableScrollWheelZoom(true);     //��������������
	var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
	scale: 0.6,//ͼ�����Ŵ�С
	strokeColor:'#fff',//����ʸ��ͼ����������ɫ
	strokeWeight: '2'//�����߿�
	});
	var icons = new BMap.IconSequence(sy, '10', '30');
	//����polyline����
	var pois = jwd;
	/* var pois = [
	new BMap.Point(106.350658,26.938285),
	new BMap.Point(106.389034,29.913828),
	new BMap.Point(111.442501,28.914603)
	]; */
	var polyline =new BMap.Polyline(pois, {
	enableEditing: true,//�Ƿ������߱༭��Ĭ��Ϊfalse
	enableClicking: true,//�Ƿ���Ӧ����¼���Ĭ��Ϊtrue
	icons:[icons],
	strokeWeight:'8',//���ߵĿ�ȣ�������Ϊ��λ
	strokeOpacity: 0.8,//���ߵ�͸���ȣ�ȡֵ��Χ0 - 1
	strokeColor:"#18a45b" //������ɫ
	});
	
	map.addOverlay(polyline);          //��������
	
	/* map.addEventListener("click",function(e){
		//alert(1);
		alert(e.point.lng + "," + e.point.lat);
	}); */
}
</script>
