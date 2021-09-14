package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class QXSBZFX implements Serializable{
	//°à×Ó·ÖÎö
	private String bz00;
	private String b0111;
	private String bzfx;
	private String tpjy;
	private String b01id;
	private String ztpj;
	private String bzbz;
	private String yhjy;
	private String jbgk;
	private String fzdw;
	
	
	public String getJbgk() {
		return jbgk;
	}
	public void setJbgk(String jbgk) {
		this.jbgk = jbgk;
	}
	public String getFzdw() {
		return fzdw;
	}
	public void setFzdw(String fzdw) {
		this.fzdw = fzdw;
	}
	public String getZtpj() {
		return ztpj;
	}
	public void setZtpj(String ztpj) {
		this.ztpj = ztpj;
	}
	public String getBzbz() {
		return bzbz;
	}
	public void setBzbz(String bzbz) {
		this.bzbz = bzbz;
	}
	public String getYhjy() {
		return yhjy;
	}
	public void setYhjy(String yhjy) {
		this.yhjy = yhjy;
	}
	public String getBz00() {
		return bz00;
	}
	public void setBz00(String bz00) {
		this.bz00 = bz00;
	}
	public String getB0111() {
		return b0111;
	}
	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}
	public String getBzfx() {
		return bzfx;
	}
	public void setBzfx(String bzfx) {
		this.bzfx = bzfx;
	}
	public String getTpjy() {
		return tpjy;
	}
	public void setTpjy(String tpjy) {
		this.tpjy = tpjy;
	}
	public String getB01id() {
		return b01id;
	}
	public void setB01id(String b01id) {
		this.b01id = b01id;
	}
	public QXSBZFX(String bz00, String b0111, String bzfx, String tpjy, String b01id) {
		super();
		this.bz00 = bz00;
		this.b0111 = b0111;
		this.bzfx = bzfx;
		this.tpjy = tpjy;
		this.b01id = b01id;
	}
	public QXSBZFX() {
		super();
	}
	@Override
	public String toString() {
		return "QXSBZFX [bz00=" + bz00 + ", b0111=" + b0111 + ", bzfx=" + bzfx + ", tpjy=" + tpjy + ", b01id=" + b01id
				+ "]";
	}
	
	
	
	
	
	
	
	
}
