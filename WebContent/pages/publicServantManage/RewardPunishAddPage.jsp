<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
#wzms{position: relative;top:10px;left:12px;}
#table1{position: relative;top:10px;left:10px;}
#table2{position: relative;margin-top:-40px;}
#btn{position: relative;top:-25px;left:35px}
#btn2{position: relative;left: 40px;}
#tol1{width:814px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A14")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A14")%>;
/* function deleteRow(){ 
	var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
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
//奖惩信息追加
function appendRewardPunish(){ 
	var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
	//var sr = getGridSelected("RewardPunishGrid");
	if(!sm.hasSelection()){
		alert("请选择一行数据！");
		return;
	}
	//alert(sm.lastActive);return;
	radow.doEvent('appendonclick',sm.lastActive+'');
}
function setA1404aValue(record,index){//奖惩名称
	Ext.getCmp('a1404a').setValue(record.data.value);
	/* var a1404b = record.data.key;
	if(a1404b.startsWith("02")){
		document.getElementById('chufa').style.visibility="visible";
	}else{
		document.getElementById('chufa').style.visibility="hidden";
	} */
}

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a1400 = record.data.a1400;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a1400+"&quot;)\">删除</a>";
}
function deleteRow2(a1400){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1400);
		}else{
			return;
		}		
	});	
}

</script>


<odin:toolBar property="toolBar5" applyTo="tol1">
<%--
				<odin:buttonForToolBar text="追加当前条" id="append" handler="appendRewardPunish" ></odin:buttonForToolBar><odin:separator/>
				<odin:buttonForToolBar text="全部替换" id="addAll"></odin:buttonForToolBar>
			
--%>
				<odin:fill></odin:fill>
				<%-- <odin:buttonForToolBar text="同步" id="synchro" icon="images/icon/reset.gif" tooltip="文字描述同步到任免表" /> --%>
				<odin:buttonForToolBar text="增加" id="RewardPunishAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<%-- <odin:buttonForToolBar text="删除" icon="images/back.gif" id="delete" handler="deleteRow" ></odin:buttonForToolBar> --%>
				<odin:buttonForToolBar text="保存" id="save22"  isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar>
				<!-- 	handler="saveReward"  保存旧方法 --> 
</odin:toolBar>
<div>
<div id="tol1" ></div>
<div id="wzms">
	<table>
		<tr>
			<odin:textarea property="a14z101" cols="80" rows="4" colspan="5" label="文字描述" validator="a14z101Length"></odin:textarea>
		</tr>
	</table>
</div>
<div id="table1">
	
	 <table>
	 	<tr>
	 		<td>
	 					<odin:grid property="RewardPunishGrid" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
			 height="200">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1400" />
			  		<odin:gridDataCol name="a1404b" />
			  		<odin:gridDataCol name="a1404a" />
			   		<odin:gridDataCol name="a1415" />
			   		<odin:gridDataCol name="a1414" />
			   		<odin:gridDataCol name="a1428" />			   		
			   		<odin:gridDataCol name="a1411a" />
			   		<odin:gridDataCol name="a1407" />
			   		<odin:gridDataCol name="a1424"/>	
			   		<odin:gridDataCol name="a1499"/>		   		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text"/>
				  <odin:gridEditColumn2 header="奖惩名称代码" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
				  <odin:gridEditColumn  header="奖惩名称"  dataIndex="a1404a" edited="false" editor="text" />
				  <odin:gridEditColumn2 header="受奖惩时职务层次" dataIndex="a1415" edited="false" codeType="ZB09" editor="select"/>
				  <odin:gridEditColumn2 header="批准机关级别" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
				  <odin:gridEditColumn2 header="批准机关性质" dataIndex="a1428" edited="false" codeType="ZB128" editor="select" hidden="true"/>
				  <odin:gridEditColumn header="批准机关" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
				  <odin:gridEditColumn header="批准日期" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
				  <odin:gridEditColumn header="撤销日期" dataIndex="a1424" edited="false" editor="text" maxLength="8" hidden="true"/>
				  <odin:gridEditColumn header="备注" dataIndex="a1499" edited="false" editor="text" maxLength="2000" hidden="true"/>
				   <odin:gridEditColumn header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
				</td>
				<td>
					<table id="table2">
						<tr id="btn" height="35px;">
							<td>
								<odin:button text="追加当前条" handler="appendRewardPunish" property="append"></odin:button>
							</td>
							<td id="btn2">
								<odin:button text="全部替换" property="addAll"></odin:button>
							</td>
						</tr>
						<tr height="35px;">
						<tags:PublicTextIconEdit property="a1404b" label="奖惩名称代码" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
						</tr>
						<tr height="35px;"><odin:textEdit property="a1404a" label="奖惩名称" required="true"></odin:textEdit></tr>
						<tr height="35px;"><tags:PublicTextIconEdit property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit></tr>
						<tr height="35px;"><odin:select2 property="a1414" label="批准机关级别"  codeType="ZB03"></odin:select2>	</tr>
						<tr height="35px;"><tags:PublicTextIconEdit property="a1428" label="批准机关性质" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit></tr>
						<tr height="35px;"><odin:textEdit property="a1411a" label="批准机关"></odin:textEdit></tr>
						<tr height="35px;"><odin:NewDateEditTag property="a1407" label="批准日期" maxlength="8" isCheck="true" required="true"></odin:NewDateEditTag></tr>
						<tr height="35px;"><odin:NewDateEditTag property="a1424" label="撤销日期" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
						<tr height="35px;" id="chufa"><odin:NewDateEditTag property="a1423" label="影响期至" maxlength="8" isCheck="true"></odin:NewDateEditTag></tr>
						<tr height="35px;">
							<odin:textarea property="a1499" label="备注" maxlength="2000" colspan="2" rows="3"></odin:textarea>
						</tr>
						<odin:hidden property="a1400" title="主键id" ></odin:hidden>
					</table>
				</td>
				</tr>
			</table>
</div>
<%-- <table cellspacing="2" width="100%" align="center">
	
	<tr>
		<odin:textarea property="a14z101" cols="110" rows="4" colspan="4" label="文字描述" validator="a14z101Length"></odin:textarea>
		<td><odin:button text="追加当前条" handler="appendRewardPunish" property="append"></odin:button> </td>
		<td><odin:button text="全部替换" property="addAll"></odin:button> </td>
	</tr>
	<tr>
		<td colspan="8">
			
			<odin:grid property="RewardPunishGrid" topBarId="toolBar5" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
			 height="200">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1400" />
			  		<odin:gridDataCol name="a1404b" />
			  		<odin:gridDataCol name="a1404a" />
			   		<odin:gridDataCol name="a1415" />
			   		<odin:gridDataCol name="a1414" />
			   		<odin:gridDataCol name="a1428" />			   		
			   		<odin:gridDataCol name="a1411a" />
			   		<odin:gridDataCol name="a1407" />
			   		
			   		
			   		
			   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text" />
				  <odin:gridEditColumn2 header="奖惩名称代码" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
				  <odin:gridEditColumn  header="奖惩名称"  dataIndex="a1404a" edited="false" editor="text" />
				  <odin:gridEditColumn2 header="受奖惩时职务层次" dataIndex="a1415" edited="false" codeType="ZB09" editor="select"/>
				  <odin:gridEditColumn2 header="批准机关级别" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
				  <odin:gridEditColumn2 header="批准机关性质" dataIndex="a1428" edited="false" codeType="ZB128" editor="select"/>
				  <odin:gridEditColumn header="批准机关" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
				  <odin:gridEditColumn header="批准日期" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
				  <odin:gridEditColumn header="奖惩撤销日期" dataIndex="a1424" edited="false" editor="text" isLast="true" maxLength="8"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a1404b" label="奖惩名称代码" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
		<odin:textEdit property="a1404a" label="奖惩名称" ></odin:textEdit>
		<tags:PublicTextIconEdit property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit>		
	</tr>
	<tr>
		<odin:select2 property="a1414" label="批准机关级别"  codeType="ZB03"></odin:select2>	
		<tags:PublicTextIconEdit property="a1428" label="批准机关性质" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit>
		<odin:textEdit property="a1411a" label="批准机关" ></odin:textEdit>		
	</tr>
	<tr>
		<odin:NewDateEditTag property="a1407" label="批准日期" maxlength="8" isCheck="true"></odin:NewDateEditTag>	
		<odin:NewDateEditTag property="a1424" label="奖惩撤销日期" maxlength="8" isCheck="true" ></odin:NewDateEditTag>	
	</tr>
	<tr>
		<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>
		<odin:hidden property="a1400" title="主键id" ></odin:hidden>
		<odin:hidden property="codevalueparameter" title="没用"/>
	</tr>
</table> --%>
</div>
<script type="text/javascript">

Ext.onReady(function(){
	var firstload = true;
	var pgrid = Ext.getCmp("RewardPunishGrid");
	var dstore = pgrid.getStore();
	dstore.on({  
       load:{  
           fn:function(){  
           	 if(firstload){
           	 	$h.selectGridRow('RewardPunishGrid',0);
           	 	firstload = false;
           	 }
           }      
       },  
       scope:this      
   });  
});
Ext.onReady(function(){
	$h.applyFontConfig($h.spFeildAll.a14);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a14);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var ext_gridobj = Ext.getCmp('RewardPunishGrid');
		var gridobj = document.getElementById('forView_RewardPunishGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className="divcover_wrap";
		cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className= "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
		
	}
	
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled);
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
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
		 //document.getElementById('tol1').style.width = document.body.clientWidth;	
	     Ext.getCmp('RewardPunishGrid').setHeight(330);
		 Ext.getCmp('RewardPunishGrid').setWidth(507); 
		 //document.getElementById('main').style.width = document.body.clientWidth-2;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
//		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	}
	side_resize();  
	window.onresize=side_resize; 
});


function save(){
	
	//文字描述不可超过1000字 
	var a14z101 = document.getElementById('a14z101').value;	
	
	if(a14z101.length>1000) {
		Ext.Msg.alert("提示信息", "文字描述长度超过限制：1000字以内！");
	　　return false; 
	}
	
	//奖惩名称不可超过20个字
	/* var a1404a = document.getElementById('a1404a').value;
	if(a1404a.length>20) {
		Ext.Msg.alert("提示信息", "奖惩名称长度超过限制：20字以内！");
	　　return false; 
	} */
	
	//批准日期
	var a1407 = document.getElementById('a1407').value;	
	var a1407_1 = document.getElementById('a1407_1').value;	
	
	/* if(!a1407_1){
		$h.alert('系统提示','批准日期不能为空！', null,200);
		return false;
	} */
	
	var text1 = dateValidateBeforeTady(a1407_1);
	if(a1407_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a1407);
	}
	if(text1!==true){
		$h.alert('系统提示','批准日期：' + text1, null,400);
		return false;
	}
	
	//撤销日期 
	var a1424 = document.getElementById('a1424').value;	
	var a1424_1 = document.getElementById('a1424_1').value;	
	
	var text2 = dateValidateBeforeTady(a1424_1);
	if(a1424_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a1424);
	}
	if(text2!==true){
		$h.alert('系统提示','撤销日期：' + text2, null,400);
		return false;
	}
	
	
	radow.doEvent("save.onclick");
　　
}

function lockINFO(){
	Ext.getCmp("RewardPunishAddBtn").disable(); 
	Ext.getCmp("save22").disable(); 
	Ext.getCmp("append").disable(); 
	Ext.getCmp("addAll").disable();  
	Ext.getCmp("RewardPunishGrid").getColumnModel().setHidden(10,true);
}

</script>

<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>