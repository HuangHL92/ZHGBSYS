package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class PersionFile implements java.io.Serializable {

	// Fields

	private String id;
	private String fileName;		//�ļ�����
	private String fileUrl;			//�ļ���ַ
	private String fileSize;		//�ļ���С
	private String filesort;		//�ļ����
	private String a0000;		    //��Աid
	private String time;		//ʱ��
	
	/** default constructor */
	public PersionFile() {
	}

	/** full constructor */
	public PersionFile(String id, String fileSize, String fileName, String fileUrl,String filesort,String a0000,String time
			) {
		super();
		this.setId(id);
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
		this.filesort = filesort;
		this.a0000 = a0000;
		this.setTime(time);
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

	public String getFilesort() {
		return filesort;
	}

	public void setFilesort(String filesort) {
		this.filesort = filesort;
	}


	public String getA0000() {
		return a0000;
	}

	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	

}