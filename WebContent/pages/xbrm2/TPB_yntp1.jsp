<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit.jsp" %>

<meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 

<table id="coordTable" class="coordTable" cellspacing="0" cellspadding="0" >
	<tbody>	
		<tr>
			<TH style="WIDTH: 1px; HEIGHT: 70px !important">�鿴</TH>
			<TH style="WIDTH: 31px; HEIGHT: 70px !important">���</TH>
			<TH style="TEXT-JUSTIFY: distribute-all-lines; TEXT-ALIGN: justify; WIDTH: 64px !important; TEXT-ALIGN-LAST: justify">����</TH>
			<TH style="WIDTH: 132px; HEIGHT: 70px !important">��Ƭ</TH>
			<TH style="WIDTH: 155px; HEIGHT: 70px !important">����ְ��</TH>
			<TH style="TEXT-ALIGN: center !important; WIDTH: 164px; HEIGHT: 70px !important">�����������</TH>
			<TH style="WIDTH: 32px; HEIGHT: 70px !important">��<BR>��</TH>
			<TH style="WIDTH: 32px; HEIGHT: 70px !important">��<BR>��</TH>
			<TH style="WIDTH: 45px; HEIGHT: 70px !important">����</TH>
			<TH style="WIDTH: 78px; HEIGHT: 70px !important">����<BR>����</TH>
			<TH style="WIDTH: 36px; HEIGHT: 70px !important">��<BR>��</TH>
			<TH style="WIDTH: 79px; HEIGHT: 70px !important">�뵳<BR>ʱ��</TH>
			<TH style="WIDTH: 58px; HEIGHT: 70px !important">ѧ&nbsp;��</TH>
			<TH style="WIDTH: 106px; HEIGHT: 70px !important">��ҵԺУ<BR>ϵ��רҵ</TH>
			<TH style="WIDTH: 79px; HEIGHT: 70px !important">����ְ<BR>ʱ&nbsp;��</TH>
			<TH style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; WIDTH: 79px; HEIGHT: 70px !important; BORDER-TOP: 0px; BORDER-RIGHT: 0px">��ְͬ<BR>��ʱ��</TH>
			<!--  <TH style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; WIDTH: 0px; HEIGHT: 70px !important; BORDER-TOP: 0px; BORDER-RIGHT: 0px">&nbsp;</TH>      					̸�������Ƽ� -->
			<!--  <TH style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; WIDTH: 0px; HEIGHT: 70px !important; BORDER-TOP: 0px; BORDER-RIGHT: 0px">&nbsp;</TH>   						�����Ƽ� -->
			<!--  <TH style="BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; WIDTH: 0px; HEIGHT: 70px !important; BORDER-TOP: 0px; BORDER-RIGHT: 0px" ;border:="" 0px;="">&nbsp;</TH>		�ص㿼�� -->
			<TH style="WIDTH: 64px; HEIGHT: 70px !important">��ע</TH>
		</tr>		 
	</tbody>	
</table>

<script type="text/javascript">

	//ҳ���ʼ��
	$(document).ready(function () {
		try{
			TIME_INIT.initShiJian();
			TIME_INIT.setTPHJLB();
			TIME_INIT.setTPHJSJ_Y();
			TIME_INIT.setTPHJSJ_M();
			TIME_INIT.setTPHJSJ_D();   
			
			$("#BiaoTouTitle").html("�����⶯��ɲ������������һ����");
			
			<% if ("1".equals(IsGDCL)){ %>
			$("#coordTable_div").css("height",$(document.body).height()-112+"px"); 
			<%}else{%>
			$("#coordTable_div").css("height",$(document.body).height()-130+"px"); 
			<%}%>
		}catch(e){
			alert(e);
		} 
	});

//GLOBLE['COL_CONFIG_3']={"2":"tp0101","3":"tp0102","4":"tp0103","5":"tp0104","6":"tp0105","7":"tp0106","8":["tp0107","tp0108","tp0109","tp0110","tp0111","tp0112","tp0113","tp0114","tp0115"]};
	GLOBLE['COL_CONFIG_3']={
			"0":"uuid",
			"1":"xh",		
			"2":"tp0101",
			"3":"tp0117",  //��Ƭ
			"4":"tp0106",
			"5":"tp0107",
			"6":"tp0118",
			"7":"tp0119",
			"8":"tp0120",
			"9":"tp0102",
			"10":"tp0121",
			"11":"tp0122",      //�뵳ʱ��
			"12":"tp0124",    	//ѧ��
			"13":"tp0123",    	//��ҵԺУ��רҵ  
			"14":"tp0103",   	//����ְʱ��
			"15":"tp0104",  	//ְͬ��ʱ��  
			"16":"tp0128"       //��ע
			// 2019-09-29 
			//"16":"tp0125",      //̸�������Ƽ�
			//"17":"tp0126",      //�����Ƽ�
			//"18":"tp0127",      //�ص㿼��
			//"19":"tp0128"       //��ע 
			
			};

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
			tr = $('<tr uuid="'+guid+'" class="data YiJiBiaoTiTR" style="height:40px !important;"><td  class="TNR default_color" style="height:40px !important;"></td><td colspan="16" class=" YiJiBiaoTiTD" style="height:40px !important;">&nbsp;</td></tr>'); //colspan="16" ԭ��19
			if(rowData){
	        	rowd=rowData;
	        	var tds = $("td:nth-child(n+2)", tr);
	        	SetTDtext(tds[0],rowData["tp0101"]);
			}else{
	        	rowd={"tp0100":guid,"type":"1","tp0101":""};
			}
			
		}else if(type=="2"){
			tr = $('<tr uuid="'+guid+'" class="data ErJiBiaoTiTR"><td class="TNR default_color"></td><td colspan="16"  class=" ErJiBiaoTiTD">&nbsp;</td></tr>');   //colspan="16" ԭ��19
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
			tr = $('<tr uuid="'+guid+'" class="data"><td class="rownum default_color"></td>' +
				'<td class="TNR '+kcclClass+' "></td>' +
					'<td style="width:68px !important; text-align: center;font-size: 11pt">&nbsp;</td>' +
				'<td class="TNR" style="font-family: ����">&nbsp;</td>' +
				'<td class="TNR" style="font-family: ����;line-height: 1"  > &nbsp;</td>' +
				'<td class="TNR" style="font-family: ����" >&nbsp;</td>' +
				'<td style="font-family: ����" >&nbsp;</td>' +
				'<td class="align-center">&nbsp;</td>' +
				'<td class="align-center" >&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			' <td style="text-align:center">&nbsp;</td> '+
			//' <td style="text-align:center">&nbsp;</td> '+
			//' <td style="text-align:center">&nbsp;</td> '+
			//' <td style="text-align:center">&nbsp;</td> '+
			' </tr>');
			if(rowData){
				var tds = $("td:nth-child(n+3)", tr);
				var yntype = $("#yntype").val();
				var tp0103="";var tp0104="";
				 
				//if("TPHJ1"==yntype||"TPHJ2"==yntype){
					tp0103 = rowData["tp0103"];
					tp0104 = rowData["tp0104"];
				//}
	        	SetTDtext(tds[0],rowData["tp0101"]);  //����
	        	SetTDPhoto(tds[1],rowData["a0000"]);  //��Ƭ       tp0117
	        	SetTDtext(tds[2],rowData["tp0106"]);  //����ְ��
	        	SetTDtext(tds[3],rowData["tp0107"]);  //������ְ��
	        	SetTDtext(tds[4],rowData["tp0118"]);  //�Ա�
	        	SetTDtext(tds[5],rowData["tp0119"]);  //����
	        	SetTDtext(tds[6],rowData["tp0120"]);  //����
	        	SetTDtext(tds[7],rowData["tp0102"]);  //��������
	        	if (rowData["tp0121"] == "0") rowData["tp0121"] = "";
	        	SetTDtext(tds[8],rowData["tp0121"]);  //����
	        	SetTDtext(tds[9],rowData["tp0122"]);  //�뵳ʱ��
	        	SetTDtext(tds[10],rowData["tp0124"]); //ѧ��
	        	SetTDtext(tds[11],rowData["tp0123"]);  //��ҵԺУ��רҵ
	        	SetTDtext(tds[12],tp0103);			  //����ְʱ��
	        	SetTDtext(tds[13],tp0104);			  //ְͬ��ʱ�� 
	        	
	        	SetTDtext(tds[14],rowData["tp0128"]); //��ע
	        	/*** 2019-09-29
	        	SetTDtext(tds[14],rowData["tp0125"]); //̸�������Ƽ�
	        	SetTDtext(tds[15],rowData["tp0126"]); //�����Ƽ�
	        	SetTDtext(tds[16],rowData["tp0127"]); //�ص㿼��
	        	SetTDtext(tds[17],rowData["tp0128"]); //��ע
	        	*****/
	        	rowd=rowData;
	        	
	        	rowIndex =0;
			}else{
				var a0000 = "ZZZZ"+guid;
				var tds = $("td:nth-child(n+3)", tr); 
				SetTDPhoto(tds[1],a0000);  //��Ƭ       tp0117 
	        	rowd={"tp0100":guid,"a0000":a0000,"type":"3","tp0101":"",
	        								   "tp0117":"",
	        								   "tp0106":"",
	        								   "tp0107":"",
	        								   "tp0118":"",
	        								   "tp0119":"",
	        								   "tp0120":"",
	        								   "tp0102":"",
	        								   "tp0121":"",
	        								   "tp0122":"",
	        								   "tp0123":"",
	        								   "tp0103":"",
	        								   "tp0104":"",
	        								   "tp0124":"",
	        								   //"tp0125":"",
	        								   //"tp0126":"",
	        								   //"tp0127":"",
	        								   "tp0128":""}; 
	        	rowData = rowd;
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
</script>	
