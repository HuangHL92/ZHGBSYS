<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<style>
#tab__tab1{
		margin-left:425;
	}
	body {
	overflow: hidden;
}
</style>

<odin:toolBar property="bbarid">
	<odin:fill />
	<odin:buttonForToolBar text="清空" icon="commform/img/icon/clearall.gif" handler="clearFN" id="clearFN" />
	<odin:buttonForToolBar text="保存至方案" icon="images/icon/save.gif" handler="saveFN" isLast="true" id="saveFN" />
</odin:toolBar>
<odin:toolBar property="bbarid2">
	<odin:fill />
	<odin:buttonForToolBar text="删除方案" icon="images/icon/delete.gif" handler="delFN" isLast="true" id="delFN" />
</odin:toolBar>

<div>
	<odin:hidden property="qvid"/>
	<odin:hidden property="qrysql"/>
</div> 
 <odin:tab id="tab">
	<odin:tabModel>
		<odin:tabItem title="条&nbsp;件&nbsp;设&nbsp;置" id="tab1" ></odin:tabItem>
		<odin:tabItem title="已定义方案 " id="tab2"  ></odin:tabItem>
		<odin:tabItem title="&nbsp;&nbsp;&nbsp;预&nbsp;&nbsp;览&nbsp;&nbsp;&nbsp;&nbsp;" id="tab3" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1">
	<div>
		<iframe id="iframeCondition" width="100%" height="525px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ldbj.TPFNCD&paramurl=UserDefinedQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
	</div>
	<div style="float: left;margin-top: 10px;">
		<table>
			<tr style="width: 700px;">
				<odin:textEdit property="queryName" label="条件名称"  width="628" ></odin:textEdit>
				<%-- <odin:select2 property="yjtype" label="条件类型" canOutSelectList="false"
					data="['0','无'],['1','红色预警'],['2','黄色预警'],['3','绿色预警'],['4','优秀']" value="0"/> --%>
			</tr>
		</table>
	</div>
	<div style="width:200;text-align:right;float: left;margin-top: 9px;margin-left: 20px;">
		<table   >
			<tr>
				<td style="text-align:right; text-align:right;" >
					<input id="btn4" class="yellowbutton" type="button" value="保存条件" onclick="udfbtn4func()" style="width: 60;"/>
				</td>
				 
				<td style="text-align:right; text-align:right;padding-left: 20px;" >
					<input id="btn5" class="yellowbutton" type="button" value="预&nbsp;&nbsp;&nbsp;&nbsp;览" onclick="previewFunc()" style="width: 60;"/>
				</td>
			</tr>
		</table>
	</div>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
<table style="width: 100%;">
<tr>
<td style="width: 20%;">
	<odin:editgrid property="viewListGrid" bbarId="bbarid"  forceNoScroll="true" autoFill="true"
	pageSize="200"  height="545"url="/" width="50" rowDbClick="rowqvDbClick" > 
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="qvid" />
			<odin:gridDataCol name="yjtype" />
			<odin:gridDataCol name="pcheck" />
			<odin:gridDataCol name="chinesename" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn2  header="selectall" width="40"
								editor="checkbox" dataIndex="pcheck" edited="true" 
								hideable="false" gridName="gridcq" 
								checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" menuDisabled="true" /> 
			<odin:gridEditColumn header="条件名称" width="440"  dataIndex="chinesename"  edited="false" editor="text" align="left" />
			<odin:gridEditColumn2 header="条件类型" width="100"  dataIndex="yjtype" renderer="yjtyperenderer" edited="false" editor="text" align="left" isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid>
</td>
<td style="width: 10px;">&nbsp;</td>
<td style="width: 20%;">
	<odin:editgrid property="viewListGrid2" width="50" bbarId="bbarid2"  forceNoScroll="true" autoFill="true"
	pageSize="200"  height="545"url="/"  >
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="yf000" />
			<odin:gridDataCol name="yf004" />
			<odin:gridDataCol name="yf002" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="方案名称" width="440"  dataIndex="yf002"  edited="false" editor="text" align="left" />
			<odin:gridEditColumn2 header="类型" width="100"  dataIndex="yf004"  edited="false" editor="select" align="left" selectData="['1','人员预警'],['2','人员筛选']" isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid>
</td>
</tr>
</table>
</odin:tabCont>
<odin:tabCont itemIndex="tab3">
	<iframe id="iframePreview" width="100%" height="575px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ldbj.PreviewQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
</odin:tabCont>
</odin:tab>
<odin:hidden property="yf000"/>
<odin:hidden property="yf002"/>
<odin:hidden property="yf004"/>
<odin:hidden property="fnlb2"/>
<script type="text/javascript">

function setPageConfig(){
	//设置信息集列表高度
	 var gird_domtable= document.getElementById('iframeCondition').contentWindow.Ext.getCmp('tableList2Grid');//获取grid对象
	 gird_domtable.setHeight(485);
	 //设置信息项列表高度
	 var gird_domcode= document.getElementById('iframeCondition').contentWindow.Ext.getCmp('codeList2Grid');//获取grid对象
	 gird_domcode.setHeight(218);
	 var gird_domcode1= document.getElementById('iframeCondition').contentWindow.Ext.getCmp('codeList2Grid1');//获取grid对象
	 gird_domcode1.setHeight(217);
	 //conditionName8 设置添加条件div高度
	 document.getElementById('iframeCondition').contentWindow.document.getElementById('conditionName8').style.height='120px';
	 //设置删除按钮 td元素高度
	 document.getElementById('iframeCondition').contentWindow.document.getElementById('iddeletecondition').style.height='30px';
	 //设置全部删除按钮 td元素高度
	 document.getElementById('iframeCondition').contentWindow.document.getElementById('iddeleteallcondition').style.height='30px';
	 //设置保存预览按钮，隐藏 
	 document.getElementById('iframeCondition').contentWindow.document.getElementById('divsave4').style.display='none';
	 //设置tab页高度
	 Ext.getCmp('tab').setHeight(605);
	 Ext.getCmp('viewListGrid2').on('rowclick',function(gridobj,index,e){rowqvDbClick2(gridobj,index,e)});
	 Ext.getCmp('tab').addListener('tabchange',function(tabp){//切换tab页重新计算宽，否则样式会乱掉
		resize();
		// alert(tabp.activeTab.id)
	 });
}
 Ext.onReady(function(){
	 
	 
 });
 function udfbtn4func(){//保存方案名称
	 //校验信息集是否被选中
	var gridtable=document.getElementById('iframeCondition').contentWindow.Ext.getCmp('tableList2Grid');
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
		return ;
	}
	 //校验信息项是否被选中
	var gridcode=document.getElementById('iframeCondition').contentWindow.Ext.getCmp('codeList2Grid1');
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
		return ;
	}
	radow.doEvent("saveschemename");
	//document.getElementById('iframeCondition').contentWindow.getSqlParent('qrysql','saveschemename');
	
 }
function savescheall(qvid){
	document.getElementById('iframeCondition').contentWindow.saveschemeall(qvid);
	 
 }
 function udfbt5func(){
	 var qvid=document.getElementById('qvid').value;
	 var querysql=document.getElementById('iframeCondition').contentWindow.getQuerysql()
	 if(querysql==null||querysql==""||querysql==" "||querysql=="null"){
		 alert("请先保存方案!");
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
function rowqvDbClick2(grid,rowIndex,colIndex,event){//已建视图行双击事件
   	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
   	document.getElementById("yf000").value = record.data.yf000;
	document.getElementById("yf002").value = record.data.yf002;
	document.getElementById("yf004").value = record.data.yf004==null?"":record.data.yf004;
	refreshViewListGrid();
} 
 
function refreshViewListGrid(){
	radow.doEvent("viewListGrid.dogridquery");
}
function getCheckList2(index){
	
}
function getCheckList(gridId,fieldName,obj){
	
}
function resize(){
	var viewSize = Ext.getBody().getViewSize();
	var viewListGrid =Ext.getCmp('viewListGrid');
	var tab = Ext.getCmp('tab');
	//tab.activate("tab2");
	viewListGrid.setWidth(488);
	var viewListGrid =Ext.getCmp('viewListGrid2');
	viewListGrid.setWidth(488);
	//tab.activate("tab1");
}
function saveFN(){
	var peopleInfoGrid =Ext.getCmp('viewListGrid');
	var store = peopleInfoGrid.store;
	var qvids = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			qvids = qvids + rowData.data.qvid + ",";
		}
	} 
	if(""==qvids){
		Ext.MessageBox.alert('系统提示','请选择条件！');
		return;
	}
	var yf000 = document.getElementById("yf000").value;
	var yf002 = document.getElementById("yf002").value;
	var yf004 = document.getElementById("yf004").value;
	var msgTemplate =  "<div style='margin-bottom:13px;'>"  
    + "<span style='font-size: 12px;line-height:24px; color:black;'>方案类别:</span><br />"  
  	+ "<input type='text' id='fnlbcombo' />" 
        + "</div>"  
        + "请输入方案名称：";  
	
	Ext.MessageBox.prompt("输入框",msgTemplate,function(bu,txt){    
		 if(bu=="ok"&&txt!=''&&document.getElementById("fnlb").value!=''){
			 document.getElementById("fnlb2").value=document.getElementById("fnlb").value;
			 radow.doEvent("saveInfo",txt);
		 }
	        
	},this,null,yf002); 
	if(Ext.getCmp('fnlbcombo')!=null){
		Ext.getCmp('fnlbcombo').destroy();
	}
	var combobox = new Ext.form.ComboBox({
        store: [['1','人员预警'],['2','人员筛选']],
        width: 150,
        valueField:'key',
        hiddenName:'fnlb',
        triggerAction: 'all',
        width:222,
        allowBlank: true,
        hiddenId:'fnlb',
        editable :true,
        id:'fnlbcombo',
        applyTo:'fnlbcombo',
        mode: 'local' ,
        forceSelection :true,
        value: yf004==''?'1':yf004 
    });
	
	
}
function delFN(){
	var yf000 = document.getElementById("yf000").value;
	var yf002 = document.getElementById("yf002").value;
	if(yf000==''){
		Ext.MessageBox.alert('系统提示','请选择方案！');
		return;
	}
	$h.confirm("系统提示：",'确定删除方案：'+yf002+"?",300,function(id) { 
		if("ok"==id){
			radow.doEvent("delFN");
		}else{
			return false;
		}		
	});
	
}
function clearFN(){
	var peopleInfoGrid =Ext.getCmp('viewListGrid');
	var store = peopleInfoGrid.store;
	var qvids = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			rowData.set("pcheck", false);
		}
	} 
	document.getElementById("yf000").value='';
	document.getElementById("yf002").value='';
	document.getElementById("yf004").value='';
	
}

function previewFunc(){//预览
	var url="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ldbj.PreviewQuery" ;
	var sqlPre=document.getElementById('iframeCondition').contentWindow.document.getElementById('querysql').value;
	var qvid=document.getElementById('iframeCondition').contentWindow.document.getElementById('qvid').value;
	
	//console.log(sqlPre);
	if(sqlPre==""||sqlPre==" "||sqlPre==null||sqlPre.length==0){
		
		
		//先保存 add by zoulei 2018年5月18日
		document.getElementById('iframeCondition').contentWindow.saveFunc("refreshPreviewTab");
		return;
	}
	odin.ext.getCmp('tab').activate('tab3');
	document.getElementById('iframePreview').src=url+"&sql="+""+"&qvid="+qvid;
}





function yjtyperenderer(value, params, record, rowIndex, colIndex, ds, colorExp){
	
	//return "<div style=' background-position: -19px -8px;width:31px;height:30px; background-image: url(icos/yujing2.jpg)'></div>";
	var imgsrc = '';
	if(value=='1'){
		imgsrc = "icos/emergency-off.png";
	}else if(value=='2'){
		imgsrc = "icos/emergency-y.png";
	}else if(value=='3'){
		imgsrc = "icos/emergency-g.png";
	}else if(value=='4'){
		imgsrc = "icos/fine.png";
	}
	
	if(value!=''&&value!=null){
		return "<div style='width:24px;height:24px;'><image style='width:24px;height:24px;' src='"+imgsrc+"'/></div>";
	}
	else
		return '';
		
}
</script>
