package com.insigma.siis.local.business.entity;

public class Gbkc implements java.io.Serializable{
	private String id;
	private String a0000;
	private String checktime;
	private String checkfile;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getCheckfile() {
		return checkfile;
	}
	public void setCheckfile(String checkfile) {
		this.checkfile = checkfile;
	}
	
	public Gbkc() {
		super();
	}
	public Gbkc(String id, String a0000, String checktime, String checkfile) {
		super();
		this.id = id;
		this.a0000 = a0000;
		this.checktime = checktime;
		this.checkfile = checkfile;
	}
	@Override
	public String toString() {
		return "GBKC [id=" + id + ", a0000=" + a0000 + ", checktime=" + checktime + ", checkfile=" + checkfile + "]";
	}
	

}
