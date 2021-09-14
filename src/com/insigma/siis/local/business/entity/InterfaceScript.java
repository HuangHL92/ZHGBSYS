package com.insigma.siis.local.business.entity;

import java.util.Date;

/**
 * InterfaceScript entity. @author MyEclipse Persistence Tools
 */

public class InterfaceScript implements java.io.Serializable {

	// Fields

	private String interfaceScriptIsn;
	private Integer interfaceScriptSequence;
	private String interfaceConfigId;
	private String interfaceScriptId;
	private String interfaceScriptName;
	private String interfaceScriptSql;
	private String targetTableName;
	private String interfaceScriptDesc;
	private Date interfaceScriptCreateDate;
	private String interfaceScriptCreateUser;
	private Date interfaceScriptChangeDate;
	private String interfaceScriptChangeUser;
	private String interfaceScriptIsedit;
	private String availabilityStateId;

	// Constructors

	/** default constructor */
	public InterfaceScript() {
	}

	/** minimal constructor */
	public InterfaceScript(Integer interfaceScriptSequence,
			String interfaceConfigId, String interfaceScriptId,
			String interfaceScriptName, String interfaceScriptSql,
			String targetTableName,
			String interfaceScriptDesc, Date interfaceScriptCreateDate,
			String interfaceScriptCreateUser, Date interfaceScriptChangeDate,
			String interfaceScriptChangeUser, String interfaceScriptIsedit) {
		this.interfaceScriptSequence = interfaceScriptSequence;
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceScriptId = interfaceScriptId;
		this.interfaceScriptName = interfaceScriptName;
		this.interfaceScriptSql = interfaceScriptSql;
		this.targetTableName = targetTableName;
		this.interfaceScriptDesc = interfaceScriptDesc;
		this.interfaceScriptCreateDate = interfaceScriptCreateDate;
		this.interfaceScriptCreateUser = interfaceScriptCreateUser;
		this.interfaceScriptChangeDate = interfaceScriptChangeDate;
		this.interfaceScriptChangeUser = interfaceScriptChangeUser;
		this.interfaceScriptIsedit = interfaceScriptIsedit;
	}

	/** full constructor */
	public InterfaceScript(Integer interfaceScriptSequence,
			String interfaceConfigId, String interfaceScriptId,
			String interfaceScriptName, String interfaceScriptSql,
			String targetTableName,
			String interfaceScriptDesc, Date interfaceScriptCreateDate,
			String interfaceScriptCreateUser, Date interfaceScriptChangeDate,
			String interfaceScriptChangeUser, String interfaceScriptIsedit,
			String availabilityStateId) {
		this.interfaceScriptSequence = interfaceScriptSequence;
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceScriptId = interfaceScriptId;
		this.interfaceScriptName = interfaceScriptName;
		this.interfaceScriptSql = interfaceScriptSql;
		this.targetTableName = targetTableName;
		this.interfaceScriptDesc = interfaceScriptDesc;
		this.interfaceScriptCreateDate = interfaceScriptCreateDate;
		this.interfaceScriptCreateUser = interfaceScriptCreateUser;
		this.interfaceScriptChangeDate = interfaceScriptChangeDate;
		this.interfaceScriptChangeUser = interfaceScriptChangeUser;
		this.interfaceScriptIsedit = interfaceScriptIsedit;
		this.availabilityStateId = availabilityStateId;
	}

	public String getInterfaceScriptIsn() {
		return interfaceScriptIsn;
	}

	public void setInterfaceScriptIsn(String interfaceScriptIsn) {
		this.interfaceScriptIsn = interfaceScriptIsn;
	}

	public Integer getInterfaceScriptSequence() {
		return interfaceScriptSequence;
	}

	public void setInterfaceScriptSequence(Integer interfaceScriptSequence) {
		this.interfaceScriptSequence = interfaceScriptSequence;
	}

	public String getInterfaceConfigId() {
		return interfaceConfigId;
	}

	public void setInterfaceConfigId(String interfaceConfigId) {
		this.interfaceConfigId = interfaceConfigId;
	}

	public String getInterfaceScriptId() {
		return interfaceScriptId;
	}

	public void setInterfaceScriptId(String interfaceScriptId) {
		this.interfaceScriptId = interfaceScriptId;
	}

	public String getInterfaceScriptName() {
		return interfaceScriptName;
	}

	public void setInterfaceScriptName(String interfaceScriptName) {
		this.interfaceScriptName = interfaceScriptName;
	}

	public String getInterfaceScriptSql() {
		return interfaceScriptSql;
	}

	public void setInterfaceScriptSql(String interfaceScriptSql) {
		this.interfaceScriptSql = interfaceScriptSql;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getInterfaceScriptDesc() {
		return interfaceScriptDesc;
	}

	public void setInterfaceScriptDesc(String interfaceScriptDesc) {
		this.interfaceScriptDesc = interfaceScriptDesc;
	}

	public Date getInterfaceScriptCreateDate() {
		return interfaceScriptCreateDate;
	}

	public void setInterfaceScriptCreateDate(Date interfaceScriptCreateDate) {
		this.interfaceScriptCreateDate = interfaceScriptCreateDate;
	}

	public String getInterfaceScriptCreateUser() {
		return interfaceScriptCreateUser;
	}

	public void setInterfaceScriptCreateUser(String interfaceScriptCreateUser) {
		this.interfaceScriptCreateUser = interfaceScriptCreateUser;
	}

	public Date getInterfaceScriptChangeDate() {
		return interfaceScriptChangeDate;
	}

	public void setInterfaceScriptChangeDate(Date interfaceScriptChangeDate) {
		this.interfaceScriptChangeDate = interfaceScriptChangeDate;
	}

	public String getInterfaceScriptChangeUser() {
		return interfaceScriptChangeUser;
	}

	public void setInterfaceScriptChangeUser(String interfaceScriptChangeUser) {
		this.interfaceScriptChangeUser = interfaceScriptChangeUser;
	}

	public String getInterfaceScriptIsedit() {
		return interfaceScriptIsedit;
	}

	public void setInterfaceScriptIsedit(String interfaceScriptIsedit) {
		this.interfaceScriptIsedit = interfaceScriptIsedit;
	}

	public String getAvailabilityStateId() {
		return availabilityStateId;
	}

	public void setAvailabilityStateId(String availabilityStateId) {
		this.availabilityStateId = availabilityStateId;
	}

	
}