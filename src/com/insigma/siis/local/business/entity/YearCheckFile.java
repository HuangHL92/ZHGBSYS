package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class YearCheckFile implements java.io.Serializable {

	// Fields

	private String id;
	private String fileName;		//文件名称
	private String fileUrl;			//文件地址
	private String fileSize;		//文件大小

	
	/** default constructor */
	public YearCheckFile() {
	}

	/** full constructor */
	public YearCheckFile(String id, String fileSize, String fileName, String fileUrl
			) {
		super();
		this.setId(id);
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

}