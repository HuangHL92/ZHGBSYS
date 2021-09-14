var g_archiveNotiRequest = null;
function getArchiveMessageNumber(callback) {
	try {
		if (g_archiveNotiRequest == null) {
			var reqUrl = System.rootPath + '/archive/bits-da-archive!createNotificationRequest.action';
			$.get(reqUrl, function(msg) {
				g_archiveNotiRequest = msg;
				queryArchiveMessageNumber(callback);
			});
		}
		else {
			queryArchiveMessageNumber(callback);
		}
	}
	catch (e) {
		callback(0);
	}
}

function showArchiveMessage() {
	try {
		if (g_archiveNotiRequest == null) {
			var reqUrl = System.rootPath + '/archive/bits-da-archive!createNotificationRequest.action';
			$.get(reqUrl, function(msg) {
				g_archiveNotiRequest = msg;
				queryArchiveMessage();
			});
		}
		else {
			queryArchiveMessage();
		}
	}
	catch (e) {
		//alert(e);
	}
}

function toLongDate(str) {
	if (str) return str.substr(0, 16);
	else return str;
}

function queryArchiveMessage() {
	if (g_archiveNotiRequest == null || g_archiveNotiRequest.web == '') return;
	
	var url = g_archiveNotiRequest.web + '/userrequest/requestor!getUnreadApprovalRequestListByApi.action';
	$.ajax({
		url: url,
		data: { userName: g_archiveNotiRequest.userName, userKey: g_archiveNotiRequest.userKey },
		dataType: 'jsonp',
		jsonp: "callback",
		jsonpCallback: "callback",
		method: 'post',
		success: function(data){
			$.each(data, function(i, v) {
				var approved = false;
				var content = '你因' + v.cyly + '的需要，于' + toLongDate(v.requested_date) + '提出查看 ' + v.person_names + ' 的档案的申请，';
				if (v.leader_id == 0) {
					if (v.request_status == 2) {
						approved = true;
						content += '已通过管理员审批！查阅时间为：' + toLongDate(v.view_start_date) + "至" + toLongDate(v.view_end_date) + "。";
					}
					else {
						content += '未通过管理员审批！未通过原因：' + v.admin_approval_remarks;
					}
				}
				else {
					if (v.request_status == 1) {
						var approver = v.admin_approval_status != 0 ? '管理员' : '部门领导';
						var remarks =  v.admin_approval_status != 0 ? v.admin_approval_remarks : v.leader_approval_remarks;
						content += '未通过' + approver + '审批！未通过原因：' + remarks;
					}
					else {
						approved = true;
						content += '已通过管理员审批！查阅时间为：' + toLongDate(v.view_start_date) + "至" + toLongDate(v.view_end_date) + "。";
					}
				}
				
				content += '<br/><br/>';
				if (approved) {
					content += '<div style="float:left;"><a href="#" onclick="viewArchiveByRequest(' + v.id + ');">查阅档案</a>&nbsp;&nbsp;</div>';
				}
				content += '<div style="float:right;"><a href="#" onclick="readArchiveApprovalRequest(' + v.id + ');">不再提醒</a>&nbsp;&nbsp;</div>';
				content += '<br/>';
				
				var n = noty({
					layout:"bottomRight", 
					type:'alert',
					text: content,
					timeout:1000 * 10
				});
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			//$.messager.alert("错误", textStatus + errorThrown, "error");
		}
	});
}

function queryArchiveMessageNumber(callback) {
	if (g_archiveNotiRequest == null || g_archiveNotiRequest.web == '') return 0;
	
	var url = g_archiveNotiRequest.web + '/userrequest/requestor!getUnreadApprovalRequestListByApi.action';
	$.ajax({
		url: url,
		data: { userName: g_archiveNotiRequest.userName, userKey: g_archiveNotiRequest.userKey },
		dataType: 'jsonp',
		jsonp: "callback",
		jsonpCallback: "callback",
		method: 'post',
		success: function(data){
			callback(data.length);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			//$.messager.alert("错误", textStatus + errorThrown, "error");
			callback(0);
		}
	});
}

function viewArchiveByRequest(requestId) {
	var url = System.rootPath + '/archive/bits-da-archive!createRequest.action';
	$.post(url, function(req) {
		try {
			if (req && req.user_name && req.request_key) {
				checkRequstForView(req, requestId);
			}
			else {
				$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
			}
		}
		catch (e) {
			$.messager.alert('错误', '无法创建档案查阅系统访问请求！', "error");
		}
	});
}

function checkRequstForView(req, requestId) {
	var url = g_archiveNotiRequest.web + '/api/auto-login.action';
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
				//Archive.openArchiveForLeader(personcode, personname);
				System.showErrorMsg('你的档案用户权限允许你直接查阅档案，请通过选择人员进行查阅！');
			}
			// 其他人员，需要审批，那么显示请求列表
			else {
				window.setTimeout(function() {
					var url = g_archiveNotiRequest.web + '/archive/view-archive-by-request.action?id=' + requestId;
					window.open(url, '查阅档案' + requestId, 'fullscreen', "directories=0,fullscreen=1,toolbar=0,location=0,status=0,menubar=0,scrollbars=0,titlebar=0,resizable=1",true) ; 
					//window.open(url);
				}, 1000);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			//$.messager.alert("错误", textStatus + errorThrown, "error");
		}
	});
}

function readArchiveApprovalRequest(id) {
	if (g_archiveNotiRequest == null || g_archiveNotiRequest.web == '') return;
	
	var url = g_archiveNotiRequest.web + '/userrequest/requestor!readRequestByApi.action';
	$.ajax({
		url: url,
		data: { 
			id : id,  
			userName: g_archiveNotiRequest.userName, 
			userKey: g_archiveNotiRequest.userKey 
		}, 
		dataType: 'jsonp',
		jsonp: "callback",
		jsonpCallback: "callback",
		method: 'post',
		success: function(data){
			// do nothing
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			//$.messager.alert("错误", textStatus + errorThrown, "error");
		}
	});
}