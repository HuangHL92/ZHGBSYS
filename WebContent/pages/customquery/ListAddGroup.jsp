<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<html style="background-color: rgb(223,232,246);">
<meta http-equiv="X-UA-Compatible"content="IE=8">
<style>

	.x-panel-bwrap {
		height: 100%
	}
	.x-panel-body {
		height: 100%
	}

</style>
<div id="conditionArea" style="height: 660; width: 1200; overflow-y: scroll;">
	<odin:groupBoxNew title="基本" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0101" label="人员姓名" maxlength="36" />
					<odin:textEdit property="a0184" label="身份证号" maxlength="18" />
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
					<odin:NewDateEditTag property="jiezsj" maxlength="6"  label="年龄年限计算截止"></odin:NewDateEditTag>
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
					
					<odin:select2 property="a0163" label="人员状态" codeType="ZB126"></odin:select2>
				</tr>
				<tr>
					 <td noWrap="nowrap" align=right><span id="zhichenSpanId" style="FONT-SIZE: 12px">职&nbsp;&nbsp;&nbsp;称</span>&nbsp;</td>
					<td colspan="1">
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
					<td noWrap="nowrap" align=right><span id="a0144SpanId" style="FONT-SIZE: 12px">参加中共时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0144A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0144B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<odin:select2 property="a0141" label="政治面貌" codeType="GB4762"></odin:select2>
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
									<label><input name="a0117" type="checkbox" value="01" />汉族 </label> 
									<label><input name="a0117" type="checkbox" value="02" />少数民族</label>
									<odin:hidden property="a0117v"/> 
								</td>
							</tr>
						</table>
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
					<odin:textEdit property="a1701" label="简历" maxlength="100" >(包含)</odin:textEdit>
				</tr>	
				<tr>
					<td noWrap="nowrap" align=right><span id="a0165SpanId" style="FONT-SIZE: 12px">人员管理类别</span>&nbsp;</td>
					<td colspan="3">
						<table>
							<tr>
								<td style="FONT-SIZE: 12px">
									<label><input name="a0165" type="checkbox" value="01" />中央管理干部</label> 
									<label><input name="a0165" type="checkbox" value="02" />省级党委管理干部 </label> 
									<label><input name="a0165" type="checkbox" value="03" />市级党委管理干部 </label> 
									<label><input name="a0165" type="checkbox" value="04" />县级党委管理干部</label> 
									<label><input name="a0165" type="checkbox" value="09" />其他</label> 
									<odin:hidden property="a0165v"/>
								</td>
							</tr>
						</table>
					</td>
					<td noWrap="nowrap" align=right><span id="xgsjSpanId" style="FONT-SIZE: 12px">最后维护时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="xgsjA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="xgsjB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit3  property="a0195" label="统计关系所在单位" readonly="true" codetype="orgTreeJsonData"  ></tags:PublicTextIconEdit3>
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>
	<odin:groupBoxNew title="职务" property="ggBox2" contentEl="zwDiv" collapsible="false">
		<div id="zwDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a0216a" label="职务名称" maxlength="100" >(包含)</odin:textEdit>
					<odin:select2 property="a0201d" label="是否领导成员" codeType="XZ09"></odin:select2>
					<odin:select2 property="a0219" label="是否领导职务" codeType="ZB42"></odin:select2>
				</tr>
				
				<tr>
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
					
					<td style="FONT-SIZE: 12px">
						<label><input name="qtxzry" id="qtxzry" type="checkbox" value="1" />其他现职人员（无职务）</label> 
					</td>
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>
	<odin:groupBoxNew title="学历学位" property="ggBox3" contentEl="xlDiv"
		collapsible="false">
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
	
	<odin:groupBoxNew title="奖惩" property="ggBox4" contentEl="jcDiv"
		collapsible="false">
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
	
	<odin:groupBoxNew title="年度考核" property="ggBox5" contentEl="ndkhDiv"
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
			
	<odin:groupBoxNew title="家庭成员" property="ggBox6" contentEl="jtcyDiv"
		collapsible="false">
		<div id="jtcyDiv">
			<table style="width: 100%">
				<tr>
					<odin:textEdit property="a3601" label="姓名" maxlength="100" ></odin:textEdit>
					<odin:textEdit property="a3684" label="身份证号"  maxlength="18"  />
					<odin:textEdit property="a3611" label="工作单位及职务"  />
				</tr>
			</table>
		</div>
	</odin:groupBoxNew>		
				
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
							<td><odin:button text="清除条件" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td style="width: 30px"></td>
							<td align="center"><odin:button text="开始查询" property="mQuery" handler="dosearch"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="radioC"/>
<odin:hidden property="sql"/>
<odin:hidden property="tableType"/>
</html>
<script type="text/javascript">

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
	radow.doEvent('mQueryonclick');
}


Ext.onReady(function(){
	var tableType = realParent.document.getElementById("tableType").value;
	document.getElementById("tableType").value = tableType;
	var sql = realParent.document.getElementById("sql").value;
	document.getElementById("sql").value = sql;
	odin.setSelectValue("a0163", realParent.document.getElementById("personq").value);
});
function clearConbtn(){
	document.getElementById("resetBtn").click();
	radow.doEvent('initX');
}
function clearCon(){
	var radioC = realParent.document.getElementsByName("radioC");  
    for (i=0; i<radioC.length; i++) {  
        if (radioC[i].checked) {  
        	radioC = radioC[i].value;
        	break;
        }  
    } 
    document.getElementById("radioC").value=radioC;
    document.getElementById("sql").value=realParent.document.getElementById("sql").value;
}

function collapseGroupWin(){
	window.close();
}
</script>
