<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>

<style>
   
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
    
    
</style>

<odin:toolBar property="toolBar">
    <odin:fill/>
    <odin:buttonForToolBar text="保存" id="save"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="外派挂职信息" property="groupBox">
        <table>
            <tr>
                <odin:textEdit property="xm" label="姓名" readonly="true" onclick="openSelectPersonWin()"/>
                <odin:select property="xb" label="性别" codeType="GB2261" readonly="true"/>
                <odin:NewDateEditTag property="csny" label="出生年月"  readonly="true"/>
            </tr>
            <tr>
                <odin:textEdit property="szcs" label="所在单位" readonly="true"/>
                <odin:select property="zw" label="职务" codeType="ZB09" readonly="true"/>
                <odin:select property="zj" label="职级" codeType="ZB148" readonly="true"/>
            </tr>
            <tr>
                <odin:textarea property="grzw" cols="97" rows="3" colspan="4" label="挂任单位及职务"/>
                
            </tr>
            <tr>
                <odin:select2 property="xpdw" label="援派地" codeType="AIDREGION"/>
                <odin:select2 property="jsgz" label="结束挂职" codeType="XZ09"/>
				<odin:select2 property="gzlx" label="批次" data="['01', '第一批次'],['02', '第二批次'],['03', '第三批次'],['04', '第四批次'],['05', '第五批次'],['06', '第六批次'],['07', '第七批次'],['08', '第八批次'],['09', '第九批次'],['10', '第十批次']" ></odin:select2>
            </tr>
            <tr>
                <odin:textEdit property="gzrw" label="挂职任务"/>
                <odin:NewDateEditTag property="kssj" isCheck="true" label="开始时间"/>
                <odin:NewDateEditTag property="jssj" isCheck="true" label="结束时间"/>
            </tr>
            <tr>
                
            </tr>
            <tr style="display: none;">
                <odin:textEdit property="khqk" label="考核情况"/>
                <odin:textEdit property="dybz" label="待遇保障"/>
            </tr>
            <tr style="display: none;">
                <odin:dateEdit property="qzsj" label="提醒时间"/>
                <odin:textEdit property="yxnr" label="提醒内容"/>
            </tr>
            
        </table>
    </odin:groupBox>
		<odin:toolBar property="btnToolBar" >
			<odin:fill />
		<odin:buttonForToolBar text="增加备注" icon="images/add.gif" id="addBtn" handler="addetc" isLast="true"/>
		</odin:toolBar>
	    <odin:editgrid property="memberGrid" hasRightMenu="true" topBarId="btnToolBar" autoFill="true" width="590" height="380" pageSize="200" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="gzbz00" />
				<odin:gridDataCol name="gzbz01"/>
				<odin:gridDataCol name="gzbz02"/>
				<odin:gridDataCol name="gzbz03" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="gzbz01"  header="日期" align="left"  />
				<odin:gridColumn dataIndex="gzbz02"  header="备注" align="left"   />
				<odin:gridColumn dataIndex="gzbz00"  header="删除" align="center" isLast="true" renderer="deleteRowRenderer" />
			</odin:gridColumnModel>
		</odin:editgrid>
    
    
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>

<div id="etcInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:NewDateEditTag property="gzbz01"   label="日期" />
		  </tr>
		  <tr>
			<odin:textarea property="gzbz02" cols="70" rows="6" label="备注"></odin:textarea>
		  </tr>
		</table>
		<odin:hidden property="gzbz00"/>
		<div style="margin-left: 275px;margin-top: 15px;">
			<odin:button text="确定" property="saveETCInfo" handler="saveETCInfo" />
		</div>
	</div>
</div>

<odin:hidden property="id"/>
<odin:hidden property="personId"/>
<odin:hidden property="ssid"/>
<script>
    Ext.onReady(function () {
        //var data = parent.Ext.getCmp(subWinId).initialConfig.data
        if(parentParams.jgId){
            document.getElementById("id").value=parentParams.jgId;
        }
        
        
        
        Ext.getCmp('memberGrid').on('rowdblclick',function(gridobj,index,e){
    		var rc = gridobj.getStore().getAt(index);
    		$('#gzbz01_1').val(rc.data.gzbz01);
    		$('#gzbz01').val(rc.data.gzbz01);
    		
        	$('#gzbz02').val(rc.data.gzbz02);
        	$('#gzbz00').val(rc.data.gzbz00);
    		openETCWin();
    		$('#gzbz01_1').focus();
    	});

    })
    function openSelectPersonWin() {
        $h.openWin('selectPerson', 'pages.gzgl.CjqkAddSelect', '选择人员 ', 1200, 610, null, contextPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close'
        })
    }

    function callback(param) {
        document.getElementById("personId").value = param
        // 关闭选择人员窗口
        $h.getTopParent().Ext.getCmp('selectPerson').close()
        // 更新人员信息
        radow.doEvent('freshInfo')
    }
    
    
    function addetc(){
    	$('#gzbz01_1').val('');
    	$('#gzbz01').val('');
    	$('#gzbz02').val('');
    	$('#gzbz00').val('');
    	openETCWin();
    	$('#gzbz01_1').focus();
    }
    
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
    
    
    function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
    	var gzbz00 = record.data.gzbz00;
    	return "<a href=\"javascript:deleteRow2(&quot;"+gzbz00+"&quot;)\">删除</a>";
    	
    }
    function deleteRow2(gzbz00){ 
    	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRow',gzbz00);
    		}else{

    		}		
    	});	
    }
</script>