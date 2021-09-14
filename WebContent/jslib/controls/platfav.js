// 平台收藏插件
var PlatFav = function(options) {
	var _self = this; // 保留自身，其他组件里面的事件，必须用_self代替this
	this.options = options;
	
	// 初始化
	PlatFav.prototype.init = function() {
		
	};
	
	// 推送
	PlatFav.prototype.addFav = function() {
		var users = System.currentUserId;
			
		if (users == '') {
			System.showErrorMsg('当前用户已注销，请重新重新登录！');
			return;
		}
		
		if (!_self.options.url) {
			System.showErrorMsg('请设置回调URL！');
			return;
		}
		
		System.showConfirmMsg('确定添加到【我的收藏】吗？', function() {
			// 调用回调，获取推送数据
			MaskUtil.mask('正在添加到【我的收藏】...');
						
			$.post(_self.options.url, { 
				pushTitle: _self.options.title, 
				pushContent: '', 
				pushRecipients: users, 
				pushPortalIds: ''
			}, 
				function(pushData) {
					if (pushData != undefined && pushData.message_content_sub_type != undefined) {
						var postData = Utils.cloneObject(pushData, 'push');
						
						// 进行推送
						var url = System.rootPath + '/plat/add-fav.action';
						System.post(url, postData, function(data) {
							System.showInfoMsg('已成功添加到【我的收藏】！');
						});
					}
					else {
						MaskUtil.unmask();
						System.showErrorMsg('无法获取收藏记录，收藏失败！');
					}
				});
		});
	};
	
	
	// 执行初始化
	this.init();
};