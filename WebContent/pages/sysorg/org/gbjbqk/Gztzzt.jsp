<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<div style="width:100%;height:100%;" id="div_gztzzt">
	<%-- <div>
		<table style="width:100%;height:100%;">
			<tr align="right">
				<odin:select property="zwlb" codeType="zwcelb" /><!-- label="ְ�����" -->
			</tr>
		</table>
	</div> --%>
	<div id="bingzhuang"  >
	</div>
</div>
<odin:hidden property="jsonString_str"/>
<odin:hidden property="jsonString_str1"/>
<script type="text/javascript">
Ext.onReady(function (){
	startLoadDate();
});
function startLoadDate(){
	   var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.gbjbqk.Gztzzt&eventNames=loadDate';
	   	Ext.Ajax.request({
	   		timeout: 900000,
	   		url: path,
	   		async: true,
            form:'data',
            callback: function (options, success, response) {
        	   if (success) {
        		   var result = response.responseText;
   					if(result){
   						var json = eval('(' + result + ')');
   						var data_str=json.data;
   						var arr=data_str.split('@');
   						if(arr[0]==1){//�ɹ�
   							document.getElementById("jsonString_str").value=arr[1];
   							json_func();
   						}else if(arr[0]==2){
   							console(arr[1]);//ͳ���쳣
   						}
   					}
        	   }
	        }
	      });
}

var obj1 =[];//x
var obj2=[];//y
var rotate_h=-30;//F C A B 3  ��    F  C  74  75  5
var y2_h=60;

function change_tb(zwlb){
	var countnum = 0;
	var name='';
	if(zwlb=='AAA'){
		name ='ϵͳ����Ա';
	}else if(zwlb=='BBB'){
		name ='��ȫ����Ա';
	}else if(zwlb=='CCC'){
		name ='��ƹ���Ա';
	}else if(zwlb=='DDD'){
		name ='��ͨ�û�';
	}else if(zwlb=='1A'){
		name ='�ۺϹ�����';
	}else if(zwlb=='1B'){
		name ='רҵ������';
	}else if(zwlb=='1C'){
		name = '����ִ����';
	}else if(zwlb=='2'){
		name = '���񾯲쾯Աְ������';
	}else if(zwlb=='3'){
		name = '���ٵȼ�';
	}else if(zwlb=='4'){
		name = '���ٵȼ�';
	}else if(zwlb=='5'){
		name = '�������ȼ�';
	}else if(zwlb=='6'){
		name = 'ִ������Աְ��ȼ�';
	}else if(zwlb=='71'){
		name = '������ִ��Ա';
	}else if(zwlb=='72'){
		name = '�����о�Ա';
	}else if(zwlb=='73'){
		name = '���ھ�����ְ��';
	}else if(zwlb=='74'){
		name = '����������Ԥ��Ա';
	}else if(zwlb=='75'){
		name = '������������ϢԱ';
	}else if(zwlb=='9'){
		name = '��ҵ��λ����ȼ�';
	}else if(zwlb=='C'){
		name = '��ҵ��λרҵ������λ';
	}else if(zwlb=='D'){
		name = '���ؼ������˸�λ';
	}else if(zwlb=='E'){
		name = '������ͨ���˸�λ';
	}else if(zwlb=='F'){
		name = '��ҵ��λ�������˸�λ';
	}else if(zwlb=='G'){
		name = '��ҵ��λ��ͨ���˸�λ';
	}else if(zwlb=='QT'){
		name = '����';
	}else{
		name='';
	}
	try{
		var num_c=0;
		obj1=[];
		obj2=[];
		for(var i=0;i<jsonstr.length;i++){
			countnum = countnum+parseInt(jsonstr[i].num);//������
			if(jsonstr[i].sub_code_value==zwlb){
				obj1.push(jsonstr[i].code_name);
				obj2.push(jsonstr[i].num);
				num_c=num_c+parseInt(jsonstr[i].num);
			}
		}
		/* if(zwlb=='F'||zwlb=='C'||zwlb=='74'||zwlb=='75'||zwlb=='5'){//ְ��㼶 ���ֹ������߶ȣ���б�ȵ�������
			//rotate_h=-40;
			//y2_h=95;
			//���ֺ�����ʾ�������������ø߶�
			rotate_h=-30;
			y2_h=32;
		}else{
			rotate_h=-30;
			y2_h=32;
		} */
		 <% 
	    	if(PrivilegeManager.getInstance().getCueLoginUser().getUsertype().equals("0")){
	    %>
		setbingzhuang(); 
		if(name==''||num_c==0){//�÷�����û��
			window.parent.document.getElementById("pg_analysis").innerHTML="��"+countnum+ "��,"+name+num_c+"��";
			return;
		}
		var ratio = (num_c/countnum*100).toFixed(2);
		window.parent.document.getElementById("pg_analysis").innerHTML="��"+countnum+ "��,"+name+num_c+"��,Լռ��"+ratio+"%";
	    <%
	    	}else{
	    %>
	    setbingzhuang(); 
	    window.parent.document.getElementById("pg_analysis").innerHTML="��"+countnum+ "��";
	    <%
	    }
	    %>
	}catch(err){
		
	}
}
var jsonstr="";
function json_func(){
	jsonstr=document.getElementById("jsonString_str").value;
    try{
    	jsonstr = eval('(' + jsonstr + ')');
    }catch(err){
    }
    <% 
    	UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
    	if(user.getUsertype().equals("0")){
    %>
    change_tb('1A');
    <%
    	}else{
    %>
    change_tb('AAA');
    <%
    }
    %>
}

//��״ͼ��ʵ�� 
function setbingzhuang(){
	// // ����׼���õ�dom����ʼ��echartsʵ��
	try{
		document.getElementById("bingzhuang").innerHTML='';
		var myChart = echarts.init(document.getElementById('bingzhuang'));
		// // ʹ�ø�ָ�����������������ʾͼ��
		var option = {
				    title : {
				        text: '',/* ���������Ϣ(�ۺ�) */
				        subtext: ''
				    },
				    barWidth : 30,
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				    	show:false,
				        data:['����']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: false, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            //restore : {show: true},
				            saveAsImage : {show: false}
				        }
				    },
				    calculable : true,
				    grid:{
				    	x:65,
		                y:35,
		                x2:30,
				    	y2:30
				    },
				    xAxis :
				        {
				            type : 'category',
				            data : obj1,
				            position:'bottom',
				            axisTick: {
				                alignWithLabel: true
				            },
				            axisLabel:{
				            	show:true,
				            	/* rotate:rotate_h, */
				                interval:0
				            }
				        },
				    yAxis : 
				        {
				            type : 'value'
				        },
				    
				    series : 
				        {
				    		show:true,
				    		name:'����',
				            type:'bar',
				            data:obj2,
				            itemStyle : { normal: {
				                color: '#327bc1',
				                borderRadius: 5
				            }
				            },
				            label:{ 
					            normal:{ 
					            	show: true, 
					            	position: 'top'
					            } 
				            }
				        }
				    
			};
		myChart.setOption(option);
		myChart.on('click', function (param) {
			var zwflcustom=window.parent.document.getElementById("zwlb_select").value;
			var namecustom=param.name;
			queryPersonBy(zwflcustom,namecustom);
		});
	}catch(err){
	}
}

function queryPersonBy(zwflcustom,namecustom){
      var aid=zwflcustom+namecustom;
      var tab=parent.parent.tabs.getItem(aid);
      if (tab){ 
     	parent.parent.tabs.activate(aid);
      }else{
       	var src = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery';
           parent.parent.parent.tabs.add({
               id: aid,
               title: '��Ա��Ϣ',
               html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'zwflcustom@@'+zwflcustom+'@@'+encodeURI(encodeURI(namecustom))+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
         	    listeners:{//�ж�ҳ���Ƿ���ģ�
         	    	
         	    },
         	    closable:true
               }).show();
      }
}

Ext.onReady(function(){
	var height=document.getElementById("div_gztzzt").parentNode.parentNode.offsetHeight;
	var width=document.getElementById("div_gztzzt").parentNode.parentNode.offsetWidth;
	document.getElementById("bingzhuang").style.height=height;
	document.getElementById("bingzhuang").style.width=width;
	document.getElementById("div_gztzzt").parentNode.parentNode.style.overflow='hidden';
	window.onresize=heightNextSet;
});
function heightNextSet(){
	try{
		var height=document.getElementById("div_gztzzt").parentNode.parentNode.offsetHeight;
		var width=document.getElementById("div_gztzzt").parentNode.parentNode.offsetWidth;
		document.getElementById("bingzhuang").style.height=height;
		document.getElementById("bingzhuang").style.width=width;
		var zwlb=window.parent.document.getElementById("zwlb_select").value;
		change_tb(zwlb);
	}catch(err){
		
	}
}
</script>