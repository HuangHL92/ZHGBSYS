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
<odin:groupBox title="��Ա��Ϣ" property="baseinfo">
<table border="0" id="myform1" align="center" width="100%"  cellpadding="0" cellspacing="0">
	<odin:tabLayOut />
    <tr>
	    <odin:textEdit property="a0101" label="����"   disabled="true"  />
        <odin:textEdit property="a0184" label="���֤"  disabled="true" />
        <odin:textEdit property="a0192a" label="������λ��ְ��"  disabled="true" />
    </tr>
	<tr>
		<odin:select2 property="audit_type" label="�������"  codeType="AUDIT_TYPE" disabled="true" />
		<odin:select2 property="auditResult" label="�������" colspan="4"  codeType="AUDIT_IDEA"  />
	</tr>
	<tr>
		<odin:textarea property="auditDetails" rows="2" colspan="6" label="�������" ></odin:textarea>
	</tr>
	<tr>
		<odin:textarea property="auditRemark" rows="2" colspan="6" label="��&nbsp;&nbsp;ע" ></odin:textarea>
	</tr>
</table>
</odin:groupBox>
	<table id="headTable"  width="100%"  cellpadding="0" cellspacing="0">
		<tr>
			<TD  align="center" width="170px;" bgcolor="#C0DCF1" class="blankTD" ><b>����λ</b></TD>
			<TD  align="center" width="260px;" bgcolor="#C0DCF1" class="blankTD" ><b>��������</b></TD>
			<TD  align="center" width="95px;"  bgcolor="#C0DCF1" class="blankTD" ><b>�Ƿ�Ӱ��</b></TD>
			<TD  align="center" width="95px;"  bgcolor="#C0DCF1" class="blankTD" ><b>��������</b></TD>
			<TD  align="center" width="150px;" bgcolor="#C0DCF1" class="blankTD" ><b>�������</b></TD>
		</tr>
	</table>
	<div id="conditionArea" class="conditionArea">
		<table class="tbClass"  width="100%"  cellpadding="0" cellspacing="0">
			<!-- һ��ʡ��ίʡ��ί
			1���Ƿ�����Υ��Υ�����ڽ��������飻
			2���Ƿ��ܵ��������񴦷֣�
			3���Ƿ��ܵ����㴦��
			4���Ƿ�����л��ܻߡ�ȨǮ�����永�����Σ�
			5���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
			 -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="5" ><b>һ��ʡ��ίʡ��ί</b>&nbsp;</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1001span">1���Ƿ�����Υ��Υ�����ڽ���������</SPAN>&nbsp;</TD>
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
				<TD align="left" width="250px;" class="firstTD" ><SPAN style="FONT-SIZE: 12px"  id="p1002span">2���Ƿ��ܵ��������񴦷�</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;"  class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1003span">3���Ƿ��ܵ����㴦��</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1004span">4���Ƿ�����л��ܻߡ�ȨǮ�����永������</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle"  class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0"  border="0">
						<odin:select2 property="p1004" codeType="XZ09" width="100"  />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px"  id="p1005span">5���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"  >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p1005" codeType="XZ09" width="100"  />
					</table>
				</td>
			</tr>

			<!-- ����ʡί��֯��
			��һ���ɲ��ۺϴ�
				1���Ƿ���ڵ���������ɣ�
				2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b> ����ʡί��֯��<br/>��һ���ɲ��ۺϴ�</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20101span">1���Ƿ���ڵ����������</SPAN>&nbsp;</TD>
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
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20102span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20102" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- �������ɲ��ල��
				1���Ƿ���ʵ������й����
				2���Ƿ��ܵ���֯����
				3���Ƿ��ܵ����㴦��
				4���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ� -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="4" ><b> ����ʡί��֯��<br/>�������ɲ��ල��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20201span">1���Ƿ���ʵ������й�����</SPAN>&nbsp;</TD>
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
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20202span">2���Ƿ��ܵ���֯����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20202" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20203span">3���Ƿ��ܵ����㴦��</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20203" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p20204span">4���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p20204" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- ����ʡ�ŷþ�
				1���Ƿ������֯�����������Ϸá�����������Ϊ��
				2���Ƿ��ж����������ɡ���������ȷ����ŷ÷�ӳ��
				3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ� -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b> ����ʡ�ŷþ�</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3001span">1���Ƿ������֯�����������Ϸá�����������Ϊ</SPAN>&nbsp;</TD>
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
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3002span">2���Ƿ��ж����������ɡ���������ȷ����ŷ÷�ӳ</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p3002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p3003span">3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p3003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>



			<!-- �ġ�ʡ��Ժ�����˼���ż����Ů��
				1����ż����Ů�Ƿ����շ��ɱ���������Ȩ����
				2����ż����Ů�Ƿ�������Υ�������̻����������ͷ���Ա��
				3�����˼���ż����Ů�Ƿ���ھܲ�����˾���о��ö�����Ϊ��
				4���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="4" ><b> �ġ�ʡ��Ժ<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4001span">1����ż����Ů�Ƿ����շ��ɱ���������Ȩ��</SPAN>&nbsp;</TD>
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
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4002span">2����ż����Ů�Ƿ�������Υ�������̻����������ͷ���Ա</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4003span">3�����˼���ż����Ů�Ƿ���ھܲ�����˾���о��ö�����Ϊ</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p4004span">4���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p4004" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- �塢ʡ���Ժ<br/>�����˼���ż����Ů��
			1����ż����Ů�Ƿ���ڱ��������������ϣ�
			2�����˼���ż����Ů�Ƿ�����л��ܻߡ�ȨǮ������Ϊ��
			3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ� -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>�塢ʡ���Ժ<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5001span">1����ż����Ů�Ƿ���ڱ���������������</SPAN>&nbsp;</TD>
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
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5002span">2�����˼���ż����Ů�Ƿ�����л��ܻߡ�ȨǮ������Ϊ</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p5002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p5003span">3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p5003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- ����ʡ����ί<br/>�����˼���ż����Ů��
				1���Ƿ��������ʧ����Ϊ������������
				2���Ƿ����3������һ��ʧ����Ϊ��
				3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
				����������ż���[������]��Ϣ����һ���ṩ��Ϊ�Ƽ�������Ϊ�øɲ��ο����� -->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>����ʡ����ί<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p6001span">1���Ƿ��������ʧ����Ϊ����������</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p6002span">2���Ƿ����3������һ��ʧ����Ϊ</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p6002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p6003span">3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�<br/>
				����������ż���[������]��Ϣ����һ���ṩ��Ϊ�Ƽ�������Ϊ�øɲ��ο�����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p6003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<!-- �ߡ�ʡ������<br/>�����˼���ż����Ů��
				1���Ƿ���֯���μӻ���Ż���а����֯��
				2���Ƿ�μ��ڽ̻�ͷ⽨���Ż��ɲ���Ӱ�죻
				3���Ƿ���������𺦹��Һ�����������Ϊ��
				4���Ƿ�������Υ�������̻����������ͷš��ͽ���Ա��
				5���Ƿ�Υ���ΰ������ɷ����ܵ�����������
				6���Ƿ����������������ڽ��������ڶ�������Ա��
				7���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="7" ><b>�ߡ�ʡ������<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p7001span">1���Ƿ���֯���μӻ���Ż���а����֯</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p7002span">2���Ƿ�μ��ڽ̻�ͷ⽨���Ż��ɲ���Ӱ��</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7003span">3���Ƿ���������𺦹��Һ�����������Ϊ</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7004span">4���Ƿ�������Υ�������̻����������ͷš��ͽ���Ա</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7004" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7005span">5���Ƿ�Υ���ΰ������ɷ����ܵ���������</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7005" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7006span">6���Ƿ����������������ڽ��������ڶ�������Ա</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7006" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p7007span">7���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p7007" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<!-- �ˡ�ʡ�����籣��
				1�����ڵ�λ�Ƿ�����޹���Ƿ���ʵ�Υ���Ͷ����÷�����Ϊ��
				2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>�ˡ�ʡ�����籣��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p801span">1�����ڵ�λ�Ƿ�����޹���Ƿ���ʵ�Υ���Ͷ����÷�����Ϊ</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p8002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p8002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<!-- �š�ʡ��Ȼ��Դ��
			1���Ƿ����Υ����Ϊ�Ҿܲ����ĵ������
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�
			-->
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>�š�ʡ��Ȼ��Դ��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p901span">1���Ƿ����Υ����Ϊ�Ҿܲ����ĵ����</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p9002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD"   >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p9002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>


			<%--ʮ��ʡ��̬������
			1���Ƿ��������̬�����𺦶���׷�����������
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮ��ʡ��̬������</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p10001span">1���Ƿ��������̬�����𺦶���׷���������</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p10002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p10002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--ʮһ��ʡ����ί
			1���Ƿ����Υ���ƻ��������ߵ������
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮһ��ʡ����ί</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p11001span">1���Ƿ����Υ���ƻ��������ߵ����</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p11002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p11002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
		<%--	ʮ����ʡӦ��������
			1���Ƿ������������ȫ�¹ʱ�׷�����ε������
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮ����ʡӦ��������</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p12001span">1���Ƿ������������ȫ�¹ʱ�׷�����ε����</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p12002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p12002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--ʮ����ʡ�����
			1���Ƿ���ڸ��������̡����󹤳̵���Ϊ��
			2���Ƿ��������Υ�������Ϊ��
			3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>ʮ����ʡ�����</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p13001span">1���Ƿ���ڸ��������̡����󹤳̵���Ϊ</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p13002span">2���Ƿ��������Υ�������Ϊ</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p13002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p13003span">3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p13003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<%--ʮ�ġ�ʡ�г���ܾ֣����˼���ż����Ů��
			1�������Ƿ���ھ��̰���ҵ�����
			2����ż����ŮͶ�ʵ���ҵ�Ƿ������쳣��Ӫ��¼������Υ��ʧ����ҵ��¼��
			3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="3" ><b>ʮ�ġ�ʡ�г���ܾ�<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p14001span">1�������Ƿ���ھ��̰���ҵ���</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p14002span">2����ż����ŮͶ�ʵ���ҵ�Ƿ������쳣��Ӫ��¼������Υ��ʧ����ҵ��¼</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p14002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>
			<tr>
				<TD align="left" width="250px;" class="firstTD"  >
					<SPAN style="FONT-SIZE: 12px" id="p14003span">3���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p14003" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--ʮ�塢ʡͳ�ƾ�
			1���Ƿ���ڸ�������١�����עˮ��Ϊ��
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>

			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮ�塢ʡͳ�ƾ�</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p15001span">1���Ƿ���ڸ�������١�����עˮ��Ϊ</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p15002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p15002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--ʮ����ʡ�ܹ���
			1���Ƿ���ڲ���г�Ͷ���ϵ��
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮ����ʡ�ܹ���</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p16001span">1���Ƿ���ڲ���г�Ͷ���ϵ</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p16002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
				</TD>
				<td align="left"  valign="middle" class="firstTD" >
					<table width="100%"  cellpadding="0" cellspacing="0" border="0">
						<odin:select2 property="p16002" codeType="XZ09" width="100" />
					</table>
				</td>
			</tr>

			<%--ʮ�ߡ�����˰���ܾ��㽭ʡ˰��֣����˼���ż����Ů��
			1����ż����Ů���䵣�η��������˵���ҵ�Ƿ�����ش�˰��Υ����Ϊ��
			2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ���Ρ�--%>
			<tr>
				<TD align="center" valign="middle" width="180px;" class="firstTD" rowspan="2" ><b>ʮ�ߡ�����˰���ܾ��㽭ʡ˰���<br/>�����˼���ż����Ů��</b>&nbsp</TD>
				<TD align="left" width="280px;" class="firstTD"  ><SPAN style="FONT-SIZE: 12px" id="p17001span">1����ż����Ů���䵣�η��������˵���ҵ�Ƿ�����ش�˰��Υ����Ϊ</SPAN>&nbsp;</TD>
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
					<SPAN style="FONT-SIZE: 12px" id="p17002span">2���Ƿ�������������Ƽ�Ϊ������Ϊ�øɲ���ѡ����</SPAN>&nbsp;
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
								<odin:button text="����" handler="doSave" ></odin:button>
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