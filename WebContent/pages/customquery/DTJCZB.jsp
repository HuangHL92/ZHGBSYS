<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>

<script  type="text/javascript">

</script>
<style>
table
{
border-collapse:collapse;
}
td {
border: 1px solid rgb(0,0,0);
font-family: ����;
font-weight: normal;
font-size: 16px;
padding-top: 4px;
padding-bottom: 4px;
}
td[class^="count"]{
    color: red;
}
</style>
<div align="center">
<table style="width:96%;border:1px solid;">
	<tr>
		<td colspan="7" align="center" style="font-family: ����С���μ���;font-size: 28px;font-weight: normal;padding-top: 8px;padding-bottom: 8px;position: relative;">
			ѡ�����˸�������չ�������۶�̬���ָ��
			<button type="button" class="btn btn-primary btn-mini" onclick="openYJTX();" 
			style="margin-top: 2px;  line-height: 14px; padding: 4px 5px;">ָ������</button>
		</td>
	</tr>
	<tr>
		<td style="width: 3%;background: #CAE8EA;font-weight:bold;"  align="center">���</td>
		<td style="width: 36%;background: #CAE8EA;font-weight:bold;"  align="center">ָ������</td>
		<td style="width: 10%;background: #CAE8EA;font-weight:bold;" align="center">ָ����</td>
		<td style="width: 1%;" rowspan="15"></td>
		<td style="width: 3%;background: #CAE8EA;font-weight:bold;" align="center">���</td>
		<td style="width: 36%;background: #CAE8EA;font-weight:bold;" align="center">ָ������</td>
		<td style="width: 10%;background: #CAE8EA;font-weight:bold;" align="center">ָ����</td>
	</tr>
	<tr  align="center">
		<td>1</td>
		<td align="left">�쵼�����ƽ���������չ�ۺϼ�Ч�������������Σ�</td>
		<td></td>
		<td>2</td>
		<td align="left">��ֱ��λ�쵼���ӳ�Ա���й��쵼�ɲ�����45�����ҡ�40�����Ҽ����¸ɲ��ֱ�ռ�ȣ�%��</td>
		<td class="count2" ></td>
	</tr>
	<tr align="center">
		<td>3</td>
		<td align="left">��ֱ��λ�����쵼�ɲ���40�����¡�35�����Ҽ����¸ɲ��ֱ�ռ�ȣ�%��</td>
		<td  class="count3"></td>
		<td>4</td>
		<td align="left">�ؼ�������ְ��40�����Ҹɲ�ռ�ȣ�%��</td>
		<td  class="count4">3.85%</td>
	</tr>
	<tr align="center">
		<td>5</td>
		<td align="left">�ؼ������쵼������40�����Ҽ����¸ɲ�������%��</td>
		<td class="count5">17.8%</td>
		<td>6</td>
		<td align="left">�����쵼������35�����¸ɲ�������%��</td>
		<td class="count6">9.4%</td>
	</tr>
	<tr align="center">
		<td>7</td>
		<td align="left">ÿ������30�����������쵼���ӳ�Ա�䱸����������</td>
		<td class="count7">8</td>
		<td>8</td>
		<td align="left">��������ְ��35�����¸ɲ�������%��</td>
		<td class="count8">6.7%</td>
	</tr>
	<tr align="center">
		<td>9</td>
		<td align="left">35��������������ְ�У�����ί���ռ�ȣ�%��</td>
		<td class="count9">7.69%</td>
		<td>10</td>
		<td align="left">��¼ѡ������������</td>
		<td>170��2021�꣩</td>
	</tr>
	<tr align="center">
		<td>11</td>
		<td align="left">רҵ�Խ�ǿ�Ļ��ز����쵼���ӳ�Ա��רҵ�͸ɲ��䱸������%��</td>
		<td>60.79%</td>
		<td>12</td>
		<td align="left">����������쵼�ɲ�ȫ���ƴ�ѧ�������ϵ�ռ�ȣ�%��</td>
		<td  class="count12">46.21%</td>
	</tr>
	<tr align="center">
		<td>13</td>
		<td align="left">������40�������й��쵼�ɲ���ȫ���ƴ�ѧ��������ѧ����Առ�ȣ�%��</td>
		<td class="count13"></td>
		<td>14</td>
		<td align="left">������40��������ֱ��λ�����쵼�ɲ���ȫ���ƴ�ѧ��������ѧ����Առ�ȣ�%��</td>
		<td class="count14">84.17%</td>
	</tr>
	<tr align="center">
		<td>15</td>
		<td align="left">������40���������أ��У����쵼�ɲ���ȫ���ƴ�ѧ��������ѧ����Առ��</td>
		<td class="count15"></td>
		<td>16</td>
		<td align="left">����Χ�ڣ��ؼ������쵼���ӳ�Ա�У��������򣨽ֵ���������ְ�����Ĵﵽ50%���ϵ��أ��С�����ռ�ȣ�%��</td>
		<td class="count16">61.54</td>
	</tr>
	<tr align="center">
		<td>17</td>
		<td align="left">�ؼ���ί�쵼�����У�Ů�ɲ��䱸����������</td>
		<td class="count17">26</td>
		<td>18</td>
		<td align="left">�ؼ������쵼�����У�Ů�ɲ��䱸����������</td>
		<td class="count18">13</td>
	</tr>
	<tr align="center">
		<td>19</td>
		<td align="left">�м��������������У��䱸Ů�ɲ����쵼����ռ�ȣ�%��</td>
		<td class="count19">77.77%</td>
		<td>20</td>
		<td align="left">�ؼ���Э�쵼�����У�����ɲ�ռ��</td>
		<td class="count20">41.18%</td>
	</tr>
	<tr align="center">
		<td>21</td>
		<td align="left">���������쵼���ӿ�ȱְ��ռ��ְ��������%��</td>
		<td class="count21">1.33%/9.77%</td>
		<td>22</td>
		<td align="left">ȱ�����������ϵĹؼ���λ�쵼�ɲ���������</td>
		<td>0</td>
	</tr>
	<tr align="center">
		<td>23</td>
		<td align="left">���걻�鴦�ı�������ɲ���Ϊ��5�������εı���</td>
		<td>0</td>
		<td>24</td>
		<td align="left">��һ���������顱ѡ�����˹�������ȣ�%��</td>
		<td>97.84%��2020�꣩</td>
	</tr>
	<tr align="center">
		<td>25</td>
		<td align="left">�����й�����˲鲻һ���ʣ�%��</td>
		<td>6.65%��2020�꣩</td>
		<td>26</td>
		<td align="left">�쵼�ɲ�ÿ��ÿ��μ�����ѧԺ��ѵѧʱ����ѧʱ��</td>
		<td>2020���Ѵ��</td>
	</tr>
	<tr align="center">
		<td>27</td>
		<td align="left">�С��أ��С�������ί����ĸɲ����µ������ֻ��ʣ�%��</td>
		<td>95%</td>
		<td>28</td>
		<td align="left">ѡ�������ۺ�Ӧ��ʡ���ع�ͨ�ʣ�%��</td>
		<td>100%</td>
	</tr>
	
	
</table>


</div>

<script type="text/javascript">
function openYJTX(){
	$h.openWin('gbmainZBXQWin','pages.amain.GBMainZBXQ','��ָ̬������',1380, 740,'','<%=request.getContextPath()%>',null,{},true);
}

</script>


