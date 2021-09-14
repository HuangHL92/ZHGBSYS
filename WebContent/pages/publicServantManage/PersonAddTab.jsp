<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%String ctxPath = request.getContextPath(); 

%>
<style type="text/css">
a#tip {position:relative;left:75px; font-weight:bold;}
a#tip:link,a#tip:hover {text-decoration:none;color:#000;display:block}
a#tip span {display:none;text-decoration:none;}
a#tip:hover #tip_info {display:block;border:1px solid #F96;background:#FFEFEF;padding:10px 20px;position:absolute;top:0px;left:90px;color:#009933}
</style>
<odin:hidden property="a0000"/>
<script type="text/javascript">

function getbaseUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson";
}
function getbaseUrl2(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson2";
}
function getzyjsUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ProfessSkillAddPage";
}
function getxlxwUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.DegreesAddPage";
}
function getgzdwjzwUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.WorkUnitsAddPage";
}
function getjcqkUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.RewardPunishAddPage";
}
function getndkhqkUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AssessmentInfoAddPage";
}
function getpxxxUrl(){
	return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.TrainingInfoAddPage";
}
function getrdsjUrl(){
    return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPartyTimeAddPage";
}
function getOrthersUrl(){
    return "<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OrthersAddPage";
}
var tabs;
Ext.onReady(function(){
	Ext.QuickTips.init();	
	var showTitle = function(id,title){
		if(id==null||id=="null"||id==undefined||id=="undefined"){
			return title;
		}else{
			return title;//return '<font  ext:qtip="红字标题表示该项有内容" color=red>'+title+'</font>';
		}

	};	
	tabs = new Ext.TabPanel({
		title:'helloal',
		region: 'center',
		margins: '0 3 0 0',
		activeTab: 0,
		enableTabScroll: true,
		frame : true,
		//plugins: new Ext.ux.TabCloseMenu(),

		items: [{
			id:"p_tab_baseInfo",
			title : showTitle('1','人员基本信息'),
			html : '<iframe  src="'+getbaseUrl()+'" width="100%" id="tab_baseInfo" height="100%" frameborder="0" scrolling="auto"></iframe>'
		}/*,{
			id:"p_tab_baseInfo2",
			title : showTitle('11','任免表2'),
			html : '<iframe  src="'+getbaseUrl2()+'" width="100%" id="tab_baseInfo" height="100%" frameborder="0" scrolling="auto"></iframe>'
		}*/,{
			id:'tab_Orthers',
			title : showTitle('22','其他信息'),
			html : '<iframe  src="' +getOrthersUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},/*{
			id:'tab_professSkill',
			title : showTitle('2','专业技术职务'),
			html : '<iframe  src="' +getzyjsUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},{
			id:'tab_degrees',
			title : showTitle('3','学历学位'),
			html : '<iframe  src="' +getxlxwUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},{
			id:'tab_workUnits',
			title : showTitle('4','工作单位及职务'),
			html : '<iframe  src="' +getgzdwjzwUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},{
			id:'tab_rewardPunish',
			title : showTitle('5','奖惩情况'),
			html : '<iframe  src="' +getjcqkUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},{
			id:'tab_assessmentInfo',
			title : showTitle('6','年度考核情况'),
			html : '<iframe  src="' +getndkhqkUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		},*/{
			id:'tab_trainingInfo',
			title : showTitle('7','培训信息'),
			html : '<iframe  src="' +getpxxxUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		}/*,{
			id:'tab_addPartyTime',
			title : showTitle('8','入党时间'),
			html : '<iframe  src="' +getrdsjUrl()+ '" width="100%" height="100%" frameborder="0" scrolling="auto" }></iframe>'
		}*/
		]
	});
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	
	
	var a0000 = parent.tabs.getActiveTab().id;
	if(a0000.indexOf("addTab")!=-1){
		radow.doEvent('tabClick',"add");
	}else{
		radow.doEvent('tabClick',a0000);
	}
	

});
/*
function  tabClickEvent(){
	var a0000 = parent.tabs.getActiveTab().id;
	radow.doEvent('tabClick',a0000);
}
*/
/*
function addevent(id){
	document.getElementById("ext-comp-1002__"+id).attachEvent("onclick",
		function(){
		//alert(document.getElementById("tab_baseInfo").contentWindow.Ext.getCmp("a0000").getValue());
			radow.doEvent('tabClick',document.getElementById("tab_baseInfo").contentWindow.Ext.getCmp("a0000").getValue());
		});
}*/

</script>
