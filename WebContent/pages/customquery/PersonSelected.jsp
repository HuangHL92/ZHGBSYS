<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<% 		
//String type=(String)request.getParameter("PersonType");
String LWflag=(String)request.getParameter("LWflag");	
 %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<!-- 机构树初始化 -->
<script type="text/javascript">
Ext.onReady(function(){
	var sign;
	var LWflag=$("#LWflag").val();
	
	if(LWflag=="1"){
		sign="look"
	}else{
		sign="write";
	}
	
   var Tree = Ext.tree;
   var tree = new Tree.TreePanel( {
	  	  id:'group',
	        el : 'tree-div',//目标div容器
	        split:false,
	        width: 270,
	        minSize: 164,
	        maxSize: 164,
	        rootVisible: false,//是否显示最上级节点
	        autoScroll : true,
	        animate : true,
	        border:false,
	        enableDD : false,
	        containerScroll : true,
	        loader : new Tree.TreeLoader( {
	              dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1',
	              baseParams : {sign: sign}
	        }),
	        listeners: {  
	            'dblclick':function(node,e){  
	               //增加tabpanel  
	               document.getElementById("b0111").value = node.id ;
	               radow.doEvent('peopleInfoGrid.dogridquery')
	            }  
	          }  
	    });
	var root = new Tree.AsyncTreeNode({
		checked : false,	
		text : 1,
		iconCls : 1,
		draggable : false,
		id : -1,//默认的node值：?node=-100
		href : "javascript:radow.doEvent('querybyid','" +1+ "')"
		
	});
	tree.setRootNode(root);
	//tree.on('click', test);
	
	
	tree.render();
})
</script>
	<style>
		.float_left1{
			float: left;
			height: 20px;
		}
	</style>
</head>
<body>
<style>
	.contentDiv{
		float: left;
		height: 380px;
		width: 506px;
	}
	.bluebutton {
		font-size:14px ;
		color: white;
		width: 60px;
		height: 25px;
		border-top: 0;
		text-align: center;
		line-height: 23px;
		border-left: 0;
		background-color: #3680C9;
	}
</style>
<table style="  border: 2px solid #c3daf9;">
   <tr>
   <td >
   <div id = "tree-div"  style=" width: 300px; height: 390px ; border: 2px solid #c3daf9 ;overflow-y:scroll;"></div>
    </td>
    <td colspan="1">
		<div class="contentDiv">
			<div class="float_left1">
				<table>
					<tbody>
					<tr>
						<odin:select2 property="stype" label="<span style='margin-left:2%'>查询条件：</span>" canOutSelectList="false" editor="false"  
									  data="['name','姓名'],['idCard','身份证号']"
									  width="100"></odin:select2>
						<odin:textEdit property="colsm" width="200"></odin:textEdit>
						<td  style="    text-align: center; width: 200px;">
							<button class="bluebutton" type="button" onclick="serach()">搜索</button>
						</td>
					</tr>
					</tbody>
				</table>

			</div>
			<div id = "Grid" style=" width: 500px; height: 350px ; border: 2px solid #c3daf9;float: left;">

				<odin:editgrid property="peopleInfoGrid" title="人员列表"  width="300" height="350" pageSize="10"
							   autoFill="false"  bbarId="pageToolBar">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0184" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0163" />
						<odin:gridDataCol name="a0192a" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
						<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
						<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
						<odin:gridColumn dataIndex="a0184" edited="false" header="身份证号" width="150"  align="center" />
						<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="250"  align="center" />
						<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
						data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid>

			</div>
		</div>
    </td>
   </tr>
</table>
<input type="hidden"  id= "b0111"  name = "b0111">

<odin:hidden property="PersonType" value="<%=LWflag%>"/>
<!-- 重写ext tree 的双击事件，并不触发展开  -->
<script type="text/javascript">

Ext.override(Ext.tree.TreeNodeUI, {
	onDblClick : function(e) {
		e.preventDefault();
		if (this.disabled) {
			return;
		}
		if (this.checkbox) {
			this.toggleCheck();
		}
		if (!this.animating && this.node.hasChildNodes()) {
			var isExpand = this.node.ownerTree.doubleClickExpand;
			if (isExpand) {
				this.node.toggle();
			}
			;
		}
		this.fireEvent("dblclick", this.node, e);
	}
});
Ext.onReady(function () {
	
    $("#stype").val("name");
    $("#stype_combo").val("姓名");
});

function serach(){
    // if(document.getElementById('stype').value==""||document.getElementById('colsm').value==null){
    //     $h.alert('系统提示','请填写姓名或身份证号');
    //     return;checkedgroupid
    // }
    radow.doEvent("peopleInfoGrid.dogridquery");
}

</script>
</body>

</html>

