package com.insigma.siis.local.business.entity;

/**
 * InterfaceUser entity. @author MyEclipse Persistence Tools
 */

public class InterfaceUser implements java.io.Serializable {

	// Fields

	private String userId;
	private String userName;
	private String password;
	private String realName;

	// Constructors

	/** default constructor */
	public InterfaceUser() {
	}

	/** minimal constructor */
	public InterfaceUser(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/** full constructor */
	public InterfaceUser(String userName, String password, String realName) {
		this.userName = userName;
		this.password = password;
		this.realName = realName;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}