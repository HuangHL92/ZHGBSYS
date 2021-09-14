<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>


<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css"/>

<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
    body {
        margin: 1px;
        overflow: auto;
        font-family: '宋体', Simsun;
        word-break: break-all;
    }

    .ui-tabs .ui-tabs-panel {
        padding: 0px;
        padding-left: 3px;
    }

    .ui-helper-reset {
        font-size: 12px;
    }

    .x-form-field-wrap {
        width: 100% !important;
    }

    /*日期宽  */
    .GBx-fieldset .x-form-trigger {
        right: 0px;
    }

    /*图标对其  */
    .GBx-fieldset input {
        width: 100% !important;
    }

    .GBx-fieldset .x-fieldset {
        padding-bottom: 0px;
        margin-bottom: -12px;
        margin-top: 12px
    }

    .GBx-fieldset .x-fieldset-bwrap {
        overflow-y: auto;
    }


    .marginbottom0px .x-form-item {
        margin-bottom: 0px;
    }

    .marginbottom0px table, .marginbottom0px table tr th, .marginbottom0px table tr td {
        border: 1px solid #74A6CC;
        padding: 3px;
        border-right-width: 0px;
    }

    .marginbottom0px table {
        line-height: 25px;
        text-align: center;
        border-collapse: collapse;
        border-right-width: 1px;
    }

    .titleTd {
        background-color: rgb(192, 220, 241);
        font-weight: bold;
        font-size: 12px;
    }

    .bh {
        display: none;
    }

    .tbh {
        display: none;
    }

    .comboh {
        cursor: pointer !important;
        background: none !important;
        background-color: white !important;
    }

    .aclass {
        font-size: 12px;
        padding-left: 3px !important;
        line-height: 30px;
    }

    TEXTAREA.x-form-field {
        overflow-y: auto;
    }

    .x-grid3-row TD {
        height: 28px;
        line-height: 28px;
        vertical-align: middle;
    }

    .x-grid3-cell-inner {
        padding-top: 0px;
    }

    .x-tip-header .x-tool {
        background-image: none;
    }
</style>

<odin:hidden property="rbId" title="批次id"/>
<odin:hidden property="block" value="" title="青干预审显示行数"/>
<odin:hidden property="docpath" title="文档地址"/>

<odin:toolBar property="bbar" applyTo="bbardiv">
    <!-- 青干预审 -->
    <odin:fill></odin:fill>
    <odin:buttonForToolBar text="保存青干预审表" icon="images/icon/save.gif" id="saveQG" isLast="true"/>
</odin:toolBar>

<div style="width: 100%; margin-top: 3px; overflow-y: scroll; height: 600px;">
    <div id="peopleInfo" style="float: left; margin-left: 3px; " class="GBx-fieldset">
        <div id="tabs-12" class="GBx-fieldset GBxDis">
            <odin:groupBox property="qgysGB" title="青干预审">
                <div id="qinggan" class="marginbottom0px">
                    <table style="width: 100%">
                        <tr>
                            <td class="titleTd" colspan="2" rowspan="2">项目</td>
                            <td class="titleTd" colspan="7">职级</td>

                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1" width="7%">正厅级</td>
                            <td class="titleTd" colspan="1" width="7%">副厅级</td>
                            <td class="titleTd" colspan="1" width="7%">巡视员</td>
                            <td class="titleTd" colspan="1" width="7%">副巡<br/>视员</td>
                            <td class="titleTd" colspan="1" width="7%">合计</td>
                            <td class="titleTd" colspan="1" width="7%">青年干部占比</td>
                            <td class="titleTd" colspan="1" width="58%">备注</td>

                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3" width="10%">现配备<br/>情况</td>
                            <td class="titleTd" colspan="1" width="10%">人数</td>
                            <odin:numberEdit property="r1c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                            <td rowspan="6">
                                <textarea id="r1c7" name="r1c7" rows="12" cols="46" readonly></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45岁以下人数</td>
                            <odin:numberEdit property="r2c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">80后人数</td>
                            <odin:numberEdit property="r3c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3">拟提拔<br/>情况</td>
                            <td class="titleTd" colspan="1">人数</td>
                            <odin:numberEdit property="r4c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45岁以下人数</td>
                            <odin:numberEdit property="r5c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1">80后人数</td>
                            <odin:numberEdit property="r6c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">省委组织部干部规划办审核意见</td>
                            <td colspan="5">
                                <odin:radio property="js1396" value="省委组织部干部规划办审核意见同意"
                                            label="省委组织部干部规划办审核意见同意"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js1396" value="省委组织部干部规划办审核意见不同意"
                                            label="省委组织部干部规划办审核意见不同意"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">省委组织部部领导审批意见</td>
                            <td colspan="5">
                                <odin:radio property="js1397" value="省委组织部部领导审批同意" label="省委组织部部领导审批同意"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js1397" value="省委组织部部领导审批不同意" label="省委组织部部领导审批不同意"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">审核意见<br/>落实情况
                            </td>
                            <odin:textarea property="js1398" maxlength="500"
                                           title="审核意见落实情况" colspan="8" rows="4" readonly="true"/>
                        </tr>


                    </table>
                </div>
            </odin:groupBox>

            <odin:groupBox property="qgysGB2" title="市（州）厅级青年干部选配专项预审">
                <div id="qinggan" class="marginbottom0px">
                    <table style="width: 100%">
                        <tr>
                            <td class="titleTd" colspan="2" rowspan="2">项目</td>
                            <td class="titleTd" colspan="7">职级</td>

                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1" width="7%">正厅级</td>
                            <td class="titleTd" colspan="1" width="7%">副厅级</td>
                            <td class="titleTd" colspan="1" width="7%">巡视员</td>
                            <td class="titleTd" colspan="1" width="7%">副巡<br/>视员</td>
                            <td class="titleTd" colspan="1" width="7%">合计</td>
                            <td class="titleTd" colspan="1" width="7%">青年干部占比</td>
                            <td class="titleTd" colspan="1" width="58%">备注</td>

                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3" width="10%">现配备<br/>情况</td>
                            <td class="titleTd" colspan="1" width="10%">人数</td>
                            <odin:numberEdit property="t2r1c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                            <td rowspan="6">
                                <textarea id="t2r1c7" name="t2r1c7" rows="12" cols="46"></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45岁以下人数</td>
                            <odin:numberEdit property="t2r2c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">80后人数</td>
                            <odin:numberEdit property="t2r3c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3">拟提拔<br/>情况</td>
                            <td class="titleTd" colspan="1">人数</td>
                            <odin:numberEdit property="t2r4c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45岁以下人数</td>
                            <odin:numberEdit property="t2r5c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1">80后人数</td>
                            <odin:numberEdit property="t2r6c1" maxlength="5" width="'100%'"
                                             title="正厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c2" maxlength="5" width="'100%'"
                                             title="副厅级" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c3" maxlength="5" width="'100%'"
                                             title="巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c4" maxlength="5" width="'100%'"
                                             title="副巡视员" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c5" maxlength="5" width="'100%'"
                                             title="合计" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c6" maxlength="5" width="'100%'"
                                             title="青年干部占比" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">省委组织部干部规划办审核意见</td>
                            <td colspan="5">
                                <odin:radio property="js9996" value="省委组织部干部规划办审核意见同意"
                                            label="省委组织部干部规划办审核意见同意" />
                            </td>
                            <td colspan="4">
                                <odin:radio property="js9996" value="省委组织部干部规划办审核意见不同意"
                                            label="省委组织部干部规划办审核意见不同意"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">省委组织部部领导审批意见</td>
                            <td colspan="5">
                                <odin:radio property="js9997" value="省委组织部部领导审批同意" label="省委组织部部领导审批同意"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js9997" value="省委组织部部领导审批不同意" label="省委组织部部领导审批不同意"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">审核意见<br/>落实情况
                            </td>
                            <odin:textarea property="js9998" maxlength="500"
                                           title="审核意见落实情况" colspan="8" rows="4" readonly="true"/>
                        </tr>


                    </table>
                </div>
            </odin:groupBox>
            <div id="bbardiv"></div>

        </div>

    </div>
</div>


<script type="text/javascript">
    Ext.onReady(function () {
        if (typeof parentParam != 'undefined') {
            document.getElementById('rbId').value = parentParam.rb_id;
        } else {
            document.getElementById('rbId').value = 'c42981e1-d876-4d5c-9e85-13eb5bad13eb';
        }
        //groupbox框调整
        var jbqkGB = $("#qgysGB .x-fieldset-bwrap");
        jbqkGB.css('width', 870);
        jbqkGB.css('height', 560);
        $("input[type='radio']").parent().parent().parent().css('cssText','width:20px !important');
        $("input[type='radio']").parent().parent().css('cssText','width:20px !important');
        $("input[type='radio']").parent().css('cssText','width:20px !important');
        $("input[type='radio']").css('cssText','width:20px !important');
    });

    function showArow() {
        var block = parseInt(document.getElementById('block').value);
        for (var t = block + 1; t <= 30; t++) { //tr_len是要控制的tr个数
            $("#tr_" + t).hide();
        }
        for (var i = 1; i <= block; i++) { //显示tr个数
            $("#tr_" + i).show();
        }
    }


    function downloadByUUID() {
        var downloadUUID = document.getElementById('docpath').value;
        window.location = '<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid=' + downloadUUID;
        return false
    }

    function ShowCellCover(elementId, titles, msgs) {
        Ext.MessageBox.buttonText.ok = "关闭";
        if (elementId.indexOf("start") != -1) {

            Ext.MessageBox.show({
                title: titles,
                msg: msgs,
                width: 300,
                height: 300,
                closable: false,
                modal: true,
                progress: true,
                wait: true,
                animEl: 'elId',
                increment: 5,
                waitConfig: {interval: 150}
            });
        }
    }

    /*输出 表*/
    function exportExcel(obj) {
        var rbId = document.getElementById("rbId").value;
        var buttonid = obj.id;
        var buttontext = obj.text;
        //alert(param);
        var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ZSYS&eventNames=ExpGird';
        //alert(path);
        ShowCellCover('start', '系统提示', '正在输出表册 ,请您稍等...');
        Ext.Ajax.request({
            timeout: 60000,
            url: path,
            async: true,
            method: "post",
            form: 'commform',
            params: {rbId: rbId, buttonid: buttonid, buttontext: buttontext},
            callback: function (options, success, response) {
                if (success) {
                    Ext.Msg.hide();
                    var result = response.responseText;
                    if (result) {
                        var cfg = Ext.util.JSON.decode(result);
                        if (0 == cfg.messageCode) {
                            if ("操作成功！" != cfg.mainMessage) {
                                Ext.Msg.alert('系统提示:', cfg.mainMessage);
                                return;
                            }
                            if (cfg.elementsScript != "") {
                                if (cfg.elementsScript.indexOf("\n") > 0) {
                                    cfg.elementsScript = cfg.elementsScript.replace(/\r/gi, "");
                                    cfg.elementsScript = cfg.elementsScript.replace(/\n/gi, "\\n");
                                }

                                //console.log(cfg.elementsScript);
                                eval(cfg.elementsScript);
                            } else {


                            }

                        } else {
                            Ext.Msg.alert('系统提示:', cfg.mainMessage);
                            return;
                        }
                    }
                }
            }
        });
    }

</script>
