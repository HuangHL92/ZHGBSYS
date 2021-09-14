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
font-family: 宋体;
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
		<td colspan="7" align="center" style="font-family: 方正小标宋简体;font-size: 28px;font-weight: normal;padding-top: 8px;padding-bottom: 8px;position: relative;">
			选人用人高质量发展考核评价动态监测指标
			<button type="button" class="btn btn-primary btn-mini" onclick="openYJTX();" 
			style="margin-top: 2px;  line-height: 14px; padding: 4px 5px;">指标详情</button>
		</td>
	</tr>
	<tr>
		<td style="width: 3%;background: #CAE8EA;font-weight:bold;"  align="center">序号</td>
		<td style="width: 36%;background: #CAE8EA;font-weight:bold;"  align="center">指标名称</td>
		<td style="width: 10%;background: #CAE8EA;font-weight:bold;" align="center">指标结果</td>
		<td style="width: 1%;" rowspan="15"></td>
		<td style="width: 3%;background: #CAE8EA;font-weight:bold;" align="center">序号</td>
		<td style="width: 36%;background: #CAE8EA;font-weight:bold;" align="center">指标名称</td>
		<td style="width: 10%;background: #CAE8EA;font-weight:bold;" align="center">指标结果</td>
	</tr>
	<tr  align="center">
		<td>1</td>
		<td align="left">领导班子推进高质量发展综合绩效考核排名（名次）</td>
		<td></td>
		<td>2</td>
		<td align="left">市直单位领导班子成员（市管领导干部）中45岁左右、40岁左右及以下干部分别占比（%）</td>
		<td class="count2" ></td>
	</tr>
	<tr align="center">
		<td>3</td>
		<td align="left">市直单位处级领导干部中40岁以下、35岁左右及以下干部分别占比（%）</td>
		<td  class="count3"></td>
		<td>4</td>
		<td align="left">县级党政正职中40岁左右干部占比（%）</td>
		<td  class="count4">3.85%</td>
	</tr>
	<tr align="center">
		<td>5</td>
		<td align="left">县级党政领导班子中40岁左右及以下干部比例（%）</td>
		<td class="count5">17.8%</td>
		<td>6</td>
		<td align="left">乡镇领导班子中35岁以下干部比例（%）</td>
		<td class="count6">9.4%</td>
	</tr>
	<tr align="center">
		<td>7</td>
		<td align="left">每个乡镇30岁以下乡镇领导班子成员配备数量（名）</td>
		<td class="count7">8</td>
		<td>8</td>
		<td align="left">乡镇党政正职中35岁以下干部比例（%）</td>
		<td class="count8">6.7%</td>
	</tr>
	<tr align="center">
		<td>9</td>
		<td align="left">35岁以下乡镇党政正职中，乡镇党委书记占比（%）</td>
		<td class="count9">7.69%</td>
		<td>10</td>
		<td align="left">招录选调生数（名）</td>
		<td>170（2021年）</td>
	</tr>
	<tr align="center">
		<td>11</td>
		<td align="left">专业性较强的机关部门领导班子成员中专业型干部配备比例（%）</td>
		<td>60.79%</td>
		<td>12</td>
		<td align="left">本级管理的领导干部全日制大学本科以上的占比（%）</td>
		<td  class="count12">46.21%</td>
	</tr>
	<tr align="center">
		<td>13</td>
		<td align="left">新提任40岁以下市管领导干部，全日制大学本科以上学历人员占比（%）</td>
		<td class="count13"></td>
		<td>14</td>
		<td align="left">新提任40岁以下市直单位处级领导干部，全日制大学本科以上学历人员占比（%）</td>
		<td class="count14">84.17%</td>
	</tr>
	<tr align="center">
		<td>15</td>
		<td align="left">新提任40岁以下区县（市）管领导干部，全日制大学本科以上学历人员占比</td>
		<td class="count15"></td>
		<td>16</td>
		<td align="left">市域范围内，县级党政领导班子成员中，具有乡镇（街道）党政正职经历的达到50%以上的县（市、区）占比（%）</td>
		<td class="count16">61.54</td>
	</tr>
	<tr align="center">
		<td>17</td>
		<td align="left">县级党委领导班子中，女干部配备数量（名）</td>
		<td class="count17">26</td>
		<td>18</td>
		<td align="left">县级政府领导班子中，女干部配备数量（名）</td>
		<td class="count18">13</td>
	</tr>
	<tr align="center">
		<td>19</td>
		<td align="left">市级党政工作部门中，配备女干部的领导班子占比（%）</td>
		<td class="count19">77.77%</td>
		<td>20</td>
		<td align="left">县级政协领导班子中，党外干部占比</td>
		<td class="count20">41.18%</td>
	</tr>
	<tr align="center">
		<td>21</td>
		<td align="left">本级管理领导班子空缺职数占总职数比例（%）</td>
		<td class="count21">1.33%/9.77%</td>
		<td>22</td>
		<td align="left">缺配三个月以上的关键岗位领导干部数（名）</td>
		<td>0</td>
	</tr>
	<tr align="center">
		<td>23</td>
		<td align="left">当年被查处的本级管理干部中为近5年新提任的比例</td>
		<td>0</td>
		<td>24</td>
		<td align="left">“一报告两评议”选人用人工作满意度（%）</td>
		<td>97.84%（2020年）</td>
	</tr>
	<tr align="center">
		<td>25</td>
		<td align="left">个人有关事项核查不一致率（%）</td>
		<td>6.65%（2020年）</td>
		<td>26</td>
		<td align="left">领导干部每人每年参加网络学院培训学时数（学时）</td>
		<td>2020年已达标</td>
	</tr>
	<tr align="center">
		<td>27</td>
		<td align="left">市、县（市、区）党委管理的干部人事档案数字化率（%）</td>
		<td>95%</td>
		<td>28</td>
		<td align="left">选人用人综合应用省市县贯通率（%）</td>
		<td>100%</td>
	</tr>
	
	
</table>


</div>

<script type="text/javascript">
function openYJTX(){
	$h.openWin('gbmainZBXQWin','pages.amain.GBMainZBXQ','动态指标详情',1380, 740,'','<%=request.getContextPath()%>',null,{},true);
}

</script>


