package com.insigma.siis.local.business.modeldb;



import java.io.Serializable;
public class SublibrariescolumnsDTO implements Serializable {
	private SublibrariescolumnsIdDTO id;
	private Integer columns_no;
	
	
	public SublibrariescolumnsDTO() {
		super();
	}


	public SublibrariescolumnsDTO(SublibrariescolumnsIdDTO id, Integer columns_no) {
		super();
		this.id = id;
		this.columns_no = columns_no;
	}


	public SublibrariescolumnsIdDTO getId() {
		return id;
	}


	public void setId(SublibrariescolumnsIdDTO id) {
		this.id = id;
	}


	public Integer getColumns_no() {
		return columns_no;
	}


	public void setColumns_no(Integer columns_no) {
		this.columns_no = columns_no;
	}
	
	
	
	
}
