<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<%-- <%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel" %> --%>
<%@include file="/comOpenWinInit2.jsp" %>
<!-- <script type="text/javascript" src="basejs/helperUtil.js"></script> -->
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script> --%>
<!-- <script type="text/javascript" src="js/lengthValidator.js"></script> -->
<style>
#tab__tab1{
		margin-left:425;
	}
</style>
<div>
	<odin:hidden property="qvid"/>
	<odin:hidden property="qrysql"/>
</div> 
 <odin:tab id="tab">
	<odin:tabModel>
		<odin:tabItem title="条&nbsp;件&nbsp;设&nbsp;置" id="tab1" ></odin:tabItem>
		<odin:tabItem title="已定义方案 " id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1">
<div style="float: left;position: relative;">
	<table width="240" cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
	</div>
	<div>
		<iframe id="iframeCondition" width="100%" height="500px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.ViewCondition&paramurl=UserDefinedQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
	</div>
	<div>
		<table>
			<tr width="1000px">
				<odin:textEdit property="queryName" label="方案名称"  width="593" ></odin:textEdit>
			</tr>
		</table>
	</div>
	<div style="width:1000;text-align:right;">
		<table style="width:100%;" border="0">
			<tr>
				<td style="text-align:right;width:768;text-align:right;" >
					<%-- <odin:button text="保存方案" property="btn4" handler="udfbtn4func"></odin:button> --%>
					<input id="btn4" class="yellowbutton" type="button" value="保存方案" onclick="udfbtn4func()" style="width: 60;"/>
				</td>
				<td width="100px"></td>
				<td style="text-align:right;width:132;text-align:left;" >
					<%-- <odin:button text="&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;" property="btn5" handler="udfbt5func"></odin:button> --%>
					<input id="btn5" class="yellowbutton" type="button" value="查&nbsp;&nbsp;&nbsp;&nbsp;询" onclick="udfbt5func()" style="width: 60;"/>
				</td>
			</tr>
		</table>
	</div>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
<odin:editgrid property="viewListGrid" bbarId="pageToolBar"  
pageSize="20" width="400" height="550"url="/" rowDbClick="rowqvDbClick" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="qvid" />
		<odin:gridDataCol name="chinesename" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn header="主键" width="0"  dataIndex="qvid"  edited="false" editor="text" align="left" />
		<odin:gridEditColumn header="方案名称" width="440"  dataIndex="chinesename"  edited="false" editor="text" align="left"/>
		<odin:gridEditColumn header="操作" editor="text" dataIndex="qvid" width="100" align="center" renderer="dodetail"  isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
<%-- <odin:tabCont itemIndex="tab2">
	<iframe id="iframePreview" width="100%" height="605px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.PreviewQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
</odin:tabCont> --%>
</odin:tab>
<script type="text/javascript">
Ext.onReady(function(){
	radow.doEvent('viewListGrid.dogridquery');
});


 Ext.onReady(function(){
	 // document.frames["iframeCondition"].document.getElementById('tabcon1').style.height='460px';
	 //设置信息集列表高度
	 var gird_domtable= document.frames["iframeCondition"].Ext.getCmp('tableList2Grid');//获取grid对象
	 gird_domtable.setHeight(460);
	 //设置信息项列表高度
	 var gird_domcode= document.frames["iframeCondition"].Ext.getCmp('codeList2Grid');//获取grid对象
	 gird_domcode.setHeight(205);
	 var gird_domcode1= document.frames["iframeCondition"].Ext.getCmp('codeList2Grid1');//获取grid对象
	 gird_domcode1.setHeight(205);
	 //conditionName8 设置添加条件div高度
	 document.frames["iframeCondition"].document.getElementById('conditionName8').style.height='120px';
	 //设置删除按钮 td元素高度
	 document.frames["iframeCondition"].document.getElementById('iddeletecondition').style.height='60px';
	 //设置全部删除按钮 td元素高度
	 document.frames["iframeCondition"].document.getElementById('iddeleteallcondition').style.height='60px';
	 //设置保存预览按钮，隐藏 
	 document.frames["iframeCondition"].document.getElementById('divsave4').style.display='none';
	 //设置tab页高度
	 Ext.getCmp('tab').setHeight(605);
	 
	 
	 
	 
	 var treediv = document.getElementById("tree-div");
	 var viewSize = Ext.getBody().getViewSize();
	 treediv.style.height = viewSize.height-60;
	 
});
 function udfbtn4func(){//保存方案名称
	 //校验信息集是否被选中
	var gridtable=document.frames["iframeCondition"].Ext.getCmp('tableList2Grid');
	var storetable=gridtable.store;
	var flagtable=false;
	for(i=0;i<storetable.totalLength;i++){
		var temp=gridtable.store.getAt(i).get("checked")
		if(temp==true){
			flagtable=true;
		}
	}
	if(flagtable==false){
		alert('请选择信息集!');
		return 2;
	}
	 //校验信息项是否被选中
	var gridcode=document.frames["iframeCondition"].Ext.getCmp('codeList2Grid1');
	var storecode=gridcode.store;
	/* var flagcode=false;
	for(i=0;i<storecode.totalLength;i++){
		var temp=gridcode.store.getAt(i).get("checked")
		if(temp==true){
			flagcode=true;
		}
	} */
	if(storecode.totalLength<1){
		alert('请选择信息项!');
		return 3;
	}
	radow.doEvent("saveschemename");
	//document.getElementById('iframeCondition').contentWindow.getSqlParent('qrysql','saveschemename');
	
 }
function savescheall(qvid){
	document.getElementById('iframeCondition').contentWindow.saveschemeall(qvid);
	 
 }
 
 function udfbt5func(){
	 //window.dialogArguments['window'].clearFields();
	 var qvid=document.getElementById('qvid').value;
	 var querysql=document.getElementById('iframeCondition').contentWindow.getQuerysql()
	 if(querysql==null||querysql==""||querysql==" "||querysql=="null"){
		 document.getElementById('queryName').value='默认方案';
		 //命令一个ID
		 document.getElementById('qvid').value='402881ee6af96a5d016af96e62cb0005';
		 var rtn4=udfbtn4func();
		 if('2'==rtn4){
			 return;
		 }
		 if('3'==rtn4){
			 return;
		 }
		//生成querysql
		 querysql=document.getElementById('iframeCondition').contentWindow.getQuerysql()
		 document.getElementById('queryName').value='';
		 
		 var setT=setTimeout(function(){
			 udfbt5func1();
		 },500);
		 return;
	 }
	// alert(qvid);
	// alert(document.getElementById('iframeCondition').contentWindow.getQuerysql());
	var pWindow=window.dialogArguments['window'];
	pWindow.document.getElementById('qvid').value=qvid;
	Ext.Ajax.request({
		method: 'POST',
	    async: true,
	    params : {'qvid':qvid},
	    timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=query_config",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				window.close();
				pWindow.document.getElementById('sql').value='';
				pWindow.document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
				//pWindow.changeType(1);
				pWindow.Ext.getCmp('peopleInfoGrid').show();
				/* pWindow.document.getElementById("pictable").style.display='none';
				pWindow.document.getElementById("picdata").style.display='none';
				pWindow.document.getElementById("btd").style.display='block'; */
				pWindow.resetCM(cfg.elementsScript);
			}else{
				Ext.Msg.alert('系统提示',cfg.mainMessage)
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
 }
 
 function udfbt5func1(){
	 window.dialogArguments['window'].clearFields();
	 var qvid=document.getElementById('qvid').value;
	 var querysql=document.getElementById('iframeCondition').contentWindow.getQuerysql()
	// alert(qvid);
	// alert(document.getElementById('iframeCondition').contentWindow.getQuerysql());
	var pWindow=window.dialogArguments['window'];
	pWindow.document.getElementById('qvid').value=qvid;
	Ext.Ajax.request({
		method: 'POST',
	    async: true,
	    params : {'qvid':qvid},
	    timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=query_config",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				window.close();
				pWindow.document.getElementById('sql').value='';
				pWindow.document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
				//pWindow.changeType(1);
				pWindow.Ext.getCmp('peopleInfoGrid').show();
				/* pWindow.document.getElementById("pictable").style.display='none';
				pWindow.document.getElementById("picdata").style.display='none';
				pWindow.document.getElementById("btd").style.display='block'; */
				pWindow.resetCM(cfg.elementsScript);
			}else{
				Ext.Msg.alert('系统提示',cfg.mainMessage)
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
 }
 
 
 function rowqvDbClick(grid,rowIndex,colIndex,event){//已建视图行双击事件
	   	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	   	//弹出窗口
	   	radow.doEvent("savetoqv",record.get("qvid"));
	}

//操作按钮
 function dodetail(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:deleteV('" + value + "')\">删除</a>&nbsp;"
		
	}
//删除
function deleteV(value){
	radow.doEvent('doDel',value);
	
}

	
</script>
<%@include file="/pages/customquery/otjs.jsp" %>