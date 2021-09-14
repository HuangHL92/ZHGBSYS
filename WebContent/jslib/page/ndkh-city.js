function addParams(url) {
	url += '?selectedFormType=' + encodeURIComponent(g_formType);
	url += '&selectedYear=' + g_year;
	url += '&selectedUnitId=' + g_unitId;
	url += '&selectedOrder=';
	return url;
}

function addParams2(url, formType) {
	url += '?selectedFormType=' + encodeURIComponent(formType);
	url += '&selectedYear=' + g_year;
	url += '&selectedUnitId=' + g_unitId;
	url += '&selectedOrder=';
	return url;
}

// 载入班子民主测评汇总数据
function loadUnitMzcp() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitMzcp.action'; 
	url = addParams(url);
	$("#unitMzcp").attr('src', url);
}

// 载入班子作风情况反向测评汇总数据
function loadUnitZfqkfxcp() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitZfqkfxcp.action'; 
	url = addParams(url);
	$("#unitZfqkfxcp").attr('src', url);
}

// 载入班子省委常委会汇总数据
function loadUnitSwcwhpj() {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwcwhpj.action'; 
	url = addParams(url);
	$("#unitSwcwhpj").attr('src', url);
}

// 载入班子省委全委会评价汇总数据
function loadUnitSwqwhpj() {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitSwqwhpj.action'; 
	url = addParams(url);
	$("#unitSwqwhpj").attr('src', url);
}

// 载入班子年度考核汇总数据
function loadUnitNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitNdkhjg.action'; 
	url = addParams(url);
	$("#unitNdkhjg").attr('src', url);
}

// 载入党政班子年度考核汇总数据
function loadUnitDzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitNdkhjg.action'; 
	url = addParams2(url, '党政');
	$("#unitDzNdkhjg").attr('src', url);
}

// 载入党委班子年度考核汇总数据
function loadUnitDwNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitNdkhjg.action'; 
	url = addParams2(url, '党委');
	$("#unitDwNdkhjg").attr('src', url);
}

// 载入政府班子年度考核汇总数据
function loadUnitZfNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitNdkhjg.action'; 
	url = addParams2(url, '政府');
	$("#unitZfNdkhjg").attr('src', url);
}

// 载入党政正职年度考核数据
function loadZzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonZzNdkhjg.action'; 
	url = addParams(url);
	$("#zzNdkhjg").attr('src', url);
}

// 载入班子副职年度考核数据
function loadBzfzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonBzfzNdkhjg.action'; 
	url = addParams(url);
	$("#bzfzNdkhjg").attr('src', url);
}

// 载入非党政班子正职年度考核数据
function loadFdzbzzzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonFdzbzzzNdkhjg.action'; 
	url = addParams(url);
	$("#fdzbzzzNdkhjg").attr('src', url);
}

// 载入厅级干部年度考核数据
function loadTjgbNdkhjg(order) {
	var url = System.rootPath + '/ndkh/ndkh!cityUnitPersonTjgbNdkhjg.action'; 
	url = addParams(url);
	$("#tjgbNdkhjg").attr('src', url);
}

// 载入班子民主推荐文件内容
function loadUnitMztjFile(unitId, year) {
	var url = System.rootPath + '/ndkh/ndkh!unitMztjFile.action'; 
	url += '?selectedUnitId=' + unitId;
	url += '&selectedYear=' + year;
	
	$("#unitMztjFile").attr('src', url);
}

// 载入班子文件内容
function loadUnitFile(unitId, year) {
	var url = System.rootPath + '/ndkh/ndkh!unitFile.action'; 
	url += '?selectedUnitId=' + unitId;
	url += '&selectedYear=' + year;
	
	$("#unitFile").attr('src', url);
}
