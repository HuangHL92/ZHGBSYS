package com.insigma.odin.framework.privilege.entity;

import java.util.Date;

/**
 * SmtGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmtGroup extends SmtPrincipal implements java.io.Serializable {
	
	// Fields

	private String parentid;
	private String org;
	private String districtcode;
	private String license;
	private String principal;
	private String shortname;
	private String address;
	private String tel;
	private String linkman;
	private String type;
	private String chargedept;
	private String otherinfo;
	private Date createdate;
	private String hashcode;
	private String rate;
	private Long sortid;

	// Constructors

	public String getRate() {
		return rate;
	}



	public void setRate(String rate) {
		this.rate = rate;
	}



	/** default constructor */
	public SmtGroup() {
	}



	// Property accessors

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrg() {
		return this.org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDistrictcode() {
		return this.districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}

	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChargedept() {
		return this.chargedept;
	}

	public void setChargedept(String chargedept) {
		this.chargedept = chargedept;
	}

	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}



	public Long getSortid() {
		return sortid;
	}



	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

}