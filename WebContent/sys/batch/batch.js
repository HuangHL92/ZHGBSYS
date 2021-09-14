/**
 * @author jinwei
 * @todo   ����ҵ�����js
 * @date 2008-1-14
 */

var batch = {
	evertime:100, //����ҵ����ѭ����ȡ����ʱ��ʱ����
	timer:{}, //�����ȴ��ڵĶ�ʱ��id����洢��ʽΪtimer={"timer_batchId":obj}
	/**
	 * ��ȡ���κ�
	 */
	getBatchId:function(){
		var req = odin.Ajax.request(contextPath+"/batch/batchBasicAction.do?method=getBatchId",{},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var batchId = odin.ext.decode(req.responseText).data;
		return batchId;
	},
	/**
	 * ��ʾ������
	 */
	showProgressDialog:function(){
		return odin.ext.MessageBox.show({
           title: '�����ĵȴ�',
           msg: '���ڴ�����...',
           progressText: 'Initializing...',
           width:300,
           progress:true,
           closable:false
       });
	},
	/**
	 * ���½���
	 * @param {Object} batchId ���κ�
	 * @param {Object} mbObj ���ڶ���
	 * @param {Object} funcname ��ȡ���ȵĺ������� �ú���д������ function getCueProgress(batchId)
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
			odin.alert("������ϣ�");
		}else{
			mbObj.updateProgress(v/100, Math.round(v)+'% ����ɣ�');
		}
	},    
	/**
	 * ����������
	 * @param {Object} time ����������ʱ����
	 * @param {Object} batchId ���κ�
	 * @param {Object} funcname ��ȡ���ȵĺ������� �ú���д������ function getCueProgress(batchId)
	 * @param {Object} mbObj ���ȴ��ڶ���
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
	 * ��ȡ��ǰ���ν�����Ϣ
	 * @param {Object} url ��ȡ������Ϣ��url��ַ
	 * @param {Object} batchId ���κ�
	 * @param {Object} progress Ŀǰ�Ľ���
	 * @param {Object} gridId ������ҳ��ʾ��ʱ���ݵ�grid����
	 * @param {Object} type  0��ʾ�ϴ����������浽��ʱ�� 1��ʾ����У�� 2��ʾ���������ҵ��
	 */
	getCueProgress:function(url,batchId,progress,gridId,type){
		var req = odin.Ajax.request(contextPath+url,{'batchId':batchId,'progress':progress},odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,false,false);
		var data = odin.ext.decode(req.responseText).data;
		ps = data.progress;               
		progress = parseFloat(ps,10);
		if(type=='0'&&progress==50){
			progressDlg.updateText("�ļ��ѳɹ��ϴ�����������");
		}else if(type=='0'&&progress>50&&progress<75){
			progressDlg.updateText("���ڽ����ļ���");
		}else if(type=='0'&&progress>75&&progress<100){
			progressDlg.updateText("���ڽ��������ݱ��浽��ʱ��");
		}else if(progress==100){
			//���ظ��ϴ������ݵ����
			odin.loadPageGridWithQueryParams(gridId,{'batchId':batchId});
		}  
		return progress;
	},
	doOpFBatchInfo:function(value, params, record, rowIndex, colIndex, ds){
		return "<a href='#' onclick='deleteAsk(\""+value+"\")'>ɾ��</a>";
	},
	doValidateStatus:function(value){
		if(value=="1"){
			return "<img src='"+contextPath+"/img/right.gif' title='�ñʷ���ҵ�����'/>";
		}else if(value=="0"){
			return "<img src='"+contextPath+"/img/wrong.gif' title='�ñ�ҵ��У�����'/>";
		}else{
			return "<img src='"+contextPath+"/img/wrong.gif' title='�����ҵ�����У�飬Ȼ�󱣴棡' />"
		}
	}
};
