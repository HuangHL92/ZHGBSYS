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
						<odin:textEdit property="turnOut" label="ת������"  disabled="true"/>
					</tr>
				</table>
			</td>
			<td id="td_type" style="position: relative;right:113px;">
		
				<table  border="0" style="width:0;height:30;">
					<tr>
					   <td></td>
					   <odin:select property="type" label="" canOutSelectList="true" value = '' data="['1','����ת�����ְ����Ϣ'],['2','����ת�����ְ����Ϣ']" width="100"></odin:select>
					</tr>
					
					
				</table>
		
			</td>
			<td style="position: relative;right:13px;">
				<table>
					<tr>
						<odin:textEdit property="changeInto" label="ת�����"  disabled="true" />
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
				<odin:gridEditColumn2 header="����" editor="text" dataIndex="a0101" width="58" align="center" edited="false"/> 
				<odin:gridEditColumn2 header="���֤��" dataIndex="a0184" width="145" isLast="true" editor="text" align="center" edited="false"/>
					
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
						<td align="center"><odin:button text="&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;" property="closeBtn" /></td>
					</tr>
				</table>
			</td>
			<td>&nbsp;</td>
			<td id="td_id1">
				<table width="100%" border="0">
					<tr>
						<td align="center"><odin:button  text="&nbsp;ִ&nbsp;&nbsp;&nbsp;��&nbsp;" property="execute" handler="transferSysOrgreq"/></td>
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
          el : 'tree-div11',//Ŀ��div����
          split:false,
          width: 200,
          //minSize: 164,  
          //maxSize: 164,
          height:384,
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
          el : 'tree-div22',//Ŀ��div����
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
	 document.getElementById('groupid').value=node.id;
	//����ѡ�л�ȡ��ѡ��
	if(node.attributes.checked==false){
		node.ui.toggleCheck(true);//�ڵ�ѡ��
	}else{
		node.ui.toggleCheck(false);//�ڵ�ȡ��ѡ��
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
//ִ���¼�
function transferSysOrgreq(){
	var grid = odin.ext.getCmp('person');
	/*var sm = grid.getSelectionModel();
	var selections = sm.getSelections();*/
	var n = grid.store.data.length;// ���������   
	
	var a0000s='';
	for(var i=0;i<n;i++){
		var personcheck = grid.getStore().getAt(i).data.personcheck;
		if(personcheck==true){
			a0000s = a0000s+"'"+grid.getStore().getAt(i).data.a0000+"',";
		}
	}
	if(a0000s==''){
		$h.alert('ϵͳ��ʾ��',"����ѡ����Ա",null,240);
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
