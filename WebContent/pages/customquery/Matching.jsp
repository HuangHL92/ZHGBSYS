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
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">����ѡ��</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" checked="checked"><font style="font-size: 13">�����¼�</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 300px;"></div>
				<odin:editgrid2 property="memberGrid" hasRightMenu="false" pageSize="100" bbarId="pageToolBar"
					title="��ѯ����" autoFill="true" height="360" url="/" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="queryname" ></odin:gridDataCol>
						<odin:gridDataCol name="queryid" isLast="true"></odin:gridDataCol>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 editor="text" edited="false" header="����" width="200" dataIndex="queryname"></odin:gridEditColumn2>
						<odin:gridEditColumn2 editor="text" edited="false" header="����" width="50" renderer="opRenderer" dataIndex="queryid" isLast="true" ></odin:gridEditColumn2>
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
       <odin:tabItem title="������Ϣ��ְ��" id="tab1" ></odin:tabItem>     
       <odin:tabItem title="ѧ��ѧλ��רҵ����" id="tab2" ></odin:tabItem>
       <odin:tabItem title="�����뽱��" id="tab3" ></odin:tabItem>
       <odin:tabItem title="��Ҫ����" id="tab4" ></odin:tabItem>
       <odin:tabItem title="��Ϥ����" id="tab5"  isLast="true"></odin:tabItem>
   		</odin:tabModel>
   		<div id="tab11">
  		<odin:tabCont itemIndex="tab1" className="tab"   >
   		<odin:groupBoxNew title="����" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%">
				<tr>
					<%-- <odin:textEdit property="a0101" label="��Ա����" maxlength="36" /> --%>
					<%-- <odin:textEdit property="a0184" label="���֤��" maxlength="18" /> --%>
					<%-- <tags:PublicOrgCheck label="ѡ�����" property="SysOrgTree"/> --%>
					
					<tags:PublicTextIconEdit isLoadData="false"  property="a0111" label="����" readonly="true" codename="code_name3" codetype="ZB01"   />
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0104" type="radio" value="1" />�� </label> 
						<label><input name="a0104" type="radio" value="2" />Ů </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td >
						<table  ><tr>
							<odin:numberEdit property="ageA"  maxlength="3" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:numberEdit property="ageB" maxlength="3" width="72" />
						</tr></table>
					</td>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="checkbox" value="01" />���� </label> 
									<label><input name="a0117" type="checkbox" value="02" />��������</label>
									<odin:hidden property="a0117v"/> 
								</td>
							</tr>
						</table>
					</td> 
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="�������޼����ֹ"></odin:NewDateEditTag>
					
					
				</tr>
				
				<tr>
				<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">��������</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
						</tr></table>
				</td>
					 <td noWrap="nowrap" align=right><span id="zhichenSpanId" style="FONT-SIZE: 12px">ְ&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="5">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB8561   -->
									<label><input name="a0601" type="checkbox" value="1" />����</label> 
									<label><input name="a0601" type="checkbox" value="2" />���� </label> 
									<label><input name="a0601" type="checkbox" value="3" />�м� </label> 
									<br style="line-height: 1px;" /> 
									<label><input name="a0601" type="checkbox" value="4,5" />���� </label> 
									<label><input name="a0601" type="checkbox" value="9" />��ְ��</label>
									<odin:hidden property="a0601v"/> 
								</td>
							</tr>
						</table>
					</td> 
					
				</tr>
				
				
				<tr>
				
				<odin:select2 property="a0160" label="��Ա���" codeType="ZB125" multiSelect="true"></odin:select2>
				<td noWrap="nowrap" align=right><span id="a0144SpanId" style="FONT-SIZE: 12px">�μ��й�ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0144A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0144B" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
				
				<tr>
					<%-- <td noWrap="nowrap" align=right><span id="a0221SpanId" style="FONT-SIZE: 12px">��ְ����</span>&nbsp;</td>
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
					</td> --%>
					
					<%-- <td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!-- GB3304   -->
									<label><input name="a0117" type="checkbox" value="01" />���� </label> 
									<label><input name="a0117" type="checkbox" value="02" />��������</label>
									<odin:hidden property="a0117v"/> 
								</td>
							</tr>
						</table>
					</td>  --%>

				</tr>
				
<%-- 				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB148" readonly="true" label="��ְ��"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">��ְ��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:textEdit property="a1701" label="����" maxlength="100" >(����)</odin:textEdit>
				</tr>	 --%>
				
				<tr>
				<odin:select2 property="a0141" label="������ò" codeType="GB4762" multiSelect="true"></odin:select2>
					
					<%-- <odin:textEdit property="a0192a" label="ְ��ȫ��" maxlength="100" >(����)</odin:textEdit> --%>
					
					<td noWrap="nowrap" align=right><span id="a0134SpanId" style="FONT-SIZE: 12px">�μӹ���ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0134A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0134B" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
					
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false"  property="a0114" label="������" codename="code_name3" codetype="ZB01" readonly="true"  />
					<odin:select2 property="a0188" label="�Ƿ������������ְ����" codeType="XZ09" />
					<!-- <td noWrap="nowrap" align=right><span id="xgsjSpanId" style="FONT-SIZE: 12px">���ά��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="xgsjA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="xgsjB" maxlength="8" width="72" />
						</tr></table>
					</td>-->
				</tr>
				
				<tr>
				<td noWrap="nowrap" align=right><span id="a0165SpanId" style="FONT-SIZE: 12px">��Ա�������</span>&nbsp;</td>
				<td style="FONT-SIZE: 12px" colspan="5">
					<label><input name="a0165" type="checkbox" value="01" />�������ɲ�</label>
					<label><input name="a0165" type="checkbox" value="02" />ʡ����ί����ɲ� </label> 
					<label><input name="a0165" type="checkbox" value="03" />�м���ί����ɲ� </label>
					<label><input name="a0165" type="checkbox" value="05" />��ίί�й���ɲ�</label>  
					<label><input name="a0165" type="checkbox" value="04" />�ؼ���ί����ɲ�</label>
					<label><input name="a0165" type="checkbox" value="09" />����</label> 
					<odin:hidden property="a0165v"/>
				</td>
				</tr>
				<tr>
					<odin:select2 property="a0163" label="��Ա״̬" codeType="ZB126" multiSelect="true"></odin:select2>
<%-- 					<odin:textEdit property="a1701" label="��&nbsp;&nbsp;&nbsp;��" maxlength="100" >
					<br/><input name="intersection1" id="intersection1" type="checkbox" value="0" ��nclick="this.value=(this.value==0)?1:0"/>��ѡ�в���,δѡ�н���,��;������
					</odin:textEdit> --%>
				</tr>
				<tr>
					<odin:textEdit property="byyxzya08" label="��ҵԺУ��רҵ" maxlength="36">
					<br/><input name="intersection3" id="intersection3" type="checkbox" value="0" ��nclick="this.value=(this.value==0)?1:0"/>��ѡ�в���,δѡ�н���,��;������
					</odin:textEdit>
					
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">�Ƿ�����Ա�</span>&nbsp;</td>
					
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="Contrast" type="radio" value="1" checked="checked"  />�� </label> 
						<label><input name="Contrast" type="radio" value="2" />��</label> 
						
					</td>
					
					
				</tr>
				<odin:hidden property="contrastOfA0000s" />
			</table>
		</div>
	</odin:groupBoxNew>
	
	
	
	
	
	<odin:groupBoxNew title="ְ��" property="ggBox2" contentEl="zwDiv" collapsible="false" collapsed="false">
		<div id="zwDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0215a" label="ְ������" maxlength="100" ></odin:textEdit>
					<odin:select2 property="a0201d" label="�Ƿ��쵼��Ա" codeType="XZ09"></odin:select2>
				</tr>
				
				<tr>
				
					<td noWrap="nowrap" align=right><span style="FONT-SIZE: 12px"></span>&nbsp;</td>
					<td noWrap="nowrap" style="FONT-SIZE: 12px">
						<input name="intersection2" id="intersection2" type="checkbox" value="0" ��nclick="this.value=(this.value==0)?1:0"/>��ѡ�в���,δѡ�н���,��;������
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0201eSpanId" style="FONT-SIZE: 12px">��Ա���</span>&nbsp;</td>
					<td colspan="2">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="a0201e" type="checkbox" value="1" />��ְ</label> 
									<label><input name="a0201e" type="checkbox" value="3" />��ְ</label> 
									<label><input name="a0201e" type="checkbox" value="Z" />����</label> 
									<odin:hidden property="a0201ev"/>
								</td>
							</tr>
						</table>
					</td>
					
				</tr>
				<tr>					
					<odin:select2 property="a0219" label="�Ƿ��쵼ְ��" codeType="ZB42"></odin:select2>
					<td noWrap="nowrap" align=right>&nbsp;</td>
					<td style="FONT-SIZE: 12px">
						<label><input name="qtxzry" id="qtxzry" type="checkbox" value="1" />������ְ��Ա����ְ��</label> 
					</td>
				</tr>
				<tr>
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
				</tr>
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a0192e"  codetype="ZB148" readonly="true" label="��ְ��"></tags:PublicTextIconEdit>
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">��ְ��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>
<%-- 	<odin:groupBoxNew title="��ͥ��Ա" property="ggBox6" contentEl="jtcyDiv" collapsed="false"
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
   </odin:tabCont>
   
   
   </div>
   
   
   
   <div id="tab21" style="display: none" >
   <odin:tabCont itemIndex="tab2" className="tab">
   <odin:groupBoxNew title="ѧ��ѧλ" property="ggBox3" contentEl="xlDiv"
		collapsible="false" collapsed="false">
		<div id="xlDiv">
			<table style="width: 100%">
				<tr>
					<td noWrap="nowrap" align=right><span id="xla0801bSpanId" style="FONT-SIZE: 12px">���ѧ��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB64  -->
									<label><input name="xla0801b" type="checkbox" value="1" />�о���</label> 
									<label><input name="xla0801b" type="checkbox" value="2" />��ѧ</label> 
									<label><input name="xla0801b" type="checkbox" value="3" />��ר</label> 
									<label><input name="xla0801b" type="checkbox" value="4" />��ר</label> 
									<label><input name="xla0801b" type="checkbox" value="6,7,8,9" />����</label> 
									<odin:hidden property="xla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xla0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="xla0824" label="רҵ"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="xwa0901bSpanId" style="FONT-SIZE: 12px">���ѧλ</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="xwa0901b" type="checkbox" value="1,2" />��ʿ</label> 
									<label><input name="xwa0901b" type="checkbox" value="3" />˶ʿ</label> 
									<label><input name="xwa0901b" type="checkbox" value="4" />ѧʿ</label> 
									<odin:hidden property="xwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="xwa0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="xwa0824" label="רҵ" maxlength="100" ></odin:textEdit>
					
				</tr>
				
				
				
				<tr>
					<td noWrap="nowrap" align=right><span id="qrzxla0801bSpanId" style="FONT-SIZE: 12px">ȫ�������ѧ��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="qrzxla0801b" type="checkbox" value="1" />�о���</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="2" />��ѧ</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="3" />��ר</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="4" />��ר</label> 
									<label><input name="qrzxla0801b" type="checkbox" value="6,7,8,9" />����</label> 
									<odin:hidden property="qrzxla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="qrzxla0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="qrzxla0824" label="רҵ"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="qrzxwa0901bSpanId" style="FONT-SIZE: 12px">ȫ�������ѧλ</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="qrzxwa0901b" type="checkbox" value="1,2" />��ʿ</label> 
									<label><input name="qrzxwa0901b" type="checkbox" value="3" />˶ʿ</label> 
									<label><input name="qrzxwa0901b" type="checkbox" value="4" />ѧʿ</label> 
									<odin:hidden property="qrzxwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="qrzxwa0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="qrzxwa0824" label="רҵ" maxlength="100" ></odin:textEdit>
					
				</tr>
				
				
				
				<tr>
					<td noWrap="nowrap" align=right><span id="zzxla0801bSpanId" style="FONT-SIZE: 12px">��ְ���ѧ��</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="zzxla0801b" type="checkbox" value="1" />�о���</label> 
									<label><input name="zzxla0801b" type="checkbox" value="2" />��ѧ</label> 
									<label><input name="zzxla0801b" type="checkbox" value="3" />��ר</label> 
									<label><input name="zzxla0801b" type="checkbox" value="4" />��ר</label> 
									<label><input name="zzxla0801b" type="checkbox" value="6,7,8,9" />����</label> 
									<odin:hidden property="zzxla0801bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="zzxla0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="zzxla0824" label="רҵ"  ></odin:textEdit>
					
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id="zzxwa0901bSpanId" style="FONT-SIZE: 12px">��ְ���ѧλ</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="zzxwa0901b" type="checkbox" value="1,2" />��ʿ</label> 
									<label><input name="zzxwa0901b" type="checkbox" value="3" />˶ʿ</label> 
									<label><input name="zzxwa0901b" type="checkbox" value="4" />ѧʿ</label> 
									<odin:hidden property="zzxwa0901bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<odin:textEdit property="zzxwa0814" label="��ҵԺУ" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="zzxwa0824" label="רҵ" maxlength="100" ></odin:textEdit>
					
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>
	<odin:groupBoxNew title="רҵ����"  property="ggBox7" contentEl="zyDiv"   collapsible="false" collapsed="false" >
			<div id="zyDiv">
				<table style="width:100%">
					<tr>
						 <tags:PublicTextIconEdit property="a0601a" label="רҵ�����ʸ����"  readonly="true" codetype="GB8561"  ></tags:PublicTextIconEdit>	
						 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 
					</tr>
				</table>		
			</div>
	</odin:groupBoxNew>
	
	
   </odin:tabCont>
   </div>
   <div id="tab31" style="display: none">
   <odin:tabCont itemIndex="tab3" className="tab">
   <odin:groupBoxNew title="����" property="ggBox4" contentEl="jcDiv"
		collapsible="false" collapsed="false">
		<div id="jcDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a14z101" label="��������" maxlength="100" >(����)</odin:textEdit>
					<td noWrap="nowrap" align=right><span id="lba1404bSpanId" style="FONT-SIZE: 12px">�������</span>&nbsp;</td>
					<td colspan="1">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px"><!--ZB65  -->
									<label><input name="lba1404b" type="checkbox" value="01" />����</label> 
									<label><input name="lba1404b" type="checkbox" value="02" />�ͽ�</label> 
									<odin:hidden property="lba1404bv"/>
								</td>
							</tr>
						</table>
					</td>
					
					<tags:PublicTextIconEdit isLoadData="false" property="a1404b" label="�������ƴ���"  readonly="true" codetype="ZB65" />
					
				</tr>
				
				<tr>
					<tags:PublicTextIconEdit isLoadData="false" property="a1415" label="�ܽ���ʱְ����" readonly="true" codetype="ZB09" />
					<odin:select2 property="a1414" label="��׼���ؼ���"  codeType="ZB03" />
					<tags:PublicTextIconEdit isLoadData="false" property="a1428" label="��׼��������" readonly="true" codetype="ZB128" />
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>
	
	
	<odin:groupBoxNew title="��ȿ���" property="ggBox5" contentEl="ndkhDiv" collapsed="false"
		collapsible="false">
		<div id="ndkhDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a15z101" label="��ȿ�������" maxlength="100" >(����)</odin:textEdit>
					<odin:select2 property="a1521" label="�������"  maxlength="4" multiSelect="true" />
					<tags:PublicTextIconEdit isLoadData="false" property="a1517" label="���˽������"  codetype="ZB18" readonly="true"/>
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
		<div class="leftMenu" onclick="changeTag(this, '01');" id="firstTag" style="border-width: 1px; background: #1E90FF">������һ���ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '02');">ʡ����ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '03');">��ʡ������ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '04');">�أ��С��ݡ��ˣ�ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '05');">�أ��С������죩ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '06');">���򣨽ֵ���ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '07');">������ҵְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '08');">��ҵְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '09');">��Уְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '10');">����Ժ��ְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '11');">����ҽԺְ��</div>
		<div class="leftMenu" onclick="changeTag(this, '12');">���Ѽ�������������</div>
		<div class="leftMenu" onclick="changeTag(this, '13');">�ſھ���</div>
		<div class="leftMenu" onclick="changeTag(this, '14');">����������һίԱ���</div>
		<div class="leftMenu" onclick="changeTag(this, '15');">������������</div>
		<div class="leftMenu" onclick="changeTag(this, '16');">���龭��</div>
		<div class="leftMenu" onclick="changeTag(this, '17');">������������������ó���Ⱦ���</div>
		<div class="leftMenu" onclick="changeTag(this, '20');">���⹤������</div>
		<div class="leftMenu" onclick="changeTag(this, '21');">ʡ����ҵ�쵼���ӹ�������</div>
		<div class="leftMenu" onclick="changeTag(this, '22');">�¼���ҵ��ְ��ְ����</div>
		<div class="leftMenu" onclick="changeTag(this, '23');">�ܲ�ְ�ܲ�����ְ����</div>
		<div class="leftMenu" onclick="changeTag(this, '24');">�Ϲ����ɹ�������</div>
		<div class="leftMenu" onclick="changeTag(this, '18');">��������</div>
	</div>
	<div id="right_div">
	    <!-- ������һ���ְ�� -->
		<table id="tag01" style="display: block;">
			<tr>
				<td>
					<input type="checkbox" name="tag0101" id="tag0101" onclick="a0193tagInfo(this,'0101','��������λ˾�ּ���ְ�쵼ְ��')" >
					<label>��������λ˾�ּ���ְ�쵼ְ��&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0102" id="tag0102" onclick="a0193tagInfo(this,'0102','��������λ˾�ּ���ְ�쵼ְ��')"  >
					<label>��������λ˾�ּ���ְ�쵼ְ��&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0103" id="tag0103" onclick="a0193tagInfo(this,'0103','��������λ˾�ּ���ְ�쵼ְ��')"  >
					<label>��������λ˾�ּ���ְ�쵼ְ��&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0104" id="tag0104" onclick="a0193tagInfo(this,'0104','��������λ˾�ּ���ְ�쵼ְ��')"  >
					<label>��������λ˾�ּ���ְ�쵼ְ��&nbsp;&nbsp;</label>
				</td>	
			</tr>	
			<tr>
				<td>
					<input type="checkbox" name="tag0105" id="tag0105" onclick="a0193tagInfo(this,'0105','�ش�����ְ�쵼ְ��')"  >
					<label>�ش�����ְ�쵼ְ��</label>
				</td>	
			</tr>
		</table>
		<table id="tag02" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0201" id="tag0201" onclick="a0193tagInfo(this,'0201','ʡֱ������ְ�쵼ְ��')"  >
					<label>ʡֱ������ְ�쵼ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0202" id="tag0202" onclick="a0193tagInfo(this,'0202','ʡֱ���Ÿ�ְ�쵼ְ��')"  >
					<label>ʡֱ���Ÿ�ְ�쵼ְ�� </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0203" id="tag0203" onclick="a0193tagInfo(this,'0203','ʡֱ�����ش�����ְ�쵼ְ��')"  >
					<label>ʡֱ�����ش�����ְ�쵼ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0204" id="tag0204" onclick="a0193tagInfo(this,'0204','ʡֱ�����ش�����ְ�쵼ְ��')" >
					<label>ʡֱ�����ش�����ְ�쵼ְ��</label>
				</td>	
			</tr>
		</table>
		<!-- ��ʡ������ְ�� -->
		<table id="tag03" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0301" id="tag0301" onclick="a0193tagInfo(this,'0301','��ʡ�����в�����������ְ�ĸ����')" >
					<label>��ʡ�����в�����������ְ�ĸ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0302" id="tag0302" onclick="a0193tagInfo(this,'0302','��ʡ��������������ְ')" >
					<label>��ʡ��������������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0303" id="tag0303" onclick="a0193tagInfo(this,'0303','��ʡ�����е�����ְ')" >
					<label>��ʡ�����е�����ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0304" id="tag0304" onclick="a0193tagInfo(this,'0304','��ʡ������ֱ��������ְ')" >
					<label>��ʡ������ֱ��������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0305" id="tag0305" onclick="a0193tagInfo(this,'0305','��ʡ������ֱ�����Ÿ�ְ')" >
					<label>��ʡ������ֱ�����Ÿ�ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0306" id="tag0306" onclick="a0193tagInfo(this,'0306','��ʡ������ֱ�������в���ְ')" >
					<label>��ʡ������ֱ�������в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0307" id="tag0307" onclick="a0193tagInfo(this,'0307','��ʡ������ֱ�������в㸱ְ')" >
					<label>��ʡ������ֱ�������в㸱ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- �أ��С��ݡ��ˣ�ְ�� -->
		<table id="tag04" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0401" id="tag0401" onclick="a0193tagInfo(this,'0401','�أ��С��ݡ��ˣ���ί��ְ')" >
					<label>�أ��С��ݡ��ˣ���ί��ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0402" id="tag0402" onclick="a0193tagInfo(this,'0402','�أ��С��ݡ��ˣ�������ְ')" >
					<label>�أ��С��ݡ��ˣ�������ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0403" id="tag0403" onclick="a0193tagInfo(this,'0403','�أ��С��ݡ��ˣ�������������ְ�ĸ����')" >
					<label>�أ��С��ݡ��ˣ�������������ְ�ĸ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0404" id="tag0404" onclick="a0193tagInfo(this,'0404','�أ��С��ݡ��ˣ���������ְ')" >
					<label>�أ��С��ݡ��ˣ���������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0405" id="tag0405" onclick="a0193tagInfo(this,'0405','�أ��С��ݡ��ˣ�������ְ')" >
					<label>�أ��С��ݡ��ˣ�������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0406" id="tag0406" onclick="a0193tagInfo(this,'0406','�أ��С��ݡ��ˣ�ֱ��������ְ')" >
					<label>�أ��С��ݡ��ˣ�ֱ��������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0407" id="tag0407" onclick="a0193tagInfo(this,'0407','��ֱ�����Ű��ӳ�Ա����ְ��')" >
					<label>��ֱ�����Ű��ӳ�Ա����ְ�� </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0408" id="tag0408" onclick="a0193tagInfo(this,'0408','��ֱ�������в���ְ')" >
					<label>��ֱ�������в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0409" id="tag0409" onclick="a0193tagInfo(this,'0409','��ֱ�������в㸱ְ')"  >
					<label>��ֱ�������в㸱ְ </label>
				</td>	
			</tr>					
		</table>	
		<!-- �أ��С������죩ְ�� -->
		<table id="tag05" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0501" id="tag0501" onclick="a0193tagInfo(this,'0501','�أ��С������죩��ί��ְ')" >
					<label>�أ��С������죩��ί��ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0502" id="tag0502" onclick="a0193tagInfo(this,'0502','�أ��С������죩������ְ')" >
					<label>�أ��С������죩������ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0504" id="tag0504" onclick="a0193tagInfo(this,'0504','�أ��С������죩��ƶ�����ڼ�ƶ���ص�����ְ')" >
					<label>�أ��С������죩��ƶ�����ڼ�ƶ���ص�����ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0503" id="tag0503" onclick="a0193tagInfo(this,'0503','�أ��С������죩������ְ ')" >
					<label>�أ��С������죩������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0505" id="tag0505" onclick="a0193tagInfo(this,'0505','�ؼ�������ְ')" >
					<label>�ؼ�������ְ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0506" id="tag0506" onclick="a0193tagInfo(this,'0506','�ؼ����Ű��ӳ�Ա����ְ��')" >
					<label>�ؼ����Ű��ӳ�Ա����ְ�� </label>
				</td>	
			</tr>
		</table>	
		<!-- ���򣨽ֵ���ְ�� -->
		<table id="tag06" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0601" id="tag0601" onclick="a0193tagInfo(this,'0601','���򣨽ֵ���������ְ')" >
					<label>���򣨽ֵ���������ְ  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0602" id="tag0602" onclick="a0193tagInfo(this,'0602','���򣨽ֵ����������ӳ�Ա' )" >
					<label>���򣨽ֵ����������ӳ�Ա </label>
				</td>	
			</tr>
		</table>	
		<!-- ������ҵְ��  -->
		<table id="tag07" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0701" id="tag0701" onclick="a0193tagInfo(this,'0701','�йܽ�����ҵ��ְ')" >
					<label>�йܽ�����ҵ��ְ  </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0702" id="tag0702" onclick="a0193tagInfo(this,'0702','�йܽ�����ҵ������ְ')" >
					<label>�йܽ�����ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0703" id="tag0703" onclick="a0193tagInfo(this,'0703','�йܽ�����ҵ������ְ')" >
					<label>�йܽ�����ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0704" id="tag0704" onclick="a0193tagInfo(this,'0704','���뵥λ����������ҵ��ְ')" >
					<label>���뵥λ����������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0705" id="tag0705" onclick="a0193tagInfo(this,'0705','���뵥λ����������ҵ��ְ')" >
					<label>���뵥λ����������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0708" id="tag0708" onclick="a0193tagInfo(this,'0708','ʡ��������ҵ��ְ')" >
					<label>ʡ��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0709" id="tag0709" onclick="a0193tagInfo(this,'0709','ʡ��������ҵ��ְ')" >
					<label>ʡ��������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0712" id="tag0712" onclick="a0193tagInfo(this,'0712','����������ҵ��ְ')" >
					<label>����������ҵ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ��ҵְ�� -->
		<table id="tag08" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag0801" id="tag0801" onclick="a0193tagInfo(this,'0801','�й���ҵ��ְ')"  >
					<label>�й���ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0802" id="tag0802" onclick="a0193tagInfo(this,'0802','�й���ҵ������ְ')"  >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0803" id="tag0803" onclick="a0193tagInfo(this,'0803','�й���ҵ������ְ')"  >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0820" id="tag0820" onclick="a0193tagInfo(this,'0820','�й���ҵ������ְ')"  >
					<label>�й���ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0804" id="tag0804" onclick="a0193tagInfo(this,'0804','����Ժ����ί�������ҵ��ְ')"  >
					<label>����Ժ����ί�������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0805" id="tag0805" onclick="a0193tagInfo(this,'0805','����Ժ����ί�������ҵ��ְ')"  >
					<label>����Ժ����ί�������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0806" id="tag0806" onclick="a0193tagInfo(this,'0806','����Ժ����ί�������ҵ������ְ')"  >
					<label>����Ժ����ί�������ҵ������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0813" id="tag0813" onclick="a0193tagInfo(this,'0813','���뵥λ������ҵ��ְ')"  >
					<label>���뵥λ������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0814" id="tag0814" onclick="a0193tagInfo(this,'0814','���뵥λ������ҵ��ְ')"  >
					<label>���뵥λ������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0808" id="tag0808" onclick="a0193tagInfo(this,'0808','ʡ����ҵ��ְ')"  >
					<label>ʡ����ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0809" id="tag0809" onclick="a0193tagInfo(this,'0809','ʡ����ҵ��ְ')"  >
					<label>ʡ����ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag0812" id="tag0812" onclick="a0193tagInfo(this,'0812','������ҵ��ְ')"  >
					<label>������ҵ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ��Уְ�� -->
		<div id="tag09" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag0901" id="tag0901" onclick="a0193tagInfo(this,'0901','�йܸ�У��ְ')" >
						<label>�йܸ�У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0902" id="tag0902" onclick="a0193tagInfo(this,'0902','�йܸ�У�в���ְ')" >
						<label>�йܸ�У�в���ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0903" id="tag0903" onclick="a0193tagInfo(this,'0903','�йܸ�У�в㸱ְ')"  >
						<label>�йܸ�У�в㸱ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0904" id="tag0904" onclick="a0193tagInfo(this,'0904','������У��ְ')" >
						<label>������У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0905" id="tag0905" onclick="a0193tagInfo(this,'0905','������У��ְ')" >
						<label>������У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0906" id="tag0906" onclick="a0193tagInfo(this,'0906','������У�в���ְ')" >
						<label>������У�в���ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0907" id="tag0907" onclick="a0193tagInfo(this,'0907','ʡ����У��ְ')" >
						<label>ʡ����У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0908" id="tag0908" onclick="a0193tagInfo(this,'0908','ʡ����У��ְ')" >
						<label>ʡ����У��ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0909" id="tag0909" onclick="a0193tagInfo(this,'0909','ʡ����У�в���ְ')" >
						<label>ʡ����У�в���ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0910" id="tag0910" onclick="a0193tagInfo(this,'0910','���ҡ�˫һ���������У')"  >
						<label>���ҡ�˫һ���������У</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0911" id="tag0911" onclick="a0193tagInfo(this,'0911','ʡ�ص㽨���У')" >
						<label>ʡ�ص㽨���У</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0912" id="tag0912" onclick="a0193tagInfo(this,'0912','���Ƹ�У��ί���')" >
						<label>���Ƹ�У��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0913" id="tag0913" onclick="a0193tagInfo(this,'0913','���Ƹ�УУ��')" >
						<label>���Ƹ�УУ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0914" id="tag0914" onclick="a0193tagInfo(this,'0914','���Ƶ�ί�����')" >
						<label>���Ƶ�ί�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0915" id="tag0915" onclick="a0193tagInfo(this,'0915','���Ƹ�У��У��')"  >
						<label>���Ƹ�У��У��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0916" id="tag0916" onclick="a0193tagInfo(this,'0916','���Ƹ�У��ίίԱ')"  >
						<label>���Ƹ�У��ίίԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0917" id="tag0917" onclick="a0193tagInfo(this,'0917','��ְԺУ��ί���')" >
						<label>��ְԺУ��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0918" id="tag0918" onclick="a0193tagInfo(this,'0918','��ְԺУУ��')" >
						<label>��ְԺУУ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0919" id="tag0919" onclick="a0193tagInfo(this,'0919','��ְԺУ��ί�����')" >
						<label>��ְԺУ��ί�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0920" id="tag0920" onclick="a0193tagInfo(this,'0920','��ְԺУ��У��')" >
						<label>��ְԺУ��У��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0921" id="tag0921" onclick="a0193tagInfo(this,'0921','���Ƹ�У����ѧԺ��ί���')"  >
						<label>���Ƹ�У����ѧԺ��ί���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0922" id="tag0922" onclick="a0193tagInfo(this,'0922','���Ƹ�У����ѧԺԺ��')" >
						<label>���Ƹ�У����ѧԺԺ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0923" id="tag0923" onclick="a0193tagInfo(this,'0923','���Ƹ�У������ְ')"  >
						<label>���Ƹ�У������ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0924" id="tag0924" onclick="a0193tagInfo(this,'0924','���Ƹ�У���Ÿ�ְ')" >
						<label>���Ƹ�У���Ÿ�ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag0925" id="tag0925" onclick="a0193tagInfo(this,'0925','�еط��쵼��������')" >
						<label>�еط��쵼��������</label>
					</td>	
				</tr>
			</table>	
		</div>
		<!-- ����Ժ��ְ��  -->
		<table id="tag10" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1001" id="tag1001" onclick="a0193tagInfo(this,'1001','������͹���Ժֱ������Ժ�����������ְ')" >
					<label>������͹���Ժֱ������Ժ�����������ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1002" id="tag1002" onclick="a0193tagInfo(this,'1002','������͹���Ժֱ������Ժ�����������ְ')"  >
					<label>������͹���Ժֱ������Ժ�����������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1003" id="tag1003" onclick="a0193tagInfo(this,'1003','���뵥λ��������Ժ����ְ')"  >
					<label>���뵥λ��������Ժ����ְ&nbsp;&nbsp;</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1004" id="tag1004" onclick="a0193tagInfo(this,'1004','���뵥λ��������Ժ����ְ')" >
					<label>���뵥λ��������Ժ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1005" id="tag1005" onclick="a0193tagInfo(this,'1005','ʡ������Ժ����ְ')" >
					<label>ʡ������Ժ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1006" id="tag1006" onclick="a0193tagInfo(this,'1006','ʡ������Ժ����ְ')" >
					<label>ʡ������Ժ����ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ����ҽԺְ�� -->
		<table id="tag11" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1101" id="tag1101" onclick="a0193tagInfo(this,'1101','����ίֱ��ҽԺ��ְ')" >
					<label>����ίֱ��ҽԺ��ְ&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1102" id="tag1102" onclick="a0193tagInfo(this,'1102','ʡ������Ժ����ְ')" >
					<label>ʡ������Ժ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1103" id="tag1103" onclick="a0193tagInfo(this,'1103','����ίֱ��ҽԺ�в���ְ')"  >
					<label>����ίֱ��ҽԺ�в���ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1104" id="tag1104" onclick="a0193tagInfo(this,'1104','�йܸ�У����ҽԺ��ְ')" >
					<label>�йܸ�У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1105" id="tag1105" onclick="a0193tagInfo(this,'1105','�йܸ�У����ҽԺ��ְ')"  >
					<label>�йܸ�У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1106" id="tag1106" onclick="a0193tagInfo(this,'1106','������У����ҽԺ��ְ')" >
					<label>������У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1107" id="tag1107" onclick="a0193tagInfo(this,'1107','������У����ҽԺ��ְ')" >
					<label>������У����ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1108" id="tag1108" onclick="a0193tagInfo(this,'1108','ʡ������ҽԺ��ְ')" >
					<label>ʡ������ҽԺ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1109" id="tag1109" onclick="a0193tagInfo(this,'1109','ʡ������ҽԺ��ְ')" >
					<label>ʡ������ҽԺ��ְ</label>09
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1110" id="tag1110" onclick="a0193tagInfo(this,'1110','��������ҽԺ��ְ')" >
					<label>��������ҽԺ��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ���Ѽ�������������  -->
		<table id="tag12" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1201" id="tag1201" onclick="a0193tagInfo(this,'1201','�½���������')" >
					<label>�½���������&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1202" id="tag1202" onclick="a0193tagInfo(this,'1202','���ع�������')" >
					<label>���ع�������&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1203" id="tag1203" onclick="a0193tagInfo(this,'1203','�ຣ��������')" >
					<label>�ຣ��������&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1204" id="tag1204" onclick="a0193tagInfo(this,'1204','ƶ��������������')" >
					<label>ƶ��������������&nbsp;&nbsp;</label>
					
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1205" id="tag1205" onclick="a0193tagInfo(this,'1205','�����������Ϲ�ҵ���غ͸�������')" >
					<label>�����������Ϲ�ҵ���غ͸�������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1206" id="tag1206" onclick="a0193tagInfo(this,'1206','������������')" >
					<label>������������ </label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1207" id="tag1207" onclick="a0193tagInfo(this,'1207','�������Ѽ�������������')" >
					<label>�������Ѽ�������������</label>
				</td>	
			</tr>
		</table>	
		<!-- �ſھ���  -->
		<table id="tag13" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1301" id="tag1301" onclick="a0193tagInfo(this,'1301','��������Ǵ����')" >
					<label>��������Ǵ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1302" id="tag1302" onclick="a0193tagInfo(this,'1302','�����벿�ţ���λ����ְ')" >
					<label>�����벿�ţ���λ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1303" id="tag1303" onclick="a0193tagInfo(this,'1303','��ʡί��ְ')" >
					<label>�����벿�ţ���λ����ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1304" id="tag1304" onclick="a0193tagInfo(this,'1304','��ʡί��ְ')" >
					<label>��ʡί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1305" id="tag1305" onclick="a0193tagInfo(this,'1305','��ʡί��ְ')" >
					<label>��ʡί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1306" id="tag1306" onclick="a0193tagInfo(this,'1306','��ʡ��������ί��ְ')" >
					<label>��ʡ��������ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1307" id="tag1307" onclick="a0193tagInfo(this,'1307','��ʡ��������ί��ְ')" >
					<label>��ʡ��������ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1308" id="tag1308" onclick="a0193tagInfo(this,'1308','����ί��ְ')" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1309" id="tag1309" onclick="a0193tagInfo(this,'1309','����ί��ְ')" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1310" id="tag1310" onclick="a0193tagInfo(this,'1310','����ί��ְ')" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1311" id="tag1311" onclick="a0193tagInfo(this,'1311','����ί��ְ')" >
					<label>����ί��ְ</label>
				</td>	
			</tr>
		</table>	
		<!-- ����������һίԱ��� -->
		<table id="tag14" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1401" id="tag1401" onclick="a0193tagInfo(this,'1401','����ȫ�����������')" >
					<label>����ȫ�����������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1402" id="tag1402" onclick="a0193tagInfo(this,'1402','ȫ���˴����')" >
					<label>ȫ���˴����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1403" id="tag1403" onclick="a0193tagInfo(this,'1403','ȫ���˴�ר��ίԱ��ίԱ')" >
					<label>ȫ���˴�ר��ίԱ��ίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1404" id="tag1404" onclick="a0193tagInfo(this,'1404','ȫ����ЭίԱ')" >
					<label>ȫ����ЭίԱ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1405" id="tag1405" onclick="a0193tagInfo(this,'1405','ȫ����Э��ר��ίԱ�ḱ����')"  >
					<label>ȫ����Э��ר��ίԱ�ḱ����</label>
				</td>	
			</tr>
		</table>	
		<!-- ������������  -->
		<div id="tag15" style="display: none;">
			<table>
				<tr>
					<td>
						<input type="checkbox" name="tag1501" id="tag1501" onclick="a0193tagInfo(this,'1501','����ί���������Ʋ��Ź���')" >
						<label>����ί���������Ʋ��Ź���&nbsp;&nbsp;</label>

					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1502" id="tag1502" onclick="a0193tagInfo(this,'1502','���Ժ����')" >
						<label>���Ժ����&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1503" id="tag1503" onclick="a0193tagInfo(this,'1503','��Ժ����')" >
						<label>��Ժ����&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1504" id="tag1504" onclick="a0193tagInfo(this,'1504','�������ع���')" >
						<label>�������ع���&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1505" id="tag1505" onclick="a0193tagInfo(this,'1505','˾������ϵͳ����')" >
						<label>˾������ϵͳ����&nbsp;&nbsp;</label>

					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1506" id="tag1506" onclick="a0193tagInfo(this,'1506','���Ұ�ȫ���ع���')" >
						<label>���Ұ�ȫ���ع���&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1507" id="tag1507" onclick="a0193tagInfo(this,'1507','���һ�ط���������')" >
						<label>���һ�ط���������&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1508" id="tag1508" onclick="a0193tagInfo(this,'1508','��ʦ')" >
						<label>��ʦ&nbsp;&nbsp;</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1509" id="tag1509" onclick="a0193tagInfo(this,'1509','���ɽ�ѧ���о�����')" >
						<label>���ɽ�ѧ���о�����&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1510" id="tag1510" onclick="a0193tagInfo(this,'1510','������λ��Ҫ�쵼��ְ����')" >
						<label>������λ��Ҫ�쵼��ְ����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1511" id="tag1511" onclick="a0193tagInfo(this,'1511','���ι�����ί���')" >
						<label>���ι�����ί���&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1512" id="tag1512" onclick="a0193tagInfo(this,'1512','���ι�����(����)')" >
						<label>���ι����조������&nbsp;&nbsp;</label>
						
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1513" id="tag1513" onclick="a0193tagInfo(this,'1513','���ι������ֳ�')" >
						<label>���ι������ֳ�&nbsp;&nbsp;</label>
						
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1514" id="tag1514" onclick="a0193tagInfo(this,'1514','���ι�˾���ֳ�')" >
						<label>���ι�˾���ֳ�&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1515" id="tag1515" onclick="a0193tagInfo(this,'1515','������λ���ӳ�Ա��ְ����')" >
						<label>������λ���ӳ�Ա��ְ����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1516" id="tag1516" onclick="a0193tagInfo(this,'1516','������λ����ְ')" >
						<label>������λ����ְ&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1517" id="tag1517" onclick="a0193tagInfo(this,'1517','������λ���ӳ�Ա')" >
						<label>������λ���ӳ�Ա&nbsp;&nbsp;</label>
						
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1518" id="tag1518" onclick="a0193tagInfo(this,'1518','��������λ����ְ�쵼��λ��ְ���')" >
						<label>��������λ����ְ�쵼��λ��ְ���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1519" id="tag1519" onclick="a0193tagInfo(this,'1519','���쵼��λ��ְʱ��')" >
						<label>���쵼��λ��ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1520" id="tag1520" onclick="a0193tagInfo(this,'1520','�ְ�����ְʱ��')" >
						<label>�ְ�����ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1521" id="tag1521" onclick="a0193tagInfo(this,'1521','���쵼ְ������ְʱ��')" >
						<label>���쵼ְ������ְʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1522" id="tag1522" onclick="a0193tagInfo(this,'1522','���·��ɹ���ʱ��')"  >
						<label>���·��ɹ���ʱ��</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1523" id="tag1523" onclick="a0193tagInfo(this,'1523','���·��ɹ������꼰����')"  >
						<label>���·��ɹ������꼰����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1524" id="tag1524" onclick="a0193tagInfo(this,'1524','���·��ɹ�������������')" >
						<label>���·��ɹ�������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1525" id="tag1525" onclick="a0193tagInfo(this,'1525','���·��ɹ���һ��������')"  >
						<label>���·��ɹ���һ��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1526" id="tag1526" onclick="a0193tagInfo(this,'1526','����ϵͳ�쵼�ɲ�������ְ���')"  >
						<label>����ϵͳ�쵼�ɲ�������ְ���</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1527" id="tag1527" onclick="a0193tagInfo(this,'1527','������ϵͳ�ڽ�����ְ')"  >
						<label>������ϵͳ�ڽ�����ְ</label> >
						<label>ͬһ��λ���¼�֮�佻����ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1528" id="tag1528" onclick="a0193tagInfo(this,'1528','������ϵͳ�ڽ�����ְ')"  >
						<label>������ϵͳ�ڽ�����ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1529" id="tag1529" onclick="a0193tagInfo(this,'1529','������ϵͳ�⽻����ְ')" >
						<label>������ϵͳ�⽻����ְ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1530" id="tag1530" onclick="a0193tagInfo(this,'1530','ͨ������˾�����ԣ���ʦ�ʸ��ԣ�')" >
						<label>ͨ������˾�����ԣ���ʦ�ʸ��ԣ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1534" id="tag1534" onclick="a0193tagInfo(this,'1534','�����󷨹١�����')" >
						<label>�����󷨹١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1535" id="tag1535" onclick="a0193tagInfo(this,'1535','һ���߼����١�����')" >
						<label>һ���߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1536" id="tag1536" onclick="a0193tagInfo(this,'1536','�����߼����١�����')" >
						<label>�����߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1537" id="tag1537" onclick="a0193tagInfo(this,'1537','�����߼����١�����')" >
						<label>�����߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1538" id="tag1538" onclick="a0193tagInfo(this,'1538','�ļ��߼����١�����')" >
						<label>�ļ��߼����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1539" id="tag1539" onclick="a0193tagInfo(this,'1539','һ�����١�����')" >
						<label>һ�����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1540" id="tag1540" onclick="a0193tagInfo(this,'1540','�������١�����')" >
						<label>�������١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1541" id="tag1541" onclick="a0193tagInfo(this,'1541','�������١�����')"  >
						<label>�������١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1542" id="tag1542" onclick="a0193tagInfo(this,'1542','�ļ����١�����')" >
						<label>�ļ����١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1543" id="tag1543" onclick="a0193tagInfo(this,'1543','�弶���١�����')" >
						<label>�弶���١�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1545" id="tag1545" onclick="a0193tagInfo(this,'1545','һ������רԱ')" >
						<label>һ������רԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1546" id="tag1546" onclick="a0193tagInfo(this,'1546','��������רԱ')" >
						<label>��������רԱ</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1547" id="tag1547" onclick="a0193tagInfo(this,'1547','һ���߼�����')" >
						<label>һ���߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1548" id="tag1548" onclick="a0193tagInfo(this,'1548','�����߼�����')" >
						<label>�����߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1549" id="tag1549" onclick="a0193tagInfo(this,'1549','�����߼�����')" >
						<label>�����߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1550" id="tag1550" onclick="a0193tagInfo(this,'1550','�ļ��߼�����')" >
						<label>�ļ��߼�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1552" id="tag1552" onclick="a0193tagInfo(this,'1552','һ������')" >
						<label>һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1553" id="tag1553" onclick="a0193tagInfo(this,'1553','��������')" >
						<label>��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1554" id="tag1554" onclick="a0193tagInfo(this,'1554','��������')" >
						<label>��������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1555" id="tag1555" onclick="a0193tagInfo(this,'1555','�ļ�����')" >
						<label>�ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1557" id="tag1557" onclick="a0193tagInfo(this,'1557','һ����Ա')" >
						<label>һ����Ա</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1558" id="tag1558" onclick="a0193tagInfo(this,'1558','������Ա')"  >
						<label>������Ա</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1560" id="tag1560" onclick="a0193tagInfo(this,'1560','������һ���ܼ�')" >
						<label>������һ���ܼ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1561" id="tag1561" onclick="a0193tagInfo(this,'1561','�����������ܼ�')" >
						<label>�����������ܼ�</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1562" id="tag1562" onclick="a0193tagInfo(this,'1562','������һ������')" >
						<label>������һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1563" id="tag1563" onclick="a0193tagInfo(this,'1563','��������������')" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1564" id="tag1564" onclick="a0193tagInfo(this,'1564','��������������')" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1565" id="tag1565" onclick="a0193tagInfo(this,'1565','�������ļ�����')" >
						<label>�������ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1566" id="tag1566" onclick="a0193tagInfo(this,'1566','������һ������')" >
						<label>������һ������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1567" id="tag1567" onclick="a0193tagInfo(this,'1567','��������������')" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1568" id="tag1568" onclick="a0193tagInfo(this,'1568','��������������')" >
						<label>��������������</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1569" id="tag1569" onclick="a0193tagInfo(this,'1569','�������ļ�����')" >
						<label>�������ļ�����</label>
					</td>	
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="tag1570" id="tag1570" onclick="a0193tagInfo(this,'1570','������Ա')" >
						<label>������Ա</label>
					</td>	
				</tr>
			</table>	
		</div>
		<!-- ���龭�� -->
		<table id="tag16" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1601" id="tag1601"  onclick="a0193tagInfo(this,'1601','��ְ���͹����쵼������')" >
					<label>��ְ���͹����쵼������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1602" id="tag1602" onclick="a0193tagInfo(this,'1602','��ְ�������쵼����')" >
					<label>��ְ�������쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1603" id="tag1603" onclick="a0193tagInfo(this,'1603','��ְ�������쵼����')" >
					<label>��ְ�������쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1604" id="tag1604" onclick="a0193tagInfo(this,'1604','����ְ���͹����쵼������')"  >
					<label>����ְ���͹����쵼������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1605" id="tag1605" onclick="a0193tagInfo(this,'1605','����ְ�������쵼����')" >
					<label>����ְ�������쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1606" id="tag1606" onclick="a0193tagInfo(this,'1606','����ְ�������쵼����')" >
					<label>����ְ�������쵼����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1607" id="tag1607" onclick="a0193tagInfo(this,'1607','�йܽ�����ҵ���й���ҵ��ְ��Ҫ����������')" >
					<label>�йܽ�����ҵ���й���ҵ��ְ��Ҫ����������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1608" id="tag1608" onclick="a0193tagInfo(this,'1608','�йܽ�����ҵ���й���ҵ����ְ��Ҫ����������')" >
					<label>�йܽ�����ҵ���й���ҵ����ְ��Ҫ����������</label>
				</td>	
			</tr>
		</table>	
		<!-- ������������������ó���Ⱦ���  -->
		<table id="tag17" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1701" id="tag1701" onclick="a0193tagInfo(this,'1701','���Ҽ�������������������ó��')" >
					<label>���Ҽ�������������������ó��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1702" id="tag1702" onclick="a0193tagInfo(this,'1702','ʡ��������������������ó��')" >
					<label>ʡ��������������������ó��</label>
				</td>	
			</tr>
		</table>
		<!-- ���⹤������  -->
		<table id="tag20" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2001" id="tag2001" onclick="a0193tagInfo(this,'2002','��ʹ')"  >
					<label>��ʹ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2002" id="tag2002" onclick="a0193tagInfo(this,'2002','��ʹ')" >
					<label>��ʹ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2003" id="tag2003" onclick="a0193tagInfo(this,2003,��ʹ�β���)" >
					<label>��ʹ�β���</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2004" id="tag2004" onclick="a0193tagInfo(this,'2004','������')" >
					<label>������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2005" id="tag2005" onclick="a0193tagInfo(this,'2005','��������')" >
					<label>��������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2006" id="tag2006" onclick="a0193tagInfo(this,'2006','���⹤��')" >
					<label>���⹤��</label>
				</td>	
			</tr>
		</table>
		<!-- ʡ����ҵ�쵼���ӹ������� -->
		<table id="tag21" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2101" id="tag2101" onclick="a0193tagInfo(this,'2101','��ί�Ṥ��')"  >
					<label>��ί�Ṥ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2102" id="tag2102" onclick="a0193tagInfo(this,'2102','���»Ṥ���������ⲿ���¡�ְ�����£�')"  >
					<label>���»Ṥ���������ⲿ���¡�ְ�����£�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2103" id="tag2103" onclick="a0193tagInfo(this,'2103','���»Ṥ�������裩')" >
					<label>����㹤��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2104" id="tag2104" onclick="a0193tagInfo(this,'2104','���»Ṥ�������裩')" >
					<label>���»Ṥ�������裩</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2105" id="tag2105" onclick="a0193tagInfo(this,'2105','������ϯ����')" >
					<label>������ϯ����</label>
				</td>	
			</tr>
		</table>	
		<!-- �¼���ҵ��ְ��ְ����-->
		<table id="tag22" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2201" id="tag2201" onclick="a0193tagInfo(this,'2201','���ι�������ҵ��ְ')"  >
					<label>���ι�������ҵ��ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2202" id="tag2202" onclick="a0193tagInfo(this,'2202','���ι�������ҵ��ְ')" >
					<label>���ι�������ҵ��ְ</label>
				</td>	
			</tr>
		</table>
		<!-- �ܲ�ְ�ܲ�����ְ���� -->
		<table id="tag23" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2301" id="tag2301" onclick="a0193tagInfo(this,'2301','���ι�������ְ')" >
					<label>���ι�������ְ</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2302" id="tag2302" onclick="a0193tagInfo(this,'2302','���ι����Ÿ�ְ')" >
					<label>���ι����Ÿ�ְ</label>
				</td>	
			</tr>
		</table>
		<!-- �Ϲ����ɹ������� -->
		<table id="tag24" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag2401" id="tag2401" onclick="a0193tagInfo(this,'2401','��ְ����ֱ�����һ��ظ�������ְ��')"  >
					<label>��ְ����ֱ�����һ��ظ�������ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2402" id="tag2402" onclick="a0193tagInfo(this,'2402','��ְ��ʡֱ��λ��������ְ��')" >
					<label>��ְ��ʡֱ��λ��������ְ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag2403" id="tag2403" onclick="a0193tagInfo(this,'2403','��ְ�����ظ�������ְ��')" >
					<label>��ְ�����ظ�������ְ��</label>
				</td>	
			</tr>
		</table>	 
		<!-- �������� -->
		<table id="tag18" style="display: none;">
			<tr>
				<td>
					<input type="checkbox" name="tag1801" id="tag1801" onclick="a0193tagInfo(this,'1801','���鲿˫������ְ�ɲ�')" >
					<label>���鲿˫������ְ�ɲ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1802" id="tag1802" onclick="a0193tagInfo(this,'1802','���鲿˫������ְ�ɲ�')" >
					<label>���鲿˫������ְ�ɲ�</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1803" id="tag1803" onclick="a0193tagInfo(this,'1803','���꼰���ϻ��㹤������')" >
					<label>���꼰���ϻ��㹤������</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1805" id="tag1805" onclick="a0193tagInfo(this,'1804','2014�������Ƹ����')"  >
					<label>2014�������Ƹ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1806" id="tag1806" onclick="a0193tagInfo(this,'1805','����ѡ��')" >
					<label>����ѡ��</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1807" id="tag1807" onclick="a0193tagInfo(this,'1806','ѡ����')" >
					<label>ѡ����</label>
				</td>	
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="tag1809" id="tag1809"  onclick="a0193tagInfo(this,'1809','����')">
					<label>����</label>
				</td>	
			</tr>
		</table>
		
				
	</div>
</div>
		<div id="tag_info_div1">
			<odin:hidden property="a0193s" title="����" />
			<textarea rows="3" cols="114" id="a0193z" name="a0193z" ></textarea>
		</div>	
			
			
   
   
   
   </odin:tabCont>
</div>
<div id="tab51" style="display: none">
   <odin:tabCont itemIndex="tab5" className="tab">
   		<odin:hidden property="a0194s"/>
		<div id="tag_container1">
		<div class="tag_div">
			<odin:checkbox property="tag011"  label="����" onclick="fullContent(this,'01','����')"></odin:checkbox>
			<odin:checkbox property="tag021"  label="�ͼ���" onclick="fullContent(this,'02','�ͼ���')"></odin:checkbox>
			<odin:checkbox property="tag031"  label="��֯����" onclick="fullContent(this,'03','��֯����')"></odin:checkbox>
			<odin:checkbox property="tag041"  label="����˼����ʶ��̬" onclick="fullContent(this,'04','����˼����ʶ��̬')"></odin:checkbox>
			<odin:checkbox property="tag051"  label="ͳս" onclick="fullContent(this,'05','ͳս')"></odin:checkbox>
			<odin:checkbox property="tag061"  label="����" onclick="fullContent(this,'06','����')"></odin:checkbox>
			<odin:checkbox property="tag071"  label="Ⱥ��" onclick="fullContent(this,'07','Ⱥ��')"></odin:checkbox>
			<odin:checkbox property="tag081"  label="�����о�" onclick="fullContent(this,'08','�����о�')"></odin:checkbox>
			<odin:checkbox property="tag091"  label="��۾���" onclick="fullContent(this,'09','��۾���')"></odin:checkbox>
			<odin:checkbox property="tag101"  label="��ҵ����" onclick="fullContent(this,'10','��ҵ����')"></odin:checkbox>
			<odin:checkbox property="tag111"  label="��Ȼ��Դ����" onclick="fullContent(this,'11','��Ȼ��Դ����')"></odin:checkbox>
			<odin:checkbox property="tag121"  label="��̬��������" onclick="fullContent(this,'12','��̬��������')"></odin:checkbox>
			<odin:checkbox property="tag131"  label="�Ļ�����" onclick="fullContent(this,'13','�Ļ�����')"></odin:checkbox>
			<odin:checkbox property="tag141"  label="�ǽ��滮" onclick="fullContent(this,'14','�ǽ��滮')"></odin:checkbox>
			<odin:checkbox property="tag151"  label="��ͨ����" onclick="fullContent(this,'15','��ͨ����')"></odin:checkbox>
		</div>
		<div class="tag_div">
			<odin:checkbox property="tag161"  label="����˰�����" onclick="fullContent(this,'16','����˰�����')"></odin:checkbox>
			<odin:checkbox property="tag171"  label="����" onclick="fullContent(this,'17','����')"></odin:checkbox>
			<odin:checkbox property="tag181"  label="��ó��ͨ" onclick="fullContent(this,'18','��ó��ͨ')"></odin:checkbox>
			<odin:checkbox property="tag191"  label="�Ƽ�" onclick="fullContent(this,'19','�Ƽ�')"></odin:checkbox>
			<odin:checkbox property="tag201"  label="���簲ȫ����Ϣ��" onclick="fullContent(this,'20','���簲ȫ����Ϣ��')"></odin:checkbox>
			<odin:checkbox property="tag211"  label="����" onclick="fullContent(this,'21','����')"></odin:checkbox>
			<odin:checkbox property="tag221"  label="����" onclick="fullContent(this,'22','����')"></odin:checkbox>
			<odin:checkbox property="tag231"  label="����" onclick="fullContent(this,'23','����')"></odin:checkbox>
			<odin:checkbox property="tag241"  label="�ۺ�ִ�����г����" onclick="fullContent(this,'24','�ۺ�ִ�����г����')"></odin:checkbox>
			<odin:checkbox property="tag251"  label="ũҵũ��" onclick="fullContent(this,'25','ũҵũ��')"></odin:checkbox>
			<odin:checkbox property="tag261"  label="ˮ��" onclick="fullContent(this,'26','ˮ��')"></odin:checkbox>
			<odin:checkbox property="tag271"  label="�����ڽ�" onclick="fullContent(this,'27','�����ڽ�')"></odin:checkbox>
			<odin:checkbox property="tag281"  label="������" onclick="fullContent(this,'28','������')"></odin:checkbox>
			<odin:checkbox property="tag291"  label="��������" onclick="fullContent(this,'29','��������')"></odin:checkbox>
			<odin:checkbox property="tag301"  label="��ȫ������Ӧ������" onclick="fullContent(this,'30','��ȫ������Ӧ������')"></odin:checkbox>
		</div>
		<div class="tag_div">
			<odin:checkbox property="tag311"  label="���¸۰�̨��" onclick="fullContent(this,'31','���¸۰�̨��')"></odin:checkbox>
			<odin:checkbox property="tag321"  label="��������" onclick="fullContent(this,'32','��������')"></odin:checkbox>
			<odin:checkbox property="tag331"  label="��ҵ��Ӫ����" onclick="fullContent(this,'33','��ҵ��Ӫ����')"></odin:checkbox>
			<odin:checkbox property="tag341"  label="��ҵ��������" onclick="fullContent(this,'34','��ҵ��������')"></odin:checkbox>
			<odin:checkbox property="tag351"  label="��ҵ�г�Ӫ��" onclick="fullContent(this,'35','��ҵ�г�Ӫ��')"></odin:checkbox>
			<odin:checkbox property="tag361"  label="��ҵ�������" onclick="fullContent(this,'36','��ҵ�������')"></odin:checkbox>
			<odin:checkbox property="tag371"  label="��ҵרҵ����" onclick="fullContent(this,'37','��ҵרҵ����')"></odin:checkbox>
			<odin:checkbox property="tag381"  label="��ҵ����ҵ��" onclick="fullContent(this,'38','��ҵ����ҵ��')"></odin:checkbox>
			<odin:checkbox property="tag391"  label="ѧ�ƽ���" onclick="fullContent(this,'39','ѧ�ƽ���')"></odin:checkbox>
			<odin:checkbox property="tag401"  label="ѧ������" onclick="fullContent(this,'40','ѧ������')"></odin:checkbox>
			<odin:checkbox property="tag411"  label="��������" onclick="fullContent(this,'41','��������')"></odin:checkbox>
			<odin:checkbox property="tag421"  label="���⽻��" onclick="fullContent(this,'42','���⽻��')"></odin:checkbox>
			<odin:checkbox property="tag431"  label="˼������" onclick="fullContent(this,'43','˼������')"></odin:checkbox>
			<odin:checkbox property="tag441"  label="���ڹ���" onclick="fullContent(this,'44','���ڹ���')"></odin:checkbox>
			<odin:checkbox property="tag991"  label="����" onclick="fullContent(this,'99','����')"></odin:checkbox>
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
							<td><odin:button text="�������" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td><odin:button text="��������" property="saveCon1" handler="saveConF"></odin:button>
							</td>
							<td align="center"><odin:button text="��ʼ��ѯ" property="mQuery" handler="dosearch"/></td>
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
		Ext.MessageBox.prompt("ϵͳ��ʾ","��ѯ���ƣ�",function(btn,value){
			if(btn=='ok'){
				if(value.trim()==''){
					Ext.MessageBox.alert("ϵͳ��ʾ","���������ƣ�");
				}
				radow.doEvent("saveCon.onclick",value);
			}
		},this,false,"");
	}
	
}

function delCond(id){
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(btn) { 
		if("yes"==btn){
			radow.doEvent('deletecond',id);
		}else{
			return;
		}		
	});
}

function opRenderer(value,params,record,rowidx,colidx,ds){
	return "<a href=\"javascript:void()\" onclick=\"delCond('"+value+"')\">ɾ��</a>";
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
	
	//��ȡ ��ҵԺУ   ������λ��ְ��  ������  ��ѯ����  ��¼���ϸ�ҳ����
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

//��ǩ��
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
 * ******************************************�˴��������з��������޸����˵������ͣ��ʽ������ʹ�á�*******************************************************
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

//�˵����������������뿪�¼�
function leftMenuHover(){
	var nodes = getElementsByClassName("leftMenu", "div");
	for(i = 0,len=nodes.length; i < len; i++) {
		bindOnmouseEvent(nodes[i]);
	}
}

/*
 * ��дgetElementsByClassName()������IE8������û�и÷���
 */
function getElementsByClassName(className, tagName) {
    if (document.getElementsByClassName) {
        // ʹ�����з���
        return document.getElementsByClassName(className);
    } else {
        // ѭ���������б�ǩ�����ش�����Ӧ������Ԫ��
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
    var arr=tagStr.className.split(/\s+/ );  //���������ʽ����Ϊclass�����ж��,�ж��Ƿ����  
    for (var i=0;i<arr.length;i++){  
           if (arr[i]==className){  
                 return true ;  
           }  
    }  
    return false ;  
}

function bindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#FFFFFF"; };//����뿪�¼�
	node.onmousedown=function(){node.style.backgroundColor = "#1E90FF";};//�����ʱ�����¼�
}

function unbindOnmouseEvent(node){
	node.onmouseover=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ͣ�¼�
	node.onmouseout=function(){ node.style.backgroundColor = "#1E90FF"; };//����뿪�¼�
	node.onmousedown=function(){ node.style.backgroundColor = "#1E90FF"; };//�����ʱ�����¼�
}

//���������ʾѡ�б�ǩ
function fullContent(check,value,valuename){
	var a0194z = document.getElementById("a0194z").value;
	var a0194s = document.getElementById("a0194s").value;
	
	if(check.checked) {
		if( a0194z == null || a0194z == '' ){
			a0194z = valuename;
		}else{
			a0194z = a0194z + "��" + valuename;
		}	
		if( a0194s == null || a0194s == '' ){
			a0194s = value;
		}else{
			a0194s = a0194s  + "��" + value;
		}			
	}else{
		a0194z = a0194z.replace('��'+valuename, '').replace(valuename+'��', '').replace(valuename, '');
		a0194s = a0194s.replace('��'+value, '').replace(value+'��', '').replace(value, '');
	}
	document.getElementById("a0194z").value = a0194z;
	document.getElementById("a0194s").value = a0194s;
	
}


//tabҳ�л��¼�
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


//a0193tag��ǩѡ��
function a0193tagInfo(check,value,name){
	
	var a0193s=document.getElementById("a0193s").value;
	var a0193z=document.getElementById("a0193z").value;
	if(check.checked) {
			if( a0193z == null || a0193z == '' ){
				a0193z = name;
			}else{
				a0193z = a0193z + "��" + name;
			}	
			if( a0193s == null || a0193s == '' ){
				a0193s = value;
			}else{
				a0193s = a0193s  + "��" + value;
			}			
		}else{
			
			a0193z = a0193z.replace("��"+name, '').replace(name+"��",'').replace(name, '');
			a0193s = a0193s.replace("��"+value, '').replace(value+"��",'').replace(value, '');
		}
		document.getElementById("a0193z").value = a0193z;
		document.getElementById("a0193s").value = a0193s;
		
		}

	function showCheckbox(){
			var a0193s=document.getElementById("a0193s").value;

			var a0194s=document.getElementById("a0194s").value;
		
			if(a0193s!=null&&a0193s!=''){
					var arr=a0193s.split("��");
					for(var i=0 ; i<arr.length ; i++){
							
						document.getElementById("tag"+arr[i]).checked=true;


					}
				}
			if(a0194s!=null&&a0194s!=''){
				var brr=a0194s.split("��");
				for(var j=0 ; j<brr.length ; j++){
				
						Ext.getCmp("tag"+brr[j]+"1").setValue(true);
						
					}


			}

		}
	

resizeframe();




</script>


<%@include file="/pages/customquery/otjs.jsp" %>

