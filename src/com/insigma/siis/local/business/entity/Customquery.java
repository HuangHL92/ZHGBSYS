package com.insigma.siis.local.business.entity;

import java.util.Date;

/**
 * Customquery entity. @author MyEclipse Persistence Tools
 */

public class Customquery implements java.io.Serializable {

	// Fields

	private String queryid;
	private String querysql;
	private String querydescription;
	private String loginname;
	private Date createtime;
	private String queryname;
	private String gridstring;
	private String sharename;
	private String querycond;
	
	// Constructors

	public String getQuerycond() {
		return querycond;
	}

	public void setQuerycond(String querycond) {
		this.querycond = querycond;
	}

	public String getSharename() {
		return sharename;
	}

	public void setSharename(String sharename) {
		this.sharename = sharename;
	}

	/** default constructor */
	public Customquery() {
	}

	/** minimal constructor */
	public Customquery(String queryid, String queryname) {
		this.queryid = queryid;
		this.queryname = queryname;
	}

	/** full constructor */
	public Customquery(String queryid, String querysql,
			String querydescription, String loginname, Date createtime,
			String queryname, String gridstring) {
		this.queryid = queryid;
		this.querysql = querysql;
		this.querydescription = querydescription;
		this.loginname = loginname;
		this.createtime = createtime;
		this.queryname = queryname;
		this.gridstring = gridstring;
	}

	// Property accessors

	public String getQueryid() {
		return this.queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	public String getQuerysql() {
		return this.querysql;
	}

	public void setQuerysql(String querysql) {
		this.querysql = querysql;
	}

	public String getQuerydescription() {
		return this.querydescription;
	}

	public void setQuerydescription(String querydescription) {
		this.querydescription = querydescription;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getQueryname() {
		return this.queryname;
	}

	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}

	public String getGridstring() {
		return this.gridstring;
	}

	public void setGridstring(String gridstring) {
		this.gridstring = gridstring;
	}

}