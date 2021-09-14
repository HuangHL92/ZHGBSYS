/**
 * ������ͨ��ͼ
 * @param data
 * @param cityid
 */
function showMap(data,cityid){
    var data = data || [
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
    $.getJSON('json/' + cityid + '.json', function (json) {
        echarts.registerMap(cityid, json);
        var mapJsonData = convertMapData(data,json);
        option.series[0].data = mapJsonData;
        getChart("chart1-1").setOption(option);
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
                console.log("�ϼ�����δָ����");
                return;
            }
            switchMap(preMapData);
        });
    });
}

function showBar(data){
    var data = data || [
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}],
        [{name:"ʡ������ְ",value:Math.random()*20},{name:"ʡ������ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"���ּ���ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"�ش�����ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ƽ���ְ",value:Math.random()*20},{name:"��Ա",value:Math.random()*20}]
    ];//[[20-30��],[30-40��],[40-50��],[50-60��],[60������]]
    var legendArray = ['20-30��', '30-40��','40-50��','50-60��','60������'];
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
                    seriesArray.push([data[i][x].name,data[i][x].value]);
                }
            }
            seriesModul.name = legendArray[i] || "����";
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
            name:"(����)",
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
}

function showLine2(data) {
    var data = data || [
        {name:"20-25��",value:40},
        {name:"25-30��",value:30},
        {name:"30-35��",value:20},
        {name:"35-40��",value:50},
        {name:"40-45��",value:70},
        {name:"45-50��",value:60},
        {name:"50-55��",value:20},
        {name:"55-60��",value:40}
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
            name: '�������',
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
}