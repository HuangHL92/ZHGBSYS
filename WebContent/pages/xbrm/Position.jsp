<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
function xzgw(){
	radow.doEvent('xzgw');
}

var g_contextpath = '<%= request.getContextPath() %>';

</script>

<odin:toolBar property="toolbar1" applyTo="tab1_topbar1">
<odin:textForToolBar text="<h3>职位情况</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<%-- <odin:buttonForToolBar text="选择职位" id="xzgw" handler="xzgw" icon="image/icon021a4.gif"></odin:buttonForToolBar>
<odin:separator/> --%>
<odin:buttonForToolBar text="导出职位情况" id="dcgwxq" handler="expExcelFromGrid1" icon="images/tb_2.jpg"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<div id="groupTreeContent" style="display:none;height: 100%; padding-top: 0px;">
	<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
		<tr>
			<td width="370" id="td1" >
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
				<div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2  no-repeat center center;"></div>
			</td>
			<td>
				<div style="width:100%;height: 100%;float: left;border:0px;margin: 0px;padding: 0px; ">
					<table id="tab1_table" style="width: 100%;height: 100%;border: 0px;" cellspacing='0'>
						<tr>
							<td colspan="5">
								<div id="tab1_topbar1"></div>
							</td>
						</tr>

						<tr>
							<td colspan="5">
								<odin:editgrid property="jggwInfoGrid" url="/"  pageSize="100" bbarId="pageToolBar" isFirstLoadData="false" title="待选职位"  autoFill="false">
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck" />
										<odin:gridDataCol name="b0111" />
										<odin:gridDataCol name="b0101" />
										<odin:gridDataCol name="b0114" />
										<odin:gridDataCol name="gwname" />
										<odin:gridDataCol name="gwnum" />
										<odin:gridDataCol name="countnum" />
										<odin:gridDataCol name="cz1" />
										<odin:gridDataCol name="cz2" />
										<odin:gridDataCol name="gwcode" />
										<odin:gridDataCol name="b0194" />
										<odin:gridDataCol name="b0104" />
										<odin:gridDataCol name="jggwconfid" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 header="selectall" width="50"  editor="checkbox" dataIndex="personcheck" edited="true" />
										<odin:gridEditColumn dataIndex="b0111" width="100" header="机构主键" edited="false" editor="text" hidden="true" />
										<odin:gridEditColumn2 dataIndex="b0101" width="230" header="机构名称" editor="text" edited="false" />
										<%-- <odin:gridEditColumn2 dataIndex="b0114" width="150" header="机构编码" editor="text" edited="false" /> --%>
										<odin:gridEditColumn2 dataIndex="gwname" width="180" header="职务名称" editor="text" edited="false" />
										<odin:gridEditColumn2 dataIndex="gwnum" align="center" width="120" header="职务额配" editor="text" edited="false"/>
										<odin:gridEditColumn2 dataIndex="sp" width="120" align="center" renderer="gwsprenderer" header="实配情况" editor="text" edited="false" />
										<odin:gridEditColumn2 dataIndex="cz1" width="120" align="center" renderer="gwqprenderer" header="缺配情况" editor="text" edited="false" />
										<odin:gridEditColumn2 dataIndex="cz2" width="120" align="center" renderer="gwcprenderer" header="超配情况" editor="text" edited="false" />
										<odin:gridEditColumn2 dataIndex="gwcode" width="40" header="职位" editor="text" hidden="true" edited="false" />
										<odin:gridEditColumn2 dataIndex="b0194" width="40" header="单位类型" editor="text" hidden="true" edited="false"/>
										<odin:gridEditColumn2 dataIndex="b0104" width="40" header="单位简称" editor="text" hidden="true" edited="false"/>
										<odin:gridEditColumn2 dataIndex="jggwconfid" width="40" header="职位id" editor="text" hidden="true" edited="false" isLast="true"/>
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
	var viewSize = Ext.getBody().getViewSize();
	//Ext.get('commForm').setWidth(viewSize.width);
	
	var tableTab1 = document.getElementById("tableTab1"); 
	tableTab1.style.height = viewSize.height+100+"px";//87 82
	
	//divresize
	var divresize = document.getElementById("divresize"); 
	divresize.style.height = viewSize.height+"px";//87 82
	//tab
	var tab = document.getElementById("tab"); 
	tab.style.height = viewSize.height+"px";//87 82
	
	var jggwInfoGrid = Ext.getCmp('jggwInfoGrid');
	jggwInfoGrid.setHeight(viewSize.height-82);
	jggwInfoGrid.setWidth(viewSize.width-380);
	
	$("#groupTreeContent").show();
	/* var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		document.getElementById('rb_name').value = rc.data.rb_name;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){  
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		document.getElementById('rb_no').value = rc.data.rb_no;
		$h.openPageModeWin('qcjs','pages.xbrm.QCJS','干部选拨任用',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
		radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //关闭  
	}); */
	
});

function queryBtn(){
	radow.doEvent('jggwInfoGrid.dogridquery');
}

function a0201bChange(record){
}

function expExcelFromGrid1(){
	var excelName = null;
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('jggwInfoGrid');
	var dstore = pgrid.getStore();
	excelName = "职位情况信息" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	odin.grid.menu.expExcelFromGrid('jggwInfoGrid', excelName, null,null, false);
}

function gwsprenderer(value, meta, record, rowIndex, colIndex, ds, view){
	//alert(record.get("gwnum"));
	//alert(record.get("cz1"));
	var sp = record.get("gwnum") - record.get("cz1");
	var gwcode = record.get("gwcode");
	//alert(gwcode);
	//return '<font style="color: black;">'+Math.abs(sp)+'</font>';
	return "<a href=\"javascript:spfc('" + gwcode + "')\"><font style='color: black;'>"+Math.abs(sp)+"</font></a>&nbsp;"
}

function spfc(gwcode){
	//alert(gwcode);
	var ereaid = document.getElementById('ereaid').value;
	//$h.openWin('YearCheck','pages.sysorg.org.YearCheck','年度考核页面',760,460,'"+groupid+"','"+ctxPath+"');"
	//radow.doEvent("spfc",value);
	$h.openPageModeWin('Spfc','pages.xbrm.Spfc','实配反查',580,300,{gwcode:gwcode,ereaid:ereaid},g_contextpath);
}

function gwqprenderer(value, meta, record, rowIndex, colIndex, ds, view){
	if(value>0){
		meta.css = "x-grid-record-green";
		meta.style = 'background: #C0C0C0'; 
		return '<font style="color: green;">'+Math.abs(value)+'</font>';
	} else {
		//return '';
		return '<font style="color: green;">'+Math.abs(0)+'</font>';
	}
}
function gwcprenderer(value, meta, record, rowIndex, colIndex, ds){
	if(value>0){
		meta.css = "x-grid-record-red";
		return '<font style="color: red;">'+Math.abs(value)+'</font>';
	} else{
		//return '';
		return '<font style="color: red;">'+Math.abs(0)+'</font>';
	}
	
}

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
	tree.expandPath(root.getPath(),null,function(){addnode();});
	tree.expandPath(root.getPath(),null,function(){addnodebm();});   //无任职部门
	root.expand(false,true, callback);
	
}); 

function tree_event(node,event){
	//alert("1");
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


