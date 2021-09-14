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

<odin:toolBar property="toolBar" applyTo="toolbardiv">
    <odin:fill/>
    <odin:buttonForToolBar text="����" id="AddBtn" icon="images/add.gif"></odin:buttonForToolBar>
    <odin:buttonForToolBar text="����"  icon="images/save.gif" isLast="true" id="save"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="��֯ʷ" property="groupBox" >
    
<table>
  <tr>
    <td>
    	<table>
    		<tr>
    			<td colspan="6" style="padding-bottom: 20px"><div id="toolbardiv" ></div></td>
    		</tr>
            <tr>
                <odin:textEdit property="zzs01" label="���" width="420"  colspan="6"/>
            </tr>
            <tr>
                <odin:textarea property="zzs02" cols="80" rows="5" colspan="6" label="����������ֹ�"  />
            </tr>
            <tr>
                <odin:textarea property="zzs03" label="ְ������" cols="80" rows="5" colspan="6"/>
            </tr>
            <tr>
                <odin:numberEdit property="zzs04" label="��ְ" width="100" decimalPrecision="0"/>
                <odin:numberEdit property="zzs05" label="��ְ" width="100"  decimalPrecision="0" />
                <odin:numberEdit property="zzs06" label="��ʦ" width="100"  decimalPrecision="0" />
            </tr>
            <tr>
                <odin:textarea property="zzs07" label="ְ����������" cols="80" rows="5" colspan="6"/>
            </tr>
            <tr>
                <odin:numberEdit property="zzs08" label="��ְ" width="100"  decimalPrecision="0"/>
                <odin:numberEdit property="zzs09" label="��ְ" width="100"  decimalPrecision="0" />
                <odin:numberEdit property="zzs10" label="��ʦ" width="100"  decimalPrecision="0" />
            </tr>
            <tr>
                <odin:numberEdit property="zzs20" label="�ϼ���ְ" width="100"  decimalPrecision="0" readonly="true"/>
                <odin:numberEdit property="zzs21" label="�ϼƸ�ְ" width="100"  decimalPrecision="0" readonly="true"/>
                <odin:numberEdit property="zzs22" label="�ϼ���ʦ" width="100"  decimalPrecision="0" readonly="true"/>
            </tr>
            
        </table>
    </td>
    <td style="width: 60%">
    	<odin:toolBar property="btnToolBar" >
			<odin:fill />
		<odin:buttonForToolBar text="�����쵼ְ��" icon="images/add.gif" id="addBtn" handler="addetc" isLast="true"/>
		</odin:toolBar>
	    <odin:editgrid property="memberGrid" hasRightMenu="true" topBarId="btnToolBar" autoFill="true"  height="420" pageSize="200" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="ld00" />
				<odin:gridDataCol name="ld01"/>
				<odin:gridDataCol name="ld02"/>
				<odin:gridDataCol name="ld03"/>
				<odin:gridDataCol name="ld04" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="ld01" width="80" header="����" align="center"  />
				<odin:gridColumn dataIndex="ld02" width="180" header="ְ��" align="center"   />
				<odin:gridColumn dataIndex="ld03" width="80" header="��ְʱ����" align="center"   />
				<odin:gridColumn dataIndex="ld04" width="80" header="ֹ" align="center"  />
				<odin:gridColumn dataIndex="ld00" width="80" header="����" align="center" isLast="true" renderer="deleteRowRenderer" />
			</odin:gridColumnModel>
		</odin:editgrid>
    </td>
  </tr>
</table>

        
    </odin:groupBox>
	    <odin:editgrid property="memberGridMain" hasRightMenu="true" load="selectRow" autoFill="true" width="590" height="100" pageSize="10" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="zzs00" />
				<odin:gridDataCol name="zzs01" />
				<odin:gridDataCol name="zzs03"/>
				<odin:gridDataCol name="zzs04"/>
				<odin:gridDataCol name="zzs05"/>
				<odin:gridDataCol name="zzs06"/>
				<odin:gridDataCol name="zzs07"/>
				<odin:gridDataCol name="zzs08"/>
				<odin:gridDataCol name="zzs09"/>
				<odin:gridDataCol name="zzs10" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="zzs01"  header="���" align="center"  />
				<odin:gridColumn dataIndex="zzs03"  header="ְ������" align="center"   />
				<odin:gridColumn dataIndex="zzs04"  header="��ְ" align="center"   />
				<odin:gridColumn dataIndex="zzs05"  header="��ְ" align="center"  />
				<odin:gridColumn dataIndex="zzs06"  header="��ʦ" align="center" />
				<odin:gridColumn dataIndex="zzs07"  header="ְ����������" align="center"   />
				<odin:gridColumn dataIndex="zzs08"  header="��ְ" align="center"   />
				<odin:gridColumn dataIndex="zzs09"  header="��ְ" align="center"  />
				<odin:gridColumn dataIndex="zzs10"  header="��ʦ" align="center" />
				<odin:gridColumn dataIndex="zzs00"  header="����" align="center" isLast="true" renderer="deleteRowRendererMain"/>
			</odin:gridColumnModel>
		</odin:editgrid>
    
    
</div>
<%-- <odin:panel property="panel" contentEl="content" topBarId="toolBar"/> --%>

<div id="etcInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
		  	<odin:textEdit property="ld01" label="����"  colspan="4"/>
		  </tr>
		  <tr>
		  	<odin:textarea property="ld02" label="ְ��" cols="63" rows="6" colspan="4"/>
		  	
		  </tr>
		  <tr>
		  	<odin:NewDateEditTag property="ld03"   label="��ְ������" />
			<odin:NewDateEditTag property="ld04"   label="ֹ" />
		  </tr>
		</table>
		<odin:hidden property="ld00"/>
		<div style="margin-left: 275px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveETCInfo" handler="saveETCInfo" />
		</div>
	</div>
</div>

<odin:hidden property="b0111"/>
<odin:hidden property="b01id"/>
<odin:hidden property="zzs00"/>
<script>
    Ext.onReady(function () {
        //var data = parent.Ext.getCmp(subWinId).initialConfig.data
        if(parentParams.jgId){
            document.getElementById("b0111").value=parentParams.jgId;
        }
        
        
        
        Ext.getCmp('memberGrid').on('rowdblclick',function(gridobj,index,e){
    		var rc = gridobj.getStore().getAt(index);
    		$('#ld03_1').val(rc.data.ld03);
    		$('#ld03').val(rc.data.ld03);
    		$('#ld04_1').val(rc.data.ld04);
    		$('#ld04').val(rc.data.ld04);
    		
        	$('#ld01').val(rc.data.ld01);
        	$('#ld02').val(rc.data.ld02);
    		openETCWin();
    		$('#ld03_1').focus();
    		$('#ld04_1').focus();
    	});

    })
   
    
    function addetc(){
    	$('#ld03_1').val('');
		$('#ld03').val('');
		$('#ld04_1').val('');
		$('#ld04').val('');
		
    	$('#ld01').val('');
    	$('#ld02').val('');
    	$('#ld00').val('');
    	openETCWin();
    	$('#ld03_1').focus();
		$('#ld04_1').focus();
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
    		height : 321,
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
    	var ld00 = record.data.ld00;
    	return "<a href=\"javascript:deleteRow2(&quot;"+ld00+"&quot;)\">ɾ��</a>";
    	
    }
    function deleteRow2(ld00){ 
    	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRow',ld00);
    		}else{

    		}		
    	});	
    }
    function deleteRowRendererMain(value, params, record,rowIndex,colIndex,ds){
    	var zzs00 = record.data.zzs00;
    	return "<a href=\"javascript:deleteRowMain(&quot;"+zzs00+"&quot;)\">ɾ��</a>";
    	
    }
    function deleteRowMain(zzs00){ 
    	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRowMain',zzs00);
    		}else{

    		}		
    	});	
    }
    
    
    
    
    
    function selectRow(a,store){
    	var peopleInfoGrid =Ext.getCmp('memberGridMain');
    	var len = peopleInfoGrid.getStore().data.length;
    	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
    		
    		peopleInfoGrid.getSelectionModel().selectRow(0,true);
    		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
    	}
    }
</script>