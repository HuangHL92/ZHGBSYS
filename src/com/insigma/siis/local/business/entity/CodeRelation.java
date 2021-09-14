package com.insigma.siis.local.business.entity;


/**
 * CodeRelation entity. @author MyEclipse Persistence Tools
 */

public class CodeRelation implements java.io.Serializable {

	// Fields

	private Long codeRelationId;
	private String codeType;
	private String tableName;
	private String columnName;

	// Constructors

	/** default constructor */
	public CodeRelation() {
	}

	/** full constructor */
	public CodeRelation(Long codeRelationId, String codeType,
			String tableName, String columnName) {
		this.codeRelationId = codeRelationId;
		this.codeType = codeType;
		this.tableName = tableName;
		this.columnName = columnName;
	}

	// Property accessors

	public Long getCodeRelationId() {
		return this.codeRelationId;
	}

	public void setCodeRelationId(Long codeRelationId) {
		this.codeRelationId = codeRelationId;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

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

}