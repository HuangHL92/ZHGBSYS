<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%String ctxPath = request.getContextPath(); 
%>	
<style>
.x-panel-body{
height: 95%
}
.x-panel-bwrap{
height: 100%
}
.picOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}
.picInnerOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}
.picGroupOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png) !important;
}
#tree-div{
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>



<odin:hidden property="sql"/>
<odin:hidden property="personType" value="X002"/>
<odin:hidden property="checkList"/>
<odin:hidden property="bo1Value" value=""/>
<odin:hidden property="a0201a"/>
<odin:toolBar property="floatToolBar" applyTo="toolDiv"  >
	
	<odin:textForToolBar text="<h3>历史和离退人员管理</h3>" ></odin:textForToolBar>
	<odin:fill />
	<odin:separator />	
	<odin:buttonForToolBar text="人员机构转移" icon="images/keyedit.gif" isLast="true" handler="transform"></odin:buttonForToolBar>
</odin:toolBar>


<div id="main" style="height: 100%;">
	<div id="toolDiv"></div>
	<table height="100%" width="100%" cellspacing="0">
		<tr>
			<td><div id="divresize" style="height: 100%;width: 3px;cursor: e-resize;"></div></td>
			<td valign="top" id="tdgrid" >
			<div id="girdDiv" style="width: 100%">
				<odin:groupBox property="ssk" title="搜索框">
					<table width="100%">
						<tr>
							<odin:hidden property="downfile"/>
							<odin:textEdit property="a0101A" label="姓名" width="140" maxlength="18"></odin:textEdit>
							<odin:textEdit property="a0184A" label="身份证号" width="150" maxlength="18"></odin:textEdit>
							<td style='border-collapse:collapse;'><span style="height:13px;float: right;font-size:12;">人员类别&nbsp;</span></td>
							<td id="type" ></td>
							<tags:PublicTextIconEdit4 readonly="true" codetype="orgTreeJsonData" required="false" onchange="a0201bChange" label="所属机构" property="a0201b" defaultValue=""/>
							<td>
							<odin:button text="查询" property="btn1" ></odin:button>
							</td>
							<td>
							<odin:button text="重置" property="reset"></odin:button>
							</td>
		
						</tr>				
					</table>
				</odin:groupBox>
			
				<odin:editgrid property="persongrid" 
					bbarId="pageToolBar" isFirstLoadData="false" url="/" autoFill="false" topBarId="" pageSize="20">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="personcheck"/>
						<odin:gridDataCol name="a0148" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="age" />
						<odin:gridDataCol name="a0117" />
						<odin:gridDataCol name="a0141" />
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="a0163"/>
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0140"/>
						<odin:gridDataCol name="a0134" />
						<odin:gridDataCol name="a0165"/>
						<odin:gridDataCol name="a0160" />
						<odin:gridDataCol name="a0120"/>
						<odin:gridDataCol name="a0192d" />
						<odin:gridDataCol name="a0121"/>
						<odin:gridDataCol name="a0184" />
						<odin:gridDataCol name="orgid" />
						<odin:gridDataCol name="qrzxl"/>
						<odin:gridDataCol name="zzxl" />
						
						<odin:gridDataCol name="a0000" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" align="center" hideable="false" dataIndex="personcheck" edited="true" gridName="persongrid" checkBoxClick="getCheckList()" checkBoxSelectAllClick="getCheckList()"/>
						<odin:gridEditColumn2 header="姓名" edited="false" width="80" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="性别" edited="false" width="60" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="年龄" edited="false" width="60" align="center"  dataIndex="age" editor="text"/>
						<odin:gridEditColumn2 header="民族" edited="false" width="80" align="center"  dataIndex="a0117" editor="select" codeType="GB3304"/>
						<odin:gridEditColumn2 header="政治面貌" edited="false" width="80" align="center"  dataIndex="a0141" editor="select" codeType="GB4762"/>
						<odin:gridEditColumn2 header="工作单位及职务" edited="false" width="350" align="center"  dataIndex="a0192a" editor="text"/>
						<odin:gridEditColumn2 header="职务层次" edited="false" width="120" align="center"  dataIndex="a0148" editor="select" codeType="ZB09" />
						<%--<odin:gridColumn header="预览打印" edited="false" width="60" dataIndex="a0000" align="center" hidden="true" renderer="openEditer" />--%>
						
						<odin:gridEditColumn2 header="出生年月" edited="false" width="120" align="center"  dataIndex="a0107" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="入党时间" edited="false" width="120" align="center"  dataIndex="a0140" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="参加工作时间" edited="false" width="120" align="center"  dataIndex="a0134" hidden="true" editor="text" />
						<odin:gridEditColumn2 header="管理类别" edited="false" width="120" align="center"  dataIndex="a0165" editor="select" hidden="true" codeType="ZB130"/>
						<odin:gridEditColumn2 header="人员类别" edited="false" width="120" align="center"  dataIndex="a0160" editor="select" hidden="true" codeType="ZB125"/>
						<odin:gridEditColumn2 header="级别" edited="false" width="120" align="center"  dataIndex="a0120" editor="select" hidden="true" codeType="ZB134"/>
						<odin:gridEditColumn2 header="职级" edited="false" width="120" align="center"  dataIndex="a0192d" editor="select" hidden="true" codeType="ZB133"/>
						<odin:gridEditColumn2 header="编制类型" edited="false" width="120" align="center"  dataIndex="a0121" editor="select" hidden="true" codeType="ZB135"/>
						<odin:gridEditColumn header="身份证号 " edited="false" width="160" align="center"  dataIndex="a0184" editor="text" hidden="true"/>
						<odin:gridColumn header="最高全日制学历" dataIndex="qrzxl" width="110" hidden="true" align="center"/>
						<odin:gridColumn header="最高在职学历" dataIndex="zzxl" width="110" hidden="true"  align="center"/>
					    <odin:gridEditColumn2 header="人员管理状态" edited="false" dataIndex="a0163" codeType="ZB126" width="110" hidden="false" editor="select" align="center"/>
					    <odin:gridColumn header="所属机构" dataIndex="orgid" width="250" hidden="false" align="center"/>
						<odin:gridEditColumn2 header="id" edited="false" width="200" dataIndex="a0000" hideable="false" isLast="true" editor="text" hidden="true" />
					</odin:gridColumnModel>
				</odin:editgrid>
				</div>
			</td>
		</tr>
	</table>
</div>


<script type="text/javascript">
Ext.onReady(function(){
    //创建数据源[数组数据源]
    var combostore = new Ext.data.SimpleStore({
        fields: ['id', 'name'],
        data: [["X002", '离退人员'], ["X003", '历史人员']]
    });
    //创建Combobox
    var combobox = new Ext.form.ComboBox({
       // fieldLabel: 'a',
        store: combostore,
        width: 150,
        displayField: 'name',
        valueField: 'id',
        triggerAction: 'all',
        emptyText: '请选择...',
        allowBlank: false,
        blankText: '请选择人员类别',
        editable: false,
        mode: 'local' ,
        value: "X002" 
    });
    //Combobox获取值
    combobox.on('select', function () {
        //alert(combobox.getValue());
        document.getElementById("personType").value = combobox.getValue();
        //var a = document.getElementById("a0201b").value;
        //alert(a);
    })

    combobox.render("type");
});




var win_addwin;
var win_addwinnew;
Ext.onReady(function() {

	win_addwin = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwin" name="iframe_addwin" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '人员新增窗口',
		layout : 'fit',
		width : 620,
		height : 415,
		closeAction : 'hide',
		closable : true,
		minimizable : false,
		maximizable : true,
		modal : false,
		maximized:true,
		id : 'addwin',
		bodyStyle : 'background-color:#FFFFFF',
		plain : true
	});
	
	win_addwinnew = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwinnew" name="iframe_addwinnew" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '人员新增窗口',
		layout : 'fit',
		width : 620,
		height : 415,
		closeAction : 'hide',
		closable : true,
		minimizable : false,
		maximizable : true,
		modal : false,
		maximized:true,
		id : 'addwinnew',
		bodyStyle : 'background-color:#FFFFFF',
		plain : true
	});
	//页面调整
	 Ext.getCmp('persongrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_persongrid'))[0]-4);
	 Ext.getCmp('persongrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_persongrid'))[1]-2); 
	 document.getElementById('toolDiv').style.width = document.body.clientWidth;
	 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 document.getElementById("main").style.width = document.body.clientWidth;
});


function openEditer(value, params, record,rowIndex,colIndex,ds){
	
	if(value){
		return "<img src='"+contextPath+"/image/u117.png' title='' style='cursor:pointer' onclick=\"printView('"+value+"');\">";
	}else{
		return null;
	}
}




//人员新增修改窗口窗口
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh,param){
      var tab=parent.tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
    	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        //personListTabId:personListTabId,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
        }).show();  
		
      }
    }

	function xx(){
		var downfile = document.getElementById('downfile').value;
		w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
		setTimeout(cc,3000);
	}
	function cc(){
	w.close();
	}
	function reload(){
		odin.reset();
	}
	
	function transform(){
		if(document.getElementById('checkList').value == "" || document.getElementById('checkList').value.length == 0){
			odin.error("请先勾选要操作的人员！");
			return;
		}
		var codeType = "orgTreeJsonData";
		var codename = "code_name";
	    var winId = "winId"+Math.round(Math.random()*10000);
	    var label = "将人员转移到选中机构";
	    //var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
		//alert(url);
		var url = "pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
	    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
	}
	
	function returnwina0201(rs){
		if(rs!=null){
			var rss = rs.split(",");
			document.getElementById('bo1Value').value=rss[0];
			var record = {data:{value:rss[1],key:rss[0]}};
			var a = document.getElementById('bo1Value').value;
		//	alert(a);
			radow.doEvent('transform');
		}
	}
	function getCheckList(){
		radow.doEvent('getCheckList');
		//alert(document.getElementById('checkList').value);
	}

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
	
</script>