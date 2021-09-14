<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<style>
.t1{
font-family: 方正小标宋简体;font-size: 22px;
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
			一、重点培养干部名单
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_1')" >1、近期可提拔市管正职干部人选名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_2')" >2、可进一步使用市管干部人选名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_3')" >3、近期可提拔市管副职干部人选名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##1_4')" >4、可关注培养的年轻干部、女干部和党外干部人选名单</a>
		</td>
	</tr>
	<tr>
		<td>
			5、可分层次培养的市管年轻干部和正处年轻干部名单
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td align="center"  class="t1">
			二、日常管理有关名单
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td class="t2">
			（一）领导班子
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_1')" >1、区、县（市）优秀党政领导班子名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_2')" >2、区、县（市）需关注党政领导班子名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_3')" >3、市直单位优秀班子名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_4')" >4、市直单位需关注班子名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_5')" >5、市属国企高校优秀班子名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_1_6')" >6、市属国企高校需关注班子名单</a>
		</td>
	</tr>
	<tr>
		<td class="t2">
			（二）领导干部
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_1')" >1、综合评价比较优秀的市管正职干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_2')" >2、综合评价比较优秀的市管副职干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_3')" >3、综合评价比较优秀的年轻干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_4')" >4、需关注的市管干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_2_5')" >5、需关注的年轻干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			6、上年度以来受处理和目前仍在处理影响期干部名单
		</td>
	</tr>
	<tr>
		<td class="t2">
			（三）分年龄段干部名单
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_1')" >1、1980年以后出生的市管干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_2')" >2、1985年以后出生的有乡镇街道党政正职经历的干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_3')" >3、1990年以后出生的有乡镇街道党政班子成员经历的干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_4')" >4、45岁左右及以下市管干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_5')" >5、40岁左右及以下处级（中层）正职干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##2_3_6')" >6、35岁左右及以下处级中层（副职）干部名单</a>
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td align="center"  class="t1">
			三、重点岗位干部名单
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td class="t2">
			（一）区、县（市）
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_1')" >1、区、县（市）党委书记名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_2')" >2、区、县（市）政府正职名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_3')" >3、区、县（市）党委副书记干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_4')" >4、区、县（市）政府常务干部名单</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_5')" >5、区、县（市）纪委书记</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_6')" >6、区、县（市）委组织部部长</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_7')" >7、区、县（市）委统战部部长</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_8')" >8、区、县（市）委宣传部部长</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_9')" >9、区、县（市）公安局长</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_1_10')" >10、区、县（市）法检两长</a>
		</td>
	</tr>
	<tr>
		<td class="t2">
			（二）市直单位
		</td>
	</tr>
	<tr>
		<td>
			1、市直单位主要领导
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_2_2')" >2、市直单位双副</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:void(0);"  onclick="openMate('gztcymd##3_2_3')" >3、市直单位副书记</a>
		</td>
	</tr>
	
	<tr height="20px">
		<td></td>
	</tr>
	<tr align="center"  class="t1">
		<td>
			四、其他有关干部名单
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
	<tr>
		<td>
			1、有法官、检察官任职资格的干部名单
		</td>
	</tr>
	<tr>
		<td>
			2、有侨眷关系、直系亲属在海外或自己具有海外留学背景的干部名单
		</td>
	</tr>
	<tr>
		<td>
			3、我市选派出国攻读MPA干部名单
		</td>
	</tr>
	<tr>
		<td>
			4、省、市担当作为好干部名单
		</td>
	</tr>
	<tr>
		<td>
			5、荣获市级以上满意公务员的市管干部名单
		</td>
	</tr>
	<tr>
		<td>
			6、荣获市级以上优秀共产党员和优秀党务工作者的市管干部名单
		</td>
	</tr>
	<tr>
		<td>
			7、荣获市级以上劳动模范的市管干部名单
		</td>
	</tr>
	<tr>
		<td>
			8、列入省委组织部优秀年轻干部名单（省组反馈）
		</td>
	</tr>
	<tr>
		<td>
			9、列入市优秀年轻干部调研名单（报省组）
		</td>
	</tr>
	<tr>
		<td>
			10、列入市委组织部优秀年轻干部名单
		</td>
	</tr>
	<tr>
		<td>
			11、列入省委组织部备案管理的干部名单人员
		</td>
	</tr>
	<tr>
		<td>
			12、列入市委组织部备案管理的干部名单人员
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>
</table>


</div>

<script type="text/javascript">
function openMate(p){
	$h.openWin('gbmainListWin','pages.amain.GBMainList','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}

</script>


