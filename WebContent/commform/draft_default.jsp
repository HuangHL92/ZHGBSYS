<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%@page import="com.insigma.odin.framework.util.commform.CertUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<odin:commformhead/>
<link href="<%=request.getContextPath()%>/commform/css/draft.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/basejs/welcome.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/basejs/sionline_sys.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/commpages/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/commform/basejs/odin.js"></script>
<odin:commformMDParam/> 

</head>
<% 
	String rate=LoginManager.getCurrentRate();
	String username=LoginManager.getCurrentLoginName();
	Boolean isNeedSign=CertUtil.isNeedSign();
	if(isNeedSign){ //���ܻ����CAPICOM�������Ը�Ϊֻ������ǩ��ʱ�Ŵ���
		%>
			<script type="text/javascript" src="basejs/signature.js"></script>
		<%
	};
%>
<script type="text/javascript">
var currentOpseno,currentUsername="<%=username%>";
var rate="<%=rate%>";
var username="<%=username%>";
var isNeedSign=<%=isNeedSign%>;
//dosuccess������Ϊ�� cpquery���ٵ���breakShale()����
function doSuccess(response) {
	breakShale();
}
</script>
<body>
<odin:base>
		
<odin:commformwindow src="/pages/netpreaudit/auditDetail.jsp"  width="505" height="380" title="����Ԥ����ϸ" id="detailWindow"></odin:commformwindow>
<odin:commformwindow src="/advancedSearch.jsp"  width="300" height="250" title="�߼�����" id="searchWindow"></odin:commformwindow>
<odin:commformwindow
			id="win_pup" src="" title="" modal="true" width="0" height="0" />
<odin:form action="/pages/comm/commAction.do?method=doAction"
		method="post">
<div id="div_1" >
<table width="100%"><tr >
<td  nowrap="nowrap"><span class="x-form-item">ҵ������</span></td>
<odin:select property="businessType" editor="false" allAsItem="true" value="all" codeType="YWLX" onchange="getBusinessTypeQueryStr" width="100"/>
<td style="cursor:hand;">
	<!-- img src="img/btn.gif" border="0" usemap="#dateMap" mapfile="map"-->
	<img src="<%=request.getContextPath()%>/commform/img/weianxiqu_btn.gif" border="0" onclick="getQueryTypeFilterStr(1)" onmouseover="this.src='<%=request.getContextPath()%>/commform/img/up_btn.gif'" onmouseout="this.src='<%=request.getContextPath()%>/commform/img/weianxiqu_btn.gif'"><img src="<%=request.getContextPath()%>/commform/img/weianxiqu_btn2.gif" border="0" onclick="getQueryTypeFilterStr(2)" onmouseover="this.src='<%=request.getContextPath()%>/commform/img/up_btn2.gif'" onmouseout="this.src='<%=request.getContextPath()%>/commform/img/weianxiqu_btn2.gif'">
</td><td><!--<odin:dateEdit property="businessDate" title="�����²�ѯ��Ϣ������������ֱ���������ơ�200709����Ȼ������������ط����в�ѯ" format="Ym" width="80"></odin:dateEdit>--></td>
<odin:textEdit  property="ywym" label="ҵ������" width="60" maxlength="6" onchange="getQueryTypeFilterStr(3)" />
<td><table><tr id="hide" style="display:block">
<odin:query property="cpquery"  width="120" label="��(����)��ѯ"/>
<odin:textEdit property="aab004"  disabled="true" width="120"/>
</tr></table></td>
<td width="100"><img onclick="breakShale()" src="<%=request.getContextPath()%>/commform/img/sxin.gif"  height="19" style="cursor:hand;  float:left;margin:0px; padding:0px 0px 3px 0px;"></td>
</tr>
<tr>

</tr>
</table></odin:form></div>

<!--content_tab����-->
<div id="content_tab">
<div id="content_tableft"> 
	<ul>
	<li class="hot2"><a href="javascript:void(0)">�ݸ���(<span id="declareSpan0"></span>)</a></li>
	<li ><a href="javascript:void(0)" onclick="right_review_on(1)">�������(<span id="declareSpan1"></span>)</a></li>
	<li ><a href="javascript:void(0)" onclick="right_review_on(2)">���ͨ��(<span id="declareSpan2"></span>)</a></li>
	<li ><a href="javascript:void(0)" onclick="right_review_on(3)">���δͨ��(<span id="declareSpan3"></span>)</a></li>
	<li ><a href="javascript:void(0)" onclick="right_review_on(4)">ȫ��(<span id="declareSpan4"></span>)</a></li>
	</ul>
</div>
<!-- ��������� -->
<div id="content_tabright">
	<div class="fillet_boxcontent">
		<div  class="fillet_boxcontent_top">
		<img src="<%=request.getContextPath()%>/commform/img/xinx.gif" border="0">�𾴵��û�������<span id="declareSpan0" style="margin:2px;color:#FF0000; font-weight:bold; line-height:23px;"></span>��ҵ��Ϊ�ݸ�״̬</div>
		<div  style=" float:right; margin:2px; height:26;">
			<!-- ɾ�� --><img style="cursor:hand;" onclick="selectDeleteDraft()" src="<%=request.getContextPath()%>/commform/img/send_shanchu.jpg">
			<!-- ȫѡ --><img style="cursor:hand;" onclick="selectall()" src="<%=request.getContextPath()%>/commform/img/send_quanxuan.jpg">
			<!-- �걨 --><img style="cursor:hand;" onclick="doDeclare()" src="<%=request.getContextPath()%>/commform/img/send_shenbao.jpg">
		</div>
		<div style="float:left;" >
		<odin:gridSelectColJs name="btype" codeType="YWLX"></odin:gridSelectColJs>
		<odin:gridWithPagingTool sm="checkbox"  autoFill="false" forceNoScroll="false"  rowDbClick="doOpenEditWindow" url="/common/pageQueryAction.do?method=query" property="grid0" pageSize="15" isFirstLoadData="false" width="880" height="400">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		  <odin:gridDataCol name="id" />
		  <odin:gridDataCol name="aac001" />
		  <odin:gridDataCol name="bcode" />
		  <odin:gridDataCol name="aab001" />
		  <odin:gridDataCol name="aab004" />
		  <odin:gridDataCol name="draftstatus"/>
		  <odin:gridDataCol name="btype"/>
		  <odin:gridDataCol name="declarestatus"/>
		  <odin:gridDataCol name="lookperson"/>
		  <odin:gridDataCol name="lookdate"/>
		  <odin:gridDataCol name="badddate"/>
  		  <odin:gridDataCol name="hashcode"/>
		  <odin:gridDataCol name="binfo" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
		  <odin:gridSmColumn />
		  <odin:gridColumn renderer="renderDraftStatus"  header="״̬" width="35" dataIndex="draftstatus" />
		  <odin:gridColumn  header="���" align="center" width="70" dataIndex="bcode" align="right"/>
		  <odin:gridColumn  header="ҵ�����" align="left" width="90" dataIndex="btype" codeType="YWLX" editor="select"  edited="false"/>
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab001" />
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab004" />
		  <odin:gridColumn  header="ҵ����Ϣ" renderer="renderAlt" width="240" align="left"  dataIndex="binfo" />
		  <odin:gridColumn  header="�ʱ��"   align="center" width="120" dataIndex="badddate" />
		  <odin:gridColumn  renderer="renderDeleteDraft" header="����" align="center" width="60" dataIndex="bcode" />
		  <odin:gridColumn  header="�޸�" renderer="renderEditDraft" align="center" width="60" dataIndex="bcode" isLast="true"/>
		</odin:gridColumnModel>		
		</odin:gridWithPagingTool>
		</div>
	</div>
</div>
</div>

<div id="content_tab" style="display:none">
<div id="content_tableft">
<ul>
<li ><a href="javascript:void(0)" onclick="right_review_on(0)">�ݸ���(<span id="declareSpan0"></span>)</a></li>
<li class="hot2"><a href="javascript:void(0)">�������(<span id="declareSpan1"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(2)">���ͨ��(<span id="declareSpan2"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(3)">���δͨ��(<span id="declareSpan3"></span>)</a></li>
<li ><a href="javascript:void(0)"  onclick="right_review_on(4)">ȫ��(<span id="declareSpan4"></span>)</a></li>
</ul>
</div>
<!-- ��������� -->
<div id="content_tabright">

<div class="fillet_boxcontent"> 
<div class="fillet_boxcontent_top">
<img src="<%=request.getContextPath()%>/commform/img/xinx.gif" border="0">�𾴵��û�����������˵���<span id="declareSpan1" style="margin:2px;color:#FF0000; font-weight:bold; line-height:23px;">10</span>��ҵ��.</div>
<div  style=" float:left; margin:0px; color:blue; font-size:12px;">��ע��鿴��˽���������δͨ������鿴δͨ��ԭ�򣬲����½���ҵ�����!</div>
<div style="float:left;" >
<odin:gridWithPagingTool  autoFill="false" forceNoScroll="false" rowDbClick="detailPreAudit2" url="/common/pageQueryAction.do?method=query" property="grid1" pageSize="15" isFirstLoadData="false" title="" width="880" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
 <odin:gridDataCol name="id" />
 <odin:gridDataCol name="aac001" />
  <odin:gridDataCol name="bcode" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004" />
  <odin:gridDataCol name="draftstatus"/>
  <odin:gridDataCol name="btype"/>
  <odin:gridDataCol name="declarestatus"/>
  <odin:gridDataCol name="lookperson"/>
  <odin:gridDataCol name="lookdate"/>
  <odin:gridDataCol name="badddate"/>
  <odin:gridDataCol name="hashcode"/>
  <odin:gridDataCol name="binfo" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
		  <odin:gridRowNumColumn></odin:gridRowNumColumn>
		  <odin:gridColumn  header="���" align="center" width="70" align="right" dataIndex="bcode" />
		  <odin:gridColumn  header="ҵ�����" align="left" width="100"  dataIndex="btype" codeType="YWLX" editor="select" edited="false"/>
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab001" />
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab004" />
		  <odin:gridColumn  header="ҵ����Ϣ" renderer="renderAlt" width="300" align="left" dataIndex="binfo" />
		  <odin:gridColumn  header="�ʱ��"  align="center" width="120" dataIndex="badddate" />
		  <odin:gridColumn  header="����" renderer="renderInfoDraft" align="center" width="80" dataIndex="bcode" isLast="true"/>
		</odin:gridColumnModel>			
</odin:gridWithPagingTool>
</div>
</div>
</div>
</div>

<div id="content_tab" style="display:none">
<div id="content_tableft">
<ul>
<li ><a href="javascript:void(0)" onclick="right_review_on(0)">�ݸ���(<span id="declareSpan0"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(1)">�������(<span id="declareSpan1"></span>)</a></li>
<li  class="hot2"><a href="javascript:void(0)">���ͨ��(<span id="declareSpan2"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(3)">���δͨ��(<span id="declareSpan3"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(4)">ȫ��(<span id="declareSpan4"></span>)</a></li>
</ul>
</div>
<!-- ��������� -->
<div id="content_tabright">
<div class="fillet_boxcontent">
<div class="fillet_boxcontent_top">
<img src="<%=request.getContextPath()%>/commform/img/xinx.gif" border="0">�𾴵��û��������ͨ������<span id="declareSpan2" style="margin:4px;color:#FF0000; font-weight:bold; line-height:23px;">15</span>��ҵ��</div>
<div style="float:left;" >
<odin:gridWithPagingTool  autoFill="false" forceNoScroll="false" rowDbClick="detailPreAudit2" url="/common/pageQueryAction.do?method=query" property="grid2" pageSize="15" isFirstLoadData="false" title="" width="880" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="aac001" />
  <odin:gridDataCol name="bcode" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004" />
  <odin:gridDataCol name="draftstatus"/>
  <odin:gridDataCol name="btype"/>
  <odin:gridDataCol name="declarestatus"/>
  <odin:gridDataCol name="lookdate"/>
  <odin:gridDataCol name="badddate"/>
  <odin:gridDataCol name="hashcode"/>
  <odin:gridDataCol name="binfo" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
		  <odin:gridRowNumColumn></odin:gridRowNumColumn>
		  <odin:gridColumn  header="���" align="center" width="70" align="right" dataIndex="bcode" />
		  <odin:gridColumn  header="ҵ�����" align="left" width="100"  dataIndex="btype" codeType="YWLX" editor="select" edited="false"/>
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab001" />
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab004" />
		  <odin:gridColumn  header="ҵ����Ϣ" renderer="renderAlt" width="260" align="left" dataIndex="binfo" />
		  <odin:gridColumn  header="�ʱ��"  align="center" width="120" dataIndex="badddate" />
		  <odin:gridColumn  header="���ʱ��" align="center"  width="120" dataIndex="lookdate" />
		  <odin:gridColumn  header="����" renderer="renderInfoDraft" align="center" width="80" dataIndex="bcode" isLast="true"/>
		</odin:gridColumnModel>			
</odin:gridWithPagingTool>
</div>
</div>
</div>
</div>


<div id="content_tab" style="display:none">
<div id="content_tableft">
<ul>
<li ><a href="javascript:void(0)"  onclick="right_review_on(0)">�ݸ���(<span id="declareSpan0"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(1)">�������(<span id="declareSpan1"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(2)">���ͨ��(<span id="declareSpan2"></span>)</a></li>
<li  class="hot2"><a href="javascript:void(0)">���δͨ��(<span id="declareSpan3"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(4)">ȫ��(<span id="declareSpan4"></span>)</a></li>
</ul>
</div>
<!-- ��������� -->
<div id="content_tabright">
<div class="fillet_boxcontent">
<div class="fillet_boxcontent_top">
<img src="<%=request.getContextPath()%>/commform/img/xinx.gif" border="0">�𾴵��û��������δͨ������<span id="declareSpan3" style="margin:4px;color:#FF0000; font-weight:bold; line-height:43px;">15</span>��ҵ��</div>
<div style="float:left;" >
<odin:gridWithPagingTool autoFill="false"  forceNoScroll="false" rowDbClick="detailPreAudit2" url="/common/pageQueryAction.do?method=query" property="grid3" pageSize="15" title="" isFirstLoadData="false" width="800" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="aac001" />
  <odin:gridDataCol name="bcode" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004" />
  <odin:gridDataCol name="draftstatus"/>
  <odin:gridDataCol name="btype"/>
  <odin:gridDataCol name="declarestatus"/>
  <odin:gridDataCol name="lookdate"/>
  <odin:gridDataCol name="badddate"/>
  <odin:gridDataCol name="notice"/>
  <odin:gridDataCol name="hashcode"/>
  <odin:gridDataCol name="binfo" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
		  <odin:gridRowNumColumn></odin:gridRowNumColumn>
		  <odin:gridColumn  header="���" align="center" width="70" align="right" dataIndex="bcode" />
		  <odin:gridColumn  header="ҵ�����" align="left" width="80"  dataIndex="btype" codeType="YWLX" editor="select" edited="false"/>
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab001" />
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab004" />
		  <odin:gridColumn  header="ҵ����Ϣ" renderer="renderAlt" align="left" width="200" dataIndex="binfo" />
		  <odin:gridColumn  header="δͨ��ԭ��" renderer="renderAlt" align="left" width="180" dataIndex="notice" />
		  <odin:gridColumn  header="�ʱ��"  align="center" width="120" dataIndex="badddate" />
		  <odin:gridColumn  header="���ʱ��" align="center"  width="120" dataIndex="lookdate" />	
		  <odin:gridColumn  header="����" renderer="renderInfoDraft" align="center" width="80" dataIndex="bcode" isLast="true"/>	  
		</odin:gridColumnModel>			 
</odin:gridWithPagingTool>
</div>
</div>
</div>
</div>


<div id="content_tab" style="display:none">
<div id="content_tableft">
<ul>
<li ><a href="javascript:void(0)" onclick="right_review_on(0)">�ݸ���(<span id="declareSpan0"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(1)">�������(<span id="declareSpan1"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(2)">���ͨ��(<span id="declareSpan2"></span>)</a></li>
<li ><a href="javascript:void(0)" onclick="right_review_on(3)">���δͨ��(<span id="declareSpan3"></span>)</a></li>
<li  class="hot2"><a href="javascript:void(0)">ȫ��(<span id="declareSpan4"></span>)</a></li>
</ul>
</div>
<!-- ��������� -->
<div id="content_tabright">
<div class="fillet_boxcontent">
<div class="fillet_boxcontent_top">
<img src="<%=request.getContextPath()%>/commform/img/xinx.gif" border="0">�𾴵��û������ܹ���<span id="declareSpan4" style="margin:4px;color:#FF0000; font-weight:bold; line-height:40px;"></span>��ҵ��</div>
<div style="float:left;" >
<odin:gridSelectColJs name="draftstatus" codeType="SBZT"></odin:gridSelectColJs>
<odin:gridWithPagingTool  autoFill="false" forceNoScroll="false" rowDbClick="detailPreAudit2" url="/common/pageQueryAction.do?method=query" property="grid4" pageSize="15" title="" isFirstLoadData="false" width="800" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="aac001" />
  <odin:gridDataCol name="bcode" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004" />
  <odin:gridDataCol name="draftstatus"/>
  <odin:gridDataCol name="btype"/>
  <odin:gridDataCol name="declarestatus"/>
  <odin:gridDataCol name="lookperson"/>
  <odin:gridDataCol name="badddate"/>
  <odin:gridDataCol name="hashcode"/>
  <odin:gridDataCol name="binfo" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
		  <odin:gridRowNumColumn></odin:gridRowNumColumn>
		  <odin:gridColumn  header="���" align="center" width="70" align="right" dataIndex="bcode" />
		  <odin:gridColumn  header="ҵ�����" align="left" width="100"  dataIndex="btype" codeType="YWLX" editor="select" edited="false"/>
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab001" />
		  <odin:gridColumn  header="��(����)����" renderer="renderAlt" width="100" align="left"  dataIndex="aab004" />
		  <odin:gridColumn  header="ҵ����Ϣ" renderer="renderAlt" width="320" align="left" dataIndex="binfo" />
		  <odin:gridColumn  header="�ʱ��"  align="center" width="120" dataIndex="badddate" />
		  <odin:gridColumn  header="�걨״̬"  align="left" width="120" dataIndex="draftstatus" editor="select" edited="false" codeType="SBZT" />
		  <odin:gridColumn  header="����" renderer="renderInfoDraft" align="center" width="120" dataIndex="bcode" isLast="true"/>
</odin:gridColumnModel>			
</odin:gridWithPagingTool>
</div>
</div>
</div>
</div>



<!-- end ��������� -->

<odin:commformwindow src="/commform/blank.htm" id="SbdnNoticeWindow" maximizable="true" width="500" height="350"></odin:commformwindow>



<!-- map name="dateMap">
	<area shape="rect" coords="1,1,42,19" href="javascript:getQueryTypeFilterStr(1)">
	<area shape="rect" coords="42,1,81,19" href="javascript:getQueryTypeFilterStr(2)">
</map-->
<script>
<odin:template name="SbdnNoticeTpl">
 	'<div  class="title"><span id="SbdnNoticeBtn" style="font-weight:bold;">���֪ͨ</span><span style=" margin-left:240px;"><a href="javascript:void(0)" onclick="doOpenMoreSbdnNotice()">����</a></span></div>',
	'<ul>',
	'<tpl for=".">',
	'<li><a href="javascript:void(0)" onclick="doShowSbdnNotice(\'{id}\')" title="{alltitle}">{title}</a></li>',
	'</tpl>',
	'</ul>'
</odin:template>
Ext.onReady(function(){
	init();
	doGetAllStatusCount(); //��ȡ����״̬�ļ�¼����
	//doLoadGridData(0); //���زݸ�������
	//getSbdnNoticeContent();//��ȡ֪ͨ����
});  
</script>
</odin:base>
</body>
</html>