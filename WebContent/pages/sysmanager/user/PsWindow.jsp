<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<style>
	div{
		text-align: center;
	}
</style>
<script type="text/javascript">
	//������У��
	function odpasswordMarkTrue(){
		document.getElementById('odpasswordMark').style.display='inline';
		document.getElementById('odpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function odpasswordMarkFalse(){
		document.getElementById('odpasswordMark').style.display='inline';
		document.getElementById('odpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '����ľ����벻��ȷ�����������룡';
	}
	//������У��
	function newpasswordTrue(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function newpasswordFalse1(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '������������룡��';
	}
	function newpasswordFalse2(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '�����������룡��';
	}
	function newpasswordFalse3(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '��������������ͬ��';
	}
	function newpasswordFalse4(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '���볤�ȱ������10λ�������֣���ĸ�������ַ���ɣ�';
	}
	
	//ȷ������У��
	function checkpasswordFalse1(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '�����������룡';
	}
	function checkpasswordFalse2(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '�������������룡';
	}
	function checkpasswordFalse3(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '��������������벻һ����';
	}
	function checkpasswordTrue(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function showResult(btn){
		parent.location.href='LogonDialog.jsp';
	}
	
	
</script>
<div>


<odin:groupBox title="�޸�����" property="gropBoxId">
	<table>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="odpassword" inputType="password" label="��&nbsp;&nbsp;��&nbsp;&nbsp;��"></odin:textEdit>
			</td>
			<td>
				<img  src="" width="20" height="20" id="odpasswordMark" style="display: none;"  />
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="newpassword" inputType="password" label="��&nbsp;&nbsp;��&nbsp;&nbsp;��"></odin:textEdit>
			</td>
			<td>
				<img src="" width="20" height="20" id="newpasswordMark" style="display: none;"/>
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="checkpassword" inputType="password" label="ȷ��������" ></odin:textEdit>
			</td>
			<td>
				<img src="" width="20" height="20" id="checkpasswordMark" style="display: none;"/>
			</td>
		</tr>
		
	</table>
	<table style="width: 100%;">
		<tr>
			<td style="padding-right: 20px;">&nbsp;</td>
			<td style="text-align: center;">
				<odin:button property="savePd" text="����"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>

<input id="hint" type="text" value="" style="border: none;text-align: center;color: red;width:98%;background-color: white;" disabled="disabled" readonly="readonly"/>


</div>


