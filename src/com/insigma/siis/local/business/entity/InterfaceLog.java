package com.insigma.siis.local.business.entity;

import java.util.Date;

/**
 * InterfaceLog entity. @author MyEclipse Persistence Tools
 */

public class InterfaceLog implements java.io.Serializable {

	// Fields

	private String interfaceLogId;
	private String interfaceConfigId;
	private String interfaceConfigName;
	private String interfaceScriptId;
	private String interfaceScriptName;
	private String interfaceExecSql;
	private String interfaceOriginalSql;
	private Date interfaceRequesttime;
	private String interfaceAccessIp;
	private String executeStateId;
	private String operateUsername;
	private String interfaceComments;

	// Constructors

	/** default constructor */
	public InterfaceLog() {
	}

	/** minimal constructor */
	public InterfaceLog(String interfaceConfigId, String interfaceConfigName,
			String interfaceScriptId, String interfaceScriptName,
			String interfaceExecSql, String interfaceOriginalSql,
			 Date interfaceRequesttime,
			String interfaceAccessIp, String executeStateId) {
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceConfigName = interfaceConfigName;
		this.interfaceScriptId = interfaceScriptId;
		this.interfaceScriptName = interfaceScriptName;
		this.interfaceExecSql = interfaceExecSql;
		this.interfaceOriginalSql = interfaceOriginalSql;
		this.interfaceRequesttime = interfaceRequesttime;
		this.interfaceAccessIp = interfaceAccessIp;
		this.executeStateId = executeStateId;
	}

	/** full constructor */
	public InterfaceLog(String interfaceConfigId, String interfaceConfigName,
			String interfaceScriptId, String interfaceScriptName,
			String interfaceExecSql, String interfaceOriginalSql,
			 Date interfaceRequesttime,
			String interfaceAccessIp, String executeStateId,
			 String operateUsername,
			String interfaceComments) {
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceConfigName = interfaceConfigName;
		this.interfaceScriptId = interfaceScriptId;
		this.interfaceScriptName = interfaceScriptName;
		this.interfaceExecSql = interfaceExecSql;
		this.interfaceOriginalSql = interfaceOriginalSql;
		this.interfaceRequesttime = interfaceRequesttime;
		this.interfaceAccessIp = interfaceAccessIp;
		this.executeStateId = executeStateId;
		this.operateUsername = operateUsername;
		this.interfaceComments = interfaceComments;
	}

	// Property accessors

	public String getInterfaceLogId() {
		return this.interfaceLogId;
	}

	public void setInterfaceLogId(String interfaceLogId) {
		this.interfaceLogId = interfaceLogId;
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

	public String getInterfaceScriptId() {
		return this.interfaceScriptId;
	}

	public void setInterfaceScriptId(String interfaceScriptId) {
		this.interfaceScriptId = interfaceScriptId;
	}

	public String getInterfaceScriptName() {
		return this.interfaceScriptName;
	}

	public void setInterfaceScriptName(String interfaceScriptName) {
		this.interfaceScriptName = interfaceScriptName;
	}

	public String getInterfaceExecSql() {
		return this.interfaceExecSql;
	}

	public void setInterfaceExecSql(String interfaceExecSql) {
		this.interfaceExecSql = interfaceExecSql;
	}

	public String getInterfaceOriginalSql() {
		return this.interfaceOriginalSql;
	}

	public void setInterfaceOriginalSql(String interfaceOriginalSql) {
		this.interfaceOriginalSql = interfaceOriginalSql;
	}

	public Date getInterfaceRequesttime() {
		return this.interfaceRequesttime;
	}

	public void setInterfaceRequesttime(Date interfaceRequesttime) {
		this.interfaceRequesttime = interfaceRequesttime;
	}

	public String getInterfaceAccessIp() {
		return this.interfaceAccessIp;
	}

	public void setInterfaceAccessIp(String interfaceAccessIp) {
		this.interfaceAccessIp = interfaceAccessIp;
	}

	public String getExecuteStateId() {
		return this.executeStateId;
	}

	public void setExecuteStateId(String executeStateId) {
		this.executeStateId = executeStateId;
	}

	public String getOperateUsername() {
		return this.operateUsername;
	}

	public void setOperateUsername(String operateUsername) {
		this.operateUsername = operateUsername;
	}

	public String getInterfaceComments() {
		return this.interfaceComments;
	}

	public void setInterfaceComments(String interfaceComments) {
		this.interfaceComments = interfaceComments;
	}

}