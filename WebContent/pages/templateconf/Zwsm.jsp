<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%
	 String initParams=request.getParameter("initParams");
	 System.out.println(initParams);
	 String[] split=initParams.split("@");
	 String gid=split[0];
	 String id=split[1];
%>

<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>

<script type="text/javascript">
	
	Ext.onReady(function() {
		$('#gid').val('<%=gid %>');
		$('#id').val('<%=id %>');
	}); 
	
	function tjry(){
		var init='<%=gid %>';
		//alert(1);
		var  contextPath='<%=request.getContextPath()%>';
		var param ="";
	    $h.openWin('pbzk','pages.templateconf.Pbzk&initParams='+init,'配备状况添加', 430, 300,param, contextPath);
	}
	
	function huixian(param){
		var paramArr=param.split('@');
		var id=paramArr[0];
		var xhhh=paramArr[1];
		var dw=paramArr[2];
		var zwmc=paramArr[3];
		var zwfg=paramArr[4];
		var zgtj=paramArr[5];
		var pbzk=paramArr[6];
		var nl=paramArr[7];
		var nlArr=nl.split('&');
		var nl1=nlArr[0];
		var nl2=nlArr[1];
		var xb=paramArr[8];
		var xl=paramArr[9];
		var zy=paramArr[10];
		var zc=paramArr[11];
		var dp=paramArr[12];
		var dy=paramArr[13];
		var csfg=paramArr[14];
		var csfgArr=csfg.split('&');
		var csfg1=csfgArr[0];
		var csfg2=csfgArr[1];
		var level=paramArr[15];
		var levelArr=level.split('#');
		var jbccArr=levelArr[0].split('&');
		var level1=jbccArr[0];
		var level2=jbccArr[1];
		var level3=levelArr[1];
		var zwccArr=levelArr[2].split('&');
		var level4=zwccArr[0];
		var level5=zwccArr[1];
		var lb=paramArr[16];
		var lbArr=lb.split('&');
		var lb1=lbArr[0];
		var lb2=lbArr[1];
		var nlyq=paramArr[17];
		var select = paramArr[18];
		var option = select.split("#");
		$('#xhhh').val(xhhh);
		$('#zwmc').val(zwmc);
		$('#zwfg').val(zwfg);
		$('#zgtj').val(zgtj);
		$('#pbzk').val(pbzk);
		$('#nl').val(nl1);
		$('#nlval').val(nl2);
		$('#csfg1').val(csfg1);
		$('#csfg2').val(csfg2);
		$('#levelSelect').val(level3);
		$('#jbcc1').val(level1);
		$('#jbcc2').val(level2);
		$('#zwcc1').val(level4);
		$('#zwcc2').val(level5);
		$('#lb1').val(lb1);
		$('#lb2').val(lb2);
		document.getElementById("select1").value=option[0];
		document.getElementById("select2").value=option[1];
		document.getElementById("select3").value=option[2];
		document.getElementById("select4").value=option[3];
		document.getElementById("select5").value=option[4];
		document.getElementById("select6").value=option[5];
		document.getElementById("select7").value=option[6];
		document.getElementById("select8").value=option[7];
		document.getElementById("select9").value=option[8];
		
	}
	
	function addNames(names){
		//alert(names);
		$('#pbzk').val(names);
	}
	
	function save(){
		var xhhh=$('#xhhh').val();
		var zwmc=$('#zwmc').val();
		var zwfg=$('#zwfg').val();
		var zgtj=$('#zgtj').val();
		var pbzk=$('#pbzk').val();
		var age=$('#nl').val()+'&'+$('#nlval').val();
		var xb= document.getElementById('xb').value;
		var xl= document.getElementById('xl').value;
		var zy= document.getElementById('zy').value;
		var zc= document.getElementById('zc').value;
		var nlyq= document.getElementById('nlyq').value;
		var dp= document.getElementById('dp').value;
		var dy= document.getElementById('dy').value;
		var csfg=$('#csfg1').val()+'&'+$('#csfg2').val();
		var level=$('#jbcc1').val()+'&'+$('#jbcc2').val()+'#'+$('#levelSelect').val()+'#'+$('#zwcc1').val()+'&'+$('#zwcc2').val();
		var lb=$('#lb1').val()+'&'+$('#lb2').val();
		var dw=document.getElementById('gid').value;
		var id=document.getElementById('id').value;
		var myselect1=document.getElementById("select1");
		var myselect2=document.getElementById("select2");
		var myselect3=document.getElementById("select3");
		var myselect4=document.getElementById("select4");
		var myselect5=document.getElementById("select5");
		var myselect6=document.getElementById("select6");
		var myselect7=document.getElementById("select7");
		var myselect8=document.getElementById("select8");
		var myselect9=document.getElementById("select9");
		var index1=myselect1.selectedIndex ;
		var index2=myselect2.selectedIndex ;
		var index3=myselect3.selectedIndex ;
		var index4=myselect4.selectedIndex ;
		var index5=myselect5.selectedIndex ;
		var index6=myselect6.selectedIndex ;
		var index7=myselect7.selectedIndex ;
		var index8=myselect8.selectedIndex ;
		var index9=myselect9.selectedIndex ;
		option1= myselect1.options[index1].value; 
		option2= myselect2.options[index2].value; 
		option3= myselect3.options[index3].value; 
		option4= myselect4.options[index4].value; 
		option5= myselect5.options[index5].value; 
		option6= myselect6.options[index6].value; 
		option7= myselect7.options[index7].value; 
		option8= myselect8.options[index8].value; 
		option9= myselect9.options[index9].value; 
		if(option1=="0"){
			alert("条件筛选当中第一条是必选项");
			return;
		}
		   var ary = new Array(option1,option2,option3,option4,option5,option6,option7,option8,option9);
	        var nary=ary.sort();
	        for(var i=0;i<ary.length;i++){
	            if (nary[i]==nary[i+1]&&nary[i]!=0){
	            	//alert("数组重复内容："+nary[i]);
	            	alert("条件筛选中有重复选项")
	            	return;
	        }
	        }
		var condition= option1+"#"+option2+"#"+option3+"#"+option4+"#"+option5+"#"+option6+"#"+option7+"#"+option8+"#"+option9;
		
		var param=xhhh+"@"+zwmc+"@"+zwfg+"@"+zgtj+"@"+pbzk+"@"+age+"@"+xb+"@"+xl+"@"+zy+"@"+zc+"@"+nlyq+"@"
		+dp+"@"+dy+"@"+csfg+"@"+level+"@"+lb+"@"+dw+"@"+id+"@"+condition;
		//alert(dw);
		radow.doEvent("saveData",param);
	}
	
	function closeAll(){
		radow.doEvent("close",1);
	}
</script>

<div id="Org" style="height: 85%;">
<table style="width: 100%">
	<tr align="center">
		<td valign="top" width="40%">
			<odin:groupBox title="基本信息">
			<div id='div11' style="height: 340px;">
			<table>
				<tr>
					<odin:textEdit property="xhhh"  width="50" label="序号" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="zwmc"  width="240" label="职位名称" ></odin:textEdit>
				</tr>
				<tr>
				<td>
				<a  style="font-size: 13px;">职位分工</a>
				<odin:textarea    cols="200"  property="zwfg"  style="width: 100%;height:96px; border:1px solid;border-color:#b5b8c8;">
				</odin:textarea>
				</td>
				</tr>
				
				<tr class='tr2'>
			    <td>
				<a  style="font-size: 13px;">资格条件</a>
				<odin:textarea    cols="200"  property="zgtj" style="width: 100%;height:96px; border:1px solid;border-color:#b5b8c8;" ></odin:textarea>
				</td>
				</tr>
				<tr class='tr2'>
				<odin:textEdit property="pbzk"  width="100" label="配备状况" readonly="true"><a><img  src="image/icon021a2.gif" style=";cursor:pointer;" onclick="tjry()"> </a></odin:textEdit>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
		<td valign="top" class='tr4' width="60%" >
			<odin:groupBox title="职数与编制信息">
			<div id='div22' style="text-align: center;">
						<table>
							<tr>
								<td colspan="2">
									<table>
										<tr>
											<td rowspan="3" style="font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年龄</td>
											<td style="font-size: 13px;"><select id="nl">
													<option value=">=">>=</option>
													<option value="<="><=</option>
											</select> <input type="text" style="width: 50px;" id="nlval" value="" />岁</td>
										</tr>
									</table>
							</tr>

							<tr>
								<odin:select2 property="xb" label="性别" readonly="false"
									codeType="GB2261" required="false"></odin:select2>
							</tr>

							<tr>
								<tags:PublicTextIconEdit property="xl" label="学历"
									required="false" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
							</tr>

							<tr>
								<tags:PublicTextIconEdit property="zy" label="专业"
									required="false" codetype="GB8561" readonly="true"></tags:PublicTextIconEdit>
							</tr>

							<tr>
								<odin:textEdit property="zc" width="160" label="职称"></odin:textEdit>
							</tr>

							<tr>
								<tags:PublicTextIconEdit property="nlyq" label="能力要求"
									required="false" codetype="NLJG" readonly="true"></tags:PublicTextIconEdit>
							</tr>

							<tr>
								<tags:PublicTextIconEdit property="dp" label="党派"
									required="false" codetype="GB4762" readonly="true"></tags:PublicTextIconEdit>
							</tr>

							<tr>
								<tags:PublicTextIconEdit property="dy" label="地域"
									required="false" codetype="DYJG" readonly="true"></tags:PublicTextIconEdit>
							</tr>


							<tr>
								<td colspan="2">
									<table border="1" cellpadding="0" cellspacing="0"
										text-align:center;margin:0
										auto;border-bottom:0px;" bordercolor="#C0C0C0">
										<tr>
											<td rowspan="3" style="font-size: 13px;">工作经历</td>
											<td style="font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从事分管<input
												type="text" style="width: 150px;" id="csfg1" value="" />满<input
												type="text" style="width: 50px;" id="csfg2" value="" />年
											</td>
										</tr>

										<tr>
											<td>
												<table>
													<tr>
														<td rowspan="2" style="font-size: 13px;"><select
															id="levelSelect">
																<option value="0">并且</option>
																<option value="1">或者</option>
														</select></td>
														<td style="font-size: 13px;">级别层次<input type="text"
															style="width: 150px;" id="jbcc1" value="" />满<input
															type="text" style="width: 50px;" id="jbcc2" value="" />年
														</td>
													</tr>
													<tr>
														<td style="font-size: 13px;">职务层次<input type="text"
															style="width: 150px;" id="zwcc1" value="" />满<input
															type="text" style="width: 50px;" id="zwcc2" value="" />年
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td style="font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别<input
												type="text" style="width: 150px;" id="lb1" value="" />满<input
												type="text" style="width: 50px;" id="lb2" value="" />年
											</td>
										</tr>
									</table>

								</td>
							</tr>
						</table>
					</div>
			</odin:groupBox>
		</td>
	</tr>
</table>
<table style="width:100%" border = "0">
<tr>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;(1)		
	</td>
	<td style="text-align:left;">
	<select id="select1">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(2)		
	</td>
	<td style="text-align:left;">
	<select id="select2">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(3)		
	</td>
	<td style="text-align:left;">
	<select id="select3">
	<option value="0" selected="selected">请选择</option>
	 <option value="0">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	</tr>
	
	<tr>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;(4)
	</td>
	<td style="text-align:left;">
	<select id="select4">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(5)		
	</td>
	<td style="text-align:left;">
	<select id="select5">
	<option value="0" selected="selected">请选择</option>
	<option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(6)		
	</td>
	<td style="text-align:left;">
	<select id="select6">
	<option value="0" selected="selected">请选择</option>
	<option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	<span style="font-size:13px;color:#F00">(根据所选项筛选条件优先级)</span>
	</td>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	
	
	</tr>
	
	
	<tr>
	<td style="text-align:left;">
	&nbsp;&nbsp;&nbsp;(7)
	</td>
	<td style="text-align:left;">
	<select id="select7">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(8)		
	</td>
	<td style="text-align:left;">
	<select id="select8">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	<td style="text-align:left;">
	(9)		
	</td>
	<td style="text-align:left;">
	<select id="select9">
	 <option value="0" selected="selected">请选择</option>
	 <option value="1">年龄</option>
	 <option value="2">性别</option>
	 <option value="3">学历</option>
	 <option value="4">专业</option>
	 <option value="5">职称</option>
	 <option value="6">能力要求</option>
	 <option value="7">党派</option>
	 <option value="8">地域</option>
	 <option value="9">工作经历</option>
	</select>
	</td>
	</tr>
</table>
<table style="width:100%;margin-top: 40px" border = "0">
<tr>

	<td style="text-align:center;"> 
 		<input class="yellowbutton" type="button" value="保&nbsp;存" onclick="save();"/> 
 		<input class="yellowbutton" type="button" value="关&nbsp;闭" onclick="closeAll()"/> 
	</td> 
	
	</tr>
</table>
<odin:hidden property="data"/>
<odin:hidden property="gid"/>
<odin:hidden property="id"/>
</div>
