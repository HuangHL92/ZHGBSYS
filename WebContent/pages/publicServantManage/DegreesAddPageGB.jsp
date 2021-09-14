<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.InfoComWindowPageModel"%>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<!-- <script type="text/javascript" src="../../basejs/helperUtil.js"></script> -->
<style>
<%=FontConfigPageModel.getFontConfig()%>
.x-panel-header{
border: 0px;
}
.x-toolbar span{
	font: bold;
}
#btnSet{position: absolute;top:550px;left:280px;}
#btnAdd{position: absolute;top:550px;left:400px;}
#btnSave{position: absolute;top:550px;left:450px;}
#btnCancel{position: absolute;top:550px;left:500px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
/* function deleteRow(){ 
	var sm = Ext.getCmp("degreesgrid").getSelectionModel();
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
function setA0801aValue(record,index){//ѧλ
	Ext.getCmp('a0801a').setValue(record.data.value);
}
function setA0901aValue(record,index){//ѧ��
	Ext.getCmp('a0901a').setValue(record.data.value);
}
function setA0824Value(record,index){//רҵ
	Ext.getCmp('a0824').setValue(record.data.value);
}
function onkeydownfn(id){
	if(id=='a0801b')
		Ext.getCmp('a0801a').setValue('');
	if(id=='a0901b')
		Ext.getCmp('a0901a').setValue('');
	if(id=='a0827')
		Ext.getCmp('a0824').setValue('');
}
odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
        if(obj.getAttribute('alowCheck')=="false"){
            return;
        }
        <%
		  String data = InfoComWindowPageModel.getInfoData();
		  String info = "checkBoxColClick";
		  if(data==null||data.equals("")){
			  info = "checkBoxColClick";
		  }else{
			  data = data.replaceAll("'","");
			  String[] datas = data.split(",");
			  boolean flag = false;
			  for(String str:datas){
				  if(str.equals("a0801a")||str.equals("a0801b")||str.equals("a0901a")||str.equals("a0901b")||str.equals("a0804")||str.equals("a0807")||str.equals("a0904")||str.equals("a0814")||str.equals("a0824")||str.equals("a0827")||str.equals("a0837")||str.equals("a0811")||str.equals("a0898")){
					  info="nosystem";
					  break;
				  }
			  }
		  }
		  %>
		if(!<%=info %>(rowIndex,colIndex,null,gridId)){
			return;
		}
        if(obj.className=='x-grid3-check-col'){
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, true);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
			}
			obj.className = 'x-grid3-check-col-on';
        }else{
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, false);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
				if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
					document.getElementById("selectall_"+gridId+"_"+colName).value='false';
					document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
				}	
			}
			obj.className = 'x-grid3-check-col';
        }
};
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
//ѧ��ѧλ�������
function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	var sr = getGridSelected(gridName);
	if(!sr){
		return;
	}
	var msg='';
	if(sr.data.a0899==='true'||sr.data.a0899===true){
		msg = 'ȡ���ü�¼��,��ѧ��ѧλ���������<br/>ȷ��Ҫȡ������ü�¼��?';
	}else{
		msg = 'ѡ��ü�¼�󣬸�ѧ��ѧλ�����<br/>ȷ��Ҫѡ������ü�¼��?';
	}
	$h.confirm('ϵͳ��ʾ',msg,220,function(id){
		if("ok"==id){
			radow.doEvent('degreesgridchecked',sr.data.a0800);
		}else{
			
			return false;
		}
	});
	
	//alert(sr.data.a0800);
	
}


function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a0800 = record.data.a0800;
	/* if(realParent.buttonDisabled){
		return "ɾ��";
	} */
	var fieldsDisabled = <%=InfoComWindowPageModel.getInfoData()%>;
	if(fieldsDisabled==''||fieldsDisabled==undefined){
		return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">ɾ��</a>";
	}
	var datas = fieldsDisabled.toString().split(',');
	for(var i=0;i<datas.length;i++){
		if(datas[i]==("a0801a")||datas[i]==("a0801b")||datas[i]==("a0901a")||datas[i]==("a0901b")||datas[i]==("a0804")||datas[i]==("a0807")||datas[i]==("a0904")||datas[i]==("a0814")||datas[i]==("a0824")||datas[i]==("a0827")||datas[i]==("a0837")||datas[i]==("a0811")||datas[i]==("a0898")){
			  Ext.getCmp("sethighest").setDisabled(true);
			  Ext.getCmp("degreesAddBtn").setDisabled(true);
			return "<u style=\"color:#D3D3D3\">ɾ��</u>"; 
		}
		
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a0800){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0800);
		}else{
			return;
		}		
	});	
}
</script>


<%--<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>--%>
<div>
<odin:hidden property="a0800" title="����id" ></odin:hidden>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a0834" title="���ѧ����־" />
<odin:hidden property="a0835" title="���ѧλ��־" />
<input type="reset" name="reset" id="resetbtn" style="display: none;" />
<table>
	<tr><div style="height: 20px"></div></tr>
	<tr>
		<td>
			<table>
				<tr><odin:select2 property="a0837" label="�������" required="true" codeType="ZB123"></odin:select2></tr>
				<tr><tags:PublicTextIconEdit property="a0801b" label="ѧ������" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
				<tr><odin:textEdit property="a0801a" label="ѧ������" validator="a0801aLength"></odin:textEdit></tr>
				<tr><odin:numberEdit property="a0811" label="ѧ������(��)" maxlength="3"></odin:numberEdit></tr>
				<tr><tags:PublicTextIconEdit property="a0901b" label="ѧλ����" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
				<tr><odin:textEdit property="a0901a" label="ѧλ����" validator="a0901aLength"></odin:textEdit></tr>
				<tr> <odin:textEdit property="a0814" label="ѧУ����λ������" validator="a0814Length"></odin:textEdit></tr>
				<tr><tags:PublicTextIconEdit property="a0827" label="��ѧרҵ���" onchange="setA0824Value" codetype="GB16835" readonly="true"/></tr>
				<tr><odin:textEdit property="a0824" label="��ѧרҵ����" validator="a0824Length"></odin:textEdit></tr>
				<tr><odin:NewDateEditTag property="a0804" label="��ѧʱ��"  maxlength="8"></odin:NewDateEditTag>	</tr>
				<tr><odin:NewDateEditTag property="a0807" label="�ϣ��ޣ�ҵʱ��" maxlength="8"></odin:NewDateEditTag></tr>
				<tr><odin:NewDateEditTag property="a0904" label="ѧλ����ʱ��" maxlength="8"></odin:NewDateEditTag></tr>
			</table>
		</td>
		<td width="300"></td>
		<td>
			<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" topBarId="toolBar2" url="/"   
			 height="350" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		     		<odin:gridDataCol name="a0899"/>
		     		<odin:gridDataCol name="a0800" />
			  		<odin:gridDataCol name="a0837" />
			  		<odin:gridDataCol name="a0801b" />
			   		<odin:gridDataCol name="a0901b" />
			   		<odin:gridDataCol name="a0814" />
			   		<odin:gridDataCol name="a0827" />			   		
			   		<odin:gridDataCol name="a0811" />
			   		<odin:gridDataCol name="a0804" />
			   		<odin:gridDataCol name="a0807" />
			   		<odin:gridDataCol name="a0904" />
			   		<odin:gridDataCol name="a0801a" />
			   		<odin:gridDataCol name="a0901a" />
			   		<odin:gridDataCol name="a0824" isLast="true"/>
			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="���" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="���" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
				  <odin:gridEditColumn header="ѧ��" dataIndex="a0801a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="ѧλ" dataIndex="a0901a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" edited="false" editor="text"/>
				  <odin:gridEditColumn header="רҵ" dataIndex="a0824" edited="false" editor="text" />
				  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>		
		</td>
	</tr>
</table>
<%-- <table cellspacing="4" width="100%" align="center">
	<tr>
		<odin:select2 property="a0837" label="�������" required="true" codeType="ZB123"></odin:select2>
		<tags:PublicTextIconEdit property="a0801b" label="ѧ������" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
		<tags:PublicTextIconEdit property="a0901b" label="ѧλ����" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit>
	</tr>
	<tr>
	    <odin:numberEdit property="a0811" label="ѧ������(��)" maxlength="3"></odin:numberEdit>
	    <odin:textEdit property="a0801a" label="ѧ������" validator="a0801aLength"></odin:textEdit>
	    <odin:textEdit property="a0901a" label="ѧλ����" validator="a0901aLength"></odin:textEdit>
	</tr>
	<tr>
	    <odin:textEdit property="a0814" label="ѧУ����λ������" validator="a0814Length"></odin:textEdit>
	    <tags:PublicTextIconEdit property="a0827" label="��ѧרҵ���" onchange="setA0824Value" codetype="GB16835" readonly="true" />
	    <odin:NewDateEditTag property="a0804" label="��ѧʱ��"  maxlength="8"></odin:NewDateEditTag>		
	</tr>
	<tr>
		<odin:NewDateEditTag property="a0807" label="�ϣ��ޣ�ҵʱ��" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a0824" label="��ѧרҵ����" validator="a0824Length"></odin:textEdit>
		<odin:NewDateEditTag property="a0904" label="ѧλ����ʱ��" maxlength="8"></odin:NewDateEditTag>		
	</tr>
	<!--<tr>
		<odin:textEdit property="qrzxl" label="ȫ���ƽ�����ѧ��" readonly="true"></odin:textEdit>	
		<odin:textEdit property="qrzxw" label="ѧλ" readonly="true"></odin:textEdit>	
		<odin:textEdit property="qrzxlxx" label="ԺУϵ��רҵ" readonly="true"></odin:textEdit>	
	</tr>
	<tr>
		<odin:textEdit property="zzxl" label="��ְ�ƽ�����ѧ��" readonly="true"></odin:textEdit>	
		<odin:textEdit property="zzxw" label="ѧλ" readonly="true"></odin:textEdit>	
		<odin:textEdit property="zzxlxx" label="ԺУϵ��רҵ" readonly="true"></odin:textEdit>	
	</tr>
	-->
	<tr>
		<td colspan="8">
			<!--<odin:toolBar property="toolBar2" applyTo="tol1">
				<odin:fill></odin:fill>
				
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��"  isLast="true" id="delete" handler="deleteRow" icon="images/back.gif"></odin:buttonForToolBar>
			</odin:toolBar>
			-->
			<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" topBarId="toolBar2" url="/"   
			 height="210" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		     		<odin:gridDataCol name="a0899"/>
		     		<odin:gridDataCol name="a0800" />
			  		<odin:gridDataCol name="a0837" />
			  		<odin:gridDataCol name="a0801b" />
			   		<odin:gridDataCol name="a0901b" />
			   		<odin:gridDataCol name="a0814" />
			   		<odin:gridDataCol name="a0827" />			   		
			   		<odin:gridDataCol name="a0811" />
			   		<odin:gridDataCol name="a0804" />
			   		<odin:gridDataCol name="a0807" />
			   		<odin:gridDataCol name="a0904" />
			   		<odin:gridDataCol name="a0801a" />
			   		<odin:gridDataCol name="a0901a" />
			   		<odin:gridDataCol name="a0824" isLast="true"/>
			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="���" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="���" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
				  <odin:gridEditColumn header="ѧ��" dataIndex="a0801a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="ѧλ" dataIndex="a0901a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" edited="false" editor="text"/>
				  <odin:gridEditColumn header="רҵ" dataIndex="a0824" edited="false" editor="text" />
				  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>		
		</td>	
	</tr>
</table> --%>
</div>
<div id='btnSet'>
<odin:button text="�������ѧ��ѧλ" handler="SetBtn"></odin:button>
</div>
<div id='btnAdd'>
<odin:button text="��&nbsp;&nbsp;��" handler="AddBtn"></odin:button>
</div>
<div id='btnSave'>
<odin:button text="��&nbsp;&nbsp;��" handler="saveDegree1"></odin:button>
</div>
<div id='btnCancel'>
<odin:button text="ȡ&nbsp;&nbsp;��" handler="Cancel"></odin:button>
<odin:hidden property="a0899" title="���"/>
<script type="text/javascript">
function SetBtn(){
	radow.doEvent('sethighest.onclick');
}

function AddBtn(){
	radow.doEvent('degreesAddBtn.onclick');
}
function Cancel(){
	alert("demo");
}
Ext.onReady(function(){
	var firstload = true;
	var pgrid = Ext.getCmp("degreesgrid");
	var dstore = pgrid.getStore();
	dstore.on({  
       load:{  
           fn:function(){  
           	 if(firstload){
           		  $h.selectGridRow('degreesgrid',0);
           		  firstload = false;
             }
           }      
       },  
       scope:this      
   });  
});
Ext.onReady(function(){
	$h.applyFontConfig($h.spFeildAll.a08);
	
	/* if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a08);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var ext_gridobj = Ext.getCmp('degreesgrid');
		var gridobj = document.getElementById('forView_degreesgrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		cover_wrap1.className=  "divcover_wrap";
		cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
		
	} */
	
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	/* $h.fieldsDisabled(realParent.fieldsDisabled); */
	
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
Ext.onReady(function(){
	/* var side_resize=function(){
		 //document.getElementById('tol1').style.width = 100;	
		 Ext.getCmp('degreesgrid').setHeight(400);
		 Ext.getCmp('degreesgrid').setWidth(570); 
		 //document.getElementById('toolBar2').style.width = document.body.clientWidth ;
		 //document.getElementById('main').style.width = document.body.clientWidth-2;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
//		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	}
	side_resize();  
	window.onresize=side_resize;  */
	
	Ext.getCmp('degreesgrid').setWidth(570); 
	//ѧ�����롢ѧ�����ơ�ѧλ���롢ѧλ���� ������ǰ���Ӻ�ɫ*��
	document.getElementById('a0801bSpanId').innerHTML='<font color="red">*</font>ѧ������';
	document.getElementById('a0801aSpanId').innerHTML='<font color="red">*</font>ѧ������';
	document.getElementById('a0901bSpanId').innerHTML='<font color="red">*</font>ѧλ����';
	document.getElementById('a0901aSpanId').innerHTML='<font color="red">*</font>ѧλ����';
	
})

function open(a0000){
	$h.openPageModeWin('selectzgxlxw','pages.publicServantManage.SelectZGXLXW','ѡ�����ѧ��ѧλ',580,180,a0000,'<%=request.getContextPath()%>',null,null,null);
}
function openWin(id,url,title,width,height,param,ctx,parentScope,wincfg,isrmb){
	if(!parentScope){
		parentScope = $h.getTopParent();
	}
	
	var pWidth = parentScope.Ext.getBody().getViewSize().width;
	var pHeigth = parentScope.Ext.getBody().getViewSize().height
	
	if(!width||pWidth<width){
		if(isrmb){
			width = pWidth;
		}else{
			width = parseInt(pWidth*0.6);
		}
		
	}
	if(!height||pHeigth<height){
		if(isrmb){
			height = pHeigth;
		}else{
			height = parseInt(pHeigth*0.8);
		}
		
	}
	var wtop = (pHeigth-height)/2;
	var wleft = (pWidth-width)/2;
	if(wincfg&&wincfg.top){
		wtop = wtop>wincfg.top?wincfg.top:wtop;
	}
	var p = Ext.urlEncode({'subWinId':id,'subWinIdBussessId':param});//'&subWinId='+id+'&subWinIdBussessId='+param;
	url = ctx+'/radowAction.do?method=doEvent&pageModel='+url+'&'+p;
	var pjson = {id:id,title:title,maximizable:true, src:url,width:width,parentWinObj:parentScope,
			height:height,closeAction:'close',thisWin:window,param:param,closable:false,maximizable:false}
	Ext.apply(pjson,wincfg);
	var newWin_ = newWin(pjson);
	newWin_.show();
	if(!wincfg||(wincfg&&!wincfg.maximized)){//�������
		newWin_.setWidth(width);
		newWin_.setHeight(height);
		newWin_.setPosition(wleft,wtop);
	}
	return newWin_;

}

function saveDegree1(){
	document.getElementById("a0000").value = window.parent.frames["BaseAddPage_GB"].document.getElementById("a0000").value;
	//��ѧʱ��
	var a0804 = document.getElementById('a0804').value;	
	var a0804_1 = document.getElementById('a0804_1').value;	
	
	var text = dateValidateBeforeTady(a0804_1);
	if(a0804_1.indexOf(".") > 0){
		text = dateValidateBeforeTady(a0804);
	}
	if(text!==true){
		$h.alert('ϵͳ��ʾ','��ѧʱ�䣺' + text, null,400);
		return false;
	}
	
	//�ϣ��ޣ�ҵʱ��
	var a0807 = document.getElementById('a0807').value;	
	var a0807_1 = document.getElementById('a0807_1').value;	
	
	var text1 = dateValidateBeforeTady(a0807_1);
	if(a0807_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0807);
	}
	if(text1!==true){
		$h.alert('ϵͳ��ʾ','�ϣ��ޣ�ҵʱ�䣺' + text1, null,400);
		return false;
	}
	
	//ѧλ����ʱ��
	var a0904 = document.getElementById('a0904').value;	
	var a0904_1 = document.getElementById('a0904_1').value;	
	
	var text2 = dateValidateBeforeTady(a0904_1);
	if(a0904_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a0904);
	}
	if(text2!==true){
		$h.alert('ϵͳ��ʾ','ѧλ����ʱ�䣺' + text2, null,400);
		return false;
	}
	
	
	saveDegree();
����
}
//����Ҫ�У�����ɾ��
function nosystem(){
}

</script>

<div id="cover_wrap1"></div>
