<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>

<%@page isELIgnored="false" %>

<script src="<%=request.getContextPath()%>/pages/fxyp/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/tableEditer.js" type="text/javascript"></script>
<%-- <script src="<%=request.getContextPath()%>/pages/fxyp/js/rxfxyp-view.js" type="text/javascript"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/fxyp/gbtp.css">

<style>
body{
margin: 1px;overflow-y: hidden;overflow-x: hidden;
font-family:'����',Simsun;
word-break:break-all;
margin-bottom: 250px;
}

.kcclClass{
background-color: rgb(102,204,255) !important;
}
.drag_color{
	background-color: rgb(232,232,232) !important;
}
.drag_pre_color{
	background-color: rgb(233,250,238)!important;
}
.default_color{
	background-color: #FFFFFF !important;
}
.cut_color{
	background-color: #D3D3D3 !important;
}
input{
	border: 1px solid #c0d1e3 !important;
}
</style>
<odin:hidden property="ynId"/>



<script type="text/javascript">
document.title = '��ѡ��������';
parent = window;
/* $(function(){
	$.fn.smartFloat = function() {
		 var position = function(element) {
		  var top = element.position().top;
		  $(window).scroll(function() {
		   var scrolls = $(this).scrollTop();
		   element.css({
			      top: scrolls+top
			     }); 
		  });
		 };
		 return $(this).each(function() {
		  position($(this));      
		 });
		};


		$(".btncls").smartFloat();
}) */



var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function(){
	$('#mntp00').val(parentParam?parentParam.mntp00:"922e35a7-235c-45b1-b570-6fefea12dd62");
});
	Array.prototype.removeIndex = function (index) {
	  if (index > - 1) {
	    this.splice(index, 1);
	  }
	};
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null")?" ":v.replace(/\n/g,"<br/>"));
	}
	//�����кŻ�ȡrowid
	function GetRowid(rowIndex){
		return GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset];
	}
	
	

	
	
	$(document).ready(function () {
		$(function(){
		});
	})
</script>

<script type="text/javascript">
var GLOBLE={};
GLOBLE.colOffset=1;//�����п�ʼ�ɱ༭����
GLOBLE.rowOffset=1;//�ڶ��п�ʼ�ɱ༭����
//�����кŴ洢��Աid �к�=index+1
GLOBLE['ROWID']=[];
//���������кŻ�ȡ����
GLOBLE['COL_CONFIG_3']={"2":"fxyp02","4":"tp0101","5":"tp0102","6":"tp0103","7":"tp0104","8":"tp0105","9":"tp0106","10":"nm_a0192a"};
//���к������ ��Ŷ���
GLOBLE['CUTEDDATA']={};
GLOBLE['TABLEROW']=[];
GLOBLE['CUTEDROWID']=[];

//����id�洢����Ϣ
GLOBLE['ID_ROWINFO']={};

//�����к������ź��кš�
var complate = function () {
    $('#coordTable tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    var k = 1;
    $('#coordTable tr:gt(0) td:nth-child(2)').each(function (i, item) {
    	if($(this).attr('gw')=='true'){
    		$(this).text(k++);
    	}
        
    });
    k = 1;
    $('#coordTable tr:gt(0) td:nth-child(4)').each(function (i, item) {
    	if($("td:nth-child(2)", $(this).parent()).attr('gw')=='true'){
    		k = 1;
    		$(this).text(k++);
    	}else{
    		$("td:nth-child(2)", $(this).parent()).text(k++);
    	}
    });
}

//������
//��λ���ж���  ��λ��Ҫ�кϲ� isHeBing �� ��Ҫ�ϲ�����countf
var createRow = function (rowIndex,guid,rowData,isHeBing,countf,rowCount,trCount) {//splice(index, 0, val);
	var borderTop = '';
	if(rowCount>0&&(countf==0||isHeBing)){//�״μ���
		borderTop = 'borderTop';
	}
	if(rowIndex>1&&(countf==0||isHeBing)){//����
		borderTop = 'borderTop';
	}
	if(rowIndex==0&&trCount>1&&(countf==0||isHeBing)){//׷��
		borderTop = 'borderTop';
	}
	if(!guid){
		guid = GUID();
	}
	var tr;
	var rowd;
	
	tr = $('<tr class="data">'+
			'<td class="rownum default_color"></td>'+//�鿴
			//'<td class="TNR"></td>'+//���
			(countf==0?'<td gw="true" class="TNR"></td>':(isHeBing?'<td gw="true" rowSpan="'+countf+'" class="TNR"></td>':''))+
			(countf==0?'<td class="align-left '+borderTop+'">&nbsp;</td>':(isHeBing?'<td rowSpan="'+countf+'" class="align-left  '+borderTop+'">&nbsp;</td>':''))+//��λ����
			'<td class="TNR  '+borderTop+'"></td>'+//���
			'<td class=" '+borderTop+'">&nbsp;</td>'+//����
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//��������
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//����ְʱ��
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//��ͬ��ʱ��
			'<td class=" '+borderTop+'">&nbsp;</td>'+//ѧ��ְ��
			'<td class="align-left '+borderTop+'">&nbsp;</td>'+//����ְ��
			'<td class="align-left '+borderTop+'">&nbsp;</td>'+//����ְ��
			'</tr>');
	
	if(rowData){
		var tsIndex = 0;
		var tds = $("td:nth-child(n+3)", tr);
    	if(isHeBing||countf==0){
    		var fxyp07 = rowData["fxyp07"]
    		if(fxyp07=='1'){
    			var zwqc01 = rowData["zwqc01"];
    			var v = rowData["fxyp02"];
    			if(zwqc01&&parseInt(zwqc01)>1){
    				v = v + "("+zwqc01+")";
    			}
	      		rm = "��"
	      		
	     		SetTDtext(tds[tsIndex++],(v==""||v==null||v=="null")?"":(rm+v));
	      	}else if(fxyp07=='-1'){
	      		rm = "��"
	      		var v = rowData["fxyp02"];
		     	SetTDtext(tds[tsIndex++],"");
			}
    		
    		tsIndex++
    	}
    	
    	SetTDtext(tds[tsIndex++],rowData["tp0101"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0102"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0103"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0104"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0105"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0106"]);
    	SetTDtext(tds[tsIndex++],rowData["nm_a0192a"]);
    	rowd=rowData;
	}else{
    	rowd={"fxyp07":"","tp0100":guid,"fxyp02":"","tp0101":"","tp0102":"","tp0103":"","tp0104":"","tp0105":"","tp0106":"","tp0107":"","tp0108":"","tp0109":"","tp0110":"","tp0111":"","tp0112":"","tp0113":"","tp0114":"","tp0115":"",
    			"nm_a0192a":"","nm_zwqc00":"","nm_a0000":"","nm_tp0100":""};
    	
	}
	//�������ݶ���
	if(rowIndex>0){
		GLOBLE['ROWID'].splice(rowIndex-GLOBLE.rowOffset, 0, guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}else if(rowIndex==0){//�ڱ�ͷ�ϱ�ʾ׷��
		GLOBLE['ROWID'].push(guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}
    return tr;
}



var menus = (function(){
	var $obj;
	var isAppend;//�Ƿ�������λ
	var appendFxyp00;//������λʱ��fxyp00
	var ret = {
		setAppendInfo : function(a,f){
			isAppend = a;
			appendFxyp00 = f
		},
		rowsPasted:function(o){//ճ������
    		var rowIndex = $(o).index();
			var $tr = $(o);
    		while($tr.length>0&&$("td:nth-child(2)", $tr).attr('gw')!='true'){
    			$tr = $tr.prev();
    		}
    		var delFormIndex = $tr.index();//�ӵڼ��п�ʼ��
		
			var plist = [];
			$.each(GLOBLE['CUTEDROWID'],function (i, item){
				plist.push(GLOBLE['CUTEDDATA'][item]);
    		});
    	
    		doAddPerson.addPerson(plist,delFormIndex);
    		
    		if(GLOBLE['TABLEROW']){
    			$.each(GLOBLE['TABLEROW'], function () {
       			 	$(this).remove();//ҳ���Ƴ���λ��Ϣ
       			});
    		}
    		
    		
    		
    		var sortInfo = "";
    		$.each(GLOBLE['ROWID'],function (i, item){
    			sortInfo = sortInfo + GLOBLE['ID_ROWINFO'][item]['zwqc00']+"@";
    		});
    		$('#gwSort').val(sortInfo);
    		radow.doEvent("gwSort");
    		
    		//��ջ���
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
    		GLOBLE['TABLEROW']=[];
    	},
		updateData:function($tr){//���з���
			if($tr==null){
				$tr = $obj;
			}
    		var delIndex = $($tr).index();//ѡ��ĵ�ǰ��
    		var delId = GetRowid(delIndex);
    		
    		var rows = [];
    		while($tr.length>0&&$("td:nth-child(2)", $tr).attr('gw')!='true'){
    			$tr = $tr.prev();
    		}
    		var delRowsCount = $("td:nth-child(2)", $tr).attr('rowspan');//��ǰ�����ڸ�λ������
    		var delFormIndex = $tr.index();//�ӵڼ��п�ʼ��
    		
    		
    		rows.push($tr);
    		for(var x=0;x<delRowsCount-1;x++){
    			$tr = $tr.next();
    			rows.push($tr);
    		}
    		var delToIndex = $tr.index();//ɾ�����ڼ���
    		var lastTrIndex = $tr.parent().children().last().index();//���һ��
    		if(delToIndex==lastTrIndex){//ɾ���������һ�� ��׷��
    			delFormIndex = 0;
    		}
			
			
			
			
    		var curCache = [];
    		$.each(rows, function () {
                var rowIndex = $(this).index();
                var rowId = GetRowid(rowIndex);
                GLOBLE['CUTEDROWID'].push(rowId);
                curCache.push(rowId);
              //���к������ ��Ŷ���
                GLOBLE['CUTEDDATA'][rowId]=GLOBLE['ID_ROWINFO'][rowId];
               
            });
    		//���е��� �û�
    		$.each(rows, function () {
    			 $(this).addClass('cut_color');//����������ɾ������Ӱ��tr���к�
    		}); 
    		GLOBLE['TABLEROW'] = GLOBLE['TABLEROW'].concat(rows);  //���е��� �û�
    		
    		
    		$.each(curCache, function (i,item) {
    			//ɾ��id
    			GLOBLE['ROWID'].splice($.inArray(item,GLOBLE['ROWID']),1);
    			delete GLOBLE['ID_ROWINFO'][item];
    		});
    	},
		getDelInfo : function($tr,delType){//ɾ����λ��ѡʱ��ʾ�����Ϣ��ȡ
			var delIndex = $($tr).index();
    		var rowId = GetRowid(delIndex);
    		var rowData = GLOBLE['ID_ROWINFO'][rowId];
    		var a0000 = rowData["a0000"];
        	
    		var retInfo='';
    		if('P'==delType){
    			if(a0000==""||a0000==null){
            		return null;
            	}
    			retInfo = "��Ա��"+"��"+rowData['tp0101']+"��";
    		}else if('GW'==delType){
    			retInfo = "��λ��"+"��"+rowData['fxyp02']+"��";
    		}
    		return retInfo;
		},
		rowsDelete : function($tr,delType){//ɾ����ѡ  ɾ����Ա ǰ̨ɾ��ȫ����λ��Ա  ��̨ɾ����  Ȼ�����¼��ظ�λ
			if(isAppend=='APPEND'){//���Ӹ�λ
	    		radow.doEvent("deletePerson",""+"@@"+"0"+"@@"+appendFxyp00+"@@"+isAppend);
				return;
			}
			if($tr==null){
				$tr = $obj;
			}
    		var delIndex = $($tr).index();//��̨ʵ��ɾ������
    		var delId = GetRowid(delIndex);
    		var fxyp00 = GLOBLE['ID_ROWINFO'][delId]['fxyp00'];
    		
    		var rowData = GLOBLE['ID_ROWINFO'][delId];
    		
    		var rows = [];
    		while($tr.length>0&&$("td:nth-child(2)", $tr).attr('gw')!='true'){
    			$tr = $tr.prev();
    		}
    		var delRowsCount = $("td:nth-child(2)", $tr).attr('rowspan');//ɾ��������
    		var delFormIndex = $tr.index();//�ӵڼ��п�ʼɾ��
    		
    		
    		rows.push($tr);
    		for(var x=0;x<delRowsCount-1;x++){
    			$tr = $tr.next();
    			rows.push($tr);
    		}
    		var delToIndex = $tr.index();//ɾ�����ڼ���
    		var lastTrIndex = $tr.parent().children().last().index();//���һ��
    		if(delToIndex==lastTrIndex){//ɾ���������һ�� ��׷��
    			delFormIndex = 0;
    		}
    		
    		//���� id
    		var nmid = "";
    		$(rows).each(function(){
    			var rowIndex = $(this).index();
    			if(rowIndex==0){//��ͷ
    				return;
    			}
    			if(GLOBLE['ID_ROWINFO'][GetRowid(rowIndex)]["nm_zwqc00"]){
    				nmid = nmid + "," + GLOBLE['ID_ROWINFO'][GetRowid(rowIndex)]["nm_zwqc00"]
    			}
    			//ɾ��id
    			delete GLOBLE['ID_ROWINFO'][GetRowid(rowIndex)];
    			GLOBLE['ROWID'].removeIndex(rowIndex-GLOBLE.rowOffset);
    			$(this).remove();
        	});
    		if(nmid.length>0){
    			nmid = nmid.substr(1,nmid.length);
    		}
    		
    		
    		//��̨ɾ��
    		radow.doEvent("deletePerson",delId+"@@"+delFormIndex+"@@"+fxyp00+"@@"+delType+"@@"+rowData["zwqc00"]+"@@"+(rowData["nm_zwqc00"]||"")+"@@"+(rowData["nm_tp0100"]||"")+"@@"+nmid+"@@x");
    	},
    	szrxtj : function(o){//������ѡ����
    		$obj = $(o);
    		ret.setAppendInfo(null,null);
    		var rowIndex = $(o).index();
    		if(rowIndex==0){
    			return;
    		}
    		var rowId = GetRowid(rowIndex);
    		var rowData = GLOBLE['ID_ROWINFO'][rowId];
			var fxyp07 = rowData["fxyp07"];
			if(fxyp07=='-1'){//��
				$h.alert('','����ְ����������ѡ��');
				return;
			}
    		
    		
    		var fxyp00 = rowData['fxyp00'];
    		openRenXuanTiaoJian(fxyp00);
    	},
    	czygrx : function(o){//�����й���ѡ
    		$obj = $(o);
    		ret.setAppendInfo(null,null);
    		var rowIndex = $(o).index();
    		if(rowIndex==0){
    			return;
    		}
    		var rowId = GetRowid(rowIndex);
    		var rowData = GLOBLE['ID_ROWINFO'][rowId];
			var fxyp07 = rowData["fxyp07"];
			if(fxyp07=='-1'){//��
				$h.alert('','����ְ����������ѡ��');
				return;
			}
    		var fxyp00 = rowData['fxyp00'];
    		openYouGuanRenXuann(fxyp00);
    	}
	}
	return ret;
})();

$(document).ready(function () {


//��������
    $('#coordTable').coordTable({
        selecte_col_len: 4, //����ÿ�е�����
        selector_row: 'tr.data td:nth-child(1)', //һ��jqueryѡ��������ʾ���Ե��ѡ���е�����
        selector_td: 'tr.data td:nth-child(n+2)', //һ��jqueryѡ��������ʾ�����������ק������
        deleted: complate,
        create_row: createRow,
    	rowsDelete : menus.rowsDelete,
    	updateData : menus.updateData,
    	rowsPasted : menus.rowsPasted,
    	szrxtj : menus.szrxtj,
    	getDelInfo : menus.getDelInfo,
    	setAppendInfo : menus.setAppendInfo,
    	czygrx : menus.czygrx
    });

    $('#coordTable').tableEditer({
        selector: 'tr.data td:nth-child(n+3)',
    	getValue:function(rowIndex,colIndex,$td){
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex);
    		var text = GLOBLE['ID_ROWINFO'][rowId][colName];
    		return text==null?"":text;
    		
    	},
    	getValueObj:function(rowIndex,colIndex,$td){
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex);
    		return GLOBLE['ID_ROWINFO'][rowId];
    		
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		
    		//�������ݶ���
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex);
    		var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    		//���Ǹ�λ  �ɱ༭
        	if((a0000==""||a0000==null)&&colName!='fxyp02'){
        		SetTDtext(obj,"");
        		return;
        	}
    		var v = value;
        	if(colName=='fxyp02'){
				var rowId = GetRowid(rowIndex);
				var rowData = GLOBLE['ID_ROWINFO'][rowId];
				var fxyp07 = rowData["fxyp07"];
				if(fxyp07=='1'){
					rm = "��"
					var zwqc01 = rowData["zwqc01"];
	    			if(zwqc01&&parseInt(zwqc01)>1){
	    				v = v + "("+zwqc01+")";
	    			}
				}else if(fxyp07=='-1'){
					rm = "��"
				}
				SetTDtext(obj,(v==""||v==null||v=="null")?"":(rm+v));
            }else{
            	SetTDtext(obj,value);
            }
    		
    		GLOBLE['ID_ROWINFO'][rowId][colName]=value;
    		rowData = GLOBLE['ID_ROWINFO'][rowId];
    		//���º�̨
    		radow.doEvent("save",colName+"@@"+value+"@@"+rowData["fxyp00"]+"@@"+rowId+"@@"+rowData["zwqc00"]+"@@"+rowData["nm_zwqc00"]+"@@"+rowData["nm_tp0100"]);
    	}
    }); 

  //���ѡ����
    $("#coordTable tr.data td:nth-child(1)").live('dblclick', function (e) {
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	}
    	$h.showModalDialog('personInfoOP',g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000,'��Ա��Ϣ�޸�',1009,730,null,
		{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
        return false;
    });
    
 });

function genTPB(){
	radow.doEvent("genTPB");
}
function infoSearch(){}
function openGDview(yn_id){
	$h.openPageModeWin('qcjs','pages.yntp.TPB','�ɲ����佨�鷽��',1150,800,
			{yn_id:yn_id,yn_type:'TPHJ1',scroll:"scroll:yes;"},g_contextpath);
}
function editorfxyp02(fxyp00,fxyp02){
	/* Ext.MessageBox.buttonText = {
      ok     : "ȷ��",
      cancel : "ȡ��",
      yes    : "��",
      no     : "��"
   }; */
   /* Ext.MessageBox.buttonText.ok='ȷ����������ѡ������';
	Ext.MessageBox.prompt("�������λ���ƣ�",'',function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("addInfo",txt);
			 //openRenXuanTiaoJian('');
		 }
		 Ext.MessageBox.buttonText.ok='ȷ��';   
	},this,true,fxyp02);  */
	window.dialogArguments.window.openZSGW();//��ְ����λ����
	window.close();
	
}

function openRenXuanTiaoJian(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(!newWin_){
		newWin_ = $h.openWin('rxtj','pages.fxyp.RenXuanTiaoJian','�ɲ�������ѡ���� ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_rxtj").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide(); 
	}
}


function openYouGuanRenXuann(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(!newWin_){
		newWin_ = $h.openWin('ygrx','pages.fxyp.YouGuanRenXuan','�й���ѡ���� ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_ygrx").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.reload();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide(); 
	}
}
function hideWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide();
	}
}
</script>




<!-- <div class="btncls">
	<div class="top_btn_style"  style="background-color:#F08000;margin-top: 15px; " onclick="save()">���桾Ctrl+S��</div>
<div id="saveid"></div>
</div> -->

<div id="selectable" style="overflow-y:scroll;overflow-x:hidden;height: 500px;">
<div style="text-align:left; margin-left:120px;margin-top:20px;margin-bottom:100px; width:1000px;">
<p class="BiaoTouP">��&nbsp;��&nbsp;��&nbsp;��&nbsp;��&nbsp;ѡ&nbsp;��&nbsp;��&nbsp;��&nbsp;��&nbsp;��</p>

<!-- <div class="top_btn_style chakan" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px;display:none;
 width: 150px;" onclick="editorfxyp02()">���Ӹ�λ
</div> -->
<div class="top_btn_style chakan" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px;
 width: 150px;" onclick="rybd()">��Ա�ȶ�
</div>
<!-- <div class="top_btn_style" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px; margin-left:530px;
 width: 150px;" onclick="genTPB()">���������ñ�
</div> -->
<!-- <div class="top_btn_style bianji toggle" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px; margin-left:824px;
 width: 80px;" onclick="bianji()">�༭
</div>
<div class="top_btn_style chakan toggle" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px; margin-left:824px; display:none;
 width: 80px;" onclick="chakan()">���
</div> -->
<!-- <div class="top_btn_style" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px;margin-left:724px;
 width: 80px;" onclick="tuiSong()">����
</div> -->
<table id="coordTable" cellspacing="0" width="100%" style="">
	<tr>
	    <th style="width:3%">��<br/>��</th>
	    <th style="width:3%">��<br/>��</th>
	    <th id="GangWeiMingCheng" style="width:15%">�������λ����</th>
	    <th style="width:3%">��<br/>��</th>
	    <th style="width:7%">����</th>
	    <th style="width:7%">����<br/>����</th>
	    <th id="RenXianZhiShiJian" style="width:7%">����ְ<br/>ʱ��</th>
	    <th id="RenTongJiShiJian" style="width:7%">��ͬ��<br/>ʱ��</th>
	    <th style="width:10%">ѧ��</th>
	    <th id="XianRenZhiWu" style="width:19%">����ְ��</th>
	    <th id="NiRenMianZhiWu" style="width:19%">����ְ��</th>
	</tr>
	
</table>
<!-- <table id="coordTable2" cellspacing="0" width="100%" >
	<tr>
	    <th style="width:3%">��<br/>��</th>
	    <th style="width:3%">��<br/>��</th>
	    <th id="GangWeiMingCheng2" style="width:15%">�������λ����</th>
	    <th style="width:3%">��<br/>��</th>
	    <th style="width:7%">����</th>
	    <th style="width:7%">����<br/>����</th>
	    <th id="RenXianZhiShiJian2" style="width:7%">����ְ<br/>ʱ��</th>
	    <th id="RenTongJiShiJian2" style="width:7%">��ͬ��<br/>ʱ��</th>
	    <th style="width:10%">ѧ��<br/>ְ��</th>
	    <th id="XianRenZhiWu2" style="width:19%">����ְ��</th>
	    <th id="NiMianZhiWu" style="width:19%">����ְ��</th>
	</tr>
	
</table> -->

	
</div>
</div>

<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<script type="text/javascript">
$(function(){
	var ah = $(window).height();
	$('#selectable').css('height',ah);
});

var doAddPerson = (function(){//������Ա
	return {
		queryByNameAndIDS:function(list,fxyp00){//��������ѯ
			$("#a0000s").val(list);
			radow.doEvent('queryByNameAndIDS',fxyp00);
		},
		addPerson:function(plist,rowIndex){//��������ѯ ������Ա
			if(!rowIndex){
				rowIndex = 0;
			}
			var trCount =  $("#coordTable tr").length;
       		if(rowIndex>0){
       			var fxyp00Prev = '';
       			var insertTR = $("#coordTable tr:nth-child("+(rowIndex+1)+")");
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					var $tr = createRow(rowIndex+i,item["tp0100"],item,isHeBing,countf,i,trCount);
					$tr.insertBefore(insertTR);
          		});
      		}else if(rowIndex==0){//׷��
      			var fxyp00Prev = '';
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					var $tr = createRow(rowIndex,item["tp0100"],item,isHeBing,countf,i,trCount);
          			$("#coordTable").append($tr);
          		});
      		}
			
			complate();
		}
	}
})();

function save(p){
	var ID_ROWINFO = Ext.encode(GLOBLE['ID_ROWINFO']);
	var ROWID = Ext.encode(GLOBLE['ROWID']);
	$('#ID_ROWINFO').val(ID_ROWINFO);
	$('#ROWID').val(ROWID);
	//console.log(ID_ROWINFO)
	if(p){
		radow.doEvent('save',p);
	}else{
		radow.doEvent('save');
	}
	
}

var TIME_INIT = (function(){
	return {
		clearData : function(){
			$(".data").remove();
			GLOBLE['ROWID']=[];
			GLOBLE['ID_ROWINFO']={};
			//��ջ���
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
    		GLOBLE['TABLEROW']=[];
		}
	}
})();







function eventajax(eventType,params,cbfun){
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.yntp.TPB&eventNames='+eventType;
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
 				  cbfun(cfg.messageCode)
 				}
      	   }
        }
   });
}



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






function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

//Ctrl+S����
/* document.onkeydown=function()   {
    if (event.ctrlKey == true && event.keyCode == 83) {//Ctrl+S
        event.returnvalue = false;
        $("tr td textarea").focusout()
        $("#saveid").click();
    	setTimeout(function(){save();},100);
        
    }
 
} */

function rybd(){
	var a0000s='';
	$(".selectTag").each(function(i,item){
		var rowId = GetRowid($(this).index());
   		var rowData = GLOBLE['ID_ROWINFO'][rowId];
   		var a0000 = rowData["a0000"];
   		a0000s = a0000s + a0000 + ',';
	});
	if (a0000s == '') {
		odin.alert("��ѡ����Ա��");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000srybd").val(a0000s);
	radow.doEvent('tpbj.onclick');
}

function clearSelected() {
	 //�б�
   jQuery.coordTable.unSelect($('.selectTag'));
   jQuery.coordTable.unSelect($('.ui-selected'));
   $('#a0000srybd').val('');
}



function tuiSong(v){
	var win = Ext.getCmp("tuiSong");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : 'ѡ�������û�',
		layout : 'fit',
		width : 300,
		height : 161,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'tuiSong',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"tuiSongInfo",
		listeners:{}
		           
	});
	win.show();
}
</script>
<div id="tuiSongInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<tags:ComBoxWithTree property="mnur01" label="ѡ���û�" readonly="true" ischecked="true" codetype="USER" />
		  </tr>
		</table>
		<div style="margin-left: 115px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveTuiSongInfo" />
		</div>
	</div>
</div>



<odin:hidden property="mntp00" />
<odin:hidden property="a0000s" title="��ѯ"/>
<odin:hidden property="a0000srybd" title="��Ա�ȶ�"/>
<odin:hidden property="docpath" />
<odin:hidden property="ID_ROWINFO" title="�������"/>
<odin:hidden property="ROWID" title="�������id"/>
<odin:hidden property="gwSort" title="��λ����"/>


<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>
