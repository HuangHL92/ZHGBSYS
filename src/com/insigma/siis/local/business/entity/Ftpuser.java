package com.insigma.siis.local.business.entity;

/**
 * FtpUser entity. @author MyEclipse Persistence Tools
 */

public class Ftpuser implements java.io.Serializable {

	// Fields

	private String userid;
	private String userpassword;
	private String homedirectory;
	private String enableflag;
	private String writepermission;
	private Long idletime;
	private Long uploadrate;
	private Long downloadrate;
	private Integer maxloginnumber;
	private Integer maxloginperip;
	private String depict;

	// Constructors

	/** default constructor */
	public Ftpuser() {
	}

	/** minimal constructor */
	public Ftpuser(String userid, String homedirectory) {
		this.userid = userid;
		this.homedirectory = homedirectory;
	}

	/** full constructor */
	public Ftpuser(String userid, String userpassword, String homedirectory,
			String enableflag, String writepermission, Long idletime,
			Long uploadrate, Long downloadrate, Integer maxloginnumber,
			Integer maxloginperip, String depict) {
		this.userid = userid;
		this.userpassword = userpassword;
		this.homedirectory = homedirectory;
		this.enableflag = enableflag;
		this.writepermission = writepermission;
		this.idletime = idletime;
		this.uploadrate = uploadrate;
		this.downloadrate = downloadrate;
		this.maxloginnumber = maxloginnumber;
		this.maxloginperip = maxloginperip;
		this.depict = depict;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpassword() {
		return this.userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getHomedirectory() {
		return this.homedirectory;
	}

	public void setHomedirectory(String homedirectory) {
		this.homedirectory = homedirectory;
	}

	public String getEnableflag() {
		return this.enableflag;
	}

	public void setEnableflag(String enableflag) {
		this.enableflag = enableflag;
	}

	public String getWritepermission() {
		return this.writepermission;
	}

	public void setWritepermission(String writepermission) {
		this.writepermission = writepermission;
	}

	public Long getIdletime() {
		return this.idletime;
	}

	public void setIdletime(Long idletime) {
		this.idletime = idletime;
	}

	public Long getUploadrate() {
		return this.uploadrate;
	}

	public void setUploadrate(Long uploadrate) {
		this.uploadrate = uploadrate;
	}

	public Long getDownloadrate() {
		return this.downloadrate;
	}

	public void setDownloadrate(Long downloadrate) {
		this.downloadrate = downloadrate;
	}

	public Integer getMaxloginnumber() {
		return this.maxloginnumber;
	}

	public void setMaxloginnumber(Integer maxloginnumber) {
		this.maxloginnumber = maxloginnumber;
	}

	public Integer getMaxloginperip() {
		return this.maxloginperip;
	}

	public void setMaxloginperip(Integer maxloginperip) {
		this.maxloginperip = maxloginperip;
	}

	public String getDepict() {
		return this.depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}

}