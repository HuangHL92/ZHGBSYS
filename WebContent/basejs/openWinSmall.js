/**
 * ���ӵ������� ȥ�����
 */
function doOpenPupWinSmall(src, title, width, height, initParams, parentWin) {
	if (parentWin == null) {
		parentWin = this;
	}
	var tempParentWin = parentWin;
	var windowId = "win_pup";
	var pupWindow = Ext.getCmp(windowId);
	var expReadyState = ""; // ����ʱ��ʼ��
	pupWindow.setTitle(title); // ����
	if (width <= 1) {
		if (width >= 0) {// С��
			width = document.body.clientWidth * width;
		} else { // ����
			width = document.body.clientWidth + width;
		}
	}
	if (height <= 1) {
		if (height >= 0) {// С��
			height = document.body.clientHeight * height;
		} else { // ����
			height = document.body.clientHeight + height;
		}
	}
	pupWindow.setSize(width, height); // ��� �߶�
	showWindowWithSrcSmall(windowId, src, initParams);
	pupWindow.center(); // ����
}

/**
 * ���ݴ���ĵ�ַ��ʾ����
 */
function showWindowWithSrcSmall(windowId, newSrc, initParams) {
	if (newSrc.substring(0, 1) == "&") {
		newSrc = addUrlParamSmall(window.location.href, newSrc);
	}
	if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
		newSrc = contextPath + newSrc;
	}
	if (initParams != null) {
		newSrc = addUrlParamSmall(newSrc, "initParams=" + encodeURIComponent(initParams));
		commParams.initParams = initParams;
		commParams.initParamsUsedBy = null;
	}
	newSrc = addUrlParamSmall(newSrc, "clientDate=" + Ext.util.Format.date(new Date(), "YmdHis"));// ʹÿ�����󶼲�һ�����������һ���򲻿�������
	// alert(newSrc);
	if (document.getElementById("iframe_" + windowId)) {
		try {
			getIFrameWin("iframe_" + windowId).odin.mask("����ˢ��...");
		} catch (e) {
		}
		document.getElementById("iframe_" + windowId).src = newSrc;
	} else {
		Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\"></iframe>";
	}
	Ext.getCmp(windowId).maximizable = false;
	Ext.getCmp(windowId).show(Ext.getCmp(windowId));
	Ext.getCmp(windowId).focus();
}

/**
 * ��url�Ӳ���
 * @param {} url ԭʼurl
 * @param {} paramUrl ����url���� &a=1&b=2
 * @return {} ����ֵ
 */
function addUrlParamSmall(url, paramUrl) {
	var paramArray = paramUrl.split("&");
	var newUrl = url.replace("#", "");
	for (var i = 0; i < paramArray.length; i++) {
		var param = paramArray[i];
		if (param.indexOf("=") != -1) {
			var paramName = param.substring(0, param.indexOf("=")).trim();
			if (newUrl.indexOf(paramName + "=") != -1) {
				var re = eval('/(' + paramName + '=)([^&]*)/gi');
				newUrl = newUrl.replace(re, param);
			} else {
				if (newUrl.indexOf("?") != -1) {
					newUrl = newUrl + "&" + param;
				} else {
					newUrl = newUrl + "?" + param;
				}
			}
		}
	}
	return newUrl;
}

/**
 * ����IE��Firefox��iframe���ڻ�ȡ����
 */
function getIFrameWin(id) {
	return document.getElementById(id).contentWindow || document.frames[id];
}