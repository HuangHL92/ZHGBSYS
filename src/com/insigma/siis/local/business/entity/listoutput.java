package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class listoutput implements Serializable {

	public listoutput() {
	}
	/**
	 * 主键
	 */
	private java.lang.String fid;
	
	public java.lang.String getId() {
		return fid;
	}
	public void setId(java.lang.String fid) {
		this.fid = fid;
	}
	/**
	 * 模板ID
	 */
	private java.lang.String tpid;
	
	public java.lang.String getTpid() {
		return tpid;
	}
	public void setTpid(java.lang.String tpid) {
		this.tpid = tpid;
	}
	/**
	 * 模板名称
	 */
	private java.lang.String tpname;
	
	public java.lang.String getTpname() {
		return tpname;
	}
	public void setTpname(java.lang.String tpname) {
		this.tpname = tpname;
	}
	/**
	 * 模板类型 1 标准， 2 自定义，3 导入',
	 */
	private java.lang.String tptype;
	
	public java.lang.String getTptype() {
		return tptype;
	}
	public void setTptype(java.lang.String tptype) {
		this.tptype = tptype;
	}
	/**
	 * 信息项中文
	 */
	private java.lang.String messagec;
	
	public java.lang.String getMessagec() {
		return messagec;
	}
	public void setMessagec(java.lang.String messagec) {
		this.messagec = messagec;
	}
	/**
	 * 信息项英文
	 */
	private java.lang.String messagee;
	
	public java.lang.String getMessagee() {
		return messagee;
	}
	public void setMessagee(java.lang.String messagee) {
		this.messagee = messagee;
	}
	/**
	 * 所在行
	 */
	private java.lang.String zbrow;
	
	public java.lang.String getZbrow() {
		return zbrow;
	}
	public void setZbrow(java.lang.String zbrow) {
		this.zbrow = zbrow;
	}
	/**
	 * 所在列
	 */
	private java.lang.String zbline;
	
	public java.lang.String getZbline() {
		return zbline;
	}
	public void setZbline(java.lang.String zbline) {
		this.zbline = zbline;
	}
	/**
	 * 设置年龄计算终点
	 */
	private java.lang.String endtime;

	public java.lang.String getEndtime() {
		return endtime;
	}
	public void setEndtime(java.lang.String endtime) {
		this.endtime = endtime;
	}
	
	/**
	 * 模板种类 
	 */
	private java.lang.String tpkind;

	public java.lang.String getTpkind() {
		return tpkind;
	}
	public void setTpkind(java.lang.String tpkind) {
		this.tpkind = tpkind;
	}
	
}
























