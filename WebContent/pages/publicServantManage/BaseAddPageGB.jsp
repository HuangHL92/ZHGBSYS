<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="java.net.URLEncoder"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<body>
<!-- ����A29��ֵ -->
<odin:hidden property="a2907"></odin:hidden>
<odin:hidden property="a2911"></odin:hidden>
<odin:hidden property="a2921a"></odin:hidden>
<odin:hidden property="a2941"></odin:hidden>
<odin:hidden property="a2944"></odin:hidden>
<odin:hidden property="a2947a_M"></odin:hidden>
<odin:hidden property="a2947a_Y"></odin:hidden>
<odin:hidden property="a2947"></odin:hidden>
<odin:hidden property="a2949"></odin:hidden>
<!-- ����A30��ֵ -->
<odin:hidden property="a3001"></odin:hidden>
<odin:hidden property="a3004"></odin:hidden>
<odin:hidden property="a3034"></odin:hidden>
<odin:hidden property="a3954a"></odin:hidden>
<odin:hidden property="a3954b"></odin:hidden>
<!-- ����A37��ֵ -->
<odin:hidden property="a3701"></odin:hidden>
<odin:hidden property="a3707a"></odin:hidden>
<odin:hidden property="a3707b"></odin:hidden>
<odin:hidden property="a3707c"></odin:hidden>
<odin:hidden property="a3711"></odin:hidden>
<odin:hidden property="a3721"></odin:hidden>
<!-- ����A80��ֵ -->
<odin:hidden property="a29314"></odin:hidden>
<odin:hidden property="a03033"></odin:hidden>
<odin:hidden property="a29321"></odin:hidden>
<odin:hidden property="a29324a"></odin:hidden>
<odin:hidden property="a29324b"></odin:hidden>
<odin:hidden property="a29327a"></odin:hidden>
<odin:hidden property="a29327b"></odin:hidden>
<odin:hidden property="a29334_GY"></odin:hidden>
<odin:hidden property="a29334_GM"></odin:hidden>
<odin:hidden property="a29337"></odin:hidden>
<odin:hidden property="a39061"></odin:hidden>
<odin:hidden property="a39064"></odin:hidden>
<odin:hidden property="a39067"></odin:hidden>
<odin:hidden property="a39071"></odin:hidden>
<odin:hidden property="a44027"></odin:hidden>
<odin:hidden property="a39077"></odin:hidden>
<odin:hidden property="a44031"></odin:hidden>
<odin:hidden property="a39084"></odin:hidden>
<odin:hidden property="a03011_a80"></odin:hidden>
<odin:hidden property="a03021_a80"></odin:hidden>
<odin:hidden property="a03095_a80"></odin:hidden>
<odin:hidden property="a03027_a80"></odin:hidden>
<odin:hidden property="a03014_a80"></odin:hidden>
<odin:hidden property="a03017_a80"></odin:hidden>
<odin:hidden property="a03018_a80"></odin:hidden>
<odin:hidden property="a03024_a80"></odin:hidden>
<!-- ����A81��ֵ -->
<odin:hidden property="g02001"></odin:hidden>
<odin:hidden property="a29071"></odin:hidden>
<odin:hidden property="a29072"></odin:hidden>
<odin:hidden property="a29341"></odin:hidden>
<odin:hidden property="a29073_Y"></odin:hidden>
<odin:hidden property="a29073_M"></odin:hidden>
<odin:hidden property="a29344"></odin:hidden>
<odin:hidden property="a29347a"></odin:hidden>
<odin:hidden property="a29347b"></odin:hidden>
<odin:hidden property="a29347c"></odin:hidden>
<odin:hidden property="a29351b"></odin:hidden>
<odin:hidden property="a39067_a81"></odin:hidden>
<odin:hidden property="a44027_a81"></odin:hidden>
<odin:hidden property="a39077_a81"></odin:hidden>
<odin:hidden property="a44031_a81"></odin:hidden>
<odin:hidden property="a39084_a81"></odin:hidden>
<odin:hidden property="a03011_a81"></odin:hidden>
<odin:hidden property="a03021_a81"></odin:hidden>
<odin:hidden property="a03095_a81"></odin:hidden>
<odin:hidden property="a03027_a81"></odin:hidden>
<odin:hidden property="a03014_a81"></odin:hidden>
<odin:hidden property="a03017_a81"></odin:hidden>
<odin:hidden property="a03018_a81"></odin:hidden>
<odin:hidden property="a03024_a81"></odin:hidden>
<!-- ����A82��ֵ -->
<odin:hidden property="a02191"></odin:hidden>
<odin:hidden property="a29301"></odin:hidden>
<odin:hidden property="a29304"></odin:hidden>
<odin:hidden property="a29044"></odin:hidden>
<odin:hidden property="a29307"></odin:hidden>
<!-- ����A83��ֵ -->
<odin:hidden property="a02192"></odin:hidden>
<odin:hidden property="a29311"></odin:hidden>
<odin:hidden property="g02002"></odin:hidden>
<odin:hidden property="a29044_a83"></odin:hidden>
<odin:hidden property="a29041"></odin:hidden>
<odin:hidden property="a29354"></odin:hidden>
<odin:hidden property="a44027_a83"></odin:hidden>
<odin:hidden property="a39077_a83"></odin:hidden>
<odin:hidden property="a44031_a83"></odin:hidden>
<odin:hidden property="a39084_a83"></odin:hidden>

<%-- <odin:hidden property="a03011"></odin:hidden>
<odin:hidden property="a03021"></odin:hidden>
<odin:hidden property="a03095"></odin:hidden>
<odin:hidden property="a03027"></odin:hidden>
<odin:hidden property="a03014"></odin:hidden>
<odin:hidden property="a03017"></odin:hidden>
<odin:hidden property="a03018"></odin:hidden>
<odin:hidden property="a03024"></odin:hidden>

<odin:hidden property="a8400"></odin:hidden>
<odin:hidden property="a84type"></odin:hidden> --%>
<!-- ����A11��ֵ -->
<odin:hidden property="a1101"></odin:hidden>
<odin:hidden property="a1104"></odin:hidden>
<odin:hidden property="a1107"></odin:hidden>
<odin:hidden property="a1111"></odin:hidden>
<odin:hidden property="a1114"></odin:hidden>
<odin:hidden property="a1151"></odin:hidden>
<odin:hidden property="a1121a"></odin:hidden>
<odin:hidden property="a1127"></odin:hidden>
<odin:hidden property="a1131"></odin:hidden>
<odin:hidden property="a1108"></odin:hidden>
<odin:hidden property="a1107c"></odin:hidden>
<odin:hidden property="g02003"></odin:hidden>
<odin:hidden property="a1108a"></odin:hidden>
<odin:hidden property="a1108b"></odin:hidden>
<odin:hidden property="g11003"></odin:hidden>
<odin:hidden property="g11004"></odin:hidden>
<odin:hidden property="g11005"></odin:hidden>
<odin:hidden property="g11006"></odin:hidden>
<odin:hidden property="g11007"></odin:hidden>
<odin:hidden property="g11008"></odin:hidden>
<odin:hidden property="g11009"></odin:hidden>
<odin:hidden property="g11010"></odin:hidden>
<odin:hidden property="g11011"></odin:hidden>
<odin:hidden property="g11012"></odin:hidden>
<odin:hidden property="g11013"></odin:hidden>
<odin:hidden property="g11014"></odin:hidden>
<odin:hidden property="g11015"></odin:hidden>


<odin:hidden property="a0000" title="��Աid"></odin:hidden>
<odin:hidden property="a01k02check" title="�Ƿ��л��㹤������" ></odin:hidden>
<odin:hidden property="n0150check" title="�Ƿ������򣨽ֵ���������ְ" ></odin:hidden>
<div id="border">
<table cellspacing="2" width="960" align="center" style="width: 100%">
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<odin:textEdit property="a0101" label="����" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0104" label="�Ա�" codeType="GB2261" required="true"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0107" isCheck="true"maxlength="8"  label="��������" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="a0117" codetype="GB3304" width="160" label="����" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="comboxArea_a0111" codetype="ZB01" width="160" label="����" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="comboxArea_a0114" codetype="ZB01" width="160" label="������" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0128" label="����״��" codeType="GB2261D" required="true"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0134" isCheck="true" label="�μӹ���ʱ��" maxlength="8" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 onchange="v_test1" property="a0141" label="������ò" width="160" codeType="GB4762"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0144" isCheck="true" label="�뵳ʱ��" maxlength="8"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a3921" label="�ڶ�����" width="160" codeType="GB4762" onchange="v_test2"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a3927" label="��������" width="160" codeType="GB4762" onchange="v_test3"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:textEdit property="a0187a" label="��Ϥרҵ�к��س�" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left">
						<input type="checkbox" id="n0150" onclick="Setn0150()"/>
						<label id="n0150SpanId" style="font-size: 12px;">�Ƿ������򣨽ֵ���������ְ </label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left">
						<input type="checkbox" id="a01k02" onclick="Seta01k02()"/>
						<label id="a01k02SpanId" style="font-size: 12px;">�Ƿ��л��㹤������ </label>
					</td>
				</tr>
				<tr>
					<td>
						<odin:numberEdit property="a0194_Y" label="���㹤������:��" size="3"></odin:numberEdit>	
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0131" label="����״��" codeType="HY24"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:textEdit property="n0152" label="���ż���ְ��"></odin:textEdit>	
					</td>
				</tr>
			</table>
		</td>
		
		
	</tr>
	
	
</table>
</div>
<div id="table2">
	<table>
		<tr>
			<td>
				<tags:PublicTextIconEdit property="a0195" codetype="orgTreeJsonData" width="160" label="ͳ�ƹ�ϵ���ڵ�λ" required="true" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="a0184" label="���֤��" required="true"></odin:textEdit>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0165" label="�������" codeType="ZB130" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0160" label="��Ա���" codeType="ZB125" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0121" label="��������" codeType="ZB135" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<tags:PublicTextIconEdit property="a0120" codetype="ZB134" width="160" label="���ʼ���" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<tags:PublicTextIconEdit property="a0122" codetype="ZB139" width="160" label="רҵ�����๫��Ա��ְ�ʸ�" readonly="true"/>
			</td>
		</tr>
	</table>
</div>
<div id="months">
<table>
	<tr>
		<td>
			<odin:numberEdit property="a0194_M" label="��" size="2"></odin:numberEdit>
		</td>
	</tr>
</table>
</div>
<div id="photo">
	 <p>��Ƭ</p>
<%-- 	<%String test = "8ad8e660-8b4f-445f-9180-58f08fb15e0d" %>
	<img alt="��Ƭ" id="personImg" style="cursor:pointer;" onclick="showdialog()" width="131" height="100%" src="<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=<%=URLEncoder.encode(URLEncoder.encode(test,"UTF-8"),"UTF-8")%>"> 
 --%></div>
<div id="a1701">
	<p>����</p>
</div>
<div id='btnSave'>
	<odin:button text="��&nbsp;&nbsp;��" handler="savePerson"></odin:button>
</div>
<div id='btnCancel'>
	<odin:button text="ȡ&nbsp;&nbsp;��" handler="Cancel"></odin:button>
</div>
<div id='btnPrint'>
	<odin:button text="��&nbsp;&nbsp;ӡ" handler="Print"></odin:button>
</div>
</body>
<script type="text/javascript">
function showdialog(){
	$h.showModalDialog('picupload',ctxPath+'/picCut/picwin.jsp?a0000="+a0000+"','ͷ���ϴ�',900,490,null,{a0000:'"+a0000+"'},true);
	/* var isUpdate = document.getElementById('isUpdate').value;
	if(isUpdate == 2){
		return;
	}
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src; */
}
function v_test(type){
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	
 	
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
	
 		//�ж�������ò
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//�ڶ������ж�
 		if(type == '2'){
 			
 			//�Ƿ��������ò�ظ�
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 			}
 			//���ɺ͵���������ͬ 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		//�жϵ�������
 		if(type==3){
 			//�ж��Ƿ��ǰ��������ͬ
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('����������ڶ����ɲ�����ͬ��');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('����������������ò������ͬ��');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������ڶ����ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 		
 	 		/* Ext.getCmp('a0144_1').setValue('');
	 		document.getElementById("a0144").value='';
	 		Ext.getCmp('a0144_1').setDisabled(true); */
 	 	}
 	
} 
function v_test3(){
	
	var type = '3';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		//�жϵ�������
 		if(type==3){
 			//�ж��Ƿ��ǰ��������ͬ
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('����������ڶ����ɲ�����ͬ��');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('����������������ò������ͬ��');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������ڶ����ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
function v_test2(){
	var type = '2';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		
 		
 		//�ڶ������ж�
 		if(type == '2'){
 			
 			//�Ƿ��������ò�ظ�
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 			}
 			//���ɺ͵���������ͬ 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
function v_test1(){
	
	var type = '1';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		//�ж�������ò
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});





Ext.onReady(function(){
	document.getElementById("a0194_Y").readOnly=true;
	document.getElementById("a0194_Y").disabled=true;
	document.getElementById("a0194_Y").style.backgroundColor="#EBEBE4";
	document.getElementById("a0194_Y").style.backgroundImage="none";
	document.getElementById("a0194_M").readOnly=true;
	document.getElementById("a0194_M").disabled=true;
	document.getElementById("a0194_M").style.backgroundColor="#EBEBE4";
	document.getElementById("a0194_M").style.backgroundImage="none";
});
function Seta01k02(){
	if(document.getElementById('a01k02').checked){
		document.getElementById('a01k02check').value = 1;
		document.getElementById("a0194_Y").readOnly=false;
		document.getElementById("a0194_Y").disabled=false;
		document.getElementById("a0194_Y").style.backgroundColor="#fff";
		document.getElementById("a0194_Y").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		document.getElementById("a0194_Y").value="";
		document.getElementById("a0194_M").readOnly=false;
		document.getElementById("a0194_M").disabled=false;
		document.getElementById("a0194_M").style.backgroundColor="#fff";
		document.getElementById("a0194_M").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		document.getElementById("a0194_M").value="";
	}else{
		document.getElementById('a01k02check').value = 0;
		document.getElementById("a0194_Y").readOnly=true;
		document.getElementById("a0194_Y").disabled=true;
		document.getElementById("a0194_Y").style.backgroundColor="#EBEBE4";
		document.getElementById("a0194_Y").style.backgroundImage="none";
		document.getElementById("a0194_Y").value="";
		document.getElementById("a0194_M").readOnly=true;
		document.getElementById("a0194_M").disabled=true;
		document.getElementById("a0194_M").style.backgroundColor="#EBEBE4";
		document.getElementById("a0194_M").style.backgroundImage="none";
		document.getElementById("a0194_M").value="";
	}
}

function Setn0150(){
	if(document.getElementById('n0150').checked){
		document.getElementById('n0150check').value = 1;
	}else{
		document.getElementById('n0150check').value = 0;
	}
}
Ext.onReady(function(){
	document.getElementById('n0150check').value=0;
	document.getElementById('a01k02check').value=0;
});

function Print(){
	alert(document.getElementById('a01k02check').value);
}
function savePerson(){
	Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
	var a0101 = document.getElementById('a0101').value;//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	//��������
	var a0107 = document.getElementById('a0107').value;	
	var a0107_1 = document.getElementById('a0107_1').value;	
	
	
	var text1 = dateValidateBeforeTady(a0107_1);
	if(a0107_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0107);
	}
	if(text1!==true){
		$h.alert('ϵͳ��ʾ','�����������ڣ�' + text1, null,400);
		return false;
	}
	var orthersWindow = null;
	//У�����֤�Ƿ�Ϸ�
	if(a0184==''){
		$h.alert('ϵͳ��ʾ��','���֤�Ų���Ϊ��!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('ϵͳ��ʾ��','���֤�Ų��ܳ���18λ!',null,220);
		return false;
	}
	
	//���֤��֤
	//var vtext = $h.IDCard(a0184);
	//var vtext = isIdCard(a0184);
	
	
	/* if(vtext!==true){
		//$h.alert('ϵͳ��ʾ��',vtext,null,320);
		$h.confirm("ϵͳ��ʾ��",vtext+'��<br/>�Ƿ�������棿',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
				savePersonSub(false);
			}else{
				return false;
			}		
		});
	}else{
		savePersonSub(true);
	} */
	a29();
	a30();
	a37();
	a80();
	a81();
	a82();
	a83();
	/* a84(); */
	a11();
	savePersonSub(true);
}
function a11(){
	document.getElementById("a1101").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1101").value;
	document.getElementById("a1104").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1104").value;
	document.getElementById("a1107").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1107").value;
	document.getElementById("a1111").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1111").value;
	document.getElementById("a1114").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1114").value;
	window.parent.frames["TrainAddPage_GB"].document.getElementById("a1151").checked?document.getElementById("a1151").value="1":document.getElementById("a1151").value="0";
	document.getElementById("a1121a").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1121a").value;
	document.getElementById("a1127").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1127").value;
	document.getElementById("a1131").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1131").value;
	document.getElementById("a1108").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1108").value;
	document.getElementById("a1107c").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1107c").value;
	document.getElementById("g02003").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g02003").value;
	document.getElementById("a1108a").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1108a").value;
	document.getElementById("a1108b").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("a1108b").value;
	document.getElementById("g11003").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11003").value;
	document.getElementById("g11004").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11004").value;
	document.getElementById("g11005").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11005").value;
	document.getElementById("g11006").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11006").value;
	document.getElementById("g11007").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11007").value;
	document.getElementById("g11008").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11008").value;
	document.getElementById("g11009").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11009").value;
	document.getElementById("g11010").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11010").value;
	document.getElementById("g11011").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11011").value;
	document.getElementById("g11012").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11012").value;
	document.getElementById("g11013").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11013").value;
	document.getElementById("g11014").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11014").value;
	document.getElementById("g11015").value = window.parent.frames["TrainAddPage_GB"].document.getElementById("g11015").value;
}
function a84(){
	document.getElementById("a03011").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03011").value;
	document.getElementById("a03021").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03021").value;
	document.getElementById("a03095").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03095").value;
	document.getElementById("a03027").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03027").value;
	document.getElementById("a03014").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03014").value;
	document.getElementById("a03017").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03017").value;
	document.getElementById("a03018").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03018").value;
	document.getElementById("a03024").value = window.parent.frames["ExamAddPage_GB"].document.getElementById("a03024").value;
}
function a83(){
	document.getElementById("a02192").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a02192").value;
	document.getElementById("a29311").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a29311").value;
	document.getElementById("g02002").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("g02002").value;
	document.getElementById("a29044_a83").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a29044").value;
	document.getElementById("a29041").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a29041").value;
	document.getElementById("a29354").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a29354").value;
	document.getElementById("a44027_a83").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a44027").value;
	document.getElementById("a39077_a83").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a39077").value;
	document.getElementById("a44031_a83").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a44031").value;
	document.getElementById("a39084_a83").value = window.parent.frames["OpenTransferringAddPage_GB"].document.getElementById("a39084").value;
}
function a82(){
	document.getElementById("a02191").value = window.parent.frames["OpenSelectAddPage_GB"].document.getElementById("a02191").value;
	document.getElementById("a29301").value = window.parent.frames["OpenSelectAddPage_GB"].document.getElementById("a29301").value;
	document.getElementById("a29304").value = window.parent.frames["OpenSelectAddPage_GB"].document.getElementById("a29304").value;
	document.getElementById("a29044").value = window.parent.frames["OpenSelectAddPage_GB"].document.getElementById("a29044").value;
	document.getElementById("a29307").value = window.parent.frames["OpenSelectAddPage_GB"].document.getElementById("a29307").value;
}


function a81(){
	document.getElementById("g02001").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("g02001").value;
	document.getElementById("a29071").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29071").value;
	document.getElementById("a29072").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29072").value;
	document.getElementById("a29341").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29341").value;
	document.getElementById("a29073_Y").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29073_Y").value;
	document.getElementById("a29073_M").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29073_M").value;
	document.getElementById("a29344").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29344").value;
	document.getElementById("a29347A").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347A").value;
	document.getElementById("a29347B").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347B").value;
	document.getElementById("a29347C").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347C").value;
	document.getElementById("a29351B").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29351B").value;
	document.getElementById("a39067").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39067").value;
	document.getElementById("a44027_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a44027").value;
	document.getElementById("a39077_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39077").value;
	document.getElementById("a44031_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a44031").value;
	document.getElementById("a39084_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39084").value;
	//����
	document.getElementById("a03011_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03011").value;
	document.getElementById("a03021_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03021").value;
	document.getElementById("a03095_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03095").value;
	document.getElementById("a03027_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03027").value;
	document.getElementById("a03014_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03014").value;
	document.getElementById("a03017_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03017").value;
	document.getElementById("a03018_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03018").value;
	document.getElementById("a03024_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a03024").value;

	/* window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("g02001").checked?document.getElementById("g02001").value="1":document.getElementById("g02001").value="0";
	document.getElementById("a29071").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29071").value;
	document.getElementById("a29072").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29072").value;
	document.getElementById("a29341").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29341").value;
	document.getElementById("a29073_Y").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29073_Y").value;
	document.getElementById("a29073_M").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29073_M").value;
	document.getElementById("a29344").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29344").value;
	document.getElementById("a29347a").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347a").value;
	document.getElementById("a29347b").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347b").value;
	document.getElementById("a29347c").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29347c").value;
	document.getElementById("a29351b").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a29351b").value;
	window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39067").checked?document.getElementById("a39067_a81").value="1":document.getElementById("a39067_a81").value="0";
	window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a44027").checked?document.getElementById("a44027_a81").value="1":document.getElementById("a44027_a81").value="0";
	document.getElementById("a39077_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39077").value;
	window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a44031").checked?document.getElementById("a44031_a81").value="1":document.getElementById("a44031_a81").value="0";
	document.getElementById("a39084_a81").value = window.parent.frames["SelectPersonAddPage_GB"].document.getElementById("a39084").value; */
}
function a80(){
	document.getElementById("a29314").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29314").value;
	document.getElementById("a03033").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03033").value;
	document.getElementById("a29321").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29321").value;
	document.getElementById("a29324a").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29324a").value;
	document.getElementById("a29324b").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29324b").value;
	document.getElementById("a29327a").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29327a").value;
	document.getElementById("a29327b").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29327b").value;
	document.getElementById("a29334_GY").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29334_GY").value;
	document.getElementById("a29334_GM").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29334_GM").value;
	document.getElementById("a29337").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a29337").value;
	document.getElementById("a39061").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39061").value;
	document.getElementById("a39064").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39064").value;
	document.getElementById("a39067").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39067").value;
	document.getElementById("a39071").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39071").value;
	document.getElementById("a44027").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a44027").value;
	document.getElementById("a39077").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39077").value;
	document.getElementById("a44031").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a44031").value;
	document.getElementById("a39084").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a39084").value;
	//����
	document.getElementById("a03011_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03011").value;
	document.getElementById("a03021_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03021").value;
	document.getElementById("a03095_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03095").value;
	document.getElementById("a03027_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03027").value;
	document.getElementById("a03014_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03014").value;
	document.getElementById("a03017_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03017").value;
	document.getElementById("a03018_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03018").value;
	document.getElementById("a03024_a80").value = window.parent.frames["ExaminationsAddPage_GB"].document.getElementById("a03024").value;

}
function a37(){
	document.getElementById("a3701").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3701").value;
	document.getElementById("a3707a").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3707a").value;
	document.getElementById("a3707b").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3707b").value;
	document.getElementById("a3707c").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3707c").value;
	document.getElementById("a3711").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3711").value;
	document.getElementById("a3721").value = window.parent.frames["AddressAddPage_GB"].document.getElementById("a3721").value;
	
}
function a30(){
	document.getElementById("a3001").value = window.parent.frames["ExitAddPage_GB"].document.getElementById("a3001").value;
	document.getElementById("a3004").value = window.parent.frames["ExitAddPage_GB"].document.getElementById("a3004").value;
	document.getElementById("a3034").value = window.parent.frames["ExitAddPage_GB"].document.getElementById("a3034").value;
	document.getElementById("a3954a").value = window.parent.frames["ExitAddPage_GB"].document.getElementById("a3954a").value;
	document.getElementById("a3954b").value = window.parent.frames["ExitAddPage_GB"].document.getElementById("a3954b").value;
	
}
function a29(){
	document.getElementById("a2907").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2907").value;
	document.getElementById("a2911").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2911").value;
	document.getElementById("a2921a").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2921a").value;
	document.getElementById("a2941").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2941").value;
	document.getElementById("a2944").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2944").value;
	document.getElementById("a2947a_Y").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2947a_Y").value;
	document.getElementById("a2947a_M").value = window.parent.frames["EnterAddPage_GB"].document.getElementById("a2947a_M").value;
	//����Ա�Ǽ�ʱ��,�Ϸ���У��
	var a2949_1 = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2947").value;//����Ա�Ǽ�ʱ��
	
	var text2 = dateValidateBeforeTady(a2949_1);
	if(a2949_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a2949);
	}
	if(text2!==true){
		$h.alert('ϵͳ��ʾ','����Ա�Ǽ�ʱ�䣺' + text2, null,400);
		return false;
	}
	document.getElementById("a2947").value = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2947").value;
	document.getElementById("a2949").value = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2949").value;
}
function savePersonSub(isIdcard){
	var a0101 = document.getElementById('a0101').value.replace(/\s/g, "");//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	var a0104 = document.getElementById('a0104').value;//�Ա�
	var a0111_combo = document.getElementById('comboxArea_a0111').value;//����
	var a0114 = document.getElementById('comboxArea_a0114').value.replace(/\s/g, "");//������
	//var a0114_combo = document.getElementById('comboxArea_a0114').value;//������

	var a0134 = document.getElementById('a0134').value;//�μӹ���ʱ��
	var a0117 = document.getElementById('a0117').value;//����
	var a0128 = document.getElementById('a0128').value;//����״��
	
	var a0195 = document.getElementById('a0195').value;//ͳ�ƹ�ϵ���ڵ�λ
	var a0160 = document.getElementById('a0160').value;//��Ա���
	var a0165 = document.getElementById('a0165').value;//�������
	//var a1701 = document.getElementById('a1701').value;//����
	var a0121 = document.getElementById('a0121').value;//��������
	//var a2949 = document.getElementById('a2949').value;//����Ա�Ǽ�ʱ��
	
	if(a0101==''){
		$h.alert('ϵͳ��ʾ��','��������Ϊ�գ�',null,220);
		return false;
	}
	if(a0101.length==1){
		$h.alert('ϵͳ��ʾ��','��������Ϊһ���֣�',null,220);
		return false;
	}
	//alert(a0101.substr(a0101.length-1,1)==/\./);
	if(/^[\.\��]/.test(a0101)){
		$h.alert('ϵͳ��ʾ��','���������ԡ���ͷ��',null,220);
		return false;
	}
	if(/[\.\��][\.\��]/.test(a0101)){
		$h.alert('ϵͳ��ʾ��','����������������2������',null,220);
		return false;
	}
	if(/[\.\��]$/.test(a0101)){
		$h.alert('ϵͳ��ʾ��','���������ԡ���β��',null,220);
		return false;
	}
	if(a0101.length>18){
		$h.alert('ϵͳ��ʾ��','�����������ܳ���18��',null,220); 
		return false;
	}
	if(a0104==''){
		$h.alert('ϵͳ��ʾ��','�Ա���Ϊ�գ�',null,220); 
		return false;
	}
	if(a0107==''){
		$h.alert('ϵͳ��ʾ��','�������²���Ϊ�գ�',null,220); 
		return false;
	}
	if(a0117==''){
		$h.alert('ϵͳ��ʾ��','���岻��Ϊ�գ�',null,220); 
		return false;
	}
	if(a0111_combo==''){
		$h.alert('ϵͳ��ʾ��','���᲻��Ϊ�գ�',null,220); 
		return false;
	}
	if(a0114==''){
		$h.alert('ϵͳ��ʾ��','�����ز���Ϊ�գ�',null,220); 
		return false;
	}
	/* if(a0141==''){
		$h.alert('ϵͳ��ʾ��','������ò����Ϊ�գ�',null,220); 
		return false;
	} */
	if(a0128==''){
		$h.alert('ϵͳ��ʾ��','����״������Ϊ�գ�',null,220); 
		return false;
	}
	if(a0134==''){
		$h.alert('ϵͳ��ʾ��','�μӹ���ʱ�䲻��Ϊ�գ�',null,220); 
		return false;
	}
	/* if(a1701==''){
		$h.alert('ϵͳ��ʾ��','��������Ϊ�գ�',null,220); 
		return false;
	} */
	if(a0195==''){
		$h.alert('ϵͳ��ʾ��','ͳ�ƹ�ϵ���ڵ�λ����Ϊ�գ�',null,220); 
		return false;
	}
	if(a0165==''){
		$h.alert('ϵͳ��ʾ��','���������Ϊ�գ�',null,220); 
		return false;
	}
	if(a0160==''){
		$h.alert('ϵͳ��ʾ��','��Ա�����Ϊ�գ�',null,220); 
		return false;
	}
	if(a0121==''){
		$h.alert('ϵͳ��ʾ��','�������Ͳ���Ϊ�գ�',null,220); 
		return false;
	}
	
	//�������ڸ�ʽ
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('ϵͳ��ʾ��','�������£�'+datetext,null,320);
		return false;
	}
	
	//������òһЩ�в���
	var a0141 = document.getElementById('a0141').value;//������ò
	if(a0141=='01'||a0141=='02'){
		//�뵳ʱ��
		var a0144 = document.getElementById('a0144').value;	
		var a0144_1 = document.getElementById('a0144_1').value;	
		if(a0144==''||a0144==undefined){
			$h.alert('ϵͳ��ʾ','����д�뵳ʱ�䣡', null,400);
			return false;
		}else{
			//�뵳ʱ��
			var a0144 = document.getElementById('a0144').value;	
			var a0144_1 = document.getElementById('a0144_1').value;	
			var text1 = dateValidate(a0144_1);
			if(a0144_1.indexOf(".") > 0){
				text1 = dateValidate(a0144);
			}
			if(text1!==true){
				$h.alert('ϵͳ��ʾ','�뵳ʱ�䣺' + text1, null,400);
				return false;
			}
		}
		
	}
	//���㹤��ʱ��
	if(document.getElementById('a01k02check').value==1){
		var a0194_Y = document.getElementById('a0194_Y').value;
		if(a0194_Y==undefined||a0194_Y==''){
			if (!(/(^[0-9]\d*$)/.test(a0194_Y))) { 
				Ext.Msg.alert("��ʾ��Ϣ", "ѡ����㹤����������������Ȼ����");
		��������return false; 
			}
		}
		var a0194_M = document.getElementById('a0194_M').value;
		if(a0194_M==undefined||a0194_M==''||a0194_M=='1'||a0194_M=='2'||a0194_M=='3'||a0194_M=='4'||a0194_M=='5'||a0194_M=='6'||a0194_M=='7'||a0194_M=='8'||a0194_M=='9'||a0194_M=='10'||a0194_M=='11'||a0194_M=='12'){
			
		}else{
			Ext.Msg.alert("��ʾ��Ϣ", "ѡ����㹤����������������1~12֮�䣡");
		����return false; 
		}
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = document.getElementById('a0107').value;//��������
	var msg = '�������������֤��һ�£�<br/>�Ƿ�������棿';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("ϵͳ��ʾ��",msg,200,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('���Ժ�...','ϵͳ��ʾ��');
				radow.doEvent('save.onclick',confirm);
			}else{
				return false;
			}		
		});	
		return false;
	}else{
		radow.doEvent('save.onclick',confirm); 
		
	}
}

//��֤���֤�Ų���ȡ��������
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	iIdNo = trim(iIdNo);
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		
		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		return tmpStr;
	}
}
function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };
function Cancel(){
	alert("demo");
}
function witherTwoYear(){
	/*  	var check = '0';
		if($('#a0197').is(':checked')){
			check = '1';
		} */
		
		//realParent.document.getElementById('a0197').value=record.data.key;
}

//���֤�ļ���
function isIdCard(sId){
	
	//��15λ���֤����ת��Ϊ18λ 
	var sId = changeFivteenToEighteen(sId);
	//alert(sId);
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) return "����������֤���Ȼ��ʽ����";
	sId=sId.replace(/x$/i,"a");
	if(aCity[parseInt(sId.substr(0,2))]==null) return "�������֤�����Ƿ�";
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "���֤�ϵĳ������ڷǷ�";
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) return "����������֤�ŷǷ�";
	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"��":"Ů");//�˴λ������жϳ���������֤�ŵ����Ա�
	 
	//��֤�Ա�
	//�ж����֤�����ڶ�λ�Ƿ���Ա�һ��
	var sex = sId.substr((sId.length-2), 1);
	var a0104 = document.getElementById('a0104').value;		//�Ա�
	
	var sexA0104 = sex%2;		//ȡ����
	
	if(sexA0104 == 0){
		sexA0104 = 2;
	}	
	
	if(sexA0104 != a0104){
		return "���֤���뵹���ڶ�λ�����Ա�һ��";
	}
	 
	return true;
}
</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
}
#a1701{position: absolute;top:300px;left:500px;}
#table2{position: absolute;top:1px;left:300px;}
#photo{position: absolute;top:120px;left:750px;}
#btnSave{position: absolute;top:550px;left:400px;}
#btnCancel{position: absolute;top:550px;left:450px;}
#btnPrint{position: absolute;top:550px;left:500px;}
#months{position: absolute;top:440px;left:172px;}
</style>



