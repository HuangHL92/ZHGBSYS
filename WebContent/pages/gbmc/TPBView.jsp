<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/pages/xbrm2/js/tableEditer.js" type="text/javascript"></script>
 
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm2/gbtp.css">

<style>
body{
margin: 1px;overflow-y: scroll;overflow-x: hidden;
font-family:'����',Simsun;
word-break:break-all;
margin-bottom: 250px;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
}
.btncls2{
	position: absolute;
	left: 10px;
	top: 117px;
	z-index: 2000;
	width: 300px;
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
</style>
<odin:hidden property="ynId"/>
<odin:hidden property="yn_gd_id"/>


<script type="text/javascript">



var g_contextpath = '<%= request.getContextPath() %>';
	Ext.onReady(function(){
		$('#ynId').val(parentParam?parentParam.yn_id:"d4535b4b-7374-4279-b6ea-cbd3df29aed4");
		$('#yn_type').val(parentParam?parentParam.yn_type:"TPHJ1");
		$("#yntype").val(parentParam?parentParam.yn_type:"TPHJ1");
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
	
</script>

<script type="text/javascript">
var GLOBLE={};
GLOBLE.colOffset=1;//�����п�ʼ�ɱ༭����
GLOBLE.rowOffset=1;//�ڶ��п�ʼ�ɱ༭����
//�����кŴ洢��Աid �к�=index+1
GLOBLE['ROWID']=[];
//���������кŻ�ȡ����
GLOBLE['COL_CONFIG_1']={"2":"tp0101"};
GLOBLE['COL_CONFIG_2']={"2":"tp0101"};
GLOBLE['COL_CONFIG_3']={"2":"tp0101","3":"tp0102","4":"tp0103","5":"tp0104","6":"tp0105","7":"tp0106","8":["tp0107","tp0108","tp0109","tp0110","tp0111","tp0112","tp0113","tp0114","tp0115"]};
//���к������ ��Ŷ���
GLOBLE['CUTEDDATA']={};
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
    	var id = GetRowid($(this).parent().index());
    	var type = GLOBLE['ID_ROWINFO'][id]["type"];
    	if(type=="3"){
    		$(this).text(k++);
    	}
        
    });
}

//������//{"8":["tp0107","tp0108","tp0109","tp0110", "tp0111","tp0112","tp0113","tp0114","tp0115"]}
var createRow = function (type,rowIndex,guid,rowData) {//splice(index, 0, val);

	if(!type){
		type="3";
	}
	if(!guid){
		guid = GUID();
	}
	var tr;
	var rowd;
	if(type=="1"){
		tr = $('<tr class="data YiJiBiaoTiTR"><td class="rownum default_color"></td><td class="TNR default_color"></td><td colspan="8" class=" YiJiBiaoTiTD">&nbsp;</td></tr>');
		if(rowData){
        	rowd=rowData;
        	var tds = $("td:nth-child(n+3)", tr);
        	SetTDtext(tds[0],rowData["tp0101"]);
		}else{
        	rowd={"tp0100":guid,"type":"1","tp0101":""};
		}
		
	}else if(type=="2"){
		tr = $('<tr class="data ErJiBiaoTiTR"><td class="rownum default_color"></td><td class="TNR default_color"></td><td colspan="8" class=" ErJiBiaoTiTD">&nbsp;</td></tr>');
		if(rowData){
        	rowd=rowData;
        	var tds = $("td:nth-child(n+2)", tr);
        	SetTDtext(tds[0],rowData["tp0101"]);
		}else{
        	rowd={"tp0100":guid,"type":"2","tp0101":""};
		}
	}else{
		var kcclClass = "";//������ϱ�����ɫ
		if(rowData){
			kcclClass = rowData['kcclclass'];
		}
		tr = $('<tr class="data"><td class="rownum default_color"></td><td class="TNR '+kcclClass+' "></td><td>&nbsp;</td><td class="TNR">&nbsp;</td><td class="TNR">&nbsp;</td><td class="TNR">&nbsp;</td><td>&nbsp;</td><td class="align-left">&nbsp;</td><td class="align-left">&nbsp;</td></tr>');
        if(rowData) {
            var tds = $("td:nth-child(n+2)", tr);
            var yntype = $("#yntype").val();
            var tp0103 = "";
            var tp0104 = "";

            SetTDtext(tds[0], rowData["a0101"]);  //   ����
            SetTDtext(tds[1], rowData["a0192a"]);  //   �ֹ�����λ��ְ��ȫ��
            SetTDtext(tds[2], rowData["a0104"]);  //   �Ա�
            SetTDtext(tds[3], rowData["a0117"]);  //   ����
            SetTDtext(tds[4], rowData["a0111a"]);  //   ����
            SetTDtext(tds[5], rowData["qrzxl"]);  //   ���ȫ����ѧ��
            SetTDtext(tds[6], rowData["qrzxlxx"]);  //   ԺУϵרҵ�����ȫ����ѧ����
            SetTDtext(tds[7], rowData["zzxl"]);  //   �����ְѧ��
            SetTDtext(tds[8], rowData["zzxlxx"]);  //   ԺУϵרҵ�������ְѧ����
            SetTDtext(tds[9], rowData["a0107"]);  //   ��������
            SetTDtext(tds[10], rowData["a0144"]);  //   �뵳ʱ��
            SetTDtext(tds[11], rowData["a0134"]);  //   �μӹ���ʱ��
            SetTDtext(tds[12], rowData["a0288"]);  //   ����ְ����ʱ��
            SetTDtext(tds[13], rowData["a0192c"]);  //   �θ�ְ��ʱ��
            // SetTDtext(tds[15], rowData["a0195"]);  //   ͳ�ƹ�ϵ����code
            // SetTDtext(tds[16], rowData["a0195A"]);  //   ͳ�ƹ�ϵ���ڵ�λ
            SetTDtext(tds[14], rowData["comments"]);  //   ���ѧλ��רҵ����
            rowd = rowData;

            rowIndex = 0;
        }
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

//�����󴥷��ķ���
var cuted = function () {
    complate();
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
        				$h.openPageModeWin('uploadKccl','pages.xbrm2.UploadKccl','��������ϴ�',300,160,
                				{a0000:a0000,ynId:ynId,$tr:$(o)},g_contextpath);
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
	        				{a0000:a0000,ynId:ynId,$tr:$(obj)},g_contextpath);
	        	}
			}
		}
	}
})();

$(document).ready(function () {


   
  //���ѡ����
    $("#coordTable tr.data td:nth-child(1)").live('dblclick', function (e) {
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	}
    	/* $h.showModalDialog('personInfoOP',g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000,'��Ա��Ϣ�޸�',1009,630,null,{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true); */
		window.open(g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000, '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no');
        return false;
    });
    
 });
    </script>

<table style="width:100%">
<tr>
<td style="width:300px">
	<div style="width:300px">
	<odin:toolBar property="topbar">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="ɾ��" id="deleteGD" handler="deleteGD" icon="images/icon/table.gif" />
		<!--<odin:buttonForToolBar text="��ӡ�����" id="Print.printWord" handler="Print.printWordNoRM" icon="images/icon/table.gif" />-->
		<odin:buttonForToolBar text="����" icon="images/icon/table.gif" handler="exportTPB" id="exportTPB" />
		<odin:buttonForToolBar text="��ȡ" icon="images/icon/table.gif" isLast="true" handler="readTPB" id="readTPB" />
	</odin:toolBar>
	<odin:editgrid2 property="memberGrid" height="640" pageSize="9999" topBarId="topbar" url="/">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="yn_id" />
			<odin:gridDataCol name="yn_type"/>
			<odin:gridDataCol name="yndate"/>
			<odin:gridDataCol name="yn_gd_desc"/>
			<odin:gridDataCol name="yn_gd_id" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
			<odin:gridEditColumn2 dataIndex="yn_type" width="110" header="���价��" editor="select" selectData="['TPHJ1','�ɲ���Ϣһ����']" edited="false" align="center"/> <!-- ,['TPHJ2','��ί���ר������Ա����'],['TPHJ3','�������'],['TPHJ4','��ί���ר�����'],['TPHJ5','��ί��ί��'] -->
			<odin:gridEditColumn2 dataIndex="yn_gd_desc" width="190" header="��ע" editor="text" edited="false" align="left"/>
			
			<odin:gridEditColumn2 dataIndex="yndate" width="110" header="�浵ʱ��" editor="text" edited="false" align="center"  isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid2>
	</div> 
</td>
<td>
	<div id="selectable" style="margin-top:30px;text-align:center">
		<p class="BiaoTouP" style="text-align:center"><font>��&nbsp;��&nbsp;��&nbsp;Ϣ&nbsp;һ&nbsp;��&nbsp;�� </font></p>
		<p class="TiaoPeiShiJian" id="TiaoPeiShiJianPrint" style="text-align:right"><span class="TiaoPeiShiJianYSPAN  TNR">2019</span>��<span class="TiaoPeiShiJianMSPAN TNR">12</span>��<span class="TiaoPeiShiJianDSPAN TNR">2</span>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
		<div id="coordTable_div" style="margin-left:20px;margin-bottom:20px; overflow:scroll;width:820px; height:560px">
			<table id="coordTable" cellspacing="0" style="width:2080px" >
				<tr>
					<%--<th rowspan="2" style="height:50px !important; width:5%">�鿴</th>--%>
					<th rowspan="2" style="height:50px !important; width:3%">��<BR>��</th>
					<th rowspan="2" style="height:50px !important; width:5%">����</th>
					<%--<th rowspan="2" style="height:50px !important; width:7%">��Ƭ</th>  --%>
					<th rowspan="2" style="height:50px !important; width:9%">����ְ��</th>
					<%--<th rowspan="2" style="height:50px !important; width:10%">������ְ��</th>--%>
					<th rowspan="2" style="height:50px !important; width:2%">��<BR>��</th>
					<th rowspan="2" style="height:50px !important; width:2%">��<BR>��</th>
					<th rowspan="2" style="height:50px !important; width:4%">����</th>

					<th colspan="2" style=" width:20%">ȫ���ƽ���</th>
					<th colspan="2" style=" width:20%">��ְ����</th>

					<th rowspan="2" style="height:50px !important; width:5%">����<BR>����</th>
					<th rowspan="2" style="height:50px !important; width:5%">�뵳<BR>ʱ��</th>
					<th rowspan="2" style="height:50px !important; width:5%">���빤��ʱ��</th>
					<th rowspan="2" style=" width:5%">����ְ<BR>ʱ&nbsp;��</th>
					<th rowspan="2" style=" width:5%">����ְ<BR>��ʱ��</th>
					<th rowspan="2" style=" width:7%">��ע</th>
				</tr>
				<tr>
					<th style="    font-family: ����; font-size: 13pt; letter-spacing: 2px">ѧ��</th>
					<th style="   font-family: ����; font-size: 13pt; letter-spacing: 2px">��ҵԺУ��רҵ</th>

					<th style="    font-family: ����; font-size: 13pt; letter-spacing: 2px">ѧ��</th>
					<th style="   font-family: ����; font-size: 13pt; letter-spacing: 2px">��ҵԺУ��רҵ</th>

				</tr>
			</table>
		</div>
	
	
		<%--<div class="NiRenMianBianJi" rowIndex="" colIndex="" id="NiRenMianBianJi" style="position: absolute;display:none; top: 0px;left: 0px; border: 1px solid silver; background-color:rgb(224,234,245); padding: 6px;">--%>
			<%--<div class="Ren" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
			<%--<div class="Mian" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
			<%--<div class="QiTa" style="height: 33%;width: 100%;margin: 0px;padding: 0px;">--%>
				<%--<textarea style="height: 100%;width: 100%"></textarea>--%>
			<%--</div>--%>
		<%--</div>--%>
		
		
	</div>
</td>
</tr>
</table>

 

<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<script type="text/javascript">

// function reShowMsg(gridobj,index,e){
// 	var rc = gridobj.getStore().getAt(index);
// 	$('#ynId').val(rc.data.yn_id);
// 	$("#yntype").val(rc.data.yn_type);
// 	$("#yn_gd_id").val(rc.data.yn_gd_id);
// 	TIME_INIT.clearData();
// 	radow.doEvent('initX');
// }
// function readTPB(){
// 	radow.doEvent('readTPB');
// }
// function deleteGD(){
// 	$h.confirm("ϵͳ��ʾ��",'ȷ��ɾ����',100,function(id) {
// 		if("ok"==id){
// 			radow.doEvent('deleteGD');
// 		}else{
// 			return false;
// 		}
// 	});
//
// }
var doAddPerson = (function(){//������Ա
    var obj = null;
    return {
        sel:function(o){
            obj = o;
            var ynId = document.getElementById('ynId').value;
            var yntype = $("#yntype").val();
            if(ynId==''){
                //$h.alert('ϵͳ��ʾ','��������Ϣ��');
                //return;
            }
            $h.openWin('seltpry','pages.xbrm2.SelectTPRY','������/���֤��ѯ ',1040,520,null,g_contextpath,window,
                {maximizable:false,resizable:false,RMRY:'������Ա',
                    ynId:ynId,yntype:yntype});
        },
        queryByNameAndIDS:function(list){//��������ѯ
            //if(obj){
            radow.doEvent('queryByNameAndIDS',list);
            //}
        },
        addPerson:function(plist){//��������ѯ ������Ա
            var rowIndex = 0;
            if(obj){
                rowIndex = $(obj).index();
            } else {
                rowIndex = 0;
            }
            try{
                if(rowIndex>0){
                    $.each(plist,function (i, item){
                        var $tr = createRow(item["type"],rowIndex+i,item["a0000"],item);
                        $tr.insertBefore($(obj));
                    });
                }else if(rowIndex==0){//׷��
                    $.each(plist,function (i, item){
                        var $tr = createRow(item["type"],rowIndex,item["a0000"],item);
                        $("#coordTable").append($tr);
                    });
                }

                complate();
                $("#coordTable_div").css("height",$(document.body).height()-130+"px");
                // jQuery.coordTable.appendDiv();
                obj = null;
            }catch(e){
                alert(e);
            }

        }

    }
})();





function exportTPB(expType){
	var yntype = $("#yntype").val();
	var ynId = $("#ynId").val();
	var yn_gd_id = $("#yn_gd_id").val();
	
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPBView&eventNames=ExpTPB';
	//alert(path);
	ShowCellCover('start','ϵͳ��ʾ','��������ɲ����佨�鷽�� ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {ynId:ynId,yntype:yntype,expType:expType,yn_gd_id:yn_gd_id},
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



function eventajax(eventType,params,cbfun){
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames='+eventType;
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








 

//��ӡ
var Print = (function(){
	var wdapp = $h.getTopParent().WORD_PRINT;
	
	return {
		printWord : function(){
			if(!wdapp){
				try{ 
					wdapp = new ActiveXObject("Word.Application");
			  	}catch(e){ 
			  		$helper.alertActiveX();
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
			  		$helper.alertActiveX();
				    return; 
			  	}
			}
			Ext.MessageBox.wait('���ڴ�ӡ�У����Ժ󡣡���');
			radow.doEvent("expWord","NORM");
		},
		startPrint : function(downloadUUID,sid){
			var url=window.location.protocol+"//"+window.location.host+
			g_contextpath+'/PublishFileServlet;jsessionid='+sid+'?method=downloadFile&uuid='+downloadUUID;
			//Ext.MessageBox.hide();
			wdapp.Documents.Open(url);//��wordģ��url
			wdapp.Application.Printout();
		}
	}
  	
  	
})();





function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}




</script>
<div style="display: none;">
<div class="TiaoPeiLeiXingDIV">
<select id="yn_type" name="yn_type">
  <option value="TPHJ1">����</option>
  <option value="TPHJ2">��ί���ר������Ա����</option>
  <option value="TPHJ3">�������</option>
  <option value="TPHJ4">��ί���ר�����</option>
  <option value="TPHJ5">��ί��ί��</option>
</select>
</div>
<div class="TiaoPeiShiJianY">
	<select id="tp_y" name="tp_y">
	</select>
</div>
<div class="TiaoPeiShiJianM">
	<select id="tp_m" name="tp_m">
	</select>
</div>
<div class="TiaoPeiShiJianD">
	<select id="tp_d" name="tp_d">
	</select>
</div>
</div>



<odin:hidden property="yntype" />
<odin:hidden property="tpy" />
<odin:hidden property="tpm" />
<odin:hidden property="tpd" />
<odin:hidden property="docpath" />
<odin:hidden property="ID_ROWINFO" title="�������"/>
<odin:hidden property="ROWID" title="�������id"/>
