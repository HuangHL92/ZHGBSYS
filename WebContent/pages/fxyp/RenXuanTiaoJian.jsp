<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	
	String ctxPath = request.getContextPath();
%>
	<style>

.x-panel-bwrap {
	height: 100%
}
.x-panel-body {
	height: 100%
}
.titleCls{
	color: blue;
}
table tr td{
	font-size: 20px;
}
</style>


<div style="float: left;position: relative;">
	<table width="240" cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">����ѡ��</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">�����¼�</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
	</div>

	<div  style="float: left;">
<div id="conditionArea" style="height: 300; overflow-y: scroll;">
	<odin:groupBoxNew title="����" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%;height: 530">
				<tr>
					<odin:textEdit property="a0101" label="��Ա����" maxlength="36" />
					
					<odin:select2 property="a0163" label="��Ա״̬" codeType="ZB126"></odin:select2>
					<odin:select2 property="b0131A" label="�������" data="['01', '��ֱ��ί����'],['02', '��ֱ��������'],['03', '�����е�ί����'],['04', '��������������']" multiSelect="true"></odin:select2>
					
										
				</tr>
				<tr>
					<odin:select2 property="a0165A" label="ʡ�ܸɲ�" data="['02', 'ʡ�ܸɲ�'],['05', 'ʡ��ί���й�']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165B" label="�йܸɲ�" data="['10', '�й���ְ'],['11', '�йܸ�ְ'],['18','��ֱ���飨��ί����Ա����Ѳ'],['19', '�й�ְ������Ա�����˳��쵼��λ��'],['04', '�й�����']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165C" label="�������в㣩�ɲ�" data="['12', '��ֱ����'],['50', '��ֱ����'],['07', '������ְ'],['08', '���ܸ�ְ'],['09', '���й���ְ'],['13', '���йܸ�ְ'],['51','�������ּ���ҵ��ְ'],['60','�������ּ���ҵ�в㸱ְ'],['52','����']" multiSelect="true"></odin:select2>
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01040" name="a0104" type="radio" value="0" />ȫ��</label>
						<label><input id="a01041" name="a0104" type="radio" value="1" />�� </label> 
						<label><input id="a01042" name="a0104" type="radio" value="2" />Ů </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01170" name="a0117" type="radio" value="0" />ȫ��</label>
						<label><input id="a01171" name="a0117" type="radio" value="1" />���� </label> 
						<label><input id="a01172" name="a0117" type="radio" value="2" />�������� </label> 
					</td>
					
					<!-- <td noWrap="nowrap" align=right><span id=a0141SpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01410" name="a0141" type="radio" value="0" />ȫ��</label>
						<label><input id="a01411" name="a0141" type="radio" value="1" />�й���Ա </label> 
						<label><input id="a01412" name="a0141" type="radio" value="2" />���й���Ա</label> 
					</td> -->
					<tags:ComBoxWithTree property="a0141" label="��&nbsp;&nbsp;&nbsp;��" readonly="true" ischecked="true" width="160" codetype="GB4762" />
					
				</tr>
				
				<tr>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:numberEdit property="ageA"  maxlength="3" width="72" />
								<td><span style="font: 12px">��</span></td>
								<odin:numberEdit property="ageB" maxlength="3" width="72" />
							</tr></table>
					</td>
						
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">��������</span>&nbsp;</td>
						<td >
							<table><tr>
								<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
								<td><span style="font: 12px">��</span></td>
								<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
							</tr></table>
					</td> 
					
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="�������޼����ֹ"></odin:NewDateEditTag>
					
				</tr>
				
				<tr>
					<odin:select2 property="xlxw" label="ѧ��ѧλ" data="['��ʿ', '��ʿ'],['˶ʿ', '˶ʿ'],['�о���', '�о���'],['��ѧ,����','��ѧ' ],['��ר,ר��,����,�м�,��ר,Сѧ,����', '��ר������']" multiSelect="true"></odin:select2>
					<td><label style="FONT-SIZE: 12px">ȫ����</label><input id="qrz" name="qrz" type="checkbox" value="1" /> </td>
					<td></td>
					<odin:textEdit property="a0824" label="רҵ��������"  />
					
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0221A" label="��ְ����" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">����ְ����ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="a0192fSpanId" style="FONT-SIZE: 12px">����ְʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192fA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192fB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0192e" label="��ְ��" readonly="true" ischecked="true" width="160" codetype="ZB148" />
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">��ְ��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ���������ְ����</label><input name="a0188" id="a0188" type="checkbox" value="1" /> </td>
					
				</tr>
				
				<tr>
					<odin:select2 property="a0144age" label="����" data="['3', '��������'],['5', '��������']" ></odin:select2>
					<tags:ComBoxWithTree property="a0194c" label="��Ҫ��ְ����" readonly="true" ischecked="true" width="160" codetype="ATTR_LRZW"  />
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ�����������ί���</label><input name="a0132" id="a0132" type="checkbox" value="1" /> </td>
				</tr>
				<tr id="tr_h">
					<odin:select2 property="a0196z" label="רҵ����" codeType="EXTRA_TAGS" multiSelect="true"></odin:select2>
					<odin:select2 property="a0196c" label="��ͷ�ɲ�" codeType="EXTRA_A0196C" multiSelect="true"></odin:select2>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ����򳤣����Σ�</label>
					<input name="a0133" id="a0133" type="checkbox" value="1" /> </td>
				</tr>
				<tr>
					<odin:textEdit property="a1701" label="������������"  />
 					<tags:ComBoxWithTree property="zdgwq" label="�ص��λ" readonly="true" ischecked="true" width="160" codetype="ZDGWBQ"></tags:ComBoxWithTree>
 					<tags:ComBoxWithTree property="a1706" label="�ֹܹ�������" readonly="true" ischecked="true" width="160" codetype="EXTRA_TAGS"></tags:ComBoxWithTree>
				</tr>
				<tr>
					<odin:select2 property="sfwxr" label="��������" data="['01', '����'],['02', '����']" ></odin:select2>
					<tags:ComBoxWithTree property="newRZJL" label="��ְ�������£�" codetype="RZJL2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
					<tags:ComBoxWithTree property="A0194_TAG" label="��Ϥ����" codetype="SXLY2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
				</tr>
				<tr id="tr_h">
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�Ƿ�Ϊѡ����</label>
					<input name="a99z103" id="a99z103" type="checkbox" value="1" /> </td>
					<td noWrap="nowrap" align=right><span id="a99z104SpanId" style="FONT-SIZE: 12px">ѡ��Ϊѡ����ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a99z104A"  maxlength="6" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a99z104B" maxlength="6" width="72" />
						</tr></table>
					</td>
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>

	<%-- <odin:groupBoxNew title="����" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0101" label="��Ա����" maxlength="36" />
					<odin:textEdit property="a0184" label="���֤��" maxlength="18" />
					<tags:PublicOrgCheck label="ѡ�����" property="SysOrgTree"/>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0111" label="����" readonly="true" codename="code_name3" codetype="ZB01"   />
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0104" type="radio" id="a01041" value="1" />�� </label> 
						<label><input name="a0104" type="radio" id="a01042" value="2" />Ů </label> 
						<input  type="button" onclick="$('input[name=a0104]').prop('checked',false)" value="���" />
					</td>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td >
						<table  ><tr>
							<odin:numberEdit property="ageA"  maxlength="3" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:numberEdit property="ageB" maxlength="3" width="72" />
						</tr></table>
					</td>
					<odin:NewDateEditTag property="jiezsj" maxlength="6" isCheck="true" label="�������޼����ֹ"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:select2 property="a0160" label="��Ա���" codeType="ZB125"></odin:select2>
					
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">��������</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<odin:select2 property="a0163" label="��Ա״̬"  codeType="ZB126"></odin:select2>
					
					
				</tr>
				
				<tr>
					 <td noWrap="nowrap" align=right><span id="zhichenSpanId" style="FONT-SIZE: 12px">ְ&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB8561   -->
									<label><input name="a0601" type="radio" id="a06011" value="1" />����</label> 
									<label><input name="a0601" type="radio" id="a06012" value="2" />���� </label> 
									<label><input name="a0601" type="radio" id="a06013" value="3" />�м� </label> 
									<br style="line-height: 1px;" /> 
									<label><input name="a0601" type="radio" id="a060145" value="4,5" />���� </label> 
									<label><input name="a0601" type="radio"  id="a06019" value="9" />��ְ��</label>
									<input  type="button" onclick="$('input[name=a0601]').prop('checked',false)" value="���" />
								</td>
							</tr>
						</table>
					</td> 
					
					<td noWrap="nowrap" align=right><span id="a0144SpanId" style="FONT-SIZE: 12px">�μ��й�ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0144A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0144B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:select2 property="a0141" label="������ò" multiSelect="true" codeType="GB4762"></odin:select2>
					
					
					
				</tr>
				
				
				<tr>
					<odin:textEdit property="a0192a" label="ְ��ȫ��" maxlength="100" >(����)</odin:textEdit>
					
					<td noWrap="nowrap" align=right><span id="a0134SpanId" style="FONT-SIZE: 12px">�μӹ���ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0134A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0134B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0114" label="������" codename="code_name3" codetype="ZB01" readonly="true"  />
				</tr>
				
				
				<tr>
					<tags:ComBoxWithTree property="a0221" label="��ְ����" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0221SpanId" style="FONT-SIZE: 12px">��ְ����</span>&nbsp;</td>
					<td >
						<table><tr>
							<tags:PublicTextIconEdit isLoadData="false"  property="a0221A" label2="��ְ����"  codetype="ZB09" width="72" readonly="true"  />
							<td><span style="font: 12px">��</span></td>
							<tags:PublicTextIconEdit isLoadData="false"  property="a0221B" label2="��ְ����"  codetype="ZB09" width="72" readonly="true"  />
						</tr></table>
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">����ְ����ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="radio" id="a011701" value="01" />���� </label> 
									<label><input name="a0117" type="radio" id="a011702" value="02" />��������</label>
									<input  type="button" onclick="$('input[name=a0117]').prop('checked',false)" value="���" />
								</td>
							</tr>
						</table>
					</td> 
				</tr>
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB09" readonly="true" label="��ְ��"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">��ְ��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:textEdit property="a1701" label="����" maxlength="100" >(����)</odin:textEdit>
				</tr>	
					
				
				<tr>
					<odin:select2 property="a0165" label="��Ա�������" codeType="ZB130" multiSelect="true"></odin:select2>
					<tags:PublicTextIconEdit3  property="a0195" label="ͳ�ƹ�ϵ���ڵ�λ" readonly="true" codetype="orgTreeJsonData"  ></tags:PublicTextIconEdit3>
				</tr>
			</table>
		</div>
	</odin:groupBoxNew> --%>
	

	
	<%-- 
	<odin:groupBoxNew title="ְ��" property="ggBox2" contentEl="zwDiv" collapsible="false">
		<div id="zwDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0216a" label="ְ������" maxlength="100" >(����)</odin:textEdit>
					<odin:select2 property="a0201d" label="�Ƿ��쵼��Ա" codeType="XZ09"></odin:select2>
					<odin:select2 property="a0219" label="�Ƿ��쵼ְ��" codeType="ZB42"></odin:select2>
				</tr>
				
				<tr>
					<odin:select2 property="a0201e" label="��Ա���" codeType="ZB129"></odin:select2>
					
					<td style="FONT-SIZE: 12px">
						<label><input name="qtxzry" id="qtxzry" type="checkbox" value="1" />������ְ��Ա����ְ��</label> 
					</td>
				</tr>
				
				
				
			</table>
		</div>
	</odin:groupBoxNew> --%>
	<%-- <odin:groupBoxNew title="ѧ��ѧλ" property="ggBox3" contentEl="xlDiv"
		collapsible="false">
		<div id="xlDiv">
			<table style="width: 100%">
				<tr>
					<td noWrap="nowrap" align=right><span id="xla0801bSpanId" style="FONT-SIZE: 12px">���ѧ��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB64  -->
									<label><input name="xla0801b" type="radio" id="xla0801b1" value="1" />�о���</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b2" value="2" />��ѧ</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b3" value="3" />��ר</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b4" value="4" />��ר</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b6789" value="6,7,8,9" />����</label> 
	  								<input  type="button" onclick="$('input[name=xla0801b]').prop('checked',false)" value="���" />
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xla0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<tags:ComBoxWithTree property="xla0824" label="רҵ" readonly="true" ischecked="true" codetype="GB16835" />
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="xwa0901bSpanId" style="FONT-SIZE: 12px">���ѧλ</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="xwa0901b" type="radio" id="xwa0901b12" value="1,2" />��ʿ</label> 
									<label><input name="xwa0901b" type="radio" id="xwa0901b3" value="3" />˶ʿ</label> 
									<label><input name="xwa0901b" type="radio" id="xwa0901b4" value="4" />ѧʿ</label> 
									<input  type="button" onclick="$('input[name=xwa0901b]').prop('checked',false)" value="���" />
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xwa0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<tags:ComBoxWithTree property="xwa0824" label="רҵ" readonly="true" ischecked="true" codetype="GB16835" />
					
				</tr>
				
				
				
			
			</table>
		</div>
	</odin:groupBoxNew>
	
		
	<odin:groupBoxNew title="��ͥ��Ա" property="ggBox6" contentEl="jtcyDiv"
		collapsible="false">
		<div id="jtcyDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a3601" label="����" maxlength="100" ></odin:textEdit>
					<odin:hidden property="a3684"  />label="���֤��"  maxlength="18" 
					<odin:textEdit property="a3611" label="������λ��ְ��"  />
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>	 --%>	
			
				
	<table id="ltb">
		<tr>
			<td style="width: 20px"></td>
			
						
		</tr>
	</table>
	
</div>	
	<div id="bottomDiv" style="width: 100%;">
					<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><odin:button text="�ɲ�����" property="gbmcQuery" handler="gbmcQueryBtn"></odin:button>
							</td>
							<td><odin:button text="�������" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<%-- <td><odin:button text="��������" property="saveCon"></odin:button>
							</td> --%>
							<td align="center"><odin:button text="��ʼ��ѯ" property="mQuery" handler="dosearch"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

</div>

<odin:hidden property="fxyp00"/>
<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="sql"/>
<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	window.onresize=resizeframe;
	resizeframe();
})


function tfckbox(checkboxName,hiddenName){
	var checkboxes = document.getElementsByName(checkboxName);
	var hiddenValue = "";
	for (i=0; i<checkboxes.length; i++) {  
        if (checkboxes[i].checked) {  
        	hiddenValue = hiddenValue + checkboxes[i].value+',';
        }  
    }
	if(hiddenValue.length>0){
		hiddenValue = hiddenValue.substring(0,hiddenValue.length-1);
	}
	document.getElementById(hiddenName).value = hiddenValue;
}

function dosearch(){
	document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	/* tfckbox('xla0801b','xla0801bv');
	tfckbox('xwa0901b','xwa0901bv');
	tfckbox('a0601','a0601v'); */
	var param;
	radow.doEvent('mQueryonclick',param);
}

function clearConbtn(condi){
	if(condi!==true){
		condi = "clear";
	}
	Ext.getCmp("a0141_combotree").clearCheck();
	Ext.getCmp("a0221A_combotree").clearCheck();
	Ext.getCmp("a0192e_combotree").clearCheck();
	Ext.getCmp("a0194c_combotree").clearCheck();
	Ext.getCmp("zdgwq_combotree").clearCheck();
	Ext.getCmp("a1706_combotree").clearCheck();
	Ext.getCmp("A0194_TAG_combotree").clearCheck();
	Ext.getCmp("newRZJL_combotree").clearCheck();
	document.getElementById("resetBtn").click();
	radow.doEvent('clearReset');
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	radow.doEvent('initX',condi);
}



function collapseGroupWin(fxyp00){
	 var newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(!newWin_){
	}else{
		//newWin_.collapse(false); 
		newWin_.hide();
		//realParent.infoSearch(fxyp00,true);
		//�� �����й���ѡҳ��
		realParent.openYouGuanRenXuann(fxyp00);
	} 
	//window.close();
}




function resizeframe(){
	var conditionArea = document.getElementById("conditionArea");
	var treediv = document.getElementById("tree-div");
	var viewSize = Ext.getBody().getViewSize();
	conditionArea.style.width = viewSize.width-240;
	conditionArea.style.height = viewSize.height-36;
	var pos = $h.pos(document.getElementById("ltb"));
	//alert(viewSize.height-pos.top);
	//document.getElementById("bottomDiv").style.marginTop = viewSize.height - pos.top-62;
	treediv.style.height = viewSize.height-25;
	
	//alert(conditionArea.parentNode.parentNode.style.width);
	conditionArea.parentNode.parentNode.style.width=viewSize.width;
}


function reloadtree(){
	var treep = Ext.getCmp('group');
	var rootNode = treep.getRootNode();
	rootNode.reload();
	rootNode.expand();
}

function gbmcQueryBtn(){
	$h.openPageModeWin('gbmcQueryMntp','pages.customquery.gbmcQuery_mntp','�ɲ���ѯ�б�',650,480,'','<%=request.getContextPath()%>',window);
}
</script>


<%@include file="/pages/customquery/otjs.jsp" %>

