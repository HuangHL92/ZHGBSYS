$(function() {
	$('.item-list').niceScroll({cursorcolor: '#ccc'});
	
	initPieCharts();
	initBarCharts();
	
	// 获取未更新人员信息
	getPendingToUpdatePersonInfo();
	
	// 获取档案未读信息
	getArchiveMsgNumber();
	
	// 获取在职和非在职的人数
	getCountPerson();
});

function initPieCharts() {
	var myChart0 = echarts.init(document.getElementById('chart0'));
	var myChart0Opt = getPieOptions('年龄');
	var myChart1 = echarts.init(document.getElementById('chart1'));
	var myChart1Opt = getPieOptions('现职年限');
	var myChart2 = echarts.init(document.getElementById('chart2'));
	var myChart2Opt = getPieOptions('最高学历');
	
	myChart0.showLoading({text : '统计中...'});
	myChart1.showLoading({text : '统计中...'});
	myChart2.showLoading({text : '统计中...'});
	
	$.ajax({
		type:'post',
		url:System.rootPath + '/analysis/person/home-person-analysis!getAnalysis.action',
		complete: function() {
		   myChart0.hideLoading();
		   myChart1.hideLoading();
		   myChart2.hideLoading();
		},
		success:function(data){
			myChart0Opt.series[0].data.push({value:data._35, name:'35岁及以下'});
			myChart0Opt.series[0].data.push({value:data._36_40, name:'36 - 40岁'});
			myChart0Opt.series[0].data.push({value:data._41_45, name:'41 - 45岁'});
			myChart0Opt.series[0].data.push({value:data._46_50, name:'46 - 50岁'});
			myChart0Opt.series[0].data.push({value:data._51_55, name:'51 - 55岁'});
			myChart0Opt.series[0].data.push({value:data._56, name:'55岁以上'});

			myChart1Opt.series[0].data.push({value:data.rznx_5, name:'5年及以下'});
			myChart1Opt.series[0].data.push({value:data.rznx_6_10, name:'6 - 10年'});
			myChart1Opt.series[0].data.push({value:data.rznx_11_15, name:'11 - 15年'});
			myChart1Opt.series[0].data.push({value:data.rznx_16, name:'15年以上'});

			myChart2Opt.series[0].data.push({value:data._by, name:'博士研究生'});
			myChart2Opt.series[0].data.push({value:data._sy, name:'硕士研究生'});
			myChart2Opt.series[0].data.push({value:data._db, name:'大学本科'});
			myChart2Opt.series[0].data.push({value:data._dz, name:'大学专科'});
			myChart2Opt.series[0].data.push({value:data._zzgz, name:'中专、高中及以下'});

			myChart0.setOption(myChart0Opt);
			myChart1.setOption(myChart1Opt);
			myChart2.setOption(myChart2Opt);
	   },
	});
}

function getPieOptions(title) {
	return {
		series : [
			{
				name: title,
				type: 'pie',
				radius: ['75%', '50%'],
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true,
						formatter: '{d}%, {c}人',
						position : 'center',
						textStyle : {
							fontSize : '12',
							fontWeight : 'bold'
						}
					}
				},
				data: []
			}
		]
	};
}

function initBarCharts() {
	var opt = {
		legend: {
			data:['人数'],
			show:false
		},
		tooltip : {
			trigger: 'axis'
		},
		xAxis : [
			{
				type : 'category',
				data : ['男','女', '汉族', '少数民族', '党员','非党员', '正厅','副厅', '正处', '副处', '其他职级']
			}
		],
		yAxis : [
			{
				type : 'value',
				name : '人',
				nameLocation: 'middle',
				nameGap: 60
			}
		],
		series : [
			{
				name:'人数',
				type:'bar',
				data: []
			}
		]
	};
	
	var myChart3 = echarts.init(document.getElementById('chart3'));
	myChart3.showLoading({text : '统计中...'});
	
	$.ajax({
	   type:'post',
	   url:System.rootPath + '/analysis/person/home-person-analysis!getAnalysisByZhuz.action',
	   complete: function() {
		   myChart3.hideLoading();
	   },
	   success:function(data){
		  opt.series[0].data.push(data._nan);
		  opt.series[0].data.push(data._nv);
		  opt.series[0].data.push(data._hz);
		  opt.series[0].data.push(data._sz);
		  opt.series[0].data.push(data._dy);
		  opt.series[0].data.push(data._fdy);
		  opt.series[0].data.push(data._zt);
		  opt.series[0].data.push(data._ft);
		  opt.series[0].data.push(data._zc);
		  opt.series[0].data.push(data._fc);
		  opt.series[0].data.push(data._qt);
		  
		  myChart3.setOption(opt);
	   }
	});
}

function getCountPerson() {
	var url = System.rootPath + '/analysis/person/home-person-analysis!getCountPerson.action';
	$.post(
	url,
	{'fzai': false}, 
	function(data) {
		if (data) {
			$("#personZai").html(data.total + '&nbsp;<span class="unit">位</span>');
		}
	});
	$.post(
	url,
	{'fzai': true}, 
	function(data) {
		if (data) {
			$("#personFzai").html(data.total + '&nbsp;<span class="unit">位</span>');
		}
	});
}

function getArchiveMsgNumber() {
	getArchiveMessageNumber(function(num) {
		$("#archiveMsgNum").html(num + '&nbsp;<span class="unit">条</span>');
		if (num > 0) {
			$("#archiveMsg").bind('click', function() {
				if (g_archiveNotiRequest == null || g_archiveNotiRequest.web == '') return;
				Archive.gotoRequestPage();
			});
		}
	});
}

function getPendingToUpdatePersonInfo() {
	var url = System.rootPath + '/analysis/person/pending-to-update-person-analysis!analysisByUser.action';
	$.post(url, function(data) {
		if (data) {
			$("#person15").html(data.results[0].total + '&nbsp;<span class="unit">位</span>');
			$("#person30").html(data.results[1].total + '&nbsp;<span class="unit">位</span>');
			$("#person365").html(data.results[2].total + '&nbsp;<span class="unit">位</span>');
			
			$("#person15Msg").bind('click', function() {
				showPendingToUpdatePersonList(data.results[0].queryType);
			});
			
			$("#person30Msg").bind('click', function() {
				showPendingToUpdatePersonList(data.results[1].queryType);
			});
			
			$("#person365Msg").bind('click', function() {
				showPendingToUpdatePersonList(data.results[2].queryType);
			});
		}
	});
}

function showPendingToUpdatePersonList(queryType) {
	var name = '你可维护干部，';
	var url = System.rootPath + '/analysis/person/pending-to-update-person-analysis!analysisPersons.action';
	url += '?queryType=' + queryType;
	url += '&userScope=1';
	url += '&name=' + name;
	
	var msg = '';
	if (queryType == 0) msg = name + '超过15天未更新人员';
	else if (queryType == 1) msg = name + '超过1个月未更新人员';
	else if (queryType == 2) msg = name + '超过1年未更新人员';
	
	parent.System.openURL(msg, url);
}

function onQuickQuery(id) {
	var url = System.rootPath + '/query/quick-query.action?itemId=' + id;
	parent.System.openURL('快速查询', url);
}