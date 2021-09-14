package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class BZZXKH implements Serializable{
	//°à×Ó×¨Ïî¿¼ºË
	private String b0111;
	private String bz00;
	private String zxkhm;
	private String zxkhjg;
	private String zxkhsj;
	
	
	public String getB0111() {
		return b0111;
	}
	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}
	public String getBz00() {
		return bz00;
	}
	public void setBz00(String bz00) {
		this.bz00 = bz00;
	}
	public String getZxkhm() {
		return zxkhm;
	}
	public void setZxkhm(String zxkhm) {
		this.zxkhm = zxkhm;
	}
	public String getZxkhjg() {
		return zxkhjg;
	}
	public void setZxkhjg(String zxkhjg) {
		this.zxkhjg = zxkhjg;
	}
	
	public String getZxkhsj() {
		return zxkhsj;
	}
	public void setZxkhsj(String zxkhsj) {
		this.zxkhsj = zxkhsj;
	}
	
	public BZZXKH() {
		super();
	}
	
	public BZZXKH(String b0111, String bz00, String zxkhm, String zxkhjg, String zxkhsj) {
		super();
		this.b0111 = b0111;
		this.bz00 = bz00;
		this.zxkhm = zxkhm;
		this.zxkhjg = zxkhjg;
		this.zxkhsj = zxkhsj;
	}
	@Override
	public String toString() {
		return "BZZXKH [b0111=" + b0111 + ", bz00=" + bz00 + ", zxkhm=" + zxkhm + ", zxkhjg=" + zxkhjg + ", zxkhsj="
				+ zxkhsj + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
