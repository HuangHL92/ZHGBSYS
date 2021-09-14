<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/SelectTPRY.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 406px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>

<odin:hidden property="ynId" />
<odin:hidden property="yntype" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!--  是否搜索刷新grid的标记 -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />


<odin:hidden property="sql2" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />
<odin:hidden property="jw" title="是否征求纪委意见进来的"/>
<div id="groupTreeContent" style="height: 94%;width: 100%;margin-left: 12px;">
	<table style="height: 100%;">
		<tr valign="top">
			<td width="20%" height="100%" rowspan="0" colspan="0">
				<div style="height: 5%;background-color: #cedff5;" align="center">
					<input type="checkbox"  id="continueCheckbox" style="display:none" onclick="continueChoose()"><!-- <font style="font-size: 13">连续选择</font> -->
					<input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font style="font-size: 13">包含下级</font>
				</div>
				<div class="busy">
					<div id="tree-div" style="border: 2px solid #c3daf9;height: 99%; width: 98%;"></div>
				</div>
			</td>  
			<td id="gridcqtd" style="width: 67%;height: 100%;">
				<table style="width: 100%;height: 100%;border: 2px solid #c3daf9;">
					<tr>
						<td colspan=3>    
							<input type="radio" checked name="rdoSwitch" id="selectByPersonIdBtn" onclick="selectByPersonId()"><font style="font-size: 13">单位人员选择</font>
							<font style="font-size: 13">
								&nbsp;中间库批次人员选择:<select id="selectByInputYnId" onchange="selectByInputYnIdClick()">
									<option>&nbsp;&nbsp;</option>		
								</select>
								
								<!-- odin:select2  property="tplb"  label="从其它会议中提取（选择后点击确定即可）"  odin:select2  -->
								
							</font>&nbsp;&nbsp;
							<input type="radio"         name="rdoSwitch" id="selectByYnIdBtn" onclick="selectByYnId()"><font style="font-size: 13">处室批次汇总</font>	
						</td>
					</tr>					
					<tr   style="width: 100%;">
						<td colspan="3">
						<table style="height:50px;width:100%">
							<tr>
								<td style="width: 130px;" >
									<div style="font-size: 12px;margin-top: 10px;margin-left: 10px;color: red;">多个姓名/身份证查</br>询，请按逗号隔开。</div>
								</td>
								<td>
									<odin:select property="tpye" label="" canOutSelectList="true" value = '1' data="['1','姓名'],['2','身份证号码']" width="120"></odin:select>
								</td>
								<td>
									<textarea  class="x-form-text x-form-field" style="width: 390px;height:25px;  margin-top:1px;" name="queryName" id="queryName"  ></textarea> 
									
								</td>
								<td> <input id="buttonSearch" type="button" style="position: relative;top: 2px;" onclick="toDOQuery()" value="搜索"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr style="width: 100%;" align="center">
						<td width="46%" height="100%">
							
							<div id="selectByPersonIdDiv" >
								<odin:editgrid property="gridcq" title="待选列表"  width="300" height="350" pageSize="9999"
									autoFill="false" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck" />	
										<odin:gridDataCol name="a0000" />
										<odin:gridDataCol name="a0101" />
										<odin:gridDataCol name="a0104" />
										<odin:gridDataCol name="a0163" />
										<odin:gridDataCol name="a0192a" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 header="selectall" width="0" hidden="true"
											editor="checkbox" dataIndex="personcheck" edited="true"
											hideable="false" gridName="persongrid" />
										<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
										<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
										<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="100"  align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false"  codeType="ZB126" />
										<odin:gridColumn header="干部批次" renderer="renderDel" align="center"  dataIndex="del" width="80" sortable="false" isLast="true" />
									</odin:gridColumnModel>
									<odin:gridJsonData>
										{
									        data:[]
									    }
									</odin:gridJsonData>
								</odin:editgrid>
							</div>
							<div id="selectByYnIdDiv" style="display:none"> 
								<odin:editgrid property="gridcq2" title="待选列表"  width="300" height="330" pageSize="9999"
									autoFill="false" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck" />	
										<odin:gridDataCol name="a0000" />
										<odin:gridDataCol name="a0101" />
										<odin:gridDataCol name="a0104" />
										<odin:gridDataCol name="a0163" />
										<odin:gridDataCol name="a0192a" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 header="selectall" width="0" hidden="true"
											editor="checkbox" dataIndex="personcheck" edited="true"
											hideable="false" gridName="persongrid" />
										<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="批次名称" width="110" align="center" />
										<odin:gridColumn dataIndex="a0104" header="填报处室" width="145" align="center"  />
										<odin:gridColumn dataIndex="a0192a" header="单位职务" width="0"  align="center" />
										<odin:gridColumn dataIndex="a0163" header="人员状态" width="0" align="center"  isLast="true"  />
									</odin:gridColumnModel>
									<odin:gridJsonData>
										{
									        data:[]
									    }
									</odin:gridJsonData>
								</odin:editgrid>
							</div>							
						</td>
						<td style="width: 8%;height: 100%;" align="center">
							<div id='rigthBtn' style="display: none"></div>
							<br>
							<div id='rigthAllBtn'></div>
							<br>
							<div id='liftBtn' style="display: none"></div>
							<br>
							<div id='liftAllBtn'></div>
						</td>
						<td width="46%;" height="100%;" align="center">
							<div id="selectByPersonIdDiv2">
								<odin:editgrid property="selectName" title="输出列表" width="300" height="350" autoFill="false" >
									<odin:gridJsonDataModel>
											<odin:gridDataCol name="personcheck" />
											<odin:gridDataCol name="a0000" />
											<odin:gridDataCol name="a0101" />
											<odin:gridDataCol name="a0104" />
											<odin:gridDataCol name="a0163" />
											<odin:gridDataCol name="a0192a" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 header="selectall" width="0" hidden="true"
												editor="checkbox" dataIndex="personcheck" edited="true"
												hideable="false" gridName="persongrid" />
											<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
											<odin:gridEditColumn2 dataIndex="a0104" header="性别" align="center" width="45" editor="select" edited="false" codeType="GB2261" />
											<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="100" align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false"  codeType="ZB126" />
										<odin:gridColumn header="干部批次" renderer="renderDel" align="center"  dataIndex="del" width="80" sortable="false" isLast="true" />
										</odin:gridColumnModel>
										<odin:gridJsonData>
											{
										        data:[]
										    }
										</odin:gridJsonData>
								</odin:editgrid>
							</div>
							<div id="selectByYnIdDiv2"  style="display:none">	
								<odin:editgrid property="selectName2" title="输出列表" width="300" height="330" autoFill="false" >
									<odin:gridJsonDataModel>
											<odin:gridDataCol name="personcheck" />
											<odin:gridDataCol name="a0000" />
											<odin:gridDataCol name="a0101" />
											<odin:gridDataCol name="a0104" />
											<odin:gridDataCol name="a0163" />
											<odin:gridDataCol name="a0192a" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 header="selectall" width="0" hidden="true"
												editor="checkbox" dataIndex="personcheck" edited="true"
												hideable="false" gridName="persongrid" />
											<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="批次名称" width="110" align="center" />
											<odin:gridColumn dataIndex="a0104" header="填报处室" align="center" width="145"  />
											<odin:gridColumn dataIndex="a0192a" header="单位职务" width="0" align="center" />
											<odin:gridColumn dataIndex="a0163" header="人员状态" width="0" align="center" isLast="true" />
										</odin:gridColumnModel>
										<odin:gridJsonData>
											{
										        data:[]
										    }
										</odin:gridJsonData>
								</odin:editgrid>
							</div>													
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</div>
<div align="right" style="margin-top: 20px;">
	<table>
		<tr>
			<td>
				<input type="button" height="20" width="40" value="清除待选列表" onclick="clearRst()">&nbsp;&nbsp;
			</td>
			<div style="display:none">
	 			<!-- odin:select2  property="tplb"  label="从其它会议中提取（选择后点击确定即可）"  odin:select2  -->
	 		</div>
			<td><input type="button" height="20" width="40" value="&nbsp;确&nbsp;&nbsp;定&nbsp;" onclick="saveSelect()">&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	//单击来回调选人员
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
	gridcq.on("rowclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = sStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
		}
		gStore.remove(rowData);
		gridcq.view.refresh();
	});

	selectName.on("rowclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = gStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}

		sStore.remove(rowData);
		selectName.view.refresh();
	});
})

function renderDel(){
	var index=document.getElementById("selectByInputYnId").selectedIndex;
	document.getElementById("selectByInputYnId").options[index].text;
	document.getElementById("selectByInputYnId").options[index].innerHTML;
	var obj=document.getElementById("selectByInputYnId");
	        for(i=0;i<obj.length;i++) {
	           if(obj[i].selected==true) {
	            var text=obj[i].text;
	            }
	　　　　}
		return text;
}

</script>

</html>