/******
 *  ���ڣ�2019-08-20
 * 	�޶�ʱ�䣺2019-10-17
 *  ��Ҫ���ܣ�
 *  	ʵ�ֶԶ���ɲ����һ����ʡί��ί��ɲ����һ����ʡί���ר�����ɲ����һ�����һ����
 *  ����ʾ���༭�����롢���ơ�ճ����������ʽ���鵵��������Ϲ�����Ƭ����ȵĹ���
 */
var GLOBLE={};
GLOBLE.colOffset=0;//�����п�ʼ�ɱ༭����
GLOBLE.rowOffset=1;//�ڶ��п�ʼ�ɱ༭����
//�����кŴ洢��Աid �к�=index+1
GLOBLE['ROWID']=[];
//���������кŻ�ȡ����
GLOBLE['COL_CONFIG_1']={"2":"tp0101"};
GLOBLE['COL_CONFIG_2']={"2":"tp0101"};
 
//���к������ ��Ŷ���
GLOBLE['CUTEDDATA']={};
GLOBLE['CUTEDROWID']=[];

//����id�洢����Ϣ
GLOBLE['ID_ROWINFO']={};
 /*GLOBLE['ID_ROWINFO']["11"]={"tp0100":"11","type":"1","tp0101":"һ��˫�ع���λ�쵼��Ա���������"};
GLOBLE['ID_ROWINFO']["22"]={"tp0100":"22","type":"2","tp0101":"�й���������ͨ�����޹�˾�����зֹ�˾��"};
GLOBLE['ID_ROWINFO']["33"]={"tp0100":"33","type":"3","tp0101":"����1","tp0102":"69.02","tp0103":"90.11","tp0104":"88.12","tp0105":"��ѧ����\n��ְ˶ʿ","tp0106":"�й���ͨϵͳ�������޹�˾�㽭ʡ�ֹ�˾�ܾ���","tp0107":"���й���ͨ�����зֹ�˾��ί��ǡ��ܾ�������ְ"};
 */

//�����к������ź��кš�
var complate = function () {
    $('#coordTable tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    
    var index = 1;
    $('#coordTable tr:gt(0) td:nth-child(2)').each(function (i, item) {
        if ($(this).parent().children().length<4){
        	index = 1;
        }else{
        	$(this).text(index);
        	index++;
        }
    });
     
     
}

 
 //�ϴ���Ƭ
function picupload1 (o) {
	var rowIndex = $(o).index();
	if(rowIndex>0){
		/* if ($("#coordTable tr:eq(1) th").length>0){
			rowIndex--;
		} */
		
		var rowId = GetRowid(rowIndex);
		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"]; 
		var imgId = a0000.replace(/[-]/g,'');
		document.getElementById("a0000").value = a0000;
		$h.showModalDialog('picupload',g_contextpath+'/picCut/picwin.jsp?ImgIdPostfix='+imgId+'&a0000="+a0000+"','ͷ���ϴ�',900,490,null,{a0000:'"+a0000+"'},true);
	}
} 

//�����󴥷��ķ���
var cuted = function () {
    complate();
}
 
function inputTPBExcel(){
	var ynId = document.getElementById('ynId').value;
	var tp0116 = document.getElementById('yntype').value;
	$("#coordTableHtmlContent").val("");
	$h.confirm("ϵͳ��ʾ��",'ģ�����ݵ��룬<br/>����Excel�ļ����ݻ������ǰ�������ݣ��Ƿ������',200,function(id) { 
		if("ok"==id){ 
			$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','����Excel',300,160,
					{"ynId":ynId,"tp0116":tp0116,isKccl:0},g_contextpath);
			location.reload(true);
		}	
	}); 
}
 
var uploadKCCL = (function(){//��������ϴ�
	var obj = null;
	return {
		hasSave:function(o){
			obj = o;
			var rowIndex = $(o).index();
        	if(rowIndex>0){
        		var rowId = GetRowid(rowIndex);
        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
        		var tp0100 = GLOBLE['ID_ROWINFO'][rowId]["tp0100"];
        		var ynId = document.getElementById('ynId').value;
        		if(a0000==""||a0000==null){
            		return;
            	}
        		eventajax("hasSaved",{tp0100:tp0100},function(code){
        			if(code==0){
        				$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','��������ϴ�',300,400,
                				{a0000:a0000,ynId:ynId,isKccl:1,$tr:$(o),scroll:"scroll:yes;"},g_contextpath);
        			}else{
        				$h.confirm("ϵͳ��ʾ��",'�������Ϣ��δ���棬<br/>�����ȷ��������������Ϣ���������ȡ����',200,function(id) { 
        					if("ok"==id){
        						//Ext.getCmp('a0184').clearInvalid();
        						save("savefirst");
        					}	
        				});
        			}
        		})
        	}
		},
		openUploadKcclWin:function(){//
			if(obj){
				var rowIndex = $(obj).index();
	        	if(rowIndex>0){
	        		var rowId = GetRowid(rowIndex);
	        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
	        		var ynId = document.getElementById('ynId').value;
	        		if(a0000==""||a0000==null){
	            		return;
	            	}
	        		$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','��������ϴ�',300,130,
	        				{a0000:a0000,ynId:ynId,isKccl:1,$tr:$(obj)},g_contextpath);
	        	}
			}
		}
	}
})();

$(document).ready(function () {


//��������
//setTimeout(function () {
    $('#coordTable').coordTable({
        selecte_col_len: 4, //����ÿ�е�����
        selector_row: 'tr.data td:nth-child(2)', //һ��jqueryѡ��������ʾ���Ե��ѡ���е�����
        selector_td: 'tr.data td:nth-child(n+2)', //һ��jqueryѡ��������ʾ�����������ק������
        appended: complate,
        inserted: complate,
        selectTPRY:doAddPerson.sel,
        updateNRM: function(o){
        	var rowIndex = $(o).index();
        	if(rowIndex>0){
        		var rowId = GetRowid(rowIndex);
        		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
        		if(a0000==""||a0000==null){
            		return;
            	}
        		radow.doEvent('updateNRM',a0000);
        	}
        	
        },
        uploadKCCL: uploadKCCL.hasSave,
        deleted: cuted,
        cleared: cuted,
        pasted: complate,
        cuted: cuted,
        create_row: createRow,
        enableCopy: true,
        enablePaste: true,
        enableCut: true,
        enableAppend: true,
        enableInsert: true,
        enableDelete: true,
        enableEdit: true,
        picupload: function (o) { 
        	picupload1(o);
        },        
        saveGDCL: function (o) { 
        	saveGD();
        },
        readGDCL: function (o) { 
        	readGD();
        },        
    	updateData:function(rows){//���з���
    		var curCache = [];
    		$.each(rows, function () {
                var rowIndex = $(this).index();
                var rowId = GetRowid(rowIndex);
                GLOBLE['CUTEDROWID'].push(rowId);
                curCache.push(rowId);
              //���к������ ��Ŷ���
                GLOBLE['CUTEDDATA'][rowId]=GLOBLE['ID_ROWINFO'][rowId];
            });
    		$.each(curCache, function (i,item) {
    			//ɾ��id
    			GLOBLE['ROWID'].splice($.inArray(item,GLOBLE['ROWID']),1);
    			delete GLOBLE['ID_ROWINFO'][item];
    		});
    	},
    	rowsPasted:function(o){//ճ������
    		var rowIndex = $(o).index();
    	
    		//���ж�����û������ֱ�ӷ���
    		if(GLOBLE['CUTEDROWID'].length==0){
    			return;
    		}
    		if(rowIndex>0){
    			$.each(GLOBLE['CUTEDROWID'],function (i, item){
        			var $tr = createRow(GLOBLE['CUTEDDATA'][item]["type"],rowIndex+i,item,GLOBLE['CUTEDDATA'][item]);
        			$tr.insertBefore($(o));
        		});
    		}else if(rowIndex==0){//׷��
				$.each(GLOBLE['CUTEDROWID'],function (i, item){
        			var $tr = createRow(GLOBLE['CUTEDDATA'][item]["type"],rowIndex,item,GLOBLE['CUTEDDATA'][item]);
        			$(o).parent().append($tr);
        		});
    		}
    		
    		//��ջ���
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
    	},
    	rowsDelete : function(rows){//ɾ����
    		$(rows).each(function(){
    			var rowIndex = $(this).index(); 
    			if(rowIndex==0){//��ͷ
    				return;
    			}
    			//ɾ��id
    			//console.log(GLOBLE); 
    			delete GLOBLE['ID_ROWINFO'][GetRowid(rowIndex)];
    			//GLOBLE['ROWID'].removeIndex(rowIndex-GLOBLE.rowOffset);
    			//alert(GetRowid(rowIndex)+"   "+findDataIndex(GetRowid(rowIndex))+"  "+(rowIndex-GLOBLE.rowOffset));
    			var dataIndex = findDataIndex(GetRowid(rowIndex));
    			GLOBLE['ROWID'].removeIndex(dataIndex);
    			$(this).remove();
        	});
    	}
    });

    $('#coordTable').tableEditer({
        selector: 'tr.data td:nth-child(n+1)',
    	getValue:function(rowIndex,colIndex){
//{"8":["tp0107","tp0108","tp0109","tp0110 tp0111","tp0112","tp0113","tp0114","tp0115]}
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex); 
    		if(colName instanceof Array){//colName������
    			var dataJSON = GLOBLE['ID_ROWINFO'][rowId];
    			var text = [dataJSON[colName[0]]||"",dataJSON[colName[4]]||"",dataJSON[colName[5]]||"",dataJSON[colName[8]]||""];
        		return text;
    		}else{
    			//alert(colName + "\n" + JSON.stringify(GLOBLE['ID_ROWINFO'][rowId]));
        		var text = GLOBLE['ID_ROWINFO'][rowId][colName];
        		return text==null?"":text;
    		}
    		
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		//�������ݶ��� 
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex);  
    		if(colName instanceof Array){//colName������  ������ְ��
    			var dataJSON = GLOBLE['ID_ROWINFO'][rowId];
    			/* dataJSON[colName[0]] = value[0];
    			dataJSON[colName[4]] = value[1];
    			dataJSON[colName[5]] = value[2];
    			dataJSON[colName[8]] = value[3]; */ 
    			SetTDtext(obj,value[0]);
    		}else{  
    			SetTDtext(obj,value); 
        		GLOBLE['ID_ROWINFO'][rowId][colName]=value; 
        		if ($(obj).parent().children("td").length<4){ 
        			GLOBLE['ID_ROWINFO'][rowId]["tp0101"]=value; 
        		}
    		}
    		
    	}
    }); 

//}, 50);
  //���ѡ����
    $("#coordTable tr.data td:nth-child(2)").live('dblclick', function (e) {
    	if ($(this).parent().children('td').length<4){//������
    		return;
    	}
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	} 
    	 
    	if (a0000.indexOf('ZZZZ')>=0){
    		a0000 = a0000.substring(4);
    	} 
    	$("#rmburl").val("");//���������URL
    	 
    	/**
		eventajax("hasPerson",{"a0000":a0000},function(code){
			if(code==0){
		    	$h.showModalDialog('personInfoOP','http://192.168.181.1:8080'+g_contextpath+'/rmb/ZHGBrmb.jsp?FromModules=1&a0000='+a0000+
		    			'&vid=076C02424269C122AB1D0635D217DB81A37F22A481F485BB55C9F513FE8FA6D5501B9174FFCC2B0D1DDE44D8687B957C5FB44758604ACC9592022A4B2BE8835D7BEA86A63CE866417787F2CCDD0D88B992A889E0BCAAE786146180B12AA07B003363FD5EDA2C969B86C8B1891F757034A0130356E5C334BD3EB2C30616DC1788','��Ա��Ϣ���',1009,630,null,
		    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
		    	        return false;
			}else{

		    	$h.alert('ϵͳ��ʾ','�ɲ����ݿ�û�и�����Ϣ��');
			}
		})   ***/
		 
		eventajax("getRMBLoginURL",{"a0000":a0000},function(code){
			var url = $("#rmburl").val(); 
			if(url !=""){ 
				if (url.toLowerCase().indexOf("http")>=0){ 
					 top.loginTemplateDB(); 
					 setTimeout("toTempDBRMB('"+a0000+"')", 1000);  
				} else {  
		    		$h.showModalDialog('personInfoOP',url,'��Ա��Ϣ���',1009,630,null,
		    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
		    	        return false;
				}
			}else{
		    	$h.alert('ϵͳ��ʾ','�ɲ����ݿ�û�и�����Ϣ��');
			}
		}) 	 	

    });
    
 });
 
 
 function toTempDBRMB(a0000){
	 var url = $("#rmburl").val(); 
 
		$h.showModalDialog('personInfoOP',url,'��Ա��Ϣ���',1009,630,null,
    			{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
    	        return false;	 
 }
function impLrmFilesToZJK(){
	
	top.loginTemplateDB(); 
	setTimeout("ImportLrms2()", 1000);   
	return;
	 
	var isBatch = 1;
	
	eventajax("getPublicServantManage",{"IsBatch":isBatch},function(code){
		var url = $("#rmburl").val();  
		/**
		//var newWin= window.open(url,'newwindow','height=400,width=600,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');//�´�һ���մ���
		***/
		
	    $h.showModalDialog('publicServantManage',url,'��Ϣ����',600,400,null,
	    			{maximizable:false,resizable:false,draggable:false},true);
	     return true;
	}) 	 		 
 }
 
 

 function ImportLrms2(){
	 var url = remoteServer+"/pages/publicServantManage/ImportLrms2.jsp?businessClass=com.picCut.servlet.SaveLrmFile";
	 //var newWin= window.open(url,'newwindow','height='+300+',width='+600+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//�´�һ���մ���
	    $h.showModalDialog('publicServantManage',url,'��Ϣ����',600,400,null,
    			{maximizable:false,resizable:false,draggable:false},true);
	 
 }
 function ImportLrm2(){
	 var url = remoteServer+"/pages/publicServantManage/ImportLrm2.jsp?businessClass=com.picCut.servlet.SaveLrmFile";
	 //var newWin= window.open(url,'newwindow','height='+300+',width='+600+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//�´�һ���մ���
	 $h.showModalDialog('publicServantManage',url,'��Ϣ����',600,400,null,
    			{maximizable:false,resizable:false,draggable:false},true);
 }
 
 function impLrmFileToZJK(){  
	 top.loginTemplateDB(); 
	 setTimeout("ImportLrm2()", 1000);   
	 return;
	 
	var isBatch = 0;
	
	eventajax("getPublicServantManage",{"IsBatch":isBatch},function(code){
		var url = $("#rmburl").val();  
		/**
		//var newWin= window.open(url,'newwindow','height=400,width=600,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');//�´�һ���մ���
		***/
		
	    $h.showModalDialog('publicServantManage',url,'��Ϣ����',600,400,null,
	    			{maximizable:false,resizable:false,draggable:false},true);
	     return true;
	}) 	 		 
 }
 
//ɾ������
	Array.prototype.removeIndex = function (index) {
	  if (index > - 1) {
	    this.splice(index, 1);
	  }
	};
	
	//����UUID
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}
	
	//�����ı���Ϣ
	function SetTDtext(td,v) {
		if ($(td).html().toLowerCase().indexOf("img")>=0){
			return; 
		}
		try{
			var colIndex = $(td).index();
			if (v==null) v="";
			//���Ƽ������ʾ��ʽ
			if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0120"){
				if (v.length == 4){
					//�����ֻ���
					v = v.substring(0,2) + "<BR/>" + v.substring(2);
				}else if (v.length == 5 || v.length == 6){
					//�����ֻ���
					v = v.substring(0,3) + "<BR/>" + v.substring(3);
				}
			}else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0119"){
				if (v.indexOf("<")<0){
					var tText = "";
					for (var ii=0;ii<=v.length;ii++){
						tText+=v.substring(ii,ii+1)+"<BR/>";
					}
					v = tText;
				}else {
					v=v.toUpperCase(); 
					while (v.endWith("<BR/>")){
						v = v.substring(0,v.length-5);
					}
					while (v.endWith("<BR>")){
						v = v.substring(0,v.length-4);
					}					
				}
			} else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0102" ||
					GLOBLE['COL_CONFIG_3'][colIndex] == "tp0103" ||
					GLOBLE['COL_CONFIG_3'][colIndex] == "tp0104"){
				if (v.length>7){
					v = v.substring(0,7);
				}
			} else if (GLOBLE['COL_CONFIG_3'][colIndex] == "tp0101"){
				//����Ϊ�����ֵ��м�ӿո���������
				v = v.replace(' ','');
				if (v.length==2){
					v = v.substring(0,1) +" "+ v.substring(1,2);
				}
			}
			
			
			  $(td).html((v==""||v==null||v=="null")?"":v.replace(/\n/g,"<br/>"));
			  var scrollHeight = $(td)[0].scrollHeight; 
			  if (scrollHeight>=160){ 
				  var fontSize = 15; 
				  while (scrollHeight>=160){
					  var s = "<span style='font-size:"+fontSize+"pt'>"+v.replace(/\n/g,"<br/>") + "</span>";
					  $(td).html((v==""||v==null||v=="null")?"":s);
					  scrollHeight = $(td)[0].scrollHeight;
					  fontSize=fontSize-1; 
				  }
				  $(td).attr("height",160);
				  /**
			  	  var fontSize = 1+parseInt(Math.ceil(15*150.0/scrollHeight));
				  var s = "<span style='font-size:"+fontSize+"pt'>"+v.replace(/\n/g,"<br/>") + "</span>";
				  $(td).html((v==""||v==null||v=="null")?"&nbsp;":s);
				  
				  $(td).attr("height",150);
				  
				  if ($(td).html().toLowerCase().indexOf("img")<0){
				  	$(td).attr("title",v);
				  } */
				  
			  }else{
				  $(td).removeAttr("height");
				  $(td).attr("title","");
			  }
			   
		}catch(e){ 
			alert(e);
		}
	}
	
	//������Ƭ
	function SetTDPhoto(td,v) {
		var imgId = "personImg"+v.replace(/[-]/g,'');
		$(td).css("font-size:0;cellpading:0px;cellspacing:0px;");
		$(td).attr("height",160);
		$(td).attr("title","");
		$(td).html("<img id='"+imgId+"' style=\"cellpading:0px;cellspacing:0px;border:0px;margin 0px;padding:0px;vertical-align:bottom;display:block;width:100%;height:160\" src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"&centerPhoto=1'/>");
		//{"background-color":"yellow","font-size":"200%"}
		//$(td).css({"background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","background-size":"40px 40px","background-repeat":"no-repeat","background-attachment":"fixed"});
		
		//����ĵģ���ʱ�ָ�
		//$(td).css({"height":"3.5cm","width":"100%","background-image":"url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+")","-moz-background-size":" 100% 100%","-o-background-size":" 100% 100%","-webkit-background-size":" 100% 100%","background-size":" 100% 100%","-moz-border-image":" url(/hzb/servlet/DownloadUserHeadImage?a0000="+v+") 0","background-repeat":"no-repeat","background-image":"none","filter":"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/hzb/servlet/DownloadUserHeadImage?a0000="+v+"', sizingMethod='scale')"});
 
	}
	//�����кŻ�ȡrowid
	function GetRowid(rowIndex){
		var rowId = $("#coordTable tbody tr:eq("+rowIndex+")").attr("uuid");
		return rowId;
		//alert(rowId+"\n"+GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset]);
		//return GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset];
	}
	
	function findDataIndex(uuid){
		var index=0;
   		$("#coordTable tbody tr").each(function(){
   			if ($(this).attr("uuid")){ 
   				if ($(this).attr("uuid")==uuid){
   					//alert($(this).attr("uuid") + "\n"+uuid+"\n"+index);
   					return index;
   				}
   				index++;
   			}
   		});	
   		return index;
	}
	

	
	
	$(document).ready(function () { 
		$(function(){ 
			$(document).bind('click',function(e){ 
				 var $NiRenMianBianJi = $('.NiRenMianBianJi');
				if($NiRenMianBianJi.css('display')=='none'){
					return;
				}
				var e = e || window.event; //����������� 
				var elem = e.target || e.srcElement; 
				
				while (elem) { //ѭ���ж������ڵ㣬��ֹ�������div��id=��test������Ԫ�� 
					if (elem.id && elem.id=='NiRenMianBianJi') { 
						return; 
					} 
					elem = elem.parentNode; 
				} 
				
				var rowIndex = $NiRenMianBianJi.attr("rowIndex");
				var colIndex = $NiRenMianBianJi.attr("colIndex");
				//������  ��һ���ͺܿ�
				var $trr = $("#coordTable tr:nth-child("+(rowIndex+1)+")");
				var $td = $("td:nth-child("+(colIndex+1)+")",$trr);
				
				//���������
				var niRenMianDesc;
				var Ren = $('.Ren textarea').val();
				var Mian = $('.Mian textarea').val();
				var QiTa = $('.QiTa textarea').val();
				if(Ren!=''&&Mian!=''&&QiTa!=''){
					niRenMianDesc = "��" + Ren + "��" + "��" + Mian + "��" + QiTa;
				}else if(Ren!=''&&Mian!=''){
					niRenMianDesc = "��" + Ren + "��" + "��" + Mian 
				}else if(Ren!=''&&QiTa!=''){
					niRenMianDesc = "��" + Ren + "��" + QiTa;
				}else if(Mian!=''&&QiTa!=''){
					niRenMianDesc = "��" + Mian + "��" + QiTa;
				}else if(Ren!=''){
					niRenMianDesc = "��" + Ren;
				}else if(Mian!=''){
					niRenMianDesc = "��" + Mian;
				}else if(QiTa!=''){
					niRenMianDesc = QiTa;
				}else{
					niRenMianDesc = "";
				}
				
	        	var NiRenMian = [niRenMianDesc,Ren,Mian,QiTa];
	        	
	        	//�������ݶ���
	            jQuery.tableEditer.setValue($td, NiRenMian,rowIndex,colIndex);
	        	
				$('#NiRenMianBianJi').css('display','none'); //����Ĳ���div������Ԫ�� 
			});	  

		});
	})
	
 var selectedRow = null;
var doAddPerson = (function(){//������Ա
	var obj = null;
	var coordTable_html;
	return {
		//ѡ����Ա
		sel:function(o){ 
			obj = o;
			var ynId = document.getElementById('ynId').value;
			var yntype = $("#yntype").val();
			$("#appointment").val("0");
			if(ynId==''){
				//$h.alert('ϵͳ��ʾ','��������Ϣ��');
				//return;
			}
			$h.openWin('seltpry','pages.xbrm2.SelectTPRY','������/���֤��ѯ ',1040,520,null,g_contextpath,window,
					{maximizable:false,resizable:false,RMRY:'������Ա',
				ynId:ynId,yntype:yntype});
		},
		
		//��ʽ����
		appointment : function(){   
			var ynId = document.getElementById('ynId').value;
			var yntype = $("#yntype").val();
			$("#appointment").val("1");
			if(ynId==''){
				//$h.alert('ϵͳ��ʾ','��������Ϣ��');
				//return;
			}
			$h.openWin('seltpry','pages.xbrm2.SelectTPRY','������/���֤��ѯ ',1040,520,null,g_contextpath,window,
					{maximizable:false,resizable:false,RMRY:'������Ա',
				ynId:ynId,yntype:yntype});
		},
		
		//��ѯ
		queryByNameAndIDS:function(list){//��������ѯ 
			//if(obj){
				radow.doEvent('queryByNameAndIDS',list);
			//}
		},
		
		//������Ա
		addPerson:function(plist){//��������ѯ ������Ա
			var rowIndex = 0;
			if(obj){
				rowIndex = $(obj).index();
			} else {
				rowIndex = 0;
			}
			try{    
				var coordTable_html =  Base64.decode ($("#coordTableHtmlContent").val());
				var isFromSaveHtml = false;
				if (typeof(coordTable_html)==undefined){
					isFromSaveHtml = false;
				} else {
					isFromSaveHtml = (coordTable_html !=null && coordTable_html !="");
				}
				//isFromSaveHtml = false;
				if (isFromSaveHtml){
					//console.log(coordTable_html);   // zouzhilin 2019-09-02
					//console.log(decodeURIComponent(coordTable_html));
					coordTable_html = decodeURIComponent(coordTable_html); 
					$("#coordTable_div").html(coordTable_html); 
					return; 
				}    
	       		if(rowIndex>0){
		   			$.each(plist,function (i, item){
		        		var $tr = createRow(item["type"],rowIndex+i,item["tp0100"],item);
		        		if (!isFromSaveHtml) $tr.insertBefore($(obj));
		   			});
	      		}else if(rowIndex==0){//׷��
					$.each(plist,function (i, item){ 
						var $tr = createRow(item["type"],rowIndex,item["tp0100"],item);
						if (!isFromSaveHtml) { 
							$("#coordTable").append($tr);
						}
	          		});
	      		}  
	       		complate(); 
	       		if (!isFromSaveHtml){ 
					jQuery.coordTable.appendDiv();
	       		}
	       		$("#coordTable tbody tr td").each(function(){
	       			if ($(this).html().indexOf("img")<0){
	       				var td = $(this);
	       				SetTDtext(td,$(this).html()); 
	       			} else { 
						console.log($(this).html());
					}
	       		});
	       		$("#coordTable").colResizable();
	       		
				$('tr:eq(0)').find('th:eq(0)').css("width","0"); 
				$('tr').find('td:eq(0)').css("width","0");
				
				obj = null;
			}catch(e){
				alert(e);
			} 
			
		}
		
	}
})();


//��������
function fillCalAgeYear(cal_age_year){
	if (cal_age_year!="" && 
			cal_age_year.indexOf('-')>0){
		var array = cal_age_year.split("-");
		if (array.length == 3){
			$("#NL_year").html(array[0]);
			$("#NL_month").html(array[1]);
			$("#NL_date").html(array[2]);
		}
	}
}

//���棬��ҳ��Ҳͬʱ����
function save(p){ 
	/***
	$("#coordTable tbody tr").each(function(){
		if ($(this).attr("uuid")){
			alert($(this).index()+"  "+ $(this).attr("uuid"))
		}
	});
	return;***/ 
	var coordTable_html =  Base64.encode(encodeURIComponent($("#coordTable_div").html()));
	$("#coordTableHtmlContent").val(coordTable_html);
 	$("#tb_unit_id").val($("#tb_unit").val()); 
 	$("#cal_age_year").val($("#NL_year").html() + "-" + $("#NL_month").html() +"-"+$("#NL_date").html());
	var ID_ROWINFO = Ext.encode(GLOBLE['ID_ROWINFO']);
	var ROWID = Ext.encode(GLOBLE['ROWID']);
	$('#ID_ROWINFO').val(ID_ROWINFO);
	/**
	$('#ROWID').val(ROWID); 
	alert(ROWID);**/
	
	var ROWID="";
	$("#coordTable tbody tr").each(function(){
		if ($(this).attr("uuid")){
			ROWID+= ",'" + $(this).attr("uuid") + "'";
		}
	});
	if (ROWID != "") ROWID = ROWID.substring(1);
	ROWID = "["+ROWID+"]"; 
	$('#ROWID').val(ROWID);  
	if(p){
		radow.doEvent('save',p);
	}else{
		radow.doEvent('save');
	}
	
}

//�鵵���ϻ���ͨ������й鵵����
function saveGD(){//�鵵
	var msgTemplate =  "�����뱸ע��";  
	Ext.MessageBox.prompt("�����",msgTemplate,function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 
			 var coordTable_html =  Base64.encode(encodeURIComponent($("#coordTable_div").html()));
			 $("#coordTableHtmlContent").val(coordTable_html);
			 			 
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("saveGD",txt);
		 }
	        
	},this,true); 
}

//��ȡ�鵵��Ϣ
function readGD(){//���� 
	$h.openPageModeWin('gdck','pages.xbrm2.TPBView','�ɲ����佨�鷽���浵��Ϣ',1150,screen.height *0.95,
			{yn_id:$('#ynId').val(),scroll:"scroll:no;"},g_contextpath);
}
function updateNRM(){
	save("updateNRM");
}

var TIME_INIT = (function(){
	return {
		setTPHJLB : function(){
			//���价�����ؼ�
			var $div = $(".TiaoPeiLeiXingDIV");
			var $sel = $("#yn_type");
			$div.hide();
			$sel.focus();
			$(".TiaoPeiLeiXingSPAN").bind('click', function(e) {
				//var left = 200 + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				//$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				//���价�����id
				var tphjid = $(this).children('option:selected').val();  
				var tphjtext = $(this).children('option:selected').text();
				$(".TiaoPeiLeiXingSPAN").text(tphjtext);
				$("#yntype").val(tphjid);
				//�������
				TIME_INIT.clearData();
				radow.doEvent('initX');
			});
		},
		changeTableType : function(tphjid){
			if(tphjid=='TPHJ1'||tphjid=='TPHJ2'){
				$('#RenXianZhiShiJian').css('width','8%');
			    $('#RenTongJiShiJian').css('width','8%');
			    $('#XianRenZhiWu').css('width','25%');
			    $('#NiRenMianZhiWu').css('width','25%');
			}else{
				$('#RenXianZhiShiJian').css('width','0%');
			    $('#RenTongJiShiJian').css('width','0%');
			    $('#XianRenZhiWu').css('width','33%');
			    $('#NiRenMianZhiWu').css('width','33%');
			}
		},
		clearData : function(){
			$(".data").remove();
			GLOBLE['ROWID']=[];
			GLOBLE['ID_ROWINFO']={};
			//��ջ���
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
		},

		setTPHJSJ_Y : function(){
			//����ʱ��ؼ�
			var $div = $(".TiaoPeiShiJianY");
			var $sel = $("#tp_y");
			$div.hide();
			$sel.focus();
			
			var $TiaoPeiShiJianY;
			$(".TiaoPeiShiJianYSPAN").bind('click', function(e) {
				$TiaoPeiShiJianY = this;
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				var tphjid = $(this).children('option:selected').val();  
				var tphjtext = $(this).children('option:selected').text();
				//alert($($div)[0].outerHTML);
				$(".TiaoPeiShiJianYSPAN").text(tphjtext); 
				if ($($TiaoPeiShiJianY).attr("id") =="TiaoPeiShiJianPrint_year"){
					$($TiaoPeiShiJianY).text(tphjtext);
					$("#tpy").val(tphjid);
				}
			});
		},
		setTPHJSJ_M : function(){
			//����ʱ��ؼ�
			var $div = $(".TiaoPeiShiJianM");
			var $sel = $("#tp_m");
			$div.hide();
			$sel.focus();
			
			var $TiaoPeiShiJianM;
			$(".TiaoPeiShiJianMSPAN").bind('click', function(e) {
				$TiaoPeiShiJianM = this;
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				var tphjid = $(this).children('option:selected').val();  
				var tphjtext = $(this).children('option:selected').text();
				//$(".TiaoPeiShiJianMSPAN").text(tphjtext);
				$($TiaoPeiShiJianM).text(tphjtext);
				$("#tpm").val(tphjid);
			});
		},
		setTPHJSJ_D : function(){
			//����ʱ��ؼ�
			var $div = $(".TiaoPeiShiJianD");
			var $sel = $("#tp_d");
			$div.hide();
			$sel.focus();
			var $TiaoPeiShiJianD;
			$(".TiaoPeiShiJianDSPAN").bind('click', function(e) {
				$TiaoPeiShiJianD = this;
				var left = $(this).offset().left + 'px';
				var top = $(this).offset().top + 'px';
				$div.css("top",top);
				$div.css("left",left);
				$div.show();
				$sel.focus();
		    });
			$sel.focusout(function (e) {
				$div.hide();
		    });
			$sel.change(function(){  
				//���价�����id
				var tphjid = $(this).children('option:selected').val();  
				var tphjtext = $(this).children('option:selected').text();
				//$(".TiaoPeiShiJianDSPAN").text(tphjtext);
				$($TiaoPeiShiJianD).text(tphjtext);
				$("#tpd").val(tphjid);
			});
		},
		initShiJian : function(){
			var myDate = new Date();
			var y = myDate.getFullYear();
			var $sel = $("#tp_y");
			for(var i=0;i<3;i++){
				var $o = $('<option value="'+(y-i+1)+'">'+(y-i+1)+'</option>');
				$sel.append($o);
			}
			$sel = $("#tp_m");
			for(var i=1;i<13;i++){
				var $o = $('<option value="'+(i)+'">'+(i)+'</option>');
				$sel.append($o);
			}
			$sel = $("#tp_d");
			for(var i=1;i<32;i++){
				var $o = $('<option value="'+(i)+'">'+(i)+'</option>');
				$sel.append($o);
			}
		},
		//���õ���ʱ����Ϣ
		setTime : function(y,m,d,tphjlb){ 
			$('#tpy').val(y);
			$('#tpm').val(m);
			$('#tpd').val(d); 
			$('#tp_y').val(y);
			$('#tp_m').val(m);
			$('#tp_d').val(d);
			$("#yntype").val(tphjlb);
			$("#yn_type").val(tphjlb);
			
			$("#TiaoPeiShiJianPrint_year").text(y);
			$("#TiaoPeiShiJianPrint_month").text(m);
			$("#TiaoPeiShiJianPrint_date").text(d);
				/*var tphjtext = $('#tp_y').children('option:selected').text();
				$("#TiaoPeiShiJianPrint_year").text(tphjtext);
				alert(y+"-"+m+"-"+d+"  "+tphjlb);
				tphjtext = $('#tp_m').children('option:selected').text();
				$("TiaoPeiShiJianPrint_month").text(tphjtext);
				tphjtext = $('#tp_d').children('option:selected').text();
				alert(y+"-"+m+"-"+d+"  "+tphjlb);
				$("#TiaoPeiShiJianPrint_date").text(tphjtext);
				alert(y+"-"+m+"-"+d+"  "+tphjlb);*/
			tphjtext = $('#yn_type').children('option:selected').text();
			$(".TiaoPeiLeiXingSPAN").text(tphjtext);
			
		}
	}
})();

function clearScreenData(){
	TIME_INIT.clearData();
	setTimeout(function(){save();},100);
}
//��������
function fontSet(){ 
	var win = new Ext.Window({
		html : '<button style="margin:10px;"  onclick="$(\'#iframe_fontSet\').attr(\'src\',\''+src+'\');this.disabled=true;">�����ʼִ�е���</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
		title : '��������',
		layout : 'fit',
		width : 400,
		height : 350,
		closeAction : 'close',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{}
		             
	});
	win.show();	
}

//��������
function calNL(){
	//NLJZRQ
	save("savefirst");
	$("#cal_age_year").val($("#NL_year").html() + "-" + $("#NL_month").html() +"-"+$("#NL_date").html());
	radow.doEvent('calNL'); 
}


//���������Excel
function exportTPB(expType){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames=ExpTPB';
	//alert(path);
	ShowCellCover('start','ϵͳ��ʾ','��������ɲ����佨�鷽�� ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType},
        callback: function (options, success, response) { 
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				 //alert(cfg.messageCode)
 					if(0==cfg.messageCode){
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}
 	 						
 	 						//console.log(cfg.elementsScript);
 	 						eval(cfg.elementsScript);
 						}else{
 							
 		 					
 						}
 						
 					}else{
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

//���������Excel
function exportTPBExcel(){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var expType = "";
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames=ExpTPBExcel';
	//alert(path);
	ShowCellCover('start','ϵͳ��ʾ','��������ɲ���Ա��Ϣһ���� ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 6000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				 //alert(cfg.messageCode)
 					if(0==cfg.messageCode){
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}
 	 						
 	 						//console.log(cfg.elementsScript);
 	 						//alert("�ļ��Ѿ����ɣ�");
 	 						eval(cfg.elementsScript);
 						}else{
 							
 		 					
 						}
 						
 					}else{
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

//ͨ��ajax����
function eventajax(eventType,params,cbfun){
	var path = g_contextpath + '/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames='+eventType;
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : params,
        callback: function (options, success, response) {
      	   if (success) {
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);  
 	      		   try{ 
	      		   		var remoteServerUrl = cfg.selfResponseFunc;
	      		   		$("#rmburl").val(remoteServerUrl);
 	      		   }catch(e){
 	      			   alert(e);
 	      		   }
 	      		   
 				  cbfun(cfg.messageCode)
 				}
      	   }
        }
   });
}


//��ʾ������
function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}
function openProcessWin(v){//�����ϻ����
	var ynId = $("#ynId").val();
	var src = g_contextpath+'/PublishFileServlet?method=expSHCL&ynId='+ynId+'&exptype='+v;
	var selecthtml = '<select id="expParm"  name="expParm" style="margin:10px;"> '+
		  '<option value ="&cur_hj=TPHJ3">����</option>'+
		  '<option value="&cur_hj=TPHJ4,TPHJ5">��ǻ᳣ί��</option>'+
		'</select>';
		
	var win = new Ext.Window({
		html : '<button style="margin:10px;"  onclick="$(\'#iframe_expFile\').attr(\'src\',\''+src+'\');this.disabled=true;">�����ʼִ�е���</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
		title : '�����ϻ����',
		layout : 'fit',
		width : 400,
		height : 350,
		closeAction : 'close',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{}
		             
	});
	win.show();
}
 
function expTable(){
	 
}
 
function CloseAfterPrint(){ 
	try{
		var tata = document.execCommand("print");
		if(tata=tata){ 
			$("#coordTable").css("width","1486px");
			$("#coordTable_div").css("overflow","scroll");
			$("#coordTable_div").css("width","1486px");
			$("#coordTable_div").css("height","500");			    
		} else {
			setTimeout("CloseAfterPrint();",1000);
		}
	}catch(e){
		alert(e);
	}
} 

function printTable11(){
		$("#coordTable").css("width","1486px");
		$("#coordTable_div").css("overflow","hidden");
		$("#coordTable_div").css("width","1486px");
		$("#coordTable_div").css("height","");	 
		$("#coordTable tr td").css("position","");
		  
		CloseAfterPrint();
}
	
function printTable()
{  
	
    var tableToPrint = document.getElementById('coordTable');//��Ҫ����ӡ�ı��
    var newWin= window.open(g_contextpath + "/pages/xbrm2/printpreview.jsp",'newwindow','height='+screen.height+',width='+screen.width+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');//�´�һ���մ���
    return;
    
    newWin.document.write('<link rel="stylesheet" href= "'+g_contextpath + '/pages/xbrm2/gbtp2print.css">');
    
    newWin.document.write('<p class="BiaoTouP" style="text-align:center">��&nbsp;��&nbsp;��&nbsp;Ϣ&nbsp;һ&nbsp;��&nbsp;�� </font></p>');
    newWin.document.write('<p class="TiaoPeiShiJian" style="text-align:right">'+$("#TiaoPeiShiJianPrint").html()+'</p>');
    newWin.document.write(' <style media="print"> '+    		
    		'	@page { '+   
    		'   size:landscape;'+ 
    		'   } '+ 
    		' *{margin:0px!important;padding:0px!important;} '+
    		' body{  '+
    		' margin: 1px;overflow-y: scroll;overflow-x: hidden; ' +
    		' font-family:"����",Simsun; ' +
    		' word-break:break-all; ' +
    		' margin-bottom: 250px; ' +
    		' overflow-y:hidden; ' +
    		' } ' +
			'    .kcclClass{ ' +
			'    background-color: rgb(102,204,255) !important; ' +
			'    } ' +
			'    .drag_color{ '+
			'    	background-color: rgb(232,232,232) !important; ' +
			'    } '+
			'    .drag_pre_color{ '+
			'    	background-color: rgb(233,250,238)!important; ' +
			'    } '+
			'    .default_color{ '+
			'    	background-color: #FFFFFF !important; ' +
			'    } '+
			'    </style> ');
    newWin.document.write('<body style="overflow-x:auto:overflow-y:auto">');
    newWin.document.write(tableToPrint.outerHTML);//�������ӽ��µĴ���
    newWin.document.write('</body>');
    newWin.document.close();//��IE�������ʹ�ñ��������һ��
    newWin.focus();//��IE�������ʹ�ñ��������һ�� 
    remove_ie_header_and_footer(); 
   	
}  
function remove_ie_header_and_footer() {
  	    var hkey_root, hkey_path, hkey_key;
  	    hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
  	    try {
  	        var RegWsh = new ActiveXObject("WScript.Shell");
  	        RegWsh.RegWrite(hkey_path + "header", "");
  	        RegWsh.RegWrite(hkey_path + "footer", "");
  	    } catch (e) {}
  	} 
//��ӡ
var Print = (function(){
	var wdapp = $h.getTopParent().WORD_PRINT;
	
	return {
		printWord : function(){
			if(!wdapp){
				try{ 
					wdapp = new ActiveXObject("Word.Application");
			  	}catch(e){ 
				    alert("�޷�����Office������ȷ�����Ļ����Ѱ�װ��Office���ѽ���ϵͳ��վ�������뵽IE������վ���б��У�����ActiveX�ؼ�ѡ��������ã�"); 
				    return; 
			  	}
			}
			Ext.MessageBox.wait('���ڴ�ӡ�У����Ժ󡣡���');
			radow.doEvent("expWord");
		},
		printWordNoRM : function(){
			if(!wdapp){
				try{ 
					wdapp = new ActiveXObject("Word.Application");
			  	}catch(e){ 
				    alert("�޷�����Office������ȷ�����Ļ����Ѱ�װ��Office���ѽ���ϵͳ��վ�������뵽IE������վ���б��У�����ActiveX�ؼ�ѡ��������ã�"); 
				    return; 
			  	}
			}
			Ext.MessageBox.wait('���ڴ�ӡ�У����Ժ󡣡���');
			radow.doEvent("expWord","NORM");
		},
		startPrint : function(downloadUUID,sid){
			//var url=window.location.protocol+"//"+window.location.host+
			//g_contextpath+'/PublishFileServlet;jsessionid='+sid+'?method=downloadFile&uuid='+downloadUUID;
			//wdapp.Documents.Open(url);//��wordģ��url
			wdapp.Application.Printout();
		}
	}
  	
  	
})();




//����
function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src',g_contextpath + '/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
window.onbeforeunload=function(){
	//realParent.infoSearch();
}
  
//Ctrl+S����
document.onkeydown=function()   {
    if (event.ctrlKey == true && event.keyCode == 83) {//Ctrl+S
        event.returnvalue = false;
        $("tr td textarea").focusout()
        $("#saveid").click();
    	setTimeout(function(){save();},100);
        
    }
 
}

function CopyTable()
{
//������񿽱���EXCEL��
var printArea = document.getElementById("selectable");
try{
	var word =new ActiveXObject("Word.Application");
	var doc =word.Documents.Add("",0,1).Range(0,1);
	var sel=document.body.createTextRange();
	sel.moveToElementText(printArea);
	sel.execCommand("Copy");
	doc.Paste();
	word.Application.Visible = true;
	word=null;
	
} catch (e) {
	alert("��������ʧ�ܣ���Ҫ�ڿͻ�������װMicrosoft Office Word(���鰲װ2010�Ժ�汾)������ǰվ���������վ�㣬������IE������ActiveX�ؼ���");
}
}


var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->'+
    ' <style type="text/css">'+
    '.excelTable  {'+
    'border-collapse:collapse;'+
    ' border:thin solid #999; '+
    '}'+
    '   .excelTable  th {'+
    '   border: thin solid #999;'+
    '  padding:20px;'+
    '  text-align: center;'+
    '  border-top: thin solid #999;'+
    ' '+
    '  }'+
    ' .excelTable  td{'+
    ' border:thin solid #999;'+
    '  padding:2px 5px;'+
    '  text-align: center;'+
    ' }</style>'+'</head><body><table border="1">{table}</table></body></html>'
        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
        var downloadLink = document.createElement("a");
        downloadLink.href = uri + base64(format(template, ctx));
        downloadLink.download = name+".xls";
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    }
})()


	function onAppointment(){  
		try{ 
			doAddPerson.appointment(selectedRow);
		}catch(e){
			alert(1);
		}
	}
 	
 	function onappendouter(){
 		try{
 			$('#coordTable').append();
 		}catch(e){
 			alert(e);
 		}
 	}
 	
 	var objTD = null;
 	function closeChangFontWindow(strHTML){ 
 		try{
 	 		var rowIndex = $(objTD).parent().index();
 	 		var colIndex = $(objTD).index();
 	 		var strtext = $(strHTML).text();
 	 		try{
	    		var colName = "";
	    		if ($(this).parent().children().length<4){
	    			colName = "tp0101";
	    		}else{
	    			colName = GLOBLE['COL_CONFIG_3'][colIndex];
	    		}
	    		var rowId = GetRowid(rowIndex); 
	    		GLOBLE['ID_ROWINFO'][rowId][colName] = strtext;
	    		
 	 		}catch(e){
 	 			alert(e);
 	 		}
	 		var jlfl = Ext.getCmp("changFont");
	 		jlfl.hide();
 		}catch(e){
 			alert(e);
 		}
 	}
 	
 	/**
 	 * ��ʽ����
 	 * 
 	 */
 	function adjStyle(){
 		var src = g_contextpath + "/pages/xbrm2/ueditor-1.4.3.3/_examples/adjStyle.jsp?text="; 		
 		window.open(src,'fullscreen','fullscreen,toolbar=no,  menubar=no,location=no,   directories=no,   copyhistory=no,  scrollbars=no,   resizable=no,status=no')   ;  
 	}
 	function changFont(obj){ 
 		if (!obj){
 			return;
 		}
 		if ($("#fontCheckBox").val()!="3"){
 			Ext.Msg.alert('ϵͳ��ʾ:',"�뵯��ģʽ�µ������壡");
 			return;
 		}
 		
 		objTD = obj;
 		var text = $(obj).html(); 
 		if ($("#fontSetFrame").attr("id")){
 			//$("#fontSetFrame").attr("src",src);
 			var jlfl = Ext.getCmp("changFont");
 			jlfl.show(); 
 			fontSetFrame.editor.html(text);
 			return;
 		} 
 		if (text.indexOf('<')>=0){
 			text = $(text).html();
 		}
 		if (text=="&nbsp;"){
 			text = "";
 		} 		
 		text = encodeURI(text);
 		
 		var src = g_contextpath + "/pages/xbrm2/changefont.jsp?text="+text;
 		var win = new Ext.Window({
 			html : '<iframe id="fontSetFrame" name="fontSetFrame" style="width:100%;height:220" src='+src+'></iframe>',
 			title : '��������',
 			layout : 'fit',
 			left:0,
 			top:0,
 			width : 480,
 			height : 220,
 			closeAction : 'close',
 			closable : true,
 			modal : false,
 			id : 'changFont',
 			collapsed:false,
 			collapsible:false,
 			bodyStyle : 'background-color:#FFFFFF',
 			plain : true,
 			titleCollapse:false,
 			listeners:{}     
 		});
 		win.show();	 
 	} 
 	
	function loginTemplateDB(){  
		var u = g_loginTemplate; 
		var url = remoteServer + '/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'ganbu'}"; 
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","myframe");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		} 				
	}  	