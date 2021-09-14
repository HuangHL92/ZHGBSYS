<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>





<html style="background-color: rgb(223,232,246);">
<meta http-equiv="X-UA-Compatible"content="IE=8">

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
	String querydb = request.getParameter("querydb");
	querydb = querydb==null?"":querydb;
%>
	<style>

.x-panel-bwrap {
	height: 100%
}
.x-panel-body {
	height: 100%
}


#tag_container1 {
	position: relative;
	width: 100%;
	height: 400px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px;
}
#tag_container1 .tag_div {
	position: relative;
	width: 30%;
	height: 100%;
	float: left;
	margin-left: 2%;
}

#tag_info_div {
	position: relative;
	width: 100%;
}

#tag_info_div1 {
	position: relative;
	width: 100%;
}

#tag_info_div #a0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}


#tag_container {
	width: 774px;
	height: 450px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
} 

#left_div {
	width: 240px;
	height: 450px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
	overflow-y: auto;
	overflow-x: hidden;
}

#left_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	border-width: 0 1px 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	line-height: 26px;
	padding-left: 2px;
}

#right_div {
	width: 520px;
	height: 450px;
	float: left;
	padding-left: 10px;
}

#right_div table {
	font-size: 14px;
}

#right_div div {
	display: none;
	width: 100%;
	height: 450px;
	overflow-y: auto;
}

#right_div div table {
	font-size: 14px;
}

#right_div table tr td {
	height: 26px;
	line-height: 26px;
}

#right_div table tr td input[type=text] {
	width: 50px;
	height: 21px;
}

#bottom_div {
	width: 100%;
	height: 40px;
	padding-top: 5px;
}

#bottom_div table {
	width: 100%;
}




</style>


<div style="float: left;position: relative;">
	<table width="240" cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" checked="checked"><font style="font-size: 13">包含下级</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 300px;"></div>
				<odin:editgrid2 property="memberGrid" hasRightMenu="false" pageSize="100" bbarId="pageToolBar"
					title="查询条件" autoFill="true" height="360" url="/" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="queryname" ></odin:gridDataCol>
						<odin:gridDataCol name="queryid" isLast="true"></odin:gridDataCol>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 editor="text" edited="false" header="名称" width="200" dataIndex="queryname"></odin:gridEditColumn2>
						<odin:gridEditColumn2 editor="text" edited="false" header="操作" width="50" renderer="opRenderer" dataIndex="queryid" isLast="true" ></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:griddata>
						{}
					</odin:griddata>
				</odin:editgrid2>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
				<odin:hidden property="LWflag"/>

<script type="text/javascript">

document.getElementById('LWflag').value = realParent.document.getElementById('LWflag').value;
</script>
			</td>
		</tr>
	</table>
	</div>

	<div  style="float: left;">
	<div id="conditionArea" style="height: 650;overflow-y: scroll;">
	<odin:tab id="tab"  tabchange="grantTabChange">
   		<odin:tabModel>
       <odin:tabItem title="基本信息与职务" id="tab1" ></odin:tabItem>     
       <odin:tabItem title="学历学位与专业技术" id="tab2" ></odin:tabItem>
       <odin:tabItem title="考核与奖惩" id="tab3" ></odin:tabItem>
       <odin:tabItem title="重要经历" id="tab4" ></odin:tabItem>
       <odin:tabItem title="熟悉领域" id="tab5"  isLast="true"></odin:tabItem>
   		</odin:tabModel>
   		<div id="tab11">
  		<odin:tabCont itemIndex="tab1" className="tab"   >
   		<odin:groupBoxNew title="基本" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%">
				<tr>
					<%-- <odin:textEdit property="a0101" label="人员姓名" maxlength="36" /> --%>
					<%-- <odin:textEdit property="a0184" label="身份证号" maxlength="18" /> --%>
					<%-- <tags:PublicOrgCheck label="选择机构" property="SysOrgTree"/> --%>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0111" label="籍贯" readonly="true" codename="code_name3" codetype="ZB01"   />
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">性&nbsp;&nbsp;&nbsp;别</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0104" type="radio" value="1" />男 </label> 
						<label><input name="a0104" type="radio" value="2" />女 </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">年&nbsp;&nbsp;&nbsp;龄</span>&nbsp;</td>
					<td >
						<table  ><tr>
							<odin:numberEdit property="ageA"  maxlength="3" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:numberEdit property="ageB" maxlength="3" width="72" />
						</tr></table>
					</td>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">民&nbsp;&nbsp;&nbsp;族</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="checkbox" value="01" />汉族 </label> 
									<label><input name="a0117" type="checkbox" value="02" />少数民族</label>
									<odin:hidden property="a0117v"/> 
								</td>
							</tr>
						</table>
					</td> 
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="年龄年限计算截止"></odin:NewDateEditTag>
					
					
				</tr>
				
				<tr>
				<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">出生年月</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
						</tr></table>
				</td>
					 <td noWrap="nowrap" align=right><span id="zhichenSpanId" style="FONT-SIZE: 12px">职&nbsp;&nbsp;&nbsp;称</span>&nbsp;</td>
					<td colspan="5">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB8561   -->
									<label><input name="a0601" type="checkbox" value="1" />正高</label> 
									<label><input name="a0601" type="checkbox" value="2" />副高 </label> 
									<label><input name="a0601" type="checkbox" value="3" />中级 </label> 
									<br style="line-height: 1px;" /> 
									<label><input name="a0601" type="checkbox" value="4,5" />初级 </label> 
									<label><input name="a0601" type="checkbox" value="9" />无职称</label>
									<odin:hidden property="a0601v"/> 
								</td>
							</tr>
						</table>
					</td> 
					
				</tr>
				
				
				<tr>
				
				<odin:select2 property="a0160" label="人员类别" codeType="ZB125" multiSelect="true"></odin:select2>
				<td noWrap="nowrap" align=right><span id="a0144SpanId" style="FONT-SIZE: 12px">参加中共时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0144A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0144B" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
				
				<tr>
					<%-- <td noWrap="nowrap" align=right><span id="a0221SpanId" style="FONT-SIZE: 12px">现职务层次</span>&nbsp;</td>
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
					</td> --%>
					
					<%-- <td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">民&nbsp;&nbsp;&nbsp;族</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="checkbox" value="01" />汉族 </label> 
									<label><input name="a0117" type="checkbox" value="02" />少数民族</label>
									<odin:hidden property="a0117v"/> 
								</td>
							</tr>
						</table>
					</td>  --%>

				</tr>
				
<%-- 				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB148" readonly="true" label="现职级"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">任职级时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:textEdit property="a1701" label="简历" maxlength="100" >(包含)</odin:textEdit>
				</tr>	 --%>
				
				<tr>
				<odin:select2 property="a0141" label="政治面貌" codeType="GB4762" multiSelect="true"></odin:select2>
					
					<%-- <odin:textEdit property="a0192a" label="职务全称" maxlength="100" >(包含)</odin:textEdit> --%>
					
					<td noWrap="nowrap" align=right><span id="a0134SpanId" style="FONT-SIZE: 12px">参加工作时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0134A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0134B" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
					
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false"  property="a0114" label="出生地" codename="code_name3" codetype="ZB01" readonly="true"  />
					<odin:select2 property="a0188" label="是否具有乡镇党政正职经历" codeType="XZ09" />
					<!-- <td noWrap="nowrap" align=right><span id="xgsjSpanId" style="FONT-SIZE: 12px">最后维护时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="xgsjA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="xgsjB" maxlength="8" width="72" />
						</tr></table>
					</td>-->
				</tr>
				
				<tr>
				<td noWrap="nowrap" align=right><span id="a0165SpanId" style="FONT-SIZE: 12px">人员管理类别</span>&nbsp;</td>
				<td style="FONT-SIZE: 12px" colspan="5">
					<label><input name="a0165" type="checkbox" value="01" />中央管理干部</label>
					<label><input name="a0165" type="checkbox" value="02" />省级党委管理干部 </label> 
					<label><input name="a0165" type="checkbox" value="03" />市级党委管理干部 </label>
					<label><input name="a0165" type="checkbox" value="05" />市委委托管理干部</label>  
					<label><input name="a0165" type="checkbox" value="04" />县级党委管理干部</label>
					<label><input name="a0165" type="checkbox" value="09" />其他</label> 
					<odin:hidden property="a0165v"/>
				</td>
				</tr>
				<tr>
					<odin:select2 property="a0163" label="人员状态" codeType="ZB126" multiSelect="true"></odin:select2>
<%-- 					<odin:textEdit property="a1701" label="简&nbsp;&nbsp;&nbsp;历" maxlength="100" >
					<br/><input name="intersection1" id="intersection1" type="checkbox" value="0" οnclick="this.value=(this.value==0)?1:0"/>（选中并集,未选中交集,用;隔开）
					</odin:textEdit> --%>
				</tr>
				<tr>
					<odin:textEdit property="byyxzya08" label="毕业院校和专业" maxlength="36">
					<br/><input name="intersection3" id="intersection3" type="checkbox" value="0" οnclick="this.value=(this.value==0)?1:0"/>（选中并集,未选中交集,用;隔开）
					</odin:textEdit>
					
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">是否简历对比</span>&nbsp;</td>
					
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="Contrast" type="radio" value="1" checked="checked"  />否 </label> 
						<label><input name="Contrast" type="radio" value="2" />是</label> 
						
					</td>
					
					
				</tr>
				<odin:hidden property="contrastOfA0000s" />
			</table>
		</div>
	</odin:groupBoxNew>
	
	
	
	
	
	<odin:groupBoxNew title="职务" property="ggBox2" contentEl="zwDiv" collapsible="false" collapsed="false">
		<div id="zwDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0215a" label="职务名称" maxlength="100" ></odin:textEdit>
					<odin:select2 property="a0201d" label="是否领导成员" codeType="XZ09"></odin:select2>
				</tr>
				
				<tr>
				
					<td noWrap="nowrap" align=right><span style="FONT-SIZE: 12px"></span>&nbsp;</td>
					<td noWrap="nowrap" style="FONT-SIZE: 12px">
						<input name="intersection2" id="intersection2" type="checkbox" value="0" οnclick="this.value=(this.value==0)?1:0"/>（选中并集,未选中交集,用;隔开）
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0201eSpanId" style="FONT-SIZE: 12px">成员类别</span>&nbsp;</td>
					<td colspan="2">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="a0201e" type="checkbox" value="1" />正职</label> 
									<label><input name="a0201e" type="checkbox" value="3" />副职</label> 
									<label><input name="a0201e" type="checkbox" value="Z" />其他</label> 
									<odin:hidden property="a0201ev"/>
								</td>
							</tr>
						</table>
					</td>
					
				</tr>
				<tr>					
					<odin:select2 property="a0219" label="是否领导职务" codeType="ZB42"></odin:select2>
					<td noWrap="nowrap" align=right>&nbsp;</td>
					<td style="FONT-SIZE: 12px">
						<label><input name="qtxzry" id="qtxzry" type="checkbox" value="1" />其他现职人员（无职务）</label> 
					</td>
				</tr>
				<tr>
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
				</tr>
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB148" readonly="true" label="现职级"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">任职级时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>
<%-- 	<odin:groupBoxNew title="家庭成员" property="ggBox6" contentEl="jtcyDiv" collapsed="false"
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
   </odin:tabCont>
   
   
   </div>
   
   
   
   <div id="tab21" style="display: none" >
   <odin:tabCont itemIndex="tab2" className="tab">
   <odin:groupBoxNew title="学历学位" property="ggBox3" contentEl="xlDiv"
		collapsible="false" collapsed="false">
		<div id="xlDiv">
			<table style="width: 100%">
				<tr>
					<td noWrap="nowrap" align=right><span id="xla0801bSpanId" style="FONT-SIZE: 12px">最高学历</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB64  -->
									<label><input name="xla0801b" type="checkbox" value="1" />研究生</label> 
									<label><input name="xla0801b" type="checkbox" value="2" />大学</label> 
									<label><input name="xla0801b" type="checkbox" value="3" />大专</label> 
									<label><input name="xla0801b" type="checkbox" value="4" />中专</label> 
									<label><input name="xla0801b" type="checkbox" value="6,7,8,9" />其他</label> 
									<odin:hidden property="xla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xla0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="xla0824" label="专业"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="xwa0901bSpanId" style="FONT-SIZE: 12px">最高学位</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="xwa0901b" type="checkbox" value="1,2" />博士</label> 
									<label><input name="xwa0901b" type="checkbox" value="3" />硕士</label> 
									<label><input name="xwa0901b" type="checkbox" value="4" />学士</label> 
									<odin:hidden property="xwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xwa0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="xwa0824" label="专业" maxlength="100" ></odin:textEdit>
					
				</tr>
				
				
				
				<tr>
					<td noWrap="nowrap" align=right><span id="qrzxla0801bSpanId" style="FONT-SIZE: 12px">全日制最高学历</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="qrzxla0801b" type="checkbox" value="1" />研究生</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="2" />大学</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="3" />大专</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="4" />中专</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="6,7,8,9" />其他</label> 
									<odin:hidden property="qrzxla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="qrzxla0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="qrzxla0824" label="专业"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="qrzxwa0901bSpanId" style="FONT-SIZE: 12px">全日制最高学位</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="qrzxwa0901b" type="checkbox" value="1,2" />博士</label> 
									<label><input name="qrzxwa0901b" type="checkbox" value="3" />硕士</label> 
									<label><input name="qrzxwa0901b" type="checkbox" value="4" />学士</label> 
									<odin:hidden property="qrzxwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="qrzxwa0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="qrzxwa0824" label="专业" maxlength="100" ></odin:textEdit>
					
				</tr>
				
				
				
				<tr>
					<td noWrap="nowrap" align=right><span id="zzxla0801bSpanId" style="FONT-SIZE: 12px">在职最高学历</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="zzxla0801b" type="checkbox" value="1" />研究生</label> 
									<label><input name="zzxla0801b" type="checkbox" value="2" />大学</label> 
									<label><input name="zzxla0801b" type="checkbox" value="3" />大专</label> 
									<label><input name="zzxla0801b" type="checkbox" value="4" />中专</label> 
									<label><input name="zzxla0801b" type="checkbox" value="6,7,8,9" />其他</label> 
									<odin:hidden property="zzxla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="zzxla0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="zzxla0824" label="专业"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="zzxwa0901bSpanId" style="FONT-SIZE: 12px">在职最高学位</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="zzxwa0901b" type="checkbox" value="1,2" />博士</label> 
									<label><input name="zzxwa0901b" type="checkbox" value="3" />硕士</label> 
									<label><input name="zzxwa0901b" type="checkbox" value="4" />学士</label> 
									<odin:hidden property="zzxwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="zzxwa0814" label="毕业院校" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="zzxwa0824" label="专业" maxlength="100" ></odin:textEdit>
					
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>
	<odin:groupBoxNew title="专业技术"  property="ggBox7" contentEl="zyDiv"   collapsible="false" collapsed="false" >
			<div id="zyDiv">
				<table style="width:100%">
					<tr>
						 <tags:PublicTextIconEdit property="a0601a" label="专业技术资格代码"  readonly="true" codetype="GB8561"  ></tags:PublicTextIconEdit>	
						 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 
					</tr>
				</table>		
			</div>
	</odin:groupBoxNew>
	
	
   </odin:tabCont>
   </div>
   <div id="tab31" style="display: none">
   <odin:tabCont itemIndex="tab3" className="tab">
   <odin:groupBoxNew title="奖惩" property="ggBox4" contentEl="jcDiv"
		collapsible="false" collapsed="false">
		<div id="jcDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a14z101" label="奖惩描述" maxlength="100" >(包含)</odin:textEdit>
					<td noWrap="nowrap" align=right><span id="lba1404bSpanId" style="FONT-SIZE: 12px">奖惩类别</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB65  -->
									<label><input name="lba1404b" type="checkbox" value="01" />奖励</label> 
									<label><input name="lba1404b" type="checkbox" value="02" />惩戒</label> 
									<odin:hidden property="lba1404bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<tags:PublicTextIconEdit isLoadData="false" property="a1404b" label="奖惩名称代码"  readonly="true" codetype="ZB65" />
					
				</tr>
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09" />
					<odin:select2 property="a1414" label="批准机关级别"  codeType="ZB03" />
					<tags:PublicTextIconEdit isLoadData="false" property="a1428" label="批准机关性质" readonly="true" codetype="ZB128" />
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>
	
	
	<odin:groupBoxNew title="年度考核" property="ggBox5" contentEl="ndkhDiv" collapsed="false"
		collapsible="false">
		<div id="ndkhDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a15z101" label="年度考核描述" maxlength="100" >(包含)</odin:textEdit>
					<odin:select2 property="a1521" label="考核年度"  maxlength="4" multiSelect="true" />
					<tags:PublicTextIconEdit isLoadData="false" property="a1517" label="考核结论类别"  codetype="ZB18" readonly="true"/>
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>
      
   
   
   </odin:tabCont>
   </div>
   <div id="tab41" style="display: none">
   <odin:tabCont itemIndex="tab4" className="tab">
		<div id="tag_container">
	<div id="left_div">
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">中央国家机关职务</div>
		<div class="leftMenu" onclick="changeTag(this, '02');">省区市职务</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">副省级城市职务</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">地（市、州、盟）职务</div>
		<div class="leftMenu" onclick="changeTag(this, '05');">县（市、区、旗）职务</div>
		<div class="leftMenu" onclick="changeTag(this, '06');">乡镇（街道）职务</div>
		<div class="leftMenu" onclick="changeTag(this, '07');">金融企业职务</div>
		<div class="leftMenu" onclick="changeTag(this, '08');">企业职务</div>
		<div class="leftMenu" onclick="changeTag(this, '09');">高校职务</div>
		<div class="leftMenu" onclick="changeTag(this, '10');">科研院所职务</div>
		<div class="leftMenu" onclick="changeTag(this, '11');">公立医院职务</div>
		<div class="leftMenu" onclick="changeTag(this, '12');">困难艰苦地区工作经历</div>
		<div class="leftMenu" onclick="changeTag(this, '13');">团口经历</div>
		<div class="leftMenu" onclick="changeTag(this, '14');">曾任两代表一委员情况</div>
		<div class="leftMenu" onclick="changeTag(this, '15');">政法工作经历</div>
		<div class="leftMenu" onclick="changeTag(this, '16');">秘书经历</div>
		<div class="leftMenu" onclick="changeTag(this, '17');">开发区、高新区、自贸区等经历</div>
		<div class="leftMenu" onclick="changeTag(this, '20');">海外工作经历</div>
		<div class="leftMenu" onclick="changeTag(this, '21');">省属企业领导班子工作经历</div>
		<div class="leftMenu" onclick="changeTag(this, '22');">下级企业正职任职经历</div>
		<div class="leftMenu" onclick="changeTag(this, '23');">总部职能部门任职经历</div>
		<div class="leftMenu" onclick="changeTag(this, '24');">上挂下派工作经历</div>
		<div class="leftMenu" onclick="changeTag(this, '18');">其他经历</div>
	</div>
	<div id="right_div">
	    <!-- 中央国家机关职务 -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="tag0101" id="tag0101" onclick="a0193tagInfo(this,'0101','正部级单位司局级正职领导职务')" >
					<label>正部级单位司局级正职领导职务&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0102" id="tag0102" onclick="a0193tagInfo(this,'0102','正部级单位司局级副职领导职务')"  >
					<label>正部级单位司局级副职领导职务&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0103" id="tag0103" onclick="a0193tagInfo(this,'0103','副部级单位司局级正职领导职务')"  >
					<label>副部级单位司局级正职领导职务&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0104" id="tag0104" onclick="a0193tagInfo(this,'0104','副部级单位司局级副职领导职务')"  >
					<label>副部级单位司局级副职领导职务&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0105" id="tag0105" onclick="a0193tagInfo(this,'0105','县处级正职领导职务')"  >
					<label>县处级正职领导职务</label>
				</td>	
			</tr>
		</table>
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0201" id="tag0201" onclick="a0193tagInfo(this,'0201','省直部门正职领导职务')"  >
					<label>省直部门正职领导职务</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0202" id="tag0202" onclick="a0193tagInfo(this,'0202','省直部门副职领导职务')"  >
					<label>省直部门副职领导职务 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0203" id="tag0203" onclick="a0193tagInfo(this,'0203','省直部门县处级正职领导职务')"  >
					<label>省直部门县处级正职领导职务</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0204" id="tag0204" onclick="a0193tagInfo(this,'0204','省直部门县处级副职领导职务')" >
					<label>省直部门县处级副职领导职务</label>
				</td>	
			</tr>
		</table>
		<!-- 副省级城市职务 -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0301" id="tag0301" onclick="a0193tagInfo(this,'0301','副省级城市不兼任政府正职的副书记')" >
					<label>副省级城市不兼任政府正职的副书记</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0302" id="tag0302" onclick="a0193tagInfo(this,'0302','副省级城市政府常务副职')" >
					<label>副省级城市政府常务副职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0303" id="tag0303" onclick="a0193tagInfo(this,'0303','副省级城市党政副职')" >
					<label>副省级城市党政副职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0304" id="tag0304" onclick="a0193tagInfo(this,'0304','副省级城市直属部门正职')" >
					<label>副省级城市直属部门正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0305" id="tag0305" onclick="a0193tagInfo(this,'0305','副省级城市直属部门副职')" >
					<label>副省级城市直属部门副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0306" id="tag0306" onclick="a0193tagInfo(this,'0306','副省级城市直属部门中层正职')" >
					<label>副省级城市直属部门中层正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0307" id="tag0307" onclick="a0193tagInfo(this,'0307','副省级城市直属部门中层副职')" >
					<label>副省级城市直属部门中层副职</label>
				</td>	
			</tr>
		</table>	
		<!-- 地（市、州、盟）职务 -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0401" id="tag0401" onclick="a0193tagInfo(this,'0401','地（市、州、盟）党委正职')" >
					<label>地（市、州、盟）党委正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0402" id="tag0402" onclick="a0193tagInfo(this,'0402','地（市、州、盟）政府正职')" >
					<label>地（市、州、盟）政府正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0403" id="tag0403" onclick="a0193tagInfo(this,'0403','地（市、州、盟）不兼任政府正职的副书记')" >
					<label>地（市、州、盟）不兼任政府正职的副书记</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0404" id="tag0404" onclick="a0193tagInfo(this,'0404','地（市、州、盟）政府常务副职')" >
					<label>地（市、州、盟）政府常务副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0405" id="tag0405" onclick="a0193tagInfo(this,'0405','地（市、州、盟）党政副职')" >
					<label>地（市、州、盟）党政副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0406" id="tag0406" onclick="a0193tagInfo(this,'0406','地（市、州、盟）直属部门正职')" >
					<label>地（市、州、盟）直属部门正职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0407" id="tag0407" onclick="a0193tagInfo(this,'0407','市直属部门班子成员（副职）')" >
					<label>市直属部门班子成员（副职） </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0408" id="tag0408" onclick="a0193tagInfo(this,'0408','市直属部门中层正职')" >
					<label>市直属部门中层正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0409" id="tag0409" onclick="a0193tagInfo(this,'0409','市直属部门中层副职')"  >
					<label>市直属部门中层副职 </label>
				</td>	
			</tr>					
		</table>	
		<!-- 县（市、区、旗）职务 -->
		<table id="tag05" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0501" id="tag0501" onclick="a0193tagInfo(this,'0501','县（市、区、旗）党委正职')" >
					<label>县（市、区、旗）党委正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0502" id="tag0502" onclick="a0193tagInfo(this,'0502','县（市、区、旗）政府正职')" >
					<label>县（市、区、旗）政府正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0504" id="tag0504" onclick="a0193tagInfo(this,'0504','县（市、区、旗）脱贫攻坚期间贫困县党政正职')" >
					<label>县（市、区、旗）脱贫攻坚期间贫困县党政正职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0503" id="tag0503" onclick="a0193tagInfo(this,'0503','县（市、区、旗）党政副职 ')" >
					<label>县（市、区、旗）党政副职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0505" id="tag0505" onclick="a0193tagInfo(this,'0505','县级部门正职')" >
					<label>县级部门正职 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0506" id="tag0506" onclick="a0193tagInfo(this,'0506','县级部门班子成员（副职）')" >
					<label>县级部门班子成员（副职） </label>
				</td>	
			</tr>
		</table>	
		<!-- 乡镇（街道）职务 -->
		<table id="tag06" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0601" id="tag0601" onclick="a0193tagInfo(this,'0601','乡镇（街道）党政正职')" >
					<label>乡镇（街道）党政正职  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0602" id="tag0602" onclick="a0193tagInfo(this,'0602','乡镇（街道）党政班子成员' )" >
					<label>乡镇（街道）党政班子成员 </label>
				</td>	
			</tr>
		</table>	
		<!-- 金融企业职务  -->
		<table id="tag07" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0701" id="tag0701" onclick="a0193tagInfo(this,'0701','中管金融企业副职')" >
					<label>中管金融企业副职  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0702" id="tag0702" onclick="a0193tagInfo(this,'0702','中管金融企业二级正职')" >
					<label>中管金融企业二级正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0703" id="tag0703" onclick="a0193tagInfo(this,'0703','中管金融企业二级副职')" >
					<label>中管金融企业二级副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0704" id="tag0704" onclick="a0193tagInfo(this,'0704','中央单位所属金融企业正职')" >
					<label>中央单位所属金融企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0705" id="tag0705" onclick="a0193tagInfo(this,'0705','中央单位所属金融企业副职')" >
					<label>中央单位所属金融企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0708" id="tag0708" onclick="a0193tagInfo(this,'0708','省属金融企业正职')" >
					<label>省属金融企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0709" id="tag0709" onclick="a0193tagInfo(this,'0709','省属金融企业副职')" >
					<label>省属金融企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0712" id="tag0712" onclick="a0193tagInfo(this,'0712','市属金融企业正职')" >
					<label>市属金融企业正职</label>
				</td>	
			</tr>
		</table>	
		<!-- 企业职务 -->
		<table id="tag08" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0801" id="tag0801" onclick="a0193tagInfo(this,'0801','中管企业副职')"  >
					<label>中管企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0802" id="tag0802" onclick="a0193tagInfo(this,'0802','中管企业二级正职')"  >
					<label>中管企业二级正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0803" id="tag0803" onclick="a0193tagInfo(this,'0803','中管企业二级副职')"  >
					<label>中管企业二级副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0820" id="tag0820" onclick="a0193tagInfo(this,'0820','中管企业三级正职')"  >
					<label>中管企业三级正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0804" id="tag0804" onclick="a0193tagInfo(this,'0804','国务院国资委管理的企业正职')"  >
					<label>国务院国资委管理的企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0805" id="tag0805" onclick="a0193tagInfo(this,'0805','国务院国资委管理的企业副职')"  >
					<label>国务院国资委管理的企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0806" id="tag0806" onclick="a0193tagInfo(this,'0806','国务院国资委管理的企业二级正职')"  >
					<label>国务院国资委管理的企业二级正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0813" id="tag0813" onclick="a0193tagInfo(this,'0813','中央单位所属企业正职')"  >
					<label>中央单位所属企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0814" id="tag0814" onclick="a0193tagInfo(this,'0814','中央单位所属企业副职')"  >
					<label>中央单位所属企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0808" id="tag0808" onclick="a0193tagInfo(this,'0808','省属企业正职')"  >
					<label>省属企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0809" id="tag0809" onclick="a0193tagInfo(this,'0809','省属企业副职')"  >
					<label>省属企业副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0812" id="tag0812" onclick="a0193tagInfo(this,'0812','市属企业正职')"  >
					<label>市属企业正职</label>
				</td>	
			</tr>
		</table>	
		<!-- 高校职务 -->
		<div id="tag09" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag0901" id="tag0901" onclick="a0193tagInfo(this,'0901','中管高校副职')" >
						<label>中管高校副职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0902" id="tag0902" onclick="a0193tagInfo(this,'0902','中管高校中层正职')" >
						<label>中管高校中层正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0903" id="tag0903" onclick="a0193tagInfo(this,'0903','中管高校中层副职')"  >
						<label>中管高校中层副职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0904" id="tag0904" onclick="a0193tagInfo(this,'0904','部属高校正职')" >
						<label>部属高校正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0905" id="tag0905" onclick="a0193tagInfo(this,'0905','部属高校副职')" >
						<label>部属高校副职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0906" id="tag0906" onclick="a0193tagInfo(this,'0906','部属高校中层正职')" >
						<label>部属高校中层正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0907" id="tag0907" onclick="a0193tagInfo(this,'0907','省属高校正职')" >
						<label>省属高校正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0908" id="tag0908" onclick="a0193tagInfo(this,'0908','省属高校副职')" >
						<label>省属高校副职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0909" id="tag0909" onclick="a0193tagInfo(this,'0909','省属高校中层正职')" >
						<label>省属高校中层正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0910" id="tag0910" onclick="a0193tagInfo(this,'0910','国家“双一流”建设高校')"  >
						<label>国家“双一流”建设高校</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0911" id="tag0911" onclick="a0193tagInfo(this,'0911','省重点建设高校')" >
						<label>省重点建设高校</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0912" id="tag0912" onclick="a0193tagInfo(this,'0912','本科高校党委书记')" >
						<label>本科高校党委书记</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0913" id="tag0913" onclick="a0193tagInfo(this,'0913','本科高校校长')" >
						<label>本科高校校长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0914" id="tag0914" onclick="a0193tagInfo(this,'0914','本科党委副书记')" >
						<label>本科党委副书记</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0915" id="tag0915" onclick="a0193tagInfo(this,'0915','本科高校副校长')"  >
						<label>本科高校副校长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0916" id="tag0916" onclick="a0193tagInfo(this,'0916','本科高校党委委员')"  >
						<label>本科高校党委委员</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0917" id="tag0917" onclick="a0193tagInfo(this,'0917','高职院校党委书记')" >
						<label>高职院校党委书记</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0918" id="tag0918" onclick="a0193tagInfo(this,'0918','高职院校校长')" >
						<label>高职院校校长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0919" id="tag0919" onclick="a0193tagInfo(this,'0919','高职院校党委副书记')" >
						<label>高职院校党委副书记</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0920" id="tag0920" onclick="a0193tagInfo(this,'0920','高职院校副校长')" >
						<label>高职院校副校长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0921" id="tag0921" onclick="a0193tagInfo(this,'0921','本科高校二级学院党委书记')"  >
						<label>本科高校二级学院党委书记</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0922" id="tag0922" onclick="a0193tagInfo(this,'0922','本科高校二级学院院长')" >
						<label>本科高校二级学院院长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0923" id="tag0923" onclick="a0193tagInfo(this,'0923','本科高校部门正职')"  >
						<label>本科高校部门正职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0924" id="tag0924" onclick="a0193tagInfo(this,'0924','本科高校部门副职')" >
						<label>本科高校部门副职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0925" id="tag0925" onclick="a0193tagInfo(this,'0925','有地方领导工作经历')" >
						<label>有地方领导工作经历</label>
					</td>	
				</tr>
			</table>	
		</div>
		<!-- 科研院所职务  -->
		<table id="tag10" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1001" id="tag1001" onclick="a0193tagInfo(this,'1001','党中央和国务院直属科研院所内设机构正职')" >
					<label>党中央和国务院直属科研院所内设机构正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1002" id="tag1002" onclick="a0193tagInfo(this,'1002','党中央和国务院直属科研院所内设机构副职')"  >
					<label>党中央和国务院直属科研院所内设机构副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1003" id="tag1003" onclick="a0193tagInfo(this,'1003','中央单位所属科研院所正职')"  >
					<label>中央单位所属科研院所正职&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1004" id="tag1004" onclick="a0193tagInfo(this,'1004','中央单位所属科研院所副职')" >
					<label>中央单位所属科研院所副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1005" id="tag1005" onclick="a0193tagInfo(this,'1005','省属科研院所正职')" >
					<label>省属科研院所正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1006" id="tag1006" onclick="a0193tagInfo(this,'1006','省属科研院所副职')" >
					<label>省属科研院所副职</label>
				</td>	
			</tr>
		</table>	
		<!-- 公立医院职务 -->
		<table id="tag11" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1101" id="tag1101" onclick="a0193tagInfo(this,'1101','卫健委直属医院正职')" >
					<label>卫健委直属医院正职&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1102" id="tag1102" onclick="a0193tagInfo(this,'1102','省属科研院所副职')" >
					<label>省属科研院所副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1103" id="tag1103" onclick="a0193tagInfo(this,'1103','卫健委直属医院中层正职')"  >
					<label>卫健委直属医院中层正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1104" id="tag1104" onclick="a0193tagInfo(this,'1104','中管高校附属医院正职')" >
					<label>中管高校附属医院正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1105" id="tag1105" onclick="a0193tagInfo(this,'1105','中管高校附属医院副职')"  >
					<label>中管高校附属医院副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1106" id="tag1106" onclick="a0193tagInfo(this,'1106','部属高校附属医院正职')" >
					<label>部属高校附属医院正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1107" id="tag1107" onclick="a0193tagInfo(this,'1107','部属高校附属医院副职')" >
					<label>部属高校附属医院副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1108" id="tag1108" onclick="a0193tagInfo(this,'1108','省属公立医院正职')" >
					<label>省属公立医院正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1109" id="tag1109" onclick="a0193tagInfo(this,'1109','省属公立医院副职')" >
					<label>省属公立医院副职</label>09
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1110" id="tag1110" onclick="a0193tagInfo(this,'1110','市属公立医院正职')" >
					<label>市属公立医院正职</label>
				</td>	
			</tr>
		</table>	
		<!-- 困难艰苦地区工作经历  -->
		<table id="tag12" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1201" id="tag1201" onclick="a0193tagInfo(this,'1201','新疆工作经历')" >
					<label>新疆工作经历&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1202" id="tag1202" onclick="a0193tagInfo(this,'1202','西藏工作经历')" >
					<label>西藏工作经历&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1203" id="tag1203" onclick="a0193tagInfo(this,'1203','青海工作经历')" >
					<label>青海工作经历&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1204" id="tag1204" onclick="a0193tagInfo(this,'1204','贫困地区工作经历')" >
					<label>贫困地区工作经历&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1205" id="tag1205" onclick="a0193tagInfo(this,'1205','西部地区、老工业基地和革命老区')" >
					<label>西部地区、老工业基地和革命老区</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1206" id="tag1206" onclick="a0193tagInfo(this,'1206','赣南中央苏区')" >
					<label>赣南中央苏区 </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1207" id="tag1207" onclick="a0193tagInfo(this,'1207','其他困难艰苦地区工作经历')" >
					<label>其他困难艰苦地区工作经历</label>
				</td>	
			</tr>
		</table>	
		<!-- 团口经历  -->
		<table id="tag13" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1301" id="tag1301" onclick="a0193tagInfo(this,'1301','团中央书记处书记')" >
					<label>团中央书记处书记</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1302" id="tag1302" onclick="a0193tagInfo(this,'1302','团中央部门（单位）正职')" >
					<label>团中央部门（单位）正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1303" id="tag1303" onclick="a0193tagInfo(this,'1303','团省委正职')" >
					<label>团中央部门（单位）副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1304" id="tag1304" onclick="a0193tagInfo(this,'1304','团省委正职')" >
					<label>团省委正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1305" id="tag1305" onclick="a0193tagInfo(this,'1305','团省委副职')" >
					<label>团省委副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1306" id="tag1306" onclick="a0193tagInfo(this,'1306','副省级城市团委正职')" >
					<label>副省级城市团委正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1307" id="tag1307" onclick="a0193tagInfo(this,'1307','副省级城市团委副职')" >
					<label>副省级城市团委副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1308" id="tag1308" onclick="a0193tagInfo(this,'1308','团市委正职')" >
					<label>团市委正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1309" id="tag1309" onclick="a0193tagInfo(this,'1309','团市委副职')" >
					<label>团市委副职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1310" id="tag1310" onclick="a0193tagInfo(this,'1310','团县委正职')" >
					<label>团县委正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1311" id="tag1311" onclick="a0193tagInfo(this,'1311','团县委副职')" >
					<label>团县委副职</label>
				</td>	
			</tr>
		</table>	
		<!-- 曾任两代表一委员情况 -->
		<table id="tag14" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1401" id="tag1401" onclick="a0193tagInfo(this,'1401','党的全国代表大会代表')" >
					<label>党的全国代表大会代表</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1402" id="tag1402" onclick="a0193tagInfo(this,'1402','全国人大代表')" >
					<label>全国人大代表</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1403" id="tag1403" onclick="a0193tagInfo(this,'1403','全国人大专门委员会委员')" >
					<label>全国人大专门委员会委员</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1404" id="tag1404" onclick="a0193tagInfo(this,'1404','全国政协委员')" >
					<label>全国政协委员</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1405" id="tag1405" onclick="a0193tagInfo(this,'1405','全国政协各专门委员会副主任')"  >
					<label>全国政协各专门委员会副主任</label>
				</td>	
			</tr>
		</table>	
		<!-- 政法工作经历  -->
		<div id="tag15" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag1501" id="tag1501" onclick="a0193tagInfo(this,'1501','政法委或政府法制部门工作')" >
						<label>政法委或政府法制部门工作&nbsp;&nbsp;</label>

					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1502" id="tag1502" onclick="a0193tagInfo(this,'1502','检察院工作')" >
						<label>检察院工作&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1503" id="tag1503" onclick="a0193tagInfo(this,'1503','法院工作')" >
						<label>法院工作&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1504" id="tag1504" onclick="a0193tagInfo(this,'1504','公安机关工作')" >
						<label>公安机关工作&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1505" id="tag1505" onclick="a0193tagInfo(this,'1505','司法监所系统工作')" >
						<label>司法监所系统工作&nbsp;&nbsp;</label>

					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1506" id="tag1506" onclick="a0193tagInfo(this,'1506','国家安全机关工作')" >
						<label>国家安全机关工作&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1507" id="tag1507" onclick="a0193tagInfo(this,'1507','国家或地方立法工作')" >
						<label>国家或地方立法工作&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1508" id="tag1508" onclick="a0193tagInfo(this,'1508','律师')" >
						<label>律师&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1509" id="tag1509" onclick="a0193tagInfo(this,'1509','法律教学和研究工作')" >
						<label>法律教学和研究工作&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1510" id="tag1510" onclick="a0193tagInfo(this,'1510','政法单位主要领导任职经历')" >
						<label>政法单位主要领导任职经历</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1511" id="tag1511" onclick="a0193tagInfo(this,'1511','担任过政法委书记')" >
						<label>担任过政法委书记&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1512" id="tag1512" onclick="a0193tagInfo(this,'1512','担任过法检(两长)')" >
						<label>担任过法检“两长”&nbsp;&nbsp;</label>
						
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1513" id="tag1513" onclick="a0193tagInfo(this,'1513','担任过公安局长')" >
						<label>担任过公安局长&nbsp;&nbsp;</label>
						
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1514" id="tag1514" onclick="a0193tagInfo(this,'1514','担任过司法局长')" >
						<label>担任过司法局长&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1515" id="tag1515" onclick="a0193tagInfo(this,'1515','政法单位班子成员任职经历')" >
						<label>政法单位班子成员任职经历</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1516" id="tag1516" onclick="a0193tagInfo(this,'1516','政法单位常务副职')" >
						<label>政法单位常务副职&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1517" id="tag1517" onclick="a0193tagInfo(this,'1517','政法单位班子成员')" >
						<label>政法单位班子成员&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1518" id="tag1518" onclick="a0193tagInfo(this,'1518','在政法单位任现职领导岗位任职情况')" >
						<label>在政法单位任现职领导岗位任职情况</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1519" id="tag1519" onclick="a0193tagInfo(this,'1519','现领导岗位任职时间')" >
						<label>现领导岗位任职时间</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1520" id="tag1520" onclick="a0193tagInfo(this,'1520','现班子任职时间')" >
						<label>现班子任职时间</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1521" id="tag1521" onclick="a0193tagInfo(this,'1521','现领导职务层次任职时间')" >
						<label>现领导职务层次任职时间</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1522" id="tag1522" onclick="a0193tagInfo(this,'1522','从事法律工作时间')"  >
						<label>从事法律工作时间</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1523" id="tag1523" onclick="a0193tagInfo(this,'1523','从事法律工作五年及以上')"  >
						<label>从事法律工作五年及以上</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1524" id="tag1524" onclick="a0193tagInfo(this,'1524','从事法律工作两年至五年')" >
						<label>从事法律工作两年至五年</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1525" id="tag1525" onclick="a0193tagInfo(this,'1525','从事法律工作一年至两年')"  >
						<label>从事法律工作一年至两年</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1526" id="tag1526" onclick="a0193tagInfo(this,'1526','政法系统领导干部交流任职情况')"  >
						<label>政法系统领导干部交流任职情况</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1527" id="tag1527" onclick="a0193tagInfo(this,'1527','从政法系统内交流任职')"  >
						<label>从政法系统内交流任职</label> >
						<label>同一单位上下级之间交流任职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1528" id="tag1528" onclick="a0193tagInfo(this,'1528','从政法系统内交流任职')"  >
						<label>从政法系统内交流任职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1529" id="tag1529" onclick="a0193tagInfo(this,'1529','从政法系统外交流任职')" >
						<label>从政法系统外交流任职</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1530" id="tag1530" onclick="a0193tagInfo(this,'1530','通过国家司法考试（律师资格考试）')" >
						<label>通过国家司法考试（律师资格考试）</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1534" id="tag1534" onclick="a0193tagInfo(this,'1534','二级大法官、检察官')" >
						<label>二级大法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1535" id="tag1535" onclick="a0193tagInfo(this,'1535','一级高级法官、检察官')" >
						<label>一级高级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1536" id="tag1536" onclick="a0193tagInfo(this,'1536','二级高级法官、检察官')" >
						<label>二级高级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1537" id="tag1537" onclick="a0193tagInfo(this,'1537','三级高级法官、检察官')" >
						<label>三级高级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1538" id="tag1538" onclick="a0193tagInfo(this,'1538','四级高级法官、检察官')" >
						<label>四级高级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1539" id="tag1539" onclick="a0193tagInfo(this,'1539','一级法官、检察官')" >
						<label>一级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1540" id="tag1540" onclick="a0193tagInfo(this,'1540','二级法官、检察官')" >
						<label>二级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1541" id="tag1541" onclick="a0193tagInfo(this,'1541','三级法官、检察官')"  >
						<label>三级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1542" id="tag1542" onclick="a0193tagInfo(this,'1542','四级法官、检察官')" >
						<label>四级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1543" id="tag1543" onclick="a0193tagInfo(this,'1543','五级法官、检察官')" >
						<label>五级法官、检察官</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1545" id="tag1545" onclick="a0193tagInfo(this,'1545','一级警务专员')" >
						<label>一级警务专员</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1546" id="tag1546" onclick="a0193tagInfo(this,'1546','二级警务专员')" >
						<label>二级警务专员</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1547" id="tag1547" onclick="a0193tagInfo(this,'1547','一级高级警长')" >
						<label>一级高级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1548" id="tag1548" onclick="a0193tagInfo(this,'1548','二级高级警长')" >
						<label>二级高级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1549" id="tag1549" onclick="a0193tagInfo(this,'1549','三级高级警长')" >
						<label>三级高级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1550" id="tag1550" onclick="a0193tagInfo(this,'1550','四级高级警长')" >
						<label>四级高级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1552" id="tag1552" onclick="a0193tagInfo(this,'1552','一级警长')" >
						<label>一级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1553" id="tag1553" onclick="a0193tagInfo(this,'1553','二级警长')" >
						<label>二级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1554" id="tag1554" onclick="a0193tagInfo(this,'1554','三级警长')" >
						<label>三级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1555" id="tag1555" onclick="a0193tagInfo(this,'1555','四级警长')" >
						<label>四级警长</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1557" id="tag1557" onclick="a0193tagInfo(this,'1557','一级警员')" >
						<label>一级警员</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1558" id="tag1558" onclick="a0193tagInfo(this,'1558','二级警员')"  >
						<label>二级警员</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1560" id="tag1560" onclick="a0193tagInfo(this,'1560','警务技术一级总监')" >
						<label>警务技术一级总监</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1561" id="tag1561" onclick="a0193tagInfo(this,'1561','警务技术二级总监')" >
						<label>警务技术二级总监</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1562" id="tag1562" onclick="a0193tagInfo(this,'1562','警务技术一级主任')" >
						<label>警务技术一级主任</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1563" id="tag1563" onclick="a0193tagInfo(this,'1563','警务技术二级主任')" >
						<label>警务技术二级主任</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1564" id="tag1564" onclick="a0193tagInfo(this,'1564','警务技术三级主任')" >
						<label>警务技术三级主任</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1565" id="tag1565" onclick="a0193tagInfo(this,'1565','警务技术四级主任')" >
						<label>警务技术四级主任</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1566" id="tag1566" onclick="a0193tagInfo(this,'1566','警务技术一级主管')" >
						<label>警务技术一级主管</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1567" id="tag1567" onclick="a0193tagInfo(this,'1567','警务技术二级主管')" >
						<label>警务技术二级主管</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1568" id="tag1568" onclick="a0193tagInfo(this,'1568','警务技术三级主管')" >
						<label>警务技术三级主管</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1569" id="tag1569" onclick="a0193tagInfo(this,'1569','警务技术四级主管')" >
						<label>警务技术四级主管</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1570" id="tag1570" onclick="a0193tagInfo(this,'1570','警务技术员')" >
						<label>警务技术员</label>
					</td>	
				</tr>
			</table>	
		</div>
		<!-- 秘书经历 -->
		<table id="tag16" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1601" id="tag1601"  onclick="a0193tagInfo(this,'1601','现职党和国家领导人秘书')" >
					<label>现职党和国家领导人秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1602" id="tag1602" onclick="a0193tagInfo(this,'1602','现职正部级领导秘书')" >
					<label>现职正部级领导秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1603" id="tag1603" onclick="a0193tagInfo(this,'1603','现职副部级领导秘书')" >
					<label>现职副部级领导秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1604" id="tag1604" onclick="a0193tagInfo(this,'1604','非现职党和国家领导人秘书')"  >
					<label>非现职党和国家领导人秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1605" id="tag1605" onclick="a0193tagInfo(this,'1605','非现职正部级领导秘书')" >
					<label>非现职正部级领导秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1606" id="tag1606" onclick="a0193tagInfo(this,'1606','非现职副部级领导秘书')" >
					<label>非现职副部级领导秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1607" id="tag1607" onclick="a0193tagInfo(this,'1607','中管金融企业、中管企业现职主要负责人秘书')" >
					<label>中管金融企业、中管企业现职主要负责人秘书</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1608" id="tag1608" onclick="a0193tagInfo(this,'1608','中管金融企业、中管企业非现职主要负责人秘书')" >
					<label>中管金融企业、中管企业非现职主要负责人秘书</label>
				</td>	
			</tr>
		</table>	
		<!-- 开发区、高新区、自贸区等经历  -->
		<table id="tag17" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1701" id="tag1701" onclick="a0193tagInfo(this,'1701','国家级开发区、高新区、自贸区')" >
					<label>国家级开发区、高新区、自贸区</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1702" id="tag1702" onclick="a0193tagInfo(this,'1702','省级开发区、高新区、自贸区')" >
					<label>省级开发区、高新区、自贸区</label>
				</td>	
			</tr>
		</table>
		<!-- 海外工作经历  -->
		<table id="tag20" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2001" id="tag2001" onclick="a0193tagInfo(this,'2002','大使')"  >
					<label>大使</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2002" id="tag2002" onclick="a0193tagInfo(this,'2002','公使')" >
					<label>公使</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2003" id="tag2003" onclick="a0193tagInfo(this,2003,公使衔参赞)" >
					<label>公使衔参赞</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2004" id="tag2004" onclick="a0193tagInfo(this,'2004','总领事')" >
					<label>总领事</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2005" id="tag2005" onclick="a0193tagInfo(this,'2005','副总领事')" >
					<label>副总领事</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2006" id="tag2006" onclick="a0193tagInfo(this,'2006','海外工作')" >
					<label>海外工作</label>
				</td>	
			</tr>
		</table>
		<!-- 省属企业领导班子工作经历 -->
		<table id="tag21" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2101" id="tag2101" onclick="a0193tagInfo(this,'2101','党委会工作')"  >
					<label>党委会工作</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2102" id="tag2102" onclick="a0193tagInfo(this,'2102','董事会工作（不含外部董事、职工董事）')"  >
					<label>董事会工作（不含外部董事、职工董事）</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2103" id="tag2103" onclick="a0193tagInfo(this,'2103','监事会工作（内设）')" >
					<label>经理层工作</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2104" id="tag2104" onclick="a0193tagInfo(this,'2104','监事会工作（内设）')" >
					<label>监事会工作（内设）</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2105" id="tag2105" onclick="a0193tagInfo(this,'2105','工会主席工作')" >
					<label>工会主席工作</label>
				</td>	
			</tr>
		</table>	
		<!-- 下级企业正职任职经历-->
		<table id="tag22" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2201" id="tag2201" onclick="a0193tagInfo(this,'2201','担任过二级企业正职')"  >
					<label>担任过二级企业正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2202" id="tag2202" onclick="a0193tagInfo(this,'2202','担任过三级企业正职')" >
					<label>担任过三级企业正职</label>
				</td>	
			</tr>
		</table>
		<!-- 总部职能部门任职经历 -->
		<table id="tag23" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2301" id="tag2301" onclick="a0193tagInfo(this,'2301','担任过部门正职')" >
					<label>担任过部门正职</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2302" id="tag2302" onclick="a0193tagInfo(this,'2302','担任过部门副职')" >
					<label>担任过部门副职</label>
				</td>	
			</tr>
		</table>
		<!-- 上挂下派工作经历 -->
		<table id="tag24" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2401" id="tag2401" onclick="a0193tagInfo(this,'2401','挂职过中直、国家机关副处以上职务')"  >
					<label>挂职过中直、国家机关副处以上职务</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2402" id="tag2402" onclick="a0193tagInfo(this,'2402','挂职过省直单位副处以上职务')" >
					<label>挂职过省直单位副处以上职务</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2403" id="tag2403" onclick="a0193tagInfo(this,'2403','挂职过市县副处以上职务')" >
					<label>挂职过市县副处以上职务</label>
				</td>	
			</tr>
		</table>	 
		<!-- 其他经历 -->
		<table id="tag18" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1801" id="tag1801" onclick="a0193tagInfo(this,'1801','中组部双向交流任职干部')" >
					<label>中组部双向交流任职干部</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1802" id="tag1802" onclick="a0193tagInfo(this,'1802','中组部双向交流挂职干部')" >
					<label>中组部双向交流挂职干部</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1803" id="tag1803" onclick="a0193tagInfo(this,'1803','两年及以上基层工作经历')" >
					<label>两年及以上基层工作经历</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1805" id="tag1805" onclick="a0193tagInfo(this,'1804','2014年以来破格提拔')"  >
					<label>2014年以来破格提拔</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1806" id="tag1806" onclick="a0193tagInfo(this,'1805','公开选拔')" >
					<label>公开选拔</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1807" id="tag1807" onclick="a0193tagInfo(this,'1806','选调生')" >
					<label>选调生</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1809" id="tag1809"  onclick="a0193tagInfo(this,'1809','其它')">
					<label>其它</label>
				</td>	
			</tr>
		</table>
		
				
	</div>
</div>
		<div id="tag_info_div1">
			<odin:hidden property="a0193s" title="编码" />
			<textarea rows="3" cols="114" id="a0193z" name="a0193z" ></textarea>
		</div>	
			
			
   
   
   
   </odin:tabCont>
</div>
<div id="tab51" style="display: none">
   <odin:tabCont itemIndex="tab5" className="tab">
   		<odin:hidden property="a0194s"/>
		<div id="tag_container1">
		<div class="tag_div">
			<odin:checkbox property="tag011"  label="党务" onclick="fullContent(this,'01','党务')"></odin:checkbox>
			<odin:checkbox property="tag021"  label="纪检监察" onclick="fullContent(this,'02','纪检监察')"></odin:checkbox>
			<odin:checkbox property="tag031"  label="组织人事" onclick="fullContent(this,'03','组织人事')"></odin:checkbox>
			<odin:checkbox property="tag041"  label="宣传思想意识形态" onclick="fullContent(this,'04','宣传思想意识形态')"></odin:checkbox>
			<odin:checkbox property="tag051"  label="统战" onclick="fullContent(this,'05','统战')"></odin:checkbox>
			<odin:checkbox property="tag061"  label="政法" onclick="fullContent(this,'06','政法')"></odin:checkbox>
			<odin:checkbox property="tag071"  label="群团" onclick="fullContent(this,'07','群团')"></odin:checkbox>
			<odin:checkbox property="tag081"  label="政策研究" onclick="fullContent(this,'08','政策研究')"></odin:checkbox>
			<odin:checkbox property="tag091"  label="宏观经济" onclick="fullContent(this,'09','宏观经济')"></odin:checkbox>
			<odin:checkbox property="tag101"  label="工业经济" onclick="fullContent(this,'10','工业经济')"></odin:checkbox>
			<odin:checkbox property="tag111"  label="自然资源管理" onclick="fullContent(this,'11','自然资源管理')"></odin:checkbox>
			<odin:checkbox property="tag121"  label="生态环境保护" onclick="fullContent(this,'12','生态环境保护')"></odin:checkbox>
			<odin:checkbox property="tag131"  label="文化旅游" onclick="fullContent(this,'13','文化旅游')"></odin:checkbox>
			<odin:checkbox property="tag141"  label="城建规划" onclick="fullContent(this,'14','城建规划')"></odin:checkbox>
			<odin:checkbox property="tag151"  label="交通运输" onclick="fullContent(this,'15','交通运输')"></odin:checkbox>
		</div>
		<div class="tag_div">
			<odin:checkbox property="tag161"  label="财政税收审计" onclick="fullContent(this,'16','财政税收审计')"></odin:checkbox>
			<odin:checkbox property="tag171"  label="金融" onclick="fullContent(this,'17','金融')"></odin:checkbox>
			<odin:checkbox property="tag181"  label="商贸流通" onclick="fullContent(this,'18','商贸流通')"></odin:checkbox>
			<odin:checkbox property="tag191"  label="科技" onclick="fullContent(this,'19','科技')"></odin:checkbox>
			<odin:checkbox property="tag201"  label="网络安全和信息化" onclick="fullContent(this,'20','网络安全和信息化')"></odin:checkbox>
			<odin:checkbox property="tag211"  label="教育" onclick="fullContent(this,'21','教育')"></odin:checkbox>
			<odin:checkbox property="tag221"  label="卫生" onclick="fullContent(this,'22','卫生')"></odin:checkbox>
			<odin:checkbox property="tag231"  label="体育" onclick="fullContent(this,'23','体育')"></odin:checkbox>
			<odin:checkbox property="tag241"  label="综合执法与市场监管" onclick="fullContent(this,'24','综合执法与市场监管')"></odin:checkbox>
			<odin:checkbox property="tag251"  label="农业农村" onclick="fullContent(this,'25','农业农村')"></odin:checkbox>
			<odin:checkbox property="tag261"  label="水利" onclick="fullContent(this,'26','水利')"></odin:checkbox>
			<odin:checkbox property="tag271"  label="民族宗教" onclick="fullContent(this,'27','民族宗教')"></odin:checkbox>
			<odin:checkbox property="tag281"  label="社会管理" onclick="fullContent(this,'28','社会管理')"></odin:checkbox>
			<odin:checkbox property="tag291"  label="行政管理" onclick="fullContent(this,'29','行政管理')"></odin:checkbox>
			<odin:checkbox property="tag301"  label="安全生产和应急管理" onclick="fullContent(this,'30','安全生产和应急管理')"></odin:checkbox>
		</div>
		<div class="tag_div">
			<odin:checkbox property="tag311"  label="外事港澳台侨" onclick="fullContent(this,'31','外事港澳台侨')"></odin:checkbox>
			<odin:checkbox property="tag321"  label="国防军事" onclick="fullContent(this,'32','国防军事')"></odin:checkbox>
			<odin:checkbox property="tag331"  label="企业经营管理" onclick="fullContent(this,'33','企业经营管理')"></odin:checkbox>
			<odin:checkbox property="tag341"  label="企业生产运行" onclick="fullContent(this,'34','企业生产运行')"></odin:checkbox>
			<odin:checkbox property="tag351"  label="企业市场营销" onclick="fullContent(this,'35','企业市场营销')"></odin:checkbox>
			<odin:checkbox property="tag361"  label="企业财务管理" onclick="fullContent(this,'36','企业财务管理')"></odin:checkbox>
			<odin:checkbox property="tag371"  label="企业专业技术" onclick="fullContent(this,'37','企业专业技术')"></odin:checkbox>
			<odin:checkbox property="tag381"  label="企业国际业务" onclick="fullContent(this,'38','企业国际业务')"></odin:checkbox>
			<odin:checkbox property="tag391"  label="学科建设" onclick="fullContent(this,'39','学科建设')"></odin:checkbox>
			<odin:checkbox property="tag401"  label="学生工作" onclick="fullContent(this,'40','学生工作')"></odin:checkbox>
			<odin:checkbox property="tag411"  label="行政管理" onclick="fullContent(this,'41','行政管理')"></odin:checkbox>
			<odin:checkbox property="tag421"  label="对外交流" onclick="fullContent(this,'42','对外交流')"></odin:checkbox>
			<odin:checkbox property="tag431"  label="思政教育" onclick="fullContent(this,'43','思政教育')"></odin:checkbox>
			<odin:checkbox property="tag441"  label="后勤管理" onclick="fullContent(this,'44','后勤管理')"></odin:checkbox>
			<odin:checkbox property="tag991"  label="其他" onclick="fullContent(this,'99','其他')"></odin:checkbox>
		</div>
		</div>
		<div id="tag_info_div">
			<textarea rows="3" cols="113" id="a0194z" name="a0194z"></textarea>
		</div>
   </odin:tabCont>
</div>	
	</odin:tab>
	
	<odin:textarea property="remark" label=""  rows="3" colspan="3"/>
	
</div> 	


<div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><odin:button text="清除条件" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td><odin:button text="保存条件" property="saveCon1" handler="saveConF"></odin:button>
							</td>
							<td align="center"><odin:button text="开始查询" property="mQuery" handler="dosearch"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

</div>


<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="radioC"/>
<odin:hidden property="sql"/>
<odin:hidden property="tableType"/>
<odin:hidden property="queryid"/>
<odin:hidden property="queryname"/>
<odin:hidden property="querydb" value="<%=querydb %>"/>
</html>
<script type="text/javascript">
function isParentLoad(){
	var qid =config.qid;
	if(qid!=null && qid!=''){
		realParent.picshow();
	}
}
var config ={};

/* function loadSj(){
	var qid = document.getElementById('subWinIdBussessId').value;
	alert(qid);
	if(qid!=null && qid!=''){
		config.qid=qid;
		Ext.getCmp('mQuery').setVisible(false);
		radow.doEvent('rclick', qid);
	}
	radow.doEvent('memberGrid.dogridquery');
} */
/* function clearCon(){
	var radioC = parent.document.getElementsByName("radioC");  
	alert(radioc)
    for (i=0; i<radioC.length; i++) {  
        if (radioC[i].checked) {  
        	radioC = radioC[i].value;
        	break;
        }  
    } 
   // alert(radioC);
    document.getElementById("radioC").value=radioC;
    document.getElementById("sql").value=realParent.document.getElementById("sql").value;
    
    window.onresize=resizeframe;
	resizeframe();
	
	document.getElementById('existsCheckbox').click();
}
 */


function setCheckBox(c, v){
	var check = document.getElementsByName(c);
	var arr = ","+v+",";
	//alert(arr);
	for(i=0;i<check.length;i++){
		//alert(arr.indexOf(','+check[i].value+','));
		if(arr.indexOf(','+check[i].value+',')>-1){
			check[i].checked=true;
		} else {
			check[i].checked=false;
		}
	}
	
	
}

function saveConF(){
	if(Ext.util.JSON.encode(doQuery())&&(Ext.util.JSON.encode(doQuery()))!="{}"){
		document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	}
	tfckbox('a0165','a0165v');
	tfckbox('a0201e','a0201ev');
	tfckbox('xla0801b','xla0801bv');
	tfckbox('xwa0901b','xwa0901bv');
	tfckbox('qrzxla0801b','qrzxla0801bv');
	tfckbox('qrzxwa0901b','qrzxwa0901bv');
	tfckbox('zzxla0801b','zzxla0801bv');
	tfckbox('zzxwa0901b','zzxwa0901bv');
	tfckbox('lba1404b','lba1404bv');
	tfckbox('a0601','a0601v');
	tfckbox('a0117','a0117v');
	
	var queryid = document.getElementById('queryid').value;
	var queryname = document.getElementById('queryname').value;
	if(queryid!=null && queryid!='' && 
			queryname!=null && queryname!=''){
		radow.doEvent("saveCon.onclick",queryname);
	} else {
		Ext.MessageBox.prompt("系统提示","查询名称：",function(btn,value){
			if(btn=='ok'){
				if(value.trim()==''){
					Ext.MessageBox.alert("系统提示","请输入名称！");
				}
				radow.doEvent("saveCon.onclick",value);
			}
		},this,false,"");
	}
	
}

function delCond(id){
	Ext.Msg.confirm("系统提示","是否确认删除？",function(btn) { 
		if("yes"==btn){
			radow.doEvent('deletecond',id);
		}else{
			return;
		}		
	});
}

function opRenderer(value,params,record,rowidx,colidx,ds){
	return "<a href=\"javascript:void()\" onclick=\"delCond('"+value+"')\">删除</a>";
}

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
	if(window.realParent.document.getElementById('fields')){
		window.realParent.clearFields();
	}
	if(Ext.util.JSON.encode(doQuery())&&(Ext.util.JSON.encode(doQuery()))!="{}"){
		document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	}
	tfckbox('a0165','a0165v');
	tfckbox('a0201e','a0201ev');
	tfckbox('xla0801b','xla0801bv');
	tfckbox('xwa0901b','xwa0901bv');
	tfckbox('qrzxla0801b','qrzxla0801bv');
	tfckbox('qrzxwa0901b','qrzxwa0901bv');
	tfckbox('zzxla0801b','zzxla0801bv');
	tfckbox('zzxwa0901b','zzxwa0901bv');
	tfckbox('lba1404b','lba1404bv');
	tfckbox('a0601','a0601v');
	tfckbox('a0117','a0117v');
	Ext.getCmp('remark').setVisible(false);
	radow.doEvent('mQueryonclick');
	
	//获取 毕业院校   工作单位及职务  简历的  查询条件  记录在上个页面上
	var word1=document.getElementById('a1701').value;
	var word2=document.getElementById('a0215a').value;
	var word3=document.getElementById('byyxzya08').value;
	realParent.document.getElementById("a1701Word").value=word1;
	realParent.document.getElementById("a0215aWord").value=word2;
	realParent.document.getElementById("a0814Word").value=word3;

}


Ext.onReady(function(){

	Ext.getCmp('remark').setVisible(false);
	
	var tableType = realParent.document.getElementById("tableType").value;
	document.getElementById("tableType").value = tableType;
	
	odin.setSelectValue("a0163", realParent.document.getElementById("personq").value);
	
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('queryid').value = rc.data.queryid;
		document.getElementById("resetBtn").click();
		var tableType = realParent.document.getElementById("tableType").value;
		document.getElementById("tableType").value = tableType;
		odin.setSelectValue("a0163", realParent.document.getElementById("personq").value);
		radow.doEvent('rclick',rc.data.queryid);
	});
});
function clearConbtn(){
	Ext.getCmp("tag011").setValue(false);Ext.getCmp("tag021").setValue(false);Ext.getCmp("tag031").setValue(false);Ext.getCmp("tag041").setValue(false);Ext.getCmp("tag051").setValue(false);
	Ext.getCmp("tag061").setValue(false);Ext.getCmp("tag071").setValue(false);Ext.getCmp("tag081").setValue(false);Ext.getCmp("tag091").setValue(false);Ext.getCmp("tag101").setValue(false);
	Ext.getCmp("tag111").setValue(false);Ext.getCmp("tag121").setValue(false);Ext.getCmp("tag131").setValue(false);Ext.getCmp("tag141").setValue(false);Ext.getCmp("tag151").setValue(false);
	Ext.getCmp("tag161").setValue(false);Ext.getCmp("tag171").setValue(false);Ext.getCmp("tag181").setValue(false);Ext.getCmp("tag191").setValue(false);Ext.getCmp("tag201").setValue(false);
	Ext.getCmp("tag211").setValue(false);Ext.getCmp("tag221").setValue(false);Ext.getCmp("tag231").setValue(false);Ext.getCmp("tag241").setValue(false);Ext.getCmp("tag251").setValue(false);
	Ext.getCmp("tag261").setValue(false);Ext.getCmp("tag271").setValue(false);Ext.getCmp("tag281").setValue(false);Ext.getCmp("tag291").setValue(false);Ext.getCmp("tag301").setValue(false);
	Ext.getCmp("tag311").setValue(false);Ext.getCmp("tag321").setValue(false);Ext.getCmp("tag331").setValue(false);Ext.getCmp("tag341").setValue(false);Ext.getCmp("tag351").setValue(false);
	Ext.getCmp("tag361").setValue(false);Ext.getCmp("tag371").setValue(false);Ext.getCmp("tag381").setValue(false);Ext.getCmp("tag391").setValue(false);Ext.getCmp("tag401").setValue(false);
	Ext.getCmp("tag411").setValue(false);Ext.getCmp("tag421").setValue(false);Ext.getCmp("tag431").setValue(false);Ext.getCmp("tag441").setValue(false);Ext.getCmp("tag991").setValue(false);

	document.getElementById("resetBtn").click();
	var tableType = realParent.document.getElementById("tableType").value;
	document.getElementById("tableType").value = tableType;
	odin.setSelectValue("a0163", realParent.document.getElementById("personq").value);
	//radow.doEvent('initX');
}

function collapseGroupWin(){
	/* var newWin_ = $h.getTopParent().Ext.getCmp('group');
	if(!newWin_){
	}else{
		//newWin_.collapse(false); 
		newWin_.close();
	} */
	window.close();
}
function resizeframe(){
	var conditionArea = document.getElementById("conditionArea");
	var viewSize = Ext.getBody().getViewSize();
	var treediv = document.getElementById("tree-div");
	$('#conditionArea').width(viewSize.width-245);
	$('#conditionArea').height(viewSize.height-31);
	
	//var pos = $h.pos(document.getElementById("ltb"));
	//alert(viewSize.height-pos.top);
	//document.getElementById("bottomDiv").style.marginTop = viewSize.height - pos.top-62;
	//treediv.style.height = viewSize.height-20;
	//var memberGrid = Ext.getCmp('memberGrid');
	//memberGrid.setHeight(viewSize.height-320);
	//alert(conditionArea.parentNode.parentNode.style.width);
	//conditionArea.parentNode.parentNode.style.width=viewSize.width-10;
}


function changeTag(node, codevalue){

	var codevaluelist = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '20', '21', '22', '23', '24'];
	for(var i = 0,len=codevaluelist.length; i < len; i++) {
		document.getElementById("tag" + codevaluelist[i]).style.display = "none";
	}

	document.getElementById("tag" + codevalue).style.display = "block";
	changeTagMenuHover(node);
}

//标签用
function disableInputTag(check,inputId) {
	var obj = $('#'+inputId);
	if($(check).is(':checked')) {
		obj.attr("disabled",false);
	} else {
		obj.val("");
		obj.attr("disabled","disabled");
	}
}
/**
 * ******************************************此处以下所有方法用于修改左侧菜单鼠标悬停样式【成套使用】*******************************************************
 */
Ext.onReady(function() {
	leftMenuHover();
	unbindOnmouseEvent(document.getElementById("firstTag"));
});

function changeTagMenuHover(node){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		nodes[i].style.backgroundColor = "#FFFFFF";
		bindOnmouseEvent(nodes[i]);
	}
	unbindOnmouseEvent(node);
	node.style.backgroundColor = "#1E90FF";
}

//菜单鼠标悬浮，点击，离开事件
function leftMenuHover(){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		bindOnmouseEvent(nodes[i]);
	}
}

/*
 * 重写getElementsByClassName()方法，IE8及以下没有该方法
 */
function getElementsByClassName(className, tagName) {
    if (document.getElementsByClassName) {
        // 使用现有方法
        return document.getElementsByClassName(className);
    } else {
        // 循环遍历所有标签，返回带有相应类名的元素
        var rets = [], nodes = document.getElementsByTagName(tagName);
        for (var i = 0, len = nodes.length; i < len; i++) {
            if (hasClass(nodes[i],className)) {
            	rets.push(nodes[i]);
            }
        }
        return rets;
    }
}

function hasClass(tagStr,className){  
    var arr=tagStr.className.split(/\s+/ );  //这个正则表达式是因为class可以有多个,判断是否包含  
    for (var i=0;i<arr.length;i++){  
           if (arr[i]==className){  
                 return true ;  
           }  
    }  
    return false ;  
}

function bindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标悬停事件
	node.onmouseout=function(){ node.style.backgroundColor = "#FFFFFF"; };//鼠标离开事件
	node.onmousedown=function(){node.style.backgroundColor = "#1E90FF";};//鼠标点击时触发事件
}

function unbindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标悬停事件
	node.onmouseout=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标离开事件
	node.onmousedown=function(){ node.style.backgroundColor = "#1E90FF"; };//鼠标点击时触发事件
}

//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0194z = document.getElementById("a0194z").value;
	var a0194s = document.getElementById("a0194s").value;
	
	if(check.checked) {
		if( a0194z == null || a0194z == '' ){
			a0194z = valuename;
		}else{
			a0194z = a0194z + "；" + valuename;
		}	
		if( a0194s == null || a0194s == '' ){
			a0194s = value;
		}else{
			a0194s = a0194s  + "；" + value;
		}			
	}else{
		a0194z = a0194z.replace('；'+valuename, '').replace(valuename+'；', '').replace(valuename, '');
		a0194s = a0194s.replace('；'+value, '').replace(value+'；', '').replace(value, '');
	}
	document.getElementById("a0194z").value = a0194z;
	document.getElementById("a0194s").value = a0194s;
	
}


//tab页切换事件
function grantTabChange(tabObj,item){
	if(item.getId()=="tab2"){
		document.getElementById("tab21").style.dispaly="block"
	}else if(item.getId()=="tab3"){
		document.getElementById("tab31").style.dispaly="block"
	}else if(item.getId()=="tab4"){
		document.getElementById("tab41").style.dispaly="block"
	}else if(item.getId()=="tab5"){
		document.getElementById("tab51").style.dispaly="block"
	}
	
	
}


//a0193tag标签选择
function a0193tagInfo(check,value,name){
	
	var a0193s=document.getElementById("a0193s").value;
	var a0193z=document.getElementById("a0193z").value;
	if(check.checked) {
			if( a0193z == null || a0193z == '' ){
				a0193z = name;
			}else{
				a0193z = a0193z + "；" + name;
			}	
			if( a0193s == null || a0193s == '' ){
				a0193s = value;
			}else{
				a0193s = a0193s  + "；" + value;
			}			
		}else{
			
			a0193z = a0193z.replace("；"+name, '').replace(name+"；",'').replace(name, '');
			a0193s = a0193s.replace("；"+value, '').replace(value+"；",'').replace(value, '');
		}
		document.getElementById("a0193z").value = a0193z;
		document.getElementById("a0193s").value = a0193s;
		
		}

	function showCheckbox(){
			var a0193s=document.getElementById("a0193s").value;

			var a0194s=document.getElementById("a0194s").value;
		
			if(a0193s!=null&&a0193s!=''){
					var arr=a0193s.split("；");
					for(var i=0 ; i<arr.length ; i++){
							
						document.getElementById("tag"+arr[i]).checked=true;


					}
				}
			if(a0194s!=null&&a0194s!=''){
				var brr=a0194s.split("；");
				for(var j=0 ; j<brr.length ; j++){
				
						Ext.getCmp("tag"+brr[j]+"1").setValue(true);
						
					}


			}

		}
	

resizeframe();




</script>


<%@include file="/pages/customquery/otjs.jsp" %>

