$(function() {
	setZwFields(g_year, 1);
	/*
	setGradeFields(3);
	
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
	if (g_isView == '0') url = System.rootPath + '/ndkh/ndkh!cityUnitPersonZzNdkhjg.action'; 
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

function showUnitSjcp(unitId) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openCityUnitSjcpWin(unitId, g_year, g_formType);
	}
}

function showPersonMzcp(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openCityUnitPersonDzbzcyMzcpWin(unitId, personcode, g_year, g_formType);
	}
}

function showPersonZfqkfxcp(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openCityUnitPersonZfqkfxcpWin(unitId, personcode, g_year, g_formType);
	}
}

function showPersonSwqwhpj(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openCityUnitPersonSwqwhpjWin(unitId, personcode, g_year, g_formType);
	}
}

function showPersonZfqkfxcp(unitId, personcode) {
	var pWin = getParent();
	if (pWin != null) {
		pWin.openCityUnitPersonZfqkfxcpWin(unitId, personcode, g_year, g_formType);
	}
}
