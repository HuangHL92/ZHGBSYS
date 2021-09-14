package com.insigma.siis.local.business.entity;

/**
 * AddType entity. @author MyEclipse Persistence Tools
 */

public class AddType implements java.io.Serializable {

	// Fields

	private String addTypeId;
	private Integer addTypeSequence;
	private String addTypeName;
	private String addTypeDetail;
	private String tableCode;

	// Constructors

	/** default constructor */
	public AddType() {
	}

	/** minimal constructor */
	public AddType(String addTypeId, Integer addTypeSequence, String addTypeName,
			String tableCode) {
		this.addTypeId = addTypeId;
		this.addTypeSequence = addTypeSequence;
		this.addTypeName = addTypeName;
		this.tableCode = tableCode;
	}

	/** full constructor */
	public AddType(String addTypeId, Integer addTypeSequence, String addTypeName,
			String addTypeDetail, String tableCode) {
		this.addTypeId = addTypeId;
		this.addTypeSequence = addTypeSequence;
		this.addTypeName = addTypeName;
		this.addTypeDetail = addTypeDetail;
		this.tableCode = tableCode;
	}

	// Property accessors

	public String getAddTypeId() {
		return this.addTypeId;
	}

	public void setAddTypeId(String addTypeId) {
		this.addTypeId = addTypeId;
	}

	public Integer getAddTypeSequence() {
		return this.addTypeSequence;
	}

	public void setAddTypeSequence(Integer addTypeSequence) {
		this.addTypeSequence = addTypeSequence;
	}

	public String getAddTypeName() {
		return this.addTypeName;
	}

	public void setAddTypeName(String addTypeName) {
		this.addTypeName = addTypeName;
	}

	public String getAddTypeDetail() {
		return this.addTypeDetail;
	}

	public void setAddTypeDetail(String addTypeDetail) {
		this.addTypeDetail = addTypeDetail;
	}

	public String getTableCode() {
		return this.tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

}