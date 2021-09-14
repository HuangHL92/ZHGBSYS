<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%@include file="/comOpenWinInit.jsp" %>

<style>
 .bluebutton {
        font-size: 11.5px;
        font-weight: bolder;
        color: white;
        width: 58px;
        height: 25px;
        /* line-height: 20px; */
        border-top: 0;
        border-left: 0;
        background-color: dodgerblue;
    }
    .x-form-item2 tr td .x-form-item {
        margin-bottom: 0px !important;
    }
    .yellow{
    	background: #fff68f;
    }
#perInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
#hbInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
#sqInfo input{
	border: 1px solid #c0d1e3 !important;
	
}

#zw textarea{
	width:200px;
	height:40px
}
#bz textarea{
	width:200px;
	height:40px
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<odin:hidden property="downfile" />
<odin:toolBar property="btnToolBar" >
	<odin:fill />
    <odin:buttonForToolBar text="合并名单" icon="images/add.gif" id="combineBtn" handler="combine" /> 
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:buttonForToolBar text="删除名单" icon="image/delete2.png" id="delBtn" handler="delmd" isLast="true"/>
	
</odin:toolBar>
<odin:toolBar property="btnToolBarET" >
	<odin:fill />
	<odin:buttonForToolBar text="重置人员排序" icon="image/icon040a10.gif" id="updateSort" handler="updateSort" />
	<odin:buttonForToolBar text="新增人员（正式库）" icon="image/icon021a2.gif" id="addperson" handler="addperson" />
	<odin:buttonForToolBar text="新增人员（库外）" icon="images/add.gif" id="addBtnET" handler="addper" />
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:buttonForToolBar text="删除人员" icon="image/delete2.png" id="delBtnET" handler="delper"/>
	<odin:buttonForToolBar text="导出任免表Lrmx" icon="images/icon/rybd.png" id="expexcela" handler="expLrmxGrid" />
	<odin:buttonForToolBar text="导出excel" icon="images/icon/table.gif" id="expexcel" handler="expExcelFromGrid"  isLast="true"/>
	
</odin:toolBar>

<table    style="width:100%">
<tr>
<td>
<table>
	<tr>
		<odin:textEdit property="seachName" label="名单名称" width="250" ></odin:textEdit>
		<td><odin:button text="搜索" property="searchOnePerson" handler="searchPersonByName"></odin:button></td>
	</tr>
	
</table>
</td>
<td rowspan="2">
	<odin:groupBox title="名单详细信息" >
	<table>
	<tr>
		<odin:textEdit property="MDName" label="名单名称" width="280" ></odin:textEdit>
		<odin:select2 property="MDLX" label="名单类型" width="200" codeType="GZMD02"  onchange="updateBq()"></odin:select2>
		<odin:select2 property="BQLX" label="标签类型" width="200" codeType="GZMD01"></odin:select2>
		<odin:select2 property="MDSD" label="是否锁定" width="150" data="['1','已锁定'],['0','未锁定']" ></odin:select2>
	</tr>
	<tr>
		<odin:textEdit property="MDremark" label="名单备注" width="280" ></odin:textEdit>
		<odin:textEdit property="MDCJR" label="创建人" width="200" readonly="true"></odin:textEdit>
		<odin:textEdit property="MDCJSJ" label="创建时间" width="200" readonly="true"></odin:textEdit>
		<td align="center"><odin:button text="授权" property="shouquan" handler="shouquan"></odin:button></td>
		<td><odin:button text="保存" property="baocun" handler="baocun"></odin:button></td>
	</tr>
	</table>
	</odin:groupBox>
</td>
</tr>
<tr>
	<td  valign="top" width="450" rowspan="2">
		 <table  >
		 	<tr >
		 		<td  >
		 			<odin:editgrid2 property="mdgrid" hasRightMenu="false" width="450"   topBarId="btnToolBar" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="mdid" />
								<odin:gridDataCol name="mdmc" />
								<odin:gridDataCol name="remark" />
								<odin:gridDataCol name="mnur02" />
								<odin:gridDataCol name="type" />
								<odin:gridDataCol name="createdate" />
								<odin:gridDataCol name="locked" />
								<odin:gridDataCol name="userid" />
								<odin:gridDataCol name="mdtype" />
								<odin:gridDataCol name="bqtype" />
								<odin:gridDataCol name="sq" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
								<odin:gridEditColumn2 header="名单名称" align="center" edited="false" width="395" dataIndex="mdmc" editor="text"  editorId="aaa" />
								<odin:gridEditColumn2 header="备注" align="center" edited="true" width="80" dataIndex="remark" editor="text" hidden="true" editorId="bbb"  />
								<odin:gridEditColumn2 header="名单类型" align="center" edited="true" editorId="bbb" width="60" dataIndex="mdtype" editor="select"  hidden="true" codeType="GZMD02" />
								<odin:gridEditColumn2 header="标签类型" align="center" edited="true" editorId="ccc" width="100" dataIndex="bqtype" editor="select"  hidden="true" codeType="GZMD01" />
								<odin:gridEditColumn2 header="锁定" align="center" edited="true" editorId="bbb" width="60" dataIndex="locked" editor="select"  hidden="true" selectData="['1','锁定'],['0','取消锁定']" />
								<odin:gridEditColumn2 header="操作" align="center" edited="false" width="40" dataIndex="sq" editor="text"  hidden="true"  renderer="sqRenderer"  />
								<odin:gridEditColumn2 header="创建人" align="center" edited="false" width="80" dataIndex="mnur02" editor="text"  hidden="true" />
								<odin:gridEditColumn2 header="用户id" align="center" edited="false" width="160" dataIndex="userid" editor="text"   hidden="true"/>
								<odin:gridEditColumn2 header="创建时间" align="center" edited="false" width="160" dataIndex="createdate" editor="text"  hidden="true" isLast="true"/>
							  </odin:gridColumnModel>
					 </odin:editgrid2>
		 		</td>
			</tr>
		 </table>
	</td>
</tr>
<tr>
	<td valign="top">
	 <div id="girdDiv" style="width: 100%;height:100%;">

				<table  >
				<tr>
					<td>	
					   <odin:hidden property="editgrid_id"/>
						<odin:editgrid2 property="editgrid" hasRightMenu="false"  topBarId="btnToolBarET" title="" autoFill="true" pageSize="200" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel>
							    <odin:gridDataCol name="checked" />
								<odin:gridDataCol name="mdid" />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="id" />
								<odin:gridDataCol name="sortid" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0192anow" />
								<odin:gridDataCol name="a0192a" />
								<odin:gridDataCol name="a0104" />
								<odin:gridDataCol name="a0117" />
								<odin:gridDataCol name="a0107" />
								<odin:gridDataCol name="a0111a" />
								<odin:gridDataCol name="a0134" />
								<odin:gridDataCol name="a0144" />
								<odin:gridDataCol name="a0141" />
								<odin:gridDataCol name="a0221" />
								<odin:gridDataCol name="qrzxlxw" />
								<odin:gridDataCol name="qrzxlxwxx" />
								<odin:gridDataCol name="zzxlxw" />
								<odin:gridDataCol name="zzxlxwxx" />
								<odin:gridDataCol name="a0196" />
								<odin:gridDataCol name="a0192f" />
								<odin:gridDataCol name="a0184" />
								<odin:gridDataCol name="remark" />
								<odin:gridDataCol name="username" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								<odin:gridEditColumn2 locked="true" header="selectall" width="30" menuDisabled="true" 
							editor="checkbox" dataIndex="checked" edited="true"
							hideable="false" gridName="meetingGrid"/>
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
								
								<odin:gridEditColumn2 header="名单主键" align="center" edited="false" width="20" dataIndex="mdid" editor="text" hidden="true"/>
								<odin:gridEditColumn2 header="人员主键" align="center" edited="false" width="20" dataIndex="a0000" editor="text" hidden="true"  />
								<odin:gridEditColumn2 header="名单内人员主键" align="center" edited="false" width="20" dataIndex="id" editor="text" hidden="true"  />
								
								<odin:gridEditColumn2 header="序号" align="center" edited="false" width="40" dataIndex="sortid"  editor="text" hidden="true"  />
								<odin:gridEditColumn2 header="姓名" align="center" edited="false" width="80" dataIndex="a0101" editor="text" renderer="nameRenderer"  />
								<odin:gridEditColumn2 header="时任职务" align="center" edited="false" width="200" dataIndex="a0192a" editor="text" />
								<odin:gridEditColumn2 header="现任职务" align="center" edited="false" width="200" dataIndex="a0192anow" editor="text" />
								<odin:gridEditColumn2 header="性别" align="center" edited="false" width="40" dataIndex="a0104" editor="select"  codeType='GB2261' />
								<odin:gridEditColumn2 header="民族" align="center" edited="false" width="40" dataIndex="a0117" editor="select" codeType='GB3304' />
								<odin:gridEditColumn2 header="党派" align="center" edited="false" width="80" dataIndex="a0141" editor="select" codeType='GB4762' />
								<odin:gridEditColumn2 header="职务层次" align="center" edited="false" width="120" dataIndex="a0221" editor="select" codeType='ZB09' />
								<odin:gridEditColumn2 header="出生年月" align="center" edited="false" width="80" dataIndex="a0107" renderer="dateRenderer" editor="text"  />	
								<odin:gridEditColumn2 header="籍贯" align="center" edited="false" width="100" dataIndex="a0111a" editor="text" />
								<odin:gridEditColumn2 header="参加工作时间" align="center" edited="false" width="100" dataIndex="a0134" renderer="dateRenderer" editor="text"  />
								<odin:gridEditColumn2 header="入党时间" align="center" edited="false" width="80" dataIndex="a0144" renderer="dateRenderer" editor="text"  />		
								<odin:gridEditColumn2 header="全日制学历学位" align="center" edited="false" width="150" dataIndex="qrzxlxw" editor="text" />
								<odin:gridEditColumn2 header="全日制学校" align="center" edited="false" width="150" dataIndex="qrzxlxwxx" editor="text" />
								<odin:gridEditColumn2 header="在职学历学位" align="center" edited="false" width="150" dataIndex="zzxlxw" editor="text" />
								<odin:gridEditColumn2 header="在职学校" align="center" edited="false" width="150" dataIndex="zzxlxwxx" editor="text" />
								<odin:gridEditColumn2 header="职称" align="center" edited="false" width="150" dataIndex="a0196" editor="text" />
								<odin:gridEditColumn2 header="任现职时间" align="center" edited="false" width="100" dataIndex="a0192f" renderer="dateRenderer" editor="text"   />
								<odin:gridEditColumn2 header="身份证号" align="center" edited="false" width="150" dataIndex="a0184" editor="text" />
								<odin:gridEditColumn2 header="备注" align="center" edited="true" width="200" editorId="asd" dataIndex="remark" editor="text"  />
							 	<odin:gridEditColumn2 header="操作者" align="center" edited="false" width="120"  dataIndex="username" editor="text" isLast="true" />
							  </odin:gridColumnModel>
					 </odin:editgrid2>
						</td>
				   </tr>
				</table>
			</div>
		</td>
	</tr>
</table>

<div id="perInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		  <tr>
			<odin:textEdit property="a0101"  colspan="4" label="姓名" width="300" required="true"></odin:textEdit>
		  </tr>
		  <tr id="zw">
			<odin:textarea property="a0192a" colspan="4"  label="时任职务"></odin:textarea>
		  </tr>
		  <tr>
			<odin:select2 property="a0104" codeType="GB2261"  width="130" label="性别" />
			<odin:select2 property="a0117" codeType="GB3304"  width="130" label="民族" />
		  </tr>
		  <tr>
			<odin:textEdit property="a0107"  label="出生年月"  width="130" maxlength="8" ></odin:textEdit>
			<odin:textEdit property="a0111a"   label="籍贯"  width="130"></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="a0134" colspan="4" width="300" label="参加工作时间" maxlength="8" ></odin:textEdit>
		  </tr>
		  <tr id='t1'>
			<odin:select2 property="a0141"  colspan="4"  width="300" codeType="GB4762"  label="党派"  />
		  </tr>
		  <tr>
			<odin:textEdit property="a0144"  colspan="4"  width="300" label="入党时间" maxlength="8" ></odin:textEdit>
		  </tr>
		  <tr id='t2'>
			<odin:select2 property="a0221" colspan="4"  width="300" codeType="ZB09"  label="职务层次" />
		  </tr>
		  <tr>
			<odin:textEdit property="qrzxlxw"  colspan="4"  width="300"  label="全日制学历学位"></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="qrzxlxwxx"  colspan="4"  width="300" label="全日制学历学位学校"></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="zzxlxw"  colspan="4"  width="300" label="在职学历学位"></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="zzxlxwxx"   colspan="4"  width="300" label="在职学历学位学校"></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="a0196"  colspan="4"  width="300" label="职称"  ></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="a0192f"  colspan="4" width="300" label="任现职时间" maxlength="8"  ></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="a0184" colspan="4"  width="300" label="身份证号"  ></odin:textEdit>
		  </tr>
		  <tr>
			<odin:textEdit property="sortid" colspan="4"  width="300" label="序号"  ></odin:textEdit>
		  </tr>
		  <tr id="bz">
			<odin:textarea property="remark" colspan="4"  label="备注"></odin:textarea>
		  </tr>
		</table>
		<odin:hidden property="a0000"/>
		<odin:hidden property="sortid"/>
		<odin:hidden property="id"/>
		<div style="margin-left: 200px;margin-top: 15px;">
			<odin:button text="确定" property="savePerInfo" handler="savePerInfo" />
		</div>
		<input id="ets01_combo" name="ets01_combo"  type="hidden"/>
		<input id="ets01" name="ets01" type="hidden"/>
	</div>
</div>


<div id="sqInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		<tr id="chooseSQ">
			<tags:ComBoxWithTree property="mnur01" label="选择用户" width="280" readonly="true" ischecked="true" codetype="USER" listHeight="300" />
		</tr>
		</table>
		<div style="margin-left: 200px;margin-top: 15px;">
			<odin:button text="确定" property="saveSQInfo" handler="saveSQInfo" />
		</div>
	</div>
</div>

<div id="hbInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		<tr id="chooseHB">
			<odin:select2 property="mdid_HB" label="选择合并名单：" width="220"  multiSelect="true" required="true"></odin:select2>
		</tr>
		<tr>
			<odin:textEdit property="mdmc_HB"  label="合并名单名称" width="220" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="mdtype_HB" label="名单类型" width="220" codeType="GZMD02"  onchange="updateBqq()"></odin:select2>
		</tr>
		<tr>
			<odin:select2 property="bqtype_HB" label="标签类型" width="220" codeType="GZMD01"  ></odin:select2>
		</tr>
		</table>
		<div style="margin-left: 150px;margin-top: 15px;">
			<odin:button text="确定" property="saveHBInfo" handler="saveHBInfo" />
		</div>
	</div>
</div>

<odin:hidden property="mdid"/>
<odin:hidden property="mdj"/>
<odin:hidden property="delid"/>
<odin:hidden property="locked"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="ss"/>
<odin:hidden property="username"/>

<script type="text/javascript">



Ext.onReady(function(){
	$h.initGridSort('editgrid',function(g){
		radow.doEvent('rolesort');
	});	
	
	var pgrid = Ext.getCmp('editgrid');
/* 	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[
		 new Ext.menu.Separator({cls:'xtb-sep'}),
		 new Ext.Button({
				icon : 'images/icon/table.gif',
				id:'getAll',
			    text:'导出Excel',
			    handler:expExcelFromGrid
			}),
			]);
	  */
	 
		
	 openPerWin();
	 openHBWin();
	 openSQWin();
	 hideWin();
	 

		
	 var viewSize = Ext.getBody().getViewSize();
	 var editgrid = Ext.getCmp('editgrid');
	 var mdgrid = Ext.getCmp('mdgrid');
	 mdgrid.setHeight(viewSize.height-51 );
	 editgrid.setHeight(viewSize.height-125 );
	 mdgrid.setWidth(435);
     editgrid.setWidth(viewSize.width - 470);
     
     Ext.getCmp('editgrid').on('rowdblclick',function(gridobj,index,e){
    	var locked=document.getElementById('locked').value;
    	if(locked=='1'){
    		$h.alert('系统提示：', '该名单已锁定！', null, 180);
            return;
    	}
 		var rc = gridobj.getStore().getAt(index);
 		odin.setSelectValue('a0000',rc.data.a0000);
 		odin.setSelectValue('a0101',rc.data.a0101);
 		odin.setSelectValue('a0192a',rc.data.a0192a);
 		odin.setSelectValue('a0104',rc.data.a0104);
 		odin.setSelectValue('a0117',rc.data.a0117);
 		odin.setSelectValue('a0107',rc.data.a0107);
 		odin.setSelectValue('a0111a',rc.data.a0111a);
 		odin.setSelectValue('a0134',rc.data.a0134);
 		odin.setSelectValue('a0144',rc.data.a0144);
 		odin.setSelectValue('a0141',rc.data.a0141);
 		odin.setSelectValue('a0221',rc.data.a0221);
 		odin.setSelectValue('qrzxlxw',rc.data.qrzxlxw);
 		odin.setSelectValue('qrzxlxwxx',rc.data.qrzxlxwxx);
 		odin.setSelectValue('zzxlxw',rc.data.zzxlxw);
 		odin.setSelectValue('zzxlxwxx',rc.data.zzxlxwxx);
 		odin.setSelectValue('a0196',rc.data.a0196);
 		odin.setSelectValue('a0192f',rc.data.a0192f);
 		odin.setSelectValue('remark',rc.data.remark);
 		odin.setSelectValue('sortid',rc.data.sortid);
 		odin.setSelectValue('id',rc.data.id);
 		odin.setSelectValue('a0184',rc.data.a0184);
 		var a0000=rc.data.a0000;
 		openPerWin(a0000);
 	});
     
     Ext.getCmp('mdgrid').on('beforeedit',function(p){
    	if(p.field=='bqtype'){
			if(p.record.data.mdtype=='03'){
				return true;
			}else{
	     		return false;
			} 
    	}else{
    		return true;
    	}
	});
     
     
    
     
});


function initX(){
	if(parentParams&&parentParams.mdid){
		$('#mdid').val(parentParams.mdid);
		$('#mdj').val(parentParams.mdj);
		radow.doEvent("mdgrid.rowclick");
		
	}
}

function dateRenderer(val) {
	if(val!=null && val!=''){
		var length=val.length;
	    if (val.length === 6 || val.length === 8) {
	        return val.substr(0, 4) + "." + val.substr(4, 2);
	    } else {
	        return val;
	    }
	}else{
		return val;
	}
	
	
}
function expExcelFromGrid(){
    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('editgrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
/*     odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false); */

/* 
        excelName = "人员信息" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd");  */
    excelName ="历史人员名单";

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}


function sqRenderer(value, params, record, rowIndex, colIndex, ds) {
	if(record.get("type")==2){
		return null;
	}
	var mdid=record.get("mdid");
	if(record.get("type")==1){
		return "<a href=\"javascript:sqRow2(&quot;"+mdid+"&quot;)\">授权</a>";
	}
}

function sqRow2(mdid){ 
	radow.doEvent('sqRow',mdid);	
	
}

function nameRenderer(value, params, record, rowIndex, colIndex, ds) {
	var a0000=record.get("a0000");
	var a0101=record.get("a0101");
	if(a0000==null || a0000==''){
		return a0101;
	}else{
		return "<a style=\"color:black;text-decoration:none\" href=\"javascript:nameRow2(&quot;"+a0000+"&quot;)\">"+a0101+"</a>";
	}
}

function nameRow2(a0000){ 
	radow.doEvent('openrmb',a0000);	
}
function tt(){ 
	var shouquan =document.getElementById("shouquan");
	var baocun =document.getElementById("baocun");

	shouquan.style.display='none';
	baocun.style.display='none';
}
function pp(){ 
	var shouquan =document.getElementById("shouquan");
	var baocun =document.getElementById("baocun");

	shouquan.style.display='';
	baocun.style.display='';
}
function delmd(){
	var locked=document.getElementById('locked').value;
	if(locked=='1'){
		$h.alert('系统提示：', '该名单已锁定！', null, 180);
        return;
	}
	var mdid=document.getElementById('mdid').value;
	if(mdid==null || mdid==''){
		$h.alert('','请选择名单！')
		return;
	}
	var g1=document.getElementById('username').value;
	var g2=document.getElementById('ss').value;
	if(g1!=g2){
		$h.alert('系统提示：', '该名单只能由创建人进行修改！', null, 180);
        return;
	}
	$h.confirm("系统提示：","是否确认删除？",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delmd");
		}else{
			return false;
		}		
	});
}
function delper(){
	var delid=document.getElementById('delid').value;
	if(delid==null || delid==''){
		$h.alert('','请选择人员！')
		return;
	}
	var locked=document.getElementById('locked').value;
	if(locked=='1'){
		$h.alert('系统提示：', '该名单已锁定！', null, 180);
        return;
	}
	var g1=document.getElementById('username').value;
	var g2=document.getElementById('ss').value;
	if(g1!=g2){
		$h.alert('系统提示：', '该人员只能由创建人进行删除！', null, 180);
        return;
	}
	$h.confirm("系统提示：","是否确认删除？",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delper");
		}else{
			return false;
		}		
	});
	
}

function addper(){
	var mdid=document.getElementById('mdid').value;
	if(mdid=='' || mdid==null){
		$h.alert('','请选择名单！')
		return;
	}
	var locked=document.getElementById('locked').value;
	if(locked=='1'){
		$h.alert('系统提示：', '该名单已锁定！', null, 180);
        return;
	}
	odin.setSelectValue('a0000','');
	odin.setSelectValue('a0101','');
	odin.setSelectValue('a0192a','');
	odin.setSelectValue('a0104','');
	odin.setSelectValue('a0117','');
	odin.setSelectValue('a0107','');
	odin.setSelectValue('a0111a','');
	odin.setSelectValue('a0134','');
	odin.setSelectValue('a0144','');
	odin.setSelectValue('a0141','');
	odin.setSelectValue('a0221','');
	odin.setSelectValue('qrzxlxw','');
	odin.setSelectValue('qrzxlxwxx','');
	odin.setSelectValue('zzxlxw','');
	odin.setSelectValue('zzxlxwxx','');
	odin.setSelectValue('a0196','');
	odin.setSelectValue('a0192f','');
	odin.setSelectValue('remark','');
	odin.setSelectValue('sortid','');
	odin.setSelectValue('id','');
	odin.setSelectValue('a0184','');
	var a0000=document.getElementById('a0000').value;;
	openPerWin(a0000);
	
}

function openSQWin(){
	var win = Ext.getCmp("addsq");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '授权维护',
		layout : 'fit',
		width : 380,
		height : 171,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addsq',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"sqInfo",
		listeners:{}
		           
	});
	win.show();
}

function openPerWin(a0000){
	if(a0000==null||a0000==''){
		document.getElementById("t1").style.display="none";
		document.getElementById("t2").style.display="none";
		var win = Ext.getCmp("addper");	
		if(win){
			win.show();	
			return;
		}
	}else{
		document.getElementById("t1").style.display="";
		document.getElementById("t2").style.display="";
		var win = Ext.getCmp("addper");	
		if(win){
		win.show();	
		return;
		}
	}
	win = new Ext.Window({
		title : '名单人员维护',
		layout : 'fit',
		width : 480,
		height : 621,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addper',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"perInfo",
		listeners:{}
		           
	});
	win.show();
}

function openHBWin(){
	var win = Ext.getCmp("addhb");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '名单合并',
		layout : 'fit',
		width : 380,
		height : 271,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addhb',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"hbInfo",
		listeners:{}
		           
	});
	win.show();
}

function hideWin(){
	var win = Ext.getCmp("addper");	
	if(win){
		win.hide();	
	}
	var win = Ext.getCmp("addsq");	
	if(win){
		win.hide();	
	}
	var win = Ext.getCmp("addhb");	
	if(win){
		win.hide();	
	}
}

function saveHBInfo(){
	radow.doEvent("saveHBInfo");
	Ext.getCmp("addhb").hide();
}

function saveSQInfo(){
	radow.doEvent("saveSQInfo");
	Ext.getCmp("addsq").hide();
}

function savePerInfo(){
	radow.doEvent("savePerInfo");
	Ext.getCmp("addper").hide();
}

function combine(){
	radow.doEvent('combine');
}

function updateBq(){
	var mdtype_HB=document.getElementById("MDLX").value;
	if(mdtype_HB=='03'){
		radow.doEvent('setDisable','3');
	}else{
		radow.doEvent('setDisable','4');
		odin.setSelectValue("BQLX", '');
	}
	
}
function updateBqq(){
	var mdtype_HB=document.getElementById("mdtype_HB").value;
	if(mdtype_HB=='03'){
		radow.doEvent('setDisablee','3');
	}else{
		radow.doEvent('setDisablee','4');
	}
	odin.setSelectValue("bqtype_HB", '');
	
}
function searchPersonByName(){
	radow.doEvent('mdgrid.dogridquery');
}

function updatePersonGrid(){
	radow.doEvent('editgrid.dogridquery');
}

function expLrmxGrid(){
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('exportLrmxBtn');
}
function downloadword(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}

function shouquan(){
	var mdid = document.getElementById('mdid').value;
	if(mdid==''){
		alert("请先选择名单再授权");
	}else{
		radow.doEvent('sqRow',mdid);	
	}
}

function baocun(){
	radow.doEvent('save');
}

var g_contextpath = '<%= request.getContextPath() %>';
function addperson(){
	var mdid=document.getElementById('mdid').value;
	if(mdid=='' || mdid==null){
		$h.alert('','请选择名单！')
		return;
	}
	var locked=document.getElementById('locked').value;
	if(locked=='1'){
		$h.alert('系统提示：', '该名单已锁定！', null, 180);
        return;
	}
	$h.openPageModeWin('addperson','pages.zwzc.LsmdByName','新增人员',1020,520,{mdid:mdid},g_contextpath);
}

function updateSort(){
	var mdid=document.getElementById('mdid').value;
	if(mdid=='' || mdid==null){
		$h.alert('','请选择名单！')
		return;
	}
	var locked=document.getElementById('locked').value;
	if(locked=='1'){
		$h.alert('系统提示：', '该名单已锁定！', null, 180);
        return;
	}
	radow.doEvent('updateSort',mdid);
}

</script>