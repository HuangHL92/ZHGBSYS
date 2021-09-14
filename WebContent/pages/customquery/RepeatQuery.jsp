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
td{
border: 0px solid red;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>



<odin:hidden property="sql"/>
<odin:hidden property="checkList"/>

<odin:toolBar property="float" applyTo="toolDiv"  >
	<odin:textForToolBar text="<h3>������� </h3>"/>
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ����" isLast="true" handler="ExpBtn" icon="images/icon_photodesk.gif"></odin:buttonForToolBar>
</odin:toolBar>

<div style="height: 100%;width: 100%">
	<div id="toolDiv" style="width: 100%;"></div>
	<table height="100%" width="100%" cellspacing="0">
		<tr>
			<td valign="top" id="tdgrid" >
			<div id="girdDiv" style="width: 100%;">
				<odin:groupBox property="ssk" title="�����ѡ��">
					<table width="100%">
						<tr>
							<td width="50px"></td>
							<odin:textEdit property="a0101A" label="����"  maxlength="36"></odin:textEdit>							
							<td>
							<odin:checkbox property="a0104A" label="�Ա�" ></odin:checkbox>
							</td>
							<td width="30px"></td>
							<td>
							<odin:checkbox property="a0107A" label="��������" ></odin:checkbox>
							</td>
							<td width="26px"></td>
							<td>
							<odin:checkbox property="a0111" label="����" ></odin:checkbox>
							</td>
							<td width="150px">
							</td>
							<td valign="top">
							<odin:button text="<h5>��ʼ���</h5>" property="btn1"></odin:button>
							</td>
							<td width="400px">
							</td>
						</tr>				
					</table>
				</odin:groupBox>
			
				<odin:grid property="persongrid" 
					bbarId="pageToolBar" isFirstLoadData="false" url="/" autoFill="false" topBarId="" pageSize="20">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="personcheck"/>
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0117" />
						<odin:gridDataCol name="a0111a" />
						<odin:gridDataCol name="a0141" />
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0134" />
						<odin:gridDataCol name="a0160" />
			
						<odin:gridDataCol name="a0000" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" align="center" hideable="false" dataIndex="personcheck" edited="true" gridName="persongrid" checkBoxClick="getCheckList()" checkBoxSelectAllClick="getCheckList()"/>
						<odin:gridEditColumn2 header="����" edited="false" width="80" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="�Ա�" edited="false" width="60" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="��������" edited="false" width="120" align="center"  dataIndex="a0107" hidden="false" editor="text" />
						<odin:gridEditColumn2 header="����" edited="false" width="80" align="center"  dataIndex="a0111a" editor="text"/>
						<odin:gridEditColumn2 header="����" edited="false" width="80" align="center"  dataIndex="a0117" editor="select" codeType="GB3304"/>
						<odin:gridEditColumn2 header="������ò" edited="false" width="80" align="center"  dataIndex="a0141" editor="select" codeType="GB4762"/>
						<odin:gridEditColumn2 header="������λ��ְ��" edited="false" width="350" align="center"  dataIndex="a0192a" editor="text"/>
						<%--<odin:gridColumn header="Ԥ����ӡ" edited="false" width="60" dataIndex="a0000" align="center" hidden="true" renderer="openEditer" />--%>
						
						
						<odin:gridEditColumn2 header="��Ա���" edited="false" width="120" align="center"  dataIndex="a0160" editor="select" hidden="false" codeType="ZB125"/>
						
					    <odin:gridEditColumn  align="center" sortable="false" width="100" header="ɾ��" dataIndex="op" editor="text" edited="false" renderer="commGridColDelete" />
						<odin:gridEditColumn2 header="id" edited="false" width="200" dataIndex="a0000" hideable="false" isLast="true" editor="text" hidden="true" />
					</odin:gridColumnModel>
				</odin:grid>
				</div>
			</td>
		</tr>
	</table>
</div>

<odin:window src="/blank.htm"  id="deletePersonWin" width="520" height="400" title="��Աɾ��" modal="true"/>

<script type="text/javascript">




var win_addwin;
var win_addwinnew;
Ext.onReady(function() {

	win_addwin = new Ext.Window({
		html : '<iframe width="100%" frameborder="0" id="iframe_addwin" name="iframe_addwin" height="100%" src="<%=request.getContextPath()%>/Index.jsp"></iframe>',
		title : '��Ա��������',
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
		title : '��Ա��������',
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
	
	//ҳ�����
	Ext.getCmp('persongrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_persongrid'))[0]-4);
	Ext.getCmp('persongrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_persongrid'))[1]-2); 
	document.getElementById("toolDiv").style.width = Ext.getCmp('persongrid').getWidth() + "px";
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

//��Ա�����޸Ĵ��ڴ���
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
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
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
	/*
	function change(obj){
		var choose = Ext.getCmp(obj).getValue();
		//alert(choose);
	}
	*/

	
	function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' onclick=\"radow.doEvent('dogriddelete','"+record.get('a0000')+"');\">";
	}
	
	function ExpBtn(){
		var fileName="���������Ϣ.xls";
		var excelType="101";
		var viewType="";
		var gridId = "persongrid";
		var a0000 = "";
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				//alert(a0000);
			}
		}
		if (a0000 == '') {
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
		a0000 = a0000.substring(0, a0000.length - 1);
		window.location="<%=request.getContextPath() %>/FiledownServlet?fileName=" + encodeURI(encodeURI(fileName)) +"&excelType="+excelType+"&viewType="+viewType+"&a0000="+a0000+"&download=true";
	
	}
	function reloadGrid(){
		radow.doEvent("btn1.onclick");
	}

	
</script>