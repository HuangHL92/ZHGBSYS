System.LevelQuery = {

	getCondition:function(target,callback){
		System.LevelQuery._callback = function(params){
			callback(params);
		};
		System.LevelQuery.closeWin = function(){
			$("#_levelQueryWin",target).window('close');
		};
		if($("#_levelQueryWin",target).length>0){
			$("#_levelQueryWin",target).window();
		}else{
			var levelUrl = System.rootPath + "/query/level-query.action?callback=true";
			var divHtml = "<div id='_levelQueryWin' class='easyui-window'>";
				divHtml += "<iframe src="+levelUrl+" width='100%' height='100%' scrolling='no' frameborder='0'></iframe>";
				divHtml += "</div>";
			$(target).append(divHtml);
			$("#_levelQueryWin",target).window({
				title:'设置查询条件',
				//fit:true,
				width:1000,
				height:500,
				collapsible:false,
				minimizable:false,
				draggable:false,
				shadow:true
			});
		}
	},
	getCondition2:function(initGroup,initTable,currentUnit,queryAllUnits, callback){
		if (currentUnit==null || currentUnit==undefined) currentUnit = ''; // 如果没有定义单位，默认搜索全部, by YZQ on 2013/03/26
		var levelUrl = System.rootPath + "/query/level-query.action?callback=true&initGroup="+initGroup+"&initTable="+initTable + "&currentUnit=" + currentUnit + "&queryAllUnits=" + queryAllUnits;
		var returnValue = false;
		try{
			returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=900px;dialogHeight=470px");
		}catch(e){
		}
		if(returnValue==null||returnValue==undefined){
			returnValue = false;
		}
		callback(returnValue);
	},
	getCondition3:function(initGroup,initTable,currentUnit,queryAllUnits, closeCallback){
		if (currentUnit==null || currentUnit==undefined) currentUnit = ''; // 如果没有定义单位，默认搜索全部, by YZQ on 2013/03/26
		var levelUrl = System.rootPath + "/query/level-query.action?callback=true&initGroup="+initGroup+"&initTable="+initTable + "&currentUnit=" + currentUnit + "&queryAllUnits=" + queryAllUnits;
		
		System.openPopupWindow(levelUrl, '高级查询', 920, 640, null, closeCallback, 'advSearchDlg');
	}
};
System.manyQuery={
	init:function(serviceid,fzai,currentUnit,callback){
		if (currentUnit==undefined) currentUnit = ''; // 如果没有定义单位，默认搜索全部, by YZQ on 2013/03/26
		if(fzai==undefined)fzai = false;
		var Url = System.rootPath + "/query/many-query.action?serviceid="+serviceid+"&fzai="+fzai+"&currentUnit="+currentUnit;
		if($("#_manyQueryWin").length==0){
			var divHtml = "<div id='_manyQueryWin' class='easyui-window'>";
				divHtml += "<iframe id='manyQueryIframe' src="+Url+" width='100%' height='100%' scrolling='no' frameborder='0'></iframe>";
				divHtml += "</div>";
			$('body').append(divHtml);
		}
		else {
			// 更新source，因为在非在职维护里面，SRC是不同的, by YZQ on 2012/09/23
			if (Url != $("#manyQueryIframe").attr("src")) {
				$("#manyQueryIframe").attr("src", Url);
			}
		}
		$("#_manyQueryWin").window({
			title:'设置查询条件',
			//fit:true,
			width: 800,
			height: 480,
			collapsible:false,
			minimizable:false,
			draggable:true,
			shadow:true,
			modal : true,
			onClose : function(){
				if(typeof(callback)=="function"){
					callback();
				}
			}
		});
	},	
	init2 : function(serviceid,callback){
		var levelUrl = System.rootPath + "/query/level-query.action";
		var returnValue = false;
		try{
			returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=900px;dialogHeight=470px");
		}catch(e){
		}
		if(returnValue==null||returnValue==undefined){
			returnValue = false;
		}
		if(typeof(callback)=="function"){
			callback(returnValue);
		}
	},
	closeWin : function(){
		$("#_manyQueryWin").window('close');
	}
};
System.Meeting = {
	pickUpPerson: function(closeCallback){
		var url = System.rootPath + '/meeting/meeting!pickPerson.action';
							
		System.openPopupWindow(url, '挑选干部', 980, 640, null, closeCallback, 'meetingPickUpPersonDlg');
	}
};
System.upload = {
	uploadFile : function (obj,ref){
		var dialog = "<div id='fileDialog'></div>";
		$(obj).parent().remove("#fileDialog").append(dialog);
		var contentUrl = System.rootPath + "/common/upload.jsp?id="+$("#"+ref).val();
		$("#fileDialog").dialog({
			title:$(obj).val(),
			width:400,
			height:280,
			shadow:true,
			modal :true,
			content : "<iframe width=\"100%\" height=\"100%\" scrolling=\"no\" frameborder=\"0\"  src='"+contentUrl+"' ></iframe>",
			onOpen : function () {
			},
			onClose:function(){
				$("#fileDialog").dialog('destroy',true);
			}
		});
	},
	_callback:function (data,callbackName){
		eval("parent."+callbackName+"("+data+");");
	},
	closeUploadFile : function () {		
		$("#fileDialog").dialog('close');
	}
};

System.code={
	clear: function(target) {
		$(target).combotree("clear");
		$(target).data("code_new", new code('', ''));
		$(target).data("code", new code('', ''));
	},
	init:function(target,codeClass,callback){
		//$(target).combotree('destroy',true);
		$(target).combotree({
			url:System.rootPath+"/code/code.action?codeClass="+codeClass,
			onLoadSuccess:function(node,data){
				if(callback){
					callback(node,data);
				}
			}
		});
	},
	delay : function(target,codeClass,codevalue,showvalue,w,clickCallBack){
		if(!w){
			w = 150;
		}
		//$(target).combotree('destroy',true);
		$(target).data("code",new code(codevalue,showvalue));
		$(target).combotree({
			width:w,
			//url:System.rootPath+"/code/code.action?codeClass="+codeClass,
			onShowPanel:function(){
				var code = $(target).data("code");
				$(target).combotree({
					width:w,
					url : System.rootPath+"/code/code.action?codeClass="+codeClass,
					value:code.value
				});
			},
			onClick :function (node){
				$(target).data("code",new code(node.id,node.text));
				clickCallBack(node);
			},
			onLoadSuccess : function(){
				var code = $(target).data("code");
				$(target).combotree('setText',code.show);
			}
		});
	},
	render : function(target,codeClass,codevalue,showvalue,w,clickCallBack,callback){
		if(!w){
			w = 150;
		}
		
		$(target).data("code", new code(codevalue,showvalue));
		$(target).data("code_new", null);
		$(target).data("isQuerying", "false");	
		$(target).data("loadedData", "false");
		
		$(target).combotree({
			width:w,
			editable:true,
			delay: 200,
			data:[],
			onSelect :function (node){
				$(target).data("code",new code(node.id,node.text));
				clickCallBack(node);
			},
			onLoadSuccess : function() {
				var code = $(target).data("code_new");
				if (code != null){
					$(target).combotree('setText', code.show);// 清空代码
					//$(target).next('span').find('.textbox-text').val(code.show);
					//$(target).next('span').find('.textbox-value').val(code.value);
				}
				else {
					$(target).combotree('setText', showvalue);
					//$(target).next('span').find('.textbox-text').val(showvalue);
					//$(target).next('span').find('.textbox-value').val('');
				}
			},
			keyHandler:{
				enter:function(){
					
				},
				query:function(q){
					if (q.length < 2) { 
						$(target).data("loadedData", 'false');
						$(target).data("isQuerying", 'false');
						
						if (q == '') {
							$(target).data("code_new", new code('',q));
							$(target).data("loadedData", 'true');
							var url = System.rootPath+"/code/code.action?codeClass="+codeClass;					
							$(target).combotree('reload', url);
						}
						
						return;
					}
					$(target).data("isQuerying", 'true');
					
					$.ajax({
						type:'post',
						url: System.rootPath + "/code/code!queryCode.action",
						dataType:'json',
						data:{'querykey':q,'codeClass':codeClass},
						success:function(json){
							if (json.length > 0) {
								$(target).data("code_new",new code(json[0].id, q));
							}
							else {
								$(target).data("code_new",new code('',q));
							}
							
							$(target).combotree('tree').tree('loadData',json);
							
							// 缺省选择第一个, by YZQ on 2012/10/15
							if (json.length != 0) {
								clickCallBack(json[0]);
							}
						},
						error:function(){
							$(target).combotree('tree').tree('loadData',[]);
						}
					});
				},
				down:function(){
				},
				up:function(){
				}
			},
			onShowPanel:function(){
				// 如果是查询，不需要任何操作
				if ($(target).data('isQuerying') == 'true') {
					var text = $(target).combotree('getText');
					if (text == '') {
						$(target).data("code_new", new code('', ''));
						var url = System.rootPath+"/code/code.action?codeClass="+codeClass;					
						$(target).combotree('reload', url);
					}
				}
				else {
					var text = $(target).combotree('getText');
					// 说明更改了文本框内容，这个会比第一次query事件提前产生
					if (text != showvalue) {
						
					}
					else {						
						$(target).data("code_new",new code('', text));
						var url = System.rootPath+"/code/code.action?codeClass="+codeClass;					
						$(target).combotree('reload', url);
					}
				}
				
			}
		});
	},
	getTreeLastNode:function (target,nodeid){
		var root  = null;
		if(nodeid){
			root = [$(target).combotree('tree').tree('find',nodeid)];
		}else{
			root  = $(target).combotree('tree').tree('getRoots');
		}				
		var lastRoot = root[root.length-1];
		if($(target).combotree('tree').tree('isLeaf',lastRoot.target)){
			return lastRoot;
		}else{
			var flag = true;
			var rNode = lastRoot;
			do{
				var childrens = $(target).combotree('tree').tree('getChildren',rNode.target);
				var lastChildren = childrens[childrens.length-1];
				if($(target).combotree('tree').tree('isLeaf',lastChildren.target)){
					flag = false;
				}
				rNode = lastChildren;
			}while(flag);
			return rNode;
		}
	},
	getRootNodeByNodeId:function (target,node){
		//var node = $(target).combotree('tree').tree('find',nodeid);
		var flag = true;
		do{
			if($(node.target).parent().parent().is(".tree")){		
				flag = false;
			}else{
				node = $(target).combotree('tree').tree('getParent',node.target);
			}
		}while(flag);
		return node;
	}
};
function code(value,show){
	this.value = value;
	this.show = show;
}

System.combogrid = {
		init:function(obj,contentUrl,callback){
			var flag = false;
			var parent = $(obj).parent();
			var w = $(obj).width()-20;
			var v = $(obj).val();
			var temp = $("<div></div>").append($(obj));
			var html = "<span class='combo'>"+temp.html()+"<span class='combo-arrow'></span></span>";
			temp.remove();
			$(parent).append(html);
			$(obj).width(w).val(v);
			$(obj).addClass('combo-text');
			$(obj).attr("readOnly",true);
			var containerId = panelInit();
			$(obj).next().hover(function(){
					$(this).css('opacity','1');	
				},function(){
					$(this).css('opacity','0.6');
				}).toggle(function(){
					if(flag){$.powerFloat.hide();}else{$(obj).trigger('click');}
				},function(){
					if(!flag){$.powerFloat.hide();}else{$(obj).trigger('click');}
				});
			$(obj).powerFloat({
					//targetMode: "ajax",	
					width:200,
					eventType: "click",
					reverseSharp:false,
					zIndex:10000,
					target: "#"+containerId,
					showCall: function() {
						$("#"+containerId+"-panel").panel('open');
						flag = true;
					},
					hideCall:function(){
						flag = false;
					}
			});
			function panelInit(){
				var id = "my-combogrid-"+$(obj).attr('id');
				if($("#"+id,'body').length<=0){
					$("<div id='"+id+"'><div id='"+id+"-panel' style='background:#ffffff;'></div></div>").appendTo('body');
				}
				var fname = ("callback_"+Math.random()+"_").replaceAll("\\.","_");
				if(/\?/.test(contentUrl)){
					contentUrl = contentUrl + "&callback=" + fname;
				}else{
					contentUrl = contentUrl + "?callback=" + fname;
				}
				$('body').data(fname,callback);
				$('#'+id+"-panel").panel({
					  width:450,
					  height:250,
					  closed:true,
					  content:"<div><iframe border='0' width='100%' height='100%' frameborder='0' src='"+contentUrl+"'></iframe></div>"
				});
				return id;
			}
		},
		_callback:function _callback(data,callbackName){
			$('body').data(callbackName)(data);
		}
};

System.report = {
	print:function(scope){
		var reportid = $("#mm").data('selectReportId');
		if(reportid == undefined || reportid == ""){
			$.messager.alert('提示','请选择表格类型!');
			return false;
		}else{
			//$.post('url',{scope:scope},function(tick){
			//	window.open("path?"+tick, "打印", 'height='+window.screen.availHeight+',width='+window.screen.availWidth+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
			//});
			 
			if(scope == ""){
				$.messager.alert('提示','请选择人员!');
			}else{
				var editPerson = null;
				var readPerson = null;
				$.ajax({
					 //async:false,
		             url: System.rootPath + '/security/access-right!printAuth.action',
		             data: {'personcodes': scope},
		             success: function(personList) {
		            
		            		 $.each(personList,function(i,value){
		            			if (value.name !=null) {
		                            if (readPerson == null) readPerson = value.name;
		                            else readPerson = readPerson + "," + value.name;
		            			}else if (value.editablePerson !=null) {
		                            if (editPerson == null) editPerson = value.editablePerson;
		                            else editPerson = editPerson + "," + value.editablePerson;
		            			}
		            		 });
		         
		            		 if (editPerson != null) {
		            			 if (readPerson != null) {
			                	     alert('你没有打印下列人员信息的权限，系统将不显示这些人员!\r\n'+readPerson);
		            			 }
			     				sysPrintReport(reportid, editPerson);
		                }else {
		                	alert('你没有打印该人员信息的权限!');
		                }
		               
		             },
		             error: function() {
		                 $.messager.alert('错误', '网络错误!', 'error');
		             },
		             complete: function() {}
		         });
				 
				
			}
			return false;
		}
	},
	printAll : function (sessionkey){
		var reportid = $("#mm").data('selectReportId');
		if(reportid == undefined || reportid == ""){
			$.messager.alert('提示','请选择表格类型!');
			return false;
		}else {
			var currentUnit = '';
			if (reportid == '-100' || reportid == '-1000') {
			// 获取当前选中单位
			try {
				if ($("#mc").length > 0) {
					var queryType = $("#mc").data("queryType");
					if (queryType == '1') {
						currentUnit = $("#currentUnit").val();
					}
				}
			}
			catch (e) {
				currentUnit = ''
			}
		}
			if (reportid == '-999') {
				System.customMc.pickItems(function(items) {
					var path = System.rootPath+"/mingc/custom-mc!generateMc.action?sessionkey="+sessionkey+"&customMcItems="+items;
					window.open(path, "输出干部名册");
				});
			}else if (reportid == '-100') {
				var path = System.rootPath+"/report/report-print!printGeneralCardReport.action?sessionkey="+sessionkey + '&currentUnit=' + currentUnit;
				window.open(path, "输出干部名册");
			}
			else if (reportid == '-1000') {
				var path = System.rootPath+"/report/report-print!printGeneralCardWordReport.action?sessionkey="+sessionkey + '&currentUnit=' + currentUnit;
				window.open(path, "输出干部名册");
			}else{
				var path = System.rootPath+"/report/report-print!reportPrint.action?rtid="+reportid+"&sessionkey="+sessionkey;
				window.open(path, "打印", 'height='+window.screen.availHeight+',width='+window.screen.availWidth+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
			}
			return false;
			}
	},
	printLeader:function(unitcode){
		var path = System.rootPath+"/report/report-print!leaderCard.action?unitcode="+unitcode;
		window.open(path, "打印领导名册", 'height='+window.screen.availHeight+',width='+window.screen.availWidth+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
		return false;
	},
	printLeaderBySessionKey:function (sessionkey, fzz){
		var path = System.rootPath+"/report/report-print!leaderCard.action?sessionkey="+sessionkey;
		// 支持非在职，by YZQ on 2012/10/15
		if (fzz) {
			path = path + "&fzz=" + fzz;
		}
				
		window.open(path, "打印领导名册", 'height='+window.screen.availHeight+',width='+window.screen.availWidth+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
		return false;
	},
	// 输出任免表, by YZQ on 2012/09/16
	printRemoval: function(keys, type) {
		keys = keys + "";
		if(keys == ""){
			$.messager.alert('提示','请选择人员!');
			return false;
		}
		else if (keys.indexOf(",") >= 0) {
			$.messager.alert('提示','请只选择一个人员!');
			return false;
		}
		//增加打印权限控制 by yhy on 2016/07/11
		 var editPerson = null;
		 $.ajax({
             //url: System.rootPath + '/security/access-right!checkIsEditableZgld.action',
			 url: System.rootPath + '/security/access-right!printAuth.action',
             data: {'personcodes': keys},
             success: function(personList) {
            	 $.each(personList,function(i,value){
            		editPerson = value.editablePerson;
         		 });
                 if (editPerson == null) {
                	 $.messager.alert('提示','你没有打印该人员信息的权限!');
                	 return false;
                 }else {
                	 var removalType = 0;
             		if (type != 'undefined') {
             			removalType = type;
             		}		
             		
             		// 弹出出表时间，by YZQ on 2012/11/05
             		if ($("#removalDateDialog").length == 0) {
             			$("body").append("<div id='removalDateDialog'></div>");			
             		}
             		
             		var content = "<table class='kv-table' width='100%'>";
             		content += "<tr height='26'><td class='kv-label'>格式设置</td><td class='kv-content'><label><input type='radio' name='removalType' id='removalType1' checked='true'>A4</label><label><input type='radio' name='removalType' id='removalType2'>B5</label><label><input type='radio' name='removalType' id='removalType3'>中组部任免表</label></td>";
             		content += "<tr height='26'><td class='kv-label'>出表时间</td><td class='kv-content'><input type='input' name='removalDate' id='removalDate'></td></tr></table>";
             		
             		// 弹出对话框
             		$('#removalDateDialog').dialog({  
             			title: '任免表输出设置',  
             			width: 400,  
             			height: 175,  
             			closed: true,
             			modal: true, 
             			content: content,
             			onOpen: function () {
             				var now = new Date();
             				var year = now.getFullYear();
             				var month = now.getMonth() + 1;
             				var day = now.getDate();
             				var monthStr = (month < 10) ? "0" + month : "" + month;				
             				var dayStr = (day < 10) ? "0" + day : "" + day;
             				var date = year + "-" + monthStr + "-" + dayStr;
             				$("#removalDate").datebox({
             					value: date
             				});
             			},
             			buttons:[{
             				text:'确定',
             				handler:function(){
             					var date = $("#removalDate").datebox('getValue');
             					if (date == '') {
             						$.messager.alert('提示','请选择出表时间!');
             						return;
             					}
             					else {
             						var path = null;
             						if ($("#removalType1").prop('checked')) {
             							path = System.rootPath+"/removal/removal!exportRemoval.action?personcode="+keys + "&removalType=0&removalDate=" + date;		
             						}
             						else if ($("#removalType2").prop('checked')) {
             							path = System.rootPath+"/removal/removal!exportRemoval.action?personcode="+keys + "&removalType=1&removalDate=" + date;		
             						}
             						else {
             							path = System.rootPath+"/removal/removal!exportLrmxRemoval.action?personcode="+keys + "&removalDate=" + date;		
             						}
             						window.open(path, "输出任免表");		
             						$('#removalDateDialog').dialog('close');						
             					}
             				}
             			}]
             		});  
             		
             		$('#removalDateDialog').dialog('open');
                 }
             },
             error: function() {
                 $.messager.alert('错误', '网络错误!', 'error');
             },
             complete: function() {}
         });
         
		
		
		return false;
	},
	//输出厅局干部培训表  by yhy on 2015/06/10
	printTrainingDetails: function(keys) {
		keys = keys + "";
		if(keys == ""){
			$.messager.alert('提示','请选择人员!');
			return false;
		}
		else if (keys.indexOf(",") >= 0) {
			$.messager.alert('提示','请只选择一个人员!');
			return false;
		}
		
		var path = System.rootPath+"/train/train!exportSimpleDetails?personcode="+keys;		
		window.open(path, "输出厅局干部培训表");
		return false;
	},
	// 输出干部简要情况表, by YZQ on 2012/09/16
	printSimpleDetails: function(keys) {
		keys = keys + "";
		if(keys == ""){
			$.messager.alert('提示','请选择人员!');
			return false;
		}
		else if (keys.indexOf(",") >= 0) {
			$.messager.alert('提示','请只选择一个人员!');
			return false;
		}
		var editPerson=null;
		//增加简要情况表打印权限 by yhy on 2016/07/11
		$.ajax({
            //url: System.rootPath + '/security/access-right!checkIsEditableZgld.action',
			url: System.rootPath + '/security/access-right!printAuth.action',
            data: {'personcodes': keys},
            success: function(personList) {
            	$.each(personList,function(i,value){
            		editPerson = value.editablePerson;
         		 });
                if (editPerson == null) {
               	 $.messager.alert('提示','你没有打印该人员信息的权限!');
                return false;
                }else {
                	var path = System.rootPath+"/removal/removal!exportSimpleDetails.action?personcode="+keys;		
            		window.open(path, "输出干部简要情况表");
                }
            },
            error: function() {
                $.messager.alert('错误', '网络错误!', 'error');
            },
            complete: function() {}
        });
		return false;
	},
	menu:function(obj,clickCallback,filterType,addLeader){
		if(addLeader == undefined) addLeader = true;
		$.post(System.rootPath+"/report/report!getPersonReportListByUser.action",{groupname:'A'},function(json){
			var menuHtml = "<div id=\"mm\" style=\"width:200px;\">";
			var menuMc = '';
			var menuCard = '';
			var menuCustom = '';
			$(json).each(function(i, v) {
				if (v.report_type == '1') {
					menuMc += "<div itemid = '"+v.id+"'>";
					menuMc += v.report_name;
					menuMc += "</div>";
				}
				else if (v.report_type == '2') {
					menuCard += "<div itemid = '"+v.id+"'>";
					menuCard += v.report_name;
					menuCard += "</div>";
				}
				else if (v.report_type == '9') {
					menuCustom += "<div itemid = '"+v.id+"'>";
					menuCustom += v.report_name;
					menuCustom += "</div>";
				}
			});
			
			if (menuMc != '') {
				menuHtml += "<div>";
				menuHtml += "<span>名册</span>";
				menuHtml += "<div style=\"width:200px;\">";
				menuHtml += menuMc;
				menuHtml += "</div>";
				menuHtml += "</div>";
				menuHtml +="<div class=\"menu-sep\"></div>";
			}
			if (menuCard != '') {
				menuHtml += "<div>";
				menuHtml += "<span>表格</span>";
				menuHtml += "<div style=\"width:200px;\">";
				menuHtml += menuCard;
				menuHtml += "</div>";
				menuHtml += "</div>";
				menuHtml +="<div class=\"menu-sep\"></div>";
			}
			if (menuCustom != '') {
				menuHtml += menuCustom;
			}
			
			menuHtml += "</div>";
			$(obj).parent().find("#mm").remove();
			$(obj).parent().append(menuHtml);
			$(obj).menubutton({
			    menu: '#mm',
			    plain:false			    
			});
			$("#mm").menu({
				onClick:function(item){
					$("#mm").data('selectReportId',$(item.target).attr("itemid"));
					$(obj).data('selectReportId',$(item.target).attr("itemid"));
					
					clickCallback();
					return false;
				}
			});
		});
	}
};

System.image = {
	initUpload : function (obj,ref,dbtype){
		var dialog = "<div id='fileDialog'></div>";
		$(obj).parent().remove("#fileDialog").append(dialog);
		var contentUrl = System.rootPath + "/common/upload-iframe.jsp?dbtype="+dbtype;
		$("#fileDialog").dialog({
			title:$(obj).val(),
			width:400,
			height:280,
			shadow:true,
			modal :true,
			href : contentUrl,
			onOpen : function () {
			},
			onClose:function(){
				var flag = $("#fileDialog").data('flag');
				if(flag == true){
					$(obj).data("clientPath",$("#fileDialog").data("clientPath"));
					var serverPath = $("#fileDialog").data("serverPath");
					$(obj).data("serverPath",serverPath);
					$("#"+ref).val(serverPath);
					var blobUpdateFields = $("#sys_blobUpdateFields").data("fields");
					if(!blobUpdateFields){
						blobUpdateFields = [];
					}
					blobUpdateFields.push(ref);
					$("#sys_blobUpdateFields").data("fields",blobUpdateFields);
				}
				$("#fileDialog").dialog('destroy',true);
			},
			onLoad : function(){
				$("#uploadFile").css({
	    			"top":"50px",
	    			"left":0,
	    			"z-index":"1000"});
				var clientPath = $(obj).data("clientPath");
				var serverPath = $(obj).data("serverPath");
				if(clientPath){
					if(dbtype == 60){
						if($.browser.msie){
							$("#preview_madia").empty().append("<embed id='preview' type='application/x-mplayer2'  autostart='true'  src='"+clinetPath+"' width='90%' height='90%' loop='false'></embed>");
						}else{
							$("#preview_madia").empty().append("<video controls='controls' id='preview'  autoplay='true'  src='"+System.rootPath+"/uploadfile/"+serverPath+"' width='90%' height='90%' loop='false' />");
						}
					}else{
						$("#preview").attr("src",System.rootPath+"/uploadfile/"+serverPath);
					}
				}else{
					var id = $("#"+ref).val();
					if(id != "" && !serverPath){
						var url = System.rootPath+"/lob/photo!getById.action?id="+id;
						if(dbtype == 60){
							if($.browser.msie){
								$("#preview_madia").empty().append("<embed id='preview' type='application/x-mplayer2' autostart='true'  src='"+url+"' width='90%' height='90%' loop='false'></embed>");
							}else{
								$("#preview_madia").empty().append("<video controls='controls' id='preview'  autoplay='true'  src='"+url+"' width='90%' height='90%' loop='false' />");
							}
						}else{
							$("#preview").attr("src",url);
						}
					}
				}
			}
		});
	},
	destoryUpload : function (flag) {	
		$("#fileDialog").data('flag',flag);
		$("#fileDialog").dialog('close');
	},
	ChageFile : function (sender,dbtype){
		var typeReg = /.jpg|.gif|.png|.bmp/i;
		if(dbtype == 60){
			typeReg = /.avi|.wmv|.rmvb|.mpg|.3gp|.mp4|.mpeg|.mp3|.swf/i;
		}
		if(!$(sender).val().match(typeReg)){ 
			$.messager.alert("错误提醒","格式无效!","error");
			return false; 
		}
		System.image.onUploadImgChange($(sender),dbtype);
	},
	onUploadImgChange :function (sender,dbtype){
		var clinetPath = "";
		if($.browser.mozilla){
			clinetPath = $(sender).get(0).files[0].getAsDataURL();
		}else{
			if($.browser.version == 6 || $.browser.safari){
				clinetPath=$(sender).val();
				//if($.browser.safari){
				//	$.messager.alert("提示信息","抱歉!! \n 本浏览器不支持本地图片预览,系统已经选择您所选择的图片提交后可以看到结果!");
				//}
			}else{
				//$(sender).show();
				$(sender).get(0).select();
				clinetPath= document.selection.createRange().text; 
				//$(sender).hide();
			}
		}
		System.image.previewFile(clinetPath,dbtype);
	},
	previewFile : function(clinetPath,dbtype){
		if(dbtype == 60){
			if($.browser.msie){
				$("#preview_madia").empty().append("<embed id='preview'  type='application/x-mplayer2' autostart='true'  src='"+clinetPath+"' width='90%' height='90%' loop='false'></embed>");
			}else{
				if($.browser.safari){
					$("#preview_madia").empty().append("<video controls='controls' id='preview' autoplay='true' width='90%' height='90%' loop='false' />");
					$.messager.alert("提示信息","抱歉!! \n 本浏览器不支持本地预览,系统已经选择您所选择的媒体文件提交后可以看到结果!");
				}else{
					$("#preview_madia").empty().append("<video controls='controls' id='preview' autoplay='true'  src='"+clinetPath+"' width='90%' height='90%' loop='false' />");
				}
			}
		}else{
			if($.browser.mozilla){
				$("#preview").attr("src",clinetPath);
			}else{
				if($.browser.safari){					
					$.messager.alert("提示信息","抱歉!! \n 本浏览器不支持本地图片预览,系统已经选择您所选择的图片提交后可以看到结果!");
					$("#preview").attr("src",System.rootPath+"/common/loading.gif");
				}else{
					$('#preview_fake').get(0).filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = clinetPath; 
					$('#preview_fake').css({"width":"90","height":"120"}); 
					$('#preview').hide();
					$('#preview_fake').show();
				}
			}
		}
		$("#preview").data("clinetPath",clinetPath);
		$("#preview").data("changed",true);
	},
	startUpload : function (formid){
		var changed = $("#preview").data("changed");
		if(changed == true){
			System.openLoadMask("#fileDialog", "正在上传...");
			$("#fileDialog").data("clientPath",$("#preview").data("clinetPath"));
			$("#browseBtn").linkbutton('disable');
			$("#btnOk").linkbutton('disable');
			$("#btnCancel").linkbutton('disable');
			$('#'+formid).submit();
			$("#preview").data("changed",false);
		}else{
			$.messager.alert('提示','请选择文件!','info');
		}
	},
	callback : function(serverPath){
		$("#fileDialog").data("serverPath",serverPath);
		System.image.destoryUpload(true);
		System.closeLoadMask("#fileDialog");
	}
};
System.leaderQuery = {
	showRemoval:function (personcode){
		var levelUrl = System.rootPath + "/removal/removal!showView.action?personcode="+personcode;
		//window.open(levelUrl);
		//returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=800px;dialogHeight=615px;status=no;resizable=yes");
		//System.openPopupWindow(levelUrl, '干部任免审批表', 1300, 700);
		window.open(levelUrl);
	},
	showRemoval2:function (personcode,packagename){
		var levelUrl = System.rootPath + "/subreceive/receive!showView.action?personcode="+personcode+"&packagename="+packagename;
		//window.open(levelUrl);
		//returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=800px;dialogHeight=590px;status=no;resizable=yes");
		System.openPopupWindow(levelUrl, '干部任免审批表', 800, 590);
	},
	editorRemoval:function(personcode){
		var levelUrl = System.rootPath + "/removal/removal!singleDetail.action?personcode="+personcode;
		//returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=800px;dialogHeight=590px;status=no;resizable=yes");
		System.openPopupWindow(levelUrl, '干部信息维护 - 任免表形式', 800, 615);
	},
	editorRemoval2:function(personcode){
		var levelUrl = System.rootPath + "/removal/removal!editorRemoval.action?personcode="+personcode;
		//returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=800px;dialogHeight=590px;status=no;resizable=yes");
		System.openPopupWindow(levelUrl, '干部信息维护 - 任免表形式', 1300, 640, null, null, 'removalEditor2', true);
	}
};

System.commonEditor = {
	editorSingePerson:function (personcode,callback){
		var levelUrl = System.rootPath + "/common/general!singleDetail.action?personcode="+personcode;
		
		/*
		var returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=900px;dialogHeight=590px;status=no;resizable=yes");
		if(typeof callback=="function"){
			callback(returnValue);
		}*/
		System.openPopupWindow(levelUrl, '干部信息维护', 1024, 640, null, callback);
	},
	
	editorSingePersonForMeeting:function (meetingId, personcode, callback){
		var levelUrl = System.rootPath + "/common/general!singleDetail.action?personcode="+personcode+"&isMeeting=1" + "&meetingId=" + meetingId;
		var returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=900px;dialogHeight=590px;status=no;resizable=yes");
		if(typeof callback=="function"){
			callback(returnValue);
		}
	}
};
System.commonView = {
	viewSingePerson:function (personcode,callback){
		var levelUrl = System.rootPath + "/common/general!singleDetailView.action?personcode="+personcode;
		var returnValue = window.showModalDialog(levelUrl,window,"dialogWidth=900px;dialogHeight=590px;status=no;resizable=yes");
		if(typeof callback=="function"){
			callback(returnValue);
		}
	}
};
System.person = {
	add : function (container,unitcode,unitname,callback){
		if (!unitcode) {
			$.messager.alert('提示', '请选择一个单位!');
			return;
		}
		
		if(container == undefined || container == null){
			container = "body";
		}
		if ($("#w").length == 0) {
			$(container).append("<div id=\"w\" style=\"padding:5px;\"></div>");
		}
		$("#w").dialog({
			modal:true,
			title:'新增人员',
			width: 640,
			height: 200,
			closed: true,
			href:System.rootPath+"/common/bus-addperson.jsp?unitcode="+unitcode+"&unitname="+encodeURI(encodeURI(unitname)),
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					if (!$("#addPersonForm").form('validate')) {
						$.messager.alert('提示', "请输入所有必填项!");
						return;
					}
					
					/*
					$('#addPersonForm').form('submit',{
				        url:System.rootPath+'/common/exec-common-method!addPerson.action',
				        onSubmit: function(){
							$("#sex").val($("#sexCode").combotree('getText'));
				            if($("#addPersonForm").form('validate')){
				            	System.openLoadMask($("#w"), "正在提交...");
				            	return true;
				            }else{
				            	return false;
				            }
				        },
				        success:function(entity){
				           System.closeLoadMask($("#w"));
				           callback(entity);
				           $("#w").dialog('close');
				           $("#w").dialog('destroy');
				           $("#w").remove();
				        }
					});
					*/
					$("#sex").val($("#sexCode").combotree('getText'));
					var sexCode = $("#sexCode").combotree('getValue');
					var params = "'unitcode':'"+$('#unitcode').val()+"','unitname':'"+$('#unitname').val()+"','xingm':'"+$('#xingm').val()+"','sexCode':'"+sexCode+"','sex':'"+$('#sex').val()+"','birthday':'"+$('#birthday').val()+"','idcard':'"+$('#idcard').val()+"'";
		            if($("#addPersonForm").form('validate')){
		            	System.openLoadMask($("#w"), "正在提交...");
		            	$.ajax({
		            		type:'post',
							url:System.rootPath+'/common/exec-common-method!addPerson.action',
							data:{'entityData':encodeURI(params)},
							success:function(entity){
		            		   callback(entity);
							   // 弹出排序提示，by YZQ on 2013/05/23
							   $.messager.alert('提示', '添加成功，请重新进行干部排序！');
					           $("#w").dialog('close');
					           $("#w").dialog('destroy');
					           $("#w").remove();
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
								$.messager.alert("网络错误","出现错误了(>_<)，稍后再试试!","error");
							},
							complete:function(XMLHttpRequest, textStatus){
								System.closeLoadMask($("#w")); 
							} 
						});
		            	return true;
		            }else{
		            	return false;
		            }
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$("#w").dialog('close');
					$("#w").dialog('destroy');
					$("#w").remove();
				}
			}],
			onLoad: function() {
				// fxxk, easyui 1.2.6 has a bug to initialize the combotree?? add a empty onLoad() function seems to can fix it??
			},
			onClose:function(){
				$("#w").dialog('destroy');
				$("#w").remove();
			}
		});
		
		$("#w").dialog('open');
	},
	orderList : function (unitcode,serviceid, callback){
		var orderUrl = System.rootPath + "/common/order!fastPersonalOrder.action?currentUnit="+unitcode+"&serviceid="+serviceid;
		//return  window.showModalDialog(orderUrl,window,"dialogWidth=800px;dialogHeight=560px");
		System.openPopupWindow(orderUrl, '人员排序', 800, 560, null, callback);
	},
	orderMutilRow2 : function (serviceid,personcode,groupkey,tablekey){
		var orderUrl = System.rootPath + "/common/common-mutilrow-order.jsp?serviceid="+serviceid+"&personcode="+personcode+"&groupkey="+groupkey+"&tablekey="+tablekey;
		return  window.showModalDialog(orderUrl,window,"dialogWidth=600px;dialogHeight=400px");
	},
	orderMutilRow :function (serviceid,personcode,groupkey,tablekey){
		var orderUrl = System.rootPath + "/common/common-mutilrow-order.jsp?serviceid="+serviceid+"&personcode="+personcode+"&groupkey="+groupkey+"&tablekey="+tablekey;
		if($('#w').length == 0){
			$("body").append("<div id=\"w\"></div>");
		}
		$("#w").window({
			shadow:true,
			modal :true,
			resizable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			width:600,
			height:400,
			title:'排序',
			content:"<iframe border='0' width='100%' height='100%' frameborder='0' src='"+orderUrl+"'></iframe>"
		});
	}
};
System.unit = {
	orderList : function (unitcode,serviceid, callback){
		var orderUrl = System.rootPath + "/common/order!fastUnitOrder.action?serviceid="+serviceid+"&currentUnit="+unitcode;
		//return  window.showModalDialog(orderUrl,window,"dialogWidth=800px;dialogHeight=560px");
		System.openPopupWindow(orderUrl, '单位排序', 800, 560, null, callback);
	}	
};
System.button = {
	isView : function(serviceid,currentUnit,callback){
		/*2012-01-07 暂时去
		$.ajax({
			url:System.rootPath+"/common/exec-common-method!isView.action",
			data:{'currentUnit':currentUnit,'serviceid':serviceid},
			success:function(back){
				
				if(back.isView){
					$("#btn_add").linkbutton("enable");
					$("#btn_remove").linkbutton("enable");
				}else{
					$("#btn_add").linkbutton("disable");
					$("#btn_remove").linkbutton("disable");
				}
				* /
				callback(back.isView);
			},
			complete :function(XMLHttpRequest, textStatus){
				//$.messager.alert("complete",XMLHttpRequest);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error",textStatus + errorThrown);
			}
		});
		*/
		//callback(false);
		/* 2012-4-14  添加*/
		$.get(System.rootPath+"/common/exec-common-method!isView.action",{'currentUnit':currentUnit,'serviceid':serviceid},
			function(back){callback(back.isView);});
	},
	editable:function(isView,allowOperator,currentUnit,unitcode){
		if(isView == 'true' || isView == true){
			return false;
		}else{
			var reg = new RegExp("^"+currentUnit+".*$");
			var notPartTime = reg.test(unitcode);
			if(notPartTime){
				return true;
			}else{
				if(allowOperator == 'true'){
					return true;
				}
			}
		}
		return false;
		//return true;
	},

	allowOperator : function(serviceid,currentUnit,personUnit,callback){
		if(currentUnit == personUnit){
			callback(true);
		}else{
			$.get(System.rootPath+"/common/exec-common-method!allowOperator.action",{'serviceid':serviceid},
					function(back){
						if(back.allowOperator){
							callback(true);
						}else{
							callback(false);
						}
					});
		}
	},

	allowOperator2:function(serviceid,currentUnit,callback){
		$.ajax({
			url:System.rootPath+"/common/exec-common-method!getEditUnits.action",
			data:{'serviceid':$("#serviceid").val()},
			success:function(back){
				var flag = false;
				$.each(back,function(i,v){
					if(v == currentUnit){
						flag = true;
						return false;// break;
					}
				});
				callback(flag);
			},
			complete :function(XMLHttpRequest, textStatus){
				//$.messager.alert("complete",XMLHttpRequest);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error",textStatus + errorThrown);
			}
		});
	}
};

// 授权相关, by YZQ on 2012/11/01
System.permission = {
	// 检查单位授权
	checkUnitPermission: function (unitcode, callback) {
		$.ajax({
			url:System.rootPath+"/security/access-right!checkUnitPermission.action",
			data: { 'unitcode': unitcode },
			success:function(flag){
				callback(flag);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	},
	
	// 检查单个信息集授权
	checkTablePermission: function (tableKey, callback) {
		$.ajax({
			url:System.rootPath+"/security/access-right!checkTablePermission.action",
			data: { 'tableKey': tableKey },
			success:function(flag){
				callback(flag);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	},
	
	// 检查多个信息集授权，信息集用逗号隔开
	checkTablesPermission: function (tableKey, callback) {
		$.ajax({
			url:System.rootPath+"/security/access-right!checkTablesPermission.action",
			data: { 'tableKey': tableKey },
			success:function(flag){
				callback(flag);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	},
	
	// 检查信息集授权 (个人或者单位信息集)
	checkPersonTablePermission: function (id, groupKey, tableKey, callback) {
		$.ajax({
			url:System.rootPath+"/security/access-right!checkPersonTablePermission.action",
			data: { 'id': id, 'groupKey': groupKey, 'tableKey' : tableKey },
			success:function(flag){
				callback(flag);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	},	
	
	// 检查干部是不是可以维护的
	checkIsEditablePersons: function (personcodes, callback) {
		$.ajax({
			url:System.rootPath+"/security/access-right!checkIsEditablePersons.action",
			data: { 'personcodes': personcodes },
			success:function(flag){
				callback(flag);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert("error", textStatus + errorThrown);
			}
		});
	}
};

function PageSelect(){
	this.pageNo;
	this.selecteds = [];
}

function SelectRow(){
	this.rowIndex;
	this.keywordId; 
	this.keywordcn;
}

function sysPrintReport(reportid, keys) {
	var currentUnit = $("#currentUnit").val();
	if (reportid == '-100' || reportid == '-1000') {
		// 获取当前选中单位
		try {
			if ($("#mc").length > 0) {
				var queryType = $("#mc").data("queryType");
				if (queryType == '1') {
					currentUnit = $("#currentUnit").val();
				}
			}
		}
		catch (e) {
			currentUnit = ''
		}
	}
	
	if (reportid == '-100') {
		var path = System.rootPath+"/report/report-print!printGeneralCardReport.action?keys="+keys + '&currentUnit=' + currentUnit;
		window.open(path, "输出干部名册");
	}
	else if (reportid == '-1000') {
		var path = System.rootPath+"/report/report-print!printGeneralCardWordReport.action?keys="+keys + '&currentUnit=' + currentUnit;
		window.open(path, "输出干部名册");
	}else if (reportid == '-999') {
		System.customMc.pickItems(function(items) {
			var path = System.rootPath+"/mingc/custom-mc!generateMc.action?keys="+keys+"&customMcItems="+items + '&currentUnit=' + currentUnit;
			window.open(path, '干部名册');
		});
	}
	else if(reportid == '-998'){
		var path = System.rootPath+"/subreceive/jpg!exportJpg.action?keys="+keys;
		window.open(path, '导出照片');
	}
	else {
	    var path = System.rootPath+"/report/report-print!reportPrint.action?rtid="+reportid+"&keys="+keys;
		window.open(path, "打印", 'height='+window.screen.availHeight+',width='+window.screen.availWidth+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	}
}

// Easy UI扩展
$.extend($.fn.layout.methods, {        
    /**
     * 设置某个region的宽度或者高度(不支持center)    
     * @param {[type]} jq     [description]    
     * @param {[type]} params [description]    
     */     
    setRegionSize:function(jq,params){      
        return jq.each(function(){      
            if(params.region=="center")      
                return;      
            var panel = $(this).layout('panel',params.region);      
            var optsOfPanel = panel.panel('options');      
            if(params.region=="north" || params.region=="south"){   
				optsOfPanel.height = params.value;      
            }else{      
                optsOfPanel.width = params.value;      
            }      
            $(this).layout('resize');      
        });      
    }
});

//自定义名册
System.customMc = {
	pickItems: function(callback) {
		if ($("#customMcDlg").length == 0) {
			$("body").append("<div id=\"customMcDlg\" style=\"padding:5px;background: #fafafa;\"></div>");
		}
		$("#customMcDlg").dialog({
			modal:true,
			title:'选择名册输出项目',
			width: 640,
			height: 480,
			closed: true,
			href: System.rootPath + "/mingc/custom-mc.action",
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var items = CustomMcItemPicker.getSelectedOptions();
					if (items == '') {
						System.showErrorMsg('请至少选择一个名册输出项目！');
						return;
					}
					
					$("#customMcDlg").dialog('close');
					callback(items);
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$("#customMcDlg").dialog('close');
				}
			}],
			onLoad: function() {
				// fxxk, easyui 1.2.6 has a bug to initialize the combotree?? add a empty onLoad() function seems to can fix it??
			}
		});
		
		$("#customMcDlg").dialog('open');
	}
};