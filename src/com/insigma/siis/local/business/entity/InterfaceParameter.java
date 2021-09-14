package com.insigma.siis.local.business.entity;

/**
 * InterfaceParameter entity. @author MyEclipse Persistence Tools
 */

public class InterfaceParameter implements java.io.Serializable {

	// Fields

	private InterfaceParameterId id;
	private Integer interfaceParameterSequence;
	private String interfaceParameterDesc;
	private String interfaceParameterType;

	// Constructors

	/** default constructor */
	public InterfaceParameter() {
	}

	/** minimal constructor */
	public InterfaceParameter(InterfaceParameterId id,
			Integer interfaceParameterSequence, String interfaceParameterType) {
		this.id = id;
		this.interfaceParameterSequence = interfaceParameterSequence;
		this.interfaceParameterType = interfaceParameterType;
	}

	/** full constructor */
	public InterfaceParameter(InterfaceParameterId id,
			Integer interfaceParameterSequence, String interfaceParameterDesc,
			String interfaceParameterType) {
		this.id = id;
		this.interfaceParameterSequence = interfaceParameterSequence;
		this.interfaceParameterDesc = interfaceParameterDesc;
		this.interfaceParameterType = interfaceParameterType;
	}

	// Property accessors

	public InterfaceParameterId getId() {
		return this.id;
	}

	public void setId(InterfaceParameterId id) {
		this.id = id;
	}

	public Integer getInterfaceParameterSequence() {
		return this.interfaceParameterSequence;
	}

	public void setInterfaceParameterSequence(Integer interfaceParameterSequence) {
		this.interfaceParameterSequence = interfaceParameterSequence;
	}

	public String getInterfaceParameterDesc() {
		return this.interfaceParameterDesc;
	}

	public void setInterfaceParameterDesc(String interfaceParameterDesc) {
		this.interfaceParameterDesc = interfaceParameterDesc;
	}

	public String getInterfaceParameterType() {
		return this.interfaceParameterType;
	}

	public void setInterfaceParameterType(String interfaceParameterType) {
		this.interfaceParameterType = interfaceParameterType;
	}

}