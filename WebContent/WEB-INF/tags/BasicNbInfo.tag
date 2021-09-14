<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="工伤基本信息查询" description="工伤基本信息查询" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="onPersonSelected" description="当人员被选择时调用" required="true"%>
<%@ attribute name="onPersonCleared" description="当人员被清除时调用" required="false"%>

<script language="JavaScript">
	var personData;
	function personSelected(psdata){
		personData=psdata;
		odin.Ajax.request(contextPath+"/com/insigma/siis/local/module/common/search/RetrieveMedInfoAction.do?method=retrieveMedInfo",
							{'aac001':psdata.aac001,'cnbflag':'2'},retrieveMedInfoSuccess,null);		
	}
	function retrieveMedInfoSuccess(response){			
		<%=onPersonSelected%>(personData,response.data);
		if(response.data.akc021_key=='31'||response.data.akc021_key=='32'||response.data.akc021_key=='34'||response.data.akc021_key=='35'||response.data.akc021_key=='36'){		
			var panel = Ext.getCmp('medInfoPanel');	
			medInfoTpl1.overwrite(panel.body,response.data);
			setTimeout(function(){Ext.getCmp('medInfoPanel').expand(true);},1);
		}else{
			var panel = Ext.getCmp('medInfoPanel');
			medInfoTpl.overwrite(panel.body,response.data);
			setTimeout(function(){Ext.getCmp('medInfoPanel').expand(true);},1);
		}
	}	
	
	/////////////////跳转界面///////////////////////////////////////
	function loadJbtsbPage(aac001){
	    var tabs=top.main.tabs;
	    var aid="4028c72016f541540116f66bf2c90005";  	   
	  	var tab=tabs.getItem(aid);
	  	if (tab){tabs.remove(tab);}
	    odin.loadPageInTab("4028c72016f541540116f66bf2c90005","/pages/medicalmgmt/especialdiseasereg/OpenEspecialDiseaseRegAction.do?aac001="+aac001+"",false,"");
	}
	function loadBctsbPage(aac001){
	    var tabs=top.main.tabs;
	    var aid="4028c95f171489cf0117151d25520008";  	   
	  	var tab=tabs.getItem(aid);
	  	if (tab){tabs.remove(tab);}
	    odin.loadPageInTab("4028c95f171489cf0117151d25520008","/pages/medicalmgmt/allowancediseasereg/OpenAllowanceDiseaseRegAction.do?aac001="+aac001+"",false,"");
	}
	function loadYdazPage(aac001){
	    var tabs=top.main.tabs;
	    var aid="4028c72016cc598e0116cc758d0f0002";  	   
	  	var tab=tabs.getItem(aid);
	  	if (tab){tabs.remove(tab);}
	    odin.loadPageInTab("4028c72016cc598e0116cc758d0f0002","/pages/medicalmgmt/patientrelocatedreg/OpenPatientRelocatedRegAction.do?aac001="+aac001+"",false,"");
	}
	function loadZwzyPage(aac001){
	    var tabs=top.main.tabs;
	    var aid="4028c72016cc598e0116cc77bf1e0004";  	   
	  	var tab=tabs.getItem(aid);
	  	if (tab){tabs.remove(tab);}
	    odin.loadPageInTab("4028c72016cc598e0116cc77bf1e0004","/pages/medicalmgmt/egresshospitalize/OpenEgressHospitalizeAction.do?aac001="+aac001+"",false,"");
	}
	function loadTqccPage(aac001){
	    var tabs=top.main.tabs;
	    var aid="4028c95f16d65c680116d661b4090002";  	   
	  	var tab=tabs.getItem(aid);
	  	if (tab){tabs.remove(tab);}
	    odin.loadPageInTab("4028c95f16d65c680116d661b4090002","/pages/medicalmgmt/egressreg/OpenEgressRegAction.do?aac001="+aac001+"",false,"");
	}
		
<odin:template name="medInfoTpl">
	'<div style="float:left;clear:both;margin-left:3px;margin-top:3px">',
	'<div><p><span class="psinfo_data">累计信息:&nbsp;统筹支付&nbsp;<span id="t_ake053">{ake053_t}</span>,&nbsp;大额支付&nbsp;<span id="d_ake053">{ake053_d}</span>,&nbsp;个帐支付&nbsp;<span id="gzlj_ake053">{ake053_gzlj}</span>,&nbsp;住院次数&nbsp;<span id="z_ake053">{ake053_z}</span>,&nbsp;当年个帐余额&nbsp;<span id="d_ekc001">{ekc001}</span>,&nbsp;历年个帐余额&nbsp;<span id="akc087">{akc087}</span>',
	'</span></div>',
	'<div style="margin-top:3px">',
	'<span class="psinfo_data">登记信息:</span>',
	'<span class="psinfo_data">',
	'<tpl for="dtolst">',
	'<a href=javascript:loadJbtsbPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst2">',
	'<a href=javascript:loadBctsbPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst3">',
	'<a href=javascript:loadYdazPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst1">',
	'<a href=javascript:loadZwzyPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst4">',
	'<a href=javascript:loadTqccPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'</span>',
	'</div>',
	'<div style="margin-top:3px">',
	'<div><p><span class="psinfo_data">当前医保待遇类别:&nbsp;<span id="t_akc021">{akc021}</span>',
	'</div>'
	
</odin:template>

<odin:template name="medInfoTpl1">
	'<div style="float:left;clear:both;margin-left:3px;margin-top:3px">',
	'<div><p><span class="psinfo_data">累计信息:&nbsp;统筹支付&nbsp;<span id="t_ake053">{ake053_t}</span>,&nbsp;大额支付&nbsp;<span id="d_ake053">{ake053_d}</span>,&nbsp;个帐支付&nbsp;<span id="gzlj_ake053">{ake053_gzlj}</span>,&nbsp;住院次数&nbsp;<span id="z_ake053">{ake053_z}</span>,&nbsp;当年个帐余额&nbsp;<span id="d_ekc001">{ekc001}</span>,&nbsp;历年个帐余额&nbsp;<span id="akc087">{akc087}</span>',
	'</span></div>',
	'<div style="margin-top:3px">',
	'<span class="psinfo_data">登记信息:</span>',
	'<span class="psinfo_data">',
	'<tpl for="dtolst">',
	'<a href=javascript:loadJbtsbPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst2">',
	'<a href=javascript:loadBctsbPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst3">',
	'<a href=javascript:loadYdazPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst1">',
	'<a href=javascript:loadZwzyPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'<tpl for="dtolst4">',
	'<a href=javascript:loadTqccPage("{personData.aac001}");><span id="t_aka083">{aka083}</span></a> ',
	'</tpl>',
	'</span>',
	'</div>',
	'<div style="margin-top:3px">',
	'<div><p><span class="psinfo_data">当前医保待遇类别:&nbsp;<span id="t_akc021">{akc021}</span>',
	'</div>'
	
</odin:template>
		
	
	
	function medInfoTplCollapse(){
		Ext.getCmp('medInfoPanel').collapse(true);
		//Ext.getCmp('medInfoPanel1').collapse(true);
	}
	
	function personCleared(){
		medInfoTplCollapse();
		<%=onPersonCleared%>();
	}
</script>

<tags:BasicEmpInfo onPersonCleared="personCleared" onPersonSelected="personSelected" beforeDetailsCollapse="medInfoTplCollapse"/>
<odin:panel property="medInfoPanel" frame="false" collapsed="true"  collapsible="true" contentEl=""/>
	
