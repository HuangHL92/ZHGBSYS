package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class A05 implements Serializable {

	/**
	 * 职务职级信息集 tongzj
	 */
	private static long serialVersionUID = 6366112080921441600L;
	private String a0000;
	private String a0500;
	private String a0531;
	private String a0501b;
	private String a0501b2;
	private String a0504;
	private String a0511;
	private String a0517;
	private String a0524;
	private String a0525;

	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getA0500() {
		return a0500;
	}
	public String getA0501b2() {
		return a0501b2;
	}
	public void setA0501b2(String a0501b2) {
		this.a0501b2 = a0501b2;
	}
	public void setA0500(String a0500) {
		this.a0500 = a0500;
	}
	public String getA0531() {
		return a0531;
	}
	public void setA0531(String a0531) {
		this.a0531 = a0531;
	}
	public String getA0501b() {
		return a0501b;
	}
	public void setA0501b(String a0501b) {
		this.a0501b = a0501b;
	}
	
	public String getA0504() {
		return a0504;
	}
	public void setA0504(String a0504) {
		this.a0504 = a0504;
	}
	public String getA0511() {
		return a0511;
	}
	public void setA0511(String a0511) {
		this.a0511 = a0511;
	}
	public String getA0517() {
		return a0517;
	}
	public void setA0517(String a0517) {
		this.a0517 = a0517;
	}
	public String getA0524() {
		return a0524;
	}
	public void setA0524(String a0524) {
		this.a0524 = a0524;
	}
	public String getA0525() {
		return a0525;
	}
	public void setA0525(String a0525) {
		this.a0525 = a0525;
	}
	public A05(String a0000, String a0500, String a0531, String a0501b,String a0501b2, String a0504, String a0511, String a0517,
			String a0524,String a0525) {
		super();
		this.a0000 = a0000;
		this.a0500 = a0500;
		this.a0531 = a0531;
		this.a0501b = a0501b;
		this.a0501b2 = a0501b2;
		this.a0504 = a0504;
		this.a0511 = a0511;
		this.a0517 = a0517;
		this.a0524 = a0524;
		this.a0525 = a0525;
	}
	public A05() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*@Override
	public String toString() {
		return "A05 [a0000=" + a0000 + ", a0500=" + a0500 + ", a0531=" + a0531 + ", a0501b=" + a0501b + ", a0504="
				+ a0504 + ", a0511=" + a0511 + ", a0517=" + a0517 + ", a0524=" + a0524 + ", a0525=" + a0525 +"]";
	}*/
	@Override
	public String toString() {
		return "A05 [a0000=" + a0000 + ", a0500=" + a0500 + ", a0531=" + a0531 + ", a0501b=" + a0501b + ", a0501b2="
				+ a0501b2 + ", a0504=" + a0504 + ", a0511=" + a0511 + ", a0517=" + a0517 + ", a0524=" + a0524
				+ ", a0525=" + a0525 + "]";
	}
	
	
	
	

}
