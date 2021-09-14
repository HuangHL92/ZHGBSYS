<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
<style type="text/css">
	    .listname {
		font-family: "微软雅黑", "黑体", "宋体";
		font-size: 14px;
		font-weight: bold;
	}
	    .listsex {
		font-family: "宋体";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "黑体", "宋体";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "宋体";
		font-size: 12px;
		text-decoration: none;
	}
		psinfo{
			background:transparent;
		}
	    .psinfo_name {
		font-family: "微软雅黑", "黑体", "宋体";
		font-size: 16px;
		font-weight: bold;
		color: #333333;
	}
	    .psinfo_title {
		font-family: "宋体";
		color: #000000;
		width: 60px;
		font-weight: 400;
		font-size: 13px;
	}
	.psinfo_brief {
		font-family: "宋体";
		margin-left: 3px;
		margin-right: 15px;
		color: #000000;
		font-size: 12px;
		font-weight:bold;
	}
	    .psinfo_data {
		font-family: "宋体";
		margin-left: 5px;
		margin-right: 15px;
		color: #333333;
		font-size: 12px;
	}
	.search-item {
	    font:normal 11px tahoma, arial, helvetica, sans-serif;
	    padding:3px 10px 3px 10px;
	    border:1px solid #fff;
	    border-bottom:1px solid #eeeeee;
	    white-space:normal;
	    color:#555;
	}
	.search-item h3 {
	    display:block;
	    font:inherit;
	    font-weight:bold;
	    color:#222;
	}
	
	.search-item h3 span {
	    float: right;
	    font-weight:normal;
	    margin:0 0 5px 5px;
	    width:100px;
	    display:block;
	    clear:none;
	}
	</style>
</head>                            
<body>
<odin:base>                             
<script>    
function doClick(){
   alert(1);
}
</script>
<div id="toolBarDiv"></div>
<script>
function getBusinessInfo(response){
   var jsonData =  response;
   var searchStrData  = document.getElementById('search').value;
   var searchData = Ext.util.JSON.decode(searchStrData);
   searchData.businessData = jsonData;
   document.all('search').value = Ext.util.JSON.encode(searchData);
   digistTpl.overwrite(Ext.get('businessInfo'),jsonData);
   //digistTpl.overwrite(Ext.getCmp('panel_digist').body,jsonData);
   //setTimeout(function(){Ext.getCmp('panel_digist').expand(true);},300);
}
<odin:gridJsonDataModel  id="psid" root="rows" totalProperty="result">
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="sex"/>
  <odin:gridDataCol name="iscode"/>
  <odin:gridDataCol name="pscode"/>
  <odin:gridDataCol name="birthdate"/>
  <odin:gridDataCol name="workdate"/>
  <odin:gridDataCol name="laborrel"/>
  <odin:gridDataCol name="psstatus"/>
  <odin:gridDataCol name="cpname"/>
  <odin:gridDataCol name="mdtype"/>
  <odin:gridDataCol name="account" isLast="true"/>
</odin:gridJsonDataModel>
<odin:dataStore name="myDataStore" url="/samples/tag/toolBarTag_data.jsp" data="{'result' : 2,'rows': [
	 { 'psid': 1, 'name': '岑伟雄', 'sex': '男', 'iscode': '3301831972120700763', 'pscode': '12345678', 'birthdate':'1972-12-07','workdate':'1994-07-01','laborrel':'在保','psstatus':'在职','cpname':'IVAO China Division','mdtype':'事业在职','account':'251.45'},
	 { 'psid': 2, 'name': '陈曦', 'sex': '女', 'iscode': '3122311978042876123', 'pscode': '12345679', 'birthdate':'1978-04-28','workdate':'1999-07-15','laborrel':'在保',  'psstatus':'在职','cpname':'测试单位11111111111111111111111111111111111111111111111111111111','mdtype':'公务员在职','account':'65.15'}
	]}">
</odin:dataStore>
<odin:template name="resultTpl">
	'<tpl for="."><div class="search-item" style="width=400px">',
	'<p><span class="listname">{name}</span>,<span class="listsex">{sex}</span>,&nbsp;{pscode},',
	'<span class="listnormal">{iscode}</span><br>',
	'<span class="listnormal">{cpname}</span></p>',
	'</div></tpl>'
</odin:template> 
<odin:template name="psinfoTpl">
	'<table width="100%"><tr><td>',
	'<div style="float:left;clear:both;margin-left:3px"><div><p><span class="psinfo_name">{name}</span><span class="psinfo_brief">&nbsp;&nbsp;{sex},&nbsp;&nbsp;{iscode},&nbsp;&nbsp;{birthdate}&nbsp;出生,&nbsp;&nbsp;{workdate}&nbsp;参加工作,&nbsp;&nbsp;{laborrel},&nbsp;&nbsp;{psstatus}</span></div>',
	'<div style="margin-top:3px"><span class="psinfo_title">社保编码:</span>',
	'<span class="psinfo_data">{pscode}</span>',
	'<span class="psinfo_title">所在单位:</span>',
	'<span class="psinfo_data"><a href="#">{cpname}</a></span>',
	'</div>',
	'<div style="margin-top:3px">',
	'<span class="psinfo_title">医疗类别:</span>',
	'<span class="psinfo_data">{mdtype}</span>',
	'<span class="psinfo_title">帐号余额:</span>',
	'<span class="psinfo_data">{account}</span>',
	'<span class="psinfo_data" style="text-align:right"><a href=#>详细信息</a></span>',
	'</div>',
	'</div>',
	'<div style="float:right;clear:both;margin-top:15px;margin-right:3px;"><img src="/insiis/img/kfind.gif" /></div>' ,
	'</td></tr><tr><td>',
	'<div id="businessInfo"></div>',
	'</td></tr></table>'
</odin:template>
<odin:template name="digistTpl">
	'<img src="<%=request.getContextPath()%>/img/separator.gif" width="98%" height="2" />',
	'<div style="margin-top:3px;margin-bottom:2px"><span class="psinfo_title">累计信息:</span>',
	'<span class="psinfo_data">&nbsp;&nbsp',
	'<tpl for="facts">',
	'{fname}:{fvalue}&nbsp;',
	'</tpl>',
	'</span>',
	'</div>',
	'<div style="margin-top:3px;margin-bottom:2px"><span class="psinfo_title">登记信息:</span>',
	'<span class="psinfo_data">&nbsp;&nbsp',
	'<tpl for="regist">',
	'<a href=#>{rname}</a>&nbsp;',
	'</tpl>',
	'</span>',
	'</div>'
</odin:template>
</script>
<odin:panel property="panel_psinfo" frame="false" collapsed="true"  collapsible="true" contentEl=""/>
<odin:panel property="panel_digist" frame="true" collapsed="true"  collapsible="true" contentEl=""/>                          
<odin:toolBar property="myToolBar" applyTo="toolBarDiv">
  <odin:textForToolBar text="<img src=/insiis/img/icon/member.gif >人员搜索:"></odin:textForToolBar>
  <odin:comboxForToolBar width="300" tpl="resultTpl" displayField="name" property="mycomboBox" store="myDataStore" onSelect="
  function(record){ 
					Ext.getCmp('mycomboBox').collapse();
					Ext.getCmp('mycomboBox').clearValue();
					Ext.getCmp('panel_psinfo').collapse(true);
					psinfoTpl.overwrite(Ext.getCmp('panel_psinfo').body,record.data);
					var searchData = {};
					searchData.basicData = record.data;
					document.all('search').value = Ext.util.JSON.encode(searchData);
					setTimeout(function(){Ext.getCmp('panel_psinfo').expand(true);},300);
					odin.Ajax.request('/insiis/samples/tag/toolBarTag_data2.jsp',{aac001:'aac001'},getBusinessInfo,null);
  }
  "></odin:comboxForToolBar>
 <odin:textForToolBar text="<a href=#>高级</a>"></odin:textForToolBar>
  <odin:fill></odin:fill>                                                
  <odin:buttonForToolBar  text="清空"  handler="function(b, pressed){
								Ext.getCmp('mycomboBox').clearValue();
								Ext.getCmp('panel_psinfo').collapse(true);
							}" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
  <odin:separator />
  <odin:buttonForToolBar isLast="true" text="查看"  handler="showSearchInfo" icon="../../basejs/ext/resources/images/default/form/exclamation.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
</odin:toolBar>
<odin:hidden property="search"/>
<script>
function showSearchInfo(){
	var searchStrData  = document.all('search').value;
    if(searchStrData!=null&&searchStrData!=""){
    	var searchData = Ext.util.JSON.decode(searchStrData);
    	psinfoTpl.overwrite(Ext.getCmp('panel_psinfo').body,searchData.basicData);
    	setTimeout(function(){Ext.getCmp('panel_psinfo').expand(true);},300);
    	digistTpl.overwrite(Ext.get('businessInfo'),searchData.businessData);
    }
}
Ext.onReady(function(){
    showSearchInfo();
});
</script>



<table width="100%"><tr><td width="50%"> </td><td width="50%"></td></tr></table>  




</odin:base>
</body>
</html>