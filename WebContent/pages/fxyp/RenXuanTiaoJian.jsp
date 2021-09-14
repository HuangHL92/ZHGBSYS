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
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
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
	<odin:groupBoxNew title="基本" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%;height: 530">
				<tr>
					<odin:textEdit property="a0101" label="人员姓名" maxlength="36" />
					
					<odin:select2 property="a0163" label="人员状态" codeType="ZB126"></odin:select2>
					<odin:select2 property="b0131A" label="机构类别" data="['01', '市直党委部门'],['02', '市直政府部门'],['03', '区县市党委部门'],['04', '区县市政府部门']" multiSelect="true"></odin:select2>
					
										
				</tr>
				<tr>
					<odin:select2 property="a0165A" label="省管干部" data="['02', '省管干部'],['05', '省管委托市管']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165B" label="市管干部" data="['10', '市管正职'],['11', '市管副职'],['18','市直党组（党委）成员、二巡'],['19', '市管职级公务员（含退出领导岗位）'],['04', '市管其他']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165C" label="处级（中层）干部" data="['12', '市直正处'],['50', '市直副处'],['07', '区管正职'],['08', '区管副职'],['09', '县市管正职'],['13', '县市管副职'],['51','市属副局级企业副职'],['60','市属副局级企业中层副职'],['52','其他']" multiSelect="true"></odin:select2>
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">性&nbsp;&nbsp;&nbsp;别</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01040" name="a0104" type="radio" value="0" />全部</label>
						<label><input id="a01041" name="a0104" type="radio" value="1" />男 </label> 
						<label><input id="a01042" name="a0104" type="radio" value="2" />女 </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">民&nbsp;&nbsp;&nbsp;族</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01170" name="a0117" type="radio" value="0" />全部</label>
						<label><input id="a01171" name="a0117" type="radio" value="1" />汉族 </label> 
						<label><input id="a01172" name="a0117" type="radio" value="2" />少数民族 </label> 
					</td>
					
					<!-- <td noWrap="nowrap" align=right><span id=a0141SpanId style="FONT-SIZE: 12px">党&nbsp;&nbsp;&nbsp;派</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01410" name="a0141" type="radio" value="0" />全部</label>
						<label><input id="a01411" name="a0141" type="radio" value="1" />中共党员 </label> 
						<label><input id="a01412" name="a0141" type="radio" value="2" />非中共党员</label> 
					</td> -->
					<tags:ComBoxWithTree property="a0141" label="党&nbsp;&nbsp;&nbsp;派" readonly="true" ischecked="true" width="160" codetype="GB4762" />
					
				</tr>
				
				<tr>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">年&nbsp;&nbsp;&nbsp;龄</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:numberEdit property="ageA"  maxlength="3" width="72" />
								<td><span style="font: 12px">至</span></td>
								<odin:numberEdit property="ageB" maxlength="3" width="72" />
							</tr></table>
					</td>
						
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">出生日期</span>&nbsp;</td>
						<td >
							<table><tr>
								<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
								<td><span style="font: 12px">至</span></td>
								<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
							</tr></table>
					</td> 
					
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="年龄年限计算截止"></odin:NewDateEditTag>
					
				</tr>
				
				<tr>
					<odin:select2 property="xlxw" label="学历学位" data="['博士', '博士'],['硕士', '硕士'],['研究生', '研究生'],['大学,本科','大学' ],['大专,专科,高中,中技,中专,小学,初中', '大专及以下']" multiSelect="true"></odin:select2>
					<td><label style="FONT-SIZE: 12px">全日制</label><input id="qrz" name="qrz" type="checkbox" value="1" /> </td>
					<td></td>
					<odin:textEdit property="a0824" label="专业（包含）"  />
					
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0221A" label="现职务层次" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">任现职务层次时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="a0192fSpanId" style="FONT-SIZE: 12px">任现职时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192fA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192fB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0192e" label="现职级" readonly="true" ischecked="true" width="160" codetype="ZB148" />
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">任职级时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">具有乡镇（街道）党政正职经历</label><input name="a0188" id="a0188" type="checkbox" value="1" /> </td>
					
				</tr>
				
				<tr>
					<odin:select2 property="a0144age" label="党龄" data="['3', '三年以上'],['5', '五年以上']" ></odin:select2>
					<tags:ComBoxWithTree property="a0194c" label="重要任职经历" readonly="true" ischecked="true" width="160" codetype="ATTR_LRZW"  />
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">现任乡镇（街道）党（工）委书记</label><input name="a0132" id="a0132" type="checkbox" value="1" /> </td>
				</tr>
				<tr id="tr_h">
					<odin:select2 property="a0196z" label="专业类型" codeType="EXTRA_TAGS" multiSelect="true"></odin:select2>
					<odin:select2 property="a0196c" label="两头干部" codeType="EXTRA_A0196C" multiSelect="true"></odin:select2>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">现任乡镇（街道）镇长（主任）</label>
					<input name="a0133" id="a0133" type="checkbox" value="1" /> </td>
				</tr>
				<tr>
					<odin:textEdit property="a1701" label="简历（包含）"  />
 					<tags:ComBoxWithTree property="zdgwq" label="重点岗位" readonly="true" ischecked="true" width="160" codetype="ZDGWBQ"></tags:ComBoxWithTree>
 					<tags:ComBoxWithTree property="a1706" label="分管工作类型" readonly="true" ischecked="true" width="160" codetype="EXTRA_TAGS"></tags:ComBoxWithTree>
				</tr>
				<tr>
					<odin:select2 property="sfwxr" label="简历类型" data="['01', '现任'],['02', '历任']" ></odin:select2>
					<tags:ComBoxWithTree property="newRZJL" label="任职经历（新）" codetype="RZJL2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
					<tags:ComBoxWithTree property="A0194_TAG" label="熟悉领域" codetype="SXLY2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
				</tr>
				<tr id="tr_h">
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">是否为选调生</label>
					<input name="a99z103" id="a99z103" type="checkbox" value="1" /> </td>
					<td noWrap="nowrap" align=right><span id="a99z104SpanId" style="FONT-SIZE: 12px">选调为选调生时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a99z104A"  maxlength="6" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a99z104B" maxlength="6" width="72" />
						</tr></table>
					</td>
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>

	<%-- <odin:groupBoxNew title="基本" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0101" label="人员姓名" maxlength="36" />
					<odin:textEdit property="a0184" label="身份证号" maxlength="18" />
					<tags:PublicOrgCheck label="选择机构" property="SysOrgTree"/>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0111" label="籍贯" readonly="true" codename="code_name3" codetype="ZB01"   />
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">性&nbsp;&nbsp;&nbsp;别</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0104" type="radio" id="a01041" value="1" />男 </label> 
						<label><input name="a0104" type="radio" id="a01042" value="2" />女 </label> 
						<input  type="button" onclick="$('input[name=a0104]').prop('checked',false)" value="清除" />
					</td>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">年&nbsp;&nbsp;&nbsp;龄</span>&nbsp;</td>
					<td >
						<table  ><tr>
							<odin:numberEdit property="ageA"  maxlength="3" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:numberEdit property="ageB" maxlength="3" width="72" />
						</tr></table>
					</td>
					<odin:NewDateEditTag property="jiezsj" maxlength="6" isCheck="true" label="年龄年限计算截止"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:select2 property="a0160" label="人员类别" codeType="ZB125"></odin:select2>
					
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">出生年月</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<odin:select2 property="a0163" label="人员状态"  codeType="ZB126"></odin:select2>
					
					
				</tr>
				
				<tr>
					 <td noWrap="nowrap" align=right><span id="zhichenSpanId" style="FONT-SIZE: 12px">职&nbsp;&nbsp;&nbsp;称</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB8561   -->
									<label><input name="a0601" type="radio" id="a06011" value="1" />正高</label> 
									<label><input name="a0601" type="radio" id="a06012" value="2" />副高 </label> 
									<label><input name="a0601" type="radio" id="a06013" value="3" />中级 </label> 
									<br style="line-height: 1px;" /> 
									<label><input name="a0601" type="radio" id="a060145" value="4,5" />初级 </label> 
									<label><input name="a0601" type="radio"  id="a06019" value="9" />无职称</label>
									<input  type="button" onclick="$('input[name=a0601]').prop('checked',false)" value="清除" />
								</td>
							</tr>
						</table>
					</td> 
					
					<td noWrap="nowrap" align=right><span id="a0144SpanId" style="FONT-SIZE: 12px">参加中共时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0144A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0144B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:select2 property="a0141" label="政治面貌" multiSelect="true" codeType="GB4762"></odin:select2>
					
					
					
				</tr>
				
				
				<tr>
					<odin:textEdit property="a0192a" label="职务全称" maxlength="100" >(包含)</odin:textEdit>
					
					<td noWrap="nowrap" align=right><span id="a0134SpanId" style="FONT-SIZE: 12px">参加工作时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0134A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0134B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0114" label="出生地" codename="code_name3" codetype="ZB01" readonly="true"  />
				</tr>
				
				
				<tr>
					<tags:ComBoxWithTree property="a0221" label="现职务层次" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0221SpanId" style="FONT-SIZE: 12px">现职务层次</span>&nbsp;</td>
					<td >
						<table><tr>
							<tags:PublicTextIconEdit isLoadData="false"  property="a0221A" label2="现职务层次"  codetype="ZB09" width="72" readonly="true"  />
							<td><span style="font: 12px">至</span></td>
							<tags:PublicTextIconEdit isLoadData="false"  property="a0221B" label2="现职务层次"  codetype="ZB09" width="72" readonly="true"  />
						</tr></table>
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">任现职务层次时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">民&nbsp;&nbsp;&nbsp;族</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="radio" id="a011701" value="01" />汉族 </label> 
									<label><input name="a0117" type="radio" id="a011702" value="02" />少数民族</label>
									<input  type="button" onclick="$('input[name=a0117]').prop('checked',false)" value="清除" />
								</td>
							</tr>
						</table>
					</td> 
				</tr>
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB09" readonly="true" label="现职级"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">任职级时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:textEdit property="a1701" label="简历" maxlength="100" >(包含)</odin:textEdit>
				</tr>	
					
				
				<tr>
					<odin:select2 property="a0165" label="人员管理类别" codeType="ZB130" multiSelect="true"></odin:select2>
					<tags:PublicTextIconEdit3  property="a0195" label="统计关系所在单位" readonly="true" codetype="orgTreeJsonData"  ></tags:PublicTextIconEdit3>
				</tr>
			</table>
		</div>
	</odin:groupBoxNew> --%>
	

	
	<%-- 
	<odin:groupBoxNew title="职务" property="ggBox2" contentEl="zwDiv" collapsible="false">
		<div id="zwDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0216a" label="职务名称" maxlength="100" >(包含)</odin:textEdit>
					<odin:select2 property="a0201d" label="是否领导成员" codeType="XZ09"></odin:select2>
					<odin:select2 property="a0219" label="是否领导职务" codeType="ZB42"></odin:select2>
				</tr>
				
				<tr>
					<odin:select2 property="a0201e" label="成员类别" codeType="ZB129"></odin:select2>
					
					<td style="FONT-SIZE: 12px">
						<label><input name="qtxzry" id="qtxzry" type="checkbox" value="1" />其他现职人员（无职务）</label> 
					</td>
				</tr>
				
				
				
			</table>
		</div>
	</odin:groupBoxNew> --%>
	<%-- <odin:groupBoxNew title="学历学位" property="ggBox3" contentEl="xlDiv"
		collapsible="false">
		<div id="xlDiv">
			<table style="width: 100%">
				<tr>
					<td noWrap="nowrap" align=right><span id="xla0801bSpanId" style="FONT-SIZE: 12px">最高学历</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB64  -->
									<label><input name="xla0801b" type="radio" id="xla0801b1" value="1" />研究生</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b2" value="2" />大学</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b3" value="3" />大专</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b4" value="4" />中专</label> 
									<label><input name="xla0801b" type="radio" id="xla0801b6789" value="6,7,8,9" />其他</label> 
	  								<input  type="button" onclick="$('input[name=xla0801b]').prop('checked',false)" value="清除" />
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xla0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<tags:ComBoxWithTree property="xla0824" label="专业" readonly="true" ischecked="true" codetype="GB16835" />
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="xwa0901bSpanId" style="FONT-SIZE: 12px">最高学位</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="xwa0901b" type="radio" id="xwa0901b12" value="1,2" />博士</label> 
									<label><input name="xwa0901b" type="radio" id="xwa0901b3" value="3" />硕士</label> 
									<label><input name="xwa0901b" type="radio" id="xwa0901b4" value="4" />学士</label> 
									<input  type="button" onclick="$('input[name=xwa0901b]').prop('checked',false)" value="清除" />
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xwa0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<tags:ComBoxWithTree property="xwa0824" label="专业" readonly="true" ischecked="true" codetype="GB16835" />
					
				</tr>
				
				
				
			
			</table>
		</div>
	</odin:groupBoxNew>
	
		
	<odin:groupBoxNew title="家庭成员" property="ggBox6" contentEl="jtcyDiv"
		collapsible="false">
		<div id="jtcyDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a3601" label="姓名" maxlength="100" ></odin:textEdit>
					<odin:hidden property="a3684"  />label="身份证号"  maxlength="18" 
					<odin:textEdit property="a3611" label="工作单位及职务"  />
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
							<td><odin:button text="干部名册" property="gbmcQuery" handler="gbmcQueryBtn"></odin:button>
							</td>
							<td><odin:button text="清除条件" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<%-- <td><odin:button text="保存条件" property="saveCon"></odin:button>
							</td> --%>
							<td align="center"><odin:button text="开始查询" property="mQuery" handler="dosearch"/></td>
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
		//打开 查找有关人选页面
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
	$h.openPageModeWin('gbmcQueryMntp','pages.customquery.gbmcQuery_mntp','干部查询列表',650,480,'','<%=request.getContextPath()%>',window);
}
</script>


<%@include file="/pages/customquery/otjs.jsp" %>

