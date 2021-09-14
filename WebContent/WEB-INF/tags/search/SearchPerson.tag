<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="人员基础信息" description="人员基础信息"%>
<%@ attribute name="onPersonSelected" description="当人员被选择时调用" required="false"%>
<%@ attribute name="onPersonCleared" description="当人员被清除时调用(暂时不用)" required="false"%>
<%@ attribute name="beforeDetailsCollapse" description="人员详细信息面板收缩前触发的事件(暂时不用)" required="false"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String ctxpathemp = request.getContextPath();
%>
<style type="text/css">
	    .listname {
		font-family: "微软雅黑","宋体";
		font-size: 14px;
		font-weight: bold;
	}
	    .listaac004 {
		font-family: "宋体";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "宋体";
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
		font-family: "微软雅黑", "宋体";
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
		font-weight:thin;
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

<script>

/**
 人员详细信息面板收缩前触发的事件
 */
function intelligentSearch_collapseDetails(){
  <% if (beforeDetailsCollapse!=null && !beforeDetailsCollapse.equals("")){ %>
    <%=beforeDetailsCollapse%>();
  <%}%>
  Ext.getCmp('intelligentSearchPersonDetailsPanel').collapse(true);
}

/**
 设置详细信息
 */
function intelligentSearch_setPersonDetails(response){
	var panel = Ext.getCmp('intelligentSearchPersonDetailsPanel');
	intelligentSearchPersonDetailsTpl.overwrite(panel.body,response.data.data[0]);
	setTimeout("Ext.getCmp('intelligentSearchPersonDetailsPanel').expand(true)",200);
	
	var data = response.data.data[0];
	//设值
	Ext.getCmp('searchAaz157').setValue(data.aaz157);
	Ext.getCmp('searchAae135').setValue(data.aae135);
	Ext.getCmp('searchEac001').setValue(data.eac001);
	Ext.getCmp('searchAaz501').setValue(data.aaz501);
	Ext.getCmp('searchAac003').setValue(data.aac003);
	Ext.getCmp('searchAac004').setValue(data.aac004);
	Ext.getCmp('searchAaz001').setValue(data.aaz001);
	Ext.getCmp('searchAab001').setValue(data.aab001);
	Ext.getCmp('searchAab004').setValue(data.aab004);
	
	<%if(onPersonSelected!=null&&!onPersonSelected.equals("")){%>
	<%=onPersonSelected%>(response.data.data[0]);
	<%}%>
}

/**
 清空操作
 */
function intelligentSearch_clear(b, pressed){
 	Ext.getCmp('searchText').clearValue();
	intelligentSearch_collapseDetails();
	<%if(onPersonCleared!=null&&!onPersonCleared.equals("")){%>
	<%=onPersonCleared%>();
	<%}else{%>
	//odin.reset();
	<%}%>
}

/**
 选中后的操作
 */
function intelligentSearch_doSelect(record){ 
	var aaz157 = record.data.aaz157;
	Ext.getCmp('searchText').collapse();
	//intelligentSearch_clear();					
	//Ext.getCmp('searchText').clearValue();
	intelligentSearch_collapseDetails();
	setTimeout(function(){odin.Ajax.request(contextPath+'/com/insigma/siis/search/SearchAction.do?method=getPersonByAaz157',
	                   {'aaz157':aaz157},
	                   intelligentSearch_setPersonDetails,
	                   null,odin.asynchronous,false);},200);
}

<odin:gridJsonDataModel>
  <odin:gridDataCol name="aae135"/>
  <odin:gridDataCol name="aac003"/>
  <odin:gridDataCol name="eac001"/>
  <odin:gridDataCol name="aaz501"/>
  <odin:gridDataCol name="aab004"/>
  <odin:gridDataCol name="aac004name"/>
  <odin:gridDataCol name="aab001"/>
  <odin:gridDataCol name="aac008name"/>
  <odin:gridDataCol name="eac070name"/>
  <odin:gridDataCol name="aaz157" isLast="true"/>
</odin:gridJsonDataModel>
<odin:dataStore name="intelligentSearchDataStore" url="/com/insigma/siis/search/SearchAction.do?method=searchPerson">
</odin:dataStore>
<odin:template name="intelligentSearchCandidateTpl">
	'<tpl for="."><div class="search-item" style="width=655px">',
	'<p><span class="listname">{aac003}</span>,&nbsp;<span class="listaac004">{aac004name}</span>,',
	'&nbsp;{aae135},',
	'&nbsp;{eac001},',
	'&nbsp;{eac070name},',
	'&nbsp;{aac008name},',
	'&nbsp;{aab004},',
	'&nbsp;{aab001}',
	'</p></div></tpl>'
</odin:template> 
<odin:template name="intelligentSearchPersonDetailsTpl">
	'<div style="float:left;clear:both;margin-left:3px">',
	'<div>',
	'<p>',
	'<span class="psinfo_name"><span>{aac003}</span></span>',
	'<span class="psinfo_brief">&nbsp;',
	'性别:<span>{aac004name}</span>,&nbsp;',
	'出生日期:<span>{bdatestring}</span>,&nbsp;',
	'身份证号码:<span>{aae135}</span>,&nbsp;',
	'个人编码:<span>{eac001}</span>,&nbsp;',
	'人员性质:<span>{eac070name}</span>,&nbsp;',
	'参保状态:<span>{aac008name}</span>,&nbsp;',
	'单位名称:<span>{aab004}</span>,&nbsp;',
	'单位编码:<span>{aab001}</span>',
	'</span>',
	'</p>',
	'</div>',
	'</div>',
	{
     hasInsurances: function(insurances){
     	alert('insurances');
         return insurances[0] != null;
     }
	}
</odin:template>


</script>

<div id="div_searchPerson"></div>
<odin:panel property="intelligentSearchPersonDetailsPanel" frame="false" collapsed="true" collapsible="true" contentEl=""/>                      

<odin:toolBar property="intelligentSearchBar" applyTo="div_searchPerson">
	<odin:textForToolBar
		text="<img src=../img/icon/ry_serach.gif>人员搜索:&nbsp;"></odin:textForToolBar>
	<odin:comboxForToolBar width="660" tpl="intelligentSearchCandidateTpl"
		displayField="aae135" property="searchText" loadingText="搜索中..." 
		store="intelligentSearchDataStore" minChars="2" onSelect="intelligentSearch_doSelect" pageSize="15"
		maxHeight="500" disableKeyFilter="true" 
		emptyText="请输入姓名、身份证或个人编码进行人员搜索，至少输入2个中文字符或4个英文(数字)字符" >
	</odin:comboxForToolBar>
	<odin:comboxForToolBar displayField="" property="searchAaz157" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAae135" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchEac001" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchCrcode" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAac003" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAac004" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAaz001" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAab001" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	<odin:comboxForToolBar displayField="" property="searchAab004" width="0" store="intelligentSearchDataStore" hideTrigger="true" />
	
	<odin:buttonForToolBar isLast="true" text="清空"  handler="intelligentSearch_clear" icon="../img/icon/qinkong.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
  	
</odin:toolBar>

<script type="text/javascript">

/**
 查询前校验
 */
function doCheckBeforeQuery(){
	var value = Ext.getCmp('searchText').getValue();
	var length = value.replace(/[^\x00-\xff]/g,'aa').length;
	if(length < 4){
		return false;
	}
	return true;
}

Ext.onReady(
	function(){
		Ext.getCmp('searchText').addListener('beforequery',doCheckBeforeQuery);
		
    }
);

</script>