package com.insigma.siis.local.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class LogMain implements Serializable {
	private java.lang.String systemlogid;
	private java.lang.String userlog;
	private Date systemoperatedate;
	private java.lang.String eventtype;
	private java.lang.String eventobject;
	private java.lang.String objectid;
	private java.lang.String objectname;
	private java.lang.String operatecomments;
	private java.lang.String ip;
	private java.lang.String tableName;
	public java.lang.String getTableName() {
		return tableName;
	}
	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}
	public java.lang.String getIp() {
		return ip;
	}
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}
	public java.lang.String getSystemlogid() {
		return systemlogid;
	}
	public void setSystemlogid(java.lang.String systemlogid) {
		this.systemlogid = systemlogid;
	}
	public java.lang.String getUserlog() {
		return userlog;
	}
	public void setUserlog(java.lang.String userlog) {
		this.userlog = userlog;
	}
	public Date getSystemoperatedate() {
		return systemoperatedate;
	}
	public void setSystemoperatedate(Date date) {
		this.systemoperatedate = date;
	}
	public java.lang.String getEventtype() {
		return eventtype;
	}
	public void setEventtype(java.lang.String eventtype) {
		this.eventtype = eventtype;
	}
	public java.lang.String getEventobject() {
		return eventobject;
	}
	public void setEventobject(java.lang.String eventobject) {
		this.eventobject = eventobject;
	}
	public java.lang.String getObjectid() {
		return objectid;
	}
	public void setObjectid(java.lang.String objectid) {
		this.objectid = objectid;
	}
	public java.lang.String getObjectname() {
		return objectname;
	}
	public void setObjectname(java.lang.String objectname) {
		this.objectname = objectname;
	}
	public java.lang.String getOperatecomments() {
		return operatecomments;
	}
	public void setOperatecomments(java.lang.String operatecomments) {
		this.operatecomments = operatecomments;
	}
	public LogMain() {
    }
	

  
}
