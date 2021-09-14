package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class Notice implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String text;
	private String fileName;
	private String fileUrl;
	private String fileSize;
	private String updateTime;
	private String a0000;
	private String a0000Name;
	private String b0111;
	private String secret;
	
	
	/** default constructor */
	public Notice() {
	}

	/** full constructor */
	public Notice(String id, String title, String fileSize, String fileName, String fileUrl, String updateTime, String a0000Name,
			String a0000, String b0111, String text, String secret) {
		super();
		this.id = id;
		this.a0000 = a0000;
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
		this.a0000 = a0000;
		this.updateTime = updateTime;
		this.a0000Name = a0000Name;
		this.b0111 = b0111;
		this.text = text;
		this.setSecret(secret);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getA0000() {
		return a0000;
	}

	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}

	public String getA0000Name() {
		return a0000Name;
	}

	public void setA0000Name(String a0000Name) {
		this.a0000Name = a0000Name;
	}

	public String getB0111() {
		return b0111;
	}

	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	

}