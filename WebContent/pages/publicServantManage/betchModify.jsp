<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@include file="/comOpenWinInit.jsp" %>
<%@page
	import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/picCut/js/jquery-1.4.2.js"></script>

<style>
.x-form-item {
	width: 100%;
	height: 100%;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">

	function link2tab(id) {
		window.realParent.tabs.activate(id);
	}
	/**删除选中行  单条**/
	function delRow(obj) {
		var grid = odin.ext.getCmp(obj.initialConfig.cls);
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			store.remove(selected);
		}
		grid.view.refresh();
	}
	/**添加行**/
	function addRow(obj) {
		radow.addGridEmptyRow(obj.initialConfig.cls, 0);
	}
	/**删除选中行  多条**/
	function delCheckedRow(obj) {
		var grid = odin.ext.getCmp(obj.initialConfig.cls);
		var arrayObj = new Array();
		;
		var store = grid.store;
		var i = store.getCount() - 1;
		if (store.getCount() > 0) {
			for (var i = store.getCount() - 1; i >= 0; i--) {
				var ck = grid.getStore().getAt(i).get("logchecked");
				if (ck == true) {
					store.remove(grid.getStore().getAt(i));
				}
			}
		}
	}

	function a1151Change() {
		
		//var a1151Type = document.getElementById('a1151Type').value;

		/* if ("0" == a1151Type) {
			selecteWinEnable('a1517');
		} else {
			selecteWinDisable('a1517');
		} */
		
		radow.doEvent('change');
	}

	function setParentValue2(obj) {
		var id = obj.id;
		var value = obj.value;
		if (id.indexOf('_combo') != -1) {
			id = id.split('_combo')[0];
			if (value == '') {
				document.getElementById(id).value = '';
				onA0160Change();
			}
			value = document.getElementById(id).value
		}
		realParent.document.getElementById(id).value = value;
	}
	//籍贯
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0111').value = record.data.key;
	}
	//出生地
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0114').value = record.data.key;
	}

	function setA1404aValue(record, index) {//奖惩名称
		Ext.getCmp('a1404a').setValue(record.data.value);
	}
	
	function setA0216aValue(record,index){//职务简称
		Ext.getCmp('a0216a').setValue(record.data.value);
	}
	//考核年度
	function yearChange() {
		var now = new Date();
		var year = now.getFullYear();
		var yearList = document.getElementById("a1521");
		for (var i = 0; i <= 50; i++) {
			year = year - 1;
			yearList.options[i] = new Option(year, year);
		}
	}

	function count() {
		radow.doEvent('count');
	}

	function count1() {
		radow.doEvent('count1');
	}
</script>
<div style="float:left;width:280px;height:290px" >
				<odin:editgrid property="peopleInfoGrid" title="人员信息列表" bbarId="pageToolBar" pageSize="500"
					autoFill="false" width="100" height="290"  hasRightMenu="true" rightMenuId="updateM" >
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="a0101" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid"  
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" />
						<odin:gridEditColumn2 dataIndex="a0101" width="80" header="姓名"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="110" header="工作单位及职务"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0000" width="110" header="人员id"
							hideable="false" editor="text" align="center" isLast="true"
							hidden="true" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
</div>
<div style="float:right;width:670px">
<odin:tab id="tab">
	<odin:tabModel>
		<odin:tabItem title="人员基本信息" id="tab1"></odin:tabItem>
		<odin:tabItem title="考核" id="tab2"></odin:tabItem>
		<%-- <odin:tabItem title="职务" id="tab4"></odin:tabItem> --%>
		<odin:tabItem title="奖惩" id="tab5" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<%-----------------------------人员基本信息-------------------<odin:hidden property="a0000"/>------------------------------------%>
	<odin:tabCont itemIndex="tab1">
		<div id="floatToolDiv1"></div>
		<odin:toolBar property="floatToolBar1" applyTo="floatToolDiv1">
			<odin:fill />
			<odin:buttonForToolBar text="执行修改" id="save" isLast="true" handler="saveConfirm"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz" style="font-size: 12; color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：空项将不被修改</label>
					
				</tr>
				
				<tr>
					<td></td>
					<td><odin:select2 property="fkbs" label="是否年轻干部" data="['1','是'],['0','否']"/></td>
				</tr>
				<tr>
					
						<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="统计关系所在单位" readonly="true"
						codetype="orgTreeJsonData"></tags:PublicTextIconEdit3>
					
					<!-- <td></td> -->
					<!-- <td>
						<input type="checkbox" name="a99z101" id="a99z101"/>
						<label id="a99z103SpanId" for="a99z101" style="font-size: 12px;">是否考录</label>
					</td> -->
					
					<odin:select2 property="a99z101" label="是否考录" data="['1','是'],['0','否']" onchange="a99z101Change"/>
				</tr>
				<tr>
					<odin:select2 property="a0160" label="人员类别" codeType="ZB125"
						onchange="onA0160Change" onblur="setParentValue2(this)"></odin:select2>
					<odin:NewDateEditTag property="a99z102" isCheck="true" maxlength="8" width="163" label="录用时间"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:select2 property="a0165" label="管理类别" codeType="ZB130"></odin:select2>
					<odin:select2 property="a99z103" label="是否选调生" data="['1','是'],['0','否']" onchange="a99z103Change"/>					
				</tr>
				
				<tr>
					<odin:select2 property="a0121" label="编制类型" codeType="ZB135"
						onblur="setParentValue(this)"></odin:select2>
					<odin:NewDateEditTag property="a99z104" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入选调生时间"></odin:NewDateEditTag>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="A0221" codetype="ZB09"  label="职务层次"  />
				
					<tags:PublicTextIconEdit property="A0192E" codetype="ZB148"  label="职级"  />
				</tr>
				
				<tr>
					<odin:select2 property="a0197" label="具有两年以上基层工作经历" data="['1','是'],['0','否']" />
					<odin:NewDateEditTag property="A0288" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任现职务层次时间"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:NewDateEditTag property="A0107" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生年月"></odin:NewDateEditTag>
					<odin:textEdit property="A0111A" label="籍贯"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="A0114A" label="出生地"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0115A" label="成长地"  width="163" ></odin:textEdit>
				</tr>								
				<tr>
					<odin:textEdit property="A0117" label="民族"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0128" label="健康情况"  width="163" ></odin:textEdit>
				</tr>	
				<tr>
					<odin:NewDateEditTag property="A0134" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参加工作时间"></odin:NewDateEditTag>
					<odin:NewDateEditTag property="A0140" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入党时间文字"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:textEdit property="A0192" label="现工作单位及职务简称"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0192A" label="现工作单位及职务全称"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="QRZXL" label="最高全日制学历"  width="163" ></odin:textEdit>
					<odin:textEdit property="QRZXLXX" label="院校系专业（最高全日制学历）"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="QRZXW" label="最高全日制学位"  width="163" ></odin:textEdit>
					<odin:textEdit property="QRZXWXX" label="院校系专业（最高全日制学位）"  width="163" ></odin:textEdit>
				</tr>													
			</table>
	</odin:tabCont>
	<%-----------------------------考核信息-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab2">
		<div id="floatToolDiv2"></div>
		<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
			<odin:fill />
			<odin:buttonForToolBar text="执行修改" id="save2" isLast="true" handler="saveConfirm2"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz2" style="font-size: 12; color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：空项将不被修改</label>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<%-- <odin:select2 property="a1151Type" label="修改方式" codeType="XGFS" onchange="a1151Change"></odin:select2> --%>
					<td></td>
					
					<td>
						<input align="middle" type="radio" name="a1151Type" id="a1151Type0" checked="checked" value="0" class="radioItem" onclick="a1151Change()"/>
						<label for="a1151Type" style="font-size: 12px;">按年度修改(自动更新文字)</label>
						<span>&nbsp;</span>
					</td>
					<td></td>
					<td>
						<input align="middle" type="radio" name="a1151Type" id="a1151Type1" value="1" class="radioItem" onclick="a1151Change()"/>
						<label for="a1151Type" style="font-size: 12px;">直接修改文字(不与列表关联)</label>
					</td>
					
					
				</tr>
				<tr>
					<odin:select2 property="a1521" label="考核(开始)年度" maxlength="4" multiSelect="true"></odin:select2>
					<tags:PublicTextIconEdit property="a1517" label="考核结果"
						codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
				</tr>
				<tr>
					<odin:hidden property="a1500" title="主键id"></odin:hidden>
				</tr>
					<odin:textarea property="a15z101" cols="100" rows="3" colspan="6"
						label="年度考核"></odin:textarea>
				
				
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<odin:textEdit property="a1527" label="考核年度输出个数"></odin:textEdit>
					<%-- <td><odin:button text="&nbsp;&nbsp;设&nbsp;&nbsp;置&nbsp;&nbsp;" property="a1527Save" handler="a1527Fun"></odin:button></td> --%>
				</tr>
				
			</table>
	</odin:tabCont>

	<%-----------------------------职务-------------------------------------------------------%>
	<%-- <odin:tabCont itemIndex="tab4">
	<odin:hidden property="ChangeValue" title="职务名称代码和单位类别的联动"/>
		<div id="floatToolDiv4"></div>
		<odin:toolBar property="floatToolBar4" applyTo="floatToolDiv4">
			<odin:fill />
			<odin:buttonForToolBar text="执行修改" id="save4" isLast="true" handler="saveConfirm4"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<odin:hidden property="a0200" title="主键id"></odin:hidden>
			<table cellspacing="2" width="88%" align="center">
				<tr>
					<label id="bz4" style="font-size: 12; color: red">注：空项将不被修改</label>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><label id="gwlb" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;</font>岗位类别</label></td>
								<td><odin:select2 property="a0222" label=""
										codeType="ZB127" onchange="a0222SelChange"></odin:select2></td>
							</tr>
						</table>
					</td>

					<td>
						<table>
							<tr>
								<td><label id="rzjg" style="font-size: 12"></font>任职机构</label></td>
								<td><label id="gzjg" style="font-size: 12; display: none"><font
										color="red">*</font>工作机构</label></td>
								<td>
        <tags:PublicTextIconEdit3  codetype="orgTreeJsonData" onchange="a0201bchange" label="" property="a0201b" defaultValue=""/> 
								</td>
							</tr>
						</table>
					</td>

					<td>
						<table>
							<tr>
								<td><label id="zwmc" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务名称</label></td>
								<td><label id="gwmc" style="font-size: 12; display: none">岗位名称</label>
								</td>
								<td>
						<table>
						  <tr>
						    <tags:PublicTextIconEdit property="a0215a" width="100" codetype="ZB08" label="" onchange="setA0216aValue" readonly="true" maxlength="40"></tags:PublicTextIconEdit>
						  	<odin:textEdit property="a0216a" maxlength="50"></odin:textEdit>	 
						  </tr>
						</table>
					</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table>
							<tr>
								<td><label id="cylb" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;成员类别</label>
								</td>
								<td><odin:select2 property="a0201e" label=""
										codeType="ZB129"></odin:select2></td>
							</tr>
						</table>
					</td>

					<td>
						<div id='a0201dView' style="display: none">
							<table>
								<tr>
									<td><label id="bzcy" style="font-size: 12">&nbsp;班子成员</label>
									</td>
									<td><odin:select2 property="a0201d" label=""
											data="['1','是'],['0','否']"></odin:select2></td>
								</tr>
							</table>
						</div>
					</td>

					<td>
						<table>
							<tr>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td height="18"></td>
					<td></td>
					<td></td>
				</tr>
				<tr valign="top">
					<td>
						<table>
							<tr>
								<td>
									<div>
										<table>
											<tr>
												<td><label id="zwcc" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;职务层次</label>
												</td>
												<td><label id="gwcc"
													style="font-size: 12; display: none">岗位层次</label></td>
												<td><tags:PublicTextIconEdit codetype="ZB09" label=""
														property="a0221" onchange="a0221change" readonly="true" /></td>
											</tr>
										</table>
									</div>
									<div>
										<table>
											<tr>
												<td><label id="zggz" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;主管工作</label>
												</td>
												<td><label id="gwgz"
													style="font-size: 12; display: none">岗位工作</label></td>
												<td><odin:textEdit property="a0229" label=""></odin:textEdit>
												</td>
											</tr>
										</table>
									</div>
									<div id="a0247View">
										<table>
											<tr>
												<td><label id="xbfs" style="font-size: 12">选拔任用方式</label>
												</td>
												<td><tags:PublicTextIconEdit property="a0247" label=""
														codetype="ZB122" readonly="true"></tags:PublicTextIconEdit>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<div id="a0251View">
							<table>
								<tr>
									<td><odin:select2 property="a0251" label="职动类型"
											codeType="ZB13"></odin:select2></td>
								</tr>
							</table>
						</div>
						<div>
							<table>
								<tr>
									<td><odin:textEdit property="a0245" label="任职文号"></odin:textEdit>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td>
						<div id="a0219View">
							<table>
								<tr>
									<td><odin:select2 property="a0219" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务类别"
											codeType="ZB42"></odin:select2></td>
								</tr>
							</table>
						</div>
						<div>
							<table>
								<tr>
									<td><label id="rzsj" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任职时间&nbsp;</label>
									</td>
									<td><label id="gzks" style="font-size: 12; display: none">工作开始&nbsp;&nbsp;</label>
									</td>
									<td><odin:NewDateEditTag property="a0243" maxlength="8"></odin:NewDateEditTag></td>
								</tr>
							</table>
						</div>
						<div id="a0288View">
							<table>
								<tr>
									<td><odin:NewDateEditTag property="a0288" label="现任职务层次时间"
											maxlength="8"></odin:NewDateEditTag></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
	</odin:tabCont> --%>
	
	
	
	
	
	<%-----------------------------奖惩-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab5">
		<odin:toolBar property="floatToolBar5" applyTo="floatToolDiv5">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="执行修改" id="save5" isLast="true" handler="saveConfirm5"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv5"></Div>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz5" style="font-size: 12; color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：空项将不被修改</label>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1404b" label="奖惩名称代码"
						onchange="setA1404aValue" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>
					<odin:textEdit property="a1404a" label="奖惩名称"></odin:textEdit>
					
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1415" label="受奖惩时职务层次" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit>
					<odin:select2 property="a1414" label="批准机关级别" codeType="ZB03"></odin:select2>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1428" label="批准机关性质"
						readonly="true" codetype="ZB128"></tags:PublicTextIconEdit>
					<odin:textEdit property="a1411a" label="批准机关"></odin:textEdit>
				</tr>
				<tr>
					<odin:NewDateEditTag property="a1407" label="批准时间" maxlength="8"
						isCheck="true"></odin:NewDateEditTag>
					<odin:NewDateEditTag property="a1424" label="撤销时间" maxlength="8"
						isCheck="true"></odin:NewDateEditTag>
					<%--<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>--%>
					<odin:hidden property="a1400" title="主键id"></odin:hidden>
				</tr>

			</table>
	</odin:tabCont>
	
	
</odin:tab>
</div>
<odin:hidden property="type" value=""/>
<odin:hidden property="ids" />
<odin:hidden property="checkIds" />
<odin:hidden property="ZJerrorId" />
<odin:hidden property="ZWCCerrorId" />
<%-----------------------------其他信息------------<odin:window src="/blank.htm"  id="professSkillAddPage" width="600" height="380" title="专业技术职务" />-------------------------------------------%>

<script>
Ext.onReady(function(){
	var width=document.body.clientWidth;
	var height=document.body.clientHeight;
	Ext.getCmp("peopleInfoGrid").setHeight(height);
	Ext.getCmp("tab").setHeight(height);
	//Ext.getCmp('a0215a_combo').hide();
});
	//职务层次  职务级别 二选一
	/* function a0221achange() {
		var a0221a = document.getElementById('a0221a').value;
		var a0221 = document.getElementById('a0221').value;
		if ((a0221a != null && a0221a != '' && a0221 != null && a0221 != '')
				|| (a0221a == '' && a0221 == '')) {
			//$h.selectShow('a0221');
			//$h.selectShow('a0221a');
			selecteWinEnable('a0221');
			selecteWinEnable('a0221a');
			return;
		}

		if (a0221a == null || a0221a == '') {
			//selecteEnable('a0221');
			selecteWinEnable('a0221');
		} else {
			//$h.selectHide('a0221');
			selecteWinDisable('a0221');
		}
	}
	function a0221change() {
		var a0221 = document.getElementById('a0221').value;
		var a0221a = document.getElementById('a0221a').value;
		if (a0221a != null && a0221a != '' && a0221 != null && a0221 != '') {
			//$h.selectShow('a0221');
			//$h.selectShow('a0221a');
			selecteWinEnable('a0221');
			selecteWinEnable('a0221a');
			return;
		}
		if (a0221 == null || a0221 == '') {
			//$h.selectShow('a0221a');
			selecteWinEnable('a0221a');
		} else {
			//$h.selectHide('a0221a');
			selecteWinDisable('a0221a');
		}
	} */
	
	function onkeydownfn(id){
		/* if(id=='a0221a'){
			a0221achange();
		}else if(id=='a0221'){
			a0221change();
		} */
	}

	function a0201bchange(record) {

		var a0222 = document.getElementById("a0222").value;
		//var a0255 = Ext.getCmp('a0255_combo');	
		if ("01" == a0222) {//公务员、参照管理人员岗位or其他
			document.getElementById("a0201dView").style.display = 'block';
		} else {
			document.getElementById("a0201dView").style.display = 'none';
		}

		radow.doEvent('setZB08Code',record.data.key);
	}

	function IsEmpty(fData) {
		return ((fData == null) || (fData.length == 0))
	}

	function a0222SelChange(record, index) {
		//a0219View,a0251View,a0247View,a0288View

		//岗位类别
		//var a0222 = record.data.key;
		var a0222 = document.getElementById("a0222").value;
		var a0201b = document.getElementById("a0201b").value;
		//var a0255 = Ext.getCmp('a0255_combo');	
		if ("01" == a0222) {
			document.getElementById("codevalueparameter").value = "01";
		}
		if ("02" == a0222) {
			document.getElementById("codevalueparameter").value = "02";
		}
		if ("03" == a0222) {
			document.getElementById("codevalueparameter").value = "03";
		}
		if ("04" == a0222) {
			document.getElementById("codevalueparameter").value = "04";
		}
		if ("05" == a0222) {
			document.getElementById("codevalueparameter").value = "05";
		}
		if ("06" == a0222) {
			document.getElementById("codevalueparameter").value = "06";
		}
		if ("07" == a0222) {
			document.getElementById("codevalueparameter").value = "07";
		}
		if ("99" == a0222) {
			document.getElementById("codevalueparameter").value = "";
		}
		if ("01" == a0222 || "99" == a0222) {//公务员、参照管理人员岗位or其他
			document.getElementById("gzjg").style.display = 'none';
			document.getElementById("gwmc").style.display = 'none';
			document.getElementById("gwcc").style.display = 'none';
			document.getElementById("gwgz").style.display = 'none';
			document.getElementById("gzks").style.display = 'none';
			document.getElementById("rzjg").style.display = 'block';
			document.getElementById("zwmc").style.display = 'block';
			document.getElementById("zwcc").style.display = 'block';
			document.getElementById("zggz").style.display = 'block';
			document.getElementById("rzsj").style.display = 'block';
			selecteEnable('a0219');//职务类别
			selecteEnable('a0251');//职动类型
			selecteEnable('a0247');//选拔任用方式
			///selecteEnable('a0271');//免职类别
			///selecteEnable('a4904');//交流原因
			///selecteEnable('a4901');//交流方向
			///selecteEnable('a4907');//交流去向
			///textEnable('a0267');//免职文号
			///textEnable('a0265');//免职时间
			textEnable('a0288');//现任职务层次时间

			///document.getElementById("mView").style.display='block'; 
			///document.getElementById("mnView").style.display='none';
			///changeSelectData({one:{key:'1',value:'在任'},two:{key:'0',value:'已免'}});

			if (("01" == a0222) && (!IsEmpty(a0201b)))
				document.getElementById("a0201dView").style.display = 'block';
			document.getElementById("a0219View").style.display = 'block';
			document.getElementById("a0251View").style.display = 'block';
			document.getElementById("a0247View").style.display = 'block';
			document.getElementById("a0288View").style.display = 'block';
		} else if ("02" == a0222 || "03" == a0222) {//事业单位管理岗位or事业单位专业技术岗位
			document.getElementById("gzjg").style.display = 'none';
			document.getElementById("gwmc").style.display = 'none';
			document.getElementById("gwcc").style.display = 'none';
			document.getElementById("gwgz").style.display = 'none';
			document.getElementById("gzks").style.display = 'none';
			document.getElementById("rzjg").style.display = 'block';
			document.getElementById("zwmc").style.display = 'block';
			document.getElementById("zwcc").style.display = 'block';
			document.getElementById("zggz").style.display = 'block';
			document.getElementById("rzsj").style.display = 'block';
			selecteDisable('a0219');//职务类别disabled
			selecteEnable('a0251');//职动类型
			selecteEnable('a0247');//选拔任用方式
			textEnable('a0288');//现任职务层次时间

			document.getElementById("a0201dView").style.display = 'none';
			document.getElementById("a0219View").style.display = 'none';
		} else if ("04" == a0222 || "05" == a0222 || "06" == a0222
				|| "07" == a0222) {//机关技术工人岗位or机关普通工人岗位or事业单位技术工人岗位or事业单位普通工人岗位
			document.getElementById("gzjg").style.display = 'block';
			document.getElementById("gwmc").style.display = 'block';
			document.getElementById("gwcc").style.display = 'block';
			document.getElementById("gwgz").style.display = 'block';
			document.getElementById("gzks").style.display = 'block';
			document.getElementById("rzjg").style.display = 'none';
			document.getElementById("zwmc").style.display = 'none';
			document.getElementById("zwcc").style.display = 'none';
			document.getElementById("zggz").style.display = 'none';
			document.getElementById("rzsj").style.display = 'none';

			selecteDisable('a0219');//职务类别
			selecteDisable('a0251');//职动类型
			selecteDisable('a0247');//选拔任用方式
			textDisable('a0288');//现任职务层次时间	

			document.getElementById("a0201dView").style.display = 'none';
			document.getElementById("a0219View").style.display = 'none';
			document.getElementById("a0251View").style.display = 'none';
			document.getElementById("a0247View").style.display = 'none';
			document.getElementById("a0288View").style.display = 'none';

		}
	}

	///function setA0255Value(record,index){
	///Ext.getCmp('a0255').setValue(record.data.key)
	///}
	//a01统计关系所在单位
	function setParentValue(record, index) {
		
		//realParent.document.getElementById('a0195').value = record.data.key;
	}
	
	//批量修改前提示 
	function saveConfirm(){
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm2(){
		//判断考核年度输出个数是否合法
		var a1527  = document.getElementById('a1527').value;				//考核年度输出个数
		
		if (!(/(^[0-9]\d*$)/.test(a1527))) { 
	　　　　
			Ext.Msg.alert("提示信息", "考核年度输出个数不是自然数！");
	　　　　return false; 
	　　}
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save2");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm3(){
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save3");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm4(){
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save4");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm5(){
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save5");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm6(){
		Ext.Msg.confirm("系统提示","是否确认批量修改？",function(id) { 
			if("yes"==id){
				radow.doEvent("save6");
			}else{
				return;
			}		
		});	
	}
	
	function getTabletype(){
		var type = realParent.document.getElementById("tableType").value;
		document.getElementById("type").value = type;
	}
	
	
	
	
	
	
	/* function check(){
		var gridId = "peopleInfoGrid";
		var fieldName = "personcheck";
		var store = odin.ext.getCmp(gridId).store;
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,1);
		}
	} */
	
	Ext.onReady(function(){
		var grid = Ext.getCmp("peopleInfoGrid");
		var store = grid.getStore();
		store.on({
			load:{
				fn:function(){
					//默认勾选全部人员
					odin.selectAllFuncForE3("peopleInfoGrid",document.getElementById("selectall_peopleInfoGrid_personcheck"),"personcheck");
					radow.doEvent("getCheckPeople");
		           }
			},
			scope:this
		});
	});
	
	/* $(function(){
		//是否考录
	    $("#a99z101").change(function() {
	    	
	    	var a99z101 = document.getElementById("a99z101").checked;
	    	
	    	if(!a99z101){
	    		$h.dateDisable('a99z102');
	    	}else{
	    		$h.dateEnable('a99z102');
	    	}
	    	
	    });
		
		//是否选调生
	    $("#a99z103").change(function() {
	    	var a99z103 = document.getElementById("a99z103").checked;
	    	
			if(!a99z103){
				$h.dateDisable('a99z104');
	    	}else{
	    		$h.dateEnable('a99z104');
	    	}
	    	
	    });
	});  */
	
	
	//是否考录
	function a99z101Change(){
		var a99z101 = document.getElementById("a99z101").value;
    	
    	if(a99z101 == 1){
    		$h.dateEnable('a99z102');
    	}else{
    		$h.dateDisable('a99z102');
    	}
	}
	
	//是否选调生
	function a99z103Change(){
		var a99z103 = document.getElementById("a99z103").value;
    	
		if(a99z103 == 1){
			$h.dateEnable('a99z104');
    	}else{
    		$h.dateDisable('a99z104');
    	}
	}
	
	
	
	//职级或者职务层次有空信息,需要确认 
	function saveZWZJConfirm(msg){
		Ext.Msg.confirm("系统提示",msg,function(id) { 
			if("yes"==id){
				radow.doEvent("saveZWZJConfirm");
			}else{
				radow.doEvent("saveA01");
			}		
		});	
	}
	
//设置按钮与执行修改按钮合并  2019.12.04 yzk
/* 	//设置考核年度输出个数
	function a1527Fun(){
		
		//判断考核年度输出个数是否合法
		var a1527  = document.getElementById('a1527').value;				//考核年度输出个数
		
		if (!(/(^[0-9]\d*$)/.test(a1527))) { 
	　　　　
			Ext.Msg.alert("提示信息", "考核年度输出个数不是自然数！");
	　　　　return false; 
	　　}else { 
			
			radow.doEvent("a1527Save");
	　　} 
		
	} */
</script>