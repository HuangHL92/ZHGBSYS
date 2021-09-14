<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<!-- 样式 开始 -->
<style>
/* .x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
} */
.edui-default{height:170px;}
.editorclass{height:170px; width: 835px; margin: 5, 0, 0, 5; overflow-y: scroll; overflow-x: hidden; border: 1px solid #B2B2B2;}
.editorclass1{height:60px; width: 1115px; margin: 5, 0, 0, 5; overflow-y: scroll; overflow-x: hidden; border: 1px solid #B2B2B2;}
/* #contenttext2{margin: 5, 0, 0, 5; height:523px;width: 740px;} */
</style>
<!-- 样式 结束 -->


<!-- JS 开始 -->
<script type="text/javascript" charset="gbk" src="rmb/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.config.js"></script>

<script type="text/javascript" charset="gbk" src="ueditor/ueditor.all.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败--> 
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文--> 
<script type="text/javascript" charset="gbk" src="ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<!-- JS 结束 -->



<!-- 界面信息 开始 -->
<body style="overflow-y:hidden;">
<odin:hidden property="a0000"/>
<odin:hidden property="a0101"/>
<odin:hidden property="a0184"/>
<!-- <div id="jlcontent" style="width:835px"> -->
<odin:toolBar property="a1701Show" applyTo="a1701Bar">
	<odin:textForToolBar text="<font style=&quot;font-size:12px;font-weight:bolder;&quot;>简历预览</font><font style=&quot;font-size:12px;color:red;font-weight:bolder;&quot;>（简历输出样式以预览效果为准，双击简历文本框区域切换至简历维护页面）</font>"></odin:textForToolBar>
	<odin:fill />
<%-- 	<odin:buttonForToolBar id="bdjgBtn" handler="bdjgFunc" text="简历比对" icon="image/u53.png"/> --%>
	<odin:buttonForToolBar id="splitBtn" handler="initJLForOne" text="简历拆分" icon="image/icon040a15.gif"/>
	<odin:buttonForToolBar id="toUp" handler="changeToUp" text="简历维护" icon="image/icon040a2.gif"/>
</odin:toolBar>
<div id= "tabJLInit" style="display:none">
	<odin:tab id="tabInit">
	<odin:tabModel>
		<odin:tabItem title='简历初始化' id='JLInit'></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="JLInit" className="JLInit">
	<table width="100%">
   		<tr>
   			<td>
	   			<odin:toolBar property="a1701Hand" applyTo="a1701HandBar">
					<odin:textForToolBar text="<font style=&quot;font-size:12px;color:red;&quot;>初次维护简历信息可在下方输入框内手动填写或使用复制粘贴，只可保存一次！</font>"></odin:textForToolBar>
					<odin:fill />
					<odin:buttonForToolBar id="saveBtn" handler="saveA1701" text="简历保存并拆分" icon="image/icon040a15.gif"/>
				</odin:toolBar>
   				<div id="a1701HandBar"></div>
   				<div id="editorHandId" class="editorHandClass">
					<textarea id="a1701ByHand" style="height:170px; width: 857px; font-size:14px;"></textarea>
				</div>
   			</td>
   		</tr>
   	</table>
   	</odin:tabCont>
   	</odin:tab>
</div>
<div id="tabJLup">
<odin:tab id="tab">
<odin:tabModel>
<%-- 	<odin:tabItem title='简历预览' id='editshow' ></odin:tabItem> --%>
	<odin:tabItem title='简历维护' id='editup'  isLast="true"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex='editshow' className="editshow">
   	<table width="100%" id="editLook">
   		<tr>
   			<td>
   				<div id="a1701Bar"></div>
   				<div id="editorid" class="editorclass">
    				<odin:hidden property="a17" name="简历"/>
					<script id="editor" type="text/plain" style="width:100%;height:100%; "></script>
				</div>
   			</td>
   		</tr>
   	</table>
</odin:tabCont>
<odin:tabCont itemIndex='editup' className="editup">
   	<table width="100%" style="overflow:auto">
   		<tr>
   			<td style="height:170px; width: 857px;">
    			<odin:toolBar property="pageTopBar">
					<odin:textForToolBar text="<font style=&quot;font-size:12px;font-weight:bolder;&quot;>简历维护</font><font style=&quot;font-size:12px;color:red;font-weight:bolder;&quot;>（默认显示主简历时间列表，简历输出样式以预览效果为准！）</font> "></odin:textForToolBar>
					<odin:fill />
					<%-- <odin:buttonForToolBar id="gzgl" text="工资信息" icon="image/u53.png"/> --%>
					<%-- <odin:buttonForToolBar id="bdjgBtn" handler="bdjgFunc" text="简历比对" icon="image/u53.png"/> --%>
<%-- 					<odin:buttonForToolBar id="splitBtn" handler="initJLForOne" text="简历拆分" icon="image/icon040a15.gif"/> --%>
					<odin:buttonForToolBar id="addBtn" handler="addFunc" text="新增行" icon="image/icon040a2.gif"/>
					<%-- <odin:buttonForToolBar id="saveBtn" handler="saveFunc" text="预览简历" icon="image/u53.png"/> --%>
					<odin:buttonForToolBar id="composeBtn" handler="composeFunc" text="保存并生成简历"   isLast="true"/>
				</odin:toolBar>
   				<odin:editgrid2 property="grid1" topBarId="pageTopBar" afteredit="cellEdit" width="670" height="420" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- 临时展示是否为期间等情况数据 1 是 0 不是 -->
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a1701" />
						<odin:gridDataCol name="a1702" />
						<odin:gridDataCol name="a1703" />
						<odin:gridDataCol name="a0221" />
						<odin:gridDataCol name="complete" />
						<odin:gridDataCol name="a1704" />
						<odin:gridDataCol name="a1705" />
						<odin:gridDataCol name="a1706" />
						<odin:gridDataCol name="a1707" />
						<odin:gridDataCol name="a1708" />
						<odin:gridDataCol name="a0192e" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 	dataIndex="a1799"  	header=""  	   width="40" editor="text" edited="false" align="center" renderer="indexFormatter"></odin:gridEditColumn2>
						<odin:gridColumn 		dataIndex="a1700"  	header="主键"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="6"	header="开始"  width="70"  editor="text" align="center" renderer="changeTrim" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="6"	header="结束"  width="70"  editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="complete" 	header="描述<font style=font-size:12px;color:red;>（需在某一行简历下方增加经历的，请先选择相应行号，<br/>再点击新增行。双击主简历描述单元格进行修改！）</font>"  width="350"  editor="text" align="left" menuDisabled="true" sortable="false" edited="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a0221" codeType="ZB09" header="职务层次"  width="30"  edited="false" editor="select" align="center" filter="code_status = '1' and code_leaf = '1'" menuDisabled="true" sortable="false" hidden="true"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a0192e" codeType="ZB148" header="职级"  width="30"  edited="false" editor="select" align="center" filter="code_status = '1' and code_leaf = '1'" menuDisabled="true" sortable="false"  hidden="true"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="重点岗位" editorId="asdasd2" width="120"  edited="true" editor="selectTree"  align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="单位类型" editorId="asd1" width="70"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="分管工作类型"  width="120"  edited="true" editor="selectTree" ischecked="true" editorId="asd" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="分管工作成效"  width="120"  edited="true" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="备注"  width="150"  edited="true" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="caozuo" 	header=""  width="40" edited="false" editor="text" align="center" renderer="caozuo"  menuDisabled="true" sortable="false" isLast="true"></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
							data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid2>
   			</td>
   		</tr>
   	</table>
   	<odin:hidden property="a1700"/>
	<odin:hidden property="a0000"/>
	<odin:hidden property="a1799"/>
<%--  	<odin:groupBox title="简历（当前主简历区域）预览" width="100%" >
		<div id="editorid1" class="editorclass1">   --%>
			<odin:hidden property="a1701z" name="简历" />
<%--  			<script id="editor1" type="text/plain" style="width: 100%;   height:30;"></script>
		</div>
	</odin:groupBox>   --%>
	<odin:groupBox title="简历详情维护" width="100%">
	<odin:toolBar property="saveBtn" applyTo="save">
	<odin:fill/>
	<odin:buttonForToolBar id="composeBtn" handler="composeFunc1" text="保存并更新主简历描述" icon="image/btn_adopt.png" isLast="true"/>
	</odin:toolBar>
	<table style="width:100%;">
		<tr>
			<td>
				<div id="save"></div>
			</td>
		</tr>
		<tr>
			<td>
			<table>
			<tr style="height:30;">
				<odin:NewDateEditTag property="a1701" label="开始时间" isCheck="false" labelSpanId="startSpanId" maxlength="6"  width="170"></odin:NewDateEditTag>
				<odin:NewDateEditTag property="a1702" label="结束时间" isCheck="false" labelSpanId="endSpanId" maxlength="6" width="170"></odin:NewDateEditTag>
				<odin:textEdit property="a1703" label="简历描述"  width="220" colspan="6"></odin:textEdit>
				<odin:select2 property="a1706" data="['党务类','党务类'],['综合管理类','综合管理类'],['制造业和工业经济类','制造业和工业经济类']
				,['大数据和信息技术类','大数据和信息技术类'],['城建城管类','城建城管类'],['教育卫生类','教育卫生类'],['服务商贸类','服务商贸类']
				,['农业农村类','农业农村类'],['文化发展和旅游类','文化发展和旅游类'],['公检法政法类','公检法政法类'],['企业经营管理类','企业经营管理类']
				,['金融财务类','金融财务类']" 
				width="130"  label="分管工作类型" multiSelect="true" />
				
			</tr>
			<tr>	
				<tags:ComBoxWithTree property="a1705" codetype="JL02"  width="150"  label="重点岗位" nodeDblclick="a1705change" />
				<odin:select2 property="a1704" codeType="JL02"  width="100"  label="单位类型" readonly="true"/>	
				<odin:textEdit property="a1707" label="分管工作成效"  width="220" colspan="6"></odin:textEdit>
				<odin:textEdit property="a1708" label="备注" width="220" colspan="6"></odin:textEdit>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<div id="zzjlgrid">
				<odin:toolBar property="zzjl" applyTo="gridTopBar">
					<odin:fill />
					<odin:buttonForToolBar id="addBtn" handler="addFunc1" isLast="true" text="新增行" icon="image/icon040a2.gif"/>
				</odin:toolBar>
				<div id ="gridTopBar"></div>
   				<odin:editgrid2 property="grid" title="温馨提示：请在此列表中添加当前主简历‘其间’或‘跨时间段’部分简历信息,双击进行维护" afteredit="cellEdit" width="670"  height="130" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- 临时展示是否为期间等情况数据 1 是 0 不是 -->
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a1701" />
						<odin:gridDataCol name="a1702" />
						<odin:gridDataCol name="a1703" />
						<odin:gridDataCol name="a0221" />
						<odin:gridDataCol name="complete"/>
						<odin:gridDataCol name="a1704" />
						<odin:gridDataCol name="a1705" />
						<odin:gridDataCol name="a1706" />
						<odin:gridDataCol name="a1707" />
						<odin:gridDataCol name="a1708" />
						<odin:gridDataCol name="a0192e" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 	dataIndex="a1799"  	header=""  	   width="40" editor="text" edited="false" align="center" renderer="indexFormatter"></odin:gridEditColumn2>
						<odin:gridColumn 		dataIndex="a1700"  	header="主键"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="8"	header="开始"  width="70" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="8"	header="结束"  width="70" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1703" 	header="描述"  width="350" edited="true" editorId="dsa11" editor="text" align="left" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="重点岗位" editorId="dsa2" width="120"  edited="true" editor="selectTree"  align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="单位类型" editorId="dsa1" width="70"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="分管工作类型"  width="120"  edited="true" editor="selectTree" ischecked="true" editorId="dsa" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="分管工作成效"  width="120"  edited="true" editorId="dsa4" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="备注"  width="150"  edited="true" editorId="dsa5" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="caozuo" 	header=""  width="35" edited="false" editor="text" align="center" renderer="caozuo1"  menuDisabled="true" sortable="false" isLast="true"></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
							data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid2>
			</div>
			</td>
		</tr>
	</table>
	</odin:groupBox>
<%--    	<table>
   		<tr style="height:40px;">  			
   			<odin:textarea property="lead" label="重要职务重要经历"></odin:textarea>
   		</tr>
   	</table> --%>
   	<odin:hidden property="lead"/>
   	
</odin:tabCont>
</odin:tab>
</div>
</body>
<!-- </div>	 -->

<!-- 界面信息 结束 -->


<!-- 函数 开始 -->
<script type="text/javascript">
 <%
/* String RrmbCodeType = (String)session.getAttribute("RrmbCodeType"); */
String RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	/* var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.EXTRA_TAGS[v[i]]){
				returnV += CodeTypeJson.EXTRA_TAGS[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	} */
	//Ext.getCmp('grid1').fireEvent("afteredit") 
	return value;
	
} 


Ext.onReady(function(){
	if(parent.document.getElementById("a0000").value!=''&&parent.document.getElementById("a0000").value!=null){
		document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	}else{
		var parentParam = window.dialogArguments.param;
		document.getElementById('a0000').value = parentParam.a0000;
	}
	
	
	
	
	var pgrid = Ext.getCmp('grid1');
	var dstore = pgrid.getStore();
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
					ddGroup : 'GridDD',
					copy : false,
					notifyDrop : function(dd,e,data){
						//选中了多少行
						var rows = data.selections;
						//拖动到第几行
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						//修改store
						for ( i=0; i<rows.length; i++){
							var rowData = rows[i];
							if (!this.copy) dstore.remove(rowData);
							dstore.insert(index, rowData);
						}
						pgrid.view.refresh();
					}
				});
	/*dstore.on('load',function(){
		insertEmptyRow(this);
	});*/
	windowOnresize();
});
function changeTab(){
	document.getElementById('tabJLInit').style.display='none';
	document.getElementById('tabJLup').style.display='block';
	windowOnresize();
}
//调整grid为拖拽窗口自适应 
window.onresize = function() {
	windowOnresize();
}; 


function isQiJian(a1701str) {
    if(a1701str.indexOf("(其") == 0||a1701str.indexOf("（其") == 0||
     a1701str.indexOf("(期") == 0||a1701str.indexOf("（期") == 0||
     a1701str.indexOf("(1") == 0 ||a1701str.indexOf("（1") == 0||
     a1701str.indexOf("(2") == 0 ||a1701str.indexOf("（2") == 0){
      return true;
    }else{
      return false;
    }
}
//获取字符串对象，无论当对象为空时，返回空字符串
function getObjStrOutNull(value) {
	return validatorNull(value) ? "" : value;
}
//判断元素是否为 空,"空",""
function validatorNull(value) {
	if(typeof value == "undefined" || value == null || value == ""){
		return true;
	}else{
		return false;
	}
}

function zzjlShow(a1700,a1799){
	var a0000=document.getElementById("a0000").value;
	var param="{'a1700':'"+a1700+"','a0000':'"+a0000+"','a1799':'"+a1799+"'}"
	$h.openWin('addZZJianLi', 'pages.customquery.person.AddZZJianLi', "简历详情维护", 800, 600, param, '<%=request.getContextPath() %>', window, {maximizable:false, resizable:true, draggable:true}, true);
	//$h.openWin('addScheme',   'pages.bzpj.PJZBScheme','指标方案维护',                 430,635,null,'<%=request.getContextPath()%>',      null, p,true);
}
 function windowOnresize() {
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	$('#tabJLInit').height(clientHeight);
	$('#tabJLInit').width(clientWidth);
	$('#tabJLup').height(clientHeight);
	$('#tabJLup').width(clientWidth);
	$('#tab').height(clientHeight);
	$('#tab').width(clientWidth);
	$('.odinTabCont').height(clientHeight-1);
	$('.odinTabCont').width(clientWidth-1);
	$('#editorid').height(clientHeight-50);
	$('#editorid').width(clientWidth-14);
	$('#editorHandId').height(clientHeight-60);
	$('#editorHandId').width(clientWidth-10);
	$('#editor').height(clientHeight);
	$('#editor').width(clientWidth-20);
	$('#a1701ByHand').height(clientHeight-60);
	$('#a1701ByHand').width(clientWidth-10);
	$('.editorid1').height(120);
	$('.editor1').height(110);
	$('#lead').width(clientWidth-110);
	var grid1 = Ext.getCmp('grid1');
	grid1.setHeight(clientHeight/2);
	var grid = Ext.getCmp('grid');
	grid.setHeight(clientHeight/4);
}
function saveA1701(){
	var a1701=document.getElementById("a1701ByHand").value;
	if(a1701){
		radow.doEvent("saveA1701",a1701);
	}else {
		odin.alert("请输入简历信息！", "", "系统提示");
	}
}
function bdjgFunc(){

	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
	var a0184 = document.getElementById("a0184").value;
	/*  $h.openWin('bdJianLi', "pages.publicServantManage.BdJianLi&bdJianLi="+Id+"&a0184="+a0184,name+"的简历比对结果", 500, 470, Id, contextPath, null, {
         maximizable: false,
         resizable: false,
         closeAction: 'close',
         data: {khcjId:khcjId,a0000:a0000}
     }); */
 	$h.openPageModeWin('bdJianLi',"pages.publicServantManage.BdJianLi&bdJianLi="+Id+"&a0184="+a0184,name+"的简历比对结果",950,560,Id,'<%=request.getContextPath() %>');

}
//插入空白行
function insertEmptyRow(ds){
	var dstorecount = ds.getCount();
	for(var gi=0;gi<8-dstorecount;gi++){
		savecond();
	}
}

//插入空白行
function savecond() {
	var grid = odin.ext.getCmp('grid1');
	var store = grid.getStore();
	var p = new Ext.data.Record({
		a1701: '',  
		a1702: '',  
		a1703: '',
		a1798: '1'
    });
	store.insert(store.getCount(), p);
}

//序号显示
function indexFormatter(value, params, record, rowIndex, colIndex, ds) {
  return rowIndex+1;
}

//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');

ue.addListener('blur',function(a,b,c){
	document.getElementById("a17").value = ue.getPlainTxt().trim();
});

var rowIndex = 0;
//用于记录当前存在行数
function isHaveRow(obj){
	rowIndex = rowIndex + parseInt(obj);
}

//去除不小心添加的空格
function changeTrim(value,params,record,rowIndex,colIndex,ds){
	if(value==null||value==''||value=='null'){
		return "";
	}else{
		return value.trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
	}
}

//操作
function caozuo(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid1').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='删除本行' onclick=\"delrowFunc('"+a1700+"');\">";
}

//简历回填
function toA1701(obj){
	ue = UE.getEditor('editor');
	
	ue.ready(function () {
		var a1701 = obj;
		a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
		ue.setContent("<p>"+a1701+"</p>", false);
		ue.fireEvent("selectionchange");
	});
	document.getElementById("a17").value = a1701Format(obj);
	window.dialogArguments.window.setJIANLI(document.getElementById("a17").value)
	if(!obj){
		document.getElementById('tabJLInit').style.display='block';
		document.getElementById('tabJLup').style.display='none';
	}
}

//简历格式化
function a1701Format(obj){
	var a1701 = obj.replace(/期间/g,'其间');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	return newA1701;
}

//拼接简历
function composeFunc(){
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid1').store;
	
	//获取三个信息项数组
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		odin.alert("请添加简历信息！", "", "系统提示");
		return false;
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//获取判断简历描述字符串
		if(a1703){
			if(isQiJian(a1703)){//如果存在期间或者用户主动换行的，则不进行处理
				continue;
			}
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		if(a1701 == '' && a1702 == '' && a1703 == ''){
			continue;
		}
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		//用于移除空白行
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	//检验多个未截止时间,校验是否连续
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "系统提示");
		return false;
	}

	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}

	changeToShow();
	radow.doEvent('compose');
}
function changeToShow(){
	odin.ext.getCmp('tab').activate('editup'); 
	odin.ext.getCmp('tab').activate('editshow');
}
function changeToUp(){
	odin.ext.getCmp('tab').activate('editshow');
	odin.ext.getCmp('tab').activate('editup');
}
//保存简历分割数据，但是未同步到A01
function saveFunc(){
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid1').store;
	
	//获取三个信息项数组
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		odin.alert("请添加简历信息！", "", "系统提示");
		return false;
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//获取判断简历描述字符串
		if(isQiJian(a1703)){//如果存在期间或者用户主动换行的，则不进行处理
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//用于移除空白行
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//检验多个未截止时间,校验是否连续
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "系统提示");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	
	radow.doEvent('save');
}

//检验简历完整性 + 检验简历连续性
function checkVarFunc(a1701Arr, a1702Arr, a1703Arr){
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//检验简历完整性
	for(var j = 0, n = a1702Arr.length; j < n; j++){
		msg = checkDate(a1701Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(a1702Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkJL(a1703Arr[j]);
		if(msg!=""){
			return msg;
		}
		if(a1701Arr[j] == ''){
			return "简历存在开始时间为空！";
		}
		else{
			if(a1702Arr[j] == '' && a1703Arr[j] == ''){
				return "简历内容存在为空！";
			}else if(a1702Arr[j] != '' && a1703Arr[j] == ''){
				return a1701Arr[j] + "到" + a1702Arr[j] + "简历内容为空！";
			}else if(a1702Arr[j] == '' && a1703Arr[j] != ''){
				num = num + 1;
			}
		}
		if(j>0){
			if(a1701Arr[j]!=a1702Arr[j-1]){
				return "存在简历时间"+a1701Arr[j]+"不连续！";
			}
		}
	}
	if(num > 1){
		return "简历信息存在超过1条记录无截止时间！";
	}
	
	//检验简历连续性
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '（' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "简历信息"+a1701Arr[i]+"到"+a1702Arr[i]+"的时间不连续！";
							}
						}
					}
				}
			}
		}
	} */
	
	return msg;
}

function checkDate(evalue){
	var reg = new RegExp("^[0-9]{6}$");
	
	if(evalue){
		var value = evalue.trim();
		//if(value.length!=6) odin.alert("检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存","","系统提示");
		if(!reg.test(value)) {
			return "检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存";
		}
		if(!/^[0-9]{6}$/.test(value)){
			return "检测到存在非6位时间数据！正确范例：202001。请检查无误后再保存";
		}
	}
	
	return "";
}
function checkJL(evalue){
	if(!evalue){
		return "检测到存在空白简历行，请检查无误后再保存！";
	}
	return "";
}
//列表编辑事件
function cellEdit(e){
	//修改过的行从0开始  e.row;
	//修改列 e.column;
	//原始值 e.originalValue;
	//修改后的值 e.value;
	//当前修改的grid e.grid;
	//正在被编辑的字段名 e.field;
	//正在被编辑的行 e.record
	var grid = odin.ext.getCmp('grid1');
	var ecolumn = grid.getColumnModel().getDataIndex(e.column);
	var evalue = e.value;
	var reg = new RegExp("^[0-9]*$");
	if("a1701" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
			if(!reg.test(value)) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
			if(!/^[0-9]*$/.test(value)) odin.alert("请输入6位开始时间！范例：202001","","系统提示");
		}
	}
	if("a1702" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("请输入6位截止时间！","","系统提示");
			if(!reg.test(value)) odin.alert("请输入6位截止时间！","","系统提示");
			if(!/^[0-9]*$/.test(value)) odin.alert("请输入6位截止时间！","","系统提示");
		}
	}
	if("a1703" == ecolumn){
		if(evalue == '') odin.alert("请输入简历内容！","","系统提示");
	}
}

//简历拆分程序
function splitFunc(){
	var obj = document.getElementById("a17").value;
	if(obj == null || obj.trim().length < 1){
		odin.alert("请输入内容！","","系统提示");
		return false;
	}
	var a1701 = obj.replace(/期间/g,'其间');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	
	/* 实现将原应该时一句的内容修改为一句 - 开始 , 实现方式倒序排列，将本属于一句描述，拼接为一句 */
	var a1701ArrayNew = new Array();//中间处理数组
	var a1701ArrayOld = new Array();//最后生成有效数组
	var a1701s="",a1701str="",indexNew=0;
	for(var index=a1701Array.length-1;index>=0;index--){
		a1701str = a1701Array[index].replace(" ","").replace("　","");
		a1701s = a1701str + a1701s;
		if(a1701str == '') continue;
		if(a1701str.indexOf("2")== 0||a1701str.indexOf("1")== 0||isQiJian(a1701str)){
			a1701ArrayNew[indexNew++]=a1701s;
			a1701s="";
			continue;
		}
	}
	/* 实现将原应该时一句的内容修改为一句 - 结束  再次倒叙将内容修改正过来*/
	for(var index=a1701ArrayNew.length-1,indexNew=0;index>=0;index--){
		var text = a1701ArrayNew[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701ArrayOld[indexNew++] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');//拼接新的方式
	}
	radow.doEvent('splitFunc', a1701ArrayOld.join("$$"));
}

//新增行
function addFunc(){
	
 	var hanghao = Ext.getCmp('grid1').getSelectionModel().lastActive;
	//alert(hanghao);
	if(!hanghao){
		hanghao=-1;
		}
	radow.addGridEmptyRow('grid1',(hanghao+1));
	try {
		Ext.getCmp('grid1').getSelectionModel().selectRow(hanghao+1);
		Ext.getCmp('grid1').getView().focusRow(hanghao+1);
	} catch (e) {
		console.log("发生异常！");
	}
	rowIndex = rowIndex + 1; 
}

//删除行
function delrowFunc(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid1');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("提示","是否确认删除本条简历及相应在职经历？",200,function(e){
		if("ok" == e){
			rowIndex = rowIndex - 1;
			var grid = odin.ext.getCmp('grid1');
			var store = grid.store;
			var hanghao = grid.getSelectionModel().lastActive;
			store.remove(store.getAt(hanghao));
			radow.doEvent("deleteRow",a1700);
		}else{
			return;
		}
		});
	}
}


function initJLForOne(){
	var a0000 = document.getElementById('a0000').value;
	if(!a0000){
		odin.alert("请先选择人员！");
		return;
	}
	ShowCellCover("start","温馨提示：","正在初始化...");
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=initJLForOne&a0000="+a0000,
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				eval(cfg.elementsScript);
				Ext.Msg.alert("温馨提示","简历拆分成功！");
				radow.doEvent("grid1.dogridquery");
				radow.doEvent("writeLead");
			}else{
				Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					Ext.Msg.alert('系统提示',cfg.mainMessage,null,380);
					return;
				}
				Ext.Msg.alert("温馨提示","初始化失败！");
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			odin.alert("网络异常！");
		}  
	});
	changeToUp();
}


function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("系统提示", msgs, function(but) {  
				
			}); 
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}

Ext.onReady(function(){
	showEditup();
});

function showEditup(){
	Ext.getCmp();
}

function a1705change(){
	var a1705=document.getElementById('a1705').value;
	var a1704=a1705.substring(0,2);
	document.getElementById('a1704').value=a1704;
	radow.doEvent('changeA1704name')
}

//拼接简历
function composeFunc1(){
	var a1700=document.getElementById('a1700').value;

	var za1701=document.getElementById('a1701').value;
	var za1703=document.getElementById('a1703').value;
	if(za1701==''||za1703==''){
		odin.info("主简历开始时间或描述为空，请正确维护简历")
		return false;
		}
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid').store;
	
	//获取三个信息项数组
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		radow.doEvent('compose');
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//获取判断简历描述字符串
		if(isQiJian(a1703)){//如果存在期间或者用户主动换行的，则不进行处理
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//用于移除空白行
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//检验多个未截止时间,校验是否连续
	var checkVar = checkVarFunc1(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "系统提示");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	radow.doEvent('compose1');
}
var ue1 = UE.getEditor('editor1');
function toA1701_1(obj){
	ue1.ready(function () {
		var a1701 = obj;
		a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
		ue1.setContent("<p>"+a1701+"</p>", false);
		//ue1.fireEvent("selectionchange");
	});
	document.getElementById("a1701z").value = a1701Format(obj);
}
//简历格式化

function a1701Format(obj){
	var a1701 = obj.replace(/期间/g,'其间');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	return newA1701;
}
//新增行
function addFunc1(){
	var a1700=document.getElementById("a1700").value;
	if(a1700==null || a1700=='' ){
		$h.alert('','请先选择简历条目！')
		return;
		
	}
	var a1701=document.getElementById("a1701").value;
	var a1703=document.getElementById("a1703").value;
	if(a1701==null || a1701=='' || a1703==null || a1703==''){
		$h.alert('','请先输入主条目的信息！')
		return;
		
	}
	
	var hanghao = Ext.getCmp('grid').getSelectionModel().lastActive;
	//alert(hanghao);
	if(!hanghao){
		hanghao=-1;
		}
	radow.addGridEmptyRow('grid',(hanghao+1));
	try {
		Ext.getCmp('grid').getSelectionModel().selectRow(hanghao+1);
		Ext.getCmp('grid').getView().focusRow(hanghao+1);
	} catch (e) {
		console.log("发生异常！");
	}
	rowIndex = rowIndex + 1;
}
function caozuo1(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='删除本行' onclick=\"delrowFunc1('"+a1700+"');\">";
}
//删除在职简历行
function delrowFunc1(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("提示","是否确认删除本条简历？",200,function(e){
		if("ok" == e){
			radow.doEvent("deleteRow1",a1700);
		}else{
			return;
		}
		});
	}
}
//检验简历完整性 + 检验简历连续性
function checkVarFunc1(a1701Arr, a1702Arr, a1703Arr){
	var za1702=document.getElementById('a1702').value;
	var za1701=document.getElementById('a1701').value;
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//检验简历完整性
	for(var j = 0, n = a1702Arr.length; j < n; j++){
		msg = checkDate(a1701Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(a1702Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkJL(a1703Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(za1702);
		if(msg!=""){
				return msg;
		}	
		if(a1701Arr[j]==""){
			//return "存在简历开始时间为空！";
		}else{
			if(a1703Arr[j]==""){
					return "存在简历内容为空！";
			}
		}
		if(za1702){
			if((a1701Arr[j]!=''&&a1701Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]<za1701)){
				return "‘其间’及‘跨时间段’简历结束时间应介于职简历开始结束时间内（或选择并维护至正确主简历行）！";
			}
		}
	}
	//检验简历连续性
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '（' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "简历信息"+a1701Arr[i]+"到"+a1702Arr[i]+"的时间不连续！";
							}
						}
					}
				}
			}
		}
	} */
	
	return msg;
}

</script>
