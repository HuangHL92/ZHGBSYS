<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.business.helperUtil.DateUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.OrthersAddPagePageModel"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" charset="gbk" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>
<script type="text/javascript">
//ѡ����
//function onA2970change(record){
//	var key = document.getElementById("a2970").value;
	
//	if(key=='01'||key=='02'){
//		document.getElementById('xds1').style.visibility='visible';
//	}else{
//		document.getElementById('xds1').style.visibility='hidden';
//	}
//}
//������� �ؼ����²ſ�¼��ְ�� ����
function selectchange(record,index){return;
	var a0165 = document.getElementById("a0165").value;
	if("04"==a0165||"09"==a0165){
		selecteEnable('a0192d');
	}else{
		selecteDisable('a0192d');
	}
	
}
function setParentValue(obj){
	var id = obj.id;
	var value = obj.value;
	if(id.indexOf('_combo')!=-1){
		id = id.split('_combo')[0];
		//onblurʱ comboΪ��ʱ key��û��Ϊ��
		if(value==''){
			document.getElementById(id).value='';
		}
		value = document.getElementById(id).value
	}
	parent.document.getElementById(id).value=value;
}
function setParentValue2(obj){
	var id = obj.id;
	var value = obj.value;
	if(id.indexOf('_combo')!=-1){
		id = id.split('_combo')[0];
		if(value==''){
			document.getElementById(id).value='';
			onA0160Change();
		}
		value = document.getElementById(id).value
	}
	parent.document.getElementById(id).value=value;
}
function onA0160Change(){
	var a0160 = parent.document.getElementById('a0160').value;//��Ա���
	
	if("2"==a0160){//רҵ������ְ�ʸ�
		selecteWinEnable('a0122');
	}else{
		//selecteWinDisable('a0122');
	}
	
	return;//�������Ͳ�����
	if('1'==a0160||'2'==a0160||'3'==a0160||'5'==a0160||'B1'==a0160||'B2'==a0160||'B3'==a0160||'B4'==a0160){//�������� 1
		changeSelectData([{key:'1',value:'��������'}]);
	}else if('A0'==a0160||'A2'==a0160||'A4'==a0160||'A5'==a0160||'A6'==a0160){//����  4
		changeSelectData([{key:'4',value:'����'}]);
	}else if('7'==a0160||'8'==a0160||'A1'==a0160||'B5'==a0160){//��ҵ���ƻ����� 3  4
		changeSelectData([{key:'3',value:'��ҵ����'},{key:'4',value:'����'}]);
	}else if('6'==a0160){//�ι���ҵ���� 2
		changeSelectData([{key:'2',value:'�ι���ҵ����'}]);
	}else{
		changeSelectData([{key:'1',value:'��������'},{key:'2',value:'�ι���ҵ����'},{key:'3',value:'��ҵ����'},{key:'4',value:'����'}]);
	}
	//�������͸�ҳ����ֵ
	setParentValue(document.getElementById('a0121'));
}

function changeSelectData(item){
	var a0121f = Ext.getCmp("a0121_combo");
	var newStore = a0121f.getStore();
	newStore.removeAll();
	
	for(var i=0;i<item.length;i++){
		newStore.add(new Ext.data.Record(item[i]));
	}
	if(item.length==1){
		document.getElementById("a0121").value=item[0].key;
		a0121f.setValue(item[0].value); 
		a0121f.disable();
	}else if(item.length==4){
		document.getElementById("a0121").value='';
		a0121f.setValue(''); 
		a0121f.enable();
	}else{
		document.getElementById("a0121").value=item[0].key;
		a0121f.setValue(item[0].value); 
		a0121f.enable();
	}
	
}
<%--
function a2911onchange(){
	var a2911 = document.getElementById('a2911').value;//���뱾��λ��ʽ
	if(a2911.indexOf('11')==0||a2911.indexOf('12')==0||a2911.indexOf('13')==0){
	//��ʵʩ����Ա�����ص���11  �Ӳ��չ���Ա�������Ⱥ�Ż��ص���12 �Ӳ��չ���Ա���������ҵ��λ����13
		selecteEnable('a2950',"0");
		selecteDisable('a2951');
	}else if(a2911.indexOf('14')==0||a2911.indexOf('15')==0){//��������ҵ��λ����14 �ӹ�����ҵ����15
		selecteEnable('a2951',"0");
		selecteDisable('a2950');
	}else{
		selecteDisable('a2950');
		selecteDisable('a2951');
	}
	
}
--%>

function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
function setParenta0122Value(record){
	parent.document.getElementById('a0122').value=record.data.key;
}
//a01����
function setParentA0120Value(record){
	parent.document.getElementById('a0120').value=record.data.key;
}
</script>
<%--
<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="����" id="saveOthers" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:floatDiv property="floatToolDiv2"></odin:floatDiv>
--%>
<br/><br/>

<%-- <odin:groupBox property="s10s" >
<table cellspacing="2" width="440" align="left">
<td align="right"><span id="a0163SpanId" style="font-size: 12px;">��Ա״̬:</span></td>
		<td colSpan="1"><div id="a0163" class="x-form-item" style="font-size: 14px;color: rgb(0,0,128);padding-left: 5px;"></div></td>
		<odin:textEdit property="a0184" label="���֤��" required="true" onblur="setParentValue(this)" validator="$h.IDCard"></odin:textEdit>
		
		<odin:select2 property="a0165" label="�������" onchange="selectchange" onblur="setParentValue(this)" codeType="ZB130"></odin:select2>
		<odin:select2 property="a0160" label="��Ա���" codeType="ZB125" onchange="onA0160Change" onblur="setParentValue2(this)"></odin:select2>
		<odin:select2 property="a0121" label="��������" codeType="ZB135" onblur="setParentValue(this)"></odin:select2>
		
	<tr>
		<tags:PublicTextIconEdit property="a0122" label="&nbsp;&nbsp;&nbsp;רҵ�����๫��Ա��ְ�ʸ�" onchange="setParenta0122Value" codetype="ZB139" readonly="true"></tags:PublicTextIconEdit>
		<tags:PublicTextIconEdit  label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����" property="a0120" onchange="setParentA0120Value"  codetype="ZB134" readonly="true"></tags:PublicTextIconEdit> 
		<odin:select2 property="a0192d" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ��" codeType="ZB133" onblur="setParentValue(this)"></odin:select2>
		<odin:textEdit property="a0115a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ɳ���" validator="a0115aLength"></odin:textEdit>
	</tr>
</table>
</odin:groupBox> --%>
<script type="text/javascript">
 Ext.onReady(function(){
 	//var newnode = document.createElement('span');
 	//newnode.style.setAttribute("visibility","hidden");   
 	//newnode.appendChild(document.createTextNode("      a"));
 	/* var objj = document.getElementById('a0192d_combo').parentNode;
 	objj.appendChild(newnode); */
 	
 });
</script>
<%-----------------------------�������-------------------------------------------------------%>


<odin:groupBoxNew property="s10" title="�������" collapsed="true" collapsible="true" contentEl="manager">
	<div id="manager">
		<table cellspacing="2" width="600" style="text-align: left;">
			<%-- 	<tr>
		<odin:textEdit property="a2921a" label="���뱾��λǰ������λ����" validator="a2921aLength"></odin:textEdit>
		
		
	</tr> --%>
			<tr>
				<odin:NewDateEditTag property="a2907"
					label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���뱾��λ����"
					isCheck="true" maxlength="8"></odin:NewDateEditTag>
				<tags:PublicTextIconEdit property="a2911"
					label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���뱾��λ�䶯���"
					codetype="ZB77" readonly="true"></tags:PublicTextIconEdit>
				<%-- <tags:PublicTextIconEdit property="a2921b" label="���뱾��λǰ������λ���ڵ�" codetype="ZB74" readonly="true"></tags:PublicTextIconEdit> --%>
				<odin:NewDateEditTag property="a2949" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����Ա�Ǽ�ʱ��" isCheck="true"
					maxlength="8"></odin:NewDateEditTag>
			</tr>
			<tr>
				<odin:textEdit property="a2941"
					label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ԭ��λְ��"
					validator="a2941Length"></odin:textEdit>
				<odin:NewDateEditTag property="a2947" label="���빫��Ա����ʱ��"
					isCheck="true" maxlength="8"></odin:NewDateEditTag>
					
				<td align="right" style="padding-right: 8px;"><span
					id="a2947aSpanId" style="font-size: 12">���뱾��λʱ���㹤������ʱ��</span></td>
				<td colspan="1" align="left">
					<table cellpadding="0" cellspacing="0">
						<tr style="padding-left: 0px; margin-left: 0px;">
							<odin:numberEdit property="a2947a" width="70"
								decimalPrecision="0" maxlength="4" minValue="0">��</odin:numberEdit>
							<odin:numberEdit property="a2947b" width="70" maxlength="2"
								maxValue="12" decimalPrecision="0" minValue="0">��</odin:numberEdit>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<%--	<odin:select2 property="a2970" label="ѡ����" codeType="ZB137" onchange="onA2970change"></odin:select2>			
		
		<odin:select2 property="a2950" label="�Ƿ񹫿���ѡ" codeType="XZ09" ></odin:select2>		
	--%>
				<%-- <odin:select2 property="a2921c" label="���뱾��λǰ������λ����" codeType="ZB140"></odin:select2> --%>
				<odin:textEdit property="a2944" label="��ԭ��λְ����"
					validator="a2944Length"></odin:textEdit>
				<%-- <odin:select2 property="a2921d" label="���뱾��λǰ������λ���" codeType="ZB141"></odin:select2> --%>
				<odin:textEdit property="a2921a" label="���뱾��λǰ������λ����"
					validator="a2921aLength"></odin:textEdit>
			</tr>
			<%-- <tr>

				<odin:NewDateEditTag property="a2949" label="����Ա�Ǽ�ʱ��" isCheck="true"
					maxlength="8"></odin:NewDateEditTag>

				<td align="right" style="padding-right: 8px;"><span
					id="a2947aSpanId" style="font-size: 12">���뱾��λʱ���㹤������ʱ��</span></td>
				<td colspan="1" align="left">
					<table cellpadding="0" cellspacing="0">
						<tr style="padding-left: 0px; margin-left: 0px;">
							<odin:numberEdit property="a2947a" width="70"
								decimalPrecision="0" maxlength="4" minValue="0">��</odin:numberEdit>
							<odin:numberEdit property="a2947b" width="70" maxlength="2"
								maxValue="12" decimalPrecision="0" minValue="0">��</odin:numberEdit>
						</tr>
					</table>
				</td>

				
		<odin:select2 property="a2951" label="�Ƿ񹫿�ѡ��" codeType="XZ09" ></odin:select2>		
			</tr> --%>

			<%-- 
	<tr id="xds1" style="visibility:visible;">
		<odin:select2 property="a2970a" label="ѡ������Դ" codeType="ZB138"></odin:select2>
		<odin:textEdit property="a2970b" label="ѡ������ʼ������λ" ></odin:textEdit>
		<odin:numberEdit property="a2970c" label="ѡ�����ڻ���������ҵ����ʱ��" maxlength="4">��</odin:numberEdit>
	</tr>
	--%>
		</table>
	</div>
</odin:groupBoxNew>




<script type="text/javascript">



</script>




<script type="text/javascript">
//�������ڲ��Ǹ����� �޷���ʾ�ڵ�ǰλ�á�
function a3101change(){ 
	var a3101 = document.getElementById('a3101').value;
	if(a3101!=null&&a3101!=''){
		odin.setSelectValue('a3001','31');
	}
	var codeType = "orgTreeJsonData";
	var codename = "code_name";
    var winId = "winId"+Math.round(Math.random()*10000);
    var label = "ѡ�������λ";
//    var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
	//alert(url);
	var url = "pages.sysorg.org.PublicWindow&property=a0201&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
//    odin.openWindow(winId,label,url,270,415,window,false,true);
    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
}
function a3140change(){
	var a3107 = document.getElementById('a3107').value;
	var a3109 = document.getElementById('a3109').value;
	if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
		//$h.selectShow('a3109');
		//$h.selectShow('a3107');
		selecteWinEnable('a3109');
		selecteWinEnable('a3107');
		return;
	}
	if(a3107==null||a3107==''){
		//selecteEnable('a3109');
		selecteWinEnable('a3109');
	}else{
		//$h.selectHide('a3109');
		//selecteWinDisable('a3109');
	}
}
function a3141change(){
	var a3109 = document.getElementById('a3109').value;
	var a3107 = document.getElementById('a3107').value;
	if(a3107!=null&&a3107!=''&&a3109!=null&&a3109!=''){
		//$h.selectShow('a3109');
		//$h.selectShow('a3107');
		selecteWinEnable('a3109');
		selecteWinEnable('a3107');
		return;
	}
	if(a3109==null||a3109==''){
		//$h.selectShow('a3107');
		selecteWinEnable('a3107');
	}else{
		//$h.selectHide('a3107');
		//selecteWinDisable('a3107');
	}
}
function onkeydownfn(id){
	if(id=='a3107'){
		a3140change();
	}else if(id=='a3109'){
		a3141change();
	}else if(id=='a2911'){
		//selecteDisable('a2950');
		//selecteDisable('a2951');
	}else if(id=='a0120'){
		var record = {data:{value:'',key:''}};
		setParentA0120Value(record);
	}else if(id=='a0122'){
		var record = {data:{value:'',key:''}};
		setParenta0122Value(record);
	}
}
</script>
<%-----------------------------����-------------------------------------------------------%>
<odin:groupBox property="s13" title="���˺��˳�����">
<table cellspacing="2" width="440" align="left">
	<tr>
		<odin:select2 property="a3101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������" onchange="a3101change" codeType="ZB132"></odin:select2>
		<odin:NewDateEditTag property="a3104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������׼����" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a3137" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������׼�ĺ�" validator="a3137Length"></odin:textEdit>		
	</tr>
	<tr>		
		<tags:PublicTextIconEdit property="a3110" label="����ǰ����" codetype="ZB134" readonly="true"></tags:PublicTextIconEdit>	
		<odin:textEdit property="a3118" label="�������ְ��" validator="a3118Length"></odin:textEdit>	
		<odin:textEdit property="a3117a" label="���˺����λ" validator="a3117aLength"></odin:textEdit>	
		<%-- <tags:PublicTextIconEdit property="a3001" label="�˳�����ʽ" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>	 --%>
	</tr>
	<tr>		
		<tags:PublicTextIconEdit property="a3107" label="����ǰְ����" codetype="ZB09" onchange="a3140change" readonly="true"></tags:PublicTextIconEdit> 
		<tags:PublicTextIconEdit property="a3109" label="����ǰְ��ȼ�" codetype="ZB136" onchange="a3141change" readonly="true" ></tags:PublicTextIconEdit> 	
		
		<odin:select2 property="a3108" label="����ǰְ��" codeType="ZB133"></odin:select2>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a3001" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˳�����ʽ" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>
		<odin:textEdit property="a3007a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������λ" validator="a3007aLength"></odin:textEdit>
		<odin:NewDateEditTag property="a3004" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����" isCheck="true" maxlength="8"></odin:NewDateEditTag>
	</tr>
	<tr>
		<odin:textEdit property="a3034" label="��ע" validator="a3034Length"></odin:textEdit>		
	
	</tr>
</table>
</odin:groupBox>
<%-----------------------------�˳�����-------------------------------------------------------%>
<%-- <odin:groupBox property="s14" title="�˳�����">
<table cellspacing="2" width="440" align="left">
	<tr>
		<tags:PublicTextIconEdit property="a3001" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˳�����ʽ" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>
		<odin:textEdit property="a3007a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������λ" validator="a3007aLength"></odin:textEdit>
		<odin:NewDateEditTag property="a3004" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
		<odin:textEdit property="a3034" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ע" validator="a3034Length"></odin:textEdit>		
	</tr>
</table>
</odin:groupBox> --%>




<%-----------------------------������-------------------------------------------------------%>
<odin:groupBox property="s11" title="������">
<odin:hidden property="a5399" title="���id" />
<table cellspacing="2" width="440" align="left">
	<tr>
		<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5304Length"></odin:textEdit>
		<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5315Length"></odin:textEdit>
		<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������" validator="a5317Length"></odin:textEdit>
	</tr>
	<tr>
		<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
		<odin:NewDateEditTag property="a5321" label="��������ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a5323" label="���ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		<odin:textEdit property="a5327" label="�����" validator="a5327Length"></odin:textEdit>
	</tr>
	<tr>
		<odin:textEdit property="a5319" label="�ʱ���λ" validator="a5319Length"></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<%-----------------------------סַͨѶA37-------------------------------------------------------%>
<odin:groupBox property="s12" title="סַͨѶ">
<table cellspacing="2" width="440" align="left">
	<tr>
		<odin:textEdit property="a3701" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�칫��ַ" colspan="4" width="406" validator="a3701Length"></odin:textEdit>
		<odin:textEdit property="a3707a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�칫�绰" validator="a3707fLength"></odin:textEdit>
		<odin:textEdit property="a3707c" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ƶ��绰" validator="a3707cLength"></odin:textEdit>
				
	</tr>
	<tr>
		<odin:textEdit property="a3707b" label="סլ�绰" validator="a3707aLength"></odin:textEdit>
		<odin:textEdit property="a3707e" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����绰"  validator="a3707cLength"></odin:textEdit>
		<odin:textEdit property="a3708" label="��������" colspan="4" validator="$h.email" width="407" maxlength="60"></odin:textEdit>	
	</tr>
	<tr>
		
		<odin:textEdit property="a3711" label="��ͥ��ַ" colspan="4" width="406" validator="a3711Length"></odin:textEdit>
		<odin:textEdit property="a3714" label="סַ�ʱ�" validator="postcode" colspan="4" width="407" maxlength="6"></odin:textEdit>	
		
	</tr>
</table>
</odin:groupBox>
<%-----------------------------��ע-------------------------------------------------------%>
<odin:groupBox property="s14" title="��ע" >
	<odin:textarea property="a7101" colspan='2' rows="4" validator='a7101Length'></odin:textarea>
</odin:groupBox>
<odin:hidden property="a7100"/>

<%-----------------------------�Զ�����Ϣ��-------------------------------------------------------%>
<%
Map<String, List<Object[]>> os_list = OrthersAddPagePageModel.getInfoExt();
if(os_list!=null&&os_list.size()>0){
	for(String key : os_list.keySet()){
		List<Object[]> entitys = os_list.get(key);
		String[] kv = key.split("___");
		%>
		<odin:groupBox property="<%=kv[0] %>" title="<%=kv[1] %>">
			<table cellspacing="2" width="98%" align="center">
				<tr>
		<%
		
		Integer size = entitys.size(),index=0;;
		for(Object[] entity : entitys){
			String data_type = entity[5].toString();
			Object code_type = entity[3];
			if(index%3==0){
				%>
				</tr>
				<tr>
				<%
			}
			if("VARCHAR2".equals(data_type)){//�ı�
				if(code_type!=null&&!"".equals(code_type)){
					%>
					<tags:PublicTextIconEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" codetype="<%=code_type.toString() %>" readonly="true"></tags:PublicTextIconEdit>
					<%
				}else{
					%>
					<odin:textEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:textEdit>
					<%
				}
			}else if("NUMBER".equals(data_type)){//����
				%>
				<odin:numberEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:numberEdit>
				<%
			}else if("DATE".equals(data_type)){//����
				%>
				<odin:NewDateEditTag property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" isCheck="true" maxlength="8" ></odin:NewDateEditTag>
				<%
			}
			index++;
		}
		
		%>
				</tr>
			</table>
		</odin:groupBox>
		<%
		
	}
	
}

%>
<script type="text/javascript">
 Ext.onReady(function(){
 	
 	//applyFontConfig();
 	/* if(parent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.orthers);
	} */
 	//$h.fieldsDisabled(parent.fieldsDisabled);
 });
 function applyFontConfig(){
	var cls = 'fontConfig';
	if(parent.isveryfy){
		cls = 'vfontConfig';
	}
	//var $ = parent.$;
	var spFeild = parent.spFeild;
	for(var i_=0;i_<spFeild.length;i_++){
		if(document.getElementById(spFeild[i_]+'SpanId_s')){
			$('#'+spFeild[i_]+'SpanId_s').addClass(cls);
		}else if(document.getElementById(spFeild[i_]+'SpanId')){
			$('#'+spFeild[i_]+'SpanId').addClass(cls);
		}
    	
    }
}
 
 function a3001change(rs){
			var codeType = "orgTreeJsonData";
			var codename = "code_name";
		    var winId = "winId"+Math.round(Math.random()*10000);
		    var label = "ѡ�������λ";
//		    var url="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
			//alert(url);
			var url = "pages.sysorg.org.PublicWindow&property=abc&codetype="+codeType+"&closewin="+winId+"&codename="+codename+"&nsjg=0";
//		    odin.openWindow(winId,label,url,270,415,window,false,true);	
		    $h.openWin(winId,url,label,270,415,null,'<%=ctxPath%>',window);
	}
	function returnwina0201(rs){
		if(rs!=null){
			var rss = rs.split(",");
			parent.document.getElementById('orgid').value=rss[0];
//			var a = parent.document.getElementById('orgid').value;
//			alert(a);
			document.getElementById('a3117a').value=rss[1];
		}
	}
	function returnwinabc(rs){
		if(rs!=null){
			var rss = rs.split(",");
			parent.document.getElementById('orgid').value=rss[0];
//			var a = parent.document.getElementById('orgid').value;
//			alert(a);
			document.getElementById('a3007a').value=rss[1];
		}
	}
	//othersadddpage
	//ְ��䶯����
	function a7101Length(value) {
		if(value.length>1000) {
			return "���ȳ������ƣ�1000������";
		} else {
			return true;
		}
	}
</script>
