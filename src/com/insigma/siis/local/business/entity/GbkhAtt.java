package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class GbkhAtt implements Serializable {
	private String pat00;
	private String gbkhid;
	private String pat04;
	private String pat07;
	private int sort;
	private String status;
	public String getPat00() {
		return pat00;
	}
	public void setPat00(String pat00) {
		this.pat00 = pat00;
	}
	public String getGbkhid() {
		return gbkhid;
	}
	public void setGbkhid(String gbkhid) {
		this.gbkhid = gbkhid;
	}
	public String getPat04() {
		return pat04;
	}
	public void setPat04(String pat04) {
		this.pat04 = pat04;
	}
	public String getPat07() {
		return pat07;
	}
	public void setPat07(String pat07) {
		this.pat07 = pat07;
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
	@Override
	public String toString() {
		return "GbkhAtt [pat00=" + pat00 + ", gbkhid=" + gbkhid + ", pat04=" + pat04 + ", pat07=" + pat07 + ", sort="
				+ sort + ", status=" + status + "]";
	}
	

}
