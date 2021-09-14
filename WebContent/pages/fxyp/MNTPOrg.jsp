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
    <odin:buttonForToolBar text="保存"  icon="images/save.gif" isLast="true" id="save"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="班子情况" property="groupBox" >
    
<table>
  <tr>
    <td>
    	<table>
    		<tr>
    			<td colspan="12" style="padding-bottom: 20px"><div id="toolbardiv" ></div></td>
    		</tr>
            <tr>
                <td colspan="12" style="text-align: center;">
                	<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="2" checked>区县市</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="3">区县市平台</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="1">市直单位</label>
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="4">国企高校</label>
                </td>
            </tr>
            <tr>
                <odin:textEdit property="b0101" label="单位名称" required="true" width="530" colspan="12"/>
            </tr>
            <tr>
                <odin:textarea property="b0236" label="缺配信息 "   rows="3" colspan="7" />
                <odin:textarea property="bz" label="备注"   rows="3" colspan="5" />
            </tr>
            
            <tr class="show-qt">
                <odin:numberEdit property="zzhd" label="正职应配" width="30"  decimalPrecision="0" colspan="6"/>
                <odin:numberEdit property="fzhd" label="副职应配" width="30"  decimalPrecision="0" colspan="6"/>
            </tr>
            <tr class="show-qxs">
                <odin:numberEdit property="bzdw" label="党委应配" width="30" decimalPrecision="0"/>
                <odin:numberEdit property="bzrd" label="人大应配" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="bzzf" label="政府应配" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="bzzx" label="政协应配" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="fy" label="法院应配" width="30"  decimalPrecision="0" />
                <odin:numberEdit property="jcy" label="检察院应配" width="30"  decimalPrecision="0" />
            </tr>
            <%-- <tr>
                <odin:textarea property="zzs02" cols="80" rows="5" colspan="6" label="内设机构及分工"  />
            </tr>
            <tr>
                <odin:textarea property="zzs03" label="职数描述" cols="80" rows="5" colspan="6"/>
            </tr>
            <tr>
                <odin:numberEdit property="zzs04" label="正职" width="100" decimalPrecision="0"/>
                <odin:numberEdit property="zzs05" label="副职" width="100"  decimalPrecision="0" />
                <odin:numberEdit property="zzs06" label="总师" width="100"  decimalPrecision="0" />
            </tr>
            <tr>
                <odin:textarea property="zzs07" label="职数描述补充" cols="80" rows="5" colspan="6"/>
            </tr>
            <tr>
                <odin:numberEdit property="zzs08" label="正职" width="100"  decimalPrecision="0"/>
                <odin:numberEdit property="zzs09" label="副职" width="100"  decimalPrecision="0" />
                <odin:numberEdit property="zzs10" label="总师" width="100"  decimalPrecision="0" />
            </tr>
            <tr>
                <odin:numberEdit property="zzs20" label="合计正职" width="100"  decimalPrecision="0" readonly="true"/>
                <odin:numberEdit property="zzs21" label="合计副职" width="100"  decimalPrecision="0" readonly="true"/>
                <odin:numberEdit property="zzs22" label="合计总师" width="100"  decimalPrecision="0" readonly="true"/>
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