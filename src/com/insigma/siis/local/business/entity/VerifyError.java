package com.insigma.siis.local.business.entity;

/**
 * VerifyError entity. @author MyEclipse Persistence Tools
 */

public class VerifyError implements java.io.Serializable {

	// Fields

	@Override
	public String toString() {
		return "VerifyError [ver001=" + ver001 + ", ver002=" + ver002
				+ ", vru003=" + vru003 + ", ver003=" + ver003 + ", ver004="
				+ ver004 + ", ver005=" + ver005 + ", ver006=" + ver006 + "]";
	}

	private String ver001;
	private String ver002;
	private String vru003;
	private String ver003;
	private String ver004;
	private String ver005;
	private Long ver006;

	// Constructors

	/** default constructor */
	public VerifyError() {
	}

	/** minimal constructor */
	public VerifyError(String ver001, String ver002, String vru003,
			String ver003, String ver004) {
		this.ver001 = ver001;
		this.ver002 = ver002;
		this.vru003 = vru003;
		this.ver003 = ver003;
		this.ver004 = ver004;
	}

	/** full constructor */
	public VerifyError(String ver001, String ver002, String vru003,
			String ver003, String ver004, String ver005, Long ver006) {
		this.ver001 = ver001;
		this.ver002 = ver002;
		this.vru003 = vru003;
		this.ver003 = ver003;
		this.ver004 = ver004;
		this.ver005 = ver005;
		this.ver006 = ver006;
	}

	// Property accessors

	public String getVer001() {
		return this.ver001;
	}

	public void setVer001(String ver001) {
		this.ver001 = ver001;
	}

	public String getVer002() {
		return this.ver002;
	}

	public void setVer002(String ver002) {
		this.ver002 = ver002;
	}

	public String getVru003() {
		return this.vru003;
	}

	public void setVru003(String vru003) {
		this.vru003 = vru003;
	}

	public String getVer003() {
		return this.ver003;
	}

	public void setVer003(String ver003) {
		this.ver003 = ver003;
	}

	public String getVer004() {
		return this.ver004;
	}

	public void setVer004(String ver004) {
		this.ver004 = ver004;
	}

	public String getVer005() {
		return this.ver005;
	}

	public void setVer005(String ver005) {
		this.ver005 = ver005;
	}

	public Long getVer006() {
		return this.ver006;
	}

	public void setVer006(Long ver006) {
		this.ver006 = ver006;
	}

}