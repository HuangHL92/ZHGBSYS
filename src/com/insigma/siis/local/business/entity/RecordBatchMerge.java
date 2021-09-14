package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;


public class RecordBatchMerge implements java.io.Serializable {
	
	private String rbmId;
	private String rbmName;
	private String rbmDate;
	private String rbmType;
	private String rbmUserid;
	private String rbmNo;
	private String rbmApplicant;
	private String rbmOrg;
	private BigDecimal rbmSysno;
	private String rbmCdate;
	private String rbmMeettype;
	private String rbmDeploytype;
	
	
	public String getRbmId() {
		return rbmId;
	}
	public void setRbmId(String rbmId) {
		this.rbmId = rbmId;
	}
	public String getRbmName() {
		return rbmName;
	}
	public void setRbmName(String rbmName) {
		this.rbmName = rbmName;
	}
	public String getRbmDate() {
		return rbmDate;
	}
	public void setRbmDate(String rbmDate) {
		this.rbmDate = rbmDate;
	}
	public String getRbmType() {
		return rbmType;
	}
	public void setRbmType(String rbmType) {
		this.rbmType = rbmType;
	}
	public String getRbmUserid() {
		return rbmUserid;
	}
	public void setRbmUserid(String rbmUserid) {
		this.rbmUserid = rbmUserid;
	}
	public String getRbmNo() {
		return rbmNo;
	}
	public void setRbmNo(String rbmNo) {
		this.rbmNo = rbmNo;
	}
	public String getRbmApplicant() {
		return rbmApplicant;
	}
	public void setRbmApplicant(String rbmApplicant) {
		this.rbmApplicant = rbmApplicant;
	}
	public String getRbmOrg() {
		return rbmOrg;
	}
	public void setRbmOrg(String rbmOrg) {
		this.rbmOrg = rbmOrg;
	}
	public BigDecimal getRbmSysno() {
		return rbmSysno;
	}
	public void setRbmSysno(BigDecimal rbmSysno) {
		this.rbmSysno = rbmSysno;
	}
	public String getRbmCdate() {
		return rbmCdate;
	}
	public void setRbmCdate(String rbmCdate) {
		this.rbmCdate = rbmCdate;
	}
	public String getRbmMeettype() {
		return rbmMeettype;
	}
	public void setRbmMeettype(String rbmMeettype) {
		this.rbmMeettype = rbmMeettype;
	}
	public String getRbmDeploytype() {
		return rbmDeploytype;
	}
	public void setRbmDeploytype(String rbmDeploytype) {
		this.rbmDeploytype = rbmDeploytype;
	}
	private String rbmDept;
	public String getRbmDept() {
		return rbmDept;
	}

	public void setRbmDept(String rbmDept) {
		this.rbmDept = rbmDept;
	}
}
