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
		<%-- <odin:button text="��&nbsp;&nbsp;��" property="save" /> --%>
		<button type='button' class="btn-primary" onclick="save()" >����</button>
	</div>		
</div> 
<div id="tag_container">

<%-- <table>
<tr>
	<td>
		<odin:checkbox property="qx01" label="<span style='font-weight: bold;'>����</span>" onclick="fullContent2(this,'01,02,03,04,05,06,07,08','����,�ͼ���,��֯����,����˼����ʶ��̬,ͳս,����������ί��,Ⱥ��,����������')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="dw" >
			<odin:checkbox property="tag01" label="����" onclick="fullContent(this,'01','����')"></odin:checkbox>
			<odin:checkbox property="tag02" label="�ͼ���" onclick="fullContent(this,'02','�ͼ���')"></odin:checkbox>
			<odin:checkbox property="tag03" label="��֯����" onclick="fullContent(this,'03','��֯����')"></odin:checkbox>
			<odin:checkbox property="tag04" label="����˼����ʶ��̬" onclick="fullContent(this,'04','����˼����ʶ��̬')"></odin:checkbox>
			<odin:checkbox property="tag05" label="ͳս" onclick="fullContent(this,'05','ͳս')"></odin:checkbox>
			<odin:checkbox property="tag06" label="����������ί��" onclick="fullContent(this,'06','����������ί��')"></odin:checkbox>
			<odin:checkbox property="tag07" label="Ⱥ��" onclick="fullContent(this,'07','Ⱥ��')"></odin:checkbox>
			<odin:checkbox property="tag08" label="����������" onclick="fullContent(this,'08','����������')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx02" label="<span style='font-weight: bold;'>����</span>" onclick="fullContent2(this,'09,10,11,12,13,14,15,16,17,18,19,19C,19D','�ش���Ŀ����,�������Ƹĸ�,���򾭼�,��ó������,���־���,��۾���,��ҵ����,ũҵũ�徭��,����˰�����ͳ��,����,��ó��ͨ,��������,���ʹ���')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="jingji" >	
			<odin:checkbox property="tag09" label="�ش���Ŀ����" onclick="fullContent(this,'09','�ش���Ŀ����')"></odin:checkbox>
			<odin:checkbox property="tag10" label="�������Ƹĸ�" onclick="fullContent(this,'10','�������Ƹĸ�')"></odin:checkbox>
			<odin:checkbox property="tag11" label="���򾭼�" onclick="fullContent(this,'11','���򾭼�')"></odin:checkbox>
			
			<odin:checkbox property="tag12" label="��ó������" onclick="fullContent(this,'12','��ó������')"></odin:checkbox>
			<odin:checkbox property="tag13" label="���־���" onclick="fullContent(this,'13','���־���')"></odin:checkbox>
			<odin:checkbox property="tag14" label="��۾���" onclick="fullContent(this,'14','��۾���')"></odin:checkbox>
			<odin:checkbox property="tag15" label="��ҵ����" onclick="fullContent(this,'15','��ҵ����')"></odin:checkbox>
			<odin:checkbox property="tag16" label="ũҵũ�徭��" onclick="fullContent(this,'16','ũҵũ�徭��')"></odin:checkbox>
			<odin:checkbox property="tag17" label="����˰�����ͳ��" onclick="fullContent(this,'17','����˰�����ͳ��')"></odin:checkbox>
			<odin:checkbox property="tag18" label="����" onclick="fullContent(this,'18','����')"></odin:checkbox>
			<odin:checkbox property="tag19" label="��ó��ͨ" onclick="fullContent(this,'19','��ó��ͨ')"></odin:checkbox>
			<odin:checkbox property="tag19C" label="��������" onclick="fullContent(this,'19C','��������')"></odin:checkbox>
			<odin:checkbox property="tag19D" label="���ʹ���" onclick="fullContent(this,'19D','���ʹ���')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx03" label="<span style='font-weight: bold;'>�滮�뽨��</span>" onclick="fullContent2(this,'20,21,22,23,24','�����ռ�滮,���罨��,�ǽ��滮,��ͨ����,���̽���ͼ�������')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="ghyjs" >
			<odin:checkbox property="tag20" label="�����ռ�滮" onclick="fullContent(this,'20','�����ռ�滮')"></odin:checkbox>
			<odin:checkbox property="tag21" label="���罨��" onclick="fullContent(this,'21','���罨��')"></odin:checkbox>
			<odin:checkbox property="tag22" label="�ǽ��滮" onclick="fullContent(this,'22','�ǽ��滮')"></odin:checkbox>
			<odin:checkbox property="tag23" label="��ͨ����" onclick="fullContent(this,'23','��ͨ����')"></odin:checkbox>
			<odin:checkbox property="tag24" label="���̽���ͼ�������" onclick="fullContent(this,'24','���̽���ͼ�������')"></odin:checkbox>
		</odin:groupBox>
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx04" label="<span style='font-weight: bold;'>�Ƽ��Ļ�����</span>" onclick="fullContent2(this,'25,26,28,29,30,30E','�Ƽ�,���簲ȫ����Ϣ,�Ļ�����,����,��������,���ų�����ý������')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>
		<odin:groupBox title="" property="kjwhws" >	
			<odin:checkbox property="tag25" label="�Ƽ�" onclick="fullContent(this,'25','�Ƽ�')"></odin:checkbox>
			<odin:checkbox property="tag26" label="���簲ȫ����Ϣ" onclick="fullContent(this,'26','���簲ȫ����Ϣ')"></odin:checkbox>		
			<odin:checkbox property="tag28" label="�Ļ�����" onclick="fullContent(this,'28','�Ļ�����')"></odin:checkbox>
			<odin:checkbox property="tag29" label="����" onclick="fullContent(this,'29','����')"></odin:checkbox>
			<odin:checkbox property="tag30" label="��������" onclick="fullContent(this,'30','��������')"></odin:checkbox>
			<odin:checkbox property="tag30E" label="���ų�����ý������" onclick="fullContent(this,'30E','���ų�����ý������')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx05" label="<span style='font-weight: bold;'>����</span>" onclick="fullContent2(this,'30F,30G,30H,31,32,32F,32G,32H,32I,33,34,35,36','�����ѧУ����,����ѧУ����,��������,��Уѧ������,��У�������,��У����,��У�ͼ�,��У��֯����,��У����˼����ʶ��̬,��У����,��У��ѧ,��У����,��У��������')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="jiaoyu" >		
			<odin:checkbox property="tag30F" label="�����ѧУ����" onclick="fullContent(this,'30F','�����ѧУ����')"></odin:checkbox>
			<odin:checkbox property="tag30G" label="����ѧУ����" onclick="fullContent(this,'30G','����ѧУ����')"></odin:checkbox>
			<odin:checkbox property="tag30H" label="��������" onclick="fullContent(this,'30H','��������')"></odin:checkbox>
			<odin:checkbox property="tag31" label="��Уѧ������" onclick="fullContent(this,'31','��Уѧ������')"></odin:checkbox>
			<odin:checkbox property="tag32" label="��У�������" onclick="fullContent(this,'32','��У�������')"></odin:checkbox>
			<odin:checkbox property="tag32F" label="��У����" onclick="fullContent(this,'32F','��У����')"></odin:checkbox>
			<odin:checkbox property="tag32G" label="��У�ͼ�" onclick="fullContent(this,'32G','��У�ͼ�')"></odin:checkbox>
			<odin:checkbox property="tag32H" label="��У��֯����" onclick="fullContent(this,'32H','��У��֯����')"></odin:checkbox>
			<odin:checkbox property="tag32I" label="��У����˼����ʶ��̬" onclick="fullContent(this,'32I','��У����˼����ʶ��̬')"></odin:checkbox>
			<odin:checkbox property="tag33" label="��У����" onclick="fullContent(this,'33','��У����')"></odin:checkbox>
			<odin:checkbox property="tag34" label="��У��ѧ" onclick="fullContent(this,'34','��У��ѧ')"></odin:checkbox>
			<odin:checkbox property="tag35" label="��У����" onclick="fullContent(this,'35','��У����')"></odin:checkbox>
			<odin:checkbox property="tag36" label="��У��������" onclick="fullContent(this,'36','��У��������')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx06" label="<span style='font-weight: bold;'>ũҵ����Դ����</span>" onclick="fullContent2(this,'37,37F,38,39,40,41,42','ũҵũ��,��ҵ,ˮ��,��Ȼ��Դ����,�����Ϣ,��ʳ����,��̬��������')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="nyyzyhj" >			
			<odin:checkbox property="tag37" label="ũҵũ��" onclick="fullContent(this,'37','ũҵũ��')"></odin:checkbox>
			<odin:checkbox property="tag37F" label="��ҵ" onclick="fullContent(this,'37F','��ҵ')"></odin:checkbox>
			<odin:checkbox property="tag38" label="ˮ��" onclick="fullContent(this,'38','ˮ��')"></odin:checkbox>
	 		<odin:checkbox property="tag39" label="��Ȼ��Դ����" onclick="fullContent(this,'39','��Ȼ��Դ����')"></odin:checkbox>
			<odin:checkbox property="tag40" label="�����Ϣ" onclick="fullContent(this,'40','�����Ϣ')"></odin:checkbox>
			<odin:checkbox property="tag41" label="��ʳ����" onclick="fullContent(this,'41','��ʳ����')"></odin:checkbox>
			<odin:checkbox property="tag42" label="��̬��������" onclick="fullContent(this,'42','��̬��������')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx07" label="<span style='font-weight: bold;'>����뷨��</span>" onclick="fullContent2(this,'43,44,45,46,47,48,49,50,51,52','����,˾�����������������ƣ�,����,���,����,���Ұ�ȫ,��ȫ������Ӧ������,�ۺ�ִ�����г����,�������,�ŷ�')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>
		<odin:groupBox title="" property="shyfz" >			
			<odin:checkbox property="tag43" label="����" onclick="fullContent(this,'43','����')"></odin:checkbox>
			<odin:checkbox property="tag44" label="˾�����������������ƣ�" onclick="fullContent(this,'44','˾�����������������ƣ�')"></odin:checkbox> 
			<odin:checkbox property="tag45" label="����" onclick="fullContent(this,'45','����')"></odin:checkbox> 
			<odin:checkbox property="tag46" label="���" onclick="fullContent(this,'46','���')"></odin:checkbox> 
			<odin:checkbox property="tag47" label="����" onclick="fullContent(this,'47','����')"></odin:checkbox> 
			<odin:checkbox property="tag48" label="���Ұ�ȫ" onclick="fullContent(this,'48','���Ұ�ȫ')"></odin:checkbox> 
			<odin:checkbox property="tag49" label="��ȫ������Ӧ������" onclick="fullContent(this,'49','��ȫ������Ӧ������')"></odin:checkbox> 
			<odin:checkbox property="tag50" label="�ۺ�ִ�����г����" onclick="fullContent(this,'50','�ۺ�ִ�����г����')"></odin:checkbox>
			<odin:checkbox property="tag51" label="�������" onclick="fullContent(this,'51','�������')"></odin:checkbox>
			<odin:checkbox property="tag52" label="�ŷ�" onclick="fullContent(this,'52','�ŷ�')"></odin:checkbox>
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx08" label="<span style='font-weight: bold;'>��������</span>" onclick="fullContent2(this,'53,54,55,55I,55J','����,��ᱣ��,���۾�������,����ͷ�ƶ,���־���')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="msbz" >		
			<odin:checkbox property="tag53" label="����" onclick="fullContent(this,'53','����')"></odin:checkbox>
			<odin:checkbox property="tag54" label="��ᱣ��" onclick="fullContent(this,'54','��ᱣ��')"></odin:checkbox>
			<odin:checkbox property="tag55" label="���۾�������" onclick="fullContent(this,'55','���۾�������')"></odin:checkbox>
			<odin:checkbox property="tag55I" label="����ͷ�ƶ" onclick="fullContent(this,'55I','����ͷ�ƶ')"></odin:checkbox>
			<odin:checkbox property="tag55J" label="���־���" onclick="fullContent(this,'55J','���־���')"></odin:checkbox>
			
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx09" label="<span style='font-weight: bold;'>��ҵ����</span>" onclick="fullContent2(this,'57,57J,57K,57L,58,59,60,61,62,63,64,64J,64K,64L,65','��ҵ��Ӫ����,�Ƶ꾭Ӫ����,��ҵ��Ӫ����,������Ӫ����,��ҵ�������С�������Ŀ,��ҵ�г�Ӫ��,��ҵ�ʱ�������Ͷ���ʹ���,��ҵרҵ����,��ҵ����ҵ��,��ҵ���չܿ�,��ҵ������,��ҵ����,��ҵ�ͼ���,��ҵ��֯����,���й�˾������Ӫ')"></odin:checkbox>
	</td>
</tr>
<tr>	
	<td>	
		<odin:groupBox title="" property="qygl" >		
			<odin:checkbox property="tag57" label="��ҵ��Ӫ����" onclick="fullContent(this,'57','��ҵ��Ӫ����')"></odin:checkbox>
			<odin:checkbox property="tag57J" label="�Ƶ꾭Ӫ����" onclick="fullContent(this,'57J','�Ƶ꾭Ӫ����')"></odin:checkbox>
			<odin:checkbox property="tag57K" label="��ҵ��Ӫ����" onclick="fullContent(this,'57K','��ҵ��Ӫ����')"></odin:checkbox>
			<odin:checkbox property="tag57L" label="������Ӫ����" onclick="fullContent(this,'57L','������Ӫ����')"></odin:checkbox>
			<odin:checkbox property="tag58" label="��ҵ�������С�������Ŀ" onclick="fullContent(this,'58','��ҵ�������С�������Ŀ')"></odin:checkbox>
			<odin:checkbox property="tag59" label="��ҵ�г�Ӫ��" onclick="fullContent(this,'59','��ҵ�г�Ӫ��')"></odin:checkbox>
			<odin:checkbox property="tag60" label="��ҵ�ʱ�������Ͷ���ʹ���" onclick="fullContent(this,'60','��ҵ�ʱ�������Ͷ���ʹ���')"></odin:checkbox>
			<odin:checkbox property="tag61" label="��ҵרҵ����" onclick="fullContent(this,'61','��ҵרҵ����')"></odin:checkbox>
			<odin:checkbox property="tag62" label="��ҵ����ҵ��" onclick="fullContent(this,'62','��ҵ����ҵ��')"></odin:checkbox>
			<odin:checkbox property="tag63" label="��ҵ���չܿ�" onclick="fullContent(this,'63','��ҵ���չܿ�')"></odin:checkbox>
			<odin:checkbox property="tag64" label="��ҵ������" onclick="fullContent(this,'64','��ҵ������')"></odin:checkbox>
			<odin:checkbox property="tag64J" label="��ҵ����" onclick="fullContent(this,'64J','��ҵ����')"></odin:checkbox>
			<odin:checkbox property="tag64K" label="��ҵ�ͼ���" onclick="fullContent(this,'64K','��ҵ�ͼ���')"></odin:checkbox>
			<odin:checkbox property="tag64L" label="��ҵ��֯����" onclick="fullContent(this,'64L','��ҵ��֯����')"></odin:checkbox>
			<odin:checkbox property="tag65" label="���й�˾������Ӫ" onclick="fullContent(this,'65','���й�˾������Ӫ')"></odin:checkbox>
		</odin:groupBox>	
	</td>	
</tr>
<tr>
	<td>
		<odin:checkbox property="qx10" label="<span style='font-weight: bold;'>���⽻�������</span>" onclick="fullContent2(this,'66,67,68','���¸۰�̨�ȼ����⽻��,��������,�˷�')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>	
		<odin:groupBox title="" property="dwjlyjs" >			
			<odin:checkbox property="tag66" label="���¸۰�̨�ȼ����⽻��" onclick="fullContent(this,'66','���¸۰�̨�ȼ����⽻��')"></odin:checkbox>
			<odin:checkbox property="tag67" label="��������" onclick="fullContent(this,'67','��������')"></odin:checkbox>
			<odin:checkbox property="tag68" label="�˷�" onclick="fullContent(this,'68','�˷�')"></odin:checkbox>
		</odin:groupBox>	
	</td>
</tr>
<tr>
	<td>
		<odin:checkbox property="qx11" label="<span style='font-weight: bold;'>����</span>" onclick="fullContent2(this,'69,70,27,56,71,71N,71O,71P,71Q','�����ڽ�,�����о�,�˲Ź���,��������,��������,��Ҫ����,�����ճ����񣨰칫�ң�,�ۺ�������Ϣ,��Ч����')"></odin:checkbox>
	</td>
</tr>
<tr>
	<td>
		<odin:groupBox title="" property="qita" >	
			<odin:checkbox property="tag69" label="�����ڽ�" onclick="fullContent(this,'69','�����ڽ�')"></odin:checkbox>
			<odin:checkbox property="tag70" label="�����о�" onclick="fullContent(this,'70','�����о�')"></odin:checkbox>
			<odin:checkbox property="tag27" label="�˲Ź���" onclick="fullContent(this,'27','�˲Ź���')"></odin:checkbox>
			<odin:checkbox property="tag56" label="��������" onclick="fullContent(this,'56','��������')"></odin:checkbox>
			<odin:checkbox property="tag71" label="��������" onclick="fullContent(this,'71','��������')"></odin:checkbox>
			<odin:checkbox property="tag71N" label="��Ҫ����" onclick="fullContent(this,'71N','��Ҫ����')"></odin:checkbox>
			<odin:checkbox property="tag71O" label="�����ճ����񣨰칫�ң�" onclick="fullContent(this,'71O','�����ճ����񣨰칫�ң�')"></odin:checkbox>
			<odin:checkbox property="tag71P" label="�ۺ�������Ϣ" onclick="fullContent(this,'71P','�ۺ�������Ϣ')"></odin:checkbox>
			<odin:checkbox property="tag71Q" label="��Ч����" onclick="fullContent(this,'71Q','��Ч����')"></odin:checkbox>
		</odin:groupBox>
	</td>
</tr>
</table> --%>
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
	<textarea rows="3" cols="113" id="a0196z" name="a0196z"></textarea>
</div>

<script type="text/javascript">
//���������ʾѡ�б�ǩ
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
		type='1';//ȫѡ
	}else{
		type='2';//ȫ��ѡ
	}
	radow.doEvent("fullAll",type+'##'+value+'##'+valuename);
}

function save(){
	radow.doEvent("save.onclick");
}

</script>
