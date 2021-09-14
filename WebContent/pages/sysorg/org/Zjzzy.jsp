<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.ZjzzyPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
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
<script type="text/javascript">
Ext.onReady(function() {
	var tree_method='<%="transferSysOrg".equals(transferType)?"orgTreeJsonChange":"orgTreeJsonDataLeftTree"%>';
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id : 'group',
          el : 'tree-div',//Ŀ��div����
          split:false,
          width: 300,
          //minSize: 164,  
          //maxSize: 164,
          //height:240,
          rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
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
						if(checked==true){//��ǰ�ڵ㱻ѡ��
							if(chs[i].attributes['id'].length<id.length
									&&id.substring(0,chs[i].attributes['id'].length)==chs[i].attributes['id']){//ѭ���ڵ�С�ڵ�ǰ�ڵ�,����ֱ�������ϼ� ����ȡ��ѡ��
								chs[i].ui.toggleCheck(false);//ѭ���ڵ�ȡ��ѡ��
							}
							if(chs[i].attributes['id'].length>id.length
									&&chs[i].attributes['id'].substring(0,id.length)==id){//ѭ���ڵ㳤�ȴ��ڵ�ǰ�ڵ����ǵ�ǰ�ڵ�ֱ�������¼�
								chs[i].ui.toggleCheck(false);//ѭ���ڵ�ȡ��ѡ��
							}
						}
					}
					//��ȡ����ѡ�еĽڵ���ı�������ֵ��ת������input��
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
            id : document.getElementById('ereaid').value//Ĭ�ϵ�nodeֵ��?node=-100
           // href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.addListener('click', BiaoZhunClickLeft);
      tree.render();
      root.expand();
      root.expand(false,true, callback);//Ĭ��չ��
      
      var man1 = document.getElementById('manager').value;
      var Tree1 = Ext.tree;
      var tree1 = new Tree.TreePanel( {
    	  id : 'groupright',
          el : 'tree-div1',//Ŀ��div����
          split:false,
          width: 300,
          //minSize: 164,
          //maxSize: 164,
          rootVisible: false,
          //height:240,
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
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
            href:"javascript:radow.doEvent('querybyidright','"+document.getElementById('ereaid').value+"')"
      });
      tree1.setRootNode(root1);
      tree1.addListener('click', BiaoZhunClickRigt);
      tree1.render();
      root1.expand();
      root1.expand(false,true, callback);//Ĭ��չ��
  
}); 
function BiaoZhunClickLeft(node, e) {
	//����ѡ�л�ȡ��ѡ��
	if(node.attributes.checked==false){
		node.ui.toggleCheck(true);//�ڵ�ѡ��
	}else{
		node.ui.toggleCheck(false);//�ڵ�ȡ��ѡ��
	}
}
function copyLeftTextToInput(){
	var tree = Ext.getCmp("group");
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
var callback = function (node){//��չ���¼�
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
</script>

<%
	String ereaname = (String) (new ZjzzyPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new ZjzzyPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new ZjzzyPageModel().areaInfo
			.get("manager"));
	String picType = (String)(new ZjzzyPageModel().areaInfo.get("picType"));
%>
<div align="center" style="width:100%;height:100%;" id="main">
	<table style="width:100%;height:100%;" border="0">
		<col width="30%">
		<col width="16%">
		<col width="8%">
		<col width="16%">
		<col width="30%">
		<tr align="center">
			<td></td>
			<td id="td_id">
				<table>
					<tr>
						<odin:textEdit property="turnOut" label="ת������"  disabled="true"/>
					</tr>
				</table>
			</td>
			<td id="td_type">
				<table>
					<tr>
						<odin:select property="type" label="" canOutSelectList="true" value = '' data="['1','����ת�����ְ����Ϣ'],['2','����ת�����ְ����Ϣ']" width="150"></odin:select>
					</tr>
				</table>
			</td>
			<td>
				<table>
					<tr>
						<odin:textEdit property="changeInto" label="ת�����"  disabled="true" />
					</tr>
				</table>
			</td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="1" align="left" valign="top">
				<div id="tree-div" style="overflow: auto; border: 2px solid #c3daf9;float: right;width: 220px;height: 290px;"></div>
			</td>
			<td align="center">
				<img src="<%=request.getContextPath()%>/pages/sysorg/org/images/arrow-right.png">
			</td>
			<td colspan="1" align="left" valign="top"><div id="tree-div1" style="overflow: auto; border: 2px solid #c3daf9;float: left;width: 220px;height: 290px;"></div></td>
			<td></td>
		</tr>
		<tr align="center">
			<td></td>
			<td>
				<table width="100%" border="0">
					<tr>
						<td align="center"><odin:button text="&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;" property="closeBtn" /></td>
					</tr>
				</table>
			</td>
			<td>&nbsp;</td>
			<td id="td_id1">
				<table width="100%" border="0">
					<tr>
						<td align="center"><odin:button  text="&nbsp;ִ&nbsp;&nbsp;&nbsp;��&nbsp;" property="transferSysOrgBtn"/></td>
					</tr>
				</table>
			</td>
			<td></td>
		</tr>
	</table>
</div>
<odin:hidden property="turnOutId"/>
<odin:hidden property="changeIntoId"/>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="forsearchgroupid" />
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
	
	if(<%="transferSysOrg".equals(transferType)%>){
		document.getElementById('td_type').style.visibility='hidden';
	}
	
	
	var heighttd=document.getElementById("td_id").offsetHeight;
	var heighttd1=document.getElementById("td_id1").offsetHeight;
	var height=document.body.offsetHeight;
	document.getElementById("tree-div").style.height=height-heighttd-heighttd1-8;
	document.getElementById("tree-div1").style.height=height-heighttd-heighttd1-8;
	document.getElementById("main").parentNode.parentNode.style.overflow='hidden';
	window.onresize=jcHeightWidth;
});
function jcHeightWidth(){
	var heighttd=document.getElementById("td_id").offsetHeight;
	var heighttd1=document.getElementById("td_id1").offsetHeight;
	var height=document.body.offsetHeight;
	document.getElementById("tree-div").style.height=height-heighttd-heighttd1-8;
	document.getElementById("tree-div1").style.height=height-heighttd-heighttd1-8;
}
</script>
