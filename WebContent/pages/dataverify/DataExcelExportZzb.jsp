<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp"%>
<%
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
	String a00 = "['1','�ۺϹ��������Ա'],['2','רҵ���������Ա']['3','�������������Ա']['4','��ҵ��λ������Ա']['5','��ҵ��λ������Ա5']";
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.business.entity.CodeValue"%>
<link rel="stylesheet" type="text/css"
	href="/hzb/basejs/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css" />
<link rel="stylesheet" type="text/css"
	href="/hzb/basejs/ext/ux/css/Ext.ux.form.LovCombo.css" />
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
#tableid1 {
	position: relative;
	left: 10px;
	top: 10px;
}
</style>

<script type="text/javascript">
//��ٽ�����
function ShowCellCover1(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "�ر�";
	   	if(elementId.indexOf("start") != -1){
	   		Ext.MessageBox.show({
	   			title:titles,
	   			msg:msgs,
	   			width:300,
	   	        height:300,
	   			closable:false,
	   			modal:true,
	   			progress:true,
	   			wait:true,
	   			animEl: 'elId',
	   			increment:5, 
	   			waitConfig: {interval:150}
	   		});
	   	}
}
function startImport(){
	
	   var exptemplate=document.getElementById("exptemplate").checked;
	   var exptunit=document.getElementById("exptunit").checked;
	   var expsy=document.getElementById("expsy").checked;
	   var exppicture=document.getElementById("exppicture").checked;
	
	   var fxzry =document.getElementById("fxzry").value; //����ְ��Ա             
	   var zdcjg = document.getElementById("zdcjg").value; //ֻ����������Ϣ           
	   var gzlbry = document.getElementById("gzlbry").value; //���й�����Ա���        
	   var gllbry = document.getElementById("gllbry").value;//���й�����Ա���        
	   var searchDeptid = document.getElementById("searchDeptid").value;//����id
	   var linkpsn = document.getElementById("linkpsn").value;//��ϵ��           
	   var linktel = document.getElementById("linktel").value;//��ϵ�绰          						pages\dataverify\DataExcelExport.jsp
	   if(searchDeptid==null||searchDeptid.length==0&&exptemplate==false){
		   alert("��ѡ�����!");
		   return;
	   }
	   try{
		   ShowCellCover1('start','ϵͳ��ʾ','���ڵ�������,�����Ե�...');
	   }catch(err){
		   
	   }
	   var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.dataverify.DataExcelExportZzb&eventNames=expbtn_onclick';
	   //radow.doEvent("expbtn_onclick");
	   Ext.Ajax.request({
	   		timeout: 9000000,
	   		url: path,
	   		async: true,
	   		method :"post",
	   		params : {
				'fxzry':fxzry,
				'zdcjg':zdcjg,
				'gzlbry':gzlbry,
				'gllbry':gllbry,
				'searchDeptid':searchDeptid,
				'linkpsn':linkpsn,
				'linktel':linktel,
				'exptemplate':exptemplate,
				'exptunit':exptunit,
				'expsy':expsy,
				'exppicture':exppicture
	   		},
	           callback: function (options, success, response) {
	        	   if (success) {
	        		   var result = response.responseText;
	   					if(result){
	   						Ext.Msg.hide();
	   						var json = eval('(' + result + ')');
	   						var data_str=json.data;
	   						var data_str=data_str.split('@@@');
	   						if(data_str[0]=='2'){//�ɹ�
	   							alert("�����ɹ�!");
	   							//alert(arr[1]);
	   						 	var curWwwPath_=window.document.location.href;   
	 						    //��ȡ������ַ֮���Ŀ¼���磺 /myproj/view/my.jsp 
	 						    var pathName_=window.document.location.pathname;  
	 						    var pos_=curWwwPath_.indexOf(pathName_);  
	 						    //��ȡ������ַ���磺 http://localhost:8083  
	 						    var localhostPaht_=curWwwPath_.substring(0,pos_);
	 						    //��ȡ��"/"����Ŀ�����磺/myproj
	 						    var projectName_=pathName_.substring(0,pathName_.substr(1).indexOf('/')+1);
	 						    //�õ��� http://localhost:8083/myproj  
	 						    var realPath_=localhostPaht_+projectName_;
	 						   // console.log(realPath_+'/ProblemDownServlet?method=downFile&prid='+encodeURI(encodeURI(arr[1])));
	 						   //URLEncoder.encode(arr[1], "utf-8")
	 							window.location.href=realPath_+'/ProblemDownServlet?method=downFileExcelry&prid='+encodeURI(encodeURI(data_str[1]));
	   							//window.parent.getListInfo();
	 							//parent.Ext.getCmp(subWinId).close();
	   						}else if(data_str[0]=='1'){//ʧ��
	   							//parent.radow.doEvent('expInfogrid.dogridquery');
	   							alert(data_str[1]);
	   							//parent.Ext.getCmp(subWinId).close();
	   						}
	   						Ext.Msg.hide();
	   					}
	        	   }
	           }
	      }); 
}
	Ext.onReady(function(){
		try{
			var dom_arr=document.getElementsByTagName("input");//��ȡ���е�input
			for(i=0;i<dom_arr.length;i++){//ѭ�����е�input
				if(dom_arr[i].type=='checkbox'){//�ж��Ƿ�Ϊcheckbox��ѡ��
					checkboxvl(dom_arr[i].id);
				}
			}
		}catch(err){
			
		}
		
	});
	function checkboxvl(id){
	    try{
	      var node_66;
	      var node_55;
	      var node_5_height;
	      var node_6_height;
	      node_66=$("#"+id).parent();
	      node_6_height=node_66[0].offsetHeight;
	      node_55=node_66.children();
	      node_5_height=node_55[0].offsetHeight;
	      var mt=(parseInt(node_6_height)-parseInt(node_5_height))/2;
	      $("#"+id).parent().parent().parent().css({"top": mt+"px"});
	      $("#"+id).parent().parent().css({"top": mt+"px"});
		}catch(err){
		}
	}
</script>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text="<h3>&nbsp;����������2000������</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="����" id="expbtn" handler="startImport" icon="images/icon/exp.png"
		isLast="true" />
</odin:toolBar>
<div id="panel_content" align="center">
	<div id="toolDiv"></div>
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<odin:hidden property="picType" value="<%=picType%>" />
	<odin:hidden property="tabimp" />
	<odin:hidden property="searchDeptid" />
	<table id="tableid1" style="width: 600px;" border ='0' >
		<tr height="6px">
			<td colspan="4"> </td>
		</tr>
		<tr>
			<tags:PublicTextIconEdit4 onchange="setParentValue" colspan="4"
				width="478" property="searchDeptBtn" label="ѡ�����" readonly="true"
				codetype="orgTreeJsonData" />
		</tr>
		<tr>
			<odin:textEdit property="linkpsn" colspan="1" width="180" label="��ϵ��" />
			<odin:textEdit property="linktel" width="180" colspan="1"
				label="��ϵ�绰" />
		</tr>
		<tr>
			<td>
			<odin:checkbox property="exptemplate" label="����ģ��" onclick="expTemplateFunc(this)"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="exptunit" label="������λ"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="expsy" label="������Ա"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="exppicture" label="����ͼƬ"></odin:checkbox>
			</td>
		</tr>
		<tr style="display:none;">
			<td colspan="4">
				<table>
					<tr>
						<td width="20"></td>
						<td><odin:checkbox property="zdcjg" onclick="onzdcjg();"
								label="ֻ����������Ϣ"></odin:checkbox></td>
						<td width="220"></td>
						<td></td>
					</tr>
					<tr id="allchoose">
						<td width="20"></td>
						<td><odin:checkbox property="gzlbry" onclick="ongzlb();"
								label="���й�����Ա���"></odin:checkbox></td>
						<td></td>
						<td><odin:checkbox property="gllbry" onclick="ongllb();"
								label="���й�����Ա���"></odin:checkbox></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="display:none;">
			<td colspan="4">
				<table style="width: 550px;">
					<tr>
						<td width="340px">
							<div id="gzlb" style="float: left;">
								<odin:groupBox title="������Ա���">
									<table>
										<tr>
											<td valign="top">
												<%
													List<CodeValue> list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
																			" codeType='ZB125' order by codeValue").list();
																		if(list!=null && list.size()>0){
																			int gzs = list.size();
																			int lefts = (gzs+1)/2;
																			for(int i=0;i<lefts;i++){
																				CodeValue code = list.get(i);
																				int k = i + 1;
																				String codename= code.getCodeName();
																				String ddd="gz_"+k;
												%> <odin:checkbox property="<%=ddd%>" label="<%=codename%>"></odin:checkbox>
												<%
													}
												%>
											</td>
											<td valign="top">
												<%
													for(int j=lefts;j<gzs;j++){
																				CodeValue code = list.get(j);
																				int k = j + 1;
																				String codename= code.getCodeName();
																				String ddd="gz_"+k;
												%> <odin:checkbox property="<%=ddd%>" label="<%=codename%>"></odin:checkbox>
												<%
													}
																		}
												%>
											</td>
										</tr>
									</table>
								</odin:groupBox>
							</div>
						</td>
						<td width="10px"></td>
						<td width="190px" valign="top">
							<div id="gllb" style="float: left;">
								<odin:groupBox title="������Ա���">
									<%
										List<CodeValue> list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
																" codeType='ZB130' order by codeValue").list();
															if(list2!=null && list2.size()>0){
																int gzs = list2.size();
																for(int i=0;i<gzs;i++){
																	CodeValue code = list2.get(i);
																	int k = i + 1;
																	String codename= code.getCodeName();
																	String ddd="gl_"+k;
									%>
									<odin:checkbox property="<%=ddd%>" label="<%=codename%>"></odin:checkbox>
									<%
										}
															}
									%>
								</odin:groupBox>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="display:none;">
			<td colspan="4">
				<table id="lslt">
					<tr>
						<td width="20px"></td>
						<td><odin:checkbox property="fxzry" label="����ְ��Ա"></odin:checkbox>
						</td>
						<td width="170px"></td>
						<odin:hidden property="gjgs" value="1" />
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350"
	maximizable="false" title="����"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350"
	maximizable="false" title="����">
</odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960"
	height="500" title="��ϢУ��" modal="true" />
<odin:window src="/blank.htm" id="refreshWin" width="550" height="400"
	maximizable="false" title="����" />
<script>
function onzdcjg(){
	var gzlb = odin.ext.getCmp('zdcjg').getValue();
	if(gzlb==1){
		document.getElementById("gzlb").disabled=true;
		document.getElementById("gllb").disabled=true;
		document.getElementById("lslt").disabled=true;
		document.getElementById("allchoose").disabled=true;
	} else {
		document.getElementById("lslt").disabled=false;
		document.getElementById("allchoose").disabled=false;
		var gzlb = odin.ext.getCmp('gzlbry').getValue();
		var gllb = odin.ext.getCmp('gllbry').getValue();
		if(gzlb==0){
			document.getElementById("gzlb").disabled=false;
		}
		if(gllb==0){
			document.getElementById("gllb").disabled=false;
		}
	}
}

function ongzlb(){
	var gzlb = odin.ext.getCmp('gzlbry').getValue();
	
	if(gzlb==1){
	<%List<CodeValue> list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
		" codeType='ZB125' order by codeValue").list();
	if(list!=null && list.size()>0){
		int gzs = list.size();
		for(int i=0;i<gzs;i++){
			int k = i + 1;
			String ddd="gz_"+k;%>
		odin.ext.getCmp('<%=ddd%>').setValue(1);	
	<%}
	}%>	
		document.getElementById("gzlb").disabled=true;
	} else {
		<%if(list!=null && list.size()>0){
		int gzs = list.size();
		for(int i=0;i<gzs;i++){
			int k = i + 1;
			String ddd="gz_"+k;%>
		odin.ext.getCmp('<%=ddd%>').setValue(0);	
	<%}
	}%>
		document.getElementById("gzlb").disabled=false;	
	}
}
function ongllb(){
	var gllb = odin.ext.getCmp('gllbry').getValue();
	if(gllb==1){
	<%List<CodeValue> list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
									" codeType='ZB130' order by codeValue").list();
								if(list2!=null && list2.size()>0){
									int gzs = list2.size();
									for(int i=0;i<gzs;i++){
										int k = i + 1;
										String ddd="gl_"+k;%>
		odin.ext.getCmp('<%=ddd%>').setValue(1);	
		<%}
	}%>
		document.getElementById("gllb").disabled=true;	
		
	} else {
	<%if(list2!=null && list2.size()>0){
									int gzs = list2.size();
									for(int i=0;i<gzs;i++){
										int k = i + 1;
										String ddd="gl_"+k;%>
		odin.ext.getCmp('<%=ddd%>').setValue(0);
<%}
	}%>
	document.getElementById("gllb").disabled = false;
		}
	}
	//a01ͳ�ƹ�ϵ���ڵ�λ
	function setParentValue(record, index) {
		document.getElementById('searchDeptid').value = record.data.key;
	}

	/* function reloadTree() {
		setTimeout(xx, 1000);
	}
	function xx() {
		var downfile = document.getElementById('downfile').value;
		w = window.open("ProblemDownServlet?method=downFile&prid="
				+ encodeURI(encodeURI(downfile)));
		setTimeout(cc, 3000);
	}
	function cc() {
		w.close();
	} */
	function grantTabChange(tabObj, item) {
		try{
			if (item.getId() == 'tab1') {
				odin.ext.getCmp('tabimp').view.refresh(1);
			}
			if (item.getId() == 'tab2') {
				odin.ext.getCmp('tabimp').view.refresh(2);
			}
		}catch(err){
			
		}
		
	}
	Ext.onReady(function() {
				//ҳ�����
				document.getElementById("panel_content").style.width = document.body.clientWidth
						+ "px";
				document.getElementById("toolDiv").style.width = document.body.clientWidth
						+ "px";
				document.getElementById("gzlbry").click();
				document.getElementById("gllbry").click();
				document.getElementById("fxzry").click();
			});

	var personTabsId = [];
	function addTab(atitle, aid, src, forced, autoRefresh) {
		try{
			var tab = parent.tabs.getItem(aid);
			if (forced)
				aid = 'R' + (Math.random() * Math.random() * 100000000);
			if (tab && !forced) {
				parent.tabs.activate(tab);
				if (typeof autoRefresh != 'undefined' && autoRefresh) {
					document.getElementById('I' + aid).src = src;
				}
			} else {
				src = src + '&' + Ext.urlEncode({
					'a0000' : aid
				});
				personTabsId.push(aid);
				parent.tabs.add(
								{
									title : (atitle),
									id : aid,
									tabid : aid,
									personid : aid,
									html : '<Iframe width="100%" height="100%" scrolling="auto" id="I'
											+ aid
											+ '" frameborder="0" src="'
											+ src
											+ '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
									listeners : {//�ж�ҳ���Ƿ���ģ�

									},
									closable : true
								}).show();

			}
		}catch(err){
			
		}
	}

	function expTemplateFunc(obj){
		if(obj.checked==true){//
			document.getElementById("exptunit").checked=false;
			document.getElementById("expsy").checked=false;
			document.getElementById("exppicture").checked=false;
			document.getElementById("exptunit").disabled=true;
			document.getElementById("expsy").disabled=true;
			document.getElementById("exppicture").disabled=true;
		}else if(obj.checked==false){//��ʾȫ����
			document.getElementById("exptunit").disabled=false;
			document.getElementById("expsy").disabled=false;
			document.getElementById("exppicture").disabled=false;
		}
	}
</script>