<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<div>
<odin:hidden property="batchid" title="ѡ�������id"/>
<odin:hidden property="status" title="ѡ�������״̬"/>
<odin:hidden property="appointment" value="0"/> 
	<table>
		<tr>
			 <td>
			<odin:toolBar property="toolBar1">
			<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;������Ϣ</h1>"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="����" id="addBatch" icon="images/add.gif"  handler="addBatch" tooltip="��������" isLast="true"/>
		</odin:toolBar>	
		<odin:editgrid property="JWBatchGrid" title=""   bbarId="pageToolBar" topBarId="toolBar1"  pageSize="10" isFirstLoadData="false" url="/" >
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="id" />
			<odin:gridDataCol name="batchno" />
			<odin:gridDataCol name="status" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="���κ�" width="120" dataIndex="batchno" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="״̬" width="100" dataIndex="status" edited="false"  editor="select" align="center"  selectData="['1', 'δ�ύ'],['2', '������'],['3','�ѷ���']" />
			<odin:gridEditColumn edited="false" dataIndex="id" header="����" editor="text" isLast="true"  width="130" align="center" renderer="caozuo"/>
		</odin:gridColumnModel>
	</odin:editgrid>
			</td>
			<td>
			
		<odin:toolBar property="toolBar2">
		<odin:textForToolBar text="<h1 id=&quot;abc&quot; style=&quot;color:rgb(21,66,139);size:11px&quot; >��Ա��Ϣ</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="ѡ��" id="AddPerson"  handler="AddPerson" icon="images/icon/right.gif" tooltip="ѡ��" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="�����������" id="setType"  handler="setType" icon="image/u53.png" tooltip="�����������" isLast="true"></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:editgrid property="JWOpinionGrid"  topBarId="toolBar2" bbarId="pageToolBar"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="personcheck" />
			<odin:gridDataCol name="oid" />
			<odin:gridDataCol name="psnname" />
			<odin:gridDataCol name="psnkey" />
			<odin:gridDataCol name="duty" />
			<odin:gridDataCol name="idcard" />
			<odin:gridDataCol name="jwopinion" />
			<odin:gridDataCol name="type" />
			<odin:gridDataCol name="batchno" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" dataIndex="personcheck" edited="true"
												hideable="false" gridName="MGrid" locked="true"  sortable="false" checkBoxSelectAllClick="getCheckList" />
			<odin:gridEditColumn header="oid" width="35" dataIndex="oid"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="����" width="100" dataIndex="psnname"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="��Աid" width="50" dataIndex="psnkey"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="���֤��" width="130" dataIndex="idcard"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn2 header="ְ��" width="200" dataIndex="duty"	edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="��ί���" width="300" dataIndex="jwopinion"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="�������" width="100" dataIndex="type"	edited="false" editor="select"  align="center" selectData="['1','��һ��ʹ��'],['2','���'],['3','����']"  />
			<odin:gridEditColumn header="����" width="100" dataIndex="oid"	edited="false" editor="text" align="center"  renderer="opinioncaozuo" />
			<odin:gridEditColumn header="���κ�" width="100" dataIndex="batchno"	edited="false" editor="text" align="center"  isLast="true" hidden="true" />
		</odin:gridColumnModel>
		</odin:editgrid>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	Ext.getCmp('JWBatchGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_JWBatchGrid'))[0]-4);
	//Ext.getCmp('JWBatchGrid').setHeight(540);
	//Ext.getCmp('JWBatchGrid').setWidth(385);
	Ext.getCmp('JWBatchGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)*0.283)
	//alert(Ext.getBody().getViewSize().width*0.283);
		//ҳ�����
	Ext.getCmp('JWOpinionGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_JWBatchGrid'))[0]-4);
	//Ext.getCmp('JWOpinionGrid').setHeight(540);
	//Ext.getCmp('JWOpinionGrid').setWidth(967);
	Ext.getCmp('JWOpinionGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)*0.713);
});
	
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}	
	
	
	
function addBatch(){
	Ext.MessageBox.prompt("��ʾ","���������κ�",function(btn,value) {
	       if(btn=="ok"){
	    	  radow.doEvent("addBatch",value);
	       }

	    },this,false,"");
}	

function changeh1(text){
	document.getElementById("abc").innerHTML="��ǰѡ�����κ�:"+text;

}

function caozuo(value, params, record,rowIndex,colIndex,ds){
	if(record.data.status=='1'){
		return "<a href=\"javascript:deleteBatch('"+value+"')\">ɾ��</a>&nbsp&nbsp<a href=\"javascript:seekOpinion('"+value+"')\">�����ί���</a>";
	}
	
}
function opinioncaozuo(value, params, record,rowIndex,colIndex,ds){
	return "<a href=\"javascript:deleteOpinion('"+value+"')\">ɾ��</a>";
}


function deleteBatch(id){
	 Ext.MessageBox.confirm("��ʾ","ȷ��Ҫɾ����",function(value) {
		if(value=="yes"){
			radow.doEvent("deleteBatch",id);
		}

	    });
	
}

function deleteOpinion(id){
	var status = document.getElementById('status').value;
	if(status!='1'){
		Ext.MessageBox.alert("��ʾ","���ύ����ɾ��",function(value) {
				
		    });
		return;
	}
	Ext.MessageBox.confirm("��ʾ","ȷ��Ҫɾ����",function(value) {
		if(value=="yes"){
			radow.doEvent("deleteOpinion",id);
		}

	    });
}

function seekOpinion(id){
	radow.doEvent("seekOpinion",id);
}



function AddPerson(){//ѡ��
	var batchid = document.getElementById('batchid').value;
	if(batchid==''){
		Ext.MessageBox.alert("��ʾ","δѡ������",function(value) {

		    });
		return;
	}
	var status = document.getElementById('status').value;
	if(status!='1'){
		Ext.MessageBox.alert("��ʾ","���ύ����ѡ��",function(value) {
				
		    });
		return;
	}
	$h.openWin('seltpry','pages.xbrm2.SelectTPRY','������/���֤��ѯ ',1040,520,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'������Ա',jw:'1'});
			
	
}

function setType(){
	 
	 Ext.useShims=true;
	  var win=new Ext.Window(
	  {title:"��ʾ",
	 html:'��ѡ���������:',
	  width:270,
	  height:50,
	  modal:true,
	  buttons:[{
	  text:"��һ��ʹ��",handler:function(){
	  
	  radow.doEvent("setType","1");
	 
	
	  win.close();

	 }}, {
	  text:"���ʹ��",handler:function(){
		  radow.doEvent("setType","2");
		  win.close();
	 }},{
	  text:"����",handler:function(){
		  radow.doEvent("setType","3");
		  win.close();
	 }
	 }]
	 });
	  win.show();
 
}

 function queryByNameAndIDS(list){
	radow.doEvent("queryByNameAndIDS",list);
} 



</script>