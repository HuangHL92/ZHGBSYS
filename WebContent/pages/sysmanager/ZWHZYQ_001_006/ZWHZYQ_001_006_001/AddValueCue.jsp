<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<title>新建补充信息类型集</title>
<table width="100%" cellpadding="10" cellspacing="0" border="0" style="margin-left:10px" >
	<tr>
		<td>
			<table  style="margin:5px;">
				<tr>
					<td>
						<odin:textEdit property="addValueSequence" disabled="true" required="true" label="序 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号"  width="213" maxlength="100"/>
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
						<odin:textEdit property="colCode" required="true" label="信息项编码" width="213" maxlength="100" validator="check" invalidText="注:信息项编码只能输入字母或阿拉伯数字,\n并只能以字母开头"/>
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
					<odin:textEdit property="addValueName" required="true" label="信息项名称" width="213" maxlength="100"/>
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
					<odin:select2 property="colType" required="true" label="信息项类型" width="213" data="['0', '文本'],['1', '数字'],['2', '日期']">
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
					<odin:select2 property="reference" editor="true" label="引用代码集" size="32"></odin:select2>
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
						<odin:textarea property="addValueDesc" cols="36" rows="3" label="信息项描述" maxlength="4000"/>
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
					<odin:checkbox property="isused" label="使用(在信息维护中显示本信息项)"/>
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
					<odin:hidden property="multilineshow" title="在信息维护中显示多行" />
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
					<odin:button text="&nbsp;保&nbsp;存&nbsp;" property="save"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="5px">
					<odin:button text="&nbsp;取&nbsp;消&nbsp;" property="cancel" handler="cancel"/>
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
	//复选框选中取消事件
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
