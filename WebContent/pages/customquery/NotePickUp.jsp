<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<style>
/* ����ɫ */
body {
	background-color: #DFE8F6;
}
#suppertext{
	width: 99%;
	border: none;
	overflow:hidden;
	resize:none;
	white-space:pre-wrap;
	white-space:-moz-pre-wrap;
 	white-space:-o-pre-wrap;
 	word-wrap:break-word;
}
#tableTest{
	margin-top: 20px;
}
#tableTest tr td{
	font-size: 16px;
	font-family: "����";
}
		
/* .hover{background: #38AAE1;} */
	
</style>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:buttonForToolBar text="ȫ��δ����Ա������ʼ��" id="initJL" handler="initJL" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="����" id="add" handler="add" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="ɾ��" id="del" handler="del" icon="images/icon/table.gif"/>
	<odin:fill/>
	<odin:buttonForToolBar text="��ǰ��Ա������ʼ��" id="initJLForOne" handler="initJLForOne" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="�鿴��Ա����" id="lookjl" handler="lookjl" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="��һ��" id="up" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="��һ��" id="next" icon="images/icon/table.gif"/>
	<odin:separator/>
	<odin:buttonForToolBar text="��ǰ��Ա�������ȷ��" id="confirm" icon="images/icon/table.gif" handler="save" isLast="true"/>
</odin:toolBar>

<odin:hidden property="ids"/>
<odin:hidden property="gridA0000"/>
<odin:hidden property="cueRowIndex"/>
<odin:hidden property="a1700"/>
<odin:hidden property="trSeq"/>
<odin:hidden property="jsonOne"/>

<div id="btnToolBarDiv" ></div>
<div style="float:left;width:280px;height:290px" >
		<odin:editgrid property="peopleInfoGrid" title="��Ա��Ϣ�б�" bbarId="pageToolBar" pageSize="500"
					autoFill="false" width="100" height="290" >
					<odin:gridJsonDataModel>
						<%-- <odin:gridDataCol name="personcheck" /> --%>
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="sign" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<%-- <odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid"  
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" /> --%>
						<odin:gridEditColumn2 dataIndex="a0000" width="50" header="��Աid"
							hideable="false" editor="text" align="center" hidden="true" />
						<odin:gridEditColumn2 dataIndex="a0101" width="60" header="����"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0192a" width="110" header="������λ��ְ��"
							align="center" editor="text" edited="false"/>
						<odin:gridEditColumn2 dataIndex="sign" width="80" header="�Ƿ���ȷ��" edited="false"
							hideable="false" editor="text" align="center" isLast="true" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
					        data:[]
					    }
					</odin:gridJsonData>
				</odin:editgrid>
</div>
<div id="div1" style="width:1050px;margin-left: 40px;overflow: auto;">
		<table id="tableTest" class="table table-bordered">
			<tr id="tableTr1">
				<td style="width:80px;text-align: center;">��ʼʱ��</td>
				<td style="width:80px;text-align: center;">����ʱ��</td>
				<td style="width:300px;text-align: center;">����</td>
				<td style="width:200px;text-align: center;">��λ(ѧУ)</td>
				<td style="width:200px;text-align: center;">ְ��(רҵ)</td>
				<td style="width:150px;text-align: center;">����</td>
			</tr>
			<!-- <tr>
				<td><input type="text" width="80" value="1997.05"></td>
				<td><input type="text" width="80" value="1996.08"></td>
				<td><textarea id="suppertext">�����й����ֳ����־ָ��ֳ�����</textarea></td>
				<td><input type="text" value=""></td>
				<td><input type="text" value=""> </td>
			</tr> -->
		</table>
</div>

<script type="text/javascript">
Ext.onReady(function(){
	var width=document.body.clientWidth;
	var height=document.body.clientHeight;
	Ext.getCmp("peopleInfoGrid").setHeight(height-25);
	document.getElementById('div1').style.height=(height-30)+"px";
});

function saveJson(){
	var data = $(".newTr");
	
    var json = [];
	for (var i=0;i<data.length;i++) {
          var tr = data[i];
          var tds = $(tr).find("td");
          
          var a1700 = $(tr).attr("id");//����
          var start = $(tds[0]).find("input").val();//��ʼʱ��
          var end = $(tds[1]).find("input").val();//����ʱ��
          //var content = $(tds[2]).find("textarea").text();//����
          var unit = $(tds[3]).find("input").val();//��λ
          var president = $(tds[4]).find("input").val();//ְ��
          var region = $(tds[5]).find("input").val();//����
          
          var j = {};
          j.a1700 = a1700;
          j.start = start;
          j.end = end;
          //j.content = content;
          j.unit = unit;
          j.president = president;
          j.region = region;
          json.push(j);
	 }
	 
	document.getElementById('jsonOne').value = JSON.stringify(json);
	
}

function add(){
	var a1700 = document.getElementById('a1700').value;
	if(!a1700){
		odin.alert("����ѡ��һ��������");
		return;
	}
	var a0000 = document.getElementById('gridA0000').value;
	if(!a0000){
		odin.alert("����ѡ����Ա��");
		return;
	}
	saveJson();
	radow.doEvent("addA1700",a0000);
	
}

function apptr(){
	var htmlList = '<tr onclick=\"saveA1700(this)\" class=\"newTr\" id=\"'+v.a1700+'\">';
	htmlList += '<td style=\"width:80px\"><input type=\"text\" style=\"width:80px;height=40px;\" value=\"'+v.start+'\"></td>';
	htmlList += '<td style=\"width:80px\"><input type=\"text\" style=\"width:80px\;height=40px;" value=\"'+v.end+'\"></td>';
	htmlList += '<td style=\"width:300px\"><textarea id=\"suppertext\" style=\"width:300px\;height=40px;overflow:auto">'+v.content+'</textarea></td>';
	htmlList += '<td style=\"width:200px\"><input type=\"text\" style=\"width:200px;height=40px;\" value=\"'+v.unit+'\"></td>';
	htmlList += '<td style=\"width:200px\"><input type=\"text\" style=\"width:200px;height=40px;\" value=\"'+v.president+'\"></td>';
	htmlList += '<td style=\"width:150px\"><input type=\"text\" style=\"width:150px;height=40px;\" value=\"'+v.region+'\"></td>';
	htmlList += '</tr>';
	//��������������
    $("#tableTest tr:last").after(htmlList);

	jueryArea();
}

function del(){
	var a1700 = document.getElementById('a1700').value;
	if(!a1700){
		odin.alert("����ѡ��һ��������");
		return;
	}
	Ext.MessageBox.confirm('��ʾ','ȷ��ɾ������������',function(btn){
		if(btn=="yes"){
			saveJson();
		    radow.doEvent("delA1700",a1700);
		}
	});
}

function lookjl(){
	var a0000 = document.getElementById('gridA0000').value;
	if(!a0000){
		odin.alert("����ѡ����Ա��");
		return;
	}
	window.open('<%=request.getContextPath()%>/rmb/resumeGZ.jsp?a0000='+a0000,"��������","height=551,width=811,status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
	<%-- $h.showWindowWithSrc('resumeWin','<%=ctxPath%>/rmb/resume.jsp?','����',811,661,null); --%>
}

function initJL(){
	ShowCellCover("start","��ܰ��ʾ��","���ڳ�ʼ��...");
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=initJL",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				eval(cfg.elementsScript);
				Ext.Msg.alert("��ܰ��ʾ","��ʼ���ɹ���");
			}else{
				Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
					return;
				}
				Ext.Msg.alert("��ܰ��ʾ","��ʼ��ʧ�ܣ�");
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			odin.alert("�����쳣��");
		}  
	});
}

function initJLForOne(){
	var a0000 = document.getElementById('gridA0000').value;
	if(!a0000){
		odin.alert("����ѡ����Ա��");
		return;
	}
	ShowCellCover("start","��ܰ��ʾ��","���ڳ�ʼ��...");
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=initJLForOne&a0000="+a0000,
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				eval(cfg.elementsScript);
				Ext.Msg.alert("��ܰ��ʾ","��ʼ���ɹ���");
				radow.doEvent("peopleInfoGrid.dogridquery");
			}else{
				Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
					return;
				}
				Ext.Msg.alert("��ܰ��ʾ","��ʼ��ʧ�ܣ�");
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			odin.alert("�����쳣��");
		}  
	});
}

var substringText, otherText;
function jueryArea(){
	$("#tableTest textarea").click(function() {
		//jiequ(this);
		getCursortPosition(this);
		fuzhi(this);
		
	});
}

function saveA1700(o){
	var a1700 = $(o).attr("id");
	
	document.getElementById('a1700').value = a1700;
	
	var trSeq = $(o).parent().find("tr").index($(o)[0]);

	document.getElementById('trSeq').value = trSeq;
    /* $("#tableTest td").click(function(){
      var trSeq = $(this).parent().parent().find("tr").index($(this).parent()[0]);
      alert("��" + (trSeq + 1) + "��");
    }); */
    
}
/* $(function() {
	$("#tableTest textarea").click(function() {
		//jiequ(this);
		getCursortPosition(this);
		fuzhi(this);
	});
}); */
function fuzhi(o) {
	var td = $($(o).parent().parent()).find("td"); // �ҵ�tdԪ��							
	var lo_id = td[3]; // ָ����Ҫ��ȡԪ�ص��±꼴��
	var zhiwu = td[4];
	//$(lo_id).val(substringText);
	$(lo_id).find("input").val(substringText);
	//$(zhiwu).val(otherText);
	$(zhiwu).find("input").val(otherText);
	
	//ȡ��substringText��ѭ��tr�������λһ�£�Ĭ�ϰѵ�����½�ȥ������Ϊ�վͲ��ģ�
	/* var region = $(td[5]).find("input").val();
	if(region&&substringText){
		saveRegion(substringText,region);
	} */
}

function saveRegion(obj){
	var region = $(obj).val();
	var td = $($(obj).parent().parent()).find("td");
	var substringText = $(td[3]).find("input").val();//��λ
	//alert(region);alert(substringText);
	
	var data = $(".newTr");
	
	for (var i=0;i<data.length;i++) {
          var tr = data[i];
          var tds = $(tr).find("td");
          
          var unit = $(tds[3]).find("input").val();//��λ
          if(unit==substringText){
        	  $(tds[5]).find("input").val(region);//����
          }
	 }
	 
}

function jiequ(obj) {
	/* 	el = obj.target; */
	var substringLenght = obj.selectionStart;
	var text = $(obj).html();
	/* ��ȡ��λ */
	substringText = text.substring(0, substringLenght);
	/* ��ȡְ�� */
	otherText = text.substring(substringLenght);
}
function getCursortPosition(ctrl){
	var CaretPos = 0;
	//IE
	if(document.selection){
		ctrl.focus();
	�� �� �� var range = document.selection.createRange();
	�� �� �� var stored_range = range.duplicate();
	�� �� �� stored_range.moveToElementText(ctrl);
	�� �� �� stored_range.setEndPoint('EndToEnd', range );
	�� �� �� ctrl.selectionStart = stored_range.text.length - range.text.length;
	�� �� �� ctrl.selectionEnd = ctrl.selectionStart + range.text.length;
	�� �� �� CaretPos = ctrl.selectionStart;
	}
	//Firefox
	if(ctrl.selectionStart||ctrl.selectionStart=='0'){
		CaretPos = ctrl.selectionStart;
	}
	var text = $(ctrl).text();
	/* ��ȡ��λ */
	substringText = text.substring(0, CaretPos);
	/* ��ȡְ�� */
	otherText = text.substring(CaretPos);
}

function appendTable(index,obj){
	$("tr").remove(".newTr") ;
	
	var val = eval('(' + obj + ')');
	//console.log(val.tr0);
	if(val){
		for(var i=0;i<index;i++){
			var tri = "val.tr"+i;
			var v = eval('(' + tri + ')');
			//console.log(v);
			var htmlList = '<tr onclick=\"saveA1700(this)\" class=\"newTr\" id=\"'+v.a1700+'\">';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a1700+'\"></td>';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a0000+'\"></td>';
			htmlList += '<td style=\"width:80px\"><input type=\"text\" style=\"width:80px;height:25px;\" value=\"'+v.start+'\"></td>';
			htmlList += '<td style=\"width:80px\"><input type=\"text\" style=\"width:80px\;height:25px;" value=\"'+v.end+'\"></td>';
			htmlList += '<td style=\"width:300px\"><textarea id=\"suppertext\" style=\"width:300px\;height=40px;overflow:auto;margin-top:15px">'+v.content+'</textarea></td>';
			htmlList += '<td style=\"width:200px\"><input type=\"text\" style=\"width:200px;height:25px;\" value=\"'+v.unit+'\"></td>';
			htmlList += '<td style=\"width:200px\"><input type=\"text\" style=\"width:200px;height:25px;\" value=\"'+v.president+'\"></td>';
			htmlList += '<td style=\"width:150px\"><input type=\"text\" onblur=\"saveRegion(this)\" style=\"width:150px;height:25px;\" value=\"'+v.region+'\"></td>';
			htmlList += '</tr>';
			//��������������
		    $("#tableTest tr:last").after(htmlList);
		}
		
		jueryArea();
	}
}

function save(){
	var data = $(".newTr");
	
    var json = [];
	for (var i=0;i<data.length;i++) {
          var tr = data[i];
          var tds = $(tr).find("td");
          
          var a1700 = $(tr).attr("id");//����
          var start = $(tds[0]).find("input").val();//��ʼʱ��
          var end = $(tds[1]).find("input").val();//����ʱ��
          //var content = $(tds[2]).find("textarea").text();//����
          var unit = $(tds[3]).find("input").val();//��λ
          var president = $(tds[4]).find("input").val();//ְ��
          var region = $(tds[5]).find("input").val();//����
          
          var j = {};
          j.a1700 = a1700;
          j.start = start;
          j.end = end;
          //j.content = content;
          j.unit = unit;
          j.president = president;
          j.region = region;
          json.push(j);
	 }
	 
	var a0000 = document.getElementById('gridA0000').value;
	//alert(a0000);
	ShowCellCover("start","��ܰ��ʾ��","���ڱ���...");
	
	 Ext.Ajax.request({
			method: 'POST',
	        async: true,
	        params : {"jsonStr":JSON.stringify(json),"a0000":a0000},
	        timeout :300000,//���������
			url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=save",
			success: function(resData){
				var cfg = Ext.util.JSON.decode(resData.responseText);
				if(0==cfg.messageCode){
					Ext.Msg.hide();	
					
					if(cfg.elementsScript.indexOf("\n")>0){
						cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
						cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
					}
					
					eval(cfg.elementsScript);
					Ext.Msg.alert("��ܰ��ʾ","����ɹ���");
					radow.doEvent("peopleInfoGrid.dogridquery");
				}else{
					Ext.Msg.hide();	
					
					if(cfg.mainMessage.indexOf("<br/>")>0){
						Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
						return;
					}
					Ext.Msg.alert("��ܰ��ʾ","����ʧ�ܣ�");
				}
			},
			failure : function(res, options){ 
				Ext.Msg.hide();
				odin.alert("�����쳣��");
			}  
		});
}

function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("ϵͳ��ʾ", msgs, function(but) {  
				
			}); 
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}

</script>
