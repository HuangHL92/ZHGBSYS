<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<style>
.t1{
font-family: ����С���μ���;font-size: 22px;
}
.t2{
font-weight: bold;
font-size: 18px;
}
</style>
<div align="center" style="overflow: auto;height:100%;">
<table style="width:90%;">
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td align="center" class="t1">
			һ���ص������ɲ�����
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_1')" >1�����ڿ�����й���ְ�ɲ���ѡ����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_2')" >2���ɽ�һ��ʹ���йܸɲ���ѡ����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_3')" >3�����ڿ�����йܸ�ְ�ɲ���ѡ����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_4')" >4���ɹ�ע����������ɲ���Ů�ɲ��͵���ɲ���ѡ����</a>
		</td>
	</tr>
	<tr>
		<td>
			5���ɷֲ���������й�����ɲ�����������ɲ�����
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td align="center"  class="t1">
			�����ճ������й�����
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td class="t2">
			��һ���쵼����
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_1')" >1�������أ��У����㵳���쵼��������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_2')" >2�������أ��У����ע�����쵼��������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_3')" >3����ֱ��λ�����������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_4')" >4����ֱ��λ���ע��������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_5')" >5�����������У�����������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_6')" >6�����������У���ע��������</a>
		</td>
	</tr>
	<tr>
		<td class="t2">
			�������쵼�ɲ�
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_1')" >1���ۺ����۱Ƚ�������й���ְ�ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_2')" >2���ۺ����۱Ƚ�������йܸ�ְ�ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_3')" >3���ۺ����۱Ƚ����������ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_4')" >4�����ע���йܸɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_5')" >5�����ע������ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			6������������ܴ����Ŀǰ���ڴ���Ӱ���ڸɲ�����
		</td>
	</tr>
	<tr>
		<td class="t2">
			������������θɲ�����
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_1')" >1��1980���Ժ�������йܸɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_2')" >2��1985���Ժ������������ֵ�������ְ�����ĸɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_3')" >3��1990���Ժ������������ֵ��������ӳ�Ա�����ĸɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_4')" >4��45�����Ҽ������йܸɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_5')" >5��40�����Ҽ����´������в㣩��ְ�ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_6')" >6��35�����Ҽ����´����в㣨��ְ���ɲ�����</a>
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td align="center"  class="t1">
			�����ص��λ�ɲ�����
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td class="t2">
			��һ�������أ��У�
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_1')" >1�������أ��У���ί�������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_2')" >2�������أ��У�������ְ����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_3')" >3�������أ��У���ί����Ǹɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_4')" >4�������أ��У���������ɲ�����</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_5')" >5�������أ��У���ί���</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_6')" >6�������أ��У�ί��֯������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_7')" >7�������أ��У�ίͳս������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_8')" >8�������أ��У�ί����������</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_9')" >9�������أ��У������ֳ�</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_10')" >10�������أ��У���������</a>
		</td>
	</tr>
	<tr>
		<td class="t2">
			��������ֱ��λ
		</td>
	</tr>
	<tr>
		<td>
			1����ֱ��λ��Ҫ�쵼
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_2_2')" >2����ֱ��λ˫��</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_2_3')" >3����ֱ��λ�����</a>
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr align="center"  class="t1">
		<td>
			�ġ������йظɲ�����
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td>
			1���з��١�������ְ�ʸ�ĸɲ�����
		</td>
	</tr>
	<tr>
		<td>
			2�����Ⱦ��ϵ��ֱϵ�����ں�����Լ����к�����ѧ�����ĸɲ�����
		</td>
	</tr>
	<tr>
		<td>
			3������ѡ�ɳ�������MPA�ɲ�����
		</td>
	</tr>
	<tr>
		<td>
			4��ʡ���е�����Ϊ�øɲ�����
		</td>
	</tr>
	<tr>
		<td>
			5���ٻ��м��������⹫��Ա���йܸɲ�����
		</td>
	</tr>
	<tr>
		<td>
			6���ٻ��м��������㹲����Ա�����㵳�����ߵ��йܸɲ�����
		</td>
	</tr>
	<tr>
		<td>
			7���ٻ��м������Ͷ�ģ�����йܸɲ�����
		</td>
	</tr>
	<tr>
		<td>
			8������ʡί��֯����������ɲ�������ʡ�鷴����
		</td>
	</tr>
	<tr>
		<td>
			9����������������ɲ�������������ʡ�飩
		</td>
	</tr>
	<tr>
		<td>
			10��������ί��֯����������ɲ�����
		</td>
	</tr>
	<tr>
		<td>
			11������ʡί��֯����������ĸɲ�������Ա
		</td>
	</tr>
	<tr>
		<td>
			12��������ί��֯����������ĸɲ�������Ա
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
</table>


</div>

<script type="text/javascript">
function openMate(p){
	$h.openWin('gbmainListWin','pages.amain.GBMainList','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}

</script>


