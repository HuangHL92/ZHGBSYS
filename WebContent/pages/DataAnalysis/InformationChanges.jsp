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
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['80后','90后','其他']
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
	            data : ['厅局级正职','厅局级副职','县处级正职','县处级副职']
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
	            name:'80后',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
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
	            name:'90后',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
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
	            name:'其他',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
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
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:['35岁以下','35岁-40岁','40岁-45岁','其他']
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
		            data : ['厅局级正职','厅局级副职','县处级正职','县处级副职']
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
		            name:'35岁以下',
		            type:'bar',
		            barWidth : 40,
		            stack: '离任',
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
		            name:'35岁-40岁',
		            type:'bar',
		            barWidth : 40,
		            stack: '离任',
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
		            name:'40岁-45岁',
		            type:'bar',
		            barWidth : 40,
		            stack: '离任',
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
		            name:'其他',
		            type:'bar',
		            barWidth : 40,
		            stack: '离任',
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
		var zjname = param.name;      //职级名称
	    var gwcode = "";      //职级代码
	    if(zjname=="厅局级正职"){
	    	gwcode = "1A21";
	    }else if(zjname=="厅局级副职"){
	    	gwcode = "1A22";
	    }else if(zjname=="县处级正职"){
	    	gwcode = "1A31";
	    }else if(zjname=="县处级副职"){
	    	gwcode = "1A32";
	    }
	    var seriesName = param.seriesName;   //类别名称
	    if(seriesName=="其他"){
	    	seriesName = param.seriesName+1;   //类别名称
	    }
	    var ereaid = document.getElementById('ereaid').value;
	    var warndate = document.getElementById('warndate').value;
	    $h.openPageModeWin('YoungCadreData','pages.DataAnalysis.YoungCadreData','年轻干部反查',580,300,{gwcode:gwcode,seriesName:seriesName,ereaid:ereaid,warndate:warndate},g_contextpath);
	});
	myChart2.on('click', function(param) {
		var zjname = param.name;      //职级名称
	    var gwcode = "";      //职级代码
	    if(zjname=="厅局级正职"){
	    	gwcode = "1A21";
	    }else if(zjname=="厅局级副职"){
	    	gwcode = "1A22";
	    }else if(zjname=="县处级正职"){
	    	gwcode = "1A31";
	    }else if(zjname=="县处级副职"){
	    	gwcode = "1A32";
	    }
	    var seriesName = param.seriesName;   //类别名称
	    if(seriesName=="其他"){
	    	seriesName = param.seriesName+2;   //类别名称
	    }
	    var ereaid = document.getElementById('ereaid').value;
	    var warndate = document.getElementById('warndate').value;
	    $h.openPageModeWin('YoungCadreData','pages.DataAnalysis.YoungCadreData','年轻干部反查',580,300,{gwcode:gwcode,seriesName:seriesName,ereaid:ereaid,warndate:warndate},g_contextpath);
	});
}
</script>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>信息项修改分析</h3>" />
	<odin:fill />
	<%-- <odin:buttonForToolBar text="领导审批" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" id="refresh" icon="images/search.gif" isLast="true" handler="refresh"/>
</odin:toolBar>
<odin:toolBar property="toolbar1" applyTo="tab1_topbar1">
<odin:textForToolBar text="<h3>职位情况</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<%-- <odin:buttonForToolBar text="选择职位" id="xzgw" handler="xzgw" icon="image/icon021a4.gif"></odin:buttonForToolBar>
<odin:separator/> --%>
<odin:buttonForToolBar text="导出职位情况" id="dcgwxq" icon="images/tb_2.jpg"></odin:buttonForToolBar>
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
									<odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
							   <%-- <odin:tabItem title="&nbsp&nbsp查询&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>   --%>
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
						<odin:groupBox title="查询条件">
							<table style="width: 100%;">
								<tr>
									<odin:textEdit property="dataname" label="信息项" ></odin:textEdit>
									<odin:textEdit property="objectname" label="被修改人" ></odin:textEdit>
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
									<odin:gridEditColumn header="信息项" align="center" edited="false" width="100" dataIndex="dataname" editor="text" />
									<odin:gridEditColumn2 header="所属人id" align="center" edited="false" width="70" dataIndex="objectid" hidden="true" editor="text" />
									<odin:gridEditColumn2 header="被修改人" align="center" edited="false" width="120" dataIndex="objectname" editor="text" />
									<odin:gridEditColumn header="修改次数" align="center" edited="false" width="80" dataIndex="cs" isLast="true" editor="text" />
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
		
		$h.openPageModeWin('ChangesItem','pages.DataAnalysis.ChangesItem','信息变动详情',580,300,{dataname:rc.data.dataname,objectid:rc.data.objectid,ereaid:ereaid},g_contextpath);
		//radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //关闭  
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
        el : 'tree-div',//目标div容器
        split:false,
        width: 370,
        height:ht,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//是否显示最上级节点
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
		id : document.getElementById('ereaid').value,//默认的node值：?node=-100
		href : "javascript:radow.doEvent('querybyid','" + document.getElementById('ereaid').value + "')"		
	});
	//alert(document.getElementById('ereaid').value);
	//document.getElementById('ereaid').value = 
	tree.setRootNode(root);
	tree.render();
	//tree.expandPath(root.getPath(),null,function(){addnode();});
	//tree.expandPath(root.getPath(),null,function(){addnodebm();});   //无任职部门
	root.expand(false,true, callback);
	
}); 

function tree_event(node,event){
	alert("1");
	/* var id=event.data.id;
	Ext.Msg.show({
		title:'提示',
		msg:'你单击了'+id,
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
		 	//收缩
		 	document.getElementById("td1").style.display="none";
	   		/* document.getElementById("divtab2").firstChild.style.width=1;//查询列表宽度设置
	        var tree = Ext.getCmp('group');
	        //tree的宽度设置
	        tree.setWidth(1);
	        //tab的宽度设置
	        var resizeobj =Ext.getCmp('tab');
			resizeobj.setWidth(1); */
			
			flag_ss=true;//隐藏标志
			//document.getElementById(obj.id).innerHTML='>';
			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }else{ //伸展开
			
			/*  document.getElementById("divtab2").firstChild.style.width=250;//查询列表宽度设置
		     var tree = Ext.getCmp('group')
		     //tree的宽度设置
		     tree.setWidth(250);
		     //tab的宽度设置
		     var resizeobj =Ext.getCmp('tab');
			 resizeobj.setWidth(250); */
			 document.getElementById("td1").style.display="block";
			 
			 flag_ss=false;//伸展开标志
			 //document.getElementById(obj.id).innerHTML='<span><</span>';
			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
			var grid=Ext.getCmp('peopleInfoGrid');
	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
	    	grid.setWidth(width);//重置grid的宽度
	    	
	    	var grid_width=grid.getWidth();
	    	//50 150 300 400 200
	    	//动态设置列宽
	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
	   }
}	

function refresh(){//刷新
	var ereaid = document.getElementById('ereaid').value;
	//alert(ereaid);
	radow.doEvent("querybyid",ereaid);
}










function leadviewshow(value, params, record, rowIndex, colIndex, ds){
	if(value=='1'){
		return '同意';
	}else if(value=='2'){
		return '不同意';
	}
}

function rbmRenderer(value,params,record,rowIndex,colIndex,ds){
	if(value=='2'){
		return '';
	} else if(value=='1'){
		return "<a href=\"javascript:void();\" onclick=\"batchMergeCancel('"+record.data.rb_id+"')\">撤回合并</a>";
	} else {
		return "<a href=\"javascript:void();\" onclick=\"batchMergeSent('"+record.data.rb_id+"')\">发起合并</a>";
	}
}
function batchMergeCancel(rb_id){
	radow.doEvent('rbmCancel',rb_id);
}
function batchMergeSent(rb_id){
	//radow.doEvent('rbmSent',rb_id);
	$h.showModalDialog('picupload',g_contextpath+'/pages/xbrm/MGDeptWin.jsp?rb_id='+rb_id+'','信息发送', 300,100,null,{rb_id : rb_id},true);

}
function statusshow(value,params,record,rowIndex,colIndex,ds){
	if(value=='1'){
		return '完成';
	}else{
		return '进行中';
	}
}


</script>


