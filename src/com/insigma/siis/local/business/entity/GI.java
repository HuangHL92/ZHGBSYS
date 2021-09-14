package com.insigma.siis.local.business.entity;

import java.util.Date;

public class GI implements java.io.Serializable{
	private String giid;
	private String xjqy;
	private String tjlx;
	private String filesize;
	private String filename;
	private String fileurl;
	private String userid;
	private java.util.Date createdon;
	public GI() {
		super();
		this.giid = giid;
		this.xjqy = xjqy;
		this.tjlx = tjlx;
		this.filesize = filesize;
		this.filename = filename;
		this.fileurl = fileurl;
		this.createdon = createdon;
		this.userid = userid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getGiid() {
		return giid;
	}
	public void setGiid(String giid) {
		this.giid = giid;
	}
	public String getXjqy() {
		return xjqy;
	}
	public void setXjqy(String xjqy) {
		this.xjqy = xjqy;
	}
	public String getTjlx() {
		return tjlx;
	}
	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public java.util.Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(java.util.Date createdon) {
		this.createdon = createdon;
	}
	
}
