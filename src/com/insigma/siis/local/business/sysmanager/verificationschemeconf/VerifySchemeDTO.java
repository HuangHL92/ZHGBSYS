package com.insigma.siis.local.business.sysmanager.verificationschemeconf;

import java.util.Date;

/**
 * VerifySchemeDTO entity. @author MyEclipse Persistence Tools
 */

public class VerifySchemeDTO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2412246008755917864L;
	private String vsc001;
	private String vsc002;
	private String vsc003;
	private String vsc004;
	private String vsc005;
	private Date vsc006;
	private String vsc007;
	private String vsc008;
	private String vsc009;
	private String vsc010;
	private String vsc011;

	// Constructors

	/** default constructor */
	public VerifySchemeDTO() {
	}

	/** minimal constructor */
	public VerifySchemeDTO(String vsc001) {
		this.vsc001 = vsc001;
	}

	/** full constructor */
	public VerifySchemeDTO(String vsc001, String vsc002, String vsc003,
			String vsc004, String vsc005, Date vsc006, String vsc007,
			String vsc008, String vsc009, String vsc010, String vsc011) {
		this.vsc001 = vsc001;
		this.vsc002 = vsc002;
		this.vsc003 = vsc003;
		this.vsc004 = vsc004;
		this.vsc005 = vsc005;
		this.vsc006 = vsc006;
		this.vsc007 = vsc007;
		this.vsc008 = vsc008;
		this.vsc009 = vsc009;
		this.vsc010 = vsc010;
		this.vsc011 = vsc011;
	}

	// Property accessors

	public String getVsc001() {
		return this.vsc001;
	}

	public void setVsc001(String vsc001) {
		this.vsc001 = vsc001;
	}

	public String getVsc002() {
		return this.vsc002;
	}

	public void setVsc002(String vsc002) {
		this.vsc002 = vsc002;
	}

	public String getVsc003() {
		return this.vsc003;
	}

	public void setVsc003(String vsc003) {
		this.vsc003 = vsc003;
	}

	public String getVsc004() {
		return this.vsc004;
	}

	public void setVsc004(String vsc004) {
		this.vsc004 = vsc004;
	}

	public String getVsc005() {
		return this.vsc005;
	}

	public void setVsc005(String vsc005) {
		this.vsc005 = vsc005;
	}

	public Date getVsc006() {
		return this.vsc006;
	}

	public void setVsc006(Date vsc006) {
		this.vsc006 = vsc006;
	}

	public String getVsc007() {
		return this.vsc007;
	}

	public void setVsc007(String vsc007) {
		this.vsc007 = vsc007;
	}

	public String getVsc008() {
		return this.vsc008;
	}

	public void setVsc008(String vsc008) {
		this.vsc008 = vsc008;
	}

	public String getVsc009() {
		return this.vsc009;
	}

	public void setVsc009(String vsc009) {
		this.vsc009 = vsc009;
	}

	public String getVsc010() {
		return this.vsc010;
	}

	public void setVsc010(String vsc010) {
		this.vsc010 = vsc010;
	}

	public String getVsc011() {
		return this.vsc011;
	}

	public void setVsc011(String vsc011) {
		this.vsc011 = vsc011;
	}

}