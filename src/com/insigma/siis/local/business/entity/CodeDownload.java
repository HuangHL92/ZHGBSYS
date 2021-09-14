package com.insigma.siis.local.business.entity;

/**
 * CodeDownload entity. @author MyEclipse Persistence Tools
 */

public class CodeDownload implements java.io.Serializable {

	// Fields

	private Integer codeValueSeq;
	private String downloadStatus;

	// Constructors

	/** default constructor */
	public CodeDownload() {
	}

	/** full constructor */
	public CodeDownload(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	// Property accessors

	public Integer getCodeValueSeq() {
		return this.codeValueSeq;
	}

	public void setCodeValueSeq(Integer codeValueSeq) {
		this.codeValueSeq = codeValueSeq;
	}

	public String getDownloadStatus() {
		return this.downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

}