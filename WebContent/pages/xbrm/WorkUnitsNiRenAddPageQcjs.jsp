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
    <odin:buttonForToolBar text="����" id="save" handler="saveNm" isLast="true" icon="images/save.gif"></odin:buttonForToolBar>
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
<odin:hidden property="subproperty"></odin:hidden>

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
                                       height="330" title="" pageSize="50" >
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
                    </td>
                </tr>
            </table>
        </td>
    </tr>
  	
</table>

<odin:hidden property="a0281" title="�������"/>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a0200s"/>

<script>
function changea0201a(value, params, record, rowIndex, colIndex, ds) {
    if (record.data.a0201b == '-1') {
        return '<a title="' + value + '(������)">' + value + '(������)</a>';
    } else {
        return '<a title="' + value + '">' + value + '</a>';
    }
}

/* function saveNm(){
	radow.doEvent('save');
} */
function saveNm(){
	var grid=Ext.getCmp('WorkUnitsGrid');
	var store=grid.getStore();
	var num=0;
	var a0200s="";
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var demo=record.data.demo;
		var a0200=record.data.a0200;
		if(demo == true){
			num=num+1;
			a0200s=a0200s+a0200+',';
		}
	}
	if(num==0){
		$h.alert('ϵͳ��ʾ','�빴ѡ�󱣴�!',null,220);
	}
	document.getElementById('a0200s').value=a0200s;
	radow.doEvent('saveCheck2.onclick');
}

function setValueToParent(json) {
	var subproperty=document.getElementById('subproperty').value;
	if('' == subproperty){
		realParent.document.getElementById("a5369").value=json.a0200;
	    realParent.document.getElementById("js0117").value = json.showWord;
	    realParent.document.getElementById("js0117a").value = json.showWord2;
	    realParent.document.getElementById("js0123").value = json.a0200;
	}else{
		realParent.document.getElementById(subproperty).value=json.showWord;
		realParent.document.getElementById(subproperty+"a").value=json.showWord2;
		realParent.document.getElementById(subproperty+"b").value=json.a0200;
	}
	
	
    window.close();
}

</script>
