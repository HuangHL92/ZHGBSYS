package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//֪ͨ���渽����
public class NoticeFileVo implements java.io.Serializable {

	// Fields

	private String id;
	private String noticeId;		//֪ͨ����ID
	private String fileName;		//�ļ�����
	private String fileUrl;			//�ļ���ַ
	private String fileSize;		//�ļ���С
	
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