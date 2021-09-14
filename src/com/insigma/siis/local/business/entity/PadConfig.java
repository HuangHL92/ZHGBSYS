package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class PadConfig implements Serializable, Cloneable{
	private java.lang.String name;
	private java.lang.String value;
	
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getValue() {
		return value;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	public PadConfig(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public PadConfig() {
		super();
	}
	
}
