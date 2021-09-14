<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����״̬����</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/js/echarts.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
Ext.onReady(function(){
	//radow.doEvent("bzyp",1);//����״��
	// ����׼���õ�dom����ʼ��echartsʵ��
	
});


</script>
<style type="text/css">
	/* .main{
	    
	    background-color: #fff;
	    border-radius: 20px;
	    width: 800px;
	    height: 450px;
	    margin: auto;
	    position: absolute;
	    top: 20;
	    left: 20%;
	    bottom: 0;
	} */
	
	.font-tab {
	font-family: "����";
	font-size: 16px;
}
.inputtable{border-collapse:collapse;}
.inputtable th{border-collapse:collapse;background:#EFEFEF;width:100px;border:1px dotted #CCCCCC;
	               font-weight:bold;text-align:center;padding:3px;}
.inputtable tr td{border-collapse:collapse;border:1px dotted #CCCCCC;width:100px;padding:3px;}
</style>
<script type="text/javascript">

function comprevalue(sumcount){
    var count = sumcount.split(",");

	document.getElementById("yhong").innerText=count[0];
	document.getElementById("fyhong").innerText=count[1];
	document.getElementById("huang").innerText=count[2];
	document.getElementById("lv").innerText=count[3];
	document.getElementById("wei").innerText=count[4];
	
	var myChart = echarts.init(document.getElementById('div2'));
	
	//app.title = '����ͼ';

	option = {
		   title : {
			   text: '����״̬����',
		       left: 'center'
		   },
		   tooltip : {
		       trigger: 'axis'
		   },
		   /* legend: {
		       data:['������']
		   }, */
		   toolbox: {
		       show : true/* ,
		       feature : {
		    	   dataView : {show: true, readOnly: false},
		           magicType : {show: true, type: ['line', 'bar']},
		           restore : {show: true},
		           saveAsImage : {show: true}
		       } */
		   },
		   calculable : true,
		   xAxis : [
		       {
		           type : 'category',
		           data : ['��(һƱ���)','��','��','��','δ����']
		       }
		   ],
		   yAxis : [
		       {
		           type : 'value'
		       }
		   ],
		   series : [
		       {
		           name:'����',
		           type:'bar',
		           label: {
		               normal: {
		                   show: true,
		                   position: 'top'
		               }
		           },
		           data:[ {value:count[0],name: '��(һƱ���)',itemStyle: {color:'Red'}},
		                  {value:count[1],name: '��',itemStyle: {color:'OrangeRed'}},
		                  {value:count[2], name: '��',itemStyle: {color:'Gold'}},
		                  {value:count[3], name: '��',itemStyle: {color:'green'}},
		                  {value:count[4], name: 'δ����',itemStyle: {color:'rgb(210,223,242)'}}]
		       }
		   ]
	};

	// ʹ�ø�ָ�����������������ʾͼ��
	myChart.setOption(option);
	
	/* myChart.on('click', function (params) { 
        //param��������Ĳ����� https://blog.csdn.net/allenjay11/article/details/76033232
        console.log(option.data[params.dataIndex]);console.log(params.value);
          //updatePage(option.data[params.dataIndex],params.value);
          //refresh();
    }); */
}

function fanc(type){
	$h.openPageModeWin('troupeDetail','pages.templateconf.TroupeDetail','����״̬����',1350,1100,type,'<%=request.getContextPath()%>',window);
}

function refresh(){             
    
    //�ֲ�ˢ��series����
    //�˴�û���ó��õ�ˢ��div�ȷ���������ֱ�Ӹı���option��ֵ��Ȼ�����¸�ֵ��myChart
   
    //�򻯷���������getSeriesData�������ݡ�
    option.series.data = getSeriesData();
    
    myChart.setOption(option);
}

function dataTb(){
	ShowCellCover("start","��ܰ��ʾ��","����ͬ�����ݣ�����ʱ��ϳ������Ժ�...");
	
	 Ext.Ajax.request({
			method: 'POST',
	        async: true,
	        params : {},
	        timeout :300000,//���������
			url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.TeamStateResearch&eventNames=dataTb",
			success: function(resData){
				var cfg = Ext.util.JSON.decode(resData.responseText);
				if(0==cfg.messageCode){
					Ext.Msg.hide();	
					
					if(cfg.elementsScript.indexOf("\n")>0){
						cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
						cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
					}
					
					eval(cfg.elementsScript);
					Ext.Msg.alert("��ܰ��ʾ","ͬ���ɹ���");
					radow.doEvent("initX");
				}else{
					Ext.Msg.hide();	
					
					if(cfg.mainMessage.indexOf("<br/>")>0){
						Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
						return;
					}
					Ext.Msg.alert("��ܰ��ʾ","ͬ��ʧ�ܣ�");
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
</head>
<input type="hidden" name="flag" id="flag"/> 
<body>
	
		<div id="div1" style="float: left;margin-left:1%">
		<table>
			<tr><td style="height: 20px"><td></tr>
		</table>
		<table class="inputtable"  style="width:280px;text-align:center;margin:0 auto;border-bottom:0px;margin-left: 100px;" >
			<tr>
				<td height=80px; style='width:50%;text-align: center;background-color: rgb(239,239,239)'><font class="font-tab">����</font></td>
				<td style='width:25%;text-align: center;background-color: rgb(239,239,239)'><font class="font-tab">����</font></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;text-align: center;background-color: red"><font class="font-tab">��<br/>��һƱ�����</font></td>
				<td><a href='javascript:void(0)'><span id="yhong" class="css1" " onclick="fanc(1)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:OrangeRed;text-align: center;"><font class="font-tab">��<br/>����һƱ�����</font></td>
				<td><a href='javascript:void(0)'><span id="fyhong" class="css1" " onclick="fanc(2)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:yellow;text-align: center;"><font class="font-tab">��</font></td>
				<td><a href='javascript:void(0)'><span id="huang" class="css1" " onclick="fanc(3)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:green;text-align: center;"><font class="font-tab">��</font></td>
				<td><a href='javascript:void(0)'><span id="lv" class="css1" " onclick="fanc(4)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:rgb(210,223,242);text-align: center;"><font class="font-tab">δ����</font></td>
				<td><a href='javascript:void(0)'><span id="wei" class="css1" " onclick="fanc(5)"></a></td>
			</tr>
			
        </table>
        
		</div>
        
    <div id="div2" style="width:880px;height:520px;text-align:center;float: right;margin-right: 3%;margin-top: 1%">
        	<div style="float: right;"><odin:button text="����ˢ��" handler="dataTb"></odin:button></div>
    </div>
    
</body>
</html>