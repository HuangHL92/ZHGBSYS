<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<link href="../../rmb/css/main.css" rel="stylesheet">

<style>
    body {
        background-color: rgb(214, 227, 243);
    }


</style>
<odin:hidden property="a5399" title="填报人id"/>
<odin:hidden property="a5300" title="id(a5300"/>
<odin:hidden property="a0000" title="人员主键"/>

<odin:hidden property="a0501b" title="拟任职务层次"/>
<odin:hidden property="a5315" title="拟任职务"/>
<odin:hidden property="a39067" title="是否领导职务"/>
<odin:hidden property="a0195" title="拟任单位"/>

<odin:hidden property="a0501b_val" title="拟任职务层次value"/>
<odin:hidden property="a39067_val" title="是否领导职务value"/>
<odin:hidden property="a0195_val" title="拟任单位value"/>

<%--拟免职务id--%>
<odin:hidden property="nmzwid"/>

<%-- <odin:toolBar property="toolBar8">
    <odin:fill/>
    <odin:buttonForToolBar text="保存" handler="saveNRM" id="saveNRM" icon="images/save.gif" cls="x-btn-text-icon"/>
    <odin:buttonForToolBar text="新增" handler="addNew" id="addNew" isLast="true" icon="images/add.gif"/>
</odin:toolBar> --%>
<table style="width:100%;margin-top: 50px;">
    <tr>

        <td class="right_td1" align="right" style="font-size: 12px"><span style="MARGIN-RIGHT: 8px">拟任职务</span></td>
       	<odin:textEdit colspan="1" property="js2302" readonly='true'></odin:textEdit>
        <td class="right_td1" align="right" style="font-size: 12px"><span style="MARGIN-RIGHT: 8px">拟免职务</span></td>
		<odin:textEdit colspan="1" property="js2303" readonly='true'></odin:textEdit>
    </tr>
    <tr>
        <td colspan="6">
            <div style="height: 5px"></div>
        </td>
    </tr>
    <tr>
        <odin:textEdit property="js2304" readonly='true' label="任免理由"></odin:textEdit>
        <odin:textEdit property="js2305" readonly='true' label="呈报单位"></odin:textEdit>
    </tr>
    <tr>
        <td colspan="6">
            <div style="height: 5px"></div>
        </td>
    </tr>
    <tr>
        <odin:NewDateEditTag property="js2306" readonly='true' label="计算年龄时间" isCheck="true" maxlength="8"></odin:NewDateEditTag>
        <odin:textEdit property="js2307" readonly='true' label="填表人"></odin:textEdit>
    </tr>
    <tr>
        <td colspan="6">
            <div style="height: 5px"></div>
        </td>
    </tr>
    <tr>
        <odin:NewDateEditTag property="js2309" readonly='true' label="填表时间" maxlength="8"></odin:NewDateEditTag>
        <odin:textEdit property="js2308" readonly='true' label="拟任状态"></odin:textEdit>
    </tr>

    <tr>

        <td colspan="6">

            <odin:grid property="TrainingInfoGrid"  sm="row" isFirstLoadData="false" url="/"
                       height="300">
                <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                    <odin:gridDataCol name="js2300"/>
                    <odin:gridDataCol name="js2302"/>
                    <odin:gridDataCol name="js2303"/>
                    <odin:gridDataCol name="js2304"/>
                    <odin:gridDataCol name="js2305"/>
                    <odin:gridDataCol name="js2306"/>
                    <odin:gridDataCol name="js2307"/>
                    <odin:gridDataCol name="js2308"/>
                    <odin:gridDataCol name="js2309"/>
                    <odin:gridDataCol name="delete" isLast="true"/>
                </odin:gridJsonDataModel>
                <odin:gridColumnModel>
                    <odin:gridRowNumColumn/>
                    <odin:gridEditColumn2 header="主键" dataIndex="js2300" editor="text" edited="false" width="100"
                                          hidden="true"/>
                    <odin:gridEditColumn2 header="拟任职务" align="center" dataIndex="js2302" editor="text" edited="false"
                                          codeType="ZB09" width="100"/>
                    <odin:gridEditColumn2 header="拟免职务" align="center" dataIndex="js2303" editor="text" edited="false"
                                          width="100"/>
                    <odin:gridEditColumn2 header="任免理由" align="center" dataIndex="js2304" editor="text" width="100"
                                          edited="false"/>
                    <odin:gridEditColumn2 header="呈报单位" align="center" dataIndex="js2305" editor="text" width="100"
                                          edited="false"/>
                    <odin:gridEditColumn2 header="计算年龄时间" align="center" dataIndex="js2306" editor="text" width="100"
                                          edited="false"/>
                    <odin:gridEditColumn2 header="填表人" align="center" dataIndex="js2307" editor="text" width="100"
                                          edited="false"/>
                    <odin:gridEditColumn2 header="拟任状态" align="center" dataIndex="js2308" editor="select" width="100"
                                          codeType="XZ01" edited="false"/>
                    <odin:gridEditColumn2 header="填表时间" align="center" dataIndex="js2309" editor="select" edited="false"
                                          selectData="['1','在任'],['0','已免']" width="100"/>
                    <odin:gridEditColumn2 width="45" header="操作" dataIndex="delete" editor="text" edited="false"
                                          renderer="deleteRowRenderer" hidden="true" isLast="true"/>
                </odin:gridColumnModel>
            </odin:grid>
        </td>
    </tr>

</table>

<script type="text/javascript">
    var ctxPath = '<%=ctxPath%>';
    Ext.onReady(function () {
        document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
    });

    function reShowMsg() {
        document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
        radow.doEvent('initX');
    }

    function save() {
        radow.doEvent('save');
    }

    function formcheck() {
        return odin.checkValue(document.forms.commForm);
    }

    function a0221Click() {
        //get parent value
        var a0501b = document.getElementById("a0501b").value;
        var a39067 = document.getElementById("a39067").value;
        var a0195 = document.getElementById("a0195").value;

        var a0501b_val = document.getElementById("a0501b_val").value;
        var a5315 = document.getElementById("a5315").value;
        var a39067_val = document.getElementById("a39067_val").value;
        var a0195_val = document.getElementById("a0195_val").value;

        var stringJSON = "";
        if (a0501b_val != "" && a5315 != "" && a39067_val != "" && a0195_val != "") {
            stringJSON = "{'a0501b_val':'" + a0501b_val +
                "','a5315':'" + a5315 +
                "','a39067_val':'" + a39067_val +
                "','a0195_val':'" + a0195_val +
                "','a0501b':'" + a0501b +
                "','a39067':'" + a39067 +
                "','a0195':'" + a0195 +
                "'}";
        }
        
        //$h.openPageModeWin('RankAddPageWin', 'pages.publicServantManage.RankAddPageNiRen', '拟任职务', 450,300, stringJSON, ctxPath);
		     
    	$h.openPageModeWin('RankAddPageNiRenQcjs','pages.xbrm.RankAddPageNiRenQcjs','拟任职务',450,300,''+','+id,ctxPath);
    	
    }

    function a0192aClick() {
        $h.openPageModeWin('workUnits', 'pages.publicServantManage.WorkUnitsNiRenAddPage', '拟免职务', 407, 260, document.getElementById('a0000').value, ctxPath, null, {
            maximizable: false,
            resizable: false
        });
    }

    function addNew() {
        radow.doEvent('addNew');

    }

    function saveNRM() {
        // 人员统一标识符
        var a0000 = $("#a0000").val();
        // 拟任免信息ID
        var a5300 = $("#a5300").val();
        // 拟任职务 val
        var a5304 = $("#a5315").val().replace(/\s+/g, "");
        // 拟免职务 val
        var a5315 = $("#a0192a").val().replace(/\s+/g, "");
        // 任免理由
        var a5317 = $("#a5317").val().replace(/\s+/g, "");
        // 呈报单位
        var a5319 = $("#a5319").val().replace(/\s+/g, "");
        // 计算年龄时间
        var a5321 = $("#a5321").val();
        // 填表时间
        var a5323 = $("#a5323").val();
        // 填表人
        var a5327 = $("#a5327").val().replace(/\s+/g, "");
        // 拟任免信息ID(primary key)
        var a5399 = $("#a5399").val();

        // 拟任职务层次
        var a5366 = $("#a0501b_val").val();
        // 是否领导职务
        var a5367 = $("#a39067").val();
        // 拟任单位
        var a5368 = $("#a0195_val").val();
        // 拟免职务id
        var a5369 = $("#nmzwid").val();
        // 拟任状态
        var a5365 = $("#a5365").val();

        if (a5365.length == 0) {
            $h.alert('系统提示', '请选择拟任状态！', null, '220');
            return;
        }
        if (a5304.length == 0) {
            $h.alert('系统提示', '拟任职务不能为空！', null, '220');
            return;
        }
        if (a5315.length == 0) {
            $h.alert('系统提示', '拟免职务不能为空！', null, '220');
            return;
        }
        if (a5317.length == 0) {
            $h.alert('系统提示', '任免理由不能为空！', null, '220');
            return;
        }
        if (a5319.length == 0) {
            $h.alert('系统提示', '呈报单位不能为空！', null, '220');
            return;
        }
        if (a5327.length == 0) {
            $h.alert('系统提示', '填表人不能为空！', null, '220');
            return;
        }
        radow.doEvent('saveNRM');
    }

    function deleteRowRenderer(value, params, record, rowIndex, colIndex, ds) {
        var a5300 = record.data.a5300;
        return "<a href=\"javascript:deleteRow2(&quot;" + a5300 + "&quot;)\">删除</a>";
    }

    function deleteRow2(a5300) {
        Ext.Msg.confirm("系统提示", "是否确认删除？", function (id) {
            if ("yes" == id) {
                radow.doEvent('deleteRow', a5300);
            } else {
                return;
            }
        });
    }

    function hideSave() {
        parent.$("#rmbButton").hide();
    }

</script>



