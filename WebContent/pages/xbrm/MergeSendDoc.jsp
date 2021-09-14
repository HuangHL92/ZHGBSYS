<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>
<%
String ctxPath=request.getContextPath();
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<style>
	
</style>
<script type="text/javascript">
function setWh(){
	var store=Ext.getCmp('gridwh').getStore();
	var rbId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var colcheck=record.data.colcheck;
		var rbd000=record.data.rbd000;
		var rbd006=record.data.rbd006;
		if(true==colcheck){
			rbId = rbd000+'@@'+rbd006;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有勾选文号列表里的文号!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一个合并批次!');
		return;
	}
	radow.doEvent('setWh',rbId);
}
</script>

<odin:toolBar property="topbar" applyTo="topbar_div">
<odin:fill></odin:fill>
<odin:separator></odin:separator>
<odin:buttonForToolBar text="设置文号" tooltip="勾选文号进行设置" handler="setWh"></odin:buttonForToolBar>
<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<odin:hidden property="rbId" title="批次id"/>


<div style="width: 100%; margin-top: 0px; ">
<table style="width: 100%;border: solid 0px !important; top: 0px;" cellspacing="0" cellpadding="0"><tr><td valign="top">
	<div style="width: 300px;float: left;" id="gridDiv">
		<odin:editgrid2 property="gridcq" grouping="true" groupCol="rb_name" 
			bbarId="bbarid" isFirstLoadData="false" title="人员列表" width="300"  pageSize="9999" 
			clicksToEdit="false" 
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"人\" : \"人\"]})&nbsp;&nbsp;&nbsp;&nbsp;"
				autoFill="false" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="js0102" />
				<odin:gridDataCol name="js0108" />
				<odin:gridDataCol name="rb_id" />
				<odin:gridDataCol name="rb_name" />
				<odin:gridDataCol name="havefine" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 dataIndex="js0102" header="姓名" width="65"  sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="rb_id" menuDisabled="true" header="批次id" hidden="true" sortable="false" width="100" edited="false"   editor="text"  align="center" />
				<odin:gridEditColumn2 dataIndex="js0108" menuDisabled="true" header="现任职务" sortable="false" width="190" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="rb_name" menuDisabled="true" header="批次名称" hidden="true" sortable="false" width="100" edited="false"   editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="js01001" header="id" hidden="true" width="45" align="center" isLast="true" editor="text" edited="false" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{data:[]}
			</odin:gridJsonData>
		</odin:editgrid2>
	</div>
</td>
<td width="1px">
<div style="width: 1px;"></div>
</td>
<td style="width: 100%;">
  	<div id="peopleInfo" >
  		<table id="wh_talbe" style="width: 100%;border: solid 0px !important;" cellspacing="0" cellpadding="0">
  			<tr>
  				<td width="100%" valign="top">
  					<odin:editgrid2 property="gridwh" title="文号列表" isFirstLoadData="false" width="350" height="150"
						clicksToEdit="false" autoFill="true" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="colcheck" />
							<odin:gridDataCol name="rbd001" />
							<odin:gridDataCol name="rbd003" />
							<odin:gridDataCol name="rbd004" />
							<odin:gridDataCol name="rbd005" />
							<odin:gridDataCol name="rbd006" />
							<odin:gridDataCol name="rbd000" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 dataIndex="rbd003" 	header="类型" width="65" sortable="false" menuDisabled="true" edited="false" hidden="true" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="rbd004" 	header="年度" width="65" sortable="false" menuDisabled="true" edited="false" hidden="true" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="rbd005" 	header="序号" width="65" sortable="false" menuDisabled="true" edited="false" hidden="true" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="colcheck"	header="" width="15" edited="true" editor="checkbox" sortable="false" checkBoxClick="checkClicktable"></odin:gridEditColumn2>
							<odin:gridEditColumn2 dataIndex="rbd001" 	header="文号" width="45" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="rbd006" 	header="发文时间" width="25" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="rbd000"	header="删除"	 width="25" align="center" renderer="delWhRenderer" sortable="false" isLast="true" editor="text" edited="false" />
						</odin:gridColumnModel>
						<odin:gridJsonData>
							{data:[]}
						</odin:gridJsonData>
					</odin:editgrid2>
  				</td>
  				<td width="230px">
  						<odin:hidden property="rbd000"/>
  						<odin:hidden property="rbd001"/>
	  					<table width="230px">
	  						<%-- <tr>
	  							<odin:textarea property="rbd001" cols="30" label="文号" rows="3"></odin:textarea>
	  						</tr> --%>
	  						<tr>
	  							<odin:select2 property="rbd003" label="类型" data="['1','黔委组'],['2','黔组干']"  canOutSelectList="false" ></odin:select2>
	  						</tr>
	  						<tr>
	  							<odin:numberEdit property="rbd004" label="年度" maxlength="4"></odin:numberEdit>
	  						</tr>
	  						<tr>
	  							<odin:textEdit property="rbd005" label="序号" colspan="4"  maxlength="10"></odin:textEdit>
	  						</tr>
	  						<tr>
	  							<odin:NewDateEditTag property="rbd006" label="发文时间" maxlength="8" />
	  						</tr>
	  						<tr>
	  							<td colspan="4">
	  								<table style="width: 100%;">
	  								<tr>
	  								<td align="center" colspan="1"><odin:button text="新增" handler="addWhNew" property="addWh"></odin:button></td>
	  								<td align="center" colspan="1"><odin:button text="保存" property="saveWh"></odin:button></td>
	  								</tr>
	  								</table>
	  							</td>
	  						</tr>
	  						
	  					</table>
  				</td>
  			</tr>
  			
  		</table>
  		
  		<table style="width: 100%;border: solid 0px !important;" cellspacing="0" cellpadding="0">
  			<tr>
  				<td style="width: 100%;"><div style="width: 100%" id="topbar_div"></div></td>
  			</tr>
  			<tr>
  				<td style="width: 100%;">
  					<odin:editgrid property="NiRenGrid" sm="row" isFirstLoadData="false" url="/"
                                   height="200" title="人员拟任职务" pageSize="50" >
                        <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                            <odin:gridDataCol name="checkid"/>
                            <odin:gridDataCol name="a0000"/>
                            <odin:gridDataCol name="js0100"/>
                            <odin:gridDataCol name="js0102"/>
                            <odin:gridDataCol name="rbd001"/>
                            <odin:gridDataCol name="js2200"/>
                            <odin:gridDataCol name="js2201"/>
                            <odin:gridDataCol name="js2202"/>
                            <odin:gridDataCol name="js2203"/>
                            <odin:gridDataCol name="js2204"/>
                            <odin:gridDataCol name="js2205"/>
                            <odin:gridDataCol name="rb_id"/>
                            <odin:gridDataCol name="rbd000"/>
                            <odin:gridDataCol name="js2300"/>
                            <odin:gridDataCol name="delete" isLast="true"/>
                        </odin:gridJsonDataModel>
                        <odin:gridColumnModel>
                            <odin:gridRowNumColumn/>
                            <odin:gridEditColumn2 header="选中" width="100" editor="checkbox" checkBoxClick="checkClickCode" dataIndex="checkid"
                                                  edited="true" align="center"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2200" editor="text"
                                                  width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="js0100" editor="text"
                                                      width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0000" editor="text"
                                                      width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="rbd000" editor="text"
                                                      width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="rb_id" editor="text"
                                                      width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2300" editor="text"
                                                      width="200" hidden="true"/>
                            <odin:gridEditColumn2 header="姓名" edited="false" dataIndex="js0102" editor="text"
                                                  width="200" hidden="false"/>
                            <odin:gridEditColumn2 header="拟任单位" edited="false" dataIndex="js2201"
                                                  editor="text" width="300"/>
                            <odin:gridEditColumn2 header="拟任单位ID" edited="false" dataIndex="js2202"
                            					  editor="text" width="300" hidden="true"/>
                            <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="js2203" editor="text"
                                                  width="300"/>
                            <odin:gridEditColumn2 header="文号" edited="false" dataIndex="rbd001" editor="text"
                                width="300"/>
                            <odin:gridEditColumn2 header="拟任职务ID" edited="false" dataIndex="js2204" editor="text"
                                                  width="300" hidden="true" />
                            <odin:gridEditColumn2 header="标记" edited="false" dataIndex="js2205" editor="text"
                                                  width="300" hidden="true" />
                            <odin:gridEditColumn  header="操作" width="300" dataIndex="delete"
                                                 editor="text"
                                                 edited="false" hidden="true" isLast="true"/>
                        </odin:gridColumnModel>
                    </odin:editgrid>
  				</td>
  			</tr>
  			<tr>
  				<td style="width: 100%;">
  					<odin:editgrid property="WorkUnitsGrid" sm="row" isFirstLoadData="false" url="/"
                                       height="330" title="人员拟免职务" pageSize="50" >
                            <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                                <odin:gridDataCol name="demo"/>
                                <odin:gridDataCol name="a0200"/>
                                <odin:gridDataCol name="js0100"/>
                                <odin:gridDataCol name="a0000"/>
                                <odin:gridDataCol name="js0102"/>
                            	<odin:gridDataCol name="rbd001"/>
                                <odin:gridDataCol name="a0201b"/>
                                <odin:gridDataCol name="a0201a"/>
                                <odin:gridDataCol name="a0215a"/>
                                <odin:gridDataCol name="a0222"/>
                                <odin:gridDataCol name="a0255"/>
                                <odin:gridDataCol name="rb_id"/>
                                <odin:gridDataCol name="rbd000"/>
                                <odin:gridDataCol name="js2300"/>
                                <odin:gridDataCol name="js2400"/>
                                <odin:gridDataCol name="delete" isLast="true"/>

                            </odin:gridJsonDataModel>
                            <odin:gridColumnModel>
                                <odin:gridRowNumColumn/>
                                <odin:gridEditColumn2 header="选中" width="100" editor="checkbox" dataIndex="demo"
                                                      edited="true" align="center"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="js0100" editor="text"
                                                      width="200" hidden="true"/>
                            	<odin:gridEditColumn2 header="id" edited="false" dataIndex="a0000" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2400" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="rb_id" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="id" edited="false" dataIndex="js2300" editor="text"
                                                      width="200" hidden="true"/>
                                <odin:gridEditColumn2 header="姓名" edited="false" dataIndex="js0102" editor="text"
                                                  width="200" hidden="false"/>
                                <odin:gridEditColumn2 header="任职机构代码" edited="false" dataIndex="a0201b"
                                                      editor="text" width="300" hidden="true"/>
                                <odin:gridEditColumn2 header="任职机构" edited="false" dataIndex="a0201a"
                                                      renderer="changea0201a" editor="text" width="300"/>
                                <odin:gridEditColumn2 header="职务名称" edited="false" dataIndex="a0215a" editor="text"
                                                      width="300"/>
                                <odin:gridEditColumn2 header="岗位类别" edited="false" dataIndex="a0222" editor="text"
                                                      hidden="true"/>
                                <odin:gridEditColumn2 header="文号" edited="false" dataIndex="rbd001" editor="text"
                                	width="300"/>
                                <odin:gridEditColumn2 header="任职状态" edited="false" dataIndex="a0255" codeType="ZB14"
                                                      editor="select" hidden="true" width="160" isLast="true" />
                                <%-- <odin:gridEditColumn hidden="true" header="操作" width="100" dataIndex="delete"
                                                     editor="text"
                                                     edited="false" renderer="deleteRowRenderer" isLast="true"/> --%>

                            </odin:gridColumnModel>
                        </odin:editgrid>
  				</td>
  			</tr>
  		</table>
	</div>  
</td></tr></table>
</div>
  



<odin:hidden property="whcheckid" value="-1" />
<odin:hidden property="js0100s" title="人员选择集合"/>
<!-- 拟任免时时保存 -->
<odin:hidden property="nrmid" title="字段"/>
<odin:hidden property="nrmdesc" title="字段中文"/>
<odin:hidden property="nrmvalue" title="值"/>
<%-- <odin:hidden property="js0100s" title="人员选择集合"/> --%>
<script type="text/javascript">
var ctxPath='<%=ctxPath%>';

function checkClicktable(row,col,dataIndex,gridid){
	var grid=Ext.getCmp(gridid);
	var store = grid.getStore();
	var rowCount = store .getCount();
	var id = "";
	var count = 0;
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		//alert(record.data.colcheck);
		/* if(record.data.colcheck){
			var id = record.data.rbd000;
			count++;
		} */
		if(row==i){
			var id = record.data.rbd000;
			count++;
		} else {
			record.set('colcheck',false);
		}
	}
	
	if(count==1){
		document.getElementById('whcheckid').value = id;
		radow.doEvent('NiRenGrid.dogridquery');
		radow.doEvent('WorkUnitsGrid.dogridquery');
	} else {
		document.getElementById('whcheckid').value = "-1";
		radow.doEvent('NiRenGrid.dogridquery');
		radow.doEvent('WorkUnitsGrid.dogridquery');
	}
}

function changea0201a(value, params, record, rowIndex, colIndex, ds) {
    if (record.data.a0201b == '-1') {
        return '<a title="' + value + '(机构外)">' + value + '(机构外)</a>';
    } else {
        return '<a title="' + value + '">' + value + '</a>';
    }
}

function addWhNew(){
	document.getElementById('rbd000').value = "";
	document.getElementById('rbd001').value = "";
	document.getElementById('rbd003').value = "1";
	document.getElementById('rbd003_combo').value = "黔委组";
	document.getElementById('rbd004').value = new Date().getFullYear();
	document.getElementById('rbd005').value = "";
}

function delWhRenderer(value, params, record, rowIndex, colIndex, ds) {
	var id = record.get('rbd000');
	var link = "<a href=\"javascript:void()\" onclick=\"deleteWH('"+id+"')\">删除</a>";
	return link;
}

function deleteWH(id){
	Ext.Msg.confirm("系统系统","你是否要删除此文号？",function(btn){
		if(btn=='yes'){
			radow.doEvent('delwh',id);
		} else {
			return;
		}
	})
}

Ext.onReady(function(){
	var viewSize = Ext.getBody().getViewSize();
	var peopleInfoGrid =Ext.getCmp('gridcq');
	peopleInfoGrid.setHeight(viewSize.height);
	var gridobj = document.getElementById('forView_gridcq');
	var grid_pos = $h.pos(gridobj);
	$( "#peopleInfo" ).css('width',viewSize.width-grid_pos.left-peopleInfoGrid.getWidth()-20);
	$( "#peopleInfo" ).css('height',peopleInfoGrid.getHeight());
	
	
	var wh_talbe_h = document.getElementById('wh_talbe').offsetHeight;
	var NiRenGrid =Ext.getCmp('NiRenGrid');
	NiRenGrid.setHeight((viewSize.height-wh_talbe_h)/2);
	var WorkUnitsGrid =Ext.getCmp('WorkUnitsGrid');
	WorkUnitsGrid.setHeight((viewSize.height-wh_talbe_h)/2);
	
	var gridwh =Ext.getCmp('gridwh');
	gridwh.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rbd000').value = rc.data.rbd000;
		document.getElementById('rbd001').value = rc.data.rbd001;
		document.getElementById('rbd003').value = rc.data.rbd003;
		document.getElementById('rbd003_combo').value = rc.data.rbd003=='1'?'黔委组':'黔组干';
		document.getElementById('rbd004').value = rc.data.rbd004;
		document.getElementById('rbd005').value = rc.data.rbd005;
	});
	document.getElementById('rbd004').value = new Date().getFullYear();
});


</script>
