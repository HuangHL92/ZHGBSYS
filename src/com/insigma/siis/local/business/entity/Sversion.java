package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class Sversion implements Serializable{
	
	private String v_name;
	
	private String v_time;
	
	private Integer v_seq;

	public String getV_name() {
		return v_name;
	}

	public void setV_name(String v_name) {
		this.v_name = v_name;
	}

	public String getV_time() {
		return v_time;
	}

	public void setV_time(String v_time) {
		this.v_time = v_time;
	}

	public Integer getV_seq() {
		return v_seq;
	}

	public void setV_seq(Integer v_seq) {
		this.v_seq = v_seq;
	}
	
	
}
