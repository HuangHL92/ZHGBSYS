package com.insigma.odin.framework.privilege.entity;

import java.util.Date;

/**
 * SmtRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmtFunctionButton extends SmtAbsResource implements java.io.Serializable {

	// Fields

	private String functionid;
	private String buttonid;
	private String title;
	private String parent;
	private String orderno;
	private String type;

	// Constructors

	/** default constructor */
	public SmtFunctionButton() {
	}

	public String getFunctionid() {
		return functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getButtonid() {
		return buttonid;
	}

	public void setButtonid(String buttonid) {
		this.buttonid = buttonid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}