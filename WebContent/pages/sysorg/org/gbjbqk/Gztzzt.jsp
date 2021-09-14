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
				<odin:select property="zwlb" codeType="zwcelb" /><!-- label="职务类别" -->
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
   						if(arr[0]==1){//成功
   							document.getElementById("jsonString_str").value=arr[1];
   							json_func();
   						}else if(arr[0]==2){
   							console(arr[1]);//统计异常
   						}
   					}
        	   }
	        }
	      });
}

var obj1 =[];//x
var obj2=[];//y
var rotate_h=-30;//F C A B 3  改    F  C  74  75  5
var y2_h=60;

function change_tb(zwlb){
	var countnum = 0;
	var name='';
	if(zwlb=='AAA'){
		name ='系统管理员';
	}else if(zwlb=='BBB'){
		name ='安全管理员';
	}else if(zwlb=='CCC'){
		name ='审计管理员';
	}else if(zwlb=='DDD'){
		name ='普通用户';
	}else if(zwlb=='1A'){
		name ='综合管理类';
	}else if(zwlb=='1B'){
		name ='专业技术类';
	}else if(zwlb=='1C'){
		name = '行政执法类';
	}else if(zwlb=='2'){
		name = '人民警察警员职务序列';
	}else if(zwlb=='3'){
		name = '法官等级';
	}else if(zwlb=='4'){
		name = '检察官等级';
	}else if(zwlb=='5'){
		name = '警务技术等级';
	}else if(zwlb=='6'){
		name = '执法勤务警员职务等级';
	}else if(zwlb=='71'){
		name = '深圳市执法员';
	}else if(zwlb=='72'){
		name = '深圳市警员';
	}else if(zwlb=='73'){
		name = '深圳警务技术职务';
	}else if(zwlb=='74'){
		name = '深圳市气象预报员';
	}else if(zwlb=='75'){
		name = '深圳市气象信息员';
	}else if(zwlb=='9'){
		name = '事业单位管理等级';
	}else if(zwlb=='C'){
		name = '事业单位专业技术岗位';
	}else if(zwlb=='D'){
		name = '机关技术工人岗位';
	}else if(zwlb=='E'){
		name = '机关普通工人岗位';
	}else if(zwlb=='F'){
		name = '事业单位技术工人岗位';
	}else if(zwlb=='G'){
		name = '事业单位普通工人岗位';
	}else if(zwlb=='QT'){
		name = '其他';
	}else{
		name='';
	}
	try{
		var num_c=0;
		obj1=[];
		obj2=[];
		for(var i=0;i<jsonstr.length;i++){
			countnum = countnum+parseInt(jsonstr[i].num);//总人数
			if(jsonstr[i].sub_code_value==zwlb){
				obj1.push(jsonstr[i].code_name);
				obj2.push(jsonstr[i].num);
				num_c=num_c+parseInt(jsonstr[i].num);
			}
		}
		/* if(zwlb=='F'||zwlb=='C'||zwlb=='74'||zwlb=='75'||zwlb=='5'){//职务层级 文字过长，高度，倾斜度单独设置
			//rotate_h=-40;
			//y2_h=95;
			//文字横向显示，不在重新设置高度
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
		if(name==''||num_c==0){//该分类下没人
			window.parent.document.getElementById("pg_analysis").innerHTML="共"+countnum+ "人,"+name+num_c+"人";
			return;
		}
		var ratio = (num_c/countnum*100).toFixed(2);
		window.parent.document.getElementById("pg_analysis").innerHTML="共"+countnum+ "人,"+name+num_c+"人,约占比"+ratio+"%";
	    <%
	    	}else{
	    %>
	    setbingzhuang(); 
	    window.parent.document.getElementById("pg_analysis").innerHTML="共"+countnum+ "人";
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

//饼状图表实现 
function setbingzhuang(){
	// // 基于准备好的dom，初始化echarts实例
	try{
		document.getElementById("bingzhuang").innerHTML='';
		var myChart = echarts.init(document.getElementById('bingzhuang'));
		// // 使用刚指定的配置项和数据显示图表。
		var option = {
				    title : {
				        text: '',/* 基本情况信息(综合) */
				        subtext: ''
				    },
				    barWidth : 30,
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				    	show:false,
				        data:['人数']
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
				    		name:'人数',
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
               title: '人员信息',
               html: '<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+src+'&groupID='+'zwflcustom@@'+zwflcustom+'@@'+encodeURI(encodeURI(namecustom))+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
         	    listeners:{//判断页面是否更改，
         	    	
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