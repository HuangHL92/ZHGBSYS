<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/comOpenWinInit.jsp"%>
<%
	String ctxPath = request.getContextPath();

%>
<script type="text/javascript">var cxt_path = "<%=ctxPath%>";</script>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/odin.css" />
<script type="text/javascript" src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<style type="text/css">
	#tablef {
		width: 430px;
		position: relative;
		left: 8px;
	}

	#popDiv {
		display: none;
		width: 765px;
		height: 150px;
		overflow-y: scroll;
		border: 1px #74c0f9 solid;
		background: #FFF;
		position: absolute;
		left: 198px;
		margin-top: 31px;
		color: #323232;
		z-index: 321;
	}
	div#nav {
		width: 100%;
		height: 38px;
		/* margin-bottom: 2px; */
		/* background-color: #9EC2E6; */
	}

	div.top_tab_style {
		min-width: 77px; height : 32px;
		float: left;
		margin: 5px 0 0 2px;
		padding: 0 8px;
		cursor: pointer;
		font-size: 14px;
		font-weight: bold;
		line-height: 35px;
		letter-spacing: 1px;
		background-color: #C0DCF1;
		height: 32px;
	}

	div.top_tab_style.active {
		color: #3680C9;
		background-color: #D6E3F3;
	}

	div.top_tab_style:hover {
		color: #3680C9;
		background-color: #D6E3F3;
	}


	div#bottomDiv .x-btn {
		background-color: #2196f3 !important;
		border-radius: 30px;
		width: 156px !important;
	}

	textarea {
		overflow-y: auto;
	}

	div.pgHead {
		width: 100%;
		margin: 2px 0 5px 0;
		font-size: 14px;
		font-weight: bold;
		line-height: 24px;
		text-align: left;
		text-indent: 6px;
		letter-spacing: 1px;
		background-color: #C0DCF1;
	}
	.labelTd2cls{
		width:1000px;
	}
	.labelTd1cls{
		float: none !important;
		height: 50px !important;
		line-height: 50px !important;
	}
	.labelTd1cls div {
		margin-top: 0;
		padding-top: 0px;
		margin-bottom: 0px;
		padding-bottom: 0px;
		float: none !important;
	}
	.tbClass{
		border: 1px solid #C0DCF1;
	}

	/*.tbClass td{
		border-top: 1px solid #C0DCF1;
	}
*/
	.conditionArea{
		overflow-y: auto;
		overflow-x:hidden;
	}

	td.firstTD{
		border-right: 1px solid #FFF;
		border-left: 1px solid #FFF;
		/*border-bottom: 1px solid #C0DCF1;*/
		border-top: 1px solid #C0DCF1;

	}
	td#headTable{
		border: 1px solid #FFF;
	}

	td.blankTD{
		border-right: 1px solid #FFF;
	}
</style>

<odin:base>
	<odin:hidden property="a0000" />
	<odin:hidden property="oid" />
<form >
<odin:groupBox title="人员信息" property="baseinfo">
<table border="0" id="myform1" align="center" width="100%"  cellpadding="0" cellspacing="0">
	<odin:tabLayOut />
    <tr>
	    <odin:textEdit property="a0101" label="姓名"   disabled="true"  />
        <odin:textEdit property="a0184" label="身份证"  disabled="true" />
        <odin:textEdit property="a0192a" label="工作单位及职务"  disabled="true" />
    </tr>
	<tr>
		<odin:select2 property="audit_type" label="联审类别"  codeType="AUDIT_TYPE" disabled="true" />
		<odin:select2 property="auditResult" label="联审意见" colspan="4"  codeType="AUDIT_IDEA"  />
	</tr>
	<tr>
		<odin:textarea property="auditDetails" rows="2" colspan="6" label="联审结论" ></odin:textarea>
	</tr>
	<tr>
		<odin:textarea property="auditRemark" rows="2" colspan="6" label="备&nbsp;&nbsp;注" ></odin:textarea>
	</tr>
</table>
</odin:groupBox>
	<table id="headTable"  width="100%"  cellpadding="0" cellspacing="0">
		<tr>
			<TD  align="center" width="170px;" bgcolor="#C0DCF1" class="blankTD" ><b>联审单位</b></TD>
			<TD  align="center" width="260px;" bgcolor="#C0DCF1" class="blankTD" ><b>问题类型</b></TD>
			<TD  align="center" width="95px;"  bgcolor="#C0DCF1" class="blankTD" ><b>是否影响</b></TD>
			<TD  align="center" width="95px;"  bgcolor="#C0DCF1" class="blankTD" ><b>反馈结论</b></TD>
			<TD  align="center" width="150px;" bgcolor="#C0DCF1" class="blankTD" ><b>反馈意见</b></TD>
		</tr>
	</table>
	<div id="conditionArea" class="conditionArea">
		<table class="tbClass"  width="100%"  cellpadding="0" cellspacing="0">
			<!-- 一、省纪委省监委
			1、是否涉嫌违纪违法正在接受审查调查；
			2、是否受到党纪政务处分；
			3、是否受到诫勉处理；
			4、是否存在行贿受贿、权钱交易涉案的情形；
			5、是否存在其他不宜推荐为担当作为好干部人选情形。
			 -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="5" ><b>一、省纪委省监委</b>&nbsp;</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1001span">1、是否涉嫌违纪违法正在接受审查调查</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"  width="80px;"  >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1001" codeType="XZ09" width="100" />
					</table>
				</td>
				<td align="left" rowspan="5" valign="middle" class="firstTD"  width="80px;" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p10auresult"   codeType="AUDIT_IDEA" width="100" />
					</table>
				</td>
				<td align="left" rowspan="5" valign="middle"  class="firstTD"  width="150px;" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p10auremark"  rows="9" colspan="2" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD" ><SPAN style="FONT-SIZE: 12px"  id="p1002span">2、是否受到党纪政务处分</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;"  class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1003span">3、是否受到诫勉处理</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1004span">4、是否存在行贿受贿、权钱交易涉案的情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle"  class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0"  border="0">
						<odin:select2 property="p1004" codeType="XZ09" width="100"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1005span">5、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1005" codeType="XZ09" width="100"  />
					</table>
				</td>
			</tr>

			<!-- 二、省委组织部
			（一）干部综合处
				1、是否存在档案造假嫌疑；
				2、是否存在其他不宜推荐为担当作为好干部人选情形。-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b> 二、省委组织部<br/>（一）干部综合处</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20101span">1、是否存在档案造假嫌疑</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20101" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p201auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p201auremark"  rows="5"  colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20102span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20102" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- （二）干部监督室
				1、是否如实填报个人有关事项；
				2、是否受到组织处理；
				3、是否受到诫勉处理；
				4、是否存在其他不宜推荐为担当作为好干部人选情形。 -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="4" ><b> 二、省委组织部<br/>（二）干部监督室</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20201span">1、是否如实填报个人有关事项</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20201" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="4" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p202auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="4" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p202auremark"  rows="7" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20202span">2、是否受到组织处理</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20202" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20203span">3、是否受到诫勉处理</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20203" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20204span">4、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20204" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- 三、省信访局
				1、是否存在组织或参与过集体上访、赴京扰序行为；
				2、是否有对其廉洁自律、工作作风等方面信访反映；
				3、是否存在其他不宜推荐为担当作为好干部人选情形。 -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b> 三、省信访局</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3001span">1、是否存在组织或参与过集体上访、赴京扰序行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p3001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p30auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p30auremark"  rows="4" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3002span">2、是否有对其廉洁自律、工作作风等方面信访反映</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p3002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3003span">3、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p3003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>



			<!-- 四、省法院（本人及配偶、子女）
				1、配偶、子女是否依照法律被剥夺政治权利；
				2、配偶、子女是否因严重违法被判刑或属于刑满释放人员；
				3、本人及配偶、子女是否存在拒不履行司法判决裁定的行为；
				4、是否存在其他不宜推荐为担当作为好干部人选情形。
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="4" ><b> 四、省法院<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4001span">1、配偶、子女是否依照法律被剥夺政治权利</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="4" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p40auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="4" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p40auremark"  rows="7" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4002span">2、配偶、子女是否因严重违法被判刑或属于刑满释放人员</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4003span">3、本人及配偶、子女是否存在拒不履行司法判决裁定的行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4004span">4、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4004" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- 五、省检察院<br/>（本人及配偶、子女）
			1、配偶、子女是否存在被检察机关提起诉讼；
			2、本人及配偶、子女是否存在行贿受贿、权钱交易行为；
			3、是否存在其他不宜推荐为担当作为好干部人选情形。 -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>五、省检察院<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5001span">1、配偶、子女是否存在被检察机关提起诉讼</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p5001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p50auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p50auremark"  rows="6" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5002span">2、本人及配偶、子女是否存在行贿受贿、权钱交易行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p5002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5003span">3、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p5003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- 六、省发改委<br/>（本人及配偶、子女）
				1、是否存在严重失信行为（黑名单）；
				2、是否存在3次以上一般失信行为；
				3、是否存在其他不宜推荐为担当作为好干部人选情形。
				（如存在守信激励[红名单]信息，可一并提供作为推荐担当作为好干部参考。） -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>六、省发改委<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p6001span">1、是否存在严重失信行为（黑名单）</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p6001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p60auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p60auremark"  rows="6" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p6002span">2、是否存在3次以上一般失信行为</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p6002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p6003span">3、是否存在其他不宜推荐为担当作为好干部人选情形。<br/>
				（如存在守信激励[红名单]信息，可一并提供作为推荐担当作为好干部参考。）</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p6003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- 七、省公安厅<br/>（本人及配偶、子女）
				1、是否组织、参加会道门或者邪教组织；
				2、是否参加宗教活动和封建迷信活动造成不良影响；
				3、是否存在严重损害国家和人民利益行为；
				4、是否因严重违法被判刑或属于刑满释放、劳教人员；
				5、是否违反治安管理法律法规受到行政处罚；
				6、是否属于宗族势力、宗教势力、黑恶势力人员；
				7、是否存在其他不宜推荐为担当作为好干部人选情形。
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="7" ><b>七、省公安厅<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p7001span">1、是否组织、参加会道门或者邪教组织</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="7" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p70auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="7" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p70auremark"  rows="12" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7002span">2、是否参加宗教活动和封建迷信活动造成不良影响</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7003span">3、是否存在严重损害国家和人民利益行为</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7004span">4、是否因严重违法被判刑或属于刑满释放、劳教人员</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7004" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7005span">5、是否违反治安管理法律法规受到行政处罚</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7005" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7006span">6、是否属于宗族势力、宗教势力、黑恶势力人员</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7006" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7007span">7、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7007" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<!-- 八、省人力社保厅
				1、所在单位是否存在无故拖欠工资等违反劳动经济法规行为；
				2、是否存在其他不宜推荐为担当作为好干部人选情形。
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>八、省人力社保厅</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p801span">1、所在单位是否存在无故拖欠工资等违反劳动经济法规行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p8001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p80auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p80auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p8002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p8002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<!-- 九、省自然资源厅
			1、是否存在违建行为且拒不整改的情况；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>九、省自然资源厅</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p901span">1、是否存在违建行为且拒不整改的情况</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p9001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p90auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p90auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p9002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p9002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<%--十、省生态环境厅
			1、是否存在因生态环境损害而被追究责任情况；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十、省生态环境厅</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p10001span">1、是否存在因生态环境损害而被追究责任情况</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p10001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p100auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p100auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p10002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p10002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--十一、省卫健委
			1、是否存在违反计划生育政策的情况；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十一、省卫健委</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p11001span">1、是否存在违反计划生育政策的情况</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p11001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p110auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p110auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p11002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p11002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
		<%--	十二、省应急管理厅
			1、是否存在因生产安全事故被追究责任的情况；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十二、省应急管理厅</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p12001span">1、是否存在因生产安全事故被追究责任的情况</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p12001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p120auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p120auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p12002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p12002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--十三、省审计厅
			1、是否存在搞政绩工程、形象工程的行为；
			2、是否存在严重违规决策行为；
			3、是否存在其他不宜推荐为担当作为好干部人选情形。--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>十三、省审计厅</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p13001span">1、是否存在搞政绩工程、形象工程的行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p13001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p130auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p130auremark"  rows="5" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p13002span">2、是否存在严重违规决策行为</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p13002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p13003span">3、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p13003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<%--十四、省市场监管局（本人及配偶、子女）
			1、本人是否存在经商办企业情况；
			2、配偶、子女投资的企业是否被列入异常经营名录或严重违法失信企业名录；
			3、是否存在其他不宜推荐为担当作为好干部人选情形。--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>十四、省市场监管局<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p14001span">1、本人是否存在经商办企业情况</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p14001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p140auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="3" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p140auremark"  rows="6" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p14002span">2、配偶、子女投资的企业是否被列入异常经营名录或严重违法失信企业名录</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p14002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p14003span">3、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p14003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--十五、省统计局
			1、是否存在搞数据造假、数字注水行为；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十五、省统计局</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p15001span">1、是否存在搞数据造假、数字注水行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p15001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p150auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p150auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p15002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p15002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--十六、省总工会
			1、是否存在不和谐劳动关系；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十六、省总工会</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p16001span">1、是否存在不和谐劳动关系</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p16001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p160auresult"   codeType="AUDIT_IDEA" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p160auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p16002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p16002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--十七、国家税务总局浙江省税务局（本人及配偶、子女）
			1、配偶、子女及其担任法定代表人的企业是否存在重大税务违法行为；
			2、是否存在其他不宜推荐为担当作为好干部人选情形。--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>十七、国家税务总局浙江省税务局<br/>（本人及配偶、子女）</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p17001span">1、配偶、子女及其担任法定代表人的企业是否存在重大税务违法行为</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"    >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p17001" codeType="XZ09" width="100"  />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0">
						<odin:select2 property="p170auresult"   codeType="AUDIT_IDEA" width="100" />
					</table>
				</td>
				<td align="left" rowspan="2" valign="middle"  class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:textarea property="p170auremark"  rows="3" colspan="2"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p17002span">2、是否存在其他不宜推荐为担当作为好干部人选情形</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p17002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td>
								<odin:button text="保存" handler="doSave" ></odin:button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</form>

<script type="text/javascript" src="<%=ctxPath%>/js/RelateSearch.js"></script>
<script>


	var clientHeight = windowHeight();

	function windowHeight() {
		var myHeight = 0;
		if (typeof(window.innerHeight) == 'number') {
			//Non-IE
			myHeight = window.innerHeight;
		} else if (document.documentElement && (document.documentElement.clientHeight)) {
			//IE 6+ in 'standards compliant mode'
			myHeight = document.documentElement.clientHeight;
		} else if (document.body && (document.body.clientHeight)) {
			//IE 4 compatible
			myHeight = document.body.clientHeight;
		}
		return myHeight;
	}

Ext.onReady(function (){
	window.onresize=resizeframe;
	resizeframe();
})

function doCloseWin(){
	parent.odin.ext.getCmp('lsData').close();
}


function doSave() {

	radow.doEvent('saveLsDatas', null);
}


function resizeframe() {

	var baseinfo = document.getElementById("baseinfo").offsetHeight;
	var headTable = document.getElementById("headTable").offsetHeight;
	$("#conditionArea").height(clientHeight-baseinfo-headTable-50);
}


function saveCallBack(msg){
	odin.alert(msg,function(){
		window.realParent.radow.doEvent('unitreGrid.dogridquery');
		doCloseWin();
	});
}

</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>