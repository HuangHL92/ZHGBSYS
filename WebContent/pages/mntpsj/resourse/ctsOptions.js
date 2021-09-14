var OPTIONS = (function(){
	return {
		option1 : function(data,dataArray,name){
			//{"a4145":"111","a4650":"246","a35":"2","a5155":"470","a3540":"32","a56":"492"}
			if(!dataArray){
				dataArray = 
					[//color: ['#50a3ba', '#eac736', '#fff','#000']
		                {value: data['a35']==0?null:data['a35'], name: '35岁及以下',id:'a35',itemStyle:{color: '#104756'}},
		                {value: data['a3540']==0?null:data['a3540'], name: '36-40岁',id:'a3540',itemStyle:{color: '#1f819c'}},
		                {value: data['a4145']==0?null:data['a4145'], name: '41-45岁',id:'a4145',itemStyle:{color: '#28a3c5'}},
		                {value: data['a4650']==0?null:data['a4650'], name: '46-50岁',id:'a4650',itemStyle:{color: '#2eb6dc'}},
		                {value: data['a5155']==0?null:data['a5155'], name: '51-55岁',id:'a5155',itemStyle:{color: '#9bd4e4'}},
		                {value: data['a56']==0?null:data['a56'], name: '56岁及以上',id:'a56',itemStyle:{color: '#d3eff7'}}
		            ];
			}
			if(!name){
				name = '年龄结构';
			}
			
			return {
			    tooltip: {
			        trigger: 'item'
			    },
			    
			   /* legend: {
			        orient: 'vertical',
			        right: 30,
			        top: 10,
			        bottom: 20
			    },*/
			 
			    series: [
			        { 
			            name: name,
			            type: 'pie',
			            
			            radius: ['30%', '60%'],
			            avoidLabelOverlap: true,
			            itemStyle: {
			                borderRadius: 5,
			                borderColor: '#fff',
			                borderWidth: 1,
			               /* color: '#c23531',
			                shadowBlur: 200,
			                shadowColor: 'rgba(0, 0, 0, 0.5)'*/
			            },
			            label: {
			                formatter: '{b|{b}}\n{hr|}\n   {per|{c}人}   ',
			                backgroundColor: '#F6F8FC',
			                borderColor: '#8C8D8E',
			                borderWidth: 1,
			                borderRadius: 4,
			                alignTo: 'edge',
			                minMargin: 0,
			                edgeDistance: 20,
			                rich: {
			                    b: {
			                        color: '#6E7079',
			                        lineHeight: 22,
			                        align: 'center'
			                    },
			                    hr: {
			                        borderColor: '#8C8D8E',
			                        width: '100%',
			                        borderWidth: 1,
			                        height: 0
			                    },
			                   
			                    per: {
			                        color: '#fff',
			                        backgroundColor: '#4C5058',
			                        padding: [3, 4],
			                        align: 'center',
			                        borderRadius: 4
			                    }
			                }
			            },
			            /*emphasis: {
			                label: {
			                    show: true,
			                    fontSize: '12',
			                    fontWeight: 'bold',
			                    formatter: '{b}: {c}'
			                }
			            },*/
			            labelLine: {
			                length: 5,
			                length2: 0,
			                maxSurfaceAngle: 80
			            },
			            left:0,
			            data: dataArray
			        }
			    ]
			}
		},
		option3: function(data){
			dataArray = 
				[
	                {value: data['ml']==0?null:data['ml'], name: '男',id:'ml',itemStyle:{color: '#1f819c'}},
	                {value: data['fml']==0?null:data['fml'], name: '女',id:'fml',itemStyle:{color: '#2eb6dc'}}
	            ];
			return this.option1(null,dataArray,'性别分布');
		},
		option2: function(data,dataArray){
			
			
			
			return this.option1(null,dataArray,'学位分布');
		},
		option4: function(data){
			$('.zgdyrs').text(data['zgdy']+"人");
			$('.fzgdyrs').text(data['feizgdy']+"人");
			
			
			
			dataArray = 
				[
					{value:data['minge'],id:"minge"},
					{value:data['minmeng'],id:"minmeng"},
					{value:data['minjian'],id:"minjian"},
					{value:data['minjin'],id:"minjin"},
					{value:data['nonggongdang'],id:"nonggongdang"},
					{value:data['zhigongdang'],id:"zhigongdang"},
					{value:data['jiusanxueshe'],id:"jiusanxueshe"},
					{value:data['wudangpai'],id:"wudangpai"}
	            ];
			return {
				xAxis: {
			        type: 'category',
			        data: ['民革', '民盟','民建', '民进', '农工党', '致公党', '九三学社', '无党派'],
			        axisLabel: {
		                color: 'rgb(110,110,110)',
		                rotate: -30,
		                textStyle: {
		                    fontSize: 12
		                }
		            }
			    },
			    grid: {
		            backgroundColor: '#ffffff',
		            borderWidth: 0,
		            x: 40,
		            y: 20,
		            x2: 50,
		            y2: 60
		        },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			        data: dataArray,
			        type: 'bar',
			        label: {
			        	show: true ,
			        	position: 'top' ,
			        	formatter: '{c}人',
			        	color:'rgb(118,223,182)'
			        },
			        itemStyle: {
			        	borderRadius:10,
			        	color:'rgb(118,223,182)'
			        },
			        barWidth:14
			       
			    }]
			}
		},
		option6: function(data){
			
			
			
			dataArray = 
				[
					{value:data['jl0507'],id:"jl0507"},
					{value:data['jl0509'],id:"jl0509"},
					{value:data['jl0510'],id:"jl0510"},
					{value:data['jl0801'],id:"jl0801"},
					{value:data['jl0805'],id:"jl0805"},
					{value:data['jl0901'],id:"jl0901"},
					{value:data['jl0902'],id:"jl0902"},
					{value:data['jl0903'],id:"jl0903"}
	            ];
			return {
				xAxis: {
			        type: 'category',
			        data: ['市直单位\n正职经历', '市直单位\n双副经历','市直单位\n副书记经历', 
			        	'区县市\n党委正职', '区县市\n政府正职',
			        	'乡镇街道\n党工委书记', 
			        	'乡镇街道\n政府正职', '乡镇街道\n班子经历'],
			        axisLabel: {
		                color: 'rgb(110,110,110)',
		                rotate: -30,
		                textStyle: {
		                	
		                    fontSize: 10
		                }
		            }
			    },
			    grid: {
		            backgroundColor: '#ffffff',
		            borderWidth: 0,
		            x: 40,
		            y: 20,
		            x2: 50,
		            y2: 60
		        },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			        data: dataArray,
			        type: 'bar',
			        label: {
			        	show: true ,
			        	position: 'top' ,
			        	formatter: '{c}人',
			        	color:'rgb(118,223,182)'
			        },
			        itemStyle: {
			        	borderRadius:10,
			        	color:'rgb(118,223,182)'
			        },
			        barWidth:14
			       
			    }]
			}
		},
		option5: function(data){
			//[{"zwrs":"理学","rs":"67"},{"zwrs":"教育学","rs":"36"},{"zwrs":"工学","rs":"174"},{"zwrs":"法学","rs":"125"},{"zwrs":"军事学","rs":"36"},{"zwrs":"历史学","rs":"27"},{"zwrs":"经济学","rs":"66"},{"zwrs":"医学","rs":"12"},{"zwrs":"农学","rs":"43"},{"zwrs":"管理学","rs":"206"},{"zwrs":"哲学","rs":"25"},{"zwrs":"文学","rs":"55"}]
			//['哲学', '医学','管理学', '军事学', '经济学', '法学', '教育学', '历史学', '文学', '理学', '工学', '农学', '有海外留学经历']
			dataArray1 = [];
			dataArray2 = [];
			$.each(data, function (i,rowsData) {
				dataArray1.push(rowsData['zwrs']);
				dataArray2.push({value:rowsData['rs'],id:rowsData['zwrs']});
			});
			return {
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: dataArray1,
			        axisLabel: {
		                color: 'rgb(110,110,110)',
		                rotate: -30,
		                textStyle: {
		                    fontSize: 12
		                }
		            }
			    },
			    grid: {
		            backgroundColor: '#ffffff',
		            borderWidth: 0,
		            x: 40,
		            y: 20,
		            x2: 50,
		            y2: 60
		        },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			        data: dataArray2,
			        type: 'line',
			        label: {
			        	show: true ,
			        	position: 'top' ,
			        	color:'rgb(0,145,255)',
			        	offset: [5, 0],
			        	formatter: '{c}人'
			        },
			        itemStyle: {
			        	//borderRadius:10,
			        	color:'rgb(0,145,255)'
			        },
			        //barWidth:14,
			        areaStyle: {
			        	normal: {
			                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			                        offset: 0,
			                        color: 'rgb(0,145,255)'
			                    }, {
			                        offset: 1,
			                        color: 'rgb(243,250,255)'
			                    }])
			            }
			        }
			    }]
			};
		}
	
	
	}
})();





