<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.TemplateConfPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>
<odin:head></odin:head>
<%@include file="/comOpenWinInit.jsp" %>




<style>
			.headerText {
				font-size: 24px;
				line-height: 24px;
				font-weight: bold;
				text-align: center;				
			}
			
			.noTop { 
				border-top: none;
			}
			
			.font1 {
				background-color: red;
			}
			
			.font2 {
				background-color: #FFCC00;
			}
			
			.font3 {
				background-color: green;
			}
			
			.structText {
				font-size: 20px;
				line-height: 20px;
				font-weight: bold;
				font-family: ����_GB2312;
			}
			
			.wholeLevelRemark {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;				
			}
			
			.wholeLevel1 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: red;
			}
			
			.wholeLevel2 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: #FFCC00;
			}
			
			.wholeLevel3 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: green;
			}
			
			.structTable {
				border: 1px solid #000000;
				border-collapse:collapse;				
			}
			
			.structTable td {
				border: 1px solid #000000;	
				empty-cells: show;				
			}
			
			.nested {
				border: none;
				border-collapse:collapse;				
			}
			
			.structTable .boldText {
				font-weight: bold;
				text-align: center;
				word-break: keep-all;
			}
			
			.structTable .normalText {
				text-align: center;
			}
			
						
			.structTable .normalTextLevel1 {
				color: red;
				text-align: center;
			}
			
			.structTable .normalTextLevel1 a {
				color: red;
			}
			
			.structTable .normalTextLevel2 {
				color: #FFCC00;
				text-align: center;
			}
			
			.structTable .normalTextLevel2 a {
				color: #FFCC00;
			}
			
			.structTable .normalTextLevel3 {
				color: green;
				text-align: center;
			}
			
			.structTable .normalTextLevel3 a {
				color: green;
			}
			
			.structTable .level1 {
				background-color: red;
				text-align: center;
			}
			
			.structTable .level2 {
				background-color: #FFCC00;
				text-align: center;
			}
			
			.structTable .level3 {
				background-color: green;
				text-align: center;
			}
			
			.structTable .chart {
				vertical-align: bottom;
				text-align: center;
				border: none;
				padding-top: 2px;				
			}
			
			.structTable .chartCell {
				text-align: center;
				vertical-align:bottom;
				padding-bottom:2px;
			}
		</style>



<script>  
var contextPath='<%=request.getContextPath()%>';
//var subWinId = 'null';

   var lvAll=0;
   var hongAll=0;
   var huangAll=0;
   var znzslv=0;
   var znzshuang=0;
   var znzshong=0;

    Ext.onReady(function(){
    	//Ext.getCmp("tab1").show();//���������Ĭ����ʾ��һ��tabҳ
   	  
    	
    }); 
    
   <%--  function fac(sql){
    	var sql1 = sql.replace(/#/g,"'")
    	document.getElementById("sql").value=sql1
    	var pa="";
    	//$h.openWin('BackCheck','pages.templateconf.BackCheck&initParams='+sql1,'��Ȼ�ṹ�������', 800, 500,'', contextPath);
		window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BackCheck',window,'dialogWidth:900px; dialogHeight:528px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
    } --%>
      
      
      
</script>  
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));	
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	%>
	<style type="text/css">
	#PageBody{width:100%;}
/* 	#Sidebar{float:left;width:15%; border-right:5px solid #c3daf9;margin-top:-15px;} */
	#MainBody{float:right;width:100%;}
	
	
	.comments {  
 width:100%;/*�Զ���Ӧ�����ֿ��*/  
 height:100%;
 overflow:auto;  
 word-break:break-all;  
}  
	
	
	
</style> 

</head>
<body style="overflow: hidden;">

<odin:hidden property="gid"/>
<odin:hidden property="type"/>
<input type="hidden" name="typeid" id="typeid" value=""/>
<input type="hidden" name="jgtypeid" id="jgtypeid" value=""/>
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />





<input type="hidden" id="gid" name="gid" />
<input type="hidden" id="sql" name="sql" />
<input type="hidden" id="znzsvv" name="znzsvv" />
<div id="PageBody"> <!--ҳ������--> 
�������� <div id="MainBody" style=" "> <!--��������-->
<!--      <table > -->
<!-- 		<tr> -->
<!-- 		  <td > -->
		   <odin:tab id="tab" width="100%"  height="document.body.clientHeight" tabchange="grantTabChange">
		   <odin:tabModel>
		    <odin:tabItem title="&nbsp�ṹģ��&nbsp" id="tab1"></odin:tabItem>
		    <odin:tabItem title="&nbsp���ӳ�Ա������&nbsp" id="tab2"></odin:tabItem>
			<odin:tabItem title="&nbsp�ṹҪ�طֲ�����&nbsp" id="tab3" isLast="true"></odin:tabItem>
		   </odin:tabModel>
		   
		   
		   <odin:tabCont itemIndex="tab1">
		   <div id="jgmx" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="60">
					<td class="headerText"><span class="headerText" id="unit" name="unit"></span>�쵼���ӽṹģ�ͼ��Աȷ�����</td>
				</tr>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr height="32">
<!-- 					<td width="50">&nbsp;</td> -->
					<td class="structText" width="150">����״����<span id="ztzk" class="css1" "></span></td>
					<td align="left" class="wholeLevelRemark">&nbsp;</td>
					<td width="250" class="structText" style="text-align:right;">����ʱ��:<span id="cjsj" class="css1" "></span></td>
<!-- 					<td width="50">&nbsp;2018��7��</td> -->
				</tr>
			</table>
							
		    <table align="center" width="100%" overflow-y: scroll; cellpadding="0" cellspacing="0" class="structTable" >
		<tr height="32">
			<td class="boldText" align="center">һ��ְ��ְ��</td>
		</tr>
		<tr>
			<td>
				<table id="zsTable" style="width:100%;height:60px;" cellpadding="0" cellspacing="0" class="nested" > 
					<tr height="60">
						<td class="boldText" style="border-left:none;border-top:none;" width="60">��Ҫ<br/>ְ��</td>
						<td  id="cosp"  style="word-break:break-all;text-align:left;border-top:none;border-right:none;">
						 <span  style="word-break:break-all;text-align:left;border-top:none;border-right:none;" id="team_responsibility" name="team_responsibility" ></span>
						</td>
					</tr>
					<tr id="yp1" height="32">
						<td class="boldText" style="border-left:none;border-bottom:none;" width="60" rowspan="3">ְλ<br/>ְ��</td>
						<td class="boldText" width="60" rowspan="2">Ӧ��</td>
<!-- 						<td class='normalText zsTableTd' style='word-break:break-all;' id="yp1"> </td> -->
					</tr>
															
					<tr id="yp2" height="32">
<!-- 						<td class='normalText' style='word-break:break-all;' ></td> -->
					</tr>
					<tr id="sp" height="32">
						<td class="boldText" style="border-bottom:none;" width="60">ʵ��</td>
<%-- 						<td id="sp" class='normalTextLevel<s:property value="#result.level"/>' style='border-bottom:none;<s:if test="#st.index==(summaryList[0].resultList.size-1)">border-right:none;</s:if>'>	</td> --%>
					</tr>			
				</table>
			</td>									
		</tr>
		<tr height="32">
			<td class="boldText" align="center">������Ȼ�ṹ</td>
		</tr>
		<tr>
			<td>
				<table id="jgTable" style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="32" id="zr1">
						<td class="boldText" width="60" style="border-left:none;border-top:none;">��Ŀ</td>
<!-- 						<td class='boldText' style='word-break:all;border-top:none;border-right:none;' colspan=''>1</td> -->
					</tr>
					<tr height="32" id="zr2">
						<td class="boldText" style="border-left:none;">Ҫ��</td>
<!-- 							<td class='normalText jgTableTd' style='word-break: break-all;border-right:none;'>2</td> -->
					</tr>
					<tr height="32" id="zr3">
						<td class="boldText" style="border-left:none;">Ŀ��</td>
<!-- 								<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;border-right:none;'> -->
<!-- 								>=31 -->
<!-- 								</td> -->
								
					</tr>
					<tr height="32" id="zr4">
						<td class="boldText" style="border-left:none;">��״</td>
<%-- 								<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;<s:if test="#st.index==(summaryList.size-1)">border-right:none;</s:if>'> --%>
								
<!-- 								</td> -->
					</tr>
					<tr height="32" id="zr5">
						<td class="boldText" style="border-left:none;border-bottom:none;">�Ա�</td>
<%-- 						<td class='level<s:property value="#summary.summaryLevel"/>' style='border-bottom:none;<s:if test="#st.index==(summaryList.size-1)">border-right:none;</s:if>' colspan='<s:property value="#summary.resultList.size"/>'><s:property value="#summary.summaryLevelDesc"/></td> --%>
					</tr>
				</table>
			</td>									
		</tr>
		<tr height="32">
			<td class="boldText" align="center">�������ܽṹ</td>
		</tr>
		<tr>
			<td>
				<table id="posTable" style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="32" id="gn1">
						<td width="60" class="boldText" style="border-left:none;border-top:none;">ְ��<br/>���</td>
<!-- 						<td class='posTableTd' align="center" style='border-top:none;border-right:none;'>2</td> -->
					</tr>
					<tr height="60" id="gn2">
						<td class="boldText" style="border-left:none;">����<br/>ְλ</td>
<!-- 						<td align="center" style='border-right:none;'>3</td> -->
					</tr>
					<tr height="60" id="gn3">
						<td class="boldText" style="border-left:none;">�ʸ�<br/>����</td>
					</tr>
					<tr height="60" id="gn4">
						<td class="boldText" style="border-left:none;">�䱸<br/>���</td>
					</tr>
					<tr height="60" id="gn5">
						<td class="boldText" style="border-left:none;border-bottom:none;">�˸�<br/>����<br/>�ȷ�<br/>��</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr height="32">
			<td class="boldText" align="center">�ġ��������ۼ��Ż�����</td>
		</tr>
		<tr>
			<td>
				<table style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="60">
						<td width="60" class="boldText" style="border-left:none;border-top:none;">����<br/>����</td>
						<td style="text-align:left;border-right:none;border-bottom:none;border-top:none;">
						<span id="" name="">���Աȣ�</span>
						<span id="namejg" name="namejg"></span>
						<span id="" name="">�쵼��������ṹΪ</span>
						<span id="namecol" name="namecol"></span>
						<span id="hlqk" name="hlqk"></span>
						<br/><br/><span id="general_evaluation" name="general_evaluation"></span>
						</td>
					</tr>
					<tr height="60">
						<td class="boldText" style="border-left:none;border-bottom:none;">�Ż�<br/>����<br/>����<br/>ʩ</td>
						<td style="text-align:left;border-right:none;border-bottom:none;" id="optimize_direction" name="optimize_direction"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
		  </div>  
		   </odin:tabCont>
		   
		   
		   
		   <odin:tabCont itemIndex="tab2">
		   
		      <div id="jgmx1" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="50">
					<td class="headerText"><span class="headerText" id="unit1" name="unit1"></span>�쵼���ӳ�Ա������</td>
				</tr>
			</table>
		   <odin:editgrid property="TrainingInfoGrid" height="document.body.clientHeight-113" title="" autoFill="true" bbarId="pageToolBar" sm="row" remoteSort="true" hasRightMenu="true" rightMenuId="updateM" isFirstLoadData="false" pageSize="20"  url="/"  >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="a0192a" />
 			  		<odin:gridDataCol name="a0107"  /> 
 			  		<odin:gridDataCol name="a0111a"  /> 
 			  		<odin:gridDataCol name="qrzxl"  /> 
 			  		<odin:gridDataCol name="qrzxlxx"  /> 
 			  		<odin:gridDataCol name="zzxl"  /> 
 			  		<odin:gridDataCol name="zzxlxx"  /> 
 			  		<odin:gridDataCol name="a0196"  /> 
 			  		<odin:gridDataCol name="a0134"  /> 
 			  		<odin:gridDataCol name="a0288"  /> 
			  		<odin:gridDataCol name="a0192c" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="����" align="center" dataIndex="a0101" edited="false" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="����ְ��" align="center" dataIndex="a0192a" edited="false" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="��������" align="center" dataIndex="a0107" editor="text"  edited="false"  width="100" /> 
 				  	<odin:gridEditColumn2 header="����" align="center" dataIndex="a0111a" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="ȫ����ѧ��" align="center" dataIndex="qrzxl" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="ȫ����ѧλ" align="center" dataIndex="qrzxlxx" editor="text"  edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="��ְѧ��" align="center" dataIndex="zzxl" editor="text"  edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="��ְѧλ" align="center" dataIndex="zzxlxx" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="רҵ����ְ��" align="center" dataIndex="a0196" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="�μӹ���ʱ��" align="center" dataIndex="a0134" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="����ְ��ʱ��" align="center" dataIndex="a0288" editor="text" edited="false" width="100"/> 
				  	<odin:gridEditColumn2 width="65" header="�����Ƽ�ʱ��" dataIndex="a0192c" editor="text" edited="false"  isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid>
		   
		   </div>
		   </odin:tabCont>
		   
		   
		   <odin:tabCont itemIndex="tab3">
		    <div id="jgmx2" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="50">
					<td class="headerText"><span class="headerText" id="unit2" name="unit2"></span>�쵼���ӳ�Ա�ṹҪ�طֲ�����</td>
				</tr>
			</table>
			<table width="100%"  cellpadding="0" cellspacing="0" class="structTable" id="jgysmc" name="jgysmc">
				<tr height="32" id="jgmcww">
					<td class="boldText">&nbsp;</td>
	<!-- 									<td class="boldText" colspan="3"/>11</td>  -->
	<!-- 									<td class="boldText" colspan="4"/>11</td>  -->
	<!-- 									<td class="boldText" colspan="2"/>11</td>  -->
					
				</tr>
				
				<!-- ���� -->						
				<tr height="32" id="jgmc2">			
					<td class="boldText">&nbsp;</td>									
<!-- 									<td class="normalText" style="word-break: keep-all;">2</td> -->
				</tr>
				
<!-- 				<tr height="32" id="jgmc3"> -->
<!-- 					<td class="normalText"><a href="###" title="����鿴�������" onclick="viewRemoval();">3</a></td> -->
<!-- 					<td class="normalText"></td> -->
<!-- 				</tr> -->
			</table>
			</table>
			</div>
		   </odin:tabCont>
		   </odin:tab>
		   </div>
		   </div>
		   
		   
<!-- 		  </td> -->
<!-- 		</tr> -->
<!-- 		</table> -->
		
������<script>  
Ext.onReady(function(){
	var pgrid = Ext.getCmp('TrainingInfoGrid');
	var bbar = pgrid.getBottomToolbar();
	bbar.insertButton(11,[
	                      new Ext.menu.Separator({cls:'xtb-sep'}),
							new Ext.Button({
								icon : 'images/icon/table.gif',
								id:'expExcelFromGrid',
							    text:'����Excel',
							    handler:expExcelFromGrid
							    
							    
							})
							
							]); 
});


	var ht=document.body.clientHeight-5;
Ext.onReady(function(){
	$('#Sidebar').height(ht);
	$('#MainBody').height(ht);
	$('#jgmx').height(document.body.clientHeight-50);
	$('#jgmx1').height(document.body.clientHeight);
	$('#jgmx2').height(document.body.clientHeight);
	var vwin = window.dialogArguments; //�õ�window����
	var gid = vwin.document.getElementById("gid").value;
    document.getElementById("gid").value=gid;
    lvAll = 0;
 	  hongAll=0;
 	  huangAll=0;
 	  znzslv=0;
 	  znzshuang=0;
 	  znzshong=0;
    radow.doEvent('Znzs',gid);//ְ��ְ��
	 radow.doEvent('Zwsm',gid);//ְλ˵��
	 radow.doEvent('zrjg',gid);//��Ȼ�ṹ������ʾ////alert(lvAll);
	 radow.doEvent('showtype',gid);//�ۺ�����
	 $('#gid').val(gid);
	 
	 radow.doEvent('hmc',gid);
	 comprevalue();
    	
    });
var lvAll=0;
var hongAll=0;
var huangAll=0;
var znzslv=0;
var znzshuang=0;
var znzshong=0;
function comprevalue(parem){//�����ۺ����ñ�
   var gid = document.getElementById("gid").value;
    var sumcol = lvAll*1+hongAll*1+huangAll*1;//��Ȼ�ṹȫ����ɫ
    var znzssumcol = znzslv*1+znzshuang*1+znzshong*1;//ֻ��ְ��ȫ����ɫ
    var sumAll = sumcol*1+znzssumcol*1;
    var lvbAll=lvAll*1+znzslv*1;//��Ȼ�ṹ��ֻ��ְ��ȫ����ɫ
    var lvbAll2= Math.round(lvbAll*1 / sumAll * 10000) / 100.00;
	if(lvbAll2>=100){
		document.getElementById("ztzk").innerText='��';//����״���ı�
	    document.getElementById("ztzk").style.background = 'green';//����״����ɫ
	    document.getElementById("namecol").innerText='��';
	    document.getElementById("namecol").style.background = 'green';
	    document.getElementById("hlqk").innerText='�����ṹ�ȽϺ�����쵼���ӡ�';
	    var statustype = "3";
 	    var gs = gid+","+statustype;
 	    radow.doEvent("ztzk",gs);//����״��
	}else if(lvbAll2>=50&&lvbAll2<100){
		document.getElementById("ztzk").innerText='��';//����״���ı�
	    document.getElementById("ztzk").style.background = 'Gold';//����״����ɫ
	    document.getElementById("namecol").innerText='��';
	    document.getElementById("namecol").style.background = 'Gold';
	    document.getElementById("hlqk").innerText='�����ṹ����������쵼���ӡ�';
	    var statustype = "2";
	    var gs = gid+","+statustype;
	    radow.doEvent("ztzk",gs);//����״��
	}else if(lvbAll2<50){
		document.getElementById("ztzk").innerText='��';//����״���ı�
	    document.getElementById("ztzk").style.background = 'red';//����״����ɫ
	    document.getElementById("namecol").innerText='��';
	    document.getElementById("namecol").style.background = 'red';
		document.getElementById("hlqk").innerText='�����ṹ���ز�������쵼���ӡ�';
	    var statustype = "1";
 	    var gs = gid+","+statustype;
 	    radow.doEvent("ztzk",gs);//����״��
	}
	document.getElementById("namejg").innerText=paremarr[0];//����״���ı�
   if(parem!="error"){
	   var paremarr = parem.split(",");
    	 document.getElementById("unit").innerHTML=paremarr[0];
    	 document.getElementById("team_responsibility").innerHTML=paremarr[2];
    	 document.getElementById("general_evaluation").innerHTML=paremarr[3];
    	 document.getElementById("optimize_direction").innerHTML=paremarr[4];
   }
	
	
}

function znzsvalue(person,creattime){//����ְ��ְ��td
	var creat = creattime.substring(0,4)+"��"+creattime.substring(4)+"��";
	   document.getElementById("cjsj").innerText=creat;//����ʱ��
	   var personAll = person.split("&");
	      var cosp= personAll.length;
	      $("#yp1").empty();
	      $("#yp2").empty();
	      $("#sp").empty();
	      var aaa="<td class='boldText' style='border-left:none;border-bottom:none;' width='60' rowspan='3'>ְλ<br/>ְ��</td>";
		var  aa="<td class='boldText' width='60' rowspan='2'>Ӧ��</td>";
			$("#yp1").append(aaa);
			$("#yp1").append(aa);
			$("#sp").append("<td class='boldText' style='border-bottom:none;' width='60'>ʵ��</td>");
	      $("#cosp").attr("colspan",cosp+1);
	      
	   for ( var i = 0; i <personAll.length; i++){
		    var aa=personAll[i];//1,5
		    var aaArr=aa.split("@@@");
		    var sql=aaArr[1]; 
		    var parem = aaArr[0].split(",");
		    var b =Math.ceil(parem[1]*1/2);
		    $("#yp1").append("<td class='normalText zsTableTd' style='word-break:break-all;'>"+parem[0]+"</td>");
		    $("#yp2").append("<td class='normalText' style='word-break:break-all;' >"+parem[1]+"</td>");
		    if(parem[6]*1>=parem[1]*1&&parem[5]*1=='1'){
		    	++znzslv
		    	  $("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[6]+"</font></a></td>");	
		    } else if(parem[6]*1>=b&&parem[6]*1<parem[1]*1&&parem[5]*1=='1'){
		    	++znzshuang
		    	 $("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[6]+"</font></a></td>");
		    }else if(parem[6]*1<b&&parem[5]*1=='1'){
		    	++znzshong
		    	$("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[6]+"</font></a></td>");
		    }else if(parem[6]*1<parem[1]*1&&parem[5]*1=='0'){
		    	++znzshong
		    	$("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[6]+"</font></a></td>");
		    }
		  
		}
	 
}

function zrjg(prame){//��Ȼ�ṹ����td
	var personAll = prame.split("$");
//alert(personAll);
    var lv = 0;
    var huang = 0;
    var hong = 0;
    var flag = "";//�Ƿ�һƱ�����yesΪ��
    
	 for ( var i = 0; i <personAll.length; i++){
		    var aa=personAll[i];
		     //alert(aa);
		    var aaArr=aa.split("@&&&");
		    var sql=aaArr[1]; 
		    var parem = aaArr[0].split(",");
		    var pra = parem[1].replace("&","");
		    var parll = pra.substring(2);
		    var fuh = pra.substring(0,1);//>�Ż�<��
		   var b =Math.ceil(parll/2);
		   var aaa=parem[3];
		  // var abcd = '"a01.a0000 = MODELCONFIG.id and a02.a0201b = 001.001.002.001.004) asd.tenuresystem = 01"';
		 // var abcd ='"select * from (select distinct a01.a0000,a01.a0101,a01.a0104,a01.a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.tenuresystem from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = 001.001.002.001.004) asd where asd.tenuresystem = 01"';
		   //alert(aaa);
		    $("#zr2").append("<td class='normalText jgTableTd' style='word-break: break-all;border-right:none;'>"+parem[2]+"</td>");
		    $("#zr3").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;border-right:none;'>"+pra+"</td>");
		    if(parem[4]*1>=parll*1&&fuh==">"){
		    	++lv;
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[4]+"</font></a></td>"); 
		    }else if(parem[4]>=b&&parem[4]<parll*1&&fuh==">"){
		    	++huang;
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[4]+"</font></a></td>"); 
		    }else if(parem[4]*1>=parll*1&&fuh=="<"){
		    	++lv;
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[4]+"</font></a></td>");
		    }else if(parem[4]>=b*2&&parem[3]>parll*1&&fuh=="<"){
		    	++huang;
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[4]+"</font></a></td>");
		    }else if(parem[4]<b&&fuh==">"&&aaa=="0"){
		    	flag="yes";
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
		    }else if(parem[4]>b&&fuh=="<"&&aaa=="0"){
		    	flag="yes";
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
		    }else{
		    	++hong;
		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
		    }
		}   
	 var type=personAll[0].split(",")[0];
	 if(type=="nl"){
		 type="����ṹ";
	 }else if(type=="xl"){
		 type="ѧ������";
	 }else if(type=="zy"){
		 type="רҵ�ṹ";
	 }else if(type=="xb"){
		type="�Ա�ṹ"; 
	 }else if(type=="dp"){
		 type="���ɽṹ";
	 }else if(type=="mz"){
		 type="����ṹ";
	 }else if(type=="ly"){
		 type="��Դ�ṹ";
	 }else if(type=="zc"){
		 type="ר���ṹ";
	 }else if(type=="dy"){
		 type="����ṹ";
	 }else if(type=="nel"){
		 type="�����ṹ";
	 }else if(type=="jy"){
		 type="����ṹ";
	 }else if(type=="jl"){
		 type="�����ṹ";
	 }else if(type=="rqz"){
		 type="������";
	 }
	 var len=personAll.length;
	 var col = lv*1+hong*1+huang*1;
	 //alert(col);
	var lv1= Math.round(lv*1 / col * 10000) / 100.00;
	var hong1 = Math.round(hong*1 / col * 10000) / 100.00;
	var huang1 = Math.round(huang*1 / col * 10000) / 100.00;
	 $("#zr1").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' colspan='"+len+"'>"+type+"</td>");
	 if(flag=="yes"){
		 ++hongAll;
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>");
	 }else if(lv1<50){
		 ++hongAll
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>");
	 }else if((lv1>=50)&&(lv1<100)){
		 ++huangAll;
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='Gold' colspan='"+len+"'></td>");
	 }else if(lv1>=100){
		 ++lvAll;
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='green' colspan='"+len+"'></td>");
	 }else if(huang1>=100){
		 ++huangAll;
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='Gold' colspan='"+len+"'></td>");
	 }else if(hong1>=100){
		 ++hongAll;
		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>"); 
	 }
 	 
}
function zwsmvalue(person,sls){//����ְλ˵��td
	   var personAll = person.split("$");
	   for ( var i = 0; i <personAll.length; i++){
		    //parem[0] parem[1] parem[4]
		    var aa=personAll[i];
		    var parem = aa.split("@");
		    $("#gn1").append("<td class='posTableTd' align='center' style='border-top:none;border-right:none;'>"+parem[1]+"</td>");
		    $("#gn2").append("<td align='center' style='border-right:none;'>"+parem[0]+"</td>");
		    $("#gn3").append("<td align='center' style='border-right:none;'>"+parem[2]+"</td>");
		    $("#gn4").append("<td align='center' style='border-right:none;'>"+parem[3]+"</td>");
		   
		}
	      var tjsls= sls.split("@");
	      for ( var i = 0; i <tjsls.length; i++){
	    	  var aa=tjsls[i];
			  var sls = aa.split(",");
	    	  $("#gn5").append("<td align='center' style='border-right:none;border-bottom:none;'>"+sls+"</td>");
	      }
    
}

function dogrid(jgname){
	 // alert(jgname);
	  document.getElementById("unit1").innerHTML=jgname;
	  document.getElementById("unit2").innerHTML=jgname;
	  
 }
 
  function fac(sql){
	var sql1 = sql.replace(/#/g,"'")
	document.getElementById("sql").value=sql1
	var pa="";
	//$h.openWin('BackCheck','pages.templateconf.BackCheck&initParams='+sql1,'��Ȼ�ṹ�������', 800, 500,'', contextPath);
	window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BackCheck',window,'dialogWidth:900px; dialogHeight:528px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
} 
  function expExcelFromGrid(){
		var excelName = null;
		//excel�������Ƶ�ƴ�� 
		var pgrid = Ext.getCmp('TrainingInfoGrid');
		var dstore = pgrid.getStore();
		excelName = "������Ϣ" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
		
		Ext.grid.menu.expExcelFromGrid('TrainingInfoGrid', excelName, null,null, false);
		alert(1);
		
	} 
   function grantTabChange(tabObj,item){
  	var tab = item.getId();
  	var gid= document.getElementById("gid").value;
  	//tab="1";
  	if(tab=="tab2"){
  		 radow.doEvent('TrainingInfoGrid.dogridquery');
  		 radow.doEvent('hmc',gid);
  	}else if(tab=="tab3"){
  		$('#jgysmc tr:gt(1)').remove();
   		 $("#jgmc2").empty();
       	 $("#jgmcww").empty();
       	  $("#jgmc2").append("<td class='boldtext'>&nbsp;</td>");
       	  $("#jgmcww").append("<td class='boldtext'>&nbsp;</td>");
  		radow.doEvent('jgysmc',gid);//��Ȼ�ṹ������ʾ
  		radow.doEvent('hmc',gid);
  	}
  }
  
  function zrjg1(prame){//�ṹҪ�طֲ�����
  	var personAll = prame.split("$");
  	 for ( var i = 0; i <personAll.length; i++){
		    //parem[0] parem[1] parem[4]
		    var aa=personAll[i];
		    var parem = aa.split(",");
		    var pra = parem[2].replace("&","");
		    $("#jgmc2").append("<td class='normalText' style='word-break: keep-all;'>"+parem[1]+"</td>");
		    
		    
		}   
  	 var type=personAll[0].split(",")[0];
  	 if(type=="nl"){
  		 type="����ṹ";
  	 }else if(type=="xl"){
  		 type="ѧ������";
  	 }else if(type=="zy"){
  		 type="רҵ�ṹ";
  	 }else if(type=="xb"){
  		type="�Ա�ṹ"; 
  	 }else if(type=="dp"){
  		 type="���ɽṹ";
  	 }else if(type=="mz"){
  		 type="����ṹ";
  	 }else if(type=="ly"){
  		 type="��Դ�ṹ";
  	 }else if(type=="zc"){
  		 type="ר���ṹ";
  	 }else if(type=="dy"){
  		 type="����ṹ";
  	 }else if(type=="nel"){
  		 type="�����ṹ";
  	 }else if(type=="jy"){
  		 type="����ṹ";
  	 }else if(type=="jl"){
  		 type="�����ṹ";
  	 }else if(type=="rqz"){
  		 type="������";
  	 }
  	 var len=personAll.length;
  	 //$("#jgmc1").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' colspan='"+len+"'>"+type+"</td>");
  	 //var tdnode=document.getElementById("jgmcww").childNodes;
  	 //var lent=tdnode.length;
  	 var tr=document.getElementById("jgmcww");
  	 var td = tr.insertCell();
  	 td.setAttribute("colSpan",len+"");
  	 //td.setAttribute("class","boldText");
  	 td.className="boldText";
  	 td.innerText=type;
  	 //alert(ryname+"================="+count);
  	 var lengt = len+1;
  }
  function rynn(name,count){
	  //var ry=document.getElementById("ryname").value;
	   // alert(ry);
	    	//var count=document.getElementById("count").value;
	    	//alert(count);
	    	var name = name.replace(/Y/g,"��").replace(/N/g," ");
	    	//var name=name.replace("Y","1").replace("N"," ");
	        var count=parseInt(count)+1;
	    	var ryname = name.split("$");
	    	if(ryname!="a" && count !=1){
		    	for ( var j = 0; j <ryname.length; j++){
		    		var dd=ryname[j];
		    		var ddarr=dd.split(',');
		    		var name=ddarr.shift();
		    		 var table=document.getElementById("jgysmc");//��ȡtable
		    		var tr= table.insertRow();
		    		 for ( var i = 0; i <count; i++){
		    			 var td=tr.insertCell();
		    			 td.className="normalText";
		    			 if(i==0){
		    				 td.innerText=name;
		    			 }else{
		    				 td.innerText=ddarr[i-1]; 
		    			 }
		    			 
		    			 
		    		 }
		    		 //var td = tr.insertCell();
	 		       	 //td.setAttribute("colSpan",len+"");
	 		       	 //td.setAttribute("class","boldText");
		    	}
		    } 
	  
  } 
    
</script> 

</body>
</html>