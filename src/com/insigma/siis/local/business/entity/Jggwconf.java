package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class Jggwconf implements Serializable {

	/**
	 * 机构岗位配置信息
	 */
	private static final long serialVersionUID = -7250292384880135165L;
	private String jggwconfid;
	private String b0111;
	private String b0101;
	private String b01id;
	public String getB01id() {
		return b01id;
	}
	public void setB01id(String b01id) {
		this.b01id = b01id;
	}
	private String gwcode;
	private String gwname;
	private Long gwnum;
	private String zjcode;
	private String zwcode;
	private String a0219;
	private String a0201d;
	private String a0201e;
	public String getJggwconfid() {
		return jggwconfid;
	}
	public String getB0111() {
		return b0111;
	}
	public String getB0101() {
		return b0101;
	}
	public String getGwcode() {
		return gwcode;
	}
	public String getGwname() {
		return gwname;
	}
	public Long getGwnum() {
		return gwnum;
	}
	public String getZjcode() {
		return zjcode;
	}
	public String getZwcode() {
		return zwcode;
	}
	public void setJggwconfid(String jggwconfid) {
		this.jggwconfid = jggwconfid;
	}
	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}
	public void setB0101(String b0101) {
		this.b0101 = b0101;
	}
	public void setGwcode(String gwcode) {
		this.gwcode = gwcode;
	}
	public void setGwname(String gwname) {
		this.gwname = gwname;
	}
	public void setGwnum(Long gwnum) {
		this.gwnum = gwnum;
	}
	public void setZjcode(String zjcode) {
		this.zjcode = zjcode;
	}
	public void setZwcode(String zwcode) {
		this.zwcode = zwcode;
	}
	
	public String getA0201e() {
		return a0201e;
	}
	public void setA0201e(String a0201e) {
		this.a0201e = a0201e;
	}
	public String getA0219() {
		return a0219;
	}
	public String getA0201d() {
		return a0201d;
	}
	public void setA0219(String a0219) {
		this.a0219 = a0219;
	}
	public void setA0201d(String a0201d) {
		this.a0201d = a0201d;
	}
	@Override
	public String toString() {
		return "Jggwconf [jggwconfid=" + jggwconfid + ", b0111=" + b0111 + ", b0101=" + b0101 + ", gwcode=" + gwcode
				+ ", gwname=" + gwname + ", gwnum=" + gwnum + ", zjcode=" + zjcode + ", zwcode=" + zwcode + "]";
	}
	private String iscount;
	private String status;
	public String getIscount() {
		return iscount;
	}
	public String getStatus() {
		return status;
	}
	public void setIscount(String iscount) {
		this.iscount = iscount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
