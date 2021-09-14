<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
/**
 * ҳ����ʽCSS
 */
#tag_container {
	width: 774px;
	height: 538px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
}

#left_div {
	width: 240px;
	height: 536px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
	overflow-y: auto;
	overflow-x: hidden;
}

#left_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	border-width: 0 1px 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	line-height: 26px;
	padding-left: 2px;
}

#right_div {
	width: 520px;
	height: 520px;
	float: left;
	padding-left: 10px;
}

#right_div table {
	font-size: 14px;
}

#right_div div {
	display: none;
	width: 100%;
	height: 536px;
	overflow-y: auto;
}

#right_div div table {
	font-size: 14px;
}

#right_div table tr td {
	height: 26px;
	line-height: 26px;
}

#right_div table tr td input[type=text] {
	width: 50px;
	height: 21px;
}

#bottom_div {
	width: 100%;
	height: 40px;
	padding-top: 5px;
}

#bottom_div table {
	width: 100%;
}
</style>
<div id="tag_container">
	<div id="left_div">
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">��ҵ����</div>
		<!-- <div class="leftMenu" onclick="changeTag(this, '02');">ʡ����ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">��ʡ������ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">�أ��С��ݡ��ˣ�ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '05');">�أ��С������죩ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '06');">���򣨽ֵ���ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '07');">������ҵְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '08');">��ҵְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '09');">��Уְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '10');">����Ժ��ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '11');">����ҽԺְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '12');">���Ѽ�������������</div> -->
		<div class="leftMenu" onclick="changeTag(this, '13');">�ſھ���</div>
		<div class="leftMenu" onclick="changeTag(this, '15');">���ɹ�������</div>
		<div class="leftMenu" onclick="changeTag(this, '16');">���龭��</div>
		<div class="leftMenu" onclick="changeTag(this, '24');">�Ϲ����ɹ�������</div>
		
		<div class="leftMenu" onclick="changeTag(this, '18');">��������</div>
		<div class="leftMenu" onclick="changeTag(this, '21');">��������</div>
		<!-- <div class="leftMenu" onclick="changeTag(this, '14');">����������һίԱ���</div>
		
		
		<div class="leftMenu" onclick="changeTag(this, '17');">������������������ó���Ⱦ���</div>
		<div class="leftMenu" onclick="changeTag(this, '20');">���⹤������</div> -->
<!-- 		
		<div class="leftMenu" onclick="changeTag(this, '22');">�¼���ҵ��ְ��ְ����</div>
		<div class="leftMenu" onclick="changeTag(this, '23');">�ܲ�ְ�ܲ�����ְ����</div>
		 -->
		
	</div>
	<div id="right_div">
	    <!-- ��ҵ���� -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="tag0101" id="tag0101" >
					<label>��Ӫ��ҵ</label>
				</td>	
			</tr>	
			
			
		</table>
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0201" id="tag0201" >
					<label>ʡֱ������ְ�쵼ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0202" id="tag0202" >
					<label>ʡֱ���Ÿ�ְ�쵼ְ�� </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0203" id="tag0203" >
					<label>ʡֱ�����ش�����ְ�쵼ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0204" id="tag0204" >
					<label>ʡֱ�����ش�����ְ�쵼ְ��</label>
				</td>	
			</tr>
		</table>
		<!-- ��ʡ������ְ�� -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0301" id="tag0301" >
					<label>��ʡ�����в�����������ְ�ĸ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0302" id="tag0302" >
					<label>��ʡ��������������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0303" id="tag0303" >
					<label>��ʡ�����е�����ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0308" id="tag0308" >
					<label>��ʡ�����з��조������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0304" id="tag0304" >
					<label>��ʡ������ֱ��������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0305" id="tag0305" >
					<label>��ʡ������ֱ�����Ÿ�ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0306" id="tag0306" >
					<label>��ʡ������ֱ�������в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0307" id="tag0307" >
					<label>��ʡ������ֱ�������в㸱ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- �أ��С��ݡ��ˣ�ְ�� -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0401" id="tag0401" >
					<label>�أ��С��ݡ��ˣ���ί��ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag0401n" id="tag0401n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0402" id="tag0402" >
					<label>�أ��С��ݡ��ˣ�������ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag0402n" id="tag0402n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0403" id="tag0403" >
					<label>�أ��С��ݡ��ˣ�������������ְ�ĸ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0404" id="tag0404" >
					<label>�أ��С��ݡ��ˣ���������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0405" id="tag0405" >
					<label>�أ��С��ݡ��ˣ�������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0410" id="tag0410" >
					<label>�أ��С��ݡ��ˣ����조������ </label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0406" id="tag0406" >
					<label>�أ��С��ݡ��ˣ�ֱ��������ְ </label>
				</td>	
			</tr>
<!-- 			<tr>
				<td>
					<input type="checkbox" name="tag0407" id="tag0407" >
					<label>��ֱ�����Ű��ӳ�Ա����ְ�� </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0408" id="tag0408" >
					<label>��ֱ�������в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0409" id="tag0409" >
					<label>��ֱ�������в㸱ְ </label>
				</td>	
			</tr>	 -->								
			<tr>
				<td>
					<input type="checkbox" name="tag0411" id="tag0411" >
					<label>�أ��С��ݡ��ˣ�ֱ�����Ű��ӳ�Ա(��ְ)</label>
				</td>	
			</tr>					
		</table>	
		<!-- �أ��С������죩ְ�� -->
		<table id="tag05" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0501" id="tag0501" >
					<label>�أ��С������죩��ί��ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag0501n" id="tag0501n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0502" id="tag0502" >
					<label>�أ��С������죩������ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag0502n" id="tag0502n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0504" id="tag0504" >
					<label>�أ��С������죩��ƶ�����ڼ�ƶ���ص�����ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0503" id="tag0503" >
					<label>�أ��С������죩������ְ </label>
				</td>	
			</tr>
<!-- 			<tr>
				<td>
					<input type="checkbox" name="tag0505" id="tag0505" >
					<label>�ؼ�������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0506" id="tag0506" >
					<label>�ؼ����Ű��ӳ�Ա����ְ�� </label>
				</td>	
			</tr> -->
			<tr>
				<td>
					<input type="checkbox" name="tag0507" id="tag0507" >
					<label>�أ��С������죩���조������ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0508" id="tag0508" >
					<label>�أ��С������죩ֱ��������ְ </label>
				</td>	
			</tr>
		</table>	
		<!-- ���򣨽ֵ���ְ�� -->
		<table id="tag06" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0601" id="tag0601" >
					<label>���򣨽ֵ���������ְ  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0602" id="tag0602" >
					<label>���򣨽ֵ����������ӳ�Ա </label>
				</td>	
			</tr>
		</table>	
		<!-- ������ҵְ��  -->
		<table id="tag07" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0701" id="tag0701" >
					<label>�йܽ�����ҵ��ְ  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0702" id="tag0702" >
					<label>�йܽ�����ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0703" id="tag0703" >
					<label>�йܽ�����ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0704" id="tag0704" >
					<label>���뵥λ����������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0705" id="tag0705" >
					<label>���뵥λ����������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0708" id="tag0708" >
					<label>ʡ��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0709" id="tag0709" >
					<label>ʡ��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0713" id="tag0713" >
					<label>ʡ��������ҵ��ίίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0714" id="tag0714" >
					<label>ʡ��������ҵ����������Ա</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0712" id="tag0712" >
					<label>����������ҵ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ��ҵְ�� -->
		<table id="tag08" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0801" id="tag0801" >
					<label>�й���ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0802" id="tag0802" >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0803" id="tag0803" >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0820" id="tag0820" >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0804" id="tag0804" >
					<label>����Ժ����ί��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0805" id="tag0805" >
					<label>����Ժ����ί��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0806" id="tag0806" >
					<label>����Ժ����ί��������ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0813" id="tag0813" >
					<label>���뵥λ������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0814" id="tag0814" >
					<label>���뵥λ������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0808" id="tag0808" >
					<label>ʡ����ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0809" id="tag0809" >
					<label>ʡ����ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0810" id="tag0810" >
					<label>ʡ����ҵ��ίίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0811" id="tag0811" >
					<label>ʡ����ҵ����������Ա</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0812" id="tag0812" >
					<label>������ҵ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ��Уְ�� -->
		<div id="tag09" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag0901" id="tag0901" >
						<label>�йܸ�У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0902" id="tag0902" >
						<label>�йܸ�У�в���ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0903" id="tag0903" >
						<label>�йܸ�У�в㸱ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0904" id="tag0904" >
						<label>������У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0905" id="tag0905" >
						<label>������У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0906" id="tag0906" >
						<label>������У�в���ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0907" id="tag0907" >
						<label>ʡ����У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0908" id="tag0908" >
						<label>ʡ����У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0909" id="tag0909" >
						<label>ʡ����У�в���ְ</label>
					</td>	
				</tr>
				<!-- <tr>
					<td>
						<input type="checkbox" name="tag0910" id="tag0910" >
						<label>���ҡ�˫һ���������У</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0911" id="tag0911" >
						<label>ʡ�ص㽨���У</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0912" id="tag0912" >
						<label>���Ƹ�У��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0913" id="tag0913" >
						<label>���Ƹ�УУ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0914" id="tag0914" >
						<label>���Ƶ�ί�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0915" id="tag0915" >
						<label>���Ƹ�У��У��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0916" id="tag0916" >
						<label>���Ƹ�У��ίίԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0917" id="tag0917" >
						<label>��ְԺУ��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0918" id="tag0918" >
						<label>��ְԺУУ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0919" id="tag0919" >
						<label>��ְԺУ��ί�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0920" id="tag0920" >
						<label>��ְԺУ��У��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0921" id="tag0921" >
						<label>���Ƹ�У����ѧԺ��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0922" id="tag0922" >
						<label>���Ƹ�У����ѧԺԺ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0923" id="tag0923" >
						<label>���Ƹ�У������ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0924" id="tag0924" >
						<label>���Ƹ�У���Ÿ�ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0925" id="tag0925" >
						<label>�еط��쵼��������</label>
					</td>	
				</tr> -->
				<tr>
					<td>
						<input type="checkbox" name="tag0926" id="tag0926" >
						<label>��˫�������ɲ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0927" id="tag0927" >
						<label>���Ҽ��߲���˲�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0928" id="tag0928" >
						<label>���и�У����Ժ(ϵ)��ְ����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0929" id="tag0929" >
						<label>���й�(��)����ѧ����</label>
					</td>	
				</tr>
			</table>	
		</div>
		<!-- ����Ժ��ְ��  -->
		<table id="tag10" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1001" id="tag1001" >
					<label>������͹���Ժֱ������Ժ�����������ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag1001n" id="tag1001n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1002" id="tag1002" >
					<label>������͹���Ժֱ������Ժ�����������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1003" id="tag1003" >
					<label>���뵥λ��������Ժ����ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag1003n" id="tag1003n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1004" id="tag1004" >
					<label>���뵥λ��������Ժ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1005" id="tag1005" >
					<label>ʡ������Ժ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1006" id="tag1006" >
					<label>ʡ������Ժ����ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ����ҽԺְ�� -->
		<table id="tag11" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1101" id="tag1101" >
					<label>��������ίֱ��ҽԺ��ְ&nbsp;&nbsp;</label>
					<label>��ְ����:</label>
					<input type="text" name="tag1101n" id="tag1101n" style="width:50px;height:21px;" >
					<label>��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1102" id="tag1102" >
					<label>��������ίֱ��ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1103" id="tag1103" >
					<label>��������ίֱ��ҽԺ�в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1104" id="tag1104" >
					<label>�йܸ�У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1105" id="tag1105" >
					<label>�йܸ�У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1106" id="tag1106" >
					<label>������У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1107" id="tag1107" >
					<label>������У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1108" id="tag1108" >
					<label>ʡ������ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1109" id="tag1109" >
					<label>ʡ������ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1111" id="tag1111" >
					<label>ʡ����У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1112" id="tag1112" >
					<label>ʡ����У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1110" id="tag1110" >
					<label>��������ҽԺ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ���Ѽ�������������  -->
		<table id="tag12" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1201" id="tag1201" >
					<label>�½���������&nbsp;&nbsp;</label>
					<label>��ע:</label>
					<input type="text" name="tag1201n" id="tag1201n" style="width:150px;height:21px;" >
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1202" id="tag1202" >
					<label>���ع�������&nbsp;&nbsp;</label>
					<label>��ע:</label>
					<input type="text" name="tag1202n" id="tag1202n" style="width:150px;height:21px;" >
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1203" id="tag1203" >
					<label>�ຣ��������&nbsp;&nbsp;</label>
					<label>��ע:</label>
					<input type="text" name="tag1203n" id="tag1203n" style="width:150px;height:21px;" >
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1208" id="tag1208" >
					<label>�Ĵ���������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1209" id="tag1209" >
					<label>���ֹ�������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1204" id="tag1204" >
					<label>ƶ��������������&nbsp;&nbsp;</label>
					<label>��ע:</label>
					<input type="text" name="tag1204n" id="tag1204n" style="width:150px;height:21px;" >
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1205" id="tag1205" >
					<label>�����������Ϲ�ҵ���غ͸�������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1206" id="tag1206" >
					<label>������������ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1207" id="tag1207" >
					<label>�������Ѽ���Կ�Ԯ��������������</label>
				</td>	
			</tr>
		</table>	
		<!-- �ſھ���  -->
		<table id="tag13" style="display: none;">
			<tr>
				<td style="display: none;">
					<input type="checkbox" name="tag1301" id="tag1301" >
					<label>��������Ǵ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1302" id="tag1302" >
					<label>�����벿�ţ���λ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1303" id="tag1303" >
					<label>�����벿�ţ���λ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1304" id="tag1304" >
					<label>��ʡί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1305" id="tag1305" >
					<label>��ʡί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1306" id="tag1306" >
					<label>��ʡί�в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1307" id="tag1307" >
					<label>��ʡί�в㸱ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1308" id="tag1308" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1309" id="tag1309" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1310" id="tag1310" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1311" id="tag1311" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ����������һίԱ��� -->
		<table id="tag14" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1401" id="tag1401" >
					<label>����ȫ������������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1402" id="tag1402" >
					<label>ȫ���˴����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1403" id="tag1403" >
					<label>ȫ���˴�ר��ίԱ��ίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1404" id="tag1404" >
					<label>ȫ����ЭίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1405" id="tag1405" >
					<label>ȫ����Э��ר��ίԱ�ḱ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1406" id="tag1406" >
					<label>����ȫʡ����������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1407" id="tag1407" >
					<label>ȫʡ�˴����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1408" id="tag1408" >
					<label>ȫʡ�˴�ר��ίԱ��ίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1409" id="tag1409" >
					<label>ȫʡ��ЭίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1410" id="tag1410" >
					<label>ȫʡ��Э��ר��ίԱ�ḱ����</label>
				</td>	
			</tr>
		</table>	
		<!-- ������������  -->
		<div id="tag15" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag1511" id="tag1511" >
						<label>���ι���������Ա���������Ա����ְ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1501" id="tag1501" >
						<label>��ʦ����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1509" id="tag1509" >
						<label>���ɽ�ѧ���о�����</label>
					</td>	
				</tr>
				<!-- 
				<tr>
					<td>
						<input type="checkbox" name="tag1502" id="tag1502" >
						<label>���Ժ����&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1502n" id="tag1502n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1503" id="tag1503" >
						<label>��Ժ����&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1503n" id="tag1503n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1504" id="tag1504" >
						<label>�������ع���&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1504n" id="tag1504n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1505" id="tag1505" >
						<label>˾������ϵͳ����&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1505n" id="tag1505n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1506" id="tag1506" >
						<label>���Ұ�ȫ���ع���&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1506n" id="tag1506n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1507" id="tag1507" >
						<label>���һ�ط���������&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1507n" id="tag1507n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1508" id="tag1508" >
						<label>��ʦ&nbsp;&nbsp;</label>
						<label>��ְ����:</label>
						<input type="text" name="tag1508n" id="tag1508n" style="width:50px;height:21px;" >
						<label>��</label>
					</td>	
				</tr>
				
				<tr>
					<td>
						<input type="checkbox" name="tag1510" id="tag1510" >
						<label>������λ��Ҫ�쵼��ְ����</label>
					</td>	
				</tr>
				
				<tr>
					<td>
						<input type="checkbox" name="tag1512" id="tag1512" >
						<label>���ι����조������&nbsp;&nbsp;</label>
						<label>��ע��ע���С��أ�:</label>
						<input type="text" name="tag1512n" id="tag1512n" style="width:50px;height:21px;" >
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1513" id="tag1513" >
						<label>���ι������ֳ�&nbsp;&nbsp;</label>
						<label>��ע��ע���С��أ�:</label>
						<input type="text" name="tag1513n" id="tag1513n" style="width:50px;height:21px;" >
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1514" id="tag1514" >
						<label>���ι�˾���ֳ�&nbsp;&nbsp;</label>
						<label>��ע��ע���С��أ�:</label>
						<input type="text" name="tag1514n" id="tag1514n" style="width:50px;height:21px;" >
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1515" id="tag1515" >
						<label>������λ���ӳ�Ա��ְ����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1516" id="tag1516" >
						<label>������λ����ְ&nbsp;&nbsp;</label>
						<label>��ע��ע��ʡ���С��أ�:</label>
						<input type="text" name="tag1516n" id="tag1516n" style="width:50px;height:21px;" >
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1517" id="tag1517" >
						<label>������λ���ӳ�Ա&nbsp;&nbsp;</label>
						<label>��ע��ע��ʡ���С��أ�:</label>
						<input type="text" name="tag1517n" id="tag1517n" style="width:50px;height:21px;" >
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1518" id="tag1518" >
						<label>��������λ����ְ�쵼��λ��ְ���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1519" id="tag1519" >
						<label>���쵼��λ��ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1520" id="tag1520" >
						<label>�ְ�����ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1521" id="tag1521" >
						<label>���쵼ְ������ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1522" id="tag1522" >
						<label>���·��ɹ���ʱ��</label>
					</td>	
				</tr> 
				<tr>
					<td>
						<input type="checkbox" name="tag1523" id="tag1523" >
						<label>���ɹ���������������</label>
					</td>	
				</tr>
				 <tr>
					<td>
						<input type="checkbox" name="tag1524" id="tag1524" >
						<label>���·��ɹ�������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1525" id="tag1525" >
						<label>���·��ɹ���һ��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1526" id="tag1526" >
						<label>����ϵͳ�쵼�ɲ�������ְ���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1527" id="tag1527" >
						<label>ͬһ��λ���¼�֮�佻����ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1528" id="tag1528" >
						<label>������ϵͳ�ڽ�����ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1529" id="tag1529" >
						<label>������ϵͳ�⽻����ְ</label>
					</td>	
				</tr> 
				<tr>
					<td>
						<input type="checkbox" name="tag1530" id="tag1530" >
						<label>ͨ������ͳһ����ְҵ�ʸ���ȡ�÷���ְҵ�ʸ�</label>
					</td>	
				</tr>
				 <tr>
					<td>
						<input type="checkbox" name="tag1534" id="tag1534" >
						<label>�����󷨹١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1535" id="tag1535" >
						<label>һ���߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1536" id="tag1536" >
						<label>�����߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1537" id="tag1537" >
						<label>�����߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1538" id="tag1538" >
						<label>�ļ��߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1539" id="tag1539" >
						<label>һ�����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1540" id="tag1540" >
						<label>�������١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1541" id="tag1541" >
						<label>�������١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1542" id="tag1542" >
						<label>�ļ����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1543" id="tag1543" >
						<label>�弶���١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1545" id="tag1545" >
						<label>һ������רԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1546" id="tag1546" >
						<label>��������רԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1547" id="tag1547" >
						<label>һ���߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1548" id="tag1548" >
						<label>�����߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1549" id="tag1549" >
						<label>�����߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1550" id="tag1550" >
						<label>�ļ��߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1552" id="tag1552" >
						<label>һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1553" id="tag1553" >
						<label>��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1554" id="tag1554" >
						<label>��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1555" id="tag1555" >
						<label>�ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1557" id="tag1557" >
						<label>һ����Ա</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1558" id="tag1558" >
						<label>������Ա</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1560" id="tag1560" >
						<label>������һ���ܼ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1561" id="tag1561" >
						<label>�����������ܼ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1562" id="tag1562" >
						<label>������һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1563" id="tag1563" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1564" id="tag1564" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1565" id="tag1565" >
						<label>�������ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1566" id="tag1566" >
						<label>������һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1567" id="tag1567" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1568" id="tag1568" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1569" id="tag1569" >
						<label>�������ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1570" id="tag1570" >
						<label>������Ա</label>
					</td>	
				</tr> -->
			</table>	
		</div>
		<!-- ���龭�� -->
		<table id="tag16" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1601" id="tag1601" >
					<label>��ְ���͹����쵼������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1602" id="tag1602" >
					<label>��ְ��ʡ���쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1603" id="tag1603" >
					<label>��ְ��ʡ���쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1604" id="tag1604" >
					<label>����ְ���͹����쵼������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1605" id="tag1605" >
					<label>����ְ��ʡ���쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1606" id="tag1606" >
					<label>����ְ��ʡ���쵼����</label>
				</td>	
			</tr>
			<tr style="display: none;">
				<td>
					<input type="checkbox" name="tag1607" id="tag1607" >
					<label>�йܽ�����ҵ���й���ҵ��ְ��Ҫ����������</label>
				</td>	
			</tr>
			<tr style="display: none;">
				<td>
					<input type="checkbox" name="tag1608" id="tag1608" >
					<label>�йܽ�����ҵ���й���ҵ����ְ��Ҫ����������</label>
				</td>	
			</tr>
		</table>	
		<!-- ������������������ó���Ⱦ���  -->
		<table id="tag17" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1701" id="tag1701" >
					<label>���Ҽ�������������������ó��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1702" id="tag1702" >
					<label>ʡ��������������������ó��</label>
				</td>	
			</tr>
		</table>
		<!-- ���⹤������  -->
		<table id="tag20" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2001" id="tag2001" >
					<label>��ʹ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2002" id="tag2002" >
					<label>��ʹ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2003" id="tag2003" >
					<label>��ʹ�β���</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2004" id="tag2004" >
					<label>������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2005" id="tag2005" >
					<label>��������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2006" id="tag2006" >
					<label>���⹤��</label>
				</td>	
			</tr>
		</table>
  		<!-- �������� -->
		<table id="tag21" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2101" id="tag2101" >
					<label>�߱��ȵ�����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2102" id="tag2102" >
					<label>�߱������й���ְ�ʸ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2103" id="tag2103" >
					<label>�߱����ڻ����й���ְ�ʸ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2104" id="tag2104" >
					<label>���</label>
				</td>	
			</tr>
		<!-- 	<tr>
				<td>
					<input type="checkbox" name="tag2105" id="tag2105" >
					<label>������ϯ����</label>
				</td>	
			</tr>-->
		</table>	
<!-- 
		<table id="tag22" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2201" id="tag2201" >
					<label>���ι�������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2202" id="tag2202" >
					<label>���ι�������ҵ��ְ</label>
				</td>	
			</tr>
		</table>
		<table id="tag23" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2301" id="tag2301" >
					<label>���ι�������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2302" id="tag2302" >
					<label>���ι����Ÿ�ְ</label>
				</td>	
			</tr>
		</table>   -->
		<!-- �Ϲ����ɹ������� -->
		<table id="tag24" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2401" id="tag2401" >
					<label>��ְ���ι�������һ��ظ�������ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2402" id="tag2402" >
					<label>��ְ���ι�ʡֱ��λ��������ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2403" id="tag2403" >
					<label>��ְ���ι����ظ�������ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2404" id="tag2404" >
					<label>��ְ���ι���������ְ��</label>
				</td>	
			</tr>
		</table>
		<!-- �������� -->
		<table id="tag18" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1801" id="tag1801" >
					<label>���鲿˫������ְ�ɲ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1802" id="tag1802" >
					<label>���鲿˫������ְ�ɲ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1803" id="tag1803" >
					<label>���꼰���ϻ��㹤������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1821" id="tag1821" >
					<label>���и߼�רҵ����ְ��ְ�ƣ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1822" id="tag1822" >
					<label>���й�(��)����ѧ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1805" id="tag1805" >
					<label>�Ƹ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1806" id="tag1806" >
					<label>����ѡ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1823" id="tag1823" >
					<label>��ʦְ���Ͼ�ת</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1824" id="tag1824" >
					<label>��ְ�����¾�ת</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1807" id="tag1807" >
					<label>ѡ����</label>
				</td>	
			</tr>
			<tr style="display: none;">
				<td>
					<input type="checkbox" name="tag1809" id="tag1809" onclick="disableInputTag(this,'tag1809n')">
					<label>����</label>
				</td>	
			</tr>
			<tr style="display: none;">
				<td>
					<textarea rows="3" cols="70"  name="tag1809n" id="tag1809n" disabled="disabled"></textarea>
				</td>	
			</tr>
		</table>		
	</div>
</div>
<%-- <div id="bottom_div">
	<table>
		<tr>
			<td align="center" >
				<odin:button text="��&nbsp;&nbsp;��" property="save" />
			</td>		
		</tr>		
	</table>
</div>  --%>
<table align="center" width="96%">	
			<td align="center">
				<img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('save')">
			</td>
</table>
<script type="text/javascript">
//���ݸ��ڵ��л��ӽڵ�
function changeTag(node, codevalue){
	var codevaluelist = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17','20','21','18','24'];
	for(var i = 0,len=codevaluelist.length; i < len; i++) {
		document.getElementById("tag" + codevaluelist[i]).style.display = "none";
	}
	document.getElementById("tag" + codevalue).style.display = "block";
	changeTagMenuHover(node);
}

//��ǩ��
function disableInputTag(check,inputId) {
	var obj = $('#'+inputId);
	if($(check).is(':checked')) {
		obj.attr("disabled",false);
	} else {
		obj.val("");
		obj.attr("disabled","disabled");
	}
}
/**
 * ******************************************�˴��������з��������޸����˵������ͣ��ʽ������ʹ�á�*******************************************************
 */
Ext.onReady(function() {
	leftMenuHover();
	unbindOnmouseEvent(document.getElementById("firstTag"));
});

function changeTagMenuHover(node){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		nodes[i].style.backgroundColor = "#FFFFFF";
		bindOnmouseEvent(nodes[i]);
	}
	unbindOnmouseEvent(node);
	node.style.backgroundColor = "#1E90FF";
}

//�˵����������������뿪�¼�
function leftMenuHover(){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		bindOnmouseEvent(nodes[i]);
	}
}

/*
 * ��дgetElementsByClassName()������IE8������û�и÷���
 */
function getElementsByClassName(className, tagName) {
    if (document.getElementsByClassName) {
        // ʹ�����з���
        return document.getElementsByClassName(className);
    } else {
        // ѭ���������б�ǩ�����ش�����Ӧ������Ԫ��
        var rets = [], nodes = document.getElementsByTagName(tagName);
        for (var i = 0, len = nodes.length; i < len; i++) {
            if (hasClass(nodes[i],className)) {
            	rets.push(nodes[i]);
            }
        }
        return rets;
    }
}

function hasClass(tagStr,className){  
    var arr=tagStr.className.split(/\s+/ );  //����������ʽ����Ϊclass�����ж��,�ж��Ƿ����  
    for (var i=0;i<arr.length;i++){  
           if (arr[i]==className){  
                 return true ;  
           }  
    }  
    return false ;  
}

function bindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#FFFFFF"; };//����뿪�¼�
	node.onmousedown=function(){node.style.backgroundColor = "#1E90FF";};//�����ʱ�����¼�
}

function unbindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#1E90FF"; };//����뿪�¼�
	node.onmousedown=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ʱ�����¼�
}
</script>