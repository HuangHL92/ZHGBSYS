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
		Ext.Msg.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
} */
function setA0801aValue(record,index){//学位
	Ext.getCmp('a0801a').setValue(record.data.value);
}
function setA0901aValue(record,index){//学历
	Ext.getCmp('a0901a').setValue(record.data.value);
}
function setA0824Value(record,index){//专业
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
//学历学位输出设置
function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	var sr = getGridSelected(gridName);
	if(!sr){
		return;
	}
	var msg='';
	if(sr.data.a0899==='true'||sr.data.a0899===true){
		msg = '取消该记录后,该学历学位将不能输出<br/>确定要取消输出该记录吗?';
	}else{
		msg = '选择该记录后，该学历学位将输出<br/>确定要选择输出该记录吗?';
	}
	$h.confirm('系统提示',msg,220,function(id){
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
		return "删除";
	} */
	var fieldsDisabled = <%=InfoComWindowPageModel.getInfoData()%>;
	if(fieldsDisabled==''||fieldsDisabled==undefined){
		return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">删除</a>";
	}
	var datas = fieldsDisabled.toString().split(',');
	for(var i=0;i<datas.length;i++){
		if(datas[i]==("a0801a")||datas[i]==("a0801b")||datas[i]==("a0901a")||datas[i]==("a0901b")||datas[i]==("a0804")||datas[i]==("a0807")||datas[i]==("a0904")||datas[i]==("a0814")||datas[i]==("a0824")||datas[i]==("a0827")||datas[i]==("a0837")||datas[i]==("a0811")||datas[i]==("a0898")){
			  Ext.getCmp("sethighest").setDisabled(true);
			  Ext.getCmp("degreesAddBtn").setDisabled(true);
			return "<u style=\"color:#D3D3D3\">删除</u>"; 
		}
		
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a0800+"&quot;)\">删除</a>";
}
function deleteRow2(a0800){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0800);
		}else{
			return;
		}		
	});	
}
</script>


<%--<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>--%>
<div>
<odin:hidden property="a0800" title="主键id" ></odin:hidden>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="a0834" title="最高学历标志" />
<odin:hidden property="a0835" title="最高学位标志" />
<input type="reset" name="reset" id="resetbtn" style="display: none;" />
<table>
	<tr><div style="height: 20px"></div></tr>
	<tr>
		<td>
			<table>
				<tr><odin:select2 property="a0837" label="教育类别" required="true" codeType="ZB123"></odin:select2></tr>
				<tr><tags:PublicTextIconEdit property="a0801b" label="学历代码" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
				<tr><odin:textEdit property="a0801a" label="学历名称" validator="a0801aLength"></odin:textEdit></tr>
				<tr><odin:numberEdit property="a0811" label="学制年限(年)" maxlength="3"></odin:numberEdit></tr>
				<tr><tags:PublicTextIconEdit property="a0901b" label="学位代码" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
				<tr><odin:textEdit property="a0901a" label="学位名称" validator="a0901aLength"></odin:textEdit></tr>
				<tr> <odin:textEdit property="a0814" label="学校（单位）名称" validator="a0814Length"></odin:textEdit></tr>
				<tr><tags:PublicTextIconEdit property="a0827" label="所学专业类别" onchange="setA0824Value" codetype="GB16835" readonly="true"/></tr>
				<tr><odin:textEdit property="a0824" label="所学专业名称" validator="a0824Length"></odin:textEdit></tr>
				<tr><odin:NewDateEditTag property="a0804" label="入学时间"  maxlength="8"></odin:NewDateEditTag>	</tr>
				<tr><odin:NewDateEditTag property="a0807" label="毕（肄）业时间" maxlength="8"></odin:NewDateEditTag></tr>
				<tr><odin:NewDateEditTag property="a0904" label="学位授予时间" maxlength="8"></odin:NewDateEditTag></tr>
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
				  <odin:gridEditColumn header="输出" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="类别" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
				  <odin:gridEditColumn header="学历" dataIndex="a0801a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="学位" dataIndex="a0901a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="学校及院系" dataIndex="a0814" edited="false" editor="text"/>
				  <odin:gridEditColumn header="专业" dataIndex="a0824" edited="false" editor="text" />
				  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>		
		</td>
	</tr>
</table>
<%-- <table cellspacing="4" width="100%" align="center">
	<tr>
		<odin:select2 property="a0837" label="教育类别" required="true" codeType="ZB123"></odin:select2>
		<tags:PublicTextIconEdit property="a0801b" label="学历代码" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
		<tags:PublicTextIconEdit property="a0901b" label="学位代码" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit>
	</tr>
	<tr>
	    <odin:numberEdit property="a0811" label="学制年限(年)" maxlength="3"></odin:numberEdit>
	    <odin:textEdit property="a0801a" label="学历名称" validator="a0801aLength"></odin:textEdit>
	    <odin:textEdit property="a0901a" label="学位名称" validator="a0901aLength"></odin:textEdit>
	</tr>
	<tr>
	    <odin:textEdit property="a0814" label="学校（单位）名称" validator="a0814Length"></odin:textEdit>
	    <tags:PublicTextIconEdit property="a0827" label="所学专业类别" onchange="setA0824Value" codetype="GB16835" readonly="true" />
	    <odin:NewDateEditTag property="a0804" label="入学时间"  maxlength="8"></odin:NewDateEditTag>		
	</tr>
	<tr>
		<odin:NewDateEditTag property="a0807" label="毕（肄）业时间" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a0824" label="所学专业名称" validator="a0824Length"></odin:textEdit>
		<odin:NewDateEditTag property="a0904" label="学位授予时间" maxlength="8"></odin:NewDateEditTag>		
	</tr>
	<!--<tr>
		<odin:textEdit property="qrzxl" label="全日制教育：学历" readonly="true"></odin:textEdit>	
		<odin:textEdit property="qrzxw" label="学位" readonly="true"></odin:textEdit>	
		<odin:textEdit property="qrzxlxx" label="院校系及专业" readonly="true"></odin:textEdit>	
	</tr>
	<tr>
		<odin:textEdit property="zzxl" label="在职制教育：学历" readonly="true"></odin:textEdit>	
		<odin:textEdit property="zzxw" label="学位" readonly="true"></odin:textEdit>	
		<odin:textEdit property="zzxlxx" label="院校系及专业" readonly="true"></odin:textEdit>	
	</tr>
	-->
	<tr>
		<td colspan="8">
			<!--<odin:toolBar property="toolBar2" applyTo="tol1">
				<odin:fill></odin:fill>
				
				<odin:buttonForToolBar text="保存" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除"  isLast="true" id="delete" handler="deleteRow" icon="images/back.gif"></odin:buttonForToolBar>
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
				  <odin:gridEditColumn header="输出" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="类别" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
				  <odin:gridEditColumn header="学历" dataIndex="a0801a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="学位" dataIndex="a0901a" edited="false" editor="text"/>
				  <odin:gridEditColumn header="学校及院系" dataIndex="a0814" edited="false" editor="text"/>
				  <odin:gridEditColumn header="专业" dataIndex="a0824" edited="false" editor="text" />
				  <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>		
		</td>	
	</tr>
</table> --%>
</div>
<div id='btnSet'>
<odin:button text="设置最高学历学位" handler="SetBtn"></odin:button>
</div>
<div id='btnAdd'>
<odin:button text="新&nbsp;&nbsp;增" handler="AddBtn"></odin:button>
</div>
<div id='btnSave'>
<odin:button text="保&nbsp;&nbsp;存" handler="saveDegree1"></odin:button>
</div>
<div id='btnCancel'>
<odin:button text="取&nbsp;&nbsp;消" handler="Cancel"></odin:button>
<odin:hidden property="a0899" title="输出"/>
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
	
	//对信息集明细的权限控制，是否可以维护 
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
	//学历代码、学历名称、学位代码、学位名称 ，文字前增加红色*号
	document.getElementById('a0801bSpanId').innerHTML='<font color="red">*</font>学历代码';
	document.getElementById('a0801aSpanId').innerHTML='<font color="red">*</font>学历名称';
	document.getElementById('a0901bSpanId').innerHTML='<font color="red">*</font>学位代码';
	document.getElementById('a0901aSpanId').innerHTML='<font color="red">*</font>学位名称';
	
})

function open(a0000){
	$h.openPageModeWin('selectzgxlxw','pages.publicServantManage.SelectZGXLXW','选择最高学历学位',580,180,a0000,'<%=request.getContextPath()%>',null,null,null);
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
	if(!wincfg||(wincfg&&!wincfg.maximized)){//不是最大化
		newWin_.setWidth(width);
		newWin_.setHeight(height);
		newWin_.setPosition(wleft,wtop);
	}
	return newWin_;

}

function saveDegree1(){
	document.getElementById("a0000").value = window.parent.frames["BaseAddPage_GB"].document.getElementById("a0000").value;
	//入学时间
	var a0804 = document.getElementById('a0804').value;	
	var a0804_1 = document.getElementById('a0804_1').value;	
	
	var text = dateValidateBeforeTady(a0804_1);
	if(a0804_1.indexOf(".") > 0){
		text = dateValidateBeforeTady(a0804);
	}
	if(text!==true){
		$h.alert('系统提示','入学时间：' + text, null,400);
		return false;
	}
	
	//毕（肄）业时间
	var a0807 = document.getElementById('a0807').value;	
	var a0807_1 = document.getElementById('a0807_1').value;	
	
	var text1 = dateValidateBeforeTady(a0807_1);
	if(a0807_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0807);
	}
	if(text1!==true){
		$h.alert('系统提示','毕（肄）业时间：' + text1, null,400);
		return false;
	}
	
	//学位授予时间
	var a0904 = document.getElementById('a0904').value;	
	var a0904_1 = document.getElementById('a0904_1').value;	
	
	var text2 = dateValidateBeforeTady(a0904_1);
	if(a0904_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a0904);
	}
	if(text2!==true){
		$h.alert('系统提示','学位授予时间：' + text2, null,400);
		return false;
	}
	
	
	saveDegree();
　　
}
//必须要有，不能删除
function nosystem(){
}

</script>

<div id="cover_wrap1"></div>
