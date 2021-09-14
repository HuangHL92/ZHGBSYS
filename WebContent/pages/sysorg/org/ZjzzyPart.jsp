<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.ZjzzyPartPageModel"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>


<style>
.x-panel-bwrap,.x-panel-body{
height: 100%
}
.picOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}
.picInnerOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}
.picGroupOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png) !important;
}
</style>
<%
	String	transferType=(String)request.getSession().getAttribute("transferType");
%>

<%
	String ereaname = (String) (new ZjzzyPartPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new ZjzzyPartPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new ZjzzyPartPageModel().areaInfo
			.get("manager"));
	String picType = (String)(new ZjzzyPartPageModel().areaInfo.get("picType"));
%>
<div align="center" style="width:100%;height:100%;" id="main">
	<table style="width:100%;height:100%;" border="0">
		<col width="30%">
		<col width="16%">
		<col width="8%">
		<col width="16%">
		<col width="30%">
		<tr align="center">
			<td id="td_id" width="30">
				<table >
					<tr>
						<odin:textEdit property="turnOut" label="转出机构"  disabled="true"/>
					</tr>
				</table>
			</td>
			<td id="td_type" style="position: relative;right:113px;">
		
				<table  border="0" style="width:0;height:30;">
					<tr>
					   <td></td>
					   <odin:select property="type" label="" canOutSelectList="true" value = '' data="['1','新增转入机构职务信息'],['2','更新转入机构职务信息']" width="100"></odin:select>
					</tr>
					
					
				</table>
		
			</td>
			<td style="position: relative;right:13px;">
				<table>
					<tr>
						<odin:textEdit property="changeInto" label="转入机构"  disabled="true" />
					</tr>
				</table>
			</td>
			
		</tr>
		<tr>
			<td colspan="1"   width="70" height="250">
			<table border="0" >
			<tr>
			<td>
				<div id="tree-div11" style="overflow: auto; border: 2px solid #c3daf9;width: 220px;"></div>
		    </td>
		    
		    <td width="300px" style="border: 0px solid #c3daf9" >
		     <div style="overflow: auto;width:250px;margin-top:1" >
			<odin:editgrid  property="person" title="" autoFill="true" pageSize="9999"  url="/" load=""  width="250" height="380" forceNoScroll="true" >
			<odin:gridJsonDataModel>
			<odin:gridDataCol name="a0000" />	
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="personcheck" />
			<odin:gridDataCol name="a0184" isLast="true" />
			</odin:gridJsonDataModel>
			
			<odin:gridColumnModel>
				<odin:gridEditColumn2 header="a0000" width="30"
						editor="text" dataIndex="a0000" hidden="true"/>
				<odin:gridEditColumn2 locked="true" header="selectall" width="45"
							editor="checkbox" dataIndex="personcheck" edited="true" 
							hideable="false" menuDisabled="true"/>
				<odin:gridEditColumn2 header="姓名" editor="text" dataIndex="a0101" width="58" align="center" edited="false"/> 
				<odin:gridEditColumn2 header="身份证号" dataIndex="a0184" width="145" isLast="true" editor="text" align="center" edited="false"/>
					
			</odin:gridColumnModel>
			</odin:editgrid>
			</div>		
			</td>
		   
		   </tr>
		   </table>
		    </td>
				
			<td align="center" width="200" height="300">
				<img src="<%=request.getContextPath()%>/pages/sysorg/org/images/arrow-right.png">
			</td>
			<td colspan="1" align="left" valign="top" height="300">
			<div id="tree-div22" style="overflow: auto; border: 2px solid #c3daf9;float: left;width: 220px;height: 100%;"></div>
			</td>
			
		</tr>
		<tr align="center">
			<td>
				<table width="100%" border="0">
					<tr>
						<td align="center"><odin:button text="&nbsp;关&nbsp;&nbsp;&nbsp;闭&nbsp;" property="closeBtn" /></td>
					</tr>
				</table>
			</td>
			<td>&nbsp;</td>
			<td id="td_id1">
				<table width="100%" border="0">
					<tr>
						<td align="center"><odin:button  text="&nbsp;执&nbsp;&nbsp;&nbsp;行&nbsp;" property="execute" handler="transferSysOrgreq"/></td>
					</tr>
				</table>
			</td>
		
		</tr>
	</table>
</div>
<odin:hidden property="turnOutId"/>
<odin:hidden property="changeIntoId"/>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="a0000s" />
<odin:hidden property="groupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="checkedgroupid1" />
<odin:hidden property="forsearchgroupid" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />


<script type="text/javascript">
Ext.onReady(function() {
	var tree_method='<%="transferSysOrg".equals(transferType)?"orgTreeJsonChange":"orgTreeJsonDataLeftTree"%>';
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id : 'group',
          el : 'tree-div11',//目标div容器
          split:false,
          width: 200,
          //minSize: 164,  
          //maxSize: 164,
          height:384,
          rootVisible: false,//是否显示最上级节点
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
        	  dataUrl :'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames='+tree_method
              	//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
       	    	//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonChange'
          }),
          listeners:{
        	  scope:this,
				checkchange:function(node,checked){
					node.attributes.checked=checked;
					var id=node.attributes['id'];
					var chs=tree.getChecked();
					for(var i=0;i<chs.length;i++){
						if(checked==true){//当前节点被选中
							if(chs[i].attributes['id'].length<id.length
									&&id.substring(0,chs[i].attributes['id'].length)==chs[i].attributes['id']){//循环节点小于当前节点,且是直属所有上级 ，则取消选中
								chs[i].ui.toggleCheck(false);//循环节点取消选中
							}
							if(chs[i].attributes['id'].length>id.length
									&&chs[i].attributes['id'].substring(0,id.length)==id){//循环节点长度大于当前节点且是当前节点直属所有下级
								chs[i].ui.toggleCheck(false);//循环节点取消选中
							}
						}
					}
					//获取所有选中的节点的文本，并赋值到转出机构input中
					var str=copyLeftTextToInput();
					document.getElementById("turnOut").value=str;
					document.getElementById("turnOut").title=str;
					str=copyLeftIdToHidden();
					document.getElementById("turnOutId").value=str;
				}
	        }
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value//默认的node值：?node=-100
           // href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.addListener('click', BiaoZhunClickLeft);
      tree.render();
      root.expand();
      root.expand(false,true, callback);//默认展开
      
      var man1 = document.getElementById('manager').value;
      var Tree1 = Ext.tree;
      var tree1 = new Tree.TreePanel( {
    	  id : 'groupright',
          el : 'tree-div22',//目标div容器
          split:false,
          width: 200,
          //minSize: 164,
          //maxSize: 164,
          rootVisible: false,
          height:384,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataright'
          })
      });
      var root1 = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyidright','"+document.getElementById('ereaid').value+"')"
      });
      tree1.setRootNode(root1);
      tree1.addListener('click', BiaoZhunClickRigt);
      tree1.render();
      root1.expand();
      root1.expand(false,true, callback);//默认展开
  	  	
}); 
function BiaoZhunClickLeft(node, e) {
	 document.getElementById('groupid').value=node.id;
	//单击选中或取消选中
	if(node.attributes.checked==false){
		node.ui.toggleCheck(true);//节点选中
	}else{
		node.ui.toggleCheck(false);//节点取消选中
	}
}
function copyLeftTextToInput(){
	var tree = Ext.getCmp("group");
	console.log(tree);
	var chs=tree.getChecked();
	var str="";
	for(var i=0;i<chs.length;i++){
		str=str+chs[i].text+" / ";
	}
	if(str.length>0){
		str=str.substring(0,str.length-2);
	}
	return str;
}
function copyLeftIdToHidden(){
	var tree = Ext.getCmp("group");
	var chs=tree.getChecked();
	var str="";
	for(var i=0;i<chs.length;i++){
		str=str+chs[i].id+",";
	}
	if(str.length>0){
		str=str.substring(0,str.length-1);
	}
	return str;
}
function BiaoZhunClickRigt(node, e) {
	radow.doEvent("querybyidright",node.id);
}
var callback = function (node){//仅展看下级
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}
function reloadTree() {
    var tree = Ext.getCmp("groupright");
    tree.root.reload();
    tree.collapseAll();
    var tree1 = Ext.getCmp("group");
    tree1.root.reload();
    tree1.collapseAll();
}
//执行事件
function transferSysOrgreq(){
	var grid = odin.ext.getCmp('person');
	/*var sm = grid.getSelectionModel();
	var selections = sm.getSelections();*/
	var n = grid.store.data.length;// 获得总行数   
	
	var a0000s='';
	for(var i=0;i<n;i++){
		var personcheck = grid.getStore().getAt(i).data.personcheck;
		if(personcheck==true){
			a0000s = a0000s+"'"+grid.getStore().getAt(i).data.a0000+"',";
		}
	}
	if(a0000s==''){
		$h.alert('系统提示：',"请先选择人员",null,240);
		return;
	}
	a0000s = a0000s.substring(0,(a0000s.length-1));
	document.getElementById("a0000s").value=a0000s;
	radow.doEvent("transferSysOrgBtn.onclick");
}
</script>

<script type="text/javascript">


Ext.onReady(function() {
	
	if(<%="transferSysOrg".equals(transferType)%>){
		document.getElementById('td_type').style.visibility='hidden';
	} 
	//Ext.getCmp('person').setWidth(500);
	/* console.log(document.getElementById("td_id"));
	var heighttd=document.getElementById("td_id").offsetHeight;
	var heighttd1=document.getElementById("td_id1").offsetHeight;
	var height=document.body.offsetHeight;
	document.getElementById("tree-div11").style.height=height-heighttd-heighttd1-8;
	document.getElementById("tree-div22").style.height=height-heighttd-heighttd1-8;
	document.getElementById("main").parentNode.parentNode.style.overflow='hidden';
	window.onresize=jcHeightWidth; */
});
 /* function jcHeightWidth(){
	var heighttd=document.getElementById("td_id").offsetHeight;
	var heighttd1=document.getElementById("td_id1").offsetHeight;
	var height=document.body.offsetHeight;
	document.getElementById("tree-div11").style.height=height-heighttd-heighttd1-8;
	document.getElementById("tree-div22").style.height=height-heighttd-heighttd1-8;
}  */
</script>
