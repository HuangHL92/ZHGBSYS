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
        font-family: '����', Simsun;
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

    /*���ڿ�  */
    .GBx-fieldset .x-form-trigger {
        right: 0px;
    }

    /*ͼ�����  */
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

<odin:hidden property="rbId" title="����id"/>
<odin:hidden property="block" value="" title="���Ԥ����ʾ����"/>
<odin:hidden property="docpath" title="�ĵ���ַ"/>

<odin:toolBar property="bbar" applyTo="bbardiv">
    <!-- ���Ԥ�� -->
    <odin:fill></odin:fill>
    <odin:buttonForToolBar text="�������Ԥ���" icon="images/icon/save.gif" id="saveQG" isLast="true"/>
</odin:toolBar>

<div style="width: 100%; margin-top: 3px; overflow-y: scroll; height: 600px;">
    <div id="peopleInfo" style="float: left; margin-left: 3px; " class="GBx-fieldset">
        <div id="tabs-12" class="GBx-fieldset GBxDis">
            <odin:groupBox property="qgysGB" title="���Ԥ��">
                <div id="qinggan" class="marginbottom0px">
                    <table style="width: 100%">
                        <tr>
                            <td class="titleTd" colspan="2" rowspan="2">��Ŀ</td>
                            <td class="titleTd" colspan="7">ְ��</td>

                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1" width="7%">������</td>
                            <td class="titleTd" colspan="1" width="7%">������</td>
                            <td class="titleTd" colspan="1" width="7%">Ѳ��Ա</td>
                            <td class="titleTd" colspan="1" width="7%">��Ѳ<br/>��Ա</td>
                            <td class="titleTd" colspan="1" width="7%">�ϼ�</td>
                            <td class="titleTd" colspan="1" width="7%">����ɲ�ռ��</td>
                            <td class="titleTd" colspan="1" width="58%">��ע</td>

                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3" width="10%">���䱸<br/>���</td>
                            <td class="titleTd" colspan="1" width="10%">����</td>
                            <odin:numberEdit property="r1c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r1c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            <td rowspan="6">
                                <textarea id="r1c7" name="r1c7" rows="12" cols="46" readonly></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45����������</td>
                            <odin:numberEdit property="r2c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r2c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">80������</td>
                            <odin:numberEdit property="r3c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r3c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3">�����<br/>���</td>
                            <td class="titleTd" colspan="1">����</td>
                            <odin:numberEdit property="r4c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r4c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45����������</td>
                            <odin:numberEdit property="r5c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r5c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1">80������</td>
                            <odin:numberEdit property="r6c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="r6c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">ʡί��֯���ɲ��滮��������</td>
                            <td colspan="5">
                                <odin:radio property="js1396" value="ʡί��֯���ɲ��滮��������ͬ��"
                                            label="ʡί��֯���ɲ��滮��������ͬ��"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js1396" value="ʡί��֯���ɲ��滮����������ͬ��"
                                            label="ʡί��֯���ɲ��滮����������ͬ��"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">ʡί��֯�����쵼�������</td>
                            <td colspan="5">
                                <odin:radio property="js1397" value="ʡί��֯�����쵼����ͬ��" label="ʡί��֯�����쵼����ͬ��"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js1397" value="ʡί��֯�����쵼������ͬ��" label="ʡί��֯�����쵼������ͬ��"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">������<br/>��ʵ���
                            </td>
                            <odin:textarea property="js1398" maxlength="500"
                                           title="��������ʵ���" colspan="8" rows="4" readonly="true"/>
                        </tr>


                    </table>
                </div>
            </odin:groupBox>

            <odin:groupBox property="qgysGB2" title="�У��ݣ���������ɲ�ѡ��ר��Ԥ��">
                <div id="qinggan" class="marginbottom0px">
                    <table style="width: 100%">
                        <tr>
                            <td class="titleTd" colspan="2" rowspan="2">��Ŀ</td>
                            <td class="titleTd" colspan="7">ְ��</td>

                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1" width="7%">������</td>
                            <td class="titleTd" colspan="1" width="7%">������</td>
                            <td class="titleTd" colspan="1" width="7%">Ѳ��Ա</td>
                            <td class="titleTd" colspan="1" width="7%">��Ѳ<br/>��Ա</td>
                            <td class="titleTd" colspan="1" width="7%">�ϼ�</td>
                            <td class="titleTd" colspan="1" width="7%">����ɲ�ռ��</td>
                            <td class="titleTd" colspan="1" width="58%">��ע</td>

                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3" width="10%">���䱸<br/>���</td>
                            <td class="titleTd" colspan="1" width="10%">����</td>
                            <odin:numberEdit property="t2r1c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r1c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                            <td rowspan="6">
                                <textarea id="t2r1c7" name="t2r1c7" rows="12" cols="46"></textarea>
                            </td>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45����������</td>
                            <odin:numberEdit property="t2r2c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r2c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">80������</td>
                            <odin:numberEdit property="t2r3c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r3c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1" rowspan="3">�����<br/>���</td>
                            <td class="titleTd" colspan="1">����</td>
                            <odin:numberEdit property="t2r4c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r4c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td class="titleTd" colspan="1">45����������</td>
                            <odin:numberEdit property="t2r5c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r5c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>
                        <tr>
                            <td class="titleTd" colspan="1">80������</td>
                            <odin:numberEdit property="t2r6c1" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c2" maxlength="5" width="'100%'"
                                             title="������" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c3" maxlength="5" width="'100%'"
                                             title="Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c4" maxlength="5" width="'100%'"
                                             title="��Ѳ��Ա" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c5" maxlength="5" width="'100%'"
                                             title="�ϼ�" colspan="1" readonly="true"/>
                            <odin:numberEdit property="t2r6c6" maxlength="5" width="'100%'"
                                             title="����ɲ�ռ��" colspan="1" readonly="true"/>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">ʡί��֯���ɲ��滮��������</td>
                            <td colspan="5">
                                <odin:radio property="js9996" value="ʡί��֯���ɲ��滮��������ͬ��"
                                            label="ʡί��֯���ɲ��滮��������ͬ��" />
                            </td>
                            <td colspan="4">
                                <odin:radio property="js9996" value="ʡί��֯���ɲ��滮����������ͬ��"
                                            label="ʡί��֯���ɲ��滮����������ͬ��"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">ʡί��֯�����쵼�������</td>
                            <td colspan="5">
                                <odin:radio property="js9997" value="ʡί��֯�����쵼����ͬ��" label="ʡί��֯�����쵼����ͬ��"/>
                            </td>
                            <td colspan="4">
                                <odin:radio property="js9997" value="ʡί��֯�����쵼������ͬ��" label="ʡί��֯�����쵼������ͬ��"/>
                            </td>
                        </tr>

                        <tr>
                            <td colspan="1" class="titleTd">������<br/>��ʵ���
                            </td>
                            <odin:textarea property="js9998" maxlength="500"
                                           title="��������ʵ���" colspan="8" rows="4" readonly="true"/>
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
        //groupbox�����
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
        for (var t = block + 1; t <= 30; t++) { //tr_len��Ҫ���Ƶ�tr����
            $("#tr_" + t).hide();
        }
        for (var i = 1; i <= block; i++) { //��ʾtr����
            $("#tr_" + i).show();
        }
    }


    function downloadByUUID() {
        var downloadUUID = document.getElementById('docpath').value;
        window.location = '<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid=' + downloadUUID;
        return false
    }

    function ShowCellCover(elementId, titles, msgs) {
        Ext.MessageBox.buttonText.ok = "�ر�";
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

    /*��� ��*/
    function exportExcel(obj) {
        var rbId = document.getElementById("rbId").value;
        var buttonid = obj.id;
        var buttontext = obj.text;
        //alert(param);
        var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ZSYS&eventNames=ExpGird';
        //alert(path);
        ShowCellCover('start', 'ϵͳ��ʾ', '���������� ,�����Ե�...');
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
                            if ("�����ɹ���" != cfg.mainMessage) {
                                Ext.Msg.alert('ϵͳ��ʾ:', cfg.mainMessage);
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
                            Ext.Msg.alert('ϵͳ��ʾ:', cfg.mainMessage);
                            return;
                        }
                    }
                }
            }
        });
    }

</script>
