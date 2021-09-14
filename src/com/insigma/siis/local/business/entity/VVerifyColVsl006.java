package com.insigma.siis.local.business.entity;

/**
 * VVerifyColVsl006 entity. @author MyEclipse Persistence Tools
 */

public class VVerifyColVsl006 implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 8657589623965055004L;
	private String tableComments;;
	private String columnName;
	private String tableName;
	public String getColumnName() {
		return columnName;
	}


	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	private String columnComments;
	private String colDataType;
	private String colDataTypeShould;
	private String vsl006s;
	private String codeType;
	private String isZbx;
	private String zbxTj;

	// Constructors

	/** default constructor */
	public VVerifyColVsl006() {
	}


	public VVerifyColVsl006(String tableComments, String columnName, String tableName, String columnComments,
			String colDataType, String colDataTypeShould, String vsl006s, String codeType, String isZbx, String zbxTj) {
		super();
		this.tableComments = tableComments;
		this.columnName = columnName;
		this.tableName = tableName;
		this.columnComments = columnComments;
		this.colDataType = colDataType;
		this.colDataTypeShould = colDataTypeShould;
		this.vsl006s = vsl006s;
		this.codeType = codeType;
		this.isZbx = isZbx;
		this.zbxTj = zbxTj;
	}


	public String getTableComments() {
		return tableComments;
	}


	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}


	public String getColumnComments() {
		return columnComments;
	}

	public void setColumnComments(String columnComments) {
		this.columnComments = columnComments;
	}

	public String getColDataType() {
		return colDataType;
	}

	public void setColDataType(String colDataType) {
		this.colDataType = colDataType;
	}

	public String getVsl006s() {
		return vsl006s;
	}

	public void setVsl006s(String vsl006s) {
		this.vsl006s = vsl006s;
	}



	public String getColDataTypeShould() {
		return colDataTypeShould;
	}


	public void setColDataTypeShould(String colDataTypeShould) {
		this.colDataTypeShould = colDataTypeShould;
	}

	public String getCodeType() {
		return codeType;
	}


	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}


	public String getIsZbx() {
		return isZbx;
	}


	public void setIsZbx(String isZbx) {
		this.isZbx = isZbx;
	}


	public String getZbxTj() {
		return zbxTj;
	}


	public void setZbxTj(String zbxTj) {
		this.zbxTj = zbxTj;
	}


}