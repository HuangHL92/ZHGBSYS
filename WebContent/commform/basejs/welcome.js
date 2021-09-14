var cue_content_tab = 0;
var records = "";
var SQL = "";
/**
 * 草稿箱、正在审核等的切换
 * 
 * @param {Object}
 *            index
 * @author jinwei
 */
function right_review_on(index) {
	cue_content_tab = index;
	var childDiv = document.all('content_tab');
	for (i = 0; i < childDiv.length; i++) {
		if (index == i) {
			childDiv[i].style.display = "";
		} else {
			childDiv[i].style.display = "none";
		}
	}
	doLoadGridData(index, 1);
}
/**
 * 获取通知公告信息，并对通知公告区域的内容重写
 */
function getSbdnNoticeContent() {
	
	var params1 = {};
	params1.sqlType = "SQL";
	params1.querySQL = "@_PSItqiEKenMdr59J+fhJ8zmHXeNA8e/uRWz+lzdS0glKzqZ/FyMt0lS6tDWvgd4/0tqjdQx4ADXI7pQb2tRJ/tPfrhMCeW3j4qZbRmBwVo1f7Am89gWPjg==_@"
			+ username + "@_mMxQSO+LfFw=_@";
	var req = odin.commonQuery(params1, odin.ajaxSuccessFunc, null, false,
			false);
	var data1 = odin.ext.decode(req.responseText).data.data[0];
	var aab301 = data1.aab301;
	var roleid = data1.roleid;
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_76tJBvkT+4VXM09mAi+FHplVviSz04f0L1f4k1tehGibA5O9PJtpFvoqIEWd2C4nObpwOl+qskXH8NC/5nNQ9UHnSNIM3T+LBC+tVNWyg/WQfU8un2JAMQ==_@"
			+ username
			+ "@_+/1CNNzAsS7oIACXTkPyOaKpMbtf8aPEJOv4JVIEm9PYCQ5n6/RY/0rbODztqoUY037SsdKgOuU8dJVELpGwbPZ4tvfNO9+ZdVMiULoZaG3tU2yYW5xDZLIhJrXjgX6WDw0E67LnbieWjKb/9IrbxqrEQ4VfsmAIlV+RBsyvHz5iI2k8K0AubISXgC/c4/Im9s6zqqXvREffBGjy3w8UKT/XNPN96YvLIkd24fM8UaE=_@"
			+ aab301
			+ "@_qgimwM16xeniRVZdpcmo/L25CUBrs3rmEV86i8eTK5gvxEnF41XTe2CM0VMle2BL_@"
			+ roleid
			+ "@_OtXnjpjOMIC3Vcq17mk0VOCv7NnmTJeSSM+gwXG3BVFKhK0RPlQHlD1D/2ZiknMB_@";
	var data = odin.ext.decode(odin.commonQuery(params, odin.ajaxSuccessFunc,
			null, false, false).responseText).data.data;
	for (i = 0; i < data.length; i++) {
		var obj = data[i];

		/*
		 * if(obj.alltitle.length>30){ data[i].title =
		 * obj.alltitle.substring(0,30)+"…";
		 * 
		 * }else{ var len=obj.alltitle.length; var space=""; for(m=len;m<33;m++){
		 * space=space+"."; } space=space+obj.time; data[i].title =
		 * obj.alltitle+space; data[i].date = obj.time; }
		 */

		if (obj.isnew != "1") {
			obj.isnewimg = '<img src=' + contextPath + '/img/new.gif>'
		}
		data[i].title = obj.alltitle;
		data[i].date = obj.time;
	}
	SbdnNoticeTpl.overwrite(odin.ext.get('c_left_content'), data);
}
/**
 * 草稿状态的处理
 * 
 * @param {Object}
 *            value
 */
function renderDraftStatus(value) {
	if (value == 1) {
		return "<img src=\"../commform/img/right.gif\" alt=\"可以申报\" width=\"14\" height=\"14\">";
	} else {
		return "<img src=\"../commform/img/wrong.gif\" alt=\"格式有误\" width=\"14\" height=\"14\">";
	}
}

/**
 * 点击显示的效果
 * 
 * @param {Object}
 *            value
 */
function renderAlt(value) {
	return '<a title=' + value + '>' + value + '</a>';
}
/**
 * 显示删除操作
 * 
 * @param {Object}
 *            value
 * @param {Object}
 *            params
 * @param {Object}
 *            record
 * @param {Object}
 *            rowIndex
 * @param {Object}
 *            colIndex
 * @param {Object}
 *            ds
 */
function renderDeleteDraft(value, params, record, rowIndex, colIndex, ds) {
	return "<a href='javascript:void(0)' onclick='tishiDeleteDraft(\"" + value + "\")'>删除</a>";
}
function renderInfoDraft(value, params, record, rowIndex, colIndex, ds) {
	return "<a href='javascript:void(0)' onclick='detailPreAudit3(\"" + value + "\")'>详细信息</a>";
}

function renderEditDraft(value, params, record, rowIndex, colIndex, ds) {
	return "<a href='javascript:void(0)' onclick='doOpenEditWindow1(\"" + value + "\")'>修改</a>";
}
/**
 * 删除草稿
 * 
 * @param {Object}
 *            id
 */
var cueID = "";
function tishiDeleteDraft(id) {
	cueID = id;
	odin.confirm("您确定要删除该条草稿吗？", deleteDraft);
}
function deleteDraft(btn) {
	if (btn == 'ok') {
		// alert("删除id为"+cueID+"的草稿！");
		// 具体删除操作
		var ywlx = document.all.businessType.value;
		var aaz001 = document.all.aaz001.value;
		var params = {};
		params.id = cueID;
		params.ywlx = ywlx;
		params.aaz001 = aaz001;
		params.username= username;
		var req = odin.Ajax.request(contextPath
						+ '/pages/draft/draftAction.do?method=deleteDraftById',
				params, odin.ajaxSuccessFunc, null, false, true); // 采用同步方式删除
		odin.ext.getCmp('grid0').getSelectionModel().clearSelections(); // 清除选种的项
		// 删除成功后重新给表格加载数据
		doLoadGridData(null, 1);
		doGetAllStatusCount();
		var response = odin.ext.decode(req.responseText);
		// odin.info(response.mainMessage);
	}
}
/**
 * 选中的草稿箱进行删除
 */
function selectDeleteDraft() {
	var gird = odin.ext.getCmp('grid0');
	var dataArray = gird.getSelectionModel().getSelections();
	if (gird.getBottomToolbar().pageSize == dataArray.length) {
		// 说明是全选
		cueID = "all";
		odin.confirm("确定要将全部草稿信息（包括全部页）进行删除吗？", deleteDraft); // 全选删除
	} else if (dataArray.length > 0) {
		var declareIds = "";
		var haveDraftError = false;
		for (i = 0; i < dataArray.length; i++) {
			var rs = dataArray[i];
			if (rs.get('draftstatus') == '01') {
				declareIds += "," + rs.get('bcode');
			} else {
				haveDraftError = true;
			}
		}
		declareIds = declareIds.substring(1);
		cueID = declareIds;
		if (haveDraftError == true) {
			odin.confirm(
					"您选择的草稿数据里有错误数据，\n 如果您选择\"确定\"系统将自动过滤掉这些数据并进行申报，否则不予申报！",
					deleteDraft);
		} else {
			odin.confirm("您确定要删除选中数据吗？", deleteDraft);
		}
	}
}
/**
 * 全选
 */
function selectall() {
	if (Ext.getCmp('grid0').getStore().getCount() > Ext.getCmp('grid0')
			.getSelectionModel().getCount()) {// 未全选时进行全选
		Ext.getCmp('grid0').getSelectionModel().selectAll();
	} else { // 全清
		Ext.getCmp('grid0').getSelectionModel().clearSelections();
	}

}
/**
 * 审核未成功的重新上报的显示
 * 
 * @param {Object}
 *            value
 * @param {Object}
 *            params
 * @param {Object}
 *            record
 * @param {Object}
 *            rowIndex
 * @param {Object}
 *            colIndex
 * @param {Object}
 *            ds
 */
// function renderNotSucc(value, params, record, rowIndex, colIndex, ds){
// return "<a href='#' >修改</a>";
// }
var cueDeclareIds = "";
var cueDeclareHashs = "";
/**
 * 申报处理
 */

function doDeclare() {
	// odin.alert("业务经办人手机号码必须正确!");
	var gird = odin.ext.getCmp('grid0');
	// 具体申报处理，对于草稿有误的不予申报
	var dataArray = gird.getSelectionModel().getSelections();
	if (gird.getBottomToolbar().pageSize == dataArray.length) {
		// 说明是全选
		cueDeclareIds = "all";
		odin.confirm("确定要将全部草稿信息（包括全部页）进行申报吗？", isdeclare); // 全选申报
		return;
	}
	if (dataArray.length > 0) {
		// 申报
		var haveDraftError = false;
		cueDeclareIds = "";
		cueDeclareHashs = "";
		for (i = 0; i < dataArray.length; i++) {
			var rs = dataArray[i];
			if (rs.get('draftstatus') == '01') {
				cueDeclareIds += "," + rs.get('bcode');
				cueDeclareHashs += "," + rs.get('hashcode');
			} else {
				haveDraftError = true;
			}
		}
		cueDeclareIds = cueDeclareIds.substring(1);
		cueDeclareHashs = cueDeclareHashs.substring(1);
		if (haveDraftError == true) {
			odin.confirm(
					"您选择的草稿数据里有错误数据，\n 如果您选择\"确定\"系统将自动过滤掉这些数据并进行申报，否则不予申报！",
					isdeclare);
		} else {
			odin.confirm("是否确定继续申报选中的数据？", isdeclare); // 申报
		}

	} else {
		odin.info('请选中要申报的纪录再点申报！');
	}
}

function isdeclare(btn) {
	if (btn == 'ok') {
		declare();
	}
}

function declare() { // 申报操作
	// 具体申报
	var params = {};
	var ywlx = document.all.businessType.value;

	var sign = "";
	var aaz001 = document.all('aaz001').value;// /单位内码
	if (isNeedSign == true && cueDeclareIds == 'all') { // 重新组装id

		var params = {};
		params.sqlType = "SQL";
		params.querySQL = "select a.opseno,'' hashcode from sbds_userlog a where a.sbzt = '01' and a.eae024='0' and a.aaz001="
				+ aaz001;
		if (ywlx != 'all') {
			params.querySQL = params.querySQL + " and a.ywlx='" + ywlx + "'";
		}
		var data = odin.ext.decode(odin.commonQuery(params,
				odin.ajaxSuccessFunc, null, false, false).responseText).data.data;
		cueDeclareIds = "";
		cueDeclareHashs = "";
		for (i = 0; i < data.length; i++) {
			var obj = data[i];
			cueDeclareIds = cueDeclareIds + "," + obj.opseno;
			cueDeclareHashs = cueDeclareHashs + "," + obj.hashcode;
		}
		cueDeclareIds = cueDeclareIds.substring(1);
		cueDeclareHashs = cueDeclareHashs.substring(1);
	}

	params.id = cueDeclareIds;
	params.ywlx = ywlx;
	if(aaz001 == null || aaz001 == ""){
		aaz001=0;
	}
	params.aaz001 = aaz001;
	params.username= username;
	if (isNeedSign == true) { // 要数字签名
		var ret = dosign(cueDeclareHashs);
		if (ret.flag == false) {
			return;
		}
		params.signdata = ret.signdata;
	}
	var req = odin.Ajax.request(contextPath
					+ '/pages/draft/draftAction.do?method=declareDraftById',
			params, odin.ajaxSuccessFunc, null, false, true); // 采用同步方式申报
	odin.ext.getCmp('grid0').getSelectionModel().clearSelections(); // 清除选种的项
	// 处理完后对草稿箱的表格进行重新加载数据
	doLoadGridData(0, 1);
	doGetAllStatusCount();// 重新处理状态数字（如草稿箱有多少笔业务）
}

// 进行数字签名
function dosign(data) {
	var signStr = "";
	var result = {};
	var cert = signature.getCertificate();

	if (cert.flag == false) {
		odin.error('未找到颁发的数字签名证书，您无法申报业务!');
		result.flag = false;
		return result;
	}
	if (!signature.checkDate(cert)) {
		odin.error('您的数字证书已经过期，无法申报业务!');
		result.flag = false;
		return result;
	}
	data = data.split(",");
	try {
		for (var i = 0; i < data.length; i++) {
			var hashcode = data[i];
			var signdata = signature.sign(cert, hashcode);
			signStr = signStr + "," + signdata;
		}
		signStr = signStr.substring(1);
		// alert(signStr);
	} catch (e) {
		odin.error(e);
		result.flag = false;
		return result;
	}

	result.flag = true;
	result.signdata = signStr;
	return result;
}

/**
 * 根据通知id获取通知内容，并将内容显示在窗口里
 * 
 * @param {Object}
 *            id
 */
function doShowSbdnNotice(id) {
	// 查数据库获取内容

	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_76tJBvkT+4WzUP/pBag8S7HH5XVSon/QvWqrSvUPkhxveszAV4/ovg==_@"
			+ id + "@_mMxQSO+LfFw=_@";

	var content = odin.ext.decode(odin.commonQuery(params,
			odin.ajaxSuccessFunc, null, false, false).responseText).data.data[0].rcontent;
	// ……………………

	var SbdnNoticeWindow = odin.ext.getCmp('SbdnNoticeWindow');
	SbdnNoticeWindow.setTitle('通知内容');
	SbdnNoticeWindow.setSize(500, 350); // 宽度 高度
	SbdnNoticeWindow.show(SbdnNoticeWindow);
	var html = "<div style='background-color:#ffffff;height:100%;OVERFLOW:auto;text-align:left;padding:5px;'>";
	html += content;
	html += "</div>";
	SbdnNoticeWindow.body.dom.innerHTML = html;

	var ODA_TRANSMIT_OBJECT = {};
	ODA_TRANSMIT_OBJECT.id = id;
	odin.Ajax
			.request(
					contextPath
							+ '/com/insigma/siis/local/comm/notice/NoticeSaveAction.do?method=save',
					{
						_ODA_TRANSMIT_OBJECT : odin.encode(ODA_TRANSMIT_OBJECT)
					}, doSuccess, null, false, false);
	getSbdnNoticeContent();
	getCpSbdnNoticeContent();
}
function doSuccess() {

}
/**
 * 打开更多所对应的tab页
 */
function doOpenMoreSbdnNotice() {
	parent.doOpenPopWindow('/notice/notice.jsp', '所有公共通知', 840, 500);
	// if (document.all("iframe_moreSbdnNoticeWindow") == null) {
	// odin.showWindowWithSrc('moreSbdnNoticeWindow','SbdnNotice/SbdnNotice.jsp');
	// }
	// odin.showWindowWithSrc('moreSbdnNoticeWindow','SbdnNotice/SbdnNotice.jsp');
	// Ext.getCmp('moreSbdnNoticeWindow').maximize();
	// parent.openUrl('所有通知','allSbdnNotice','SbdnNotice/SbdnNotice.jsp');
	// parent.openUrl('人员新增','4028c71b1956608101195678303d0008','/pages/joinsafetydeclare/joinsafetydeclareAction.do');
}

function doOpenmanadd() {
	parent.openUrl('人员新增', '4028c71b1956608101195678303d0008',
			'/pages/joinsafetydeclare/joinsafetydeclareAction.do');
}
/**
 * 加载表格数据 index可以为空，它表示那种情况 type==1 表示当前按类型查 type == 2 表示当前是按类型同时结合时间查 type == 3
 * 表示当前是按高级搜索里的时间同时结合类型查
 */
function doLoadGridData(index, type) {

	var aaz001 = document.all('aaz001').value;// /单位内码
	if (index == 'undefined' || index == null) {
		index = cue_content_tab;
	}
	var params = {};
	if (index == 0) {// 加载草稿箱表格的数据
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.sbzt='01'  and viewdraft='0'";
	} else if (index == 1) {// 加载正在审核表格的数据
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and (a.sbzt='11' or a.sbzt='30') and viewdraft='0'";
	} else if (index == 2) {// 加载审核成功表格的数据
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.sbzt='41' and viewdraft='0'";
	} else if (index == 3) {// 加载审核不成功表格的数据
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,a.digest as notice,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and (a.sbzt='42' or a.sbzt='99') and viewdraft='0'";
	} else if (index == 4) {// 加载所有的申报数据
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,decode(a.sbzt,'99','42',a.sbzt) as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'详细信息' as info,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.opseno is not null and viewdraft='0'";
	}
	if (type != 'undefined' && type == 1) {
		params.querySQL += queryDateFilterStr;
	} else if (type != 'undefined' && type == 2) {
		params.querySQL += queryDateFilterStr;
	} else if (type != 'undefined' && type == 3) {
		params.querySQL += queryDateFilterStr;
	}
	if (aaz001 != null && aaz001 != 0) {
		params.querySQL += " and aaz001=" + aaz001 + "";
	}
	params.querySQL += " and a.eae024='0' and a.aae011='"
			+ username
			+ "' order by a.czdate desc,a.aae036 desc ,a.opseno  desc ";
	params.sqlType = "SQL";
	odin.loadPageGridWithQueryParams('grid' + index, params);
	doGetAllStatusCount();
}
/**
 * 获取所有状态对应的记录条数，并修改页面相关地方
 */
function doGetAllStatusCount() {
	var aaz001 = document.all('aaz001').value;// /单位内码

	var params = {};
	params.querySQL = "select count(*) as total,opseno,sbzt from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and eae024='0' and b.viewdraft='0' and a.aae011='"
			+ username+"'" ;
	params.sqlType = "SQL";
	if (aaz001 != null && aaz001 != 0) {
		params.querySQL += "  and aaz001=" + aaz001 + "";
	}
	params.querySQL += queryDateFilterStr + " group by opseno,sbzt";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var data = odin.ext.decode(req.responseText).data.data;
	// alert(odin.ext.encode(data));
	// 将取到的数据分别填到对应页面的区域上去
	var draftTotalCount = 0; // 草稿箱
	var declareSpan1 = 0; // 正在审核
	var declareSpan2 = 0; // 审核成功
	var declareSpan3 = 0; // 审核不成功
	var total = 0; // 全部
	for (i = 0; i < data.length; i++) {
		var obj = data[i];
		total += parseInt(obj.total);
		if (obj.sbzt == '01' || obj.sbzt == '02') {
			draftTotalCount += parseInt(obj.total);
		} else if (obj.sbzt == '11' || obj.sbzt == '21' || obj.sbzt == '30') {
			declareSpan1 += parseInt(obj.total);
		} else if (obj.sbzt == '41') {
			declareSpan2 += parseInt(obj.total);
		} else if (obj.sbzt == '22' || obj.sbzt == '32' || obj.sbzt == '42'
				|| obj.sbzt == '99') {
			declareSpan3 += parseInt(obj.total);
		}
	}
	// alert("草稿箱"+draftTotalCount+"正在审核"+declareSpan1+"审核成功"+declareSpan2+"审核不成功"+declareSpan3+"全部"+total);
	for (i = 0; i <= 4; i++) {
		var spanObj = document.all('declareSpan' + i);
		for (j = 0; j < spanObj.length; j++) {
			switch (i) {
				case 0 :
					spanObj[j].innerText = draftTotalCount;
					break;
				case 1 :
					spanObj[j].innerText = declareSpan1;
					break;
				case 2 :
					spanObj[j].innerText = declareSpan2;
					break;
				case 3 :
					spanObj[j].innerText = declareSpan3;
					break;
				case 4 :
					spanObj[j].innerText = total;
					break;
			}
		}
	}
}
var queryDateFilterStr = ""; // 查询时按时间的一个过滤
var queryTypeFilterStr = ""; // 查询时按类型的一个过滤
var queryByAdvancedSearch = "";// 高级搜索
var queryDateFilterStrtmp = "";// 时间临时替换
/**
 * 形成根据时间和业务类型的查询条件，并根据选择重新加载数据
 * 
 * @param {Object}
 *            type
 */
function getQueryTypeFilterStr(type) { // type==1 代表本月 type==2代表上月
	var date = new Date();
	var queryStr = "@_XMm7cs3HRqFiOqWSJDncwweEn5gEI7MtLxvZMLPwW92YzFBI74t8XA==_@"
	if (type == 1) {
		document.all.ywym.value = date.format('Ym');
		queryStr += date.format('Ym');
	} else if (type == 2) {
		document.all.ywym.value = date.add(Date.MONTH, -1).format('Ym');
		queryStr += date.add(Date.MONTH, -1).format('Ym');
	} else {
		var time = document.all.ywym.value;
		if (time == null || time == "") {
			queryStr = "@_XMm7cs3HRqFiOqWSJDncwweEn5gEI7MtJh2B/QAOQQkcgzTL7xurww==_@"
		} else if (time.length != 6) {
			alert("业务年月位数不对");
			return;
		} else {
			queryStr += time;
		}
	}
	queryStr += "' ";
	queryDateFilterStrtmp = queryStr;
	queryDateFilterStr = queryTypeFilterStr + queryStr;
	// alert(queryDateFilterStr);
	doLoadGridData(null, type);
}
/**
 * 形成根据业务类型的查询条件，并根据选择重新加载数据
 */
function getBusinessTypeQueryStr() {
	var queryStr = "";
	var type = document.all('businessType').value;
	if (type != 100 && type != "") {
		// alert(type);
		if (type == "all")
			type = "%";
		queryStr += "@_H2A9J21oMSTlmLh3VQK1JY2cQ53gXt4yJyeBCI4o1kA=_@" + type
				+ "@_fqOngwdfJf8=_@";
	}
	queryTypeFilterStr = queryStr;
	queryDateFilterStr = queryStr + queryDateFilterStrtmp;
	// alert(queryTypeFilterStr);
	if (type != "") { // 类型不选的话，不加载数据
		doLoadGridData(null, 1);
	}
}
/**
 * 处理草稿箱的双击编辑功能 jinwei
 * 
 * @param {Object}
 *            grid
 * @param {Object}
 *            rowIndex
 * @param {Object}
 *            event
 */
function doOpenEditWindow(grid, rowIndex, event) {
	var store = grid.store;
	var cueRecord = store.getAt(rowIndex);
	var src = contextPath;
	var aac001 = cueRecord.get('aac001'); // 单位或人员内码
	var cae001 = cueRecord.get('bcode'); // 操作序列号
	var openWin = odin.ext.getCmp('SbdnNoticeWindow');
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5WlsD0wAN7ei3Rzo/V+uWBdpsklrVM1bzKxmn/YItH3cMluOAme1fMMPsPcdF3TG+FmhKQpTrVaN6FsJSiO3LOot1qYsRlLIzefW6D9kAfQp8fTGbdxLtCWqgfZ7UtTudzwxBrQCpD5dg==_@"
			+ cae001 + "@_ApaZmPpyq84=_@";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	// alert(Ext.encode(data));
	src += data.location + "&opseno=" + cae001;

	parent.doOpenPopWindow(src, data.title, 810, 500);
	/*
	 * //alert(src); openWin.setTitle(data.title); openWin.setSize(800,450);
	 * //宽度 高度 \* if(cueRecord.get('btype')=='42'){ //人员基础信息修改 src +=
	 * '/pages/personbaseinfomodify/personbaseinfomodifywindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('人员基本信息变更编辑窗口'); openWin.setSize(505,380); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='43'){ //人员新参保申报 src +=
	 * '/pages/joinsafetydeclare/joinsafetydeclarelistAction.do?cae001='+cae001;
	 * openWin.setTitle('人员新增编辑窗口'); openWin.setSize(520,360); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='45'){ //人员中断申报 src +=
	 * '/pages/safetyintermitdeclare/safetyintermitdeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('人员中断编辑窗口'); openWin.setSize(505,380); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='48'){ //人员险种减少 src +=
	 * '/pages/safetyreducedeclare/safetyreducedeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('人员险种减少编辑窗口'); openWin.setSize(510,410); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='47'){ //人员险种增加 src +=
	 * '/pages/safetyadddeclare/safetyadddeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('人员险种增加编辑窗口'); openWin.setSize(500,345); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='44'){ //人员再参保申报 src +=
	 * '/pages/resafetydeclare/resafetydeclareListAction.do?cae001='+cae001;
	 * openWin.setTitle('人员续保编辑窗口'); openWin.setSize(510,420); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='46'){ //年度工资上报 src +=
	 * '/pages/paymentwagedeclare/paymentWageDeclareWindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('年度工资上报编辑窗口'); openWin.setSize(500,200); //宽度 高度 }else
	 * if(cueRecord.get('btype')=='41'){ //单位基本信息变更 src +=
	 * '/pages/companybaseinfomodify/companybaseinfomodifyAction.do?cae001='+cae001;
	 * openWin.setTitle('单位基本信息变更编辑窗口'); openWin.setSize(800,380); //宽度 高度 } \
	 * if (document.all("iframe_SbdnNoticeWindow") == null) {
	 * openWin.resizable=false; odin.showWindowWithSrc('SbdnNoticeWindow',src); }
	 * openWin.resizable=false; odin.showWindowWithSrc('SbdnNoticeWindow',src);
	 */
}

/** ********界面还原***************** */
var isFirst = true;
function detailPreAudit2(grid, rowIndex, event) {
	var store = grid.store;
	var cueRecord = store.getAt(rowIndex);
	var src = contextPath;
	var aac001 = cueRecord.get('aac001'); // 单位或人员内码
	var cae001 = cueRecord.get('bcode'); // 操作序列号
	var openWin = odin.ext.getCmp('SbdnNoticeWindow');
	var btype = cueRecord.get('btype');

	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5WlsD0wAN7ei3Rzo/V+uWBdpsklrVM1bzKxmn/YItH3cMluOAme1fMMPsPcdF3TG+FmhKQpTrVaN6FsJSiO3LOot1qYsRlLIzefW6D9kAfQp8fTGbdxLtCWqgfZ7UtTudzwxBrQCpD5dg==_@"
			+ cae001 + "@_ApaZmPpyq84=_@";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	// alert(Ext.encode(data));
	src += "/commform/commpages/comm/orisource.jsp?opseno=" + cae001;
	// alert(src);
	parent.doOpenPopWindow(src, data.title, 810, 500);
	/*
	 * openWin.setTitle(data.title); openWin.setSize(800,450); //宽度 高度 if
	 * (document.all("iframe_SbdnNoticeWindow") == null) {
	 * //openWin.resizable=false;
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src);
	 * //window.setTimeout("Ext.getCmp('SbdnNoticeWindow').setDisabled(true)",1000);
	 * //openWin.disabledClass = "x-item-disabled";
	 * 
	 * \*if(cueRecord.get('btype')=='46'){ //人员缴费工资申报 src +=
	 * '/pages/paymentwagedeclare/paymentWageDeclareWindowList.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('工资上报信息'); openWin.setSize(500,130); //宽度 高度
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else
	 * if(cueRecord.get('btype')=='44'){ //人员续保申报 src +=
	 * '/pages/resafetydeclare/resafetydeclareListNew.jsp?cae001='+cae001;
	 * openWin.setTitle('人员续保编辑窗口'); openWin.setSize(510,420); //宽度 高度
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else
	 * if(cueRecord.get('btype')=='43'){ //人员新参保申报 src +=
	 * '/pages/joinsafetydeclare/joinsafetydeclareListNew.jsp?cae001='+cae001;
	 * openWin.setTitle('人员新增编辑窗口'); openWin.setSize(520,360); //宽度 高度
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else{ var
	 * url=contextPath+"/business/netPreAuditAction.do?method=getOriSource&cae001="+cae001;
	 * if(isFirst == true){ odin.showWindowWithSrc('detailWindow',url); }
	 * isFirst = false; odin.showWindowWithSrc('detailWindow',url); }*\
	 */
}
/** *************界面刷新********************* */
function breakShale() {
	doLoadGridData(null, 1);
	doGetAllStatusCount();
}
/** **************时间显示处理******************************** */
function renderDate() {
	if (value != null) {
		var bb = value + "";
		value = bb.substring(0, 4) + "-" + bb.substring(4, 6) + "-"
				+ bb.substring(6, 8);
		return value;
	} else {

		return value;
	}
}
/**
 * 获取通知公告信息，并对通知公告区域的内容重写
 */
function getCpSbdnNoticeContent() {
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_76tJBvkT+4VXM09mAi+FHplVviSz04f0L1f4k1tehGibA5O9PJtpFvoqIEWd2C4nObpwOl+qskXH8NC/5nNQ9UHnSNIM3T+LBC+tVNWyg/WQfU8un2JAMQ==_@"
			+ username
			+ "@_+/1CNNzAsS7oIACXTkPyOaKpMbtf8aPEJOv4JVIEm9PYCQ5n6/RY/0rbODztqoUYE5hNeA5aNGxt7ihPbL9IEvQ4frfjy96qkqrMXdNDk2tH2T0CsNKb1RB8KLdO3gddMJ8Cs+5o8XozSLb0KPX4PAwNiFpQPH7Ny9X8wdHLTUc6W1F2L3OEsgMDevAZGIgyUi7yyvLOHnk=_@"
			+ username
			+ "@_NFxbv1ZPCBz2zrOqpe9ER87MI7MAdWLLbF4TVayOqjsVoUfC9QCMTh3AtKB7pp9XjnQzzhpcby7G5jjjSMu6pA==_@";
	var data = odin.ext.decode(odin.commonQuery(params, odin.ajaxSuccessFunc,
			null, false, false).responseText).data.data;
	for (i = 0; i < data.length; i++) {
		var obj = data[i];
		/*
		 * if(obj.alltitle.length>30){ data[i].title =
		 * obj.alltitle.substring(0,30)+"…";
		 * 
		 * }else{ var len=obj.alltitle.length; var space=""; for(m=len;m<33;m++){
		 * space=space+"."; } space=space+obj.time; data[i].title =
		 * obj.alltitle+space; data[i].date = obj.time; }
		 */

		if (obj.isnew != "1") {
			obj.isnewimg = '<img src=' + contextPath + '/img/new.gif>'
		}
		data[i].title = obj.alltitle;
		data[i].date = obj.time;
	}
	SbdnNoticeTpl.overwrite(odin.ext.get('c_right_content'), data);
}
function doOpenMoreCpSbdnNotice() {
	parent.doOpenPopWindow('/notice/notice.jsp?username=' + username, '所有私有通知',
			840, 500);
}
function detailPreAudit3(value) {
	var src = contextPath;
	var cae001 = value; // 操作序列号
	var openWin = odin.ext.getCmp('SbdnNoticeWindow');
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5WlsD0wAN7ei3Rzo/V+uWBdpsklrVM1bzKxmn/YItH3cMluOAme1fMMPsPcdF3TG+FmhKQpTrVaN6FsJSiO3LOot1qYsRlLIzefW6D9kAfQp8fTGbdxLtCWqgfZ7UtTudzwxBrQCpD5dg==_@"
			+ cae001 + "@_ApaZmPpyq84=_@";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	// alert(Ext.encode(data));
	if(data.location.indexOf('pages/')>0){
		src += "/commform/commpages/comm/orisource.jsp?opseno=" + cae001;
	}else{
		src += "/radow/orisource.jsp?opseno=" + cae001;
	}
	parent.doOpenPopWindow(src, data.title, 810, 500);
}
function doOpenEditWindow1(value) {
	var src = contextPath;
	var cae001 = value
	var openWin = odin.ext.getCmp('SbdnNoticeWindow');
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5WlsD0wAN7ei3Rzo/V+uWBdpsklrVM1bzKxmn/YItH3cMluOAme1fMMPsPcdF3TG+FmhKQpTrVaN6FsJSiO3LOot1qYsRlLIzefW6D9kAfQp8fTGbdxLtCWqgfZ7UtTudzwxBrQCpD5dg==_@"
			+ cae001 + "@_ApaZmPpyq84=_@";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	// alert(Ext.encode(data));
	src += data.location + "&opseno=" + cae001;
	parent.doOpenPopWindow(src, data.title, 810, 500);
}
function init() {
	var time = new Date().format('Ym');
	document.all.ywym.value = time;
	if (rate == 7) {
		document.all.aaz001.value = aaz001;
		document.getElementById("hide").style.display = "none";
	} else {
		document.all.aaz001.value = 0;
	}
	getQueryTypeFilterStr(3);
}

/**
 * 基数申报用的操作
 */
function doWelcomeJssbUserOperation() {
	if (!parent.isJssbUser()) {
		return;
	}
	document.getElementById('font_right_bady3').innerHTML = "欢迎使用网上基数申报！";
	// document.getElementById('func').style.visibility = "hidden";
	var func = getFrame('funcFrame');
	func.document.getElementById('a1-i').style.visibility = "hidden";
	func.document.getElementById('a2-i').style.visibility = "hidden";
	func.document.getElementById('a3-i').style.visibility = "hidden";
	// 修改index界面信息
	parent.document.getElementById('helpHref').href = "jssbhelp.doc";
	// document.getElementById('helpLink').style.visibility = "true";
	parent.document.getElementById('li_draft').style.visibility = "hidden";

}
/**
 * 个人账户查询
 */
function doWelcomeGrzhUserOperation() {
	if (!parent.isGrzhUser()) {
		return;
	}
	document.getElementById('font_right_bady3').innerHTML = "欢迎使用个人账户查询系统！";
	// document.getElementById('func').style.visibility = "hidden";
	var func = getFrame('funcFrame');
	func.document.getElementById('a1-i').style.visibility = "hidden";
	func.document.getElementById('a2-i').style.visibility = "hidden";
	func.document.getElementById('a3-i').style.visibility = "hidden";
	func.document.getElementById('a4-i').style.visibility = "hidden";
	// 修改index界面信息
	parent.document.getElementById('helpHref').href = "loginhelpgrzh.jsp";
	// document.getElementById('helpLink').style.visibility = "true";
	parent.document.getElementById('li_draft').style.visibility = "hidden";
	parent.document.getElementById('li_welcome').style.visibility = "hidden";
	parent.document.getElementById('li_content').style.visibility = "hidden";
	parent.showDiv('content');
}
function checkSelect(objId) {
	var ret = checkSelectValueInList(objId);
	if (ret == true) {
		Ext.getCmp(objId + "_combo").checkSelectComplete = true;
		setValid(objId, true);
		return true;
	} else {
		var combo = Ext.getCmp(objId + "_combo");
		var value = document.getElementById(objId + "_combo").value;
		if (combo.mode == "remote" && combo.lastQuery != value) {
			if (combo.checkSelectComplete == null || combo.checkSelectComplete == true) {// 已经在执行了则不再执行
				combo.store.on('load', storeRemoteOnLoad = function(ds) {
							odin.comboBlur(combo);
							combo.triggerBlur();
							combo.store.un("load", storeRemoteOnLoad);
						});
				window.setTimeout("Ext.getCmp('" + objId + "_combo').doQuery('" + value + "', true)");
			}
			combo.checkSelectComplete = false;
			return false;
		} else {
			combo.checkSelectComplete = true;
		}
		setValid(objId, false, ret);
		return false;
	}
}
function initSelect(name){
	if (Ext.getCmp(name + "_combo")) {
		if (checkSelect(name) == false) {
			// alert(11);
			return;
		}
		value = document.getElementById(name).value;
		// alert(value);
	}
}

/**
 * 按系统按辖区的特殊操作
 */
function doWelcomeSacodeOperation() {
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_PSItqiEKenPuR+wLKCzFiLo1qTQsOhP12jzDqbK9jZeARJhLXZZBCDacNa1fxdBF2VM5cCy5esU=_@";
	var req = odin
			.commonQuery(params, odin.ajaxSuccessFunc, null, false, false);
	document.getElementById('font_right_bady3').innerHTML = odin.ext.decode(req.responseText).data.data[0].aaa005;
}

/**
 * 按系统按辖区的特殊操作
 */
function doWelcomeAab301Operation() {
	if (business_xt == "ZS") { // 舟山
		document.getElementById('font_right_bady3').innerHTML = "1、登录网上申报系统后，就可以进行网上申报操作，网上申报的所有业务操作都将先保存到我的文档里的草稿箱中。<br>"
				+ "2、在我的文档里的草稿箱中可以进行数据再次删除或修改，确认后进行发送，申报发送后，不允许再对已发送业务进行修改。<br>"
				+ "3、申报发送完成后，社保系统会对数据进行审核，并会尽快返回审核结果，可在我的文档中查看审核情况。<br>"
				+ "4、申报日期为工作日，请勿在公休日操作。对已经申报发送且审核通过的业务需作废的，请到社保业务大厅办理。<br>"
				+ "5、如果Flash Player版本过低而出现打印出错请下载<a href='http://www.onlinedown.net/soft/14968.htm' TARGET='_blank'>FlashPlayer 10</a>";
		if(rate!='7'){ //非单位
			document.getElementById('func').style.display='none';
		}else{
			var func = getFrame('funcFrame');
			func.document.getElementById('a2-i').href = "javascript:addTab('人员续保','2881a0071c1b5c42011c1b70dbb30004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.Psxb');";
		}
		parent.document.getElementById('helpHref').href = "help.doc";
	} else if (business_xt == "SX") { // 绍兴
		document.getElementById('font_right_bady3').innerHTML = "1、登录社会保险申报系统后，就可以进行社会保险申报操作，申报的所有业务操作都将先保存到我的文档里的草稿箱中。<br>"
				+ "2、在我的文档里的草稿箱中可以进行数据再次删除或修改，确认后进行发送，申报发送后，不允许再对已发送业务进行修改。<br>"
				+ "3、申报发送完成后，县社保局会对数据进行审核，并会尽快返回审核结果，可在我的文档中查看审核情况。<br>"
				+ "4、对已经申报发送且审核通过的业务需作废的，请到社保业务大厅办理。<br>"
		var func = getFrame('funcFrame');
		func.document.getElementById('a1-i').href = "javascript:addTab('人员新增','8a8a8a812143130d012143186f000002',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin');";
		func.document.getElementById('a2-i').href = "javascript:addTab('人员续保','8a8a8a812156e491012156e63b3a0002',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin-2');";
		func.document.getElementById('a3-i').href = "javascript:addTab('人员中断','8a8a8a81215917e60121591941ee0003',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin-1');";
		// func.document.getElementById('a4-i').href =
		// "javascript:addTab('基数申报录入','2881a0071f841284011f841894500026',top.contextPath+'/pages/commAction.do?method=sqsb.psrewage.JsInput');";
		func.document.getElementById('a4-i').style.visibility = "hidden";
		parent.document.getElementById('helpHref').href = "help.doc";
	} else { // 其他，默认
		document.getElementById('font_right_bady3').innerHTML = "1、登录网上申报系统后，就可以进行网上申报操作，网上申报的所有业务操作都将先保存到我的文档里的草稿箱中。<br>"
				+ "2、在我的文档里的草稿箱中可以进行数据再次删除或修改，确认后进行发送，申报发送后，不允许再对已发送业务进行修改。<br>"
				+ "3、申报发送完成后，社保系统会对数据进行审核，并会尽快返回审核结果，可在我的文档中查看审核情况。<br>"
				+ "4、对已经申报发送且审核通过的业务需作废的，请到社保业务大厅办理。<br>"
				+ "5、如果Flash Player版本过低而出现打印出错请下载<a href='http://www.onlinedown.net/soft/14968.htm' TARGET='_blank'>FlashPlayer 10</a>";
		// document.getElementById('func').style.visibility = "hidden";
		var func = getFrame('funcFrame');
		func.document.getElementById('a1-i').href = "javascript:addTab('人员新增','402880e51b0155ca011b016bb9f20005',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin');";
		func.document.getElementById('a2-i').href = "javascript:addTab('人员续保','2881a0071c1b5c42011c1b70dbb30004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin-1');";
		func.document.getElementById('a3-i').href = "javascript:addTab('人员中断','2881a0071c02094e011c02c3801c0004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.PauseJoin');";
		func.document.getElementById('a4-i').href = "javascript:addTab('基数申报录入','2881a0071f841284011f841894500026',top.contextPath+'/pages/commAction.do?method=wssb.psrewage.JsInput');";
	}
}
function getFrame(id){
	return document.getElementById(id).contentWindow || document.frames[id];
}
