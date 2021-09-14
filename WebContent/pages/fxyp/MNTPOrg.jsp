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
    <odin:buttonForToolBar text="����"  icon="images/save.gif" isLast="true" id="save"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="�������" property="groupBox" >
    
<table>
  <tr>
    <td>
    	<table>
    		<tr>
    			<td colspan="12" style="padding-bottom: 20px"><div id="toolbardiv" ></div></td>
    		</tr>
            <tr>
                <td colspan="12" style="text-align: center;">
                	<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="2" checked>������</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="3">������ƽ̨</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="1">��ֱ��λ</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="4">�����У</label>
                </td>
            </tr>
            <tr>
                <odin:textEdit property="b0101" label="��λ����" required="true" width="530" colspan="12"/>
            </tr>
            <tr>
                <odin:textarea property="b0236" label="ȱ����Ϣ "   rows="3" colspan="7" />
                <odin:textarea property="bz" label="��ע"   rows="3" colspan="5" />
            </tr>
            
            <tr class="show-qt">
                <odin:numberEdit property="zzhd" label="��ְӦ��" width="30"  decimalPrecision="0" colspan="6"/>
                <odin:numberEdit property="fzhd" label="��ְӦ��" width="30"  decimalPrecision="0" colspan="6"/>
            </tr>
            <tr class="show-qxs">
                <odin:numberEdit property="bzdw" label="��ίӦ��" width="30" decimalPrecision="0"/>
                <odin:numberEdit property="bzrd" label="�˴�Ӧ��" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="bzzf" label="����Ӧ��" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="bzzx" label="��ЭӦ��" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="fy" label="��ԺӦ��" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="jcy" label="���ԺӦ��" width="30"  decimalPrecision="0" />
            </tr>
            <%-- <tr>
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
             --%>
        </table>
    </td>
  </tr>
</table>

        
    </odin:groupBox>
    
    
</div>

<odin:hidden property="b01id"/>
<odin:hidden property="mntp00"/>
<script>
    Ext.onReady(function () {
        //var data = parent.Ext.getCmp(subWinId).initialConfig.data
        if(parentParams.mntp00){
            document.getElementById("mntp00").value=parentParams.mntp00;
        }
        
        changeZSB();

    })
   
    
    
    
   /*  function saveETCInfo(){
    	radow.doEvent("addETCInfo");
    	Ext.getCmp("addetc").hide();
    } */
    
    
    
    
    function changeZSB(obj){
    	var mntp05 = $("input[name='mntp05_r']:checked").val();
		$("#mntp05").val(mntp05);
		
		if(mntp05=='2'){
			$('.show-qxs').css("visibility","visible");
			$('.show-qt').hide();
		}else{
			$('.show-qxs').css("visibility","hidden");
			$('.show-qt').show();
		}
    }
    
    
</script>

<odin:hidden property="mntp05"/>