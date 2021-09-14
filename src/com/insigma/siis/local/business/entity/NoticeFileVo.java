package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//通知公告附件表
public class NoticeFileVo implements java.io.Serializable {

	// Fields

	private String id;
	private String noticeId;		//通知公告ID
	private String fileName;		//文件名称
	private String fileUrl;			//文件地址
	private String fileSize;		//文件大小
	
	public String getId() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNOTICEID(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFILENAME(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFILEURL(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFILESIZE(String fileSize) {
		this.fileSize = fileSize;
	}

	

}