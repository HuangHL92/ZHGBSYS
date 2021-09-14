package com.insigma.siis.local.business.entity;

/**
 * CodeType entity. @author MyEclipse Persistence Tools
 */

public class CodeType implements java.io.Serializable {

	// Fields

	private String codeType;
	private String typeName;
	private String codeGroup;
	private String codeDescription;
	private String iscustomize;

	// Constructors

	/** default constructor */
	public CodeType() {
	}

	/** full constructor */
	public CodeType(String typeName, String codeGroup, String codeDescription,
			String iscustomize) {
		this.typeName = typeName;
		this.codeGroup = codeGroup;
		this.codeDescription = codeDescription;
		this.iscustomize = iscustomize;
	}

	// Property accessors

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCodeGroup() {
		return this.codeGroup;
	}

	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
	}

	public String getCodeDescription() {
		return this.codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public String getIscustomize() {
		return this.iscustomize;
	}

	public void setIscustomize(String iscustomize) {
		this.iscustomize = iscustomize;
	}

}