package com.insigma.siis.local.business.entity;

/**
 * CodeValue entity. @author MyEclipse Persistence Tools
 */

public class CodeValue implements java.io.Serializable {

	// Fields

	private Integer codeValueSeq;
	private String codeType;
	private String codeColumnName;
	private String codeLevel;
	private String codeValue;
	private Integer inino;
	private String subCodeValue;
	private String codeName;
	private String codeName2;
	private String codeName3;
	private String codeRemark;
	private String codeSpelling;
	private String iscustomize;
	private String codeAssist;
	private String codeStatus;
	private String codeLeaf;
	private String startDate;
	private String stopDate;

	// Constructors

	/** default constructor */
	public CodeValue() {
	}

	/** minimal constructor */
	public CodeValue(Integer codeValueSeq, String codeType) {
		this.codeValueSeq = codeValueSeq;
		this.codeType = codeType;
	}

	/** full constructor */
	public CodeValue(Integer codeValueSeq, String codeType,
			String codeColumnName, String codeLevel, String codeValue,
			Integer inino, String subCodeValue, String codeName,
			String codeName2, String codeName3, String codeRemark,
			String codeSpelling, String iscustomize, String codeAssist,
			String codeStatus, String codeLeaf, String startDate,
			String stopDate) {
		this.codeValueSeq = codeValueSeq;
		this.codeType = codeType;
		this.codeColumnName = codeColumnName;
		this.codeLevel = codeLevel;
		this.codeValue = codeValue;
		this.inino = inino;
		this.subCodeValue = subCodeValue;
		this.codeName = codeName;
		this.codeName2 = codeName2;
		this.codeName3 = codeName3;
		this.codeRemark = codeRemark;
		this.codeSpelling = codeSpelling;
		this.iscustomize = iscustomize;
		this.codeAssist = codeAssist;
		this.codeStatus = codeStatus;
		this.codeLeaf = codeLeaf;
		this.startDate = startDate;
		this.stopDate = stopDate;
	}

	// Property accessors

	public Integer getCodeValueSeq() {
		return this.codeValueSeq;
	}

	public void setCodeValueSeq(Integer codeValueSeq) {
		this.codeValueSeq = codeValueSeq;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeColumnName() {
		return this.codeColumnName;
	}

	public void setCodeColumnName(String codeColumnName) {
		this.codeColumnName = codeColumnName;
	}

	public String getCodeLevel() {
		return this.codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Integer getInino() {
		return this.inino;
	}

	public void setInino(Integer inino) {
		this.inino = inino;
	}

	public String getSubCodeValue() {
		return this.subCodeValue;
	}

	public void setSubCodeValue(String subCodeValue) {
		this.subCodeValue = subCodeValue;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeName2() {
		return this.codeName2;
	}

	public void setCodeName2(String codeName2) {
		this.codeName2 = codeName2;
	}

	public String getCodeName3() {
		return this.codeName3;
	}

	public void setCodeName3(String codeName3) {
		this.codeName3 = codeName3;
	}

	public String getCodeRemark() {
		return this.codeRemark;
	}

	public void setCodeRemark(String codeRemark) {
		this.codeRemark = codeRemark;
	}

	public String getCodeSpelling() {
		return this.codeSpelling;
	}

	public void setCodeSpelling(String codeSpelling) {
		this.codeSpelling = codeSpelling;
	}

	public String getIscustomize() {
		return this.iscustomize;
	}

	public void setIscustomize(String iscustomize) {
		this.iscustomize = iscustomize;
	}

	public String getCodeAssist() {
		return this.codeAssist;
	}

	public void setCodeAssist(String codeAssist) {
		this.codeAssist = codeAssist;
	}

	public String getCodeStatus() {
		return this.codeStatus;
	}

	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}

	public String getCodeLeaf() {
		return this.codeLeaf;
	}

	public void setCodeLeaf(String codeLeaf) {
		this.codeLeaf = codeLeaf;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return this.stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

}