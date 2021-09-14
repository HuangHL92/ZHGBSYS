/**
 * @author jinwei
 * @todo   批量业务核心js
 * @date 2008-1-14
 */

var batch = {
	evertime:100, //批量业务在循环获取进度时的时间间隔
	timer:{}, //各进度窗口的定时器id，其存储方式为timer={"timer_batchId":obj}
	/**
	 * 获取批次号
	 */
	getBatchId:function(){
		var req = odin.Ajax.request(contextPath+"/batch/batchBasicAction.do?method=getBatchId",{},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var batchId = odin.ext.decode(req.responseText).data;
		return batchId;
	},
	/**
	 * 显示进度条
	 */
	showProgressDialog:function(){
		return odin.ext.MessageBox.show({
           title: '请耐心等待',
           msg: '正在处理中...',
           progressText: 'Initializing...',
           width:300,
           progress:true,
           closable:false
       });
	},
	/**
	 * 更新进度
	 * @param {Object} batchId 批次号
	 * @param {Object} mbObj 窗口对象
	 * @param {Object} funcname 获取进度的函数名字 该函数写法如下 function getCueProgress(batchId)
	 */
	updateProgress:function(batchId,mbObj,funcname){
		var v = null;
		if(typeof funcname == 'function'){
			v = funcname(batchId);
		}else{
			v = eval(funcname+"('"+batchId+"')");
		}
		v = parseFloat(v,10);
		if(odin.round(v,0)==100){
			var t = eval("batch.timer.timer_"+batchId);
			clearInterval(t);
			t = null;
			mbObj.hide();
			odin.alert("处理完毕！");
		}else{
			mbObj.updateProgress(v/100, Math.round(v)+'% 已完成！');
		}
	},    
	/**
	 * 启动进度条
	 * @param {Object} time 进度条更新时间间隔
	 * @param {Object} batchId 批次号
	 * @param {Object} funcname 获取进度的函数名字 该函数写法如下 function getCueProgress(batchId)
	 * @param {Object} mbObj 进度窗口对象
	 */
	beginProgress:function(time,batchId,funcname,mbObj){
		var t = setInterval(batch.bindInterval(batch.updateProgress, batchId, mbObj,funcname),time);
		eval("batch.timer.timer_"+batchId +" = t; ");
	},
	bindInterval: function(funcName){
		var args = [];
		for (var i = 1; i < arguments.length; i++) {
			args.push(arguments[i]);
		}
		return function(){
			funcName.apply(this, args);
		}
	},
	/**
	 * 获取当前批次进度信息
	 * @param {Object} url 获取进度信息的url地址
	 * @param {Object} batchId 批次号
	 * @param {Object} progress 目前的进度
	 * @param {Object} gridId 用来分页显示临时数据的grid名称
	 * @param {Object} type  0表示上传解析并保存到临时表 1表示批量校验 2表示批量保存成业务
	 */
	getCueProgress:function(url,batchId,progress,gridId,type){
		var req = odin.Ajax.request(contextPath+url,{'batchId':batchId,'progress':progress},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var data = odin.ext.decode(req.responseText).data;
		ps = data.progress;               
		progress = parseFloat(ps,10);
		if(type=='0'&&progress==50){
			progressDlg.updateText("文件已成功上传到服务器！");
		}else if(type=='0'&&progress>50&&progress<75){
			progressDlg.updateText("正在解析文件！");
		}else if(type=='0'&&progress>75&&progress<100){
			progressDlg.updateText("正在将批量数据保存到临时表！");
		}else if(progress==100){
			//加载刚上传的数据到表格
			odin.loadPageGridWithQueryParams(gridId,{'batchId':batchId});
		}  
		return progress;
	},
	doOpFBatchInfo:function(value, params, record, rowIndex, colIndex, ds){
		return "<a href='#' onclick='deleteAsk(\""+value+"\")'>删除</a>";
	},
	doValidateStatus:function(value){
		if(value=="1"){
			return "<img src='"+contextPath+"/img/right.gif' title='该笔符合业务规则！'/>";
		}else if(value=="0"){
			return "<img src='"+contextPath+"/img/wrong.gif' title='该笔业务校验错误！'/>";
		}else{
			return "<img src='"+contextPath+"/img/wrong.gif' title='请进行业务规则校验，然后保存！' />"
		}
	}
};
