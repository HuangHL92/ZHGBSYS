function openBGWQP(obj){
	
  $h.openWin('MNTPGWPZ','pages.mntpsj.MNTPGWPZ','ģ������λ����',950,570,null,contextPath,null,
		  {fabd00:$('#fabd00').val()}
  ,true);
  
 
  
}



var B0131DECODE = {"1":"��ְ","3":"��ְ",'1001':'��ί','1004':'����','1003':'�˴�','1005':'��Э','1006':'Ժ��','1007':'��鳤'};

function openBDFAPage(){
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('��ѡ�񷽰�');
		return;
	}
	$h.openWin('MNTPlistsj','pages.mntpsj.MNTPSJDW','��λ����',900,570,null,contextPath,null,
		      {fabd00:fabd00},true);
}
//ѡ���λ
function openXZGWPage(obj){
	var famx00='',b0111='';
	if(obj){
		famx00 = obj.attr('famx00');
		b0111 = obj.attr('b0111');
	}
	
	
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('��ѡ�񷽰�');
		return;
	}
	$h.openWin('MNTPXZGWPage','pages.mntpsj.MNTPSJXZGW','ѡ���λ',800,570,null,contextPath,null,
			{fabd00:fabd00,b0111:b0111,famx00:famx00},true);
}

function infoSearch(){
	myMask.show();
	var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('��ѡ�񷽰�');
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
		alert('��ѡ�񷽰�');
		return;
	}
	
	showDataSyn.getSynData();
}

var showDataSyn = (function(){
	//���� ��Ŵ����ǰ�棬 �Ӻ��濪ʼ�滻�� ����ǰ�汻�滻 �ᵼ�º����޷����� �к������б�ɾ��
	var compare = function (x, y) {//�ȽϺ���
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
			//����λ��
			for(var x=0;x<parm1.length;x++){
				var p = parm1[x];
				radow.doEvent('showDataSyn',p[2]+"@@"+x);
			}
			
		},
		showDataSyn : function(tableData,index,tw){//���µ�λ��
			var html = getHTML(tableData,tw);
			var p = parm1[index];
			
			//console.log(parm1)
			//ɾ���ÿ����
			for(var ai=0;ai<p[0].length;ai++){
				p[0][ai].remove();
			}
			//�µĲ���
			$(html).insertAfter(p[1]);
			//�����¼�
			if(index==parm1.length-1){
				//����ק
				bindMove();
				//�󶨰�ť
				bindButton();
				//���ÿ�Ⱥ��¼�
				setTableWidth(false);
				
				bindEditor();
				this.clearParm();
				myMask.hide();//����
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
	
	//����ק
	bindMove();
	//�󶨰�ť
	bindButton();
	//���ÿ�Ⱥ��¼�
	setTableWidth(tw);
	bindEditor();
	myMask.hide();//����
}
//��ȡ��λ���tr
function getDWTRs($tr){
	//console.log($tr.html())
	var trs = [];//��Ҫɾ����tr
	var $insertTR = $tr;
	var $dwTD;
	while($insertTR.length>0){
		trs.push($insertTR);
		//��λ��һ�е�һ��������� dwIndex���ԣ���ʾ������Ϊ�ڼ��У���λ��û�����ݣ��ϲ��Ŀհ�ҲҪ��
		if($insertTR.children(":first").hasClass('classBT')){
			$dwTD = $insertTR.children(":first");
			
			//��λ����һ����Ϊ�����
			$insertTR = $insertTR.prev();
			//��λ����һ�м��˱��⣬Ҫ���������һ��
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
	var tdMove = $('#tdMove'),px=15;//������ƫ��������ƫ������ƶ����̻���������div�ƶ���������ƶ���td���޷���Ӧ������ʽ
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
	        
	        
	        //�ж���ѡ�Ƿ��ظ����
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
	        	$h.confirm('ϵͳ��ʾ', '�������и���ѡ���Ƿ���������ѡ��', '400', function(id) {
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


//���õ���˵��
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
			var displays=tableObj.rows[i].cells[j].style.display;//������
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
    //�������
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
	        	tdtext = $(this).clone()    //����Ԫ��
	            .find('div,button') //��ȡ������Ԫ��
	            .remove()   //ɾ��������Ԫ��
	            .end()  //�ص�ѡ���Ԫ��
	            .html().replace('<br>','\n');//��ȡ�ı�ֵ
	        	
	        	//��λ�ϲ�  ���յ�һ����λ�ϲ�
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
		  ajaxSubmit('expExceldata',{"data":Ext.encode(data),"excelname":"���ձ�"});
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
	        timeout :300000,//���������
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

	        if("�����ɹ���"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);

	        }else{
//	           Ext.Msg.hide();
	        }
	      }else{
	        //Ext.Msg.hide();

	        /* if(cfg.mainMessage.indexOf("<br/>")>0){

	          $h.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
	          return;
	        } */

	        if("�����ɹ���"!=cfg.mainMessage){
	          Ext.Msg.hide();
	          Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
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
	      alert("�����쳣��");
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

//��ǰ�ϵ���ϲ�ѯ
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
		newWin_ = $h.openWin('ygrxs','pages.mntpsj.YouGuanRenXuan','�й���ѡ���� ',1370,920,null,g_contextpath,null,
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
	//ˢ�±��
}
var doAddPerson = (function(){//������Ա
	return {
		
		queryByNameAndIDS:function(a0000s,fxyp00,a0200s){//�����ݴ���
			$('#a0000s').val(a0000s);
			$('#a0200s').val(a0200s);
			$('#fxyp00').val(fxyp00);
			radow.doEvent('queryByNameAndIDS',fxyp00);
		}
	}
})();




//ѡ��ȥ���λ
function openQuXiangDanWei(){
	var a0200 = $('.button-div').attr('a0200');
	var b0111 = $('.button-div').attr('qx');
	var famx00 = $('#selfamx00').val();
	var a0000 = $('.button-div').attr('a0000');
	
	if(a0000==''){
		Ext.example.msg('','��״��λ����Ա��Ϣ��',1);
		return;
	}
	$h.openWin('QXDW','pages.mntpsj.QXDW','ѡ��ȥ���λ',750,570,null,contextPath,null,
			{fabd00:$('#fabd00').val(),a0000:a0000,b0111:b0111,famx00:famx00,a0200:a0200},true);
	/*$h.openWin('MNTPXZGWPage','pages.mntpsj.MNTPSJXZGW','ѡ���λ',900,570,null,contextPath,null,
			{fabd00:fabd00,b0111:b0111,famx00:famx00},true);*/
}
//��������
function setLiuRen(){
	/* $h.confirm('ϵͳ��ʾ', '����������λ�и���ѡ������ѡ����ȡ���������ȷ������ť�����������ȡ����ȡ�����β�����', '400', function(id) {
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
		Ext.example.msg('','��״��λ����Ա��Ϣ��',1);
		return;
	}
	$('#a0000s').val(a0000);
	$('#a0200s').val(a0200);
	$('#b0111').val(b0111);
	radow.doEvent('setLiuRen',a0200);
}
//������ְ
function setMianZhi(){
	var a0200 = $('.button-div').attr('a0200');
	var b0111 = $('.button-div').attr('qx');
	var famx00 = $('.button-div').attr('famx00');
	var a0000 = $('.button-div').attr('a0000');
	
	if(a0000==''){
		Ext.example.msg('','��״��λ����Ա��Ϣ��',1);
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
	//�ж��ظ� �����ظ��ļӱ�����ɫ��class
	$('.tableTile[famx01="2"]').each(function(){
		var famx00 = $(this).attr('famx00');
		nameRepeat[famx00] = {};
		$('.delIcon[famx00='+famx00+']').each(function(i,item){
			var itm2 = nameRepeat[famx00][$(item).attr('a0000')];
			if(itm2){
				
				//��λ��������Ա  ʵ�ʴ���
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
				//��λ��������Ա  ʵ�ʴ���
				if($(item).attr('fxyp00')==$(item).attr('fxyp00_ry')){
					
					var rgb = Math.round(Math.random()*79)+1;
					nameRepeat[famx00][$(item).attr('a0000')] = [$(item),rgb];
				}
				
			}
		});
		
		//���ظ������ֻ�ȡ��λ�͵�λ
		var tdMove = $('#tdTipMove'),px=15;
		$('.nameRepeat'+famx00).unbind('mousemove').mousemove(function (e) {
			$('h4',tdTipMove).html('������λ��ʾ��');
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
	               	//<p><strong>11</strong> <span>��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ. </span></p>
	        		htmlStr+='<p>'+(index++)+'�� <span>'+$(item).attr('jgjc')+'</span>��<strong>'+$(item).attr('gwName')+'</strong></p>';
	        	}
	        });
	        $(".RenGangXX").html(htmlStr);
	    }).unbind('mouseout').mouseout(function () {

	        tdMove.hide();
	    });
		
		
		//������ʾ
		$('.jlmd').unbind('mousemove').mousemove(function (e) {
			$('h4',tdTipMove).html('������ʾ��');
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
		odin.alert("��ѡ����Ա��");
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



/*****�༭��**************************************************/
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
		//�к� zoulei
        var rowIndex = td.parent().index();
        //�к�zoulei
        var colIndex = td.index();
		var text = GRYP.getValue(rowIndex,colIndex,td);
		//����padding����2
		var width = td.width() +6*2;
        var height = td.height()+8*2;
        if(tagName=="DIV"){
        	width = td.width();
            height = td.height();
        }
        $('.button-div').hide();
      //�����ı���Ҳ����input�Ľڵ�   
       var ps = td.position();
        var div = $('<div style="position:absolute;top:-1px;left:-2px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //���ı����ݼ���td   
        td.append(div);
        //�����ı���ֵ����������ı�����   
        input.attr('value', text);
        input.css('border', "0px");
        input.css('text-align', "left");
        //��Ϊjqueryѡ��ı�־
        input.addClass('txt_editer');
        if(tagName=="SPAN"){
        	input.css('overflow', "hidden");
        	input.css('border', "solid 1px #000000");
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei �������λ��������Լ����ͱ�������򡣷�ֹ�㵽�����հ�λ��Ҳ�ᴥ�����¼�
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//�������ݶ���
            GRYP.setValue(td, $(this).val(),rowIndex,colIndex);
            
        });
        input.trigger("focus").focusEnd();
	});
}


var GLOBLE = {};
//����id�洢����Ϣ
GLOBLE['ID_ROWINFO']={};
//�����кŻ�ȡrowid

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
    		
    		//�������ݶ���
    		var colName = obj.attr('colname');
    		var tpyjid = obj.attr('tpyjid');
    		
    		SetTDtext(obj,value);
    		GLOBLE['ID_ROWINFO'][tpyjid][colName]=value;
    		
    		GLOBLE['ID_ROWINFO'][tpyjid]['famx00']=obj.attr('famx00');
    		GLOBLE['ID_ROWINFO'][tpyjid]['a0200']=obj.attr('a0200');
    		GLOBLE['ID_ROWINFO'][tpyjid]['colname']=obj.attr('colname');
    		//���º�̨
    		var tpyjInfoJSON = Ext.encode(GLOBLE['ID_ROWINFO'][tpyjid]);
			$('#tpyjInfoJSON').val(tpyjInfoJSON);
    		radow.doEvent('updateTPYJ');
    		
    		var mntp05 = $('#mntp05').val();
    		var fabd05 = $('#fabd05').val();
    		if("4"==mntp05&&"1"==fabd05){
    			//�ɲ���������ͳ��
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
		$h.openPageModeWin('MNTPSJGWYL','pages.mntpsj.MNTPSJGWYL','��Ա�б�',1350,680,
				{fabd00:$('#fabd00').val(),famx00:famx00,b0111:b0111},contextPath);
	}else{
		$h.openPageModeWin('MNTPSJGWYL','pages.mntpsj.MNTPSJGWYL','��Ա�б�',1350,680,
				{fabd00:$('#fabd00').val(),famx00:'',b0111:''},contextPath);
	}
	
}

/**
 * ѡ��λ
 * @param obj
 * @returns
 */
function openXZDW(obj){
	if(obj){
		var famx00 = obj.attr('famx00');
		var famx01 = obj.attr('famx01');
		$h.openPageModeWin('choosedwsj','pages.mntpsj.CHOOSEdw','����ѡ��',530,550,{famx00:famx00,famx01:famx01},contextPath);
	}
	
}
function reload(){
	infoSearch();
}




/************��λ����ɲ�����������ͳ��******************************************************************/
function addGBSCTJ(_$td){//ûȥ��
	var famx00 = _$td.attr('famx00');
	var fmt = function(num){
		var $num = $(num);
		var n = parseInt($num.text())
		if(n<0){
			return '������' + '<span style="color:#FF4500">'+-n+'</span>';
		}else{
			return '������' + '<span style="color:#FF4500">'+n+'</span>';
		}
	};
	
	var html_TJSTR = '';
	//ͳ����Ա
	//��ȡ������״ ��Ҫͳ�Ƶ���  ��״ ���� �����У
	var tjrx = $("[famx01='1'][a0000][qx^='001.001.003']");
	//ͳ�ƽ��       [0�˳��쵼��λ, 1���xx��, 2��45�꼰����xx�ˣ�, 3����λ���, 4���������, 5��ֱ��λ���, 6�����أ��У����]
	//		  [7ƽְ����, 8��������������, 9��ֱ��λ����, 10�����أ��У�����xx��, 11��λ�ڲ��ָ� ]
	//4 ƽ������xx.xx�꣬������xx.xx�ꡣ45�������쵼���ӳ�Աxx�ˣ�ռ��xx.xx%����������xx�ˣ� ���������xx.xx%����
	//[12 ��״ƽ������xx.xx��, 13������xx.xx��, 14 45��������״�쵼���ӳ�Աxx��, 15 45��������״��ռ��xx.xx%��, 16 45������������xx��, 17 45�����£� ���������xx.xx%��]
	//[ 18��״������, 19����������, 20 45�����µ����쵼���ӳ�Աxx��, 21 ����ƽ������xx.xx��, 22 45�����µ��䣨ռ��xx.xx%��]
	
	//6 ��ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ���ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[23 ��״��ѧ����ѧ��xx��,24 �����ѧ����ѧ��xx��,25���ӣ����٣���x��, 26��״˶ʿ�о�������ѧ��xx��, 27����˶ʿ�о�������ѧ��xx��, 28���ӣ����٣���x��  ]
	//[29��״��ʿ�о���xx��, 30���䲩ʿ�о���xx��, 31���ӣ����٣���x��]
	
	//7ȫ���ƴ�ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ����˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ���Ʋ�ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[32 ��״ȫ���ƴ�ѧ����ѧ��xx��,33 ����ȫ���ƴ�ѧ����ѧ��xx��,34���ӣ����٣���x��, 35��״ȫ����˶ʿ�о�������ѧ��xx��, 36����ȫ����˶ʿ�о�������ѧ��xx��, 37���ӣ����٣���x��  ]
	//[38��״ȫ���Ʋ�ʿ�о���xx��, 39����ȫ���Ʋ�ʿ�о���xx��, 40���ӣ����٣���x��]
	
	var tjjg = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	
	tjrx.each(function(i,td){
		//��������  ���ݳ������»�ȡ����
		var a0107 = $(td).attr('a0107');
		
		var age = 0;
		if(a0107){
			age = getAgeRender(a0107.replace('.','')+"01");
		}
		
		//45��������״�쵼���ӳ�Աxx��  14
		if(age<=45){
			tjjg[14]++;
		}
		//18��״������
		tjjg[18] += age;
		
		if($(td).text().indexOf('�˳��쵼��λ')>=0){
			tjjg[0]++;
		}
		
		
		/*//����λid
		var qx = $(td).attr('qx')||'';
		//ȥ��id ��001.001.003.006����001.001.002.01T��
		var quxiangb0111s = $(td).attr('quxiangb0111s')||'';
		if($(td).text().indexOf('���')>=0){
			tjjg[1]++;
			//45�꼰����xx��
			if(age<=45){
				tjjg[2]++;
			}
			if(quxiangb0111s==""){//3����λ���
				tjjg[3]++;
			}
			if(quxiangb0111s.indexOf('��001.001.003')>=0){//4���������
				tjjg[4]++;
			}
			if(quxiangb0111s.indexOf('��001.001.002')>=0){//5��ֱ��λ���
				tjjg[5]++;
			}
			if(quxiangb0111s.indexOf('��001.001.004')>=0){//6�����أ��У����
				tjjg[6]++;
			}
			
		}
		
		if($(td).text().indexOf('����')>=0){
			tjjg[7]++;
			if(quxiangb0111s==""){//11��λ�ڲ��ָ�
				tjjg[11]++;
			}
			if(quxiangb0111s.indexOf('��001.001.003')>=0){//8��������������
				tjjg[8]++;
			}
			if(quxiangb0111s.indexOf('��001.001.002')>=0){//9��ֱ��λ����
				tjjg[9]++;
			}
			if(quxiangb0111s.indexOf('��001.001.004')>=0){//10�����أ��У�����
				tjjg[10]++;
			}
			
		}*/
		
		//ѧ��ѧλͳ��
		var zgxl = $(td).attr('zgxl')||'';
		var qrzxl = $(td).attr('qrzxl')||'';
		//23 ��״��ѧ����ѧ��xx��
		if(zgxl.indexOf('��ѧ')>=0||zgxl.indexOf('�о���')>=0){
			tjjg[23]++;
		}
		//26��״˶ʿ�о�������ѧ��xx��
		if(zgxl.indexOf('˶ʿ')>=0){
			tjjg[26]++;
		}
		//29��״��ʿ�о���xx��
		if(zgxl.indexOf('��ʿ')>=0){
			tjjg[29]++;
		}
		
		//32 ��״ȫ���ƴ�ѧ����ѧ��xx��
		if(qrzxl.indexOf('��ѧ')>=0||qrzxl.indexOf('�о���')>=0){
			tjjg[32]++;
		}
		//35��״ȫ����˶ʿ�о�������ѧ��xx��
		if(qrzxl.indexOf('˶ʿ')>=0){
			tjjg[35]++;
		}
		//38��״ȫ���Ʋ�ʿ�о���xx��
		if(qrzxl.indexOf('��ʿ')>=0){
			tjjg[38]++;
		}
	});
	var xzry = tjrx.length;
	//12 ��״ƽ������xx.xx��
	tjjg[12] = (tjjg[18]/xzry).toFixed(2);
	//15 45��������״��ռ��xx.xx%��
	tjjg[15] = (tjjg[14]/xzry*100).toFixed(2);
	
	//ͳ�ƽ��       [0�˳��쵼��λ, 1���xx��, 2��45�꼰����xx�ˣ�, 3����λ���, 4���������, 5��ֱ��λ���, 6�����أ��У����]
	//		  [7ƽְ����, 8��������������, 9��ֱ��λ����, 10�����أ��У�����xx��, 11��λ�ڲ��ָ� ]
	//4 ƽ������xx.xx�꣬������xx.xx�ꡣ45�������쵼���ӳ�Աxx�ˣ�ռ��xx.xx%����������xx�ˣ� ���������xx.xx%����
	//[12 ��״ƽ������xx.xx��, 13������xx.xx��, 14 45��������״�쵼���ӳ�Աxx��, 15 45��������״��ռ��xx.xx%��, 16 45������������xx��, 17 45�����£� ���������xx.xx%��]
	//[ 18��״������, 19����������, 20 45�����µ����쵼���ӳ�Աxx��, 21 ����ƽ������xx.xx��, 22 45�����µ��䣨ռ��xx.xx%��]
	
	//6 ��ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ���ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[23 ��״��ѧ����ѧ��xx��,24 �����ѧ����ѧ��xx��,25���ӣ����٣���x��, 26��״˶ʿ�о�������ѧ��xx��, 27����˶ʿ�о�������ѧ��xx��, 28���ӣ����٣���x��  ]
	//[29��״��ʿ�о���xx��, 30���䲩ʿ�о���xx��, 31���ӣ����٣���x��]
	
	//7ȫ���ƴ�ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ����˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ���Ʋ�ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[32 ��״ȫ���ƴ�ѧ����ѧ��xx��,33 ����ȫ���ƴ�ѧ����ѧ��xx��,34���ӣ����٣���x��, 35��״ȫ����˶ʿ�о�������ѧ��xx��, 36����ȫ����˶ʿ�о�������ѧ��xx��, 37���ӣ����٣���x��  ]
	//[38��״ȫ���Ʋ�ʿ�о���xx��, 39����ȫ���Ʋ�ʿ�о���xx��, 40���ӣ����٣���x��]
	
	//��ȡ���е��� ��Ҫͳ�Ƶ���  ��״ ���� �����У ����֮���ⵥλ�Ĳ���Ҫͳ��
	tjrx = $("[famx01='2'][famx00='"+famx00+"'][a0000][qx^='001.001.003']");
	
	tjrx.each(function(i,td){
		
		//��������  ���ݳ������»�ȡ����
		var a0107 = $(td).attr('a0107');
		var age = 0;
		if(a0107){
			age = getAgeRender(a0107.replace('.','')+"01");
		}
		
		
		//���䵥λid  ����ȥ��id
		var qx = $(td).attr('qx')||'';
		//ȥ��id ��001.001.003.006����001.001.002.01T��
		var quxiangb0111s = qx;
		var laiyuanb0111 = $(td).attr('laiyuanb0111')||''
		if($(td).text().indexOf('���')>=0){
			tjjg[1]++;
			//45�꼰����xx��
			if(age<=45){
				tjjg[2]++;
			}
			if(quxiangb0111s==laiyuanb0111){//3����λ���
				tjjg[3]++;
			}
			if(laiyuanb0111.indexOf('001.001.003')==0&&quxiangb0111s!=laiyuanb0111){//4���������
				tjjg[4]++;
			}
			if(laiyuanb0111.indexOf('001.001.002')==0){//5��ֱ��λ���
				tjjg[5]++;
			}
			if(laiyuanb0111.indexOf('001.001.004')==0){//6�����أ��У����
				tjjg[6]++;
			}
			
		}
		
		if($(td).text().indexOf('����')>=0||$(td).text().indexOf('�ڲ��ָ�')>=0){
			tjjg[7]++;
			if($(td).text().indexOf('�ڲ��ָ�')>=0){//11��λ�ڲ��ָ�
				tjjg[11]++;
			}else{
				if(laiyuanb0111.indexOf('001.001.003')==0){//8��������������
					tjjg[8]++;
				}
				if(laiyuanb0111.indexOf('001.001.002')==0){//9��ֱ��λ����
					tjjg[9]++;
				}
				if(laiyuanb0111.indexOf('001.001.004')==0){//10�����أ��У�����
					tjjg[10]++;
				}
			}
			
			
		}
		
		
		
		
		
		//20 45�����µ����쵼���ӳ�Աxx��
		if(age<=45){
			tjjg[20]++;
		}
		//19����������
		tjjg[19] += age;
		
		//ѧ��ѧλͳ��
		var zgxl = $(td).attr('zgxl')||'';
		var qrzxl = $(td).attr('qrzxl')||'';
		//24 �����ѧ����ѧ��xx��
		if(zgxl.indexOf('��ѧ')>=0||zgxl.indexOf('�о���')>=0){
			tjjg[24]++;
		}
		//27����˶ʿ�о�������ѧ��xx��
		if(zgxl.indexOf('˶ʿ')>=0){
			tjjg[27]++;
		}
		//30���䲩ʿ�о���xx��
		if(zgxl.indexOf('��ʿ')>=0){
			tjjg[30]++;
		}
		
		//33 ����ȫ���ƴ�ѧ����ѧ��xx��
		if(qrzxl.indexOf('��ѧ')>=0||qrzxl.indexOf('�о���')>=0){
			tjjg[33]++;
		}
		//36����ȫ����˶ʿ�о�������ѧ��xx��
		if(qrzxl.indexOf('˶ʿ')>=0){
			tjjg[36]++;
		}
		//39����ȫ���Ʋ�ʿ�о���xx��
		if(qrzxl.indexOf('��ʿ')>=0){
			tjjg[39]++;
		}
		
	});
	var tpry = tjrx.length;
	//21 ����ƽ������xx.xx��
	tjjg[21] = (tjjg[19]/tpry).toFixed(2);
	//22 45�����µ��䣨ռ��xx.xx%��
	tjjg[22] = (tjjg[20]/tpry*100).toFixed(2);
	//13������xx.xx��  ��״-����
	tjjg[13] = (tjjg[12] - tjjg[21]).toFixed(2);
	//16 45������������xx��       ����-��״
	tjjg[16] = tjjg[20] - tjjg[14];
	//17 45�����£� ���������xx.xx%��
	tjjg[17] = (tjjg[22] - tjjg[15]).toFixed(2);
	
	//ͳ�ƽ��       [0�˳��쵼��λ, 1���xx��, 2��45�꼰����xx�ˣ�, 3����λ���, 4���������, 5��ֱ��λ���, 6�����أ��У����]
	//		  [7ƽְ����, 8��������������, 9��ֱ��λ����, 10�����أ��У�����xx��, 11��λ�ڲ��ָ� ]
	//4 ƽ������xx.xx�꣬������xx.xx�ꡣ45�������쵼���ӳ�Աxx�ˣ�ռ��xx.xx%����������xx�ˣ� ���������xx.xx%����
	//[12 ��״ƽ������xx.xx��, 13������xx.xx��, 14 45��������״�쵼���ӳ�Աxx��, 15 45��������״��ռ��xx.xx%��, 16 45������������xx��, 17 45�����£� ���������xx.xx%��]
	//[ 18��״������, 19����������, 20 45�����µ����쵼���ӳ�Աxx��, 21 ����ƽ������xx.xx��, 22 45�����µ��䣨ռ��xx.xx%��]
	
	//6 ��ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ���ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[23 ��״��ѧ����ѧ��xx��,24 �����ѧ����ѧ��xx��,25���ӣ����٣���x��, 26��״˶ʿ�о�������ѧ��xx��, 27����˶ʿ�о�������ѧ��xx��, 28���ӣ����٣���x��  ]
	//[29��״��ʿ�о���xx��, 30���䲩ʿ�о���xx��, 31���ӣ����٣���x��]
	
	//7ȫ���ƴ�ѧ����ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ����˶ʿ�о�������ѧ��xx�ˣ����ӣ����٣���x�ˣ�ȫ���Ʋ�ʿ�о���xx�ˣ����ӣ����٣���x�ˡ�
	//[32 ��״ȫ���ƴ�ѧ����ѧ��xx��,33 ����ȫ���ƴ�ѧ����ѧ��xx��,34���ӣ����٣���x��, 35��״ȫ����˶ʿ�о�������ѧ��xx��, 36����ȫ����˶ʿ�о�������ѧ��xx��, 37���ӣ����٣���x��  ]
	//[38��״ȫ���Ʋ�ʿ�о���xx��, 39����ȫ���Ʋ�ʿ�о���xx��, 40���ӣ����٣���x��]
	
	//25���ӣ����٣���x��
	tjjg[25] = tjjg[24] - tjjg[23];
	//28���ӣ����٣���x��
	tjjg[28] = tjjg[27] - tjjg[26];
	//31���ӣ����٣���x��
	tjjg[31] = tjjg[30] - tjjg[29];
	
	//34���ӣ����٣���x��
	tjjg[34] = tjjg[33] - tjjg[32];
	//37���ӣ����٣���x��
	tjjg[37] = tjjg[36] - tjjg[35];
	//40���ӣ����٣���x��
	tjjg[40] = tjjg[39] - tjjg[38];
	$.each(tjjg, function(i,data){
		tjjg[i] = '<span style="color:#FF4500">'+data+'</span>';
	});
	html_TJSTR = '<br/>�˳��쵼��λ��'+tjjg[0]+'�ˡ�<br/><br/>'
	+'���'+tjjg[1]+'�ˣ�45�꼰����'+tjjg[2]+'�ˣ������б���λ���'+tjjg[3]+'�ˣ��������������'+tjjg[4]+'�ˣ���ֱ��λ���'+tjjg[5]+'�ˣ������أ��У����'+tjjg[6]+'�ˡ�<br/><br/>'
	+'ƽְ����'+tjjg[7]+'�ˣ���������������'+tjjg[8]+'�ˣ���ֱ��λ����'+tjjg[9]+'�ˣ������أ��У�����'+tjjg[10]+'�ˣ���λ�ڲ��ָ�'+tjjg[11]+'�ˡ�<br/><br/>'
	+'ƽ������'+tjjg[21]+'�꣬������'+tjjg[13]+'�ꡣ45�������쵼���ӳ�Ա'+tjjg[20]+'�ˣ�ռ��'+tjjg[22]+'%����������'+tjjg[16]+'�ˣ� ���������'+tjjg[17]+'%����<br/><br/>'
	
	/*+'��ѧ����ѧ��'+tjjg[24]+'�ˣ�������'+tjjg[25]+'�ˣ�˶ʿ�о�������ѧ��'+tjjg[27]+'�ˣ�������'+tjjg[28]+'�ˣ���ʿ�о���'+tjjg[30]+'�ˣ�������'+tjjg[31]+'�ˡ�<br/><br/>'
	+'ȫ���ƴ�ѧ����ѧ��'+tjjg[33]+'�ˣ�������'+tjjg[34]+'�ˣ�ȫ����˶ʿ�о�������ѧ��'+tjjg[36]+'�ˣ�������'+tjjg[37]+'�ˣ�ȫ���Ʋ�ʿ�о���'+tjjg[39]+'�ˣ�������'+tjjg[40]+'�ˡ�<br/><br/>'
	*/
	+'��ѧ����ѧ��'+tjjg[24]+'�ˣ�'+fmt(tjjg[25])+'�ˣ�˶ʿ�о�������ѧ��'+tjjg[27]+'�ˣ�'+fmt(tjjg[28])+'�ˣ���ʿ�о���'+tjjg[30]+'�ˣ�'+fmt(tjjg[31])+'�ˡ�<br/><br/>'
	+'ȫ���ƴ�ѧ����ѧ��'+tjjg[33]+'�ˣ�'+fmt(tjjg[34])+'�ˣ�ȫ����˶ʿ�о�������ѧ��'+tjjg[36]+'�ˣ�'+fmt(tjjg[37])+'�ˣ�ȫ���Ʋ�ʿ�о���'+tjjg[39]+'�ˣ�'+fmt(tjjg[40])+'�ˡ�<br/><br/>'
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




//ͬ���Ƚϻ����
function savePFromTpbj(){}

function openRMB(a0000){
	var rmbs = $("#rmbs").val();
	$("#rmbs").val(rmbs+"@"+a0000);
	if(rmbs.indexOf(a0000)>=0) {
		$h.alert("","�Ѿ�����");
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
 * �����λ
 * @param obj
 * @returns
 */
function addGWInfo(){
	var gwname = $('#gwname').val();	
	var gwtype = $('#gwtype').val();
	var bzgw = $('#bzgw').val();
	if(gwname==""){
		Ext.Msg.alert("ϵͳ��ʾ","��λ���Ʋ���Ϊ�գ�");
		return;
	}
	
	if(gwtype==""){
		Ext.Msg.alert("ϵͳ��ʾ","��Ա�����Ϊ�գ�");
		return;
	}
	radow.doEvent('saveGW');
}
/**
 * ���ò���
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
 * �޸�ҳ��
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
 * �򿪸�λ�༭ҳ��
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
		title : '��λά��',
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
			//�����ק����ͬһ��λ������Ч
			if(fxyp00_drop!=fxyp00_drag){
				return false;
			}
			//ͬһ����
			if(a0000_drop==a0000_drag){
				return false;
			}
			//����첽ˢ�²���
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
