<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<script type="text/javascript">
function test(){
	//
}
</script>
<ss:toolBar property="t1">
	<ss:fill></ss:fill>
	<ss:doClickBtn handler="test" text="����" handlerName="test"></ss:doClickBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="6" title="��������">
	<ss:dateEdit property="d1" label="���ڲ���" onchange="true" p="E"></ss:dateEdit>
	<ss:select property="taab301" label="��������" p="E" codeType="EAZ216"></ss:select>
	<ss:empeyTD empeyTDCount="2"><td rowspan="6" colspan="2" align="center">
		<img width="120" style="border:solid 1px #D3D0D0;" height="150" src="images/icon/emptyPerson.jpg"/></td>
	</ss:empeyTD>
	<ss:textEdit property="tt1"  label="����" p="E" value="ttt123test"/>
	<ss:select property="aab301" label="����Ϣ" p="E" onchange="true"></ss:select>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:select property="aaf015" label="�ֵ���Ϣ" p="E" onchange="true"></ss:select>
	<ss:select property="aaf030" label="������Ϣ" p="E"></ss:select>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:numberEdit property="test1" isMoney="true" label="Ǯ" p="R"></ss:numberEdit>
	<ss:numberEdit property="aaa001" isPercent="true" label="�ٷֱ�" p="E"></ss:numberEdit>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:numberEdit property="test3" isPercentOrNot="true" label="�ٷֱ�����" p="E"></ss:numberEdit>
	<ss:select property="msel" multiSelect="true" label="��ѡ����" codeType="EAZ216" p="E" onchange="true"></ss:select>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:select property="msel2" label="��ҳ����" isPageSelect="true" codeType="AAE140" p="E" onchange="true"></ss:select>
</ss:hlistDiv>
<ss:hlistDiv id="div_2" cols="6">
	<ss:dateEdit property="d12" label="���ڲ���" onchange="true" p="E"></ss:dateEdit>
	<ss:select property="taab3012" label="��������" p="E" codeType="EAZ216"></ss:select>
	<ss:img property="pic1" colspan="2" rowspan="6"></ss:img>
	
	<ss:textEdit property="tt12"  label="����" p="D" value="ttt123test"/>
	<ss:select property="aab3012" label="����Ϣ" p="E" onchange="true"></ss:select>
	
	<ss:select property="aaf0152" label="�ֵ���Ϣ" p="E" onchange="true"></ss:select>
	<ss:select property="aaf0302" label="������Ϣ" p="E"></ss:select>
	
	<ss:numberEdit property="test12" isMoney="true" label="Ǯ" p="R"></ss:numberEdit>
	<ss:numberEdit property="aaa0012" isPercent="true" label="�ٷֱ�" p="E"></ss:numberEdit>
	
	<ss:numberEdit property="test32" isPercentOrNot="true" label="�ٷֱ�����" p="E"></ss:numberEdit>
	<ss:select property="msel3" multiSelect="true" label="��ѡ����" codeType="EAZ216" p="E" onchange="true"></ss:select>
	
	<ss:select property="msel22" label="��ҳ����" isPageSelect="true" codeType="AAE140" p="E" onchange="true"></ss:select>
	<ss:numberEdit property="num1" label="��ͨ����" p="E"></ss:numberEdit>
	<ss:textEdit property="txt1" label="��ͨ�ı�" p="E"></ss:textEdit>
</ss:hlistDiv>
<script type="text/javascript">
	document.getElementById("tt1").onpaste = function(e){
		var key = "";
		if(window.clipboardData){
			key = window.clipboardData.getData('Text');
		}else{
			key = e.clipboardData.getData("text/plain");
		}
		alert(key);
	}
</script>