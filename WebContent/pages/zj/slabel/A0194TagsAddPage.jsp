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

#tag_info_div #a0194z {
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
</style>

<odin:hidden property="a0194s"/>
<div id="tag_container">
	<odin:groupBox title="党务" property="dw" >
		<odin:checkbox property="tag01" label="党建" onclick="fullContent(this,'01','党建')"></odin:checkbox>
		<odin:checkbox property="tag02" label="纪检监察" onclick="fullContent(this,'02','纪检监察')"></odin:checkbox>
		<odin:checkbox property="tag03" label="组织人事" onclick="fullContent(this,'03','组织人事')"></odin:checkbox>
		<odin:checkbox property="tag04" label="宣传思想意识形态" onclick="fullContent(this,'04','宣传思想意识形态')"></odin:checkbox>
		<odin:checkbox property="tag05" label="统战" onclick="fullContent(this,'05','统战')"></odin:checkbox>
		<odin:checkbox property="tag06" label="政法（政法委）" onclick="fullContent(this,'06','政法（政法委）')"></odin:checkbox>
		<odin:checkbox property="tag07" label="群团" onclick="fullContent(this,'07','群团')"></odin:checkbox>
		<odin:checkbox property="tag08" label="其他党务工作" onclick="fullContent(this,'08','其他党务工作')"></odin:checkbox>
	</odin:groupBox>
	
	<odin:groupBox title="经济" property="jingji" >	
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
		
		
	<odin:groupBox title="规划与建设" property="ghyjs" >
		<odin:checkbox property="tag20" label="国土空间规划" onclick="fullContent(this,'20','国土空间规划')"></odin:checkbox>
		<odin:checkbox property="tag21" label="城乡建设" onclick="fullContent(this,'21','城乡建设')"></odin:checkbox>
		<odin:checkbox property="tag22" label="城建规划" onclick="fullContent(this,'22','城建规划')"></odin:checkbox>
		<odin:checkbox property="tag23" label="交通运输" onclick="fullContent(this,'23','交通运输')"></odin:checkbox>
		<odin:checkbox property="tag24" label="工程建设和技术管理" onclick="fullContent(this,'24','工程建设和技术管理')"></odin:checkbox>
	</odin:groupBox>	
		
		
	<odin:groupBox title="科技文化卫生" property="kjwhws" >	
		<odin:checkbox property="tag25" label="科技" onclick="fullContent(this,'25','科技')"></odin:checkbox>
		<odin:checkbox property="tag26" label="网络安全和信息" onclick="fullContent(this,'26','网络安全和信息')"></odin:checkbox>		
		
		<odin:checkbox property="tag28" label="文化旅游" onclick="fullContent(this,'28','文化旅游')"></odin:checkbox>
		<odin:checkbox property="tag29" label="体育" onclick="fullContent(this,'29','体育')"></odin:checkbox>
		<odin:checkbox property="tag30" label="卫生健康" onclick="fullContent(this,'30','卫生健康')"></odin:checkbox>
		<odin:checkbox property="tag30E" label="新闻出版与媒体艺术" onclick="fullContent(this,'30E','新闻出版与媒体艺术')"></odin:checkbox>
	</odin:groupBox>	
		
	<odin:groupBox title="教育" property="jiaoyu" >		
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
	
	<odin:groupBox title="农业与资源环境" property="nyyzyhj" >			
		<odin:checkbox property="tag37" label="农业农村" onclick="fullContent(this,'37','农业农村')"></odin:checkbox>
		<odin:checkbox property="tag37F" label="林业" onclick="fullContent(this,'37F','林业')"></odin:checkbox>
		<odin:checkbox property="tag38" label="水利" onclick="fullContent(this,'38','水利')"></odin:checkbox>
 		<odin:checkbox property="tag39" label="自然资源管理" onclick="fullContent(this,'39','自然资源管理')"></odin:checkbox>
		<odin:checkbox property="tag40" label="测绘信息" onclick="fullContent(this,'40','测绘信息')"></odin:checkbox>
		<odin:checkbox property="tag41" label="粮食物资" onclick="fullContent(this,'41','粮食物资')"></odin:checkbox>
		<odin:checkbox property="tag42" label="生态环境保护" onclick="fullContent(this,'42','生态环境保护')"></odin:checkbox>
	</odin:groupBox>	
	
	<odin:groupBox title="社会与法制" property="shyfz" >			
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
		
	<odin:groupBox title="民生保障" property="msbz" >		
		<odin:checkbox property="tag53" label="民政" onclick="fullContent(this,'53','民政')"></odin:checkbox>
		<odin:checkbox property="tag54" label="社会保障" onclick="fullContent(this,'54','社会保障')"></odin:checkbox>
		<odin:checkbox property="tag55" label="退役军人事务" onclick="fullContent(this,'55','退役军人事务')"></odin:checkbox>
		<odin:checkbox property="tag55I" label="帮扶和扶贫" onclick="fullContent(this,'55I','帮扶和扶贫')"></odin:checkbox>
		<odin:checkbox property="tag55J" label="抗灾救灾" onclick="fullContent(this,'55J','抗灾救灾')"></odin:checkbox>
		
	</odin:groupBox>		
		
	<odin:groupBox title="企业管理" property="qygl" >		
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
	
	<odin:groupBox title="对外交流与军事" property="dwjlyjs" >			
		<odin:checkbox property="tag66" label="外事港澳台侨及对外交流" onclick="fullContent(this,'66','外事港澳台侨及对外交流')"></odin:checkbox>
		<odin:checkbox property="tag67" label="国防军事" onclick="fullContent(this,'67','国防军事')"></odin:checkbox>
		<odin:checkbox property="tag68" label="人防" onclick="fullContent(this,'68','人防')"></odin:checkbox>
	</odin:groupBox>	
	<odin:groupBox title="其他" property="qita" >	
		<odin:checkbox property="tag69" label="民族宗教" onclick="fullContent(this,'69','民族宗教')"></odin:checkbox>
		<odin:checkbox property="tag70" label="政策研究" onclick="fullContent(this,'70','政策研究')"></odin:checkbox>
		<odin:checkbox property="tag27" label="人才工作" onclick="fullContent(this,'27','人才工作')"></odin:checkbox>
		<odin:checkbox property="tag56" label="档案工作" onclick="fullContent(this,'56','档案工作')"></odin:checkbox>
		<odin:checkbox property="tag71" label="行政管理" onclick="fullContent(this,'71','行政管理')"></odin:checkbox>
		<odin:checkbox property="tag71N" label="机要保密" onclick="fullContent(this,'71N','机要保密')"></odin:checkbox>
		<odin:checkbox property="tag710" label="机关日常事务（办公室）" onclick="fullContent(this,'710','机关日常事务（办公室）')"></odin:checkbox>
		<odin:checkbox property="tag71P" label="综合文字信息" onclick="fullContent(this,'71P','综合文字信息')"></odin:checkbox>
		<odin:checkbox property="tag71Q" label="绩效管理" onclick="fullContent(this,'71Q','绩效管理')"></odin:checkbox>
	</odin:groupBox>	

</div>
<div id="tag_info_div">
	<textarea rows="3" cols="113" id="a0194z" name="a0194z"></textarea>
</div>
<div id="bottom_div">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div> 
<script type="text/javascript">
//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0194z = document.getElementById("a0194z").value;
	var a0194s = document.getElementById("a0194s").value;
	if($(check).is(':checked')) {
		if( a0194z == null || a0194z == '' ){
			a0194z = valuename;
		}else{
			a0194z = a0194z + "；" + valuename;
		}	
		if( a0194s == null || a0194s == '' ){
			a0194s = value;
		}else{
			a0194s = a0194s  + "；" + value;
		}			
	}else{
		a0194z = a0194z.replace(valuename+'；', '').replace(valuename, '');
		a0194s = a0194s.replace(value+'；', '').replace(value, '');
	}
	document.getElementById("a0194z").value = a0194z;
	document.getElementById("a0194s").value = a0194s;
}


</script>
