<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="self" %>
<script type="text/javascript">

 function openDiseaseInfoCommonQuery()
{
		var rt=window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.his.chargemgmt.charge.IllQuery',null,'help:no;status:no;dialogWidth:50;dialogHeight:35');
		if(rt)
		{
			document.all.akc008.value=rt.aka052;
			document.all.akc009.value=rt.aka053;
			document.all.S2_intelligentSearch_InputBox.value=rt.aka053;			
		}
}
function doSelect(data){
     document.all.akc008.value=data.aka052;
     document.all.akc009.value=data.aka053;
}
 function ReadCardNo()
 {
	document.all('text1').value = document.all.ocxObj.ICGetData();//����,0��������ʱ       //CardNo�ǿ��ţ�����Ϊ�쳣��CardNo�ǹ�����Ϣ
	document.all('text2').value = document.all.ocxObj.OutData;//����
	if(document.all('text1').value!="0"){
		alert(document.all('text2').value);
		return false;
	}
	radow.doEvent("doRead");
 	return;
 }

</script>
<odin:MDParam></odin:MDParam>
<odin:floatDiv property="btnToolBarDiv"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:opLogButtonForToolBar />
	<odin:buttonForToolBar text="����(<U>C</U>)" id="clear"
		cls="x-btn-text-icon" icon="images/sx.gif" />
	<odin:buttonForToolBar text="��ӡԤ����(<U>C</U>)" id="print"
		cls="x-btn-text-icon" icon="images/print.gif" />
	<odin:buttonForToolBar text="ҽ������" id="duka" cls="x-btn-text-icon"
		icon="images/duka.gif" handler="ReadCardNo"/>
	<odin:buttonForToolBar text="��ѯ" id="nbReadCard" />
	<odin:buttonForToolBar isLast="true" text="����(<U>S</U>)" id="save"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>

<table>
	<tr>
		<td height="26">&nbsp;</td>
	</tr>
</table>
<odin:hidden property="aac001"/>
<odin:hidden property="aac002"/>
<odin:hidden property="akc002"/>
<odin:hidden property="text2"/>
<odin:hidden property="text1"/>
<odin:hidden property="getResMsg"/>
<odin:groupBox title="���˻�����Ϣ">
<table>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</table>
	<table border="0" id="myform1" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<odin:tabLayOut />
		<tr>
		    <odin:hidden property="aac003" value="1"/>
		    <odin:textEdit property="aac004" label="���֤��" required="false" ></odin:textEdit>
		    <odin:textEdit property="aac005" label="����"  required="false" maxlength="5"></odin:textEdit>	    
			<odin:select property="aac006" label="�Ա�" codeType="AAC006" required="false" disabled="true"/>						
		</tr>	
		<tr>
		    <odin:numberEdit property="akc020" label="����" required="false" disabled="true" maxlength="3"/>
		    <odin:dateEdit property="aac008old" label="��������" required="false" disabled="true"/>
		    <odin:hidden property="aac008"/>
		    <odin:textEdit property="aae003" label="��ϵ�绰" required="false" />
		    
		</tr>
		<tr>
			<odin:select  property="aac022" label="��Ա���"  readonly="true" value="2" codeType="AAC022" />
			<odin:textEdit property="aae002" label="��ϵ��ַ" />
			<odin:textEdit property="cardNO" label="ũ������" />
		</tr>
		<tr>
			<odin:textEdit property="mes" label="ҽ����Ϣ" readonly="true" disabled="true" colspan="6" width="660"></odin:textEdit>						
	    </tr>
	</table>
	<table>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</table>
</odin:groupBox>
<odin:groupBox title="��Ժ�Ǽ���Ϣ">	
      <table border="0" id="myform1" align="center" width="100%"
		cellpadding="0" cellspacing="0">
        <tr>	
            <odin:numberEdit property="akc006" label="סԺ��" />
		    <odin:dateEdit property="aae101" label="סԺ����" />     		
			<odin:select property="akc012" label="����ҽ��" 
				codeType="AKB042"></odin:select>
	    </tr>
        <tr>
			<odin:select property="akc014" label="����" codeType="AKB032" valueNotFoundText="false"></odin:select>	
			<odin:hidden property="akc015"/>
			

                       <td style="font-size: 12px" align="right">����&nbsp;</td>
		               <td><table><tr><td>
			           <self:IntelligentSeach2 property="S2" showColHeads="��������:110,Ӣ������:80,������:60,��������:60"  emptyText="������/������"  minChars="1" onselect="doSelect" width="160" listWidth="380" pageSize="5" displayField="aka053" showColNames="aka053,aka054,aab008,aka055,aka052" dataColNames="aka051,aka052,aka053,aka054,aab008,aka055" queryClass="com.insigma.siis.local.pagemodel.his.hospitalmgmt.bookcharge.IllCommQueryPageModel"/>
			           </td><td>
			           <a style="font-size: 12px" href=javascript:openDiseaseInfoCommonQuery();>�߼�</a></td></tr></table>
		               </td>
		               <odin:textEdit  property="akc016" label="������λ" required="false"/>
		               <td width="400"></td>
	
			<odin:hidden property="akc008"/>
			<odin:hidden property="akc009"/>
		</tr>
		<tr>
		     <odin:textarea property="akc017" label="��Ҫ��������" value=" " colspan="6" cols="130" rows="6"></odin:textarea>		
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox title="Ԥ������Ϣ">
<table>
	<tr>
		<td height="16">&nbsp;</td>
	</tr>
</table>
     <table>
	     <tr>
	     	 <td width="400"></td>
		     <td align="right"><span style="font-size: 15pt;">Ԥ�ɿ�&nbsp;</span></td>
			 <odin:numberEdit property="akc183" value="0.00" style="height:30px;font-size:24px;line-height: 24px"></odin:numberEdit>
	     </tr>
	 </table>
	 <table>
	     <tr>
		     <td height="16">&nbsp;</td>
	     </tr>
	 </table>    
</odin:groupBox>	
 <object id="ocxObj" style="display:none" classid="CLSID:B5D14523-F44A-4123-A6FE-2C9348D90965" 
 codeBase="ICCInter01014.CAB">
</object>
