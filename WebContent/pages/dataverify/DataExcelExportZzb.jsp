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
	String a00 = "['1','综合管理类管理员'],['2','专业技术类管理员']['3','行政管理类管理员']['4','事业单位管理人员']['5','事业单位管理人员5']";
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
//虚假进度条
function ShowCellCover1(elementId, titles, msgs){	
	   	Ext.MessageBox.buttonText.ok = "关闭";
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
	
	   var fxzry =document.getElementById("fxzry").value; //非现职人员             
	   var zdcjg = document.getElementById("zdcjg").value; //只导出机构信息           
	   var gzlbry = document.getElementById("gzlbry").value; //所有工作人员类别        
	   var gllbry = document.getElementById("gllbry").value;//所有管理人员类别        
	   var searchDeptid = document.getElementById("searchDeptid").value;//机构id
	   var linkpsn = document.getElementById("linkpsn").value;//联系人           
	   var linktel = document.getElementById("linktel").value;//联系电话          						pages\dataverify\DataExcelExport.jsp
	   if(searchDeptid==null||searchDeptid.length==0&&exptemplate==false){
		   alert("请选择机构!");
		   return;
	   }
	   try{
		   ShowCellCover1('start','系统提示','正在导出数据,请您稍等...');
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
	   						if(data_str[0]=='2'){//成功
	   							alert("导出成功!");
	   							//alert(arr[1]);
	   						 	var curWwwPath_=window.document.location.href;   
	 						    //获取主机地址之后的目录，如： /myproj/view/my.jsp 
	 						    var pathName_=window.document.location.pathname;  
	 						    var pos_=curWwwPath_.indexOf(pathName_);  
	 						    //获取主机地址，如： http://localhost:8083  
	 						    var localhostPaht_=curWwwPath_.substring(0,pos_);
	 						    //获取带"/"的项目名，如：/myproj
	 						    var projectName_=pathName_.substring(0,pathName_.substr(1).indexOf('/')+1);
	 						    //得到了 http://localhost:8083/myproj  
	 						    var realPath_=localhostPaht_+projectName_;
	 						   // console.log(realPath_+'/ProblemDownServlet?method=downFile&prid='+encodeURI(encodeURI(arr[1])));
	 						   //URLEncoder.encode(arr[1], "utf-8")
	 							window.location.href=realPath_+'/ProblemDownServlet?method=downFileExcelry&prid='+encodeURI(encodeURI(data_str[1]));
	   							//window.parent.getListInfo();
	 							//parent.Ext.getCmp(subWinId).close();
	   						}else if(data_str[0]=='1'){//失败
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
			var dom_arr=document.getElementsByTagName("input");//获取所有的input
			for(i=0;i<dom_arr.length;i++){//循环所有的input
				if(dom_arr[i].type=='checkbox'){//判断是否为checkbox复选框
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
	<odin:textForToolBar text="<h3>&nbsp;适用人数：2000人以下</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="导出" id="expbtn" handler="startImport" icon="images/icon/exp.png"
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
				width="478" property="searchDeptBtn" label="选择机构" readonly="true"
				codetype="orgTreeJsonData" />
		</tr>
		<tr>
			<odin:textEdit property="linkpsn" colspan="1" width="180" label="联系人" />
			<odin:textEdit property="linktel" width="180" colspan="1"
				label="联系电话" />
		</tr>
		<tr>
			<td>
			<odin:checkbox property="exptemplate" label="导出模板" onclick="expTemplateFunc(this)"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="exptunit" label="导出单位"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="expsy" label="导出人员"></odin:checkbox>
			</td>
			<td>
			<odin:checkbox property="exppicture" label="导出图片"></odin:checkbox>
			</td>
		</tr>
		<tr style="display:none;">
			<td colspan="4">
				<table>
					<tr>
						<td width="20"></td>
						<td><odin:checkbox property="zdcjg" onclick="onzdcjg();"
								label="只导出机构信息"></odin:checkbox></td>
						<td width="220"></td>
						<td></td>
					</tr>
					<tr id="allchoose">
						<td width="20"></td>
						<td><odin:checkbox property="gzlbry" onclick="ongzlb();"
								label="所有工作人员类别"></odin:checkbox></td>
						<td></td>
						<td><odin:checkbox property="gllbry" onclick="ongllb();"
								label="所有管理人员类别"></odin:checkbox></td>
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
								<odin:groupBox title="工作人员类别">
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
								<odin:groupBox title="管理人员类别">
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
						<td><odin:checkbox property="fxzry" label="非现职人员"></odin:checkbox>
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
	maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350"
	maximizable="false" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="dataVerifyWin" width="960"
	height="500" title="信息校验" modal="true" />
<odin:window src="/blank.htm" id="refreshWin" width="550" height="400"
	maximizable="false" title="窗口" />
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
	//a01统计关系所在单位
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
				//页面调整
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
									listeners : {//判断页面是否更改，

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
		}else if(obj.checked==false){//显示全零行
			document.getElementById("exptunit").disabled=false;
			document.getElementById("expsy").disabled=false;
			document.getElementById("exppicture").disabled=false;
		}
	}
</script>