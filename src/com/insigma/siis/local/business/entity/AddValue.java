package com.insigma.siis.local.business.entity;

/**
 * AddValue entity. @author MyEclipse Persistence Tools
 */

public class AddValue implements java.io.Serializable {

	// Fields

	private String addValueId;
	private Integer addValueSequence;
	private String addTypeId;
	private String addValueName;
	private String colCode;
	private String colType;
	private String publishStatus;
	private String isused;
	private String multilineshow;
	private String addValueDetail;
	private String codeType;

	// Constructors

	/** default constructor */
	public AddValue() {
	}

	/** minimal constructor */
	public AddValue(String addValueId, Integer addValueSequence,
			String addTypeId, String addValueName, String colCode,
			String colType, String publishStatus, String isused,
			String multilineshow, String codeType) {
		this.addValueId = addValueId;
		this.addValueSequence = addValueSequence;
		this.addTypeId = addTypeId;
		this.addValueName = addValueName;
		this.colCode = colCode;
		this.colType = colType;
		this.publishStatus = publishStatus;
		this.isused = isused;
		this.multilineshow = multilineshow;
		this.codeType = codeType;
	}

	/** full constructor */
	public AddValue(String addValueId, Integer addValueSequence,
			String addTypeId, String addValueName, String colCode,
			String colType, String publishStatus, String isused,
			String multilineshow, String addValueDetail, String codeType) {
		this.addValueId = addValueId;
		this.addValueSequence = addValueSequence;
		this.addTypeId = addTypeId;
		this.addValueName = addValueName;
		this.colCode = colCode;
		this.colType = colType;
		this.publishStatus = publishStatus;
		this.isused = isused;
		this.multilineshow = multilineshow;
		this.addValueDetail = addValueDetail;
		this.codeType = codeType;
	}

	// Property accessors

	public String getAddValueId() {
		return this.addValueId;
	}

	public void setAddValueId(String addValueId) {
		this.addValueId = addValueId;
	}

	public Integer getAddValueSequence() {
		return this.addValueSequence;
	}

	public void setAddValueSequence(Integer addValueSequence) {
		this.addValueSequence = addValueSequence;
	}

	public String getAddTypeId() {
		return this.addTypeId;
	}

	public void setAddTypeId(String addTypeId) {
		this.addTypeId = addTypeId;
	}

	public String getAddValueName() {
		return this.addValueName;
	}

	public void setAddValueName(String addValueName) {
		this.addValueName = addValueName;
	}

	public String getColCode() {
		return this.colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public String getColType() {
		return this.colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getPublishStatus() {
		return this.publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getIsused() {
		return this.isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	public String getMultilineshow() {
		return this.multilineshow;
	}

	public void setMultilineshow(String multilineshow) {
		this.multilineshow = multilineshow;
	}

	public String getAddValueDetail() {
		return this.addValueDetail;
	}

	public void setAddValueDetail(String addValueDetail) {
		this.addValueDetail = addValueDetail;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}