package com.insigma.siis.local.business.entity;

/**
 * VerifyColVsl006 entity. @author MyEclipse Persistence Tools
 */

public class VerifyColVsl006 implements java.io.Serializable {

	// Fields

	private VerifyColVsl006Id id;
	private String tableComments;
	private String columnComments;
	private String colDataType;
	private String vsl006s;

	// Constructors

	/** default constructor */
	public VerifyColVsl006() {
	}

	/** minimal constructor */
	public VerifyColVsl006(VerifyColVsl006Id id) {
		this.id = id;
	}

	/** full constructor */
	public VerifyColVsl006(VerifyColVsl006Id id, String tableComments,
			String columnComments, String colDataType, String vsl006s) {
		this.id = id;
		this.tableComments = tableComments;
		this.columnComments = columnComments;
		this.colDataType = colDataType;
		this.vsl006s = vsl006s;
	}

	// Property accessors

	public VerifyColVsl006Id getId() {
		return this.id;
	}

	public void setId(VerifyColVsl006Id id) {
		this.id = id;
	}

	public String getTableComments() {
		return this.tableComments;
	}

	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}

	public String getColumnComments() {
		return this.columnComments;
	}

	public void setColumnComments(String columnComments) {
		this.columnComments = columnComments;
	}

	public String getColDataType() {
		return this.colDataType;
	}

	public void setColDataType(String colDataType) {
		this.colDataType = colDataType;
	}

	public String getVsl006s() {
		return this.vsl006s;
	}

	public void setVsl006s(String vsl006s) {
		this.vsl006s = vsl006s;
	}

}