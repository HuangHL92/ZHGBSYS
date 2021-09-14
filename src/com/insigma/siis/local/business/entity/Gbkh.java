package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class Gbkh implements Serializable{
	private String gbkhid;
	private String a0000;
	private String year;
	private int sort;
	private String status;
	private String grade;
	public String getGbkhid() {
		return gbkhid;
	}
	public void setGbkhid(String gbkhid) {
		this.gbkhid = gbkhid;
	}
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Gbkh [gbkhid=" + gbkhid + ", a0000=" + a0000 + ", year=" + year + ", sort=" + sort + ", status="
				+ status + ", grade=" + grade + "]";
	}
	
	
}
