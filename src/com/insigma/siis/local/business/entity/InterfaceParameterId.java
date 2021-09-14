package com.insigma.siis.local.business.entity;

/**
 * InterfaceParameterId entity. @author MyEclipse Persistence Tools
 */

public class InterfaceParameterId implements java.io.Serializable {

	// Fields

	private String interfaceConfigId;
	private String interfaceParameterName;

	// Constructors

	/** default constructor */
	public InterfaceParameterId() {
	}

	/** full constructor */
	public InterfaceParameterId(String interfaceConfigId,
			String interfaceParameterName) {
		this.interfaceConfigId = interfaceConfigId;
		this.interfaceParameterName = interfaceParameterName;
	}

	// Property accessors

	public String getInterfaceConfigId() {
		return this.interfaceConfigId;
	}

	public void setInterfaceConfigId(String interfaceConfigId) {
		this.interfaceConfigId = interfaceConfigId;
	}

	public String getInterfaceParameterName() {
		return this.interfaceParameterName;
	}

	public void setInterfaceParameterName(String interfaceParameterName) {
		this.interfaceParameterName = interfaceParameterName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InterfaceParameterId))
			return false;
		InterfaceParameterId castOther = (InterfaceParameterId) other;

		return ((this.getInterfaceConfigId() == castOther
				.getInterfaceConfigId()) || (this.getInterfaceConfigId() != null
				&& castOther.getInterfaceConfigId() != null && this
				.getInterfaceConfigId()
				.equals(castOther.getInterfaceConfigId())))
				&& ((this.getInterfaceParameterName() == castOther
						.getInterfaceParameterName()) || (this
						.getInterfaceParameterName() != null
						&& castOther.getInterfaceParameterName() != null && this
						.getInterfaceParameterName().equals(
								castOther.getInterfaceParameterName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getInterfaceConfigId() == null ? 0 : this
						.getInterfaceConfigId().hashCode());
		result = 37
				* result
				+ (getInterfaceParameterName() == null ? 0 : this
						.getInterfaceParameterName().hashCode());
		return result;
	}

}