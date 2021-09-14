<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>

<style>
#wzms{position: relative;top:10px;left:15px;}
#grid{position: relative;top:10px;left:15px;}
</style>
<script type="text/javascript">
/* 	function deleteRow(){ 
	var sm = Ext.getCmp("AssessmentInfoGrid").getSelectionModel();
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


function save(){
	
	//文字描述不可超过1000字 
	var a15z101 = document.getElementById('a15z101').value;	
	
	if(a15z101.length>1000) {
		
		Ext.Msg.alert("提示信息", "文字描述长度超过限制：1000字以内！");
	　　return false; 
	} 
	
	//判断调整序号是否合法
	var a1527  = document.getElementById('a1527').value;				//选择年度个数
	
	if (!(/(^[0-9]\d*$)/.test(a1527))) { 
　　　　
		Ext.Msg.alert("提示信息", "选择年度个数不是自然数！");
　　　　return false; 
　　}else { 
		
		radow.doEvent("save.onclick");
　　} 
	
}

function changedispaly(obj){
	var choose = Ext.getCmp('a0191').getValue();	
	if(choose){
		document.getElementById('choose').style.visibility='visible';
		document.getElementById('a15z101').readOnly = true;
	}else{
		document.getElementById('a15z101').readOnly = false;
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
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a1500+"&quot;)\">删除</a>";
}
function deleteRow2(a1500){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1500);
		}else{
			return;
		}		
	});	
}
</script>


<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<%-- <odin:buttonForToolBar text="删除" icon="images/back.gif" cls="x-btn-text-icon" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
				<%--
				<odin:buttonForToolBar text="批量保存" id="saveAll" cls="x-btn-text-icon" icon="images/save.gif" ></odin:buttonForToolBar>
				--%>
				<odin:buttonForToolBar text="增加" id="AssessmentInfoAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
				
				<odin:buttonForToolBar text="保存" id="save22" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar>
</odin:toolBar>
<div>
<div id="btnToolBarDiv" align="center"></div>
<odin:hidden property="a1500" title="主键id" ></odin:hidden>
<div id="wzms">
	<table>
		<tr>
			<td><odin:textarea property="a15z101"  cols="85" rows="4" label="文字描述" validator="a15z101Length"></odin:textarea></td>
			<td>
				<div id="choose" style="visibility: hidden;">
					<table>
					<tr>
					<td  align="right" valign="middle">
				  		<label for="a1527" style="font-size: 12px;" id="a1527SpanId">选择年度个数 </label>
					</td>
					<td>
<%-- <odin:numberEdit property="a1527" label="&nbsp;&nbsp;&nbsp;选择年度个数" size="4" value="3"></odin:numberEdit> --%>
						<input id="a1527" name=""/>
					</td></tr>
					</table>
				</div>
			</td>
			<td>
				<span>&nbsp;&nbsp;</span>
			</td>
			<td id="td"><odin:checkbox property="a0191" label="与列表关联" onclick="changedispaly(this)"></odin:checkbox></td>
		
		</tr>
	</table>
</div>
<div id="grid">
	<table>
		<tr>
			<td>
				<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="200">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1500" />
			  		<odin:gridDataCol name="a1521" />
			   		<odin:gridDataCol name="a1517"/>			   		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="年度" dataIndex="a1521" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="考核结论"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"  />
				   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>	
			</td>
			<td>
				<table>
					<tr height="50">
						<odin:select2 property="a1521" label="考核年度" required="true" maxlength="4" multiSelect="true" ></odin:select2>
					</tr>
					<tr height="50">
						<tags:PublicTextIconEdit property="a1517" label="考核结论" required="true" codetype="ZB18" readonly="true" ></tags:PublicTextIconEdit>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<%-- <table cellspacing="2" width="98%" align="left">
	<tr>
		<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>
		
	</tr>
	<tr>
		<td><odin:textarea property="a15z101" cols="70" rows="4" colspan="4" label="文字描述" validator="a15z101Length"></odin:textarea></td>
		
		<td><div id="choose" style="visibility: hidden;">
		<table><odin:numberEdit property="a1527" label="选择年度个数" size="6"></odin:numberEdit></table>
		</div></td>
		
		<td><odin:checkbox property="a0191" label="与列表关联" onclick="changedispaly(this)"></odin:checkbox></td>
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
				  <odin:gridEditColumn header="年度" dataIndex="a1521" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="考核结论类别"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
	<tr>
	    <td><odin:select2 property="a1521" label="考核年度" required="true" maxlength="4" multiSelect="true" ></odin:select2></td>
		<!-- <td><odin:numberEdit property="a1521" label="考核(开始)年度" required="true" minValue="1900" maxlength="4"></odin:numberEdit></td> 
		<td><odin:numberEdit property="yearEnd" label="结束年度" minValue="1900" maxlength="4"></odin:numberEdit></td> -->
		<td><tags:PublicTextIconEdit property="a1517" label="考核结论类别" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit></td>	
	</tr>
</table> --%>
</div>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A15")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A15")%>;
Ext.onReady(function(){
	<%-- <odin:numberEdit property="a1527" label="&nbsp;&nbsp;&nbsp;选择年度个数" size="4" value="3"></odin:numberEdit> --%>
	 new Ext.ux.form.SpinnerField({
         fieldLabel: '选择年度个数',
         minValue: 0,
     	 maxValue: 100,
     	 decimalPrecision: 0,
     	 applyTo:"a1527",
     	 width:50,
     	 value:3
     })
	
	
	
	
	
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
	var side_resize=function(){
		 document.getElementById('btnToolBarDiv').style.width = document.body.clientWidth;	
//		 Ext.getCmp('AssessmentInfoGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_AssessmentInfoGrid'))[0]-4);
		 Ext.getCmp('AssessmentInfoGrid').setWidth(535); 
		 //document.getElementById('main').style.width = document.body.clientWidth-2;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
//		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	}
	side_resize();  
	window.onresize=side_resize; 
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled);
	for(var i=0;i<fieldsDisabled.length;i++){
		if(fieldsDisabled[i].split("_")[1]=="a1527"){
			var formobj = null;
			if(formobj = document.getElementById("a1527")){
				formobj.readOnly = "true";
				$("#a1527").addClass('bgclor');
			}
			if(formobj = document.getElementById("a0191")){
				$(formobj).addClass('bgclor');
				formobj.onclick = function(){return false;};
			}
		}
	}
	//对信息集明细的权限控制，是否可以查看
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	/* if(selectDisabled!=""){
		var div = document.createElement("div");
		var pNode = document.getElementById("AssessmentInfoGrid").parentNode;
		pNode.style.position = "relative";
		div.style.border="1px solid rgb(192,192,192)";
		div.style.width = document.getElementById("AssessmentInfoGrid").offsetWidth;
		div.style.height = document.getElementById("AssessmentInfoGrid").offsetHeight;
		div.style.position = "absolute";
		div.style.left = '0px';
		div.style.top = '0px';
		div.style.backgroundImage = imgdata;
		div.style.backgroundRepeat = "no-repeat";
		div.style.backgroundColor = "white";
		div.style.backgroundPosition = "center";
		pNode.appendChild(div);
	} */
	for(var i=0;i<selectDisabled.length;i++){
		if(selectDisabled[i].split("_")[1]=="a1527"){
			var formobj = null;
			if(formobj = document.getElementById("a1527")){
				var div = document.createElement("div");
				var pNode = document.getElementById("a1527").parentNode;
				pNode.style.position = "relative";
				div.style.border="1px solid rgb(192,192,192)";
				div.style.width = document.getElementById("a1527").offsetWidth;
				div.style.height = document.getElementById("a1527").offsetHeight;
				div.style.position = "absolute";
				div.style.left = '0px';
				div.style.top = '0px';
				div.style.backgroundImage = imgdata;
				div.style.backgroundRepeat = "no-repeat";
				div.style.backgroundColor = "white";
				div.style.backgroundPosition = "center";
				pNode.appendChild(div);
				formobj.readOnly = "true";
				$("#a1527").addClass('bgclor');
			}
			if(formobj = document.getElementById("a0191")){
				var div = document.createElement("div");
				var pNode = document.getElementById("a0191").parentNode;
				pNode.style.position = "relative";
				div.style.border="1px solid rgb(192,192,192)";
				div.style.width = document.getElementById("a0191").offsetWidth;
				div.style.height = document.getElementById("a0191").offsetHeight;
				div.style.position = "absolute";
				div.style.left = $("#a0191").position().left;
				div.style.top = $("#a0191").position().top;
		　　　　div.style.backgroundImage = imgdata;
				div.style.backgroundRepeat = "no-repeat";
				div.style.backgroundColor = "white";
				div.style.backgroundPosition = "center";
		　　　　pNode.appendChild(div);
				$(formobj).addClass('bgclor');
				formobj.onclick = function(){return false;};
				
			}
		}
	}
});

function lockINFO(){
	Ext.getCmp("AssessmentInfoAddBtn").disable(); 
	Ext.getCmp("save22").disable(); 
	Ext.getCmp("AssessmentInfoGrid").getColumnModel().setHidden(4,true);
}
</script>


<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>