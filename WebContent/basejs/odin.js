/***
 * **************************
 * ������¶���������޹�˾
 * ������ܱ�ǩ��ĺ���js�ļ�
 * version:2.0
 * version:2.0.1  �����˸��±��ĳ�����ݵ�ͨ�÷�����2010.3.9��
 * version:2.0.2  ������setGridData�͸���ĳ�����ݵķ�����ʹ��֧�ֻس����У�����������ָ����ַ�
 * version:2.0.3  �����˲�����־��������ʽ��������ʱ��������ִ��������������������ʽ��(2010.7.12)
 * version:2.0.4  �����˶��Զ������༭���ĳ��Ԫ��ı༭ģʽ��֧��(2010.7.15)
 * version:2.0.5  �޸���select��ǩ������Ϊ���ַ���ʱ��bug��2010.8.17��
 * version:2.0.6  �޸��˺ͱ�����ص���ͨ����ʹ�ӡʱģʽ�Ի���Ŀ�͸� ��2010.9.7��
 * version:2.0.7  �޸��˴�ϵͳ����tabҳ�ķ����������˵���ǿ��ʱ���Զ�ˢ��ҳ��Ĺ��ܣ�2010.11.24��
 * version:2.0.8  �޸���ĳЩ�����ݵ�jsд��(2011.1.7)
 * version:2.0.9  �޸���ͳһ���ύ֮ǰ��ȫ��У�麯����ʹ���ڷ�IE��Ҳ����������(2011.2.18)
 * version:2.1.0  �����˶�ĳ��tabҳ��ϵͳ�Ķ�tabҳ����ģ���ڵģ��Ƿ��Ǵ��ڼ���״̬���жϣ�2011.5.23��
 * version:2.1.1  �޸�������ѡ���ǩ��valueNotFoundText������Ե�֧�֣����������Ϊfalseʱ������ʾ������ҪôĬ����ʾ��Ҫô����valueNotFoundText���ֵ����ʾ��2011.5.26��
 * version:2.1.2  �޸��˶�onkeydown�Ĵ���ʹ�ķ����쳣ʱ������ִ�У�2011.6.1��
 * version:4.0.0  ������extjs3������һЩ������2011.8��
 * version:4.0.1  �޸�checkValue����������chrome8�Ժ����13�������2011.8.24��
 * version:4.0.2  �޸��˻�ȡԭʼ����ķ������򿪲�����־ʱ�ķ������ж�ĳ��tabҳ�Ƿ��Ǵ��ڼ���״̬�ķ������Լ���firefox��2011.9.13��
 * version:4.0.3  �޸������ڿؼ���ֻ����������Ӧ�µı�ǩ��(2011.9.19)
 * version:4.0.4  ���⴦���˷�IE�µĻس���TAB���ܣ���������Զ������¸�Ԫ���������򡢰�ť��fieldset����disable��(2011.9.21)
 * version:4.0.5  �������Զ���ת�¸�Ԫ�صķ�����ֻ���ȫҳ��һ������ʽ������������������ѡ�к�س����ö���λ���¸�Ԫ����(2011.9.22)
 * version:4.0.6  �޸��˴򿪱����ڵķ�ʽ����ʹ��IE���е�ģʽ���ڷ�ʽ(2011.9.26)
 * version:4.0.7  �����˶Դ����ӳټ��ص�֧�֣�2012.5.3��
 * version:4.0.8  ������setGridData������ʹ����ûֵʱ��removeAll��add(2012.5.7)
 * version:5.0.0  �����˸�����������صĻ�ȡ�����߶Ⱥ������޸�������firefox��chrome����ʾ�쳣�Ͳ���֮���������⣨2012.7.3��
 * version:5.0.1  �����˱������������
 * version:5.0.2  �޸��˱��Ԫ���ܸ�������
 * version:6.0.0  ����cm�����ȶ�
 * version:6.0.1  �޸��ڷ�IE��ȡ������־ʱץȡ���Ľ���html������inputû��valueֵ���⣬�Լ��޸�combo�����tab���л�ʱ�䳤 jinwei 2013.7.15
 * version:6.0.2  �޸���ҳ������PageToolbar��ʾ�쳣��������setListWidth���� -- jinwei 2013.7.29
 * version:6.0.3  ����F8������˵������ڳ��� -- ljd 2013.8.16
 * version:6.0.4  �޸�info��question����ʾ��Ϣ���ں�alert��errorЧ��һ�£�����������ô�ѿ� -- jinwei 2013.8.20
 * version:6.0.5  �޸�����������һ�������ڵ�ֵ����ʾ����Ȼ����ѡһ����ȷֵ���������� -- jinwei 2013.8.26
 * version:6.0.6  �޸��ϴθ���û�п��ǵ���cm��ʽҳ�������򣬵������������������bug -- jinwei 2013.8.28
 * version:6.0.7  �޸���ҳ�������޷���ȷ����ֵ���� -- jinwei 2013.8.29
 * version:6.0.8  �޸�ʹ�����ڷ�ʽ�����һ�濪��ʱ��������ֵ����JS���� -- jinwei 2013.9.3
 * version:6.0.9  ����select��ʧȥ�����¼����valid���� -- jinwei 2013.9.4
 * version:6.1.0  ���ݸ�ʽ���ַ�����������������ƣ���������������ƥ�䷽ʽ����Ϊģ��ƥ�� -- jinwei 2013.9.9
 * version:6.1.1  �޸�tab��ı����ҳ�淢�����ź����޷��Զ����� -- jinwei 2013.9.12
 * version:6.1.2  ����loadPageInTab������ʹ�����֧�ָ��˹���̨�µĴ򿪹���ҳ�� -- jinwei 2013.9.17
 * version:6.1.3  �޸�autoNextElement���������ý���ʱû������ͨ��setOpItem���ò��ɼ���Ԫ�� -- jinwei 2013.9.22
 * version:6.1.4  ���ӱ����ĳ�а��س����Զ�����һ������һ�п��й��� -- jinwei 2013.10.10
 * version:6.1.5  Э���°���˹���̨�����س����Զ�������û�з���Ȩ�ޡ���ҳ������ -- jinwei 2013.10.14
 * version:6.1.6  �޸��ڸ��˹���̨�����һ��Ԫ�ػس����ᴥ��onchange���� -- jinwei 2013.10.28
 * version:6.1.7  ����񰴻س��Զ����ӿ��иĳ��Զ���������¼�����ǰֵΪgridEnterAddRow��-- jinwei 2013.10.30
 * version:6.1.8  �޸��ӳټ�������ѡ��ʱ����ȫѡ�ᵼ��JS������޷���ʾ����ѡ�� -- jinwei 2013.11.1
 * version:6.1.9  ��С���̻س��������⴦�� -- jinwei 2013.11.6
 * version:6.2.0  �޸�tab�´�ͼ����������������ɫͼ���trrigerͼ���ص� -- jinwei 2013.11.22
 * version:6.2.1  �����backspace���ᵼ�º��˵���¼ҳ������ -- jinwei 2013.11.28
 * version:6.2.2  ���������ѡ�����գ�ȡ������Ȼ��ԭ����ֵ������ -- ljd 2013.12.3
 * version:6.2.3  ����getSysdate���� -- ljd 2013.12.13
 * version:6.2.4  �޸��������������ͼ��������κ�ѡ��ʱ����ִ�����̨��������ֵΪδ���� -- jinwei 2014.1.3
 * version:6.2.5  �޸�request����ʹgrid�༭���¼�ֻ����grid�������afteredit��query����ͬʱ���������� -- ljd 2014.2.20
 * version:6.2.6  ��grid�в�ѯ�ٶ����Ż� -- ljd 2014.3.14
 * version:6.2.7  �޸�tab�±��ؼ���width����ʱ���½�����ʾ�쳣bug -- jinwei 2014.3.24
 * version:6.2.8  �޸�Chrome�£��򿪴�����Ҫ��2�ε�BUG -- ljd 2014.4.15
 * version:6.2.9  �޸����⸴��ҳ�����в�����־ʱ�ᵼ����������ڼ���״̬ -- jinwei 2014.7.1
 * version:6.3.0  �޸���ѡ�������ǿ�У��BUG -- ljd 2014.8.7
 * version:6.3.1  �ޏ�readonly�ǰ�Backspace���ص�������Ć��}-- ljd 2014.10.8
 * version:6.3.1  �޸�doAccOnEditorKey  �����Ƿ񰴻س��Զ�������һ�����-- ljd 2014.10.17
 * version:6.3.1  �޸�error  ���������Ƴ�����-- ljd 2014.10.21
 * version:6.3.2  ��ʱ������˹���̨��������С���̻س��޷�ѡ������ -- jinwei 2014.10.31
 * version:6.3.3  �޸�������ֻ����ʱ�س������������� -- jinwei 2014.11.3
 * version:6.3.4  �޸�checkValue���Ի�ȡ������ͽ����쳣��׽����ֹ���ÿ�ܿ���ʱ���ֵ��쳣 -- ljd 2014.11.11
 * version:6.3.5  �޸�onkeydown���� ���޸���ѡ���޷����س�����onchange -- ljd 2014.12.24
 * version:6.3.6  �޸��Զ���ת����һԪ��bug -- jinwei 2015.4.7
 * version:6.3.7  ���Ӹ��ݱ��ID����dataIndex�������е�keyֵ��ȡValueֵ -- jinwei 2015.6.8
 * version:6.3.7  ���ύǰУ�鵯����ʽΪalert ��ʾ����ĵ������� -- zoulei 2016.5.31
 * version:6.3.8  odin.openWindow��Ӳ���thisWin ����ҳ��ĵ�ǰ���ڶ���,isTopParentWin �Ƿ������ҳ����Ϊ��ҳ��,modal ��ҳ���Ƿ����֣��� -- zoulei 2016.12.22
 * **************************
 */
var odin = {
	version:'1.0', /**ϵͳ�汾��**/
    defaultTitle:'ϵͳ��ʾ',
    msg : '���ڴ�����...',
    msgCls : 'x-mask-loading',  
	ajaxaSynchronous:false,/*****ajaxĬ�ϵ�ͬ���첽��־��falseΪͬ����trueΪ�첽*******/
	isWorkpf : (navigator.userAgent.indexOf("Workpf") != -1), // �Ƿ�Ϊ���˹���̨
	workpfVer : (navigator.userAgent.indexOf("Workpf") != -1 ? navigator.userAgent.substring(navigator.userAgent.indexOf("Workpf") + 7) : 0),// ���˹���̨�汾��
    confirm:function(info,fun,title){
    	var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	     Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: false,
           fn: fun,
           icon : Ext.MessageBox.QUESTION
       });
    },
    alert:function(info,fun,title,width){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           width:width,
           fn: fun
       });
    },
    prompt:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
        Ext.MessageBox.prompt(boxtitle,info,fun);
    },
    promptWithMul:function(info,fun,title){
    	var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:300,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: true,
           fn: fun
       });
    },
    error:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
		info = "<font color=red>" + info + "</font>";
		try{
			if(typeof loading != 'undefined'){
				loading.remove();
			}
		}catch(exception){
			
		}
		Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.ERROR
       });
    },
	question:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OKCANCEL,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.QUESTION
       });
    },
	warning:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.WARNING
       });
    },
    info:function(info,fun,title){
        var boxtitle = odin.defaultTitle;
    	if(title){
    	   boxtitle = title;
    	}
    	info = odin.toHtmlString(info);
	    Ext.MessageBox.show({
           title: boxtitle,
           msg: info,
           minWidth:200,
           buttons: Ext.MessageBox.OK,
           multiline: false,
           fn: fun,
           icon:Ext.MessageBox.INFO
       });
    },
    progress : function(progressPecent, progressText, info, title) {
		if (odin.msgShow == false || odin.msgShow_progress == false) {
			return;
		}
		var boxtitle = "�����ĵȴ�...";
		if (title) {
			boxtitle = title;
		}
		info = odin.toHtmlString(info);
		Ext.MessageBox.show({
					title : boxtitle,
					msg : info,
					width : 320,
					progress : true,
					closable : false
				});
		Ext.MessageBox.updateProgress(progressPecent, progressText);
	},
    
    onFilterCode:function(){ 
		var obj = event.srcElement; 
		if(obj.value=="")
		{
		    Ext.get(obj).prev().value = '';
		}else{
		    var reg = /^\d+/;
		    if(obj.value.match(reg))
		    {
		        if(obj.value.replace(/\d+/,'').length>4)
		        {
		            Ext.get(obj).prev().value = obj.value.match(reg);
		            obj.value = obj.value.replace(/\d+/,'').substring(4);
		        }else{
		           obj.value = '';
		           Ext.get(obj).prev().value = '';
		        }
		    }else{
		        if(Ext.get(obj).prev().value == '')
		        {
		            obj.value = '';
		        }else{
		            //
		        }
		    }
		}
    },
    changeCodeText:function(field){
        Ext.get(field.getId()).next().value = Ext.get(field.getId()).next().value.replace(/\d+/,'').substring(4);
        if(Ext.get(field.getId()).next().value=="")
        {
            field.setValue('');
        }
    },
    setSelectValue:function(id,newvalue){
    	if(typeof radow_select_info != 'undefined'){
    		if(typeof radow_select_default == 'undefined'){
    			radow_select_default = [];
    		}
    		radow_select_default.push({'id':id,'newvalue':newvalue});
    		return;
    	}
    	odin.setSelectValueReal(id,newvalue);
    },
    setSelectValueReal:function(id,newvalue){
    	var value = document.getElementById(id).value;
		if(newvalue==null) newvalue = "";
		if(typeof newvalue == 'undefined') newvalue = "";
        value = newvalue;
        document.getElementById(id).value = value;
		//alert("value:"+value);
        var combo = odin.ext.getCmp(id+"_combo");
        if(!combo){
        	return;
        }
		if(combo.getXType()=='lovcombo'){
			combo.setValue(value,'key');
			return;
		}
        if(value!=null&&value!=""){ 
	        var store = Ext.getCmp(id+"_combo").store;
	        var length = store.getCount();
	        if(combo.mode=='remote' && store.url!=""){
	        	var param = store.baseParams;
				param.query = value;
				var req = odin.Ajax.request(store.url, param, odin.ajaxSuccessFunc, odin.ajaxSuccessFunc, false, false);
				var data = Ext.decode(req.responseText).data;
				if (data != null && data.length == 1) {
					document.getElementById(id).value = data[0].key;
					Ext.getCmp(id + "_combo").setValue(data[0].value);
				} else {
					document.getElementById(id).value = value;
					Ext.getCmp(id + "_combo").setValue(value);
				}
	        }else{
		        var isExsist = false;
		        for(i=0;i<length;i++)
		        {
		            var rs = store.getAt(i);
		            if(rs.get('key')==value)
		            {
		                var newValue = rs.get('value');
		                if(newValue.indexOf('&nbsp;')>0){
			                newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
		                }
		                Ext.getCmp(id+"_combo").setValue(newValue);
		                isExsist = true
		                break;
		            }
		        }
		        if(!isExsist){
		            Ext.getCmp(id+"_combo").setValue(value);
		        }
	        }
        }else{
			Ext.getCmp(id+"_combo").setValue('');
			document.getElementById(id).value = "";
		}
    },
    doAccForSelect:function(field){
        var comboId = field.getId();
        var id = comboId.substring(0,comboId.lastIndexOf("_combo"));
        var store = Ext.getCmp(comboId).store;
        var canOutSelectList = Ext.getCmp(comboId).canOutSelectList;
        var length = store.getCount();
        var isExsist = false;
		for(i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('value')==field.getValue()){
                isExsist = true;
                Ext.get(id).value = rs.get('key');
                var newValue = field.getValue();
                if(newValue.indexOf('&nbsp;')>0){
	                newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
	                Ext.getCmp(comboId).setValue(newValue);
                }
                break;
            }
            if(rs.get('key')==field.getValue()){
            	document.getElementById(comboId).value = rs.get("value");
                isExsist = true;
                break;
            }
        }
		//alert("isExsist:"+isExsist);
		if(field.getValue()==''){
			isExsist=true;
			Ext.get(id).value="";
		}
        if(!isExsist){
        	if(field.getValue()!=""){
				var msg = "�������ֵû�ж�Ӧ��ƥ�������Ϣ�����������롣";
				if(field.valueNotFoundText && field.valueNotFoundText!="false"){
					msg = field.valueNotFoundText;
				}else if(field.valueNotFoundText && field.valueNotFoundText=="false"){
					msg = "";
				}else if(field.canOutSelectList=="true"){
					msg = "";
				}
        		odin.cueCheckObj = document.getElementById(comboId);
				if (msg != "") {
					if(typeof radow!='undefined' && radow && radow.cm){
						radow.cm.setValid(id,false,msg);
					}else{
						odin.alert(msg, odin.doFocus);
					}
				}
        	}
            Ext.get(id).value = field.getValue();
        }else{
        	if(typeof radow!='undefined' && radow && radow.cm){
        		if(true != field.noClearValid){
        			radow.cm.setValid(id,true,'');
        		}
        	}
        }
        if(typeof Ext.get(id).value == 'undefined'){
        	Ext.get(id).value = "";
        }
        document.getElementById(id).value = Ext.get(id).value;
        //alert(Ext.get(id).value);
        if(odin.isWorkpf){
        	odin.autoNextElement(document.getElementById(comboId));
        }
    },
    setHiddenTextValue:function(combo,record,index ){
        var comboId = combo.getId();
        var value = record.get('key');
        var id = comboId.substring(0,comboId.indexOf("_combo"));
        Ext.get(id).value = value;
        var newValue = record.get('value');
        if(newValue.indexOf('&nbsp;')>0){
	        newValue = newValue.replace(/\d+/,'').replace(/&nbsp;/g,'');
        } 
        combo.setValue(newValue);
        document.getElementById(id).value = Ext.get(id).value;
        //alert(id+"||"+Ext.get(id).value);
    },
    fillLeft:function(oldstr,ch,len){
    	var newstr=oldstr;
    	for(var i=oldstr.length;i<len;i++){
    		newstr=ch+newstr;
    	}
    	return newstr;
    },
    fillRight:function(oldstr,ch,len){
    	var newstr=oldstr;
    	for(var i=oldstr.length;i<len;i++){
    		newstr=newstr+ch;
    	}
    	return newstr;
    },
    Ajax:{
		/**
		 * ajax
		 * @param {Object} reqUrl
		 * @param {Object} reqParams
		 * @param {Object} successFun
		 * @param {Object} failureFun
		 * @param {Object} asynchronous ͬ���첽��־��trueΪ�첽��falseΪͬ��
		 * @param {Object} isMask �Ƿ�Ҫ��mask���ִ���
		 */
    	request:function(reqUrl,reqParams,successFun,failureFun,asynchronous,isMask){
    		Ext.Ajax.timeout=30000000;
    		var el = null;             
            if(reqParams != null && reqParams.tmpEl != null){
                el = reqParams.tmpEl;
                delete  reqParams.tmpEl;
            }else{
                el = Ext.get(document.body);
            }
             
			if (isMask!=null&&isMask==false) { 
				//����������Ϊfalse
				//alert('����������Ϊfalse');
			}else{
				el.mask(odin.msg, odin.msgCls);
			}
			if (asynchronous == null) {
				asynchronous = odin.ajaxaSynchronous;
			}
			if (asynchronous!=false) {
				asynchronous = true;
			}
			return	Ext.Ajax.request({
					url: reqUrl,
					success: doSuccess,
					failure: doFailure,
					params: reqParams,
					asynchronous: asynchronous
				});
			function doSuccess(request){
				el.unmask();
				var response=null;
				try{
					response=eval('('+request.responseText+')');
				}catch(err){
					//debugger;
					if(request.responseText.indexOf("�е�¼��ʱ�������µ�¼")>0){
						alert("û�е�¼��ʱ�������µ�¼��");
					}else{
						alert("û�е�¼��ʱ�������µ�¼��");
						//alert("Ajax���󷵻ص����ݷǷ���");
					}
					top.parent.location.href = 'logonAction.do';
					throw err;
				}
				if(response.messageCode=="0"){//����ɹ�
					if(successFun){//�гɹ��ص�����
						successFun(response);
					}else{
						alert(response.mainMessage);
					}
				}else{//����ʧ��
					if(failureFun){//��ʧ�ܻص�����
						failureFun(response);
					}else{
						var errmsg=response.mainMessage;
						if(response.detailMessage!=""){
							errmsg=errmsg+"\n��ϸ��Ϣ:"+response.detailMessage;
						}
						alert(errmsg);
					}
				}
			}
			
			function doFailure(request){
				//console.log(request.responseText);
				el.unmask();
				//alert("Ajax����ʧ�ܣ�����ϵͳ����Ա��ϵ��");
				if(request.responseText.indexOf("�е�¼��ʱ�������µ�¼")>0){
					alert("û�е�¼��ʱ�������µ�¼��");
				}else{
					alert("û�е�¼��ʱ�������µ�¼��");
					//alert("Ajax���󷵻ص����ݷǷ���");
				}
				top.parent.location.href = 'logonAction.do';
			}	
	    },
	    formatDate:function(jsonDate){
			if(!jsonDate) return null;

	    	var year=String(jsonDate.year+1900);
	    	var month=String(jsonDate.month+1);
	    	var date=String(jsonDate.date);
	    	month=odin.fillLeft(month,"0",2);
	    	date=odin.fillLeft(date,"0",2);
	    	var theDate=year+"-"+month+"-"+date;
	    	return theDate;
	    },
	    formatDateTime:function(jsonDate){
			if(!jsonDate) return null;

	    	var year=String(jsonDate.year+1900);
	    	var month=String(jsonDate.month+1);
	    	var date=String(jsonDate.date);
	    	var hour=String(jsonDate.hours);
	    	var minute=String(jsonDate.minutes);
	    	var second=String(jsonDate.seconds);
	    	month=odin.fillLeft(month,"0",2);
	    	date=odin.fillLeft(date,"0",2);
	    	hour=odin.fillLeft(hour,"0",2);
	    	minute=odin.fillLeft(minute,"0",2);
	    	second=odin.fillLeft(second,"0",2);
	    	var theDateTime=year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	    	return theDateTime;
	    }
    },
    enterToTab:function(){
    	//alert("ok");
    	var e=window.event.srcElement;
    	var type=e.type;
    	if(type!='button'&&type!='textarea'&&event.keyCode==13){
    		event.keyCode=9;
    	}
    },
    doOpLog:function(theForm){
    	//������־����
    	var prsource;
    	try{
    		prsource=eval(MDParam.prsource);
    	}catch(e){
			return null;
		}
		if(prsource){
	    	/*var digest=new Array(10);
	    	for(var i=0;i<10;i++){
	    		digest[i]="";
	    	}
	    	//��ȡ������־ժҪ��Ϣ
	    	var len=Math.min(prsource.length,10);
	    	for(var i=0;i<len;i++){
	    		var prop=prsource[i].property;
	    		if(prop=="") continue;
	    		
	    		var el=document.getElementById(prop);
	    		if(el){
	    			digest[i]=el.value;
	    		}
	    	}
	    	*/
	    	var len=prsource.length;
	    	var items=new Array(len);
	    	for(var i=0;i<len;i++){
	    		items[i]="";
	    	}
	    	for(var i=0;i<len;i++){
	    		var prop=prsource[i].property;
	    		if(prop=="") continue;
	    		
	    		var type=prsource[i].type;
	    		if(type&&type=="select"){
	    			prop=prop+"_combo";
	    		}
	    		var el=document.getElementById(prop);
	    		if(el){
	    			if(type&&type.toLowerCase()=="text"){
	    				items[i]=el.innerHTML;
	    			}else{
	    				items[i]=el.value;
	    			}
	    		}
	    	}
	    	var digest="";
	    	for(var i=2;i<len;i++){
	    		if(items[i]=="") continue;
	    		
	    		digest=digest+","+prsource[i].label+":"+items[i];
	    	}
	    	if(digest!=""){
	    		digest=digest.substring(1);
	    	}
	    	//��ȡԭʼ������Ϣ
	    	//var oriSource="<html>"+document.documentElement.innerHTML+"</html>";
	    	var oriSource="";
	    	if(!Ext.isIE){
				var inputs = document.getElementsByTagName("input");
				for(var i=0;i<inputs.length;i++){
					inputs[i].setAttribute("value",inputs[i].value);
				}
			}
			if(document.documentElement.outerHTML){
				oriSource = document.documentElement.outerHTML;
			}else{ //firefox 2011.9.13
				oriSource = document.documentElement.innerHTML
			}
	    	//alert(oriSource);
	    	//ȥ��javascript����
	    	var oriSource=oriSource.replace(/<script([^>]*?>([^<]*(<[^\/]*)*))<\/script>/gi,"");
	    	
	    	/*var userlog={functionid:MDParam.functionid,
	    				 aac001:digest[0],
	    				 aab001:digest[1],
	    				 prcol1:digest[2],
	    				 prcol2:digest[3],
	    				 prcol3:digest[4],
	    				 prcol4:digest[5],
	    				 prcol5:digest[6],
	    				 prcol6:digest[7],
	    				 prcol7:digest[8],
	    				 prcol8:digest[9],
	    				 orisource:oriSource
	    				};*/
	    	var userlog={functionid:MDParam.functionid,
	    				 aac001:items[0],
	    				 aab001:items[1],
	    				 digest:digest,
	    				 prcol1:"",
	    				 prcol2:"",
	    				 prcol3:"",
	    				 prcol4:"",
	    				 prcol5:"",
	    				 prcol6:"",
	    				 prcol7:"",
	    				 prcol8:"",
	    				 orisource:oriSource
	    				};
	    	//var srtUserlog=JSON.stringify(userlog);
	    	var srtUserlog=Ext.util.JSON.encode(userlog);
	    	//alert(srtUserlog);
	    	return srtUserlog;
	    }else{
	    	return null;
	    }
    },
    /*submit:function(theForm){//����ʽ�ύ����
    	//������־����
    	var srtUserlog=this.doOpLog(theForm);
    	//alert(srtUserlog);
	    if(srtUserlog){
	    	//����в��������־�����ֶ�    	
	    	var userlogNode=document.getElementById("userlog");
	    	if(!userlogNode){
	    		userlogNode=document.createElement("input");
	    		userlogNode.setAttribute("name","userlog");
	    		userlogNode.setAttribute("type","hidden");	    		
	    		theForm.appendChild(userlogNode);
	    	}
	    	userlogNode.setAttribute("value",String(srtUserlog));	
	    }	
	    
	    theForm.submit();		 
    },*/
    submit:function(theForm,successFun,failureFun){//Ajax��ʽ�ύ����
    	var params={};
    	//�������е�����ת���ɲ�������
    	var elList=theForm.getElementsByTagName("input");
		//var areaList = theForm.getElementsByTagName("textarea");
		//if (areaList.length > 0) {
		//	elList = elList.concat(areaList);
		//}
    	for(var i=0;i<elList.length;i++){ 
    		if(elList.item(i).name&&elList.item(i).name.indexOf("-")==-1){
	    		var exp="params."+elList.item(i).name+"="+Ext.util.JSON.encode(elList.item(i).value);
	    		//alert(exp);
	    		eval(exp);
    		}
    	}

		elList=theForm.getElementsByTagName("textarea");
    	for(var i=0;i<elList.length;i++){ 
    		if(elList.item(i).name&&elList.item(i).name.indexOf("-")==-1){
	    		var exp="params."+elList.item(i).name+"="+Ext.util.JSON.encode(elList.item(i).value);
	    		//alert(exp);
	    		eval(exp);
    		}
    	} 

    	//������־����
    	var srtUserlog=this.doOpLog(theForm);
    	if(srtUserlog){
    		params.userlog=String(srtUserlog);
    	}
    	//����ģ��functionid 2009-05-20 zhangy
    	if(MDParam){
    		params._modulesysfunctionid=MDParam.functionid;
    	}
    	if (odin.checkValue(theForm) == true) { //��ͨһУ���ж�
			this.Ajax.request(theForm.action, params, successFun, failureFun,false,true); //ͬ�� ����
		}
    },
    formClear:function(theForm){
    	var elList=theForm.getElementsByTagName("input");
    	for(var i=0;i<elList.length;i++){
    		//elList.item(i).setAttribute("value","");
    		elList.item(i).value = "";
    	}
    },
    reset:function(){
    	//location.reload();
    	location.href=contextPath+MDParam.location;
    },
    loadPageGridWithQueryParams:function(gridId,params){
        var store = Ext.getCmp(gridId).getStore(); 
        if(Ext.getCmp(gridId).getTopToolbar() && Ext.getCmp(gridId).getTopToolbar().pageSize){
        	params.limit = Ext.getCmp(gridId).getTopToolbar().pageSize;
        }else if(Ext.getCmp(gridId).getBottomToolbar() && Ext.getCmp(gridId).getBottomToolbar().pageSize){
            params.limit = Ext.getCmp(gridId).getBottomToolbar().pageSize;
        }else{
            params.limit = sysDefaultPageSize;
        }  
        store.on('beforeload', function(ds) {
        	var limit = ds.baseParams.limit;
        	var lastParams = ds.lastOptions.params;
        	odin.ext.apply(ds.baseParams,params);
        	if(typeof lastParams=='undefined' || lastParams.start==0){
		    	//ds.baseParams = params;
        	}else{
        		if(typeof radow!='undefined' && radow && radow.cm){
	        		delete ds.baseParams.rc;
	        		delete ds.baseParams.radow_parent_data;
        		}
        	}
        	if(typeof limit!='undefined'){
        		ds.baseParams.limit = limit;
        	}
        	ds.baseParams.cueGridId = gridId;

        });     
        store.load();
    },
    loadGridData:function(gridId,params,beforeloadFun){
        var store = Ext.getCmp(gridId).getStore(); 
        store.on('beforeload', function(ds) {
            if(beforeloadFun){beforeloadFun(ds)};
		    var limit = ds.baseParams.limit;
        	var lastParams = ds.lastOptions.params;
        	odin.ext.apply(ds.baseParams,params);
        	if(typeof lastParams=='undefined' || lastParams.start==0){
		    	//ds.baseParams = params;
        	}else{
        		if(typeof radow!='undefined' && radow && radow.cm){
	        		delete ds.baseParams.rc;
	        		delete ds.baseParams.radow_parent_data;
        		}
        	}
        	if(typeof limit!='undefined'){
        		ds.baseParams.limit = limit;
        	}
        	ds.baseParams.cueGridId = gridId;

        });     
        store.load();
    },
    showWindowWithSrc:function(windowId,newSrc){
		if (document.getElementById("iframe_" + windowId) != null) {
			document.getElementById("iframe_" + windowId).src = newSrc;
			window.setTimeout('Ext.getCmp(\"'+windowId+'\").show(Ext.getCmp(\"'+windowId+'\"))',200);
		}
		else {		
			Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\"></iframe>";
			window.setTimeout('Ext.getCmp(\"'+windowId+'\").show(Ext.getCmp(\"'+windowId+'\"))',200);
		}     
    },
    changeToUrl:function(value, params, record,rowIndex,colIndex,ds){
        return "<a href='www.baidu.com?test="+record.data.price+"&test2="+record.data.change+"'>"+value+"</a>";
    },
    gridCellClick:function(grid,rowIndex,colIndex,event){
        //alert(colIndex);
        if(colIndex%2==0){
            grid.getColumnModel().setEditable(colIndex,false);
        }else if(colIndex!=3){
            grid.getColumnModel().setEditable(colIndex,true);
        }
    },
    afterEditForEditGrid:function(e){
        var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
        var sel_data;
		if (odin.cueSelectArrayData.length > 0) {
			eval("window." + field + "_select = odin.cueSelectArrayData; ");
		}
		//alert(value+":value||field:"+field);
		var find = false;
        if(eval("window."+field+"_select")){
            sel_data  = eval(field+"_select");
            for(i=0;i<sel_data.length;i++){
				//alert(sel_data[i][1]+"||valuee:"+value);
                if(sel_data[i][1] == value){
                    grid.store.getAt(row).set(field,sel_data[i][0]);
                    find = true;
					break;
                }
            }
            if(!find){
            	for(i=0;i<sel_data.length;i++){
                if(sel_data[i][0] == value){
                    find = true;
					break;
                }
            }
            }
            if(!find && sel_data.length>0 && sel_data[0].length>0 && record.get(field)!=''){
            	odin.alert("�������ֵû�ж�Ӧ��ƥ�������Ϣ��ϵͳ��Ĭ�ϸ���ѡ�е�һ�");
            	record.set(field,sel_data[0][0]);
            }
        }
        //alert(field);
        //alert(row+"|"+column);
        //var editor = grid.getColumnModel().getCellEditor(column,row);
        //alert(grid.store.getAt(row).get(field));
        //alert(typeof(editor));
        //if(editor instanceof Ext.grid.GridEditor){
             //alert(1);
        //}
        //alert(editor.getValue());
    },
    doGridSelectAcc:function(value, params, record,rowIndex,colIndex,ds){
	    var selectColumnName = "pctChange";
	    var sel_data = eval(selectColumnName+"_select");
	    for(i=0;i<sel_data.length;i++){
	        if(sel_data[i][0] == value){
	              value = sel_data[i][1];
	              break;
	        }
	    }
	    return value;
	},
	renderDate:function(dateVal){
	    if(!dateVal||dateVal==""){
        	return "";
	    }else{
	     	if(typeof dateVal == 'string'){
	    		dateVal = Date.parseDate(dateVal,'Y-m-d');
	    	}
	    }
    	return Ext.util.Format.date(dateVal,'Y-m-d');
    },
    billPrint:function(repid,queryName,param,preview){
    	//var strpreview="true";
    	//if(!preview){
    	//	strpreview="false";
    	//}
    	var repmode="3";
    	if(!preview){
    		repmode="2";
    	}
    	var url=contextPath+"/common/billPrintAction.do?repid="+repid+"&queryname="+queryName+"&param="+encodeURIComponent(encodeURIComponent(param))+"&repmode="+repmode;
    	window.showModalDialog(url,null,"dialogWidth=800px;dialogHeight=600px");
		//window.open(url,"billWin","status=no,toolbar=no,height=600,width=800");
    },
    reportPrint:function(repid,queryName,param,repmode){
    	if(!repmode){
    		repmode="5";
    	}
    	var url=contextPath+"/common/billPrintAction.do?repid="+repid+"&queryname="+queryName+"&param="+encodeURIComponent(encodeURIComponent(param))+"&repmode="+repmode;
    	window.showModalDialog(url,null,"dialogWidth=800px;dialogHeight=600px");
		//window.open(url,"repWin","status=no,toolbar=no,height=600,width=800");
    },
    getGridJsonData:function(gridId,inputName){
        if(!inputName){
            inputName = gridId+"Data";
        }
        var grid = Ext.getCmp(gridId);
        var store = grid.store;
        var dataArray = new Array(store.data.length);
        for(i=0;i<store.data.length;i++){
            dataArray[i] = store.getAt(i).data; //store.data.length-(i+1)
        }
        var gridJsonStr = Ext.util.JSON.encode(dataArray);
        document.getElementById(inputName).value = gridJsonStr;
        //alert(document.getElementById(inputName).value);
        return gridJsonStr;
    },
    setGridJsonData:function(gridId,inputName){
        if(!inputName){
            inputName = gridId+"Data";
        }
        if(document.getElementById(inputName).value!=null&&document.getElementById(inputName).value!=""){
	        var jsonStr = Ext.util.JSON.decode(document.getElementById(inputName).value);
	        Ext.getCmp(gridId).store.removeAll();
	        if(jsonStr!=null&&jsonStr!=""){
		        var grid = Ext.getCmp(gridId);
		        var store = grid.store;
		        var rsData = new Array(jsonStr.length);
		        for(i=0;i<jsonStr.length;i++){
		            //alert(Ext.util.JSON.encode(jsonStr[i]));
		            rsData[i] = new Ext.data.Record(jsonStr[i]);
		        }
		        store.add(rsData);
	        }
        }
    },
    setGridData:function(gridId,strJsonData,isRemoveAll){
    	strJsonData = strJsonData.replace(/\r/gi,"");
    	strJsonData = strJsonData.replace(/\n/gi,"\\n");
    	var grid = odin.ext.getCmp(gridId);
    	var store = grid.store;
    	if(isRemoveAll && store.getCount()>0){
    		store.removeAll();
    	}
    	var data = odin.ext.decode(strJsonData);
    	if(data.length>0){
    		var rsData = new Array(data.length);
//	    	for(i=0;i<data.length;i++){
//	    		 if( typeof store.reader.meta.id != "undefined"){
//		    	        rsData[i] = new Ext.data.Record(data[i],data[i][store.reader.meta.id]);
//		    	    }else{
//		    	        rsData[i] = new Ext.data.Record(data[i]);
//		    	    }
//
//	        }
//	    	store.add(rsData);
	    	 var readRecords = {};
	          readRecords[store.reader.meta.root] = data;        
	          readRecords[store.reader.meta.totalProperty] = data.length;                     	                   
	          store.loadData(readRecords, false);
	        
    	}
    },
    /**
    *���±��һ������
    **/
    updateGridRowData:function(gridId,rowIndex,rowJsonData){
    	rowJsonData = rowJsonData.replace(/\r/gi,"");
    	rowJsonData = rowJsonData.replace(/\n/gi,"\\n");
    	var data = odin.ext.decode(rowJsonData);
    	var store = odin.ext.getCmp(gridId).store;
    	var rd = store.getAt(rowIndex);
    	for(o in data){
    		rd.set(o,eval('data.'+o));
    	}
    },
    addGridRowData:function(gridId,dataObj,rowCount){
        var grid = Ext.getCmp(gridId);
        var store = grid.store;
        if(!rowCount){
           rowCount = 1;
        }
        var rsData = new Array(rowCount);
        for(i=0;i<rsData.length;i++){
			if (!dataObj) {
				rsData[i] = store.getAt(store.data.length - 1).copy(store.data.length);
			}else{
				rsData[i] = new store.reader.recordType(dataObj);
			}
        }
        store.insert(store.getCount(),rsData);
    },
    openOpLogList:function(){
    	var aid="oplog";
    	var atitle="������־����";
    	//2009-05-20 zhangy 
    	//var src=contextPath+"/sys/MDOpLogListAction.do?functionid="+MDParam.functionid+"&location="+MDParam.location;
    	var src=contextPath+"/sys/MDOpLogListAction.do?functionid="+MDParam.functionid;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
    		var tab=tabs.getItem(aid);
    		if (tab){tabs.remove(tab);}
    		top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    openWzOpLogList:function(sql,colsInfo){
    	parent.wzOpSql = sql;
    	parent.wzColsInfo = colsInfo;
    	var aid="oplog";
    	var atitle="������־����";
    	//var src=contextPath+"/sys/WzMDOpLogListAction.do?functionid="+MDParam.functionid;
    	var src=contextPath+"/radowAction.do?method=doEvent&pageModel=cm&bs=oplog.WzCMOpLogList&initParams="+sql;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
	    	var tab=tabs.getItem(aid);
	    	if (tab){tabs.remove(tab);}
	    	top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    openPMWzOpLogList:function(sql,colsInfo){
    	parent.wzOpSql = sql;
    	parent.wzColsInfo = colsInfo;
    	var aid="oplog";
    	var atitle="������־����";
    	var src=contextPath+"/sys/WzPMMDOpLogListAction.do?functionid="+MDParam.functionid;
    	if(!odin.isWorkpf){
    		var tabs=top.frames[1].tabs;
	    	var tab=tabs.getItem(aid);
	    	if (tab){tabs.remove(tab);}
	    	top.frames[1].addTab(atitle,aid,src);
    	}else{
    		var win = qtobj.openNewTab(src,atitle);
    	}
    },
    accCheckboxCol:function(value, params, record,rowIndex,colIndex,ds){
        var rtn = "";
        rtn += '<div class=\"x-grid-editor\">';
		rtn += '<div class=\"x-form-check-wrap\">';
        if(value==true){
            rtn += "<div ><input type='checkbox' alowCheck='true' name='col"+rowIndex+colIndex+"' onclick='odin.accChecked(this,"+rowIndex+","+colIndex+",\"change\")' checked />";
        }else{
            rtn += "<input type='checkbox' alowCheck='true' name='col"+rowIndex+colIndex+"' onclick='odin.accChecked(this,"+rowIndex+","+colIndex+",\"change\")' />";
        }
        rtn += '</div></div>';
		for(obj in record){
			alert(obj);
		}
        odin.checkboxds = ds;
        return rtn;
    },
    accChecked:function(obj,rowIndex,colIndex,colName,gridId){
		odin.cueCheckBoxChecked = obj.checked;
        if(obj.alowCheck=="false"){
            obj.checked=!obj.checked;
            return;
        }

        if(obj.checked){
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, true);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
			}
        }else{
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, false);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
			}
        }
		odin.checked = obj.cueCheckBoxChecked;
    },
	accCheckedForE3:function(obj,rowIndex,colIndex,colName,gridId){
        if(obj.getAttribute('alowCheck')=="false"){
            return;
        }

        if(obj.className=='x-grid3-check-col'){
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, true);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
			}
			obj.className = 'x-grid3-check-col-on';
        }else{
			if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
				odin.checkboxds.getAt(rowIndex).set(colName, false);
			}else{
				odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
				if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
					document.getElementById("selectall_"+gridId+"_"+colName).value='false';
					document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
				}	
			}
			obj.className = 'x-grid3-check-col';
        }
    },
    doFilterGridCueData:function(grid,filterFunc){
    	grid.store.filterBy(filterFunc);
    },
    doClearFilter:function(grid){
    	grid.store.clearFilter(false);
    },
    showErrorMessage:function(response){
    	var errmsg=response.mainMessage;
		if(response.detailMessage!=""){
			errmsg=errmsg+"\n��ϸ��Ϣ:"+response.detailMessage;
		}
		alert(errmsg);
    },
	encode:Ext.encode,
	beforeedit:function(e){
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
		var cancel = e.cancel;
		//ͨ�����������Խ�ֹ��ǰ��Ԫ��ı༭
		//e.cancel = true; 
	},
	/***************��select������������**************************/
	reSetSelectData:function(selectId,jsonData){
		var store = null;
		var combo = null;
		try{
			combo = odin.ext.getCmp(selectId+"_combo");
			store = combo.store;
		}catch(e){
			combo = odin.ext.getCmp(selectId);
			store = combo.store;
		}
		try{
			document.getElementById(selectId).value = "";
		}catch(e){
		}
		var count = store.getCount();
		//store.removeAll(); ʹ�����������⣬����һ�ζ�ͬһ������ʹ�ô˷���û���⣬���Ժ�ͻᱨjs����
		//��������ͨ��һ��һ����remove����
		for(i=0;i<count;i++){ 
			store.remove(store.getAt(0));
		}
		//�������¼������ݺ��allѡ�� jinwei 2013.3.26
		var allAsItem = combo.allAsItem;
		if(typeof allAsItem != 'undefined' && allAsItem == true){
			store.insert(0, new odin.ext.data.Record({key:"all",value:"ȫ��",params:""})); 
		}
		if(jsonData!=null && jsonData.length>0){
			var data = new Array(jsonData.length);
			for(i=0;i<jsonData.length;i++){
				data[i] = new odin.ext.data.Record(jsonData[i]);
			}
			store.add(data);
		}
	},
	/***********ͳһ���ύ֮ǰ��ȫ��У�麯���������жϷǿյ��Ƿ��пյĴ��ڣ����յ��Ƿ����ҵ��У�����*******************/
	checkValue:function(userTestForm){
		var errtitle = "<b>������������������ٽ��б�������</b><br>";
		var eles = userTestForm.elements;
		for(i=0;i<eles.length;i++){
			var obj = eles[i];
			if(obj.tagName=='OBJECT'||obj.tagName=='object'){//zoulei 2018��4��16�� 
				continue;
			}
			odin.cueCheckObj = obj;	
			if(obj.getAttribute("required")=='true' || (!obj.getAttribute("required") && obj.required)){	//Ϊ�˼���IE�ͷ�IE��IE��Ϊ��true������chrom��֧�֣���getAttribute���߶�֧��
				var type ="";
				try{
					type =radow.findElementTypeByID(obj.id);
				}catch(exception){
					
				}				
				var value="";
				if(type == 'lovcombo'){
					value=odin.ext.getCmp(obj.id+"_combo").getCheckedValue('key');
				}else{
					value=obj.value;
				}
				if(value==""){ //�ǿ��ж�
					//����ǰ��obj.label��Ϊ�������ַ�ʽ��֧�ַ�IE���������˸Ķ�
				 	//odin.alert(obj.getAttribute("label")+"����Ϊ�գ�",odin.doFocus);
					odin.alert(errtitle + obj.getAttribute("label") + "����Ϊ�գ�", odin.doFocus);
					return false;
				}
			}
			if(obj.value!=null&&obj.value!=""){
				//oracle����򲻵ô���4000 by zoul 2016-05-25 16:38:56
				if(odin.getStringByteLength(obj.value)>4000){
					/*if(parent.$h)
					parent.$h.alert('ϵͳ��ʾ',obj.getAttribute("label") + " ֵ���ܴ���4000��", odin.doFocus);
					else
					odin.alert(obj.getAttribute("label") + " ֵ���ܴ���4000��", odin.doFocus,null,400);
					return false;*/
				}
				var eObj = odin.getCmpByName(obj.name);
				if(eObj){
					if (!eObj.isValid(false)) {
						//odin.alert(eObj.invalidText,odin.doFocus);
						var spanId = obj.name.replace("_combo","");
						//alert(Ext.query('#'+spanId+'+div')[0].qtip);
						//odin.error(errtitle + '��' + odin.ext.get(spanId).dom.getAttribute("label") + '���������ֵ������Ҫ�����������룡', odin.doFocus);
						if(Ext.query('#'+spanId+'+div').length>0){
							if(parent.$h)
								if((!window.dialogArguments)||typeof(realParent)=='undefined')
									parent.$h.alert('ϵͳ��ʾ',odin.ext.get(spanId).dom.getAttribute("label")+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,400);
								else
									$h.alert('ϵͳ��ʾ',(obj.getAttribute("titleLabel")==null?odin.ext.get(spanId).dom.getAttribute("label"):obj.getAttribute("titleLabel"))+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,400);
							else
							odin.alert(odin.ext.get(spanId).dom.getAttribute("label")+":"+Ext.query('#'+spanId+'+div')[0].qtip, odin.doFocus,null,400);
						}else if(obj.title){
							if(parent.$h)
							parent.$h.alert('ϵͳ��ʾ',odin.ext.get(spanId).dom.getAttribute("label")+":"+obj.title, odin.doFocus,400);
							else
							odin.alert(odin.ext.get(spanId).dom.getAttribute("label")+":"+obj.title, odin.doFocus,null,400);
						}else{
							odin.alert(errtitle + '��' + odin.ext.get(spanId).dom.getAttribute("label") + '���������ֵ������Ҫ�����������룡', odin.doFocus,null,400);
						}
						
						return false;
					}
				}
			}
		}
		return true;
	},
	/**
	 * ��ȡ�ַ����ֽڳ���
	 */
	getStringByteLength: function(val){

     var Zhlength=0;// ȫ��
     var Enlength=0;// ���
    
     for(var i=0;i<val.length;i++){
         if(val.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null)
        Zhlength+=1;
        else
        Enlength+=1;
     }
     // ���ص�ǰ�ַ����ֽڳ���
     return (Zhlength*2)+Enlength;
   },
	/**
	 * ͨ������ȡ�ö���֧��comboֱ��ȡ
	 */
	getCmpByName:function(name) {
		var obj = null;
		obj = Ext.getCmp(name);
		if (obj == null) {
			obj = Ext.getCmp(name + "_combo");
		}
		return obj;
	},
	cueCheckObj:null,
	isSelectText:true,
	setInvalidMsg:function(id , nowMsg){
		var obj = odin.ext.getCmp(id);
		if(obj){
			obj.invalidText = nowMsg;
		}else{
			obj = odin.ext.getCmp(id+"_combo");
			obj.invalidText = nowMsg;
		}
	},
	doFocus:function (){
		if(odin.cueCheckObj!=null&&odin.cueCheckObj){
			try{
				if (odin.isSelectText==true) {
					odin.ext.getCmp(odin.cueCheckObj.name).focus(true);
				}
				else {
					odin.cueCheckObj.focus();
				}
				odin.cueCheckObj = null;
			}catch(e){
				var comboName = odin.cueCheckObj.name+"_combo";
				if(odin.ext.getCmp(comboName)){
					if(odin.isSelectText==true){
						odin.ext.getCmp(comboName).focus(true);
					}else{
						document.getElementById(comboName).focus();
					}
				}
			}
		}
	}
	/******************************ͳһУ�����****************************************/
	,
	/*********select��ǩ������ftlģ��*************/
	showSelectCodeFtl:new Ext.XTemplate( /**��ʾ�����ͬʱ��ʾֵ��ftl�ļ�����Ҫ����������**/
		'<tpl for="."><div class="x-combo-list-item">',
		'{key}&nbsp;&nbsp;{value}',
		'</div>',
		'</tpl>'
	),
	showValueAndFilterCodeFtl:new Ext.XTemplate( /**ֻ��ʾֵ��ͬʱҪ��������Ĵ���������ѡ��ֵ��ftl�ļ�����Ҫ����������**/
		'<tpl for="."><div class="x-combo-list-item">',
		'{value}',
		'</div>',
		'</tpl>'
	)
	/***********end  ftl****************/
	,
	getSelectDataToArray:function(selectId){ /***��ȡselect��������ݣ�����ת������Ҫ������������******/
		var store = odin.ext.getCmp(selectId).store;
		var length = store.getCount();
		var arrayData = new Array(length);
		for(i=0;i<length;i++){
			var temp = new Array(2);
			temp[0] = store.getAt(i).get('key');
			temp[1] = store.getAt(i).get('value');
			arrayData[i] = temp;
		}
		odin.cueSelectArrayData = arrayData;
	},
	cueSelectArrayData:new Array(0),
	accEditGridSelectColSelEve: function(record,index){ /***�Ա༭���������༭ʱѡ���¼���Ĭ�ϴ���***/
		if(this.fireEvent('beforeselect', this, record, index) !== false){
            this.setValue(record.data[this.valueField || this.displayField]);
            this.collapse();
            this.fireEvent('select', this, record, index);
        }
		odin.getSelectDataToArray(this.getId());
	},
	/**
	 * ��ͨͨ�ò�ѯ
	 * @param {Object} params(Ϊjson����������ֱ�ΪquerySQL����ѯ��sql��hql�����о���sqlType�����ʾ��ѯ�ķ�ʽ������Ϊ"SQL"��"HQL")
	 * @param {Object} succFunc ��ѯ�ɹ���Ҫִ�еĺ���
	 * @param {Object} failFunc ��ѯʧ�ܺ�Ҫִ�еĺ���
	 * @param {Object} sync �������ͣ�true��Ϊͬ����Ĭ��Ϊfalse�����첽����
	 * @param {Object} isMask �Ƿ�����
	 */
	commonQuery:function(params,succFunc,failFunc,sync,isMask){ 
		var url = contextPath+"/common/commQueryAction.do?method=query";
		var req = odin.Ajax.request(url,params,succFunc,failFunc,sync,isMask);
		return req;
	},
	/**
	 * Ϊselect store�������һ���Ĳ�ѯ�����͹����������¼�������
	 * @param {Object} objId Ҫ�������ݵ����id
	 * @param {Object} aaa100 �������
	 * @param {Object} aaa105 ��������
	 * @param {Object} filter ��������
	 * @param {Object} isRemoveAllBeforeAdd ����֮ǰ�Ƿ�Ҫ�����ǰ�����ݣ�Ĭ���������ʱû�ã����������ǰ���ݣ�
	 */
	loadDataForSelectStore:function(objId,aaa100,aaa105,filter,isRemoveAllBeforeAdd){
		var params = {};
		/*---------  ����ͳһ��SysCodeAction����ѯ��   
		params.querySQL = " select * from v_aa10 where aaa100='"+aaa100+"' ";
		if(aaa105!=null&&aaa105!=""){
			params.querySQL += " and aaa105='"+aaa105+"' ";
		}
		if(filter!=null&&filter!=""){
			params.querySQL += " and ("+filter+") ";
		}
		params.querySQL=params.querySQL+" order by aaa102";
		params.sqlType = "SQL";
		var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
		var data = odin.ext.decode(req.responseText).data.data;
		var selectData = null;
		if(data!=null&&data.length>0){
			selectData = new Array(data.length);
			for(i=0;i<data.length;i++){
				selectData[i] = {};
				selectData[i].key = data[i].aaa102;
				selectData[i].value = data[i].aaa103;
			}
		}
		//if (selectData != null) {
			if (isRemoveAllBeforeAdd == false) {
			//
			}
			else {
				odin.reSetSelectData(objId, selectData);
			}
		//}
		*/
		//ͨ��SysCodeAction��ѯ
		params.aaa100 = aaa100;
		params.aaa105 = aaa105;
		params.filter = filter;
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValues",params,odin.ajaxSuccessFunc,null,false,false);
		var data = odin.ext.decode(req.responseText).data;
		var selectData = null;
		if(data!=null&&data.length>0){
			selectData = new Array(data.length);
			for(i=0;i<data.length;i++){
				selectData[i] = {};
				if(data[i].id){
					selectData[i].key = data[i].id.aaa102;
					selectData[i].value = data[i].id.aaa103;
				}else{
					selectData[i].key = data[i].aaa102;
					selectData[i].value = data[i].aaa102;
				}
				selectData[i].params = data[i].eaa101;
			}
		}
		odin.reSetSelectData(objId, selectData);
	},/**
	 * Ϊselect store�������һ���Ĳ�ѯ�����͹����������¼������� ����  �����첽��ʽ
	 * @param {Object} objId Ҫ�������ݵ����id
	 * @param {Object} aaa100 �������
	 * @param {Object} aaa105 ��������
	 * @param {Object} filter ��������
	 * @param {Object} isRemoveAllBeforeAdd ����֮ǰ�Ƿ�Ҫ�����ǰ�����ݣ�Ĭ���������ʱû�ã����������ǰ���ݣ�
	 */
	loadDataForSelectStore2:function(objId,aaa100,aaa105,filter,isRemoveAllBeforeAdd){
		var params = {};
		params.aaa100 = aaa100;
		params.aaa105 = aaa105;
		params.filter = filter;
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValues",params,function(res){
			var data = res.data;
			var selectData = null;
			if(data!=null&&data.length>0){
				selectData = new Array(data.length);
				for(i=0;i<data.length;i++){
					selectData[i] = {};
					if(data[i].id){
						selectData[i].key = data[i].id.aaa102;
						selectData[i].value = data[i].id.aaa103;
					}else{
						selectData[i].key = data[i].aaa102;
						selectData[i].value = data[i].aaa103;
					}
					selectData[i].params = data[i].eaa101;
				}
			}
			odin.reSetSelectData(objId, selectData);
		},null,true,false);
	},
	/**
	 * һ�����첽���ز������µ��������Ӧ��������ѡ����Ϣ
	 * �磺[{id:"AAC005_combo",aaa100:"AAC005",aaa105:"",filter:"",isRemoveAll:true},
	 * {id:"AKB011_combo",aaa100:"AKB011",aaa105:"",filter:"",isRemoveAll:true}]
	 * @param {} selectCodeInfo
	 */
	loadDataForSelectStoreBatch:function(selectCodeInfo,loadFinishFunc){
		var params = {};
		params.codeInfo = odin.encode(selectCodeInfo);
		var req = odin.Ajax.request(contextPath+"/common/sysCodeAction.do?method=querySelectCodeValuesBatch",params,function(res){
			var data = res.data;
			for(var key in data){
				var selectData = null;
				if(data[key]!=null&&data[key].length>0){
					selectData = new Array(data[key].length);
					for(i=0;i<data[key].length;i++){
						selectData[i] = {};
						if(data[key][i].id){
							selectData[i].key = data[key][i].id.aaa102;
							selectData[i].value = data[key][i].id.aaa103;
						}else{
							selectData[i].key = data[key][i].aaa102;
							selectData[i].value = data[key][i].aaa103;
						}
						selectData[i].params = data[key][i].eaa101;
					}
				}
				odin.reSetSelectData(key, selectData);
				//odin.setListWidth(odin.ext.getCmp(key+"_combo"));
			}
			if(typeof radow_select_default != 'undefined'){
				for(var i=0;i<radow_select_default.length;i++){
					var temp = radow_select_default[i];
					odin.setSelectValueReal(temp.id,temp.newvalue);
				}
			}
			if(loadFinishFunc){
				loadFinishFunc();
			}
		},null,true,false);
	},
	ajaxSuccessFunc:function(responseTxt){ //�յ�ajax���óɹ��Ĵ�����
		//
	},/****��ʱ��ؼ���һ��readonly����*****/
	setDateReadOnly:function(id,isReadOnly){
		document.getElementById(id).readOnly = isReadOnly;
		if(isReadOnly==false){
			odin.ext.getCmp(id).onSelect = odin.dateCanSetValue;
		}else{
			odin.ext.getCmp(id).onSelect = odin.dateCanNotSetValue;
		}
	},
	dateCanSetValue:function(m,d){
		this.setValue(d);
		this.fireEvent('select', this, d);
		this.menu.hide();
		if(document.getElementById(this.getId()).onchange){
			document.getElementById(this.getId()).onchange();
		}
	},
	dateCanNotSetValue:function(m,d){
		//
	},
	/*********end ��ʱ��ؼ���һ��readonly����********/
	/****�������ؼ���һ��readonly����*****/
	setComboReadOnly:function(id,isReadOnly){
		document.getElementById(id).readOnly = isReadOnly;
		if(isReadOnly==false){
			odin.ext.getCmp(id+'_combo').onSelect = odin.comboCanSetValue;
		}else{
			odin.ext.getCmp(id+'_combo').onSelect = odin.comboCanNotSetValue;
		}
	},
	comboCanSetValue: function(record, index){
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
			this.setValue(record.data[this.valueField || this.displayField]);
			this.collapse();
			this.fireEvent('select', this, record, index);
		}
	},	
	hasMsgOrMask : function() {
		return (Ext.MessageBox && Ext.MessageBox.isVisible()) || Ext.get(document.body).isMasked();
	},
	comboFocus : function(combo) {
		try {
			if (eval("comboSetFocusForInit_" + combo.getId())) { // ��������ʾ��־
				eval("comboSetFocusForInit_" + combo.getId() + "=false"); // ʹ�ú�ԭ
				return;
			}
		} catch (e) {
		}
		try {
			if (isQuerying) { // ���ڲ�ѯ������ʾ������
				return;
			}
		} catch (e) {
		}
		if (odin.hasMsgOrMask()) {// �����֣�����ʾ������
			combo.triggerBlur();
			return;
		}
		combo.preValue = document.getElementById(combo.getId()).value;
		if (!(combo.list && combo.list.isVisible())) {
			combo.onTriggerClick();
		}
	},
	comboBlur : function(combo) {
		if (combo.list && combo.list.isVisible()) {
			combo.list.hide();
		}
	},
	
	comboCanNotSetValue:function(record, index){
		if(this.fireEvent('beforeselect', this, record, index) !== false){ }
	},
	/*****end �������ؼ���һ��readonly����*****/
	/**
	 * loadPageInTab ��Tab����ʾָ��url��ҳ�棨ע�⣬����ҵ��ҳ���е�JS���ô˺��� ��
	 * @param {} aid    ģ���������Զ����ַ����������Ҫ��ֻ֤��һ��ģ�飬�봫��ģ�����
	 * @param {} url    ҳ���ַ��������context�� ���� /page/.....
	 * @param {} forced �Ƿ�ǿ�ƴ�һ����tab
	 * @param {} text   ���⣬���Ϊ����ȡ��Ӧģ�������
	 * @param {} autoRefresh ��forcedΪfalse������ǿ�ƴ�ʱ���Ƿ��Զ�ˢ��ҳ��  jinwei add 2010.11.24
	 */
	loadPageInTab : function(aid, url, forced, text, autoRefresh) {
		if(odin.isWorkpf){
			qtobj.openNewTab(contextPath + url,text);
		}else{
			var treenode = parent.tree.getNodeById(aid);
			var title;
			if (treenode) {
				if (text)
					parent.addTab(text, treenode.id, contextPath + url, forced,autoRefresh);
				else
					parent.addTab(treenode.text, treenode.id, contextPath + url,forced, autoRefresh);
			} else {
				if (text)
					parent.addTab(text, aid, contextPath + url, forced,autoRefresh);
				else
					parent.addTab("", aid.id, contextPath + url, forced,autoRefresh);
			}
		}
	},
	doChangeDate: function(obj){ //��ʱ���һ�������������롰Ymd�����ָ�ʽ��ʱ��ʱ�Զ�����ת���ɡ�Y-m-d���������ڸ�ʽ������
		var date;
		if(obj.value.indexOf("-")==-1){
			date = Date.parseDate(obj.value,'Ymd');
		}else{
			date = Date.parseDate(obj.value,'Y-m-d');
		}
		obj.value = date.format('Y-m-d');
	},
	renderDate: function(dateVal){ //���������Ҫ��Ϊ�˽���༭������ʱ��༭���޷�����ֵ�����⣬�ɸ�����Ҫ�޸��¡�Y-m���ַ�
		if (!dateVal || dateVal == "") {
			//dateVal = new Date();
			return "";
		}
		else {
			if (typeof dateVal == 'string') {
				dateVal = Date.parseDate(dateVal, 'Y-m-d');
			}
		}
		return Ext.util.Format.date(dateVal, 'Y-m-d');
	},
	/***********����div��ʹ�õ�js**********/
	ClientWidth: function(){
		var theWidth = 0;
		if (window.innerWidth) {
			theWidth = window.innerWidth
		}else if (document.documentElement && document.documentElement.clientWidth) {
				theWidth = document.documentElement.clientWidth
		}else if (document.body) {
			theWidth = document.body.clientWidth
		}
		return theWidth;
	},
	ClientHeight: function(){
		var theHeight = 0;
		if (window.innerHeight) {
			theHeight = window.innerHeight
		}
		else 
			if (document.documentElement && document.documentElement.clientHeight) {
				theHeight = document.documentElement.clientHeight
			}
			else 
				if (document.body) {
					theHeight = document.body.clientHeight
				}
		return theHeight;
	},
	ScrollTop: function(){
		var theSTop = 0;
		if (document.body && odin.ext.isIE) {
			theSTop = document.body.scrollTop
		} else if (document.documentElement) {
			theSTop = document.documentElement.scrollTop + document.body.scrollTop;
		}
		return theSTop;
	},
	close_event: function(){
		control = true;
		document.getElementById('floatZc').style.display = "none";
	},
	/***********end ����div��ʹ�õ�js**********/	
	onKeyDown: function(){ //��ֹ���˼�backspace
		var  event= window.event||arguments[0];
		var ele = event.srcElement||event.target;
		var type = ele.type;
		var keyCode = event.keyCode || event.which || event.charCode;
		try {
			if (keyCode == 8 && ele.readOnly==true) {//����readonlyʱ  ϵͳ�Զ��˳�������
				event.returnValue = false;
			}
		}catch(e){
		}

		// ������˳�����F8��ť�Ĳ���
		if (keyCode == 119) {
			parent.curparent = window;
			if (MDParam == null) {
				return;
			}
			var src = contextPath;
			src += "/radowAction.do?method=doEvent&pageModel=cm&bs=intelligent.CmRuleConfig&initParams="+MDParam.functionid;
			var initParams = MDParam.functionid;
			// alert(Ext.util.JSON.encode(MDParam));
			var uptype = MDParam.uptype;
			if (MDParam.zyxx != "" && MDParam.zyxx != "{}") {// ����һ������Ա
				if(parent != this && parent.odin.openWindow){
					parent.odin.openWindow("pupWindowC0","�����������--" + MDParam.title,src,0.95,0.95);
				}else{
					odin.openWindow("pupWindowC0","�����������--" + MDParam.title,src,0.95,0.95);
				}
				/*doOpenPupWinOnTop(src, "�����������--" + MDParam.title, 0.95,0.95, initParams);*/
			} else {
				odin.info("��<b>" + MDParam.title + "</b>��Ŀǰ����Ϊ��ҵ�񾭰�ģ�飬��������������ˣ�");
			}
			if (Ext.isIE) {
				event.keyCode = 0;
				event.returnValue = false;
			} else {
				event.preventDefault();
				event.stopPropagation();
			}
			return;
		}
		try {
			if ((event.keyCode == 8) && (type != "text" && type != "textarea" && type != "password")) {
				if (Ext.isIE) {
					event.keyCode = 0;
					event.returnValue = false;
				} else {
					event.preventDefault();
					event.stopPropagation();
				}
			}
			//���س���ɰ���tab��
			if (type != 'button' && type != 'textarea' && event.keyCode == 13 ) {
				if(odin.isWorkpf) event.preventDefault();
				if (event.which) {
					 odin.autoNextElement(ele);
				}else{
					event.keyCode = 9;
				}
				var formElement = odin.ext.getCmp(ele.name);
				var xtype = formElement.getXType();
				if (xtype && (xtype == 'datefield' || xtype == 'combo' ||xtype=='lovcombo')) {
					formElement.fireEvent("change", formElement);
				}
			}
		}catch(e){
		}
	},
	autoNextElement:function(ele){ //jinwei 2011.9.22 �Զ���ת���¸�Ԫ��ȥ
		var childs = document.forms[0].elements;
        for (var i = 0; i < childs.length; i++) {
            var q = (i == childs.length - 1) ? 0 : i + 1;// if last element : if any other
            if (ele == childs[i]) {
                while (true) { //����������button��fieldset��Ԫ��
                    var isFound = true;
                    if(childs[q].type == 'hidden' || childs[q].type == 'button' || childs[q].tagName == "FIELDSET" || childs[q].disabled == true/*|| childs[q].readOnly == true*/){
	                    isFound = false;
                    }else{
                    	var _id = childs[q].id;
                    	if(childs[q].readOnly == true){
                    		isFound = false;
                    	}
                    	if(typeof _id !='undefined'){
	                    	if(_id.indexOf("_combo")>0){
	                    		if(odin.ext.getCmp(_id).readOnly !== true){
	                    			isFound = true;
	                    		}
	                    		_id = _id.replace(/_combo/gi,'');
	                    	}
	                    	var _ele = document.getElementById(_id+"TdId");
	                    	if(_ele && _ele!='null'){
	                    		if(_ele.style.display=='none'){
	                    		isFound = false;
	                    	    }
	                    	}
	                    	
                    	}
                    }
                    if(!isFound){
                    	q++;
	                    if (q == (childs.length - 1)) {
	                        break;
	                    }
                    }else{
                    	break;
                    }
                }
                ele.blur();
                var nextel=childs[q];
                if(Ext.getCmp(ele.id) && Ext.getCmp(ele.id).nextElement){
                	nextel= document.getElementById(Ext.getCmp(ele.id).nextElement)
                }
                nextel.focus();
                var combo = document.getElementById(nextel.id + "_combo")
                if (combo) {
                    combo.focus();
                }
                break;
            }
        }
	},
	/*  
	*    round(Dight,How):��ֵ��ʽ��������DightҪ  
	*    ��ʽ����  ���֣�HowҪ������С��λ����  
	*/  
	round: function(Dight, How){
		Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
		return Dight;
	},
	/**
	 * ����ȫѡ������
	 * @param {Object} gridId
	 * @param {Object} obj
	 * @param {Object} fieldName
	 */
	selectAllFunc:function(gridId,obj,fieldName){
		var store = odin.ext.getCmp(gridId).store;
		var value = false;
		if(obj.checked){
			value = true;
		}
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
			//alert(index+":"+store.getCount());
		}
	},
	getSysdate : function() { // ȡ�����ݿ�ʱ��
		var params = {};
		params.sqlType = "SQL";
		params.querySQL = "@_sAnQMaRvSymKDwZt0lEyEqY9ds6ZPFSwqMLdfYvZDtE=_@";
		var req = odin.commonQuery(params, odin.blankFunc, odin.blankFunc, false, false);
		var data = odin.ext.decode(req.responseText).data.data[0];
		var value = data.sysdate;
		value = odin.Ajax.formatDate(value);
		value = Date.parseDate(value, 'Y-m-d');
		return value;
	},
	selectAllFuncForE3:function(gridId,obj,fieldName){
		if(obj.getAttribute('alowCheck')=="false"){
            return;
        }
		var store = odin.ext.getCmp(gridId).store;
		var value = false;
		if (obj.className == 'x-grid3-check-col-on') {
			obj.className = "x-grid3-check-col";
		}else{
			value = true;
			obj.className = 'x-grid3-check-col-on';
		}
		var length = store.getCount();
		for(index=0;index<length;index++){
			store.getAt(index).set(fieldName,value);
		}
	},
	doAccSpecialkey:function(e){
		if(e.getKey()==e.ENTER){
			e.keyCode = 9;
			if(this.getXType()=="combo"){
				var nowValue = e.target.value;
				var oldValue = this.getValue();
				if(nowValue!=oldValue){
					this.setValue(nowValue);
				}
			}
		}
		if(e.isSpecialKey()){
            this.fireEvent("specialkey", this, e);
        }
	},
	
	gridWalkRows : function(grid, row, col, step, fn, scope){     
        var cm = grid.colModel, clen = cm.getColumnCount();
        var ds = grid.store, rlen = ds.getCount();
        
        if(step < 0){
            if(row < 0){ 
                row = rlen - 1;                                               
                while(--col >= 0){                    
                    if(fn.call(scope || this, row, col, cm) === true){
                        return [row, col];
                    }
                }                               
            }else{                
                return [row, col];
            }           
        } else {                         
            if(row >=rlen){
                row = 0;
                while(++col < clen){                    
                    if(fn.call(scope || this, row, col, cm) === true){                        
                        return [row, col];
                    }
                }     
            }else{
               return [row, col];  
            }
                
        }
        return null;
	},
	
	doAccOnEditorKey : function(field, e) { // grid���ⰴ���Ĵ���
		var k = e.getKey(), newCell, g = this.grid, ed = g.activeEditor, shiftKey = e.shiftKey, ctrlKey = e.ctrlKey;
		var isAutoNext=true;//Ĭ�ϻس��Զ�������һ��
		if (k == e.ENTER || k == e.TAB) {
			var cueRow = g.activeEditor.row;
			var cueCol = g.activeEditor.col;
			var colCfg = g.getColumnModel().config[cueCol];
			isAutoNext=colCfg.isAutoNext;
			if(true == colCfg.enterAutoAddRow){
				//radow.addGridEmptyRow(g.getId(),cueRow+1);
				radow.cm.doGridEnterAddRow(g.getId(),cueRow,cueCol,colCfg.dataIndex);
			}
			if (field.getXType() == "combo") { // ���������⴦��
				field.onViewClick(false);
				field.triggerBlur();
			}
			if (k != e.TAB) { // tab��ʱ��������򽹵��������
				e.stopEvent();
			}
			ed.completeEdit();
			if (shiftKey) {
			    if(g.moveWay !== undefined && g.moveWay == "cell"){
			       newCell = odin.gridWalkRows(g, ed.row -1, ed.col, -1, this.acceptsNav, this);
			    }else{
			       newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this); 
			    }				
			} else {
			    if(g.moveWay !== undefined && g.moveWay == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row + 1, ed.col, 1, this.acceptsNav, this);
                }else{
				   newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
				}
			}
		} else if (ctrlKey && (k == e.UP || k == e.DOWN)) {
			e.stopEvent();
			ed.completeEdit();
			if (k == e.UP) {
				if(g.editswitch !== undefined && g.editswitch == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row -1, ed.col, -1, this.acceptsNav, this);
                }else{
                   newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this); 
                }
			} else {
				if(g.editswitch !== undefined && g.editswitch == "cell"){
                   newCell = odin.gridWalkRows(g, ed.row + 1, ed.col, 1, this.acceptsNav, this);
                }else{
                   newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
                }
			}
		} else if (k == e.ESC) {
			ed.cancelEdit();
		}
		if (newCell) {
			try{
				if(isAutoNext!=false && isAutoNext!='false'){
					g.startEditing(newCell[0], newCell[1]);
				}
			}catch(exception){
				g.startEditing(newCell[0], newCell[1]);
			}
						
		}
	},
	/**
	 * ����ͳ��ʱ��Ĭ��render�������������ľ��ǽ�ͳ�ƽ������С�������λ
	 * @param {Object} v
	 * @param {Object} params
	 * @param {Object} data
	 */
	defaultSumRender:function(v, params, data){
		return odin.round(v,2);
	},
	/**
	 * �Զ�����༭���ĳ��ĳ�еı༭ģʽ
	 * @param {Object} gridId ���ID��������
	 * @param {Object} row  �к�
	 * @param {Object} col  �к�
	 */
	startEditing:function(gridId,row,col){
		odin.ext.getCmp(gridId).startEditing(row,col);
	},
	/**
	 * �ж�ĳ��tabҳ�Ƿ��Ǵ��ڼ���״̬�����tabҳ������ϵͳ��tabҳ����ĳ������ģ�����tabҳ
	 * @param {Object} tabid  tab��id���ɴ��ɲ���������ʱĬ��ȡ����functionid
	 */
	isActivedByTabId:function(tabid){
		if(tabid){
			return top.frames[1].tabs.getActiveTab().getId() == tabid;
		}else{
			return top.frames[1].tabs.getActiveTab().getId() == MDParam.functionid;
		}
	},
	toHtmlString : function(str) {// ת����html�ĸ�ʽ
		if (str == null) {
			str = "";
		}
		str = ""+str;
		// �ո��ת��
		str = str.replace(/ /g, "&nbsp;");
		// ���з�ת��
		str = str.replace(/\r\n/g, "<br>");
		str = str.replace(/\n\r/g, "<br>");
		str = str.replace(/\r/g, "<br>");
		str = str.replace(/\n/g, "<br>");
		if (str.indexOf("<font&nbsp;") != -1) { // ����Ĳ�����ԭ
			str = str.replace("<font&nbsp;", "<font ");
		}
		return str;
	},	
	closeMsgBox : function() {
		Ext.MessageBox.hide();
	},
	/**
	 * ���������ݺ��ͳһ�ص�������Ŀǰ����ͳһ����û�������ʱ���쳣��ʾ
	 * @author jinwei 2013.3.35
	 */
	doCommLoad:function(obj){
		var response = obj.reader.jsonData;
		if(response && response.messageCode=='1'){
			//�������˲�ѯ�쳣�����ؿ�����
			odin.error(response.mainMessage);
		}
		//alert(odin.encode(response));
	},
	/**
	 * �����Ľ���������еĴ���ת��˵�����֣�����˵value����01ת�ɡ���λA��
	 * @author jiwnei 2013.3.26
	 * @param {} name
	 * @param {} data
	 * @param {} value
	 * @param {} params
	 * @param {} record
	 * @param {} rowIndex
	 * @param {} colIndex
	 * @param {} ds
	 */
	doGridSelectColRender:function(gridId,name,value, params, record,rowIndex,colIndex,ds){
		if(gridId == null || gridId=="" || gridId=="null"){
			return value;
		}
		var selectCol = odin.getGridColumn(gridId, name);
		var comboObj = null;
		if (selectCol == null) {
			return value;
		} else {
			comboObj = selectCol.editor;
		}
		var store = comboObj.store;
		store.clearFilter();
        var length = store.getCount();
        for(var i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('key')==value){
                value = comboObj.displayField=='key'?rs.get('key'):rs.get('value');
                break;
            }
        }
		return value;
	},
	/**
	 * ��ȡ��������е�key��Ӧ��valueֵ
	 * @param {} gridId
	 * @param {} name
	 * @param {} value
	 */
	getGridSelectColValue:function(gridId,name,value){
		if(gridId == null || gridId=="" || gridId=="null"){
			return value;
		}
		var selectCol = odin.getGridColumn(gridId, name);
		var comboObj = null;
		if (selectCol == null) {
			return value;
		} else {
			comboObj = selectCol.editor;
		}
		var store = comboObj.store;
		store.clearFilter();
        var length = store.getCount();
        for(var i=0;i<length;i++)
        {
            var rs = store.getAt(i);
            if(rs.get('key')==value){
                value = rs.get('value');
                break;
            }
        }
		return value;
	},
	createFilterFn:function(property, value, anyMatch, caseSensitive){
        if(Ext.isEmpty(value, false)){
            return false;
        }
        //value = this.data.createValueMatcher(value, anyMatch, caseSensitive);
        return function(r){
            //return value.test(r.data[property]);
        	if((r.get("key")+"@"+r.get("value")).match(new RegExp(value.replace(/ /gi,".*"),"gi"))!=null){
        		return true;
        	}
        	return false;
        };
    },
    /**
     * ����������ΪpjobName�Ĳ�������
     * @param {} pjobName ������
     * @param execParam -- ��������ִ�в���
     */
    startPJob:function(pjobName,execParam){
    	var url = contextPath+"/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobManage";
    	var param = {};
    	param.eventParameter = pjobName;
    	if(typeof execParam!='undefined' && ""!=execParam){
    		param.eventParameter += "@"+execParam;
    	}
    	param.eventNames = "runPJob";
    	var req = odin.Ajax.request(url,param,odin.ajaxSuccessFunc,odin.ajaxSuccessFunc,null,false,false);
    	var res = odin.ext.decode(req.responseText);
		if(res.messageCode=='0'){
			odin.confirm("�����ɹ������ȷ�����Զ���������ִ�н��ȼ�ؽ��棬��ȷ����Ҫ������",function(btn){
				if(btn=='ok'){
					odin.monitorPJob(pjobName);
				}
			});
		}else{
			odin.error(res.mainMessage);
		}
    },
    /**
     * �򿪶�ʱ�����ش���
     * @param {} name
     */
    monitorPJob:function(name){
		doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.pjob.PJobMonitor&initParams="+name,"����������",700,388);
	},
	/**
	 * ���ݸ�ʽ���ַ������������������
	 * @param {} input
	 * @param {} format
	 */
	commInputMask:function(input,format){
		var v = input.value;
		if ("Y-m-d" == format) {
			if (v.match(/^\d{4}$/) !== null) {
				input.value = v + '-';
			} else if (v.match(/^\d{4}\-\d{2}$/) !== null) {
				input.value = v + '-';
			} else if (v.match(/^\d{4}\-\d{2}\-\d{2,}$/) != null) {
				input.value = v.substr(0, 10);
			}
		}
	},
	/**
	 * �޸�tab�´�ͼ����������������ɫͼ���trrigerͼ���ص�
	 * @param {} field
	 * @param {} msg
	 */
	trrigerCommInvalid:function(field,msg){
		var offsetLeft = field.errorIcon.dom.offsetLeft;
		var width = field.width;
		if(offsetLeft<width){
			field.errorIcon.dom.style.left = (offsetLeft+17)+"px";
		}
	}
};     
Ext.namespace('odin.ext');          
odin.ext = Ext;

//�����������n����
Date.addMonth=function(d,n){
	var year=d.getYear();
	var month=d.getMonth()+n;
	var date=d.getDate();
	return new Date(year,month,date);
}	
//������ת�������͵�����
Date.getYM=function(d){
	var year=d.getYear();
	var month=d.getMonth()+1;
	return year*100+month;
}	
//�����͵����������n����
Date.addMonthYM=function(ym,n){
	var year=Math.floor(ym/100);
	var month=ym%100-1;
	var d=new Date(year,month,1);
	d=this.addMonth(d,n);
	return this.getYM(d);
}
document.onkeydown = odin.onKeyDown;
/**
 * ����Commform�����ӽ�����JS ���� jinwei 2013.3.5
 */
/**
 * �������������Զ���������
 * @param {} combo
 */
odin.setListWidth = function(combo) {
		var width = combo.list.getWidth();
		var comboElements = combo.view.all.elements;
		for (var i = 0; i < comboElements.length; i++) {
			var value = comboElements[i].outerText || comboElements[i].textContent;;
			if (value == null) {
				continue;
			}
			var valueWidth = value.replace(/[^\u0000-\u00ff]/g, "aa").length * 6.5 + 25;
			if (width < valueWidth) {
				width = valueWidth;
			}
		}
		if (combo.mode == "remote" && width < 260) {
			width = 260;
		}
		combo.list.setWidth(width);
		combo.innerList.setWidth('auto');
}
// ���Ӷ�ѡ��MultiCombo
odin.doMultiSelectWithAll = function(combo, record, index) {
	if (record.get('key') == 'all') {
		var checked = record.get('checked');
		combo.deselectAll();
		record.set('checked', checked);
	} else {
		if (combo.store.getAt(0).get('key') == 'all' && combo.store.getAt(0).get('checked') == true) {
			combo.store.getAt(0).set('checked', false);
		}
	}
}
/**
 * ȡ��grid����
 */
odin.getGridColumn = function(gridName, fieldName) {
	if (gridName == null || !Ext.getCmp(gridName)) {
		return;
	}
	
	var gridColumnModel = null;
	
	try {
		gridColumnModel = Ext.getCmp(gridName).getColumnModel();
	} catch (e) {
		return;
	}
	if (!gridColumnModel) {
		return;
	}
	
	var column = null;
	for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
		if (gridColumnModel.getDataIndex(j) == fieldName) {
			column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
			break;
		}
	}
	return column;
};
/**
 * �༭���Ч��
 */
odin.renderEdit = function(value, params, record, rowIndex, colIndex, ds) {
	if (!ds) {// dsΪ�գ�������
		return value;
	}
	var e = {};
	var gridId = ds.baseParams.cueGridId;
	e.grid = Ext.getCmp(gridId);
	e.record = record;
	e.field = Ext.getCmp(gridId).getColumnModel().getDataIndex(colIndex);
	e.originalValue = value;
	e.value = value;
	e.row = rowIndex;
	e.column = colIndex;
	var col = odin.getGridColumn(gridId, e.field);
	if (col.editor && col.editable==true) {
		var type = col.editor.getXType();
		if (type == "numberfield" || type == "textfield" || type == "datefield" || type == "combo" || type == "textarea" || type == "timefield"||type=="ComboBoxWidthTree") {// ֻ�����ַ��������֡����ڡ��������ʽ����
			params.css = "x-grid3-cell-enable";
		}
	}else{
		params.css = "x-grid3-cell-disable";
	}
	return value;
}
/**
 * tabpanel�Ĳ��ֵ���������������ͼ�꼰grid������bug
 * @param {} tab
 */
odin.doTabPanelLayout = function(tab) { // tabpanel�Ĳ��ֵ���������������ͼ�꼰grid������bug
//		var divList = tab.getEl().dom.getElementsByTagName('div');
//		for (var i = 0; i < divList.length; i++) {
//			var divId = divList.item(i).id;
//			if (document.getElementById('gridDiv_' + divId)) {
//				Ext.getCmp(divId).doLayout();
//				Ext.getCmp(divId).view.refresh(true);
//			} else{
//				var inputList = divList.item(i).getElementsByTagName('input');
//				for (var j = 0; j < inputList.length; j++) {
//					var inputItem = inputList.item(j);
//					var cmp = Ext.getCmp(inputItem.name);
//					if (cmp && typeof(cmp.hideTrigger) != "undefined" && !cmp.hideTrigger) {
//						var width = cmp.width;
//						cmp.setWidth(0);
//						if(Ext.isChrome){
//							width = width-17;
//						}
//						cmp.setWidth(width);
//						// cmp.trigger.setLeft(width-(Ext.isIE?3:0));
//					}
//				}
//			}
//		}
		var itab = !tab.getActiveTab ? tab : tab.getActiveTab();
	    var eList = Ext.DomQuery.select("div[@id^=gridDiv_],input[@id]", itab.getEl().dom);
	    var etmp = null;
	    var etagtmp = null;
	    var cmptmp = null;
	    var widthtmp = 0;
	    var girdPefix = "gridDiv_";
	    var inputList = [];
	    for (var i = 0; i < eList.length; i++) {
	        etmp = eList[i];
	        etagtmp = etmp.tagName.toLowerCase();	        
	        if (etagtmp == "div") {
	            cmptmp = Ext.getCmp(etmp.id.substr(girdPefix.length));	            
	            if(!cmptmp){
	                continue;
	            }	            
	            //if(!cmptmp.store || cmptmp.store.getCount()==0){
	                cmptmp.doLayout();
	                cmptmp.view.refresh(true);
	           // }	                           
	        } else if (etagtmp == "input" && etmp.type.toLowerCase() != "hidden") {
	            inputList.push(etmp);
	        }
	    }	
	    for (var j = 0; j < inputList.length; j++) {
			etmp = inputList[j];
			cmptmp = Ext.getCmp(etmp.name);
			if (cmptmp && typeof(cmptmp.hideTrigger) != "undefined"
					&& !cmptmp.hideTrigger) {
				widthtmp = cmptmp.width;
				if(typeof widthtmp == 'undefined') break;
				if (!Ext.isIE || !cmptmp.disabled) {
					widthtmp = widthtmp - 17;
				}
				etmp.style.width = widthtmp + "px";
			}
		}    
	};
	
/**
 * ���Ӵ���
 */	
odin.openWindow = function(id,title,url,width,height,thisWin,isTopParentWin,modal){
	    var win = Ext.get(id);
	    //var onload = "doWinOnload()";
	    if (win) {
	        win.close();
	        return;
	    }
	    if (width <= 1) {
			if (width >= 0) {// С��
				width = document.body.clientWidth * width;
			} else { // ����
				width = document.body.clientWidth + width;
			}
		}
		if (height <= 1) {
			if (height >= 0) {// С��
				height = document.body.clientHeight * height;
			} else { // ����
				height = document.body.clientHeight + height;
			}
		}
		var thisw = thisWin;//��ǰ����
		var parentWinScope = window;//��������
		
		while(true&&isTopParentWin){//������ҳ��
			if(parentWinScope==parentWinScope.parent){
				break;
			}else{
				parentWinScope = parentWinScope.parent;
			}
		}
		if(modal){
			modal = true;
		}else{
			modal = false;
		}
	    win = new parentWinScope.Ext.Window({
	        id:id,
	        title:title,
	        layout:'fit',
	        width:width,
	        height:height,
	        closeAction:'close',
	        collapsible:true,
	        plain: false,
	        resizable: true,
	        thisWin: thisw,
	        modal: modal,
	        html: "<iframe style=\"background:white;border:none;\" id=\"iframe_"+id+"\" width=\"100%\" height=\"100%\" "+(onload == null ? "" : "onload=\"" + onload) + "\" src=\"" + url + "\"></iframe>"
	    });
	    win.show();
	}

//�����������ڵ��ƶ����⣬�ƶ�����������棬��flash���ǵ�����
Ext.useShims = true; // ָ������������õ�ƬЧ��
Ext.override(Ext.Window, {
			listeners : {
				move : function(in_this, x, y) {
					// max window weight and width, -20 because we always want
					// to see at least small part
					var maxX = Ext.getBody().getViewSize().width - 20;
					var maxY = Ext.getBody().getViewSize().height - 20;
					// new position
					x = parseInt(x);
					y = parseInt(y);
					if (x < 0 || x > maxX || y < 0 || y > maxY) { // ����ƶ���ʱ���Ƶ���������������
						// fix if moved too far on top/left
						if (y < 0)
							y = 0;
						if (x < 0)
							x = 0;
						// fix if moved too far on down/right
						if (y > maxY)
							y = maxY - in_this.getHeight();
						if (x > maxX)
							x = maxX - in_this.getWidth();
						// tries to show whole window, if it's too big it will
						// go to left/top corner

						// move window on new position
						in_this.setPosition(x, y);
					}
					if (in_this.el.shim) { // ����ƶ���ʱ��ײ������ֲ��봰��left��top�����룬��Ⱥ͸߶��봰�ڲ�ͳһ����
						in_this.el.shim.setLeftTop(in_this.el.getLeft(true), in_this.el.getTop(true));
						in_this.el.shim.setHeight(in_this.el.getHeight());
						in_this.el.shim.setWidth(in_this.el.getWidth());
					}
				}
			}
		})

	/**
	 * Ext������д
	 */
	Ext.override(Ext.form.VTypes = function() {
		var C = /^[a-zA-Z_\u4e00-\u9fa5]+$/;
		var D = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
		var B = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
		var A = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
		return {
			"email" : function(E) {
				return B.test(E)
			},
			"emailText" : "This field should be an e-mail address in the format \"user@domain.com\"",
			"emailMask" : /[a-z0-9_\.\-@]/i,
			"url" : function(E) {
				return A.test(E)
			},
			"urlText" : "This field should be a URL in the format \"http:/" + "/www.domain.com\"",
			"alpha" : function(E) {
				return C.test(E)
			},
			"alphaText" : "This field should only contain letters and _",
			"alphaMask" : /[a-z_]/i,
			"lt_cn_nm" : function(E) {
				return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(E)
			},
			"lt_cn_nmText" : "��������ֻ���Ǻ��֡���ĸ������",
			"lt_cn_nmMask" : /[a-z0-9\u4e00-\u9fa5]/i,
			"lt_cn" : function(E) {
				return /^[a-zA-Z\u4e00-\u9fa5]+$/.test(E)
			},
			"lt_cnText" : "��������ֻ���Ǻ��ֻ���ĸ",
			"lt_cnMask" : /[a-z\u4e00-\u9fa5]/i,
			"lt_nm" : function(E) {
				return /^[a-zA-Z0-9]+$/.test(E)
			},
			"lt_nmText" : "��������ֻ������ĸ������",
			"lt_nmMask" : /[a-z0-9]/i,
			"cn" : function(E) {
				return /^[\u4e00-\u9fa5]+$/.test(E)
			},
			"cnText" : "��������ֻ���Ǻ���",
			"cnMask" : /[\u4e00-\u9fa5]/i,
			"lt" : function(E) {
				return /^[a-zA-Z]+$/.test(E)
			},
			"ltText" : "��������ֻ������ĸ",
			"ltMask" : /[a-z]/i,
			"nm" : function(E) {
				return /^[0-9]+$/.test(E)
			},
			"nmText" : "��������ֻ��������",
			"nmMask" : /[0-9]/i,
			"isBeforeSysdate" : function(E) { // У�����ڲ��ܴ��ڵ�ǰ����
				return renderDate(E) <= renderDate(odin.getSysdate())
			},
			"isBeforeSysdateText" : "��������ڲ��ܴ��ڵ�ǰ����",
			"isBeforeSysdateMask" : /[0-9]/i,
			"isYM" : function(value) { // У�����ֵ�����
				if (value.length != 6) {
					return false;
				}
				if (value.substr(4) > 12) {
					return false;
				}
				if (value.substr(4) == 0) {
					return false;
				}
				return true;
			},
			"isYMText" : "��������ֻ����6λ�������֣����·ݲ��ܴ���12��Ϊ00����200808",
			"isYMMask" : /[0-9]/i,
			"isBeforeSysYM" : function(value) { // У�����ֵ�����
				if (value.length != 6) {
					return false;
				}
				if (value.substr(4) > 12) {
					return false;
				}
				if (value.substr(4) == 0) {
					return false;
				}
				if (value > Ext.util.Format.date(odin.getSysdate(), 'Ym')) {
					return false;
				}
				return true;
			},
			"isBeforeSysYMText" : "��������ֻ����6λ�������֣����·ݲ��ܴ���12��Ϊ00������������²��ܴ��ڵ�ǰ����",
			"isBeforeSysYMMask" : /[0-9]/i,
			"alphanum" : function(E) {
				return D.test(E)
			},
			"alphanumText" : "This field should only contain letters, numbers and _",
			"alphanumMask" : /[a-z0-9_]/i
		}
	}())
	
	
Ext.apply(Ext.data.Record.prototype,{set : function(name, value){
    if(String(this.data[name]) == String(value)){
        return;
    }
    //this.dirty = true;
    if(!this.modified){
        this.modified = {};
    }
    if(typeof this.modified[name] == 'undefined'){
        this.modified[name] = this.data[name];
    }
    this.data[name] = value;
    if(!this.editing){
        this.store.afterEdit(this);
    }       
}})







/*Ext.override(Ext.grid.EditorGridPanel, {
	
			onEditComplete : function(ed, value, startValue) {
		        this.editing = false;
		        this.activeEditor = null;
		        ed.un("specialkey", this.selModel.onEditorKey, this.selModel);
				var r = ed.record;
		        var field = this.colModel.getDataIndex(ed.col);
		        value = this.postEditValue(value, startValue, r, field);
		        if(this.forceValidation === true || String(value) !== String(startValue)){
		            var e = {
		                grid: this,
		                record: r,
		                field: field,
		                originalValue: startValue,
		                value: value,
		                row: ed.row,
		                column: ed.col,
		                cancel:false
		            };
		            if(this.fireEvent("validateedit", e) !== false && !e.cancel && String(value) !== String(startValue)){
		                r.set(field, e.value);
		                delete e.cancel;
		                this.fireEvent("afteredit", e);
		            }
		        }
		        this.view.focusCell(ed.row, ed.col);
		    }
});*/



