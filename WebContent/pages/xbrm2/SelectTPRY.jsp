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
<odin:hidden property="mark" /> <!--  �Ƿ�����ˢ��grid�ı�� -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />


<odin:hidden property="sql2" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />
<odin:hidden property="jw" title="�Ƿ������ί���������"/>
<div id="groupTreeContent" style="height: 94%;width: 100%;margin-left: 12px;">
	<table style="height: 100%;">
		<tr valign="top">
			<td width="20%" height="100%" rowspan="0" colspan="0">
				<div style="height: 5%;background-color: #cedff5;" align="center">
					<input type="checkbox"  id="continueCheckbox" style="display:none" onclick="continueChoose()"><!-- <font style="font-size: 13">����ѡ��</font> -->
					<input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font style="font-size: 13">�����¼�</font>
				</div>
				<div class="busy">
					<div id="tree-div" style="border: 2px solid #c3daf9;height: 99%; width: 98%;"></div>
				</div>
			</td>  
			<td id="gridcqtd" style="width: 67%;height: 100%;">
				<table style="width: 100%;height: 100%;border: 2px solid #c3daf9;">
					<tr>
						<td colspan=3>    
							<input type="radio" checked name="rdoSwitch" id="selectByPersonIdBtn" onclick="selectByPersonId()"><font style="font-size: 13">��λ��Աѡ��</font>
							<font style="font-size: 13">
								&nbsp;�м��������Աѡ��:<select id="selectByInputYnId" onchange="selectByInputYnIdClick()">
									<option>&nbsp;&nbsp;</option>		
								</select>
								
								<!-- odin:select2  property="tplb"  label="��������������ȡ��ѡ�����ȷ�����ɣ�"  odin:select2  -->
								
							</font>&nbsp;&nbsp;
							<input type="radio"         name="rdoSwitch" id="selectByYnIdBtn" onclick="selectByYnId()"><font style="font-size: 13">�������λ���</font>	
						</td>
					</tr>					
					<tr   style="width: 100%;">
						<td colspan="3">
						<table style="height:50px;width:100%">
							<tr>
								<td style="width: 130px;" >
									<div style="font-size: 12px;margin-top: 10px;margin-left: 10px;color: red;">�������/���֤��</br>ѯ���밴���Ÿ�����</div>
								</td>
								<td>
									<odin:select property="tpye" label="" canOutSelectList="true" value = '1' data="['1','����'],['2','���֤����']" width="120"></odin:select>
								</td>
								<td>
									<textarea  class="x-form-text x-form-field" style="width: 390px;height:25px;  margin-top:1px;" name="queryName" id="queryName"  ></textarea> 
									
								</td>
								<td> <input id="buttonSearch" type="button" style="position: relative;top: 2px;" onclick="toDOQuery()" value="����"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr style="width: 100%;" align="center">
						<td width="46%" height="100%">
							
							<div id="selectByPersonIdDiv" >
								<odin:editgrid property="gridcq" title="��ѡ�б�"  width="300" height="350" pageSize="9999"
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
										<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
										<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
										<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="100"  align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" width="65" align="center" editor="select" edited="false"  codeType="ZB126" />
										<odin:gridColumn header="�ɲ�����" renderer="renderDel" align="center"  dataIndex="del" width="80" sortable="false" isLast="true" />
									</odin:gridColumnModel>
									<odin:gridJsonData>
										{
									        data:[]
									    }
									</odin:gridJsonData>
								</odin:editgrid>
							</div>
							<div id="selectByYnIdDiv" style="display:none"> 
								<odin:editgrid property="gridcq2" title="��ѡ�б�"  width="300" height="330" pageSize="9999"
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
										<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="��������" width="110" align="center" />
										<odin:gridColumn dataIndex="a0104" header="�����" width="145" align="center"  />
										<odin:gridColumn dataIndex="a0192a" header="��λְ��" width="0"  align="center" />
										<odin:gridColumn dataIndex="a0163" header="��Ա״̬" width="0" align="center"  isLast="true"  />
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
								<odin:editgrid property="selectName" title="����б�" width="300" height="350" autoFill="false" >
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
											<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
											<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" align="center" width="45" editor="select" edited="false" codeType="GB2261" />
											<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="100" align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" width="65" align="center" editor="select" edited="false"  codeType="ZB126" />
										<odin:gridColumn header="�ɲ�����" renderer="renderDel" align="center"  dataIndex="del" width="80" sortable="false" isLast="true" />
										</odin:gridColumnModel>
										<odin:gridJsonData>
											{
										        data:[]
										    }
										</odin:gridJsonData>
								</odin:editgrid>
							</div>
							<div id="selectByYnIdDiv2"  style="display:none">	
								<odin:editgrid property="selectName2" title="����б�" width="300" height="330" autoFill="false" >
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
											<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="��������" width="110" align="center" />
											<odin:gridColumn dataIndex="a0104" header="�����" align="center" width="145"  />
											<odin:gridColumn dataIndex="a0192a" header="��λְ��" width="0" align="center" />
											<odin:gridColumn dataIndex="a0163" header="��Ա״̬" width="0" align="center" isLast="true" />
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
				<input type="button" height="20" width="40" value="�����ѡ�б�" onclick="clearRst()">&nbsp;&nbsp;
			</td>
			<div style="display:none">
	 			<!-- odin:select2  property="tplb"  label="��������������ȡ��ѡ�����ȷ�����ɣ�"  odin:select2  -->
	 		</div>
			<td><input type="button" height="20" width="40" value="&nbsp;ȷ&nbsp;&nbsp;��&nbsp;" onclick="saveSelect()">&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	//�������ص�ѡ��Ա
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
	��������}
		return text;
}

</script>

</html>