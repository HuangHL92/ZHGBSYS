package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;

/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class Policy implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String fileSize;
	private String fileName;
	private String fileUrl;
	private String updateTime;
	private String userName;
	private String a0000;
	private String remark;
	private String secret;

	
	/** default constructor */
	public Policy() {
	}

	/** full constructor */
	public Policy(String id, String title, String fileSize, String fileName, String fileUrl, String updateTime, String userName,
			String a0000, String remark, String secret) {
		super();
		this.id = id;
		this.a0000 = a0000;
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
		this.a0000 = a0000;
		this.updateTime = updateTime;
		this.userName = userName;
		this.remark = remark;
		this.secret = secret;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getFileSize() {
		return fileSize;
	}



	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getFileUrl() {
		return fileUrl;
	}



	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}



	public String getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getA0000() {
		return a0000;
	}



	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	
	

}