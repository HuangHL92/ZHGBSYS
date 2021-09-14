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
                                <odin:tabItem title="������" id="tab1" isLast="true"></odin:tabItem>
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
                                            <span style="font-size: 13px">�����¼�</span>
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
                            <odin:button text="&nbsp;ͬ���Ƚ�&nbsp;" property="tpbj"></odin:button>
                        </td> --%>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="outExec" handler="outExec"></odin:button>
                        </td> --%>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;ѯ&nbsp;" property="personQuery" handler="PersonQuery"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="personAdd" handler="personAdd"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;ɾ&nbsp;&nbsp;��&nbsp;" property="gbjyDelete"></odin:button>
                        </td>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;ע&nbsp;" property="gbjyAddBZ" handler="gbjyAddBZ"></odin:button>
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
                                    <odin:gridEditColumn2 dataIndex="a0000" width="50" header="��Աid" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="id" width="50" header="��ְ����id" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="nd" width="50" header="���" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xm" width="100" header="����" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xb" width="48" header="�Ա�" editor="select" edited="false" align="center" codeType="GB2261"/>
                                    <odin:gridEditColumn2 dataIndex="csny" width="140" header="��������" editor="text" edited="false" align="center" renderer="formatCsny"/>
                                    <odin:gridEditColumn2 dataIndex="rzjg" width="300" header="���ڵ�λ" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="zw" width="150" header="ְ��" editor="select" codeType="ZB09" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="zj" width="150" header="ְ�����㼶��" editor="select" codeType="ZB148" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="grzw" width="150" header="����ְ��" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="xpdw" width="150" header="Ԯ�ɵ�" editor="select" codeType="AIDREGION" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="gzlx" width="150" header="����" editor="select" selectData="['01', '��һ����'],['02', '�ڶ�����'],['03', '��������'],['04', '��������'],['05', '��������'],['06', '��������'],['07', '��������'],['08', '�ڰ�����'],['09', '�ھ�����'],['10', '��ʮ����']" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="gzrw" width="150" header="��ְ����" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="kssj" width="150" header="��ʼʱ��" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="jssj" width="150" header="����ʱ��" editor="text" edited="false" isLast="true" align="center"/>
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
            el: 'tree-div',//Ŀ��div����
            split: false,
            width: 270,
            height: 600,
            minSize: 164,
            maxSize: 164,
            rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
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
        $h.openWin('wpgzAdd', 'pages.gzgl.WpgzAdd', '�������ɹ�ְ ', 920, 650, null, ctxPath, null, {
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
    	//�б�
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
		$h.alert('ϵͳ��ʾ��','��ѡ��һ����¼��',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	alert(a0000)
	//$h.openWin('khpjWin','pages.gbwh.KHPJ','��������',1100,600,a0000,g_contextpath,null,{maximizable:false,resizable:false});
	
}
</script>
