package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * UserOpCode entity. @author MyEclipse Persistence Tools
 */

public class UserOpCode implements java.io.Serializable {

	// Fields

	private UserOpCodeId id;
	private BigDecimal codeCount;
	private String status;
	private Date createTime;

	// Constructors

	/** default constructor */
	public UserOpCode() {
	}

	/** minimal constructor */
	public UserOpCode(UserOpCodeId id, BigDecimal codeCount, String status) {
		this.id = id;
		this.codeCount = codeCount;
		this.status = status;
	}

	/** full constructor */
	public UserOpCode(UserOpCodeId id, BigDecimal codeCount, String status,
			Date createTime) {
		this.id = id;
		this.codeCount = codeCount;
		this.status = status;
		this.createTime = createTime;
	}

	// Property accessors

	public UserOpCodeId getId() {
		return this.id;
	}

	public void setId(UserOpCodeId id) {
		this.id = id;
	}

	public BigDecimal getCodeCount() {
		return this.codeCount;
	}

	public void setCodeCount(BigDecimal codeCount) {
		this.codeCount = codeCount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}