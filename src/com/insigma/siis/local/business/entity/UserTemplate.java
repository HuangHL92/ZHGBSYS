package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class UserTemplate implements Serializable {

	public UserTemplate() {
	}
	/**
	 * ����
	 */
	private java.lang.String userTemplateId;
	
	/**
	 * ģ��ID
	 */
	private java.lang.String tpid;
	
	public java.lang.String getTpid() {
		return tpid;
	}
	public void setTpid(java.lang.String tpid) {
		this.tpid = tpid;
	}
	/**
	 * �û�ID 
	 */
	private java.lang.String userid;

	public java.lang.String getUserTemplateId() {
		return userTemplateId;
	}
	public void setUserTemplateId(java.lang.String userTemplateId) {
		this.userTemplateId = userTemplateId;
	}
	public java.lang.String getUserid() {
		return userid;
	}
	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}
}
























