<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#tag_container {
	/* position: relative;
	width: 100%;
	height: 450px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px; */
	margin: 10px;
}

#tag_container .tag_div {
	/* position: relative;
	width: 30%;
	height: 100%;
	float: left;
	margin-left: 2%; */
	
}

#tag_info_div {
	position: relative;
	width: 100%;
}

#tag_info_div #a0196z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}

.x-form-item{
display: inline;
}

.x-form-check-wrap{
height: 13px;

}
.x-form-item{
	font-size: 13px;
}
.x-fieldset{
	padding:3px;
	margin-bottom:4px
}
#bottom_div{
position:fixed;
top:200px;
right:20px;
}

.btn-primary{
width: 80px;
height: 50px;
font-size: 20px;
}
</style>

<odin:hidden property="a0196s"/>
<div id="bottom_div">
	<div align="right">
		<%-- <odin:button text="保&nbsp;&nbsp;存" property="save" /> --%>
		<button type='button' class="btn-primary" onclick="save()" >保存</button>
	</div>		
</div> 
<div id="tag_container">

<%-- <table>
<tr>
	<td>
		<odin:checkbox property="qx01" label="<span style='font-weight: bold;'>党务</span>" onclick="fullContent2(this,'01,02,03,04,05,06,07,08','党建,纪检监察,组织人事,宣传思想意识形态,统战,政法（政法委）,群团,其他党务工作')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="dw" >
			<odin:checkbox property="tag01" label="党建" onclick="fullContent(this,'01','党建')"></odin:checkbox>
			<odin:checkbox property="tag02" label="纪检监察" onclick="fullContent(this,'02','纪检监察')"></odin:checkbox>
			<odin:checkbox property="tag03" label="组织人事" onclick="fullContent(this,'03','组织人事')"></odin:checkbox>
			<odin:checkbox property="tag04" label="宣传思想意识形态" onclick="fullContent(this,'04','宣传思想意识形态')"></odin:checkbox>
			<odin:checkbox property="tag05" label="统战" onclick="fullContent(this,'05','统战')"></odin:checkbox>
			<odin:checkbox property="tag06" label="政法（政法委）" onclick="fullContent(this,'06','政法（政法委）')"></odin:checkbox>
			<odin:checkbox property="tag07" label="群团" onclick="fullContent(this,'07','群团')"></odin:checkbox>
			<odin:checkbox property="tag08" label="其他党务工作" onclick="fullContent(this,'08','其他党务工作')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx02" label="<span style='font-weight: bold;'>经济</span>" onclick="fullContent2(this,'09,10,11,12,13,14,15,16,17,18,19,19C,19D','重大项目管理,经济体制改革,区域经济,自贸区建设,数字经济,宏观经济,工业经济,农业农村经济,财政税收审计统计,金融,商贸流通,招商引资,国资管理')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="jingji" >	
			<odin:checkbox property="tag09" label="重大项目管理" onclick="fullContent(this,'09','重大项目管理')"></odin:checkbox>
			<odin:checkbox property="tag10" label="经济体制改革" onclick="fullContent(this,'10','经济体制改革')"></odin:checkbox>
			<odin:checkbox property="tag11" label="区域经济" onclick="fullContent(this,'11','区域经济')"></odin:checkbox>
			
			<odin:checkbox property="tag12" label="自贸区建设" onclick="fullContent(this,'12','自贸区建设')"></odin:checkbox>
			<odin:checkbox property="tag13" label="数字经济" onclick="fullContent(this,'13','数字经济')"></odin:checkbox>
			<odin:checkbox property="tag14" label="宏观经济" onclick="fullContent(this,'14','宏观经济')"></odin:checkbox>
			<odin:checkbox property="tag15" label="工业经济" onclick="fullContent(this,'15','工业经济')"></odin:checkbox>
			<odin:checkbox property="tag16" label="农业农村经济" onclick="fullContent(this,'16','农业农村经济')"></odin:checkbox>
			<odin:checkbox property="tag17" label="财政税收审计统计" onclick="fullContent(this,'17','财政税收审计统计')"></odin:checkbox>
			<odin:checkbox property="tag18" label="金融" onclick="fullContent(this,'18','金融')"></odin:checkbox>
			<odin:checkbox property="tag19" label="商贸流通" onclick="fullContent(this,'19','商贸流通')"></odin:checkbox>
			<odin:checkbox property="tag19C" label="招商引资" onclick="fullContent(this,'19C','招商引资')"></odin:checkbox>
			<odin:checkbox property="tag19D" label="国资管理" onclick="fullContent(this,'19D','国资管理')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx03" label="<span style='font-weight: bold;'>规划与建设</span>" onclick="fullContent2(this,'20,21,22,23,24','国土空间规划,城乡建设,城建规划,交通运输,工程建设和技术管理')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="ghyjs" >
			<odin:checkbox property="tag20" label="国土空间规划" onclick="fullContent(this,'20','国土空间规划')"></odin:checkbox>
			<odin:checkbox property="tag21" label="城乡建设" onclick="fullContent(this,'21','城乡建设')"></odin:checkbox>
			<odin:checkbox property="tag22" label="城建规划" onclick="fullContent(this,'22','城建规划')"></odin:checkbox>
			<odin:checkbox property="tag23" label="交通运输" onclick="fullContent(this,'23','交通运输')"></odin:checkbox>
			<odin:checkbox property="tag24" label="工程建设和技术管理" onclick="fullContent(this,'24','工程建设和技术管理')"></odin:checkbox>
		</odin:groupBox>
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx04" label="<span style='font-weight: bold;'>科技文化卫生</span>" onclick="fullContent2(this,'25,26,28,29,30,30E','科技,网络安全和信息,文化旅游,体育,卫生健康,新闻出版与媒体艺术')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>
		<odin:groupBox title="" property="kjwhws" >	
			<odin:checkbox property="tag25" label="科技" onclick="fullContent(this,'25','科技')"></odin:checkbox>
			<odin:checkbox property="tag26" label="网络安全和信息" onclick="fullContent(this,'26','网络安全和信息')"></odin:checkbox>		
			<odin:checkbox property="tag28" label="文化旅游" onclick="fullContent(this,'28','文化旅游')"></odin:checkbox>
			<odin:checkbox property="tag29" label="体育" onclick="fullContent(this,'29','体育')"></odin:checkbox>
			<odin:checkbox property="tag30" label="卫生健康" onclick="fullContent(this,'30','卫生健康')"></odin:checkbox>
			<odin:checkbox property="tag30E" label="新闻出版与媒体艺术" onclick="fullContent(this,'30E','新闻出版与媒体艺术')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx05" label="<span style='font-weight: bold;'>教育</span>" onclick="fullContent2(this,'30F,30G,30H,31,32,32F,32G,32H,32I,33,34,35,36','义务段学校管理,高中学校管理,教育管理,高校学生管理,高校党务管理,高校党建,高校纪检,高校组织人事,高校宣传思想意识形态,高校科研,高校教学,高校人事,高校行政管理')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="jiaoyu" >		
			<odin:checkbox property="tag30F" label="义务段学校管理" onclick="fullContent(this,'30F','义务段学校管理')"></odin:checkbox>
			<odin:checkbox property="tag30G" label="高中学校管理" onclick="fullContent(this,'30G','高中学校管理')"></odin:checkbox>
			<odin:checkbox property="tag30H" label="教育管理" onclick="fullContent(this,'30H','教育管理')"></odin:checkbox>
			<odin:checkbox property="tag31" label="高校学生管理" onclick="fullContent(this,'31','高校学生管理')"></odin:checkbox>
			<odin:checkbox property="tag32" label="高校党务管理" onclick="fullContent(this,'32','高校党务管理')"></odin:checkbox>
			<odin:checkbox property="tag32F" label="高校党建" onclick="fullContent(this,'32F','高校党建')"></odin:checkbox>
			<odin:checkbox property="tag32G" label="高校纪检" onclick="fullContent(this,'32G','高校纪检')"></odin:checkbox>
			<odin:checkbox property="tag32H" label="高校组织人事" onclick="fullContent(this,'32H','高校组织人事')"></odin:checkbox>
			<odin:checkbox property="tag32I" label="高校宣传思想意识形态" onclick="fullContent(this,'32I','高校宣传思想意识形态')"></odin:checkbox>
			<odin:checkbox property="tag33" label="高校科研" onclick="fullContent(this,'33','高校科研')"></odin:checkbox>
			<odin:checkbox property="tag34" label="高校教学" onclick="fullContent(this,'34','高校教学')"></odin:checkbox>
			<odin:checkbox property="tag35" label="高校人事" onclick="fullContent(this,'35','高校人事')"></odin:checkbox>
			<odin:checkbox property="tag36" label="高校行政管理" onclick="fullContent(this,'36','高校行政管理')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx06" label="<span style='font-weight: bold;'>农业与资源环境</span>" onclick="fullContent2(this,'37,37F,38,39,40,41,42','农业农村,林业,水利,自然资源管理,测绘信息,粮食物资,生态环境保护')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="nyyzyhj" >			
			<odin:checkbox property="tag37" label="农业农村" onclick="fullContent(this,'37','农业农村')"></odin:checkbox>
			<odin:checkbox property="tag37F" label="林业" onclick="fullContent(this,'37F','林业')"></odin:checkbox>
			<odin:checkbox property="tag38" label="水利" onclick="fullContent(this,'38','水利')"></odin:checkbox>
	 		<odin:checkbox property="tag39" label="自然资源管理" onclick="fullContent(this,'39','自然资源管理')"></odin:checkbox>
			<odin:checkbox property="tag40" label="测绘信息" onclick="fullContent(this,'40','测绘信息')"></odin:checkbox>
			<odin:checkbox property="tag41" label="粮食物资" onclick="fullContent(this,'41','粮食物资')"></odin:checkbox>
			<odin:checkbox property="tag42" label="生态环境保护" onclick="fullContent(this,'42','生态环境保护')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx07" label="<span style='font-weight: bold;'>社会与法制</span>" onclick="fullContent2(this,'43,44,45,46,47,48,49,50,51,52','立法,司法行政（含政府法制）,公安,检察,审判,国家安全,安全生产和应急管理,综合执法与市场监管,社会治理,信访')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>
		<odin:groupBox title="" property="shyfz" >			
			<odin:checkbox property="tag43" label="立法" onclick="fullContent(this,'43','立法')"></odin:checkbox>
			<odin:checkbox property="tag44" label="司法行政（含政府法制）" onclick="fullContent(this,'44','司法行政（含政府法制）')"></odin:checkbox> 
			<odin:checkbox property="tag45" label="公安" onclick="fullContent(this,'45','公安')"></odin:checkbox> 
			<odin:checkbox property="tag46" label="检察" onclick="fullContent(this,'46','检察')"></odin:checkbox> 
			<odin:checkbox property="tag47" label="审判" onclick="fullContent(this,'47','审判')"></odin:checkbox> 
			<odin:checkbox property="tag48" label="国家安全" onclick="fullContent(this,'48','国家安全')"></odin:checkbox> 
			<odin:checkbox property="tag49" label="安全生产和应急管理" onclick="fullContent(this,'49','安全生产和应急管理')"></odin:checkbox> 
			<odin:checkbox property="tag50" label="综合执法与市场监管" onclick="fullContent(this,'50','综合执法与市场监管')"></odin:checkbox>
			<odin:checkbox property="tag51" label="社会治理" onclick="fullContent(this,'51','社会治理')"></odin:checkbox>
			<odin:checkbox property="tag52" label="信访" onclick="fullContent(this,'52','信访')"></odin:checkbox>
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx08" label="<span style='font-weight: bold;'>民生保障</span>" onclick="fullContent2(this,'53,54,55,55I,55J','民政,社会保障,退役军人事务,帮扶和扶贫,抗灾救灾')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="msbz" >		
			<odin:checkbox property="tag53" label="民政" onclick="fullContent(this,'53','民政')"></odin:checkbox>
			<odin:checkbox property="tag54" label="社会保障" onclick="fullContent(this,'54','社会保障')"></odin:checkbox>
			<odin:checkbox property="tag55" label="退役军人事务" onclick="fullContent(this,'55','退役军人事务')"></odin:checkbox>
			<odin:checkbox property="tag55I" label="帮扶和扶贫" onclick="fullContent(this,'55I','帮扶和扶贫')"></odin:checkbox>
			<odin:checkbox property="tag55J" label="抗灾救灾" onclick="fullContent(this,'55J','抗灾救灾')"></odin:checkbox>
			
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx09" label="<span style='font-weight: bold;'>企业管理</span>" onclick="fullContent2(this,'57,57J,57K,57L,58,59,60,61,62,63,64,64J,64K,64L,65','企业经营管理,酒店经营管理,物业经营管理,景区经营管理,企业生产运行、工程项目,企业市场营销,企业资本运作和投融资管理,企业专业技术,企业国际业务,企业风险管控,企业党务工作,企业党建,企业纪检监察,企业组织人事,上市公司管理运营')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="qygl" >		
			<odin:checkbox property="tag57" label="企业经营管理" onclick="fullContent(this,'57','企业经营管理')"></odin:checkbox>
			<odin:checkbox property="tag57J" label="酒店经营管理" onclick="fullContent(this,'57J','酒店经营管理')"></odin:checkbox>
			<odin:checkbox property="tag57K" label="物业经营管理" onclick="fullContent(this,'57K','物业经营管理')"></odin:checkbox>
			<odin:checkbox property="tag57L" label="景区经营管理" onclick="fullContent(this,'57L','景区经营管理')"></odin:checkbox>
			<odin:checkbox property="tag58" label="企业生产运行、工程项目" onclick="fullContent(this,'58','企业生产运行、工程项目')"></odin:checkbox>
			<odin:checkbox property="tag59" label="企业市场营销" onclick="fullContent(this,'59','企业市场营销')"></odin:checkbox>
			<odin:checkbox property="tag60" label="企业资本运作和投融资管理" onclick="fullContent(this,'60','企业资本运作和投融资管理')"></odin:checkbox>
			<odin:checkbox property="tag61" label="企业专业技术" onclick="fullContent(this,'61','企业专业技术')"></odin:checkbox>
			<odin:checkbox property="tag62" label="企业国际业务" onclick="fullContent(this,'62','企业国际业务')"></odin:checkbox>
			<odin:checkbox property="tag63" label="企业风险管控" onclick="fullContent(this,'63','企业风险管控')"></odin:checkbox>
			<odin:checkbox property="tag64" label="企业党务工作" onclick="fullContent(this,'64','企业党务工作')"></odin:checkbox>
			<odin:checkbox property="tag64J" label="企业党建" onclick="fullContent(this,'64J','企业党建')"></odin:checkbox>
			<odin:checkbox property="tag64K" label="企业纪检监察" onclick="fullContent(this,'64K','企业纪检监察')"></odin:checkbox>
			<odin:checkbox property="tag64L" label="企业组织人事" onclick="fullContent(this,'64L','企业组织人事')"></odin:checkbox>
			<odin:checkbox property="tag65" label="上市公司管理运营" onclick="fullContent(this,'65','上市公司管理运营')"></odin:checkbox>
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx10" label="<span style='font-weight: bold;'>对外交流与军事</span>" onclick="fullContent2(this,'66,67,68','外事港澳台侨及对外交流,国防军事,人防')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="dwjlyjs" >			
			<odin:checkbox property="tag66" label="外事港澳台侨及对外交流" onclick="fullContent(this,'66','外事港澳台侨及对外交流')"></odin:checkbox>
			<odin:checkbox property="tag67" label="国防军事" onclick="fullContent(this,'67','国防军事')"></odin:checkbox>
			<odin:checkbox property="tag68" label="人防" onclick="fullContent(this,'68','人防')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx11" label="<span style='font-weight: bold;'>其他</span>" onclick="fullContent2(this,'69,70,27,56,71,71N,71O,71P,71Q','民族宗教,政策研究,人才工作,档案工作,行政管理,机要保密,机关日常事务（办公室）,综合文字信息,绩效管理')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="qita" >	
			<odin:checkbox property="tag69" label="民族宗教" onclick="fullContent(this,'69','民族宗教')"></odin:checkbox>
			<odin:checkbox property="tag70" label="政策研究" onclick="fullContent(this,'70','政策研究')"></odin:checkbox>
			<odin:checkbox property="tag27" label="人才工作" onclick="fullContent(this,'27','人才工作')"></odin:checkbox>
			<odin:checkbox property="tag56" label="档案工作" onclick="fullContent(this,'56','档案工作')"></odin:checkbox>
			<odin:checkbox property="tag71" label="行政管理" onclick="fullContent(this,'71','行政管理')"></odin:checkbox>
			<odin:checkbox property="tag71N" label="机要保密" onclick="fullContent(this,'71N','机要保密')"></odin:checkbox>
			<odin:checkbox property="tag71O" label="机关日常事务（办公室）" onclick="fullContent(this,'71O','机关日常事务（办公室）')"></odin:checkbox>
			<odin:checkbox property="tag71P" label="综合文字信息" onclick="fullContent(this,'71P','综合文字信息')"></odin:checkbox>
			<odin:checkbox property="tag71Q" label="绩效管理" onclick="fullContent(this,'71Q','绩效管理')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
</table> --%>
<odin:checkbox property="tag01" label="政法工作" onclick="fullContent(this,'01','政法工作')"></odin:checkbox>
		<odin:checkbox property="tag02" label="农业农村" onclick="fullContent(this,'02','农业农村')"></odin:checkbox>
		<odin:checkbox property="tag03" label="纪检监察" onclick="fullContent(this,'03','纪检监察')"></odin:checkbox>
		<odin:checkbox property="tag04" label="组织人事" onclick="fullContent(this,'04','组织人事')"></odin:checkbox>
		<odin:checkbox property="tag05" label="意识形态" onclick="fullContent(this,'05','意识形态')"></odin:checkbox>
		<odin:checkbox property="tag06" label="统战工作" onclick="fullContent(this,'06','统战工作')"></odin:checkbox>
		<odin:checkbox property="tag07" label="发展改革" onclick="fullContent(this,'07','发展改革')"></odin:checkbox>
		<odin:checkbox property="tag08" label="工信科技" onclick="fullContent(this,'08','工信科技')"></odin:checkbox>
		<odin:checkbox property="tag09" label="财政金融" onclick="fullContent(this,'09','财政金融')"></odin:checkbox>
		<odin:checkbox property="tag10" label="国土规划" onclick="fullContent(this,'10','国土规划')"></odin:checkbox>
		<odin:checkbox property="tag11" label="城建城管" onclick="fullContent(this,'11','城建城管')"></odin:checkbox>
		<odin:checkbox property="tag12" label="教育卫生" onclick="fullContent(this,'12','教育卫生')"></odin:checkbox>
		<odin:checkbox property="tag13" label="商贸旅游" onclick="fullContent(this,'13','商贸旅游')"></odin:checkbox>
		<odin:checkbox property="tag14" label="生态环保" onclick="fullContent(this,'14','生态环保')"></odin:checkbox>
		<odin:checkbox property="tag15" label="企业管理" onclick="fullContent(this,'15','企业管理')"></odin:checkbox>
		<odin:checkbox property="tag16" label="民生社保" onclick="fullContent(this,'16','民生社保')"></odin:checkbox>
		<odin:checkbox property="tag17" label="群团建设" onclick="fullContent(this,'17','群团建设')"></odin:checkbox>
</div>
<div id="tag_info_div">
	<textarea rows="3" cols="113" id="a0196z" name="a0196z"></textarea>
</div>

<script type="text/javascript">
//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0196z = document.getElementById("a0196z").value;
	var a0196s = document.getElementById("a0196s").value;
	if($(check).is(':checked')) {
		if( a0196z == null || a0196z == '' ){
			a0196z = valuename;
		}else{
			a0196z = a0196z + "," + valuename;
		}	
		if( a0196s == null || a0196s == '' ){
			a0196s = value;
		}else{
			a0196s = a0196s  + "," + value;
		}			
	}else{
		a0196z = a0196z.replace(valuename+',', '').replace(valuename, '');
		a0196s = a0196s.replace(value+',', '').replace(value, '');
		if(a0196z!=null && a0196z!='' && a0196z.charAt(a0196z.length-1) == ","){
			a0196z=a0196z.substr(0,a0196z.length-1);
			a0196s=a0196s.substr(0,a0196s.length-1);
		}
	}
	document.getElementById("a0196z").value = a0196z;
	document.getElementById("a0196s").value = a0196s;
}

function fullContent2(check,value,valuename){
	var type;
	if($(check).is(':checked')) {
		type='1';//全选
	}else{
		type='2';//全不选
	}
	radow.doEvent("fullAll",type+'##'+value+'##'+valuename);
}

function save(){
	radow.doEvent("save.onclick");
}

</script>
