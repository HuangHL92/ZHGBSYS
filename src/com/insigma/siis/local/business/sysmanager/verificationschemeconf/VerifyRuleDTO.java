package com.insigma.siis.local.business.sysmanager.verificationschemeconf;


/**
 * VerifyRule entity. @author MyEclipse Persistence Tools
 */

public class VerifyRuleDTO implements java.io.Serializable {

	// Fields

	private String vru001;
	private String vsc001;
	private String vru002;
	private String vru003;
	private String vru004;
	private String vru005;
	private String vru006;
	private String vru007;
	private String vru008;
	private String vru009;

	// Constructors

	/** default constructor */
	public VerifyRuleDTO() {
	}

	/** minimal constructor */
	public VerifyRuleDTO(String vru001) {
		this.vru001 = vru001;
	}

	/** full constructor */
	public VerifyRuleDTO(String vru001, String vsc001, String vru002,
			String vru003, String vru004, String vru005, String vru006,
			String vru007, String vru008, String vru009) {
		this.vru001 = vru001;
		this.vsc001 = vsc001;
		this.vru002 = vru002;
		this.vru003 = vru003;
		this.vru004 = vru004;
		this.vru005 = vru005;
		this.vru006 = vru006;
		this.vru007 = vru007;
		this.vru008 = vru008;
		this.vru009 = vru009;
	}

	// Property accessors

	public String getVru001() {
		return this.vru001;
	}

	public void setVru001(String vru001) {
		this.vru001 = vru001;
	}

	public String getVsc001() {
		return this.vsc001;
	}

	public void setVsc001(String vsc001) {
		this.vsc001 = vsc001;
	}

	public String getVru002() {
		return this.vru002;
	}

	public void setVru002(String vru002) {
		this.vru002 = vru002;
	}

	public String getVru003() {
		return this.vru003;
	}

	public void setVru003(String vru003) {
		this.vru003 = vru003;
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

	public String getVru006() {
		return this.vru006;
	}

	public void setVru006(String vru006) {
		this.vru006 = vru006;
	}

	public String getVru007() {
		return this.vru007;
	}

	public void setVru007(String vru007) {
		this.vru007 = vru007;
	}

	public String getVru008() {
		return this.vru008;
	}

	public void setVru008(String vru008) {
		this.vru008 = vru008;
	}

	public String getVru009() {
		return this.vru009;
	}

	public void setVru009(String vru009) {
		this.vru009 = vru009;
	}

}