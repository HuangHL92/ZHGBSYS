package com.insigma.siis.local.business.entity;

import java.util.Date;

/**
 * InterfaceConfig entity. @author MyEclipse Persistence Tools
 */

public class InterfaceConfig implements java.io.Serializable {

	// Fields

	private String interfaceConfigIsn;
	private Integer interfaceConfigSequence;
	private String interfaceConfigId;
	private String interfaceConfigName;
	private String interfaceConfigDesc;
	private Date interfaceConfigCreateDate;
	private String interfaceConfigCreateUser;
	private Date interfaceConfigChangeDate;
	private String interfaceConfigChangeUser;
	private String interfaceConfigIsedit;
	private String availabilityStateId;
    private String publishStateId;
	// Constructors

	/** default constructor */
	public InterfaceConfig() {
	}

	/** full constructor */
	public InterfaceConfig(Integer interfaceConfigSequence,
			String interfaceConfigId, String interfaceConfigName,
			String interfaceConfigDesc, Date interfaceConfigCreateDate,
			String interfaceConfigCreateUser, Date interfaceConfigChangeDate,
			String interfaceConfigChangeUser, String interfaceConfigIsedit,
			String availabilityStateId, String publishStateId) {
		this.interfaceConfigSequence = interfaceConfigSequence;
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceConfigName = interfaceConfigName;
		this.interfaceConfigDesc = interfaceConfigDesc;
		this.interfaceConfigCreateDate = interfaceConfigCreateDate;
		this.interfaceConfigCreateUser = interfaceConfigCreateUser;
		this.interfaceConfigChangeDate = interfaceConfigChangeDate;
		this.interfaceConfigChangeUser = interfaceConfigChangeUser;
		this.interfaceConfigIsedit = interfaceConfigIsedit;
		this.availabilityStateId = availabilityStateId;
		this.publishStateId = publishStateId;
	}

	// Property accessors

	public String getPublishStateId() {
		return publishStateId;
	}

	public void setPublishStateId(String publishStateId) {
		this.publishStateId = publishStateId;
	}

	public String getInterfaceConfigIsn() {
		return this.interfaceConfigIsn;
	}

	public void setInterfaceConfigIsn(String interfaceConfigIsn) {
		this.interfaceConfigIsn = interfaceConfigIsn;
	}

	public Integer getInterfaceConfigSequence() {
		return this.interfaceConfigSequence;
	}

	public void setInterfaceConfigSequence(Integer interfaceConfigSequence) {
		this.interfaceConfigSequence = interfaceConfigSequence;
	}

	public String getInterfaceConfigId() {
		return this.interfaceConfigId;
	}

	public void setInterfaceConfigId(String interfaceConfigId) {
		this.interfaceConfigId = interfaceConfigId;
	}

	public String getInterfaceConfigName() {
		return this.interfaceConfigName;
	}

	public void setInterfaceConfigName(String interfaceConfigName) {
		this.interfaceConfigName = interfaceConfigName;
	}

	public String getInterfaceConfigDesc() {
		return this.interfaceConfigDesc;
	}

	public void setInterfaceConfigDesc(String interfaceConfigDesc) {
		this.interfaceConfigDesc = interfaceConfigDesc;
	}

	public Date getInterfaceConfigCreateDate() {
		return this.interfaceConfigCreateDate;
	}

	public void setInterfaceConfigCreateDate(Date interfaceConfigCreateDate) {
		this.interfaceConfigCreateDate = interfaceConfigCreateDate;
	}

	public String getInterfaceConfigCreateUser() {
		return this.interfaceConfigCreateUser;
	}

	public void setInterfaceConfigCreateUser(String interfaceConfigCreateUser) {
		this.interfaceConfigCreateUser = interfaceConfigCreateUser;
	}

	public Date getInterfaceConfigChangeDate() {
		return this.interfaceConfigChangeDate;
	}

	public void setInterfaceConfigChangeDate(Date interfaceConfigChangeDate) {
		this.interfaceConfigChangeDate = interfaceConfigChangeDate;
	}

	public String getInterfaceConfigChangeUser() {
		return this.interfaceConfigChangeUser;
	}

	public void setInterfaceConfigChangeUser(String interfaceConfigChangeUser) {
		this.interfaceConfigChangeUser = interfaceConfigChangeUser;
	}

	public String getInterfaceConfigIsedit() {
		return this.interfaceConfigIsedit;
	}

	public void setInterfaceConfigIsedit(String interfaceConfigIsedit) {
		this.interfaceConfigIsedit = interfaceConfigIsedit;
	}

	public String getAvailabilityStateId() {
		return this.availabilityStateId;
	}

	public void setAvailabilityStateId(String availabilityStateId) {
		this.availabilityStateId = availabilityStateId;
	}

}