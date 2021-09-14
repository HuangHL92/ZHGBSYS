// 打开干部年度考核结果反查窗口
function openPersonNdkhjgViewWin(year, personcodes, personType) {
	var url = System.rootPath + '/ndkh/ndkh!personNdkhjg.action'; 
	url = addParamsForPersons(url, personcodes, year, personType);
	
	showDetailsWindow(url, '年度考核结果');
}

// 打开干部民主测评反查窗口
function openPersonMzcpViewWin(year, personcodes, personType) {
	var url = System.rootPath + '/ndkh/ndkh!personMzcp.action'; 
	url = addParamsForPersons(url, personcodes, year, personType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开干部省委全委会评价反查窗口
function openPersonSwqwhpjViewWin(year, personcodes, personType) {
	var url = System.rootPath + '/ndkh/ndkh!personSwqwhpj.action'; 
	url = addParamsForPersons(url, personcodes, year, personType);
	
	showDetailsWindow(url, '省委全委会评价');
}

// 打开省直干部作风情况专项测评反查窗口
function openProvincePersonZfqkfxcpViewWin(year, personcodes, personType) {
	var url = System.rootPath + '/ndkh/ndkh!provincePersonZfqkfxcp.action'; 
	url = addParamsForPersons(url, personcodes, year, personType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开市州干部作风情况专项测评反查窗口
function openCityPersonZfqkfxcpViewWin(year, personcodes, personType) {
	var url = System.rootPath + '/ndkh/ndkh!cityPersonZfqkfxcp.action'; 
	url = addParamsForPersons(url, personcodes, year, personType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开班子民意调查报告窗口
function openUnitMydcbgWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!unitMydcbg.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '民意调查报告');
}

// 打开省直班子市州对口部门评价总窗口
function openProvinceUnitSzdkbmpjWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSzdkbmpj.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '市州对口部门评价');
}

// 打开省直班子市州对口部门评价总窗口
function openProvinceUnitsSzdkbmpjWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSzdkbmpj.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '市州对口部门评价');
}


// 打开市州班子实绩考核汇总窗口
function openCityUnitSjcpWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSjcp.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '实绩考核');
}

// 打开市州班子实绩考核汇总窗口
function openCityUnitsSjcpWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSjcp.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '实绩考核');
}

// 打开省直班子民主测评汇总窗口
function openProvinceUnitMzcpWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitMzcp.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开省直班子民主测评汇总窗口
function openProvinceUnitsMzcpWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitMzcp.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开市州班子民主测评汇总窗口
function openCityUnitMzcpWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitMzcp.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开市州班子民主测评汇总窗口
function openCityUnitsMzcpWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitMzcp.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开省直班子作风情况专项测评汇总窗口
function openProvinceUnitZfqkfxcpWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitZfqkfxcp.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开省直班子作风情况专项测评汇总窗口
function openProvinceUnitsZfqkfxcpWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitZfqkfxcp.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}


// 打开市州班子作风情况专项测评汇总窗口
function openCityUnitZfqkfxcpWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!oneCityUnitZfqkfxcp.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开省直班子省委常委会评价汇总窗口
function openProvinceUnitSwcwhpjWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwcwhpj.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '省委常委会评价');
}

// 打开省直班子省委常委会评价汇总窗口
function openProvinceUnitsSwcwhpjWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwcwhpj.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '省委常委会评价');
}

// 打开市州班子省委常委会评价汇总窗口
function openCityUnitSwcwhpjWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSwcwhpj.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '省委常委会评价');
}

// 打开市州班子省委常委会评价汇总窗口
function openCityUnitsSwcwhpjWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSwcwhpj.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '省委常委会评价');
}

// 打开省直班子省委全委会评价汇总窗口
function openProvinceUnitSwqwhpjWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwqwhpj.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '省委全委会评价');
}

// 打开省直班子省委全委会评价汇总窗口
function openProvinceUnitsSwqwhpjWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwqwhpj.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '省委全委会评价');
}

// 打开市州班子省委全委会评价汇总窗口
function openCityUnitSwqwhpjWin(unitId, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSwqwhpj.action'; 
	url = addParamsForUnit(url, unitId, year, formType);
	
	showDetailsWindow(url, '省委全委会评价测评');
}

// 打开市州班子省委全委会评价汇总窗口
function openCityUnitsSwqwhpjWin(unitIds, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitSwqwhpj.action'; 
	url = addParamsForUnits(url, unitIds, year, formType);
	
	showDetailsWindow(url, '省委全委会评价测评');
}

// 打开省直干部正职省委全委会评价窗口
function openProvinceUnitPersonZzSwqwhpjWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzSwqwhpj.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '省委全委会评价');
}

// 打开省直干部正职民主测评窗口
function openProvinceUnitPersonZzMzcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzMzcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开省直干部民主测评窗口
function openProvinceUnitPersonMzcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonMzcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开市州党政班子成员民主测评窗口
function openCityUnitPersonDzbzcyMzcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonDzbzcyMzcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开市州政协人大其他民主测评窗口
function openCityUnitPersonZxrdqtMzcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonZxrdqtMzcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '民主测评');
}

// 打开省直干部正职作风情况专项测评窗口
function openProvinceUnitPersonZzZfqkfxcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzZfqkfxcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开省直干部作风情况专项测评窗口
function openProvinceUnitPersonZfqkfxcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZfqkfxcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开市州干部作风情况专项测评窗口
function openCityUnitPersonZfqkfxcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonZfqkfxcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '作风情况专项测评');
}

// 打开市州干部省委全委会评价窗口
function openCityUnitPersonSwqwhpjWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonSwqwhpj.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '省委全委会评价');
}

// 打开市州干部实绩测评窗口
function openCityUnitPersonSjcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonSjcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '个人分管工作实绩民主测评');
}

// 打开省直干部实绩测评窗口
function openProvinceUnitPersonSjcpWin(unitId, personcode, year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonSjcp.action'; 
	url = addPersonParams(url, unitId, personcode, year, formType);
	
	showDetailsWindow(url, '个人分管工作实绩民主测评');
}

function addParamsForUnit(url, unitId, year, formType) {
	url += '?selectedFormType=' + encodeURIComponent(formType);
	url += '&selectedYear=' + year;
	url += '&selectedUnitId=' + unitId;
	url += '&selectedOrder=';
	return url;
}

function addParamsForUnits(url, unitIds, year, formType) {
	url += '?selectedFormType=' + encodeURIComponent(formType);
	url += '&selectedYear=' + year;
	url += '&selectedUnitIds=' + unitIds;
	url += '&selectedOrder=';
	return url;
}

function addParamsForPersons(url, personcodes, year, personType) {
	url += '?selectedPersonType=' + personType;
	url += '&selectedYear=' + year;
	url += '&selectedPersoncodes=' + personcodes;
	url += '&selectedOrder=';
	return url;
}


function addPersonParams(url, unitId, personcode, year, formType) {
	url += '?selectedFormType=' + encodeURIComponent(formType);
	url += '&selectedYear=' + year;
	url += '&selectedUnitId=' + unitId;
	url += '&selectedPersoncode=' + personcode;
	url += '&selectedOrder=';
	return url;
}

function viewPersonNdkh(personName, unitId, personcode, year, formType, form) {
	var url;
	

	if (form == '1') {
		// 市州党政正职
		url = System.rootPath + '/ndkh/ndkh!cityUnitPersonZzNdkhjg.action';
	}
	else if (form == '2') {
		// 市州班子副职
		url = System.rootPath + '/ndkh/ndkh!cityUnitPersonBzfzNdkhjg.action'
	}
	else if (form == '3') {
		// 市州非党政班子正职
		url = System.rootPath + '/ndkh/ndkh!cityUnitPersonFdzbzzzNdkhjg.action'
	}
	else if (form == '4') {
		// 市州厅级干部
		url = System.rootPath + '/ndkh/ndkh!cityUnitPersonTjgbNdkhjg.action'
	}
	else if (form == '5') {
		// 省直党政正职
		url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzNdkhjg.action';
	}
	else if (form == '6') {
		// 省直班子副职
		url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonBzfzNdkhjg.action';
	}
	else if (form == '7') {
		// 省直厅级干部
		url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonTjgbNdkhjg.action';
	}
	else {
		$.messager.alert("提示","未知年度考核结果类型, form=" + form);	
		return;
	}
	
	url = url + "?selectedFormType="
			+ encodeURIComponent(formType) 
			+ "&selectedUnitId=" + unitId 
			+ "&selectedYear=" + year 
			+ "&selectedPersoncode=" + personcode;
	showDetailsWindow(url, personName + '同志' + year + '年年度考核结果');
}

function showDetailsWindow(url, title) {
	$("#detailsIframe").attr('src', url);
	$("#detailsWin").show();
	$("#detailsWin").window({
		modal: true,
		width: 1100,
		height: 450,
		title: title,
		collapsible: false,
		minimizable: false,
		maximizable: true,
		onClose: function() {
			$("#detailsIframe").attr('src', '');
		}
	});
	//$("#detailsWin").window('open');
}

function ndkhIsReady() {
	return true;
}

// ---------- 更新职务相关 --------------
function setZwFields(year, personType) {
	// 设置编辑字段
	$(".zw").each(function() {
		var ndkhjgId = $(this).attr('ndkhjgId');
		var personcode = $(this).attr('personcode');
		var parent = $(this).parent();
		
		if (parent != null) {
			parent.attr('title', '双击编辑职务');
			parent.dblclick(function() {
				showPersonZwDialog(ndkhjgId, year, personcode, personType);
			});
		}				
	});
}

function showPersonZwDialog(ndkhjgId, year, personcode, personType) {
	$("#zwDialog").show();				
	$("#zwDialog").dialog({
		shadow:true,
		modal :true,
		resizable:false,
		width: 480,
		height: 120,
		title: '编辑职务',
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#zwDialog').dialog('close');
				updatePersonZw(ndkhjgId, year, personcode, personType);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#zwDialog').dialog('close');
			}
		}],
		onOpen : function () {
			var html = $('#zw' + ndkhjgId).html(); 
			$('#zw' + ndkhjgId).parent().addClass('editingField'); // 当前编辑的单元格变色
			$('#zwField').focus();	
			$('#zwField').val(html);
		},
		onClose: function() {
			$("#zw" + ndkhjgId).parent().removeClass('editingField'); // 恢复原来的颜色				
		}
	});
}

function updatePersonZw(ndkhjgId, year, personcode, personType) {
	var url = System.rootPath + "/ndkh/ndkh!updatePersonZw.action";
	
	$.ajax({
		type: "POST",
		url: url,
		data: { 
			selectedNdkhjgId: ndkhjgId,
			personcode: personcode,
			selectedYear: year,
			selectedPersonType: personType,
			selectedZw: $("#zwField").val()
		},
		success:function(json){
			//alert(json);
			try {
				if (json.indexOf("成功") >= 0) {	
					$('#zw' + ndkhjgId).html(Utils.htmlEncode($("#zwField").val()));
				}
				else {
					$.messager.alert('提示', '保存失败');						
				}
			}
			catch (ex) {
				$.messager.alert('提示', ex);						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});	
}


// ---------- 考核等次相关 --------------
function setGradeFields(type) {
	if (!isValidGradeYear(g_year)) return;
	
	// 设置编辑字段
	$(".grade").each(function() {
		var pid = $(this).attr('pid');
		//alert(pid);
		var tipObject = null;
		
		var parent = $(this).parent();
		
		if (parent != null) {
			parent.attr('title', '双击编辑');
			parent.dblclick(function() {
				showGradeDialog(pid, type);
			});
		}				
	});
}

function showGradeDialog(pid, type) {
	$("#gradeDialog").show();				
	$("#gradeDialog").dialog({
		shadow:true,
		modal :true,
		resizable:false,
		width: 240,
		height: 120,
		title: '编辑考核等次',
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#gradeDialog').dialog('close');
				updateGrade(pid, type);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#gradeDialog').dialog('close');
			}
		}],
		onOpen : function () {
			var html = $('#grade' + pid).html(); 
			$('#grade' + pid).parent().addClass('editingField'); // 当前编辑的单元格变色
			$('#gradeField').focus();	
			$('#gradeField').val(html);
		},
		onClose: function() {
			$("#grade" + pid).parent().removeClass('editingField'); // 恢复原来的颜色				
		}
	});
}

function updateGrade(pid, type) {
	var url = '';
	if (type == 0) {
		url = System.rootPath + "/ndkh/ndkh!updateProvinceUnitNdkhjgGrade.action";
	}
	else if (type == 1) {	
		url = System.rootPath + "/ndkh/ndkh!updateCityUnitNdkhjgGrade.action";
	}
	else if (type == 2) {
		url = System.rootPath + "/ndkh/ndkh!updateProvinceUnitPersonZzNdkhjgGrade.action";
	}
	else {
		url = System.rootPath + "/ndkh/ndkh!updateCityUnitPersonZzNdkhjgGrade.action";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: { 
			personcode: pid,
			selectedUnitId: pid,
			selectedYear: g_year,
			selectedFormType: g_formType,
			selectedGrade: $("#gradeField").val()
		},
		success:function(json){
			try {
				if (json.indexOf("成功") >= 0) {	
					$('#grade' + pid).html(Utils.htmlEncode($("#gradeField").val()));
				}
				else {
					$.messager.alert('提示', '保存失败');						
				}
			}
			catch (ex) {
				$.messager.alert('提示', ex);						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});	
}

function autoSetUnitNdkhjgExcellentGrade(year, formType) {
	if (!isValidGradeYear(year)) return;
	
	$.messager.confirm('确认',  '确定自动设置' + year + '年度' + formType + '的领导班子优秀考核等次？', function(isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath + '/ndkh/ndkh!autoSetUnitNdkhjgExcellentGrade.action',
				data: { 
					selectedYear: year,
					selectedFormType: formType
				},
				success:function(json){
					window.location.reload();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "设置失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});
}

function autoSetPersonZzNdkhjgExcellentGrade(year, formType) {
	if (!isValidGradeYear(year)) return;
	
	$.messager.confirm('确认',  '确定自动设置' + year + '年度' + formType + '的党政正职优秀考核等次？', function(isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath + '/ndkh/ndkh!autoSetPersonZzNdkhjgExcellentGrade.action',
				data: { 
					selectedYear: year,
					selectedFormType: formType
				},
				success:function(json){
					window.location.reload();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "设置失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});
}

/*************** 考核排位 ***************/
function autoOrderUnitNdkhjg(year, formType) {
	$.messager.confirm('确认',  '确定对' + year + '年度' + formType + '的考核结果进行自动排位？', function(isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath + '/ndkh/ndkh!autoOrderUnitNdkhjg.action',
				data: { 
					selectedYear: year,
					selectedFormType: formType
				},
				success:function(json){
					window.location.reload();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "设置失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});			
}

function autoOrderPersonZzNdkhjg(year, formType) {
	$.messager.confirm('确认',  '确定对' + year + '年度' + formType + '的党政正职考核结果进行自动排位？', function(isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath + '/ndkh/ndkh!autoOrderPersonZzNdkhjg.action',
				data: { 
					selectedYear: year,
					selectedFormType: formType
				},
				success:function(json){
					window.location.reload();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "设置失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});			
}

/*************** 导出 ************ */
function exportUnitNdkhFeedback(year, formType) {
	var url = System.rootPath + '/ndkh/ndkh!exportUnitNdkhFeedback.action?selectedYear=' + year + '&selectedFormType=' + encodeURIComponent(formType);
	window.open(url);
}

function exportUnitNdkhScoreAndOrder(year, formType) {
	if (year <= 2013) {
		$.messager.alert('提示', "只支持2014年度及以后的考核数据！");
		return;
	}
	
	var url = System.rootPath + '/ndkh/ndkh!exportUnitNdkhScoreAndOrder.action?selectedYear=' + year + '&selectedFormType=' + encodeURIComponent(formType);
	window.open(url);
}

function exportPersonNdkhjgFzzReport(year, formType) {
	if (year <= 2013) {
		$.messager.alert('提示', "只支持2014年度及以后的考核数据！");
		return;
	}
	
	var url = System.rootPath + '/ndkh/ndkh!exportPersonNdkhjgFzzReport.action?selectedYear=' + year + '&selectedFormType=' + encodeURIComponent(formType);
	window.open(url);
}
			
/*************** 文件材料 **************/
// 查看干部文件资料
function viewPersonFiles(ndkhjgId, personcode, year, personType) {
	var url = System.rootPath + '/ndkh/ndkh!personFile.action'; 
	url = url + "?personcode=" + personcode;
	url = url + "&selectedYear=" + year;
	url = url + "&selectedNdkhjgId=" + ndkhjgId;
	url = url + "&selectedPersonType=" + personType;
	
	//window.open(url);
	showDetailsWindow(url, '干部年度考核相关文件');
}

// 查看班子文件资料
function viewUnitFiles(unitId, year) {
	var url = System.rootPath + '/ndkh/ndkh!unitFile.action'; 
	url = url + "?selectedUnitId=" + unitId;
	url = url + "&selectedYear=" + year;
	
	//window.open(url);
	showDetailsWindow(url, '班子年度考核相关文件');
}

function isValidGradeYear(year) {
	var now = new Date();
	var tyear = now.getFullYear() - 1;
	var ryear = parseInt(year, 10);
	if (ryear >= tyear) return true;
	else return false;
}

function isProvinceFormType(formType) {
	if (formType == null || formType == '' || formType == '武汉城市圈' || formType == '鄂西生态文化旅游圈' || formType == '党委' || formType == '政府' || formType == '党政') {
		return false;
	}
	else {
		return true;
	}
}
			