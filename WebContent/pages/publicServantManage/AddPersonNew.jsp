<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<style>
.x-panel-header{
border: 0px;

}
.x-toolbar span{
	font: bold;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
/*
function fillfield(obj){
	var isv = validator.IDCard(obj.value);
	if(!isv){
		alert(validator.IDCardText);
	}
}
*/
/**ɾ��ѡ����  ����**/
function delRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);				
	}
	grid.view.refresh();			
}
/**�����**/
function addRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount();
	radow.addGridEmptyRow(obj.initialConfig.cls,i);
}
/**ɾ��ѡ����  ����**/
function delCheckedRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount()-1;
	if(store.getCount() > 0){
		for( var i = store.getCount()-1 ;i>=0; i-- ){
			var ck = grid.getStore().getAt(i).get("logchecked");
	        if(ck == true){
	        	store.remove(grid.getStore().getAt(i));
			}
		}
	}
}
</script>
<odin:tab id="tab" tabchange="grantTabChange">
	<odin:tabModel>
		<odin:tabItem title="������Ϣ" id="tab1"></odin:tabItem>
		<odin:tabItem title="������Ϣ" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1">
<odin:floatDiv property="floatToolDiv" position="up" ></odin:floatDiv>
<br/><br/><br/>
<odin:toolBar property="floatToolBar" applyTo="floatToolDiv">
	<odin:buttonForToolBar text="��" handler="scroll" cls="tab" /><odin:separator/>
	<odin:buttonForToolBar text="��Ա������Ϣ" handler="scroll" cls="s1" /><odin:separator/>
	<odin:buttonForToolBar text="רҵ����ְ��" handler="scroll" cls="s2" /><odin:separator/>
	<odin:buttonForToolBar text="ѧλѧ��" handler="scroll" cls="s3" /><odin:separator/>
	<odin:buttonForToolBar text="������λ��ְ��" handler="scroll" cls="s4" /><odin:separator/>
	<odin:buttonForToolBar text="�������" handler="scroll" cls="s6" /><odin:separator/>
	<odin:buttonForToolBar text="��ȿ������" handler="scroll" cls="s7" /><odin:separator/>
	<odin:buttonForToolBar text="��ͥ��Ҫ��Ա����Ҫ����ϵ" handler="scroll" cls="s8" /><odin:separator/>
	<odin:buttonForToolBar text="��ѵ��Ϣ" handler="scroll" cls="s12" />
	<odin:fill />
	<odin:buttonForToolBar text="����" id="save" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<%-----------------------------��Ա������Ϣ-------------------<odin:hidden property="a0000"/>------------------------------------%>
<!--<odin:groupBox property="s1" title="��Ա������Ϣ">
--><table cellspacing="4" width="100%" align="center" border="0px" >
	<tr>		
		<odin:textEdit property="a0101" label="����" required="true"></odin:textEdit>
		<odin:select property="a0104" label="�Ա�" codeType="GB2261" ></odin:select>
		<odin:dateEdit property="a0107" label="��������" format="Ymd" ></odin:dateEdit>
		<td rowspan="4">
		<div id="photo" style="overflow: auto; height: 140px; width: 120px; border: 2px solid #c3daf9;">
		<label style="font-size: 24" id="photo">��Ƭ</label>
		</div>
		</td>
		
		<!--<odin:select property="a0165" label="�������" codeType="ZB130" ></odin:select>	
		<odin:select property="a0141" label="��һ����" codeType="GB4762" ></odin:select>-->
	</tr>
	<tr>
	    <odin:select property="a0117" label="����" codeType="GB3304"></odin:select>
	    <odin:textEdit property="a0111" label="����" ></odin:textEdit>
		<!--<odin:textEdit property="a0184" label="���֤��" required="true"></odin:textEdit>	-->
		<odin:textEdit property="a0114" label="������" ></odin:textEdit>
		<!--<odin:select property="a0160" label="��Ա���" codeType="ZB125"></odin:select>
		<odin:dateEdit property="a0144" label="�뵳ʱ��" format="Ymd"></odin:dateEdit>-->
	</tr>
	<tr>
		<odin:select property="a0141" label="��һ����" codeType="GB4762" ></odin:select>		
		<odin:dateEdit property="a0144" label="�뵳ʱ��" format="Ymd"></odin:dateEdit>
		<!--<odin:textEdit property="a0128" label="����״��" value="����"></odin:textEdit>-->
		<odin:select property="a3921" label="�ڶ�����" codeType="GB4762"></odin:select>		
	</tr>
	<tr>
	    <odin:hidden property="a0000" title="����a0000" ></odin:hidden>
	    <odin:select property="a3927" label="��������" codeType="GB4762"></odin:select>
		<odin:dateEdit property="a0134" label="�μӹ���ʱ��" format="Ymd"></odin:dateEdit>	
		<odin:textEdit property="a0187a" label="��Ϥרҵ�к�ר��"></odin:textEdit>
		<!--<odin:textarea property="a0187a" cols="70" rows="4" colspan="4" label="��Ϥרҵ�к�ר��" ></odin:textarea>-->	
	</tr>
	<tr>		
		<odin:textEdit property="a0128" label="����״��" value="����"></odin:textEdit>
		<odin:select property="a0160" label="��Ա���" codeType="ZB125"></odin:select>
		<odin:textEdit property="a0184" label="���֤��" required="true"></odin:textEdit>
		<odin:textEdit property="a0194" label="���㹤����������" size="6"></odin:textEdit>	
		<!--<odin:textarea property="a1701" cols="70" rows="4" colspan="4" label="����" ></odin:textarea>-->	
	</tr>
	</table>
<!--</odin:groupBox>
--><%-----------------------------רҵ����ְ��-------------------------------------------------------%>
<!--<odin:groupBox property="s2" title="רҵ����ְ��">-->

<odin:toolBar property="tol1" applyTo="toolBar11">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>רҵ����ְ��<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center" >
	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="professSkillAddBtn" cls="professSkillgrid" handler="addRow"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="professSkillgrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar11"></div>
				<table align="center" width="98%">
    <tr>	
		<odin:textEdit property="a0196" label="רҵ����ְ��" readonly="true" colspan="8" width="1010"></odin:textEdit>	
			
	</tr>
</table>
			<odin:editgrid property="professSkillgrid" isFirstLoadData="false" forceNoScroll="true" url="/" topBarId="toolBar1" 
			 height="140" pageSize="5">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0600" />
					<odin:gridDataCol name="a0601" />
					<odin:gridDataCol name="a0602" />
					<odin:gridDataCol name="a0604" />
					<odin:gridDataCol name="a0607" />
					<odin:gridDataCol name="a0611" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>

					<odin:gridEditColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
					<odin:gridEditColumn header="רҵ�����ʸ����" dataIndex="a0601" codeType="GB8561" editor="select" width="15"/>
					<odin:gridEditColumn header="רҵ�����ʸ�" dataIndex="a0602" editor="text" width="15"/>
					<odin:gridEditColumn header="����ʸ�����" dataIndex="a0604" editor="text" width="10"/>
					<odin:gridEditColumn header="��ȡ�ʸ�;��" dataIndex="a0607" codeType="ZB24" editor="select" width="15"/>
					<odin:gridEditColumn header="��ί���������" dataIndex="a0611" editor="text" isLast="true" width="15"/>				

				</odin:gridColumnModel>
			</odin:editgrid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>-->
<%-----------------------------ѧλѧ��-------------------------------------------------------%>
<!--<odin:groupBox property="s3" title="ѧλѧ��(a08)">
-->
<odin:toolBar property="tol2" applyTo="toolBar22">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>ѧ��ѧλ<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table  cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar2" >
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="degreesgrid" id="degreesAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="degreesgrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar22"></div>
				
			<table cellspacing="4" width="98%" align="center">
    <tr>
		<odin:textEdit property="qrzxl" label="ȫ���ƽ���"  readonly="true"></odin:textEdit>	
			
		<odin:textEdit property="qrzxlxx" label="ԺУϵ��רҵ" readonly="true" colspan="4" width="770"></odin:textEdit>	
	</tr>
	<tr>
		<odin:textEdit property="zzxl" label="��ְ�ƽ���"  readonly="true"></odin:textEdit>	
			
		<odin:textEdit property="zzxlxx" label="ԺУϵ��רҵ" readonly="true" colspan="4" width="770"></odin:textEdit>	
	</tr>
</table>
			<odin:grid property="degreesgrid" topBarId="toolBar2" sm="row" forceNoScroll="true" remoteSort="true" isFirstLoadData="false" url="/"
			 height="170" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		     		<odin:gridDataCol name="a0899"/>
		     		<odin:gridDataCol name="a0800" />
			  		<odin:gridDataCol name="a0837" />
			  		<odin:gridDataCol name="a0801b" />
			   		<odin:gridDataCol name="a0901b" />
			   		<odin:gridDataCol name="a0814" />
			   		<odin:gridDataCol name="a0827" />			   		
			   		<odin:gridDataCol name="a0811" />
			   		<odin:gridDataCol name="a0804" />
			   		<odin:gridDataCol name="a0807" />
			   		<odin:gridDataCol name="a0904" />
			   		<odin:gridDataCol name="a0801a" />
			   		<odin:gridDataCol name="a0901a" />
			   		<odin:gridDataCol name="a0824" isLast="true"/>
			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridColumn header="���" width="25" editor="checkbox" dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="�������" dataIndex="a0837" codeType="ZB123" editor="select"/>
				  <odin:gridEditColumn  header="ѧ������"  dataIndex="a0801b" codeType="ZB64" editor="select" />
				  <odin:gridEditColumn header="ѧλ����"  dataIndex="a0901b" codeType="GB6864" editor="select"/>
				  <odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" editor="text"/>
				  <odin:gridEditColumn header="רҵ����" dataIndex="a0827" codeType="GB16835" editor="select"/>				  
				  <odin:gridEditColumn header="ѧ������(��)" dataIndex="a0811" editor="text"/>
				  <odin:gridEditColumn header="��ѧʱ��" dataIndex="a0804" editor="text"/>
				  <odin:gridEditColumn header="��ҵʱ��" dataIndex="a0807" editor="text"/>
				  <odin:gridEditColumn header="ѧλ����ʱ��" dataIndex="a0904" editor="text"/>
				  <odin:gridEditColumn header="ѧ��" dataIndex="a0801a" editor="text"/>
				  <odin:gridEditColumn header="ѧλ" dataIndex="a0901a" editor="text"/>
				  <odin:gridEditColumn header="רҵ" dataIndex="a0824" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
	--><%-----------------------------������λ��ְ��-------------------------------------------------------%>
<!--<odin:groupBox property="s4" title="������λ��ְ��A02">
<odin:toolBar property="toolBar3" applyTo="toolDiv2">
	<odin:buttonForToolBar text="��������"  ></odin:buttonForToolBar>
	<odin:buttonForToolBar text="������Ϣ"  ></odin:buttonForToolBar>
	<odin:fill isLast="true"></odin:fill>
</odin:toolBar>
-->

<odin:toolBar property="tol3" applyTo="toolBar33">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>������λ��ְ��<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar4">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="WorkUnitsGrid" id="WorkUnitsAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="WorkUnitsGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar33"></div>
				<table cellspacing="4" width="98%" align="center">
    <tr>
		<odin:textEdit property="a0192b" width="900" label="������λ��ְ����" readonly="true" colspan="8" ><span>&nbsp;&nbsp;(��������)</span></odin:textEdit>
	</tr>
    <tr>
		<odin:textEdit property="a0192" width="900" label="������λ��ְ��ȫ��" readonly="true" colspan="8" ><span>&nbsp;&nbsp;(���������)</span></odin:textEdit>
	    
	</tr>
</table>
			<odin:grid property="WorkUnitsGrid" topBarId="toolBar4"  sm="row" forceNoScroll="false" remoteSort="true" isFirstLoadData="false" url="/"
			 height="170"   >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		     		<odin:gridDataCol name="checkedout2"/>
		     		<odin:gridDataCol name="a0200" />
			  		<odin:gridDataCol name="a0201" />
			  		<odin:gridDataCol name="a0215a" />
			   		<odin:gridDataCol name="a0255" />
			   		<odin:gridDataCol name="name37" />
			   		<odin:gridDataCol name="name38" />			   		
			   		<odin:gridDataCol name="a0299" />
			   		<odin:gridDataCol name="name40_n" />
			   		<odin:gridDataCol name="name40_y" />
			   		<odin:gridDataCol name="a0222" />
			   		<odin:gridDataCol name="a0201d" />
			   		<odin:gridDataCol name="a0201e" />
			   		<odin:gridDataCol name="a0221" />
			   		<odin:gridDataCol name="a0219" />
			   		<odin:gridDataCol name="a0229" />
			   		<odin:gridDataCol name="a0251" />
			   		<odin:gridDataCol name="a0243" />
			   		<odin:gridDataCol name="a0247" />
			   		<odin:gridDataCol name="a0245" />
			   		<odin:gridDataCol name="a0288" />
			   		<odin:gridDataCol name="a0265" />
			   		<odin:gridDataCol name="a0271" />
			   		<odin:gridDataCol name="a0267" />
			   		<odin:gridDataCol name="name58" />
			   		<odin:gridDataCol name="a4904" />
			   		<odin:gridDataCol name="a4901" />
			   		<odin:gridDataCol name="a4907" isLast="true"/>
			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridColumn header="���" width="50" editor="checkbox" dataIndex="checkedout2" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0200" editor="text" width="200" hidden="true"/>
				  <odin:gridEditColumn header="��ְ����" dataIndex="a0201" editor="text" width="200"/>
				  <odin:gridEditColumn header="ְ������"  dataIndex="a0215a" editor="text" width="200"/>
				  <odin:gridEditColumn header="��ְ״̬" dataIndex="a0255" codeType="ZB14" editor="select" width="200"/>
				  <odin:gridEditColumn header="ȫ��"  dataIndex="name37" editor="text" width="200"/>
				  <odin:gridEditColumn header="���" dataIndex="name38" editor="text" width="200"/>
				  <odin:gridEditColumn header="ͳ�ƹ�ϵ���ڵ�λ" dataIndex="a0299"  editor="text" width="200"/>				  
				  <odin:gridEditColumn header="���㹤��������" dataIndex="name40_n" editor="text" width="200"/>
				  <odin:gridEditColumn header="��" dataIndex="name40_y" editor="text" width="200"/>				  
				  <odin:gridEditColumn header="��λ���" dataIndex="a0222" codeType="ZB127" editor="select" width="200"/>
				  <odin:gridEditColumn header="���ӳ�Ա" dataIndex="a0201d" editor="text" width="200"/>
				  <odin:gridEditColumn header="��Ա���" dataIndex="a0201e" codeType="ZB129" editor="select" width="200"/>
				  <odin:gridEditColumn header="ְ����" dataIndex="a0221" editor="text" width="200"/>
				  <odin:gridEditColumn header="ְ�����" dataIndex="a0219" codeType="ZB42" editor="select" width="200"/>
				  <odin:gridEditColumn header="���ܹ���" dataIndex="a0229" editor="text" width="200"/>
				  <odin:gridEditColumn header="ְ������" dataIndex="a0251" codeType="ZB13" editor="select" width="200"/>
				  <odin:gridEditColumn header="��ְʱ��" dataIndex="a0243" editor="text" width="200"/>
				  <odin:gridEditColumn header="ѡ�����÷�ʽ" dataIndex="a0247" codeType="ZB122" editor="select" width="200"/>
				  <odin:gridEditColumn header="��ְ�ĺ�" dataIndex="a0245" editor="text" width="200"/>
				  <odin:gridEditColumn header="����ְ����ʱ��" dataIndex="a0288" editor="text" width="200"/>
				  <odin:gridEditColumn header="��ְʱ��" dataIndex="a0265" editor="text" width="200"/>
				  <odin:gridEditColumn header="��ְ����" dataIndex="a0271" codeType="ZB16" editor="select" width="200"/>
				  <odin:gridEditColumn header="��ְ�ĺ�" dataIndex="a0267" editor="text" width="200"/>
				  <odin:gridEditColumn header="�Ƿ���" dataIndex="name58" editor="text" width="200"/>
				  <odin:gridEditColumn header="����ԭ��" dataIndex="a4904" codeType="ZB73" editor="select" width="200"/>
				  <odin:gridEditColumn header="������ʽ" dataIndex="a4901" codeType="ZB72" editor="select" width="200"/>
				  <odin:gridEditColumn header="����ȥ��" dataIndex="a4907" editor="select"  codeType="ZB74" isLast="true" width="200"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<table align="center" width="98%">
<tr>
<odin:textarea property="a1701" cols="207" rows="4" colspan="4" label="����" ></odin:textarea>
</tr>
</table>
<br>
<!--</odin:groupBox>
	--><%-----------------------------�������-------------------------------------------------------%>
<!--<odin:groupBox property="s6" title="�������A14">
-->
<odin:toolBar property="tol4" applyTo="toolBar44">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>�������<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>

<br>
<table cellspacing="2" width="98%" align="center">
	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar5">
				<odin:buttonForToolBar text="׷�ӵ�ǰ��" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ȫ���滻" ></odin:buttonForToolBar>
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="RewardPunishGrid" id="RewardPunishAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="RewardPunishGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar44"></div>
				<table align="center" width="98%">
<tr>
<td><odin:textEdit property="a14z101" label="�������" colspan="8" width="950"></odin:textEdit></td>
<td><odin:button text="����" property="1"></odin:button></td>
</tr>
</table>
			<odin:grid property="RewardPunishGrid" topBarId="toolBar5" sm="row" forceNoScroll="true" remoteSort="true" isFirstLoadData="false" url="/"
			 height="170">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1400" />
			  		<odin:gridDataCol name="a1404b" />
			  		<odin:gridDataCol name="a1404a" />
			   		<odin:gridDataCol name="a1415" />
			   		<odin:gridDataCol name="a1414" />
			   		<odin:gridDataCol name="a1428" />			   		
			   		<odin:gridDataCol name="a1411a" />
			   		<odin:gridDataCol name="a1407" />
			   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn  header="id"  dataIndex="a1400" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="�������ƴ���" dataIndex="a1404b" codeType="GB12404" editor="select"/>
				  <odin:gridEditColumn  header="��������"  dataIndex="a1404a" editor="text" />
				  <odin:gridEditColumn header="�ܽ���ʱְ����"  dataIndex="a1415" editor="text"/>
				  <odin:gridEditColumn header="��׼���ؼ���" dataIndex="a1414" editor="text"/>
				  <odin:gridEditColumn header="��׼��������" dataIndex="a1428"  editor="text"/>				  
				  <odin:gridEditColumn header="��׼����" dataIndex="a1411a" editor="text"/>
				  <odin:gridEditColumn header="��׼ʱ��" dataIndex="a1407" editor="text"/>
				  <odin:gridEditColumn header="����ʱ��" dataIndex="a1424" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
--><%----------------------------- ��ȿ������-------------------------------------------------------%>
<!--<odin:groupBox property="s7" title="��ȿ������A15">
-->

<odin:toolBar property="tol5" applyTo="toolBar55">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>��ȿ������<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center">	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar6">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="AssessmentInfoGrid" id="AssessmentInfoAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="AssessmentInfoGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar55"></div>
				<table align="center" width="98%">
<tr>
<td><odin:textEdit property="a15z101" label="��ȿ���" colspan="8" width="950"></odin:textEdit></td>
<td><odin:button text="����" property="2"></odin:button></td>
</tr>
</table>
			<odin:grid property="AssessmentInfoGrid" topBarId="toolBar6" sm="row" remoteSort="true" forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="170" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1500" />
			  		<odin:gridDataCol name="a1521" />
			   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="���" dataIndex="a1521" editor="text"/>
				  <odin:gridEditColumn  header="���˽��"  dataIndex="a1517" editor="select" codeType="ZB18" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
		--><%-----------------------------��ͥ��Ҫ��Ա����Ҫ����ϵ-------------------------------------------------------%>
<!--<odin:groupBox property="s8" title="��ͥ��Ҫ��Ա����Ҫ����ϵA36">
--><table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar7">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="FamilyRelationsGrid" id="FamilyRelationsAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="jtcyjshgx" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="FamilyRelationsGrid" topBarId="toolBar7" sm="row" remoteSort="true" forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="170" title="��ͥ��Ҫ��Ա����Ҫ����ϵ">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a3600" />
			  		<odin:gridDataCol name="a3604a" />
			  		<odin:gridDataCol name="a3601" />
			  		<odin:gridDataCol name="a3607" />
			  		<odin:gridDataCol name="a3627" />
			   		<odin:gridDataCol name="a3611" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a3600" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="��ν" dataIndex="a3604a" codeType="GB4761" editor="select"/>
				  <odin:gridEditColumn header="����" dataIndex="a3601" editor="text"/>
				  <odin:gridEditColumn header="��������" dataIndex="a3607" editor="text"/>
				  <odin:gridEditColumn header="������ò" dataIndex="a3627" codeType="GB4762" editor="select"/>
				  <odin:gridEditColumn header="������λ��ְ��" dataIndex="a3611" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
--><%-----------------------------��ѵ��Ϣ-------------------------------------------------------%>
<!--<odin:groupBox property="s12" title="��ѵ��ϢA11">
--><table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar8">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" cls="TrainingInfoGrid" id="TrainingInfoAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" cls="TrainingInfoGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row" remoteSort="true" isFirstLoadData="false" url="/"
			 height="170" title="��ѵ��Ϣ">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1100" />
			  		<odin:gridDataCol name="a1101" />
			  		<odin:gridDataCol name="a1131" />
			  		<odin:gridDataCol name="a1107" />
			  		<odin:gridDataCol name="a1111" />
			  		<odin:gridDataCol name="a1107a" />
			  		<odin:gridDataCol name="a1107b" />
			  		<odin:gridDataCol name="a1114" />
			  		<odin:gridDataCol name="a1121a" />
			  		<odin:gridDataCol name="a1127" />
			  		<odin:gridDataCol name="a1104" />			
			   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text" width="100" hidden="true"/>
				  <odin:gridEditColumn header="��ѵ���" dataIndex="a1101" editor="select" codeType="ZB29" width="100"/>
				  <odin:gridEditColumn header="��ѵ������" dataIndex="a1131" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵʱ��" dataIndex="a1107" editor="text" width="100"/>
				  <odin:gridEditColumn header="��" dataIndex="a1111" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵʱ�����£�" dataIndex="a1107a" editor="text" width="100"/>
				  <odin:gridEditColumn header="��" dataIndex="a1107b" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵ���쵥λ" dataIndex="a1114" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵ��������" dataIndex="a1121a" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵ�������" dataIndex="a1127" editor="select" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn header="��ѵ���״̬" dataIndex="a1104" editor="select" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn header="����(��)��ʶ" dataIndex="a1151" editor="select" selectData="['1','��'],['0','��']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
<!--</odin:groupBox>
--></odin:tabCont>
<%-----------------------------������Ϣ----<Div id="floatToolDiv2" ></Div>---------------------------------------------------%>
<odin:tabCont itemIndex="tab2" >

<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="����" id="saveOthers" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<Div id="floatToolDiv2" ></Div>
<%-----------------------------�������-------------------------------------------------------%>
<odin:groupBox property="s10" title="�������">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a2921a" label="ԭ��λ����" ></odin:textEdit>
		<odin:textEdit property="a2941" label="ԭ��λְ��" ></odin:textEdit>
		<odin:textEdit property="a2944" label="ԭ��λ����" ></odin:textEdit>	
	</tr>
	<tr>
		<odin:dateEdit property="a2907" label="���뱾��λ����" format="Ymd"></odin:dateEdit>
		<odin:select property="a2911" label="���뱾��λ��ʽ" codeType="ZB77"></odin:select>		
		<odin:dateEdit property="a2949" label="����Ա�Ǽ�ʱ��" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a2947" label="���빫��Ա����ʱ��" format="Ymd"></odin:dateEdit>		
	</tr>
</table>
</odin:groupBox>
<%-----------------------------������-------------------------------------------------------%>
<odin:groupBox property="s11" title="������">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a5304" label="����ְ��" ></odin:textEdit>
		<odin:textEdit property="a5315" label="����ְ��" ></odin:textEdit>
		<odin:textEdit property="a5317" label="��������" ></odin:textEdit>
		<odin:textEdit property="a5319" label="�ʱ���λ" ></odin:textEdit>
	</tr>
	<tr>
		
		<odin:dateEdit property="a5321" label="��������ʱ��" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a5323" label="���ʱ��" format="Ymd"></odin:dateEdit>
		<odin:textEdit property="a5327" label="�����" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<%-----------------------------סַͨѶA37-------------------------------------------------------%>
<odin:groupBox property="s12" title="סַͨѶ">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a3701" label="�칫��ַ" colspan="4" width="400"></odin:textEdit>
		<odin:textEdit property="a3708" label="��������" colspan="4" width="400"></odin:textEdit>		
	</tr>
	<tr>
		<odin:textEdit property="a3711" label="��ͥ��ַ" colspan="4" width="400"></odin:textEdit>
		<odin:textEdit property="a3714" label="סַ�ʱ�" colspan="4" width="400"></odin:textEdit>		
	</tr>
	<tr>
		<odin:textEdit property="a3707a" label="�칫�绰" ></odin:textEdit>
		<odin:textEdit property="a3707c" label="�ƶ��绰" ></odin:textEdit>
		<odin:textEdit property="a3707b" label="סլ�绰" ></odin:textEdit>
		<odin:textEdit property="a3707e" label="����绰" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<%-----------------------------����-------------------------------------------------------%>
<odin:groupBox property="s13" title="����">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3101" label="�������" codeType="ZB132"></odin:select>
		<odin:dateEdit property="a3104" label="������׼����" format="Ymd"></odin:dateEdit>
		<odin:textEdit property="a3137" label="������׼�ĺ�" ></odin:textEdit>		
	</tr>
	<tr>		
		<odin:select property="a3107" label="����ǰ����" codeType="ZB09"></odin:select>	
		<odin:textEdit property="a3118" label="�������ְ��" ></odin:textEdit>	
		<odin:textEdit property="a3117a" label="���˺����λ" ></odin:textEdit>		
	</tr>
</table>
</odin:groupBox>
<%-----------------------------�˳�����-------------------------------------------------------%>
<odin:groupBox property="s14" title="�˳�����">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3001" label="�˳�����ʽ" codeType="ZB78"></odin:select>
		<odin:textEdit property="a3007a" label="������λ" ></odin:textEdit>
		<odin:dateEdit property="a3004" label="����" format="Ymd"></odin:dateEdit>	
		<odin:textEdit property="a3034" label="��ע" ></odin:textEdit>		
	</tr>
</table>
</odin:groupBox>
</odin:tabCont>
</odin:tab>

<%-----------------------------������Ϣ------------<odin:window src="/blank.htm"  id="professSkillAddPage" width="600" height="380" title="רҵ����ְ��" />-------------------------------------------%>









<script type="text/javascript">
//������ҳ��λ�ö�λ
function scroll(obj){
	document.body.scrollTop=document.getElementById(obj.initialConfig.cls).offsetTop;
}


Ext.onReady(function(){
	//�������ڵ�����ԭ���ı�ǩ���Բ����á�
	newWin({id:'professSkillAddPage',title:'רҵ����ְ��'});
	newWin({id:'DegreesAddPage',title:'ѧλѧ��',width:800});
	newWin({id:'WorkUnitsAddPage',title:'������λ��ְ��',width:1000,height:460});
	newWin({id:'RewardPunishAddPage',title:'�������',width:800});
	newWin({id:'AssessmentInfoAddPage',title:'��ȿ������'});
	newWin({id:'FamilyRelationsAddPage',title:'��ͥ��Ҫ��Ա����Ҫ����ϵ'});
	newWin({id:'TrainingInfoAddPage',title:'��ѵ��Ϣ',width:800});
	
	//ҳ�������tabҳ�����Ⱦ������Ҫ�����������������ҹ�����
	var divobj = document.getElementById('tab1');
	divobj.style.width='100%';
	divobj.childNodes.item(0).childNodes.item(0).style.width='100%';
});

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		//ҳ��������������ҹ��������⡣�ڶ���tabҳ��ʱ ��ȱȵ�һ���󣬲�֪��Ϊʲô��
		var divobj = document.getElementById('tab2');
		document.getElementById('tab2').style.width=document.getElementById('tab1').style.width;
		divobj.childNodes.item(0).childNodes.item(0).style.width=document.getElementById('tab1').style.width;
	}
}
//ҳ���������д�� ����������λ�ã�  ԭ����tabҳ�������,�м�϶�� 
function eventhing(){ 
var offset=0;
if(odin.ScrollTop()>30){
	offset=0;
}else{
	offset=30-odin.ScrollTop();
}
document.getElementById("bottombar").style.top = (odin.ScrollTop()+offset - document.getElementById("bottombar").offsetHeight) + "px"; 
document.getElementById("bottombar").style.left = "0px"; 
} 
</script>