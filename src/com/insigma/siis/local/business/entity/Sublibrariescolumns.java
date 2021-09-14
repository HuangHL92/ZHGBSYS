package com.insigma.siis.local.business.entity;



import java.io.Serializable;
public class Sublibrariescolumns implements Serializable {
	private SublibrariescolumnsId id;
	private Integer columns_no;
	
	
	public Sublibrariescolumns() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Sublibrariescolumns(SublibrariescolumnsId id, Integer columns_no) {
		super();
		this.id = id;
		this.columns_no = columns_no;
	}


	public SublibrariescolumnsId getId() {
		return id;
	}


	public void setId(SublibrariescolumnsId id) {
		this.id = id;
	}


	public Integer getColumns_no() {
		return columns_no;
	}


	public void setColumns_no(Integer columns_no) {
		this.columns_no = columns_no;
	}
	
	
	
	
}
