package com.insigma.odin.framework.privilege.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * �û�VO��
 * @author jinwei
 * @version 1.0
 * @created 18-����-2009 11:48:15
 */
public class UserVO extends PrincipalVO implements Serializable,Cloneable{
	
	@Override
	public UserVO clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (UserVO)super.clone();
	}
	
	/**
	 * ����
	 */
	private String passwd;
	/**
	 * ��¼��
	 */
	private String loginname;
	/**
	 * ����
	 */
	private String dept;
	/**
	 * IPLIST
	 */
	private String iplist;
	/**
	 * �Ƿ���IP
	 */
	private String checkip;
	/**
	 * �Ƿ��쵼
	 */
	private String isleader;
	/**
	 * �������
	 */
	private String regionid;
	/**
	 * ����2
	 */
	private String rate;
	/**
	 * ����1
	 */
	private String empid;
	/**
	 * ������ַ
	 */
	private String macaddr;
	/**
	 * �û����
	 */
	private String usertype;
	/**
	 * ������Ϣ
	 */
	private String otherinfo;
	/**
	 * ����ʱ��
	 */
	private Date createdate;
	/**
	 * ɢ��ֵ
	 */
	private String hashcode;
	/**
	 * �û�����
	 */
	private Long sortid;
	/**
	 * ������λ
	 */
	private String work;
	/**
	 * �ֻ�
	 */
	private String mobile;
	/**
	 * �칫�绰
	 */
	private String tel;
	/**
	 * �ʼ�
	 */
	private String email;
	/**
	 * ��Ч��
	 */
	private String validity;
	
	private String sec;
	
	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public UserVO(){

	}

	public void finalize() throws Throwable {

	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIplist() {
		return iplist;
	}

	public void setIplist(String iplist) {
		this.iplist = iplist;
	}

	public String getCheckip() {
		return checkip;
	}

	public void setCheckip(String checkip) {
		this.checkip = checkip;
	}

	public String getIsleader() {
		return isleader;
	}

	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return hashcode;
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