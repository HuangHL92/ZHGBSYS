package com.insigma.siis.local.business.entity;

import java.io.Serializable;
import java.util.Date;

public class PAKH_SCHEME implements Serializable{
	private String ps00;
	private String ps01;
	private String ps02;
	private Date ps03;
	private String ps04;
	
	
	
	
	public String getPs00() {
		return ps00;
	}




	public void setPs00(String ps00) {
		this.ps00 = ps00;
	}




	public String getPs01() {
		return ps01;
	}




	public void setPs01(String ps01) {
		this.ps01 = ps01;
	}




	public String getPs02() {
		return ps02;
	}




	public void setPs02(String ps02) {
		this.ps02 = ps02;
	}




	public Date getPs03() {
		return ps03;
	}




	public void setPs03(Date ps03) {
		this.ps03 = ps03;
	}




	public String getPs04() {
		return ps04;
	}




	public void setPs04(String ps04) {
		this.ps04 = ps04;
	}




	@Override
	public String toString() {
		return "PAKH_SCHEME [ps00=" + ps00 + ", ps01=" + ps01 + ", ps02=" + ps02 + ", ps03=" + ps03 + ", ps04=" + ps04
				+ ", getPs00()=" + getPs00() + ", getPs01()=" + getPs01() + ", getPs02()=" + getPs02() + ", getPs03()="
				+ getPs03() + ", getPs04()=" + getPs04() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
		

}
