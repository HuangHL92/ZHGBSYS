package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class NoticeVo implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String see;
	
	public String getId() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTITLE(String title) {
		this.title = title;
	}
	public String getSee() {
		return see;
	}
	public void setSEE(String see) {
		this.see = see;
	}
	
}