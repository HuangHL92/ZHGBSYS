<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<style>
    body {
        overflow: hidden;
    }

    .x-tab-panel-body.x-tab-panel-body-top {
        height: 540px !important;
    }

    .x-panel-body.x-panel-body-noheader.x-panel-body-noborder {
        height: auto !important;
    }

    #commForm {
        width: 1180px;
    }

    .left-container {
        float: left;
    }

    #tree-div {
        overflow: auto;
        border: 2px solid #c3daf9;
        width: 280px !important;
        height: 551px;
    }

    #tableTab1 {
        height: 540px !important;
        border-collapse: collapse;
    }

    .right-container {
        float: right;
        width: 909px;
        height: 570px;
    }
</style>

<div class="left-container">
    <table cellspacing="0" cellpadding="0;" style="margin-top: 0;border-collapse:collapse;height:100%;">
        <tr>
            <td valign="top">
                <odin:tab id="tab" width="270" height="498">
                    <odin:tabModel>
                        <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                    </odin:tabModel>
                    <odin:tabCont itemIndex="tab1" className="tab">
                        <table id="tableTab1">
                            <tr>
                                <td colspan="2">
                                    <div id="tree-div" style="overflow: auto; height: 100%; width: 100%; border: 2px solid #c3daf9;"></div>
                                    <odin:hidden property="checkedgroupid"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                    <input type="checkbox" id="isContain" checked> <span style="font-size: 13px">包含下级</span>
                                </td>
                            </tr>
                        </table>
                    </odin:tabCont>
                </odin:tab>
            </td>
        </tr>
    </table>
</div>
<div class="right-container">
    <odin:toolBar property="toolBar">
        <odin:fill/>
        <odin:buttonForToolBar text="查询" id="search"/>
        <odin:separator/>
        <odin:buttonForToolBar text="确定" handler="confim"/>
    </odin:toolBar>

    <div id="content">
        <odin:groupBox title="查询条件" property="groupBox">
            <table style="width: 100%;">
                <tr>
                    <td>
                        <odin:textEdit property="q_name" label="姓名"/>
                    </td>
                    <td>
                        <odin:select property="q_sex" label="性别" codeType="GB2261"/>
                    </td>
                </tr>
            </table>
        </odin:groupBox>
    </div>

    <odin:panel property="panel" contentEl="content" topBarId="toolBar"/>

    <odin:editgrid property="grid" isFirstLoadData="false" url="/" autoFill="false" bbarId="pageToolBar" pageSize="15" height="451">
        <odin:gridJsonDataModel>
            <odin:gridDataCol name="id"/>
            <odin:gridDataCol name="xm"/>
            <odin:gridDataCol name="xb"/>
            <odin:gridDataCol name="csny"/>
            <odin:gridDataCol name="zw"/>
            <odin:gridDataCol name="zj"/>
            <odin:gridDataCol name="rzjg" isLast="true"/>
        </odin:gridJsonDataModel>
        <odin:gridColumnModel>
            <odin:gridRowNumColumn width="30"/>
            <odin:gridColumn dataIndex="xm" editor="text" edited="false" header="姓名" width="100" align="center"/>
            <odin:gridColumn dataIndex="xb" editor="select" edited="false" header="性别" width="50" align="center" codeType="GB2261"/>
            <odin:gridColumn dataIndex="csny" editor="date" edited="false" header="出生年月" width="80" align="center" renderer="formatCsny"/>
            <odin:gridColumn dataIndex="zw" editor="select" edited="false" header="职务" width="150" align="center" codeType="ZB09"/>
            <odin:gridColumn dataIndex="zj" editor="select" edited="false" header="职级" width="130" align="center" codeType="ZB148"/>
            <odin:gridColumn dataIndex="rzjg" editor="text" edited="false" header="所在处室" width="350" align="center" isLast="true"/>
        </odin:gridColumnModel>
    </odin:editgrid>
</div>

<odin:hidden property="SysOrgTreeIds" value="{}"/>


<script>

    var tree;
    Ext.onReady(function () {
        var Tree = Ext.tree;
        tree = new Tree.TreePanel({
            id: 'group',
            el: 'tree-div',//目标div容器
            split: false,
            width: 270,
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
    })

    var callback = function (node) {
        if (node.hasChildNodes()) {
            node.eachChild(function (child) {
                child.expand();
            })
        }
    }

    function treeClick(node, e) {
        document.getElementById("checkedgroupid").value = node.id
        search()
    }

    // 回车搜索
    document.onkeydown = function (event) {
        event = event || window.event
        if (event.keyCode == 13) {
            search()
        }
    }

    function search() {
        radow.doEvent('grid.dogridquery')
    }

    function formatCsny(val) {
        if (val.length === 6 || val.length === 8) {
            return val.substr(0, 4) + "." + val.substr(4, 2)
        } else {
            return val
        }
    }

    function confim() {
        var selectedRow = odin.ext.getCmp('grid').getSelectionModel().getSelections()[0]
        var id = selectedRow.get('id')
        realParent.callback(id)
    }
</script>
