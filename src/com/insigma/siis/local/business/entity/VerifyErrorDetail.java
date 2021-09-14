package com.insigma.siis.local.business.entity;


/**
 * VerifyErrorDetail entity. @author MyEclipse Persistence Tools
 */

public class VerifyErrorDetail implements java.io.Serializable {

	// Fields

	private String ved001;
	private String ver001;
	private String vru001;
	private String vru004;
	private String vru005;
	private String ved002;
	private String ved003;

	// Constructors

	/** default constructor */
	public VerifyErrorDetail() {
	}

	/** minimal constructor */
	public VerifyErrorDetail(String ved001, String ver001) {
		this.ved001 = ved001;
		this.ver001 = ver001;
	}

	/** full constructor */
	public VerifyErrorDetail(String ved001, String ver001, String vru001,
			String vru004, String vru005, String ved002, String ved003) {
		this.ved001 = ved001;
		this.ver001 = ver001;
		this.vru001 = vru001;
		this.vru004 = vru004;
		this.vru005 = vru005;
		this.ved002 = ved002;
		this.ved003 = ved003;
	}

	// Property accessors

	public String getVed001() {
		return this.ved001;
	}

	public void setVed001(String ved001) {
		this.ved001 = ved001;
	}

	public String getVer001() {
		return this.ver001;
	}

	public void setVer001(String ver001) {
		this.ver001 = ver001;
	}

	public String getVru001() {
		return this.vru001;
	}

	public void setVru001(String vru001) {
		this.vru001 = vru001;
	}

	public String getVru004() {
		return this.vru004;
	}

	public void setVru004(String vru004) {
		this.vru004 = vru004;
	}

	public String getVru005() {
		return this.vru005;
	}

	public void setVru005(String vru005) {
		this.vru005 = vru005;
	}

	public String getVed002() {
		return this.ved002;
	}

	public void setVed002(String ved002) {
		this.ved002 = ved002;
	}

	public String getVed003() {
		return this.ved003;
	}

	public void setVed003(String ved003) {
		this.ved003 = ved003;
	}

}