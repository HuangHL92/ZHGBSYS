<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<title></title>
<script type="text/javascript">
</script>
<style>
.rmbword{
    font-family: ����;
    position: absolute;
    left:0; right:0;  bottom:0;
    margin:auto;
    top:10px;
    width: 100%;
    float: right;
}
td{
	font-family: ����;
}
.tbr{
    text-align: left;padding-left:288px;padding-top:8px;margin-bottom:20px;font-size: 18px;
} 
.jianli p{
	font-family:����;
	padding-left: 9em!important;
	text-align: left
       
}

.tdlabel{
   text-align: center; 
       
}


#tableJBXX{ 
	font-size: 18px;  
	border-left: 1px solid black;
	border-top: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 1px solid black;
	width: 628px;
}
#tableJBXX tr td{ 
	border-left: 1px solid black;
	border-top: 1px solid black;
}
.width1{ width: 66px; }.width2{ width: 86px; }.width3{ width: 70px; }
.width4{ width: 86px; }.width5{ width: 86px; }.width6{ width: 88px; }.width7{ width: 136px; }
.height1{height: 45px;}.height2{height: 46px;}.height3{height: 44px;}.height4{height: 46px;}
.height5{height: 45px;}.height6{height: 45px;}.height7{height: 47px;}.height8{height: 47px;}
.height9{height: 47px;}



#tableJianli{
	border-left: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableJianli tr td{ 
	border-left: 1px solid black;
}

#coordTable {
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}
#coordTable2 {
margin-top:-2px;
border-right: 2px solid #74A6CC;
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}

.class1 {
border-left: 2px solid #74A6CC;
border-top: 2px solid #74A6CC;
}
.class2 {
border-left: 2px solid #74A6CC;
border-right: 2px solid #74A6CC;
border-top: 2px solid #74A6CC;
}

#tableJiaTingChengYuan{
	font-size: 18px;  
	border-left: 1px solid black;
	border-top: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableJiaTingChengYuan tr td{ 
	border-left: 1px solid black;
	border-top: 1px solid black;
}
.Jwidth1{width: 48px;}.Jwidth2{width: 56px;}.Jwidth3{width: 84px;}.Jwidth4{width: 38px;}
.Jwidth5{width: 80px;}.Jwidth6{width: 314px;}
.Jheight1{height: 93px;}.Jheight2{height: 67px;}.Jheight3{height: 90px;}.Jheight4{height: 44px;}
.Jheight5{height: 45px;}.Jheight6{height: 140px;}

a:link {color: black; text-decoration:none;} 
a:active:{color: red; } 
a:hover {color: red; text-decoration:underline;} //����ƽ�����ɫ���»���

#tableSP{
	font-size: 18px;  
	border-left: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableSP tr td{ 
	border-left: 1px solid black;
}
.SPwidth1{width: 46px;}.SPwidth2{width: 268px;}.SPwidth3{width: 52px;}.SPwidth4{width: 255px;}.SPheight{height: 177px;}
</style>
</head>
<odin:hidden property="a0000"/>
<odin:hidden property="location" value="1"/>
<body >
	  <div id="div_top" style="width: 200px;height:800;position:fixed;z-index:3;">
	  	<table style="margin-top: 30px;">
	  		<tr>
	  			<td style="width: 130px;height: 35px;" bgcolor="#00BFFF" align="center"><a href="#tab1"><span id="sp1">��Ա������Ϣ</span></a></td>
	  		</tr>
	  		<tr >
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center" ><a href="#tab2"><span id="sp2">������ǩ��Ϣ</span></a></td>
	  		</tr>
	  		<tr >
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center"><a href="#tab3"><span id="sp3">����ίԱ��Ϣ</span></a></td>
	  		</tr>
	  		<tr>
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center"><a href="#tab4"><span id="sp4">�ɲ�Ԥ����Ϣ</span></a></td>
	  		</tr>
	  	</table>
	  </div>
      <div  class="rmbword" >
        <div style='text-align:center;'  align="center">
          	<h1 style="font-size: 24px;"  ><a name="tab1" style="text-decoration: none">��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;��</a></h1>
	         <br><br>
	          <table  cellpadding="0" cellspacing="0" id="tableJBXX" style="margin:auto;">
	            <tr align="center">
	              <td class="tdlabel width1 height1">��&ensp;&ensp;��</td>
	              <td class="width2" id="iA0101i">&ensp;</td>
	              <td class="tdlabel width3">��&ensp;&ensp;��</td>
	              <td class="width4" id="iA0104i">&ensp;</td>
	              <td class="tdlabel width5">��������<br/>(��)</td>
	              <td class="width6" id="iA0107_1i">&ensp;</td>
	              <td  class="width7" rowspan=4>
	                <div style='width:100%; height:100%'>
	                  <img src='<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=' id="iphoto_pathi" style=" width:136px;height:170px;" />
	                </div>
	
	              </td>
	            </tr>
	            <tr  align="center">
	              <td  class="tdlabel height2">��&ensp;&ensp;��</td>
	              <td  id="iA0117i">&ensp;</td>
	              <td  class="tdlabel">��&ensp;&ensp;��</td>
	              <td  id="iA0111Ai">&ensp;</td>
	              <td  class="tdlabel">��&ensp;��&ensp;��</td>
	              <td  id="iA0114Ai">&ensp;</td>
	
	            </tr>
	            <tr align="center">
	              <td  class="tdlabel height3">��&ensp;&ensp;��<br/>ʱ&ensp;&ensp;��</td>
	              <td  id="iA0140i">&ensp;</td>
	              <td  class="tdlabel">�μӹ�<br/>��ʱ��</td>
	              <td  id="iA0134_1i">&ensp;</td>
	              <td  class="tdlabel">����״��</td>
	              <td  id="iA0128i">&ensp;</td>
	            </tr>
	            <tr align="center">
	              <td  class="tdlabel height4">רҵ��<br/>��ְ��</td>
	              <td  colspan=2 id="iA0196i">&ensp;</td>
	              <td  class="tdlabel">��Ϥרҵ<br/>�к��س�</td>
	              <td  colspan=2 id="iA0187Ai">&ensp;</td>
	            </tr>
	            <tr align="center">
	              <td  class="tdlabel "  rowspan=2>ѧ&ensp;&ensp;��<br/>ѧ&ensp;&ensp;λ</td>
	              <td  class="tdlabel height5">ȫ����<br/>��&ensp;&ensp;��</td>
	              <td  colspan=2 id="iQRZXLi">&ensp;</td>
	              <td  class="tdlabel">��ҵԺУ<br/>ϵ��רҵ</td>
	              <td  colspan=2 id="iQRZXLXXi" align="left">&ensp;</td>
	
	            </tr>
	            <tr align="center">
	
	              <td class="tdlabel height6">��&ensp;&ensp;ְ<br/>��&ensp;&ensp;��</td>
	              <td colspan=2 id="iZZXLi">&ensp;</td>
	              <td class="tdlabel">��ҵԺУ<br/>ϵ��רҵ</td>
	              <td  colspan=2 id="iZZXLXXi" align="left">&ensp;</td>
	            </tr>
	
	            <tr>
	              <td class="tdlabel height7" colspan=2>����ְ��</td>
	              <td colspan=5 id="iA0192Ai">&ensp;</td>
	            </tr>
	          </table>
          
          
	          <table cellpadding="0" cellspacing="0" id="tableJianli" style="margin:auto">
	            <tr>
	              <td style='width:56px;font-size: 18px' class="tdlabel">��<br/><br/><br/><br/><br/>��</td>
	              <td >
	                <div class="jianli" style='width:568px;text-align:left; margin:4px;' id="iA1701i">
	                  
	                </div>
	              </td>
	            </tr>
	          </table>
        </div>
        <div  style='text-align:center;'>
          <table cellpadding="0" cellspacing="0" id="tableJiaTingChengYuan" style="margin:auto">
            <tr>
              <td  align="center">����<br />���</td>
              <td colspan="5">
                <div style='width:572px;border-bottom:0px;text-align:left;' id="iA14Z101i">
                  
                </div>
              </td>
            </tr>
            <tr>
              <td align="center">���<br />����</td>
              <td colspan="5">
                <div style='width:572px;border-bottom:0px;text-align:left; ' id="iA15Z101i">
                  
                </div>

              </td>
            </tr>

          
            <tr align="center">
              <td  class="tdlabel Jwidth1 " rowspan="8">��<br />ͥ<br />��<br />Ҫ<br />��<br />Ա<br />��<br />��<br />Ҫ<br />��<br />��<br />��<br />ϵ</td>
              <td class="tdlabel Jwidth2 Jheight4">��&ensp;ν</td>
              <td class="tdlabel Jwidth3">��&ensp;&ensp;��</td>
              <td class="tdlabel Jwidth4">��<br/>��</td>
              <td class="tdlabel Jwidth5">��&ensp;��<br />��&ensp;ò</td>
              <td class="tdlabel Jwidth6">��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;λ&ensp;&ensp;��&ensp;&ensp;ְ&ensp;&ensp;��</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_1i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_1i">&ensp;</td>
              <td  id="iA3607_1i">&ensp;</td>
              <td  id="iA3627_1i">&ensp;</td>
              <td id="iA3611_1i" align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_2i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_2i">&ensp;</td>
              <td  id="iA3607_2i">&ensp;</td>
              <td  id="iA3627_2i">&ensp;</td>
              <td   id="iA3611_2i"  align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_3i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_3i">&ensp;</td>
              <td  id="iA3607_3i">&ensp;</td>
              <td  id="iA3627_3i">&ensp;</td>
              <td  id="iA3611_3i"  align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_4i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_4i">&ensp;</td>
              <td  id="iA3607_4i">&ensp;</td>
              <td  id="iA3627_4i">&ensp;</td>
              <td  id="iA3611_4i"  align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_5i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_5i">&ensp;</td>
              <td  id="iA3607_5i">&ensp;</td>
              <td  id="iA3627_5i">&ensp;</td>
              <td  id="iA3611_5i" align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td  id="iA3604A_6i" class="Jheight5">&ensp;</td>
              <td  id="iA3601_6i">&ensp;</td>
              <td  id="iA3607_6i">&ensp;</td>
              <td  id="iA3627_6i">&ensp;</td>
              <td  id="iA3611_6i" align="left">&ensp;</td>
            </tr>
            <tr align="center">

              <td id="iA3604A_7i" class="Jheight5">&ensp;</td>
              <td id="iA3601_7i">&ensp;</td>
              <td id="iA3607_7i">&ensp;</td>
              <td id="iA3627_7i">&ensp;</td>
              <td id="iA3611_7i" align="left">&ensp;</td>
            </tr>	  
          </table>
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;" ><a name="tab2" style="text-decoration: none">��&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;ǩ&ensp;&ensp;��&ensp;&ensp;Ϣ</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>������Ҫְ����Ҫ������</td>
				</tr>
				<tr>
					<td id="a0193z"></td>
				</tr>
				<tr height="10">
					<td></td>
				</tr>
				<tr>
					<td>��Ϥ����</td>
				</tr>
				<tr>
					<td id="a0194z"></td>
				</tr>
			</table>
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;"><a name="tab3" style="text-decoration: none" >��&ensp;&ensp;��&ensp;&ensp;ί&ensp;&ensp;Ա&ensp;&ensp;��&ensp;&ensp;Ϣ</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>����ίԱ������</td>
				</tr>
				<tr>
					<td id="dbwy"></td>
				</tr>
			</table>
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;"><a name="tab4" style="text-decoration: none">��&ensp;&ensp;��&ensp;&ensp;Ԥ&ensp;&ensp;��&ensp;&ensp;��&ensp;&ensp;Ϣ</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>���䵽�������</td>
				</tr>
				<tr>
					<td id="dldr"></td>
				</tr>
			</table>
			<br>
			<table id="coordTable" cellspacing="0" style="font-size: 18px;margin-left: 150px;">
				<tr><td colspan="3">���Ѻ�ѯ���������</td></tr>
				<tr  align="center">
					<td width="150" class='class1'>����ʱ��</td>
					<td width="150" class='class1'>�ܴ������</td>
					<td width="400" class='class2'>����ժҪ</td>
				</tr>
			</table>
			<table id="coordTable2" cellspacing="0" style="font-size: 18px;margin-left: 150px;">
			</table>
        </div>
        <div style="height: 400px"></div>
      </div>
</body>
</html>
<script type="text/javascript">
<%
String a0000 = request.getParameter("a0000");

%>
Ext.onReady(function() {
	if(""!='<%=a0000==null?"":a0000%>'){
		$('#a0000').val('<%=a0000%>');
	}else{
		document.getElementById('a0000').value = parentParam.a0000;
	}
	
	//document.getElementById('location').value = parentParam.location;
});

function updateImg(str){
	document.getElementById('iphoto_pathi').src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000='+str;
}

function Add(data){
	$.each(data, function (i,item) {
		var tr;
		tr = $('<tr align="center"><td width="150" class="class1"></td><td width="150" class="class1"></td><td width="400" class="class1"></td></tr>');	
		var tds = $("td", tr);
 		SetTDtext(tds[0],item["time"]); 
		SetTDtext(tds[1],item["nr"]); 
		SetTDtext(tds[2],item["type"]);
		$('#coordTable2').append(tr);
	});
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0")?" ":v.replace(/\n/g,"<br/>"));
}

</script>

