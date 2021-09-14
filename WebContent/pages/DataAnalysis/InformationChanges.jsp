<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
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
	var currentDate = new Date();
	var Y = currentDate.getFullYear()+"-";
	var M = currentDate.getMonth()+1 < 10 ? '0'+(currentDate.getMonth()+1)+"-" : currentDate.getMonth()+1+"-";
	var D = currentDate.getDate() < 10 ? '0'+(currentDate.getDate()) : currentDate.getDate();
	$("#warndate").val(""+Y+M+D);
	
	var zt8 = 0; var ft8 = 0; var zc8 = 0; var fc8 = 0;
	var zt9 = 0; var ft9 = 0; var zc9 = 0; var fc9 = 0;
	var zt = 0; var ft = 0; var zc = 0; var fc = 0;
	var zt1 = 0; var ft1 = 0; var zc1 = 0; var fc1 = 0;
	var zt2 = 0; var ft2 = 0; var zc2 = 0; var fc2 = 0;
	var zt3 = 0; var ft3 = 0; var zc3 = 0; var fc3 = 0;
	//initAllChart(zt8,ft8,zc8,fc8,zt9,ft9,zc9,fc9,zt,ft,zc,fc,zt1,ft1,zc1,fc1,zt2,ft2,zc2,fc2,zt3,ft3,zc3,fc3);
});

function initAllChart(zt8,ft8,zc8,fc8,zt9,ft9,zc9,fc9,zt,ft,zc,fc,zt1,ft1,zc1,fc1,zt2,ft2,zc2,fc2,zt3,ft3,zc3,fc3) {

	var myChart1 = echarts.init(document.getElementById("RetireChart1"), 'macarons');
	var myChart2 = echarts.init(document.getElementById("RetireChart2"), 'macarons');
    zt10 = zt-zt8-zt9; 
    ft10 = ft-ft8-ft9; 
    zc10 = zc-zc8-zc9; 
    fc10 = fc-fc8-fc9; 

    zt4 = zt-zt1-zt2-zt3;  
    ft4 = ft-ft1-ft2-ft3;  
    zc4 = zc-zc1-zc2-zc3;  
    fc4 = fc-fc1-fc2-fc3;  

	option1 = {			
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // ������ָʾ���������ᴥ����Ч
	            type : 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['80��','90��','����']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : ['���ּ���ְ','���ּ���ְ','�ش�����ְ','�ش�����ְ']
	            //code : ['1A21','1A22','1A31','1A32']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'80��',
	            type:'bar',
	            barWidth : 40,
	            stack: '����',
	            label: {
	                normal: {
	                    show: true
	                    //position: 'insideRight'
	                }
	            },
	            //data:[3,5,2,4],
	            data:[zt8,ft8,zc8,fc8],
	            itemStyle:{
                    normal:{
                    	color:'#C23531'   
                    }
                }
	        },
	        {
	            name:'90��',
	            type:'bar',
	            barWidth : 40,
	            stack: '����',
	            label: {
	                normal: {
	                    show: true
	                    //position: 'insideRight'
	                }
	            },
	            //data:[5,1,3,2],
	            data:[zt9,ft9,zc9,fc9],
	            itemStyle:{
                    normal:{
                        color:'#3398DB'  
                    }
                }
	        },
	        {
	            name:'����',
	            type:'bar',
	            barWidth : 40,
	            stack: '����',
	            label: {
	                normal: {
	                    show: true
	                    //position: 'insideRight'
	                }
	            },
	            //data:[3,6,2,4],
	            data:[zt10,ft10,zc10,fc10],
	            itemStyle:{
                    normal:{
                    	color:'#4ad2ff'
                    }
                }
	        }
	    ]
	};
	
	option2 = {
			
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // ������ָʾ���������ᴥ����Ч
		            type : 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:['35������','35��-40��','40��-45��','����']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['���ּ���ְ','���ּ���ְ','�ش�����ְ','�ش�����ְ']
		            //code : ['1A21','1A22','1A31','1A32']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'35������',
		            type:'bar',
		            barWidth : 40,
		            stack: '����',
		            //data:[3,5,2,4],
		            label: {
		                normal: {
		                    show: true
		                    //position: 'insideRight'
		                }
		            },
		            data:[zt1,ft1,zc1,fc1],
		            itemStyle:{
	                    normal:{
	                    	color:'#C23531'   
	                    }
	                }
		        },
		        {
		            name:'35��-40��',
		            type:'bar',
		            barWidth : 40,
		            stack: '����',
		            //data:[5,1,3,2],
		            label: {
		                normal: {
		                    show: true
		                    //position: 'insideRight'
		                }
		            },
		            data:[zt2,ft2,zc2,fc2],
		            itemStyle:{
	                    normal:{
	                        color:'#3398DB'  
	                    }
	                }
		        },
		        {
		            name:'40��-45��',
		            type:'bar',
		            barWidth : 40,
		            stack: '����',
		            //data:[5,1,3,2],
		            label: {
		                normal: {
		                    show: true
		                    //position: 'insideRight'
		                }
		            },
		            data:[zt3,ft3,zc3,fc3],
		            itemStyle:{
	                    normal:{
	                        color:'#6AB0B8'  
	                    }
	                }
		        },
		        {
		            name:'����',
		            type:'bar',
		            barWidth : 40,
		            stack: '����',
		            //data:[3,6,2,4],
		            label: {
		                normal: {
		                    show: true
		                    //position: 'insideRight'
		                }
		            },
		            data:[zt4,ft4,zc4,fc4],
		            itemStyle:{
	                    normal:{
	                    	color:'#4ad2ff'
	                    }
	                }
		        }
		    ]
		};
	
	myChart1.setOption(option1);
	myChart2.setOption(option2);
	if(myChart1._$handlers.click){
	    myChart1._$handlers.click.length = 0;
	}
	if(myChart2._$handlers.click){
	    myChart2._$handlers.click.length = 0;
	}
	myChart1.on('click', function(param) {
		var zjname = param.name;      //ְ������
	    var gwcode = "";      //ְ������
	    if(zjname=="���ּ���ְ"){
	    	gwcode = "1A21";
	    }else if(zjname=="���ּ���ְ"){
	    	gwcode = "1A22";
	    }else if(zjname=="�ش�����ְ"){
	    	gwcode = "1A31";
	    }else if(zjname=="�ش�����ְ"){
	    	gwcode = "1A32";
	    }
	    var seriesName = param.seriesName;   //�������
	    if(seriesName=="����"){
	    	seriesName = param.seriesName+1;   //�������
	    }
	    var ereaid = document.getElementById('ereaid').value;
	    var warndate = document.getElementById('warndate').value;
	    $h.openPageModeWin('YoungCadreData','pages.DataAnalysis.YoungCadreData','����ɲ�����',580,300,{gwcode:gwcode,seriesName:seriesName,ereaid:ereaid,warndate:warndate},g_contextpath);
	});
	myChart2.on('click', function(param) {
		var zjname = param.name;      //ְ������
	    var gwcode = "";      //ְ������
	    if(zjname=="���ּ���ְ"){
	    	gwcode = "1A21";
	    }else if(zjname=="���ּ���ְ"){
	    	gwcode = "1A22";
	    }else if(zjname=="�ش�����ְ"){
	    	gwcode = "1A31";
	    }else if(zjname=="�ش�����ְ"){
	    	gwcode = "1A32";
	    }
	    var seriesName = param.seriesName;   //�������
	    if(seriesName=="����"){
	    	seriesName = param.seriesName+2;   //�������
	    }
	    var ereaid = document.getElementById('ereaid').value;
	    var warndate = document.getElementById('warndate').value;
	    $h.openPageModeWin('YoungCadreData','pages.DataAnalysis.YoungCadreData','����ɲ�����',580,300,{gwcode:gwcode,seriesName:seriesName,ereaid:ereaid,warndate:warndate},g_contextpath);
	});
}
</script>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>��Ϣ���޸ķ���</h3>" />
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
<odin:toolBar property="toolbar1" applyTo="tab1_topbar1">
<odin:textForToolBar text="<h3>ְλ���</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<%-- <odin:buttonForToolBar text="ѡ��ְλ" id="xzgw" handler="xzgw" icon="image/icon021a4.gif"></odin:buttonForToolBar>
<odin:separator/> --%>
<odin:buttonForToolBar text="����ְλ���" id="dcgwxq" icon="images/tb_2.jpg"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<div id="groupTreeContent" style="display:none;height: 100%; padding-top: 0px;">
	<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
		<tr>
			<td width="370" height="100%" id="td1" >
				<table width="370"  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;">
					<tr>
						<td valign="top">
							<odin:tab id="tab" width="370"  tabchange="grantTabChange">
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
				<div id="echartsdiv" style="width:100%;height: 100%;float: left;border:0px;margin: 0px;padding: 0px; ">
					<div id="groupTreePanel"></div>
					<!-- <div id="Retire" style="width: 100%; margin: 0 auto;"> -->
						<odin:groupBox title="��ѯ����">
							<table style="width: 100%;">
								<tr>
									<odin:textEdit property="dataname" label="��Ϣ��" ></odin:textEdit>
									<odin:textEdit property="objectname" label="���޸���" ></odin:textEdit>
								</tr>
							</table>
						</odin:groupBox>
					<!-- </div> -->
					<!-- <div style="text-align:center;padding-top:0px;"> -->
						<odin:editgrid property="grid1" autoFill="true" forceNoScroll="true" pageSize="100"
							topBarId="ToolBar" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel id="id" root="data">
							        <odin:gridDataCol name="dataname" /> 
									<odin:gridDataCol name="objectid" />	
									<odin:gridDataCol name="objectname" />	
									<odin:gridDataCol name="cs" isLast="true" />
				
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="��Ϣ��" align="center" edited="false" width="100" dataIndex="dataname" editor="text" />
									<odin:gridEditColumn2 header="������id" align="center" edited="false" width="70" dataIndex="objectid" hidden="true" editor="text" />
									<odin:gridEditColumn2 header="���޸���" align="center" edited="false" width="120" dataIndex="objectname" editor="text" />
									<odin:gridEditColumn header="�޸Ĵ���" align="center" edited="false" width="80" dataIndex="cs" isLast="true" editor="text" />
							 </odin:gridColumnModel>
						</odin:editgrid>
					<!-- </div>		 -->
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
	<odin:hidden property="dataname"/>
	<odin:hidden property="objectid"/>
	


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
	divresize.style.height = viewSize.height+15+"px";//87 82
	//tab
	var tab = document.getElementById("tab"); 
	tab.style.height = viewSize.height+15+"px";//87 82
	
	var grid1 = Ext.getCmp('grid1');
	grid1.setHeight(viewSize.height-90);
	grid1.setWidth(viewSize.width-360);
	
	var echartsdiv = document.getElementById("echartsdiv"); 
	echartsdiv.style.width = viewSize.width-360+"px";//87 82
	
	grid1.on('rowdblclick',function(gridobj,index,e){  
		var rc = gridobj.getStore().getAt(index)
		var ereaid = document.getElementById('ereaid').value;
		document.getElementById('dataname').value = rc.data.dataname;
		document.getElementById('objectid').value = rc.data.objectid;
		
		$h.openPageModeWin('ChangesItem','pages.DataAnalysis.ChangesItem','��Ϣ�䶯����',580,300,{dataname:rc.data.dataname,objectid:rc.data.objectid,ereaid:ereaid},g_contextpath);
		//radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //�ر�  
	});
	
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
        width: 370,
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
	//tree.expandPath(root.getPath(),null,function(){addnode();});
	//tree.expandPath(root.getPath(),null,function(){addnodebm();});   //����ְ����
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


