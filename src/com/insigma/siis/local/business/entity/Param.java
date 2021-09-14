package com.insigma.siis.local.business.entity;

public class Param {

	private String name;
	
	private String type;
	
	private String value;
	
	private String desc;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Param() {
		super();
	}

	public Param(String name, String type, String value,String desc) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
		this.desc = desc;
	}
	
}
