package com.insigma.odin.framework.privilege.entity;

import java.util.Date;

/**
 * SmtRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmtRole extends SmtAbsResource implements java.io.Serializable {

	// Fields

	private String parent;
	private Date createdate;
	private String hashcode;

	// Constructors

	/** default constructor */
	public SmtRole() {
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	
	/**
     * 所在系统：1、汇总 2、统计
     */
    private java.lang.String hostsys;

    public void setHostsys(final java.lang.String hostsys) {
        this.hostsys = hostsys;
    }

    public java.lang.String getHostsys() {
        return this.hostsys;
    }
    
    private Long sortid;

	public Long getSortid() {
		return sortid;
	}

	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}


}