package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class NoticeListVo implements java.io.Serializable {

	// Fields

	private String id;
	private String content;
	private String dateShow;
	private String see;
	
	
	public String getId() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setCONTENT(String content) {
		this.content = content;
	}
	
	public String getSee() {
		return see;
	}
	public void setSEE(String see) {
		this.see = see;
	}
	public String getDateShow() {
		return dateShow;
	}
	public void setDATESHOW(String dateShow) {
		this.dateShow = dateShow;
	}
	
	
	
}