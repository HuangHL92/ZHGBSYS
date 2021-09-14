function setNumY(data, data2) {


    var option = {
        textStyle: {
            fontSize: 14
        },
        legend: {
	        data: ['调配前平均年龄：'+((data2['ageCount']/data2['totalCount']).toFixed(2)), '调配后平均年龄：'+((data['ageCount']/data['totalCount']).toFixed(2))]
	    },
        grid: {
            backgroundColor: '#ffffff',
            borderWidth: 0,
            x: 50,
            y: 50,
            x2: 20,
            y2: 30
        },
        xAxis: [{
            type: 'category',
            name: '',
            boundaryGap: true,
            data: ['40岁以下', '41-45岁', '46-50岁', '51-55岁', '56-59岁', '60岁以上'],
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#ECECEC',
                    width: 1
                }
            },
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#ddd',
                    // 左边线的颜色
                    width: '2' // 坐标线的宽度
                }
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#8D8D8D' // 坐标值得具体的颜色

                },
                interval: 0,
                fontSize: '14'
            }
        }, {
            type: 'category',
            name: '',
            boundaryGap: true,
            //data : [ '40岁以下', '41-45岁', '46-50岁', '51-55岁', '56-59岁', '60岁以上' ],
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#5793f3',
                    width: 1
                }
            },
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#ddd',
                    // 左边线的颜色
                    width: '2' // 坐标线的宽度
                }
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#d14a61' // 坐标值得具体的颜色

                },
                interval: 0,
                fontSize: '14'
            }
        }],
        yAxis: {
            name: '',
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: '#8D8D8D' // 坐标值得具体的颜色
                },
                fontSize: '14'
            },
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#ddd',
                    // 左边线的颜色
                    width: '2' // 坐标线的宽度
                }
            },
            axisTick: {
                show: false
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#ECECEC',
                    width: 1
                }
            }

        },
        series: [{
            type: 'line',
            name:'调配后平均年龄：'+((data['ageCount']/data['totalCount']).toFixed(2)),
            data: [data['ageLT40'] || 0, data['ageGT41LT45'] || 0, data['ageGT46LT50'] || 0, data['ageGT51LT55'] || 0, data['ageGT56LT59'] || 0, data['ageGT60'] || 0],
            itemStyle: {
                normal: {
                    color: '#5EB3F3'
                }
            },

            symbolSize: 12,
            label: {
                normal: {
                    show: true,
                    // 圆点上显示值
                    color: '#999999',
                    position: 'right'
                }
            }
        }, {
            type: 'line',
            name:'调配前平均年龄：'+((data2['ageCount']/data2['totalCount']).toFixed(2)),
            data: [data2['ageLT40'] || 0, data2['ageGT41LT45'] || 0, data2['ageGT46LT50'] || 0, data2['ageGT51LT55'] || 0, data2['ageGT56LT59'] || 0, data2['ageGT60'] || 0],
            itemStyle: {
                normal: {
                    color: '#d14a61'
                }
            },

            symbolSize: 12,
            label: {
                normal: {
                    show: true,
                    // 圆点上显示值
                    color: '#999999',
                    position: 'left'
                }
            }
        }]
    };
    getChart("chart1-2").setOption(option);
}


function setchart2(data,data2) {
    var option = {
        textStyle: {
            fontSize: 14
        },
        title: [{
            subtext: '调配前',
            left: '24%',
            top: '85%',
            textAlign: 'center',
            subtextStyle:{
            	color:'#5EB3F3'
            }
            
        }, {
            subtext: '调配后',
            left: '75%',
            top: '85%',
            textAlign: 'center',
            subtextStyle:{
            	color:'#5EB3F3'
            }
        }],
        series: [{
            name: '学历结构',
            type: 'pie',
            minAngle: 5,
            　　 // 最小的扇区角度（0 ~
            // 360），用于防止某个值过小导致扇区太小影响交互
            avoidLabelOverlap: true,
            radius: '40%',
            center: ['25%', '60%'],
            // color: ['#a2d13d', '#f39800', '#fff100', '#15d9db',
            // '#00a0e9'],

            legend: {
                type: 'scroll',
                orient: 'vertical',
                right: 10,
                top: 20,
                // data: ['a','a'],
                bottom: 20
            },
            label: {
                normal: {
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: 14
                    },
                    formatter: '{b|{b}:  }{c}\n{per|{d}%}',
                    rich: {
                        a: {
                            color: '#999',
                            lineHeight: 15,
                            align: 'left'
                        },
                        hr: {
                            borderColor: '#aaa',
                            width: '100%',
                            borderWidth: 0.5,
                            height: 0
                        },
                        b: {
                            fontSize: 14,
                            lineHeight: 15
                        },
                        per: {
                            color: '#eee',
                            backgroundColor: '#334455',
                            padding: [2, 5, 2, 5],
                            fontSize: 14,
                            borderRadius: 2
                        }
                    }
                }
            },
            data: [{
                name: '研究生及以\r\n上',
                value: data2['xlxwLev1'] || 0
            }, {
                name: '大学',
                value: data2['xlxwLev3'] || 0
            }, {
                name: '大专及以下',
                value: data2['xlxwLev4'] || 0
            }],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },{

            name: '学历结构',
            type: 'pie',
            minAngle: 5,
            　　 // 最小的扇区角度（0 ~
            // 360），用于防止某个值过小导致扇区太小影响交互
            avoidLabelOverlap: true,
            radius: '40%',
            center: ['75%', '60%'],
            // color: ['#a2d13d', '#f39800', '#fff100', '#15d9db',
            // '#00a0e9'],
            
            legend: {
                type: 'scroll',
                orient: 'vertical',
                right: 10,
                top: 20,
                // data: ['a','a'],
                bottom: 20
            },
            label: {
                normal: {
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: 14
                    },
                    formatter: '{b|{b}:  }{c}\n{per|{d}%}',
                    rich: {
                        a: {
                            color: '#999',
                            lineHeight: 15,
                            align: 'left'
                        },
                        hr: {
                            borderColor: '#aaa',
                            width: '100%',
                            borderWidth: 0.5,
                            height: 0
                        },
                        b: {
                            fontSize: 14,
                            lineHeight: 15
                        },
                        per: {
                            color: '#eee',
                            backgroundColor: '#334455',
                            padding: [2, 5, 2, 5],
                            fontSize: 14,
                            borderRadius: 2
                        }
                    }
                }
            },
            data: [{
                name: '研究生及以\r\n上',
                value: data['xlxwLev1'] || 0
            }, {
                name: '大学',
                value: data['xlxwLev3'] || 0
            }, {
                name: '大专及以下',
                value: data['xlxwLev4'] || 0
            }],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        
        }]
    };
    getChart("chart2-2").setOption(option);
}

function setchart3(data,data2) {
    var dataStyle = {
        normal: {
            label: {
                show: false
            },
            labelLine: {
                show: false
            },
            shadowBlur: 40,
            shadowColor: 'rgba(40, 40, 40, 0.5)'
        }
    };

    var placeHolderStyle = {
        normal: {
            color: '#3396EC',
            // 未完成的圆环的颜色
            label: {
                show: false
            },
            labelLine: {
                show: false
            }
        }
    };

    var placeHolderStyle2 = {
        normal: {
            color: '#DA0C78',
            // 未完成的圆环的颜色
            label: {
                show: false
            },
            labelLine: {
                show: false
            }
        }
    };
    var option = {
        
        tooltip: {
            show: true
        },
        toolbox: {
            show: true
        },
        series: [{
            name: '结构化干部',
            type: 'pie',
            clockWise: false,
            radius: [65, 40],
            itemStyle: dataStyle,
            hoverAnimation: false,
            center: ['20%', '30%'],
            data: [{
                value: data2['totalCount'] - data2['noZGParty'],
                name: '中共党员',
                label: {
                    normal: {
                        formatter: function(params) {
                            return Math.round((data2['noZGParty'] || 0) / (data2['totalCount']) * 100) + '%'
                        },
                        position: 'center',
                        show: true,
                        textStyle: {
                            fontSize: '14',
                            fontWeight: 'normal',
                            color: '#666666'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#CAD2DB',
                        shadowColor: '#CAD2DB',
                        shadowBlur: 10
                    }
                }
            }, {
                value: data2['noZGParty'],
                name: '党外干部',
                itemStyle: placeHolderStyle
            }]
        }, {
            name: '结构化干部',
            type: 'pie',
            clockWise: true,
            radius: [65, 40],
            itemStyle: dataStyle,
            hoverAnimation: false,
            center: ['65%', '30%'],
            data: [{
                value: data2['totalCount'] - data2['female'],
                name: '男',
                label: {
                    normal: {
                        formatter: function(params) {
                            return Math.round((data2['female'] || 0) / (data2['totalCount']) * 100) + '%'
                        },
                        position: 'center',
                        show: true,
                        textStyle: {
                            fontSize: '14',
                            fontWeight: 'normal',
                            color: '#666666'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#CAD2DB',
                        shadowColor: '#CAD2DB',
                        shadowBlur: 10
                    }
                }
            }, {
                value: data2['female'],
                name: '女',
                itemStyle: placeHolderStyle2
            }]
        },{
            name: '结构化干部',
            type: 'pie',
            clockWise: false,
            radius: [65, 40],
            itemStyle: dataStyle,
            hoverAnimation: false,
            center: ['20%', '76%'],
            data: [{
                value: data['totalCount'] - data['noZGParty'],
                name: '中共党员',
                label: {
                    normal: {
                        formatter: function(params) {
                            return Math.round((data['noZGParty'] || 0) / (data['totalCount']) * 100) + '%'
                        },
                        position: 'center',
                        show: true,
                        textStyle: {
                            fontSize: '14',
                            fontWeight: 'normal',
                            color: '#666666'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#CAD2DB',
                        shadowColor: '#CAD2DB',
                        shadowBlur: 10
                    }
                }
            }, {
                value: data['noZGParty'],
                name: '党外干部',
                itemStyle: placeHolderStyle
            }]
        }, {
            name: '结构化干部',
            type: 'pie',
            clockWise: true,
            radius: [65, 40],
            itemStyle: dataStyle,
            hoverAnimation: false,
            center: ['65%', '76%'],
            data: [{
                value: data['totalCount'] - data['female'],
                name: '男',
                label: {
                    normal: {
                        formatter: function(params) {
                            return Math.round((data['female'] || 0) / (data['totalCount']) * 100) + '%'
                        },
                        position: 'center',
                        show: true,
                        textStyle: {
                            fontSize: '14',
                            fontWeight: 'normal',
                            color: '#666666'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#CAD2DB',
                        shadowColor: '#CAD2DB',
                        shadowBlur: 10
                    }
                }
            }, {
                value: data['female'],
                name: '女',
                itemStyle: placeHolderStyle2
            }]
        }]
    }

    getChart("chart3-2").setOption(option);
}






function setchart4(data,data2) {

    var option = {
		legend: {
	        data: ['调配前', '调配后']
	    },
        color: ['#d14a61','#5EB3F3'],
        xAxis: [{
            type: 'category',
            data: ['党务类', '综合管理类', '制造业和工业经济类', '大数据和信息技术类', '城建城管类', '教育卫生类', '服务商贸类', '农业农村类', '文化发展和旅游类', '公检法政法类', '企业经营管理类', '金融财务类'],
            axisTick: {
                show: false //隐藏X轴刻度
            },
            axisLabel: {
                color: 'rgb(110,110,110)',
                rotate: -30,
                textStyle: {
                    fontSize: 10
                }
            },
            axisLine: {
                show: false //隐藏X轴轴线
            }
        }],
        yAxis: [{
            type: 'value',
            axisLabel: {
                color: 'rgb(110,110,110)',
                textStyle: {
                    fontSize: 14
                }
            },
            axisLine: {
                show: false //隐藏X轴轴线
            },
            axisTick: {
                show: false //隐藏X轴刻度
            }
        }],
        series: [{
        	name:'调配前',
            type: 'bar',
            barGap: 0,
            barWidth: '45%',
            itemStyle: {
                normal: {
                    barBorderRadius: [10, 10, 0, 0],
/*color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            { offset: 1, color: '#FEC767' },
	                            { offset: 0.5, color: '#FAA80B' },
	                            { offset: 0, color: '#FAA80B' }
	                        ]
	                    ),*/
                    "label": {
                        "show": true,
                        "position": "top",
                        formatter: function(p) {
                            return p.value > 0 ? (p.value) : '';
                        },
                        textStyle: {
                            color: '#d14a61'
                        }
                    }
                }
            },
            data: data2['ZYLXList']
        },{
        	name:'调配后',
            type: 'bar',
            barWidth: '45%',
            itemStyle: {
                normal: {
                    barBorderRadius: [10, 10, 0, 0],
/*color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            { offset: 1, color: '#FEC767' },
	                            { offset: 0.5, color: '#FAA80B' },
	                            { offset: 0, color: '#FAA80B' }
	                        ]
	                    ),*/
                    "label": {
                        "show": true,
                        "position": "top",
                        formatter: function(p) {
                            return p.value > 0 ? (p.value) : '';
                        },
                        textStyle: {
                            color: '#5EB3F3'
                        }
                    }
                }
            },
            data: data['ZYLXList']
        }]
    };
    getChart("chart4-2").setOption(option);
}




function getChart(eleID) {
    if (!echarts.getInstanceByDom(document.getElementById(eleID))) {
        return echarts.init(document.getElementById(eleID));
    } else {
        return echarts.getInstanceByDom(document.getElementById(eleID));
    }
}