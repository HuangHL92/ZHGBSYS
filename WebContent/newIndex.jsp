<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.util.ArrayList"%> 
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.comm.CommQuery"%> 
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%> 
<%@ page import="com.insigma.odin.framework.util.SysUtil" %>
<%@page import="org.apache.noggit.JSONUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.odin.framework.AppException" %>
<% 
	String data = CodeType2js.getCodeTypeJS(request);
	CommQuery cq = new CommQuery();
	String userId = SysManagerUtils.getUserId();
	String a0165Sql = "";
	if("40288103556cc97701556d629135000f".equals(userId)){
		
	}else{
		a0165Sql =" and exists(select 1 from COMPETENCE_USERDEPT t,b01 where t.userid='"+userId+"' and b01.b0111=t.b0111)";
	}
	String rySql = "select count(DISTINCT A01.a0000) as num from a01 where a01.a0165='03' and a0163 = '1' "+a0165Sql
	+ " union all select count(DISTINCT A01.a0000) as num from a01 where a01.a0165='05' and a0163 = '1' "+a0165Sql
	+ " union all select count(DISTINCT A01.a0000) as num from a01 where substr(a01.A0107,1,6)>=to_char(Addmonth(sysdate,-723),'yyyymm') and a0163 = '1' "+a0165Sql
	+ " union all select count(DISTINCT A01.a0000) as num from a01 where a01.a0221 in ('1A98','1B98','1C98','27','7118','7218','7318','7418','7518','911','C98') and a0163 = '1' "+a0165Sql;
	String ryJson ="";
	try {
		List<HashMap<String,Object>> rylist = cq.getListBySQL(rySql);
		ryJson= JSONUtil.toJSON(rylist);
	} catch (AppException e) {
		e.printStackTrace();
	}


%>
<html>
<odin:head/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="icon" href="<%=request.getContextPath()%>/image/logo_icon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo_icon.ico" type="image/x-icon" />
<title>�й�������ί��֯���ۺ�ҵ��ƽ̨ </title>
<link href="mainPage/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="mainPage/js/jquery.js"></script>

<script type="text/javascript" src="mainPage/js/bootstrap.min.js"></script>
<script type="text/javascript" src="basejs/dateutil.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<script type='text/javascript' src='js/dropdown.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.url.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>

</head>

<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';
function fileView(index){
	//alert(index);
	var path = document.getElementById('filePath'+index).value;
	window.top.location.href="<%=request.getContextPath()%>/SorlQueryServlet?path="+path;
}
$(function(){
	<%=data %>
	var data = CodeTypeTree.tree;
	data = data.children;
	for(var i=0;i<data.length;i++){
		if(data[i].text=='��������'){//1
			$('#li_tab1').css('display','block');
		}
		if(data[i].text=='�ɲ�����'){//2
			$('#li_tab2').css('display','block');
		}
		if(data[i].text=='�ɲ�����'){//3
			$('#li_tab3').css('display','block');
		}
		if(data[i].text=='�ۺϲ�ѯ'){//4
			$('#li_tab4').css('display','block');
		}
		if(data[i].text=='���ݽ���'){//5
			$('#li_tab5').css('display','block');
		}
		if(data[i].text=='ϵͳ����'){//6
			$('#li_tab6').css('display','block');
		}
		if(data[i].text=='��ѧ�����'){//20
			$('#li_tab7').css('display','block');
		}
		if(data[i].text=='����ɲ�'){//21
			$('#li_tab8').css('display','block');
		}
	}
	rychart();
});
</script>
<body>
<div class="top">
<odin:hidden property="nowFilePath"/>
  <div class="top_left">
    �й�������ί��֯���ۺ�ҵ��ƽ̨
  </div>
  <div class="top_right">
  <span style=" float:left;margin-top:10px;"><img src="mainPage/images/images/index_06.png" width="30" height="30" /></span>&nbsp;&nbsp;&nbsp;<%=SysUtil.getCacheCurrentUser().getName()%></div>
</div>
<div class="contain">
  <div class="contain_left">
    <div class="contain_left_1">
      <table width="100%%" border="0">
  <tr>
    <td width="15%" rowspan="2"><img src="mainPage/images/images/index_06.png" width="60" height="60" /></td>
    <td style="font-size:16px;" width="32%" height="30" colspan="2"><%=SysUtil.getCacheCurrentUser().getName()%></td>
  </tr>
  <tr>
    <td style="font-size:12px;"><span style=" float:left;"><img style="margin-top:5px;" src="mainPage/images/images/index_16.png" width="13" height="14" /></span>&nbsp;<a href="#" onclick="logout()">ע��</a></td>
    <td style="font-size:12px;"><span style=" float:left;"><img style="margin-top:5px;" src="mainPage/images/images/index_18.png" width="12" height="14" /></span>&nbsp;<a href="#">����</a></td>
  </tr>
</table>
  </div>
  <ul>
    <li id="li_tab1" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_33.png" width="22" height="20" />&nbsp;&nbsp;<a style="" href="#" onclick="tabOpen(1)">��������</a></div><div style="float:right; margin-top:20px;"><a href="#"><img src="images/images/index_36.png" width="6" height="11" /></a></div>
    </li>
     <li id="li_tab2" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_43.png" width="22" height="20" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(2)">�ɲ�����</a></div>
    </li>
     <li id="li_tab3" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_46.png" width="22" height="20" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(3)">�ɲ�����</a></div>
    </li>
     <li id="li_tab4" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_48.png" width="22" height="24" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(4)">�ۺϲ�ѯ</a></div>
    </li>
     <li id="li_tab5" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_50.png" width="22" height="19" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(5)">���ݽ���</a></div>
    </li>
    <li id="li_tab7" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_43.png" width="22" height="19" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(20)">��ѧ�����</a></div>
    </li>
    <li id="li_tab8" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_43.png" width="22" height="19" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(21)">����ɲ�</a></div>
    </li>
    <li id="li_tab6" style="display: none">
       <div style="float:left;"><img src="mainPage/images/images/index_50.png" width="22" height="19" />&nbsp;&nbsp;<a href="#" onclick="tabOpen(6)">ϵͳ����</a></div>
    </li>
  </ul>
  
  </div>
  <div class="contain_right">
     <!-- <div class="contain_right_title">
     <li>
     <img src="images/images/index_03.png" width="18" height="18" />&nbsp;&nbsp;��ҳ
     </li>
     </div> -->
     <div class="contain_right_all">
       <div class="contain_right_all_1">
         <div class="contain_right_all_1_title">
             <div style="float:left; color:#219dc6; font-size:18px;"><img src="mainPage/images/images/index_10.png" onclick="refreshDBInfo();" width="23" height="21" />&nbsp;&nbsp;��������</div><div style="float:right; margin-top:15px;"><a href="#"><img src="mainPage/images/images/index_21.png" onclick="moreDBInfo();" width="8" height="10" /></a></div>
         </div>
         <div id="db_div" class="contain_right_all_shixiang">
          <!-- <div class="contain_right_all_shixiang_1">
             <table width="100%" border="0">
			  <tr style="font-size:16px;">
			    <td colspan="2">2019��06��05��</td>
			    <td style="text-align:right;">����һ</td>
			  </tr>
			  <tr>
			    <td style="font-size:14px; width:20px" height="30"><img src="mainPage/images/images/index_41.png" width="13" height="13" /></td>
			    <td>���������������������ʾ</td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			     <td style="font-size:14px; width:20px;" height="20"><img src="mainPage/images/images/index_41.png" width="13" height="13" /></td>
			    <td>���������������������ʾ</td>
			    <td>&nbsp;</td>
			  </tr>
			</table>

           </div>
           <div style="border:none; margin-top:20px;" class="contain_right_all_shixiang_1">
             <table width="100%" border="0">
				  <tr style="font-size:16px;">
				    <td  colspan="2">2019��06��05��</td>
				    <td style="text-align:right;">����һ</td>
				  </tr>
				  <tr>
				    <td style="font-size:14px;width:20px;" height="30"><img src="mainPage/images/images/index_41.png" width="13" height="13" /></td>
				    <td>���������������������ʾ</td>
				    <td>&nbsp;</td>
				  </tr>
				  <tr>
				     <td style="font-size:14px;" height="20"><img src="mainPage/images/images/index_41.png" width="13" height="13" /></td>
				    <td>���������������������ʾ</td>
				    <td>&nbsp;</td>
				  </tr>
			</table>

           </div> -->
           </div>
       </div>
       <div class="contain_right_all_2">
         <div class="contain_right_all_2_title">
             <div style="float:left; color:#219dc6; font-size:18px;"><img src="mainPage/images/images/index_13.png" width="20" height="20" />&nbsp;&nbsp;���߷���</div><div style="float:right; margin-top:15px;"><a href="#" onclick="openquery()"><img src="mainPage/images/images/index_21.png" width="8" height="10" /></a></div>
         </div>
         <div class="contain_right_all_news" id="file_list">
           <!-- <div class="contain_right_all_news_list">
            <div class="contain_right_all_news_list_left">1</div>
              <div class="contain_right_all_news_list_center">
                <table width="100%" border="0">
  <tr>
    <td style="font-size:16px; font-weight:800; height:30px;"><a href="#">���߹涨�������ִ����</a></td>
    <td style="font-size:12px; text-align:right;">2019/06/03   09:20</td>
  </tr>
  <tr>
    <td colspan="2">��ʾ��������</td>
    </tr>
</table>

             </div>
             <div class="contain_right_all_news_list_right"></div>
           </div> -->
           <!-- <div class="contain_right_all_news_list">
            <div class="contain_right_all_news_list_left">2</div>
              <div class="contain_right_all_news_list_center">
                <table width="100%" border="0">
  <tr>
    <td style="font-size:16px; font-weight:800; height:30px;"><a href="#">���߹涨�������ִ����</a></td>
    <td style="font-size:12px; text-align:right;">2019/06/03   09:20</td>
  </tr>
  <tr>
    <td colspan="2">��ʾ��������</td>
    </tr>
</table>

             </div>
             <div class="contain_right_all_news_list_right"></div>
           </div> -->
<!--            <div class="contain_right_all_news_list">
            <div class="contain_right_all_news_list_left">3</div>
              <div class="contain_right_all_news_list_center">
                <table width="100%" border="0">
  <tr>
    <td style="font-size:16px; font-weight:800; height:30px;"><a href="#">���߹涨�������ִ����</a></td>
    <td style="font-size:12px; text-align:right;">2019/06/03   09:20</td>
  </tr>
  <tr>
    <td colspan="2">��ʾ��������</td>
    </tr>
</table>

             </div>
        //     <div class="contain_right_all_news_list_right"></div>
           </div> -->
         </div>
         
       </div>
       <div class="contain_right_all_3">
         <div class="contain_right_all_3_title">
          <div style="float:left; color:#219dc6; font-size:18px;"><img src="mainPage/images/images/index_52.png" width="23" height="16" />&nbsp;&nbsp;��������</div><div style="float:right; margin-top:15px;"><a href="javascript:void()" onclick="searchCond();"><img src="mainPage/images/images/index_21.png" width="8" height="10" /></a></div>
         </div>
         <div class="contain_right_all_3_mingce">
         <ul>
           <li onclick="openWinZ45()" style="cursor: pointer;height: 50px;line-height:50px;">45����������</li>
           <li onclick="openWinZ40()" style="margin-right:0;cursor: pointer;height: 50px;line-height:50px;">40����������</li>
           <li onclick="openWinF40()" style="cursor: pointer;height: 50px;line-height:50px;">40�����¸���</li>
           <li onclick="openWinF35()" style="margin-right:0;cursor: pointer;height: 50px;line-height:50px;">35�����¸���</li>
           <li onclick="openWinDwsggb()" style="cursor: pointer;height: 50px;line-height:50px;">�����йܸɲ�</li>
           <li onclick="openWinNsggb()" style="margin-right:0;cursor: pointer;height: 50px;line-height:50px;">Ů�йܸɲ�</li>
           <li onclick="openWinBnntx()" style="cursor: pointer;height: 50px;line-height:50px;">����������</li>
           <li onclick="openWinXqxzgb()" style="margin-right:0;cursor: pointer;height: 50px;line-height:50px;">��������ɲ�</li>
         </ul>
         </div>
       </div>
       <div class="contain_right_all_4">
         <div class="contain_right_all_4_title">
          <div style="float:left; color:#219dc6; font-size:18px;"><img src="mainPage/images/images/index_54.png" width="20" height="17" />&nbsp;&nbsp;ҵ�����</div><div style="float:right; margin-top:15px;"><a href="#" onclick="tabOpen(7)"><img src="mainPage/images/images/index_21.png" width="8" height="10" /></a></div>
         </div>
         <div class="contain_right_all_4_fenxi" id="rychart" style="height: 270px;width: 500px;">
         <!-- <img src="mainPage/images/images/index_61.png" width="517" height="229" /> -->
         </div>
       </div>
     </div>
  </div>
</div>
<div class="bottom">����֧��:������¶���������޹�˾</div>
</body>
</html>
<script>
function tabOpen(n){ 
	for(var i=1;i<=6;i++){
		if(i==n){
			$('#li_tab'+n).css('background-color','#eaedf1');
			$('#li_tab'+n+' div').css('color','#2a579a');
			$('#li_tab'+n+' div a').css('color','#2a579a');
		}else{
			$('#li_tab'+i).css('background-color','');
			$('#li_tab'+i+' div').css('color','');
			$('#li_tab'+i+' div a').css('color','');
		}
	}
	window.open('MainX.jsp?tabId='+n,'tab'+n);
}

function openWinZ45(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q01','tab4');
}
function openWinZ40(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q02','tab4');
}
function openWinF40(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q03','tab4');
}
function openWinF35(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q04','tab4');
}
function openWinDwsggb(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q05','tab4');
}
function openWinNsggb(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q06','tab4');
}
function openWinBnntx(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q07','tab4');
}
function openWinXqxzgb(){ 
	window.open('IndexO.jsp?tabId=4&qt=1&qid=q08','tab4');
}
function searchCond(){ 
	window.open('IndexO.jsp?tabId=8','tab8');
}

function logout(){
	window.top.location.href='<%=request.getContextPath()%>/mainLogOff.jsp';
}
function openquery(){
	window.open('http://127.0.0.1:8088/query_solr','toQuery');
}
function getInfoToDo(){
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.comm.InfoToDo&eventNames=getDBmsgs",
		success: function(resData){
			var arr = Ext.util.JSON.decode(resData.responseText);
			var dbdiv = $("#db_div");
			//console.log(arr);
			var count = 0;
			var count1 = 0;
			var rq = '';
			var table = null;
			var div = null;
			for(var i=0; i<arr.length; i++){
				var info = arr[i];
				if(rq!=info.rq){
					if((count==1 && count1>=6) || (count==2 && count1>=5) || (count==3 && count1>=3)){
						break;
					}
					rq=info.rq;
					div = $('<div class="contain_right_all_shixiang_1" style="padding-bottom:10px">');
			        table = $('<table width="100%" border="0">');
					div.append(table);
					var tr = $('<tr style="font-size:16px;">');
					table.append(tr);
					var td1 = $('<td colspan="2">');
					td1.html(rq);
					tr.append(td1);
					var td2 = $('<td style="text-align:right;">');
					td2.html(info.xq);
					tr.append(td2);
					count ++;
					dbdiv.append(div);
				}
				var tr = $('<tr syle="cursor: pointer;" >');
				table.append(tr);
				var td1 = $('<td style="font-size:14px; width:20px" height="25">');
				td1.html('<img src="mainPage/images/images/index_41.png" width="13" height="13" />');
				tr.append(td1);
				var td2 = $('<td>');
				td2.html('<a href="javascript:void();" onclick="openDBInfo(\''+info.itdc003+'\',\''+info.itdr000+'\')">����һ�� '+info.itdc002+ ' ��Ҫ���� </a>');
				tr.append(td2);
				var td3 = $('<td>');
				td3.html('&nbsp;');
				tr.append(td3);
				count1 ++;
				dbdiv.append(div);
				//console.log(count);
				//console.log(count1);
				if((count==1 && count1>=7) || (count==2 && count1>=6) || (count==3 && count1>=3)){
					break;
				}
				
			}
			div.css('border', "0px");
			
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
}

function moreDBInfo(){
	window.open('IndexO.jsp?tabId=dbInfoMore','tab8');
}
function refreshDBInfo(){
	var dbdiv = $("#db_div");
	dbdiv.empty();
	//alert();
	getInfoToDo();
}
function openDBInfo(type, rid){
	window.open('IndexO.jsp?tabId=dbInfo&rid='+rid,'tab7');
}

function rychart(){
	var res=<%=ryJson%>
	//res=eval('('+res+')');
	document.getElementById("rychart").innerHTML='';
	var chart = echarts.init(document.getElementById('rychart'));
    var option = {
      title : {
          text: '�ɲ���Ա�ֲ�'
      },
      tooltip : {
          trigger: 'axis'
      },
      legend: {
          data:['����']
      },
      toolbox: {
          show : true,
          feature : {
              mark : {show: true}
          }
      },
      calculable : true,
      xAxis : [
          {
              data : ['�йܸɲ�','���ܸɲ�','3���ڵ�60����','������'],
              axisLabel:{
                //rotate:-45,
                fontSize:10
              }
              //nameLocation:'center'
          }
      ],
      yAxis : [
          {
              type : 'value',
              axisLabel:{
                //rotate:-45
              }
          }
      ],
      series : [
          {
              name:'����',
              type:'bar',
              data:[res[0].num,res[1].num,res[2].num,res[3].num],
              itemStyle : { normal: {
                color: '#327bc1'
                //borderRadius: 5
            }
            },
              markPoint : {
                symbolSize:50,
                  data : [
                      {value : res[0].num, xAxis: 0, yAxis: res[0].num},
                      {value : res[1].num, xAxis: 1, yAxis: res[1].num},
                      {value : res[2].num, xAxis: 2, yAxis: res[2].num},
                      {value : res[3].num, xAxis: 3, yAxis: res[3].num}
                  ]
              }
          }
      ]
  };
    chart.setOption(option);
}

function getFileList(){
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.policy.PolicySetList&eventNames=getFileList",
		success: function(resData){
			var arr = Ext.util.JSON.decode(resData.responseText);
			$('#file_list').html('');
			var addHtml='';
			for(var i=0;i<arr.length;i++){
				var file = arr[i];
				if(file!=''){
					var filename = file.filename.substring(0,file.filename.indexOf("."));
					if(i==2){
						addHtml += "<div class='contain_right_all_news_list'>";
						addHtml += "<div class='contain_right_all_news_list_left'>"+(i+1)+"</div>";
						addHtml += "<div class='contain_right_all_news_list_center'><table width='100%' border='0'><tr>";
						addHtml += "<td style='font-size:16px; height:30px;''><a href='#' onclick=openFile('"+file.fileurl+"')>"+filename+"</a></td>";
						addHtml += "<td style='font-size:12px; text-align:right;'>"+file.updatetime+"</td>";
						addHtml += "</tr><tr><td colspan='2'></td></tr></table><a href='#' style='float:right;' onclick='hasMore()'>����</a></div><div class='contain_right_all_news_list_right'></div></div>";
					}else if(i<2){
						addHtml += "<div class='contain_right_all_news_list'>";
						addHtml += "<div class='contain_right_all_news_list_left'>"+(i+1)+"</div>";
						addHtml += "<div class='contain_right_all_news_list_center'><table width='100%' border='0'><tr>";
						addHtml += "<td style='font-size:16px; height:30px;''><a href='#' onclick=openFile('"+file.fileurl+"')>"+filename+"</a></td>";
						addHtml += "<td style='font-size:12px; text-align:right;'>"+file.updatetime+"</td>";
						addHtml += "</tr><tr><td colspan='2'></td></tr></table></div><div class='contain_right_all_news_list_right'></div></div>";
					}
				}
			}
			$('#file_list').html(addHtml);
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
}
function hasMore(){
	window.open('IndexO.jsp?tabId=13','tab13');
}
function openFile(path){
	//window.location.href="<%=request.getContextPath()%>/SorlQueryServlet?path="+encodeURI(encodeURI(path));
	window.open("<%=request.getContextPath()%>/SorlQueryServlet?path="+encodeURI(encodeURI(path)));
}
Ext.onReady(function(){
	getInfoToDo();
	getFileList();
})
</script>