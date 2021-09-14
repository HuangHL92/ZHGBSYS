<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel" %>
<%
    String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>

<%@include file="../../comOpenWinInit2.jsp" %>

<body>

<odin:toolBar property="toolBar8" applyTo="tol2">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="保存" id="save1" handler="save" icon="images/save.gif"/>
    <odin:fill></odin:fill>
    <odin:buttonForToolBar text="新增" id="add" handler="addsave" icon="images/save.gif" isLast="true"/>
</odin:toolBar>

<odin:hidden property="a0000"/>
<odin:hidden property="js0100"/>
<odin:hidden property="js2200"/>
<odin:hidden property="js2201"/>
<odin:hidden property="js2202"/>
<odin:hidden property="js2203"/>
<odin:hidden property="js2204"/>

<odin:hidden property="js2200s"/>
<odin:hidden property="js2201s"/>
<odin:hidden property="js2203s"/>
<odin:hidden property="subproperty"/>

<div id="border">
    <div id="tol2" align="left" style="width: 40px"></div>
    <odin:hidden property="a0500" title="主键id"/>
    <odin:hidden property="a0000" title="人员外键"/>
    <table cellspacing="2" align="center" style="width: 100%">
        <tr>
            <%-- <tags:PublicTextIconEdit property="a0501b" codetype="ZB09" width="160" label="拟任职务层次" required="true"
                                     readonly="true"/>
            <td>&nbsp;</td> --%>
            <tags:PublicTextIconEdit3 property="a0195id" width="130" onfocus="changea0195()" codetype="orgTreeJsonData" readonly="true"
                                  label="拟任单位"/>
            <tags:PublicTextIconEdit3 codetype="GWGLLB" width="130" label="拟任职务" property="a5315" readonly="true"/>
			<td>
				<odin:checkbox property="js2207" label="主职务" ></odin:checkbox>
			</td>
        </tr>
        
  <%--       <odin:select property="a39067" label="是否领导职务" codeType="XZ09" required="true"/>
        <td>&nbsp;</td> --%>
        
        <tr>

        </tr>
    </table>
    <table style="width: 100%">

    <tr>
        <td>
            <table width="440">
                <tr>
                    <td>
                        <odin:editgrid property="NiRenGrid" sm="row" isFirstLoadData="false" url="/"
                                       height="200" title="" pageSize="50" enableDragDrop="true" >
                            <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                                <odin:gridDataCol name="checkid"/>
                                <odin:gridDataCol name="a0000"/>
                                <odin:gridDataCol name="js0100"/>
                                <odin:gridDataCol name="js2200"/>
                                <odin:gridDataCol name="js2201"/>
                                <odin:gridDataCol name="js2202"/>
                                <odin:gridDataCol name="js2203"/>
                                <odin:gridDataCol name="js2207"/>
                                <odin:gridDataCol name="js2201b"/>
                                <odin:gridDataCol name="delete" isLast="true"/>
                            </odin:gridJsonDataModel>
                            <odin:gridColumnModel>
                                <odin:gridRowNumColumn/>
                                <odin:gridEditColumn2 header="选中" width="100" editor="checkbox" dataIndex="checkid"
                                                      edited="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2200" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="拟任单位(简称)" edited="false" dataIndex="js2201b" 
                                                      editor="text" width="300"/>
                                <odin:gridEditColumn2 header="拟任单位ID" edited="false" dataIndex="js2202"
                                					  editor="text" width="300" hidden="true"/>
                                <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="js2203" editor="text"
                                                      width="300"/>
                               <%--  <odin:gridEditColumn2 header="主职务" edited="true" dataIndex="js2207" editor="text"
                                                      codeType="XZ09" width="150"/> --%>
                                <odin:gridEditColumn2 header="主职务" edited="true" dataIndex="js2207" editor="select"
                                                      codeType="XZ09" width="150" onSelect="selectXZ"/> 
                                <odin:gridEditColumn  header="操作" width="150" dataIndex="delete"
                                                     editor="text"
                                                     edited="false" renderer="deleterow" isLast="true"/>
                            </odin:gridColumnModel>
                        </odin:editgrid>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
  	
</table>
</div>
<odin:hidden property="a0531" value="0"/>
<odin:hidden property="a0525"/>
<odin:hidden property="a0201b"/>
<odin:hidden property="a0195"/>
</body>
<script type="text/javascript">
function selectXZ(obj){
	var val = obj.data.key;
	if(!val){
		val = '1';
	}
	var grid = odin.ext.getCmp('NiRenGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var js2200 = selections[0].data.js2200;
	var v = js2200 + "," + val;
	//alert(v);
	radow.doEvent("selectXZ",v);
}


function zzwrenderer(value, params, record, rowIndex, colIndex, ds){
	if(value == '1'){
		return '是';
	} else {
		return '否';
	}
}
	function deleterow(value, params, record, rowIndex, colIndex, ds){
		var js2200=record.data.js2200;
		return "<a href=\"javascript:deleteRow2(&quot;" + js2200 + "&quot;)\">删除</a>";
	}
	function deleteRow2(js2200){
		radow.doEvent("deleterow",js2200);
	}
	
	
	function save(){
		var grid=Ext.getCmp('NiRenGrid');
		var store=grid.getStore();
		var num=0;
		var js2200s="";
		for(var i=0;i<store.getCount();i++){
			var record=store.getAt(i);
			var checkid=record.data.checkid;
			var js2200=record.data.js2200;
			if(checkid == true){
				num=num+1;
				js2200s=js2200s+js2200+',';
			}
		}
		if(num==0){
			$h.alert('系统提示','请勾选后保存!',null,220);
		}
		document.getElementById('js2200s').value=js2200s;
		radow.doEvent('UpdateTitleBtn.onclick');
	}
	
	function saveafter(js2201s,js2203s,js2202s,zrqm){
		
		//var js2203s=document.getElementById('js2203s').value;//职务
		//var js2201s=document.getElementById('js2201s').value;//单位
		//var subProperty=document.getElementById('subproperty').value;
		//alert(subProperty)
		//if('' == subProperty){
			realParent.document.getElementById("js0111").value = js2203s;
	        realParent.document.getElementById("js0116value").value =js2201s;
	        realParent.document.getElementById("js0116").value = js2202s;
	        realParent.document.getElementById("js0111a").value = zrqm;
	        
		//}else{
		//	realParent.document.getElementById(subProperty).value =js2203s;
		//}
        window.close();
	}
		
	function changea0195(){
		document.getElementById('a0201b').value=document.getElementById('a0195id').value;
		document.getElementById('a0195').value=document.getElementById('a5315_combo').value;
	}
	
	function addsave(){
		//拟任职务ID
		var nrzwid = document.getElementById("a5315").value;
		//拟任职务
        var a5315 = document.getElementById("a5315_combo").value.replace(/\s+/g, "");
        //拟任单位ID
        var a0195 = document.getElementById("a0195id").value;
        //拟任单位
        var a0195value=document.getElementById("a0195id_combo").value;
        if (a5315.length == 0) {
            $h.alert('系统提示', '拟任职务不能为空！', null, '220');
            return;
        }
        if (a0195 == "") {
            $h.alert('系统提示', '拟任单位不能为空！', null, '220');
            return;
        }
        document.getElementById('js2201').value=a0195value;
        document.getElementById('js2202').value=a0195;
        document.getElementById('js2203').value=a5315;
        document.getElementById('js2204').value=nrzwid;
        radow.doEvent('save');
	}
	
	
    function saveTrain() {

        //拟任职务
        var a5315 = document.getElementById("a5315_combo").value.replace(/\s+/g, "");
        //拟任单位
        var a0195 = document.getElementById("a0195id").value;
        if (a5315.length == 0) {
            $h.alert('系统提示', '拟任职务不能为空！', null, '220');
            return;
        }
        if (a0195 == "") {
            $h.alert('系统提示', '拟任单位不能为空！', null, '220');
            return;
        }
        //alert(a5315)//拟任职务
       // alert(a0195)//拟任机构ID
       // alert(document.getElementById("a0195_combo").value)//拟任机构文本
        realParent.document.getElementById("js0111").value = document.getElementById("a0195id_combo").value+a5315;
        realParent.document.getElementById("js0116value").value = document.getElementById("a0195id_combo").value;
        realParent.document.getElementById("js0116").value = a0195;
        window.close();
    }

    // get parent value and set this page
    function setValue() {
        var stringJSON = document.getElementById("subWinIdBussessId").value;
        if (stringJSON != "") {
            var obj = eval("(" + stringJSON + ")");
            document.getElementById("a0501b_combo").value = obj.a0501b_val;
            document.getElementById("a5315").value = obj.a5315;
            document.getElementById("a39067_combo").value = obj.a39067_val;
            document.getElementById("a0195_combo").value = obj.a0195_val;
         /*    document.getElementById("a0501b").value = obj.a0501b; */
            /* document.getElementById("a39067").value = obj.a39067; */
            document.getElementById("a0195").value = obj.a0195;
        }
    }
    Ext.onReady(function(){
    	var grid=Ext.getCmp('NiRenGrid');
    	grid.dragDropCallback = function(){
    		radow.doEvent('saveSort');
    	}
    	
    	$('#js2207').attr('checked',true)  //将输入框的状态设置为checked 
		
    });
    
</script>
<style>
    <%=FontConfigPageModel.getFontConfig()%>
    .vfontConfig {
        color: red;
    }

    #border {
        position: relative;
        left: 0px;
        top: 0px;
        width: 0px;
        border: 1px solid #99bbe8;
    }

    #toolBar8 {
        width: 454px !important;
    }
</style>
