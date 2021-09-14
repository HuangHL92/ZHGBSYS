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
public class InfoGroupInfo implements Serializable {
	private java.lang.String infogroupinfoid;
	private java.lang.String infoid;
	private java.lang.String infogroupid;
	

	public java.lang.String getInfogroupinfoid() {
		return infogroupinfoid;
	}


	public void setInfogroupinfoid(java.lang.String infogroupinfoid) {
		this.infogroupinfoid = infogroupinfoid;
	}


	public java.lang.String getInfoid() {
		return infoid;
	}


	public void setInfoid(java.lang.String infoid) {
		this.infoid = infoid;
	}


	public java.lang.String getInfogroupid() {
		return infogroupid;
	}


	public void setInfogroupid(java.lang.String infogroupid) {
		this.infogroupid = infogroupid;
	}


	public InfoGroupInfo() {
    }




}
