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
		
		<odin:checkbox property="tag01" label="��������" onclick="fullContent(this,'01','��������')"></odin:checkbox>
		<odin:checkbox property="tag02" label="ũҵũ��" onclick="fullContent(this,'02','ũҵũ��')"></odin:checkbox>
		<odin:checkbox property="tag03" label="�ͼ���" onclick="fullContent(this,'03','�ͼ���')"></odin:checkbox>
		<odin:checkbox property="tag04" label="��֯����" onclick="fullContent(this,'04','��֯����')"></odin:checkbox>
		<odin:checkbox property="tag05" label="��ʶ��̬" onclick="fullContent(this,'05','��ʶ��̬')"></odin:checkbox>
		<odin:checkbox property="tag06" label="ͳս����" onclick="fullContent(this,'06','ͳս����')"></odin:checkbox>
		<odin:checkbox property="tag07" label="��չ�ĸ�" onclick="fullContent(this,'07','��չ�ĸ�')"></odin:checkbox>
		<odin:checkbox property="tag08" label="���ſƼ�" onclick="fullContent(this,'08','���ſƼ�')"></odin:checkbox>
		<odin:checkbox property="tag09" label="��������" onclick="fullContent(this,'09','��������')"></odin:checkbox>
		<odin:checkbox property="tag10" label="�����滮" onclick="fullContent(this,'10','�����滮')"></odin:checkbox>
		<odin:checkbox property="tag11" label="�ǽ��ǹ�" onclick="fullContent(this,'11','�ǽ��ǹ�')"></odin:checkbox>
		<odin:checkbox property="tag12" label="��������" onclick="fullContent(this,'12','��������')"></odin:checkbox>
		<odin:checkbox property="tag13" label="��ó����" onclick="fullContent(this,'13','��ó����')"></odin:checkbox>
		<odin:checkbox property="tag14" label="��̬����" onclick="fullContent(this,'14','��̬����')"></odin:checkbox>
		<odin:checkbox property="tag15" label="��ҵ����" onclick="fullContent(this,'15','��ҵ����')"></odin:checkbox>
		<odin:checkbox property="tag16" label="�����籣" onclick="fullContent(this,'16','�����籣')"></odin:checkbox>
		<odin:checkbox property="tag17" label="Ⱥ�Ž���" onclick="fullContent(this,'17','Ⱥ�Ž���')"></odin:checkbox>
	
	

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
//���������ʾѡ�б�ǩ
function fullContent(check,value,valuename){
	var a0197z = document.getElementById("a0197z").value;
	var a0197s = document.getElementById("a0197s").value;
	if($(check).is(':checked')) {
		if( a0197z == null || a0197z == '' ){
			a0197z = valuename;
		}else{
			a0197z = a0197z + "��" + valuename;
		}	
		if( a0197s == null || a0197s == '' ){
			a0197s = value;
		}else{
			a0197s = a0197s  + "��" + value;
		}			
	}else{
		a0197z = a0197z.replace(valuename+'��', '').replace(valuename, '');
		a0197s = a0197s.replace(value+'��', '').replace(value, '');
	}
	document.getElementById("a0197z").value = a0197z;
	document.getElementById("a0197s").value = a0197s;
}


</script>
