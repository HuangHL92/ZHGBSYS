<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel"%>
<%String ctxPath = request.getContextPath(); 

%>	
<style>
.x-panel-bwrap,.x-panel-body{
height: 97%
}
body{
margin: 0px;padding: 0px;
}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	  var obj = window.dialogArguments;
	  document.getElementById('codetype').value=obj.codetype;
	  document.getElementById('codename').value=obj.codename;
	  document.getElementById('nsjg').value=obj.nsjg;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id:'group',
          el : 'tree-div',//Ŀ��div����
          split:false,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonData&codetype='+obj.codetype+'&codename='+obj.codename+'&codevalueparameter='+obj.codevalueparameter+'&nsjg='+obj.nsjg
          })
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      
      //�󶨽ڵ�˫���¼�  
      //add  2016-04-07 mengl
      tree.on('dblclick', function(node){
	      if(node.attributes.href&&node.id!='root' && (node.id.split("|")[1] != 'false') ){
	    	  
	    	 // alert(node.id.split("|")[0]);
	      	  //alert(node.id.split("|")[1]);
	      	//alert(node.text);
	      	radow.doEvent('dblclickTree',node.id.split("|")[0]);//���ú�̨�¼�
	      	window.returnValue=node.id.split("|")[0]+','+node.text; //����ֵ
			window.close();
	      }
      });
      
      tree.render();
      if('orgTreeJsonData'==obj.codetype){
      	tree.expandPath(root.getPath(),null,function(){addnode(tree);});
      }else{
      	root.expand();
      }
      
     
}); 
function addnode(tree){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '������λ', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'-1|true',
          leaf: true,
          href:"javascript:radow.doEvent('querybyidorg','-1|true--������λ')"
      });
   nodeadd.appendChild(newnode);
 }
function returnWin (returnValue) {
	window.returnValue=returnValue; //����ֵ
	window.close();
}

function OnInput (event) {
    //alert ("The new content: " + event.target.value);
    radow.doEvent('likeQuery',"1");
    linkTab('tab3');
}

function linkTab(id){
	Ext.getCmp("tab").activate(id);
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		document.getElementById('tabchange').value="2";
	}else if(item.getId()=='tab3'){
		document.getElementById('tabchange').value="3";
	}else{
		document.getElementById('tabchange').value="1";
	}
}

function returncode(){ 
	var tabchange = document.getElementById('tabchange').value;
	var commGrid = Ext.getCmp("commGrid").getSelectionModel();
	var memberGrid = Ext.getCmp("memberGrid").getSelectionModel();
	if(tabchange=='1'){
		if(!commGrid.hasSelection()){
			Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
			return;
		}else{
			radow.doEvent('yesBtn',commGrid.lastActive+'');
		}
	}
	if(tabchange=='2'){
		radow.doEvent('treeyesBtn');
	}
	if(tabchange=='3'){
		if(!memberGrid.hasSelection()){
			Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
			return;
		}else{
			radow.doEvent('yesBtn',memberGrid.lastActive+'');
		}
	}
}

</script>
<% 
	String ereaname = "���ϼ�";
	String ereaid = "-1";
%>
	<table style="padding-left: 0px;margin-left: 0px;" cellpadding="0" cellspacing="0">
		<tr>
			<td height="1"></td>
			<odin:hidden property="selectGroupname" />
		</tr> 
	   <tr align="left">
	   		<td width="10" align="left"></td>
		    <td align="left"><label style="font-size: 12" >���ٲ���&nbsp;</label></td>
	   		<td><input type="text" id ="likeQuery" class=" x-form-text x-form-field" onpropertychange="OnInput (event)"></td>

	   </tr>
	 </table>

<odin:tab id="tab" height="320" width="250" tabchange="grantTabChange" >
<odin:tabModel  >
	<odin:tabItem title="���ô���" id="tab1"></odin:tabItem>
	<odin:tabItem title="���д���" id="tab2" ></odin:tabItem>
	<odin:tabItem title="���ҽ��" id="tab3" isLast="true"></odin:tabItem>
</odin:tabModel > 
<odin:tabCont itemIndex="tab1"  >
<div id="tree-div111" style="overflow: auto; height: 300px; width: 250px; border: 2px solid #c3daf9;">
	<odin:editgrid property="commGrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" pageSize="10" width="50%"  height="265">
	<odin:gridJsonDataModel  id="code_value" root="data" totalProperty="totalCount">
	  <odin:gridDataCol name="code_value" />
	  <odin:gridDataCol name="code_name" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	  <odin:gridRowNumColumn />
	  <odin:gridColumn  header="����" dataIndex="code_value" width="200"  hidden="true" />
	  <odin:gridColumn  header="����" dataIndex="code_name" width="200"   isLast="true" />
	</odin:gridColumnModel>		
	</odin:editgrid>

</div>
</odin:tabCont>

<odin:tabCont itemIndex="tab2" >
<div id="tree-div" style="overflow: auto; height: 300px; width: 250px; border: 2px solid #c3daf9;"></div>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="checkedgroupname"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="codetype" />
					<odin:hidden property="codename" />
					<odin:hidden property="tabchange" value="1"/>
					<odin:hidden property="nsjg" />
				</tr>
</odin:tabCont>

<odin:tabCont itemIndex="tab3" >
<odin:editgrid property="memberGrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" pageSize="10" width="50%"  height="265">
<odin:gridJsonDataModel  id="code_value" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="code_value" />
  <odin:gridDataCol name="code_name" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn  header="����" dataIndex="code_value" width="200"  hidden="true" />
  <odin:gridColumn  header="����" dataIndex="code_name" width="200"  isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
</odin:tabCont>
</odin:tab>
	<table>
			<tr>
			<td height="1"></td>
			</tr>
			<tr > 		
			<td width="45px" height="20px">
			<td><odin:button property="resetBtn" text="���" ></odin:button></td>
			<td width="20px">
			<td><odin:button property="yesBtn" text="ȷ��" handler="returncode"></odin:button></td>
			<td width="20px">
			<td><odin:button property="cancelBtn" text="ȡ��"></odin:button></td>
			</tr>
</table>