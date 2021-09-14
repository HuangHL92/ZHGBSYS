<%@page import="com.insigma.siis.local.business.entity.B01"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.MechanismComWindowPageModel"%>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="../../ext-all.js"></script>

    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>
<%
	String ereaid = (String) (new MechanismComWindowPageModel().areaInfo.get("areaid"));
    String groupid = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(request.getParameter("userid")).getOtherinfo();
//    String groupname = HBUtil.getValueFromTab("B0101", "B01", "B0111='"+groupid+"'");
	HBSession sess = HBUtil.getHBSession();
	String groupname = "";
	String b0194 = "";
	String qxtype = "";
	String check = "";
	if(groupid.equals("X001")){
		check = "indeterminate";
		b0194 = "3";
	}else{
		B01 b01 = (B01)sess.get(B01.class, groupid);
		 groupname = b01.getB0101();
		 b0194 = b01.getB0194();
	     qxtype = MechanismComWindowPageModel.getQxtype(request.getParameter("userid"), groupid);
	     check = qxtype.equals("1") ? "checked" : "indeterminate";
	}

%>
<script type="text/javascript">
var ids="";
Ext.onReady(function(){
	var userid = getValue();

	var root_id = '<%=groupid%>';
	var icon ="";
	if('<%=b0194%>'=='1'){
		icon="./main/images/icons/companyOrgImg2.png";
	}else if('<%=b0194%>'=='2'){
		icon="./main/images/tree/leaf.gif";
	}else if('<%=b0194%>'=='3'){
		icon="./main/images/tree/folder.gif";
	}

	var id0 = root_id + "0";
    var id1 = root_id + "1";
    var tree = new Ext.tree.ColumnTree({
        el:'tree',
//        width:565,
//        autoHeight:true,
		height:240,
		border:false,
        rootVisible:false,
        autoScroll:true,
//        title: '������Ȩ',
        
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
		duration:"<input type='checkbox' onclick='checkhorizon(this)' indeterminate  id='X001'  name='-11'/>",
        user:"<input type='checkbox' onclick='checkhorizon(this)'  indeterminate id='X001' name='-11'/>" ,
  		children: [
  	         { 	 
  	        	 text: '<%=groupname%>', 
  	        	 leaf: false,
				 id : root_id,
				 icon: icon,
				 expanded: true,
				 duration:"<input type=\"checkbox\" onclick=\"checkhorizon(this)\" checked  id=\""+id0+"\"  name=\"-10\"/>",
  	        	 user:"<input type=\"checkbox\" onclick=\"checkhorizon(this)\" <%=check%>  id=\""+id1+"\"  name=\"-11\"/>",
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
    	'append':function(tree,node,node){  
            if(!node.isLeaf() &&node.getDepth() <3){  
                node.expand();    
            }  
          }
    }
	/* rootVisible:false; */
});
tree.render();

    
    var treegrid = new Ext.tree.ColumnTree({
      	id:'treegrid',
          el:'ry',
          autoHeight:true,
          rootVisible:false,	
          autoScroll:true,
          border:false,
//          title: '��Ա�鿴����',          
          columns:[{
              header:'�������',
              width:250,
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
              dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&eventNames=orgTreeGridJsonData&userid='+userid+'&b0111='+parent.document.getElementById('checkedgroupid').value,
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
    	 /* if((th.id.substr(th.id.length-1)=="1")&&th.checked==true){
    		 for (var i = 0; i < box.length; i++) {
	             if(box[i].id==th.id){
	           	 	box[i-1].checked=true;
	             }
	              if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
	            	 box[i].checked=false;
	             } 
	         } 
    	 }else{ */
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
    	/*  } */
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
						box[i-1].checked=false;
						box[i-1].value='3';
					}
					
				}
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
	 if(type1.checked){//�����¼��� ȷ����ʼ���꣬�������ꡣ��������֪����ʲô��
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
		
		//if(start==0||start==1){
		//	end=box.length+1;
		//}
		
		if(th.checked){// ���ø�ѡ�� ѡ��
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
					// if(childtype.substr(childtype.length-1)=="1"){ 
					if(!box[i].disabled){
						box[i].checked=true;
						box[i].value='1';
					}
					 	//} 
					} 
					 /* for (var i = 0; i < box.length; i++) {
			             if(box[i].id==th.id){
			           	 	box[i-1].checked=true;
			             }
			             if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
			            	 box[i].checked=false;
			             }
			         }  */
			 	} 
		 	}
	     }else{// ���ø�ѡ��  ȡ��ѡ��
			for(var i = start; i < end; i++){//ѭ������
				var id1 = th.id;
				var type = id1.substr(id1.length-1);
				if(type=="0"){//���
					for(var i = start; i < end; i++){
						box[i].checked=false;
						box[i].value='2';
					}
				}
				if(type=="1"){//ά��
					
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
			  /* if(th.checked=true&&(th.id.substr(th.id.length-1)=="1")){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i-1].checked=true;
		             }
		             if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
		            	 box[i].checked=false;
		             }
		         }  
			 }  */
		}
	}
	 
}
 


 function dogant(){
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
	 var count = 0;
     var result = '';
     for (var i = 0; i < box.length; i++) {
         if (box[i].type == 'checkbox') {
             result = result + box[i].id + ':'+box[i].checked+':'+box[i].value+',';
         }
     }
   	 //�ж�Խ��ѡ��
     var arraybo = [];
     var arrayObj = result.split(",");
     for(i=0;i<arrayObj.length;i++){
    	 var array = arrayObj[i].split(":");
    	 if(array[1]=='true'){
    		//�õ����õĻ�������(true)
    		var a = array[0].substr(0,(array[0].length-1));
    		arraybo.push(a);
    	 }
     }
     //ȥ���׻���������
     var newArr1 = [];
     for(var i =0;i<arraybo.length-1;i++){
     ���� if(newArr1.indexOf(arraybo[i]) == -1){
     ��������	newArr1.push(arraybo[i]);
     ��		}
     }
     //û��ȥ���׻���������
     var newArr2 = [];
     for(var i =0;i<arraybo.length-1;i++){
     ���� if(newArr2.indexOf(arraybo[i]) == -1){
     ��������	newArr2.push(arraybo[i]);
     ��		}
     }
     //����sum�Ĵ������ж�
     var sum =0;
     //ȥ���׻���
     newArr1.shift();
     for(i=0;i<newArr1.length;i++){
    	for(j=0;j<newArr2.length;j++){
    	   if(newArr1[i].substr(0,(newArr1[i].length-4))==newArr2[j]){
    	    	sum++;
    	    }
    	}
    }
    if(sum == (newArr1.length)){
    	
    }else{
    	Ext.Msg.alert('ϵͳ��ʾ', '��Ȩ����Խ��');
    	return;
    } 
    ids=result;
//    console.log(ids);
	 var fid1 = document.getElementById('ry');
     var box1 = fid1.getElementsByTagName('input');
	 var count = 0;
     var result1 = '';
     for (var i = 0; i < box1.length; i++) {
         if (box1[i].type == 'checkbox') {
        	 result1 = result1 + box1[i].id + ':'+box1[i].checked+':'+box1[i].value+',';
         }
     }
     document.getElementById('ryIds').value = result1;
	radow.doEvent('dogrant',ids);
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


/*   function dogant(){

      radow.doEvent('saveUser',result1); //��������Ա��ģ��
  } */

</script>
<odin:toolBar property="btnToolBar2" applyTo="toolBar2">
	<odin:textForToolBar text="<h3>��Ա����</h3>" />
	<odin:fill isLast="true"/>
</odin:toolBar>
<div>
<div id="groupTreeContent" >
</div>
<table>
	<tr>
		<td><odin:checkbox property="type1" label="�����¼�"  onclick="changeType1()" /></td>
		<td><odin:checkbox property="type2" label="����ѡ��" onclick="changeType2()" /></td>
	</tr>
</table>


<div id="tree"></div>

<div id="toolBar2"></div>
<div id="ry" ></div>
</div>

<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="ryIds"/>
<odin:toolBar property="btnToolBar" applyTo="groupTreeContent">
	<odin:textForToolBar text="<h3>��������</h3>" />
	<odin:fill />

	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" handler="dogant" tooltip="����" isLast="true"/>
</odin:toolBar>

<%-- <odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
 --%><odin:window src="/blank.htm" id="mechanismRemWin" width="600" height="500" title="����Ȩ���Ƴ�ҳ��"></odin:window>	

