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
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitMzcp.action'; 
	url = addParams(url);
	$("#unitMzcp").attr('src', url);
}

// 载入班子作风情况反向测评汇总数据
function loadUnitZfqkfxcp() {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitZfqkfxcp.action'; 
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
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitNdkhjg.action'; 
	url = addParams(url);
	$("#unitNdkhjg").attr('src', url);
}

// 载入党政正职年度考核数据
function loadZzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZzNdkhjg.action'; 
	url = addParams(url);
	$("#zzNdkhjg").attr('src', url);
}

// 载入班子副职年度考核数据
function loadBzfzNdkhjg() {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonBzfzNdkhjg.action'; 
	url = addParams(url);
	$("#bzfzNdkhjg").attr('src', url);
}

// 载入厅级干部年度考核数据
function loadTjgbNdkhjg(order) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonTjgbNdkhjg.action'; 
	url = addParams(url);
	$("#tjgbNdkhjg").attr('src', url);
}

// 载入干部民主测评数据
function loadPersonMzcp(order) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonMzcp.action'; 
	url = addParams(url);
	$("#personMzcp").attr('src', url);
}

// 载入干部作风情况反向测评数据
function loadPersonZfqkfxcp(order) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonZfqkfxcp.action'; 
	url = addParams(url);
	$("#personZfqkfxcp").attr('src', url);
}

// 载入干部实绩测评数据
function loadPersonSjcp(order) {
	var url = System.rootPath + '/ndkh/ndkh!provinceUnitPersonSjcp.action'; 
	url = addParams(url);
	$("#personSjcp").attr('src', url);
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
