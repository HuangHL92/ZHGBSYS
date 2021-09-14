package com.insigma.siis.local.business.helperUtil;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class ReturnDO<T> implements Serializable {

	/**
	 * 处理返回信息结果集
	 */
	private static final long serialVersionUID = 1704541882494207704L;
	private String coder;
	private T obj;
	private String errorMsg;

	public ReturnDO() {
		this.coder = "000000";
	}

	public T getObj() {
		return this.obj;
	}

	public ReturnDO<T> setObj(T obj) {
		this.obj = obj;
		return this;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public String getCoder() {
		return this.coder;
	}

	public ReturnDO<T> setErrorMsg(String coder, String errorMsg) {
		this.coder = coder;
		this.errorMsg = errorMsg;
		return this;
	}

	public boolean isSuccess() {
		return "000000".equals(this.coder);
	}

	public String toString() {
		JSONObject toJsonString = new JSONObject();
		toJsonString.put("coder", this.coder);
		toJsonString.put("message", this.errorMsg);
		toJsonString.put("data", this.obj);
		return toJsonString.toString();
	}

}
