package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class Association implements Serializable{
	/**
	 * …ÁÕ≈ºÊ÷∞
	 */
	private String a0000;
	private String st00;
	private String stname;
	private String stjob;
	private String stnature;
	private String approvaldate;
	private String closingdate;
	private String sessionsnum;
	private String status;
	private String salary;
	private String startdate;
	
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	
	public String getSt00() {
		return st00;
	}
	public void setSt00(String st00) {
		this.st00 = st00;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	public String getStjob() {
		return stjob;
	}
	public void setStjob(String stjob) {
		this.stjob = stjob;
	}
	public String getStnature() {
		return stnature;
	}
	public void setStnature(String stnature) {
		this.stnature = stnature;
	}
	public String getApprovaldate() {
		return approvaldate;
	}
	public void setApprovaldate(String approvaldate) {
		this.approvaldate = approvaldate;
	}
	public String getClosingdate() {
		return closingdate;
	}
	public void setClosingdate(String closingdate) {
		this.closingdate = closingdate;
	}
	public String getSessionsnum() {
		return sessionsnum;
	}
	public void setSessionsnum(String sessionsnum) {
		this.sessionsnum = sessionsnum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	

	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public Association(String a0000, String st00, String stname, String stjob, String stnature, String approvaldate,
			String closingdate, String sessionsnum, String status, String salary) {
		super();
		this.a0000 = a0000;
		this.st00 = st00;
		this.stname = stname;
		this.stjob = stjob;
		this.stnature = stnature;
		this.approvaldate = approvaldate;
		this.closingdate = closingdate;
		this.sessionsnum = sessionsnum;
		this.status = status;
		this.salary = salary;
	}
	public Association() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Association [a0000=" + a0000 + ", st00=" + st00 + ", stname=" + stname + ", stjob=" + stjob
				+ ", stnature=" + stnature + ", approvaldate=" + approvaldate + ", closingdate=" + closingdate
				+ ", sessionsnum=" + sessionsnum + ", status=" + status + ", salary=" + salary + ", startdate="
				+ startdate + "]";
	}
	
	
	
	
	
	
	
}
