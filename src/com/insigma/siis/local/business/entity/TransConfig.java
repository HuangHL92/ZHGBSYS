package com.insigma.siis.local.business.entity;

/**
 * TransConfig entity. @author MyEclipse Persistence Tools
 */

public class TransConfig implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String hostname;
	private Long port;
	private String username;
	private String password;
	private String type;
	private String status;

	// Constructors

	/** default constructor */
	public TransConfig() {
	}

	/** full constructor */
	public TransConfig(String name,String hostname, Long port, String username,
			String password, String type, String status) {
		this.name = name;
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.type = type;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
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

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Long getPort() {
		return this.port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}