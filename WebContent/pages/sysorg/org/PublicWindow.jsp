<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page
	import="com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel"%>
<%
	String ctxPath = request.getContextPath();
	String codetype = request.getParameter("codetype");
	String codevalue = request.getParameter("codevalue");
	String codename = request.getParameter("codename");
	String closewin = request.getParameter("closewin");
	String property = request.getParameter("property");
	String nsjg = request.getParameter("nsjg");
	String codevalueparameter = request
			.getParameter("codevalueparameter");
	String valuestr=request.getParameter("valuestr");
	
	if(valuestr!=null){
		//���Ľ���
		valuestr = new String(request.getParameter("valuestr").getBytes("iso8859-1"),"utf8");
	}
	if(valuestr==null||"null".equals(valuestr)){
		valuestr="";
	}
%>
<%@include file="/comOpenWinInit2.jsp" %>	
<style>
.x-panel-bwrap,.x-panel-body{
#msg-div{
display: none;
}
height: 97%
}
body{
margin: 0px;padding: 0px;
overflow: hidden;
}
</style>
<script type="text/javascript">
//���input��enter�س��¼�
function EnterPress(e){ //���� event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
		document.getElementById("likeQuery").focus(); 
		OnInput (event);
	}
	e.cancelBubble=true;//��ֹ�¼�
	
}
//�����̰����¼�
document.onkeydown=function(event){ 
    var event=event||window.event;
    if(event.keyCode==13){//enter������
    	returncode();
    }
    
};
//��ʼ����
Ext.onReady(function() {
	  //var obj = window.dialogArguments;
	  //document.getElementById('codetype').value=obj.codetype;
	  //document.getElementById('codename').value=obj.codename;
	  document.getElementById('nsjg').value='<%=nsjg%>';
	  document.getElementById('codevalueparameter').value='<%=codevalueparameter%>';
	  document.getElementById('codetype').value='<%=codetype%>';
	  document.getElementById('codename').value='<%=codename%>';
	  document.getElementById('closewin').value='<%=closewin%>';
	  document.getElementById('property').value='<%=property%>';
	  document.getElementById('likeQuery').value='<%=valuestr%>';
	  var ChangeValue = '';
	  if(realParent.document.getElementById('ChangeValue')){
	  	ChangeValue = '&ChangeValue=' + realParent.document.getElementById('ChangeValue').value;
	  }
	  var codevalueparameter = '';
	  if(realParent.document.getElementById('codevalueparameter')){
	  	codevalueparameter = '&codevalueparameter=' + realParent.document.getElementById('codevalueparameter').value;
	  }
	  if(<%=codetype.equals("GWGLLB")%>){
		  codevalueparameter = '&codevalueparameter=' + document.getElementById('codevalueparameter').value;
	  }
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
          height:298,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonData&codetype=<%=codetype%>&codename=<%=codename%>&nsjg=<%=nsjg %>'+ChangeValue+codevalueparameter
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
    	 // console.log("node",node.attributes);
    	  //var tree = Ext.getCmp("group");
		  
    	  //alert(node.getPath());
	     if(node.attributes.href&&node.id!='root' && (node.id.split("|")[1] != 'false') ){
	    	 // alert(node.id.split("|")[0]);
	      	  //alert(node.id.split("|")[1]);
	      	//alert(node.text);
	      	radow.doEvent('dblclickTree',node.id.split("|")[0]);//���ú�̨�¼�
	      	radow.doEvent('cancelBtn.onclick',node.id.split("|")[0]+','+node.text);
	      	
	      	//window.returnValue=node.id.split("|")[0]+','+node.text; //����ֵ
			//window.close();
	      }
      });
      tree.on('load',function(node){if(node.id!=-1){return;}curNode();})
      tree.render();
      if('orgTreeJsonData'=='<%=codetype%>' && '<%=nsjg%>'!='0'){
      	tree.expandPath(root.getPath(),null,function(){addnode(tree); });
      }else{
      	root.expand();
      }
     
      setTimeout(linktabstart,500);
      
      
      
}); 

function linktabstart(){
	radow.doEvent('linktabstart','<%=codetype %>');
}
function addnode(tree){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '������λ', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001|true',
          leaf: true,
          href:"javascript:radow.doEvent('querybyidorg','X001|true--������λ')"
      });
   nodeadd.appendChild(newnode);
 }
function returnWin (returnValue) {
	radow.doEvent('cancelBtn.onclick',returnValue);
	//window.returnValue=returnValue; //����ֵ
	//window.close();
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
	//var act = document.activeElement.id;
	//alert(act);
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
		//radow.doEvent('hasRule',memberGrid.lastActive+'');
		if(!memberGrid.hasSelection()){
			Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
			return;
		}else{
			radow.doEvent('yesBtn',memberGrid.lastActive+'');
		}
	}
}
//��λ
function curNode(){
	var codeType="";
	var codeValue="";
	var sourceFalg ="";//node·����ʶ��
	var codeName="";
	try{
		codeType='<%=codetype %>';
		codeValue ='<%=codevalue %>';
		sourceFalg="rmb";
	}catch(err){
		codeType="";
		codeValue = "";
		sourceFalg="rmb";
	}
	
	if(codeValue != "" && codeType != ""){
		Ext.Ajax.request({
			url : '<%=request.getContextPath()%>/JGQueryServlet?method=JGQuery_xzcc',
			params : {'codeValue' : codeValue,'codeType':codeType,'codeName':codeName,'sourceFalg':sourceFalg},
			method : "post",
			success : function(a, b) {
			  var r = Ext.util.JSON.decode(a.responseText)+"";
			  if(r!="" &&r != null){
				  var tree = Ext.getCmp("group");
				  tree.expandPath(r, 'id', function(bSucess, oLastNode) {  
                      tree.getSelectionModel().select(oLastNode);  
                  });  
			  };
			}
		});
	}
}
function setcommGridSelect(){
	var valuestr_=document.getElementById("likeQuery").value;
	if(valuestr_==""||valuestr_.length==0||valuestr_=="null"||valuestr_=='undefined'||valuestr_==null){//û�в�ѯ����.������
		return;
	}
	/* var data_ = odin.ext.getCmp('commGrid').store.data;//��ȡgrid��������
	var code_name_="";//ÿ�е�code_name ����ֵ
	var flag_=false;
	for(var i=0;i<data_.length;i++){
		if(flag_==true){//�Ѿ�ѡ�� ѡ�У�����ִ��
			//break;
			return;
		}
		code_name_=data_.itemAt(i).get('code_name'); 
		if(code_name_.indexOf(valuestr_)==-1){//code_name���б�ֵ�� ������ valuestr_(��ѯ����)
			
		}else{
			odin.ext.getCmp('commGrid').getSelectionModel().selectRow(i);//���ñ��б�ѡ��
			flag_=true;//ƥ�䵽��Ӧ��ֵ
		}
	} */
	//if(flag_==false){//�����б��У�δƥ�䵽��Ӧ��ֵ
		//ִ�в�ѯ
		radow.doEvent('likeQuery',"1");
		//�л�tab
	    linkTab('tab3');
	    //setmemberGridSelect();//���ñ�ѡ����
	//}
}
function setmemberGridSelect(){
	var valuestr_=document.getElementById("likeQuery").value;
	if(valuestr_==""||valuestr_.length==0){//û�в�ѯ����.������ѡ��
		return;
	}
	var data_ = odin.ext.getCmp('memberGrid').store.data;//��ȡgrid��������
	var code_name_="";//ÿ�е�code_name ����ֵ
	var flag_=false;
	
	for(var i=0;i<data_.length;i++){
		if(flag_==true){//�Ѿ�ѡ�� ������ִ��
			//break;
			return;
		}
		code_name_=data_.itemAt(i).get('code_name'); 
		if(code_name_.indexOf(valuestr_)==-1){//code_name���б�ֵ�� ������ valuestr_(��ѯ����)
			
		}else{
			odin.ext.getCmp('memberGrid').getSelectionModel().selectRow(i);//���ñ��б�ѡ��
			try{
				//����  memberGrid�۽�����һ��
				document.getElementById("gridDiv_memberGrid").firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.focus();
			}catch(err){
				
			}
			flag_=true;//ƥ�䵽��Ӧ��ֵ
		}
	}
	
	if(flag_==false&&data_.length>0){//δƥ�䵽��¼���Ҽ�¼��Ϊ�գ�����ƴ������
		odin.ext.getCmp('memberGrid').getSelectionModel().selectRow(0);//���õ�һ�б�ѡ��
		try{
			//����  memberGrid�۽�����һ��
			document.getElementById("gridDiv_memberGrid").firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.focus();
		}catch(err){
			
		}
		return;
	}
}
</script>
<%
	String ereaname = "���ϼ�";
	String ereaid = "-1";
%>
<div>
	<table style="padding-left: 0px;margin-left: 0px;" cellpadding="0" cellspacing="0">
		<tr>
			<td height="1"></td>
			<odin:hidden property="selectGroupname" />
			<odin:hidden property="closewin" />
			<odin:hidden property="property" />
		</tr> 
	    <tr align="left">
	   		<td width="10" align="left"></td>
		    <td align="left"><label style="font-size: 12" >���ٲ���&nbsp</label></td>
	   		<td><input type="text" id ="likeQuery" name="likeQuery" class=" x-form-text x-form-field" onkeypress="EnterPress(event)" onkeydown="EnterPress()"></td>	
 			<td>&nbsp</td>
  			<td><odin:button property="likeQuery2" text="��ѯ" handler="OnInput" ></odin:button></td>
      	
 	    </tr>
	 </table>

<odin:tab id="tab"  tabchange="grantTabChange">
	<odin:tabModel>
		<odin:tabItem title="���ô���" id="tab1"></odin:tabItem>
		<odin:tabItem title="���д���" id="tab2"></odin:tabItem>
		<odin:tabItem title="���ҽ��" id="tab3" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="tab1">
		<div id="tree-div111"
			style="overflow: auto; height: 300px;  border: 2px solid #c3daf9;">
		<odin:editgrid property="commGrid" bbarId="pageToolBar" 
			isFirstLoadData="false" url="/" pageSize="10" load="setcommGridSelect"
			height="265">
			<odin:gridJsonDataModel id="code_value" root="data"
				totalProperty="totalCount">
				<odin:gridDataCol name="code_value" />
				<odin:gridDataCol name="code_name" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn />
				<odin:gridColumn header="����" dataIndex="code_value" width="800"
					hidden="true" />
				<odin:gridColumn header="����" dataIndex="code_name" width="800"
					isLast="true" />
			</odin:gridColumnModel>
		</odin:editgrid></div>
	</odin:tabCont>

	<odin:tabCont itemIndex="tab2">
		<div id="tree-div"
			style="overflow: auto; height: 300px;  border: 2px solid #c3daf9;"></div>
		<tr>
			<odin:hidden property="checkedgroupid" />
			<odin:hidden property="checkedgroupname" />
			<odin:hidden property="forsearchgroupid" />
			<odin:hidden property="ereaname" value="<%=ereaname%>" />
			<odin:hidden property="ereaid" value="<%=ereaid%>" />
			<odin:hidden property="codetype" />
			<odin:hidden property="codename" />
			<odin:hidden property="tabchange" value="1" />
			<odin:hidden property="nsjg" />
			<odin:hidden property="codevalueparameter" />
		</tr>
	</odin:tabCont>
	

	<odin:tabCont itemIndex="tab3">
		<odin:editgrid property="memberGrid" bbarId="pageToolBar"
			isFirstLoadData="false" url="/" pageSize="10" width="50%" load="setmemberGridSelect"
			height="265" rowDbClick="returncode">
			<odin:gridJsonDataModel id="code_value" root="data"
				totalProperty="totalCount">
				<odin:gridDataCol name="code_value" />
				<odin:gridDataCol name="code_name" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn />
				<odin:gridColumn header="����" dataIndex="code_value" width="800"
					hidden="true" />
				<odin:gridColumn header="����" dataIndex="code_name" width="800"
					isLast="true" />
			</odin:gridColumnModel>
		</odin:editgrid>
	</odin:tabCont>
</odin:tab>
<table>
	<tr>
		<td height="1"></td>
	</tr>
	<tr>
		<td width="45px" height="20px"></td>
		<td><odin:button property="resetBtn" text="���"></odin:button></td>
		<td width="20px"></td>
		<td><odin:button property="yesBtn" text="ȷ��" handler="returncode"></odin:button></td>
		<td width="20px"></td>
		<td><odin:button property="cancelBtn" text="ȡ��"></odin:button></td>
	</tr>
</table>
</div>
