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
            return "删除";
        }
        var fieldsDisabled = <%=TableColInterface.getAllUpdateData()%>;
        if (fieldsDisabled == '' || fieldsDisabled == undefined) {
            return "<a href=\"javascript:deleteRow2(&quot;" + a0200 + "&quot;)\">删除</a>";
        }
        var datas = fieldsDisabled.toString().split(',');
        for (var i = 0; i < datas.length; i++) {
            if (datas[i] == ("a0201a") || datas[i] == ("a0201b") || datas[i] == ("a0201d") || datas[i] == ("a0201e") || datas[i] == ("a0215a") || datas[i] == ("a0219") || datas[i] == ("a0223") || datas[i] == ("a0225") || datas[i] == ("a0243") || datas[i] == ("a0245") || datas[i] == ("a0247") || datas[i] == ("a0251b") || datas[i] == ("a0255") || datas[i] == ("a0265") || datas[i] == ("a0267") || datas[i] == ("a0272") || datas[i] == ("a0281") || datas[i] == ("a0222") || datas[i] == ("a0279") || datas[i] == ("a0504") || datas[i] == ("a0517")) {
                Ext.getCmp("WorkUnitsAddBtn").setDisabled(true);
                return "<u style=\"color:#D3D3D3\">删除</u>";
            }
        }
        return "<a href=\"javascript:deleteRow2(&quot;" + a0200 + "&quot;)\">删除</a>";
    }

    function deleteRow2(a0200) {
        var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
        if (gridSize <= 1) {
            Ext.Msg.alert("系统提示", "最后一条数据无法删除！");
            return;
        }
        Ext.Msg.confirm("系统提示", "是否确认删除？", function (id) {
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
            Ext.Msg.alert("系统提示", "请选择一行数据！");
            return;
        }
        var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
        if (gridSize <= 1) {
            Ext.Msg.alert("系统提示", "最后一条数据无法删除！");
            return;
        }
        Ext.Msg.confirm("系统提示", "是否确认删除？", function (id) {
            if ("yes" == id) {
                radow.doEvent('deleteRow', sm.lastActive + '');
            } else {
                return;
            }
        });
    }

    Ext.onReady(function () {
    	
    });

    //工作单位职务输出设置
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
        'a0255SpanId': ['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>任职状态', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>工作状态'],
        'a0201bSpanId': ['<font color="red">*</font>任职机构名称', '<font color="red">*</font>工作机构名称'],
        'a0215aSpanId': ['<font color="red">*</font>职务名称', '<font color="red">*</font>岗位名称'],
        'a0221SpanId': ['职务层次', '岗位层次'],
        'a0229SpanId': ['分管（从事）工作', '岗位工作'],
        'a0243SpanId': ['任职时间', '工作开始']
    };

    function changeLabel(type) {
        for (var key in labelText) {
            document.getElementById(key).innerHTML = labelText[key][type];
        }
    }

    function a0222SelChangePage(record, index) {
        //岗位类别onchange时，职务层次赋值为空
        document.getElementById("a0221").value = '';
        document.getElementById("a0221_combo").value = '';
        a0221achange();
        a0222SelChange(record, index)
    }

    function a0222SelChange(record, index) {

        //岗位类别
        var a0222 = document.getElementById("a0222").value;
        var a0201b = document.getElementById("a0201b").value;


        document.getElementById("codevalueparameter").value = a0222;

        if ("01" == a0222) {//班子成员
            selecteEnable('a0201d', '0');
        } else {
            selecteDisable('a0201d');
        }

        if ("01" == a0222 || "99" == a0222) {
            //公务员、参照管理人员岗位or其他
            //职务类别
            selecteEnable('a0219');
            //职动类型
            selecteEnable('a0251');
            //选拔任用方式
            selecteWinEnable('a0247');
            document.getElementById('yimian').style.visibility = "visible";


            changeSelectData({one: {key: '1', value: '在任'}, two: {key: '0', value: '已免'}});
            changeLabel(0);
        } else if ("02" == a0222 || "03" == a0222) {
            //事业单位管理岗位or事业单位专业技术岗位
            //职务类别disabled
            selecteDisable('a0219');
            //职动类型
            selecteEnable('a0251');
            //选拔任用方式
            selecteWinEnable('a0247');
            document.getElementById('yimian').style.visibility = "visible";
            changeSelectData({one: {key: '1', value: '在任'}, two: {key: '0', value: '已免'}});
            changeLabel(0);
        } else if ("04" == a0222 || "05" == a0222 || "06" == a0222 || "07" == a0222) {
            //机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
            //职务类别
            selecteDisable('a0219');
            //职动类型
            selecteDisable('a0251');
            //选拔任用方式
            selecteWinDisable('a0247');
            document.getElementById('yimian').style.visibility = "hidden";
            changeSelectData({one: {key: '1', value: '在职'}, two: {key: '0', value: '不在职'}});
            changeLabel(1);
        } else {
            document.getElementById('yimian').style.visibility = "hidden";
        }
        a0255SelChange();
    }

    function a0255SelChange() {
        var a0222 = document.getElementById("a0222").value;
        if ("04" == a0222 || "05" == a0222 || "06" == a0222 || "07" == a0222) {//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
            return;
        }

        var a0255 = $("input[name='a0255']:checked").val();
        if ("1" == a0255) {//在任
            document.getElementById('yimian').style.visibility = "hidden";
        } else if ("0" == a0255) {//以免
            document.getElementById('yimian').style.visibility = "visible";
        }

        document.getElementById('a0255').value = a0255;

    }


    function setA0215aValue(record, index) {
        //职务简称
        Ext.getCmp('a0215a').setValue(record.data.value);
    }

    function setA0255Value(record, index) {
        Ext.getCmp('a0255').setValue(record.data.key)
    }

    //a01统计关系所在单位
    function setParentValue(record, index) {
        document.getElementById('a0195key').value = record.data.key;
        document.getElementById('a0195value').value = record.data.value;

        var a0195 = document.getElementById("a0195").value;
        radow.doEvent('a0195Change', a0195);

    }

    function witherTwoYear() {

    }

    //a01级别
    function setParentA0120Value(record, index) {
        realParent.document.getElementById('a0120').value = record.data.key;
    }

    //a01 基层工作年限
    function setParentA0194Value(record, index) {

    }


    function a0201bChange(record) {

        //任职结构类别 和 职务名称代码对应关系
        radow.doEvent('setZB08Code', record.data.key);

        //如果当前人员还没有“统计关系所在单位”，并且还是当前没有职务,则给“统计关系所在单位”赋值为任职机构
        radow.doEvent("a0201bChange", record.data.key);
    }

    function a0251change() {//职动类型  破格晋升
        var a0251 = document.getElementById('a0251').value;
        var a0251bOBJ = document.getElementById('a0251b');
        var a0251bTD = document.getElementById('a0251bTD');
        if ('27' == a0251) {
            a0251bOBJ.checked = true;
        }
    }


    function setA0201eDisabled() {

        var a0201d = document.getElementById("a0201d").checked;
        document.getElementById('a0201eSpanId').innerHTML = '成员类别';


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
                document.getElementById('a0201eSpanId').innerHTML = '<font color="red">*</font>成员类别';
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
    <odin:buttonForToolBar text="保存" id="save" handler="saveNm" isLast="true" icon="images/save.gif"></odin:buttonForToolBar>
</odin:toolBar>

<odin:hidden property="a0200" title="主键id"></odin:hidden>
<odin:hidden property="a0255" title="任职状态"></odin:hidden>
<odin:hidden property="a0225" title="成员内排序" value="0"></odin:hidden>
<odin:hidden property="a0223" title="多职务排序"></odin:hidden>
<odin:hidden property="a0201c" title="机构简称"></odin:hidden>
<odin:hidden property="codevalueparameter" title="职务层次和岗位类别的联动"/>
<odin:hidden property="ChangeValue" title="职务名称代码和单位类别的联动"/>
<odin:hidden property="a0271" value=''/>
<odin:hidden property="a0222" value=''/>
<odin:hidden property="a0195key" value=''/>
<odin:hidden property="a0195value" value=''/>
<odin:hidden property="b0194Type" value=''/>
<odin:hidden property="a0192f" title="工作单位及职务全称对应的，任职时间"></odin:hidden>
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
                                <odin:gridEditColumn2 header="选中" width="100" editor="checkbox" dataIndex="demo"
                                                      edited="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="任职机构代码" edited="false" dataIndex="a0201b"
                                                      editor="text" width="300" hidden="true"/>
                                <odin:gridEditColumn2 header="任职机构" edited="false" dataIndex="a0201a"
                                                      renderer="changea0201a" editor="text" width="300"/>
                                <odin:gridEditColumn2 header="职务名称" edited="false" dataIndex="a0215a" editor="text"
                                                      width="200"/>
                                <odin:gridEditColumn2 header="岗位类别" edited="false" dataIndex="a0222" editor="text"
                                                      hidden="true"/>
                                <odin:gridEditColumn2 header="任职状态" edited="false" dataIndex="a0255" codeType="ZB14"
                                                      editor="select" width="160"/>
                                <odin:gridEditColumn hidden="true" header="操作" width="100" dataIndex="delete"
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

<odin:hidden property="a0281" title="输出设置"/>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="a0200s"/>

<script>
function changea0201a(value, params, record, rowIndex, colIndex, ds) {
    if (record.data.a0201b == '-1') {
        return '<a title="' + value + '(机构外)">' + value + '(机构外)</a>';
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
		$h.alert('系统提示','请勾选后保存!',null,220);
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
