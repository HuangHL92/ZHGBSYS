<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<title>�½�������Ϣ���ͼ�</title>
<table width="100%" cellpadding="10" cellspacing="0" border="0" style="margin-left:10px" >
	<tr>
		<td>
			<table  style="margin:5px;">
				<tr>
					<td>
						<odin:textEdit property="addValueSequence" disabled="true" required="true" label="�� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��"  width="213" maxlength="100"/>
					</td>
			    </tr>
			</table>
		</td>
	</tr>
	<tr>	
		<td>
			<table  style="margin:5px;">
				<tr>
					<td>
						<odin:textEdit property="colCode" required="true" label="��Ϣ�����" width="213" maxlength="100" validator="check" invalidText="ע:��Ϣ�����ֻ��������ĸ����������,\n��ֻ������ĸ��ͷ"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>	
		<td>
			<table  style="margin:5px;">
				<tr>
					<td>
					<odin:textEdit property="addValueName" required="true" label="��Ϣ������" width="213" maxlength="100"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>	
		<td>
			<table  style="margin:5px;">
				<tr>
					<td>
					<odin:select2 property="colType" required="true" label="��Ϣ������" width="213" data="['0', '�ı�'],['1', '����'],['2', '����']">
                    </odin:select2>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:5px;">
				<tr>
					<td width="4px">
					<odin:select2 property="reference" editor="true" label="���ô��뼯" size="32"></odin:select2>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:5px;">
				<tr>
					<td width="4px">
						<odin:textarea property="addValueDesc" cols="36" rows="3" label="��Ϣ������" maxlength="4000"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:2px;">
				<tr>
					<td width="10px"></td>
					<td>
					<odin:checkbox property="isused" label="ʹ��(����Ϣά������ʾ����Ϣ��)"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:2px;">
				<tr>
				    <td width="10px"></td>
					<td>
					<odin:hidden property="multilineshow" title="����Ϣά������ʾ����" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
					<odin:button text="&nbsp;��&nbsp;��&nbsp;" property="save"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="5px">
					<odin:button text="&nbsp;ȡ&nbsp;��&nbsp;" property="cancel" handler="cancel"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">

	function cancel(){
        radow.doEvent("close");       	
	}
	
	Ext.onReady(function(){
		radow.doEvent('queryObj');
	}, this, {
		delay : 500
	});

	function propertyValueOnchange(){
		document.all.changeflag.value='true';
	}
	//��ѡ��ѡ��ȡ���¼�
	var isused = document.getElementById('isused');
	/* isused.onclick = function(){
		var multilineshow = document.getElementById('multilineshow');
		if(this.checked){
			multilineshow.setAttribute('disabled',false);
		} else {
			if(multilineshow.checked) {
				multilineshow.checked = false;
			}
			multilineshow.setAttribute('disabled',true);
		}
	} */
	
	function check(){
		var colCode = document.all.colCode.value; 
		var pattern = /^([A-Z|a-z|]+[A-Z|a-z|0-9]*)$/;
		flag = pattern.test(colCode);
		if(!flag){
			return false;
		}else{
			return true;
		}
	}

</script>
