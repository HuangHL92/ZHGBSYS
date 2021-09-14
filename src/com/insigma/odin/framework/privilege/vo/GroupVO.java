package com.insigma.odin.framework.privilege.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户组VO类
 * @author jinwei
 * @version 1.0
 * @created 18-九月-2009 11:48:08
 */
public class GroupVO extends PrincipalVO implements Serializable{

	/**
	 * 父组（部门）id
	 */
	private String parentid;
	/**
	 * 系统机构编码
	 */
	private String org;
	/**
	 * 地区代码
	 */
	private String districtcode;
	/**
	 * 保留
	 */
	private String license;
	/**
	 * 机构负责人
	 */
	private String principal;
	/**
	 * 简称
	 */
	private String shortname;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 主管部门
	 */
	private String chargedept;
	/**
	 * 其它信息(扩展使用)
	 */
	private String otherinfo;
	/**
	 * 创建时间
	 */
	private Date createdate;
	/**
	 * 散列值
	 */
	private String hashcode;
	
	/**
	 * 宁波：组的级别 —— 与aa26对应
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