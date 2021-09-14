<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<style>
    .x-form-item2 tr td .x-form-item {
        margin-bottom: 0px !important;
    }
    .yellow{
    	background: #fff68f;
    }
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<table cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;">
    <tr>
        <td width="270" id="td1">
            <table cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;">
                <tr>
                    <td valign="top">
                        <odin:tab id="tab" width="270" height="498">
                            <odin:tabModel>
                                <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                            </odin:tabModel>
                            <odin:tabCont itemIndex="tab1" className="tab">
                                <table id="tableTab1" style="height: 465px;border-collapse:collapse;">
                                    <tr>
                                        <td colspan="2">
                                            <div id="tree-div" style="overflow: auto; height: 100%; width: 100%; border: 2px solid #c3daf9;"></div>
                                            <odin:hidden property="checkedgroupid"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                            <input type="checkbox" checked id="isContain"/>
                                            <span style="font-size: 13px">包含下级</span>
                                        </td>
                                    </tr>
                                </table>
                            </odin:tabCont>
                        </odin:tab>
                    </td>
                </tr>
            </table>
        </td>
        <td valign="top">
            <div id="girdDiv" style="width: 100%;margin:0;">
                <table class="x-form-item2" style="background-color: rgb(209,223,245); border-left: 1px solid rgb(153,187,232);
	border-top: 1px solid rgb(153,187,232); border-right: 1px solid rgb(153,187,232);top: 0px;width: 100%;">
                    <tr>
                        <td height="20px">
                            <div style="width: 170px"></div>
                        </td>
                        <%-- <td align="right" style="width:40.7%;">
                            <odin:button text="&nbsp;同屏比较&nbsp;" property="tpbj"></odin:button>
                        </td> --%>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;导&nbsp;&nbsp;出&nbsp;" property="outExec" handler="outExec"></odin:button>
                        </td> --%>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;查&nbsp;&nbsp;询&nbsp;" property="personQuery" handler="PersonQuery"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;新&nbsp;&nbsp;增&nbsp;" property="personAdd" handler="personAdd"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;删&nbsp;&nbsp;除&nbsp;" property="gbjyDelete"></odin:button>
                        </td>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;备&nbsp;&nbsp;注&nbsp;" property="gbjyAddBZ" handler="gbjyAddBZ"></odin:button>
                        </td> --%>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            <odin:editgrid2 property="editgrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
                                <odin:gridJsonDataModel>
                                    <odin:gridDataCol name="checked"/>
                                    <odin:gridDataCol name="a0000"/>
                                    <odin:gridDataCol name="id"/>
                                    <odin:gridDataCol name="xm"/>
                                    <odin:gridDataCol name="xb"/>
                                    <odin:gridDataCol name="csny"/>
                                    <odin:gridDataCol name="rzjg"/>
                                    <odin:gridDataCol name="zw"/>
                                    <odin:gridDataCol name="zj"/>
                                    <odin:gridDataCol name="grzw"/>
                                    <odin:gridDataCol name="xpdw"/>
                                    <odin:gridDataCol name="gzlx"/>
                                    <odin:gridDataCol name="gzrw"/>
                                    <odin:gridDataCol name="kssj"/>
                                    <odin:gridDataCol name="jssj"/>
                                    <odin:gridDataCol name="khqk"/>
                                    <odin:gridDataCol name="dybz"/>
                                    <odin:gridDataCol name="nd"/>
                                    <odin:gridDataCol name="jsgz" isLast="true"/>
                                </odin:gridJsonDataModel>
                                <odin:gridColumnModel>
                                    <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                                    <odin:gridEditColumn2 locked="true" header="selectall" width="40" editor="checkbox" dataIndex="checked" edited="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0000" width="50" header="人员id" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="id" width="50" header="挂职管理id" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="nd" width="50" header="年度" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xm" width="100" header="姓名" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xb" width="48" header="性别" editor="select" edited="false" align="center" codeType="GB2261"/>
                                    <odin:gridEditColumn2 dataIndex="csny" width="140" header="出生年月" editor="text" edited="false" align="center" renderer="formatCsny"/>
                                    <odin:gridEditColumn2 dataIndex="rzjg" width="300" header="所在单位" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="zw" width="150" header="职务" editor="select" codeType="ZB09" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="zj" width="150" header="职级（层级）" editor="select" codeType="ZB148" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="grzw" width="150" header="挂任职务" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xpdw" width="150" header="援派地" editor="select" codeType="AIDREGION" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="gzlx" width="150" header="批次" editor="select" selectData="['01', '第一批次'],['02', '第二批次'],['03', '第三批次'],['04', '第四批次'],['05', '第五批次'],['06', '第六批次'],['07', '第七批次'],['08', '第八批次'],['09', '第九批次'],['10', '第十批次']" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="gzrw" width="150" header="挂职任务" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="kssj" width="150" header="开始时间" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="jssj" width="150" header="结束时间" editor="text" edited="false" isLast="true" align="center"/>
                                </odin:gridColumnModel>
                            </odin:editgrid2>
                            <odin:hidden property="elearningid"/>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
Ext.onReady(function(){
	
	setColor();
	
});
function setColor(){
	var grid = Ext.getCmp("editgrid");
	grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if(record.data.jsgz == '0'){
			return 'yellow';
		}else{
			return '';
		}
    };
}


    var ctxPath = '<%= request.getContextPath() %>';
    var tree;
    Ext.onReady(function () {
        var Tree = Ext.tree;
        tree = new Tree.TreePanel({
            id: 'group',
            el: 'tree-div',//目标div容器
            split: false,
            width: 270,
            height: 600,
            minSize: 164,
            maxSize: 164,
            rootVisible: false,//是否显示最上级节点
            autoScroll: true,
            animate: true,
            border: false,
            enableDD: false,
            containerScroll: true,
            loader: new Tree.TreeLoader({
                dataUrl: 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1'
            })
        });
        tree.on('click', treeClick);

        var root = new Tree.AsyncTreeNode({
            id: "-1"
        });
        tree.setRootNode(root);
        tree.render();
        root.expand(false, true, callback);

        var viewSize = Ext.getBody().getViewSize();
        var editgrid = Ext.getCmp('editgrid');

        var resizeobj = Ext.getCmp('tab');
        resizeobj.setHeight(viewSize.height - 19);//34 - 29
        var tableTab1 = document.getElementById("tableTab1");
        tableTab1.style.height = viewSize.height - 49 + "px";//87 82
        editgrid.setHeight(viewSize.height - 50);
        editgrid.setWidth(viewSize.width - 270);
    });

    var callback = function (node) {
        if (node.hasChildNodes()) {
            node.eachChild(function (child) {
                child.expand();
            })
        }
    }

    function treeClick(node, e) {
        document.getElementById("checkedgroupid").value = node.id;
        PersonQuery()
    }

    function reload() {
        radow.doEvent('editgrid.dogridquery');
    }

    function PersonQuery() {
        radow.doEvent("editgrid.dogridquery");
    }

    function personAdd() {
        $h.openWin('wpgzAdd', 'pages.gzgl.WpgzAdd', '新增外派挂职 ', 920, 650, null, ctxPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close'
        })
    }

    function formatCsny(val) {
        if (val.length === 6 || val.length === 8) {
            return val.substr(0, 4) + "." + val.substr(4, 2)
        } else {
            return val
        }
    }
    function outExec() {
        radow.doEvent("OutExec");
    }
    function outExcel(path) {
        var url = ctxPath + '/PublishFileServlet?method=Jbgz&param=' + path;
        window.location.href = url;
    }
    
    function clearSelected() {
    	//列表
		var gridId = "editgrid";
		var fieldName = "checked";
		var store = odin.ext.getCmp(gridId).store;
		var length = store.getCount();
		for (var i = 0; i < length; i++) {
			store.getAt(i).set(fieldName, false);
		}
		//store.reload()
    }
    
    
    
function gbjyAddBZ(){
	var grid = odin.ext.getCmp('editgrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var a0000s = "";

	
	var flag=0;
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.checked == true){
			a0000s=a0000s+rowData.data.a0000+",";
			flag++;
		}
	} 
	if(a0000s == null || a0000s == ""||flag>1){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	alert(a0000)
	//$h.openWin('khpjWin','pages.gbwh.KHPJ','考核评价',1100,600,a0000,g_contextpath,null,{maximizable:false,resizable:false});
	
}
</script>
