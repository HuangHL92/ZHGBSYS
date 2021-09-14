<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.TemplateConfPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>

<style type="text/css">
.font-tab {
	font-family: "黑体";
	font-size: 16px;
}
.x-tree-node {
	font-size: 12px !important;
}
.inputtable{border-collapse:collapse;}
.inputtable th{border-collapse:collapse;background:#EFEFEF;width:100px;border:1px dotted #CCCCCC;
	               font-weight:bold;text-align:center;padding:3px;}
.inputtable tr td{border-collapse:collapse;border:1px dotted #CCCCCC;width:100px;padding:3px;}
</style>

<script>  
var contextPath='<%=request.getContextPath()%>';

var ht=document.body.clientHeight-5;

//var subWinId = 'null';
    Ext.onReady(function(){
    	var man = document.getElementById('manager').value;
        var Tree = Ext.tree;
        tree = new Tree.TreePanel( {
   	  	id:'group',
         el : 'div_1',//目标div容器
         split:true,
         height:ht,
         width: 238,
         minSize: 164,
         maxSize: 164,
         rootVisible: false,//是否显示最上级节点
         autoScroll : true,
         animate : true,
         border:false,
         enableDD : false,
         containerScroll : true,
         loader : new Tree.TreeLoader( {
               dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
         })
     });
        tree.on('click',treeClick); //tree点击事件
		var root = new Ext.tree.AsyncTreeNode({
			/* checked : true,
			text : "其他单位",
			iconCls : "picOrg",
			draggable : true,
			id : "-1" */
			checked : false,//当前节点的选择状态
			text : document.getElementById('ereaname').value,//节点上的文本信息
			iconCls : document.getElementById('picType').value,//应用到节点图标上的样式
			draggable : false,//是否允许拖曳
			id : document.getElementById('ereaid').value,//默认的node值：?node=-100
			href : "javascript:radow.doEvent('querybyid','"//节点的连接属性
					+ document.getElementById('ereaid').value + "')"
		});
		tree.setRootNode(root);
		tree.render();
		root.expand(false,true,callback);
		var callback = function (node){
			if(node.hasChildNodes()) {
				node.eachChild(function(child){
					child.expand();
				})
			}
		}
		/* tree.setRootNode(root);//设置根结点
		//tree.addListener('click', BiaoZhunClick);
		tree.render();//显示 */
		root.expand(false,true, callback);//默认展开
    	
    }); 
    function treeClick(node,e){
    	//console.log(node);//companyOrgImg2 法人单位
    	if(node.attributes.icon.indexOf("companyOrgImg2") == -1){
    		odin.alert("请选择法人单位！");
    		return;
    	}
    	 var gid = node.id;
    	 //alert(gid);
    	 //document.getElementById("gid").value=gid;
    	 $('#gid').val(gid);
    	 Ext.getCmp("tab0").show();//点击机构数默认显示第一个tab页

    	radow.doEvent('grid1.dogridquery');

     	document.getElementById("typeid").value="";
     	
     	radow.doEvent('showjgtz',gid);
     	
    	document.getElementById("MainBody").style.visibility="visible";//显
    	//document.getElementById("btnToolBarDiv").style.display="";
    }
    
    
    $(function(){
    	document.getElementById("MainBody").style.visibility="hidden";//隐藏
    	//document.getElementById("btnToolBarDiv").style.display = "none";
    	$('#Sidebar').height(ht);
    	$('#MainBody').height(ht);
    	$('#div_1').height(ht);
    	
    	$('#tt').height(ht);
    	var wh=document.body.clientWidth*0.8;
    	$('#tt').width(wh);
    	
    }); 
    
    function countrenderer(value, params, rs, rowIndex, colIndex, ds){
    	value = value==null|| value==''?'0' :value;
    	if(parseInt(value)>parseInt(rs.get('gwnum'))){
    		params.css='cellyellow';
    		return "<span style='color:red;font-weight:bold;'>"+value+"</span>";
    	} else {
    		return value;
    	}
    }
   
    function oneticket(value, params, record,rowIndex,colIndex,ds){
        return value == '0'? "是" : "否";
    }
    
 function refresh(){//刷新gird职务职树
	 radow.doEvent('TrainingInfoGrid.dogridquery');
 }
 function refresh4(){//刷新gird年龄
	 radow.doEvent('TrainingInfoGrid4.dogridquery');
 }
 function refresh2(){//刷新gird年龄
	 radow.doEvent('TrainingInfoGrid2.dogridquery');
 }
 //refresh2
  function rowDbClick4(grid,rowIndex,event){//双击修改年龄
	  var gid=document.getElementById("gid").value;
	  var record = grid.getStore().getAt(rowIndex);   //获取当前行的数据
		//alert(record.data.formula);
		var order_number=record.data.order_number;
		var project1   =record.data.project;
		var project = encodeURI(encodeURI(project1,'utf-8'),'utf-8');
		var quan=record.data.quantity;
		var quantity1 = quan.substring(0,2);
		var quantity = quan.substring(2);
		var one_ticket_veto =record.data.one_ticket_veto ;
		var category =record.data.category ;
		//var remark    =record.data.remark;
		var id=record.data.id;
		var params = order_number+","+project+","+quantity1+","+quantity+","+one_ticket_veto+","+id;
	  $h.openWin('znzw','pages.templateconf.TyGrid&initParams='+gid+','+category,'编辑结构设定', 450, 270,params, contextPath);
  }
  
  function update4(){
	  var gid=document.getElementById("gid").value;
	  var row = Ext.getCmp("TrainingInfoGrid4").getSelectionModel().getSelections();
	  if(row.length == 0) {  
		    Ext.Msg.alert("提示信息", "您没有选中行!");  
		    return;
	  } 
	  if(row.length > 1) {  
		    Ext.Msg.alert("提示信息", "请您选中一条信息修改!");  
		    return;
	  } 
	  
	  var order_number = row[0].data.order_number;
	  var project = row[0].data.project;
	  var quan = row[0].data.quantity;
	  var quantity1 = quan.substring(0,2);
	  var quantity = quan.substring(2);
	  var one_ticket_veto = row[0].data.one_ticket_veto;
	  var category = row[0].data.category;
	  var id = row[0].data.id;
	  
	  var params = order_number+","+project+","+quantity1+","+quantity+","+one_ticket_veto+","+id;
	  $h.openWin('znzw','pages.templateconf.TyGrid&initParams='+gid+','+category,'编辑结构设定', 450, 270,params, contextPath);
	  
  }
  
 function remove4(){//删除年龄
	  var grid = odin.ext.getCmp('TrainingInfoGrid4');
		 var store = grid.store;
		 var i = store.getCount()-1;
		 var count=0;
		 var ids="";	
		 if (store.getCount() > 0) {
				for (var i = store.getCount() - 1; i >= 0; i--) {
					var ck = grid.getStore().getAt(i).get("logchecked");
					if (ck == true) {
						count++;
						ids += grid.getStore().getAt(i).get("id") + ",";
					}
				}
			}
		 
		if(count ==0){
			 Ext.Msg.alert("提示信息", "您没有选中行!");  
			 return;
		}
		
		ids=ids.substring(0,ids.length-1);
		radow.doEvent('delete4',ids);
  }
    
    function grantTabChange(tabObj,item){//切换
    	var tab = item.getId();
    	//alert(tab);
    	if (tab=='tab1'){
	    	document.getElementById("type").value='banzi';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if (tab=='tab9'){
	    	document.getElementById("type").value='xb';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if (tab=='tab10'){
	    	document.getElementById("type").value='dp';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if (tab=='tab11'){
	    	document.getElementById("type").value='mz';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if(tab=='tab4'){//年龄
        	 document.getElementById("type").value='nl';
        	 radow.doEvent('TrainingInfoGrid4.dogridquery');
        	 item.add(Ext.getCmp("btnToolBar"));  
        	item.add(Ext.getCmp("TrainingInfoGrid4"));  
        	item.doLayout();   
         } else if (tab=='tab5'){//学历
        	 document.getElementById("type").value='xl';
        	 radow.doEvent('TrainingInfoGrid4.dogridquery');
        	 item.add(Ext.getCmp("btnToolBar"));  
        	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
        	 item.doLayout();   
	    } else if (tab=='tab6'){
	    	 document.getElementById("type").value='zy';
        	 radow.doEvent('TrainingInfoGrid4.dogridquery');
        	 item.add(Ext.getCmp("btnToolBar"));  
        	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
        	 item.doLayout();   
	    }else if (tab=='tab8'){
	    	document.getElementById("type").value='dy';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if (tab=='tab7'){
	    	document.getElementById("type").value='sxly';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }else if (tab=='tab14'){
	    	document.getElementById("type").value='jl';
	       	 radow.doEvent('TrainingInfoGrid4.dogridquery');
	       	 item.add(Ext.getCmp("btnToolBar"));  
	       	 item.add(Ext.getCmp("TrainingInfoGrid4"));  
	       	 item.doLayout();   
	    }
    	
    }

    function comprevalue17(parem){//回显结构调整
    	if(parem=="null"){
    		document.getElementById("bzf").value='10';
         	document.getElementById("xbf").value='10';
         	document.getElementById("dpf").value='10';
         	document.getElementById("mzf").value='10';
         	document.getElementById("nlf").value='10';
         	document.getElementById("xlf").value='10';
         	document.getElementById("zyf").value='10';
         	document.getElementById("dyf").value='10';
         	document.getElementById("knowf").value='10';
         	document.getElementById("jlf").value='10';
         	document.getElementById("remarks").value='';
         	document.getElementById("redf").value='60';
         	document.getElementById("greenf").value='80';
    	}else{
    		var paremarr = parem.split(",");
            
         	document.getElementById("bzf").value=paremarr[0];
         	document.getElementById("xbf").value=paremarr[1];
         	document.getElementById("dpf").value=paremarr[2];
         	document.getElementById("mzf").value=paremarr[3];
         	document.getElementById("nlf").value=paremarr[4];
         	document.getElementById("xlf").value=paremarr[5];
         	document.getElementById("zyf").value=paremarr[6];
         	document.getElementById("dyf").value=paremarr[7];
         	document.getElementById("knowf").value=paremarr[8];
         	document.getElementById("jlf").value=paremarr[9];
         	document.getElementById("remarks").value=paremarr[10];
         	document.getElementById("redf").value=paremarr[11];
         	document.getElementById("greenf").value=paremarr[12];
    	}
     	
     	
     }
    
    function loadadd1(){//新增职能职树
    	var param ="";
   // alert(param);
   		var gid=document.getElementById("gid").value;
    	$h.openWin('znzw','pages.templateconf.ZnzsView&initParams='+gid,'编辑结构设定', 450, 270,param, contextPath);
    }
    
     function tygrid4(){
    	 var type = document.getElementById("type").value;
    	 var gid=document.getElementById("gid").value+","+type;//年龄
    	  var param="";
     	$h.openWin('nl','pages.templateconf.TyGrid&initParams='+gid,'编辑结构设定', 450, 270,param, contextPath); 
    } 
      
      function LegalEntitySaveBtn(){
    	 var sign = true;
    	 var gid = document.getElementById("gid").value;//机构ID
    	  
   	  	 var bzf = document.getElementById("bzf").value; 
   		 var xbf = document.getElementById("xbf").value;
   		 var dpf = document.getElementById("dpf").value;
   		 var mzf = document.getElementById("mzf").value;
   		 var nlf = document.getElementById("nlf").value;
   		 var xlf = document.getElementById("xlf").value;
   		 var zyf = document.getElementById("zyf").value;
   		 var dyf = document.getElementById("dyf").value;
   		 var knowf = document.getElementById("knowf").value;
   		 var jlf = document.getElementById("jlf").value;
   		 sign=validate(bzf,sign);sign=validate(xbf,sign);sign=validate(dpf,sign);sign=validate(mzf,sign);sign=validate(nlf,sign);
   		 sign=validate(xlf,sign);sign=validate(zyf,sign);sign=validate(dyf,sign);sign=validate(dyf,sign);sign=validate(jlf,sign);
   		 if(!sign){
   			odin.alert("请输入0~100正整数值!");  
   			return;
   		 }
	
  		 var remarks = document.getElementById("remarks").value;

  		 var redf = document.getElementById("redf").value;
  		 var greenf = document.getElementById("greenf").value;
  		 if(!redf){
  			odin.alert("请输入判'红'分值'!");  
      	  	return;
  		 }
  		 if(!greenf){
  			odin.alert("请输入判'绿'分值'!");  
      	  	return;
  		 }
  		 sign=validate(redf,sign);sign=validate(greenf,sign);
   		 /*  if(jgtypeid==null || jgtypeid=='undefined' || jgtypeid==''){
   			 jgtypeid='hhhh';
   	 	 } */
   	 	
   	 	 var jgtz = bzf+","+xbf+","+dpf+","+mzf+","+nlf+","+xlf+","+zyf+","+dyf+","+knowf+","+jlf
   	 	 +","+remarks+","+redf+","+greenf+","+gid;
   	 	 
   	 	 radow.doEvent('LegalEntitySaveBtn',jgtz);
      }
      
      function validate(val,sign){  
          var reg = new RegExp("^[0-9]*$");
       	  if(!reg.test(val)){
       		  sign = false;
          }  
          if(!/^[0-9]*$/.test(val)){   
        	  sign = false;
          }  
          return sign;
      }  
      
</script>  
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	%>
	<style type="text/css">
	/* #PageBody{width:101%;} */
	#Sidebar{border-right:5px solid #c3daf9;}
	/* #MainBody{float:right;width:82%;margin-top:-20px;} */
	
	
	
	
</style> 

</head>
<body style="overflow: hidden;">

<odin:hidden property="gid"/>
<odin:hidden property="type"/>
<input type="hidden" name="typeid" id="typeid" value=""/>
<input type="hidden" name="jgtypeid" id="jgtypeid" value=""/>
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />


<table style="width: 100%;">
	<tr>
		<td width="15%" valign="top">
			<div id="Sidebar"> <!--侧边栏-->
    		<div id="div_1" style="overflow: auto; height: 100%; width: 100%;margin-top: 0px">
    		</div>
　　　　		 </div>
		</td>
		<td width="85%" valign="top">
			<div id="MainBody" style="overflow-x:scroll"> <!--主体内容-->
		  <odin:tab id="tab"  height="document.body.clientHeight" tabchange="grantTabChange">
		   <odin:tabModel>
		   	<odin:tabItem title="&nbsp职位概况&nbsp" id="tab0"></odin:tabItem>
		    <odin:tabItem title="&nbsp领导班子人数&nbsp" id="tab1"></odin:tabItem>
		    <odin:tabItem title="&nbsp性&nbsp&nbsp别&nbsp" id="tab9"></odin:tabItem>
		    <odin:tabItem title="&nbsp党&nbsp&nbsp派&nbsp" id="tab10"></odin:tabItem>
		    <odin:tabItem title="&nbsp民&nbsp&nbsp族&nbsp" id="tab11"></odin:tabItem>
		    
		    <odin:tabItem title="&nbsp年&nbsp&nbsp龄&nbsp" id="tab4"></odin:tabItem>
		    
		    <odin:tabItem title="&nbsp学&nbsp&nbsp历&nbsp"  id="tab5"></odin:tabItem>
		    <odin:tabItem title="&nbsp专&nbsp&nbsp业&nbsp" id="tab6"></odin:tabItem>
		    <odin:tabItem title="&nbsp地&nbsp&nbsp域&nbsp" id="tab8"></odin:tabItem>
		    <odin:tabItem title="&nbsp熟悉领域&nbsp" id="tab7"></odin:tabItem>
		    <odin:tabItem title="&nbsp经&nbsp&nbsp历&nbsp" id="tab14"></odin:tabItem>
			<odin:tabItem title="&nbsp得分设置&nbsp" id="tab17" isLast="true"></odin:tabItem>
		   </odin:tabModel>
		   
		   <odin:tabCont itemIndex="tab0">
				<odin:editgrid property="grid1" autoFill="true" pageSize="100" height="document.body.clientHeight-55" 
					topBarId="ToolBar" bbarId="pageToolBar" url="/">
					<odin:gridJsonDataModel id="id" root="data">
							<odin:gridDataCol name="b0111" />					
							<odin:gridDataCol name="gwcode" />
							<odin:gridDataCol name="gwnum" />
							<odin:gridDataCol name="countnum" />
							<odin:gridDataCol name="zjcode" />
							<odin:gridDataCol name="zwcode" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="机构ID" align="center" edited="false" width="100" dataIndex="b0111" editor="text" hidden="true"/>
							<odin:gridEditColumn2 header="职位名称" align="center" edited="false" width="250" dataIndex="gwcode" editor="select" codeType="GWGLLB"/>
							<odin:gridEditColumn header="职位数量" align="center" edited="false" width="250" dataIndex="gwnum" editor="text" />
							<odin:gridEditColumn header="实有数量" align="center" renderer="countrenderer" edited="false" width="250" dataIndex="countnum" editor="text" />
							<odin:gridEditColumn2 header="对应职务层次" align="center" edited="false" width="250" dataIndex="zwcode" editor="select" isLast="true" codeType="ZB09"/>
					 </odin:gridColumnModel>
				</odin:editgrid>
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab1">
		   </odin:tabCont>
		   
		    <odin:tabCont itemIndex="tab9">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab10">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab11">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab4">
		   <div id="btnToolBarDiv4"></div>
		  <odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv4">
		   <odin:buttonForToolBar text="&nbsp;增加" icon="image/icon021a2.gif" id="loadadd1"  handler="tygrid4"/>
		   <odin:separator></odin:separator>
		   <odin:buttonForToolBar text="&nbsp;修改" icon="image/icon021a6.gif"  id="rmbUpdate" handler="update4"/>
		   <odin:separator></odin:separator>
		   <odin:buttonForToolBar text="&nbsp;删除" icon="image/icon021a3.gif" id="deletePersonBtn" isLast="true"  handler="remove4"/>
		   </odin:toolBar>
		    <odin:grid property="TrainingInfoGrid4" height="document.body.clientHeight-90" title="" autoFill="true" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="20"  url="/" rowDbClick="rowDbClick4">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="logchecked" />
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="order_number" />
 			  		<odin:gridDataCol name="project_cn"  /> 
 			  		<odin:gridDataCol name="project"  /> 
 			  		<odin:gridDataCol name="quantity"  /> 
 			  		<odin:gridDataCol name="category"  /> 
			  		<odin:gridDataCol name="one_ticket_veto" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				    <odin:gridColumn dataIndex="logchecked" header="selectall" gridName="TrainingInfoGrid4" edited="true" width="50" editor="checkbox"></odin:gridColumn>
				  	<odin:gridEditColumn2 header="主键" dataIndex="id" editor="text" edited="false" width="100" hidden="true"/>
				  	<odin:gridEditColumn2 header="序号" align="center" dataIndex="order_number" editor="text" edited="false"  width="100"/>
				  	<odin:gridEditColumn2 header="项目" align="center" dataIndex="project_cn" editor="text" edited="false"  width="200" /> 
				  	<odin:gridEditColumn2 header="项目" align="center" dataIndex="project" editor="text" edited="false" hidden="true" width="100" /> 
 				  	<odin:gridEditColumn2 header="数量" align="center" dataIndex="quantity" editor="text" edited="false" width="200"/> 
 				  	<odin:gridEditColumn2 header="自然结构类型" align="center" dataIndex="category" hidden="true" editor="text" edited="false" width="100"/> 
				  	<odin:gridEditColumn2 width="200" align="center" header="一票否决" dataIndex="one_ticket_veto" editor="text" edited="false" renderer="oneticket"  isLast="true"/>
					</odin:gridColumnModel>
			</odin:grid>
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab5">
	
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab6">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab8">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab7">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab14">
		   </odin:tabCont>
		   
		   <odin:tabCont itemIndex="tab17">
		   <div id="tt" style="overflow:auto; margin-top:20px; text-align: center;">
		   <table class="inputtable"  style="width:600px;text-align:center;margin:0 auto;border-bottom:0px;" >
		     
		     <tr style="width: 100%;height: 25px;">
		       <td height="50px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"><font class="font-tab" style="font-weight: bolder;">结构类型</font></td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"><font class="font-tab" style="font-weight: bolder;">分值（总分100），单位：分</font></td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"><font class="font-tab" style="font-weight: bolder;">结构类型</font></td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"><font class="font-tab" style="font-weight: bolder;">分值（总分100），单位：分</font></td>
		      </tr>
		      <tr style="width: 100%;height: 25px;">
		       <td height="40px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"><font class="font-tab">领导班子人数</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="bzf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">学历结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="xlf" type="text" value="" style="width: 60px"/>
		       </td>
		      </tr>
		      
		      <tr style="width: 100%;height: 25px;">
		       <td height="40px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">性别结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="xbf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">专业结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="zyf" type="text" value="" style="width: 60px"/>
		       </td>
		      </tr>
		      
		      <tr style="width: 100%;height: 25px;">
		       <td height="40px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">党派结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="dpf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">地域结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="dyf" type="text" value="" style="width: 60px"/>
		       </td>
		      </tr>
		      
		      <tr style="width: 100%;height: 25px;">
		       <td height="40px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">民族结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="mzf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">熟悉领域</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="knowf" type="text" value="" style="width: 60px"/>
		       </td>
		      </tr>
		      
		      <tr style="width: 100%;height: 25px;">
		       <td height="40px" style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">年龄结构</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="nlf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 25%;text-align: center;background-color: rgb(239,239,239)"> <font class="font-tab">经历</font></td>
		       <td style="width: 25%;text-align: center;"> 
					<input id="jlf" type="text" value="" style="width: 60px"/>
		       </td>
		      </tr>
		      
		      <tr style="width: 100%;height: 50px;display: none">
		       <td> <font class="font-tab">备注</font></td>
		       <td> 
		       <textarea name="remarks" id="remarks" style="width:100%" rows="4" ></textarea>
		       </td>
		      </tr>
		   </table>
		   
		   <table class="inputtable"  style="width:900px;text-align:center;margin:0 auto;border-bottom:0px;margin-top: 30px" >
		    <tr style="width: 100%;">
		       <td colspan="6" height="50px" style="width: 16.6%;background-color: rgb(239,239,239)"> <font class="font-tab" style="font-weight: bolder;">评分设置</font></td>
		    </tr>
		    <tr>
		       <td style="width: 16.6%;background-color: rgb(239,239,239)"  height="40px" > <font class="font-tab" style="color: red">红</font></td>
		       <td style="width: 16.6%"> 
					<font class="font-tab">&nbsp;&nbsp;小于</font>
					<input id="redf" type="text" value="" style="width: 60px"/>
		       </td>
		       <td style="width: 16.6%;background-color: rgb(239,239,239)"> <font class="font-tab" style="color: yellow">黄</font></td>
		       <td style="width: 16.6%"> 
					<font class="font-tab"></font><input id="" type="text" value="60" style="width: 60px;display: none"/>
		       </td>
		       <td style="width: 16.6%;background-color: rgb(239,239,239)"> <font class="font-tab" style="color: green">绿</font></td>
		       <td style="width: 16.6%"> 
					<font class="font-tab">大于等于</font><input id="greenf" type="text" value="" style="width: 60px"/>
		       </td>
		    </tr>
		   </table>
		   
		   <table   style="width:629px;text-align:center;margin:0 auto;" >
		   	<tr >
		      <td style="height: 20px;"></td>
		    </tr>
		    <tr >
		      <td  ><input class="yellowbutton" type="button" value="保&nbsp;存" onclick="LegalEntitySaveBtn()"/></td>
		    </tr>
		   </table>
		   </div>
		   </odin:tabCont>
		   
		   </odin:tab>
		
　　　　 </div>
		</td>
	</tr>
</table>

　　　　  
　　　　 
</body>
</html>