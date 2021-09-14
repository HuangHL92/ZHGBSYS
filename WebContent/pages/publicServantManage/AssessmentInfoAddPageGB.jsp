<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#wzms{position: relative;top:60px;left:15px;}
#grid{position: relative;top:20px;left:15px;}
#btnAdd{position: absolute;top:550px;left:400px;}
#btnSave{position: absolute;top:550px;left:450px;}
#btnCancel{position: absolute;top:550px;left:500px;}
</style>
<script type="text/javascript">
/* 	function deleteRow(){ 
	var sm = Ext.getCmp("AssessmentInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
} */

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function save(){
	document.getElementById("a0000").value = window.parent.frames["BaseAddPage_GB"].document.getElementById("a0000").value;
	//�����������ɳ���1000�� 
	var a15z101 = document.getElementById('a15z101').value;	
	
	if(a15z101.length>1000) {
		
		Ext.Msg.alert("��ʾ��Ϣ", "�����������ȳ������ƣ�1000�����ڣ�");
	����return false; 
	} 
	
	//�жϵ�������Ƿ�Ϸ�
	var a1527  = document.getElementById('a1527').value;				//ѡ����ȸ���
	
	if (!(/(^[0-9]\d*$)/.test(a1527))) { 
��������
		Ext.Msg.alert("��ʾ��Ϣ", "ѡ����ȸ���������Ȼ����");
��������return false; 
����}else { 
		
		radow.doEvent("save.onclick");
����} 
	
}

function changedispaly(obj){
	var choose = Ext.getCmp('a0191').getValue();	
	if(choose){
		document.getElementById('choose').style.visibility='visible';
	}else{
		document.getElementById('choose').style.visibility='hidden';
	}
}

function yearChange(){
    var now = new Date();
    var year = now.getFullYear();
    var yearList = document.getElementById("a1521");
    for(var i=0;i<=50;i++){
        year = year-1;
        yearList.options[i] = new Option(year,year);
    }
}

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a1500 = record.data.a1500;
	/* if(realParent.buttonDisabled){
		return "ɾ��";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+a1500+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a1500){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1500);
		}else{
			return;
		}		
	});	
}
</script>



<odin:hidden property="a1500" title="����id" ></odin:hidden>
<odin:hidden property="a0000" title="��Ա����"/>
<div id="grid">
	<table>
		<tr>
			<td>
			<table>
					<tr height="50">
						<odin:select2 property="a1521" label="�������" required="true" maxlength="4" multiSelect="true" ></odin:select2>
					</tr>
					<tr height="50">
						<tags:PublicTextIconEdit property="a1517" label="���˽���" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
					</tr>
				</table>
			</td>
			<td><div style="width:350px"></div></td>
			<td>
				<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="150">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1500" />
			  		<odin:gridDataCol name="a1521" />
			   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="���" dataIndex="a1521" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="���˽���"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"/>
				   <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</td>
		</tr>
	</table>
</div>
<div id="wzms">
	<table>
		<tr>
			<td><odin:textarea property="a15z101" cols="85" rows="4" label="��������" validator="a15z101Length"></odin:textarea></td>
			<td>
				<div id="choose" style="visibility: hidden;">
					<table><odin:numberEdit property="a1527" label="&nbsp;&nbsp;&nbsp;ѡ����ȸ���" size="4" value="3"></odin:numberEdit></table>
				</div>
			</td>
			<td>
				<span>&nbsp;&nbsp;</span>
			</td>
			<td id="td"><odin:checkbox property="a0191" label="���б����" onclick="changedispaly(this)"></odin:checkbox></td>
		
		</tr>
	</table>
</div>
<%-- <table cellspacing="2" width="98%" align="left">
	<tr>
		<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>
		
	</tr>
	<tr>
		<td><odin:textarea property="a15z101" cols="70" rows="4" colspan="4" label="��������" validator="a15z101Length"></odin:textarea></td>
		
		<td><div id="choose" style="visibility: hidden;">
		<table><odin:numberEdit property="a1527" label="ѡ����ȸ���" size="6"></odin:numberEdit></table>
		</div></td>
		
		<td><odin:checkbox property="a0191" label="���б����" onclick="changedispaly(this)"></odin:checkbox></td>
	</tr>
	<tr>
		<td colspan="8">
			
			<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="200">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1500" />
			  		<odin:gridDataCol name="a1521" />
			   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="���" dataIndex="a1521" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="���˽������"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
	<tr>
	    <td><odin:select2 property="a1521" label="�������" required="true" maxlength="4" multiSelect="true" ></odin:select2></td>
		<!-- <td><odin:numberEdit property="a1521" label="����(��ʼ)���" required="true" minValue="1900" maxlength="4"></odin:numberEdit></td> 
		<td><odin:numberEdit property="yearEnd" label="�������" minValue="1900" maxlength="4"></odin:numberEdit></td> -->
		<td><tags:PublicTextIconEdit property="a1517" label="���˽������" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit></td>	
	</tr>
</table> --%>
</div>
<div id='btnAdd'>
<odin:button text="��&nbsp;&nbsp;��" handler="AddBtn"></odin:button>
</div>
<div id='btnSave'>
<odin:button text="��&nbsp;&nbsp;��" handler="save"></odin:button>
</div>
<div id='btnCancel'>
<odin:button text="ȡ&nbsp;&nbsp;��" handler="Cancel"></odin:button>
</div>
<script type="text/javascript">
function AddBtn(){
	radow.doEvent('AssessmentInfoAddBtn.onclick');
}
function Cancel(){
	alert("demo");
}
/* Ext.onReady(function(){
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a15);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var ext_gridobj = Ext.getCmp('AssessmentInfoGrid');
		var gridobj = document.getElementById('forView_AssessmentInfoGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className= "divcover_wrap";
		cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className= "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
		
	}
	
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(realParent.fieldsDisabled);
});	 */

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
Ext.onReady(function(){
	Ext.getCmp('AssessmentInfoGrid').setWidth(300); 

});
/* Ext.onReady(function(){
	var side_resize=function(){
//		 Ext.getCmp('AssessmentInfoGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_AssessmentInfoGrid'))[0]-4);
		 Ext.getCmp('AssessmentInfoGrid').setWidth(535); 
		 //document.getElementById('main').style.width = document.body.clientWidth-2;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
//		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	}
	side_resize();  
	window.onresize=side_resize; 
}); */
</script>


<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>