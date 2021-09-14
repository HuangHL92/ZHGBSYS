<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.MechanismComWindowPageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.business.entity.B01"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>
    <script type="text/javascript" src="basejs/ext/ux/TreeCheckNodeUI.js"></script>
<%
	String ereaid = (String) (new MechanismComWindowPageModel().areaInfo.get("areaid"));
String groupid = request.getParameter("groupid");
//String groupname = HBUtil.getValueFromTab("B0101", "B01", "B0111='"+groupid+"'");
String groupname = "";
String b0194 = "";
if(!groupid.equals("X001")){
	HBSession sess = HBUtil.getHBSession();
	B01 b01 = (B01)sess.get(B01.class, groupid);
	groupname = b01.getB0101();
	 b0194 = b01.getB0194();
}

%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<style type="text/css">
body {overflow: hidden ;}
fieldset {overflow: auto ;}
legend {font:bold 12px tahoma, arial, helvetica, sans-serif;color:#15428b}
#savebtn{font:bold 12px tahoma, arial, helvetica, sans-serif;color:#15428b}
.save {background: url('<%=request.getContextPath()%>/images/save.gif') left  no-repeat !important;}
</style>
	<odin:toolBar property="btnToolBar" applyTo="toolBar">		
		<odin:fill />
		<odin:buttonForToolBar text="����" icon="./images/save.gif" id="savebut" tooltip="�û�����ʾ����" isLast="true"  handler="dogant"/>
	</odin:toolBar>
	<odin:hidden property="changeNode"/>
	<odin:hidden property="ryIds"/>
<div>
<div id="toolBar"></div>
<div id="left" style="width: 60%;float: left;height:100%">
<div id="baseInfo" style="height:38%;">
<fieldset style="height: 200">
<legend>�û�������Ϣ</legend>
	<table  id="myform" >
		<tr>
			<odin:textEdit property="loginname" label="�û���¼��" required="true"></odin:textEdit>
			<odin:textEdit property="username" label="����" required="true"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="password" inputType="password" label="&nbsp;����" required="true"></odin:textEdit>
			<odin:textEdit property="surepassword" inputType="password" label="&nbsp;ȷ������" required="true"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="work" label="&nbsp;������λ"></odin:textEdit>
			<odin:textEdit property="mobile" label="&nbsp;�ֻ�" maxlength="11" validator="tel_check"></odin:textEdit>
		</tr>

		<tr>
			<odin:textEdit property="tel" label="&nbsp;�칫�绰" validator="phone_check" maxlength="20"></odin:textEdit>
			<odin:textEdit property="email" label="&nbsp;�ʼ�" mask="email"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="useful" label="&nbsp;�û�״̬"  required="true" ></odin:select2>
			 <%-- <odin:select2 property="isleader" label="&nbsp;�Ƿ�Ϊ����Ա" data="['1','��'],['0','��']" required="true" ></odin:select2>  --%>
		</tr>
		<tr>
			<td></td>
<%-- 			<td align="center">
				<odin:button text="����" property="savebut"></odin:button>
			</td> --%>
		</tr>
	</table>
</fieldset>
</div>
<div style="height: 55%;" id="org">

<fieldset style="height: 300" >
<legend>������Ȩ </legend>
<table>
	<tr>
		<td><odin:checkbox property="type1" label="�����¼�"  onclick="changeType1()"/></td>
		<td><odin:checkbox property="type2" label="����ѡ��" onclick="changeType2()" /></td>
	</tr>
</table>
<div id="tree" style="height: 80%;overflow-y:auto;">

</div>
</fieldset>
</div>
</div>

<div id="right" style="height:100%;width:40%; position: absolute;padding-left: 10px;">
<fieldset style="height: 45%"  >
	<legend>����ģ����Ȩ</legend> 
<%--  <table>
	<tr>
	<odin:select property="roleSelect" data="['1','���н�ɫ'],['2','�Զ����ɫ']" required="true" label="��ɫ����" value="1" onchange="roleChange"></odin:select>
	</tr>
</table> --%> 
<div id="roleDiv" style="height:80%;overflow-y:auto;display: none">
 <table>
	<tr>
		<odin:textEdit property="roleName" label="<font color=\\'red\\'>*</font>��ɫ����"  > </odin:textEdit>
	</tr>
</table> 
	
</div>
<div id="roleDiv2">
<odin:editgrid property="grid6" hasRightMenu="false" topBarId="pageTopBar" bbarId="pageToolBar" url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.role&eventNames=grid6.dogridquery&hostsys=2" height="160" isFirstLoadData="false" >
<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
	<odin:gridDataCol name="checked" />
  <odin:gridDataCol name="roleid" />
  <odin:gridDataCol name="roledesc" />
  <odin:gridDataCol name="owner" />
  <odin:gridDataCol name="ownername" />
  <odin:gridDataCol name="hostsys" />
  <odin:gridDataCol name="rolecode" />
  <odin:gridDataCol name="status" />
  <odin:gridDataCol name="rolename" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn ></odin:gridRowNumColumn>
 <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" />
  <odin:gridEditColumn header="��ɫid" hidden="true" dataIndex="roleid" editor="text" edited="false"/>
  <odin:gridEditColumn header="��ɫ����" align="center" width="100" dataIndex="rolename" selectOnFocus="true" editor="text" edited="false"  />
  <odin:gridEditColumn header="��ɫ����" width="130" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="false" />
  <odin:gridEditColumn header="��ɫ������"  width="80" dataIndex="ownername" edited="false" editor="text" align="center" hidden="true"/>
  <odin:gridEditColumn header="��ɫ����"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" hidden="true"/>
  <odin:gridEditColumn2 header="��ɫ����ϵͳ" dataIndex="hostsys" align="center" hidden="true" edited="false" editor="select" width="80" codeType="ROLESYS"/>
  <odin:gridEditColumn2 header="״̬" sortable="false" align="center" width="80" edited="false" dataIndex="status" editor="select"  renderer="radow.commUserfulForGrid" selectData="['1','��Ч'],['0','��Ч']" isLast="true"/>
</odin:gridColumnModel>			
</odin:editgrid>	
</div>
</fieldset>
<fieldset style="height: 39%"  >
<legend>��Ա�鿴����</legend>
<div id="ry" ></div>
</fieldset>
<%-- <table style="padding-bottom: 15px;padding-left: 10">
	<tr align="center">
		<td colspan="2">
		<odin:button text="<font id=\\'savebtn\\' ><h1 style=\\'display:inline\\'>&nbsp;����</h1></font>',width:'60',iconCls:'save" handler="dogant"></odin:button>
		</td>
	</tr>
</table> --%>
</div>
</div>

<script type="text/javascript">
var ids="";
var changeNode = "";
Ext.onReady(function(){
	Ext.getCmp('grid6').store.reload();
	document.getElementById('type1').checked= true;
	changeType1();
			
	var userid = '';
	var root_id = realParent.document.getElementById('checkedgroupid').value;
	
	var icon ="";
	if('<%=b0194%>'=='1'){
		icon="./main/images/icons/companyOrgImg2.png";
	}else if('<%=b0194%>'=='2'){
		icon="./main/images/tree/leaf.gif";
	}else if('<%=b0194%>'=='3'){
		icon="./main/images/tree/folder.gif";
	}
    var tree = new Ext.tree.ColumnTree({
        el:'tree',
        width:565,
        autoHeight:true,
        border:false,
        rootVisible: false,
        autoScroll:true,
        
        columns:[{
            header:'��������',
            width:350,
            dataIndex:'task'
        },{
            header:'���',
            width:0,
            dataIndex:'duration'
        },{
            header:'ѡ��',
            width:106,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&eventNames=orgTreeJsonData&userid='+userid,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),

          	root: new Ext.tree.AsyncTreeNode({
	            text:'Tasks',
	            id: "X001" ,   
				duration:"<input type='checkbox' onclick='checkhorizon(this)' indeterminate  id='X0010'  name='-11'/>",
  	            user:'<input type="checkbox" onclick="checkhorizon(this)" indeterminate id="X0011"  name="-11"/>',
          		children: [
          	         { 	 
          	        	 text: realParent.document.getElementById('optionGroup').value, 
          	        	 leaf: false,
						 id : root_id,
						 expanded: true,
						 icon: icon,
						 duration:'<input type="checkbox" onclick="checkhorizon(this)" checked id="'+root_id+'0"  name="-10"/>',
          	        	 user:'<input type="checkbox" onclick="checkhorizon(this)" checked id="'+root_id+'1"  name="-11"/>',
          	        	 uiProvider:'col' 
          	         }
          	         ]

        }), 
        listeners:{
        	'expandnode':function(node){
        		//��ȡչ���ڵ����Ϣ
        		var th_user=node.attributes.user;
        		var th_duration=node.attributes.duration;
        		var a = th_user.split('"');
        		var b = th_duration.split('"');
        		var type1 =  document.getElementById('type1');
        		if(type1.checked){
        			var fid = document.getElementById('tree');
	      			var box = fid.getElementsByTagName('input');
	      			for (var i = 0; i < box.length; i++) {
	      				if(a[5] == box[i].id || b[5] == box[i].id){//��ǰ�ڵ�ʱ������ѡ����
		      				if(box[i].checked == true){
								if(isHaveChilden(box[i]) == true){
									if(box[i].value=='1'){
			      						checkChild1(box[i]);
									}
								}
		      				}else if(box[i].checked == false){
		      					if(isHaveChilden1(box[i]) == true){
									if(box[i].value=='2'){
			      						checkChild2(box[i]);
									}
								}
		      				}
	      				}
					}
				}
        	},
        	'append':function(tree,node,node,index){  
                if(!node.isLeaf() &&node.getDepth() <3){  
                    node.expand();    
                }  
              }
        }
    	/* rootVisible:false; */
    });
/* 	 var root = new Tree.AsyncTreeNode({
			text : 'a',
			draggable : false,
			id : 'aaa'
		});
	tree.setRootNode(root); */
    
    tree.render();

  
      
	  var roleid = '';
      var Tree = Ext.tree;
      var roletree = new Tree.TreePanel( {
            el : 'roleDiv',//Ŀ��div����
            autoScroll : true,
            rootVisible: false,
            stateful:true,
            animate : true,
            border:false,
            enableDD : true,
            checkModel: 'cascade',
            containerScroll : true,
            loader : new Tree.TreeLoader( {
            	  baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI },
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&eventNames=orgTreeJsonData&roleid='+roleid
            })
      });
      var root1 = new Tree.AsyncTreeNode( {
            text : 'ҵ��˵���',
            draggable : false,
            id : 'S000000'//Ĭ�ϵ�nodeֵ��?node=-100
      });
      roletree.setRootNode(root1);
      roletree.on("check",function(node,checked){
      	 changeNode += node.id+":"+checked+",";
//     	alert(node.id+" = "+checked);
      }); //ע��"check"�¼� 
      roletree.render();
      root1.expand();
      
      var treegrid = new Ext.tree.ColumnTree({
      	id:'treegrid',
          el:'ry',
 //         width:677,
//          height : 415,
          autoHeight:true,
          rootVisible:false,	
          autoScroll:true,
          border:false,
//          title: '���ɼ�����Ա�б�',
          
          columns:[{
              header:'�������',
              width:175,
              dataIndex:'task'
          },{
              header:'���',
              width:100,
              dataIndex:'duration'
          },{
              header:'ά��',
              width:106,
              dataIndex:'user'
          }],

          loader: new Ext.tree.TreeLoader({
              dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&eventNames=orgTreeGridJsonData&userid='+userid+'&b0111='+realParent.document.getElementById('checkedgroupid').value,
              uiProviders:{
                  'col': Ext.tree.ColumnNodeUI
              }
          }),

           	root: new Ext.tree.AsyncTreeNode({
              text:'Tasks' 

          })
      });
      treegrid.render();
      treegrid.expandAll();
      document.getElementById('left').style.height = document.body.clientHeight;
//      document.getElementById('tree').style.height = document.getElementById('org').style.height;
});

//�жϽ���Ƿ����δ����ѡ���¼����
function isHaveChilden(th){
 	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].checked==false){
				return true;
			}
		}
	}
	return false;
 }
 
 //�жϽ���Ƿ�����ѱ���ѡ���¼����
function isHaveChilden1(th){
 	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].checked==true){
				return true;
			}
		}
	}
	return false;
 }

//��ѡ�¼����
function checkChild1(th){
	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			//if(box[i].value == '2' || box[i].value == '3'){
				box[i].checked=true;
				box[i].value='1';
			//}
		}
	}
}

//ȡ����ѡ�¼����
function checkChild2(th){
	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].value != '2' && box[i].value != '3'){
				box[i].checked=false;
				box[i].value='2';
			}
		}
	}
}
 
function getValue(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
        var aParam = aParams[i].split('=');   
        URLParams[aParam[0]] = aParam[1];    
    }   
    return URLParams["userid"];
}
var beginnum=-1;
var endnum=-1;
var index=0;
function checkhorizon(th){
	var name=th.name;
	var fid = document.getElementById('tree');
    var box = fid.getElementsByTagName('input');
    var type2 =  document.getElementById('type2');
    var type1 =  document.getElementById('type1');
    if(type2.checked){
		for (var i = 0; i < box.length; i++) {
			if(box[i].id==th.id){
				if(index==0){
					beginnum=i;
				}
				if(index==1){
					endnum=i;
				}
				box[i].checked=true;
			}
		}
		index++;
		if(index==2){
			index=0;
		}
    	
	}else{
		beginnum=-1;
    	endnum=-1;
    	index=0;
	}
	//�Ƚ�����ѡ�¼���ѡ�У��ڹ�ѡ����ʱ����ֵ��Ϊ1��δ��ѡ����ѡ�¼���ʱ��ѡ��������������ֵ��Ϊ0
	if(th.checked==true){
		if(type1.checked==true){
			th.value='1';
		}else if(type1.checked==false){
			th.value='0';
		}
	}
	//�Ƚ�����ѡ�¼���ѡ�У�ȡ������ʱ��ֵΪ2��δ��ѡ����ѡ�¼�����ȡ������ʱֵΪ3
	if(th.checked==false){
		if(type1.checked==true){
			th.value='2';
		}else if(type1.checked==false){
			th.value='3';
		}
	}
	//�����ӽڵ�
	checkChild(th);
	
	var a = th.id;
	if(type1.checked==false&&type2.checked==false){
		if(a.substr(a.length-1)=="0"){
			if(th.checked==false){
				for (var i = 0; i < box.length; i++) {
					if(box[i].id==th.id){
						box[i+1].checked=false;
					}
				}
			}
		}
		if(a.substr(a.length-1)=="1"){
			if(th.checked==true){
				for (var i = 0; i < box.length; i++) {
					if(box[i].id==th.id){
						box[i-1].checked=true;
						box[i-1].value='0';
					}
		             /* if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
		            	 box[i].checked=false;
		             } */
				}
			} else {
				for (var i = 0; i < box.length; i++) {
					if(box[i].id==th.id){
						///alert(box[i].value);
						box[i-1].checked=false;
						box[i-1].value='3';
					}
					
				}
			}
		} 
	}
}

function lbClick(th){
	var fid = document.getElementById('ry');
    var box = fid.getElementsByTagName('input');
	var a = th.id;
	if(a.substr(a.length-1)=="0"){
		if(th.checked==false){
			for (var i = 0; i < box.length; i++) {
				
				if(box[i].id==th.id){
					box[i+1].checked=false;
				}
			}
		}
		
		if(th.checked==true){
			for (var i = 0; i < box.length; i++) {
				
				if(box[i].id==th.id){
					box[i+1].checked=true;
					
				}
	             /* if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
	            	 box[i].checked=false;
	             } */
			}
		}
	}
	

}

 function checkChild(th){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 var str = '';
	 var start=0;
	 var end=0;
	 if(type1.checked){
		var fid = document.getElementById('tree');
	    var box = fid.getElementsByTagName('input');
	    for (var i = 0; i < box.length; i++) {
	    	if (box[i].id ==th.id && box[i].name==th.name) {
	    		start=i;
	            for(var j = i+1; j < box.length; j++){
	            	if(box[j].name==th.name){
	            		end=j;
	            		break;
	            	}
	            }
	        }
	    }
		var id1 = th.id;
		var type = id1.substr(id1.length-1);
		var index=0;
		if(end==0){
			if(start<box.length){
				for(var i = start+2; i < box.length; i=i+2){
					for(var j = start-2; j >=0; j=j-2){
						if(box[i].name==box[j].name){
							if(type=='0'){
								end=i-2;
							}else{
								end=i-2; 
							}
							index=1;
							break;
						}
					}
					if(index==1){
						index=0;
						break;
					}
					if(i==box.length-1||i==box.length-2){
						end=box.length-1;
					}
				}
			}
			if(end!=0){
				if(type=="0"){
					end+=2;
		     	}else{
		     		end+=2;
		     	} 
	     	}
		}
		if(end==0){
			end=start+2;
		}
		

		
		if(th.checked){
			for(var i = start; i < end; i++){
				var id1 = th.id;
				var type = id1.substr(id1.length-1);
				if(type=="0"){
					for(var i = start; i < end; i++){
						var childtype = box[i].id;
				 		if(childtype.substr(childtype.length-1)=="0" && !box[i].disabled){
					 		box[i].checked=true;
				 		}
					}
			 	}
			  	if(type=="1"){
					for(var i = start-1; i < end-1; i++){
						var childtype = box[i].id;
					if(!box[i].disabled){
						box[i].checked=true;
						box[i].value='1';
					}
					 	//} 
					} 
			 	} 
		 	}
	     }else{
			for(var i = start; i < end; i++){
				var id1 = th.id;
				var type = id1.substr(id1.length-1);
				if(type=="0"){
					for(var i = start; i < end; i++){
						box[i].checked=false;
						box[i].value='2';
					}
				}
				if(type=="1"){
					
					/*  ԭ  ֻȥ��ά �¼�Ȩ��
					for(var i = start-1; i < end-1; i++){
						var childtype = box[i].id;
						if(childtype.substr(childtype.length-1)=="1"){
							box[i].checked=false;
							box[i].value='2';
						}
					} */
					////ά�� ���¼� ���/ά�� Ȩ��ȫ��ȥ��
					for(var i = start-1; i < end-1; i++){
						box[i].checked=false;
						box[i].value='2';
					}
				} 
			}
	    }
	}
	if(type2.checked){
		var fid = document.getElementById('tree');
	    var box = fid.getElementsByTagName('input');
		if(beginnum!=-1&&endnum!=-1){
			if(beginnum>endnum){
				var t =beginnum;
				beginnum=endnum;
				endnum=t;
			}

			var childtype1 = box[beginnum].id;
			var childtype2 = box[endnum].id;
			if(childtype1.substr(childtype1.length-1)==childtype2.substr(childtype2.length-1)){
				if(childtype1.substr(childtype1.length-1)=="0"){
					for(var i = beginnum; i < endnum+1; i++){
						var childtype = box[i].id;
						if(childtype.substr(childtype.length-1)=="0"){
							box[i].checked=true;
							if(i == endnum){
								box[i].value='0';
							}else{
								box[i].value='1';
							}
						} 
					}
				}
				if(childtype1.substr(childtype1.length-1)=="1"){
					for(var i = beginnum-1; i < endnum+1; i++){
						box[i].checked=true;
						if(i == endnum || i == endnum-1){
							box[i].value='0';
						}else{
							box[i].value='1';
						}
					}
				} 
				beginnum=-1;
				endnum=-1;
				index=0;
			}

		}
	}
	 
}
 


 function dogant(){
//	 Ext.Msg.wait('���Ե�...','ϵͳ��ʾ');
//		Ext.get(document.body).mask('���Ե�...',odin.msgCls);
//	window.setTimeout("Ext.get(document.body).mask('���Ե�...',odin.msgCls)",100);
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
	 var count = 0;
     var result = '';
     for (var i = 0; i < box.length; i++) {
         if (box[i].type == 'checkbox') {
             result = result + box[i].id + ':'+box[i].checked+':'+box[i].value+',';
         }
     }
	 var fid1 = document.getElementById('ry');
     var box1 = fid1.getElementsByTagName('input');
	 var count = 0;
     var result1 = '';
     for (var i = 0; i < box1.length; i++) {
         if (box1[i].type == 'checkbox') {
        	 result1 = result1 + box1[i].id + ':'+box1[i].checked+':'+box1[i].value+',';
         }
     }
//     alert(result);
     document.getElementById('ryIds').value = result1;
     document.getElementById('changeNode').value = changeNode;
     radow.doEvent('saveUser',result); //��������Ա��ģ��
 }

 function changeType1(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type1.checked){
		 type2.checked=false;
	 }
 }
 function changeType2(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type2.checked){
		 type1.checked=false;
	 }
 }
 function roleChange(value){
	 if(value.data['key'] == '2'){
		 document.getElementById('roleDiv').style.display = 'block';
		 document.getElementById('roleDiv2').style.display = 'none';
		 var a  = odin.ext.getCmp('roleName');
		 a.allowBlank = false;
	 }else{
		 document.getElementById('roleDiv').style.display = 'none';
		 document.getElementById('roleDiv2').style.display = 'block';
		 var a  = odin.ext.getCmp('roleName');
		 a.allowBlank = true;
	 }
 }
 function tel_check(phone){
	   if(phone == ""){ 
	    	return true;  	         
	    }
	   if(!(/^1[34578]\d{9}$/.test(phone))){ 
	    	return "�ֻ���������������";  	         
	    }else{
			 return true;
		 }
 }
 
 function phone_check(tel){
	    if(tel == ""){ 
	   	return true;  	         
	    }
	    if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(tel)){
			 return '�̶��绰����������';		 
		 }else{
			 return true;
		 }
 }
</script>
