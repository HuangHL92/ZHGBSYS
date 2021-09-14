<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@include file="/comOpenWinInit.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<style>
   
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
    
    
</style>

<odin:toolBar property="toolBar">
    <odin:fill/>
    <odin:buttonForToolBar text="保存" id="save" isLast="true"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="其间简历信息" property="groupBox">
        <table>
			<tr style="height:30;">
				<odin:NewDateEditTag property="a1701" label="开始时间" isCheck="false" labelSpanId="startSpanId" maxlength="6"   width="170"></odin:NewDateEditTag>
				<td style="width:10px"></td>
				<td style="width:10px"></td>
				<odin:NewDateEditTag property="a1702" label="结束时间" isCheck="false" labelSpanId="endSpanId" maxlength="6" width="170"></odin:NewDateEditTag>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1703" label="简历描述" required="true" width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr>
				<tags:ComBoxWithTree property="a1705" codetype="JL02"  width="150"  label="重点岗位" nodeDblclick="a1705change"   />
			</tr>
			<tr>
				<odin:select2 property="a1704" codeType="JL02"  width="150"  label="工作（学习）单位类型"  readonly="true"/>
			</tr>
			<tr>
				<odin:select2 property="a1706" data="['党务类','党务类'],['综合管理类','综合管理类'],['制造业和工业经济类','制造业和工业经济类']
				,['大数据和信息技术类','大数据和信息技术类'],['城建城管类','城建城管类'],['教育卫生类','教育卫生类'],['服务商贸类','服务商贸类']
				,['农业农村类','农业农村类'],['文化发展和旅游类','文化发展和旅游类'],['公检法政法类','公检法政法类'],['企业经营管理类','企业经营管理类']
				,['金融财务类','金融财务类']" width="130"  label="分管工作类型" multiSelect="true" />
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1707" label="分管工作成效"  width="520" colspan="6"></odin:textEdit>
			</tr>
			<tr style="height:30;">
				<odin:textEdit property="a1708" label="备注" width="520" colspan="6"></odin:textEdit>
			</tr>
			</table>
    </odin:groupBox>
		
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>


<odin:hidden property="a1700"/>
<odin:hidden property="a1700parent"/>
<odin:hidden property="a0000"/>
<odin:hidden property="ssid"/>
<script>
    Ext.onReady(function () {
        //var data = parent.Ext.getCmp(subWinId).initialConfig.data
        if(parentParams.a1700){
            document.getElementById("a1700").value=parentParams.a1700;
            document.getElementById("a1700parent").value=parentParams.a1700parent;
            document.getElementById("a0000").value=parentParams.a0000;
        }
/*         var a1700=document.getElementById("a1700").value;
        var a1700parent=document.getElementById("a1700parent").value;
        var a0000=document.getElementById("a0000").value;
        alert(a1700);
        alert(a1700parent);
        alert(a0000); */
        

    })
   

    
    
   
    
    function openETCWin(){
    	var win = Ext.getCmp("addetc");	
    	if(win){
    		win.show();	
    		return;
    	}
    	win = new Ext.Window({
    		title : '指标类维护',
    		layout : 'fit',
    		width : 500,
    		height : 221,
    		closeAction : 'hide',
    		closable : true,
    		modal : true,
    		id : 'addetc',
    		collapsed:false,
    		collapsible:false,
    		bodyStyle : 'background-color:#FFFFFF',
    		plain : true,
    		titleCollapse:false,
    		contentEl:"etcInfo",
    		listeners:{}
    		           
    	});
    	win.show();
    }
    
    function saveETCInfo(){
    	radow.doEvent("addETCInfo");
    	Ext.getCmp("addetc").hide();
    }
    
    function a1705change(){
    	var a1705=document.getElementById('a1705').value;
    	var a1704=a1705.substring(0,2);
    	document.getElementById('a1704').value=a1704;
    	radow.doEvent('changeA1704name')
    }


</script>