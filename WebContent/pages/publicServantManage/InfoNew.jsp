<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.Pagedata"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@include file="/comOpenWinInit.jsp"%>
<%@page	import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page	import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<%@page import="javax.servlet.http.HttpSession"%>	

	
<%@page import ="java.util.*"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>

<style>
.x-form-item {
	width: 100%;
	height: 100%;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}
#a01Grid table,#a01Grid div{
	border-collapse:collapse;
	border:0;
	margin:0;
	border-collapse:collapse;
	border-spacing:0;
}
tr.x-grid-record-yellow .x-grid-td {
    background: YELLOW;
} 
#a01Grid td,#a06Grid td,#a36Grid td,#a99Z1Grid td,#a15Grid td,#a14Grid td,#a02Grid td,#a05Grid td,#a08Grid td,#a05Grid2{
/* background:#E8E8E8 ;  */
	border-left-style:none;
	/*border-right-style:none;*/
	/* border-bottom-style:none; */
	border-top-style:none;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
	
</script>

<div style="float: right;">
	<odin:tab id="tab">
		<odin:tabModel>
			<odin:tabItem title="人员基本信息" id="tab1"></odin:tabItem>
			<odin:tabItem title="工作单位及职务" id="tab2"></odin:tabItem>
			<odin:tabItem title="现职务层次" id="tab3"></odin:tabItem>
			<odin:tabItem title="现职级" id="tab4"></odin:tabItem>
			<odin:tabItem title="专业技术职务" id="tab5"></odin:tabItem>
			<odin:tabItem title="学历学位" id="tab6"></odin:tabItem>
			<odin:tabItem title="奖惩综述" id="tab7"></odin:tabItem>
			<odin:tabItem title="考核信息" id="tab8"></odin:tabItem>
			<odin:tabItem title="家庭成员" id="tab9"></odin:tabItem>
			<odin:tabItem title="其他信息集" id="tab10" isLast="true"></odin:tabItem>
		</odin:tabModel>


		<%----------------------------------------------人员基本信息----------------------------------------------------%>
		<odin:tabCont itemIndex="tab1">
			<div id="floatToolDiv1"></div>
			<odin:toolBar property="floatToolBar1" applyTo="floatToolDiv1">
				<odin:fill />
				<odin:buttonForToolBar text="保存当前列表顺序" id="a01Order" handler="a01Order" icon="image/u53.png" cls="x-btn-text-icon" />
				<odin:buttonForToolBar text="保存" id="a01Save" handler="a01Save" icon="images/save.gif" cls="x-btn-text-icon" />
				<odin:buttonForToolBar text="新增" id="a01Savecond" handler="a01Savecond" icon="images/add.png" isLast="true"/>
			<%-- 	<odin:buttonForToolBar text="删除" id="a01Deletecond" handler="a01Deletecond" icon="images/delete.png" /> --%>
			</odin:toolBar>
			<div id="floatToolDiv1" style="min-width:960px;"></div>
			<odin:editgrid2 property="a01Grid" topBarId="toolBar2"
				autoFill="true" height="607" width="700" sm="row"
				remoteSort="false" >
				<odin:gridJsonDataModel>
					<%
					List<String> listtop = (List<String>) request.getSession().getAttribute("order");
					for(int i=0;i<listtop.size();i++){
						if(i==listtop.size()-1){
							String dataIndex = Pagedata.map.get(listtop.get(i)).get("dataIndex");
							System.out.println(dataIndex);
							%>
							<odin:gridDataCol name="<%=dataIndex %>" isLast="true" />
							
							<% 
						}else{
							String dataIndex = Pagedata.map.get(listtop.get(i)).get("dataIndex");
							System.out.println(dataIndex);
							%>
							<odin:gridDataCol name="<%=dataIndex %>" />
							
							<%
						}
					}
						
					%>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				<odin:gridColumn header="A0000主键" editor="checkbox"
						dataIndex="a0000" hidden="true" sortable="false" />
					 <odin:gridRowNumColumn header="序号" width="35" ></odin:gridRowNumColumn> 
					<%
					List<String> list = (List<String>) request.getSession().getAttribute("order");
					for(int i=0;i<list.size();i++) {
						if(i==list.size()-1){
							String dataIndex = Pagedata.map.get(list.get(i)).get("dataIndex");
							System.out.println(dataIndex);
							String header = Pagedata.map.get(list.get(i)).get("header");
							System.out.println(header);
							String width = Pagedata.map.get(list.get(i)).get("width");
							System.out.println(width);
							String edited = Pagedata.map.get(list.get(i)).get("edited");
							System.out.println(edited);
							String editor = Pagedata.map.get(list.get(i)).get("editor");
							System.out.println(editor);
							String required = Pagedata.map.get(list.get(i)).get("required");
							System.out.println(required);
							String codeType = Pagedata.map.get(list.get(i)).get("codeType");
							System.out.println(codeType);
							String editorId = Pagedata.map.get(list.get(i)).get("editorId");
							System.out.println(editorId);
							String onSelect = Pagedata.map.get(list.get(i)).get("onSelect");
							System.out.println(onSelect);
							String onKeyDown = Pagedata.map.get(list.get(i)).get("onKeyDown");
							System.out.println(onKeyDown);
							if(edited=="false"){
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" 
									align="center" width="<%=width %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" isLast="true"/>
							<% 
							}else{
							%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" 
									align="center" width="<%=width %>" edited="<%=edited %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" isLast="true"/>
						<% 
							}
						}else{
							String dataIndex = Pagedata.map.get(list.get(i)).get("dataIndex");
							System.out.println(dataIndex);
							String header = Pagedata.map.get(list.get(i)).get("header");
							System.out.println(header);
							String width = Pagedata.map.get(list.get(i)).get("width");
							System.out.println(width);
							String edited = Pagedata.map.get(list.get(i)).get("edited");
							System.out.println(edited);
							String editor = Pagedata.map.get(list.get(i)).get("editor");
							System.out.println(editor);
							String required = Pagedata.map.get(list.get(i)).get("required");
							System.out.println(required);
							String codeType = Pagedata.map.get(list.get(i)).get("codeType");
							System.out.println(codeType);
							String editorId = Pagedata.map.get(list.get(i)).get("editorId");
							System.out.println(editorId);
							String onSelect = Pagedata.map.get(list.get(i)).get("onSelect");
							System.out.println(onSelect);
							String onKeyDown = Pagedata.map.get(list.get(i)).get("onKeyDown");
							System.out.println(onKeyDown);
							if(edited=="false"){
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" 
									align="center" width="<%=width %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" />
							<% 		
							}else{
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" 
									align="center" width="<%=width %>" edited="<%=edited %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" />
								<%
							}
							
						}
					}
					%>
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
	
		<script>
		
		
		
		
		</script>
		
		
		
		
<%-----------------------------工作单位及职务-------------------------------------------------------%>
<odin:tabCont itemIndex="tab2" >
			<odin:toolBar property="floatToolBar2" applyTo="floatToolDiv2">
				<odin:textForToolBar text="当前操作人员: <span id=\"tab2A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab2A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
<odin:buttonForToolBar text="保存当前列表顺序" id="a02Order" handler="a02Order" icon="image/u53.png" cls="x-btn-text-icon" />
<odin:buttonForToolBar text="保存" id="save2" handler="a02Save" icon="images/save.gif" cls="x-btn-text-icon" />
<odin:buttonForToolBar text="新增" id="a02Savecond" handler="a02Savecond" icon="images/add.png" />
<odin:buttonForToolBar text="删除" id="a02Deletecond" handler="a02Deletecond" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<div id="floatToolDiv2"  style="min-width:60px;"></div>
			<odin:editgrid2 property="a02Grid" topBarId="toolBar2" forceNoScroll="true" autoFill="true" height="607"  width="10%" sm="row" remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0200"/>
					<odin:gridDataCol name="a0281"/>		<!-- 输出-->
					<%
						List<String> a02ListTop = (List<String>) request.getSession().getAttribute("a02Order");
						for(int i=0;i<a02ListTop.size();i++){
							String dataIndex = Pagedata.map.get(a02ListTop.get(i)).get("dataIndex");
							if(i==a02ListTop.size()-1){
								%>
								<odin:gridDataCol name="<%=dataIndex %>" isLast="true" />
								<% 
							}else{
								%>
								<odin:gridDataCol name="<%=dataIndex %>" />
								<%
							}
						}
					%>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn2 dataIndex="a0200"  header="ID" locked="true" editorId="a0200" align="center" edited="false" hidden="true" width="0"  editor="text" required="false"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a0281" header="输出" locked="true" align="center" codeType="BOOLEAN" width="20" editor="select" required="true"  sortable="false"/>
					<%
					List<String> a02ListTop = (List<String>) request.getSession().getAttribute("a02Order");
					for(int i=0;i<a02ListTop.size();i++){
						String dataIndex = Pagedata.map.get(a02ListTop.get(i)).get("dataIndex");
						String header = Pagedata.map.get(a02ListTop.get(i)).get("header");
						String width = Pagedata.map.get(a02ListTop.get(i)).get("width");
						String edited = Pagedata.map.get(a02ListTop.get(i)).get("edited");
						String editor = Pagedata.map.get(a02ListTop.get(i)).get("editor");
						String required = Pagedata.map.get(a02ListTop.get(i)).get("required");
						String codeType = Pagedata.map.get(a02ListTop.get(i)).get("codeType");
						String editorId = Pagedata.map.get(a02ListTop.get(i)).get("editorId");
						String onSelect = Pagedata.map.get(a02ListTop.get(i)).get("onSelect");
						String onKeyDown = Pagedata.map.get(a02ListTop.get(i)).get("onKeyDown");
						if(i==a02ListTop.size()-1){
							if(edited=="false"){
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" align="center" width="<%=width %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" isLast="true"/>
								<% 
							}else{
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" align="center" width="<%=width %>" edited="<%=edited %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" isLast="true"/>
								<% 
							}
						}else{
							if(edited=="false"){
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" align="center" width="<%=width %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" />
								<% 		
							}else{
								%>
								<odin:gridEditColumn2 dataIndex="<%=dataIndex %>" header="<%=header%>" align="center" width="<%=width %>" edited="<%=edited %>" editor="<%=editor %>"
									required="<%=required %>" sortable="false" codeType="<%=codeType %>" editorId="<%=editorId %>" onSelect="<%=onSelect %>" onKeyDown="<%=onKeyDown %>" />
								<%
							}
						}
					}
					%>
					</odin:gridColumnModel>
								<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
<script>
	Ext.onReady(function(){
		a02Savecond();
		var A02Grid = odin.ext.getCmp('a02Grid');
		var A02store = A02Grid.getStore();
		$(window).resize(function (){
			a02SetWidthHeight();
		});
		A02Grid.on('beforeedit', function(iEventobj){//参数iEventobj是单元格
			var n = A02Grid.store.data.length;// 获得总行数   
           	var col = iEventobj.column;//获取单元格的列
            var row = iEventobj.row;//获取单元格的行
            var _record = iEventobj.record;//获取当前选中的这个单元格所在的一整行数据
            var A0255 = _record.get("a0255");//任免状态
            var A0201d = _record.get("a0201d");//领导成员
			var sm = A02Grid.getSelectionModel();
			var selections = sm.getSelections();
			var conLength = A02Grid.colModel.config.length;
			var a0265Col = 0;
			var a0267Col = 0;
			var a0201eCol = 0;
			for(var k=0;k<conLength;k++){
				if("a0265" == A02Grid.colModel.config[k].dataIndex){
					a0265Col = k ;
				}
				if("a0267" == A02Grid.colModel.config[k].dataIndex){
					a0267Col = k ;
				}
				if("a0201e" == A02Grid.colModel.config[k].dataIndex){
					a0201eCol = k ;
				}
			}
            for(var i=0;i<n;i++){
            	if(A0255=="1" || A0255 == ""){
                	if (a0265Col == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
 	                   return false;
 	               	}
                	if (a0267Col == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
  	                   return false;
  	               	}
                }
            	if(A0201d == "0" || A0201d == ""){
            		if (a0201eCol == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
  	                   return false;
  	               	}
            	}
            }
            return true;
    	});
	});
	function a02SetWidthHeight() {
		document.getElementById("floatToolDiv2").parentNode.parentNode.style.overflow = 'hidden';
		var height = document.body.clientHeight;
		var width = document.body.clientWidth;
		document.getElementById("floatToolDiv2").parentNode.style.width = width + 'px';
		var height_top = document.getElementById("floatToolDiv2").offsetHeight;
		document.getElementById("floatToolDiv2").style.width = width + 'px';
		Ext.getCmp("a02Grid").setHeight(height-height_top);
		Ext.getCmp("a02Grid").setWidth(width);
	}
		function a02Savecond() {
			var A02Grid = odin.ext.getCmp('a02Grid');
			var A02store = A02Grid.getStore();
			var A02p = new Ext.data.Record({  
				a0200: '',
				a0281: '',
				a0201b: '',
				a0201d: '',
				a0201a: '',
				a0215a: '',
				a0255: '',
				a0279: '',
				a0219: '',
				a0201e: '',
				a0251b: '',
				a0247: '',
				a0243: '',
				a0245: '',
				a0265: '',
				a0267: '',
				a0272: ''
		    });
			A02store.insert(A02store.getCount(), A02p);
			
		}
		//删除行
		function a02Deletecond(){
			var delA0200="";
			var grid = odin.ext.getCmp('a02Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selected = selections[i];
				var a0200=selections[i].data.a0200;
				if(a0200!=""){
					delA0200 += a0200+"@";
				}; 
				grid.store.remove(selected);
			}
			if(delA0200 != ""){
				delA0200 = delA0200.substring(0,delA0200.length-1);
				radow.doEvent('a02Delete',delA0200);
			}
			grid.view.refresh();
		}
		function isA0201b(){
			var value=$("#a0201b").val();
			radow.doEvent('isA0201b',value);
		}
		function clearA0201bValue(){//清空
			var A02Grid = odin.ext.getCmp('a02Grid');
			var sm = A02Grid.getSelectionModel();
			var a02Selections = sm.getSelections();//获取当前行
			for(var i=0;i<a02Selections.length;i++){
				a02Selections[i].data.a0201b="";
				a02Selections[i].data.a0201a="";
			};
			A02Grid.view.refresh();
		}
		function setA0201aValue(){//赋值
			var value=$("#a0201b").val();
			var A02Grid = odin.ext.getCmp('a02Grid');
			var sm = A02Grid.getSelectionModel();
			var a02Selections = sm.getSelections();//获取当前行
			for(var i=0;i<a02Selections.length;i++){
				a02Selections[i].data.a0201a=value;
			}; 
			A02Grid.view.refresh();
		}
		function officeState(){//控制免职
			var a0255=$("#a0255").val();
			if(a0255 != '免职'){
				var A02Grid = odin.ext.getCmp('a02Grid');
				var sm = A02Grid.getSelectionModel();
				var a02Selections = sm.getSelections();//获取当前行
				for(var i=0;i<a02Selections.length;i++){
					a02Selections[i].data.a0265="";
					a02Selections[i].data.a0267="";
				};
				return false;
			}
		}
		function setA0201e(){//控制成员类别
			a0201d=$("#a0201d").val();
			var A02Grid = odin.ext.getCmp('a02Grid');
			var sm = A02Grid.getSelectionModel();
			var a02Selections = sm.getSelections();//获取当前行
			if("否" == a0201d){
				for(var i=0;i<a02Selections.length;i++){
					a02Selections[i].data.a0201e="";
				};
			}
			
		}
		function a02Order(){
			var grid = odin.ext.getCmp("a02Grid");
			var data = "";
			if(grid.colModel.config[0].header!="序号"){
				$h.alert('系统提示：', "序号不能拖动保存！", null, 260);
				return;
			}
			if(grid.colModel.config[1].header!="ID"){
				$h.alert('系统提示：', "ID不能拖动保存！", null, 260);
				return;
			}
			if(grid.colModel.config[2].header!="输出"){
				$h.alert('系统提示：', "输出不能拖动保存！", null, 260);
				return;
			}
			for(var i=3;i<18;i++){
				data+=grid.colModel.config[i].header+",";
			}
			radow.doEvent('a02OrderSave',data);
		}
	</script>
	<%-----------------------------现职务层次-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab3">
			<odin:toolBar property="floatToolBar3" applyTo="floatToolDiv3">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab3A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab3A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="a05Save" handler="a05Save" icon="images/save.gif" cls="x-btn-text-icon"/>
			    <odin:buttonForToolBar text="新增" id="a05Savecond" handler="a05Savecond" icon="images/add.png"/>
				<odin:buttonForToolBar text="删除" id="a05Deletecond" handler="a05Deletecond" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<div id="floatToolDiv3" style="min-width:960px;"></div>

			<odin:editgrid2 property="a05Grid" topBarId="toolBar2"
				autoFill="true" height="425" width="700" sm="row"
				remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0500" />
					<odin:gridDataCol name="a0501b" /><!-- 职务层次 -->
					<odin:gridDataCol name="a0524" /><!-- 状态 -->
					<odin:gridDataCol name="a0504" /><!-- 批准日期 -->
					<odin:gridDataCol name="a0517" isLast="true" /><!-- 终止日期 -->
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" editor="checkbox" dataIndex="logchecked" hidden="true" sortable="false" />
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn2 dataIndex="a0500" hidden="true" header="ID" editorId="a0500" align="center" width="300"  edited="true" editor="text" required="false" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0501b" header="职务层次" editorId="a0501b" align="center" width="260" codeType="ZB09" edited="true" editor="select" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0524" editorId="a0524" onSelect="getA0524" header="状态" align="center" width="160"  required="true" codeType="ZB14" edited="true" editor="select" sortable="false" />
					<odin:gridEditColumn2  dataIndex="a0504" editorId="a0504" header="批准日期" align="center" width="260" edited="true" editor="text" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0517" editorId="a0517" onKeyDown="setA0517Value" header="终止日期 " align="center" width="260" edited="true" editor="text"  sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
				</odin:editgrid2>
			</odin:tabCont>
		<script>
			Ext.onReady(function() {
				a05Savecond();
				$(window).resize(function (){
					a05SetWidthHeight();
				});
			});
			function a05SetWidthHeight() {
				document.getElementById("floatToolDiv3").parentNode.parentNode.style.overflow = 'hidden';
				var height = document.body.clientHeight;
				var width = document.body.clientWidth;
				document.getElementById("floatToolDiv3").parentNode.style.width = width + 'px';
				var height_top = document.getElementById("floatToolDiv3").offsetHeight;
				document.getElementById("floatToolDiv3").style.width = width + 'px';
				Ext.getCmp("a05Grid").setHeight(height-height_top);
				Ext.getCmp("a05Grid").setWidth(width);
			}
			function a05Savecond() {
				var a05Grid = odin.ext.getCmp('a05Grid');
				var store = a05Grid.getStore();
				var p = new Ext.data.Record({
					a0500 : '',
					a0501b : '',
					a0524 : '',
					a0504 : '',
					a0517 : ''
				});
				store.insert(store.getCount(), p);
			}
			function a05Deletecond(){
				var delA0500="";
				var grid = odin.ext.getCmp('a05Grid');
				var sm = grid.getSelectionModel();
				var selections = sm.getSelections();
				for (var i = 0; i < selections.length; i++) {
					var selected = selections[i];
					var a0500=selections[i].data.a0500;
					if(a0500!=""){
						delA0500 += a0500+"@";
					}; 
					grid.store.remove(selected);
				}
				if(delA0500 != ""){
					delA0500 = delA0500.substring(0,delA0500.length-1);
					radow.doEvent('a05Delete',delA0500);
				}
				grid.view.refresh();
			}
			var a0524="";
			function getA0524(){
				a0524 = $("#a0524").val();
				var grid = odin.ext.getCmp('a05Grid');
				var sm = grid.getSelectionModel();
				var a05Selections = sm.getSelections();//获取当前行
				if("已免" != a0524){
					for(var i=0;i<a05Selections.length;i++){
						a05Selections[i].data.a0517="";
					};
				}
			}
			function setA0517Value(){
				var grid = odin.ext.getCmp('a05Grid');
				var sm = grid.getSelectionModel();
				var a05Selections = sm.getSelections();//获取当前行
				if("已免" != a0524){
					for(var i=0;i<a05Selections.length;i++){
						a05Selections[i].data.a0517="";
					};
					grid.view.refresh();
					$h.alert('系统提示：', "<在任>状态，禁止编辑！", null, 200);
				}
			}
		</script>
		<odin:gridSelectColJs2 name="a0524" codeType="ZB14"></odin:gridSelectColJs2>

		<%-----------------------------现职级-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab4">
			<odin:toolBar property="floatToolBar4" applyTo="floatToolDiv4">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab4A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab4A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="a05Save2" handler="a05Save2" icon="images/save.gif" cls="x-btn-text-icon" />
			    <odin:buttonForToolBar text="新增" id="a05Savecond2" handler="a05Savecond2" icon="images/add.png"/>
				<odin:buttonForToolBar text="删除" id="a05Deletecond2" handler="a05Deletecond2" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<div id="floatToolDiv4" style="min-width:960px;"></div>
			<odin:editgrid2 property="a05Grid2" topBarId="toolBar2"
				autoFill="true" height="700" sm="row"
				remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0500" />
					<odin:gridDataCol name="a0501b2" /><!-- 职级 -->
					<odin:gridDataCol name="a0524" /><!-- 状态 -->
					<odin:gridDataCol name="a0504" /><!-- 批准日期 -->
					<odin:gridDataCol name="a0517" isLast="true" /><!-- 终止日期 -->
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" editor="checkbox" dataIndex="logchecked" hidden="true" sortable="false" />
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
			  	    <odin:gridEditColumn2 dataIndex="a0500" hidden="true" header="ID" editorId="a0500" align="center" width="200"  edited="true" editor="text" required="false" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0501b2" header="职级 "align="center" width="260" codeType="ZB148" edited="true" editor="select" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0524" header="状态" align="center" width="160" codeType="ZB14" edited="true" editor="select" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0504" header="批准日期" align="center" width="260" edited="true" editor="text" required="true" sortable="false" codeType="ZB01" />
					<odin:gridEditColumn2 dataIndex="a0517" header="终止日期 " align="center" width="260"  onKeyDown="setA0517Value2" edited="true" editor="text" hidden="false" sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
		<script>
			Ext.onReady(function() {
				a05Savecond2();
				$(window).resize(function(){  
					a05SetWidthHeight2();  
				});  
			});
			function a05SetWidthHeight2() {
				document.getElementById("floatToolDiv4").parentNode.parentNode.style.overflow = 'hidden';
				var height = document.body.clientHeight;
				var width = document.body.clientWidth;
				document.getElementById("floatToolDiv4").parentNode.style.width = width + 'px';
				var height_top = document.getElementById("floatToolDiv4").offsetHeight;
				document.getElementById("floatToolDiv4").style.width = width + 'px';
				Ext.getCmp("a05Grid2").setHeight(height-height_top);
				Ext.getCmp("a05Grid2").setWidth(width);
			}
			function a05Savecond2() {
				var a05Grid2 = odin.ext.getCmp('a05Grid2');
				var store = a05Grid2.getStore();
				var p = new Ext.data.Record({
					a0500 : '',
					a0501b2 : '',
					a0524 : '',
					a0504 : '',
					a0517 : ''
				});
				store.insert(store.getCount(), p);
			}
			function a05Deletecond2(){
				var delA0500="";
				var grid = odin.ext.getCmp('a05Grid2');
				var sm = grid.getSelectionModel();
				var selections = sm.getSelections();
				for (var i = 0; i < selections.length; i++) {
					var selected = selections[i];
					var a0500=selections[i].data.a0500;
					if(a0500!=""){
						delA0500 += a0500+"@";
					}; 
					grid.store.remove(selected);
				}
				if(delA0500 != ""){
					delA0500 = delA0500.substring(0,delA0500.length-1);
					radow.doEvent('a05Delete2',delA0500);
				}
				grid.view.refresh();
			}
			function setA0517Value2(){
				var grid = odin.ext.getCmp('a05Grid2');
				var sm = grid.getSelectionModel();
				var a05Selections = sm.getSelections();//获取当前行
				if("已免" != a0524){
					for(var i=0;i<a05Selections.length;i++){
						a05Selections[i].data.a0517="";
					};
					$h.alert('系统提示：', "<在任>状态，禁止编辑！", null, 200);
					grid.view.refresh();
				}
			}
		</script>
		<odin:gridSelectColJs2 name="a0524" codeType="ZB14"></odin:gridSelectColJs2>
		<!----------------------------------------------end===>lixl ------------------------------------------------->

		<%-----------------------------专业技术职务-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab5">
			<odin:toolBar property="floatToolBar5" applyTo="floatToolDiv5">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab5A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab5A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="save5"
					handler="a06Save" icon="images/save.gif" cls="x-btn-text-icon" />
					<odin:buttonForToolBar text="新增" id="addA06Count" handler="addA06Count" icon="images/add.png" />
<odin:buttonForToolBar text="删除" id="deleteA06Count" handler="deleteA06" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<Div id="floatToolDiv5"></Div>
			<odin:editgrid2 property="a06Grid" topBarId="toolBar2"
				autoFill="true" height="605" sm="row"
				remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="A0600" />
					<odin:gridDataCol name="A0699" />
					<odin:gridDataCol name="A0601" />
					<odin:gridDataCol name="A0602" />
					<odin:gridDataCol name="A0604" />
					<odin:gridDataCol name="A0607" />
					<odin:gridDataCol name="A0611" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="A0600" editor="text" dataIndex="A0600"
						hidden="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="A0699" header="是否输出" align="center"
						width="100" editor="select" required="true"
						sortable="false" codeType="BOOLEAN" />
					<odin:gridEditColumn2 dataIndex="A0601" header="专业技术资格代码" align="center"
						width="300" editor="select" required="true"
						sortable="false" codeType="GB8561" />
					<odin:gridEditColumn2 dataIndex="A0602" header="专业技术职务名称" align="center"
						width="300" edited="true" editor="text"
						sortable="false" />
					<odin:gridEditColumn2 dataIndex="A0604" header="获得资格日期"
						align="center" width="300" editor="text"
						required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="A0607" header="取得资格途径" align="center"
						width="300" editor="select"
						sortable="false" codeType="ZB24" />
					<odin:gridEditColumn2 dataIndex="A0611" header="评委会或考试名称" align="center"
						width="300" edited="true" editor="text"
						sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
		<%-----------------------------专业职务技术初始化-------------------------------------------------------%>
		<script>
			Ext.onReady(function() {
				var a06Grid = Ext.getCmp('a06Grid');
				var a06store = a06Grid.getStore();

				//dstore.on('load',function(){
				a06InsertEmptyRow(a06store);
				//});

			});
			function a06InsertEmptyRow(ds) {
				var dstorecount = ds.getCount();
				for (var gi = 0; gi < 5 - dstorecount; gi++) {
					a06Savecond();
				}
			}
			function a06Savecond() {
				//document.getElementById("conditionName").focus();
				var grid = odin.ext.getCmp('a06Grid');
				var store = grid.getStore();
			 	var s = [];
			    var hexDigits = "0123456789abcdef";
			    for (var i = 0; i < 36; i++) {
			        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			    }
			    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
			    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
			    s[8] = s[13] = s[18] = s[23] = "-";
			    var uuid = s.join("");
				var p = new Ext.data.Record({
					A0600 : uuid,
					A0601 : '',
					A0602 : '',
					A0604 : '',
					A0607 : '',
					A0611 : ''
				});
				store.insert(store.getCount(), p);
			}
			function deleteA06Count(){
				var a06Grid = odin.ext.getCmp('a06Grid');
				delGridColum(a06Grid);
			}
			function addA06Count(){
				a06Savecond();
			}
		</script>

		<!----------------------------------------------start===>lixl ------------------------------------------------->
		<%-----------------------------学历学位-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab6">
			<odin:toolBar property="floatToolBar6" applyTo="floatToolDiv6">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab6A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab6A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="a08Save" handler="a08Save" icon="images/save.gif" cls="x-btn-text-icon" />
				<odin:buttonForToolBar text="新增" id="a08Savecond" handler="a08Savecond" icon="images/add.png"/>
				<odin:buttonForToolBar text="删除" id="a08Deletecond" handler="a08Deletecond" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<div id="floatToolDiv6" style="min-width:960px;"></div>
			<odin:editgrid2 property="a08Grid" topBarId="toolBar2" autoFill="true" forceNoScroll="true" height="700" sm="row" remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0800" />
					<odin:gridDataCol name="a0837" /><!-- 教育类别 -->
					<odin:gridDataCol name="a0801b" /><!-- 学历代码 -->
					<odin:gridDataCol name="a0801a" /><!-- 学历名称 -->
					<odin:gridDataCol name="a0811" /><!-- 学制年限（年） -->
					<odin:gridDataCol name="a0901b" /><!-- 学位代码 -->
					<odin:gridDataCol name="a0901a" /><!-- 学位名称 -->
					<odin:gridDataCol name="a0814" /><!-- 学校（单位）名称-->
					<odin:gridDataCol name="a0827" /><!-- 所学专业类别 -->
					<odin:gridDataCol name="a0824" /><!-- 所学专业名称 -->
					<odin:gridDataCol name="a0804" /><!-- 入学时间 -->
					<odin:gridDataCol name="a0807" /><!-- 毕（肄）业时间 -->
					<odin:gridDataCol name="a0904" isLast="true" /><!-- 学位授予时间 -->
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35">
					</odin:gridRowNumColumn>
        	    <odin:gridEditColumn2 dataIndex="a0800" hidden="true" header="ID" editorId="a0800" align="center" width="300"  edited="true" editor="text" required="false" sortable="false" />
														
					<odin:gridEditColumn2 dataIndex="a0837" header="教育类别" editorId="a0837" align="center" width="30" codeType="ZB123" edited="true" editor="select" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0801b" header="学历代码" onSelect="setA0801bValue" editorId="a0801b" align="center" width="30" codeType="ZB64" filter="CODE_LEAF != 0" edited="true" editor="select" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0801a" header="学历名称" align="center" width="30" edited="true" editor="text" required="true" sortable="false" codeType="ZB01" />
					<odin:gridEditColumn2 dataIndex="a0811" header="学制年限（年）" align="center" width="40" edited="true" editor="number" sortable="false" maxLength="3" />
					<odin:gridEditColumn2 dataIndex="a0901b" header="学位代码" onSelect="setA0901bValue" editorId="a0901b"  align="center" width="30" codeType="GB6864" edited="true" editor="select" required="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0901a" header="学位名称" align="center" width="30" required="true" edited="true" editor="text" sortable="false" codeType="ZB130" />
					<odin:gridEditColumn2 dataIndex="a0814" header="学校（单位）名称" align="center" width="30" editor="text" edited="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0827" header="所学专业类别" align="center" onSelect="setA0824bValue" editorId="a0827" filter="CODE_LEAF != 0" width="30" codeType="GB16835" editor="selectTree" required="false" edited="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0824" header="所学专业名称" align="center" editorId="a0824" width="30" editor="text" edited="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0804" header="入学时间" align="center" width="30" editor="text" edited="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0807" header="毕（肄）业时间" align="center" width="30" editor="text" edited="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="a0904" header="学位授予时间" align="center" width="30" editor="text" edited="true" sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
		<script>
			Ext.onReady(function() {
				a08Savecond();
				$(window).resize(function (){
					a08SetWidthHeight();
				});
			});
			function a08SetWidthHeight() {
				document.getElementById("floatToolDiv6").parentNode.parentNode.style.overflow = 'hidden';
				var height = document.body.clientHeight;
				var width = document.body.clientWidth;
				document.getElementById("floatToolDiv6").parentNode.style.width = width + 'px';
				var height_top = document.getElementById("floatToolDiv3").offsetHeight;
				document.getElementById("floatToolDiv6").style.width = width + 'px';
				Ext.getCmp("a08Grid").setHeight(height-height_top);
				Ext.getCmp("a08Grid").setWidth(width);
			}
			function a08Savecond() {
				var a08Grid = odin.ext.getCmp('a08Grid');
				var store = a08Grid.getStore();
				var p = new Ext.data.Record({
					a0800 : '',
					a0837 : '',
					a0801b : '',
					a0801a : '',
					a0811 : '',
					a0901b : '',
					a0901a : '',
					a0814 : '',
					a0827 : '',
					a0824 : '',
					a0804 : '',
					a0807 : '',
					a0904 : ''
				});
				store.insert(store.getCount(), p);

			}
			function a08Deletecond(){
				var delA0800="";
				var grid = odin.ext.getCmp('a08Grid');
				var sm = grid.getSelectionModel();
				var selections = sm.getSelections();
				for (var i = 0; i < selections.length; i++) {
					var selected = selections[i];
					var a0800=selections[i].data.a0800;
					if(a0800!=""){
						delA0800 += a0800+"@";
					}; 
					grid.store.remove(selected);
				}
				if(delA0800 != ""){
					delA0800 = delA0800.substring(0,delA0800.length-1);
					radow.doEvent('a08Delete',delA0800);
				}
				grid.view.refresh();
			}
			function setA0801bValue(){
				var value=$("#a0801b").val();
				var A08Grid = odin.ext.getCmp('a08Grid');
				var sm = A08Grid.getSelectionModel();
				var a08Selections = sm.getSelections();//获取当前行
				for(var i=0;i<a08Selections.length;i++){
					a08Selections[i].data.a0801a=value;
				};
			}
			function setA0901bValue(){
				var value=$("#a0901b").val();
				var A08Grid = odin.ext.getCmp('a08Grid');
				var sm = A08Grid.getSelectionModel();
				var a08Selections = sm.getSelections();//获取当前行
				for(var i=0;i<a08Selections.length;i++){
					a08Selections[i].data.a0901a=value;
				};
			}
			function setA0824bValue(){
				var value=$("#a0827").val();
				var A08Grid = odin.ext.getCmp('a08Grid');
				var sm = A08Grid.getSelectionModel();
				var a08Selections = sm.getSelections();//获取当前行
				for(var i=0;i<a08Selections.length;i++){
					a08Selections[i].data.a0824=value;
				};
			}
			function open(a0000){
				$h.openPageModeWin('selectzgxlxw','pages.publicServantManage.SelectZGXLXW','选择最高学历学位',580,180,a0000,'<%=request.getContextPath()%>',null,null,null);
			}

		</script>

		<%-----------------------------奖惩综述-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab7">
			<odin:toolBar property="floatToolBar7" applyTo="floatToolDiv7">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab7A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab7A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
					<odin:buttonForToolBar text="保存" id="a14Save"  handler="a14Save" icon="images/save.gif" cls="x-btn-text-icon" />
			<odin:buttonForToolBar text="新增" id="a14Savecond" handler="a14Savecond" icon="images/add.png" />
			<odin:buttonForToolBar text="删除" id="a14Deletecond" handler="a14Deletecond" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<Div id="floatToolDiv7"></Div>
			<odin:editgrid2 property="a14Grid" topBarId="toolBar2" autoFill="true" height="200" sm="row" remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a1400" />		<!--id-->
			  		<odin:gridDataCol name="a1404b" />		<!--奖惩名称代码-->
			  		<odin:gridDataCol name="a1404a" />		<!--奖惩名称-->
			   		<odin:gridDataCol name="a1415" />		<!--受奖惩时职务层次-->
			   		<odin:gridDataCol name="a1414" />		<!--批准机关级别-->
			   		<odin:gridDataCol name="a1428" />		<!--批准机关性质-->			   		
			   		<odin:gridDataCol name="a1411a" />		<!--批准机关-->
			   		<odin:gridDataCol name="a1407" />		<!--批准日期-->
			   		<odin:gridDataCol name="a1424" isLast="true"/>		<!--撤销日期-->			
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn header="id" dataIndex="a1400" hidden="true"  width="35" editor="text" />
					<odin:gridEditColumn2 dataIndex="a1404b" header="奖惩名称代码 " align="center" editorId="a1404b" onSelect="setA1404aValue" codeType="ZB65"  width="200"  edited="true" editor="select" required="true"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1404a" header="奖惩名称 " align="center" editorId="a1404a" edited="true" width="100"  editor="text" required="true"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1415" header="受奖惩时职务层次" align="center"  codeType="ZB09" edited="true" width="200"  editor="selectTree" required="false"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1414" header="批准机关级别" align="center"  codeType="ZB03" edited="true" width="100"  editor="select" required="false"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1428" header="批准机关性质" align="center"  codeType="ZB128" edited="true" width="100"  editor="select" required="false"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1411a" header="批准机关" align="center" edited="true"  width="100"  editor="text" required="false"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1407" header="批准日期" align="center" maxLength="8" edited="true" width="100"  editor="text" required="true"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1424" header="撤销日期" align="center" maxLength="8" edited="true" width="100"  editor="text" required="false"  sortable="false" isLast="true"/>
				</odin:gridColumnModel>
								<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
	<script>
		Ext.onReady(function(){
			a14Savecond();
		});
		//初始化grid
		function a14Savecond() {
			var A14Grid = odin.ext.getCmp('a14Grid');
			var A14store = A14Grid.getStore();
			var A14p = new Ext.data.Record({  
				a1400: '',
				a1404b: '',
				a1404a: '',
				a1415: '',
				a1414: '',
				a1428: '',
				a1411a: '',
				a1407: '',
				a1424: ''
		    });
			A14store.insert(A14store.getCount(), A14p); 
			
		}
		//删除行
		function a14Deletecond(){
			var delA1400="";
			var grid = odin.ext.getCmp('a14Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selected = selections[i];
				var a1400=selections[i].data.a1400;
				if(a1400!=""){
					delA1400 += a1400+"@";
				}; 
				grid.store.remove(selected);
			}
			if(delA1400 != ""){
				delA1400 = delA1400.substring(0,delA1400.length-1);
				radow.doEvent('a14Delete',delA1400);
			}
			grid.view.refresh();
		}
		function setA1404aValue(){
			var value=$("#a1404b").val();
			var a14Grid = odin.ext.getCmp('a14Grid');
			var sm = a14Grid.getSelectionModel();
			var a14Selections = sm.getSelections();//获取当前行
			for(var i=0;i<a14Selections.length;i++){
				a14Selections[i].data.a1404a=value;
			};
		}
	</script>

		<%-----------------------------考核信息-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab8">
			<odin:toolBar property="floatToolBar8" applyTo="floatToolDiv8">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab8A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab8A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="a15Save" handler="a15Save" icon="images/save.gif" cls="x-btn-text-icon" />
				<odin:buttonForToolBar text="新增" id="a15Savecond" handler="a15Savecond" icon="images/add.png" />
				<odin:buttonForToolBar text="删除" id="a15Deletecond" handler="a15Deletecond"  icon="images/delete.png"  isLast="true"/>
			</odin:toolBar>
			<Div id="floatToolDiv8"></Div>
			<odin:editgrid2 property="a15Grid" topBarId="toolBar2" autoFill="true" height="200" sm="row" remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a1500" />				<!-- id -->
			  		<odin:gridDataCol name="a1521" />				<!-- 年度 -->
			   		<odin:gridDataCol name="a1517" isLast="true"/>	<!-- 考核结论 -->
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn header="id" dataIndex="a1500" hidden="true"  width="35" edited="false" editor="text" />
					<odin:gridEditColumn2 dataIndex="a1521" header="考核年度 " editorId="a1521" align="center"  width="100"  edited="true" editor="select" required="true"  sortable="false"/>
					<odin:gridEditColumn2 dataIndex="a1517" header="考核结论 "  codeType="ZB18" align="center" edited="true" width="300"  editor="select" required="true"  sortable="false"  isLast="true"/>
				</odin:gridColumnModel>
								<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>
		</odin:tabCont>
	<script>
	Ext.onReady(function(){
		a15Savecond();
	});
		function a15Savecond() {
			var A15Grid = odin.ext.getCmp('a15Grid');
			var A15store = A15Grid.getStore();
			var A15p = new Ext.data.Record({  
				a1500: '',
				a1521: '',
				a1527: ''
		    });
			A15store.insert(A15store.getCount(), A15p);
			
		}
		function a15Deletecond(){
			var delA1500="";
			var grid = odin.ext.getCmp('a15Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selected = selections[i];
				var a1500=selections[i].data.a1500;
				if(a1500!=""){
					delA1500 += a1500+"@";
				}; 
				grid.store.remove(selected);
			}
			if(delA1500 != ""){
				delA1500 = delA1500.substring(0,delA1500.length-1);
				radow.doEvent('a15Delete',delA1500);
			}
			grid.view.refresh();
		}
	</script>

		<%-----------------------------家庭成员-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab9">
			<odin:toolBar property="floatToolBar9" applyTo="floatToolDiv9" >
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab9A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab9A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="save9" handler="a36Save" icon="images/save.gif" cls="x-btn-text-icon" />
				<odin:buttonForToolBar text="新增" id="addA36Count" handler="addA36Count" icon="images/add.png" />
				<odin:buttonForToolBar text="删除" id="deleteA36Count" handler="deleteA36" icon="images/delete.png" isLast="true"/>
				
			</odin:toolBar>
			<Div id="floatToolDiv9" ></Div>
			<odin:editgrid2 property="a36Grid" topBarId="toolBar2"
				autoFill="true" height="607" sm="row"
				remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="A3600" />
					<odin:gridDataCol name="A3601" />
					<odin:gridDataCol name="A3604A" />
					<odin:gridDataCol name="A3607" />
					<odin:gridDataCol name="A3627" />
					<odin:gridDataCol name="A3611" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridColumn header="A3600" editor="text" dataIndex="A3600"
						hidden="true" sortable="false" />
					<odin:gridColumn dataIndex="A3601" header="姓名" align="center"
						width="200" edited="true" editor="text" required="true"
						sortable="false" />
					<odin:gridEditColumn2 dataIndex="A3604A" header="称谓" align="center"
						width="200" editor="select" required="true"
						sortable="false" codeType="GB4761" />
					<odin:gridEditColumn2 dataIndex="A3607" header="出生日期"
						align="center" width="200" edited="true" editor="text"
						required="false" sortable="false" />
					<odin:gridEditColumn2 dataIndex="A3627" header="政治面貌"
						align="center" width="200" editor="select"
						required="true" sortable="false" codeType="GB4762" />
					<odin:gridEditColumn2 dataIndex="A3611" header="工作单位及职务"
						align="center" width="300"  editor="text"
						required="true" sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid2>

		</odin:tabCont>


		<%-----------------------------家庭成员及社会关系信息集的初始化-------------------------------------------------------%>
		<script>
			Ext.onReady(function() {
				var a36Grid = Ext.getCmp('a36Grid');
				var a36store = a36Grid.getStore();

				//dstore.on('load',function(){
				a36InsertEmptyRow(a36store);
				//});

			});
			function a36InsertEmptyRow(ds) {
				var dstorecount = ds.getCount();
				for (var gi = 0; gi < 5 - dstorecount; gi++) {
					a36Savecond();
				}
			}
			function a36Savecond() {
				//document.getElementById("conditionName").focus();
				var grid = odin.ext.getCmp('a36Grid');
				var store = grid.getStore();
				var s = [];
			    var hexDigits = "0123456789abcdef";
			    for (var i = 0; i < 36; i++) {
			        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			    }
			    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
			    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
			    s[8] = s[13] = s[18] = s[23] = "-";
			    var uuid = s.join("");
				var p = new Ext.data.Record({
					A3600 : uuid,
					A3601 : '',
					A3604A : '',
					A3607 : '',
					A3627 : '',
					A3611 : ''
				});
				store.insert(store.getCount(), p);
			}
			function deleteA36(){
				var delA3600="";
				var grid = odin.ext.getCmp('a36Grid');
				var sm = grid.getSelectionModel();
				var selections = sm.getSelections();
				for (var i = 0; i < selections.length; i++) {
					var selected = selections[i];
					var a3600=selections[i].data.A3600;
					if(a3600!=""){
						delA3600 += a3600+"@";
					}; 
				}
				if(delA3600 != ""){
					delA3600 = delA3600.substring(0,delA3600.length-1);
					radow.doEvent('a36Delete',delA3600);
				}
				/* var a36Grid = odin.ext.getCmp('a36Grid');
				delGridColum(a36Grid); */
			}
			function addA36Count(){
				a36Savecond();
			}
		</script>
		<odin:gridSelectColJs2 name="A0104" codeType="GB2261"></odin:gridSelectColJs2>


		<%-----------------------------其他信息集-------------------------------------------------------%>
		<odin:tabCont itemIndex="tab10">
			<odin:toolBar property="floatToolBar10" applyTo="floatToolDiv10">
				<odin:textForToolBar text="" />
				<odin:textForToolBar text="当前操作人员: <span id=\"tab10A0101\" ></span>"></odin:textForToolBar>
				<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:textForToolBar>
				<odin:textForToolBar text="身份证号: <span id=\"tab10A0184\" ></span>"></odin:textForToolBar>
				<odin:fill />
				<odin:buttonForToolBar text="保存" id="a10Save" handler="a99Z1Save" icon="images/save.gif" cls="x-btn-text-icon"/>
				<%-- <odin:buttonForToolBar text="新增" id="a99Z1Savecond" handler="addA99Z1Count" icon="images/add.png"/> --%>
				<odin:buttonForToolBar text="删除" id="a99Z1Deletecond" handler="deleteA99Z1" icon="images/delete.png" isLast="true"/>
			</odin:toolBar>
			<Div id="floatToolDiv10"></Div>
			<odin:editgrid property="a99Z1Grid" topBarId="toolBar2"
				autoFill="true" height="605" sm="row" 
				remoteSort="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="A99Z100" />
					<odin:gridDataCol name="A99Z101" />
					<odin:gridDataCol name="A99Z102" />
					<odin:gridDataCol name="A99Z103" />
					<odin:gridDataCol name="A99Z104" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn header="序号" width="35"></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="A99Z100" editor="text" dataIndex="A99Z100" 
						hidden="true" sortable="false" />
					<odin:gridEditColumn2 dataIndex="A99Z101" header="是否考录" align="center" 
						width="100" edited="true" editor="select" 
						sortable="false" codeType="XZ09" />
					<odin:gridEditColumn2 dataIndex="A99Z102" header="录用时间" align="center"
						width="200" edited="true" editor="text" 
						sortable="false" />
					<odin:gridEditColumn2 dataIndex="A99Z103" header="是否选调生"
						align="center" width="200" edited="true" editor="select"
						 sortable="false" codeType="XZ09" onSelect="" />
					<odin:gridEditColumn2 dataIndex="A99Z104" header="进入选调生时间"
						align="center" width="200" edited="true" editor="text"
						 sortable="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData> 
								{data:[]}
							</odin:gridJsonData>
			</odin:editgrid>
		</odin:tabCont>
	</odin:tab>
</div>
	
	<%-----------------------------其他信息集的初始化-------------------------------------------------------%>
	
	<script>
		Ext.onReady(function() {
			var a99Z1Grid = Ext.getCmp('a99Z1Grid');
			var a99Z1store = a99Z1Grid.getStore();

			//dstore.on('load',function(){
			a99Z1InsertEmptyRow(a99Z1store);
			//});
			/* a99Z1Grid.on('validateedit',function(editor, e,e1) {
					//e.cancel = true;
				   // e.record.data[e.field] = e.value;
				var grid = editor.grid;
				//grid.view.getCell(0,3).style.visibility = 'hidden';
				//grid.view.getCell(0,3).accessKey="false";
				//grid.view.getCell(0,3).onclick=function(){return false;};
				var A99Z101 = $("#A99Z101").val();
				
			}); */
			a99Z1Grid.on('beforeedit', function(iEventobj){//参数iEventobj是单元格
               	var col = iEventobj.column;//获取单元格的列
                var row = iEventobj.row;//获取单元格的行
                var _record = iEventobj.record;//获取当前选中的这个单元格所在的一整行数据
               /// var itemtype = _record.get("itemtype");//获取这行数据中itemtype对应的值
                var A99Z101 = _record.get("A99Z101");
                var A99Z103 = _record.get("A99Z103");
                if(A99Z101=="0"){
                	if (3 == col && 0 == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
 	                   return false;
 	               	}
                }
                if(A99Z103=="0"){
                	if(5 == col && 0 == row){
                	   return false;
                	}
                }
                return true;
        	});
		});
		/* function gridssss(){alert();}; */
		/*  function kaolu(v){
			
			var grid = odin.ext.getCmp('a99Z1Grid');
			if(v.data.value=="否"){
				grid.view.getCell(0,3).style.visibility = 'hidden';
			}
			console.log(grid.view.getCell(0,3).style.visibility);
		}  */
		
		/* Ext.onReady(function() {
			$('#A99Z101').change(function(e){
				alert();
	            if($("#A99Z101").val()=="否") {
	            	grid.view.getCell(0,3).style.visibility = 'hidden';
	        	}else{
					grid.view.getCell(0,3).style.visibility = 'visible';
				}
	        });
			 new materialUploadFiled({
			     listener:{
			          onchange:function($("#A99Z101").val()){
			             alert();
			          }
			      }
			}); 
		}); */
		
		
		
		function a99Z1InsertEmptyRow(ds) {
			var dstorecount = ds.getCount();
			for (var gi = 0; gi < 1 - dstorecount; gi++) {
				a99Z1Savecond();
			}
		}
		function a99Z1Savecond() {
			//document.getElementById("conditionName").focus();
			var grid = odin.ext.getCmp('a99Z1Grid');
			var store = grid.getStore();
			var s = [];
		    var hexDigits = "0123456789abcdef";
		    for (var i = 0; i < 36; i++) {
		        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		    }
		    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
		    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
		    s[8] = s[13] = s[18] = s[23] = "-";
		    var uuid = s.join("");
			var p = new Ext.data.Record({
				A99Z100 : uuid,
				A99Z101 : '',
				A99Z102 : '',
				A99Z103 : '',
				A99Z104 : ''
			});
			store.insert(store.getCount(), p);
		}
		
		function deleteA99Z1Count(){
			var a99Z1Grid = odin.ext.getCmp('a99Z1Grid');
			delGridColum(a99Z1Grid);
		}
		function addA99Z1Count(){
			a99Z1Savecond();
		}
		
		/* function kaolu(){
			var A99Z101 = $("#A99Z101").val();
			var grid = odin.ext.getCmp('a99Z1Grid');
			if(A99Z101=='否'){
				grid.view.getCell(0,3).style.visibility = 'hidden';
			}else{
				grid.view.getCell(0,3).style.visibility = 'visible';
			}
		} */
		 //单元格编辑后事件
		 var count = 0;
		var boo1 = true;
		function A99Z1linkage(value, cellmeta, record, rowIndex, columnIndex, store ){
			count ++;
			var grid = Ext.getCmp('a99Z1Grid');
			var view = grid.getView();
			var row =grid.getView().getRows()[rowIndex];
			if(count>1){
				
				
				//row.getElementsByTagName("TD")[3].style.visibility = 'hidden';
				//console.log(grid.view.getCell(0,3).getElementsByTagName("DIV")[0]);
				//grid.view.getCell(0,3).getElementsByTagName("DIV")[0].style.disabled = 'hidden';
				//grid.view.getCell(0,3).getElementsByTagName("DIV")[0].style.visibility = 'hidden';
				//x-grid3-col x-grid3-cell x-grid3-td-94 x-selectable x-grid3-cell-enable
				//x-grid3-col x-grid3-cell x-grid3-td-94 x-selectable x-grid3-cell-disable
				console.log(grid.view.getCell(0,3));
				//grid.view.getCell(0,3).aria-disabled = 'true';
				//grid.view.getCell(0,3).runtimeStyle.visibility = 'hidden';
				/* grid.view.getCell(0,3).style.visibility = 'hidden';
				grid.view.getCell(0,3).applyStyles('visibility:hidden'); */
				//grid.view.getCell(0,3).className='x-grid3-cell-disable';
				
				if(value=="否"){
					grid.view.getCell(0,3).style.visibility = 'hidden';
					grid.view.getCell(0,3).style.visibility = 'hidden';
					grid.view.getCell(0,3).attributes[1].value='x-grid3-col x-grid3-cell x-grid3-td-94 x-selectable x-grid3-cell-disable';
				}else{
					grid.view.getCell(0,3).style.visibility = 'visible';
					grid.view.getCell(0,3).style.visibility = 'visible';
					grid.view.getCell(0,3).attributes[1]='x-grid3-col x-grid3-cell x-grid3-td-94 x-selectable x-grid3-cell-enable';
				}
				
				//grid.view.getCell(0,3).addClass('x-grid3-col x-grid3-cell x-grid3-td-94 x-selectable x-grid3-cell-disable');
			}
			
			/* console.log(grid.view.getCell(0,3));
			if(selections[0].data.A99Z101=="否"){
				grid.view.getCell(0,3).style.visibility = 'hidden';
			}else{
				grid.view.getCell(0,3).style.visibility = 'visible';
			}
			if(selections[0].data.A99Z103=="否"){
				grid.view.getCell(0,5).style.visibility = 'hidden';
			}else{
				grid.view.getCell(0,5).style.visibility = 'visible';
			} */
			return "222";
		} /**/
	</script>



	<%-----------------------------基本信息集的初始化-------------------------------------------------------%>
	<script>
		Ext.onReady(function() {
			var a01Grid = Ext.getCmp('a01Grid');
			var a01store = a01Grid.getStore();
				
			//dstore.on('load',function(){
			a01InsertEmptyRow(a01store);
			$(window).resize(function(){  
				a01SetWidthHeight2();  
			});
			function a01SetWidthHeight2() {
				document.getElementById("floatToolDiv1").parentNode.parentNode.style.overflow = 'hidden';
				var height = document.body.clientHeight;
				var width = document.body.clientWidth;
				document.getElementById("floatToolDiv1").parentNode.style.width = width + 'px';
				var height_top = document.getElementById("floatToolDiv1").offsetHeight;
				document.getElementById("floatToolDiv1").style.width = width + 'px';
				Ext.getCmp("a01Grid").setHeight(height-height_top);
				Ext.getCmp("a01Grid").setWidth(width);
			}
			//});
			
			//-----------------------入党时间与政治面貌的联动---------------------------
			a01Grid.on('beforeedit', function(iEventobj){//参数iEventobj是单元格
				//找到入党时间的字段，政治面貌，第二党派，第三党派
				//var n = a01Grid.store.data.length;// 获得总行数   
				document.getElementById('personA0000').value=a01Grid.getSelectionModel().getSelections()[0].data.A0000;
				var n = a01Grid.colModel.config.length;
				var A0140Num;
				var A0141Num;
				var A3921Num;
				var A3927Num;
				for(var i=0;i<n;i++){
					if(a01Grid.colModel.config[i].dataIndex=="A0140"){//入党时间
						A0140Num=i;
					}
					if(a01Grid.colModel.config[i].dataIndex=="A0141"){//政治面貌
						A0141Num=i;
					}
					if(a01Grid.colModel.config[i].dataIndex=="A3921"){//第二党派
						A3921Num=i;
					}
					if(a01Grid.colModel.config[i].dataIndex=="A3927"){//第三党派
						A3927Num=i;
					}
				}
               	var col = iEventobj.column;//获取单元格的列
                var row = iEventobj.row;//获取单元格的行
                var _record = iEventobj.record;//获取当前选中的这个单元格所在的一整行数据
               /// var itemtype = _record.get("itemtype");//获取这行数据中itemtype对应的值
                var A0140 = _record.get("A0140");//入党时间
                var A0141 = _record.get("A0141");//政治面貌
                var A3921 = _record.get("A3921");//第二党派
                var A3927 = _record.get("A3927");//第三党派
    			var sm = a01Grid.getSelectionModel();
    			var selections = sm.getSelections();
                for(var i=0;i<n;i++){
                	if(A0141=="13"){
                    	if (A3921Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
     	                   return false;
     	               	}
                    	if (A3927Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
      	                   return false;
      	               	}
                    }
                	if(A0141!="01"&&A0141!="02"){
                		if (A0140Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
      	                   return false;
      	               	}
                	}
                	if(A0141==''||A0141==undefined){
                		if (A3921Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
      	                   return false;
      	               	}
                     	if (A3927Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
       	                   return false;
       	               	}
                	}
                	if(A3921==''||A3921==undefined){
                		if (A3927Num == col && i == row){//如果itemtype=1，为可用状态，则返回false，返回false就是不可编辑
        	                   return false;
        	            }
                	}
                }
                return true;
        	});
			
		});
		
		
		/* -------------------------------------------政治面貌、第二党派、第三党派的校验 ------------------------------------*/
		//政治面貌 
		function verifyA0141(){
			var grid = odin.ext.getCmp('a01Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			var a0141 = $("#A0141").val();
			var a0140 = $("#A0140").val();
			
			if(a0141=="中共党员"||a0141=="预备党员"||a0141=="共青团员"){
				a0141="-1";
			}
			var a3921 = $("#A3921").val();
			if(a3921=="中共党员"||a3921=="预备党员"||a3921=="共青团员"){
				a3921="-1";
			}
			var a3927 = $("#A3927").val();
			if(a3927=="中共党员"||a3927=="预备党员"||a3927=="共青团员"){
				a3927="-1";
			}
			//alert(a0141+','+a3921+','+a3927);
			if(a0141 == a3921 && a0141 != ''){
				selections[0].data.A3921='';
				$h.alert('系统提示：',"政治面貌与第二党派不能一致，默认将第二党派的数据清空！",null,200);
 			}
			
 			if(a0141 == a3927 && a0141 != ''){
				selections[0].data.A3927='';
				$h.alert('系统提示：',"政治面貌与第三党派不能一致，默认将第三党派的数据清空！",null,200);
 			}
 			if(a0141!="中共党员" && a0141!="预备党员"){
				if(a0140 !=''||a0140 !=undefined){
					selections[0].data.A0140='';
					return;
				}
			}
		}
		
		//第二党派
		function verifyA3921(){
			var grid = odin.ext.getCmp('a01Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			var a0141 = $("#A0141").val();
			if(a0141==''||a0141==undefined){
				selections[0].data.A3921='';
				grid.view.refresh();
				$h.alert('系统提示：',"请先填写政治面貌！",null,200);
				return;
			}
			if(a0141=="中共党员"||a0141=="预备党员"||a0141=="共青团员"){
				a0141="-1";
			}
			var a3921 = $("#A3921").val();
			if(a3921=="中共党员"||a3921=="预备党员"||a3921=="共青团员"){
				a3921="-1";
			}
			var a3927 = $("#A3927").val();
			if(a3927=="中共党员"||a3927=="预备党员"||a3927=="共青团员"){
				a3927="-1";
			}
			//是否和政治面貌重复
			if(a0141==a3921 && a3921 != ''){
				selections[0].data.A3921='';
				grid.view.refresh();
				$h.alert('系统提示：',"政治面貌与第二党派不能一致，默认将第二党派的数据清空！",null,200);
 			}
 			//不可和第三党派相同 
 			if(a3921 != '' && a3921==a3927){
 				selections[0].data.A3927='';
				$h.alert('系统提示：',"第二党派与第三党派不能一致，默认将第三党派的数据清空！",null,200);
 			}
		}
		//第三党派
		function verifyA3927(){
			var grid = odin.ext.getCmp('a01Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			var a0141 = $("#A0141").val();
			if(a0141==''||a0141==undefined){
				selections[0].data.A3927='';
				grid.view.refresh();
				$h.alert('系统提示：',"请先填写政治面貌！",null,200);
				return;
			}
			if(a0141=="中共党员"||a0141=="预备党员"||a0141=="共青团员"){
				a0141="-1";
			}
			var a3921 = $("#A3921").val();
			if(a3921=="中共党员"||a3921=="预备党员"||a3921=="共青团员"){
				a3921="-1";
			}
			var a3927 = $("#A3927").val();
			if(a3927=="中共党员"||a3927=="预备党员"||a3927=="共青团员"){
				a3927="-1";
			}
			//alert(a0141+','+a3921+','+a3927);
			if(a3921 != '' && a3921==a3927){
 				selections[0].data.A3927='';
 				grid.view.refresh();
				$h.alert('系统提示：',"第二党派与第三党派不能一致，默认将第三党派的数据清空！",null,200);
 			}
			if(a0141 == a3927 && a0141 != ''){
				selections[0].data.A3927='';
				grid.view.refresh();
				$h.alert('系统提示：',"政治面貌与第三党派不能一致，默认将第三党派的数据清空！",null,200);
 			}
			
		}
		
		
		
		function a01InsertEmptyRow(ds) {
			var dstorecount = ds.getCount();
			for (var gi = 0; gi < 5 - dstorecount; gi++) {
				a01Savecond();
			}
		}
		function a01Savecond() {
			//document.getElementById("conditionName").focus();
			var grid = odin.ext.getCmp('a01Grid');
			var store = grid.getStore();
			//生成uuid主键（a0000）
		    var s = [];
		    var hexDigits = "0123456789abcdef";
		    for (var i = 0; i < 36; i++) {
		        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		    }
		    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
		    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
		    s[8] = s[13] = s[18] = s[23] = "-";
		    var uuid = s.join("");
			var p = new Ext.data.Record({  
				A0000: uuid,  
				A0101: '',  
				A0184: '',  
				A0104: '',  
				A0107: '', 
				A0117: '',
				A0111A: '',  
				A0114A: '',
				A0140: '',
				A0141: '',
				A3921: '',
				A3927: '',
				A0195: '',
				A2949: '',
				A0120: '',
				A0115A: '',
				A0122: '',
				A0180: '',
				A0134: '',
				A0128: '',
				A0187A: '',
				A0160: '',
				A0165: '',
				A0121: ''
		    });
			store.insert(store.getCount(), p);
		}
		function a01Deletecond(){
			var a01Grid = odin.ext.getCmp('a01Grid');
			delGridColum(a01Grid);
		}
		
		
		function a01Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a01Grid');
			/*var sm = grid.getSelectionModel();
			var selections = sm.getSelections();*/
			var n = grid.store.data.length;// 获得总行数   
			for(var i=0;i<n;i++){
				//var a0000 = grid.getStore().getAt(i).data.A0000;
				var a0101 = grid.getStore().getAt(i).data.A0101;
				var a0184 = grid.getStore().getAt(i).data.A0184;
				var a0104 = grid.getStore().getAt(i).data.A0104;
				var a0107 = grid.getStore().getAt(i).data.A0107;
				var a0117 = grid.getStore().getAt(i).data.A0117;
				var a0111A = grid.getStore().getAt(i).data.A0111A;
				var a0114A = grid.getStore().getAt(i).data.A0114A;
				var a0140 = grid.getStore().getAt(i).data.A0140;
				var a0134 = grid.getStore().getAt(i).data.A0134;
				var a0128 = grid.getStore().getAt(i).data.A0128;
				var a0187A = grid.getStore().getAt(i).data.A0187A;
				var a0160 = grid.getStore().getAt(i).data.A0160;
				var a0165 = grid.getStore().getAt(i).data.A0165;
				var a0121 = grid.getStore().getAt(i).data.A0121;
				var a0141 = grid.getStore().getAt(i).data.A0141;
				var a3921 = grid.getStore().getAt(i).data.A3921;
				var a3927 = grid.getStore().getAt(i).data.A3927;
				var a2949 = grid.getStore().getAt(i).data.A2949;
				var a0120 = grid.getStore().getAt(i).data.A0120;
				var a0115A = grid.getStore().getAt(i).data.A0115A;
				var a0122 = grid.getStore().getAt(i).data.A0122;
				var a0180 = grid.getStore().getAt(i).data.A0180;
				var a0195 = grid.getStore().getAt(i).data.A0195;
				var row = i+1;
				var result = a01Check(a0101,a0184,a0104,a0107,a0117,a0111A,a0114A,a0140,a0134,a0128,a0187A,a0160,a0165,a0121,a0141,a3921,a3927,a0195,a2949,a0120,a0115A,a0122,a0180,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a01Save');

		}
		function a01Check(a0101,a0184,a0104,a0107,a0117,a0111A,a0114A,a0140,a0134,a0128,a0187A,a0160,a0165,a0121,a0141,a3921,a3927,a0195,a2949,a0120,a0115A,a0122,a0180,row){
			var num = 0;
			var message = '';
			if(a0101==''){
				message=message+'姓名不能为空！';
				num++;
			}
			
			//身份证验证
			var vtext = isIdCard(a0184,a0104);
			if(vtext!==true){
				message=message+vtext+"！";
				/* //$h.alert('系统提示：',vtext,null,320);
				$h.confirm("系统提示：",vtext+'，<br/>是否继续保存？',400,function(id) { 
					if("ok"==id){
						//Ext.getCmp('a0184').clearInvalid();
						Ext.Msg.wait('请稍候...','系统提示：');
						savePersonSub(a,b,confirm,false);
					}else{
						return false;
					}		
				}); */
			}
			if(a0184==''){
				message=message+'身份证号不能为空！';
				num++;
			}else{
				if(a0184.length>18){
					message=message+'身份证号不能超过18位！';
				}
			}
			if(a0101.length==1){
				message = message+'姓名不能为一个字！';
			}
			//alert(a0101.substr(a0101.length-1,1)==/\./);
			if(/^[\.\·]/.test(a0101)){
				message = message+'姓名不能以·开头！';
			}
			if(/[\.\·][\.\·]/.test(a0101)){
				message = message+'姓名不能连续出现2个·！';
			}
			if(/[\.\·]$/.test(a0101)){
				message = message+'姓名不能以·结尾！';
			}
			if(a0101.length>18){
				message = message+'姓名字数不能超过18！';
			}
			if(a0104==''){
				message = message+'性别不能为空！';
				num++;
			}
			if(a0107==''){
				message = message+'出生年月不能为空！';
				num++;
			}else{
				var datetext = $h.date(a0107);
				if(datetext!==true){
					message = message+'出生'+datetext+'!';
				}
			}
			
			var birthdaya0184 = getBirthdatByIdNo(a0184);
			if(a0184&&(a0107==''||(a0107!=birthdaya0184&&a0107!=birthdaya0184.substring(0, 6)))){
				message = message+'出生日期与身份证不一致！';
			}
			
			if(a0117==''){
				message = message+'民族不能为空！';
				num++;
			}
			if(a0111A==''){
				message = message+'籍贯不能为空！';
				num++;
			}
			if(a0114A==''){
				message = message+'出生地不能为空！';
				num++;
			}
			if(a0128==''){
				message = message+'健康状况不能为空！';
				num++;
			}
			if(a0165==''){
				message = message+'管理类别不能为空！';
				num++;
			}
			if(a0160==''){
				message = message+'人员类别不能为空！';
				num++;
			}
			if(a0121==''){
				message = message+'编制类型不能为空！';
				num++;
			}
			if(a0141==''||a0141==undefined){
				message = message+'政治面貌不能为空！';
				num++;
			}
			//当政治面貌填写的时候，入党时间必须填写
			if(a0141=="01"||a0141=="02"){
				if(a0140==''||a0140==undefined){
					message = message+'入党时间不能为空！';
				}
				
			}
			if(a3921==''||a3921==undefined){
				num++;
			}
			if(a3927==''||a3927==undefined){
				num++;
			}
			if(a0195==''||a0195==undefined){
				message = message+'统计关系所在单位不能为空！';
				num++;
			}
			
			if(a2949==''||a2949==undefined){
				num++;
			}
			if(a0140==''){//入党时间
				num++;
			}
			if(a0134==''){//参加工作时间
				message = message+'参加工作时间不能为空！';
				num++;
			}else{
				var datetext = $h.date(a0134);
				if(datetext!==true){
					message = message+'参加工作'+datetext+'!';
				}
			}
			if(a0187A==''){//专长
				num++;
			}
			if(a0120==''||a0120==undefined){
				num++;
			}
			if(a0115A==''||a0115A==undefined){
				num++;
			}
			if(a0122==''||a0122==undefined){
				num++;
			}
			if(a0180==''||a0180==undefined){
				num++;
			}
			if(message==''||num==23){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		
		//验证身份证号并获取出生日期
		function getBirthdatByIdNo(iIdNo) {
			var tmpStr = "";
			var idDate = "";
			var tmpInt = 0;
			var strReturn = "";
			
			iIdNo = trim(iIdNo);
			if (iIdNo.length == 15) {
				tmpStr = iIdNo.substring(6, 12);
				tmpStr = "19" + tmpStr;
				tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
				
				return tmpStr;
			} else {
				tmpStr = iIdNo.substring(6, 14);
				tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
				return tmpStr;
			}
		}
		function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };
		//身份证验证
		function isIdCard(sId,a0104){
			//将15位身份证号码转换为18位 
			var sId = changeFivteenToEighteen(sId);
			//alert(sId);
			var iSum=0 ;
			var info="" ;
			if(!/^\d{17}(\d|x)$/i.test(sId)) return "您输入的身份证长度或格式错误";
			sId=sId.replace(/x$/i,"a");
			if(aCity[parseInt(sId.substr(0,2))]==null) return "您的身份证地区非法";
			sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
			var d=new Date(sBirthday.replace(/-/g,"/")) ;
			if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";
			for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
			if(iSum%11!=1) return "您输入的身份证号非法";
			//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
			 
			//验证性别
			//判断身份证倒数第二位是否和性别一致
			var sex = sId.substr((sId.length-2), 1);
			//var a0104 = document.getElementById('a0104').value;		//性别
			
			var sexA0104 = sex%2;		//取余数
			
			if(sexA0104 == 0){
				sexA0104 = 2;
			}	
			
			if(sexA0104 != a0104){
				return "身份证号码倒数第二位数和性别不一致";
			}
			return true;
		}
		var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
		//将15位身份证号码转换为18位 	
		function changeFivteenToEighteen(obj) { 
			 if(obj.length == '15') 
			 { 
			  var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			  var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			  var cardTemp = 0, i;  
			  obj = obj.substr(0, 6) + '19' + obj.substr(6, obj.length - 6); 
			  for(i = 0; i < 17; i ++) 
			  { 
			   cardTemp += obj.substr(i, 1) * arrInt[i]; 
			  } 
			  obj += arrCh[cardTemp % 11]; 
			  return obj; 
			 } 
			 return obj; 
		};

		function a05Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a05Grid');
			var n = grid.store.data.length;// 获得总行数   
			if(n < 1){
				a05Savecond();
				$h.alert('系统提示：',"空表禁止保存！",null,120);
				return;
			}else{
				var a05MainJOB={};  
				for(var i = 0; i < n; i++){
					var a0501b = grid.getStore().getAt(i).data.a0501b;
					var a0524 =  grid.getStore().getAt(i).data.a0524;
					var job=i+1;
					if(a05MainJOB[a0501b] == null && (a0501b != null && a0501b != "") && a05MainJOB[a0524] == null){
						a05MainJOB[a0501b] = job;
						if( a0524 == 1){
							a05MainJOB[a0524] = job;
						}
						continue;
					}
					if(a05MainJOB[a0501b] != null && (a0501b != null && a0501b != "")){
						$h.alert('系统提示：','职务层次重复。原因：第'+ (i+1) +'行与第'+ a05MainJOB[a0501b] +'行的职务层次重复!',null,500);
						return false;
					}
					if(a05MainJOB[a0524] != null && (a0524 != null && a0524 != "") ){
						$h.alert('系统提示：','职务层次不可重复在任。原因：第'+ (i+1) +'行与第'+ a05MainJOB[a0524] +'行的状态均为<在任>!',null,500);
						return false;
					}
				};
			}
			for (var i = 0; i < n; i++) {
				var a0501b = grid.getStore().getAt(i).data.a0501b;
				var a0524 = grid.getStore().getAt(i).data.a0524;
				var a0504 = grid.getStore().getAt(i).data.a0504;
				var a0517 = grid.getStore().getAt(i).data.a0517;
				var row = i + 1;
				var result = a05Check(a0501b, a0524, a0504, a0517, row);
				if (result != 1) {
					collect += result + '<br>';
					$h.alert('系统提示：', collect, null, 500);
					return;
				}
			}
			radow.doEvent('a05Save');
		}
		function a05Check(a0501b, a0524, a0504, a0517, row) {
			var num = 0;
			var message = '';
			if (a0501b == '') {
				message += '职务层次不能为空！';
				num++;
			}
			if (a0524 == '') {
				message += '状态不能为空！';
				num++;
			}
			if (a0504 == '') {
				message += '批准日期不能为空！';
				num++;
			}
			if (a0517 == '' && a0524 == '以免') {
				message += '终止日期不能为空！';
				num++;
			}
			if (message == '' || num == 3) {
				return 1;//没错误
			}
			return '第' + row + '行的错误：' + message;
		}
		function a05Save2() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a05Grid2');
			var n = grid.store.data.length;// 获得总行数 
			if(n < 1){
				a05Savecond2();
				$h.alert('系统提示：',"空表禁止保存！",null,120);
				return;
			}else{
				var a05MainJOB2={};  
				for(var i = 0; i < n; i++){
					var a0501b2 = grid.getStore().getAt(i).data.a0501b2;
					var a0524 =  grid.getStore().getAt(i).data.a0524;
					var job2=i+1;
					if(a05MainJOB2[a0501b2] == null && (a0501b2 != null && a0501b2 != "") && a05MainJOB2[a0524] == null){
						a05MainJOB2[a0501b2] = job2;
						if( a0524 == 1){
							a05MainJOB2[a0524] = job2;
						}
						continue;
					}
					if(a05MainJOB2[a0501b2] != null && (a0501b2 != null && a0501b2 != "")){
						$h.alert('系统提示：','职级重复。原因：第'+ (i+1) +'行与第'+ a05MainJOB2[a0501b2] +'行的职级重复!',null,500);
						return false;
					}
					if(a05MainJOB2[a0524] != null && (a0524 != null && a0524 != "") ){
						$h.alert('系统提示：','职级不可重复在任。原因：第'+ (i+1) +'行与第'+ a05MainJOB2[a0524] +'行的状态均为<在任>!',null,500);
						return false;
					}
				};
			}
			for(var i=0;i<n;i++){
				var a0501b2 = grid.getStore().getAt(i).data.a0501b2;
				var a0524 = grid.getStore().getAt(i).data.a0524;
				var a0504 = grid.getStore().getAt(i).data.a0504;
				var a0517 = grid.getStore().getAt(i).data.a0517;
				var row = i+1;
				var result = a05Check2(a0501b2,a0524,a0504,a0517,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a05Save2');

		}
		function a05Check2(a0501b2,a0524,a0504,a0517,row){
			var num = 0;
			var message = '';
			if(a0501b2==''){
				message+='职务层次不能为空！';
				num++;
			}
			if(a0524==''){
				message+='状态不能为空！';
				num++;
			}
			if(a0504==''){
				message+='批准日期不能为空！';
				num++;
			}
			if(a0524==0 ){
				if(a0517 == ""){
					message+='终止日期不能为空！';
					num++;
				}
			}
			if(message==''||num==3 || num==4){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		function a08Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a08Grid');
			var n = grid.store.data.length;// 获得总行数   
			for(var i=0;i<n;i++){
				var a0837 = grid.getStore().getAt(i).data.a0837;
				var a0801b = grid.getStore().getAt(i).data.a0801b;
				var a0801a = grid.getStore().getAt(i).data.a0801a;
				var a0811 = grid.getStore().getAt(i).data.a0811;
				var a0901b = grid.getStore().getAt(i).data.a0901b;
				var a0901a = grid.getStore().getAt(i).data.a0901a;
				var a0814 = grid.getStore().getAt(i).data.a0814;
				var a0827 = grid.getStore().getAt(i).data.a0827;
				var a0824 = grid.getStore().getAt(i).data.a0824;
				var a0804 = grid.getStore().getAt(i).data.a0804;
				var a0807 = grid.getStore().getAt(i).data.a0807;
				var a0904 = grid.getStore().getAt(i).data.a0904;
				var row = i+1;
				var result = a08Check(a0837,a0801b,a0801a,a0811,a0901b,a0901a,a0814,a0827,a0824,a0804,a0807,a0904,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a08Save');

		}
		function a08Check(a0837,a0801b,a0801a,a0811,a0901b,a0901a,a0814,a0827,a0824,a0804,a0807,a0904,row){
			var num = 0;
			var message = '';
			if(a0837==''){
				message+='教育类别不能为空！';
				num++;
			}
			if(a0801b=='' && a0901b=='' ){
				message+='学历代码或学位代码不能同时为空！';
				num++;
			}
			if(a0801a=='' && a0801b!=''){
				message+='学历名称不能为空！';
				num++;
			}
			if(a0901b=='' && a0901a!=''){
				message+='学位名称不能为空！';
				num++;
			}
			if(a0811=='' ){
				message+='学制年限（年）不能为空！';
				num++;
			}
			if(a0814=='' ){
				message+='学校（单位）名称不能为空！';
				num++;
			}
			if(a0827=='' ){
				message+='所学专业类别不能为空！';
				num++;
			}
			if(a0824=='' ){
				message+='所学专业名称不能为空！';
				num++;
			}
			if(a0804=='' || a0804==null){
				message+=' 入学时间不能为空！';
				num++;
			}else{
				var datetextaA0804 = $h.date(a0804);
				if(datetextaA0804 != true){
					message += '入学'+datetextaA0804+'!';
					num++;
				}
			}
			if(a0807=='' || a0807 == null){
				message+='毕（肄）业时间不能为空！';
				num++;
			}else{
				var datetextaA0807 = $h.date(a0807);
				if(datetextaA0807 != true){
					message += '毕（肄）业'+datetextaA0807+'!';
					num++;
				}
			}
			
			if(a0811=='' ){
				message+='学制年限（年）不能为空！';
				num++;
			}
			if(a0904=='' || a0904==null){
				message+='学位授予时间不能为空！';
				num++;
			}else{
				var datetextaA0904 = $h.date(a0904);
				if(datetextaA0904 != true){
					message += '学位授予'+datetextaA0904+'!';
					num++;
				}
			}
		
			if(message==''||num==12){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		
		function a02Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a02Grid');
			var n = grid.store.data.length;// 获得总行数 
			if(n < 1){
				a02Savecond();
				$h.alert('系统提示：',"空表禁止保存！",null,120);
				return;
			}else{
				var a02MainOffices={};
				var A02Grid = odin.ext.getCmp('a02Grid');
				var n =  A02Grid.store.data.length;// 获得总行数   
				for(var i = 0; i < n; i++){
					var a0279 = A02Grid.getStore().getAt(i).data.a0279;
					if(a02MainOffices[a0279] == null && (a0279 != null && a0279 != "")){
						a02MainOffices[a0279] = i+1;
						continue;
					}
					if(a02MainOffices[a0279] != null && (a0279 != null && a0279 != "")){
						$h.alert('系统提示：','只能有一条主职务。原因：第'+ (i+1) +'行与第'+ a02MainOffices[a0279] +'行的主职务重复!',null,500);
						return false;
					}
				};
			}
			for(var i=0;i<n;i++){
				var a0201a = grid.getStore().getAt(i).data.a0201a;
				var a0201b = grid.getStore().getAt(i).data.a0201b;
				var a0215a = grid.getStore().getAt(i).data.a0215a;
				var a0243  = grid.getStore().getAt(i).data.a0243;
				var a0201d = grid.getStore().getAt(i).data.a0201d;
				var a0201e = grid.getStore().getAt(i).data.a0201e;
				var a0279 = grid.getStore().getAt(i).data.a0279;
				var a0281 = grid.getStore().getAt(i).data.a0281;
				var a0255 = grid.getStore().getAt(i).data.a0255;
				var a0265 = grid.getStore().getAt(i).data.a0265;
				var row = i+1;
				var result = a02Check(a0201a,a0201b,a0215a,a0243,a0201d,a0201e,a0279,a0281,a0255,a0265,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a02Save');
		}
		function a02Check(a0201a,a0201b,a0215a,a0243,a0201d,a0201e,a0279,a0281,a0255,a0265,row){
			var num=0;
			var message = '';
			if(a0281==''){
				message +='输出为空！';
			}
			if(a0201b==''){
				message +='任职机构为空！';
				num ++;
			}
			if(a0201a==''){
				message +='任职机构名称为空！';
			}
			if(a0215a==''){
				message +='职务名称为空！';
			}
			if(a0279==''){
				message +='主职务为空！';
			}
			if(a0201d=='是'){
				if(a0201e==''){
					message +='成员类别为空！';
				}
			}
			if(a0243=='' || a0243==null){
				message +='任职时间为空！';
			}else{
				var datetextaA0243 = $h.date(a0243);
				if(datetextaA0243 != true){
					message += '任职'+datetextaA0243+'!';
				}
			}
			if(a0255=='已免'){
				if(a0265=='' || a0265==null){
					message +='免职时间为空！';
				}else{
					var datetextaA0255 = $h.date(a0255);
					if(datetextaA0255 != true){
						message += '免职'+datetextaA0255+'!';
					}
				}
			}
			if(message=='' || num ==1){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		function saveConfirm4() {

		}
		function a14Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a14Grid');
			var n = grid.store.data.length;
			if(n < 1){
				a14Savecond();
				$h.alert('系统提示：',"空表禁止保存！",null,120);
				return;
			}
			for(var i=0;i<n;i++){
				var a1404b  = grid.getStore().getAt(i).data.a1404b;
				var a1404a  = grid.getStore().getAt(i).data.a1404a;
				var a1407  = grid.getStore().getAt(i).data.a1407;
				var a1424  = grid.getStore().getAt(i).data.a1424;
				var row = i+1;
				var result = a14Check(a1404b,a1404a,a1407,a1424,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a14Save');
		}
		function a14Check(a1404b,a1404a,a1407,a1424,row){
			var message = '';
			if(a1404b==''){
				message += '奖惩名称代码不能为空！';
			}
			if(a1404a==''){
				message += '奖惩名称不能为空！';
			}
			if(a1407==''){
				message += '批准日期不能为空！';
			}
			var datetexta1407 = $h.date(a1407);
			if(datetexta1407 != true){
				message += '批准'+datetexta1407+'!';
			}
			if(a1424 != "" && a1424 != null){
				var dateTextA1424 = $h.date(a1424);
				if(dateTextA1424 != true){
					message += '撤销'+dateTextA1424+'!';
				}
			}
			
			if(message==''){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		function a15Save() {
			if(a15YearRepeat()){
				var collect = '';//错误收集
				var grid = odin.ext.getCmp('a15Grid');
				var n = grid.store.data.length;
				if(n < 1){
					a15Savecond();
					$h.alert('系统提示：',"空表禁止保存！",null,120);
					return;
				}
				for(var i=0;i<n;i++){
					var a1521  = grid.getStore().getAt(i).data.a1521;
					var a1517  = grid.getStore().getAt(i).data.a1517;
					var row = i+1;
					var result = a15Check(a1521,a1517,row);
					if(result!=1){
						collect +=result+'<br>';
						$h.alert('系统提示：',collect,null,500);
						return;
					}
				}
				radow.doEvent('a15Save');
			};
			
		}
		function a15Check(a1521,a1517,row){
			var num = 0;
			var message = '';
			if(a1521=='' || a1521==null){
				message=message+'考核年度不能为空！';
				num ++;
			}
			if(a1517=='' || a1517==null){
				message=message+'考核结论不能为空！';
				num ++;
			}
			if(message=='' || num ==2){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}
		//保存时验证考核年度是否重复
		function a15YearRepeat(){
			var a15Years={};
			var A15Grid = odin.ext.getCmp('a15Grid');
			var n =  A15Grid.store.data.length;// 获得总行数   
			for(var i = 0; i < n; i++){
				var a1521=A15Grid.getStore().getAt(i).data.a1521;
				if(a15Years[a1521] ==null && (a1521 != null && a1521 != "")){
					a15Years[a1521] = i+1;
					continue;
				}
				if(a15Years[a1521] !=null &&  (a1521 != null && a1521 != "")){
					$h.alert('系统提示：','考核年度重复!',null,500);
					//$h.alert('系统提示：','第'+ (i+1) +'行与第'+ a15Years[a1521] +'行的考核年度重复!',null,500);
					return false;
				}
			};
			
			return true;
		}
		
		//删除操作
		function deleteA06(){
			var delA0600="";
			var grid = odin.ext.getCmp('a06Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selected = selections[i];
				var a0600=selections[i].data.A0600;
				if(a0600!=""){
					delA0600 += a0600+"@";
				}; 
			}
			if(delA0600 != ""){
				delA0600 = delA0600.substring(0,delA0600.length-1);
				radow.doEvent('a06Delete',delA0600);
			}
			/* var a36Grid = odin.ext.getCmp('a36Grid');
			delGridColum(a36Grid); */
		}
		//专业技术职务的保存
		function a06Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a06Grid');
			/*var sm = grid.getSelectionModel();
			var selections = sm.getSelections();*/
			var n = grid.store.data.length;// 获得总行数   
			for(var i=0;i<n;i++){
				//var a0000 = grid.getStore().getAt(i).data.A0000;
				var a0699 = grid.getStore().getAt(i).data.A0699;
				var a0601 = grid.getStore().getAt(i).data.A0601;
				var a0602 = grid.getStore().getAt(i).data.A0602;
				var a0604 = grid.getStore().getAt(i).data.A0604;
				var a0607 = grid.getStore().getAt(i).data.A0607;
				var a0611 = grid.getStore().getAt(i).data.A0611;
				var row = i+1;
				var result = a06Check(a0699,a0601,a0602,a0604,a0607,a0611,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a06Save');
		}
		function a06Check(a0699,a0601,a0602,a0604,a0607,a0611,row){
			var num = 0;
			var message = '';
			if(a0699==''||a0699==undefined){
				message=message+'必填是否输出！';
				num++;
			}
			if(a0601==''){
				message=message+'专业技术资格代码不能为空！';
				num++;
			}
			if(a0602==''){
				num++;
			}
			if(a0604==''){
				num++;
			}else{
				var datetext = $h.date(a0604);
				if(datetext!==true){
					message = message+'获得资格日期'+datetext+'！';
				}
			}
			if(a0607==''){
				num++;
			}
			if(a0611==''){
				num++;
			}
			if(message==''||num==6){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message;
		}

		function saveConfirm6() {

		}

		function saveConfirm7() {

		}

		function saveConfirm8() {

		}
		//家庭成员的保存
		function a36Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a36Grid');
			/*var sm = grid.getSelectionModel();
			var selections = sm.getSelections();*/
			var n = grid.store.data.length;// 获得总行数   
			for(var i=0;i<n;i++){
				var a3601 = grid.getStore().getAt(i).data.A3601;
				var a3604A = grid.getStore().getAt(i).data.A3604A;
				var a3607 = grid.getStore().getAt(i).data.A3607;
				var a3627 = grid.getStore().getAt(i).data.A3627;
				var a3611 = grid.getStore().getAt(i).data.A3611;
				var row = i+1;
				var result = a36Check(a3601,a3604A,a3607,a3627,a3611,row);
				if(result!=1){
					collect  = result;
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a36Save');
		}
		function  a36Check(a3601,a3604A,a3607,a3627,a3611,row){
			var num =0;//计数器
			var message = '';//错误信息
			if(a3601==''){
				message=message+'姓名不能为空！';
				num++;
			}
			if(a3604A==''){
				message+='称谓不能为空！';
				num++;
			}
			if(a3607==''){
				num++;
			}else{
				var datetext = $h.date(a3607);
				if(datetext!==true){
					message = message+'出生'+datetext+'！';
				}
			}
			if(a3627==''){
				message+='政治面貌不能为空！';
				num++;
			}
			if(a3611==''){
				message+='工作单位及职务不能为空！';
				num++;
			}
			if(message==''||num==5){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message; 
		}
		
		
		//其他信息的保存
		function a99Z1Save() {
			var collect = '';//错误收集
			var grid = odin.ext.getCmp('a99Z1Grid');
			/*var sm = grid.getSelectionModel();
			var selections = sm.getSelections();*/
			var n = grid.store.data.length;// 获得总行数   
			for(var i=0;i<n;i++){
				//var a0000 = grid.getStore().getAt(i).data.A0000;
				var a99Z101 = grid.getStore().getAt(i).data.A99Z101;
				var a99Z102 = grid.getStore().getAt(i).data.A99Z102;
				var a99Z103 = grid.getStore().getAt(i).data.A99Z103;
				var A99Z104 = grid.getStore().getAt(i).data.A99Z104;
				var row = i+1;
				var result = a99Z1Check(a99Z101,a99Z102,a99Z103,A99Z104,row);
				if(result!=1){
					collect +=result+'<br>';
					$h.alert('系统提示：',collect,null,500);
					return;
				}
			}
			radow.doEvent('a99Z1Save');
			
		}
		function  a99Z1Check(a99Z101,a99Z102,a99Z103,A99Z104,row){
			var num =0;//计数器
			var message = '';//错误信息
			if(a99Z101==''){
				num++;
			}
			if(a99Z102==''){
				num++;
			}
			if(a99Z103==''){
				num++;
			}
			if(A99Z104==''){
				num++;
			}
			if(message==''||num==4){
				return 1;//没错误
			}
			return '第'+row+'行的错误：'+message; 
		}
		
		//专业技术删除操作
		function deleteA99Z1(){
			var delA99Z100="";
			var grid = odin.ext.getCmp('a99Z1Grid');
			var sm = grid.getSelectionModel();
			var selections = sm.getSelections();
			for (var i = 0; i < selections.length; i++) {
				var selected = selections[i];
				var A99Z100=selections[i].data.A99Z100;
				if(A99Z100!=""){
					delA99Z100 += A99Z100+"@";
				}; 
			}
			if(A99Z100 != ""){
				delA99Z100 = delA99Z100.substring(0,delA99Z100.length-1);
				radow.doEvent('A99Z1Delete',delA99Z100);
			}
			/* var a36Grid = odin.ext.getCmp('a36Grid');
			delGridColum(a36Grid); */
		}
		
		
		/* function celledit(e){
		var A0101 =e.record.data.A0101;
		var A0184 = e.record.data.A0184;
		document.getElementById('tab10A0101').innerHTML=A0101;
		document.getElementById('tab10A0184').innerHTML=A0184;
	} */
	/* function personm() {
		document.getElementById('tab10A0101').innerHTML=document.getElementById('A0101').value;
		document.getElementById('tab10A0184').innerHTML=document.getElementById('A0184').value;
	} */	
	
	
	
	
	//tab页切换当前页面的显示  
	function select(){
		var grid = odin.ext.getCmp('a01Grid');
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		var a0101 = '';
		var a0184 = '';
		/* for(var i=0;i<selections.length;i++){
			var selected = selections[i];
			//store.remove(selected);
			*///console.log(selections);
			if(selections[0]==undefined){//直接跳转,防止报错
				return;
			}
			a0000 = selections[0].data.A0000;
			a0101 = selections[0].data.A0101;
			a0184 = selections[0].data.A0184;
		/* } */
		if(a0101==null||a0101==''){
			Ext.Msg.alert('提示', "请填写完姓名！！");
			//不让tab跳转
			odin.ext.getCmp('tab').activate('tab1');
			return;
		}
		if(a0184==null||a0184==''){
			Ext.Msg.alert('提示', "请填写完身份证号！！");
			//不让tab跳转
			odin.ext.getCmp('tab').activate('tab1');
			return;
		}
		/* document.getElementById('personA0000').value=a0000; */
		if(odin.ext.getCmp('tab').getActiveTab().id=='tab2'){
			document.getElementById('tab2A0101').innerHTML=a0101;
			document.getElementById('tab2A0184').innerHTML=a0184;
			Ext.getCmp("a02Grid").store.reload();
			radow.doEvent('jspGogridquery','a02Grid@a02Savecond');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab3'){
			document.getElementById('tab3A0184').innerHTML=a0184;
			document.getElementById('tab3A0101').innerHTML=a0101;
			Ext.getCmp("a05Grid").store.reload();
			radow.doEvent("jspGogridquery","a05Grid@a05Savecond@and A0531 = '0'");
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab4'){
			document.getElementById('tab4A0184').innerHTML=a0184;
			document.getElementById('tab4A0101').innerHTML=a0101;
			Ext.getCmp("a05Grid2").store.reload();
			radow.doEvent("a05JspGogridquery","a05Grid2@a05Savecond2@and A0531 = '1'");
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab5'){
			document.getElementById('tab5A0184').innerHTML=a0184;
			document.getElementById('tab5A0101').innerHTML=a0101;
			radow.doEvent('a06Check');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab6'){
			document.getElementById('tab6A0184').innerHTML=a0184;
			document.getElementById('tab6A0101').innerHTML=a0101;
			Ext.getCmp("a08Grid").store.reload();
			radow.doEvent('jspGogridquery','a08Grid@a08Savecond');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab7'){
			document.getElementById('tab7A0184').innerHTML=a0184;
			document.getElementById('tab7A0101').innerHTML=a0101;
			Ext.getCmp("a14Grid").store.reload();
			radow.doEvent('jspGogridquery','a14Grid@a14Savecond@order by A1407 desc');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab8'){
			document.getElementById('tab8A0184').innerHTML=a0184;
			document.getElementById('tab8A0101').innerHTML=a0101;
			Ext.getCmp("a15Grid").store.reload();
			radow.doEvent('jspGogridquery','a15Grid@a15Savecond@order by A1521 desc');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab9'){
			document.getElementById('tab9A0184').innerHTML=a0184;
			document.getElementById('tab9A0101').innerHTML=a0101;
			radow.doEvent('a36Check');
		}else if(odin.ext.getCmp('tab').getActiveTab().id=='tab10'){
			document.getElementById('tab10A0184').innerHTML=a0184;
			document.getElementById('tab10A0101').innerHTML=a0101;
			var grid = odin.ext.getCmp('a99Z1Grid');
			grid.addListener=('afteredit',function(){
	            /* if($("#A99Z101").val()=="否") {
	            	grid.view.getCell(0,3).style.visibility = 'hidden';
	        	}else{
					grid.view.getCell(0,3).style.visibility = 'visible';
				} */
			
			});
			radow.doEvent('a99Z1Check');
		}
		/* document.getElementById('tab2A0101').innerHTML=a0101;
		document.getElementById('tab2A0184').innerHTML=a0184; */
		/* document.getElementById('tab3A0184').innerHTML=a0184;
		document.getElementById('tab3A0101').innerHTML=a0101; */
		/* document.getElementById('tab4A0184').innerHTML=a0184;
		document.getElementById('tab4A0184').innerHTML=a0184; */
		/* document.getElementById('tab5A0101').innerHTML=a0101;
		document.getElementById('tab5A0184').innerHTML=a0184;
		document.getElementById('tab6A0184').innerHTML=a0184;
		document.getElementById('tab6A0101').innerHTML=a0101;
		document.getElementById('tab7A0184').innerHTML=a0184;
		document.getElementById('tab7A0184').innerHTML=a0184;
		document.getElementById('tab8A0101').innerHTML=a0101;
		document.getElementById('tab8A0184').innerHTML=a0184;
		document.getElementById('tab10A0101').innerHTML=a0101;
		document.getElementById('tab10A0184').innerHTML=a0184;
		document.getElementById('tab9A0101').innerHTML=a0101;
		document.getElementById('tab9A0184').innerHTML=a0184; */
	}
	function delGridColum(grid){
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			store.remove(selected);
		}
		grid.view.refresh();
	}
	
	//删除表格上的数据(pagem调用)
	function delData(Grid){
		var grid = odin.ext.getCmp(Grid);
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			grid.store.remove(selected);
		}
		grid.view.refresh();
		/* var a36Grid = odin.ext.getCmp('a36Grid');
		delGridColum(a36Grid); */
	}
		
	/* function hint(data){//提示保存的人员和更新的人员
		$h.alert('系统提示：',data,null,500);
	}	 */
	</script>
	<odin:hidden property="type" value=""/>
	<!-- //a0000的隐藏值 -->
	<odin:hidden property="personA0000" value=""/>
	<script >
	function a01Order(){
		var grid = odin.ext.getCmp("a01Grid");
		var data = "";
		if(grid.colModel.config[1].header!="序号"){
			$h.alert('系统提示：', "序号不能拖动保存！", null, 260);
			return;
		}
		
		for(var i=1;i<24;i++){
			data+=grid.colModel.config[i].header+",";
			
		}
		radow.doEvent('a01OrderSave',data);
	}
	
	//自定义list
	function List() {  
	    this.value = [];  
	  
	    /* 添加 */  
	    this.add = function(obj) {  
	        return this.value.push(obj);  
	    };  
	  
	    /* 大小 */  
	    this.size = function() {  
	        return this.value.length;  
	    };  
	  
	    /* 返回指定索引的值 */  
	    this.get = function(index) {  
	        return this.value[index];  
	    };  
	  
	    /* 删除指定索引的值 */  
	    this.remove = function(index) {  
	        this.value.splice(index,1);  
	        return this.value;  
	    };  
	  
	    /* 删除全部值 */  
	    this.removeAll = function() {  
	        return this.value = [];  
	    };  
	  
	    /* 是否包含某个对象 */  
	    this.constains = function(obj) {  
	        for ( var i in this.value) {  
	            if (obj == this.value[i]) {  
	                return true;  
	            } else {  
	                continue;  
	            }  
	        }  
	        return false;  
	    };  
	      
	    /* 是否包含某个对象 */  
	    this.getAll = function() {  
	        var allInfos = '';  
	        for ( var i in this.value) {  
	            if(i != (value.length-1)){  
	                allInfos += this.value[i]+",";  
	            }else{  
	                allInfos += this.value[i];  
	            }  
	        }  
	        alert(allInfos);  
	        return allInfos += this.value[i]+",";;  
	    };  
	      
	} 
	</script>
	<odin:gridSelectColJs2 name="A0104" codeType="GB2261"></odin:gridSelectColJs2><!-- 性别 -->
	<odin:gridSelectColJs2 name="A0117" codeType="GB3304"></odin:gridSelectColJs2><!-- 民族 -->
	<odin:gridSelectColJs2 name="A0160" codeType="ZB125"></odin:gridSelectColJs2><!-- 人员类别 -->
	<odin:gridSelectColJs2 name="A0165" codeType="ZB130"></odin:gridSelectColJs2><!-- 管理类别 -->
	<odin:gridSelectColJs2 name="A0121" codeType="ZB135"></odin:gridSelectColJs2><!-- 编制类别 -->
	<odin:gridSelectColJs2 name="A0601" codeType="GB8561"></odin:gridSelectColJs2><!-- 专业技术资格代码 -->
	<odin:gridSelectColJs2 name="A0607" codeType="ZB24"></odin:gridSelectColJs2><!--取得资格途径 -->
	<odin:gridSelectColJs2 name="A0699" codeType="BOOLEAN"></odin:gridSelectColJs2><!--是否输出 -->
	<odin:gridSelectColJs2 name="A0141" codeType="GB4762"></odin:gridSelectColJs2><!-- 政治面貌 -->
	<odin:gridSelectColJs2 name="A3921" codeType="GB4762"></odin:gridSelectColJs2><!-- 第二党派 -->
	<odin:gridSelectColJs2 name="A3927" codeType="GB4762"></odin:gridSelectColJs2><!-- 第三党派 -->
	<odin:gridSelectColJs2 name="A0120" codeType="ZB134"></odin:gridSelectColJs2><!-- 级别-->
	<odin:gridSelectColJs2 name="A0115A" codeType="ZB01"></odin:gridSelectColJs2><!-- 成长地 -->
	<odin:gridSelectColJs2 name="A0122" codeType="ZB139"></odin:gridSelectColJs2><!-- 专业技术类公务员任职资格 -->
	
	<odin:gridSelectColJs2 name="A99Z101" codeType="XZ09"></odin:gridSelectColJs2>
	<odin:gridSelectColJs2 name="A99Z103" codeType="XZ09"></odin:gridSelectColJs2>
	
	<odin:gridSelectColJs2 name="a0201d" codeType="VSC007"></odin:gridSelectColJs2>		<!-- 领导成员 -->
	<odin:gridSelectColJs2 name="a0219" codeType="JOB001"></odin:gridSelectColJs2>		<!-- 领导职务 -->
	<odin:gridSelectColJs2 name="a0251b" codeType="VSC007"></odin:gridSelectColJs2>		<!-- 破格提拔 -->
	<odin:gridSelectColJs2 name="a0255" codeType="ZB14"></odin:gridSelectColJs2>	<!-- 任免状态 -->
	<odin:gridSelectColJs2 name="a0281" codeType="BOOLEAN"></odin:gridSelectColJs2>		<!-- 输出 -->
	<odin:gridSelectColJs2 name="a0279" codeType="XZ09"></odin:gridSelectColJs2>		<!-- 主职务 -->
	<odin:gridSelectColJs2 name="a0501b" codeType="ZB09"></odin:gridSelectColJs2><!-- 职务层次 -->
	<odin:gridSelectColJs2 name="a0501b2" codeType="ZB148"></odin:gridSelectColJs2><!-- 职级 -->
	<odin:gridSelectColJs2 name="a0837" codeType="ZB123"></odin:gridSelectColJs2><!-- 教育类别 -->
	<odin:gridSelectColJs2 name="a0801B" codeType="ZB64"></odin:gridSelectColJs2><!-- 学历代码 -->
	<odin:gridSelectColJs2 name="a0901b" codeType="GB6864"></odin:gridSelectColJs2><!-- 学位代码 -->
	<odin:gridSelectColJs2 name="a0827" codeType="GB16835"></odin:gridSelectColJs2><!-- 所学专业类别   -->

	<odin:gridSelectColJs2 name="a0201e" codeType="ZB129"></odin:gridSelectColJs2>		<!-- 成员类别 -->
	<odin:gridSelectColJs2 name="a0247" codeType="ZB122"></odin:gridSelectColJs2>		<!-- 选拔任用方式 -->
	<odin:gridSelectColJs2 name="a1517" codeType="ZB18"></odin:gridSelectColJs2>	<!-- 考核结论 -->
	<odin:gridSelectColJs2 name="a1404b" codeType="ZB65"></odin:gridSelectColJs2>	<!-- 奖惩名称代码 -->
	<odin:gridSelectColJs2 name="a1415" codeType="ZB09"></odin:gridSelectColJs2>	<!-- 受奖惩时职务层次 -->
	<odin:gridSelectColJs2 name="a1414" codeType="ZB03"></odin:gridSelectColJs2>	<!-- 批准机关级别 -->
	<odin:gridSelectColJs2 name="a1428" codeType="ZB128"></odin:gridSelectColJs2>	<!-- 批准机关性质 -->