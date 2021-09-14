// 比特流数字档案查阅系统接口
var g_archiveUser;

$(function() {
	Archive.init();
});

Archive = {
	// 初始化
	init: function() {
		
	},
	// 查阅档案（admin, 审批人员，领导）
	openArchiveForLeader: function(personcode, personname) {
		var url = System.archiveWeb + '/archive/view-archive!show.action?id=' + personcode;
		window.open(url, '查阅档案' + personcode, 'fullscreen', "directories=0,fullscreen=1,toolbar=0,location=0,status=0,menubar=0,scrollbars=0,titlebar=0,resizable=1",true) ; 
		//window.open(url);
		
	},
	// 查阅档案（其他用户）
	openArchive: function(requestId) {	
		var url = System.archiveWeb + '/archive/view-archive-by-request.action?id=' + requestId;
		window.open(url, '查阅档案' + requestId, 'fullscreen', "directories=0,fullscreen=1,toolbar=0,location=0,status=0,menubar=0,scrollbars=0,titlebar=0,resizable=1",true) ; 
		//window.open(url);
	},
	// 检查档案查阅请求，看下一步怎么操作
	checkRequstForView: function(personcode, personname, req) {
		var url = System.archiveWeb + '/api/auto-login.action';
		$.ajax({
			url: url,
			dataType: 'jsonp',
			jsonp: "callback",
            jsonpCallback: "callback",
			method: 'post',
			data: { 'userName': req.user_name, 'requestKey': req.request_key },
			success: function(result){
				if (result.code != 'success') {
					$.messager.alert('错误', result.message, "error");
					return;
				}
				
				g_archiveUser = result.object;
				
				// admin, 审批人员或者领导
				if (g_archiveUser.user_role <= 2) {
					window.setTimeout(function() {
						Archive.openArchiveForLeader(personcode, personname);
					}, 1000);
				}
				// 其他人员，需要审批，那么显示请求列表
				else {
					window.setTimeout(function() {
						Archive.showRequestList(personcode, personname);
					}, 1000);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("错误", textStatus + errorThrown, "error");
			}
		});
	},
	// 检查调整到申请列表的查阅请求，看下一步怎么操作
	checkRequstForGotoRequestPage: function(req) {
		var url = System.archiveWeb + '/api/auto-login.action';
		$.ajax({
			url: url,
			dataType: 'jsonp',
			jsonp: "callback",
            jsonpCallback: "callback",
			method: 'post',
			data: { 'userName': req.user_name, 'requestKey': req.request_key },
			success: function(result){
				if (result.code != 'success') {
					$.messager.alert('错误', result.message, "error");
					return;
				}
				
				g_archiveUser = result.object;
				
				// admin, 审批人员或者领导
				if (g_archiveUser.user_role <= 2) {
					System.showErrorMsg('你的帐号不需要申请查阅申请！');
				}
				// 其他人员，跳转到申请列表
				else {
					window.setTimeout(function() {
						window.open(System.archiveWeb + '/userrequest/requestor.action');
					}, 500);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("错误", textStatus + errorThrown, "error");
			}
		});
	},
	// 检查申请查阅请求，看下一步怎么操作
	checkRequstForBatchSubmit: function(personcode, personname, req) {
		var url = System.archiveWeb + '/api/auto-login.action';
		$.ajax({
			url: url,
			method: 'post',
			dataType: 'jsonp',
			jsonp: "callback",
            jsonpCallback: "callback",
			data: { 'userName': req.user_name, 'requestKey': req.request_key },
			success: function(result){
				if (result.code != 'success') {
					$.messager.alert('错误', result.message, "error");
					return;
				}
				
				g_archiveUser = result.object;
				
				// admin, 审批人员或者领导
				if (g_archiveUser.user_role <= 2) {
					$.messager.alert("错误", '你的帐号不需要提交查阅申请！', "error");
				}
				// 其他人员，提交请求
				else {
					window.setTimeout(
					function() { Archive.showSubmitApprovelDialog(
							personcode, 
							personname,
					"cc", null);}, 1000);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("错误", textStatus + errorThrown, "error");
			}
		});
	},
	// 跳转到申请页面
	gotoRequestPage: function() {
		var url = System.rootPath + '/archive/bits-da-archive!createRequest.action';
		$.post(url, function(req) {
			try {
				if (req && req.user_name && req.request_key) {
					Archive.checkRequstForGotoRequestPage(req);
				}
				else {
					$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
				}
			}
			catch (e) {
				$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
			}
		});
	},
	// 查阅档案
	viewArchive: function(personcode, personname) {
		var url = System.rootPath + '/archive/bits-da-archive!createRequest.action';
		$.post(url, function(req) {
			try {
				if (req && req.user_name && req.request_key) {
					Archive.checkRequstForView(personcode, personname, req);
				}
				else {
					$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
				}
			}
			catch (e) {
				$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
			}
		});
	},
	// 显示授权请求
	showRequestList: function(personcode, personname) {
		if ($("#archiveDialog").length <= 0) {
			$(document.body).append('<div id="archiveDialog" style="display:none;"><table id="archiveTable"></table></div>');
		}
		$("#archiveDialog").show();
		$("#archiveDialog").dialog({
			width: 950,
			height: 480,
			title: personname + '同志的档案查阅申请列表',
			modal: true,
			onOpen: function() {
				Archive.queryApprovelList(personcode, personname);
			}
		});
		// EasyUI 1.4.5，会自动打开对话框？？
		//$("#archiveDialog").dialog('open');
	},
	// 获取授权请求
	queryApprovelList: function(personcode, personname) {
		var url = System.archiveWeb + '/userrequest/requestor!searchByApi.action?searchParam.personId=' + personcode;
		$("#archiveTable").datagrid({
			loader: function(param,success,error) {
				$.ajax({
					type: 'get',
					url: url + '&pageNumber=' + param.page + '&pageSize=' + param.rows,
					dataType: 'jsonp',
					jsonp: "callback",
					jsonpCallback: "callback",
					success: function(data){
						success(data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert("错误", textStatus + errorThrown, "error");
					}
				});
			},
			fit: true,
			pageSize: 10,
			pageList: [10, 20, 30],
			pagination: true,
			rownumbers: true,
			singleSelect: true,
			toolbar: [
				{
					id: 'btn_add',
					iconCls: 'icon-add',
					text: '申请查阅',
					handler: function() {
						Archive.showSubmitApprovelDialog(
							personcode, 
							personname,
							"cc", "archiveTable");
					}
				}
			],
			columns: [[
				{ field: 'requested_date', title: '申请日期', width: 150, align: 'center' },
				{ field: 'cyly', title: '查阅理由', width: 120, align: 'center' },
				{ field: 'person_names', title: '被查阅人', width: 100, align: 'center' },
				{ field: 'leader_approval_status', title: '部门领导审批', align: 'center', width: 100,
					formatter: function(value, row, index) {
						if (row.leader_id == 0) {
							return '不需要';
						}
						else {
							if (value == 0) return '待审批';
							else if (value == 1) return '未通过<br/>' + row.leader_approval_remarks;
							else if (value == 2) return '已通过';
							else return '';
						}
					}
				},
				{ field: 'admin_approval_status', title: '审批人员审批', align: 'center', width: 100,
					formatter: function(value, row, index) {
						if (value == 0) return '待审批';
						else if (value == 1) return '未通过<br/>' + row.admin_approval_remarks;
						else if (value == 2) return '已通过';
						else return '';
					}
				},
				{ field: 'view_start_date', title: '查阅时间', align: 'center',  width: 120,
					formatter: function(value, row, index) {
						if (row.request_status == 2) {
							return Utils.toLongDate(row.view_start_date) + '<br/>' + Utils.toLongDate(row.view_end_date);
						}
						else return '';
					}
				},
				{ field: 'view_time', title: '查阅时限', align: 'center',  width: 120,
					formatter: function(value, row, index) {
						if (row.request_status == 2) {
						var html = '无';
						if (row.view_time_type == 0) {
							if (row.view_rest_time == 0) html = '已达阅档时限';
							else {
								var h = parseInt(row.view_rest_time / 3600);
								var m = parseInt((row.view_rest_time - h * 3600) / 60);
								if (h == 0 && m == 0) html = '剩余' + row.view_rest_time + '秒';
								else if (h == 0 && m != 0) html = '剩余' + m + '分钟';
								else html = '剩余' + h + '小时' + m + '分钟';
							}
						}
						
						return html;
					}
					else return '';
					}
				},
				{ field: 'action', title: '操作', width: 80, align: 'center',
					formatter: function(value, row, rowIndex) {
						if (row.request_status == 0) {
							return '待审批';
						}
						else if (row.request_status == 1) {
							return '审批未通过';
						}
						else {
							var html = "<a href='#' onclick='Archive.openArchive(" + row.id  + ");'>查看</a>";
							var now = Utils.getNowLongDate();
							var start = Utils.toLongDate(row.view_start_date);
							var end = Utils.toLongDate(row.view_end_date);
								
							if (now < start) return '未到查阅时间';
							
							if (row.view_time_type == 0) {
								if (row.view_rest_time <= 0) return '已过期';
								else if (now > end) return '已过期';
								else return html;
							}
							else {
								if (now > end) return '已过期';
								else return html;
							}
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
			$.messager.alert('错误', '请选择至少一位干部！', "error");
			return;
		}
		
		var personcodes = '';
		var personnames = '';
		$(rows).each(function(index, row){
			if (personcodes == '') personcodes = row.personcode;
			else personcodes = personcodes + "," + row.personcode;
			
			if (personnames == '') personnames = row.xingm;
			else personnames = personnames + "," + row.xingm;
		});
		
		// 先创建请求
		var url = System.rootPath + '/archive/bits-da-archive!createRequest.action';
		$.post(url, function(req) {
			try {
				if (req && req.user_name && req.request_key) {
					Archive.checkRequstForBatchSubmit(personcodes, personnames, req);
				}
				else {
					$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
				}
			}
			catch (e) {
				$.messager.alert('错误', '无法创建档案查阅系统访问请求！错误：' + e, "error");
			}
		});
	},
	// 显示提交查阅申请对话框
	showSubmitApprovelDialog: function(personcodes, personnames, mask, datagrid) {
		if ($("#archiveSubmitDialog").length <= 0) {
			$(document.body).append('<div id="archiveSubmitDialog" style="display:none;"></div>');
		}
		
		$("#archiveSubmitDialog").show();
		$("#archiveSubmitDialog").dialog({
			href: System.rootPath + '/archive/bits-da-archive!userRequest.action',
			width: 640,
			height: 600,
			title: '提交查阅申请',
			modal: true,
			onOpen: function() {
				window.setTimeout(function() {
					$("#archiveCheckAll").attr('checked', false);
					$(".archiveCheckBox").attr('checked', false);
					$("#archiveCysx").val('30天0小时0分钟');
					$("#archiveLookReason").val('工作查考');
					$("#archiveCysxSpan").hide();
					$("#archiveRsnames").html(personnames);
				}, 500);
				
				try {
					if (g_archiveUser != null && g_archiveUser.user_role == 4) {
						window.setTimeout(function() {
							var url = System.archiveWeb + '/userrequest/requestor!getLeadersByApi.action';
							$.ajax({
								url: url,
								dataType: 'jsonp',
								jsonp: "callback",
								jsonpCallback: "callback",
								method: 'post',
								success: function(data){
									$("#archiveLeaderRow").show();
									$("#archiveLeader").combobox({
										data: data,
										valueField: 'id',
										textField: 'user_name',
										editable: false
									});
								},
								error : function(XMLHttpRequest, textStatus, errorThrown){
									$.messager.alert("错误", textStatus + errorThrown, "error");
								}
							});
						}, 1000);
					}
				}
				catch (e) {
					alert(e);
				}
			},
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var memo = getCheckedArchiveFL();
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
					
					var req = {};
					req["req.cyly"] = lookReason;
					req["req.cynr"] = memo;
					req["req.cysx"] = cysx;
					req["personIds"] = personcodes;
					
					if (g_archiveUser != null && g_archiveUser.user_role == 4) {
						req["req.leader_id"] = $("#archiveLeader").combobox('getValue');
					}
					
					$.ajax({
						type: "POST",
						url: System.archiveWeb + '/userrequest/requestor!submitRequestByApi.action',
						data: req,
						dataType: 'jsonp',
						jsonp: "callback",
						jsonpCallback: "callback",
						success:function(msg){
							System.closeLoadMask("#" + mask);
							
							try {
								if (msg.code == 'success') {							
									$.messager.alert('提示', '成功提交查阅申请!', "info");
									try {
										if (datagrid != null) {
											$('#' + datagrid).datagrid('reload');
										}
									}
									catch (e) {
									}
									
									$('#archiveSubmitDialog').dialog('close');	
								}
								else {
									$.messager.alert('错误', msg.message, "error");						
								}
							}
							catch (ex) {
								$.messager.alert('提示', '提交失败，错误信息：' + ex, "error");						
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							System.closeLoadMask("#" + mask);
							$.messager.alert('提示', "提交失败,请重试!"+textStatus+errorThrown, "error");
						}
					});							
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
						$.messager.alert('错误', "天数输入应为正整数！", "error");
						return '';
					}
			   }
			   if (hour != 0) {              
					if (!re.test(hour)) {
						$.messager.alert('错误', "小时数输入应为正整数！", "error");
						return '';
					}
					if (hour > 23) {
						 $.messager.alert('错误', "小时输入不正确，应该在0-23之间！", "error");
						 return '';
					}
			   }
			   if (min != 0) {
					if (!re.test(min)) {
						 $.messager.alert('错误', "分钟数输入应为正整数！", "error");
						 return '';
					}
					if (min > 59) {
						 $.messager.alert('错误', "分钟数输入不正确，应该在0-59之间！", "error");
						 return '';
					}
			   }
		  }
		  else {
			   $.messager.alert('错误', "天、小时、分钟的输入不能全部为0！", "error");
			   return '';
		  }
	 }
	 else {
		  $.messager.alert('错误', "天、小时、分钟的输入不能为空!", "error");
		  return '';
	 }

	 return day + '天' + hour + '小时' + min + '分钟';
}
