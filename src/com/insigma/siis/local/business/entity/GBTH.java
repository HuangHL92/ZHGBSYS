package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GBTH implements Serializable {
	public GBTH() {}
	
	public java.lang.String getThtime() {
		return thtime;
	}
	public void setThtime(java.lang.String thtime) {
		this.thtime = thtime;
	}
	public java.lang.String getThtext() {
		return thtext;
	}
	public void setThtext(java.lang.String thtext) {
		this.thtext = thtext;
	}
	public java.lang.String getThdx() {
		return thdx;
	}
	public void setThdx(java.lang.String thdx) {
		this.thdx = thdx;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public void setA0000(java.lang.String a0000) {
		this.a0000 = a0000;
	}
	public java.lang.String getA0000() {
	    return this.a0000;
	}
	
	
	
	@Override
	public String toString() {
		return "GBTH [id=" + id + ", a0000=" + a0000 + ", thtime=" + thtime + ", thtext=" + thtext + ", thdx=" + thdx
				+ ", name=" + name + "]";
	}
	private java.lang.String id;
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a0000 == null) ? 0 : a0000.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((thdx == null) ? 0 : thdx.hashCode());
		result = prime * result + ((thtext == null) ? 0 : thtext.hashCode());
		result = prime * result + ((thtime == null) ? 0 : thtime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GBTH other = (GBTH) obj;
		if (a0000 == null) {
			if (other.a0000 != null)
				return false;
		} else if (!a0000.equals(other.a0000))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (thdx == null) {
			if (other.thdx != null)
				return false;
		} else if (!thdx.equals(other.thdx))
			return false;
		if (thtext == null) {
			if (other.thtext != null)
				return false;
		} else if (!thtext.equals(other.thtext))
			return false;
		if (thtime == null) {
			if (other.thtime != null)
				return false;
		} else if (!thtime.equals(other.thtime))
			return false;
		return true;
	}
	private java.lang.String a0000;
	private java.lang.String thtime;
	private java.lang.String thtext;
	private java.lang.String thdx;
	private java.lang.String name;
	
}
