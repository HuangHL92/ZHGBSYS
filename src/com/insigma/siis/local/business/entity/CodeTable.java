package com.insigma.siis.local.business.entity;

/**
 * CodeTable entity. @author MyEclipse Persistence Tools
 */

public class CodeTable implements java.io.Serializable {

	// Fields

	private String tableCode;
	private String tableName;

	// Constructors

	/** default constructor */
	public CodeTable() {
	}

	/** full constructor */
	public CodeTable(String tableCode, String tableName) {
		this.tableCode = tableCode;
		this.tableName = tableName;
	}

	// Property accessors

	public String getTableCode() {
		return this.tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}