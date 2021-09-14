/**
 * 获取审核不通过的具体信息
 */  
var sql = "select  au.seno as seno,func.title as functiontitle,func.functionid as functionid,ul.digest as digest,su.username as aae011,ul.aae036 as aae036,au.auflag as auflag,ul.opseno as opseno from operationaudit au, sbds_userlog  ul, smt_function func,smt_user su where au.opseno = ul.opseno and ul.functionid = func.functionid and ul.aae011=su.loginname and ul.eae024='0' and au.auflag = '0' ";//and au.auflag = '0' 
if(MDParam.param1){   	
	sql+=" and ul.functionid='"+MDParam.param1+"' ";
}
/* 
function doGetAuditGridData(){

    var sql = "select au.seno as seno,func.title as functiontitle,func.functionid as functionid,ul.digest as digest,su.operatorname as aae011,ul.aae036 as aae036,au.auflag as auflag,ul.opseno as opseno from operationaudit au, sbds_userlog  ul, sysfunction func,sysuser su where au.opseno = ul.opseno and ul.functionid = func.functionid and ul.aae011=su.username and ul.eae024='0' and au.auflag = '0' ";//and au.auflag = '0'     
   
    if(MDParam.param1){   	
		sql+=" and ul.functionid='"+MDParam.param1+"' ";
	}
	sql += " order by ul.opseno ";
	var params = {};
    params.querySQL = sql;
    params.sqlType = "SQL";
    odin.loadPageGridWithQueryParams('auditGrid', params);
}
*/

function doGetAuditGridData(where){  
	
	if(where==null || where==""){
		var querySQL=sql;
	}else{
		var querySQL=sql+where;
	}
	
	querySQL += " order by ul.opseno desc";
	var params = {};
    params.querySQL = querySQL;
    params.sqlType = "SQL";
    odin.loadPageGridWithQueryParams('auditGrid', params);
}


function doFormatBirthDay(value, params, record, rowIndex, colIndex, ds){
    return value.substring(0,16);
}
function initLookInfo(value, params, record, rowIndex, colIndex, ds){
    return "<font color=red><a href='javascript:void(0);' onclick='doOpenMoreInfoTab("+value+")' >详细信息</a></font>";
}
function doOpenMoreInfoTab(opseno){
	var src=contextPath+"/common/auditAction.do?method=getOriSource&opseno="+opseno;
	var tabs=top.main.tabs;
	var aid="businessOldPage";
	var atitle="业务原始界面";
	var tab=tabs.getItem(aid);        
	if (tab){tabs.remove(tab);}
	top.main.addTab(atitle,aid,src);  
}
function renderDigest(value, params, record, rowIndex, colIndex, ds){
    return "<font color='#009933'><a style='text-decoration:none' href='javascript:void(0);' title='"+value+"'>"+value+"</a></font>";
}
function renderOpseno(value){
	return "<font color=red><b>"+value+"</b></font>";
}
function doRowDClick(grid, rowIndex, colIndex, event){
	var opseno = grid.store.getAt(rowIndex).get('opseno');
	doOpenMoreInfoTab(opseno);
}
function renderAuflag(value, params, record, rowIndex, colIndex, ds){
    if(value==1){
		return "<font color=red><b>通过</b></font>";
	}else{
		return "<font color=red><b>不通过</b></font>";
	}
}
/**
 * 审核操作
 */
function doAudit(b){
	var grid = odin.ext.getCmp('auditGrid');
	var store = grid.store;
	if(store.getCount()>0){
		var params = new Array();
		var index = 0;
		for(i=0;i<store.getCount();i++){
			var rs = store.getAt(i);
			//alert(rs.get('isselect'));
			if (rs.get('isselect') == true) {
				var auditBean = {};
				auditBean.functionid = rs.get('functionid');
				auditBean.opseno = rs.get('opseno');
				params[index] = auditBean;
				index++;
			}
		}
		if (index == 0) {
			odin.alert('请选择要审核的项！');
			return false;
		}
		//alert(odin.ext.encode(params));
		var url = contextPath+'/common/auditAction.do?method=audit';
		var p = {};
		p.auditObjs = odin.ext.encode(params);
		p.auditlog = odin.doOpLog();
		odin.Ajax.request(url,p,doAuditSuccess,null);
	}else{
		odin.alert('请选择要审核的项！');
	}
}
function doAuditSuccess(response){
	alert("审核成功！");
	for(var i=0;i<response.data.length;i++){
		print(response.data[i]);
	}
	doGetAuditGridData();
}

function print(plList){
	for(var i=0;i<plList.length;i++){
		if(confirm("是否打印【"+plList[i].title+"】？")){
			var preview=true;
			if(plList[i].preview=="0"){
				preview=false;
			}
			odin.billPrint(plList[i].repid,plList[i].queryname,plList[i].param,preview);				
		}
	}
}

function simpleQueryByDate(){
	var where="";
	var querydate=document.all.querydate.value;
	if(querydate!=""){
		where=where+" and ul.aae036>=to_date('"+querydate+"','yyyy-mm-dd') and ul.aae036<to_date('"+querydate+"','yyyy-mm-dd')+1";
	}
	doGetAuditGridData(where);
}

function expExcelData(){
	expExcel("auditGrid");
} 
