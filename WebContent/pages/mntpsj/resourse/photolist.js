var Photo_List = (function(){
	var p = {};
	var _this = p;
	p.getA0192a = function(a0192a){
		return "����"+a0192a;
	}
	p.getXLXW = function(zgxl,zgxw){
		var desc = zgxl;
		if(desc!=''){
			if(zgxw!=''){
				return desc+"��"+zgxw
			}else{
				return desc
			}
		}else{
			return zgxw
		}
	}
	p.getA0134 = function(a0134){
		if(a0134!=''&&a0134.length>=6){
			return a0134.substring(0, 4)+"��"+a0134.substring(4, 6)+"�²μӹ���" 
		}else{
			return "";
		}
		
	}
	p.getA0140 = function(a0140,a0141,a0144){
		if(a0141=='01'){
			if(a0144!=''&&a0144.length>=6){
				return a0144.substring(0, 4)+"��"+a0144.substring(4, 6)+"�¼����й�������" 
			}else{
				return a0140;
			}
		}else if(a0140!=''){
			return a0140;
		}else{
			return "";
		}
		
	}
	p.removeRmbs = function(a0000){
		var rmbs=document.getElementById('rmbs').value;
		document.getElementById('rmbs').value=rmbs.replace(new RegExp(a0000,'g'),"");
	}
	p.getA0104 = function(a0104,a0111a){
		var xb = "";
		if(a0104=='1'){
			xb = "��";
		}else if(a0104=='2'){
			xb = "Ů";
		}
		return xb + "��" + (a0111a||"") + "��";
	}
	p.getA0107 = function(value){
		var returnAge;
	    if (value == "" || value == null || typeof (value) == "undefined") {
	        return returnAge;
	    } else {
	        var birthYear = value.toString().substring(0, 4);
	        var birthMonth = value.toString().substring(4, 6);
	        var birthDay = value.toString().substring(6, 8);
	        if (birthDay == "" || birthDay == null
	            || typeof (birthDay) == "undefined") {
	            birthDay = "01";
	        }
	        d = new Date();
	        var nowYear = d.getFullYear();
	        var nowMonth = d.getMonth() + 1;
	        var nowDay = d.getDate();
	        if (nowYear == birthYear) {
	            returnAge = 0; // ͬ�귵��0��
	        } else {
	            var ageDiff = nowYear - birthYear; // ��ֻ��
	            if (ageDiff > 0) {
	                if (nowMonth == birthMonth) {
	                    var dayDiff = nowDay - birthDay;// ��֮��
	                    if (dayDiff < 0) {
	                        returnAge = ageDiff - 1;
	                    } else {
	                        returnAge = ageDiff;
	                    }
	                } else {
	                    var monthDiff = nowMonth - birthMonth;// ��֮��
	                    if (monthDiff < 0) {
	                        returnAge = ageDiff - 1;
	                    } else {
	                        returnAge = ageDiff;
	                    }
	                }
	            } else {
	                returnAge = -1;//�������ڴ��� ���ڽ���
	            }
	        }

	        var msg = value.toString().substring(0, 4)+"��"+value.toString().substring(4, 6)+"�³�����"+returnAge.toString()+"�꣩";
	        return msg;

	    }
	}
	
	
	//��ҳ��Ϣ
	p.pageSize=12;
	p.start=0;
	p.nextPage = function() {
        var totalCount = p.store.totalCount;
        var pagecount = p.store.data.length;
        if(p.start>=totalCount||pagecount!=p.pageSize){
        	p.start = 0;
        }else{
        	p.start += p.pageSize;
        }
    }
	return {
		'_p' : p,
		changckbox : function(_this){
			var e = event || window.event;
			var a01content = $('.a01-content');
			var checkbox = $('input[name=a01a0000]',_this);
			e.stopPropagation ? e.stopPropagation() : (e.cancelBubble = true);
			var tg = e.target||e.srcElement;
			var className = $(tg).attr('class')
			if(tg.tagName=="INPUT"||className=='pa0101'||className=='a0104'||className=='a0101'){
				return;
			}
			checkbox.prop('checked', !checkbox.prop('checked'));
			if(checkbox.is(':checked')){
				$(_this).css('background-color','rgb(178 223 255)');
			}else{
				$(_this).css('background-color','rgb(255,255,255)');
			}
			
		},
		setA01Content :function(data){
			
			var _this = this._p;
			
			
			var a01Obj = $('.gbtj-content');
			var a01ObjTem = $('.gbtj-content1');
			
			var templateStr = a01ObjTem.html();
			
			a01Obj.html('');
			$.each(data,function(i,a01){
				var template = $(templateStr);
				$('.a0101',template).text((a01.a0101||""));
				$('.a0104',template).text(_this.getA0104(a01.a0104,a01.a0111a));
				$('.a0107',template).text(_this.getA0107(a01.a0107));
				$('.a0141',template).text(_this.getA0140(a01.a0140,a01.a0141,a01.a0144||"")||"");
				$('.a0134',template).text(_this.getA0134(a01.a0134||""));
				$('.xlxw',template).html(_this.getXLXW(a01.zgxl||"",a01.zgxw||"")||"&nbsp;");
				$('.a0192a',template).text(_this.getA0192a(a01.a0192a||""));
				$('.a0192a',template).attr('title',_this.getA0192a(a01.a0192a||""));
				$('[name=a01a0000]',template).attr('a0000',a01.a0000);//.css('background-color','')
				$('.gb-title-img',template).attr('src',contextPath+'/servlet/DownloadUserHeadImage?a0000='+a01.a0000);
				$('p.pa0101',template).bind('click',function(){
					var rmbs = $("#rmbs").val();
					$("#rmbs").val(rmbs+"@"+a01.a0000);
					if(rmbs.indexOf(a01.a0000)>=0) {
						$h.alert("","�Ѿ�����");
						return ;
					}
					var rmbWin=window.open(contextPath+'/rmb/ZHGBrmb.jsp?a0000='+a01.a0000, '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
					
					var loop = setInterval(function() {
						if(rmbWin.closed) {
							clearInterval(loop);
							_this.removeRmbs(a01.a0000);
							}
						}, 500);
					/*radow.doEvent('elearningGrid.rowdbclick',a01.a0000);*/
				});
				a01Obj.append(template)
			});
			a01Obj.show();
			this.doResize();
			
			myMask.hide();//����
		},
		clearSelectedA01 :function() {
			$('input:checkbox[name=a01a0000]:checked').prop('checked', false);
			$('.gbtj-list').css('background-color','rgb(255,255,255)');
		},
		getSelectedA01 :function () {
			var a0000s='';
			$('input:checkbox[name=a01a0000]:checked').each(function(i){
				a0000s = a0000s + $(this).attr('a0000') + ',';
		     });
			if (a0000s == '') {
				odin.alert("��ѡ����Ա��");
				return;
			}
			a0000s = a0000s.substring(0, a0000s.length - 1);
			return a0000s
		},
		doResize : function(){
			var viewSize = Ext.getBody().getViewSize();
			//�����ø� �����ÿ�
			$('.gbtj-content').height(viewSize.height-$h.pos($('.gbtj-content')[0]).top-1);
			$('.gbtj-content').width($(document.body).width());
		},
		ajaxSubmit : function(radowEvent, pageModel, parm, callback,NO_SPE_SUCCESS) {
			
			var _this = this;
			myMask.show();//��ʾ
			if (parm) {
			} else {
				parm = {};
			}
			parm['start'] = this._p.start;
			parm['limit'] = this._p.pageSize;
			Ext.Ajax.request({
				method : 'POST',
				form : 'commForm',
				async : true,
				params : parm,
				timeout : 300000,// ���������
				url : contextPath+"/radowAction.do?method=doEvent&pageModel=" + pageModel + "&eventNames=" + radowEvent,
				success : function(resData) {
					var cfg = Ext.util.JSON.decode(resData.responseText);
					//console.log(cfg)
					if(cfg.messageCode=="1"){
						$('.gbtj-content').html(cfg.mainMessage).show();
						$h.alert('',cfg.mainMessage)
						return;
					}
					
					
					//ֱ����ʾ SPE_SUCCESS
					if(!NO_SPE_SUCCESS){
						
						var qdata = cfg.data;
						// console.log(qdata)
						if(qdata&&qdata.length==0){
							$('.gbtj-content').html('�޲�ѯ�����').show();
						}
						_this._p.store = cfg;
						_this.setA01Content(qdata);
					}
					//responseText
					if (!!callback) {
						if(cfg.elementsScript&&cfg.elementsScript.indexOf("\\n")>0){
				          cfg.elementsScript = cfg.elementsScript.replace(/\\r/gi,"");
				          cfg.elementsScript = cfg.elementsScript.replace(/\\n/gi,"{RN}");
				        }
						callback(cfg);
					}
				},
				failure : function(res, options) {
					Ext.Msg.hide();
					alert("�����쳣��");
				}
			});
		}
	
	
	
	
	
	}
})();









