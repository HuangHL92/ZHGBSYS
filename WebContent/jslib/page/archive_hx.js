// 档案相关, by YZQ on 2014/12/21
$(function() {
	Archive.init();
});

Archive = {
	// 初始化
	init: function() {
		
	},
	openArchive: function(rsid, applyid) {	
		var archiveUrl = System.rootPath + '/archive/archive!viewArchive.action?rsid=' + rsid + '&applyid=' + applyid;
		top.window.open(archiveUrl, '查阅档案' + rsid, 'fullscreen', "directories=0,fullscreen=1,toolbar=0,location=0,status=0,menubar=0,scrollbars=0,titlebar=0,resizable=1",true) ; 
	},
	// 查看档案
	viewArchive: function(personcode) {
		var url = System.rootPath + '/archive/archive!checkArchiveViewRequest.action';
		$.ajax({
			url: url,
			data: { 'personcode': personcode },
			success: function(result){
				if (result.userType == 'undefined') {
					$.messager.alert('错误', '请求出现异常，' + data);
					return;
				}
				
				if (result.userType != '1' && 
				    result.userType != '2' && 
					result.userType != '3' &&
					result.userType != '4') {
					$.messager.alert('错误', '当前平台用户不是档案系统的查询用户，无法查阅档案！');
					return;
				}
				
				if (result.rsid == 0) {
					$.messager.alert('错误', '档案系统中不存在该干部！');
					return;
				}
				
				// 审批人员或者领导
				if (result.userType == '1' || result.userType == '2') {
					Archive.openArchive(result.rsid, 0);
				}
				// 业务处领导或者业务处人员
				else if (result.userType == '3' || result.userType == '4') {
					// 显示所有请求
					$("#archiveDialog").data("applyUserId", result.yhbh);
					$("#archiveDialog").data("rsid", result.rsid);
					$("#archiveDialog").data("rsname", result.rsname);
					$("#archiveDialog").data("userType", result.userType);
		
					Archive.showApprovelList();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	},
	// 显示授权请求
	showApprovelList: function() {
		$("#archiveDialog").show();
		$("#archiveDialog").dialog({
			width: 950,
			height: 320,
			title: $("#archiveDialog").data("rsname") + '同志的档案查阅授权记录',
			modal: true
		});
		$("#archiveDialog").dialog('open');
		Archive.queryApprovelList();
	},
	// 获取授权请求
	queryApprovelList: function() {
		var url = System.rootPath + '/archive/archive!getApprovelList.action';
		url = url + '?applyUserId=' + $("#archiveDialog").data("applyUserId");
		url = url + '&rsid=' + $("#archiveDialog").data("rsid");
		
		$("#archiveTable").datagrid({
			fit: true,
			url: url,
			pagination: true,
			pageSize: 10,
			singleSelect: true,
			toolbar: [
				{
					id: 'btn_add',
					iconCls: 'icon-add',
					text: '申请查阅',
					handler: function() {
						Archive.showSubmitApprovelDialog(
							$("#archiveDialog").data("rsid"), 
							$("#archiveDialog").data("rsname"),
							"cc", "archiveTable");
					}
				}
			],
			columns: [[
				{ field: 'APPLY_DATE', title: '申请日期', width: 120, align: 'center' },
				{ field: 'LOOK_REASON', title: '查阅原因', width: 150, align: 'center' },
				{ field: 'EXAM_RSNAME', title: '查档简况', width: 150, align: 'center' },
				
				{ field: 'DEPARTMENTLEADERAGREE', title: '部门领导', width: 100, align: 'center',
					formatter: function(value, rowData, rowIndex) {
						var userType = $("#archiveDialog").data("userType");
						if (userType == '3') {
							return '已批准';
						}
						else {
							if (rowData.DEPARTMENTLEADERAGREE == '1') {
								return '已批准';
							}
							else if (rowData.DEPARTMENTLEADERAGREE == '2') {
								return '已拒绝';
							}
							else {
								return '审核中';
							}
						}
					}
				},
				{ field: 'JULEADERAGREE', title: '档案管理员', width: 100, align: 'center',
					formatter: function(value, rowData, rowIndex) {
						var userType = $("#archiveDialog").data("userType");
						if (userType == '3') {
							if (rowData.DEAL_STATE == '1') {
								return '等待审批';
							}
							else if (rowData.DEAL_STATE == '2') {
								return '已批准';
							}
							else {
								return '已拒绝';
							}
						}
						else {
							if (rowData.DEPARTMENTLEADERAGREE == '1') {
								if (rowData.DEAL_STATE == '1') {
									return '等待审批';
								}
								else if (rowData.DEAL_STATE == '2') {
									return '已批准';
								}
								else if (rowData.DEAL_STATE == '3'){
									return '已拒绝';
								}
								else {
									return;
								}
							}
							else {
								return '';
							}
						}
						
						return '';
					}
				},
				{ field: 'jssj', title: '结束时间', width: 120, align: 'center',
					formatter: function(value, rowData, rowIndex) {
						if (rowData.DEAL_STATE == '2') {
							if (rowData.END_DATE == null) {
								var h = parseInt(rowData.TIME_OUT / 3600);
								var m = parseInt((rowData.TIME_OUT - h * 3600) / 60);
								return '剩余' + h + '小时' + m + '分钟';
							}
							else {
								return rowData.END_DATE;
							}
						}
						else {
							return '';
						}
					}
				},
				{ field: 'action', title: '操作', width: 80, align: 'center',
					formatter: function(value, rowData, rowIndex) {
						if (rowData.DEAL_STATE == '2') {
							//if (rowData.END_DATE == null) {
							//	return '请到档案系统查看';
							//}
							//else {
								var rsid = $("#archiveDialog").data('rsid');
								return "<a href='#' onclick='Archive.openArchive(" + rsid + "," + rowData.ID + ");'>查看</a>";
							//}
						}
						else {
							return '';
						}
					}
				}
			]]					
		});
	},
	// 多人
	batchSubmitApprove: function(datagrid) {
		var rows = $("#" + datagrid).datagrid('getSelections');
		if (rows == null || rows.length == 0) {
			$.messager.alert('错误', '请选择至少一位干部！');
			return;
		}
		if (rows) {
			var personcodes = '';
			$(rows).each(function(index, row){
				if (personcodes == '') personcodes = row.personcode;
				else personcodes = personcodes + "," + row.personcode;
			});
			
			var url = System.rootPath + '/archive/archive!checkBatchSubmitApprovelRequest.action';
			$.ajax({
				url: url,
				data: { 'personcodes': personcodes },
				success: function(result){
					if (result.userType == 'undefined') {
						$.messager.alert('错误', '请求出现异常，' + data);
						return;
					}
					
					if (result.userType != '1' && 
						result.userType != '2' && 
						result.userType != '3' &&
						result.userType != '4') {
						$.messager.alert('错误', '当前平台用户不是档案系统的查询用户，无法查阅档案！');
						return;
					}
					
					// 审批人员或者领导
					if (result.userType == '1' || result.userType == '2') {
						$.messager.alert('错误', '你已经是档案系统的审批用户，无需申请查阅！');
						return;
					}
					
					if (result.rsid == null || result.rsid == '') {
						$.messager.alert('错误', '档案系统中不存在任何一个所选干部！');
						return;
					}
					
					if (result.notRsUserName != null && result.notRsUserName != '') {
						$.messager.confirm('提示', '下列这些干部不存在档案系统中，将跳过。确定要继续吗？<br/>' + result.notRsUserName, function(isOk) {
							if (isOk) {
								// 显示所有请求
								$("#archiveDialog").data("applyUserId", result.yhbh);
								$("#archiveDialog").data("rsid", result.rsid);
								$("#archiveDialog").data("rsname", result.rsname);
								$("#archiveDialog").data("userType", result.userType);
								
								Archive.showSubmitApprovelDialog(
									result.rsid, 
									result.rsname,
									"cc", null);
							}
						});
					}
					else {
						// 显示所有请求
						$("#archiveDialog").data("applyUserId", result.yhbh);
						$("#archiveDialog").data("rsid", result.rsid);
						$("#archiveDialog").data("rsname", result.rsname);
						$("#archiveDialog").data("userType", result.userType);
						
						Archive.showSubmitApprovelDialog(
							result.rsid, 
							result.rsname,
							"cc", null);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert("error", textStatus + errorThrown);
				}
			});
		}
	},
	// 显示提交查阅申请对话框
	showSubmitApprovelDialog: function(rsids, rsnames, mask, datagrid) {
		$("#archiveSubmitDialog").show();
		$("#archiveSubmitDialog").dialog({
			width: 600,
			height: 550,
			title: '提交查阅申请',
			modal: true,
			onBeforeOpen: function() {
				$("#archiveCheckAll").attr('checked', false);
				$(".archiveCheckBox").attr('checked', false);
				$("#archiveCysx").val('30天0小时0分钟');
				$("#archiveLookReason").val('工作查考');
				$("#archiveCysxSpan").hide();
				$("#archiveRsnames").html(rsnames);
			},
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var memo = getCheckedArchiveFL();
					if (memo == '') {
						$.messager.alert('错误', '查阅内容不能为空！');
						return;
					}
					
					var lookReason = $("#archiveLookReason").val();
					var cysx = $("#archiveCysx").val();
					if (cysx == '自选') {
						cysx = getCustomCysx();
						if (cysx == '') return;
					}
					
					// 保存
					try {
						System.openLoadMask("#" + mask, "保存中...");
					}
					catch (e) {
					}
		
					$.ajax({
						type: "POST",
						url: System.rootPath+ '/archive/archive!createApprovel.action',
						data: { 
							rsids: rsids,
							rsnames: rsnames,
							memo: memo,
							cysx: cysx,
							lookReason: lookReason
						},
						success:function(json){
							System.closeLoadMask("#" + mask);
							
							try {
								if (json.indexOf("保存成功") >= 0) {							
									$.messager.alert('提示', '申请成功!');	
									if (datagrid != null) {
										$('#' + datagrid).datagrid('reload');
									}
								}
								else {
									alert('保存失败，错误信息：' + json);						
								}
							}
							catch (ex) {
								alert('提示', '保存失败，错误信息：' + ex);						
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							System.closeLoadMask("#" + mask);
							$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
						}
					});		
					
					$('#archiveSubmitDialog').dialog('close');							
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#archiveSubmitDialog').dialog('close');
				}
			}]
		});
		$("#archiveSubmitDialog").dialog('open');
	}
};

function setSameArchiveCheckbox(obj,name)
{
	var State;
	if(obj.checked)
		State = true;
	else
		State = false;
	var txts=document.getElementsByTagName("input");  
	for(var i=0;i <txts.length;i++)
	{  
		if(txts[i].type=="checkbox" && txts[i].name == name)  
		{  
				txts[i].checked=State;
		}
	}
}

function getCheckedArchiveFL() {
	var content = ''
	$(".archiveCheckBox").each(function() {
		if ($(this).attr('checked')) {
			if (content == '') content = $(this).val();
			else content = content + ';' + $(this).val();
		}
	});
	
	return content;
}

function onArchiveCheckAll(obj) {
	$(".archiveCheckBox").attr('checked', obj.checked);
}

function toggleArchiveCysxTime() {
	var show = ($("#archiveCysx").val() == '自选');
	if (show) {
		$("#archiveCysxSpan").show();
	}
	else {
		$("#archiveCysxSpan").hide();
	}
}

function getCustomCysx() {
	var day = $("#archiveCysxDay").val();
	var hour = $("#archiveCysxHour").val();
	var min = $("#archiveCysxMin").val();

	if (day != "" && hour != "" && min != "") {
		  if(day != 0 || hour != 0 || min != 0){
			   var re = /^([1-9]\d*)$/;  //判断正整数  
			   if (day != 0) {
					if (!re.test(day)) {
						$.messager.alert('错误', "天数输入应为正整数！");
						return '';
					}
			   }
			   if (hour != 0) {              
					if (!re.test(hour)) {
						$.messager.alert('错误', "小时数输入应为正整数！");
						return '';
					}
					if (hour > 23) {
						 $.messager.alert('错误', "小时输入不正确，应该在0-23之间！");
						 return '';
					}
			   }
			   if (min != 0) {
					if (!re.test(min)) {
						 $.messager.alert('错误', "分钟数输入应为正整数！");
						 return '';
					}
					if (min > 59) {
						 $.messager.alert('错误', "分钟数输入不正确，应该在0-59之间！");
						 return '';
					}
			   }
		  }
		  else {
			   $.messager.alert('错误', "天、小时、分钟的输入不能全部为0！");
			   return '';
		  }
	 }
	 else {
		  $.messager.alert('错误', "天、小时、分钟的输入不能为空!");
		  return '';
	 }

	 return day + '天' + hour + '小时' + min + '分钟';
}
