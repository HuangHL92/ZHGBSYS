package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;

/**
 * A32 entity. @author MyEclipse Persistence Tools
 */

public class A32 implements java.io.Serializable {

	// Fields

	private String userid;
	private String useful;
	private Short mage;
	private Short fmage;
	private BigDecimal syday;
	private BigDecimal birthday;

	// Constructors

	/** default constructor */
	public A32() {
	}

	/** minimal constructor */
	public A32(String userid, String useful) {
		this.userid = userid;
		this.useful = useful;
	}

	/** full constructor */
	public A32(String userid, String useful, Short mage, Short fmage,
			BigDecimal syday, BigDecimal birthday) {
		this.userid = userid;
		this.useful = useful;
		this.mage = mage;
		this.fmage = fmage;
		this.syday = syday;
		this.birthday = birthday;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUseful() {
		return this.useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	public Short getMage() {
		return this.mage;
	}

	public void setMage(Short mage) {
		this.mage = mage;
	}

	public Short getFmage() {
		return this.fmage;
	}

	public void setFmage(Short fmage) {
		this.fmage = fmage;
	}

	public BigDecimal getSyday() {
		return this.syday;
	}

	public void setSyday(BigDecimal syday) {
		this.syday = syday;
	}

	public BigDecimal getBirthday() {
		return this.birthday;
	}

	public void setBirthday(BigDecimal birthday) {
		this.birthday = birthday;
	}

}