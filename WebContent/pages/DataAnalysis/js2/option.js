/**
 * 生成普通地图
 * @param data
 * @param cityid
 */
function showMap(data,cityid){
	 var myChart = echarts.init(document.getElementById('chart1-1'),'shine');
    var data = data;/* || [
        {name:"浙江省", value:102012},
        {name:"江苏省", value:132112},
        {name:"四川省", value:32374},
        {name:"甘肃省", value:34150},
        {name:"山西省", value:45231},
        {name:"青海省", value:45334},
        {name:"吉林省", value:79851},
        {name:"广东省", value:75234},
        {name:"海南省", value:23464},
        {name:"湖南省", value:132112},
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
            name:"人数",
            mapType: cityid,
            //zoom: 1.2,
           /* aspectScale:1,   */ //地图宽高比
            roam: true,     //是否开启鼠标缩放和地图拖动。默认不开启。如果只想要开启缩放或者拖动，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
          
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
       if(cityid=='460000') { //由于海南地图包括南海及南海诸岛在内的大片区域，所以显示海南地图时，要将地图放大，并设置海南岛居中显示
     	option.series[0].center = [109.844902, 19.0392];
     	option.series[0].layoutCenter = ['50%', '50%'];
     	option.series[0].layoutSize = "600%";
     	} else if(cityid=='100000') { //非显示海南时，将设置的参数恢复默认值
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
			    $h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=00&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
			 }
	    });*/
    });
}



/**
 * 生成钻取地图
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
            name:"公务员人数",
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
       /* if(cityid=='460000') { //由于海南地图包括南海及南海诸岛在内的大片区域，所以显示海南地图时，要将地图放大，并设置海南岛居中显示
         	option.series[0].center = [109.844902, 19.0392];
         	option.series[0].layoutCenter = ['50%', '50%'];
         	option.series[0].layoutSize = "600%";
         	} else { //非显示海南时，将设置的参数恢复默认值
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
                console.log("上级区域未指定。");
                return;
            }
            switchMap(preMapData);
        });
    });
}

function showBar2(data){
	var data = data;
	var myChart = echarts.init(document.getElementById('chart1'),'shine');
	/*  ||[{value:535, name: '荆州'},
      {value:510, name: '兖州'},
      {value:634, name: '益州'},
      {value:735, name: '西凉'}];*/
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
					    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=1&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
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
	/*  ||[{value:535, name: '荆州'},
      {value:510, name: '兖州'},
      {value:634, name: '益州'},
      {value:735, name: '西凉'}];*/
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
					    $h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=1.1&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
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
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}]
    ];*///[[20-30岁],[30-40岁],[40-50岁],[50-60岁],[60岁以上]]
    var legendArray = ['30岁及以下', '30-40岁','40-50岁','50-60岁','60岁以上'];
    var itemArray = [],
        seriesData = [];
   
    for(var i=0;i<data.length;i++){
        if(data[i]){
            var seriesArray = [];
            var seriesModul = {
                name: '',
                type: 'bar',
                stack: '总量',
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
            seriesModul.name = legendArray[i] || "其他";
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
                        str += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ val.color +';"></span>' + val.seriesName + '：'+val.data[2]+'人,    占 ' + val.data[1] + '%<br>'
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
                	var newParamsName = "";// 最终拼接成的字符串
                    var paramsNameNumber = params.length;// 实际标签的个数
                    var provideNumber = 2;// 每行能显示的字的个数
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
                    /**
                     * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                     */
                    // 条件等同于rowNumber>1
                    if (paramsNameNumber > provideNumber) {
                        /** 循环每一行,p表示行 */
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";// 表示每一次截取的字符串
                            var start = p * provideNumber;// 开始截取的位置
                            var end = start + provideNumber;// 结束截取的位置
                            // 此处特殊处理最后一行的索引值
                            if (p == rowNumber - 1) {
                                // 最后一次不换行
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                // 每一次拼接字符串并换行
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;// 最终拼成的字符串
                        }

                    } else {
                        // 将旧标签的值赋给新标签
                        newParamsName = params;
                    }
                    //将最终的字符串返回
                    return newParamsName
                }

               
                /*rotate:-2 文字倾斜*/
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
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=2&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500,'','shadow:false');
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
function showLine11(data) {//任现职务层次年限
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
		                　　　　interval:0
		                    
		                　　}
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
    	        show:true,formatter : '{a}{b}占比 : {c}%'
    	    },
    	    series: [{
    	    	name: '任职年限',
    	        data: seriesdata,//[120, 200, 150, 80, 70, 110, 130],
    	        type: 'bar',
    	        barWidth : 30,//柱图宽度
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
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=3&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
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

function showLine12(data) {//历年公务员人数变化情况(万人)
	 var myChart = echarts.init(document.getElementById('chart2-1'),'shine');

    var data = data; /*|| [
        {name:"2008年",value:40},
        {name:"2009年",value:30},
        {name:"2010年",value:20},
        {name:"2011年",value:50},
        {name:"2012年",value:70},
        {name:"2013年",value:60},
        {name:"2014年",value:20},
        {name:"2015年",value:40},
        {name:"2016年",value:60},
        {name:"2017年",value:20},
        {name:"2018年",value:80}
    ];*/
    var option = {
       // color:["#fd7779"],
        tooltip:{show:true,formatter : '{b}{a} : {c}万人'},
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
            　　　　interval:0,
                
            　　　　}
        },
        series: [{
            name: '公务员人数',
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
        {name:"20-25岁",value:40},
        {name:"25-30岁",value:30},
        {name:"30-35岁",value:20},
        {name:"35-40岁",value:50},
        {name:"40-45岁",value:70},
        {name:"45-50岁",value:60},
        {name:"50-55岁",value:20},
        {name:"55-60岁",value:40}
    ];*/
    var option = {
       // color:["#fec36a"],
        tooltip:{show:true,formatter : '{b}{a}占比 : {c}%'},
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
            　　　　interval:0,
                formatter:function(params){
                	var newParamsName = "";// 最终拼接成的字符串
                    var paramsNameNumber = params.length;// 实际标签的个数
                    var provideNumber = 5;// 每行能显示的字的个数
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
                    /**
                     * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                     */
                    // 条件等同于rowNumber>1
                    if (paramsNameNumber > provideNumber) {
                        /** 循环每一行,p表示行 */
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";// 表示每一次截取的字符串
                            var start = p * provideNumber;// 开始截取的位置
                            var end = start + provideNumber;// 结束截取的位置
                            // 此处特殊处理最后一行的索引值
                            if (p == rowNumber - 1) {
                                // 最后一次不换行
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                // 每一次拼接字符串并换行
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;// 最终拼成的字符串
                        }

                    } else {
                        // 将旧标签的值赋给新标签
                        newParamsName = params;
                    }
                    //将最终的字符串返回
                    return newParamsName
                }
            　　　　}
        },
        series: [{
            name: '年龄',
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
                            offset: 0, color: "rgba(254,195,106,0.8)" // 0% 处的颜色
                        }, {
                            offset: 1, color: "rgba(254,195,106,0)" // 100% 处的颜色
                        }],
                        globalCoord: false // 缺省为 false
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
	  		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img=4&name='+ encodeURI(name)+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
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
		    	$h.showWindowWithSrc_reloadable('win3',ctpath+'/CheckTBAction.do?method=init&img='+img+'&name='+ encodeURI(encodeURI(name))+'&cityid='+cityid+'&tjtype='+tjtype,'详细信息',700,500);
		 }
   });
}
