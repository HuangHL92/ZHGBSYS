package com.insigma.siis.local.business.entity;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

/**
 * Customquerylist entity. @author MyEclipse Persistence Tools
 */

public class Customquerylist implements java.io.Serializable {

	// Fields

	private String cqli;
	private String listname;
	private String listcount;
	private Blob listdata;
	private String loginname;
	private Date listtime;
	private String parentid;

	// Constructors

	/** default constructor */
	public Customquerylist() {
	}

	/** minimal constructor */
	public Customquerylist(String cqli) {
		this.cqli = cqli;
	}

	/** full constructor */
	public Customquerylist(String cqli, String listname, String listcount,
			Blob listdata, String loginname, Date listtime) {
		this.cqli = cqli;
		this.listname = listname;
		this.listcount = listcount;
		this.listdata = listdata;
		this.loginname = loginname;
		this.listtime = listtime;
	}
	public Customquerylist(String cqli, String listname, String listcount,
			Blob listdata, String loginname, Date listtime,String parentid) {
		this.cqli = cqli;
		this.listname = listname;
		this.listcount = listcount;
		this.listdata = listdata;
		this.loginname = loginname;
		this.listtime = listtime;
		this.parentid = parentid;
	}

	// Property accessors

	public String getCqli() {
		return this.cqli;
	}

	public void setCqli(String cqli) {
		this.cqli = cqli;
	}

	public String getListname() {
		return this.listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getListcount() {
		return this.listcount;
	}

	public void setListcount(String listcount) {
		this.listcount = listcount;
	}

	public Blob getListdata() {
		return this.listdata;
	}

	public void setListdata(Blob listdata) {
		this.listdata = listdata;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Date getListtime() {
		return this.listtime;
	}

	public void setListtime(Date listtime) {
		this.listtime = listtime;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}