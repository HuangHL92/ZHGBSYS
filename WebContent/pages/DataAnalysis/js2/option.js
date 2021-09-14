/**
 * ������ͨ��ͼ
 * @param data
 * @param cityid
 */
function showMap(data,cityid){
	 var myChart = echarts.init(document.getElementById('chart1-1'),'shine');
    var data = data;/* || [
        {name:"�㽭ʡ", value:102012},
        {name:"����ʡ", value:132112},
        {name:"�Ĵ�ʡ", value:32374},
        {name:"����ʡ", value:34150},
        {name:"ɽ��ʡ", value:45231},
        {name:"�ຣʡ", value:45334},
        {name:"����ʡ", value:79851},
        {name:"�㶫ʡ", value:75234},
        {name:"����ʡ", value:23464},
        {name:"����ʡ", value:132112},
    ];*/
    var dataArray = getArrValue(data);
    var maxValue = Math.max.apply(null,dataArray);
    var minValue = Math.min.apply(null,dataArray);
    var option = {
       visualMap: {
            min: minValue*0.9,
            max: maxValue*1.1,
            right: 30,
            bottom: 10,
            itemWidth:"30%",
            itemHeight:"100%",
            calculable: false
           /* inRange:{
                color: ['#ffdab9','#ffa07a']
            }*/
        },
        tooltip:{
            show: true,
        },
        series: [{
            type: 'map',
            name:"����",
            mapType: cityid,
            //zoom: 1.2,
           /* aspectScale:1,   */ //��ͼ��߱�
            roam: true,     //�Ƿ���������ź͵�ͼ�϶���Ĭ�ϲ����������ֻ��Ҫ�������Ż����϶����������ó� 'scale' ���� 'move'�����ó� true Ϊ������
          
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    textStyle: {
                        //color: '#000'
                    }
                }
            },
            itemStyle: {
                normal: {
                   // borderColor: '#fff',
                    borderWidth:0.3
                  //  areaColor: '#fff',
                },
                emphasis: {
                   // areaColor: '#a3c1ff',
                    borderWidth: 0
                }
            },
            animation: false,
            data: data
        }]
    };
    $.getJSON('js2/map_city/' + cityid + '_full.json', function (json) {
        echarts.registerMap(cityid, json);
        var mapJsonData = convertMapData(data,json);
        option.series[0].data = mapJsonData;
       if(cityid=='460000') { //���ں��ϵ�ͼ�����Ϻ����Ϻ�����ڵĴ�Ƭ����������ʾ���ϵ�ͼʱ��Ҫ����ͼ�Ŵ󣬲����ú��ϵ�������ʾ
     	option.series[0].center = [109.844902, 19.0392];
     	option.series[0].layoutCenter = ['50%', '50%'];
     	option.series[0].layoutSize = "600%";
     	} else if(cityid=='100000') { //����ʾ����ʱ�������õĲ����ָ�Ĭ��ֵ
     	option.series[0].center = undefined;
     	option.series[0].layoutCenter = ['50%','70%'];
     	option.series[0].layoutSize = '120%';
     	}else{
     		option.series[0].center = undefined;
         	option.series[0].layoutCenter = undefined;
         	option.series[0].layoutSize = undefined;
     	}
        getChart("chart1-1").setOption(option);
       /* $('#ccc').change(function(){
        	var pifu =  $("#ccc").find("option:selected").val();
		       if(pifu == 'macarons'){
		    	   myChart.dispose();
		            myChart = echarts.init(document.getElementById('chart1-1'),'macarons');
		            myChart.setOption(option);
		       }else{
		    	    myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart1-1'),'shine');
			        myChart.setOption(option);
		       }
           
        });*/
        var i = 0;
        $('#ccc').click(function(){
        	if(i++%2 == 0){
        		myChart.dispose();
    	        myChart = echarts.init(document.getElementById('chart1-1'),'macarons');
    	        myChart.setOption(option);
        	}else{
        		 myChart.dispose();
    		     myChart = echarts.init(document.getElementById('chart1-1'),'shine');
    		     myChart.setOption(option);
        	}
        });
/*        myChart.on('click', function (params) {
	    	 var type =  $("#bbb").find("option:selected").val();
			 var cityid = document.getElementById("cityid").value;
	    	 var name = params.name; 
	    	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
	    	 var tjtype = document.getElementById("tjtype").value;
			 if(type == 'zt'){
			    $h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=00&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
			 }
	    });*/
    });
}



/**
 * ������ȡ��ͼ
 * @param data
 * @param cityid
 */
function showMap2(data,cityid){
    var data = data || [
        {id:"330000", value:102012},
        {id:"330200", value:132112}
       
    ];
    var dataArray = getArrValue(data);
    var maxValue = Math.max.apply(null,dataArray);
    var minValue = Math.min.apply(null,dataArray);
    var option = {
        visualMap: {
            min: minValue*0.9,
            max: maxValue*1.1,
            right: 30,
            bottom: 10,
            itemWidth:"30%",
            itemHeight:"100%",
            calculable: false,
            inRange:{
                color: ['#fffbc5','#fdb99c']
            }
        },
        tooltip:{
            show: true,
        },
        series: [{
            type: 'map',
            name:"����Ա����",
            mapType: cityid,
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    textStyle: {
                        color: '#000'
                    }
                }
            },
            itemStyle: {
                normal: {
                    borderColor: '#fff',
                    borderWidth:0.3,
                    areaColor: '#fff',
                },
                emphasis: {
                    areaColor: '#a3c1ff',
                    borderWidth: 0
                }
            },
            animation: false,
            data: data
        }]
    };
    $.getJSON('js2/map_city/' + cityid + '_full.json', function (json) {
    
        echarts.registerMap(cityid, json);
        var mapJsonData = convertMapData2(data,json);
        option.series[0].data = mapJsonData;
       /* if(cityid=='460000') { //���ں��ϵ�ͼ�����Ϻ����Ϻ�����ڵĴ�Ƭ����������ʾ���ϵ�ͼʱ��Ҫ����ͼ�Ŵ󣬲����ú��ϵ�������ʾ
         	option.series[0].center = [109.844902, 19.0392];
         	option.series[0].layoutCenter = ['50%', '50%'];
         	option.series[0].layoutSize = "600%";
         	} else { //����ʾ����ʱ�������õĲ����ָ�Ĭ��ֵ
         	option.series[0].center = undefined;
         	option.series[0].layoutCenter = undefined;
         	option.series[0].layoutSize = undefined;
         	}*/
        getChart("chart1-1").setOption(option);
        getChart("chart1-1").off("click");
        getChart("chart1-1").off("dblclick");
        var clickJob,
            preMapData = {id:"100000"};
        getChart("chart1-1").on("click",function(p){
            clearTimeout(clickJob);
            clickJob = setTimeout(function(){
                switchMap(p.data);
            },500);
        });
        getChart("chart1-1").on("dblclick",function(p){
            clearTimeout(clickJob);
            if(!preMapData){
                console.log("�ϼ�����δָ����");
                return;
            }
            switchMap(preMapData);
        });
    });
}

function showBar2(data){
	var data = data;
	var myChart = echarts.init(document.getElementById('chart1'),'shine');
	/*  ||[{value:535, name: '����'},
      {value:510, name: '����'},
      {value:634, name: '����'},
      {value:735, name: '����'}];*/
	 var option = {
		   // color:["#ffa995","#fff797","#a6ffd2","#a3c1ff"],
			 tooltip : {                    	            
				 trigger: 'item',                    	            
				 formatter: "{b} : {c} ({d}%)"                    	        
			},

			
		        legend: {  
                    orient : 'vertical',  
                    x : 'left',  
                    data:data.name  
                },
                calculable : true, 
		        series: [{
		            type: 'pie',
		            radius: ['50%', '80%'],// '70%',
		            center: ['50%', '50%'],
		            data:data,
		            label: {
		                normal: {
		                    show: true
		                   // formatter: '{b}: {c}({d}%)'
		                },
		              //  labelLine :{show:true}
		            }

		           
		        }]
	 };

	 
		    getChart("chart1").setOption(option);
		   /* $('#ccc').change(function(){
		       var pifu =  $("#ccc").find("option:selected").val();
		       if(pifu == 'macarons'){
		    	    myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart1'),'macarons');
			        myChart.setOption(option);
		       }else{
		    	    myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart1'),'shine');
			        myChart.setOption(option);
		       }
		       myChart.on('click', function (params) {
					 var type =  $("#bbb").find("option:selected").val();
					 var cityid = document.getElementById("cityid").value;
			    	 var name = params.name; 
			    	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
			    	 var tjtype = document.getElementById("tjtype").value;
					 if(type=='zt'){
					    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=1&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
					 }
			    });
		    });*/
		    
		    var i = 0;
		    var img = 1;
		    $('#ccc').click(function(){
		    	if(i++%2 == 0){
		    		myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart1'),'macarons');
			        myChart.setOption(option);
		    	}else{
		    		 myChart.dispose();
				     myChart = echarts.init(document.getElementById('chart1'),'shine');
				     myChart.setOption(option);
		    	}
		    	 check(img,myChart);
		    });

		  //  var myChart = echarts.init(document.getElementById('chart1'));
		    check(img,myChart);
}

function showBar3(data){
	var data = data;
	 var myChart = echarts.init(document.getElementById('chart2'),'shine');
	/*  ||[{value:535, name: '����'},
      {value:510, name: '����'},
      {value:634, name: '����'},
      {value:735, name: '����'}];*/
	 var option = {
		   // color:["#ffa995","#fff797","#a6ffd2","#a3c1ff"],
			 tooltip : {                    	            
				 trigger: 'item',                    	            
				 formatter: "{b} : {c} ({d}%)"                    	        
			},

			
		        legend: {  
                    orient : 'vertical',  
                    x : 'left',  
                    data:data.name  
                },
                calculable : true, 
		        series: [{
		            type: 'pie',
		            radius: ['50%', '80%'],// '70%',
		            center: ['50%', '50%'],
		            data:data,
		            label: {
		                normal: {
		                    show: true
		                   // formatter: '{b}: {c}({d}%)'
		                },
		              //  labelLine :{show:true}
		            }

		           
		        }]
	 };

	 
		    getChart("chart2").setOption(option);
		   /* $('#ccc').click(function(){
		    	   var pifu =  $("#ccc").find("option:selected").val();
			       if(pifu == 'macarons'){
			    	   myChart.dispose();
				        myChart = echarts.init(document.getElementById('chart2'),'macarons');
				        myChart.setOption(option);
			       }else{
			    	    myChart.dispose();
				        myChart = echarts.init(document.getElementById('chart2'),'shine');
				        myChart.setOption(option);
			       }
		      
		        myChart.on('click', function (params) {
			    	 var type =  $("#bbb").find("option:selected").val();
					 var cityid = document.getElementById("cityid").value;
			    	 var name = params.name; 
			    	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
			    	 var tjtype = document.getElementById("tjtype").value;
					 if(type == 'zt'){
					    $h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=1.1&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
					 }
			    });
		    });
		   */
		    var i = 0;
		    var img = 1.1;
		    $('#ccc').click(function(){
		    	if(i++%2 == 0){
		    		myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart2'),'macarons');
			        myChart.setOption(option);
		    	}else{
		    		 myChart.dispose();
				     myChart = echarts.init(document.getElementById('chart2'),'shine');
				     myChart.setOption(option);
		    	}
		    	 check(img,myChart);
		    });
		    check(img,myChart);
}

function showBar(data){
	/*console.log(data);
	*/
    var myChart = echarts.init(document.getElementById('chart1-2'),'shine');

    var data = data;/*|| [
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}]
    ];*///[[20-30��],[30-40��],[40-50��],[50-60��],[60������]]
    var legendArray = ['30�꼰����', '30-40��','40-50��','50-60��','60������'];
    var itemArray = [],
        seriesData = [];
   
    for(var i=0;i<data.length;i++){
        if(data[i]){
            var seriesArray = [];
            var seriesModul = {
                name: '',
                type: 'bar',
                stack: '����',
                label: {
                    normal: {
                        show: false
                    }
                },
                barWidth:"40%",
                data: []
            };
            for(var x=0;x<data[i].length;x++){
                if(data[i][x]){
                    itemArray.indexOf(data[i][x].name)===-1?itemArray.push(data[i][x].name):"";
                    seriesArray.push([data[i][x].name,data[i][x].val,data[i][x].value]);
                }
            }
            seriesModul.name = legendArray[i] || "����";
            seriesModul.data = seriesArray;
            seriesData.push(seriesModul);
        }

    }
    var option = {
       // color : ["#ffa995","#fff797","#a6ffd2","#a3c1ff","#ffd898"],
        tooltip : {
            trigger: 'axis',
            axisPointer : {
                type : 'shadow'
            },
            formatter:function(parmas){
                var str = parmas[0].axisValue + "<br>";
                for(var i=0;i<parmas.length;i++){
                    if(parmas[i]){
                        var val = parmas[i];
                        str += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ val.color +';"></span>' + val.seriesName + '��'+val.data[2]+'��,    ռ ' + val.data[1] + '%<br>'
                    }
                }
                return str;
            }
        },
        legend: {
            data: legendArray,
            right:"4%",
            top:"13%",
            width:"100%",
            icon:"circle"
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top:"25%",
            containLabel: true
        },
        yAxis:  {
            type: 'value',
            axisLabel:{
                interval:0,
                formatter: '{value}%'},
            axisLine:{show:false},
            axisTick:{show:false},
        },
        xAxis: {
            type: 'category',
            axisTick:{show:true},
            data: itemArray,
            axisLabel :{
                interval:0,
                formatter:function(params){
                	var newParamsName = "";// ����ƴ�ӳɵ��ַ���
                    var paramsNameNumber = params.length;// ʵ�ʱ�ǩ�ĸ���
                    var provideNumber = 2;// ÿ������ʾ���ֵĸ���
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// ���еĻ�����Ҫ��ʾ���У�����ȡ��
                    /**
                     * �жϱ�ǩ�ĸ����Ƿ���ڹ涨�ĸ����� ������ڣ�����л��д��� ��������ڣ������ڻ�С�ڣ��ͷ���ԭ��ǩ
                     */
                    // ������ͬ��rowNumber>1
                    if (paramsNameNumber > provideNumber) {
                        /** ѭ��ÿһ��,p��ʾ�� */
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";// ��ʾÿһ�ν�ȡ���ַ���
                            var start = p * provideNumber;// ��ʼ��ȡ��λ��
                            var end = start + provideNumber;// ������ȡ��λ��
                            // �˴����⴦�����һ�е�����ֵ
                            if (p == rowNumber - 1) {
                                // ���һ�β�����
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                // ÿһ��ƴ���ַ���������
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;// ����ƴ�ɵ��ַ���
                        }

                    } else {
                        // ���ɱ�ǩ��ֵ�����±�ǩ
                        newParamsName = params;
                    }
                    //�����յ��ַ�������
                    return newParamsName
                }

               
                /*rotate:-2 ������б*/
            }
            


        },
        series: seriesData
    };
    getChart("chart1-2").setOption(option);
    /*$('#ccc').change(function(){
    	var pifu =  $("#ccc").find("option:selected").val();
	       if(pifu == 'macarons'){
	    	   myChart.dispose();
	           myChart = echarts.init(document.getElementById('chart1-2'),'macarons');
	           myChart.setOption(option);
	       }else{
	    	    myChart.dispose();
		        myChart = echarts.init(document.getElementById('chart1-2'),'shine');
		        myChart.setOption(option);
	       }
	       myChart.on('click', function (params) {
	      	 var type =  $("#bbb").find("option:selected").val();
	  		 var cityid = document.getElementById("cityid").value;
	      	 var name = params.value[0]; 
	      	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
	      	 var tjtype = document.getElementById("tjtype").value;
	  		 if(type == 'zt'){
	  		//window.open(ctpath+'/CheckTBAction.do?method=init&img=2&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype, "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes");
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=2&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500,'','shadow:false');
	  		 }
	      });
    });*/
    var i = 0;
    var img = 2;
    $('#ccc').click(function(){
    	if(i++%2 == 0){
    		myChart.dispose();
	        myChart = echarts.init(document.getElementById('chart1-2'),'macarons');
	        myChart.setOption(option);
    	}else{
    		 myChart.dispose();
		     myChart = echarts.init(document.getElementById('chart1-2'),'shine');
		     myChart.setOption(option);
    	}
    	 check(img,myChart);
    });
    check(img,myChart);
    
}
function showLine11(data) {//����ְ��������
    var myChart = echarts.init(document.getElementById('chart2-1'),'shine');

    var xAxisdata=[];
    var seriesdata=[];
    for(var x=0;x<data.length;x++){
        if(data[x]){
        	xAxisdata.push(data[x].name);
        	seriesdata.push(data[x].value);
        }
    }
    var option = {
    		
    	    xAxis: {
    	        type: 'category',
    	        data: xAxisdata,//['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    	        axisLabel:{
		                ��������interval:0
		                    
		                ����}
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel:{
                    interval:0,
                    formatter: '{value}%'},
                axisLine:{show:false},
                axisTick:{show:false},
    	    },
    	    tooltip: {
    	        trigger: 'item',
    	        show:true,formatter : '{a}{b}ռ�� : {c}%'
    	    },
    	    series: [{
    	    	name: '��ְ����',
    	        data: seriesdata,//[120, 200, 150, 80, 70, 110, 130],
    	        type: 'bar',
    	        barWidth : 30,//��ͼ���
    	         itemStyle: {
    	                normal: {
    	                    /*color: function(params) {
    	                        // build a color map as your need.
    	                        var colorList = [
    	                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
    	                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
    	                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
    	                        ];
    	                        return colorList[params.dataIndex]
    	                    },
    	                    label: {
    	                        show: true,
    	                        position: 'top',
    	                        formatter: '{b}\n{c}'
    	                    }*/
    	                }
    	            },
    	    }]
    	};

    getChart("chart2-1").setOption(option);
/*    $('#ccc').change(function(){
    	var pifu =  $("#ccc").find("option:selected").val();
	       if(pifu == 'macarons'){
	    	   myChart.dispose();
	           myChart = echarts.init(document.getElementById('chart2-1'),'macarons');
	           myChart.setOption(option);
	       }else{
	    	    myChart.dispose();
		        myChart = echarts.init(document.getElementById('chart2-1'),'shine');
		        myChart.setOption(option);
	       }
	       myChart.on('click', function (params) {
	      	 var type =  $("#bbb").find("option:selected").val();
	  		 var cityid = document.getElementById("cityid").value;
	      	var name = params.name; 
	      	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
	      	var tjtype = document.getElementById("tjtype").value;
	  		 if(type == 'zt'){
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=3&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
	  		 }
	      });
      
    });*/
    var i = 0;
    var img = 3;
    $('#ccc').click(function(){
    	if(i++%2 == 0){
    		myChart.dispose();
	        myChart = echarts.init(document.getElementById('chart2-1'),'macarons');
	        myChart.setOption(option);
    	}else{
    		 myChart.dispose();
		     myChart = echarts.init(document.getElementById('chart2-1'),'shine');
		     myChart.setOption(option);
    	}
    	 check(img,myChart);
    });
    check(img,myChart);
}

function showLine12(data) {//���깫��Ա�����仯���(����)
	 var myChart = echarts.init(document.getElementById('chart2-1'),'shine');

    var data = data; /*|| [
        {name:"2008��",value:40},
        {name:"2009��",value:30},
        {name:"2010��",value:20},
        {name:"2011��",value:50},
        {name:"2012��",value:70},
        {name:"2013��",value:60},
        {name:"2014��",value:20},
        {name:"2015��",value:40},
        {name:"2016��",value:60},
        {name:"2017��",value:20},
        {name:"2018��",value:80}
    ];*/
    var option = {
       // color:["#fd7779"],
        tooltip:{show:true,formatter : '{b}{a} : {c}����'},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top:"28%",
            containLabel: true
        },
        yAxis:  {
            type: 'value',
            nameLocation:"end",
            axisLine:{show:false},
            axisTick:{show:false},
        },
        xAxis: {
            type: 'category',
            axisTick:{show:false},
            data: getArrValue(data,"name"),
            axisLabel:{
            ��������interval:0,
                
            ��������}
        },
        series: [{
            name: '����Ա����',
            type: 'line',
            symbolSize:6,
            label: {
                normal: {
                    show: false
                }
            },
            data: getArrValue(data)
        }]
    };
    getChart("chart2-1").setOption(option);
    /*$('#ccc').change(function(){
    	var pifu =  $("#ccc").find("option:selected").val();
	       if(pifu == 'macarons'){
	    	   myChart.dispose();
	           myChart = echarts.init(document.getElementById('chart2-1'),'macarons');
	           myChart.setOption(option);
	       }else{
	    	    myChart.dispose();
		        myChart = echarts.init(document.getElementById('chart2-1'),'shine');
		        myChart.setOption(option);
	       }
       
    });*/
    var i = 0;
   
    $('#ccc').click(function(){
    	if(i++%2 == 0){
    		myChart.dispose();
	        myChart = echarts.init(document.getElementById('chart2-1'),'macarons');
	        myChart.setOption(option);
    	}else{
    		 myChart.dispose();
		     myChart = echarts.init(document.getElementById('chart2-1'),'shine');
		     myChart.setOption(option);
    	}
    });
    
}

function showLine2(data) {
	 var myChart = echarts.init(document.getElementById('chart2-2'),'shine');
    var data = data;/* || [
        {name:"20-25��",value:40},
        {name:"25-30��",value:30},
        {name:"30-35��",value:20},
        {name:"35-40��",value:50},
        {name:"40-45��",value:70},
        {name:"45-50��",value:60},
        {name:"50-55��",value:20},
        {name:"55-60��",value:40}
    ];*/
    var option = {
       // color:["#fec36a"],
        tooltip:{show:true,formatter : '{b}{a}ռ�� : {c}%'},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top:"25%",
            containLabel: true
        },
        yAxis:  {
            type: 'value',
            axisLabel:{
                interval:0,
                formatter: '{value}%'},
            axisLine:{show:false},
            axisTick:{show:false},
        },
        xAxis: {
            type: 'category',
            axisTick:{show:false},
            data: getArrValue(data,"name"),
            axisLabel:{
            ��������interval:0,
                formatter:function(params){
                	var newParamsName = "";// ����ƴ�ӳɵ��ַ���
                    var paramsNameNumber = params.length;// ʵ�ʱ�ǩ�ĸ���
                    var provideNumber = 5;// ÿ������ʾ���ֵĸ���
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// ���еĻ�����Ҫ��ʾ���У�����ȡ��
                    /**
                     * �жϱ�ǩ�ĸ����Ƿ���ڹ涨�ĸ����� ������ڣ�����л��д��� ��������ڣ������ڻ�С�ڣ��ͷ���ԭ��ǩ
                     */
                    // ������ͬ��rowNumber>1
                    if (paramsNameNumber > provideNumber) {
                        /** ѭ��ÿһ��,p��ʾ�� */
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";// ��ʾÿһ�ν�ȡ���ַ���
                            var start = p * provideNumber;// ��ʼ��ȡ��λ��
                            var end = start + provideNumber;// ������ȡ��λ��
                            // �˴����⴦�����һ�е�����ֵ
                            if (p == rowNumber - 1) {
                                // ���һ�β�����
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                // ÿһ��ƴ���ַ���������
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;// ����ƴ�ɵ��ַ���
                        }

                    } else {
                        // ���ɱ�ǩ��ֵ�����±�ǩ
                        newParamsName = params;
                    }
                    //�����յ��ַ�������
                    return newParamsName
                }
            ��������}
        },
        series: [{
            name: '����',
            type: 'line',
            symbolSize:8,
            symbol:"circle",
            label: {
                normal: {
                    show: false
                }
            },
            areaStyle:{
                normal:{
                    /*color:{
                        type: 'linear',x: 0, y: 0, x2: 0, y2: 1,
                        colorStops: [{
                            offset: 0, color: "rgba(254,195,106,0.8)" // 0% ������ɫ
                        }, {
                            offset: 1, color: "rgba(254,195,106,0)" // 100% ������ɫ
                        }],
                        globalCoord: false // ȱʡΪ false
                    },*/
                    shadowColor: 'rgba(0, 0, 0, 0)',
                    shadowBlur: 10,
                    shadowOffsetX: 10
                }
            },
            data: getArrValue(data)
        }]
    };
    getChart("chart2-2").setOption(option);
 /*   $('#ccc').change(function(){
    	 var pifu =  $("#ccc").find("option:selected").val();
	       if(pifu == 'macarons'){
			        myChart.dispose();
			        myChart = echarts.init(document.getElementById('chart2-2'),'macarons');
			        myChart.setOption(option);
	       }else{
	    	    myChart.dispose();
		        myChart = echarts.init(document.getElementById('chart2-2'),'shine');
		        myChart.setOption(option);
	       }
	       myChart.on('click', function (params) {
	      	 var type =  $("#bbb").find("option:selected").val();
	  		 var cityid = document.getElementById("cityid").value;
	      	 var name = params.name; 
	      	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
	      	 var tjtype = document.getElementById("tjtype").value;
	  		 if(type == 'zt'){
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=4&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
	  		 }
	      });
    });*/
    var i = 0;
    var img = 4;
    $('#ccc').click(function(){
    	if(i++%2 == 0){
    		myChart.dispose();
	        myChart = echarts.init(document.getElementById('chart2-2'),'macarons');
	        myChart.setOption(option);
    	}else{
    		 myChart.dispose();
		     myChart = echarts.init(document.getElementById('chart2-2'),'shine');
		     myChart.setOption(option);
    	}
    	 check(img,myChart);
    });
         
         check(img,myChart);
    
    
}

function check(img,myChart){
	myChart.on('dblclick', function (params) {
	//console.log(params.name);
   	 var type =  $("#bbb").find("option:selected").val();
		 var cityid = document.getElementById("cityid").value;
   	 var name = params.name; 
   	//var  url= '/QueryCheckTBAction.do?method=init&col1='+col1+'&row1='+row1+'&unid='+unid+'&tableid='+tableid;
   	 var tjtype = document.getElementById("tjtype").value;
		 if(type == 'zt'){
			 if(name==""){
				name = params.value[0]; 
			 }
		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img='+img+'&name='+ encodeURI(encodeURI(name))+'&cityid='+cityid+'&tjtype='+tjtype,'��ϸ��Ϣ',700,500);
		 }
   });
}
