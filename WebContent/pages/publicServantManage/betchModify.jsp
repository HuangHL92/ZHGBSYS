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
	/**ɾ��ѡ����  ����**/
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
	/**�����**/
	function addRow(obj) {
		radow.addGridEmptyRow(obj.initialConfig.cls, 0);
	}
	/**ɾ��ѡ����  ����**/
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
	//����
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0111').value = record.data.key;
	}
	//������
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0114').value = record.data.key;
	}

	function setA1404aValue(record, index) {//��������
		Ext.getCmp('a1404a').setValue(record.data.value);
	}
	
	function setA0216aValue(record,index){//ְ����
		Ext.getCmp('a0216a').setValue(record.data.value);
	}
	//�������
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
				<odin:editgrid property="peopleInfoGrid" title="��Ա��Ϣ�б�" bbarId="pageToolBar" pageSize="500"
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
						<odin:gridEditColumn2 dataIndex="a0101" width="80" header="����"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="110" header="������λ��ְ��"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0000" width="110" header="��Աid"
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
		<odin:tabItem title="��Ա������Ϣ" id="tab1"></odin:tabItem>
		<odin:tabItem title="����" id="tab2"></odin:tabItem>
		<%-- <odin:tabItem title="ְ��" id="tab4"></odin:tabItem> --%>
		<odin:tabItem title="����" id="tab5" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<%-----------------------------��Ա������Ϣ-------------------<odin:hidden property="a0000"/>------------------------------------%>
	<odin:tabCont itemIndex="tab1">
		<div id="floatToolDiv1"></div>
		<odin:toolBar property="floatToolBar1" applyTo="floatToolDiv1">
			<odin:fill />
			<odin:buttonForToolBar text="ִ���޸�" id="save" isLast="true" handler="saveConfirm"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz" style="font-size: 12; color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע����������޸�</label>
					
				</tr>
				
				<tr>
					<td></td>
					<td><odin:select2 property="fkbs" label="�Ƿ�����ɲ�" data="['1','��'],['0','��']"/></td>
				</tr>
				<tr>
					
						<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="ͳ�ƹ�ϵ���ڵ�λ" readonly="true"
						codetype="orgTreeJsonData"></tags:PublicTextIconEdit3>
					
					<!-- <td></td> -->
					<!-- <td>
						<input type="checkbox" name="a99z101" id="a99z101"/>
						<label id="a99z103SpanId" for="a99z101" style="font-size: 12px;">�Ƿ�¼</label>
					</td> -->
					
					<odin:select2 property="a99z101" label="�Ƿ�¼" data="['1','��'],['0','��']" onchange="a99z101Change"/>
				</tr>
				<tr>
					<odin:select2 property="a0160" label="��Ա���" codeType="ZB125"
						onchange="onA0160Change" onblur="setParentValue2(this)"></odin:select2>
					<odin:NewDateEditTag property="a99z102" isCheck="true" maxlength="8" width="163" label="¼��ʱ��"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:select2 property="a0165" label="�������" codeType="ZB130"></odin:select2>
					<odin:select2 property="a99z103" label="�Ƿ�ѡ����" data="['1','��'],['0','��']" onchange="a99z103Change"/>					
				</tr>
				
				<tr>
					<odin:select2 property="a0121" label="��������" codeType="ZB135"
						onblur="setParentValue(this)"></odin:select2>
					<odin:NewDateEditTag property="a99z104" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ѡ����ʱ��"></odin:NewDateEditTag>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="A0221" codetype="ZB09"  label="ְ����"  />
				
					<tags:PublicTextIconEdit property="A0192E" codetype="ZB148"  label="ְ��"  />
				</tr>
				
				<tr>
					<odin:select2 property="a0197" label="�����������ϻ��㹤������" data="['1','��'],['0','��']" />
					<odin:NewDateEditTag property="A0288" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ����ʱ��"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:NewDateEditTag property="A0107" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������"></odin:NewDateEditTag>
					<odin:textEdit property="A0111A" label="����"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="A0114A" label="������"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0115A" label="�ɳ���"  width="163" ></odin:textEdit>
				</tr>								
				<tr>
					<odin:textEdit property="A0117" label="����"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0128" label="�������"  width="163" ></odin:textEdit>
				</tr>	
				<tr>
					<odin:NewDateEditTag property="A0134" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�μӹ���ʱ��"></odin:NewDateEditTag>
					<odin:NewDateEditTag property="A0140" isCheck="true" maxlength="8" width="163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�뵳ʱ������"></odin:NewDateEditTag>
				</tr>
				<tr>
					<odin:textEdit property="A0192" label="�ֹ�����λ��ְ����"  width="163" ></odin:textEdit>
					<odin:textEdit property="A0192A" label="�ֹ�����λ��ְ��ȫ��"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="QRZXL" label="���ȫ����ѧ��"  width="163" ></odin:textEdit>
					<odin:textEdit property="QRZXLXX" label="ԺУϵרҵ�����ȫ����ѧ����"  width="163" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="QRZXW" label="���ȫ����ѧλ"  width="163" ></odin:textEdit>
					<odin:textEdit property="QRZXWXX" label="ԺУϵרҵ�����ȫ����ѧλ��"  width="163" ></odin:textEdit>
				</tr>													
			</table>
	</odin:tabCont>
	<%-----------------------------������Ϣ-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab2">
		<div id="floatToolDiv2"></div>
		<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
			<odin:fill />
			<odin:buttonForToolBar text="ִ���޸�" id="save2" isLast="true" handler="saveConfirm2"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz2" style="font-size: 12; color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע����������޸�</label>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<%-- <odin:select2 property="a1151Type" label="�޸ķ�ʽ" codeType="XGFS" onchange="a1151Change"></odin:select2> --%>
					<td></td>
					
					<td>
						<input align="middle" type="radio" name="a1151Type" id="a1151Type0" checked="checked" value="0" class="radioItem" onclick="a1151Change()"/>
						<label for="a1151Type" style="font-size: 12px;">������޸�(�Զ���������)</label>
						<span>&nbsp;</span>
					</td>
					<td></td>
					<td>
						<input align="middle" type="radio" name="a1151Type" id="a1151Type1" value="1" class="radioItem" onclick="a1151Change()"/>
						<label for="a1151Type" style="font-size: 12px;">ֱ���޸�����(�����б����)</label>
					</td>
					
					
				</tr>
				<tr>
					<odin:select2 property="a1521" label="����(��ʼ)���" maxlength="4" multiSelect="true"></odin:select2>
					<tags:PublicTextIconEdit property="a1517" label="���˽��"
						codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
				</tr>
				<tr>
					<odin:hidden property="a1500" title="����id"></odin:hidden>
				</tr>
					<odin:textarea property="a15z101" cols="100" rows="3" colspan="6"
						label="��ȿ���"></odin:textarea>
				
				
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<odin:textEdit property="a1527" label="��������������"></odin:textEdit>
					<%-- <td><odin:button text="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" property="a1527Save" handler="a1527Fun"></odin:button></td> --%>
				</tr>
				
			</table>
	</odin:tabCont>

	<%-----------------------------ְ��-------------------------------------------------------%>
	<%-- <odin:tabCont itemIndex="tab4">
	<odin:hidden property="ChangeValue" title="ְ�����ƴ���͵�λ��������"/>
		<div id="floatToolDiv4"></div>
		<odin:toolBar property="floatToolBar4" applyTo="floatToolDiv4">
			<odin:fill />
			<odin:buttonForToolBar text="ִ���޸�" id="save4" isLast="true" handler="saveConfirm4"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
			<odin:hidden property="a0200" title="����id"></odin:hidden>
			<table cellspacing="2" width="88%" align="center">
				<tr>
					<label id="bz4" style="font-size: 12; color: red">ע����������޸�</label>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><label id="gwlb" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;</font>��λ���</label></td>
								<td><odin:select2 property="a0222" label=""
										codeType="ZB127" onchange="a0222SelChange"></odin:select2></td>
							</tr>
						</table>
					</td>

					<td>
						<table>
							<tr>
								<td><label id="rzjg" style="font-size: 12"></font>��ְ����</label></td>
								<td><label id="gzjg" style="font-size: 12; display: none"><font
										color="red">*</font>��������</label></td>
								<td>
        <tags:PublicTextIconEdit3  codetype="orgTreeJsonData" onchange="a0201bchange" label="" property="a0201b" defaultValue=""/> 
								</td>
							</tr>
						</table>
					</td>

					<td>
						<table>
							<tr>
								<td><label id="zwmc" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ������</label></td>
								<td><label id="gwmc" style="font-size: 12; display: none">��λ����</label>
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
								<td><label id="cylb" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;��Ա���</label>
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
									<td><label id="bzcy" style="font-size: 12">&nbsp;���ӳ�Ա</label>
									</td>
									<td><odin:select2 property="a0201d" label=""
											data="['1','��'],['0','��']"></odin:select2></td>
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
												<td><label id="zwcc" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;ְ����</label>
												</td>
												<td><label id="gwcc"
													style="font-size: 12; display: none">��λ���</label></td>
												<td><tags:PublicTextIconEdit codetype="ZB09" label=""
														property="a0221" onchange="a0221change" readonly="true" /></td>
											</tr>
										</table>
									</div>
									<div>
										<table>
											<tr>
												<td><label id="zggz" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;���ܹ���</label>
												</td>
												<td><label id="gwgz"
													style="font-size: 12; display: none">��λ����</label></td>
												<td><odin:textEdit property="a0229" label=""></odin:textEdit>
												</td>
											</tr>
										</table>
									</div>
									<div id="a0247View">
										<table>
											<tr>
												<td><label id="xbfs" style="font-size: 12">ѡ�����÷�ʽ</label>
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
									<td><odin:select2 property="a0251" label="ְ������"
											codeType="ZB13"></odin:select2></td>
								</tr>
							</table>
						</div>
						<div>
							<table>
								<tr>
									<td><odin:textEdit property="a0245" label="��ְ�ĺ�"></odin:textEdit>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td>
						<div id="a0219View">
							<table>
								<tr>
									<td><odin:select2 property="a0219" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ�����"
											codeType="ZB42"></odin:select2></td>
								</tr>
							</table>
						</div>
						<div>
							<table>
								<tr>
									<td><label id="rzsj" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ְʱ��&nbsp;</label>
									</td>
									<td><label id="gzks" style="font-size: 12; display: none">������ʼ&nbsp;&nbsp;</label>
									</td>
									<td><odin:NewDateEditTag property="a0243" maxlength="8"></odin:NewDateEditTag></td>
								</tr>
							</table>
						</div>
						<div id="a0288View">
							<table>
								<tr>
									<td><odin:NewDateEditTag property="a0288" label="����ְ����ʱ��"
											maxlength="8"></odin:NewDateEditTag></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
	</odin:tabCont> --%>
	
	
	
	
	
	<%-----------------------------����-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab5">
		<odin:toolBar property="floatToolBar5" applyTo="floatToolDiv5">
			<odin:textForToolBar text="" />
			<odin:fill />
			<odin:buttonForToolBar text="ִ���޸�" id="save5" isLast="true" handler="saveConfirm5"
				icon="images/save.gif" cls="x-btn-text-icon" />
		</odin:toolBar>
		<Div id="floatToolDiv5"></Div>
			<table cellspacing="2" width="88%" align="left">
				<tr>
					<label id="bz5" style="font-size: 12; color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע����������޸�</label>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1404b" label="�������ƴ���"
						onchange="setA1404aValue" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>
					<odin:textEdit property="a1404a" label="��������"></odin:textEdit>
					
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1415" label="�ܽ���ʱְ����" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit>
					<odin:select2 property="a1414" label="��׼���ؼ���" codeType="ZB03"></odin:select2>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a1428" label="��׼��������"
						readonly="true" codetype="ZB128"></tags:PublicTextIconEdit>
					<odin:textEdit property="a1411a" label="��׼����"></odin:textEdit>
				</tr>
				<tr>
					<odin:NewDateEditTag property="a1407" label="��׼ʱ��" maxlength="8"
						isCheck="true"></odin:NewDateEditTag>
					<odin:NewDateEditTag property="a1424" label="����ʱ��" maxlength="8"
						isCheck="true"></odin:NewDateEditTag>
					<%--<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>--%>
					<odin:hidden property="a1400" title="����id"></odin:hidden>
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
<%-----------------------------������Ϣ------------<odin:window src="/blank.htm"  id="professSkillAddPage" width="600" height="380" title="רҵ����ְ��" />-------------------------------------------%>

<script>
Ext.onReady(function(){
	var width=document.body.clientWidth;
	var height=document.body.clientHeight;
	Ext.getCmp("peopleInfoGrid").setHeight(height);
	Ext.getCmp("tab").setHeight(height);
	//Ext.getCmp('a0215a_combo').hide();
});
	//ְ����  ְ�񼶱� ��ѡһ
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
		if ("01" == a0222) {//����Ա�����չ�����Ա��λor����
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

		//��λ���
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
		if ("01" == a0222 || "99" == a0222) {//����Ա�����չ�����Ա��λor����
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
			selecteEnable('a0219');//ְ�����
			selecteEnable('a0251');//ְ������
			selecteEnable('a0247');//ѡ�����÷�ʽ
			///selecteEnable('a0271');//��ְ���
			///selecteEnable('a4904');//����ԭ��
			///selecteEnable('a4901');//��������
			///selecteEnable('a4907');//����ȥ��
			///textEnable('a0267');//��ְ�ĺ�
			///textEnable('a0265');//��ְʱ��
			textEnable('a0288');//����ְ����ʱ��

			///document.getElementById("mView").style.display='block'; 
			///document.getElementById("mnView").style.display='none';
			///changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});

			if (("01" == a0222) && (!IsEmpty(a0201b)))
				document.getElementById("a0201dView").style.display = 'block';
			document.getElementById("a0219View").style.display = 'block';
			document.getElementById("a0251View").style.display = 'block';
			document.getElementById("a0247View").style.display = 'block';
			document.getElementById("a0288View").style.display = 'block';
		} else if ("02" == a0222 || "03" == a0222) {//��ҵ��λ�����λor��ҵ��λרҵ������λ
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
			selecteDisable('a0219');//ְ�����disabled
			selecteEnable('a0251');//ְ������
			selecteEnable('a0247');//ѡ�����÷�ʽ
			textEnable('a0288');//����ְ����ʱ��

			document.getElementById("a0201dView").style.display = 'none';
			document.getElementById("a0219View").style.display = 'none';
		} else if ("04" == a0222 || "05" == a0222 || "06" == a0222
				|| "07" == a0222) {//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
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

			selecteDisable('a0219');//ְ�����
			selecteDisable('a0251');//ְ������
			selecteDisable('a0247');//ѡ�����÷�ʽ
			textDisable('a0288');//����ְ����ʱ��	

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
	//a01ͳ�ƹ�ϵ���ڵ�λ
	function setParentValue(record, index) {
		
		//realParent.document.getElementById('a0195').value = record.data.key;
	}
	
	//�����޸�ǰ��ʾ 
	function saveConfirm(){
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
			if("yes"==id){
				radow.doEvent("save");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm2(){
		//�жϿ��������������Ƿ�Ϸ�
		var a1527  = document.getElementById('a1527').value;				//��������������
		
		if (!(/(^[0-9]\d*$)/.test(a1527))) { 
	��������
			Ext.Msg.alert("��ʾ��Ϣ", "��������������������Ȼ����");
	��������return false; 
	����}
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
			if("yes"==id){
				radow.doEvent("save2");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm3(){
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
			if("yes"==id){
				radow.doEvent("save3");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm4(){
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
			if("yes"==id){
				radow.doEvent("save4");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm5(){
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
			if("yes"==id){
				radow.doEvent("save5");
			}else{
				return;
			}		
		});	
	}
	
	function saveConfirm6(){
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�������޸ģ�",function(id) { 
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
					//Ĭ�Ϲ�ѡȫ����Ա
					odin.selectAllFuncForE3("peopleInfoGrid",document.getElementById("selectall_peopleInfoGrid_personcheck"),"personcheck");
					radow.doEvent("getCheckPeople");
		           }
			},
			scope:this
		});
	});
	
	/* $(function(){
		//�Ƿ�¼
	    $("#a99z101").change(function() {
	    	
	    	var a99z101 = document.getElementById("a99z101").checked;
	    	
	    	if(!a99z101){
	    		$h.dateDisable('a99z102');
	    	}else{
	    		$h.dateEnable('a99z102');
	    	}
	    	
	    });
		
		//�Ƿ�ѡ����
	    $("#a99z103").change(function() {
	    	var a99z103 = document.getElementById("a99z103").checked;
	    	
			if(!a99z103){
				$h.dateDisable('a99z104');
	    	}else{
	    		$h.dateEnable('a99z104');
	    	}
	    	
	    });
	});  */
	
	
	//�Ƿ�¼
	function a99z101Change(){
		var a99z101 = document.getElementById("a99z101").value;
    	
    	if(a99z101 == 1){
    		$h.dateEnable('a99z102');
    	}else{
    		$h.dateDisable('a99z102');
    	}
	}
	
	//�Ƿ�ѡ����
	function a99z103Change(){
		var a99z103 = document.getElementById("a99z103").value;
    	
		if(a99z103 == 1){
			$h.dateEnable('a99z104');
    	}else{
    		$h.dateDisable('a99z104');
    	}
	}
	
	
	
	//ְ������ְ�����п���Ϣ,��Ҫȷ�� 
	function saveZWZJConfirm(msg){
		Ext.Msg.confirm("ϵͳ��ʾ",msg,function(id) { 
			if("yes"==id){
				radow.doEvent("saveZWZJConfirm");
			}else{
				radow.doEvent("saveA01");
			}		
		});	
	}
	
//���ð�ť��ִ���޸İ�ť�ϲ�  2019.12.04 yzk
/* 	//���ÿ�������������
	function a1527Fun(){
		
		//�жϿ��������������Ƿ�Ϸ�
		var a1527  = document.getElementById('a1527').value;				//��������������
		
		if (!(/(^[0-9]\d*$)/.test(a1527))) { 
	��������
			Ext.Msg.alert("��ʾ��Ϣ", "��������������������Ȼ����");
	��������return false; 
	����}else { 
			
			radow.doEvent("a1527Save");
	����} 
		
	} */
</script>