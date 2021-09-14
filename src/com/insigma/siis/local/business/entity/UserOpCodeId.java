package com.insigma.siis.local.business.entity;

/**
 * UserOpCodeId entity. @author MyEclipse Persistence Tools
 */

public class UserOpCodeId implements java.io.Serializable {

	// Fields

	private String userid;
	private String codeType;
	private String codeValue;

	// Constructors

	/** default constructor */
	public UserOpCodeId() {
	}

	/** full constructor */
	public UserOpCodeId(String userid, String codeType, String codeValue) {
		this.userid = userid;
		this.codeType = codeType;
		this.codeValue = codeValue;
	}

	// Property accessors

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserOpCodeId))
			return false;
		UserOpCodeId castOther = (UserOpCodeId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
				&& ((this.getCodeType() == castOther.getCodeType()) || (this
						.getCodeType() != null
						&& castOther.getCodeType() != null && this
						.getCodeType().equals(castOther.getCodeType())))
				&& ((this.getCodeValue() == castOther.getCodeValue()) || (this
						.getCodeValue() != null
						&& castOther.getCodeValue() != null && this
						.getCodeValue().equals(castOther.getCodeValue())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		result = 37 * result
				+ (getCodeValue() == null ? 0 : this.getCodeValue().hashCode());
		return result;
	}

}