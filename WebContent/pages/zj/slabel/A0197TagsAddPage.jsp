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

#tag_info_div #a0197z {
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

<odin:hidden property="a0197s"/>
<div id="tag_container">
		
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
	<textarea rows="3" cols="113" id="a0197z" name="a0197z"></textarea>
</div>
<table align="center" width="96%">	
			<td align="center">
				<img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('save')">
			</td>
</table>
<script type="text/javascript">
//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0197z = document.getElementById("a0197z").value;
	var a0197s = document.getElementById("a0197s").value;
	if($(check).is(':checked')) {
		if( a0197z == null || a0197z == '' ){
			a0197z = valuename;
		}else{
			a0197z = a0197z + "；" + valuename;
		}	
		if( a0197s == null || a0197s == '' ){
			a0197s = value;
		}else{
			a0197s = a0197s  + "；" + value;
		}			
	}else{
		a0197z = a0197z.replace(valuename+'；', '').replace(valuename, '');
		a0197s = a0197s.replace(value+'；', '').replace(value, '');
	}
	document.getElementById("a0197z").value = a0197z;
	document.getElementById("a0197s").value = a0197s;
}


</script>
