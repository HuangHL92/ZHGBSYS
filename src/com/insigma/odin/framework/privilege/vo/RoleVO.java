package com.insigma.odin.framework.privilege.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ��ɫVO��
 * @author jinwei
 * @version 1.0
 * @created 18-����-2009 11:48:11
 */
public class RoleVO extends AbsResourceVO implements Serializable{
	
	/**
     * ����ϵͳ��1������ 2��ͳ��
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
	
	/**
	 * ����
	 */
	private String desc;
	/**
	 * ��ID
	 */
	private String parent;
	/**
	 * ״̬
	 */
	private String status;
	/**
	 * ����ʱ��
	 */
	private Date createdate;
	/**
	 * ɢ��ֵ
	 */
	private String hashcode;
	
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public RoleVO(){

	}

	public void finalize() throws Throwable {

	}

}