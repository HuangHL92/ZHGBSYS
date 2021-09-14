package com.insigma.odin.framework.privilege.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * �û���VO��
 * @author jinwei
 * @version 1.0
 * @created 18-����-2009 11:48:08
 */
public class GroupVO extends PrincipalVO implements Serializable{

	/**
	 * ���飨���ţ�id
	 */
	private String parentid;
	/**
	 * ϵͳ��������
	 */
	private String org;
	/**
	 * ��������
	 */
	private String districtcode;
	/**
	 * ����
	 */
	private String license;
	/**
	 * ����������
	 */
	private String principal;
	/**
	 * ���
	 */
	private String shortname;
	/**
	 * ��ַ
	 */
	private String address;
	/**
	 * �绰
	 */
	private String tel;
	/**
	 * ��ϵ��
	 */
	private String linkman;
	/**
	 * ����
	 */
	private String type;
	/**
	 * ���ܲ���
	 */
	private String chargedept;
	/**
	 * ������Ϣ(��չʹ��)
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
	 * ��������ļ��� ���� ��aa26��Ӧ
	 */
	private String rate;
	
	private Long sortid;
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChargedept() {
		return chargedept;
	}

	public void setChargedept(String chargedept) {
		this.chargedept = chargedept;
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

	public GroupVO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getSortid() {
		return sortid;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

}