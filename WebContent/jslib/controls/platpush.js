// 平台推送插件
var PlatPush = function(options) {
	var _self = this; // 保留自身，其他组件里面的事件，必须用_self代替this
	
	this.userTag = null;
	
	this.dialogId = 'pushInfoDlg';
	this.options = options;
	
	// 初始化
	PlatPush.prototype.init = function() {
		_self._init();
	};
	
	// 显示对话框
	PlatPush.prototype.show = function() {
		_self._show();
	};
	
	// 初始化
	this._init = function() {
		_self._initControl();
	};
	
	// 推送
	this._push = function() {
		var recipients = $("#pushMsgRecipients").tags('getValues');
		var portalIds = $("#pushPortal").combobox('getValue');
			
		var users = recipients.join();
		if (users == '' && portalIds == '') {
			System.showErrorMsg('请选择发送目标！');
			return;
		}
		
		if (!_self.options.url) {
			System.showErrorMsg('请设置回调URL！');
			return;
		}
		
		// 调用回调，获取推送数据
		$.post(_self.options.url, { 
			pushTitle: $("#pushTitle").textbox('getValue'), 
			pushContent: $("#pushContent").textbox('getValue'), 
			pushRecipients: users, 
			pushPortalIds: portalIds
		}, 
			function(pushData) {
				if (pushData != undefined && pushData.message_content_sub_type != undefined) {
					var postData = Utils.cloneObject(pushData, 'push');
					
					// 进行推送
					var url = System.rootPath + '/plat/push.action';
					MaskUtil.mask();
					System.post(url, postData, function(data) {
						System.showInfoMsg('信息已成功发送！');
						_self.dialog.dialog('close');
					});
				}
				else {
					System.showErrorMsg('无法获取发送记录，发送失败！');
				}
			});
	},
	
	// -------------- 用户相关
	this._onAddUser = function() {
		_self._showUserDialog();
	},
	
	this._addSelectedUsers = function() {
		var tree = $.fn.zTree.getZTreeObj('pushUserTree');
		var nodes = tree.getCheckedNodes();
		
		if (nodes != null && nodes.length > 0) {
			$(nodes).each(function(index, node) {
				if (node.attributes.nodeType == 'user') {
					_self.userTag.tags('addTag', node.text, node.id);
				}
			});
		}
		
		$("#pushUserTreeDlg").dialog('close');
	},
	
	this._onClearUser = function() {
		_self.userTag.tags('removeAllTags');
	},
	
	this._showUserDialog = function() {
		$("#pushUserTreeDlg").show();
		$("#pushUserTreeDlg").dialog({
			shadow:true,
			modal :true,
			resizable:true,
			width: 320,
			height: 380,
			title: '用户选择',
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					_self._addSelectedUsers();					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#pushUserTreeDlg').dialog('close');
				}
			}],
			onOpen : function () {
				var plat_setting = {
					check: {
						enable: true,
						nocheckInherit: false,
						chkboxType: { "Y" : "s", "N" : "s" } // 勾选不影响父节点，取消影响子节点
					},
					data: {
						key : {
							name: 'text'
						}
					},
					async: {
						enable: true,
						url: System.rootPath + '/security/access-right!getPlatUnitUserTree.action?lazyLoading=true',
						autoParam:["code=parentUnitCode"]
					}
				};
				
				$.fn.zTree.init($("#pushUserTree"), plat_setting);
			}
		});
	},
	
	// 弹出对话框
	this._show = function() {
		_self.dialog.dialog({  
			title: '发送',  
			width: 640,  
			height: 490,  
			content: _self.dialogContent,
			iconCls: 'icon-send',
			closed: true,
			modal: true,
			buttons:[{
				iconCls:'icon-send',
				text:'发送',
				handler:function(){
					_self._push();
				}
			},{
				iconCls:'icon-cancel',
				text:'取消',
				handler:function(){	
					_self.dialog.dialog('close');
				}
			}],
			onOpen: function() {
				if (_self.options.title) {
					$("#pushTitle").textbox('setValue', _self.options.title);
				}
				if (_self.options.content) {
					$("#pushContent").textbox('setValue', _self.options.content);
				}
				
				$("#pushBtnAddUser").linkbutton({
					onClick: _self._onAddUser
				});
				
				$("#pushBtnClearUser").linkbutton({
					onClick: _self._onClearUser
				});
				
				$("#pushPortal").combobox({
					url: System.rootPath + '/plat/portal-list.action',
					textField: 'portal_name',
					valueField: 'portal_id',
					editable: false,
					width: 200
				});
				
				_self.userTag = $("#pushMsgRecipients").tags();
			}
		});
		
		_self.dialog.dialog('open');
	};
	
	// 初始化其他
	this._initControl = function() {
		// 创建对话框内容
		var content = '';
		content += "<div class='easyui-layout' fit='true'>";
		content += "	<div region='center' border='false' style='overflow: hidden;'>";
		content += '		<table class="inputtable">';
		content += '			<tr>';
		content += '				<th width="100">标题</th>';
		content += '				<td><input class="easyui-textbox" id="pushTitle" style="width:450px;" required="true"/></td>';
		content += '			</tr>';
		content += '			<tr>';
		content += '				<th width="100">内容</th>';
		content += '				<td><input class="easyui-textbox" id="pushContent" style="width:450px;height:80px;" multiline="true"/></td>';
		content += '			</tr>';
		content += '			<tr>';
		content += '				<th rowspan="2">发送给用户</th>';
		content += '				<td><div id="pushMsgRecipients" class="tags" style="width:430px;height:80px;"></td>';
		content += '			</tr>';
		content += '			<tr><td>';
		content += '				<a href="javascript:void(0);" id="pushBtnAddUser" iconCls="icon-add">添加用户</a>';
		content += '				<a href="javascript:void(0);" id="pushBtnClearUser" iconCls="icon-remove">清空用户</a>';
		content += '				</td>';
		content += '			</tr>';
		content += '			<tr><th>推送到门户</th>';
		content += '				<td><select id="pushPortal"></select></td>';
		content += '			</tr>';
		content += '		</table>';
		content += '		<div class="alert-blue">温馨提示：<br/>1. 系统发送的数据为数据链接，一旦原始数据被删除，将无法访问；<br/>2. 推送到门户，需管理员审核后才能正式发布！</div>';
		content += '		<div id="pushUserTreeDlg" style="display:none;">';
		content += '			<ul id="pushUserTree" class="ztree"></ul>';
		content += '		</div>';
		content += '	</div>';
		content += '</div>';
		
		if ($('#' + _self.dialogId).length <= 0) {
			$("body").append("<div id='pushInfoDlg'></div>");	
		}
		
		_self.dialogContent = content;
		_self.dialog = $('#' + _self.dialogId);
	};
	
	// 执行初始化
	this.init();
};