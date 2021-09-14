package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class UserInfoGroup implements Serializable {
	private java.lang.String userinfogroupid;
	private java.lang.String userid;
	private java.lang.String infogroupid;
	private java.lang.String type;

   

	public java.lang.String getUserinfogroupid() {
		return userinfogroupid;
	}



	public void setUserinfogroupid(java.lang.String userinfogroupid) {
		this.userinfogroupid = userinfogroupid;
	}



	public java.lang.String getUserid() {
		return userid;
	}



	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}



	public java.lang.String getInfogroupid() {
		return infogroupid;
	}



	public void setInfogroupid(java.lang.String infogroupid) {
		this.infogroupid = infogroupid;
	}



	public java.lang.String getType() {
		return type;
	}



	public void setType(java.lang.String type) {
		this.type = type;
	}



	public UserInfoGroup() {
    }




}
