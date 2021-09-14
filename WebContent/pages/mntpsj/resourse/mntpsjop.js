function openBGWQP(obj){
	
  $h.openWin('MNTPGWPZ','pages.mntpsj.MNTPGWPZ','模拟调配岗位配置',950,570,null,contextPath,null,
		  {fabd00:$('#fabd00').val()}
  ,true);
  
 
  
}



var B0131DECODE = {"1":"正职","3":"副职",'1001':'党委','1004':'政府','1003':'人大','1005':'政协','1006':'院长','1007':'检查长'};

function openBDFAPage(){
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择方案');
		return;
	}
	$h.openWin('MNTPlistsj','pages.mntpsj.MNTPSJDW','单位设置',900,570,null,contextPath,null,
		      {fabd00:fabd00},true);
}
//选择岗位
function openXZGWPage(obj){
	var famx00='',b0111='';
	if(obj){
		famx00 = obj.attr('famx00');
		b0111 = obj.attr('b0111');
	}
	
	
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择方案');
		return;
	}
	$h.openWin('MNTPXZGWPage','pages.mntpsj.MNTPSJXZGW','选择岗位',800,570,null,contextPath,null,
			{fabd00:fabd00,b0111:b0111,famx00:famx00},true);
}

function infoSearch(){
	myMask.show();
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择方案');
		return;
	}
	setTimeout(function(){radow.doEvent('showData');},50)
	
}

function infoSearchSyn(type,a0000){
	if('add'==type||'del'==type){
		var $tds = $('td[a0000="'+a0000+'"].name');
		$.each($tds, function (i,rowsData) {
			var synparm = getDWTRs($(rowsData).parent());
		    showDataSyn.putParm(synparm);
		    
		});
		return;
	}
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('请选择方案');
		return;
	}
	
	showDataSyn.getSynData();
}

var showDataSyn = (function(){
	//排序 序号大的在前面， 从后面开始替换， 否则前面被替换 会导致后面无法新增 行后插入的行被删除
	var compare = function (x, y) {//比较函数
	    if (x[2] < y[2]) {
	        return 1;
	    } else if (x[2] > y[2]) {
	        return -1;
	    } else {
	        return 0;
	    }
	};
	var parm1 = [];
	return {
		putParm : function(p){
			for(var x=0;x<parm1.length;x++){
				if(parm1[x][2]==p[2]){
					return;
				}
			}
			parm1.push(p);
		},
		clearParm : function(){
			parm1 = [];
		},
		getSynData : function(){
			//myMask.show();
			parm1.sort(compare);
			//请求单位块
			for(var x=0;x<parm1.length;x++){
				var p = parm1[x];
				radow.doEvent('showDataSyn',p[2]+"@@"+x);
			}
			
		},
		showDataSyn : function(tableData,index,tw){//更新单位块
			var html = getHTML(tableData,tw);
			var p = parm1[index];
			
			//console.log(parm1)
			//删除该快的行
			for(var ai=0;ai<p[0].length;ai++){
				p[0][ai].remove();
			}
			//新的插入
			$(html).insertAfter(p[1]);
			//最后绑定事件
			if(index==parm1.length-1){
				//绑定拖拽
				bindMove();
				//绑定按钮
				bindButton();
				//设置宽度和事件
				setTableWidth(false);
				
				bindEditor();
				this.clearParm();
				myMask.hide();//隐藏
			}
		}
	}
})();

function getHTML(tableData,tw){
	var html = '';
	$.each(tableData, function (i,rowsData) {
		html = html + '<tr>';
		$.each(rowsData, function (j,cellMap) {
			
			html = html + '<td '
			+(cellMap['rowspan']?'rowspan="'+cellMap['rowspan']+'"':' ')
			+(cellMap['colspan']?'colspan="'+cellMap['colspan']+'"':' ')
			+(cellMap['sclass']?'class="'+cellMap['sclass']+'"':' ')
			+(cellMap['style']?('style="'+cellMap['style']+(cellMap['tableWidth']?(cellMap['tableWidth']/tw*100+'%'):'')+   ';   "'):' ')
			+(cellMap['a0000']?'a0000="'+cellMap['a0000']+'"':' ')
			+(cellMap['a0200']?'a0200="'+cellMap['a0200']+'"':' ')
			+(cellMap['zwqc00']?'zwqc00="'+cellMap['zwqc00']+'"':' ')
			+(cellMap['dwIndex']?'dwIndex="'+cellMap['dwIndex']+'"':' ')
			+(cellMap['tpyjid']?'tpyjid="'+cellMap['tpyjid']+'"':' ')
			+(cellMap['gwName']?'gwName="'+cellMap['gwName']+'"':' ')
			+(cellMap['jgjc']?'jgjc="'+cellMap['jgjc']+'"':' ')
			+(cellMap['zgxl']?'zgxl="'+cellMap['zgxl']+'"':' ')
			+(cellMap['qrzxl']?'qrzxl="'+cellMap['qrzxl']+'"':' ')
			+(cellMap['famx01']?'famx01="'+cellMap['famx01']+'"':' ')
			+(cellMap['a0107']?'a0107="'+cellMap['a0107']+'"':' ')
			+(cellMap['b0111']?'b0111="'+cellMap['b0111']+'"':' ')
			+(cellMap['jlmdtext']?'jlmdtext="'+cellMap['jlmdtext']+'"':' ')
			+(cellMap['fxyp00_ry']?'fxyp00_ry="'+cellMap['fxyp00_ry']+'"':' ')
			+(cellMap['colname']?'colname="'+cellMap['colname']+'"':' ')
			+(cellMap['famx00']?'famx00="'+cellMap['famx00']+'"':' ')
			+(cellMap['quxiangb0111s']?'quxiangb0111s="'+cellMap['quxiangb0111s']+'"':' ')
			+(cellMap['laiyuanb0111']?'laiyuanb0111="'+cellMap['laiyuanb0111']+'"':' ')
			+(cellMap['gwmc']?'gwmc="'+cellMap['gwmc']+'"':' ')
			+(cellMap['fxyp00']?'fxyp00="'+cellMap['fxyp00']+'"':' ')
			+(cellMap['personStatus']?'personStatus="'+cellMap['personStatus']+'"':' ')
			+(cellMap['qx']?'qx="'+cellMap['qx']+'"':' ')
			+'>'+cellMap['text']+'</td>';
			
			if(cellMap['tpyjid']){
				if(GLOBLE['ID_ROWINFO'][cellMap['tpyjid']]){
					GLOBLE['ID_ROWINFO'][cellMap['tpyjid']][cellMap['colname']]=cellMap['text'];
				}else{
					GLOBLE['ID_ROWINFO'][cellMap['tpyjid']]={'tpyjid':cellMap['tpyjid']};
					GLOBLE['ID_ROWINFO'][cellMap['tpyjid']][cellMap['colname']]=cellMap['text'];
				}
				
			}
			
		})
		html = html + '</tr>\n';
	});
	return html;
}

function showData(tableData,tw){
	var html = getHTML(tableData,tw);
	$('#coordTable').html(html);
	
	//绑定拖拽
	bindMove();
	//绑定按钮
	bindButton();
	//设置宽度和事件
	setTableWidth(tw);
	bindEditor();
	myMask.hide();//隐藏
}
//获取单位块的tr
function getDWTRs($tr){
	//console.log($tr.html())
	var trs = [];//需要删除的tr
	var $insertTR = $tr;
	var $dwTD;
	while($insertTR.length>0){
		trs.push($insertTR);
		//单位第一行第一个必须加入 dwIndex属性，表示所在行为第几行，单位中没有数据，合并的空白也要加
		if($insertTR.children(":first").hasClass('classBT')){
			$dwTD = $insertTR.children(":first");
			
			//单位上面一行作为插入点
			$insertTR = $insertTR.prev();
			//单位上面一行加了标题，要在往上面加一个
			//trs.push($insertTR);
			//$insertTR = $insertTR.prev();
			break;
		}
		
		$insertTR = $insertTR.prev();
	}
	
	while($tr.length>0){
		$tr = $tr.next();
		if($tr.children(":first").hasClass('classBT')){
			break;
		}
		trs.push($tr);
	}
	//console.log($dwTD.html())
	return [trs,$insertTR,$dwTD.attr('dwIndex'),$dwTD];
}

function bindMove(){
	var tdMove = $('#tdMove'),px=15;//浮动块偏移量，不偏移鼠标移动过程会出现在这个div移动的情况，移动到td上无法响应高亮样式
	$('td.moveFrom').unbind('mousedown').mousedown(function (e) {
	    $(this).addClass('mselected');
	    showDataSyn.clearParm();
	    tdMove.html(this.innerHTML).css({ left: e.clientX + px, top: e.clientY + px }).show();
	    //tdMove.css({width:$(this).width(),height:$(this).height()})
	    $(document).unbind('mousemove').mousemove(function (e) {
	        var el = e.target;
	        tdMove.css({ left: e.clientX + px, top: e.clientY + px });
	        if ( $(el).hasClass("moveTo") ) {
	            $('td.moveTo').removeClass('mhover'); $(el).addClass('mhover');
	        }else{
	        	$('td.moveTo').removeClass('mhover');
	        }
	    }).unbind('mouseup').mouseup(function () {
	        $(document).unbind();

	        tdMove.hide();
	        
	        
	        
	        
	        
	        var a0000 = $('td.mselected').attr('a0000');
	        var a0200 = $('td.mselected').attr('a0200');
	        var fxyp00 = $('td.mhover').attr('fxyp00');
	        
	        
	        //判断人选是否重复添加
	        var td = $('td.mhover,td.mselected');
	        var hasA0000 = false;
	        if(fxyp00){
		        var $moveNext = $('td.mhover').next();
	        	var famx00 = $moveNext.attr('famx00');
	        	var $a0000s = $("[famx00="+famx00+"][a0000]");
	        	$.each($a0000s, function (i,rowsData) {
	        		if($(this).attr('a0000')==a0000){
	        			hasA0000 = true;
	        		}
	        	});
	        	
	        	var synparm2 = getDWTRs($('td.mhover').parent());
	        	showDataSyn.putParm(synparm2);
	        	var zwqc00 = $('td.mhover').attr("zwqc00");
	        	if(zwqc00){
	        		$.each($('td[zwqc00="'+zwqc00+'"]'), function (i2,rowsData2) {
						var synparm = getDWTRs($(rowsData2).parent());
					    showDataSyn.putParm(synparm);
					});
	        	}
			    
	        }
	        td.removeClass('mhover mselected');
	       
	        if(fxyp00&&hasA0000){
	        	$h.confirm('系统提示', '方案已有该人选，是否继续添加人选？', '400', function(id) {
    				if (id == 'ok') {
    					doAddPerson.queryByNameAndIDS(a0000,fxyp00,a0200);
    				} else if (id == 'cancel') {
    					showDataSyn.clearParm();
    					return;
    				}
    			});
	        }else if(fxyp00){
	        	doAddPerson.queryByNameAndIDS(a0000,fxyp00,a0200);
	        }else{
	        	showDataSyn.clearParm();
	        }
	        
	    });
	})
}


//设置调配说明
function setTPSM(famx00,b0111,html){
	var o = null;
	o = $('.tpsm[famx00="'+famx00+'"][b0111="'+b0111+'"]');
	o.html(html);
	//alert(o.attr('a0000'))
}


function getTabArray() {
	var tableObj = document.getElementById('coordTable');
	var maxrow=tableObj.rows.length;
	var maxcell=0;
	for (var i = 0; i <maxrow; i++) { 
		var maxcellx=tableObj.rows[i].cells.length;
		for(var j=0;j<tableObj.rows[i].cells.length;j++){
			var displays=tableObj.rows[i].cells[j].style.display;//多少列
		    if(displays=="none"){
			   maxcellx=maxcellx-1;
		    }
		    
		}
		if(maxcellx>maxcell){
			maxcell=maxcellx;
		}
	} 
    var data = [];
    if ($("#coordTable tr").length == 0) {
        return data;
    }
    for(var n=0;n<maxrow;n++){
    	var arr=[];
		for(var m=0;m<maxcell;m++){
			if(m==0){
				arr.push({'text':'','rowHeight':$(tableObj.rows[n]).height()});
			}else{
				arr.push({'text':''});
			}
    		
    	}
    	data.push(arr);
    }
    //填充数组
    var ii = 0; var tdtext = "";
    var tdrowspan=null,tdcolspan=null,tdcolspan2=null;
    $("#coordTable tr").each(function () {
        var jj=0;
        $(this).children('th,td').each(function () {
        	if($(this).css('display')!="none"){
	        	while(data[ii][jj].text=='#x$'){
	        		jj=jj+1;
	        	}
	        	tdrowspan = $(this).attr("rowspan");
	        	tdcolspan = $(this).attr("colspan");
	        	tdtext = $(this).clone()    //复制元素
	            .find('div,button') //获取所有子元素
	            .remove()   //删除所有子元素
	            .end()  //回到选择的元素
	            .html().replace('<br>','\n');//获取文本值
	        	
	        	//单位合并  按照第一个单位合并
	        	tdcolspan2 = tdcolspan;
	        	if($(this).hasClass("unit")){
	        		if($(this).index()==0){
	        			tdcolspan2 = maxcell;
	        		}
	        	}
	            Ext.apply(data[ii][jj], {'text':$('<div>'+tdtext+'</div>').text().trim(),'rowspan':tdrowspan
	            	, 'colspan':tdcolspan2,'color':$(this).css("background-color") 
	            	,'fontfamily':$(this).css('font-family'),'fontsize':$(this).css('font-size')
	            	,'textalign':$(this).css('text-align')});
	            
	            if(tdrowspan&&tdrowspan!=1){
	            	for(var rows=0;rows<tdrowspan;rows++){
	                    if (tdcolspan&&tdcolspan!=1) {
	                    	for (var cells = 0;cells< parseInt(tdcolspan);cells++) {
	                    		if(rows!=0||cells!=0){
	                    			Ext.apply(data[ii+rows][jj+cells],{'text':'#x$'});
	                    		}
	                         }
	                	}else{
	                		if(rows!=0){
	                			Ext.apply(data[ii+rows][jj],{'text':'#x$'});
	                    	}
	                		Ext.apply(data[ii+rows][jj],{'colWidth':parseInt($(this).width())});
	                	}  
	            	}
	
	            }else{
	                if (tdcolspan&&tdcolspan!=1) {
	                     for (var cells = 1;cells< parseInt(tdcolspan);cells++) {
	                    	 Ext.apply(data[ii][jj+cells],{'text':'#x$'});
	                      }
	             		
	                }else{
	                	Ext.apply(data[ii][jj],{'colWidth':parseInt($(this).width())});
	                }  
	            	
	            }
	            if (tdcolspan) {
	            	jj=jj+parseInt(tdcolspan)-1;
	            }  
	            //alert(jj);
				jj=jj+1;
			}
        });
        ii=ii+1;
    });
    //console.log(data)
    //console.log(Ext.encode(data))
	 if(data.length>0){
		  ajaxSubmit('expExceldata',{"data":Ext.encode(data),"excelname":"对照表"});
	}
}

function ajaxSubmit(radowEvent,parm,callback){
	  if(parm){
	  }else{
	    parm = {};
	  }
	  Ext.Ajax.request({
	    method: 'POST',
	    //form:'rmbform',
	        async: true,
	        params : parm,
	        timeout :300000,//按毫秒计算
	    url: contextPath+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.hzb.ExpExcel&eventNames="+radowEvent,
	    success: function(resData){
	      var cfg = Ext.util.JSON.decode(resData.responseText);
	      //alert(cfg.messageCode)
	      if(0==cfg.messageCode){
	                Ext.Msg.hide();

	                if(cfg.elementsScript.indexOf("\n")>0){
	          cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
	          cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
	        }

	        //console.log(cfg.elementsScript);

	        eval(cfg.elementsScript);

	        if("操作成功！"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('系统提示:',cfg.mainMessage);

	        }else{
//	           Ext.Msg.hide();
	        }
	      }else{
	        //Ext.Msg.hide();

	        /* if(cfg.mainMessage.indexOf("<br/>")>0){

	          $h.alert('系统提示',cfg.mainMessage,null,380);
	          return;
	        } */

	        if("操作成功！"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('系统提示:',cfg.mainMessage);
	        }else{
	          Ext.Msg.hide();
	        }
	      }
	      if(!!callback){
	        callback();
	      }
	    },
	    failure : function(res, options){
	      Ext.Msg.hide();
	      alert("网络异常！");
	    }
	  });
	}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src',contextPath + '/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}




function openRenXuanTiaoJian(fxyp00,t){
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
	
	if(!newWin_){
		newWin_ = $h.openWin('rxtjs','pages.mntpsj.RenXuanTiaoJian','',1370,720,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||"",queryType:t},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_rxtjs").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.queryType=(t||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('ygrxs');
	if(newWin_){
		newWin_.hide(); 
	}
}

//以前老的组合查询
function openRenXuanTiaoJian2(fxyp00){
	var t = "1";
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtjs2');
	
	if(!newWin_){
		newWin_ = $h.openWin('rxtjs2','pages.mntpsj.RenXuanTiaoJian2','',1370,720,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||"",queryType:t},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_rxtjs2").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.queryType=(t||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('ygrxs');
	if(newWin_){
		newWin_.hide(); 
	}
}

function openYouGuanRenXuann(fxyp00,t){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrxs');
	if(!newWin_){
		newWin_ = $h.openWin('ygrxs','pages.mntpsj.YouGuanRenXuan','有关人选名单 ',1370,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_ygrxs").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.queryType=(t||"");
		subwindow.reload();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
	if(newWin_){
		newWin_.hide(); 
	}
}

function hidewin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
	if(newWin_){
		newWin_.hide(); 
	}
	newWin_ = $h.getTopParent().Ext.getCmp('ygrxs');
	if(newWin_){
		newWin_.hide(); 
	}
}
function closeWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrxs');
	if(newWin_){
		newWin_.destroy();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtjs');
	if(newWin_){
		newWin_.destroy();
	}
	//刷新表格
}
var doAddPerson = (function(){//增加人员
	return {
		
		queryByNameAndIDS:function(a0000s,fxyp00,a0200s){//放入暂存区
			$('#a0000s').val(a0000s);
			$('#a0200s').val(a0200s);
			$('#fxyp00').val(fxyp00);
			radow.doEvent('queryByNameAndIDS',fxyp00);
		}
	}
})();




//选择去向岗位
function openQuXiangDanWei(){
	var a0200 = $('.button-div').attr('a0200');
	var b0111 = $('.button-div').attr('qx');
	var famx00 = $('#selfamx00').val();
	var a0000 = $('.button-div').attr('a0000');
	
	if(a0000==''){
		Ext.example.msg('','现状岗位无人员信息。',1);
		return;
	}
	$h.openWin('QXDW','pages.mntpsj.QXDW','选择去向岗位',750,570,null,contextPath,null,
			{fabd00:$('#fabd00').val(),a0000:a0000,b0111:b0111,famx00:famx00,a0200:a0200},true);
	/*$h.openWin('MNTPXZGWPage','pages.mntpsj.MNTPSJXZGW','选择岗位',900,570,null,contextPath,null,
			{fabd00:fabd00,b0111:b0111,famx00:famx00},true);*/
}
//设置留任
function setLiuRen(){
	/* $h.confirm('系统提示', '若在其它单位有该人选，该人选将被取消，点击【确定】按钮继续，点击【取消】取消本次操作！', '400', function(id) {
		if (id == 'ok') {
			doAddPerson.queryByNameAndIDS(a0000,fxyp00,a0200);
		} else if (id == 'cancel') {
			return;
		}
	}); */
	var a0200 = $('.button-div').attr('a0200');
	var b0111 = $('.button-div').attr('qx');
	var famx00 = $('.button-div').attr('famx00');
	var a0000 = $('.button-div').attr('a0000');
	
	if(a0000==''){
		Ext.example.msg('','现状岗位无人员信息。',1);
		return;
	}
	$('#a0000s').val(a0000);
	$('#a0200s').val(a0200);
	$('#b0111').val(b0111);
	radow.doEvent('setLiuRen',a0200);
}
//设置免职
function setMianZhi(){
	var a0200 = $('.button-div').attr('a0200');
	var b0111 = $('.button-div').attr('qx');
	var famx00 = $('.button-div').attr('famx00');
	var a0000 = $('.button-div').attr('a0000');
	
	if(a0000==''){
		Ext.example.msg('','现状岗位无人员信息。',1);
		return;
	}
	$('#a0000s').val(a0000);
	$('#a0200s').val(a0200);
	$('#b0111').val(b0111);
	radow.doEvent('setMianZhi',a0200);
}


function bindButton(){
	var btndiv = $('.button-div');
	var btntpyj = $('.button-tpyj');
	$('.button-tpyj-over').unbind('mouseenter').mouseenter(function(e) {
		
		var el = e.target;
		
		var ps = $(this).position();
		btndiv.attr('a0200',$(el).attr('a0200'));
		if($(el).attr('a0000')){
			btndiv.attr('a0000',$(el).attr('a0000'));
		}else{
			btndiv.attr('a0000','');
			return;
		}
		
		btndiv.attr('qx',$(el).attr('qx'));
		btndiv.attr('famx00',$(el).attr('famx00'));
		//btndiv.height($(el).height()+4);
		//btntpyj.css('margin-top',($(el).height()-24)/2)
		//btndiv.show().css({ left: ps.left-50, top: ps.top });
		btndiv.show().css({ left: ps.left+$(el).width()-15, top: ps.top });
		
		
	}).unbind('mouseleave').mouseleave(function(e) {
		var el = e.target;
		var toobj = document.elementFromPoint(e.clientX,e.clientY);
		
		//alert(toel.indexOf('button-div')+":"+toel.indexOf('button-tpyj'))
		if($(toobj).hasClass('button-div')||$(toobj).hasClass('button-tpyj')){
			
			return;
		}
		
		$('.button-div').hide();
	});
	
	$('.button-div').unbind('mouseleave').mouseleave(function(e) {
		var el = e.target;
		var toobj = document.elementFromPoint(e.clientX,e.clientY);
		
		var toel = toobj.className;
		//alert(toel)
		$('.button-div').hide();
	});
	
	
	
	
	$('.delIcon,.delIconGW').unbind('mouseenter').mouseenter(function() {
		$('a',this).show();
		
		if($('input',this).length>0){
			$('input',this).show();
		}
		if($('img',this).length>0){
			$('img',this).show();
		}
		
	}).unbind('mouseleave').mouseleave(function() {
		$('a',this).hide();
		if($('input',this).length>0&&!$('input',this).is(':checked')){
			$('input',this).hide();
		}
		if($('img',this).length>0){
			$('img',this).hide();
		}
	});
	
	var nameRepeat = {}
	/* background-color:#c1cedc; */
	/* color: white; */
	//判断重复 姓名重复的加背景颜色和class
	$('.tableTile[famx01="2"]').each(function(){
		var famx00 = $(this).attr('famx00');
		nameRepeat[famx00] = {};
		$('.delIcon[famx00='+famx00+']').each(function(i,item){
			var itm2 = nameRepeat[famx00][$(item).attr('a0000')];
			if(itm2){
				
				//岗位联动的人员  实际存在
				if($(item).attr('fxyp00')==$(item).attr('fxyp00_ry')){
					/*$(item).addClass('nameRepeat'+famx00);
					$(item).css({"background-color":"rgb(73, 146, 219)","color":"white"});
					if(!itm2[0].hasClass("nameRepeat"+famx00)){
						itm2[0].addClass('nameRepeat'+famx00);
						itm2[0].css({"background-color":"rgb(73, 146, 219)","color":"white"});
					}*/
					$('.delIcon[a0000="'+$(item).attr('a0000')+'"][famx00="'+famx00+'"]').each(function(x,itm3){
						if(!$(itm3).hasClass("nameRepeat"+famx00)){
							$(itm3).addClass('nameRepeat'+famx00);
							$(itm3).css({"background-color":"rgb(73, 146, 219)","color":"white"});
						}
					})
				}
				
				
			}else{
				//岗位联动的人员  实际存在
				if($(item).attr('fxyp00')==$(item).attr('fxyp00_ry')){
					
					var rgb = Math.round(Math.random()*79)+1;
					nameRepeat[famx00][$(item).attr('a0000')] = [$(item),rgb];
				}
				
			}
		});
		
		//将重复的名字获取岗位和单位
		var tdMove = $('#tdTipMove'),px=15;
		$('.nameRepeat'+famx00).unbind('mousemove').mousemove(function (e) {
			$('h4',tdTipMove).html('其他岗位提示：');
	        var el = e.target;
	        if(el.tagName=='A'||el.tagName=='INPUT'){
	        	return;
	        }
	        tdMove.css({ left: e.clientX + px, top: e.clientY + px });
	        tdMove.show();
	        var a0000 = $(el).attr('a0000');
	        var htmlStr = ""
	        var index=1;
	        var np = $('td[a0000="'+a0000+'"].nameRepeat'+famx00).each(function(i,item){
	        	if(item!=el){
	               	//<p><strong>11</strong> <span>请注意你的个人隐私安全.请注意你的个人隐私安全.请注意你的个人隐私安全.请注意你的个人隐私安全. </span></p>
	        		htmlStr+='<p>'+(index++)+'、 <span>'+$(item).attr('jgjc')+'</span>：<strong>'+$(item).attr('gwName')+'</strong></p>';
	        	}
	        });
	        $(".RenGangXX").html(htmlStr);
	    }).unbind('mouseout').mouseout(function () {

	        tdMove.hide();
	    });
		
		
		//交流提示
		$('.jlmd').unbind('mousemove').mousemove(function (e) {
			$('h4',tdTipMove).html('交流提示：');
	        var el = e.target;
	        tdMove.css({ left: e.clientX + px, top: e.clientY + px });
	        tdMove.show();
	        var jlmdtext = $(el).attr('jlmdtext');
	        $(".RenGangXX").html('<p>'+jlmdtext+'</p>');
	    }).unbind('mouseout').mouseout(function () {

	        tdMove.hide();
	    });
		
		
	});
	
	
	
	
	
}



function rybd(){
	var a0000s='';
	$('input:checkbox[name=a01a0000]:checked').each(function(i){
		a0000s = a0000s + $(this).parent().attr('a0000') + ',';
     });
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000rybd").val(a0000s);
	radow.doEvent('tpbj.onclick');
}

function clearSelected() {
	$('input:checkbox[name=a01a0000]:checked').prop('checked', false).hide();
   $('#a0000rybd').val(''); 
}



/*****编辑域**************************************************/
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}
function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0")?" ":v.replace(/\n/g,"<br/>"));
	}
$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}

function GUID() {
  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
    return v.toString(16);
  });
}


function bindEditor(){
	$(".input-editor").unbind('click').bind('click', function (event) {
		var td = $(this);
		var tagName = td.get(0).tagName;
		if (td.children("textarea").length > 0) {
            return false;
        }
		//行号 zoulei
        var rowIndex = td.parent().index();
        //列号zoulei
        var colIndex = td.index();
		var text = GRYP.getValue(rowIndex,colIndex,td);
		//加上padding乘以2
		var width = td.width() +6*2;
        var height = td.height()+8*2;
        if(tagName=="DIV"){
        	width = td.width();
            height = td.height();
        }
        $('.button-div').hide();
      //建立文本框，也就是input的节点   
       var ps = td.position();
        var div = $('<div style="position:absolute;top:-1px;left:-2px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //将文本内容加入td   
        td.append(div);
        //设置文本框值，即保存的文本内容   
        input.attr('value', text);
        input.css('border', "0px");
        input.css('text-align', "left");
        //作为jquery选择的标志
        input.addClass('txt_editer');
        if(tagName=="SPAN"){
        	input.css('overflow', "hidden");
        	input.css('border', "solid 1px #000000");
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei 输入框点击位置如果是自己，就保留输入框。防止点到输入框空白位置也会触发该事件
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//更新数据对象
            GRYP.setValue(td, $(this).val(),rowIndex,colIndex);
            
        });
        input.trigger("focus").focusEnd();
	});
}


var GLOBLE = {};
//根据id存储行信息
GLOBLE['ID_ROWINFO']={};
//根据行号获取rowid

var GRYP = (function(){
	return {
		getValue:function(rowIndex,colIndex,$td){
    		var colName = $td.attr('colname');
    		var tpyjid = $td.attr('tpyjid');
    		
    		if(!tpyjid){
    			tpyjid = GUID();
    			$td.attr('tpyjid',tpyjid);
    			GLOBLE['ID_ROWINFO'][tpyjid]={colName:"",tpyjid:tpyjid,updatetype:"insert"}
    			return "";
    		}
    		var text = GLOBLE['ID_ROWINFO'][tpyjid][colName];
    		return text==null?"":text;
    		
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		
    		//更新数据对象
    		var colName = obj.attr('colname');
    		var tpyjid = obj.attr('tpyjid');
    		
    		SetTDtext(obj,value);
    		GLOBLE['ID_ROWINFO'][tpyjid][colName]=value;
    		
    		GLOBLE['ID_ROWINFO'][tpyjid]['famx00']=obj.attr('famx00');
    		GLOBLE['ID_ROWINFO'][tpyjid]['a0200']=obj.attr('a0200');
    		GLOBLE['ID_ROWINFO'][tpyjid]['colname']=obj.attr('colname');
    		//更新后台
    		var tpyjInfoJSON = Ext.encode(GLOBLE['ID_ROWINFO'][tpyjid]);
			$('#tpyjInfoJSON').val(tpyjInfoJSON);
    		radow.doEvent('updateTPYJ');
    		
    		var mntp05 = $('#mntp05').val();
    		var fabd05 = $('#fabd05').val();
    		if("4"==mntp05&&"1"==fabd05){
    			//干部三处总体统计
    			//addGBSCTJ();
    		}
    	}
		
	}
})();
/******************************************************************************/

function openMate(obj){
	if(obj){
		var famx00 = obj.attr('famx00');
		var b0111 = "";
		if(obj.attr('qx')){
			b0111 = obj.attr('qx');
		}
		$h.openPageModeWin('MNTPSJGWYL','pages.mntpsj.MNTPSJGWYL','人员列表',1350,680,
				{fabd00:$('#fabd00').val(),famx00:famx00,b0111:b0111},contextPath);
	}else{
		$h.openPageModeWin('MNTPSJGWYL','pages.mntpsj.MNTPSJGWYL','人员列表',1350,680,
				{fabd00:$('#fabd00').val(),famx00:'',b0111:''},contextPath);
	}
	
}

/**
 * 选择单位
 * @param obj
 * @returns
 */
function openXZDW(obj){
	if(obj){
		var famx00 = obj.attr('famx00');
		var famx01 = obj.attr('famx01');
		$h.openPageModeWin('choosedwsj','pages.mntpsj.CHOOSEdw','部门选择',530,550,{famx00:famx00,famx01:famx01},contextPath);
	}
	
}
function reload(){
	infoSearch();
}




/************单位调配干部三处的总体统计******************************************************************/
function addGBSCTJ(_$td){//没去重
	var famx00 = _$td.attr('famx00');
	var fmt = function(num){
		var $num = $(num);
		var n = parseInt($num.text())
		if(n<0){
			return '减少了' + '<span style="color:#FF4500">'+-n+'</span>';
		}else{
			return '增加了' + '<span style="color:#FF4500">'+n+'</span>';
		}
	};
	
	var html_TJSTR = '';
	//统计人员
	//获取所有现状 需要统计的人  现状 有人 国企高校
	var tjrx = $("[famx01='1'][a0000][qx^='001.001.003']");
	//统计结果       [0退出领导岗位, 1提拔xx人, 2（45岁及以下xx人）, 3本单位提拔, 4国企交流提拔, 5市直单位提拔, 6区、县（市）提拔]
	//		  [7平职交流, 8其中市属国企交流, 9市直单位交流, 10区、县（市）交流xx人, 11单位内部轮岗 ]
	//4 平均年龄xx.xx岁，降低了xx.xx岁。45岁以下领导班子成员xx人（占比xx.xx%），增加了xx人（ 比例提高了xx.xx%）。
	//[12 现状平均年龄xx.xx岁, 13降低了xx.xx岁, 14 45岁以下现状领导班子成员xx人, 15 45岁以下现状（占比xx.xx%）, 16 45岁以下增加了xx人, 17 45岁以下（ 比例提高了xx.xx%）]
	//[ 18现状总年龄, 19调配总年龄, 20 45岁以下调配领导班子成员xx人, 21 调配平均年龄xx.xx岁, 22 45岁以下调配（占比xx.xx%）]
	
	//6 大学以上学历xx人，增加（减少）了x人；硕士研究生以上学历xx人，增加（减少）了x人；博士研究生xx人，增加（减少）了x人。
	//[23 现状大学以上学历xx人,24 调配大学以上学历xx人,25增加（减少）了x人, 26现状硕士研究生以上学历xx人, 27调配硕士研究生以上学历xx人, 28增加（减少）了x人  ]
	//[29现状博士研究生xx人, 30调配博士研究生xx人, 31增加（减少）了x人]
	
	//7全日制大学以上学历xx人，增加（减少）了x人；全日制硕士研究生以上学历xx人，增加（减少）了x人；全日制博士研究生xx人，增加（减少）了x人。
	//[32 现状全日制大学以上学历xx人,33 调配全日制大学以上学历xx人,34增加（减少）了x人, 35现状全日制硕士研究生以上学历xx人, 36调配全日制硕士研究生以上学历xx人, 37增加（减少）了x人  ]
	//[38现状全日制博士研究生xx人, 39调配全日制博士研究生xx人, 40增加（减少）了x人]
	
	var tjjg = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	
	tjrx.each(function(i,td){
		//出生年月  根据出生年月获取年龄
		var a0107 = $(td).attr('a0107');
		
		var age = 0;
		if(a0107){
			age = getAgeRender(a0107.replace('.','')+"01");
		}
		
		//45岁以下现状领导班子成员xx人  14
		if(age<=45){
			tjjg[14]++;
		}
		//18现状总年龄
		tjjg[18] += age;
		
		if($(td).text().indexOf('退出领导岗位')>=0){
			tjjg[0]++;
		}
		
		
		/*//本单位id
		var qx = $(td).attr('qx')||'';
		//去向id ￥001.001.003.006￥￥001.001.002.01T￥
		var quxiangb0111s = $(td).attr('quxiangb0111s')||'';
		if($(td).text().indexOf('提拔')>=0){
			tjjg[1]++;
			//45岁及以下xx人
			if(age<=45){
				tjjg[2]++;
			}
			if(quxiangb0111s==""){//3本单位提拔
				tjjg[3]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.003')>=0){//4国企交流提拔
				tjjg[4]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.002')>=0){//5市直单位提拔
				tjjg[5]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.004')>=0){//6区、县（市）提拔
				tjjg[6]++;
			}
			
		}
		
		if($(td).text().indexOf('交流')>=0){
			tjjg[7]++;
			if(quxiangb0111s==""){//11单位内部轮岗
				tjjg[11]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.003')>=0){//8其中市属国企交流
				tjjg[8]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.002')>=0){//9市直单位交流
				tjjg[9]++;
			}
			if(quxiangb0111s.indexOf('￥001.001.004')>=0){//10区、县（市）交流
				tjjg[10]++;
			}
			
		}*/
		
		//学历学位统计
		var zgxl = $(td).attr('zgxl')||'';
		var qrzxl = $(td).attr('qrzxl')||'';
		//23 现状大学以上学历xx人
		if(zgxl.indexOf('大学')>=0||zgxl.indexOf('研究生')>=0){
			tjjg[23]++;
		}
		//26现状硕士研究生以上学历xx人
		if(zgxl.indexOf('硕士')>=0){
			tjjg[26]++;
		}
		//29现状博士研究生xx人
		if(zgxl.indexOf('博士')>=0){
			tjjg[29]++;
		}
		
		//32 现状全日制大学以上学历xx人
		if(qrzxl.indexOf('大学')>=0||qrzxl.indexOf('研究生')>=0){
			tjjg[32]++;
		}
		//35现状全日制硕士研究生以上学历xx人
		if(qrzxl.indexOf('硕士')>=0){
			tjjg[35]++;
		}
		//38现状全日制博士研究生xx人
		if(qrzxl.indexOf('博士')>=0){
			tjjg[38]++;
		}
	});
	var xzry = tjrx.length;
	//12 现状平均年龄xx.xx岁
	tjjg[12] = (tjjg[18]/xzry).toFixed(2);
	//15 45岁以下现状（占比xx.xx%）
	tjjg[15] = (tjjg[14]/xzry*100).toFixed(2);
	
	//统计结果       [0退出领导岗位, 1提拔xx人, 2（45岁及以下xx人）, 3本单位提拔, 4国企交流提拔, 5市直单位提拔, 6区、县（市）提拔]
	//		  [7平职交流, 8其中市属国企交流, 9市直单位交流, 10区、县（市）交流xx人, 11单位内部轮岗 ]
	//4 平均年龄xx.xx岁，降低了xx.xx岁。45岁以下领导班子成员xx人（占比xx.xx%），增加了xx人（ 比例提高了xx.xx%）。
	//[12 现状平均年龄xx.xx岁, 13降低了xx.xx岁, 14 45岁以下现状领导班子成员xx人, 15 45岁以下现状（占比xx.xx%）, 16 45岁以下增加了xx人, 17 45岁以下（ 比例提高了xx.xx%）]
	//[ 18现状总年龄, 19调配总年龄, 20 45岁以下调配领导班子成员xx人, 21 调配平均年龄xx.xx岁, 22 45岁以下调配（占比xx.xx%）]
	
	//6 大学以上学历xx人，增加（减少）了x人；硕士研究生以上学历xx人，增加（减少）了x人；博士研究生xx人，增加（减少）了x人。
	//[23 现状大学以上学历xx人,24 调配大学以上学历xx人,25增加（减少）了x人, 26现状硕士研究生以上学历xx人, 27调配硕士研究生以上学历xx人, 28增加（减少）了x人  ]
	//[29现状博士研究生xx人, 30调配博士研究生xx人, 31增加（减少）了x人]
	
	//7全日制大学以上学历xx人，增加（减少）了x人；全日制硕士研究生以上学历xx人，增加（减少）了x人；全日制博士研究生xx人，增加（减少）了x人。
	//[32 现状全日制大学以上学历xx人,33 调配全日制大学以上学历xx人,34增加（减少）了x人, 35现状全日制硕士研究生以上学历xx人, 36调配全日制硕士研究生以上学历xx人, 37增加（减少）了x人  ]
	//[38现状全日制博士研究生xx人, 39调配全日制博士研究生xx人, 40增加（减少）了x人]
	
	//获取所有调配 需要统计的人  现状 有人 国企高校 调配之后到外单位的不需要统计
	tjrx = $("[famx01='2'][famx00='"+famx00+"'][a0000][qx^='001.001.003']");
	
	tjrx.each(function(i,td){
		
		//出生年月  根据出生年月获取年龄
		var a0107 = $(td).attr('a0107');
		var age = 0;
		if(a0107){
			age = getAgeRender(a0107.replace('.','')+"01");
		}
		
		
		//调配单位id  既是去向id
		var qx = $(td).attr('qx')||'';
		//去向id ￥001.001.003.006￥￥001.001.002.01T￥
		var quxiangb0111s = qx;
		var laiyuanb0111 = $(td).attr('laiyuanb0111')||''
		if($(td).text().indexOf('提拔')>=0){
			tjjg[1]++;
			//45岁及以下xx人
			if(age<=45){
				tjjg[2]++;
			}
			if(quxiangb0111s==laiyuanb0111){//3本单位提拔
				tjjg[3]++;
			}
			if(laiyuanb0111.indexOf('001.001.003')==0&&quxiangb0111s!=laiyuanb0111){//4国企交流提拔
				tjjg[4]++;
			}
			if(laiyuanb0111.indexOf('001.001.002')==0){//5市直单位提拔
				tjjg[5]++;
			}
			if(laiyuanb0111.indexOf('001.001.004')==0){//6区、县（市）提拔
				tjjg[6]++;
			}
			
		}
		
		if($(td).text().indexOf('交流')>=0||$(td).text().indexOf('内部轮岗')>=0){
			tjjg[7]++;
			if($(td).text().indexOf('内部轮岗')>=0){//11单位内部轮岗
				tjjg[11]++;
			}else{
				if(laiyuanb0111.indexOf('001.001.003')==0){//8其中市属国企交流
					tjjg[8]++;
				}
				if(laiyuanb0111.indexOf('001.001.002')==0){//9市直单位交流
					tjjg[9]++;
				}
				if(laiyuanb0111.indexOf('001.001.004')==0){//10区、县（市）交流
					tjjg[10]++;
				}
			}
			
			
		}
		
		
		
		
		
		//20 45岁以下调配领导班子成员xx人
		if(age<=45){
			tjjg[20]++;
		}
		//19调配总年龄
		tjjg[19] += age;
		
		//学历学位统计
		var zgxl = $(td).attr('zgxl')||'';
		var qrzxl = $(td).attr('qrzxl')||'';
		//24 调配大学以上学历xx人
		if(zgxl.indexOf('大学')>=0||zgxl.indexOf('研究生')>=0){
			tjjg[24]++;
		}
		//27调配硕士研究生以上学历xx人
		if(zgxl.indexOf('硕士')>=0){
			tjjg[27]++;
		}
		//30调配博士研究生xx人
		if(zgxl.indexOf('博士')>=0){
			tjjg[30]++;
		}
		
		//33 调配全日制大学以上学历xx人
		if(qrzxl.indexOf('大学')>=0||qrzxl.indexOf('研究生')>=0){
			tjjg[33]++;
		}
		//36调配全日制硕士研究生以上学历xx人
		if(qrzxl.indexOf('硕士')>=0){
			tjjg[36]++;
		}
		//39调配全日制博士研究生xx人
		if(qrzxl.indexOf('博士')>=0){
			tjjg[39]++;
		}
		
	});
	var tpry = tjrx.length;
	//21 调配平均年龄xx.xx岁
	tjjg[21] = (tjjg[19]/tpry).toFixed(2);
	//22 45岁以下调配（占比xx.xx%）
	tjjg[22] = (tjjg[20]/tpry*100).toFixed(2);
	//13降低了xx.xx岁  现状-调配
	tjjg[13] = (tjjg[12] - tjjg[21]).toFixed(2);
	//16 45岁以下增加了xx人       调配-现状
	tjjg[16] = tjjg[20] - tjjg[14];
	//17 45岁以下（ 比例提高了xx.xx%）
	tjjg[17] = (tjjg[22] - tjjg[15]).toFixed(2);
	
	//统计结果       [0退出领导岗位, 1提拔xx人, 2（45岁及以下xx人）, 3本单位提拔, 4国企交流提拔, 5市直单位提拔, 6区、县（市）提拔]
	//		  [7平职交流, 8其中市属国企交流, 9市直单位交流, 10区、县（市）交流xx人, 11单位内部轮岗 ]
	//4 平均年龄xx.xx岁，降低了xx.xx岁。45岁以下领导班子成员xx人（占比xx.xx%），增加了xx人（ 比例提高了xx.xx%）。
	//[12 现状平均年龄xx.xx岁, 13降低了xx.xx岁, 14 45岁以下现状领导班子成员xx人, 15 45岁以下现状（占比xx.xx%）, 16 45岁以下增加了xx人, 17 45岁以下（ 比例提高了xx.xx%）]
	//[ 18现状总年龄, 19调配总年龄, 20 45岁以下调配领导班子成员xx人, 21 调配平均年龄xx.xx岁, 22 45岁以下调配（占比xx.xx%）]
	
	//6 大学以上学历xx人，增加（减少）了x人；硕士研究生以上学历xx人，增加（减少）了x人；博士研究生xx人，增加（减少）了x人。
	//[23 现状大学以上学历xx人,24 调配大学以上学历xx人,25增加（减少）了x人, 26现状硕士研究生以上学历xx人, 27调配硕士研究生以上学历xx人, 28增加（减少）了x人  ]
	//[29现状博士研究生xx人, 30调配博士研究生xx人, 31增加（减少）了x人]
	
	//7全日制大学以上学历xx人，增加（减少）了x人；全日制硕士研究生以上学历xx人，增加（减少）了x人；全日制博士研究生xx人，增加（减少）了x人。
	//[32 现状全日制大学以上学历xx人,33 调配全日制大学以上学历xx人,34增加（减少）了x人, 35现状全日制硕士研究生以上学历xx人, 36调配全日制硕士研究生以上学历xx人, 37增加（减少）了x人  ]
	//[38现状全日制博士研究生xx人, 39调配全日制博士研究生xx人, 40增加（减少）了x人]
	
	//25增加（减少）了x人
	tjjg[25] = tjjg[24] - tjjg[23];
	//28增加（减少）了x人
	tjjg[28] = tjjg[27] - tjjg[26];
	//31增加（减少）了x人
	tjjg[31] = tjjg[30] - tjjg[29];
	
	//34增加（减少）了x人
	tjjg[34] = tjjg[33] - tjjg[32];
	//37增加（减少）了x人
	tjjg[37] = tjjg[36] - tjjg[35];
	//40增加（减少）了x人
	tjjg[40] = tjjg[39] - tjjg[38];
	$.each(tjjg, function(i,data){
		tjjg[i] = '<span style="color:#FF4500">'+data+'</span>';
	});
	html_TJSTR = '<br/>退出领导岗位：'+tjjg[0]+'人。<br/><br/>'
	+'提拔'+tjjg[1]+'人（45岁及以下'+tjjg[2]+'人），其中本单位提拔'+tjjg[3]+'人，市属国企交流提拔'+tjjg[4]+'人，市直单位提拔'+tjjg[5]+'人，区、县（市）提拔'+tjjg[6]+'人。<br/><br/>'
	+'平职交流'+tjjg[7]+'人，其中市属国企交流'+tjjg[8]+'人，市直单位交流'+tjjg[9]+'人，区、县（市）交流'+tjjg[10]+'人，单位内部轮岗'+tjjg[11]+'人。<br/><br/>'
	+'平均年龄'+tjjg[21]+'岁，降低了'+tjjg[13]+'岁。45岁以下领导班子成员'+tjjg[20]+'人（占比'+tjjg[22]+'%），增加了'+tjjg[16]+'人（ 比例提高了'+tjjg[17]+'%）。<br/><br/>'
	
	/*+'大学以上学历'+tjjg[24]+'人，增加了'+tjjg[25]+'人；硕士研究生以上学历'+tjjg[27]+'人，增加了'+tjjg[28]+'人；博士研究生'+tjjg[30]+'人，增加了'+tjjg[31]+'人。<br/><br/>'
	+'全日制大学以上学历'+tjjg[33]+'人，增加了'+tjjg[34]+'人；全日制硕士研究生以上学历'+tjjg[36]+'人，增加了'+tjjg[37]+'人；全日制博士研究生'+tjjg[39]+'人，增加了'+tjjg[40]+'人。<br/><br/>'
	*/
	+'大学以上学历'+tjjg[24]+'人，'+fmt(tjjg[25])+'人；硕士研究生以上学历'+tjjg[27]+'人，'+fmt(tjjg[28])+'人；博士研究生'+tjjg[30]+'人，'+fmt(tjjg[31])+'人。<br/><br/>'
	+'全日制大学以上学历'+tjjg[33]+'人，'+fmt(tjjg[34])+'人；全日制硕士研究生以上学历'+tjjg[36]+'人，'+fmt(tjjg[37])+'人；全日制博士研究生'+tjjg[39]+'人，'+fmt(tjjg[40])+'人。<br/><br/>'
	;
	
	
	
	$('.JGFXInfo').html(html_TJSTR);
	/*var tablefrow = $('#coordTable tr:eq(0)');
	var colspan = 0;
	if(tablefrow.length>0){
		$('td',tablefrow).each(function(i,td){
			colspan += parseInt($(td).attr('colspan')||1);
		});
	}
	
	var $lastrow = $('#coordTable tr:last');
	if($lastrow.hasClass('lastrow')){
		$('td',$lastrow).html(html_TJSTR)
	}else{
		$lastrow = $("<tr class='lastrow'><td class='classBR classBT lastrow' colspan='"+colspan+"'></td></tr>");
		$('td',$lastrow).html(html_TJSTR).css({'text-align':'left','padding':'8px 13px 8px 13px'});
		$('#coordTable').append($lastrow);
	}*/
	
}




//同屏比较会调用
function savePFromTpbj(){}

function openRMB(a0000){
	var rmbs = $("#rmbs").val();
	$("#rmbs").val(rmbs+"@"+a0000);
	if(rmbs.indexOf(a0000)>=0) {
		$h.alert("","已经打开了");
		return ;
	}
	var rmbWin=window.open(contextPath+'/rmb/ZHGBrmb.jsp?a0000='+a0000, '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	
	var loop = setInterval(function() {
	if(rmbWin.closed) {
			clearInterval(loop);
			var rmbs=document.getElementById('rmbs').value;
			document.getElementById('rmbs').value=rmbs.replace(new RegExp(a0000,'g'),"");
		}
	}, 500);
	/*radow.doEvent('elearningGrid.rowdbclick',a01.a0000);*/

}




/**
 * 保存岗位
 * @param obj
 * @returns
 */
function addGWInfo(){
	var gwname = $('#gwname').val();	
	var gwtype = $('#gwtype').val();
	var bzgw = $('#bzgw').val();
	if(gwname==""){
		Ext.Msg.alert("系统提示","岗位名称不能为空！");
		return;
	}
	
	if(gwtype==""){
		Ext.Msg.alert("系统提示","成员类别不能为空！");
		return;
	}
	radow.doEvent('saveGW');
}
/**
 * 设置参数
 * @returns
 */
function setOpenGWWin(obj){
	$('#fxyp00').val('');
	$('#gwname').val('');
	$('#gwtype').val('');
	$('#gwmc').val('');
	$('#bzgw').val('');
	Ext.getCmp('bzgw_combotree').setValue('');
	Ext.getCmp('gwtype_combo').setValue('');
	Ext.getCmp('gwmc_combo').setValue('');
	$('#famx00').val(obj.attr('famx00'));
	$('#b0111gw').val(obj.attr('b0111'));
	$('#mntp_b01').val($('span',obj).text());
	openGWWin(obj)
}
/**
 * 修改页面
 * @returns
 */
function editOpenGWWin(obj){
	if(obj){
		showDataSyn.clearParm();
	    var synparm = getDWTRs(obj.parent());
	    showDataSyn.putParm(synparm);
	    
	}
	radow.doEvent('setGWInfo',obj.attr('fxyp00'));
	
}
/**
 * 打开岗位编辑页面
 * @param obj
 * @returns
 */
function openGWWin(obj){
	if(obj){
		showDataSyn.clearParm();
	    var synparm = getDWTRs(obj.parent());
	    showDataSyn.putParm(synparm);
	    
	}
	var win = Ext.getCmp("gwwinid");	
	
	if(win){
		win.show();	
		return win;
	}
	
	win = new Ext.Window({
		title : '岗位维护',
		layout : 'fit',
		width : 280,
		height : 270,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'gwwinid',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"gwInfo",
		listeners:{}
		           
	});
	win.show();
	
	return win;
}

function renxuanpaixu(){
	$('.gwtj[rowspan!="1"]').each(function(){
		var fxyp00=$(this).attr("fxyp00");
		if(fxyp00==null || fxyp00==''){
			return;
		}
		$('.name[fxyp00="'+fxyp00+'"]').attr('draggable','true').unbind('dragstart').on('dragstart',function(ev) {
			event.dataTransfer.setData("fxyp00_drag",$(this).attr('fxyp00'));
			event.dataTransfer.setData("a0000_drag",$(this).attr('a0000'));
			event.dataTransfer.setData("clientY_drag",event.clientY);
		}).unbind('drop').on('drop',function(ev) {
			
			var fxyp00_drop = $(this).attr('fxyp00');
			var a0000_drop = $(this).attr('a0000');
			var clientY_drop = event.clientY;
			var fxyp00_drag = event.dataTransfer.getData("fxyp00_drag");
			var a0000_drag = event.dataTransfer.getData("a0000_drag");
			var clientY_drag = event.dataTransfer.getData("clientY_drag");
			//如果拖拽不在同一岗位上则无效
			if(fxyp00_drop!=fxyp00_drag){
				return false;
			}
			//同一个人
			if(a0000_drop==a0000_drag){
				return false;
			}
			//添加异步刷新参数
			showDataSyn.clearParm();
		    var synparm = getDWTRs($(this).parent());
		    showDataSyn.putParm(synparm);
		    var pointer = "up";
		    if(clientY_drop>clientY_drag){
		    	pointer = "down";
		    }
			var parm = fxyp00_drag+"@@"+a0000_drag+"@@"+a0000_drop+"@@"+pointer;
			radow.doEvent('renxuanpaixu',parm);
			//alert(a0000_drag)
		});
	});
}
