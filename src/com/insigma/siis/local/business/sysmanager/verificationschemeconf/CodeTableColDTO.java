package com.insigma.siis.local.business.sysmanager.verificationschemeconf;

/**
 * CodeTableCol entity. @author MyEclipse Persistence Tools
 */

public class CodeTableColDTO implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -603277518398848345L;
	private String ctci;
	private String tableCode;
	private String colCode;
	private String colName;
	private String codeType;
	private String colLectionCode;
	private String colLectionName;
	private String fixedOperator;
	private String isNewCodeCol;
	private String colDataTypeShould;
	private String vsl006s;
	private String isZbx;

	// Constructors

	public String getIsZbx() {
		return isZbx;
	}

	public void setIsZbx(String isZbx) {
		this.isZbx = isZbx;
	}

	/** default constructor */
	public CodeTableColDTO() {
	}

	/** minimal constructor */
	public CodeTableColDTO(String ctci, String tableCode, String colCode,
			String colName) {
		this.ctci = ctci;
		this.tableCode = tableCode;
		this.colCode = colCode;
		this.colName = colName;
	}

	/** full constructor */
	public CodeTableColDTO(String ctci, String tableCode, String colCode,
			String colName, String codeType, String colLectionCode,
			String colLectionName, String fixedOperator, String isNewCodeCol,
			String colDataTypeShould, String vsl006s) {
		this.ctci = ctci;
		this.tableCode = tableCode;
		this.colCode = colCode;
		this.colName = colName;
		this.codeType = codeType;
		this.colLectionCode = colLectionCode;
		this.colLectionName = colLectionName;
		this.fixedOperator = fixedOperator;
		this.isNewCodeCol = isNewCodeCol;
		this.colDataTypeShould = colDataTypeShould;
		this.vsl006s = vsl006s;
	}

	// Property accessors

	public String getCtci() {
		return this.ctci;
	}

	public void setCtci(String ctci) {
		this.ctci = ctci;
	}

	public String getTableCode() {
		return this.tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getColCode() {
		return this.colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getColLectionCode() {
		return this.colLectionCode;
	}

	public void setColLectionCode(String colLectionCode) {
		this.colLectionCode = colLectionCode;
	}

	public String getColLectionName() {
		return this.colLectionName;
	}

	public void setColLectionName(String colLectionName) {
		this.colLectionName = colLectionName;
	}

	public String getFixedOperator() {
		return this.fixedOperator;
	}

	public void setFixedOperator(String fixedOperator) {
		this.fixedOperator = fixedOperator;
	}

	public String getIsNewCodeCol() {
		return this.isNewCodeCol;
	}

	public void setIsNewCodeCol(String isNewCodeCol) {
		this.isNewCodeCol = isNewCodeCol;
	}

	public String getColDataTypeShould() {
		return this.colDataTypeShould;
	}

	public void setColDataTypeShould(String colDataTypeShould) {
		this.colDataTypeShould = colDataTypeShould;
	}

	public String getVsl006s() {
		return this.vsl006s;
	}

	public void setVsl006s(String vsl006s) {
		this.vsl006s = vsl006s;
	}

}