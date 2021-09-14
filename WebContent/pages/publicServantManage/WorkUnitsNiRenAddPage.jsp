<%@page import="com.insigma.odin.framework.persistence.HBUtil" %>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface" %>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel" %>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String ctxPath = request.getContextPath();
%>
<%@page import="java.util.List" %>
<style>
    <%=FontConfigPageModel.getFontConfig()%>
    .inline {
        display: inline;
    }

    .pl {
        margin-left: 8px;
    }

    .savecls {
        position: absolute;
        left: 25px;
        top: 20px;
        z-index: 100000;
    }

    * html {
        background-image: url(about:blank);
        background-attachment: fixed;
    }
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript">

    function deleteRowRenderer(value, params, record, rowIndex, colIndex, ds) {
        var a0200 = record.data.a0200;
        if (realParent.buttonDisabled) {
            return "ɾ��";
        }
        var fieldsDisabled = <%=TableColInterface.getAllUpdateData()%>;
        if (fieldsDisabled == '' || fieldsDisabled == undefined) {
            return "<a href=\"javascript:deleteRow2(&quot;" + a0200 + "&quot;)\">ɾ��</a>";
        }
        var datas = fieldsDisabled.toString().split(',');
        for (var i = 0; i < datas.length; i++) {
            if (datas[i] == ("a0201a") || datas[i] == ("a0201b") || datas[i] == ("a0201d") || datas[i] == ("a0201e") || datas[i] == ("a0215a") || datas[i] == ("a0219") || datas[i] == ("a0223") || datas[i] == ("a0225") || datas[i] == ("a0243") || datas[i] == ("a0245") || datas[i] == ("a0247") || datas[i] == ("a0251b") || datas[i] == ("a0255") || datas[i] == ("a0265") || datas[i] == ("a0267") || datas[i] == ("a0272") || datas[i] == ("a0281") || datas[i] == ("a0222") || datas[i] == ("a0279") || datas[i] == ("a0504") || datas[i] == ("a0517")) {
                Ext.getCmp("WorkUnitsAddBtn").setDisabled(true);
                return "<u style=\"color:#D3D3D3\">ɾ��</u>";
            }

        }
        return "<a href=\"javascript:deleteRow2(&quot;" + a0200 + "&quot;)\">ɾ��</a>";
    }

    function deleteRow2(a0200) {
        var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
        if (gridSize <= 1) {
            Ext.Msg.alert("ϵͳ��ʾ", "���һ�������޷�ɾ����");
            return;
        }
        Ext.Msg.confirm("ϵͳ��ʾ", "�Ƿ�ȷ��ɾ����", function (id) {
            if ("yes" == id) {
                radow.doEvent('deleteRow', a0200);
            } else {
                return;
            }
        });
    }

    function deleteRow() {
        var sm = Ext.getCmp("WorkUnitsGrid").getSelectionModel();
        if (!sm.hasSelection()) {
            Ext.Msg.alert("ϵͳ��ʾ", "��ѡ��һ�����ݣ�");
            return;
        }
        var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
        if (gridSize <= 1) {
            Ext.Msg.alert("ϵͳ��ʾ", "���һ�������޷�ɾ����");
            return;
        }
        Ext.Msg.confirm("ϵͳ��ʾ", "�Ƿ�ȷ��ɾ����", function (id) {
            if ("yes" == id) {
                radow.doEvent('deleteRow', sm.lastActive + '');
            } else {
                return;
            }
        });
    }

    Ext.onReady(function () {
    });

    //������λְ���������
    function a02checkBoxColClick(rowIndex, colIndex, dataIndex, gridName) {
        if (realParent.buttonDisabled) {
            return;
        }

        var sr = getGridSelected(gridName);

        if (!sr) {
            return;
        }
        radow.doEvent('workUnitsgridchecked', sr.data.a0200 + "," + sr.data.a0281);
    }


    function changeSelectData(item) {
        var a0255f = Ext.getCmp("a0255_combo");
        var newStore = a0255f.getStore();
        newStore.removeAll();
        newStore.add(new Ext.data.Record(item.one));
        newStore.add(new Ext.data.Record(item.two));
        var keya0255 = document.getElementById("a0255").value;//alert(item.one.key+','+keya0255);
        if (item.one.key == keya0255) {
            a0255f.setValue(item.one.value);
        } else if (keya0255 == '') {
            a0255f.setValue(item.one.value);
            document.getElementById("a0255").value = item.one.key;
        } else {
            a0255f.setValue(item.two.value);
            document.getElementById("a0255").value = item.two.key;
        }
    }

    var labelText = {
        'a0255SpanId': ['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>��ְ״̬', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>����״̬'],
        'a0201bSpanId': ['<font color="red">*</font>��ְ��������', '<font color="red">*</font>������������'],
        'a0215aSpanId': ['<font color="red">*</font>ְ������', '<font color="red">*</font>��λ����'],
        'a0221SpanId': ['ְ����', '��λ���'],
        'a0229SpanId': ['�ֹܣ����£�����', '��λ����'],
        'a0243SpanId': ['��ְʱ��', '������ʼ']
    };

    function changeLabel(type) {
        for (var key in labelText) {
            document.getElementById(key).innerHTML = labelText[key][type];
        }
    }

    function a0222SelChangePage(record, index) {
        //��λ���onchangeʱ��ְ���θ�ֵΪ��
        document.getElementById("a0221").value = '';
        document.getElementById("a0221_combo").value = '';
        a0221achange();
        a0222SelChange(record, index)
    }

    function a0222SelChange(record, index) {

        //��λ���
        var a0222 = document.getElementById("a0222").value;
        var a0201b = document.getElementById("a0201b").value;


        document.getElementById("codevalueparameter").value = a0222;

        if ("01" == a0222) {//���ӳ�Ա
            selecteEnable('a0201d', '0');
        } else {
            selecteDisable('a0201d');
        }

        if ("01" == a0222 || "99" == a0222) {
            //����Ա�����չ�����Ա��λor����
            //ְ�����
            selecteEnable('a0219');
            //ְ������
            selecteEnable('a0251');
            //ѡ�����÷�ʽ
            selecteWinEnable('a0247');
            document.getElementById('yimian').style.visibility = "visible";


            changeSelectData({one: {key: '1', value: '����'}, two: {key: '0', value: '����'}});
            changeLabel(0);
        } else if ("02" == a0222 || "03" == a0222) {
            //��ҵ��λ�����λor��ҵ��λרҵ������λ
            //ְ�����disabled
            selecteDisable('a0219');
            //ְ������
            selecteEnable('a0251');
            //ѡ�����÷�ʽ
            selecteWinEnable('a0247');
            document.getElementById('yimian').style.visibility = "visible";
            changeSelectData({one: {key: '1', value: '����'}, two: {key: '0', value: '����'}});
            changeLabel(0);
        } else if ("04" == a0222 || "05" == a0222 || "06" == a0222 || "07" == a0222) {
            //���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
            //ְ�����
            selecteDisable('a0219');
            //ְ������
            selecteDisable('a0251');
            //ѡ�����÷�ʽ
            selecteWinDisable('a0247');
            document.getElementById('yimian').style.visibility = "hidden";
            changeSelectData({one: {key: '1', value: '��ְ'}, two: {key: '0', value: '����ְ'}});
            changeLabel(1);
        } else {
            document.getElementById('yimian').style.visibility = "hidden";
        }
        a0255SelChange();
    }

    function a0255SelChange() {
        var a0222 = document.getElementById("a0222").value;
        if ("04" == a0222 || "05" == a0222 || "06" == a0222 || "07" == a0222) {//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
            return;
        }

        var a0255 = $("input[name='a0255']:checked").val();
        if ("1" == a0255) {//����
            document.getElementById('yimian').style.visibility = "hidden";
        } else if ("0" == a0255) {//����
            document.getElementById('yimian').style.visibility = "visible";
        }

        document.getElementById('a0255').value = a0255;

    }


    function setA0215aValue(record, index) {
        //ְ����
        Ext.getCmp('a0215a').setValue(record.data.value);
    }

    function setA0255Value(record, index) {
        Ext.getCmp('a0255').setValue(record.data.key)
    }

    //a01ͳ�ƹ�ϵ���ڵ�λ
    function setParentValue(record, index) {
        document.getElementById('a0195key').value = record.data.key;
        document.getElementById('a0195value').value = record.data.value;

        var a0195 = document.getElementById("a0195").value;
        radow.doEvent('a0195Change', a0195);

    }

    function witherTwoYear() {

    }

    //a01����
    function setParentA0120Value(record, index) {
        realParent.document.getElementById('a0120').value = record.data.key;
    }

    //a01 ���㹤������
    function setParentA0194Value(record, index) {

    }


    function a0201bChange(record) {

        //��ְ�ṹ��� �� ְ�����ƴ����Ӧ��ϵ
        radow.doEvent('setZB08Code', record.data.key);

        //�����ǰ��Ա��û�С�ͳ�ƹ�ϵ���ڵ�λ�������һ��ǵ�ǰû��ְ��,�����ͳ�ƹ�ϵ���ڵ�λ����ֵΪ��ְ����
        radow.doEvent("a0201bChange", record.data.key);
    }

    function a0251change() {//ְ������  �Ƹ����
        var a0251 = document.getElementById('a0251').value;
        var a0251bOBJ = document.getElementById('a0251b');
        var a0251bTD = document.getElementById('a0251bTD');
        if ('27' == a0251) {
            a0251bOBJ.checked = true;
        }
    }


    function setA0201eDisabled() {

        var a0201d = document.getElementById("a0201d").checked;
        document.getElementById('a0201eSpanId').innerHTML = '��Ա���';


        if (!a0201d) {
            document.getElementById("a0201e_combo").disabled = true;
            document.getElementById("a0201e_combo").style.backgroundColor = "#EBEBE4";
            document.getElementById("a0201e_combo").style.backgroundImage = "none";
            Ext.query("#a0201e_combo+img")[0].style.display = "none";
            document.getElementById("a0201e").value = "";
            document.getElementById("a0201e_combo").value = "";
        } else {
            document.getElementById("a0201e_combo").readOnly = false;
            document.getElementById("a0201e_combo").disabled = false;
            document.getElementById("a0201e_combo").style.backgroundColor = "#fff";
            document.getElementById("a0201e_combo").style.backgroundImage = "url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
            Ext.query("#a0201e_combo+img")[0].style.display = "block";
            if (a0201d) {
                document.getElementById('a0201eSpanId').innerHTML = '<font color="red">*</font>��Ա���';
            }
        }
    }

    $(function () {
        $("#a0201d").change(function () {
            setA0201eDisabled();
        });
    });

</script>


<div id="btnToolBarDiv" style="width: 407px;"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">


    <odin:fill></odin:fill>

    <%--				<odin:buttonForToolBar text="����" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>--%>
    <odin:buttonForToolBar text="����" id="save" isLast="true" icon="images/save.gif"
                           handler="saveA02"></odin:buttonForToolBar>
</odin:toolBar>

<odin:hidden property="a0200" title="����id"></odin:hidden>
<odin:hidden property="a0255" title="��ְ״̬"></odin:hidden>
<odin:hidden property="a0225" title="��Ա������" value="0"></odin:hidden>
<odin:hidden property="a0223" title="��ְ������"></odin:hidden>
<odin:hidden property="a0201c" title="�������"></odin:hidden>
<odin:hidden property="codevalueparameter" title="ְ���κ͸�λ��������"/>
<odin:hidden property="ChangeValue" title="ְ�����ƴ���͵�λ��������"/>
<odin:hidden property="a0271" value=''/>
<odin:hidden property="a0222" value=''/>
<odin:hidden property="a0195key" value=''/>
<odin:hidden property="a0195value" value=''/>
<odin:hidden property="b0194Type" value=''/>
<odin:hidden property="a0192f" title="������λ��ְ��ȫ�ƶ�Ӧ�ģ���ְʱ��"></odin:hidden>


<odin:hidden property="a0195"/>
<odin:hidden property="a0195_val"/>
<odin:hidden property="a0197"/>

<odin:hidden property="a0192a"/>
<odin:hidden property="a0192"/>

<table style="width: 100%">

    <tr>
        <td>
            <table width="410">
                <tr>
                    <td>
                        <odin:editgrid property="WorkUnitsGrid" sm="row" isFirstLoadData="false" url="/"
                                       height="330" title="" pageSize="50" load="oldCheckBox">
                            <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                                <odin:gridDataCol name="demo"/>
                                <odin:gridDataCol name="a0200"/>
                                <odin:gridDataCol name="a0201b"/>
                                <odin:gridDataCol name="a0201a"/>
                                <odin:gridDataCol name="a0215a"/>
                                <odin:gridDataCol name="a0222"/>
                                <odin:gridDataCol name="a0255"/>
                                <odin:gridDataCol name="delete" isLast="true"/>

                            </odin:gridJsonDataModel>
                            <odin:gridColumnModel>
                                <odin:gridRowNumColumn/>
                                <odin:gridEditColumn2 header="ѡ��" width="100" editor="checkbox" dataIndex="demo"
                                                      edited="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="��ְ��������" edited="false" dataIndex="a0201b"
                                                      editor="text" width="300" hidden="true"/>
                                <odin:gridEditColumn2 header="��ְ����" edited="false" dataIndex="a0201a"
                                                      renderer="changea0201a" editor="text" width="300"/>
                                <odin:gridEditColumn2 header="ְ������" edited="false" dataIndex="a0215a" editor="text"
                                                      width="200"/>
                                <odin:gridEditColumn2 header="��λ���" edited="false" dataIndex="a0222" editor="text"
                                                      hidden="true"/>
                                <odin:gridEditColumn2 header="��ְ״̬" edited="false" dataIndex="a0255" codeType="ZB14"
                                                      editor="select" width="160"/>
                                <odin:gridEditColumn hidden="true" header="����" width="100" dataIndex="delete"
                                                     editor="text"
                                                     edited="false" renderer="deleteRowRenderer" isLast="true"/>

                            </odin:gridColumnModel>
                        </odin:editgrid>
                        <table style="display: none">
                            <label><input type="checkbox" checked="checked" id="xsymzw"
                                          onclick="checkChange(this)"/><font
                                    style="font-size: 12px;">��ʾ����ְ��</font></label>
                            <div id="btngroup"></div>
                            <div style="margin-top: 8px;" id="btngroup2"></div>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <table style="display: none">
                <tr>
                    <tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="��ְ����"
                                              property="a0201b" defaultValue="" readonly="true"/>

                </tr>
                <tr align="left">
                    <odin:textEdit property="a0201a" label="��ְ��������" required="true"></odin:textEdit>
                    <!-- <odin:select2 property="a0255" label="��ְ״̬" required="true" onchange="a0255SelChange" value="1" codeType="ZB14"></odin:select2> -->
                    <td align="right"><span id="a0195SpanId_s" style="font-size: 12px;" class="fontConfig"><!-- <font color="red">*</font>��ְ״̬&nbsp; --></span>
                    </td>

                    <td align="left">
                        <input align="middle" type="radio" name="a0255" id="a02551" checked="checked" value="1"
                               class="radioItem"/>
                        <label for="a0255" style="font-size: 12px;">����</label>
                        <span>&nbsp;</span>
                        <input align="middle" type="radio" name="a0255" id="a02550" value="0" class="radioItem"/>
                        <label for="a0255" style="font-size: 12px;">����</label>
                    </td>
                </tr>
                <tr>
                    <odin:textEdit property="a0215a" label="ְ������" required="true" maxlength="50"></odin:textEdit>
                    <!-- <odin:select2 property="a0201d" label="�Ƿ���ӳ�Ա" data="['1','��'],['0','��']" onchange="setA0201eDisabled"></odin:select2> -->

                    <td></td>
                    <td align="left" id="a0219TD">
                        <input type="checkbox" name="a0279" id="a0279"/>
                        <label id="a0279SpanId" for="a0279" style="font-size: 12px;">��ְ��</label>
                        <input type="checkbox" name="a0219" id="a0219"/>
                        <label id="a0219SpanId" for="a0219" style="font-size: 12px;">�쵼ְ��</label>
                    </td>
                </tr>

                <tr align="left">
                    <odin:select2 property="a0201e" label="��Ա���" codeType="ZB129"></odin:select2>
                    <td></td>
                    <td align="left" id="a0201dTD">
                        <input type="checkbox" name="a0201d" id="a0201d"/>
                        <label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">�쵼��Ա</label>
                    </td>
                </tr>
                <tr>
                    <tags:PublicTextIconEdit3 property="a0247" label="ѡ�����÷�ʽ" codetype="ZB122"
                                              readonly="true"></tags:PublicTextIconEdit3>
                    <td></td>
                    <td align="left" id="a0251bTD">
                        <input type="checkbox" name="a0251b" id="a0251b"/>
                        <label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">�Ƹ����</label>
                    </td>
                </tr>
                <tr>
                    <odin:NewDateEditTag property="a0243" labelSpanId="a0243SpanId" maxlength="8" label="��ְʱ��"
                                         required="true"></odin:NewDateEditTag>
                    <odin:textEdit property="a0245" label="��ְ�ĺ�" validator="a0245Length"></odin:textEdit>
                </tr>
                <tr align="left">
                    <odin:hidden property="a0221a" value="0"/>
                </tr>
                <tr align="left">
                    <odin:hidden property="a0229" value="0"/>
                    <odin:hidden property="a0251" value="0"/>
                </tr>


                <tr id='yimian'>
                    <odin:NewDateEditTag property="a0265" label="��ְʱ��" labelSpanId="a0265SpanId" maxlength="8"
                                         required="true"></odin:NewDateEditTag>
                    <odin:textEdit property="a0267" label="��ְ�ĺ�" validator="a0267Length"></odin:textEdit>
                </tr>

                <tr>
                    <%-- <odin:textEdit property="a0272" label="ְ��䶯ԭ������"></odin:textEdit> --%>

                    <odin:textarea property="a0272" label="ְ��䶯ԭ������" colspan='4' rows="4"></odin:textarea>
                </tr>
                <%
                    String sql = CommSQL.getInfoSQL("'A02'");

                    List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
                    if (list.size() > 0) {
                %>
                <tr>
                    <td><odin:button text="������Ϣ" property="SubInfo" handler="openSubWin"></odin:button></td>
                </tr>
                <%} %>
                <tr>
                    <td><br>
                    <td>
                </tr>
                <tr>
                    <td><br>
                    <td>
                </tr>
                <tr>
                    <td><br>
                    <td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td align="right" colspan="4">
            <div id="btngroup3"></div>
        </td>
    </tr>
</table>

<odin:hidden property="a0281" title="�������"/>
<odin:hidden property="a0000" title="��Ա����"/>
<script type="text/javascript">
    function openSubWin() {
        var a0200 = document.getElementById('a0200').value;
        if (a0200 == null || a0200 == '') {
            $h.alert('ϵͳ��ʾ��', '���ȱ���ְ����Ϣ!');
            return;
        }
        $h.openPageModeWin('openSubWin', 'pages.publicServantManage.WorkUnitsSubInfo', 'ְ�񲹳���', 600, 400, document.getElementById('a0200').value, '<%=ctxPath%>')
    }

    Ext.onReady(function () {
        new Ext.Button({
            icon: 'images/icon/arrowup.gif',
            id: 'UpBtn',
            text: '����',
            cls: 'inline',
            renderTo: "btngroup",
            handler: UpBtn
        });
        new Ext.Button({
            icon: 'images/icon/arrowdown.gif',
            id: 'DownBtn',
            text: '����',
            cls: 'inline pl',
            renderTo: "btngroup",
            handler: DownBtn
        });
        new Ext.Button({
            icon: 'images/icon/save.gif',
            id: 'saveSortBtn',
            text: '��������',
            cls: 'inline pl',
            renderTo: "btngroup",
            handler: function () {
                radow.doEvent('worksort');
            }
        });
        new Ext.Button({
            icon: 'images/icon/save.gif',
            id: 'sortUseTimeS',
            text: '����ְʱ������',
            cls: 'inline pl',
            renderTo: "btngroup",
            handler: function () {
                radow.doEvent('sortUseTime');
            }
        });

    });

    //ͳ�ƹ�ϵ���ڵ�λ����ְ������������
    function saveA02() {

        //ͳ�ƹ�ϵ���ڵ�λ����Ϊ��
        var a0195 = document.getElementById('a0195').value;

        if (a0195 == "") {
            Ext.Msg.alert("ϵͳ��ʾ", "ͳ�ƹ�ϵ���ڵ�λ����Ϊ�գ�");
            return;
        }

        //��ְ״̬
        var a0255 = document.getElementById('a0255').value;
        //��ְ��������
        var a0201a = document.getElementById('a0201a').value;
        var a0201b = document.getElementById('a0201b').value;
        var a0195 = document.getElementById('a0195').value;
        if (a0201b == "") {
            Ext.Msg.alert("ϵͳ��ʾ", "���ȵ��ͼ�������ְ������ѡ��");
            return;
        }

        if (a0201a == "") {
            Ext.Msg.alert("ϵͳ��ʾ", "��ְ�������Ʋ���Ϊ�գ�");
            return;
        }

        //��ְʱ��
        var a0243 = document.getElementById('a0243').value;
        var a0243_1 = document.getElementById('a0243_1').value;

        if (!a0243_1) {
            $h.alert('ϵͳ��ʾ', '��ְʱ�䲻��Ϊ�գ�', null, 200);
            return false;
        }

        var text1 = dateValidateBeforeTady(a0243_1);
        if (a0243_1.indexOf(".") > 0) {
            text1 = dateValidateBeforeTady(a0243);
        }
        if (text1 !== true) {
            $h.alert('ϵͳ��ʾ', '��ְʱ�䣺' + text1, null, 400);
            return false;
        }

        //��ְʱ��
        var a0265 = document.getElementById('a0265').value;
        var a0265_1 = document.getElementById('a0265_1').value;

        //���ְ��Ϊ���⣬����ְʱ�䲻��Ϊ��
        if (a0255 != null && a0255 == 0) {
            if (!a0265_1) {
                $h.alert('ϵͳ��ʾ', '��ְʱ�䲻��Ϊ�գ�', null, 200);
                return false;
            }
        }


        var text2 = dateValidateBeforeTady(a0265_1);
        if (a0265_1.indexOf(".") > 0) {
            text2 = dateValidateBeforeTady(a0265);
        }
        if (text2 !== true) {
            $h.alert('ϵͳ��ʾ', '��ְʱ�䣺' + text2, null, 400);
            return false;
        }

        //"ְ��䶯ԭ������"���ɳ���50��
        var a0272 = document.getElementById('a0272').value;

        if (a0272.length > 50) {
            Ext.Msg.alert("ϵͳ��ʾ", "ְ��䶯ԭ���������ɳ���50�֣�");
            return;
        }

        radow.doEvent('save');
    }
</script>
<script>

    function changea0201a(value, params, record, rowIndex, colIndex, ds) {
        if (record.data.a0201b == '-1') {
            return '<a title="' + value + '(������)">' + value + '(������)</a>';
        } else {
            return '<a title="' + value + '">' + value + '</a>';
        }
    }

    function seta0255Value(value, params, record, rowIndex, colIndex, ds) {
        var a0222 = record.data.a0222;
        var textValue = '';
        if ("01" == a0222 || "99" == a0222 || "02" == a0222 || "03" == a0222) {
            textValue = getTextValue({one: {key: '1', value: '����'}, two: {key: '0', value: '����'}}, value);
        } else if ("04" == a0222 || "05" == a0222 || "06" == a0222 || "07" == a0222) {//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
            textValue = getTextValue({one: {key: '1', value: '��ְ'}, two: {key: '0', value: '����ְ'}}, value);
        }
        return '<a title="' + textValue + '">' + textValue + '</a>';
    }

    function getTextValue(item, v) {
        if (item.one.key == v) {
            return item.one.value;
        } else {
            return item.two.value;
        }
    }

    function checkChange() {
        var checkbox = document.getElementById("xsymzw");
        var grid = Ext.getCmp("WorkUnitsGrid");
        var store = grid.getStore();
        var vibility;
        if (checkbox.checked) {
            vibility = "block";
        } else {
            vibility = "none";
        }

        var len = store.data.length;
        for (var i = 0; i < len; i++) {
            var data = store.getAt(i).data;
            var a0255 = data.a0255;//��ְ״̬
            if (a0255 == '0') {
                grid.getView().getRow(i).style.display = vibility;
            }
        }
    }

    function UpBtn() {
        var grid = odin.ext.getCmp('WorkUnitsGrid');

        var sm = grid.getSelectionModel().getSelections();
        var store = grid.store;

        if (sm.length <= 0) {

            $h.alert('ϵͳ��ʾ��', '��ѡ����Ҫ�����ְ��', null, 180);
            return;
        }

        //ѡ�����еĵ�һ��
        var selectdata = sm[0];
        var index = store.indexOf(selectdata);
        if (index == 0) {

            $h.alert('ϵͳ��ʾ��', '��ְ���Ѿ�������ϣ�', null, 180);
            return;
        }
        //�Ƴ�
        store.remove(selectdata);
        //���뵽��һ��ǰ��
        store.insert(index - 1, selectdata);

        //ѡ�����ƶ������
        grid.getSelectionModel().selectRow(index - 1, true);

        grid.getView().refresh();
    }

    function DownBtn() {
        var grid = odin.ext.getCmp('WorkUnitsGrid');

        var sm = grid.getSelectionModel().getSelections();
        var store = grid.store;
        if (sm.length <= 0) {

            $h.alert('ϵͳ��ʾ��', '��ѡ����Ҫ�����ְ��', null, 180);
            return;
        }

        //ѡ�����еĵ�һ��
        var selectdata = sm[0];
        var index = store.indexOf(selectdata);
        var total = store.getCount();
        if (index == (total - 1)) {

            $h.alert('ϵͳ��ʾ��', '��ְ���Ѿ���������£�', null, 180);
            return;
        }

        //�Ƴ�
        store.remove(selectdata);
        //���뵽��һ��ǰ��
        store.insert(index + 1, selectdata);
        //ѡ�����ƶ������
        grid.getSelectionModel().selectRow(index + 1, true);
        grid.view.refresh();
    }

    Ext.onReady(function () {

        var pgrid = Ext.getCmp("WorkUnitsGrid");


        var bbar = pgrid.getBottomToolbar();

        var dstore = pgrid.getStore();
        var firstload = true;
        dstore.on({
            load: {
                fn: function () {
                    checkChange();
                    if (firstload) {
                        $h.selectGridRow('WorkUnitsGrid', 0);
                        firstload = false;
                    }
                }
            },
            scope: this
        });

        var ddrow = new Ext.dd.DropTarget(pgrid.container, {
            ddGroup: 'GridDD',
            copy: false,
            notifyDrop: function (dd, e, data) {
                //ѡ���˶�����
                var rows = data.selections;
                //�϶����ڼ���
                var index = dd.getDragData(e).rowIndex;
                if (typeof (index) == "undefined") {
                    return;
                }
                //�޸�store
                for (i = 0; i < rows.length; i++) {
                    var rowData = rows[i];
                    if (!this.copy) dstore.remove(rowData);
                    dstore.insert(index, rowData);
                }
                pgrid.view.refresh();
                radow.doEvent('worksort');
            }
        });


    });


    function openSortWin() {
        var a0201b = document.getElementById("a0201b").value;
        if (a0201b == '') {
            $h.alert('ϵͳ��ʾ��', '����ѡ�����!');
            return;
        }
        realParent.window.a0201b = a0201b;
        $h.openPageModeWin('A01SortGrid', 'pages.publicServantManage.PersonSort', '����������', 650, 450, document.getElementById('a0000').value, '<%=ctxPath%>', window);
    }


    Ext.onReady(function () {


        document.getElementById("a0000").value = realParent.document.getElementById("a0000").value;

        $h.applyFontConfig($h.spFeildAll.a02);
        $h.applyFontConfig($h.spFeildAll.a01);
        if (realParent.buttonDisabled) {
            $h.setDisabled($h.disabledButtons.a02);


        }


        Ext.getCmp('WorkUnitsGrid').setWidth(400);
        Ext.getCmp('WorkUnitsGrid').setHeight(250);

        document.getElementById('a0201bSpanId').innerHTML = '<font color="red">*</font>��ְ����';

        //�Ƿ����ε�����
        a0255SelChange();
        $(".radioItem").change(function () {
            var a0255 = $("input[name='a0255']:checked").val();
            if ("1" == a0255) {
                //����
                document.getElementById('yimian').style.visibility = "hidden";
            } else if ("0" == a0255) {//����
                document.getElementById('yimian').style.visibility = "visible";
            }
            document.getElementById('a0255').value = a0255;
        })

        //������ְ����ѡ�����
        document.getElementById('a0201b_combo').style.width = "190px";
        document.getElementById('a0201a').style.width = "207px";
        //����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
        $h.fieldsDisabled(realParent.fieldsDisabled);
        //����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
        var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
        $h.selectDisabled(realParent.selectDisabled, imgdata);
        //a0195��������
        for (var i = 0; i < realParent.fieldsDisabled.length; i++) {
            if (realParent.fieldsDisabled[i] == 'a0195') {
                if (formobj = Ext.getCmp(realParent.fieldsDisabled[i] + '_combo')) {
                    formobj.disable();
                    var img = Ext.query("#" + realParent.fieldsDisabled[i] + "_combo+img")[0];
                    img.onclick = null;
                    $('#' + realParent.fieldsDisabled[i] + '_combo').parent().removeClass('x-item-disabled');
                    $('#' + realParent.fieldsDisabled[i] + '_combo').addClass('bgclor');
                }
            }
        }
        for (var i = 0; i < realParent.selectDisabled.length; i++) {
            if (realParent.selectDisabled[i] == 'a0195') {
                if (formobj = Ext.getCmp(realParent.selectDisabled[i] + '_combo')) {
                    var div = document.createElement("div");
                    div.style.border = "1px solid rgb(192,192,192)";
                    div.style.width = document.getElementById(realParent.selectDisabled[i] + '_combo').offsetWidth;
                    div.style.height = document.getElementById(realParent.selectDisabled[i] + '_combo').offsetHeight;
                    div.style.position = "absolute";
                    div.style.left = $('#' + realParent.selectDisabled[i] + '_combo').offset().left + 'px';
                    div.style.top = $('#' + realParent.selectDisabled[i] + '_combo').offset().top + 'px';
                    div.style.backgroundImage = imgdata;
                    div.style.backgroundRepeat = "no-repeat";
                    div.style.backgroundColor = "white";
                    div.style.backgroundPosition = "center";
                    document.body.appendChild(div);


                    formobj.disable();
                    var img = Ext.query("#" + realParent.selectDisabled[i] + "_combo+img")[0];
                    //img.style.display="none";
                    img.onclick = null;
                    $('#' + realParent.selectDisabled[i] + '_combo').parent().removeClass('x-item-disabled');
                    $('#' + realParent.selectDisabled[i] + '_combo').addClass('bgclor');
                }
            }
        }
    });


    function changea0201d(type) {
        if (type == '1') {
        }
        if (type == '2') {
            document.getElementById('a0201dSpanId').innerHTML = '�쵼��Ա';
            document.getElementById('a0201eSpanId').innerHTML = '��Ա���';
        }
    }

    Ext.onReady(function () {

    });

    function nosystem() {

    }

    var objs = {};
    var rowIndexs = {};
    var colIndexs = {};
    var colNames = {};
    var gridIds = {};

    //���һ�β���box�仯
    function changebox() {
        var obj = objs.obj;
        var rowIndex = rowIndexs.rowIndex;
        var colIndex = colIndexs.colIndex;
        var colName = colNames.colName;
        var gridId = gridIds.gridId;
        if (obj.className == 'x-grid3-check-col') {
            if (typeof (gridId) == 'undefined' || (typeof (gridId) == 'string' && gridId == '')) {
                odin.checkboxds.getAt(rowIndex).set(colName, true);
            } else {
                odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
            }
            obj.className = 'x-grid3-check-col-on';
        } else {
            if (typeof (gridId) == 'undefined' || (typeof (gridId) == 'string' && gridId == '')) {
                odin.checkboxds.getAt(rowIndex).set(colName, false);
            } else {
                odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
                if (document.getElementById("selectall_" + gridId + "_" + colName) != null) {
                    document.getElementById("selectall_" + gridId + "_" + colName).value = 'false';
                    document.getElementById("selectall_" + gridId + "_" + colName).className = 'x-grid3-check-col';
                }
            }
            obj.className = 'x-grid3-check-col';
        }
    }

    function lockINFO() {
        Ext.getCmp("WorkUnitsAddBtn").disable();
        Ext.getCmp("save").disable();
        Ext.getCmp("UpdateTitleBtn").disable();
        Ext.getCmp("personGRIDSORT").disable();
        Ext.getCmp("UpBtn").disable();
        Ext.getCmp("DownBtn").disable();
        Ext.getCmp("saveSortBtn").disable();
        Ext.getCmp("sortUseTimeS").disable();
        Ext.getCmp("WorkUnitsGrid").getColumnModel().setHidden(8, true);
    }

    function setValueToParent(json) {
        realParent.document.getElementById("nmzwid").value = json.a0200;
        realParent.document.getElementById("a0192a").value = json.showWord;

        window.close();
    }

    function oldCheckBox() {
        var nmzwid = realParent.document.getElementById("nmzwid").value;
        var beforeList = Ext.getCmp("WorkUnitsGrid").store;
        for (var i = 0; i < beforeList.getCount(); i++) {
            if (nmzwid.indexOf(beforeList.data.itemAt(i).get("a0200")) != -1) {
                beforeList.data.itemAt(i).set("demo", true);
            }
        }

    }

</script>

<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>
<div id="cover_wrap3"></div>
