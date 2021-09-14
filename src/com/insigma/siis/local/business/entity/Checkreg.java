package com.insigma.siis.local.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Checkreg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkregid;
	private String groupid;
	private String groupname;
	private String regno;
	private String regname;
	private String checkdate;
	private String regstatus;
	private String publishtime;
	private String reguser;
	private String userid;
	
	private String regtype;
	private String regotherid;
	public String getRegtype() {
		return regtype;
	}
	public String getRegotherid() {
		return regotherid;
	}
	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}
	public void setRegotherid(String regotherid) {
		this.regotherid = regotherid;
	}
	private java.sql.Timestamp createtime;
	public String getCheckregid() {
		return checkregid;
	}
	public String getRegno() {
		return regno;
	}
	public String getRegname() {
		return regname;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public String getRegstatus() {
		return regstatus;
	}
	public String getPublishtime() {
		return publishtime;
	}
	public String getReguser() {
		return reguser;
	}
	public String getUserid() {
		return userid;
	}
	public void setCheckregid(String checkregid) {
		this.checkregid = checkregid;
	}
	public String getGroupid() {
		return groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public void setRegname(String regname) {
		this.regname = regname;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	public void setReguser(String reguser) {
		this.reguser = reguser;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public java.sql.Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(java.sql.Timestamp createtime) {
		this.createtime = createtime;
	}
	
	private String fcxx;
	private String crjxx;
	private String gpjjxx;
	private String sybxxx;
	private String gsxx1;
	private String gsxx2;
	public String getFcxx() {
		return fcxx;
	}
	public String getCrjxx() {
		return crjxx;
	}
	public String getGpjjxx() {
		return gpjjxx;
	}
	public String getSybxxx() {
		return sybxxx;
	}
	public String getGsxx1() {
		return gsxx1;
	}
	public String getGsxx2() {
		return gsxx2;
	}
	public void setFcxx(String fcxx) {
		this.fcxx = fcxx;
	}
	public void setCrjxx(String crjxx) {
		this.crjxx = crjxx;
	}
	public void setGpjjxx(String gpjjxx) {
		this.gpjjxx = gpjjxx;
	}
	public void setSybxxx(String sybxxx) {
		this.sybxxx = sybxxx;
	}
	public void setGsxx1(String gsxx1) {
		this.gsxx1 = gsxx1;
	}
	public void setGsxx2(String gsxx2) {
		this.gsxx2 = gsxx2;
	}
	
	private String gacltx;
	private String zjjcltx;
	private String zjhcltx;
	private String gscltx;
	public String getGacltx() {
		return gacltx;
	}
	public String getZjjcltx() {
		return zjjcltx;
	}
	public String getZjhcltx() {
		return zjhcltx;
	}
	public void setGacltx(String gacltx) {
		this.gacltx = gacltx;
	}
	public void setZjjcltx(String zjjcltx) {
		this.zjjcltx = zjjcltx;
	}
	public void setZjhcltx(String zjhcltx) {
		this.zjhcltx = zjhcltx;
	}
	public String getGscltx() {
		return gscltx;
	}
	public void setGscltx(String gscltx) {
		this.gscltx = gscltx;
	}
	
	
	
}
