package com.insigma.odin.framework.privilege.entity;

import java.util.Date;

/**
 * SmtUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmtUser extends SmtPrincipal implements java.io.Serializable {

	//父类属性

	//子类属性
	private String id;
	private String passwd;
	private String loginname;
	private String dept;
	private String iplist;
	private String checkip;
	private String isleader;
	private String regionid;
	private String rate;
	private String empid;
	private String macaddr;
	private String usertype;
	private String otherinfo;
	private Date createdate;
	private String hashcode;
	private Long sortid;
	private String work;
	private String mobile;
	private String tel;
	private String email;
	private String validity;
	private String sec;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}
	// Constructors

	/** default constructor */
	public SmtUser() {
	}

	/** minimal constructor */

	
	

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getIplist() {
		return this.iplist;
	}

	public void setIplist(String iplist) {
		this.iplist = iplist;
	}

	public String getCheckip() {
		return this.checkip;
	}

	public void setCheckip(String checkip) {
		this.checkip = checkip;
	}

	public String getIsleader() {
		return this.isleader;
	}

	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}

	public String getRegionid() {
		return this.regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getEmpid() {
		return this.empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public Long getSortid() {
		return sortid;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

}