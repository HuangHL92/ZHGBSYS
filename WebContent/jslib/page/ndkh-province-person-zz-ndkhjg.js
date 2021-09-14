$(function() {
	setZwFields(g_year, 5);
	/*
	setGradeFields(2);
	
	if (isValidGradeYear(g_year)) {
		$("#btnAutoSetPersonZzNdkhjgGrade").linkbutton("enable");
	}
	*/
});

function viewPersonFiles(ndkhjgId, personcode, year, personType) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.viewPersonFiles(ndkhjgId, personcode, year, personType);
	}
}

function orderRecord(order) {
	var url = '';
	if (g_isView == '0') url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzNdkhjg.action'; 
	else url = System.rootPath + '/ndkh/ndkh!personNdkhjg.action'; 
	
	url = addParams(url, order);
	window.location.href = url;
}

function addParams(url, order) {
	url += '?selectedFormType=' + encodeURIComponent(g_formType);
	url += '&selectedYear=' + g_year;
	url += '&selectedUnitId=' + g_unitId;
	url += '&selectedOrder=' + order;
	url += '&selectedPersoncode=' + g_personcode;
	url += '&selectedPersoncodes=' + g_personcodes;
	url += '&selectedPersonType=' + g_personType;
	
	return url;
}

function getParent() {
	try {
		window.parent.parent.ndkhIsReady();
		return window.parent.parent;
	}
	catch (e) {
		try {
			window.parent.ndkhIsReady();
			return window.parent;
		}
		catch (ex) {
			
		}
	}

	return null;
}

function showPersonMzcp(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openProvinceUnitPersonZzMzcpWin(unitId, personcode, g_year, g_formType);
	}
}

function showPersonSwqwhpj(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openProvinceUnitPersonZzSwqwhpjWin(unitId, personcode, g_year, g_formType);
	}
}

function showPersonZfqkfxcp(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openProvinceUnitPersonZzZfqkfxcpWin(unitId, personcode, g_year, g_formType);
	}
}