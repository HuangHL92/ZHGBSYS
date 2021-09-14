package com.insigma.odin.framework.privilege.entity;

/**
 * SmtAct entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmtAct implements java.io.Serializable {

	// Fields

	private String actid;
	private String objectid;
	private String objecttype;
	private String roleid;
	private String sceneid;
	private String dispatchauth;
	private String hashcode;
	private String userid;

	// Constructors

	/** default constructor */
	public SmtAct() {
	}



	public String getActid() {
		return actid;
	}



	public void setActid(String actid) {
		this.actid = actid;
	}



	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getSceneid() {
		return this.sceneid;
	}

	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}

	public String getDispatchauth() {
		return this.dispatchauth;
	}

	public void setDispatchauth(String dispatchauth) {
		this.dispatchauth = dispatchauth;
	}

	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}



	public String getObjecttype() {
		return objecttype;
	}



	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	

}