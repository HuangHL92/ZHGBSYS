var cue_content_tab = 0;
var records = "";
var SQL = "";
/**
 * �ݸ��䡢������˵ȵ��л�
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
 * ��ȡ֪ͨ������Ϣ������֪ͨ���������������д
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
		 * obj.alltitle.substring(0,30)+"��";
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
 * �ݸ�״̬�Ĵ���
 * 
 * @param {Object}
 *            value
 */
function renderDraftStatus(value) {
	if (value == 1) {
		return "<img src=\"../commform/img/right.gif\" alt=\"�����걨\" width=\"14\" height=\"14\">";
	} else {
		return "<img src=\"../commform/img/wrong.gif\" alt=\"��ʽ����\" width=\"14\" height=\"14\">";
	}
}

/**
 * �����ʾ��Ч��
 * 
 * @param {Object}
 *            value
 */
function renderAlt(value) {
	return '<a title=' + value + '>' + value + '</a>';
}
/**
 * ��ʾɾ������
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
	return "<a href='javascript:void(0)' onclick='tishiDeleteDraft(\"" + value + "\")'>ɾ��</a>";
}
function renderInfoDraft(value, params, record, rowIndex, colIndex, ds) {
	return "<a href='javascript:void(0)' onclick='detailPreAudit3(\"" + value + "\")'>��ϸ��Ϣ</a>";
}

function renderEditDraft(value, params, record, rowIndex, colIndex, ds) {
	return "<a href='javascript:void(0)' onclick='doOpenEditWindow1(\"" + value + "\")'>�޸�</a>";
}
/**
 * ɾ���ݸ�
 * 
 * @param {Object}
 *            id
 */
var cueID = "";
function tishiDeleteDraft(id) {
	cueID = id;
	odin.confirm("��ȷ��Ҫɾ�������ݸ���", deleteDraft);
}
function deleteDraft(btn) {
	if (btn == 'ok') {
		// alert("ɾ��idΪ"+cueID+"�Ĳݸ壡");
		// ����ɾ������
		var ywlx = document.all.businessType.value;
		var aaz001 = document.all.aaz001.value;
		var params = {};
		params.id = cueID;
		params.ywlx = ywlx;
		params.aaz001 = aaz001;
		params.username= username;
		var req = odin.Ajax.request(contextPath
						+ '/pages/draft/draftAction.do?method=deleteDraftById',
				params, odin.ajaxSuccessFunc, null, false, true); // ����ͬ����ʽɾ��
		odin.ext.getCmp('grid0').getSelectionModel().clearSelections(); // ���ѡ�ֵ���
		// ɾ���ɹ������¸�����������
		doLoadGridData(null, 1);
		doGetAllStatusCount();
		var response = odin.ext.decode(req.responseText);
		// odin.info(response.mainMessage);
	}
}
/**
 * ѡ�еĲݸ������ɾ��
 */
function selectDeleteDraft() {
	var gird = odin.ext.getCmp('grid0');
	var dataArray = gird.getSelectionModel().getSelections();
	if (gird.getBottomToolbar().pageSize == dataArray.length) {
		// ˵����ȫѡ
		cueID = "all";
		odin.confirm("ȷ��Ҫ��ȫ���ݸ���Ϣ������ȫ��ҳ������ɾ����", deleteDraft); // ȫѡɾ��
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
					"��ѡ��Ĳݸ��������д������ݣ�\n �����ѡ��\"ȷ��\"ϵͳ���Զ����˵���Щ���ݲ������걨���������걨��",
					deleteDraft);
		} else {
			odin.confirm("��ȷ��Ҫɾ��ѡ��������", deleteDraft);
		}
	}
}
/**
 * ȫѡ
 */
function selectall() {
	if (Ext.getCmp('grid0').getStore().getCount() > Ext.getCmp('grid0')
			.getSelectionModel().getCount()) {// δȫѡʱ����ȫѡ
		Ext.getCmp('grid0').getSelectionModel().selectAll();
	} else { // ȫ��
		Ext.getCmp('grid0').getSelectionModel().clearSelections();
	}

}
/**
 * ���δ�ɹ��������ϱ�����ʾ
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
// return "<a href='#' >�޸�</a>";
// }
var cueDeclareIds = "";
var cueDeclareHashs = "";
/**
 * �걨����
 */

function doDeclare() {
	// odin.alert("ҵ�񾭰����ֻ����������ȷ!");
	var gird = odin.ext.getCmp('grid0');
	// �����걨�������ڲݸ�����Ĳ����걨
	var dataArray = gird.getSelectionModel().getSelections();
	if (gird.getBottomToolbar().pageSize == dataArray.length) {
		// ˵����ȫѡ
		cueDeclareIds = "all";
		odin.confirm("ȷ��Ҫ��ȫ���ݸ���Ϣ������ȫ��ҳ�������걨��", isdeclare); // ȫѡ�걨
		return;
	}
	if (dataArray.length > 0) {
		// �걨
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
					"��ѡ��Ĳݸ��������д������ݣ�\n �����ѡ��\"ȷ��\"ϵͳ���Զ����˵���Щ���ݲ������걨���������걨��",
					isdeclare);
		} else {
			odin.confirm("�Ƿ�ȷ�������걨ѡ�е����ݣ�", isdeclare); // �걨
		}

	} else {
		odin.info('��ѡ��Ҫ�걨�ļ�¼�ٵ��걨��');
	}
}

function isdeclare(btn) {
	if (btn == 'ok') {
		declare();
	}
}

function declare() { // �걨����
	// �����걨
	var params = {};
	var ywlx = document.all.businessType.value;

	var sign = "";
	var aaz001 = document.all('aaz001').value;// /��λ����
	if (isNeedSign == true && cueDeclareIds == 'all') { // ������װid

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
	if (isNeedSign == true) { // Ҫ����ǩ��
		var ret = dosign(cueDeclareHashs);
		if (ret.flag == false) {
			return;
		}
		params.signdata = ret.signdata;
	}
	var req = odin.Ajax.request(contextPath
					+ '/pages/draft/draftAction.do?method=declareDraftById',
			params, odin.ajaxSuccessFunc, null, false, true); // ����ͬ����ʽ�걨
	odin.ext.getCmp('grid0').getSelectionModel().clearSelections(); // ���ѡ�ֵ���
	// �������Բݸ���ı��������¼�������
	doLoadGridData(0, 1);
	doGetAllStatusCount();// ���´���״̬���֣���ݸ����ж��ٱ�ҵ��
}

// ��������ǩ��
function dosign(data) {
	var signStr = "";
	var result = {};
	var cert = signature.getCertificate();

	if (cert.flag == false) {
		odin.error('δ�ҵ��䷢������ǩ��֤�飬���޷��걨ҵ��!');
		result.flag = false;
		return result;
	}
	if (!signature.checkDate(cert)) {
		odin.error('��������֤���Ѿ����ڣ��޷��걨ҵ��!');
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
 * ����֪ͨid��ȡ֪ͨ���ݣ�����������ʾ�ڴ�����
 * 
 * @param {Object}
 *            id
 */
function doShowSbdnNotice(id) {
	// �����ݿ��ȡ����

	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_76tJBvkT+4WzUP/pBag8S7HH5XVSon/QvWqrSvUPkhxveszAV4/ovg==_@"
			+ id + "@_mMxQSO+LfFw=_@";

	var content = odin.ext.decode(odin.commonQuery(params,
			odin.ajaxSuccessFunc, null, false, false).responseText).data.data[0].rcontent;
	// ����������������

	var SbdnNoticeWindow = odin.ext.getCmp('SbdnNoticeWindow');
	SbdnNoticeWindow.setTitle('֪ͨ����');
	SbdnNoticeWindow.setSize(500, 350); // ��� �߶�
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
 * �򿪸�������Ӧ��tabҳ
 */
function doOpenMoreSbdnNotice() {
	parent.doOpenPopWindow('/notice/notice.jsp', '���й���֪ͨ', 840, 500);
	// if (document.all("iframe_moreSbdnNoticeWindow") == null) {
	// odin.showWindowWithSrc('moreSbdnNoticeWindow','SbdnNotice/SbdnNotice.jsp');
	// }
	// odin.showWindowWithSrc('moreSbdnNoticeWindow','SbdnNotice/SbdnNotice.jsp');
	// Ext.getCmp('moreSbdnNoticeWindow').maximize();
	// parent.openUrl('����֪ͨ','allSbdnNotice','SbdnNotice/SbdnNotice.jsp');
	// parent.openUrl('��Ա����','4028c71b1956608101195678303d0008','/pages/joinsafetydeclare/joinsafetydeclareAction.do');
}

function doOpenmanadd() {
	parent.openUrl('��Ա����', '4028c71b1956608101195678303d0008',
			'/pages/joinsafetydeclare/joinsafetydeclareAction.do');
}
/**
 * ���ر������ index����Ϊ�գ�����ʾ������� type==1 ��ʾ��ǰ�����Ͳ� type == 2 ��ʾ��ǰ�ǰ�����ͬʱ���ʱ��� type == 3
 * ��ʾ��ǰ�ǰ��߼��������ʱ��ͬʱ������Ͳ�
 */
function doLoadGridData(index, type) {

	var aaz001 = document.all('aaz001').value;// /��λ����
	if (index == 'undefined' || index == null) {
		index = cue_content_tab;
	}
	var params = {};
	if (index == 0) {// ���زݸ����������
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.sbzt='01'  and viewdraft='0'";
	} else if (index == 1) {// ����������˱�������
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and (a.sbzt='11' or a.sbzt='30') and viewdraft='0'";
	} else if (index == 2) {// ������˳ɹ���������
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.sbzt='41' and viewdraft='0'";
	} else if (index == 3) {// ������˲��ɹ���������
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,a.sbzt as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,a.digest as notice,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and (a.sbzt='42' or a.sbzt='99') and viewdraft='0'";
	} else if (index == 4) {// �������е��걨����
		params.querySQL = "select rownum as id,a.aaz157 as aac001,a.opseno as bcode,a.aab001,(select aab004 from ab01 where aab001=a.aab001) aab004,decode(a.sbzt,'99','42',a.sbzt) as draftstatus,a.ywlx as btype,a.sbzt as declarestatus,a.czcode as lookperson,to_char(a.czdate,'yyyy-mm-dd hh24:mi:ss') as lookdate,to_char(a.aae036,'yyyy-mm-dd hh24:mi:ss') as badddate,a.digest as binfo,'��ϸ��Ϣ' as info,'' hashcode  from sbds_userlog a,sbdn_ywlxpz b where a.ywlx=b.ywlx and a.opseno is not null and viewdraft='0'";
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
 * ��ȡ����״̬��Ӧ�ļ�¼���������޸�ҳ����صط�
 */
function doGetAllStatusCount() {
	var aaz001 = document.all('aaz001').value;// /��λ����

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
	// ��ȡ�������ݷֱ����Ӧҳ���������ȥ
	var draftTotalCount = 0; // �ݸ���
	var declareSpan1 = 0; // �������
	var declareSpan2 = 0; // ��˳ɹ�
	var declareSpan3 = 0; // ��˲��ɹ�
	var total = 0; // ȫ��
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
	// alert("�ݸ���"+draftTotalCount+"�������"+declareSpan1+"��˳ɹ�"+declareSpan2+"��˲��ɹ�"+declareSpan3+"ȫ��"+total);
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
var queryDateFilterStr = ""; // ��ѯʱ��ʱ���һ������
var queryTypeFilterStr = ""; // ��ѯʱ�����͵�һ������
var queryByAdvancedSearch = "";// �߼�����
var queryDateFilterStrtmp = "";// ʱ����ʱ�滻
/**
 * �γɸ���ʱ���ҵ�����͵Ĳ�ѯ������������ѡ�����¼�������
 * 
 * @param {Object}
 *            type
 */
function getQueryTypeFilterStr(type) { // type==1 ������ type==2��������
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
			alert("ҵ������λ������");
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
 * �γɸ���ҵ�����͵Ĳ�ѯ������������ѡ�����¼�������
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
	if (type != "") { // ���Ͳ�ѡ�Ļ�������������
		doLoadGridData(null, 1);
	}
}
/**
 * ����ݸ����˫���༭���� jinwei
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
	var aac001 = cueRecord.get('aac001'); // ��λ����Ա����
	var cae001 = cueRecord.get('bcode'); // �������к�
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
	 * //��� �߶� \* if(cueRecord.get('btype')=='42'){ //��Ա������Ϣ�޸� src +=
	 * '/pages/personbaseinfomodify/personbaseinfomodifywindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('��Ա������Ϣ����༭����'); openWin.setSize(505,380); //��� �߶� }else
	 * if(cueRecord.get('btype')=='43'){ //��Ա�²α��걨 src +=
	 * '/pages/joinsafetydeclare/joinsafetydeclarelistAction.do?cae001='+cae001;
	 * openWin.setTitle('��Ա�����༭����'); openWin.setSize(520,360); //��� �߶� }else
	 * if(cueRecord.get('btype')=='45'){ //��Ա�ж��걨 src +=
	 * '/pages/safetyintermitdeclare/safetyintermitdeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('��Ա�жϱ༭����'); openWin.setSize(505,380); //��� �߶� }else
	 * if(cueRecord.get('btype')=='48'){ //��Ա���ּ��� src +=
	 * '/pages/safetyreducedeclare/safetyreducedeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('��Ա���ּ��ٱ༭����'); openWin.setSize(510,410); //��� �߶� }else
	 * if(cueRecord.get('btype')=='47'){ //��Ա�������� src +=
	 * '/pages/safetyadddeclare/safetyadddeclarewindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('��Ա�������ӱ༭����'); openWin.setSize(500,345); //��� �߶� }else
	 * if(cueRecord.get('btype')=='44'){ //��Ա�ٲα��걨 src +=
	 * '/pages/resafetydeclare/resafetydeclareListAction.do?cae001='+cae001;
	 * openWin.setTitle('��Ա�����༭����'); openWin.setSize(510,420); //��� �߶� }else
	 * if(cueRecord.get('btype')=='46'){ //��ȹ����ϱ� src +=
	 * '/pages/paymentwagedeclare/paymentWageDeclareWindow.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('��ȹ����ϱ��༭����'); openWin.setSize(500,200); //��� �߶� }else
	 * if(cueRecord.get('btype')=='41'){ //��λ������Ϣ��� src +=
	 * '/pages/companybaseinfomodify/companybaseinfomodifyAction.do?cae001='+cae001;
	 * openWin.setTitle('��λ������Ϣ����༭����'); openWin.setSize(800,380); //��� �߶� } \
	 * if (document.all("iframe_SbdnNoticeWindow") == null) {
	 * openWin.resizable=false; odin.showWindowWithSrc('SbdnNoticeWindow',src); }
	 * openWin.resizable=false; odin.showWindowWithSrc('SbdnNoticeWindow',src);
	 */
}

/** ********���滹ԭ***************** */
var isFirst = true;
function detailPreAudit2(grid, rowIndex, event) {
	var store = grid.store;
	var cueRecord = store.getAt(rowIndex);
	var src = contextPath;
	var aac001 = cueRecord.get('aac001'); // ��λ����Ա����
	var cae001 = cueRecord.get('bcode'); // �������к�
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
	 * openWin.setTitle(data.title); openWin.setSize(800,450); //��� �߶� if
	 * (document.all("iframe_SbdnNoticeWindow") == null) {
	 * //openWin.resizable=false;
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src);
	 * //window.setTimeout("Ext.getCmp('SbdnNoticeWindow').setDisabled(true)",1000);
	 * //openWin.disabledClass = "x-item-disabled";
	 * 
	 * \*if(cueRecord.get('btype')=='46'){ //��Ա�ɷѹ����걨 src +=
	 * '/pages/paymentwagedeclare/paymentWageDeclareWindowList.jsp?aac001='+aac001+"&cae001="+cae001;
	 * openWin.setTitle('�����ϱ���Ϣ'); openWin.setSize(500,130); //��� �߶�
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else
	 * if(cueRecord.get('btype')=='44'){ //��Ա�����걨 src +=
	 * '/pages/resafetydeclare/resafetydeclareListNew.jsp?cae001='+cae001;
	 * openWin.setTitle('��Ա�����༭����'); openWin.setSize(510,420); //��� �߶�
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else
	 * if(cueRecord.get('btype')=='43'){ //��Ա�²α��걨 src +=
	 * '/pages/joinsafetydeclare/joinsafetydeclareListNew.jsp?cae001='+cae001;
	 * openWin.setTitle('��Ա�����༭����'); openWin.setSize(520,360); //��� �߶�
	 * odin.showWindowWithSrc('SbdnNoticeWindow',src); }else{ var
	 * url=contextPath+"/business/netPreAuditAction.do?method=getOriSource&cae001="+cae001;
	 * if(isFirst == true){ odin.showWindowWithSrc('detailWindow',url); }
	 * isFirst = false; odin.showWindowWithSrc('detailWindow',url); }*\
	 */
}
/** *************����ˢ��********************* */
function breakShale() {
	doLoadGridData(null, 1);
	doGetAllStatusCount();
}
/** **************ʱ����ʾ����******************************** */
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
 * ��ȡ֪ͨ������Ϣ������֪ͨ���������������д
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
		 * obj.alltitle.substring(0,30)+"��";
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
	parent.doOpenPopWindow('/notice/notice.jsp?username=' + username, '����˽��֪ͨ',
			840, 500);
}
function detailPreAudit3(value) {
	var src = contextPath;
	var cae001 = value; // �������к�
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
 * �����걨�õĲ���
 */
function doWelcomeJssbUserOperation() {
	if (!parent.isJssbUser()) {
		return;
	}
	document.getElementById('font_right_bady3').innerHTML = "��ӭʹ�����ϻ����걨��";
	// document.getElementById('func').style.visibility = "hidden";
	var func = getFrame('funcFrame');
	func.document.getElementById('a1-i').style.visibility = "hidden";
	func.document.getElementById('a2-i').style.visibility = "hidden";
	func.document.getElementById('a3-i').style.visibility = "hidden";
	// �޸�index������Ϣ
	parent.document.getElementById('helpHref').href = "jssbhelp.doc";
	// document.getElementById('helpLink').style.visibility = "true";
	parent.document.getElementById('li_draft').style.visibility = "hidden";

}
/**
 * �����˻���ѯ
 */
function doWelcomeGrzhUserOperation() {
	if (!parent.isGrzhUser()) {
		return;
	}
	document.getElementById('font_right_bady3').innerHTML = "��ӭʹ�ø����˻���ѯϵͳ��";
	// document.getElementById('func').style.visibility = "hidden";
	var func = getFrame('funcFrame');
	func.document.getElementById('a1-i').style.visibility = "hidden";
	func.document.getElementById('a2-i').style.visibility = "hidden";
	func.document.getElementById('a3-i').style.visibility = "hidden";
	func.document.getElementById('a4-i').style.visibility = "hidden";
	// �޸�index������Ϣ
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
			if (combo.checkSelectComplete == null || combo.checkSelectComplete == true) {// �Ѿ���ִ��������ִ��
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
 * ��ϵͳ��Ͻ�����������
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
 * ��ϵͳ��Ͻ�����������
 */
function doWelcomeAab301Operation() {
	if (business_xt == "ZS") { // ��ɽ
		document.getElementById('font_right_bady3').innerHTML = "1����¼�����걨ϵͳ�󣬾Ϳ��Խ��������걨�����������걨������ҵ����������ȱ��浽�ҵ��ĵ���Ĳݸ����С�<br>"
				+ "2�����ҵ��ĵ���Ĳݸ����п��Խ��������ٴ�ɾ�����޸ģ�ȷ�Ϻ���з��ͣ��걨���ͺ󣬲������ٶ��ѷ���ҵ������޸ġ�<br>"
				+ "3���걨������ɺ��籣ϵͳ������ݽ�����ˣ����ᾡ�췵����˽���������ҵ��ĵ��в鿴��������<br>"
				+ "4���걨����Ϊ�����գ������ڹ����ղ��������Ѿ��걨���������ͨ����ҵ�������ϵģ��뵽�籣ҵ���������<br>"
				+ "5�����Flash Player�汾���Ͷ����ִ�ӡ����������<a href='http://www.onlinedown.net/soft/14968.htm' TARGET='_blank'>FlashPlayer 10</a>";
		if(rate!='7'){ //�ǵ�λ
			document.getElementById('func').style.display='none';
		}else{
			var func = getFrame('funcFrame');
			func.document.getElementById('a2-i').href = "javascript:addTab('��Ա����','2881a0071c1b5c42011c1b70dbb30004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.Psxb');";
		}
		parent.document.getElementById('helpHref').href = "help.doc";
	} else if (business_xt == "SX") { // ����
		document.getElementById('font_right_bady3').innerHTML = "1����¼��ᱣ���걨ϵͳ�󣬾Ϳ��Խ�����ᱣ���걨�������걨������ҵ����������ȱ��浽�ҵ��ĵ���Ĳݸ����С�<br>"
				+ "2�����ҵ��ĵ���Ĳݸ����п��Խ��������ٴ�ɾ�����޸ģ�ȷ�Ϻ���з��ͣ��걨���ͺ󣬲������ٶ��ѷ���ҵ������޸ġ�<br>"
				+ "3���걨������ɺ����籣�ֻ�����ݽ�����ˣ����ᾡ�췵����˽���������ҵ��ĵ��в鿴��������<br>"
				+ "4�����Ѿ��걨���������ͨ����ҵ�������ϵģ��뵽�籣ҵ���������<br>"
		var func = getFrame('funcFrame');
		func.document.getElementById('a1-i').href = "javascript:addTab('��Ա����','8a8a8a812143130d012143186f000002',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin');";
		func.document.getElementById('a2-i').href = "javascript:addTab('��Ա����','8a8a8a812156e491012156e63b3a0002',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin-2');";
		func.document.getElementById('a3-i').href = "javascript:addTab('��Ա�ж�','8a8a8a81215917e60121591941ee0003',top.contextPath+'/pages/commAction.do?method=sqsb.psdecl.NewJoin-1');";
		// func.document.getElementById('a4-i').href =
		// "javascript:addTab('�����걨¼��','2881a0071f841284011f841894500026',top.contextPath+'/pages/commAction.do?method=sqsb.psrewage.JsInput');";
		func.document.getElementById('a4-i').style.visibility = "hidden";
		parent.document.getElementById('helpHref').href = "help.doc";
	} else { // ������Ĭ��
		document.getElementById('font_right_bady3').innerHTML = "1����¼�����걨ϵͳ�󣬾Ϳ��Խ��������걨�����������걨������ҵ����������ȱ��浽�ҵ��ĵ���Ĳݸ����С�<br>"
				+ "2�����ҵ��ĵ���Ĳݸ����п��Խ��������ٴ�ɾ�����޸ģ�ȷ�Ϻ���з��ͣ��걨���ͺ󣬲������ٶ��ѷ���ҵ������޸ġ�<br>"
				+ "3���걨������ɺ��籣ϵͳ������ݽ�����ˣ����ᾡ�췵����˽���������ҵ��ĵ��в鿴��������<br>"
				+ "4�����Ѿ��걨���������ͨ����ҵ�������ϵģ��뵽�籣ҵ���������<br>"
				+ "5�����Flash Player�汾���Ͷ����ִ�ӡ����������<a href='http://www.onlinedown.net/soft/14968.htm' TARGET='_blank'>FlashPlayer 10</a>";
		// document.getElementById('func').style.visibility = "hidden";
		var func = getFrame('funcFrame');
		func.document.getElementById('a1-i').href = "javascript:addTab('��Ա����','402880e51b0155ca011b016bb9f20005',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin');";
		func.document.getElementById('a2-i').href = "javascript:addTab('��Ա����','2881a0071c1b5c42011c1b70dbb30004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.NewJoin-1');";
		func.document.getElementById('a3-i').href = "javascript:addTab('��Ա�ж�','2881a0071c02094e011c02c3801c0004',top.contextPath+'/pages/commAction.do?method=wssb.psdecl.PauseJoin');";
		func.document.getElementById('a4-i').href = "javascript:addTab('�����걨¼��','2881a0071f841284011f841894500026',top.contextPath+'/pages/commAction.do?method=wssb.psrewage.JsInput');";
	}
}
function getFrame(id){
	return document.getElementById(id).contentWindow || document.frames[id];
}
