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
    <odin:buttonForToolBar text="����" id="save" isLast="true"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="����ίԱ��Ϣ" property="groupBox">
        <table>
            <tr>
                <odin:textEdit property="xm" label="����" readonly="true" onclick="openSelectPersonWin()"/>
                <odin:select property="xb" label="�Ա�" codeType="GB2261" readonly="true"/>
                <odin:NewDateEditTag property="csny" label="��������"  readonly="true"/>
            </tr>
            <tr>
                <odin:textEdit property="szcs" label="���ڵ�λ" readonly="true"/>
                <odin:select property="zw" label="ְ��" codeType="ZB09" readonly="true"/>
                <odin:select property="zj" label="ְ��" codeType="ZB148" readonly="true"/>
            </tr>
            <tr>
                <odin:textEdit  property="tbfzsj"  label="��θ�ְʱ��" />
                <odin:textEdit  property="tbzzsj" label="�����ְʱ��" />
            </tr>
            <tr>

        </table>
    </odin:groupBox>
		
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>


<odin:hidden property="id"/>
<odin:hidden property="a0000"/>
<odin:hidden property="ssid"/>
<odin:hidden property="b0111"/>
<odin:hidden property="b0101"/>
<odin:hidden property="checkedgroupid"/>
<script>
    Ext.onReady(function () {
        //var data = parent.Ext.getCmp(subWinId).initialConfig.data
        if(parent.Ext.getCmp(subWinId).initialConfig.checkedgroupid){
           $('#checkedgroupid').val(parent.Ext.getCmp(subWinId).initialConfig.checkedgroupid);
        }
        if(parentParams.jgId){
            document.getElementById("id").value=parentParams.jgId;
        }

    })
    function openSelectPersonWin() {
        $h.openWin('selectPerson', 'pages.gzgl.CjqkAddSelect', 'ѡ����Ա ', 1200, 610, null, contextPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close'
        })
    }

    function callback(param) {
        document.getElementById("a0000").value = param
        // �ر�ѡ����Ա����
        $h.getTopParent().Ext.getCmp('selectPerson').close()
        // ������Ա��Ϣ
        radow.doEvent('freshInfo')
    }
    
    
   
    
    function openETCWin(){
    	var win = Ext.getCmp("addetc");	
    	if(win){
    		win.show();	
    		return;
    	}
    	win = new Ext.Window({
    		title : 'ָ����ά��',
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
    
    


</script>