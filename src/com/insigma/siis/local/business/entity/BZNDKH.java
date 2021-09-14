package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class BZNDKH implements Serializable{
	//°à×ÓÄê¶È¿¼ºË
	private String b0111;
	private String bz00;
	private String year;
	private String ndkhjg;
	
	
	
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getNdkhjg() {
		return ndkhjg;
	}
	public void setNdkhjg(String ndkhjg) {
		this.ndkhjg = ndkhjg;
	}
	
	public BZNDKH(String b0111, String bz00, String year, String ndkhjg) {
		super();
		this.b0111 = b0111;
		this.bz00 = bz00;
		this.year = year;
		this.ndkhjg = ndkhjg;
	}
	public BZNDKH() {
		super();
	}
	@Override
	public String toString() {
		return "BZNDKH [b0111=" + b0111 + ", bz00=" + bz00 + ", year=" + year + ", ndkhjg=" + ndkhjg + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
