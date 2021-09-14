/**
 * 生成普通地图
 * @param data
 * @param cityid
 */
function showMap(data,cityid){
    var data = data || [
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
    $.getJSON('json/' + cityid + '.json', function (json) {
        echarts.registerMap(cityid, json);
        var mapJsonData = convertMapData(data,json);
        option.series[0].data = mapJsonData;
        getChart("chart1-1").setOption(option);
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
        {id:"420000", value:132112},
        {id:"340000", value:32374},
        {id:"530000", value:34150},
        {id:"630000", value:45231},
        {id:"140000", value:45334},
        {id:"210000", value:79851},
        {id:"540000", value:75234},
        {id:"650000", value:23464},
        {id:"620000", value:132112},
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
    $.getJSON('json/' + cityid + '.json', function (json) {
        echarts.registerMap(cityid, json);
        var mapJsonData = convertMapData2(data,json);
        option.series[0].data = mapJsonData;
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

function showBar(data){
    var data = data || [
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}],
        [{name:"省部级正职",value:Math.random()*20},{name:"省部级副职",value:Math.random()*20},{name:"厅局级正职",value:Math.random()*20},{name:"厅局级副职",value:Math.random()*20},{name:"县处级正职",value:Math.random()*20},{name:"县处级副职",value:Math.random()*20},{name:"乡科级正职",value:Math.random()*20},{name:"乡科级副职",value:Math.random()*20},{name:"科员",value:Math.random()*20}]
    ];//[[20-30岁],[30-40岁],[40-50岁],[50-60岁],[60岁以上]]
    var legendArray = ['20-30岁', '30-40岁','40-50岁','50-60岁','60岁以上'];
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
                    seriesArray.push([data[i][x].name,data[i][x].value]);
                }
            }
            seriesModul.name = legendArray[i] || "其他";
            seriesModul.data = seriesArray;
            seriesData.push(seriesModul);
        }

    }
    var option = {
        color : ["#ffa995","#fff797","#a6ffd2","#a3c1ff","#ffd898"],
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
                        str += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ val.color +';"></span>' + val.seriesName + ': ' + val.data[1] + '%<br>'
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
            axisTick:{show:false},
            data: itemArray,
        },
        series: seriesData
    };
    getChart("chart1-2").setOption(option);
}

function showLine1(data) {
    var data = data || [
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
    ];
    var option = {
        color:["#fd7779"],
        tooltip:{show:true},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top:"28%",
            containLabel: true
        },
        yAxis:  {
            type: 'value',
            name:"(万人)",
            nameLocation:"end",
            axisLine:{show:false},
            axisTick:{show:false},
        },
        xAxis: {
            type: 'category',
            axisTick:{show:false},
            data: getArrValue(data,"name"),
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
}

function showLine2(data) {
    var data = data || [
        {name:"20-25岁",value:40},
        {name:"25-30岁",value:30},
        {name:"30-35岁",value:20},
        {name:"35-40岁",value:50},
        {name:"40-45岁",value:70},
        {name:"45-50岁",value:60},
        {name:"50-55岁",value:20},
        {name:"55-60岁",value:40}
    ];
    var option = {
        color:["#fec36a"],
        tooltip:{show:true},
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
        },
        series: [{
            name: '年龄情况',
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
}