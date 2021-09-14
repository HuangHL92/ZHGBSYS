package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class GBXLH implements Serializable {
	public GBXLH() {}
	
	public java.lang.String getA0000() {
		return a0000;
	}
	public void setA0000(java.lang.String a0000) {
		this.a0000 = a0000;
	}
	public java.lang.String getXlhtime() {
		return xlhtime;
	}
	public void setXlhtime(java.lang.String xlhtime) {
		this.xlhtime = xlhtime;
	}
	public java.lang.String getXlhtext() {
		return xlhtext;
	}
	public void setXlhtext(java.lang.String xlhtext) {
		this.xlhtext = xlhtext;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a0000 == null) ? 0 : a0000.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((xlhtext == null) ? 0 : xlhtext.hashCode());
		result = prime * result + ((xlhtime == null) ? 0 : xlhtime.hashCode());
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
		GBXLH other = (GBXLH) obj;
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
		if (xlhtext == null) {
			if (other.xlhtext != null)
				return false;
		} else if (!xlhtext.equals(other.xlhtext))
			return false;
		if (xlhtime == null) {
			if (other.xlhtime != null)
				return false;
		} else if (!xlhtime.equals(other.xlhtime))
			return false;
		return true;
	}
	private java.lang.String id;
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GBXLH [a0000=" + a0000 + ", xlhtime=" + xlhtime + ", xlhtext=" + xlhtext + ", name=" + name + "]";
	}
	private java.lang.String a0000;
	private java.lang.String xlhtime;
	private java.lang.String xlhtext;
	private java.lang.String name;
}
