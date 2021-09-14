<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	String ctxPath = request.getContextPath();
	String groupID=request.getParameter("groupID");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>数据审核</title>
<odin:head />
<script type="text/javascript">var ctx_path = "<%=ctxPath%>";</script>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/odin.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/templete/templete-default.css" />
<script type="text/javascript" src="<%=ctxPath%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/console.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/pageUtil.js"></script>
<style type="text/css">
.x-form-check-wrap{
	padding-top: 2px
}

#popDiv {
   display: none;
   width: 528px;
   height:60px;
   overflow-y:scroll;
   border: 1px #74c0f9 solid;
   background: #FFF;
   position: absolute;
   left: 108px;
   margin-top:25px;
   color: #323232;
   z-index:321;
}   

</style>
</head>
<body style="overflow: hidden;">
	
	<div id="container">
		<table id="container-table" border="0" cellspacing='0'>
			<tr>
				<td valign="top" style="position: relative;width: 330px" id="left-td">
					<div id="search-div" class="scrollbar" >
						 <table id="search-tbl" align="center" class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-right: 1px solid rgb(153,187,232);">
							<tr>
							  <td>
							  	<tags:PublicTextIconEdit3 width="80" codetype="orgTreeJsonData"  onchange="a0201bChange" label="机构选择" property="dept" defaultValue="" />
							  </td>
							   <odin:textarea property="seachName" rows="1" style="width:120px; overflow: auto;"></odin:textarea>
								<td><odin:button text="搜索" property="searchOnePerson" handler="searchPersonByName" ></odin:button></td> 
							</tr>
						</table> 
					</div>
					<div id="search-btn" align="center">
					
						<div id="">
						<odin:editgrid property="perGrid" title="人员信息" autoFill="false" width="280px" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
							<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="percheck" />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0192"   />
								<odin:gridDataCol name="shzt"   />
								 <odin:gridDataCol name="a0107" isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
							    <odin:gridRowNumColumn></odin:gridRowNumColumn>
							    <odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" dataIndex="percheck" edited="true" hideable="false" gridName="perGrid"/>
							    <odin:gridEditColumn header="人员主键"  dataIndex="a0000" align="center" editor="text" width="20" hidden="true" edited="false"/>
								<odin:gridEditColumn header="姓名" dataIndex="a0101" align="center" edited="false" editor="text" width="50" />
								<odin:gridEditColumn header="工作单位及职务" dataIndex="a0192" align="center" edited="false" editor="text" width="90"  />
								<odin:gridEditColumn header="审核状态" dataIndex="shzt" align="center" edited="false" editor="select"  width="80" renderer="shzt" />
								 <odin:gridEditColumn header="出生日期" dataIndex="a0107" align="center" edited="false" editor="text" width="60" isLast="true" /> 
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
						
					</div>
				</td>
				
				<td valign="top" id="right-td" style="padding-left: 10px"  >
					<div id="container-div" style="overflow: scroll" >
						<div id="famToolDiv"></div>	
						<div id="fileDiv"  style="overflow_x:hidden"   >
							<odin:editgrid property="MGrid2"  title="正式表" autoFill="false" width="75%" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" grouping="true" groupCol="updated" >
							<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="a3600" />
								<odin:gridDataCol name="a3604a" />
								<odin:gridDataCol name="a3601" />
								<odin:gridDataCol name="a3611" />
								<odin:gridDataCol name="a3607" />
								<odin:gridDataCol name="a3627" />
								<odin:gridDataCol name="a0184gz" />
								<odin:gridDataCol name="a0111gz" />
								<odin:gridDataCol name="a0115gz" />
								<odin:gridDataCol name="a0111gzb" />
								<odin:gridDataCol name="a3621" />
								<odin:gridDataCol name="a3631" />
								
								<odin:gridDataCol name="updated"></odin:gridDataCol>
								<odin:gridDataCol name="a3641" isLast="true" />		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
							    <odin:gridRowNumColumn></odin:gridRowNumColumn>
							    <odin:gridEditColumn header="主键"  dataIndex="a3600" align="center" editor="text" width="100" hidden="true" edited="false"/>
								<odin:gridEditColumn header="称谓" dataIndex="a3604a" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="姓名" dataIndex="a3601" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" align="center" edited="false" editor="text" width="160" />
								<odin:gridEditColumn header="出生年月" dataIndex="a3607" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="政治面貌" dataIndex="a3627" align="center" edited="false" editor="text" width="80" />
								<odin:gridEditColumn header="身份证号" dataIndex="a0184gz" align="center" edited="false"  editor="text" width="120"/>
								<odin:gridEditColumn header="籍贯" dataIndex="a0111gz" align="center" edited="false" editor="text" width="70" />
								<odin:gridEditColumn header="居住地" dataIndex="a0115gz" align="center" edited="false" editor="text" width="70"/>
								<odin:gridEditColumn header="国籍或地区" dataIndex="a0111gzb" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="民族" dataIndex="a3621" align="center" edited="false" editor="text" width="60"  />
								<odin:gridEditColumn header="人员身份" dataIndex="a3631" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="人员现状" dataIndex="a3641" align="center" edited="false" editor="text"  width="80" />
								<odin:gridEditColumn editor="select" dataIndex="updated" hidden="true" edited="false" codeType="A36_GROUP" header="分类" width="40" isLast="true" />
								
							</odin:gridColumnModel>
						</odin:editgrid>
						
						
						</div>     
						<div id="fam_xxDiv" >
							<odin:toolBar property="famBtnToolBar" applyTo="famToolDiv">
							<odin:textForToolBar text=""/>
							
							<odin:fill/>
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="审批" id="examine" handler="addexamine"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="批量审批" id="baexamine" handler="batchexamine"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="归档" id="file" handler="addfile"  icon="images/mylog.gif" />
							<odin:separator></odin:separator>
						    <odin:buttonForToolBar  text="批量归档"  id="batchfile" handler="addbatchfile"   icon="images/mylog.gif" isLast="true" />
						</odin:toolBar>

						</div>
						
						
						<!-- 主列表 -->	     
						<div id="girdDiv" style="width: 100%;margin:0px 0px 0px 0px; overflow_x:hidden " >    
						<odin:editgrid property="MGrid" title="审核表" autoFill="false" width="75%" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" grouping="true" groupCol="updated" >
							<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="a3600" />
								<odin:gridDataCol name="a3604a" />
								<odin:gridDataCol name="a3601" />
								<odin:gridDataCol name="a3611" />
								<odin:gridDataCol name="a3607" />
								<odin:gridDataCol name="a3627" />
								<odin:gridDataCol name="a0184gz" />
								<odin:gridDataCol name="a0111gz" />
								<odin:gridDataCol name="a0115gz" />
								<odin:gridDataCol name="a0111gzb" />
								<odin:gridDataCol name="a3621" />
								<odin:gridDataCol name="a3631" />
								<odin:gridDataCol name="a3645" />
								<odin:gridDataCol name="updated"></odin:gridDataCol>
								<odin:gridDataCol name="a3641" isLast="true" />		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
							    <odin:gridRowNumColumn></odin:gridRowNumColumn>
							    <odin:gridEditColumn header="主键"  dataIndex="a3600" align="center" editor="text" width="100" hidden="true" edited="false"/>
								<odin:gridEditColumn header="称谓" dataIndex="a3604a" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="姓名" dataIndex="a3601" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" align="center" edited="false" editor="text" width="160" />
								<odin:gridEditColumn header="出生年月" dataIndex="a3607" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="政治面貌" dataIndex="a3627" align="center" edited="false" editor="text" width="80" />
								<odin:gridEditColumn header="身份证号" dataIndex="a0184gz" align="center" edited="false"  editor="text" width="120"/>
								<odin:gridEditColumn header="籍贯" dataIndex="a0111gz" align="center" edited="false" editor="text" width="70" />
								<odin:gridEditColumn header="居住地" dataIndex="a0115gz" align="center" edited="false" editor="text" width="70"/>
								<odin:gridEditColumn header="国籍或地区" dataIndex="a0111gzb" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="民族" dataIndex="a3621" align="center" edited="false" editor="text" width="60"  />
								<odin:gridEditColumn header="人员身份" dataIndex="a3631" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="人员现状" dataIndex="a3641" align="center" edited="false" editor="text"  width="80" />
								<odin:gridEditColumn header="审核状态" dataIndex="a3645" align="center" edited="false" editor="select"  width="70" renderer="shzt" />
								<odin:gridEditColumn editor="select" dataIndex="updated" hidden="true" edited="false" codeType="A36_GROUP" header="分类" width="40" isLast="true"  />
								
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- 该JS文件需在页面元素末尾加载 -->
	
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	<odin:hidden property="a0000" title="人员主键"></odin:hidden>
	<odin:hidden property="a0000s" title="附件上传人员主键" />
	<odin:hidden property="a0101" title="人员姓名" />
	<odin:hidden property="a3600" title="家庭成员主键"/>
	<odin:hidden property="usertype" title="用户登录名" />
	<odin:hidden property="b0111" title="单位"/>
	<odin:hidden property="zippath"  title="导出路径"/>
	<odin:hidden property="jtcy" title="家庭成员" />
	<odin:hidden property="userid" title="用户id" />
	<odin:hidden property="sql" title="sql"/>
	
 <script type="text/javascript">  
 	//var maxHeight = window.parent.document.body.offsetHeight;
 
    Ext.onReady(function() {


      
		//自适应大小
		window.onresize=resizeframe;
		resizeframe();

		window.onresize=resizeframe1;
		resizeframe1();
		$("#seachName").keyup(function(){

			   if(event.keyCode == 13){

				   searchPersonByName();

			   }

			   });
    	
    });
   

    function a0201bChange() {
    	var dept=document.getElementById("dept").value;
    	if(dept=='')
    		{
    		$h.alert('系统提示','机构不能为空');
    		return;
    		}
    	radow.doEvent('queryByName');
    }
    

   

    function shzt(value, params, rs, rowIndex, colIndex, ds){
		if(value == '0')
			return "未审核";
		else 
			return "已审核";
	}
   

	
	
	


    
   

function resizeframe(){
	var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	var h=clientHeight/2
	
	var grid = Ext.getCmp('MGrid');
	var grid1=Ext.getCmp('MGrid2');
	var gbox=Ext.getCmp('famxx');
	var groupbox1=Ext.getCmp('perxx');
	grid.setHeight(h-16);
	grid.setWidth(clientWidth-document.getElementById("left-td").offsetWidth-10);
	grid1.setWidth(clientWidth-document.getElementById("left-td").offsetWidth-10);
	grid1.setHeight(h-6);
	//alert(clientWidth-document.getElementById("left-td").offsetWidth);
}

function resizeframe1(){
	var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	var grid = Ext.getCmp('perGrid');
	grid.setHeight(clientHeight-document.getElementById("search-div").offsetHeight);
	
} 


function UpBtn(){	
	var grid = odin.ext.getCmp('MGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		
		$h.alert('系统提示：','请选中需要排序的职务！',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		
		$h.alert('系统提示：','该职务已经排在最顶上！',null,180);
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}

function DownBtn(){	
	var grid = odin.ext.getCmp('MGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		
		$h.alert('系统提示：','请选中需要排序的职务！',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		
		$h.alert('系统提示：','该职务已经排在最底下！',null,180);
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}




//姓名查询
function searchPersonByName(){
	var dept=document.getElementById("dept").value;
	if(dept=='')
		{
		$h.alert('系统提示','机构不能为空');
		return;
		}
	radow.doEvent('queryByName');
}

//批量导入页面
function excelimp() {
	$h.showWindowWithSrc2('FamExcelImp', contextPath+ "/pages/FamilyMember/FamExcelImp.jsp", '导入窗口', 600, 200, null,
			{maximizable:false,resizable:false,draggable:false}, true); 
}

	
	
	
function addexamine(){
	var a0101=$("#a0101").val();

	if(a0101!=''&&a0101!=null){
		$h.confirm('系统提示', '审批前请先确定'+a0101+'的审批表是否有数据,否则审批会失败',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent('addexamine');
			} else {
				return;
			}
		});

		}else{
			alert("审批出错!!!")	
			
		}
	
	
}	

//批量审批
function batchexamine(){
	$h.confirm('系统提示', '批量审批前请先确定的审批表是否有数据,否则审批会失败',null, function(btn) {
		if ('ok' == btn) {
			radow.doEvent('beatchexamine');
		} else {
			return;
		}
	});
}
//归档
function addfile(){
	var a0101=$("#a0101").val();

	if(a0101!=''&&a0101!=null){
		$h.confirm('系统提示', '是否对'+a0101+'的正式表进行归档',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent('addfile');
			} else {
				return;
			}
		});

		}else{
			alert("归档出错!!!")	
			
		}
}
//批量归档
function addbatchfile(){
	$h.confirm('系统提示', '是否对正式表进行批量归档',null, function(btn) {
		if ('ok' == btn) {
			radow.doEvent('addbatchfile');
		} else {
			return;
		}
	});

}
//刷新Grid
function freshGrid(gridName){
	 var gridnames = gridName.split(",");
	 for(var i = 0; i<gridnames.length ; i++){
	     var grid = Ext.getCmp(gridnames[i]);
		 grid.store.reload();
	 }

}

</script>
</body>
</html>
