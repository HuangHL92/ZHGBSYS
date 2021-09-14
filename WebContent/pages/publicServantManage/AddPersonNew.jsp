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
/**删除选中行  单条**/
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
/**添加行**/
function addRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount();
	radow.addGridEmptyRow(obj.initialConfig.cls,i);
}
/**删除选中行  多条**/
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
		<odin:tabItem title="基本信息" id="tab1"></odin:tabItem>
		<odin:tabItem title="其它信息" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
<odin:tabCont itemIndex="tab1">
<odin:floatDiv property="floatToolDiv" position="up" ></odin:floatDiv>
<br/><br/><br/>
<odin:toolBar property="floatToolBar" applyTo="floatToolDiv">
	<odin:buttonForToolBar text="↑" handler="scroll" cls="tab" /><odin:separator/>
	<odin:buttonForToolBar text="人员基本信息" handler="scroll" cls="s1" /><odin:separator/>
	<odin:buttonForToolBar text="专业技术职务" handler="scroll" cls="s2" /><odin:separator/>
	<odin:buttonForToolBar text="学位学历" handler="scroll" cls="s3" /><odin:separator/>
	<odin:buttonForToolBar text="工作单位及职务" handler="scroll" cls="s4" /><odin:separator/>
	<odin:buttonForToolBar text="奖惩情况" handler="scroll" cls="s6" /><odin:separator/>
	<odin:buttonForToolBar text="年度考核情况" handler="scroll" cls="s7" /><odin:separator/>
	<odin:buttonForToolBar text="家庭主要成员及重要社会关系" handler="scroll" cls="s8" /><odin:separator/>
	<odin:buttonForToolBar text="培训信息" handler="scroll" cls="s12" />
	<odin:fill />
	<odin:buttonForToolBar text="保存" id="save" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<%-----------------------------人员基本信息-------------------<odin:hidden property="a0000"/>------------------------------------%>
<!--<odin:groupBox property="s1" title="人员基本信息">
--><table cellspacing="4" width="100%" align="center" border="0px" >
	<tr>		
		<odin:textEdit property="a0101" label="姓名" required="true"></odin:textEdit>
		<odin:select property="a0104" label="性别" codeType="GB2261" ></odin:select>
		<odin:dateEdit property="a0107" label="出生日期" format="Ymd" ></odin:dateEdit>
		<td rowspan="4">
		<div id="photo" style="overflow: auto; height: 140px; width: 120px; border: 2px solid #c3daf9;">
		<label style="font-size: 24" id="photo">照片</label>
		</div>
		</td>
		
		<!--<odin:select property="a0165" label="管理类别" codeType="ZB130" ></odin:select>	
		<odin:select property="a0141" label="第一党派" codeType="GB4762" ></odin:select>-->
	</tr>
	<tr>
	    <odin:select property="a0117" label="民族" codeType="GB3304"></odin:select>
	    <odin:textEdit property="a0111" label="籍贯" ></odin:textEdit>
		<!--<odin:textEdit property="a0184" label="身份证号" required="true"></odin:textEdit>	-->
		<odin:textEdit property="a0114" label="出生地" ></odin:textEdit>
		<!--<odin:select property="a0160" label="人员类别" codeType="ZB125"></odin:select>
		<odin:dateEdit property="a0144" label="入党时间" format="Ymd"></odin:dateEdit>-->
	</tr>
	<tr>
		<odin:select property="a0141" label="第一党派" codeType="GB4762" ></odin:select>		
		<odin:dateEdit property="a0144" label="入党时间" format="Ymd"></odin:dateEdit>
		<!--<odin:textEdit property="a0128" label="健康状况" value="健康"></odin:textEdit>-->
		<odin:select property="a3921" label="第二党派" codeType="GB4762"></odin:select>		
	</tr>
	<tr>
	    <odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
	    <odin:select property="a3927" label="第三党派" codeType="GB4762"></odin:select>
		<odin:dateEdit property="a0134" label="参加工作时间" format="Ymd"></odin:dateEdit>	
		<odin:textEdit property="a0187a" label="熟悉专业有何专长"></odin:textEdit>
		<!--<odin:textarea property="a0187a" cols="70" rows="4" colspan="4" label="熟悉专业有何专长" ></odin:textarea>-->	
	</tr>
	<tr>		
		<odin:textEdit property="a0128" label="健康状况" value="健康"></odin:textEdit>
		<odin:select property="a0160" label="人员类别" codeType="ZB125"></odin:select>
		<odin:textEdit property="a0184" label="身份证号" required="true"></odin:textEdit>
		<odin:textEdit property="a0194" label="基层工作经历年限" size="6"></odin:textEdit>	
		<!--<odin:textarea property="a1701" cols="70" rows="4" colspan="4" label="简历" ></odin:textarea>-->	
	</tr>
	</table>
<!--</odin:groupBox>
--><%-----------------------------专业技术职务-------------------------------------------------------%>
<!--<odin:groupBox property="s2" title="专业技术职务">-->

<odin:toolBar property="tol1" applyTo="toolBar11">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>专业技术职务<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center" >
	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" id="professSkillAddBtn" cls="professSkillgrid" handler="addRow"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="professSkillgrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar11"></div>
				<table align="center" width="98%">
    <tr>	
		<odin:textEdit property="a0196" label="专业技术职务" readonly="true" colspan="8" width="1010"></odin:textEdit>	
			
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
					<odin:gridEditColumn header="专业技术资格代码" dataIndex="a0601" codeType="GB8561" editor="select" width="15"/>
					<odin:gridEditColumn header="专业技术资格" dataIndex="a0602" editor="text" width="15"/>
					<odin:gridEditColumn header="获得资格日期" dataIndex="a0604" editor="text" width="10"/>
					<odin:gridEditColumn header="获取资格途径" dataIndex="a0607" codeType="ZB24" editor="select" width="15"/>
					<odin:gridEditColumn header="评委会或考试名称" dataIndex="a0611" editor="text" isLast="true" width="15"/>				

				</odin:gridColumnModel>
			</odin:editgrid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>-->
<%-----------------------------学位学历-------------------------------------------------------%>
<!--<odin:groupBox property="s3" title="学位学历(a08)">
-->
<odin:toolBar property="tol2" applyTo="toolBar22">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>学历学位<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table  cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar2" >
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="degreesgrid" id="degreesAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="degreesgrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar22"></div>
				
			<table cellspacing="4" width="98%" align="center">
    <tr>
		<odin:textEdit property="qrzxl" label="全日制教育"  readonly="true"></odin:textEdit>	
			
		<odin:textEdit property="qrzxlxx" label="院校系及专业" readonly="true" colspan="4" width="770"></odin:textEdit>	
	</tr>
	<tr>
		<odin:textEdit property="zzxl" label="在职制教育"  readonly="true"></odin:textEdit>	
			
		<odin:textEdit property="zzxlxx" label="院校系及专业" readonly="true" colspan="4" width="770"></odin:textEdit>	
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
				  <odin:gridColumn header="输出" width="25" editor="checkbox" dataIndex="a0899" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="教育类别" dataIndex="a0837" codeType="ZB123" editor="select"/>
				  <odin:gridEditColumn  header="学历代码"  dataIndex="a0801b" codeType="ZB64" editor="select" />
				  <odin:gridEditColumn header="学位代码"  dataIndex="a0901b" codeType="GB6864" editor="select"/>
				  <odin:gridEditColumn header="学校及院系" dataIndex="a0814" editor="text"/>
				  <odin:gridEditColumn header="专业代码" dataIndex="a0827" codeType="GB16835" editor="select"/>				  
				  <odin:gridEditColumn header="学制年限(年)" dataIndex="a0811" editor="text"/>
				  <odin:gridEditColumn header="入学时间" dataIndex="a0804" editor="text"/>
				  <odin:gridEditColumn header="毕业时间" dataIndex="a0807" editor="text"/>
				  <odin:gridEditColumn header="学位授予时间" dataIndex="a0904" editor="text"/>
				  <odin:gridEditColumn header="学历" dataIndex="a0801a" editor="text"/>
				  <odin:gridEditColumn header="学位" dataIndex="a0901a" editor="text"/>
				  <odin:gridEditColumn header="专业" dataIndex="a0824" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
	--><%-----------------------------工作单位及职务-------------------------------------------------------%>
<!--<odin:groupBox property="s4" title="工作单位及职务A02">
<odin:toolBar property="toolBar3" applyTo="toolDiv2">
	<odin:buttonForToolBar text="更新名称"  ></odin:buttonForToolBar>
	<odin:buttonForToolBar text="补充信息"  ></odin:buttonForToolBar>
	<odin:fill isLast="true"></odin:fill>
</odin:toolBar>
-->

<odin:toolBar property="tol3" applyTo="toolBar33">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>工作单位及职务<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar4">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="WorkUnitsGrid" id="WorkUnitsAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="WorkUnitsGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar33"></div>
				<table cellspacing="4" width="98%" align="center">
    <tr>
		<odin:textEdit property="a0192b" width="900" label="工作单位及职务简称" readonly="true" colspan="8" ><span>&nbsp;&nbsp;(用于名册)</span></odin:textEdit>
	</tr>
    <tr>
		<odin:textEdit property="a0192" width="900" label="工作单位及职务全称" readonly="true" colspan="8" ><span>&nbsp;&nbsp;(用于任免表)</span></odin:textEdit>
	    
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
				  <odin:gridColumn header="输出" width="50" editor="checkbox" dataIndex="checkedout2" edited="true"/>
				  <odin:gridEditColumn header="id" dataIndex="a0200" editor="text" width="200" hidden="true"/>
				  <odin:gridEditColumn header="任职机构" dataIndex="a0201" editor="text" width="200"/>
				  <odin:gridEditColumn header="职务名称"  dataIndex="a0215a" editor="text" width="200"/>
				  <odin:gridEditColumn header="任职状态" dataIndex="a0255" codeType="ZB14" editor="select" width="200"/>
				  <odin:gridEditColumn header="全称"  dataIndex="name37" editor="text" width="200"/>
				  <odin:gridEditColumn header="简称" dataIndex="name38" editor="text" width="200"/>
				  <odin:gridEditColumn header="统计关系所在单位" dataIndex="a0299"  editor="text" width="200"/>				  
				  <odin:gridEditColumn header="基层工作经历年" dataIndex="name40_n" editor="text" width="200"/>
				  <odin:gridEditColumn header="月" dataIndex="name40_y" editor="text" width="200"/>				  
				  <odin:gridEditColumn header="岗位类别" dataIndex="a0222" codeType="ZB127" editor="select" width="200"/>
				  <odin:gridEditColumn header="班子成员" dataIndex="a0201d" editor="text" width="200"/>
				  <odin:gridEditColumn header="成员类别" dataIndex="a0201e" codeType="ZB129" editor="select" width="200"/>
				  <odin:gridEditColumn header="职务层次" dataIndex="a0221" editor="text" width="200"/>
				  <odin:gridEditColumn header="职务类别" dataIndex="a0219" codeType="ZB42" editor="select" width="200"/>
				  <odin:gridEditColumn header="主管工作" dataIndex="a0229" editor="text" width="200"/>
				  <odin:gridEditColumn header="职动类型" dataIndex="a0251" codeType="ZB13" editor="select" width="200"/>
				  <odin:gridEditColumn header="任职时间" dataIndex="a0243" editor="text" width="200"/>
				  <odin:gridEditColumn header="选拔任用方式" dataIndex="a0247" codeType="ZB122" editor="select" width="200"/>
				  <odin:gridEditColumn header="任职文号" dataIndex="a0245" editor="text" width="200"/>
				  <odin:gridEditColumn header="现任职务层次时间" dataIndex="a0288" editor="text" width="200"/>
				  <odin:gridEditColumn header="免职时间" dataIndex="a0265" editor="text" width="200"/>
				  <odin:gridEditColumn header="免职类型" dataIndex="a0271" codeType="ZB16" editor="select" width="200"/>
				  <odin:gridEditColumn header="免职文号" dataIndex="a0267" editor="text" width="200"/>
				  <odin:gridEditColumn header="是否交流" dataIndex="name58" editor="text" width="200"/>
				  <odin:gridEditColumn header="交流原因" dataIndex="a4904" codeType="ZB73" editor="select" width="200"/>
				  <odin:gridEditColumn header="交流方式" dataIndex="a4901" codeType="ZB72" editor="select" width="200"/>
				  <odin:gridEditColumn header="交流去向" dataIndex="a4907" editor="select"  codeType="ZB74" isLast="true" width="200"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<table align="center" width="98%">
<tr>
<odin:textarea property="a1701" cols="207" rows="4" colspan="4" label="简历" ></odin:textarea>
</tr>
</table>
<br>
<!--</odin:groupBox>
	--><%-----------------------------奖惩情况-------------------------------------------------------%>
<!--<odin:groupBox property="s6" title="奖惩情况A14">
-->
<odin:toolBar property="tol4" applyTo="toolBar44">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>奖惩情况<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>

<br>
<table cellspacing="2" width="98%" align="center">
	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar5">
				<odin:buttonForToolBar text="追加当前条" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="全部替换" ></odin:buttonForToolBar>
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="RewardPunishGrid" id="RewardPunishAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="RewardPunishGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar44"></div>
				<table align="center" width="98%">
<tr>
<td><odin:textEdit property="a14z101" label="奖惩情况" colspan="8" width="950"></odin:textEdit></td>
<td><odin:button text="更新" property="1"></odin:button></td>
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
				  <odin:gridEditColumn header="奖惩名称代码" dataIndex="a1404b" codeType="GB12404" editor="select"/>
				  <odin:gridEditColumn  header="奖惩名称"  dataIndex="a1404a" editor="text" />
				  <odin:gridEditColumn header="受奖惩时职务层次"  dataIndex="a1415" editor="text"/>
				  <odin:gridEditColumn header="批准机关级别" dataIndex="a1414" editor="text"/>
				  <odin:gridEditColumn header="批准机关性质" dataIndex="a1428"  editor="text"/>				  
				  <odin:gridEditColumn header="批准机关" dataIndex="a1411a" editor="text"/>
				  <odin:gridEditColumn header="批准时间" dataIndex="a1407" editor="text"/>
				  <odin:gridEditColumn header="撤销时间" dataIndex="a1424" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
--><%----------------------------- 年度考核情况-------------------------------------------------------%>
<!--<odin:groupBox property="s7" title="年度考核情况A15">
-->

<odin:toolBar property="tol5" applyTo="toolBar55">
				<odin:textForToolBar text="<span style=&quot;background-image: url(&quot;&quot;);&quot; class=&quot;x-panel-header x-unselectable x-panel-header-text&quot;>年度考核情况<span>"></odin:textForToolBar>
				<odin:fill isLast="true"></odin:fill >
			</odin:toolBar>
<table cellspacing="2" width="98%" align="center">	
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar6">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="AssessmentInfoGrid" id="AssessmentInfoAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="AssessmentInfoGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<div style="border: 1px solid #99bbe8;">
				<div id="toolBar55"></div>
				<table align="center" width="98%">
<tr>
<td><odin:textEdit property="a15z101" label="年度考核" colspan="8" width="950"></odin:textEdit></td>
<td><odin:button text="更新" property="2"></odin:button></td>
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
				  <odin:gridEditColumn header="年度" dataIndex="a1521" editor="text"/>
				  <odin:gridEditColumn  header="考核结果"  dataIndex="a1517" editor="select" codeType="ZB18" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
			</div>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
		--><%-----------------------------家庭主要成员及重要社会关系-------------------------------------------------------%>
<!--<odin:groupBox property="s8" title="家庭主要成员及重要社会关系A36">
--><table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar7">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="FamilyRelationsGrid" id="FamilyRelationsAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="jtcyjshgx" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="FamilyRelationsGrid" topBarId="toolBar7" sm="row" remoteSort="true" forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="170" title="家庭主要成员及重要社会关系">
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
				  <odin:gridEditColumn header="称谓" dataIndex="a3604a" codeType="GB4761" editor="select"/>
				  <odin:gridEditColumn header="姓名" dataIndex="a3601" editor="text"/>
				  <odin:gridEditColumn header="出身日期" dataIndex="a3607" editor="text"/>
				  <odin:gridEditColumn header="政治面貌" dataIndex="a3627" codeType="GB4762" editor="select"/>
				  <odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
<br>
<!--</odin:groupBox>
--><%-----------------------------培训信息-------------------------------------------------------%>
<!--<odin:groupBox property="s12" title="培训信息A11">
--><table cellspacing="2" width="98%" align="center">
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar8">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" cls="TrainingInfoGrid" id="TrainingInfoAddBtn" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" cls="TrainingInfoGrid" handler="delRow" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row" remoteSort="true" isFirstLoadData="false" url="/"
			 height="170" title="培训信息">
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
				  <odin:gridEditColumn header="培训类别" dataIndex="a1101" editor="select" codeType="ZB29" width="100"/>
				  <odin:gridEditColumn header="培训班名称" dataIndex="a1131" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训时间" dataIndex="a1107" editor="text" width="100"/>
				  <odin:gridEditColumn header="至" dataIndex="a1111" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训时长（月）" dataIndex="a1107a" editor="text" width="100"/>
				  <odin:gridEditColumn header="天" dataIndex="a1107b" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训主办单位" dataIndex="a1114" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训机构名称" dataIndex="a1121a" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训机构类别" dataIndex="a1127" editor="select" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn header="培训离岗状态" dataIndex="a1104" editor="select" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn header="出国(境)标识" dataIndex="a1151" editor="select" selectData="['1','是'],['0','否']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
<!--</odin:groupBox>
--></odin:tabCont>
<%-----------------------------其它信息----<Div id="floatToolDiv2" ></Div>---------------------------------------------------%>
<odin:tabCont itemIndex="tab2" >

<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
	<odin:textForToolBar text="" />
	<odin:fill />
	<odin:buttonForToolBar text="保存" id="saveOthers" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<Div id="floatToolDiv2" ></Div>
<%-----------------------------进入管理-------------------------------------------------------%>
<odin:groupBox property="s10" title="进入管理">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a2921a" label="原单位名称" ></odin:textEdit>
		<odin:textEdit property="a2941" label="原单位职务" ></odin:textEdit>
		<odin:textEdit property="a2944" label="原单位级别" ></odin:textEdit>	
	</tr>
	<tr>
		<odin:dateEdit property="a2907" label="进入本单位日期" format="Ymd"></odin:dateEdit>
		<odin:select property="a2911" label="进入本单位方式" codeType="ZB77"></odin:select>		
		<odin:dateEdit property="a2949" label="公务员登记时间" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a2947" label="进入公务员队伍时间" format="Ymd"></odin:dateEdit>		
	</tr>
</table>
</odin:groupBox>
<%-----------------------------拟任免-------------------------------------------------------%>
<odin:groupBox property="s11" title="拟任免">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a5304" label="拟任职务" ></odin:textEdit>
		<odin:textEdit property="a5315" label="拟免职务" ></odin:textEdit>
		<odin:textEdit property="a5317" label="任免理由" ></odin:textEdit>
		<odin:textEdit property="a5319" label="呈报单位" ></odin:textEdit>
	</tr>
	<tr>
		
		<odin:dateEdit property="a5321" label="计算年龄时间" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a5323" label="填表时间" format="Ymd"></odin:dateEdit>
		<odin:textEdit property="a5327" label="填表人" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<%-----------------------------住址通讯A37-------------------------------------------------------%>
<odin:groupBox property="s12" title="住址通讯">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a3701" label="办公地址" colspan="4" width="400"></odin:textEdit>
		<odin:textEdit property="a3708" label="电子邮箱" colspan="4" width="400"></odin:textEdit>		
	</tr>
	<tr>
		<odin:textEdit property="a3711" label="家庭地址" colspan="4" width="400"></odin:textEdit>
		<odin:textEdit property="a3714" label="住址邮编" colspan="4" width="400"></odin:textEdit>		
	</tr>
	<tr>
		<odin:textEdit property="a3707a" label="办公电话" ></odin:textEdit>
		<odin:textEdit property="a3707c" label="移动电话" ></odin:textEdit>
		<odin:textEdit property="a3707b" label="住宅电话" ></odin:textEdit>
		<odin:textEdit property="a3707e" label="秘书电话" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<%-----------------------------离退-------------------------------------------------------%>
<odin:groupBox property="s13" title="离退">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3101" label="离退类别" codeType="ZB132"></odin:select>
		<odin:dateEdit property="a3104" label="离退批准日期" format="Ymd"></odin:dateEdit>
		<odin:textEdit property="a3137" label="离退批准文号" ></odin:textEdit>		
	</tr>
	<tr>		
		<odin:select property="a3107" label="离退前级别" codeType="ZB09"></odin:select>	
		<odin:textEdit property="a3118" label="曾任最高职务" ></odin:textEdit>	
		<odin:textEdit property="a3117a" label="离退后管理单位" ></odin:textEdit>		
	</tr>
</table>
</odin:groupBox>
<%-----------------------------退出管理-------------------------------------------------------%>
<odin:groupBox property="s14" title="退出管理">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3001" label="退出管理方式" codeType="ZB78"></odin:select>
		<odin:textEdit property="a3007a" label="调往单位" ></odin:textEdit>
		<odin:dateEdit property="a3004" label="日期" format="Ymd"></odin:dateEdit>	
		<odin:textEdit property="a3034" label="备注" ></odin:textEdit>		
	</tr>
</table>
</odin:groupBox>
</odin:tabCont>
</odin:tab>

<%-----------------------------其它信息------------<odin:window src="/blank.htm"  id="professSkillAddPage" width="600" height="380" title="专业技术职务" />-------------------------------------------%>









<script type="text/javascript">
//悬浮条页面位置定位
function scroll(obj){
	document.body.scrollTop=document.getElementById(obj.initialConfig.cls).offsetTop;
}


Ext.onReady(function(){
	//弹出窗口调整：原来的标签属性不够用。
	newWin({id:'professSkillAddPage',title:'专业技术职务'});
	newWin({id:'DegreesAddPage',title:'学位学历',width:800});
	newWin({id:'WorkUnitsAddPage',title:'工作单位及职务',width:1000,height:460});
	newWin({id:'RewardPunishAddPage',title:'奖惩情况',width:800});
	newWin({id:'AssessmentInfoAddPage',title:'年度考核情况'});
	newWin({id:'FamilyRelationsAddPage',title:'家庭主要成员及重要社会关系'});
	newWin({id:'TrainingInfoAddPage',title:'培训信息',width:800});
	
	//页面调整：tab页宽度渲染不是想要的那样，出现了左右滚动条
	var divobj = document.getElementById('tab1');
	divobj.style.width='100%';
	divobj.childNodes.item(0).childNodes.item(0).style.width='100%';
});

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		//页面调整：还是左右滚动条问题。第二个tab页打开时 宽度比第一个大，不知道为什么。
		var divobj = document.getElementById('tab2');
		document.getElementById('tab2').style.width=document.getElementById('tab1').style.width;
		divobj.childNodes.item(0).childNodes.item(0).style.width=document.getElementById('tab1').style.width;
	}
}
//页面调整：重写， 调整悬浮条位置，  原在有tab页的情况看,有间隙。 
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