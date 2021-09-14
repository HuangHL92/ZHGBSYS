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
<!-- 隐藏A29的值 -->
<odin:hidden property="a2907"></odin:hidden>
<odin:hidden property="a2911"></odin:hidden>
<odin:hidden property="a2921a"></odin:hidden>
<odin:hidden property="a2941"></odin:hidden>
<odin:hidden property="a2944"></odin:hidden>
<odin:hidden property="a2947a_M"></odin:hidden>
<odin:hidden property="a2947a_Y"></odin:hidden>
<odin:hidden property="a2947"></odin:hidden>
<odin:hidden property="a2949"></odin:hidden>
<!-- 隐藏A30的值 -->
<odin:hidden property="a3001"></odin:hidden>
<odin:hidden property="a3004"></odin:hidden>
<odin:hidden property="a3034"></odin:hidden>
<odin:hidden property="a3954a"></odin:hidden>
<odin:hidden property="a3954b"></odin:hidden>
<!-- 隐藏A37的值 -->
<odin:hidden property="a3701"></odin:hidden>
<odin:hidden property="a3707a"></odin:hidden>
<odin:hidden property="a3707b"></odin:hidden>
<odin:hidden property="a3707c"></odin:hidden>
<odin:hidden property="a3711"></odin:hidden>
<odin:hidden property="a3721"></odin:hidden>
<!-- 隐藏A80的值 -->
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
<!-- 隐藏A81的值 -->
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
<!-- 隐藏A82的值 -->
<odin:hidden property="a02191"></odin:hidden>
<odin:hidden property="a29301"></odin:hidden>
<odin:hidden property="a29304"></odin:hidden>
<odin:hidden property="a29044"></odin:hidden>
<odin:hidden property="a29307"></odin:hidden>
<!-- 隐藏A83的值 -->
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
<!-- 隐藏A11的值 -->
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


<odin:hidden property="a0000" title="人员id"></odin:hidden>
<odin:hidden property="a01k02check" title="是否有基层工作经历" ></odin:hidden>
<odin:hidden property="n0150check" title="是否担任乡镇（街道）党政正职" ></odin:hidden>
<div id="border">
<table cellspacing="2" width="960" align="center" style="width: 100%">
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<odin:textEdit property="a0101" label="姓名" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0104" label="性别" codeType="GB2261" required="true"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0107" isCheck="true"maxlength="8"  label="出生年月" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="a0117" codetype="GB3304" width="160" label="民族" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="comboxArea_a0111" codetype="ZB01" width="160" label="籍贯" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<tags:PublicTextIconEdit property="comboxArea_a0114" codetype="ZB01" width="160" label="出生地" required="true" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0128" label="健康状况" codeType="GB2261D" required="true"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0134" isCheck="true" label="参加工作时间" maxlength="8" required="true"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 onchange="v_test1" property="a0141" label="政治面貌" width="160" codeType="GB4762"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:NewDateEditTag property="a0144" isCheck="true" label="入党时间" maxlength="8"></odin:NewDateEditTag>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a3921" label="第二党派" width="160" codeType="GB4762" onchange="v_test2"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a3927" label="第三党派" width="160" codeType="GB4762" onchange="v_test3"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:textEdit property="a0187a" label="熟悉专业有何特长" required="true"></odin:textEdit>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left">
						<input type="checkbox" id="n0150" onclick="Setn0150()"/>
						<label id="n0150SpanId" style="font-size: 12px;">是否担任乡镇（街道）党政正职 </label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="left">
						<input type="checkbox" id="a01k02" onclick="Seta01k02()"/>
						<label id="a01k02SpanId" style="font-size: 12px;">是否有基层工作经历 </label>
					</td>
				</tr>
				<tr>
					<td>
						<odin:numberEdit property="a0194_Y" label="基层工作经历:年" size="3"></odin:numberEdit>	
					</td>
				</tr>
				<tr>
					<td>
						<odin:select2 property="a0131" label="婚姻状况" codeType="HY24"></odin:select2>
					</td>
				</tr>
				<tr>
					<td>
						<odin:textEdit property="n0152" label="社团兼任职务"></odin:textEdit>	
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
				<tags:PublicTextIconEdit property="a0195" codetype="orgTreeJsonData" width="160" label="统计关系所在单位" required="true" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<odin:textEdit property="a0184" label="身份证号" required="true"></odin:textEdit>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0165" label="管理类别" codeType="ZB130" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0160" label="人员类别" codeType="ZB125" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<odin:select2 property="a0121" label="编制类型" codeType="ZB135" required="true"></odin:select2>
			</td>
		</tr>
		<tr>
			<td>
				<tags:PublicTextIconEdit property="a0120" codetype="ZB134" width="160" label="工资级别" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<tags:PublicTextIconEdit property="a0122" codetype="ZB139" width="160" label="专业技术类公务员任职资格" readonly="true"/>
			</td>
		</tr>
	</table>
</div>
<div id="months">
<table>
	<tr>
		<td>
			<odin:numberEdit property="a0194_M" label="月" size="2"></odin:numberEdit>
		</td>
	</tr>
</table>
</div>
<div id="photo">
	 <p>照片</p>
<%-- 	<%String test = "8ad8e660-8b4f-445f-9180-58f08fb15e0d" %>
	<img alt="照片" id="personImg" style="cursor:pointer;" onclick="showdialog()" width="131" height="100%" src="<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=<%=URLEncoder.encode(URLEncoder.encode(test,"UTF-8"),"UTF-8")%>"> 
 --%></div>
<div id="a1701">
	<p>简历</p>
</div>
<div id='btnSave'>
	<odin:button text="保&nbsp;&nbsp;存" handler="savePerson"></odin:button>
</div>
<div id='btnCancel'>
	<odin:button text="取&nbsp;&nbsp;消" handler="Cancel"></odin:button>
</div>
<div id='btnPrint'>
	<odin:button text="打&nbsp;&nbsp;印" handler="Print"></odin:button>
</div>
</body>
<script type="text/javascript">
function showdialog(){
	$h.showModalDialog('picupload',ctxPath+'/picCut/picwin.jsp?a0000="+a0000+"','头像上传',900,490,null,{a0000:'"+a0000+"'},true);
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
	
	//获取下拉选的值
	var a0141 = document.getElementById('a0141').value;		//政治面貌
 	var a0144 = document.getElementById('a0144').value;		//入党时间
 	var a3921 = document.getElementById('a3921').value;		//第二党派
 	var a3927 = document.getElementById('a3927').value;		//第三党派 
 	
 	
 	//如果政治面貌为群众、无党派，他输入框均不可输入
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
 		alert('不可选择群众或无党派！');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('不可选择群众或无党派！');
		return false;
 	}
 	
	
 		//判断政治面貌
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//第二党派判断
 		if(type == '2'){
 			
 			//是否和政治面貌重复
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与政治面貌不能相同！');
 	 		 	return false;
 			}
 			//不可和第三党派相同 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与政治面貌不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		//判断第三党派
 		if(type==3){
 			//判断是否和前两党派相同
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('第三党派与第二党派不能相同！');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('第三党派与政治面貌不能相同！');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与第二党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与政治面貌不能相同！');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//最后判断是否有共产党党派，有“入党时间”可以编辑 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//有共产党党派
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
	
	//获取下拉选的值
	var a0141 = document.getElementById('a0141').value;		//政治面貌
 	var a0144 = document.getElementById('a0144').value;		//入党时间
 	var a3921 = document.getElementById('a3921').value;		//第二党派
 	var a3927 = document.getElementById('a3927').value;		//第三党派 
 	
 	//如果政治面貌为群众、无党派，他输入框均不可输入
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
 		alert('不可选择群众或无党派！');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('不可选择群众或无党派！');
		return false;
 	}
 	
 		
 		//判断第三党派
 		if(type==3){
 			//判断是否和前两党派相同
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('第三党派与第二党派不能相同！');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('第三党派与政治面貌不能相同！');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与第二党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与政治面貌不能相同！');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//最后判断是否有共产党党派，有“入党时间”可以编辑 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//有共产党党派
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
function v_test2(){
	var type = '2';
	
	//获取下拉选的值
	var a0141 = document.getElementById('a0141').value;		//政治面貌
 	var a0144 = document.getElementById('a0144').value;		//入党时间
 	var a3921 = document.getElementById('a3921').value;		//第二党派
 	var a3927 = document.getElementById('a3927').value;		//第三党派 
 	
 	//如果政治面貌为群众、无党派，他输入框均不可输入
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
 		alert('不可选择群众或无党派！');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('不可选择群众或无党派！');
		return false;
 	}
 	
 		
 		
 		
 		//第二党派判断
 		if(type == '2'){
 			
 			//是否和政治面貌重复
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与政治面貌不能相同！');
 	 		 	return false;
 			}
 			//不可和第三党派相同 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与政治面貌不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		
 		//最后判断是否有共产党党派，有“入党时间”可以编辑 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//有共产党党派
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
function v_test1(){
	
	var type = '1';
	
	//获取下拉选的值
	var a0141 = document.getElementById('a0141').value;		//政治面貌
 	var a0144 = document.getElementById('a0144').value;		//入党时间
 	var a3921 = document.getElementById('a3921').value;		//第二党派
 	var a3927 = document.getElementById('a3927').value;		//第三党派 
 	
 	//如果政治面貌为群众、无党派，他输入框均不可输入
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
 		alert('不可选择群众或无党派！');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('不可选择群众或无党派！');
		return false;
 	}
 	
 		
 		//判断政治面貌
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//最后判断是否有共产党党派，有“入党时间”可以编辑 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//有共产党党派
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
	Ext.Msg.wait('请稍候...','系统提示：');
	var a0101 = document.getElementById('a0101').value;//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	//出生年月
	var a0107 = document.getElementById('a0107').value;	
	var a0107_1 = document.getElementById('a0107_1').value;	
	
	
	var text1 = dateValidateBeforeTady(a0107_1);
	if(a0107_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0107);
	}
	if(text1!==true){
		$h.alert('系统提示','出生年月日期：' + text1, null,400);
		return false;
	}
	var orthersWindow = null;
	//校验身份证是否合法
	if(a0184==''){
		$h.alert('系统提示：','身份证号不能为空!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('系统提示：','身份证号不能超过18位!',null,220);
		return false;
	}
	
	//身份证验证
	//var vtext = $h.IDCard(a0184);
	//var vtext = isIdCard(a0184);
	
	
	/* if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续保存？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				Ext.Msg.wait('请稍候...','系统提示：');
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
	//号码
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
	//号码
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
	//公务员登记时间,合法性校验
	var a2949_1 = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2947").value;//公务员登记时间
	
	var text2 = dateValidateBeforeTady(a2949_1);
	if(a2949_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a2949);
	}
	if(text2!==true){
		$h.alert('系统提示','公务员登记时间：' + text2, null,400);
		return false;
	}
	document.getElementById("a2947").value = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2947").value;
	document.getElementById("a2949").value = window.parent.frames["RegisterAddPage_GB"].document.getElementById("a2949").value;
}
function savePersonSub(isIdcard){
	var a0101 = document.getElementById('a0101').value.replace(/\s/g, "");//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var a0104 = document.getElementById('a0104').value;//性别
	var a0111_combo = document.getElementById('comboxArea_a0111').value;//籍贯
	var a0114 = document.getElementById('comboxArea_a0114').value.replace(/\s/g, "");//出生地
	//var a0114_combo = document.getElementById('comboxArea_a0114').value;//出生地

	var a0134 = document.getElementById('a0134').value;//参加工作时间
	var a0117 = document.getElementById('a0117').value;//民族
	var a0128 = document.getElementById('a0128').value;//健康状况
	
	var a0195 = document.getElementById('a0195').value;//统计关系所在单位
	var a0160 = document.getElementById('a0160').value;//人员类别
	var a0165 = document.getElementById('a0165').value;//管理类别
	//var a1701 = document.getElementById('a1701').value;//简历
	var a0121 = document.getElementById('a0121').value;//编制类型
	//var a2949 = document.getElementById('a2949').value;//公务员登记时间
	
	if(a0101==''){
		$h.alert('系统提示：','姓名不能为空！',null,220);
		return false;
	}
	if(a0101.length==1){
		$h.alert('系统提示：','姓名不能为一个字！',null,220);
		return false;
	}
	//alert(a0101.substr(a0101.length-1,1)==/\./);
	if(/^[\.\·]/.test(a0101)){
		$h.alert('系统提示：','姓名不能以·开头！',null,220);
		return false;
	}
	if(/[\.\·][\.\·]/.test(a0101)){
		$h.alert('系统提示：','姓名不能连续出现2个·！',null,220);
		return false;
	}
	if(/[\.\·]$/.test(a0101)){
		$h.alert('系统提示：','姓名不能以·结尾！',null,220);
		return false;
	}
	if(a0101.length>18){
		$h.alert('系统提示：','姓名字数不能超过18！',null,220); 
		return false;
	}
	if(a0104==''){
		$h.alert('系统提示：','性别不能为空！',null,220); 
		return false;
	}
	if(a0107==''){
		$h.alert('系统提示：','出生年月不能为空！',null,220); 
		return false;
	}
	if(a0117==''){
		$h.alert('系统提示：','民族不能为空！',null,220); 
		return false;
	}
	if(a0111_combo==''){
		$h.alert('系统提示：','籍贯不能为空！',null,220); 
		return false;
	}
	if(a0114==''){
		$h.alert('系统提示：','出生地不能为空！',null,220); 
		return false;
	}
	/* if(a0141==''){
		$h.alert('系统提示：','政治面貌不能为空！',null,220); 
		return false;
	} */
	if(a0128==''){
		$h.alert('系统提示：','健康状况不能为空！',null,220); 
		return false;
	}
	if(a0134==''){
		$h.alert('系统提示：','参加工作时间不能为空！',null,220); 
		return false;
	}
	/* if(a1701==''){
		$h.alert('系统提示：','简历不能为空！',null,220); 
		return false;
	} */
	if(a0195==''){
		$h.alert('系统提示：','统计关系所在单位不能为空！',null,220); 
		return false;
	}
	if(a0165==''){
		$h.alert('系统提示：','管理类别不能为空！',null,220); 
		return false;
	}
	if(a0160==''){
		$h.alert('系统提示：','人员类别不能为空！',null,220); 
		return false;
	}
	if(a0121==''){
		$h.alert('系统提示：','编制类型不能为空！',null,220); 
		return false;
	}
	
	//出生日期格式
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('系统提示：','出生年月：'+datetext,null,320);
		return false;
	}
	
	//政治面貌一些列操作
	var a0141 = document.getElementById('a0141').value;//政治面貌
	if(a0141=='01'||a0141=='02'){
		//入党时间
		var a0144 = document.getElementById('a0144').value;	
		var a0144_1 = document.getElementById('a0144_1').value;	
		if(a0144==''||a0144==undefined){
			$h.alert('系统提示','请填写入党时间！', null,400);
			return false;
		}else{
			//入党时间
			var a0144 = document.getElementById('a0144').value;	
			var a0144_1 = document.getElementById('a0144_1').value;	
			var text1 = dateValidate(a0144_1);
			if(a0144_1.indexOf(".") > 0){
				text1 = dateValidate(a0144);
			}
			if(text1!==true){
				$h.alert('系统提示','入党时间：' + text1, null,400);
				return false;
			}
		}
		
	}
	//基层工作时间
	if(document.getElementById('a01k02check').value==1){
		var a0194_Y = document.getElementById('a0194_Y').value;
		if(a0194_Y==undefined||a0194_Y==''){
			if (!(/(^[0-9]\d*$)/.test(a0194_Y))) { 
				Ext.Msg.alert("提示信息", "选择基层工作经历年数不是自然数！");
		　　　　return false; 
			}
		}
		var a0194_M = document.getElementById('a0194_M').value;
		if(a0194_M==undefined||a0194_M==''||a0194_M=='1'||a0194_M=='2'||a0194_M=='3'||a0194_M=='4'||a0194_M=='5'||a0194_M=='6'||a0194_M=='7'||a0194_M=='8'||a0194_M=='9'||a0194_M=='10'||a0194_M=='11'||a0194_M=='12'){
			
		}else{
			Ext.Msg.alert("提示信息", "选择基层工作经历月数必须在1~12之间！");
		　　return false; 
		}
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = document.getElementById('a0107').value;//出身年月
	var msg = '出生日期与身份证不一致，<br/>是否继续保存？';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("系统提示：",msg,200,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('请稍候...','系统提示：');
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

//验证身份证号并获取出生日期
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

//身份证的检验
function isIdCard(sId){
	
	//将15位身份证号码转换为18位 
	var sId = changeFivteenToEighteen(sId);
	//alert(sId);
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) return "您输入的身份证长度或格式错误";
	sId=sId.replace(/x$/i,"a");
	if(aCity[parseInt(sId.substr(0,2))]==null) return "您的身份证地区非法";
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) return "您输入的身份证号非法";
	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
	 
	//验证性别
	//判断身份证倒数第二位是否和性别一致
	var sex = sId.substr((sId.length-2), 1);
	var a0104 = document.getElementById('a0104').value;		//性别
	
	var sexA0104 = sex%2;		//取余数
	
	if(sexA0104 == 0){
		sexA0104 = 2;
	}	
	
	if(sexA0104 != a0104){
		return "身份证号码倒数第二位数和性别不一致";
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



