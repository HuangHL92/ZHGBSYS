<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="��Ա������Ϣ" description="��Ա������Ϣ" %>
<%@ attribute name="onPersonSelected" description="����Ա��ѡ��ʱ����" required="false"%>
<%@ attribute name="onPersonCleared" description="����Ա�����ʱ����" required="false"%>
<%@ attribute name="beforeDetailsCollapse" description="��Ա��ϸ��Ϣ�������ǰ�������¼�" required="false"%> 
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<style type="text/css">
	    .listname {
		font-family: "΢���ź�", "����", "����";
		font-size: 14px;
		font-weight: bold;
	}
	    .listaac004 {
		font-family: "����";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "����", "����";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "����";
		font-size: 12px;
		text-decoration: none;
	}
		psinfo{
			background:transparent;
		}
	    .psinfo_name {
		font-family: "΢���ź�", "����", "����";
		font-size: 16px;
		font-weight: bold;
		color: #333333;
	}
	    .psinfo_title {
		font-family: "����";
		color: #000000;
		width: 60px;
		font-weight: 400;
		font-size: 13px;
	}
	.psinfo_brief {
		font-family: "����";
		margin-left: 3px;
		margin-right: 15px;
		color: #000000;
		font-size: 12px;
		font-weight:thin;
	}
	    .psinfo_data {
		font-family: "����";
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
<div id="toolBarDiv"></div>
<script>
function doReadCard(){
  var cardno;
  cardno = document.all('ocxObj').ReadICData(1,15,1);
  if (cardno !=null) 
    if (cardno.length != 19)
      alert(cardno);
    else{
		//intelligentSearch_clear();
		intelligentSearch_collapseDetails();
		setTimeout(function(){
			odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/search/GetPersonByCardIdAction.do?method=getPersonByCardId',
			                   {'cardId':cardno},
			                   intelligentSearch_setPersonDetails,
			                   null);
			      },200);    
    }
}

function intelligentSearch_collapseDetails(){
  <% if (beforeDetailsCollapse!=null && !beforeDetailsCollapse.equals("")){ %>
    <%=beforeDetailsCollapse%>();
  <%}%>
  Ext.getCmp('intelligentSearchPersonDetailsPanel').collapse(true);
}

function intelligentSearch_setPersonDetails(response){
	var panel = Ext.getCmp('intelligentSearchPersonDetailsPanel');
	intelligentSearchPersonDetailsTpl.overwrite(panel.body,response.data);
	setTimeout(function(){Ext.getCmp('intelligentSearchPersonDetailsPanel').expand(true);},200);
	<%if(onPersonSelected!=null&&!onPersonSelected.equals("")){%>
	<%=onPersonSelected%>(response.data);
	<%}%>
}

function intelligentSearch_clear(b, pressed){
	Ext.getCmp('intelligentSearchBox').clearValue();
	intelligentSearch_collapseDetails();
	<%if(onPersonCleared!=null&&!onPersonCleared.equals("")){%>
	<%=onPersonCleared%>();
	<%}else{%>
	odin.reset();
	<%}%>
}

function advanceSerach(){
	var rs=window.showModalDialog(contextPath+"/pages/common/openEmpInfoCommonQueryAction.do",null,"help:no;status:no;dialogWidth:50;dialogHeight:35");
	if(rs){
		odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/search/GetPersonByIdAction.do?method=getPersonById',
		                   {'personId':rs.aac001},
		                   intelligentSearch_setPersonDetails,
		                   null);
	}
}

<odin:gridJsonDataModel  id="aac001" root="candidates" totalProperty="count">
  <odin:gridDataCol name="aac001"/>
  <odin:gridDataCol name="eac001"/>
  <odin:gridDataCol name="aae135"/>
  <odin:gridDataCol name="aac003"/>
  <odin:gridDataCol name="aac004"/>
  <odin:gridDataCol name="aac006" isLast="true"/>
</odin:gridJsonDataModel>
<odin:dataStore name="intelligentSearchDataStore" url="/com/insigma/siis/local/module/common/search/SearchPersonAction.do?method=searchPerson">
</odin:dataStore>
<odin:template name="intelligentSearchCandidateTpl">
	'<tpl for="."><div class="search-item" style="width=400px">',
	'<p><span class="listname">{aac003}</span>,<span class="listaac004">{aac004}</span>,&nbsp;{eac001},{aae135},',
	'<span class="listnormal">{aac006}</span></p>',
	'</div></tpl>'
</odin:template> 
<odin:template name="intelligentSearchPersonDetailsTpl">
	'<div style="float:left;clear:both;margin-left:3px"><div><p><span class="psinfo_name"><span id="t_aac003">{aac003}</span></span><span class="psinfo_brief">&nbsp;<span id="t_aac004">{aac004Name}</span>,�籣����&nbsp;<span id="t_eac001">{eac001}</span>,&nbsp;<span id="t_aaa029">{aaa029Name}</span>&nbsp;<span id="t_aae135">{aae135}</span>,',
	'&nbsp;&nbsp;<span id="t_aac006">{aac006s}</span>&nbsp;����,&nbsp;&nbsp;<span id="t_aac007">{aac007s}</span>&nbsp;�μӹ���,&nbsp;<span id="t_aac012">{aac012Name},',
	'<tpl for="insurances">',
	'&nbsp;{aae140_value}',
	'</tpl>',
	'&nbsp;</span><span id="link"><a href=javascript:loadPersonPage("{aac001}");>��ϸ��Ϣ</a></span>',
	'</span></div>',
	'<div style="margin-top:3px">',
	'<span class="psinfo_title">�α���λ:</span>',
	'<span class="psinfo_data">',
	'<tpl for="corps">',
	'<a href=javascript:loadOrgPage("{eab001}");>{aae044}</a>&nbsp;',
	'</tpl>',
	'</span>',
	'</div>',
	'</div>',
	{
     hasInsurances: function(insurances){
         return insurances[0] != null;
     }
	}
</odin:template>
////////////////���õ�λ�ۺϲ�ѯ///////////////////////////////
function loadOrgPage(eab001){
  	var tabs=top.main.tabs;
	var aid="737cbc96cc4e0051161145b2bf0b6fa5";  	   
	var tab=tabs.getItem(aid);
	if (tab){tabs.remove(tab);}
    odin.loadPageInTab("737cbc96cc4e0051161145b2bf0b6fa5","/pages/organizationmgmt/archivequery/OpenArchiveQueryAction.do?orgid="+eab001+"",false,"");
}
/////////////////������Ա�ۺϲ�ѯ///////////////////////////////////////
function loadPersonPage(aac001){
    var tabs=top.main.tabs;
    var aid="4028c7be16f518780116f51fe87f0006";  	   
  	var tab=tabs.getItem(aid);
  	if (tab){tabs.remove(tab);}
    odin.loadPageInTab("4028c7be16f518780116f51fe87f0006","/pages/insuredmgmt/archivequery/OpenArchiveQueryAction.do?aac001="+aac001+"",false,"");
}
</script>
 <object id="ocxObj" style="display:none" classid="CLSID:A00B372D-2E58-4874-B9CD-2BDC179FD821" 
 codebase="<%=ctxpathemp %>/ICCInter.ocx">
 </object>
<odin:panel property="intelligentSearchPersonDetailsPanel" frame="false" collapsed="true"  collapsible="true" contentEl=""/>                      
<odin:toolBar property="intelligentSearchBar" applyTo="toolBarDiv">
  <odin:textForToolBar text="<img src=/insiis/img/icon/ry_serach.gif >��Ա����:"></odin:textForToolBar>
  <odin:comboxForToolBar width="350" tpl="intelligentSearchCandidateTpl" displayField="aac003" property="intelligentSearchBox" store="intelligentSearchDataStore" minChars="2" onSelect="
  function(record){ 
					var aac001 = record.data.aac001;
					Ext.getCmp('intelligentSearchBox').collapse();
					//intelligentSearch_clear();					
					Ext.getCmp('intelligentSearchBox').clearValue();
					intelligentSearch_collapseDetails();
					setTimeout(function(){odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/search/GetPersonByIdAction.do?method=getPersonById',
					                   {'personId':aac001},
					                   intelligentSearch_setPersonDetails,
					                   null);},200);
					  }
  "></odin:comboxForToolBar>
 <odin:separator></odin:separator>
 <odin:textForToolBar text="<a href=javascript:advanceSerach();>�߼�</a>"></odin:textForToolBar>
  <odin:fill></odin:fill>                                                
  <odin:buttonForToolBar  text="���"  handler="intelligentSearch_clear" icon="/insiis/img/icon/qinkong.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
  <odin:separator />
  <odin:buttonForToolBar isLast="true" text="����"  handler="doReadCard" icon="/insiis/img/icon/duka.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
</odin:toolBar>

