package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;

/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class Folder implements java.io.Serializable {

	// Fields

	private String id;
	private String catalog;
	private String name;
	private String fileSize;
	private String uploads;
	private String a0000;
	private BigDecimal sortId;
	private String time;
	private String treeId;

	// Constructors

	/** default constructor */
	public Folder() {
	}

	/** minimal constructor */
	public Folder(String id, String a0000) {
		this.id = id;
		this.a0000 = a0000;
	}

	/** full constructor */
	public Folder(String id, String catalog, String name, String fileSize, String uploads, String a0000, String time,
			String treeId, BigDecimal sortId) {
		super();
		this.id = id;
		this.a0000 = a0000;
		this.catalog = catalog;
		this.name = name;
		this.fileSize = fileSize;
		this.uploads = uploads;
		this.a0000 = a0000;
		this.time = time;
		this.treeId = treeId;
		this.sortId = sortId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploads() {
		return uploads;
	}

	public void setUploads(String uploads) {
		this.uploads = uploads;
	}

	public String getA0000() {
		return a0000;
	}

	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}

	public BigDecimal getSortId() {
		return sortId;
	}

	public void setSortId(BigDecimal sortId) {
		this.sortId = sortId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	

}