package com.insigma.siis.local.business.entity;

import java.io.Serializable;
/**
 * 备注信息集
 * @author tongzj  2017/5/30
 *
 */
public class A71 implements Serializable {
	private String a0000;
	private String a7100;
	private String a7101;
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getA7100() {
		return a7100;
	}
	public void setA7100(String a7100) {
		this.a7100 = a7100;
	}
	public String getA7101() {
		return a7101;
	}
	public void setA7101(String a7101) {
		this.a7101 = a7101;
	}
	public A71(String a0000, String a7100, String a7101) {
		super();
		this.a0000 = a0000;
		this.a7100 = a7100;
		this.a7101 = a7101;
	}
	public A71() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "A71 [a0000=" + a0000 + ", a7100=" + a7100 + ", a7101=" + a7101 + "]";
	}
	
	
}
