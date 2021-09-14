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
    <odin:buttonForToolBar text="����" id="save"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="���ɹ�ְ��Ϣ" property="groupBox">
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
                <odin:textarea property="grzw" cols="97" rows="3" colspan="4" label="���ε�λ��ְ��"/>
                
            </tr>
            <tr>
                <odin:select2 property="xpdw" label="Ԯ�ɵ�" codeType="AIDREGION"/>
                <odin:select2 property="jsgz" label="������ְ" codeType="XZ09"/>
				<odin:select2 property="gzlx" label="����" data="['01', '��һ����'],['02', '�ڶ�����'],['03', '��������'],['04', '��������'],['05', '��������'],['06', '��������'],['07', '��������'],['08', '�ڰ�����'],['09', '�ھ�����'],['10', '��ʮ����']" ></odin:select2>
            </tr>
            <tr>
                <odin:textEdit property="gzrw" label="��ְ����"/>
                <odin:NewDateEditTag property="kssj" isCheck="true" label="��ʼʱ��"/>
                <odin:NewDateEditTag property="jssj" isCheck="true" label="����ʱ��"/>
            </tr>
            <tr>
                
            </tr>
            <tr style="display: none;">
                <odin:textEdit property="khqk" label="�������"/>
                <odin:textEdit property="dybz" label="��������"/>
            </tr>
            <tr style="display: none;">
                <odin:dateEdit property="qzsj" label="����ʱ��"/>
                <odin:textEdit property="yxnr" label="��������"/>
            </tr>
            
        </table>
    </odin:groupBox>
		<odin:toolBar property="btnToolBar" >
			<odin:fill />
		<odin:buttonForToolBar text="���ӱ�ע" icon="images/add.gif" id="addBtn" handler="addetc" isLast="true"/>
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
				<odin:gridColumn dataIndex="gzbz01"  header="����" align="left"  />
				<odin:gridColumn dataIndex="gzbz02"  header="��ע" align="left"   />
				<odin:gridColumn dataIndex="gzbz00"  header="ɾ��" align="center" isLast="true" renderer="deleteRowRenderer" />
			</odin:gridColumnModel>
		</odin:editgrid>
    
    
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>

<div id="etcInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:NewDateEditTag property="gzbz01"   label="����" />
		  </tr>
		  <tr>
			<odin:textarea property="gzbz02" cols="70" rows="6" label="��ע"></odin:textarea>
		  </tr>
		</table>
		<odin:hidden property="gzbz00"/>
		<div style="margin-left: 275px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveETCInfo" handler="saveETCInfo" />
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
        $h.openWin('selectPerson', 'pages.gzgl.CjqkAddSelect', 'ѡ����Ա ', 1200, 610, null, contextPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close'
        })
    }

    function callback(param) {
        document.getElementById("personId").value = param
        // �ر�ѡ����Ա����
        $h.getTopParent().Ext.getCmp('selectPerson').close()
        // ������Ա��Ϣ
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
    
    
    function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
    	var gzbz00 = record.data.gzbz00;
    	return "<a href=\"javascript:deleteRow2(&quot;"+gzbz00+"&quot;)\">ɾ��</a>";
    	
    }
    function deleteRow2(gzbz00){ 
    	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRow',gzbz00);
    		}else{

    		}		
    	});	
    }
</script>