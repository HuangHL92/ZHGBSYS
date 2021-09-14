package com.insigma.siis.local.business.entity;

import java.math.BigDecimal;

/**
 * Folder entity. @author MyEclipse Persistence Tools
 */

public class FolderTree implements java.io.Serializable {

	// Fields

	private String id;
	private String parentId;
	private String name;
	private String a0000;
	private BigDecimal sortId;
	private String parentIdName;

	/** default constructor */
	public FolderTree() {
	}

	/** minimal constructor */
	public FolderTree(String id, String a0000) {
		this.id = id;
		this.a0000 = a0000;
	}

	/** full constructor */
	public FolderTree(String id, String parentId, String name, String a0000, String parentIdName,
			BigDecimal sortId) {
		super();
		this.id = id;
		this.setParentId(parentId);
		this.name = name;
		this.a0000 = a0000;
		this.sortId = sortId;
		this.setParentIdName(parentIdName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIdName() {
		return parentIdName;
	}

	public void setParentIdName(String parentIdName) {
		this.parentIdName = parentIdName;
	}

	
	

}