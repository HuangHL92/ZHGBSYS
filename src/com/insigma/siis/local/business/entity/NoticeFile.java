package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//֪ͨ���渽����
public class NoticeFile implements java.io.Serializable {

	// Fields

	private String id;
	private String noticeId;		//֪ͨ����ID
	private String fileName;		//�ļ�����
	private String fileUrl;			//�ļ���ַ
	private String fileSize;		//�ļ���С

	
	/** default constructor */
	public NoticeFile() {
	}

	/** full constructor */
	public NoticeFile(String id, String noticeId, String fileSize, String fileName, String fileUrl
			) {
		super();
		this.id = id;
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
		this.noticeId = noticeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
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

	

}