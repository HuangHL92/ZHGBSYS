<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
#grid1 {
	width: 316px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">


</script>
<table>
	<tr>
		<td width="45%">
			<div style="width: 640px; height: 200px;">
				<odin:groupBox property="group1" title="已有信息群">
					<table border="1">
						<tr>
							<td rowspan="2">
								<table>
									<tr>
										<td><odin:grid property="grid1" autoFill="false"
												height="165" url="/">
												<odin:gridJsonDataModel id="id" root="data">
													<odin:gridDataCol name="secretlevel" />
													<odin:gridDataCol name="rowsname" isLast="true" />
												</odin:gridJsonDataModel>
												<odin:gridColumnModel>
													<odin:gridRowNumColumn></odin:gridRowNumColumn>
													<odin:gridEditColumn header="等级" align="left"
														edited="false" width="270" dataIndex="secretlevel"
														editor="text" hidden="true"/>
													<odin:gridEditColumn header="信息群名" align="left"
														edited="false" width="270" dataIndex="rowsname"
														editor="text" isLast="true" />
												</odin:gridColumnModel>
											</odin:grid></td>
									</tr>
								</table>

							</td>
							<td colspan="3"><odin:groupBox title="信息群集属性">
									<table style="height: 100px; width: 300px;">
										<tr>
											<td>
												<table>
													<tr>
														<td><odin:textEdit property="t1" label="群代码" required="true" maxlength="1"  onchange="changeValue();" value=" "
																width="230"></odin:textEdit></td>
													</tr>
													<tr>
														<td><odin:textEdit property="t2" label="群名称" required="true" maxlength="200" onchange="specialValue();"
																width="230"></odin:textEdit></td>
													</tr>
													<tr>
														<td><odin:select property="t3" label="等级"
																width="230"></odin:select></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<table>
										<tr align="center" id="showbtn1">
											<td style="width:100;padding-left:40px;" align="center">
											<input  type="button" class="yellowbutton" value="新&nbsp;增" id="btn1" onclick="addtolist()"/>
											</td>
											<td style="width:100;" align="center">
											<input  type="button" class="graybutton" value="删&nbsp;除" disabled="disabled"/>
											</td>
											<td style="width:100;" align="center">
											<input  type="button" class="graybutton" value="保&nbsp;存" disabled="disabled"/>
											</td>
										</tr>
										<tr align="center" id="hiddenbtn1" style="display: none;">
											<td style="width:100;padding-left:40px;" align="center">
											<input  type="button" class="graybutton" value="新&nbsp;增" disabled="disabled"/>
											</td>
											<td style="width:100;" align="center">
											<input  type="button" class="yellowbutton" value="删&nbsp;除" id="btn2" onclick="deletetolist()"/>
											</td>
											<td style="width:100;" align="center">
											<input  type="button" class="yellowbutton" value="保&nbsp;存" id="btn3" onclick="changetolist()"/>
											</td>
										</tr>
									</table>
								</odin:groupBox>
							</td>
						</tr>
					</table>
				</odin:groupBox>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">

function addtolist(){//添加到列表
	radow.doEvent("addtolist");
}

function deletetolist(){//从列表删除
	if(confirm("    确定删除吗？")){    
		radow.doEvent("deletetolist");
	}else{
	   	return ;
	}
	
}

function changetolist(){//列表中国修改数据
	radow.doEvent("changetolist");
}

function changeValue(){
	radow.doEvent("changeValue");
}

function specialValue(){
	radow.doEvent("specialValue");
}
function showbtn1(){
	document.getElementById('showbtn1').style.display='block'; 
	document.getElementById('hiddenbtn1').style.display='none';
}
function hiddenbtn1(){
	document.getElementById('showbtn1').style.display='none'; 
	document.getElementById('hiddenbtn1').style.display='block';
}
</script>

