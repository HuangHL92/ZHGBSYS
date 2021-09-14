var OPTIONS = (function(){
	return {
		option1 : function(data,dataArray,name){
			//{"a4145":"111","a4650":"246","a35":"2","a5155":"470","a3540":"32","a56":"492"}
			if(!dataArray){
				dataArray = 
					[
		                {value: data['a35'], name: '35岁及以下',id:'a35'},
		                {value: data['a3540'], name: '36-40岁',id:'a3540'},
		                {value: data['a4145'], name: '41-45岁',id:'a4145'},
		                {value: data['a4650'], name: '46-50岁',id:'a4650'},
		                {value: data['a5155'], name: '51-55岁',id:'a5155'},
		                {value: data['a56'], name: '56岁及以上',id:'a56'}
		            ];
			}
			if(!name){
				name = '年龄结构';
			}
			return {
			    tooltip: {
			        trigger: 'item'
			    },
			    legend: {
			        orient: 'vertical',
			        right: 10,
			        top: 20,
			        bottom: 20
			    },
			    series: [
			        { 
			            name: name,
			            type: 'pie',
			            
			            radius: ['40%', '70%'],
			            avoidLabelOverlap: false,
			            itemStyle: {
			                borderRadius: 5,
			                borderColor: '#fff',
			                borderWidth: 1
			            },
			            label: {
			                show: false,
			                position: 'center'
			            },
			            emphasis: {
			                label: {
			                    show: true,
			                    fontSize: '12',
			                    fontWeight: 'bold',
			                    formatter: '{b}: {c}'
			                }
			            },
			            labelLine: {
			                show: false
			            },
			            left:-100,
			            data: dataArray
			        }
			    ]
			}
		},
		option3: function(data){
			dataArray = 
				[
	                {value: data['ml'], name: '男',id:'ml'},
	                {value: data['fml'], name: '女',id:'fml'}
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





