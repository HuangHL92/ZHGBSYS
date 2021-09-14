package com.insigma.siis.local.business.entity;

/**
 * VVerifyColVsl006Id entity. @author MyEclipse Persistence Tools
 */

public class VVerifyColVsl006Id implements java.io.Serializable {

	// Fields

	private String tableName;
	private String columnName;

	// Constructors

	/** default constructor */
	public VVerifyColVsl006Id() {
	}

	/** minimal constructor */
	public VVerifyColVsl006Id(String tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
	}


	// Property accessors

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}





	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37
				* result
				+ (getColumnName() == null ? 0 : this.getColumnName()
						.hashCode());
		return result;
	}

}