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
    <odin:buttonForToolBar text="保存" id="save1" handler="saveTrain" icon="images/save.gif"
                           isLast="true"/>
</odin:toolBar>

<div id="border">
    <div id="tol2" align="left" style="width: 40px"></div>
    <odin:hidden property="a0500" title="主键id"/>
    <odin:hidden property="a0000" title="人员外键"/>
    <table cellspacing="2" align="center" style="width: 100%">
        <tr>
            <%-- <tags:PublicTextIconEdit property="a0501b" codetype="ZB09" width="160" label="拟任职务层次" required="true"
                                     readonly="true"/>
            <td>&nbsp;</td> --%>
            <tags:PublicTextIconEdit3 property="a0195id" onfocus="changea0195()" codetype="orgTreeJsonData" readonly="true"
                                  label="拟任单位"/>
            <tags:PublicTextIconEdit3 codetype="GWGLLB" label="拟任职务" property="a5315" readonly="true"/>
			
        </tr>
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
                                       height="200" title="" pageSize="50" >
                            <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                                <odin:gridDataCol name="checkid"/>
                                <odin:gridDataCol name="js0100"/>
                                <odin:gridDataCol name="js2200"/>
                                <odin:gridDataCol name="js2201"/>
                                <odin:gridDataCol name="js2202"/>
                                <odin:gridDataCol name="js2203"/>
                                <odin:gridDataCol name="delete" isLast="true"/>
                            </odin:gridJsonDataModel>
                            <odin:gridColumnModel>
                                <odin:gridRowNumColumn/>
                                <odin:gridEditColumn2 header="选中" width="100" editor="checkbox" dataIndex="checkid"
                                                      edited="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2200" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="拟任单位" edited="false" dataIndex="js2201"
                                                      editor="text" width="300"/>
                                <odin:gridEditColumn2 header="拟任单位ID" edited="false" dataIndex="js2202"
                                					  editor="text" width="300" hidden="true"/>
                                <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="js2203" editor="text"
                                                      width="300"/>
                                <odin:gridEditColumn  header="操作" width="300" dataIndex="delete"
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
<odin:hidden property="a0201b"/>
<odin:hidden property="a0195"/>
</body>
<script type="text/javascript">

	function changea0195(){
		document.getElementById('a0201b').value=document.getElementById('a0195id').value;
		document.getElementById('a0195').value=document.getElementById('a5315_combo').value;
	}
	
    function saveTrain() {

    	/* //拟任职务层次
        var a0501b = document.getElementById("a0501b").value; */
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
        realParent.document.getElementById("a0195").value = a0195;
		
        realParent.document.getElementById("a5315").value = a5315;

        realParent.document.getElementById("a0195_val").value = document.getElementById("a0195id_combo").value;

        realParent.document.getElementById("a0221").value = document.getElementById("a5315_combo").value;

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
        width: 484px !important;
    }
</style>
