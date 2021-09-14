<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<link rel="stylesheet" type="text/css" href="pages/mntpsj/resourse/mntpsjop.css"> 
<style>
input{
	border: 1px solid #c0d1e3 !important;
}
legend {
    width: auto;
    margin-bottom:auto;
    border-bottom: none;
}
form {
    margin: auto;
}
.GQGX{
	display: inline-block;
}
</style>

<table style="width: 100%;height: 100%;">
	<tr>
		<td  style="width: 300px;height: 100%;" >
		  <odin:groupBox title="名单集" >
				<div id="tree-div1" style="height: 100%;"></div>
			</odin:groupBox> 
		</td>
		<td>
			<div style="align:left top;overflow:auto;margin-left: 5px;height: 300px;"  id="selectable">
				<div style="display: table; text-align: center;width: 100%; " >
				
					<table id="coordTable" cellspacing="0" ondragover="event.preventDefault();"
					 style="margin:auto;width: 100%;"  >
				
						
					</table>
					
				</div>
				<div align="right">
					<div class="btn btn-primary" 
					 style="margin: 20px 20px 20px auto; " onclick="getTabArray()">导出excel
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>


<div class="mdjInfo" id="mdjInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textEdit property="lwxj01"  label="名单描述"  />
		  </tr>
		  <tr>
		  	<odin:textEdit property="lwxj05" label="备注" ></odin:textEdit>
		  </tr>
		  <tr>
		  	<odin:select2 property="mdid" label="名单引用"   />
		  <tr>
		</table>
		<odin:hidden property="lwxj00" title="当前id"/>
		<odin:hidden property="lwxj02" title="上级id"/>
		<div style="width: 100%;text-align: center;margin-top: 20px;">
			<button type='button' class="btn btn-primary" onclick="saveInfo()"  >保存</button>
		</div>
	</div>
</div>

<div  class="opt-btn-tpl" style="display: none;">
	<div class="opt-btn">
			<button type='button' class="btn btn-primary btn-mini" onclick="openViewMDXG($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>编辑名单</button>
			
			<button type='button' class="btn btn-primary btn-mini GQGX" onclick="addGBSCTJ($(this).parent().parent());$('.JGFX').fadeIn(300);" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>结构分析</button>
	</div>
</div>

<!-- //结构分析 -->
<div class="JGFX" style="width: 600px;">
	<div class="alert alert-success">
		 <button type="button" class="close" onclick="$('.JGFX').fadeOut(300)">×</button>
		<h4>
			结构分析：
		</h4> 
		<div class="JGFXInfo">
       		<p><strong>11</strong> <span>请注意你的个人隐私安全.请注意你的个人隐私安全.请注意你的个人隐私安全.请注意你的个人隐私安全. </span></p>
       </div>
	</div>
</div>
<odin:hidden property="rmbs"/>


<script type="text/javascript">

var nodemenu  = (function(){
	var node,ev;
	
	return {
		setNode : function(n,e){
			node = n;
			ev=e;
		},
		menu : new Ext.menu.Menu({  
		    items:[{  
		        text:"添加节点",  
		        icon:'./images/add.png',//右键名称前的小图片  
		        handler:function(){  
		        	setOpenGWWin(node);
		        }  

		    },{ 
		        text:"编辑节点",  
		        icon: './images/setter.png',  
		        cls: 'x-btn-text-icon',  
		        handler:function(){  
		        	if(node.id=='-1'){
		        		Ext.Msg.alert("系统提示","无法修改根节点！");
		        		return;
		        	}
		        	radow.doEvent('setInfo',node.id);
		        }  
		    },{  
		        text:"删除节点",  
		         icon: './images/delete.png',  
		         cls: 'x-btn-text-icon',  
		        handler:function(){  
		        	if(node.id=='-1'){
		        		Ext.Msg.alert("系统提示","无法删除根节点！");
		        		return;
		        	}
		        	$h.confirm("系统提示：",'删除当前名单及下级名单（不会删除人员数据），确定删除：'+node.text+"?",400,function(id) { 
		        		if("ok"==id){
		        			radow.doEvent('delNode',node.id);
		        		}else{
		        			return false;
		        		}		
		        	});
		        	
		        }  

		    }] 
		})
	}
	 
})();
	
	
Ext.onReady(function() {
	
	var tree = new Ext.tree.TreePanel( {
		 id:'group',
		 el : 'tree-div1',//目标div容器
		 split:false,
		 width: 300,
		 height:70,
		 minSize: 164,
		 maxSize: 164,
		 rootVisible: true,//是否显示最上级节点
		 autoScroll : true,
		 animate : true,
		 border:false,
		 enableDD : true,
		 containerScroll : true,
		 loader : new Ext.tree.TreeLoader( {
		       dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.mntpxzjd.MDJ&eventNames=TreeJsonData'
		 }),
		 root : new Ext.tree.AsyncTreeNode({    
             text: "名单集",
             id:'-1',editable :true
         }), 
		 listeners:{  
		 	"contextmenu":function(node,e){  
		 		if(node.id=='-1'){
		 			
		 		}
		 		node.select();
		 		nodemenu.setNode(node,e);
            	nodemenu.menu.showAt(e.getPoint());//menu的showAt，不要忘记  

         	},
         	"click" : function(node){  
         		radow.doEvent('nodeclick',node.id);
         	},
         	"dragdrop": function(treep,dragNode){  
         		var nodeid = dragNode.id;
         		var psnodeid = dragNode.previousSibling?dragNode.previousSibling.id:"";
         		var prtnodeid = dragNode.parentNode.id;
         		radow.doEvent('sortNode',nodeid+"@@"+psnodeid+"@@"+prtnodeid);
         	}
         	 ,
         	"nodedragover":function(params ){  
         		//treep,target,data ,point ,source ,rawEvent ,dropNode ,cancel
         		var node = params.target;
         		node.leaf=false;
         		
         		//node.getUI().getIconEl().src=contextPath+"/basejs/ext/resources/images/default/tree/folder.gif";
         		//node.ui.updateExpandIcon();
         		//debugger
         		
         	} 
         
		} 
	});
	
	
	tree.render();
	tree.getRootNode().expand();
	var viewSize = Ext.getBody().getViewSize();
	tree.setHeight(viewSize.height-55);
	
	$("#selectable").css('height',viewSize.height-10);
	$("#selectable").css('width',viewSize.width-310);
	
	var win = openMDJWin(false);
	win.hide();
});


/**
 * 设置参数
 * @returns
 */
function setOpenGWWin(node){
	$('#lwxj01').val('');
	$('#lwxj05').val('');
	$('#lwxj00').val('');
	$('#lwxj02').val(node.id);
	Ext.getCmp('mdid_combo').setValue('');
	openMDJWin(node)
}
/**
 * 打开岗位编辑页面
 * @param obj
 * @returns
 */
function openMDJWin(node){
	var win = Ext.getCmp("mdjwinid");	
	
	if(win){
		win.show();	
		return win;
	}
	
	win = new Ext.Window({
		title : '名单信息维护',
		layout : 'fit',
		width : 280,
		height : 270,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'mdjwinid',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"mdjInfo",
		listeners:{}
		           
	});
	win.show();
	
	return win;
}

/**
 * 保存岗位
 * @param obj
 * @returns
 */
function saveInfo(){
	var lwxj01 = $('#lwxj01').val();	
	if(lwxj01==""){
		Ext.Msg.alert("系统提示","名单描述不能为空！");
		return;
	}
	
	radow.doEvent('saveInfo');
}

function showData(tableData){
	var html = getHTML(tableData);
	$('#coordTable').html(html);
	$('.titleColor').css('background-color','rgb(230 228 228)');
	
	
	$('.name').unbind('click').bind('click',function(e){
		var tgt = e.target;
		var a0000=$(this).attr("a0000")
		if(a0000==null || a0000==''||tgt.tagName=='A'||tgt.tagName=='INPUT'){
			return;
		}
		openRMB(a0000)
	});
	
	var optbtnhtml = $('.opt-btn-tpl').html();
	$('.opbtn').each(function(i,org){
		$(this).append($(optbtnhtml));
	});
	$('.opt-btn').show();
}
function getHTML(tableData,tw){
	var html = '';
	$.each(tableData, function (i,rowsData) {
		html = html + '<tr>';
		$.each(rowsData, function (j,cellMap) {
			
			html = html + '<td '
			+(cellMap['rowspan']?'rowspan="'+cellMap['rowspan']+'"':' ')
			+(cellMap['colspan']?'colspan="'+cellMap['colspan']+'"':' ')
			+(cellMap['sclass']?'class="'+cellMap['sclass']+'"':' ')
			+(cellMap['style']?'style="'+cellMap['style']+'"':' ')
			+(cellMap['a0000']?'a0000="'+cellMap['a0000']+'"':' ')
			+(cellMap['dzzz']?'dzzz="'+cellMap['dzzz']+'"':' ')
			+(cellMap['a01a0104']?'a01a0104="'+cellMap['a01a0104']+'"':' ')
			+(cellMap['a01a0141']?'a01a0141="'+cellMap['a01a0141']+'"':' ')
			+(cellMap['mdid']?'mdid="'+cellMap['mdid']+'"':' ')
			+(cellMap['a0107']?'a0107="'+cellMap['a0107']+'"':' ')
			+(cellMap['b0111']?'b0111="'+cellMap['b0111']+'"':' ')
			+'>'+cellMap['text']+'</td>';
			
			
		})
		html = html + '</tr>\n';
	});
	return html;
}



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
    var tdrowspan=null,tdcolspan=null;
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
	            Ext.apply(data[ii][jj], {'text':$('<div>'+tdtext+'</div>').text().trim(),'rowspan':tdrowspan
	            	, 'colspan':tdcolspan,'color':$(this).css("background-color") 
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
		  ajaxSubmit('expExceldata',{"data":Ext.encode(data),"excelname":"名单集"});
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





function openViewMDXG(obj){//方案比对
	var mdid = obj.attr('mdid');
	$h.openWin('ViewMDXG','pages.zwzc.Lsmd','干部工作名单',1810,950,'','<%=request.getContextPath()%>',null,
			{mdid:mdid,mdj:"1",maximizable:true},true);
}


function addGBSCTJ(_$td){
	var tjjg = [0,0,0,0,0];
	var tjrx = $("td[class='name']");
	tjrx.each(function(i,td){
		var a01a0104 = $(td).attr('a01a0104');
		var a01a0141 = $(td).attr('a01a0141');
		var dzzz = $(td).attr('dzzz');
		if("2"==a01a0104){
			tjjg[0]++;
		}
		if("01"!=a01a0141){
			tjjg[1]++;
		}
		if("1"==dzzz){
			tjjg[2]++;
		}
	});
	$.each(tjjg, function(i,data){
		tjjg[i] = '<span style="color:#FF4500">'+data+'</span>';
	});
	var html_TJSTR = '<br/>女干部'+tjjg[0]+'名，党外干部'+tjjg[1]+'名，具有乡镇街道党政正职经历的'+tjjg[2]+'名。<br/><br/>'
	$('.JGFXInfo').html(html_TJSTR);
}

</script>


<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>


