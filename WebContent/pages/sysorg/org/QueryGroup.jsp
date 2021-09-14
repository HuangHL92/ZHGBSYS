<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%-- <%@include file="/comOpenWinInit.jsp" %> --%>
<script  type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<div class="mainDiv">
	<table style="width: 100%;height: 100%;">
		<tr height="80%" align="center" valign="top">
			<td width="25%">
				<div style="width: 100%;height: 100%;">
					<odin:groupBox title="查询条件" width="90%">
						<table border="0">
							<tr height="10"><td><td></tr>
							<tr>
								<odin:select2 property="b0194" label="单位类型" data="['1','法人单位'],['2','内设机构'],['3','机构分组']" onchange="typeChange()"></odin:select2>
							</tr>
							<tr height="10"><td><td></tr>
							<tr>
								<odin:textEdit property="b0114" validator="b0114Length" label="机构编码"></odin:textEdit>
							</tr>
							<tr height="10"><td><td></tr>
							<tr>
								<odin:textEdit property="b0101"  validator="b0101Length" label="机构名称" ></odin:textEdit>
							</tr>
							<tr height="10"><td><td></tr>
							<tr>
								<odin:textEdit property="b0104"  validator="b0104Length" label="简&nbsp;&nbsp;&nbsp;&nbsp;称" ></odin:textEdit>
							</tr>
							<tr height="10"><td><td></tr>
							<tr class='tr1'>
								<tags:PublicTextIconEdit property="b0117" label="所在政区" codetype="ZB01" codename="code_name3" maxlength="8" />
							</tr>
							<tr height="10"><td><td></tr>
							<tr class='tr1'>
								<tags:PublicTextIconEdit property="b0124" label="隶属关系" codetype="ZB87" maxlength="8" />
							</tr>
							<tr height="10"><td><td></tr>
							<tr class='tr1'>
								<tags:PublicTextIconEdit property="b0131" label="机构类别" codetype="ZB04" maxlength="8" />
							</tr>
							<tr height="10"><td></td></tr>
							<tr class='tr2'>
								<tags:PublicTextIconEdit property="b0127" label="机构级别" codetype="ZB03" maxlength="8" />
							</tr>
							<tr height="10"><td><td></tr>
							<tr height="10"><td><td></tr>
							<tr height="10"><td><td></tr>
						</table>
					</odin:groupBox>
					<div>
						<table>
							<tr>
							<td>
								<odin:button text="清空" handler="clearValue" />
							</td>
							<td width="10"></td>
							<td>
								<odin:button text="查询" handler="queryDO" />
							</td>
							<td width="10"></td>
							<td>
								<odin:button text="导出" handler="exportExcel"></odin:button>
							</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
			<td width="75%" >
				<div style="position: relative;top: 7px;">
					<table style="width:100%;">
						<tr>
							<td>
								<odin:editgrid property="queryGroupByInfo" title="查询结果"  height="440" width="390"  autoFill="true" 
								 bbarId="pageToolBar" rowDbClick="rowDoubleC" pageSize="20" isFirstLoadData="false"  url="/" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck"/>
										<odin:gridDataCol name="b0111" />
										<odin:gridDataCol name="b0114" />
										<odin:gridDataCol name="b0101" />
										<odin:gridDataCol name="b0194" isLast="true" />
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn  >
										<odin:gridEditColumn header="selectall" dataIndex="personcheck" align="center" edited="true" editor="checkbox" width="40" />
										<odin:gridEditColumn header="" dataIndex="b0111"  edited="false" editor="text" hidden="true" />
										<odin:gridEditColumn header="机构编码" dataIndex="b0114" align="center" edited="false" editor="text" width="150" />
										<odin:gridEditColumn header="机构名称" dataIndex="b0101"  edited="false" editor="text" width="200" />
										<odin:gridEditColumn2 header="机构类型" align="center" dataIndex="b0194" width="100" editor="select" edited="false" selectData="['1','法人单位'],['2','内设机构'],['3','机构分组']" isLast="true"/>		
									</odin:gridColumnModel>
								</odin:editgrid>
							</td>
						</tr>
					</table>
 				</div>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	//	清空
	function clearValue(){
		$('#b0194').val("");
		$('#b0114').val("");
		$('#b0101').val("");
		$('#b0104').val("");
		$('#b0117').val("");
		$('#b0124').val("");
		$('#b0131').val("");
		$('#b0127').val("");
		$('#b0194_combo').val("");
		$('#b0114_combo').val("");
		$('#b0101_combo').val("");
		$('#b0104_combo').val("");
		$('#b0117_combo').val("");
		$('#b0124_combo').val("");
		$('#b0131_combo').val("");
		$('#b0127_combo').val("");
	}
	//机构类型变更逻辑
	function typeChange(){
		var type=document.getElementById('b0194').value;
	}
	//查询
	function queryDO(){
		radow.doEvent('queryGroupByInfo.dogridquery');
	}
	//双击列表查询
	function rowDoubleC(rowIndex,colIndex,dataIndex,gridName){
		try{
			var record = odin.ext.getCmp('queryGroupByInfo').getSelectionModel().getSelections()[0];
			radow.doEvent("rowDoubleWin",record.data.b0111);
		}catch(err){
		}
	}
	//导出列表数据
	function exportExcel(){
		radow.doEvent('exportExcel');
	}
	//导出列表数据
	function getDown(path){
		window.open('ProblemDownServlet?method=downFile&prid='+path);
	}

	window.document.onkeydown=func_press;
	function func_press(ev){
		ev=(ev)?ev:window.event;
		if(ev.keyCode==8||ev.keyCode=='8'){//backspace
			if('b0117_combo' == document.activeElement.id){//机构政区
				$('#b0117_combo').val("");
				$('#b0117').val("");
			}
			if('b0124_combo' == document.activeElement.id){//隶属关系
				$('#b0124_combo').val("");
				$('#b0124').val("");
			}
			if('b0131_combo' == document.activeElement.id){//机构类别
				$('#b0131_combo').val("");
				$('#b0131').val("");
			}
			if('b0127_combo' == document.activeElement.id){//机构级别
				$('#b0127_combo').val("");
				$('#b0127').val("");
			}
		}
	}
</script>