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
	document.all('text1').value = document.all.ocxObj.ICGetData();//读卡,0正常，此时       //CardNo是卡号；其他为异常，CardNo是故障信息
	document.all('text2').value = document.all.ocxObj.OutData;//卡号
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
	<odin:buttonForToolBar text="清屏(<U>C</U>)" id="clear"
		cls="x-btn-text-icon" icon="images/sx.gif" />
	<odin:buttonForToolBar text="打印预交单(<U>C</U>)" id="print"
		cls="x-btn-text-icon" icon="images/print.gif" />
	<odin:buttonForToolBar text="医保读卡" id="duka" cls="x-btn-text-icon"
		icon="images/duka.gif" handler="ReadCardNo"/>
	<odin:buttonForToolBar text="查询" id="nbReadCard" />
	<odin:buttonForToolBar isLast="true" text="保存(<U>S</U>)" id="save"
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
<odin:groupBox title="病人基本信息">
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
		    <odin:textEdit property="aac004" label="身份证号" required="false" ></odin:textEdit>
		    <odin:textEdit property="aac005" label="姓名"  required="false" maxlength="5"></odin:textEdit>	    
			<odin:select property="aac006" label="性别" codeType="AAC006" required="false" disabled="true"/>						
		</tr>	
		<tr>
		    <odin:numberEdit property="akc020" label="年龄" required="false" disabled="true" maxlength="3"/>
		    <odin:dateEdit property="aac008old" label="出生日期" required="false" disabled="true"/>
		    <odin:hidden property="aac008"/>
		    <odin:textEdit property="aae003" label="联系电话" required="false" />
		    
		</tr>
		<tr>
			<odin:select  property="aac022" label="人员类别"  readonly="true" value="2" codeType="AAC022" />
			<odin:textEdit property="aae002" label="联系地址" />
			<odin:textEdit property="cardNO" label="农保卡号" />
		</tr>
		<tr>
			<odin:textEdit property="mes" label="医保信息" readonly="true" disabled="true" colspan="6" width="660"></odin:textEdit>						
	    </tr>
	</table>
	<table>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
</table>
</odin:groupBox>
<odin:groupBox title="入院登记信息">	
      <table border="0" id="myform1" align="center" width="100%"
		cellpadding="0" cellspacing="0">
        <tr>	
            <odin:numberEdit property="akc006" label="住院号" />
		    <odin:dateEdit property="aae101" label="住院日期" />     		
			<odin:select property="akc012" label="主治医生" 
				codeType="AKB042"></odin:select>
	    </tr>
        <tr>
			<odin:select property="akc014" label="科室" codeType="AKB032" valueNotFoundText="false"></odin:select>	
			<odin:hidden property="akc015"/>
			

                       <td style="font-size: 12px" align="right">疾病&nbsp;</td>
		               <td><table><tr><td>
			           <self:IntelligentSeach2 property="S2" showColHeads="疾病名称:110,英文名称:80,助记码:60,疾病分类:60"  emptyText="疾病名/助记码"  minChars="1" onselect="doSelect" width="160" listWidth="380" pageSize="5" displayField="aka053" showColNames="aka053,aka054,aab008,aka055,aka052" dataColNames="aka051,aka052,aka053,aka054,aab008,aka055" queryClass="com.insigma.siis.local.pagemodel.his.hospitalmgmt.bookcharge.IllCommQueryPageModel"/>
			           </td><td>
			           <a style="font-size: 12px" href=javascript:openDiseaseInfoCommonQuery();>高级</a></td></tr></table>
		               </td>
		               <odin:textEdit  property="akc016" label="病区床位" required="false"/>
		               <td width="400"></td>
	
			<odin:hidden property="akc008"/>
			<odin:hidden property="akc009"/>
		</tr>
		<tr>
		     <odin:textarea property="akc017" label="主要病情描述" value=" " colspan="6" cols="130" rows="6"></odin:textarea>		
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox title="预交款信息">
<table>
	<tr>
		<td height="16">&nbsp;</td>
	</tr>
</table>
     <table>
	     <tr>
	     	 <td width="400"></td>
		     <td align="right"><span style="font-size: 15pt;">预缴款&nbsp;</span></td>
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
